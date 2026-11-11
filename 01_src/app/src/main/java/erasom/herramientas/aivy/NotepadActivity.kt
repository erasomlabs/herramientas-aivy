package erasom.herramientas.aivy

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import erasom.herramientas.aivy.databinding.ActivityNotepadBinding

class NotepadActivity : ToolActivity() {

    private lateinit var binding: ActivityNotepadBinding
    private lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotepadBinding.inflate(layoutInflater)
        setContentView(binding.root)

        prefs = getSharedPreferences("notepad_prefs", MODE_PRIVATE)
        binding.etNotepad.setText(prefs.getString("note", ""))

        binding.btnSave.setOnClickListener {
            prefs.edit().putString("note", binding.etNotepad.text.toString()).apply()
            Toast.makeText(this, "Guardado", Toast.LENGTH_SHORT).show()
        }

        binding.btnBack.setOnClickListener { finish() }
    }
}
