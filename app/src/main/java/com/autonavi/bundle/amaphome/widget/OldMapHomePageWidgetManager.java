package com.autonavi.bundle.amaphome.widget;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout.LayoutParams;
import com.autonavi.bundle.amaphome.widget.header.OldHomePageHeader;
import com.autonavi.bundle.amaphome.widget.header.OldHomePageHeaderPresenter;
import com.autonavi.bundle.uitemplate.api.IWidgetProperty;
import com.autonavi.bundle.uitemplate.mapwidget.IMapWidgetManager.Stub;
import com.autonavi.bundle.uitemplate.mapwidget.custom.nearby.OldNearbySearchMapWidget;
import com.autonavi.bundle.uitemplate.mapwidget.custom.nearby.OldNearbySearchWidgetPresenter;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetProperty;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;
import com.autonavi.bundle.uitemplate.mapwidget.widget.diy.DIYMainMapPresenter;
import com.autonavi.bundle.uitemplate.mapwidget.widget.diy.DIYMainMapWidgetManager;
import com.autonavi.bundle.uitemplate.mapwidget.widget.homecorp.HomeCorpMapWidget;
import com.autonavi.bundle.uitemplate.mapwidget.widget.homecorp.HomeCorpWidgetPresenter;
import com.autonavi.bundle.uitemplate.mapwidget.widget.layer.LayerWidgetPresenter;
import com.autonavi.bundle.uitemplate.mapwidget.widget.routeline.OldRouteLineMapWidget;
import com.autonavi.bundle.uitemplate.mapwidget.widget.routeline.RouteLineWidgetPresenter;
import com.autonavi.bundle.uitemplate.mapwidget.widget.user.UserCenterWidgetPresenter;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.bundle.amaphome.ab.page.OldMapHomePage;

public class OldMapHomePageWidgetManager extends BaseHomePageWidgetManager<OldMapHomePage> {
    private OldHomePageHeaderPresenter mHeaderPresenter;
    private OldNearbySearchWidgetPresenter mNearByPresenter;
    private OldHomePageHeader mOldHomePageHeader;
    private RouteLineWidgetPresenter mRouteLinePresenter;

    public OldMapHomePageWidgetManager(OldMapHomePage oldMapHomePage) {
        super(oldMapHomePage);
    }

    public IWidgetProperty[] getPageMapWidgets() {
        setMapWidgetContainerPadding();
        if (this.mWidgetProperties == null) {
            int a = bet.a(AMapPageUtil.getAppContext(), 5);
            int a2 = bet.a(AMapPageUtil.getAppContext(), 3);
            setContainerPadding(a2, (a * 11) + a2, a2, a * 14);
            WidgetProperty widgetProperty = new WidgetProperty(1, 35, WidgetType.ACTIVITY, 1, setWidgetParams(0, 5, 0, 10));
            WidgetProperty widgetProperty2 = new WidgetProperty(1, 25, WidgetType.COMPASS, 2, setWidgetParams(5, 0, 0, 0));
            WidgetProperty widgetProperty3 = new WidgetProperty(4, 80, "diy", 1, setWidgetParams(0, 0, 1, 0));
            WidgetProperty widgetProperty4 = new WidgetProperty(3, 45, "floor", 2, setWidgetParams(5, 0, 0, 0));
            WidgetProperty widgetProperty5 = new WidgetProperty(6, 75, WidgetType.HOME_CORP, 0, setWidgetParams(0, 0, 1, 0));
            this.mWidgetProperties = new IWidgetProperty[]{new WidgetProperty(4, 60, (String) WidgetType.LAYER, 0), new WidgetProperty(1, 30, (String) WidgetType.MSG_BOX, 0), widgetProperty, widgetProperty2, widgetProperty3, new WidgetProperty(5, 20, (String) WidgetType.ZOOM_IN_OUT, 0), getGpsScaleCombineWidget(0), widgetProperty4, new WidgetProperty(3, 40, (String) WidgetType.AUTO_REMOTE, 1), widgetProperty5};
        }
        return this.mWidgetProperties;
    }

    /* access modifiers changed from: protected */
    public void setRouteLineDelegate() {
        if (this.mRouteLinePresenter != null) {
            this.mRouteLinePresenter.setEventDelegate(new asc());
        }
    }

    /* access modifiers changed from: protected */
    public void setNearbyDelegate() {
        if (this.mNearByPresenter != null) {
            this.mNearByPresenter.setEventDelegate(new asb());
            responseNearbyData("");
        }
    }

    public void registerListeners() {
        Stub.getMapWidgetManager().registerListener(WidgetType.LAYER, this);
        Stub.getMapWidgetManager().registerListener(WidgetType.GPS, Stub.getCombineWidgetsTag(WidgetType.GPS, WidgetType.SCALE), this);
    }

