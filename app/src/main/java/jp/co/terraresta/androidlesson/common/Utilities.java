package jp.co.terraresta.androidlesson.common;

import jp.co.terraresta.androidlesson.BuildConfig;

/**
 * Created by ooyama on 2017/05/24.
 */

public class Utilities {

    /**
     * API_URL取得
     * Acquisition of API_URL
     *
     * @return url
     */
    public static String getApiUrl() {
        if (BuildConfig.DEBUG) {
            //return String.format("http://%s/ms4/mb/", Constants.SERVER_DOMAIN);
        } else {
            //return String.format("http://%s/ms/mb/", Constants.SERVER_DOMAIN);
        }
        return "https://www.googleapis.com/";
    }

    /**
     * API_URL取得(https用)
     * Acquisition of API_URL(for https)
     *
     * @return url
     */
    public static String getApiUrlForHttps() {
        if (BuildConfig.DEBUG) {
            //return String.format("https://%s/ms4/mb/", Constants.SERVER_DOMAIN);
        } else {
            //return String.format("https://%s/ms/mb/", Constants.SERVER_DOMAIN);
        }
        return "https://www.googleapis.com/";
    }

    /**
     * Emailの正規表現チェック
     *
     * @param email e-mail
     * @return true:OK,false:NG
     */
    public static boolean emailCheck(String email) {
        if (Constants.EMAIL_PATTERN.matcher(email).find()) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * パスワードの正規表現チェック
     *
     * @param password パスワード
     * @return true:OK,false:NG
     */
    public static boolean passwordCheck(String password) {
        if (Constants.PASSWORD_PATTERN.matcher(password).find()) {
            return false;
        } else {
            return true;
        }
    }
}
