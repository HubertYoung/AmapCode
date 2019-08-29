package com.taobao.accs.base;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import com.taobao.accs.utl.ALog;
import com.taobao.accs.utl.ALog.Level;
import java.io.Serializable;
import java.util.Map;

public abstract class TaoBaseService extends Service implements AccsDataListener {
    private static final String TAG = "TaoBaseService";
    private AccsAbstractDataListener mDefaultDataListener = new AccsAbstractDataListener() {
        public void onBind(String str, int i, ExtraInfo extraInfo) {
        }

        public void onData(String str, String str2, String str3, byte[] bArr, ExtraInfo extraInfo) {
        }

        public void onResponse(String str, String str2, int i, byte[] bArr, ExtraInfo extraInfo) {
        }

        public void onSendData(String str, String str2, int i, ExtraInfo extraInfo) {
        }

        public void onUnbind(String str, int i, ExtraInfo extraInfo) {
        }
    };
    private Messenger messenger = new Messenger(new Handler() {
        public void handleMessage(Message message) {
            if (message != null) {
                ALog.i(TaoBaseService.TAG, "handleMessage on receive msg", "msg", message.toString());
                Intent intent = (Intent) message.getData().getParcelable("intent");
                if (intent != null) {
                    ALog.i(TaoBaseService.TAG, "handleMessage get intent success", "intent", intent.toString());
                    TaoBaseService.this.onStartCommand(intent, 0, 0);
                }
            }
        }
    });

    public static class ConnectInfo implements Serializable {
        private static final long serialVersionUID = 8974674111758240362L;
        public boolean connected;
        public int errorCode;
        public String errordetail;
        public String host;
        public boolean isCenterHost;
        public boolean isInapp;

        public ConnectInfo(String str, boolean z, boolean z2) {
            this.host = str;
            this.isInapp = z;
            this.isCenterHost = z2;
        }

        public ConnectInfo(String str, boolean z, boolean z2, int i, String str2) {
            this.host = str;
            this.isInapp = z;
            this.isCenterHost = z2;
            this.errorCode = i;
            this.errordetail = str2;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder("ConnectInfo{");
            sb.append("host='");
            sb.append(this.host);
            sb.append('\'');
            sb.append(", isInapp=");
            sb.append(this.isInapp);
            sb.append(", isCenterHost=");
            sb.append(this.isCenterHost);
            sb.append(", connected=");
            sb.append(this.connected);
            sb.append(", errorCode=");
            sb.append(this.errorCode);
            sb.append(", errorDetail='");
            sb.append(this.errordetail);
            sb.append('\'');
            sb.append('}');
            return sb.toString();
        }
    }

    public enum ExtHeaderType {
        TYPE_BUSINESS,
        TYPE_SID,
        TYPE_USERID,
        TYPE_COOKIE,
        TYPE_TAG,
        TYPE_STATUS,
        TYPE_DELAY,
        TYPE_EXPIRE,
        TYPE_LOCATION,
        TYPE_UNIT,
        TYPE_NEED_BUSINESS_ACK;

        public static ExtHeaderType valueOf(int i) {
            switch (i) {
                case 0:
                    return TYPE_BUSINESS;
                case 1:
                    return TYPE_SID;
                case 2:
                    return TYPE_USERID;
                case 3:
                    return TYPE_COOKIE;
                case 4:
                    return TYPE_TAG;
                case 5:
                    return TYPE_STATUS;
                case 6:
                    return TYPE_DELAY;
                case 7:
                    return TYPE_EXPIRE;
                case 8:
                    return TYPE_LOCATION;
                case 9:
                    return TYPE_UNIT;
                case 10:
                    return TYPE_NEED_BUSINESS_ACK;
                default:
                    return null;
            }
        }
    }

    public static class ExtraInfo implements Serializable {
        public static final String EXT_HEADER = "ext_header";
        public int connType;
        public Map<ExtHeaderType, String> extHeader;
        public String fromHost;
        public String fromPackage;
        public Map<Integer, String> oriExtHeader;
    }

    public IBinder onBind(Intent intent) {
        return this.messenger.getBinder();
    }

    public void onAntiBrush(boolean z, ExtraInfo extraInfo) {
        this.mDefaultDataListener.onAntiBrush(z, extraInfo);
    }

    public void onDisconnected(ConnectInfo connectInfo) {
        this.mDefaultDataListener.onDisconnected(connectInfo);
    }

    public void onConnected(ConnectInfo connectInfo) {
        this.mDefaultDataListener.onConnected(connectInfo);
    }

    public void onCreate() {
        super.onCreate();
    }

    public void onDestroy() {
        super.onDestroy();
    }

    public int onStartCommand(Intent intent, int i, int i2) {
        if (ALog.isPrintLog(Level.D)) {
            ALog.d(TAG, "onStartCommand", "className", getClass().getSimpleName());
        }
        return AccsAbstractDataListener.onReceiveData(this, intent, this);
    }
}
