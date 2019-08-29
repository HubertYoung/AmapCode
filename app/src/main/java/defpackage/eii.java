package defpackage;

import android.net.Uri;
import com.autonavi.annotation.Router;
import com.autonavi.bundle.account.api.IAccountService;
import com.autonavi.bundle.account.api.IAccountService.AccountType;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.route.train.page.TrainSearchPage;
import java.util.List;

@Router({"train"})
/* renamed from: eii reason: default package */
/* compiled from: TrainRouter */
public class eii extends esk {
    /* access modifiers changed from: private */
    public void a(IAccountService iAccountService) {
        iAccountService.a(AMapPageUtil.getPageContext(), AccountType.Taobao, (anq) new anq() {
            public final void loginOrBindCancel() {
            }

            public final void onComplete(boolean z) {
                if (z) {
                    eii.this.startPage((String) "amap.extra.route.train_order_list", (PageBundle) null);
                }
            }
        });
    }

    public boolean start(ese ese) {
        Uri uri = ese.a;
        if (uri == null) {
            return false;
        }
        if ("TrainSearch".equalsIgnoreCase(uri.getQueryParameter("featureName"))) {
            bid pageContext = AMapPageUtil.getPageContext();
            if (pageContext != null) {
                pageContext.startPage(TrainSearchPage.class, (PageBundle) null);
            }
            return true;
        } else if (!"train".equals(uri.getHost())) {
            return false;
        } else {
            List<String> pathSegments = uri.getPathSegments();
            if (pathSegments == null || pathSegments.size() == 0 || !"orderList".equalsIgnoreCase(pathSegments.get(0))) {
                return false;
            }
            final IAccountService iAccountService = (IAccountService) a.a.a(IAccountService.class);
            if (iAccountService != null) {
                if (iAccountService.a() && iAccountService.a(AccountType.Taobao)) {
                    startPage((String) "amap.extra.route.train_order_list", (PageBundle) null);
                } else if (!iAccountService.a() || iAccountService.a(AccountType.Taobao)) {
                    iAccountService.a(AMapPageUtil.getPageContext(), (anq) new anq() {
                        public final void loginOrBindCancel() {
                        }

                        public final void onComplete(boolean z) {
                            if (z) {
                                if (iAccountService.a(AccountType.Taobao)) {
                                    eii.this.startPage((String) "amap.extra.route.train_order_list", (PageBundle) null);
                                    return;
                                }
                                eii.this.a(iAccountService);
                            }
                        }
                    });
                } else {
                    a(iAccountService);
                }
            }
            return true;
        }
    }
}
