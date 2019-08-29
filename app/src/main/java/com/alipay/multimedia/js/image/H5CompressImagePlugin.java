package com.alipay.multimedia.js.image;

import android.text.TextUtils;
import android.util.Log;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.android.hackbyte.ClassVerifier;
import com.alipay.android.phone.mobilecommon.multimedia.api.MultimediaImageProcessor;
import com.alipay.android.phone.mobilecommon.multimedia.api.data.image.processor.encode.APEncodeOptions;
import com.alipay.android.phone.mobilecommon.multimedia.api.data.image.processor.encode.APEncodeResult;
import com.alipay.android.phone.mobilecommon.multimedia.api.data.image.processor.encode.APMaxLenMode;
import com.alipay.mobile.beehive.audio.Constants;
import com.alipay.mobile.beehive.plugins.Constant;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5Event.Error;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.mobile.nebula.resourcehandler.H5ResourceHandlerUtil;
import com.alipay.mobile.nebula.util.H5NetworkUtil;
import com.alipay.mobile.nebula.util.H5NetworkUtil.Network;
import com.alipay.multimedia.io.FileUtils;
import com.alipay.multimedia.io.PathUtils;
import com.alipay.multimedia.js.base.MMH5SimplePlugin;
import com.alipay.multimedia.js.utils.Base64Utils;
import com.alipay.multimedia.js.utils.Logger;
import com.alipay.multimedia.js.utils.MD5Utils;
import com.alipay.multimedia.js.utils.Utils;
import java.io.File;

public class H5CompressImagePlugin extends MMH5SimplePlugin {
    public static final String ACTION_COMPRESS_IMAGE = "compressImage";
    public static final int COMPRESS_LEVEL_AUTO = 4;
    public static final int COMPRESS_LEVEL_HIGH = 2;
    public static final int COMPRESS_LEVEL_LOW = 0;
    public static final int COMPRESS_LEVEL_NONE = 3;
    public static final int COMPRESS_LEVEL_NORMAL = 1;
    public static final String DATA_TYPE_DATA_URL = "dataURL";
    public static final String DATA_TYPE_FILE_URL = "fileURL";
    public static final String DATA_TYPE_LOCAL_ID = "localID";

