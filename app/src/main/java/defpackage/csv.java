package defpackage;

import android.view.View;
import com.autonavi.bundle.uitemplate.mapwidget.IMapWidgetManagerService;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetListener;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;
import com.autonavi.bundle.uitemplate.mapwidget.widget.layer.LayerWidgetPresenter;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.common.utils.Constant;
import com.autonavi.minimap.basemap.traffic.page.TrafficMainMapPage;

/* renamed from: csv reason: default package */
/* compiled from: TrafficMapWidgetManager */
public final class csv extends WidgetListener<TrafficMainMapPage> {
    public IMapWidgetManagerService a = ((IMapWidgetManagerService) ank.a(IMapWidgetManagerService.class));
    private TrafficMainMapPage b;

    public csv(TrafficMainMapPage trafficMainMapPage) {
        this.b = trafficMainMapPage;
    }

    public final void onClick(View view, String str) {
        if (((str.hashCode() == 102749521 && str.equals(WidgetType.LAYER)) ? (char) 0 : 65535) == 0) {
            LayerWidgetPresenter.logClick();
            PageBundle pageBundle = new PageBundle();
            pageBundle.putString(Constant.KEY_TRAFFIC_RESULT, Constant.OPEN_MAPLAYER_DRAWER);
            this.b.setResult(ResultType.OK, pageBundle);
            this.b.finish();
        }
    }
}
