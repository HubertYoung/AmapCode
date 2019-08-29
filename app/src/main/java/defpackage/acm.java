package defpackage;

import android.text.TextUtils;
import com.alibaba.analytics.core.sync.UploadQueueMgr;
import com.amap.bundle.datamodel.poi.POIFactory;
import com.amap.bundle.planhome.page.AbstractPlanHomePage;
import com.autonavi.bundle.routecommon.inter.IRouteEditView.State;
import com.autonavi.bundle.routecommon.model.RouteType;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.POI;
import com.autonavi.common.model.RouteHeaderModel;
import com.autonavi.common.utils.Constant.SelectPoiFromMapFragment.SelectFor;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.map.fragmentcontainer.page.PageContainer;
import com.autonavi.minimap.basemap.traffic.TrafficUtil;

/* renamed from: acm reason: default package */
/* compiled from: PlanSearchCallbackUtil */
public class acm {
    public static acm a;
    public AbstractPlanHomePage b;
    public add c;

    public static acm a() {
        if (a == null) {
            synchronized (acm.class) {
                try {
                    if (a == null) {
                        a = new acm();
                    }
                }
            }
        }
        return a;
    }

    public final acm a(AbstractPlanHomePage abstractPlanHomePage, add add) {
        this.b = abstractPlanHomePage;
        this.c = add;
        return this;
    }

    public final void a(String str, int i, String str2, SelectFor selectFor, boolean z, int i2) {
        if (this.b != null) {
            PageBundle pageBundle = new PageBundle();
            pageBundle.putInt("search_for", 1);
            pageBundle.putObject("route_type", adf.a().b());
            pageBundle.putString("hint", str2);
            if (TextUtils.isEmpty(str) || !str.equalsIgnoreCase("我的位置")) {
                pageBundle.putString(TrafficUtil.KEYWORD, str);
            } else {
                pageBundle.putString(TrafficUtil.KEYWORD, "");
            }
            pageBundle.putBoolean("isHideMyPosition", false);
            pageBundle.putObject("selectedfor", selectFor);
            pageBundle.putInt("from_page", 12400);
            String str3 = "0";
            if (i == 1001 || i == 1003 || i == 1002) {
                str3 = UploadQueueMgr.MSGTYPE_REALTIME;
            } else if (i == 1004 || i == 1005) {
                str3 = "j";
            }
            RouteHeaderModel routeHeaderModel = new RouteHeaderModel();
            a(routeHeaderModel);
            routeHeaderModel.mEditStatus = this.c.r() == 1 ? State.PRE_EDIT : State.SUMMARY;
            PageContainer pageContainer = this.b.getPageContainer();
            if (pageContainer != null) {
                AbstractBasePage cureentRecordPage = pageContainer.getCureentRecordPage();
                if (cureentRecordPage != null) {
                    routeHeaderModel.mChildPageClass = cureentRecordPage.getClass();
                }
            }
            if (i2 < 0) {
                routeHeaderModel.mWidgetId = a(selectFor);
            } else {
                routeHeaderModel.mWidgetId = i2;
            }
            routeHeaderModel.mCanExchange = true;
            routeHeaderModel.mCanExchange = acq.a().e();
            pageBundle.putObject(RouteHeaderModel.ROUTE_HEADER_MODEL_KEY, routeHeaderModel);
            pageBundle.putString("SUPER_ID", str3);
            pageBundle.putBoolean("auto_search", z);
            this.b.startPageForResult((String) "drive.search.fragment.SearchCallbackFragment", pageBundle, i);
        }
    }

    public final void a(PageBundle pageBundle) {
        if (this.b != null) {
            RouteHeaderModel routeHeaderModel = new RouteHeaderModel();
            a(routeHeaderModel);
            routeHeaderModel.mEditStatus = this.c.r() == 1 ? State.PRE_EDIT : State.SUMMARY;
            PageContainer pageContainer = this.b.getPageContainer();
            if (pageContainer != null) {
                AbstractBasePage cureentRecordPage = pageContainer.getCureentRecordPage();
                if (cureentRecordPage != null) {
                    routeHeaderModel.mChildPageClass = cureentRecordPage.getClass();
                }
            }
            routeHeaderModel.mWidgetId = 16;
            routeHeaderModel.mCanExchange = true;
            routeHeaderModel.mCanExchange = acq.a().e();
            if (pageBundle != null) {
                routeHeaderModel.mWidgetId = pageBundle.getInt("route_edit_dispatch_widget_id", 16);
                pageBundle.putObject("route_type", adf.a().b());
                pageBundle.putObject(RouteHeaderModel.ROUTE_HEADER_MODEL_KEY, routeHeaderModel);
            }
            this.b.startPageForResult((String) "drive.search.fragment.SearchCallbackFragment", pageBundle, 1001);
        }
    }

    private static void a(RouteHeaderModel routeHeaderModel) {
        RouteType b2 = adf.a().b();
        POI b3 = ade.a().b(true);
        POI d = ade.a().d(true);
        if (b2 == RouteType.TAXI) {
            if (b3 == null) {
                b3 = POIFactory.createPOI();
            }
            b3.setName(acq.a().h());
            if (d != null) {
                d.setName(acq.a().i());
            }
        }
        routeHeaderModel.mStartPoi = b3;
        routeHeaderModel.mEndPoi = d;
        routeHeaderModel.mMidPois = ade.a().c();
    }

    private static int a(SelectFor selectFor) {
        int i = 16;
        if (selectFor == null) {
            return 16;
        }
        if (!(selectFor == SelectFor.DEFAULT_POI || selectFor == SelectFor.FROM_POI)) {
            if (selectFor == SelectFor.TO_POI) {
                i = 32;
            } else if (selectFor == SelectFor.MID_POI) {
                i = 768;
            } else if (selectFor == SelectFor.MID_POI_1) {
                i = 64;
            } else if (selectFor == SelectFor.MID_POI_2) {
                i = 80;
            } else if (selectFor == SelectFor.MID_POI_3) {
                i = 96;
            }
        }
        return i;
    }
}
