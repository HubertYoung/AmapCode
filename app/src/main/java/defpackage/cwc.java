package defpackage;

import com.autonavi.common.Callback;
import com.autonavi.minimap.bundle.apm.internal.report.SendManager$1;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/* renamed from: cwc reason: default package */
/* compiled from: SendManager */
public final class cwc {
    public static byte a = 1;
    private static long b = System.currentTimeMillis();
    private static List<Object> c = new ArrayList();

    /* renamed from: cwc$a */
    /* compiled from: SendManager */
    public static class a {
        public int a = -1;

        a() {
        }
    }

    public static boolean a(File file) {
        if (file == null) {
            return false;
        }
        a aVar = new a();
        zp.a(file, (Callback<Integer>) new SendManager$1<Integer>(aVar, file));
        int i = 0;
        while (aVar.a == -1 && i < 50) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException unused) {
            }
            i++;
        }
        if (aVar.a == 200) {
            return true;
        }
        return false;
    }
}
