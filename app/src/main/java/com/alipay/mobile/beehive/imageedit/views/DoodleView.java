package com.alipay.mobile.beehive.imageedit.views;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.media.ExifInterface;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import com.alipay.mobile.beehive.R;
import com.alipay.mobile.beehive.imageedit.modle.ImageInfo;
import com.alipay.mobile.beehive.imageedit.utils.CommonUtil;
import com.alipay.mobile.beehive.imageedit.utils.ImageEditLogger;
import com.alipay.mobile.framework.app.ui.BaseActivity;
import com.alipay.mobile.framework.service.common.TaskScheduleService.ScheduleType;
import com.alipay.mobile.tinyappcommon.template.TemplateTinyApp;
import java.io.File;
import java.util.LinkedList;
import java.util.List;

public class DoodleView extends ImageView {
    private static final float MAX_SCALE_FACTOR = 3.2f;
    private static final String TAG = "MosaicImageView";
    private final int MOSAIC_GRID_SIZE_PX;
    private boolean isValid;
    private DoodleEffect mCurrentMode;
    private PathWithPaint mCurrentPath;
    /* access modifiers changed from: private */
    public Bitmap mEffectBitmap;
    private Canvas mEffectCanvas;
    private List<PathWithPaint> mEffectPaths;
    private File mImageFile;
    private Bitmap mMosaicBitmap;
    private Bitmap mOriginalBitmap;
    public ImageInfo mOriginalInfo;
    private OnPathUpdateListener mPathListener;
    private float mScaleFactor;
    private int mScreenHeight;
    private int mScreenWidth;
    private Bitmap mTouchBitmap;
    private Canvas mTouchCanvas;
    private Rect mVisualRect;
    private List<Path> movePaths;
    private float tx;
    private float ty;

    public interface OnPathUpdateListener {
        void onCurrentPaths(List<PathWithPaint> list);
    }

    public static class PathWithPaint {
        public Paint paint;
        public Path path;

        public PathWithPaint(Path path2, Paint paint2) {
            this.path = path2;
            this.paint = paint2;
        }
    }

    public interface onImageSaveResultListener {
        void onImageSaveResult(boolean z, ImageInfo imageInfo);
    }

    public List<PathWithPaint> getCurrentPaths() {
        return this.mEffectPaths;
    }

    public void setOnPathUpdateListener(OnPathUpdateListener listener) {
        this.mPathListener = listener;
    }

    public DoodleView(Context context) {
        this(context, null, 0);
    }

