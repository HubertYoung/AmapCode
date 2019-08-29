package com.autonavi.minimap.ajx3.util;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import android.graphics.drawable.Drawable;
import com.alipay.mobile.common.share.widget.ResUtils;
import com.autonavi.minimap.ajx3.Ajx;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.image.PictureParams;

public class AjxImageThemeUtil {
    public static String parseAjxImagePath(IAjxContext iAjxContext, String str) {
        if (str == null || "".equals(str)) {
            return null;
        }
        PictureParams pictureParams = new PictureParams();
        pictureParams.url = str;
        String processingPath = Ajx.getInstance().lookupLoader(str).processingPath(pictureParams);
        if (processingPath == null || processingPath.equals(str)) {
            return null;
        }
        return processingPath;
    }

    public static int loadAjxImageResourceId(IAjxContext iAjxContext, String str) {
        String parseAjxImagePath = parseAjxImagePath(iAjxContext, str);
        if (parseAjxImagePath == null) {
            return 0;
        }
        Context nativeContext = iAjxContext.getNativeContext();
        int identifier = nativeContext.getResources().getIdentifier(parseAjxImagePath, ResUtils.DRAWABLE, nativeContext.getPackageName());
        if (identifier > 0) {
            return identifier;
        }
        Resources resources = nativeContext.getResources();
        StringBuilder sb = new StringBuilder();
        sb.append(parseAjxImagePath);
        sb.append("_selector");
        int identifier2 = resources.getIdentifier(sb.toString(), ResUtils.DRAWABLE, nativeContext.getPackageName());
        if (identifier2 > 0) {
            return identifier2;
        }
        Resources resources2 = nativeContext.getResources();
        StringBuilder sb2 = new StringBuilder();
        sb2.append(parseAjxImagePath);
        sb2.append("_normal");
        return resources2.getIdentifier(sb2.toString(), ResUtils.DRAWABLE, nativeContext.getPackageName());
    }

    public static Drawable loadAjxImageResource(IAjxContext iAjxContext, String str) {
        int loadAjxImageResourceId = loadAjxImageResourceId(iAjxContext, str);
        if (loadAjxImageResourceId > 0) {
            try {
                return iAjxContext.getNativeContext().getResources().getDrawable(loadAjxImageResourceId);
            } catch (NotFoundException unused) {
            }
        }
        return null;
    }
}
