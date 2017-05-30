package jp.co.terraresta.androidlesson.data.model.common;

import com.google.gson.annotations.Expose;

/**
 * Created by ooyama on 2017/05/26.
 */

public class BaseResultData {

    @Expose
    private int statusCode;
    @Expose
    private ErrorData errorData;

    public int getStatusCode() {
        return statusCode;
    }

    public ErrorData getErrorData() {
        return errorData;
    }
}
