package com.autonavi.inter.impl;

import com.autonavi.inter.IMultipleServiceLoader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public final class MultipleServiceLoaderImpl implements IMultipleServiceLoader {
    private static final bqp comparator = new bqp();
    private static final Map<Class, List<Class>> sMap = new HashMap();

    static {
        doPut(new AMAP_MODULE_OPERATION_MultipleImpl_DATA());
        doPut(new AMAP_MODULE_COMMON_MultipleImpl_DATA());
        doPut(new AMAP_MODULE_AJX_MultipleImpl_DATA());
        doPut(new ACCOUNT_MultipleImpl_DATA());
        doPut(new ACTIVITIES_MultipleImpl_DATA());
        doPut(new IMPRESSIONREPORTER_MultipleImpl_DATA());
        doPut(new MSGBOX_MultipleImpl_DATA());
        doPut(new NOTIFICATION_MultipleImpl_DATA());
        doPut(new ONEKEYCHECK_MultipleImpl_DATA());
        doPut(new RIDERESULT_MultipleImpl_DATA());
        doPut(new ROUTECOMMON_MultipleImpl_DATA());
        doPut(new ROUTECOMMUTE_MultipleImpl_DATA());
        doPut(new SEARCHAROUND_MultipleImpl_DATA());
        doPut(new SEARCHCOMMON_MultipleImpl_DATA());
        doPut(new SEARCHHOME_MultipleImpl_DATA());
        doPut(new STATISTICS_MultipleImpl_DATA());
        doPut(new TRAFFICEVENT_MultipleImpl_DATA());
        doPut(new TRAIN_MultipleImpl_DATA());
        doPut(new WEBVIEW_MultipleImpl_DATA());
        doPut(new AMAPHOME_MultipleImpl_DATA());
        doPut(new BUSNAVI_MultipleImpl_DATA());
        doPut(new CAROWNERSERVICE_MultipleImpl_DATA());
        doPut(new ENVIRONMENTMAP_MultipleImpl_DATA());
        doPut(new LIFE_MultipleImpl_DATA());
        doPut(new ROUTEPLAN_MultipleImpl_DATA());
        doPut(new SCENICAREA_MultipleImpl_DATA());
        doPut(new SEARCHRESULT_MultipleImpl_DATA());
        doPut(new DRIVECOMMON_MultipleImpl_DATA());
        doPut(new PLANHOME_MultipleImpl_DATA());
        doPut(new TRIPGROUP_MultipleImpl_DATA());
        doPut(new PAY_MultipleImpl_DATA());
        sort();
    }

    public final List<Class> loadServices(Class cls) {
        return sMap.get(cls);
    }

    private static void doPut(HashMap<Class, List<Class>> hashMap) {
        for (Entry next : hashMap.entrySet()) {
            Class cls = (Class) next.getKey();
            List list = (List) next.getValue();
            if (!sMap.containsKey(cls)) {
                sMap.put(cls, new ArrayList());
            }
            sMap.get(cls).addAll(list);
        }
    }

    private static void sort() {
        for (List<Class> sort : sMap.values()) {
            Collections.sort(sort, comparator);
        }
    }
}
