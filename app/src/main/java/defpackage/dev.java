package defpackage;

import com.autonavi.map.fragmentcontainer.page.IMapPage;
import com.autonavi.map.suspend.refactor.scale.ScaleView;

/* renamed from: dev reason: default package */
/* compiled from: CarErrorReportSuspendViewHelper */
public final class dev extends ccv {
    private ccy a;

    public dev(IMapPage iMapPage) {
        super(iMapPage.getContext());
        this.a = iMapPage.getSuspendWidgetHelper();
        addWidget(this.a.d(), this.a.e(), 3);
        addWidget(this.a.l(), this.a.m(), 6);
        ScaleView f = this.a.f();
        addWidget(f, this.a.g(), 7);
        f.setScaleStatus(0);
        f.changeLogoStatus(true);
        addWidget(this.a.a(), this.a.c(), 1);
    }
}
