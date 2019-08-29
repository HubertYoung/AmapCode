package com.amap.bundle.commonui.linechart.internal;

import android.annotation.SuppressLint;
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
import com.amap.bundle.commonui.linechart.LineChart.b;
import com.amap.bundle.commonui.linechart.LineChart.c;
import com.autonavi.link.protocol.http.MultipartUtility;
import com.autonavi.minimap.R;
import com.uc.webview.export.extension.UCCore;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class LineChartContentView extends View {
    static final String STATE_SUPER = "line_chart_view_super";
    private a gestureDetector;
    private Paint mBgPaint;
    private int mBottomMargin;
    private int mCircleRadius;
    private int mCircleSelectedRadius;
    private int mCircleWidthRadius;
    private int mContentHeight;
    private int mContentWidth;
    private int mHeight;
    private boolean mIsOverLayerShowing;
    private boolean mIsValid;
    private int mLeftMargin;
    private Comparator<Point> mPointComparator;
    b mPopAdapter;
    private Paint mPopBgPaint;
    private String mPopString;
    private TextPaint mPopTextPaint;
    private int mRightMargin;
    private Paint mSelectedLinePaint;
    private int mSelectedX;
    private int mSpacing;
    private int mTapCircleRadius;
    private int mTopMargin;
    private int mWidth;
    private List<String> mXAxis;
    private Paint mXTextPaint;
    private Paint mXTextSelectedPaint;
    private List<String> mYAxis;
    private List<Float> mYAxisValue;
    private Paint mYTextPaint;
    private int offsetX;
    private List<c> valueEntities;

    public class a implements OnGestureListener {
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

        public a() {
        }

        public final boolean onSingleTapUp(MotionEvent motionEvent) {
            return LineChartContentView.this.showPopStringIfNeed(((int) motionEvent.getX()) + LineChartContentView.this.getScrollX(), ((int) motionEvent.getY()) + LineChartContentView.this.getScrollY());
        }
    }

    public void setData(List<String> list, List<String> list2, List<Float> list3, List<c> list4, b bVar) {
        this.mXAxis.clear();
        this.mYAxis.clear();
        this.mYAxisValue.clear();
        this.valueEntities.clear();
        this.mXAxis.addAll(list);
        this.mYAxis.addAll(list2);
        this.mYAxisValue.addAll(list3);
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
        this.mPopString = "";
        this.mSelectedX = -1;
        this.mXAxis = new ArrayList();
        this.mYAxis = new ArrayList();
        this.mYAxisValue = new ArrayList();
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
        this.mSelectedLinePaint = new Paint();
        this.mSelectedLinePaint.setStyle(Style.FILL);
        this.mSelectedLinePaint.setColor(context.getResources().getColor(R.color.chart_f_c_4n));
        this.mSelectedLinePaint.setStrokeWidth(1.0f);
        this.mSelectedLinePaint.setAntiAlias(true);
        this.mBgPaint = new Paint();
        this.mBgPaint.setStyle(Style.FILL);
        this.mBgPaint.setColor(context.getResources().getColor(17170443));
        this.mBgPaint.setAntiAlias(true);
        this.mBgPaint.setStrokeWidth(2.0f);
        this.mXTextPaint = new Paint();
        this.mXTextPaint.setTextAlign(Align.CENTER);
        this.mXTextPaint.setStyle(Style.FILL);
        this.mXTextPaint.setColor(context.getResources().getColor(R.color.chart_f_c_2n));
        this.mXTextPaint.setAntiAlias(true);
        this.mXTextPaint.setTextSize(getResources().getDimension(R.dimen.chart_f_s_16));
        this.mXTextSelectedPaint = new Paint();
        this.mXTextSelectedPaint.setTextAlign(Align.CENTER);
        this.mXTextSelectedPaint.setStyle(Style.FILL);
        this.mXTextSelectedPaint.setColor(context.getResources().getColor(R.color.chart_f_c_6n));
        this.mXTextSelectedPaint.setAntiAlias(true);
        this.mXTextSelectedPaint.setTextSize(getResources().getDimension(R.dimen.chart_f_s_16));
        this.mYTextPaint = new Paint();
        this.mYTextPaint.setTextAlign(Align.RIGHT);
        this.mYTextPaint.setStyle(Style.FILL);
        this.mYTextPaint.setColor(context.getResources().getColor(R.color.chart_f_c_2n));
        this.mYTextPaint.setAntiAlias(true);
        this.mYTextPaint.setTextSize(getResources().getDimension(R.dimen.chart_f_s_16));
        this.mPopBgPaint = new Paint();
        this.mPopBgPaint.setStyle(Style.FILL);
        this.mPopBgPaint.setColor(context.getResources().getColor(17170444));
        this.mPopBgPaint.setAlpha(204);
        this.mPopBgPaint.setAntiAlias(true);
        this.mPopTextPaint = new TextPaint();
        this.mPopTextPaint.setTextAlign(Align.LEFT);
        this.mPopTextPaint.setStyle(Style.FILL);
        this.mPopTextPaint.setColor(context.getResources().getColor(R.color.chart_f_c_1));
        this.mPopTextPaint.setAntiAlias(true);
        this.mPopTextPaint.setTextSize(getResources().getDimension(R.dimen.chart_f_s_14));
        this.offsetX = Math.max(mk.a(this.mXTextPaint, this.mXAxis.get(0)) / 2, this.mCircleSelectedRadius);
        this.mLeftMargin = mk.a(context, 68);
        this.mRightMargin = mk.a(context, 32);
        this.mTopMargin = mk.a(context, 76);
        this.mBottomMargin = mk.a(context, 64);
        this.mCircleRadius = mk.a(getContext(), 3);
        this.mCircleWidthRadius = mk.a(getContext(), 6);
        this.mCircleSelectedRadius = mk.a(getContext(), 12);
        this.mTapCircleRadius = mk.a(getContext(), 14);
        this.mSelectedX = -1;
        this.mPopString = "";
        for (int i = 0; i < this.valueEntities.size(); i++) {
            c cVar = list.get(i);
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
    @SuppressLint({"SwitchIntDef"})
    public void onMeasure(int i, int i2) {
        this.mWidth = MeasureSpec.getSize(i);
        int size = this.mTopMargin + this.mBottomMargin + ((this.mYAxis.size() - 1) * mk.a(getContext(), 36));
        int mode = MeasureSpec.getMode(i2);
        int size2 = MeasureSpec.getSize(i2);
        if (mode == Integer.MIN_VALUE) {
            this.mHeight = Math.min(size2, size);
        } else if (mode != 1073741824) {
            this.mHeight = size;
        } else {
            this.mHeight = size2;
        }
        setMeasuredDimension(MeasureSpec.makeMeasureSpec(this.mWidth, UCCore.VERIFY_POLICY_QUICK), MeasureSpec.makeMeasureSpec(this.mHeight, UCCore.VERIFY_POLICY_QUICK));
        calcPoint();
    }

    private void calcPoint() {
        if (this.mXAxis.size() < 2 || this.mYAxisValue.isEmpty()) {
            this.mIsValid = false;
            return;
        }
        this.mSpacing = (this.mWidth - ((this.mLeftMargin + this.mRightMargin) + (this.offsetX * 2))) / this.mXAxis.size();
        if (this.mXAxis.size() <= 6) {
            this.mSpacing = (this.mWidth - ((this.mLeftMargin + this.mRightMargin) + (this.offsetX * 2))) / 5;
        } else {
            this.mSpacing = (this.mWidth - ((this.mLeftMargin + this.mRightMargin) + (this.offsetX * 2))) / (this.mXAxis.size() - 1);
        }
        this.mContentWidth = this.mWidth;
        this.mContentHeight = this.mHeight;
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
        if (!TextUtils.isEmpty(this.mPopString)) {
            Point point = null;
            for (int i = 0; i < this.valueEntities.size(); i++) {
                if (this.valueEntities.get(i).d.size() > this.mSelectedX) {
                    if (point == null) {
                        point = this.valueEntities.get(i).d.get(this.mSelectedX);
                    } else if (this.valueEntities.get(i).d.get(this.mSelectedX).y < point.y) {
                        point = this.valueEntities.get(i).d.get(this.mSelectedX);
                    }
                }
            }
            if (point != null) {
                int a2 = mk.a(getContext(), 6);
                if (this.mXAxis.size() > this.mSelectedX) {
                    canvas.drawLine((float) (this.mLeftMargin + (this.mSpacing * this.mSelectedX) + this.offsetX), (float) ((point.y - this.mCircleSelectedRadius) - a2), (float) (this.mLeftMargin + (this.mSpacing * this.mSelectedX) + this.offsetX), (float) (this.mContentHeight - this.mBottomMargin), this.mSelectedLinePaint);
                }
            }
        }
    }

    private void drawVerticalValues(Canvas canvas) {
        float f = this.mYTextPaint.getFontMetrics().ascent;
        canvas.drawRect((float) getScrollX(), (float) (this.mTopMargin - this.mCircleSelectedRadius), (float) (getScrollX() + this.mLeftMargin), (float) this.mHeight, this.mBgPaint);
        for (int i = 0; i < this.mYAxis.size(); i++) {
            canvas.drawText(this.mYAxis.get(i), (float) ((this.mLeftMargin + getScrollX()) - mk.a(getContext(), 16)), ((float) ((this.mTopMargin + ((this.mContentHeight - this.mTopMargin) - this.mBottomMargin)) - ((((this.mContentHeight - this.mTopMargin) - this.mBottomMargin) * i) / (this.mYAxis.size() - 1)))) - (f / 2.0f), this.mYTextPaint);
        }
    }

    private void drawHorizontalAxis(Canvas canvas) {
        this.mXTextPaint.setTextAlign(Align.CENTER);
        int a2 = mk.a(getContext(), 24);
        FontMetrics fontMetrics = this.mXTextPaint.getFontMetrics();
        for (int i = 0; i < this.mXAxis.size(); i++) {
            if (i == this.mSelectedX) {
                canvas.drawText(this.mXAxis.get(i), (float) (this.mLeftMargin + (this.mSpacing * i) + this.offsetX), (((float) ((this.mHeight + getScrollY()) - this.mBottomMargin)) - fontMetrics.top) + ((float) a2), this.mXTextSelectedPaint);
            } else {
                canvas.drawText(this.mXAxis.get(i), (float) (this.mLeftMargin + (this.mSpacing * i) + this.offsetX), (((float) ((this.mHeight + getScrollY()) - this.mBottomMargin)) - fontMetrics.top) + ((float) a2), this.mXTextPaint);
            }
        }
    }

    private boolean computePoint() {
        int i;
        float floatValue = this.mYAxisValue.get(0).floatValue();
        float floatValue2 = this.mYAxisValue.get(this.mYAxisValue.size() - 1).floatValue();
        if (floatValue2 <= floatValue) {
            return false;
        }
        for (int i2 = 0; i2 < this.valueEntities.size(); i2++) {
            for (int i3 = 0; i3 < this.valueEntities.get(i2).c.size(); i3++) {
                int i4 = this.mLeftMargin + (this.mSpacing * i3) + this.offsetX;
                if (((double) floatValue2) == 0.0d) {
                    i = this.mHeight - this.mBottomMargin;
                } else if (this.valueEntities.get(i2).c.get(i3).floatValue() < 0.0f) {
                    i = this.mHeight - this.mBottomMargin;
                } else {
                    i = (int) (((float) (this.mTopMargin + ((this.mContentHeight - this.mTopMargin) - this.mBottomMargin))) - ((((float) ((this.mContentHeight - this.mTopMargin) - this.mBottomMargin)) * (this.valueEntities.get(i2).c.get(i3).floatValue() - floatValue)) / (floatValue2 - floatValue)));
                }
                Point point = this.valueEntities.get(i2).d.get(i3);
                point.x = i4;
                point.y = i;
                Rect rect = this.valueEntities.get(i2).e.get(i3);
                rect.left = i4 - this.mTapCircleRadius;
                rect.top = i - this.mTapCircleRadius;
                rect.right = i4 + this.mTapCircleRadius;
                rect.bottom = i + this.mTapCircleRadius;
            }
        }
        return true;
    }

    private void drawValues(Canvas canvas) {
        for (int i = 0; i < this.valueEntities.size(); i++) {
            for (int i2 = 0; i2 < this.valueEntities.get(i).c.size(); i2++) {
                Point point = this.valueEntities.get(i).d.get(i2);
                if (i2 == this.mSelectedX) {
                    canvas.drawCircle((float) point.x, (float) point.y, (float) this.mCircleSelectedRadius, this.valueEntities.get(i).g);
                    canvas.drawCircle((float) point.x, (float) point.y, (float) this.mCircleSelectedRadius, this.valueEntities.get(i).g);
                }
                canvas.drawCircle((float) point.x, (float) point.y, (float) this.mCircleWidthRadius, this.valueEntities.get(i).f);
                canvas.drawCircle((float) point.x, (float) point.y, (float) this.mCircleRadius, this.mBgPaint);
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
        if (!TextUtils.isEmpty(this.mPopString)) {
            Point point = null;
            for (int i = 0; i < this.valueEntities.size(); i++) {
                if (this.valueEntities.get(i).d.size() > this.mSelectedX) {
                    if (point == null) {
                        point = this.valueEntities.get(i).d.get(this.mSelectedX);
                    } else if (this.valueEntities.get(i).d.get(this.mSelectedX).y < point.y) {
                        point = this.valueEntities.get(i).d.get(this.mSelectedX);
                    }
                }
            }
            if (point != null) {
                String[] split = this.mPopString.split(MultipartUtility.LINE_FEED);
                int i2 = 0;
                for (String str : split) {
                    if (i2 <= mk.a((Paint) this.mPopTextPaint, str)) {
                        i2 = mk.a((Paint) this.mPopTextPaint, str);
                    }
                }
                FontMetrics fontMetrics = this.mPopTextPaint.getFontMetrics();
                float f = fontMetrics.bottom - fontMetrics.top;
                int a2 = mk.a(getContext(), 8);
                int a3 = mk.a(getContext(), 8);
                int a4 = mk.a(getContext(), 6);
                int a5 = mk.a(getContext(), 20);
                int a6 = mk.a(getContext(), 9);
                int a7 = mk.a(getContext(), 8);
                int i3 = i2 / 2;
                int max = Math.max(a7, (point.x - i3) - a2);
                if (point.x + i3 + a2 > this.mContentWidth) {
                    max = ((this.mContentWidth - i2) - (a2 * 2)) - a7;
                }
                float length = (((float) split.length) * f) + ((float) (a3 * 2));
                float f2 = (float) a6;
                float max2 = Math.max(0.0f, (((((float) point.y) - length) - ((float) this.mCircleSelectedRadius)) - f2) - ((float) a4));
                float f3 = (float) ((int) (length + max2));
                canvas2.drawRoundRect(new RectF((float) max, max2, (float) (i2 + max + (a2 * 2)), f3), 6.0f, 6.0f, this.mPopBgPaint);
                Path path = new Path();
                int i4 = a5 / 2;
                path.moveTo((float) (point.x - i4), f3);
                path.lineTo((float) (point.x + i4), f3);
                path.lineTo((float) point.x, f3 + f2);
                path.close();
                canvas2.drawPath(path, this.mPopBgPaint);
                for (int i5 = 0; i5 < split.length; i5++) {
                    canvas2.drawText(split[i5], (float) (max + a2), ((((float) a3) + max2) - fontMetrics.top) + (((float) i5) * f), this.mPopTextPaint);
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
                    this.mSelectedX = i4;
                    int lineIndex = getLineIndex(this.mSelectedX, i2);
                    if (lineIndex >= 0) {
                        if (this.mPopAdapter != null) {
                            this.mPopString = this.mPopAdapter.getPopString(lineIndex, i4);
                            this.mIsOverLayerShowing = !TextUtils.isEmpty(this.mPopString);
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

    public void showOverLayer(String str, int i) {
        if (this.mSelectedX == i && this.mSelectedX >= 0 && !TextUtils.isEmpty(str)) {
            this.mPopString = str;
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
        if (this.mSelectedX != -1 || "".equals(this.mPopString)) {
            this.mSelectedX = -1;
            this.mPopString = "";
            invalidate();
        }
    }

    /* access modifiers changed from: protected */
    public Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putString("popString", this.mPopString);
        bundle.putInt("selectedX", this.mSelectedX);
        bundle.putParcelable(STATE_SUPER, super.onSaveInstanceState());
        return bundle;
    }

    /* access modifiers changed from: protected */
    public void onRestoreInstanceState(Parcelable parcelable) {
        if (parcelable instanceof Bundle) {
            Bundle bundle = (Bundle) parcelable;
            this.mPopString = bundle.getString("popString", "");
            this.mSelectedX = bundle.getInt("selectedX", -1);
            super.onRestoreInstanceState(bundle.getParcelable(STATE_SUPER));
            return;
        }
        super.onRestoreInstanceState(parcelable);
    }
}
