package com.alipay.android.phone.bluetoothsdk;

import android.util.SparseArray;
import java.util.List;
import java.util.Map;

public final class ScanRecord {
    private static final int DATA_TYPE_FLAGS = 1;
    private static final int DATA_TYPE_LOCAL_NAME_COMPLETE = 9;
    private static final int DATA_TYPE_LOCAL_NAME_SHORT = 8;
    private static final int DATA_TYPE_MANUFACTURER_SPECIFIC_DATA = 255;
    private static final int DATA_TYPE_SERVICE_DATA = 22;
    private static final int DATA_TYPE_SERVICE_UUIDS_128_BIT_COMPLETE = 7;
    private static final int DATA_TYPE_SERVICE_UUIDS_128_BIT_PARTIAL = 6;
    private static final int DATA_TYPE_SERVICE_UUIDS_16_BIT_COMPLETE = 3;
    private static final int DATA_TYPE_SERVICE_UUIDS_16_BIT_PARTIAL = 2;
    private static final int DATA_TYPE_SERVICE_UUIDS_32_BIT_COMPLETE = 5;
    private static final int DATA_TYPE_SERVICE_UUIDS_32_BIT_PARTIAL = 4;
    private static final int DATA_TYPE_TX_POWER_LEVEL = 10;
    private static final String TAG = "ScanRecord";
    private final int mAdvertiseFlags;
    private final byte[] mBytes;
    private final String mDeviceName;
    private final SparseArray<byte[]> mManufacturerSpecificData;
    private final Map<String, String> mServiceData;
    private final List<String> mServiceUuids;
    private final int mTxPowerLevel;

    public final int getAdvertiseFlags() {
        return this.mAdvertiseFlags;
    }

    public final List<String> getServiceUuids() {
        return this.mServiceUuids;
    }

    public final SparseArray<byte[]> getManufacturerSpecificData() {
        return this.mManufacturerSpecificData;
    }

    public final byte[] getManufacturerSpecificData(int manufacturerId) {
        return this.mManufacturerSpecificData.get(manufacturerId);
    }

    public final Map<String, String> getServiceData() {
        return this.mServiceData;
    }

    public final int getTxPowerLevel() {
        return this.mTxPowerLevel;
    }

    public final String getDeviceName() {
        return this.mDeviceName;
    }

    public final byte[] getBytes() {
        return this.mBytes;
    }

    private ScanRecord(List<String> serviceUuids, SparseArray<byte[]> manufacturerData, Map<String, String> serviceData, int advertiseFlags, int txPowerLevel, String localName, byte[] bytes) {
        this.mServiceUuids = serviceUuids;
        this.mManufacturerSpecificData = manufacturerData;
        this.mServiceData = serviceData;
        this.mDeviceName = localName;
        this.mAdvertiseFlags = advertiseFlags;
        this.mTxPowerLevel = txPowerLevel;
        this.mBytes = bytes;
    }

