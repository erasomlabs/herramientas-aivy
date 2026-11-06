package erasom.herramientas.aivy

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import erasom.herramientas.aivy.databinding.ActivityWhatsappLinkBinding

class WhatsAppLinkActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWhatsappLinkBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWhatsappLinkBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnOpen.setOnClickListener {
            val number = binding.etNumber.text.toString().trim()
            val message = binding.etMessage.text.toString().trim()
            if (number.isEmpty()) {
                Toast.makeText(this, "Ingresa un número", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val url = "https://wa.me/$number?text=${Uri.encode(message)}"
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
        }

        binding.btnBack.setOnClickListener { finish() }
    }
}
