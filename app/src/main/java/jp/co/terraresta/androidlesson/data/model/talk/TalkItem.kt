package jp.co.terraresta.androidlesson.data.model.talk

import com.google.gson.annotations.Expose

/**
 * Created by ooyama on 2017/05/26.
 */

class TalkItem {

    @Expose
    var talkId: Int = 0
    @Expose
    var messageId: Int = 0
    @Expose
    var userId: Int = 0
    @Expose
    var message: String? = null
    @Expose
    var mediaId: Int = 0
    @Expose
    var mediaSize: String? = null
    @Expose
    var mediaUrl: String? = null
    @Expose
    var mediaType: Int = 0
    @Expose
    var time: String? = null
    @Expose
    var messageKind: Int = 0
}
