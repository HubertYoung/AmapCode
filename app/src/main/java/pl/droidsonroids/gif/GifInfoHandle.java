package pl.droidsonroids.gif;

import android.content.ContentResolver;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.FloatRange;
import android.support.annotation.IntRange;
import android.view.Surface;
import com.alibaba.analytics.core.sync.UploadQueueMgr;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public final class GifInfoHandle {
    volatile long a;

    static native void bindSurface(long j, Surface surface, long[] jArr);

    private static native void free(long j);

    private static native long getAllocationByteCount(long j);

    private static native String getComment(long j);

    private static native int getCurrentFrameIndex(long j);

    private static native int getCurrentLoop(long j);

    private static native int getCurrentPosition(long j);

    private static native int getDuration(long j);

    private static native int getFrameDuration(long j, int i);

    private static native int getHeight(long j);

    private static native int getLoopCount(long j);

    private static native long getMetadataByteCount(long j);

    private static native int getNativeErrorCode(long j);

    private static native int getNumberOfFrames(long j);

    private static native long[] getSavedState(long j);

    private static native long getSourceLength(long j);

    private static native int getWidth(long j);

    private static native boolean isAnimationCompleted(long j);

    private static native boolean isOpaque(long j);

    static native long openByteArray(byte[] bArr) throws GifIOException;

    static native long openDirectByteBuffer(ByteBuffer byteBuffer) throws GifIOException;

    static native long openFd(FileDescriptor fileDescriptor, long j) throws GifIOException;

    static native long openFile(String str) throws GifIOException;

    static native long openStream(InputStream inputStream) throws GifIOException;

    private static native void postUnbindSurface(long j);

    private static native long renderFrame(long j, Bitmap bitmap);

    private static native boolean reset(long j);

    private static native long restoreRemainder(long j);

    private static native int restoreSavedState(long j, long[] jArr, Bitmap bitmap);

    private static native void saveRemainder(long j);

    private static native void seekToFrame(long j, int i, Bitmap bitmap);

    private static native void seekToTime(long j, int i, Bitmap bitmap);

    static native void setLoopCount(long j, char c);

    static native void setOptions(long j, char c, boolean z);

    private static native void setSpeedFactor(long j, float f);

    GifInfoHandle() {
    }

    GifInfoHandle(FileDescriptor fileDescriptor) throws GifIOException {
        this.a = openFd(fileDescriptor, 0);
    }

    GifInfoHandle(byte[] bArr) throws GifIOException {
        this.a = openByteArray(bArr);
    }

    GifInfoHandle(ByteBuffer byteBuffer) throws GifIOException {
        this.a = openDirectByteBuffer(byteBuffer);
    }

    GifInfoHandle(String str) throws GifIOException {
        this.a = openFile(str);
    }

    GifInfoHandle(InputStream inputStream) throws GifIOException {
        if (!inputStream.markSupported()) {
            throw new IllegalArgumentException("InputStream does not support marking");
        }
        this.a = openStream(inputStream);
    }

    public GifInfoHandle(AssetFileDescriptor assetFileDescriptor) throws IOException {
        try {
            this.a = openFd(assetFileDescriptor.getFileDescriptor(), assetFileDescriptor.getStartOffset());
        } finally {
            try {
                assetFileDescriptor.close();
            } catch (IOException unused) {
            }
        }
    }

    static GifInfoHandle a(ContentResolver contentResolver, Uri uri) throws IOException {
        if ("file".equals(uri.getScheme())) {
            return new GifInfoHandle(uri.getPath());
        }
        return new GifInfoHandle(contentResolver.openAssetFileDescriptor(uri, UploadQueueMgr.MSGTYPE_REALTIME));
    }

    public final synchronized long a(Bitmap bitmap) {
        return renderFrame(this.a, bitmap);
    }

    /* access modifiers changed from: 0000 */
    public final synchronized void a() {
        free(this.a);
        this.a = 0;
    }

    /* access modifiers changed from: 0000 */
    public final synchronized long b() {
        try {
        }
        return restoreRemainder(this.a);
    }

    /* access modifiers changed from: 0000 */
    public final synchronized boolean c() {
        try {
        }
        return reset(this.a);
    }

    /* access modifiers changed from: 0000 */
    public final synchronized void d() {
        saveRemainder(this.a);
    }

    /* access modifiers changed from: 0000 */
    public final synchronized String e() {
        try {
        }
        return getComment(this.a);
    }

    /* access modifiers changed from: 0000 */
    public final synchronized int f() {
        try {
        }
        return getLoopCount(this.a);
    }

    /* access modifiers changed from: 0000 */
    public final synchronized long g() {
        try {
        }
        return getSourceLength(this.a);
    }

    /* access modifiers changed from: 0000 */
    public final synchronized int h() {
        try {
        }
        return getNativeErrorCode(this.a);
    }

    /* access modifiers changed from: 0000 */
    public final void a(@FloatRange(from = 0.0d, fromInclusive = false) float f) {
        if (f <= 0.0f || Float.isNaN(f)) {
            throw new IllegalArgumentException("Speed factor is not positive");
        }
        if (f < 4.656613E-10f) {
            f = 4.656613E-10f;
        }
        synchronized (this) {
            setSpeedFactor(this.a, f);
        }
    }

    /* access modifiers changed from: 0000 */
    public final synchronized int i() {
        try {
        }
        return getDuration(this.a);
    }

    /* access modifiers changed from: 0000 */
    public final synchronized int j() {
        try {
        }
        return getCurrentPosition(this.a);
    }

    /* access modifiers changed from: 0000 */
    public final synchronized int k() {
        try {
        }
        return getCurrentFrameIndex(this.a);
    }

    /* access modifiers changed from: 0000 */
    public final synchronized int l() {
        try {
        }
        return getCurrentLoop(this.a);
    }

    /* access modifiers changed from: 0000 */
    public final synchronized void a(@IntRange(from = 0, to = 2147483647L) int i, Bitmap bitmap) {
        seekToTime(this.a, i, bitmap);
    }

    /* access modifiers changed from: 0000 */
    public final synchronized void b(@IntRange(from = 0, to = 2147483647L) int i, Bitmap bitmap) {
        seekToFrame(this.a, i, bitmap);
    }

    /* access modifiers changed from: 0000 */
    public final synchronized long m() {
        try {
        }
        return getAllocationByteCount(this.a);
    }

    /* access modifiers changed from: 0000 */
    public final synchronized long n() {
        try {
        }
        return getMetadataByteCount(this.a);
    }

    /* access modifiers changed from: 0000 */
    public final synchronized boolean o() {
        return this.a == 0;
    }

    /* access modifiers changed from: protected */
    public final void finalize() throws Throwable {
        try {
            a();
        } finally {
            super.finalize();
        }
    }

    /* access modifiers changed from: 0000 */
    public final synchronized void p() {
        postUnbindSurface(this.a);
    }

    /* access modifiers changed from: 0000 */
    public final synchronized boolean q() {
        try {
        }
        return isAnimationCompleted(this.a);
    }

    /* access modifiers changed from: 0000 */
    public final synchronized long[] r() {
        return getSavedState(this.a);
    }

    /* access modifiers changed from: 0000 */
    public final synchronized int a(long[] jArr, Bitmap bitmap) {
        return restoreSavedState(this.a, jArr, bitmap);
    }

    /* access modifiers changed from: 0000 */
    public final int a(@IntRange(from = 0) int i) {
        int frameDuration;
        synchronized (this) {
            if (i >= 0) {
                try {
                    if (i < getNumberOfFrames(this.a)) {
                        frameDuration = getFrameDuration(this.a, i);
                    }
                } finally {
                }
            }
            throw new IndexOutOfBoundsException("Frame index is out of bounds");
        }
        return frameDuration;
    }

    /* access modifiers changed from: 0000 */
    public final synchronized int s() {
        try {
        }
        return getWidth(this.a);
    }

    /* access modifiers changed from: 0000 */
    public final synchronized int t() {
        try {
        }
        return getHeight(this.a);
    }

    public final synchronized int u() {
        try {
        }
        return getNumberOfFrames(this.a);
    }

    /* access modifiers changed from: 0000 */
    public final synchronized boolean v() {
        try {
        }
        return isOpaque(this.a);
    }

    static {
        try {
            System.loadLibrary("gif");
        } catch (UnsatisfiedLinkError unused) {
            fhq.a(fho.a());
        }
    }
}
