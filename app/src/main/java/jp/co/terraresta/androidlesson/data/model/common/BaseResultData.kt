package jp.co.terraresta.androidlesson.data.model.common

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by ooyama on 2017/05/26.
 */

open class BaseResultData {

    @Expose
    val status: Int = 0
    @SerializedName("error")
    @Expose
    val errorData: ErrorData? = null
}
