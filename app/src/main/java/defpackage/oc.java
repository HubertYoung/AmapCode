package defpackage;

import android.view.View;
import com.amap.bundle.datamodel.poi.POIFactory;
import com.amap.bundle.drivecommon.tools.DriveUtil;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.common.model.POI;
import com.autonavi.map.core.MapManager;
import com.autonavi.minimap.R;
import com.autonavi.minimap.basemap.errorback.inter.IReportErrorManager;
import com.autonavi.minimap.basemap.errorback.model.ReportErrorBean;
import com.autonavi.minimap.basemap.errorback.navi.ErrorType;
import com.autonavi.sdk.location.LocationInstrument;
import java.util.LinkedHashSet;
import java.util.List;

/* renamed from: oc reason: default package */
/* compiled from: ManualScreenShoter */
public final class oc extends oa {
    public IReportErrorManager e;
    public String f;
    public List<POI> g;
    public POI h;
    public POI i;
    public String j;
    public b k;
    public ErrorType l;
    public LinkedHashSet m;
    public sd n;
    private boolean o;

    /* renamed from: oc$a */
    /* compiled from: ManualScreenShoter */
    public static class a {
        public MapManager a;
        public sp b;
        public IReportErrorManager c;
        public String d;
        public LinkedHashSet e;
        public View f;
        public b g;
        public String h;
        public ErrorType i;
    }

    /* renamed from: oc$b */
    /* compiled from: ManualScreenShoter */
    public interface b {
    }

    public oc(View view, MapManager mapManager) {
        super(view, mapManager);
    }

    public final void a(int i2, int i3) {
        ErrorType errorType = this.l;
        if ((this.a == null || this.b == null || this.e == null) ? false : true) {
            this.o = false;
            this.e.setCurrentBean(null);
            POI createPOI = POIFactory.createPOI();
            createPOI.setPoint(LocationInstrument.getInstance().getLatestPosition());
            ReportErrorBean reportErrorBean = new ReportErrorBean(this.f, "", this.h, this.i, this.g, createPOI, this.j);
            if (this.m != null) {
                reportErrorBean.expand = tl.a().a(AMapAppGlobal.getApplication().getApplicationContext(), DriveUtil.generateNaviIDString(this.m), this.n);
            } else {
                reportErrorBean.expand = tl.a().a(AMapAppGlobal.getApplication().getApplicationContext(), "", this.n);
            }
            reportErrorBean.title = AMapAppGlobal.getApplication().getResources().getString(R.string.report_error_location);
            reportErrorBean.errortype = errorType.getType();
            this.e.setCurrentBean(reportErrorBean);
            this.e.setNaviErrorReportFlag(this.f);
        }
        super.a(i2, i3);
    }

    public final void a(String str) {
        if (this.e != null) {
            ReportErrorBean currentBean = this.e.getCurrentBean();
            currentBean.errorImgUrl = str;
            this.e.setCurrentBean(this.e.saveOrUpdate(currentBean));
        }
        this.o = true;
    }
}
