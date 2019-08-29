package com.alipay.mobile.framework.service.common.impl;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.framework.service.common.ImageLoaderListener;
import com.alipay.mobile.framework.service.common.impl.ImageLoaderServiceImpl.ImageStrategy;
import java.io.IOException;
import java.io.InputStream;

public class DefaultImageStrategy implements ImageStrategy {
    private static int[][] a = {new int[]{40, 40}, new int[]{80, 80}, new int[]{160, 160}, new int[]{-1, -1}};

    class Size {
        public int mHeight;
        public int mWidth;

        public Size(int width, int height) {
            this.mWidth = width;
            this.mHeight = height;
            if (Boolean.FALSE.booleanValue()) {
                Log.v("hackbyte ", ClassVerifier.class.toString());
            }
        }
    }

    public DefaultImageStrategy() {
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    private static Size a(Size size) {
        int width = size.mWidth;
        int height = size.mHeight;
        int variancePre = Integer.MAX_VALUE;
        for (int index = 0; a[index][0] > 0; index++) {
            int varianceCur = ((a[index][0] - size.mWidth) * (a[index][0] - size.mWidth)) + ((a[index][1] - size.mHeight) * (a[index][1] - size.mHeight));
            if (varianceCur < variancePre) {
                variancePre = varianceCur;
                width = a[index][0];
                height = a[index][1];
            }
        }
        return new Size(width, height);
    }

    public String preferImageUrl(String orignUrl, int width, int height) {
        String url = orignUrl;
        if (orignUrl.contains("[imgWidth]")) {
            if (width < 0 || height < 0) {
                LoggerFactory.getTraceLogger().warn((String) "ImageStrategy", (String) "width<0||height<0");
            }
            Size size = a(new Size(width, height));
            return orignUrl.replace("[imgWidth]", size.mWidth).replace("[imgHeight]", size.mHeight);
        } else if (!orignUrl.contains("[pixelWidth]")) {
            return url;
        } else {
            if (width < 0) {
                LoggerFactory.getTraceLogger().warn((String) "ImageStrategy", (String) "width<0");
            }
            String url2 = orignUrl.replace("[pixelWidth]", String.valueOf(width));
            if (!orignUrl.contains("[pixelHeight]")) {
                return url2;
            }
            if (height < 0) {
                LoggerFactory.getTraceLogger().warn((String) "ImageStrategy", (String) "height<0");
            }
            return url2.replace("[pixelHeight]", String.valueOf(height));
        }
    }

    public boolean loadAssetImage(String orignUrl, ImageLoaderListener listener) {
        if (!orignUrl.contains("[asset]")) {
            return false;
        }
        AssetManager assetManager = LauncherApplicationAgent.getInstance().getApplicationContext().getAssets();
        InputStream stream = null;
        try {
            listener.onPreLoad(orignUrl);
            stream = assetManager.open(orignUrl.substring(16));
            Bitmap bitmap = BitmapFactory.decodeStream(stream);
            listener.onProgressUpdate(orignUrl, 1.0d);
            listener.onPostLoad(orignUrl, bitmap);
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    LoggerFactory.getTraceLogger().error((String) "ImageStrategy", String.valueOf(e));
                }
            }
        } catch (IOException e2) {
            listener.onFailed(orignUrl, 0, String.valueOf(e2));
            LoggerFactory.getTraceLogger().error((String) "ImageStrategy", String.valueOf(e2));
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e3) {
                    LoggerFactory.getTraceLogger().error((String) "ImageStrategy", String.valueOf(e3));
                }
            }
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e4) {
                    LoggerFactory.getTraceLogger().error((String) "ImageStrategy", String.valueOf(e4));
                }
            }
        }
        return true;
    }
}
