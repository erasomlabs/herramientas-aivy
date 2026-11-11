package erasom.herramientas.aivy

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.pdf.PdfRenderer
import android.net.Uri
import android.os.Bundle
import android.os.ParcelFileDescriptor
import androidx.appcompat.app.AppCompatActivity
import erasom.herramientas.aivy.databinding.ActivityPdfReaderBinding
import java.io.File
import java.io.FileOutputStream

class PDFReaderActivity : ToolActivity() {

    private lateinit var binding: ActivityPdfReaderBinding
    private var pdfRenderer: PdfRenderer? = null
    private var currentPage: PdfRenderer.Page? = null
    private var currentPageIndex = 0
    private var parcelFileDescriptor: ParcelFileDescriptor? = null

    private val filePicker = registerForActivityResult(androidx.activity.result.contract.ActivityResultContracts.OpenDocument()) { uri: Uri? ->
        uri?.let { openPdf(it) }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPdfReaderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnOpen.setOnClickListener { filePicker.launch(arrayOf("application/pdf")) }
        binding.btnPrev.setOnClickListener { showPage(currentPageIndex - 1) }
        binding.btnNext.setOnClickListener { showPage(currentPageIndex + 1) }
        binding.btnBack.setOnClickListener { finish() }
    }

    private fun openPdf(uri: Uri) {
        try {
            parcelFileDescriptor = contentResolver.openFileDescriptor(uri, "r")
            pdfRenderer = PdfRenderer(parcelFileDescriptor!!)
            currentPageIndex = 0
            showPage(0)
            binding.tvPageInfo.text = "Página 1 de ${pdfRenderer!!.pageCount}"
        } catch (e: Exception) {
            binding.tvPageInfo.text = "Error al abrir PDF: ${e.message}"
        }
    }

    private fun showPage(index: Int) {
        val renderer = pdfRenderer ?: return
        if (index < 0 || index >= renderer.pageCount) return

        currentPage?.close()
        currentPage = renderer.openPage(index)
        currentPageIndex = index

        val page = currentPage!!
        val bitmap = Bitmap.createBitmap(page.width, page.height, Bitmap.Config.ARGB_8888)
        page.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
        binding.ivPdf.setImageBitmap(bitmap)

        binding.tvPageInfo.text = "Página ${index + 1} de ${renderer.pageCount}"
    }

    override fun onDestroy() {
        currentPage?.close()
        pdfRenderer?.close()
        parcelFileDescriptor?.close()
        super.onDestroy()
    }
}
