package defpackage;

import android.net.Uri;
import android.text.TextUtils;
import com.autonavi.annotation.Router;

@Router({"qrscan"})
/* renamed from: dbx reason: default package */
/* compiled from: QRScanRouter */
public class dbx extends esk {
    public boolean start(ese ese) {
        Uri uri = ese.a;
        if (!TextUtils.equals("mainView", uri.getPathSegments().get(0))) {
            return false;
        }
        String queryParameter = uri.getQueryParameter("firepage");
        awt awt = (awt) a.a.a(awt.class);
        if (awt != null) {
            awt.a(queryParameter);
        }
        return true;
    }
}
