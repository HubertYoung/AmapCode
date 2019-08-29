package defpackage;

import com.amap.bundle.blutils.app.ConfigerHelper;
import com.autonavi.bundle.openlayer.entity.LayerItem;
import java.util.ArrayList;
import java.util.List;

/* renamed from: awo reason: default package */
/* compiled from: IOpenLayerService */
public interface awo extends esc {
    public static final String c;
    public static final String d;

    /* renamed from: awo$a */
    /* compiled from: IOpenLayerService */
    public interface a {
        void a();
    }

    /* renamed from: awo$b */
    /* compiled from: IOpenLayerService */
    public interface b {
    }

    void a(int i);

    void a(int i, boolean z);

    void a(a aVar);

    void a(b bVar);

    void a(String str);

    void a(List<Integer> list);

    void a(boolean z);

    boolean a();

    void b();

    void b(int i);

    void b(boolean z);

    void c();

    void c(int i);

    boolean d();

    boolean d(int i);

    void e();

    boolean e(int i);

    void f();

    void g();

    long h();

    ArrayList<LayerItem> i();

    ArrayList<Integer> j();

    static {
        StringBuilder sb = new StringBuilder("{\"update_period\": 300,\"cachemode\":2, \"url\": \"");
        sb.append(ConfigerHelper.getInstance().getTrafficEventEngineUrl());
        sb.append("\",\"bounds\":[{\"y1\": 122421247,\"x2\": 235405312, \"x1\": 188874751, \"y2\": 85065727}], \"sublyr\": [{\"type\": 4, \"sid\": 9000004, \"zlevel\": 2}], \"minzoom\": 6, \"maxzoom\": 20, \"id\":9001}");
        c = sb.toString();
        StringBuilder sb2 = new StringBuilder("{\"update_period\": 90, \"cachemode\":2,\"url\": \"");
        sb2.append(ConfigerHelper.getInstance().getTrafficEventEngineUrl());
        sb2.append("\",\"bounds\":[{\"y1\": 122421247,\"x2\": 235405312, \"x1\": 188874751, \"y2\": 85065727}], \"sublyr\": [{\"type\": 4, \"sid\": 9000003}], \"minzoom\": 6, \"maxzoom\": 20, \"id\":9003}");
        d = sb2.toString();
    }
}
