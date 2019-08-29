package defpackage;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.view.View;
import com.autonavi.bundle.busnavi.ajx.ModuleBus;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.IMapPage;
import com.autonavi.map.fragmentcontainer.page.IMapPagePresenter;
import com.autonavi.minimap.route.bus.navidetail.view.BusNaviDetailDialogFactory.OnDialogClickListener;

/* renamed from: dws reason: default package */
/* compiled from: IBusNaviDetailContract */
public interface dws {

    /* renamed from: dws$a */
    /* compiled from: IBusNaviDetailContract */
    public interface a extends IMapPage {
        void a(float f);

        void a(Class<? extends bid> cls, PageBundle pageBundle, int i);

        void a(String str);

        void a(boolean z);

        void a(boolean z, String str, @Nullable OnDialogClickListener onDialogClickListener);

        void b();

        void b(boolean z);

        void c();

        boolean d();

        Activity e();

        View f();

        PageBundle g();
    }

    /* renamed from: dws$b */
    /* compiled from: IBusNaviDetailContract */
    public interface b extends IMapPagePresenter {
        void a();

        void a(ModuleBus moduleBus);

        void a(PageBundle pageBundle);

        void a(boolean z);

        void b();

        String c();

        boolean d();

        boolean e();
    }
}
