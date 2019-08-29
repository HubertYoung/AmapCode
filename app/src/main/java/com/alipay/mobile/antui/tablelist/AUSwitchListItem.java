package com.alipay.mobile.antui.tablelist;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout.LayoutParams;
import com.alipay.mobile.antui.R;
import com.alipay.mobile.antui.basic.AUSwitch;
import com.alipay.mobile.antui.basic.AUToggleButton;

public class AUSwitchListItem extends AUAbsListItem implements OnClickListener {
    private boolean isToggle;
    private AUSwitch mSwitch;
    @Deprecated
    private OnSwitchListener mSwitchListener;
    private AUToggleButton mToggle;

    @Deprecated
    public interface OnSwitchListener {
        void onSwitchListener(boolean z, View view);
    }

    public AUSwitchListItem(Context context) {
        this(context, null);
    }

    public AUSwitchListItem(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AUSwitchListItem(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.isToggle = false;
        this.mLeftImageView.setVisibility(8);
        setClickable(true);
        setBackgroundColor(getResources().getColor(R.color.AU_COLOR_UNIVERSAL_BG));
        setArrowVisibility(false);
        initSwitchView(context);
    }

    /* access modifiers changed from: protected */
    public int getImageVerticalMargin(Context context) {
        return getResources().getDimensionPixelOffset(R.dimen.au_list_image_space);
    }

    /* access modifiers changed from: protected */
    public int getLeftImageSize(Context context) {
        return 0;
    }

    private void initSwitchView(Context context) {
        try {
            View view = LayoutInflater.from(context).inflate(R.layout.au_switch_list_item, null);
            this.mSwitch = (AUSwitch) view.findViewById(R.id.listItem_switch);
            this.mSwitch.setChecked(false);
            addRightView(view);
        } catch (NullPointerException npe) {
            Log.w("AUSwitchListItem", npe);
        }
    }

    public void onClick(View v) {
        if (v.equals(this.mSwitch) && this.mSwitchListener != null) {
            this.mSwitchListener.onSwitchListener(this.mSwitch.isChecked(), this.mSwitch);
        }
        if (this.mToggle != null && v.equals(this.mToggle) && this.mSwitchListener != null) {
            this.mSwitchListener.onSwitchListener(this.mToggle.isChecked(), this.mToggle);
        }
    }

    public void setAnimationOff(boolean isOff) {
        this.isToggle = isOff;
        if (isOff) {
            if (this.mToggle == null) {
                this.mToggle = (AUToggleButton) findViewById(R.id.listItem_toggle);
            }
            this.mSwitch.setVisibility(8);
            this.mToggle.setVisibility(0);
            return;
        }
        if (this.mToggle != null) {
            this.mToggle.setVisibility(8);
        }
        this.mSwitch.setVisibility(0);
    }

    public void setOnSwitchListener(OnCheckedChangeListener onCheckedChangeListener) {
        this.mSwitch.setOnCheckedChangeListener(onCheckedChangeListener);
        if (this.mToggle != null) {
            this.mToggle.setOnCheckedChangeListener(onCheckedChangeListener);
        }
    }

    @Deprecated
    public AUSwitch getSwitch() {
        return this.mSwitch;
    }

    public CompoundButton getCompoundSwitch() {
        if (this.isToggle) {
            return this.mToggle;
        }
        return this.mSwitch;
    }

    public void setCompoundSwitchVisible(int visible) {
        if (this.isToggle) {
            this.mToggle.setVisibility(visible);
        } else {
            this.mSwitch.setVisibility(visible);
        }
    }

    public boolean isSwitchOn() {
        if (this.isToggle) {
            return this.mToggle.isChecked();
        }
        return this.mSwitch.isChecked();
    }

    public void setSwitchStatus(boolean status) {
        try {
            this.mSwitch.setChecked(status);
            if (this.mToggle != null) {
                this.mToggle.setChecked(status);
            }
        } catch (NullPointerException npe) {
            Log.w("AUSwitchListItem", npe);
        }
    }

    public void setSwitchEnabled(boolean enabled) {
        this.mSwitch.setEnabled(enabled);
        if (this.mToggle != null) {
            this.mToggle.setEnabled(enabled);
        }
    }

    @Deprecated
    public void setToggleButtonBackGroundResource(int resource) {
    }

    @Deprecated
    public OnSwitchListener getSwitchListener() {
        return this.mSwitchListener;
    }

    @Deprecated
    public void setOnSwitchListener(OnSwitchListener onSwitchListener) {
        this.mSwitchListener = onSwitchListener;
    }

    @Deprecated
    public AUToggleButton getToggleButton() {
        return new AUToggleButton(getContext());
    }

    @Deprecated
    public boolean isSwitchChecked() {
        return this.mSwitch.isChecked();
    }

    @Deprecated
    public void setToggleButtonChecked(boolean checked) {
        this.mSwitch.setChecked(checked);
    }

    @Deprecated
    public void showToggleButton(boolean checked) {
        this.mSwitch.setVisibility(0);
        this.mSwitch.setChecked(checked);
        this.mSwitch.setOnClickListener(this);
    }

    public void setLeftSubText(CharSequence text) {
        if (this.mLeftSubTextView == null) {
            initLeftSubText();
        }
        this.mLeftSubTextView.setText(text);
    }

    public void attachFlagToArrow(View flagView) {
        LayoutParams params = new LayoutParams(-2, -2);
        params.gravity = 16;
        params.leftMargin = getResources().getDimensionPixelSize(R.dimen.AU_SPACE17);
        this.mListLayout.addView(flagView, 1, (ViewGroup.LayoutParams) params);
    }
}
