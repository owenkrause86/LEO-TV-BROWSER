package com.leo.tvbrowser

import android.os.Bundle
import android.webkit.*
import androidx.appcompat.app.AppCompatActivity
import android.widget.*

class MainActivity : AppCompatActivity() {
    lateinit var webView: WebView
    lateinit var urlBar: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        webView = findViewById(R.id.webView)
        urlBar = findViewById(R.id.urlBar)
        val goBtn = findViewById<Button>(R.id.goBtn)

        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true
        webView.settings.userAgentString = "Mozilla/5.0 (TV) LEO Browser Brave-like"
        webView.webViewClient = object : WebViewClient() {
            override fun shouldInterceptRequest(view: WebView, request: WebResourceRequest): WebResourceResponse? {
                val url = request.url.toString()
                if (url.contains("doubleclick") || url.contains("ads") || url.contains("googlesyndication") || url.contains("adservice")) {
                    return WebResourceResponse("text/plain", "utf-8", null) // Bloquea Ads
                }
                return super.shouldInterceptRequest(view, request)
            }
        }
        webView.loadUrl("https://www.google.com")
        goBtn.setOnClickListener {
            var url = urlBar.text.toString()
            if (!url.startsWith("http")) {
                url = if (url.contains(".")) "https://$url" else "https://www.google.com/search?q=$url"
            }
            webView.loadUrl(url)
        }
        webView.requestFocus()
    }
    override fun onBackPressed() {
        if (webView.canGoBack()) webView.goBack() else super.onBackPressed()
    }
}
