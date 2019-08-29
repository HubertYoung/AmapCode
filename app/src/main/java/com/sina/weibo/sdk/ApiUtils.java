package com.sina.weibo.sdk;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.text.TextUtils;
import com.sina.weibo.sdk.constant.WBConstants;
import com.sina.weibo.sdk.utils.LogUtil;
import com.sina.weibo.sdk.utils.MD5;

public class ApiUtils {
    public static final int BUILD_INT = 10350;
    public static final int BUILD_INT_440 = 10355;
    public static final int BUILD_INT_VER_2_2 = 10351;
    public static final int BUILD_INT_VER_2_3 = 10352;
    public static final int BUILD_INT_VER_2_5 = 10353;
    public static final int STORY_INT_VER = 10772;
    private static final String TAG = "com.sina.weibo.sdk.ApiUtils";

    public static boolean validateWeiboSign(Context context, String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        try {
            return containSign(context.getPackageManager().getPackageInfo(str, 64).signatures, WBConstants.WEIBO_SIGN);
        } catch (NameNotFoundException unused) {
            return false;
        }
    }

    private static boolean containSign(Signature[] signatureArr, String str) {
        if (signatureArr == null || str == null) {
            return false;
        }
        for (Signature byteArray : signatureArr) {
            if (str.equals(MD5.hexdigest(byteArray.toByteArray()))) {
                LogUtil.d(TAG, "check pass");
                return true;
            }
        }
        return false;
    }
}
