package defpackage;

import android.text.Html;
import android.view.View;
import android.widget.TextView;
import com.amap.bundle.drivecommon.payfor.PayforNaviData;
import com.autonavi.carowner.payfor.ApplyPayForResultFragment;
import com.autonavi.common.Page.ON_BACK_TYPE;
import com.autonavi.minimap.R;

/* renamed from: bhl reason: default package */
/* compiled from: ApplyPayForResultPresenter */
public final class bhl extends sw<ApplyPayForResultFragment, bhf> {
    public bhl(ApplyPayForResultFragment applyPayForResultFragment) {
        super(applyPayForResultFragment);
    }

    public final void onPageCreated() {
        ApplyPayForResultFragment applyPayForResultFragment = (ApplyPayForResultFragment) this.mPage;
        applyPayForResultFragment.a = (PayforNaviData) applyPayForResultFragment.getArguments().getObject("payforNaviData");
        View contentView = applyPayForResultFragment.getContentView();
        contentView.findViewById(R.id.title_btn_left).setVisibility(8);
        applyPayForResultFragment.c = contentView.findViewById(R.id.back_amap);
        applyPayForResultFragment.d = contentView.findViewById(R.id.look_over_activities);
        ((TextView) contentView.findViewById(R.id.title_text_name)).setText(R.string.oper_result);
        ((TextView) contentView.findViewById(R.id.txt_payfor_audit_tips)).setText(applyPayForResultFragment.getString(R.string.activities_applied_payfor_audit_tips, Integer.valueOf(applyPayForResultFragment.a.doneDays)));
        applyPayForResultFragment.e = (TextView) contentView.findViewById(R.id.payforCashTv);
        applyPayForResultFragment.f = contentView.findViewById(R.id.shareTv);
        ((TextView) contentView.findViewById(R.id.friends_text)).setText(Html.fromHtml(applyPayForResultFragment.getString(R.string.activities_friends)));
        if (applyPayForResultFragment.c != null) {
            applyPayForResultFragment.c.setOnClickListener(applyPayForResultFragment);
        }
        if (applyPayForResultFragment.d != null) {
            applyPayForResultFragment.d.setOnClickListener(applyPayForResultFragment);
        }
        if (applyPayForResultFragment.f != null) {
            applyPayForResultFragment.f.setOnClickListener(applyPayForResultFragment);
        }
        if (!(applyPayForResultFragment.a == null || applyPayForResultFragment.e == null)) {
            applyPayForResultFragment.b = applyPayForResultFragment.a.shareUrl;
            double d = applyPayForResultFragment.a.moneyMaypayed;
            if (PayforNaviData.isNeedShowMoney(d)) {
                applyPayForResultFragment.e.setText(Html.fromHtml(applyPayForResultFragment.getString(R.string.activities_apply_maypayed_money_html, Double.valueOf(d))));
            } else {
                applyPayForResultFragment.e.setText(R.string.activities_apply_maypayed_money_html_without_count);
            }
        }
        if (euk.a()) {
            int a = euk.a(applyPayForResultFragment.getContext());
            View contentView2 = applyPayForResultFragment.getContentView();
            contentView2.setPadding(contentView2.getPaddingLeft(), contentView2.getPaddingTop() + a, contentView2.getPaddingRight(), contentView2.getPaddingBottom());
        }
    }

    public final ON_BACK_TYPE onBackPressed() {
        ((ApplyPayForResultFragment) this.mPage).a();
        return ON_BACK_TYPE.TYPE_IGNORE;
    }

    public final /* synthetic */ su a() {
        return new bhf(this);
    }
}
