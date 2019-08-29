package org.android.agoo.accs;

import android.text.TextUtils;
import com.taobao.accs.base.TaoBaseService;
import com.taobao.accs.base.TaoBaseService.ExtraInfo;
import com.taobao.accs.client.GlobalClientInfo;
import com.taobao.accs.common.Constants;
import com.taobao.accs.utl.ALog;
import com.taobao.accs.utl.ALog.Level;
import com.taobao.accs.utl.AdapterUtilityImpl;
import com.taobao.accs.utl.AppMonitorAdapter;
import com.taobao.accs.utl.BaseMonitor;
import com.taobao.accs.utl.UTMini;
import com.taobao.tao.remotebusiness.js.MtopJSBridge.MtopJSParam;
import java.nio.charset.Charset;
import org.android.agoo.common.CallBack;
import org.android.agoo.common.Config;
import org.android.agoo.control.AgooFactory;
import org.android.agoo.control.NotifManager;
import org.android.agoo.message.MessageService;
import org.json.JSONObject;

public class AgooService extends TaoBaseService {
    public static CallBack a;
    public static CallBack b;
    private AgooFactory c;

    public void onCreate() {
        super.onCreate();
        ALog.d("AgooService", "into--[onCreate]", new Object[0]);
        this.c = new AgooFactory();
        this.c.a(getApplicationContext(), (NotifManager) null, (MessageService) null);
    }

    public void onData(String str, String str2, String str3, byte[] bArr, ExtraInfo extraInfo) {
        if (ALog.isPrintLog(Level.I)) {
            StringBuilder sb = new StringBuilder("into--[onData]:serviceId:");
            sb.append(str);
            sb.append(",dataId=");
            sb.append(str3);
            ALog.i("AgooService", sb.toString(), new Object[0]);
            StringBuilder sb2 = new StringBuilder("push data:");
            sb2.append(new String(bArr, Charset.forName("UTF-8")));
            ALog.d("AgooService", sb2.toString(), new Object[0]);
        }
        UTMini.getInstance().commitEvent(66002, "accs.agooService", AdapterUtilityImpl.getDeviceId(getApplicationContext()), str3);
        try {
            this.c.a(bArr, (String) null);
            this.c.a(bArr, (String) "accs", extraInfo);
        } catch (Throwable th) {
            UTMini.getInstance().commitEvent(66002, "accs.agooService", "onDataError", th);
            ALog.e("AgooService", "into--[onData,dealMessage]:error:".concat(String.valueOf(th)), new Object[0]);
        }
    }

    public void onBind(String str, int i, ExtraInfo extraInfo) {
        if (ALog.isPrintLog(Level.E)) {
            StringBuilder sb = new StringBuilder("into--[onBind]:serviceId:");
            sb.append(str);
            sb.append(",errorCode=");
            sb.append(i);
            ALog.e("AgooService", sb.toString(), new Object[0]);
        }
        if (!(a == null || !GlobalClientInfo.AGOO_SERVICE_ID.equals(str) || i == 200)) {
            String.valueOf(i);
        }
        a = null;
    }

    public void onUnbind(String str, int i, ExtraInfo extraInfo) {
        if (ALog.isPrintLog(Level.E)) {
            StringBuilder sb = new StringBuilder("into--[onUnbind]:serviceId:");
            sb.append(str);
            sb.append(",errorCode=");
            sb.append(i);
            ALog.e("AgooService", sb.toString(), new Object[0]);
        }
        if (!(b == null || !GlobalClientInfo.AGOO_SERVICE_ID.equals(str) || i == 200)) {
            String.valueOf(i);
        }
        b = null;
    }

