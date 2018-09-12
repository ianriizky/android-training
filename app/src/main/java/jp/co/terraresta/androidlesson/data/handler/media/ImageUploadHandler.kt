package jp.co.terraresta.androidlesson.data.handler.media

import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import jp.co.terraresta.androidlesson.common.Constants.API_ACTION_NAME_IMAGE_UPLOAD
import jp.co.terraresta.androidlesson.common.Constants.API_CTRL_NAME_MEDIA
import jp.co.terraresta.androidlesson.common.Constants.REQUEST_NAME_ACCESS_TOKEN
import jp.co.terraresta.androidlesson.common.Constants.REQUEST_NAME_DATA
import jp.co.terraresta.androidlesson.common.Constants.REQUEST_NAME_LOCATION
import jp.co.terraresta.androidlesson.data.handler.common.BaseHandler
import jp.co.terraresta.androidlesson.data.model.media.ImageUploadData
import jp.co.terraresta.androidlesson.presenter.media.MediaUploadContract
import jp.co.terraresta.androidlesson.presenter.media.MediaUploadPresenter
import okhttp3.MultipartBody
import okhttp3.RequestBody

/**
 * Created by ooyama on 2017/05/29.
 */

class ImageUploadHandler: BaseHandler {
    var params: MutableMap<String, Any>? = HashMap()
    var mediaUploadPresenter: MediaUploadPresenter? = null
    var imageData: MultipartBody.Part? = null

    constructor(token: String, location: String, imageData: MultipartBody.Part, presenter:MediaUploadPresenter) {
        params?.put(REQUEST_NAME_ACCESS_TOKEN, token)
        params?.put(REQUEST_NAME_LOCATION, location)
        this.imageData = imageData
        mediaUploadPresenter = presenter
    }

    fun getImageUploadServices(): MediaUploadContract.Services? {
        return RestClient()?.create(MediaUploadContract.Services::class.java)
    }

    fun uploadImageAction() {
        println("Location: " +params?.get(REQUEST_NAME_LOCATION))
        try {
            getImageUploadServices()?.postImage(API_CTRL_NAME_MEDIA, API_ACTION_NAME_IMAGE_UPLOAD, params!!, imageData!!)
                    ?.subscribeOn(Schedulers.newThread())
                    ?.observeOn(AndroidSchedulers.mainThread())
                    ?.subscribe(object : Observer<ImageUploadData> {
                        override fun onComplete() {
                        }

                        override fun onSubscribe(d: Disposable) {
                        }

                        override fun onNext(t: ImageUploadData) {
                            mediaUploadPresenter?.isUploadImageSuccess(t)
                        }

                        override fun onError(e: Throwable) {
                            e.stackTrace
                            println("error retrofit: " +e)
                        }

                    })
        } catch (e: Exception){
            println("error: " +e)

        }
    }
}
