package defpackage;

import android.text.Html;
import android.view.View;
import android.widget.TextView;
import com.amap.bundle.drivecommon.payfor.PayforNaviData;
import com.autonavi.carowner.payfor.view.SubmitSuccessFragment;
import com.autonavi.common.Page.ON_BACK_TYPE;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.minimap.R;

/* renamed from: bhn reason: default package */
/* compiled from: SubmitSuccessPresenter */
public final class bhn extends sw<SubmitSuccessFragment, bhh> {
    public bhn(SubmitSuccessFragment submitSuccessFragment) {
        super(submitSuccessFragment);
    }

    public final void onPageCreated() {
        SubmitSuccessFragment submitSuccessFragment = (SubmitSuccessFragment) this.mPage;
        submitSuccessFragment.d = (PayforNaviData) submitSuccessFragment.getArguments().get("naviData");
        View contentView = submitSuccessFragment.getContentView();
        submitSuccessFragment.a = contentView.findViewById(R.id.title_btn_left);
        ((TextView) contentView.findViewById(R.id.title_text_name)).setText(R.string.activities_commit_success);
        submitSuccessFragment.b = contentView.findViewById(R.id.loginBtn);
        submitSuccessFragment.c = (TextView) contentView.findViewById(R.id.payforCashTv);
        if (submitSuccessFragment.a != null) {
            submitSuccessFragment.a.setOnClickListener(submitSuccessFragment);
        }
        if (submitSuccessFragment.b != null) {
            submitSuccessFragment.b.setOnClickListener(submitSuccessFragment);
        }
        if (submitSuccessFragment.c != null) {
            if (PayforNaviData.isNeedShowMoney(submitSuccessFragment.d.moneyMaypayed)) {
                submitSuccessFragment.c.setText(Html.fromHtml(submitSuccessFragment.getString(R.string.activities_need_login_to_get_payed, Double.valueOf(submitSuccessFragment.d.moneyMaypayed))));
                return;
            }
            submitSuccessFragment.c.setText(R.string.activities_need_login_to_get_payed_without_count);
        }
    }

    public final ON_BACK_TYPE onBackPressed() {
        SubmitSuccessFragment submitSuccessFragment = (SubmitSuccessFragment) this.mPage;
        boolean z = true;
        if (submitSuccessFragment.getRequestCode() == 10001) {
            submitSuccessFragment.setResult(ResultType.OK, (PageBundle) null);
            submitSuccessFragment.finish();
        } else {
            if (submitSuccessFragment.getRequestCode() == 1) {
                PageBundle pageBundle = new PageBundle();
                pageBundle.putString("soure_from_page", "submit");
                submitSuccessFragment.setResult(ResultType.OK, pageBundle);
            }
            z = false;
        }
        if (z) {
            return ON_BACK_TYPE.TYPE_IGNORE;
        }
        return super.onBackPressed();
    }

    public final /* synthetic */ su a() {
        return new bhh(this);
    }
}
