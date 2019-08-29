package defpackage;

import android.content.res.Resources;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.minimap.R;
import com.j256.ormlite.stmt.query.SimpleComparison;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/* renamed from: lf reason: default package */
/* compiled from: DateTimeUtil */
public final class lf {
    static String[] a = {"Jan.", "Feb.", "Mar.", "Apr.", "May", "Jun", "Jul.", "Aug.", "Sep.", "Oct.", "Nov.", "Dec."};

    public static String a(long j, long j2) {
        Calendar instance = Calendar.getInstance();
        instance.setTimeInMillis(j2);
        long j3 = j - j2;
        Resources resources = AMapAppGlobal.getApplication().getResources();
        if (j3 > 259200000 || j3 < 0) {
            StringBuilder sb = new StringBuilder();
            sb.append(instance.get(2) + 1);
            sb.append(resources.getString(R.string.calendar_month));
            sb.append(instance.get(5));
            sb.append(resources.getString(R.string.calendar_day));
            return sb.toString();
        } else if (j3 > 86400000) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(j3 / 86400000);
            sb2.append(resources.getString(R.string.day_ago));
            return sb2.toString();
        } else if (j3 > 3600000) {
            StringBuilder sb3 = new StringBuilder();
            sb3.append(j3 / 3600000);
            sb3.append(resources.getString(R.string.hours_ago));
            return sb3.toString();
        } else if (j3 > 60000) {
            StringBuilder sb4 = new StringBuilder();
            sb4.append(j3 / 60000);
            sb4.append(resources.getString(R.string.mins_ago));
            return sb4.toString();
        } else {
            StringBuilder sb5 = new StringBuilder();
            sb5.append(j3 / 1000);
            sb5.append(resources.getString(R.string.sec_ago));
            return sb5.toString();
        }
    }

    public static long a() {
        long j;
        Date date = new Date();
        try {
            j = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).parse("2011-01-01 00:00:00").getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            j = 0;
        }
        return (date.getTime() - j) / 1000;
    }

    public static String a(Date date) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(date);
    }

    public static Date a(String str) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).parse(str);
        } catch (ParseException unused) {
            return null;
        }
    }

    public static String a(int i) {
        int i2 = i / 60;
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
            int i3 = i2 % 60;
            if (i3 <= 0) {
                return sb4;
            }
            StringBuilder sb5 = new StringBuilder();
            sb5.append(sb4);
            sb5.append(i3);
            sb5.append(resources.getString(R.string.minute));
            return sb5.toString();
        } else {
            int i4 = i2 / 60;
            int i5 = i4 / 24;
            int i6 = i4 % 24;
            int i7 = i2 % 60;
            String string = resources.getString(R.string.day);
            String string2 = resources.getString(R.string.hour);
            String string3 = resources.getString(R.string.minute);
            StringBuilder sb6 = new StringBuilder();
            sb6.append(i5);
            sb6.append(string);
            String sb7 = sb6.toString();
            if (i6 != 0) {
                StringBuilder sb8 = new StringBuilder();
                sb8.append(sb7);
                sb8.append(i6);
                sb8.append(string2);
                sb7 = sb8.toString();
            }
            if (i7 == 0) {
                return sb7;
            }
            StringBuilder sb9 = new StringBuilder();
            sb9.append(sb7);
            sb9.append(i7);
            sb9.append(string3);
            return sb9.toString();
        }
    }

    public static String b(int i) {
        int ceil = (int) Math.ceil(((double) i) / 60.0d);
        Resources resources = AMapAppGlobal.getApplication().getResources();
        if (ceil < 60) {
            if (ceil == 0) {
                StringBuilder sb = new StringBuilder(SimpleComparison.LESS_THAN_OPERATION);
                sb.append(resources.getString(R.string.a_minute));
                return sb.toString();
            }
            StringBuilder sb2 = new StringBuilder();
            sb2.append(ceil);
            sb2.append(resources.getString(R.string.minute));
            return sb2.toString();
        } else if (ceil < 1440) {
            StringBuilder sb3 = new StringBuilder();
            sb3.append(ceil / 60);
            sb3.append(resources.getString(R.string.hour));
            String sb4 = sb3.toString();
            int i2 = ceil % 60;
            if (i2 <= 0) {
                return sb4;
            }
            StringBuilder sb5 = new StringBuilder();
            sb5.append(sb4);
            sb5.append(i2);
            sb5.append(resources.getString(R.string.minute));
            return sb5.toString();
        } else {
            int i3 = ceil / 60;
            int i4 = i3 / 24;
            int i5 = i3 % 24;
            int i6 = ceil % 60;
            String string = resources.getString(R.string.day);
            String string2 = resources.getString(R.string.hour);
            String string3 = resources.getString(R.string.minute);
            StringBuilder sb6 = new StringBuilder();
            sb6.append(i4);
            sb6.append(string);
            String sb7 = sb6.toString();
            if (i5 != 0) {
                StringBuilder sb8 = new StringBuilder();
                sb8.append(sb7);
                sb8.append(i5);
                sb8.append(string2);
                sb7 = sb8.toString();
            }
            if (i6 == 0) {
                return sb7;
            }
            StringBuilder sb9 = new StringBuilder();
            sb9.append(sb7);
            sb9.append(i6);
            sb9.append(string3);
            return sb9.toString();
        }
    }

    public static String c(int i) {
        int i2 = (i + 30) / 60;
        Resources resources = AMapAppGlobal.getApplication().getResources();
        if (i2 >= 60) {
            int i3 = i2 / 60;
            int i4 = i2 % 60;
            if (i4 > 0) {
                StringBuilder sb = new StringBuilder();
                sb.append(i3);
                sb.append(resources.getString(R.string.hour));
                sb.append(i4);
                sb.append(resources.getString(R.string.minute));
                return sb.toString();
            }
            StringBuilder sb2 = new StringBuilder();
            sb2.append(i3);
            sb2.append(resources.getString(R.string.hour));
            return sb2.toString();
        } else if (i2 == 0) {
            StringBuilder sb3 = new StringBuilder("<1");
            sb3.append(resources.getString(R.string.minute));
            return sb3.toString();
        } else {
            StringBuilder sb4 = new StringBuilder();
            sb4.append(i2);
            sb4.append(resources.getString(R.string.minute));
            return sb4.toString();
        }
    }

    public static String d(int i) {
        Resources resources = AMapAppGlobal.getApplication().getResources();
        StringBuilder sb = new StringBuilder();
        int i2 = i / 60;
        if (i2 <= 0) {
            sb.append(SimpleComparison.LESS_THAN_OPERATION);
            sb.append(resources.getString(R.string.a_minute));
        } else if (i2 < 60) {
            sb.append(i2);
            sb.append(resources.getString(R.string.minute));
        } else {
            sb.append(i2 / 60);
            sb.append(resources.getString(R.string.hour));
            int i3 = i2 % 60;
            if (i3 > 0) {
                sb.append(i3);
                sb.append(resources.getString(R.string.minute));
            }
        }
        return sb.toString();
    }

    public static String b() {
        long currentTimeMillis = System.currentTimeMillis();
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM月dd日 HH:mm");
        date.setTime(currentTimeMillis);
        simpleDateFormat.applyPattern("HH");
        StringBuilder sb = new StringBuilder();
        sb.append(simpleDateFormat.format(date));
        sb.append(":00");
        String sb2 = sb.toString();
        date.setTime(currentTimeMillis + 3600000);
        simpleDateFormat.applyPattern("HH");
        StringBuilder sb3 = new StringBuilder();
        sb3.append(simpleDateFormat.format(date));
        sb3.append(":00");
        String sb4 = sb3.toString();
        StringBuilder sb5 = new StringBuilder();
        sb5.append(sb2);
        sb5.append("-");
        sb5.append(sb4);
        return sb5.toString();
    }
}
