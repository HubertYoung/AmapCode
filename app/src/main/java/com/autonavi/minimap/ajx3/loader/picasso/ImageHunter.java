package com.autonavi.minimap.ajx3.loader.picasso;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.net.NetworkInfo;
import com.autonavi.minimap.ajx3.image.ImageCache.Image;
import com.autonavi.minimap.ajx3.loader.picasso.Downloader.ResponseException;
import com.autonavi.minimap.ajx3.loader.picasso.Picasso.LoadedFrom;
import com.autonavi.minimap.ajx3.loader.picasso.Picasso.Priority;
import com.autonavi.minimap.ajx3.loader.picasso.RequestHandler.Result;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;
import pl.droidsonroids.gif.GifDrawable;

class ImageHunter implements Runnable {
    private static final Object DECODE_LOCK = new Object();
    private static final RequestHandler ERRORING_HANDLER = new RequestHandler() {
        public final boolean canHandleRequest(Request request) {
            return true;
        }

        public final Result load(Request request, int i) throws IOException {
            throw new IllegalStateException("Unrecognized type of request: ".concat(String.valueOf(request)));
        }
    };
    private static final ThreadLocal<StringBuilder> NAME_BUILDER = new ThreadLocal<StringBuilder>() {
        /* access modifiers changed from: protected */
        public final StringBuilder initialValue() {
            return new StringBuilder("Picasso-");
        }
    };
    private static final AtomicInteger SEQUENCE_GENERATOR = new AtomicInteger();
    Action action;
    List<Action> actions;
    final Cache<Image> cache;
    final Request data;
    final Dispatcher dispatcher;
    Exception exception;
    int exifRotation;
    Future<?> future;
    final String key;
    LoadedFrom loadedFrom;
    final int memoryPolicy;
    int networkPolicy;
    final Picasso picasso;
    Priority priority;
    final RequestHandler requestHandler;
    Image result;
    int retryCount;
    final int sequence = SEQUENCE_GENERATOR.incrementAndGet();
    final Stats stats;

    private static boolean shouldResize(boolean z, int i, int i2, int i3, int i4) {
        return !z || i > i3 || i2 > i4;
    }

    ImageHunter(Picasso picasso2, Dispatcher dispatcher2, Cache cache2, Stats stats2, Action action2, RequestHandler requestHandler2) {
        this.picasso = picasso2;
        this.dispatcher = dispatcher2;
        this.cache = cache2;
        this.stats = stats2;
        this.action = action2;
        this.key = action2.getKey();
        this.data = action2.getRequest();
        this.priority = action2.getPriority();
        this.memoryPolicy = action2.getMemoryPolicy();
        this.networkPolicy = action2.getNetworkPolicy();
        this.requestHandler = requestHandler2;
        this.retryCount = requestHandler2.getRetryCount();
    }

    static Image decodeStream(InputStream inputStream, Request request) throws IOException {
        MarkableInputStream markableInputStream = new MarkableInputStream(inputStream);
        markableInputStream.allowMarksToExpire(false);
        long savePosition = markableInputStream.savePosition(1024);
        Options createBitmapOptions = RequestHandler.createBitmapOptions(request);
        boolean requiresInSampleSize = RequestHandler.requiresInSampleSize(createBitmapOptions);
        boolean isGifFile = Utils.isGifFile(markableInputStream);
        markableInputStream.reset(savePosition);
        if (isGifFile) {
            GifDrawable decodeGifFile = decodeGifFile(markableInputStream);
            if (decodeGifFile == null) {
                return null;
            }
            Image image = new Image();
            image.gif = decodeGifFile;
            return image;
        }
        Bitmap decodeStream = decodeStream(markableInputStream, request, createBitmapOptions, requiresInSampleSize, markableInputStream, savePosition);
        Image image2 = new Image();
        image2.bitmap = decodeStream;
        return image2;
    }

    private static GifDrawable decodeGifFile(InputStream inputStream) throws IOException {
        return new GifDrawable(Utils.toByteArray(inputStream));
    }

