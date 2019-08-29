package defpackage;

import android.content.Context;
import android.text.TextUtils;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import com.amap.bundle.planhome.page.AjxPlanResultPage;
import com.amap.bundle.planhome.view.UgcBusResultEditView;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.bundle.account.api.IAccountService;
import com.autonavi.bundle.routecommon.entity.IBusRouteResult;
import com.autonavi.minimap.R;
import com.autonavi.minimap.ajx3.views.AmapAjxView;
import com.autonavi.minimap.ajx3.views.AmapAjxViewInterface;

/* renamed from: adi reason: default package */
/* compiled from: UgcEditViewController */
public final class adi implements a {
    public UgcBusResultEditView a;
    public Context b;
    public IBusRouteResult c;
    public boolean d = false;
    public AjxPlanResultPage e;
    public AmapAjxView f;

    public adi(AjxPlanResultPage ajxPlanResultPage) {
        this.e = ajxPlanResultPage;
        this.b = ajxPlanResultPage.getContext();
        this.a = new UgcBusResultEditView(this.b);
    }

    public final void a() {
        a(false);
    }

    public final void a(String str) {
        if (TextUtils.isEmpty(str)) {
            ToastHelper.showLongToast(this.b.getString(R.string.ugc_bus_result_entrance_et_empty_toast));
        } else if (this.c != null) {
            ToastHelper.showLongToast(this.b.getString(R.string.route_ugc_submit_suc));
            a(true);
            this.a.setEtText(null);
            ejz ejz = new ejz();
            if (!TextUtils.isEmpty(b())) {
                ejz.a = b();
            }
            ejz.c = this.c.getBsid();
            ejz.d = str;
            ejz.e = String.valueOf(System.currentTimeMillis() / 1000);
            ejz.b = 1;
            if (this.c.getBusPathsResult() != null) {
                String str2 = this.c.getBusPathsResult().mShowInput_Type;
                if (TextUtils.isEmpty(str2)) {
                    str2 = "0";
                }
                ejz.h = Integer.parseInt(str2);
            }
            bdp bdp = (bdp) a.a.a(bdp.class);
            if (bdp != null) {
                bdp.a(ejz);
            }
        }
    }

    public final void a(boolean z) {
        if (this.e.isAlive()) {
            a(false, R.anim.fade_out_to_bottom, null);
            atb atb = (atb) a.a.a(atb.class);
            if (atb != null) {
                atb.b().a((AmapAjxViewInterface) this.f, z);
            }
            ((InputMethodManager) this.b.getSystemService("input_method")).hideSoftInputFromWindow(this.a.getWindowToken(), 2);
        }
    }

    public final void a(boolean z, int i, a aVar) {
        this.a.setOnUgcBusResultEditListener(aVar);
        this.a.setVisibility(z ? 0 : 8);
        this.d = z;
        this.a.setAnimation(AnimationUtils.loadAnimation(this.b, i));
    }

    private static String b() {
        IAccountService iAccountService = (IAccountService) a.a.a(IAccountService.class);
        if (iAccountService == null) {
            return "";
        }
        ant e2 = iAccountService.e();
        if (e2 == null) {
            return "";
        }
        return e2.a;
    }
}
