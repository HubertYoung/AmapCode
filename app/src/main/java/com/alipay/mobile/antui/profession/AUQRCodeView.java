package com.alipay.mobile.antui.profession;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import com.alipay.mobile.antui.R;
import com.alipay.mobile.antui.basic.AUEmptyGoneTextView;
import com.alipay.mobile.antui.basic.AUImageView;
import com.alipay.mobile.antui.basic.AULinearLayout;
import com.alipay.mobile.antui.basic.AUTextView;
import com.alipay.mobile.antui.iconfont.AUIconDrawable;
import com.alipay.mobile.antui.iconfont.model.IconPaintBuilder;

public class AUQRCodeView extends AULinearLayout {
    private AUEmptyGoneTextView mAvatarDescription;
    private AUImageView mAvatarImage;
    private AUTextView mAvatarName;
    private AULinearLayout mButton;
    private AUEmptyGoneTextView mButtonContent;
    private AUTextView mButtonTitle;
    private AUEmptyGoneTextView mCodeDescription;
    private AUImageView mCodeImage;
    private AUTextView mCodeTitle;
    private View mSaveView;

    public AUQRCodeView(Context context) {
        super(context);
        init(context);
    }

    public AUQRCodeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public AUQRCodeView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.au_qr_code_view, this, true);
        setOrientation(1);
        setBackgroundResource(R.color.qr_background_color);
        this.mSaveView = findViewById(R.id.save_view);
        this.mAvatarImage = (AUImageView) findViewById(R.id.avatar_image);
        this.mAvatarName = (AUTextView) findViewById(R.id.avatar_name);
        this.mAvatarDescription = (AUEmptyGoneTextView) findViewById(R.id.avatar_description);
        this.mCodeImage = (AUImageView) findViewById(R.id.qr_code_image);
        this.mCodeTitle = (AUTextView) findViewById(R.id.qr_code_title);
        this.mCodeDescription = (AUEmptyGoneTextView) findViewById(R.id.qr_code_description);
        this.mButton = (AULinearLayout) findViewById(R.id.qr_code_button);
        this.mButtonTitle = (AUTextView) findViewById(R.id.qr_code_button_title);
        this.mButtonContent = (AUEmptyGoneTextView) findViewById(R.id.qr_code_button_content);
        this.mButtonTitle.setCompoundDrawablePadding(getResources().getDimensionPixelSize(R.dimen.AU_SPACE1));
    }

    public void setAvatarName(CharSequence name) {
        this.mAvatarName.setText(name);
    }

    public void setAvatarDescription(CharSequence description) {
        this.mAvatarDescription.setText(description);
    }

    public void setCodeInfo(CharSequence title, CharSequence description) {
        setCodeTitle(title);
        setCodeDescription(description);
    }

    public void setCodeTitle(CharSequence title) {
        this.mCodeTitle.setText(title);
    }

    public void setCodeDescription(CharSequence description) {
        this.mCodeDescription.setText(description);
    }

    public void setButtonInfo(CharSequence title, CharSequence content) {
        setButtonInfo(title, content, true);
    }

    public void setButtonInfo(CharSequence title, CharSequence content, boolean isToken) {
        setButtonVisibility(true);
        setButtonToken(isToken);
        setButtonTitle(title);
        setButtonContent(content);
    }

    public void setButtonTitle(CharSequence title) {
        setButtonVisibility(true);
        this.mButtonTitle.setText(title);
    }

    public void setButtonToken(boolean isToken) {
        if (isToken) {
            int size = getResources().getDimensionPixelSize(R.dimen.qr_code_bg_padding);
            AUIconDrawable drawable = new AUIconDrawable(getContext(), new IconPaintBuilder(-1, size, R.string.iconfont_systen_key));
            drawable.setBounds(0, 0, size, size);
            this.mButtonTitle.setCompoundDrawables(drawable, null, null, null);
            return;
        }
        this.mButtonTitle.setCompoundDrawables(null, null, null, null);
    }

    public void setCodeImageSize(int size) {
        LayoutParams params = this.mCodeImage.getLayoutParams();
        params.width = size;
        params.height = size;
        this.mCodeImage.setLayoutParams(params);
    }

    public void setButtonVisibility(boolean isVisible) {
        this.mButton.setVisibility(isVisible ? 0 : 8);
    }

    public void setButtonContent(CharSequence content) {
        this.mButtonContent.setText(content);
        if (this.mButtonTitle.getVisibility() != 0 || !TextUtils.isEmpty(content)) {
            this.mButtonTitle.setTextSize(1, 16.0f);
        } else {
            this.mButtonTitle.setTextSize(1, 18.0f);
        }
    }

    public AUImageView getAvatarImage() {
        return this.mAvatarImage;
    }

    public AUTextView getAvatarName() {
        return this.mAvatarName;
    }

    public AUImageView getCodeImage() {
        return this.mCodeImage;
    }

    public AUTextView getCodeTitle() {
        return this.mCodeTitle;
    }

    public AUEmptyGoneTextView getCodeDescription() {
        return this.mCodeDescription;
    }

    public AULinearLayout getButton() {
        return this.mButton;
    }

    public AUTextView getButtonTitle() {
        return this.mButtonTitle;
    }

    public AUEmptyGoneTextView getButtonContent() {
        return this.mButtonContent;
    }

    public View getSaveView() {
        return this.mSaveView;
    }

    public Bitmap getSaveViewBitmap() {
        this.mSaveView.setDrawingCacheEnabled(true);
        this.mSaveView.setDrawingCacheBackgroundColor(getResources().getColor(R.color.qr_background_color));
        this.mSaveView.buildDrawingCache();
        return this.mSaveView.getDrawingCache();
    }
}
