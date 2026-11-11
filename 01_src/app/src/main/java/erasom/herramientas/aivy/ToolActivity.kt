package erasom.herramientas.aivy

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

/**
 * Base para todas las actividades de herramientas.
 * Al cerrarse (botón atrás del layout o back del sistema) muestra el
 * intersticial cada 4 usos antes de terminar la activity.
 */
abstract class ToolActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "ToolActivity"
    }

    private var closingWithAd = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "${javaClass.simpleName} abierta")
    }

    override fun finish() {
        val mm = MonetizationManager.shared
        if (mm != null && !closingWithAd) {
            closingWithAd = true
            mm.onToolClosed(this) { super.finish() }
        } else {
            super.finish()
        }
    }
}
