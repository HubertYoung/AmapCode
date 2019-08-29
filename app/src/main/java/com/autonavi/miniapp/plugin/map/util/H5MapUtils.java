package com.autonavi.miniapp.plugin.map.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.text.TextUtils;
import android.text.TextUtils.TruncateAt;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import com.alipay.mobile.antui.utils.DensityUtil;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;
import com.amap.bundle.datamodel.point.GeoPointHD;
import com.autonavi.ae.gmap.glinterface.GLGeoPoint;
import com.autonavi.miniapp.plugin.map.AMapH5EmbedMapView;
import com.autonavi.miniapp.plugin.map.markerstyle.view.StrokeTextView;
import com.autonavi.miniapp.plugin.util.MiniAppHelper;
import com.autonavi.minimap.R;
import com.autonavi.minimap.ajx3.Ajx;
import com.autonavi.minimap.ajx3.image.PictureParams;
import com.autonavi.minimap.ajx3.loader.ImageCallback;
import java.math.BigDecimal;
import pl.droidsonroids.gif.GifDrawable;

public class H5MapUtils {

    public interface ImgCallback {
        void onLoadImage(Bitmap bitmap);
    }

    public static boolean isLatLonValid(double d, double d2) {
        return d > -90.0d && d < 90.0d && d2 > -180.0d && d2 < 180.0d;
    }

    private static int rgba2argb(int i) {
        return ((i >> 8) & ViewCompat.MEASURED_SIZE_MASK) | ((i & 255) << 24);
    }

    public static void getImgFromPkg(H5Page h5Page, String str, final ImgCallback imgCallback) {
        if (h5Page != null) {
            String absoluteUrl = getAbsoluteUrl(str, h5Page.getParams());
            Bitmap loadOfflineImage = EmbedMapOfflineImageLoader.getInstance().loadOfflineImage(h5Page, absoluteUrl);
            if (loadOfflineImage != null) {
                imgCallback.onLoadImage(loadOfflineImage);
            } else {
                Ajx.getInstance().lookupLoader(absoluteUrl).preLoadImage(PictureParams.make(null, absoluteUrl, false), new ImageCallback() {
                    public final void onPrepareLoad(Drawable drawable) {
                    }

                    public final void onGifLoaded(GifDrawable gifDrawable) {
                        imgCallback.onLoadImage(null);
                    }

                    public final void onBitmapLoaded(Bitmap bitmap) {
                        imgCallback.onLoadImage(bitmap);
                    }

                    public final void onBitmapFailed(Drawable drawable) {
                        imgCallback.onLoadImage(null);
                    }
                });
            }
        }
    }

    private static String getAbsoluteUrl(String str, Bundle bundle) {
        String string = H5Utils.getString(bundle, (String) "url");
        if (!TextUtils.isEmpty(string)) {
            return H5Utils.getAbsoluteUrlV2(string, str, null);
        }
        return null;
    }

    public static Bitmap getIconWithString2(Context context, float f, int i, String str, String str2, String str3, Bitmap bitmap) {
        if (context == null) {
            LoggerFactory.getTraceLogger().info(AMapH5EmbedMapView.TAG, "iconFromView, context == null");
            return null;
        }
        int convertRGBAColor = convertRGBAColor(str2);
        int convertRGBAColor2 = convertRGBAColor(str3);
        View inflate = LayoutInflater.from(context).inflate(R.layout.marker_icon_from_view, null);
        StrokeTextView strokeTextView = (StrokeTextView) inflate.findViewById(R.id.icon_from_view_str);
        strokeTextView.setTextSize(f);
        strokeTextView.setText(str);
        strokeTextView.setGravity(i);
        strokeTextView.setTextColor(convertRGBAColor);
        strokeTextView.setStrokeWidth(8);
        strokeTextView.setStrokeColor(convertRGBAColor2);
        strokeTextView.setEllipsize(TruncateAt.END);
        strokeTextView.setMaxLines(2);
        strokeTextView.setLayoutParams(new LayoutParams((int) Math.ceil((double) getMaxWidth(context, str, f, 7)), -2));
        ImageView imageView = (ImageView) inflate.findViewById(R.id.icon_from_view_icon);
        if (bitmap == null || bitmap.isRecycled()) {
            imageView.setVisibility(8);
        } else {
            imageView.setVisibility(0);
            imageView.setImageBitmap(bitmap);
        }
        inflate.setDrawingCacheEnabled(true);
        inflate.measure(MeasureSpec.makeMeasureSpec(0, 0), MeasureSpec.makeMeasureSpec(0, 0));
        inflate.layout(0, 0, inflate.getMeasuredWidth(), inflate.getMeasuredHeight());
        inflate.buildDrawingCache();
        Bitmap createBitmap = Bitmap.createBitmap(inflate.getDrawingCache());
        inflate.setDrawingCacheEnabled(false);
        return createBitmap;
    }

