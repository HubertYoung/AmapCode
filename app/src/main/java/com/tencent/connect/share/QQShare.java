package com.tencent.connect.share;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Base64;
import com.autonavi.minimap.ajx3.loader.AjxHttpLoader;
import com.huawei.android.pushagent.PushReceiver.KEY_TYPE;
import com.tencent.connect.a.a;
import com.tencent.connect.auth.QQToken;
import com.tencent.connect.common.BaseApi;
import com.tencent.connect.common.Constants;
import com.tencent.connect.common.UIListenerManager;
import com.tencent.open.TDialog;
import com.tencent.open.a.f;
import com.tencent.open.b.d;
import com.tencent.open.utils.AsynLoadImg;
import com.tencent.open.utils.AsynLoadImgBack;
import com.tencent.open.utils.Global;
import com.tencent.open.utils.ServerSetting;
import com.tencent.open.utils.SystemUtils;
import com.tencent.open.utils.Util;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;
import java.io.File;
import java.util.ArrayList;

/* compiled from: ProGuard */
public class QQShare extends BaseApi {
    public static final int QQ_SHARE_SUMMARY_MAX_LENGTH = 60;
    public static final int QQ_SHARE_TITLE_MAX_LENGTH = 45;
    public static final String SHARE_TO_QQ_APP_NAME = "appName";
    public static final String SHARE_TO_QQ_AUDIO_URL = "audio_url";
    public static final String SHARE_TO_QQ_EXT_INT = "cflag";
    public static final String SHARE_TO_QQ_EXT_STR = "share_qq_ext_str";
    public static final int SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN = 1;
    public static final int SHARE_TO_QQ_FLAG_QZONE_ITEM_HIDE = 2;
    public static final String SHARE_TO_QQ_IMAGE_LOCAL_URL = "imageLocalUrl";
    public static final String SHARE_TO_QQ_IMAGE_URL = "imageUrl";
    public static final String SHARE_TO_QQ_KEY_TYPE = "req_type";
    public static final String SHARE_TO_QQ_SITE = "site";
    public static final String SHARE_TO_QQ_SUMMARY = "summary";
    public static final String SHARE_TO_QQ_TARGET_URL = "targetUrl";
    public static final String SHARE_TO_QQ_TITLE = "title";
    public static final int SHARE_TO_QQ_TYPE_APP = 6;
    public static final int SHARE_TO_QQ_TYPE_AUDIO = 2;
    public static final int SHARE_TO_QQ_TYPE_DEFAULT = 1;
    public static final int SHARE_TO_QQ_TYPE_IMAGE = 5;
    public String mViaShareQQType = "";

    public void releaseResource() {
    }

    public QQShare(Context context, QQToken qQToken) {
        super(qQToken);
    }

