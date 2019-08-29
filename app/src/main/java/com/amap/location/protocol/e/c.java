package com.amap.location.protocol.e;

import android.content.Context;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Base64;
import com.alipay.mobile.mrtc.api.constants.APCallCode;
import com.amap.location.common.f.f;
import com.amap.location.common.f.h;
import com.amap.location.common.model.AmapLoc;
import com.amap.location.common.model.CellState;
import com.amap.location.common.model.CellStatus;
import com.amap.location.common.model.CellStatus.HistoryCell;
import com.amap.location.common.model.FPS;
import com.amap.location.common.model.HisLocation;
import com.amap.location.common.model.WiFi;
import com.amap.location.common.model.WifiStatus;
import com.amap.location.protocol.b.b;
import com.amap.location.security.Core;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;

/* compiled from: RequestDataBuilder */
public class c {
    private static volatile boolean b = true;
    private static a c = new a();
    private b a = new b();

    /* compiled from: RequestDataBuilder */
    static class a implements Comparator<WiFi> {
        private a() {
        }

        /* renamed from: a */
        public int compare(WiFi wiFi, WiFi wiFi2) {
            return wiFi2.rssi - wiFi.rssi;
        }
    }

    private static byte a(int i, int i2) {
        if (i > 127 || i < -128) {
            i = i2;
        }
        return (byte) i;
    }

    private static int a(int i) {
        if (i < 0) {
            return 0;
        }
        if (i > 65535) {
            return 65535;
        }
        return i;
    }

    public static b a(Context context, FPS fps, byte[] bArr, List<HisLocation> list, String str, String str2, String str3, boolean z, boolean z2, boolean z3, int i) {
        b bVar = new b();
        if (!z2) {
            z |= true;
        }
        bVar.a = z ? 1 : 0;
        bVar.b = com.amap.location.protocol.b.a.b;
        bVar.c = com.amap.location.protocol.b.a.c;
        bVar.d = null;
        bVar.e = null;
        bVar.f = a.a();
        bVar.g = a.b();
        bVar.h = null;
        bVar.i = a.a(context);
        if (!TextUtils.isEmpty(str)) {
            StringBuilder sb = new StringBuilder();
            sb.append(bVar.i);
            sb.append("*");
            sb.append(str);
            bVar.i = sb.toString();
        }
        if (b) {
            bVar.j = com.amap.location.common.a.a(context);
            b = false;
        } else {
            bVar.j = com.amap.location.common.a.a(context);
        }
        bVar.k = com.amap.location.common.a.d(context);
        bVar.B = com.amap.location.common.a.e();
        bVar.l = h.a(a.b(context));
        bVar.m = com.amap.location.protocol.b.a.d;
        bVar.n = com.amap.location.protocol.b.a.e;
        bVar.o = str2;
        bVar.p = com.amap.location.common.a.a();
        bVar.q = a.c(context);
        bVar.r = (byte) a.c();
        bVar.s = 0;
        bVar.t = fps;
        if (fps.wifiStatus != null) {
            bVar.u = (int) ((SystemClock.elapsedRealtime() - fps.wifiStatus.updateTime) / 1000);
        }
        bVar.v = com.amap.location.protocol.a.a.a().a(i);
        bVar.w = null;
        bVar.x = AmapLoc.sCxtFromServer;
        bVar.y = null;
        if (z3) {
            bVar.z = bArr;
        }
        bVar.A = list;
        return bVar;
    }

