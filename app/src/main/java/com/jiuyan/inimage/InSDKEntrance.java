package com.jiuyan.inimage;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.jiuyan.inimage.callback.IEditCallback;
import com.jiuyan.inimage.callback.IFaceDelegate;
import com.jiuyan.inimage.callback.IPhotoSaveDelegate;
import com.jiuyan.inimage.http.HttpInitializer;
import com.jiuyan.inimage.util.b;
import com.jiuyan.inimage.util.q;

public class InSDKEntrance {
    private static String sDownloadBiz = "INPhotoEdit";
    public static IEditCallback sEditCallback;
    private static String sH5Action = "NEBULANOTIFY_inPaster";
    private static String sH5PasterAppId = "60000033";
    public static IFaceDelegate sIFaceDelegate;
    public static IPhotoSaveDelegate sPhotoSaveDelegate;

    public InSDKEntrance() {
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public static void initialize(Context context) {
    }

    public static void setH5PasterAppId(String str) {
        sH5PasterAppId = str;
    }

    public static String getH5PasterAppId() {
        return sH5PasterAppId;
    }

    public static void setDownloadBiz(String str) {
        sDownloadBiz = str;
    }

    public static String getDownloadBiz() {
        return sDownloadBiz;
    }

    public static void setH5Action(String str) {
        sH5Action = str;
    }

    public static String getH5Action() {
        return sH5Action;
    }

    public static boolean startEdit(Context context, InSDKConfig inSDKConfig) {
        if (context == null || inSDKConfig == null) {
            throw new IllegalArgumentException("params can't be null");
        }
        try {
            b.a = null;
            HttpInitializer.initialize(context);
            if (inSDKConfig.faceDelegate != null) {
                sIFaceDelegate = inSDKConfig.faceDelegate;
            }
            if (inSDKConfig.editCallback != null) {
                sEditCallback = inSDKConfig.editCallback;
            }
            if (inSDKConfig.photoSaveDelegate != null) {
                sPhotoSaveDelegate = inSDKConfig.photoSaveDelegate;
            }
            b.a = inSDKConfig.bitmap;
            Intent intent = new Intent(context, InPhotoEditActivity.class);
            if (context instanceof Application) {
                intent.addFlags(268435456);
            }
            intent.putExtra("insdk_param", inSDKConfig.flag);
            intent.putExtra("insdk_param_1", inSDKConfig.activitySecure);
            if (inSDKConfig.textConfig != null && inSDKConfig.textConfig.size() > 0) {
                for (String next : inSDKConfig.textConfig.keySet()) {
                    intent.putExtra(next, inSDKConfig.textConfig.get(next));
                }
            }
            q.a("mingtian ", "start activity");
            LauncherApplicationAgent.getInstance().getMicroApplicationContext().startExtActivity(null, intent);
            inSDKConfig.reset();
            return true;
        } catch (Exception e) {
            sIFaceDelegate = null;
            sEditCallback = null;
            sPhotoSaveDelegate = null;
            b.a = null;
            inSDKConfig.reset();
            e.printStackTrace();
            q.a("startEdit exception ", e.toString());
            return false;
        }
    }

    public static void release() {
        sIFaceDelegate = null;
        sEditCallback = null;
        sPhotoSaveDelegate = null;
        b.a = null;
    }
}
