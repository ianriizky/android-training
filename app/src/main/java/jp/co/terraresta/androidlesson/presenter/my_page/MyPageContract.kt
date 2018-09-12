package jp.co.terraresta.androidlesson.presenter.my_page

import android.graphics.Bitmap
import io.reactivex.Observable
import jp.co.terraresta.androidlesson.data.model.media.ImageUploadData
import jp.co.terraresta.androidlesson.data.model.profile.ProfileDisplayData
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface MyPageContract {

    interface View {
        fun setFetchRess(userData: ProfileDisplayData)
        fun showError(msg: String)
    }
    interface Presenter {
        fun fetchUserData()
        fun takePhoto(img: Bitmap)
        fun openGallery(img: Bitmap)
        fun isSucessFetchUserData(ressData: ProfileDisplayData)
        fun updatePhotoProfile(data:ImageUploadData, action: String)
    }
    interface Services {
        @GET("/app/api/{controller}/{action}")
        fun getUserData(
                @Path("controller") controller: String,
                @Path("action") action: String,
                @QueryMap params: MutableMap<String, Any>
        ): Observable<ProfileDisplayData>
    }
}