package com.alipay.mobile.antui.iconfont;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint.Align;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.TextPaint;
import android.text.TextUtils;
import com.alipay.mobile.antui.iconfont.manager.TypefaceCache;
import com.alipay.mobile.antui.iconfont.model.IconPaintBuilder;
import com.alipay.mobile.antui.utils.AuiLogger;
import com.alipay.mobile.antui.utils.DensityUtil;
import com.alipay.mobile.antui.utils.ResourceUtils;
import com.alipay.mobile.common.share.widget.ResUtils;
import java.io.File;

public class AUIconDrawable extends Drawable {
    private final String ICONFONT_SCHEMA = "iconfont://";
    private final String TAG = "AUIconDrawable";
    private int size = 0;
    private String text = "";
    private final TextPaint textPaint = new TextPaint(1);

    public AUIconDrawable(Context context, String code) {
        if (code.startsWith("iconfont://")) {
            try {
                Uri uri = Uri.parse(code);
                String id = uri.getQueryParameter("id");
                String iconSize = uri.getQueryParameter("size");
                String iconColor = uri.getQueryParameter("color");
                init(context, new IconPaintBuilder(Color.parseColor(iconColor), DensityUtil.dip2px(context, Float.valueOf(iconSize).floatValue()), id));
            } catch (Throwable t) {
                AuiLogger.error("AUIconDrawable", t.toString());
            }
        }
    }

    public AUIconDrawable(Context context, IconPaintBuilder builder) {
        init(context, builder);
    }

    private void init(Context context, IconPaintBuilder builder) {
        if (builder != null) {
            if (builder.resId == 0 && !TextUtils.isEmpty(builder.resString)) {
                builder.resId = ResourceUtils.getResourceId(context, builder.resString, ResUtils.STRING);
            }
            this.size = builder.size;
            this.text = context.getResources().getString(builder.resId);
            this.textPaint.setColor(builder.color);
            this.textPaint.setAntiAlias(true);
            this.textPaint.setFakeBoldText(builder.isBold);
            this.textPaint.setTypeface(TypefaceCache.getTypeface(context, getIconfontBundle(), getTTFFilePath()));
            this.textPaint.setTextAlign(Align.CENTER);
        }
    }

    public void draw(Canvas canvas) {
        Rect r = getBounds();
        int width = this.size < 0 ? r.width() : this.size;
        int height = this.size < 0 ? r.height() : this.size;
        this.textPaint.setTextSize((float) width);
        canvas.drawText(this.text, (float) (width / 2), ((float) (height / 2)) - ((this.textPaint.descent() + this.textPaint.ascent()) / 2.0f), this.textPaint);
    }

    public void setAlpha(int alpha) {
        this.textPaint.setAlpha(alpha);
    }

    public void setColorFilter(ColorFilter colorFilter) {
        this.textPaint.setColorFilter(colorFilter);
    }

    public int getOpacity() {
        return -3;
    }

    public int getIntrinsicWidth() {
        return this.size;
    }

    public int getIntrinsicHeight() {
        return this.size;
    }

    private String getTTFFilePath() {
        return getIconfontBundle() + File.separator + "default.ttf";
    }

    private String getIconfontBundle() {
        return "default";
    }
}
