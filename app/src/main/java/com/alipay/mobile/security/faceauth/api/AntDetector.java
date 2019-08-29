package com.alipay.mobile.security.faceauth.api;

import android.content.Context;
import android.graphics.Bitmap;
import com.alipay.mobile.security.faceauth.api.login.AntFaceLoginParameter;
import com.alipay.mobile.security.faceauth.api.sample.AntSampleFaceParameter;

public interface AntDetector {
    public static final int ACTION_ID_LOGIN = 65537;
    public static final int ACTION_ID_SAMPLE = 65536;
    public static final int ACTION_ID_TRY_LOGIN = 65538;
    public static final int APP_ID_ALIPAY = 1;
    public static final int APP_ID_BANK = 0;
    public static final int APP_ID_OTHER = 3;
    public static final int APP_ID_PRAISE = 2;
    public static final int COMMAND_CANCLE = 4099;
    public static final int COMMAND_VALIDATE_FAIL = 4100;
    public static final String EXT_KEY_AB_TEST = "abTest";
    public static final String EXT_KEY_APDID = "APDID";
    public static final String EXT_KEY_APPID = "appid";
    public static final String EXT_KEY_SCENEID = "SCENE_ID";
    public static final String EXT_KEY_UID = "userid";
    public static final String EXT_KEY_USER_NAME_HIDDEN = "userNameHidden";
    public static final String EXT_KEY_VTOKENID = "TOKEN_ID";
    public static final int SCENE_ID_LOGIN_REGIST = 131072;

    void auth(AntDetectParameter antDetectParameter, AntDetectCallback antDetectCallback);

    void cancle();

    int checkEnvironment(Context context);

    void command(int i);

    void destroy();

    void doCloseFaceService();

    FaceFrame doFaceQualityDetection(Bitmap bitmap);

    RESULT doFaceQualityDetection(YUVFrame yUVFrame, FaceCallback faceCallback);

    RESULT doInitFaceService();

    void login(AntFaceLoginParameter antFaceLoginParameter, AntDetectCallback antDetectCallback);

    void sample(AntSampleFaceParameter antSampleFaceParameter, AntDetectCallback antDetectCallback);
}
