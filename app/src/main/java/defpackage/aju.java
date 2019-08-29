package defpackage;

import android.text.TextUtils;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.inter.IPageManifest;

/* renamed from: aju reason: default package */
/* compiled from: PageBackNativeEqual */
final class aju implements ajr {
    aju() {
    }

    public final boolean a(String str, bid bid) {
        if (bid == null || TextUtils.isEmpty(str)) {
            return false;
        }
        String[] split = str.split("\\?");
        if (split == null || split.length <= 0) {
            return false;
        }
        String str2 = split[0];
        Class<?> page = ((IPageManifest) bqn.a(IPageManifest.class)).getPage(str2);
        if (page == null) {
            StringBuilder sb = new StringBuilder("不存在Action为：");
            sb.append(str2);
            sb.append("的Page");
            AMapLog.e("PageBackImpl", sb.toString());
        }
        if (page == null) {
            return false;
        }
        return page.equals(bid.getClass());
    }
}
