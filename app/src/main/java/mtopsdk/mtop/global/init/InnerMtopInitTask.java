package mtopsdk.mtop.global.init;

import android.os.Process;
import mtopsdk.common.util.TBSdkLog;
import mtopsdk.common.util.TBSdkLog.LogEnable;
import mtopsdk.mtop.domain.EntranceEnum;
import mtopsdk.mtop.features.MtopFeatureManager;
import mtopsdk.mtop.intf.Mtop;

public class InnerMtopInitTask implements IMtopInitTask {
    private static final String TAG = "mtopsdk.InnerMtopInitTask";

    public void executeCoreTask(ffd ffd) {
        TBSdkLog.a(ffd.v != null ? ffd.v : new fcw());
        String str = ffd.a;
        if (TBSdkLog.a(LogEnable.InfoEnable)) {
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append(" [executeInitCoreTask]MtopSDK initcore start. ");
            TBSdkLog.b(TAG, sb.toString());
        }
        try {
            Mtop mtop = ffd.b;
            MtopFeatureManager.a(mtop, 1);
            MtopFeatureManager.a(mtop, 2);
            MtopFeatureManager.a(mtop, 4);
            MtopFeatureManager.a(mtop, 5);
            if (ffd.w == null) {
                ffd.w = new ffw();
            }
            ffd.y = new ffk();
            fgy.a(ffd.e);
            fgy.a(str, "ttid", ffd.m);
            ffd.y.b(ffd.m);
            fgr fgr = ffd.l;
            if (fgr == null) {
                fgr = new fgs();
            }
            fgr.a(ffd);
            ffd.d = EntranceEnum.GW_INNER;
            ffd.l = fgr;
            ffd.j = fgr.a(new a(ffd.k, ffd.h));
            ffd.q = Process.myPid();
            ffd.K = new fea();
            if (ffd.x == null) {
                ffd.x = new fef(ffd.e);
            }
            if (ffd.J == null) {
                ffd.J = new fgk(ffd.e);
            }
        } catch (Throwable th) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(str);
            sb2.append(" [executeInitCoreTask]MtopSDK initCore error");
            TBSdkLog.b((String) TAG, sb2.toString(), th);
        }
        if (TBSdkLog.a(LogEnable.InfoEnable)) {
            StringBuilder sb3 = new StringBuilder();
            sb3.append(str);
            sb3.append(" [executeInitCoreTask]MtopSDK initCore end");
            TBSdkLog.b(TAG, sb3.toString());
        }
    }

    public void executeExtraTask(ffd ffd) {
        String str = ffd.a;
        if (TBSdkLog.a(LogEnable.InfoEnable)) {
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append(" [executeInitExtraTask]MtopSDK initExtra start");
            TBSdkLog.b(TAG, sb.toString());
        }
        try {
            if (ffd.B) {
                ffa.a().a(ffd.e, ffd.j);
            }
            fff.a();
            a.a.a(ffd);
        } catch (Throwable th) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(str);
            sb2.append(" [executeInitExtraTask] execute MtopSDK initExtraTask error.");
            TBSdkLog.b((String) TAG, sb2.toString(), th);
        }
        if (TBSdkLog.a(LogEnable.InfoEnable)) {
            StringBuilder sb3 = new StringBuilder();
            sb3.append(str);
            sb3.append(" [executeInitExtraTask]MtopSDK initExtra end");
            TBSdkLog.b(TAG, sb3.toString());
        }
    }
}
