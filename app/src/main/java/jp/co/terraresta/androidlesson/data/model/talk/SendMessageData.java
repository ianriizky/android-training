package jp.co.terraresta.androidlesson.data.model.talk;

import com.google.gson.annotations.Expose;

import jp.co.terraresta.androidlesson.data.model.common.BaseResultData;

/**
 * Created by ooyama on 2017/05/26.
 */

public class SendMessageData extends BaseResultData {

    @Expose
    public int messageId;

    public int getMessageId() {
        return messageId;
    }
}
