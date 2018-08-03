package jp.co.terraresta.androidlesson.data.model.profile_feed

import com.google.gson.annotations.Expose

import jp.co.terraresta.androidlesson.data.model.common.BaseResultData

/**
 * Created by ooyama on 2017/05/26.
 */

class ProfileFeedData : BaseResultData() {

    @Expose
    val lastLoginTime: String? = null
    @Expose
    val items: List<ProfileFeedItem>? = null
}
