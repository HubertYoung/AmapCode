package com.autonavi.minimap.ajx3.mapcanvas;

import android.graphics.Bitmap;
import android.os.Build;
import android.os.Build.VERSION;
import android.text.TextUtils;
import com.alipay.mobile.nebula.resourcehandler.H5ResourceHandlerUtil;
import com.alipay.sdk.packet.d;
import com.autonavi.jni.ae.gmap.GLMapState;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.minimap.acanvas.ACanvasJNI;
import com.autonavi.minimap.acanvas.IACanvasFpsListener;
import com.autonavi.minimap.acanvas.IACanvasFpsUpdater;
import com.autonavi.minimap.ajx3.Ajx;
import com.autonavi.minimap.ajx3.action.IActionLog;
import com.autonavi.minimap.ajx3.mapcanvas.AmapCanvasListener.BeforeDrawFrameListener;
import java.text.DecimalFormat;
import java.util.concurrent.ConcurrentLinkedQueue;
import org.json.JSONObject;

public class AmapCanvasContext2D implements IACanvasFpsListener, BeforeDrawFrameListener {
    private IActionLog mActionLog = Ajx.getInstance().getActionLog();
    private AmapCanvasListener mAmapCanvasListener;
    private String mCanvasId;
    private float mCanvasScale = 1.0f;
    private long mCanvasView;
    private long mCommandCount = 0;
    private float mContentScale;
    private float mCurrentFps = 0.0f;
    private long mDrawCount = 0;
    private int mHeight;
    private String mLastRenderCommand;
    private long mPtr;
    private ConcurrentLinkedQueue<String> mRenderCommand;
    private float mTargetFps = 0.0f;
    private int mWidth;

    public void drawTime(long j, long j2, int i) {
    }

    public AmapCanvasContext2D(String str, int i, int i2, float f) {
        this.mCanvasId = str;
        this.mWidth = i;
        this.mHeight = i2;
        this.mContentScale = f;
        this.mAmapCanvasListener = new AmapCanvasListener(this);
        this.mRenderCommand = new ConcurrentLinkedQueue<>();
        bty mapView = DoNotUseTool.getMapView();
        if (mapView != null) {
            akq c = mapView.c();
            if (c != null) {
                this.mCanvasView = c.d.createCanvasView(mapView.m(mapView.ad()));
                if (this.mCanvasView != 0) {
                    this.mPtr = ACanvasJNI.mapCreateContext2D(this.mCanvasView, i, i2, f, this);
                    mapView.a((amk) this.mAmapCanvasListener);
                    return;
                }
                actionLogError(1020, "createCanvasView failed");
            }
        }
    }

    public void beforeDrawFrame(int i, GLMapState gLMapState) {
        String poll = this.mRenderCommand.poll();
        if (!TextUtils.isEmpty(poll)) {
            long j = this.mDrawCount + 1;
            this.mDrawCount = j;
            if (j >= Long.MAX_VALUE) {
                this.mDrawCount = 0;
            }
            this.mLastRenderCommand = poll;
            if (this.mPtr != 0) {
                ACanvasJNI.mapRenderCommand(this.mPtr, poll);
            }
        } else if (this.mCanvasScale == 0.0f && !TextUtils.isEmpty(this.mLastRenderCommand) && this.mPtr != 0) {
            ACanvasJNI.mapRenderCommand(this.mPtr, this.mLastRenderCommand);
        }
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

    public void onSizeChanged(int i, int i2) {
        if (this.mPtr != 0) {
            if (!(this.mWidth == i && this.mHeight == i2)) {
                this.mWidth = i;
                this.mHeight = i2;
                ACanvasJNI.onMapSizeChanged(this.mPtr, i, i2);
            }
        }
    }

    public void onCanvasScaleChanged(float f) {
        if (!(this.mPtr == 0 || this.mCanvasScale == f)) {
            this.mCanvasScale = f;
            ACanvasJNI.onMapCanvasScaleChanged(this.mPtr, f);
        }
    }

    public void renderCommand(String str) {
        this.mRenderCommand.offer(str);
        long j = this.mCommandCount + 1;
        this.mCommandCount = j;
        if (j >= Long.MAX_VALUE) {
            this.mCommandCount = 0;
        }
    }

    public float measureText(String str, String str2) {
        if (this.mPtr == 0) {
            return 0.0f;
        }
        return ACanvasJNI.mapMeasureText(this.mPtr, str, str2);
    }

    public void bindImageTexture(int i, Bitmap bitmap) {
        if (this.mPtr != 0) {
            ACanvasJNI.mapBindImageTexture(this.mPtr, i, bitmap);
        }
    }

    public void releaseImageTexture(int i) {
        if (this.mPtr != 0) {
            ACanvasJNI.mapReleaseImageTexture(this.mPtr, i);
        }
    }

    public void destroy() {
        actionLogFPS(this.mCommandCount, this.mDrawCount);
        bty mapView = DoNotUseTool.getMapView();
        if (mapView != null) {
            mapView.b((amk) this.mAmapCanvasListener);
            akq c = mapView.c();
            if (!(c == null || this.mCanvasView == 0)) {
                c.d.destroyCanvasView(this.mCanvasView);
                this.mCanvasView = 0;
            }
        }
        if (this.mPtr != 0) {
            ACanvasJNI.mapDestroyContext2D(this.mPtr);
            this.mPtr = 0;
        }
        this.mRenderCommand.clear();
    }

    public void addFpsUpdater(float f, IACanvasFpsUpdater iACanvasFpsUpdater) {
        this.mTargetFps = f;
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
}
