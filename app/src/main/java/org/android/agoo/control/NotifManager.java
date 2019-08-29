package org.android.agoo.control;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.text.TextUtils;
import com.alipay.mobile.antui.screenadpt.AUScreenAdaptTool;
import com.alipay.mobile.common.logging.api.ProcessInfo;
import com.taobao.accs.ACCSManager;
import com.taobao.accs.ACCSManager.AccsRequest;
import com.taobao.accs.IACCSManager;
import com.taobao.accs.base.TaoBaseService.ExtraInfo;
import com.taobao.accs.common.Constants;
import com.taobao.accs.common.ThreadPoolExecutorFactory;
import com.taobao.accs.utl.ALog;
import com.taobao.accs.utl.ALog.Level;
import com.taobao.accs.utl.AdapterUtilityImpl;
import com.taobao.accs.utl.AppMonitorAdapter;
import com.taobao.accs.utl.BaseMonitor;
import com.taobao.accs.utl.UTMini;
import com.taobao.tao.remotebusiness.js.MtopJSBridge.MtopJSParam;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.android.agoo.common.Config;
import org.android.agoo.common.MsgDO;
import org.android.agoo.common.ThreadUtil;
import org.json.JSONObject;

public class NotifManager {
    /* access modifiers changed from: private */
    public static Context b;
    public ScheduledThreadPoolExecutor a;

    public final void a(Context context) {
        b = context;
        this.a = ThreadUtil.a();
    }

    public static void a(MsgDO msgDO, ExtraInfo extraInfo) {
        if (!TextUtils.isEmpty(msgDO.a) || !TextUtils.isEmpty(msgDO.c) || !TextUtils.isEmpty(msgDO.d)) {
            try {
                HashMap hashMap = new HashMap();
                hashMap.put(MtopJSParam.API, "agooAck");
                StringBuilder sb = new StringBuilder();
                sb.append(msgDO.a);
                sb.append(AUScreenAdaptTool.PREFIX_ID);
                sb.append(msgDO.e);
                hashMap.put("id", sb.toString());
                if (!TextUtils.isEmpty(msgDO.c)) {
                    hashMap.put("del_pack", msgDO.c);
                }
                if (!TextUtils.isEmpty(msgDO.d)) {
                    hashMap.put("ec", msgDO.d);
                }
                if (!TextUtils.isEmpty(msgDO.f)) {
                    hashMap.put("type", msgDO.f);
                }
                if (!TextUtils.isEmpty(msgDO.b)) {
                    hashMap.put(ProcessInfo.ALIAS_EXT, msgDO.b);
                }
                hashMap.put("appkey", Config.a(b));
                hashMap.put("utdid", AdapterUtilityImpl.getDeviceId(b));
                byte[] bytes = new JSONObject(hashMap).toString().getBytes("UTF-8");
                UTMini.getInstance().commitEvent(66002, "accs.ackMessage", AdapterUtilityImpl.getDeviceId(b), "handlerACKMessageSendData", msgDO.a);
                AppMonitorAdapter.commitCount("accs", BaseMonitor.COUNT_AGOO_ACK, "handlerACKMessage", 0.0d);
                AccsRequest accsRequest = new AccsRequest(null, "agooAck", bytes, null, null, null, null);
                accsRequest.setTag(msgDO.a);
                Context context = b;
                ALog.i("NotifManager", "handlerACKMessage,endRequest,dataId=".concat(String.valueOf(ACCSManager.getAccsInstance(context, Config.a(context), Config.b(b)).sendPushResponse(b, accsRequest, extraInfo))), new Object[0]);
            } catch (Throwable th) {
                if (ALog.isPrintLog(Level.E)) {
                    StringBuilder sb2 = new StringBuilder("handlerACKMessage Throwable,msgIds=");
                    sb2.append(msgDO.a);
                    sb2.append(",type=");
                    sb2.append(msgDO.f);
                    sb2.append(",e=");
                    sb2.append(th.toString());
                    ALog.e("NotifManager", sb2.toString(), new Object[0]);
                }
                UTMini.getInstance().commitEvent(66002, "accs.ackMessage", AdapterUtilityImpl.getDeviceId(b), "handlerACKMessageExceptionFailed", th.toString());
            }
        } else {
            UTMini instance = UTMini.getInstance();
            String deviceId = AdapterUtilityImpl.getDeviceId(b);
            StringBuilder sb3 = new StringBuilder("msgids=");
            sb3.append(msgDO.a);
            sb3.append(",removePacks=");
            sb3.append(msgDO.c);
            sb3.append(",errorCode=");
            sb3.append(msgDO.d);
            instance.commitEvent(66002, "accs.ackMessage", deviceId, "handlerACKMessageRetuen", sb3.toString());
        }
    }

