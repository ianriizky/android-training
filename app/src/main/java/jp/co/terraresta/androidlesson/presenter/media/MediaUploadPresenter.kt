package jp.co.terraresta.androidlesson.presenter.media

import android.app.Fragment
import android.content.Context
import android.database.Cursor
import android.graphics.Bitmap
import android.net.Uri
import android.preference.EditTextPreference
import android.provider.MediaStore
import android.view.View
import jp.co.terraresta.androidlesson.common.Constants.REQUEST_CODE_UPDATE_PP
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

/**
 * Created by ooyama on 2017/05/29.
 */

class MediaUploadPresenter(ctx: Context, pref: Preferences):MediaUploadContract.Presenter {
    override fun uploadImageProfile(img: Bitmap, presenter: MyPagePresenter) {
        myPagePresenter = presenter
        var tempUri: Uri = getImageUri( img)
        var file: File = File(getRealPath(tempUri))
        var reqFile: RequestBody = RequestBody.create(MediaType.parse("image/*"), file)
        var fileBody = MultipartBody.Part.createFormData(REQUEST_NAME_DATA, file.name, reqFile)

        println("PATH: " +file)

        var location: String = "Profile"
        imageuploadHandler = ImageUploadHandler(mediaUploadPref.getToken(mediaUpCtx), location, fileBody, this)
        imageuploadHandler?.uploadImageAction()
    }

    override fun isUploadImageSuccess(data: ImageUploadData) {
        if(data.status != 1) {
            myPagePresenter?.myPageView?.showError(data.errorData?.errorMessage!!)
        } else {
           myPagePresenter?.updatePhotoProfile(data, "")
        }
    }

    fun getImageUri( image: Bitmap): Uri{
        var bytes: ByteArrayOutputStream = ByteArrayOutputStream()
        image.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        var path: String = MediaStore.Images.Media.insertImage(mediaUpCtx.contentResolver, image, "Title", null)

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

