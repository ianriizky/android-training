package jp.co.terraresta.androidlesson.presenter.account

import io.reactivex.Observable
import jp.co.terraresta.androidlesson.data.handler.common.BaseHandler
import jp.co.terraresta.androidlesson.data.model.common.BaseResultData
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.QueryMap

/**
 * Created by ooyama on 2017/05/29.
 */

interface DeleteAccountContract {
    interface View {

    }

    interface Presenter {
       fun deleteAccount()
        fun isDeleteAccountSuccess(data: BaseResultData)
    }

    interface Services {
       @GET("/app/api/{controller}/{action}")
        fun getDelAccount(
               @Path("controller") controller: String,
               @Path("action") action: String,
               @QueryMap params: MutableMap<String, String>
       ):Observable<BaseResultData>
    }
}
