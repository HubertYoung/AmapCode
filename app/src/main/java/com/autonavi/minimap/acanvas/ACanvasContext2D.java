package com.autonavi.minimap.acanvas;

import android.graphics.Bitmap;
import android.os.Build;
import android.os.Build.VERSION;
import android.view.Surface;
import com.alipay.mobile.nebula.resourcehandler.H5ResourceHandlerUtil;
import com.alipay.sdk.packet.d;
import com.autonavi.minimap.ajx3.Ajx;
import com.autonavi.minimap.ajx3.action.IActionLog;
import java.text.DecimalFormat;
import org.json.JSONObject;

public class ACanvasContext2D implements IACanvasFpsListener {
    private IActionLog mActionLog = Ajx.getInstance().getActionLog();
    private String mCanvasId;
    private float mCanvasScale = 1.0f;
    private float mContentScale;
    private float mCurrentFps = 0.0f;
    private IACanvasFpsUpdater mFpsUpdater;
    private int mHeight;
    private long mPtr;
    private Surface mSurface;
    private float mTargetFps = 0.0f;
    private int mWidth;

    public ACanvasContext2D(String str, int i, int i2, float f) {
        this.mCanvasId = str;
        this.mWidth = i;
        this.mHeight = i2;
        this.mContentScale = f;
        this.mPtr = ACanvasJNI.createContext2D(str, i, i2, f, this);
    }

    public String getCanvasId() {
        return this.mCanvasId;
    }

    public int getWidth() {
        return this.mWidth;
    }

    public int getHeight() {
        return this.mHeight;
    }

    public void onSurfaceChanged(Surface surface) {
        if (this.mSurface != surface) {
            this.mSurface = surface;
            if (this.mPtr != 0) {
                ACanvasJNI.onSurfaceChanged(this.mPtr, surface);
            }
        }
    }

    public void onSizeChanged(int i, int i2) {
        if (this.mWidth != i || this.mHeight != i2) {
            this.mWidth = i;
            this.mHeight = i2;
            if (this.mPtr != 0) {
                ACanvasJNI.onSizeChanged(this.mPtr, i, i2);
            }
        }
    }

    public void onCanvasScaleChanged(float f) {
        if (this.mCanvasScale != f) {
            this.mCanvasScale = f;
            if (this.mPtr != 0) {
                ACanvasJNI.onCanvasScaleChanged(this.mPtr, f);
            }
        }
    }

    public void renderCommand(String str) {
        if (this.mPtr != 0) {
            ACanvasJNI.renderCommand(this.mPtr, str);
        }
    }

    public void redraw() {
        if (this.mPtr != 0) {
            ACanvasJNI.redraw(this.mPtr);
        }
    }

    public float measureText(String str, String str2) {
        if (this.mPtr != 0) {
            return ACanvasJNI.measureText(this.mPtr, str, str2);
        }
        return 0.0f;
    }

    public void bindImageTexture(int i, Bitmap bitmap) {
        if (this.mPtr != 0) {
            ACanvasJNI.bindImageTexture(this.mPtr, i, bitmap);
        }
    }

    public void releaseImageTexture(int i) {
        if (this.mPtr != 0) {
            ACanvasJNI.releaseImageTexture(this.mPtr, i);
        }
    }

    public void destroy() {
        if (this.mPtr != 0) {
            ACanvasJNI.destroyContext2D(this.mPtr);
            this.mPtr = 0;
        }
        this.mFpsUpdater = null;
    }

    public void addFpsUpdater(float f, IACanvasFpsUpdater iACanvasFpsUpdater) {
        this.mCurrentFps = f;
        this.mTargetFps = f;
        this.mFpsUpdater = iACanvasFpsUpdater;
    }

