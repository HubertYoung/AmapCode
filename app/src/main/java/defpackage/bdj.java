package defpackage;

import android.net.Uri;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.annotation.Router;

@Router({"subway"})
/* renamed from: bdj reason: default package */
/* compiled from: SubwayRouter */
public class bdj extends esk {
    public boolean start(ese ese) {
        if (ese != null) {
            Uri uri = ese.a;
            if (uri != null) {
                String queryParameter = uri.getQueryParameter("adCode");
                eif.a();
                eif.a(AMapAppGlobal.getTopActivity(), queryParameter);
            }
        }
        return true;
    }
}
