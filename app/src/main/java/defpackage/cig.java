package defpackage;

import com.autonavi.minimap.bundle.agroup.api.IDataService.TeamStatus;

/* renamed from: cig reason: default package */
/* compiled from: AGroupServiceHelper */
public final class cig implements com.autonavi.minimap.bundle.agroup.api.IDataService.a {
    public cjt a = cjt.a();
    public a b;
    private boolean c;

    /* renamed from: cig$a */
    /* compiled from: AGroupServiceHelper */
    public interface a {
        void a();
    }

    public final void onSuperGroupInfoChanged() {
    }

    public final void onTeamInfoChanged() {
    }

    public final void onTeamStatusChanged(TeamStatus teamStatus) {
    }

    public final void onMemberBaseInfoChanged() {
        if (this.b != null) {
            this.b.a();
        }
        this.c = true;
    }

    public final void onMemberLocationInfoChanged() {
        if (!this.c && this.b != null) {
            this.b.a();
        }
    }
}
