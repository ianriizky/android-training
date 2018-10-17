package jp.co.terraresta.androidlesson.view.view_model.profile

import android.databinding.BaseObservable
import android.databinding.Bindable
import android.databinding.PropertyChangeRegistry
import jp.co.terraresta.androidlesson.BR
import jp.co.terraresta.androidlesson.data.model.profile.ProfileDisplayData
import java.io.Serializable

/**
 * Created by ooyama on 2017/05/29.
 */

class ProfileEditViewModel: BaseObservable(){

    @get:Bindable
    var nickname: String =""
    set(value) {
        field = value
        notifyPropertyChanged(BR.nickname)
    }

    @get:Bindable
    var birthday: String =""
        set(value) {
            field = value
            notifyPropertyChanged(BR.birthday)
        }

    @get:Bindable
    var gender: String =""
        set(value) {
            field = value
            notifyPropertyChanged(BR.gender)
        }

    @get:Bindable
    var job: String =""
        set(value) {
            field = value
            notifyPropertyChanged(BR.job)
        }

    @get:Bindable
    var residence: String =""
        set(value) {
            field = value
            notifyPropertyChanged(BR.residence)
        }

    @get:Bindable
    var hobby: String =""
        set(value) {
            field = value
            notifyPropertyChanged(BR.hobby)
        }

    @get:Bindable
    var personality: String =""
        set(value) {
            field = value
            notifyPropertyChanged(BR.personality)
        }

    @get:Bindable
    var aboutme: String =""
        set(value) {
            field = value
            notifyPropertyChanged(BR.aboutme)
        }

//    lateinit var birthday: String
//    lateinit var gender: String
//    lateinit var job: String
//    lateinit var residence: String
//    lateinit var hobby: String
//    lateinit var personality: String
//    lateinit var aboutme: String
}
