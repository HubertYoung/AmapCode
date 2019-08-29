package com.uc.webview.export.internal.interfaces;

import android.graphics.drawable.Drawable;
import com.uc.webview.export.annotations.Interface;

@Interface
/* compiled from: ProGuard */
public interface IWebResourceInternal {
    public static final String COLOR_DROP_DOWN_BG_COLOR = "drop_down_bg_color";
    public static final String COLOR_DROP_DOWN_TEXT_COLOR = "drop_down_text_color";
    public static final String DRAWABLE_UC_LOGO = "uc_logo";
    public static final String DRAWABLE_WEBVIEW_DROP_DOWN_SHADOW = "webview_drop_down_shadow";
    public static final String TEXT_DOWP_DOWN_BRAND_EXPOSURE = "dowp_down_brand_exposure_text";

    int getColor(String str);

    Drawable getDrawable(String str);

    String getText(String str);

    void setDrawable(String str, Drawable drawable);
}
