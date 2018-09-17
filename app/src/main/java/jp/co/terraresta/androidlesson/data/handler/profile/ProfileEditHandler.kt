package jp.co.terraresta.androidlesson.data.handler.profile

import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import jp.co.terraresta.androidlesson.common.Constants.API_ACTION_NAME_PROFILE_EDIT
import jp.co.terraresta.androidlesson.common.Constants.API_CTRL_NAME_PROFILE
import jp.co.terraresta.androidlesson.common.Constants.REQUEST_NAME_ABOUT_ME
import jp.co.terraresta.androidlesson.common.Constants.REQUEST_NAME_ACCESS_TOKEN
import jp.co.terraresta.androidlesson.common.Constants.REQUEST_NAME_BIRTHDAY
import jp.co.terraresta.androidlesson.common.Constants.REQUEST_NAME_DATA
import jp.co.terraresta.androidlesson.common.Constants.REQUEST_NAME_GENDER
import jp.co.terraresta.androidlesson.common.Constants.REQUEST_NAME_HOBBY
import jp.co.terraresta.androidlesson.common.Constants.REQUEST_NAME_IMAGE_ID
import jp.co.terraresta.androidlesson.common.Constants.REQUEST_NAME_JOB
import jp.co.terraresta.androidlesson.common.Constants.REQUEST_NAME_NICKNAME
import jp.co.terraresta.androidlesson.common.Constants.REQUEST_NAME_PERSONALITY
import jp.co.terraresta.androidlesson.common.Constants.REQUEST_NAME_RESIDENCE
import jp.co.terraresta.androidlesson.data.handler.common.BaseHandler
import jp.co.terraresta.androidlesson.data.model.common.BaseResultData
import jp.co.terraresta.androidlesson.data.model.profile.ProfileDisplayData
import jp.co.terraresta.androidlesson.presenter.profile.ProfileEditContract
import jp.co.terraresta.androidlesson.presenter.profile.ProfileEditPresenter

/**
 * Created by ooyama on 2017/05/26.
 */

class ProfileEditHandler: BaseHandler {

    var params: MutableMap<String, Any>? = HashMap()
    var dataparams: MutableMap<String, Any>? = HashMap()
    var dataProfile: ProfileDisplayData? = null
    var profileEditPresenter: ProfileEditPresenter? = null
    var accessToken: String? = null
    var action: String? = null

    constructor(token: String, data:ProfileDisplayData, presenter: ProfileEditPresenter, action: String) {
        accessToken = token
        dataparams?.put(REQUEST_NAME_IMAGE_ID, data.imageId)
        dataparams?.put(REQUEST_NAME_NICKNAME, data.nickname!!)
        dataparams?.put(REQUEST_NAME_RESIDENCE, data.residence!!)
        dataparams?.put(REQUEST_NAME_GENDER, data.gender)
        dataparams?.put(REQUEST_NAME_JOB, data.job)
        dataparams?.put(REQUEST_NAME_BIRTHDAY, data.birthday!!)
        dataparams?.put(REQUEST_NAME_HOBBY, data.hobby!!)
        dataparams?.put(REQUEST_NAME_PERSONALITY, data.personality)
        dataparams?.put(REQUEST_NAME_ABOUT_ME, data.aboutMe!!)

        this.action = action
        profileEditPresenter = presenter
    }

    fun getProfileEditServices(): ProfileEditContract.Services? {
        return RestClient()?.create(ProfileEditContract.Services::class.java)
    }

    fun editProfileAction() {
        try {
            getProfileEditServices()?.getEditProfile(API_CTRL_NAME_PROFILE, API_ACTION_NAME_PROFILE_EDIT, accessToken!!, dataparams!!)
                    ?.subscribeOn(Schedulers.newThread())
                    ?.observeOn(AndroidSchedulers.mainThread())
                    ?.subscribe(object : Observer<BaseResultData>{
                        override fun onComplete() {
                        }

                        override fun onSubscribe(d: Disposable) {
                        }

                        override fun onNext(t: BaseResultData) {
                            profileEditPresenter?.isSuccessEditProfile(t, action!!)
                            println("ERROR API: " +t.errorData?.errorMessage)
                        }

                        override fun onError(e: Throwable) {
                            e.stackTrace
                            println("error profile edit: " +e)
                        }

                    })
        } catch (e: Exception) {
            println("err: " +e)
        }
    }
}
