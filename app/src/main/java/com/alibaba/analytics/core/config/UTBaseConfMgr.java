package com.alibaba.analytics.core.config;

import android.text.TextUtils;
import com.alibaba.analytics.core.Variables;
import com.alibaba.analytics.core.config.timestamp.ConfigTimeStampMgr;
import com.alibaba.analytics.utils.Logger;
import com.alibaba.analytics.utils.StringUtils;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import org.json.JSONException;
import org.json.JSONObject;

public abstract class UTBaseConfMgr {
    public static String TAG_CONFIG_TIMESTAMP = "timestamp";
    private String DefaultWhiteConfigs1 = "{\"B02N_utap_system\":{\"content\":{\"fu\":30,\"sw_plugin\":0,\"bu\":300}},\"B02N_ut_sample\":{\"content\":{\"1\":{\"cp\":10000},\"1000\":{\"cp\":10000},\"2000\":{\"cp\":10000},\"2100\":{\"cp\":10000},\"3002\":{\"cp\":10000},\"3003\":{\"cp\":10000},\"3004\":{\"cp\":10000},\"4007\":{\"cp\":10000},\"5002\":{\"cp\":10000},\"5004\":{\"cp\":10000},\"5005\":{\"cp\":10000},\"6004\":{\"cp\":10000},\"9001\":{\"cp\":10000},\"9002\":{\"cp\":10000},\"9003\":{\"cp\":10000},\"9101\":{\"cp\":10000},\"9199\":{\"cp\":10000},\"15301\":{\"cp\":10000},\"15302\":{\"cp\":1},\"15303\":{\"cp\":10000},\"15304\":{\"cp\":10000},\"15305\":{\"cp\":10000},\"15306\":{\"cp\":100},\"15307\":{\"cp\":10000},\"15391\":{\"cp\":100},\"15392\":{\"cp\":10000},\"15393\":{\"cp\":1200},\"15394\":{\"cp\":10000},\"19999\":{\"cp\":10000},\"21032\":{\"cp\":10000},\"21034\":{\"cp\":1},\"21064\":{\"cp\":10000},\"22064\":{\"cp\":1},\"61001\":{\"cp\":10000},\"61006\":{\"cp\":10000},\"61007\":{\"cp\":10000},\"65001\":{\"cp\":1},\"65100\":{\"cp\":1},\"65101\":{\"cp\":4},\"65104\":{\"cp\":10000},\"65105\":{\"cp\":10000},\"65111\":{\"cp\":100},\"65113\":{\"cp\":10000},\"65114\":{\"cp\":10000},\"65125\":{\"cp\":10000},\"65132\":{\"cp\":10000},\"65171\":{\"cp\":100},\"65172\":{\"cp\":100},\"65173\":{\"cp\":100},\"65174\":{\"cp\":100},\"65175\":{\"cp\":100},\"65176\":{\"cp\":100},\"65177\":{\"cp\":100},\"65178\":{\"cp\":100},\"65180\":{\"cp\":900},\"65183\":{\"cp\":10000},\"65200\":{\"cp\":10000},\"65501\":{\"cp\":10000},\"65502\":{\"cp\":10000},\"65503\":{\"cp\":10000},\"66001\":{\"cp\":100},\"66003\":{\"cp\":10000},\"66101\":{\"cp\":10000},\"66404\":{\"cp\":10000},\"66602\":{\"cp\":10000}}},\"B02N_ut_stream\":{\"content\":{\"1\":{\"stm\":\"stm_x\"},\"1000\":{\"stm\":\"stm_p\"},\"2000\":{\"stm\":\"stm_p\"},\"2100\":{\"stm\":\"stm_c\"},\"4007\":{\"stm\":\"stm_d\"},\"5002\":{\"stm\":\"stm_d\"},\"5004\":{\"stm\":\"stm_d\"},\"5005\":{\"stm\":\"stm_d\"},\"6004\":{\"stm\":\"stm_d\"},\"9001\":{\"stm\":\"stm_d\"},\"9002\":{\"stm\":\"stm_d\"},\"9003\":{\"stm\":\"stm_d\"},\"9101\":{\"stm\":\"stm_d\"},\"9199\":{\"stm\":\"stm_d\"},\"15301\":{\"stm\":\"stm_x\"},\"15302\":{\"stm\":\"stm_x\"},\"15303\":{\"stm\":\"stm_x\"},\"15304\":{\"stm\":\"stm_x\"},\"15305\":{\"stm\":\"stm_x\"},\"15306\":{\"stm\":\"stm_x\"},\"15307\":{\"stm\":\"stm_x\"},\"15391\":{\"stm\":\"stm_x\"},\"15392\":{\"stm\":\"stm_x\"},\"15393\":{\"stm\":\"stm_x\"},\"15394\":{\"stm\":\"stm_x\"},\"19999\":{\"stm\":\"stm_d\"},\"21032\":{\"stm\":\"stm_x\"},\"21034\":{\"stm\":\"stm_x\"},\"21064\":{\"stm\":\"stm_x\"},\"22064\":{\"stm\":\"stm_x\"},\"61001\":{\"stm\":\"stm_x\"},\"61006\":{\"stm\":\"stm_x\"},\"61007\":{\"stm\":\"stm_x\"},\"65001\":{\"stm\":\"stm_x\"},\"65100\":{\"stm\":\"stm_x\"},\"65101\":{\"stm\":\"stm_x\"},\"65104\":{\"stm\":\"stm_x\"},\"65105\":{\"stm\":\"stm_x\"},\"65111\":{\"stm\":\"stm_x\"},\"65113\":{\"stm\":\"stm_x\"},\"65114\":{\"stm\":\"stm_x\"},\"65125\":{\"stm\":\"stm_x\"},\"65132\":{\"stm\":\"stm_x\"},\"65171\":{\"stm\":\"stm_x\"},\"65172\":{\"stm\":\"stm_x\"},\"65173\":{\"stm\":\"stm_x\"},\"65174\":{\"stm\":\"stm_x\"},\"65175\":{\"stm\":\"stm_x\"},\"65176\":{\"stm\":\"stm_x\"},\"65177\":{\"stm\":\"stm_x\"},\"65178\":{\"stm\":\"stm_x\"},\"65180\":{\"stm\":\"stm_x\"},\"65183\":{\"stm\":\"stm_x\"},\"65200\":{\"stm\":\"stm_d\"},\"65501\":{\"stm\":\"stm_d\"},\"65502\":{\"stm\":\"stm_d\"},\"65503\":{\"stm\":\"stm_d\"},\"66001\":{\"stm\":\"stm_d\"},\"66003\":{\"stm\":\"stm_d\"},\"66101\":{\"stm\":\"stm_d\"},\"66404\":{\"stm\":\"stm_d\"}}},\"B02N_ut_bussiness\":{\"content\":{\"tpk\":[{\"kn\":\"adid\",\"ty\":\"nearby\"},{\"kn\":\"ucm\",\"ty\":\"nearby\"},{\"kn\":\"bdid\",\"ty\":\"far\"},{\"kn\":\"ref_pid\",\"ty\":\"far\"},{\"kn\":\"pid\",\"ty\":\"far\"},{\"kn\":\"tpa\",\"ty\":\"far\"},{\"kn\":\"point\",\"ty\":\"far\"},{\"kn\":\"ali_trackid\",\"ty\":\"far\"},{\"kn\":\"xncode\",\"ty\":\"nearby\"}]}}}";
    private String DefaultWhiteConfigs2 = "{\"B02N_utap_system\":{\"content\":{\"fu\":30,\"sw_plugin\":0,\"bu\":300,\"delay\":{\"2101\":{\"all_d\":1,\"arg1\":[\"%Page_Home_Button-Huichang%\",\"%Page_Home_Button-renqunhuichang%\",\"JuJRT_JRT_LIST_LOAD\",\"JuJRT_JRT_LIST\"]},\"2102\":{\"all_d\":1},\"19999\":{\"all_d\":0,\"arg1\":[\"Show1212Box_aop\",\"Page_Home_Show-LeiMuHuiChang\",\"Page_Home_Show-RenQunHuiChang\"]}}}},\"B02N_ap_counter\":{\"content\":{\"event_type\":{\"cp\":1000}}},\"B02N_ap_alarm\":{\"content\":{\"event_type\":{\"cp\":1000}}},\"B02N_ap_stat\":{\"content\":{\"event_type\":{\"cp\":1000}}},\"B02N_ut_sample\":{\"content\":{\"1\":{\"cp\":10000},\"1000\":{\"cp\":10000},\"2000\":{\"cp\":10000},\"2100\":{\"cp\":10000},\"4007\":{\"cp\":10000},\"5002\":{\"cp\":10000},\"5004\":{\"cp\":10000},\"5005\":{\"cp\":10000},\"6004\":{\"cp\":10000},\"9001\":{\"cp\":10000},\"9002\":{\"cp\":10000},\"9003\":{\"cp\":10000},\"9101\":{\"cp\":10000},\"9199\":{\"cp\":10000},\"15301\":{\"cp\":10000},\"15302\":{\"cp\":1},\"15303\":{\"cp\":10000},\"15304\":{\"cp\":10000},\"15305\":{\"cp\":10000},\"15306\":{\"cp\":100},\"15307\":{\"cp\":10000},\"15391\":{\"cp\":100},\"15392\":{\"cp\":10000},\"15393\":{\"cp\":1200},\"15394\":{\"cp\":10000},\"19999\":{\"cp\":10000},\"21032\":{\"cp\":10000},\"21034\":{\"cp\":1},\"21064\":{\"cp\":10000},\"22064\":{\"cp\":1},\"61001\":{\"cp\":10000},\"61006\":{\"cp\":10000},\"61007\":{\"cp\":10000},\"65001\":{\"cp\":1},\"65100\":{\"cp\":1},\"65101\":{\"cp\":4},\"65104\":{\"cp\":10000},\"65105\":{\"cp\":10000},\"65111\":{\"cp\":100},\"65113\":{\"cp\":10000},\"65114\":{\"cp\":10000},\"65125\":{\"cp\":10000},\"65132\":{\"cp\":10000},\"65171\":{\"cp\":100},\"65172\":{\"cp\":100},\"65173\":{\"cp\":100},\"65174\":{\"cp\":100},\"65175\":{\"cp\":100},\"65176\":{\"cp\":100},\"65177\":{\"cp\":100},\"65178\":{\"cp\":100},\"65180\":{\"cp\":900},\"65183\":{\"cp\":10000},\"65200\":{\"cp\":10000},\"65501\":{\"cp\":10000},\"65502\":{\"cp\":10000},\"65503\":{\"cp\":10000},\"66001\":{\"cp\":100},\"66003\":{\"cp\":10000},\"66101\":{\"cp\":10000},\"66404\":{\"cp\":10000}}},\"B02N_ut_stream\":{\"content\":{\"1\":{\"stm\":\"stm_x\"},\"1000\":{\"stm\":\"stm_p\"},\"2000\":{\"stm\":\"stm_p\"},\"2100\":{\"stm\":\"stm_c\"},\"4007\":{\"stm\":\"stm_d\"},\"5002\":{\"stm\":\"stm_d\"},\"5004\":{\"stm\":\"stm_d\"},\"5005\":{\"stm\":\"stm_d\"},\"6004\":{\"stm\":\"stm_d\"},\"9001\":{\"stm\":\"stm_d\"},\"9002\":{\"stm\":\"stm_d\"},\"9003\":{\"stm\":\"stm_d\"},\"9101\":{\"stm\":\"stm_d\"},\"9199\":{\"stm\":\"stm_d\"},\"15301\":{\"stm\":\"stm_x\"},\"15302\":{\"stm\":\"stm_x\"},\"15303\":{\"stm\":\"stm_x\"},\"15304\":{\"stm\":\"stm_x\"},\"15305\":{\"stm\":\"stm_x\"},\"15306\":{\"stm\":\"stm_x\"},\"15307\":{\"stm\":\"stm_x\"},\"15391\":{\"stm\":\"stm_x\"},\"15392\":{\"stm\":\"stm_x\"},\"15393\":{\"stm\":\"stm_x\"},\"15394\":{\"stm\":\"stm_x\"},\"19999\":{\"stm\":\"stm_d\"},\"21032\":{\"stm\":\"stm_x\"},\"21034\":{\"stm\":\"stm_x\"},\"21064\":{\"stm\":\"stm_x\"},\"22064\":{\"stm\":\"stm_x\"},\"61001\":{\"stm\":\"stm_x\"},\"61006\":{\"stm\":\"stm_x\"},\"61007\":{\"stm\":\"stm_x\"},\"65001\":{\"stm\":\"stm_x\"},\"65100\":{\"stm\":\"stm_x\"},\"65101\":{\"stm\":\"stm_x\"},\"65104\":{\"stm\":\"stm_x\"},\"65105\":{\"stm\":\"stm_x\"},\"65111\":{\"stm\":\"stm_x\"},\"65113\":{\"stm\":\"stm_x\"},\"65114\":{\"stm\":\"stm_x\"},\"65125\":{\"stm\":\"stm_x\"},\"65132\":{\"stm\":\"stm_x\"},\"65171\":{\"stm\":\"stm_x\"},\"65172\":{\"stm\":\"stm_x\"},\"65173\":{\"stm\":\"stm_x\"},\"65174\":{\"stm\":\"stm_x\"},\"65175\":{\"stm\":\"stm_x\"},\"65176\":{\"stm\":\"stm_x\"},\"65177\":{\"stm\":\"stm_x\"},\"65178\":{\"stm\":\"stm_x\"},\"65180\":{\"stm\":\"stm_x\"},\"65183\":{\"stm\":\"stm_x\"},\"65200\":{\"stm\":\"stm_d\"},\"65501\":{\"stm\":\"stm_d\"},\"65502\":{\"stm\":\"stm_d\"},\"65503\":{\"stm\":\"stm_d\"},\"66001\":{\"stm\":\"stm_d\"},\"66003\":{\"stm\":\"stm_d\"},\"66101\":{\"stm\":\"stm_d\"},\"66404\":{\"stm\":\"stm_d\"}}},\"B02N_ut_bussiness\":{\"content\":{\"tpk\":[{\"kn\":\"adid\",\"ty\":\"nearby\"},{\"kn\":\"ucm\",\"ty\":\"nearby\"},{\"kn\":\"bdid\",\"ty\":\"far\"},{\"kn\":\"ref_pid\",\"ty\":\"far\"},{\"kn\":\"pid\",\"ty\":\"far\"},{\"kn\":\"tpa\",\"ty\":\"far\"},{\"kn\":\"point\",\"ty\":\"far\"},{\"kn\":\"ali_trackid\",\"ty\":\"far\"},{\"kn\":\"xncode\",\"ty\":\"nearby\"}]}}}";
    private Vector<UTOrangeConfBiz> mConfBizList = new Vector<>();
    private Map<String, UTOrangeConfBiz> mConfBizMap = new HashMap();
    private String mDefaultWhiteConfigs = null;
    private List<UTDBConfigEntity> mLocalDBConfigEntities = new LinkedList();

