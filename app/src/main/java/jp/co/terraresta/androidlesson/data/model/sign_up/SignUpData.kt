package jp.co.terraresta.androidlesson.data.model.sign_up

import com.google.gson.annotations.Expose

import jp.co.terraresta.androidlesson.data.model.common.BaseResultData

/**
 * Created by ooyama on 2017/05/26.
 */

class SignUpData : BaseResultData() {

    @Expose
    val accessToken: String? = null
    @Expose
    val userId: Int = 0
}
