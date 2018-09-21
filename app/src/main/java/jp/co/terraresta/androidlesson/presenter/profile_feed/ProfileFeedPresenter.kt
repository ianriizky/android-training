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
    override fun isSuccessFetchFeed(data: ProfileFeedData) {
        if(data.status == 1){
           profileFeedView.setRess(data)
        } else {
            profileFeedView.showError(data.errorData?.errorMessage!!)
        }
    }

    var profileFeedCtx = ctx
    var profileFeedView = view
    var pref: Preferences = Preferences()
    var profileFeedHandler:ProfileFeedHandler? = null

    override fun fetchProfileFeed() {
        var date = Calendar.getInstance().time
        var local = date.toLocaleString()
        println("DATE: " +local)
        profileFeedHandler  = ProfileFeedHandler(pref.getToken(profileFeedCtx), local, this)
        profileFeedHandler?.fetchFeedAction()
    }

}
