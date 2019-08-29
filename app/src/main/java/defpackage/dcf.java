package defpackage;

import android.app.Activity;
import android.graphics.Bitmap;
import android.text.TextUtils;
import com.amap.bundle.blutils.app.ConfigerHelper;
import com.amap.bundle.utils.ui.ToastHelper;
import com.android.dingtalk.share.ddsharemodule.DDShareApiFactory;
import com.android.dingtalk.share.ddsharemodule.IDDShareApi;
import com.android.dingtalk.share.ddsharemodule.message.DDImageMessage;
import com.android.dingtalk.share.ddsharemodule.message.DDMediaMessage;
import com.android.dingtalk.share.ddsharemodule.message.DDWebpageMessage;
import com.android.dingtalk.share.ddsharemodule.message.SendMessageToDD.Req;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;
import com.autonavi.minimap.bundle.share.entity.ShareBase;
import com.autonavi.minimap.bundle.share.entity.ShareParam.DingDingParam;
import com.autonavi.minimap.bundle.share.entity.ShareParam.DingDingParam.SendType;
import java.io.File;

/* renamed from: dcf reason: default package */
/* compiled from: DingDingShare */
public final class dcf extends ShareBase {
    public static boolean a = bno.a;
    private a b;

    public final int getShareType() {
        return 11;
    }

    public static boolean a() {
        IDDShareApi b2 = b();
        if (b2 != null) {
            return b2.isDDAppInstalled();
        }
        return false;
    }

    public static a a(DingDingParam dingDingParam) {
        a aVar = new a();
        aVar.h = dingDingParam.c;
        aVar.i = dingDingParam.d;
        aVar.f = dingDingParam.a;
        aVar.g = dingDingParam.b;
        aVar.b = dingDingParam.f;
        aVar.d = dingDingParam.h;
        aVar.c = dingDingParam.g;
        aVar.e = dingDingParam.i;
        aVar.a = dingDingParam.e;
        return aVar;
    }

    public dcf(a aVar) {
        this.b = aVar;
    }

    private void a(String str) {
        boolean z;
        if (TextUtils.isEmpty(str)) {
            ToastHelper.showLongToast(AMapPageUtil.getAppContext().getString(R.string.email_share_get_content_failed));
            notifyShareResult(-1);
            return;
        }
        String str2 = this.b.g;
        String str3 = this.b.b;
        String str4 = this.b.f;
        String str5 = this.b.d;
        Bitmap bitmap = this.b.c;
        DDWebpageMessage dDWebpageMessage = new DDWebpageMessage();
        if (!TextUtils.isEmpty(str2)) {
            dDWebpageMessage.mUrl = str2;
        }
        DDMediaMessage dDMediaMessage = new DDMediaMessage();
        dDMediaMessage.mMediaObject = dDWebpageMessage;
        dDMediaMessage.mTitle = str3;
        dDMediaMessage.mContent = str4;
        if (!TextUtils.isEmpty(str5)) {
            dDMediaMessage.mThumbUrl = str5;
        }
        if (bitmap != null) {
            dDMediaMessage.setThumbImage(bitmap);
        }
        Req req = new Req();
        req.mMediaMessage = dDMediaMessage;
        IDDShareApi b2 = b();
        if (b2 != null) {
            z = b2.sendReq(req);
        } else {
            z = false;
        }
        if (!z) {
            ToastHelper.showLongToast(AMapPageUtil.getAppContext().getString(R.string.puberr));
            notifyShareResult(-1);
        }
    }

    public static IDDShareApi b() {
        Activity activity = DoNotUseTool.getActivity();
        if (activity == null) {
            return null;
        }
        return DDShareApiFactory.createDDShareApi(activity, "dingoaodtmx3bkaoebkwjm", bno.a && "test".equals(ConfigerHelper.getInstance().getNetCondition()));
    }

    public final void startShare() {
        boolean z;
        if (!a()) {
            ToastHelper.showToast(AMapPageUtil.getAppContext().getString(R.string.share_pls_install_dingding));
            notifyShareResult(2);
            return;
        }
        IDDShareApi b2 = b();
        boolean z2 = false;
        if (b2 != null) {
            z = b2.isDDSupportAPI();
        } else {
            z = false;
        }
        if (!z) {
            ToastHelper.showToast(AMapPageUtil.getAppContext().getString(R.string.share_check_dingding_version));
            notifyShareResult(-1);
        } else if (!(!TextUtils.isEmpty(this.b.g))) {
            if (this.b.a.equals(SendType.OnlineImage) && !TextUtils.isEmpty(this.b.d)) {
                String str = this.b.d;
                DDImageMessage dDImageMessage = new DDImageMessage();
                dDImageMessage.mImageUrl = str;
                DDMediaMessage dDMediaMessage = new DDMediaMessage();
                dDMediaMessage.mMediaObject = dDImageMessage;
                Req req = new Req();
                req.mMediaMessage = dDMediaMessage;
                IDDShareApi b3 = b();
                if (b3 != null) {
                    b3.sendReq(req);
                }
                return;
            }
            if (this.b.a.equals(SendType.LocalImage) && !TextUtils.isEmpty(this.b.e)) {
                z2 = true;
            }
            if (z2) {
                String substring = this.b.e.substring(6);
                if (new File(substring).exists()) {
                    DDImageMessage dDImageMessage2 = new DDImageMessage();
                    dDImageMessage2.mImagePath = substring;
                    DDMediaMessage dDMediaMessage2 = new DDMediaMessage();
                    dDMediaMessage2.mMediaObject = dDImageMessage2;
                    Req req2 = new Req();
                    req2.mMediaMessage = dDMediaMessage2;
                    IDDShareApi b4 = b();
                    if (b4 != null) {
                        b4.sendReq(req2);
                    }
                }
                return;
            }
            ToastHelper.showLongToast(AMapPageUtil.getAppContext().getString(R.string.email_share_get_content_failed));
            notifyShareResult(-1);
        } else if (this.b.h) {
            requestShortUrl(this.b.g, this.b.i);
        } else {
            a(this.b.g);
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

    public static void a(String str, Object... objArr) {
        if (a && a) {
            try {
                String.format(str, objArr);
                if (!a) {
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
