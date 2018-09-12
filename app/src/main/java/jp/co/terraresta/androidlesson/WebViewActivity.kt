package jp.co.terraresta.androidlesson

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import jp.co.terraresta.androidlesson.common.Constants.WEB_INFO_PAGE_TERMS_SERVICE
import kotlinx.android.synthetic.main.activity_web_view.*

class WebViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)
        wv_terms_services.loadUrl(WEB_INFO_PAGE_TERMS_SERVICE)
    }
}
