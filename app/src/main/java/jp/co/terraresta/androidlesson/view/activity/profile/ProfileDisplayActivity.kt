package jp.co.terraresta.androidlesson.view.activity.profile

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.squareup.picasso.Picasso

import jp.co.terraresta.androidlesson.R
import jp.co.terraresta.androidlesson.common.Constants.REQUEST_KEY_IMAGE_URL
import jp.co.terraresta.androidlesson.common.Constants.REQUEST_NAME_USER_ID
import jp.co.terraresta.androidlesson.data.model.profile.ProfileDisplayData
import jp.co.terraresta.androidlesson.presenter.profile.ProfileDisplayContract
import jp.co.terraresta.androidlesson.presenter.profile.ProfileDisplayPresenter
import jp.co.terraresta.androidlesson.view.activity.media.PhotoViewerActivity
import jp.co.terraresta.androidlesson.view.activity.talk.TalkActivity
import kotlinx.android.synthetic.main.activity_profile_display.*

/**
 * Created by ooyama on 2017/05/29.
 */

class ProfileDisplayActivity : AppCompatActivity(), ProfileDisplayContract.View {
    override fun showError(msg: String) {
        alertError(msg)
        loading?.dismiss()
    }

    override fun setRess(data: ProfileDisplayData) {
        profileData = data
        if(data.imageId != 0) {
            Picasso.with(this).load(data.imageUrl).into(iv_photo_profile)
        }
        if(data.job != 0){
            tv_job.text = arrJob!![data.job]
        }
        if(data.personality != 0){
            tv_personal.text = arrPersonal!![data.personality]
        }
        if(data.gender != 0){
            tv_gender.text = arrGender!![data.gender]
        }

        tv_aboutme.text = data.aboutMe
        tv_residence.text = data.residence
        tv_birthday.text = data.birthday
        setHobby(arrHobby!!, data)
        nickname.text = data.nickname
        loading?.dismiss()
    }

    var profileData: ProfileDisplayData? = null
    var arrHobby: Array<String>? = null
    var arrGender: Array<String>? = null
    var arrJob: Array<String>? = null
    var arrResidence: Array<String>? = null
    var arrPersonal: Array<String>? = null

    var uid: Int? = null
    var profileDisplayPresenter: ProfileDisplayContract.Presenter? = null
    var loading: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar()?.setDisplayShowHomeEnabled(true);

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_display)
        initialize()
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    override fun onSupportNavigateUp(): Boolean {
        super.onBackPressed()
        return super.onSupportNavigateUp()
    }

    fun initialize(){
        profileDisplayPresenter = ProfileDisplayPresenter(this, this)
        val intent: Intent = intent
        uid = intent.getIntExtra(REQUEST_NAME_USER_ID, 0)

        iv_photo_profile.setImageResource(R.drawable.ic_android_black_24dp)
        nickname.text = ""
        tv_job.text = ""
        tv_birthday.text = ""
        tv_gender.text = ""
        tv_residence.text = ""
        tv_personal.text = ""
        tv_aboutme.text = ""

        arrHobby = resources.getStringArray(R.array.hobby)
        arrGender = resources.getStringArray(R.array.sex)
        arrJob = resources.getStringArray(R.array.job)
        arrResidence = resources.getStringArray(R.array.residence)
        arrPersonal = resources.getStringArray(R.array.personality)
        photo_profile_foreground.setOnClickListener {
            var intent: Intent = Intent(this, PhotoViewerActivity::class.java)
            intent.putExtra(REQUEST_KEY_IMAGE_URL, profileData?.imageUrl)
            startActivity(intent)
        }
        btn_send_message.setOnClickListener {
            var intent: Intent = Intent(this, TalkActivity::class.java)
            startActivity(intent)
        }

        profileDisplayPresenter?.fetchUserData(uid!!)
        loader()
    }

    fun setHobby(arr: Array<String>, profileDisplayData: ProfileDisplayData){
        var arrHobby = arr
        var hobbyList= profileDisplayData?.hobby!!.replace(",", "")
        var tempHobby: Array<String>? = Array(arrHobby.size){""}
        val codeHobby = Array(arrHobby!!.size) {it}
        tv_hobby.text = ""

        for(i in codeHobby.indices){
            if(hobbyList.length != 0){
                for(j in 0..hobbyList.length-1){
                    if(codeHobby[i].toString() == hobbyList[j].toString()){
                        tempHobby!![i] = hobbyList[j].toString()
                    }
                }
                if(tempHobby!![i] == codeHobby[i].toString()){
                    tv_hobby.text = tv_hobby.text.toString().plus(arrHobby[i] +", ")
                }
            }
        }
    }

    /*
     * LOADER
      */
    fun loader() {
        loading = ProgressDialog(this)
        loading?.setMessage("Fetching profile...")
        loading?.setCancelable(false)
        loading?.show()
    }

    /*
     * ALERT ERROR
      */
    fun alertError(message: String) {
        var alertError = AlertDialog.Builder(this).create()
        alertError?.setMessage(message)
        alertError?.setButton(AlertDialog.BUTTON_NEUTRAL, "Ok", DialogInterface.OnClickListener { dialogInterface, i ->
            alertError.dismiss()
        })
        alertError.show()
    }
}
