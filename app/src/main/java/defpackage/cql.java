package defpackage;

import android.content.Context;
import android.graphics.Point;
import com.autonavi.miniapp.plugin.map.route.MiniAppShowRouteConfigHelper;
import java.util.regex.Pattern;

/* renamed from: cql reason: default package */
/* compiled from: CameraConfigurationManager */
final class cql {
    private static final String f = "cql";
    private static final Pattern g = Pattern.compile(",");
    final Context a;
    Point b;
    Point c;
    int d;
    String e;

    cql(Context context) {
        this.a = context;
    }

    static Point a(CharSequence charSequence, Point point) {
        String[] split = g.split(charSequence);
        int length = split.length;
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        int i4 = Integer.MAX_VALUE;
        while (true) {
            if (i >= length) {
                break;
            }
            String trim = split[i].trim();
            int indexOf = trim.indexOf(MiniAppShowRouteConfigHelper.LINE_TYPE_VIA_ROAD_LINE_FREE_);
            if (indexOf >= 0) {
                try {
                    int parseInt = Integer.parseInt(trim.substring(0, indexOf));
                    int parseInt2 = Integer.parseInt(trim.substring(indexOf + 1));
                    int abs = Math.abs(parseInt - point.x) + Math.abs(parseInt2 - point.y);
                    if (abs == 0) {
                        i2 = parseInt2;
                        i3 = parseInt;
                        break;
                    } else if (abs < i4) {
                        i2 = parseInt2;
                        i4 = abs;
                        i3 = parseInt;
                    }
                } catch (NumberFormatException unused) {
                    continue;
                }
            }
            i++;
        }
        if (i3 <= 0 || i2 <= 0) {
            return null;
        }
        return new Point(i3, i2);
    }

    static int a(CharSequence charSequence, int i) {
        String[] split = g.split(charSequence);
        int length = split.length;
        int i2 = 0;
        int i3 = 0;
        while (i2 < length) {
            try {
                double parseDouble = Double.parseDouble(split[i2].trim());
                int i4 = (int) (10.0d * parseDouble);
                if (Math.abs(((double) i) - parseDouble) < ((double) Math.abs(i - i3))) {
                    i3 = i4;
                }
                i2++;
            } catch (NumberFormatException unused) {
                return i;
            }
        }
        return i3;
    }
}
