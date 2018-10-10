package jp.co.terraresta.androidlesson.data.model.talk

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class TalkItemRealm: RealmObject() {

    @PrimaryKey
    var talkId: Int = 0
    var messageId: Int = 0
    var userId: Int = 0
    var message: String? = null
    var mediaId: Int = 0
    var mediaSize: String? = null
    var mediaUrl: String? = null
    var mediaType: Int = 0
    var time: String? = null
    var messageKind: Int = 0
    var imageId: Int = 0
    var imageUrl: String? = null
}