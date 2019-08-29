package defpackage;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.text.TextUtils;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.bundle.account.api.IAccountService;
import com.autonavi.bundle.account.api.IThirdAuth.a;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;
import com.autonavi.minimap.bundle.share.entity.ShareBase;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX.Req;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXTextObject;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import java.io.ByteArrayOutputStream;

/* renamed from: dcv reason: default package */
/* compiled from: WechatShare */
public final class dcv extends ShareBase {
    private j a;

    public dcv(j jVar) {
        this.a = jVar;
    }

    private static byte[] a(Bitmap bitmap) {
        if (bitmap == null || bitmap.isRecycled()) {
            return null;
        }
        byte[] a2 = a(bitmap, 100);
        if (a2.length > 32768) {
            a2 = a(bitmap, 90);
        }
        bitmap.recycle();
        return a2;
    }

    private static byte[] a(Bitmap bitmap, int i) {
        if (bitmap == null || bitmap.isRecycled()) {
            return null;
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(CompressFormat.JPEG, i, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        try {
            byteArrayOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return byteArray;
    }

    private static String a(String str, boolean z) {
        Object[] objArr = new Object[3];
        objArr[0] = Long.valueOf(System.currentTimeMillis());
        if (TextUtils.isEmpty(str)) {
            str = "default";
        }
        objArr[1] = str;
        objArr[2] = z ? "1" : "0";
        return String.format("%s_%s_%s", objArr);
    }

    private void a(String str) {
        if (TextUtils.isEmpty(str)) {
            ToastHelper.showLongToast(AMapPageUtil.getAppContext().getString(R.string.email_share_get_content_failed));
            notifyShareResult(3);
            return;
        }
        char c = 65413;
        if (this.a.a == 0) {
            String str2 = this.a.b;
            String str3 = this.a.f;
            Bitmap bitmap = this.a.c;
            int a2 = this.a.a();
            if (a2 == 3 && TextUtils.isEmpty(str3)) {
                str3 = AMapPageUtil.getAppContext().getString(R.string.share_wx_nocontent);
            }
            if (1 != a2 || b()) {
                WXWebpageObject wXWebpageObject = new WXWebpageObject();
                wXWebpageObject.webpageUrl = str;
                WXMediaMessage wXMediaMessage = new WXMediaMessage(wXWebpageObject);
                wXMediaMessage.title = str2;
                wXMediaMessage.description = str3;
                if (bitmap != null) {
                    byte[] a3 = a(bitmap);
                    if (a3 != null) {
                        if (a3.length < 32768) {
                            wXMediaMessage.thumbData = a3;
                        } else {
                            c = 1;
                        }
                    }
                }
                Req req = new Req();
                req.transaction = a((String) null, a2 == 1);
                req.message = wXMediaMessage;
                if (1 == a2) {
                    req.scene = 1;
                } else {
                    req.scene = 0;
                }
                c = a((BaseReq) req) ? (char) 0 : 3;
            } else {
                c = 2;
            }
        }
        switch (c) {
            case 1:
                ToastHelper.showToast(AMapPageUtil.getAppContext().getString(R.string.share_image_too_large));
                notifyShareResult(1);
                return;
            case 2:
                ToastHelper.showToast(AMapPageUtil.getAppContext().getString(R.string.share_check_wechat_version));
                notifyShareResult(2);
                return;
            case 3:
                notifyShareResult(3);
                break;
        }
    }

    public final void startShare() {
        if (!a()) {
            ToastHelper.showToast(AMapPageUtil.getAppContext().getString(R.string.share_pls_install_wechat));
            notifyShareResult(2);
            return;
        }
        char c = 0;
        if (this.a.a == 1) {
            String str = this.a.f;
            int a2 = this.a.a();
            if (1 == a2 && !b()) {
                c = 2;
            } else if (TextUtils.isEmpty(str)) {
                c = 4;
            } else {
                WXTextObject wXTextObject = new WXTextObject(str);
                WXMediaMessage wXMediaMessage = new WXMediaMessage();
                wXMediaMessage.description = str;
                wXMediaMessage.mediaObject = wXTextObject;
                Req req = new Req();
                req.transaction = a((String) null, a2 == 1);
                req.message = wXMediaMessage;
                if (1 == a2) {
                    req.scene = 1;
                } else {
                    req.scene = 0;
                }
                if (!a((BaseReq) req)) {
                    c = 3;
                }
            }
            switch (c) {
                case 1:
                case 2:
                    ToastHelper.showToast(AMapPageUtil.getAppContext().getString(R.string.share_check_wechat_version));
                    notifyShareResult(2);
                    return;
                case 3:
                    notifyShareResult(3);
                    break;
                case 4:
                    ToastHelper.showToast("发送失败");
                    notifyShareResult(4);
                    return;
            }
        } else if (this.a.a == 2) {
            Bitmap bitmap = this.a.c;
            int a3 = this.a.a();
            if (1 != a3 || b()) {
                WXImageObject wXImageObject = new WXImageObject(bitmap);
                WXMediaMessage wXMediaMessage2 = new WXMediaMessage();
                wXMediaMessage2.mediaObject = wXImageObject;
                if (bitmap != null) {
                    byte[] a4 = a(bitmap);
                    if (a4 == null || a4.length >= 32768) {
                        c = 1;
                    } else {
                        wXMediaMessage2.thumbData = a4;
                    }
                }
                Req req2 = new Req();
                req2.transaction = a((String) null, a3 == 1);
                req2.message = wXMediaMessage2;
                if (1 == a3) {
                    req2.scene = 1;
                } else {
                    req2.scene = 0;
                }
                if (!a((BaseReq) req2)) {
                    c = 3;
                }
            } else {
                c = 2;
            }
            switch (c) {
                case 1:
                    ToastHelper.showToast(AMapPageUtil.getAppContext().getString(R.string.share_image_too_large));
                    notifyShareResult(1);
                    return;
                case 2:
                    ToastHelper.showToast(AMapPageUtil.getAppContext().getString(R.string.share_check_wechat_version));
                    notifyShareResult(2);
                    return;
                case 3:
                    notifyShareResult(3);
                    break;
            }
        } else if (this.a.a == 3) {
            String str2 = this.a.d;
            Bitmap bitmap2 = this.a.c;
            int a5 = this.a.a();
            if (1 != a5 || b()) {
                byte[] a6 = a(bitmap2);
                WXImageObject wXImageObject2 = new WXImageObject();
                wXImageObject2.setImagePath(str2);
                WXMediaMessage wXMediaMessage3 = new WXMediaMessage();
                wXMediaMessage3.mediaObject = wXImageObject2;
                if (a6 != null) {
                    if (a6.length < 32768) {
                        wXMediaMessage3.thumbData = a6;
                    } else {
                        c = 1;
                    }
                }
                Req req3 = new Req();
                req3.transaction = a((String) "img", a5 == 1);
                req3.message = wXMediaMessage3;
                if (1 == a5) {
                    req3.scene = 1;
                } else {
                    req3.scene = 0;
                }
                if (!a((BaseReq) req3)) {
                    c = 3;
                }
            } else {
                c = 2;
            }
            switch (c) {
                case 1:
                    ToastHelper.showToast(AMapPageUtil.getAppContext().getString(R.string.share_image_too_large));
                    notifyShareResult(1);
                    return;
                case 2:
                    ToastHelper.showToast(AMapPageUtil.getAppContext().getString(R.string.share_check_wechat_version));
                    notifyShareResult(2);
                    return;
                case 3:
                    notifyShareResult(3);
                    break;
            }
        } else if (this.a.a == 4) {
            Bitmap bitmap3 = this.a.c;
            int a7 = this.a.a();
            if (1 == a7 && !b()) {
                c = 2;
            } else if (bitmap3 == null) {
                c = 4;
            } else {
                byte[] a8 = a(Bitmap.createScaledBitmap(bitmap3, 150, 150, true));
                WXImageObject wXImageObject3 = new WXImageObject(bitmap3);
                WXMediaMessage wXMediaMessage4 = new WXMediaMessage();
                wXMediaMessage4.mediaObject = wXImageObject3;
                if (a8 != null) {
                    if (a8.length < 32768) {
                        wXMediaMessage4.thumbData = a8;
                    } else {
                        c = 1;
                    }
                }
                Req req4 = new Req();
                req4.transaction = a((String) "img", a7 == 1);
                req4.message = wXMediaMessage4;
                if (1 == a7) {
                    req4.scene = 1;
                } else {
                    req4.scene = 0;
                }
                if (!a((BaseReq) req4)) {
                    c = 3;
                }
            }
            switch (c) {
                case 1:
                    ToastHelper.showToast(AMapPageUtil.getAppContext().getString(R.string.share_image_too_large));
                    notifyShareResult(1);
                    return;
                case 2:
                    ToastHelper.showToast(AMapPageUtil.getAppContext().getString(R.string.share_check_wechat_version));
                    notifyShareResult(2);
                    return;
                case 3:
                    notifyShareResult(3);
                    break;
                case 4:
                    ToastHelper.showToast("发送失败");
                    notifyShareResult(4);
                    return;
            }
        } else if (TextUtils.isEmpty(this.a.g)) {
            ToastHelper.showLongToast(AMapPageUtil.getAppContext().getString(R.string.email_share_get_content_failed));
            notifyShareResult(4);
        } else if (!this.a.h || TextUtils.isEmpty(this.a.g)) {
            a(this.a.g);
        } else {
            requestShortUrl(this.a.g, this.a.i);
        }
    }

    public final void onFinishResult(String str) {
        if (str == null) {
            ToastHelper.showLongToast(AMapPageUtil.getAppContext().getString(R.string.email_share_get_content_failed));
            notifyShareResult(4);
            return;
        }
        a(str);
    }

    public final int getShareType() {
        return this.a.a() == 1 ? 4 : 3;
    }

    public static boolean a() {
        IAccountService iAccountService = (IAccountService) a.a.a(IAccountService.class);
        if (iAccountService == null) {
            return false;
        }
        a a2 = iAccountService.c().a();
        if (a2 == null) {
            return false;
        }
        return a2.b();
    }

    private static boolean b() {
        IAccountService iAccountService = (IAccountService) a.a.a(IAccountService.class);
        if (iAccountService == null) {
            return false;
        }
        a a2 = iAccountService.c().a();
        if (a2 == null) {
            return false;
        }
        return a2.c();
    }

    private static boolean a(BaseReq baseReq) {
        IAccountService iAccountService = (IAccountService) a.a.a(IAccountService.class);
        if (iAccountService == null) {
            return false;
        }
        a a2 = iAccountService.c().a();
        if (a2 == null) {
            return false;
        }
        return a2.a(baseReq);
    }
}
