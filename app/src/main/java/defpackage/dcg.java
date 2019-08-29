package defpackage;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;
import com.autonavi.minimap.bundle.share.entity.ShareBase;
import com.uc.webview.export.WebView;

/* renamed from: dcg reason: default package */
/* compiled from: EmailShare */
public final class dcg extends ShareBase {
    private b a;

    public final int getShareType() {
        return 1;
    }

    public dcg(b bVar) {
        this.a = bVar;
    }

    private void a(String str) {
        String str2 = this.a.f;
        if (str.length() > 0) {
            StringBuilder sb = new StringBuilder();
            sb.append(AMapPageUtil.getAppContext().getString(R.string.email_share_amap));
            sb.append(this.a.f);
            sb.append("\n");
            sb.append(str);
            str2 = sb.toString();
        }
        try {
            Intent intent = new Intent("android.intent.action.SENDTO", Uri.parse(WebView.SCHEME_MAILTO));
            intent.putExtra("android.intent.extra.TEXT", str2);
            Activity topActivity = AMapAppGlobal.getTopActivity();
            if (topActivity != null) {
                topActivity.startActivity(intent);
            }
            notifyShareResult(0);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
            ToastHelper.showToast(AMapPageUtil.getAppContext().getString(R.string.email_share_no_email_account));
            notifyShareResult(-1);
        }
    }

    public final void startShare() {
        if (!this.a.h || TextUtils.isEmpty(this.a.g)) {
            a(this.a.g);
        } else {
            requestShortUrl(this.a.g, this.a.i);
        }
    }

    public final void onFinishResult(String str) {
        if (str == null) {
            ToastHelper.showLongToast(AMapPageUtil.getAppContext().getString(R.string.email_share_get_content_failed));
            notifyShareResult(-1);
            return;
        }
        a(str);
    }
}
