package erasom.herramientas.aivy

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import erasom.herramientas.aivy.databinding.ActivityLedPanelBinding

class LEDPanelActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLedPanelBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLedPanelBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnDisplay.setOnClickListener {
            val text = binding.etText.text.toString()
            if (text.isNotEmpty()) {
                binding.tvLED.text = text.uppercase()
            }
        }

        binding.btnBack.setOnClickListener { finish() }
    }
}
