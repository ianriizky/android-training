package jp.co.terraresta.androidlesson.presenter.media

import android.graphics.Bitmap
import android.net.Uri
import io.reactivex.Observable
import jp.co.terraresta.androidlesson.data.model.media.ImageUploadData
import jp.co.terraresta.androidlesson.data.model.media.VideoUploadData
import jp.co.terraresta.androidlesson.presenter.my_page.MyPagePresenter
import jp.co.terraresta.androidlesson.presenter.talk.TalkPresenter
import jp.co.terraresta.androidlesson.view.activity.talk.TalkActivity
import okhttp3.MultipartBody
import retrofit2.http.*

/**
 * Created by ooyama on 2017/05/29.
 */

interface MediaUploadContract {
    interface  View {

    }

    interface  Presenter {
        fun openGale(uri: Uri, presenter: MyPagePresenter)
        fun takePhoto(uri: Uri, presenter: MyPagePresenter)
        fun uploadCamMediaTalk(uri: Uri,type: Int, presenter: TalkPresenter)
        fun uploadGaleMediaTalk(uri: Uri, type: Int, presenter: TalkPresenter)
        fun isUploadImageSuccess(data: ImageUploadData)
        fun isUpladoVideoSuccess(data: VideoUploadData)
        fun isUploadMediaSuccess(msg:String)
    }

    interface  Services {
        @Multipart
        @POST("/app/api/{controller}/{action}")
        fun postImage(
            @Path("controller") controller: String,
            @Path("action") action: String,
            @QueryMap params: MutableMap<String, Any>,
            @Part data: MultipartBody.Part
       ): Observable<ImageUploadData>
    }

    interface Services1 {
        @Multipart
        @POST("/app/api/{controller}/{action}")
        fun postVideo(
                @Path("controller") controller: String,
                @Path("action") action: String,
                @QueryMap params: MutableMap<String, Any>,
                @Part data: MultipartBody.Part
        ): Observable<VideoUploadData>
    }
}
