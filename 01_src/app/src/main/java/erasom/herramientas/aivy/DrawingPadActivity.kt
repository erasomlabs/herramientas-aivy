package erasom.herramientas.aivy

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import erasom.herramientas.aivy.databinding.ActivityDrawingPadBinding

class DrawingPadActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDrawingPadBinding
    private lateinit var canvas: Canvas
    private lateinit var bitmap: Bitmap
    private val paint = Paint().apply {
        color = Color.BLACK
        strokeWidth = 8f
        style = Paint.Style.STROKE
        isAntiAlias = true
        strokeCap = Paint.Cap.ROUND
    }
    private var lastX = 0f
    private var lastY = 0f
    private var isDrawing = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDrawingPadBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.drawingView.post {
            bitmap = Bitmap.createBitmap(binding.drawingView.width, binding.drawingView.height, Bitmap.Config.ARGB_8888)
            canvas = Canvas(bitmap)
            canvas.drawColor(Color.WHITE)
            binding.drawingView.setImageBitmap(bitmap)
        }

        binding.drawingView.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    lastX = event.x
                    lastY = event.y
                    isDrawing = true
                    true
                }
                MotionEvent.ACTION_MOVE -> {
                    if (isDrawing) {
                        canvas.drawLine(lastX, lastY, event.x, event.y, paint)
                        binding.drawingView.invalidate()
                        lastX = event.x
                        lastY = event.y
                    }
                    true
                }
                MotionEvent.ACTION_UP -> {
                    isDrawing = false
                    true
                }
                else -> false
            }
        }

        binding.btnClear.setOnClickListener {
            canvas.drawColor(Color.WHITE)
            binding.drawingView.invalidate()
        }

        binding.btnColorBlack.setOnClickListener { paint.color = Color.BLACK }
        binding.btnColorRed.setOnClickListener { paint.color = Color.RED }
        binding.btnColorBlue.setOnClickListener { paint.color = Color.BLUE }
        binding.btnColorGreen.setOnClickListener { paint.color = Color.GREEN }

        binding.btnBack.setOnClickListener { finish() }
    }
}