    private static Bitmap decodeWebPFile(InputStream inputStream, Request request, Options options, boolean z) throws IOException {
        byte[] byteArray = Utils.toByteArray(inputStream);
        if (z) {
            BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length, options);
            RequestHandler.calculateInSampleSize(request.targetWidth, request.targetHeight, options, request);
        }
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length, options);
    }

    private static Bitmap decodeStream(InputStream inputStream, Request request, Options options, boolean z, MarkableInputStream markableInputStream, long j) throws IOException {
        if (z) {
            BitmapFactory.decodeStream(inputStream, null, options);
            RequestHandler.calculateInSampleSize(request.targetWidth, request.targetHeight, options, request);
            markableInputStream.reset(j);
        }
        markableInputStream.allowMarksToExpire(true);
        Bitmap decodeStream = BitmapFactory.decodeStream(inputStream, null, options);
        if (decodeStream != null) {
            return decodeStream;
        }
        throw new IOException("Failed to decode stream.");
    }

    public void run() {
        try {
            updateThreadName(this.data);
            if (this.picasso.loggingEnabled) {
                Utils.log("Hunter", "executing", Utils.getLogIdsForHunter(this));
            }
            this.result = hunt();
            if (this.result == null) {
                this.dispatcher.dispatchFailed(this);
            } else {
                this.dispatcher.dispatchComplete(this);
            }
        } catch (ResponseException e) {
            if (!e.localCacheOnly || e.responseCode != 504) {
                this.exception = e;
            }
            this.dispatcher.dispatchFailed(this);
        } catch (ContentLengthException e2) {
            this.exception = e2;
            this.dispatcher.dispatchRetry(this);
        } catch (IOException e3) {
            this.exception = e3;
            this.dispatcher.dispatchRetry(this);
        } catch (OutOfMemoryError e4) {
            StringWriter stringWriter = new StringWriter();
            this.stats.createSnapshot().dump(new PrintWriter(stringWriter));
            this.exception = new RuntimeException(stringWriter.toString(), e4);
            this.dispatcher.dispatchFailed(this);
        } catch (Exception e5) {
            this.exception = e5;
            this.dispatcher.dispatchFailed(this);
        } catch (Throwable th) {
            Thread.currentThread().setName("Picasso-Idle");
            throw th;
        }
        Thread.currentThread().setName("Picasso-Idle");
    }

    /* access modifiers changed from: 0000 */
    public Image hunt() throws IOException {
        Image image;
        boolean z;
        if (MemoryPolicy.shouldReadFromMemoryCache(this.memoryPolicy)) {
            image = (Image) this.cache.get(this.key);
            if (image != null) {
                this.stats.dispatchCacheHit();
                this.loadedFrom = LoadedFrom.MEMORY;
                if (this.picasso.loggingEnabled) {
                    Utils.log("Hunter", "decoded", this.data.logId(), "from cache");
                }
                return image;
            }
        } else {
            image = null;
        }
        this.data.networkPolicy = this.retryCount == 0 ? NetworkPolicy.OFFLINE.index : this.networkPolicy;
        Result load = this.requestHandler.load(this.data, this.networkPolicy);
        if (load != null) {
            this.loadedFrom = load.getLoadedFrom();
            this.exifRotation = load.getExifOrientation();
            GifDrawable gifDrawable = load.getGifDrawable();
            if (!(load.getBitmap() == null && gifDrawable == null)) {
                image = new Image();
                image.gif = load.getGifDrawable();
                image.bitmap = load.getBitmap();
            }
            if (image == null) {
                InputStream stream = load.getStream();
                if (stream != null) {
                    try {
                        image = decodeStream(stream, this.data);
                    } finally {
                        Utils.closeQuietly(stream);
                    }
                }
            }
        }
        if (!(image == null || image.bitmap == null)) {
            if (this.picasso.loggingEnabled) {
                Utils.log("Hunter", "decoded", this.data.logId());
            }
            this.stats.dispatchBitmapDecoded(image.bitmap);
            if (this.data.needsTransformation() || this.exifRotation != 0) {
                synchronized (DECODE_LOCK) {
                    try {
                        if (this.data.needsMatrixTransform() || this.exifRotation != 0) {
                            image.bitmap = transformResult(this.data, image.bitmap, this.exifRotation);
                            if (this.picasso.loggingEnabled) {
                                Utils.log("Hunter", "transformed", this.data.logId());
                            }
                        }
                        if (this.data.hasCustomTransformations()) {
                            image.bitmap = applyCustomTransformations(this.data.transformations, image.bitmap);
                            if (this.picasso.loggingEnabled) {
                                Utils.log("Hunter", "transformed", this.data.logId(), "from custom transformations");
                            }
                        }
                    }
                }
                if (image != null) {
                    this.stats.dispatchBitmapTransformed(image.bitmap);
                }
            }
            if (MemoryPolicy.shouldWriteToMemoryCache(this.memoryPolicy)) {
                this.cache.set(this.key, image);
            }
            if (this.loadedFrom == LoadedFrom.NETWORK && NetworkPolicy.shouldWriteToDiskCache(this.networkPolicy)) {
                String generateImageName = Utils.generateImageName(this.data.uri.toString());
                if (load.isWebResource) {
                    z = this.picasso.webCache.save(generateImageName, image.bitmap);
                } else {
                    z = this.picasso.diskCache.save(generateImageName, image.bitmap);
                }
                Utils.log("Hunter", "disk_cache", this.data.logId(), "Disk cache ".concat(String.valueOf(z)));
            }
        }
        return image;
    }

    /* access modifiers changed from: 0000 */
    public void attach(Action action2) {
        boolean z = this.picasso.loggingEnabled;
        Request request = action2.request;
        if (this.action == null) {
            this.action = action2;
            if (z) {
                if (this.actions == null || this.actions.isEmpty()) {
                    Utils.log("Hunter", "joined", request.logId(), "to empty hunter");
                    return;
                }
                Utils.log("Hunter", "joined", request.logId(), Utils.getLogIdsForHunter(this, "to "));
            }
            return;
        }
        if (this.actions == null) {
            this.actions = new ArrayList(3);
        }
        this.actions.add(action2);
        if (z) {
            Utils.log("Hunter", "joined", request.logId(), Utils.getLogIdsForHunter(this, "to "));
        }
        Priority priority2 = action2.getPriority();
        if (priority2.ordinal() > this.priority.ordinal()) {
            this.priority = priority2;
        }
    }

    /* access modifiers changed from: 0000 */
    public void detach(Action action2) {
        boolean z;
        if (this.action == action2) {
            this.action = null;
            z = true;
        } else {
            z = this.actions != null ? this.actions.remove(action2) : false;
        }
        if (z && action2.getPriority() == this.priority) {
            this.priority = computeNewPriority();
        }
        if (this.picasso.loggingEnabled) {
            Utils.log("Hunter", "removed", action2.request.logId(), Utils.getLogIdsForHunter(this, "from "));
        }
    }

    private Priority computeNewPriority() {
        Priority priority2 = Priority.LOW;
        boolean z = true;
        boolean z2 = this.actions != null && !this.actions.isEmpty();
        if (this.action == null && !z2) {
            z = false;
        }
        if (!z) {
            return priority2;
        }
        if (this.action != null) {
            priority2 = this.action.getPriority();
        }
        if (z2) {
            int size = this.actions.size();
            for (int i = 0; i < size; i++) {
                Priority priority3 = this.actions.get(i).getPriority();
                if (priority3.ordinal() > priority2.ordinal()) {
                    priority2 = priority3;
                }
            }
        }
        return priority2;
    }

    /* access modifiers changed from: 0000 */
    public boolean cancel() {
        if (this.action != null || ((this.actions != null && !this.actions.isEmpty()) || this.future == null || !this.future.cancel(false))) {
            return false;
        }
        return true;
    }

    /* access modifiers changed from: 0000 */
    public boolean isCancelled() {
        return this.future != null && this.future.isCancelled();
    }

    /* access modifiers changed from: 0000 */
    public boolean shouldRetry(boolean z, NetworkInfo networkInfo) {
        if (!(this.retryCount > 0)) {
            return false;
        }
        this.retryCount--;
        return this.requestHandler.shouldRetry(z, networkInfo);
    }

    /* access modifiers changed from: 0000 */
    public boolean supportsReplay() {
        return this.requestHandler.supportsReplay();
    }

    /* access modifiers changed from: 0000 */
    public Image getResult() {
        return this.result;
    }

    /* access modifiers changed from: 0000 */
    public String getKey() {
        return this.key;
    }

    /* access modifiers changed from: 0000 */
    public int getMemoryPolicy() {
        return this.memoryPolicy;
    }

    /* access modifiers changed from: 0000 */
    public Request getData() {
        return this.data;
    }

    /* access modifiers changed from: 0000 */
    public Action getAction() {
        return this.action;
    }

    /* access modifiers changed from: 0000 */
    public Picasso getPicasso() {
        return this.picasso;
    }

    /* access modifiers changed from: 0000 */
    public List<Action> getActions() {
        return this.actions;
    }

    /* access modifiers changed from: 0000 */
    public Exception getException() {
        return this.exception;
    }

    /* access modifiers changed from: 0000 */
    public LoadedFrom getLoadedFrom() {
        return this.loadedFrom;
    }

    /* access modifiers changed from: 0000 */
    public Priority getPriority() {
        return this.priority;
    }

    static void updateThreadName(Request request) {
        String name = request.getName();
        StringBuilder sb = NAME_BUILDER.get();
        sb.ensureCapacity(name.length() + 8);
        sb.replace(8, sb.length(), name);
        Thread.currentThread().setName(sb.toString());
    }

    static ImageHunter forRequest(Picasso picasso2, Dispatcher dispatcher2, Cache cache2, Stats stats2, Action action2) {
        Request request = action2.getRequest();
        List<RequestHandler> requestHandlers = picasso2.getRequestHandlers();
        int size = requestHandlers.size();
        for (int i = 0; i < size; i++) {
            RequestHandler requestHandler2 = requestHandlers.get(i);
            if (requestHandler2.canHandleRequest(request)) {
                ImageHunter imageHunter = new ImageHunter(picasso2, dispatcher2, cache2, stats2, action2, requestHandler2);
                return imageHunter;
            }
        }
        ImageHunter imageHunter2 = new ImageHunter(picasso2, dispatcher2, cache2, stats2, action2, ERRORING_HANDLER);
        return imageHunter2;
    }

    static Bitmap applyCustomTransformations(List<Transformation> list, Bitmap bitmap) {
        int size = list.size();
        int i = 0;
        while (i < size) {
            final Transformation transformation = list.get(i);
            try {
                Bitmap transform = transformation.transform(bitmap);
                if (transform == null) {
                    final StringBuilder sb = new StringBuilder("Transformation ");
                    sb.append(transformation.key());
                    sb.append(" returned null after ");
                    sb.append(i);
                    sb.append(" previous transformation(s).\n\nTransformation list:\n");
                    for (Transformation key2 : list) {
                        sb.append(key2.key());
                        sb.append(10);
                    }
                    Picasso.HANDLER.post(new Runnable() {
                        public final void run() {
                            throw new NullPointerException(sb.toString());
                        }
                    });
                    return null;
                } else if (transform == bitmap && bitmap.isRecycled()) {
                    Picasso.HANDLER.post(new Runnable() {
                        public final void run() {
                            StringBuilder sb = new StringBuilder("Transformation ");
                            sb.append(transformation.key());
                            sb.append(" returned input Bitmap but recycled it.");
                            throw new IllegalStateException(sb.toString());
                        }
                    });
                    return null;
                } else if (transform == bitmap || bitmap.isRecycled()) {
                    i++;
                    bitmap = transform;
                } else {
                    Picasso.HANDLER.post(new Runnable() {
                        public final void run() {
                            StringBuilder sb = new StringBuilder("Transformation ");
                            sb.append(transformation.key());
                            sb.append(" mutated input Bitmap but failed to recycle the original.");
                            throw new IllegalStateException(sb.toString());
                        }
                    });
                    return null;
                }
            } catch (RuntimeException e) {
                Picasso.HANDLER.post(new Runnable() {
                    public final void run() {
                        StringBuilder sb = new StringBuilder("Transformation ");
                        sb.append(transformation.key());
                        sb.append(" crashed with exception.");
                        throw new RuntimeException(sb.toString(), e);
                    }
                });
                return null;
            }
        }
        return bitmap;
    }

    /* JADX WARNING: Removed duplicated region for block: B:43:0x00b6  */
    /* JADX WARNING: Removed duplicated region for block: B:46:0x00c2  */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x00c6  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static android.graphics.Bitmap transformResult(com.autonavi.minimap.ajx3.loader.picasso.Request r13, android.graphics.Bitmap r14, int r15) {
        /*
            int r0 = r14.getWidth()
            int r1 = r14.getHeight()
            boolean r2 = r13.onlyScaleDown
            android.graphics.Matrix r8 = new android.graphics.Matrix
            r8.<init>()
            boolean r3 = r13.needsMatrixTransform()
            r4 = 0
            if (r3 == 0) goto L_0x00b1
            int r3 = r13.targetWidth
            int r5 = r13.targetHeight
            float r6 = r13.rotationDegrees
            r7 = 0
            int r7 = (r6 > r7 ? 1 : (r6 == r7 ? 0 : -1))
            if (r7 == 0) goto L_0x0030
            boolean r7 = r13.hasRotationPivot
            if (r7 == 0) goto L_0x002d
            float r7 = r13.rotationPivotX
            float r9 = r13.rotationPivotY
            r8.setRotate(r6, r7, r9)
            goto L_0x0030
        L_0x002d:
            r8.setRotate(r6)
        L_0x0030:
            boolean r6 = r13.centerCrop
            if (r6 == 0) goto L_0x0074
            float r13 = (float) r3
            float r6 = (float) r0
            float r7 = r13 / r6
            float r9 = (float) r5
            float r10 = (float) r1
            float r11 = r9 / r10
            int r12 = (r7 > r11 ? 1 : (r7 == r11 ? 0 : -1))
            if (r12 <= 0) goto L_0x0054
            float r11 = r11 / r7
            float r10 = r10 * r11
            double r10 = (double) r10
            double r10 = java.lang.Math.ceil(r10)
            int r13 = (int) r10
            int r6 = r1 - r13
            int r6 = r6 / 2
            float r10 = (float) r13
            float r11 = r9 / r10
            r9 = r13
            r13 = r7
            r7 = r0
            goto L_0x0067
        L_0x0054:
            float r7 = r7 / r11
            float r6 = r6 * r7
            double r6 = (double) r6
            double r6 = java.lang.Math.ceil(r6)
            int r6 = (int) r6
            int r7 = r0 - r6
            int r7 = r7 / 2
            float r9 = (float) r6
            float r13 = r13 / r9
            r9 = r1
            r4 = r7
            r7 = r6
            r6 = 0
        L_0x0067:
            boolean r0 = shouldResize(r2, r0, r1, r3, r5)
            if (r0 == 0) goto L_0x0070
            r8.preScale(r13, r11)
        L_0x0070:
            r5 = r6
            r6 = r7
            r7 = r9
            goto L_0x00b4
        L_0x0074:
            boolean r13 = r13.centerInside
            if (r13 == 0) goto L_0x008e
            float r13 = (float) r3
            float r6 = (float) r0
            float r13 = r13 / r6
            float r6 = (float) r5
            float r7 = (float) r1
            float r6 = r6 / r7
            int r7 = (r13 > r6 ? 1 : (r13 == r6 ? 0 : -1))
            if (r7 >= 0) goto L_0x0083
            goto L_0x0084
        L_0x0083:
            r13 = r6
        L_0x0084:
            boolean r2 = shouldResize(r2, r0, r1, r3, r5)
            if (r2 == 0) goto L_0x00b1
            r8.preScale(r13, r13)
            goto L_0x00b1
        L_0x008e:
            if (r3 != 0) goto L_0x0092
            if (r5 == 0) goto L_0x00b1
        L_0x0092:
            if (r3 != r0) goto L_0x0096
            if (r5 == r1) goto L_0x00b1
        L_0x0096:
            if (r3 == 0) goto L_0x009c
            float r13 = (float) r3
            float r6 = (float) r0
        L_0x009a:
            float r13 = r13 / r6
            goto L_0x009f
        L_0x009c:
            float r13 = (float) r5
            float r6 = (float) r1
            goto L_0x009a
        L_0x009f:
            if (r5 == 0) goto L_0x00a5
            float r6 = (float) r5
            float r7 = (float) r1
        L_0x00a3:
            float r6 = r6 / r7
            goto L_0x00a8
        L_0x00a5:
            float r6 = (float) r3
            float r7 = (float) r0
            goto L_0x00a3
        L_0x00a8:
            boolean r2 = shouldResize(r2, r0, r1, r3, r5)
            if (r2 == 0) goto L_0x00b1
            r8.preScale(r13, r6)
        L_0x00b1:
            r6 = r0
            r7 = r1
            r5 = 0
        L_0x00b4:
            if (r15 == 0) goto L_0x00ba
            float r13 = (float) r15
            r8.preRotate(r13)
        L_0x00ba:
            r9 = 1
            r3 = r14
            android.graphics.Bitmap r13 = android.graphics.Bitmap.createBitmap(r3, r4, r5, r6, r7, r8, r9)
            if (r13 == r14) goto L_0x00c6
            r14.recycle()
            goto L_0x00c7
        L_0x00c6:
            r13 = r14
        L_0x00c7:
            return r13
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.ajx3.loader.picasso.ImageHunter.transformResult(com.autonavi.minimap.ajx3.loader.picasso.Request, android.graphics.Bitmap, int):android.graphics.Bitmap");
    }
}
