package jp.co.terraresta.androidlesson.data.model.profile_feed;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import jp.co.terraresta.androidlesson.data.model.common.BaseResultData;

/**
 * Created by ooyama on 2017/05/26.
 */

public class ProfileFeedData extends BaseResultData {

    @Expose
    private String lastLoginTime;
    @SerializedName("items")
    @Expose
    private List<ProfileFeedItem> items = null;

    public String getLastLoginTime() {
        return lastLoginTime;
    }

    public List<ProfileFeedItem> getItems() {
        return items;
    }
}
