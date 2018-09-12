package jp.co.terraresta.androidlesson.presenter.login

import android.app.Activity
import android.content.Context
import jp.co.terraresta.androidlesson.common.Preferences
import jp.co.terraresta.androidlesson.common.Utilities
import jp.co.terraresta.androidlesson.data.handler.login.LoginHandler
import jp.co.terraresta.androidlesson.data.model.login.LoginData
import jp.co.terraresta.androidlesson.view.activity.login.LoginActivity
import jp.co.terraresta.androidlesson.view.view_model.login.LoginViewModel.login_id
import jp.co.terraresta.androidlesson.view.view_model.login.LoginViewModel.password

/**
 * Created by ooyama on 2017/05/29.
 */

class LoginPresenter(view: LoginContract.View, ctx: Context): LoginContract.Presenter{
    override fun isLoginSuccess(response: LoginData) {
       if(response?.errorData?.errorMessage != null){
            loginView.showError(response?.errorData?.errorMessage!!)
       } else {
           loginView.showError(" ")
           loginPref  = Preferences()
           loginPref?.setIdentifier(loginCtx, response.accessToken!!, response.userId)
           loginPref?.navHome(loginCtx)
       }
    }

    var loginView = view
    var loginCtx = ctx
    var loginHandler: LoginHandler? = null
    var loginPref: Preferences? = null

    override fun login() {
        loginHandler = LoginHandler(login_id!!, password!!, this)
        loginHandler?.loginAction()
    }

}
