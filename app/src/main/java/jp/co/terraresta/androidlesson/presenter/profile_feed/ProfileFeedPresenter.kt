package jp.co.terraresta.androidlesson.presenter.profile_feed

import android.content.Context
import android.view.View
import jp.co.terraresta.androidlesson.common.Preferences
import jp.co.terraresta.androidlesson.data.handler.profile_feed.ProfileFeedHandler
import jp.co.terraresta.androidlesson.data.model.profile_feed.ProfileFeedData
import java.util.*

/**
 * Created by ooyama on 2017/05/29.
 */

class ProfileFeedPresenter(ctx:Context, view:ProfileFeedContract.View): ProfileFeedContract.Presenter {
    override fun fetchProfileNext() {
       nextData = true
       profileFeedHandler?.fetchFeedAction(pref.getToken(profileFeedCtx), lastLogin)
    }

    override fun isSuccessFetchFeed(data: ProfileFeedData) {
        if(data.status == 1){
            if(nextData){
                profileFeedView.setNextRess(data)
            } else {
                profileFeedView.setRess(data)
            }
                lastLogin = data.lastLoginTime!!
        } else {
            profileFeedView.showError(data.errorData?.errorMessage!!)
        }
    }

    var profileFeedCtx = ctx
    var profileFeedView = view
    var pref: Preferences = Preferences()
    var profileFeedHandler:ProfileFeedHandler? = null
    var lastLogin: String = ""
    var nextData: Boolean = false

    override fun fetchProfileFeed() {
        profileFeedHandler  = ProfileFeedHandler( this)
        profileFeedHandler?.fetchFeedAction(pref.getToken(profileFeedCtx), lastLogin)
    }

}
