package com.taobao.accs.data;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import com.alipay.mobile.common.logging.api.LogCategory;
import com.taobao.accs.IAppReceiver;
import com.taobao.accs.IAppReceiverV1;
import com.taobao.accs.base.AccsAbstractDataListener;
import com.taobao.accs.base.TaoBaseService.ConnectInfo;
import com.taobao.accs.client.GlobalClientInfo;
import com.taobao.accs.common.Constants;
import com.taobao.accs.common.ThreadPoolExecutorFactory;
import com.taobao.accs.dispatch.IntentDispatch;
import com.taobao.accs.utl.ALog;
import com.taobao.accs.utl.ALog.Level;
import com.taobao.accs.utl.AdapterUtilityImpl;
import com.taobao.accs.utl.AppMonitorAdapter;
import com.taobao.accs.utl.BaseMonitor;
import com.taobao.accs.utl.UTMini;
import com.taobao.accs.utl.UtilityImpl;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class MsgDistribute {
    private static final String KEY_ROUTING_ACK = "routingAck";
    private static final String KEY_ROUTING_MSG = "routingMsg";
    private static final long MIN_SPACE = 5242880;
    private static final String TAG = "MsgDistribute";
    private static volatile MsgDistribute instance;
    /* access modifiers changed from: private */
    public static Set<String> mRoutingDataIds;

    /* access modifiers changed from: protected */
    public String getChannelService(Context context) {
        return AdapterUtilityImpl.channelService;
    }

    /* access modifiers changed from: protected */
    public String getMsgDistributeService(Context context) {
        return AdapterUtilityImpl.msgService;
    }

    public static MsgDistribute getInstance() {
        if (instance == null) {
            synchronized (MsgDistribute.class) {
                if (instance == null) {
                    instance = new MsgDistribute();
                }
            }
        }
        return instance;
    }

    public static void distribMessage(final Context context, final Intent intent) {
        try {
            ThreadPoolExecutorFactory.getScheduledExecutor().execute(new Runnable() {
                public final void run() {
                    MsgDistribute.getInstance().distribute(context, intent);
                }
            });
        } catch (Throwable th) {
            ALog.e(TAG, "distribMessage", th, new Object[0]);
            UTMini instance2 = UTMini.getInstance();
            StringBuilder sb = new StringBuilder("distribMessage");
            sb.append(th.toString());
            instance2.commitEvent(66001, "MsgToBuss8", sb.toString(), Integer.valueOf(Constants.SDK_VERSION_CODE));
        }
    }

    /* access modifiers changed from: private */
    public void distribute(Context context, Intent intent) {
        String str;
        char c;
        String str2;
        int i;
        char c2;
        Throwable th;
        String str3;
        char c3;
        String str4;
        Throwable th2;
        Context context2 = context;
        Intent intent2 = intent;
        String stringExtra = intent2.getStringExtra(Constants.KEY_DATA_ID);
        String stringExtra2 = intent2.getStringExtra("serviceId");
        String action = intent.getAction();
        if (ALog.isPrintLog(Level.D)) {
            ALog.d(TAG, "distribute ready", "action", action, Constants.KEY_DATA_ID, stringExtra, "serviceId", stringExtra2);
        }
        if (TextUtils.isEmpty(action)) {
            ALog.e(TAG, "action null", new Object[0]);
            UTMini.getInstance().commitEvent(66001, "MsgToBuss9", "action null", Integer.valueOf(Constants.SDK_VERSION_CODE));
            return;
        }
        try {
            if (TextUtils.equals(action, Constants.ACTION_RECEIVE)) {
                try {
                    i = intent2.getIntExtra("command", -1);
                    try {
                        String stringExtra3 = intent2.getStringExtra("userInfo");
                        int intExtra = intent2.getIntExtra("errorCode", 0);
                        String stringExtra4 = intent2.getStringExtra("appKey");
                        String stringExtra5 = intent2.getStringExtra(Constants.KEY_CONFIG_TAG);
                        if (intent.getPackage() == null) {
                            try {
                                intent2.setPackage(context.getPackageName());
                            } catch (Throwable th3) {
                                th = th3;
                                str = stringExtra;
                                c2 = 0;
                                c = 2;
                                str2 = stringExtra2;
                            }
                        }
                        if ("accs".equals(stringExtra2)) {
                            ALog.e(TAG, "distribute start", "appkey", stringExtra4, "config", stringExtra5);
                        } else {
                            ALog.d(TAG, "distribute start", "appkey", stringExtra4, "config", stringExtra5);
                        }
                        if (!handleRoutingMsgAck(context2, intent2, stringExtra, stringExtra2)) {
                            if (i < 0) {
                                ALog.e(TAG, "command error:".concat(String.valueOf(i)), "serviceId", stringExtra2);
                            } else if (!checkSpace(i, stringExtra2, stringExtra) && !handleRoutingMsg(context2, intent2, stringExtra, stringExtra2)) {
                                Map<String, IAppReceiver> appReceiver = GlobalClientInfo.getInstance(context).getAppReceiver();
                                IAppReceiver iAppReceiver = null;
                                if (!TextUtils.isEmpty(stringExtra5) && appReceiver != null) {
                                    iAppReceiver = appReceiver.get(stringExtra5);
                                }
                                IAppReceiver iAppReceiver2 = iAppReceiver;
                                Map<String, IAppReceiver> map = appReceiver;
                                String str5 = stringExtra5;
                                if (!handleMsgInChannelProcess(context2, stringExtra2, stringExtra, intent2, iAppReceiver2)) {
                                    String str6 = stringExtra4;
                                    int i2 = i;
                                    int i3 = i;
                                    String str7 = stringExtra3;
                                    c = 2;
                                    String str8 = stringExtra;
                                    str = stringExtra;
                                    str2 = stringExtra2;
                                    try {
                                        handleControlMsg(context2, intent2, str5, str6, i2, str7, stringExtra2, str8, iAppReceiver2, intExtra);
                                        if (!TextUtils.isEmpty(str2)) {
                                            handleBusinessMsg(context2, iAppReceiver2, intent2, str2, str, i3, intExtra);
                                        } else {
                                            handBroadCastMsg(context2, map, intent2, i3, intExtra);
                                        }
                                    } catch (Throwable th4) {
                                        th = th4;
                                        i = i3;
                                        c2 = 0;
                                        Object[] objArr = new Object[4];
                                        objArr[c2] = Constants.KEY_DATA_ID;
                                        objArr[1] = str;
                                        objArr[c] = "serviceId";
                                        objArr[3] = str2;
                                        ALog.e(TAG, "distribMessage", th, objArr);
                                        StringBuilder sb = new StringBuilder("distribute error ");
                                        sb.append(i);
                                        sb.append(UtilityImpl.getStackMsg(th));
                                        AppMonitorAdapter.commitAlarmFail("accs", BaseMonitor.ALARM_POINT_REQ_ERROR, str2, "1", sb.toString());
                                    }
                                }
                            }
                        }
                    } catch (Throwable th5) {
                        int i4 = i;
                        str = stringExtra;
                        c = 2;
                        str2 = stringExtra2;
                        th = th5;
                        c2 = 0;
                        Object[] objArr2 = new Object[4];
                        objArr2[c2] = Constants.KEY_DATA_ID;
                        objArr2[1] = str;
                        objArr2[c] = "serviceId";
                        objArr2[3] = str2;
                        ALog.e(TAG, "distribMessage", th, objArr2);
                        StringBuilder sb2 = new StringBuilder("distribute error ");
                        sb2.append(i);
                        sb2.append(UtilityImpl.getStackMsg(th));
                        AppMonitorAdapter.commitAlarmFail("accs", BaseMonitor.ALARM_POINT_REQ_ERROR, str2, "1", sb2.toString());
                    }
                } catch (Throwable th6) {
                    str3 = stringExtra;
                    c3 = 2;
                    str4 = stringExtra2;
                    th2 = th6;
                    c2 = 0;
                    i = 0;
                    Object[] objArr22 = new Object[4];
                    objArr22[c2] = Constants.KEY_DATA_ID;
                    objArr22[1] = str;
                    objArr22[c] = "serviceId";
                    objArr22[3] = str2;
                    ALog.e(TAG, "distribMessage", th, objArr22);
                    StringBuilder sb22 = new StringBuilder("distribute error ");
                    sb22.append(i);
                    sb22.append(UtilityImpl.getStackMsg(th));
                    AppMonitorAdapter.commitAlarmFail("accs", BaseMonitor.ALARM_POINT_REQ_ERROR, str2, "1", sb22.toString());
                }
            } else {
                str3 = stringExtra;
                c3 = 2;
                str4 = stringExtra2;
                c2 = 0;
                try {
                    ALog.e(TAG, "distribMessage action error", new Object[0]);
                    UTMini.getInstance().commitEvent(66001, "MsgToBuss10", action, Integer.valueOf(Constants.SDK_VERSION_CODE));
                } catch (Throwable th7) {
                    th = th7;
                    th2 = th;
                    i = 0;
                    Object[] objArr222 = new Object[4];
                    objArr222[c2] = Constants.KEY_DATA_ID;
                    objArr222[1] = str;
                    objArr222[c] = "serviceId";
                    objArr222[3] = str2;
                    ALog.e(TAG, "distribMessage", th, objArr222);
                    StringBuilder sb222 = new StringBuilder("distribute error ");
                    sb222.append(i);
                    sb222.append(UtilityImpl.getStackMsg(th));
                    AppMonitorAdapter.commitAlarmFail("accs", BaseMonitor.ALARM_POINT_REQ_ERROR, str2, "1", sb222.toString());
                }
            }
        } catch (Throwable th8) {
            th = th8;
            str3 = stringExtra;
            c2 = 0;
            c3 = 2;
            str4 = stringExtra2;
            th2 = th;
            i = 0;
            Object[] objArr2222 = new Object[4];
            objArr2222[c2] = Constants.KEY_DATA_ID;
            objArr2222[1] = str;
            objArr2222[c] = "serviceId";
            objArr2222[3] = str2;
            ALog.e(TAG, "distribMessage", th, objArr2222);
            StringBuilder sb2222 = new StringBuilder("distribute error ");
            sb2222.append(i);
            sb2222.append(UtilityImpl.getStackMsg(th));
            AppMonitorAdapter.commitAlarmFail("accs", BaseMonitor.ALARM_POINT_REQ_ERROR, str2, "1", sb2222.toString());
        }
    }

    /* access modifiers changed from: protected */
    public boolean checkSpace(int i, String str, String str2) {
        if (i != 100 && !GlobalClientInfo.AGOO_SERVICE_ID.equals(str)) {
            long usableSpace = UtilityImpl.getUsableSpace();
            if (usableSpace != -1 && usableSpace <= MIN_SPACE) {
                AppMonitorAdapter.commitAlarmFail("accs", BaseMonitor.ALARM_POINT_REQ_ERROR, str, "1", "space low ".concat(String.valueOf(usableSpace)));
                ALog.e(TAG, "user space low, don't distribute", "size", Long.valueOf(usableSpace), "serviceId", str);
                return true;
            }
        }
        return false;
    }

    /* access modifiers changed from: protected */
    public boolean handleMsgInChannelProcess(Context context, String str, String str2, Intent intent, IAppReceiver iAppReceiver) {
        boolean z = false;
        try {
            if (TextUtils.isEmpty(str)) {
                return false;
            }
            String str3 = null;
            if (iAppReceiver != null) {
                str3 = iAppReceiver.getService(str);
            }
            if (TextUtils.isEmpty(str3)) {
                str3 = GlobalClientInfo.getInstance(context).getService(str);
            }
            if (TextUtils.isEmpty(str3) && !UtilityImpl.isMainProcess(context)) {
                if ("accs".equals(str)) {
                    ALog.e(TAG, "start MsgDistributeService", Constants.KEY_DATA_ID, str2);
                } else {
                    ALog.i(TAG, "start MsgDistributeService", Constants.KEY_DATA_ID, str2);
                }
                intent.setClassName(intent.getPackage(), getMsgDistributeService(context));
                IntentDispatch.dispatchIntent(context, intent, false);
                z = true;
            }
            return z;
        } catch (Throwable th) {
            ALog.e(TAG, "handleMsgInChannelProcess", th, new Object[0]);
        }
    }

    private void handleControlMsg(Context context, Intent intent, String str, String str2, int i, String str3, String str4, String str5, IAppReceiver iAppReceiver, int i2) {
        String str6 = str2;
        int i3 = i;
        String str7 = str3;
        String str8 = str4;
        String str9 = str5;
        IAppReceiver iAppReceiver2 = iAppReceiver;
        int i4 = i2;
        if (ALog.isPrintLog(Level.D)) {
            Object[] objArr = new Object[12];
            objArr[0] = Constants.KEY_CONFIG_TAG;
            objArr[1] = str;
            objArr[2] = Constants.KEY_DATA_ID;
            objArr[3] = str9;
            objArr[4] = "serviceId";
            objArr[5] = str8;
            objArr[6] = "command";
            objArr[7] = Integer.valueOf(i);
            objArr[8] = "errorCode";
            objArr[9] = Integer.valueOf(i2);
            objArr[10] = "appReceiver";
            objArr[11] = iAppReceiver2 == null ? null : iAppReceiver.getClass().getName();
            ALog.d(TAG, "handleControlMsg", objArr);
        }
        if (iAppReceiver2 != null) {
            switch (i3) {
                case 1:
                    if (!(iAppReceiver2 instanceof IAppReceiverV1)) {
                        iAppReceiver.onBindApp(i2);
                        break;
                    } else {
                        ((IAppReceiverV1) iAppReceiver2).onBindApp(i4, null);
                        break;
                    }
                case 2:
                    if (i4 == 200) {
                        UtilityImpl.disableService(context);
                    }
                    iAppReceiver.onUnbindApp(i2);
                    break;
                case 3:
                    iAppReceiver2.onBindUser(str7, i4);
                    break;
                case 4:
                    iAppReceiver.onUnbindUser(i2);
                    break;
                default:
                    switch (i3) {
                        case 100:
                            if (TextUtils.isEmpty(str4)) {
                                iAppReceiver2.onSendData(str9, i4);
                                break;
                            }
                            break;
                        case 101:
                            if (TextUtils.isEmpty(str4)) {
                                ALog.d(TAG, "handleControlMsg serviceId isEmpty", new Object[0]);
                                byte[] byteArrayExtra = intent.getByteArrayExtra("data");
                                if (byteArrayExtra != null) {
                                    iAppReceiver2.onData(str7, str9, byteArrayExtra);
                                    break;
                                }
                            }
                            break;
                    }
            }
        }
        if (i3 != 1 || GlobalClientInfo.mAgooAppReceiver == null || str6 == null || !str6.equals(GlobalClientInfo.mAgooAppReceiver.getAppkey())) {
            if (!(iAppReceiver2 != null || i3 == 104 || i3 == 103)) {
                AppMonitorAdapter.commitAlarmFail("accs", BaseMonitor.ALARM_POINT_REQ_ERROR, str8, "1", "appReceiver null return");
                UTMini instance2 = UTMini.getInstance();
                String concat = "commandId=".concat(String.valueOf(i));
                StringBuilder sb = new StringBuilder("serviceId=");
                sb.append(str8);
                sb.append(" errorCode=");
                sb.append(i4);
                sb.append(" dataId=");
                sb.append(str9);
                instance2.commitEvent(66001, "MsgToBuss7", concat, sb.toString(), Integer.valueOf(Constants.SDK_VERSION_CODE));
            }
            return;
        }
        ALog.d(TAG, "handleControlMsg agoo receiver onBindApp", new Object[0]);
        GlobalClientInfo.mAgooAppReceiver.onBindApp(i4, null);
    }

    /* access modifiers changed from: protected */
    public void handleBusinessMsg(Context context, IAppReceiver iAppReceiver, Intent intent, String str, String str2, int i, int i2) {
        ALog.i(TAG, "handleBusinessMsg start", Constants.KEY_DATA_ID, str2, "serviceId", str, "command", Integer.valueOf(i));
        String service = iAppReceiver != null ? iAppReceiver.getService(str) : null;
        if (TextUtils.isEmpty(service)) {
            service = GlobalClientInfo.getInstance(context).getService(str);
        }
        if (!TextUtils.isEmpty(service)) {
            if (ALog.isPrintLog(Level.D)) {
                ALog.d(TAG, "handleBusinessMsg to start service", "className", service);
            }
            intent.setClassName(context, service);
            IntentDispatch.dispatchIntent(context, intent, true);
        } else {
            AccsAbstractDataListener listener = GlobalClientInfo.getInstance(context).getListener(str);
            if (listener != null) {
                if (ALog.isPrintLog(Level.D)) {
                    ALog.d(TAG, "handleBusinessMsg getListener not null", new Object[0]);
                }
                AccsAbstractDataListener.onReceiveData(context, intent, listener);
            } else {
                ALog.e(TAG, "handleBusinessMsg getListener also null", new Object[0]);
                AppMonitorAdapter.commitAlarmFail("accs", BaseMonitor.ALARM_POINT_REQ_ERROR, str, "1", "service is null");
            }
        }
        UTMini instance2 = UTMini.getInstance();
        String concat = "commandId=".concat(String.valueOf(i));
        StringBuilder sb = new StringBuilder("serviceId=");
        sb.append(str);
        sb.append(" errorCode=");
        sb.append(i2);
        sb.append(" dataId=");
        sb.append(str2);
        instance2.commitEvent(66001, "MsgToBuss", concat, sb.toString(), Integer.valueOf(Constants.SDK_VERSION_CODE));
        StringBuilder sb2 = new StringBuilder("2commandId=");
        sb2.append(i);
        sb2.append("serviceId=");
        sb2.append(str);
        AppMonitorAdapter.commitCount("accs", BaseMonitor.COUNT_POINT_TO_BUSS, sb2.toString(), 0.0d);
    }

    /* access modifiers changed from: protected */
    public void handBroadCastMsg(Context context, Map<String, IAppReceiver> map, Intent intent, int i, int i2) {
        Context context2 = context;
        Intent intent2 = intent;
        int i3 = i;
        ALog.i(TAG, "handBroadCastMsg", "command", Integer.valueOf(i));
        HashMap hashMap = new HashMap();
        if (map != null) {
            for (Entry<String, IAppReceiver> value : map.entrySet()) {
                Map<String, String> allServices = ((IAppReceiver) value.getValue()).getAllServices();
                if (allServices != null) {
                    hashMap.putAll(allServices);
                }
            }
        }
        if (i3 == 103) {
            for (String str : hashMap.keySet()) {
                if ("accs".equals(str) || "windvane".equals(str) || "motu-remote".equals(str)) {
                    String str2 = (String) hashMap.get(str);
                    if (TextUtils.isEmpty(str2)) {
                        str2 = GlobalClientInfo.getInstance(context).getService(str);
                    }
                    if (!TextUtils.isEmpty(str2)) {
                        intent2.setClassName(context2, str2);
                        IntentDispatch.dispatchIntent(context2, intent2, true);
                    }
                }
            }
            boolean booleanExtra = intent2.getBooleanExtra(Constants.KEY_CONNECT_AVAILABLE, false);
            String stringExtra = intent2.getStringExtra("host");
            String stringExtra2 = intent2.getStringExtra(Constants.KEY_ERROR_DETAIL);
            boolean booleanExtra2 = intent2.getBooleanExtra(Constants.KEY_TYPE_INAPP, false);
            boolean booleanExtra3 = intent2.getBooleanExtra(Constants.KEY_CENTER_HOST, false);
            ConnectInfo connectInfo = null;
            if (!TextUtils.isEmpty(stringExtra)) {
                if (booleanExtra) {
                    connectInfo = new ConnectInfo(stringExtra, booleanExtra2, booleanExtra3);
                } else {
                    connectInfo = new ConnectInfo(stringExtra, booleanExtra2, booleanExtra3, i2, stringExtra2);
                }
                connectInfo.connected = booleanExtra;
            }
            if (connectInfo != null) {
                ALog.d(TAG, "handBroadCastMsg ACTION_CONNECT_INFO", connectInfo);
                Intent intent3 = new Intent(Constants.ACTION_CONNECT_INFO);
                intent3.setPackage(context.getPackageName());
                intent3.putExtra(Constants.KEY_CONNECT_INFO, connectInfo);
                context2.sendBroadcast(intent3);
                return;
            }
            ALog.e(TAG, "handBroadCastMsg connect info null, host empty", new Object[0]);
        } else if (i3 == 104) {
            for (String str3 : hashMap.keySet()) {
                String str4 = (String) hashMap.get(str3);
                if (TextUtils.isEmpty(str4)) {
                    str4 = GlobalClientInfo.getInstance(context).getService(str3);
                }
                if (!TextUtils.isEmpty(str4)) {
                    intent2.setClassName(context2, str4);
                    IntentDispatch.dispatchIntent(context2, intent2, true);
                }
            }
        } else {
            ALog.w(TAG, "handBroadCastMsg not handled command", new Object[0]);
        }
    }

    private boolean handleRoutingMsgAck(Context context, Intent intent, String str, String str2) {
        boolean z;
        boolean booleanExtra = intent.getBooleanExtra(KEY_ROUTING_ACK, false);
        boolean booleanExtra2 = intent.getBooleanExtra(KEY_ROUTING_MSG, false);
        if (booleanExtra) {
            ALog.e(TAG, "recieve routiong ack", Constants.KEY_DATA_ID, str, "serviceId", str2);
            if (mRoutingDataIds != null) {
                mRoutingDataIds.remove(str);
            }
            AppMonitorAdapter.commitAlarmSuccess("accs", BaseMonitor.ALARM_MSG_ROUTING_RATE, "");
            z = true;
        } else {
            z = false;
        }
        if (booleanExtra2) {
            try {
                String stringExtra = intent.getStringExtra("packageName");
                ALog.e(TAG, "send routiong ack", Constants.KEY_DATA_ID, str, "to pkg", stringExtra, "serviceId", str2);
                Intent intent2 = new Intent(Constants.ACTION_COMMAND);
                intent2.putExtra("command", 106);
                intent2.setClassName(stringExtra, AdapterUtilityImpl.channelService);
                intent2.putExtra(KEY_ROUTING_ACK, true);
                intent2.putExtra("packageName", stringExtra);
                intent2.putExtra(Constants.KEY_DATA_ID, str);
                IntentDispatch.dispatchIntent(context, intent, false);
            } catch (Throwable th) {
                ALog.e(TAG, "send routing ack", th, "serviceId", str2);
            }
        }
        return z;
    }

    private boolean handleRoutingMsg(Context context, final Intent intent, final String str, final String str2) {
        if (context.getPackageName().equals(intent.getPackage())) {
            return false;
        }
        try {
            ALog.e(TAG, "start MsgDistributeService", "receive pkg", context.getPackageName(), "target pkg", intent.getPackage(), "serviceId", str2);
            intent.setClassName(intent.getPackage(), AdapterUtilityImpl.msgService);
            intent.putExtra(KEY_ROUTING_MSG, true);
            intent.putExtra("packageName", context.getPackageName());
            context.startService(intent);
            if (mRoutingDataIds == null) {
                mRoutingDataIds = new HashSet();
            }
            mRoutingDataIds.add(str);
            ThreadPoolExecutorFactory.schedule(new Runnable() {
                public void run() {
                    if (MsgDistribute.mRoutingDataIds != null && MsgDistribute.mRoutingDataIds.contains(str)) {
                        ALog.e(MsgDistribute.TAG, "routing msg time out, try election", Constants.KEY_DATA_ID, str, "serviceId", str2);
                        MsgDistribute.mRoutingDataIds.remove(str);
                        StringBuilder sb = new StringBuilder("pkg:");
                        sb.append(intent.getPackage());
                        AppMonitorAdapter.commitAlarmFail("accs", BaseMonitor.ALARM_MSG_ROUTING_RATE, "", "timeout", sb.toString());
                    }
                }
            }, 10, TimeUnit.SECONDS);
        } catch (Throwable th) {
            AppMonitorAdapter.commitAlarmFail("accs", BaseMonitor.ALARM_MSG_ROUTING_RATE, "", LogCategory.CATEGORY_EXCEPTION, th.toString());
            ALog.e(TAG, "routing msg error, try election", th, "serviceId", str2, Constants.KEY_DATA_ID, str);
        }
        return true;
    }
}
