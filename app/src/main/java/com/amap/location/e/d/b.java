package com.amap.location.e.d;

import android.annotation.TargetApi;
import android.telephony.CellIdentityCdma;
import android.telephony.CellIdentityGsm;
import android.telephony.CellIdentityLte;
import android.telephony.CellIdentityWcdma;
import android.telephony.CellInfo;
import android.telephony.CellInfoCdma;
import android.telephony.CellInfoGsm;
import android.telephony.CellInfoLte;
import android.telephony.CellInfoWcdma;
import android.telephony.cdma.CdmaCellLocation;
import android.telephony.gsm.GsmCellLocation;
import android.text.TextUtils;
import com.alipay.mobile.mrtc.api.constants.APCallCode;
import com.amap.location.common.d.a;
import com.amap.location.common.model.CellState;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* compiled from: DataConversion */
public class b {
    public static int a(int i) {
        return (i * 2) + APCallCode.CALL_ERROR_INNER;
    }

    public static boolean b(int i) {
        return i >= 0 && i <= 32767;
    }

    private static boolean c(int i) {
        return i >= 0 && i <= 65535;
    }

    private static boolean d(int i) {
        return i >= 0 && i <= 268435455;
    }

    private static boolean e(int i) {
        return i > 0 && i <= 32767;
    }

    private static boolean f(int i) {
        return i >= 0 && i <= 65535;
    }

    private static boolean g(int i) {
        return i >= 0 && i <= 65535;
    }