    public H5CompressImagePlugin() {
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public void onPrepare(H5EventFilter filter) {
        super.onPrepare(filter);
        filter.addAction(ACTION_COMPRESS_IMAGE);
    }

    public boolean handleEvent(H5Event event, H5BridgeContext context) {
        Logger.debug("H5CompressImage", "handleEvent event: " + event + ", context: " + context);
        try {
            final JSONArray array = event.getParam().getJSONArray(Constant.AL_MEDIA_FILES);
            final int maxWidth = getIntParam(event, "maxWidth", 1280);
            final int maxHeight = getIntParam(event, "maxHeight", 1280);
            final int compressLevel = event.getParam().containsKey(MultimediaImageProcessor.COMPOSITE_INT_KEY_COMPRESS_LEVEL) ? getIntParam(event, MultimediaImageProcessor.COMPOSITE_INT_KEY_COMPRESS_LEVEL, 4) : getIntParam(event, "compressLevel", 4);
            final String business = getStringParam(event, Constants.KEY_AUDIO_BUSINESS_ID, "apm-h5");
            final String data = getStringParam(event, "data");
            final String dataType = getStringParam(event, "dataType");
            if ((array == null || array.isEmpty()) && (TextUtils.isEmpty(data) || TextUtils.isEmpty(dataType))) {
                Logger.debug("H5CompressImage", "handleEvent error, array: " + array + ", data: " + data + ", dataType: " + dataType + ",business=" + business);
                return context.sendError(event, Error.INVALID_PARAM);
            }
            final H5BridgeContext h5BridgeContext = context;
            if (!Utils.execute(new Runnable() {
                {
                    if (Boolean.FALSE.booleanValue()) {
                        Log.v("hackbyte ", ClassVerifier.class.toString());
                    }
                }

                public void run() {
                    try {
                        if (array != null) {
                            H5CompressImagePlugin.this.a(array, maxWidth, maxHeight, compressLevel, business, h5BridgeContext);
                        } else {
                            H5CompressImagePlugin.this.a(data, dataType, maxWidth, maxHeight, compressLevel, h5BridgeContext);
                        }
                    } catch (Exception e2) {
                        Logger.error("H5CompressImage", "handleEvent error", e2);
                        H5CompressImagePlugin.b(h5BridgeContext);
                    }
                }
            })) {
                context.sendError(event, Error.UNKNOWN_ERROR);
            }
            return true;
        } catch (Throwable e) {
            Logger.error("H5CompressImage", "handleEvent.file patch error:", e);
            context.sendError(event, Error.INVALID_PARAM);
        }
    }

    /* access modifiers changed from: private */
    public void a(String data, String dataType, int maxWidth, int maxHeight, int compressLevel, H5BridgeContext context) {
        MultimediaImageProcessor processor = (MultimediaImageProcessor) Utils.getService(MultimediaImageProcessor.class);
        int max = Math.max(maxWidth, maxHeight);
        String out = null;
        if (TextUtils.equals(dataType, DATA_TYPE_FILE_URL) || TextUtils.equals(dataType, DATA_TYPE_LOCAL_ID)) {
            if (3 != compressLevel) {
                String path = PathUtils.trimFilePath(decodeToPath(data));
                if (!TextUtils.isEmpty(path)) {
                    out = a(compressLevel, max, processor, new File(path));
                }
            } else {
                out = data;
            }
        } else if (TextUtils.equals(dataType, DATA_TYPE_DATA_URL)) {
            out = a(compressLevel, max, processor, Base64Utils.decodeToBytes(data));
        }
        if (out != null) {
            a(context, out);
        } else {
            b(context);
        }
    }

    /* access modifiers changed from: private */
    public void a(JSONArray array, int maxWidth, int maxHeight, int compressLevel, String business, H5BridgeContext context) {
        a(context, a(array, maxWidth, maxHeight, compressLevel, business));
    }

    private String[] a(JSONArray array, int maxWidth, int maxHeight, int compressLevel, String business) {
        String[] result = new String[array.size()];
        int max = Math.max(maxWidth, maxHeight);
        MultimediaImageProcessor processor = (MultimediaImageProcessor) Utils.getService(MultimediaImageProcessor.class);
        for (int i = 0; i < array.size(); i++) {
            long start = System.currentTimeMillis();
            String item = array.getString(i);
            result[i] = item;
            if (compressLevel != 3) {
                String path = PathUtils.trimFilePath(H5ResourceHandlerUtil.apUrlToFilePath(item));
                String out = a(compressLevel, max, processor, TextUtils.isEmpty(path) ? null : new File(path));
                if (out == null) {
                    out = item;
                }
                result[i] = out;
            }
            Logger.debug("H5CompressImage", "compressFiles item: " + item + ", result: " + result[i] + ", biz: " + business + ", cost: " + (System.currentTimeMillis() - start));
        }
        return result;
    }

    private String a(int compressLevel, int max, MultimediaImageProcessor processor, File file) {
        String out = null;
        if (file != null && file.exists() && file.length() > 0) {
            int quality = a(compressLevel);
            String outPath = a(file.getName(), max, quality);
            try {
                long start = System.currentTimeMillis();
                if (FileUtils.checkFile(outPath)) {
                    outPath = encodeToLocalId(outPath);
                    out = H5ResourceHandlerUtil.localIdToUrl(outPath, "image");
                } else {
                    APEncodeOptions options = new APEncodeOptions();
                    options.mode = new APMaxLenMode(max);
                    options.quality = quality;
                    options.outputFile = outPath;
                    APEncodeResult encodeResult = processor.compress(file, options);
                    if (encodeResult.isSuccess()) {
                        outPath = encodeToLocalId(encodeResult.encodeFilePath);
                        out = H5ResourceHandlerUtil.localIdToUrl(outPath, "image");
                    }
                }
                Logger.debug("H5CompressImage", "compressFile in: " + file + ", len: " + file.length() + ", outPath: " + outPath + ", length: " + (TextUtils.isEmpty(outPath) ? 0 : new File(outPath).length()) + ", cost: " + (System.currentTimeMillis() - start));
            } catch (Exception t) {
                Logger.error("H5CompressImage", "compressFiles file: " + file + ", quality: " + quality + ", out: " + null + " error!!", t);
            }
        }
        return out;
    }

    private String a(int compressLevel, int max, MultimediaImageProcessor processor, byte[] data) {
        String out = null;
        if (data == null || data.length <= 0) {
            return null;
        }
        int quality = a(compressLevel);
        String outPath = a(MD5Utils.getMD5String(data), max, quality);
        if (compressLevel == 3) {
            FileUtils.safeCopyToFile(data, new File(outPath));
            String out2 = H5ResourceHandlerUtil.localIdToUrl(encodeToLocalId(outPath), "image");
            Logger.debug("H5CompressImage", "compressData none, " + out2);
            return out2;
        }
        try {
            long start = System.currentTimeMillis();
            if (FileUtils.checkFile(outPath)) {
                outPath = encodeToLocalId(outPath);
                out = H5ResourceHandlerUtil.localIdToUrl(outPath, "image");
            } else {
                APEncodeOptions options = new APEncodeOptions();
                options.mode = new APMaxLenMode(max);
                options.quality = quality;
                options.outputFile = outPath;
                APEncodeResult encodeResult = processor.compress(data, options);
                if (encodeResult.isSuccess()) {
                    outPath = encodeToLocalId(encodeResult.encodeFilePath);
                    out = H5ResourceHandlerUtil.localIdToUrl(outPath, "image");
                }
            }
            Logger.debug("H5CompressImage", "compressData in: " + data + ", len: " + data.length + ", outPath: " + outPath + ", length: " + (TextUtils.isEmpty(outPath) ? 0 : new File(outPath).length()) + ", cost: " + (System.currentTimeMillis() - start));
            return out;
        } catch (Exception t) {
            Logger.error("H5CompressImage", "compressData data: " + data + ", quality: " + quality + ", out: " + null + " error!!", t);
            return null;
        }
    }

    private static int a(int compressLevel) {
        int quality;
        switch (compressLevel) {
            case 2:
                quality = 1;
                break;
            case 4:
                if (H5NetworkUtil.getInstance().getNetworkType() != Network.NETWORK_WIFI) {
                    quality = 0;
                    break;
                } else {
                    quality = 1;
                    break;
                }
            default:
                quality = 0;
                break;
        }
        Logger.debug("H5CompressImage", "calcQuality compressLevel: " + compressLevel);
        return quality;
    }

    private static String a(String name, int max, int quality) {
        File targetFile = new File(Utils.getContext().getCacheDir() + File.separator + "apm-h5", max + "_" + quality + "_" + name);
        targetFile.getParentFile().mkdirs();
        Logger.debug("H5CompressImage", "makeOutputPath: " + targetFile);
        return targetFile.getAbsolutePath();
    }

    private static void a(H5BridgeContext context, String[] data) {
        JSONObject result = new JSONObject();
        result.put((String) Constant.AL_MEDIA_FILES, (Object) data);
        a(context, result);
    }

    private static void a(H5BridgeContext context, String path) {
        JSONObject result = new JSONObject();
        result.put((String) Constant.AL_MEDIA_FILE, (Object) path);
        a(context, result);
    }

    private static void a(H5BridgeContext context, JSONObject result) {
        Logger.debug("H5CompressImage", "sendResult result: " + result);
        context.sendBridgeResult(result);
    }

    /* access modifiers changed from: private */
    public static void b(H5BridgeContext context) {
        JSONObject result = new JSONObject();
        result.put((String) "error", (Object) Integer.valueOf(2));
        a(context, result);
    }
}
