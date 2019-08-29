package defpackage;

import android.view.View;
import com.autonavi.minimap.R;
import com.autonavi.widget.ui.TitleBar;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: blt reason: default package */
/* compiled from: SetWebViewCloseBtnAction */
public class blt extends vz {
    public final void a(JSONObject jSONObject, wa waVar) throws JSONException {
        boolean optBoolean = jSONObject.optBoolean("hidden");
        bid bid = a().mPageContext;
        if (bid != null && bid.getContentView() != null) {
            View findViewById = bid.getContentView().findViewById(R.id.title);
            if (findViewById instanceof TitleBar) {
                ((TitleBar) findViewById).setWidgetVisibility(2, optBoolean ? 8 : 0);
            }
        }
    }
}