    public static void sendConfigTimeStamp(String str) {
    }

    public abstract void requestOnlineConfig();

    public void setDefaultWhiteConfigs(String str) {
        this.mDefaultWhiteConfigs = str;
    }

    public UTBaseConfMgr() {
        setDefaultWhiteConfigs(this.DefaultWhiteConfigs1);
    }

    public synchronized void addConfBiz(UTOrangeConfBiz uTOrangeConfBiz) {
        if (uTOrangeConfBiz != null) {
            this.mConfBizList.add(uTOrangeConfBiz);
            for (String put : uTOrangeConfBiz.getOrangeGroupnames()) {
                this.mConfBizMap.put(put, uTOrangeConfBiz);
            }
        }
    }

    public synchronized void removeConfBiz(UTOrangeConfBiz uTOrangeConfBiz) {
        this.mConfBizList.remove(uTOrangeConfBiz);
        for (String remove : uTOrangeConfBiz.getOrangeGroupnames()) {
            this.mConfBizMap.remove(remove);
        }
    }

    /* access modifiers changed from: protected */
    public final synchronized List<UTDBConfigEntity> getLocalDBConfigEntities() {
        return this.mLocalDBConfigEntities;
    }

    private final synchronized void _updateLocalConfigEntities(List<UTDBConfigEntity> list) {
        this.mLocalDBConfigEntities = list;
    }

