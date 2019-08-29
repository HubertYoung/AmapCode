package defpackage;

import android.text.TextUtils;
import java.util.Locale;

/* renamed from: dhr reason: default package */
/* compiled from: RestrictCity */
public final class dhr implements Comparable<dhr> {
    public String a;
    public String b;
    public String c;
    private String d;
    private String e;

    public final /* synthetic */ int compareTo(Object obj) {
        int i;
        dhr dhr = (dhr) obj;
        if (this.b.length() > dhr.b.length()) {
            i = dhr.b.length();
        } else {
            i = this.b.length();
        }
        for (int i2 = 0; i2 < i; i2++) {
            String lowerCase = this.b.toLowerCase(Locale.getDefault());
            String lowerCase2 = dhr.b.toLowerCase(Locale.getDefault());
            int compareTo = String.valueOf(lowerCase.charAt(i2)).compareTo(String.valueOf(lowerCase2.charAt(i2)));
            if (compareTo != 0) {
                return compareTo;
            }
        }
        return 0;
    }

    public dhr(String str, String str2, String str3) {
        this.a = str;
        this.d = str2;
        this.b = str3;
    }

    public dhr(String str, String str2, String str3, String str4) {
        this.a = str;
        this.d = str2;
        this.b = str3;
        this.c = str4;
    }

    public final String a() {
        if (!TextUtils.isEmpty(this.d) && TextUtils.isEmpty(this.e)) {
            this.e = this.d;
            int length = this.d.length();
            if (length < 6) {
                StringBuilder sb = new StringBuilder(this.d);
                for (int i = 0; i < 6 - length; i++) {
                    sb.append("0");
                }
                this.e = sb.toString();
                return this.e;
            }
        }
        return this.e;
    }

    public final boolean equals(Object obj) {
        if (obj != null && (obj instanceof dhr) && !TextUtils.isEmpty(this.d)) {
            dhr dhr = (dhr) obj;
            if (!TextUtils.isEmpty(dhr.d)) {
                return a().equals(dhr.a());
            }
        }
        return false;
    }

    public final int hashCode() {
        if (TextUtils.isEmpty(this.d)) {
            return super.hashCode();
        }
        try {
            return Integer.parseInt(a());
        } catch (NumberFormatException e2) {
            e2.printStackTrace();
            return super.hashCode();
        }
    }
}
