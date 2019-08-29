package com.ant.phone.xmedia.algorithm;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.alipay.alipaylogger.Log;
import com.alipay.mobile.common.transport.monitor.RPCDataParser;
import com.alipay.streammedia.cvengine.CVNativeException;
import com.alipay.streammedia.cvengine.tracking.MultiTrackerResult;
import com.alipay.streammedia.cvengine.tracking.TargetRect;
import com.alipay.streammedia.cvengine.tracking.TargetRectResult;
import com.ant.phone.xmedia.api.utils.FrameCapture;
import com.ant.phone.xmedia.benchmark.AlgorithmBenchmark;
import com.ant.phone.xmedia.data.DebugSwitch;
import com.ant.phone.xmedia.params.BoundingBox;
import com.ant.phone.xmedia.params.ErrorInfo;
import com.ant.phone.xmedia.params.TrackerItem;
import com.ant.phone.xmedia.params.XMediaResponse;
import com.ant.phone.xmedia.params.XMediaTrackResult;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/* compiled from: TrackAlgorithm */
final class g extends Handler {
    final /* synthetic */ TrackAlgorithm a;
    private WeakReference<TrackAlgorithm> b;
    private Looper c;
    private List<TrackerItem> d;

    /* JADX WARN: Illegal instructions before constructor call commented (this can break semantics) */
    public g(TrackAlgorithm trackAlgorithm, TrackAlgorithm render, Looper looper) {
        // this.a = trackAlgorithm;
        super(looper);
        this.b = new WeakReference<>(render);
        this.c = looper;
    }