    public void onSendData(String str, String str2, int i, ExtraInfo extraInfo) {
        try {
            if (ALog.isPrintLog(Level.I)) {
                StringBuilder sb = new StringBuilder("onSendData,dataId=");
                sb.append(str2);
                sb.append(",errorCode=");
                sb.append(i);
                sb.append(",serviceId=");
                sb.append(str);
                ALog.i("AgooService", sb.toString(), new Object[0]);
            }
            if (i == 200) {
                if (TextUtils.equals("agooAck", str)) {
                    AppMonitorAdapter.commitCount("accs", BaseMonitor.COUNT_AGOO_SUCCESS_ACK, "8/9", 0.0d);
                }
                if (TextUtils.isEmpty(str) || !TextUtils.equals(str, "agooAck") || Long.parseLong(str2) <= 300000000 || Long.parseLong(str2) >= 600000000) {
                    if (!TextUtils.isEmpty(str) && TextUtils.equals(str, "agooAck") && Long.parseLong(str2) > 600000000 && ALog.isPrintLog(Level.I)) {
                        StringBuilder sb2 = new StringBuilder("onSendData,reportData=");
                        sb2.append(str2);
                        sb2.append(",serviceId=");
                        sb2.append(str);
                        ALog.i("AgooService", sb2.toString(), new Object[0]);
                    }
                } else if (ALog.isPrintLog(Level.I)) {
                    StringBuilder sb3 = new StringBuilder("onSendData,AckData=");
                    sb3.append(str2);
                    sb3.append(",serviceId=");
                    sb3.append(str);
                    ALog.i("AgooService", sb3.toString(), new Object[0]);
                }
            } else {
                if (TextUtils.equals("agooAck", str)) {
                    Config.c(getApplicationContext());
                    AppMonitorAdapter.commitCount("accs", BaseMonitor.COUNT_AGOO_FAIL_ACK, String.valueOf(i), 0.0d);
                }
                if (ALog.isPrintLog(Level.I)) {
                    StringBuilder sb4 = new StringBuilder("onSendData error,dataId=");
                    sb4.append(str2);
                    sb4.append(",serviceId=");
                    sb4.append(str);
                    ALog.i("AgooService", sb4.toString(), new Object[0]);
                    ALog.e("AgooService", "into--[parseError]", new Object[0]);
                }
                UTMini instance = UTMini.getInstance();
                String deviceId = AdapterUtilityImpl.getDeviceId(getApplicationContext());
                StringBuilder sb5 = new StringBuilder();
                sb5.append(str2);
                sb5.append(",serviceId=");
                sb5.append(str);
                sb5.append(",errorCode=");
                sb5.append(i);
                instance.commitEvent(66002, "accs.agooService", deviceId, "errorCode", sb5.toString());
            }
        } catch (Throwable th) {
            if (ALog.isPrintLog(Level.E)) {
                StringBuilder sb6 = new StringBuilder("onSendData exception,e=");
                sb6.append(th.getMessage());
                sb6.append(",e.getStackMsg=");
                sb6.append(a(th));
                ALog.e("AgooService", sb6.toString(), new Object[0]);
            }
            UTMini.getInstance().commitEvent(66002, "accs.agooService", AdapterUtilityImpl.getDeviceId(getApplicationContext()), "onSendDataException", a(th));
        }
    }

