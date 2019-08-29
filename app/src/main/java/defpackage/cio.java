package defpackage;

import com.autonavi.jni.eyrie.amap.redesign.maps.overlay.BaseLayer;
import com.autonavi.jni.eyrie.amap.redesign.maps.overlay.BaseOverlay.EOverlaySubType;
import com.autonavi.jni.eyrie.amap.redesign.maps.overlay.PointOverlay;
import com.autonavi.jni.eyrie.amap.redesign.maps.overlay.item.PointOverlayItem;
import com.autonavi.jni.eyrie.amap.redesign.maps.texture.OverlayTextureParam;
import com.autonavi.jni.eyrie.amap.redesign.maps.typedef.Coord;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.agroup.entity.MemberInfo;
import java.util.ArrayList;

/* renamed from: cio reason: default package */
/* compiled from: EFBaseMemberPointOverlay */
public abstract class cio extends PointOverlay<PointOverlayItem> implements cjg {
    protected final Object a = new Object();
    protected boolean b = true;
    protected cjd c;
    protected cjj d;
    protected final Object e = new Object();
    protected BaseLayer f;
    private ArrayList<MemberInfo> g = new ArrayList<>();
    private PointOverlayItem h;

    /* access modifiers changed from: protected */
    public abstract OverlayTextureParam a(MemberInfo memberInfo);

    public void d(MemberInfo memberInfo) {
    }

    public cio(BaseLayer baseLayer, String str, cjd cjd) {
        super(baseLayer, str);
        this.f = baseLayer;
        this.c = cjd;
        this.d = new cjj(AMapPageUtil.getAppContext(), this.f, this);
        setAutoMoveToFocus(false);
        setAutoFocus(false);
        setSubType(EOverlaySubType.ESubTypeMember);
    }

    public synchronized void a(ArrayList<MemberInfo> arrayList) {
        this.g.clear();
        if (arrayList != null && arrayList.size() > 0) {
            this.g.addAll(arrayList);
        }
    }

    public final void a() {
        synchronized (this.a) {
            this.b = true;
        }
    }

    public final void b() {
        synchronized (this.a) {
            this.b = false;
        }
    }

    public synchronized void clear() {
        synchronized (this.e) {
            this.h = null;
        }
        this.d.a();
        super.clear();
    }

    /* access modifiers changed from: protected */
    public final void c() {
        synchronized (this.e) {
            this.h = null;
        }
        this.d.a();
        super.clear();
    }

