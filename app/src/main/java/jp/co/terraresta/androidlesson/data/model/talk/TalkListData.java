package jp.co.terraresta.androidlesson.data.model.talk;

import com.google.gson.annotations.Expose;

import java.util.List;

import jp.co.terraresta.androidlesson.data.model.common.BaseResultData;

/**
 * Created by ooyama on 2017/05/26.
 */

public class TalkListData extends BaseResultData {

    @Expose
    public List<TalkListItem> items = null;

    public List<TalkListItem> getItems() {
        return items;
    }
}
