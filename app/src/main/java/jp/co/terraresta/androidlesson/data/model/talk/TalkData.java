package jp.co.terraresta.androidlesson.data.model.talk;

import com.google.gson.annotations.Expose;

import java.util.List;

import jp.co.terraresta.androidlesson.data.model.common.BaseResultData;

/**
 * Created by ooyama on 2017/05/26.
 */

public class TalkData extends BaseResultData {

    @Expose
    public List<TalkItem> items = null;

    public List<TalkItem> getItems() {
        return items;
    }
}
