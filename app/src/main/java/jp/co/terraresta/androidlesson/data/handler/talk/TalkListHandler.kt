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

class TalkListHandler: BaseHandler{
    var params: MutableMap<String, Any>? = HashMap()
    var presenter: TalkListPresenter? = null

    constructor(token: String, date: String, presenter: TalkListPresenter){
        params?.put(REQUEST_NAME_ACCESS_TOKEN, token)
        params?.put(REQUEST_NAME_LAST_LOGIN_TIME, date)
        this.presenter = presenter

    }

    fun getTalklistServices(): TalkListContract.Services?{
        return RestClient()?.create(TalkListContract.Services::class.java)
    }

    fun getTalklistDelServices(): TalkListContract.Services?{
        return RestClient()?.create(TalkListContract.Services::class.java)
    }

    fun delTalkListAction(talkid: String, token:String){
        params?.put(REQUEST_NAME_TALK_IDS, talkid)
        params?.put(REQUEST_NAME_ACCESS_TOKEN, token)
        try{
            getTalklistDelServices()?.getTalkListDel(API_CTRL_NAME_TALK, "Delete", params!!)
                    ?.subscribeOn(Schedulers.newThread())
                    ?.observeOn(AndroidSchedulers.mainThread())
                    ?.subscribe(object : Observer<BaseResultData>{
                        override fun onComplete() {
                        }

                        override fun onSubscribe(d: Disposable) {
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
            println("error kotlin - talklist handler: " +e)
        }
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
