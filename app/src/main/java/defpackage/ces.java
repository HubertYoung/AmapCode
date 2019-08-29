package defpackage;

import com.autonavi.map.suspend.refactor.scale.ScaleView;

/* renamed from: ces reason: default package */
/* compiled from: ScaleManager */
public final class ces {
    private cdc a;

    public ces(cdc cdc) {
        this.a = cdc;
    }

    public final ScaleView a() {
        ScaleView scaleView = new ScaleView(this.a.a(), this.a.e());
        scaleView.setScaleStatus(0);
        scaleView.setMapManager(this.a.b());
        scaleView.getScaleLineView().mAlignRight = false;
        scaleView.setPadding(0, 0, 0, agn.a(this.a.a(), 6.0f));
        scaleView.setContentDescription(null);
        return scaleView;
    }
}
