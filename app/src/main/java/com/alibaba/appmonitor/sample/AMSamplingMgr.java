package com.alibaba.appmonitor.sample;

import android.text.TextUtils;
import com.alibaba.analytics.core.Variables;
import com.alibaba.analytics.core.config.UTOrangeConfBiz;
import com.alibaba.analytics.core.db.Entity;
import com.alibaba.analytics.core.selfmonitor.SelfMonitorEvent;
import com.alibaba.analytics.utils.Logger;
import com.alibaba.analytics.utils.StringUtils;
import com.alibaba.appmonitor.event.EventType;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class AMSamplingMgr extends UTOrangeConfBiz {
    private static final int MAX_SAMPLING_SEED = 10000;
    private static AMSamplingMgr instance;
    private static final String[] namespaces = {"ap_stat", "ap_counter", "ap_alarm"};
    private Map<EventType, AMConifg> eventTypeSamplings = Collections.synchronizedMap(new HashMap(3));
    private int samplingSeed;

    private AMSamplingMgr() {
        EventType[] values;
        updateSamplingSeed();
        for (EventType eventType : EventType.values()) {
            Class cls = eventType.getCls();
            AMConifg buildRelation = buildRelation(Variables.getInstance().getDbMgr().find(cls, null, "module,mp ASC ", -1));
            if (buildRelation == null) {
                try {
                    AMConifg aMConifg = (AMConifg) cls.newInstance();
                    try {
                        aMConifg.module = SampleConfigConstant.TAG_ROOT;
                        aMConifg.setSampling(eventType.getDefaultSampling());
                    } catch (Exception unused) {
                    }
                    buildRelation = aMConifg;
                } catch (Exception unused2) {
                }
            }
            this.eventTypeSamplings.put(eventType, buildRelation);
        }
    }

    public static AMSamplingMgr getInstance() {
        if (instance == null) {
            synchronized (AMSamplingMgr.class) {
                try {
                    if (instance == null) {
                        instance = new AMSamplingMgr();
                    }
                }
            }
        }
        return instance;
    }

    public boolean checkSampled(EventType eventType, String str, String str2) {
        return isSampled(eventType, str, str2, null);
    }

    public boolean checkSampled(EventType eventType, String str, String str2, Map<String, String> map) {
        return isSampled(eventType, str, str2, map);
    }

    public boolean checkAlarmSampled(String str, String str2, Boolean bool, Map<String, String> map) {
        return isAlarmSampled(str, str2, bool, map);
    }

    public boolean isSampled(EventType eventType, String str, String str2, Map<String, String> map) {
        AMConifg aMConifg = this.eventTypeSamplings.get(eventType);
        if (aMConifg != null) {
            return aMConifg.isSampled(this.samplingSeed, str, str2, map);
        }
        Logger.d((String) "eventTypeSample  ==null", new Object[0]);
        return false;
    }

    public boolean isAlarmSampled(String str, String str2, Boolean bool, Map<String, String> map) {
        AMConifg aMConifg = this.eventTypeSamplings.get(EventType.ALARM);
        if (aMConifg == null || !(aMConifg instanceof AlarmConfig)) {
            return false;
        }
        return ((AlarmConfig) aMConifg).isSampled(this.samplingSeed, str, str2, bool, map);
    }

    public void updateSamplingSeed() {
        this.samplingSeed = new Random(System.currentTimeMillis()).nextInt(10000);
    }

    public void setEventTypeSampling(EventType eventType, int i) {
        AMConifg aMConifg = this.eventTypeSamplings.get(eventType);
        if (aMConifg != null) {
            aMConifg.setSampling(i);
        }
        Logger.d((String) "setSampling end", new Object[0]);
    }

    private AMConifg parseConfigEntity(Class<? extends AMConifg> cls, JSONObject jSONObject) {
        AMConifg aMConifg;
        try {
            aMConifg = (AMConifg) cls.newInstance();
            try {
                if (jSONObject.containsKey("offline")) {
                    aMConifg.offline = jSONObject.getString("offline");
                }
                if (jSONObject.containsKey("cp")) {
                    aMConifg.setSampling(jSONObject.getIntValue("cp"));
                }
                if (aMConifg instanceof AlarmConfig) {
                    AlarmConfig alarmConfig = (AlarmConfig) aMConifg;
                    if (jSONObject.containsKey(SampleConfigConstant.TAG_SCP)) {
                        alarmConfig.successSampling = jSONObject.getIntValue(SampleConfigConstant.TAG_SCP);
                    }
                    if (jSONObject.containsKey(SampleConfigConstant.TAG_FCP)) {
                        alarmConfig.failSampling = jSONObject.getIntValue(SampleConfigConstant.TAG_FCP);
                    }
                    return alarmConfig;
                } else if (!(aMConifg instanceof StatConfig)) {
                    return aMConifg;
                } else {
                    StatConfig statConfig = (StatConfig) aMConifg;
                    if (!jSONObject.containsKey("detail")) {
                        return aMConifg;
                    }
                    statConfig.detail = jSONObject.getIntValue("detail");
                    return aMConifg;
                }
            } catch (Throwable unused) {
                Logger.e((String) "new AppMonitorConfig error", new Object[0]);
                return aMConifg;
            }
        } catch (Throwable unused2) {
            aMConifg = null;
            Logger.e((String) "new AppMonitorConfig error", new Object[0]);
            return aMConifg;
        }
    }

    private AMConifg buildRelation(List<AMConifg> list) {
        AMConifg aMConifg;
        int size = list.size();
        int i = 0;
        while (i < size && !SampleConfigConstant.TAG_ROOT.equalsIgnoreCase(list.get(i).module)) {
            i++;
        }
        if (i < size) {
            aMConifg = list.remove(i);
            Logger.d((String) "remove root element", new Object[0]);
        } else {
            Logger.w((String) "cannot found the root element", new Object[0]);
            aMConifg = null;
        }
        if (aMConifg == null) {
            return null;
        }
        int size2 = list.size();
        for (int i2 = 0; i2 < size2; i2++) {
            AMConifg aMConifg2 = list.get(i2);
            if (TextUtils.isEmpty(aMConifg2.monitorPoint)) {
                aMConifg.add(aMConifg2.module, aMConifg2);
            } else {
                aMConifg.getOrBulidNext(aMConifg2.module).add(aMConifg2.monitorPoint, aMConifg2);
            }
        }
        return aMConifg;
    }

    @Deprecated
    public String[] returnOrangeConfigurationNameList() {
        return namespaces;
    }

    public void onNonOrangeConfigurationArrive(String str) {
        super.onNonOrangeConfigurationArrive(str);
    }

    public String[] getOrangeGroupnames() {
        return namespaces;
    }

    public void onOrangeConfigurationArrive(String str, Map<String, String> map) {
        AMConifg aMConifg;
        Logger.d((String) "", "namespace", str, "config:", map);
        if (!StringUtils.isBlank(str) && map != null) {
            ArrayList arrayList = new ArrayList();
            EventType eventTypeByNameSpace = EventType.getEventTypeByNameSpace(str);
            Class cls = eventTypeByNameSpace.getCls();
            try {
                if (map.containsKey(SampleConfigConstant.TAG_ROOT)) {
                    aMConifg = parseConfigEntity(cls, JSON.parseObject(map.get(SampleConfigConstant.TAG_ROOT)));
                    map.remove(SampleConfigConstant.TAG_ROOT);
                } else {
                    try {
                        aMConifg = (AMConifg) cls.newInstance();
                        if (aMConifg instanceof AlarmConfig) {
                            AlarmConfig alarmConfig = (AlarmConfig) aMConifg;
                            alarmConfig.successSampling = eventTypeByNameSpace.getDefaultSampling();
                            alarmConfig.failSampling = eventTypeByNameSpace.getDefaultSampling();
                        } else {
                            aMConifg.setSampling(eventTypeByNameSpace.getDefaultSampling());
                        }
                    } catch (Throwable unused) {
                        return;
                    }
                }
                aMConifg.module = SampleConfigConstant.TAG_ROOT;
                for (String next : map.keySet()) {
                    JSONObject jSONObject = null;
                    jSONObject = JSON.parseObject(map.get(next));
                    if (jSONObject != null) {
                        try {
                            AMConifg parseConfigEntity = parseConfigEntity(cls, jSONObject);
                            parseConfigEntity.module = next;
                            JSONObject jSONObject2 = jSONObject.getJSONObject(SampleConfigConstant.TAG_MPS);
                            if (jSONObject2 != null) {
                                for (String next2 : jSONObject2.keySet()) {
                                    AMConifg parseConfigEntity2 = parseConfigEntity(cls, jSONObject2.getJSONObject(next2));
                                    parseConfigEntity2.module = next;
                                    parseConfigEntity2.monitorPoint = next2;
                                    parseConfigEntity.add(next2, parseConfigEntity2);
                                    arrayList.add(parseConfigEntity2);
                                }
                            }
                            aMConifg.add(next, parseConfigEntity);
                            arrayList.add(parseConfigEntity);
                        } catch (Throwable th) {
                            th.printStackTrace();
                        }
                    }
                }
                arrayList.add(aMConifg);
                this.eventTypeSamplings.put(eventTypeByNameSpace, aMConifg);
                Variables.getInstance().getDbMgr().clear(aMConifg.getClass());
                Variables.getInstance().getDbMgr().insert((List<? extends Entity>) arrayList);
            } catch (Throwable th2) {
                Logger.e((String) "", "parse config error", th2);
            }
        }
    }

    public void enableOffline(EventType eventType, String str, String[] strArr) {
        if (eventType != null && !TextUtils.isEmpty(str) && strArr != null) {
            AMConifg aMConifg = this.eventTypeSamplings.get(eventType);
            if (aMConifg != null) {
                AMConifg orBulidNext = aMConifg.getOrBulidNext(str);
                for (int i = 0; i < strArr.length; i++) {
                    if (orBulidNext.isContains(strArr[i])) {
                        orBulidNext.getNext(strArr[i]).enableOffline();
                    } else {
                        try {
                            AMConifg aMConifg2 = (AMConifg) orBulidNext.clone();
                            aMConifg2.module = str;
                            aMConifg2.monitorPoint = strArr[i];
                            aMConifg2.enableOffline();
                            orBulidNext.add(strArr[i], aMConifg2);
                        } catch (CloneNotSupportedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    public boolean isOffline(EventType eventType, String str, String str2) {
        if (isSelfMonitorEvent(eventType, str, str2)) {
            return true;
        }
        AMConifg aMConifg = this.eventTypeSamplings.get(eventType);
        if (aMConifg != null) {
            return aMConifg.isOffline(str, str2);
        }
        return false;
    }

    public boolean isDetail(String str, String str2) {
        AMConifg aMConifg = this.eventTypeSamplings.get(EventType.STAT);
        if (aMConifg == null) {
            return false;
        }
        return ((StatConfig) aMConifg).isDetail(str, str2);
    }

    private boolean isSelfMonitorEvent(EventType eventType, String str, String str2) {
        return eventType != null && eventType == EventType.COUNTER && SelfMonitorEvent.module.equalsIgnoreCase(str) && (SelfMonitorEvent.UPLOAD_TRAFFIC_OFFLINE.equalsIgnoreCase(str2) || SelfMonitorEvent.TNET_REQUEST_SEND_OFFLINE.equalsIgnoreCase(str2));
    }

    public int getSamplingSeed() {
        return this.samplingSeed;
    }
}
