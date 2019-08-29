package defpackage;

import com.autonavi.minimap.bundle.agroup.api.IAgroupOverlayService.MemberIconStyle;

/* renamed from: cja reason: default package */
/* compiled from: NavigationAGroupConfig */
public final class cja implements cuf {
    private boolean a;

    public final boolean a() {
        return true;
    }

    public final int b() {
        return 6;
    }

    public cja(boolean z) {
        this.a = z;
    }

    public final MemberIconStyle c() {
        return this.a ? MemberIconStyle.SMALL_NIGHT : MemberIconStyle.SMALL_DAY;
    }
}
