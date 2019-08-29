package com.autonavi.carowner.payfor;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import com.amap.bundle.drivecommon.mvp.view.DriveBasePage;
import com.amap.bundle.drivecommon.payfor.PayforNaviData;
import com.amap.bundle.statistics.LogManager;
import com.autonavi.annotation.PageAction;
import com.autonavi.carowner.payfor.ApplyPayForLocationErrorFragment.ErrorType;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.IPresenter;
import com.autonavi.minimap.R;
import com.autonavi.sdk.log.util.LogConstant;

@PageAction("apply_pay_type_choose")
public class ApplyPayForTypeChooseFragment extends DriveBasePage<bhm> implements OnClickListener {
    public Button a;
    public RelativeLayout b;
    public RelativeLayout c;
    public RelativeLayout d;
    public PayforNaviData e;

    public void onCreate(Context context) {
        super.onCreate(context);
        setContentView(R.layout.activities_apply_pay_for_choose_type);
    }

    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.ckb_pay_for_wrongplace) {
            PageBundle pageBundle = new PageBundle();
            pageBundle.putObject("naviData", this.e);
            startPageForResult(ApplyPayForFragment.class, pageBundle, 1);
        } else if (id == R.id.ckb_pay_for_no_loc) {
            PageBundle pageBundle2 = new PageBundle();
            pageBundle2.putObject(ApplyPayForLocationErrorFragment.b, ErrorType.UNKNOWN_LOCATION);
            pageBundle2.putObject(ApplyPayForLocationErrorFragment.a, this.e);
            startPageForResult(ApplyPayForLocationErrorFragment.class, pageBundle2, 1);
            LogManager.actionLog(LogConstant.PAGE_NAVI_PROTECTION, 10);
        } else {
            if (id == R.id.ckb_pay_for_have_loc) {
                PageBundle pageBundle3 = new PageBundle();
                pageBundle3.putObject(ApplyPayForLocationErrorFragment.b, ErrorType.KNOW_LOCATION);
                pageBundle3.putObject(ApplyPayForLocationErrorFragment.a, this.e);
                startPageForResult(ApplyPayForLocationErrorFragment.class, pageBundle3, 1);
                LogManager.actionLog(LogConstant.PAGE_NAVI_PROTECTION, 9);
            }
        }
    }

    public /* synthetic */ IPresenter createPresenter() {
        return new bhm(this);
    }
}
