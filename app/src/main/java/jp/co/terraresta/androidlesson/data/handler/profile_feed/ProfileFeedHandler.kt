package jp.co.terraresta.androidlesson.data.handler.profile_feed

import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import jp.co.terraresta.androidlesson.common.Constants.API_ACTION_NAME_PROFILE_FEED
import jp.co.terraresta.androidlesson.common.Constants.API_CTRL_NAME_PROFILE_FEED
import jp.co.terraresta.androidlesson.common.Constants.REQUEST_NAME_ACCESS_TOKEN
import jp.co.terraresta.androidlesson.common.Constants.REQUEST_NAME_LAST_LOGIN_TIME
import jp.co.terraresta.androidlesson.data.handler.common.BaseHandler
import jp.co.terraresta.androidlesson.data.model.profile_feed.ProfileFeedData
import jp.co.terraresta.androidlesson.presenter.profile_feed.ProfileFeedContract
import jp.co.terraresta.androidlesson.presenter.profile_feed.ProfileFeedPresenter

/**
 * Created by ooyama on 2017/05/26.
 */

class ProfileFeedHandler: BaseHandler {

    var params: MutableMap<String, Any>? = HashMap()
    var profileFeedPresenter: ProfileFeedPresenter? = null

    constructor(token: String, lastlogin: String, presenter:ProfileFeedPresenter){
        params?.put(REQUEST_NAME_ACCESS_TOKEN, token)
        params?.put(REQUEST_NAME_LAST_LOGIN_TIME, lastlogin)

        profileFeedPresenter = presenter

    }

    fun getProfileFeedServices(): ProfileFeedContract.Services? {
        return RestClient()?.create(ProfileFeedContract.Services::class.java)
    }

    fun fetchFeedAction(){
        try {
            getProfileFeedServices()?.getFeedData(API_CTRL_NAME_PROFILE_FEED, API_ACTION_NAME_PROFILE_FEED, params!!)
                    ?.subscribeOn(Schedulers.newThread())
                    ?.observeOn(AndroidSchedulers.mainThread())
                    ?.subscribe(object : Observer<ProfileFeedData>{
                        override fun onComplete() {
                        }

                        override fun onSubscribe(d: Disposable) {
                        }

                        override fun onNext(t: ProfileFeedData) {
                            profileFeedPresenter?.isSuccessFetchFeed(t)
                        }

                        override fun onError(e: Throwable) {
                            e.stackTrace
                            println("error retrofit: " +e)
                        }

                    })

        } catch (e: Exception){
            println("error catch: " +e)

        }
    }
}
