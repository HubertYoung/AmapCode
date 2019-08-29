package com.tencent.connect.share;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Base64;
import com.huawei.android.pushagent.PushReceiver.KEY_TYPE;
import com.tencent.connect.a.a;
import com.tencent.connect.auth.QQToken;
import com.tencent.connect.common.BaseApi;
import com.tencent.connect.common.Constants;
import com.tencent.connect.common.UIListenerManager;
import com.tencent.open.TDialog;
import com.tencent.open.a.f;
import com.tencent.open.b.d;
import com.tencent.open.utils.AsynLoadImgBack;
import com.tencent.open.utils.Global;
import com.tencent.open.utils.ServerSetting;
import com.tencent.open.utils.SystemUtils;
import com.tencent.open.utils.Util;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;
import java.net.URLEncoder;
import java.util.ArrayList;

/* compiled from: ProGuard */
public class QzoneShare extends BaseApi {
    public static final String SHARE_TO_QQ_APP_NAME = "appName";
    public static final String SHARE_TO_QQ_AUDIO_URL = "audio_url";
    public static final String SHARE_TO_QQ_EXT_INT = "cflag";
    public static final String SHARE_TO_QQ_EXT_STR = "share_qq_ext_str";
    public static final String SHARE_TO_QQ_IMAGE_LOCAL_URL = "imageLocalUrl";
    public static final String SHARE_TO_QQ_IMAGE_URL = "imageUrl";
    public static final String SHARE_TO_QQ_SITE = "site";
    public static final String SHARE_TO_QQ_SUMMARY = "summary";
    public static final String SHARE_TO_QQ_TARGET_URL = "targetUrl";
    public static final String SHARE_TO_QQ_TITLE = "title";
    public static final String SHARE_TO_QZONE_KEY_TYPE = "req_type";
    public static final int SHARE_TO_QZONE_TYPE_APP = 6;
    public static final int SHARE_TO_QZONE_TYPE_IMAGE = 5;
    public static final int SHARE_TO_QZONE_TYPE_IMAGE_TEXT = 1;
    public static final int SHARE_TO_QZONE_TYPE_NO_TYPE = 0;
    private boolean a = true;
    private boolean b = false;
    private boolean c = false;
    private boolean d = false;
    public String mViaShareQzoneType = "";

    public void releaseResource() {
    }

    public QzoneShare(Context context, QQToken qQToken) {
        super(qQToken);
    }

