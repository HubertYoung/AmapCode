package com.autonavi.minimap.route.foot.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.support.v4.internal.view.SupportMenu;
import android.util.AttributeSet;
import android.widget.ImageView;
import com.autonavi.minimap.R;
import java.util.HashMap;

@SuppressLint({"AppCompatCustomView"})
public class Compass extends ImageView {
    private static final int DEAFULT_ARC_GAP = 4;
    private static final int DEFAULT_ARC_RADIUS = 3;
    private static final int DEFAULT_BIG_MARKER_HEIGHT = 15;
    private static final int DEFAULT_BIG_MARKER_WIDTH = 2;
    private static final int DEFAULT_COMPASS_TEXT_GAP = 30;
    private static final int DEFAULT_LINE_GAP = 10;
    private static final int DEFAULT_LINE_WIDTH = 1;
    private static final int DEFAULT_SMALL_MARKER_HEIGHT = 10;
    private static final int DEFAULT_SMALL_MARKER_WIDTH = 1;
    private static int textSize = 20;
    private float absSweepAngel;
    private int arcDistance;
    private Paint arcPaint;
    private int arcRadius;
    private Paint bigColorPaint;
    private Paint bigMarkerGreyPaint;
    private Paint bigMarkerPaint;
    private int bigMarkerStart;
    private Paint bitmapPaint;
    private int bmpX;
    private int bmpY;
    private int colorLine;
    private int colorMarkerBig;
    private int colorMarkerGreen;
    private int colorMarkerOrange;
    private int colorMarkerRed;
    private int colorMarkerSmall;
    private int compassRadius;
    private float compassRadiusGap;
    private float currentPoint;
    private float density;
    private float eastX;
    private float eastY;
    private boolean hasFindRightDirection = false;
    private float lbx;
    private float lby;
    private float lineLength;
    private Paint linePaint;
    private float lineY;
    private float lsx;
    private float lsy;
    private float mDirection;
    private a mListener;
    private float northX;
    private float northY;
    private RectF oval;
    private float pi;
    private Bitmap pointerBmp;
    private int px;
    private int py;
    private HashMap<Float, float[]> scFloatBuffer;
    private Paint smallColorPaint;
    private Paint smallMarkerPaint;
    private int smallMarkerStart;
    private float southX;
    private float southY;
    private float sweepAngel;
    private int sweepIntMax;
    private int sweepIntMin;
    private float targetDirection;
    private Paint textPaint;
    private float textRadius;
    private HashMap<Float, float[]> textXYBuffer;
    private float westX;
    private float westY;

    public interface a {
        void onFindRightDirection();
    }

    public Compass(Context context) {
        super(context);
        init();
    }

