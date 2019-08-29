package com.alipay.mobile.antui.dialog;

import android.content.Context;
import android.support.annotation.ColorInt;
import android.text.TextUtils;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.widget.LinearLayout.LayoutParams;
import com.alipay.mobile.antui.badge.AUBadgeView;
import com.alipay.mobile.antui.badge.AUBadgeView.Style;
import com.alipay.mobile.antui.basic.AULinearLayout;
import com.alipay.mobile.antui.basic.AUTextView;
import com.alipay.mobile.antui.iconfont.AUIconView;
import com.alipay.mobile.antui.iconfont.model.IconInfo;
import com.alipay.mobile.antui.iconfont.model.MessagePopItem;
import com.alipay.mobile.antui.theme.AUAbsTheme;
import com.alipay.mobile.antui.theme.AUThemeKey;
import com.alipay.mobile.antui.theme.AUThemeManager;
import com.alipay.mobile.antui.utils.AuiLogger;
import java.util.HashMap;

public abstract class BaseMessagePopItemView extends AULinearLayout {
    private int horizonPadding;
    protected AUBadgeView mBadgeView;
    protected AUIconView mIconView;
    protected AUTextView mTitleView;
    private boolean makeTitleMax = false;

    /* access modifiers changed from: protected */
    public abstract int getHorizonPadding(Context context);

    /* access modifiers changed from: protected */
    public abstract void initView(Context context);

    public BaseMessagePopItemView(Context context) {
        super(context);
        initView(context);
        this.horizonPadding = getHorizonPadding(context);
        setPadding(this.horizonPadding, 0, this.horizonPadding, 0);
    }

    public void setMakeTitleMax(boolean makeTitleMax2) {
        this.makeTitleMax = makeTitleMax2;
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int badgeWidth;
        if (this.makeTitleMax) {
            if (this.mBadgeView == null || this.mBadgeView.getVisibility() != 0) {
                this.mTitleView.setMaxWidth(Integer.MAX_VALUE);
            } else {
                int mWidth = MeasureSpec.getSize(widthMeasureSpec);
                int spec = MeasureSpec.makeMeasureSpec(mWidth, Integer.MIN_VALUE);
                int iconWidth = 0;
                this.mBadgeView.measure(spec, 0);
                LayoutParams lp1 = (LayoutParams) this.mBadgeView.getLayoutParams();
                if (lp1 != null) {
                    badgeWidth = this.mBadgeView.getMeasuredWidth() + lp1.leftMargin + lp1.rightMargin;
                } else {
                    badgeWidth = this.mBadgeView.getMeasuredWidth();
                }
                if (this.mIconView != null && this.mIconView.getVisibility() == 0) {
                    this.mIconView.measure(spec, 0);
                    LayoutParams lp2 = (LayoutParams) this.mIconView.getLayoutParams();
                    iconWidth = lp2 != null ? this.mIconView.getMeasuredWidth() + lp2.leftMargin + lp2.rightMargin : this.mIconView.getMeasuredWidth();
                }
                this.mTitleView.setMaxWidth(((mWidth - badgeWidth) - iconWidth) - (this.horizonPadding * 2));
            }
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public void setPopItem(MessagePopItem item) {
        if (item != null && !TextUtils.isEmpty(item.title)) {
            setTitleText(item.title);
            setIconView(this.mIconView, item.icon);
            setBadgeInfo(item.externParam);
        }
    }

    public void setTitleText(CharSequence title) {
        this.mTitleView.setText(title);
    }

    public void setTitleTextSize(int size) {
        this.mTitleView.setTextSize(0, (float) size);
    }

    public void setIconfontSize(int size) {
        this.mIconView.setIconfontSize((float) size);
        ViewGroup.LayoutParams params = this.mIconView.getLayoutParams();
        params.width = size;
        params.height = size;
        this.mIconView.setLayoutParams(params);
    }

    public void setTitleTextColor(@ColorInt int color) {
        this.mTitleView.setTextColor(color);
    }

    private void setIconView(AUIconView imageView, IconInfo iconInfo) {
        if (iconInfo == null) {
            imageView.setVisibility(8);
        } else if (iconInfo.type == 3) {
            if (iconInfo.iconRes != 0) {
                imageView.setImageResource(iconInfo.iconRes);
                imageView.setVisibility(0);
            } else if (iconInfo.drawable != null) {
                imageView.setImageDrawable(iconInfo.drawable);
                imageView.setVisibility(0);
            } else {
                imageView.setVisibility(8);
            }
        } else if (iconInfo.type == 2) {
            imageView.setVisibility(0);
            imageView.setIconfontUnicode(iconInfo.icon);
            AUAbsTheme theme = AUThemeManager.getCurrentTheme();
            Integer iconfontColor = theme.getColor(getContext(), AUThemeKey.FLOATMENU_ICON_COLOR);
            if (iconfontColor != null) {
                imageView.setIconfontColor(iconfontColor.intValue());
            }
            Float iconfontSize = theme.getDimension(getContext(), AUThemeKey.FLOATMENU_ICON_SIZE);
            if (iconfontSize != null) {
                imageView.setIconfontSize(iconfontSize.floatValue());
            }
        } else {
            imageView.setVisibility(8);
        }
    }

    private void setBadgeInfo(HashMap<String, Object> ext) {
        try {
            this.mBadgeView.dismiss();
            if (ext != null) {
                Object badgeType = ext.get(AUBadgeView.KEY_BADGE_STYLE);
                if ((badgeType instanceof String) && !TextUtils.isEmpty((String) badgeType)) {
                    this.mBadgeView.setVisibility(0);
                    if (badgeType.equals("msg_redpoint")) {
                        this.mBadgeView.setRedPoint(true);
                    } else if (badgeType.equals("msg_text")) {
                        this.mBadgeView.setStyleAndMsgText(Style.NUM, (String) ext.get(AUBadgeView.KEY_BADGE_CONTENT));
                    } else if (badgeType.equals("text")) {
                        this.mBadgeView.setStyleAndMsgText(Style.TEXT, (String) ext.get(AUBadgeView.KEY_BADGE_CONTENT));
                    }
                } else if (badgeType instanceof Style) {
                    this.mBadgeView.setStyleAndContent((Style) badgeType, (String) ext.get(AUBadgeView.KEY_BADGE_CONTENT));
                }
            }
        } catch (Throwable e) {
            AuiLogger.error("BaseMessagePopItemView", "setBadgeInfo ext : " + e);
        }
    }
}