    public void shareToQzone(Activity activity, Bundle bundle, IUiListener iUiListener) {
        final Activity activity2 = activity;
        final Bundle bundle2 = bundle;
        final IUiListener iUiListener2 = iUiListener;
        f.c("openSDK_LOG.QzoneShare", "shareToQzone() -- start");
        if (bundle2 == null) {
            iUiListener2.onError(new UiError(-6, Constants.MSG_PARAM_NULL_ERROR, null));
            f.e("openSDK_LOG.QzoneShare", "shareToQzone() params is null");
            d.a().a(1, "SHARE_CHECK_SDK", "1000", this.mToken.getAppId(), "4", Long.valueOf(SystemClock.elapsedRealtime()), 0, 1, Constants.MSG_PARAM_NULL_ERROR);
            return;
        }
        String string = bundle2.getString("title");
        String string2 = bundle2.getString("summary");
        String string3 = bundle2.getString("targetUrl");
        ArrayList<String> stringArrayList = bundle2.getStringArrayList("imageUrl");
        String applicationLable = Util.getApplicationLable(activity);
        if (applicationLable == null) {
            applicationLable = bundle2.getString("appName");
        } else if (applicationLable.length() > 20) {
            StringBuilder sb = new StringBuilder();
            sb.append(applicationLable.substring(0, 20));
            sb.append("...");
            applicationLable = sb.toString();
        }
        int i = bundle2.getInt("req_type");
        if (i != 1) {
            switch (i) {
                case 5:
                    this.mViaShareQzoneType = "2";
                    break;
                case 6:
                    this.mViaShareQzoneType = "4";
                    break;
                default:
                    this.mViaShareQzoneType = "1";
                    break;
            }
        } else {
            this.mViaShareQzoneType = "1";
        }
        if (i != 1) {
            switch (i) {
                case 5:
                    iUiListener2.onError(new UiError(-5, Constants.MSG_SHARE_TYPE_ERROR, null));
                    f.e("openSDK_LOG.QzoneShare", "shareToQzone() error--end请选择支持的分享类型");
                    d.a().a(1, "SHARE_CHECK_SDK", "1000", this.mToken.getAppId(), "4", Long.valueOf(SystemClock.elapsedRealtime()), 0, 1, "shareToQzone() 请选择支持的分享类型");
                    return;
                case 6:
                    if (SystemUtils.compareQQVersion(activity2, SystemUtils.QQ_VERSION_NAME_5_0_0) >= 0) {
                        string3 = String.format(ServerSetting.APP_DETAIL_PAGE, new Object[]{this.mToken.getAppId(), "mqq"});
                        bundle2.putString("targetUrl", string3);
                        break;
                    } else {
                        iUiListener2.onError(new UiError(-15, Constants.MSG_PARAM_APPSHARE_TOO_LOW, null));
                        f.e("openSDK_LOG.QzoneShare", "-->shareToQzone, app share is not support below qq5.0.");
                        d.a().a(1, "SHARE_CHECK_SDK", "1000", this.mToken.getAppId(), "4", Long.valueOf(SystemClock.elapsedRealtime()), 0, 1, "shareToQzone, app share is not support below qq5.0.");
                        return;
                    }
                default:
                    if (Util.isEmpty(string) && Util.isEmpty(string2)) {
                        if (stringArrayList != null && stringArrayList.size() != 0) {
                            this.a = false;
                            break;
                        } else {
                            StringBuilder sb2 = new StringBuilder("来自");
                            sb2.append(applicationLable);
                            sb2.append("的分享");
                            string = sb2.toString();
                        }
                    }
                    this.a = true;
                    break;
            }
        } else {
            this.a = true;
        }
        this.b = false;
        this.c = true;
        this.d = false;
        if (Util.hasSDCard() || SystemUtils.compareQQVersion(activity2, SystemUtils.QQ_VERSION_NAME_4_5_0) >= 0) {
            if (this.a) {
                if (TextUtils.isEmpty(string3)) {
                    iUiListener2.onError(new UiError(-5, Constants.MSG_PARAM_TARGETURL_NULL_ERROR, null));
                    f.e("openSDK_LOG.QzoneShare", "shareToQzone() targetUrl null error--end");
                    d.a().a(1, "SHARE_CHECK_SDK", "1000", this.mToken.getAppId(), "4", Long.valueOf(SystemClock.elapsedRealtime()), 0, 1, Constants.MSG_PARAM_TARGETURL_NULL_ERROR);
                    return;
                } else if (!Util.isValidUrl(string3)) {
                    iUiListener2.onError(new UiError(-5, Constants.MSG_PARAM_TARGETURL_ERROR, null));
                    f.e("openSDK_LOG.QzoneShare", "shareToQzone() targetUrl error--end");
                    d.a().a(1, "SHARE_CHECK_SDK", "1000", this.mToken.getAppId(), "4", Long.valueOf(SystemClock.elapsedRealtime()), 0, 1, Constants.MSG_PARAM_TARGETURL_ERROR);
                    return;
                }
            }
            if (this.b) {
                bundle2.putString("title", "");
                bundle2.putString("summary", "");
            } else if (!this.c || !Util.isEmpty(string)) {
                if (!Util.isEmpty(string) && string.length() > 200) {
                    bundle2.putString("title", Util.subString(string, 200, null, null));
                }
                if (!Util.isEmpty(string2) && string2.length() > 600) {
                    bundle2.putString("summary", Util.subString(string2, 600, null, null));
                }
            } else {
                iUiListener2.onError(new UiError(-6, Constants.MSG_PARAM_TITLE_NULL_ERROR, null));
                f.e("openSDK_LOG.QzoneShare", "shareToQzone() title is null--end");
                d.a().a(1, "SHARE_CHECK_SDK", "1000", this.mToken.getAppId(), "4", Long.valueOf(SystemClock.elapsedRealtime()), 0, 1, "shareToQzone() title is null");
                return;
            }
            if (!TextUtils.isEmpty(applicationLable)) {
                bundle2.putString("appName", applicationLable);
            }
            if (stringArrayList != null && (stringArrayList == null || stringArrayList.size() != 0)) {
                for (int i2 = 0; i2 < stringArrayList.size(); i2++) {
                    String str = stringArrayList.get(i2);
                    if (!Util.isValidUrl(str) && !Util.isValidPath(str)) {
                        stringArrayList.remove(i2);
                    }
                }
                if (stringArrayList.size() == 0) {
                    iUiListener2.onError(new UiError(-6, Constants.MSG_PARAM_IMAGE_URL_FORMAT_ERROR, null));
                    f.e("openSDK_LOG.QzoneShare", "shareToQzone() MSG_PARAM_IMAGE_URL_FORMAT_ERROR--end");
                    d.a().a(1, "SHARE_CHECK_SDK", "1000", this.mToken.getAppId(), "4", Long.valueOf(SystemClock.elapsedRealtime()), 0, 1, "shareToQzone() 非法的图片地址!");
                    return;
                }
                bundle2.putStringArrayList("imageUrl", stringArrayList);
            } else if (this.d) {
                iUiListener2.onError(new UiError(-6, Constants.MSG_PARAM_IMAGE_ERROR, null));
                f.e("openSDK_LOG.QzoneShare", "shareToQzone() imageUrl is null -- end");
                d.a().a(1, "SHARE_CHECK_SDK", "1000", this.mToken.getAppId(), "4", Long.valueOf(SystemClock.elapsedRealtime()), 0, 1, "shareToQzone() imageUrl is null");
                return;
            }
            if (SystemUtils.compareQQVersion(activity2, SystemUtils.QQ_VERSION_NAME_4_6_0) >= 0) {
                f.c("openSDK_LOG.QzoneShare", "shareToQzone() qqver greater than 4.6.0");
                a.a((Context) activity2, stringArrayList, (AsynLoadImgBack) new AsynLoadImgBack() {
                    public void saved(int i, String str) {
                    }

                    public void batchSaved(int i, ArrayList<String> arrayList) {
                        if (i == 0) {
                            bundle2.putStringArrayList("imageUrl", arrayList);
                        }
                        QzoneShare.this.a(activity2, bundle2, iUiListener2);
                    }
                });
            } else if (SystemUtils.compareQQVersion(activity2, SystemUtils.QQ_VERSION_NAME_4_2_0) < 0 || SystemUtils.compareQQVersion(activity2, SystemUtils.QQ_VERSION_NAME_4_6_0) >= 0) {
                f.d("openSDK_LOG.QzoneShare", "shareToQzone() qqver below 4.2.0, will show download dialog");
                TDialog tDialog = new TDialog(activity2, "", getCommonDownloadQQUrl(""), null, this.mToken);
                tDialog.show();
            } else {
                f.d("openSDK_LOG.QzoneShare", "shareToQzone() qqver between 4.2.0 and 4.6.0, will use qqshare");
                QQShare qQShare = new QQShare(activity2, this.mToken);
                if (stringArrayList != null && stringArrayList.size() > 0) {
                    String str2 = stringArrayList.get(0);
                    if (i != 5 || Util.fileExists(str2)) {
                        bundle2.putString("imageLocalUrl", str2);
                    } else {
                        iUiListener2.onError(new UiError(-6, Constants.MSG_PARAM_IMAGE_URL_MUST_BE_LOCAL, null));
                        f.e("openSDK_LOG.QzoneShare", "shareToQzone()手Q版本过低，纯图分享不支持网路图片");
                        d.a().a(1, "SHARE_CHECK_SDK", "1000", this.mToken.getAppId(), "4", Long.valueOf(SystemClock.elapsedRealtime()), 0, 1, "shareToQzone()手Q版本过低，纯图分享不支持网路图片");
                        return;
                    }
                }
                if (SystemUtils.compareQQVersion(activity2, SystemUtils.QQ_VERSION_NAME_4_5_0) >= 0) {
                    bundle2.putInt("cflag", 1);
                }
                qQShare.shareToQQ(activity2, bundle2, iUiListener2);
            }
            f.c("openSDK_LOG.QzoneShare", "shareToQzone() --end");
            return;
        }
        iUiListener2.onError(new UiError(-6, Constants.MSG_SHARE_NOSD_ERROR, null));
        f.e("openSDK_LOG.QzoneShare", "shareToQzone() sdcard is null--end");
        d.a().a(1, "SHARE_CHECK_SDK", "1000", this.mToken.getAppId(), "4", Long.valueOf(SystemClock.elapsedRealtime()), 0, 1, Constants.MSG_SHARE_NOSD_ERROR);
    }

