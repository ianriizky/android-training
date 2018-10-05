package jp.co.terraresta.androidlesson.data.handler.talk

import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import jp.co.terraresta.androidlesson.common.Constants.API_ACTION_NAME_TALK
import jp.co.terraresta.androidlesson.common.Constants.API_CTRL_NAME_TALK
import jp.co.terraresta.androidlesson.common.Constants.REQUEST_NAME_ACCESS_TOKEN
import jp.co.terraresta.androidlesson.common.Constants.REQUEST_NAME_BORDER_MESSAGE_ID
import jp.co.terraresta.androidlesson.common.Constants.REQUEST_NAME_HOW_TO_REQUEST
import jp.co.terraresta.androidlesson.common.Constants.REQUEST_NAME_TO_USER_ID
import jp.co.terraresta.androidlesson.data.handler.common.BaseHandler
import jp.co.terraresta.androidlesson.data.model.talk.TalkData
import jp.co.terraresta.androidlesson.presenter.talk.TalkContract
import jp.co.terraresta.androidlesson.presenter.talk.TalkPresenter

/**
 * Created by ooyama on 2017/05/26.
 */

class TalkHandler(token: String, toUserId: Int, borderMessage: Int, presenter: TalkPresenter ): BaseHandler(){

    val params: MutableMap<String, Any> = HashMap()
    val presenter: TalkPresenter = presenter
    init {
        params.put(REQUEST_NAME_ACCESS_TOKEN, token)
        params.put(REQUEST_NAME_TO_USER_ID, toUserId)
        params.put(REQUEST_NAME_BORDER_MESSAGE_ID, borderMessage)
    }

   private fun getTalkServices(): TalkContract.Services?{
       return RestClient()?.create(TalkContract.Services::class.java)
    }

    fun fetchTalkAction(howToReq: Int){
        params.put(REQUEST_NAME_HOW_TO_REQUEST, howToReq)
        try {
            getTalkServices()?.getTalkData(API_CTRL_NAME_TALK, API_ACTION_NAME_TALK, params)
                    ?.subscribeOn(Schedulers.newThread())
                    ?.observeOn(AndroidSchedulers.mainThread())
                    ?.subscribe(object : Observer<TalkData>{
                        override fun onComplete() {
                        }

                        override fun onSubscribe(d: Disposable) {
                        }

                        override fun onNext(t: TalkData) {
                            presenter.isSuccessFetchTalk(t)
                        }

                        override fun onError(e: Throwable) {
                            e.stackTrace
                        }

                    })

        } catch (e: Exception){
            println("error kotlin - talkhandler: $e")
        }
    }
}
