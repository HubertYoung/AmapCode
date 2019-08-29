package defpackage;

import com.taobao.tlog.adapter.AdapterForTLog;

/* renamed from: ap reason: default package */
/* compiled from: TLogAdapter */
public final class ap implements a {
    public final void a(String str, String str2) {
        AdapterForTLog.logd(str, str2);
    }

    public final void b(String str, String str2) {
        AdapterForTLog.logi(str, str2);
    }

    public final void c(String str, String str2) {
        AdapterForTLog.logw(str, str2);
    }

    public final void a(String str, String str2, Throwable th) {
        AdapterForTLog.logw(str, str2, th);
    }

    public final void d(String str, String str2) {
        AdapterForTLog.loge(str, str2);
    }

    public final void e(String str, String str2) {
        AdapterForTLog.loge(str, str2);
    }

    public final boolean a(int i) {
        int i2;
        switch (AdapterForTLog.getLogLevel().charAt(0)) {
            case 'D':
                i2 = 1;
                break;
            case 'E':
                i2 = 4;
                break;
            case 'I':
                i2 = 2;
                break;
            case 'V':
                i2 = 0;
                break;
            case 'W':
                i2 = 3;
                break;
            default:
                i2 = 5;
                break;
        }
        return i >= i2;
    }

    public final boolean a() {
        return AdapterForTLog.isValid();
    }
}
