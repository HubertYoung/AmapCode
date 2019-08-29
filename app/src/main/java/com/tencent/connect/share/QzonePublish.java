package com.tencent.connect.share;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
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
import com.tencent.open.TDialog;
import com.tencent.open.a.f;
import com.tencent.open.b.d;
import com.tencent.open.utils.Global;
import com.tencent.open.utils.SystemUtils;
import com.tencent.open.utils.Util;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;
import java.io.File;
import java.net.URLEncoder;
import java.util.ArrayList;

/* compiled from: ProGuard */
public class QzonePublish extends BaseApi {
    public static final String PUBLISH_TO_QZONE_APP_NAME = "appName";
    public static final String PUBLISH_TO_QZONE_IMAGE_URL = "imageUrl";
    public static final String PUBLISH_TO_QZONE_KEY_TYPE = "req_type";
    public static final String PUBLISH_TO_QZONE_SUMMARY = "summary";
    public static final int PUBLISH_TO_QZONE_TYPE_PUBLISHMOOD = 3;
    public static final int PUBLISH_TO_QZONE_TYPE_PUBLISHVIDEO = 4;
    public static final String PUBLISH_TO_QZONE_VIDEO_DURATION = "videoDuration";
    public static final String PUBLISH_TO_QZONE_VIDEO_PATH = "videoPath";
    public static final String PUBLISH_TO_QZONE_VIDEO_SIZE = "videoSize";

    public QzonePublish(Context context, QQToken qQToken) {
        super(qQToken);
    }

    public void publishToQzone(Activity activity, Bundle bundle, IUiListener iUiListener) {
        final Bundle bundle2 = bundle;
        final IUiListener iUiListener2 = iUiListener;
        f.c("openSDK_LOG.QzonePublish", "publishToQzone() -- start");
        if (bundle2 == null) {
            iUiListener2.onError(new UiError(-6, Constants.MSG_PARAM_NULL_ERROR, null));
            f.e("openSDK_LOG.QzonePublish", "-->publishToQzone, params is null");
            d.a().a(1, "SHARE_CHECK_SDK", "1000", this.mToken.getAppId(), "4", Long.valueOf(SystemClock.elapsedRealtime()), 0, 1, Constants.MSG_PARAM_NULL_ERROR);
            return;
        }
        final Activity activity2 = activity;
        if (SystemUtils.compareQQVersion(activity2, SystemUtils.QQ_VERSION_NAME_5_9_5) < 0) {
            iUiListener2.onError(new UiError(-15, Constants.MSG_PARAM_VERSION_TOO_LOW, null));
            f.e("openSDK_LOG.QzonePublish", "-->publishToQzone, this is not support below qq 5.9.5");
            d.a().a(1, "SHARE_CHECK_SDK", "1000", this.mToken.getAppId(), "4", Long.valueOf(SystemClock.elapsedRealtime()), 0, 1, "publicToQzone, this is not support below qq 5.9.5");
            TDialog tDialog = new TDialog(activity2, "", getCommonDownloadQQUrl(""), null, this.mToken);
            tDialog.show();
            return;
        }
        String string = bundle2.getString("summary");
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
        if (!TextUtils.isEmpty(applicationLable)) {
            bundle2.putString("appName", applicationLable);
        }
        bundle2.putString("summary", string);
        int i = bundle2.getInt("req_type");
        if (i == 3) {
            if (stringArrayList != null && stringArrayList.size() > 0) {
                for (int i2 = 0; i2 < stringArrayList.size(); i2++) {
                    if (!Util.isValidPath(stringArrayList.get(i2))) {
                        stringArrayList.remove(i2);
                    }
                }
                bundle2.putStringArrayList("imageUrl", stringArrayList);
            }
            a(activity, bundle, iUiListener);
            f.c("openSDK_LOG.QzonePublish", "publishToQzone() --end");
        } else if (i == 4) {
            String string2 = bundle2.getString(PUBLISH_TO_QZONE_VIDEO_PATH);
            if (!Util.isValidPath(string2)) {
                f.e("openSDK_LOG.QzonePublish", "publishToQzone() video url invalid");
                iUiListener2.onError(new UiError(-5, Constants.MSG_PUBLISH_VIDEO_ERROR, null));
                return;
            }
            MediaPlayer mediaPlayer = new MediaPlayer();
            final String str = string2;
            final IUiListener iUiListener3 = iUiListener2;
            AnonymousClass1 r0 = new OnPreparedListener() {
                public void onPrepared(MediaPlayer mediaPlayer) {
                    long length = new File(str).length();
                    int duration = mediaPlayer.getDuration();
                    bundle2.putString(QzonePublish.PUBLISH_TO_QZONE_VIDEO_PATH, str);
                    bundle2.putInt(QzonePublish.PUBLISH_TO_QZONE_VIDEO_DURATION, duration);
                    bundle2.putLong(QzonePublish.PUBLISH_TO_QZONE_VIDEO_SIZE, length);
                    QzonePublish.this.a(activity2, bundle2, iUiListener3);
                    f.c("openSDK_LOG.QzonePublish", "publishToQzone() --end");
                }
            };
            mediaPlayer.setOnPreparedListener(r0);
            mediaPlayer.setOnErrorListener(new OnErrorListener() {
                public boolean onError(MediaPlayer mediaPlayer, int i, int i2) {
                    f.e("openSDK_LOG.QzonePublish", "publishToQzone() mediaplayer onError()");
                    iUiListener2.onError(new UiError(-5, Constants.MSG_PUBLISH_VIDEO_ERROR, null));
                    return false;
                }
            });
            try {
                mediaPlayer.setDataSource(string2);
                mediaPlayer.prepareAsync();
            } catch (Exception unused) {
                f.e("openSDK_LOG.QzonePublish", "publishToQzone() exception(s) occurred when preparing mediaplayer");
                iUiListener2.onError(new UiError(-5, Constants.MSG_PUBLISH_VIDEO_ERROR, null));
            }
        } else {
            iUiListener2.onError(new UiError(-5, Constants.MSG_SHARE_TYPE_ERROR, null));
            f.e("openSDK_LOG.QzonePublish", "publishToQzone() error--end请选择支持的分享类型");
            d.a().a(1, "SHARE_CHECK_SDK", "1000", this.mToken.getAppId(), "4", Long.valueOf(SystemClock.elapsedRealtime()), 0, 1, "publishToQzone() 请选择支持的分享类型");
        }
    }