    private void a(List<TrackerItem> list, int w, int h) {
        Log.i("TrackAlgorithm", "handleInit");
        this.d = list;
        List targetRects = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            TargetRect rect = list.get(i).mRect;
            Log.i("TrackAlgorithm", "tracking init x:" + rect.X + ", y:" + rect.Y + ", width:" + rect.width + ", height:" + rect.height);
            targetRects.add(rect);
        }
        long t = System.currentTimeMillis();
        AlgorithmBenchmark.a((String) "KEY_INIT").putLong("ENGINE_INIT_START", t);
        TrackAlgorithm trackAlgorithm = this.a;
        this.a.i = 0;
        trackAlgorithm.j = 0;
        if (this.a.a != null) {
            try {
                int max = Math.max(w, h);
                if (max > 640) {
                    max = 640;
                }
                this.a.c = this.a.a.trackerInit(targetRects, w, h, this.a.d, max);
            } catch (Exception e) {
                try {
                    this.a.a.Destory();
                } catch (CVNativeException cve) {
                    Log.e("TrackAlgorithm", "mTracker.Destory exp:" + cve);
                }
                Log.e("TrackAlgorithm", "handleInit exp:" + e);
            }
        }
        long end = System.currentTimeMillis();
        AlgorithmBenchmark.a((String) "KEY_INIT").putLong("ENGINE_INIT_END", end);
        Log.i("TrackAlgorithm", "handleInit end. mTrackInitialized:" + this.a.c + ", cost time:" + (end - t) + RPCDataParser.TIME_MS);
        AlgorithmBenchmark.a(this.a.c ? 0 : 1);
        if (!this.a.c && this.a.l != null) {
            XMediaResponse response = new XMediaResponse();
            response.mErrInfo = new ErrorInfo(10003, "track algorithm init failed.");
            response.mMode = 8;
            this.a.l.onResponse(response);
        }
    }

    private void a(int w, int h) {
        long t = System.currentTimeMillis();
        if (this.a.a != null && this.a.c) {
            if (this.a.i == 0) {
                AlgorithmBenchmark.a((String) "KEY_FRAME_DET_TRACK").putLong("FRAME_TRACK_START", t);
            }
            MultiTrackerResult result = null;
            try {
                if (DebugSwitch.b) {
                    FrameCapture.a(this.a.f, this.a.e);
                }
                result = this.a.a.trackerStart(this.a.d, w, h);
                this.a.b = false;
            } catch (Exception e) {
                try {
                    this.a.a.Destory();
                } catch (CVNativeException cve) {
                    Log.e("TrackAlgorithm", "mTracker.Destory exp:" + cve);
                }
                Log.e("TrackAlgorithm", "handleInit exp:" + e);
            }
            if (!(result == null || result.getObjRects() == null)) {
                ArrayList xMediaResults = new ArrayList();
                List objRects = result.getObjRects();
                for (int i = 0; i < objRects.size(); i++) {
                    TargetRectResult rectResult = objRects.get(i);
                    if (rectResult == null) {
                        Log.w("TrackAlgorithm", "track item is null, skip to next.");
                    } else {
                        Log.i("TrackAlgorithm", "tracking x:" + rectResult.X + " y:" + rectResult.Y + " width:" + rectResult.width + " height:" + rectResult.height + " isLost:" + rectResult.isLost);
                        XMediaTrackResult trackResult = new XMediaTrackResult();
                        trackResult.mIsLost = rectResult.isLost ? 1 : 0;
                        trackResult.mBoundingBox = a(rectResult, w, h, this.a.m);
                        trackResult.mLabel = this.d.get(i).mLabel;
                        trackResult.mConfidence = this.d.get(i).mConfidence;
                        xMediaResults.add(trackResult);
                    }
                }
                if (this.a.l != null) {
                    XMediaResponse response = new XMediaResponse();
                    response.mErrInfo = new ErrorInfo(0, "no error");
                    response.mMode = 8;
                    response.mResult = xMediaResults;
                    this.a.l.onTrack(response);
                }
            }
        }
        long duration = System.currentTimeMillis() - t;
        this.a.j = this.a.j + duration;
        Log.i("TrackAlgorithm", "handleTrack took " + duration + "ms, framecount:" + this.a.i);
        this.a.i = this.a.i + 1;
    }

    private static BoundingBox a(TargetRectResult src, int w, int h, int rotation) {
        int x1 = 0;
        int y1 = 0;
        int x2 = 0;
        int y2 = 0;
        int x0 = src.X;
        int y0 = src.Y;
        int rectw = src.width;
        int recth = src.height;
        int displayh = h;
        int displayw = w;
        if (rotation == 90 || rotation == 270) {
            displayh = w;
            displayw = h;
        }
        switch (rotation) {
            case 0:
                x1 = x0;
                y1 = y0;
                x2 = x1 + rectw;
                y2 = y1 + recth;
                break;
            case 90:
                x1 = (h - y0) - recth;
                y1 = x0;
                x2 = x1 + recth;
                y2 = y1 + rectw;
                break;
            case 180:
                x1 = (w - x0) - rectw;
                y1 = (h - y0) - recth;
                x2 = x1 + rectw;
                y2 = y1 + recth;
                break;
            case 270:
                x1 = y0;
                y1 = (w - x0) - rectw;
                x2 = x1 + recth;
                y2 = y1 + rectw;
                break;
        }
        return new BoundingBox((((float) x1) * 1.0f) / ((float) displayw), (((float) y1) * 1.0f) / ((float) displayh), (((float) (x2 - x1)) * 1.0f) / ((float) displayw), (((float) (y2 - y1)) * 1.0f) / ((float) displayh));
    }

    private void a() {
        long t = System.currentTimeMillis();
        AlgorithmBenchmark.a((String) "KEY_FRAME_DET_TRACK").putLong("FRAME_TRACK_END", t);
        if (DebugSwitch.b) {
            FrameCapture.a(this.a.f, this.a.e);
        }
        if (this.a.c && this.a.a != null) {
            try {
                this.a.a.Destory();
            } catch (CVNativeException cve) {
                Log.e("TrackAlgorithm", "mTracker.Destory exp:" + cve);
            }
            this.a.c = false;
            AlgorithmBenchmark.a((String) "KEY_FRAME_DET_TRACK").putLong("ENGINE_TOTAL_TIME_TRACK", this.a.j);
            AlgorithmBenchmark.a((String) "KEY_FRAME_DET_TRACK").putInt("FRAME_TOTAL_COUNT_TRACK", this.a.i);
            AlgorithmBenchmark.c();
        }
        this.a.b = false;
        TrackAlgorithm trackAlgorithm = this.a;
        this.a.i = 0;
        trackAlgorithm.j = 0;
        Log.i("TrackAlgorithm", "handleUninit took " + (System.currentTimeMillis() - t) + RPCDataParser.TIME_MS);
    }

    public final void handleMessage(Message inputMessage) {
        int what = inputMessage.what;
        Object obj = inputMessage.obj;
        if (((TrackAlgorithm) this.b.get()) == null) {
            Log.i("TrackAlgorithm", "TrackHandler.handleMessage: render is null");
            return;
        }
        Log.i("TrackAlgorithm", "handleMessage handle msg:" + what);
        switch (what) {
            case 0:
                try {
                    this.a.b();
                    a((List) obj, inputMessage.arg1, inputMessage.arg2);
                    return;
                } catch (Exception e) {
                    Log.e("TrackAlgorithm", "handleMessage error, msg mErrInfo:" + what, e);
                }
            case 1:
                a(inputMessage.arg1, inputMessage.arg2);
                return;
            case 2:
                a();
                return;
            case 3:
                try {
                    this.c.quit();
                    Log.i("TrackAlgorithm", "thread quit");
                    synchronized (this.a.n) {
                        this.a.n.notifyAll();
                        Log.i("TrackAlgorithm", "notifyAll");
                    }
                    return;
                } catch (Exception e2) {
                    Log.e("TrackAlgorithm", "quit exp:", e2);
                    Log.i("TrackAlgorithm", "thread quit");
                    synchronized (this.a.n) {
                        this.a.n.notifyAll();
                        Log.i("TrackAlgorithm", "notifyAll");
                        return;
                    }
                } catch (Throwable th) {
                    Log.i("TrackAlgorithm", "thread quit");
                    synchronized (this.a.n) {
                        this.a.n.notifyAll();
                        Log.i("TrackAlgorithm", "notifyAll");
                        throw th;
                    }
                }
            default:
                return;
        }
        Log.e("TrackAlgorithm", "handleMessage error, msg mErrInfo:" + what, e);
    }
}
