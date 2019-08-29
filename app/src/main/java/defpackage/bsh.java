package defpackage;

import android.view.View;
import android.widget.ImageView.ScaleType;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName;
import com.amap.bundle.statistics.LogManager;
import com.autonavi.map.core.MapManager;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: bsh reason: default package */
/* compiled from: TrafficPresenter */
public final class bsh extends bsb {
    private boolean b = false;
    private MapManager c;
    private boolean d = true;

    public bsh(MapManager mapManager, boolean z) {
        this.c = mapManager;
        this.d = z;
    }

    /* access modifiers changed from: protected */
    public final void a() {
        super.a();
        this.a.setScaleType(ScaleType.FIT_CENTER);
        this.a.setImageResource(R.drawable.suspend_traffic_icon_selector);
        this.a.setBackgroundResource(R.drawable.icon_c_bg_single);
        a(new MapSharePreference(SharePreferenceName.SharedPreferences).getBooleanValue("traffic", false));
    }

    public final void a(boolean z) {
        this.a.setSelected(z);
        this.a.setImageResource(z ? R.drawable.icon_c4_a_selector : R.drawable.icon_c4_selector);
    }

    public final void onClick(View view) {
        boolean booleanValue = new MapSharePreference(SharePreferenceName.SharedPreferences).getBooleanValue("traffic", false);
        boolean s = this.c.getMapView().s();
        bqx bqx = (bqx) ank.a(bqx.class);
        if (booleanValue == s) {
            if (!booleanValue) {
                this.c.getMapView().t();
            }
            if (bqx != null) {
                bqx.a(!booleanValue, true, this.c, AMapPageUtil.getAppContext());
            }
            this.b = !booleanValue;
        } else {
            if (!s) {
                this.c.getMapView().t();
            }
            if (bqx != null) {
                bqx.a(!s, true, this.c, AMapPageUtil.getAppContext());
            }
            this.b = !s;
        }
        a(!s);
        JSONObject jSONObject = new JSONObject();
        if (!booleanValue) {
            try {
                jSONObject.put("type", "1");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            jSONObject.put("type", "0");
        }
        jSONObject.put("status", 1);
        LogManager.actionLogV2("P00001", "B014", jSONObject);
    }
}
