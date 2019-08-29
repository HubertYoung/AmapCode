package defpackage;

import android.text.TextUtils;
import java.io.File;
import java.util.List;
import java.util.Map;

/* renamed from: lk reason: default package */
/* compiled from: GlobalDBDownloadCallback */
public final class lk implements bjf {
    public static String a = "GlobalDBDownloadCallback";
    String b;
    String c;

    public final void onError(int i, int i2) {
    }

    public final void onProgressUpdate(long j, long j2) {
    }

    public final void onStart(long j, Map<String, List<String>> map, int i) {
    }

    lk(String str, String str2) {
        this.b = str;
        this.c = str2;
    }

    public final void onFinish(bpk bpk) {
        ahm.a(new Runnable() {
            public final void run() {
                StringBuilder sb = new StringBuilder();
                sb.append(lk.this.b);
                sb.append(ll.b);
                File file = new File(sb.toString());
                String a2 = agy.a(file, null, true);
                if (TextUtils.isEmpty(a2) || !a2.equals(lk.this.c)) {
                    file.delete();
                } else {
                    lk.a(lk.this);
                }
            }
        });
    }

    static /* synthetic */ void a(lk lkVar) {
        File file = new File(lkVar.b, ll.a);
        if (file.exists()) {
            file.delete();
        }
        File file2 = new File(lkVar.b, ll.b);
        File file3 = new File(lkVar.b, ll.a);
        if (file2.exists()) {
            file2.renameTo(file3);
        }
    }
}
