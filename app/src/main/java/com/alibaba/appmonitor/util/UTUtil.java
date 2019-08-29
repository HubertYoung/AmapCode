package com.alibaba.appmonitor.util;

import com.alibaba.analytics.core.model.Log;
import com.alibaba.analytics.core.model.LogField;
import com.alibaba.analytics.core.store.LogStoreMgr;
import com.alibaba.appmonitor.delegate.AppMonitorDelegate;
import com.alibaba.appmonitor.delegate.SdkMeta;
import com.alibaba.appmonitor.event.Event;
import com.alibaba.appmonitor.event.EventType;
import com.alibaba.appmonitor.event.UTEvent;
import com.alibaba.appmonitor.model.UTDimensionValueSet;
import com.alibaba.appmonitor.pool.BalancedPool;
import com.alibaba.appmonitor.pool.ReuseJSONArray;
import com.alibaba.fastjson.JSON;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class UTUtil {
    public static void uploadEvent(Map<UTDimensionValueSet, List<Event>> map) {
        for (Entry next : map.entrySet()) {
            StringBuilder sb = new StringBuilder();
            StringBuilder sb2 = new StringBuilder();
            UTDimensionValueSet uTDimensionValueSet = (UTDimensionValueSet) next.getKey();
            List<Event> list = (List) next.getValue();
            if (list.size() != 0) {
                Integer eventId = uTDimensionValueSet.getEventId();
                if (eventId != null) {
                    EventType eventType = EventType.getEventType(eventId.intValue());
                    int i = 0;
                    UTEvent uTEvent = (UTEvent) BalancedPool.getInstance().poll(UTEvent.class, new Object[0]);
                    uTEvent.eventId = eventId.intValue();
                    uTEvent.args.putAll(AppMonitorDelegate.getGlobalArgsMap());
                    if (uTDimensionValueSet.getMap() != null) {
                        uTEvent.args.putAll(uTDimensionValueSet.getMap());
                        uTEvent.args.remove("commitDay");
                    }
                    HashMap hashMap = new HashMap();
                    hashMap.put("meta", SdkMeta.getSDKMetaData());
                    ReuseJSONArray reuseJSONArray = (ReuseJSONArray) BalancedPool.getInstance().poll(ReuseJSONArray.class, new Object[0]);
                    for (Event event : list) {
                        reuseJSONArray.add(event.dumpToJSONObject());
                        if (i == 0) {
                            sb.append(event.module);
                            sb2.append(event.monitorPoint);
                        } else {
                            sb.append(",");
                            sb.append(event.module);
                            sb2.append(",");
                            sb2.append(event.monitorPoint);
                        }
                        i++;
                        BalancedPool.getInstance().offer(event);
                    }
                    hashMap.put("data", reuseJSONArray);
                    uTEvent.args.put(eventType.getAggregateEventArgsKey(), JSON.toJSONString(hashMap));
                    String sb3 = sb.toString();
                    String sb4 = sb2.toString();
                    uTEvent.args.put(LogField.ARG1.toString(), sb3);
                    uTEvent.args.put(LogField.ARG2.toString(), sb4);
                    uTEvent.arg1 = sb3;
                    uTEvent.arg2 = sb4;
                    sendUTEventWithPlugin(uTEvent);
                    BalancedPool.getInstance().offer(reuseJSONArray);
                }
            }
            BalancedPool.getInstance().offer(uTDimensionValueSet);
        }
    }

    public static void sendRealDebugEvent(UTDimensionValueSet uTDimensionValueSet, Event event) {
        Integer eventId = uTDimensionValueSet.getEventId();
        if (eventId != null) {
            EventType eventType = EventType.getEventType(eventId.intValue());
            UTEvent uTEvent = (UTEvent) BalancedPool.getInstance().poll(UTEvent.class, new Object[0]);
            uTEvent.eventId = 6699;
            uTEvent.arg1 = event.module;
            uTEvent.arg2 = event.monitorPoint;
            uTEvent.args.putAll(AppMonitorDelegate.getGlobalArgsMap());
            if (uTDimensionValueSet.getMap() != null) {
                uTEvent.args.putAll(uTDimensionValueSet.getMap());
                uTEvent.args.remove("commitDay");
            }
            HashMap hashMap = new HashMap();
            hashMap.put("meta", SdkMeta.getSDKMetaData());
            hashMap.put("_event_id", eventId);
            ReuseJSONArray reuseJSONArray = (ReuseJSONArray) BalancedPool.getInstance().poll(ReuseJSONArray.class, new Object[0]);
            reuseJSONArray.add(event.dumpToJSONObject());
            BalancedPool.getInstance().offer(event);
            hashMap.put("data", reuseJSONArray);
            uTEvent.args.put(eventType.getAggregateEventArgsKey(), JSON.toJSONString(hashMap));
            uTEvent.args.put(LogField.EVENTID.toString(), "6699");
            sendUTEventWithPlugin(uTEvent);
            BalancedPool.getInstance().offer(reuseJSONArray);
        }
    }

    public static void sendAppException(UTEvent uTEvent) {
        if (uTEvent != null) {
            LogStoreMgr instance = LogStoreMgr.getInstance();
            Log log = new Log(uTEvent.page, String.valueOf(uTEvent.eventId), uTEvent.arg1, uTEvent.arg2, uTEvent.arg3, uTEvent.args);
            instance.add(log);
            BalancedPool.getInstance().offer(uTEvent);
        }
    }

    public static void sendUTEventWithPlugin(UTEvent uTEvent) {
        Log log = new Log(uTEvent.page, String.valueOf(uTEvent.eventId), uTEvent.arg1, uTEvent.arg2, uTEvent.arg3, uTEvent.args);
        LogStoreMgr.getInstance().add(log);
        BalancedPool.getInstance().offer(uTEvent);
    }
}
