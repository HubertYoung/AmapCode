package com.amap.bundle.commonui.linechart;

import android.annotation.SuppressLint;
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
import com.amap.bundle.commonui.linechart.internal.LineChartContentView;
import com.amap.bundle.commonui.linechart.internal.LineChartTitleView;
import com.autonavi.minimap.R;
import com.uc.webview.export.extension.UCCore;
import java.util.ArrayList;
import java.util.List;

public class LineChart extends FrameLayout {
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
        String getPopString(int i, int i2);
    }

    public static class c {
        public int a;
        public String b;
        public List<Float> c = new ArrayList();
        public List<Point> d = new ArrayList();
        public List<Rect> e = new ArrayList();
        public Paint f;
        public Paint g;
        public int h = 2;

        public c(int i, int i2, String str, List<Float> list) {
            this.a = i;
            this.b = str;
            this.c = list;
            this.h = i2;
            for (int i3 = 0; i3 < list.size(); i3++) {
                this.d.add(new Point());
                this.e.add(new Rect());
            }
        }
    }

    public void setData(List<String> list, List<String> list2, List<Float> list3, List<c> list4, b bVar) {
        this.mLineChartContentView.setData(list, list2, list3, list4, bVar);
        this.mLineChartTitleView.setData(list4);
    }

    @SuppressLint({"RtlHardcoded"})
    public void setTitleGravity(int i) {
        if (3 == i || 17 == i || 5 == i) {
            this.mLineChartTitleView.setGravity(i | 80);
        }
    }

    public void showHintMessage(String str, int i) {
        this.mLineChartContentView.showOverLayer(str, i);
    }

    public void showOverLayer(String str, int i, int i2) {
        this.mLineChartContentView.showOverLayer(str, i2);
    }

    public boolean dismissOverLayer() {
        return this.mLineChartContentView.dismissOverLayer();
    }

    public boolean dismissrHintMessage() {
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
        setBackgroundColor(getResources().getColor(17170443));
        this.mLineChartTitleView = new LineChartTitleView(context);
        this.mLineChartTitleView.setGravity(80);
        addView(this.mLineChartTitleView, -1, mk.a(getContext(), 46));
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

    public boolean onTouchEvent(MotionEvent motionEvent) {
        this.gestureDetector.onTouchEvent(motionEvent);
        return true;
    }
}
