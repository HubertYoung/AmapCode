package com.alipay.multimedia.gles;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.opengl.EGL14;
import android.opengl.EGLSurface;
import android.opengl.GLES20;
import android.os.Build.VERSION;
import com.alipay.alipaylogger.Log;
import com.alipay.mobile.common.transport.monitor.RPCDataParser;
import com.alipay.multimedia.io.IOUtils;
import com.alipay.rdssecuritysdk.constant.DictionaryKeys;
import com.googlecode.androidannotations.api.BackgroundExecutor;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

@TargetApi(17)
public class EglSurfaceBase {
    protected EglCore a;
    private EGLSurface b = EGL14.EGL_NO_SURFACE;
    private int c = -1;
    private int d = -1;

    protected EglSurfaceBase(EglCore eglCore) {
        this.a = eglCore;
    }

    public void createWindowSurface(Object surface) {
        if (this.b != EGL14.EGL_NO_SURFACE) {
            throw new IllegalStateException("surface already created");
        }
        this.b = this.a.createWindowSurface(surface);
    }

    public void createOffscreenSurface(int width, int height) {
        if (this.b != EGL14.EGL_NO_SURFACE) {
            throw new IllegalStateException("surface already created");
        }
        this.b = this.a.createOffscreenSurface(width, height);
        this.c = width;
        this.d = height;
    }

    public int getWidth() {
        if (this.c < 0) {
            return this.a.querySurface(this.b, 12375);
        }
        return this.c;
    }

    public int getHeight() {
        if (this.d < 0) {
            return this.a.querySurface(this.b, 12374);
        }
        return this.d;
    }

    public void releaseEglSurface() {
        this.a.releaseSurface(this.b);
        this.b = EGL14.EGL_NO_SURFACE;
        this.d = -1;
        this.c = -1;
    }

    public void makeCurrent() {
        this.a.makeCurrent(this.b);
    }

    public void makeCurrentReadFrom(EglSurfaceBase readSurface) {
        this.a.makeCurrent(this.b, readSurface.b);
    }

    public boolean swapBuffers() {
        boolean result = this.a.swapBuffers(this.b);
        if (!result) {
            Log.d(GlUtil.TAG, "WARNING: swapBuffers() failed");
        }
        return result;
    }

    public void setPresentationTime(long nsecs) {
        this.a.setPresentationTime(this.b, nsecs);
    }

