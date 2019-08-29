package com.alipay.mobile.antui.iconfont;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;
import com.alipay.mobile.antui.R;
import com.alipay.mobile.antui.basic.AUFrameLayout;
import com.alipay.mobile.antui.basic.AUImageView;
import com.alipay.mobile.antui.basic.AUTextView;
import com.alipay.mobile.antui.iconfont.constants.IconfontConstants;
import com.alipay.mobile.antui.iconfont.manager.TypefaceCache;
import com.alipay.mobile.antui.iconfont.util.UIPropUtil;
import com.alipay.mobile.antui.utils.ResourceUtils;
import com.alipay.mobile.common.share.widget.ResUtils;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AUIconView extends AUFrameLayout implements IconfontInterface {
    private String iconfontFileName;
    private AUImageView imageView;
    private boolean mEnabled;
    private String mIconfontBundle;
    private int mIconfontColor;
    private ColorStateList mIconfontColorStateList;
    private float mIconfontSize;
    private int mImageSize;
    private boolean mIsColorInt;
    private List<AUTextView> mTextViews;

    public AUIconView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mIconfontBundle = "default";
        this.mTextViews = new ArrayList();
        this.mEnabled = true;
        this.mIconfontSize = -1.0f;
        this.mImageSize = 0;
        this.mIsColorInt = true;
        this.mIconfontColorStateList = null;
        this.iconfontFileName = "default";
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.IconView);
        if (ta.hasValue(0)) {
            setIconfontBundle(ta.getString(0));
        }
        if (ta.hasValue(2)) {
            setIconfontFileName(ta.getString(2));
        }
        if (ta.hasValue(3)) {
            try {
                setIconfontFonts(new JSONArray(ta.getString(3)));
            } catch (JSONException e) {
                Log.d("IconView", "JSONException = " + e);
            }
        }
        if (ta.hasValue(5)) {
            setIconfontUnicode(ta.getString(5));
        }
        if (ta.hasValue(4)) {
            setIconfontSize(ta.getDimension(4, 24.0f));
        }
        if (ta.hasValue(1)) {
            setIconfontColorStates(ta.getColorStateList(1));
        }
        if (ta.hasValue(7)) {
            this.mImageSize = (int) ta.getDimension(7, 0.0f);
        }
        if (ta.hasValue(6)) {
            setImageResource(ta.getResourceId(6, 0));
        }
        ta.recycle();
    }

    public AUIconView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AUIconView(Context context) {
        this(context, null);
    }

    private void setupTypeface() {
        setIconfontTypeface(TypefaceCache.getTypeface(getContext(), getIconfontBundle(), getTTFFilePath()));
    }

    public AUIconView setIconfontBundle(String mBundleName) {
        this.mIconfontBundle = mBundleName;
        if (this.mTextViews != null && !this.mTextViews.isEmpty()) {
            setupTypeface();
        }
        return this;
    }

    public String getIconfontBundle() {
        return this.mIconfontBundle;
    }

    public Context getIconfontContext() {
        return getContext();
    }

    public void destroy() {
    }

    public AUIconView setIconfontTypeface(Typeface typeface) {
        if (typeface != null) {
            for (AUTextView typeface2 : this.mTextViews) {
                typeface2.setTypeface(typeface);
            }
        }
        return this;
    }

    public AUIconView setIconfontUnicode(String text) {
        if (!TextUtils.isEmpty(text)) {
            clearView();
            AUTextView textView = new AUTextView(getContext());
            textView.setGravity(17);
            textView.setText(text);
            setupTextViewCS(textView);
            this.mTextViews.add(textView);
            LayoutParams params = new LayoutParams(-2, -2);
            params.gravity = 17;
            addView((View) textView, (ViewGroup.LayoutParams) params);
            setupTypeface();
        }
        return this;
    }

    public AUIconView setImageResource(int resId) {
        if (resId != 0) {
            clearView();
            initImageView();
            this.imageView.setImageResource(resId);
            addView(this.imageView);
        }
        return this;
    }

    private void initImageView() {
        this.imageView = new AUImageView(getContext());
        this.imageView.setScaleType(ScaleType.CENTER_INSIDE);
        this.imageView.setEnabled(this.mEnabled);
        setImageViewSize(this.mImageSize);
    }

    public IconfontInterface setImageDrawable(Drawable drawable) {
        clearView();
        initImageView();
        this.imageView.setImageDrawable(drawable);
        addView(this.imageView);
        return this;
    }

    public AUIconView setIconfontFonts(JSONArray fonts) {
        if (fonts != null && fonts.length() > 0) {
            clearView();
            for (int i = 0; i < fonts.length(); i++) {
                JSONObject fontObject = fonts.optJSONObject(i);
                if (fontObject != null) {
                    String unicode = fontObject.optString(IconfontConstants.KEY_ICON_UNICODE);
                    String color = fontObject.optString("color");
                    AUTextView textView = new AUTextView(getContext());
                    textView.setGravity(17);
                    textView.setText(unicode);
                    setupTextViewCS(textView);
                    if (!TextUtils.isEmpty(color)) {
                        try {
                            String[] colorArray = color.split(";");
                            if (colorArray != null && colorArray.length == 1) {
                                textView.setTextColor(UIPropUtil.getColorByValue(color));
                            } else if (colorArray != null) {
                                if (colorArray.length > 1) {
                                    ColorStateList colorStateList = UIPropUtil.genTextSelector(getContext(), colorArray);
                                    if (colorStateList != null) {
                                        textView.setTextColor(colorStateList);
                                    }
                                }
                            }
                        } catch (Exception e) {
                            Log.d("IconView", "颜色转型错误 Exception = " + e);
                        }
                    }
                    this.mTextViews.add(textView);
                    addView(textView);
                }
            }
            setupTypeface();
        }
        return this;
    }

    private void clearView() {
        removeAllViews();
        this.imageView = null;
        this.mTextViews.clear();
    }

    private void setupTextViewCS(TextView textView) {
        if (textView != null) {
            if (this.mIsColorInt) {
                textView.setTextColor(this.mIconfontColor);
            } else if (this.mIconfontColorStateList != null && !this.mIsColorInt) {
                textView.setTextColor(this.mIconfontColorStateList);
            }
            if (this.mIconfontSize != -1.0f) {
                textView.setTextSize(0, this.mIconfontSize);
            }
            textView.setEnabled(this.mEnabled);
        }
    }

    public AUIconView setIconfontColor(String colorStr) {
        if (!TextUtils.isEmpty(colorStr)) {
            try {
                String[] colorArray = colorStr.split(";");
                if (colorArray != null && colorArray.length == 1) {
                    setIconfontColor(UIPropUtil.getColorByValue(colorStr));
                } else if (colorArray != null && colorArray.length > 1) {
                    setIconfontColorStates(UIPropUtil.genTextSelector(getContext(), colorArray));
                }
            } catch (Exception e) {
                Log.d("IconView", "颜色转型错误 Exception = " + e);
            }
        }
        return this;
    }

    public AUIconView setIconfontColor(int color) {
        this.mIsColorInt = true;
        this.mIconfontColor = color;
        for (AUTextView textColor : this.mTextViews) {
            textColor.setTextColor(color);
        }
        return this;
    }

    public AUIconView setIconfontColorStates(ColorStateList color) {
        this.mIsColorInt = false;
        this.mIconfontColorStateList = color;
        if (color != null) {
            for (AUTextView textColor : this.mTextViews) {
                textColor.setTextColor(color);
            }
        }
        return this;
    }

    public AUIconView setIconfontSize(String size) {
        float iconfontSize;
        try {
            iconfontSize = UIPropUtil.getPx(size, getContext());
            if (iconfontSize == -1.0f) {
                iconfontSize = -1.0f;
            }
        } catch (NumberFormatException e) {
            iconfontSize = -1.0f;
        }
        setIconfontSize(iconfontSize);
        return this;
    }

    public AUIconView setIconfontSize(float size) {
        this.mIconfontSize = size;
        if (size != -1.0f) {
            for (AUTextView textSize : this.mTextViews) {
                textSize.setTextSize(0, size);
            }
        }
        return this;
    }

    public void setIconTextMinHeight(int height) {
        for (AUTextView minHeight : this.mTextViews) {
            minHeight.setMinHeight(height);
        }
    }

    public void setImageViewSize(int size) {
        if (size != 0) {
            this.mImageSize = size;
            if (this.imageView != null) {
                LayoutParams lp = (LayoutParams) this.imageView.getLayoutParams();
                if (lp != null) {
                    lp.height = size;
                    lp.width = size;
                } else {
                    lp = new LayoutParams(size, size);
                }
                this.imageView.setLayoutParams(lp);
            }
        }
    }

    public AUImageView getImageView() {
        if (this.imageView == null) {
            clearView();
            initImageView();
            addView(this.imageView);
        }
        return this.imageView;
    }

    public void setIconfontFileName(String iconfontFileName2) {
        this.iconfontFileName = iconfontFileName2;
    }

    public String getIconfontFileName() {
        return this.iconfontFileName;
    }

    private String getTTFFilePath() {
        return getIconfontBundle() + File.separator + getIconfontFileName() + IconfontConstants.ICONFONT_FILE_SUFFIX;
    }

    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        this.mEnabled = enabled;
        for (int i = 0; i < getChildCount(); i++) {
            getChildAt(i).setEnabled(enabled);
        }
    }

    public void setImportantForAccessibility(int mode) {
        super.setImportantForAccessibility(mode);
        for (int i = 0; i < getChildCount(); i++) {
            getChildAt(i).setImportantForAccessibility(mode);
        }
    }

    public void setContentDescription(CharSequence contentDescription) {
        super.setContentDescription(contentDescription);
        for (int i = 0; i < getChildCount(); i++) {
            getChildAt(i).setContentDescription(contentDescription);
        }
    }

    public void setIconByName(String name) {
        setIconfontUnicode(getResources().getString(ResourceUtils.getResourceId(getContext(), name, ResUtils.STRING)));
    }
}
