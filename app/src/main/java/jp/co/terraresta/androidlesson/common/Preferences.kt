package jp.co.terraresta.androidlesson.common

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import jp.co.terraresta.androidlesson.presenter.sign_up.SignUpPresenter

/**
 * Created by ooyama on 2017/05/24.
 */

open class Preferences {

//    var asd: Context? = null
//
    constructor() {

    }
//
    fun getSharedPref(ctx: Context): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(ctx)
    }

    fun setToken(ctx: Context, token: String) {
        var edt:SharedPreferences.Editor = getSharedPref(ctx).edit()
        edt.putString(Constants?.REQUEST_NAME_ACCESS_TOKEN, token)
        edt.commit()
    }

    fun getToken(ctx: Context): String {
        return getSharedPref(ctx)?.getString(Constants?.REQUEST_NAME_ACCESS_TOKEN, "")
    }
}