    /* access modifiers changed from: private */
    public void a(Activity activity, Bundle bundle, IUiListener iUiListener) {
        int i;
        Activity activity2 = activity;
        Bundle bundle2 = bundle;
        IUiListener iUiListener2 = iUiListener;
        f.c("openSDK_LOG.QzoneShare", "doshareToQzone() --start");
        StringBuffer stringBuffer = new StringBuffer("mqqapi://share/to_qzone?src_type=app&version=1&file_type=news");
        ArrayList<String> stringArrayList = bundle2.getStringArrayList("imageUrl");
        String string = bundle2.getString("title");
        String string2 = bundle2.getString("summary");
        String string3 = bundle2.getString("targetUrl");
        String string4 = bundle2.getString("audio_url");
        int i2 = bundle2.getInt("req_type", 1);
        String string5 = bundle2.getString("appName");
        int i3 = bundle2.getInt("cflag", 0);
        String string6 = bundle2.getString("share_qq_ext_str");
        String appId = this.mToken.getAppId();
        String openId = this.mToken.getOpenId();
        f.a("openSDK_LOG.QzoneShare", "openId:".concat(String.valueOf(openId)));
        if (stringArrayList != null) {
            StringBuffer stringBuffer2 = new StringBuffer();
            int i4 = 9;
            if (stringArrayList.size() <= 9) {
                i4 = stringArrayList.size();
            }
            int i5 = 0;
            while (i5 < i4) {
                ArrayList<String> arrayList = stringArrayList;
                stringBuffer2.append(URLEncoder.encode(stringArrayList.get(i5)));
                if (i5 != i4 - 1) {
                    stringBuffer2.append(";");
                }
                i5++;
                stringArrayList = arrayList;
            }
            StringBuilder sb = new StringBuilder("&image_url=");
            sb.append(Base64.encodeToString(Util.getBytesUTF8(stringBuffer2.toString()), 2));
            stringBuffer.append(sb.toString());
        }
        if (!TextUtils.isEmpty(string)) {
            StringBuilder sb2 = new StringBuilder("&title=");
            sb2.append(Base64.encodeToString(Util.getBytesUTF8(string), 2));
            stringBuffer.append(sb2.toString());
        }
        if (!TextUtils.isEmpty(string2)) {
            StringBuilder sb3 = new StringBuilder("&description=");
            sb3.append(Base64.encodeToString(Util.getBytesUTF8(string2), 2));
            stringBuffer.append(sb3.toString());
        }
        if (!TextUtils.isEmpty(appId)) {
            stringBuffer.append("&share_id=".concat(String.valueOf(appId)));
        }
        if (!TextUtils.isEmpty(string3)) {
            StringBuilder sb4 = new StringBuilder("&url=");
            sb4.append(Base64.encodeToString(Util.getBytesUTF8(string3), 2));
            stringBuffer.append(sb4.toString());
        }
        if (!TextUtils.isEmpty(string5)) {
            StringBuilder sb5 = new StringBuilder("&app_name=");
            sb5.append(Base64.encodeToString(Util.getBytesUTF8(string5), 2));
            stringBuffer.append(sb5.toString());
        }
        if (!Util.isEmpty(openId)) {
            StringBuilder sb6 = new StringBuilder("&open_id=");
            sb6.append(Base64.encodeToString(Util.getBytesUTF8(openId), 2));
            stringBuffer.append(sb6.toString());
        }
        if (!Util.isEmpty(string4)) {
            StringBuilder sb7 = new StringBuilder("&audioUrl=");
            i = 2;
            sb7.append(Base64.encodeToString(Util.getBytesUTF8(string4), 2));
            stringBuffer.append(sb7.toString());
        } else {
            i = 2;
        }
        StringBuilder sb8 = new StringBuilder("&req_type=");
        sb8.append(Base64.encodeToString(Util.getBytesUTF8(String.valueOf(i2)), i));
        stringBuffer.append(sb8.toString());
        if (!Util.isEmpty(string6)) {
            StringBuilder sb9 = new StringBuilder("&share_qq_ext_str=");
            sb9.append(Base64.encodeToString(Util.getBytesUTF8(string6), i));
            stringBuffer.append(sb9.toString());
        }
        StringBuilder sb10 = new StringBuilder("&cflag=");
        sb10.append(Base64.encodeToString(Util.getBytesUTF8(String.valueOf(i3)), i));
        stringBuffer.append(sb10.toString());
        StringBuilder sb11 = new StringBuilder("doshareToQzone, url: ");
        sb11.append(stringBuffer.toString());
        f.a("openSDK_LOG.QzoneShare", sb11.toString());
        a.a(Global.getContext(), this.mToken, "requireApi", "shareToNativeQQ");
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setData(Uri.parse(stringBuffer.toString()));
        Activity activity3 = activity;
        intent.putExtra(KEY_TYPE.PKGNAME, activity.getPackageName());
        if (SystemUtils.compareQQVersion(activity3, SystemUtils.QQ_VERSION_NAME_4_6_0) < 0) {
            if (hasActivityForIntent(intent)) {
                UIListenerManager.getInstance().setListenerWithRequestcode(11104, iUiListener);
                startAssitActivity(activity3, intent, 11104);
            }
            f.c("openSDK_LOG.QzoneShare", "doShareToQzone() -- QQ Version is < 4.6.0");
        } else {
            f.c("openSDK_LOG.QzoneShare", "doShareToQzone() -- QQ Version is > 4.6.0");
            if (UIListenerManager.getInstance().setListnerWithAction(SystemUtils.QZONE_SHARE_CALLBACK_ACTION, iUiListener) != null) {
                f.c("openSDK_LOG.QzoneShare", "doShareToQzone() -- do listener onCancel()");
            }
            if (hasActivityForIntent(intent)) {
                startAssistActivity(activity3, (int) Constants.REQUEST_QZONE_SHARE, intent, false);
            }
        }
        if (hasActivityForIntent(intent)) {
            d.a().a(this.mToken.getOpenId(), this.mToken.getAppId(), Constants.VIA_SHARE_TO_QZONE, "11", "3", "0", this.mViaShareQzoneType, "0", "1", "0");
            d.a().a(0, "SHARE_CHECK_SDK", "1000", this.mToken.getAppId(), "4", Long.valueOf(SystemClock.elapsedRealtime()), 0, 1, "");
        } else {
            d.a().a(this.mToken.getOpenId(), this.mToken.getAppId(), Constants.VIA_SHARE_TO_QZONE, "11", "3", "1", this.mViaShareQzoneType, "0", "1", "0");
            d.a().a(1, "SHARE_CHECK_SDK", "1000", this.mToken.getAppId(), "4", Long.valueOf(SystemClock.elapsedRealtime()), 0, 1, "hasActivityForIntent fail");
        }
        f.c("openSDK_LOG", "doShareToQzone() --end");
    }
}
