package defpackage;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout.LayoutParams;
import com.autonavi.jni.eyrie.amap.redesign.maps.overlay.BaseLayer;
import com.autonavi.jni.eyrie.amap.redesign.maps.texture.OverlayTextureParam;
import com.autonavi.jni.eyrie.amap.redesign.maps.texture.TextureWrapper;
import com.autonavi.minimap.R;
import com.autonavi.minimap.agroup.entity.MemberInfo;
import com.autonavi.minimap.agroup.widget.MemberIconView;
import com.autonavi.minimap.agroup.widget.MemberTipView;
import com.autonavi.minimap.bundle.agroup.api.IAgroupOverlayService.MemberIconStyle;
import java.util.concurrent.ConcurrentHashMap;

/* renamed from: cjj reason: default package */
/* compiled from: EFMemberPointItemFactory */
public final class cjj implements cjf {
    public cjm a;
    private Context b;
    private BaseLayer c;
    private LayoutInflater d;
    private final TextureWrapper[] e = new TextureWrapper[10];
    private MemberTipView f;
    private MemberTipView g;
    private ConcurrentHashMap<Integer, MemberIconView> h = new ConcurrentHashMap<>();
    private cjg i;

    public cjj(Context context, BaseLayer baseLayer, cjg cjg) {
        this.b = context;
        this.c = baseLayer;
        this.d = LayoutInflater.from(context);
        this.a = new cjm();
        this.i = cjg;
    }

    public final TextureWrapper a(OverlayTextureParam overlayTextureParam, MemberInfo memberInfo, MemberIconStyle memberIconStyle) {
        int i2;
        TextureWrapper textureWrapper;
        if (memberInfo.isMyself()) {
            MemberIconView memberIconView = (MemberIconView) this.d.inflate(R.layout.agroup_member_overlay_icon_layout, null);
            ((ImageView) memberIconView.findViewById(R.id.member_icon)).setImageDrawable(this.b.getResources().getDrawable(R.drawable.agroup_my_icon_default));
            memberIconView.setLayoutParams(new LayoutParams(-2, -2));
            if (!memberIconView.initWithStyle(memberInfo, memberIconStyle, this)) {
                this.h.put(Integer.valueOf(memberInfo.hashCode()), memberIconView);
                memberIconView.setAssignedOverlayTextureParam(overlayTextureParam);
            }
            return this.c.makeTextureWrapper((View) memberIconView);
        }
        switch (memberIconStyle) {
            case BIG_NIGHT:
                i2 = 4;
                break;
            case SMALL_NIGHT:
                i2 = 3;
                break;
            case BIG_DAY:
                i2 = 2;
                break;
            case MID_DAY:
                i2 = 1;
                break;
            default:
                i2 = 0;
                break;
        }
        if (!memberInfo.online) {
            i2 += 5;
        }
        synchronized (this.e) {
            if (i2 >= 0) {
                try {
                    if (i2 < this.e.length) {
                        textureWrapper = this.e[i2];
                    }
                } finally {
                    while (true) {
                    }
                }
            }
            textureWrapper = null;
        }
        TextureWrapper textureWrapper2 = TextUtils.isEmpty(memberInfo.imgUrl) ? textureWrapper : null;
        if (textureWrapper2 == null) {
            MemberIconView memberIconView2 = (MemberIconView) this.d.inflate(R.layout.agroup_member_overlay_icon_layout, null);
            memberIconView2.setLayoutParams(new LayoutParams(-2, -2));
            if (!memberIconView2.initWithStyle(memberInfo, memberIconStyle, this)) {
                this.h.put(Integer.valueOf(memberInfo.hashCode()), memberIconView2);
                memberIconView2.setAssignedOverlayTextureParam(overlayTextureParam);
                textureWrapper2 = textureWrapper;
            }
            if (textureWrapper2 == null) {
                textureWrapper2 = this.c.makeTextureWrapper((View) memberIconView2);
                synchronized (this.e) {
                    if (i2 >= 0) {
                        try {
                            if (i2 < this.e.length) {
                                this.e[i2] = textureWrapper2;
                            }
                        } finally {
                        }
                    }
                }
            }
        }
        return textureWrapper2;
    }

    public final TextureWrapper a(MemberInfo memberInfo, MemberIconStyle memberIconStyle) {
        MemberTipView a2 = a(memberIconStyle);
        if (a2 == null) {
            return null;
        }
        a2.initWithStyle(memberInfo, memberIconStyle, null);
        return this.c.makeTextureWrapper((View) a2);
    }

    private MemberTipView a(MemberIconStyle memberIconStyle) {
        MemberTipView memberTipView;
        int b2 = b(memberIconStyle);
        if (b2 == R.layout.agroup_member_overlay_big_tip_layout) {
            if (this.f == null) {
                this.f = (MemberTipView) this.d.inflate(b2, null);
            }
            this.f.destroyDrawingCache();
            memberTipView = this.f;
        } else if (b2 != R.layout.agroup_member_overlay_tip_layout) {
            return null;
        } else {
            if (this.g == null) {
                this.g = (MemberTipView) this.d.inflate(b2, null);
            }
            this.g.destroyDrawingCache();
            memberTipView = this.g;
        }
        if (memberTipView.getLayoutParams() == null) {
            memberTipView.setLayoutParams(new LayoutParams(-2, -2));
        }
        return memberTipView;
    }

    private static int b(MemberIconStyle memberIconStyle) {
        if (memberIconStyle == MemberIconStyle.BIG_DAY || memberIconStyle == MemberIconStyle.BIG_NIGHT) {
            return R.layout.agroup_member_overlay_big_tip_layout;
        }
        return R.layout.agroup_member_overlay_tip_layout;
    }

    public final void a(MemberInfo memberInfo, MemberIconView memberIconView) {
        if (memberInfo != null) {
            MemberIconView remove = this.h.remove(Integer.valueOf(memberInfo.hashCode()));
            OverlayTextureParam assignedOverlayTextureParam = memberIconView.getAssignedOverlayTextureParam();
            if (!(remove == null || remove != memberIconView || assignedOverlayTextureParam == null || assignedOverlayTextureParam.data == null)) {
                try {
                    assignedOverlayTextureParam.data = null;
                    remove.destroyDrawingCache();
                    TextureWrapper makeTextureWrapper = this.c.makeTextureWrapper((View) memberIconView);
                    if (this.c instanceof cir) {
                        ((cir) this.c).d.put(assignedOverlayTextureParam.uri, makeTextureWrapper);
                    }
                    this.c.destroyTexture(assignedOverlayTextureParam.uri);
                } catch (Exception unused) {
                    assignedOverlayTextureParam.uri = null;
                }
                if (this.i != null) {
                    this.i.d(memberInfo);
                }
            }
        }
    }

    public final void a() {
        this.h.clear();
        this.a.a();
        synchronized (this.e) {
            for (int i2 = 0; i2 < this.e.length; i2++) {
                this.e[i2] = null;
            }
        }
    }
}
