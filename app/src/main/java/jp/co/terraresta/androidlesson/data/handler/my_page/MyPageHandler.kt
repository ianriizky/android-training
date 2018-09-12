package jp.co.terraresta.androidlesson.data.handler.my_page

import android.content.Context
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import jp.co.terraresta.androidlesson.common.Constants.API_ACTION_NAME_PROFILE_DISPLAY
import jp.co.terraresta.androidlesson.common.Constants.API_CTRL_NAME_PROFILE
import jp.co.terraresta.androidlesson.common.Constants.REQUEST_NAME_ACCESS_TOKEN
import jp.co.terraresta.androidlesson.common.Constants.REQUEST_NAME_USER_ID
import jp.co.terraresta.androidlesson.data.handler.common.BaseHandler
import jp.co.terraresta.androidlesson.data.model.login.LoginData
import jp.co.terraresta.androidlesson.data.model.profile.ProfileDisplayData
import jp.co.terraresta.androidlesson.presenter.my_page.MyPageContract
import jp.co.terraresta.androidlesson.presenter.my_page.MyPagePresenter



class MyPageHandler: BaseHandler {

    var myPageParams: MutableMap<String, Any> = HashMap()
    var myPagePresenter: MyPagePresenter? = null
    var ctx: Context? = null

    constructor(token: String, id: Int, presenter: MyPagePresenter){
        println("TOKEN: " +token)
        myPageParams.put(REQUEST_NAME_ACCESS_TOKEN, token)
        myPageParams.put(REQUEST_NAME_USER_ID, id)
        myPagePresenter = presenter
        this.ctx = ctx
    }

    fun getMypageServices(): MyPageContract.Services?{
       return RestClient()?.create(MyPageContract.Services::class.java)
    }

    fun fetchUserDataActiion(){
       try {
           getMypageServices()?.getUserData(API_CTRL_NAME_PROFILE, API_ACTION_NAME_PROFILE_DISPLAY, myPageParams)
                   ?.subscribeOn(Schedulers.newThread())
                   ?.observeOn(AndroidSchedulers.mainThread())
                   ?.subscribe(object : Observer<ProfileDisplayData> {
                       override fun onComplete() {
                       }

                       override fun onSubscribe(d: Disposable) {
                       }

                       override fun onNext(t: ProfileDisplayData) {
                           myPagePresenter?.isSucessFetchUserData(t)
                       }

                       override fun onError(e: Throwable) {
                           println("ERROR RETROFIT: " +e)
                       }

                   })
       } catch (e: Exception) {
            println("ERROR: " +e)
       }
    }
}