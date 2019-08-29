package defpackage;

import android.view.View;
import android.view.View.OnClickListener;
import com.amap.bundle.statistics.LogManager;
import com.autonavi.common.PageBundle;
import com.autonavi.map.core.view.RouteLineView;
import com.autonavi.minimap.R;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: cyv reason: default package */
/* compiled from: RouteLinePresenter */
public final class cyv implements OnClickListener {
    public RouteLineView a = null;

    public final void onClick(View view) {
        if (view.getId() == R.id.linear_path_container) {
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("status", 1);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            LogManager.actionLogV2("P00001", "B004", jSONObject);
            bax bax = (bax) a.a.a(bax.class);
            if (bax != null) {
                bax.a((PageBundle) null);
            }
        }
    }
}
