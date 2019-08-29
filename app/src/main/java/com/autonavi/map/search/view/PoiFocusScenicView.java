package com.autonavi.map.search.view;

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
import android.widget.TextView;
import com.autonavi.minimap.R;

public class PoiFocusScenicView extends FrameLayout implements bch {
    private TextView mTvDesc;
    private TextView mTvTitle;

    public void setStartIconBackground(Drawable drawable) {
    }

    public PoiFocusScenicView(@NonNull Context context) {
        super(context);
        initView();
    }

    public PoiFocusScenicView(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        initView();
    }

    public PoiFocusScenicView(@NonNull Context context, @Nullable AttributeSet attributeSet, @AttrRes int i) {
        super(context, attributeSet, i);
        initView();
    }

    public boolean setTitle(String str) {
        return setTextView(this.mTvTitle, str);
    }

    public boolean setDesc(String str) {
        return setTextView(this.mTvDesc, str);
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.poi_focus_scenic_view, this, true);
        this.mTvTitle = (TextView) findViewById(R.id.tv_bubble_scenic_shortname);
        TextPaint paint = this.mTvTitle.getPaint();
        if (paint != null) {
            paint.setStyle(Style.FILL_AND_STROKE);
            paint.setStrokeWidth(1.0f);
        }
        this.mTvDesc = (TextView) findViewById(R.id.tv_bubble_scenic_duration);
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
