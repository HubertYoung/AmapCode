package com.taobao.accs.internal;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
import com.taobao.accs.ACCSClient;
import com.taobao.accs.ACCSManager.AccsRequest;
import com.taobao.accs.AccsClientConfig;
import com.taobao.accs.ErrorCode;
import com.taobao.accs.IACCSManager;
import com.taobao.accs.IAppReceiver;
import com.taobao.accs.ILoginInfo;
import com.taobao.accs.base.AccsAbstractDataListener;
import com.taobao.accs.client.GlobalClientInfo;
import com.taobao.accs.common.Constants;
import com.taobao.accs.data.Message;
import com.taobao.accs.data.MsgDistribute;
import com.taobao.accs.net.BaseConnection;
import com.taobao.accs.net.InAppConnection;
import com.taobao.accs.utl.ALog;
import com.taobao.accs.utl.AdapterUtilityImpl;
import com.taobao.accs.utl.AppMonitorAdapter;
import com.taobao.accs.utl.BaseMonitor;
import com.taobao.accs.utl.UtilityImpl;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class ACCSManagerImpl implements IACCSManager {
    private String TAG = "ACCSMgrImpl_";
    private int baseDataId = 0;
    private String mConfigTag;
    public BaseConnection mConnection;

    public String getUserUnit() {
        return null;
    }

    public ACCSManagerImpl(Context context, String str) {
        GlobalClientInfo.mContext = context.getApplicationContext();
        this.mConnection = new InAppConnection(GlobalClientInfo.mContext, 1, str);
        this.mConfigTag = str;
        StringBuilder sb = new StringBuilder();
        sb.append(this.TAG);
        sb.append(this.mConnection.mConfigTag);
        this.TAG = sb.toString();
    }

    public void bindApp(Context context, String str, String str2, IAppReceiver iAppReceiver) {
        bindApp(context, str, "accs", str2, iAppReceiver);
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(3:41|42|43) */
    /* JADX WARNING: Code restructure failed: missing block: B:42:?, code lost:
        com.taobao.accs.utl.ALog.w(r7.TAG, "no orange sdk", new java.lang.Object[0]);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x0114, code lost:
        return;
     */
    /* JADX WARNING: Missing exception handler attribute for start block: B:41:0x010b */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x00a1 A[Catch:{ Throwable -> 0x0115 }] */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x00d6 A[Catch:{ Throwable -> 0x0115 }] */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x00e0 A[Catch:{ Throwable -> 0x0115 }] */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x00f6 A[Catch:{ Throwable -> 0x010b }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void bindApp(android.content.Context r8, java.lang.String r9, java.lang.String r10, java.lang.String r11, com.taobao.accs.IAppReceiver r12) {
        /*
            r7 = this;
            if (r8 != 0) goto L_0x0003
            return
        L_0x0003:
            java.lang.String r10 = r7.TAG
            java.lang.String r0 = "bindApp"
            r1 = 2
            java.lang.Object[] r1 = new java.lang.Object[r1]
            java.lang.String r2 = "appKey"
            r3 = 0
            r1[r3] = r2
            r2 = 1
            r1[r2] = r9
            com.taobao.accs.utl.ALog.i(r10, r0, r1)
            java.lang.String r10 = r8.getPackageName()
            com.taobao.accs.data.Message r10 = com.taobao.accs.data.Message.buildParameterError(r10, r2)
            boolean r0 = com.taobao.accs.utl.UtilityImpl.getFocusDisableStatus(r8)
            if (r0 == 0) goto L_0x002f
            java.lang.String r0 = r7.TAG
            java.lang.String r1 = "accs disabled, try enable"
            java.lang.Object[] r4 = new java.lang.Object[r3]
            com.taobao.accs.utl.ALog.e(r0, r1, r4)
            com.taobao.accs.utl.UtilityImpl.focusEnableService(r8)
        L_0x002f:
            com.taobao.accs.net.BaseConnection r0 = r7.mConnection
            boolean r0 = r0.isSecurityOff()
            if (r0 == 0) goto L_0x004d
            com.taobao.accs.net.BaseConnection r0 = r7.mConnection
            com.taobao.accs.AccsClientConfig r0 = r0.mConfig
            java.lang.String r0 = r0.getAppSecret()
            boolean r0 = android.text.TextUtils.isEmpty(r0)
            if (r0 == 0) goto L_0x004d
            com.taobao.accs.net.BaseConnection r8 = r7.mConnection
            r9 = -15
            r8.onResult(r10, r9)
            return
        L_0x004d:
            boolean r0 = android.text.TextUtils.isEmpty(r9)
            if (r0 == 0) goto L_0x005b
            com.taobao.accs.net.BaseConnection r8 = r7.mConnection
            r9 = -14
            r8.onResult(r10, r9)
            return
        L_0x005b:
            com.taobao.accs.net.BaseConnection r10 = r7.mConnection
            r10.mTtid = r11
            com.taobao.accs.net.BaseConnection r10 = r7.mConnection
            r10.mAppkey = r9
            com.taobao.accs.net.BaseConnection r10 = r7.mConnection
            com.taobao.accs.AccsClientConfig r10 = r10.mConfig
            java.lang.String r10 = r10.getAppSecret()
            com.taobao.accs.utl.UtilityImpl.saveAppKey(r8, r9, r10)
            if (r12 == 0) goto L_0x0079
            com.taobao.accs.client.GlobalClientInfo r10 = com.taobao.accs.client.GlobalClientInfo.getInstance(r8)
            java.lang.String r0 = r7.mConfigTag
            r10.setAppReceiver(r0, r12)
        L_0x0079:
            com.taobao.accs.utl.UtilityImpl.enableService(r8)
            android.content.Intent r10 = r7.getIntent(r8, r2)
            if (r10 != 0) goto L_0x0083
            return
        L_0x0083:
            com.taobao.accs.client.GlobalClientInfo r12 = com.taobao.accs.client.GlobalClientInfo.getInstance(r8)     // Catch:{ Throwable -> 0x0115 }
            android.content.pm.PackageInfo r12 = r12.getPackageInfo()     // Catch:{ Throwable -> 0x0115 }
            java.lang.String r12 = r12.versionName     // Catch:{ Throwable -> 0x0115 }
            boolean r0 = com.taobao.accs.utl.UtilityImpl.appVersionChanged(r8)     // Catch:{ Throwable -> 0x0115 }
            if (r0 != 0) goto L_0x009e
            java.lang.String r0 = "ACCS_SDK"
            boolean r0 = com.taobao.accs.utl.UtilityImpl.utdidChanged(r0, r8)     // Catch:{ Throwable -> 0x0115 }
            if (r0 == 0) goto L_0x009c
            goto L_0x009e
        L_0x009c:
            r0 = 0
            goto L_0x009f
        L_0x009e:
            r0 = 1
        L_0x009f:
            if (r0 == 0) goto L_0x00b3
            java.lang.String r1 = r7.TAG     // Catch:{ Throwable -> 0x0115 }
            java.lang.String r4 = "bindApp"
            java.lang.Object[] r5 = new java.lang.Object[r2]     // Catch:{ Throwable -> 0x0115 }
            java.lang.String r6 = "need force bind"
            r5[r3] = r6     // Catch:{ Throwable -> 0x0115 }
            com.taobao.accs.utl.ALog.d(r1, r4, r5)     // Catch:{ Throwable -> 0x0115 }
            java.lang.String r1 = "fouce_bind"
            r10.putExtra(r1, r2)     // Catch:{ Throwable -> 0x0115 }
        L_0x00b3:
            java.lang.String r1 = "appKey"
            r10.putExtra(r1, r9)     // Catch:{ Throwable -> 0x0115 }
            java.lang.String r9 = "ttid"
            r10.putExtra(r9, r11)     // Catch:{ Throwable -> 0x0115 }
            java.lang.String r9 = "appVersion"
            r10.putExtra(r9, r12)     // Catch:{ Throwable -> 0x0115 }
            java.lang.String r9 = "app_sercet"
            com.taobao.accs.net.BaseConnection r11 = r7.mConnection     // Catch:{ Throwable -> 0x0115 }
            com.taobao.accs.AccsClientConfig r11 = r11.mConfig     // Catch:{ Throwable -> 0x0115 }
            java.lang.String r11 = r11.getAppSecret()     // Catch:{ Throwable -> 0x0115 }
            r10.putExtra(r9, r11)     // Catch:{ Throwable -> 0x0115 }
            boolean r9 = com.taobao.accs.utl.UtilityImpl.isMainProcess(r8)     // Catch:{ Throwable -> 0x0115 }
            if (r9 == 0) goto L_0x00e0
            com.taobao.accs.net.BaseConnection r9 = r7.mConnection     // Catch:{ Throwable -> 0x0115 }
            com.taobao.accs.data.Message r9 = com.taobao.accs.data.Message.buildBindApp(r9, r8, r10)     // Catch:{ Throwable -> 0x0115 }
            r7.sendControlMessage(r8, r9, r2, r0)     // Catch:{ Throwable -> 0x0115 }
            goto L_0x00e9
        L_0x00e0:
            java.lang.String r9 = r7.TAG     // Catch:{ Throwable -> 0x0115 }
            java.lang.String r10 = "bindApp only allow in main process"
            java.lang.Object[] r11 = new java.lang.Object[r3]     // Catch:{ Throwable -> 0x0115 }
            com.taobao.accs.utl.ALog.w(r9, r10, r11)     // Catch:{ Throwable -> 0x0115 }
        L_0x00e9:
            com.taobao.accs.net.BaseConnection r9 = r7.mConnection     // Catch:{ Throwable -> 0x0115 }
            android.content.Context r8 = r8.getApplicationContext()     // Catch:{ Throwable -> 0x0115 }
            r9.startChannelService(r8)     // Catch:{ Throwable -> 0x0115 }
            boolean r8 = com.taobao.accs.utl.OrangeAdapter.mOrangeValid     // Catch:{ Throwable -> 0x010b }
            if (r8 == 0) goto L_0x010a
            java.lang.String r8 = "accs"
            java.lang.String[] r8 = new java.lang.String[]{r8}     // Catch:{ Throwable -> 0x010b }
            com.taobao.accs.utl.OrangeAdapter$OrangeListener r9 = new com.taobao.accs.utl.OrangeAdapter$OrangeListener     // Catch:{ Throwable -> 0x010b }
            r9.<init>()     // Catch:{ Throwable -> 0x010b }
            com.taobao.accs.utl.OrangeAdapter.registerListener(r8, r9)     // Catch:{ Throwable -> 0x010b }
            com.taobao.accs.utl.OrangeAdapter.checkAccsEnabled()     // Catch:{ Throwable -> 0x010b }
            com.taobao.accs.utl.OrangeAdapter.getConfigForAccs()     // Catch:{ Throwable -> 0x010b }
        L_0x010a:
            return
        L_0x010b:
            java.lang.String r8 = r7.TAG     // Catch:{ Throwable -> 0x0115 }
            java.lang.String r9 = "no orange sdk"
            java.lang.Object[] r10 = new java.lang.Object[r3]     // Catch:{ Throwable -> 0x0115 }
            com.taobao.accs.utl.ALog.w(r8, r9, r10)     // Catch:{ Throwable -> 0x0115 }
            return
        L_0x0115:
            r8 = move-exception
            java.lang.String r9 = r7.TAG
            java.lang.String r10 = "bindApp exception"
            java.lang.Object[] r11 = new java.lang.Object[r3]
            com.taobao.accs.utl.ALog.e(r9, r10, r8, r11)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.taobao.accs.internal.ACCSManagerImpl.bindApp(android.content.Context, java.lang.String, java.lang.String, java.lang.String, com.taobao.accs.IAppReceiver):void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:13:0x00a4, code lost:
        r8 = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x00cd, code lost:
        r8 = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x00ce, code lost:
        if (r8 == false) goto L_0x00e8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x00d0, code lost:
        com.taobao.accs.utl.ALog.i(r7.TAG, "sendControlMessage", "command", java.lang.Integer.valueOf(r10));
        r7.mConnection.send(r9, true);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x00e8, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void sendControlMessage(android.content.Context r8, com.taobao.accs.data.Message r9, int r10, boolean r11) {
        /*
            r7 = this;
            com.taobao.accs.net.BaseConnection r0 = r7.mConnection
            r0.start()
            r0 = 0
            if (r9 != 0) goto L_0x0020
            java.lang.String r9 = r7.TAG
            java.lang.String r11 = "message is null"
            java.lang.Object[] r0 = new java.lang.Object[r0]
            com.taobao.accs.utl.ALog.e(r9, r11, r0)
            java.lang.String r8 = r8.getPackageName()
            com.taobao.accs.data.Message r8 = com.taobao.accs.data.Message.buildParameterError(r8, r10)
            com.taobao.accs.net.BaseConnection r9 = r7.mConnection
            r10 = -2
            r9.onResult(r8, r10)
            return
        L_0x0020:
            r8 = 200(0xc8, float:2.8E-43)
            r1 = 2
            r2 = 1
            switch(r10) {
                case 1: goto L_0x00a6;
                case 2: goto L_0x0073;
                case 3: goto L_0x0029;
                default: goto L_0x0027;
            }
        L_0x0027:
            goto L_0x00cd
        L_0x0029:
            com.taobao.accs.net.BaseConnection r3 = r7.mConnection
            com.taobao.accs.client.ClientManager r3 = r3.getClientManager()
            java.lang.String r4 = r9.getPackageName()
            java.lang.String r5 = r9.userinfo
            boolean r3 = r3.isUserBinded(r4, r5)
            if (r3 == 0) goto L_0x00cd
            if (r11 != 0) goto L_0x00cd
            java.lang.String r3 = r7.TAG
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = r9.getPackageName()
            r4.append(r5)
            java.lang.String r5 = "/"
            r4.append(r5)
            java.lang.String r5 = r9.userinfo
            r4.append(r5)
            java.lang.String r5 = " isUserBinded"
            r4.append(r5)
            java.lang.String r4 = r4.toString()
            java.lang.Object[] r5 = new java.lang.Object[r1]
            java.lang.String r6 = "isForceBind"
            r5[r0] = r6
            java.lang.Boolean r11 = java.lang.Boolean.valueOf(r11)
            r5[r2] = r11
            com.taobao.accs.utl.ALog.i(r3, r4, r5)
            com.taobao.accs.net.BaseConnection r11 = r7.mConnection
            r11.onResult(r9, r8)
            goto L_0x00a4
        L_0x0073:
            com.taobao.accs.net.BaseConnection r11 = r7.mConnection
            com.taobao.accs.client.ClientManager r11 = r11.getClientManager()
            java.lang.String r3 = r9.getPackageName()
            boolean r11 = r11.isAppUnbinded(r3)
            if (r11 == 0) goto L_0x00cd
            java.lang.String r11 = r7.TAG
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = r9.getPackageName()
            r3.append(r4)
            java.lang.String r4 = " isAppUnbinded"
            r3.append(r4)
            java.lang.String r3 = r3.toString()
            java.lang.Object[] r4 = new java.lang.Object[r0]
            com.taobao.accs.utl.ALog.i(r11, r3, r4)
            com.taobao.accs.net.BaseConnection r11 = r7.mConnection
            r11.onResult(r9, r8)
        L_0x00a4:
            r8 = 0
            goto L_0x00ce
        L_0x00a6:
            java.lang.String r3 = r9.getPackageName()
            com.taobao.accs.net.BaseConnection r4 = r7.mConnection
            com.taobao.accs.client.ClientManager r4 = r4.getClientManager()
            boolean r4 = r4.isAppBinded(r3)
            if (r4 == 0) goto L_0x00cd
            if (r11 != 0) goto L_0x00cd
            java.lang.String r11 = r7.TAG
            java.lang.String r4 = "isAppBinded"
            java.lang.Object[] r5 = new java.lang.Object[r1]
            java.lang.String r6 = "package"
            r5[r0] = r6
            r5[r2] = r3
            com.taobao.accs.utl.ALog.i(r11, r4, r5)
            com.taobao.accs.net.BaseConnection r11 = r7.mConnection
            r11.onResult(r9, r8)
            goto L_0x00a4
        L_0x00cd:
            r8 = 1
        L_0x00ce:
            if (r8 == 0) goto L_0x00e8
            java.lang.String r8 = r7.TAG
            java.lang.String r11 = "sendControlMessage"
            java.lang.Object[] r1 = new java.lang.Object[r1]
            java.lang.String r3 = "command"
            r1[r0] = r3
            java.lang.Integer r10 = java.lang.Integer.valueOf(r10)
            r1[r2] = r10
            com.taobao.accs.utl.ALog.i(r8, r11, r1)
            com.taobao.accs.net.BaseConnection r8 = r7.mConnection
            r8.send(r9, r2)
        L_0x00e8:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.taobao.accs.internal.ACCSManagerImpl.sendControlMessage(android.content.Context, com.taobao.accs.data.Message, int, boolean):void");
    }

    public void unbindApp(Context context) {
        String str = this.TAG;
        StringBuilder sb = new StringBuilder("unbindApp");
        sb.append(UtilityImpl.getStackMsg(new Exception()));
        ALog.e(str, sb.toString(), new Object[0]);
        if (!UtilityImpl.getFocusDisableStatus(context)) {
            Intent intent = getIntent(context, 2);
            if (intent == null) {
                sendAppNotBind(context, 2, null, null);
                return;
            }
            if (UtilityImpl.isMainProcess(context)) {
                sendControlMessage(context, Message.buildUnbindApp(this.mConnection, context, intent), 2, false);
            }
        }
    }

    public void bindUser(Context context, String str) {
        bindUser(context, str, false);
    }

    public void bindUser(Context context, String str, boolean z) {
        try {
            ALog.i(this.TAG, "bindUser", "userId", str);
            if (UtilityImpl.getFocusDisableStatus(context)) {
                ALog.e(this.TAG, "accs disabled", new Object[0]);
                return;
            }
            Intent intent = getIntent(context, 3);
            if (intent == null) {
                ALog.e(this.TAG, "intent null", new Object[0]);
                sendAppNotBind(context, 3, null, null);
                return;
            }
            String appkey = this.mConnection.getAppkey();
            if (TextUtils.isEmpty(appkey)) {
                ALog.e(this.TAG, "appKey null", new Object[0]);
                return;
            }
            if (UtilityImpl.appVersionChanged(context) || z) {
                ALog.i(this.TAG, "force bind User", new Object[0]);
                intent.putExtra(Constants.KEY_FOUCE_BIND, true);
                z = true;
            }
            intent.putExtra("appKey", appkey);
            intent.putExtra("userInfo", str);
            if (UtilityImpl.isMainProcess(context)) {
                sendControlMessage(context, Message.buildBindUser(this.mConnection, context, intent), 3, z);
            }
            this.mConnection.startChannelService(context.getApplicationContext());
        } catch (Throwable th) {
            ALog.e(this.TAG, "bindUser", th, new Object[0]);
        }
    }

    public void unbindUser(Context context) {
        if (!UtilityImpl.getFocusDisableStatus(context) && !UtilityImpl.getFocusDisableStatus(context)) {
            Intent intent = getIntent(context, 4);
            if (intent == null) {
                sendAppNotBind(context, 4, null, null);
                return;
            }
            String appkey = this.mConnection.getAppkey();
            if (!TextUtils.isEmpty(appkey)) {
                intent.putExtra("appKey", appkey);
                if (UtilityImpl.isMainProcess(context)) {
                    sendControlMessage(context, Message.buildUnbindUser(this.mConnection, context, intent), 4, false);
                }
            }
        }
    }

    public void bindService(Context context, String str) {
        if (!UtilityImpl.getFocusDisableStatus(context) && !UtilityImpl.getFocusDisableStatus(context)) {
            Intent intent = getIntent(context, 5);
            if (intent == null) {
                sendAppNotBind(context, 5, str, null);
                return;
            }
            String appkey = this.mConnection.getAppkey();
            if (!TextUtils.isEmpty(appkey)) {
                intent.putExtra("appKey", appkey);
                intent.putExtra("serviceId", str);
                if (UtilityImpl.isMainProcess(context)) {
                    sendControlMessage(context, Message.buildBindService(this.mConnection, context, intent), 5, false);
                }
                this.mConnection.startChannelService(context.getApplicationContext());
            }
        }
    }

    public void unbindService(Context context, String str) {
        if (!UtilityImpl.getFocusDisableStatus(context)) {
            Intent intent = getIntent(context, 6);
            if (intent == null) {
                sendAppNotBind(context, 6, str, null);
                return;
            }
            String appkey = this.mConnection.getAppkey();
            if (!TextUtils.isEmpty(appkey)) {
                intent.putExtra("appKey", appkey);
                intent.putExtra("serviceId", str);
                if (UtilityImpl.isMainProcess(context)) {
                    sendControlMessage(context, Message.buildUnbindService(this.mConnection, context, intent), 6, false);
                }
            }
        }
    }

    public String sendData(Context context, String str, String str2, byte[] bArr, String str3) {
        return sendData(context, str, str2, bArr, str3, null);
    }

    public String sendData(Context context, String str, String str2, byte[] bArr, String str3, String str4) {
        return sendData(context, str, str2, bArr, str3, str4, null);
    }

    public String sendData(Context context, String str, String str2, byte[] bArr, String str3, String str4, URL url) {
        AccsRequest accsRequest = new AccsRequest(str, str2, bArr, str3, str4, url, null);
        return sendData(context, accsRequest);
    }

    public String sendData(Context context, AccsRequest accsRequest) {
        try {
            boolean focusDisableStatus = UtilityImpl.getFocusDisableStatus(context);
            if (!UtilityImpl.isMainProcess(context)) {
                ALog.e(this.TAG, "sendData not in mainprocess", new Object[0]);
                return null;
            }
            if (!focusDisableStatus) {
                if (accsRequest != null) {
                    if (TextUtils.isEmpty(accsRequest.dataId)) {
                        synchronized (ACCSManagerImpl.class) {
                            this.baseDataId++;
                            StringBuilder sb = new StringBuilder();
                            sb.append(this.baseDataId);
                            accsRequest.dataId = sb.toString();
                        }
                    }
                    String appkey = this.mConnection.getAppkey();
                    if (TextUtils.isEmpty(appkey)) {
                        AppMonitorAdapter.commitAlarmFail("accs", BaseMonitor.ALARM_POINT_REQ_ERROR, accsRequest.serviceId, "1", "data appkey null");
                        ALog.e(this.TAG, "sendData appkey null", Constants.KEY_DATA_ID, accsRequest.dataId);
                        return null;
                    }
                    this.mConnection.start();
                    Message buildSendData = Message.buildSendData(this.mConnection, context, context.getPackageName(), appkey, accsRequest);
                    if (buildSendData.getNetPermanceMonitor() != null) {
                        buildSendData.getNetPermanceMonitor().onSend();
                    }
                    this.mConnection.send(buildSendData, true);
                    return accsRequest.dataId;
                }
            }
            if (focusDisableStatus) {
                AppMonitorAdapter.commitAlarmFail("accs", BaseMonitor.ALARM_POINT_REQ_ERROR, accsRequest.serviceId, "1", "accs disable");
            } else {
                AppMonitorAdapter.commitAlarmFail("accs", BaseMonitor.ALARM_POINT_REQ_ERROR, "", "1", "data null");
            }
            ALog.e(this.TAG, "sendData dataInfo null or disable:".concat(String.valueOf(focusDisableStatus)), new Object[0]);
            return null;
        } catch (Throwable th) {
            String str = accsRequest.serviceId;
            StringBuilder sb2 = new StringBuilder("data ");
            sb2.append(th.toString());
            AppMonitorAdapter.commitAlarmFail("accs", BaseMonitor.ALARM_POINT_REQ_ERROR, str, "1", sb2.toString());
            ALog.e(this.TAG, "sendData", th, "dataid", accsRequest.dataId);
        }
    }

    public String sendRequest(Context context, String str, String str2, byte[] bArr, String str3, String str4) {
        return sendRequest(context, str, str2, bArr, str3, str4, null);
    }

    public String sendRequest(Context context, String str, String str2, byte[] bArr, String str3, String str4, URL url) {
        AccsRequest accsRequest = new AccsRequest(str, str2, bArr, str3, str4, url, null);
        return sendRequest(context, accsRequest);
    }

    public String sendRequest(Context context, AccsRequest accsRequest, String str, boolean z) {
        if (accsRequest == null) {
            try {
                ALog.e(this.TAG, "sendRequest request null", new Object[0]);
                AppMonitorAdapter.commitAlarmFail("accs", BaseMonitor.ALARM_POINT_REQ_ERROR, null, "1", "request null");
                return null;
            } catch (Throwable th) {
                if (accsRequest != null) {
                    String str2 = accsRequest.serviceId;
                    StringBuilder sb = new StringBuilder("request ");
                    sb.append(th.toString());
                    AppMonitorAdapter.commitAlarmFail("accs", BaseMonitor.ALARM_POINT_REQ_ERROR, str2, "1", sb.toString());
                    ALog.e(this.TAG, "sendRequest", th, Constants.KEY_DATA_ID, accsRequest.dataId);
                }
            }
        } else if (!UtilityImpl.isMainProcess(context)) {
            ALog.e(this.TAG, "sendRequest not in mainprocess", new Object[0]);
            return null;
        } else if (UtilityImpl.getFocusDisableStatus(context)) {
            ALog.e(this.TAG, "sendRequest disable", new Object[0]);
            AppMonitorAdapter.commitAlarmFail("accs", BaseMonitor.ALARM_POINT_REQ_ERROR, accsRequest.serviceId, "1", "accs disable");
            return null;
        } else {
            if (TextUtils.isEmpty(accsRequest.dataId)) {
                synchronized (ACCSManagerImpl.class) {
                    this.baseDataId++;
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(this.baseDataId);
                    accsRequest.dataId = sb2.toString();
                }
            }
            String appkey = this.mConnection.getAppkey();
            if (TextUtils.isEmpty(appkey)) {
                AppMonitorAdapter.commitAlarmFail("accs", BaseMonitor.ALARM_POINT_REQ_ERROR, accsRequest.serviceId, "1", "request appkey null");
                ALog.e(this.TAG, "sendRequest appkey null", Constants.KEY_DATA_ID, accsRequest.dataId);
                return null;
            }
            this.mConnection.start();
            if (str == null) {
                str = context.getPackageName();
            }
            Message buildRequest = Message.buildRequest(this.mConnection, context, str, appkey, accsRequest, z);
            if (buildRequest.getNetPermanceMonitor() != null) {
                buildRequest.getNetPermanceMonitor().onSend();
            }
            this.mConnection.send(buildRequest, true);
            return accsRequest.dataId;
        }
    }

    public String sendRequest(Context context, AccsRequest accsRequest) {
        return sendRequest(context, accsRequest, null, true);
    }

    /* JADX INFO: finally extract failed */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x00ea A[Catch:{ all -> 0x0083, Throwable -> 0x022c }] */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x014d A[Catch:{ all -> 0x0083, Throwable -> 0x022c }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.String sendPushResponse(android.content.Context r20, com.taobao.accs.ACCSManager.AccsRequest r21, com.taobao.accs.base.TaoBaseService.ExtraInfo r22) {
        /*
            r19 = this;
            r1 = r19
            r2 = r20
            r3 = r21
            r6 = 5
            r7 = 4
            r8 = 3
            r9 = 2
            r10 = 0
            r11 = 1
            r12 = 0
            if (r2 == 0) goto L_0x01ff
            if (r3 != 0) goto L_0x0013
            goto L_0x01ff
        L_0x0013:
            java.lang.String r13 = "accs"
            java.lang.String r14 = "send_fail"
            java.lang.String r15 = "push response total"
            com.taobao.accs.utl.AppMonitorAdapter.commitAlarmSuccess(r13, r14, r15)     // Catch:{ Throwable -> 0x022c }
            boolean r13 = com.taobao.accs.utl.UtilityImpl.getFocusDisableStatus(r20)     // Catch:{ Throwable -> 0x022c }
            if (r13 == 0) goto L_0x0030
            java.lang.String r2 = "accs"
            java.lang.String r4 = "send_fail"
            java.lang.String r5 = r3.serviceId     // Catch:{ Throwable -> 0x022c }
            java.lang.String r6 = "1"
            java.lang.String r7 = "sendPushResponse accs disable"
            com.taobao.accs.utl.AppMonitorAdapter.commitAlarmFail(r2, r4, r5, r6, r7)     // Catch:{ Throwable -> 0x022c }
            return r10
        L_0x0030:
            com.taobao.accs.net.BaseConnection r13 = r1.mConnection     // Catch:{ Throwable -> 0x022c }
            java.lang.String r13 = r13.getAppkey()     // Catch:{ Throwable -> 0x022c }
            boolean r14 = android.text.TextUtils.isEmpty(r13)     // Catch:{ Throwable -> 0x022c }
            if (r14 == 0) goto L_0x0061
            java.lang.String r2 = "accs"
            java.lang.String r4 = "send_fail"
            java.lang.String r5 = r3.serviceId     // Catch:{ Throwable -> 0x022c }
            java.lang.String r6 = "1"
            java.lang.String r7 = "sendPushResponse appkey null"
            com.taobao.accs.utl.AppMonitorAdapter.commitAlarmFail(r2, r4, r5, r6, r7)     // Catch:{ Throwable -> 0x022c }
            java.lang.String r2 = r1.TAG     // Catch:{ Throwable -> 0x022c }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x022c }
            java.lang.String r5 = "sendPushResponse appkey null dataid:"
            r4.<init>(r5)     // Catch:{ Throwable -> 0x022c }
            java.lang.String r5 = r3.dataId     // Catch:{ Throwable -> 0x022c }
            r4.append(r5)     // Catch:{ Throwable -> 0x022c }
            java.lang.String r4 = r4.toString()     // Catch:{ Throwable -> 0x022c }
            java.lang.Object[] r5 = new java.lang.Object[r12]     // Catch:{ Throwable -> 0x022c }
            com.taobao.accs.utl.ALog.e(r2, r4, r5)     // Catch:{ Throwable -> 0x022c }
            return r10
        L_0x0061:
            java.lang.String r14 = r3.dataId     // Catch:{ Throwable -> 0x022c }
            boolean r14 = android.text.TextUtils.isEmpty(r14)     // Catch:{ Throwable -> 0x022c }
            if (r14 == 0) goto L_0x0087
            java.lang.Class<com.taobao.accs.internal.ACCSManagerImpl> r14 = com.taobao.accs.internal.ACCSManagerImpl.class
            monitor-enter(r14)     // Catch:{ Throwable -> 0x022c }
            int r15 = r1.baseDataId     // Catch:{ all -> 0x0083 }
            int r15 = r15 + r11
            r1.baseDataId = r15     // Catch:{ all -> 0x0083 }
            java.lang.StringBuilder r15 = new java.lang.StringBuilder     // Catch:{ all -> 0x0083 }
            r15.<init>()     // Catch:{ all -> 0x0083 }
            int r5 = r1.baseDataId     // Catch:{ all -> 0x0083 }
            r15.append(r5)     // Catch:{ all -> 0x0083 }
            java.lang.String r5 = r15.toString()     // Catch:{ all -> 0x0083 }
            r3.dataId = r5     // Catch:{ all -> 0x0083 }
            monitor-exit(r14)     // Catch:{ all -> 0x0083 }
            goto L_0x0087
        L_0x0083:
            r0 = move-exception
            r2 = r0
            monitor-exit(r14)     // Catch:{ all -> 0x0083 }
            throw r2     // Catch:{ Throwable -> 0x022c }
        L_0x0087:
            if (r22 != 0) goto L_0x008f
            com.taobao.accs.base.TaoBaseService$ExtraInfo r4 = new com.taobao.accs.base.TaoBaseService$ExtraInfo     // Catch:{ Throwable -> 0x022c }
            r4.<init>()     // Catch:{ Throwable -> 0x022c }
            goto L_0x0091
        L_0x008f:
            r4 = r22
        L_0x0091:
            r3.host = r10     // Catch:{ Throwable -> 0x022c }
            java.lang.String r5 = r20.getPackageName()     // Catch:{ Throwable -> 0x022c }
            r4.fromPackage = r5     // Catch:{ Throwable -> 0x022c }
            int r5 = r4.connType     // Catch:{ Throwable -> 0x022c }
            if (r5 == 0) goto L_0x00a4
            java.lang.String r5 = r4.fromHost     // Catch:{ Throwable -> 0x022c }
            if (r5 != 0) goto L_0x00a2
            goto L_0x00a4
        L_0x00a2:
            r5 = 1
            goto L_0x00b8
        L_0x00a4:
            r4.connType = r12     // Catch:{ Throwable -> 0x022c }
            java.lang.String r5 = r1.TAG     // Catch:{ Throwable -> 0x022c }
            java.lang.String r14 = "pushresponse use channel"
            java.lang.Object[] r15 = new java.lang.Object[r9]     // Catch:{ Throwable -> 0x022c }
            java.lang.String r17 = "host"
            r15[r12] = r17     // Catch:{ Throwable -> 0x022c }
            java.lang.String r10 = r4.fromHost     // Catch:{ Throwable -> 0x022c }
            r15[r11] = r10     // Catch:{ Throwable -> 0x022c }
            com.taobao.accs.utl.ALog.w(r5, r14, r15)     // Catch:{ Throwable -> 0x022c }
            r5 = 0
        L_0x00b8:
            java.lang.String r10 = r1.TAG     // Catch:{ Throwable -> 0x022c }
            java.lang.String r14 = "sendPushResponse"
            r15 = 8
            java.lang.Object[] r15 = new java.lang.Object[r15]     // Catch:{ Throwable -> 0x022c }
            java.lang.String r17 = "sendbyInapp"
            r15[r12] = r17     // Catch:{ Throwable -> 0x022c }
            java.lang.Boolean r17 = java.lang.Boolean.valueOf(r5)     // Catch:{ Throwable -> 0x022c }
            r15[r11] = r17     // Catch:{ Throwable -> 0x022c }
            java.lang.String r17 = "host"
            r15[r9] = r17     // Catch:{ Throwable -> 0x022c }
            java.lang.String r11 = r4.fromHost     // Catch:{ Throwable -> 0x022c }
            r15[r8] = r11     // Catch:{ Throwable -> 0x022c }
            java.lang.String r11 = "pkg"
            r15[r7] = r11     // Catch:{ Throwable -> 0x022c }
            java.lang.String r11 = r4.fromPackage     // Catch:{ Throwable -> 0x022c }
            r15[r6] = r11     // Catch:{ Throwable -> 0x022c }
            java.lang.String r11 = "dataId"
            r16 = 6
            r15[r16] = r11     // Catch:{ Throwable -> 0x022c }
            r11 = 7
            java.lang.String r6 = r3.dataId     // Catch:{ Throwable -> 0x022c }
            r15[r11] = r6     // Catch:{ Throwable -> 0x022c }
            com.taobao.accs.utl.ALog.i(r10, r14, r15)     // Catch:{ Throwable -> 0x022c }
            if (r5 == 0) goto L_0x014d
            java.lang.String r5 = r1.TAG     // Catch:{ Throwable -> 0x022c }
            java.lang.String r6 = "sendPushResponse inapp by"
            java.lang.Object[] r7 = new java.lang.Object[r9]     // Catch:{ Throwable -> 0x022c }
            java.lang.String r8 = "app"
            r7[r12] = r8     // Catch:{ Throwable -> 0x022c }
            java.lang.String r8 = r4.fromPackage     // Catch:{ Throwable -> 0x022c }
            r9 = 1
            r7[r9] = r8     // Catch:{ Throwable -> 0x022c }
            com.taobao.accs.utl.ALog.i(r5, r6, r7)     // Catch:{ Throwable -> 0x022c }
            java.net.URL r5 = new java.net.URL     // Catch:{ Throwable -> 0x022c }
            java.lang.String r6 = r4.fromHost     // Catch:{ Throwable -> 0x022c }
            r5.<init>(r6)     // Catch:{ Throwable -> 0x022c }
            r3.host = r5     // Catch:{ Throwable -> 0x022c }
            java.lang.String r5 = r20.getPackageName()     // Catch:{ Throwable -> 0x022c }
            java.lang.String r6 = r4.fromPackage     // Catch:{ Throwable -> 0x022c }
            boolean r5 = r5.equals(r6)     // Catch:{ Throwable -> 0x022c }
            if (r5 == 0) goto L_0x0120
            boolean r5 = com.taobao.accs.utl.UtilityImpl.isMainProcess(r20)     // Catch:{ Throwable -> 0x022c }
            if (r5 == 0) goto L_0x0120
            java.lang.String r4 = r20.getPackageName()     // Catch:{ Throwable -> 0x022c }
            r1.sendRequest(r2, r3, r4, r12)     // Catch:{ Throwable -> 0x022c }
            goto L_0x0262
        L_0x0120:
            android.content.Intent r5 = new android.content.Intent     // Catch:{ Throwable -> 0x022c }
            java.lang.String r6 = "com.taobao.accs.intent.action.SEND"
            r5.<init>(r6)     // Catch:{ Throwable -> 0x022c }
            java.lang.String r4 = r4.fromPackage     // Catch:{ Throwable -> 0x022c }
            java.lang.String r6 = "com.taobao.accs.data.MsgDistributeService"
            r5.setClassName(r4, r6)     // Catch:{ Throwable -> 0x022c }
            java.lang.String r4 = "packageName"
            java.lang.String r6 = r20.getPackageName()     // Catch:{ Throwable -> 0x022c }
            r5.putExtra(r4, r6)     // Catch:{ Throwable -> 0x022c }
            java.lang.String r4 = "reqdata"
            r5.putExtra(r4, r3)     // Catch:{ Throwable -> 0x022c }
            java.lang.String r4 = "appKey"
            r5.putExtra(r4, r13)     // Catch:{ Throwable -> 0x022c }
            java.lang.String r4 = "configTag"
            java.lang.String r6 = r1.mConfigTag     // Catch:{ Throwable -> 0x022c }
            r5.putExtra(r4, r6)     // Catch:{ Throwable -> 0x022c }
            com.taobao.accs.dispatch.IntentDispatch.dispatchIntent(r2, r5, r12)     // Catch:{ Throwable -> 0x022c }
            goto L_0x0262
        L_0x014d:
            r5 = 100
            android.content.Intent r6 = r1.getIntent(r2, r5)     // Catch:{ Throwable -> 0x022c }
            if (r6 != 0) goto L_0x0189
            java.lang.String r6 = "accs"
            java.lang.String r10 = "send_fail"
            java.lang.String r11 = r3.serviceId     // Catch:{ Throwable -> 0x022c }
            java.lang.String r13 = "1"
            java.lang.String r14 = "push response intent null"
            com.taobao.accs.utl.AppMonitorAdapter.commitAlarmFail(r6, r10, r11, r13, r14)     // Catch:{ Throwable -> 0x022c }
            java.lang.String r6 = r3.serviceId     // Catch:{ Throwable -> 0x022c }
            java.lang.String r10 = r3.dataId     // Catch:{ Throwable -> 0x022c }
            r1.sendAppNotBind(r2, r5, r6, r10)     // Catch:{ Throwable -> 0x022c }
            java.lang.String r5 = r1.TAG     // Catch:{ Throwable -> 0x022c }
            java.lang.String r6 = "sendPushResponse input null"
            r10 = 6
            java.lang.Object[] r10 = new java.lang.Object[r10]     // Catch:{ Throwable -> 0x022c }
            java.lang.String r11 = "context"
            r10[r12] = r11     // Catch:{ Throwable -> 0x022c }
            r11 = 1
            r10[r11] = r2     // Catch:{ Throwable -> 0x022c }
            java.lang.String r2 = "response"
            r10[r9] = r2     // Catch:{ Throwable -> 0x022c }
            r10[r8] = r3     // Catch:{ Throwable -> 0x022c }
            java.lang.String r2 = "extraInfo"
            r10[r7] = r2     // Catch:{ Throwable -> 0x022c }
            r2 = 5
            r10[r2] = r4     // Catch:{ Throwable -> 0x022c }
            com.taobao.accs.utl.ALog.e(r5, r6, r10)     // Catch:{ Throwable -> 0x022c }
            r2 = 0
            return r2
        L_0x0189:
            java.lang.String r5 = r1.TAG     // Catch:{ Throwable -> 0x022c }
            java.lang.String r7 = "sendPushResponse channel by"
            java.lang.Object[] r8 = new java.lang.Object[r9]     // Catch:{ Throwable -> 0x022c }
            java.lang.String r9 = "app"
            r8[r12] = r9     // Catch:{ Throwable -> 0x022c }
            java.lang.String r9 = r4.fromPackage     // Catch:{ Throwable -> 0x022c }
            r10 = 1
            r8[r10] = r9     // Catch:{ Throwable -> 0x022c }
            com.taobao.accs.utl.ALog.i(r5, r7, r8)     // Catch:{ Throwable -> 0x022c }
            java.lang.String r4 = r4.fromPackage     // Catch:{ Throwable -> 0x022c }
            java.lang.String r5 = "com.taobao.accs.ChannelService"
            r6.setClassName(r4, r5)     // Catch:{ Throwable -> 0x022c }
            java.lang.String r4 = "send_type"
            com.taobao.accs.data.Message$ReqType r5 = com.taobao.accs.data.Message.ReqType.REQ     // Catch:{ Throwable -> 0x022c }
            r6.putExtra(r4, r5)     // Catch:{ Throwable -> 0x022c }
            java.lang.String r4 = "appKey"
            r6.putExtra(r4, r13)     // Catch:{ Throwable -> 0x022c }
            java.lang.String r4 = "userInfo"
            java.lang.String r5 = r3.userId     // Catch:{ Throwable -> 0x022c }
            r6.putExtra(r4, r5)     // Catch:{ Throwable -> 0x022c }
            java.lang.String r4 = "serviceId"
            java.lang.String r5 = r3.serviceId     // Catch:{ Throwable -> 0x022c }
            r6.putExtra(r4, r5)     // Catch:{ Throwable -> 0x022c }
            java.lang.String r4 = "data"
            byte[] r5 = r3.data     // Catch:{ Throwable -> 0x022c }
            r6.putExtra(r4, r5)     // Catch:{ Throwable -> 0x022c }
            java.lang.String r4 = "dataId"
            java.lang.String r5 = r3.dataId     // Catch:{ Throwable -> 0x022c }
            r6.putExtra(r4, r5)     // Catch:{ Throwable -> 0x022c }
            java.lang.String r4 = "configTag"
            java.lang.String r5 = r1.mConfigTag     // Catch:{ Throwable -> 0x022c }
            r6.putExtra(r4, r5)     // Catch:{ Throwable -> 0x022c }
            java.lang.String r4 = r3.businessId     // Catch:{ Throwable -> 0x022c }
            boolean r4 = android.text.TextUtils.isEmpty(r4)     // Catch:{ Throwable -> 0x022c }
            if (r4 != 0) goto L_0x01e1
            java.lang.String r4 = "businessId"
            java.lang.String r5 = r3.businessId     // Catch:{ Throwable -> 0x022c }
            r6.putExtra(r4, r5)     // Catch:{ Throwable -> 0x022c }
        L_0x01e1:
            java.lang.String r4 = r3.tag     // Catch:{ Throwable -> 0x022c }
            boolean r4 = android.text.TextUtils.isEmpty(r4)     // Catch:{ Throwable -> 0x022c }
            if (r4 != 0) goto L_0x01f0
            java.lang.String r4 = "extTag"
            java.lang.String r5 = r3.tag     // Catch:{ Throwable -> 0x022c }
            r6.putExtra(r4, r5)     // Catch:{ Throwable -> 0x022c }
        L_0x01f0:
            java.lang.String r4 = r3.target     // Catch:{ Throwable -> 0x022c }
            if (r4 == 0) goto L_0x01fb
            java.lang.String r4 = "target"
            java.lang.String r5 = r3.target     // Catch:{ Throwable -> 0x022c }
            r6.putExtra(r4, r5)     // Catch:{ Throwable -> 0x022c }
        L_0x01fb:
            com.taobao.accs.dispatch.IntentDispatch.dispatchIntent(r2, r6, r12)     // Catch:{ Throwable -> 0x022c }
            goto L_0x0262
        L_0x01ff:
            java.lang.String r5 = r1.TAG     // Catch:{ Throwable -> 0x022c }
            java.lang.String r6 = "sendPushResponse input null"
            r10 = 6
            java.lang.Object[] r10 = new java.lang.Object[r10]     // Catch:{ Throwable -> 0x022c }
            java.lang.String r11 = "context"
            r10[r12] = r11     // Catch:{ Throwable -> 0x022c }
            r11 = 1
            r10[r11] = r2     // Catch:{ Throwable -> 0x022c }
            java.lang.String r2 = "response"
            r10[r9] = r2     // Catch:{ Throwable -> 0x022c }
            r10[r8] = r3     // Catch:{ Throwable -> 0x022c }
            java.lang.String r2 = "extraInfo"
            r10[r7] = r2     // Catch:{ Throwable -> 0x022c }
            r2 = 5
            r10[r2] = r22     // Catch:{ Throwable -> 0x022c }
            com.taobao.accs.utl.ALog.e(r5, r6, r10)     // Catch:{ Throwable -> 0x022c }
            java.lang.String r2 = "accs"
            java.lang.String r4 = "send_fail"
            java.lang.String r5 = ""
            java.lang.String r6 = "1"
            java.lang.String r7 = "sendPushResponse null"
            com.taobao.accs.utl.AppMonitorAdapter.commitAlarmFail(r2, r4, r5, r6, r7)     // Catch:{ Throwable -> 0x022c }
            r2 = 0
            return r2
        L_0x022c:
            r0 = move-exception
            r2 = r0
            java.lang.String r4 = "accs"
            java.lang.String r5 = "send_fail"
            java.lang.String r6 = r3.serviceId
            java.lang.String r7 = "1"
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            java.lang.String r9 = "push response "
            r8.<init>(r9)
            java.lang.String r9 = r2.toString()
            r8.append(r9)
            java.lang.String r8 = r8.toString()
            com.taobao.accs.utl.AppMonitorAdapter.commitAlarmFail(r4, r5, r6, r7, r8)
            java.lang.String r4 = r1.TAG
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            java.lang.String r6 = "sendPushResponse dataid:"
            r5.<init>(r6)
            java.lang.String r3 = r3.dataId
            r5.append(r3)
            java.lang.String r3 = r5.toString()
            java.lang.Object[] r5 = new java.lang.Object[r12]
            com.taobao.accs.utl.ALog.e(r4, r3, r2, r5)
        L_0x0262:
            r2 = 0
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.taobao.accs.internal.ACCSManagerImpl.sendPushResponse(android.content.Context, com.taobao.accs.ACCSManager$AccsRequest, com.taobao.accs.base.TaoBaseService$ExtraInfo):java.lang.String");
    }

    public boolean isNetworkReachable(Context context) {
        return UtilityImpl.isNetworkConnected(context);
    }

    private Intent getIntent(Context context, int i) {
        if (i == 1 || !UtilityImpl.getFocusDisableStatus(context)) {
            Intent intent = new Intent();
            intent.setAction(Constants.ACTION_COMMAND);
            intent.setClassName(context.getPackageName(), AdapterUtilityImpl.channelService);
            intent.putExtra("packageName", context.getPackageName());
            intent.putExtra("command", i);
            intent.putExtra("appKey", this.mConnection.mAppkey);
            intent.putExtra(Constants.KEY_CONFIG_TAG, this.mConfigTag);
            return intent;
        }
        StringBuilder sb = new StringBuilder("getIntent null command:");
        sb.append(i);
        sb.append(" accs enabled:");
        sb.append(UtilityImpl.getFocusDisableStatus(context));
        ALog.e(this.TAG, sb.toString(), new Object[0]);
        return null;
    }

    public void forceDisableService(Context context) {
        UtilityImpl.focusDisableService(context);
    }

    public void forceEnableService(Context context) {
        UtilityImpl.focusEnableService(context);
    }

    @Deprecated
    public void setMode(Context context, int i) {
        ACCSClient.setEnvironment(context, i);
    }

    private void sendAppNotBind(Context context, int i, String str, String str2) {
        Intent intent = new Intent(Constants.ACTION_RECEIVE);
        intent.setPackage(context.getPackageName());
        intent.putExtra("command", i);
        intent.putExtra("serviceId", str);
        intent.putExtra(Constants.KEY_DATA_ID, str2);
        intent.putExtra("appKey", this.mConnection.mAppkey);
        intent.putExtra(Constants.KEY_CONFIG_TAG, this.mConfigTag);
        intent.putExtra("errorCode", i == 2 ? 200 : 300);
        MsgDistribute.distribMessage(context, intent);
    }

    public void setProxy(Context context, String str, int i) {
        Editor edit = context.getSharedPreferences(Constants.SP_FILE_NAME, 0).edit();
        if (!TextUtils.isEmpty(str)) {
            edit.putString(Constants.KEY_PROXY_HOST, str);
        }
        edit.putInt(Constants.KEY_PROXY_PORT, i);
        edit.apply();
    }

    public void startInAppConnection(Context context, String str, String str2, IAppReceiver iAppReceiver) {
        startInAppConnection(context, str, null, str2, iAppReceiver);
    }

    public void startInAppConnection(Context context, String str, String str2, String str3, IAppReceiver iAppReceiver) {
        GlobalClientInfo.getInstance(context).setAppReceiver(this.mConfigTag, iAppReceiver);
        if (!UtilityImpl.isMainProcess(context)) {
            ALog.d(this.TAG, "inapp only init in main process!", new Object[0]);
            return;
        }
        ALog.d(this.TAG, "startInAppConnection APPKEY:".concat(String.valueOf(str)), new Object[0]);
        if (!TextUtils.isEmpty(str)) {
            if (!TextUtils.equals(this.mConnection.getAppkey(), str)) {
                this.mConnection.mTtid = str3;
                this.mConnection.mAppkey = str;
                UtilityImpl.saveAppKey(context, str, this.mConnection.mConfig.getAppSecret());
            }
            this.mConnection.start();
        }
    }

    public void setLoginInfo(Context context, ILoginInfo iLoginInfo) {
        GlobalClientInfo.getInstance(context).setLoginInfoImpl(this.mConnection.mConfigTag, iLoginInfo);
    }

    public void clearLoginInfo(Context context) {
        GlobalClientInfo.getInstance(context).clearLoginInfoImpl();
    }

    public boolean cancel(Context context, String str) {
        return this.mConnection.cancel(str);
    }

    public Map<String, Boolean> getChannelState() throws Exception {
        String host = this.mConnection.getHost(GlobalClientInfo.getContext(), null);
        HashMap hashMap = new HashMap();
        hashMap.put(host, Boolean.FALSE);
        if (r.a(this.mConnection.mConfig.getAppKey()).b(host) != null) {
            hashMap.put(host, Boolean.TRUE);
        }
        String str = this.TAG;
        StringBuilder sb = new StringBuilder("getChannelState ");
        sb.append(hashMap.toString());
        ALog.d(str, sb.toString(), new Object[0]);
        return hashMap;
    }

    public Map<String, Boolean> forceReConnectChannel() throws Exception {
        r.a(this.mConnection.mConfig.getAppKey()).h.a(true);
        return getChannelState();
    }

    public boolean isChannelError(int i) {
        return ErrorCode.isChannelError(i);
    }

    public void registerSerivce(Context context, String str, String str2) {
        GlobalClientInfo.getInstance(context).registerService(str, str2);
    }

    public void unRegisterSerivce(Context context, String str) {
        GlobalClientInfo.getInstance(context).unRegisterService(str);
    }

    public void registerDataListener(Context context, String str, AccsAbstractDataListener accsAbstractDataListener) {
        GlobalClientInfo.getInstance(context).registerListener(str, accsAbstractDataListener);
    }

    public void unRegisterDataListener(Context context, String str) {
        GlobalClientInfo.getInstance(context).unregisterListener(str);
    }

    public void sendBusinessAck(String str, String str2, String str3, short s, String str4, Map<Integer, String> map) {
        this.mConnection.send(Message.buildPushAck(this.mConnection, str, str2, str3, true, s, str4, map), true);
    }

    public void updateConfig(AccsClientConfig accsClientConfig) {
        if (this.mConnection instanceof InAppConnection) {
            ((InAppConnection) this.mConnection).updateConfig(accsClientConfig);
        }
    }
}
