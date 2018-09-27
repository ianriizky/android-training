package jp.co.terraresta.androidlesson.presenter.talk

import io.reactivex.Observable
import jp.co.terraresta.androidlesson.data.model.common.BaseResultData
import jp.co.terraresta.androidlesson.data.model.talk.TalkListData
import jp.co.terraresta.androidlesson.data.model.talk.TalkListItem
import jp.co.terraresta.androidlesson.data.model.talk.TalkListItemRealm
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

/**
 * Created by ooyama on 2017/05/29.
 */

interface TalkListContract{
    interface View {
        fun setRess(data:List<TalkListItem>)
        fun showError(msg: String)
    }

    interface Presenter{
        fun fetchTalkList()
        fun fetchTalkListRealm(err: String)
        fun delTalkList(talkids: String)
        fun isSuccessFetchTalkList(data: TalkListData)
        fun isSuccessDelTalkList(data: BaseResultData)
    }

    interface Services {
        @GET("app/api/{controller}/{action}")
        fun getTalkList(
               @Path("controller") controller: String,
               @Path("action") action: String,
               @QueryMap params: MutableMap<String, Any>
        ): Observable<TalkListData>

        @GET("app/api/{controller}/{action}")
        fun getTalkListDel(
                @Path("controller") controller: String,
                @Path("action") action: String,
                @QueryMap params: MutableMap<String, Any>
        ): Observable<BaseResultData>
    }
}
