package com.autonavi.minimap.route.common.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint.Style;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;
import com.autonavi.minimap.R;

public class RouteStrokeTextView extends TextView {
    private TextView borderText = null;

    public RouteStrokeTextView(Context context) {
        super(context);
        this.borderText = new TextView(context);
        init();
    }

    public RouteStrokeTextView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.borderText = new TextView(context, attributeSet);
        init();
    }

    public RouteStrokeTextView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.borderText = new TextView(context, attributeSet, i);
        init();
    }

    public void init() {
        TextPaint paint = this.borderText.getPaint();
        paint.setStrokeWidth(3.0f);
        paint.setStyle(Style.STROKE);
        this.borderText.setTextColor(getResources().getColor(R.color.font_cff));
        this.borderText.setGravity(getGravity());
    }

    public void setLayoutParams(LayoutParams layoutParams) {
        super.setLayoutParams(layoutParams);
        this.borderText.setLayoutParams(layoutParams);
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        CharSequence text = this.borderText.getText();
        if (text == null || !text.equals(getText())) {
            this.borderText.setText(getText());
            postInvalidate();
        }
        this.borderText.measure(i, i2);
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        this.borderText.layout(i, i2, i3, i4);
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        this.borderText.draw(canvas);
        super.onDraw(canvas);
    }
}
