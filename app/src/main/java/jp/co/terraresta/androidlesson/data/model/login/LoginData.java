package jp.co.terraresta.androidlesson.data.model.login;

import com.google.gson.annotations.Expose;

import jp.co.terraresta.androidlesson.data.model.common.BaseResultData;

/**
 * Created by ooyama on 2017/05/26.
 */

public class LoginData extends BaseResultData {

    @Expose
    private String accessToken;
    @Expose
    private int userId;

    public String getAccessToken() {
        return accessToken;
    }

    public int getUserId() {
        return userId;
    }
}
