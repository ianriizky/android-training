package jp.co.terraresta.androidlesson.data.model.login

import com.google.gson.annotations.Expose

import jp.co.terraresta.androidlesson.data.model.common.BaseResultData

/**
 * Created by ooyama on 2017/05/26.
 */

class LoginData : BaseResultData() {

    @Expose
    val accessToken: String? = null
    @Expose
    val userId: Int = 0
}