    public void extractVideoFrame(final File videoFile, final File thumbFile, final int orientation) {
        BackgroundExecutor.execute((Runnable) new Runnable() {
            public void run() {
                long start = System.currentTimeMillis();
                Bitmap bmp = EglSurfaceBase.createVideoThumbnail(videoFile.getAbsolutePath());
                BufferedOutputStream bos = null;
                try {
                    BufferedOutputStream bos2 = new BufferedOutputStream(new FileOutputStream(thumbFile));
                    try {
                        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bmp, 240, 424, false);
                        if (orientation != 0) {
                            Matrix matrix = new Matrix();
                            matrix.postRotate((float) orientation);
                            scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0, scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix, false);
                        }
                        scaledBitmap.compress(CompressFormat.JPEG, 70, bos2);
                        scaledBitmap.recycle();
                        bmp.recycle();
                        IOUtils.closeQuietly(bos2);
                        BufferedOutputStream bufferedOutputStream = bos2;
                    } catch (FileNotFoundException e) {
                        exception = e;
                        bos = bos2;
                        try {
                            Log.e(GlUtil.TAG, "bg saveFrame: ", exception);
                            IOUtils.closeQuietly(bos);
                            Log.d(GlUtil.TAG, "extractVideoFrame 240x424 frame as '" + thumbFile + "', cost: " + (System.currentTimeMillis() - start));
                        } catch (Throwable th) {
                            th = th;
                            IOUtils.closeQuietly(bos);
                            throw th;
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        bos = bos2;
                        IOUtils.closeQuietly(bos);
                        throw th;
                    }
                } catch (FileNotFoundException e2) {
                    exception = e2;
                    Log.e(GlUtil.TAG, "bg saveFrame: ", exception);
                    IOUtils.closeQuietly(bos);
                    Log.d(GlUtil.TAG, "extractVideoFrame 240x424 frame as '" + thumbFile + "', cost: " + (System.currentTimeMillis() - start));
                }
                Log.d(GlUtil.TAG, "extractVideoFrame 240x424 frame as '" + thumbFile + "', cost: " + (System.currentTimeMillis() - start));
            }
        });
    }

    public static Bitmap createVideoThumbnail(String filePath) {
        Class clazz = null;
        Object instance = null;
        try {
            clazz = Class.forName("android.media.MediaMetadataRetriever");
            instance = clazz.newInstance();
            clazz.getMethod("setDataSource", new Class[]{String.class}).invoke(instance, new Object[]{filePath});
            if (VERSION.SDK_INT <= 9) {
                Bitmap bitmap = (Bitmap) clazz.getMethod("captureFrame", new Class[0]).invoke(instance, new Object[0]);
                if (instance == null) {
                    return bitmap;
                }
                try {
                    clazz.getMethod("release", new Class[0]).invoke(instance, new Object[0]);
                    return bitmap;
                } catch (Exception ignored) {
                    Log.e(GlUtil.TAG, "createVideoThumbnail error, path: " + filePath, ignored);
                    return bitmap;
                }
            } else {
                byte[] data = (byte[]) clazz.getMethod("getEmbeddedPicture", new Class[0]).invoke(instance, new Object[0]);
                if (data != null) {
                    Bitmap bitmap2 = BitmapFactory.decodeByteArray(data, 0, data.length);
                    if (bitmap2 != null) {
                        if (instance != null) {
                            try {
                                clazz.getMethod("release", new Class[0]).invoke(instance, new Object[0]);
                            } catch (Exception ignored2) {
                                Log.e(GlUtil.TAG, "createVideoThumbnail error, path: " + filePath, ignored2);
                            }
                        }
                        return bitmap2;
                    }
                }
                Bitmap bitmap3 = (Bitmap) clazz.getMethod("getFrameAtTime", new Class[0]).invoke(instance, new Object[0]);
                if (instance == null) {
                    return bitmap3;
                }
                try {
                    clazz.getMethod("release", new Class[0]).invoke(instance, new Object[0]);
                    return bitmap3;
                } catch (Exception ignored3) {
                    Log.e(GlUtil.TAG, "createVideoThumbnail error, path: " + filePath, ignored3);
                    return bitmap3;
                }
            }
        } catch (Exception ex) {
            Log.e(GlUtil.TAG, "createVideoThumbnail error, path: " + filePath, ex);
            if (instance != null) {
                try {
                    clazz.getMethod("release", new Class[0]).invoke(instance, new Object[0]);
                } catch (Exception ignored4) {
                    Log.e(GlUtil.TAG, "createVideoThumbnail error, path: " + filePath, ignored4);
                }
            }
            return null;
        } catch (Throwable th) {
            if (instance != null) {
                try {
                    clazz.getMethod("release", new Class[0]).invoke(instance, new Object[0]);
                } catch (Exception ignored5) {
                    Log.e(GlUtil.TAG, "createVideoThumbnail error, path: " + filePath, ignored5);
                }
            }
            throw th;
        }
    }

    public void saveFrame(File file, int orientation) {
        if (!this.a.isCurrent(this.b)) {
            throw new RuntimeException("Expected EGL context/surface is not current");
        }
        final String filename = file.toString();
        int width = getWidth();
        int height = getHeight();
        final ByteBuffer buf = ByteBuffer.allocateDirect(width * height * 4);
        buf.order(ByteOrder.LITTLE_ENDIAN);
        GLES20.glReadPixels(0, 0, width, height, 6408, 5121, buf);
        GlUtil.checkGlError("glReadPixels");
        buf.rewind();
        final int i = width;
        final int i2 = height;
        final int i3 = orientation;
        BackgroundExecutor.execute((Runnable) new Runnable() {
            public void run() {
                EglSurfaceBase.b(buf, i, i2);
                BufferedOutputStream bos = null;
                try {
                    BufferedOutputStream bos2 = new BufferedOutputStream(new FileOutputStream(filename));
                    try {
                        Bitmap bmp = Bitmap.createBitmap(i, i2, Config.ARGB_8888);
                        bmp.copyPixelsFromBuffer(buf);
                        if (i3 != 0) {
                            Matrix matrix = new Matrix();
                            matrix.postRotate((float) i3);
                            bmp = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), matrix, false);
                        }
                        bmp.compress(CompressFormat.JPEG, 80, bos2);
                        bmp.recycle();
                        IOUtils.closeQuietly(bos2);
                        BufferedOutputStream bufferedOutputStream = bos2;
                    } catch (FileNotFoundException e2) {
                        exception = e2;
                        bos = bos2;
                        try {
                            Log.e(GlUtil.TAG, "bg saveFrame: ", exception);
                            IOUtils.closeQuietly(bos);
                            Log.d(GlUtil.TAG, "Saved " + i + DictionaryKeys.CTRLXY_X + i2 + " frame as '" + filename + "'");
                        } catch (Throwable th) {
                            th = th;
                            IOUtils.closeQuietly(bos);
                            throw th;
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        bos = bos2;
                        IOUtils.closeQuietly(bos);
                        throw th;
                    }
                } catch (FileNotFoundException e3) {
                    exception = e3;
                    Log.e(GlUtil.TAG, "bg saveFrame: ", exception);
                    IOUtils.closeQuietly(bos);
                    Log.d(GlUtil.TAG, "Saved " + i + DictionaryKeys.CTRLXY_X + i2 + " frame as '" + filename + "'");
                }
                Log.d(GlUtil.TAG, "Saved " + i + DictionaryKeys.CTRLXY_X + i2 + " frame as '" + filename + "'");
            }
        });
    }

    public Bitmap saveFrameSync(File file, int orientation) {
        if (!this.a.isCurrent(this.b)) {
            throw new RuntimeException("Expected EGL context/surface is not current");
        }
        String filename = file.toString();
        int width = getWidth();
        int height = getHeight();
        ByteBuffer buf = ByteBuffer.allocateDirect(width * height * 4);
        buf.order(ByteOrder.LITTLE_ENDIAN);
        GLES20.glReadPixels(0, 0, width, height, 6408, 5121, buf);
        GlUtil.checkGlError("glReadPixels");
        buf.rewind();
        b(buf, width, height);
        BufferedOutputStream bos = null;
        Bitmap bmp = null;
        try {
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(filename));
            try {
                bmp = Bitmap.createBitmap(width, height, Config.ARGB_8888);
                bmp.copyPixelsFromBuffer(buf);
                if (orientation != 0) {
                    Matrix matrix = new Matrix();
                    matrix.postRotate((float) orientation);
                    bmp = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), matrix, false);
                }
                IOUtils.closeQuietly(bufferedOutputStream);
                BufferedOutputStream bufferedOutputStream2 = bufferedOutputStream;
            } catch (FileNotFoundException e) {
                exception = e;
                bos = bufferedOutputStream;
                try {
                    Log.e(GlUtil.TAG, "bg saveFrame: ", exception);
                    IOUtils.closeQuietly(bos);
                    Log.d(GlUtil.TAG, "Saved " + width + DictionaryKeys.CTRLXY_X + height + " frame as '" + filename + "'");
                    return bmp;
                } catch (Throwable th) {
                    th = th;
                    IOUtils.closeQuietly(bos);
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                bos = bufferedOutputStream;
                IOUtils.closeQuietly(bos);
                throw th;
            }
        } catch (FileNotFoundException e2) {
            exception = e2;
            Log.e(GlUtil.TAG, "bg saveFrame: ", exception);
            IOUtils.closeQuietly(bos);
            Log.d(GlUtil.TAG, "Saved " + width + DictionaryKeys.CTRLXY_X + height + " frame as '" + filename + "'");
            return bmp;
        }
        Log.d(GlUtil.TAG, "Saved " + width + DictionaryKeys.CTRLXY_X + height + " frame as '" + filename + "'");
        return bmp;
    }

    /* access modifiers changed from: private */
    public static void b(ByteBuffer buf, int width, int height) {
        long ts = System.currentTimeMillis();
        int i = 0;
        byte[] tmp = new byte[(width * 4)];
        while (true) {
            int i2 = i + 1;
            if (i < height / 2) {
                buf.get(tmp);
                System.arraycopy(buf.array(), buf.limit() - buf.position(), buf.array(), buf.position() - (width * 4), width * 4);
                System.arraycopy(tmp, 0, buf.array(), buf.limit() - buf.position(), width * 4);
                i = i2;
            } else {
                buf.rewind();
                Log.d(GlUtil.TAG, "reverseBuf took " + (System.currentTimeMillis() - ts) + RPCDataParser.TIME_MS);
                return;
            }
        }
    }
}