    public DoodleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DoodleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.MOSAIC_GRID_SIZE_PX = (int) getResources().getDimension(R.dimen.mosaic_grid_size);
        this.mCurrentMode = DoodleEffect.COLOR_RED;
        this.mVisualRect = new Rect(0, 0, 0, 0);
        this.movePaths = new LinkedList();
        this.mOriginalInfo = new ImageInfo();
        setScaleType(ScaleType.MATRIX);
        setLayerType(2, null);
        init();
    }

    private void init() {
        setContentDescription(getContext().getString(R.string.tb_doodle_image));
        this.mEffectPaths = new LinkedList();
        Point p = new Point();
        ((WindowManager) getContext().getSystemService(TemplateTinyApp.WINDOW_KEY)).getDefaultDisplay().getSize(p);
        this.mScreenWidth = p.x;
        this.mScreenHeight = p.y;
        DoodleEffect.init(getContext());
    }

    public void setImageSrc(String path) {
        reset();
        File file = new File(path);
        if (!file.exists() || !file.isFile() || !file.canRead()) {
            ImageEditLogger.debug(TAG, "ImageFile is invalid.");
            return;
        }
        this.mImageFile = file;
        requestLayout();
    }

    private void reset() {
        setImageBitmap(null);
        this.isValid = false;
        this.mImageFile = null;
    }

    public void setMode(DoodleEffect mode) {
        this.mCurrentMode = mode;
    }

    public void undoStep() {
        this.mEffectCanvas.drawPaint(DoodleEffect.ERASER.paint);
        int size = this.mEffectPaths.size();
        if (size > 0) {
            this.mEffectPaths.remove(size - 1);
            notifyPathUpdated();
        }
        drawPathWithPaint(true);
    }

    public void resetImage() {
        this.mEffectCanvas.drawPaint(DoodleEffect.ERASER.paint);
        this.mEffectPaths.clear();
        notifyPathUpdated();
        invalidate();
    }

    private void notifyPathUpdated() {
        if (this.mPathListener != null) {
            this.mPathListener.onCurrentPaths(this.mEffectPaths);
        }
    }

    public void saveImageToFile(File imgFile, CompressFormat format, int quality, onImageSaveResultListener listener) {
        if (imgFile == null) {
            throw new IllegalArgumentException("File to save image,should not be null!");
        }
        int oW = this.mOriginalBitmap.getWidth();
        int oH = this.mOriginalBitmap.getHeight();
        this.mEffectCanvas.save();
        this.mEffectCanvas.setMatrix(new Matrix());
        this.mEffectCanvas.drawBitmap(this.mOriginalBitmap, 0.0f, 0.0f, null);
        this.mEffectCanvas.restore();
        drawPathWithPaint(false);
        ImageInfo info = new ImageInfo();
        info.path = CommonUtil.absPath2Url(imgFile.getAbsolutePath());
        info.width = oW;
        info.height = oH;
        final ImageInfo finalInfo = info;
        ImageEditLogger.debug(TAG, "Start save to file in async thread.");
        final File file = imgFile;
        final CompressFormat compressFormat = format;
        final int i = quality;
        final onImageSaveResultListener onimagesaveresultlistener = listener;
        CommonUtil.getExecutor(ScheduleType.URGENT).execute(new Runnable() {
            /* JADX WARNING: Removed duplicated region for block: B:17:0x0082 A[SYNTHETIC, Splitter:B:17:0x0082] */
            /* JADX WARNING: Removed duplicated region for block: B:23:0x0092 A[SYNTHETIC, Splitter:B:23:0x0092] */
            /* JADX WARNING: Removed duplicated region for block: B:33:? A[RETURN, SYNTHETIC] */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public final void run() {
                /*
                    r10 = this;
                    r1 = 0
                    java.io.File r3 = r2     // Catch:{ IOException -> 0x005e }
                    boolean r3 = r3.exists()     // Catch:{ IOException -> 0x005e }
                    if (r3 != 0) goto L_0x000e
                    java.io.File r3 = r2     // Catch:{ IOException -> 0x005e }
                    r3.createNewFile()     // Catch:{ IOException -> 0x005e }
                L_0x000e:
                    java.io.FileOutputStream r2 = new java.io.FileOutputStream     // Catch:{ IOException -> 0x005e }
                    java.io.File r3 = r2     // Catch:{ IOException -> 0x005e }
                    r2.<init>(r3)     // Catch:{ IOException -> 0x005e }
                    long r4 = java.lang.System.currentTimeMillis()     // Catch:{ IOException -> 0x00a2, all -> 0x009f }
                    com.alipay.mobile.beehive.imageedit.views.DoodleView r3 = com.alipay.mobile.beehive.imageedit.views.DoodleView.this     // Catch:{ IOException -> 0x00a2, all -> 0x009f }
                    android.graphics.Bitmap r3 = r3.mEffectBitmap     // Catch:{ IOException -> 0x00a2, all -> 0x009f }
                    android.graphics.Bitmap$CompressFormat r6 = r3     // Catch:{ IOException -> 0x00a2, all -> 0x009f }
                    int r7 = r4     // Catch:{ IOException -> 0x00a2, all -> 0x009f }
                    r3.compress(r6, r7, r2)     // Catch:{ IOException -> 0x00a2, all -> 0x009f }
                    java.lang.String r3 = "MosaicImageView"
                    java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x00a2, all -> 0x009f }
                    java.lang.String r7 = "Save to file cost time = "
                    r6.<init>(r7)     // Catch:{ IOException -> 0x00a2, all -> 0x009f }
                    long r8 = java.lang.System.currentTimeMillis()     // Catch:{ IOException -> 0x00a2, all -> 0x009f }
                    long r8 = r8 - r4
                    java.lang.StringBuilder r6 = r6.append(r8)     // Catch:{ IOException -> 0x00a2, all -> 0x009f }
                    java.lang.String r6 = r6.toString()     // Catch:{ IOException -> 0x00a2, all -> 0x009f }
                    com.alipay.mobile.beehive.imageedit.utils.ImageEditLogger.debug(r3, r6)     // Catch:{ IOException -> 0x00a2, all -> 0x009f }
                    com.alipay.mobile.beehive.imageedit.views.DoodleView r3 = com.alipay.mobile.beehive.imageedit.views.DoodleView.this     // Catch:{ IOException -> 0x00a2, all -> 0x009f }
                    java.io.File r6 = r2     // Catch:{ IOException -> 0x00a2, all -> 0x009f }
                    r3.notifyScanner(r6)     // Catch:{ IOException -> 0x00a2, all -> 0x009f }
                    r2.close()     // Catch:{ IOException -> 0x0055 }
                L_0x0049:
                    com.alipay.mobile.beehive.imageedit.views.DoodleView r3 = com.alipay.mobile.beehive.imageedit.views.DoodleView.this
                    r6 = 1
                    com.alipay.mobile.beehive.imageedit.views.DoodleView$onImageSaveResultListener r7 = r5
                    com.alipay.mobile.beehive.imageedit.modle.ImageInfo r8 = r6
                    r3.postNotifyResult(r6, r7, r8)
                    r1 = r2
                L_0x0054:
                    return
                L_0x0055:
                    r3 = move-exception
                    java.lang.String r3 = "MosaicImageView"
                    java.lang.String r6 = "Ignore exception"
                    com.alipay.mobile.beehive.imageedit.utils.ImageEditLogger.debug(r3, r6)
                    goto L_0x0049
                L_0x005e:
                    r0 = move-exception
                L_0x005f:
                    com.alipay.mobile.beehive.imageedit.views.DoodleView r3 = com.alipay.mobile.beehive.imageedit.views.DoodleView.this     // Catch:{ all -> 0x008f }
                    r6 = 0
                    com.alipay.mobile.beehive.imageedit.views.DoodleView$onImageSaveResultListener r7 = r5     // Catch:{ all -> 0x008f }
                    r8 = 0
                    r3.postNotifyResult(r6, r7, r8)     // Catch:{ all -> 0x008f }
                    java.lang.String r3 = "MosaicImageView"
                    java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ all -> 0x008f }
                    java.lang.String r7 = "saveImageToFile exception,"
                    r6.<init>(r7)     // Catch:{ all -> 0x008f }
                    java.lang.String r7 = r0.getMessage()     // Catch:{ all -> 0x008f }
                    java.lang.StringBuilder r6 = r6.append(r7)     // Catch:{ all -> 0x008f }
                    java.lang.String r6 = r6.toString()     // Catch:{ all -> 0x008f }
                    com.alipay.mobile.beehive.imageedit.utils.ImageEditLogger.warn(r3, r6)     // Catch:{ all -> 0x008f }
                    if (r1 == 0) goto L_0x0054
                    r1.close()     // Catch:{ IOException -> 0x0086 }
                    goto L_0x0054
                L_0x0086:
                    r3 = move-exception
                    java.lang.String r3 = "MosaicImageView"
                    java.lang.String r6 = "Ignore exception"
                    com.alipay.mobile.beehive.imageedit.utils.ImageEditLogger.debug(r3, r6)
                    goto L_0x0054
                L_0x008f:
                    r3 = move-exception
                L_0x0090:
                    if (r1 == 0) goto L_0x0095
                    r1.close()     // Catch:{ IOException -> 0x0096 }
                L_0x0095:
                    throw r3
                L_0x0096:
                    r6 = move-exception
                    java.lang.String r6 = "MosaicImageView"
                    java.lang.String r7 = "Ignore exception"
                    com.alipay.mobile.beehive.imageedit.utils.ImageEditLogger.debug(r6, r7)
                    goto L_0x0095
                L_0x009f:
                    r3 = move-exception
                    r1 = r2
                    goto L_0x0090
                L_0x00a2:
                    r0 = move-exception
                    r1 = r2
                    goto L_0x005f
                */
                throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.beehive.imageedit.views.DoodleView.AnonymousClass1.run():void");
            }
        });
    }

    /* access modifiers changed from: private */
    public void notifyScanner(File imgFile) {
        ImageEditLogger.debug(TAG, "Notify media scanner at path " + imgFile.getAbsolutePath());
        Intent scanIntent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
        scanIntent.setData(Uri.fromFile(imgFile));
        getContext().sendBroadcast(scanIntent);
    }

    /* access modifiers changed from: private */
    public void postNotifyResult(final boolean success, final onImageSaveResultListener listener, final ImageInfo finalInfo) {
        post(new Runnable() {
            public final void run() {
                ImageEditLogger.debug(DoodleView.TAG, "Notify onImageSaveResult.");
                listener.onImageSaveResult(success, finalInfo);
            }
        });
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.mEffectBitmap != null) {
            canvas.save();
            canvas.translate(this.tx, this.ty);
            canvas.scale(this.mScaleFactor, this.mScaleFactor);
            canvas.drawBitmap(this.mEffectBitmap, 0.0f, 0.0f, null);
            canvas.restore();
        }
    }

    private void asyncSetUpImage(final File file) {
        handleDialog(true);
        CommonUtil.getExecutor(ScheduleType.URGENT).execute(new Runnable() {
            public final void run() {
                DoodleView.this.decodeBitmap(file);
                DoodleView.this.post(new Runnable() {
                    public final void run() {
                        DoodleView.this.prepareData();
                        DoodleView.this.handleDialog(false);
                    }
                });
            }
        });
    }

    /* access modifiers changed from: protected */
    public boolean setFrame(int l, int t, int r, int b) {
        boolean changed = super.setFrame(l, t, r, b);
        if (changed && this.mImageFile != null) {
            ImageEditLogger.debug(TAG, "setFrame -> asyncSetUpImage");
            asyncSetUpImage(this.mImageFile);
        }
        return changed;
    }

    /* access modifiers changed from: private */
    public void handleDialog(boolean isShow) {
        Context activity = getContext();
        if (!(activity instanceof BaseActivity)) {
            return;
        }
        if (isShow) {
            ((BaseActivity) activity).showProgressDialog(activity.getString(R.string.preparing_image));
        } else {
            ((BaseActivity) activity).dismissProgressDialog();
        }
    }

    /* access modifiers changed from: private */
    public void prepareData() {
        if (this.mOriginalBitmap == null) {
            ImageEditLogger.warn((String) TAG, (String) "mOriginalBitmap is Null!");
            this.isValid = false;
            return;
        }
        ImageEditLogger.debug(TAG, "prepareData");
        int w = getWidth();
        int h = getHeight();
        setImageBitmap(this.mOriginalBitmap);
        this.mVisualRect = calculateVisualZone(this.mOriginalBitmap, w, h);
        Matrix matrix = new Matrix();
        matrix.postScale(this.mScaleFactor, this.mScaleFactor, 0.5f, 0.5f);
        this.tx = ((float) (w - this.mVisualRect.width())) / 2.0f;
        this.ty = ((float) (h - this.mVisualRect.height())) / 2.0f;
        matrix.postTranslate(this.tx, this.ty);
        setImageMatrix(matrix);
        if (DoodleEffect.MOSAIC.isShow) {
            ImageEditLogger.debug(TAG, "Setup mosaic bitmap and touch bitmap.");
            this.mMosaicBitmap = genMosaicBmp(this.mOriginalBitmap, this.mVisualRect);
            this.mTouchBitmap = Bitmap.createBitmap(this.mVisualRect.width(), this.mVisualRect.height(), Config.ARGB_8888);
            this.mTouchCanvas = new Canvas(this.mTouchBitmap);
        }
        this.mEffectBitmap = Bitmap.createBitmap(this.mOriginalBitmap.getWidth(), this.mOriginalBitmap.getHeight(), Config.ARGB_8888);
        this.mEffectCanvas = new Canvas(this.mEffectBitmap);
        Matrix m2 = new Matrix();
        matrix.invert(m2);
        this.mEffectCanvas.setMatrix(m2);
        this.isValid = true;
    }

    /* access modifiers changed from: private */
    public void decodeBitmap(File imgFile) {
        Options opts = new Options();
        opts.inJustDecodeBounds = true;
        opts.inSampleSize = 1;
        BitmapFactory.decodeFile(imgFile.getAbsolutePath(), opts);
        int oriWidth = opts.outWidth;
        int oriHeight = opts.outHeight;
        this.mOriginalInfo.width = oriWidth;
        this.mOriginalInfo.height = oriHeight;
        this.mOriginalInfo.path = imgFile.getAbsolutePath();
        opts.inJustDecodeBounds = false;
        opts.inSampleSize = (int) Math.max(((float) oriWidth) / ((float) this.mScreenWidth), ((float) oriHeight) / ((float) this.mScreenHeight));
        this.mOriginalBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath(), opts);
        this.mOriginalBitmap = adaptRotation(imgFile.getAbsolutePath(), this.mOriginalBitmap);
    }

    private Bitmap adaptRotation(String path, Bitmap src) {
        Bitmap ret = src;
        try {
            ExifInterface exif = new ExifInterface(path);
            Matrix matrix = new Matrix();
            int r = exif.getAttributeInt("Orientation", 1);
            this.mOriginalInfo.rotation = r;
            switch (r) {
                case 3:
                    matrix.postRotate(180.0f);
                    break;
                case 6:
                    matrix.postRotate(90.0f);
                    break;
                case 8:
                    matrix.postRotate(270.0f);
                    break;
            }
            return Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(), matrix, true);
        } catch (Throwable e) {
            ImageEditLogger.warn((String) TAG, "adaptRotation exception," + e.getMessage());
            return ret;
        }
    }

    private Rect calculateVisualZone(Bitmap bmp, int viewWidth, int viewHeight) {
        float scaleFactor;
        int w = bmp.getWidth();
        int h = bmp.getHeight();
        float bRatio = ((float) w) / ((float) h);
        float sRatio = ((float) viewWidth) / ((float) viewHeight);
        if (w > viewWidth || h > viewHeight) {
            if (bRatio <= sRatio) {
                scaleFactor = ((float) viewHeight) / ((float) h);
            } else {
                scaleFactor = ((float) viewWidth) / ((float) w);
            }
        } else if (bRatio <= sRatio) {
            scaleFactor = ((float) viewHeight) / ((float) h);
        } else {
            scaleFactor = ((float) viewWidth) / ((float) w);
        }
        if (scaleFactor > MAX_SCALE_FACTOR) {
            scaleFactor = MAX_SCALE_FACTOR;
        }
        this.mScaleFactor = scaleFactor;
        return new Rect(0, 0, (int) (((float) w) * scaleFactor), (int) (((float) h) * scaleFactor));
    }

    private void drawMoveAction(PathWithPaint pp) {
        if (pp.paint == DoodleEffect.MOSAIC.paint) {
            drawMosaic(this.mCurrentPath.path);
            return;
        }
        drawIntoEffectCanvas(pp);
        invalidate();
    }

    private void drawIntoEffectCanvas(PathWithPaint pp) {
        this.mEffectCanvas.drawPath(pp.path, pp.paint);
    }

    private void drawMosaic(List<Path> paths) {
        if (!DoodleEffect.MOSAIC.isShow) {
            ImageEditLogger.debug(TAG, "Mosaic is disabled,should not be here.");
        } else if (paths != null && !paths.isEmpty()) {
            DoodleEffect.MOSAIC.paint.setXfermode(null);
            this.mTouchCanvas.drawPaint(DoodleEffect.ERASER.paint);
            this.mTouchCanvas.save();
            this.mTouchCanvas.translate(-this.tx, -this.ty);
            for (Path p : paths) {
                this.mTouchCanvas.drawPath(p, DoodleEffect.MOSAIC.paint);
            }
            this.mTouchCanvas.restore();
            DoodleEffect.MOSAIC.paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
            this.mTouchCanvas.drawBitmap(this.mMosaicBitmap, null, this.mVisualRect, DoodleEffect.MOSAIC.paint);
            this.mEffectCanvas.drawBitmap(this.mTouchBitmap, null, this.mVisualRect, null);
            invalidate();
        }
    }

    private void drawMosaic(Path path) {
        this.movePaths.clear();
        this.movePaths.add(path);
        drawMosaic(this.movePaths);
    }

    private void drawPathWithPaint(boolean needClear) {
        if (needClear) {
            this.mEffectCanvas.drawPaint(DoodleEffect.ERASER.paint);
        }
        List mosaicPaths = new LinkedList();
        for (PathWithPaint pp : this.mEffectPaths) {
            if (pp.paint == DoodleEffect.MOSAIC.paint) {
                mosaicPaths.add(pp.path);
            } else {
                drawMosaic(mosaicPaths);
                mosaicPaths.clear();
                drawIntoEffectCanvas(pp);
            }
        }
        drawMosaic(mosaicPaths);
        mosaicPaths.clear();
        invalidate();
    }

    private Bitmap genMosaicBmp(Bitmap bmp, Rect visualRect) {
        long start = System.currentTimeMillis();
        int w = visualRect.width();
        int h = visualRect.height();
        Bitmap temp = Bitmap.createBitmap(w, h, Config.RGB_565);
        new Canvas(temp).drawBitmap(bmp, null, new Rect(0, 0, w, h), null);
        int[] pixels = new int[(w * h)];
        int horCount = (int) Math.floor((double) (((float) w) / ((float) this.MOSAIC_GRID_SIZE_PX)));
        int verCount = (int) Math.floor((double) (((float) h) / ((float) this.MOSAIC_GRID_SIZE_PX)));
        for (int i = 0; i < horCount; i++) {
            for (int j = 0; j < verCount; j++) {
                int left = this.MOSAIC_GRID_SIZE_PX * i;
                int top = this.MOSAIC_GRID_SIZE_PX * j;
                int cx = left + (this.MOSAIC_GRID_SIZE_PX / 2);
                if (cx > w) {
                    cx = w;
                }
                int cy = top + (this.MOSAIC_GRID_SIZE_PX / 2);
                if (cy > h) {
                    cy = h;
                }
                int gridCenterColor = temp.getPixel(cx, cy);
                int gridBoundY = Math.min(this.MOSAIC_GRID_SIZE_PX + top, h);
                int gridBoundX = Math.min(this.MOSAIC_GRID_SIZE_PX + left, w);
                for (int y = top; y < gridBoundY; y++) {
                    int shift = y * w;
                    for (int x = left; x < gridBoundX; x++) {
                        pixels[x + shift] = gridCenterColor;
                    }
                }
            }
        }
        ImageEditLogger.d(TAG, "cost = " + (System.currentTimeMillis() - start));
        temp.recycle();
        return Bitmap.createBitmap(pixels, w, h, Config.RGB_565);
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        DoodleEffect.release();
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (!this.isValid) {
            return false;
        }
        float x = event.getX();
        float y = event.getY();
        switch (event.getActionMasked()) {
            case 0:
                this.mCurrentPath = new PathWithPaint(new Path(), this.mCurrentMode.paint);
                this.mEffectPaths.add(this.mCurrentPath);
                notifyPathUpdated();
                this.mCurrentPath.path.moveTo(x, y);
                return true;
            case 2:
                this.mCurrentPath.path.lineTo(x, y);
                drawMoveAction(this.mCurrentPath);
                break;
        }
        return super.onTouchEvent(event);
    }
}
