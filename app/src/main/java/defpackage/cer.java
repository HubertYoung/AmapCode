package defpackage;

import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.items.LiveConfigItem;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.minimap.R;

/* renamed from: cer reason: default package */
/* compiled from: ScaleLine */
public final class cer {
    private static final int[] a = {32, 26, 26, 25, 20, 20, 20, 25, 32, 32, 33, 26, 26, 28, 26, 26, 26, 26, 26, 26};
    private static final int[] b = {LiveConfigItem.PLAYTIMEOUT, 2000000, 1000000, 500000, 200000, 100000, 50000, 30000, 20000, 10000, 5000, 2000, 1000, 500, 200, 100, 50, 25, 10, 5};

    public static String a(int i) {
        if (i < 0) {
            i = 2;
        }
        if (i >= b.length) {
            i = b.length - 1;
        }
        if (b[i] % 1000 == 0) {
            StringBuilder sb = new StringBuilder();
            sb.append(b[i] / 1000);
            sb.append(AMapAppGlobal.getApplication().getString(R.string.km));
            return sb.toString();
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append(b[i]);
        sb2.append(AMapAppGlobal.getApplication().getString(R.string.meter));
        return sb2.toString();
    }

    public static int b(int i) {
        if (i < 0) {
            i = 2;
        }
        if (i >= b.length) {
            i = b.length - 1;
        }
        return b[i];
    }
}
