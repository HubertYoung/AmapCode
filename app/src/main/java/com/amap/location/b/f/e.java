package com.amap.location.b.f;

import android.content.Context;
import android.location.Location;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.annotation.NonNull;
import com.alipay.android.phone.inside.sdk.util.GlobalConstants;
import com.amap.location.b.c.g;
import com.amap.location.b.c.j;
import com.amap.location.common.d.a;
import com.amap.location.g.b.c;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;
import java.util.List;

/* compiled from: GpsUtil */
public class e {
    public static void a(@NonNull g gVar, short s, @NonNull Location location, long j, long j2) {
        gVar.j = s;
        a(gVar, location, j, j2);
    }

    public static void a(@NonNull g gVar, @NonNull Location location, long j, long j2) {
        gVar.b = j;
        gVar.a = j2;
        gVar.c = (int) (location.getLongitude() * 1000000.0d);
        gVar.d = (int) (location.getLatitude() * 1000000.0d);
        gVar.e = (int) location.getAltitude();
        gVar.f = (int) location.getAccuracy();
        gVar.g = (int) location.getSpeed();
        gVar.h = (short) ((int) location.getBearing());
        Bundle extras = location.getExtras();
        gVar.i = 0;
        if (extras != null) {
            try {
                gVar.i = (byte) extras.getInt("satellites", 0);
            } catch (Exception e) {
                a.a((Throwable) e);
            }
        }
    }

    public static short a(@NonNull List<j> list, boolean z, List<c> list2) {
        list.clear();
        short s = Short.MAX_VALUE;
        if (list2 != null) {
            double d = 0.0d;
            int i = 0;
            for (c next : list2) {
                int b = next.b();
                float d2 = next.d();
                boolean a = next.a();
                if (b > 1 && b <= 32) {
                    if (a && ((double) d2) > 10.0d) {
                        d += (double) next.c();
                        i++;
                    }
                    if (z) {
                        j jVar = new j();
                        jVar.a = (byte) b;
                        jVar.b = (byte) Math.round(next.c());
                        jVar.c = (byte) Math.round(d2);
                        jVar.d = (short) Math.round(next.e());
                        jVar.e = a ? (byte) 1 : 0;
                        list.add(jVar);
                    }
                }
                if (i > 0) {
                    s = (short) Math.round(((float) (d / ((double) i))) * 100.0f);
                }
            }
        }
        return s;
    }

    public static boolean a(Location location) {
        return location != null && WidgetType.GPS.equalsIgnoreCase(location.getProvider()) && location.getLatitude() > -90.0d && location.getLatitude() < 90.0d && location.getLongitude() > -180.0d && location.getLongitude() < 180.0d;
    }

    public static boolean a(Context context, Location location) {
        if (VERSION.SDK_INT >= 18) {
            if (!d.a(location)) {
                return false;
            }
        } else if (!Build.MODEL.equals(GlobalConstants.EXCEPTIONTYPE) && !d.b(context)) {
            return false;
        }
        return true;
    }
}
