package defpackage;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.bundle.account.api.IAccountService;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;
import com.autonavi.minimap.bundle.share.entity.ShareBase;
import com.autonavi.server.aos.serverkey;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import java.util.ArrayList;

/* renamed from: dcm reason: default package */
/* compiled from: QQZoneShare */
public final class dcm extends ShareBase {
    private e a;

    /* renamed from: dcm$a */
    /* compiled from: QQZoneShare */
    static class a implements IUiListener {
        public final void onCancel() {
        }

        private a() {
        }

        /* synthetic */ a(byte b) {
            this();
        }

        public final void onComplete(Object obj) {
            ddi.a().a(9, 0);
            ddi.a().b();
        }

        public final void onError(UiError uiError) {
            ddi.a().a(9, -1);
            ddi.a().b();
        }
    }

    public final int getShareType() {
        return 9;
    }

    public dcm(e eVar) {
        this.a = eVar;
    }

    private void a(String str) {
        if (TextUtils.isEmpty(str)) {
            ToastHelper.showLongToast(AMapPageUtil.getAppContext().getString(R.string.email_share_get_content_failed));
            notifyShareResult(-1);
            return;
        }
        Tencent createInstance = Tencent.createInstance(serverkey.getQQCustomKey(), AMapAppGlobal.getApplication());
        a aVar = new a(0);
        defpackage.esb.a.a.a(IAccountService.class);
        defpackage.ddd.a.a.a(aVar);
        Bundle bundle = new Bundle();
        bundle.putInt("req_type", 1);
        bundle.putString("title", TextUtils.isEmpty(this.a.a) ? "高德分享" : this.a.a);
        bundle.putString("appName", "高德地图");
        if (!TextUtils.isEmpty(this.a.f)) {
            bundle.putString("summary", this.a.f);
        }
        bundle.putString("targetUrl", TextUtils.isEmpty(this.a.g) ? "https://amap.com" : this.a.g);
        ArrayList arrayList = new ArrayList();
        if (TextUtils.isEmpty(this.a.b) && this.a.c != null) {
            this.a.b = compressBitmapToTempFile(this.a.c, true);
        }
        if (!TextUtils.isEmpty(this.a.b)) {
            arrayList.add(this.a.b);
            bundle.putStringArrayList("imageUrl", arrayList);
        }
        Activity topActivity = AMapAppGlobal.getTopActivity();
        if (topActivity != null) {
            createInstance.shareToQzone(topActivity, bundle, aVar);
            return;
        }
        ToastHelper.showLongToast(AMapPageUtil.getAppContext().getString(R.string.email_share_get_content_failed));
        ddi.a().b();
    }

    public final void startShare() {
        if (TextUtils.isEmpty(this.a.g)) {
            ToastHelper.showLongToast(AMapPageUtil.getAppContext().getString(R.string.email_share_get_content_failed));
            ddi.a().b();
        } else if (!this.a.h || TextUtils.isEmpty(this.a.g)) {
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
