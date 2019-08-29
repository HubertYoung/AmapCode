package com.alipay.mobile.antui.tablelist;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import com.alipay.mobile.antui.R;
import com.alipay.mobile.antui.basic.AUEmptyGoneTextView;
import com.alipay.mobile.antui.basic.AUImageView;
import com.alipay.mobile.antui.basic.AULinearLayout;
import com.alipay.mobile.antui.basic.AURoundImageView;
import com.alipay.mobile.antui.basic.AUTextView;
import com.alipay.mobile.antui.common.AUCheckIcon;
import com.alipay.mobile.antui.utils.DensityUtil;

public abstract class AUAbsListItem extends AUBaseListItem {
    public static final int ARROW_STYLE_DOWN = 33;
    public static final int ARROW_STYLE_RIGHT = 32;
    public static final int BUTTON = 17;
    public static final int NEW_FLAG_TYPE_IMAGE = 2;
    public static final int NEW_FLAG_TYPE_TEXT = 1;
    public static final int SIZE_LARGE = 49;
    public static final int SIZE_NORMAL = 48;
    public static final int TEXT_IMAGE = 16;
    protected int arrowType;
    protected int emojiSize;
    @Deprecated
    protected int fatherId;
    private boolean hasRound;
    protected boolean isShowCheck;
    protected AULinearLayout leftContainer;
    private Drawable leftImage;
    private float leftImageHeight;
    protected int leftImageSizeType;
    private float leftImageWidth;
    private String leftText;
    private int leftTextColor;
    protected View leftTextNewFlagView;
    private float leftTextSize;
    protected AUCheckIcon mLeftCheckIcon;
    protected AURoundImageView mLeftImageView;
    protected AUEmptyGoneTextView mLeftSubTextView;
    protected AUTextView mLeftTextView;
    protected boolean supportEmoji;

    /* access modifiers changed from: protected */
    public abstract int getImageVerticalMargin(Context context);

    /* access modifiers changed from: protected */
    public abstract int getLeftImageSize(Context context);

    public AUAbsListItem(Context context) {
        this(context, null);
    }

    public AUAbsListItem(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AUAbsListItem(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.supportEmoji = false;
        this.emojiSize = 0;
        this.isShowCheck = false;
        this.arrowType = -1;
        initStyle(context, attrs);
        initViews(context);
        initImageMargin();
    }

    /* access modifiers changed from: protected */
    public void inflateLayout(Context context) {
        LayoutInflater.from(context).inflate(R.layout.au_abs_list_item, this.mListLayout, true);
        this.mLeftTextView = (AUTextView) this.mListLayout.findViewById(R.id.item_left_text);
        this.leftContainer = (AULinearLayout) this.mListLayout.findViewById(R.id.list_left_stub);
    }

    /* access modifiers changed from: protected */
    public void inflateImageLayout() {
        LayoutInflater.from(getContext()).inflate(R.layout.au_abs_list_image, this.imageContainer, true);
        this.mLeftImageView = (AURoundImageView) this.imageContainer.findViewById(R.id.list_item_icon);
    }

    private void initStyle(Context context, AttributeSet attrs) {
        initEmojiStyle(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.listItem);
        this.leftText = typedArray.getString(2);
        this.leftImage = typedArray.getDrawable(8);
        this.leftTextColor = typedArray.getColor(6, 0);
        this.leftTextSize = typedArray.getDimension(4, 0.0f);
        this.leftImageWidth = typedArray.getDimension(9, 0.0f);
        this.leftImageHeight = typedArray.getDimension(10, 0.0f);
        this.leftImageSizeType = typedArray.getInt(17, 48);
        this.isShowCheck = typedArray.getBoolean(16, false);
        this.arrowType = typedArray.getInt(12, 32);
        this.hasRound = typedArray.getBoolean(18, false);
        typedArray.recycle();
    }

    private void initEmojiStyle(Context context, AttributeSet attrs) {
        TypedArray emojiTA = context.obtainStyledAttributes(attrs, R.styleable.EmojiAttr);
        this.supportEmoji = emojiTA.getBoolean(0, false);
        this.emojiSize = emojiTA.getDimensionPixelSize(2, 0);
        emojiTA.recycle();
    }

    /* access modifiers changed from: protected */
    public void onFinishInflate() {
        super.onFinishInflate();
        intLeftTextNewFlagViewLayout();
    }

    private void initViews(Context context) {
        boolean z = false;
        this.mLeftImageView.setRounded(this.hasRound);
        if (this.leftTextColor != 0) {
            this.mLeftTextView.setTextColor(this.leftTextColor);
        }
        if (this.leftTextSize != 0.0f) {
            this.mLeftTextView.setTextSize(0, this.leftTextSize);
        }
        if (this.leftText != null) {
            setLeftText(this.leftText);
        }
        float defaultIconSize = (float) getLeftImageSize(context);
        if (this.leftImageWidth == 0.0f) {
            this.leftImageWidth = defaultIconSize;
        }
        if (this.leftImageHeight == 0.0f) {
            this.leftImageHeight = defaultIconSize;
        }
        setIconSize(this.leftImageWidth, this.leftImageHeight);
        if (this.leftImage != null) {
            setLeftImage(this.leftImage);
        }
        if (this.isShowArrow || this.isShowCheck) {
            z = true;
        }
        setArrowVisibility(z);
        if (this.isShowCheck) {
            this.mArrowView.setIconfontUnicode(getResources().getString(R.string.iconfont_selected));
            this.mArrowView.setIconfontColor(getResources().getColor(R.color.AU_COLOR_LINK));
        } else if (this.isShowArrow) {
            setArrowType(this.arrowType);
        }
    }

    public void initLeftSubText() {
        this.mLeftSubTextView = (AUEmptyGoneTextView) findViewById(R.id.item_left_sub_text);
    }

    public void addRightView(View view) {
        LayoutParams params = new LayoutParams(-2, -2);
        params.gravity = 16;
        params.leftMargin = getResources().getDimensionPixelSize(R.dimen.AU_SPACE17);
        this.mListLayout.addView(view, (ViewGroup.LayoutParams) params);
    }

    public void addRightViewAssit(View view) {
        LayoutParams params = new LayoutParams(-1, -2);
        params.gravity = 16;
        params.leftMargin = getResources().getDimensionPixelSize(R.dimen.AU_SPACE17);
        params.weight = 1.0f;
        LayoutParams leftParams = (LayoutParams) this.leftContainer.getLayoutParams();
        leftParams.width = -2;
        leftParams.weight = 0.0f;
        this.mLeftTextView.setMaxWidth(DensityUtil.dip2px(getContext(), 200.0f));
        this.mListLayout.addView(view, (ViewGroup.LayoutParams) params);
    }

    @Deprecated
    public void attachNewFlag2LeftText(View newFlagView) {
        this.leftTextNewFlagView = newFlagView;
        addView(newFlagView);
        this.fatherId = R.id.list_left_stub;
        intLeftTextNewFlagViewLayout();
    }

    @Deprecated
    public void setArrowImageVisibility(int visibility) {
        this.mArrowView.setVisibility(visibility);
        setArrowType(this.arrowType);
    }

    public void setArrowVisibility(boolean isVisible) {
        super.setArrowVisibility(isVisible);
        setArrowType(this.arrowType);
    }

    private void setArrowType(int arrowType2) {
        if (arrowType2 == 33) {
            this.mArrowView.setIconfontUnicode(getResources().getString(R.string.iconfont_pulldown));
        }
    }

    private void intLeftTextNewFlagViewLayout() {
        if (this.leftTextNewFlagView != null) {
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-2, -2);
            layoutParams.addRule(1, this.fatherId);
            layoutParams.addRule(15);
            this.leftTextNewFlagView.setLayoutParams(layoutParams);
        }
    }

