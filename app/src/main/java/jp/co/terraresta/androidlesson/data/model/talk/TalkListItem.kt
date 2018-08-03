package jp.co.terraresta.androidlesson.data.model.talk

import com.google.gson.annotations.Expose

/**
 * Created by ooyama on 2017/05/26.
 */

class TalkListItem {

    @Expose
    var talkId: Int = 0
    @Expose
    var toUserId: Int = 0
    @Expose
    var messageId: Int = 0
    @Expose
    var userId: Int = 0
    @Expose
    var nickname: String? = null
    @Expose
    var imageId: Int = 0
    @Expose
    var imageSize: String? = null
    @Expose
    var imageUrl: String? = null
    @Expose
    var message: String? = null
    @Expose
    var mediaType: Int = 0
    @Expose
    var userStatus: Int = 0
    @Expose
    var time: String? = null
    @Expose
    var lastUpdateTime: String? = null
}
