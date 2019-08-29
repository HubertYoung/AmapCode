package com.squareup.picasso;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory.Options;
import android.net.NetworkInfo;
import com.squareup.picasso.Picasso.LoadedFrom;
import java.io.IOException;
import java.io.InputStream;

public abstract class RequestHandler {

    public static final class Result {
        private final Bitmap bitmap;
        private final int exifOrientation;
        private final LoadedFrom loadedFrom;
        private final InputStream stream;

        public Result(Bitmap bitmap2, LoadedFrom loadedFrom2) {
            this((Bitmap) Utils.checkNotNull(bitmap2, "bitmap == null"), null, loadedFrom2, 0);
        }

        public Result(InputStream inputStream, LoadedFrom loadedFrom2) {
            this(null, (InputStream) Utils.checkNotNull(inputStream, "stream == null"), loadedFrom2, 0);
        }

        Result(Bitmap bitmap2, InputStream inputStream, LoadedFrom loadedFrom2, int i) {
            boolean z = false;
            if (!((inputStream != null ? true : z) ^ (bitmap2 != null))) {
                throw new AssertionError();
            }
            this.bitmap = bitmap2;
            this.stream = inputStream;
            this.loadedFrom = (LoadedFrom) Utils.checkNotNull(loadedFrom2, "loadedFrom == null");
            this.exifOrientation = i;
        }

        public final Bitmap getBitmap() {
            return this.bitmap;
        }

        public final InputStream getStream() {
            return this.stream;
        }

        public final LoadedFrom getLoadedFrom() {
            return this.loadedFrom;
        }

        /* access modifiers changed from: 0000 */
        public final int getExifOrientation() {
            return this.exifOrientation;
        }
    }

    public abstract boolean canHandleRequest(Request request);

    /* access modifiers changed from: 0000 */
    public int getRetryCount() {
        return 0;
    }

    public abstract Result load(Request request, int i) throws IOException;

    /* access modifiers changed from: 0000 */
    public boolean shouldRetry(boolean z, NetworkInfo networkInfo) {
        return false;
    }

    /* access modifiers changed from: 0000 */
    public boolean supportsReplay() {
        return false;
    }

    static Options createBitmapOptions(Request request) {
        boolean hasSize = request.hasSize();
        boolean z = request.config != null;
        Options options = null;
        if (hasSize || z) {
            options = new Options();
            options.inJustDecodeBounds = hasSize;
            if (z) {
                options.inPreferredConfig = request.config;
            }
        }
        return options;
    }

    static boolean requiresInSampleSize(Options options) {
        return options != null && options.inJustDecodeBounds;
    }

    static void calculateInSampleSize(int i, int i2, Options options, Request request) {
        calculateInSampleSize(i, i2, options.outWidth, options.outHeight, options, request);
    }

    static void calculateInSampleSize(int i, int i2, int i3, int i4, Options options, Request request) {
        int i5;
        if (i4 <= i2 && i3 <= i) {
            i5 = 1;
        } else if (i2 == 0) {
            i5 = (int) Math.floor((double) (((float) i3) / ((float) i)));
        } else if (i == 0) {
            i5 = (int) Math.floor((double) (((float) i4) / ((float) i2)));
        } else {
            int floor = (int) Math.floor((double) (((float) i4) / ((float) i2)));
            int floor2 = (int) Math.floor((double) (((float) i3) / ((float) i)));
            i5 = request.centerInside ? Math.max(floor, floor2) : Math.min(floor, floor2);
        }
        options.inSampleSize = i5;
        options.inJustDecodeBounds = false;
    }
}
