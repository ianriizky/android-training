package jp.co.terraresta.androidlesson.presenter.profile

import android.content.Context
import jp.co.terraresta.androidlesson.common.Preferences
import jp.co.terraresta.androidlesson.data.handler.profile.ProfileEditHandler
import jp.co.terraresta.androidlesson.data.model.common.BaseResultData
import jp.co.terraresta.androidlesson.data.model.profile.ProfileDisplayData
import jp.co.terraresta.androidlesson.presenter.my_page.MyPagePresenter

/**
 * Created by ooyama on 2017/05/29.
 */

class ProfileEditPresenter(ctx: Context, pref: Preferences): ProfileEditContract.Presenter {

    var profileEditCtx = ctx
    var profileEditPref = pref
    var profileEditHandler: ProfileEditHandler? = null
    var myPagePresenter: MyPagePresenter? = null


    override fun isSuccessEditProfile(data: BaseResultData) {
        if(data.status == 1) {
            myPagePresenter?.fetchUserData()
        } else {
            myPagePresenter?.myPageView?.showError(data.errorData?.errorMessage!!)
        }
    }

    override fun editProfile() {
    }

    override fun editProfilePhotoProfile(presenter: MyPagePresenter, data: ProfileDisplayData) {
        myPagePresenter = presenter
        println("EDIT PROFILE IMG ID: " +data.imageId)
        profileEditHandler = ProfileEditHandler(profileEditPref.getToken(profileEditCtx), data,  this)
        profileEditHandler?.editProfileAction()
    }




}
