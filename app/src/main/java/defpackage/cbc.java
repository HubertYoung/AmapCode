package defpackage;

import android.graphics.Rect;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import com.amap.bundle.statistics.LogManager;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.bl.search.InfoliteParam;
import com.autonavi.common.Page.ON_BACK_TYPE;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.common.SuperId;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.map.search.page.SearchErrorIndoorPage;
import com.autonavi.minimap.R;
import com.autonavi.minimap.basemap.traffic.TrafficUtil;
import com.autonavi.minimap.controller.AppManager;
import org.json.JSONObject;

/* renamed from: cbc reason: default package */
/* compiled from: SearchErrorIndoorPresenter */
public final class cbc extends cau<SearchErrorIndoorPage> implements OnClickListener {
    private Rect a;

    public cbc(SearchErrorIndoorPage searchErrorIndoorPage) {
        super(searchErrorIndoorPage);
    }

    public final ON_BACK_TYPE onBackPressed() {
        ((SearchErrorIndoorPage) this.mPage).setResult(ResultType.CANCEL, (PageBundle) null);
        return super.onBackPressed();
    }

    public final void onStart() {
        String str;
        super.onStart();
        SuperId.getInstance().setBit3("14");
        PageBundle arguments = ((SearchErrorIndoorPage) this.mPage).getArguments();
        if (arguments != null && arguments.containsKey(TrafficUtil.KEYWORD)) {
            ((SearchErrorIndoorPage) this.mPage).a.setKeyword(arguments.getString(TrafficUtil.KEYWORD));
        }
        SearchErrorIndoorPage searchErrorIndoorPage = (SearchErrorIndoorPage) this.mPage;
        if (TextUtils.isEmpty(elc.d)) {
            searchErrorIndoorPage.b.setText(R.string.search_indoor_no_result_toast);
            return;
        }
        if (elc.d == null || elc.d.length() <= 8) {
            str = elc.d;
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append(elc.d.substring(0, 7));
            sb.append("...");
            str = sb.toString();
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append(str);
        sb2.append(searchErrorIndoorPage.getResources().getString(R.string.search_indoor_no_result_toast));
        SpannableString spannableString = new SpannableString(sb2.toString());
        if (!TextUtils.isEmpty(str)) {
            spannableString.setSpan(new ForegroundColorSpan(searchErrorIndoorPage.getResources().getColor(R.color.f_c_6)), 0, str.length(), 17);
        }
        searchErrorIndoorPage.b.setText(spannableString);
    }

    public final void onClick(View view) {
        if (view instanceof TextView) {
            String charSequence = ((TextView) view).getText().toString();
            boolean isEmpty = TextUtils.isEmpty(charSequence.trim());
            if (isEmpty) {
                ToastHelper.showToast(((SearchErrorIndoorPage) this.mPage).getString(R.string.act_search_error_searchcontempty));
            }
            if (!isEmpty) {
                JSONObject jSONObject = new JSONObject();
                try {
                    jSONObject.put("Keyword", charSequence);
                } catch (Exception e) {
                    kf.a((Throwable) e);
                }
                LogManager.actionLogV2("P00004", "B034", jSONObject);
                elc.e = elc.d;
                bty mapView = DoNotUseTool.getMapView();
                if (mapView != null) {
                    this.a = mapView.H();
                    double longitude = GeoPoint.glGeoPoint2GeoPoint(mapView.n()).getLongitude();
                    double latitude = GeoPoint.glGeoPoint2GeoPoint(mapView.n()).getLatitude();
                    InfoliteParam a2 = bbv.a(AppManager.getInstance().getUserLocInfo(), charSequence, this.a);
                    if (a2 != null) {
                        a2.search_operate = 1;
                        TextUtils.equals(null, "000000");
                        a2.sugadcode = null;
                        a2.interior_scene = "2";
                        a2.interior_poi = elc.g;
                        a2.interior_floor = elc.h;
                        a2.longitude = longitude;
                        a2.latitude = latitude;
                        bwx bwx = new bwx(((SearchErrorIndoorPage) this.mPage).c, 0, false);
                        bvt bvt = new bvt();
                        bvt.e = ((SearchErrorIndoorPage) this.mPage).c;
                        bvt.f = null;
                        bvt.d = bwx;
                        new ekv().a(a2, 1, bvt);
                        ((SearchErrorIndoorPage) this.mPage).finish();
                    }
                }
            }
        }
    }
}
