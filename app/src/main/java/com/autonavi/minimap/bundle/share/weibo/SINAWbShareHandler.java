package com.autonavi.minimap.bundle.share.weibo;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import com.alipay.mobile.beehive.imageedit.constant.Constants;
import com.amap.bundle.blutils.app.ConfigerHelper;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.common.PageBundle;
import com.sina.weibo.sdk.WbSdk;
import com.sina.weibo.sdk.WeiboAppManager;
import com.sina.weibo.sdk.api.ImageObject;
import com.sina.weibo.sdk.api.TextObject;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.share.WbShareHandler;
import java.io.File;

public class SINAWbShareHandler {
    private static final String APP_KEY = "884965267";
    private static final String SCOPE = "email,direct_messages_read,direct_messages_write";
    private WbShareHandler mShareHandler;

    static class a {
        static final SINAWbShareHandler a = new SINAWbShareHandler();
    }

    private SINAWbShareHandler() {
        installSdk();
    }

    public static SINAWbShareHandler getInstance() {
        return a.a;
    }

    public void installSdk() {
        Application application = AMapAppGlobal.getApplication();
        if (application != null) {
            try {
                ddl.a("SINAWbShareHandler", "wbsdk install start");
                WbSdk.install(application, new AuthInfo(application, APP_KEY, ConfigerHelper.REDIRECT_URL, SCOPE));
                ddl.a("SINAWbShareHandler", "wbsdk install success");
            } catch (Exception e) {
                StringBuilder sb = new StringBuilder("wbsdk install exception:");
                sb.append(e.getMessage());
                ddl.a("SINAWbShareHandler", sb.toString());
            }
        }
    }

    public void initShareHandler() {
        try {
            ddl.a("SINAWbShareHandler", "initShareHandler");
            Activity topActivity = AMapAppGlobal.getTopActivity();
            if (topActivity != null) {
                this.mShareHandler = new WbShareHandler(topActivity);
                this.mShareHandler.registerApp();
                ddl.a("SINAWbShareHandler", "initShareHandler registerApp success");
            }
        } catch (Exception e) {
            StringBuilder sb = new StringBuilder("initShareHandler e:");
            sb.append(e.getMessage());
            ddl.a("SINAWbShareHandler", sb.toString());
        }
    }

    public void sendMessage(PageBundle pageBundle) {
        if (pageBundle != null && pageBundle.containsKey("poi_x") && pageBundle.containsKey("poi_y")) {
            cfg.a((long) pageBundle.getInt("poi_x"), (long) pageBundle.getInt("poi_y"));
        }
        sendMultiMessage(pageBundle);
    }

    private void sendMultiMessage(PageBundle pageBundle) {
        Bitmap bitmap;
        ddl.a("SINAWbShareHandler", "sendMultiMessage");
        if (pageBundle != null) {
            WeiboMultiMessage weiboMultiMessage = new WeiboMultiMessage();
            String string = pageBundle.getString("short_url");
            String string2 = pageBundle.getString("content");
            String string3 = pageBundle.getString("title");
            String string4 = pageBundle.getString("pic_url");
            try {
                bitmap = (Bitmap) pageBundle.getObject(Constants.KEY_BITMAP);
            } catch (Exception unused) {
                bitmap = null;
            }
            boolean z = pageBundle.getBoolean("ISFROMNAVI", false);
            weiboMultiMessage.textObject = getTextObj(string3, string2, string);
            ImageObject imageObj = getImageObj(string4, bitmap, z);
            if (imageObj != null && WbSdk.isWbInstall(AMapAppGlobal.getApplication()) && WeiboAppManager.getInstance(AMapAppGlobal.getApplication()).getWbAppInfo().getSupportVersion() > 10000) {
                weiboMultiMessage.imageObject = imageObj;
            }
            try {
                this.mShareHandler.shareMessage(weiboMultiMessage, false);
                ddl.a("SINAWbShareHandler", "send shareMessage");
            } catch (Exception e) {
                StringBuilder sb = new StringBuilder("sendMultiMessage e:");
                sb.append(e.getMessage());
                ddl.a("SINAWbShareHandler", sb.toString());
            }
        }
    }

    private TextObject getTextObj(String str, String str2, String str3) {
        TextObject textObject = new TextObject();
        textObject.title = str;
        StringBuilder sb = new StringBuilder();
        sb.append(str2);
        if (TextUtils.isEmpty(str3)) {
            str3 = "";
        }
        sb.append(str3);
        textObject.text = sb.toString();
        return textObject;
    }

    private ImageObject getImageObj(String str, Bitmap bitmap, boolean z) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        ImageObject imageObject = new ImageObject();
        if (z) {
            if (!new File(str).exists()) {
                return null;
            }
            Bitmap a2 = ahc.a((Context) AMapAppGlobal.getApplication(), str);
            if (a2 == null) {
                return null;
            }
            imageObject.setImageObject(a2);
            return imageObject;
        } else if (bitmap == null) {
            return null;
        } else {
            imageObject.setImageObject(bitmap);
            return imageObject;
        }
    }
}
