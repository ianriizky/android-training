package jp.co.terraresta.androidlesson.data.model.talk

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class TalkListItemRealm: RealmObject(){
    @PrimaryKey
    var talkId: Int? = 0
    var toUserId: Int = 0
    var messageId: Int = 0
    var userId: Int = 0
    var nickname: String? = null
    var imageId: Int = 0
    var imageSize: String? = null
    var imageUrl: String? = null
    var message: String? = null
    var mediaType: Int = 0
    var userStatus: Int = 0
    var time: String? = null
    var lastUpdateTime: String? = null
}