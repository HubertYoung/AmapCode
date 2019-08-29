package com.amap.location.b.f;

import android.content.ContentResolver;
import android.content.Context;
import android.os.Build.VERSION;
import android.provider.Settings.Global;
import android.provider.Settings.System;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.telephony.CellIdentityCdma;
import android.telephony.CellIdentityGsm;
import android.telephony.CellIdentityLte;
import android.telephony.CellIdentityWcdma;
import android.telephony.CellInfo;
import android.telephony.CellInfoCdma;
import android.telephony.CellInfoGsm;
import android.telephony.CellInfoLte;
import android.telephony.CellInfoWcdma;
import android.telephony.CellLocation;
import android.telephony.NeighboringCellInfo;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import android.telephony.cdma.CdmaCellLocation;
import android.telephony.gsm.GsmCellLocation;
import com.alipay.mobile.mrtc.api.constants.APCallCode;
import com.amap.location.b.c.c;
import com.amap.location.b.c.h;
import com.amap.location.b.c.i;
import com.amap.location.b.c.k;
import com.amap.location.common.d.a;
import java.util.Iterator;
import java.util.List;

/* compiled from: CellUtil */
public class b {
    private static int a(int i) {
        return (i * 2) + APCallCode.CALL_ERROR_INNER;
    }

    public static void a(@NonNull Context context, @NonNull com.amap.location.b.c.b bVar, @Nullable CellLocation cellLocation, @Nullable SignalStrength signalStrength, @Nullable List<CellInfo> list) {
        List list2;
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
        bVar.a(a(telephonyManager), b(telephonyManager));
        if (cellLocation != null) {
            if (cellLocation instanceof GsmCellLocation) {
                try {
                    list2 = telephonyManager.getNeighboringCellInfo();
                } catch (Throwable th) {
                    a.a(th);
                }
                a(bVar, cellLocation, signalStrength, list2);
            }
            list2 = null;
            a(bVar, cellLocation, signalStrength, list2);
        }
        if (list != null) {
            a(bVar, list);
        }
    }

    private static byte a(TelephonyManager telephonyManager) {
        try {
            return (byte) telephonyManager.getNetworkType();
        } catch (Throwable th) {
            a.a(th);
            return 0;
        }
    }

    private static String b(TelephonyManager telephonyManager) {
        String str;
        try {
            str = telephonyManager.getNetworkOperator();
        } catch (Throwable th) {
            a.a(th);
            str = null;
        }
        return str == null ? "" : str;
    }

