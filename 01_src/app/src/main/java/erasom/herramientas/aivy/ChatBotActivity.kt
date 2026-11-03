package erasom.herramientas.aivy

import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import erasom.herramientas.aivy.databinding.ActivityChatBotBinding

class ChatBotActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChatBotBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBotBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val webView = binding.webView
        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true
        webView.webViewClient = WebViewClient()

        // Cargar Hugging Face Chat (IA open source gratuita)
        // El usuario puede cambiar esta URL por otra IA open source
        webView.loadUrl("https://huggingface.co/chat")

        binding.btnBack.setOnClickListener { finish() }
    }
}
