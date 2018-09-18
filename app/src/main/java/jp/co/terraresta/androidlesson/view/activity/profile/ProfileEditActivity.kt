package jp.co.terraresta.androidlesson.view.activity.profile

import android.app.*
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

import jp.co.terraresta.androidlesson.R
import jp.co.terraresta.androidlesson.common.Preferences
import jp.co.terraresta.androidlesson.presenter.profile.ProfileEditContract
import jp.co.terraresta.androidlesson.presenter.profile.ProfileEditPresenter
import kotlinx.android.synthetic.main.activity_profile_edit.*
import android.content.Intent
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import jp.co.terraresta.androidlesson.data.model.profile.ProfileDisplayData
import java.util.zip.Inflater
import android.graphics.drawable.ColorDrawable
import jp.co.terraresta.androidlesson.MainActivity
import android.content.DialogInterface
import android.databinding.DataBindingUtil
import android.graphics.Color
import java.util.*
import android.databinding.adapters.TextViewBindingAdapter.setText
import android.widget.DatePicker
import jp.co.terraresta.androidlesson.common.Constants.REQUEST_KEY_PROFILE_DATA
import jp.co.terraresta.androidlesson.data.model.common.BaseResultData
import jp.co.terraresta.androidlesson.databinding.ActivityProfileEditBinding
import jp.co.terraresta.androidlesson.view.view_model.profile.ProfileEditViewModel
import org.w3c.dom.Text


/**
 * Created by ooyama on 2017/05/29.
 */

class ProfileEditActivity : AppCompatActivity(), ProfileEditContract.View {
    override fun showError(msg: String) {
        alertError(msg)
        loading?.dismiss()
    }

    override fun setResponse(data: BaseResultData) {
        loading?.dismiss()
        val intent:Intent = intent
        intent.putExtra(REQUEST_KEY_PROFILE_DATA, profileDisplayData)
        setResult(Activity.RESULT_OK, intent)
        profileUpdate = true
        Toast.makeText(this, "Profile has been update", Toast.LENGTH_LONG).show()
        onBackPressed()
    }

    var profileUpdate: Boolean = false
    var profileEditPresenter: ProfileEditContract.Presenter? = null
    var profileEditPref: Preferences? = null
    var profileDisplayData: ProfileDisplayData? = null
    private var mDateSetListener: DatePickerDialog.OnDateSetListener? = null
    var loading: ProgressDialog? = null
    lateinit var profileBinding: ActivityProfileEditBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar()?.setDisplayShowHomeEnabled(true);