    /* access modifiers changed from: private */
    public void a(Activity activity, Bundle bundle, IUiListener iUiListener) {
        int i;
        Bundle bundle2 = bundle;
        f.c("openSDK_LOG.QzonePublish", "doPublishToQzone() --start");
        StringBuffer stringBuffer = new StringBuffer("mqqapi://qzone/publish?src_type=app&version=1&file_type=news");
        ArrayList<String> stringArrayList = bundle2.getStringArrayList("imageUrl");
        String string = bundle2.getString("summary");
        int i2 = bundle2.getInt("req_type", 3);
        String string2 = bundle2.getString("appName");
        String string3 = bundle2.getString(PUBLISH_TO_QZONE_VIDEO_PATH);
        int i3 = bundle2.getInt(PUBLISH_TO_QZONE_VIDEO_DURATION);
        long j = bundle2.getLong(PUBLISH_TO_QZONE_VIDEO_SIZE);
        String appId = this.mToken.getAppId();
        String openId = this.mToken.getOpenId();
        f.a("openSDK_LOG.QzonePublish", "openId:".concat(String.valueOf(openId)));
        if (3 == i2 && stringArrayList != null) {
            StringBuffer stringBuffer2 = new StringBuffer();
            int size = stringArrayList.size();
            for (int i4 = 0; i4 < size; i4++) {
                stringBuffer2.append(URLEncoder.encode(stringArrayList.get(i4)));
                if (i4 != size - 1) {
                    stringBuffer2.append(";");
                }
            }
            StringBuilder sb = new StringBuilder("&image_url=");
            sb.append(Base64.encodeToString(Util.getBytesUTF8(stringBuffer2.toString()), 2));
            stringBuffer.append(sb.toString());
        }
        if (4 == i2) {
            StringBuilder sb2 = new StringBuilder("&videoPath=");
            sb2.append(Base64.encodeToString(Util.getBytesUTF8(string3), 2));
            stringBuffer.append(sb2.toString());
            StringBuilder sb3 = new StringBuilder("&videoDuration=");
            sb3.append(Base64.encodeToString(Util.getBytesUTF8(String.valueOf(i3)), 2));
            stringBuffer.append(sb3.toString());
            StringBuilder sb4 = new StringBuilder("&videoSize=");
            sb4.append(Base64.encodeToString(Util.getBytesUTF8(String.valueOf(j)), 2));
            stringBuffer.append(sb4.toString());
        }
        if (!TextUtils.isEmpty(string)) {
            StringBuilder sb5 = new StringBuilder("&description=");
            sb5.append(Base64.encodeToString(Util.getBytesUTF8(string), 2));
            stringBuffer.append(sb5.toString());
        }
        if (!TextUtils.isEmpty(appId)) {
            stringBuffer.append("&share_id=".concat(String.valueOf(appId)));
        }
        if (!TextUtils.isEmpty(string2)) {
            StringBuilder sb6 = new StringBuilder("&app_name=");
            sb6.append(Base64.encodeToString(Util.getBytesUTF8(string2), 2));
            stringBuffer.append(sb6.toString());
        }
        if (!Util.isEmpty(openId)) {
            StringBuilder sb7 = new StringBuilder("&open_id=");
            i = 2;
            sb7.append(Base64.encodeToString(Util.getBytesUTF8(openId), 2));
            stringBuffer.append(sb7.toString());
        } else {
            i = 2;
        }
        StringBuilder sb8 = new StringBuilder("&req_type=");
        sb8.append(Base64.encodeToString(Util.getBytesUTF8(String.valueOf(i2)), i));
        stringBuffer.append(sb8.toString());
        StringBuilder sb9 = new StringBuilder("doPublishToQzone, url: ");
        sb9.append(stringBuffer.toString());
        f.a("openSDK_LOG.QzonePublish", sb9.toString());
        a.a(Global.getContext(), this.mToken, "requireApi", "shareToNativeQQ");
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setData(Uri.parse(stringBuffer.toString()));
        intent.putExtra(KEY_TYPE.PKGNAME, activity.getPackageName());
        if (hasActivityForIntent(intent)) {
            startAssistActivity(activity, (int) Constants.REQUEST_QZONE_SHARE, intent, false);
            d.a().a(0, "SHARE_CHECK_SDK", "1000", this.mToken.getAppId(), "4", Long.valueOf(SystemClock.elapsedRealtime()), 0, 1, "hasActivityForIntent success");
        } else {
            f.e("openSDK_LOG.QzonePublish", "doPublishToQzone() target activity not found");
            d.a().a(1, "SHARE_CHECK_SDK", "1000", this.mToken.getAppId(), "4", Long.valueOf(SystemClock.elapsedRealtime()), 0, 1, "hasActivityForIntent fail");
        }
        f.c("openSDK_LOG", "doPublishToQzone() --end");
    }
}