    public void shareToQQ(Activity activity, Bundle bundle, IUiListener iUiListener) {
        f.c("openSDK_LOG.QQShare", "shareToQQ() -- start.");
        String string = bundle.getString("imageUrl");
        String string2 = bundle.getString("title");
        String string3 = bundle.getString("summary");
        String string4 = bundle.getString("targetUrl");
        String string5 = bundle.getString("imageLocalUrl");
        int i = bundle.getInt("req_type", 1);
        f.c("openSDK_LOG.QQShare", "shareToQQ -- type: ".concat(String.valueOf(i)));
        switch (i) {
            case 1:
                this.mViaShareQQType = "1";
                break;
            case 2:
                this.mViaShareQQType = "3";
                break;
            case 5:
                this.mViaShareQQType = "2";
                break;
            case 6:
                this.mViaShareQQType = "4";
                break;
        }
        if (i == 6) {
            if (SystemUtils.compareQQVersion(activity, SystemUtils.QQ_VERSION_NAME_5_0_0) < 0) {
                iUiListener.onError(new UiError(-15, Constants.MSG_PARAM_APPSHARE_TOO_LOW, null));
                f.e("openSDK_LOG.QQShare", "shareToQQ, app share is not support below qq5.0.");
                d.a().a(1, "SHARE_CHECK_SDK", "1000", this.mToken.getAppId(), "0", Long.valueOf(SystemClock.elapsedRealtime()), 0, 1, "shareToQQ, app share is not support below qq5.0.");
                return;
            }
            string4 = String.format(ServerSetting.APP_DETAIL_PAGE, new Object[]{this.mToken.getAppId(), "mqq"});
            bundle.putString("targetUrl", string4);
        }
        if (Util.hasSDCard() || SystemUtils.compareQQVersion(activity, SystemUtils.QQ_VERSION_NAME_4_5_0) >= 0) {
            if (i == 5) {
                if (SystemUtils.compareQQVersion(activity, SystemUtils.QQ_VERSION_NAME_4_3_0) < 0) {
                    iUiListener.onError(new UiError(-6, Constants.MSG_PARAM_QQ_VERSION_ERROR, null));
                    f.e("openSDK_LOG.QQShare", "shareToQQ, version below 4.3 is not support.");
                    d.a().a(1, "SHARE_CHECK_SDK", "1000", this.mToken.getAppId(), "0", Long.valueOf(SystemClock.elapsedRealtime()), 0, 1, "shareToQQ, version below 4.3 is not support.");
                    return;
                } else if (!Util.fileExists(string5)) {
                    iUiListener.onError(new UiError(-6, Constants.MSG_PARAM_IMAGE_URL_FORMAT_ERROR, null));
                    f.e("openSDK_LOG.QQShare", "shareToQQ -- error: 非法的图片地址!");
                    d.a().a(1, "SHARE_CHECK_SDK", "1000", this.mToken.getAppId(), "0", Long.valueOf(SystemClock.elapsedRealtime()), 0, 1, Constants.MSG_PARAM_IMAGE_URL_FORMAT_ERROR);
                    return;
                }
            }
            if (i != 5) {
                if (TextUtils.isEmpty(string4) || (!string4.startsWith(AjxHttpLoader.DOMAIN_HTTP) && !string4.startsWith(AjxHttpLoader.DOMAIN_HTTPS))) {
                    iUiListener.onError(new UiError(-6, Constants.MSG_PARAM_ERROR, null));
                    f.e("openSDK_LOG.QQShare", "shareToQQ, targetUrl is empty or illegal..");
                    d.a().a(1, "SHARE_CHECK_SDK", "1000", this.mToken.getAppId(), "0", Long.valueOf(SystemClock.elapsedRealtime()), 0, 1, "shareToQQ, targetUrl is empty or illegal..");
                    return;
                } else if (TextUtils.isEmpty(string2)) {
                    iUiListener.onError(new UiError(-6, Constants.MSG_PARAM_TITLE_NULL_ERROR, null));
                    f.e("openSDK_LOG.QQShare", "shareToQQ, title is empty.");
                    d.a().a(1, "SHARE_CHECK_SDK", "1000", this.mToken.getAppId(), "0", Long.valueOf(SystemClock.elapsedRealtime()), 0, 1, "shareToQQ, title is empty.");
                    return;
                }
            }
            if (TextUtils.isEmpty(string) || string.startsWith(AjxHttpLoader.DOMAIN_HTTP) || string.startsWith(AjxHttpLoader.DOMAIN_HTTPS) || new File(string).exists()) {
                if (!TextUtils.isEmpty(string2) && string2.length() > 45) {
                    bundle.putString("title", Util.subString(string2, 45, null, null));
                }
                if (!TextUtils.isEmpty(string3) && string3.length() > 60) {
                    bundle.putString("summary", Util.subString(string3, 60, null, null));
                }
                if (Util.isMobileQQSupportShare(activity)) {
                    f.c("openSDK_LOG.QQShare", "shareToQQ, support share");
                    a(activity, bundle, iUiListener);
                } else {
                    try {
                        f.d("openSDK_LOG.QQShare", "shareToQQ, don't support share, will show download dialog");
                        TDialog tDialog = new TDialog(activity, "", getCommonDownloadQQUrl(""), null, this.mToken);
                        tDialog.show();
                    } catch (RuntimeException e) {
                        f.b("openSDK_LOG.QQShare", " shareToQQ, TDialog.show not in main thread", e);
                        e.printStackTrace();
                        iUiListener.onError(new UiError(-6, Constants.MSG_NOT_CALL_ON_MAIN_THREAD, null));
                    }
                }
                f.c("openSDK_LOG.QQShare", "shareToQQ() -- end.");
                return;
            }
            iUiListener.onError(new UiError(-6, Constants.MSG_PARAM_IMAGE_URL_FORMAT_ERROR, null));
            f.e("openSDK_LOG.QQShare", "shareToQQ, image url is emprty or illegal.");
            d.a().a(1, "SHARE_CHECK_SDK", "1000", this.mToken.getAppId(), "0", Long.valueOf(SystemClock.elapsedRealtime()), 0, 1, "shareToQQ, image url is emprty or illegal.");
            return;
        }
        iUiListener.onError(new UiError(-6, Constants.MSG_SHARE_NOSD_ERROR, null));
        f.e("openSDK_LOG.QQShare", "shareToQQ sdcard is null--end");
        d.a().a(1, "SHARE_CHECK_SDK", "1000", this.mToken.getAppId(), "0", Long.valueOf(SystemClock.elapsedRealtime()), 0, 1, "shareToQQ sdcard is null");
    }

