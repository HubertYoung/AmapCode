package defpackage;

import android.net.Uri;
import com.autonavi.jni.eyrie.amap.redesign.maps.overlay.BaseLayer;
import com.autonavi.jni.eyrie.amap.redesign.maps.texture.OverlayTextureParam;
import com.autonavi.jni.eyrie.amap.redesign.maps.texture.TextureWrapper;
import com.autonavi.jni.eyrie.amap.redesign.maps.vmap.IVPageContext;
import com.autonavi.minimap.agroup.entity.MemberInfo;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* renamed from: cir reason: default package */
/* compiled from: EFMemberLayer */
public final class cir extends BaseLayer {
    public cip a;
    public ciq b;
    public cis c;
    public Map<String, TextureWrapper> d = new HashMap();

    public cir(IVPageContext iVPageContext, cjd cjd) {
        super(iVPageContext);
        this.b = new ciq(this, cjd);
        this.c = new cis(this, cjd);
        this.a = new cip(this, cjd);
    }

    public final TextureWrapper loadTexture(OverlayTextureParam overlayTextureParam) {
        if (!overlayTextureParam.uri.startsWith("redesign://basemap/member/")) {
            return null;
        }
        List<String> pathSegments = Uri.parse(overlayTextureParam.uri).getPathSegments();
        if (pathSegments == null || pathSegments.size() < 3) {
            return null;
        }
        TextureWrapper remove = this.d.remove(overlayTextureParam.uri);
        if (remove != null) {
            return remove;
        }
        if (!(overlayTextureParam.data instanceof MemberInfo)) {
            return null;
        }
        if ("memberName/".startsWith(pathSegments.get(1))) {
            return this.c.e((MemberInfo) overlayTextureParam.data);
        }
        if ("memberIcon/".startsWith(pathSegments.get(1))) {
            return this.b.a(overlayTextureParam, (MemberInfo) overlayTextureParam.data);
        }
        if ("memberFocusIcon/".startsWith(pathSegments.get(1))) {
            return this.a.a(overlayTextureParam, (MemberInfo) overlayTextureParam.data);
        }
        if ("memberFocusName/".startsWith(pathSegments.get(1))) {
            return this.a.f((MemberInfo) overlayTextureParam.data);
        }
        return null;
    }
}
