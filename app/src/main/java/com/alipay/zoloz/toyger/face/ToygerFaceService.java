package com.alipay.zoloz.toyger.face;

import android.content.Context;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.HandlerThread;
import com.alipay.mobile.security.bio.common.record.MetaRecord;
import com.alipay.zoloz.toyger.ToygerService;
import com.alipay.zoloz.toyger.algorithm.TGDepthFrame;
import com.alipay.zoloz.toyger.algorithm.TGFrame;
import com.alipay.zoloz.toyger.algorithm.Toyger;
import com.alipay.zoloz.toyger.algorithm.ToygerConfig;
import com.alipay.zoloz.toyger.blob.BitmapHelper;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

public class ToygerFaceService extends ToygerService<ToygerFaceCallback, ToygerFaceState, ToygerFaceAttr, ToygerFaceInfo, ToygerFaceAlgorithmConfig> {
    public static final String KEY_TOYGER_DEPTH_FRAME = "toyger_depth_frame";
    public static final String KEY_TOYGER_FRAME = "toyger_frame";
    private static final int QUEUE_LENGTH = 1;
    /* access modifiers changed from: private */
    public static String licenses;
    /* access modifiers changed from: private */
    public static byte[] model = null;
    private FaceBlobManager blobManager;
    /* access modifiers changed from: private */
    public boolean isMirror = false;
    /* access modifiers changed from: private */
    public final ReentrantLock mColorFrameQueueLock = new ReentrantLock();
    /* access modifiers changed from: private */
    public final BlockingQueue<TGDepthFrame> mDepthFrameQueue = new LinkedBlockingDeque(1);
    /* access modifiers changed from: private */
    public final ReentrantLock mDepthFrameQueueLock = new ReentrantLock();
    /* access modifiers changed from: private */
    public final FrameProcessor mFrameProcessor = new FrameProcessor();
    /* access modifiers changed from: private */
    public final BlockingQueue<List<TGFrame>> mFrameQueue = new LinkedBlockingDeque(1);
    private HandlerThread mProcessThread;
    private Handler mProcessThreadHandler;
    /* access modifiers changed from: private */
    public final AtomicBoolean mRunning = new AtomicBoolean(false);

