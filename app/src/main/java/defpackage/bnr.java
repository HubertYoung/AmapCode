package defpackage;

import android.webkit.JavascriptInterface;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.map.db.H5WebStorageDao.Properties;
import de.greenrobot.dao.query.WhereCondition;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.xidea.el.json.JSONEncoder;

/* renamed from: bnr reason: default package */
/* compiled from: H5WebStroageProxy */
public class bnr {
    @JavascriptInterface
    @com.uc.webview.export.JavascriptInterface
    public long length(String str) {
        synchronized (bnr.class) {
            AMapAppGlobal.getApplication().getApplicationContext();
            List<bta> a = bsn.a().a(str);
            if (a == null || a.size() <= 0) {
                return 0;
            }
            long size = (long) a.size();
            return size;
        }
    }

    @JavascriptInterface
    @com.uc.webview.export.JavascriptInterface
    public String key(String str, int i) {
        synchronized (bnr.class) {
            AMapAppGlobal.getApplication().getApplicationContext();
            List<bta> a = bsn.a().a(str);
            if (a != null && a.size() > 0) {
                int i2 = 0;
                for (bta next : a) {
                    if (i2 == i) {
                        String str2 = next.b;
                        return str2;
                    }
                    i2++;
                }
            }
            return null;
        }
    }

    @JavascriptInterface
    @com.uc.webview.export.JavascriptInterface
    public String getItem(String str, String str2) {
        synchronized (bnr.class) {
            AMapAppGlobal.getApplication().getApplicationContext();
            List list = bsn.a().a.queryBuilder().where(Properties.a.eq(str), Properties.b.eq(str2)).build().list();
            bta bta = (list == null || list.size() <= 0) ? null : (bta) list.get(0);
            if (bta == null) {
                return "";
            }
            String str3 = bta.c;
            return str3;
        }
    }

    @JavascriptInterface
    @com.uc.webview.export.JavascriptInterface
    public void setItem(String str, String str2, String str3) {
        synchronized (bnr.class) {
            AMapAppGlobal.getApplication().getApplicationContext();
            bsn a = bsn.a();
            List list = a.a.queryBuilder().where(Properties.a.eq(str), Properties.b.eq(str2)).build().list();
            if (list == null || list.size() <= 0) {
                bta bta = new bta();
                bta.a = str;
                bta.b = str2;
                bta.c = str3;
                a.a.insert(bta);
            } else {
                bta bta2 = new bta();
                bta2.a = str;
                bta2.b = str2;
                bta2.c = str3;
                a.a.queryBuilder().where(Properties.a.eq(str), Properties.b.eq(str2)).buildDelete().executeDeleteWithoutDetachingEntities();
                a.a.insert(bta2);
            }
        }
    }

    @JavascriptInterface
    @com.uc.webview.export.JavascriptInterface
    public void removeItem(String str, String str2) {
        synchronized (bnr.class) {
            AMapAppGlobal.getApplication().getApplicationContext();
            bsn.a().a.queryBuilder().where(Properties.a.eq(str), Properties.b.eq(str2)).buildDelete().executeDeleteWithoutDetachingEntities();
        }
    }

    @JavascriptInterface
    @com.uc.webview.export.JavascriptInterface
    public void clear(String str) {
        synchronized (bnr.class) {
            AMapAppGlobal.getApplication().getApplicationContext();
            bsn.a().a.queryBuilder().where(Properties.a.eq(str), new WhereCondition[0]).buildDelete().executeDeleteWithoutDetachingEntities();
        }
    }

    @JavascriptInterface
    @com.uc.webview.export.JavascriptInterface
    public String getAllKeys(String str) {
        String data;
        synchronized (bnr.class) {
            data = getData(str, new ArrayList());
        }
        return data;
    }

    @JavascriptInterface
    @com.uc.webview.export.JavascriptInterface
    public String getAll(String str) {
        String data;
        synchronized (bnr.class) {
            data = getData(str, new HashMap());
        }
        return data;
    }

    @JavascriptInterface
    @com.uc.webview.export.JavascriptInterface
    private String getData(String str, Object obj) {
        List<bta> ws = ws(str);
        if (ws != null && ws.size() > 0) {
            if (obj instanceof List) {
                for (bta bta : ws) {
                    ((List) obj).add(bta.b);
                }
            } else {
                for (bta next : ws) {
                    ((Map) obj).put(next.b, next.c);
                }
            }
        }
        return JSONEncoder.encode(obj);
    }

    @JavascriptInterface
    @com.uc.webview.export.JavascriptInterface
    private List<bta> ws(String str) {
        AMapAppGlobal.getApplication().getApplicationContext();
        return bsn.a().a(str);
    }
}
