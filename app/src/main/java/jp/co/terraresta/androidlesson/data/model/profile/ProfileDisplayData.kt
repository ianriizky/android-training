package jp.co.terraresta.androidlesson.data.model.profile

import com.google.gson.annotations.Expose

import jp.co.terraresta.androidlesson.data.model.common.BaseResultData

/**
 * Created by ooyama on 2017/05/26.
 */

class ProfileDisplayData : BaseResultData() {

    @Expose
    val userId: Int = 0
    @Expose
    val nickname: String? = null
    @Expose
    val imageId: Int = 0
    @Expose
    val imageSize: String? = null
    @Expose
    val imageUrl: String? = null
    @Expose
    val gender: Int = 0
    @Expose
    val age: Int = 0
    @Expose
    val job: Int = 0
    @Expose
    val residence: String? = null
    @Expose
    val personality: Int = 0
    @Expose
    val hobby: Int = 0
    @Expose
    val aboutMe: String? = null
    @Expose
    val userStatus: Int = 0
    @Expose
    val email: String? = null
    @Expose
    val password: String? = null
}
