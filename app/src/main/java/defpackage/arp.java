package defpackage;

import com.autonavi.bundle.amaphome.page.MapHomeTabPage;
import com.autonavi.common.Page.ON_BACK_TYPE;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.statusbar.StatusBarManager;

/* renamed from: arp reason: default package */
/* compiled from: MapHomeTabPresenter */
public final class arp extends beq<MapHomeTabPage> {
    public arp(MapHomeTabPage mapHomeTabPage) {
        super(mapHomeTabPage);
    }

    public final void onStart() {
        super.onStart();
        ((MapHomeTabPage) this.a).requestScreenOrientation(1);
        StatusBarManager.d().a(AMapPageUtil.getPageContext());
    }

    public final void onStop() {
        super.onStop();
        StatusBarManager.d().b(AMapPageUtil.getPageContext());
    }

    public final ON_BACK_TYPE onBackPressed() {
        beo e = ((MapHomeTabPage) this.a).e();
        if (e == null || !e.o()) {
            return ON_BACK_TYPE.TYPE_FINISH;
        }
        return ON_BACK_TYPE.TYPE_IGNORE;
    }
}
