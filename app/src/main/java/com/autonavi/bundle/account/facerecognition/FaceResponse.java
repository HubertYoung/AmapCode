package com.autonavi.bundle.account.facerecognition;

import com.alipay.mobile.security.zim.api.ZIMCallback;
import com.alipay.mobile.security.zim.api.ZIMResponse;
import com.amap.bundle.logs.AMapLog;

@Deprecated
public class FaceResponse implements ZIMCallback {
    private static final String TAG = "FaceResponse";
    private a mCallback;
    private String mZimID;

    public FaceResponse(String str, a aVar) {
        this.mZimID = str;
        this.mCallback = aVar;
    }

    public boolean response(ZIMResponse zIMResponse) {
        if (this.mCallback == null) {
            AMapLog.error("basemap.account", TAG, "非法参数：请检查参数是否正确，构造方法第二个参数错误");
            return false;
        } else if (zIMResponse == null) {
            return false;
        } else {
            if (bno.a) {
                StringBuilder sb = new StringBuilder("verify response:");
                sb.append(zIMResponse.toString());
                AMapLog.debug("basemap.account", TAG, sb.toString());
            }
            int i = zIMResponse.code;
            if (i != 1000) {
                if (i != 1003) {
                    if (i == 2002) {
                        StringBuilder sb2 = new StringBuilder("网络连接失败:");
                        sb2.append(zIMResponse.subCode);
                        AMapLog.error("basemap.account", TAG, sb2.toString());
                        new StringBuilder("网络连接失败：").append(zIMResponse.subCode);
                    } else if (i != 2006) {
                        StringBuilder sb3 = new StringBuilder("网络连接失败:");
                        sb3.append(zIMResponse.subCode);
                        AMapLog.error("basemap.account", TAG, sb3.toString());
                        new StringBuilder("获取身份认证信息失败:").append(zIMResponse.subCode);
                    }
                }
                return true;
            }
            String str = zIMResponse.subCode;
            return true;
        }
    }
}
