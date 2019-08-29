package com.alipay.mobile.antui.input;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputFilter.LengthFilter;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView.OnEditorActionListener;
import com.alipay.mobile.antui.R;
import com.alipay.mobile.antui.api.AULineGroupItemInterface;
import com.alipay.mobile.antui.basic.AUEditText;
import com.alipay.mobile.antui.basic.AUImageView;
import com.alipay.mobile.antui.basic.AULinearLayout;
import com.alipay.mobile.antui.basic.AURelativeLayout;
import com.alipay.mobile.antui.basic.AUTextView;
import com.alipay.mobile.antui.iconfont.AUIconView;
import com.alipay.mobile.antui.utils.DensityUtil;

public class AUInputBox extends AURelativeLayout implements OnFocusChangeListener, AULineGroupItemInterface {
    private View bottomDivider;
    private int emojiSize;
    private int inputType;
    private boolean isNeedShowClearButton;
    /* access modifiers changed from: private */
    public OnClickListener mCleanButtonListener;
    /* access modifiers changed from: private */
    public final AUIconView mClearButton;
    private OnFocusChangeListener mFocusChangeListener;
    protected AULinearLayout mInputContainer;
    /* access modifiers changed from: private */
    public final AUEditText mInputEditText;
    private final AUImageView mInputImage;
    private final AUTextView mInputName;
    /* access modifiers changed from: private */
    public AUFormatter mTextFormatter;
    private boolean supportEmoji;
    private View topDivider;

    public AUInputBox(Context context) {
        this(context, null);
    }

    public AUInputBox(Context context, AttributeSet set) {
        super(context, set);
        this.supportEmoji = false;
        this.emojiSize = 0;
        this.isNeedShowClearButton = true;
        this.mTextFormatter = null;
        this.inputType = 1;
        inflateLayout(context);
        this.mInputContainer = (AULinearLayout) findViewById(R.id.inputbox_container);
        this.mInputEditText = (AUEditText) findViewById(R.id.input_edit);
        this.mInputName = (AUTextView) findViewById(R.id.input_name);
        this.mClearButton = (AUIconView) findViewById(R.id.clearButton);
        this.mInputImage = (AUImageView) findViewById(R.id.input_image);
        String hintString = null;
        String inputName = null;
        int maxLength = -1;
        int itemType = 16;
        Drawable inputImage = null;
        if (set != null) {
            TypedArray a = context.obtainStyledAttributes(set, R.styleable.AUInputBox);
            inputName = a.getString(1);
            this.inputType = a.getInt(5, 1);
            maxLength = a.getInt(4, -1);
            hintString = a.getString(0);
            inputImage = a.getDrawable(8);
            a.recycle();
            TypedArray emojia = context.obtainStyledAttributes(set, R.styleable.EmojiAttr);
            this.supportEmoji = emojia.getBoolean(0, false);
            this.emojiSize = emojia.getDimensionPixelSize(2, 0);
            emojia.recycle();
            TypedArray backgounda = context.obtainStyledAttributes(set, R.styleable.listItem);
            itemType = backgounda.getInt(0, 16);
            backgounda.recycle();
        }
        setItemPositionStyle(itemType);
        this.mInputEditText.setSupportEmoji(this.supportEmoji);
        this.mInputEditText.setEmojiSize(this.emojiSize);
        setInputName(inputName);
        setInputType(this.inputType);
        setMaxLength(maxLength);
        setHint(hintString);
        setInputImage(inputImage);
        afterInflate();
    }

    public String getUbbStr() {
        return this.mInputEditText.getUbbStr();
    }

    public void setEmojiSize(int emojiSize2) {
        this.emojiSize = emojiSize2;
    }

    public void setSupportEmoji(boolean isSupport) {
        this.supportEmoji = isSupport;
    }

    /* access modifiers changed from: protected */
    public void inflateLayout(Context context) {
        LayoutInflater.from(context).inflate(R.layout.au_inputbox, this, true);
    }

    private void afterInflate() {
        addTextChangedListener(new a(this));
        addClearListener();
        this.mInputEditText.setOnFocusChangeListener(this);
        setOnTouchListener(new b(this));
    }

    public void setTextFormatter(AUFormatter formatter) {
        this.mTextFormatter = formatter;
    }

    private void addClearListener() {
        this.mClearButton.setOnClickListener(new c(this));
    }

    public void setApprerance(boolean isBold) {
        Typeface tf = this.mInputEditText.getTypeface();
        if (isBold) {
            this.mInputEditText.setTypeface(tf, 1);
        } else {
            this.mInputEditText.setTypeface(tf, 0);
        }
    }

    public void setOnEditorActionListener(OnEditorActionListener l) {
        if (this.mInputEditText != null) {
            this.mInputEditText.setOnEditorActionListener(l);
        }
    }

    public void addTextChangedListener(TextWatcher watcher) {
        if (this.mInputEditText != null) {
            this.mInputEditText.addTextChangedListener(watcher);
        }
    }

    public void setOnFocusChangeListener(OnFocusChangeListener l) {
        if (this.mInputEditText != null) {
            this.mFocusChangeListener = l;
        }
    }

    public void setCleanButtonListener(OnClickListener listener) {
        this.mCleanButtonListener = listener;
    }

    public void setText(CharSequence inputContent) {
        this.mInputEditText.setText(inputContent);
        Editable text = this.mInputEditText.getText();
        if (text instanceof Spannable) {
            Selection.setSelection(text, text.length());
        }
    }

