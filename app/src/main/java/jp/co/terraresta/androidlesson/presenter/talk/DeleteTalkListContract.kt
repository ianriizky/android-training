package jp.co.terraresta.androidlesson.presenter.talk

import io.reactivex.Observable
import jp.co.terraresta.androidlesson.data.model.common.BaseResultData
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface DeleteTalkListContract {

    interface Services{
        @GET("app/api/{controller}/{action}")
        fun getTalkListDel(
                @Path("controller") controller: String,
                @Path("action") action: String,
                @QueryMap params: MutableMap<String, Any>
        ): Observable<BaseResultData>
    }
}