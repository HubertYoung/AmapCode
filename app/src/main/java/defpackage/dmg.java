package defpackage;

import com.amap.bundle.logs.AMapLog;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.widget.ConfirmDlgLifeCircle;

/* renamed from: dmg reason: default package */
/* compiled from: PageStackUtil */
public final class dmg {
    public static void a() {
        try {
            apr apr = (apr) a.a.a(apr.class);
            if (apr != null) {
                apr.b(AMapPageUtil.getPageContext());
            }
            ConfirmDlgLifeCircle.removeAll();
        } catch (Exception e) {
            StringBuilder sb = new StringBuilder();
            sb.append(e.getMessage());
            AMapLog.e("IntentController", sb.toString());
        }
    }
}
