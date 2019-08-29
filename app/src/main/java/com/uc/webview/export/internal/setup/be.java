package com.uc.webview.export.internal.setup;

import android.webkit.ValueCallback;
import com.uc.webview.export.CDParamKeys;
import com.uc.webview.export.extension.UCCore;
import com.uc.webview.export.internal.interfaces.IWaStat;
import com.uc.webview.export.internal.interfaces.IWaStat.WaStat;
import com.uc.webview.export.internal.utility.Log;
import com.uc.webview.export.internal.utility.g;
import com.uc.webview.export.internal.utility.j;

/* compiled from: ProGuard */
public class be extends bw {
    public static String a = "o_local_dir";
    public static String b = "o_dec_file";
    public static String c = "e_delay_search_core_file";
    private static String e = "be";
    private static long f = 1;
    private static long g = 2;
    private static long h = 4;
    private static long i = 8;
    private static long j = 16;
    private static long k = 32;

    public void run() {
        long j2;
        long j3;
        long j4 = g;
        try {
            WaStat.stat((String) IWaStat.SHARE_CORE_SDCARD_SETUP_TASK_RUN);
            if (!j.a(getTotalLoadedUCM())) {
                WaStat.stat((String) IWaStat.SHARE_CORE_SDCARD_SETUP_TASK_HAD_INIT);
                Log.d(e, ".run stat: ".concat(String.valueOf(j4)));
                return;
            }
            String str = (String) getOption(a);
            if (j.a(str)) {
                str = g.e();
            }
            Log.d(e, ".run locationDecDir: ".concat(String.valueOf(str)));
            if (!j.a(str)) {
                j3 = h;
                try {
                    WaStat.stat((String) IWaStat.SHARE_CORE_SDCARD_SETUP_TASK_LOCATION_DEC);
                    ((t) ((t) ((t) ((t) ((t) setup((String) UCCore.OPTION_DEX_FILE_PATH, (Object) null)).setup((String) UCCore.OPTION_SO_FILE_PATH, (Object) null)).setup((String) UCCore.OPTION_RES_FILE_PATH, (Object) null)).setup((String) UCCore.OPTION_UCM_LIB_DIR, (Object) null)).setup((String) UCCore.OPTION_UCM_CFG_FILE, (Object) null)).setup((String) UCCore.OPTION_UCM_KRL_DIR, (Object) str);
                    super.run();
                } catch (Throwable th) {
                    th = th;
                }
            } else {
                ValueCallback valueCallback = (ValueCallback) invokeO(10007, c);
                Log.d(e, ".run delaySeareCoreFileCB: ".concat(String.valueOf(valueCallback)));
                if (valueCallback != null) {
                    try {
                        WaStat.stat((String) IWaStat.SHARE_CORE_SDCARD_SETUP_DELAY_SEARCH_CORE_FILE);
                        valueCallback.onReceiveValue(this);
                        StringBuilder sb = new StringBuilder();
                        sb.append(e);
                        sb.append(" delay search sdcard core file.");
                        throw new UCSetupException(3034, sb.toString());
                    } catch (Throwable th2) {
                        th = th2;
                        j2 = k;
                        Log.d(e, ".run stat: ".concat(String.valueOf(j2)));
                        throw th;
                    }
                } else {
                    String str2 = (String) getOption(b);
                    if (j.a(str2)) {
                        str2 = g.f();
                    }
                    Log.d(e, ".run sdCoreDecFilePath: ".concat(String.valueOf(str2)));
                    if (!j.a(str2)) {
                        j3 = i;
                        Integer num = (Integer) this.mOptions.get(UCCore.OPTION_VERIFY_POLICY);
                        String param = UCCore.getParam(CDParamKeys.CD_KEY_SHARE_CORE_CLIENT_VERIFY_POLICY);
                        if (CDParamKeys.CD_VALUE_VERIFY_ALL_HASH_ASYNC.equals(param)) {
                            num = Integer.valueOf(num.intValue() | UCCore.VERIFY_POLICY_ALL_FULL_HASH);
                        } else if (CDParamKeys.CD_VALUE_VERIFY_ALL_HASH_SYNC.equals(param)) {
                            num = Integer.valueOf((num.intValue() | UCCore.VERIFY_POLICY_ALL_FULL_HASH) & Integer.MAX_VALUE);
                        }
                        WaStat.stat((String) IWaStat.SHARE_CORE_SDCARD_SETUP_TASK_SDCARD);
                        Object[] objArr = {this.mCallbacks};
                        this.mCallbacks = null;
                        resetCrashFlag();
                        ((t) ((t) ((t) ((t) ((t) ((t) ((t) ((t) ((t) ((t) new n().invoke(10001, this)).invoke(10002, objArr)).setOptions(this.mOptions)).setup((String) UCCore.OPTION_VERIFY_POLICY, (Object) num)).setup((String) UCCore.OPTION_DEX_FILE_PATH, (Object) null)).setup((String) UCCore.OPTION_SO_FILE_PATH, (Object) null)).setup((String) UCCore.OPTION_RES_FILE_PATH, (Object) null)).setup((String) UCCore.OPTION_UCM_CFG_FILE, (Object) null)).setup((String) UCCore.OPTION_UCM_KRL_DIR, (Object) null)).setup((String) UCCore.OPTION_UCM_ZIP_FILE, (Object) str2)).start();
                    } else {
                        long j5 = j;
                        try {
                            WaStat.stat((String) IWaStat.SHARE_CORE_SDCARD_SETUP_TASK_EXCEPTION);
                            StringBuilder sb2 = new StringBuilder();
                            sb2.append(e);
                            sb2.append(" not found uc core");
                            throw new UCSetupException(3022, sb2.toString());
                        } catch (Throwable th3) {
                            j2 = j5;
                            th = th3;
                            Log.d(e, ".run stat: ".concat(String.valueOf(j2)));
                            throw th;
                        }
                    }
                }
            }
            Log.d(e, ".run stat: ".concat(String.valueOf(j3)));
        } catch (Throwable th4) {
            th = th4;
            j2 = j4;
            Log.d(e, ".run stat: ".concat(String.valueOf(j2)));
            throw th;
        }
    }
}
