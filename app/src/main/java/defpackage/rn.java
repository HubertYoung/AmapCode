package defpackage;

import com.amap.bundle.drivecommon.model.NavigationPath;
import com.amap.bundle.drivecommon.model.NavigationResult;
import com.amap.bundle.drivecommon.route.utils.CarRouteParser;
import com.amap.bundle.drivecommon.tools.DriveUtil;
import com.autonavi.common.model.POI;
import com.autonavi.jni.ae.route.route.CalcRouteResult;
import com.autonavi.minimap.drive.route.CalcRouteScene;

/* renamed from: rn reason: default package */
/* compiled from: NavigationResultBuilderUtils */
public final class rn {
    public static NavigationResult a(CalcRouteScene calcRouteScene, POI poi, POI poi2, CalcRouteResult calcRouteResult) {
        NavigationResult navigationResult = new NavigationResult();
        int pathCount = calcRouteResult.getPathCount();
        navigationResult.mPathNum = pathCount;
        navigationResult.mstartX = poi.getPoint().x;
        navigationResult.mstartY = poi.getPoint().y;
        navigationResult.mendX = poi2.getPoint().x;
        navigationResult.mendY = poi2.getPoint().y;
        navigationResult.mPaths = new NavigationPath[pathCount];
        navigationResult.maxBound = DriveUtil.getRouteResultBound(calcRouteResult);
        for (int i = 0; i < pathCount; i++) {
            navigationResult.mPaths[i] = CarRouteParser.parseNavigationPath(calcRouteResult.getRoute(i), calcRouteScene);
        }
        return navigationResult;
    }
}
