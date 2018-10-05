package jp.co.terraresta.androidlesson.view.activity.media

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.squareup.picasso.Picasso

import jp.co.terraresta.androidlesson.R
import jp.co.terraresta.androidlesson.common.Constants.REQUEST_KEY_IMAGE_URL
import kotlinx.android.synthetic.main.activity_photo_viewer.*
import kotlinx.android.synthetic.main.activity_video_viewer.*

/**
 * Created by ooyama on 2017/05/29.
 */

class PhotoViewerActivity : AppCompatActivity() {

    var imageUrl: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_viewer)

        setSupportActionBar(toolbar_photo_view)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        val intent: Intent = intent
        imageUrl = intent.getStringExtra(REQUEST_KEY_IMAGE_URL)

        if(imageUrl == null){
           image_view.setImageResource(R.drawable.ic_android_white_24dp)
        } else {
           Picasso.with(this)
                   .load(imageUrl)
                   .into(image_view)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        finish()
        return super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}
