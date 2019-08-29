package com.autonavi.minimap.ajx3.modules;

import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.core.JsFunctionCallback;
import com.autonavi.sync.beans.JsonDataWithId;
import com.autonavi.sync.beans.JsonDatasWithType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import org.json.JSONArray;
import org.json.JSONObject;

@AjxModule("cloudSync")
public class ModuleCloudSync extends AbstractModule {
    public static final String MODULE_NAME = "cloudSync";
    private static volatile int token_merge_end_listener;
    private static volatile int token_sync_end_listener;
    private static volatile int token_want_merge_listener;
    private MySyncDataSuccessListener mCloudSyncEndListener;
    /* access modifiers changed from: private */
    public boolean mMergeAlertFlag = false;
    private HashMap<String, MySyncMergeEndListener> mMergeEndListenerSet = new HashMap<>();
    private HashMap<String, MySyncDataSuccessListener> mSyncEndListenerSet = new HashMap<>();
    private HashMap<String, MySyncWantMergeListener> mWantMergeListenerSet = new HashMap<>();

    static class MySyncDataSuccessListener implements biy {
        private JsFunctionCallback mJsCallback;

        private MySyncDataSuccessListener() {
            this.mJsCallback = null;
        }

        public void updateSuccess() {
            if (this.mJsCallback != null) {
                this.mJsCallback.callback("1");
            }
        }

        public void setJsCallback(JsFunctionCallback jsFunctionCallback) {
            this.mJsCallback = jsFunctionCallback;
        }
    }

    static class MySyncMergeEndListener implements bja {
        private JsFunctionCallback mJsCallback;

        private MySyncMergeEndListener() {
            this.mJsCallback = null;
        }

        public void onMergeEnd(boolean z) {
            if (this.mJsCallback != null) {
                this.mJsCallback.callback("1");
            }
        }

        public void setJsCallback(JsFunctionCallback jsFunctionCallback) {
            this.mJsCallback = jsFunctionCallback;
        }
    }

    static class MySyncWantMergeListener implements bjc {
        private JsFunctionCallback mJsCallback;

        private MySyncWantMergeListener() {
            this.mJsCallback = null;
        }

        public void onWantMerge() {
            if (this.mJsCallback != null) {
                this.mJsCallback.callback("1");
            }
        }

        public void setJsCallback(JsFunctionCallback jsFunctionCallback) {
            this.mJsCallback = jsFunctionCallback;
        }
    }

    public ModuleCloudSync(IAjxContext iAjxContext) {
        super(iAjxContext);
    }

    public void onModuleDestroy() {
        super.onModuleDestroy();
        clearSyncEndListener();
        clearMergeEndListener();
        clearWantMergeListener();
    }

    @AjxMethod(invokeMode = "sync", value = "addSyncEndListener")
    public String addSyncEndListener(JsFunctionCallback jsFunctionCallback) {
        if (jsFunctionCallback == null) {
            return "";
        }
        int i = token_sync_end_listener + 1;
        token_sync_end_listener = i;
        String valueOf = String.valueOf(i);
        MySyncDataSuccessListener mySyncDataSuccessListener = new MySyncDataSuccessListener();
        this.mSyncEndListenerSet.put(valueOf, mySyncDataSuccessListener);
        mySyncDataSuccessListener.setJsCallback(jsFunctionCallback);
        bim.aa().b((biy) mySyncDataSuccessListener);
        return valueOf;
    }

    @AjxMethod(invokeMode = "sync", value = "removeSyncEndListener")
    public int removeSyncEndListener(String str) {
        MySyncDataSuccessListener remove = this.mSyncEndListenerSet.remove(str);
        bim.aa().c((biy) remove);
        return remove != null ? 1 : 0;
    }

    private void clearSyncEndListener() {
        for (Entry<String, MySyncDataSuccessListener> value : this.mSyncEndListenerSet.entrySet()) {
            bim.aa().c((biy) (MySyncDataSuccessListener) value.getValue());
        }
    }

