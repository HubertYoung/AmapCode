package com.autonavi.minimap.ajx3.debug;

import com.autonavi.minimap.ajx3.core.JsContextRef;
import com.autonavi.minimap.ajx3.util.AjxDebugUtils;
import org.json.JSONObject;

public class AjxSocketHandler {
    public static final String IP_PATH = "/sdcard/autonavi/ip.txt";
    private AjxDebugUtils ajxDebugUtils;
    private long curContextId;

    public AjxSocketHandler(AjxDebugUtils ajxDebugUtils2) {
        this.ajxDebugUtils = ajxDebugUtils2;
    }

    public void handleCmd(String str) {
        try {
            if (handleCmd(new JSONObject(str))) {
                JsContextRef.debugCommand(str);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void handleInspector(long j, String str) {
        try {
            if (handleMethod(j, new JSONObject(str))) {
                JsContextRef.debugCommand(str);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:12:0x002f A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:13:0x0030  */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x0036  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean handleCmd(org.json.JSONObject r6) throws org.json.JSONException {
        /*
            r5 = this;
            java.lang.String r0 = "cmd"
            java.lang.String r0 = r6.getString(r0)
            int r1 = r0.hashCode()
            r2 = -1953714197(0xffffffff8b8cafeb, float:-5.419073E-32)
            r3 = 0
            r4 = 1
            if (r1 == r2) goto L_0x0021
            r2 = -1779258180(0xffffffff95f2acbc, float:-9.801555E-26)
            if (r1 == r2) goto L_0x0017
            goto L_0x002b
        L_0x0017:
            java.lang.String r1 = "ajx.debug.reloadURL"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x002b
            r0 = 1
            goto L_0x002c
        L_0x0021:
            java.lang.String r1 = "ajx.debug.openURL"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x002b
            r0 = 0
            goto L_0x002c
        L_0x002b:
            r0 = -1
        L_0x002c:
            switch(r0) {
                case 0: goto L_0x0036;
                case 1: goto L_0x0030;
                default: goto L_0x002f;
            }
        L_0x002f:
            return r3
        L_0x0030:
            com.autonavi.minimap.ajx3.util.AjxDebugUtils r6 = r5.ajxDebugUtils
            r6.reloadUrl()
            return r4
        L_0x0036:
            java.lang.String r0 = "args"
            org.json.JSONArray r6 = r6.getJSONArray(r0)
            java.lang.Object r6 = r6.get(r3)
            java.lang.String r6 = (java.lang.String) r6
            com.autonavi.minimap.ajx3.util.AjxDebugUtils r0 = r5.ajxDebugUtils
            r0.openUrl(r6)
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.ajx3.debug.AjxSocketHandler.handleCmd(org.json.JSONObject):boolean");
    }

    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean handleMethod(long r3, org.json.JSONObject r5) throws org.json.JSONException {
        /*
            r2 = this;
            java.lang.String r0 = "method"
            java.lang.String r0 = r5.getString(r0)
            com.autonavi.minimap.ajx3.debug.AjxInspector.setContextId(r3)
            int r3 = r0.hashCode()
            r4 = 0
            r1 = 1
            switch(r3) {
                case -1154924519: goto L_0x0070;
                case -890707658: goto L_0x0065;
                case -470436135: goto L_0x005b;
                case -360486120: goto L_0x0051;
                case -270313910: goto L_0x0047;
                case -233777704: goto L_0x003d;
                case 618852101: goto L_0x0033;
                case 738116684: goto L_0x0028;
                case 796265827: goto L_0x001e;
                case 1676315462: goto L_0x0014;
                default: goto L_0x0012;
            }
        L_0x0012:
            goto L_0x007a
        L_0x0014:
            java.lang.String r3 = "DOMStorage.getDOMStorageItems"
            boolean r3 = r0.equals(r3)
            if (r3 == 0) goto L_0x007a
            r3 = 3
            goto L_0x007b
        L_0x001e:
            java.lang.String r3 = "Network.enable"
            boolean r3 = r0.equals(r3)
            if (r3 == 0) goto L_0x007a
            r3 = 1
            goto L_0x007b
        L_0x0028:
            java.lang.String r3 = "Database.executeSQL"
            boolean r3 = r0.equals(r3)
            if (r3 == 0) goto L_0x007a
            r3 = 9
            goto L_0x007b
        L_0x0033:
            java.lang.String r3 = "DOMStorage.removeDOMStorageItem"
            boolean r3 = r0.equals(r3)
            if (r3 == 0) goto L_0x007a
            r3 = 5
            goto L_0x007b
        L_0x003d:
            java.lang.String r3 = "Database.getDatabaseTableNames"
            boolean r3 = r0.equals(r3)
            if (r3 == 0) goto L_0x007a
            r3 = 7
            goto L_0x007b
        L_0x0047:
            java.lang.String r3 = "DOMStorage.getLocalStorageNamespace"
            boolean r3 = r0.equals(r3)
            if (r3 == 0) goto L_0x007a
            r3 = 2
            goto L_0x007b
        L_0x0051:
            java.lang.String r3 = "DOMStorage.clear"
            boolean r3 = r0.equals(r3)
            if (r3 == 0) goto L_0x007a
            r3 = 4
            goto L_0x007b
        L_0x005b:
            java.lang.String r3 = "DOMStorage.setDOMStorageItem"
            boolean r3 = r0.equals(r3)
            if (r3 == 0) goto L_0x007a
            r3 = 6
            goto L_0x007b
        L_0x0065:
            java.lang.String r3 = "Database.enable"
            boolean r3 = r0.equals(r3)
            if (r3 == 0) goto L_0x007a
            r3 = 8
            goto L_0x007b
        L_0x0070:
            java.lang.String r3 = "Network.getResponseBody"
            boolean r3 = r0.equals(r3)
            if (r3 == 0) goto L_0x007a
            r3 = 0
            goto L_0x007b
        L_0x007a:
            r3 = -1
        L_0x007b:
            switch(r3) {
                case 0: goto L_0x012b;
                case 1: goto L_0x0127;
                case 2: goto L_0x011d;
                case 3: goto L_0x0109;
                case 4: goto L_0x00f5;
                case 5: goto L_0x00dd;
                case 6: goto L_0x00c1;
                case 7: goto L_0x00a5;
                case 8: goto L_0x00a1;
                case 9: goto L_0x007f;
                default: goto L_0x007e;
            }
        L_0x007e:
            return r4
        L_0x007f:
            java.lang.String r3 = "id"
            int r3 = r5.getInt(r3)
            java.lang.String r4 = "params"
            org.json.JSONObject r4 = r5.getJSONObject(r4)
            java.lang.String r0 = "databaseId"
            java.lang.String r4 = r4.getString(r0)
            java.lang.String r0 = "params"
            org.json.JSONObject r5 = r5.getJSONObject(r0)
            java.lang.String r0 = "query"
            java.lang.String r5 = r5.getString(r0)
            com.autonavi.minimap.ajx3.debug.AjxInspector.executeSQL(r3, r4, r5)
            return r1
        L_0x00a1:
            com.autonavi.minimap.ajx3.debug.AjxInspector.setDatabaseEnable(r1)
            return r1
        L_0x00a5:
            boolean r3 = com.autonavi.minimap.ajx3.debug.AjxInspector.isDatabaseEnable()
            if (r3 == 0) goto L_0x00c0
            java.lang.String r3 = "id"
            int r3 = r5.getInt(r3)
            java.lang.String r4 = "params"
            org.json.JSONObject r4 = r5.getJSONObject(r4)
            java.lang.String r5 = "databaseId"
            java.lang.String r4 = r4.getString(r5)
            com.autonavi.minimap.ajx3.debug.AjxInspector.getDatabaseTableNames(r3, r4)
        L_0x00c0:
            return r1
        L_0x00c1:
            boolean r3 = com.autonavi.minimap.ajx3.debug.AjxInspector.isLocalStorage(r5)
            if (r3 == 0) goto L_0x00dc
            java.lang.String r3 = "id"
            int r3 = r5.getInt(r3)
            java.lang.String r4 = com.autonavi.minimap.ajx3.debug.AjxInspector.getNamespace(r5)
            java.lang.String r0 = com.autonavi.minimap.ajx3.debug.AjxInspector.getKeyOfRemoveDOMStorageItem(r5)
            java.lang.String r5 = com.autonavi.minimap.ajx3.debug.AjxInspector.getValueOfsetDOMStorageItem(r5)
            com.autonavi.minimap.ajx3.debug.AjxInspector.setDOMStorageItem(r3, r4, r0, r5)
        L_0x00dc:
            return r1
        L_0x00dd:
            boolean r3 = com.autonavi.minimap.ajx3.debug.AjxInspector.isLocalStorage(r5)
            if (r3 == 0) goto L_0x00f4
            java.lang.String r3 = "id"
            int r3 = r5.getInt(r3)
            java.lang.String r4 = com.autonavi.minimap.ajx3.debug.AjxInspector.getNamespace(r5)
            java.lang.String r5 = com.autonavi.minimap.ajx3.debug.AjxInspector.getKeyOfRemoveDOMStorageItem(r5)
            com.autonavi.minimap.ajx3.debug.AjxInspector.removeLocalStorage(r3, r4, r5)
        L_0x00f4:
            return r1
        L_0x00f5:
            boolean r3 = com.autonavi.minimap.ajx3.debug.AjxInspector.isLocalStorage(r5)
            if (r3 == 0) goto L_0x0108
            java.lang.String r3 = "id"
            int r3 = r5.getInt(r3)
            java.lang.String r4 = com.autonavi.minimap.ajx3.debug.AjxInspector.getNamespace(r5)
            com.autonavi.minimap.ajx3.debug.AjxInspector.clearLocalStorage(r3, r4)
        L_0x0108:
            return r1
        L_0x0109:
            boolean r3 = com.autonavi.minimap.ajx3.debug.AjxInspector.isLocalStorage(r5)
            if (r3 == 0) goto L_0x011c
            java.lang.String r3 = "id"
            int r3 = r5.getInt(r3)
            java.lang.String r4 = com.autonavi.minimap.ajx3.debug.AjxInspector.getNamespace(r5)
            com.autonavi.minimap.ajx3.debug.AjxInspector.getDOMStorageItems(r3, r4)
        L_0x011c:
            return r1
        L_0x011d:
            java.lang.String r3 = "id"
            int r3 = r5.getInt(r3)
            com.autonavi.minimap.ajx3.debug.AjxInspector.getLocalStorageNamespace(r3)
            return r1
        L_0x0127:
            com.autonavi.minimap.ajx3.debug.AjxInspector.setNetworkEnable(r1)
            return r1
        L_0x012b:
            java.lang.String r3 = "id"
            int r3 = r5.getInt(r3)
            java.lang.String r4 = "params"
            org.json.JSONObject r4 = r5.getJSONObject(r4)
            java.lang.String r5 = "requestId"
            java.lang.String r4 = r4.getString(r5)
            com.autonavi.minimap.ajx3.debug.AjxInspector.sendRespToInspector(r3, r4)
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.ajx3.debug.AjxSocketHandler.handleMethod(long, org.json.JSONObject):boolean");
    }
}
