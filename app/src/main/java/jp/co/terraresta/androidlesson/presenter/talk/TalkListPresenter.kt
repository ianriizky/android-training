package jp.co.terraresta.androidlesson.presenter.talk

import android.content.Context
import io.realm.Realm
import io.realm.RealmConfiguration
import jp.co.terraresta.androidlesson.common.Preferences
import jp.co.terraresta.androidlesson.data.handler.talk.TalkListHandler
import jp.co.terraresta.androidlesson.data.model.common.BaseResultData
import jp.co.terraresta.androidlesson.data.model.talk.TalkListData
import jp.co.terraresta.androidlesson.data.model.talk.TalkListDataRealm
import jp.co.terraresta.androidlesson.data.model.talk.TalkListItem
import jp.co.terraresta.androidlesson.data.model.talk.TalkListItemRealm
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by ooyama on 2017/05/29.
 */

class TalkListPresenter(ctx: Context, view: TalkListContract.View): TalkListContract.Presenter{
    override fun isSuccessDelTalkList(data: BaseResultData) {
        if(data.status == 1){
            fetchTalkList()
        } else {
            talkListView.showError(data.errorData?.errorMessage!!)
        }
    }

    override fun delTalkList(talkids: String) {
        talkListHandler?.delTalkListAction(talkids, pref.getToken(talkListCtx))
    }

    var talkListCtx: Context = ctx
    var talkListItems: List<TalkListItem>? = null
    var talkListView: TalkListContract.View = view
    var talkListHandler: TalkListHandler? = null
    var pref: Preferences = Preferences()

    // REALM CONFIG
    fun initRealm():Realm {
        Realm.init(talkListCtx)
        val config = RealmConfiguration.Builder()
                .name("talklist.realm").build()
        val realm = Realm.getInstance(config)
        return realm
    }

    override fun fetchTalkListRealm(err: String) {
        var realm = initRealm()
        val talk = realm.where(TalkListItemRealm::class.java).findAll()
        if(!talk.isEmpty()){
            talkListView.setRess(fromRealmToList(talk.toList()))
        }
    }
    override fun fetchTalkList() {
        var date = ""
            talkListHandler = TalkListHandler(pref.getToken(talkListCtx), date, this)
            talkListHandler?.fetchTalkListAction()
    }

    fun fromRealmToList(listRealm:List<TalkListItemRealm>): List<TalkListItem>{
        var list: MutableList<TalkListItem> = MutableList(listRealm.size){ TalkListItem()}

        for(i in listRealm.indices){
            list[i].nickname = listRealm[i].nickname
            list[i].lastUpdateTime = listRealm[i].lastUpdateTime
            list[i].message = listRealm[i].message
            list[i].imageId = listRealm[i].imageId
            list[i].imageUrl = listRealm[i].imageUrl
//            println("lastlogin: " +listRealm[i].lastUpdateTime)
        }
        return list
    }

    override fun isSuccessFetchTalkList(data: TalkListData) {
        if(data.status == 1) {
            saveDataRealm(data.items!!)
            talkListView.setRess(data.items!!)
        }
//        } else {
////            talkListView.showError(data.errorData?.errorMessage!!)
//        }
    }

    fun saveDataRealm(talkitem: List<TalkListItem>){
        try {
            var realm = initRealm()
            realm.beginTransaction()
            realm.deleteAll()
            for(i in talkitem.indices){
                var talklist = realm.createObject(TalkListItemRealm::class.java, talkitem[i].talkId)
            talklist.imageId =talkitem[i].imageId
            talklist.imageSize = talkitem[i].imageSize
            talklist.imageUrl = talkitem[i].imageUrl
            talklist.lastUpdateTime = talkitem[i].lastUpdateTime
            talklist.mediaType = talkitem[i].mediaType
            talklist.messageId = talkitem[i].messageId
            talklist.message = talkitem[i].message
            talklist.userId = talkitem[i].userId
            talklist.userStatus = talkitem[i].userStatus
            talklist.toUserId = talkitem[i].toUserId
            talklist.nickname = talkitem[i].nickname
            talklist.time =  talkitem[i].time
//                println("success commit realm" +i.toString())
            }
            realm.commitTransaction()
        } catch(e: Exception){
           println("ERROR REALM:  " +e)
        }
    }

}
