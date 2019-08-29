package defpackage;

import com.autonavi.jni.eyrie.amap.redesign.maps.overlay.BaseLayer;
import com.autonavi.jni.eyrie.amap.redesign.maps.texture.OverlayTextureParam;
import com.autonavi.jni.eyrie.amap.redesign.maps.texture.TextureWrapper;
import com.autonavi.minimap.agroup.entity.MemberInfo;

/* renamed from: cis reason: default package */
/* compiled from: EFMemberNameOverlay */
public final class cis extends cio {
    cis(BaseLayer baseLayer, cjd cjd) {
        super(baseLayer, "MemberName", cjd);
        setCheckCover(true);
        setClickable(false);
    }

    /* access modifiers changed from: protected */
    public final OverlayTextureParam a(MemberInfo memberInfo) {
        BaseLayer baseLayer = this.f;
        StringBuilder sb = new StringBuilder("redesign://basemap/member/memberName/");
        sb.append(this.c.c());
        sb.append("/");
        StringBuilder sb2 = new StringBuilder();
        sb2.append(memberInfo.uid);
        sb2.append(memberInfo.nickname);
        sb2.append(String.valueOf(memberInfo.online));
        sb.append(agy.a(sb2.toString()));
        sb.append("/");
        sb.append(memberInfo.getIdentityType());
        return baseLayer.makeCustomTextureParam(sb.toString(), 0.5f, 1.0f, memberInfo);
    }

    /* access modifiers changed from: 0000 */
    public final TextureWrapper e(MemberInfo memberInfo) {
        return this.d.a(memberInfo, this.c.c());
    }
}
