package jp.co.terraresta.androidlesson.data.handler.common

import jp.co.terraresta.androidlesson.common.Constants.SERVER_DOMAIN
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by ooyama on 2017/05/24.
 */

open class BaseHandler {

    var retrofit: Retrofit? = null;
//    var retrofitBuilder: Retrofit.Builder? = null
    var httpLoggingInterceptor = HttpLoggingInterceptor()
    var httpClient: OkHttpClient.Builder = OkHttpClient.Builder();

    fun RestClient(): Retrofit? {
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        httpClient.addInterceptor(httpLoggingInterceptor)
        retrofit = Retrofit.Builder()
                .baseUrl(SERVER_DOMAIN)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build()

        return retrofit
    }



}
