package com.alipay.mobile.antui.basic;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.StyleSpan;
import android.util.AttributeSet;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.TextView.BufferType;
import com.alipay.mobile.antui.R;
import com.alipay.mobile.antui.api.AntUIHelper;
import com.alipay.mobile.antui.api.OnParseEmotionListener;
import com.alipay.mobile.antui.screenadpt.AUAttrsConstant;
import com.alipay.mobile.antui.screenadpt.AUAttrsUtils;
import com.alipay.mobile.antui.screenadpt.AUScreenAdaptTool;
import com.alipay.mobile.antui.screenadpt.AUScreenUtils;
import com.alipay.mobile.antui.utils.APEmojiRender;
import com.alipay.mobile.antui.utils.DensityUtil;
import com.alipay.mobile.antui.utils.EmojiUtil;
import com.uc.webview.export.extension.UCCore;

public class AUTextView extends TextView implements AUViewInterface {
    private static final int MAX_ELLIPISE_WIDTH = 9999;
    private static OnParseEmotionListener parseEmotionListener;
    private String attrHeight;
    private AttributeSet attrs;
    private float defalutTextSize;
    private boolean dynamicTextSize;
    private int ellipsizeWidth;
    private int emojiSize;
    private boolean hasEmotion;
    private Boolean isAP;
    private OnTextViewTextChangeListener onTextChangeListener;
    private OnVisibilityChangeListener onVisibilityChangeListener;
    private int replaceCount;
    private boolean supportEmoji;
    private boolean supportEmotion;

    public interface OnTextViewTextChangeListener {
        void onTextViewTextChange(TextView textView, String str);
    }

    public interface OnVisibilityChangeListener {
        void onChange(int i);
    }

    public void setOnVisibilityChangeListener(OnVisibilityChangeListener onVisibilityChangeListener2) {
        this.onVisibilityChangeListener = onVisibilityChangeListener2;
    }

    public OnVisibilityChangeListener getOnVisibilityChangeListener() {
        return this.onVisibilityChangeListener;
    }

    public OnTextViewTextChangeListener getOnTextChangeListener() {
        return this.onTextChangeListener;
    }

    public void setOnTextChangeListener(OnTextViewTextChangeListener onTextChangeListener2) {
        this.onTextChangeListener = onTextChangeListener2;
    }

    public AUTextView(Context context) {
        super(context);
        this.emojiSize = 0;
        this.dynamicTextSize = false;
        this.ellipsizeWidth = 9999;
        this.defalutTextSize = getTextSize();
        setScaleSize();
    }

    public AUTextView(Context context, AttributeSet attrs2) {
        super(context, attrs2);
        this.emojiSize = 0;
        this.dynamicTextSize = false;
        this.ellipsizeWidth = 9999;
        initSelfDefAttrs(context, attrs2);
        if (AUScreenUtils.checkApFlag(context, attrs2, this)) {
            AUAttrsUtils.adptApPadding(this, context);
        }
    }

    public AUTextView(Context context, AttributeSet attrs2, int defStyle) {
        super(context, attrs2, defStyle);
        this.emojiSize = 0;
        this.dynamicTextSize = false;
        this.ellipsizeWidth = 9999;
        initSelfDefAttrs(context, attrs2);
        if (AUScreenUtils.checkApFlag(context, attrs2, this)) {
            AUAttrsUtils.adptApPadding(this, context);
        }
    }

    public void setTextSize(int unit, float size) {
        if (AUScreenUtils.checkApFlag(getContext(), this.attrs, this) && unit == 1) {
            size = (float) AUScreenAdaptTool.getApFromDp(getContext(), size);
            unit = 0;
        }
        super.setTextSize(unit, size);
        this.defalutTextSize = getTextSize();
        setScaleSize();
    }

    private void initSelfDefAttrs(Context context, AttributeSet attrs2) {
        this.attrs = attrs2;
        if (attrs2 != null) {
            TypedArray a = context.obtainStyledAttributes(attrs2, R.styleable.EmojiAttr);
            this.attrHeight = attrs2.getAttributeValue("http://schemas.android.com/apk/res/android", AUAttrsConstant.VIEW_HEIGHT);
            this.supportEmoji = a.getBoolean(0, false);
            this.supportEmotion = a.getBoolean(1, false);
            this.emojiSize = a.getDimensionPixelSize(2, 0);
            a.recycle();
            TypedArray a2 = context.obtainStyledAttributes(attrs2, R.styleable.TextAttr);
            this.dynamicTextSize = a2.getBoolean(0, false);
            a2.recycle();
            setApTextSize(attrs2);
            this.defalutTextSize = getTextSize();
            setScaleSize();
            if (AUScreenUtils.checkApFlag(context, attrs2, this)) {
                AUAttrsUtils.adptApPadding(this, context);
            }
        }
    }

