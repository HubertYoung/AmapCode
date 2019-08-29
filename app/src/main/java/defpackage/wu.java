package defpackage;

import android.content.Context;
import android.support.annotation.Nullable;
import com.amap.bundle.logs.AMapLog;

/* renamed from: wu reason: default package */
/* compiled from: CommandFactory */
public class wu {
    private static final String a = "wu";

    @Nullable
    public static wv a(Context context, int i) {
        switch (i) {
            case 1:
                return new wz(context);
            case 2:
                return new ww();
            case 3:
                return new xb();
            case 4:
                return new wy();
            case 5:
                return new xa(context);
            case 6:
                return new wx(context);
            default:
                if (bno.a) {
                    AMapLog.e(a, "invalid cmdType:".concat(String.valueOf(i)), true);
                }
                return null;
        }
    }
}
