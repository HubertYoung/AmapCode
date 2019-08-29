package com.alibaba.analytics.core.config;

import com.alibaba.analytics.utils.StringUtils;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONObject;

public class UTStreamConfBiz extends UTOrangeConfBiz {
    private static UTStreamConfBiz s_instance;
    private Map<String, UTStreamItem> mStreamItemMap = new HashMap();

    static class UTStreamItem {
        private static final String KEY_ARG1 = "arg1";
        private static final String KEY_STM = "stm";
        private Map<String, String> mArg1Stm = new HashMap();
        private String mDefaultStreamName = null;

        private UTStreamItem() {
        }

        public String getStmName(String str) {
            if (!StringUtils.isEmpty(str)) {
                String _getArg1StmName = _getArg1StmName(str);
                if (_getArg1StmName != null) {
                    return _getArg1StmName;
                }
            }
            return this.mDefaultStreamName;
        }

        private String _getArg1StmName(String str) {
            if (str != null) {
                for (String next : this.mArg1Stm.keySet()) {
                    if (!next.startsWith("%") || !next.endsWith("%")) {
                        if (str.equals(next)) {
                            return this.mArg1Stm.get(next);
                        }
                    } else if (str.contains(next.substring(1, next.length() - 1))) {
                        return this.mArg1Stm.get(next);
                    }
                }
            }
            return null;
        }

        public static UTStreamItem parseJson(String str) {
            try {
                UTStreamItem uTStreamItem = new UTStreamItem();
                JSONObject jSONObject = new JSONObject(str);
                if (jSONObject.has(KEY_STM)) {
                    uTStreamItem.mDefaultStreamName = jSONObject.optString(KEY_STM);
                }
                if (jSONObject.has(KEY_ARG1)) {
                    HashMap hashMap = new HashMap();
                    JSONObject optJSONObject = jSONObject.optJSONObject(KEY_ARG1);
                    if (optJSONObject != null) {
                        Iterator<String> keys = optJSONObject.keys();
                        while (keys.hasNext()) {
                            String next = keys.next();
                            hashMap.put(next, optJSONObject.optString(next));
                        }
                    }
                    uTStreamItem.mArg1Stm = hashMap;
                }
                return uTStreamItem;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    public static UTStreamConfBiz getInstance() {
        if (s_instance == null) {
            s_instance = new UTStreamConfBiz();
        }
        return s_instance;
    }

    private UTStreamConfBiz() {
    }

    public void resetStreamItemMap() {
        this.mStreamItemMap.clear();
    }

    /* JADX WARNING: Removed duplicated region for block: B:13:0x0031 A[Catch:{ Exception -> 0x001f }] */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x0044 A[DONT_GENERATE] */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x0046 A[SYNTHETIC, Splitter:B:18:0x0046] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized java.lang.String getStreamName(java.util.Map<java.lang.String, java.lang.String> r5) {
        /*
            r4 = this;
            monitor-enter(r4)
            com.alibaba.analytics.core.model.LogField r0 = com.alibaba.analytics.core.model.LogField.EVENTID     // Catch:{ all -> 0x0076 }
            java.lang.String r0 = r0.toString()     // Catch:{ all -> 0x0076 }
            boolean r0 = r5.containsKey(r0)     // Catch:{ all -> 0x0076 }
            r1 = -1
            if (r0 == 0) goto L_0x0023
            com.alibaba.analytics.core.model.LogField r0 = com.alibaba.analytics.core.model.LogField.EVENTID     // Catch:{ Exception -> 0x001f }
            java.lang.String r0 = r0.toString()     // Catch:{ Exception -> 0x001f }
            java.lang.Object r0 = r5.get(r0)     // Catch:{ Exception -> 0x001f }
            java.lang.String r0 = (java.lang.String) r0     // Catch:{ Exception -> 0x001f }
            int r0 = java.lang.Integer.parseInt(r0)     // Catch:{ Exception -> 0x001f }
            goto L_0x0024
        L_0x001f:
            r0 = move-exception
            r0.printStackTrace()     // Catch:{ all -> 0x0076 }
        L_0x0023:
            r0 = -1
        L_0x0024:
            r2 = 0
            com.alibaba.analytics.core.model.LogField r3 = com.alibaba.analytics.core.model.LogField.ARG1     // Catch:{ all -> 0x0076 }
            java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x0076 }
            boolean r3 = r5.containsKey(r3)     // Catch:{ all -> 0x0076 }
            if (r3 == 0) goto L_0x003e
            com.alibaba.analytics.core.model.LogField r2 = com.alibaba.analytics.core.model.LogField.ARG1     // Catch:{ all -> 0x0076 }
            java.lang.String r2 = r2.toString()     // Catch:{ all -> 0x0076 }
            java.lang.Object r5 = r5.get(r2)     // Catch:{ all -> 0x0076 }
            r2 = r5
            java.lang.String r2 = (java.lang.String) r2     // Catch:{ all -> 0x0076 }
        L_0x003e:
            java.lang.String r5 = r4._getStreamName(r0, r2)     // Catch:{ all -> 0x0076 }
            if (r5 == 0) goto L_0x0046
            monitor-exit(r4)
            return r5
        L_0x0046:
            int r5 = r0 % 10
            int r5 = r0 - r5
            java.lang.String r5 = r4._getStreamName(r5, r2)     // Catch:{ all -> 0x0076 }
            if (r5 == 0) goto L_0x0052
            monitor-exit(r4)
            return r5
        L_0x0052:
            int r5 = r0 % 100
            int r5 = r0 - r5
            java.lang.String r5 = r4._getStreamName(r5, r2)     // Catch:{ all -> 0x0076 }
            if (r5 == 0) goto L_0x005e
            monitor-exit(r4)
            return r5
        L_0x005e:
            int r5 = r0 % 1000
            int r0 = r0 - r5
            java.lang.String r5 = r4._getStreamName(r0, r2)     // Catch:{ all -> 0x0076 }
            if (r5 == 0) goto L_0x0069
            monitor-exit(r4)
            return r5
        L_0x0069:
            java.lang.String r5 = r4._getStreamName(r1, r2)     // Catch:{ all -> 0x0076 }
            if (r5 == 0) goto L_0x0071
            monitor-exit(r4)
            return r5
        L_0x0071:
            java.lang.String r5 = "stm_d"
            monitor-exit(r4)
            return r5
        L_0x0076:
            r5 = move-exception
            monitor-exit(r4)
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.analytics.core.config.UTStreamConfBiz.getStreamName(java.util.Map):java.lang.String");
    }

    private String _getStreamName(int i, String str) {
        String valueOf = String.valueOf(i);
        if (this.mStreamItemMap.containsKey(valueOf)) {
            return this.mStreamItemMap.get(valueOf).getStmName(str);
        }
        return null;
    }

    public void onNonOrangeConfigurationArrive(String str) {
        super.onNonOrangeConfigurationArrive(str);
    }

    public synchronized void onOrangeConfigurationArrive(String str, Map<String, String> map) {
        this.mStreamItemMap.clear();
        for (String next : map.keySet()) {
            String str2 = map.get(next);
            if (str2 != null) {
                UTStreamItem parseJson = UTStreamItem.parseJson(str2);
                if (parseJson != null) {
                    this.mStreamItemMap.put(next, parseJson);
                }
            }
        }
    }

    public String[] getOrangeGroupnames() {
        return new String[]{"ut_stream"};
    }
}
