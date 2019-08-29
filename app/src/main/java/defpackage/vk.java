package defpackage;

import com.autonavi.jni.eyrie.amap.glphy.GlyphLoaderFactoryImpl;
import com.autonavi.jni.eyrie.amap.maps.MapViewManager;

/* renamed from: vk reason: default package */
/* compiled from: EyrieMapManager */
public class vk {
    private static vk c;
    public final amj a = new vj();
    public final c b = new vi();

    public static vk a() {
        if (c == null) {
            synchronized (vk.class) {
                try {
                    if (c == null) {
                        c = new vk();
                    }
                }
            }
        }
        return c;
    }

    public static void a(int i) {
        MapViewManager.newMapView(i);
        MapViewManager.setGlyphLoaderFactory(new GlyphLoaderFactoryImpl());
    }

    public static void b() {
        MapViewManager.uninit();
    }

    private vk() {
    }
}
