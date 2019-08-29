package com.autonavi.minimap.ajx3.views;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Looper;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import com.autonavi.minimap.ajx3.R;
import com.autonavi.minimap.ajx3.util.DimensionUtils;
import com.autonavi.minimap.ajx3.util.ViewUtils;

public class OfflineLabel extends RelativeLayout {
    private int FONT_SIZE = 24;
    private int IMAGE_HEIGHT = 42;
    private int IMAGE_ID = R.id.temp_view;
    private int IMAGE_WIDTH = 42;
    private int MARGIN_LEFT = 14;
    /* access modifiers changed from: private */
    public ImageView mImage;
    private TextView mText;

    public OfflineLabel(Context context) {
        super(context);
        init(context);
        setChildPadding(this.MARGIN_LEFT);
        setTextSize(this.FONT_SIZE);
        setTextColor(-16777216);
    }

    private void init(Context context) {
        this.mText = new TextView(context);
        this.mText.setGravity(8388629);
        LayoutParams layoutParams = new LayoutParams(-1, -1);
        layoutParams.addRule(9, -1);
        layoutParams.addRule(15, -1);
        layoutParams.addRule(0, this.IMAGE_ID);
        this.mImage = new ImageView(context);
        this.mImage.setId(this.IMAGE_ID);
        LayoutParams layoutParams2 = new LayoutParams(DimensionUtils.standardUnitToPixel((float) this.IMAGE_WIDTH), DimensionUtils.standardUnitToPixel((float) this.IMAGE_HEIGHT));
        layoutParams2.addRule(11, -1);
        layoutParams2.addRule(15, -1);
        addView(this.mImage, layoutParams2);
        addView(this.mText, layoutParams);
    }

    public void setText(String str) {
        this.mText.setText(str);
    }

    public void setTextColor(int i) {
        this.mText.setTextColor(i);
    }

    public void setTextSize(int i) {
        this.mText.setTextSize(0, (float) DimensionUtils.standardUnitToPixel((float) i));
    }

    public void setChildPadding(int i) {
        ((LayoutParams) this.mImage.getLayoutParams()).setMargins(DimensionUtils.standardUnitToPixel((float) i), 0, 0, 0);
    }

    public void setImageVisible(boolean z) {
        this.mImage.setVisibility(z ? 0 : 8);
    }

    public void setImageDrawable(final Drawable drawable) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            ViewUtils.setBackground(this.mImage, drawable);
        } else {
            this.mImage.post(new Runnable() {
                public void run() {
                    ViewUtils.setBackground(OfflineLabel.this.mImage, drawable);
                }
            });
        }
    }
}
