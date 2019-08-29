package com.alibaba.baichuan.android.jsbridge.a;

import com.alibaba.baichuan.android.jsbridge.plugin.AlibcPluginManager;

public class c {
    public static void a() {
        AlibcPluginManager.registerPlugin(b.a, b.class, true);
        AlibcPluginManager.registerPlugin("AliBCNetWork", d.class, true);
        AlibcPluginManager.registerPlugin(a.a, a.class, true);
        AlibcPluginManager.registerPlugin(f.a, f.class, true);
    }
}
