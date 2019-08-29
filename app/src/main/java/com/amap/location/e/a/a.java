package com.amap.location.e.a;

import android.content.Context;
import android.location.Location;
import android.os.Handler;
import android.text.TextUtils;
import com.amap.location.common.f.e;
import com.amap.location.common.model.AmapLoc;
import com.amap.location.common.model.CellState;
import com.amap.location.common.model.FPS;
import com.amap.location.common.model.HisLocation;
import com.amap.location.common.model.WiFi;
import com.amap.location.e.b;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/* compiled from: HistoryLocationRecorder */
public class a {
    private LinkedList<HisLocation> a = new LinkedList<>();
    private File b;
    /* access modifiers changed from: private */
    public boolean c = false;
    /* access modifiers changed from: private */
    public Handler d;
    /* access modifiers changed from: private */
    public boolean e;
    /* access modifiers changed from: private */
    public Runnable f = new Runnable() {
        public void run() {
            if (!a.this.c) {
                if (a.this.e) {
                    a.this.d();
                    a.this.e = false;
                }
                if (a.this.d != null) {
                    a.this.d.postDelayed(a.this.f, 60000);
                }
            }
        }
    };

    public a(Context context, Handler handler) {
        this.d = handler;
        if (context != null) {
            this.b = new File(context.getFilesDir().getAbsolutePath(), "hisloc");
            if (!this.b.getParentFile().exists()) {
                this.b.getParentFile().mkdirs();
            }
        }
        c();
        if (this.d != null) {
            this.d.removeCallbacks(this.f);
            this.d.postDelayed(this.f, 60000);
        }
    }

    public void a() {
        if (this.d != null) {
            this.d.removeCallbacks(this.f);
        }
        this.f.run();
    }

    public void b() {
        this.f.run();
        if (this.d != null) {
            this.d.removeCallbacks(this.f);
        }
        this.a.clear();
        this.c = true;
    }

    public void a(Location location) {
        HisLocation makeLocationByGpsLocation = HisLocation.makeLocationByGpsLocation(location);
        if (makeLocationByGpsLocation != null) {
            Iterator it = this.a.iterator();
            HisLocation hisLocation = null;
            HisLocation hisLocation2 = null;
            int i = 0;
            while (it.hasNext()) {
                HisLocation hisLocation3 = (HisLocation) it.next();
                if (hisLocation3.locType == 1) {
                    if (hisLocation2 == null) {
                        hisLocation2 = hisLocation3;
                    }
                    i++;
                    hisLocation = hisLocation3;
                }
            }
            if (hisLocation == null || makeLocationByGpsLocation.time - hisLocation.time >= b.c * 1000 || makeLocationByGpsLocation.distanceTo(hisLocation) >= ((double) b.b)) {
                if (i >= b.d) {
                    this.a.remove(hisLocation2);
                }
                if (this.a.size() >= b.a) {
                    this.a.removeFirst();
                }
                this.a.add(makeLocationByGpsLocation);
                this.e = true;
            }
        }
    }

    public void a(AmapLoc amapLoc) {
        HisLocation makeLocationByNetworkLocation = HisLocation.makeLocationByNetworkLocation(amapLoc);
        if (makeLocationByNetworkLocation != null && this.a.size() > 0) {
            if (makeLocationByNetworkLocation.locType == 2) {
                HisLocation last = this.a.getLast();
                if (!(last.lon == makeLocationByNetworkLocation.lon && last.lat == makeLocationByNetworkLocation.lat && last.radius == makeLocationByNetworkLocation.radius)) {
                    if (this.a.size() >= b.a) {
                        this.a.removeFirst();
                    }
                    this.a.add(makeLocationByNetworkLocation);
                    this.e = true;
                }
            } else if (!this.a.contains(makeLocationByNetworkLocation)) {
                if (this.a.size() >= b.a) {
                    this.a.removeFirst();
                }
                this.a.add(makeLocationByNetworkLocation);
                this.e = true;
            }
        }
    }

    public List<HisLocation> a(FPS fps) {
        if (!b(fps)) {
            return null;
        }
        long currentTimeMillis = System.currentTimeMillis();
        ArrayList arrayList = new ArrayList();
        Iterator it = this.a.iterator();
        while (it.hasNext()) {
            HisLocation hisLocation = (HisLocation) it.next();
            if (currentTimeMillis - hisLocation.time < b.e * 1000) {
                arrayList.add(hisLocation);
            }
        }
        return arrayList;
    }

    private boolean b(FPS fps) {
        if (fps == null) {
            return false;
        }
        HashSet hashSet = new HashSet();
        if (fps.cellStatus != null) {
            if (fps.cellStatus.mainCell != null && fps.cellStatus.mainCell.isValid()) {
                hashSet.add(fps.cellStatus.mainCell.getKeyWithOutInterface());
            }
            if (fps.cellStatus.neighbors != null) {
                for (CellState next : fps.cellStatus.neighbors) {
                    if (next.isValid()) {
                        hashSet.add(next.getKeyWithOutInterface());
                    }
                }
            }
            if (fps.cellStatus.cellStateList2 != null) {
                for (CellState next2 : fps.cellStatus.cellStateList2) {
                    if (next2.isValid()) {
                        hashSet.add(next2.getKeyWithOutInterface());
                    }
                }
            }
            if (hashSet.size() >= b.g) {
                hashSet.clear();
                return false;
            }
        }
        hashSet.clear();
        if (fps.wifiStatus != null) {
            if (fps.wifiStatus.mainWifi != null) {
                StringBuilder sb = new StringBuilder();
                sb.append(fps.wifiStatus.mainWifi.mac);
                hashSet.add(sb.toString());
            }
            if (fps.wifiStatus.getWifiList() != null) {
                for (WiFi wiFi : fps.wifiStatus.getWifiList()) {
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(wiFi.mac);
                    hashSet.add(sb2.toString());
                }
            }
        }
        if (hashSet.size() >= b.f) {
            hashSet.clear();
            return false;
        }
        hashSet.clear();
        return true;
    }

    private void c() {
        for (String makeLocationByText : e.c(this.b)) {
            HisLocation makeLocationByText2 = HisLocation.makeLocationByText(makeLocationByText);
            if (makeLocationByText2 != null) {
                this.a.add(makeLocationByText2);
            }
        }
    }

    /* access modifiers changed from: private */
    public void d() {
        StringBuilder sb = new StringBuilder();
        Iterator it = this.a.iterator();
        while (it.hasNext()) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(((HisLocation) it.next()).toString());
            sb2.append("\n");
            sb.append(sb2.toString());
        }
        String sb3 = sb.toString();
        if (!TextUtils.isEmpty(sb)) {
            e.a(sb3, this.b, false);
        }
    }
}
