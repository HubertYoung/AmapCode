package com.autonavi.widget.charts.graph;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.view.View.MeasureSpec;
import android.widget.ImageView;
import com.uc.webview.export.extension.UCCore;

public class CircleImageView extends ImageView {
    /* access modifiers changed from: private */
    public float radius;

    class a extends OvalShape {
        private a() {
        }

        /* synthetic */ a(CircleImageView circleImageView, byte b) {
            this();
        }

        public final void draw(Canvas canvas, Paint paint) {
            canvas.drawCircle((float) (CircleImageView.this.getWidth() / 2), (float) (CircleImageView.this.getHeight() / 2), CircleImageView.this.radius, paint);
        }
    }

    public CircleImageView(Context context, int i, float f) {
        super(context);
        this.radius = f;
        ShapeDrawable shapeDrawable = new ShapeDrawable(new a(this, 0));
        shapeDrawable.getPaint().setColor(i);
        setBackgroundDrawable(shapeDrawable);
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        setMeasuredDimension(MeasureSpec.makeMeasureSpec((int) (this.radius * 2.0f), UCCore.VERIFY_POLICY_QUICK), MeasureSpec.makeMeasureSpec((int) (this.radius * 2.0f), UCCore.VERIFY_POLICY_QUICK));
    }
}