    private void initImageMargin() {
        int verticalPadding = getImageVerticalMargin(getContext());
        this.mListLayout.setPadding(0, verticalPadding, 0, verticalPadding);
        this.imageContainer.setPadding(0, verticalPadding, 0, verticalPadding);
    }

    public void setIconSize(float width, float height) {
        LayoutParams layoutParams = (LayoutParams) this.mLeftImageView.getLayoutParams();
        if (width != 0.0f) {
            layoutParams.width = (int) width;
            this.leftImageWidth = width;
        }
        if (height != 0.0f) {
            layoutParams.height = (int) height;
            this.leftImageHeight = height;
        }
    }

    @Deprecated
    public void setType(int type) {
        setItemPositionStyle(type);
    }

    /* access modifiers changed from: protected */
    public float getLeftImageHeight() {
        return this.leftImageHeight;
    }

    public CharSequence getLeftText() {
        return this.mLeftTextView.getText().toString();
    }

    public void setLeftText(CharSequence text) {
        if (this.mLeftTextView.getVisibility() != 0) {
            this.mLeftTextView.setVisibility(0);
        }
        this.mLeftTextView.setText(text);
    }

    public void setLeftTextColor(int color) {
        this.mLeftTextView.setTextColor(color);
    }

    public AURoundImageView getRoundLeftImageView() {
        if (this.mLeftImageView.getVisibility() != 0) {
            this.mLeftImageView.setVisibility(0);
            setImageContainerVisibility(true);
        }
        return this.mLeftImageView;
    }

    @Deprecated
    public AUImageView getLeftImageView() {
        return getRoundLeftImageView();
    }

    public void setLeftImage(int resId) {
        if (this.mLeftImageView.getVisibility() != 0) {
            this.mLeftImageView.setVisibility(0);
            setImageContainerVisibility(true);
        }
        this.mLeftImageView.setImageResource(resId);
    }

    public void setLeftImageVisibility(int vis) {
        this.mLeftImageView.setVisibility(vis);
        setImageContainerVisibility(vis == 0);
    }

    public void setLeftImage(Drawable drawable) {
        if (this.mLeftImageView.getVisibility() != 0) {
            this.mLeftImageView.setVisibility(0);
            setImageContainerVisibility(true);
        }
        this.mLeftImageView.setImageDrawable(drawable);
    }

    public void setLeftCheckIconChecked(boolean isShowCheck2) {
        initCheckIcon();
        this.mLeftCheckIcon.setChecked(isShowCheck2);
    }

    public void setLeftCheckIconEnabled(boolean isEnabled) {
        initCheckIcon();
        this.mLeftCheckIcon.setEnabled(isEnabled);
    }

    public AUCheckIcon getLeftCheckIcon() {
        initCheckIcon();
        return this.mLeftCheckIcon;
    }

    public AUTextView getLeftTextView() {
        return this.mLeftTextView;
    }

    public void initCheckIcon() {
        if (this.mLeftCheckIcon == null) {
            this.mLeftCheckIcon = new AUCheckIcon(getContext(), 2, true);
            int size = getResources().getDimensionPixelSize(R.dimen.au_list_checkbox_size);
            LayoutParams layoutParams = new LayoutParams(size, size);
            layoutParams.leftMargin = getResources().getDimensionPixelOffset(R.dimen.AU_MARGIN_UNIVERSAL);
            this.mLeftCheckIcon.setLayoutParams(layoutParams);
            this.mLeftCheckIcon.setVisibility(0);
            setImageContainerVisibility(true);
            this.imageContainer.addView((View) this.mLeftCheckIcon, 0);
        }
    }
}