    public void drawTime(long j, long j2, int i) {
        float f = (float) j;
        float f2 = ((float) i) / (f / 1000.0f);
        if (this.mFpsUpdater != null) {
            if (f2 < this.mTargetFps) {
                double d = (double) ((((float) j2) * 1.0f) / f);
                if (d > 0.8d) {
                    f2 = this.mCurrentFps - 9.0f;
                } else if (d > 0.6d) {
                    f2 = this.mCurrentFps - 6.0f;
                } else if (d > 0.4d) {
                    f2 = this.mCurrentFps - 3.0f;
                } else {
                    f2 = this.mCurrentFps + 3.0f;
                }
            } else if (this.mCurrentFps < this.mTargetFps) {
                f2 = this.mCurrentFps + 3.0f;
            }
            if (f2 > this.mTargetFps) {
                f2 = this.mTargetFps;
            } else if (f2 <= 1.0f) {
                f2 = 1.0f;
            }
            if (Math.abs(this.mCurrentFps - f2) >= 1.0f) {
                this.mCurrentFps = f2;
                this.mFpsUpdater.updateFps(f2);
            }
        }
        StringBuilder sb = new StringBuilder("totalTime: ");
        sb.append(j);
        sb.append(",drawTime: ");
        sb.append(j2);
        sb.append(",drawCount: ");
        sb.append(i);
        sb.append(",fps: ");
        sb.append(f2);
        ACanvasLog.i(sb.toString());
    }

    public void actionLogFPS(long j, long j2) {
        if (this.mActionLog != null) {
            try {
                JSONObject jSONObject = new JSONObject();
                JSONObject jSONObject2 = new JSONObject();
                jSONObject2.put("canvasid", this.mCanvasId);
                jSONObject2.put("agent", VERSION.RELEASE);
                jSONObject2.put(d.n, Build.MODEL);
                jSONObject2.put("command_count", j);
                jSONObject2.put("draw_count", j2);
                jSONObject2.put("dcr", new DecimalFormat("#0.00").format((double) (j > 0 ? (((float) j2) * 1.0f) / ((float) j) : -1.0f)));
                JSONObject jSONObject3 = new JSONObject();
                jSONObject3.put("targetFPS", (double) this.mTargetFps);
                jSONObject3.put("currentFps", (double) this.mCurrentFps);
                jSONObject3.put("canvasWidth", this.mWidth);
                jSONObject3.put("canvasHeight", this.mHeight);
                jSONObject3.put("contentScale", (double) this.mContentScale);
                jSONObject2.put("other_info", jSONObject3);
                jSONObject.put(H5ResourceHandlerUtil.OTHER, jSONObject2);
                this.mActionLog.actionLogV2("AJX-Canvas", "B001", jSONObject);
            } catch (Exception unused) {
            }
        }
    }

    public void actionLogError(int i, String str) {
        if (this.mActionLog != null) {
            try {
                JSONObject jSONObject = new JSONObject();
                JSONObject jSONObject2 = new JSONObject();
                jSONObject2.put("canvasid", this.mCanvasId);
                jSONObject2.put("agent", VERSION.RELEASE);
                jSONObject2.put(d.n, Build.MODEL);
                if (i >= 3000) {
                    jSONObject2.put("error_type", "error_image");
                } else if (i >= 2000) {
                    jSONObject2.put("error_type", "error_text");
                } else if (i >= 1000) {
                    jSONObject2.put("error_type", "error_context");
                }
                StringBuilder sb = new StringBuilder("errorCode:");
                sb.append(i);
                sb.append(", ");
                sb.append(str);
                jSONObject2.put("error_message", sb.toString());
                jSONObject2.put("other_info", "");
                jSONObject.put(H5ResourceHandlerUtil.OTHER, jSONObject2);
                this.mActionLog.actionLogV2("AJX-Canvas", "B002", jSONObject);
            } catch (Exception unused) {
            }
        }
    }

    public int hashCode() {
        return this.mCanvasId.hashCode();
    }

    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("canvasId: ");
        sb.append(this.mCanvasId);
        sb.append(", render: ");
        sb.append(this.mPtr);
        sb.append(", surface: ");
        sb.append(this.mSurface);
        return sb.toString();
    }
}
