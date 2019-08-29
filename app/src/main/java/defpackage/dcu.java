package defpackage;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.map.core.MapCustomizeManager;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;
import com.autonavi.minimap.bundle.share.entity.ShareBase;

/* renamed from: dcu reason: default package */
/* compiled from: SmsShare */
public final class dcu extends ShareBase {
    private g a;

    public final int getShareType() {
        return 0;
    }

    public dcu(g gVar) {
        this.a = gVar;
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
        Intent intent = new Intent("android.intent.action.SENDTO", Uri.parse("smsto:"));
        intent.setFlags(MapCustomizeManager.VIEW_SEARCH_ALONG);
        intent.putExtra("sms_body", str2);
        Activity topActivity = AMapAppGlobal.getTopActivity();
        if (topActivity != null) {
            try {
                topActivity.startActivity(intent);
                notifyShareResult(0);
            } catch (Exception e) {
                if (e instanceof ActivityNotFoundException) {
                    ToastHelper.showLongToast(AMapPageUtil.getAppContext().getString(R.string.sms_share_no_app));
                    notifyShareResult(-1);
                }
            }
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
