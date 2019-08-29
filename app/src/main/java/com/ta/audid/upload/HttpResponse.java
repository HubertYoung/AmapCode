package com.ta.audid.upload;

import android.text.TextUtils;
import com.alipay.mobile.mrtc.api.wwj.StreamerConstants;
import com.ta.audid.utils.MD5Utils;
import com.ta.audid.utils.UtdidLogger;
import com.ta.utdid2.android.utils.Base64;

public class HttpResponse {
    public byte[] data = null;
    public int httpResponseCode = -1;
    public long rt = 0;
    public String signature = "";
    public long timestamp = 0;

    public static boolean checkSignature(String str, String str2) {
        try {
            if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2)) {
                UtdidLogger.sd("", "result", str, StreamerConstants.DEFAULT_SIGNATURE, str2);
                if (str2.equals(Base64.encodeToString(MD5Utils.getHmacMd5Hex(str).getBytes(), 2))) {
                    UtdidLogger.d((String) "", "signature is ok");
                    return true;
                }
                UtdidLogger.d((String) "", "signature is error");
            }
        } catch (Exception e) {
            UtdidLogger.d((String) "", e);
        }
        return false;
    }
}