    public void onResponse(String str, String str2, int i, byte[] bArr, ExtraInfo extraInfo) {
        if (ALog.isPrintLog(Level.I)) {
            StringBuilder sb = new StringBuilder("onResponse,dataId=");
            sb.append(str2);
            sb.append(",errorCode=");
            sb.append(i);
            sb.append(",data=");
            sb.append(bArr);
            sb.append(",serviceId=");
            sb.append(str);
            ALog.i("AgooService", sb.toString(), new Object[0]);
        }
        Object obj = null;
        if (bArr != null) {
            try {
                if (bArr.length > 0) {
                    obj = new String(bArr, "utf-8");
                }
            } catch (Throwable th) {
                ALog.e("AgooService", "onResponse get data error,e=".concat(String.valueOf(th)), new Object[0]);
            }
        }
        if (ALog.isPrintLog(Level.D)) {
            ALog.d("AgooService", "onResponse,message=".concat(String.valueOf(obj)), new Object[0]);
        }
        if (i == 200 && TextUtils.equals(str, "agooAck")) {
            if (ALog.isPrintLog(Level.E)) {
                ALog.e("AgooService", "request is success", Constants.KEY_DATA_ID, str2);
            }
            AgooFactory agooFactory = this.c;
            agooFactory.b.execute(new Runnable(bArr) {
                final /* synthetic */ byte[] a;
                final /* synthetic */ boolean b = true;

                {
                    this.a = r2;
                }

                public void run() {
                    try {
                        String str = new String(this.a, "utf-8");
                        if (TextUtils.isEmpty(str)) {
                            AppMonitorAdapter.commitCount("accs", BaseMonitor.COUNT_AGOO_FAIL_ACK, "msg==null", 0.0d);
                            return;
                        }
                        ALog.i("AgooFactory", "message = ".concat(String.valueOf(str)), new Object[0]);
                        JSONObject jSONObject = new JSONObject(str);
                        String str2 = null;
                        String string = jSONObject.getString(MtopJSParam.API);
                        String string2 = jSONObject.getString("id");
                        if (TextUtils.equals(string, "agooReport")) {
                            str2 = jSONObject.getString("status");
                        }
                        if (TextUtils.equals(string, "agooAck")) {
                            AppMonitorAdapter.commitCount("accs", BaseMonitor.COUNT_AGOO_SUCCESS_ACK, "handlerACKMessage", 0.0d);
                        }
                        if (!TextUtils.isEmpty(string) && !TextUtils.isEmpty(string2)) {
                            if (!TextUtils.isEmpty(str2)) {
                                if (ALog.isPrintLog(Level.I)) {
                                    StringBuilder sb = new StringBuilder("updateMsg data begin,api=");
                                    sb.append(string);
                                    sb.append(",id=");
                                    sb.append(string2);
                                    sb.append(",status=");
                                    sb.append(str2);
                                    sb.append(",reportTimes=");
                                    sb.append(Config.f(AgooFactory.d));
                                    ALog.i("AgooFactory", sb.toString(), new Object[0]);
                                }
                                if (TextUtils.equals(string, "agooReport")) {
                                    if (TextUtils.equals(str2, "4") && this.b) {
                                        AgooFactory.this.c.a(string2, (String) "1");
                                    } else if ((TextUtils.equals(str2, "8") || TextUtils.equals(str2, "9")) && this.b) {
                                        AgooFactory.this.c.a(string2, (String) "100");
                                    }
                                    AppMonitorAdapter.commitCount("accs", BaseMonitor.COUNT_AGOO_SUCCESS_ACK, str2, 0.0d);
                                }
                                return;
                            }
                        }
                        AppMonitorAdapter.commitCount("accs", BaseMonitor.COUNT_AGOO_FAIL_ACK, "json key null", 0.0d);
                    } catch (Throwable th) {
                        ALog.e("AgooFactory", "updateMsg get data error,e=".concat(String.valueOf(th)), new Object[0]);
                        AppMonitorAdapter.commitCount("accs", BaseMonitor.COUNT_AGOO_FAIL_ACK, "json exception", 0.0d);
                    }
                }
            });
        } else if (i == 200 || !TextUtils.equals(str, "agooAck")) {
            if (ALog.isPrintLog(Level.E)) {
                ALog.e("AgooService", "business request is error,message=".concat(String.valueOf(obj)), new Object[0]);
            }
        } else {
            if (ALog.isPrintLog(Level.E)) {
                ALog.e("AgooService", "request is error", Constants.KEY_DATA_ID, str2, "errorid", Integer.valueOf(i));
            }
            Config.c(getApplicationContext());
            AppMonitorAdapter.commitCount("accs", BaseMonitor.COUNT_AGOO_FAIL_ACK, String.valueOf(i), 0.0d);
        }
    }

    public void onDestroy() {
        super.onDestroy();
    }

    private static String a(Throwable th) {
        StringBuffer stringBuffer = new StringBuffer();
        StackTraceElement[] stackTrace = th.getStackTrace();
        if (stackTrace != null && stackTrace.length > 0) {
            for (StackTraceElement stackTraceElement : stackTrace) {
                StringBuilder sb = new StringBuilder();
                sb.append(stackTraceElement.toString());
                sb.append("\n");
                stringBuffer.append(sb.toString());
            }
        }
        return stringBuffer.toString();
    }
}
