package com.autonavi.bundle.amaphome.widget;

import android.view.View;
import com.autonavi.bundle.amaphome.msg.IMapHomeMsgDispatchService;
import com.autonavi.bundle.amaphome.msg.IMapHomeMsgDispatchService.Sub;
import com.autonavi.bundle.uitemplate.mapwidget.IMapWidgetManager;
import com.autonavi.bundle.uitemplate.mapwidget.IMapWidgetManager.Stub;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;
import com.autonavi.bundle.uitemplate.mapwidget.manager.AbstractPageMapWidgetManager;
import com.autonavi.bundle.uitemplate.mapwidget.widget.activity.OperateActivityWidgetPresenter;
import com.autonavi.bundle.uitemplate.mapwidget.widget.msg.MsgGroupWidgetPresenter;
import com.autonavi.bundle.uitemplate.mapwidget.widget.routeline.RouteLineWidgetPresenter;
import com.autonavi.bundle.uitemplate.mapwidget.widget.search.NearBySearchWidgetPresenter;
import com.autonavi.bundle.uitemplate.mapwidget.widget.user.UserCenterWidgetPresenter;
import com.autonavi.map.fragmentcontainer.page.AbstractBaseMapPage;

public abstract class BaseHomePageWidgetManager<T extends AbstractBaseMapPage> extends AbstractPageMapWidgetManager<T> {
    public void dispatchClickEvent(View view, String str) {
    }

    public BaseHomePageWidgetManager(T t) {
        super(t);
    }

    public void initMapWidgetDelegate() {
        setMsgGroupDelegate();
        setUserDelegate();
        setNearbyDelegate();
        setRouteLineDelegate();
    }

    /* access modifiers changed from: protected */
    public void setMsgGroupDelegate() {
        IMapWidgetManager mapWidgetManager = Stub.getMapWidgetManager();
        IMapHomeMsgDispatchService instance = Sub.getInstance();
        MsgGroupWidgetPresenter msgGroupWidgetPresenter = (MsgGroupWidgetPresenter) mapWidgetManager.getPresenter(WidgetType.MSG_BOX);
        OperateActivityWidgetPresenter operateActivityWidgetPresenter = (OperateActivityWidgetPresenter) mapWidgetManager.getPresenter(WidgetType.ACTIVITY);
        if (msgGroupWidgetPresenter != null && operateActivityWidgetPresenter != null && instance != null) {
            operateActivityWidgetPresenter.setEventDelegate(new ary());
            msgGroupWidgetPresenter.setEventDelegate(new asa());
            instance.bindUi(msgGroupWidgetPresenter, operateActivityWidgetPresenter);
        }
    }

    /* access modifiers changed from: protected */
    public void setUserDelegate() {
        UserCenterWidgetPresenter userCenterWidgetPresenter = (UserCenterWidgetPresenter) Stub.getMapWidgetManager().getPresenter(WidgetType.USER_CENTER);
        if (userCenterWidgetPresenter != null) {
            userCenterWidgetPresenter.setEventDelegate(new asd());
        }
    }

    /* access modifiers changed from: protected */
    public void setNearbyDelegate() {
        NearBySearchWidgetPresenter nearBySearchWidgetPresenter = (NearBySearchWidgetPresenter) Stub.getMapWidgetManager().getPresenter(WidgetType.NEARBY_SEARCH);
        if (nearBySearchWidgetPresenter != null) {
            nearBySearchWidgetPresenter.setEventDelegate(new asb());
            nearBySearchWidgetPresenter.responseData(null);
        }
    }

    public void responseNearbyData(String str) {
        NearBySearchWidgetPresenter nearBySearchWidgetPresenter = (NearBySearchWidgetPresenter) Stub.getMapWidgetManager().getPresenter(WidgetType.NEARBY_SEARCH);
        if (nearBySearchWidgetPresenter != null) {
            nearBySearchWidgetPresenter.responseData(str);
        }
    }

    /* access modifiers changed from: protected */
    public void setRouteLineDelegate() {
        RouteLineWidgetPresenter routeLineWidgetPresenter = (RouteLineWidgetPresenter) Stub.getMapWidgetManager().getPresenter(WidgetType.ROUTE_LINE);
        if (routeLineWidgetPresenter != null) {
            routeLineWidgetPresenter.setEventDelegate(new asc());
        }
    }

    public void onClick(View view, String str) {
        if (((str.hashCode() == 102570 && str.equals(WidgetType.GPS)) ? (char) 0 : 65535) == 0) {
            doGpsAction();
        }
        dispatchClickEvent(view, str);
    }

    /* access modifiers changed from: protected */
    public void doGpsAction() {
        bci bci = (bci) a.a.a(bci.class);
        this.mMapPage.getMapManager().getOverlayManager().getGpsLayer();
        cdy b = cdx.b();
        if (bci != null) {
            bci.a((bid) this.mMapPage, b);
        }
    }
}
