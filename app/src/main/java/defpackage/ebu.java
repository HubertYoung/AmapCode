package defpackage;

import android.view.View;
import com.autonavi.bundle.account.api.IAccountService;
import com.autonavi.minimap.widget.SyncPopupWindow;

/* renamed from: ebu reason: default package */
/* compiled from: BaseRouteSyncDataController */
public final class ebu {
    SyncPopupWindow a;
    public boolean b = false;

    public final void a() {
        if (this.a != null) {
            this.a.hide();
            this.b = true;
        }
        bim.aa().a((biv) null);
    }

    public final void b() {
        if (this.a != null) {
            this.a.hide();
            this.b = true;
        }
    }

    public final void a(final View view) {
        bim.aa().a((biv) new biv() {
            public final void saveSucess() {
                IAccountService iAccountService = (IAccountService) a.a.a(IAccountService.class);
                if (!(iAccountService != null && iAccountService.a())) {
                    if (ebu.this.a == null) {
                        ebu.this.a = new SyncPopupWindow(view);
                        ebu.this.a.show();
                        ebu.this.b = false;
                        return;
                    }
                    ebu.this.a.show();
                    ebu.this.b = false;
                }
            }
        });
    }
}
