package com.alipay.mobile.antui.screenadpt;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup.MarginLayoutParams;
import com.alipay.mobile.antui.R;
import java.util.HashMap;
import java.util.Map;

public class AUAttrsUtils {
    public static final int HEIGHT = 1;
    public static final int MARGINBOTTOM = 5;
    public static final int MARGINLEFT = 2;
    public static final int MARGINRIGHT = 4;
    public static final int MARGINTOP = 3;
    public static final String TAG = "AUAttrsUtils";
    public static final int WIDTH = 0;

    public static Boolean isAP(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.AUScreenAdapt);
        boolean isAp = a.getBoolean(0, false);
        a.recycle();
        return Boolean.valueOf(isAp);
    }

    public static int[] getViewSizeAndMargin(Context context, Map<String, Object> property) {
        int width;
        int height;
        int marginLeft;
        int marginTop;
        int marginRight;
        int marginBottom;
        String widthStr = (String) property.get(AUAttrsConstant.VIEW_WIDTH);
        String heightStr = (String) property.get(AUAttrsConstant.VIEW_HEIGHT);
        String marginStr = (String) property.get(AUAttrsConstant.VIEW_MARGIN);
        String marginLeftStr = (String) property.get(AUAttrsConstant.VIEW_MARGIN_LEFT);
        String marginTopStr = (String) property.get(AUAttrsConstant.VIEW_MARGIN_TOP);
        String marginRightStr = (String) property.get(AUAttrsConstant.VIEW_MARGIN_RIGHT);
        String marginBottomStr = (String) property.get(AUAttrsConstant.VIEW_MARGIN_BOTTOM);
        if (TextUtils.equals(widthStr, "-1")) {
            width = -1;
        } else if (TextUtils.equals(widthStr, "-2")) {
            width = -2;
        } else {
            width = AUScreenAdaptTool.getApFromAttrsStr(context, widthStr);
        }
        if (TextUtils.equals(heightStr, "-1")) {
            height = -1;
        } else if (TextUtils.equals(heightStr, "-2")) {
            height = -2;
        } else {
            height = AUScreenAdaptTool.getApFromAttrsStr(context, heightStr);
        }
        if (!TextUtils.isEmpty(marginStr)) {
            int margin = AUScreenAdaptTool.getApFromAttrsStr(context, marginStr);
            marginLeft = margin;
            marginTop = margin;
            marginRight = margin;
            marginBottom = margin;
        } else {
            if (!TextUtils.isEmpty(marginLeftStr)) {
                marginLeft = AUScreenAdaptTool.getApFromAttrsStr(context, marginLeftStr);
            } else {
                marginLeft = 0;
            }
            if (!TextUtils.isEmpty(marginTopStr)) {
                marginTop = AUScreenAdaptTool.getApFromAttrsStr(context, marginTopStr);
            } else {
                marginTop = 0;
            }
            if (!TextUtils.isEmpty(marginRightStr)) {
                marginRight = AUScreenAdaptTool.getApFromAttrsStr(context, marginRightStr);
            } else {
                marginRight = 0;
            }
            if (!TextUtils.isEmpty(marginBottomStr)) {
                marginBottom = AUScreenAdaptTool.getApFromAttrsStr(context, marginBottomStr);
            } else {
                marginBottom = 0;
            }
        }
        return new int[]{width, height, marginLeft, marginTop, marginRight, marginBottom};
    }

    public static void replaceLayoutParam(Context context, MarginLayoutParams lp, int[] sizeAndMargin) {
        lp.width = chooseSizeAndMargin(context, lp.width, sizeAndMargin[0]);
        lp.height = chooseSizeAndMargin(context, lp.height, sizeAndMargin[1]);
        lp.setMargins(chooseSizeAndMargin(context, lp.leftMargin, sizeAndMargin[2]), chooseSizeAndMargin(context, lp.topMargin, sizeAndMargin[3]), chooseSizeAndMargin(context, lp.rightMargin, sizeAndMargin[4]), chooseSizeAndMargin(context, lp.bottomMargin, sizeAndMargin[5]));
    }

    public static int chooseSizeAndMargin(Context context, int res, int des) {
        if (des <= 0 && res > 0) {
            return AUScreenAdaptTool.getApFromPx(context, (float) res);
        }
        if (des > 0) {
            return des;
        }
        return res;
    }

    public static Map<String, Object> handleAttrs(AttributeSet attrs) {
        Map property = new HashMap();
        int attrsCount = attrs.getAttributeCount();
        if (attrsCount >= 0) {
            for (int i = 0; i < attrsCount; i++) {
                String name = attrs.getAttributeName(i);
                String value = attrs.getAttributeValue(i);
                if (value != null) {
                    property.put(name, value);
                }
            }
        }
        return property;
    }

    public static void adptApPadding(View view, Context context) {
        if (view != null) {
            view.setPadding(AUScreenAdaptTool.getApFromPx(context, (float) view.getPaddingLeft()), AUScreenAdaptTool.getApFromPx(context, (float) view.getPaddingTop()), AUScreenAdaptTool.getApFromPx(context, (float) view.getPaddingRight()), AUScreenAdaptTool.getApFromPx(context, (float) view.getPaddingBottom()));
        }
    }

    public static void adptApMinMax(View view, Context context) {
        if (view != null && view.getMinimumHeight() > 0) {
            view.setMinimumHeight(AUScreenAdaptTool.getApFromPx(context, (float) view.getMinimumHeight()));
        }
    }
}
