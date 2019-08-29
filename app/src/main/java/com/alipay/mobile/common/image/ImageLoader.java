package com.alipay.mobile.common.image;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.net.Uri;
import android.util.Log;
import com.alibaba.wireless.security.SecExceptionCode;
import com.alipay.android.hackbyte.ClassVerifier;
import com.alipay.mobile.common.cache.disk.CacheException;
import com.alipay.mobile.common.cache.disk.DiskCache;
import com.alipay.mobile.common.cache.mem.MemCache;
import com.alipay.mobile.common.info.DeviceInfo;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.task.AsyncTaskExecutor;
import com.alipay.mobile.common.transport.Request;
import com.alipay.mobile.common.transport.Response;
import com.alipay.mobile.common.transport.TransportCallback;
import com.alipay.mobile.common.transport.http.HttpManager;
import com.alipay.mobile.common.transport.http.HttpUrlRequest;
import com.alipay.mobile.common.transport.http.HttpUrlResponse;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Future;

public class ImageLoader {
    /* access modifiers changed from: private */
    public DiskCache a;
    /* access modifiers changed from: private */
    public MemCache<Bitmap> b;
    private HttpManager c;
    /* access modifiers changed from: private */
    public Map<String, HttpCallback> d = new HashMap();
    /* access modifiers changed from: private */
    public Map<String, Future<?>> e = new HashMap();

    class HttpCallback implements TransportCallback {
        private String a;
        private String b;
        private Set<ImageLoaderListener> c = new HashSet();
        private int d;
        private int e;
        private ImageCacheListener f;

        public HttpCallback(String owner, String group, int width, int height, ImageCacheListener imageCacheListener) {
            this.a = owner;
            this.b = group;
            this.d = width;
            this.e = height;
            this.f = imageCacheListener;
            if (Boolean.FALSE.booleanValue()) {
                Log.v("hackbyte ", ClassVerifier.class.toString());
            }
        }

        public void addListener(ImageLoaderListener listener) {
            this.c.add(listener);
        }

        public void removeListener(ImageLoaderListener listener) {
            this.c.remove(listener);
        }

        public int getListenerSize() {
            return this.c.size();
        }

        public void onProgressUpdate(Request request, double percent) {
            for (ImageLoaderListener listener : this.c) {
                listener.onProgressUpdate(((HttpUrlRequest) request).getUrl(), percent);
            }
        }

        public void onPreExecute(Request request) {
            for (ImageLoaderListener listener : this.c) {
                listener.onPreLoad(((HttpUrlRequest) request).getUrl());
            }
        }

        public void onPostExecute(Request request, Response response) {
            long period;
            HttpUrlRequest httpUrlRequest = (HttpUrlRequest) request;
            synchronized (ImageLoader.this.d) {
                if (this.c.size() > 0) {
                    HttpUrlResponse httpUrlResponse = (HttpUrlResponse) response;
                    byte[] data = response.getResData();
                    Bitmap bitmap = ImageLoader.this.createBitmap(data, this.d, this.e);
                    if (bitmap != null) {
                        for (ImageLoaderListener onPostLoad : this.c) {
                            onPostLoad.onPostLoad(httpUrlRequest.getUrl(), bitmap);
                        }
                        ImageLoader.this.b.put(this.a, this.b, httpUrlRequest.getUrl(), bitmap);
                        if (this.f != null) {
                            period = this.f.getCachePeriod(httpUrlRequest, httpUrlResponse);
                        } else {
                            period = httpUrlResponse.getPeriod() * 1000;
                        }
                        ImageLoader.this.a.put(this.a, this.b, httpUrlRequest.getUrl(), data, httpUrlResponse.getCreateTime(), period, httpUrlResponse.getContentType());
                    } else {
                        for (ImageLoaderListener onFailed : this.c) {
                            onFailed.onFailed(httpUrlRequest.getUrl(), SecExceptionCode.SEC_ERROR_UMID_UNKNOWN_ERR, "网络请求返回的不是图片");
                        }
                    }
                }
                ImageLoader.this.d.remove(httpUrlRequest.getUrl());
                ImageLoader.this.e.remove(httpUrlRequest.getUrl());
            }
        }

