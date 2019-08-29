package com.alipay.mobile.antui.bar;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import com.alipay.mobile.antui.R;
import com.alipay.mobile.antui.api.AntUI;
import com.alipay.mobile.antui.basic.AUTextView;
import com.alipay.mobile.antui.iconfont.util.IconUtils;
import com.alipay.mobile.antui.theme.AUAbsTheme;
import com.alipay.mobile.antui.theme.AUThemeKey;
import com.alipay.mobile.antui.theme.AUThemeManager;
import com.alipay.mobile.antui.utils.DensityUtil;

public class AUTabBarItem extends AUTextView implements AntUI {
    private static final String TAG = "AUTabBar";
    private ColorStateList defaultColor;
    private Integer defaultIconSize;
    private Integer defaultTextSize;
    private Drawable topDrawable;

    public AUTabBarItem(Context context) {
        this(context, null);
    }

    public AUTabBarItem(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AUTabBarItem(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TabBar);
            init(context, attrs, typedArray);
            initContent(context, attrs, typedArray);
            initStyleByTheme(context);
            initAttrStyle(context, attrs, typedArray);
            typedArray.recycle();
            return;
        }
        initContent(context, attrs, null);
        initStyleByTheme(context);
    }

    public void init(Context context, AttributeSet attrs, TypedArray typedArray) {
        setGravity(17);
    }

    public void initContent(Context context, AttributeSet attrs, TypedArray typedArray) {
        this.defaultIconSize = Integer.valueOf(context.getResources().getDimensionPixelSize(R.dimen.AU_ICONSIZE3));
        this.defaultTextSize = Integer.valueOf(context.getResources().getDimensionPixelSize(R.dimen.AU_TEXTSIZE1));
        this.defaultColor = context.getResources().getColorStateList(R.color.tabbar_text_color);
        if (getTextSize() == 0.0f) {
            setTextSize((float) this.defaultTextSize.intValue());
        }
        setTextColor(this.defaultColor);
    }

    public void initStyleByTheme(Context context) {
        AUAbsTheme theme = AUThemeManager.getCurrentTheme();
        if (theme.containsKey(AUThemeKey.TABBAR_TEXTCOLOR)) {
            this.defaultColor = theme.getColorStateList(context, AUThemeKey.TABBAR_TEXTCOLOR);
            setTextColor(this.defaultColor);
        }
        if (theme.containsKey(AUThemeKey.TABBAR_ICON_SIZE)) {
            this.defaultIconSize = theme.getDimensionPixelOffset(context, AUThemeKey.TABBAR_ICON_SIZE);
        }
        if (theme.containsKey(AUThemeKey.TABBAR_TEXTSIZE)) {
            setTextSize((float) theme.getDimensionPixelOffset(context, AUThemeKey.TABBAR_TEXTSIZE).intValue());
        }
    }

    public void initAttrStyle(Context context, AttributeSet attrs, TypedArray typedArray) {
        this.topDrawable = typedArray.getDrawable(0);
        this.defaultIconSize = Integer.valueOf(typedArray.getDimensionPixelSize(1, this.defaultIconSize.intValue()));
        this.topDrawable.setBounds(0, 0, this.defaultIconSize.intValue(), this.defaultIconSize.intValue());
        setCompoundDrawables(null, this.topDrawable, null, null);
        setCompoundDrawablePadding(DensityUtil.dip2px(context, 1.0f));
        ColorStateList textColor = typedArray.getColorStateList(2);
        if (textColor != null) {
            Log.d(TAG, "textColor != null");
            setTextColor(textColor);
        }
    }

    public void upDateTheme(Context context, AUAbsTheme theme) {
    }

    public void setIconInfo(int iconSize, Drawable drawable) {
        this.topDrawable = drawable;
        this.topDrawable.setBounds(0, 0, iconSize, iconSize);
        setCompoundDrawables(null, this.topDrawable, null, null);
    }

    public void setIconUnicode(int unicodeId) {
        setIconInfo(this.defaultIconSize.intValue(), IconUtils.getIconListDrawable(getContext(), this.defaultColor, unicodeId));
    }
}
