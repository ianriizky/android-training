package jp.co.terraresta.androidlesson.data.model.media

import com.google.gson.annotations.Expose

import jp.co.terraresta.androidlesson.data.model.common.BaseResultData

/**
 * Created by ooyama on 2017/05/29.
 */

class ImageUploadData : BaseResultData() {

    @Expose
    var imageId: Int = 0
}