    @AjxMethod(invokeMode = "sync", value = "addMergeEndListener")
    public String addMergeEndListener(JsFunctionCallback jsFunctionCallback) {
        if (jsFunctionCallback == null) {
            return "";
        }
        int i = token_merge_end_listener + 1;
        token_merge_end_listener = i;
        String valueOf = String.valueOf(i);
        MySyncMergeEndListener mySyncMergeEndListener = new MySyncMergeEndListener();
        this.mMergeEndListenerSet.put(valueOf, mySyncMergeEndListener);
        mySyncMergeEndListener.setJsCallback(jsFunctionCallback);
        bim.aa().a((bja) mySyncMergeEndListener);
        return valueOf;
    }

    @AjxMethod(invokeMode = "sync", value = "removeMergeEndListener")
    public int removeMergeEndListener(String str) {
        MySyncMergeEndListener remove = this.mMergeEndListenerSet.remove(str);
        bim.aa().b((bja) remove);
        return remove != null ? 1 : 0;
    }

    private void clearMergeEndListener() {
        for (Entry<String, MySyncMergeEndListener> value : this.mMergeEndListenerSet.entrySet()) {
            bim.aa().b((bja) (MySyncMergeEndListener) value.getValue());
        }
    }

    @AjxMethod(invokeMode = "sync", value = "setSilentMergeFlag")
    public String setSilentMergeFlag() {
        return bim.aa().K();
    }

    @AjxMethod(invokeMode = "sync", value = "removeSilentMergeFlag")
    public int removeSilentMergeFlag(String str) {
        return bim.aa().n(str) ? 1 : 0;
    }

