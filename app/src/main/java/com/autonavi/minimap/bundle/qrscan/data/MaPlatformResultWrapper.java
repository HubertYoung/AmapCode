package com.autonavi.minimap.bundle.qrscan.data;

import com.alipay.android.phone.maplatformlib.MaPlatformResult;
import com.alipay.android.phone.maplatformlib.MaPlatformResult.RouteInfo;
import java.util.HashSet;

public class MaPlatformResultWrapper implements IScanResult {
    private static final HashSet<Integer> HTTP_ERROR_CODE_SET;
    private MaPlatformResult mMaPlatformResult;
    private String mOriginalText;

    static {
        HashSet<Integer> hashSet = new HashSet<>();
        HTTP_ERROR_CODE_SET = hashSet;
        hashSet.add(Integer.valueOf(0));
        HTTP_ERROR_CODE_SET.add(Integer.valueOf(1));
        HTTP_ERROR_CODE_SET.add(Integer.valueOf(2));
        HTTP_ERROR_CODE_SET.add(Integer.valueOf(3));
        HTTP_ERROR_CODE_SET.add(Integer.valueOf(4));
        HTTP_ERROR_CODE_SET.add(Integer.valueOf(5));
        HTTP_ERROR_CODE_SET.add(Integer.valueOf(6));
        HTTP_ERROR_CODE_SET.add(Integer.valueOf(7));
        HTTP_ERROR_CODE_SET.add(Integer.valueOf(8));
        HTTP_ERROR_CODE_SET.add(Integer.valueOf(9));
        HTTP_ERROR_CODE_SET.add(Integer.valueOf(10));
        HTTP_ERROR_CODE_SET.add(Integer.valueOf(11));
        HTTP_ERROR_CODE_SET.add(Integer.valueOf(12));
        HTTP_ERROR_CODE_SET.add(Integer.valueOf(13));
        HTTP_ERROR_CODE_SET.add(Integer.valueOf(14));
        HTTP_ERROR_CODE_SET.add(Integer.valueOf(15));
        HTTP_ERROR_CODE_SET.add(Integer.valueOf(16));
        HTTP_ERROR_CODE_SET.add(Integer.valueOf(20));
        HTTP_ERROR_CODE_SET.add(Integer.valueOf(1001));
        HTTP_ERROR_CODE_SET.add(Integer.valueOf(1002));
        HTTP_ERROR_CODE_SET.add(Integer.valueOf(2000));
        HTTP_ERROR_CODE_SET.add(Integer.valueOf(3000));
        HTTP_ERROR_CODE_SET.add(Integer.valueOf(3001));
        HTTP_ERROR_CODE_SET.add(Integer.valueOf(3002));
        HTTP_ERROR_CODE_SET.add(Integer.valueOf(4001));
        HTTP_ERROR_CODE_SET.add(Integer.valueOf(4002));
        HTTP_ERROR_CODE_SET.add(Integer.valueOf(4003));
        HTTP_ERROR_CODE_SET.add(Integer.valueOf(5000));
        HTTP_ERROR_CODE_SET.add(Integer.valueOf(6000));
        HTTP_ERROR_CODE_SET.add(Integer.valueOf(6001));
        HTTP_ERROR_CODE_SET.add(Integer.valueOf(6002));
        HTTP_ERROR_CODE_SET.add(Integer.valueOf(6003));
        HTTP_ERROR_CODE_SET.add(Integer.valueOf(6004));
        HTTP_ERROR_CODE_SET.add(Integer.valueOf(6005));
        HTTP_ERROR_CODE_SET.add(Integer.valueOf(6666));
        HTTP_ERROR_CODE_SET.add(Integer.valueOf(8001));
    }

    public MaPlatformResultWrapper(MaPlatformResult maPlatformResult) {
        this.mMaPlatformResult = maPlatformResult;
    }

    public int getErrorType() {
        if (this.mMaPlatformResult == null) {
            return 102;
        }
        if (!this.mMaPlatformResult.succeed) {
            return getErrorTypeBasedOnErrorCode(this.mMaPlatformResult.exception.errorCode);
        }
        return 100;
    }

    public int getErrorCode() {
        if (this.mMaPlatformResult == null) {
            return 0;
        }
        if (!this.mMaPlatformResult.succeed) {
            return this.mMaPlatformResult.exception.errorCode;
        }
        return 1000;
    }

    public String getText() {
        String str = null;
        if (this.mMaPlatformResult == null || !this.mMaPlatformResult.succeed) {
            if (getErrorType() == 101) {
                return this.mOriginalText;
            }
        } else if (this.mMaPlatformResult.routeInfo != null && this.mMaPlatformResult.routeInfo.size() > 0) {
            RouteInfo routeInfo = this.mMaPlatformResult.routeInfo.get(0);
            if (routeInfo != null) {
                str = routeInfo.uri;
            }
        }
        return str;
    }

    private static int getErrorTypeBasedOnErrorCode(int i) {
        if (i == 1000) {
            return 100;
        }
        return HTTP_ERROR_CODE_SET.contains(Integer.valueOf(i)) ? 101 : 102;
    }

    public void setOriginalText(String str) {
        this.mOriginalText = str;
    }
}
