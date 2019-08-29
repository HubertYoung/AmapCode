package defpackage;

import android.text.TextUtils;
import com.amap.bundle.drive.setting.navisetting.page.NaviSettingPage;
import com.amap.bundle.drivecommon.tools.DriveUtil;
import com.autonavi.common.Page.ON_BACK_TYPE;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.minimap.R;
import com.autonavi.minimap.drive.widget.CarPlateInputView;

/* renamed from: qj reason: default package */
/* compiled from: NaviSettingPresenter */
public final class qj extends sw<NaviSettingPage, qi> {
    public final void onStart() {
        super.onStart();
        ((NaviSettingPage) this.mPage).requestScreenOrientation(1);
    }

    public qj(NaviSettingPage naviSettingPage) {
        super(naviSettingPage);
    }

    public final void onResume() {
        super.onResume();
        NaviSettingPage naviSettingPage = (NaviSettingPage) this.mPage;
        boolean z = false;
        if (naviSettingPage.d != null) {
            naviSettingPage.d.b();
            qf qfVar = naviSettingPage.d;
            if (!(qfVar.b == null || qfVar.a == null)) {
                qfVar.b.setText(re.a(qfVar.a.getContext()));
            }
            naviSettingPage.d.c();
            qf qfVar2 = naviSettingPage.d;
            if (!(qfVar2.c == null || qfVar2.a == null)) {
                qfVar2.c.setText(qfVar2.a.getString(re.a((String) "speaker_paly_sound", false) ? R.string.telephony_channel : R.string.media_channel));
            }
        }
        if (naviSettingPage.a != null) {
            naviSettingPage.a.a();
            qe qeVar = naviSettingPage.a;
            if (qeVar.c != null && qeVar.d && !qeVar.c.isChecked()) {
                naviSettingPage.a.b();
            }
        }
        if (naviSettingPage.b != null && !naviSettingPage.b.a() && naviSettingPage.e && naviSettingPage.h == NaviSettingPage.f) {
            naviSettingPage.finish();
        }
        qg qgVar = ((NaviSettingPage) this.mPage).c;
        if (qgVar != null) {
            String motorInfo = DriveUtil.getMotorInfo();
            String str = qgVar.b;
            if (!TextUtils.isEmpty(motorInfo) && !motorInfo.equals(str)) {
                z = true;
            }
            if (z) {
                qgVar.a();
            }
        }
    }

    public final ON_BACK_TYPE onBackPressed() {
        boolean z;
        NaviSettingPage naviSettingPage = (NaviSettingPage) this.mPage;
        if (naviSettingPage.b != null) {
            z = naviSettingPage.b.e;
        } else {
            z = false;
        }
        if (z) {
            return ON_BACK_TYPE.TYPE_IGNORE;
        }
        NaviSettingPage naviSettingPage2 = (NaviSettingPage) this.mPage;
        if (naviSettingPage2.a == null || naviSettingPage2.i == null || !naviSettingPage2.i.b()) {
            if (naviSettingPage2.g != 2) {
                naviSettingPage2.a();
            }
            return ON_BACK_TYPE.TYPE_NORMAL;
        }
        naviSettingPage2.i.c();
        return ON_BACK_TYPE.TYPE_IGNORE;
    }

    public final void onResult(int i, ResultType resultType, PageBundle pageBundle) {
        if (resultType == ResultType.OK && i == 65537) {
            qg qgVar = ((NaviSettingPage) this.mPage).c;
            if (qgVar != null) {
                qgVar.d = false;
                qgVar.a();
            }
        }
        if (resultType == ResultType.OK && pageBundle != null && pageBundle.containsKey(CarPlateInputView.BUNDLE_KEY_CAR_PLATE_NUMBER)) {
            if (i == 65536) {
                qe qeVar = ((NaviSettingPage) this.mPage).a;
                if (qeVar != null) {
                    qeVar.a();
                }
            } else if (i == 65538 || i == 65539) {
                qh qhVar = ((NaviSettingPage) this.mPage).b;
                if (qhVar != null) {
                    qhVar.a();
                }
            }
        }
        super.onResult(i, resultType, pageBundle);
    }

    public final /* synthetic */ su a() {
        return new qi(this);
    }
}
