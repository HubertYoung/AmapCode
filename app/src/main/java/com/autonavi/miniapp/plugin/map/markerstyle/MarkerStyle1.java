package com.autonavi.miniapp.plugin.map.markerstyle;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.FontMetrics;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.text.TextUtils;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.antui.utils.DensityUtil;
import com.alipay.mobile.h5container.api.H5Page;
import com.autonavi.miniapp.plugin.map.markerstyle.BaseMarkerStyle.Callback;
import com.autonavi.miniapp.plugin.map.util.H5MapUtils;
import com.autonavi.miniapp.plugin.map.util.H5MapUtils.ImgCallback;

class MarkerStyle1 extends BaseMarkerStyle {
    private int mBubbleTailSize;
    private int mBubbleWidth;
    private String mIcon1;
    private int mIcon1Size;
    private String mIcon2;
    private int mIcon2Size;
    /* access modifiers changed from: private */
    public int mLaunch;
    private int mLaunchCount;
    private int mLeftRightMargin = 0;
    private int mLeftRightPadding;
    private String mText1;
    private int mTextLeftMargin;
    private Paint mTextPaint;
    private int mTextSize;
    private int mTopBottomPadding;

    public MarkerStyle1(H5Page h5Page, Context context) {
        super(h5Page, context);
        this.mLeftRightPadding = DensityUtil.dip2px(context, 12.0f);
        this.mTopBottomPadding = DensityUtil.dip2px(context, 5.0f);
        this.mTextLeftMargin = DensityUtil.dip2px(context, 4.0f);
        this.mIcon1Size = DensityUtil.dip2px(context, 14.0f);
        this.mIcon2Size = DensityUtil.dip2px(context, 14.0f);
        this.mTextSize = DensityUtil.dip2px(context, 12.0f);
        this.mBubbleTailSize = DensityUtil.dip2px(context, 4.0f);
        this.mTextPaint = new Paint();
        this.mTextPaint.setTextSize((float) this.mTextSize);
        this.mTextPaint.setColor(-12303292);
        this.mTextPaint.setTextAlign(Align.LEFT);
        this.mTextPaint.setTypeface(Typeface.create(Typeface.SANS_SERIF, 0));
        this.mTextPaint.setAntiAlias(true);
        this.mTextPaint.setStrokeWidth((float) DensityUtil.dip2px(context, 1.5f));
    }

    /* access modifiers changed from: 0000 */
    public BaseMarkerStyle bindData(JSONObject jSONObject) {
        this.mIcon1 = jSONObject.getString("icon1");
        this.mText1 = jSONObject.getString("text1");
        this.mIcon2 = jSONObject.getString("icon2");
        if (((Context) this.mContext.get()) != null) {
            if (TextUtils.isEmpty(this.mIcon2)) {
                this.mLeftRightMargin = 0;
            } else {
                this.mLeftRightMargin = this.mIcon2Size / 2;
            }
        }
        return this;
    }

    /* access modifiers changed from: 0000 */
    public boolean measure() {
        Rect rect = new Rect();
        this.mTextPaint.getTextBounds(this.mText1, 0, this.mText1.length(), rect);
        this.mMeasuredWidth = (this.mLeftRightMargin * 2) + (this.mLeftRightPadding * 2) + this.mIcon1Size + rect.width() + this.mTextLeftMargin;
        this.mMeasuredHeight = (this.mTopBottomPadding * 2) + this.mIcon1Size + this.mBubbleTailSize;
        this.mBubbleWidth = this.mMeasuredWidth - (this.mLeftRightMargin * 2);
        return true;
    }