        super.onCreate(savedInstanceState)
        profileBinding = DataBindingUtil.setContentView(this, R.layout.activity_profile_edit)
//       setContentView(R.layout.activity_profile_edit)
        initView()
    }

    override fun onBackPressed() {
        if(profileUpdate){
            finish()
           super.onBackPressed()
        } else {
            dialogMessageExit()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        dialogMessageExit()
        return true
    }

    fun initView(){
        val intent:Intent = intent
        profileDisplayData = intent.getSerializableExtra(REQUEST_KEY_PROFILE_DATA) as ProfileDisplayData
        profileEditPref = Preferences()
        profileEditPresenter = ProfileEditPresenter(this, profileEditPref!!)

        var arrGender: Array<String>? = resources.getStringArray(R.array.sex)
        var arrJob: Array<String>? = resources.getStringArray(R.array.job)
        var arrResidence: Array<String>? = resources.getStringArray(R.array.residence)
        var arrPersonal: Array<String>? = resources.getStringArray(R.array.personality)
        var arrHobby: Array<String>? = resources.getStringArray(R.array.hobby)
        var title: String? = null
        var action: Int? = null


        var profileViewModel = ProfileEditViewModel()
        profileViewModel.nickname = profileDisplayData?.nickname!!
        profileViewModel.aboutme = profileDisplayData?.aboutMe!!
        if(profileDisplayData?.birthday != null){
            profileViewModel.birthday = profileDisplayData?.birthday!!
        } else {
           profileViewModel.birthday ="YYYY/MM/DD"
        }
        if(profileDisplayData?.residence != ""){
            profileViewModel.residence = profileDisplayData?.residence!!
        } else {
            profileViewModel.residence = arrResidence!![0]
        }
        profileViewModel.gender =  arrGender!![profileDisplayData?.gender!!]
        profileViewModel.personality = arrPersonal!![profileDisplayData?.personality!!]
        profileViewModel.job = arrJob!![profileDisplayData?.job!!]
        if(arrHobby?.size != profileDisplayData?.hobby!!.length){
           profileViewModel.hobby = ""
        } else {
            for(i in arrHobby!!.indices){
                if(profileDisplayData?.hobby!! != ""){
                    if(i.toString() == profileDisplayData?.hobby!![i].toString()){
                        profileViewModel.hobby = tv_hobby.text.toString().plus("- " +arrHobby[i])
                    }
                } else {
                    profileViewModel.hobby = ""
                }
            }
        }
        profileBinding.profileModel = profileViewModel

//        tv_personal.text = arrPersonal!![profileDisplayData?.personality!!]
//        tv_gender.text = arrGender!![profileDisplayData?.gender!!]
//        tv_job.text = arrJob!![profileDisplayData?.job!!]
//        tv_residence.text = profileDisplayData?.residence
//        tv_birthday.text = profileDisplayData?.birthday
//        aboutme.setText(profileDisplayData?.aboutMe, TextView.BufferType.EDITABLE)
//        nickname.setText(profileDisplayData?.nickname, TextView.BufferType.EDITABLE)

        // gender opions
        gender.setOnClickListener {
            title = "Choose your Gender"
            action = R.id.gender
            dialogSingleChoice(arrGender!!, profileDisplayData?.gender!! ,title!!, action!!)
        }

        // job options
        job.setOnClickListener {
            title = "What is your job?"
            action = R.id.job
            dialogSingleChoice(arrJob!!, profileDisplayData?.job!!, title!!, action!!)
        }

        // residence options
        residence.setOnClickListener {
            title = "Where do you live?"
            action = R.id.residence
            var checkeditem: Int? = 0
            for (i in arrResidence!!.indices){
               if(profileDisplayData?.residence!! == arrResidence!![i]){
                   checkeditem = i
               }
            }
            dialogSingleChoice(arrResidence!!, checkeditem!!,title!!, action!!)
        }

        // personal options
        personal.setOnClickListener {
            title = "How your personality?"
            action = R.id.personal
            dialogSingleChoice(arrPersonal!!, profileDisplayData?.personality!! ,title!!, action!!)
        }

        hobby.setOnClickListener {
            dialogMultiChoice(arrHobby!!)
        }

        // birthday options
        birthday.setOnClickListener {
            dialogDateTime()
        }
    }

    /*
    * SINGLE CHOICE DIALOG MESSAGE
     */
    fun dialogSingleChoice(arr: Array<String>, checkedItem:Int, title: String, action: Int) {
        val dialog: AlertDialog.Builder = AlertDialog.Builder(this, R.style.MaterialThemeDialog)
        dialog.setTitle(title)

        dialog.setSingleChoiceItems(arr, checkedItem){ dialogInterface, i ->
            when(action){
                R.id.gender -> {
                    profileDisplayData?.gender = i
                    tv_gender.text = arr[i]
                 }
                R.id.job -> {
                   profileDisplayData?.job = i
                    tv_job.text = arr[i]
                }
                R.id.residence -> {
                    profileDisplayData?.residence = arr[i]
                    tv_residence.text = arr[i]
                }
                R.id.personal -> {
                    profileDisplayData?.personality = i
                    tv_personal.text = arr[i]
                }
            }
                dialogInterface.dismiss()
        }

        dialog.setNeutralButton("Cancel"){ dialogInterface, i ->
           dialogInterface.cancel()
        }

        var alertDialog = dialog.create()
        alertDialog.show()
    }

    /*
    * MULTI CHOICE DIALOG MASSAGE
     */
   fun dialogMultiChoice(array: Array<String>){
       val arr = array
       val strings = Array(arr!!.size) { "-" }
       val arrChecked = BooleanArray(arr!!.size)

       for(i in arr.indices){
           if(profileDisplayData?.hobby!! != ""){
               if(profileDisplayData?.hobby!![i].toString() == i.toString()){
                    arrChecked[i]= true
               }
           }
       }

       val dialog:AlertDialog.Builder = AlertDialog.Builder(this, R.style.MaterialThemeDialog)
       dialog?.setTitle("Pick your hobby")
       dialog.setMultiChoiceItems(arr, arrChecked){dialogInterface, i, checkeditem ->
           arrChecked[i] = checkeditem
       }

       dialog.setPositiveButton("OK"){dialogInterface, i ->
           var hobbyString: String? = ""
           tv_hobby.text = ""
           for (j in arr.indices){
               if(arrChecked[j]){
                  strings[j] = j.toString()
                   tv_hobby.text = tv_hobby.text.toString().plus("-" +arr[j])
               }
               hobbyString = hobbyString.plus(strings[j])

           }
           profileDisplayData?.hobby = hobbyString
       }

       dialog.setNeutralButton("Cancel"){dialogInterface, i ->
          dialogInterface.dismiss()
       }

       val alertDialog = dialog.create()
       alertDialog.show()
   }



    /*
    * DATETIME DIALOG MESSAGE
     */
    fun dialogDateTime() {
        mDateSetListener = DatePickerDialog.OnDateSetListener { datePicker, year, month, day ->
            var month = month
            month = month + 1

            var finaldate: String = year.toString() + "/" + month + "/" + day
            tv_birthday.text = finaldate
            profileDisplayData?.birthday = finaldate
        }

        val cal = Calendar.getInstance()
        val year = cal.get(Calendar.YEAR)
        val month = cal.get(Calendar.MONTH)
        val day = cal.get(Calendar.DAY_OF_MONTH)

        val dialog = DatePickerDialog(
                this,
                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                mDateSetListener,
                year, month, day)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()
    }

    /*
    * ALERT DIALOG EXIT
     */
    fun dialogMessageExit() {
        var alertDialog: AlertDialog? = null
        var dialogInterface: DialogInterface.OnClickListener = DialogInterface.OnClickListener { dialogInterface, i ->
            when(i){
                DialogInterface.BUTTON_POSITIVE -> {
                    super.onBackPressed()
                    finish()
                }
                DialogInterface.BUTTON_NEGATIVE -> {
                    alertDialog!!.dismiss()
                }
            }
        }

        alertDialog = AlertDialog.Builder(this).create()
        alertDialog?.setMessage("Data is not saved. Do you want to discard the corrected data?")
        alertDialog?.setCancelable(true)
        alertDialog?.setButton(AlertDialog.BUTTON_POSITIVE, "Yes", dialogInterface)
        alertDialog?.setButton(AlertDialog.BUTTON_NEGATIVE, "No", dialogInterface)
        alertDialog?.show()
    }

    /*
    * ALER DIALOG ERROR
     */
    fun alertError(message: String) {
        var alertError = AlertDialog.Builder(this).create()
        alertError?.setMessage(message)
        alertError?.setButton(AlertDialog.BUTTON_NEUTRAL, "Ok", DialogInterface.OnClickListener { dialogInterface, i ->
            alertError.dismiss()
        })
        alertError.show()
    }

    /*
    * ALERT ERROR
     */
    fun loader() {
        loading = ProgressDialog(this)
        loading?.setMessage("Updating profile...")
        loading?.setCancelable(false)
        loading?.show()
    }

    /*
    * SAVE ACTION MENU RIGHT NAVBAR
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        var menuInflater: MenuInflater = getMenuInflater()
        menuInflater.inflate(R.menu.profileedit_save, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId) {
           R.id.profile_save -> {
               profileDisplayData?.nickname = nickname.text.toString().trim()
               profileDisplayData?.aboutMe = aboutme.text.toString().trim()
               profileDisplayData?.imageId = 0
               profileEditPresenter?.editProfile(this, profileDisplayData!!)

               loader()
           }
        }
        return super.onOptionsItemSelected(item)
    }

}
