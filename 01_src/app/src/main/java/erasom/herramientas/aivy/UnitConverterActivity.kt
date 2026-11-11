package erasom.herramientas.aivy

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import erasom.herramientas.aivy.databinding.ActivityUnitConverterBinding

class UnitConverterActivity : ToolActivity() {

    private lateinit var binding: ActivityUnitConverterBinding

    private val categories = listOf("Longitud", "Peso", "Temperatura", "Volumen")
    private val units = mapOf(
        "Longitud" to listOf("Metros", "Kilómetros", "Centímetros", "Pulgadas", "Pies"),
        "Peso" to listOf("Kilogramos", "Gramos", "Libras", "Onzas"),
        "Temperatura" to listOf("Celsius", "Fahrenheit", "Kelvin"),
        "Volumen" to listOf("Litros", "Mililitros", "Galones", "Tazas")
    )

    private val conversionFactors = mapOf(
        "Metros" to 1.0, "Kilómetros" to 1000.0, "Centímetros" to 0.01,
        "Pulgadas" to 0.0254, "Pies" to 0.3048,
        "Kilogramos" to 1.0, "Gramos" to 0.001, "Libras" to 0.453592, "Onzas" to 0.0283495,
        "Litros" to 1.0, "Mililitros" to 0.001, "Galones" to 3.78541, "Tazas" to 0.236588
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUnitConverterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val catAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, categories)
        binding.spinnerCategory.adapter = catAdapter

        binding.spinnerCategory.setOnItemSelectedListener(object : android.widget.AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: android.widget.AdapterView<*>?, view: android.view.View?, position: Int, id: Long) {
                val cat = categories[position]
                val unitList = units[cat] ?: emptyList()
                val unitAdapter = ArrayAdapter(this@UnitConverterActivity, android.R.layout.simple_spinner_dropdown_item, unitList)
                binding.spinnerFrom.adapter = unitAdapter
                binding.spinnerTo.adapter = unitAdapter
            }
            override fun onNothingSelected(parent: android.widget.AdapterView<*>?) {}
        })

        binding.btnConvert.setOnClickListener {
            val value = binding.etValue.text.toString().toDoubleOrNull()
            if (value == null) {
                Toast.makeText(this, "Ingresa un número", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val cat = binding.spinnerCategory.selectedItem.toString()
            val from = binding.spinnerFrom.selectedItem.toString()
            val to = binding.spinnerTo.selectedItem.toString()

            val result = if (cat == "Temperatura") {
                convertTemperature(value, from, to)
            } else {
                val fromFactor = conversionFactors[from] ?: 1.0
                val toFactor = conversionFactors[to] ?: 1.0
                value * (fromFactor / toFactor)
            }
            binding.tvResult.text = "$value $from = %.4f $to".format(result)
        }

        binding.btnBack.setOnClickListener { finish() }
    }

    private fun convertTemperature(value: Double, from: String, to: String): Double {
        val celsius = when (from) {
            "Celsius" -> value
            "Fahrenheit" -> (value - 32) * 5 / 9
            "Kelvin" -> value - 273.15
            else -> value
        }
        return when (to) {
            "Celsius" -> celsius
            "Fahrenheit" -> celsius * 9 / 5 + 32
            "Kelvin" -> celsius + 273.15
            else -> celsius
        }
    }
}
