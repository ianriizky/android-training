package jp.co.terraresta.androidlesson.data.model.common;

import com.google.gson.annotations.Expose;

/**
 * Created by ooyama on 2017/05/29.
 */

public class ErrorData {

    @Expose
    private int errorCode;
    @Expose
    private String errorTitle;
    @Expose
    private String errorMessage;


    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorTitle() {
        return errorTitle;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
