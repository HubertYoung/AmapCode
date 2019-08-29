package com.ali.auth.third.core.ut;

import android.os.Build.VERSION;
import android.text.TextUtils;
import com.ali.auth.third.core.context.KernelContext;
import com.ali.auth.third.core.service.UserTrackerService;
import com.ali.auth.third.core.service.impl.CredentialManager;
import com.ali.auth.third.core.util.CommonUtils;
import com.ali.auth.third.core.util.JSONUtils;
import com.ali.auth.third.core.util.SystemUtils;
import com.alibaba.baichuan.android.trade.constants.UserTrackerConstants;
import com.alipay.mobile.monitor.track.spm.merge.MergeUtil;
import com.taobao.accs.common.Constants;
import java.util.Map;
import org.json.JSONObject;

public class UserTracer implements UserTrackerService {
    public static JSONObject getAppInfo() {
        String str;
        String str2;
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("diskSize", CommonUtils.getSDCardSize());
            jSONObject.put("sysSize", CommonUtils.getSystemSize());
            jSONObject.put("memorySize", CommonUtils.getTotalMemory());
            jSONObject.put("deviceId", CommonUtils.getAndroidId());
            jSONObject.put("uuid", KernelContext.UUID);
        } catch (Exception unused) {
        }
        try {
            jSONObject.put(Constants.KEY_OS_TYPE, "android");
            StringBuilder sb = new StringBuilder();
            sb.append(VERSION.SDK_INT);
            jSONObject.put("osVersion", sb.toString());
        } catch (Exception unused2) {
        }
        try {
            jSONObject.put("sdkName", "alibabauth_sdk");
            jSONObject.put(Constants.KEY_SDK_VERSION, KernelContext.sdkVersion);
            if (KernelContext.isMini) {
                str = UserTrackerConstants.SDK_TYPE;
                str2 = com.alibaba.analytics.core.Constants.SDK_TYPE;
            } else {
                str = UserTrackerConstants.SDK_TYPE;
                str2 = "std";
            }
            jSONObject.put(str, str2);
        } catch (Exception unused3) {
        }
        try {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(KernelContext.getApplicationContext().getPackageName());
            sb2.append(MergeUtil.SEPARATOR_KV);
            sb2.append(SystemUtils.getApkPublicKeyDigest());
            jSONObject.put("appId", sb2.toString());
            jSONObject.put("appKey", KernelContext.getAppKey());
            jSONObject.put("appName", CommonUtils.getAppLabel());
            jSONObject.put("appVersion", CommonUtils.getAndroidAppVersion());
        } catch (Exception unused4) {
        }
        try {
            jSONObject.put("openId", CredentialManager.INSTANCE.getInternalSession().user.openId);
        } catch (Exception unused5) {
        }
        return jSONObject;
    }

    public void init() {
    }

    public void send(String str, Map<String, String> map) {
        JSONObject appInfo = getAppInfo();
        try {
            if (!TextUtils.isEmpty(str)) {
                appInfo.put("action", str);
            }
            if (map != null) {
                appInfo.put("actionExt", JSONUtils.toJsonObject(map));
            }
            new Thread(new a(this, appInfo)).start();
        } catch (Exception unused) {
        }
    }
}
