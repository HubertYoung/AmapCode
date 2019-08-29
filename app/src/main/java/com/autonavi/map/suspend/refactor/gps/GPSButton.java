package com.autonavi.map.suspend.refactor.gps;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageButton;
import android.widget.ImageView.ScaleType;
import com.autonavi.minimap.R;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.concurrent.atomic.AtomicReference;

public class GPSButton extends ImageButton implements ced {
    public static final int BTN_SERIAL_DEFAULT = 0;
    public static final int BTN_SERIAL_LOCK_STATE = 2;
    public static final int BTN_SERIAL_NO_3D = 1;
    public static final int STATE_LOCKCENTER = 2;
    public static final int STATE_LOCKCENTER_3D = 6;
    public static final int STATE_LOCKCENTER_3D_FOCUS = 7;
    public static final int STATE_LOCKCENTER_FOCUS = 5;
    public static final int STATE_NULL = -1;
    public static final int STATE_OFF = 0;
    public static final int STATE_OFF_FOCUS = 3;
    public static final int STATE_UNLOCKCENTER = 1;
    public static final int STATE_UNLOCKCENTER_FOCUS = 4;
    private int mState = -1;

    @Retention(RetentionPolicy.SOURCE)
    public @interface SerialType {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface State {
    }

    public String getLogVersionState() {
        return "";
    }

    public View getView() {
        return this;
    }

    public GPSButton(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        setPadding(0, 0, 0, 0);
        setScaleType(ScaleType.FIT_XY);
        setState(0);
        int dimension = (int) context.getResources().getDimension(R.dimen.map_container_btn_size);
        setLayoutParams(new LayoutParams(dimension, dimension));
        setContentDescription(context.getResources().getString(R.string.LocationMe));
    }

    public int getState() {
        return this.mState;
    }

    public void setState(int i) {
        if (i != this.mState) {
            this.mState = i;
            AtomicReference atomicReference = new AtomicReference();
            AtomicReference atomicReference2 = new AtomicReference();
            getResId(i, atomicReference, atomicReference2);
            Integer num = (Integer) atomicReference.get();
            Integer num2 = (Integer) atomicReference2.get();
            if (!(num == null || num.intValue() == 0)) {
                setImageResource(num.intValue());
            }
            if (num2 != null && num2.intValue() != 0) {
                setBackgroundResource(num2.intValue());
            }
        }
    }

    /* access modifiers changed from: protected */
    public void getResId(int i, AtomicReference<Integer> atomicReference, AtomicReference<Integer> atomicReference2) {
        int i2;
        int i3 = 0;
        switch (i) {
            case 0:
                i3 = R.drawable.icon_c11;
                i2 = R.drawable.icon_c_bg_single;
                break;
            case 1:
                i3 = R.drawable.icon_c11;
                i2 = R.drawable.icon_c_bg_single;
                break;
            case 2:
                i3 = R.drawable.icon_c11_b;
                i2 = R.drawable.icon_c_bg_single;
                break;
            case 3:
                i3 = R.drawable.icon_c11_press;
                i2 = R.drawable.icon_c_bg_single;
                break;
            case 4:
                i3 = R.drawable.icon_c11_press;
                i2 = R.drawable.icon_c_bg_single;
                break;
            case 5:
                i3 = R.drawable.icon_c11_b_press;
                i2 = R.drawable.icon_c_bg_single;
                break;
            case 6:
                i3 = R.drawable.icon_c11_a;
                i2 = R.drawable.icon_c_bg_single;
                break;
            case 7:
                i3 = R.drawable.icon_c11_a_press;
                i2 = R.drawable.icon_c_bg_single;
                break;
            default:
                i2 = 0;
                break;
        }
        if (atomicReference != null) {
            atomicReference.set(Integer.valueOf(i3));
        }
        if (atomicReference2 != null) {
            atomicReference2.set(Integer.valueOf(i2));
        }
    }

    public void setVisibility(int i) {
        super.setVisibility(i);
    }
}
