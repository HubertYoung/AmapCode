package com.alipay.mobile.beehive.capture.edgedetect;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import com.alipay.android.phone.mobilecommon.multimedia.widget.SightCameraView.FramePreprocessor;
import com.alipay.mobile.beehive.R;
import com.alipay.mobile.beehive.capture.magicscan.SimpleRender;
import com.alipay.mobile.beehive.capture.magicscan.utils.FPSCalculate;
import com.alipay.mobile.beehive.capture.utils.Logger;

public class EdgeDetector implements FramePreprocessor {
    private static final String TAG = "EdgeDetector";
    private volatile boolean mEnabledByUser;
    private FPSCalculate mFpsCal;
    private Bitmap mMeshBitmap;
    private SimpleRender mRender;
    private boolean mSelfEnabled;

    public EdgeDetector(Context context) {
        this(context, false);
    }

    public EdgeDetector(Context context, boolean enabled) {
        this.mSelfEnabled = true;
        this.mEnabledByUser = false;
        this.mEnabledByUser = enabled;
        Logger.debug(TAG, "EdgeDetector construct, enabled=" + enabled);
        try {
            this.mMeshBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.scan_mesh_2);
        } catch (Exception e) {
            Logger.error((String) TAG, (Throwable) e);
            this.mSelfEnabled = false;
        }
        this.mFpsCal = new FPSCalculate();
    }

    public void setEnabled(boolean enabled) {
        this.mEnabledByUser = enabled;
    }

    public boolean isEnabled() {
        return this.mSelfEnabled && this.mEnabledByUser;
    }

    public int onProcess(int i, float[] matrix, int i1) {
        this.mFpsCal.addRecord();
        if (this.mRender == null) {
            this.mRender = new SimpleRender();
            if (this.mMeshBitmap != null && !this.mMeshBitmap.isRecycled()) {
                this.mRender.setMeshData(rgbaValuesFromBitmap(this.mMeshBitmap), this.mMeshBitmap.getWidth(), this.mMeshBitmap.getHeight());
                this.mMeshBitmap.recycle();
            }
            this.mRender.prepareRender(i, i1);
        }
        this.mRender.draw(i, i1, matrix, isEnabled());
        float fps = this.mFpsCal.getFps();
        if (fps > 0.0f && fps < 20.0f) {
            Logger.debug(TAG, "onProcess, Fps Low, fps=" + fps);
        }
        return 1;
    }

    public void onRelease() {
        Logger.debug(TAG, "onRelease");
        try {
            if (this.mRender != null) {
                this.mRender.destroyRender();
            }
        } catch (Throwable t) {
            Logger.error((String) TAG, t);
        }
        this.mRender = null;
    }

    private byte[] rgbaValuesFromBitmap(Bitmap bitmap) {
        ColorFilter colorFilter = new ColorMatrixColorFilter(new ColorMatrix());
        Bitmap argbBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(argbBitmap);
        Paint paint = new Paint();
        paint.setColorFilter(colorFilter);
        canvas.drawBitmap(bitmap, 0.0f, 0.0f, paint);
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int totalPixels = width * height;
        byte[] rgbaValues = new byte[(totalPixels * 4)];
        int[] argbPixels = new int[totalPixels];
        argbBitmap.getPixels(argbPixels, 0, width, 0, 0, width, height);
        for (int i = 0; i < totalPixels; i++) {
            int argbPixel = argbPixels[i];
            int red = Color.red(argbPixel);
            int green = Color.green(argbPixel);
            int blue = Color.blue(argbPixel);
            int alpha = Color.alpha(argbPixel);
            rgbaValues[(i * 4) + 0] = (byte) red;
            rgbaValues[(i * 4) + 1] = (byte) green;
            rgbaValues[(i * 4) + 2] = (byte) blue;
            rgbaValues[(i * 4) + 3] = (byte) alpha;
        }
        argbBitmap.recycle();
        return rgbaValues;
    }
}