    /* access modifiers changed from: protected */
    public final int a(int i) {
        int i2;
        synchronized (getItems()) {
            int itemCount = getItemCount();
            i2 = 0;
            while (true) {
                if (i2 >= itemCount) {
                    i2 = -1;
                    break;
                } else if (((MemberInfo) ((PointOverlayItem) getItem(i2)).tag).hashCode() == i) {
                    break;
                } else {
                    i2++;
                }
            }
        }
        return i2;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:16:?, code lost:
        r3 = r9.g.get(r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0025, code lost:
        if (r3.isMyself() == false) goto L_0x003b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0027, code lost:
        r4 = com.autonavi.sdk.location.LocationInstrument.getInstance().getLatestPosition();
        r3.lon = r4.getLongitude();
        r3.lat = r4.getLatitude();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x003b, code lost:
        r4 = b(r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0043, code lost:
        if (r2 >= getItemCount()) goto L_0x004c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0045, code lost:
        r5 = (com.autonavi.jni.eyrie.amap.redesign.maps.overlay.item.PointOverlayItem) getItem(r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x004c, code lost:
        r5 = new com.autonavi.jni.eyrie.amap.redesign.maps.overlay.item.PointOverlayItem();
        r5.coord = r4;
        r5.priority = r2;
        addItem(r5);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0058, code lost:
        r5.tag = r3;
        r5.coord = r4;
        r6 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x005f, code lost:
        if (r5.defaultTexture == null) goto L_0x0066;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x0061, code lost:
        r4 = r5.defaultTexture.uri;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0066, code lost:
        r4 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0067, code lost:
        r5.defaultTexture = a(r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x006f, code lost:
        if (r5.defaultTexture == null) goto L_0x0075;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x0071, code lost:
        r6 = r5.defaultTexture.uri;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x0079, code lost:
        if (android.text.TextUtils.isEmpty(r4) != false) goto L_0x0086;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x007f, code lost:
        if (r4.equals(r6) != false) goto L_0x0086;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x0081, code lost:
        r9.f.destroyTexture(r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x0086, code lost:
        updateItem(r5);
        r4 = r9.e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x008b, code lost:
        monitor-enter(r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:?, code lost:
        r6 = r9.c.a();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x0092, code lost:
        if (r6 == null) goto L_0x00a7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x0096, code lost:
        if (r6.uid == null) goto L_0x00a7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x00a0, code lost:
        if (r6.uid.equals(r3.uid) == false) goto L_0x00a7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x00a2, code lost:
        r9.h = r5;
        setItemVisble(r5, false, false, false);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:0x00a7, code lost:
        monitor-exit(r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:46:0x00a8, code lost:
        r2 = r2 + 1;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void d() {
        /*
            r9 = this;
            monitor-enter(r9)
            java.util.ArrayList<com.autonavi.minimap.agroup.entity.MemberInfo> r0 = r9.g     // Catch:{ all -> 0x0166 }
            int r0 = r0.size()     // Catch:{ all -> 0x0166 }
            r1 = 0
            r2 = 0
        L_0x0009:
            if (r2 >= r0) goto L_0x00b2
            java.lang.Object r3 = r9.a     // Catch:{ all -> 0x0166 }
            monitor-enter(r3)     // Catch:{ all -> 0x0166 }
            boolean r4 = r9.b     // Catch:{ all -> 0x00af }
            if (r4 != 0) goto L_0x0018
            r9.clear()     // Catch:{ all -> 0x00af }
            monitor-exit(r3)     // Catch:{ all -> 0x00af }
            monitor-exit(r9)
            return
        L_0x0018:
            monitor-exit(r3)     // Catch:{ all -> 0x00af }
            java.util.ArrayList<com.autonavi.minimap.agroup.entity.MemberInfo> r3 = r9.g     // Catch:{ all -> 0x0166 }
            java.lang.Object r3 = r3.get(r2)     // Catch:{ all -> 0x0166 }
            com.autonavi.minimap.agroup.entity.MemberInfo r3 = (com.autonavi.minimap.agroup.entity.MemberInfo) r3     // Catch:{ all -> 0x0166 }
            boolean r4 = r3.isMyself()     // Catch:{ all -> 0x0166 }
            if (r4 == 0) goto L_0x003b
            com.autonavi.sdk.location.LocationInstrument r4 = com.autonavi.sdk.location.LocationInstrument.getInstance()     // Catch:{ all -> 0x0166 }
            com.autonavi.common.model.GeoPoint r4 = r4.getLatestPosition()     // Catch:{ all -> 0x0166 }
            double r5 = r4.getLongitude()     // Catch:{ all -> 0x0166 }
            r3.lon = r5     // Catch:{ all -> 0x0166 }
            double r4 = r4.getLatitude()     // Catch:{ all -> 0x0166 }
            r3.lat = r4     // Catch:{ all -> 0x0166 }
        L_0x003b:
            com.autonavi.jni.eyrie.amap.redesign.maps.typedef.Coord r4 = b(r3)     // Catch:{ all -> 0x0166 }
            int r5 = r9.getItemCount()     // Catch:{ all -> 0x0166 }
            if (r2 >= r5) goto L_0x004c
            com.autonavi.jni.eyrie.amap.redesign.maps.overlay.item.BaseOverlayItem r5 = r9.getItem(r2)     // Catch:{ all -> 0x0166 }
            com.autonavi.jni.eyrie.amap.redesign.maps.overlay.item.PointOverlayItem r5 = (com.autonavi.jni.eyrie.amap.redesign.maps.overlay.item.PointOverlayItem) r5     // Catch:{ all -> 0x0166 }
            goto L_0x0058
        L_0x004c:
            com.autonavi.jni.eyrie.amap.redesign.maps.overlay.item.PointOverlayItem r5 = new com.autonavi.jni.eyrie.amap.redesign.maps.overlay.item.PointOverlayItem     // Catch:{ all -> 0x0166 }
            r5.<init>()     // Catch:{ all -> 0x0166 }
            r5.coord = r4     // Catch:{ all -> 0x0166 }
            r5.priority = r2     // Catch:{ all -> 0x0166 }
            r9.addItem(r5)     // Catch:{ all -> 0x0166 }
        L_0x0058:
            r5.tag = r3     // Catch:{ all -> 0x0166 }
            r5.coord = r4     // Catch:{ all -> 0x0166 }
            com.autonavi.jni.eyrie.amap.redesign.maps.texture.OverlayTextureParam r4 = r5.defaultTexture     // Catch:{ all -> 0x0166 }
            r6 = 0
            if (r4 == 0) goto L_0x0066
            com.autonavi.jni.eyrie.amap.redesign.maps.texture.OverlayTextureParam r4 = r5.defaultTexture     // Catch:{ all -> 0x0166 }
            java.lang.String r4 = r4.uri     // Catch:{ all -> 0x0166 }
            goto L_0x0067
        L_0x0066:
            r4 = r6
        L_0x0067:
            com.autonavi.jni.eyrie.amap.redesign.maps.texture.OverlayTextureParam r7 = r9.a(r3)     // Catch:{ all -> 0x0166 }
            r5.defaultTexture = r7     // Catch:{ all -> 0x0166 }
            com.autonavi.jni.eyrie.amap.redesign.maps.texture.OverlayTextureParam r7 = r5.defaultTexture     // Catch:{ all -> 0x0166 }
            if (r7 == 0) goto L_0x0075
            com.autonavi.jni.eyrie.amap.redesign.maps.texture.OverlayTextureParam r6 = r5.defaultTexture     // Catch:{ all -> 0x0166 }
            java.lang.String r6 = r6.uri     // Catch:{ all -> 0x0166 }
        L_0x0075:
            boolean r7 = android.text.TextUtils.isEmpty(r4)     // Catch:{ all -> 0x0166 }
            if (r7 != 0) goto L_0x0086
            boolean r6 = r4.equals(r6)     // Catch:{ all -> 0x0166 }
            if (r6 != 0) goto L_0x0086
            com.autonavi.jni.eyrie.amap.redesign.maps.overlay.BaseLayer r6 = r9.f     // Catch:{ all -> 0x0166 }
            r6.destroyTexture(r4)     // Catch:{ all -> 0x0166 }
        L_0x0086:
            r9.updateItem(r5)     // Catch:{ all -> 0x0166 }
            java.lang.Object r4 = r9.e     // Catch:{ all -> 0x0166 }
            monitor-enter(r4)     // Catch:{ all -> 0x0166 }
            cjd r6 = r9.c     // Catch:{ all -> 0x00ac }
            com.autonavi.minimap.agroup.entity.MemberInfo r6 = r6.a()     // Catch:{ all -> 0x00ac }
            if (r6 == 0) goto L_0x00a7
            java.lang.String r7 = r6.uid     // Catch:{ all -> 0x00ac }
            if (r7 == 0) goto L_0x00a7
            java.lang.String r6 = r6.uid     // Catch:{ all -> 0x00ac }
            java.lang.String r3 = r3.uid     // Catch:{ all -> 0x00ac }
            boolean r3 = r6.equals(r3)     // Catch:{ all -> 0x00ac }
            if (r3 == 0) goto L_0x00a7
            r9.h = r5     // Catch:{ all -> 0x00ac }
            r9.setItemVisble(r5, r1, r1, r1)     // Catch:{ all -> 0x00ac }
        L_0x00a7:
            monitor-exit(r4)     // Catch:{ all -> 0x00ac }
            int r2 = r2 + 1
            goto L_0x0009
        L_0x00ac:
            r0 = move-exception
            monitor-exit(r4)     // Catch:{ all -> 0x00ac }
            throw r0     // Catch:{ all -> 0x0166 }
        L_0x00af:
            r0 = move-exception
            monitor-exit(r3)     // Catch:{ all -> 0x00af }
            throw r0     // Catch:{ all -> 0x0166 }
        L_0x00b2:
            java.util.Map r1 = r9.getItems()     // Catch:{ all -> 0x0166 }
            monitor-enter(r1)     // Catch:{ all -> 0x0166 }
            int r2 = r9.getItemCount()     // Catch:{ all -> 0x0163 }
            r3 = 1
            int r2 = r2 - r3
        L_0x00bd:
            if (r2 < r0) goto L_0x00cb
            com.autonavi.jni.eyrie.amap.redesign.maps.overlay.item.BaseOverlayItem r4 = r9.getItem(r2)     // Catch:{ all -> 0x0163 }
            com.autonavi.jni.eyrie.amap.redesign.maps.overlay.item.PointOverlayItem r4 = (com.autonavi.jni.eyrie.amap.redesign.maps.overlay.item.PointOverlayItem) r4     // Catch:{ all -> 0x0163 }
            r9.removeItem(r4, r3)     // Catch:{ all -> 0x0163 }
            int r2 = r2 + -1
            goto L_0x00bd
        L_0x00cb:
            monitor-exit(r1)     // Catch:{ all -> 0x0163 }
            cjj r0 = r9.d     // Catch:{ all -> 0x0166 }
            java.util.ArrayList<com.autonavi.minimap.agroup.entity.MemberInfo> r1 = r9.g     // Catch:{ all -> 0x0166 }
            cjm r0 = r0.a     // Catch:{ all -> 0x0166 }
            java.util.HashMap r2 = new java.util.HashMap     // Catch:{ all -> 0x0166 }
            r2.<init>()     // Catch:{ all -> 0x0166 }
            java.util.HashMap<java.lang.Integer, java.lang.Integer> r3 = r0.c     // Catch:{ all -> 0x0166 }
            r2.putAll(r3)     // Catch:{ all -> 0x0166 }
            java.util.HashMap<java.lang.Integer, java.lang.Integer> r3 = r0.b     // Catch:{ all -> 0x0166 }
            r3.clear()     // Catch:{ all -> 0x0166 }
            java.util.HashMap<java.lang.Integer, java.lang.Integer> r3 = r0.c     // Catch:{ all -> 0x0166 }
            r3.clear()     // Catch:{ all -> 0x0166 }
            r3 = 100
            r0.a = r3     // Catch:{ all -> 0x0166 }
            if (r1 == 0) goto L_0x013d
            java.util.Iterator r1 = r1.iterator()     // Catch:{ all -> 0x0166 }
        L_0x00f0:
            boolean r4 = r1.hasNext()     // Catch:{ all -> 0x0166 }
            if (r4 == 0) goto L_0x013d
            java.lang.Object r4 = r1.next()     // Catch:{ all -> 0x0166 }
            com.autonavi.minimap.agroup.entity.MemberInfo r4 = (com.autonavi.minimap.agroup.entity.MemberInfo) r4     // Catch:{ all -> 0x0166 }
            int r5 = r4.hashCode()     // Catch:{ all -> 0x0166 }
            java.lang.Integer r5 = java.lang.Integer.valueOf(r5)     // Catch:{ all -> 0x0166 }
            java.lang.Object r5 = r2.remove(r5)     // Catch:{ all -> 0x0166 }
            java.lang.Integer r5 = (java.lang.Integer) r5     // Catch:{ all -> 0x0166 }
            if (r5 == 0) goto L_0x00f0
            int r5 = r5.intValue()     // Catch:{ all -> 0x0166 }
            int r4 = r4.hashCode()     // Catch:{ all -> 0x0166 }
            java.util.HashMap<java.lang.Integer, java.lang.Integer> r6 = r0.b     // Catch:{ all -> 0x0166 }
            java.lang.Integer r7 = java.lang.Integer.valueOf(r5)     // Catch:{ all -> 0x0166 }
            java.lang.Integer r8 = java.lang.Integer.valueOf(r4)     // Catch:{ all -> 0x0166 }
            r6.put(r7, r8)     // Catch:{ all -> 0x0166 }
            java.util.HashMap<java.lang.Integer, java.lang.Integer> r6 = r0.c     // Catch:{ all -> 0x0166 }
            java.lang.Integer r4 = java.lang.Integer.valueOf(r4)     // Catch:{ all -> 0x0166 }
            java.lang.Integer r7 = java.lang.Integer.valueOf(r5)     // Catch:{ all -> 0x0166 }
            r6.put(r4, r7)     // Catch:{ all -> 0x0166 }
            int r4 = r0.a     // Catch:{ all -> 0x0166 }
            if (r5 <= r4) goto L_0x00f0
            r0.a = r5     // Catch:{ all -> 0x0166 }
            int r4 = r0.a     // Catch:{ all -> 0x0166 }
            r5 = 9999(0x270f, float:1.4012E-41)
            if (r4 < r5) goto L_0x00f0
            r0.a = r3     // Catch:{ all -> 0x0166 }
            goto L_0x00f0
        L_0x013d:
            java.util.Set r1 = r2.entrySet()     // Catch:{ all -> 0x0166 }
            java.util.Iterator r1 = r1.iterator()     // Catch:{ all -> 0x0166 }
        L_0x0145:
            boolean r2 = r1.hasNext()     // Catch:{ all -> 0x0166 }
            if (r2 == 0) goto L_0x0161
            java.lang.Object r2 = r1.next()     // Catch:{ all -> 0x0166 }
            java.util.Map$Entry r2 = (java.util.Map.Entry) r2     // Catch:{ all -> 0x0166 }
            java.lang.Object r3 = r2.getValue()     // Catch:{ all -> 0x0166 }
            if (r3 == 0) goto L_0x0145
            java.util.ArrayDeque<java.lang.Integer> r3 = r0.d     // Catch:{ all -> 0x0166 }
            java.lang.Object r2 = r2.getValue()     // Catch:{ all -> 0x0166 }
            r3.add(r2)     // Catch:{ all -> 0x0166 }
            goto L_0x0145
        L_0x0161:
            monitor-exit(r9)
            return
        L_0x0163:
            r0 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x0163 }
            throw r0     // Catch:{ all -> 0x0166 }
        L_0x0166:
            r0 = move-exception
            monitor-exit(r9)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.cio.d():void");
    }

    protected static Coord b(MemberInfo memberInfo) {
        return memberInfo != null ? new Coord(memberInfo.lon, memberInfo.lat) : new Coord(0, 0);
    }

    public void c(MemberInfo memberInfo) {
        synchronized (this.e) {
            if (memberInfo != null) {
                try {
                    this.h = (PointOverlayItem) getItem(memberInfo.getIndex());
                    if (this.h != null) {
                        setItemVisble(this.h, false, false, false);
                    }
                } finally {
                }
            }
        }
    }

    public void e() {
        synchronized (this.e) {
            if (this.h != null) {
                setItemVisble(this.h, true, true, true);
                clearFocus();
                this.h = null;
            }
        }
    }
}
