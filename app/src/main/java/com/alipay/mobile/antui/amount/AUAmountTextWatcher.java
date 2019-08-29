package com.alipay.mobile.antui.amount;

import android.content.Context;
import android.graphics.Typeface;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;
import com.alipay.mobile.antui.constant.AUConstant;
import com.alipay.mobile.antui.iconfont.constants.IconfontConstants;
import com.alipay.mobile.antui.iconfont.manager.TypefaceCache;
import java.io.File;

public class AUAmountTextWatcher implements TextWatcher {
    private boolean isShowClear = true;
    private View mClearIcon;
    private String mResBundle = AUConstant.RES_BUNDLE;
    private String mTTFFilePath = AUConstant.AMOUNT_NUM_FILE_NAME;
    private TextView mTextView;
    private Typeface mTypeface;

    public AUAmountTextWatcher(Context context, TextView textView, View clearIcon) {
        this.mTextView = textView;
        this.mClearIcon = clearIcon;
        this.mTypeface = TypefaceCache.getTypeface(context, this.mResBundle, getTTFFilePath());
    }

    private String getTTFFilePath() {
        return this.mResBundle + File.separator + this.mTTFFilePath + IconfontConstants.ICONFONT_FILE_SUFFIX;
    }

    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (s == null || s.length() == 0) {
            changeTextSizeChange(0);
            this.mTextView.setTypeface(null);
        } else {
            this.mTextView.setTypeface(this.mTypeface);
            changeTextSizeChange(s.length());
        }
        onInputTextStatusChanged(TextUtils.isEmpty(s), this.mTextView.isFocused());
    }

    public void afterTextChanged(Editable s) {
        String editString = s.toString();
        int posDot = editString.indexOf(".");
        if (posDot > 0 && (editString.length() - posDot) - 1 > 2) {
            s.delete(posDot + 3, posDot + 4);
        }
    }

    private void changeTextSizeChange(int length) {
        if (length != 0) {
            if (length >= 0 && length <= 8) {
                this.mTextView.setTextSize(1, 55.0f);
                return;
            } else if (length > 8 && length <= 10) {
                this.mTextView.setTextSize(1, 45.0f);
                return;
            }
        }
        this.mTextView.setTextSize(1, 35.0f);
    }

    public void setShowClear(boolean showClear) {
        this.isShowClear = showClear;
    }

    public void onInputTextStatusChanged(boolean isEmpty, boolean isFocused) {
        if (this.mClearIcon != null) {
            if (isEmpty || !isFocused || !this.isShowClear) {
                this.mClearIcon.setVisibility(8);
            } else {
                this.mClearIcon.setVisibility(0);
            }
        }
    }
}
