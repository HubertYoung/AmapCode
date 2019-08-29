package defpackage;

import com.autonavi.bundle.openlayer.entity.LayerItem;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import java.util.List;

/* renamed from: bsc reason: default package */
/* compiled from: MapLayerDrawerContract */
public interface bsc {

    /* renamed from: bsc$a */
    /* compiled from: MapLayerDrawerContract */
    public static class a implements d {
        public final boolean a() {
            return new bnv().a();
        }

        public final boolean b() {
            if (!(AMapPageUtil.getPageContext() instanceof AbstractBasePage) || DoNotUseTool.getSuspendManager() == null || DoNotUseTool.getSuspendManager().i() == null) {
                return false;
            }
            return DoNotUseTool.getSuspendManager().i().b();
        }
    }

    /* renamed from: bsc$b */
    /* compiled from: MapLayerDrawerContract */
    public static class b implements c {
    }

    /* renamed from: bsc$c */
    /* compiled from: MapLayerDrawerContract */
    public interface c {
    }

    /* renamed from: bsc$d */
    /* compiled from: MapLayerDrawerContract */
    public interface d {
        boolean a();

        boolean b();
    }

    /* renamed from: bsc$e */
    /* compiled from: MapLayerDrawerContract */
    public interface e extends h {
        void a();

        void b();
    }

    /* renamed from: bsc$f */
    /* compiled from: MapLayerDrawerContract */
    public interface f {
        void a(int i);

        void a(boolean z);

        void b(int i);

        void b(boolean z);

        void c();

        void c(boolean z);

        void d();

        void d(boolean z);

        void e();

        void e(boolean z);

        boolean f();
    }

    /* renamed from: bsc$g */
    /* compiled from: MapLayerDrawerContract */
    public interface g<T extends f> {
        void a();

        void a(int i);

        void a(d dVar);

        void a(e eVar);

        void a(T t);

        void a(List<LayerItem> list);

        void a(boolean z);

        void b();

        void b(boolean z);

        void c(boolean z);

        void d(boolean z);

        void e(boolean z);

        void f(boolean z);
    }

    /* renamed from: bsc$h */
    /* compiled from: MapLayerDrawerContract */
    public interface h {
        void g();
    }
}
