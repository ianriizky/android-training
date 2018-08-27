package jp.co.terraresta.androidlesson.data.handler.sign_up

import jp.co.terraresta.androidlesson.common.Constants.API_ACTION_NAME_SIGN_UP
import jp.co.terraresta.androidlesson.common.Constants.API_CTRL_NAME_SIGN_UP
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

open class SignUpHandler: BaseHandler {

    private var signUpParam: MutableMap<String, String>? = HashMap()
    private var signUpPresenter: SignUpPresenter? = null


    constructor(email:String, username:String, password:String, presenter:SignUpPresenter) {
        signUpParam?.put("login_id", email)
        signUpParam?.put("password", password)
        signUpParam?.put("nickname", username)
        signUpParam?.put("language", "en")
        signUpPresenter = presenter
    }

    fun getSignupService(): SignUpContract.Services? {
     return RestClient()?.create(SignUpContract.Services::class.java)
    }

    fun getLoginService() {

    }

    fun signUpAction() {
        try {
            getSignupService()?.getSignupData(API_CTRL_NAME_SIGN_UP, API_ACTION_NAME_SIGN_UP, signUpParam!!)?.enqueue(object : Callback<SignUpData> {
                override fun onFailure(call: Call<SignUpData>?, t: Throwable?) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onResponse(call: Call<SignUpData>?, response: Response<SignUpData>?) {
                    if(response != null) {
//                        println("RESS: " +response.body().toString())
                        var ress: SignUpData = response.body()!!
                        println("RESS UID: " +ress.userId)
                        println("RESS TOKEN: " +ress.accessToken)
                        println("RESS ERROR: " +ress.errorData?.errorMessage)
                        println("RESS STAT: " +ress.status)

                        signUpPresenter?.getError(ress.errorData?.errorMessage.toString())
                        signUpPresenter?.setRessApi(ress.userId, ress.accessToken.toString())

                    } else {
                        println("GAGAL")
                    }
                }

            })
        } catch (e: Exception) {
            println("CATCH ERROR: " +e.toString())
        }
    }


}

