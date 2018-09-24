package jp.co.terraresta.androidlesson.presenter.profile

import io.reactivex.Observable
import jp.co.terraresta.androidlesson.data.model.profile.ProfileDisplayData
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

/**
 * Created by ooyama on 2017/05/29.
 */

interface ProfileDisplayContract {
    interface View {
        fun setRess(data: ProfileDisplayData)
        fun showError(msg: String)
    }

    interface Presenter {
        fun isSuccessFetchData(data:ProfileDisplayData)
        fun fetchUserData(uid: Int)
    }

    interface Services{
        @GET("/app/api/{controller}/{action}")
        fun getUserData(
                @Path("controller") controller: String,
                @Path("action") action: String,
                @QueryMap params: MutableMap<String, Any>
        ): Observable<ProfileDisplayData>
    }
}
