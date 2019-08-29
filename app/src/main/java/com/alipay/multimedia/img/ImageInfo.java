package com.alipay.multimedia.img;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.media.ExifInterface;
import android.text.TextUtils;
import com.alipay.multimedia.img.utils.Exif;
import com.alipay.multimedia.img.utils.ImageFileType;
import com.alipay.multimedia.img.utils.LogUtils;
import com.alipay.multimedia.io.FileUtils;
import com.alipay.streammedia.mmengine.MMNativeEngineApi;
import com.alipay.streammedia.mmengine.MMNativeException;
import com.alipay.streammedia.mmengine.picture.jpg.PictureFileConfig;
import com.alipay.streammedia.mmengine.picture.jpg.PictureHevcFileInfo;
import java.io.FileDescriptor;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

public class ImageInfo {
    public static final int ORIENTATION_FLIP_HORIZONTAL = 2;
    public static final int ORIENTATION_FLIP_VERTICAL = 4;
    public static final int ORIENTATION_NORMAL = 1;
    public static final int ORIENTATION_ROTATE_180 = 3;
    public static final int ORIENTATION_ROTATE_270 = 8;
    public static final int ORIENTATION_ROTATE_90 = 6;
    public static final int ORIENTATION_TRANSPOSE = 5;
    public static final int ORIENTATION_TRANSVERSE = 7;
    public static final int ROTATION_0 = 0;
    public static final int ROTATION_180 = 180;
    public static final int ROTATION_270 = 270;
    public static final int ROTATION_90 = 90;
    private static final String TAG = "ImageInfo";
    public static final Pattern sExcludePathPattern = Pattern.compile("(multimedia/[0-9a-z]{32})|(tencent)", 2);
    private static Set<String> sNoneThumbnailSet = Collections.synchronizedSet(new HashSet());
    public int correctHeight;
    public int correctWidth;
    public byte[] data;
    public Integer format = null;
    public int height;
    private ImageInfo mThumbnailInfo;
    public int orientation = 1;
    public String path;
    public int rotation;
    public int version = -1;
    public int width;

    public static ImageInfo getImageInfo(Bitmap bitmap, int rotation2) {
        ImageInfo info = new ImageInfo();
        if (bitmap != null) {
            info.width = bitmap.getWidth();
            info.height = bitmap.getHeight();
        }
        info.rotation = rotation2;
        info.reviseWidthAndHeight();
        return info;
    }

    public static ImageInfo getImageInfo(int width2, int height2) {
        ImageInfo info = new ImageInfo();
        info.width = width2;
        info.height = height2;
        info.reviseWidthAndHeight();
        return info;
    }

    public static ImageInfo getImageInfo(FileDescriptor fd) {
        long start = System.currentTimeMillis();
        int width2 = 0;
        int height2 = 0;
        int rotation2 = 0;
        int orientation2 = 1;
        try {
            Options options = new Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFileDescriptor(fd, null, options);
            width2 = options.outWidth;
            height2 = options.outHeight;
            orientation2 = Exif.getOrientation(fd);
            rotation2 = getImageRotation(orientation2);
        } catch (Throwable t) {
            LogUtils.w(TAG, "getImageInfo exp fd: " + fd + ", error: " + t);
        }
        ImageInfo info = new ImageInfo();
        info.width = width2;
        info.height = height2;
        info.rotation = rotation2;
        info.orientation = orientation2;
        info.version = -1;
        info.reviseWidthAndHeight();
        info.format = Integer.valueOf(6);
        LogUtils.d(TAG, "getImageInfo from FileDescriptor, cost: " + (System.currentTimeMillis() - start) + ", info: " + info);
        return info;
    }

