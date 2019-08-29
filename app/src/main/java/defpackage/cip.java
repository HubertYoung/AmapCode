package defpackage;

import android.text.TextUtils;
import com.autonavi.jni.eyrie.amap.redesign.maps.overlay.BaseLayer;
import com.autonavi.jni.eyrie.amap.redesign.maps.overlay.BaseOverlay;
import com.autonavi.jni.eyrie.amap.redesign.maps.overlay.IClickListener;
import com.autonavi.jni.eyrie.amap.redesign.maps.overlay.item.BaseOverlayItem;
import com.autonavi.jni.eyrie.amap.redesign.maps.overlay.item.PointOverlayItem;
import com.autonavi.jni.eyrie.amap.redesign.maps.texture.OverlayTextureParam;
import com.autonavi.jni.eyrie.amap.redesign.maps.texture.TextureWrapper;
import com.autonavi.jni.eyrie.amap.redesign.maps.typedef.Coord;
import com.autonavi.minimap.agroup.entity.MemberInfo;
import com.autonavi.minimap.agroup.widget.MemberBigTipView;
import com.autonavi.minimap.bundle.agroup.api.IAgroupOverlayService.MemberIconStyle;
import java.util.ArrayList;
import java.util.Map;

/* renamed from: cip reason: default package */
/* compiled from: EFMemberFocusOverlay */
public final class cip extends cio implements IClickListener {
    private MemberInfo g;
    private a h;
    private b i;

    /* renamed from: cip$a */
    /* compiled from: EFMemberFocusOverlay */
    class a implements Runnable {
        private MemberInfo b;

        /* synthetic */ a(cip cip, MemberInfo memberInfo, byte b2) {
            this(memberInfo);
        }

        private a(MemberInfo memberInfo) {
            this.b = memberInfo;
        }

        public final void run() {
            cip.a(cip.this, this.b);
        }
    }

    /* renamed from: cip$b */
    /* compiled from: EFMemberFocusOverlay */
    public interface b {
        boolean a(MemberInfo memberInfo);
    }

    /* access modifiers changed from: protected */
    public final OverlayTextureParam a(MemberInfo memberInfo) {
        return null;
    }

    public cip(BaseLayer baseLayer, cjd cjd) {
        super(baseLayer, "MemberFocus", cjd);
        setClickListener(this);
    }

    public final synchronized void a(ArrayList<MemberInfo> arrayList) {
        super.a(arrayList);
        e((arrayList == null || arrayList.size() <= 0) ? null : arrayList.get(0));
    }

    public final synchronized void e(MemberInfo memberInfo) {
        if (!(this.g == null || this.g.hashCode() == memberInfo.hashCode())) {
            clear();
        }
        this.g = memberInfo;
        if (this.g == null) {
            clear();
        }
    }

    public final void c(MemberInfo memberInfo) {
        a(memberInfo, (b) null);
    }

    public final void a(MemberInfo memberInfo, b bVar) {
        synchronized (this.e) {
            if (memberInfo == null) {
                try {
                } catch (Throwable th) {
                    throw th;
                }
            } else {
                if (!(this.g == null || this.g.hashCode() == memberInfo.hashCode())) {
                    c();
                }
                this.g = memberInfo;
                this.i = bVar;
                d();
            }
        }
    }

    public final void e() {
        synchronized (this.e) {
            this.g = null;
        }
        c();
    }

    private MemberIconStyle f() {
        switch (this.c.c()) {
            case BIG_NIGHT:
            case SMALL_NIGHT:
                return MemberIconStyle.BIG_NIGHT;
            default:
                return MemberIconStyle.BIG_DAY;
        }
    }

    private void g(MemberInfo memberInfo) {
        c();
        PointOverlayItem pointOverlayItem = new PointOverlayItem();
        pointOverlayItem.coord = b(memberInfo);
        pointOverlayItem.tag = memberInfo;
        BaseLayer baseLayer = this.f;
        StringBuilder sb = new StringBuilder("redesign://basemap/member/memberFocusIcon/");
        sb.append(f());
        sb.append("/");
        sb.append(j(memberInfo));
        pointOverlayItem.defaultTexture = baseLayer.makeCustomTextureParam(sb.toString(), 0.5f, 1.0f, memberInfo);
        if (!g() && !h(memberInfo)) {
            addItem(pointOverlayItem);
            PointOverlayItem pointOverlayItem2 = new PointOverlayItem();
            pointOverlayItem2.coord = b(memberInfo);
            pointOverlayItem2.tag = memberInfo;
            BaseLayer baseLayer2 = this.f;
            StringBuilder sb2 = new StringBuilder("redesign://basemap/member/memberFocusName/");
            sb2.append(f());
            sb2.append("/");
            sb2.append(i(memberInfo));
            pointOverlayItem2.defaultTexture = baseLayer2.makeCustomTextureParam(sb2.toString(), 0.5f, 1.0f, memberInfo);
            if (g() || h(memberInfo)) {
                c();
            } else {
                addItem(pointOverlayItem2);
            }
        }
    }

    public final void d() {
        synchronized (this.e) {
            if (this.h != null) {
                ahm.b(this.h);
            }
            if (this.g != null) {
                this.h = new a(this, this.g, 0);
                ahm.a(this.h, 20);
            } else {
                c();
            }
        }
    }

