package defpackage;

import com.autonavi.common.Page.ON_BACK_TYPE;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.minimap.ajx3.Ajx3Page;
import com.autonavi.minimap.ajx3.Ajx3PagePresenter;
import com.autonavi.minimap.bundle.locationselect.page.SelectFixPoiFromMapAjx3Page;
import com.autonavi.minimap.search.model.SearchConst;
import com.autonavi.minimap.search.model.SelectPoiFromMapBean;

/* renamed from: cyr reason: default package */
/* compiled from: SelectFixPoiFromMapAjx3PagePresenter */
public final class cyr extends Ajx3PagePresenter {
    private POI a = null;
    private POI b;
    private GeoPoint c;
    private String d = SearchConst.b;
    private POI e;
    private POI f;
    private SelectFixPoiFromMapAjx3Page g;

    public cyr(SelectFixPoiFromMapAjx3Page selectFixPoiFromMapAjx3Page) {
        super(selectFixPoiFromMapAjx3Page);
        this.g = selectFixPoiFromMapAjx3Page;
    }

    public final void onPageCreated() {
        super.onPageCreated();
        PageBundle arguments = ((Ajx3Page) this.mPage).getArguments();
        if (arguments.containsKey("SelectPoiFromMapBean")) {
            SelectPoiFromMapBean selectPoiFromMapBean = (SelectPoiFromMapBean) arguments.getObject("SelectPoiFromMapBean");
            if (selectPoiFromMapBean != null) {
                this.a = selectPoiFromMapBean.getOldPOI();
                if (this.a != null) {
                    this.c = selectPoiFromMapBean.getOldPOI().getPoint();
                }
                this.e = selectPoiFromMapBean.getFromPOI();
                this.f = selectPoiFromMapBean.getToPOI();
                if (selectPoiFromMapBean.getLevel() > 0) {
                    ((Ajx3Page) this.mPage).getMapView().e(selectPoiFromMapBean.getLevel());
                }
            }
        }
    }

    public final void onStart() {
        super.onStart();
    }

    public final void onMapSurfaceChanged(int i, int i2) {
        super.onMapSurfaceChanged(i, i2);
    }

    public final ON_BACK_TYPE onBackPressed() {
        this.b = null;
        SelectFixPoiFromMapAjx3Page selectFixPoiFromMapAjx3Page = this.g;
        selectFixPoiFromMapAjx3Page.setResult(ResultType.CANCEL, (PageBundle) null);
        selectFixPoiFromMapAjx3Page.finish();
        return ON_BACK_TYPE.TYPE_IGNORE;
    }
}