    /* JADX WARNING: Removed duplicated region for block: B:11:0x0073 A[SYNTHETIC, Splitter:B:11:0x0073] */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x0092  */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x009e A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x009f A[RETURN] */
    @com.autonavi.minimap.ajx3.modules.AjxMethod(invokeMode = "sync", value = "needShowSyncTip")
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int needShowSyncTip() {
        /*
            r8 = this;
            bim r0 = defpackage.bim.aa()
            boolean r0 = r0.t()
            com.autonavi.amap.app.AMapAppGlobal.getApplication()
            defpackage.bst.a()
            bim r0 = defpackage.bim.aa()
            java.lang.String r0 = r0.x()
            int r0 = defpackage.bst.a(r0)
            com.autonavi.amap.app.AMapAppGlobal.getApplication()
            defpackage.bss.a()
            bim r1 = defpackage.bim.aa()
            java.lang.String r1 = r1.x()
            int r1 = defpackage.bss.a(r1)
            bim r2 = defpackage.bim.aa()
            java.lang.String r3 = "901"
            int r2 = r2.a(r3)
            bim r3 = defpackage.bim.aa()
            com.autonavi.sync.GirfSyncServiceSDK$GirfSyncService r3 = r3.n()
            java.lang.String r3 = r3.getFrequentAddress()
            boolean r4 = android.text.TextUtils.isEmpty(r3)
            r5 = 0
            if (r4 != 0) goto L_0x0060
            org.json.JSONObject r4 = new org.json.JSONObject     // Catch:{ JSONException -> 0x005c }
            r4.<init>(r3)     // Catch:{ JSONException -> 0x005c }
            java.lang.String r3 = "value"
            org.json.JSONArray r3 = r4.optJSONArray(r3)     // Catch:{ JSONException -> 0x005c }
            if (r3 == 0) goto L_0x0060
            int r3 = r3.length()     // Catch:{ JSONException -> 0x005c }
            goto L_0x0061
        L_0x005c:
            r3 = move-exception
            r3.printStackTrace()
        L_0x0060:
            r3 = 0
        L_0x0061:
            bim r4 = defpackage.bim.aa()
            java.lang.String r6 = "1001"
            java.lang.String r7 = "1001"
            java.lang.String r4 = r4.b(r6, r7)
            boolean r6 = android.text.TextUtils.isEmpty(r4)
            if (r6 != 0) goto L_0x008a
            org.json.JSONObject r6 = new org.json.JSONObject     // Catch:{ JSONException -> 0x0086 }
            r6.<init>(r4)     // Catch:{ JSONException -> 0x0086 }
            java.lang.String r4 = "value"
            org.json.JSONArray r4 = r6.optJSONArray(r4)     // Catch:{ JSONException -> 0x0086 }
            if (r4 == 0) goto L_0x008a
            int r4 = r4.length()     // Catch:{ JSONException -> 0x0086 }
            goto L_0x008b
        L_0x0086:
            r4 = move-exception
            r4.printStackTrace()
        L_0x008a:
            r4 = 0
        L_0x008b:
            int r0 = r0 + r1
            int r0 = r0 + r2
            int r0 = r0 + r3
            int r0 = r0 + r4
            r1 = 1
            if (r0 <= 0) goto L_0x009b
            r2 = 3
            if (r0 == r2) goto L_0x0099
            int r0 = r0 % 5
            if (r0 != 0) goto L_0x009b
        L_0x0099:
            r0 = 1
            goto L_0x009c
        L_0x009b:
            r0 = 0
        L_0x009c:
            if (r0 == 0) goto L_0x009f
            return r1
        L_0x009f:
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.ajx3.modules.ModuleCloudSync.needShowSyncTip():int");
    }

    @AjxMethod(invokeMode = "sync", value = "getJsonData")
    public String getJsonData(String str, String str2) {
        return bim.aa().b(str, str2);
    }

    @AjxMethod(invokeMode = "sync", value = "setJsonData")
    public void setJsonData(String str, String str2, String str3, int i) {
        bim.aa().a(str, str2, str3, i);
    }

    @AjxMethod(invokeMode = "sync", value = "setSettingsValue")
    public void setSettingsValue(String str, String str2) {
        bim.aa().c(str, str2);
    }

    @AjxMethod(invokeMode = "sync", value = "getSettingsValue")
    public String getSettingsValue(String str) {
        return bim.aa().m(str);
    }

    @AjxMethod(invokeMode = "sync", value = "getSyncDataArrayWithType")
    public String getSyncDataArrayWithType(String str, int i, int i2) {
        if (i2 <= 0) {
            return "";
        }
        JsonDatasWithType h = bim.aa().h(str);
        if (h == null) {
            return "";
        }
        ArrayList arrayList = (ArrayList) h.jsonDataWithId;
        if (arrayList == null || arrayList.size() == 0) {
            return "";
        }
        int i3 = i > 0 ? i - 1 : 0;
        JSONArray jSONArray = new JSONArray();
        for (int i4 = 0; i4 < i2; i4++) {
            int i5 = i3 + i4;
            if (i5 >= arrayList.size()) {
                break;
            }
            JsonDataWithId jsonDataWithId = (JsonDataWithId) arrayList.get(i5);
            try {
                JSONObject jSONObject = new JSONObject(jsonDataWithId.data);
                jSONObject.put("id", jsonDataWithId.id);
                jSONArray.put(jSONObject);
            } catch (Exception unused) {
            }
        }
        return jSONArray.toString();
    }

    @AjxMethod(invokeMode = "sync", value = "clearJsonData")
    public int clearJsonData(String str, String str2) {
        return bim.aa().b(str, str2, 1);
    }

    @AjxMethod(invokeMode = "sync", value = "getDataCountWithType")
    public int getDataCountWithType(String str) {
        return bim.aa().a(str);
    }

    @AjxMethod(invokeMode = "sync", value = "needShowMergeDialog")
    public int needShowMergeDialog() {
        return bim.aa().w() ? 1 : 0;
    }

    @AjxMethod(invokeMode = "sync", value = "addWantMergeListener")
    public String addWantMergeListener(JsFunctionCallback jsFunctionCallback) {
        if (jsFunctionCallback == null) {
            return "";
        }
        int i = token_want_merge_listener + 1;
        token_want_merge_listener = i;
        String valueOf = String.valueOf(i);
        MySyncWantMergeListener mySyncWantMergeListener = new MySyncWantMergeListener();
        this.mWantMergeListenerSet.put(valueOf, mySyncWantMergeListener);
        mySyncWantMergeListener.setJsCallback(jsFunctionCallback);
        bim.aa().a((bjc) mySyncWantMergeListener);
        return valueOf;
    }

    @AjxMethod(invokeMode = "sync", value = "removeWantMergeListener")
    public int removeWantMergeListener(String str) {
        MySyncWantMergeListener remove = this.mWantMergeListenerSet.remove(str);
        bim.aa().b((bjc) remove);
        return remove != null ? 1 : 0;
    }

    private void clearWantMergeListener() {
        for (Entry<String, MySyncWantMergeListener> value : this.mWantMergeListenerSet.entrySet()) {
            bim.aa().b((bjc) (MySyncWantMergeListener) value.getValue());
        }
    }

    @AjxMethod(invokeMode = "sync", value = "addWantMergeMessage")
    public void addWantMergeMessage() {
        bim.aa().a(getContext().getNativeContext().getResources().getString(R.string.sync_copyhistory_tip), (String) "amapuri://map/showmergedialog", 171, (String) "1");
    }

    @AjxMethod("cloudSyncMergeAlertFlag")
    public void cloudSyncMergeAlertFlag(int i) {
        boolean z = true;
        if (i != 1) {
            z = false;
        }
        this.mMergeAlertFlag = z;
    }

    @AjxMethod("cloudSyncCheckMerge")
    public void cloudSyncCheckMerge(int i) {
        if (i == 1) {
            if (bim.aa().w()) {
                aho.a(new Runnable() {
                    public void run() {
                        if (ModuleCloudSync.this.mMergeAlertFlag) {
                            bid pageContext = AMapPageUtil.getPageContext();
                            if (pageContext != null) {
                                atu atu = new atu();
                                atu.a(pageContext);
                                atu.a();
                            }
                        } else {
                            bim.aa().a(ModuleCloudSync.this.getContext().getNativeContext().getResources().getString(R.string.sync_copyhistory_tip), (String) "amapuri://map/showmergedialog", 171, (String) "1");
                        }
                        bim.aa().k(false);
                    }
                });
            }
            bim.aa().a((bit) new bit() {
                public void showDialog() {
                    aho.a(new Runnable() {
                        public void run() {
                            if (ModuleCloudSync.this.mMergeAlertFlag) {
                                bid pageContext = AMapPageUtil.getPageContext();
                                if (pageContext != null) {
                                    atu atu = new atu();
                                    atu.a(pageContext);
                                    atu.a = true;
                                    atu.a();
                                }
                            } else {
                                bim.aa().a(ModuleCloudSync.this.getContext().getNativeContext().getResources().getString(R.string.sync_copyhistory_tip), (String) "amapuri://map/showmergedialog", 171, (String) "1");
                            }
                            bim.aa().k(false);
                        }
                    });
                }
            });
            return;
        }
        bim.aa().a((bit) null);
    }

    @AjxMethod("cloudSyncEndListener")
    public void cloudSyncEndListener(JsFunctionCallback jsFunctionCallback) {
        if (this.mCloudSyncEndListener == null) {
            this.mCloudSyncEndListener = new MySyncDataSuccessListener();
        }
        bim.aa().c((biy) this.mCloudSyncEndListener);
        this.mCloudSyncEndListener.setJsCallback(jsFunctionCallback);
        if (jsFunctionCallback != null) {
            bim.aa().b((biy) this.mCloudSyncEndListener);
        }
    }

    @AjxMethod(invokeMode = "sync", value = "confirmMerge")
    public void confirmMerge() {
        bin.a.m(true);
    }

    @AjxMethod(invokeMode = "sync", value = "cancelMerge")
    public void cancelMerge() {
        bin.a.m(false);
    }
}
