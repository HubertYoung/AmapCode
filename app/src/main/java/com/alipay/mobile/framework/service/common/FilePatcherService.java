package com.alipay.mobile.framework.service.common;

import android.content.Context;
import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;
import com.alipay.mobile.common.patch.PatchCallBack;
import com.alipay.mobile.framework.service.CommonService;

public abstract class FilePatcherService extends CommonService {
    public abstract void doPatch(Context context, String str, String str2, String str3, String str4, String str5, PatchCallBack patchCallBack);

    public abstract void doPatch(Context context, String str, String str2, String str3, String str4, String str5, String str6, PatchCallBack patchCallBack);

    public abstract boolean patcher(String str, String str2, String str3, String str4, String str5);

    public FilePatcherService() {
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }
}
