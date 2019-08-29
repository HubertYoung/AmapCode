package defpackage;

import mtopsdk.common.util.TBSdkLog;
import mtopsdk.mtop.domain.MtopResponse;

/* renamed from: fey reason: default package */
/* compiled from: MtopBaseListenerProxy */
public class fey extends feq {
    protected few a = null;
    public MtopResponse b = null;
    public Object c = null;
    protected boolean d = false;

    public fey(few few) {
        this.a = few;
    }

    public void onHeader(fev fev, Object obj) {
        if (this.a instanceof c) {
            ((c) this.a).onHeader(fev, obj);
        }
    }

    public void onFinished(feu feu, Object obj) {
        if (!(feu == null || feu.a == null)) {
            this.b = feu.a;
            this.c = obj;
        }
        synchronized (this) {
            try {
                notifyAll();
            } catch (Exception unused) {
                TBSdkLog.d("mtopsdk.MtopListenerProxy", "[onFinished] notify error");
            }
        }
        if ((this.a instanceof b) && (!this.d || (this.b != null && this.b.isApiSuccess()))) {
            ((b) this.a).onFinished(feu, obj);
        }
    }
}
