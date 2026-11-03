package erasom.herramientas.aivy

import android.os.Bundle
import android.webkit.JavascriptInterface
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import erasom.herramientas.aivy.databinding.ActivityQrReaderBinding

class QRReaderActivity : AppCompatActivity() {

    private lateinit var binding: ActivityQrReaderBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQrReaderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val webView = binding.webView
        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true
        webView.webChromeClient = WebChromeClient()
        webView.webViewClient = WebViewClient()
        webView.addJavascriptInterface(QRInterface(), "Android")

        // Load local HTML with jsQR library
        webView.loadUrl("file:///android_asset/qr_scanner.html")

        binding.btnBack.setOnClickListener { finish() }
    }

    inner class QRInterface {
        @JavascriptInterface
        fun onQRScanned(result: String) {
            runOnUiThread {
                binding.tvResult.text = result
                Toast.makeText(this@QRReaderActivity, "QR detectado", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
