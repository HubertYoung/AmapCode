package defpackage;

import android.text.TextUtils;
import com.autonavi.common.tool.CrashLogUtil;
import com.autonavi.common.tool.dumpcrash;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;

/* renamed from: bmp reason: default package */
/* compiled from: CrashLogRecorder */
public final class bmp {
    public static List<String> a = new ArrayList();
    private static final TreeMap<String, String> b = new TreeMap<>();

    public static void b() {
    }

    public static void a(String str, int i) {
        a(str, Integer.toString(i));
    }

    public static void a(String str, boolean z) {
        a(str, z ? "1" : "0");
    }

    public static void a(String str, String str2) {
        if (str == null) {
            throw new IllegalArgumentException();
        } else if (CrashLogUtil.isInited()) {
            if (str2 == null) {
                str2 = "<null>";
            } else if (str2.equals("")) {
                str2 = "<empty>";
            }
            if (CrashLogUtil.debugMode()) {
                StringBuilder sb = new StringBuilder("record: key=");
                sb.append(str);
                sb.append("; value=");
                sb.append(str2);
            }
            synchronized (b) {
                b.put(str, str2);
            }
            dumpcrash.recordKeyValue(str, str2);
        }
    }

    public static String a() {
        StringBuilder sb = new StringBuilder();
        synchronized (b) {
            sb.append("===[START] KeyValue Size:");
            sb.append(b.size());
            sb.append(", Log Buffer Index:0===\n\n");
            sb.append("[KeyValue]\n");
            for (Entry next : b.entrySet()) {
                sb.append((String) next.getKey());
                sb.append(": ");
                sb.append((String) next.getValue());
                sb.append("\n");
            }
            sb.append("\n===[END] KeyValue & Log===\n");
        }
        return sb.toString();
    }

    @Deprecated
    public static void a(String str) {
        if (!TextUtils.isEmpty(str)) {
            a.add(str);
        }
    }
}
