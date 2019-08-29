package com.alipay.mobile.antui.tablelist;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.alipay.mobile.antui.R;
import com.alipay.mobile.antui.api.AULineGroupItemInterface;
import com.alipay.mobile.antui.basic.AULinearLayout;
import com.alipay.mobile.antui.basic.AURelativeLayout;
import com.alipay.mobile.antui.iconfont.AUIconView;

public abstract class AUBaseListItem extends AURelativeLayout implements AULineGroupItemInterface {
    private View bottomDivider;
    protected AULinearLayout imageContainer;
    private Boolean isAP;
    protected boolean isShowArrow;
    private AULinearLayout listContainer;
    protected AUIconView mArrowView;
    protected AULinearLayout mListLayout;
    private View topDivider;
    private int type;

    /* access modifiers changed from: protected */
    public abstract void inflateLayout(Context context);

    public AUBaseListItem(Context context) {
        super(context);
        init(context, null);
    }

    public AUBaseListItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public AUBaseListItem(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.au_base_list_item, this, true);
        this.listContainer = (AULinearLayout) findViewById(R.id.listContainer);
        this.imageContainer = (AULinearLayout) findViewById(R.id.leftImageContainer);
        this.mListLayout = (AULinearLayout) findViewById(R.id.list_layout);
        this.mArrowView = (AUIconView) findViewById(R.id.list_arrow);
        inflateLayout(context);
        inflateImageLayout();
        initContent(context, attrs);
        setArrowVisibility(this.isShowArrow);
        setItemPositionStyle(this.type);
        setBackgroundResource(R.drawable.au_list_item_bg);
    }

    /* access modifiers changed from: protected */
    public void inflateImageLayout() {
    }

    private void initContent(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.listItem);
        this.type = typedArray.getInt(0, 16);
        this.isShowArrow = typedArray.getBoolean(11, true);
        typedArray.recycle();
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(heightMeasureSpec), 0));
    }

    public void setItemPositionStyle(int positionStyle) {
        switch (positionStyle) {
            case 16:
                addTopDivider();
                addBottomDivider(false);
                return;
            case 17:
                addTopDivider();
                addBottomDivider(true);
                return;
            case 18:
                cleanTopDivider();
                addBottomDivider(false);
                return;
            case 19:
                cleanTopDivider();
                addBottomDivider(true);
                return;
            case 20:
                cleanTopDivider();
                addBottomDivider(false);
                return;
            case 21:
                cleanTopDivider();
                cleanBottomDivider();
                return;
            default:
                return;
        }
    }

    private void addTopDivider() {
        if (this.topDivider == null) {
            this.topDivider = new View(getContext());
            this.topDivider.setBackgroundColor(getResources().getColor(R.color.AU_COLOR_LINE));
            addView(this.topDivider, (LayoutParams) getTopParams());
        }
        this.topDivider.setVisibility(0);
    }

    private void addBottomDivider(boolean hasLeftMargin) {
        if (this.bottomDivider == null) {
            this.bottomDivider = new View(getContext());
            this.bottomDivider.setBackgroundColor(getResources().getColor(R.color.AU_COLOR_LINE));
            addView(this.bottomDivider);
        }
        this.bottomDivider.setVisibility(0);
        this.bottomDivider.setLayoutParams(getBottomParams(hasLeftMargin));
    }

    private void cleanTopDivider() {
        if (this.topDivider != null) {
            this.topDivider.setVisibility(8);
        }
    }

    private void cleanBottomDivider() {
        if (this.bottomDivider != null) {
            this.bottomDivider.setVisibility(8);
        }
    }

    private RelativeLayout.LayoutParams getBottomParams(boolean hasLeftMargin) {
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-1, 1);
        layoutParams.addRule(12);
        if (hasLeftMargin) {
            layoutParams.addRule(5, R.id.listContainer);
        }
        return layoutParams;
    }

    private RelativeLayout.LayoutParams getTopParams() {
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-1, 1);
        layoutParams.addRule(10);
        return layoutParams;
    }

    public void setVisualStyle(int visualStyle) {
    }

    public void setArrowVisibility(boolean isVisible) {
        if (this.mArrowView != null) {
            this.mArrowView.setVisibility(isVisible ? 0 : 8);
        }
    }

    public void setImageContainerVisibility(boolean isVisible) {
        if (this.imageContainer != null) {
            this.imageContainer.setVisibility(isVisible ? 0 : 8);
        }
    }

    public void attachFlagToArrow(View flagView) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(-2, -2);
        params.gravity = 16;
        params.leftMargin = getResources().getDimensionPixelSize(R.dimen.AU_SPACE17);
        this.listContainer.addView(flagView, 1, (LayoutParams) params);
    }

    public AUIconView getArrowImage() {
        return this.mArrowView;
    }

    public Boolean isAP() {
        return this.isAP;
    }

    public void setAP(Boolean isAP2) {
        this.isAP = isAP2;
    }
}
