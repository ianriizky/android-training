package jp.co.terraresta.androidlesson.view.activity.sign_up

import android.content.Context
import android.os.Bundle
import android.preference.Preference
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView

import jp.co.terraresta.androidlesson.R
import jp.co.terraresta.androidlesson.common.Constants
import jp.co.terraresta.androidlesson.common.Utilities
import jp.co.terraresta.androidlesson.presenter.sign_up.SignUpContract
import jp.co.terraresta.androidlesson.presenter.sign_up.SignUpPresenter
import jp.co.terraresta.androidlesson.view.view_model.sign_up.SignUpViewModel
import okhttp3.internal.Util

/**
 * Created by ooyama on 2017/05/29.
 */

class SignUpActivity : AppCompatActivity(), SignUpContract.View, TextWatcher {
    override fun showError(errorMassage: String) {
        errorView?.setText(errorMassage)
    }

    override fun afterTextChanged(p0: Editable?) {}
    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        when(p0?.hashCode()) {
            username?.text?.hashCode() -> {
                if(username?.text.toString().isEmpty()) {
                    username?.setError(Constants.ERROR_INPUT_EMPTY)
                    usernameValid = false
                } else if(Utilities.usernameCheck(username?.text.toString().trim())) {
                    username?.setError(Constants.ERROR_USERNAME_FORMAT)
                    usernameValid = false
                } else { usernameValid = true }
            }
            email?.text?.hashCode() -> {
                if(email?.text.toString().isEmpty()) {
                    email?.setError(Constants.ERROR_INPUT_EMPTY)
                    emailValid = false
                } else if (Utilities.emailCheck(email?.text.toString())) {
                    email?.setError(Constants.ERROR_EMAIL_FORMAT)
                    emailValid = false
                } else { emailValid = true }
            }
            password?.text?.hashCode() -> {
                if(password?.text.toString().isEmpty()) {
                    password?.setError(Constants.ERROR_INPUT_EMPTY)
                    passwordValid = false
                } else if(Utilities.passwordCheck(password?.text.toString())) {
                    password?.setError(Constants.ERROR_PASSWORD_FORMAT)
                    passwordValid = false
                } else { passwordValid = true }
            }
        }
        isValid(emailValid!!, usernameValid!!, passwordValid!!)
    }

    // INITIALIZE ALL VARIABEL
    var usernameValid: Boolean? = false
    var emailValid: Boolean? = false
    var passwordValid: Boolean? = false

    var register: Button? = null
    var username: EditText? = null
    var email: EditText? = null
    var password: EditText? = null
    var signUpPresenter: SignUpPresenter? = null
    var signUpPref: Preference? = null
    var errorView: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        setView()
        actionBar?.setDisplayHomeAsUpEnabled(true)
    }

    fun setView() {
        signUpPresenter = SignUpPresenter(this, this, this)
        register  = findViewById(R.id.btn_signup) as Button
        email = findViewById(R.id.edt_email) as EditText
        password = findViewById(R.id.edt_password) as EditText
        username = findViewById(R.id.edt_username) as EditText
        errorView = findViewById(R.id.tv_errorcode) as TextView

        email?.addTextChangedListener(this)
        password?.addTextChangedListener(this)
        username?.addTextChangedListener(this)
        register?.setEnabled(false)
        register?.setOnClickListener {
            setData()
            signUpPresenter?.signUp()
        }
    }


    fun isValid(email: Boolean, username: Boolean, pass: Boolean) {
        if(email && username && pass){
            register?.setEnabled(true)
        } else {
            register?.setEnabled(false)
        }
    }

    fun setData() {
        SignUpViewModel.login_id = email?.text.toString();
        SignUpViewModel.nickname = username?.text.toString();
        SignUpViewModel.password = password?.text.toString();
        }


    }










