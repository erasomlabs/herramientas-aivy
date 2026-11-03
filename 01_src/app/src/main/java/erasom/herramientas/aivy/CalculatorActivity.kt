package erasom.herramientas.aivy

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import erasom.herramientas.aivy.databinding.ActivityCalculatorBinding

class CalculatorActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCalculatorBinding
    private var currentInput = ""
    private var operator = ""
    private var firstOperand = 0.0
    private var isNewInput = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCalculatorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val buttons = listOf(
            binding.btn0, binding.btn1, binding.btn2, binding.btn3,
            binding.btn4, binding.btn5, binding.btn6, binding.btn7,
            binding.btn8, binding.btn9, binding.btnDot
        )
        buttons.forEach { btn ->
            btn.setOnClickListener {
                if (isNewInput) { currentInput = ""; isNewInput = false }
                currentInput += btn.text
                binding.tvDisplay.text = currentInput
            }
        }

        binding.btnAdd.setOnClickListener { setOperator("+") }
        binding.btnSub.setOnClickListener { setOperator("-") }
        binding.btnMul.setOnClickListener { setOperator("*") }
        binding.btnDiv.setOnClickListener { setOperator("/") }

        binding.btnEquals.setOnClickListener { calculate() }
        binding.btnClear.setOnClickListener { clear() }
        binding.btnBack.setOnClickListener { finish() }
    }

    private fun setOperator(op: String) {
        if (currentInput.isNotEmpty()) {
            firstOperand = currentInput.toDoubleOrNull() ?: 0.0
            operator = op
            isNewInput = true
            binding.tvDisplay.text = "$firstOperand $op"
        }
    }

    private fun calculate() {
        val second = currentInput.toDoubleOrNull() ?: 0.0
        val result = when (operator) {
            "+" -> firstOperand + second
            "-" -> firstOperand - second
            "*" -> firstOperand * second
            "/" -> if (second != 0.0) firstOperand / second else { Toast.makeText(this, "División por cero", Toast.LENGTH_SHORT).show(); return }
            else -> second
        }
        binding.tvDisplay.text = result.toString()
        currentInput = result.toString()
        isNewInput = true
        operator = ""
    }

    private fun clear() {
        currentInput = ""
        operator = ""
        firstOperand = 0.0
        isNewInput = true
        binding.tvDisplay.text = "0"
    }
}
