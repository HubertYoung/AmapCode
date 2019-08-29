package com.alipay.biometrics.ui.widget;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.TouchDelegate;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.alipay.android.phone.mobilecommon.biometric.a.a.C0109a;
import com.alipay.android.phone.mobilecommon.biometric.a.a.c;
import com.alipay.android.phone.mobilecommon.biometric.a.a.d;
import com.alipay.android.phone.mobilecommon.biometric.a.a.f;
import com.alipay.mobile.security.bio.utils.BioLog;

public class TitleBar extends RelativeLayout {
    private static final String TAG = "APTitleBar";
    float icon_height = 0.0f;
    float icon_width = 0.0f;
    private Button mBackButton;
    private Button mLeftButton;
    private int mLeftButtonIcon;
    private View mNewFlagView;
    private Button mRightButton;
    private TextView mSecondTitleTextView;
    private Button mSoundButton;
    private LinearLayout mSwitchContainer;
    private int mTitleColor;
    private String mTitleText;
    private TextView mTitleTextView;
    float margin_right = 0.0f;
    float margin_right_generic = 0.0f;
    float margin_seperator = 0.0f;
    private boolean showBackButton;
    private boolean showSoundButton;
    float touch_height = 0.0f;
    float touch_width = 0.0f;

    public TitleBar(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        LayoutInflater.from(context).inflate(d.title_bar, this, true);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, f.titleBar);
        this.mTitleText = obtainStyledAttributes.getString(f.titleBar_bio_titleText);
        this.showBackButton = obtainStyledAttributes.getBoolean(f.titleBar_bio_showBackButton, true);
        this.mTitleColor = obtainStyledAttributes.getColor(f.titleBar_bio_title_color, 17170443);
        this.mLeftButtonIcon = obtainStyledAttributes.getResourceId(f.titleBar_bio_leftButtonIcon, 0);
        this.showSoundButton = obtainStyledAttributes.getBoolean(f.titleBar_bio_showSoundButton, true);
        obtainStyledAttributes.recycle();
        this.icon_height = (float) getResources().getDimensionPixelSize(C0109a.title_bar_icon_height);
        this.icon_width = (float) getResources().getDimensionPixelSize(C0109a.title_bar_icon_width);
    }

    /* access modifiers changed from: protected */
    public void onFinishInflate() {
        this.mTitleTextView = (TextView) findViewById(c.title_bar_title);
        this.mSecondTitleTextView = (TextView) findViewById(c.title_bar_title_second);
        this.mBackButton = (Button) findViewById(c.title_bar_back_button);
        this.mSoundButton = (Button) findViewById(c.title_bar_sound_button);
        setTitleText(this.mTitleText);
        setTitleColer(this.mTitleColor);
        if (this.showSoundButton) {
            this.mSoundButton.setVisibility(0);
        } else {
            this.mSoundButton.setVisibility(8);
        }
        if (this.mLeftButtonIcon != 0) {
            setLeftButtonIconResource(this.mLeftButtonIcon);
        }
        initBackbutton();
    }

    public void setSoundButton(int i) {
        this.mSoundButton.setVisibility(i);
    }

    public void enableSound(boolean z) {
        Drawable drawable = this.mSoundButton.getCompoundDrawables()[2];
        if (z) {
            drawable.setLevel(0);
        } else {
            drawable.setLevel(1);
        }
    }

    public void setCloseButtonVisible(int i) {
        this.mBackButton.setVisibility(i);
    }

    public void setSoundButtonVisible(int i) {
        this.mSoundButton.setVisibility(i);
    }

    public void setTimeOut(String str) {
        if (this.mSoundButton != null) {
            this.mSoundButton.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            this.mSoundButton.setVisibility(0);
            this.mSoundButton.setText(str);
            requestLayout();
        }
    }

    public Button getTimeOut() {
        return this.mSoundButton;
    }

    public void setTitleText(String str) {
        this.mTitleTextView.setText(str);
    }

    public void setTitleColer(int i) {
        this.mTitleTextView.setTextColor(i);
    }

    public void setBackButtonText(String str) {
        if (this.showBackButton) {
            this.mBackButton.setVisibility(0);
            this.mBackButton.setText(str);
        }
    }

    public void setBackButtonListener(OnClickListener onClickListener) {
        this.mBackButton.setVisibility(0);
        this.mBackButton.setOnClickListener(onClickListener);
    }

    public void setSoundButtonListener(OnClickListener onClickListener) {
        this.mSoundButton.setVisibility(0);
        this.mSoundButton.setOnClickListener(onClickListener);
    }

    public void setSwitchContainerVisiable(boolean z) {
        this.mSwitchContainer.setVisibility(z ? 0 : 8);
    }

    public void attachNewFlagView(View view) {
        this.mNewFlagView = view;
        addView(this.mNewFlagView);
    }

    public void unAttachNewFlagView(View view) {
        if (this.mNewFlagView != null) {
            removeView(this.mNewFlagView);
            this.mNewFlagView = null;
        }
    }

    public void setLeftButtonListener(OnClickListener onClickListener) {
        this.mLeftButton.setOnClickListener(onClickListener);
    }

    public void setRightButtonListener(OnClickListener onClickListener) {
        this.mRightButton.setOnClickListener(onClickListener);
    }

    public Button getLeftButton() {
        return this.mLeftButton;
    }

    public TextView getTitleTextView() {
        return this.mTitleTextView;
    }

    public Button getBackButton() {
        return this.mBackButton;
    }

    public TextView getSecondTitleTextView() {
        return this.mSecondTitleTextView;
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
    }

    public void setLeftButtonIconResource(int i) {
        setLeftButtonIcon(getResources().getDrawable(i));
    }

    public void setRightButtonIconResource(int i) {
        setRightButtonIcon(getResources().getDrawable(i));
    }

    public void setLeftButtonIcon(Drawable drawable) {
        layoutButton(drawable, (LayoutParams) this.mLeftButton.getLayoutParams(), 0.0f, this.margin_seperator, this.icon_width, this.mLeftButton);
    }

    public void setRightButtonIcon(Drawable drawable) {
        layoutButton(drawable, (LayoutParams) this.mRightButton.getLayoutParams(), this.margin_seperator, this.margin_right, this.icon_width, this.mRightButton);
    }

    private void layoutButton(Drawable drawable, MarginLayoutParams marginLayoutParams, float f, float f2, float f3, Button button) {
        int i;
        int i2;
        if (drawable != null) {
            int intrinsicHeight = drawable.getCurrent().getIntrinsicHeight();
            int intrinsicWidth = drawable.getCurrent().getIntrinsicWidth();
            if (((float) intrinsicWidth) > f3) {
                i = (int) f3;
                i2 = (int) ((((float) intrinsicHeight) * f3) / ((float) intrinsicWidth));
            } else {
                i = intrinsicWidth;
                i2 = intrinsicHeight;
            }
            marginLayoutParams.height = i2;
            marginLayoutParams.width = i;
            button.setBackgroundDrawable(drawable);
            marginLayoutParams.leftMargin = (int) (((f3 - ((float) i)) / 2.0f) + f);
            marginLayoutParams.rightMargin = (int) (((f3 - ((float) i)) / 2.0f) + f2);
            BioLog.d(TAG, "leftMargin=" + marginLayoutParams.leftMargin + ", rightMargin=" + marginLayoutParams.rightMargin);
        }
    }

    private void adjustTouchDelegate(Button button, float f, float f2) {
        Rect rect = new Rect();
        button.getHitRect(rect);
        if (((float) rect.height()) < f) {
            int height = (int) ((f - ((float) rect.height())) / 2.0f);
            rect.top -= height;
            rect.bottom = height + rect.bottom;
        }
        if (((float) rect.width()) < f2) {
            int width = (int) ((f2 - ((float) rect.width())) / 2.0f);
            rect.left -= width;
            rect.right = width + rect.right;
        }
        TouchDelegate touchDelegate = new TouchDelegate(rect, button);
        if (View.class.isInstance(button.getParent())) {
            ((View) button.getParent()).setTouchDelegate(touchDelegate);
        }
    }

    private void initBackbutton() {
        this.mBackButton.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                try {
                    Context context = TitleBar.this.getContext();
                    ((InputMethodManager) context.getSystemService("input_method")).hideSoftInputFromWindow(view.getWindowToken(), 0);
                    if (context != null && (context instanceof Activity)) {
                        ((Activity) context).onBackPressed();
                    }
                } catch (Exception e) {
                }
            }
        });
    }
}
