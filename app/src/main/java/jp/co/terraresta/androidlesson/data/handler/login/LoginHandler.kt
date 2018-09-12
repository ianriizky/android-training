package jp.co.terraresta.androidlesson.data.handler.login

import jp.co.terraresta.androidlesson.common.Constants.API_ACTION_NAME_LOGIN
import jp.co.terraresta.androidlesson.common.Constants.API_CTRL_NAME_LOGIN
import jp.co.terraresta.androidlesson.data.handler.common.BaseHandler
import jp.co.terraresta.androidlesson.data.model.login.LoginData
import jp.co.terraresta.androidlesson.presenter.login.LoginContract
import jp.co.terraresta.androidlesson.presenter.login.LoginPresenter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by ooyama on 2017/05/24.
 */

open class LoginHandler: BaseHandler {

    var loginParams: MutableMap<String, String>? = HashMap()
    var loginPresenter: LoginPresenter? = null

    constructor(email: String, pass:String, presenter: LoginPresenter) {
        loginParams?.put("login_id", email)
        loginParams?.put("password", pass)
        loginParams?.put("language", "en")
        loginPresenter = presenter
    }

    fun getLoginService():LoginContract.Services? {
        return RestClient()?.create(LoginContract.Services::class.java)
    }

    fun loginAction(){
        try {
            getLoginService()?.getLoginData(API_CTRL_NAME_LOGIN, API_ACTION_NAME_LOGIN, loginParams!!)?.enqueue(object: Callback<LoginData>{
                override fun onFailure(call: Call<LoginData>?, t: Throwable?) {
                }

                override fun onResponse(call: Call<LoginData>?, response: Response<LoginData>?) {
                    var ress:LoginData? = response?.body()
                    loginPresenter?.isLoginSuccess(ress!!)
                }

            });
        } catch (e: Exception) {

        }
    }
}
