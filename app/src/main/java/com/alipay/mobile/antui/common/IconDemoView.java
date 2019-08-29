package com.alipay.mobile.antui.common;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout.LayoutParams;
import com.alipay.mobile.antui.R;
import com.alipay.mobile.antui.api.AntUI;
import com.alipay.mobile.antui.basic.AUFrameLayout;
import com.alipay.mobile.antui.basic.AUTextView;
import com.alipay.mobile.antui.iconfont.AUIconView;
import com.alipay.mobile.antui.theme.AUAbsTheme;
import com.alipay.mobile.antui.theme.AUThemeKey;
import com.alipay.mobile.antui.theme.AUThemeManager;

public class IconDemoView extends AUFrameLayout implements AntUI {
    private AUTextView mContentView;
    private AUIconView mIconView;

    public IconDemoView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.IconDemoView);
        init(getContext(), null, ta);
        initContent(getContext(), null, ta);
        initStyleByTheme(getContext());
        initAttrStyle(getContext(), null, ta);
        ta.recycle();
    }

    public IconDemoView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IconDemoView(Context context) {
        this(context, null, 0);
    }

    public void init(Context context, AttributeSet attrs, TypedArray ta) {
        LayoutInflater.from(context).inflate(R.layout.icon_demo_view, this, true);
        this.mIconView = (AUIconView) findViewById(R.id.icon_view);
        this.mContentView = (AUTextView) findViewById(R.id.content_view);
    }

    public void initContent(Context context, AttributeSet attrs, TypedArray ta) {
    }

    public void initStyleByTheme(Context context) {
        AUAbsTheme theme = AUThemeManager.getCurrentTheme();
        if (theme.containsKey(AUThemeKey.ICONDEMOVIEW_ICONCOLOR)) {
            setIconfontColorStates(getResources().getColorStateList(theme.getResId(AUThemeKey.ICONDEMOVIEW_ICONCOLOR).intValue()));
        }
    }

    public void initAttrStyle(Context context, AttributeSet attrs, TypedArray ta) {
        if (ta.hasValue(2)) {
            setIconfontUnicode(ta.getString(2));
        }
        if (ta.hasValue(1)) {
            setIconfontSize(ta.getDimension(1, getResources().getDimension(R.dimen.AU_ICONSIZE3)));
        }
        if (ta.hasValue(0)) {
            setIconfontColorStates(ta.getColorStateList(0));
        }
        if (ta.hasValue(4)) {
            setImageResource(ta.getResourceId(4, 0));
        }
        if (ta.hasValue(3)) {
            setImageViewSize(ta.getDimensionPixelSize(1, getResources().getDimensionPixelSize(R.dimen.AU_ICONSIZE3)));
        }
        if (ta.hasValue(5)) {
            setContent(ta.getString(5));
        }
    }

    public void upDateTheme(Context context, AUAbsTheme theme) {
    }

    public void setIconfontSize(float iconfontSize) {
        this.mIconView.setIconfontSize(iconfontSize);
    }

    public void setImageResource(int imageResource) {
        this.mIconView.setImageResource(imageResource);
    }

    public void setIconfontColorStates(ColorStateList iconfontColorStates) {
        this.mIconView.setIconfontColorStates(iconfontColorStates);
    }

    public void setImageViewSize(int imageViewSize) {
        LayoutParams layoutParams = (LayoutParams) this.mIconView.getLayoutParams();
        if (layoutParams == null) {
            layoutParams = new LayoutParams(imageViewSize, imageViewSize);
        } else {
            layoutParams.height = imageViewSize;
            layoutParams.width = imageViewSize;
        }
        this.mIconView.setLayoutParams(layoutParams);
    }

    public void setContent(String content) {
        this.mContentView.setText(content);
    }

    public void setIconfontUnicode(String iconfontUnicode) {
        this.mIconView.setIconfontUnicode(iconfontUnicode);
    }
}
