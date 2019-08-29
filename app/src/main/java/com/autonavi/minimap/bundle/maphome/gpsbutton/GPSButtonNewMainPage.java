package com.autonavi.minimap.bundle.maphome.gpsbutton;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView.ScaleType;
import com.autonavi.map.suspend.refactor.gps.GPSButton;
import com.autonavi.minimap.R;
import java.util.concurrent.atomic.AtomicReference;

public class GPSButtonNewMainPage extends GPSButton {
    public GPSButtonNewMainPage(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        setPadding(0, 0, 0, 0);
        setScaleType(ScaleType.FIT_XY);
    }

    public void getResId(int i, AtomicReference<Integer> atomicReference, AtomicReference<Integer> atomicReference2) {
        int i2;
        int i3 = 0;
        switch (i) {
            case 0:
                i3 = R.drawable.icon_c34;
                i2 = R.drawable.icon_c_bg_d;
                break;
            case 1:
                i3 = R.drawable.icon_c34;
                i2 = R.drawable.icon_c_bg_d;
                break;
            case 2:
                i3 = R.drawable.icon_c34_b;
                i2 = R.drawable.icon_c_bg_d;
                break;
            case 3:
                i3 = R.drawable.icon_c34_circle_press;
                i2 = R.drawable.icon_c_bg_d;
                break;
            case 4:
                i3 = R.drawable.icon_c34_circle_press;
                i2 = R.drawable.icon_c_bg_d;
                break;
            case 5:
                i3 = R.drawable.icon_c34_b_circle_press;
                i2 = R.drawable.icon_c_bg_d;
                break;
            case 6:
                i3 = R.drawable.icon_c34_a;
                i2 = R.drawable.icon_c_bg_d;
                break;
            case 7:
                i3 = R.drawable.icon_c34_a_circle_press;
                i2 = R.drawable.icon_c_bg_d;
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
}
