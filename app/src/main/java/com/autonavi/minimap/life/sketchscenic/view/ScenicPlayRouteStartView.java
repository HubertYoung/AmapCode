package com.autonavi.minimap.life.sketchscenic.view;

import android.content.Context;
import android.graphics.Paint.Style;
import android.graphics.drawable.Drawable;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.autonavi.minimap.R;

public class ScenicPlayRouteStartView extends FrameLayout implements bch {
    private ImageView mStartIcon;
    public TextView mTvDesc;
    private TextView mTvTitle;

    public @interface ChangeType {
        public static final int BIG_TYPE = 1;
        public static final int SMALL_TYPE = 2;
    }

    public ScenicPlayRouteStartView(@NonNull Context context) {
        super(context);
    }

    public ScenicPlayRouteStartView(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public ScenicPlayRouteStartView(@NonNull Context context, @Nullable AttributeSet attributeSet, @AttrRes int i) {
        super(context, attributeSet, i);
    }

    public boolean setTitle(String str) {
        return setTextView(this.mTvTitle, str);
    }

    public boolean setDesc(String str) {
        return setTextView(this.mTvDesc, str);
    }

    public void setStartIconBackground(Drawable drawable) {
        if (this.mStartIcon != null) {
            this.mStartIcon.setBackground(drawable);
        }
    }

    public void initView(@ChangeType int i) {
        LayoutInflater.from(getContext()).inflate(i == 1 ? R.layout.play_operate_poi_layout_overlay : R.layout.play_operate_poi_layout_small, this, true);
        this.mTvTitle = (TextView) findViewById(R.id.play_operate_bubble_title);
        TextPaint paint = this.mTvTitle.getPaint();
        if (paint != null) {
            paint.setStyle(Style.FILL_AND_STROKE);
            paint.setStrokeWidth(1.0f);
        }
        this.mTvDesc = (TextView) findViewById(R.id.play_operate_bubble_desc);
        this.mStartIcon = (ImageView) findViewById(R.id.play_start_icon);
    }

    private boolean setTextView(TextView textView, String str) {
        if (textView == null) {
            return false;
        }
        if (TextUtils.isEmpty(str)) {
            textView.setText("");
        } else {
            textView.setText(str);
        }
        return true;
    }
}
