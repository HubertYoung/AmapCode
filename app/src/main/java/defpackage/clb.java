package defpackage;

import android.app.Application;
import android.support.annotation.NonNull;
import com.amap.bundle.statistics.LogManager;
import com.autonavi.ae.AEUtil;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.sdk.location.LocationInstrument;

/* renamed from: clb reason: default package */
/* compiled from: LogManagerInit */
public final class clb extends cky {
    afh a;
    afi b;

    /* renamed from: clb$a */
    /* compiled from: LogManagerInit */
    class a implements afj {
        private a() {
        }

        /* synthetic */ a(clb clb, byte b) {
            this();
        }

        public final afh a() {
            if (clb.this.a == null) {
                clb.this.a = new afh() {
                    public final GeoPoint a() {
                        return LocationInstrument.getInstance().getLatestPosition();
                    }

                    public final GeoPoint b() {
                        return LocationInstrument.getInstance().getLatestPosition(5);
                    }
                };
            }
            return clb.this.a;
        }

        public final afi b() {
            if (clb.this.b == null) {
                clb.this.b = new afi() {
                    public final String a() {
                        return AEUtil.getMapVersion();
                    }
                };
            }
            return clb.this.b;
        }
    }

    /* access modifiers changed from: 0000 */
    @NonNull
    public final String a() {
        return "LogManagerInit";
    }

    /* access modifiers changed from: 0000 */
    public final void a(Application application) {
        LogManager.getInstance();
        afk.a = new a(this, 0);
    }
}
