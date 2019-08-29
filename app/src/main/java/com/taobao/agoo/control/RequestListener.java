package com.taobao.agoo.control;

import android.content.Context;
import android.text.TextUtils;
import com.taobao.accs.base.AccsAbstractDataListener;
import com.taobao.accs.base.TaoBaseService.ExtraInfo;
import com.taobao.accs.client.GlobalClientInfo;
import com.taobao.accs.utl.JsonUtility;
import com.taobao.agoo.ICallback;
import com.taobao.agoo.control.data.AliasDO;
import java.util.HashMap;
import java.util.Map;
import org.android.agoo.common.Config;
import org.json.JSONException;
import org.json.JSONObject;

public class RequestListener extends AccsAbstractDataListener {
    private static final String TAG = "RequestListener";
    public static AgooBindCache mAgooBindCache;
    public Map<String, ICallback> mListeners = new HashMap();

    public void onBind(String str, int i, ExtraInfo extraInfo) {
    }

    public void onData(String str, String str2, String str3, byte[] bArr, ExtraInfo extraInfo) {
    }

    public void onSendData(String str, String str2, int i, ExtraInfo extraInfo) {
    }

    public void onUnbind(String str, int i, ExtraInfo extraInfo) {
    }

    public RequestListener(Context context) {
        if (mAgooBindCache == null) {
            mAgooBindCache = new AgooBindCache(context.getApplicationContext());
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:68:0x0138, code lost:
        if (com.taobao.agoo.TaobaoConstants.SERVICE_ID_DEVICECMD.equals(r6) != false) goto L_0x013a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:69:0x013a, code lost:
        r5.mListeners.remove(r7);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:70:0x013f, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:77:0x0152, code lost:
        if (com.taobao.agoo.TaobaoConstants.SERVICE_ID_DEVICECMD.equals(r6) == false) goto L_0x0155;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:78:0x0155, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onResponse(java.lang.String r6, java.lang.String r7, int r8, byte[] r9, com.taobao.accs.base.TaoBaseService.ExtraInfo r10) {
        /*
            r5 = this;
            r10 = 0
            java.lang.String r0 = "AgooDeviceCmd"
            boolean r0 = r0.equals(r6)     // Catch:{ Throwable -> 0x0142 }
            if (r0 == 0) goto L_0x0132
            java.util.Map<java.lang.String, com.taobao.agoo.ICallback> r0 = r5.mListeners     // Catch:{ Throwable -> 0x0142 }
            java.lang.Object r0 = r0.get(r7)     // Catch:{ Throwable -> 0x0142 }
            com.taobao.agoo.ICallback r0 = (com.taobao.agoo.ICallback) r0     // Catch:{ Throwable -> 0x0142 }
            r1 = 200(0xc8, float:2.8E-43)
            if (r8 != r1) goto L_0x0127
            java.lang.String r8 = new java.lang.String     // Catch:{ Throwable -> 0x0142 }
            java.lang.String r1 = "utf-8"
            r8.<init>(r9, r1)     // Catch:{ Throwable -> 0x0142 }
            java.lang.String r9 = "RequestListener"
            java.lang.String r1 = "RequestListener onResponse"
            r2 = 6
            java.lang.Object[] r2 = new java.lang.Object[r2]     // Catch:{ Throwable -> 0x0142 }
            java.lang.String r3 = "dataId"
            r2[r10] = r3     // Catch:{ Throwable -> 0x0142 }
            r3 = 1
            r2[r3] = r7     // Catch:{ Throwable -> 0x0142 }
            r3 = 2
            java.lang.String r4 = "listener"
            r2[r3] = r4     // Catch:{ Throwable -> 0x0142 }
            r3 = 3
            r2[r3] = r0     // Catch:{ Throwable -> 0x0142 }
            r3 = 4
            java.lang.String r4 = "json"
            r2[r3] = r4     // Catch:{ Throwable -> 0x0142 }
            r3 = 5
            r2[r3] = r8     // Catch:{ Throwable -> 0x0142 }
            com.taobao.accs.utl.ALog.i(r9, r1, r2)     // Catch:{ Throwable -> 0x0142 }
            org.json.JSONObject r9 = new org.json.JSONObject     // Catch:{ Throwable -> 0x0142 }
            r9.<init>(r8)     // Catch:{ Throwable -> 0x0142 }
            java.lang.String r8 = "resultCode"
            r1 = 0
            java.lang.String r8 = com.taobao.accs.utl.JsonUtility.getString(r9, r8, r1)     // Catch:{ Throwable -> 0x0142 }
            java.lang.String r2 = "cmd"
            java.lang.String r2 = com.taobao.accs.utl.JsonUtility.getString(r9, r2, r1)     // Catch:{ Throwable -> 0x0142 }
            java.lang.String r3 = "success"
            boolean r3 = r3.equals(r8)     // Catch:{ Throwable -> 0x0142 }
            if (r3 != 0) goto L_0x0070
            if (r0 == 0) goto L_0x0062
            java.lang.String r8 = java.lang.String.valueOf(r8)     // Catch:{ Throwable -> 0x0142 }
            java.lang.String r9 = "agoo server error"
            r0.onFailure(r8, r9)     // Catch:{ Throwable -> 0x0142 }
        L_0x0062:
            java.lang.String r8 = "AgooDeviceCmd"
            boolean r6 = r8.equals(r6)
            if (r6 == 0) goto L_0x006f
            java.util.Map<java.lang.String, com.taobao.agoo.ICallback> r6 = r5.mListeners
            r6.remove(r7)
        L_0x006f:
            return
        L_0x0070:
            java.lang.String r8 = "register"
            boolean r8 = r8.equals(r2)     // Catch:{ Throwable -> 0x0142 }
            if (r8 == 0) goto L_0x00c4
            java.lang.String r8 = "deviceId"
            java.lang.String r8 = com.taobao.accs.utl.JsonUtility.getString(r9, r8, r1)     // Catch:{ Throwable -> 0x0142 }
            boolean r9 = android.text.TextUtils.isEmpty(r8)     // Catch:{ Throwable -> 0x0142 }
            if (r9 == 0) goto L_0x008e
            if (r0 == 0) goto L_0x00b6
            java.lang.String r8 = ""
            java.lang.String r9 = "agoo server error deviceid null"
            r0.onFailure(r8, r9)     // Catch:{ Throwable -> 0x0142 }
            goto L_0x00b6
        L_0x008e:
            android.content.Context r9 = com.taobao.accs.client.GlobalClientInfo.getContext()     // Catch:{ Throwable -> 0x0142 }
            org.android.agoo.common.Config.a(r9, r8)     // Catch:{ Throwable -> 0x0142 }
            com.taobao.agoo.control.AgooBindCache r9 = mAgooBindCache     // Catch:{ Throwable -> 0x0142 }
            android.content.Context r1 = com.taobao.accs.client.GlobalClientInfo.getContext()     // Catch:{ Throwable -> 0x0142 }
            java.lang.String r1 = r1.getPackageName()     // Catch:{ Throwable -> 0x0142 }
            r9.onAgooRegister(r1)     // Catch:{ Throwable -> 0x0142 }
            if (r0 == 0) goto L_0x00b6
            boolean r9 = r0 instanceof com.taobao.agoo.IRegister     // Catch:{ Throwable -> 0x0142 }
            if (r9 == 0) goto L_0x00b6
            java.lang.String r9 = "Agoo_AppStore"
            android.content.Context r1 = com.taobao.accs.client.GlobalClientInfo.getContext()     // Catch:{ Throwable -> 0x0142 }
            com.taobao.accs.utl.UtilityImpl.saveUtdid(r9, r1)     // Catch:{ Throwable -> 0x0142 }
            com.taobao.agoo.IRegister r0 = (com.taobao.agoo.IRegister) r0     // Catch:{ Throwable -> 0x0142 }
            r0.onSuccess(r8)     // Catch:{ Throwable -> 0x0142 }
        L_0x00b6:
            java.lang.String r8 = "AgooDeviceCmd"
            boolean r6 = r8.equals(r6)
            if (r6 == 0) goto L_0x00c3
            java.util.Map<java.lang.String, com.taobao.agoo.ICallback> r6 = r5.mListeners
            r6.remove(r7)
        L_0x00c3:
            return
        L_0x00c4:
            java.lang.String r8 = "setAlias"
            boolean r8 = r8.equals(r2)     // Catch:{ Throwable -> 0x0142 }
            if (r8 == 0) goto L_0x00dd
            r5.handleSetAlias(r9, r0)     // Catch:{ Throwable -> 0x0142 }
            java.lang.String r8 = "AgooDeviceCmd"
            boolean r6 = r8.equals(r6)
            if (r6 == 0) goto L_0x00dc
            java.util.Map<java.lang.String, com.taobao.agoo.ICallback> r6 = r5.mListeners
            r6.remove(r7)
        L_0x00dc:
            return
        L_0x00dd:
            java.lang.String r8 = "removeAlias"
            boolean r8 = r8.equals(r2)     // Catch:{ Throwable -> 0x0142 }
            if (r8 == 0) goto L_0x0104
            android.content.Context r8 = com.taobao.accs.client.GlobalClientInfo.getContext()     // Catch:{ Throwable -> 0x0142 }
            org.android.agoo.common.Config.b(r8, r1)     // Catch:{ Throwable -> 0x0142 }
            if (r0 == 0) goto L_0x00f1
            r0.onSuccess()     // Catch:{ Throwable -> 0x0142 }
        L_0x00f1:
            com.taobao.agoo.control.AgooBindCache r8 = mAgooBindCache     // Catch:{ Throwable -> 0x0142 }
            r8.onAgooAliasUnBind()     // Catch:{ Throwable -> 0x0142 }
            java.lang.String r8 = "AgooDeviceCmd"
            boolean r6 = r8.equals(r6)
            if (r6 == 0) goto L_0x0103
            java.util.Map<java.lang.String, com.taobao.agoo.ICallback> r6 = r5.mListeners
            r6.remove(r7)
        L_0x0103:
            return
        L_0x0104:
            java.lang.String r8 = "enablePush"
            boolean r8 = r8.equals(r2)     // Catch:{ Throwable -> 0x0142 }
            if (r8 != 0) goto L_0x0114
            java.lang.String r8 = "disablePush"
            boolean r8 = r8.equals(r2)     // Catch:{ Throwable -> 0x0142 }
            if (r8 == 0) goto L_0x0132
        L_0x0114:
            if (r0 == 0) goto L_0x0119
            r0.onSuccess()     // Catch:{ Throwable -> 0x0142 }
        L_0x0119:
            java.lang.String r8 = "AgooDeviceCmd"
            boolean r6 = r8.equals(r6)
            if (r6 == 0) goto L_0x0126
            java.util.Map<java.lang.String, com.taobao.agoo.ICallback> r6 = r5.mListeners
            r6.remove(r7)
        L_0x0126:
            return
        L_0x0127:
            if (r0 == 0) goto L_0x0132
            java.lang.String r8 = java.lang.String.valueOf(r8)     // Catch:{ Throwable -> 0x0142 }
            java.lang.String r9 = "accs channel error"
            r0.onFailure(r8, r9)     // Catch:{ Throwable -> 0x0142 }
        L_0x0132:
            java.lang.String r8 = "AgooDeviceCmd"
            boolean r6 = r8.equals(r6)
            if (r6 == 0) goto L_0x0155
        L_0x013a:
            java.util.Map<java.lang.String, com.taobao.agoo.ICallback> r6 = r5.mListeners
            r6.remove(r7)
            return
        L_0x0140:
            r8 = move-exception
            goto L_0x0156
        L_0x0142:
            r8 = move-exception
            java.lang.String r9 = "RequestListener"
            java.lang.String r0 = "onResponse"
            java.lang.Object[] r10 = new java.lang.Object[r10]     // Catch:{ all -> 0x0140 }
            com.taobao.accs.utl.ALog.e(r9, r0, r8, r10)     // Catch:{ all -> 0x0140 }
            java.lang.String r8 = "AgooDeviceCmd"
            boolean r6 = r8.equals(r6)
            if (r6 == 0) goto L_0x0155
            goto L_0x013a
        L_0x0155:
            return
        L_0x0156:
            java.lang.String r9 = "AgooDeviceCmd"
            boolean r6 = r9.equals(r6)
            if (r6 == 0) goto L_0x0163
            java.util.Map<java.lang.String, com.taobao.agoo.ICallback> r6 = r5.mListeners
            r6.remove(r7)
        L_0x0163:
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.taobao.agoo.control.RequestListener.onResponse(java.lang.String, java.lang.String, int, byte[], com.taobao.accs.base.TaoBaseService$ExtraInfo):void");
    }

    private void handleSetAlias(JSONObject jSONObject, ICallback iCallback) throws JSONException {
        String string = JsonUtility.getString(jSONObject, AliasDO.JSON_PUSH_USER_TOKEN, null);
        if (!TextUtils.isEmpty(string)) {
            Config.b(GlobalClientInfo.getContext(), string);
            if (iCallback != null) {
                iCallback.onSuccess();
                mAgooBindCache.onAgooAliasBind(iCallback.extra);
            }
        } else if (iCallback != null) {
            iCallback.onFailure("", "agoo server error-pushtoken null");
        }
    }
}
