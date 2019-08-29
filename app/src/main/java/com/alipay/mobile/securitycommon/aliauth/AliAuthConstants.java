package com.alipay.mobile.securitycommon.aliauth;

public final class AliAuthConstants {

    public static final class Config {
        public static final String CFG_GENERAL_AUTOLOGIN_ENABLE = "CFG_GENERAL_AUTOLOGIN_ENABLE";
    }

    public static final class Key {
        public static final String ACTION = "action";
        public static final String ALIPAY_UID = "alipayUid";
        public static final String AUTO_LOGIN_DOMAINS = "aliAutoLoginDomains";
        public static final String BIND_TAOBAO = "bindTaobao";
        public static final String BIND_TAOBAO_RES = "BindTaobaoRes";
        public static final String BIZ_SCENE = "bizScene";
        public static final String DELETE_ALI_LOGIN_COOKIE = "deleteAliLoginCookie";
        public static final String FLAG = "flag";
        public static final String FORCE_AUTH = "forceAuth";
        public static final String LOGIN_ID = "loginId";
        public static final String REDIRECT_URL = "redirectURL";
        public static final String RESULT_CODE = "resultCode";
        public static final String SAVE_ALI_LOGIN_COOKIE = "saveAliLoginCookie";
        public static final String SHOW_UI = "showUi";
        public static final String SOURCE = "source";
        public static final String SOURCE_TYPE = "sourceType";
        public static final String TAOBAO_ID = "taobaoId";
        public static final String TARGET_URL = "targetUrl";
        public static final String USER_ID = "userId";
    }

    public static final class Result {
        public static final String BIND_ERROR = "1001";
        public static final String BIND_PHONE = "1002";
        public static final String PUNISH_ACCOUNT = "4004";
        public static final String RPC_EXCEPTION = "2003";
        public static final String RUBBISH_ACCOUNT = "4005";
        public static final String SUCCESS = "1000";
        public static final String TAOBAO_ACTIVE = "1003";
    }

    public static final class SourceType {
        public static final String H5 = "H5";
        public static final String NATIVE = "Native";
    }

    public static final class Value {
        public static final String CANCEL = "cancel";
        public static final String CONTINUE_LOGIN = "continueLogin";
        public static final String DEFAULT_DOMAIN = "http://www.taobao.com";
        public static final String FALSE = "false";
        public static final String NO = "NO";
        public static final String OK = "ok";
        public static final String QUIT = "quit";
        public static final String TRUE = "true";
        public static final String YES = "YES";
    }
}
