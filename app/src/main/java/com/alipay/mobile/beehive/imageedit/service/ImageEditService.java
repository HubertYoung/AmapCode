package com.alipay.mobile.beehive.imageedit.service;

import android.content.Context;
import android.os.Bundle;
import com.alipay.mobile.framework.app.MicroApplication;
import com.alipay.mobile.framework.service.ext.ExternalService;
import java.util.Map;

public abstract class ImageEditService extends ExternalService {
    public static final String IN_EDIT_TYPE_CROP = "crop";
    public static final String IN_EDIT_TYPE_FULL = "full";
    public static final String IN_EDIT_TYPE_MAGIC = "magic";
    public static final String IN_EDIT_TYPE_PASTER = "paster";
    public static final String IN_EDIT_TYPE_ROTATE = "rotate";
    public static final String IN_EDIT_TYPE_TEXT = "text";
    public static final String IN_KEY_CANCEL_TEXT = "cancelText";
    public static final String IN_KEY_DISABLE_SCREEN_SHOT = "disableScreenshot";
    public static final String IN_KEY_WATERMARK_TEXT = "watermarkText";
    public static final String[] validEditTypes = {"full", IN_EDIT_TYPE_MAGIC, IN_EDIT_TYPE_CROP, IN_EDIT_TYPE_ROTATE, IN_EDIT_TYPE_PASTER, "text"};

    public abstract void editImage(MicroApplication microApplication, ImageEditListener imageEditListener, String str, String str2, Bundle bundle);

    public abstract void editImageUseIn(Context context, Map<String, Object> map, InImageEditListener inImageEditListener);

    public abstract void editImageUseIn(Map<String, Object> map, InImageEditListener inImageEditListener);
}