        public void onFailed(Request request, int code, String msg) {
            HttpUrlRequest httpUrlRequest = (HttpUrlRequest) request;
            synchronized (ImageLoader.this.d) {
                for (ImageLoaderListener onFailed : this.c) {
                    onFailed.onFailed(httpUrlRequest.getUrl(), code, msg);
                }
                ImageLoader.this.d.remove(httpUrlRequest.getUrl());
                ImageLoader.this.e.remove(httpUrlRequest.getUrl());
            }
        }

        public void onCancelled(Request request) {
            HttpUrlRequest httpUrlRequest = (HttpUrlRequest) request;
            synchronized (ImageLoader.this.d) {
                for (ImageLoaderListener onCancelled : this.c) {
                    onCancelled.onCancelled(httpUrlRequest.getUrl());
                }
                ImageLoader.this.d.remove(httpUrlRequest.getUrl());
                ImageLoader.this.e.remove(httpUrlRequest.getUrl());
            }
        }
    }

    public ImageLoader(HttpManager transport, DiskCache diskCache, MemCache<Bitmap> memCache) {
        this.c = transport;
        this.a = diskCache;
        this.b = memCache;
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public void startLoad(String owner, String group, String path, ImageLoaderListener listener, int width, int height) {
        startLoad(owner, group, path, listener, width, height, null);
    }

    public void startLoad(String owner, String group, String path, ImageLoaderListener listener, int width, int height, ImageCacheListener imageCacheListener) {
        if (path == null) {
            LoggerFactory.getTraceLogger().error((String) "ImageLoader", (String) "path must be set.");
        } else if (!a(owner, path, listener)) {
            Uri uri = Uri.parse(path);
            if (!"http".equalsIgnoreCase(uri.getScheme()) && !"https".equalsIgnoreCase(uri.getScheme())) {
                a(owner, group, path, listener, width, height);
            } else if (!b(owner, group, path, listener, width, height)) {
                a(owner, group, path, listener, width, height, imageCacheListener);
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:15:?, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void cancel(java.lang.String r5, com.alipay.mobile.common.image.ImageLoaderListener r6) {
        /*
            r4 = this;
            java.util.Map<java.lang.String, com.alipay.mobile.common.image.ImageLoader$HttpCallback> r2 = r4.d
            monitor-enter(r2)
            java.util.Map<java.lang.String, com.alipay.mobile.common.image.ImageLoader$HttpCallback> r1 = r4.d     // Catch:{ all -> 0x0026 }
            java.lang.Object r0 = r1.get(r5)     // Catch:{ all -> 0x0026 }
            com.alipay.mobile.common.image.ImageLoader$HttpCallback r0 = (com.alipay.mobile.common.image.ImageLoader.HttpCallback) r0     // Catch:{ all -> 0x0026 }
            if (r0 != 0) goto L_0x000f
            monitor-exit(r2)     // Catch:{ all -> 0x0026 }
        L_0x000e:
            return
        L_0x000f:
            r0.removeListener(r6)     // Catch:{ all -> 0x0026 }
            int r1 = r0.getListenerSize()     // Catch:{ all -> 0x0026 }
            if (r1 > 0) goto L_0x0024
            java.util.Map<java.lang.String, java.util.concurrent.Future<?>> r1 = r4.e     // Catch:{ all -> 0x0026 }
            java.lang.Object r1 = r1.get(r5)     // Catch:{ all -> 0x0026 }
            java.util.concurrent.Future r1 = (java.util.concurrent.Future) r1     // Catch:{ all -> 0x0026 }
            r3 = 1
            r1.cancel(r3)     // Catch:{ all -> 0x0026 }
        L_0x0024:
            monitor-exit(r2)     // Catch:{ all -> 0x0026 }
            goto L_0x000e
        L_0x0026:
            r1 = move-exception
            monitor-exit(r2)     // Catch:{ all -> 0x0026 }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.common.image.ImageLoader.cancel(java.lang.String, com.alipay.mobile.common.image.ImageLoaderListener):void");
    }

    private void a(String owner, String group, String path, ImageLoaderListener listener, int width, int height, ImageCacheListener imageCacheListener) {
        synchronized (this.d) {
            if (this.d.containsKey(path)) {
                this.d.get(path).addListener(listener);
            } else {
                HttpUrlRequest request = new HttpUrlRequest(path);
                HttpCallback httpCallback = new HttpCallback(owner, group, width, height, imageCacheListener);
                httpCallback.addListener(listener);
                request.setTransportCallback(httpCallback);
                this.e.put(path, this.c.execute(request));
                this.d.put(path, httpCallback);
            }
        }
    }

    private void a(String owner, String group, String path, ImageLoaderListener listener, int width, int height) {
        final ImageLoaderListener imageLoaderListener = listener;
        final String str = path;
        final int i = width;
        final int i2 = height;
        final String str2 = owner;
        final String str3 = group;
        AsyncTaskExecutor.getInstance().execute(new Runnable() {
            {
                if (Boolean.FALSE.booleanValue()) {
                    Log.v("hackbyte ", ClassVerifier.class.toString());
                }
            }

            public void run() {
                imageLoaderListener.onPreLoad(str);
                Bitmap bitmap = ImageLoader.this.createBitmap(str, i, i2);
                if (bitmap != null) {
                    imageLoaderListener.onPostLoad(str, bitmap);
                    ImageLoader.this.b.put(str2, str3, str, bitmap);
                    return;
                }
                imageLoaderListener.onFailed(str, 0, "can't load.");
            }
        });
    }

    private boolean a(String owner, String path, ImageLoaderListener listener) {
        Bitmap bitmap = (Bitmap) this.b.get(owner, path);
        if (bitmap == null || bitmap.isRecycled()) {
            return false;
        }
        listener.onPostLoad(path, bitmap);
        listener.onProgressUpdate(path, 1.0d);
        return true;
    }

    private boolean b(String owner, String group, String path, ImageLoaderListener listener, int width, int height) {
        this.a.open();
        try {
            byte[] data = this.a.get(owner, path);
            if (data != null) {
                listener.onPreLoad(path);
                Bitmap bitmap = createBitmap(data, width, height);
                if (bitmap != null) {
                    listener.onPostLoad(path, bitmap);
                    this.b.put(owner, group, path, bitmap);
                    this.a.close();
                    return true;
                }
                this.a.remove(path);
            }
        } catch (CacheException e2) {
            LoggerFactory.getTraceLogger().error((String) "ImageLoader", "[" + e2.getCode() + "]" + e2.getMsg());
        } finally {
            this.a.close();
        }
        return false;
    }

    public Bitmap createBitmap(Object data, int width, int height) {
        Options options = new Options();
        int scale = 1;
        if (width > 0 && height > 0) {
            options.inJustDecodeBounds = true;
            if (data instanceof String) {
                BitmapFactory.decodeFile((String) data, options);
            } else {
                BitmapFactory.decodeByteArray((byte[]) data, 0, ((byte[]) data).length, options);
            }
            scale = Math.max(options.outWidth / width, options.outHeight / height);
            options = new Options();
        }
        options.inDensity = DeviceInfo.getInstance().getDencity();
        options.inScaled = true;
        options.inPurgeable = true;
        options.inSampleSize = scale;
        if (data instanceof String) {
            return BitmapFactory.decodeFile((String) data, options);
        }
        return BitmapFactory.decodeByteArray((byte[]) data, 0, ((byte[]) data).length, options);
    }
}
