package mtopsdk.mtop.global.init;

import android.os.Process;
import mtopsdk.common.util.TBSdkLog;
import mtopsdk.common.util.TBSdkLog.LogEnable;
import mtopsdk.mtop.domain.EntranceEnum;
import mtopsdk.mtop.features.MtopFeatureManager;
import mtopsdk.mtop.intf.Mtop;

public class ProductMtopInitTask implements IMtopInitTask {
    private static final String TAG = "mtopsdk.ProductMtopInitTask";

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
            fgy.a(ffd.e);
            fgy.a(str, "ttid", ffd.m);
            fgv fgv = new fgv();
            fgv.a(ffd);
            ffd.d = EntranceEnum.GW_INNER;
            ffd.l = fgv;
            ffd.j = fgv.a(new a(ffd.k, ffd.h));
            ffd.q = Process.myPid();
            ffd.K = new fec();
            if (ffd.x == null) {
                ffd.x = new fef(ffd.e);
            }
            if (ffd.J == null) {
                ffd.J = new fgk(ffd.e);
            }
            if (ffd.w == null) {
                ffd.w = new ffw();
            }
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
