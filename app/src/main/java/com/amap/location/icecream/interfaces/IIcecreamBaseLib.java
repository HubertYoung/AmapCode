package com.amap.location.icecream.interfaces;

import android.annotation.TargetApi;
import android.content.Context;
import android.location.GnssStatus.Callback;
import android.location.GpsStatus;
import android.location.GpsStatus.Listener;
import android.location.GpsStatus.NmeaListener;
import android.location.Location;
import android.location.LocationListener;
import android.location.OnNmeaMessageListener;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.os.Looper;
import android.support.annotation.RequiresApi;
import android.telephony.CellInfo;
import android.telephony.CellLocation;
import android.telephony.NeighboringCellInfo;
import android.telephony.PhoneStateListener;
import java.util.HashMap;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public interface IIcecreamBaseLib {
    public static final int LOCAL_INTERFACE_VERSION = 5;

    public interface IAmapBroadcastDispatcher {
        void addListener(IAmapBroadcastListener iAmapBroadcastListener);

        int getListenerCountByAction(int i);

        void handleMessage(int i, long j, long j2, Object obj);

        void removeListener(IAmapBroadcastListener iAmapBroadcastListener);
    }

    public interface IAmapBroadcastListener {
        public static final int BRD_MSG_NETWORK_FPS = 1;

        int getAction();

        void handleMessage(int i, long j, long j2, Object obj);
    }

    public interface IAmapLocationDispatcher {
        void addLocationListener(LocationListener locationListener);

        void onLocationChanged(Location location);

        void removeLocationListener(LocationListener locationListener);
    }

    public interface IAmapParamDispatcher {
        void addParamListener(IAmapParamListener iAmapParamListener);

        void onParamChanged(String str);

        void removeParamListener(IAmapParamListener iAmapParamListener);
    }

    public interface IAmapParamListener {
        void onParamChanged(String str);
    }

    public interface ICommon {
        void e(String str, String str2);

        void e(String str, String str2, boolean z);

        String getAdiu(Context context);

        String getImei(Context context);

        String getManufacturer();

        String getModel();

        String getTid(Context context);

        void i(String str, String str2);

        void i(String str, String str2, boolean z);

        String logEncode(String str);

        String longToMac(long j);

        long macToLong(String str);

        void trace(String str, String str2);
    }

    public interface ICore {
        String saos(String str, String str2, String str3);

        byte[] xxt(byte[] bArr, int i);
    }

    public interface INetWork {
        byte[] doHttpGet(String str, HashMap<String, String> hashMap);

        byte[] doHttpPost(String str, HashMap<String, String> hashMap, byte[] bArr);
    }

    public interface ISignal {
        boolean addGpsStatusListener(Listener listener, Looper looper);

        boolean addNmeaListener(NmeaListener nmeaListener, Looper looper);

        boolean addNmeaListener(OnNmeaMessageListener onNmeaMessageListener);

        boolean enableWifiAlwaysScan(Context context);

        @TargetApi(17)
        List<CellInfo> getAllCellInfo();

        List<String> getAllProviders();

        CellLocation getCellLocation();

        WifiInfo getConnectionInfo();

        GpsStatus getGpsStatus(GpsStatus gpsStatus);

        List<NeighboringCellInfo> getNeighboringCellInfo();

        String getNetworkOperator();

        int getNetworkType();

        int getPhoneType();

        List<ScanResult> getScanResults();

        int getWifiState();

        boolean isProviderEnabled(String str);

        boolean isScanAlwaysAvailable();

        boolean isWifiEnabled();

        void listen(PhoneStateListener phoneStateListener, int i, long j, Looper looper);

        @RequiresApi(api = 24)
        boolean registerGnssStatusCallback(Callback callback, Looper looper);

        void removeGpsStatusListener(Listener listener);

        void removeNmeaListener(NmeaListener nmeaListener);

        void removeNmeaListener(OnNmeaMessageListener onNmeaMessageListener);

        void removeUpdates(LocationListener locationListener);

        void requestLocationUpdates(String str, long j, float f, LocationListener locationListener, Looper looper);

        boolean startScan();

        @RequiresApi(api = 24)
        void unregisterGnssStatusCallback(Callback callback);
    }

    public interface IUptunnel {
        void addCount(int i);

        void execCMD(JSONArray jSONArray);

        void execCMD(JSONObject jSONObject);

        long getTableSize(int i);

        void reportBlockData(int i, byte[] bArr);

        void reportEvent(int i, byte[] bArr);

        void reportKeyLog(int i, byte[] bArr);

        void reportLog(int i, String str);
    }

    IAmapBroadcastDispatcher getAmapBroadcastInstance();

    IAmapLocationDispatcher getAmapLocationInstance();

    IAmapParamDispatcher getAmapParamInstance();

    ICommon getCommonInstance();

    ICore getCoreInstance();

    INetWork getNetWorkInstance();

    ISignal getSignalInstance();

    IUptunnel getUptunnelInstance();
}
