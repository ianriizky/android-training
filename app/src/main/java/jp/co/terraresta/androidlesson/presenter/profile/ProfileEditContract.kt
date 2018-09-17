package jp.co.terraresta.androidlesson.presenter.profile

import io.reactivex.Observable
import jp.co.terraresta.androidlesson.data.model.common.BaseResultData
import jp.co.terraresta.androidlesson.data.model.profile.ProfileDisplayData
import jp.co.terraresta.androidlesson.presenter.my_page.MyPagePresenter
import retrofit2.http.*
import rx.Observer

/**
 * Created by ooyama on 2017/05/29.
 */

interface ProfileEditContract {
    interface  View {
        fun setResponse(data: BaseResultData)
        fun showError(msg: String)
    }

    interface  Presenter {
        fun isSuccessEditProfile(data: BaseResultData, action: String)
        fun editProfile()
        fun editProfilePhotoProfile(presenter: MyPagePresenter, data: ProfileDisplayData)
        fun editProfile(view: ProfileEditContract.View, data:ProfileDisplayData)
    }

    interface  Services {
        @FormUrlEncoded
       @POST("/app/api/{controller}/{action}" )
        fun getEditProfile(
                @Path("controller") controller: String,
                @Path("action") action: String,
                @Query("access_token") token: String,
                @FieldMap  params: MutableMap<String, Any>
       ):Observable<BaseResultData>

    }
}
