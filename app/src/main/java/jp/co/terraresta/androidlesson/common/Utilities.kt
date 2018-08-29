package jp.co.terraresta.androidlesson.common

import android.app.Activity
import android.content.Context
import android.content.Intent
import jp.co.terraresta.androidlesson.BuildConfig
import jp.co.terraresta.androidlesson.view.activity.common.MainTabActivity

/**
 * Created by ooyama on 2017/05/24.
 */

object Utilities {

    /**
     * API_URL取得
     * Acquisition of API_URL
     *
     * @return url
     */
    //return String.format("http://%s/ms4/mb/", Constants.SERVER_DOMAIN);
    //return String.format("http://%s/ms/mb/", Constants.SERVER_DOMAIN);
    val apiUrl: String
        get() {
            if (BuildConfig.DEBUG) {
            } else {
            }
            return "https://www.googleapis.com/"
        }

    /**
     * API_URL取得(https用)
     * Acquisition of API_URL(for https)
     *
     * @return url
     */
    //return String.format("https://%s/ms4/mb/", Constants.SERVER_DOMAIN);
    //return String.format("https://%s/ms/mb/", Constants.SERVER_DOMAIN);
    val apiUrlForHttps: String
        get() {
            if (BuildConfig.DEBUG) {
            } else {
            }
            return "https://www.googleapis.com/"
        }

    /**
     * Emailの正規表現チェック
     *
     * @param email e-mail
     * @return true:OK,false:NG
     */
    fun emailCheck(email: String): Boolean {
        return !Constants.EMAIL_PATTERN.matcher(email).find()
    }

    fun usernameCheck(username: String): Boolean {
        return !Constants.USERNAME_PATTERN.matcher(username).find()
    }


    /**
     * パスワードの正規表現チェック
     *
     * @param password パスワード
     * @return true:OK,false:NG
     */
    fun passwordCheck(password: String): Boolean {
        return !Constants.PASSWORD_PATTERN.matcher(password).find()
    }


}
