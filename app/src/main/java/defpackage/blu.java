package defpackage;

import android.view.View;
import com.autonavi.minimap.R;
import com.autonavi.widget.ui.TitleBar;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: blu reason: default package */
/* compiled from: SetWebViewTitleBarAction */
public class blu extends vz {
    public final void a(JSONObject jSONObject, wa waVar) throws JSONException {
        boolean optBoolean = jSONObject.optBoolean("hidden");
        bid bid = a().mPageContext;
        if (bid != null && bid.getContentView() != null) {
            View findViewById = bid.getContentView().findViewById(R.id.title);
            if (findViewById instanceof TitleBar) {
                findViewById.setVisibility(optBoolean ? 8 : 0);
                JSONObject jSONObject2 = new JSONObject();
                jSONObject2.put("message", "success");
                a().callJs(waVar.a, jSONObject2.toString());
            }
        }
    }
}
