package com.autonavi.minimap.route.coach.util;

import android.text.TextUtils;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.utils.ui.CompatDialog;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.bundle.account.api.IAccountService;
import com.autonavi.common.Callback;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;
import com.autonavi.minimap.order.OrderRequestHolder;
import com.autonavi.minimap.order.param.BusOrderSubmitRequest;
import com.autonavi.minimap.route.coach.model.CoachPlanItem;
import com.autonavi.minimap.route.coach.net.CoachPurchaseCallback;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.net.UnknownHostException;

public final class CoachPurchaseUtil {
    private static CoachPurchaseUtil c;
    CompatDialog a;
    final anq b = new anq() {
        public final void loginOrBindCancel() {
        }

        public final void onComplete(boolean z) {
        }
    };

    class CoachPurchasingListener implements Callback<dzp> {
        AbstractBasePage<?> a;
        CoachPlanItem b;
        String c;
        String d;
        String e;

        public CoachPurchasingListener(AbstractBasePage<?> abstractBasePage, CoachPlanItem coachPlanItem, String str, String str2, String str3) {
            this.a = abstractBasePage;
            this.b = coachPlanItem;
            this.c = str2;
            this.d = str3;
            this.e = str;
        }

        public void callback(dzp dzp) {
            StringBuilder sb = new StringBuilder();
            sb.append(dzp.a);
            sb.append(", ");
            sb.append(dzp.errorMessage);
            sb.append(", ");
            sb.append(dzp.errorCode);
            AMapLog.d("CoachRequest", sb.toString());
            if (CoachPurchaseUtil.this.a != null && CoachPurchaseUtil.this.a.isShowing()) {
                CoachPurchaseUtil.this.a.dismiss();
            }
            if (dzp == null || !this.a.isAlive()) {
                return;
            }
            if (dzp.errorCode == 14) {
                IAccountService iAccountService = (IAccountService) defpackage.esb.a.a.a(IAccountService.class);
                if (iAccountService != null) {
                    bid pageContext = AMapPageUtil.getPageContext();
                    a aVar = new a(this.a, this.b, this.e, this.c, this.d);
                    iAccountService.a(pageContext, (anq) aVar);
                    ToastHelper.showLongToast(this.a.getString(R.string.train_plan_not_login));
                }
            } else if (dzp.errorCode == 44) {
                IAccountService iAccountService2 = (IAccountService) defpackage.esb.a.a.a(IAccountService.class);
                if (iAccountService2 != null) {
                    iAccountService2.a(this.a.getPageContext(), this.a.getString(R.string.train_plan_buy_ticket_bind_phone), CoachPurchaseUtil.this.b);
                    ToastHelper.showLongToast(this.a.getString(R.string.train_plan_buy_ticket_bind_phone));
                }
            } else if (TextUtils.isEmpty(dzp.a)) {
                ToastHelper.showLongToast(this.a.getString(R.string.train_plan_elong_network_error));
            } else {
                aja aja = new aja(CoachPurchaseUtil.a(dzp.a));
                aja.b = new ajf() {
                    public final boolean g() {
                        return true;
                    }

                    public final boolean h() {
                        return true;
                    }
                };
                aix aix = (aix) defpackage.esb.a.a.a(aix.class);
                if (aix != null) {
                    aix.a(AMapPageUtil.getPageContext(), aja);
                }
            }
        }

        public void error(Throwable th, boolean z) {
            if (CoachPurchaseUtil.this.a != null && CoachPurchaseUtil.this.a.isShowing()) {
                CoachPurchaseUtil.this.a.dismiss();
            }
            if (z || !(th instanceof UnknownHostException)) {
                ToastHelper.showLongToast(this.a.getString(R.string.train_plan_network_status_error_callback));
            } else {
                ToastHelper.showLongToast(this.a.getString(R.string.train_plan_network_status_error_not_reach));
            }
        }
    }

    class a implements anq {
        AbstractBasePage<?> a;
        CoachPlanItem b;
        String c;
        String d;
        String e;

        public final void loginOrBindCancel() {
        }

        public a(AbstractBasePage<?> abstractBasePage, CoachPlanItem coachPlanItem, String str, String str2, String str3) {
            this.a = abstractBasePage;
            this.b = coachPlanItem;
            this.c = str2;
            this.d = str3;
            this.e = str;
        }

        public final void onComplete(boolean z) {
            String str;
            if (z) {
                IAccountService iAccountService = (IAccountService) defpackage.esb.a.a.a(IAccountService.class);
                if (iAccountService != null) {
                    ant e2 = iAccountService.e();
                    if (iAccountService.a()) {
                        if (e2 == null) {
                            str = null;
                        } else {
                            str = e2.h;
                        }
                        if (!TextUtils.isEmpty(str)) {
                            CoachPurchaseUtil.this.a(this.a, this.b, this.e, this.c, this.d);
                            return;
                        }
                        iAccountService.a(this.a.getPageContext(), this.a.getString(R.string.train_plan_buy_ticket_bind_phone), CoachPurchaseUtil.this.b);
                    }
                }
            }
        }
    }

    private CoachPurchaseUtil() {
    }

    public static synchronized CoachPurchaseUtil a() {
        CoachPurchaseUtil coachPurchaseUtil;
        synchronized (CoachPurchaseUtil.class) {
            try {
                if (c == null) {
                    c = new CoachPurchaseUtil();
                }
                coachPurchaseUtil = c;
            }
        }
        return coachPurchaseUtil;
    }

    public final synchronized void a(AbstractBasePage<?> abstractBasePage, CoachPlanItem coachPlanItem, String str, String str2, String str3) {
        BusOrderSubmitRequest busOrderSubmitRequest = new BusOrderSubmitRequest();
        StringBuilder sb = new StringBuilder();
        sb.append(coachPlanItem.dateSource);
        busOrderSubmitRequest.b = sb.toString();
        busOrderSubmitRequest.c = coachPlanItem.busNumber;
        busOrderSubmitRequest.g = (int) coachPlanItem.fullPrice;
        StringBuilder sb2 = new StringBuilder();
        sb2.append(str);
        sb2.append(Token.SEPARATOR);
        sb2.append(coachPlanItem.departTime);
        busOrderSubmitRequest.f = sb2.toString();
        busOrderSubmitRequest.e = coachPlanItem.depName;
        busOrderSubmitRequest.d = coachPlanItem.depCity;
        busOrderSubmitRequest.i = coachPlanItem.arrName;
        busOrderSubmitRequest.h = coachPlanItem.arrCity;
        CoachPurchasingListener coachPurchasingListener = new CoachPurchasingListener(abstractBasePage, coachPlanItem, str, str2, str3);
        CoachPurchaseCallback coachPurchaseCallback = new CoachPurchaseCallback(new dzp(), coachPurchasingListener);
        this.a = aav.a(busOrderSubmitRequest, AMapPageUtil.getAppContext().getString(R.string.progress_message));
        this.a.show();
        OrderRequestHolder.getInstance().sendBusOrderSubmit(busOrderSubmitRequest, coachPurchaseCallback);
    }

    public static String a(String str) {
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        try {
            String encode = URLEncoder.encode(str, "utf-8");
            StringBuilder sb = new StringBuilder("http://f.amap.com/new/redirect?target=");
            sb.append(encode);
            sb.append("&from=amap");
            return sb.toString();
        } catch (UnsupportedEncodingException unused) {
            return str;
        }
    }
}
