package com.alibaba.baichuan.android.jsbridge;

import com.alibaba.baichuan.android.trade.utils.log.AlibcLogger;
import com.alibaba.fastjson.JSONObject;

public class AlibcJsResult {
    public static final String CLOSED = "7";
    public static final String FAIL = "6";
    public static final String NO_METHOD = "1";
    public static final String NO_PERMISSION = "4";
    public static final String PARAM_ERR = "2";
    public static final AlibcJsResult RET_CLOSED = new AlibcJsResult("7");
    public static final AlibcJsResult RET_FAIL = new AlibcJsResult("6");
    public static final AlibcJsResult RET_NO_METHOD = new AlibcJsResult("1");
    public static final AlibcJsResult RET_NO_PERMISSION = new AlibcJsResult("4");
    public static final AlibcJsResult RET_PARAM_ERR = new AlibcJsResult("2");
    public static final AlibcJsResult RET_SUCCESS = new AlibcJsResult("0");
    public static final String SUCCESS = "0";
    public static final String TIMEOUT = "5";
    public static final String UNKNOWN_ERR = "3";
    private JSONObject a = new JSONObject();
    private String b = "6";
    private String c = "";

    public AlibcJsResult() {
    }

    public AlibcJsResult(String str) {
        setResultMsg(str);
    }

    public void addData(String str, Object obj) {
        this.a.put(str, obj);
    }

    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.String getErrorMessage(java.lang.String r2) {
        /*
            r1 = this;
            int r0 = r2.hashCode()
            switch(r0) {
                case 48: goto L_0x004e;
                case 49: goto L_0x0044;
                case 50: goto L_0x003a;
                case 51: goto L_0x0030;
                case 52: goto L_0x0026;
                case 53: goto L_0x001c;
                case 54: goto L_0x0012;
                case 55: goto L_0x0008;
                default: goto L_0x0007;
            }
        L_0x0007:
            goto L_0x0058
        L_0x0008:
            java.lang.String r0 = "7"
            boolean r2 = r2.equals(r0)
            if (r2 == 0) goto L_0x0058
            r2 = 7
            goto L_0x0059
        L_0x0012:
            java.lang.String r0 = "6"
            boolean r2 = r2.equals(r0)
            if (r2 == 0) goto L_0x0058
            r2 = 6
            goto L_0x0059
        L_0x001c:
            java.lang.String r0 = "5"
            boolean r2 = r2.equals(r0)
            if (r2 == 0) goto L_0x0058
            r2 = 5
            goto L_0x0059
        L_0x0026:
            java.lang.String r0 = "4"
            boolean r2 = r2.equals(r0)
            if (r2 == 0) goto L_0x0058
            r2 = 4
            goto L_0x0059
        L_0x0030:
            java.lang.String r0 = "3"
            boolean r2 = r2.equals(r0)
            if (r2 == 0) goto L_0x0058
            r2 = 3
            goto L_0x0059
        L_0x003a:
            java.lang.String r0 = "2"
            boolean r2 = r2.equals(r0)
            if (r2 == 0) goto L_0x0058
            r2 = 2
            goto L_0x0059
        L_0x0044:
            java.lang.String r0 = "1"
            boolean r2 = r2.equals(r0)
            if (r2 == 0) goto L_0x0058
            r2 = 1
            goto L_0x0059
        L_0x004e:
            java.lang.String r0 = "0"
            boolean r2 = r2.equals(r0)
            if (r2 == 0) goto L_0x0058
            r2 = 0
            goto L_0x0059
        L_0x0058:
            r2 = -1
        L_0x0059:
            switch(r2) {
                case 0: goto L_0x0074;
                case 1: goto L_0x0071;
                case 2: goto L_0x006e;
                case 3: goto L_0x006b;
                case 4: goto L_0x0068;
                case 5: goto L_0x0065;
                case 6: goto L_0x0062;
                case 7: goto L_0x005f;
                default: goto L_0x005c;
            }
        L_0x005c:
            java.lang.String r2 = "BC_UNKNOWN"
            return r2
        L_0x005f:
            java.lang.String r2 = "BC_CLOSED"
            return r2
        L_0x0062:
            java.lang.String r2 = "BC_FAILED"
            return r2
        L_0x0065:
            java.lang.String r2 = "BC_TIMEOUT"
            return r2
        L_0x0068:
            java.lang.String r2 = "BC_NO_PERMISSION"
            return r2
        L_0x006b:
            java.lang.String r2 = "BC_UNKNOWN_ERR"
            return r2
        L_0x006e:
            java.lang.String r2 = "BC_PARAM_ERR"
            return r2
        L_0x0071:
            java.lang.String r2 = "BC_NO_HANDLER"
            return r2
        L_0x0074:
            java.lang.String r2 = "BC_SUCCESS"
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.baichuan.android.jsbridge.AlibcJsResult.getErrorMessage(java.lang.String):java.lang.String");
    }

    public void setResultCode(String str) {
        this.b = str;
        this.c = getErrorMessage(str);
    }

    public void setResultMsg(String str) {
        this.c = str;
    }

    public void setSuccess() {
        this.b = "0";
        this.c = getErrorMessage("0");
    }

    public String toJsonString() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put((String) "code", (Object) this.b);
            jSONObject.put((String) "msg", (Object) this.c);
            jSONObject.put((String) "data", (Object) this.a);
        } catch (Exception e) {
            AlibcLogger.e("alibc", e.getMessage());
        }
        return jSONObject.toString();
    }
}