    private void a(Activity activity, Bundle bundle, IUiListener iUiListener) {
        f.c("openSDK_LOG.QQShare", "shareToMobileQQ() -- start.");
        String string = bundle.getString("imageUrl");
        final String string2 = bundle.getString("title");
        final String string3 = bundle.getString("summary");
        f.a("openSDK_LOG.QQShare", "shareToMobileQQ -- imageUrl: ".concat(String.valueOf(string)));
        if (!TextUtils.isEmpty(string)) {
            if (!Util.isValidUrl(string)) {
                bundle.putString("imageUrl", null);
                if (SystemUtils.compareQQVersion(activity, SystemUtils.QQ_VERSION_NAME_4_3_0) < 0) {
                    f.b("openSDK_LOG.QQShare", "shareToMobileQQ -- QQ Version is < 4.3.0 ");
                } else {
                    f.b("openSDK_LOG.QQShare", "shareToMobileQQ -- QQ Version is > 4.3.0 ");
                    final Bundle bundle2 = bundle;
                    final IUiListener iUiListener2 = iUiListener;
                    final Activity activity2 = activity;
                    AnonymousClass2 r2 = new AsynLoadImgBack() {
                        public void batchSaved(int i, ArrayList<String> arrayList) {
                        }

                        public void saved(int i, String str) {
                            if (i == 0) {
                                bundle2.putString("imageLocalUrl", str);
                            } else if (TextUtils.isEmpty(string2) && TextUtils.isEmpty(string3)) {
                                if (iUiListener2 != null) {
                                    iUiListener2.onError(new UiError(-6, Constants.MSG_SHARE_GETIMG_ERROR, null));
                                    f.e("openSDK_LOG.QQShare", "shareToMobileQQ -- error: 获取分享图片失败!");
                                }
                                d.a().a(1, "SHARE_CHECK_SDK", "1000", QQShare.this.mToken.getAppId(), "0", Long.valueOf(SystemClock.elapsedRealtime()), 0, 1, Constants.MSG_SHARE_GETIMG_ERROR);
                                return;
                            }
                            QQShare.this.b(activity2, bundle2, iUiListener2);
                        }
                    };
                    a.a((Context) activity, string, (AsynLoadImgBack) r2);
                    f.c("openSDK_LOG.QQShare", "shareToMobileQQ() -- end");
                }
            } else if (TextUtils.isEmpty(string2) && TextUtils.isEmpty(string3)) {
                if (iUiListener != null) {
                    iUiListener.onError(new UiError(-6, Constants.MSG_SHARE_NOSD_ERROR, null));
                    f.e("openSDK_LOG.QQShare", Constants.MSG_SHARE_NOSD_ERROR);
                }
                d.a().a(1, "SHARE_CHECK_SDK", "1000", this.mToken.getAppId(), "0", Long.valueOf(SystemClock.elapsedRealtime()), 0, 1, Constants.MSG_SHARE_NOSD_ERROR);
                return;
            } else if (SystemUtils.compareQQVersion(activity, SystemUtils.QQ_VERSION_NAME_4_3_0) < 0) {
                AsynLoadImg asynLoadImg = new AsynLoadImg(activity);
                final Bundle bundle3 = bundle;
                final IUiListener iUiListener3 = iUiListener;
                final Activity activity3 = activity;
                AnonymousClass1 r22 = new AsynLoadImgBack() {
                    public void batchSaved(int i, ArrayList<String> arrayList) {
                    }

                    public void saved(int i, String str) {
                        if (i == 0) {
                            bundle3.putString("imageLocalUrl", str);
                        } else if (TextUtils.isEmpty(string2) && TextUtils.isEmpty(string3)) {
                            if (iUiListener3 != null) {
                                iUiListener3.onError(new UiError(-6, Constants.MSG_SHARE_GETIMG_ERROR, null));
                                f.e("openSDK_LOG.QQShare", "shareToMobileQQ -- error: 获取分享图片失败!");
                            }
                            d.a().a(1, "SHARE_CHECK_SDK", "1000", QQShare.this.mToken.getAppId(), "0", Long.valueOf(SystemClock.elapsedRealtime()), 0, 1, Constants.MSG_SHARE_GETIMG_ERROR);
                            return;
                        }
                        QQShare.this.b(activity3, bundle3, iUiListener3);
                    }
                };
                asynLoadImg.save(string, r22);
                f.c("openSDK_LOG.QQShare", "shareToMobileQQ() -- end");
            }
        }
        b(activity, bundle, iUiListener);
        f.c("openSDK_LOG.QQShare", "shareToMobileQQ() -- end");
    }

