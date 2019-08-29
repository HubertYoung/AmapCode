package defpackage;

import android.net.Uri;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.annotation.Router;
import com.autonavi.bundle.account.api.IAccountService;
import com.autonavi.bundle.account.api.IAccountService.AccountType;
import com.autonavi.bundle.carownerservice.ajx.ModuleCarOwner;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.route.coach.util.CoachActionLogUtil;
import java.util.List;

@Router({"coach"})
/* renamed from: dzb reason: default package */
/* compiled from: CoachRouter */
public class dzb extends esk {
    /* access modifiers changed from: private */
    public void a(IAccountService iAccountService, final Uri uri) {
        iAccountService.a(AMapPageUtil.getPageContext(), AccountType.Taobao, (anq) new anq() {
            public final void loginOrBindCancel() {
            }

            public final void onComplete(boolean z) {
                if (z) {
                    dzb.b(uri);
                    dzb.this.startPage((String) "amap.extra.route.coach_order_list", (PageBundle) null);
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public static void b(Uri uri) {
        if (uri != null) {
            String queryParameter = uri.getQueryParameter(ModuleCarOwner.KEY_FROM_SOURCE);
            if (queryParameter != null) {
                try {
                    CoachActionLogUtil.a(Integer.parseInt(queryParameter));
                } catch (Exception unused) {
                    AMapLog.e("CoachRouter", "addActionLog exception: ".concat(String.valueOf(queryParameter)));
                }
            }
        }
    }

    public boolean start(ese ese) {
        final Uri uri = ese.a;
        if (uri == null) {
            return false;
        }
        List<String> pathSegments = uri.getPathSegments();
        if (pathSegments == null || pathSegments.size() == 0 || !"orderList".equalsIgnoreCase(pathSegments.get(0))) {
            return false;
        }
        final IAccountService iAccountService = (IAccountService) a.a.a(IAccountService.class);
        if (iAccountService != null) {
            if (iAccountService.a() && iAccountService.a(AccountType.Taobao)) {
                b(uri);
                startPage((String) "amap.extra.route.coach_order_list", (PageBundle) null);
            } else if (!iAccountService.a() || iAccountService.a(AccountType.Taobao)) {
                iAccountService.a(AMapPageUtil.getPageContext(), (anq) new anq() {
                    public final void loginOrBindCancel() {
                    }

                    public final void onComplete(boolean z) {
                        if (z) {
                            if (iAccountService.a(AccountType.Taobao)) {
                                dzb.b(uri);
                                dzb.this.startPage((String) "amap.extra.route.coach_order_list", (PageBundle) null);
                                return;
                            }
                            dzb.this.a(iAccountService, uri);
                        }
                    }
                });
            } else {
                a(iAccountService, uri);
            }
        }
        return true;
    }
}
