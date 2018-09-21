package jp.co.terraresta.androidlesson.presenter.profile_feed

import io.reactivex.Observable
import jp.co.terraresta.androidlesson.data.model.profile_feed.ProfileFeedData
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

/**
 * Created by ooyama on 2017/05/29.
 */

interface ProfileFeedContract {
    interface View{
        fun setRess(data:ProfileFeedData)
        fun showError(msg:String)
    }

    interface Presenter{
        fun fetchProfileFeed()
        fun isSuccessFetchFeed(data:ProfileFeedData)
    }

    interface Services{
        @GET("/app/api/{controller}/{action}" )
        fun getFeedData(
                @Path("controller") controller: String,
                @Path("action") action: String,
                @QueryMap params: MutableMap<String, Any>
        ): Observable<ProfileFeedData>
    }
}
