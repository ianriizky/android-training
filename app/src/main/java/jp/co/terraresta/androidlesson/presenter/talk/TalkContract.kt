package jp.co.terraresta.androidlesson.presenter.talk

import android.net.Uri
import io.reactivex.Observable
import jp.co.terraresta.androidlesson.data.model.talk.SendMessageData
import jp.co.terraresta.androidlesson.data.model.talk.TalkData
import jp.co.terraresta.androidlesson.data.model.talk.TalkItem
import retrofit2.http.*

/**
 * Created by ooyama on 2017/05/29.
 */

interface TalkContract {
    interface View {
        fun setRess(data: List<TalkItem>)
        fun showError(msg: String)
    }
    interface Presenter {
        fun isSuccessFetchTalk(data: TalkData)
        fun upCamMedia(uri: Uri, type: Int)
        fun upGaleMedia(uri: Uri, type: Int)
        fun fetchTalk(toUserId: Int, howToReq: Int)
        fun uploadMedia(mediaId: Int, type: Int)
        fun sendMessage(msg: String)
        fun isSuccessSendMessage(data: SendMessageData)
        fun fetchTalkRealm(msg: String)
    }
    interface Services {
        @GET("app/api/{controller}/{action}")
        fun getTalkData(
                @Path("controller") controller: String,
                @Path("action") action: String,
                @QueryMap params: MutableMap<String, Any>
        ): Observable<TalkData>

    }

    interface Services1 {
        @FormUrlEncoded
        @POST("app/api/{controller}/{action}")
        fun getMessageData(
                @Path("controller") controller: String,
                @Path("action") action: String,
                @Field("message") message: String,
                @QueryMap params: MutableMap<String, Any>
        ): Observable<SendMessageData>
    }
}
