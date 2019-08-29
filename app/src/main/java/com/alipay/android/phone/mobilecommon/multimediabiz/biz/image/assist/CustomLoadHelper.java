package com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.assist;

import android.graphics.drawable.Drawable;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.load.DisplayImageOptions.DrawableDecoder;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.io.IOUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.ImageUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class CustomLoadHelper {
    public static Drawable loadDrawable(File file, DrawableDecoder decoder) {
        Drawable drawable = null;
        if (decoder == null) {
            return ImageUtils.decodeDrawable(file);
        }
        FileInputStream fis = null;
        try {
            FileInputStream fis2 = new FileInputStream(file);
            try {
                Drawable drawable2 = decoder.decode(fis2);
                IOUtils.closeQuietly((InputStream) fis2);
                return drawable2;
            } catch (Exception e) {
                fis = fis2;
                try {
                    Logger.E("CustomLoadHelper", "loadDrawable custom decode fail: " + file, new Object[0]);
                    IOUtils.closeQuietly((InputStream) fis);
                    return drawable;
                } catch (Throwable th) {
                    th = th;
                    IOUtils.closeQuietly((InputStream) fis);
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                fis = fis2;
                IOUtils.closeQuietly((InputStream) fis);
                throw th;
            }
        } catch (Exception e2) {
            Logger.E("CustomLoadHelper", "loadDrawable custom decode fail: " + file, new Object[0]);
            IOUtils.closeQuietly((InputStream) fis);
            return drawable;
        }
    }
}
