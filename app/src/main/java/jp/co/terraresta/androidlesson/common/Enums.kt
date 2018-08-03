package jp.co.terraresta.androidlesson.common

/**
 * Created by ooyama on 2017/05/24.
 */

class Enums {

    /**
     * APIステータス
     */
    enum class ApiStatus {
        ERROR,
        OK
    }

    /**
     * エラーコード
     */
    enum class ErrorCode (val id: Int) {
        RESPONSE_ERROR(0),
        NO_DATA(1),
        INTERNAL_ERROR(2),
        NOT_ENOUGH_POINT(4),
        USER_AGE_NOT_CONFIRMED_YET(5),
        USER_BLOCKED(6),
        REGISTERED(19),
        MAIL_TRANSMISSION_RESULT(20),
        REGI_CHECK_PHONE_USER_ID(23),
        ACTION_ON_BLOCKED_USER(50),
        ADD_FAVORITE_FAILED(51),
        DELETE_FAVORITE_FAILED(52),
        SERVICE_BORDER_AGE(55),
        RE_REGISTRATION_PERMISSION_SPAN(56),
        NO_LATEST_MESSAGE(57);


        companion object {

            fun getById(id: Int): ErrorCode? {
                val types = ErrorCode.values()
                for (type in types) {
                    if (type.id == id) {
                        return type
                    }
                }
                return null
            }
        }
    }
}
