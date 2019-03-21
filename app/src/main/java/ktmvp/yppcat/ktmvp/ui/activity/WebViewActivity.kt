package ktmvp.yppcat.ktmvp.ui.activity

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.view.View
import android.webkit.*
import com.tencent.sonic.sdk.SonicConfig
import com.tencent.sonic.sdk.SonicEngine
import com.tencent.sonic.sdk.SonicSession
import com.tencent.sonic.sdk.SonicSessionConfig
import kotlinx.android.synthetic.main.activity_web_view.*
import ktmvp.yppcat.ktmvp.R
import ktmvp.yppcat.ktmvp.R.id.webView
import ktmvp.yppcat.ktmvp.application.HostRuntime
import ktmvp.yppcat.ktmvp.application.SonicSessionClientImpl
import ktmvp.yppcat.ktmvp.base.BaseActivity

@Suppress("DEPRECATION")
class WebViewActivity : BaseActivity() {

    private lateinit var url: String
    private var sonicSession: SonicSession? = null
    private var sonicSessionClient: SonicSessionClientImpl = SonicSessionClientImpl()

    companion object {
        const val PARAM_URL = "param_url"
        const val PARAM_MODE = "param_mode"
    }


    override fun layoutId(): Int {
        return R.layout.activity_web_view
    }

    override fun initData() {
        url = intent.getStringExtra(PARAM_URL)

        if (!SonicEngine.isGetInstanceAllowed()) {
            SonicEngine.createInstance(HostRuntime(application), SonicConfig.Builder().build())
        }

        val sessionConfigBuilder = SonicSessionConfig.Builder()
        sonicSession = SonicEngine.getInstance().createSession(url, sessionConfigBuilder.build())
        if (null != sonicSession) {
            sonicSession!!.bindClient(sonicSessionClient)
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun initView() {
        val webSettings = webView.settings
        webSettings?.javaScriptEnabled = true
        webSettings?.allowContentAccess = true
        webSettings?.databaseEnabled = true
        webSettings?.domStorageEnabled = true
        webSettings?.setAppCacheEnabled(true)
        webSettings?.savePassword = true
        webSettings?.saveFormData = true
        webSettings?.useWideViewPort = true
        webSettings?.loadWithOverviewMode = true
        webView.canGoBack()

        sonicSessionClient.bindWebView(webView)
        sonicSessionClient.clientReady()

        webView!!.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: String) {
                super.onPageFinished(view, url)
                if (sonicSession != null) {
                    sonicSession!!.sessionClient.pageFinish(url)
                }
            }

            @TargetApi(21)
            override fun shouldInterceptRequest(view: WebView, request: WebResourceRequest): WebResourceResponse? {
                return shouldInterceptRequest(view, request.url.toString())
            }

            override fun shouldInterceptRequest(view: WebView, url: String): WebResourceResponse? {
                return if (sonicSession != null) {
                    sonicSession!!.sessionClient.requestResource(url) as WebResourceResponse
                } else null
            }

        }

        webView!!.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView, newProgress: Int) {
                progressbar!!.progress = newProgress
                if (newProgress == 100) {
                    progressbar!!.visibility = View.GONE
                }
                super.onProgressChanged(view, newProgress)
            }
        }
    }

    override fun start() {
        webView.loadUrl(url)
    }

    override fun onBackPressed() {
        if (webView!!.canGoBack()) {
            webView!!.goBack()
        } else {
            super.onBackPressed()
        }
    }

    override fun onDestroy() {
        if (null != sonicSession) {
            sonicSession!!.destroy()
            sonicSession = null
        }
        super.onDestroy()
    }
}
