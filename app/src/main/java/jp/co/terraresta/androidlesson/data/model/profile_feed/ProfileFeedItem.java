package jp.co.terraresta.androidlesson.data.model.profile_feed;

import com.google.gson.annotations.Expose;

/**
 * Created by ooyama on 2017/05/26.
 */

public class ProfileFeedItem {

    @Expose
    private int userId;
    @Expose
    private String nickname;
    @Expose
    private int imageId;
    @Expose
    private String imageSize;
    @Expose
    private String imageUrl;

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
}
