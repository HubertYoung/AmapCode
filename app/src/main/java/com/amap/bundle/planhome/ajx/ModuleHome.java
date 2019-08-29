package com.amap.bundle.planhome.ajx;

import com.amap.bundle.cloudconfig.appinit.FunctionSupportConfiger;
import com.amap.bundle.drivecommon.tools.DriveUtil;
import com.autonavi.bundle.routecommon.model.RouteType;
import com.autonavi.miniapp.plugin.map.action.ShowRouteActionProcessor;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.core.JsFunctionCallback;
import com.autonavi.minimap.ajx3.modules.AbstractModule;
import com.autonavi.minimap.ajx3.modules.AjxMethod;
import com.autonavi.minimap.ajx3.modules.AjxModule;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

@AjxModule("route_home")
public class ModuleHome extends AbstractModule {
    public static final String MODULE_NAME = "route_home";
    private static HashMap<ModuleHome, LinkedList<JsFunctionCallback>> sCallbacks = new HashMap<>();

    public ModuleHome(IAjxContext iAjxContext) {
        super(iAjxContext);
    }

    @AjxMethod("routeTypeChange")
    public void routeTypeChange(JsFunctionCallback jsFunctionCallback) {
        if (!sCallbacks.containsKey(this)) {
            LinkedList linkedList = new LinkedList();
            linkedList.add(jsFunctionCallback);
            sCallbacks.put(this, linkedList);
        } else {
            LinkedList linkedList2 = sCallbacks.get(this);
            if (!linkedList2.contains(jsFunctionCallback)) {
                linkedList2.add(jsFunctionCallback);
            }
        }
        RouteType a = acr.a();
        if (jsFunctionCallback != null) {
            jsFunctionCallback.callback(a.getKeyName());
        }
    }

    public static String getType(RouteType routeType) {
        if (RouteType.CAR == routeType) {
            return "car";
        }
        if (RouteType.BUS == routeType) {
            return ShowRouteActionProcessor.SEARCH_TYPE_BUS;
        }
        if (RouteType.ONFOOT == routeType) {
            return "foot";
        }
        if (RouteType.RIDE == routeType) {
            return ShowRouteActionProcessor.SEARCH_TYPE_RIDE;
        }
        if (RouteType.TRAIN == routeType) {
            return "train";
        }
        if (RouteType.COACH == routeType) {
            return "coach";
        }
        if (RouteType.TAXI == routeType) {
            return FunctionSupportConfiger.TAXI_TAG;
        }
        if (RouteType.TRUCK == routeType) {
            return DriveUtil.NAVI_TYPE_TRUCK;
        }
        if (RouteType.ETRIP == routeType) {
            return "etrip";
        }
        if (RouteType.FREERIDE == routeType) {
            return "freeride";
        }
        if (RouteType.AIRTICKET == routeType) {
            return "airticket";
        }
        if (RouteType.MOTOR == routeType) {
            return "motor";
        }
        return "";
    }

    public void notifyRouteTypeChange(RouteType routeType) {
        String keyName = routeType.getKeyName();
        if (sCallbacks.size() > 0) {
            for (ModuleHome moduleHome : sCallbacks.keySet()) {
                LinkedList linkedList = sCallbacks.get(moduleHome);
                if (linkedList != null) {
                    Iterator it = linkedList.iterator();
                    while (it.hasNext()) {
                        ((JsFunctionCallback) it.next()).callback(keyName);
                    }
                }
            }
        }
    }

    public void onModuleDestroy() {
        super.onModuleDestroy();
        if (sCallbacks.containsKey(this)) {
            sCallbacks.remove(this);
        }
    }
}
