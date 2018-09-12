package jp.co.terraresta.androidlesson.view.fragment.my_page

import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.support.design.widget.BottomSheetBehavior
import android.support.v4.app.Fragment
import android.view.*
import android.webkit.WebView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import de.hdodenhof.circleimageview.CircleImageView
import jp.co.terraresta.androidlesson.MainActivity

import jp.co.terraresta.androidlesson.R
import jp.co.terraresta.androidlesson.WebViewActivity
import jp.co.terraresta.androidlesson.common.Constants.REQUEST_CODE_CAMERA_ACTIVITY
import jp.co.terraresta.androidlesson.common.Constants.WEB_INFO_PAGE_TERMS_SERVICE
import jp.co.terraresta.androidlesson.common.Preferences
import jp.co.terraresta.androidlesson.data.model.profile.ProfileDisplayData
import jp.co.terraresta.androidlesson.presenter.account.DeleteAccountContract
import jp.co.terraresta.androidlesson.presenter.account.DeleteAccountPresenter
import jp.co.terraresta.androidlesson.presenter.my_page.MyPageContract
import jp.co.terraresta.androidlesson.presenter.my_page.MyPagePresenter
import android.R.attr.data
import android.app.*
import android.app.Activity.RESULT_OK
import android.net.Uri
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v4.app.NotificationCompat.getExtras
import android.support.v4.content.FileProvider
import android.widget.Button
import com.squareup.picasso.Picasso
import jp.co.terraresta.androidlesson.common.Constants.REQUEST_CODE_DELETE_PP
import jp.co.terraresta.androidlesson.common.Constants.REQUEST_CODE_GALLERY_ACTIVITY
import jp.co.terraresta.androidlesson.common.Constants.REQUEST_CODE_UPDATE_PP
import jp.co.terraresta.androidlesson.data.model.media.ImageUploadData
import jp.co.terraresta.androidlesson.view.activity.profile.ProfileEditActivity


/**
 * Created by ooyama on 2017/05/29.
 */

class MyPageFragment :  Fragment(), MyPageContract.View{
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

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        myPageView= inflater?.inflate(R.layout.fragment_my_page, container, false)
        initView()
        return myPageView
    }

    fun initView(){
        myPagePresenter = MyPagePresenter(this.context, this)
        myPagePresenter?.fetchUserData()
        editProfile = myPageView?.findViewById(R.id.btn_edit_profile) as Button
        deleteProfile = myPageView?.findViewById(R.id.ll_delete_account) as LinearLayout
        termsServices = myPageView?.findViewById(R.id.ll_terms) as LinearLayout

        loader()
        deleteProfile?.setOnClickListener {
            dialogMessageDel()
        }
        termsServices?.setOnClickListener {
            var webview: Intent = Intent(this.context, WebViewActivity::class.java)
            this.context.startActivity(webview)
        }
        editProfile?.setOnClickListener {
            var intent: Intent = Intent(this.context, ProfileEditActivity::class.java)
            startActivity(intent)
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

        //default photo profile

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
            takePhoto()
            photoProfileSheetBehavior?.state = BottomSheetBehavior.STATE_COLLAPSED
        }

        // take photo from gallery
        sheetGalery?.setOnClickListener {
            openGallery()
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

    fun openGallery(){
        var intent: Intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, REQUEST_CODE_GALLERY_ACTIVITY)
    }

    fun takePhoto() {
        var intent: Intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, REQUEST_CODE_CAMERA_ACTIVITY)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(resultCode == RESULT_OK){
            loader()
           if(requestCode == REQUEST_CODE_CAMERA_ACTIVITY){
               var photo: Bitmap = data?.extras?.get("data") as Bitmap
               myPagePresenter?.takePhoto(photo)
           } else if(requestCode == REQUEST_CODE_GALLERY_ACTIVITY) {
               var uri: Uri = data!!.data
                var photoGallery: Bitmap = MediaStore.Images.Media.getBitmap(this.context.contentResolver, uri)
               myPagePresenter?.openGallery(photoGallery)
           }
        } else {
            return
        }
        super.onActivityResult(requestCode, resultCode, data)
    }




    fun loader() {
        loading = ProgressDialog(this.context)
        loading?.setMessage("Please Wait...")
        loading?.setCancelable(false)
        loading?.show()
    }

    fun alertError(message: String) {
        var alertError = AlertDialog.Builder(this.context).create()
        alertError?.setMessage(message)
        alertError?.setButton(AlertDialog.BUTTON_NEUTRAL, "Ok", DialogInterface.OnClickListener { dialogInterface, i ->
            alertError.dismiss()
        })
        alertError.show()
    }

    fun dialogMessageDel() {
        var dialogInterface: DialogInterface.OnClickListener = DialogInterface.OnClickListener { dialogInterface, i ->
            when(i){
                DialogInterface.BUTTON_POSITIVE -> {
//
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
        inflater?.inflate(R.menu.logout, menu)
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




