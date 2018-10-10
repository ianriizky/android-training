package jp.co.terraresta.androidlesson.data.handler.talk

import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import jp.co.terraresta.androidlesson.common.Constants.API_ACTION_NAME_SEND_MESSAGE
import jp.co.terraresta.androidlesson.common.Constants.API_CTRL_NAME_TALK
import jp.co.terraresta.androidlesson.common.Constants.REQUEST_NAME_ACCESS_TOKEN
import jp.co.terraresta.androidlesson.common.Constants.REQUEST_NAME_IMAGE_ID
import jp.co.terraresta.androidlesson.common.Constants.REQUEST_NAME_TO_USER_ID
import jp.co.terraresta.androidlesson.common.Constants.REQUEST_NAME_VIDEO_ID
import jp.co.terraresta.androidlesson.data.handler.common.BaseHandler
import jp.co.terraresta.androidlesson.data.model.talk.SendMessageData
import jp.co.terraresta.androidlesson.presenter.talk.TalkContract
import jp.co.terraresta.androidlesson.presenter.talk.TalkPresenter

/**
 * Created by ooyama on 2017/05/26.
 */

class SendMessageHandler(token: String, touserid: Int, msg: String, presenter: TalkPresenter): BaseHandler(){

    val params: MutableMap<String, Any> = HashMap()
    val message: String = msg
    val talkpresenter = presenter

   init {
       params.put(REQUEST_NAME_TO_USER_ID, touserid)
       params.put(REQUEST_NAME_ACCESS_TOKEN, token)
   }

    fun getMessageServices(): TalkContract.Services1? {
       return RestClient()?.create(TalkContract.Services1::class.java)
    }

    fun sendMessageAction(vid: Int?, img: Int?){
        if(vid != 0){
            params.put(REQUEST_NAME_VIDEO_ID, vid!!)
        } else if(img != 0){
            params.put(REQUEST_NAME_IMAGE_ID, img!!)
        }
        try{
           getMessageServices()?.getMessageData(API_CTRL_NAME_TALK, API_ACTION_NAME_SEND_MESSAGE, message, params)
                   ?.subscribeOn(Schedulers.newThread())
                   ?.observeOn(AndroidSchedulers.mainThread())
                   ?.subscribe(object: Observer<SendMessageData>{
                       override fun onComplete() {
                       }

                       override fun onSubscribe(d: Disposable) {
                       }

                       override fun onNext(t: SendMessageData) {
                           talkpresenter.isSuccessSendMessage(t)
                       }
                       override fun onError(e: Throwable) {
                           e.stackTrace
                           println("error retrofit send message: $e")
                           talkpresenter.talkView.showError(e.message!!)
                       }

                   })

        } catch(e: Exception){
            println("error kotlin send message: $e")
        }
    }
}
