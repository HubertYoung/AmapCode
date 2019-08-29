package com.squareup.picasso;

import android.graphics.Bitmap;
import android.net.NetworkInfo;
import com.squareup.picasso.Downloader.Response;
import com.squareup.picasso.Picasso.LoadedFrom;
import com.squareup.picasso.RequestHandler.Result;
import java.io.IOException;
import java.io.InputStream;

class NetworkRequestHandler extends RequestHandler {
    static final int RETRY_COUNT = 2;
    private static final String SCHEME_HTTP = "http";
    private static final String SCHEME_HTTPS = "https";
    private final Downloader downloader;
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

    public NetworkRequestHandler(Downloader downloader2, Stats stats2) {
        this.downloader = downloader2;
        this.stats = stats2;
    }

    public boolean canHandleRequest(Request request) {
        String scheme = request.uri.getScheme();
        return "http".equals(scheme) || "https".equals(scheme);
    }

    public Result load(Request request, int i) throws IOException {
        Response load = this.downloader.load(request.uri, request.networkPolicy);
        if (load == null) {
            return null;
        }
        LoadedFrom loadedFrom = load.cached ? LoadedFrom.DISK : LoadedFrom.NETWORK;
        Bitmap bitmap = load.getBitmap();
        if (bitmap != null) {
            return new Result(bitmap, loadedFrom);
        }
        InputStream inputStream = load.getInputStream();
        if (inputStream == null) {
            return null;
        }
        if (loadedFrom == LoadedFrom.DISK && load.getContentLength() == 0) {
            Utils.closeQuietly(inputStream);
            throw new ContentLengthException("Received response with 0 content-length header.");
        }
        if (loadedFrom == LoadedFrom.NETWORK && load.getContentLength() > 0) {
            this.stats.dispatchDownloadFinished(load.getContentLength());
        }
        return new Result(inputStream, loadedFrom);
    }

    /* access modifiers changed from: 0000 */
    public boolean shouldRetry(boolean z, NetworkInfo networkInfo) {
        return networkInfo == null || networkInfo.isConnected();
    }
}
