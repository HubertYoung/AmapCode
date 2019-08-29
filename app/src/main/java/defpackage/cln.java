package defpackage;

import android.app.Application;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.bundle.account.api.IAccountService;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;

/* renamed from: cln reason: default package */
/* compiled from: SyncInit */
public final class cln extends cky {
    /* access modifiers changed from: 0000 */
    @NonNull
    public final String a() {
        return "SyncInit";
    }

    /* access modifiers changed from: 0000 */
    public final void a(Application application) {
        boolean z;
        Application application2 = AMapAppGlobal.getApplication();
        if (application2.getFilesDir() != null) {
            bim.aa().a(application2.getFilesDir().getPath(), (bij) new bij() {
                public final String a() {
                    IAccountService iAccountService = (IAccountService) a.a.a(IAccountService.class);
                    if (iAccountService == null) {
                        return "";
                    }
                    ant e = iAccountService.e();
                    if (e == null) {
                        return "";
                    }
                    return e.a;
                }

                public final void b() {
                    IAccountService iAccountService = (IAccountService) a.a.a(IAccountService.class);
                    if (iAccountService != null) {
                        iAccountService.a(AMapPageUtil.getPageContext(), (anq) new anq() {
                            public final void loginOrBindCancel() {
                            }

                            public final void onComplete(boolean z) {
                            }
                        });
                    }
                }

                public final boolean c() {
                    IAccountService iAccountService = (IAccountService) a.a.a(IAccountService.class);
                    if (iAccountService == null) {
                        return false;
                    }
                    return iAccountService.a();
                }
            });
            String str = "";
            coy coy = (coy) ank.a(coy.class);
            if (coy != null) {
                str = coy.a();
            }
            IAccountService iAccountService = (IAccountService) a.a.a(IAccountService.class);
            if (iAccountService == null) {
                z = false;
            } else {
                z = iAccountService.a();
            }
            if (z || (!TextUtils.isEmpty(str) && !"public".equals(str))) {
                bim.aa().j(str);
            } else {
                bim.aa().C();
            }
        }
    }
}
