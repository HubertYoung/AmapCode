package com.alipay.mobile.security.bio.api;

public interface BioDetector {
    public static final int COMMAND_CANCLE = 4099;
    public static final int COMMAND_SERVER_CONTINUE = 4098;
    public static final int COMMAND_SERVER_FAIL = 8193;
    public static final int COMMAND_SERVER_RETRY = 8194;
    public static final int COMMAND_SERVER_SUCCESS = 4097;
    public static final int COMMAND_VALIDATE_FAIL = 4100;
    public static final String EXT_KEY_AMOUNT = "amount";
    public static final String EXT_KEY_APDID = "APDID";
    public static final String EXT_KEY_APPID = "appid";
    public static final String EXT_KEY_APPID_BUNDLE = "appid";
    public static final String EXT_KEY_AREA_CODE = "areaCode";
    public static final String EXT_KEY_AUTH_IN_BACKGROUND = "auth_in_background";
    public static final String EXT_KEY_AVATAR = "avatar";
    public static final String EXT_KEY_BISCFG_BUNDLE = "biscfg";
    public static final String EXT_KEY_BISTOKEN = "bis_token";
    public static final String EXT_KEY_BOX_FlOWID = "boxFlowID";
    public static final String EXT_KEY_BOX_ID = "boxID";
    public static final String EXT_KEY_BRAND_CODE = "brandCode";
    public static final String EXT_KEY_CARD_TYPE = "card_type";
    public static final String EXT_KEY_DEVICE_ID = "deviceId";
    public static final String EXT_KEY_DEVICE_MAC = "deviceMac";
    public static final String EXT_KEY_FACE_TOKEN = "ftoken";
    public static final String EXT_KEY_GEO = "geo";
    public static final String EXT_KEY_GOODS_COUNT = "goodsCount";
    public static final String EXT_KEY_HAS_OTHERS = "HAS_OTHERS";
    public static final String EXT_KEY_IS_MOCK = "mock";
    public static final String EXT_KEY_MACHINE_CODE = "machineCode";
    public static final String EXT_KEY_MERCHANT_ID = "merchantId";
    public static final String EXT_KEY_MODULE_DATA_BUNDLE = "viModuleData";
    public static final String EXT_KEY_PAGENUM = "pageNum";
    public static final String EXT_KEY_PARTNER_ID = "partnerId";
    public static final String EXT_KEY_REMOTE_LOG_ID = "remoteLogID";
    public static final String EXT_KEY_SCENEID = "SCENE_ID";
    public static final String EXT_KEY_SCENE_ID_BUNDLE = "sceneId";
    public static final String EXT_KEY_STORE_CODE = "storeCode";
    public static final String EXT_KEY_TOTAL_PAGE_NUM = "total_page_num";
    public static final String EXT_KEY_UID = "userid";
    public static final String EXT_KEY_UPLOAD_RESPONSE = "upload_response";
    public static final String EXT_KEY_USER_ID_BUNDLE = "USER_ID";
    public static final String EXT_KEY_VERIFYID = "verifyid";
    public static final String EXT_KEY_VTOKENID = "TOKEN_ID";
    public static final String EXT_KEY_WIFI_MAC = "wifimac";
    public static final String EXT_KEY_WIFI_NAME = "wifiname";
    public static final String EXT_KEY_WITHOUT_PAY = "withoutPay";

    void auth(BioParameter bioParameter, BioCallback bioCallback);

    void command(int i);

    void destroy();

    int preLoad();
}
