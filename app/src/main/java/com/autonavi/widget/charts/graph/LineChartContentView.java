package com.autonavi.widget.charts.graph;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.FontMetrics;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Looper;
import android.os.Parcelable;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import com.autonavi.link.protocol.http.MultipartUtility;
import com.autonavi.widget.R;
import com.autonavi.widget.charts.graph.LineChart.b;
import com.autonavi.widget.charts.graph.LineChart.c;
import com.uc.webview.export.extension.UCCore;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class LineChartContentView extends View {
    static final String STATE_SUPER = "line_chart_view_super";
    private Paint bgPaint;
    private int bottomMargin;
    private int circleRadius;
    private int circleSelectedRadius;
    private int circleWidthRadius;
    private int contentHeight;
    private int contentWidth;
    private a gestureDetector;
    private int height;
    private int leftMargin;
    private boolean mIsOverLayerShowing;
    private boolean mIsValid;
    private Comparator<Point> mPointComparator;
    b mPopAdapter;
    private int offsetX;
    private Paint popBgPaint;
    private String popString;
    private TextPaint popTextPaint;
    private int rightMargin;
    private Paint selectedLinePaint;
    private int selectedX;
    private int spacing;
    private int tapCircleRadius;
    private int topMargin;
    private List<c> valueEntities;
    private int width;
    private List<String> xAxis;
    private Paint xTextPaint;
    private Paint xTextSelectedPaint;
    private List<String> yAxis;
    private List<Float> yAxisValue;
    private Paint yTextPaint;

    class a implements OnGestureListener {
        public final boolean onDown(MotionEvent motionEvent) {
            return false;
        }

        public final boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
            return false;
        }

        public final void onLongPress(MotionEvent motionEvent) {
        }

        public final boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
            return false;
        }

        public final void onShowPress(MotionEvent motionEvent) {
        }

        a() {
        }

        public final boolean onSingleTapUp(MotionEvent motionEvent) {
            return LineChartContentView.this.showPopStringIfNeed(((int) motionEvent.getX()) + LineChartContentView.this.getScrollX(), ((int) motionEvent.getY()) + LineChartContentView.this.getScrollY());
        }
    }

    public void setData(List<String> list, List<String> list2, List<Float> list3, List<c> list4, b bVar) {
        this.xAxis.clear();
        this.yAxis.clear();
        this.yAxisValue.clear();
        this.valueEntities.clear();
        this.xAxis.addAll(list);
        this.yAxis.addAll(list2);
        this.yAxisValue.addAll(list3);
        this.valueEntities.addAll(list4);
        this.mPopAdapter = bVar;
        init(getContext(), list4);
        requestLayout();
    }

    public LineChartContentView(Context context) {
        this(context, null);
    }

    public LineChartContentView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public LineChartContentView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.selectedX = -1;
        this.popString = "";
        this.xAxis = new ArrayList();
        this.yAxis = new ArrayList();
        this.yAxisValue = new ArrayList();
        this.valueEntities = new ArrayList();
        this.mIsOverLayerShowing = false;
        this.mPointComparator = new Comparator<Point>() {
            public final /* bridge */ /* synthetic */ int compare(Object obj, Object obj2) {
                return ((Point) obj).y - ((Point) obj2).y;
            }
        };
        this.gestureDetector = new a();
    }

    public a getGestureDetector() {
        return this.gestureDetector;
    }

    private void init(Context context, List<c> list) {
        dismissOverLayer();
        this.selectedLinePaint = new Paint();
        this.selectedLinePaint.setStyle(Style.FILL);
        this.selectedLinePaint.setColor(context.getResources().getColor(R.color.chart_f_c_4n));
        this.selectedLinePaint.setStrokeWidth(1.0f);
        this.selectedLinePaint.setAntiAlias(true);
        this.bgPaint = new Paint();
        this.bgPaint.setStyle(Style.FILL);
        this.bgPaint.setColor(context.getResources().getColor(17170443));
        this.bgPaint.setAntiAlias(true);
        this.bgPaint.setStrokeWidth(2.0f);
        this.xTextPaint = new Paint();
        this.xTextPaint.setTextAlign(Align.CENTER);
        this.xTextPaint.setStyle(Style.FILL);
        this.xTextPaint.setColor(context.getResources().getColor(R.color.chart_f_c_2n));
        this.xTextPaint.setAntiAlias(true);
        this.xTextPaint.setTextSize(getResources().getDimension(R.dimen.chart_f_s_16));
        this.xTextSelectedPaint = new Paint();
        this.xTextSelectedPaint.setTextAlign(Align.CENTER);
        this.xTextSelectedPaint.setStyle(Style.FILL);
        this.xTextSelectedPaint.setColor(context.getResources().getColor(R.color.chart_f_c_6n));
        this.xTextSelectedPaint.setAntiAlias(true);
        this.xTextSelectedPaint.setTextSize(getResources().getDimension(R.dimen.chart_f_s_16));
        this.yTextPaint = new Paint();
        this.yTextPaint.setTextAlign(Align.RIGHT);
        this.yTextPaint.setStyle(Style.FILL);
        this.yTextPaint.setColor(context.getResources().getColor(R.color.chart_f_c_2n));
        this.yTextPaint.setAntiAlias(true);
        this.yTextPaint.setTextSize(getResources().getDimension(R.dimen.chart_f_s_16));
        this.popBgPaint = new Paint();
        this.popBgPaint.setStyle(Style.FILL);
        this.popBgPaint.setColor(context.getResources().getColor(17170444));
        this.popBgPaint.setAlpha(204);
        this.popBgPaint.setAntiAlias(true);
        this.popTextPaint = new TextPaint();
        this.popTextPaint.setTextAlign(Align.LEFT);
        this.popTextPaint.setStyle(Style.FILL);
        this.popTextPaint.setColor(context.getResources().getColor(R.color.chart_f_c_1));
        this.popTextPaint.setAntiAlias(true);
        this.popTextPaint.setTextSize(getResources().getDimension(R.dimen.chart_f_s_14));
        this.offsetX = Math.max(eqp.a(this.xTextPaint, this.xAxis.get(0)) / 2, this.circleSelectedRadius);
        this.leftMargin = eqp.a(context, 68);
        this.rightMargin = eqp.a(context, 32);
        this.topMargin = eqp.a(context, 76);
        this.bottomMargin = eqp.a(context, 64);
        this.circleRadius = eqp.a(getContext(), 3);
        this.circleWidthRadius = eqp.a(getContext(), 6);
        this.circleSelectedRadius = eqp.a(getContext(), 12);
        this.tapCircleRadius = eqp.a(getContext(), 14);
        this.selectedX = -1;
        this.popString = "";
        for (int i = 0; i < this.valueEntities.size(); i++) {
            c cVar = list.get(i);
            getContext();
            cVar.f = new Paint();
            cVar.f.setStyle(Style.FILL);
            cVar.f.setColor(cVar.a);
            cVar.f.setAntiAlias(true);
            cVar.f.setStrokeWidth((float) cVar.h);
            cVar.g = new Paint();
            cVar.g.setTextAlign(Align.CENTER);
            cVar.g.setStyle(Style.FILL);
            cVar.g.setColor(cVar.a);
            cVar.g.setAlpha(102);
            cVar.g.setAntiAlias(true);
            cVar.g.setStrokeWidth((float) cVar.h);
        }
    }

    public boolean isValid() {
        return this.mIsValid;
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        this.width = MeasureSpec.getSize(i);
        int size = this.topMargin + this.bottomMargin + ((this.yAxis.size() - 1) * eqp.a(getContext(), 36));
        int mode = MeasureSpec.getMode(i2);
        int size2 = MeasureSpec.getSize(i2);
        if (mode == Integer.MIN_VALUE) {
            this.height = Math.min(size2, size);
        } else if (mode != 1073741824) {
            this.height = size;
        } else {
            this.height = size2;
        }
        setMeasuredDimension(MeasureSpec.makeMeasureSpec(this.width, UCCore.VERIFY_POLICY_QUICK), MeasureSpec.makeMeasureSpec(this.height, UCCore.VERIFY_POLICY_QUICK));
        calcPoint();
    }

    private void calcPoint() {
        if (this.xAxis.size() < 2 || this.yAxisValue.isEmpty()) {
            this.mIsValid = false;
            return;
        }
        this.spacing = (this.width - ((this.leftMargin + this.rightMargin) + (this.offsetX * 2))) / this.xAxis.size();
        if (this.xAxis.size() <= 6) {
            this.spacing = (this.width - ((this.leftMargin + this.rightMargin) + (this.offsetX * 2))) / 5;
        } else {
            this.spacing = (this.width - ((this.leftMargin + this.rightMargin) + (this.offsetX * 2))) / (this.xAxis.size() - 1);
        }
        this.contentWidth = this.width;
        this.contentHeight = this.height;
        this.mIsValid = computePoint();
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (isValid()) {
            drawSelectedLine(canvas);
            drawLine(canvas);
            drawValues(canvas);
            drawHorizontalAxis(canvas);
            drawVerticalValues(canvas);
            drawPopString(canvas);
        }
    }

    private void drawSelectedLine(Canvas canvas) {
        if (!TextUtils.isEmpty(this.popString)) {
            Point point = null;
            for (int i = 0; i < this.valueEntities.size(); i++) {
                if (this.valueEntities.get(i).d.size() > this.selectedX) {
                    if (point == null) {
                        point = this.valueEntities.get(i).d.get(this.selectedX);
                    } else if (this.valueEntities.get(i).d.get(this.selectedX).y < point.y) {
                        point = this.valueEntities.get(i).d.get(this.selectedX);
                    }
                }
            }
            if (point != null) {
                int a2 = eqp.a(getContext(), 6);
                if (this.xAxis.size() > this.selectedX) {
                    canvas.drawLine((float) (this.leftMargin + (this.spacing * this.selectedX) + this.offsetX), (float) ((point.y - this.circleSelectedRadius) - a2), (float) (this.leftMargin + (this.spacing * this.selectedX) + this.offsetX), (float) (this.contentHeight - this.bottomMargin), this.selectedLinePaint);
                }
            }
        }
    }

    private void drawVerticalValues(Canvas canvas) {
        float f = this.yTextPaint.getFontMetrics().ascent;
        canvas.drawRect((float) getScrollX(), (float) (this.topMargin - this.circleSelectedRadius), (float) (getScrollX() + this.leftMargin), (float) this.height, this.bgPaint);
        for (int i = 0; i < this.yAxis.size(); i++) {
            canvas.drawText(this.yAxis.get(i), (float) ((this.leftMargin + getScrollX()) - eqp.a(getContext(), 16)), ((float) ((this.topMargin + ((this.contentHeight - this.topMargin) - this.bottomMargin)) - ((((this.contentHeight - this.topMargin) - this.bottomMargin) * i) / (this.yAxis.size() - 1)))) - (f / 2.0f), this.yTextPaint);
        }
    }

    private void drawHorizontalAxis(Canvas canvas) {
        this.xTextPaint.setTextAlign(Align.CENTER);
        int a2 = eqp.a(getContext(), 24);
        FontMetrics fontMetrics = this.xTextPaint.getFontMetrics();
        for (int i = 0; i < this.xAxis.size(); i++) {
            if (i == this.selectedX) {
                canvas.drawText(this.xAxis.get(i), (float) (this.leftMargin + (this.spacing * i) + this.offsetX), (((float) ((this.height + getScrollY()) - this.bottomMargin)) - fontMetrics.top) + ((float) a2), this.xTextSelectedPaint);
            } else {
                canvas.drawText(this.xAxis.get(i), (float) (this.leftMargin + (this.spacing * i) + this.offsetX), (((float) ((this.height + getScrollY()) - this.bottomMargin)) - fontMetrics.top) + ((float) a2), this.xTextPaint);
            }
        }
    }

    private boolean computePoint() {
        int i;
        float floatValue = this.yAxisValue.get(0).floatValue();
        float floatValue2 = this.yAxisValue.get(this.yAxisValue.size() - 1).floatValue();
        if (floatValue2 <= floatValue) {
            return false;
        }
        for (int i2 = 0; i2 < this.valueEntities.size(); i2++) {
            for (int i3 = 0; i3 < this.valueEntities.get(i2).c.size(); i3++) {
                int i4 = this.leftMargin + (this.spacing * i3) + this.offsetX;
                if (((double) floatValue2) == 0.0d) {
                    i = this.height - this.bottomMargin;
                } else if (this.valueEntities.get(i2).c.get(i3).floatValue() < 0.0f) {
                    i = this.height - this.bottomMargin;
                } else {
                    i = (int) (((float) (this.topMargin + ((this.contentHeight - this.topMargin) - this.bottomMargin))) - ((((float) ((this.contentHeight - this.topMargin) - this.bottomMargin)) * (this.valueEntities.get(i2).c.get(i3).floatValue() - floatValue)) / (floatValue2 - floatValue)));
                }
                Point point = this.valueEntities.get(i2).d.get(i3);
                point.x = i4;
                point.y = i;
                Rect rect = this.valueEntities.get(i2).e.get(i3);
                rect.left = i4 - this.tapCircleRadius;
                rect.top = i - this.tapCircleRadius;
                rect.right = i4 + this.tapCircleRadius;
                rect.bottom = i + this.tapCircleRadius;
            }
        }
        return true;
    }

    private void drawValues(Canvas canvas) {
        for (int i = 0; i < this.valueEntities.size(); i++) {
            for (int i2 = 0; i2 < this.valueEntities.get(i).c.size(); i2++) {
                Point point = this.valueEntities.get(i).d.get(i2);
                if (i2 == this.selectedX) {
                    canvas.drawCircle((float) point.x, (float) point.y, (float) this.circleSelectedRadius, this.valueEntities.get(i).g);
                    canvas.drawCircle((float) point.x, (float) point.y, (float) this.circleSelectedRadius, this.valueEntities.get(i).g);
                }
                canvas.drawCircle((float) point.x, (float) point.y, (float) this.circleWidthRadius, this.valueEntities.get(i).f);
                canvas.drawCircle((float) point.x, (float) point.y, (float) this.circleRadius, this.bgPaint);
            }
        }
    }

    private void drawLine(Canvas canvas) {
        for (int i = 0; i < this.valueEntities.size(); i++) {
            int i2 = 0;
            while (i2 < this.valueEntities.get(i).c.size() - 1) {
                Point point = this.valueEntities.get(i).d.get(i2);
                i2++;
                Point point2 = this.valueEntities.get(i).d.get(i2);
                canvas.drawLine((float) point.x, (float) point.y, (float) point2.x, (float) point2.y, this.valueEntities.get(i).f);
            }
        }
    }

    private void drawPopString(Canvas canvas) {
        Canvas canvas2 = canvas;
        if (!TextUtils.isEmpty(this.popString)) {
            Point point = null;
            for (int i = 0; i < this.valueEntities.size(); i++) {
                if (this.valueEntities.get(i).d.size() > this.selectedX) {
                    if (point == null) {
                        point = this.valueEntities.get(i).d.get(this.selectedX);
                    } else if (this.valueEntities.get(i).d.get(this.selectedX).y < point.y) {
                        point = this.valueEntities.get(i).d.get(this.selectedX);
                    }
                }
            }
            if (point != null) {
                String[] split = this.popString.split(MultipartUtility.LINE_FEED);
                int i2 = 0;
                for (String str : split) {
                    if (i2 <= eqp.a((Paint) this.popTextPaint, str)) {
                        i2 = eqp.a((Paint) this.popTextPaint, str);
                    }
                }
                FontMetrics fontMetrics = this.popTextPaint.getFontMetrics();
                float f = fontMetrics.bottom - fontMetrics.top;
                int a2 = eqp.a(getContext(), 8);
                int a3 = eqp.a(getContext(), 8);
                int a4 = eqp.a(getContext(), 6);
                int a5 = eqp.a(getContext(), 20);
                int a6 = eqp.a(getContext(), 9);
                int a7 = eqp.a(getContext(), 8);
                int i3 = i2 / 2;
                int max = Math.max(a7, (point.x - i3) - a2);
                if (point.x + i3 + a2 > this.contentWidth) {
                    max = ((this.contentWidth - i2) - (a2 * 2)) - a7;
                }
                float length = (((float) split.length) * f) + ((float) (a3 * 2));
                float f2 = (float) a6;
                float max2 = Math.max(0.0f, (((((float) point.y) - length) - ((float) this.circleSelectedRadius)) - f2) - ((float) a4));
                float f3 = (float) ((int) (length + max2));
                canvas2.drawRoundRect(new RectF((float) max, max2, (float) (i2 + max + (a2 * 2)), f3), 6.0f, 6.0f, this.popBgPaint);
                Path path = new Path();
                int i4 = a5 / 2;
                path.moveTo((float) (point.x - i4), f3);
                path.lineTo((float) (point.x + i4), f3);
                path.lineTo((float) point.x, f3 + f2);
                path.close();
                canvas2.drawPath(path, this.popBgPaint);
                for (int i5 = 0; i5 < split.length; i5++) {
                    canvas2.drawText(split[i5], (float) (max + a2), ((((float) a3) + max2) - fontMetrics.top) + (((float) i5) * f), this.popTextPaint);
                }
                this.mIsOverLayerShowing = true;
                return;
            }
            return;
        }
        this.mIsOverLayerShowing = false;
    }

    public boolean dismissOverLayer() {
        if (!this.mIsOverLayerShowing) {
            return false;
        }
        dismissPopStringIfNeed();
        return true;
    }

    /* access modifiers changed from: private */
    public boolean showPopStringIfNeed(int i, int i2) {
        for (int i3 = 0; i3 < this.valueEntities.size(); i3++) {
            int i4 = 0;
            while (true) {
                if (i4 >= this.valueEntities.get(i3).e.size()) {
                    break;
                }
                Rect rect = this.valueEntities.get(i3).e.get(i4);
                if (rect.left >= i || rect.right <= i || rect.top >= i2) {
                    i4++;
                } else {
                    this.selectedX = i4;
                    if (getLineIndex(this.selectedX, i2) >= 0) {
                        if (this.mPopAdapter != null) {
                            this.popString = this.mPopAdapter.a();
                            this.mIsOverLayerShowing = !TextUtils.isEmpty(this.popString);
                        }
                        invalidate();
                        return true;
                    }
                }
            }
        }
        dismissPopStringIfNeed();
        return false;
    }

    public void showOverLayer(String str, int i, int i2) {
        if (this.selectedX == i2 && this.selectedX >= 0 && !TextUtils.isEmpty(str)) {
            this.popString = str;
            this.mIsOverLayerShowing = true;
            if (Looper.myLooper() == Looper.getMainLooper()) {
                invalidate();
                forceLayout();
                requestLayout();
                return;
            }
            postInvalidate();
        }
    }

    private int getLineIndex(int i, int i2) {
        ArrayList arrayList = new ArrayList();
        int i3 = 0;
        for (int i4 = 0; i4 < this.valueEntities.size(); i4++) {
            c cVar = this.valueEntities.get(i4);
            if (cVar.d.size() > i) {
                arrayList.add(new Point(i4, cVar.d.get(i).y));
            }
        }
        if (arrayList.isEmpty()) {
            return -1;
        }
        Collections.sort(arrayList, this.mPointComparator);
        int i5 = 0;
        while (true) {
            if (i3 >= arrayList.size()) {
                break;
            }
            Point point = (Point) arrayList.get(i3);
            if (point.y < i2) {
                int i6 = i3;
                i3++;
                i5 = i6;
            } else if (i5 != i3 && Math.abs(((Point) arrayList.get(i5)).y - i2) > Math.abs(point.y - i2)) {
                i5 = i3;
            }
        }
        return ((Point) arrayList.get(i5)).x;
    }

    private void dismissPopStringIfNeed() {
        this.mIsOverLayerShowing = false;
        if (this.selectedX != -1 || "".equals(this.popString)) {
            this.selectedX = -1;
            this.popString = "";
            invalidate();
        }
    }

    /* access modifiers changed from: protected */
    public Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putString("popString", this.popString);
        bundle.putInt("selectedX", this.selectedX);
        bundle.putParcelable(STATE_SUPER, super.onSaveInstanceState());
        return bundle;
    }

    /* access modifiers changed from: protected */
    public void onRestoreInstanceState(Parcelable parcelable) {
        if (parcelable instanceof Bundle) {
            Bundle bundle = (Bundle) parcelable;
            this.popString = bundle.getString("popString", "");
            this.selectedX = bundle.getInt("selectedX", -1);
            super.onRestoreInstanceState(bundle.getParcelable(STATE_SUPER));
            return;
        }
        super.onRestoreInstanceState(parcelable);
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }
}
