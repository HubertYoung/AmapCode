package com.uc.webview.export.internal.setup;

import android.webkit.ValueCallback;
import com.alipay.mobile.common.logging.api.LogCategory;
import com.amap.bundle.cloudconfig.appinit.FunctionSupportConfiger;
import com.uc.webview.export.CDParamKeys;
import com.uc.webview.export.extension.UCCore;
import com.uc.webview.export.internal.interfaces.IWaStat;
import com.uc.webview.export.internal.interfaces.IWaStat.WaStat;
import com.uc.webview.export.internal.utility.Log;
import com.uc.webview.export.internal.utility.g;
import com.uc.webview.export.internal.utility.j;

/* compiled from: ProGuard */
public class bf extends bw {
    /* access modifiers changed from: private */
    public static String a = "bf";
    private static long b = 1;
    private static long c = 2;
    private static long e = 4;
    private static long f = 8;
    private static long g = 16;

    public void run() {
        long j;
        long j2 = c;
        try {
            WaStat.stat((String) IWaStat.SHARE_CORE_SEARCH_CORE_FILE_TASK_RUN);
            if (!"0".equals((String) UCCore.getGlobalOption(UCCore.PROCESS_PRIVATE_DATA_DIR_SUFFIX_OPTION))) {
                Log.d(a, ".run stat: ".concat(String.valueOf(g)));
                return;
            }
            ((t) ((t) ((t) onEvent((String) "success", (ValueCallback<CALLBACK_TYPE>) new bj<CALLBACK_TYPE>(this))).onEvent((String) LogCategory.CATEGORY_EXCEPTION, (ValueCallback<CALLBACK_TYPE>) new bi<CALLBACK_TYPE>(this))).onEvent((String) "setup", (ValueCallback<CALLBACK_TYPE>) new bh<CALLBACK_TYPE>(this))).onEvent((String) FunctionSupportConfiger.SWITCH_TAG, (ValueCallback<CALLBACK_TYPE>) new bg<CALLBACK_TYPE>(this));
            String f2 = g.f();
            Log.d(a, ".run sdCoreDecFilePath: ".concat(String.valueOf(f2)));
            if (!j.a(f2)) {
                j = e;
                try {
                    Integer num = (Integer) this.mOptions.get(UCCore.OPTION_VERIFY_POLICY);
                    String param = UCCore.getParam(CDParamKeys.CD_KEY_SHARE_CORE_CLIENT_VERIFY_POLICY);
                    if (CDParamKeys.CD_VALUE_VERIFY_ALL_HASH_ASYNC.equals(param)) {
                        num = Integer.valueOf(num.intValue() | UCCore.VERIFY_POLICY_ALL_FULL_HASH);
                    } else if (CDParamKeys.CD_VALUE_VERIFY_ALL_HASH_SYNC.equals(param)) {
                        num = Integer.valueOf((num.intValue() | UCCore.VERIFY_POLICY_ALL_FULL_HASH) & Integer.MAX_VALUE);
                    }
                    WaStat.stat((String) IWaStat.SHARE_CORE_SEARCH_CORE_FILE_SDCARD);
                    Object[] objArr = {this.mCallbacks};
                    this.mCallbacks = null;
                    resetCrashFlag();
                    ((t) ((t) ((t) ((t) ((t) ((t) ((t) ((t) ((t) ((t) ((t) ((t) new n().invoke(10001, this)).invoke(10002, objArr)).setOptions(this.mOptions)).setup((String) UCCore.OPTION_VERIFY_POLICY, (Object) num)).setup((String) UCCore.OPTION_DEX_FILE_PATH, (Object) null)).setup((String) UCCore.OPTION_SO_FILE_PATH, (Object) null)).setup((String) UCCore.OPTION_RES_FILE_PATH, (Object) null)).setup((String) UCCore.OPTION_UCM_CFG_FILE, (Object) null)).setup((String) UCCore.OPTION_UCM_KRL_DIR, (Object) null)).setup((String) UCCore.OPTION_UCM_ZIP_FILE, (Object) f2)).setup((String) "scst_flag", (Object) Boolean.TRUE)).setup((String) UCCore.OPTION_ENABLE_LOAD_CLASS, (Object) Boolean.FALSE)).start();
                    Log.d(a, ".run stat: ".concat(String.valueOf(j)));
                } catch (Throwable th) {
                    th = th;
                    Log.d(a, ".run stat: ".concat(String.valueOf(j)));
                    throw th;
                }
            } else {
                long j3 = f;
                try {
                    WaStat.stat((String) IWaStat.SHARE_CORE_SEARCH_CORE_FILE_EXCEPTION);
                    StringBuilder sb = new StringBuilder();
                    sb.append(a);
                    sb.append(" not found uc core");
                    throw new UCSetupException(3035, sb.toString());
                } catch (Throwable th2) {
                    j = j3;
                    th = th2;
                    Log.d(a, ".run stat: ".concat(String.valueOf(j)));
                    throw th;
                }
            }
        } catch (Throwable th3) {
            th = th3;
            j = j2;
            Log.d(a, ".run stat: ".concat(String.valueOf(j)));
            throw th;
        }
    }
}
