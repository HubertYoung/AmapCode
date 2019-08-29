package com.alibaba.baichuan.android.trade.c.a.a.b;

import com.alibaba.baichuan.android.trade.c.a.a.c;
import com.alipay.mobile.h5container.api.H5PageData;
import java.io.Serializable;
import java.util.regex.Pattern;

public class f implements Serializable {
    public static final String[] a = {"http", "https"};
    public static final String[] b = {"tbopen"};
    public String c;
    public String d;
    public String[] e;
    public String[] f;
    private transient Pattern[] g;

    public boolean a(c cVar) {
        String str;
        boolean z;
        if (this.e == null || this.e.length <= 0) {
            str = cVar.d();
        } else {
            str = cVar.c();
            String f2 = cVar.f();
            if (f2 == null) {
                return false;
            }
            String[] strArr = this.e;
            int length = strArr.length;
            int i = 0;
            while (true) {
                if (i >= length) {
                    z = false;
                    break;
                } else if (f2.equals(strArr[i])) {
                    z = true;
                    break;
                } else {
                    i++;
                }
            }
            if (!z) {
                return false;
            }
        }
        if ("all".equals(this.d)) {
            return true;
        }
        if ((this.f == null) || (this.f.length == 0)) {
            return false;
        }
        if ("pattern".equals(this.d)) {
            if (this.g == null) {
                this.g = new Pattern[this.f.length];
                int length2 = this.g.length;
                for (int i2 = 0; i2 < length2; i2++) {
                    this.g[i2] = Pattern.compile(this.f[i2]);
                }
            }
            for (Pattern matcher : this.g) {
                if (matcher.matcher(cVar.d()).matches()) {
                    return true;
                }
            }
        } else if (H5PageData.KEY_UC_START.equals(this.d)) {
            for (String startsWith : this.f) {
                if (str.startsWith(startsWith)) {
                    return true;
                }
            }
        }
        return false;
    }
}