    private void setApTextSize(AttributeSet attrs2) {
        int textSize;
        if (AUScreenUtils.checkApFlag(getContext(), attrs2, this)) {
            String textSizeStr = (String) AUAttrsUtils.handleAttrs(attrs2).get(AUAttrsConstant.TV_TEXTSIZE);
            if (TextUtils.isEmpty(textSizeStr)) {
                textSize = AUScreenAdaptTool.getApFromPx(getContext(), getTextSize());
            } else {
                textSize = AUScreenAdaptTool.getApFromAttrsStr(getContext(), textSizeStr);
            }
            super.setTextSize(0, (float) textSize);
        }
    }

    public void setOnClickListener(OnClickListener l) {
        super.setOnClickListener(AUViewEventHelper.wrapClickListener(l));
    }

    public void setSupportEmoji(boolean isEmoji) {
        this.supportEmoji = isEmoji;
    }

    public void setSupportEmotion(boolean isSupportEmotion) {
        this.supportEmotion = isSupportEmotion;
    }

    public void setEmojiSize(int emojiSize2) {
        this.emojiSize = emojiSize2;
    }

    private void singleLineEllipsize(int width) {
        if (width >= 0) {
            CharSequence sourceText = getText();
            CharSequence formatStr = TextUtils.ellipsize(sourceText, getPaint(), (float) width, getEllipsize());
            if (!TextUtils.equals(formatStr, sourceText)) {
                if (sourceText.length() > formatStr.length()) {
                    updateEllipsizeWidth(width);
                }
                setText(formatStr);
            }
        }
    }

