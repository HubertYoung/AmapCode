package mtopsdk.mtop.global.init;

import android.os.Process;
import mtopsdk.common.util.TBSdkLog;
import mtopsdk.common.util.TBSdkLog.LogEnable;
import mtopsdk.mtop.domain.EntranceEnum;
import mtopsdk.mtop.domain.EnvModeEnum;
import mtopsdk.mtop.features.MtopFeatureManager;

public class OpenMtopInitTask implements IMtopInitTask {
    private static final String TAG = "mtopsdk.OpenMtopInitTask";

    public void executeCoreTask(ffd ffd) {
        if (ffd.v != null) {
            TBSdkLog.a(ffd.v);
        }
        String str = ffd.a;
        if (TBSdkLog.a(LogEnable.InfoEnable)) {
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append(" [executeInitCoreTask]MtopSDK initcore start. ");
            TBSdkLog.b(TAG, sb.toString());
        }
        try {
            MtopFeatureManager.a(ffd.b, 5);
            fgy.a(ffd.e);
            fgy.a(str, "ttid", ffd.m);
            fgu fgu = new fgu();
            fgu.a(ffd);
            ffd.d = EntranceEnum.GW_OPEN;
            ffd.l = fgu;
            ffd.j = fgu.a(new a(ffd.k, ffd.h));
            ffd.q = Process.myPid();
            ffd.K = new feb();
            if (ffd.J == null) {
                ffd.J = new fgm(ffd.e, ffy.a());
            }
            ffd.L.a(EnvModeEnum.ONLINE, "acs4baichuan.m.taobao.com");
            ffd.L.a(EnvModeEnum.PREPARE, "acs.wapa.taobao.com");
            ffd.L.a(EnvModeEnum.TEST, "acs.waptest.taobao.com");
            ffd.L.a(EnvModeEnum.TEST_SANDBOX, "api.waptest2nd.taobao.com");
        } catch (Throwable th) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(str);
            sb2.append(" [executeInitCoreTask]MtopSDK initcore error.");
            TBSdkLog.b((String) TAG, sb2.toString(), th);
        }
        if (TBSdkLog.a(LogEnable.InfoEnable)) {
            StringBuilder sb3 = new StringBuilder();
            sb3.append(str);
            sb3.append(" [executeInitCoreTask]MtopSDK initcore end");
            TBSdkLog.b(TAG, sb3.toString());
        }
    }

    public void executeExtraTask(ffd ffd) {
        String str = ffd.a;
        if (TBSdkLog.a(LogEnable.InfoEnable)) {
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append(" [executeInitExtraTask]MtopSDK initextra start");
            TBSdkLog.b(TAG, sb.toString());
        }
        try {
            fff.a();
        } catch (Throwable th) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(str);
            sb2.append(" [executeInitExtraTask] execute MtopSDK initExtraTask error.");
            TBSdkLog.b((String) TAG, sb2.toString(), th);
        }
        if (TBSdkLog.a(LogEnable.InfoEnable)) {
            StringBuilder sb3 = new StringBuilder();
            sb3.append(str);
            sb3.append(" [executeInitExtraTask]MtopSDK initextra end");
            TBSdkLog.b(TAG, sb3.toString());
        }
    }
}
