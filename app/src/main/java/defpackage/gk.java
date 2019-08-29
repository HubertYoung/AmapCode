package defpackage;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable.Callback;
import android.support.annotation.Nullable;
import android.view.View;
import com.alipay.mobile.antui.iconfont.constants.IconfontConstants;
import java.util.HashMap;
import java.util.Map;

/* renamed from: gk reason: default package */
/* compiled from: FontAssetManager */
public final class gk {
    public final gu<String> a = new gu<>();
    public final Map<gu<String>, Typeface> b = new HashMap();
    public final Map<String, Typeface> c = new HashMap();
    public final AssetManager d;
    @Nullable
    public es e;
    public String f = IconfontConstants.ICONFONT_FILE_SUFFIX;

    public gk(Callback callback, @Nullable es esVar) {
        this.e = esVar;
        if (!(callback instanceof View)) {
            this.d = null;
        } else {
            this.d = ((View) callback).getContext().getAssets();
        }
    }
}
