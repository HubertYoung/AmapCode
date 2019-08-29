package com.autonavi.inter.impl;

import com.autonavi.inter.IRouterLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public final class RouterLoaderImpl implements IRouterLoader {
    private static final Map<String, List<Class>> sRouterMap = new HashMap();

    static {
        doPut(new AMAP_MODULE_AJX_Router_DATA());
        doPut(new QRSCAN_Router_DATA());
        doPut(new MINIAPP_Router_DATA());
        doPut(new ACTIVITIES_Router_DATA());
        doPut(new BUSCARD_Router_DATA());
        doPut(new BUSLINE_Router_DATA());
        doPut(new COACH_Router_DATA());
        doPut(new FAVORITES_Router_DATA());
        doPut(new FEATUREGUIDE_Router_DATA());
        doPut(new FEEDBACK_Router_DATA());
        doPut(new FOOTNAVI_Router_DATA());
        doPut(new FOOTRESULT_Router_DATA());
        doPut(new HEALTHYRIDE_Router_DATA());
        doPut(new HEALTHYRUN_Router_DATA());
        doPut(new OFFLINEMAP_API_Router_DATA());
        doPut(new REALTIMEBUS_Router_DATA());
        doPut(new RIDENAVI_Router_DATA());
        doPut(new ROUTECOMMUTE_Router_DATA());
        doPut(new RUNRECOMMEND_Router_DATA());
        doPut(new SHAREBIKE_Router_DATA());
        doPut(new TRAFFICEVENT_Router_DATA());
        doPut(new TRAIN_Router_DATA());
        doPut(new WALLET_Router_DATA());
        doPut(new AGROUP_Router_DATA());
        doPut(new AIRTICKET_Router_DATA());
        doPut(new AMAPHOME_Router_DATA());
        doPut(new CAROWNERSERVICE_Router_DATA());
        doPut(new LIFE_Router_DATA());
        doPut(new PROFILE_Router_DATA());
        doPut(new SETTING_Router_DATA());
        doPut(new SUBWAY_Router_DATA());
        doPut(new TOOLBOX_Router_DATA());
        doPut(new DRIVE_Router_DATA());
        doPut(new PLANHOME_Router_DATA());
        doPut(new TRIPGROUP_Router_DATA());
        doPut(new SCHOOLBUS_Router_DATA());
        doPut(new PAY_Router_DATA());
    }

    public final List<Class> findRouterClass(String str) {
        return sRouterMap.get(str);
    }

    private static void doPut(HashMap<String, List<Class>> hashMap) {
        for (Entry next : hashMap.entrySet()) {
            String str = (String) next.getKey();
            List list = (List) next.getValue();
            if (!sRouterMap.containsKey(str)) {
                sRouterMap.put(str, new ArrayList());
            }
            sRouterMap.get(str).addAll(list);
        }
    }
}
