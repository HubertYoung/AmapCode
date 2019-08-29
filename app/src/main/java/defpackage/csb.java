package defpackage;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import com.autonavi.map.core.IOverlayManager;
import com.autonavi.map.fragmentcontainer.page.MapBasePage;
import com.autonavi.map.suspend.SuspendViewBaseTemplate;
import com.autonavi.map.suspend.refactor.gps.GPSButton;
import com.autonavi.minimap.R;
import de.greenrobot.event.EventBus;

/* renamed from: csb reason: default package */
/* compiled from: ReleatedTrafficEventContract */
public interface csb {

    /* renamed from: csb$a */
    /* compiled from: ReleatedTrafficEventContract */
    public static abstract class a {
        private Context a;
        public MapBasePage b;
        public IOverlayManager c;
        protected cry d;
        private bty e;
        private int f;

        public a(MapBasePage mapBasePage) {
            if (mapBasePage == null) {
                throw new NullPointerException("mapBasePage can not be null!!!!!!");
            }
            this.b = mapBasePage;
            this.a = this.b.getContext();
            this.e = mapBasePage.getGLMapView();
            this.c = mapBasePage.getMapManager().getOverlayManager();
            this.d = (cry) ank.a(cry.class);
            this.d.a(this);
            EventBus.getDefault().register(this);
        }

        public final View e() {
            if (this.b == null) {
                return null;
            }
            return this.b.getTopMapInteractiveView();
        }

        public final View f() {
            if (this.b == null) {
                return null;
            }
            return this.b.getBottomMapInteractiveView();
        }

        public final boolean g() {
            if (this.d != null) {
                return this.d.d();
            }
            return false;
        }

        public final boolean h() {
            if (this.d != null) {
                return this.d.c();
            }
            return false;
        }

        public final void i() {
            if (this.d != null) {
                this.d.a();
            }
        }

        public final void a(View view) {
            if (this.d != null) {
                this.d.a(view);
            }
        }

        public final void j() {
            if (this.d != null) {
                this.d.b();
            }
        }

        public void a() {
            if (f() != null) {
                f().setVisibility(4);
            }
        }

        public int c() {
            int i = this.a.getResources().getDisplayMetrics().densityDpi;
            int i2 = 0;
            if (this.b == null) {
                return 0;
            }
            if (this.b.getSuspendWidgetHelper() != null) {
                View mapSuspendView = this.b.getMapSuspendView();
                if (mapSuspendView instanceof SuspendViewBaseTemplate) {
                    ViewGroup viewGroupByPosition = ((SuspendViewBaseTemplate) mapSuspendView).getViewGroupByPosition(3);
                    if (viewGroupByPosition != null && (viewGroupByPosition instanceof ViewGroup)) {
                        ViewGroup viewGroup = viewGroupByPosition;
                        if (viewGroup.getChildCount() > 0) {
                            int i3 = 0;
                            while (true) {
                                if (i3 >= viewGroup.getChildCount()) {
                                    break;
                                }
                                View childAt = viewGroup.getChildAt(i3);
                                if (childAt instanceof GPSButton) {
                                    i2 = childAt.getWidth();
                                    break;
                                }
                                i3++;
                            }
                        }
                    }
                }
            }
            return i2 == 0 ? this.b.getContentView().findViewById(R.id.GpsButton).getWidth() : i2;
        }

        public void b() {
            if (f() != null) {
                f().setVisibility(0);
            }
            a(true);
        }

        private void a(boolean z) {
            if (z) {
                this.b.getMapCustomizeManager().enableView(8192);
            } else {
                this.b.getMapCustomizeManager().disableView(8192);
            }
            View mapSuspendView = this.b.getMapSuspendView();
            if (mapSuspendView != null && (mapSuspendView instanceof SuspendViewBaseTemplate)) {
                ViewGroup viewGroupByPosition = ((SuspendViewBaseTemplate) mapSuspendView).getViewGroupByPosition(7);
                if (viewGroupByPosition != null) {
                    viewGroupByPosition.setVisibility(z ? 0 : 8);
                }
            }
        }

        public void a(int i, int i2, boolean z) {
            this.f = i;
            if (this.d != null) {
                this.d.a(i, i2, z);
            }
            a(i2 <= 0);
        }

        public static void k() {
            EventBus.getDefault().post(new e());
        }

        @NonNull
        public void onEventMainThread(@NonNull e eVar) {
            g();
        }

        public void d() {
            if (!(this.e == null || this.c == null)) {
                brp affectAreaOverlayManager = this.c.getAffectAreaOverlayManager();
                if (affectAreaOverlayManager != null && affectAreaOverlayManager.b()) {
                    DisplayMetrics displayMetrics = this.a.getResources().getDisplayMetrics();
                    this.e.b(displayMetrics.widthPixels / 2, (displayMetrics.heightPixels - this.f) / 2);
                }
            }
            if (this.d != null) {
                this.d.e();
            }
        }
    }

    /* renamed from: csb$b */
    /* compiled from: ReleatedTrafficEventContract */
    public interface b {
        void a();

        void a(int i, int i2, boolean z);

        void a(View view);

        void a(c cVar);

        void b();

        boolean c();

        void d();
    }

    /* renamed from: csb$c */
    /* compiled from: ReleatedTrafficEventContract */
    public interface c {
        void f();

        void g();
    }

    /* renamed from: csb$d */
    /* compiled from: ReleatedTrafficEventContract */
    public interface d {
        a getReleatedTrafficEventHandler();
    }

    /* renamed from: csb$e */
    /* compiled from: ReleatedTrafficEventContract */
    public static class e {
    }

    /* renamed from: csb$f */
    /* compiled from: ReleatedTrafficEventContract */
    public interface f {
    }
}
