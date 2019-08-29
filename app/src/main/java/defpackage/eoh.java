package defpackage;

import android.content.SharedPreferences.Editor;
import android.net.Uri;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName;
import com.autonavi.annotation.Router;
import java.util.List;

@Router({"QATest"})
/* renamed from: eoh reason: default package */
/* compiled from: QATestRouter */
public class eoh extends esk {
    private static void a(String str, boolean z) {
        Editor edit = new MapSharePreference(SharePreferenceName.SharedPreferences).edit();
        edit.putBoolean(str, z);
        edit.commit();
    }

    public boolean start(ese ese) {
        Uri uri = ese.a;
        if (uri == null) {
            return false;
        }
        List<String> pathSegments = uri.getPathSegments();
        if (pathSegments == null || pathSegments.size() == 0) {
            return false;
        }
        String str = pathSegments.get(0);
        try {
            if ("ScreenShotEnable".equalsIgnoreCase(str)) {
                a("ScreenShotEnable", Boolean.parseBoolean(uri.getQueryParameter("state")));
            } else if ("UTChannel".equalsIgnoreCase(str)) {
                a("UTChannel", Boolean.parseBoolean(uri.getQueryParameter("state")));
            }
        } catch (Exception e) {
            e.getMessage();
        }
        return true;
    }
}
