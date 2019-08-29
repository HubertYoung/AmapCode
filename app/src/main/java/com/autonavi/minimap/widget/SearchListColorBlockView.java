package com.autonavi.minimap.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import com.alipay.mobile.security.bio.common.record.MetaRecord;

public class SearchListColorBlockView extends View {
    private static final String DEFAULT_PAINT_STYLE = "0";
    private static final String DEFAULT_TEXT_COLOR = "#999999";
    private static final String TEXT_COLOR = "";
    private int mBorder = 1;
    private int mBottomPd = 10;
    private ItemInfo mItemInfo = new ItemInfo();
    private int mLayoutMargin;
    private int mLeftPd = 26;
    private int mMargin = 16;
    private int mRightPd = 26;
    private int mTTextL;
    private int mTextSize = 30;
    private int mTopPadding = 10;
    private int topPadding = 2;

    public static class ColorBlock {
        public int mColor = Color.parseColor("#CCCCCC");
        public Style mPaintStyle = Style.FILL;
        public String mText;
        public int mTextColor = Color.parseColor("#ffffff");
    }

    public static class ItemInfo {
        /* access modifiers changed from: private */
        public ColorBlock[] mColorBlocks = new ColorBlock[0];
        /* access modifiers changed from: private */
        public int mMoreTextColor;

        public ItemInfo setBlockColor(int i) {
            this.mMoreTextColor = i;
            return this;
        }

        public ItemInfo setBlocks(ColorBlock[] colorBlockArr) {
            this.mColorBlocks = colorBlockArr;
            return this;
        }

        public boolean isEmpty() {
            return this.mColorBlocks == null || this.mColorBlocks.length == 0;
        }

        public static ItemInfo parse(String str, boolean z) {
            if (TextUtils.isEmpty(str)) {
                return null;
            }
            String[] split = str.split(";");
            ColorBlock[] colorBlockArr = new ColorBlock[split.length];
            for (int i = 0; i < split.length; i++) {
                String[] split2 = split[i].split("\\|");
                int length = split2.length;
                if (length != 0) {
                    ColorBlock colorBlock = new ColorBlock();
                    if (!TextUtils.isEmpty(split2[0])) {
                        colorBlock.mText = split2[0];
                    }
                    if (length > 1) {
                        colorBlock.mColor = SearchListColorBlockView.safeParseColor(split2[1], SearchListColorBlockView.DEFAULT_TEXT_COLOR);
                    } else {
                        colorBlock.mColor = SearchListColorBlockView.safeParseColor(SearchListColorBlockView.DEFAULT_TEXT_COLOR, SearchListColorBlockView.DEFAULT_TEXT_COLOR);
                    }
                    if (z) {
                        colorBlock.mPaintStyle = Style.FILL;
                        colorBlock.mTextColor = Color.parseColor("#ffffff");
                    } else if (length != 3 || split2[2] == null) {
                        colorBlock.mPaintStyle = Style.STROKE;
                        colorBlock.mTextColor = Color.parseColor("#757575");
                    } else if ("0".equals(split2[2])) {
                        colorBlock.mPaintStyle = Style.FILL;
                        colorBlock.mTextColor = Color.parseColor("#ffffff");
                    } else {
                        colorBlock.mPaintStyle = Style.STROKE;
                        colorBlock.mTextColor = SearchListColorBlockView.safeParseColor(split2[1], "#757575");
                    }
                    colorBlockArr[i] = colorBlock;
                }
            }
            ItemInfo itemInfo = new ItemInfo();
            itemInfo.setBlocks(colorBlockArr).setBlockColor(colorBlockArr[colorBlockArr.length - 1].mColor);
            return itemInfo;
        }
    }

    public SearchListColorBlockView(Context context) {
        super(context);
        init(context);
    }

    public SearchListColorBlockView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context);
    }

    public SearchListColorBlockView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(context);
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        this.mTTextL = 0;
        drawTexts(canvas);
        super.onDraw(canvas);
    }

    private void init(Context context) {
        this.mMargin = agn.a(context, 4.0f);
        this.mBottomPd = agn.a(context, 5.0f);
        this.mTopPadding = agn.a(context, 1.0f);
        this.mRightPd = agn.a(context, 4.0f);
        this.mLeftPd = agn.a(context, 2.0f);
        this.topPadding = agn.a(context, 1.0f);
        this.mTextSize = agn.a(context, 10.0f);
        this.mLayoutMargin = agn.a(context, 10.0f);
    }

    private void drawTexts(Canvas canvas) {
        int i;
        Canvas canvas2 = canvas;
        ColorBlock[] a = this.mItemInfo.mColorBlocks;
        Paint paint = new Paint();
        paint.setTextSize((float) this.mTextSize);
        paint.setStrokeWidth((float) this.mBorder);
        int i2 = 1;
        paint.setAntiAlias(true);
        TextPaint textPaint = new TextPaint();
        textPaint.setAntiAlias(true);
        textPaint.setTextSize((float) this.mTextSize);
        RectF rectF = new RectF(0.0f, 0.0f, 50.0f, 50.0f);
        int i3 = 0;
        while (i3 < a.length) {
            ColorBlock colorBlock = a[i3];
            float[] fArr = new float[colorBlock.mText.length()];
            textPaint.getTextWidths(colorBlock.mText, fArr);
            textPaint.setColor(colorBlock.mTextColor);
            int i4 = 0;
            for (float f : fArr) {
                i4 += (int) f;
            }
            paint.setColor(colorBlock.mColor);
            paint.setStyle(colorBlock.mPaintStyle);
            TextPaint textPaint2 = new TextPaint(33);
            textPaint2.setColor(this.mItemInfo.mMoreTextColor);
            textPaint2.setTextSize((float) (this.mTextSize + 10));
            float[] fArr2 = new float[i2];
            textPaint2.getTextWidths(".", fArr2);
            if (i3 == 0) {
                i = 1;
            } else {
                i = this.mMargin;
            }
            rectF.set((float) (this.mTTextL + i), (float) this.topPadding, (float) (this.mTTextL + i4 + i + this.mRightPd), (float) (this.topPadding + this.mTextSize + this.mBottomPd));
            float f2 = (float) i;
            if (((int) (((float) this.mTTextL) + rectF.width() + f2 + (fArr2[0] * 3.0f))) + this.mLayoutMargin > getWidth()) {
                canvas2.drawText("...", (float) (this.mTTextL + i + this.mLeftPd), (float) (this.mTextSize + this.topPadding + this.mTopPadding), textPaint2);
                return;
            }
            canvas2.drawRoundRect(rectF, 5.0f, 5.0f, paint);
            canvas2.drawText(colorBlock.mText, (float) (this.mTTextL + i + this.mLeftPd), (float) (this.mTextSize + this.topPadding + this.mTopPadding), textPaint);
            this.mTTextL = (int) (((float) this.mTTextL) + rectF.width() + f2);
            i3++;
            i2 = 1;
        }
    }

    public void setItemInfo(ItemInfo itemInfo) {
        this.mItemInfo = itemInfo;
        invalidate();
    }

    /* access modifiers changed from: private */
    public static int safeParseColor(String str, String str2) {
        int parseColor = Color.parseColor(str2);
        if (TextUtils.isEmpty(str)) {
            return parseColor;
        }
        try {
            if (!str.startsWith(MetaRecord.LOG_SEPARATOR)) {
                str = MetaRecord.LOG_SEPARATOR.concat(String.valueOf(str));
            }
            return Color.parseColor(str);
        } catch (Throwable unused) {
            return parseColor;
        }
    }
}