    public static void b(MsgDO msgDO, ExtraInfo extraInfo) {
        if (!TextUtils.isEmpty(msgDO.j)) {
            try {
                if (Integer.parseInt(msgDO.j) >= -1) {
                    if (msgDO == null) {
                        ALog.e("NotifManager", "reportMethod msg null", new Object[0]);
                    } else {
                        AccsRequest accsRequest = new AccsRequest(null, "agooAck", b(msgDO), null, null, null, null);
                        accsRequest.setTag(msgDO.a);
                        Context context = b;
                        String sendPushResponse = ACCSManager.getAccsInstance(context, Config.a(context), Config.b(b)).sendPushResponse(b, accsRequest, extraInfo);
                        if (ALog.isPrintLog(Level.E)) {
                            ALog.e("NotifManager", "report", Constants.KEY_DATA_ID, sendPushResponse, "status", msgDO.l, "errorcode", msgDO.d);
                        }
                    }
                    if (!msgDO.m) {
                        AppMonitorAdapter.commitCount("accs", BaseMonitor.COUNT_AGOO_ACK, msgDO.l, 0.0d);
                    }
                }
            } catch (Throwable th) {
                ALog.e("NotifManager", "[report] is error", th, new Object[0]);
            }
        }
    }

    public static void a(MsgDO msgDO) {
        if (msgDO != null) {
            try {
                AppMonitorAdapter.commitCount("accs", BaseMonitor.COUNT_AGOO_REPORT_ID, msgDO.a, 0.0d);
                AccsRequest accsRequest = new AccsRequest(null, "agooAck", b(msgDO), null, null, null, null);
                Context context = b;
                IACCSManager accsInstance = ACCSManager.getAccsInstance(context, Config.a(context), Config.b(b));
                String sendRequest = accsInstance.sendRequest(b, accsRequest);
                accsInstance.sendPushResponse(b, accsRequest, null);
                if (ALog.isPrintLog(Level.E)) {
                    ALog.e("NotifManager", "reportNotifyMessage", Constants.KEY_DATA_ID, sendRequest, "status", msgDO.l);
                }
                AppMonitorAdapter.commitCount("accs", BaseMonitor.COUNT_AGOO_CLICK, msgDO.l, 0.0d);
                AppMonitorAdapter.commitCount("accs", BaseMonitor.COUNT_AGOO_ACK, msgDO.l, 0.0d);
            } catch (Throwable th) {
                ALog.e("NotifManager", "[reportNotifyMessage] is error", th, new Object[0]);
                AppMonitorAdapter.commitCount("accs", "error", th.toString(), 0.0d);
            }
        }
    }

    private static byte[] b(MsgDO msgDO) throws UnsupportedEncodingException {
        HashMap hashMap = new HashMap();
        hashMap.put(MtopJSParam.API, "agooReport");
        StringBuilder sb = new StringBuilder();
        sb.append(msgDO.a);
        sb.append(AUScreenAdaptTool.PREFIX_ID);
        sb.append(msgDO.e);
        hashMap.put("id", sb.toString());
        hashMap.put(ProcessInfo.ALIAS_EXT, msgDO.b);
        hashMap.put("status", msgDO.l);
        if (!TextUtils.isEmpty(msgDO.d)) {
            hashMap.put("ec", msgDO.d);
        }
        if (!TextUtils.isEmpty(msgDO.f)) {
            hashMap.put("type", msgDO.f);
        }
        if (!TextUtils.isEmpty(msgDO.g)) {
            hashMap.put("fromPkg", msgDO.g);
        }
        if (!TextUtils.isEmpty(msgDO.h)) {
            hashMap.put("fromAppkey", msgDO.h);
        }
        if (!TextUtils.isEmpty(msgDO.n)) {
            hashMap.put("notifyEnable", msgDO.n);
        }
        if (!TextUtils.isEmpty(msgDO.b)) {
            hashMap.put(ProcessInfo.ALIAS_EXT, msgDO.b);
        }
        hashMap.put("isStartProc", Boolean.toString(msgDO.k));
        hashMap.put("appkey", Config.a(b));
        hashMap.put("utdid", AdapterUtilityImpl.getDeviceId(b));
        return new JSONObject(hashMap).toString().getBytes("UTF-8");
    }

