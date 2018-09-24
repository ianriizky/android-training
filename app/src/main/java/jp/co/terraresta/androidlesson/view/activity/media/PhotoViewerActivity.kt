package jp.co.terraresta.androidlesson.view.activity.media

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.squareup.picasso.Picasso

import jp.co.terraresta.androidlesson.R
import jp.co.terraresta.androidlesson.common.Constants.REQUEST_KEY_IMAGE_URL
import kotlinx.android.synthetic.main.activity_photo_viewer.*

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

        var intent: Intent = intent
        imageUrl = intent.getStringExtra(REQUEST_KEY_IMAGE_URL)

        if(imageUrl != null){
            Picasso.with(this).load(imageUrl).into(image_view)
        } else {
            image_view.setImageResource(R.drawable.ic_android_white_24dp)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}
