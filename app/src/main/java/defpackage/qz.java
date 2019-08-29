package defpackage;

import android.location.Location;
import android.text.format.Time;
import com.autonavi.widget.ui.BalloonLayout;

/* renamed from: qz reason: default package */
/* compiled from: CalcSunRiseAndSunSetTools */
public final class qz {
    public static boolean a(double d, double d2, Location location) {
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        if (ro.a(location)) {
            Time time = new Time();
            time.set(location.getTime());
            if (time.year == 1970 || Math.abs(location.getTime() - System.currentTimeMillis()) > BalloonLayout.DEFAULT_DISPLAY_DURATION) {
                time.setToNow();
            }
            i4 = time.year;
            i3 = time.month + 1;
            i2 = time.monthDay;
            i = time.hour;
            i5 = time.minute;
        } else {
            Time time2 = new Time();
            time2.setToNow();
            i4 = time2.year;
            i3 = time2.month + 1;
            i2 = time2.monthDay;
            i = time2.hour;
            i5 = time2.minute;
        }
        int i6 = i4;
        int i7 = i3;
        int i8 = i2;
        double d3 = d;
        double d4 = d2;
        double a = a(i6, i7, i8, d3, d4, true);
        double a2 = a(i6, i7, i8, d3, d4, false);
        double d5 = ((double) i) + (((double) i5) / 60.0d);
        if (d5 < a || d5 >= a2) {
            return false;
        }
        return true;
    }

    private static double a(int i, int i2, int i3, double d, double d2, boolean z) {
        int i4;
        boolean z2;
        int i5 = i;
        int i6 = 1;
        int i7 = i2;
        int i8 = 0;
        for (int i9 = 1; i9 < i7; i9++) {
            switch (i9) {
                case 1:
                case 3:
                case 5:
                case 7:
                case 8:
                case 10:
                case 12:
                    i4 = 31;
                    break;
                case 2:
                    int i10 = i5 % 4;
                    if (i10 == 0 && ((i10 == 0 && i5 % 100 != 0) || (i5 % 100 == 0 && i5 % 400 == 0))) {
                        z2 = true;
                    } else {
                        z2 = false;
                    }
                    if (!z2) {
                        i4 = 28;
                        break;
                    } else {
                        i4 = 29;
                        break;
                    }
                case 4:
                case 6:
                case 9:
                case 11:
                    i4 = 30;
                    break;
                default:
                    i4 = 0;
                    break;
            }
            i8 += i4;
        }
        int i11 = i8 + i3;
        if (!z) {
            i6 = -1;
        }
        double d3 = 180.0d;
        while (true) {
            double d4 = (double) i11;
            double d5 = ((359.9905d * d4) / 365.25d) + 357.528d;
            double d6 = d5 * 0.0174532925199433d;
            double d7 = d5 * 2.0d * 0.0174532925199433d;
            double sin = ((360.0077d * d4) / 365.25d) + 280.46d + (Math.sin(d6) * 1.915d) + (Math.sin(d7) * 0.02d);
            double sin2 = ((((d3 - 180.0d) - (Math.sin(d6) * 1.915d)) - (Math.sin(d7) * 0.02d)) + (Math.sin((2.0d * sin) * 0.0174532925199433d) * 2.466d)) - (Math.sin((sin * 4.0d) * 0.0174532925199433d) * 0.053d);
            double d8 = d2 * 0.0174532925199433d;
            double asin = Math.asin(Math.sin((23.4393d - ((d4 * 0.013d) / 36525.0d)) * 0.0174532925199433d) * Math.sin(sin * 0.0174532925199433d)) * 57.2957795130823d * 0.0174532925199433d;
            double acos = d3 - ((sin2 + d) + (((double) i6) * (Math.acos((Math.sin(-0.014543828656868752d) - (Math.sin(d8) * Math.sin(asin))) / (Math.cos(d8) * Math.cos(asin))) * 57.2957795130823d)));
            if (Math.abs(acos - d3) <= 0.1d) {
                return (acos / 15.0d) + 8.0d;
            }
            d3 = acos;
        }
    }
}