    private static boolean load(Context context) {
        try {
            InputStream open = context.getAssets().open(ToygerService.ASSET_FACE);
            model = new byte[open.available()];
            open.read(model);
            if (model == null) {
                return false;
            }
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public static boolean preLoad(Context context) {
        if (model != null) {
            return true;
        }
        return load(context);
    }

    public boolean init(Context context, ToygerFaceCallback toygerFaceCallback, String str, String str2, Map<String, Object> map) {
        String str3;
        ToygerFaceAlgorithmConfig toygerFaceAlgorithmConfig;
        this.mToygerCallback = toygerFaceCallback;
        if (model == null) {
            load(context);
        }
        if (this.mProcessThread == null) {
            this.mProcessThread = new HandlerThread("ToygerProcessQueue");
            this.mProcessThread.start();
        }
        if (this.mProcessThreadHandler == null) {
            this.mProcessThreadHandler = new Handler(this.mProcessThread.getLooper());
        }
        String str4 = null;
        if (map != null && map.containsKey(ToygerService.KEY_PUBLIC_KEY)) {
            str4 = map.get(ToygerService.KEY_PUBLIC_KEY).toString();
        }
        ToygerFaceBlobConfig from = ToygerFaceBlobConfig.from(str2, str4);
        if (from == null) {
            return false;
        }
        if (map != null && map.containsKey(ToygerService.KEY_IS_MIRROR)) {
            this.isMirror = Boolean.parseBoolean(map.get(ToygerService.KEY_IS_MIRROR).toString());
        }
        int i = 2;
        if (map != null && map.containsKey("meta_serializer")) {
            i = Integer.parseInt(map.get("meta_serializer").toString());
        }
        switch (i) {
            case 2:
                str3 = "com.alipay.zoloz.toyger.face.FaceBlobManagerPb";
                break;
            default:
                str3 = "com.alipay.zoloz.toyger.face.FaceBlobManagerJson";
                break;
        }
        try {
            Constructor<?> declaredConstructor = Class.forName(str3).getDeclaredConstructor(new Class[]{ToygerFaceBlobConfig.class});
            declaredConstructor.setAccessible(true);
            this.blobManager = (FaceBlobManager) declaredConstructor.newInstance(new Object[]{from});
            ToygerFaceAlgorithmConfig from2 = ToygerFaceAlgorithmConfig.from(str);
            if (from2 == null) {
                toygerFaceAlgorithmConfig = new ToygerFaceAlgorithmConfig();
            } else {
                toygerFaceAlgorithmConfig = from2;
            }
            final ToygerConfig toygerConfig = toygerFaceAlgorithmConfig.toToygerConfig();
            String str5 = "";
            if (from.collection != null) {
                for (int i2 = 0; i2 < from.collection.size(); i2++) {
                    if (str5.length() > 0) {
                        str5 = str5.concat(MetaRecord.LOG_SEPARATOR).concat(from.collection.get(i2));
                    } else {
                        str5 = from.collection.get(i2);
                    }
                }
            }
            toygerConfig.livenessConfig.collection = str5;
            String str6 = "";
            for (int i3 = 0; i3 < toygerFaceAlgorithmConfig.liveness_combination.size(); i3++) {
                if (str6.length() > 0) {
                    str6 = str6.concat(MetaRecord.LOG_SEPARATOR).concat(toygerFaceAlgorithmConfig.liveness_combination.get(i3));
                } else {
                    str6 = toygerFaceAlgorithmConfig.liveness_combination.get(i3);
                }
            }
            toygerConfig.livenessConfig.livenessCombinations = str6;
            new StringBuilder("toygerConfig=").append(toygerConfig);
            final ToygerFaceCallback toygerFaceCallback2 = (ToygerFaceCallback) this.mToygerCallback;
            final Context context2 = context;
            this.mProcessThreadHandler.post(new Runnable() {
                public void run() {
                    HashMap hashMap = new HashMap();
                    hashMap.put("StateSignature", ToygerFaceState.class.getName().replace(".", "/"));
                    hashMap.put("AttrSignature", ToygerFaceAttr.class.getName().replace(".", "/"));
                    hashMap.put("FrameSignature", TGFrame.class.getName().replace(".", "/"));
                    boolean init = Toyger.init(context2, ToygerFaceService.model, ToygerFaceService.licenses, context2.getPackageName(), hashMap);
                    ToygerFaceService.model = null;
                    if (init) {
                        Toyger.config(this, toygerConfig);
                        ToygerFaceService.this.mRunning.set(true);
                        return;
                    }
                    toygerFaceCallback2.onEvent(-4, null);
                }
            });
            return true;
        } catch (Throwable th) {
            return false;
        }
    }

    public boolean processImage(List<TGFrame> list, TGDepthFrame tGDepthFrame) {
        ArrayList arrayList = new ArrayList();
        if (this.mFrameQueue.offer(arrayList)) {
            this.mColorFrameQueueLock.lock();
            for (TGFrame deepCopy : list) {
                arrayList.add(deepCopy.deepCopy());
            }
            this.mColorFrameQueueLock.unlock();
        }
        TGDepthFrame tGDepthFrame2 = new TGDepthFrame();
        if (tGDepthFrame != null && (!(tGDepthFrame.data == null && tGDepthFrame.shortBuffer == null) && this.mDepthFrameQueue.offer(tGDepthFrame2))) {
            this.mDepthFrameQueueLock.lock();
            tGDepthFrame2.assign(tGDepthFrame);
            this.mDepthFrameQueueLock.unlock();
        }
        if (this.mProcessThreadHandler == null) {
            return false;
        }
        this.mProcessThreadHandler.post(new Runnable() {
            public void run() {
                try {
                    if (ToygerFaceService.this.mRunning.get()) {
                        List<TGFrame> list = (List) ToygerFaceService.this.mFrameQueue.poll(1, TimeUnit.SECONDS);
                        TGDepthFrame tGDepthFrame = (TGDepthFrame) ToygerFaceService.this.mDepthFrameQueue.poll();
                        ToygerFaceService.this.mColorFrameQueueLock.lock();
                        ToygerFaceService.this.mDepthFrameQueueLock.lock();
                        if (!(list == null || list.size() == 0)) {
                            if (ToygerFaceService.this.isMirror) {
                                for (TGFrame tGFrame : list) {
                                    tGFrame.data = ToygerFaceService.mirrorYUV420(tGFrame.data, tGFrame.width, tGFrame.height);
                                }
                                tGDepthFrame.data = ToygerFaceService.mirrorDepth(tGDepthFrame.data, tGDepthFrame.width, tGDepthFrame.height);
                            }
                            ToygerFaceService.this.mFrameProcessor.initFame((TGFrame) list.get(0), tGDepthFrame);
                            Toyger.processImage(list, tGDepthFrame, new ToygerFaceAttr());
                        }
                        ToygerFaceService.this.mFrameProcessor.clearFrame();
                        ToygerFaceService.this.mColorFrameQueueLock.unlock();
                        ToygerFaceService.this.mDepthFrameQueueLock.unlock();
                    }
                } catch (Throwable th) {
                }
            }
        });
        return true;
    }

    public void reset() {
        Iterator it = this.mFrameQueue.iterator();
        while (it.hasNext()) {
            try {
                this.mColorFrameQueueLock.lock();
                for (TGFrame recycle : (List) it.next()) {
                    recycle.recycle();
                }
                this.mColorFrameQueueLock.unlock();
                it.remove();
            } catch (Throwable th) {
                th.toString();
            }
        }
        Iterator it2 = this.mDepthFrameQueue.iterator();
        while (it2.hasNext()) {
            try {
                this.mDepthFrameQueueLock.lock();
                ((TGDepthFrame) it2.next()).recycle();
                it2.remove();
                this.mDepthFrameQueueLock.unlock();
            } catch (Throwable th2) {
                th2.toString();
            }
        }
        this.mProcessThreadHandler.post(new Runnable() {
            public void run() {
                Toyger.reset();
            }
        });
    }

    public void release() {
        this.mRunning.set(false);
        reset();
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        this.mProcessThreadHandler.post(new Runnable() {
            public void run() {
                Toyger.release();
                countDownLatch.countDown();
            }
        });
        try {
            countDownLatch.await(1, TimeUnit.SECONDS);
            if (this.mProcessThread != null) {
                if (VERSION.SDK_INT >= 18) {
                    this.mProcessThread.quitSafely();
                } else {
                    this.mProcessThread.quit();
                }
            }
            this.mProcessThread = null;
            this.mProcessThreadHandler.removeCallbacksAndMessages(null);
            this.mProcessThreadHandler = null;
        } catch (InterruptedException e) {
            e.toString();
        }
    }

    public void handleStateUpdated(ToygerFaceState toygerFaceState, ToygerFaceAttr toygerFaceAttr) {
        new StringBuilder("handleStateUpdated(): state=").append(toygerFaceState).append(", attr=").append(toygerFaceAttr);
        HashMap hashMap = new HashMap();
        hashMap.put(KEY_TOYGER_FRAME, this.mFrameProcessor.getTgFrame());
        hashMap.put(KEY_TOYGER_DEPTH_FRAME, this.mFrameProcessor.getTgDepthFrame());
        ((ToygerFaceCallback) this.mToygerCallback).onStateUpdated(toygerFaceState, toygerFaceAttr, hashMap);
    }

    public void handleInfoReady(TGFrame tGFrame, ToygerFaceAttr toygerFaceAttr) {
        new StringBuilder("handleInfoReady(): frame=").append(tGFrame).append(", attr=").append(toygerFaceAttr);
        ((ToygerFaceCallback) this.mToygerCallback).onHighQualityFrame(BitmapHelper.reverseBitmap(BitmapHelper.rotateBitmap(BitmapHelper.bytes2Bitmap(tGFrame.data, tGFrame.width, tGFrame.height, tGFrame.frameMode), tGFrame.rotation), 0), toygerFaceAttr);
    }

    public void handleCaptureCompleted(int i, List<ToygerFaceInfo> list, Map<String, Object> map) {
        new StringBuilder("handleCaptureCompleted(): result=").append(i).append(", infos=").append(list).append(", extIno=").append(map);
        ((ToygerFaceCallback) this.mToygerCallback).onComplete(i, this.blobManager.generateBlob(list, map), this.blobManager.getKey(), this.blobManager.isUTF8());
    }

    public void addMonitorImage(TGFrame tGFrame) {
        this.blobManager.addMonitorImage(tGFrame);
    }

    public Map<String, Object> generateMonitorBlob() {
        byte[] monitorBlob = this.blobManager.getMonitorBlob();
        byte[] key = this.blobManager.getKey();
        HashMap hashMap = new HashMap();
        hashMap.put("content", monitorBlob);
        hashMap.put("key", key);
        hashMap.put(ToygerService.KEY_RES_9_IS_UTF8, Boolean.valueOf(this.blobManager.isUTF8()));
        return hashMap;
    }

    /* access modifiers changed from: private */
    public static byte[] mirrorYUV420(byte[] bArr, int i, int i2) {
        int i3 = 0;
        byte[] bArr2 = new byte[(((i * i2) * 3) / 2)];
        int i4 = 0;
        while (i4 < i2) {
            int i5 = i3;
            for (int i6 = i - 1; i6 >= 0; i6--) {
                bArr2[i5] = bArr[(i4 * i) + i6];
                i5++;
            }
            i4++;
            i3 = i5;
        }
        int i7 = i3;
        for (int i8 = i2; i8 < (i2 * 3) / 2; i8++) {
            int i9 = i - 2;
            while (i9 >= 0) {
                bArr2[i7] = bArr[(i8 * i) + i9];
                int i10 = i7 + 1;
                bArr2[i10] = bArr[(i8 * i) + i9 + 1];
                i9 -= 2;
                i7 = i10 + 1;
            }
        }
        return bArr2;
    }

    /* access modifiers changed from: private */
    public static short[] mirrorDepth(short[] sArr, int i, int i2) {
        short[] sArr2 = new short[(i * i2)];
        int i3 = 0;
        for (int i4 = 0; i4 < i2; i4++) {
            for (int i5 = i - 1; i5 >= 0; i5--) {
                sArr2[i3] = sArr[(i4 * i) + i5];
                i3++;
            }
        }
        return sArr2;
    }
}
