package defpackage;

import android.text.TextUtils;
import com.amap.bundle.searchservice.api.ISearchService;
import com.amap.bundle.statistics.LogManager;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.bl.search.InfoliteParam;
import com.autonavi.bl.search.OfflineParam;
import com.autonavi.bundle.entity.infolite.internal.InfoliteResult;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.minimap.basemap.traffic.TrafficUtil;
import com.autonavi.minimap.map.DPoint;
import com.autonavi.sdk.location.LocationInstrument;
import com.autonavi.sdk.log.util.LogConstant;
import java.util.AbstractMap.SimpleEntry;
import java.util.List;

/* renamed from: elr reason: default package */
/* compiled from: SearchServiceProxy */
public final class elr {
    private final ISearchService a;

    /* renamed from: elr$a */
    /* compiled from: SearchServiceProxy */
    static class a extends elp<InfoliteResult> {
        a(bbs<InfoliteResult> bbs) {
            super(bbs);
        }

        public final void a(InfoliteResult infoliteResult) {
            super.a(infoliteResult);
            List<aup> o = bcy.o(infoliteResult);
            boolean z = false;
            if (o != null && o.size() > 0) {
                LogManager.actionLogV25(LogConstant.SEARCH_RESULT_LIST, LogConstant.MAIN_MAP_GUIDE_MAP_SHOW, new SimpleEntry(TrafficUtil.KEYWORD, bcy.f(infoliteResult) ? infoliteResult.mWrapper.keywords : ""));
            }
            if (!(infoliteResult == null || infoliteResult.responseHeader == null || infoliteResult.responseHeader.f)) {
                z = true;
            }
            if (z) {
                a();
            }
        }

        public final void a(int i) {
            super.a(i);
            if (i == 3) {
                a();
            }
        }

        private static void a() {
            LogManager.actionLogV25("P00158", "B005", new SimpleEntry("type", Integer.valueOf(1)));
        }
    }

    /* renamed from: elr$b */
    /* compiled from: SearchServiceProxy */
    public static class b {
        public static final elr a = new elr(0);

        static {
            AMapAppGlobal.getApplication();
        }
    }

    /* synthetic */ elr(byte b2) {
        this();
    }

    private elr() {
        this.a = (ISearchService) defpackage.esb.a.a.a(ISearchService.class);
    }

    public final adx a(InfoliteParam infoliteParam, int i, bbs<InfoliteResult> bbs) {
        infoliteParam.offline_param = a(infoliteParam);
        final a aVar = new a(bbs);
        return this.a.a(infoliteParam, i, (aeb<InfoliteResult>) new aeb<InfoliteResult>() {
            public final /* synthetic */ void callback(Object obj) {
                aVar.a((InfoliteResult) obj);
            }

            public final void error(int i) {
                aVar.a(i);
            }
        });
    }

    private static OfflineParam a(InfoliteParam infoliteParam) {
        OfflineParam offlineParam = new OfflineParam();
        offlineParam.keyword = infoliteParam.keywords;
        if ("IDQ".equals(infoliteParam.query_type)) {
            offlineParam.keyword = infoliteParam.id;
        }
        offlineParam.resultMaxCount = 200;
        offlineParam.searchForm = 2;
        offlineParam.searchType = b(infoliteParam);
        offlineParam.languageType = 0;
        offlineParam.aroundRadius = 20000;
        GeoPoint c = c(infoliteParam);
        offlineParam.adcode = c.getAdCode();
        offlineParam.latitude = c.getLatitude();
        offlineParam.longitude = c.getLongitude();
        return offlineParam;
    }

    private static int b(InfoliteParam infoliteParam) {
        if ("TQUERY".equals(infoliteParam.query_type)) {
            return 1;
        }
        if ("RQBXY".equals(infoliteParam.query_type)) {
            return 2;
        }
        if ("IDQ".equals(infoliteParam.query_type)) {
            return 5;
        }
        return 1;
    }

    private static GeoPoint c(InfoliteParam infoliteParam) {
        lj ljVar;
        if (!TextUtils.isEmpty(infoliteParam.city)) {
            if (infoliteParam.city.length() < 6) {
                ljVar = li.a().a(infoliteParam.city);
            } else {
                ljVar = li.a().a((int) ((long) Integer.parseInt(infoliteParam.city)));
            }
            if (ljVar != null) {
                return new GeoPoint(ljVar.f, ljVar.g);
            }
        }
        if (!"TQUERY".equals(infoliteParam.query_type)) {
            return new GeoPoint(infoliteParam.longitude, infoliteParam.latitude);
        }
        GeoPoint glGeoPoint2GeoPoint = GeoPoint.glGeoPoint2GeoPoint(DoNotUseTool.getMapCenter());
        li a2 = li.a();
        GeoPoint latestPosition = LocationInstrument.getInstance().getLatestPosition();
        if (!(latestPosition == null || a2 == null)) {
            int a3 = a2.a(latestPosition.getLongitude(), latestPosition.getLatitude());
            if (glGeoPoint2GeoPoint != null && a3 == glGeoPoint2GeoPoint.getAdCode()) {
                glGeoPoint2GeoPoint = latestPosition;
            }
        }
        if (glGeoPoint2GeoPoint == null) {
            return latestPosition;
        }
        DPoint a4 = cfg.a((long) glGeoPoint2GeoPoint.x, (long) glGeoPoint2GeoPoint.y);
        return new GeoPoint(a4.x, a4.y);
    }
}
