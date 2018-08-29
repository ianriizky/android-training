package jp.co.terraresta.androidlesson.view.activity.login

import android.app.Activity
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

import jp.co.terraresta.androidlesson.R
import jp.co.terraresta.androidlesson.common.Constants
import jp.co.terraresta.androidlesson.common.Utilities
import jp.co.terraresta.androidlesson.presenter.login.LoginContract
import jp.co.terraresta.androidlesson.presenter.login.LoginPresenter
import jp.co.terraresta.androidlesson.view.view_model.login.LoginViewModel
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_sign_up.*

/**
 * Created by ooyama on 2017/05/29.
 */

class LoginActivity : AppCompatActivity(), TextWatcher, LoginContract.View{
    override fun showError(err: String) {
        tv_errorlogin.setText(err)
    }

    override fun afterTextChanged(p0: Editable?) {}
    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        when(p0?.hashCode()){
            edt_login_email.text.hashCode() -> {
                if(edt_login_email.text.toString().isEmpty()){
                    edt_login_email.setError(Constants.ERROR_INPUT_EMPTY)
                    emailValid= false
                }else if(Utilities.emailCheck(edt_login_email.text.toString())) {
                    edt_login_email.setError(Constants.ERROR_EMAIL_FORMAT)
                    emailValid= false
                }else { emailValid = true }
            }

            edt_login_password.text.hashCode() -> {
                if(edt_login_password.text.toString().isEmpty()){
                   edt_login_password.setError(Constants.ERROR_INPUT_EMPTY)
                    passwordValid = false
                }else if(Utilities.passwordCheck(edt_login_password.text.toString())) {
                    edt_login_password.setError(Constants.ERROR_PASSWORD_FORMAT)
                    passwordValid = false
                }else {passwordValid = true}
            }
        }

        isValid(emailValid!!, passwordValid!!)
    }

    var emailValid: Boolean? = false
    var passwordValid: Boolean? = false
    var email: EditText? = null
    var loginPresenter: LoginContract.Presenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        intializeComponent()
        addTextListener()
    }

    fun intializeComponent(){
        loginPresenter = LoginPresenter(this, this)
        btn_login.setEnabled(false)
        btn_login.setOnClickListener {
            setData()
            loginPresenter?.login()
        }
    }

    fun setData() {
        LoginViewModel.login_id = edt_login_email.text.toString().trim()
        LoginViewModel.password = edt_login_password.text.toString().trim()
    }

    fun addTextListener() {
        edt_login_email.addTextChangedListener(this)
        edt_login_password.addTextChangedListener(this)
    }



    fun isValid(email:Boolean, pass:Boolean){
        if(email && pass){ btn_login.setEnabled(true)
        } else  { btn_login.setEnabled(false) }
    }

}
