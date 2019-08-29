package defpackage;

import com.amap.bundle.drive.etrip.home.AjxEtripResultPage;
import com.amap.bundle.drive.result.driveresult.result.AjxRouteCarResultPage;
import com.amap.bundle.drive.result.driveresult.result.AjxRouteTruckResultPage;
import com.amap.bundle.drive.result.motorresult.result.AjxRouteMotorResultPage;
import com.amap.bundle.drivecommon.model.ICarRouteResult;
import com.amap.bundle.drivecommon.model.NavigationPath;
import com.amap.bundle.drivecommon.overlay.RouteCarResultRouteItem;
import com.amap.bundle.drivecommon.tools.DriveUtil;
import com.autonavi.bundle.routecommon.model.RouteType;
import com.autonavi.common.Callback;
import com.autonavi.common.Callback.a;
import com.autonavi.common.PageBundle;
import com.autonavi.jni.ae.route.model.LineItem;
import com.autonavi.jni.ae.route.route.Route;
import com.autonavi.minimap.drive.route.CalcRouteScene;
import com.autonavi.minimap.drive.route.IDriveRouteResult;
import com.autonavi.minimap.route.export.model.IRouteResultData;
import org.json.JSONObject;

/* renamed from: on reason: default package */
/* compiled from: DriveRouteManagerIml */
public class on implements dhz {
    public final a a(dhx dhx) {
        PageBundle pageBundle = new PageBundle();
        pageBundle.putObject("bundle_key_route_type", RouteType.CAR);
        pageBundle.putObject("bundle_key_poi_start", dhx.a);
        pageBundle.putObject("bundle_key_poi_end", dhx.c);
        bax bax = (bax) a.a.a(bax.class);
        if (bax != null) {
            bax.b(pageBundle);
        }
        return null;
    }

    public final a a(dhx dhx, CalcRouteScene calcRouteScene, final dhy dhy) {
        return om.a(new ta(dhx, calcRouteScene), (axa) new axa() {
            public final void a(IRouteResultData iRouteResultData, RouteType routeType) {
                dhy.a((IDriveRouteResult) iRouteResultData);
            }

            public final void a(RouteType routeType, int i, String str) {
                dhy.a();
            }

            public final void a(Throwable th, boolean z) {
                dhy.b();
            }
        });
    }

    public final a a(dhx dhx, final Callback<int[]> callback) {
        return om.a(new ta(dhx), (axa) new axa() {
            public final void a(IRouteResultData iRouteResultData, RouteType routeType) {
                if (iRouteResultData == null || !iRouteResultData.hasData()) {
                    callback.error(new Exception(), true);
                    return;
                }
                NavigationPath focusNavigationPath = ((ICarRouteResult) iRouteResultData).getFocusNavigationPath();
                if (focusNavigationPath == null || focusNavigationPath.mLongDistnceSceneData == null) {
                    callback.error(new Exception(), true);
                } else {
                    callback.callback(focusNavigationPath.mLongDistnceSceneData.b);
                }
            }

            public final void a(RouteType routeType, int i, String str) {
                callback.error(new Exception(str), false);
            }

            public final void a(Throwable th, boolean z) {
                callback.error(th, z);
            }
        });
    }

    public final Object a(JSONObject jSONObject, boolean z) {
        return rv.a(jSONObject, z);
    }

    public final String a(Object obj) {
        return rv.a(obj);
    }

    public final void a(PageBundle pageBundle) {
        if (pageBundle.containsKey("bundle_key_save_route")) {
            DriveUtil.showSaveRouteFragment((cor) pageBundle.getObject("bundle_key_save_route"));
        }
    }

    public final Class a() {
        return AjxRouteCarResultPage.class;
    }

    public final Class b() {
        return AjxRouteMotorResultPage.class;
    }

    public final Class c() {
        return AjxRouteTruckResultPage.class;
    }

    public final Class d() {
        return AjxEtripResultPage.class;
    }

    public final Object a(Route route) {
        if (route == null) {
            return null;
        }
        LineItem[] lineItems = route.getLineItems();
        if (lineItems == null || lineItems.length == 0) {
            return null;
        }
        return new RouteCarResultRouteItem(lineItems[0]);
    }
}
