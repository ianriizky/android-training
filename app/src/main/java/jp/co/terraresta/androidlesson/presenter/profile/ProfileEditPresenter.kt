package jp.co.terraresta.androidlesson.presenter.profile

import android.content.Context
import jp.co.terraresta.androidlesson.common.Constants.API_ACTION_NAME_IMAGE_UPLOAD
import jp.co.terraresta.androidlesson.common.Constants.API_ACTION_NAME_PROFILE_EDIT
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
    var profileEditView: ProfileEditContract.View? = null


    override fun isSuccessEditProfile(data: BaseResultData, action: String) {
        if(data.status == 1) {
            if(action == API_ACTION_NAME_IMAGE_UPLOAD ){
                myPagePresenter?.fetchUserData()
            } else {
                profileEditView?.setResponse(data)
                myPagePresenter?.fetchUserData()
            }
        } else {
            if(action == API_ACTION_NAME_IMAGE_UPLOAD){
                myPagePresenter?.myPageView?.showError(data.errorData?.errorMessage!!)
            } else {
                profileEditView?.showError(data.errorData?.errorMessage!!)
            }
        }
    }

    override fun editProfile() {
    }

    override fun editProfilePhotoProfile(presenter: MyPagePresenter, data: ProfileDisplayData) {
        myPagePresenter = presenter
        profileEditHandler = ProfileEditHandler(profileEditPref.getToken(profileEditCtx), data,  this, API_ACTION_NAME_IMAGE_UPLOAD)
        profileEditHandler?.editProfileAction()
    }

    override fun editProfile(view: ProfileEditContract.View, data: ProfileDisplayData) {
        profileEditView = view
        profileEditHandler = ProfileEditHandler(profileEditPref.getToken(profileEditCtx), data,  this, API_ACTION_NAME_PROFILE_EDIT)
        profileEditHandler?.editProfileAction()
    }




}
