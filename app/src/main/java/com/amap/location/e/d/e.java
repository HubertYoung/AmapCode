package com.amap.location.e.d;

import android.content.ContentResolver;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.os.Build.VERSION;
import android.os.PowerManager;
import android.os.SystemClock;
import android.provider.Settings.Global;
import android.provider.Settings.System;
import android.telephony.CellLocation;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import android.telephony.cdma.CdmaCellLocation;
import android.telephony.gsm.GsmCellLocation;
import com.amap.location.common.f.h;
import com.amap.location.common.model.CellState;
import com.amap.location.common.model.CellStatus;
import com.amap.location.common.model.FPS;
import com.amap.location.common.model.WiFi;
import com.amap.location.common.model.WifiStatus;
import java.util.Comparator;
import java.util.List;

/* compiled from: NLUtils */
public class e {
    private static a a = new a();

    /* compiled from: NLUtils */
    static class a implements Comparator<CellState> {
        private a() {
        }

        /* renamed from: a */
        public int compare(CellState cellState, CellState cellState2) {
            return cellState.cid - cellState2.cid;
        }
    }

    public static boolean a(Context context) {
        int i;
        ContentResolver contentResolver = context.getContentResolver();
        try {
            if (VERSION.SDK_INT >= 17) {
                i = Global.getInt(contentResolver, "airplane_mode_on", 0);
            } else {
                i = System.getInt(contentResolver, "airplane_mode_on", 0);
            }
            if (i != 0) {
                return true;
            }
            return false;
        } catch (SecurityException e) {
            com.amap.location.common.d.a.a((Throwable) e);
            return false;
        } catch (Throwable th) {
            com.amap.location.common.d.a.a(th);
            return false;
        }
    }

    public static boolean b(Context context) {
        com.amap.location.g.d.a a2 = com.amap.location.g.d.a.a(context);
        boolean g = a2.g();
        if (g || VERSION.SDK_INT < 18 || !a2.d()) {
            return g;
        }
        return true;
    }

    public static boolean c(Context context) {
        PowerManager powerManager = (PowerManager) context.getSystemService("power");
        if (VERSION.SDK_INT >= 20) {
            return powerManager.isInteractive();
        }
        return powerManager.isScreenOn();
    }

