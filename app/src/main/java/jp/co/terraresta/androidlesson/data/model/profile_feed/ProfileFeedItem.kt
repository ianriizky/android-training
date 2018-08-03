package jp.co.terraresta.androidlesson.data.model.profile_feed

import com.google.gson.annotations.Expose

/**
 * Created by ooyama on 2017/05/26.
 */

class ProfileFeedItem {

    @Expose
    val userId: Int = 0
    @Expose
    val nickname: String? = null
    @Expose
    val imageId: Int = 0
    @Expose
    val imageSize: String? = null
    @Expose
    val imageUrl: String? = null
}
