package jp.co.terraresta.androidlesson.presenter.sign_up

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import jp.co.terraresta.androidlesson.MainActivity
import jp.co.terraresta.androidlesson.common.Preferences
import jp.co.terraresta.androidlesson.common.Utilities
import jp.co.terraresta.androidlesson.data.handler.sign_up.SignUpHandler
import jp.co.terraresta.androidlesson.view.activity.common.MainTabActivity
import jp.co.terraresta.androidlesson.view.view_model.sign_up.SignUpViewModel.login_id
import jp.co.terraresta.androidlesson.view.view_model.sign_up.SignUpViewModel.nickname
import jp.co.terraresta.androidlesson.view.view_model.sign_up.SignUpViewModel.password

/**
 * Created by ooyama on 2017/05/29.
 */

open class SignUpPresenter: SignUpContract.Presenter {
    override fun signUp() {
       signUpHandler = SignUpHandler(login_id!!, nickname!!, password!!, this)
        signUpHandler?.signUpAction()
    }

    var signUpView: SignUpContract.View? = null
    var signUpHandler: SignUpHandler? = null
    var signUpCtx: Context? = null
    var signUpAct: AppCompatActivity? = null
    var signUpPref: Preferences? = null
    var accessToken: String? = null; var userId: Int? = null

    constructor(view: SignUpContract.View, ctx: Context, act: AppCompatActivity) {
        signUpView = view
        signUpCtx = ctx
        signUpAct = act
    }

    fun getError (err: String) {
        if(err != "null") {
            signUpView?.showError("*" +err)
        } else {
            signUpView?.showError(" ")
            signUpPref = Preferences()
            signUpPref?.navHome(signUpCtx!!)
        }

    }


    fun setRessApi(userid:Int, token:String) {
        signUpPref = Preferences()
        if(userid != 0 && token != "null") {
            signUpPref?.setIdentifier(signUpCtx!!, token, userid)
            println("GET TOKEN PREF: " +signUpPref?.getToken(signUpCtx!!))
        }

    }
}
