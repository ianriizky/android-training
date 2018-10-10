package jp.co.terraresta.androidlesson.presenter.talk

import android.content.Context
import android.net.Uri
import io.realm.Realm
import io.realm.RealmConfiguration
import jp.co.terraresta.androidlesson.common.Preferences
import jp.co.terraresta.androidlesson.data.handler.talk.SendMessageHandler
import jp.co.terraresta.androidlesson.data.handler.talk.TalkHandler
import jp.co.terraresta.androidlesson.data.model.talk.SendMessageData
import jp.co.terraresta.androidlesson.data.model.talk.TalkData
import jp.co.terraresta.androidlesson.data.model.talk.TalkItem
import jp.co.terraresta.androidlesson.data.model.talk.TalkItemRealm
import jp.co.terraresta.androidlesson.presenter.media.MediaUploadPresenter
import jp.co.terraresta.androidlesson.view.activity.talk.TalkActivity

/**
 * Created by ooyama on 2017/05/29.
 */

class TalkPresenter(ctx: Context, view: TalkActivity): TalkContract.Presenter{

    // fetcing data from local database
    override fun fetchTalkRealm(msg: String) {
        val realm = initRealm()
        val talk = realm.where(TalkItemRealm::class.java).findAll()
        if(talk.toList().size > 0){
           println("db is not empty")
        } else {
            println("db empty")
        }
//        println("user id: ${talk.userId}")
//        if(!talk.isEmpty()) {
//           println("local db empty")
//        } else {
//            println("local not empty")
//        }
//        } else {
//            talkView.showError(msg)
//        }
    }

    override fun uploadMedia(mediaId: Int, type: Int) {
        sendMessageHandler = SendMessageHandler(pref.getToken(talkCtx), touserId!!, "", this)
        if(type == 0){
            // upload image to server
            sendMessageHandler?.sendMessageAction(0, mediaId)
        } else {
            // upload video to server
            sendMessageHandler?.sendMessageAction(mediaId, 0)
        }
    }

    override fun isSuccessSendMessage(data: SendMessageData) {
        if(data.status == 1){
            fetchTalk(touserId!!, 0)
        }
    }

    override fun sendMessage(msg: String) {
        sendMessageHandler = SendMessageHandler(pref.getToken(talkCtx), touserId!!, msg, this)
        sendMessageHandler?.sendMessageAction(videoId!!, imageId!!)
    }

    // Upload media from gallery
    override fun upGaleMedia(uri: Uri, type: Int) {
        mediaPresenter = MediaUploadPresenter(talkCtx, pref)
        when(type){
            0 ->
                // image upload
                mediaPresenter?.uploadGaleMediaTalk(uri, 0, this)
            1 ->
                // video upload
                mediaPresenter?.uploadGaleMediaTalk(uri, 1, this)
        }
    }

    // Upload media from camera
    override fun upCamMedia(uri: Uri, type: Int) {
        mediaPresenter = MediaUploadPresenter(talkCtx, pref)
        when(type){
            0 ->
                mediaPresenter?.uploadCamMediaTalk(uri, 0, this)
            1 ->
                mediaPresenter?.uploadCamMediaTalk(uri, 1, this)
        }
    }


    override fun fetchTalk(toUserId: Int, howToReq: Int) {
        touserId = toUserId
        talkHandler = TalkHandler(pref.getToken(talkCtx), toUserId, 0 , this)
        talkHandler?.fetchTalkAction(howToReq)
    }


    // REALM CONFIG
    private fun initRealm(): Realm {
        Realm.init(talkCtx)
        val config = RealmConfiguration.Builder()
                .name("talk.realm").build()
        return Realm.getInstance(config)
    }

    private fun checkData(talkitem: List<TalkItem>){
        val realm = initRealm()
        var item: TalkItemRealm? = null
        for(i in talkitem.indices){
           item =  realm.where(TalkItemRealm::class.java)
                   .equalTo("messageId", talkitem[i].messageId)
                   .findFirst()
        }
        if(item == null){
            println("ok put the new object mate")
        } else {
            println("theres some item here")
            println("message id ${item.messageId}")
        }
    }

    // saving data to local database from server
    private fun saveDataRealm(talkitem: List<TalkItem>){
       try{
           val realm = initRealm()
           realm.beginTransaction()
//           realm.delete(TalkItemRealm::class.java)
           for(i in talkitem.indices){
               for(j in talkitem.indices){
                   val talkRealm = realm.createObject(TalkItemRealm::class.java, talkitem[i].messageId)
                   talkRealm.messageId = talkitem[i].messageId
                   talkRealm.userId = talkitem[i].userId
                   talkRealm.message = talkitem[i].message
                   talkRealm.mediaId = talkitem[i].mediaId
                   talkRealm.mediaSize = talkitem[i].mediaSize
                   talkRealm.mediaUrl = talkitem[i].mediaUrl
                   talkRealm.mediaType = talkitem[i].mediaType
                   talkRealm.time = talkitem[i].time
                   talkRealm.messageKind = talkitem[i].messageKind
                   talkRealm.imageId = talkitem[i].imageId
                   talkRealm.imageUrl = talkitem[i].imageUrl
               }
           }
           realm.commitTransaction()

//           for(i in talkitem.indices){
//               val talk = realm.where(TalkItemRealm::class.java).equalTo("userId", touserId).findAll()
//               println("message id: ${talk[i].messageId}")
//           }

       } catch (e: Exception){
          e.stackTrace
           println("error save realm - talk presenter: ${e.message}")
       }
    }

    // convert from local database realm to list
    private fun fromRealmToList(talkRealm:List<TalkItemRealm>): List<TalkItem>{
        val talkitem: MutableList<TalkItem> = MutableList(talkRealm.size){ TalkItem()}

        for(i in talkRealm.indices){
            talkitem[i].talkId = talkRealm[i].talkId
            talkitem[i].messageId = talkRealm[i].messageId
            talkitem[i].userId = talkRealm[i].userId
            talkitem[i].message = talkRealm[i].message
            talkitem[i].mediaId = talkRealm[i].mediaId
            talkitem[i].mediaSize = talkRealm[i].mediaSize
            talkitem[i].mediaUrl = talkRealm[i].mediaUrl
            talkitem[i].mediaType = talkRealm[i].mediaType
            talkitem[i].time = talkRealm[i].time
            talkitem[i].messageKind = talkRealm[i].messageKind
            talkitem[i].imageId = talkRealm[i].imageId
            talkitem[i].imageUrl = talkRealm[i].imageUrl
//            println("lastlogin: " +listRealm[i].lastUpdateTime)
        }
        return talkitem
    }

    // init local variabel
    val pref: Preferences = Preferences()
    var talkHandler: TalkHandler? = null
    var sendMessageHandler: SendMessageHandler? = null
    var mediaPresenter: MediaUploadPresenter? = null
    var imageId: Int? = 0
    var videoId: Int? = 0
    var touserId: Int? = null
    var talkView = view
    var talkCtx = ctx


    // is success fetching data talk from server
    override fun isSuccessFetchTalk(data: TalkData) {
        if(data.status == 1){
            if(data.items != null){
                talkView.setRess(data.items!!)
                saveDataRealm(data.items!!)
            }
        } else {
            talkView.showError(data.errorData?.errorMessage!!)
        }
    }

}