    public String getInputedText() {
        return this.mInputEditText.getText().toString();
    }

    public AUEditText getInputEdit() {
        return this.mInputEditText;
    }

    public void setInputName(String title) {
        if (!TextUtils.isEmpty(title)) {
            this.mInputName.setText(title);
            this.mInputName.setVisibility(0);
            return;
        }
        this.mInputName.setVisibility(8);
    }

    public AUTextView getInputName() {
        return this.mInputName;
    }

    public void setInputNameTextSize(float textSize) {
        if (textSize > 0.0f) {
            this.mInputName.setTextSize(0, textSize);
        }
    }

    public void setInputTextSize(float textSize) {
        if (textSize > 0.0f) {
            this.mInputEditText.setTextSize(0, textSize);
        }
    }

    public void setInputTextColor(int textColor) {
        this.mInputEditText.setTextColor(textColor);
    }

    public void setInputType(int inputType2) {
        this.mInputEditText.setInputType(inputType2);
    }

    public void setHint(String hintString) {
        if (!TextUtils.isEmpty(hintString)) {
            this.mInputEditText.setHint(hintString);
        }
    }

    public void setInputImage(Drawable drawable) {
        if (drawable != null) {
            this.mInputImage.setVisibility(0);
            this.mInputImage.setImageDrawable(drawable);
        }
    }

    public AUImageView getInputImage() {
        return this.mInputImage;
    }

    public void setHintTextColor(int textColor) {
        this.mInputEditText.setHintTextColor(textColor);
    }

    public void setMaxLength(int maxlength) {
        if (maxlength >= 0) {
            this.mInputEditText.setFilters(new InputFilter[]{new LengthFilter(maxlength)});
            return;
        }
        this.mInputEditText.setFilters(new InputFilter[0]);
    }

    /* access modifiers changed from: protected */
    public void onInputTextStatusChanged(boolean isEmpty, boolean isFocused) {
        if (isEmpty || !isFocused) {
            setClearButtonVisiable(false);
        } else {
            setClearButtonVisiable(true);
        }
    }

    /* access modifiers changed from: protected */
    public void setClearButtonVisiable(boolean visiable) {
        if (!visiable || !this.isNeedShowClearButton) {
            this.mClearButton.setVisibility(8);
        } else {
            this.mClearButton.setVisibility(0);
        }
    }

    public AUIconView getClearButton() {
        return this.mClearButton;
    }

    public boolean isNeedShowClearButton() {
        return this.isNeedShowClearButton;
    }

    public void setNeedShowClearButton(boolean isNeedShowClearButton2) {
        this.isNeedShowClearButton = isNeedShowClearButton2;
    }

    public void onFocusChange(View v, boolean hasFocus) {
        onInputTextStatusChanged(this.mInputEditText.getText().length() == 0, hasFocus);
        if (hasFocus) {
            if (this.mInputEditText.getText() != null) {
                this.mInputEditText.setSelection(this.mInputEditText.getText().length());
            } else {
                this.mInputEditText.setSelection(0);
            }
        }
        if (this.mFocusChangeListener != null) {
            this.mFocusChangeListener.onFocusChange(v, hasFocus);
        }
    }

    public void setItemPositionStyle(int positionStyle) {
        switch (positionStyle) {
            case 16:
                addTopDivider(false);
                addBottomDivider(false);
                return;
            case 17:
                addTopDivider(false);
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

    private void addTopDivider(boolean hasLeftMargin) {
        int i = 0;
        if (this.topDivider == null) {
            this.topDivider = new View(getContext());
            this.topDivider.setBackgroundColor(getResources().getColor(R.color.AU_COLOR_LINE));
            addView(this.topDivider);
        }
        this.topDivider.setVisibility(0);
        if (hasLeftMargin) {
            i = 14;
        }
        this.topDivider.setLayoutParams(getTopParams(i));
    }

    private void addBottomDivider(boolean hasLeftMargin) {
        int i = 0;
        if (this.bottomDivider == null) {
            this.bottomDivider = new View(getContext());
            this.bottomDivider.setBackgroundColor(getResources().getColor(R.color.AU_COLOR_LINE));
            addView(this.bottomDivider);
        }
        this.bottomDivider.setVisibility(0);
        if (hasLeftMargin) {
            i = 14;
        }
        this.bottomDivider.setLayoutParams(getBottomParams(i));
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

    private LayoutParams getBottomParams(int marginLeft) {
        LayoutParams layoutParams = new LayoutParams(-1, 1);
        layoutParams.addRule(8, R.id.inputbox_container);
        layoutParams.leftMargin = DensityUtil.dip2px(getContext(), (float) marginLeft);
        return layoutParams;
    }

    private LayoutParams getTopParams(int marginLeft) {
        LayoutParams layoutParams = new LayoutParams(-1, 1);
        layoutParams.addRule(6, R.id.inputbox_container);
        layoutParams.leftMargin = DensityUtil.dip2px(getContext(), (float) marginLeft);
        return layoutParams;
    }

    public void setVisualStyle(int visualStyle) {
    }

    public int getInputType() {
        return this.inputType;
    }

    public void showSoftKeyboard() {
        ((InputMethodManager) getContext().getSystemService("input_method")).showSoftInput(this.mInputEditText, 2);
    }
}
