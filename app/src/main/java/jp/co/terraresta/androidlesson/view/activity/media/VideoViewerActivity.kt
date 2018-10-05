package jp.co.terraresta.androidlesson.view.activity.media

import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.MediaController
import jp.co.terraresta.androidlesson.R
import jp.co.terraresta.androidlesson.common.Constants.REQUEST_KEY_VIDEO_URL
import kotlinx.android.synthetic.main.activity_video_viewer.*

class VideoViewerActivity : AppCompatActivity() {

    var vidUrl: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_viewer)

        setSupportActionBar(toolbar_video_view)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        var intent: Intent = intent
        vidUrl = intent.getStringExtra(REQUEST_KEY_VIDEO_URL)

        val vidUri = Uri.parse(vidUrl)
        video_view.setVideoURI(vidUri)
        video_view.setMediaController(MediaController(this))
        video_view.start()
    }

    override fun onBackPressed() {
        finish()
        super.onBackPressed()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        finish()
        return super.onSupportNavigateUp()
    }
}
