package jp.co.terraresta.androidlesson.view.activity.profile

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

import jp.co.terraresta.androidlesson.R

/**
 * Created by ooyama on 2017/05/29.
 */

class ProfileEditActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_edit)
    }

    override fun onBackPressed() {
        finish()
        super.onBackPressed()
    }
}
