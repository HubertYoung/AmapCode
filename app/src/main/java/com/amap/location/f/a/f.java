package com.amap.location.f.a;

import android.support.annotation.NonNull;
import com.amap.location.common.model.CellState;
import com.amap.location.common.model.CellStatus;
import com.amap.location.common.model.FPS;
import com.amap.location.common.model.WiFi;
import com.amap.location.common.model.WifiStatus;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

/* compiled from: LocationCacheUtil */
public class f {
    private static a a = new a();

    /* compiled from: LocationCacheUtil */
    static class a implements Comparator<CellState> {
        private a() {
        }

        /* renamed from: a */
        public int compare(CellState cellState, CellState cellState2) {
            return cellState.cid - cellState2.cid;
        }
    }

    static boolean a(long j, long j2) {
        return j != 0 && j == j2;
    }

    /* JADX WARNING: Removed duplicated region for block: B:32:0x0047 A[RETURN] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static boolean a(com.amap.location.common.model.CellState r4, com.amap.location.common.model.CellState r5) {
        /*
            r0 = 1
            if (r4 != r5) goto L_0x0004
            return r0
        L_0x0004:
            r1 = 0
            if (r4 == 0) goto L_0x0048
            if (r5 != 0) goto L_0x000a
            goto L_0x0048
        L_0x000a:
            int r2 = r4.type
            int r3 = r5.type
            if (r2 == r3) goto L_0x0011
            return r1
        L_0x0011:
            int r2 = r4.mcc
            int r3 = r5.mcc
            if (r2 == r3) goto L_0x0018
            return r1
        L_0x0018:
            int r2 = r4.mnc
            int r3 = r5.mnc
            if (r2 == r3) goto L_0x001f
            return r1
        L_0x001f:
            int r2 = r4.type
            r3 = 2
            if (r2 == r3) goto L_0x0032
            int r2 = r4.lac
            int r3 = r5.lac
            if (r2 == r3) goto L_0x002b
            return r1
        L_0x002b:
            int r4 = r4.cid
            int r5 = r5.cid
            if (r4 == r5) goto L_0x0047
            return r1
        L_0x0032:
            int r2 = r4.sid
            int r3 = r5.sid
            if (r2 == r3) goto L_0x0039
            return r1
        L_0x0039:
            int r2 = r4.nid
            int r3 = r5.nid
            if (r2 == r3) goto L_0x0040
            return r1
        L_0x0040:
            int r4 = r4.bid
            int r5 = r5.bid
            if (r4 == r5) goto L_0x0047
            return r1
        L_0x0047:
            return r0
        L_0x0048:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.amap.location.f.a.f.a(com.amap.location.common.model.CellState, com.amap.location.common.model.CellState):boolean");
    }

    private static boolean a(List<CellState> list, List<CellState> list2) {
        if (list == list2) {
            return true;
        }
        if (list == null || list2 == null || list.size() != list2.size()) {
            return false;
        }
        int size = list.size();
        for (int i = 0; i < size; i++) {
            if (!a(list.get(i), list2.get(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean a(@NonNull FPS fps, @NonNull FPS fps2) {
        if (fps == fps2) {
            return true;
        }
        CellStatus cellStatus = fps.cellStatus;
        CellStatus cellStatus2 = fps2.cellStatus;
        if ((cellStatus.cellType == 0 || cellStatus2.cellType == 0) && cellStatus.cellType != cellStatus2.cellType) {
            return false;
        }
        if (cellStatus.cellType != 0) {
            CellState cellState = cellStatus.mainCell;
            if (cellState == null) {
                cellState = cellStatus.mainCell2;
            }
            CellState cellState2 = cellStatus2.mainCell;
            if (cellState2 == null) {
                cellState2 = cellStatus2.mainCell2;
            }
            if (!a(cellState, cellState2)) {
                return false;
            }
        }
        List<CellState> list = cellStatus.cellStateList2;
        if (list == null) {
            list = cellStatus.neighbors;
        }
        List<CellState> list2 = cellStatus2.cellStateList2;
        if (list2 == null) {
            list2 = cellStatus2.neighbors;
        }
        return a(list, list2);
    }

    public static boolean a(@NonNull FPS fps, @NonNull FPS fps2, double d) {
        int i;
        if (fps == fps2) {
            return true;
        }
        int[] iArr = new int[1];
        long[] a2 = a(fps, iArr);
        int i2 = iArr[0];
        long[] a3 = a(fps2, iArr);
        int i3 = iArr[0];
        if (i2 == 0 && i3 == 0) {
            return true;
        }
        int i4 = i2 + i3;
        if (a2 == null || a3 == null) {
            i = 0;
        } else {
            i = 0;
            for (int i5 = 0; i5 < i2; i5++) {
                long j = a2[i5];
                int i6 = 0;
                while (true) {
                    if (i6 >= i3) {
                        break;
                    } else if (j == a3[i6]) {
                        i++;
                        break;
                    } else {
                        i6++;
                    }
                }
            }
        }
        if (((double) (i * 2)) >= ((double) i4) * d) {
            return true;
        }
        return false;
    }

    private static long[] a(@NonNull FPS fps, @NonNull int[] iArr) {
        long[] jArr;
        int i;
        if (fps.wifiStatus != null) {
            long j = (fps.wifiStatus.mainWifi == null || fps.wifiStatus.mainWifi.mac == 0) ? 0 : fps.wifiStatus.mainWifi.mac;
            List<WiFi> wifiList = fps.wifiStatus.getWifiList();
            int i2 = 1;
            if (wifiList != null && wifiList.size() > 0) {
                if (j != 0) {
                    jArr = new long[(wifiList.size() + 1)];
                    jArr[0] = j;
                } else {
                    jArr = new long[wifiList.size()];
                    i2 = 0;
                }
                if (wifiList instanceof ArrayList) {
                    int size = wifiList.size();
                    i = i2;
                    for (int i3 = 0; i3 < size; i3++) {
                        WiFi wiFi = wifiList.get(i3);
                        if (!(wiFi.mac == 0 || wiFi.mac == j)) {
                            jArr[i] = wiFi.mac;
                            i++;
                        }
                    }
                } else {
                    i = i2;
                    for (WiFi next : wifiList) {
                        if (!(next.mac == 0 || next.mac == j)) {
                            jArr[i] = next.mac;
                            i++;
                        }
                    }
                }
                iArr[0] = i;
                return jArr;
            } else if (j != 0) {
                long[] jArr2 = {j};
                iArr[0] = 1;
                return jArr2;
            } else {
                iArr[0] = 0;
            }
        } else {
            iArr[0] = 0;
        }
        return null;
    }

    static void a(CellStatus cellStatus, b bVar) {
        if (cellStatus != null && cellStatus.cellType != 0) {
            CellState cellState = cellStatus.mainCell;
            if (cellState == null) {
                cellState = cellStatus.mainCell2;
            }
            a a2 = a(cellState);
            if (a2 != null) {
                bVar.a.add(a2);
            }
            List<CellState> list = cellStatus.cellStateList2;
            if (list == null || list.size() == 0) {
                list = cellStatus.neighbors;
            }
            if (list != null && list.size() > 0) {
                Collections.sort(list, a);
                for (CellState a3 : list) {
                    a a4 = a(a3);
                    if (a4 != null) {
                        bVar.a.add(a4);
                    }
                }
            }
        }
    }

    private static a a(CellState cellState) {
        if (cellState == null || cellState.type == 0) {
            return null;
        }
        a aVar = new a();
        if (cellState.type == 2) {
            aVar.a = cellState.mcc;
            aVar.b = cellState.sid;
            aVar.c = cellState.nid;
            aVar.d = cellState.bid;
            return aVar;
        }
        aVar.a = cellState.mcc;
        aVar.b = cellState.mnc;
        aVar.c = cellState.lac;
        aVar.d = cellState.cid;
        return aVar;
    }

    static void a(WifiStatus wifiStatus, g gVar) {
        if (wifiStatus != null) {
            if (wifiStatus.mainWifi != null) {
                gVar.a = wifiStatus.mainWifi.mac;
            }
            int numWiFis = wifiStatus.numWiFis();
            if (numWiFis > 0) {
                for (int i = 0; i < numWiFis; i++) {
                    WiFi wiFi = wifiStatus.getWiFi(i);
                    if (wiFi.mac != 0) {
                        gVar.b.add(Long.valueOf(wiFi.mac));
                    }
                }
            }
            if (gVar.a != 0) {
                gVar.b.add(Long.valueOf(gVar.a));
            }
        }
    }

    static boolean a(@NonNull HashSet<Long> hashSet, @NonNull HashSet<Long> hashSet2) {
        int i;
        int i2;
        int size = hashSet.size();
        if (!hashSet2.isEmpty()) {
            i2 = hashSet2.size();
            Iterator<Long> it = hashSet2.iterator();
            i = 0;
            while (it.hasNext()) {
                if (hashSet.contains(it.next())) {
                    i++;
                }
            }
        } else {
            i2 = 0;
            i = 0;
        }
        int i3 = size + i2;
        if ((size != 0 || i2 != 0) && ((double) (i * 2)) < ((double) i3) * 0.618d) {
            return false;
        }
        return true;
    }

    private static double[] a(double[] dArr, double[] dArr2) {
        double[] dArr3 = dArr;
        double[] dArr4 = {0.0d, 0.0d, 0.0d};
        double d = 0.0d;
        double d2 = 0.0d;
        double d3 = 0.0d;
        int i = 0;
        int i2 = 0;
        for (int i3 = 0; i3 < dArr3.length; i3++) {
            d += dArr3[i3] * dArr3[i3];
            d2 += dArr2[i3] * dArr2[i3];
            d3 += dArr3[i3] * dArr2[i3];
            if (dArr2[i3] == 1.0d) {
                i++;
                if (dArr3[i3] == 1.0d) {
                    i2++;
                }
            }
        }
        if (i == 0) {
            return dArr4;
        }
        dArr4[0] = d3 / (Math.sqrt(d) * Math.sqrt(d2));
        double d4 = (double) i2;
        dArr4[1] = (d4 * 1.0d) / ((double) i);
        dArr4[2] = d4;
        for (int i4 = 0; i4 < 2; i4++) {
            if (dArr4[i4] > 1.0d) {
                dArr4[i4] = 1.0d;
            }
            dArr4[i4] = ((double) Math.round(dArr4[i4] * 100.0d)) / 100.0d;
        }
        return dArr4;
    }

    static double[] b(HashSet<Long> hashSet, HashSet<Long> hashSet2) {
        double[] dArr = {0.0d, 0.0d, 0.0d};
        HashSet<Long> hashSet3 = new HashSet<>();
        hashSet3.addAll(hashSet);
        hashSet3.addAll(hashSet2);
        if (hashSet3.size() == 0) {
            return dArr;
        }
        double[] dArr2 = new double[hashSet3.size()];
        double[] dArr3 = new double[hashSet3.size()];
        int i = 0;
        for (Long l : hashSet3) {
            double d = 0.0d;
            dArr2[i] = hashSet.contains(l) ? 1.0d : 0.0d;
            if (hashSet2.contains(l)) {
                d = 1.0d;
            }
            dArr3[i] = d;
            i++;
        }
        return a(dArr2, dArr3);
    }
}
