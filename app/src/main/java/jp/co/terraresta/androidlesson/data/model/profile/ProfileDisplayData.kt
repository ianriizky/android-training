package jp.co.terraresta.androidlesson.data.model.profile

import com.google.gson.annotations.Expose

import jp.co.terraresta.androidlesson.data.model.common.BaseResultData
import java.io.Serializable

/**
 * Created by ooyama on 2017/05/26.
 */

class ProfileDisplayData : BaseResultData(), Serializable {

    @Expose
    val userId: Int = 0
    @Expose
    var nickname: String? = null
    @Expose
    var imageId: Int = 0
    @Expose
    val imageSize: String? = null
    @Expose
    val imageUrl: String? = null
    @Expose
    var gender: Int = 0
    @Expose
    val age: Int = 0
    @Expose
    var job: Int = 0
    @Expose
    var residence: String? = null
    @Expose
    var personality: Int = 0
    @Expose
    var hobby: String? = null
    @Expose
    var aboutMe: String? = null
    @Expose
    val userStatus: Int = 0
    @Expose
    val email: String? = null
    @Expose
    val password: String? = null
    @Expose
    var birthday: String? = null
}
