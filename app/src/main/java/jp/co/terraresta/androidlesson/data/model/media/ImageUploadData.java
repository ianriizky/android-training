package jp.co.terraresta.androidlesson.data.model.media;

import com.google.gson.annotations.Expose;

import jp.co.terraresta.androidlesson.data.model.common.BaseResultData;

/**
 * Created by ooyama on 2017/05/29.
 */

public class ImageUploadData extends BaseResultData {

    @Expose
    public int imageId;

    public int getImageId() {
        return imageId;
    }
}