    private static float getMaxWidth(Context context, String str, float f, int i) {
        Paint paint = new Paint();
        paint.setTextSize((float) DensityUtil.sp2px(context, f));
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, 0));
        if (str.length() <= i) {
            i = str.length();
        }
        return paint.measureText(str, 0, i);
    }

    public static int convertRGBAColor(String str) {
        return convertRGBAColor2(str, "#00000000");
    }

    public static int convertRGBAColor2(String str, String str2) {
        try {
            if (TextUtils.isEmpty(str)) {
                return rgba2argb(Color.parseColor(str2));
            }
            if (str.length() == 7) {
                return Color.parseColor(str);
            }
            return rgba2argb(Color.parseColor(str));
        } catch (Throwable unused) {
            LoggerFactory.getTraceLogger().error((String) AMapH5EmbedMapView.TAG, "error, ori=".concat(String.valueOf(str)));
            return rgba2argb(Color.parseColor(str2));
        }
    }

    private static void ellipsizeEnd(TextView textView, String str, int i) {
        try {
            if (textView.getLineCount() > i) {
                int lineEnd = textView.getLayout().getLineEnd(i - 1);
                if (lineEnd >= 0) {
                    if (lineEnd < str.length()) {
                        int i2 = lineEnd - 3;
                        if (i2 >= 0) {
                            StringBuilder sb = new StringBuilder();
                            sb.append(str.substring(0, i2));
                            sb.append("...");
                            textView.setText(sb.toString());
                        }
                    }
                }
            }
        } catch (Exception e) {
            H5Log.e((String) AMapH5EmbedMapView.TAG, (Throwable) e);
        }
    }

    public static double convertLatLon(double d) {
        return BigDecimal.valueOf(d).setScale(7, 1).doubleValue();
    }

    public static double convertScale(double d) {
        return BigDecimal.valueOf(d).setScale(2, 1).doubleValue();
    }

    public static double convertScreenDP(double d) {
        return BigDecimal.valueOf(d).setScale(2, 1).doubleValue();
    }

    public static double convertAngle(double d) {
        return BigDecimal.valueOf(d).setScale(2, 1).doubleValue();
    }

    public static double convertDegree(double d) {
        return BigDecimal.valueOf(d).setScale(2, 1).doubleValue();
    }

    public static boolean isLatLonEqual(double d, double d2, double d3, double d4) {
        return Math.abs(d - d3) < 1.0E-6d && Math.abs(d2 - d4) < 1.0E-6d;
    }

    public static boolean isLatLonEqual(double d, double d2, GLGeoPoint gLGeoPoint) {
        GeoPointHD geoPointHD = new GeoPointHD(gLGeoPoint);
        return isLatLonEqual(d, geoPointHD.getLatitude(), d2, geoPointHD.getLongitude());
    }

    public static boolean isScaleEqual(float f, float f2) {
        return ((double) Math.abs(f - f2)) < 0.01d;
    }

    public static boolean isAngleEqual(float f, float f2) {
        return ((double) Math.abs(f - f2)) < 0.01d;
    }

    public static boolean isDegreeEqual(float f, float f2) {
        return ((double) Math.abs(f - f2)) < 0.01d;
    }

    public static void setMapCenter(double d, double d2, bty bty) {
        if (!MiniAppHelper.compareEqual(BigDecimal.valueOf(d2), BigDecimal.valueOf(-1000.0d)) && !MiniAppHelper.compareEqual(BigDecimal.valueOf(d), BigDecimal.valueOf(-1000.0d)) && !isLatLonEqual(d, d2, bty.n())) {
            GeoPointHD geoPointHD = new GeoPointHD(d2, d);
            bty.a(geoPointHD.x, geoPointHD.y);
        }
    }

    public static void setMapScale(float f, bty bty) {
        if (!MiniAppHelper.compareEqual(BigDecimal.valueOf((double) f), BigDecimal.valueOf(-1.0d)) && !isScaleEqual(f, bty.v())) {
            bty.d(f);
        }
    }

    public static boolean isPointValid(double d, double d2, bty bty) {
        return d >= 0.0d && d <= ((double) bty.al()) && d2 >= 0.0d && d2 <= ((double) bty.am()) && d2 >= ((double) bty.f().getWinSkyHeight());
    }

    public static float px2dp(Context context, float f) {
        return f / context.getResources().getDisplayMetrics().density;
    }

    public static float dp2px(Context context, float f) {
        return f * context.getResources().getDisplayMetrics().density;
    }
}
