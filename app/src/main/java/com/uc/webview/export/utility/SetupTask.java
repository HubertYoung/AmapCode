package com.uc.webview.export.utility;

import android.util.Pair;
import com.uc.webview.export.annotations.Api;
import com.uc.webview.export.cyclone.UCHashMap;
import com.uc.webview.export.cyclone.UCLogger;
import com.uc.webview.export.internal.SDKFactory;
import com.uc.webview.export.internal.interfaces.IWaStat;
import com.uc.webview.export.internal.setup.UCMPackageInfo;
import com.uc.webview.export.internal.setup.UCSetupException;
import com.uc.webview.export.internal.setup.UCSetupTask;
import com.uc.webview.export.internal.utility.Log;
import java.util.Map.Entry;

@Api
/* compiled from: ProGuard */
public abstract class SetupTask extends UCSetupTask<SetupTask, SetupTask> {
    public static UCMPackageInfo sFirstUCM;
    private final String a = "SetupTask";

    public void startSync() {
        start();
        try {
            Thread.sleep(200);
        } catch (Throwable unused) {
        }
        SDKFactory.invoke(10029, new Object[0]);
    }

    public void callbackFinishStat(String str) {
        UCLogger create = UCLogger.create("i", "SetupTask");
        if (create != null) {
            create.print("finish: core=".concat(String.valueOf(str)), new Throwable[0]);
        }
    }

    public void setException(UCSetupException uCSetupException) {
        setException(uCSetupException, true);
    }

    public void setException(UCSetupException uCSetupException, boolean z) {
        super.setException(uCSetupException);
        callStatException(IWaStat.SETUP_TOTAL_EXCEPTION, uCSetupException);
        callbackFinishStat("0");
        if (z) {
            SDKFactory.invoke(UCMPackageInfo.compareVersion, uCSetupException.toRunnable());
        }
    }

    public void callStatException(String str, UCSetupException uCSetupException) {
        Object obj;
        Object obj2;
        try {
            obj = uCSetupException.getRootCause().getClass().getName();
            try {
                String message = uCSetupException.getRootCause().getMessage();
                int i = 256;
                if (256 > message.length()) {
                    i = message.length();
                }
                obj2 = message.substring(0, i);
            } catch (Exception unused) {
                obj2 = "";
                callbackStat(new Pair(str, new UCHashMap().set("cnt", "1").set("err", String.valueOf(uCSetupException.errCode())).set("cls", obj).set("msg", obj2)));
            }
        } catch (Exception unused2) {
            obj = "";
            obj2 = "";
            callbackStat(new Pair(str, new UCHashMap().set("cnt", "1").set("err", String.valueOf(uCSetupException.errCode())).set("cls", obj).set("msg", obj2)));
        }
        try {
            callbackStat(new Pair(str, new UCHashMap().set("cnt", "1").set("err", String.valueOf(uCSetupException.errCode())).set("cls", obj).set("msg", obj2)));
        } catch (Throwable unused3) {
        }
    }

    public SetupTask setAsDefault() {
        SDKFactory.H = this;
        SDKFactory.r = true;
        return this;
    }

    public String getFirstUCMFileHashs() {
        StringBuilder sb = new StringBuilder();
        try {
            if (sFirstUCM != null) {
                for (Entry next : sFirstUCM.getFileHashs().entrySet()) {
                    sb.append((String) next.getKey());
                    sb.append("=");
                    sb.append((String) next.getValue());
                    sb.append(";");
                }
            } else {
                sb.append("first_ucm=null;");
            }
        } catch (Throwable th) {
            Log.d("SetupTask", "getFirstUCMFileHashs error", th);
        }
        return sb.toString();
    }
}
