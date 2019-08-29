package com.alipay.mobile.antui.tablelist;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import com.alipay.mobile.antui.R;
import com.alipay.mobile.antui.basic.AUProcessButton;
import com.alipay.mobile.antui.basic.AURoundImageView;
import com.alipay.mobile.antui.basic.AUTextView;

public class AUDoubleTitleListItem extends AUAbsListItem {
    private String leftSubText;
    private int leftSubTextColor;
    private float leftSubTextSize;
    private AURoundImageView mRightImageView;
    protected AUTextView mRightTextView;
    private AUProcessButton processButton;
    private String rightText;
    private int type;

    public AUDoubleTitleListItem(Context context) {
        this(context, null);
    }

    public AUDoubleTitleListItem(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AUDoubleTitleListItem(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initSelfDefAttrs(context, attrs);
        setLeftViewParams();
        setRightViewParams();
    }

    /* access modifiers changed from: protected */
    public int getImageVerticalMargin(Context context) {
        switch (this.leftImageSizeType) {
            case 48:
                return context.getResources().getDimensionPixelOffset(R.dimen.au_list_image_space_2);
            case 49:
                return context.getResources().getDimensionPixelOffset(R.dimen.au_list_image_space_3);
            default:
                return context.getResources().getDimensionPixelOffset(R.dimen.au_list_image_space_2);
        }
    }

    /* access modifiers changed from: protected */
    public int getLeftImageSize(Context context) {
        switch (this.leftImageSizeType) {
            case 48:
                return context.getResources().getDimensionPixelOffset(R.dimen.au_double_image_size_2);
            case 49:
                return context.getResources().getDimensionPixelOffset(R.dimen.au_double_image_size_3);
            default:
                return context.getResources().getDimensionPixelOffset(R.dimen.au_double_image_size_2);
        }
    }

    private void initSelfDefAttrs(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.listItem);
        this.leftSubText = ta.getString(3);
        this.leftSubTextColor = ta.getColor(7, 0);
        this.leftSubTextSize = ta.getDimension(5, 0.0f);
        this.rightText = ta.getString(13);
        this.type = ta.getInt(14, 16);
        ta.recycle();
    }

    private void setLeftViewParams() {
        if (!TextUtils.isEmpty(this.leftSubText)) {
            setLeftSubText(this.leftSubText);
        }
        if (this.leftSubTextColor != 0) {
            this.mLeftSubTextView.setTextColor(this.leftSubTextColor);
        }
        if (this.leftSubTextSize != 0.0f) {
            this.mLeftSubTextView.setTextSize(0, this.leftSubTextSize);
        }
    }

    private void setRightViewParams() {
        if (!TextUtils.isEmpty(this.rightText)) {
            switch (this.type) {
                case 17:
                    setRightButtonText(this.rightText);
                    return;
                default:
                    setRightText(this.rightText);
                    return;
            }
        }
    }

    private void initRightView(int type2) {
        switch (type2) {
            case 17:
                initButtonView();
                return;
            default:
                initTextImage();
                return;
        }
    }

    /* access modifiers changed from: protected */
    public void initTextImage() {
        View rightView = LayoutInflater.from(getContext()).inflate(R.layout.au_single_title_list_item, null);
        this.mRightTextView = (AUTextView) rightView.findViewById(R.id.list_right_text);
        this.mRightImageView = (AURoundImageView) rightView.findViewById(R.id.list_right_image);
        this.mRightTextView.setSupportEmoji(this.supportEmoji);
        this.mRightTextView.setEmojiSize(this.emojiSize);
        addRightView(rightView);
    }

    private void initButtonView() {
        this.processButton = new AUProcessButton(getContext());
        this.processButton.setTextSize(1, 13.0f);
        this.processButton.setTextColor(getResources().getColorStateList(R.color.au_button_textcolor_blue));
        this.processButton.setBackgroundResource(R.drawable.au_button_bg_for_ass);
        int h_padding = getResources().getDimensionPixelSize(R.dimen.AU_HEIGHT1);
        int v_padding = getResources().getDimensionPixelSize(R.dimen.AU_SPACE1);
        this.processButton.setPadding(h_padding, v_padding, h_padding, v_padding);
        setArrowVisibility(false);
        addRightView(this.processButton);
    }

    public void setLeftSubText(CharSequence text) {
        if (this.mLeftSubTextView == null) {
            initLeftSubText();
        }
        if (this.mLeftSubTextView.getVisibility() != 0) {
            this.mLeftSubTextView.setVisibility(0);
        }
        this.mLeftSubTextView.setText(text);
    }

    public void setRightText(CharSequence text) {
        if (this.mRightTextView == null) {
            initRightView(16);
        }
        this.mRightTextView.setText(text);
    }

    public void setRightImage(int resId) {
        if (this.mRightTextView == null) {
            initRightView(16);
        }
        if (this.mRightImageView.getVisibility() != 0) {
            this.mRightImageView.setVisibility(0);
        }
        this.mRightImageView.setImageResource(resId);
    }

    public void setRightImage(Drawable drawable) {
        if (this.mRightTextView == null) {
            initRightView(16);
        }
        if (this.mRightImageView.getVisibility() != 0) {
            this.mRightImageView.setVisibility(0);
        }
        this.mRightImageView.setImageDrawable(drawable);
    }

    public AURoundImageView getRightImage() {
        if (this.mRightImageView == null) {
            initRightView(16);
        }
        if (this.mRightImageView.getVisibility() != 0) {
            this.mRightImageView.setVisibility(0);
        }
        return this.mRightImageView;
    }

    public void setRightButtonText(CharSequence text) {
        if (this.processButton == null) {
            initRightView(17);
        }
        this.processButton.setText(text);
    }

    public AUTextView getRightTextView() {
        if (this.mRightTextView == null) {
            initRightView(16);
        }
        return this.mRightTextView;
    }

    public AUTextView getLeftSubTextView() {
        if (this.mLeftSubTextView == null) {
            initLeftSubText();
        }
        return this.mLeftSubTextView;
    }

    public AUProcessButton getProcessButton() {
        if (this.processButton == null) {
            initRightView(17);
        }
        return this.processButton;
    }

    public void setButtonClickListener(OnClickListener listener) {
        if (this.processButton == null) {
            initRightView(17);
        }
        this.processButton.setOnClickListener(listener);
    }

    public void setRightType(int type2) {
        initRightView(type2);
    }
}
