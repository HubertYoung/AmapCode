package com.autonavi.minimap.ajx3.modules;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.statistics.LogManager;
import com.autonavi.ae.AEUtil;
import com.autonavi.minimap.ajx3.AjxInit;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.core.JsFunctionCallback;
import com.autonavi.minimap.ajx3.upgrade.Ajx3ActionLogUtil;
import com.autonavi.minimap.alc.model.ALCLogLevel;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@AjxModule("logService")
public final class ModuleLog extends AbstractModule {
    private static String APK_MD5 = null;
    public static final String MODULE_NAME = "logService";
    private JsFunctionCallback mErrorCallBack;

    @AjxMethod("logToFile")
    @Deprecated
    public final void logToFile(String str) {
    }

    public ModuleLog(IAjxContext iAjxContext) {
        super(iAjxContext);
    }

    @AjxMethod(invokeMode = "sync", value = "add")
    public final void add(String str, String str2, String str3) {
        if (TextUtils.isEmpty(str3)) {
            LogManager.actionLogV2(str, str2);
            return;
        }
        JSONObject jSONObject = null;
        try {
            jSONObject = new JSONObject(str3);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        LogManager.actionLogV2(str, str2, jSONObject);
    }

    @AjxMethod("addLogs")
    public final void addLogs(String str) {
        if (str != null) {
            try {
                JSONArray jSONArray = new JSONArray(str);
                for (int i = 0; i < jSONArray.length(); i++) {
                    try {
                        JSONObject jSONObject = jSONArray.getJSONObject(i);
                        LogManager.actionLogV2(jSONObject.optString("pageId"), jSONObject.optString("buttonId"), jSONObject.optJSONObject("data"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }
    }

    @AjxMethod("enterScene")
    public final void enterScene(String str, String str2) {
        if (!TextUtils.isEmpty(str)) {
            try {
                zt.a().a(str, true);
            } catch (Throwable unused) {
            }
        }
    }

    @AjxMethod("exitScene")
    public final void exitScene(String str, String str2) {
        if (!TextUtils.isEmpty(str)) {
            try {
                zt.a().a(str, false);
            } catch (Exception unused) {
            }
        }
    }

    @AjxMethod("bindErrorLogCallback")
    public final void bindJsErrorLogCallback(JsFunctionCallback jsFunctionCallback) {
        this.mErrorCallBack = jsFunctionCallback;
    }

    private String getApkMd5() {
        try {
            if (TextUtils.isEmpty(APK_MD5)) {
                String a = bmx.a(getNativeContext().getApplicationInfo().sourceDir);
                APK_MD5 = a.substring(a.indexOf("md5=") + 4, a.indexOf("[") - 1);
            }
        } catch (Exception unused) {
        }
        return APK_MD5;
    }

    public final void transJsErrorMsg(int i, String str, String str2) {
        getApkMd5();
        String generateJsErrorLog = Ajx3ActionLogUtil.generateJsErrorLog(i, str, str2, APK_MD5);
        if (!TextUtils.isEmpty(generateJsErrorLog)) {
            if (this.mErrorCallBack == null) {
                Ajx3ActionLogUtil.actionLogJsErrorCallbackNullMsg(generateJsErrorLog, str2);
                com.autonavi.minimap.ajx3.log.LogManager.jsRuntimeExceptionLog(str, str2);
                return;
            }
            this.mErrorCallBack.callback(generateJsErrorLog);
        }
    }

    @AjxMethod("logErrorMsg")
    public final void logJsErrorMsg(String str) {
        if (!TextUtils.isEmpty(str)) {
            if (AEUtil.isAjx3Debug) {
                AjxInit.showErrorMsg(null, str);
                com.autonavi.minimap.ajx3.log.LogManager.jsErrorLog(str);
            }
            getApkMd5();
            Ajx3ActionLogUtil.actionLogParseFailedWithJsMsg(str, APK_MD5);
            com.autonavi.minimap.ajx3.log.LogManager.jsErrorLog(str);
        }
    }

    private void debugShowErrorMsg(final String str) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            public void run() {
                Context nativeContext = ModuleLog.this.getNativeContext();
                if (nativeContext != null) {
                    Builder builder = new Builder(nativeContext);
                    builder.setTitle("Error!");
                    if (!TextUtils.isEmpty(str)) {
                        builder.setMessage(str);
                    }
                    builder.setNegativeButton(17039360, new OnClickListener() {
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if (dialogInterface != null) {
                                dialogInterface.dismiss();
                            }
                        }
                    });
                    builder.setPositiveButton(17039370, new OnClickListener() {
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if (dialogInterface != null) {
                                dialogInterface.dismiss();
                            }
                        }
                    });
                    AlertDialog create = builder.create();
                    create.setCancelable(false);
                    try {
                        create.show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    @AjxMethod("logP1ToServer")
    @Deprecated
    public final void logP1ToServer(String str, String str2, String str3) {
        cjy.a(ALCLogLevel.P1, (String) AMapLog.GROUP_AJX, (String) "D2", str, str2, str3);
    }

    @AjxMethod("logP2ToServer")
    @Deprecated
    public final void logP2ToServer(String str, String str2, String str3) {
        cjy.a(ALCLogLevel.P2, (String) AMapLog.GROUP_AJX, (String) "D2", str, str2, str3);
    }

    @AjxMethod("logP3ToServer")
    @Deprecated
    public final void logP3ToServer(String str, String str2, String str3) {
        cjy.a(ALCLogLevel.P3, (String) AMapLog.GROUP_AJX, (String) "D2", str, str2, str3);
    }

    @AjxMethod("logP4ToServer")
    @Deprecated
    public final void logP4ToServer(String str, String str2, String str3) {
        cjy.a(ALCLogLevel.P4, (String) AMapLog.GROUP_AJX, (String) "D2", str, str2, str3);
    }

    @AjxMethod("logP5ToServer")
    public final void logP5ToServer(String str, String str2, String str3) {
        cjy.a(ALCLogLevel.P5, (String) AMapLog.GROUP_AJX, (String) "D2", str, str2, str3);
    }

    @AjxMethod("logP6ToServer")
    public final void logP6ToServer(String str, String str2, String str3) {
        cjy.a(ALCLogLevel.P6, (String) "T11", (String) "D11", str, str2, str3);
    }

    @AjxMethod("h5OnlineLog")
    public final void h5OnlineLog(String str, String str2, String str3) {
        Ajx3ActionLogUtil.actionLogH5Online(str, str2, str3);
    }
}
