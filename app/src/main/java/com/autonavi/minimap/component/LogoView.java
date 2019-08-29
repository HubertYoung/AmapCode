package com.autonavi.minimap.component;

import android.content.Context;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import com.autonavi.minimap.R;

public class LogoView extends ImageView {
    public void destroy() {
    }

    public LogoView(Context context, drr drr, String str) {
        super(context);
        init(drr, str);
    }

    private void init(drr drr, String str) {
        LayoutParams layoutParams = new LayoutParams(-2, -2);
        layoutParams.gravity = 80;
        layoutParams.leftMargin = agn.a(getContext(), 10.0f);
        layoutParams.bottomMargin = agn.a(getContext(), 24.0f);
        setLayoutParams(layoutParams);
        int i = "1".equals(str) ? R.drawable.logo_white : "2".equals(str) ? R.drawable.logo_black : "3".equals(str) ? R.drawable.logo_base : 0;
        setImageResource(i);
    }
}
