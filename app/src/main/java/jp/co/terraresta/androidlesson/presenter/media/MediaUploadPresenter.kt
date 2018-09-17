package jp.co.terraresta.androidlesson.presenter.media

import android.app.Fragment
import android.content.Context
import android.database.Cursor
import android.graphics.Bitmap
import android.net.Uri
import android.preference.EditTextPreference
import android.provider.MediaStore
import android.view.View
import jp.co.terraresta.androidlesson.common.Constants.REQUEST_CODE_GALLERY_ACTIVITY
import jp.co.terraresta.androidlesson.common.Constants.REQUEST_NAME_DATA
import jp.co.terraresta.androidlesson.common.Preferences
import jp.co.terraresta.androidlesson.data.handler.media.ImageUploadHandler
import jp.co.terraresta.androidlesson.data.handler.profile.ProfileEditHandler
import jp.co.terraresta.androidlesson.data.model.media.ImageUploadData
import jp.co.terraresta.androidlesson.presenter.my_page.MyPageContract
import jp.co.terraresta.androidlesson.presenter.my_page.MyPagePresenter
import jp.co.terraresta.androidlesson.presenter.profile.ProfileEditContract
import jp.co.terraresta.androidlesson.presenter.profile.ProfileEditPresenter
import jp.co.terraresta.androidlesson.view.fragment.my_page.MyPageFragment
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import android.provider.MediaStore.Images
import jp.co.terraresta.androidlesson.common.Constants.REQUEST_CODE_CAMERA_ACTIVITY


/**
 * Created by ooyama on 2017/05/29.
 */

class MediaUploadPresenter(ctx: Context, pref: Preferences):MediaUploadContract.Presenter {
    override fun takePhoto(uri:Uri, presenter: MyPagePresenter) {
        myPagePresenter = presenter
        var file: File = File(uri.toString())
        uploadMedia(file)
    }

    override fun openGale(uri: Uri, presenter: MyPagePresenter) {
        myPagePresenter = presenter
        var file: File = File(getRealPath(uri))
        uploadMedia(file)
    }

    fun uploadMedia(file: File){
        var reqFile: RequestBody = RequestBody.create(MediaType.parse("image/*"), file)
        var fileBody = MultipartBody.Part.createFormData(REQUEST_NAME_DATA, file?.name, reqFile)
        var location: String = "Profile"
        imageuploadHandler = ImageUploadHandler(mediaUploadPref.getToken(mediaUpCtx), location, fileBody, this)
        imageuploadHandler?.uploadImageAction()

    }

//override fun uploadImageProfile(uri: Uri, presenter: MyPagePresenter, source: Int) {
//        myPagePresenter = presenter
//        var file: File? = null
//        if (source == REQUEST_CODE_GALLERY_ACTIVITY) {
//            file = File(getRealPath(uri))
//            println("GALERY:  " +file)
//        } else {
////            var uriLength: Int= uri.toString().length
////            var finaluri: String = uri.toString().slice(5..uriLength-1)
//            file = File(uri.toString())
//
//            println("CAMERA: " +file)
//        }
////
////        println("FILE URI: " +file)
//    }



    override fun isUploadImageSuccess(data: ImageUploadData) {
        if(data.status == 1) {
           myPagePresenter?.updatePhotoProfile(data, "")
        } else {
            myPagePresenter?.myPageView?.showError(data.errorData?.errorMessage!!)
        }
    }

    fun getImageUri(inContext: Context, inImage: Bitmap): Uri {
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path: String? = Images.Media.insertImage(inContext.contentResolver, inImage, "Title", null)
        return Uri.parse(path)
    }

    fun getRealPath(uri: Uri): String{
        var cursor: Cursor = mediaUpCtx.contentResolver.query(uri, null, null, null, null)
        cursor.moveToFirst()
        var idx: Int = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
//        if(mediaUpCtx.contentResolver != null){
//           var cursor: Cursor = mediaUpCtx.contentResolver.query(uri, null, null, null, null)
//            if(cursor != null) {
//                cursor.moveToFirst()
//                var idx: Int = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
//                path = cursor.getString(idx)
//                cursor.close()
//            }
//        }
        return cursor.getString(idx)
    }

    var mediaUpCtx = ctx
    var mediaUploadPref = pref
    var imageuploadHandler: ImageUploadHandler? = null
    var myPagePresenter: MyPagePresenter? = null
}

