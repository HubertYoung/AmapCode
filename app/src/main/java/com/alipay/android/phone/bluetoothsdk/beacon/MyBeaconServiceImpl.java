package com.alipay.android.phone.bluetoothsdk.beacon;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.RemoteException;
import android.text.TextUtils;
import com.alipay.android.phone.androidannotations.utils.PermissionUtils;
import com.alipay.android.phone.bluetoothsdk.BluetoothHelper;
import com.alipay.android.phone.bluetoothsdk.BluetoothState;
import com.alipay.android.phone.bluetoothsdk.better.ble.ErrorConstants;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.Region;
import org.altbeacon.beacon.d;
import org.altbeacon.beacon.g;
import org.altbeacon.beacon.j;
import org.altbeacon.beacon.o;

public class MyBeaconServiceImpl extends MyBeaconService {
    private static final String IBEACON_LAYOUT = "m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24,d:25-25";
    private static final String TAG = "MyBeaconServiceImpl";
    /* access modifiers changed from: private */
    public List<MyBeacon> allBeaconList;
    private d beaconConsumer = new d() {
        public void onBeaconServiceConnect() {
            MyBeaconServiceImpl.this.beaconManager.a((o) new o() {
                public void didRangeBeaconsInRegion(Collection<Beacon> beacons, Region region) {
                    if (beacons != null) {
                        LoggerFactory.getTraceLogger().debug(MyBeaconServiceImpl.TAG, "found beacons, size:" + beacons.size());
                        MyBeaconServiceImpl.this.myBeaconList.clear();
                        for (Beacon beacon : beacons) {
                            LoggerFactory.getTraceLogger().debug(MyBeaconServiceImpl.TAG, "beacon:" + beacon.toString());
                            if (MyBeaconServiceImpl.this.filterUUIDList != null && MyBeaconServiceImpl.this.filterUUIDList.contains(beacon.b(0).b())) {
                                MyBeacon myBeacon = new MyBeacon(beacon.b(0).toString(), beacon.b(1).a(), beacon.b(2).a(), beacon.e(), beacon.f(), beacon.g());
                                MyBeaconServiceImpl.this.myBeaconList.add(myBeacon);
                                if (!MyBeaconServiceImpl.this.allBeaconList.contains(myBeacon)) {
                                    MyBeaconServiceImpl.this.allBeaconList.add(myBeacon);
                                }
                            }
                        }
                        if (MyBeaconServiceImpl.this.myBeaconListener != null && !MyBeaconServiceImpl.this.myBeaconList.isEmpty()) {
                            MyBeaconServiceImpl.this.myBeaconListener.onBeaconUpdate(MyBeaconServiceImpl.this.myBeaconList);
                        }
                    }
                }
            });
            try {
                MyBeaconServiceImpl.this.beaconManager.a(new Region(getApplicationContext().getPackageName()));
            } catch (RemoteException e) {
                LoggerFactory.getTraceLogger().debug(MyBeaconServiceImpl.TAG, "start ranging exception");
            }
        }

        public Context getApplicationContext() {
            return LauncherApplicationAgent.getInstance().getApplicationContext();
        }

        public void unbindService(ServiceConnection connection) {
            LoggerFactory.getTraceLogger().debug(MyBeaconServiceImpl.TAG, "unbindService");
            LauncherApplicationAgent.getInstance().getApplicationContext().unbindService(connection);
        }

        public boolean bindService(Intent intent, ServiceConnection connection, int mode) {
            LoggerFactory.getTraceLogger().debug(MyBeaconServiceImpl.TAG, "bindService");
            boolean bind = LauncherApplicationAgent.getInstance().getApplicationContext().bindService(intent, connection, mode);
            if (!bind) {
                MyBeaconServiceImpl.this.isDiscovering = false;
            }
            return bind;
        }
    };
    /* access modifiers changed from: private */
    public g beaconManager;
    private BroadcastReceiver bluetoothReceiver;
    /* access modifiers changed from: private */
    public List<UUID> filterUUIDList;
    /* access modifiers changed from: private */
    public boolean isDiscovering;
    /* access modifiers changed from: private */
    public List<MyBeacon> myBeaconList;
    /* access modifiers changed from: private */
    public MyBeaconListener myBeaconListener;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        initBeaconManager();
        this.myBeaconList = new ArrayList();
        this.allBeaconList = new ArrayList();
        this.filterUUIDList = new ArrayList();
    }

    /* access modifiers changed from: protected */
    public void onDestroy(Bundle bundle) {
        this.beaconManager = null;
    }

    private void initBeaconManager() {
        this.beaconManager = g.a((Context) LauncherApplicationAgent.getInstance().getApplicationContext());
    }

    public void setMyBeaconListener(MyBeaconListener myBeaconListener2) {
        this.myBeaconListener = myBeaconListener2;
    }

    public BeaconResult startBeaconDiscovery(String[] uuids) {
        if (this.isDiscovering) {
            return new BeaconResult(false, ErrorConstants.ERROR_BEACON_ALREADY_DISCOVERING);
        }
        if (!BluetoothHelper.isSupportBLE(getMicroApplicationContext().getApplicationContext())) {
            return new BeaconResult(false, ErrorConstants.ERROR_BEACON_UNSUPPORT);
        }
        if (BluetoothHelper.getBluetoothState() != BluetoothState.ON) {
            return new BeaconResult(false, ErrorConstants.ERROR_BEACON_BLUETOOTH_UNAVAILABLE);
        }
        if (!PermissionUtils.hasSelfPermissions(getMicroApplicationContext().getApplicationContext(), "android.permission.ACCESS_COARSE_LOCATION")) {
            if (!PermissionUtils.hasSelfPermissions(getMicroApplicationContext().getApplicationContext(), "android.permission.ACCESS_FINE_LOCATION")) {
                return new BeaconResult(false, ErrorConstants.ERROR_BEACON_LOCATION_FORBIDDEN);
            }
        }
        if (this.beaconManager == null) {
            initBeaconManager();
        }
        this.filterUUIDList.clear();
        if (uuids != null) {
            for (String uuidString : uuids) {
                if (TextUtils.isEmpty(uuidString)) {
                    return new BeaconResult(false, ErrorConstants.ERROR_BEACON_UUID_EMPTY);
                }
                UUID uuid = BluetoothHelper.getUUIDFromString(uuidString);
                if (uuid == null) {
                    return new BeaconResult(false, ErrorConstants.ERROR_BEACON_INVALID_UUID);
                }
                this.filterUUIDList.add(uuid);
            }
        }
        this.isDiscovering = true;
        LoggerFactory.getTraceLogger().debug(TAG, "isMainProcess:" + this.beaconManager.b());
        this.beaconManager.m();
        this.beaconManager.l();
        this.beaconManager.d().clear();
        this.beaconManager.d().add(new j().a((String) IBEACON_LAYOUT));
        this.beaconManager.a(this.beaconConsumer);
        registerReceiver();
        return new BeaconResult(true);
    }

    public BeaconResult stopBeaconDiscovery() {
        this.isDiscovering = false;
        this.filterUUIDList.clear();
        this.myBeaconList.clear();
        this.allBeaconList.clear();
        this.beaconManager.m();
        this.beaconManager.l();
        this.beaconManager.b(this.beaconConsumer);
        unregisterReceiver();
        return new BeaconResult(true);
    }

    public BeaconResult getBeacons() {
        BeaconResult beaconResult = new BeaconResult(true);
        beaconResult.obj = this.allBeaconList;
        return beaconResult;
    }

    private void registerReceiver() {
        if (this.bluetoothReceiver == null) {
            this.bluetoothReceiver = new BroadcastReceiver() {
                public void onReceive(Context context, Intent intent) {
                    switch (intent.getIntExtra("android.bluetooth.adapter.extra.STATE", Integer.MIN_VALUE)) {
                        case 10:
                            if (MyBeaconServiceImpl.this.myBeaconListener != null) {
                                MyBeaconServiceImpl.this.myBeaconListener.onBeaconServiceChange(false, MyBeaconServiceImpl.this.isDiscovering);
                                return;
                            }
                            return;
                        case 12:
                            if (MyBeaconServiceImpl.this.myBeaconListener != null) {
                                MyBeaconServiceImpl.this.myBeaconListener.onBeaconServiceChange(true, MyBeaconServiceImpl.this.isDiscovering);
                                return;
                            }
                            return;
                        default:
                            return;
                    }
                }
            };
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.bluetooth.adapter.action.STATE_CHANGED");
            getMicroApplicationContext().getApplicationContext().registerReceiver(this.bluetoothReceiver, intentFilter);
        }
    }

    private void unregisterReceiver() {
        LoggerFactory.getTraceLogger().debug(TAG, "unregisterReceiver");
        if (this.bluetoothReceiver != null) {
            getMicroApplicationContext().getApplicationContext().unregisterReceiver(this.bluetoothReceiver);
        }
        this.bluetoothReceiver = null;
    }
}
