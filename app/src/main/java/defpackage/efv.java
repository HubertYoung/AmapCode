package defpackage;

import android.widget.TextView;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.minimap.R;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/* renamed from: efv reason: default package */
/* compiled from: RunDataShowUtil */
public final class efv {
    public static String a(long j) {
        long j2 = j / 3600;
        long j3 = (j % 3600) / 60;
        long j4 = j % 60;
        StringBuilder sb = new StringBuilder();
        if (j2 > 9) {
            sb.append(j2);
        } else {
            sb.append(0);
            sb.append(j2);
        }
        sb.append(":");
        if (j3 > 9) {
            sb.append(j3);
        } else {
            sb.append(0);
            sb.append(j3);
        }
        sb.append(":");
        if (j4 > 9) {
            sb.append(j4);
        } else {
            sb.append(0);
            sb.append(j4);
        }
        return sb.toString();
    }

    public static boolean a(TextView textView, TextView textView2, String[] strArr) {
        if (textView == null || textView2 == null || strArr == null || strArr.length != 2) {
            return false;
        }
        efx.a(textView);
        efx.a(textView2);
        textView.setText(strArr[0]);
        textView2.setText(strArr[1]);
        return true;
    }

    public static String[] a(int i) {
        String[] strArr = {"", ""};
        if (i >= 1000) {
            strArr[1] = AMapAppGlobal.getApplication().getString(R.string.running_route_start_running_unit_km);
        } else {
            strArr[1] = AMapAppGlobal.getApplication().getString(R.string.running_route_start_running_unit_m);
        }
        if (i < 1000) {
            strArr[0] = String.valueOf(i);
        } else if (i >= 100000) {
            StringBuilder sb = new StringBuilder();
            sb.append(Math.round(((float) i) / 1000.0f));
            strArr[0] = sb.toString();
        } else {
            strArr[0] = new DecimalFormat("#.0").format((double) (((float) i) / 1000.0f));
        }
        return strArr;
    }

    public static String a(double d) {
        StringBuilder sb = new StringBuilder();
        if (d != -1.0d) {
            int i = (int) d;
            int i2 = i / 60;
            int i3 = i % 60;
            sb.append(i2);
            sb.append("'");
            if (i3 < 10) {
                sb.append("0");
            }
            sb.append(i3);
            sb.append("\"");
        } else {
            sb.append("_ _");
            sb.append("'");
            sb.append("_ _");
            sb.append("\"");
        }
        return sb.toString();
    }

    public static String b(long j) {
        String format = new SimpleDateFormat("yyyy-MM-dd").format(new Date(j));
        StringBuilder sb = new StringBuilder();
        sb.append(format);
        sb.append("  ");
        sb.append(new SimpleDateFormat("HH:mm").format(new Date(j)));
        return sb.toString();
    }

    public static String c(long j) {
        String format = new SimpleDateFormat("yyyy-MM-dd").format(new Date(j));
        StringBuilder sb = new StringBuilder();
        sb.append(format);
        sb.append("  ");
        sb.append(new SimpleDateFormat("HH:mm").format(new Date(j)));
        return sb.toString();
    }

    public static String b(double d) {
        if (d < 0.0d) {
            return "99.99";
        }
        return d == 0.0d ? "0.00" : new DecimalFormat("0.00").format(d);
    }
}
