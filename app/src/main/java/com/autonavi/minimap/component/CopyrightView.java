package com.autonavi.minimap.component;

import android.content.Context;
import android.widget.ImageView;
import android.widget.RelativeLayout.LayoutParams;
import com.autonavi.minimap.R;

public class CopyrightView extends ImageView {
    public void destroy() {
    }

    public CopyrightView(Context context, drr drr) {
        super(context);
        init(drr);
    }

    private void init(drr drr) {
        setLayoutParams(new LayoutParams(-1, -2));
        setBackgroundColor(-1);
        setImageResource(R.drawable.v3_splash);
        setOnClickListener(drr);
    }
}
