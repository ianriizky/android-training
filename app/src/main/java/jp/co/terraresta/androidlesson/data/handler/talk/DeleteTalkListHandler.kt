package jp.co.terraresta.androidlesson.data.handler.talk

import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import jp.co.terraresta.androidlesson.common.Constants.API_CTRL_NAME_TALK
import jp.co.terraresta.androidlesson.common.Constants.REQUEST_NAME_ACCESS_TOKEN
import jp.co.terraresta.androidlesson.common.Constants.REQUEST_NAME_TALK_IDS
import jp.co.terraresta.androidlesson.data.handler.common.BaseHandler
import jp.co.terraresta.androidlesson.data.model.common.BaseResultData
import jp.co.terraresta.androidlesson.presenter.talk.DeleteTalkListContract
import jp.co.terraresta.androidlesson.presenter.talk.TalkListPresenter

class DeleteTalkListHandler(token: String, talkID: String, presenter: TalkListPresenter): BaseHandler() {

    var params: MutableMap<String, Any>? = HashMap()
    var presenter: TalkListPresenter? = presenter
    init {
        params?.put(REQUEST_NAME_TALK_IDS, talkID)
        params?.put(REQUEST_NAME_ACCESS_TOKEN, token)
    }

    private fun getTalklistDelServices(): DeleteTalkListContract.Services?{
        return RestClient()?.create(DeleteTalkListContract.Services::class.java)
    }

    fun delTalkListAction(){
        try{
            getTalklistDelServices()?.getTalkListDel(API_CTRL_NAME_TALK, "Delete", params!!)
                    ?.subscribeOn(Schedulers.newThread())
                    ?.observeOn(AndroidSchedulers.mainThread())
                    ?.subscribe(object : Observer<BaseResultData> {
                        override fun onSubscribe(d: Disposable) {
                        }

                        override fun onComplete() {
                        }

                        override fun onNext(t: BaseResultData) {
                            presenter?.isSuccessDelTalkList(t)
                        }

                        override fun onError(e: Throwable) {
                            e.stackTrace
                            presenter?.fetchTalkListRealm(e.message!!)
                        }

                    })

        } catch (e: Exception){
            println("error kotlin - talklist handler: $e")
        }
    }
}