    /* JADX WARNING: Removed duplicated region for block: B:18:0x0041  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.util.List<com.amap.location.common.model.CellState> a(android.telephony.CellLocation r8, java.util.List<android.telephony.NeighboringCellInfo> r9, java.lang.String r10, java.lang.Object r11) {
        /*
            if (r8 == 0) goto L_0x0075
            int[] r10 = a(r10)
            r0 = 0
            r1 = r10[r0]
            r2 = 1
            r10 = r10[r2]
            r3 = 0
            java.util.ArrayList r4 = new java.util.ArrayList
            r4.<init>()
            boolean r5 = r8 instanceof android.telephony.gsm.GsmCellLocation
            if (r5 == 0) goto L_0x0022
            android.telephony.gsm.GsmCellLocation r8 = (android.telephony.gsm.GsmCellLocation) r8
            com.amap.location.common.model.CellState r3 = a(r1, r10, r8)
            if (r3 == 0) goto L_0x0033
            r4.add(r3)
            goto L_0x0033
        L_0x0022:
            boolean r5 = r8 instanceof android.telephony.cdma.CdmaCellLocation
            if (r5 == 0) goto L_0x0033
            android.telephony.cdma.CdmaCellLocation r8 = (android.telephony.cdma.CdmaCellLocation) r8
            com.amap.location.common.model.CellState r3 = a(r1, r10, r8, r11)
            if (r3 == 0) goto L_0x0031
            r4.add(r3)
        L_0x0031:
            r8 = 0
            goto L_0x0034
        L_0x0033:
            r8 = 1
        L_0x0034:
            if (r3 == 0) goto L_0x0074
            if (r8 == 0) goto L_0x0074
            if (r9 == 0) goto L_0x0074
            int r8 = r9.size()
            r11 = 0
        L_0x003f:
            if (r11 >= r8) goto L_0x0074
            java.lang.Object r3 = r9.get(r11)
            android.telephony.NeighboringCellInfo r3 = (android.telephony.NeighboringCellInfo) r3
            if (r3 == 0) goto L_0x0071
            int r5 = r3.getLac()
            int r6 = r3.getCid()
            boolean r7 = a(r5, r6)
            if (r7 == 0) goto L_0x0071
            com.amap.location.common.model.CellState r7 = new com.amap.location.common.model.CellState
            r7.<init>(r2, r0, r0)
            r7.mcc = r1
            r7.mnc = r10
            r7.lac = r5
            r7.cid = r6
            int r3 = r3.getRssi()
            int r3 = a(r3)
            r7.signalStrength = r3
            r4.add(r7)
        L_0x0071:
            int r11 = r11 + 1
            goto L_0x003f
        L_0x0074:
            return r4
        L_0x0075:
            java.util.List r8 = java.util.Collections.emptyList()
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.amap.location.e.d.b.a(android.telephony.CellLocation, java.util.List, java.lang.String, java.lang.Object):java.util.List");
    }

    private static CellState a(int i, int i2, GsmCellLocation gsmCellLocation) {
        int lac = gsmCellLocation.getLac();
        int cid = gsmCellLocation.getCid();
        if (!a(lac, cid)) {
            return null;
        }
        CellState cellState = new CellState(1, true, false);
        cellState.mcc = i;
        cellState.mnc = i2;
        cellState.lac = lac;
        cellState.cid = cid;
        return cellState;
    }

    private static CellState a(int i, int i2, CdmaCellLocation cdmaCellLocation, Object obj) {
        int systemId = cdmaCellLocation.getSystemId();
        int networkId = cdmaCellLocation.getNetworkId();
        int baseStationId = cdmaCellLocation.getBaseStationId();
        if (a(systemId, networkId, baseStationId)) {
            CellState cellState = new CellState(2, true, false);
            cellState.mcc = i;
            cellState.mnc = i2;
            cellState.sid = systemId;
            cellState.nid = networkId;
            cellState.bid = baseStationId;
            cellState.latitude = cdmaCellLocation.getBaseStationLatitude();
            cellState.longitude = cdmaCellLocation.getBaseStationLongitude();
            return cellState;
        }
        if (obj != null) {
            try {
                Field declaredField = cdmaCellLocation.getClass().getDeclaredField("mGsmCellLoc");
                if (!declaredField.isAccessible()) {
                    declaredField.setAccessible(true);
                }
                return a(i, i2, (GsmCellLocation) declaredField.get(cdmaCellLocation));
            } catch (Throwable th) {
                a.a(th);
            }
        }
        return null;
    }

    @TargetApi(18)
    public static List<CellState> a(List<CellInfo> list, String str, com.amap.location.e.c.b bVar) {
        char c;
        Object obj;
        if (list == null) {
            return Collections.emptyList();
        }
        int size = list.size();
        ArrayList arrayList = new ArrayList();
        Object obj2 = null;
        char c2 = 0;
        for (int i = 0; i < size; i++) {
            CellInfo cellInfo = list.get(i);
            if (cellInfo != null) {
                boolean isRegistered = cellInfo.isRegistered();
                if (cellInfo instanceof CellInfoGsm) {
                    obj = a((CellInfoGsm) cellInfo, isRegistered);
                    c = 4;
                } else if (cellInfo instanceof CellInfoLte) {
                    obj = a((CellInfoLte) cellInfo, isRegistered);
                    c = 3;
                } else if (cellInfo instanceof CellInfoWcdma) {
                    obj = a((CellInfoWcdma) cellInfo, isRegistered);
                    c = 2;
                } else if (cellInfo instanceof CellInfoCdma) {
                    obj = a((CellInfoCdma) cellInfo, str, isRegistered);
                    c = 1;
                } else {
                    obj = null;
                    c = 0;
                }
                if (obj != null) {
                    if (!isRegistered) {
                        arrayList.add(obj);
                    } else if (c2 < c) {
                        if (obj2 != null) {
                            arrayList.add(obj2);
                        }
                        obj2 = obj;
                        c2 = c;
                    } else {
                        arrayList.add(obj);
                    }
                }
            }
        }
        if (obj2 != null) {
            arrayList.add(0, obj2);
        }
        return arrayList;
    }

    @TargetApi(17)
    private static CellState a(CellInfoGsm cellInfoGsm, boolean z) {
        CellIdentityGsm cellIdentity = cellInfoGsm.getCellIdentity();
        int lac = cellIdentity.getLac();
        int cid = cellIdentity.getCid();
        if (!a(lac, cid)) {
            return null;
        }
        CellState cellState = new CellState(1, z);
        cellState.mcc = cellIdentity.getMcc();
        cellState.mnc = cellIdentity.getMnc();
        cellState.lac = lac;
        cellState.cid = cid;
        cellState.signalStrength = cellInfoGsm.getCellSignalStrength().getDbm();
        return cellState;
    }

    @TargetApi(18)
    private static CellState a(CellInfoWcdma cellInfoWcdma, boolean z) {
        CellIdentityWcdma cellIdentity = cellInfoWcdma.getCellIdentity();
        int lac = cellIdentity.getLac();
        int cid = cellIdentity.getCid();
        int psc = cellIdentity.getPsc();
        if (psc < 0 || psc > 32767) {
            psc = 32767;
        }
        if (!a(lac, cid) && !b(psc)) {
            return null;
        }
        CellState cellState = new CellState(4, z);
        cellState.mcc = cellIdentity.getMcc();
        cellState.mnc = cellIdentity.getMnc();
        cellState.lac = lac;
        cellState.cid = cid;
        cellState.pci = (short) psc;
        cellState.signalStrength = cellInfoWcdma.getCellSignalStrength().getDbm();
        if (!a(lac, cid)) {
            cellState.lastUpdateUtcMills = -1;
        }
        return cellState;
    }

    @TargetApi(17)
    private static CellState a(CellInfoLte cellInfoLte, boolean z) {
        CellIdentityLte cellIdentity = cellInfoLte.getCellIdentity();
        int tac = cellIdentity.getTac();
        int ci = cellIdentity.getCi();
        int pci = cellIdentity.getPci();
        if (pci < 0 || pci > 32767) {
            pci = 32767;
        }
        if (!a(tac, ci) && !b(pci)) {
            return null;
        }
        CellState cellState = new CellState(3, z);
        cellState.mcc = cellIdentity.getMcc();
        cellState.mnc = cellIdentity.getMnc();
        cellState.lac = tac;
        cellState.cid = ci;
        cellState.pci = (short) pci;
        cellState.signalStrength = cellInfoLte.getCellSignalStrength().getDbm();
        if (!a(tac, ci)) {
            cellState.lastUpdateUtcMills = -1;
        }
        return cellState;
    }

    @TargetApi(17)
    private static CellState a(CellInfoCdma cellInfoCdma, String str, boolean z) {
        CellIdentityCdma cellIdentity = cellInfoCdma.getCellIdentity();
        int systemId = cellIdentity.getSystemId();
        int networkId = cellIdentity.getNetworkId();
        int basestationId = cellIdentity.getBasestationId();
        if (!a(systemId, networkId, basestationId)) {
            return null;
        }
        CellState cellState = new CellState(2, z);
        int[] a = a(str);
        cellState.mcc = a[0];
        cellState.mnc = a[1];
        cellState.sid = systemId;
        cellState.nid = networkId;
        cellState.bid = basestationId;
        cellState.latitude = cellIdentity.getLatitude();
        cellState.longitude = cellIdentity.getLongitude();
        cellState.signalStrength = cellInfoCdma.getCellSignalStrength().getCdmaDbm();
        return cellState;
    }

    private static int[] a(String str) {
        int[] iArr = {65535, 65535};
        if (TextUtils.isEmpty(str)) {
            return iArr;
        }
        try {
            iArr[0] = Integer.parseInt(str.substring(0, 3));
            iArr[1] = Integer.parseInt(str.substring(3));
        } catch (Exception unused) {
            iArr[0] = 65535;
            iArr[1] = 65535;
        }
        if (iArr[0] == 0 && iArr[1] == 0) {
            iArr[0] = 65535;
            iArr[1] = 65535;
        }
        return iArr;
    }

    public static boolean a(int i, int i2) {
        return c(i) && d(i2);
    }

    public static boolean a(int i, int i2, int i3) {
        return e(i) && f(i2) && g(i3);
    }
}
