package com.autonavi.minimap.route.ugc.page;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.alipay.mobile.h5container.api.H5PageData;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.bundle.account.api.IAccountService;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.IPresenter;
import com.autonavi.minimap.R;
import com.autonavi.minimap.comment.CommentRequestHolder;
import com.autonavi.minimap.route.ugc.net.callback.FootNaviReviewRequestCallback;
import com.autonavi.minimap.route.ugc.net.param.FootNaviReviewParam;

public class FootNaviReviewPage extends ReviewPage<eki> {
    /* access modifiers changed from: private */
    public eka f = new eka();
    private String g = "";
    private String h = "";
    private String i = "";

    /* access modifiers changed from: protected */
    public final void a() {
        PageBundle arguments = getArguments();
        if (arguments != null) {
            this.g = arguments.getString(H5PageData.KEY_UC_START, "");
            this.h = arguments.getString("end", "");
            this.f.b = arguments.getString("naviid", "");
            this.f.d = arguments.getInt("source", 2);
        }
    }

    /* access modifiers changed from: protected */
    public final void a(View view) {
        ((ImageView) view.findViewById(R.id.iv_indicator)).setImageResource(R.drawable.icon_foot_review);
        ((TextView) view.findViewById(R.id.tv_start)).setText(this.g);
        ((TextView) view.findViewById(R.id.tv_end)).setText(this.h);
        ((TextView) view.findViewById(R.id.tv_route)).setVisibility(8);
    }

    /* access modifiers changed from: protected */
    public final void b(View view) {
        ((Button) view.findViewById(R.id.btn_submit)).setOnClickListener(new OnClickListener() {
            public final void onClick(View view) {
                FootNaviReviewPage.this.f.g = (int) FootNaviReviewPage.this.a.getRating();
                if (FootNaviReviewPage.this.f.g == 0) {
                    ToastHelper.showToast(FootNaviReviewPage.this.getString(R.string.ugc_rating_star_hint));
                    return;
                }
                FootNaviReviewPage.this.f.a = FootNaviReviewPage.d();
                FootNaviReviewPage.this.f.c = FootNaviReviewPage.this.b.getText().toString().trim();
                eka a2 = FootNaviReviewPage.this.f;
                StringBuilder sb = new StringBuilder();
                sb.append(System.currentTimeMillis() / 1000);
                a2.e = sb.toString();
                FootNaviReviewPage.this.f.f = FootNaviReviewPage.this.e();
                eke a3 = eke.a(FootNaviReviewPage.this.getContext());
                eka a4 = FootNaviReviewPage.this.f;
                CommentRequestHolder.getInstance().sendWalkCreate(FootNaviReviewParam.buildParam(a4), new FootNaviReviewRequestCallback(a3.a, a4));
                ToastHelper.showToast(FootNaviReviewPage.this.getString(R.string.ugc_thank_you_for_your_submit));
                FootNaviReviewPage.this.setResult(ResultType.OK, (PageBundle) null);
                FootNaviReviewPage.this.finish();
            }
        });
    }

    public final void b() {
        super.b();
    }

    /* access modifiers changed from: protected */
    public final String[] c() {
        return new String[]{"非最优方案", "小路太多", "道路泥泞不平", "语音播报不准", "不适合夜行"};
    }

    public /* synthetic */ IPresenter createPresenter() {
        return new eki(this);
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
