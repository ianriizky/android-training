package jp.co.terraresta.androidlesson.data.model.talk;

import com.google.gson.annotations.Expose;

/**
 * Created by ooyama on 2017/05/26.
 */

public class TalkItem {

    @Expose
    public int talkId;
    @Expose
    public int messageId;
    @Expose
    public int userId;
    @Expose
    public String message;
    @Expose
    public int mediaId;
    @Expose
    public String mediaSize;
    @Expose
    public String mediaUrl;
    @Expose
    public int mediaType;
    @Expose
    public String time;
    @Expose
    public int messageKind;

    public int getTalkId() {
        return talkId;
    }

    public int getMessageId() {
        return messageId;
    }

    public int getUserId() {
        return userId;
    }

    public String getMessage() {
        return message;
    }

    public int getMediaId() {
        return mediaId;
    }

    public String getMediaSize() {
        return mediaSize;
    }

    public String getMediaUrl() {
        return mediaUrl;
    }

    public int getMediaType() {
        return mediaType;
    }

    public String getTime() {
        return time;
    }

    public int getMessageKind() {
        return messageKind;
    }
}
