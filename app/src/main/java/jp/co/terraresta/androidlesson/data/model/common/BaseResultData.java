package jp.co.terraresta.androidlesson.data.model.common;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ooyama on 2017/05/26.
 */

public class BaseResultData {

    @Expose
    private int status;
    @SerializedName("error")
    @Expose
    private ErrorData errorData;

    public int getStatus() {
        return status;
    }

    public ErrorData getErrorData() {
        return errorData;
    }
}