    /* access modifiers changed from: protected */
    public final synchronized void init() {
        this.mLocalDBConfigEntities = _loadAllDBConfig();
        if ((this.mLocalDBConfigEntities == null || this.mLocalDBConfigEntities.size() == 0) && this.mDefaultWhiteConfigs != null) {
            try {
                this.mLocalDBConfigEntities = UTConfigUtils.convertOnlineJsonConfToUTDBConfigEntities(new JSONObject(this.mDefaultWhiteConfigs));
            } catch (JSONException e) {
                Logger.e(null, e, new Object[0]);
            }
        }
    }

    private final synchronized List<UTDBConfigEntity> _loadAllDBConfig() {
        return Variables.getInstance().getDbMgr().find(UTDBConfigEntity.class, null, null, -1);
    }

    /* access modifiers changed from: protected */
    public final synchronized void deleteDBConfigEntity(String str) {
        if (str != null) {
            if (this.mLocalDBConfigEntities != null) {
                LinkedList<UTDBConfigEntity> linkedList = new LinkedList<>();
                for (UTDBConfigEntity next : this.mLocalDBConfigEntities) {
                    if (str.equals(next.getGroupname())) {
                        next.delete();
                        linkedList.add(next);
                    }
                }
                for (UTDBConfigEntity remove : linkedList) {
                    this.mLocalDBConfigEntities.remove(remove);
                }
            }
        }
    }

