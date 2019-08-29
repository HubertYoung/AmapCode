package defpackage;

import java.util.List;
import java.util.Map;

/* renamed from: ath reason: default package */
/* compiled from: CarLogoDownloadCallback */
public final class ath implements bjf {
    String a;
    private String b;
    private String c;
    private String d;

    public final void onError(int i, int i2) {
    }

    public final void onProgressUpdate(long j, long j2) {
    }

    public final void onStart(long j, Map<String, List<String>> map, int i) {
    }

    public ath(String str, String str2, String str3) {
        this.b = str;
        this.c = str2;
        this.d = str3;
    }

    public final void onFinish(bpk bpk) {
        ati.a(this.b, this.a, this.d, this.c);
    }
}
