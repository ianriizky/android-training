package jp.co.terraresta.androidlesson.data.handler.sign_up

import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import jp.co.terraresta.androidlesson.common.Constants.API_ACTION_NAME_SIGN_UP
import jp.co.terraresta.androidlesson.common.Constants.API_CTRL_NAME_SIGN_UP
import jp.co.terraresta.androidlesson.common.Constants.REQUEST_NAME_LANGUAGE
import jp.co.terraresta.androidlesson.common.Constants.REQUEST_NAME_LOGIN_ID
import jp.co.terraresta.androidlesson.common.Constants.REQUEST_NAME_NICKNAME
import jp.co.terraresta.androidlesson.common.Constants.REQUEST_NAME_PASSWORD
import jp.co.terraresta.androidlesson.data.handler.common.BaseHandler
import jp.co.terraresta.androidlesson.data.model.sign_up.SignUpData
import jp.co.terraresta.androidlesson.presenter.login.LoginContract
import jp.co.terraresta.androidlesson.presenter.sign_up.SignUpContract
import jp.co.terraresta.androidlesson.presenter.sign_up.SignUpPresenter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by ooyama on 2017/05/26.
 */

open class SignUpHandler(email: String, username: String, password: String, presenter: SignUpPresenter) : BaseHandler() {

    private var signUpParam: MutableMap<String, Any>? = HashMap()
    private var signUpPresenter: SignUpPresenter? = presenter


    init {
        signUpParam?.put(REQUEST_NAME_LANGUAGE, "en")
        signUpParam?.put(REQUEST_NAME_LOGIN_ID, email)
        signUpParam?.put(REQUEST_NAME_PASSWORD, password)
        signUpParam?.put(REQUEST_NAME_NICKNAME, username)
    }

    private fun getSignupService(): SignUpContract.Services? {
     return RestClient()?.create(SignUpContract.Services::class.java)
    }


    fun signUpAction() {
        try {
            getSignupService()?.getSignupData("SignUpCtrl", "SignUp", signUpParam!!)
                    ?.subscribeOn(Schedulers.newThread())
                    ?.observeOn(AndroidSchedulers.mainThread())
                    ?.subscribe(object : Observer<SignUpData>{
                        override fun onComplete() {
                        }

                        override fun onSubscribe(d: Disposable) {
                        }

                        override fun onNext(t: SignUpData) {
                        signUpPresenter?.getError(t.errorData?.errorMessage.toString())
                        signUpPresenter?.setRessApi(t.userId, t.accessToken.toString())
                        }

                        override fun onError(e: Throwable) {
                            e.stackTrace
                            println("error retrofit - signuphandler: $e")
                        }

                    })
//                    ?.enqueue(object : Callback<SignUpData> {
//                override fun onFailure(call: Call<SignUpData>?, t: Throwable?) {
//                }
//
//                override fun onResponse(call: Call<SignUpData>?, response: Response<SignUpData>?) {
//                    if(response != null) {
////                        println("RESS: " +response.body().toString())
//                        var ress: SignUpData = response.body()!!
//                        println("RESS UID: " +ress.userId)
//                        println("RESS TOKEN: " +ress.accessToken)
//                        println("RESS ERROR: " +ress.errorData?.errorMessage)
//                        println("RESS STAT: " +ress.status)
//
//                        signUpPresenter?.getError(ress.errorData?.errorMessage.toString())
//                        signUpPresenter?.setRessApi(ress.userId, ress.accessToken.toString())
//
//                    } else {
//                        println("GAGAL")
//                    }
//                }
//
//            })
        } catch (e: Exception) {
            println("error kotlin - signup handler: " +e.toString())
        }
    }


}

