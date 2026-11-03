package erasom.herramientas.aivy

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import erasom.herramientas.aivy.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnNotepad.setOnClickListener { startActivity(Intent(this, NotepadActivity::class.java)) }
        binding.btnChatBot.setOnClickListener { startActivity(Intent(this, ChatBotActivity::class.java)) }
        binding.btnCalendar.setOnClickListener { startActivity(Intent(this, CalendarActivity::class.java)) }
        binding.btnWhatsApp.setOnClickListener { startActivity(Intent(this, WhatsAppLinkActivity::class.java)) }
        binding.btnCalculator.setOnClickListener { startActivity(Intent(this, CalculatorActivity::class.java)) }
        binding.btnDrawing.setOnClickListener { startActivity(Intent(this, DrawingPadActivity::class.java)) }
        binding.btnQR.setOnClickListener { startActivity(Intent(this, QRReaderActivity::class.java)) }
        binding.btnMaps.setOnClickListener { startActivity(Intent(this, MapsActivity::class.java)) }
        binding.btnConverter.setOnClickListener { startActivity(Intent(this, UnitConverterActivity::class.java)) }
        binding.btnPDF.setOnClickListener { startActivity(Intent(this, PDFReaderActivity::class.java)) }
        binding.btnLED.setOnClickListener { startActivity(Intent(this, LEDPanelActivity::class.java)) }
    }
}
