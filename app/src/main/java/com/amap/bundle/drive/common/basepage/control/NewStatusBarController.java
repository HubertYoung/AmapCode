package com.amap.bundle.drive.common.basepage.control;

import android.app.Application;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.text.TextUtils;
import com.amap.bundle.drive.ajx.inter.IStatusBarChange;
import com.amap.bundle.drive.ajx.module.ModuleDriveNavi;
import com.amap.bundle.drive.common.basepage.control.statusbar.EventBroadCast;
import com.amap.bundle.drive.common.basepage.control.statusbar.EventBroadCast.BroadEvent;
import com.amap.bundle.drive.common.basepage.control.statusbar.StatusBarBatteryStateReceiver;
import com.amap.bundle.drive.common.basepage.control.statusbar.StatusBarTimeBroadcastReceiver;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.common.Callback;
import com.autonavi.common.impl.Locator.Status;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import org.json.JSONObject;

public class NewStatusBarController implements IStatusBarChange, com.amap.bundle.drive.common.basepage.control.statusbar.EventBroadCast.a, com.amap.bundle.drive.common.basepage.control.statusbar.StatusBarBatteryStateReceiver.a, com.amap.bundle.drive.common.basepage.control.statusbar.StatusBarTimeBroadcastReceiver.a, Callback<Status> {
    private static final String KEY_BATTERY = "battery";
    private static final String KEY_BATTERYSTATE = "batteryState";
    private static final String KEY_BLUETOOTH = "bluetooth";
    private static final String KEY_TIMEINTERVAL = "timeInterval";
    private static final String KEY_VOLUMN = "volumn";
    private static final int STATUSBAR_STATE_BATTERYSTATE_CHARGING = 1;
    private static final int STATUSBAR_STATE_BATTERYSTATE_NORMAL = 0;
    private static final int STATUSBAR_STATE_BLUETOOTH_CONNECTED = 1;
    private static final int STATUSBAR_STATE_BLUETOOTH_DISCONNECTED = 0;
    private static final int STATUSBAR_STATE_BLUETOOTH_INIT = -1;
    private static final int STATUSBAR_STATE_GPS_INIT = -1;
    private static final int STATUSBAR_STATE_GPS_STRONG = 0;
    private static final int STATUSBAR_STATE_GPS_WEAK = 1;
    private static final String TAG = "StatusBarController";
    private int mBatteryPercent = 0;
    private StatusBarBatteryStateReceiver mBatteryReceiver = null;
    private EventBroadCast mEventBroadCast = null;
    private boolean mIsFirstBatteryChargingStateReceived = false;
    private ModuleDriveNavi mModuleRouteNaviCar;
    private AbstractBasePage mPage;
    private boolean mStateBatteryCharging = false;
    private int mStateBlutooth = -1;
    private int mStateGps = -1;
    private a mStatusBarInfoListener;
    private StatusBarTimeBroadcastReceiver mStatusBarTimeBroadcastReceiver = null;

    public interface a {
        void a(boolean z);
    }

    public void callback(Status status) {
    }

    public void error(Throwable th, boolean z) {
    }

    public NewStatusBarController(AbstractBasePage abstractBasePage, a aVar) {
        this.mPage = abstractBasePage;
        this.mStatusBarInfoListener = aVar;
    }

