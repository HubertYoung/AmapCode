package com.alibaba.analytics.core.selfmonitor;

import android.text.TextUtils;
import com.alibaba.analytics.core.Variables;
import com.alibaba.analytics.core.config.SystemConfigMgr;
import com.alibaba.analytics.core.config.SystemConfigMgr.IKVChangeListener;
import com.alibaba.analytics.core.config.UTDBConfigEntity;
import com.alibaba.analytics.core.config.UTSampleConfBiz;
import com.alibaba.analytics.core.db.Entity;
import com.alibaba.analytics.core.model.LogField;
import com.alibaba.analytics.core.sync.TnetUtil;
import com.alibaba.analytics.core.sync.UploadLogFromDB;
import com.alibaba.analytics.core.sync.UploadMgr;
import com.alibaba.analytics.core.sync.UrlWrapper;
import com.alibaba.analytics.utils.AppInfoUtil;
import com.alibaba.analytics.utils.Logger;
import com.alibaba.analytics.utils.TaskExecutor;
import com.alibaba.analytics.version.UTBuildInfo;
import com.alibaba.appmonitor.event.EventType;
import com.alibaba.appmonitor.offline.TempEvent;
import com.alibaba.appmonitor.sample.AMSamplingMgr;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.utils.AudioBenchmark;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.LogItem;
import com.alipay.mobile.h5container.api.H5Param;
import com.ut.mini.core.sign.IUTRequestAuthentication;
import com.ut.mini.core.sign.UTBaseRequestAuthentication;
import com.ut.mini.core.sign.UTSecuritySDKRequestAuthentication;
import com.ut.mini.core.sign.UTSecurityThridRequestAuthentication;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

public class SelfChecker implements IKVChangeListener {
    private static SelfChecker instance = new SelfChecker();

    public static SelfChecker getInstance() {
        return instance;
    }

    public void register() {
        SystemConfigMgr.getInstance().register("selfcheck", this);
    }

    public void onChange(final String str, final String str2) {
        Logger.e((String) "SelfChecker", "key", str, "value", str2);
        TaskExecutor.getInstance().schedule(null, new Runnable() {
            public void run() {
                SelfChecker.this.check(str, str2);
            }
        }, 5000);
    }

