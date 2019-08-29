package defpackage;

import android.text.TextUtils;
import com.autonavi.jni.eyrie.amap.redesign.maps.overlay.BaseLayer;
import com.autonavi.jni.eyrie.amap.redesign.maps.overlay.IClickListener;
import com.autonavi.jni.eyrie.amap.redesign.maps.overlay.item.BaseOverlayItem;
import com.autonavi.jni.eyrie.amap.redesign.maps.overlay.item.PointOverlayItem;
import com.autonavi.jni.eyrie.amap.redesign.maps.texture.OverlayTextureParam;
import com.autonavi.jni.eyrie.amap.redesign.maps.texture.TextureWrapper;
import com.autonavi.minimap.agroup.entity.MemberInfo;

/* renamed from: ciq reason: default package */
/* compiled from: EFMemberIconOverlay */
public final class ciq extends cio implements IClickListener {
    ciq(BaseLayer baseLayer, cjd cjd) {
        super(baseLayer, "MemberIcon", cjd);
        setClickListener(this);
    }

    /* access modifiers changed from: protected */
    public final OverlayTextureParam a(MemberInfo memberInfo) {
        BaseLayer baseLayer = this.f;
        StringBuilder sb = new StringBuilder("redesign://basemap/member/memberIcon/");
        sb.append(this.c.c());
        sb.append("/");
        StringBuilder sb2 = new StringBuilder();
        sb2.append(memberInfo.uid);
        sb2.append(memberInfo.imgUrl);
        sb2.append(String.valueOf(memberInfo.online));
        sb.append(agy.a(sb2.toString()));
        return baseLayer.makeCustomTextureParam(sb.toString(), 0.5f, 1.0f, memberInfo);
    }

    /* access modifiers changed from: 0000 */
    public final TextureWrapper a(OverlayTextureParam overlayTextureParam, MemberInfo memberInfo) {
        return this.d.a(overlayTextureParam, memberInfo, this.c.c());
    }

    /* JADX WARNING: Removed duplicated region for block: B:16:0x003e  */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x0049  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void onClick(com.autonavi.jni.eyrie.amap.redesign.maps.overlay.BaseOverlay r3, final com.autonavi.jni.eyrie.amap.redesign.maps.overlay.item.BaseOverlayItem r4, int r5) {
        /*
            r2 = this;
            boolean r3 = r4 instanceof com.autonavi.jni.eyrie.amap.redesign.maps.overlay.item.PointOverlayItem
            if (r3 == 0) goto L_0x004c
            bid r3 = com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil.getPageContext()
            r5 = 1
            if (r3 == 0) goto L_0x003b
            esb r0 = defpackage.esb.a.a
            java.lang.Class<bci> r1 = defpackage.bci.class
            esc r0 = r0.a(r1)
            bci r0 = (defpackage.bci) r0
            if (r0 == 0) goto L_0x0023
            boolean r0 = r0.a(r3)
            if (r0 == 0) goto L_0x0023
            r3.finish()
            goto L_0x003c
        L_0x0023:
            esb r0 = defpackage.esb.a.a
            java.lang.Class<bdl> r1 = defpackage.bdl.class
            esc r0 = r0.a(r1)
            bdl r0 = (defpackage.bdl) r0
            if (r0 == 0) goto L_0x003b
            boolean r0 = r0.a(r3)
            if (r0 == 0) goto L_0x003b
            r3.finish()
            goto L_0x003c
        L_0x003b:
            r5 = 0
        L_0x003c:
            if (r5 == 0) goto L_0x0049
            ciq$1 r3 = new ciq$1
            r3.<init>(r4)
            r4 = 500(0x1f4, double:2.47E-321)
            defpackage.ahm.a(r3, r4)
            return
        L_0x0049:
            r2.a(r4)
        L_0x004c:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.ciq.onClick(com.autonavi.jni.eyrie.amap.redesign.maps.overlay.BaseOverlay, com.autonavi.jni.eyrie.amap.redesign.maps.overlay.item.BaseOverlayItem, int):void");
    }

    /* access modifiers changed from: private */
    public void a(BaseOverlayItem baseOverlayItem) {
        PointOverlayItem pointOverlayItem = (PointOverlayItem) baseOverlayItem;
        if (pointOverlayItem.tag instanceof MemberInfo) {
            MemberInfo memberInfo = (MemberInfo) pointOverlayItem.tag;
            if (this.c != null) {
                this.c.a(memberInfo);
            }
            return;
        }
        if (this.c != null) {
            this.c.b();
        }
    }

    public final void d(MemberInfo memberInfo) {
        if (memberInfo != null && !TextUtils.isEmpty(memberInfo.uid)) {
            int a = a(memberInfo.hashCode());
            if (a >= 0) {
                updateItem((PointOverlayItem) getItem(a));
            }
        }
    }
}
