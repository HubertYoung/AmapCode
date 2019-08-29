package defpackage;

import com.amap.bundle.aosservice.request.AosRequest;
import com.autonavi.common.Callback.a;

/* renamed from: aas reason: default package */
/* compiled from: AosRequestCancelable */
public final class aas implements a {
    private boolean a = false;
    private AosRequest b;

    public aas(AosRequest aosRequest) {
        this.b = aosRequest;
    }

    public final void cancel() {
        if (!this.a) {
            this.a = true;
            if (this.b != null) {
                this.b.cancel();
            }
        }
    }

    public final boolean isCancelled() {
        return this.a;
    }
}