    public static ImageInfo getImageInfo(String image) {
        long start = System.currentTimeMillis();
        int width2 = 0;
        int height2 = 0;
        int rotation2 = 0;
        int version2 = -1;
        int orientation2 = 1;
        int format2 = 6;
        if (FileUtils.checkFile(image)) {
            format2 = ImageFileType.detectImageFileType(image);
            if (5 == format2) {
                PictureFileConfig cfg = new PictureFileConfig();
                cfg.srcFile = image;
                try {
                    PictureHevcFileInfo info = MMNativeEngineApi.getHevcFileInfo(cfg);
                    if (info != null && info.errorno == 0) {
                        width2 = info.width;
                        height2 = info.height;
                        version2 = info.version;
                    }
                } catch (MMNativeException e) {
                    LogUtils.e(TAG, "getHevcImageInfo image: " + image + ";code=" + e.getCode(), e);
                }
            } else {
                try {
                    Options options = new Options();
                    options.inJustDecodeBounds = true;
                    BitmapFactory.decodeFile(image, options);
                    width2 = options.outWidth;
                    height2 = options.outHeight;
                    orientation2 = Exif.getOrientation(image);
                    rotation2 = getImageRotation(orientation2);
                } catch (Throwable t) {
                    LogUtils.w(TAG, "getImageInfo image: " + image + ", error: " + t);
                }
            }
        }
        ImageInfo info2 = new ImageInfo();
        info2.width = width2;
        info2.height = height2;
        info2.rotation = rotation2;
        info2.orientation = orientation2;
        info2.version = version2;
        info2.reviseWidthAndHeight();
        info2.path = image;
        info2.format = Integer.valueOf(format2);
        LogUtils.d(TAG, "getImageInfo from file, cost: " + (System.currentTimeMillis() - start) + ", file: " + image + ", info: " + info2);
        return info2;
    }

    public static int getImageRotation(int orientation2) {
        switch (orientation2) {
            case 1:
                return 0;
            case 3:
                return 180;
            case 6:
                return 90;
            case 8:
                return 270;
            default:
                return 0;
        }
    }

    public static ImageInfo getImageInfo(byte[] image) {
        long start = System.currentTimeMillis();
        int orientation2 = Exif.getOrientation(image);
        Options options = new Options();
        if (image != null) {
            try {
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeByteArray(image, 0, image.length, options);
            } catch (Throwable t) {
                LogUtils.w(TAG, "getImageInfo image: " + image + ", error: " + t);
            }
        }
        ImageInfo info = new ImageInfo();
        info.width = options.outWidth;
        info.height = options.outHeight;
        info.rotation = getImageRotation(orientation2);
        info.orientation = orientation2;
        info.reviseWidthAndHeight();
        info.data = image;
        LogUtils.d(TAG, "getImageInfo from byte[], cost: " + (System.currentTimeMillis() - start) + ", info: " + info);
        return info;
    }

    public ImageInfo getThumbnailInfo() {
        if (this.mThumbnailInfo == null && !TextUtils.isEmpty(this.path) && !sNoneThumbnailSet.contains(this.path) && !sExcludePathPattern.matcher(this.path).find() && matchFormat(0)) {
            try {
                byte[] data2 = new ExifInterface(this.path).getThumbnail();
                if (data2 != null) {
                    this.mThumbnailInfo = getImageInfo(data2);
                }
            } catch (Exception e) {
                sNoneThumbnailSet.add(this.path);
            }
        }
        return this.mThumbnailInfo;
    }

    private void reviseWidthAndHeight() {
        switch (this.orientation) {
            case 6:
            case 8:
                this.correctWidth = this.height;
                this.correctHeight = this.width;
                return;
            default:
                this.correctWidth = this.width;
                this.correctHeight = this.height;
                return;
        }
    }

    public int getFormat() {
        if (this.format == null) {
            this.format = Integer.valueOf(this.data == null ? ImageFileType.detectImageFileType(this.path) : ImageFileType.detectImageDataType(this.data));
        }
        return this.format.intValue();
    }

    public boolean matchFormat(int format2) {
        return format2 == getFormat();
    }

    public String toString() {
        return "ImageInfo{path='" + this.path + '\'' + ", width=" + this.width + ", height=" + this.height + ", rotation=" + this.rotation + ", orientation=" + this.orientation + ", correctWidth=" + this.correctWidth + ", correctHeight=" + this.correctHeight + ", data=" + this.data + ", mThumbnailInfo=" + this.mThumbnailInfo + ", format=" + this.format + ", version=" + this.version + '}';
    }
}