    public void initStatusBar() {
        registerEventBroadCast();
        this.mBatteryReceiver = new StatusBarBatteryStateReceiver();
        this.mBatteryReceiver.a = this;
        StatusBarBatteryStateReceiver statusBarBatteryStateReceiver = this.mBatteryReceiver;
        Application application = AMapAppGlobal.getApplication();
        if (application != null) {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.intent.action.BATTERY_CHANGED");
            try {
                application.registerReceiver(statusBarBatteryStateReceiver, intentFilter);
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
        this.mStatusBarTimeBroadcastReceiver = new StatusBarTimeBroadcastReceiver();
        this.mStatusBarTimeBroadcastReceiver.a = this;
        StatusBarTimeBroadcastReceiver statusBarTimeBroadcastReceiver = this.mStatusBarTimeBroadcastReceiver;
        Application application2 = AMapAppGlobal.getApplication();
        if (application2 != null) {
            IntentFilter intentFilter2 = new IntentFilter();
            intentFilter2.addAction("android.intent.action.TIME_TICK");
            intentFilter2.addAction("android.intent.action.TIMEZONE_CHANGED");
            intentFilter2.addAction("android.intent.action.DATE_CHANGED");
            intentFilter2.addAction("android.intent.action.TIME_SET");
            try {
                application2.registerReceiver(statusBarTimeBroadcastReceiver, intentFilter2);
            } catch (Throwable th2) {
                th2.printStackTrace();
            }
        }
    }

    public void releaseStatusBar() {
        unRegisterEventBroadCast();
        if (this.mBatteryReceiver != null) {
            this.mBatteryReceiver.a = null;
            StatusBarBatteryStateReceiver statusBarBatteryStateReceiver = this.mBatteryReceiver;
            Application application = AMapAppGlobal.getApplication();
            if (application != null) {
                try {
                    application.unregisterReceiver(statusBarBatteryStateReceiver);
                } catch (Throwable th) {
                    th.printStackTrace();
                }
            }
        }
        if (this.mStatusBarTimeBroadcastReceiver != null && ra.a(this.mPage)) {
            this.mStatusBarTimeBroadcastReceiver.a = null;
            StatusBarTimeBroadcastReceiver statusBarTimeBroadcastReceiver = this.mStatusBarTimeBroadcastReceiver;
            Application application2 = AMapAppGlobal.getApplication();
            if (application2 != null) {
                try {
                    application2.unregisterReceiver(statusBarTimeBroadcastReceiver);
                } catch (Throwable th2) {
                    th2.printStackTrace();
                }
            }
        }
    }

    public void setModuleRoute(ModuleDriveNavi moduleDriveNavi) {
        this.mModuleRouteNaviCar = moduleDriveNavi;
        this.mModuleRouteNaviCar.setStatusBarChangeListener(this);
    }

    private void registerEventBroadCast() {
        if (this.mEventBroadCast == null) {
            this.mEventBroadCast = new EventBroadCast();
            this.mEventBroadCast.a = this;
            AMapAppGlobal.getApplication().registerReceiver(this.mEventBroadCast, EventBroadCast.a());
        }
    }

    private void unRegisterEventBroadCast() {
        if (this.mEventBroadCast != null) {
            this.mEventBroadCast.a = null;
            AMapAppGlobal.getApplication().unregisterReceiver(this.mEventBroadCast);
            this.mEventBroadCast = null;
        }
    }

    public void onReceive(BroadEvent broadEvent, Intent intent) {
        if (broadEvent != null && intent != null) {
            if (TextUtils.equals(broadEvent.action(), BroadEvent.VOLUME_CHANGED.action())) {
                updateMuteState();
            } else if (TextUtils.equals(broadEvent.action(), BroadEvent.BLUETOOTH_CONNECTED.action())) {
                BluetoothDevice bluetoothDevice = (BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
                if (bluetoothDevice != null) {
                    BluetoothClass bluetoothClass = bluetoothDevice.getBluetoothClass();
                    if (bluetoothClass != null && bluetoothClass.hasService(2097152)) {
                        updateBluetoothState(true);
                        if (this.mStatusBarInfoListener != null) {
                            this.mStatusBarInfoListener.a(true);
                        }
                    }
                }
            } else if (TextUtils.equals(broadEvent.action(), BroadEvent.BLUETOOTH_DISCONNECTED.action())) {
                BluetoothDevice bluetoothDevice2 = (BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
                if (bluetoothDevice2 != null) {
                    BluetoothClass bluetoothClass2 = bluetoothDevice2.getBluetoothClass();
                    if (bluetoothClass2 != null && bluetoothClass2.hasService(2097152)) {
                        updateBluetoothState(false);
                        if (this.mStatusBarInfoListener != null) {
                            this.mStatusBarInfoListener.a(false);
                        }
                    }
                }
            } else {
                if (TextUtils.equals(broadEvent.action(), BroadEvent.BLUETOOTH_STATE_CHANGE.action()) && intent.getIntExtra("android.bluetooth.adapter.extra.STATE", 10) == 10) {
                    updateBluetoothState(false);
                    if (this.mStatusBarInfoListener != null) {
                        this.mStatusBarInfoListener.a(false);
                    }
                }
            }
        }
    }

    private void updateMuteState() {
        HashMap hashMap = new HashMap();
        hashMap.put(KEY_VOLUMN, Integer.valueOf(getVolumn()));
        JSONObject json = toJson(hashMap);
        if (json != null && this.mModuleRouteNaviCar != null) {
            this.mModuleRouteNaviCar.notifyStatusBarChange(json);
        }
    }

    private void updateBluetoothState(boolean z) {
        if (this.mStateBlutooth != -1) {
            if (z && this.mStateBlutooth == 1) {
                return;
            }
            if (!z && this.mStateBlutooth == 0) {
                return;
            }
        }
        this.mStateBlutooth = z ? 1 : 0;
        HashMap hashMap = new HashMap();
        hashMap.put(KEY_BLUETOOTH, Integer.valueOf(this.mStateBlutooth));
        JSONObject json = toJson(hashMap);
        if (json == null || this.mModuleRouteNaviCar == null) {
            this.mStateBlutooth = -1;
        } else {
            this.mModuleRouteNaviCar.notifyStatusBarChange(json);
        }
    }

    public void onBatteryNormal(int i) {
        this.mIsFirstBatteryChargingStateReceived = true;
        if (this.mStateBatteryCharging || this.mBatteryPercent != i) {
            this.mStateBatteryCharging = false;
            this.mBatteryPercent = i;
            HashMap hashMap = new HashMap();
            hashMap.put("battery", Integer.valueOf(i));
            hashMap.put(KEY_BATTERYSTATE, Integer.valueOf(0));
            JSONObject json = toJson(hashMap);
            log("batterStateChanged  onBatteryNormal    ".concat(String.valueOf(json)));
            if (!(json == null || this.mModuleRouteNaviCar == null)) {
                this.mModuleRouteNaviCar.notifyStatusBarChange(json);
            }
        }
    }

    public void onBatteryCharging(int i) {
        this.mIsFirstBatteryChargingStateReceived = true;
        StringBuilder sb = new StringBuilder("onBatteryCharging mStateBatteryCharging:");
        sb.append(this.mStateBatteryCharging);
        sb.append("   mBatteryPercent:");
        sb.append(this.mBatteryPercent);
        sb.append("  percent:");
        sb.append(i);
        log(sb.toString());
        if (!this.mStateBatteryCharging || this.mBatteryPercent != i) {
            this.mStateBatteryCharging = true;
            this.mBatteryPercent = i;
            HashMap hashMap = new HashMap();
            hashMap.put("battery", Integer.valueOf(i));
            hashMap.put(KEY_BATTERYSTATE, Integer.valueOf(1));
            JSONObject json = toJson(hashMap);
            log("onBatteryCharging    ".concat(String.valueOf(json)));
            if (!(json == null || this.mModuleRouteNaviCar == null)) {
                this.mModuleRouteNaviCar.notifyStatusBarChange(json);
            }
        }
    }

    public void onUpdate() {
        updateTimeState();
    }

    private void updateTimeState() {
        HashMap hashMap = new HashMap();
        hashMap.put(KEY_TIMEINTERVAL, Long.valueOf(getSystemTime()));
        JSONObject json = toJson(hashMap);
        if (json != null && this.mModuleRouteNaviCar != null) {
            this.mModuleRouteNaviCar.notifyStatusBarChange(json);
        }
    }

    private void updateGpsStatus(boolean z) {
        if (this.mStateGps != -1) {
            if (z && this.mStateGps == 1) {
                return;
            }
            if (!z && this.mStateGps == 0) {
                return;
            }
        }
        this.mStateGps = z ? 1 : 0;
        HashMap hashMap = new HashMap();
        hashMap.put("gpsLevel", Integer.valueOf(this.mStateGps));
        JSONObject json = toJson(hashMap);
        if (!(json == null || this.mModuleRouteNaviCar == null)) {
            this.mModuleRouteNaviCar.notifyStatusBarChange(json);
        }
    }

    private JSONObject toJson(Map<String, Object> map) {
        JSONObject jSONObject;
        if (map == null) {
            return null;
        }
        try {
            jSONObject = new JSONObject();
            try {
                for (Entry next : map.entrySet()) {
                    jSONObject.put((String) next.getKey(), next.getValue());
                }
            } catch (Exception e) {
                e = e;
                e.printStackTrace();
                return jSONObject;
            }
        } catch (Exception e2) {
            e = e2;
            jSONObject = null;
            e.printStackTrace();
            return jSONObject;
        }
        return jSONObject;
    }

    private void log(String str) {
        if (b.a) {
            AMapLog.i(TAG, "daihq".concat(String.valueOf(str)));
        }
        ku.a().c("NaviMonitor", "[StatusBarController] ".concat(String.valueOf(str)));
    }

    /* JADX WARNING: Removed duplicated region for block: B:11:0x003c  */
    /* JADX WARNING: Removed duplicated region for block: B:12:0x0047  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x002b  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void registeStatusBarInfoChange() {
        /*
            r5 = this;
            java.util.HashMap r0 = new java.util.HashMap
            r0.<init>()
            android.app.Application r1 = com.autonavi.amap.app.AMapAppGlobal.getApplication()
            java.lang.String r2 = "batterymanager"
            java.lang.Object r1 = r1.getSystemService(r2)
            android.os.BatteryManager r1 = (android.os.BatteryManager) r1
            int r2 = android.os.Build.VERSION.SDK_INT
            r3 = 0
            r4 = 23
            if (r2 < r4) goto L_0x0026
            boolean r2 = r1.isCharging()
            r4 = 4
            int r1 = r1.getIntProperty(r4)
            if (r1 == 0) goto L_0x0026
            r5.mBatteryPercent = r1
            goto L_0x0027
        L_0x0026:
            r2 = 0
        L_0x0027:
            boolean r1 = r5.mIsFirstBatteryChargingStateReceived
            if (r1 != 0) goto L_0x002d
            r5.mStateBatteryCharging = r2
        L_0x002d:
            java.lang.String r1 = "battery"
            int r2 = r5.mBatteryPercent
            java.lang.Integer r2 = java.lang.Integer.valueOf(r2)
            r0.put(r1, r2)
            boolean r1 = r5.mStateBatteryCharging
            if (r1 == 0) goto L_0x0047
            java.lang.String r1 = "batteryState"
            r2 = 1
            java.lang.Integer r2 = java.lang.Integer.valueOf(r2)
            r0.put(r1, r2)
            goto L_0x0050
        L_0x0047:
            java.lang.String r1 = "batteryState"
            java.lang.Integer r2 = java.lang.Integer.valueOf(r3)
            r0.put(r1, r2)
        L_0x0050:
            java.lang.String r1 = "volumn"
            int r2 = r5.getVolumn()
            java.lang.Integer r2 = java.lang.Integer.valueOf(r2)
            r0.put(r1, r2)
            java.lang.String r1 = "timeInterval"
            long r2 = r5.getSystemTime()
            java.lang.Long r2 = java.lang.Long.valueOf(r2)
            r0.put(r1, r2)
            android.app.Activity r1 = com.autonavi.amap.app.AMapAppGlobal.getTopActivity()
            boolean r1 = defpackage.tp.a(r1)
            r5.mStateBlutooth = r1
            java.lang.String r1 = "bluetooth"
            int r2 = r5.mStateBlutooth
            java.lang.Integer r2 = java.lang.Integer.valueOf(r2)
            r0.put(r1, r2)
            org.json.JSONObject r0 = r5.toJson(r0)
            if (r0 == 0) goto L_0x0090
            com.amap.bundle.drive.ajx.module.ModuleDriveNavi r1 = r5.mModuleRouteNaviCar
            if (r1 == 0) goto L_0x0090
            com.amap.bundle.drive.ajx.module.ModuleDriveNavi r1 = r5.mModuleRouteNaviCar
            r1.notifyStatusBarChange(r0)
        L_0x0090:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.amap.bundle.drive.common.basepage.control.NewStatusBarController.registeStatusBarInfoChange():void");
    }

    private long getSystemTime() {
        return System.currentTimeMillis() / 1000;
    }

    private int getVolumn() {
        AudioManager audioManager = (AudioManager) AMapAppGlobal.getApplication().getSystemService("audio");
        int streamMaxVolume = audioManager.getStreamMaxVolume(3);
        int streamVolume = audioManager.getStreamVolume(3);
        AMapLog.i(TAG, "daihq    MaxVolume:".concat(String.valueOf(streamMaxVolume)));
        AMapLog.i(TAG, "daihq    currentVolume:".concat(String.valueOf(streamVolume)));
        int i = (streamVolume * 100) / streamMaxVolume;
        AMapLog.i(TAG, "daihq    volumn:".concat(String.valueOf(i)));
        return i;
    }
}
