package jp.co.terraresta.androidlesson.presenter.login

import android.app.Activity
import jp.co.terraresta.androidlesson.data.model.login.LoginData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

/**
 * Created by ooyama on 2017/05/29.
 */

interface LoginContract {
    interface  View {
        fun showError(err: String)
    }

    interface Presenter {
        fun login()
        fun isLoginSuccess(response: LoginData)
    }

    interface Services {
        @GET("/app/api/{controller}/{action}")
        fun getLoginData(
                @Path("controller") controller: String,
                @Path("action") action: String,
                @QueryMap params:MutableMap<String, String>
        ): Call<LoginData>

    }
}