    public final void a(final String str, final String str2, final boolean z) {
        ThreadPoolExecutorFactory.schedule(new Runnable() {
            public void run() {
                String str;
                try {
                    HashMap hashMap = new HashMap();
                    hashMap.put("thirdTokenType", str2);
                    hashMap.put("token", str);
                    hashMap.put("appkey", Config.a(NotifManager.b));
                    hashMap.put("utdid", AdapterUtilityImpl.getDeviceId(NotifManager.b));
                    StringBuilder sb = new StringBuilder("report,utdid=");
                    sb.append(AdapterUtilityImpl.getDeviceId(NotifManager.b));
                    sb.append(",regId=");
                    sb.append(str);
                    sb.append(",type=");
                    sb.append(str2);
                    ALog.d("NotifManager", sb.toString(), new Object[0]);
                    AccsRequest accsRequest = new AccsRequest(null, "agooTokenReport", new JSONObject(hashMap).toString().getBytes("UTF-8"), null, null, null, null);
                    IACCSManager accsInstance = ACCSManager.getAccsInstance(NotifManager.b, Config.a(NotifManager.b), Config.b(NotifManager.b));
                    if (z) {
                        str = accsInstance.sendData(NotifManager.b, accsRequest);
                    } else {
                        str = accsInstance.sendPushResponse(NotifManager.b, accsRequest, new ExtraInfo());
                    }
                    if (ALog.isPrintLog(Level.D)) {
                        StringBuilder sb2 = new StringBuilder("reportThirdPushToken,dataId=");
                        sb2.append(str);
                        sb2.append(",regId=");
                        sb2.append(str);
                        sb2.append(",type=");
                        sb2.append(str2);
                        ALog.i("NotifManager", sb2.toString(), new Object[0]);
                    }
                } catch (Throwable th) {
                    UTMini.getInstance().commitEvent(66002, "reportThirdPushToken", AdapterUtilityImpl.getDeviceId(NotifManager.b), th.toString());
                    if (ALog.isPrintLog(Level.E)) {
                        ALog.e("NotifManager", "[report] is error", th, new Object[0]);
                    }
                }
            }
        }, 10, TimeUnit.SECONDS);
    }

    public static void a(String str) {
        try {
            HashMap hashMap = new HashMap();
            hashMap.put("pack", str);
            hashMap.put("appkey", Config.a(b));
            hashMap.put("utdid", AdapterUtilityImpl.getDeviceId(b));
            AccsRequest accsRequest = new AccsRequest(null, "agooKick", new JSONObject(hashMap).toString().getBytes("UTF-8"), null, null, null, null);
            Context context = b;
            ACCSManager.getAccsInstance(context, Config.a(context), Config.b(b)).sendPushResponse(b, accsRequest, new ExtraInfo());
        } catch (Throwable th) {
            ALog.e("NotifManager", "[doUninstall] is error", th, new Object[0]);
        }
    }

    /* access modifiers changed from: private */
    public static boolean d(String str) {
        PackageInfo packageInfo;
        try {
            if (TextUtils.isEmpty(str)) {
                return false;
            }
            packageInfo = b.getPackageManager().getPackageInfo(str, 0);
            if (packageInfo == null) {
                return false;
            }
            ALog.i("NotifManager", "isAppInstalled true..", new Object[0]);
            return true;
        } catch (Throwable unused) {
            packageInfo = null;
        }
    }

    /* access modifiers changed from: private */
    public static String e(String str) {
        try {
            if (TextUtils.isEmpty(str)) {
                return "null";
            }
            String str2 = b.getPackageManager().getPackageInfo(str, 0).versionName;
            ALog.d("NotifManager", "getVersion###版本号为 : ".concat(String.valueOf(str2)), new Object[0]);
            return str2;
        } catch (Throwable unused) {
            return "null";
        }
    }
}
