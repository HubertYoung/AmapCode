package defpackage;

import android.content.Context;
import android.text.TextUtils;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.annotation.MultipleImpl;
import java.io.File;

@MultipleImpl(awa.class)
/* renamed from: eku reason: default package */
/* compiled from: SearchInit */
public class eku implements awa {
    Context a = null;

    public final void a() {
        this.a = AMapAppGlobal.getApplication();
        bxq a2 = bxq.a(this.a);
        String b = a2.b();
        boolean z = false;
        if (!TextUtils.isEmpty(b)) {
            File file = new File(b);
            if (file.exists() && file.isFile()) {
                z = true;
            }
        }
        if (!z) {
            a2.a();
        }
    }
}