    public byte[] a(b bVar, boolean z) throws Exception {
        this.a.a(1);
        this.a.a(bVar.a, 2);
        this.a.a(bVar.b);
        this.a.a(bVar.c);
        this.a.a(bVar.d);
        this.a.a(bVar.e);
        this.a.a(bVar.f);
        this.a.a(bVar.g);
        this.a.a(bVar.h);
        this.a.a(bVar.i);
        this.a.a(bVar.j);
        this.a.a(bVar.k);
        if (bVar.l == 0) {
            this.a.a(0);
        } else {
            this.a.a(6);
            this.a.a(bVar.l);
        }
        this.a.a(bVar.m);
        this.a.a(bVar.n);
        this.a.a(bVar.o);
        this.a.a(bVar.p);
        this.a.a(bVar.q);
        this.a.a(bVar.r);
        this.a.a(bVar.s);
        a(bVar.t.cellStatus);
        a(bVar.t.wifiStatus, bVar.u);
        a(bVar.v);
        this.a.a(bVar.w);
        byte[] a2 = a(bVar.x);
        if (a2 == null) {
            this.a.a((String) "", 2);
        } else {
            this.a.a(a2, 2);
        }
        this.a.a(0, 2);
        this.a.a(0, 2);
        if (bVar.z == null) {
            this.a.a(0, 2);
        } else {
            this.a.a(bVar.z, 2);
        }
        List<HistoryCell> historyCells = bVar.t.cellStatus.getHistoryCells();
        int min = Math.min(127, historyCells.size());
        this.a.a((byte) min);
        for (int i = 0; i < min; i++) {
            HistoryCell historyCell = historyCells.get(i);
            byte b2 = (byte) historyCell.type;
            if (b2 > 0 && b2 <= 4) {
                this.a.a(b2);
                switch (historyCell.type) {
                    case 1:
                    case 3:
                    case 4:
                        this.a.a(historyCell.lac, 2);
                        this.a.a(historyCell.cid, 4);
                        break;
                    case 2:
                        this.a.a(historyCell.sid, 2);
                        this.a.a(historyCell.nid, 2);
                        this.a.a(historyCell.bid, 2);
                        break;
                }
                long elapsedRealtime = (SystemClock.elapsedRealtime() - historyCell.lastUpdateTimeMills) / 1000;
                if (elapsedRealtime > 32767 || elapsedRealtime < 0) {
                    elapsedRealtime = 32767;
                }
                this.a.a((int) (short) ((int) elapsedRealtime), 2);
            }
        }
        this.a.a(bVar.B);
        b(bVar.A);
        byte[] a3 = this.a.a();
        CRC32 crc32 = new CRC32();
        crc32.update(a3);
        this.a.a(crc32.getValue(), 8);
        byte[] a4 = this.a.a();
        return z ? f.a(a4) : a4;
    }

    private void a(WifiStatus wifiStatus, int i) {
        long currentTimeMillis = System.currentTimeMillis();
        long j = (wifiStatus == null || wifiStatus.mainWifi == null) ? 0 : wifiStatus.mainWifi.mac;
        if (j == 0) {
            this.a.a(0);
        } else {
            this.a.a(1);
            this.a.a(j);
            this.a.a(wifiStatus.mainWifi.ssid);
            this.a.a(a(wifiStatus.mainWifi.rssi, (int) APCallCode.CALL_ERROR_INNER));
            this.a.a(a((int) ((currentTimeMillis - wifiStatus.mainWifi.lastUpdateUtcMills) / 1000)), 2);
        }
        if (wifiStatus == null || wifiStatus.getWifiList() == null || wifiStatus.getWifiList().size() <= 0) {
            this.a.a(0);
            return;
        }
        List<WiFi> wifiList = wifiStatus.getWifiList();
        Collections.sort(wifiList, c);
        int min = Math.min(25, wifiList.size());
        this.a.a((byte) min);
        for (int i2 = 0; i2 < min; i2++) {
            WiFi wiFi = wifiList.get(i2);
            this.a.a(wiFi.mac);
            this.a.a(a(wiFi.ssid, true, i2));
            this.a.a(a(wiFi.rssi, (int) APCallCode.CALL_ERROR_INNER));
            this.a.a(a((int) ((currentTimeMillis - wiFi.lastUpdateUtcMills) / 1000)), 2);
            this.a.a(a(wiFi.frequency), 2);
        }
        this.a.a(a(i), 2);
    }

