package com.alipay.mobile.antui.iconfont.util;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.StateListDrawable;
import android.support.annotation.ColorInt;
import com.alipay.mobile.antui.R;
import com.alipay.mobile.antui.iconfont.AUIconDrawable;
import com.alipay.mobile.antui.iconfont.model.IconPaintBuilder;
import com.alipay.mobile.antui.utils.DensityUtil;
import com.alipay.mobile.antui.utils.StateListUtils;

public class IconUtils {
    public static StateListDrawable getTitleIcon_White(Context context, int resId) {
        return getTitleIcon(context, resId, context.getResources().getColor(R.color.AU_COLOR_UNIVERSAL_BG));
    }

    public static StateListDrawable getTitleIcon_Blue(Context context, int resId) {
        return getTitleIcon(context, resId, context.getResources().getColor(R.color.AU_COLOR_LINK));
    }

    public static StateListDrawable getTitleIcon(Context context, int resId, int normalColor) {
        return getIconListDrawable(context, DensityUtil.dip2px(context, 22.0f), StateListUtils.getColorStateList(normalColor, normalColor & 1728053247, 0), resId);
    }

    public static StateListDrawable getIconListDrawable(Context context, ColorStateList colorStateList, int resId) {
        return getIconListDrawable(context, 0, colorStateList, resId);
    }

    public static StateListDrawable getIconListDrawable(Context context, int defaultSize, ColorStateList colorStateList, int resId) {
        if (colorStateList == null) {
            return null;
        }
        int normalColor = colorStateList.getDefaultColor();
        int pressedColor = colorStateList.getColorForState(new int[]{16842919}, 0);
        int selectedColor = colorStateList.getColorForState(new int[]{16842913}, 0);
        return getIconListDrawable(context, defaultSize, normalColor, pressedColor, colorStateList.getColorForState(new int[]{-16842910}, 0), selectedColor, resId);
    }

    public static StateListDrawable getIconListDrawable(Context context, @ColorInt int normalColor, @ColorInt int pressedColor, int resId) {
        return getIconListDrawable(context, normalColor, pressedColor, 0, resId);
    }

    public static StateListDrawable getIconListDrawable(Context context, @ColorInt int normalColor, @ColorInt int pressedColor, @ColorInt int disabledColor, int resId) {
        return getIconListDrawable(context, 0, normalColor, pressedColor, disabledColor, 0, resId);
    }

    public static StateListDrawable getIconListDrawable(Context context, int defaultSize, @ColorInt int normalColor, @ColorInt int pressedColor, @ColorInt int disabledColor, int resId) {
        return getIconListDrawable(context, defaultSize, normalColor, pressedColor, disabledColor, 0, resId);
    }

    public static StateListDrawable getIconListDrawable(Context context, int defaultSize, @ColorInt int normalColor, @ColorInt int pressedColor, @ColorInt int disabledColor, @ColorInt int selectedColor, int resId) {
        if (normalColor == 0) {
            return null;
        }
        if (defaultSize == 0) {
            defaultSize = DensityUtil.dip2px(context, 20.0f);
        }
        AUIconDrawable normalDrawable = new AUIconDrawable(context, new IconPaintBuilder(normalColor, defaultSize, resId));
        AUIconDrawable pressedDrawable = null;
        AUIconDrawable disabledDrawable = null;
        AUIconDrawable selectedDrawable = null;
        if (pressedColor != 0) {
            pressedDrawable = new AUIconDrawable(context, new IconPaintBuilder(pressedColor, defaultSize, resId));
        }
        if (disabledColor != 0) {
            disabledDrawable = new AUIconDrawable(context, new IconPaintBuilder(disabledColor, defaultSize, resId));
        }
        if (selectedColor != 0) {
            selectedDrawable = new AUIconDrawable(context, new IconPaintBuilder(selectedColor, defaultSize, resId));
        }
        return StateListUtils.getStateListDrawable(normalDrawable, pressedDrawable, disabledDrawable, selectedDrawable);
    }
}
