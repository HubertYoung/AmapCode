package com.autonavi.carowner.payfor.view;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.autonavi.minimap.R;

public class NaviInfoGroupItemView extends RelativeLayout {
    private final View bottomTimeLine = findViewById(R.id.time_line_bottom);
    private final ImageView clockView = ((ImageView) findViewById(R.id.clock));
    private final TextView timeTV = ((TextView) findViewById(R.id.time));
    private final View topTimeLine = findViewById(R.id.time_line_top);

    public NaviInfoGroupItemView(Context context) {
        super(context);
        setBackgroundColor(-1052173);
        inflate(context, R.layout.activities_naviinfo_time_group_item, this);
    }

    public void setDate(String str) {
        String[] split = str.split("-");
        if (split.length == 2) {
            str = getContext().getString(R.string.activities_navi_date_title, new Object[]{split[0], split[1]});
        }
        if (this.timeTV != null) {
            this.timeTV.setText(str);
        }
    }

    public void setFirst(boolean z) {
        if (z) {
            this.topTimeLine.setVisibility(4);
        } else {
            this.topTimeLine.setVisibility(0);
        }
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        measureTimeLine(0, 1);
    }

    /* access modifiers changed from: private */
    public void measureTimeLine(final int i, final int i2) {
        int height = getHeight();
        if (height > 0) {
            int height2 = (height - this.clockView.getHeight()) / 2;
            this.topTimeLine.setMinimumHeight(height2);
            this.bottomTimeLine.setMinimumHeight(height2);
        } else if (i <= 10) {
            postDelayed(new Runnable() {
                public final void run() {
                    NaviInfoGroupItemView.this.measureTimeLine(i + 1, i2 + 1);
                }
            }, (long) i2);
        }
    }
}