    private void a(CellStatus cellStatus) {
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        CellStatus cellStatus2 = cellStatus;
        long currentTimeMillis = System.currentTimeMillis();
        if (cellStatus2 == null || cellStatus2.cellType == 0) {
            this.a.a(0);
        } else {
            byte a2 = a(cellStatus2.cellType, 0);
            this.a.a(a2);
            if ((a2 & 1) != 0) {
                this.a.a(cellStatus2.mainCell.mcc, 2);
                this.a.a(cellStatus2.mainCell.mnc, 2);
                this.a.a(cellStatus2.mainCell.lac, 2);
                this.a.a(cellStatus2.mainCell.cid, 4);
                this.a.a(a(cellStatus2.mainCell.signalStrength, 99));
                if (cellStatus2.mainCell.lastUpdateUtcMills < 0) {
                    i4 = 0;
                } else {
                    i4 = (int) ((currentTimeMillis - cellStatus2.mainCell.lastUpdateUtcMills) / 1000);
                }
                this.a.a(a(i4), 2);
                if (cellStatus2.neighbors == null || cellStatus2.neighbors.size() <= 0) {
                    this.a.a(0);
                } else {
                    int min = Math.min(127, cellStatus2.neighbors.size());
                    this.a.a((byte) min);
                    Iterator<CellState> it = cellStatus2.neighbors.iterator();
                    int i6 = 0;
                    while (it.hasNext() && i6 < min) {
                        CellState next = it.next();
                        this.a.a(next.lac, 2);
                        this.a.a(next.cid, 4);
                        this.a.a(a(next.signalStrength, 99));
                        if (next.lastUpdateUtcMills < 0) {
                            i5 = 0;
                        } else {
                            i5 = (int) ((currentTimeMillis - next.lastUpdateUtcMills) / 1000);
                        }
                        this.a.a(a(i5), 2);
                        i6++;
                    }
                }
            } else if ((a2 & 2) != 0) {
                this.a.a(cellStatus2.mainCell.mcc, 2);
                this.a.a(cellStatus2.mainCell.sid, 2);
                this.a.a(cellStatus2.mainCell.nid, 2);
                this.a.a(cellStatus2.mainCell.bid, 2);
                this.a.a(cellStatus2.mainCell.longitude, 4);
                this.a.a(cellStatus2.mainCell.latitude, 4);
                this.a.a(a(cellStatus2.mainCell.signalStrength, 99));
                if (cellStatus2.mainCell.lastUpdateUtcMills < 0) {
                    i3 = 0;
                } else {
                    i3 = (int) ((currentTimeMillis - cellStatus2.mainCell.lastUpdateUtcMills) / 1000);
                }
                this.a.a(a(i3), 2);
                this.a.a(0);
            }
        }
        if (!(cellStatus2 == null || cellStatus2.networkOperator == null || (cellStatus2.cellType & 8) == 0)) {
            try {
                this.a.a(cellStatus2.networkOperator.getBytes("UTF-8"), 60, 1);
            } catch (Exception unused) {
            }
            if (cellStatus2 != null || (cellStatus2.cellType & 4) == 0) {
                this.a.a(0);
            }
            int min2 = Math.min(127, cellStatus2.cellStateList2.size());
            this.a.a((byte) min2);
            for (int i7 = 0; i7 < min2; i7++) {
                CellState cellState = cellStatus2.cellStateList2.get(i7);
                int i8 = cellState.type;
                if (cellState.registered) {
                    i8 |= 8;
                }
                switch (cellState.type) {
                    case 1:
                    case 3:
                    case 4:
                        this.a.a((byte) i8);
                        this.a.a(cellState.mcc, 2);
                        this.a.a(cellState.mnc, 2);
                        this.a.a(cellState.lac, 2);
                        this.a.a(cellState.cid, 4);
                        this.a.a(a(cellState.signalStrength, 99));
                        if (cellState.lastUpdateUtcMills < 0) {
                            i = 0;
                        } else {
                            i = (int) ((currentTimeMillis - cellState.lastUpdateUtcMills) / 1000);
                        }
                        this.a.a(a(i), 2);
                        if (cellState.type != 3 && cellState.type != 4) {
                            break;
                        } else {
                            this.a.a((int) cellState.pci, 2);
                            continue;
                        }
                    case 2:
                        this.a.a((byte) i8);
                        this.a.a(cellState.mcc, 2);
                        this.a.a(cellState.sid, 2);
                        this.a.a(cellState.nid, 2);
                        this.a.a(cellState.bid, 2);
                        this.a.a(cellState.longitude, 4);
                        this.a.a(cellState.latitude, 4);
                        this.a.a(a(cellState.signalStrength, 99));
                        if (cellState.lastUpdateUtcMills < 0) {
                            i2 = 0;
                        } else {
                            i2 = (int) ((currentTimeMillis - cellState.lastUpdateUtcMills) / 1000);
                        }
                        this.a.a(a(i2), 2);
                        break;
                }
            }
            return;
        }
        this.a.a(0);
        if (cellStatus2 != null) {
        }
        this.a.a(0);
    }

