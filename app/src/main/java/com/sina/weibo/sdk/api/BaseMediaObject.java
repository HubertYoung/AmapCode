package com.sina.weibo.sdk.api;

import android.os.Parcel;
import android.os.Parcelable;
import com.sina.weibo.sdk.utils.LogUtil;

public abstract class BaseMediaObject implements Parcelable {
    public static final int MEDIA_TYPE_IMAGE = 2;
    public static final int MEDIA_TYPE_MULITI_IMAGE = 6;
    public static final int MEDIA_TYPE_SOURCE_VIDEO = 7;
    public static final int MEDIA_TYPE_TEXT = 1;
    public static final int MEDIA_TYPE_WEBPAGE = 5;
    public String actionUrl;
    public String description;
    public String identify;
    public String schema;
    public byte[] thumbData;
    public String title;

    public int describeContents() {
        return 0;
    }

    public abstract int getObjType();

    /* access modifiers changed from: protected */
    public abstract BaseMediaObject toExtraMediaObject(String str);

    /* access modifiers changed from: protected */
    public abstract String toExtraMediaString();

    public BaseMediaObject() {
    }

    public BaseMediaObject(Parcel parcel) {
        this.actionUrl = parcel.readString();
        this.schema = parcel.readString();
        this.identify = parcel.readString();
        this.title = parcel.readString();
        this.description = parcel.readString();
        this.thumbData = parcel.createByteArray();
    }

    /* JADX WARNING: Removed duplicated region for block: B:20:0x0031 A[SYNTHETIC, Splitter:B:20:0x0031] */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x003d A[SYNTHETIC, Splitter:B:27:0x003d] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void setThumbImage(android.graphics.Bitmap r4) {
        /*
            r3 = this;
            r0 = 0
            java.io.ByteArrayOutputStream r1 = new java.io.ByteArrayOutputStream     // Catch:{ Exception -> 0x0024 }
            r1.<init>()     // Catch:{ Exception -> 0x0024 }
            android.graphics.Bitmap$CompressFormat r0 = android.graphics.Bitmap.CompressFormat.JPEG     // Catch:{ Exception -> 0x001f, all -> 0x001c }
            r2 = 85
            r4.compress(r0, r2, r1)     // Catch:{ Exception -> 0x001f, all -> 0x001c }
            byte[] r4 = r1.toByteArray()     // Catch:{ Exception -> 0x001f, all -> 0x001c }
            r3.thumbData = r4     // Catch:{ Exception -> 0x001f, all -> 0x001c }
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
            java.lang.String r4 = "Weibo.BaseMediaObject"
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
        throw new UnsupportedOperationException("Method not decompiled: com.sina.weibo.sdk.api.BaseMediaObject.setThumbImage(android.graphics.Bitmap):void");
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.actionUrl);
        parcel.writeString(this.schema);
        parcel.writeString(this.identify);
        parcel.writeString(this.title);
        parcel.writeString(this.description);
        parcel.writeByteArray(this.thumbData);
    }

    /* access modifiers changed from: protected */
    public boolean checkArgs() {
        if (this.actionUrl == null || this.actionUrl.length() > 512) {
            LogUtil.e("Weibo.BaseMediaObject", "checkArgs fail, actionUrl is invalid");
            return false;
        } else if (this.identify == null || this.identify.length() > 512) {
            LogUtil.e("Weibo.BaseMediaObject", "checkArgs fail, identify is invalid");
            return false;
        } else if (this.thumbData == null || this.thumbData.length > 32768) {
            StringBuilder sb = new StringBuilder("checkArgs fail, thumbData is invalid,size is ");
            sb.append(this.thumbData != null ? this.thumbData.length : -1);
            sb.append("! more then 32768.");
            LogUtil.e("Weibo.BaseMediaObject", sb.toString());
            return false;
        } else if (this.title == null || this.title.length() > 512) {
            LogUtil.e("Weibo.BaseMediaObject", "checkArgs fail, title is invalid");
            return false;
        } else if (this.description != null && this.description.length() <= 1024) {
            return true;
        } else {
            LogUtil.e("Weibo.BaseMediaObject", "checkArgs fail, description is invalid");
            return false;
        }
    }
}
