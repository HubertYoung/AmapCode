package defpackage;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.UiThread;
import android.text.TextUtils;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.ajx3.Ajx3Page;
import com.autonavi.minimap.bundle.maphome.service.IMainMapService;

/* renamed from: cjp reason: default package */
/* compiled from: AGroupPageSchemeUtils */
public final class cjp {
    @UiThread
    public static boolean a(@NonNull String str, @Nullable String str2) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        IMainMapService iMainMapService = (IMainMapService) ank.a(IMainMapService.class);
        if (iMainMapService == null) {
            return false;
        }
        bid e = iMainMapService.e();
        if (e == null) {
            return false;
        }
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setData(Uri.parse(str));
        if (!TextUtils.isEmpty(str2)) {
            intent.putExtra("page_data_key", str2);
        }
        e.startScheme(intent);
        return true;
    }

    @Nullable
    public static bid a(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        bid pageContext = AMapPageUtil.getPageContext();
        if (pageContext != null && (pageContext instanceof Ajx3Page) && TextUtils.equals(((Ajx3Page) pageContext).getAjx3Url(), str)) {
            return pageContext;
        }
        return null;
    }
}
