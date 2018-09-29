package jp.co.terraresta.androidlesson.data.handler.talk

import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import jp.co.terraresta.androidlesson.common.Constants.API_ACTION_NAME_TALK_LIST
import jp.co.terraresta.androidlesson.common.Constants.API_CTRL_NAME_TALK
import jp.co.terraresta.androidlesson.common.Constants.REQUEST_NAME_ACCESS_TOKEN
import jp.co.terraresta.androidlesson.common.Constants.REQUEST_NAME_LAST_LOGIN_TIME
import jp.co.terraresta.androidlesson.common.Constants.REQUEST_NAME_TALK_IDS
import jp.co.terraresta.androidlesson.data.handler.common.BaseHandler
import jp.co.terraresta.androidlesson.data.model.common.BaseResultData
import jp.co.terraresta.androidlesson.data.model.talk.TalkListData
import jp.co.terraresta.androidlesson.presenter.talk.TalkListContract
import jp.co.terraresta.androidlesson.presenter.talk.TalkListPresenter

/**
 * Created by ooyama on 2017/05/26.
 */

class TalkListHandler(token: String, date: String, presenter: TalkListPresenter) : BaseHandler() {
    var params: MutableMap<String, Any>? = HashMap()
    var presenter: TalkListPresenter? = presenter

    init {
        params?.put(REQUEST_NAME_ACCESS_TOKEN, token)
        params?.put(REQUEST_NAME_LAST_LOGIN_TIME, date)
    }

    private fun getTalklistServices(): TalkListContract.Services?{
        return RestClient()?.create(TalkListContract.Services::class.java)
    }


    fun fetchTalkListAction(){
        try{
            getTalklistServices()?.getTalkList(API_CTRL_NAME_TALK, API_ACTION_NAME_TALK_LIST, params!!)
                    ?.subscribeOn(Schedulers.newThread())
                    ?.observeOn(AndroidSchedulers.mainThread())
                    ?.subscribe(object : Observer<TalkListData>{
                        override fun onComplete() {
                        }

                        override fun onSubscribe(d: Disposable) {
                        }

                        override fun onNext(t: TalkListData) {
                            presenter?.isSuccessFetchTalkList(t)
                        }

                        override fun onError(e: Throwable) {
                            e.stackTrace
                            println("error retrofit-talklist: " +e)
                            presenter?.fetchTalkListRealm(e.message!!)
                        }

                    })

        } catch (e:Exception){
           println("Error kotlin: " +e)
        }
    }
}
