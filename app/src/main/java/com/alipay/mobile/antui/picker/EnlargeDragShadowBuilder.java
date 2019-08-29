package com.alipay.mobile.antui.picker;

import android.graphics.Canvas;
import android.graphics.Point;
import android.view.View;
import android.view.View.DragShadowBuilder;
import com.alipay.mobile.antui.utils.AuiLogger;

public class EnlargeDragShadowBuilder extends DragShadowBuilder {
    private Point mScaleFactor;

    public EnlargeDragShadowBuilder(View v) {
        super(v);
    }

    public void onProvideShadowMetrics(Point size, Point touch) {
        int width = (int) (((double) getView().getWidth()) * 1.1d);
        int height = (int) (((double) getView().getHeight()) * 1.1d);
        size.set(width, height);
        this.mScaleFactor = size;
        int i = (int) (AUImagePickerSkeleton.partionX * ((float) width));
        int j = (int) (AUImagePickerSkeleton.partionY * ((float) height));
        AuiLogger.info("ItemDragCallback", " i:" + (AUImagePickerSkeleton.partionX * ((float) width)) + "   " + i + "\r\n  j:" + j + "   " + (AUImagePickerSkeleton.partionY * ((float) height)));
        touch.set(i, j);
    }

    public void onDrawShadow(Canvas canvas) {
        canvas.scale(((float) this.mScaleFactor.x) / ((float) getView().getWidth()), ((float) this.mScaleFactor.y) / ((float) getView().getHeight()));
        getView().draw(canvas);
    }
}
