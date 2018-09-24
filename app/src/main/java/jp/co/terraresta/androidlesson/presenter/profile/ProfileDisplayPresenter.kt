package jp.co.terraresta.androidlesson.presenter.profile

import android.content.Context
import jp.co.terraresta.androidlesson.common.Preferences
import jp.co.terraresta.androidlesson.data.handler.profile.ProfileDisplayHandler
import jp.co.terraresta.androidlesson.data.model.profile.ProfileDisplayData

/**
 * Created by ooyama on 2017/05/29.
 */

class ProfileDisplayPresenter(ctx: Context, view:ProfileDisplayContract.View): ProfileDisplayContract.Presenter {
    var profileDisplayView: ProfileDisplayContract.View = view
    var profileDisplayCtx: Context = ctx
    var profileDisplayHandler: ProfileDisplayHandler? = null
    var pref: Preferences = Preferences()

    override fun isSuccessFetchData(data: ProfileDisplayData) {
        if(data.status == 1){
           profileDisplayView.setRess(data)
        } else {
            profileDisplayView.showError(data.errorData?.errorMessage!!)
        }
    }

    override fun fetchUserData(uid: Int) {
        profileDisplayHandler = ProfileDisplayHandler(pref.getToken(profileDisplayCtx),uid, this)
        profileDisplayHandler?.fetchUserDataAction()
    }

}
