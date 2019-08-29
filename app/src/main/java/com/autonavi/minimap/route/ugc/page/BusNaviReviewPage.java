package com.autonavi.minimap.route.ugc.page;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.alipay.mobile.h5container.api.H5PageData;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.bundle.account.api.IAccountService;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.IPresenter;
import com.autonavi.minimap.R;

public class BusNaviReviewPage extends ReviewPage<ekh> {
    private static String i = "";
    /* access modifiers changed from: private */
    public ejz f = new ejz();
    private String g = "";
    private String h = "";
    private MapSharePreference j = new MapSharePreference(SharePreferenceName.user_route_method_info);

    /* access modifiers changed from: protected */
    public final void a() {
        PageBundle arguments = getArguments();
        if (arguments != null) {
            this.g = arguments.getString(H5PageData.KEY_UC_START, "");
            this.h = arguments.getString("end", "");
            i = arguments.getString("mainDes", "");
            this.f.c = arguments.getString("bsid", "");
            this.f.b = arguments.getInt("source", 2);
            this.f.f = i;
            this.f.g = arguments.getInt("project_no", 1);
            this.f.h = arguments.getInt("title_no", 1);
        }
    }

    /* access modifiers changed from: protected */
    public final void a(View view) {
        ((ImageView) view.findViewById(R.id.iv_indicator)).setImageResource(R.drawable.icon_bus_review);
        ((TextView) view.findViewById(R.id.tv_start)).setText(this.g);
        ((TextView) view.findViewById(R.id.tv_end)).setText(this.h);
        axs.a((TextView) view.findViewById(R.id.tv_route), i, R.drawable.bus_result_item_main_des_next);
    }

    /* access modifiers changed from: protected */
    public final void b(View view) {
        ((Button) view.findViewById(R.id.btn_submit)).setOnClickListener(new OnClickListener() {
            public final void onClick(View view) {
                BusNaviReviewPage.this.f.j = (int) BusNaviReviewPage.this.a.getRating();
                if (BusNaviReviewPage.this.f.j == 0) {
                    ToastHelper.showToast(BusNaviReviewPage.this.getString(R.string.ugc_rating_star_hint));
                    return;
                }
                BusNaviReviewPage.this.j.putIntValue("route_bus_detail_ugc_close_count", 0);
                BusNaviReviewPage.this.f.a = BusNaviReviewPage.d();
                BusNaviReviewPage.this.f.d = BusNaviReviewPage.this.b.getText().toString().trim();
                ejz a2 = BusNaviReviewPage.this.f;
                StringBuilder sb = new StringBuilder();
                sb.append(System.currentTimeMillis() / 1000);
                a2.e = sb.toString();
                BusNaviReviewPage.this.f.i = BusNaviReviewPage.this.e();
                eke.a(BusNaviReviewPage.this.getContext()).a(BusNaviReviewPage.this.f);
                ToastHelper.showToast(BusNaviReviewPage.this.getString(R.string.ugc_thank_you_for_your_submit));
                BusNaviReviewPage.this.setResult(ResultType.OK, (PageBundle) null);
                BusNaviReviewPage.this.finish();
            }
        });
    }

    public final void b() {
        super.b();
    }

    /* access modifiers changed from: protected */
    public final String[] c() {
        return new String[]{"步行时间长", "车内环境差", "票价不准", "等车时间长", "人多拥挤"};
    }

    public /* synthetic */ IPresenter createPresenter() {
        return new ekh(this);
    }

    static /* synthetic */ String d() {
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
}
