package com.autonavi.minimap.drive.sticker;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import com.autonavi.minimap.R;
import java.util.List;

public class StickerTimeTableView extends View {
    private static int COLOR_DANGER = -33024;
    private static int COLOR_HIGH_DANGER = -1894119;
    private static int COLOR_SAFE = -15345994;
    private static int COLOR_STRICT_CONTROL = -5308416;
    private Paint axisLinePaint;
    private Paint hLinePaint;
    int mMaxIdx = 0;
    int mMaxValue = 0;
    private int mRoundWidth = 4;
    private div mSticker;
    private Paint recPaint;
    RectF rect = new RectF();
    private String[] timerList = {"0:00", "2:00", "4:00", "6:00", "8:00", "10:00", "12:00", "14:00", "16:00", "18:00", "20:00", "22:00"};
    private Paint titlePaint;
    private int[] valueList = new int[12];

    public StickerTimeTableView(Context context) {
        super(context);
        init();
    }

    public StickerTimeTableView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    private void init() {
        this.axisLinePaint = new Paint();
        this.hLinePaint = new Paint();
        this.titlePaint = new Paint();
        this.recPaint = new Paint();
        this.recPaint.setAntiAlias(true);
        Typeface create = Typeface.create(Typeface.SANS_SERIF, 0);
        this.recPaint.setTypeface(create);
        this.axisLinePaint.setColor(-1907998);
        this.hLinePaint.setColor(-3355444);
        this.titlePaint.setColor(getResources().getColor(R.color.f_c_3));
        this.titlePaint.setAntiAlias(true);
        this.titlePaint.setTypeface(create);
        for (int i = 0; i < 12; i++) {
            this.valueList[i] = 0;
        }
    }

    public void setStickerTimerInfo(div div) {
        this.mSticker = div;
        setStickerTimerInfo(div.r);
    }

    public void setStickerTimerInfo(List<a> list) {
        if (this.mSticker != null && this.mSticker.r != null && this.mSticker.r.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                a aVar = list.get(i);
                String str = aVar.a;
                if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(aVar.b)) {
                    try {
                        int parseInt = Integer.parseInt(str.substring(0, 2)) / 2;
                        int parseInt2 = Integer.parseInt(aVar.b);
                        int[] iArr = this.valueList;
                        iArr[parseInt] = iArr[parseInt] + parseInt2;
                    } catch (NumberFormatException e) {
                        kf.a((Throwable) e);
                    }
                }
            }
            for (int i2 = 0; i2 < this.valueList.length; i2++) {
                int i3 = this.valueList[i2];
                if (i3 > this.mMaxValue) {
                    this.mMaxIdx = i2;
                    this.mMaxValue = i3;
                }
            }
            postInvalidate();
        }
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        Canvas canvas2 = canvas;
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight() - 50;
        int i = height / 3;
        this.hLinePaint.setTextAlign(Align.CENTER);
        float f = (float) height;
        float f2 = (float) width;
        canvas2.drawLine(0.0f, f, f2, f, this.axisLinePaint);
        float f3 = (float) (height - i);
        Canvas canvas3 = canvas2;
        canvas3.drawLine(0.0f, f3, f2, f3, this.axisLinePaint);
        int i2 = i * 2;
        int i3 = height - i2;
        float f4 = (float) i3;
        canvas3.drawLine(0.0f, f4, f2, f4, this.axisLinePaint);
        int length = this.timerList.length;
        int i4 = width / length;
        int i5 = i4 / 8;
        this.titlePaint.setTextSize(20.0f);
        for (int i6 = 0; i6 < length; i6++) {
            float f5 = (float) ((i4 * i6) + i5);
            float f6 = (float) (height + 15);
            canvas2.rotate(45.0f, f5, f6);
            canvas2.drawText(this.timerList[i6], f5, f6, this.titlePaint);
            canvas2.rotate(-45.0f, f5, f6);
        }
        float f7 = f - 60.0f;
        float f8 = 0.0f;
        this.recPaint.setTextSize(26.0f);
        if (this.mMaxValue > 0) {
            f8 = this.mMaxValue > 4 ? f7 / ((float) this.mMaxValue) : ((float) i) / 2.0f;
            this.recPaint.setColor(getResources().getColor(R.color.f_c_3));
            StringBuilder sb = new StringBuilder();
            sb.append(this.mMaxValue);
            float measureText = this.recPaint.measureText(sb.toString());
            if (((float) this.mMaxValue) * f8 > ((float) i2)) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append(this.mMaxValue);
                canvas2.drawText(sb2.toString(), ((float) ((this.mMaxIdx * i4) + (i5 * 4))) - (measureText / 2.0f), (float) (i3 - 10), this.recPaint);
            } else {
                StringBuilder sb3 = new StringBuilder();
                sb3.append(this.mMaxValue);
                canvas2.drawText(sb3.toString(), ((float) ((this.mMaxIdx * i4) + (i5 * 4))) - (measureText / 2.0f), (f - (((float) this.mMaxValue) * f8)) - 10.0f, this.recPaint);
            }
        }
        if (this.valueList != null && this.valueList.length > 0) {
            int length2 = this.valueList.length;
            for (int i7 = 0; i7 < length2; i7++) {
                int i8 = this.valueList[i7];
                this.rect.left = (float) ((i4 * i7) + i5);
                this.rect.right = this.rect.left + ((float) (i5 * 6));
                this.rect.bottom = f;
                if (i8 == 0) {
                    if (this.mSticker == null || this.mSticker.g != 3) {
                        this.recPaint.setColor(COLOR_SAFE);
                    } else {
                        this.recPaint.setColor(COLOR_STRICT_CONTROL);
                    }
                    this.rect.top = this.rect.bottom - ((float) this.mRoundWidth);
                } else {
                    if (i8 <= 50) {
                        this.recPaint.setColor(COLOR_DANGER);
                    } else {
                        this.recPaint.setColor(COLOR_HIGH_DANGER);
                    }
                    float f9 = ((float) i8) * f8;
                    if (f9 > ((float) i2)) {
                        this.rect.top = (float) (this.mRoundWidth + i3);
                    } else {
                        this.rect.top = (f - f9) + ((float) this.mRoundWidth);
                    }
                    if (this.rect.bottom - this.rect.top < ((float) this.mRoundWidth)) {
                        this.rect.top = this.rect.bottom - ((float) this.mRoundWidth);
                    }
                }
                canvas2.drawRect(this.rect, this.recPaint);
                this.rect.top -= (float) this.mRoundWidth;
                canvas2.drawRoundRect(this.rect, (float) this.mRoundWidth, (float) this.mRoundWidth, this.recPaint);
            }
        }
    }
}
