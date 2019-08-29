package defpackage;

import java.util.HashMap;

/* renamed from: cvg reason: default package */
/* compiled from: PluginNameBuilder */
public final class cvg {
    private static HashMap<Integer, String> a;

    static {
        HashMap<Integer, String> hashMap = new HashMap<>();
        a = hashMap;
        hashMap.put(Integer.valueOf(1), "MainThreadBlockPlugin");
        a.put(Integer.valueOf(2), "CpuPlugin");
        a.put(Integer.valueOf(3), "SmoothPlugin");
        a.put(Integer.valueOf(4), "MemoryPlugin");
        a.put(Integer.valueOf(5), "LaunchPlugin");
    }

    public static String a(int i) {
        return a.get(Integer.valueOf(i));
    }
}