    public String check(String str, String str2) {
        JSONObject jSONObject = new JSONObject();
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(System.currentTimeMillis());
            jSONObject.put("current_time", sb.toString());
            StringBuilder sb2 = new StringBuilder();
            sb2.append(Variables.getInstance().isInit());
            jSONObject.put("is_init", sb2.toString());
            StringBuilder sb3 = new StringBuilder();
            sb3.append(UTBuildInfo.getInstance().getFullSDKVersion());
            jSONObject.put("sdk_version", sb3.toString());
            StringBuilder sb4 = new StringBuilder();
            sb4.append(Variables.getInstance().getAppkey());
            jSONObject.put("appkey", sb4.toString());
            StringBuilder sb5 = new StringBuilder();
            sb5.append(Variables.getInstance().getSecret());
            jSONObject.put("secret", sb5.toString());
            IUTRequestAuthentication requestAuthenticationInstance = Variables.getInstance().getRequestAuthenticationInstance();
            if (requestAuthenticationInstance == null) {
                jSONObject.put("security_mode", "-1");
            } else if (requestAuthenticationInstance instanceof UTBaseRequestAuthentication) {
                jSONObject.put("security_mode", "1");
            } else if (requestAuthenticationInstance instanceof UTSecuritySDKRequestAuthentication) {
                jSONObject.put("security_mode", "2");
            } else if (requestAuthenticationInstance instanceof UTSecurityThridRequestAuthentication) {
                jSONObject.put("security_mode", "3");
            }
            jSONObject.put("run_process", AppInfoUtil.getCurProcessName(Variables.getInstance().getContext()));
            jSONObject.put("ut_realtime_debug_switch", Variables.getInstance().isRealTimeDebug());
            jSONObject.put("ap_realtime_debug_switch", Variables.getInstance().isApRealTimeDebugging());
            jSONObject.put("ap_sampling_seed", AMSamplingMgr.getInstance().getSamplingSeed());
            jSONObject.put("upload_interval", UploadMgr.getInstance().getCurrentUploadInterval());
            samplingCheck(jSONObject, str2);
            boolean hasSuccess = UploadLogFromDB.getInstance().hasSuccess();
            jSONObject.put(AudioBenchmark.KEY_UPLOAD_SUCCESS, hasSuccess);
            StringBuilder sb6 = new StringBuilder();
            sb6.append(UploadMgr.getInstance().getCurrentMode());
            jSONObject.put("upload_mode", sb6.toString());
            boolean isHttpService = Variables.getInstance().isHttpService();
            jSONObject.put("tnet_degrade", isHttpService);
            if (isHttpService) {
                jSONObject.put("tnet_error_code", TnetUtil.mErrorCode);
            }
            if (!hasSuccess) {
                jSONObject.put("http_error_code", UrlWrapper.mErrorCode);
            }
            List<? extends Entity> find = Variables.getInstance().getDbMgr().find(UTDBConfigEntity.class, null, null, -1);
            if (find != null) {
                for (int i = 0; i < find.size(); i++) {
                    UTDBConfigEntity uTDBConfigEntity = (UTDBConfigEntity) find.get(i);
                    StringBuilder sb7 = new StringBuilder("entity.getGroupname()");
                    sb7.append(uTDBConfigEntity.getGroupname());
                    jSONObject.put(sb7.toString(), uTDBConfigEntity.getConfContent());
                }
            }
        } catch (Throwable th) {
            try {
                StringBuilder sb8 = new StringBuilder();
                sb8.append(th.getLocalizedMessage());
                jSONObject.put("resport_error", sb8.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        String jSONObject2 = jSONObject.toString();
        report(jSONObject2);
        return jSONObject2;
    }

    private void samplingCheck(JSONObject jSONObject, String str) {
        CharSequence charSequence;
        String str2;
        String str3;
        String str4;
        try {
            if (!TextUtils.isEmpty(str)) {
                try {
                    JSONObject optJSONObject = new JSONObject(str).optJSONObject("cp");
                    String str5 = null;
                    if (optJSONObject != null) {
                        JSONObject optJSONObject2 = optJSONObject.optJSONObject(H5Param.ANTI_PHISHING);
                        if (optJSONObject2 != null) {
                            str3 = optJSONObject2.optString("type");
                            str2 = optJSONObject2.optString(TempEvent.TAG_MODULE);
                            str4 = optJSONObject2.optString("point");
                        } else {
                            str4 = null;
                            str3 = null;
                            str2 = null;
                        }
                        JSONObject optJSONObject3 = optJSONObject.optJSONObject(LogItem.MM_C03_K4_UPLOAD_TYPE);
                        if (optJSONObject3 != null) {
                            str5 = optJSONObject3.optString("eventId");
                            charSequence = optJSONObject3.optString("arg1");
                        } else {
                            charSequence = null;
                        }
                    } else {
                        charSequence = null;
                        str4 = null;
                        str3 = null;
                        str2 = null;
                    }
                    if (!TextUtils.isEmpty(str3) && !TextUtils.isEmpty(str2) && !TextUtils.isEmpty(str4)) {
                        jSONObject.put("ap_sampling_result", AMSamplingMgr.getInstance().checkSampled(EventType.getEventTypeByNameSpace(str3), str2, str4));
                    }
                    if (!TextUtils.isEmpty(str5)) {
                        HashMap hashMap = new HashMap();
                        hashMap.put(LogField.EVENTID.toString(), str5);
                        if (!TextUtils.isEmpty(charSequence)) {
                            hashMap.put(LogField.ARG1.toString(), charSequence);
                        }
                        jSONObject.put("ut_sampling_result", UTSampleConfBiz.getInstance().isSampleSuccess(hashMap));
                    }
                } catch (Throwable unused) {
                }
            }
        } catch (Throwable unused2) {
        }
    }

    public void report(String str) {
        File externalFilesDir = Variables.getInstance().getContext().getExternalFilesDir("logs");
        if (externalFilesDir != null) {
            StringBuilder sb = new StringBuilder();
            sb.append(externalFilesDir.getAbsolutePath());
            sb.append(File.separator);
            sb.append("analytics.log");
            File file = new File(sb.toString());
            if (file.exists()) {
                file.delete();
            } else {
                try {
                    if (!externalFilesDir.exists()) {
                        file.getParentFile().mkdirs();
                    }
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                fileOutputStream.write(str.getBytes());
                fileOutputStream.flush();
                fileOutputStream.close();
            } catch (Throwable unused) {
                Logger.e();
            }
        }
    }
}
