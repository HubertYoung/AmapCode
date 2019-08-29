package defpackage;

import com.amap.bundle.drivecommon.tools.DriveUtil;
import defpackage.sv;

/* renamed from: np reason: default package */
/* compiled from: NaviEntranceModel */
public final class np<Presenter extends sv> extends st<Presenter> {
    String a;

    public np(Presenter presenter) {
        super(presenter);
    }

    public final void a(String str) {
        if ("car".equals(str) || DriveUtil.NAVI_TYPE_TRUCK.equals(str)) {
            this.a = str;
        } else {
            this.a = "car";
        }
    }
}
