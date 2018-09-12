package jp.co.terraresta.androidlesson.presenter.account

import android.content.Context
import jp.co.terraresta.androidlesson.common.Preferences
import jp.co.terraresta.androidlesson.data.handler.account.DeleteAccountHandler
import jp.co.terraresta.androidlesson.data.handler.common.BaseHandler
import jp.co.terraresta.androidlesson.data.model.common.BaseResultData
import jp.co.terraresta.androidlesson.presenter.my_page.MyPageContract

/**
 * Created by ooyama on 2017/05/29.
 */

class DeleteAccountPresenter(ctx: Context, view: MyPageContract.View): DeleteAccountContract.Presenter {
    override fun isDeleteAccountSuccess(data: BaseResultData) {
        if(data.status == 1){
            delPref.navRoot(delAccountCtx)
            delPref.clearSharedPref(delAccountCtx)
        } else {
           myPageView.showError(data.errorData?.errorMessage!!)
        }
    }


    var delAccountCtx = ctx;
    var delAccountHandler: DeleteAccountHandler? = null
    var delPref = Preferences()
    var myPageView = view

    override fun deleteAccount() {

        delAccountHandler = DeleteAccountHandler(delPref.getToken(delAccountCtx), this)
        delAccountHandler?.deleteAccountAction()

    }

}
