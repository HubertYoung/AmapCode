package com.alipay.mobile.antui.tablelist;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import com.alipay.mobile.antui.R;
import com.alipay.mobile.antui.basic.AUImageView;
import com.alipay.mobile.antui.basic.AUProcessButton;
import com.alipay.mobile.antui.basic.AURoundImageView;
import com.alipay.mobile.antui.basic.AUTextView;
import com.alipay.mobile.antui.utils.DensityUtil;

public class AUSingleTitleListItem extends AUAbsListItem {
    private boolean hasNormalSize;
    private int leftImageHeight;
    private int leftImageWidth;
    private float leftTextSize;
    private AURoundImageView mRightImageView;
    private AUTextView mRightTextView;
    private AUProcessButton processButton;
    private Drawable rightImage;
    private int rightImageHeight;
    private int rightImageWidth;
    private String rightText;
    private float rightTextSize;
    private View rightView;
    private float scaleRate;

    public AUSingleTitleListItem(Context context) {
        this(context, null);
    }

    public AUSingleTitleListItem(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AUSingleTitleListItem(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.hasNormalSize = false;
        this.scaleRate = 1.0f;
        initSelfDefAttrs(context, attrs);
    }

    /* access modifiers changed from: protected */
    public int getImageVerticalMargin(Context context) {
        return context.getResources().getDimensionPixelOffset(R.dimen.AU_SPACE2);
    }

    /* access modifiers changed from: protected */
    public int getLeftImageSize(Context context) {
        return context.getResources().getDimensionPixelOffset(R.dimen.au_single_image_size);
    }

    private void initSelfDefAttrs(Context context, AttributeSet attrs) {
        int type = 16;
        if (attrs != null) {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.listItem);
            this.rightText = ta.getString(13);
            this.rightImage = ta.getDrawable(15);
            type = ta.getInt(14, 16);
            ta.recycle();
        }
        if (!TextUtils.isEmpty(this.rightText)) {
            switch (type) {
                case 17:
                    setRightButtonText(this.rightText);
                    break;
                default:
                    setRightText(this.rightText);
                    break;
            }
        }
        if (this.rightImage != null) {
            setRightImage(this.rightImage);
        }
    }

    private void initRightView(int type) {
        switch (type) {
            case 17:
                initButtonView();
                return;
            default:
                initTextImage(getContext());
                return;
        }
    }

    private void initTextImage(Context context) {
        this.rightView = LayoutInflater.from(context).inflate(R.layout.au_single_title_list_item, null);
        this.mRightTextView = (AUTextView) this.rightView.findViewById(R.id.list_right_text);
        this.mRightImageView = (AURoundImageView) this.rightView.findViewById(R.id.list_right_image);
        int corner = DensityUtil.dip2px(context, 1.0f);
        this.mRightImageView.setRoundSize(corner, corner);
        this.mRightTextView.setSupportEmoji(this.supportEmoji);
        this.mRightTextView.setEmojiSize(this.emojiSize);
        initRightControlSize();
        addRightViewAssit(this.rightView);
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

    public void setItemChecked(boolean checked) {
        if (!this.isShowCheck || !checked) {
            this.mArrowView.setVisibility(4);
            return;
        }
        this.mArrowView.setVisibility(0);
        this.mArrowView.setIconfontUnicode(getResources().getString(R.string.iconfont_selected));
        this.mArrowView.setIconfontColor(getResources().getColor(R.color.AU_COLOR_LINK));
    }

    public void setRightText(CharSequence text) {
        if (this.mRightTextView == null) {
            initRightView(16);
        }
        this.mRightTextView.setText(text);
        if (this.mRightTextView.getVisibility() != 0) {
            this.mRightTextView.setVisibility(0);
        }
    }

    public void setRightButtonText(CharSequence text) {
        if (this.processButton == null) {
            initRightView(17);
        }
        this.processButton.setText(text);
    }

    public void setRightImage(int resId) {
        if (this.mRightImageView == null) {
            initRightView(16);
        }
        if (this.mRightImageView.getVisibility() != 0) {
            this.mRightImageView.setVisibility(0);
        }
        this.mRightImageView.setImageResource(resId);
    }

    public void setRightImage(Bitmap bitmap) {
        if (this.mRightImageView == null) {
            initRightView(16);
        }
        if (this.mRightImageView.getVisibility() != 0) {
            this.mRightImageView.setVisibility(0);
        }
        this.mRightImageView.setImageBitmap(bitmap);
    }

    public void setRightImage(Drawable drawable) {
        if (this.mRightImageView == null) {
            initRightView(16);
        }
        if (this.mRightImageView.getVisibility() != 0) {
            this.mRightImageView.setVisibility(0);
        }
        this.mRightImageView.setImageDrawable(drawable);
        invalidate();
    }

    public AUTextView getRightTextView() {
        if (this.mRightTextView == null) {
            initRightView(16);
        }
        return this.mRightTextView;
    }

    public AUImageView getRightImageView() {
        if (this.mRightImageView == null) {
            initRightView(16);
        }
        return this.mRightImageView;
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

    public void setRightType(int type) {
        initRightView(type);
    }

    public void setScaleRate(float scaleRate2) {
        if (this.scaleRate != scaleRate2) {
            this.scaleRate = scaleRate2;
            if (!this.hasNormalSize) {
                this.leftImageHeight = this.mLeftImageView.getLayoutParams().height;
                this.leftImageWidth = this.mLeftImageView.getLayoutParams().width;
                this.leftTextSize = this.mLeftTextView.getTextSize();
                this.hasNormalSize = true;
            }
            if (this.leftImageHeight > 0) {
                this.mLeftImageView.getLayoutParams().height = (int) (((float) this.leftImageHeight) * scaleRate2);
            }
            if (this.leftImageWidth > 0) {
                this.mLeftImageView.getLayoutParams().width = (int) (((float) this.leftImageHeight) * scaleRate2);
            }
            if (this.leftImageHeight > 0 || this.leftImageWidth > 0) {
                this.mLeftImageView.requestLayout();
            }
            this.mLeftTextView.setTextSize(0, this.leftTextSize * scaleRate2);
            setRightControlSize(scaleRate2);
        }
    }

    private void setRightControlSize(float scaleRate2) {
        if (this.scaleRate != scaleRate2) {
            if (this.mRightTextView != null) {
                this.mRightTextView.setTextSize(0, this.rightTextSize * scaleRate2);
            }
            if (this.mRightImageView != null) {
                if (this.rightImageHeight > 0) {
                    this.mRightImageView.getLayoutParams().height = (int) (((float) this.rightImageHeight) * scaleRate2);
                }
                if (this.rightImageWidth > 0) {
                    this.mRightImageView.getLayoutParams().width = (int) (((float) this.rightImageWidth) * scaleRate2);
                }
                if (this.rightImageHeight > 0 || this.rightImageWidth > 0) {
                    this.mRightImageView.requestLayout();
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public void initRightControlSize() {
        this.rightImageHeight = this.mRightImageView.getLayoutParams().height;
        this.rightImageWidth = this.mRightImageView.getLayoutParams().width;
        this.rightTextSize = this.mRightTextView.getTextSize();
        if (this.scaleRate > 0.0f) {
            setRightControlSize(this.scaleRate);
        }
    }
}
