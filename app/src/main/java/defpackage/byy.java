package defpackage;

import android.os.Message;

/* renamed from: byy reason: default package */
/* compiled from: IndoorWidgetChangeListener */
public final class byy implements bzt {
    public bxo a;

    public final void onFloorChanged(int i, int i2) {
        elc.h = String.valueOf(i2);
        if (i != i2 && this.a != null) {
            bxo bxo = this.a;
            cde suspendManager = bxo.f.getSuspendManager();
            bxc X = bxo.X();
            if (!(suspendManager == null || X == null || suspendManager.c().a() == null)) {
                c cVar = bxo.I;
                if (cVar != null) {
                    cVar.removeMessages(2);
                    Message obtainMessage = cVar.obtainMessage();
                    obtainMessage.what = 2;
                    cVar.sendMessageDelayed(obtainMessage, 0);
                }
            }
        }
    }
}
