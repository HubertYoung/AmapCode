package com.alipay.apmobilesecuritysdk.face;

import android.content.Context;
import com.alipay.apmobilesecuritysdk.DeviceFingerprintService;
import com.alipay.apmobilesecuritysdk.DeviceFingerprintServiceImpl;
import java.util.Map;

public class APSecuritySdk {
    private static volatile APSecuritySdk instance;
    private DeviceFingerprintService mDeviceFingerprintService;

    public interface InitResultListener {
        void onResult(TokenResult tokenResult);
    }

    public static class TokenResult {
        public String apdid;
        public String apdidToken;
        public String clientKey;
        public String umidToken;
    }

    private APSecuritySdk() {
    }

    public static APSecuritySdk getInstance(Context context) {
        if (instance == null) {
            synchronized (APSecuritySdk.class) {
                try {
                    if (instance == null) {
                        if (context == null) {
                            return null;
                        }
                        APSecuritySdk aPSecuritySdk = new APSecuritySdk();
                        aPSecuritySdk.mDeviceFingerprintService = new DeviceFingerprintServiceImpl();
                        aPSecuritySdk.mDeviceFingerprintService.setContext(context);
                        instance = aPSecuritySdk;
                    }
                }
            }
        }
        return instance;
    }

    public void initToken(int i, Map<String, String> map, InitResultListener initResultListener) {
        this.mDeviceFingerprintService.initToken(i, map, initResultListener);
    }

    public synchronized TokenResult getTokenResult() {
        try {
        }
        return this.mDeviceFingerprintService.getTokenResult();
    }

    public String getApdidToken() {
        return this.mDeviceFingerprintService.getApdidToken();
    }

    public TokenResult getTokenResult(DeviceTokenBizID deviceTokenBizID) {
        return this.mDeviceFingerprintService.getTokenResult(deviceTokenBizID);
    }

    public String getApdidToken(DeviceTokenBizID deviceTokenBizID) {
        return this.mDeviceFingerprintService.getApdidToken(deviceTokenBizID);
    }

    public String getSdkName() {
        return this.mDeviceFingerprintService.getSdkName();
    }

    public String getSdkVersion() {
        return this.mDeviceFingerprintService.getSdkVersion();
    }
}
