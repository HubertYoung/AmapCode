package com.ant.phone.slam;

import android.content.Context;
import android.opengl.Matrix;
import com.alipay.alipaylogger.Log;
import com.alipay.streammedia.cvengine.slam.ORBRenderModelParams;
import com.ant.phone.slam.process.SlamProcessor;

public class SlamSession {
    private final String TAG = "SlamSession";
    private boolean mParamValid = false;
    private float[] mProjectionMatrix = new float[16];
    private SlamProcessor mSlamProcessor;

    public SlamSession(Context context) {
        this.mSlamProcessor = new SlamProcessor(context);
        Matrix.setIdentityM(this.mProjectionMatrix, 0);
    }

    public void setSlamParams(SlamParams params) {
        this.mParamValid = SlamProcessor.b(params);
        if (this.mParamValid) {
            this.mSlamProcessor.a(params);
            try {
                Matrix.perspectiveM(this.mProjectionMatrix, 0, params.cameraHorizontalViewAngle, (1.0f * ((float) params.viewWidth)) / ((float) params.viewHeight), 0.8f, 50000.0f);
            } catch (Exception e) {
                Log.e("SlamSession", "set projection matrix exp:", e);
            }
        } else {
            Log.e("SlamSession", "setSlamParams params = " + params);
            throw new RuntimeException("params illegal.");
        }
    }

    public boolean start() {
        return this.mSlamProcessor.c();
    }

    public void stop() {
        this.mSlamProcessor.d();
    }

    public float[] getInitMatrix() {
        return this.mSlamProcessor.a();
    }

    public float[] getPojectMatrix() {
        return this.mProjectionMatrix;
    }

    public boolean supportSlam() {
        return this.mSlamProcessor.b();
    }

    public SlamData process(byte[] data, int width, int height, ORBRenderModelParams orbRenderModelParams) {
        SlamData resultSlamData = this.mSlamProcessor.a(data, width, height, orbRenderModelParams);
        if (resultSlamData == null) {
            Log.w("SlamSession", "process resultSlamData null viewWidth = " + width + " viewHeight = " + height + " param = " + orbRenderModelParams);
        }
        return resultSlamData;
    }
}
