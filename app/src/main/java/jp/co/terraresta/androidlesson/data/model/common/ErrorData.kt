package jp.co.terraresta.androidlesson.data.model.common

import com.google.gson.annotations.Expose

/**
 * Created by ooyama on 2017/05/29.
 */

class ErrorData {

    @Expose
    val errorCode: Int = 0
    @Expose
    val errorTitle: String? = null
    @Expose
    val errorMessage: String? = null
}
