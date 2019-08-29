package com.autonavi.bundle.amaphome.components;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.View;

public class BitmapSkelentonView extends View {
    private Bitmap mBitmap = load();
    private final String mFilePath;

    public BitmapSkelentonView(Context context, String str) {
        super(context);
        this.mFilePath = str;
    }

    private Bitmap load() {
        return BitmapFactory.decodeFile(this.mFilePath);
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        if (this.mBitmap == null) {
            this.mBitmap = load();
        }
        canvas.drawColor(-1);
        canvas.drawBitmap(this.mBitmap, 0.0f, 0.0f, null);
    }

    public void setVisibility(int i) {
        super.setVisibility(i);
        if (i == 8 && this.mBitmap != null) {
            this.mBitmap.recycle();
            this.mBitmap = null;
        }
    }
}
