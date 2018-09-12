package jp.co.terraresta.androidlesson.common

import android.app.Activity
import android.app.AlertDialog
import android.app.Fragment
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.preference.PreferenceManager
import jp.co.terraresta.androidlesson.MainActivity
import jp.co.terraresta.androidlesson.R
import jp.co.terraresta.androidlesson.presenter.sign_up.SignUpPresenter
import jp.co.terraresta.androidlesson.view.activity.common.MainTabActivity

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

    fun clearSharedPref(ctx: Context) {
        var edt:SharedPreferences.Editor = getSharedPref(ctx).edit()
        edt.clear()
        edt.commit()
    }

    fun setIdentifier(ctx: Context, token: String, userid: Int) {
        var edt:SharedPreferences.Editor = getSharedPref(ctx).edit()
        edt.putString(Constants?.REQUEST_NAME_ACCESS_TOKEN, token)
        edt.putInt(Constants?.REQUEST_NAME_USER_ID, userid)
        edt.commit()
    }
    fun getToken(ctx: Context): String {
        return getSharedPref(ctx)?.getString(Constants?.REQUEST_NAME_ACCESS_TOKEN, "")
    }
    fun getUserid(ctx: Context): Int{
       return getSharedPref(ctx)?.getInt(Constants?.REQUEST_NAME_USER_ID, 0)
    }

    fun navHome(ctx: Context){
        var homeintent: Intent = Intent(ctx, MainTabActivity::class.java)
        homeintent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK )
        ctx.startActivity(homeintent)
    }

    fun navRoot(ctx: Context){
        var rootintent: Intent = Intent(ctx, MainActivity::class.java)
        rootintent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK )
        ctx.startActivity(rootintent)
    }
}
