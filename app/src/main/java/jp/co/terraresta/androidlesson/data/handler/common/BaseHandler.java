package jp.co.terraresta.androidlesson.data.handler.common;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import jp.co.terraresta.androidlesson.BuildConfig;
import jp.co.terraresta.androidlesson.common.Utilities;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ooyama on 2017/05/24.
 */

public class BaseHandler {

    private boolean isHttps;

    public void setIsHttps(boolean isHttps) {
        this.isHttps = isHttps;
    }

    public Retrofit getRetrofit() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.readTimeout(60, TimeUnit.SECONDS);
        httpClient.connectTimeout(60, TimeUnit.SECONDS);
        httpClient.addInterceptor(chain -> {
            Request original = chain.request();

            Request request = original.newBuilder()
                    .header("User-Agent", "")
                    .method(original.method(), original.body())
                    .build();

            return chain.proceed(request);
        });

        //debugのみ通信時にログを出力する
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            httpClient.addInterceptor(interceptor);
        }

//        Gson gson = new GsonBuilder()
//                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
//                .create();

        // Gsonクラスをインスタンス化する際にExposeアノテーションを扱えるように設定します。
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

        OkHttpClient client = httpClient.build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(this.isHttps ? Utilities.getApiUrlForHttps() : Utilities.getApiUrl())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson)) //パラメータにgsonを指定
                .client(client)
                .build();

        return retrofit;
    }
}
