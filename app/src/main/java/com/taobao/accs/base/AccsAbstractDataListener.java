package com.taobao.accs.base;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import com.taobao.accs.ACCSManager;
import com.taobao.accs.IACCSManager;
import com.taobao.accs.base.TaoBaseService.ConnectInfo;
import com.taobao.accs.base.TaoBaseService.ExtHeaderType;
import com.taobao.accs.base.TaoBaseService.ExtraInfo;
import com.taobao.accs.common.Constants;
import com.taobao.accs.utl.ALog;
import com.taobao.accs.utl.ALog.Level;
import com.taobao.accs.utl.AppMonitorAdapter;
import com.taobao.accs.utl.BaseMonitor;
import com.taobao.accs.utl.UTMini;
import java.util.HashMap;
import java.util.Map;

public abstract class AccsAbstractDataListener implements AccsDataListener {
    private static final String TAG = "AccsAbstractDataListener";

    public void onAntiBrush(boolean z, ExtraInfo extraInfo) {
    }

    public void onConnected(ConnectInfo connectInfo) {
    }

    public void onDisconnected(ConnectInfo connectInfo) {
    }

    public static int onReceiveData(Context context, Intent intent, AccsDataListener accsDataListener) {
        String str;
        Exception exc;
        Context context2 = context;
        Intent intent2 = intent;
        AccsDataListener accsDataListener2 = accsDataListener;
        if (accsDataListener2 == null || context2 == null) {
            ALog.e(TAG, "onReceiveData listener or context null", new Object[0]);
            return 2;
        }
        if (intent2 != null) {
            try {
                int intExtra = intent2.getIntExtra("command", -1);
                int intExtra2 = intent2.getIntExtra("errorCode", 0);
                String stringExtra = intent2.getStringExtra("userInfo");
                String stringExtra2 = intent2.getStringExtra(Constants.KEY_DATA_ID);
                str = intent2.getStringExtra("serviceId");
                try {
                    if (ALog.isPrintLog(Level.I)) {
                        ALog.i(TAG, "onReceiveData", Constants.KEY_DATA_ID, stringExtra2, "serviceId", str, "command", Integer.valueOf(intExtra), "className", accsDataListener.getClass().getName());
                    }
                    if (intExtra > 0) {
                        UTMini instance = UTMini.getInstance();
                        String concat = "commandId=".concat(String.valueOf(intExtra));
                        StringBuilder sb = new StringBuilder("serviceId=");
                        sb.append(str);
                        sb.append(" dataId=");
                        sb.append(stringExtra2);
                        instance.commitEvent(66001, "MsgToBuss5", concat, sb.toString(), Integer.valueOf(Constants.SDK_VERSION_CODE));
                        StringBuilder sb2 = new StringBuilder("3commandId=");
                        sb2.append(intExtra);
                        sb2.append("serviceId=");
                        sb2.append(str);
                        AppMonitorAdapter.commitCount("accs", BaseMonitor.COUNT_POINT_TO_BUSS, sb2.toString(), 0.0d);
                        switch (intExtra) {
                            case 5:
                                accsDataListener2.onBind(str, intExtra2, getExtraInfo(intent));
                                break;
                            case 6:
                                accsDataListener2.onUnbind(str, intExtra2, getExtraInfo(intent));
                                break;
                            case 100:
                                String stringExtra3 = intent2.getStringExtra(Constants.KEY_DATA_ID);
                                if (!TextUtils.equals("res", intent2.getStringExtra(Constants.KEY_SEND_TYPE))) {
                                    accsDataListener2.onSendData(str, stringExtra3, intExtra2, getExtraInfo(intent));
                                    break;
                                } else {
                                    accsDataListener2.onResponse(str, stringExtra3, intExtra2, intent2.getByteArrayExtra("data"), getExtraInfo(intent));
                                    break;
                                }
                            case 101:
                                byte[] byteArrayExtra = intent2.getByteArrayExtra("data");
                                boolean booleanExtra = intent2.getBooleanExtra(Constants.KEY_NEED_BUSINESS_ACK, false);
                                if (byteArrayExtra == null) {
                                    ALog.e(TAG, "onReceiveData COMMAND_RECEIVE_DATA msg null", new Object[0]);
                                    AppMonitorAdapter.commitAlarmFail("accs", BaseMonitor.ALARM_POINT_REQ_ERROR, str, "1", "COMMAND_RECEIVE_DATA msg null");
                                    break;
                                } else {
                                    String stringExtra4 = intent2.getStringExtra(Constants.KEY_DATA_ID);
                                    if (ALog.isPrintLog(Level.D)) {
                                        StringBuilder sb3 = new StringBuilder("onReceiveData COMMAND_RECEIVE_DATA onData dataId:");
                                        sb3.append(stringExtra4);
                                        sb3.append(" serviceId:");
                                        sb3.append(str);
                                        ALog.d(TAG, sb3.toString(), new Object[0]);
                                    }
                                    ExtraInfo extraInfo = getExtraInfo(intent);
                                    if (booleanExtra) {
                                        ALog.i(TAG, "onReceiveData try to send biz ack dataId ".concat(String.valueOf(stringExtra4)), new Object[0]);
                                        sendBusinessAck(context2, intent2, stringExtra4, extraInfo.oriExtHeader);
                                    }
                                    accsDataListener2.onData(str, stringExtra, stringExtra4, byteArrayExtra, extraInfo);
                                    break;
                                }
                            case 103:
                                boolean booleanExtra2 = intent2.getBooleanExtra(Constants.KEY_CONNECT_AVAILABLE, false);
                                String stringExtra5 = intent2.getStringExtra("host");
                                String stringExtra6 = intent2.getStringExtra(Constants.KEY_ERROR_DETAIL);
                                boolean booleanExtra3 = intent2.getBooleanExtra(Constants.KEY_TYPE_INAPP, false);
                                boolean booleanExtra4 = intent2.getBooleanExtra(Constants.KEY_CENTER_HOST, false);
                                if (!TextUtils.isEmpty(stringExtra5)) {
                                    if (!booleanExtra2) {
                                        ConnectInfo connectInfo = new ConnectInfo(stringExtra5, booleanExtra3, booleanExtra4, intExtra2, stringExtra6);
                                        accsDataListener2.onDisconnected(connectInfo);
                                        break;
                                    } else {
                                        accsDataListener2.onConnected(new ConnectInfo(stringExtra5, booleanExtra3, booleanExtra4));
                                        break;
                                    }
                                }
                                break;
                            case 104:
                                boolean booleanExtra5 = intent2.getBooleanExtra(Constants.KEY_ANTI_BRUSH_RET, false);
                                ALog.e(TAG, "onReceiveData anti brush result:".concat(String.valueOf(booleanExtra5)), new Object[0]);
                                accsDataListener2.onAntiBrush(booleanExtra5, null);
                                break;
                            default:
                                ALog.w(TAG, "onReceiveData command not handled", new Object[0]);
                                break;
                        }
                    } else {
                        ALog.w(TAG, "onReceiveData command not handled", new Object[0]);
                    }
                } catch (Exception e) {
                    exc = e;
                    try {
                        StringBuilder sb4 = new StringBuilder("callback error");
                        sb4.append(exc.toString());
                        AppMonitorAdapter.commitAlarmFail("accs", BaseMonitor.ALARM_POINT_REQ_ERROR, str, "1", sb4.toString());
                        ALog.e(TAG, "onReceiveData", exc, new Object[0]);
                        return 2;
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            } catch (Exception e2) {
                exc = e2;
                str = "";
                StringBuilder sb42 = new StringBuilder("callback error");
                sb42.append(exc.toString());
                AppMonitorAdapter.commitAlarmFail("accs", BaseMonitor.ALARM_POINT_REQ_ERROR, str, "1", sb42.toString());
                ALog.e(TAG, "onReceiveData", exc, new Object[0]);
                return 2;
            }
        }
        return 2;
    }

    private static Map<ExtHeaderType, String> getExtHeader(Map<Integer, String> map) {
        Map<ExtHeaderType, String> map2;
        ExtHeaderType[] values;
        if (map == null) {
            return null;
        }
        try {
            map2 = new HashMap<>();
            try {
                for (ExtHeaderType extHeaderType : ExtHeaderType.values()) {
                    String str = map.get(Integer.valueOf(extHeaderType.ordinal()));
                    if (!TextUtils.isEmpty(str)) {
                        map2.put(extHeaderType, str);
                    }
                }
            } catch (Exception e) {
                e = e;
                ALog.e(TAG, "getExtHeader", e, new Object[0]);
                return map2;
            }
        } catch (Exception e2) {
            e = e2;
            map2 = null;
            ALog.e(TAG, "getExtHeader", e, new Object[0]);
            return map2;
        }
        return map2;
    }

    private static ExtraInfo getExtraInfo(Intent intent) {
        ExtraInfo extraInfo = new ExtraInfo();
        try {
            HashMap hashMap = (HashMap) intent.getSerializableExtra(ExtraInfo.EXT_HEADER);
            Map<ExtHeaderType, String> extHeader = getExtHeader(hashMap);
            String stringExtra = intent.getStringExtra("packageName");
            String stringExtra2 = intent.getStringExtra("host");
            extraInfo.connType = intent.getIntExtra(Constants.KEY_CONN_TYPE, 0);
            extraInfo.extHeader = extHeader;
            extraInfo.oriExtHeader = hashMap;
            extraInfo.fromPackage = stringExtra;
            extraInfo.fromHost = stringExtra2;
        } catch (Throwable th) {
            ALog.e(TAG, "getExtraInfo", th, new Object[0]);
        }
        return extraInfo;
    }

    private static void sendBusinessAck(Context context, Intent intent, String str, Map<Integer, String> map) {
        try {
            ALog.i(TAG, "sendBusinessAck", Constants.KEY_DATA_ID, str);
            if (intent != null) {
                String stringExtra = intent.getStringExtra("host");
                String stringExtra2 = intent.getStringExtra("source");
                String stringExtra3 = intent.getStringExtra("target");
                String stringExtra4 = intent.getStringExtra("appKey");
                String stringExtra5 = intent.getStringExtra(Constants.KEY_CONFIG_TAG);
                short shortExtra = intent.getShortExtra(Constants.KEY_FLAGS, 0);
                IACCSManager accsInstance = ACCSManager.getAccsInstance(context, stringExtra4, stringExtra5);
                if (accsInstance != null) {
                    accsInstance.sendBusinessAck(stringExtra3, stringExtra2, str, shortExtra, stringExtra, map);
                    AppMonitorAdapter.commitCount("accs", BaseMonitor.COUNT_BUSINESS_ACK_SUCC, "", 0.0d);
                    return;
                }
                AppMonitorAdapter.commitCount("accs", BaseMonitor.COUNT_BUSINESS_ACK_FAIL, "no acsmgr", 0.0d);
            }
        } catch (Throwable th) {
            ALog.e(TAG, "sendBusinessAck", th, new Object[0]);
            AppMonitorAdapter.commitCount("accs", BaseMonitor.COUNT_BUSINESS_ACK_FAIL, th.toString(), 0.0d);
        }
    }
}
