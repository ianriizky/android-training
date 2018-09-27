package jp.co.terraresta.androidlesson.view.fragment.my_page

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.support.design.widget.BottomSheetBehavior
import android.support.v4.app.Fragment
import android.view.*
import android.webkit.WebView
import de.hdodenhof.circleimageview.CircleImageView

import jp.co.terraresta.androidlesson.R
import jp.co.terraresta.androidlesson.WebViewActivity
import jp.co.terraresta.androidlesson.common.Constants.REQUEST_CODE_CAMERA_ACTIVITY
import jp.co.terraresta.androidlesson.common.Preferences
import jp.co.terraresta.androidlesson.data.model.profile.ProfileDisplayData
import jp.co.terraresta.androidlesson.presenter.account.DeleteAccountContract
import jp.co.terraresta.androidlesson.presenter.account.DeleteAccountPresenter
import jp.co.terraresta.androidlesson.presenter.my_page.MyPageContract
import jp.co.terraresta.androidlesson.presenter.my_page.MyPagePresenter
import android.app.*
import android.app.Activity.RESULT_OK
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Environment
import android.support.v4.app.ActivityCompat
import android.support.v4.content.FileProvider
import android.widget.*
import com.squareup.picasso.Picasso
import jp.co.terraresta.androidlesson.common.Constants.REQUEST_CODE_GALLERY_ACTIVITY
import jp.co.terraresta.androidlesson.common.Constants.REQUEST_CODE_PROFILE_EDIT
import jp.co.terraresta.androidlesson.common.Constants.REQUEST_KEY_PROFILE_DATA
import jp.co.terraresta.androidlesson.data.model.media.ImageUploadData
import jp.co.terraresta.androidlesson.view.activity.profile.ProfileEditActivity
import java.io.File
import java.text.SimpleDateFormat
import java.util.*


/**
 * Created by ooyama on 2017/05/29.
 */

class MyPageFragment :  android.support.v4.app.Fragment(), MyPageContract.View{
    override fun showError(msg: String) {
        alertError(msg)
        loading?.dismiss()
    }

    override fun setFetchRess(data: ProfileDisplayData) {
        profileDisplayData = data
        displayEmail = myPageView?.findViewById(R.id.tv_email)  as TextView
        displayPass = myPageView?.findViewById(R.id.tv_password) as TextView
        photoProfile = myPageView?.findViewById(R.id.iv_photo_profile) as CircleImageView

        if(data.imageId != 0){
            Picasso.with(this.context).load(data.imageUrl).into(photoProfile)
            sheetDelPic?.visibility = View.VISIBLE
        } else {
            photoProfile?.setImageResource(R.drawable.ic_android_black_24dp)
            sheetDelPic?.visibility = View.GONE
        }

        displayEmail?.text = data.email
        displayPass?.text = data.password
        loading?.dismiss()

    }


    //    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//    }
    var myPageView: View? = null
    var pref: Preferences = Preferences()
    var myPagePresenter: MyPageContract.Presenter? = null
    var delAccountPresenter: DeleteAccountContract.Presenter? = null
    var loading: ProgressDialog? = null
    var alertDialog: AlertDialog? = null
    var profileDisplayData: ProfileDisplayData? = null

    var photoProfile: CircleImageView? = null
    var photoProfileAct: RelativeLayout? = null
    var photoProfileSheet: View? = null
    var photoProfileSheetBehavior: BottomSheetBehavior<View>? =null
    var sheetTakePic: LinearLayout? = null
    var sheetGalery: LinearLayout? = null
    var sheetDelPic: LinearLayout? = null

    var deleteProfile: LinearLayout? = null
    var editProfile: Button? = null
    var termsServices: LinearLayout? = null
    var displayEmail: TextView? = null
    var displayPass: TextView? = null
    var termsWebView: WebView? = null