    public void addNearBySearchToPage() {
        FrameLayout l = this.mMapPage == null ? null : ((OldMapHomePage) this.mMapPage).l();
        if (l != null) {
            OldNearbySearchMapWidget oldNearbySearchMapWidget = new OldNearbySearchMapWidget(l.getContext());
            this.mNearByPresenter = (OldNearbySearchWidgetPresenter) oldNearbySearchMapWidget.getPresenter();
            l.addView(oldNearbySearchMapWidget.getContentView());
        }
    }

    private void setFooterVisible(int i) {
        FrameLayout l = this.mMapPage == null ? null : ((OldMapHomePage) this.mMapPage).l();
        if (l != null) {
            l.setVisibility(i);
        }
    }

    private void setRouteLineVisible(int i) {
        FrameLayout m = this.mMapPage == null ? null : ((OldMapHomePage) this.mMapPage).m();
        if (m != null) {
            m.setVisibility(i);
        }
    }

    public void addRouteToPage() {
        FrameLayout m = this.mMapPage == null ? null : ((OldMapHomePage) this.mMapPage).m();
        if (m != null) {
            OldRouteLineMapWidget oldRouteLineMapWidget = new OldRouteLineMapWidget(m.getContext());
            this.mRouteLinePresenter = (RouteLineWidgetPresenter) oldRouteLineMapWidget.getPresenter();
            m.addView(oldRouteLineMapWidget.getContentView());
        }
    }

    public void addHomePageHeader(bid bid) {
        if (this.mOldHomePageHeader == null && bid != null) {
            this.mOldHomePageHeader = new OldHomePageHeader(bid);
            this.mHeaderPresenter = (OldHomePageHeaderPresenter) this.mOldHomePageHeader.getPresenter();
            ((UserCenterWidgetPresenter) this.mOldHomePageHeader.getUserCenterMapWidget().getPresenter()).setEventDelegate(new asd());
            LayoutParams layoutParams = new LayoutParams(-1, -2);
            int a = bet.a(bid.getContext(), 3);
            layoutParams.leftMargin = a;
            layoutParams.rightMargin = a;
            layoutParams.bottomMargin = a;
            Stub.getMapWidgetManager().setHeaderView(this.mOldHomePageHeader.getContentView(), layoutParams);
        }
    }

    public void onPageResume(boolean z) {
        if (this.mHeaderPresenter != null && !z) {
            this.mHeaderPresenter.onPageResume(this.mMapPage);
        }
    }

    public void existImmersiveMode() {
        updateCustomWidgetVisible(0);
    }

    public void enterImmersiveMode() {
        updateCustomWidgetVisible(8);
    }

    public Rect getImmersiveModeMargins(Context context) {
        if (context != null) {
            return new Rect(0, -bet.a(context, 58), 0, -bet.a(context, 70));
        }
        return null;
    }

    private void updateCustomWidgetVisible(int i) {
        setRouteLineVisible(i);
        setFooterVisible(i);
        Stub.getMapWidgetManager().setHeaderVisibility(i);
    }

    public void responseNearbyData(String str) {
        if (this.mNearByPresenter != null) {
            this.mNearByPresenter.responseData(str);
        }
    }

    public void dispatchClickEvent(View view, String str) {
        if (((str.hashCode() == 102749521 && str.equals(WidgetType.LAYER)) ? (char) 0 : 65535) == 0) {
            LayerWidgetPresenter.logClick();
            aqw aqw = ((cun) ((OldMapHomePage) this.mMapPage).mPresenter).a;
            if (aqw != null && !aqw.c()) {
                aqw.a();
            }
        }
    }

    public void setDIYManager(DIYMainMapWidgetManager dIYMainMapWidgetManager) {
        ((DIYMainMapPresenter) Stub.getMapWidgetManager().getPresenter("diy")).init(dIYMainMapWidgetManager);
    }

    public void initFrequentLocationController(cya cya, bid bid) {
        HomeCorpWidgetPresenter homeCorpWidgetPresenter = (HomeCorpWidgetPresenter) Stub.getMapWidgetManager().getPresenter(WidgetType.HOME_CORP);
        homeCorpWidgetPresenter.init(bid);
        cya.b = homeCorpWidgetPresenter;
        cya.a = ((HomeCorpMapWidget) homeCorpWidgetPresenter.getWidget()).getFrequentLocationView();
    }

    public void onCover() {
        if (this.mHeaderPresenter != null) {
            this.mHeaderPresenter.dismissVUIGuideTip();
        }
    }

    public void onFullScreenStateChanged(boolean z) {
        if (this.mHeaderPresenter != null) {
            this.mHeaderPresenter.dismissVUIGuideTip();
        }
    }
}