    /* access modifiers changed from: private */
    public void b(Activity activity, Bundle bundle, IUiListener iUiListener) {
        Activity activity2 = activity;
        Bundle bundle2 = bundle;
        IUiListener iUiListener2 = iUiListener;
        f.c("openSDK_LOG.QQShare", "doShareToQQ() -- start");
        StringBuffer stringBuffer = new StringBuffer("mqqapi://share/to_fri?src_type=app&version=1&file_type=news");
        String string = bundle2.getString("imageUrl");
        String string2 = bundle2.getString("title");
        String string3 = bundle2.getString("summary");
        String string4 = bundle2.getString("targetUrl");
        String string5 = bundle2.getString("audio_url");
        int i = bundle2.getInt("req_type", 1);
        int i2 = bundle2.getInt("cflag", 0);
        String string6 = bundle2.getString("share_qq_ext_str");
        String applicationLable = Util.getApplicationLable(activity);
        if (applicationLable == null) {
            applicationLable = bundle2.getString("appName");
        }
        String string7 = bundle2.getString("imageLocalUrl");
        String appId = this.mToken.getAppId();
        String openId = this.mToken.getOpenId();
        f.a("openSDK_LOG.QQShare", "doShareToQQ -- openid: ".concat(String.valueOf(openId)));
        if (!TextUtils.isEmpty(string)) {
            StringBuilder sb = new StringBuilder("&image_url=");
            sb.append(Base64.encodeToString(Util.getBytesUTF8(string), 2));
            stringBuffer.append(sb.toString());
        }
        if (!TextUtils.isEmpty(string7)) {
            StringBuilder sb2 = new StringBuilder("&file_data=");
            sb2.append(Base64.encodeToString(Util.getBytesUTF8(string7), 2));
            stringBuffer.append(sb2.toString());
        }
        if (!TextUtils.isEmpty(string2)) {
            StringBuilder sb3 = new StringBuilder("&title=");
            sb3.append(Base64.encodeToString(Util.getBytesUTF8(string2), 2));
            stringBuffer.append(sb3.toString());
        }
        if (!TextUtils.isEmpty(string3)) {
            StringBuilder sb4 = new StringBuilder("&description=");
            sb4.append(Base64.encodeToString(Util.getBytesUTF8(string3), 2));
            stringBuffer.append(sb4.toString());
        }
        if (!TextUtils.isEmpty(appId)) {
            stringBuffer.append("&share_id=".concat(String.valueOf(appId)));
        }
        if (!TextUtils.isEmpty(string4)) {
            StringBuilder sb5 = new StringBuilder("&url=");
            sb5.append(Base64.encodeToString(Util.getBytesUTF8(string4), 2));
            stringBuffer.append(sb5.toString());
        }
        if (!TextUtils.isEmpty(applicationLable)) {
            if (applicationLable.length() > 20) {
                StringBuilder sb6 = new StringBuilder();
                sb6.append(applicationLable.substring(0, 20));
                sb6.append("...");
                applicationLable = sb6.toString();
            }
            StringBuilder sb7 = new StringBuilder("&app_name=");
            sb7.append(Base64.encodeToString(Util.getBytesUTF8(applicationLable), 2));
            stringBuffer.append(sb7.toString());
        }
        if (!TextUtils.isEmpty(openId)) {
            StringBuilder sb8 = new StringBuilder("&open_id=");
            sb8.append(Base64.encodeToString(Util.getBytesUTF8(openId), 2));
            stringBuffer.append(sb8.toString());
        }
        if (!TextUtils.isEmpty(string5)) {
            StringBuilder sb9 = new StringBuilder("&audioUrl=");
            sb9.append(Base64.encodeToString(Util.getBytesUTF8(string5), 2));
            stringBuffer.append(sb9.toString());
        }
        StringBuilder sb10 = new StringBuilder("&req_type=");
        sb10.append(Base64.encodeToString(Util.getBytesUTF8(String.valueOf(i)), 2));
        stringBuffer.append(sb10.toString());
        if (!TextUtils.isEmpty(string6)) {
            StringBuilder sb11 = new StringBuilder("&share_qq_ext_str=");
            sb11.append(Base64.encodeToString(Util.getBytesUTF8(string6), 2));
            stringBuffer.append(sb11.toString());
        }
        StringBuilder sb12 = new StringBuilder("&cflag=");
        sb12.append(Base64.encodeToString(Util.getBytesUTF8(String.valueOf(i2)), 2));
        stringBuffer.append(sb12.toString());
        StringBuilder sb13 = new StringBuilder("doShareToQQ -- url: ");
        sb13.append(stringBuffer.toString());
        f.a("openSDK_LOG.QQShare", sb13.toString());
        a.a(Global.getContext(), this.mToken, "requireApi", "shareToNativeQQ");
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setData(Uri.parse(stringBuffer.toString()));
        Activity activity3 = activity;
        intent.putExtra(KEY_TYPE.PKGNAME, activity.getPackageName());
        if (SystemUtils.compareQQVersion(activity3, SystemUtils.QQ_VERSION_NAME_4_6_0) < 0) {
            f.c("openSDK_LOG.QQShare", "doShareToQQ, qqver below 4.6.");
            if (hasActivityForIntent(intent)) {
                UIListenerManager.getInstance().setListenerWithRequestcode(Constants.REQUEST_OLD_SHARE, iUiListener);
                startAssitActivity(activity3, intent, (int) Constants.REQUEST_OLD_SHARE);
            }
        } else {
            f.c("openSDK_LOG.QQShare", "doShareToQQ, qqver greater than 4.6.");
            if (UIListenerManager.getInstance().setListnerWithAction(SystemUtils.QQ_SHARE_CALLBACK_ACTION, iUiListener) != null) {
                f.c("openSDK_LOG.QQShare", "doShareToQQ, last listener is not null, cancel it.");
            }
            if (hasActivityForIntent(intent)) {
                startAssistActivity(activity3, (int) Constants.REQUEST_QQ_SHARE, intent, true);
            }
        }
        if (hasActivityForIntent(intent)) {
            d.a().a(this.mToken.getOpenId(), this.mToken.getAppId(), Constants.VIA_SHARE_TO_QQ, "10", "3", "0", this.mViaShareQQType, "0", "1", "0");
            d.a().a(0, "SHARE_CHECK_SDK", "1000", this.mToken.getAppId(), "0", Long.valueOf(SystemClock.elapsedRealtime()), 0, 1, "");
        } else {
            d.a().a(this.mToken.getOpenId(), this.mToken.getAppId(), Constants.VIA_SHARE_TO_QQ, "10", "3", "1", this.mViaShareQQType, "0", "1", "0");
            d.a().a(1, "SHARE_CHECK_SDK", "1000", this.mToken.getAppId(), "0", Long.valueOf(SystemClock.elapsedRealtime()), 0, 1, "hasActivityForIntent fail");
        }
        f.c("openSDK_LOG.QQShare", "doShareToQQ() --end");
    }
}