    var photoFile: File? = null
    var photoUri: Uri? = null


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        myPageView= inflater?.inflate(R.layout.fragment_my_page, container, false)
        initView()
        return myPageView
    }

    fun initView(){
        myPagePresenter = MyPagePresenter(this.context, this)
        myPagePresenter?.fetchUserData()
//        loader()
        editProfile = myPageView?.findViewById(R.id.btn_edit_profile) as Button
        deleteProfile = myPageView?.findViewById(R.id.ll_delete_account) as LinearLayout
        termsServices = myPageView?.findViewById(R.id.ll_terms) as LinearLayout

//        loader()
        deleteProfile?.setOnClickListener {
            dialogMessageDel()
        }
        termsServices?.setOnClickListener {
            var webview: Intent = Intent(this.context, WebViewActivity::class.java)
            this.context.startActivity(webview)
        }

        // EDIT PROFILE BUTTON
        editProfile?.setOnClickListener {
            var intent: Intent = Intent(this.context, ProfileEditActivity::class.java)
            intent.putExtra(REQUEST_KEY_PROFILE_DATA, profileDisplayData)
            startActivityForResult(intent, REQUEST_CODE_PROFILE_EDIT)
        }
        photoprofileInit()

    }


    fun photoprofileInit(){
       photoProfileSheet = myPageView?.findViewById(R.id.sheet_photo_profile) as View
        photoProfileAct = myPageView?.findViewById(R.id.rl_photo_profile_clickable) as RelativeLayout
        photoProfileSheetBehavior = BottomSheetBehavior.from(photoProfileSheet)
        sheetGalery = myPageView?.findViewById(R.id.sheet_select_photo) as LinearLayout
        sheetTakePic = myPageView?.findViewById(R.id.sheet_take_photo) as LinearLayout
        sheetDelPic = myPageView?.findViewById(R.id.sheet_delete_photo) as LinearLayout


        // open up bottom sheet
        photoProfileAct?.setOnClickListener {
            if(photoProfileSheetBehavior?.state != BottomSheetBehavior.STATE_EXPANDED){
               photoProfileSheetBehavior?.state = BottomSheetBehavior.STATE_EXPANDED
            } else {
                photoProfileSheetBehavior?.state = BottomSheetBehavior.STATE_COLLAPSED
            }
        }

        // take photo profile from camera
        sheetTakePic?.setOnClickListener {
            requestPermission(REQUEST_CODE_CAMERA_ACTIVITY)
            photoProfileSheetBehavior?.state = BottomSheetBehavior.STATE_COLLAPSED
        }

        // take photo from gallery
        sheetGalery?.setOnClickListener {
            requestPermission(REQUEST_CODE_GALLERY_ACTIVITY)
            photoProfileSheetBehavior?.state = BottomSheetBehavior.STATE_COLLAPSED
        }

        // delete photo profile
        sheetDelPic?.setOnClickListener {
            var imageData: ImageUploadData? = ImageUploadData()
            imageData?.imageId = profileDisplayData!!.imageId

            myPagePresenter?.updatePhotoProfile(imageData!!, "delete_pp")
            photoProfileSheetBehavior?.state = BottomSheetBehavior.STATE_COLLAPSED

            loader()
        }

    }


    /*
     * RESULT REQEST PERMISSION
      */
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when(requestCode){
            REQUEST_CODE_CAMERA_ACTIVITY -> {
                if(grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    takePhoto()
                } else {
                    return
                }
                return
            }

            REQUEST_CODE_GALLERY_ACTIVITY -> {
                if(grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                   openGallery()
                } else {
                   return
                }
            }
        }
    }

    fun openGallery(){
        var intent: Intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, REQUEST_CODE_GALLERY_ACTIVITY)
    }

    fun getPhotoFileUri(): File {
        // Create an image file name
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imageFileName = "JPEG_" + timeStamp + "_"
        val storageDir = this.context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val image = File.createTempFile(
                imageFileName, /* prefix */
                ".jpg", /* suffix */
                storageDir      /* directory */
        )

        // Save a file: path for use with ACTION_VIEW intents
        // Return the file target for the photo based on filename
        return image
    }

    fun takePhoto() {
        var intent: Intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        photoFile = getPhotoFileUri()
        if(photoFile != null) {
            var fileprovider: Uri = FileProvider.getUriForFile(this.activity, "jp.co.terraresta.androidlesson.fileprovider", photoFile)
            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileprovider)
            startActivityForResult(intent, REQUEST_CODE_CAMERA_ACTIVITY)
        }
    }

    /*
     * UTILITY REQUEST PERMISSION (CAMERA, GALLERY)
      */
    fun requestPermission(codePerimission: Int) {
        when(codePerimission){
            REQUEST_CODE_CAMERA_ACTIVITY -> {
               if(ActivityCompat.checkSelfPermission(this.context, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
                   requestPermissions(arrayOf(android.Manifest.permission.CAMERA), REQUEST_CODE_CAMERA_ACTIVITY)
                   return
               } else {
                   takePhoto()
               }
            }

            REQUEST_CODE_GALLERY_ACTIVITY -> {
               if(ActivityCompat.checkSelfPermission(this.context, android.Manifest.permission.READ_EXTERNAL_STORAGE ) != PackageManager.PERMISSION_GRANTED){
                   requestPermissions(arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), REQUEST_CODE_GALLERY_ACTIVITY)
                   return
               } else {
                   openGallery()
               }
            }
        }
    }

    /*
    * ACTIVITY INTENT RESULT (CAMERA. GALLERY INTENT)
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(resultCode == RESULT_OK){
            loader()
            if(requestCode == REQUEST_CODE_CAMERA_ACTIVITY){
                var uri: Uri = Uri.parse(photoFile?.absolutePath!!)
                myPagePresenter?.takePhoto(uri)
            } else if(requestCode == REQUEST_CODE_GALLERY_ACTIVITY) {
                var uri: Uri = data?.data!!
                myPagePresenter?.openGallery(uri)
            } else if(requestCode == REQUEST_CODE_PROFILE_EDIT){
               profileDisplayData  = data?.extras?.get(REQUEST_KEY_PROFILE_DATA) as ProfileDisplayData
                loading?.dismiss()
            }
//            if(data != null) {
//            } else {
//               Toast.makeText(this.context, "Please try again", Toast.LENGTH_LONG).show()
//            }
        } else {
            return
        }
        super.onActivityResult(requestCode, resultCode, data)
    }


    /*
     * LOADER
      */
    fun loader() {
        loading = ProgressDialog(activity)
        loading?.setMessage("Please Wait...")
        loading?.setCancelable(false)
        loading?.show()
    }

    /*
     * ALERT ERROR
      */
    fun alertError(message: String) {
        var alertError = AlertDialog.Builder(activity).create()
        alertError?.setMessage(message)
        alertError?.setButton(AlertDialog.BUTTON_NEUTRAL, "Ok", DialogInterface.OnClickListener { dialogInterface, i ->
            alertError.dismiss()
        })
        alertError.show()
    }

    /*
    * DIALOG ALERT
     */
    fun dialogMessageDel() {
        var dialogInterface: DialogInterface.OnClickListener = DialogInterface.OnClickListener { dialogInterface, i ->
            when(i){
                DialogInterface.BUTTON_POSITIVE -> {
            delAccountPresenter = DeleteAccountPresenter(this.context, this)
            delAccountPresenter?.deleteAccount()
                }
                DialogInterface.BUTTON_NEGATIVE -> {
                    alertDialog!!.dismiss()
                }
            }
        }

        alertDialog = AlertDialog.Builder(this.context).create()
        alertDialog?.setMessage("Are you sure to delete account?")
        alertDialog?.setCancelable(true)
        alertDialog?.setButton(AlertDialog.BUTTON_POSITIVE, "Ok", dialogInterface)
        alertDialog?.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel", dialogInterface)
        alertDialog?.show()
    }


    /*
    * LOGOUT MENU
     */
    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.mypage_logout, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        if(item?.itemId == R.id.logout) {
            logoutAction()
            return true
        } else {
            return super.onOptionsItemSelected(item)
        }

    }

    fun logoutAction(){
        pref.clearSharedPref(this.context)
        pref.navRoot(this.context)
    }
}




