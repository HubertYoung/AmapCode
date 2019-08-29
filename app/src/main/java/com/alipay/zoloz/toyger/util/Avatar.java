package com.alipay.zoloz.toyger.util;

import android.graphics.Bitmap;
import android.util.Base64;
import com.alipay.mobile.security.bio.utils.BitmapHelper;
import com.alipay.zoloz.toyger.face.ToygerFaceAttr;
import com.alipay.zoloz.toyger.workspace.task.ToygerBaseTask;

public class Avatar {
    public static String genAvatar(Bitmap bitmap, ToygerFaceAttr toygerFaceAttr) {
        byte[] bitmap2Bytes = BitmapHelper.bitmap2Bytes(ToygerBaseTask.blur(bitmap, 1.0f));
        if (bitmap2Bytes == null) {
            return "";
        }
        return Base64.encodeToString(bitmap2Bytes, 8);
    }
}
