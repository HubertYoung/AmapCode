package defpackage;

import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.amap.bundle.drivecommon.payfor.PayforNaviData;
import com.autonavi.carowner.payfor.ApplyPayForTypeChooseFragment;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.minimap.R;
import com.autonavi.widget.ui.TitleBar;

/* renamed from: bhm reason: default package */
/* compiled from: ApplyPayForTypeChoosePresenter */
public final class bhm extends sw<ApplyPayForTypeChooseFragment, bhg> {
    public bhm(ApplyPayForTypeChooseFragment applyPayForTypeChooseFragment) {
        super(applyPayForTypeChooseFragment);
    }

    public final void onPageCreated() {
        ApplyPayForTypeChooseFragment applyPayForTypeChooseFragment = (ApplyPayForTypeChooseFragment) this.mPage;
        PageBundle arguments = applyPayForTypeChooseFragment.getArguments();
        if (arguments != null && arguments.containsKey("ApplyPayForTypeChooseFragment.PayforNaviData")) {
            applyPayForTypeChooseFragment.e = (PayforNaviData) arguments.getObject("ApplyPayForTypeChooseFragment.PayforNaviData");
        }
        View contentView = applyPayForTypeChooseFragment.getContentView();
        ((TitleBar) contentView.findViewById(R.id.title)).setOnBackClickListener((OnClickListener) new OnClickListener() {
            public final void onClick(View view) {
                if (ApplyPayForTypeChooseFragment.this.getRequestCode() != 10001) {
                    PageBundle pageBundle = new PageBundle();
                    pageBundle.putBoolean("ApplyPayForTypeChooseFragment.resultData", true);
                    ApplyPayForTypeChooseFragment.this.setResult(ResultType.OK, pageBundle);
                }
                ApplyPayForTypeChooseFragment.this.finish();
            }
        });
        applyPayForTypeChooseFragment.a = (Button) contentView.findViewById(R.id.txt_pay_for_description);
        applyPayForTypeChooseFragment.a.setText(Html.fromHtml(applyPayForTypeChooseFragment.getContext().getString(R.string.activities_apply_payfor_description, new Object[]{applyPayForTypeChooseFragment.e.toAddress})));
        applyPayForTypeChooseFragment.b = (RelativeLayout) contentView.findViewById(R.id.ckb_pay_for_wrongplace);
        applyPayForTypeChooseFragment.b.setOnClickListener(applyPayForTypeChooseFragment);
        applyPayForTypeChooseFragment.c = (RelativeLayout) contentView.findViewById(R.id.ckb_pay_for_have_loc);
        applyPayForTypeChooseFragment.c.setOnClickListener(applyPayForTypeChooseFragment);
        applyPayForTypeChooseFragment.d = (RelativeLayout) contentView.findViewById(R.id.ckb_pay_for_no_loc);
        applyPayForTypeChooseFragment.d.setOnClickListener(applyPayForTypeChooseFragment);
        bhb.a((TextView) contentView.findViewById(R.id.applyTip), (TextView) contentView.findViewById(R.id.look_over_activities_view), applyPayForTypeChooseFragment.e, applyPayForTypeChooseFragment);
    }

    public final void onResult(int i, ResultType resultType, PageBundle pageBundle) {
        ApplyPayForTypeChooseFragment applyPayForTypeChooseFragment = (ApplyPayForTypeChooseFragment) this.mPage;
        if (i == 10001) {
            applyPayForTypeChooseFragment.setResult(ResultType.OK, (PageBundle) null);
            applyPayForTypeChooseFragment.finish();
            return;
        }
        if (i == 1 && resultType == ResultType.OK) {
            applyPayForTypeChooseFragment.finish();
        }
    }

    public final /* synthetic */ su a() {
        return new bhg(this);
    }
}
