package jp.co.terraresta.androidlesson.common;

import java.util.regex.Pattern;

/**
 * Created by ooyama on 2017/05/24.
 */

public class Constants {

    // TODO:利用規約ページを追記。
    public static final String WEB_INFO_PAGE_TERMS_SERVICE = "";

    public static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9\\._\\-\\+]+@[a-zA-Z0-9_\\-]+\\.[a-zA-Z\\.]+[a-zA-Z]$");
    public static final Pattern PASSWORD_PATTERN = Pattern.compile("[^a-zA-Z0-9._-]");

    // NOTE:通常はドメインを指定してください。
    public static final String SERVER_DOMAIN = "52.193.52.243";

    // APIコントローラ名称
    public static final String API_CTRL_NAME_SIGN_UP = "SignUpCtrl";
    public static final String API_CTRL_NAME_LOGIN = "LoginCtrl";
    public static final String API_CTRL_NAME_PROFILE_FEED = "ProfileFeedCtrl";
    public static final String API_CTRL_NAME_PROFILE = "ProfileCtrl";
    public static final String API_CTRL_NAME_TALK = "TalkCtrl";
    public static final String API_CTRL_NAME_MEDIA = "MediaCtrl";
    public static final String API_CTRL_NAME_ACCOUNT = "AccountCtrl";

    // APIアクション名称
    public static final String API_ACTION_NAME_SIGN_UP = "SignUp";
    public static final String API_ACTION_NAME_LOGIN = "Login";
    public static final String API_ACTION_NAME_PROFILE_FEED = "ProfileFeed";
    public static final String API_ACTION_NAME_PROFILE_DISPLAY = "ProfileDisplay";
    public static final String API_ACTION_NAME_TALK_LIST = "TalkList";
    public static final String API_ACTION_NAME_TALK = "Talk";
    public static final String API_ACTION_NAME_SEND_MESSAGE = "SendMessage";
    public static final String API_ACTION_NAME_IMAGE_UPLOAD = "ImageUpload";
    public static final String API_ACTION_NAME_VIDEO_UPLOAD = "VideoUpload";
    public static final String API_ACTION_NAME_PROFILE_EDIT = "ProfileEdit";
    public static final String API_ACTION_NAME_DELETE_ACCOUNT = "DeleteAccount";

    // APIリクエストパラメータ
    public static final String REQUEST_NAME_LOGIN_ID = "login_id";
    public static final String REQUEST_NAME_PASSWORD = "password";
    public static final String REQUEST_NAME_NICKNAME = "nickname";
    public static final String REQUEST_NAME_LANGUAGE = "language";
    public static final String REQUEST_NAME_ACCESS_TOKEN = "access_token";
    public static final String REQUEST_NAME_LAST_LOGIN_TIME = "last_login_time";
    public static final String REQUEST_NAME_USER_ID = "user_id";
    public static final String REQUEST_NAME_LAST_UPDATE_TIME = "last_update_time";
    public static final String REQUEST_NAME_TO_USER_ID = "to_user_id";
    public static final String REQUEST_NAME_BORDER_MESSAGE_ID = "border_message_id";
    public static final String REQUEST_NAME_HOW_TO_REQUEST = "how_to_request";
    public static final String REQUEST_NAME_MESSAGE = "message";
    public static final String REQUEST_NAME_IMAGE_ID = "image_id";
    public static final String REQUEST_NAME_VIDEO_ID = "video_id";
    public static final String REQUEST_NAME_LOCATION_FLAG = "location_flag";
    public static final String REQUEST_NAME_DATA = "data";
    public static final String REQUEST_NAME_BIRTHDAY = "birthday";
    public static final String REQUEST_NAME_RESIDENCE = "residence";
    public static final String REQUEST_NAME_JOB = "job";
    public static final String REQUEST_NAME_PERSONALITY = "personality";
    public static final String REQUEST_NAME_HOBBY = "hobby";
    public static final String REQUEST_NAME_ABOUT_ME = "about_me";

}
