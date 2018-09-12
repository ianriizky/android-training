package jp.co.terraresta.androidlesson.data.handler.account

import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import jp.co.terraresta.androidlesson.common.Constants.API_ACTION_NAME_DELETE_ACCOUNT
import jp.co.terraresta.androidlesson.common.Constants.API_CTRL_NAME_ACCOUNT
import jp.co.terraresta.androidlesson.common.Constants.REQUEST_NAME_ACCESS_TOKEN
import jp.co.terraresta.androidlesson.data.handler.common.BaseHandler
import jp.co.terraresta.androidlesson.data.model.common.BaseResultData
import jp.co.terraresta.androidlesson.presenter.account.DeleteAccountContract

/**
 * Created by ooyama on 2017/05/26.
 */

class DeleteAccountHandler: BaseHandler {

    var delParams: MutableMap<String, String> = HashMap()
    var delAccountPresenter: DeleteAccountContract.Presenter? = null

    constructor(token: String, presenter: DeleteAccountContract.Presenter) {
        delParams.put(REQUEST_NAME_ACCESS_TOKEN, token)
        delAccountPresenter = presenter
    }

    fun getDelServices(): DeleteAccountContract.Services? {
       return RestClient()?.create(DeleteAccountContract.Services::class.java)
    }

    fun deleteAccountAction() {
        try{
            getDelServices()?.getDelAccount(API_CTRL_NAME_ACCOUNT, API_ACTION_NAME_DELETE_ACCOUNT, delParams!!)
                    ?.subscribeOn(Schedulers.newThread())
                    ?.observeOn(AndroidSchedulers.mainThread())
                    ?.subscribe(object : Observer<BaseResultData> {
                        override fun onNext(t: BaseResultData) {
                            println("DATA: " +t.status)
                            delAccountPresenter?.isDeleteAccountSuccess(t)
                        }

                        override fun onComplete() {
                        }

                        override fun onSubscribe(d: Disposable) {
                        }

                        override fun onError(e: Throwable) {
                            e.stackTrace
                            println("ERR" + e)
                        }
                    })

        } catch (e: Exception) {
            println("ERRS: "+e)
        }
    }
}
