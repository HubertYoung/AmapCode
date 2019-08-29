package org.altbeacon.beacon.distance;

import android.os.Build;
import android.os.Build.VERSION;
import org.altbeacon.beacon.b.d;

/* compiled from: AndroidModel */
public final class a {
    String a;
    String b;
    String c;
    String d;

    public a(String version, String buildNumber, String model, String manufacturer) {
        this.a = version;
        this.b = buildNumber;
        this.c = model;
        this.d = manufacturer;
    }

    public static a a() {
        return new a(VERSION.RELEASE, Build.ID, Build.MODEL, Build.MANUFACTURER);
    }

    public final String b() {
        return this.a;
    }

    public final String c() {
        return this.b;
    }

    public final String d() {
        return this.c;
    }

    public final String e() {
        return this.d;
    }

    public final int a(a otherModel) {
        int score = 0;
        if (this.d.equalsIgnoreCase(otherModel.d)) {
            score = 1;
        }
        if (score == 1 && this.c.equals(otherModel.c)) {
            score = 2;
        }
        if (score == 2 && this.b.equals(otherModel.b)) {
            score = 3;
        }
        if (score == 3 && this.a.equals(otherModel.a)) {
            score = 4;
        }
        d.a("AndroidModel", "Score is %s for %s compared to %s", Integer.valueOf(score), toString(), otherModel);
        return score;
    }

    public final String toString() {
        return this.d + ";" + this.c + ";" + this.b + ";" + this.a;
    }
}
