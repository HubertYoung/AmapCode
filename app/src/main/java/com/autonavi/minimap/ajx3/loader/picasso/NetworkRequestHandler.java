package com.autonavi.minimap.ajx3.loader.picasso;

import android.graphics.Bitmap;
import android.net.NetworkInfo;
import com.autonavi.minimap.ajx3.loader.picasso.Downloader.Response;
import com.autonavi.minimap.ajx3.loader.picasso.Picasso.LoadedFrom;
import com.autonavi.minimap.ajx3.loader.picasso.RequestHandler.Result;
import com.autonavi.minimap.ajx3.util.LogHelper;
import java.io.IOException;
import java.io.InputStream;

class NetworkRequestHandler extends RequestHandler {
    static final int RETRY_COUNT = 2;
    protected static final String SCHEME_HTTP = "http";
    protected static final String SCHEME_HTTPS = "https";
    private final AbstractDiskCache diskCache;
    private final Downloader downloader;
    private final boolean isWebResource;
    private final Stats stats;

    static class ContentLengthException extends IOException {
        public ContentLengthException(String str) {
            super(str);
        }
    }

    /* access modifiers changed from: 0000 */
    public int getRetryCount() {
        return 2;
    }

    /* access modifiers changed from: 0000 */
    public boolean supportsReplay() {
        return true;
    }

    public NetworkRequestHandler(Downloader downloader2, Stats stats2, AbstractDiskCache abstractDiskCache) {
        this(downloader2, stats2, abstractDiskCache, false);
    }

    public NetworkRequestHandler(Downloader downloader2, Stats stats2, AbstractDiskCache abstractDiskCache, boolean z) {
        this.downloader = downloader2;
        this.stats = stats2;
        this.diskCache = abstractDiskCache;
        this.isWebResource = z;
    }

    public boolean canHandleRequest(Request request) {
        String scheme = request.uri.getScheme();
        return "http".equals(scheme) || "https".equals(scheme);
    }

    public Result load(Request request, int i) throws IOException {
        Utils.checkNotMain();
        String generateImageName = Utils.generateImageName(request.uri.toString());
        if (NetworkPolicy.isOfflineOnly(i)) {
            InputStream inputStream = this.diskCache != null ? this.diskCache.get(generateImageName) : null;
            if (inputStream == null) {
                return null;
            }
            StringBuilder sb = new StringBuilder("NetworkRequestHandler offline 命中 DiskCache url =  ");
            sb.append(request.uri.toString());
            LogHelper.d(sb.toString());
            return new Result(inputStream, LoadedFrom.DISK, this.isWebResource);
        }
        if (NetworkPolicy.shouldReadFromDiskCache(i) && this.diskCache != null) {
            InputStream inputStream2 = this.diskCache.get(generateImageName);
            if (inputStream2 != null) {
                StringBuilder sb2 = new StringBuilder("NetworkRequestHandler 命中 DiskCache url =  ");
                sb2.append(request.uri.toString());
                LogHelper.d(sb2.toString());
                return new Result(inputStream2, LoadedFrom.DISK, this.isWebResource);
            }
        }
        Response load = this.downloader.load(request.uri, request.networkPolicy);
        if (load == null) {
            return null;
        }
        LoadedFrom loadedFrom = load.cached ? LoadedFrom.DISK : LoadedFrom.NETWORK;
        Bitmap bitmap = load.getBitmap();
        if (bitmap != null) {
            return new Result(bitmap, loadedFrom, this.isWebResource);
        }
        InputStream inputStream3 = load.getInputStream();
        if (inputStream3 == null) {
            return null;
        }
        if (loadedFrom == LoadedFrom.DISK && load.getContentLength() == 0) {
            Utils.closeQuietly(inputStream3);
            throw new ContentLengthException("Received response with 0 content-length header.");
        }
        if (loadedFrom == LoadedFrom.NETWORK && load.getContentLength() > 0) {
            this.stats.dispatchDownloadFinished(load.getContentLength());
        }
        return new Result(inputStream3, loadedFrom, this.isWebResource);
    }

    /* access modifiers changed from: 0000 */
    public boolean shouldRetry(boolean z, NetworkInfo networkInfo) {
        return networkInfo == null || networkInfo.isConnected();
    }
}
