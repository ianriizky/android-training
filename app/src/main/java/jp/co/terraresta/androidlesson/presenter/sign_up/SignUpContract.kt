package jp.co.terraresta.androidlesson.presenter.sign_up

import android.telecom.Call
import android.view.View
import jp.co.terraresta.androidlesson.data.model.sign_up.SignUpData
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap
import java.util.*

/**
 * Created by ooyama on 2017/05/29.
 */

open interface SignUpContract {

    interface View {
        fun showError(errorMassage: String)
    }

    interface Presenter {
        fun signUp()
    }

    interface Services {
        @GET("/app/api/{controller}/{action}")
        fun getSignupData (
                @Path("controller") controller: String,
                @Path("action") action:String,
                @QueryMap params:MutableMap<String, String>
        ): retrofit2.Call<SignUpData>

    }
}
