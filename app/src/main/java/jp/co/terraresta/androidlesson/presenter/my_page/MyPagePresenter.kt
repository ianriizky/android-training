package jp.co.terraresta.androidlesson.presenter.my_page

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import jp.co.terraresta.androidlesson.common.Preferences
import jp.co.terraresta.androidlesson.data.handler.my_page.MyPageHandler
import jp.co.terraresta.androidlesson.data.model.media.ImageUploadData
import jp.co.terraresta.androidlesson.data.model.profile.ProfileDisplayData
import jp.co.terraresta.androidlesson.presenter.media.MediaUploadContract
import jp.co.terraresta.androidlesson.presenter.media.MediaUploadPresenter
import jp.co.terraresta.androidlesson.presenter.profile.ProfileEditPresenter
import jp.co.terraresta.androidlesson.view.fragment.my_page.MyPageFragment
import okhttp3.MediaType
import okhttp3.RequestBody
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.File

class MyPagePresenter(ctx: Context, view: MyPageContract.View): MyPageContract.Presenter{
    override fun takePhoto(uri: Uri?) {
        println("take photo: " +uri)
        mediaUpPresenter = MediaUploadPresenter(myPageContext!!, myPagePreferences)
        mediaUpPresenter?.takePhoto(uri!!, this)
    }


    override fun openGallery(uri: Uri?) {
        mediaUpPresenter = MediaUploadPresenter(myPageContext!!, myPagePreferences)
        mediaUpPresenter?.openGale(uri!!, this)
    }


    override fun updatePhotoProfile(data: ImageUploadData, action: String) {
        if(action == "delete_pp") {
            profileDisplayData?.imageId = data.imageId
            profileEditPresenter = ProfileEditPresenter(myPageContext!!, myPagePreferences)
            profileEditPresenter?.editProfilePhotoProfile(this, profileDisplayData!!)
        } else {

            fetchUserData()
        }

    }

    override fun isSucessFetchUserData(ressData: ProfileDisplayData) {
        if(ressData.status == 1) {
            println("NICKNAME: " +ressData.nickname)
            profileDisplayData = ressData
            myPageView.setFetchRess(ressData)
        } else {
            myPageView.showError(ressData.errorData?.errorMessage!!)
        }
    }

    var myPagePreferences: Preferences = Preferences()
    var myPageHandler: MyPageHandler? = null
    var myPageView: MyPageContract.View = view
    var myPageContext: Context? = ctx
    var mediaUpPresenter: MediaUploadContract.Presenter? = null
    var profileDisplayData: ProfileDisplayData? = null
    var profileEditPresenter: ProfileEditPresenter? = null

    override fun fetchUserData() {
        myPageHandler = MyPageHandler(myPagePreferences.getToken(myPageContext!!),myPagePreferences.getUserid(myPageContext!!), this )
        myPageHandler?.fetchUserDataActiion()
    }
}