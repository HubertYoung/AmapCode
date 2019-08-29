package com.autonavi.miniapp.plugin.search;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputFilter.LengthFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import com.alipay.mobile.antui.basic.AUEditText;
import com.alipay.mobile.antui.basic.AUImageButton;
import com.alipay.mobile.antui.basic.AUImageView;
import com.alipay.mobile.antui.basic.AURelativeLayout;
import com.alipay.mobile.antui.basic.AUTextView;
import com.autonavi.minimap.R;

public class AUSocialSearchBar extends AURelativeLayout {
    private AUImageButton mBackButton;
    private int mBackIcon = 0;
    private boolean mHasInput = false;
    private AUImageView mHintIconView;
    private String mInputHint = "";
    private int mInputMaxLength = 20;
    private String mInputText = "";
    private View mNormalClearButton;
    private AURelativeLayout mNormalRelativeLayout;
    private AUTextView mNormalSearchButton;
    /* access modifiers changed from: private */
    public AUEditText mNormalSearchInput;
    private String mSearchButtonText = "";
    private int mSearchHintIcon = 0;
    private boolean mShowSearchButton = false;
    private boolean mShowVoiceSearch = false;
    private View mVoiceButton;

    public AUSocialSearchBar(Context context) {
        super(context);
    }

    public AUSocialSearchBar(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public AUSocialSearchBar(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        LayoutInflater.from(context).inflate(R.layout.ap_social_search_bar, this, true);
        this.mBackButton = (AUImageButton) findViewById(R.id.social_search_back_button);
        this.mNormalSearchInput = (AUEditText) findViewById(R.id.social_search_normal_input);
        this.mNormalClearButton = findViewById(R.id.social_btn_normal_clear);
        this.mVoiceButton = findViewById(R.id.social_search_voice_btn);
        this.mHintIconView = (AUImageView) findViewById(R.id.social_search_normal_left_icon);
        this.mNormalSearchButton = (AUTextView) findViewById(R.id.social_btn_normal_do_search);
        this.mNormalRelativeLayout = (AURelativeLayout) findViewById(R.id.social_search_normal_layout);
    }

    /* access modifiers changed from: protected */
    public void onFinishInflate() {
        super.onFinishInflate();
        initBackButton();
        setInputMaxLength();
        setNormalTextChanged();
        setNormalClearBtnClick();
        initContent();
    }

    private void initContent() {
        if (!TextUtils.isEmpty(this.mInputText)) {
            this.mNormalSearchInput.setText(this.mInputText);
        }
        if (!TextUtils.isEmpty(this.mInputHint)) {
            this.mNormalSearchInput.setHint(this.mInputHint);
        }
        if (!TextUtils.isEmpty(this.mSearchButtonText)) {
            this.mNormalSearchButton.setText(this.mSearchButtonText);
        }
        if (this.mSearchHintIcon != 0) {
            this.mHintIconView.setImageDrawable(getResources().getDrawable(this.mSearchHintIcon));
        }
        if (this.mBackIcon != 0) {
            this.mBackButton.setImageDrawable(getResources().getDrawable(this.mBackIcon));
        }
        if (this.mShowSearchButton) {
            this.mNormalSearchButton.setVisibility(0);
        } else {
            this.mNormalSearchButton.setVisibility(8);
        }
        if (!this.mShowVoiceSearch || !TextUtils.isEmpty(this.mInputText)) {
            this.mVoiceButton.setVisibility(8);
        } else {
            this.mVoiceButton.setVisibility(0);
        }
    }

    /* access modifiers changed from: protected */
    public void initBackButton() {
        this.mBackButton.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                try {
                    Context context = AUSocialSearchBar.this.getContext();
                    if (context != null) {
                        ((InputMethodManager) context.getSystemService("input_method")).hideSoftInputFromWindow(view.getWindowToken(), 0);
                        if (context instanceof Activity) {
                            ((Activity) context).onBackPressed();
                        }
                    }
                } catch (Exception unused) {
                }
            }
        });
    }

    public void setInputMaxLength() {
        if (this.mInputMaxLength >= 0) {
            this.mNormalSearchInput.setFilters(new InputFilter[]{new LengthFilter(this.mInputMaxLength)});
            return;
        }
        this.mNormalSearchInput.setFilters(new InputFilter[0]);
    }

    /* access modifiers changed from: protected */
    public void setNormalClearBtnClick() {
        this.mNormalClearButton.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                AUSocialSearchBar.this.mNormalSearchInput.setText("");
                ((InputMethodManager) AUSocialSearchBar.this.mNormalSearchInput.getContext().getSystemService("input_method")).showSoftInput(AUSocialSearchBar.this.mNormalSearchInput, 1);
            }
        });
    }

    /* access modifiers changed from: protected */
    public void setNormalTextChanged() {
        this.mNormalSearchInput.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable editable) {
            }

            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                if (charSequence == null || charSequence.length() == 0) {
                    AUSocialSearchBar.this.setButtonUnActivity();
                } else {
                    AUSocialSearchBar.this.setButtonActivity();
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public void setButtonUnActivity() {
        if (this.mHasInput) {
            this.mNormalClearButton.setVisibility(8);
            this.mNormalSearchButton.setEnabled(false);
            if (this.mShowVoiceSearch) {
                this.mVoiceButton.setVisibility(0);
            }
            this.mHasInput = false;
        }
    }

    /* access modifiers changed from: private */
    public void setButtonActivity() {
        if (!this.mHasInput) {
            this.mNormalClearButton.setVisibility(0);
            this.mNormalSearchButton.setEnabled(true);
            this.mVoiceButton.setVisibility(8);
            this.mHasInput = true;
        }
    }

    public AUImageButton getBackButton() {
        return this.mBackButton;
    }

    public View getClearButton() {
        return this.mNormalClearButton;
    }

    public AUEditText getSearchInputEdit() {
        return this.mNormalSearchInput;
    }

    public AUTextView getSearchButton() {
        return this.mNormalSearchButton;
    }

    public AURelativeLayout getSearchRelativeLayout() {
        return this.mNormalRelativeLayout;
    }

    public View getVoiceButton() {
        return this.mVoiceButton;
    }
}