    /* JADX WARNING: Incorrect type for immutable var: ssa=byte, code=int, for r8v2, types: [byte] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.alipay.android.phone.bluetoothsdk.ScanRecord parseFromBytes(byte[] r26) {
        /*
            if (r26 != 0) goto L_0x0004
            r3 = 0
        L_0x0003:
            return r3
        L_0x0004:
            r18 = 0
            r7 = -1
            java.util.ArrayList r4 = new java.util.ArrayList
            r4.<init>()
            r9 = 0
            r8 = -2147483648(0xffffffff80000000, float:-0.0)
            android.util.SparseArray r5 = new android.util.SparseArray
            r5.<init>()
            java.util.HashMap r6 = new java.util.HashMap
            r6.<init>()
            r19 = r18
        L_0x001b:
            r0 = r26
            int r3 = r0.length     // Catch:{ Exception -> 0x004c }
            r0 = r19
            if (r0 >= r3) goto L_0x00e3
            int r18 = r19 + 1
            byte r3 = r26[r19]     // Catch:{ Exception -> 0x00f5 }
            r0 = r3 & 255(0xff, float:3.57E-43)
            r21 = r0
            if (r21 == 0) goto L_0x00e5
            int r20 = r21 + -1
            int r19 = r18 + 1
            byte r3 = r26[r18]     // Catch:{ Exception -> 0x004c }
            r3 = r3 & 255(0xff, float:3.57E-43)
            switch(r3) {
                case 1: goto L_0x003c;
                case 2: goto L_0x0041;
                case 3: goto L_0x0041;
                case 4: goto L_0x0078;
                case 5: goto L_0x0078;
                case 6: goto L_0x0083;
                case 7: goto L_0x0083;
                case 8: goto L_0x008f;
                case 9: goto L_0x008f;
                case 10: goto L_0x009f;
                case 22: goto L_0x00a2;
                case 255: goto L_0x00c2;
                default: goto L_0x0037;
            }     // Catch:{ Exception -> 0x004c }
        L_0x0037:
            int r18 = r19 + r20
            r19 = r18
            goto L_0x001b
        L_0x003c:
            byte r3 = r26[r19]     // Catch:{ Exception -> 0x004c }
            r7 = r3 & 255(0xff, float:3.57E-43)
            goto L_0x0037
        L_0x0041:
            r3 = 2
            r0 = r26
            r1 = r19
            r2 = r20
            parseServiceUuid(r0, r1, r2, r3, r4)     // Catch:{ Exception -> 0x004c }
            goto L_0x0037
        L_0x004c:
            r3 = move-exception
            r18 = r19
        L_0x004f:
            java.lang.String r3 = "ScanRecord"
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            java.lang.String r11 = "unable to parse scan record: "
            r10.<init>(r11)
            java.lang.String r11 = java.util.Arrays.toString(r26)
            java.lang.StringBuilder r10 = r10.append(r11)
            java.lang.String r10 = r10.toString()
            android.util.Log.e(r3, r10)
            com.alipay.android.phone.bluetoothsdk.ScanRecord r10 = new com.alipay.android.phone.bluetoothsdk.ScanRecord
            r11 = 0
            r12 = 0
            r13 = 0
            r14 = -1
            r15 = -2147483648(0xffffffff80000000, float:-0.0)
            r16 = 0
            r17 = r26
            r10.<init>(r11, r12, r13, r14, r15, r16, r17)
            r3 = r10
            goto L_0x0003
        L_0x0078:
            r3 = 4
            r0 = r26
            r1 = r19
            r2 = r20
            parseServiceUuid(r0, r1, r2, r3, r4)     // Catch:{ Exception -> 0x004c }
            goto L_0x0037
        L_0x0083:
            r3 = 16
            r0 = r26
            r1 = r19
            r2 = r20
            parseServiceUuid(r0, r1, r2, r3, r4)     // Catch:{ Exception -> 0x004c }
            goto L_0x0037
        L_0x008f:
            java.lang.String r9 = new java.lang.String     // Catch:{ Exception -> 0x004c }
            r0 = r26
            r1 = r19
            r2 = r20
            byte[] r3 = extractBytes(r0, r1, r2)     // Catch:{ Exception -> 0x004c }
            r9.<init>(r3)     // Catch:{ Exception -> 0x004c }
            goto L_0x0037
        L_0x009f:
            byte r8 = r26[r19]     // Catch:{ Exception -> 0x004c }
            goto L_0x0037
        L_0x00a2:
            r3 = 2
            r0 = r26
            r1 = r19
            byte[] r25 = extractBytes(r0, r1, r3)     // Catch:{ Exception -> 0x004c }
            int r3 = r19 + 2
            int r10 = r20 + -2
            r0 = r26
            byte[] r24 = extractBytes(r0, r3, r10)     // Catch:{ Exception -> 0x004c }
            java.lang.String r3 = com.alipay.android.phone.bluetoothsdk.BluetoothHelper.bytesToHexStringInReverse(r25)     // Catch:{ Exception -> 0x004c }
            java.lang.String r10 = com.alipay.android.phone.bluetoothsdk.BluetoothHelper.bytesToHexString(r24)     // Catch:{ Exception -> 0x004c }
            r6.put(r3, r10)     // Catch:{ Exception -> 0x004c }
            goto L_0x0037
        L_0x00c2:
            int r3 = r19 + 1
            byte r3 = r26[r3]     // Catch:{ Exception -> 0x004c }
            r3 = r3 & 255(0xff, float:3.57E-43)
            int r3 = r3 << 8
            byte r10 = r26[r19]     // Catch:{ Exception -> 0x004c }
            r10 = r10 & 255(0xff, float:3.57E-43)
            int r23 = r3 + r10
            int r3 = r19 + 2
            int r10 = r20 + -2
            r0 = r26
            byte[] r22 = extractBytes(r0, r3, r10)     // Catch:{ Exception -> 0x004c }
            r0 = r23
            r1 = r22
            r5.put(r0, r1)     // Catch:{ Exception -> 0x004c }
            goto L_0x0037
        L_0x00e3:
            r18 = r19
        L_0x00e5:
            boolean r3 = r4.isEmpty()     // Catch:{ Exception -> 0x00f5 }
            if (r3 == 0) goto L_0x00ec
            r4 = 0
        L_0x00ec:
            com.alipay.android.phone.bluetoothsdk.ScanRecord r3 = new com.alipay.android.phone.bluetoothsdk.ScanRecord     // Catch:{ Exception -> 0x00f5 }
            r10 = r26
            r3.<init>(r4, r5, r6, r7, r8, r9, r10)     // Catch:{ Exception -> 0x00f5 }
            goto L_0x0003
        L_0x00f5:
            r3 = move-exception
            goto L_0x004f
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.android.phone.bluetoothsdk.ScanRecord.parseFromBytes(byte[]):com.alipay.android.phone.bluetoothsdk.ScanRecord");
    }

    public final String toString() {
        return "ScanRecord [mAdvertiseFlags=" + this.mAdvertiseFlags + ", mServiceUuids=" + BluetoothLeUtils.toString(this.mServiceUuids) + ", mManufacturerSpecificData=" + BluetoothLeUtils.toString(this.mManufacturerSpecificData) + ", mServiceData=" + BluetoothLeUtils.toString(this.mServiceData) + ", mTxPowerLevel=" + this.mTxPowerLevel + ", mDeviceName=" + this.mDeviceName + "]";
    }

    private static int parseServiceUuid(byte[] scanRecord, int currentPos, int dataLength, int uuidLength, List<String> serviceUuids) {
        while (dataLength > 0) {
            byte[] uuidBytes = extractBytes(scanRecord, currentPos, uuidLength);
            if (uuidLength == 2 || uuidLength == 4) {
                serviceUuids.add(BluetoothHelper.bytesToHexStringInReverse(uuidBytes));
            } else {
                serviceUuids.add(BluetoothUuid.parseUuidFrom(uuidBytes).toString());
            }
            dataLength -= uuidLength;
            currentPos += uuidLength;
        }
        return currentPos;
    }

    private static byte[] extractBytes(byte[] scanRecord, int start, int length) {
        byte[] bytes = new byte[length];
        System.arraycopy(scanRecord, start, bytes, 0, length);
        return bytes;
    }
}
