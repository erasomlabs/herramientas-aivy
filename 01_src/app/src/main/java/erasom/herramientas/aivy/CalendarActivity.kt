package erasom.herramientas.aivy

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import erasom.herramientas.aivy.databinding.ActivityCalendarBinding

class CalendarActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCalendarBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCalendarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            binding.tvSelectedDate.text = "$dayOfMonth/${month + 1}/$year"
        }

        binding.btnBack.setOnClickListener { finish() }
    }
}