    private void a(List<AmapLoc> list) {
        if (list != null) {
            long currentTimeMillis = System.currentTimeMillis();
            int min = Math.min(127, list.size());
            this.a.a((byte) min);
            for (int i = 0; i < min; i++) {
                AmapLoc amapLoc = list.get(i);
                try {
                    this.a.a(Integer.parseInt(Core.ce(amapLoc.getLon(), 1)), 4);
                } catch (Throwable unused) {
                    this.a.a(0, 4);
                }
                try {
                    this.a.a(Integer.parseInt(Core.ce(amapLoc.getLat(), 2)), 4);
                } catch (Throwable unused2) {
                    this.a.a(0, 4);
                }
                this.a.a(a(Math.round(amapLoc.getAccuracy())), 2);
                try {
                    this.a.a(a(Integer.parseInt(amapLoc.getRetype()), -100));
                } catch (NumberFormatException unused3) {
                    this.a.a(-100);
                }
                this.a.a(a((int) ((currentTimeMillis - amapLoc.getTime()) / 1000)), 2);
            }
            return;
        }
        this.a.a(0);
    }

    private void b(List<HisLocation> list) {
        if (list == null || list.size() <= 0) {
            this.a.a(0);
            return;
        }
        long a2 = e.a();
        int min = Math.min(127, list.size());
        this.a.a((byte) min);
        for (int i = 0; i < min; i++) {
            HisLocation hisLocation = list.get(i);
            try {
                this.a.a(a((int) ((a2 - hisLocation.time) / 1000)), 2);
            } catch (Throwable unused) {
                this.a.a(0, 2);
            }
            try {
                this.a.a(hisLocation.lon, 4);
            } catch (Throwable unused2) {
                this.a.a(0, 4);
            }
            try {
                this.a.a(hisLocation.lat, 4);
            } catch (Throwable unused3) {
                this.a.a(0, 4);
            }
            try {
                this.a.a(a(hisLocation.radius), 2);
            } catch (Throwable unused4) {
                this.a.a(0, 2);
            }
            try {
                this.a.a((int) (short) ((d(hisLocation.subretype) | (b(hisLocation.locType) << 13) | ((c(hisLocation.retype) + 64) << 6)) & 65535), 2);
            } catch (Throwable unused5) {
                this.a.a(0, 2);
            }
        }
    }

    private static String a(String str, boolean z, int i) {
        if (TextUtils.isEmpty(str)) {
            return "unkwn";
        }
        if (!z) {
            return String.valueOf(i);
        }
        String replace = str.replace("*", ".");
        try {
            if (replace.getBytes("UTF-8").length >= 32) {
                return String.valueOf(i);
            }
            return replace;
        } catch (Exception unused) {
            return String.valueOf(i);
        }
    }

    private static byte[] a(String str) {
        if (!TextUtils.isEmpty(str)) {
            try {
                return Base64.decode(str, 0);
            } catch (Exception unused) {
            }
        }
        return null;
    }

    private int b(int i) {
        return Math.min(Math.max(i, 0), 7);
    }

    private int c(int i) {
        return Math.min(Math.max(i, -64), 63);
    }

    private int d(int i) {
        return Math.min(Math.max(i, 0), 63);
    }
}
