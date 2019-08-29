package defpackage;

import com.autonavi.bundle.routecommon.model.RouteType;
import com.autonavi.minimap.route.export.model.IRouteResultData;

/* renamed from: axa reason: default package */
/* compiled from: IRouteResultCallBack */
public interface axa {
    void a(RouteType routeType, int i, String str);

    void a(IRouteResultData iRouteResultData, RouteType routeType);

    void a(Throwable th, boolean z);
}