    private boolean g() {
        boolean z;
        synchronized (this.a) {
            z = !this.b;
            if (!this.b) {
                this.i = null;
            }
        }
        return z;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:14:0x001f, code lost:
        if (r5.hashCode() == r4.g.hashCode()) goto L_0x000b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:6:0x0009, code lost:
        if (r4.g == null) goto L_0x000b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:7:0x000b, code lost:
        r1 = false;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean h(com.autonavi.minimap.agroup.entity.MemberInfo r5) {
        /*
            r4 = this;
            java.lang.Object r0 = r4.e
            monitor-enter(r0)
            r1 = 1
            r2 = 0
            if (r5 != 0) goto L_0x000f
            com.autonavi.minimap.agroup.entity.MemberInfo r3 = r4.g     // Catch:{ all -> 0x000d }
            if (r3 != 0) goto L_0x000f
        L_0x000b:
            r1 = 0
            goto L_0x0021
        L_0x000d:
            r5 = move-exception
            goto L_0x0023
        L_0x000f:
            if (r5 == 0) goto L_0x0021
            com.autonavi.minimap.agroup.entity.MemberInfo r3 = r4.g     // Catch:{ all -> 0x000d }
            if (r3 == 0) goto L_0x0021
            int r5 = r5.hashCode()     // Catch:{ all -> 0x000d }
            com.autonavi.minimap.agroup.entity.MemberInfo r3 = r4.g     // Catch:{ all -> 0x000d }
            int r3 = r3.hashCode()     // Catch:{ all -> 0x000d }
            if (r5 == r3) goto L_0x000b
        L_0x0021:
            monitor-exit(r0)     // Catch:{ all -> 0x000d }
            return r1
        L_0x0023:
            monitor-exit(r0)     // Catch:{ all -> 0x000d }
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.cip.h(com.autonavi.minimap.agroup.entity.MemberInfo):boolean");
    }

    public final void onClick(BaseOverlay baseOverlay, BaseOverlayItem baseOverlayItem, int i2) {
        if (this.c != null) {
            this.c.b();
        }
    }

    public final void d(MemberInfo memberInfo) {
        if (memberInfo != null && !h(memberInfo) && getItemCount() > 0) {
            updateItem((PointOverlayItem) getItem(0));
        }
    }

    /* access modifiers changed from: 0000 */
    public final TextureWrapper a(OverlayTextureParam overlayTextureParam, MemberInfo memberInfo) {
        return this.d.a(overlayTextureParam, memberInfo, f());
    }

    /* access modifiers changed from: 0000 */
    public final TextureWrapper f(MemberInfo memberInfo) {
        return this.d.a(memberInfo, f());
    }

    private static String i(MemberInfo memberInfo) {
        StringBuilder sb = new StringBuilder();
        sb.append(memberInfo.uid);
        sb.append(memberInfo.nickname);
        sb.append(String.valueOf(memberInfo.online));
        sb.append(String.valueOf(memberInfo.isLeader()));
        sb.append(MemberBigTipView.getFormatUpdateTime(memberInfo));
        sb.append(MemberBigTipView.getFormatDistance(memberInfo));
        return agy.a(sb.toString());
    }

    private static String j(MemberInfo memberInfo) {
        StringBuilder sb = new StringBuilder();
        sb.append(memberInfo.uid);
        sb.append(memberInfo.imgUrl);
        sb.append(String.valueOf(memberInfo.online));
        return agy.a(sb.toString());
    }

    static /* synthetic */ void a(cip cip, MemberInfo memberInfo) {
        if (memberInfo != null) {
            Map items = cip.getItems();
            if (items == null || items.size() != 2) {
                cip.g(memberInfo);
            } else {
                PointOverlayItem pointOverlayItem = (PointOverlayItem) cip.getItem(0);
                PointOverlayItem pointOverlayItem2 = (PointOverlayItem) cip.getItem(1);
                if (pointOverlayItem == null || pointOverlayItem2 == null) {
                    cip.g(memberInfo);
                    return;
                }
                Coord b2 = b(memberInfo);
                pointOverlayItem.tag = memberInfo;
                pointOverlayItem.coord = b2;
                String str = pointOverlayItem.defaultTexture != null ? pointOverlayItem.defaultTexture.uri : null;
                BaseLayer baseLayer = cip.f;
                StringBuilder sb = new StringBuilder("redesign://basemap/member/memberFocusIcon/");
                sb.append(cip.f());
                sb.append("/");
                sb.append(j(memberInfo));
                pointOverlayItem.defaultTexture = baseLayer.makeCustomTextureParam(sb.toString(), 0.5f, 1.0f, memberInfo);
                Object obj = pointOverlayItem.defaultTexture != null ? pointOverlayItem.defaultTexture.uri : null;
                if (!TextUtils.isEmpty(str) && !str.equals(obj)) {
                    cip.f.destroyTexture(str);
                }
                cip.updateItem(pointOverlayItem, true);
                pointOverlayItem2.tag = memberInfo;
                pointOverlayItem2.coord = b2;
                String str2 = pointOverlayItem2.defaultTexture != null ? pointOverlayItem2.defaultTexture.uri : null;
                BaseLayer baseLayer2 = cip.f;
                StringBuilder sb2 = new StringBuilder("redesign://basemap/member/memberFocusName/");
                sb2.append(cip.f());
                sb2.append("/");
                sb2.append(i(memberInfo));
                pointOverlayItem2.defaultTexture = baseLayer2.makeCustomTextureParam(sb2.toString(), 0.5f, 1.0f, memberInfo);
                Object obj2 = pointOverlayItem2.defaultTexture != null ? pointOverlayItem2.defaultTexture.uri : null;
                if (!TextUtils.isEmpty(str2) && !str2.equals(obj2)) {
                    cip.f.destroyTexture(str2);
                }
                cip.updateItem(pointOverlayItem2, true);
                if (cip.g()) {
                    cip.c();
                }
            }
            if (cip.i != null && cip.i.a(memberInfo)) {
                cip.i = null;
            }
        }
    }
}
