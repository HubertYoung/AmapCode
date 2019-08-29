package defpackage;

import android.view.View;
import com.autonavi.bundle.account.api.IAccountService;
import com.autonavi.minimap.widget.SyncPopupWindow;

/* renamed from: dur reason: default package */
/* compiled from: RouteSyncDataModel */
public final class dur implements biv {
    public boolean a;
    public View b;
    private SyncPopupWindow c;

    public final void a() {
        if (this.c != null) {
            this.c.hide();
        }
        this.a = true;
    }

    public final void saveSucess() {
        boolean z;
        IAccountService iAccountService = (IAccountService) a.a.a(IAccountService.class);
        if (iAccountService == null) {
            z = false;
        } else {
            z = iAccountService.a();
        }
        if (!z) {
            if (this.c == null) {
                this.c = new SyncPopupWindow(this.b);
                this.a = true;
            }
            if (this.a) {
                this.c.show();
                this.a = false;
            }
        }
    }
}
