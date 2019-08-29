package com.autonavi.indoor.provider;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothAdapter.LeScanCallback;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresPermission;
import com.autonavi.indoor.constant.MessageCode;
import com.autonavi.indoor.entity.Beacon;
import com.autonavi.indoor.entity.ScanData;
import com.autonavi.indoor.entity.ScanPair;
import com.autonavi.indoor.util.BLEUtils;
import com.autonavi.indoor.util.L;
import com.autonavi.indoor.util.MacUtils;
import com.autonavi.indoor.util.MapUtils;
import com.autonavi.indoor.util.MathUtils;
import com.autonavi.indoor.util.MessageHelper;
import com.autonavi.indoor.util.Rsa;
import com.autonavi.widget.ui.BalloonLayout;
import java.lang.ref.WeakReference;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

@TargetApi(18)
public class BLEProvider extends IProvider {
    public static final int BLE_SCAN_NUM_RESTART_MIN_NUM = 8;
    public static final float BLE_SCAN_RESTART_RATE = 0.33f;
    public static final int MIN_BLE_SCAN_NUM_MAX = 10;
    public static final int MSG_BLE_ONLESCAN = 2200;
    private static volatile BLEProvider instance;
    /* access modifiers changed from: private */
    public HashMap<String, ArrayList<Integer>> cachedRSSI = new HashMap<>();
    private Map<String, String> encodedMap = new HashMap();
    ArrayList<Integer> histScanCount = new ArrayList<>(50);
    private LeScanCallback leScanCallback;
    private BLEHelper mBLEHelper;
    private final int mBleCheckInterval = 2000;
    private final int mBleReportInteval = 3000;
    /* access modifiers changed from: private */
    public BluetoothAdapter mBluetoothAdapter;
    List<Integer> mHisBleNum = new ArrayList();
    Map<String, Long> mHisScanData = new HashMap();
    int mLastBLEState = 10;
    long mLastLeScanCallbackTime = 0;
    long mLastReReregistTime = -1;
    long mLastUpdateTime = 0;
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @RequiresPermission("android.permission.BLUETOOTH_ADMIN")
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BLEProvider.this.mIsListening && !BLEProvider.this.mOutterHandlers.isEmpty() && action.equals("android.bluetooth.adapter.action.STATE_CHANGED")) {
                int intExtra = intent.getIntExtra("android.bluetooth.adapter.extra.STATE", Integer.MIN_VALUE);
                if ((intExtra == 10 || intExtra == 12) && intExtra != BLEProvider.this.mLastBLEState) {
                    BLEProvider.this.mLastBLEState = intExtra;
                    if (intExtra == 10) {
                        if (L.isLogging) {
                            L.d((String) "BLE closed, no ble LeScanCallback recived");
                        }
                        MessageHelper.publishMessage(BLEProvider.this.mOutterHandlers, 504);
                        if (MessageHelper.isValidHandler(BLEProvider.this.mInnerHandler)) {
                            BLEProvider.this.mInnerHandler.removeMessages(1200);
                        }
                    } else if (intExtra == 12) {
                        if (L.isLogging) {
                            L.d((String) "BLE opend, we should restart LeScanCallback");
                        }
                        BLEProvider.this.reRegisterReceiver();
                        if (MessageHelper.isValidHandler(BLEProvider.this.mInnerHandler)) {
                            BLEProvider.this.mInnerHandler.sendEmptyMessageDelayed(1200, 2000);
                        }
                    }
                }
            }
        }
    };
    long mStartScanTime;
    private final int statisticCount = 49;

    static class InnerHandler extends Handler {
        private final WeakReference<BLEProvider> mParent;

        public InnerHandler(BLEProvider bLEProvider) {
            this.mParent = new WeakReference<>(bLEProvider);
        }

        @RequiresPermission(allOf = {"android.permission.BLUETOOTH_ADMIN", "android.permission.BLUETOOTH"})
        public void handleMessage(Message message) {
            BLEProvider bLEProvider = (BLEProvider) this.mParent.get();
            if (bLEProvider != null) {
                if (message.what == 1200) {
                    bLEProvider.checkNeedReregistScan();
                    if (bLEProvider.mLastLeScanCallbackTime != 0 && System.currentTimeMillis() - bLEProvider.mLastLeScanCallbackTime > 6001) {
                        if (bLEProvider.mBluetoothAdapter == null || !bLEProvider.mBluetoothAdapter.isEnabled()) {
                            if (L.isLogging) {
                                StringBuilder sb = new StringBuilder("BLE not enabled in 9s. LastLeScanCallbackTime=");
                                sb.append(bLEProvider.mLastLeScanCallbackTime);
                                sb.append(", currentTimeMillis=");
                                sb.append(System.currentTimeMillis());
                                L.d(sb.toString());
                            }
                            MessageHelper.publishMessage(bLEProvider.mOutterHandlers, 504);
                        } else {
                            if (L.isLogging) {
                                StringBuilder sb2 = new StringBuilder("there is no ble scan in 9s. LastLeScanCallbackTime=");
                                sb2.append(bLEProvider.mLastLeScanCallbackTime);
                                sb2.append(", currentTimeMillis=");
                                sb2.append(System.currentTimeMillis());
                                L.d(sb2.toString());
                            }
                            MessageHelper.publishMessage(bLEProvider.mOutterHandlers, MessageCode.MSG_BLE_NO_SCAN);
                        }
                        bLEProvider.mLastLeScanCallbackTime = System.currentTimeMillis();
                    }
                    removeMessages(1200);
                    if (bLEProvider.mIsListening) {
                        sendEmptyMessageDelayed(1200, 2000);
                    }
                } else if (message.what == 2200) {
                    bLEProvider.mHisScanData.put((String) message.obj, Long.valueOf(System.currentTimeMillis()));
                }
            }
        }
    }

    static class MyLeScanCallback implements LeScanCallback {
        private final WeakReference<BLEProvider> mParent;

        public MyLeScanCallback(BLEProvider bLEProvider) {
            this.mParent = new WeakReference<>(bLEProvider);
        }

        @RequiresPermission(allOf = {"android.permission.BLUETOOTH_ADMIN", "android.permission.BLUETOOTH"})
        public void onLeScan(BluetoothDevice bluetoothDevice, int i, byte[] bArr) {
            BLEProvider bLEProvider = (BLEProvider) this.mParent.get();
            if (bLEProvider != null && bLEProvider.mIsListening) {
                bLEProvider.mLastLeScanCallbackTime = System.currentTimeMillis();
                String access$100 = bLEProvider.getID(bluetoothDevice, i, bArr);
                if (access$100.length() <= 1 || i >= 0) {
                    if (L.isLogging) {
                        L.d((String) "Invalid mac id or rssi");
                    }
                    return;
                }
                Handler handler = bLEProvider.mInnerHandler;
                if (MessageHelper.isValidHandler(handler)) {
                    handler.obtainMessage(2200, access$100).sendToTarget();
                    if (!bLEProvider.cachedRSSI.containsKey(access$100)) {
                        bLEProvider.cachedRSSI.put(access$100, new ArrayList());
                    }
                    ((ArrayList) bLEProvider.cachedRSSI.get(access$100)).add(Integer.valueOf(i));
                    long currentTimeMillis = System.currentTimeMillis();
                    if (currentTimeMillis - bLEProvider.mLastUpdateTime >= BalloonLayout.DEFAULT_DISPLAY_DURATION) {
                        ArrayList arrayList = new ArrayList();
                        for (Entry entry : bLEProvider.cachedRSSI.entrySet()) {
                            arrayList.add(new ScanPair((String) entry.getKey(), MathUtils.avg((ArrayList) entry.getValue())));
                        }
                        ScanData scanData = new ScanData(System.currentTimeMillis(), 1, arrayList);
                        if (L.isLogging) {
                            StringBuilder sb = new StringBuilder("pass BLE to host, scan size:");
                            sb.append(scanData.scans_.size());
                            L.d(sb.toString());
                        }
                        MessageHelper.publishMessage(bLEProvider.mOutterHandlers, 1202, (Object) scanData);
                        bLEProvider.cachedRSSI.clear();
                        bLEProvider.mLastUpdateTime = currentTimeMillis;
                    }
                }
            }
        }
    }

    public static BLEProvider getInstance() {
        if (instance == null) {
            synchronized (BLEProvider.class) {
                try {
                    if (instance == null) {
                        instance = new BLEProvider();
                    }
                }
            }
        }
        return instance;
    }

    protected BLEProvider() {
    }

    /* JADX WARNING: Code restructure failed: missing block: B:16:0x002c, code lost:
        return 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x0084, code lost:
        r5 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:0x0087, code lost:
        if (com.autonavi.indoor.util.L.isLogging != false) goto L_0x0089;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:48:0x0089, code lost:
        com.autonavi.indoor.util.L.d((java.lang.String) "Can't getSystemService of BLUETOOTH_SERVICE, BLE not work!");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:50:0x0090, code lost:
        if (com.autonavi.indoor.util.L.isLogging != false) goto L_0x0092;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:51:0x0092, code lost:
        com.autonavi.indoor.util.L.d(r5);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:52:0x0095, code lost:
        r4.mBluetoothAdapter = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:5:0x000b, code lost:
        r5 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:61:0x00ad, code lost:
        throw r5;
     */
    /* JADX WARNING: Exception block dominator not found, dom blocks: [B:2:0x0003, B:36:0x005e] */
    @android.annotation.TargetApi(18)
    @android.support.annotation.RequiresPermission("android.permission.BLUETOOTH")
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized int init(android.content.Context r5) {
        /*
            r4 = this;
            monitor-enter(r4)
            if (r5 != 0) goto L_0x000e
            java.lang.IllegalArgumentException r5 = new java.lang.IllegalArgumentException     // Catch:{ all -> 0x000b }
            java.lang.String r0 = "PedProvider context can not be initialized with null"
            r5.<init>(r0)     // Catch:{ all -> 0x000b }
            throw r5     // Catch:{ all -> 0x000b }
        L_0x000b:
            r5 = move-exception
            goto L_0x00ac
        L_0x000e:
            com.autonavi.indoor.provider.BLEHelper r0 = r4.mBLEHelper     // Catch:{ all -> 0x000b }
            if (r0 != 0) goto L_0x0019
            com.autonavi.indoor.provider.BLEHelper r0 = new com.autonavi.indoor.provider.BLEHelper     // Catch:{ all -> 0x000b }
            r0.<init>(r5)     // Catch:{ all -> 0x000b }
            r4.mBLEHelper = r0     // Catch:{ all -> 0x000b }
        L_0x0019:
            com.autonavi.indoor.provider.BLEHelper r0 = r4.mBLEHelper     // Catch:{ all -> 0x000b }
            boolean r0 = r0.hasBluetooth()     // Catch:{ all -> 0x000b }
            r1 = 0
            if (r0 != 0) goto L_0x002d
            boolean r5 = com.autonavi.indoor.util.L.isLogging     // Catch:{ all -> 0x000b }
            if (r5 == 0) goto L_0x002b
            java.lang.String r5 = "The phone don't support BLE."
            com.autonavi.indoor.util.L.d(r5)     // Catch:{ all -> 0x000b }
        L_0x002b:
            monitor-exit(r4)
            return r1
        L_0x002d:
            android.bluetooth.BluetoothAdapter$LeScanCallback r0 = r4.leScanCallback     // Catch:{ all -> 0x000b }
            if (r0 != 0) goto L_0x0038
            com.autonavi.indoor.provider.BLEProvider$MyLeScanCallback r0 = new com.autonavi.indoor.provider.BLEProvider$MyLeScanCallback     // Catch:{ all -> 0x000b }
            r0.<init>(r4)     // Catch:{ all -> 0x000b }
            r4.leScanCallback = r0     // Catch:{ all -> 0x000b }
        L_0x0038:
            com.autonavi.indoor.provider.BLEHelper r0 = r4.mBLEHelper     // Catch:{ all -> 0x000b }
            boolean r0 = r0.checkPermissions()     // Catch:{ all -> 0x000b }
            if (r0 != 0) goto L_0x004d
            boolean r5 = com.autonavi.indoor.util.L.isLogging     // Catch:{ all -> 0x000b }
            if (r5 == 0) goto L_0x0049
            java.lang.String r5 = "The phone don't allowe use BLE."
            com.autonavi.indoor.util.L.d(r5)     // Catch:{ all -> 0x000b }
        L_0x0049:
            r5 = 505(0x1f9, float:7.08E-43)
            monitor-exit(r4)
            return r5
        L_0x004d:
            android.content.Context r0 = r4.mContext     // Catch:{ all -> 0x000b }
            r2 = 504(0x1f8, float:7.06E-43)
            if (r0 != 0) goto L_0x009b
            boolean r0 = com.autonavi.indoor.util.L.isLogging     // Catch:{ all -> 0x000b }
            if (r0 == 0) goto L_0x005c
            java.lang.String r0 = "Initialize BleProvider"
            com.autonavi.indoor.util.L.d(r0)     // Catch:{ all -> 0x000b }
        L_0x005c:
            r4.mContext = r5     // Catch:{ all -> 0x000b }
            android.content.Context r5 = r4.mContext     // Catch:{ Throwable -> 0x0084 }
            java.lang.String r0 = "bluetooth"
            java.lang.Object r5 = r5.getSystemService(r0)     // Catch:{ Throwable -> 0x0084 }
            android.bluetooth.BluetoothManager r5 = (android.bluetooth.BluetoothManager) r5     // Catch:{ Throwable -> 0x0084 }
            android.bluetooth.BluetoothAdapter r5 = r5.getAdapter()     // Catch:{ Throwable -> 0x0084 }
            r4.mBluetoothAdapter = r5     // Catch:{ Throwable -> 0x0084 }
            android.bluetooth.BluetoothAdapter r5 = r4.mBluetoothAdapter     // Catch:{ Throwable -> 0x0084 }
            if (r5 == 0) goto L_0x007a
            android.bluetooth.BluetoothAdapter r5 = r4.mBluetoothAdapter     // Catch:{ Throwable -> 0x0084 }
            boolean r5 = r5.isEnabled()     // Catch:{ Throwable -> 0x0084 }
            if (r5 != 0) goto L_0x00a4
        L_0x007a:
            boolean r5 = com.autonavi.indoor.util.L.isLogging     // Catch:{ Throwable -> 0x0084 }
            if (r5 == 0) goto L_0x0098
            java.lang.String r5 = "Failed to get BluetoothManager from SystemService"
            com.autonavi.indoor.util.L.d(r5)     // Catch:{ Throwable -> 0x0084 }
            goto L_0x0098
        L_0x0084:
            r5 = move-exception
            boolean r0 = com.autonavi.indoor.util.L.isLogging     // Catch:{ all -> 0x000b }
            if (r0 == 0) goto L_0x008e
            java.lang.String r0 = "Can't getSystemService of BLUETOOTH_SERVICE, BLE not work!"
            com.autonavi.indoor.util.L.d(r0)     // Catch:{ all -> 0x000b }
        L_0x008e:
            boolean r0 = com.autonavi.indoor.util.L.isLogging     // Catch:{ all -> 0x000b }
            if (r0 == 0) goto L_0x0095
            com.autonavi.indoor.util.L.d(r5)     // Catch:{ all -> 0x000b }
        L_0x0095:
            r5 = 0
            r4.mBluetoothAdapter = r5     // Catch:{ all -> 0x000b }
        L_0x0098:
            r1 = 504(0x1f8, float:7.06E-43)
            goto L_0x00a4
        L_0x009b:
            boolean r5 = com.autonavi.indoor.util.L.isLogging     // Catch:{ all -> 0x000b }
            if (r5 == 0) goto L_0x00a4
            java.lang.String r5 = "Try to initialize PedProvider which had already been initialized before. To re-init PedProvider with new mConfiguration call PedProvider.destroy() at first."
            com.autonavi.indoor.util.L.d(r5)     // Catch:{ all -> 0x000b }
        L_0x00a4:
            long r2 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x000b }
            r4.mLastUpdateTime = r2     // Catch:{ all -> 0x000b }
            monitor-exit(r4)
            return r1
        L_0x00ac:
            monitor-exit(r4)
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.indoor.provider.BLEProvider.init(android.content.Context):int");
    }

    public void registerListener(Handler handler) {
        if (this.mBLEHelper == null || !this.mBLEHelper.hasBluetooth()) {
            if (L.isLogging) {
                L.d((String) "The phone don't support BLE.");
            }
            return;
        }
        super.registerListener(handler);
    }

    public void unregisterListener(Handler handler) {
        if (this.mBLEHelper == null || !this.mBLEHelper.hasBluetooth()) {
            if (L.isLogging) {
                L.d((String) "The phone don't support BLE.");
            }
            return;
        }
        super.unregisterListener(handler);
    }

    @RequiresPermission("android.permission.BLUETOOTH")
    public boolean isEnabled() {
        if (!this.mBLEHelper.hasBluetooth()) {
            if (L.isLogging) {
                L.d((String) " The phone don't support BLE!");
            }
            return false;
        } else if (this.mBluetoothAdapter == null || !this.mBLEHelper.isBluetoothEnabled()) {
            return false;
        } else {
            return true;
        }
    }

    /* access modifiers changed from: protected */
    @RequiresPermission(allOf = {"android.permission.BLUETOOTH_ADMIN", "android.permission.BLUETOOTH"})
    public int start() {
        if (L.isLogging) {
            L.d((String) " start ble scan");
        }
        if (this.mIsListening) {
            return 0;
        }
        if (!this.mBLEHelper.hasBluetooth()) {
            if (L.isLogging) {
                L.d((String) "The phone don't support BLE.");
            }
            return 504;
        } else if (this.mBluetoothAdapter == null) {
            if (L.isLogging) {
                L.d((String) "Can't getSystemService of BLUETOOTH_SERVICE, BLE not work!");
            }
            this.mLastBLEState = 10;
            return 504;
        } else {
            this.mLastBLEState = 12;
            this.mContext.registerReceiver(this.mReceiver, new IntentFilter("android.bluetooth.adapter.action.STATE_CHANGED"));
            if (!this.mBLEHelper.isBluetoothEnabled()) {
                if (L.isLogging) {
                    L.d((String) "BLE not useable.");
                }
                this.mLastBLEState = 10;
            }
            this.mLastUpdateTime = System.currentTimeMillis();
            this.mLastLeScanCallbackTime = System.currentTimeMillis();
            this.mIsListening = true;
            this.mInnerHandler = new InnerHandler(this);
            this.mInnerHandler.sendEmptyMessageDelayed(1200, 2000);
            this.mStartScanTime = System.currentTimeMillis();
            this.histScanCount.clear();
            this.mHisScanData.clear();
            this.mHisBleNum.clear();
            if (this.mBluetoothAdapter != null) {
                try {
                    boolean startLeScan = this.mBluetoothAdapter.startLeScan(this.leScanCallback);
                    if (L.isLogging) {
                        L.d("startLeScan status:".concat(String.valueOf(startLeScan)));
                    }
                } catch (Exception e) {
                    if (L.isLogging) {
                        StringBuilder sb = new StringBuilder("startLeScan Exception:");
                        sb.append(e.getMessage());
                        L.d(sb.toString());
                    }
                }
            }
            return 0;
        }
    }

    @RequiresPermission("android.permission.BLUETOOTH_ADMIN")
    public boolean stop() {
        if (!this.mIsListening) {
            return true;
        }
        this.mIsListening = false;
        this.mInnerHandler.removeMessages(1200);
        this.mInnerHandler = null;
        if (this.mBluetoothAdapter != null) {
            try {
                this.mBluetoothAdapter.stopLeScan(this.leScanCallback);
            } catch (Throwable th) {
                if (L.isLogging) {
                    L.d(th);
                }
            }
        }
        if (L.isLogging) {
            L.d((String) "---Stop Ble Scan----");
        }
        this.mContext.unregisterReceiver(this.mReceiver);
        return true;
    }

    /* access modifiers changed from: 0000 */
    @RequiresPermission(allOf = {"android.permission.BLUETOOTH_ADMIN", "android.permission.BLUETOOTH"})
    public void checkNeedReregistScan() {
        int GetBleScanNumber = GetBleScanNumber(3000);
        this.mHisBleNum.add(Integer.valueOf(GetBleScanNumber));
        if (this.mHisBleNum.size() > 20) {
            this.mHisBleNum.remove(0);
        }
        int i = 10;
        for (Integer next : this.mHisBleNum) {
            if (next.intValue() > i) {
                i = next.intValue();
            }
        }
        if (L.isLogging) {
            StringBuilder sb = new StringBuilder("checkNeedReregistScan, average_ble_scan=");
            sb.append(GetBleScanNumber);
            sb.append(", max_num=");
            sb.append(i);
            sb.append(", mHisScanData=");
            sb.append(this.mHisScanData.size());
            L.d(sb.toString());
        }
        if (((float) GetBleScanNumber) < ((float) i) * 0.33f && GetBleScanNumber < 8 && this.mHisScanData.size() > 0) {
            if (L.isLogging) {
                L.d((String) "reRegisterReceiver");
            }
            reRegisterReceiver();
            this.mHisScanData.clear();
            this.mLastReReregistTime = System.currentTimeMillis();
        }
        ClearBleScanNumber(50000);
    }

    /* access modifiers changed from: 0000 */
    public int GetBleScanNumber(int i) {
        long currentTimeMillis = System.currentTimeMillis();
        int i2 = 0;
        for (Entry<String, Long> value : this.mHisScanData.entrySet()) {
            if (currentTimeMillis - ((Long) value.getValue()).longValue() < ((long) i)) {
                i2++;
            }
        }
        return i2;
    }

    /* access modifiers changed from: 0000 */
    public void ClearBleScanNumber(int i) {
        long currentTimeMillis = System.currentTimeMillis();
        Iterator<Entry<String, Long>> it = this.mHisScanData.entrySet().iterator();
        while (it.hasNext()) {
            if (currentTimeMillis - ((Long) it.next().getValue()).longValue() >= ((long) i)) {
                it.remove();
            }
        }
    }

    /* access modifiers changed from: private */
    @RequiresPermission("android.permission.BLUETOOTH")
    public String getID(BluetoothDevice bluetoothDevice, int i, byte[] bArr) {
        String str;
        String CheckMTeamBeacon = CheckMTeamBeacon(bArr);
        if (CheckMTeamBeacon.length() == 16) {
            StringBuilder sb = new StringBuilder();
            sb.append(CheckMTeamBeacon);
            sb.append("0000000000000000_0000_0000");
            str = sb.toString();
        } else if (CheckMTeamBeacon.length() == 12) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(CheckMTeamBeacon);
            sb2.append("00000000000000000000_0000_0000");
            str = sb2.toString();
        } else {
            Beacon createFromScanData = BLEUtils.createFromScanData(bluetoothDevice, i, bArr);
            if (createFromScanData == null) {
                return "";
            }
            str = MacUtils.encodeBleID(createFromScanData);
        }
        return str;
    }

    private String CheckMTeamBeacon(byte[] bArr) {
        if (bArr == null || bArr.length <= 24) {
            if (L.isLogging) {
                L.d(bArr);
            }
            return "";
        }
        if (bArr[0] == 2 && bArr[1] == 1 && ((bArr[2] == 5 || bArr[2] == 6) && bArr[3] == 23)) {
            byte[] bArr2 = new byte[16];
            System.arraycopy(bArr, 9, bArr2, 0, 16);
            StringBuffer stringBuffer = new StringBuffer();
            for (int i = 0; i < 16; i++) {
                stringBuffer.append(String.format("%02X", new Object[]{Byte.valueOf(bArr2[i])}));
            }
            String stringBuffer2 = stringBuffer.toString();
            String str = this.encodedMap.get(stringBuffer2);
            if (str == null) {
                byte[] decrypt = Rsa.decrypt(Rsa.byteReverse(bArr2), new BigInteger("8021267762677846189778330391499"), new BigInteger("49549924105414102803086139689747"));
                if (decrypt == null || decrypt.length < 8) {
                    return "";
                }
                StringBuffer stringBuffer3 = new StringBuffer();
                for (int i2 = 6; i2 > 0; i2--) {
                    stringBuffer3.append(String.format("%02X", new Object[]{Byte.valueOf(decrypt[i2])}));
                }
                str = stringBuffer3.toString();
                this.encodedMap.put(stringBuffer2, str);
            }
            return str;
        }
        if (bArr[0] == 2 && bArr[1] == 1 && bArr[2] == 6 && bArr[3] == 22 && bArr[5] == -88 && bArr[6] == 1 && bArr[7] == 32) {
            try {
                byte[] decryptAES = MapUtils.decryptAES(MapUtils.copyOf(bArr, 10, 26), new byte[]{-1, -15, 55, 33, 4, 21, 16, 20, -85, 9, 0, 2, -91, -43, -59, -75});
                if (decryptAES != null) {
                    StringBuffer stringBuffer4 = new StringBuffer();
                    for (int i3 = 0; i3 < 8; i3++) {
                        stringBuffer4.append(String.format("%02X", new Object[]{Byte.valueOf(decryptAES[i3])}));
                    }
                    return stringBuffer4.toString();
                }
            } catch (Throwable th) {
                if (L.isLogging) {
                    L.d(th);
                }
            }
        }
        return "";
    }

    /* access modifiers changed from: 0000 */
    @RequiresPermission("android.permission.BLUETOOTH_ADMIN")
    public void reRegisterReceiver() {
        if (this.mBluetoothAdapter != null) {
            try {
                this.mBluetoothAdapter.stopLeScan(this.leScanCallback);
                if (!this.mBluetoothAdapter.startLeScan(this.leScanCallback)) {
                    if (L.isLogging) {
                        L.d((String) "restartLeScan failed!!");
                    } else if (L.isLogging) {
                        L.d((String) "restartLeScan success");
                    }
                }
            } catch (Throwable th) {
                if (L.isLogging) {
                    L.d(th);
                }
            }
        }
    }
}
