package defpackage;

import android.content.res.Resources;
import android.text.TextUtils;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.minimap.R;
import com.autonavi.sync.beans.GirfFavoriteRoute;
import com.j256.ormlite.stmt.query.SimpleComparison;

/* renamed from: cpc reason: default package */
/* compiled from: SimpleRoute */
public class cpc {
    private static final String e = "cpc";
    public String a = "";
    public String b = "";
    public String c = "";
    public GirfFavoriteRoute d;

    public cpc(String str) {
        this.d = bim.aa().b(str);
    }

    public static String[] a(int i) {
        String[] strArr = {"", ""};
        Resources resources = AMapAppGlobal.getApplication().getResources();
        if (i < 1000) {
            strArr[0] = String.valueOf(i);
            strArr[1] = resources.getString(R.string.meter);
            return strArr;
        }
        int i2 = i / 1000;
        int i3 = (i % 1000) / 100;
        String valueOf = String.valueOf(i2);
        if (i3 > 0) {
            StringBuilder sb = new StringBuilder();
            sb.append(valueOf);
            sb.append(".");
            sb.append(i3);
            strArr[0] = sb.toString();
            strArr[1] = resources.getString(R.string.km);
        } else {
            strArr[0] = valueOf;
            strArr[1] = resources.getString(R.string.km);
        }
        return strArr;
    }

    public final String a() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.d.id);
        return sb.toString();
    }

    public final int b() {
        if (TextUtils.isEmpty(this.d.costTime)) {
            return 0;
        }
        try {
            return Integer.parseInt(this.d.costTime);
        } catch (NumberFormatException e2) {
            e2.printStackTrace();
            return 0;
        }
    }

    public static String a(String str) {
        try {
            if (str.length() > 4) {
                return str.substring(0, 4);
            }
            return String.format("%04d", new Object[]{Integer.valueOf(Integer.parseInt(str))});
        } catch (NumberFormatException e2) {
            e2.printStackTrace();
            r4 = "";
            return "";
        }
    }

    public static boolean b(String str) {
        if (str == null || str.length() == 0) {
            return false;
        }
        try {
            if (Integer.parseInt(str) < 0) {
                return false;
            }
            String a2 = a(str);
            String substring = a2.substring(0, 2);
            String substring2 = a2.substring(2);
            int parseInt = Integer.parseInt(substring);
            int parseInt2 = Integer.parseInt(substring2);
            if (parseInt > 24 || parseInt < 0 || parseInt2 > 59 || parseInt2 < 0) {
                return false;
            }
            return true;
        } catch (NumberFormatException e2) {
            e2.printStackTrace();
            return false;
        }
    }

    public static int c(String str) {
        try {
            return Integer.parseInt(str);
        } catch (Exception e2) {
            e2.printStackTrace();
            return 0;
        }
    }

    public static String b(int i) {
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

    /* JADX WARNING: Removed duplicated region for block: B:18:0x005e  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.String c() {
        /*
            r8 = this;
            java.lang.StringBuffer r0 = new java.lang.StringBuffer
            r0.<init>()
            com.autonavi.sync.beans.GirfFavoriteRoute r1 = r8.d
            if (r1 == 0) goto L_0x00b9
            com.autonavi.sync.beans.GirfFavoriteRoute r1 = r8.d
            java.util.ArrayList<java.lang.String> r1 = r1.busSectionNames
            if (r1 == 0) goto L_0x00b9
            com.autonavi.sync.beans.GirfFavoriteRoute r1 = r8.d
            java.util.ArrayList<java.lang.String> r1 = r1.busSectionNames
            int r1 = r1.size()
            if (r1 <= 0) goto L_0x00b9
            android.content.Context r1 = com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil.getAppContext()
            com.autonavi.sync.beans.GirfFavoriteRoute r2 = r8.d
            int r3 = r2.routeType
            r4 = 2
            r5 = 1
            r6 = 0
            if (r3 == r4) goto L_0x0028
        L_0x0026:
            r1 = 0
            goto L_0x005c
        L_0x0028:
            cpm r3 = defpackage.cpm.b()
            java.lang.String r3 = r3.a()
            cpg r3 = defpackage.cpg.c(r3)
            java.lang.String r2 = r2.itemId
            bti r2 = r3.d(r2)
            r3 = 0
            boolean r4 = r2.e()
            if (r4 == 0) goto L_0x0026
            esb r4 = defpackage.esb.a.a
            java.lang.Class<asy> r7 = defpackage.asy.class
            esc r4 = r4.a(r7)
            asy r4 = (defpackage.asy) r4
            if (r4 == 0) goto L_0x0055
            java.lang.String r2 = r2.a
            java.lang.String r3 = r4.a(r1, r2)
        L_0x0055:
            boolean r1 = android.text.TextUtils.isEmpty(r3)
            if (r1 != 0) goto L_0x0026
            r1 = 1
        L_0x005c:
            if (r1 == 0) goto L_0x00b9
            com.autonavi.sync.beans.GirfFavoriteRoute r1 = r8.d
            java.util.ArrayList<java.lang.String> r1 = r1.busSectionNames
            int r1 = r1.size()
            if (r1 != r5) goto L_0x0077
            com.autonavi.sync.beans.GirfFavoriteRoute r0 = r8.d
            java.util.ArrayList<java.lang.String> r0 = r0.busSectionNames
            java.lang.Object r0 = r0.get(r6)
            java.lang.String r0 = (java.lang.String) r0
            java.lang.String r0 = d(r0)
            return r0
        L_0x0077:
            com.autonavi.sync.beans.GirfFavoriteRoute r1 = r8.d
            java.util.ArrayList<java.lang.String> r1 = r1.busSectionNames
            int r1 = r1.size()
        L_0x007f:
            int r2 = r1 + -1
            if (r6 >= r2) goto L_0x00a8
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            com.autonavi.sync.beans.GirfFavoriteRoute r3 = r8.d
            java.util.ArrayList<java.lang.String> r3 = r3.busSectionNames
            java.lang.Object r3 = r3.get(r6)
            java.lang.String r3 = (java.lang.String) r3
            java.lang.String r3 = d(r3)
            r2.append(r3)
            java.lang.String r3 = "→"
            r2.append(r3)
            java.lang.String r2 = r2.toString()
            r0.append(r2)
            int r6 = r6 + 1
            goto L_0x007f
        L_0x00a8:
            com.autonavi.sync.beans.GirfFavoriteRoute r1 = r8.d
            java.util.ArrayList<java.lang.String> r1 = r1.busSectionNames
            java.lang.Object r1 = r1.get(r2)
            java.lang.String r1 = (java.lang.String) r1
            java.lang.String r1 = d(r1)
            r0.append(r1)
        L_0x00b9:
            java.lang.String r0 = r0.toString()
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.cpc.c():java.lang.String");
    }

    private static String d(String str) {
        if (str == null || str.length() == 0) {
            return "";
        }
        int indexOf = str.indexOf(40);
        if (indexOf > 0) {
            return str.substring(0, indexOf);
        }
        int indexOf2 = str.indexOf(65288);
        return indexOf2 > 0 ? str.substring(0, indexOf2) : str;
    }
}
