package defpackage;

import android.text.TextUtils;
import android.view.SurfaceHolder;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName;
import com.amap.bundle.statistics.util.LogUtil;
import com.autonavi.common.Page.ON_BACK_TYPE;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.minimap.basemap.route.external.zxingwrapper.decoding.CapturePageHandler;
import com.autonavi.minimap.basemap.route.page.CarLicenseScanPage;
import com.autonavi.minimap.basemap.traffic.TrafficUtil;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: cqu reason: default package */
/* compiled from: CarLicenseScanPresenter */
public final class cqu extends cqv<CarLicenseScanPage> {
    public cqu(CarLicenseScanPage carLicenseScanPage) {
        super(carLicenseScanPage);
    }

    public final void onPageCreated() {
        super.onPageCreated();
    }

    public final void a() {
        JSONObject jSONObject = new JSONObject();
        try {
            CarLicenseScanPage carLicenseScanPage = (CarLicenseScanPage) this.mPage;
            int i = 0;
            if (carLicenseScanPage.b != null) {
                CapturePageHandler capturePageHandler = carLicenseScanPage.b;
                if (capturePageHandler.a != null) {
                    cqp cqp = capturePageHandler.a;
                    if (cqp.a != null) {
                        i = ((cqo) cqp.a).a;
                    }
                }
            }
            jSONObject.put("status", i);
            LogUtil.actionLogV2("P00224", "B006", jSONObject);
        } catch (JSONException unused) {
        }
    }

    public static void a(String str) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("status", str);
            LogUtil.actionLogV2("P00224", "B005", jSONObject);
        } catch (JSONException unused) {
        }
    }

    public static void b(String str) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(TrafficUtil.KEYWORD, str);
            LogUtil.actionLogV2("P00224", "B003", jSONObject);
        } catch (JSONException unused) {
        }
    }

    public final void onResume() {
        super.onResume();
    }

    public static void a(String str, String str2) {
        cqj cqj = new cqj(str, str2);
        MapSharePreference mapSharePreference = new MapSharePreference(SharePreferenceName.SharedPreferences);
        if (!TextUtils.isEmpty(cqj.a)) {
            mapSharePreference.putStringValue("car_license_vin", cqj.a);
        }
        if (!TextUtils.isEmpty(cqj.b)) {
            mapSharePreference.putStringValue("car_license_engine_number", cqj.b);
        }
    }

    public final void onStart() {
        super.onStart();
        CarLicenseScanPage carLicenseScanPage = (CarLicenseScanPage) this.mPage;
        carLicenseScanPage.i = true;
        SurfaceHolder holder = carLicenseScanPage.d.getHolder();
        if (!carLicenseScanPage.e) {
            holder.addCallback(carLicenseScanPage);
            holder.setType(3);
        } else if (!carLicenseScanPage.a(holder)) {
            carLicenseScanPage.k.sendEmptyMessage(4);
        }
    }

    public final void onStop() {
        super.onStop();
        CarLicenseScanPage carLicenseScanPage = (CarLicenseScanPage) this.mPage;
        carLicenseScanPage.i = false;
        carLicenseScanPage.e();
    }

    public final void onResult(int i, ResultType resultType, PageBundle pageBundle) {
        super.onResult(i, resultType, pageBundle);
        if (i == 1002 && resultType == ResultType.OK) {
            if (((CarLicenseScanPage) this.mPage).j) {
                CarLicenseScanPage carLicenseScanPage = (CarLicenseScanPage) this.mPage;
                carLicenseScanPage.g = 0;
                carLicenseScanPage.h = 0;
                if (carLicenseScanPage.b != null) {
                    carLicenseScanPage.b.b();
                }
                return;
            }
            ((CarLicenseScanPage) this.mPage).finish();
        }
    }

    public final void onDestroy() {
        super.onDestroy();
        CarLicenseScanPage.c();
        CarLicenseScanPage.a();
    }

    public final ON_BACK_TYPE onBackPressed() {
        if (((CarLicenseScanPage) this.mPage).hasViewLayer()) {
            return ON_BACK_TYPE.TYPE_IGNORE;
        }
        return super.onBackPressed();
    }
}
