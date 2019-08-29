package defpackage;

import android.content.res.Resources;
import android.text.TextUtils;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.bundle.routecommon.entity.BusPath;
import com.autonavi.minimap.R;
import com.j256.ormlite.stmt.query.SimpleComparison;
import java.text.SimpleDateFormat;
import java.util.Date;

/* renamed from: axt reason: default package */
/* compiled from: TimeTransfer */
public final class axt {
    public static String a(int i, boolean z) {
        int i2;
        if (z) {
            i2 = (int) Math.round(((double) i) / 60.0d);
        } else {
            int i3 = i / 60;
            i2 = (i3 <= 0 || i % 60 == 0) ? i3 : i3 + 1;
        }
        Resources resources = AMapAppGlobal.getApplication().getResources();
        if (i2 < 60) {
            if (i2 == 0) {
                StringBuilder sb = new StringBuilder(SimpleComparison.LESS_THAN_OPERATION);
                sb.append(resources.getString(R.string.a_minute));
                return sb.toString();
            }
            StringBuilder sb2 = new StringBuilder();
            sb2.append(i2);
            sb2.append(resources.getString(R.string.minute));
            return sb2.toString();
        } else if (i2 < 1440) {
            StringBuilder sb3 = new StringBuilder();
            sb3.append(i2 / 60);
            sb3.append(resources.getString(R.string.hour));
            String sb4 = sb3.toString();
            int i4 = i2 % 60;
            if (i4 <= 0) {
                return sb4;
            }
            StringBuilder sb5 = new StringBuilder();
            sb5.append(sb4);
            sb5.append(i4);
            sb5.append(resources.getString(R.string.minute));
            return sb5.toString();
        } else {
            int i5 = i2 / 60;
            int i6 = i5 / 24;
            int i7 = i5 % 24;
            int i8 = i2 % 60;
            String string = resources.getString(R.string.day_774);
            String string2 = resources.getString(R.string.hour);
            String string3 = resources.getString(R.string.minute);
            StringBuilder sb6 = new StringBuilder();
            sb6.append(i6);
            sb6.append(string);
            String sb7 = sb6.toString();
            if (i7 != 0) {
                StringBuilder sb8 = new StringBuilder();
                sb8.append(sb7);
                sb8.append(i7);
                sb8.append(string2);
                sb7 = sb8.toString();
            }
            if (i8 == 0) {
                return sb7;
            }
            StringBuilder sb9 = new StringBuilder();
            sb9.append(sb7);
            sb9.append(i8);
            sb9.append(string3);
            return sb9.toString();
        }
    }

    public static String a(int i) {
        if (i < 0 || i > 60) {
            throw new IllegalArgumentException("wrong minute: ".concat(String.valueOf(i)));
        }
        StringBuilder sb = new StringBuilder();
        if (i < 10) {
            sb.append("0");
        }
        sb.append(i);
        return sb.toString();
    }

    public static String b(int i) {
        if (i < 0) {
            return "";
        }
        int i2 = i / 100;
        int i3 = i % 100;
        if (i2 > 24 || i3 > 60) {
            return "";
        }
        StringBuilder sb = new StringBuilder(5);
        if (i2 < 10) {
            sb.append("0");
        }
        sb.append(i2);
        sb.append(":");
        if (i3 < 10) {
            sb.append("0");
        }
        sb.append(i3);
        return sb.toString();
    }

    public static String a(BusPath busPath) {
        if (busPath == null) {
            return "";
        }
        int i = busPath.expenseTime;
        if (busPath.taxiBusPath != null && busPath.taxiBusPath.mDriveTime > 0) {
            i += busPath.taxiBusPath.mDriveTime;
        }
        if (TextUtils.isEmpty(lf.b(i))) {
            return "";
        }
        return lf.b(i);
    }

    public static String a(long j) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM月dd日");
        if (simpleDateFormat.format(new Date()).equalsIgnoreCase(simpleDateFormat.format(new Date(j)))) {
            SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("MM月dd日");
            Date date = new Date(j);
            StringBuilder sb = new StringBuilder();
            sb.append(simpleDateFormat2.format(date));
            sb.append(Token.SEPARATOR);
            sb.append(AMapAppGlobal.getApplication().getString(R.string.date_today));
            return sb.toString();
        }
        SimpleDateFormat simpleDateFormat3 = new SimpleDateFormat("MM月dd日");
        if (!simpleDateFormat3.format(new Date(System.currentTimeMillis() + 86400000)).equalsIgnoreCase(simpleDateFormat3.format(new Date(j)))) {
            return new SimpleDateFormat("MM月dd日 E").format(new Date(j));
        }
        SimpleDateFormat simpleDateFormat4 = new SimpleDateFormat("MM月dd日");
        Date date2 = new Date(j);
        StringBuilder sb2 = new StringBuilder();
        sb2.append(simpleDateFormat4.format(date2));
        sb2.append(Token.SEPARATOR);
        sb2.append(AMapAppGlobal.getApplication().getString(R.string.date_tomorrow));
        return sb2.toString();
    }
}