    public Compass(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    public Compass(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init();
    }

    private void init() {
        this.density = getResources().getDisplayMetrics().density;
        this.scFloatBuffer = new HashMap<>();
        this.textXYBuffer = new HashMap<>();
        this.pointerBmp = BitmapFactory.decodeResource(getResources(), R.drawable.compass_pointer);
        this.bmpX = this.pointerBmp.getWidth();
        this.bmpY = this.pointerBmp.getHeight();
        this.mDirection = 0.0f;
        this.hasFindRightDirection = false;
        this.colorMarkerRed = getResources().getColor(R.color.compass_marker_red);
        this.colorMarkerBig = -16777216;
        this.colorMarkerSmall = getResources().getColor(R.color.compass_marker_grey);
        this.colorLine = getResources().getColor(R.color.compass_pointer);
        this.colorMarkerGreen = getResources().getColor(R.color.compass_marker_green);
        this.colorMarkerOrange = getResources().getColor(R.color.compass_marker_orange);
        this.arcRadius = (int) (this.density * 3.0f);
        this.arcPaint = new Paint();
        this.arcPaint.setColor(SupportMenu.CATEGORY_MASK);
        this.arcPaint.setAntiAlias(true);
        this.arcPaint.setStyle(Style.STROKE);
        this.arcPaint.setStrokeWidth((float) this.arcRadius);
        this.textPaint = new Paint();
        this.textPaint.setTextSize(this.density * ((float) textSize));
        this.textPaint.setAntiAlias(true);
        this.textPaint.setFakeBoldText(true);
        this.textPaint.setColor(this.colorMarkerBig);
        this.linePaint = new Paint();
        this.linePaint.setColor(this.colorLine);
        this.linePaint.setAntiAlias(true);
        this.linePaint.setStrokeWidth(this.density * 1.0f);
        this.bigMarkerPaint = new Paint();
        this.bigMarkerPaint.setColor(this.colorMarkerBig);
        this.bigMarkerPaint.setAntiAlias(true);
        this.bigMarkerPaint.setFakeBoldText(true);
        this.bigMarkerPaint.setStrokeWidth(this.density * 2.0f);
        this.bigMarkerGreyPaint = new Paint();
        this.bigMarkerGreyPaint.setColor(this.colorMarkerSmall);
        this.bigMarkerGreyPaint.setAntiAlias(true);
        this.bigMarkerGreyPaint.setFakeBoldText(true);
        this.bigMarkerGreyPaint.setStrokeWidth(this.density * 2.0f);
        this.bigColorPaint = new Paint();
        this.bigColorPaint.setColor(this.colorMarkerRed);
        this.bigColorPaint.setFakeBoldText(true);
        this.bigColorPaint.setAntiAlias(true);
        this.bigColorPaint.setStrokeWidth(this.density * 2.0f);
        this.smallMarkerPaint = new Paint();
        this.smallMarkerPaint.setColor(this.colorMarkerSmall);
        this.smallMarkerPaint.setAntiAlias(true);
        this.smallMarkerPaint.setStrokeWidth(this.density * 1.0f);
        this.smallColorPaint = new Paint();
        this.smallColorPaint.setAntiAlias(true);
        this.smallColorPaint.setColor(this.colorMarkerRed);
        this.smallColorPaint.setStrokeWidth(this.density * 1.0f);
        this.bitmapPaint = new Paint(1);
        this.bitmapPaint.setFilterBitmap(true);
        this.bitmapPaint.setDither(true);
        setBackgroundResource(R.drawable.compass_bg);
    }

    public void clearCache() {
        if (this.scFloatBuffer != null) {
            this.scFloatBuffer.clear();
            this.scFloatBuffer = null;
        }
        if (this.textXYBuffer != null) {
            this.textXYBuffer.clear();
            this.textXYBuffer = null;
        }
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        if (this.px == 0) {
            this.px = getWidth() / 2;
            this.py = getHeight() / 2;
            this.compassRadiusGap = ((float) getWidth()) * 0.13f;
            this.compassRadius = (int) (((float) this.px) - this.compassRadiusGap);
            this.arcDistance = (int) (((float) (this.px - this.compassRadius)) - (this.density * 4.0f));
            this.oval = new RectF((float) this.arcDistance, (float) this.arcDistance, (float) (getWidth() - this.arcDistance), (float) (getHeight() - this.arcDistance));
            this.textRadius = ((float) this.compassRadius) - (this.density * 30.0f);
            this.lineLength = (((float) (this.compassRadius - (this.bmpY / 2))) - ((this.density * 10.0f) * 3.0f)) - (this.density * 15.0f);
            this.bmpX = this.px - (this.bmpX / 2);
            this.bmpY = this.py - (this.bmpY / 2);
            this.lineY = ((float) this.bmpY) - (this.density * 10.0f);
            this.bigMarkerStart = (int) (((float) this.compassRadius) - (this.density * 15.0f));
            this.smallMarkerStart = (int) (((float) this.compassRadius) - (this.density * 10.0f));
            this.pi = 0.017453292f;
        }
        this.currentPoint = 360.0f - this.mDirection;
        this.sweepAngel = this.targetDirection - this.currentPoint;
        if (Math.abs(this.sweepAngel) <= 3.0f && this.mListener != null && !this.hasFindRightDirection) {
            StringBuilder sb = new StringBuilder("mD = ");
            sb.append(this.mDirection);
            sb.append("    T =");
            sb.append(this.targetDirection);
            eau.a("yangqiang", sb.toString());
            this.mListener.onFindRightDirection();
            this.hasFindRightDirection = true;
        }
        if (Math.abs(this.sweepAngel) > 180.0f) {
            if (this.sweepAngel > 0.0f) {
                this.sweepAngel = 360.0f - this.sweepAngel;
            } else {
                this.sweepAngel += 360.0f;
            }
            if (this.targetDirection > 180.0f && this.sweepAngel > 0.0f) {
                this.sweepAngel *= -1.0f;
            }
        }
        this.absSweepAngel = Math.abs(this.sweepAngel);
        if (this.currentPoint > 180.0f) {
            this.sweepIntMax = ((int) (this.currentPoint - 360.0f)) / 3;
        } else {
            this.sweepIntMax = ((int) this.currentPoint) / 3;
        }
        if (this.targetDirection > 180.0f) {
            this.sweepIntMin = ((int) (this.targetDirection - 360.0f)) / 3;
        } else {
            this.sweepIntMin = ((int) this.targetDirection) / 3;
        }
        boolean z = false;
        if (Math.abs(this.sweepIntMax - this.sweepIntMin) > 60) {
            z = true;
        }
        swap();
        drawCompass(canvas, z);
        canvas.drawArc(this.oval, -90.0f, this.sweepAngel, false, this.arcPaint);
        canvas.drawBitmap(this.pointerBmp, (float) this.bmpX, (float) this.bmpY, this.bitmapPaint);
        canvas.drawLine((float) this.px, this.lineY, (float) this.px, this.lineY - this.lineLength, this.linePaint);
        drawDirectionText(canvas);
    }

    private void drawCompass(Canvas canvas, boolean z) {
        float f;
        float f2;
        if (this.scFloatBuffer == null || !this.scFloatBuffer.containsKey(Float.valueOf(this.mDirection))) {
            f2 = (float) Math.sin((double) (Math.abs(this.mDirection + 90.0f + 360.0f) * this.pi));
            f = (float) Math.cos((double) (Math.abs(this.mDirection + 90.0f + 360.0f) * this.pi));
            float[] fArr = {f2, f};
            if (this.scFloatBuffer == null) {
                this.scFloatBuffer = new HashMap<>();
            }
            this.scFloatBuffer.put(Float.valueOf(this.mDirection), fArr);
            eau.a("yangqiang", "sc 命中");
        } else {
            float[] fArr2 = this.scFloatBuffer.get(Float.valueOf(this.mDirection));
            float f3 = fArr2[0];
            f = fArr2[1];
            f2 = f3;
        }
        for (int i = -60; i < 60; i++) {
            if (i % 10 == 0) {
                this.lbx = ((float) this.px) + (((float) this.bigMarkerStart) * f);
                this.lby = ((float) this.px) + (((float) this.bigMarkerStart) * f2);
                this.lsx = ((float) this.px) + (((float) this.compassRadius) * f);
                this.lsy = ((float) this.py) + (((float) this.compassRadius) * f2);
                if (this.absSweepAngel <= 30.0f) {
                    this.bigColorPaint.setColor(this.colorMarkerGreen);
                    this.smallColorPaint.setColor(this.colorMarkerGreen);
                    this.arcPaint.setColor(this.colorMarkerGreen);
                } else if (this.absSweepAngel > 80.0f || this.absSweepAngel <= 30.0f) {
                    this.bigColorPaint.setColor(this.colorMarkerRed);
                    this.smallColorPaint.setColor(this.colorMarkerRed);
                    this.arcPaint.setColor(this.colorMarkerRed);
                } else {
                    this.bigColorPaint.setColor(this.colorMarkerOrange);
                    this.smallColorPaint.setColor(this.colorMarkerOrange);
                    this.arcPaint.setColor(this.colorMarkerOrange);
                }
                if (i > this.sweepIntMax || i < this.sweepIntMin) {
                    float f4 = this.lbx;
                    float f5 = this.lby;
                    float f6 = this.lsx;
                    float f7 = this.lsy;
                    Paint paint = z ? this.bigColorPaint : i % 30 == 0 ? this.bigMarkerPaint : this.bigMarkerGreyPaint;
                    canvas.drawLine(f4, f5, f6, f7, paint);
                } else {
                    float f8 = this.lbx;
                    float f9 = this.lby;
                    float f10 = this.lsx;
                    float f11 = this.lsy;
                    Paint paint2 = z ? i % 30 == 0 ? this.bigMarkerPaint : this.bigMarkerGreyPaint : this.bigColorPaint;
                    canvas.drawLine(f8, f9, f10, f11, paint2);
                }
                canvas.save();
                canvas.restore();
            } else {
                this.lbx = ((float) this.px) + (((float) this.smallMarkerStart) * f);
                this.lby = ((float) this.px) + (((float) this.smallMarkerStart) * f2);
                this.lsx = ((float) this.px) + (((float) this.compassRadius) * f);
                this.lsy = ((float) this.py) + (((float) this.compassRadius) * f2);
                if (i > this.sweepIntMax || i < this.sweepIntMin) {
                    canvas.drawLine(this.lbx, this.lby, this.lsx, this.lsy, z ? this.smallColorPaint : this.smallMarkerPaint);
                } else {
                    canvas.drawLine(this.lbx, this.lby, this.lsx, this.lsy, z ? this.smallMarkerPaint : this.smallColorPaint);
                }
                canvas.save();
                canvas.restore();
            }
            canvas.rotate(3.0f, (float) this.px, (float) this.py);
        }
    }

    private void swap() {
        if (this.sweepIntMax < this.sweepIntMin) {
            int i = this.sweepIntMax;
            this.sweepIntMax = this.sweepIntMin;
            this.sweepIntMin = i;
        }
    }

    public void updateDirection(float f) {
        this.mDirection = f;
        invalidate();
    }

    public void setTargetDirection(float f) {
        this.currentPoint = 0.0f;
        this.targetDirection = f;
        this.hasFindRightDirection = false;
    }

    public void setOnFindRightDirectionListener(a aVar) {
        this.mListener = aVar;
    }

    private void drawDirectionText(Canvas canvas) {
        Canvas canvas2 = canvas;
        if (this.textXYBuffer == null || !this.textXYBuffer.containsKey(Float.valueOf(this.mDirection))) {
            this.eastX = (float) ((((double) this.px) + (((double) this.textRadius) * Math.cos((double) (Math.abs(this.mDirection) * this.pi)))) - ((double) textSize));
            this.eastY = (float) (((double) this.py) + (((double) this.textRadius) * Math.sin((double) (Math.abs(this.mDirection) * this.pi))) + ((double) textSize));
            this.southX = (float) ((((double) this.px) + (((double) this.textRadius) * Math.cos((double) (Math.abs((this.mDirection + 90.0f) + 360.0f) * this.pi)))) - ((double) textSize));
            this.southY = (float) (((double) this.py) + (((double) this.textRadius) * Math.sin((double) (Math.abs(this.mDirection + 90.0f + 360.0f) * this.pi))) + ((double) textSize));
            this.westX = (float) ((((double) this.px) + (((double) this.textRadius) * Math.cos((double) (Math.abs((this.mDirection + 180.0f) + 360.0f) * this.pi)))) - ((double) textSize));
            this.westY = (float) (((double) this.py) + (((double) this.textRadius) * Math.sin((double) (Math.abs(this.mDirection + 180.0f + 360.0f) * this.pi))) + ((double) textSize));
            this.northX = (float) ((((double) this.px) + (((double) this.textRadius) * Math.cos((double) (Math.abs((this.mDirection + 270.0f) + 360.0f) * this.pi)))) - ((double) textSize));
            this.northY = (float) (((double) this.py) + (((double) this.textRadius) * Math.sin((double) (Math.abs(this.mDirection + 270.0f + 360.0f) * this.pi))) + ((double) textSize));
            float[] fArr = {this.eastX, this.eastY, this.southX, this.southY, this.westX, this.westY, this.northX, this.northY};
            if (this.textXYBuffer == null) {
                this.textXYBuffer = new HashMap<>();
            }
            this.textXYBuffer.put(Float.valueOf(this.mDirection), fArr);
            eau.a("yangqiang", "text 命中");
        } else {
            float[] fArr2 = this.textXYBuffer.get(Float.valueOf(this.mDirection));
            this.eastX = fArr2[0];
            this.eastY = fArr2[1];
            this.southX = fArr2[2];
            this.southY = fArr2[3];
            this.westX = fArr2[4];
            this.westY = fArr2[5];
            this.northX = fArr2[6];
            this.northY = fArr2[7];
        }
        canvas2.drawText("南", this.southX, this.southY, this.textPaint);
        canvas2.drawText("西", this.westX, this.westY, this.textPaint);
        canvas2.drawText("北", this.northX, this.northY, this.textPaint);
        canvas2.drawText("东", this.eastX, this.eastY, this.textPaint);
    }
}
