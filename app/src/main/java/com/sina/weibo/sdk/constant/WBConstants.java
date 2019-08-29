package com.sina.weibo.sdk.constant;

public class WBConstants {
    public static final String ACTION_WEIBO_REGISTER = "com.sina.weibo.sdk.Intent.ACTION_WEIBO_REGISTER";
    public static final String ACTION_WEIBO_SDK_PERMISSION = "com.sina.weibo.permission.WEIBO_SDK_PERMISSION";
    public static final String ACTIVITY_REQ_SDK = "com.sina.weibo.sdk.action.ACTION_SDK_REQ_ACTIVITY";
    public static final String ACTIVITY_WEIBO = "com.sina.weibo.sdk.action.ACTION_WEIBO_ACTIVITY";
    public static final String AID = "aid";
    public static final String AUTH_PARAMS_AID = "aid";
    public static final String AUTH_PARAMS_CLIENT_ID = "client_id";
    public static final String AUTH_PARAMS_KEY_HASH = "key_hash";
    public static final String AUTH_PARAMS_PACKAGE_NAME = "packagename";
    public static final String AUTH_PARAMS_REDIRECT_URL = "redirect_uri";
    public static final String AUTH_PARAMS_RESPONSE_TYPE = "response_type";
    public static final String AUTH_PARAMS_SCOPE = "scope";
    public static final String AUTH_PARAMS_VERSION = "version";
    public static final int COMMAND_SSO = 3;
    public static final int COMMAND_TO_WEIBO = 1;
    public static final String COMMAND_TYPE_KEY = "_weibo_command_type";
    public static final int SDK_ACTIVITY_FOR_RESULT_CODE = 765;
    public static final int SDK_NEW_PAY_VERSION = 1920;
    public static final String SHARE_CALLBACK_ID = "callbackId";
    public static final String SHARE_START_ACTION = "startAction";
    public static final String SHARE_START_ACTIVITY = "startActivity";
    public static final String SHARE_START_FLAG = "startFlag";
    public static final String SHARE_START_GOTO_ACTIVITY = "gotoActivity";
    public static final String SHARE_START_PACKAGE = "startPackage";
    public static final String SHARE_START_RESULT_FLAG = "resultDataFlag";
    public static final String SIGN = "_weibo_sign";
    public static final String SSO_APP_KEY = "appKey";
    public static final String SSO_KEY_HASH = "key_hash";
    public static final String SSO_PACKAGE_NAME = "packagename";
    public static final String SSO_REDIRECT_URL = "redirectUri";
    public static final String SSO_USER_SCOPE = "scope";
    public static final String THIRD_APP_IS_FIRST = "third_app_is_first_tag";
    public static final String THIRD_APP_IS_FIRST_KEY = "third_app_is_first_key";
    public static final String TRAN = "_weibo_transaction";
    public static final String TRANS_PROGRESS_COLOR = "progressColor";
    public static final String TRANS_PROGRESS_ID = "progressId";
    public static final int WEIBO_FLAG_SDK = 538116905;
    public static final String WEIBO_SIGN = "18da2bf10352443a00a5e046d9fca6bd";

    public interface Base {
        public static final String APP_KEY = "_weibo_appKey";
        public static final String APP_PKG = "_weibo_appPackage";
        public static final String SDK_VER = "_weibo_sdkVersion";
    }

    public interface ErrorCode {
        public static final int ERR_CANCEL = 1;
        public static final int ERR_FAIL = 2;
        public static final int ERR_OK = 0;
    }

    public interface Msg {
        public static final String IDENTIFY = "_weibo_message_identify";
        public static final String IMAGE = "_weibo_message_image";
        public static final String IMAGE_EXTRA = "_weibo_message_image_extra";
        public static final String MEDIA = "_weibo_message_media";
        public static final String MEDIA_EXTRA = "_weibo_message_media_extra";
        public static final String MULTI_IMAGE = "_weibo_message_multi_image";
        public static final String SHEAR_TYPE = "_weibo_message_type";
        public static final String STORY = "_weibo_message_stroy";
        public static final String TEXT = "_weibo_message_text";
        public static final String TEXT_EXTRA = "_weibo_message_text_extra";
        public static final String VIDEO_SOURCE = "_weibo_message_video_source";
    }

    public interface Response {
        public static final String ERRCODE = "_weibo_resp_errcode";
        public static final String ERRMSG = "_weibo_resp_errstr";
    }

    public interface SDK {
        public static final String FLAG = "_weibo_flag";
    }
}
