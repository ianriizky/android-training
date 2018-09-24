package jp.co.terraresta.androidlesson.data.handler.profile

import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import jp.co.terraresta.androidlesson.common.Constants.API_ACTION_NAME_PROFILE_DISPLAY
import jp.co.terraresta.androidlesson.common.Constants.API_CTRL_NAME_PROFILE
import jp.co.terraresta.androidlesson.common.Constants.REQUEST_NAME_ACCESS_TOKEN
import jp.co.terraresta.androidlesson.common.Constants.REQUEST_NAME_USER_ID
import jp.co.terraresta.androidlesson.data.handler.common.BaseHandler
import jp.co.terraresta.androidlesson.data.model.profile.ProfileDisplayData
import jp.co.terraresta.androidlesson.presenter.profile.ProfileDisplayContract
import jp.co.terraresta.androidlesson.presenter.profile.ProfileDisplayPresenter

/**
 * Created by ooyama on 2017/05/26.
 */

class ProfileDisplayHandler: BaseHandler{

    var params: HashMap<String, Any> = HashMap()
    var presenter: ProfileDisplayPresenter? = null
    constructor(token: String, uid: Int, presenter: ProfileDisplayPresenter){
        params.put(REQUEST_NAME_ACCESS_TOKEN, token)
        params.put(REQUEST_NAME_USER_ID, uid)

        this.presenter = presenter
    }

    fun getUserService():ProfileDisplayContract.Services? {
       return RestClient()?.create(ProfileDisplayContract.Services::class.java)
    }

    fun fetchUserDataAction(){
        try{
           getUserService()?.getUserData(API_CTRL_NAME_PROFILE, API_ACTION_NAME_PROFILE_DISPLAY, params!!)
                   ?.subscribeOn(Schedulers.newThread())
                   ?.observeOn(AndroidSchedulers.mainThread())
                   ?.subscribe(object : Observer<ProfileDisplayData>{
                       override fun onComplete() {
                       }

                       override fun onSubscribe(d: Disposable) {
                       }

                       override fun onNext(t: ProfileDisplayData) {
                           presenter?.isSuccessFetchData(t)
                       }

                       override fun onError(e: Throwable) {
                           e.stackTrace
                           println("error retrofit: " +e)
                       }

                   })
        } catch (e: Exception){
            println("error java: " +e)

        }
    }
}
