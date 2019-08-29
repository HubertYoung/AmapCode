package defpackage;

import android.os.Message;
import android.widget.FrameLayout;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import java.lang.ref.WeakReference;

/* renamed from: nh reason: default package */
/* compiled from: AbstractDriveDialog */
public abstract class nh {
    public Message a;
    public final WeakReference<bid> b;
    public FrameLayout c;
    public boolean d;

    public final boolean a() {
        return b() && this.d;
    }

    public final boolean b() {
        bid bid = (bid) this.b.get();
        if (bid == null || bid.getContentView() == null || !(bid instanceof AbstractBasePage)) {
            return false;
        }
        return ((AbstractBasePage) bid).isAlive();
    }
}
