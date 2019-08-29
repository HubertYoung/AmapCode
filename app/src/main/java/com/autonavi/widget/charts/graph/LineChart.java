package com.autonavi.widget.charts.graph;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View.MeasureSpec;
import android.widget.FrameLayout;
import com.autonavi.widget.R;
import com.uc.webview.export.extension.UCCore;
import java.util.List;

public class LineChart extends FrameLayout {
    private int bgColor;
    private GestureDetector gestureDetector;
    /* access modifiers changed from: private */
    public LineChartContentView mLineChartContentView;
    private LineChartTitleView mLineChartTitleView;

    class a implements OnGestureListener {
        public final boolean onDown(MotionEvent motionEvent) {
            return false;
        }

        public final boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
            return false;
        }

        public final void onLongPress(MotionEvent motionEvent) {
        }

        public final void onShowPress(MotionEvent motionEvent) {
        }

        a() {
        }

        public final boolean onSingleTapUp(MotionEvent motionEvent) {
            LineChart.this.mLineChartContentView.getGestureDetector().onSingleTapUp(motionEvent);
            return false;
        }

        public final boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
            LineChart.this.mLineChartContentView.getGestureDetector().onScroll(motionEvent, motionEvent2, f, f2);
            return false;
        }
    }

    public interface b {
        String a();
    }

    public static class c {
        public int a;
        public String b;
        public List<Float> c;
        public List<Point> d;
        public List<Rect> e;
        public Paint f;
        public Paint g;
        public int h;
    }

    public void setData(List<String> list, List<String> list2, List<Float> list3, List<c> list4, b bVar) {
        this.mLineChartContentView.setData(list, list2, list3, list4, bVar);
        this.mLineChartTitleView.setData(list4);
    }

    public boolean dismissOverLayer() {
        return this.mLineChartContentView.dismissOverLayer();
    }

    public LineChart(Context context) {
        this(context, null);
    }

    public LineChart(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public LineChart(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.bgColor = 17170443;
        setBackgroundColor(getResources().getColor(this.bgColor));
        this.mLineChartTitleView = new LineChartTitleView(context);
        this.mLineChartTitleView.setGravity(80);
        addView(this.mLineChartTitleView, -1, eqp.a(getContext(), 46));
        this.mLineChartContentView = new LineChartContentView(context);
        this.mLineChartContentView.setId(R.id.graph_grapha_id);
        addView(this.mLineChartContentView, -1, -2);
        this.gestureDetector = new GestureDetector(context, new a());
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        int mode = MeasureSpec.getMode(i2);
        int size = MeasureSpec.getSize(i2);
        if (mode == 1073741824) {
            LineChartContentView lineChartContentView = this.mLineChartContentView;
            lineChartContentView.measure(resolveSize(i, lineChartContentView.getMeasuredWidth()), MeasureSpec.makeMeasureSpec(size, UCCore.VERIFY_POLICY_QUICK));
        }
    }

    public void setTitleGravity(int i) {
        if (i == 3 || i == 5 || i == 17) {
            this.mLineChartTitleView.setGravity(i | 80);
        }
    }

    public void showOverLayer(String str, int i, int i2) {
        this.mLineChartContentView.showOverLayer(str, i, i2);
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        this.gestureDetector.onTouchEvent(motionEvent);
        return true;
    }
}
