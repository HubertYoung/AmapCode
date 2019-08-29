package com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.assist;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory.Options;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.support.annotation.Nullable;
import android.support.v4.util.LruCache;
import android.text.TextUtils;
import android.util.TypedValue;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.CutScaleType;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.BitmapCacheLoader;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.memory.utils.AshmemLocalMonitor;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.io.IOUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.common.TaskScheduleManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.ConfigManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.ImageLoadReq;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.drawable.ResBitmapDrawable;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.AppUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.PathUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.UCLogUtil;
import com.alipay.mobile.common.transport.monitor.RPCDataParser;
import com.alipay.mobile.framework.service.common.TaskScheduleService;
import com.alipay.multimedia.img.ImageInfo;
import com.alipay.multimedia.img.decode.DecodeOptions;
import com.alipay.multimedia.img.decode.InnerDecoder;
import com.alipay.rdssecuritysdk.constant.DictionaryKeys;
import com.autonavi.common.SuperId;
import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ResourcesHelper {
    private static final LruCache<String, Integer> a = new LruCache<>(10000);
    private static Set<String> b = new HashSet();
    private static SharedPreferences c;

    public static Drawable getDrawable(Context context, String resPath, ImageLoadReq loadReq) {
        if (context == null || !PathUtils.isResFile(resPath)) {
            return null;
        }
        Bitmap bitmap = getBitmap(context, resPath, loadReq);
        if (bitmap != null) {
            return makeResDrawable(context, bitmap, resPath);
        }
        return null;
    }

    @Nullable
    public static Bitmap getBitmap(Context context, String resPath, ImageLoadReq loadReq) {
        final BitmapCacheLoader cacheLoader = loadReq.loadEngine.getCacheLoader();
        Bitmap bitmap = cacheLoader.getMemCache(loadReq.cacheKey, loadReq.options.getBusinessId());
        if (bitmap == null) {
            Logger.D("ResourcesHelper", "getDrawable resPath: " + resPath + ", not hit cache!", new Object[0]);
            int resId = PathUtils.extraResId(resPath);
            if (resId > 0) {
                long start = System.currentTimeMillis();
                InputStream inputStream = context.getResources().openRawResource(resId);
                if (CutScaleType.NONE.equals(loadReq.options.getCutScaleType())) {
                    try {
                        byte[] data = IOUtils.readToBytes(inputStream, true);
                        ImageInfo info = ImageInfo.getImageInfo(data);
                        DecodeOptions options = new DecodeOptions();
                        int tdpi = AppUtils.getResources().getDisplayMetrics().densityDpi;
                        int dpi = getDensity(loadReq.options.getContext(), resPath);
                        if (dpi > tdpi) {
                            options.baseOptions = new Options();
                            options.baseOptions.inTargetDensity = tdpi;
                            options.baseOptions.inDensity = dpi;
                        }
                        bitmap = InnerDecoder.decodeByteArray(data, info, options).bitmap;
                    } catch (Exception e) {
                        bitmap = null;
                        Logger.E((String) "ResourcesHelper", (Throwable) e, "getBitmap error, ctx: " + context + ", path: " + resPath + ", req: " + loadReq, new Object[0]);
                    } finally {
                        IOUtils.closeQuietly(inputStream);
                    }
                } else {
                    try {
                        bitmap = FalconFacade.get().cutImageKeepRatio(inputStream, loadReq.options.getWidth().intValue(), loadReq.options.getHeight().intValue());
                    } catch (Exception e2) {
                        bitmap = null;
                        Logger.E((String) "ResourcesHelper", (Throwable) e2, "getDrawable error, context: " + context + ", path: " + resPath + ", req: " + loadReq, new Object[0]);
                    } finally {
                        IOUtils.closeQuietly(inputStream);
                    }
                }
                int cost = (int) (System.currentTimeMillis() - start);
                if (bitmap != null) {
                    a(context, resId, cost, resPath, bitmap, loadReq);
                }
            }
            if (bitmap != null) {
                if (AppUtils.inMainLooper()) {
                    final ImageLoadReq imageLoadReq = loadReq;
                    final Bitmap bitmap2 = bitmap;
                    TaskScheduleManager.get().commonExecutor().submit(new Runnable() {
                        public final void run() {
                            cacheLoader.putMemCache(imageLoadReq.cacheKey, bitmap2, imageLoadReq.options.getBusinessId());
                        }
                    });
                } else {
                    cacheLoader.putMemCache(loadReq.cacheKey, bitmap, loadReq.options.getBusinessId());
                }
            }
        }
        return bitmap;
    }

    public static Drawable makeResDrawable(Context context, Bitmap bitmap, String resPath) {
        int tdpi = AppUtils.getResources().getDisplayMetrics().densityDpi;
        int dpi = getDensity(context, resPath);
        if (VERSION.SDK_INT >= ConfigManager.getInstance().getCommonConfigItem().resDpiChangeVer) {
            dpi = Math.min(tdpi, dpi);
        }
        return ResBitmapDrawable.create(AppUtils.getResources(), bitmap, dpi);
    }

    public static int getDensity(Context context, String resPath) {
        Integer density = (Integer) a.get(resPath);
        if (density == null) {
            if (context != null) {
                TypedValue value = new TypedValue();
                context.getResources().getValue(PathUtils.extraResId(resPath), value, true);
                density = Integer.valueOf(value.density);
                a.put(resPath, density);
            } else {
                density = Integer.valueOf(AppUtils.getResources().getDisplayMetrics().densityDpi);
            }
        }
        return density.intValue();
    }

    private static void a(Context context, int resId, int cost, String resPath, Bitmap bitmap, ImageLoadReq req) {
        final String zoom;
        if (CutScaleType.NONE.equals(req.options.getCutScaleType())) {
            zoom = "-1x-1";
        } else {
            zoom = req.options.getWidth() + DictionaryKeys.CTRLXY_X + req.options.getHeight();
        }
        final int width = bitmap.getWidth();
        final int height = bitmap.getHeight();
        final int config = bitmap.getConfig() == null ? 1 : Config.RGB_565.equals(bitmap.getConfig()) ? 2 : 4;
        final int memSize = (bitmap.getRowBytes() * bitmap.getHeight()) / 1024;
        TaskScheduleService service = AppUtils.getTaskScheduleService();
        if (service != null) {
            final int i = resId;
            final Context context2 = context;
            final String str = resPath;
            final ImageLoadReq imageLoadReq = req;
            final int i2 = cost;
            service.addIdleTask(new Runnable() {
                public final void run() {
                    int reduceHeapMem;
                    if (!ResourcesHelper.b(i, zoom)) {
                        int density = ResourcesHelper.getDensity(context2, str);
                        int targetDensity = AppUtils.getResources().getDisplayMetrics().densityDpi;
                        int ashmem = AshmemLocalMonitor.get().isUseAshmem() ? 1 : 0;
                        int osMem = (int) ((((double) ((width * height) * config)) * Math.pow((double) ((((float) targetDensity) * 1.0f) / ((float) density)), 2.0d)) / 1024.0d);
                        int reduceMem = osMem - memSize;
                        if (ashmem == 1) {
                            reduceHeapMem = osMem;
                        } else {
                            reduceHeapMem = reduceMem;
                        }
                        Map ext4 = new HashMap();
                        ext4.put("id", Integer.toHexString(i));
                        ext4.put("dpi", String.valueOf(density));
                        ext4.put("tdpi", String.valueOf(targetDensity));
                        ext4.put("zoom", zoom);
                        ext4.put("w", String.valueOf(width));
                        ext4.put("h", String.valueOf(height));
                        ext4.put(SuperId.BIT_1_NEARBY_SEARCH, String.valueOf(config));
                        ext4.put(RPCDataParser.TIME_MS, String.valueOf(memSize));
                        ext4.put("oms", String.valueOf(osMem));
                        ext4.put("ash", String.valueOf(ashmem));
                        ext4.put("rm", String.valueOf(reduceMem));
                        ext4.put("rhm", String.valueOf(reduceHeapMem));
                        ext4.put("bz", imageLoadReq.options.getBizType());
                        UCLogUtil.UC_MM_C501(0, i2, ext4);
                    }
                }
            });
        }
    }

    /* access modifiers changed from: private */
    public static boolean b(int resId, String zoom) {
        String key = resId + "_" + zoom;
        synchronized (ResourcesHelper.class) {
            if (b.contains(key)) {
                return true;
            }
            if (c == null || !c.getBoolean(key, false)) {
                if (c == null) {
                    SharedPreferences sharedPreferences = AppUtils.getApplicationContext().getSharedPreferences("apm_report_res", 0);
                    c = sharedPreferences;
                    String ver = sharedPreferences.getString("ver", "");
                    String version = AppUtils.getVersion(AppUtils.getApplicationContext());
                    if (!TextUtils.equals(ver, version)) {
                        c.edit().clear().apply();
                        c.edit().putString("ver", version).apply();
                    }
                }
                c.edit().putBoolean(key, true).apply();
                return false;
            }
            b.add(key);
            return true;
        }
    }
}
