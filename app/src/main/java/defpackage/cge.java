package defpackage;

import android.support.annotation.StringRes;
import com.autonavi.common.PageBundle;
import org.json.JSONObject;

/* renamed from: cge reason: default package */
/* compiled from: FeedbackEntryEntity */
public abstract class cge {
    private String a;
    private JSONObject b = null;
    private boolean c = false;
    private String d = null;

    @StringRes
    public abstract int a();

    public abstract void a(PageBundle pageBundle);

    public cge(String str) {
        this.a = str;
    }
}