    public static int a() {
        int i;
        try {
            Class.forName("android.telephony.MSimTelephonyManager");
            i = 1;
        } catch (Throwable unused) {
            i = 0;
        }
        if (i != 0) {
            return i;
        }
        try {
            Class.forName("android.telephony.TelephonyManager2");
            return 2;
        } catch (Throwable unused2) {
            return i;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:21:0x003c  */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x004b  */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x008f  */
    /* JADX WARNING: Removed duplicated region for block: B:66:0x00f8  */
    /* JADX WARNING: Removed duplicated region for block: B:69:0x0101  */
    /* JADX WARNING: Removed duplicated region for block: B:72:0x010a  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.amap.location.common.model.CellStatus a(android.content.Context r4, com.amap.location.g.a.a r5, java.lang.Object r6, com.amap.location.e.c.b r7, android.telephony.CellLocation r8, com.amap.location.common.model.FPS r9) {
        /*
            java.lang.String r0 = r5.e()
            java.util.List r1 = java.util.Collections.emptyList()
            if (r8 != 0) goto L_0x0019
            android.telephony.CellLocation r8 = a(r4, r5)     // Catch:{ SecurityException -> 0x0017, Exception -> 0x0015 }
            if (r8 != 0) goto L_0x0019
            android.telephony.CellLocation r8 = a(r6)     // Catch:{ SecurityException -> 0x0017, Exception -> 0x0015 }
            goto L_0x0019
        L_0x0015:
            r4 = move-exception
            goto L_0x0029
        L_0x0017:
            r4 = move-exception
            goto L_0x002d
        L_0x0019:
            r4 = 0
            if (r8 == 0) goto L_0x0024
            boolean r2 = r8 instanceof android.telephony.gsm.GsmCellLocation     // Catch:{ SecurityException -> 0x0017, Exception -> 0x0015 }
            if (r2 == 0) goto L_0x0024
            java.util.List r4 = r5.f()     // Catch:{ SecurityException -> 0x0017, Exception -> 0x0015 }
        L_0x0024:
            java.util.List r4 = com.amap.location.e.d.b.a(r8, r4, r0, r6)     // Catch:{ SecurityException -> 0x0017, Exception -> 0x0015 }
            goto L_0x0031
        L_0x0029:
            com.amap.location.common.d.a.a(r4)
            goto L_0x0030
        L_0x002d:
            com.amap.location.common.d.a.a(r4)
        L_0x0030:
            r4 = r1
        L_0x0031:
            com.amap.location.common.model.CellStatus r6 = new com.amap.location.common.model.CellStatus
            r6.<init>()
            boolean r8 = android.text.TextUtils.isEmpty(r0)
            if (r8 != 0) goto L_0x0044
            r6.networkOperator = r0
            int r8 = r6.cellType
            r8 = r8 | 8
            r6.cellType = r8
        L_0x0044:
            int r8 = r4.size()
            r1 = 0
            if (r8 <= 0) goto L_0x0089
            java.lang.Object r8 = r4.get(r1)
            com.amap.location.common.model.CellState r8 = (com.amap.location.common.model.CellState) r8
            boolean r2 = r8.registered
            if (r2 == 0) goto L_0x0087
            com.amap.location.common.model.CellStatus r2 = r9.cellStatus
            com.amap.location.common.model.CellState r2 = r2.mainCell
            if (r2 == 0) goto L_0x006d
            com.amap.location.common.model.CellStatus r2 = r9.cellStatus
            com.amap.location.common.model.CellState r2 = r2.mainCell
            int r2 = r2.signalStrength
            r3 = 99
            if (r2 == r3) goto L_0x006d
            com.amap.location.common.model.CellStatus r9 = r9.cellStatus
            com.amap.location.common.model.CellState r9 = r9.mainCell
            int r9 = r9.signalStrength
            r8.signalStrength = r9
        L_0x006d:
            r6.mainCell = r8
            r4.remove(r1)
            int r9 = r8.type
            r2 = 1
            if (r9 != r2) goto L_0x007d
            int r8 = r6.cellType
            r8 = r8 | r2
            r6.cellType = r8
            goto L_0x0087
        L_0x007d:
            int r8 = r8.type
            r9 = 2
            if (r8 != r9) goto L_0x0087
            int r8 = r6.cellType
            r8 = r8 | r9
            r6.cellType = r8
        L_0x0087:
            r6.neighbors = r4
        L_0x0089:
            int r4 = android.os.Build.VERSION.SDK_INT
            r8 = 18
            if (r4 < r8) goto L_0x00c0
            java.util.List r4 = java.util.Collections.emptyList()
            java.util.List r5 = r5.b()     // Catch:{ SecurityException -> 0x00a2, Exception -> 0x009d }
            java.util.List r5 = com.amap.location.e.d.b.a(r5, r0, r7)     // Catch:{ SecurityException -> 0x00a2, Exception -> 0x009d }
            r4 = r5
            goto L_0x00a6
        L_0x009d:
            r5 = move-exception
            com.amap.location.common.d.a.a(r5)
            goto L_0x00a6
        L_0x00a2:
            r5 = move-exception
            com.amap.location.common.d.a.a(r5)
        L_0x00a6:
            int r5 = r4.size()
            if (r5 <= 0) goto L_0x00c0
            int r5 = r6.cellType
            r5 = r5 | 4
            r6.cellType = r5
            java.lang.Object r5 = r4.get(r1)
            com.amap.location.common.model.CellState r5 = (com.amap.location.common.model.CellState) r5
            boolean r8 = r5.registered
            if (r8 == 0) goto L_0x00be
            r6.mainCell2 = r5
        L_0x00be:
            r6.cellStateList2 = r4
        L_0x00c0:
            a(r6)
            long r4 = android.os.SystemClock.elapsedRealtime()
            r6.updateTime = r4
            java.util.List<com.amap.location.common.model.CellState> r4 = r6.neighbors
            if (r4 == 0) goto L_0x00dc
            java.util.List<com.amap.location.common.model.CellState> r4 = r6.neighbors
            int r4 = r4.size()
            if (r4 <= 0) goto L_0x00dc
            java.util.List<com.amap.location.common.model.CellState> r4 = r6.neighbors
            com.amap.location.e.d.e$a r5 = a
            java.util.Collections.sort(r4, r5)
        L_0x00dc:
            java.util.List<com.amap.location.common.model.CellState> r4 = r6.cellStateList2
            if (r4 == 0) goto L_0x00ef
            java.util.List<com.amap.location.common.model.CellState> r4 = r6.cellStateList2
            int r4 = r4.size()
            if (r4 <= 0) goto L_0x00ef
            java.util.List<com.amap.location.common.model.CellState> r4 = r6.cellStateList2
            com.amap.location.e.d.e$a r5 = a
            java.util.Collections.sort(r4, r5)
        L_0x00ef:
            java.util.ArrayList r4 = new java.util.ArrayList
            r4.<init>()
            com.amap.location.common.model.CellState r5 = r6.mainCell
            if (r5 == 0) goto L_0x00fd
            com.amap.location.common.model.CellState r5 = r6.mainCell
            r4.add(r5)
        L_0x00fd:
            java.util.List<com.amap.location.common.model.CellState> r5 = r6.neighbors
            if (r5 == 0) goto L_0x0106
            java.util.List<com.amap.location.common.model.CellState> r5 = r6.neighbors
            r4.addAll(r5)
        L_0x0106:
            java.util.List<com.amap.location.common.model.CellState> r5 = r6.cellStateList2
            if (r5 == 0) goto L_0x010f
            java.util.List<com.amap.location.common.model.CellState> r5 = r6.cellStateList2
            r4.addAll(r5)
        L_0x010f:
            r7.a(r4)
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.amap.location.e.d.e.a(android.content.Context, com.amap.location.g.a.a, java.lang.Object, com.amap.location.e.c.b, android.telephony.CellLocation, com.amap.location.common.model.FPS):com.amap.location.common.model.CellStatus");
    }

    public static CellLocation a(Context context, com.amap.location.g.a.a aVar) {
        CellLocation cellLocation = null;
        try {
            CellLocation a2 = aVar.a();
            try {
                if (a(a2)) {
                    return a2;
                }
                TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
                try {
                    a2 = a((List) com.amap.location.g.c.a.a(telephonyManager, "getAllCellInfo", new Object[0]));
                } catch (Exception e) {
                    com.amap.location.common.d.a.a((Throwable) e);
                }
                if (a(a2)) {
                    return a2;
                }
                try {
                    Object a3 = com.amap.location.g.c.a.a(telephonyManager, "getCellLocationExt", Integer.valueOf(1));
                    if (a3 != null) {
                        a2 = (CellLocation) a3;
                    }
                } catch (Exception e2) {
                    com.amap.location.common.d.a.a((Throwable) e2);
                }
                if (a(a2)) {
                    return a2;
                }
                try {
                    Object a4 = com.amap.location.g.c.a.a(telephonyManager, "getCellLocationGemini", Integer.valueOf(1));
                    cellLocation = a4 != null ? (CellLocation) a4 : a2;
                } catch (Exception e3) {
                    com.amap.location.common.d.a.a((Throwable) e3);
                    cellLocation = a2;
                }
                if (a(cellLocation)) {
                    return cellLocation;
                }
                return cellLocation;
            } catch (SecurityException e4) {
                e = e4;
                cellLocation = a2;
                com.amap.location.common.d.a.a((Throwable) e);
                return cellLocation;
            } catch (Throwable th) {
                th = th;
                cellLocation = a2;
                com.amap.location.common.d.a.a(th);
                return cellLocation;
            }
        } catch (SecurityException e5) {
            e = e5;
            com.amap.location.common.d.a.a((Throwable) e);
            return cellLocation;
        } catch (Throwable th2) {
            th = th2;
            com.amap.location.common.d.a.a(th);
            return cellLocation;
        }
    }

    public static boolean a(CellLocation cellLocation) {
        if (cellLocation == null) {
            return false;
        }
        if (cellLocation instanceof GsmCellLocation) {
            GsmCellLocation gsmCellLocation = (GsmCellLocation) cellLocation;
            return b.a(gsmCellLocation.getLac(), gsmCellLocation.getCid());
        } else if (!(cellLocation instanceof CdmaCellLocation)) {
            return false;
        } else {
            CdmaCellLocation cdmaCellLocation = (CdmaCellLocation) cellLocation;
            return b.a(cdmaCellLocation.getSystemId(), cdmaCellLocation.getNetworkId(), cdmaCellLocation.getBaseStationId());
        }
    }

    /* JADX WARNING: type inference failed for: r3v0 */
    /* JADX WARNING: type inference failed for: r1v2 */
    /* JADX WARNING: type inference failed for: r1v3 */
    /* JADX WARNING: type inference failed for: r7v6 */
    /* JADX WARNING: type inference failed for: r1v5 */
    /* JADX WARNING: type inference failed for: r1v6 */
    /* JADX WARNING: type inference failed for: r3v5 */
    /* JADX WARNING: type inference failed for: r1v9 */
    /* JADX WARNING: type inference failed for: r3v8 */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x0038 A[Catch:{ Exception -> 0x001c, Throwable -> 0x006c }] */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x004e A[Catch:{ Exception -> 0x001c, Throwable -> 0x006c }] */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x0063 A[Catch:{ Exception -> 0x001c, Throwable -> 0x006c }] */
    /* JADX WARNING: Unknown variable types count: 2 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static android.telephony.CellLocation a(java.lang.Object r7) {
        /*
            r0 = 0
            if (r7 != 0) goto L_0x0004
            return r0
        L_0x0004:
            java.lang.Class r1 = b()     // Catch:{ Throwable -> 0x006c }
            boolean r2 = r1.isInstance(r7)     // Catch:{ Throwable -> 0x006c }
            if (r2 == 0) goto L_0x0065
            java.lang.Object r7 = r1.cast(r7)     // Catch:{ Throwable -> 0x006c }
            java.lang.String r1 = "getCellLocation"
            r2 = 0
            java.lang.Object[] r3 = new java.lang.Object[r2]     // Catch:{ Exception -> 0x001c }
            java.lang.Object r3 = com.amap.location.g.c.a.a(r7, r1, r3)     // Catch:{ Exception -> 0x001c }
            goto L_0x0021
        L_0x001c:
            r3 = move-exception
            com.amap.location.common.d.a.a(r3)     // Catch:{ Throwable -> 0x006c }
            r3 = r0
        L_0x0021:
            r4 = 1
            if (r3 != 0) goto L_0x0035
            java.lang.Object[] r5 = new java.lang.Object[r4]     // Catch:{ Exception -> 0x0031 }
            java.lang.Integer r6 = java.lang.Integer.valueOf(r4)     // Catch:{ Exception -> 0x0031 }
            r5[r2] = r6     // Catch:{ Exception -> 0x0031 }
            java.lang.Object r1 = com.amap.location.g.c.a.a(r7, r1, r5)     // Catch:{ Exception -> 0x0031 }
            goto L_0x0036
        L_0x0031:
            r1 = move-exception
            com.amap.location.common.d.a.a(r1)     // Catch:{ Throwable -> 0x006c }
        L_0x0035:
            r1 = r3
        L_0x0036:
            if (r1 != 0) goto L_0x004c
            java.lang.String r3 = "getCellLocationGemini"
            java.lang.Object[] r5 = new java.lang.Object[r4]     // Catch:{ Exception -> 0x0048 }
            java.lang.Integer r4 = java.lang.Integer.valueOf(r4)     // Catch:{ Exception -> 0x0048 }
            r5[r2] = r4     // Catch:{ Exception -> 0x0048 }
            java.lang.Object r3 = com.amap.location.g.c.a.a(r7, r3, r5)     // Catch:{ Exception -> 0x0048 }
            r1 = r3
            goto L_0x004c
        L_0x0048:
            r3 = move-exception
            com.amap.location.common.d.a.a(r3)     // Catch:{ Throwable -> 0x006c }
        L_0x004c:
            if (r1 != 0) goto L_0x0063
            java.lang.String r1 = "getAllCellInfo"
            java.lang.Object[] r2 = new java.lang.Object[r2]     // Catch:{ Exception -> 0x0059 }
            java.lang.Object r7 = com.amap.location.g.c.a.a(r7, r1, r2)     // Catch:{ Exception -> 0x0059 }
            java.util.List r7 = (java.util.List) r7     // Catch:{ Exception -> 0x0059 }
            goto L_0x005e
        L_0x0059:
            r7 = move-exception
            com.amap.location.common.d.a.a(r7)     // Catch:{ Throwable -> 0x006c }
            r7 = r0
        L_0x005e:
            android.telephony.CellLocation r7 = a(r7)     // Catch:{ Throwable -> 0x006c }
            goto L_0x0066
        L_0x0063:
            r7 = r1
            goto L_0x0066
        L_0x0065:
            r7 = r0
        L_0x0066:
            if (r7 == 0) goto L_0x0070
            android.telephony.CellLocation r7 = (android.telephony.CellLocation) r7     // Catch:{ Throwable -> 0x006c }
            r0 = r7
            goto L_0x0070
        L_0x006c:
            r7 = move-exception
            com.amap.location.common.d.a.a(r7)
        L_0x0070:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.amap.location.e.d.e.a(java.lang.Object):android.telephony.CellLocation");
    }

    private static Class<?> b() {
        String str;
        switch (a()) {
            case 0:
                str = "android.telephony.TelephonyManager";
                break;
            case 1:
                str = "android.telephony.MSimTelephonyManager";
                break;
            case 2:
                str = "android.telephony.TelephonyManager2";
                break;
            default:
                str = null;
                break;
        }
        try {
            return ClassLoader.getSystemClassLoader().loadClass(str);
        } catch (Exception e) {
            com.amap.location.common.d.a.a((Throwable) e);
            return null;
        }
    }

    private static CellLocation a(List<?> list) {
        Throwable th;
        Object obj;
        List<?> list2 = list;
        if (list2 == null || list.isEmpty()) {
            return null;
        }
        ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
        int i = 0;
        GsmCellLocation gsmCellLocation = null;
        CdmaCellLocation cdmaCellLocation = null;
        char c = 0;
        while (true) {
            if (i >= list.size()) {
                break;
            }
            Object obj2 = list2.get(i);
            if (obj2 != null) {
                try {
                    Class<?> loadClass = systemClassLoader.loadClass("android.telephony.CellInfoGsm");
                    Class<?> loadClass2 = systemClassLoader.loadClass("android.telephony.CellInfoWcdma");
                    Class<?> loadClass3 = systemClassLoader.loadClass("android.telephony.CellInfoLte");
                    Class<?> loadClass4 = systemClassLoader.loadClass("android.telephony.CellInfoCdma");
                    c = loadClass.isInstance(obj2) ? 1 : loadClass2.isInstance(obj2) ? 2 : loadClass3.isInstance(obj2) ? 3 : loadClass4.isInstance(obj2) ? (char) 4 : 0;
                    if (c > 0) {
                        if (c == 1) {
                            try {
                                obj = loadClass.cast(obj2);
                            } catch (Exception e) {
                                th = e;
                                com.amap.location.common.d.a.a(th);
                                i++;
                            }
                        } else {
                            obj = c == 2 ? loadClass2.cast(obj2) : c == 3 ? loadClass3.cast(obj2) : c == 4 ? loadClass4.cast(obj2) : null;
                        }
                        Object a2 = com.amap.location.g.c.a.a(obj, "getCellIdentity", new Object[0]);
                        if (a2 != null) {
                            if (c == 4) {
                                CdmaCellLocation cdmaCellLocation2 = new CdmaCellLocation();
                                try {
                                    try {
                                        cdmaCellLocation2.setCellLocationData(com.amap.location.g.c.a.b(a2, "getBasestationId", new Object[0]), com.amap.location.g.c.a.b(a2, "getLatitude", new Object[0]), com.amap.location.g.c.a.b(a2, "getLongitude", new Object[0]), com.amap.location.g.c.a.b(a2, "getSystemId", new Object[0]), com.amap.location.g.c.a.b(a2, "getNetworkId", new Object[0]));
                                        cdmaCellLocation = cdmaCellLocation2;
                                        break;
                                    } catch (Exception e2) {
                                        th = e2;
                                        cdmaCellLocation = cdmaCellLocation2;
                                    }
                                } catch (Exception e3) {
                                    th = e3;
                                    cdmaCellLocation = cdmaCellLocation2;
                                    com.amap.location.common.d.a.a(th);
                                    i++;
                                }
                            } else if (c == 3) {
                                try {
                                    int b = com.amap.location.g.c.a.b(a2, "getTac", new Object[0]);
                                    int b2 = com.amap.location.g.c.a.b(a2, "getCi", new Object[0]);
                                    GsmCellLocation gsmCellLocation2 = new GsmCellLocation();
                                    try {
                                        gsmCellLocation2.setLacAndCid(b, b2);
                                        gsmCellLocation = gsmCellLocation2;
                                        break;
                                    } catch (Exception e4) {
                                        th = e4;
                                        gsmCellLocation = gsmCellLocation2;
                                    }
                                } catch (Exception e5) {
                                    e = e5;
                                    th = e;
                                    com.amap.location.common.d.a.a(th);
                                    i++;
                                }
                            } else {
                                int b3 = com.amap.location.g.c.a.b(a2, "getLac", new Object[0]);
                                int b4 = com.amap.location.g.c.a.b(a2, "getCid", new Object[0]);
                                GsmCellLocation gsmCellLocation3 = new GsmCellLocation();
                                try {
                                    gsmCellLocation3.setLacAndCid(b3, b4);
                                    gsmCellLocation = gsmCellLocation3;
                                    break;
                                } catch (Exception e6) {
                                    th = e6;
                                    gsmCellLocation = gsmCellLocation3;
                                }
                            }
                        }
                    }
                } catch (Exception e7) {
                    e = e7;
                    th = e;
                    com.amap.location.common.d.a.a(th);
                    i++;
                }
            }
            i++;
        }
        return c == 4 ? cdmaCellLocation : gsmCellLocation;
    }

    private static void a(CellStatus cellStatus) {
        if (cellStatus.mainCell != null && cellStatus.mainCell.type != 2 && cellStatus.mainCell.mcc == 65535 && cellStatus.mainCell.mnc == 65535) {
            int size = cellStatus.cellStateList2.size();
            if (size > 0) {
                for (int i = 0; i < size; i++) {
                    CellState cellState = cellStatus.cellStateList2.get(i);
                    if (cellState.type != 2 && cellState.lac == cellStatus.mainCell.lac && cellState.cid == cellStatus.mainCell.cid) {
                        cellStatus.mainCell.mcc = cellState.mcc;
                        cellStatus.mainCell.mnc = cellState.mnc;
                        return;
                    }
                }
            }
        }
    }

    public static boolean a(FPS fps) {
        if (fps.cellStatus.mainCell != null || fps.cellStatus.mainCell2 != null || fps.wifiStatus.numWiFis() >= 2) {
            return true;
        }
        if (fps.wifiStatus.mainWifi == null || fps.wifiStatus.mainWifi.mac <= 0 || fps.wifiStatus.numWiFis() != 1 || fps.wifiStatus.mainWifi.mac == fps.wifiStatus.getWiFi(0).mac) {
            return false;
        }
        return true;
    }

    public static void a(WifiStatus wifiStatus, WifiInfo wifiInfo, Context context) {
        if (wifiInfo != null) {
            int frequency = VERSION.SDK_INT >= 21 ? wifiInfo.getFrequency() : 0;
            long a2 = h.a(wifiInfo.getBSSID());
            if (a2 == 0 || !d(context)) {
                wifiStatus.mainWifi = null;
                return;
            }
            WiFi wiFi = new WiFi(a2, wifiInfo.getSSID(), wifiInfo.getRssi(), frequency, SystemClock.elapsedRealtime(), true);
            wifiStatus.mainWifi = wiFi;
            return;
        }
        wifiStatus.mainWifi = null;
    }

    public static boolean d(Context context) {
        return com.amap.location.g.d.a.a(context).g() && !(h.a(context) == 0);
    }

    public static int a(SignalStrength signalStrength, com.amap.location.g.a.a aVar, int i) {
        if (i == 1) {
            if (aVar == null || aVar.c() != 13) {
                return b.a(signalStrength.getGsmSignalStrength());
            }
            try {
                return ((Integer) SignalStrength.class.getDeclaredMethod("getLteDbm", new Class[0]).invoke(signalStrength, new Object[0])).intValue();
            } catch (Exception e) {
                com.amap.location.common.d.a.a((Throwable) e);
                return 99;
            }
        } else if (i == 2) {
            return signalStrength.getCdmaDbm();
        } else {
            return 99;
        }
    }
}