    /* access modifiers changed from: 0000 */
    public void getBitmapImpl(final Callback callback) {
        this.mLaunchCount = 0;
        this.mLaunchCount += TextUtils.isEmpty(this.mIcon1) ^ true ? 1 : 0;
        this.mLaunchCount += TextUtils.isEmpty(this.mIcon2) ^ true ? 1 : 0;
        final Bitmap createBitmap = Bitmap.createBitmap(this.mMeasuredWidth, this.mMeasuredHeight, Config.ARGB_8888);
        final Canvas canvas = new Canvas(createBitmap);
        drawBubble(canvas);
        drawText(canvas);
        if (this.mLaunchCount == 0) {
            checkAndCallback(callback, createBitmap);
            return;
        }
        if (!TextUtils.isEmpty(this.mIcon1)) {
            H5MapUtils.getImgFromPkg((H5Page) this.mPage.get(), this.mIcon1, new ImgCallback() {
                public void onLoadImage(Bitmap bitmap) {
                    MarkerStyle1.this.mLaunch = MarkerStyle1.this.mLaunch + 1;
                    if (bitmap != null) {
                        MarkerStyle1.this.drawIcon1(canvas, bitmap);
                    }
                    MarkerStyle1.this.checkAndCallback(callback, createBitmap);
                }
            });
        }
        if (!TextUtils.isEmpty(this.mIcon2)) {
            H5MapUtils.getImgFromPkg((H5Page) this.mPage.get(), this.mIcon2, new ImgCallback() {
                public void onLoadImage(Bitmap bitmap) {
                    MarkerStyle1.this.mLaunch = MarkerStyle1.this.mLaunch + 1;
                    if (bitmap != null) {
                        MarkerStyle1.this.drawIcon2(canvas, bitmap);
                    }
                    MarkerStyle1.this.checkAndCallback(callback, createBitmap);
                }
            });
        }
    }

    /* access modifiers changed from: private */
    public void checkAndCallback(Callback callback, Bitmap bitmap) {
        if (this.mLaunchCount == this.mLaunch) {
            callback.call(bitmap, 0);
        }
    }

    /* access modifiers changed from: private */
    public void drawIcon1(Canvas canvas, Bitmap bitmap) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        canvas.save();
        canvas.translate((float) (this.mLeftRightMargin + this.mLeftRightPadding), (float) this.mTopBottomPadding);
        canvas.drawBitmap(bitmap, getMatrix(new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight()), new Rect(0, 0, this.mIcon1Size, this.mIcon1Size)), paint);
        canvas.restore();
    }

    /* access modifiers changed from: private */
    public void drawIcon2(Canvas canvas, Bitmap bitmap) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        canvas.save();
        canvas.translate(0.0f, (float) (((this.mMeasuredHeight - this.mBubbleTailSize) - this.mIcon2Size) / 2));
        canvas.drawBitmap(bitmap, getMatrix(new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight()), new Rect(0, 0, this.mIcon1Size, this.mIcon1Size)), paint);
        canvas.restore();
    }

    private void drawText(Canvas canvas) {
        FontMetrics fontMetrics = this.mTextPaint.getFontMetrics();
        canvas.drawText(this.mText1, (float) (this.mLeftRightMargin + this.mLeftRightPadding + this.mIcon1Size + this.mTextLeftMargin), (float) ((int) ((((float) ((this.mMeasuredHeight - this.mBubbleTailSize) / 2)) - (fontMetrics.top / 2.0f)) - (fontMetrics.bottom / 2.0f))), this.mTextPaint);
    }

    private void drawBubble(Canvas canvas) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(-263173);
        paint.setAlpha(255);
        RectF rectF = new RectF();
        rectF.set(0.0f, 0.0f, (float) this.mBubbleWidth, (float) (this.mMeasuredHeight - this.mBubbleTailSize));
        int save = canvas.save();
        canvas.translate((float) this.mLeftRightMargin, 0.0f);
        canvas.drawRoundRect(rectF, rectF.height() / 2.0f, rectF.height() / 2.0f, paint);
        int i = this.mBubbleWidth / 2;
        int sqrt = this.mMeasuredHeight - ((int) (((double) this.mBubbleTailSize) * Math.sqrt(2.0d)));
        rectF.set((float) (i - this.mBubbleTailSize), (float) (sqrt - this.mBubbleTailSize), (float) (i + this.mBubbleTailSize), (float) (sqrt + this.mBubbleTailSize));
        int save2 = canvas.save();
        canvas.rotate(45.0f, rectF.centerX(), rectF.centerY());
        canvas.drawRoundRect(rectF, 2.0f, 2.0f, paint);
        canvas.restoreToCount(save2);
        canvas.restoreToCount(save);
    }
}