    public static CellInfo a(List<CellInfo> list) {
        CellInfo cellInfo;
        if (VERSION.SDK_INT < 17) {
            return null;
        }
        if (list != null) {
            Iterator<CellInfo> it = list.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                cellInfo = it.next();
                if ((((cellInfo instanceof CellInfoLte) || (cellInfo instanceof CellInfoCdma) || (cellInfo instanceof CellInfoGsm)) && cellInfo.isRegistered()) || (VERSION.SDK_INT >= 18 && (cellInfo instanceof CellInfoWcdma) && cellInfo.isRegistered())) {
                    break;
                }
            }
            return cellInfo;
        }
        cellInfo = null;
        return cellInfo;
    }

    private static void a(@NonNull com.amap.location.b.c.b bVar, @NonNull CellLocation cellLocation, @Nullable SignalStrength signalStrength, @Nullable List<NeighboringCellInfo> list) {
        int i;
        if (cellLocation instanceof GsmCellLocation) {
            GsmCellLocation gsmCellLocation = (GsmCellLocation) cellLocation;
            T hVar = new h();
            hVar.c = gsmCellLocation.getLac();
            hVar.d = gsmCellLocation.getCid();
            if (VERSION.SDK_INT >= 9) {
                hVar.i = gsmCellLocation.getPsc();
            }
            if (signalStrength != null) {
                int gsmSignalStrength = signalStrength.getGsmSignalStrength();
                if (gsmSignalStrength == 99) {
                    i = Integer.MAX_VALUE;
                } else {
                    i = a(gsmSignalStrength);
                }
                hVar.e = i;
            }
            c cVar = new c();
            cVar.a = 1;
            cVar.f = hVar;
            cVar.b = 1;
            cVar.c = 0;
            bVar.c.add(cVar);
            if (list != null) {
                for (NeighboringCellInfo next : list) {
                    T hVar2 = new h();
                    hVar2.c = next.getLac();
                    hVar2.d = next.getCid();
                    hVar2.e = next.getRssi();
                    hVar2.i = next.getPsc();
                    c cVar2 = new c();
                    cVar2.a = 1;
                    cVar2.f = hVar2;
                    cVar2.b = 0;
                    cVar2.c = 0;
                    bVar.c.add(cVar2);
                }
            }
            return;
        }
        if (cellLocation instanceof CdmaCellLocation) {
            CdmaCellLocation cdmaCellLocation = (CdmaCellLocation) cellLocation;
            T aVar = new com.amap.location.b.c.a();
            aVar.d = cdmaCellLocation.getBaseStationLatitude();
            aVar.e = cdmaCellLocation.getBaseStationLongitude();
            aVar.a = cdmaCellLocation.getSystemId();
            aVar.b = cdmaCellLocation.getNetworkId();
            aVar.c = cdmaCellLocation.getBaseStationId();
            if (signalStrength != null) {
                aVar.f = signalStrength.getCdmaDbm();
            }
            c cVar3 = new c();
            cVar3.a = 2;
            cVar3.f = aVar;
            cVar3.b = 1;
            cVar3.c = 0;
            bVar.c.add(cVar3);
        }
    }

    private static void a(@NonNull com.amap.location.b.c.b bVar, @NonNull List<CellInfo> list) {
        if (VERSION.SDK_INT >= 17) {
            for (CellInfo next : list) {
                if (next instanceof CellInfoCdma) {
                    CellInfoCdma cellInfoCdma = (CellInfoCdma) next;
                    CellIdentityCdma cellIdentity = cellInfoCdma.getCellIdentity();
                    T aVar = new com.amap.location.b.c.a();
                    aVar.d = cellIdentity.getLatitude();
                    aVar.e = cellIdentity.getLongitude();
                    aVar.a = cellIdentity.getSystemId();
                    aVar.b = cellIdentity.getNetworkId();
                    aVar.c = cellIdentity.getBasestationId();
                    aVar.f = cellInfoCdma.getCellSignalStrength().getCdmaDbm();
                    aVar.g = cellInfoCdma.getCellSignalStrength().getAsuLevel();
                    c cVar = new c();
                    cVar.a = 2;
                    cVar.f = aVar;
                    cVar.b = next.isRegistered() ? (byte) 1 : 0;
                    cVar.c = 1;
                    bVar.c.add(cVar);
                } else if (next instanceof CellInfoGsm) {
                    CellInfoGsm cellInfoGsm = (CellInfoGsm) next;
                    CellIdentityGsm cellIdentity2 = cellInfoGsm.getCellIdentity();
                    T hVar = new h();
                    hVar.a = cellIdentity2.getMcc();
                    hVar.b = cellIdentity2.getMnc();
                    hVar.c = cellIdentity2.getLac();
                    hVar.d = cellIdentity2.getCid();
                    hVar.e = cellInfoGsm.getCellSignalStrength().getDbm();
                    hVar.h = cellInfoGsm.getCellSignalStrength().getAsuLevel();
                    if (VERSION.SDK_INT >= 24) {
                        hVar.f = cellIdentity2.getArfcn();
                        hVar.g = cellIdentity2.getBsic();
                    }
                    c cVar2 = new c();
                    cVar2.a = 1;
                    cVar2.f = hVar;
                    cVar2.b = next.isRegistered() ? (byte) 1 : 0;
                    cVar2.c = 1;
                    bVar.c.add(cVar2);
                } else if (next instanceof CellInfoLte) {
                    CellInfoLte cellInfoLte = (CellInfoLte) next;
                    CellIdentityLte cellIdentity3 = cellInfoLte.getCellIdentity();
                    T iVar = new i();
                    iVar.a = cellIdentity3.getMcc();
                    iVar.b = cellIdentity3.getMnc();
                    iVar.c = cellIdentity3.getTac();
                    iVar.d = cellIdentity3.getCi();
                    iVar.e = cellIdentity3.getPci();
                    iVar.f = cellInfoLte.getCellSignalStrength().getDbm();
                    iVar.h = cellInfoLte.getCellSignalStrength().getAsuLevel();
                    if (VERSION.SDK_INT >= 24) {
                        iVar.g = cellIdentity3.getEarfcn();
                    }
                    c cVar3 = new c();
                    cVar3.a = 3;
                    cVar3.f = iVar;
                    cVar3.b = next.isRegistered() ? (byte) 1 : 0;
                    cVar3.c = 1;
                    bVar.c.add(cVar3);
                } else if (VERSION.SDK_INT >= 18 && (next instanceof CellInfoWcdma)) {
                    CellInfoWcdma cellInfoWcdma = (CellInfoWcdma) next;
                    CellIdentityWcdma cellIdentity4 = cellInfoWcdma.getCellIdentity();
                    T kVar = new k();
                    kVar.a = cellIdentity4.getMcc();
                    kVar.b = cellIdentity4.getMnc();
                    kVar.c = cellIdentity4.getLac();
                    kVar.d = cellIdentity4.getCid();
                    kVar.e = cellIdentity4.getPsc();
                    kVar.f = cellInfoWcdma.getCellSignalStrength().getDbm();
                    kVar.h = cellInfoWcdma.getCellSignalStrength().getAsuLevel();
                    if (VERSION.SDK_INT >= 24) {
                        kVar.g = cellIdentity4.getUarfcn();
                    }
                    c cVar4 = new c();
                    cVar4.a = 4;
                    cVar4.f = kVar;
                    cVar4.b = next.isRegistered() ? (byte) 1 : 0;
                    cVar4.c = 1;
                    bVar.c.add(cVar4);
                }
            }
        }
    }

    public static boolean a(CellLocation cellLocation, CellLocation cellLocation2) {
        if (cellLocation == cellLocation2) {
            return true;
        }
        if (cellLocation == null || cellLocation2 == null) {
            return false;
        }
        if ((cellLocation instanceof GsmCellLocation) && (cellLocation2 instanceof GsmCellLocation)) {
            GsmCellLocation gsmCellLocation = (GsmCellLocation) cellLocation;
            GsmCellLocation gsmCellLocation2 = (GsmCellLocation) cellLocation2;
            return gsmCellLocation.getCid() == gsmCellLocation2.getCid() && gsmCellLocation.getLac() == gsmCellLocation2.getLac();
        } else if (!(cellLocation instanceof CdmaCellLocation) || !(cellLocation2 instanceof CdmaCellLocation)) {
            return false;
        } else {
            CdmaCellLocation cdmaCellLocation = (CdmaCellLocation) cellLocation;
            CdmaCellLocation cdmaCellLocation2 = (CdmaCellLocation) cellLocation2;
            return cdmaCellLocation.getBaseStationId() == cdmaCellLocation2.getBaseStationId() && cdmaCellLocation.getNetworkId() == cdmaCellLocation2.getNetworkId() && cdmaCellLocation.getSystemId() == cdmaCellLocation2.getSystemId();
        }
    }

    public static boolean a(CellInfo cellInfo, CellInfo cellInfo2) {
        if (cellInfo == cellInfo2) {
            return true;
        }
        if (!(cellInfo == null || cellInfo2 == null || VERSION.SDK_INT < 17)) {
            if ((cellInfo instanceof CellInfoGsm) && (cellInfo2 instanceof CellInfoGsm)) {
                CellIdentityGsm cellIdentity = ((CellInfoGsm) cellInfo).getCellIdentity();
                CellIdentityGsm cellIdentity2 = ((CellInfoGsm) cellInfo2).getCellIdentity();
                return cellIdentity.getCid() == cellIdentity2.getCid() && cellIdentity.getLac() == cellIdentity2.getLac();
            } else if ((cellInfo instanceof CellInfoCdma) && (cellInfo2 instanceof CellInfoCdma)) {
                CellIdentityCdma cellIdentity3 = ((CellInfoCdma) cellInfo).getCellIdentity();
                CellIdentityCdma cellIdentity4 = ((CellInfoCdma) cellInfo2).getCellIdentity();
                return cellIdentity3.getBasestationId() == cellIdentity4.getBasestationId() && cellIdentity3.getNetworkId() == cellIdentity4.getNetworkId() && cellIdentity3.getSystemId() == cellIdentity4.getSystemId();
            } else if ((cellInfo instanceof CellInfoLte) && (cellInfo2 instanceof CellInfoLte)) {
                CellIdentityLte cellIdentity5 = ((CellInfoLte) cellInfo).getCellIdentity();
                CellIdentityLte cellIdentity6 = ((CellInfoLte) cellInfo2).getCellIdentity();
                return cellIdentity5.getCi() == cellIdentity6.getCi() && cellIdentity5.getTac() == cellIdentity6.getTac();
            } else if (VERSION.SDK_INT >= 18 && (cellInfo instanceof CellInfoWcdma) && (cellInfo2 instanceof CellInfoWcdma)) {
                CellIdentityWcdma cellIdentity7 = ((CellInfoWcdma) cellInfo).getCellIdentity();
                CellIdentityWcdma cellIdentity8 = ((CellInfoWcdma) cellInfo2).getCellIdentity();
                return cellIdentity7.getCid() == cellIdentity8.getCid() && cellIdentity7.getLac() == cellIdentity8.getLac();
            }
        }
        return false;
    }

    public static boolean a(Context context) {
        int i;
        try {
            ContentResolver contentResolver = context.getContentResolver();
            if (VERSION.SDK_INT >= 17) {
                i = Global.getInt(contentResolver, "airplane_mode_on", 0);
            } else {
                i = System.getInt(contentResolver, "airplane_mode_on", 0);
            }
            if (i != 0) {
                return true;
            }
            return false;
        } catch (Throwable th) {
            a.a(th);
            return false;
        }
    }
}
