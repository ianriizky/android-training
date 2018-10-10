package jp.co.terraresta.androidlesson.presenter.talk

import android.content.Context
import io.realm.Realm
import io.realm.RealmConfiguration
import jp.co.terraresta.androidlesson.common.Preferences
import jp.co.terraresta.androidlesson.data.handler.talk.DeleteTalkListHandler
import jp.co.terraresta.androidlesson.data.handler.talk.TalkListHandler
import jp.co.terraresta.androidlesson.data.model.common.BaseResultData
import jp.co.terraresta.androidlesson.data.model.talk.TalkListData
import jp.co.terraresta.androidlesson.data.model.talk.TalkListItem
import jp.co.terraresta.androidlesson.data.model.talk.TalkListItemRealm

/**
 * Created by ooyama on 2017/05/29.
 */

class TalkListPresenter(ctx: Context, view: TalkListContract.View): TalkListContract.Presenter{

    override fun fetchTalkListRealm(msg: String) {
        val realm = initRealm()
        val talk = realm.where(TalkListItemRealm::class.java).findAll()
        if(!talk.isEmpty()){
            talkListView.setRess(fromRealmToList(talk.toList()))
        } else {
            talkListView.showError(msg)
        }

    }

    // DELETE TALK LIST
    override fun isSuccessDelTalkList(data: BaseResultData) {
        if(data.status == 1){
           fetchTalkList()
        } else {
            talkListView.showError(data.errorData?.errorMessage!!)
        }
    }

    override fun delTalkList(talkids: String) {
        talkListDelHandler = DeleteTalkListHandler(pref.getToken(talkListCtx), talkids, this)
        talkListDelHandler?.delTalkListAction()
    }

    private var talkListCtx: Context = ctx
    private var talkListView: TalkListContract.View = view
    var pref: Preferences = Preferences()
    private var talkListHandler: TalkListHandler? = null
    private var talkListDelHandler: DeleteTalkListHandler? = null

    // REALM CONFIG
    private fun initRealm():Realm {
        Realm.init(talkListCtx)
        val config = RealmConfiguration.Builder()
                .name("talk.realm").build()
        return Realm.getInstance(config)
    }

    // FETCHING TALK LIST
    override fun fetchTalkList() {
            talkListHandler = TalkListHandler(pref.getToken(talkListCtx), "", this)
            talkListHandler?.fetchTalkListAction()
    }

    private fun fromRealmToList(listRealm:List<TalkListItemRealm>): List<TalkListItem>{
        val list: MutableList<TalkListItem> = MutableList(listRealm.size){ TalkListItem()}

        for(i in listRealm.indices){
            list[i].nickname = listRealm[i].nickname
            list[i].lastUpdateTime = listRealm[i].lastUpdateTime
            list[i].message = listRealm[i].message
            list[i].imageId = listRealm[i].imageId
            list[i].talkId = listRealm[i].talkId!!
            list[i].toUserId = listRealm[i].toUserId
            list[i].imageUrl = listRealm[i].imageUrl
//            println("lastlogin: " +listRealm[i].lastUpdateTime)
        }
        return list
    }

    override fun isSuccessFetchTalkList(data: TalkListData) {
        if(data.status == 1) {
            if(data.items != null){
                saveDataRealm(data.items!!)
                talkListView.setRess(data.items!!)
            }
        }else {
            talkListView.showError(data.errorData?.errorMessage!!)
        }
    }

    private fun saveDataRealm(talkitem: List<TalkListItem>){
        try {
            val realm = initRealm()
            realm.beginTransaction()
            realm.deleteAll()
            for(i in talkitem.indices){
                val talklist = realm.createObject(TalkListItemRealm::class.java, talkitem[i].talkId)
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
           println("ERROR REALM:  $e")
        }
    }

}
