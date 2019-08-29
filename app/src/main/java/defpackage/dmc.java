package defpackage;

import android.app.Activity;
import android.content.Intent;
import com.autonavi.bundle.amaphome.api.MapHomeIntentAction;
import com.autonavi.common.PageBundle;
import com.tencent.connect.common.Constants;

/* renamed from: dmc reason: default package */
/* compiled from: ShortUrlIntentInterceptor */
public final class dmc implements dlh {
    private dlq a;

    public dmc(Activity activity) {
        this.a = new dlq(activity);
    }

    public final boolean a(Intent intent) {
        if (intent.getData() == null) {
            return false;
        }
        PageBundle pageBundle = new PageBundle();
        pageBundle.putString(Constants.KEY_ACTION, "action_base_map_scheme");
        pageBundle.putObject("key_scheme_feature", MapHomeIntentAction.SHORT_URL);
        pageBundle.putObject("key_schema_short_url_intent", intent);
        dlq.a(pageBundle);
        return true;
    }
}
