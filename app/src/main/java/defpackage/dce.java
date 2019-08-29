package defpackage;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.text.TextUtils;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;
import com.autonavi.minimap.bundle.share.entity.ShareBase;

/* renamed from: dce reason: default package */
/* compiled from: CopyLinkShare */
public final class dce extends ShareBase {
    private String a;
    private boolean b;
    private String c;
    private ClipboardManager d;

    public final int getShareType() {
        return 6;
    }

    public dce(String str, boolean z, String str2) {
        this.a = str;
        this.b = z;
        this.c = str2;
    }

    public final void startShare() {
        if (TextUtils.isEmpty(this.a) || !this.b) {
            a(this.a);
        } else {
            requestShortUrl(this.a, this.c);
        }
    }

    private void a() {
        ToastHelper.showLongToast(AMapPageUtil.getAppContext().getString(R.string.copy_link_share_get_content_failed));
        notifyShareResult(-1);
    }

    public final void onFinishResult(String str) {
        if (TextUtils.isEmpty(str)) {
            a();
        } else {
            a(str);
        }
    }

    private void a(String str) {
        if (this.d == null) {
            this.d = (ClipboardManager) AMapAppGlobal.getApplication().getSystemService("clipboard");
        }
        if (this.d == null) {
            a();
            return;
        }
        this.d.setPrimaryClip(ClipData.newPlainText(null, str));
        ToastHelper.showLongToast(AMapPageUtil.getAppContext().getString(R.string.copy_link_share_get_content_success));
        notifyShareResult(0);
    }
}
