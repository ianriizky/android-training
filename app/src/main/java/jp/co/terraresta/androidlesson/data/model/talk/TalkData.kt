package jp.co.terraresta.androidlesson.data.model.talk

import com.google.gson.annotations.Expose

import jp.co.terraresta.androidlesson.data.model.common.BaseResultData

/**
 * Created by ooyama on 2017/05/26.
 */

class TalkData : BaseResultData() {

    @Expose
    var items: List<TalkItem>? = null
}
