package defpackage;

import android.view.MotionEvent;
import android.view.ViewGroup;
import com.autonavi.ae.gmap.gloverlay.BaseOverlay;
import com.autonavi.bundle.mapevent.listener.MainMapEventListener;
import com.autonavi.bundle.uitemplate.container.SlideContainer.b;
import com.autonavi.map.core.MapManager;

/* renamed from: arn reason: default package */
/* compiled from: IMapHomePage */
public interface arn extends bid {
    void a();

    boolean a(b bVar);

    void addMainMapEventListener(MainMapEventListener mainMapEventListener);

    void addOverlay(BaseOverlay baseOverlay);

    void b();

    boolean b(b bVar);

    void c();

    void d();

    aqw e();

    boolean f();

    void g();

    MapManager getMapManager();

    bty getMapView();

    cde getSuspendManager();

    void h();

    boolean i();

    ViewGroup j();

    boolean onMapTouchEvent(MotionEvent motionEvent);

    void removeMainMapEventListener(MainMapEventListener mainMapEventListener);
}
