package jp.co.terraresta.androidlesson.common

import java.util.regex.Pattern

/**
 * Created by ooyama on 2017/05/24.
 */

object Constants {

    // TODO:利用規約ページを追記。
    val WEB_INFO_PAGE_TERMS_SERVICE = "https://s3-ap-northeast-1.amazonaws.com/app-lesson-media/tos.html"

    val EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9\\._\\-\\+]+@[a-zA-Z0-9_\\-]+\\.[a-zA-Z\\.]+[a-zA-Z]$")
    val PASSWORD_PATTERN = Pattern.compile("^[a-zA-Z0-9]{4,10}\$")
    val PASSWORD_PATTERN_1 = Pattern.compile("[^a-zA-Z0-9._-]")
    val USERNAME_PATTERN = Pattern.compile("^[a-zA-Z0-9]{1,20}$")

    // NOTE:通常はドメインを指定してください。
    val SERVER_DOMAIN = "https://terraresta.com"

    // APIコントローラ名称
    val API_CTRL_NAME_SIGN_UP = "SignUpCtrl"
    val API_CTRL_NAME_LOGIN = "LoginCtrl"
    val API_CTRL_NAME_PROFILE_FEED = "ProfileFeedCtrl"
    val API_CTRL_NAME_PROFILE = "ProfileCtrl"
    val API_CTRL_NAME_TALK = "TalkCtrl"
    val API_CTRL_NAME_MEDIA = "MediaCtrl"
    val API_CTRL_NAME_ACCOUNT = "AccountCtrl"

    // APIアクション名称
    val API_ACTION_NAME_SIGN_UP = "SignUp"
    val API_ACTION_NAME_LOGIN = "Login"
    val API_ACTION_NAME_PROFILE_FEED = "ProfileFeed"
    val API_ACTION_NAME_PROFILE_DISPLAY = "ProfileDisplay"
    val API_ACTION_NAME_TALK_LIST = "TalkList"
    val API_ACTION_NAME_TALK = "Talk"
    val API_ACTION_NAME_SEND_MESSAGE = "SendMessage"
    val API_ACTION_NAME_IMAGE_UPLOAD = "ImageUpload"
    val API_ACTION_NAME_VIDEO_UPLOAD = "VideoUpload"
    val API_ACTION_NAME_PROFILE_EDIT = "ProfileEdit"
    val API_ACTION_NAME_DELETE_ACCOUNT = "DeleteAccount"

    // APIリクエストパラメータ
    val REQUEST_NAME_LOGIN_ID = "login_id"
    val REQUEST_NAME_PASSWORD = "password"
    val REQUEST_NAME_NICKNAME = "nickname"
    val REQUEST_NAME_LANGUAGE = "language"
    val REQUEST_NAME_ACCESS_TOKEN = "access_token"
    val REQUEST_NAME_LAST_LOGIN_TIME = "last_login_time"
    val REQUEST_NAME_USER_ID = "user_id"
    val REQUEST_NAME_LOCATION = "location"
    val REQUEST_NAME_LAST_UPDATE_TIME = "last_update_time"
    val REQUEST_NAME_TO_USER_ID = "to_user_id"
    val REQUEST_NAME_BORDER_MESSAGE_ID = "border_message_id"
    val REQUEST_NAME_HOW_TO_REQUEST = "how_to_request"
    val REQUEST_NAME_MESSAGE = "message"
    val REQUEST_NAME_IMAGE_ID = "image_id"
    val REQUEST_NAME_VIDEO_ID = "video_id"
    val REQUEST_NAME_DATA = "data"
    val REQUEST_NAME_BIRTHDAY = "birthday"
    val REQUEST_NAME_RESIDENCE = "residence"
    val REQUEST_NAME_JOB = "job"
    val REQUEST_NAME_PERSONALITY = "personality"
    val REQUEST_NAME_GENDER = "gender"
    val REQUEST_NAME_HOBBY = "hobby"
    val REQUEST_NAME_ABOUT_ME = "about_me"

    // common
    val REQUEST_CODE_CAMERA_ACTIVITY = 1034
    val REQUEST_CODE_GALLERY_ACTIVITY = 1035
    val REQUEST_CODE_WRITE_STORAGE = 1036
    val REQUEST_CODE_DELETE_PP = "delete_photo_profile"
    val REQUEST_CODE_UPDATE_PP = "update_photo_profile"
    val PACKAGE_NAME = "jp.co.terraresta.androidlesson.common"

    //Error signup
    val ERROR_INPUT_EMPTY = "This field required"
    val ERROR_EMAIL_FORMAT = "Your email is incorrect"
    val ERROR_USERNAME_FORMAT = "Username must not contain special character and maximum 20 character"
    val ERROR_PASSWORD_FORMAT = "Password must not contain special character (4 - 10 character)"

}
