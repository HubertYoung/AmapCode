package defpackage;

import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import com.autonavi.map.fragmentcontainer.page.IMapPage;
import com.autonavi.map.suspend.refactor.gps.GPSButton;
import com.autonavi.map.suspend.refactor.scale.ScaleView;
import com.autonavi.map.suspend.refactor.zoom.ZoomView;

/* renamed from: ccu reason: default package */
/* compiled from: ISuspendWidgetHelper */
public interface ccu {

    /* renamed from: ccu$a */
    /* compiled from: ISuspendWidgetHelper */
    public interface a {
        boolean a();

        boolean b();

        boolean c();
    }

    View a();

    View a(boolean z);

    View a(boolean z, a aVar);

    void a(Configuration configuration);

    void a(View view);

    void a(View view, View view2, LayoutParams layoutParams, int i);

    void a(cdp cdp);

    void a(defpackage.ceb.a aVar);

    void a(@NonNull IMapPage iMapPage);

    void a(GPSButton gPSButton);

    View b(boolean z);

    void b();

    void b(View view);

    View c(boolean z);

    LinearLayout.LayoutParams c();

    GPSButton d();

    void d(boolean z);

    View e(boolean z);

    LinearLayout.LayoutParams e();

    ScaleView f();

    LinearLayout.LayoutParams g();

    View h();

    LinearLayout.LayoutParams i();

    View j();

    LinearLayout.LayoutParams k();

    ZoomView l();

    LinearLayout.LayoutParams m();

    View n();

    @Nullable
    ViewGroup o();

    LinearLayout.LayoutParams p();

    LinearLayout.LayoutParams q();

    void r();

    void s();

    void t();

    void u();
}
