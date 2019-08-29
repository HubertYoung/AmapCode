package com.jiuyan.inimage;

import android.graphics.Bitmap;
import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;
import com.jiuyan.inimage.callback.IEditCallback;
import com.jiuyan.inimage.callback.IFaceDelegate;
import com.jiuyan.inimage.callback.IPhotoSaveDelegate;
import java.util.HashMap;
import java.util.Map;

public class InSDKConfig {
    private static final int FLAG_BASE = 0;
    public static final int FLAG_EMPTY = 0;
    public static final int FLAG_FULL = -1;
    public static final int FLAG_FUNC_CROPPER = 1;
    public static final int FLAG_FUNC_MAGIC = 4;
    public static final int FLAG_FUNC_PASTERMALL = 8;
    public static final int FLAG_FUNC_ROTATION = 2;
    public static final int FLAG_FUNC_TEXT = 16;
    public static final String KEY_CONFIG_TEXT_FUNC_CROPPER = "KEY_CONFIG_TEXT_FUNC_CROPPER";
    public static final String KEY_CONFIG_TEXT_FUNC_PASTERMALL = "KEY_CONFIG_TEXT_FUNC_PASTERMALL";
    public static final String KEY_CONFIG_TEXT_FUNC_ROTATION = "KEY_CONFIG_TEXT_FUNC_ROTATION";
    public static final String KEY_CONFIG_TEXT_FUNC_TEXT = "KEY_CONFIG_TEXT_FUNC_TEXT";
    public static final String KEY_CONFIG_TEXT_LEFT = "KEY_CONFIG_TEXT_LEFT";
    public static final String KEY_CONFIG_TEXT_RIGHT = "KEY_CONFIG_TEXT_RIGHT";
    protected boolean activitySecure = false;
    protected Bitmap bitmap;
    protected IEditCallback editCallback;
    protected IFaceDelegate faceDelegate;
    protected int flag = 0;
    protected IPhotoSaveDelegate photoSaveDelegate;
    protected Map<String, String> textConfig = new HashMap();

    public InSDKConfig() {
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public static InSDKConfig build() {
        return new InSDKConfig();
    }

    private boolean isTextConfigFlagAvailable(String str) {
        if (KEY_CONFIG_TEXT_LEFT.equals(str) || KEY_CONFIG_TEXT_RIGHT.equals(str) || KEY_CONFIG_TEXT_FUNC_CROPPER.equals(str) || KEY_CONFIG_TEXT_FUNC_ROTATION.equals(str) || KEY_CONFIG_TEXT_FUNC_PASTERMALL.equals(str) || KEY_CONFIG_TEXT_FUNC_TEXT.equals(str)) {
            return true;
        }
        return false;
    }

    public InSDKConfig setFlag(int i) {
        this.flag = i;
        return this;
    }

    public InSDKConfig addFlag(int i) {
        this.flag |= i;
        return this;
    }

    public InSDKConfig customText(String str, String str2) {
        if (isTextConfigFlagAvailable(str)) {
            this.textConfig.put(str, str2);
        }
        return this;
    }

    public InSDKConfig bitmap(Bitmap bitmap2) {
        this.bitmap = bitmap2;
        return this;
    }

    public InSDKConfig editCallback(IEditCallback iEditCallback) {
        this.editCallback = iEditCallback;
        return this;
    }

    public InSDKConfig faceDelegate(IFaceDelegate iFaceDelegate) {
        this.faceDelegate = iFaceDelegate;
        return this;
    }

    public InSDKConfig photoSaveDelegate(IPhotoSaveDelegate iPhotoSaveDelegate) {
        this.photoSaveDelegate = iPhotoSaveDelegate;
        return this;
    }

    public InSDKConfig activitySecure(boolean z) {
        this.activitySecure = z;
        return this;
    }

    public void reset() {
        this.flag = 0;
        this.bitmap = null;
        this.editCallback = null;
        this.faceDelegate = null;
        this.photoSaveDelegate = null;
        this.textConfig.clear();
        this.activitySecure = false;
    }

    public static boolean isEnabled(int i, int i2) {
        return (i & i2) == i2;
    }
}
