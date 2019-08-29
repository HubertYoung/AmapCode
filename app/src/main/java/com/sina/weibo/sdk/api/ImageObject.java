package com.sina.weibo.sdk.api;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.file.HttpFileUpMMTask;
import com.sina.weibo.sdk.utils.LogUtil;
import java.io.File;

public class ImageObject extends BaseMediaObject {
    public static final Creator<ImageObject> CREATOR = new Creator<ImageObject>() {
        public final ImageObject createFromParcel(Parcel parcel) {
            return new ImageObject(parcel);
        }

        public final ImageObject[] newArray(int i) {
            return new ImageObject[i];
        }
    };
    private static final int DATA_SIZE = 2097152;
    public byte[] imageData;
    public String imagePath;

    public int describeContents() {
        return 0;
    }

    public int getObjType() {
        return 2;
    }

    /* access modifiers changed from: protected */
    public BaseMediaObject toExtraMediaObject(String str) {
        return this;
    }

    /* access modifiers changed from: protected */
    public String toExtraMediaString() {
        return "";
    }

    public ImageObject() {
    }

    public ImageObject(Parcel parcel) {
        this.imageData = parcel.createByteArray();
        this.imagePath = parcel.readString();
    }

    /* JADX WARNING: Removed duplicated region for block: B:20:0x0031 A[SYNTHETIC, Splitter:B:20:0x0031] */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x003d A[SYNTHETIC, Splitter:B:27:0x003d] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void setImageObject(android.graphics.Bitmap r4) {
        /*
            r3 = this;
            r0 = 0
            java.io.ByteArrayOutputStream r1 = new java.io.ByteArrayOutputStream     // Catch:{ Exception -> 0x0024 }
            r1.<init>()     // Catch:{ Exception -> 0x0024 }
            android.graphics.Bitmap$CompressFormat r0 = android.graphics.Bitmap.CompressFormat.JPEG     // Catch:{ Exception -> 0x001f, all -> 0x001c }
            r2 = 85
            r4.compress(r0, r2, r1)     // Catch:{ Exception -> 0x001f, all -> 0x001c }
            byte[] r4 = r1.toByteArray()     // Catch:{ Exception -> 0x001f, all -> 0x001c }
            r3.imageData = r4     // Catch:{ Exception -> 0x001f, all -> 0x001c }
            r1.close()     // Catch:{ IOException -> 0x0017 }
            return
        L_0x0017:
            r4 = move-exception
            r4.printStackTrace()
            return
        L_0x001c:
            r4 = move-exception
            r0 = r1
            goto L_0x003b
        L_0x001f:
            r4 = move-exception
            r0 = r1
            goto L_0x0025
        L_0x0022:
            r4 = move-exception
            goto L_0x003b
        L_0x0024:
            r4 = move-exception
        L_0x0025:
            r4.printStackTrace()     // Catch:{ all -> 0x0022 }
            java.lang.String r4 = "Weibo.ImageObject"
            java.lang.String r1 = "put thumb failed"
            com.sina.weibo.sdk.utils.LogUtil.e(r4, r1)     // Catch:{ all -> 0x0022 }
            if (r0 == 0) goto L_0x003a
            r0.close()     // Catch:{ IOException -> 0x0035 }
            goto L_0x003a
        L_0x0035:
            r4 = move-exception
            r4.printStackTrace()
            return
        L_0x003a:
            return
        L_0x003b:
            if (r0 == 0) goto L_0x0045
            r0.close()     // Catch:{ IOException -> 0x0041 }
            goto L_0x0045
        L_0x0041:
            r0 = move-exception
            r0.printStackTrace()
        L_0x0045:
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.sina.weibo.sdk.api.ImageObject.setImageObject(android.graphics.Bitmap):void");
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeByteArray(this.imageData);
        parcel.writeString(this.imagePath);
    }

    public boolean checkArgs() {
        if (this.imageData == null && this.imagePath == null) {
            LogUtil.e("Weibo.ImageObject", "imageData and imagePath are null");
            return false;
        } else if (this.imageData != null && this.imageData.length > 2097152) {
            LogUtil.e("Weibo.ImageObject", "imageData is too large");
            return false;
        } else if (this.imagePath == null || this.imagePath.length() <= 512) {
            if (this.imagePath != null) {
                File file = new File(this.imagePath);
                try {
                    if (!file.exists() || file.length() == 0 || file.length() > HttpFileUpMMTask.BIG_FILE_SIZE_THRESHOLD) {
                        LogUtil.e("Weibo.ImageObject", "checkArgs fail, image content is too large or not exists");
                        return false;
                    }
                } catch (SecurityException unused) {
                    LogUtil.e("Weibo.ImageObject", "checkArgs fail, image content is too large or not exists");
                    return false;
                }
            }
            return true;
        } else {
            LogUtil.e("Weibo.ImageObject", "imagePath is too length");
            return false;
        }
    }
}
