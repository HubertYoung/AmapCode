package defpackage;

import com.amap.bundle.aosservice.request.AosMultipartRequest;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosStringResponse;
import com.amap.bundle.dumpcrash.CrashUploadParam;
import com.amap.bundle.mapstorage.MapSharePreference;
import java.io.File;

/* renamed from: uj reason: default package */
/* compiled from: DumpEventListener */
public final class uj {
    uc a;
    long b = 0;
    private volatile MapSharePreference c;

    uj(uc ucVar) {
        this.a = ucVar;
    }

    /* access modifiers changed from: 0000 */
    public final MapSharePreference a() {
        if (this.c == null) {
            synchronized (this) {
                try {
                    if (this.c == null) {
                        this.c = new MapSharePreference((String) "dumpcrash_pref");
                    }
                }
            }
        }
        return this.c;
    }

    static String a(String str, File file) {
        AosMultipartRequest c2 = aax.c(new CrashUploadParam(str));
        if (file != null && file.exists()) {
            c2.a((String) "file", file);
        }
        try {
            yq.a();
            AosStringResponse aosStringResponse = (AosStringResponse) yq.a((AosRequest) c2, AosStringResponse.class);
            if (aosStringResponse == null) {
                return null;
            }
            return (String) aosStringResponse.getResult();
        } catch (Throwable th) {
            th.printStackTrace();
            return null;
        }
    }
}
