package defpackage;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import java.util.Arrays;

/* renamed from: lj reason: default package */
/* compiled from: CityInfo */
public final class lj implements Cloneable, Comparable<lj> {
    public String a;
    public String b;
    public String c;
    public String d;
    public String e;
    public int f;
    public int g;
    public int h;
    public String i;
    public int j;

    public final /* synthetic */ int compareTo(@NonNull Object obj) {
        lj ljVar = (lj) obj;
        if (ljVar == null || TextUtils.isEmpty(ljVar.b)) {
            return 1;
        }
        if (TextUtils.isEmpty(this.b)) {
            return -1;
        }
        return this.b.compareTo(ljVar.b);
    }

    public lj() {
    }

    /* JADX WARNING: Code restructure failed: missing block: B:7:0x0018, code lost:
        r1 = r0.substring(0, 1);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public lj(com.autonavi.jni.ae.data.ADCityInfo r4) {
        /*
            r3 = this;
            r3.<init>()
            java.lang.String r0 = r4.mJianpin
            r1 = 0
            if (r0 == 0) goto L_0x000f
            java.lang.String r0 = r4.mJianpin
            java.lang.String r0 = r0.toUpperCase()
            goto L_0x0010
        L_0x000f:
            r0 = r1
        L_0x0010:
            if (r0 == 0) goto L_0x001e
            int r2 = r0.length()
            if (r2 <= 0) goto L_0x001e
            r1 = 0
            r2 = 1
            java.lang.String r1 = r0.substring(r1, r2)
        L_0x001e:
            java.lang.String r2 = r4.mCityName
            r3.a = r2
            java.lang.String r2 = r4.mQuanpin
            r3.b = r2
            r3.c = r0
            r3.d = r1
            java.lang.String r0 = r4.mPronvinceName
            r3.e = r0
            int r0 = r4.mCenterPointX
            r3.f = r0
            int r0 = r4.mCenterPointY
            r3.g = r0
            int r0 = r4.mLevel
            r3.h = r0
            java.lang.String r0 = r4.mCitycode
            r3.i = r0
            int r4 = r4.mAdcode
            r3.j = r4
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.lj.<init>(com.autonavi.jni.ae.data.ADCityInfo):void");
    }

    public final boolean a() {
        return ((long) this.j) != 710000;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("CityInfo{mName='");
        sb.append(this.a);
        sb.append('\'');
        sb.append(", mInitials='");
        sb.append(this.c);
        sb.append('\'');
        sb.append(", mInitial='");
        sb.append(this.d);
        sb.append('\'');
        sb.append(", mProvinceName='");
        sb.append(this.e);
        sb.append('\'');
        sb.append(", mCenterX=");
        sb.append(this.f);
        sb.append('\'');
        sb.append(", mCenterY=");
        sb.append(this.g);
        sb.append('\'');
        sb.append(", mMapLevel=");
        sb.append(this.h);
        sb.append('\'');
        sb.append(", mCityCode='");
        sb.append(this.i);
        sb.append('\'');
        sb.append(", mAdcode=");
        sb.append(this.j);
        sb.append('}');
        return sb.toString();
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        lj ljVar = (lj) obj;
        return this.f == ljVar.f && this.g == ljVar.g && this.h == ljVar.h && this.j == ljVar.j && TextUtils.equals(this.a, ljVar.a) && TextUtils.equals(this.b, ljVar.b) && TextUtils.equals(this.c, ljVar.c) && TextUtils.equals(this.d, ljVar.d) && TextUtils.equals(this.e, ljVar.e) && TextUtils.equals(this.i, ljVar.i);
    }

    public final int hashCode() {
        return Arrays.hashCode(new Object[]{this.a, this.b, this.c, this.d, this.e, Integer.valueOf(this.f), Integer.valueOf(this.g), Integer.valueOf(this.h), this.i, Integer.valueOf(this.j)});
    }

    /* renamed from: b */
    public final lj clone() {
        lj ljVar = new lj();
        ljVar.a = this.a;
        ljVar.b = this.b;
        ljVar.c = this.c;
        ljVar.d = this.d;
        ljVar.e = this.e;
        ljVar.f = this.f;
        ljVar.g = this.g;
        ljVar.h = this.h;
        ljVar.i = this.i;
        ljVar.j = this.j;
        return ljVar;
    }
}
