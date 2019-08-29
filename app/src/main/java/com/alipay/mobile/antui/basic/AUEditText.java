package com.alipay.mobile.antui.basic;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Spannable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView.BufferType;
import com.alipay.mobile.antui.R;
import com.alipay.mobile.antui.api.AntUIHelper;
import com.alipay.mobile.antui.utils.APEmojiRender;
import com.alipay.mobile.antui.utils.DensityUtil;
import com.alipay.mobile.antui.utils.EmojiUtil;

public class AUEditText extends EditText implements AUViewInterface {
    private float defalutTextSize;
    private boolean dynamicTextSize;
    private int emojiMaxRenderLength;
    private int emojiSize;
    private Boolean isAP;
    public OnPasteListener listener;
    private boolean supportEmoji;

    public interface OnPasteListener {
        void onPaste();
    }

    public Boolean isAP() {
        return this.isAP;
    }

    public void setAP(Boolean isAP2) {
        this.isAP = isAP2;
    }

    public void setPasteListener(OnPasteListener listener2) {
        this.listener = listener2;
    }

    public AUEditText(Context context) {
        super(context);
        this.supportEmoji = false;
        this.emojiSize = 0;
        this.dynamicTextSize = false;
        this.emojiMaxRenderLength = -1;
        this.defalutTextSize = getTextSize();
        setScaleSize();
    }

    public AUEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.supportEmoji = false;
        this.emojiSize = 0;
        this.dynamicTextSize = false;
        this.emojiMaxRenderLength = -1;
        initSelfDefAttrs(context, attrs);
    }

    public AUEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.supportEmoji = false;
        this.emojiSize = 0;
        this.dynamicTextSize = false;
        this.emojiMaxRenderLength = -1;
        initSelfDefAttrs(context, attrs);
    }

    public void setTextSize(int unit, float size) {
        super.setTextSize(unit, size);
        this.defalutTextSize = getTextSize();
        setScaleSize();
    }

    private void initSelfDefAttrs(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.EmojiAttr);
        this.supportEmoji = a.getBoolean(0, false);
        this.emojiSize = a.getDimensionPixelSize(2, 0);
        this.emojiMaxRenderLength = a.getInt(3, -1);
        a.recycle();
        TypedArray a2 = context.obtainStyledAttributes(attrs, R.styleable.TextAttr);
        this.dynamicTextSize = a2.getBoolean(0, false);
        a2.recycle();
        this.defalutTextSize = getTextSize();
        setScaleSize();
    }

    private void setScaleSize() {
        if (this.dynamicTextSize && AntUIHelper.getTextSizeGearGetter() != null) {
            float scaleSize = DensityUtil.getTextSize(DensityUtil.px2sp(getContext(), this.defalutTextSize), AntUIHelper.getTextSizeGearGetter().getCurrentGear());
            if (!DensityUtil.isValueEqule(DensityUtil.px2sp(getContext(), getTextSize()), scaleSize)) {
                super.setTextSize(2, scaleSize);
            }
        }
    }

    public void setEmojiSize(int emojiSize2) {
        this.emojiSize = emojiSize2;
    }

    public void setSupportEmoji(boolean isSupport) {
        this.supportEmoji = isSupport;
    }

    public void setEmojiMaxRenderLength(int length) {
        this.emojiMaxRenderLength = length;
    }

    public String getUbbStr() {
        String source = getText().toString();
        try {
            return EmojiUtil.utf2ubb(getContext(), source);
        } catch (Exception e) {
            return source;
        }
    }

    public void setText(CharSequence text, BufferType type) {
        if (this.supportEmoji && text != null) {
            text = EmojiUtil.ubb2utf(text.toString());
        }
        super.setText(text, type);
    }

    /* access modifiers changed from: protected */
    public void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        int i;
        if (text == null || !this.supportEmoji) {
            super.onTextChanged(text, start, lengthBefore, lengthAfter);
            return;
        }
        if (lengthBefore > 0 && lengthAfter == 0) {
            Log.d("APEditText", "lengthBefore > 0 && lengthAfter == 0");
        } else if (lengthBefore != 0 || lengthAfter <= 0) {
            Spannable tempText = getText();
            if (tempText != null) {
                Context context = getContext();
                int emojiSize2 = getEmojiSize();
                int length = tempText.length();
                if (this.emojiMaxRenderLength > 0) {
                    i = this.emojiMaxRenderLength;
                } else {
                    i = -1;
                }
                APEmojiRender.renderEmoji(context, tempText, emojiSize2, 0, length, i);
            }
        } else {
            APEmojiRender.renderEmoji(getContext(), getText(), getEmojiSize(), start, lengthAfter - lengthBefore, this.emojiMaxRenderLength > 0 ? this.emojiMaxRenderLength : -1);
        }
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
    }

    public int getEmojiSize() {
        if (this.emojiSize <= 0) {
            return ((int) getTextSize()) + DensityUtil.dip2px(getContext(), 2.0f);
        }
        return this.emojiSize;
    }

    public boolean onTextContextMenuItem(int id) {
        if (id != 16908322) {
            return super.onTextContextMenuItem(id);
        }
        super.onTextContextMenuItem(id);
        if (this.listener != null) {
            this.listener.onPaste();
        }
        return true;
    }
}
