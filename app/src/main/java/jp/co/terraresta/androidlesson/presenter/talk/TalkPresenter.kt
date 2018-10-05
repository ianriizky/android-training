package jp.co.terraresta.androidlesson.presenter.talk

import android.content.Context
import android.net.Uri
import jp.co.terraresta.androidlesson.common.Preferences
import jp.co.terraresta.androidlesson.data.handler.talk.SendMessageHandler
import jp.co.terraresta.androidlesson.data.handler.talk.TalkHandler
import jp.co.terraresta.androidlesson.data.model.talk.SendMessageData
import jp.co.terraresta.androidlesson.data.model.talk.TalkData
import jp.co.terraresta.androidlesson.presenter.media.MediaUploadPresenter
import jp.co.terraresta.androidlesson.view.activity.talk.TalkActivity

/**
 * Created by ooyama on 2017/05/29.
 */

class TalkPresenter(ctx: Context, view: TalkActivity): TalkContract.Presenter{
    override fun uploadMedia(mediaId: Int, type: Int) {
        sendMessageHandler = SendMessageHandler(pref.getToken(talkCtx), touserId!!, "", this)
        if(type == 0){
            sendMessageHandler?.sendMessageAction(videoId!!, mediaId)
        } else {
            sendMessageHandler?.sendMessageAction(mediaId, imageId!!)
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

    override fun upGaleMedia(uri: Uri, type: Int) {
        mediaPresenter = MediaUploadPresenter(talkCtx, pref)
        when(type){
            0 ->
                mediaPresenter?.uploadGaleMediaTalk(uri, 0, this)
            1 ->
                mediaPresenter?.uploadGaleMediaTalk(uri, 1, this)
        }
    }
    override fun upCamMedia(uri: Uri, type: Int) {
        mediaPresenter = MediaUploadPresenter(talkCtx, pref)
        when(type){
            0 ->
                mediaPresenter?.uploadCamMediaTalk(uri, 0, this)
            1 ->
                mediaPresenter?.uploadCamMediaTalk(uri, 1, this)
        }
    }

    override fun fetchTalkOld(howToReq: Int) {
        talkHandler?.fetchTalkAction(howToReq)
    }

    override fun fetchTalk(toUserId: Int, howToReq: Int) {
        touserId = toUserId
        talkHandler = TalkHandler(pref.getToken(talkCtx), toUserId, 0 , this)
        talkHandler?.fetchTalkAction(howToReq)
    }

    val pref: Preferences = Preferences()
    var talkHandler: TalkHandler? = null
    var sendMessageHandler: SendMessageHandler? = null
    var mediaPresenter: MediaUploadPresenter? = null
    var imageId: Int? = 0
    var videoId: Int? = 0
    var touserId: Int? = null
    var talkView = view
    var talkCtx = ctx

    override fun isSuccessFetchTalk(data: TalkData) {
        if(data.status == 1){
            if(data.items != null){
                talkView.setRess(data.items!!)
            }
        } else {
        }
    }

}
