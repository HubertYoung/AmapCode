package test.tinyapp.alipay.com.testlibrary.service.performancepanel.bean;

import android.text.TextUtils;

/* compiled from: DisplayItemInfo */
public final class a {
    private String a;
    private PerformanceItemInfo b;
    private int c;

    private a(int itemType, String title, PerformanceItemInfo performanceItemInfo) {
        this.c = itemType;
        this.a = title;
        this.b = performanceItemInfo;
    }

    public static a a(String title) {
        return new a(1, title, null);
    }

    public static a a(String title, String performanceName, String performanceValue) {
        return new a(2, title, PerformanceItemInfo.a(title, performanceName, performanceValue));
    }

    public final String a() {
        return this.a;
    }

    public final PerformanceItemInfo b() {
        return this.b;
    }

    public final int c() {
        return this.c;
    }

    public final String toString() {
        if (this.b != null) {
            return this.b.toString();
        }
        return TextUtils.isEmpty(this.a) ? "empty info" : this.a;
    }
}
