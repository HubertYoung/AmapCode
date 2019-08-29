package mtopsdk.mtop.features;

import android.content.Context;
import mtopsdk.common.util.TBSdkLog;
import mtopsdk.common.util.TBSdkLog.LogEnable;
import mtopsdk.mtop.intf.Mtop;

public final class MtopFeatureManager {

    public enum MtopFeatureEnum {
        SUPPORT_RELATIVE_URL(1),
        UNIT_INFO_FEATURE(2),
        DISABLE_WHITEBOX_SIGN(3),
        SUPPORT_UTDID_UNIT(4),
        DISABLE_X_COMMAND(5),
        SUPPORT_OPEN_ACCOUNT(6);
        
        long a;

        public final long getFeature() {
            return this.a;
        }

        private MtopFeatureEnum(long j) {
            this.a = j;
        }
    }

    public static long a(int i) {
        if (i <= 0) {
            return 0;
        }
        return (long) (1 << (i - 1));
    }

    public static long a(Mtop mtop) {
        if (mtop == null) {
            mtop = Mtop.a((Context) null);
        }
        long j = 0;
        try {
            for (Integer intValue : mtop.c.D) {
                j |= a(intValue.intValue());
            }
        } catch (Exception e) {
            StringBuilder sb = new StringBuilder();
            sb.append(mtop.b);
            sb.append(" [getMtopTotalFeatures] get mtop total features error.---");
            sb.append(e.toString());
            TBSdkLog.c("mtopsdk.MtopFeatureManager", sb.toString());
        }
        return j;
    }

    public static void a(Mtop mtop, int i) {
        if (mtop == null) {
            mtop = Mtop.a((Context) null);
        }
        mtop.c.D.add(Integer.valueOf(i));
        if (TBSdkLog.a(LogEnable.InfoEnable)) {
            StringBuilder sb = new StringBuilder();
            sb.append(mtop.b);
            sb.append(" [setMtopFeatureFlag] set feature=");
            sb.append(i);
            sb.append(" , openFlag=true");
            TBSdkLog.b("mtopsdk.MtopFeatureManager", sb.toString());
        }
    }
}
