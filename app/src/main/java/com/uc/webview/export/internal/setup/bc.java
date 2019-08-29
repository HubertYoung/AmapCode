package com.uc.webview.export.internal.setup;

import android.util.Pair;
import com.uc.webview.export.CDParamKeys;
import com.uc.webview.export.extension.UCCore;
import com.uc.webview.export.internal.interfaces.IWaStat;
import com.uc.webview.export.internal.utility.Log;
import com.uc.webview.export.internal.utility.j;

/* compiled from: ProGuard */
public class bc extends bw {
    private static String a = "bc";
    private static long b = 1;
    private static long c = 2;
    private static long e = 4;
    private static long f = 8;
    private static long g = (e << 1);

    public void run() {
        long j;
        Throwable th;
        long j2;
        Log.d(a, "==run.");
        long j3 = c;
        callbackStat(new Pair(IWaStat.SHARE_CORE_FAULTTOLERANCE_SETUP_TASK_RUN, null));
        try {
            if (!j.a(UCCore.getParam(CDParamKeys.CD_KEY_SHARE_CORE_CLIENT_BAK_KRL_DIR))) {
                j2 = e;
                try {
                    String str = a;
                    StringBuilder sb = new StringBuilder(".run bak krl dir: ");
                    sb.append(UCCore.getParam(CDParamKeys.CD_KEY_SHARE_CORE_CLIENT_BAK_KRL_DIR));
                    Log.d(str, sb.toString());
                    callbackStat(new Pair(IWaStat.SHARE_CORE_FAULTTOLERANCE_SETUP_TASK_KRL, null));
                    ((t) ((t) ((t) ((t) ((t) setup((String) UCCore.OPTION_DEX_FILE_PATH, (Object) null)).setup((String) UCCore.OPTION_SO_FILE_PATH, (Object) null)).setup((String) UCCore.OPTION_RES_FILE_PATH, (Object) null)).setup((String) UCCore.OPTION_UCM_LIB_DIR, (Object) null)).setup((String) UCCore.OPTION_UCM_KRL_DIR, (Object) UCCore.getParam(CDParamKeys.CD_KEY_SHARE_CORE_CLIENT_BAK_KRL_DIR))).setup((String) UCCore.OPTION_UCM_CFG_FILE, (Object) null);
                    super.run();
                } catch (Throwable th2) {
                    th = th2;
                    j = j2;
                }
            } else if (!j.a(UCCore.getParam(CDParamKeys.CD_KEY_SHARE_CORE_CLIENT_BAK_ZIP_FPATH))) {
                long j4 = f;
                try {
                    String str2 = a;
                    StringBuilder sb2 = new StringBuilder(".run bak core file: ");
                    sb2.append(UCCore.getParam(CDParamKeys.CD_KEY_SHARE_CORE_CLIENT_BAK_ZIP_FPATH));
                    Log.d(str2, sb2.toString());
                    callbackStat(new Pair(IWaStat.SHARE_CORE_FAULTTOLERANCE_SETUP_TASK_ZIP, null));
                    Object[] objArr = {this.mCallbacks};
                    this.mCallbacks = null;
                    resetCrashFlag();
                    ((t) ((t) ((t) ((t) ((t) ((t) ((t) ((t) ((t) new n().invoke(10001, this)).invoke(10002, objArr)).setOptions(this.mOptions)).setup((String) UCCore.OPTION_DEX_FILE_PATH, (Object) null)).setup((String) UCCore.OPTION_SO_FILE_PATH, (Object) null)).setup((String) UCCore.OPTION_RES_FILE_PATH, (Object) null)).setup((String) UCCore.OPTION_UCM_CFG_FILE, (Object) null)).setup((String) UCCore.OPTION_UCM_KRL_DIR, (Object) null)).setup((String) UCCore.OPTION_UCM_ZIP_FILE, (Object) UCCore.getParam(CDParamKeys.CD_KEY_SHARE_CORE_CLIENT_BAK_ZIP_FPATH))).start();
                    j2 = j4;
                } catch (Throwable th3) {
                    th = th3;
                    Log.d(a, ".run stat: ".concat(String.valueOf(j)));
                    throw th;
                }
            } else {
                long j5 = g;
                callbackStat(new Pair(IWaStat.SHARE_CORE_FAULTTOLERANCE_SETUP_TASK_EXCEPTION, null));
                StringBuilder sb3 = new StringBuilder();
                sb3.append(a);
                sb3.append(" not config (%s or %s)");
                throw new UCSetupException(3033, String.format(sb3.toString(), new Object[]{CDParamKeys.CD_KEY_SHARE_CORE_CLIENT_BAK_KRL_DIR, CDParamKeys.CD_KEY_SHARE_CORE_CLIENT_BAK_ZIP_FPATH}));
            }
            Log.d(a, ".run stat: ".concat(String.valueOf(j2)));
        } catch (Throwable th4) {
            j = j3;
            th = th4;
            Log.d(a, ".run stat: ".concat(String.valueOf(j)));
            throw th;
        }
    }
}