    private final synchronized void _clearAllOnlineConfig() {
        Variables.getInstance().getDbMgr().clear(UTDBConfigEntity.class);
        _updateLocalConfigEntities(new LinkedList());
    }

    private final synchronized void _dispatchConfig(List<UTDBConfigEntity> list) {
        String[] orangeGroupnames;
        if (list != null) {
            HashMap hashMap = new HashMap();
            for (UTDBConfigEntity next : list) {
                if (next.getGroupname() != null) {
                    hashMap.put(next.getGroupname(), next);
                }
            }
            Iterator<UTOrangeConfBiz> it = this.mConfBizList.iterator();
            while (it.hasNext()) {
                UTOrangeConfBiz next2 = it.next();
                for (String str : next2.getOrangeGroupnames()) {
                    if (hashMap.containsKey(str)) {
                        UTDBConfigEntity uTDBConfigEntity = (UTDBConfigEntity) hashMap.get(str);
                        if (Logger.isDebug()) {
                            Logger.d((String) "", "Groupname", str, "DBConfEntity", StringUtils.transStringToMap(uTDBConfigEntity.getConfContent()));
                        }
                        next2.onOrangeConfigurationArrive(str, StringUtils.transStringToMap(uTDBConfigEntity.getConfContent()));
                    } else {
                        next2.onNonOrangeConfigurationArrive(str);
                    }
                }
            }
        }
    }

