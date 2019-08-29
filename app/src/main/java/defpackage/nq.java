package defpackage;

import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewStub;
import com.amap.bundle.drive.entrance.NaviEntrancePage;
import com.amap.bundle.drivecommon.tools.DriveUtil;
import com.amap.bundle.statistics.LogManager;
import com.autonavi.common.Page.ON_BACK_TYPE;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.minimap.R;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: nq reason: default package */
/* compiled from: NaviEntrancePresenter */
public final class nq extends sv<NaviEntrancePage, np> implements OnClickListener {
    private int c = -1;

    public nq(NaviEntrancePage naviEntrancePage) {
        super(naviEntrancePage);
    }

    public final void onPageCreated() {
        super.onPageCreated();
        NaviEntrancePage naviEntrancePage = (NaviEntrancePage) this.mPage;
        View contentView = naviEntrancePage.getContentView();
        naviEntrancePage.a = ((ViewStub) contentView.findViewById(R.id.choose_view)).inflate();
        naviEntrancePage.c = naviEntrancePage.a.findViewById(R.id.car_option);
        naviEntrancePage.d = naviEntrancePage.a.findViewById(R.id.truck_option);
        naviEntrancePage.c.setOnClickListener((OnClickListener) naviEntrancePage.mPresenter);
        naviEntrancePage.d.setOnClickListener((OnClickListener) naviEntrancePage.mPresenter);
        naviEntrancePage.b = ((ViewStub) contentView.findViewById(R.id.supplement_view)).inflate();
        naviEntrancePage.b.setVisibility(8);
        naviEntrancePage.e = naviEntrancePage.b.findViewById(R.id.cancel_supplement);
        naviEntrancePage.f = naviEntrancePage.b.findViewById(R.id.go_to_supplement);
        naviEntrancePage.e.setOnClickListener((OnClickListener) naviEntrancePage.mPresenter);
        naviEntrancePage.f.setOnClickListener((OnClickListener) naviEntrancePage.mPresenter);
        PageBundle arguments = ((NaviEntrancePage) this.mPage).getArguments();
        if (arguments != null) {
            this.c = arguments.getInt("mit_voice_tokenid", -1);
        }
    }

    public final void onResume() {
        super.onResume();
    }

    public final void onStart() {
        super.onStart();
        ((NaviEntrancePage) this.mPage).getMapView().f(false);
    }

    public final void onStop() {
        super.onStop();
    }

    public final void onDestroy() {
        super.onDestroy();
        if (this.c >= 0) {
            d.a.a(this.c, 10000, (String) "");
        }
    }

    public final ON_BACK_TYPE onBackPressed() {
        return super.onBackPressed();
    }

    public final void onResult(int i, ResultType resultType, PageBundle pageBundle) {
        super.onResult(i, resultType, pageBundle);
        if (i == 1002) {
            b();
        }
    }

    public final void onClick(View view) {
        int id = view.getId();
        JSONObject jSONObject = new JSONObject();
        if (id == R.id.car_option) {
            this.c = -1;
            ((np) this.b).a("car");
            b();
            try {
                jSONObject.put("type", "car");
                LogManager.actionLogV2("P00025", "B125", jSONObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if (id == R.id.truck_option) {
            ((np) this.b).a(DriveUtil.NAVI_TYPE_TRUCK);
            if (!tk.a() || (tk.c() && "0".equals(tk.a(tk.b())))) {
                NaviEntrancePage naviEntrancePage = (NaviEntrancePage) this.mPage;
                if (naviEntrancePage.a != null) {
                    naviEntrancePage.a.setVisibility(8);
                }
                if (naviEntrancePage.b != null) {
                    naviEntrancePage.b.setVisibility(0);
                }
                JSONObject jSONObject2 = new JSONObject();
                String str = "";
                if (!tk.a()) {
                    str = "incomplete";
                } else if (tk.c() && "0".equals(tk.a(tk.b()))) {
                    str = "abnormal";
                }
                if (!TextUtils.isEmpty(str)) {
                    try {
                        jSONObject2.put("type", str);
                        LogManager.actionLogV2("P00025", "B126", jSONObject2);
                    } catch (JSONException e2) {
                        e2.printStackTrace();
                    }
                }
            } else {
                b();
            }
            try {
                jSONObject.put("type", DriveUtil.NAVI_TYPE_TRUCK);
                LogManager.actionLogV2("P00025", "B125", jSONObject);
            } catch (JSONException e3) {
                e3.printStackTrace();
            }
        } else if (id == R.id.cancel_supplement) {
            tk.a("1", tk.b());
            ((NaviEntrancePage) this.mPage).a();
            b();
            try {
                jSONObject.put("type", "cancel");
                LogManager.actionLogV2("P00025", "B127", jSONObject);
            } catch (JSONException e4) {
                e4.printStackTrace();
            }
        } else {
            if (id == R.id.go_to_supplement) {
                tk.a("1", tk.b());
                ((NaviEntrancePage) this.mPage).a();
                String truckCarPlateNumber = DriveUtil.getTruckCarPlateNumber();
                if (!TextUtils.isEmpty(truckCarPlateNumber)) {
                    String carEditPath = DriveUtil.getCarEditPath(truckCarPlateNumber);
                    StringBuilder sb = new StringBuilder();
                    sb.append(carEditPath);
                    sb.append("&perfectTruck=1");
                    ((NaviEntrancePage) this.mPage).startScheme(new Intent("android.intent.action.VIEW", Uri.parse(sb.toString())));
                }
                try {
                    jSONObject.put("type", "check");
                    LogManager.actionLogV2("P00025", "B127", jSONObject);
                } catch (JSONException e5) {
                    e5.printStackTrace();
                }
            }
        }
    }

    private void b() {
        nn nnVar = (nn) ((NaviEntrancePage) this.mPage).getArguments().getObject("callback");
        if (nnVar != null) {
            nnVar.a(((np) this.b).a);
        }
        ((NaviEntrancePage) this.mPage).finish();
    }

    public final /* synthetic */ st a() {
        return new np(this);
    }
}
