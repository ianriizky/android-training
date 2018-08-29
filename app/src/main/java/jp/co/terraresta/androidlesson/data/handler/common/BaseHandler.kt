package jp.co.terraresta.androidlesson.data.handler.common

import jp.co.terraresta.androidlesson.common.Constants.SERVER_DOMAIN
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by ooyama on 2017/05/24.
 */

open class BaseHandler {

    var retrofit: Retrofit? = null;
//    var retrofitBuilder: Retrofit.Builder? = null
    var httpClient: OkHttpClient.Builder? = null;

    fun RestClient(): Retrofit? {
        httpClient = OkHttpClient.Builder()
        retrofit = Retrofit.Builder()
                .baseUrl(SERVER_DOMAIN)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient?.build())
                .build()

        return retrofit
    }



}
