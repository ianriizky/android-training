package jp.co.terraresta.androidlesson.data.handler.media

import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import jp.co.terraresta.androidlesson.common.Constants.API_ACTION_NAME_VIDEO_UPLOAD
import jp.co.terraresta.androidlesson.common.Constants.API_CTRL_NAME_MEDIA
import jp.co.terraresta.androidlesson.common.Constants.REQUEST_NAME_ACCESS_TOKEN
import jp.co.terraresta.androidlesson.data.handler.common.BaseHandler
import jp.co.terraresta.androidlesson.data.model.media.VideoUploadData
import jp.co.terraresta.androidlesson.presenter.media.MediaUploadContract
import jp.co.terraresta.androidlesson.presenter.media.MediaUploadPresenter
import okhttp3.MultipartBody

/**
 * Created by ooyama on 2017/05/29.
 */

class VideoUploadHandler(token: String, data:MultipartBody.Part, presenter: MediaUploadPresenter): BaseHandler(){

    val params: MutableMap<String, Any> = HashMap()
    var vidData: MultipartBody.Part = data
    var presenter = presenter
    init{
       params.put(REQUEST_NAME_ACCESS_TOKEN, token)
    }

    fun getVideoServices(): MediaUploadContract.Services1?{
        return RestClient()?.create(MediaUploadContract.Services1::class.java)
    }

    fun uploadVideoAction(){
        try {
           getVideoServices()?.postVideo(API_CTRL_NAME_MEDIA, API_ACTION_NAME_VIDEO_UPLOAD, params, vidData)
                   ?.subscribeOn(Schedulers.newThread())
                   ?.observeOn(AndroidSchedulers.mainThread())
                   ?.subscribe(object: Observer<VideoUploadData>{
                       override fun onComplete() {
                       }

                       override fun onSubscribe(d: Disposable) {
                       }

                       override fun onNext(t: VideoUploadData) {
                           presenter.isUpladoVideoSuccess(t)
                       }

                       override fun onError(e: Throwable) {
                           e.stackTrace
                           println("error retrofit video upload: $e")
                       }

                   })
        } catch (e: Exception){
            println("error kotlin video handler: $e")
        }
    }
}
