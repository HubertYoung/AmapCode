package com.alipay.mobile.beehive.imageedit.views;

import android.content.Context;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.text.TextUtils;
import android.util.Log;
import com.alipay.mobile.beehive.R;
import com.alipay.mobile.beehive.imageedit.utils.ImageEditLogger;

public enum DoodleEffect {
    UNDO(true, false, R.drawable.sel_undo, "", R.string.tb_undo),
    RESET(true, false, R.drawable.sel_reset, "", R.string.tb_reset),
    ERASER(false, false, R.drawable.ic_undo, "", R.string.tb_eraser),
    MOSAIC(false, false, R.drawable.ic_mosaic, "", R.string.tb_mosaic),
    COLOR_RED(R.drawable.paint_red, "#FF3011", R.string.tb_paint_red),
    COLOR_ORANGE(R.drawable.paint_orange, "#FF7E20", R.string.tb_paint_orange),
    COLOR_YELLOW(R.drawable.paint_yellow, "#FFE200", R.string.tb_paint_yellow),
    COLOR_GREEN(R.drawable.paint_green, "#82EA00", R.string.tb_paint_green),
    COLOR_BLUE(R.drawable.paint_blue, "#008FFC", R.string.tb_paint_blue),
    COLOR_PURPLE(R.drawable.paint_purple, "#B800D8", R.string.tb_paint_purple);
    
    private static final String TAG = "DoodleEffect";
    public int color;
    public boolean isEnabled;
    public boolean isSelected;
    public boolean isShow;
    public Paint paint;
    public int resId;
    public int tackBackId;

    private DoodleEffect(int resId2, String colorStr, int talkBackId) {
        this(r9, r10, true, true, resId2, colorStr, talkBackId);
    }

    private DoodleEffect(boolean isShow2, boolean isEnabled2, int resId2, String colorStr, int talkBackId) {
        this.isShow = isShow2;
        this.isEnabled = isEnabled2;
        this.tackBackId = talkBackId;
        this.resId = resId2;
        if (!TextUtils.isEmpty(colorStr)) {
            this.color = Color.parseColor(colorStr);
        }
    }

    public final void setPaint(Paint paint2) {
        this.paint = paint2;
    }

    public static void init(Context context) {
        Log.d(TAG, "Init paints");
        Paint eraser = new Paint();
        eraser.setXfermode(new PorterDuffXfermode(Mode.CLEAR));
        eraser.setAntiAlias(true);
        eraser.setStrokeWidth(context.getResources().getDimension(R.dimen.stroke_width_eraser));
        eraser.setStyle(Style.STROKE);
        eraser.setPathEffect(new CornerPathEffect(context.getResources().getDimension(R.dimen.corner_path_width)));
        ERASER.setPaint(eraser);
        Paint mosaic = new Paint();
        mosaic.setAntiAlias(true);
        mosaic.setStrokeWidth(context.getResources().getDimension(R.dimen.stroke_width_mosaic));
        mosaic.setStyle(Style.STROKE);
        mosaic.setColor(-16777216);
        mosaic.setPathEffect(new CornerPathEffect(context.getResources().getDimension(R.dimen.corner_path_width)));
        MOSAIC.setPaint(mosaic);
        COLOR_RED.setPaint(genColorPaint(context, COLOR_RED.color));
        COLOR_YELLOW.setPaint(genColorPaint(context, COLOR_YELLOW.color));
        COLOR_ORANGE.setPaint(genColorPaint(context, COLOR_ORANGE.color));
        COLOR_GREEN.setPaint(genColorPaint(context, COLOR_GREEN.color));
        COLOR_BLUE.setPaint(genColorPaint(context, COLOR_BLUE.color));
        COLOR_PURPLE.setPaint(genColorPaint(context, COLOR_PURPLE.color));
    }

    public static void release() {
        ImageEditLogger.debug(TAG, "Release paints");
        for (DoodleEffect paint2 : values()) {
            paint2.setPaint(null);
        }
    }

    private static Paint genColorPaint(Context context, int color2) {
        Paint p = new Paint();
        p.setAntiAlias(true);
        p.setColor(color2);
        p.setStrokeWidth(context.getResources().getDimension(R.dimen.stroke_width_paint));
        p.setAntiAlias(true);
        p.setStyle(Style.STROKE);
        p.setPathEffect(new CornerPathEffect(context.getResources().getDimension(R.dimen.corner_path_width)));
        return p;
    }
}
