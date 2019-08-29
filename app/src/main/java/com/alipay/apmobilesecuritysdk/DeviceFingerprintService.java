package com.alipay.apmobilesecuritysdk;

import android.content.Context;
import com.alipay.apmobilesecuritysdk.face.APSecuritySdk.InitResultListener;
import com.alipay.apmobilesecuritysdk.face.APSecuritySdk.TokenResult;
import com.alipay.apmobilesecuritysdk.face.DeviceTokenBizID;
import java.util.Map;

public abstract class DeviceFingerprintService {
    public abstract String getApdidToken();

    public abstract String getApdidToken(DeviceTokenBizID deviceTokenBizID);

    public abstract String getSdkName();

    public abstract String getSdkVersion();

    public abstract TokenResult getTokenResult();

    public abstract TokenResult getTokenResult(DeviceTokenBizID deviceTokenBizID);

    public abstract String getUtdid();

    public abstract void initToken(int i, Map<String, String> map, InitResultListener initResultListener);

    public abstract void setContext(Context context);
}
