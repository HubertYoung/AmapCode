package com.android.dingtalk.share.ddsharemodule.message;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.os.Bundle;
import com.android.dingtalk.share.ddsharemodule.ShareConstant;
import com.android.dingtalk.share.ddsharemodule.message.DDMediaMessage.IMediaObject;
import java.io.ByteArrayOutputStream;

public class DDImageMessage implements IMediaObject {
    private static final int MAX_IMAGE_DATA_LENGTH = 10485760;
    private static final int MAX_IMAGE_THUMB_DATA_LENGTH = 32768;
    private static final int MAX_IMAGE_URL_LENGTH = 10240;
    private static final String TAG = "DDImageMessage";
    public byte[] mImageData;
    public String mImagePath;
    public String mImageUrl;

    public int type() {
        return 3;
    }

    public DDImageMessage() {
    }

    public DDImageMessage(byte[] bArr) {
        this.mImageData = bArr;
    }

    public DDImageMessage(Bitmap bitmap) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(CompressFormat.JPEG, 85, byteArrayOutputStream);
            this.mImageData = byteArrayOutputStream.toByteArray();
            byteArrayOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void serialize(Bundle bundle) {
        bundle.putByteArray(ShareConstant.EXTRA_IMAGE_OBJECT_IMAGE_DATA, this.mImageData);
        bundle.putString(ShareConstant.EXTRA_IMAGE_OBJECT_IMAGE_PATH, this.mImagePath);
        bundle.putString(ShareConstant.EXTRA_IMAGE_OBJECT_IMAGE_URL, this.mImageUrl);
    }

    public void unserialize(Bundle bundle) {
        this.mImageData = bundle.getByteArray(ShareConstant.EXTRA_IMAGE_OBJECT_IMAGE_DATA);
        this.mImagePath = bundle.getString(ShareConstant.EXTRA_IMAGE_OBJECT_IMAGE_PATH);
        this.mImageUrl = bundle.getString(ShareConstant.EXTRA_IMAGE_OBJECT_IMAGE_URL);
    }

    /* JADX WARNING: Removed duplicated region for block: B:33:0x0061 A[RETURN] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean checkArgs() {
        /*
            r6 = this;
            byte[] r0 = r6.mImageData
            r1 = 0
            if (r0 == 0) goto L_0x000a
            byte[] r0 = r6.mImageData
            int r0 = r0.length
            if (r0 != 0) goto L_0x0022
        L_0x000a:
            java.lang.String r0 = r6.mImagePath
            if (r0 == 0) goto L_0x0016
            java.lang.String r0 = r6.mImagePath
            int r0 = r0.length()
            if (r0 != 0) goto L_0x0022
        L_0x0016:
            java.lang.String r0 = r6.mImageUrl
            if (r0 == 0) goto L_0x0071
            java.lang.String r0 = r6.mImageUrl
            int r0 = r0.length()
            if (r0 == 0) goto L_0x0071
        L_0x0022:
            byte[] r0 = r6.mImageData
            r2 = 10485760(0xa00000, float:1.469368E-38)
            if (r0 == 0) goto L_0x002e
            byte[] r0 = r6.mImageData
            int r0 = r0.length
            if (r0 <= r2) goto L_0x002e
            return r1
        L_0x002e:
            java.lang.String r0 = r6.mImagePath
            r3 = 10240(0x2800, float:1.4349E-41)
            if (r0 == 0) goto L_0x003d
            java.lang.String r0 = r6.mImagePath
            int r0 = r0.length()
            if (r0 <= r3) goto L_0x003d
            return r1
        L_0x003d:
            java.lang.String r0 = r6.mImagePath
            if (r0 == 0) goto L_0x0062
            java.lang.String r0 = r6.mImagePath
            java.lang.String r4 = r6.mImagePath
            if (r4 == 0) goto L_0x005e
            int r4 = r0.length()
            if (r4 == 0) goto L_0x005e
            java.io.File r4 = new java.io.File
            r4.<init>(r0)
            boolean r0 = r4.exists()
            if (r0 == 0) goto L_0x005e
            long r4 = r4.length()
            int r0 = (int) r4
            goto L_0x005f
        L_0x005e:
            r0 = 0
        L_0x005f:
            if (r0 <= r2) goto L_0x0062
            return r1
        L_0x0062:
            java.lang.String r0 = r6.mImageUrl
            if (r0 == 0) goto L_0x006f
            java.lang.String r0 = r6.mImageUrl
            int r0 = r0.length()
            if (r0 <= r3) goto L_0x006f
            return r1
        L_0x006f:
            r0 = 1
            return r0
        L_0x0071:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.dingtalk.share.ddsharemodule.message.DDImageMessage.checkArgs():boolean");
    }
}
