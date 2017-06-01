package jp.co.terraresta.androidlesson.data.model.talk;

import com.google.gson.annotations.Expose;

/**
 * Created by ooyama on 2017/05/26.
 */

public class TalkListItem {

    @Expose
    public int talkId;
    @Expose
    public int toUserId;
    @Expose
    public int messageId;
    @Expose
    public int userId;
    @Expose
    public String nickname;
    @Expose
    public int imageId;
    @Expose
    public String imageSize;
    @Expose
    public String imageUrl;
    @Expose
    public String message;
    @Expose
    public int mediaType;
    @Expose
    public int userStatus;
    @Expose
    public String time;
    @Expose
    public String lastUpdateTime;

    public int getTalkId() {
        return talkId;
    }

    public int getToUserId() {
        return toUserId;
    }

    public int getMessageId() {
        return messageId;
    }

    public int getUserId() {
        return userId;
    }

    public String getNickname() {
        return nickname;
    }

    public int getImageId() {
        return imageId;
    }

    public String getImageSize() {
        return imageSize;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getMessage() {
        return message;
    }

    public int getMediaType() {
        return mediaType;
    }

    public int getUserStatus() {
        return userStatus;
    }

    public String getTime() {
        return time;
    }

    public String getLastUpdateTime() {
        return lastUpdateTime;
    }
}
