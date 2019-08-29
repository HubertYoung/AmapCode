package defpackage;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import com.amap.bundle.drivecommon.tools.DriveUtil;
import com.autonavi.common.model.POI;

/* renamed from: dzj reason: default package */
/* compiled from: CoachPoiHistoryStatusManager */
public final class dzj {
    public boolean a = false;
    public boolean b = false;
    public boolean c = false;
    private dzr d;
    private boolean e = true;

    public dzj(@NonNull dzr dzr) {
        this.d = dzr;
    }

    public final void a(boolean z) {
        if (z && !this.e) {
            this.a = false;
            this.b = false;
            this.c = false;
        }
        this.e = z;
    }

    public final void a() {
        if (this.e && this.a && this.b && this.c) {
            String str = this.d.b.f;
            String str2 = this.d.b.g;
            if (this.d.c != null && this.d.d != null && !TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2) && !DriveUtil.MY_LOCATION_LOADING.equals(str) && !DriveUtil.MY_LOCATION_LOADING.equals(str2)) {
                final POI clone = this.d.c.clone();
                final POI clone2 = this.d.d.clone();
                clone.setName(str);
                clone2.setName(str2);
                ahm.c(new Runnable() {
                    public final void run() {
                        ebm.a(clone, clone2);
                    }
                });
                this.e = false;
            }
        }
    }
}
