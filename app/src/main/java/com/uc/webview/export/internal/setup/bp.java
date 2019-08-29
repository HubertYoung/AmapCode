package com.uc.webview.export.internal.setup;

import android.util.Pair;
import android.webkit.ValueCallback;
import com.alipay.mobile.beehive.capture.utils.AudioUtils;
import com.alipay.mobile.common.logging.api.LogCategory;
import com.amap.bundle.cloudconfig.appinit.FunctionSupportConfiger;
import com.uc.webview.export.extension.UCCore;
import com.uc.webview.export.internal.SDKFactory;
import com.uc.webview.export.internal.setup.UCAsyncTask.c;
import com.uc.webview.export.internal.setup.UCSubSetupTask.a;
import com.uc.webview.export.internal.uc.startup.StartupTrace;
import com.uc.webview.export.internal.utility.j;

/* compiled from: ProGuard */
final class bp implements ValueCallback<r> {
    final /* synthetic */ bo a;

    bp(bo boVar) {
        this.a = boVar;
    }

    public final /* synthetic */ void onReceiveValue(Object obj) {
        Integer num = (Integer) this.a.b.b.mOptions.get(UCCore.OPTION_VERIFY_POLICY);
        boolean z = (num == null || (num.intValue() & Integer.MIN_VALUE) == 0) ? false : true;
        StartupTrace.traceEvent("StandardSetupTask.runInternal InitTask");
        Pair<Integer, Object> a2 = this.a.b.a.a(6000);
        if (((Integer) a2.first).intValue() == 3) {
            bl.a(this.a.b.b, z, 1);
            UCSetupException uCSetupException = (UCSetupException) a2.second;
            new StringBuilder("VerifyTask Fail:").append(uCSetupException.toString());
            this.a.b.b.setException(uCSetupException);
        } else if (((Integer) a2.first).intValue() == 1) {
            bl.a(this.a.b.b, z, 2);
            this.a.b.b.setException(new UCSetupException(1011, String.format("So files hash verify timeout.", new Object[0])));
        } else {
            bl.a(this.a.b.b, z, 0);
            try {
                bl blVar = this.a.b.b;
                UCMRunningInfo uCMRunningInfo = r5;
                UCMRunningInfo uCMRunningInfo2 = new UCMRunningInfo(this.a.b.b.getContext(), this.a.a.mUCM, this.a.a.mCL, this.a.a.mShellCL, this.a.a.a, this.a.a.b, SDKFactory.d, ((Integer) SDKFactory.invoke(SDKFactory.getCoreType, new Object[0])).intValue(), j.a((Boolean) this.a.b.b.mOptions.get("scst_flag")), j.a(this.a.a.mUCM.isSpecified, j.a((Boolean) this.a.b.b.mOptions.get("scst_flag"))));
                blVar.setLoadedUCM(uCMRunningInfo);
                this.a.b.b.callback("init");
                this.a.b.b.callback(FunctionSupportConfiger.SWITCH_TAG);
                Boolean bool = (Boolean) this.a.b.b.getOption(UCCore.OPTION_PRECREATE_WEBVIEW);
                if (bool != null && bool.booleanValue() && !aa.b()) {
                    bl blVar2 = this.a.b.b;
                    blVar2.getClass();
                    bl blVar3 = this.a.b.b;
                    blVar3.getClass();
                    ((aa) ((aa) ((aa) ((aa) ((aa) ((aa) ((aa) ((aa) new aa().invoke(10001, this.a.b.b)).setOptions(this.a.b.b.mOptions)).setUCM(this.a.a.mUCM)).setClassLoader(this.a.a.mCL)).onEvent("stat", new a())).onEvent(AudioUtils.CMDSTOP, new c())).onEvent("success", new br(this))).onEvent(LogCategory.CATEGORY_EXCEPTION, new bq(this))).start();
                }
            } catch (UCSetupException e) {
                this.a.b.b.setException(e);
            } catch (Throwable th) {
                this.a.b.b.setException(new UCSetupException(4018, th));
            }
        }
    }
}