    private void setScaleSize() {
        if (this.dynamicTextSize && AntUIHelper.getTextSizeGearGetter() != null) {
            float scaleSize = DensityUtil.getTextSize(this.defalutTextSize, AntUIHelper.getTextSizeGearGetter().getCurrentGear());
            if (!DensityUtil.isValueEqule(getTextSize(), scaleSize)) {
                super.setTextSize(0, scaleSize);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if ((this.supportEmoji || this.supportEmotion) && isSingleLine() && "-2".equals(this.attrHeight)) {
            int gear = 1;
            if (AntUIHelper.getTextSizeGearGetter() != null) {
                gear = AntUIHelper.getTextSizeGearGetter().getCurrentGear();
            }
            super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(((int) (getTextSize() + DensityUtil.getTextSize((float) AUScreenAdaptTool.getApFromDp(getContext(), 6.0f), gear))) + getPaddingTop() + getPaddingBottom(), UCCore.VERIFY_POLICY_QUICK));
            return;
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        setScaleSize();
        if (((this.supportEmoji && this.replaceCount > 0) || (this.supportEmotion && this.hasEmotion)) && getEllipsize() != null && isSingleLine()) {
            int width = ((right - left) - getLeftPaddingOffset()) - getRightPaddingOffset();
            if (this.ellipsizeWidth != 9999) {
                width = Math.max(width, this.ellipsizeWidth);
            }
            singleLineEllipsize(width);
        }
    }

    private void updateEllipsizeWidth(int width) {
        if (this.ellipsizeWidth == 9999) {
            this.ellipsizeWidth = width;
        } else {
            this.ellipsizeWidth = Math.max(width, this.ellipsizeWidth);
        }
    }

    public void setText(CharSequence text, BufferType type) {
        SpannableStringBuilder builder;
        if ((this.supportEmoji || this.supportEmotion) && text != null) {
            if (this.supportEmoji) {
                if (!(text instanceof SpannableStringBuilder) && !(text instanceof SpannableString)) {
                    text = EmojiUtil.ubb2utf(text.toString());
                }
                builder = new SpannableStringBuilder(text);
                this.replaceCount = APEmojiRender.renderEmojiReturncount(getContext(), builder, getEmojiSize());
            } else {
                builder = new SpannableStringBuilder(text);
            }
            if (this.supportEmotion && parseEmotionListener != null) {
                this.hasEmotion = parseEmotionListener.parser(getContext(), builder, text, getEmojiSize());
            }
            if ((this.replaceCount <= 0 && !this.hasEmotion) || getEllipsize() == null) {
                super.setText(builder, type);
            } else if (isSingleLine()) {
                singeLineRender(builder, type);
            } else {
                super.setText(builder, type);
            }
            if (this.onTextChangeListener != null && text != null) {
                this.onTextChangeListener.onTextViewTextChange(this, builder.toString());
                return;
            }
            return;
        }
        super.setText(text, type);
        if (this.onTextChangeListener != null && text != null) {
            this.onTextChangeListener.onTextViewTextChange(this, text.toString());
        }
    }

    private void singeLineRender(CharSequence text, BufferType type) {
        super.setText(TextUtils.ellipsize(text, getPaint(), (float) this.ellipsizeWidth, getEllipsize()), type);
    }

    private boolean isSingleLine() {
        return getMaxLines() == 1;
    }

    public void setAutoSplitText(String rawText) {
        setText(rawText);
        getViewTreeObserver().addOnGlobalLayoutListener(new ao(this, 0));
    }

    public void setBoldAutoSplitText(String text) {
        if (text == null) {
            text = "";
        }
        SpannableString spannableString = new SpannableString(text);
        spannableString.setSpan(new StyleSpan(1), 0, spannableString.length(), 0);
        setText(spannableString);
        getViewTreeObserver().addOnGlobalLayoutListener(new ao(this, 0));
    }

    public void setBoldText(String boldText) {
        if (boldText == null) {
            boldText = "";
        }
        SpannableString spannableString = new SpannableString(boldText);
        spannableString.setSpan(new StyleSpan(1), 0, spannableString.length(), 0);
        setText(spannableString);
    }

    private String autoSplitText() {
        CharSequence content = getText();
        if ((content instanceof Spanned) && ((Spanned) content).getSpans(0, content.length(), Object.class).length > 0) {
            return null;
        }
        String rawText = content.toString();
        Paint tvPaint = getPaint();
        float tvWidth = (float) ((getWidth() - getPaddingLeft()) - getPaddingRight());
        if (tvWidth <= 0.0f) {
            return null;
        }
        String[] rawTextLines = rawText.replaceAll("\r", "").split("\n");
        StringBuilder sbNewText = new StringBuilder();
        for (String rawTextLine : rawTextLines) {
            if (tvPaint.measureText(rawTextLine) <= tvWidth) {
                sbNewText.append(rawTextLine);
            } else {
                float lineWidth = 0.0f;
                int cnt = 0;
                while (cnt != rawTextLine.length()) {
                    char ch = rawTextLine.charAt(cnt);
                    lineWidth += tvPaint.measureText(String.valueOf(ch));
                    if (lineWidth <= tvWidth) {
                        sbNewText.append(ch);
                    } else {
                        sbNewText.append("\n");
                        lineWidth = 0.0f;
                        cnt--;
                    }
                    cnt++;
                }
            }
            sbNewText.append("\n");
        }
        if (!rawText.endsWith("\n")) {
            sbNewText.deleteCharAt(sbNewText.length() - 1);
        }
        return sbNewText.toString();
    }

    public int getEmojiSize() {
        if (this.emojiSize <= 0) {
            return ((int) getTextSize()) + AUScreenAdaptTool.getApFromDp(getContext(), 2.0f);
        }
        return this.emojiSize;
    }

    public boolean isDynamicTextSize() {
        return this.dynamicTextSize;
    }

    public void setDynamicTextSize(boolean dynamicTextSize2) {
        this.dynamicTextSize = dynamicTextSize2;
    }

    public static OnParseEmotionListener getParseEmotionListener() {
        return parseEmotionListener;
    }

    public static void setParseEmotionListener(OnParseEmotionListener parseEmotionListener2) {
        parseEmotionListener = parseEmotionListener2;
    }

    public void setVisibility(int visibility) {
        super.setVisibility(visibility);
        if (this.onVisibilityChangeListener != null) {
            this.onVisibilityChangeListener.onChange(visibility);
        }
    }

    public Boolean isAP() {
        return this.isAP;
    }

    public void setAP(Boolean isAP2) {
        this.isAP = isAP2;
    }
}
