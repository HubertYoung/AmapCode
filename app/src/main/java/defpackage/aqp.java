package defpackage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.minimap.R;
import java.io.File;

/* renamed from: aqp reason: default package */
/* compiled from: SkeletonScreenLoader */
public final class aqp {
    private static final String a;

    public static void a() {
    }

    static {
        StringBuilder sb = new StringBuilder();
        sb.append(AMapAppGlobal.getApplication().getCacheDir().getAbsolutePath());
        sb.append(File.separator);
        sb.append("amaphome.quickservice.drawingcache.png");
        a = sb.toString();
    }

    public static View a(Context context) {
        return LayoutInflater.from(context).inflate(R.layout.amaphome_quickservice_preloadview, null);
    }
}