    private final synchronized void _dispatchNonConfig() {
        Iterator<UTOrangeConfBiz> it = this.mConfBizList.iterator();
        while (it.hasNext()) {
            UTOrangeConfBiz next = it.next();
            for (String onNonOrangeConfigurationArrive : next.getOrangeGroupnames()) {
                next.onNonOrangeConfigurationArrive(onNonOrangeConfigurationArrive);
            }
        }
    }

    /* access modifiers changed from: protected */
    public final void dispatchLocalCacheConfigs() {
        _dispatchConfig(getLocalDBConfigEntities());
    }

    private final synchronized List<UTDBConfigEntity> _mergeAndSaveConfig(List<UTDBConfigEntity> list) {
        LinkedList linkedList;
        HashMap hashMap = new HashMap();
        for (UTDBConfigEntity next : this.mLocalDBConfigEntities) {
            if (next.getGroupname() != null) {
                hashMap.put(next.getGroupname(), next);
            }
        }
        linkedList = new LinkedList();
        for (UTDBConfigEntity next2 : list) {
            String groupname = next2.getGroupname();
            if (groupname != null) {
                UTDBConfigEntity uTDBConfigEntity = (UTDBConfigEntity) hashMap.get(groupname);
                if (uTDBConfigEntity != null) {
                    if (!next2.is304()) {
                        uTDBConfigEntity.setConfContent(next2.getConfContent());
                        uTDBConfigEntity.setConfTimestamp(next2.getConfTimestamp());
                    }
                    uTDBConfigEntity.store();
                    linkedList.add(uTDBConfigEntity);
                } else {
                    deleteDBConfigEntity(groupname);
                    next2.store();
                    linkedList.add(next2);
                    this.mLocalDBConfigEntities.add(next2);
                }
            }
        }
        return linkedList;
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x001f, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final synchronized void updateAndDispatch(java.util.List<com.alibaba.analytics.core.config.UTDBConfigEntity> r1, boolean r2) {
        /*
            r0 = this;
            monitor-enter(r0)
            if (r1 != 0) goto L_0x000a
            r0._dispatchNonConfig()     // Catch:{ all -> 0x0008 }
            monitor-exit(r0)
            return
        L_0x0008:
            r1 = move-exception
            goto L_0x0020
        L_0x000a:
            if (r2 == 0) goto L_0x000f
            r0._clearAllOnlineConfig()     // Catch:{ all -> 0x0008 }
        L_0x000f:
            java.util.List r1 = r0._mergeAndSaveConfig(r1)     // Catch:{ all -> 0x0008 }
            if (r1 == 0) goto L_0x001e
            int r2 = r1.size()     // Catch:{ all -> 0x0008 }
            if (r2 <= 0) goto L_0x001e
            r0._dispatchConfig(r1)     // Catch:{ all -> 0x0008 }
        L_0x001e:
            monitor-exit(r0)
            return
        L_0x0020:
            monitor-exit(r0)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.analytics.core.config.UTBaseConfMgr.updateAndDispatch(java.util.List, boolean):void");
    }

    /* access modifiers changed from: protected */
    public final synchronized void updateAndDispatch(UTDBConfigEntity uTDBConfigEntity, boolean z) {
        LinkedList linkedList = new LinkedList();
        linkedList.add(uTDBConfigEntity);
        updateAndDispatch((List<UTDBConfigEntity>) linkedList, z);
    }

    public final synchronized void updateAndDispatch(String str, Map<String, String> map) {
        if (map != null) {
            String remove = map.remove(TAG_CONFIG_TIMESTAMP);
            if (!TextUtils.isEmpty(remove)) {
                ConfigTimeStampMgr.getInstance().put(str, remove);
            }
            long j = 0;
            if (remove != null) {
                try {
                    j = Long.valueOf(remove).longValue();
                } catch (Throwable unused) {
                    Logger.e((String) "parse Timestamp error", "timestamp", remove);
                }
            }
            updateAndDispatch(UTConfigUtils.convertKVToDBConfigEntity(str, map, j), false);
        }
    }
}
