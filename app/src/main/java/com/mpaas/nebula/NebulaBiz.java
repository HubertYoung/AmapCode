package com.mpaas.nebula;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;
import com.alibaba.fastjson.JSONObject;
import com.alipay.android.phone.mobilecommon.multimedia.api.APMToolService;
import com.alipay.android.phone.mobilecommon.multimedia.api.MultimediaAudioService;
import com.alipay.android.phone.mobilecommon.multimedia.api.MultimediaImageService;
import com.alipay.android.phone.mobilecommon.multimedia.api.MultimediaVideoService;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.APImageDownLoadCallback;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APImageDownloadRsp;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APImageLoadRequest;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.CutScaleType;
import com.alipay.mobile.base.config.ConfigService;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.framework.app.MicroApplication;
import com.alipay.mobile.framework.app.ui.BaseActivity;
import com.alipay.mobile.framework.app.ui.BaseFragmentActivity;
import com.alipay.mobile.framework.service.ext.ExternalService;
import com.alipay.mobile.h5container.api.H5ImageByteListener;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.h5container.service.H5EventHandlerService;
import com.alipay.mobile.h5container.service.H5Service;
import com.alipay.mobile.nebula.process.H5IpcServer;
import com.alipay.mobile.nebula.provider.H5LogProvider;
import com.alipay.mobile.nebula.provider.H5ProviderManager;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5UrlHelper;
import com.alipay.mobile.nebula.util.H5Utils;
import com.mpaas.nebula.adapter.api.H5ExtConfigProvider;
import com.mpaas.nebula.config.H5DefaultConfig;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class NebulaBiz {
    static int BUF_SIZE = 4096;
    public static final boolean DEBUG = a();
    public static final String MULTIMEDIA_IMAGE_BIZ = "NebulaImage";
    public static final String TAG = "NebulaBiz";
    private static ThreadPoolExecutor a;

    public static ThreadPoolExecutor getExecutor() {
        synchronized (NebulaBiz.class) {
            if (a == null) {
                H5Log.d(TAG, "use ThreadPoolExecutor");
                a = new ThreadPoolExecutor(0, 1, 60, TimeUnit.SECONDS, new LinkedBlockingQueue(), new ThreadFactory() {
                    public final Thread newThread(Runnable runnable) {
                        Thread result = new Thread(runnable, "H5 background executor");
                        result.setDaemon(true);
                        return result;
                    }
                });
            }
        }
        return a;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x004b, code lost:
        if (android.text.TextUtils.isEmpty(r0) == false) goto L_0x0016;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:3:0x0014, code lost:
        if (android.text.TextUtils.isEmpty(r0) == false) goto L_0x0016;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String getLocalPathFromId(java.lang.String r4) {
        /*
            java.lang.String r0 = ""
            java.lang.String r1 = "image"
            boolean r1 = r4.endsWith(r1)
            if (r1 == 0) goto L_0x0039
            java.lang.String r1 = "image"
            java.lang.String r0 = matchLocalId(r4, r1)
            boolean r1 = android.text.TextUtils.isEmpty(r0)
            if (r1 != 0) goto L_0x001a
        L_0x0016:
            java.lang.String r4 = decodeToPath(r0)
        L_0x001a:
            java.lang.String r1 = "NebulaBiz"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r3 = "id:"
            r2.<init>(r3)
            java.lang.StringBuilder r2 = r2.append(r0)
            java.lang.String r3 = " filePath:"
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.StringBuilder r2 = r2.append(r4)
            java.lang.String r2 = r2.toString()
            com.alipay.mobile.nebula.util.H5Log.d(r1, r2)
            return r4
        L_0x0039:
            java.lang.String r1 = "video"
            boolean r1 = r4.endsWith(r1)
            if (r1 == 0) goto L_0x004e
            java.lang.String r1 = "video"
            java.lang.String r0 = matchLocalId(r4, r1)
            boolean r1 = android.text.TextUtils.isEmpty(r0)
            if (r1 != 0) goto L_0x001a
            goto L_0x0016
        L_0x004e:
            java.lang.String r1 = "audio"
            boolean r1 = r4.endsWith(r1)
            if (r1 == 0) goto L_0x001a
            java.lang.String r1 = "audio"
            java.lang.String r0 = matchLocalId(r4, r1)
            boolean r1 = android.text.TextUtils.isEmpty(r0)
            if (r1 != 0) goto L_0x001a
            boolean r1 = com.alipay.mobile.nebula.util.H5Utils.isInTinyProcess()
            if (r1 == 0) goto L_0x0016
            java.lang.String r4 = decodePathInTinyProcess(r0)
            goto L_0x001a
        */
        throw new UnsupportedOperationException("Method not decompiled: com.mpaas.nebula.NebulaBiz.getLocalPathFromId(java.lang.String):java.lang.String");
    }

    public static String decodeToPath(String localId) {
        if (H5Utils.isInTinyProcess()) {
            H5EventHandlerService h5EventHandlerService = (H5EventHandlerService) H5Utils.findServiceByInterface(H5EventHandlerService.class.getName());
            if (h5EventHandlerService != null) {
                try {
                    H5IpcServer h5IpcServer = (H5IpcServer) h5EventHandlerService.getIpcProxy(H5IpcServer.class);
                    if (h5IpcServer != null) {
                        return h5IpcServer.decodeToPath(localId);
                    }
                } catch (Throwable throwable) {
                    H5Log.e((String) TAG, throwable);
                }
            }
        } else {
            APMToolService apmToolService = (APMToolService) findServiceByInterface(APMToolService.class.getName());
            if (apmToolService != null) {
                String path = apmToolService.decodeToPath(localId);
                H5Log.d(TAG, "localId :" + localId + " path:" + path);
                return path;
            }
            H5Log.e((String) TAG, (String) "apmToolService ==null ");
        }
        return null;
    }

    public static String decodePathInTinyProcess(String localId) {
        APMToolService apmToolService = (APMToolService) findServiceByInterface(APMToolService.class.getName());
        if (apmToolService != null) {
            String path = apmToolService.decodeToPath(localId);
            H5Log.d(TAG, "localId :" + localId + " path:" + path);
            return path;
        }
        H5Log.e((String) TAG, (String) "apmToolService ==null ");
        return null;
    }

    public static long copy(InputStream from, OutputStream to) {
        byte[] buf = new byte[BUF_SIZE];
        long total = 0;
        while (true) {
            int r = from.read(buf);
            if (r == -1) {
                return total;
            }
            to.write(buf, 0, r);
            total += (long) r;
        }
    }

    public static MicroApplication getMicroApplication(Context context) {
        if (context instanceof BaseActivity) {
            return ((BaseActivity) context).getActivityApplication();
        }
        if (context instanceof BaseFragmentActivity) {
            return ((BaseFragmentActivity) context).getActivityApplication();
        }
        return null;
    }

    public static Resources getResources() {
        return LauncherApplicationAgent.getInstance().getBundleContext().getResourcesByBundle("com-mpaas-nebula-adapter-mpaasnebulaadapter");
    }

    public static Context getContext() {
        return LauncherApplicationAgent.getInstance().getApplicationContext();
    }

    public static final <T extends ExternalService> T getExtServiceByInterface(String name) {
        return LauncherApplicationAgent.getInstance().getMicroApplicationContext().getExtServiceByInterface(name);
    }

    public static final <T> T findServiceByInterface(String name) {
        return LauncherApplicationAgent.getInstance().getMicroApplicationContext().findServiceByInterface(name);
    }

    public static H5ProviderManager getH5ProviderManager() {
        H5Service h5Service = (H5Service) findServiceByInterface(H5Service.class.getName());
        if (h5Service != null) {
            return h5Service.getProviderManager();
        }
        return null;
    }

    public static H5LogProvider getH5LogProvider() {
        H5ProviderManager h5ProviderManager = getH5ProviderManager();
        if (h5ProviderManager != null) {
            return (H5LogProvider) h5ProviderManager.getProvider(H5LogProvider.class.getName());
        }
        return null;
    }

    private static boolean a() {
        try {
            if ((getContext().getApplicationInfo().flags & 2) != 0) {
                return true;
            }
            return false;
        } catch (Throwable t) {
            H5Log.e(TAG, "exception detail", t);
            return false;
        }
    }

    public static Bitmap captureActivity(Activity activity) {
        View root = activity.getWindow().getDecorView().getRootView();
        root.setDrawingCacheEnabled(true);
        root.buildDrawingCache();
        return root.getDrawingCache();
    }

    public static boolean getSwitchVal(String key, String subKey, boolean defVal) {
        if (TextUtils.isEmpty(key) || TextUtils.isEmpty(subKey)) {
            return defVal;
        }
        String value = getConfig(key);
        return !TextUtils.isEmpty(value) ? "YES".equalsIgnoreCase(H5Utils.getString(H5Utils.parseObject(value), subKey)) : defVal;
    }

    public static String getConfigVal(String key, String subKey, String defVal) {
        if (TextUtils.isEmpty(key) || TextUtils.isEmpty(subKey)) {
            return defVal;
        }
        String value = getConfig(key);
        if (TextUtils.isEmpty(value)) {
            return defVal;
        }
        try {
            JSONObject object = H5Utils.parseObject(value);
            if (object == null || !object.containsKey(subKey)) {
                return defVal;
            }
            return H5Utils.getString(object, subKey);
        } catch (Exception e) {
            H5Log.e(TAG, "exception detail", e);
            return defVal;
        }
    }

    public static String getConfig(String configName) {
        String str = null;
        ConfigService configService = (ConfigService) findServiceByInterface(ConfigService.class.getName());
        if (configService == null) {
            H5Log.e((String) TAG, (String) "failed get config service");
            return str;
        }
        try {
            String configValue = configService.getConfig(configName);
            if (!TextUtils.isEmpty(configValue)) {
                return configValue;
            }
            H5ExtConfigProvider extConfigProvider = (H5ExtConfigProvider) H5Utils.getProvider(H5ExtConfigProvider.class.getName());
            if (extConfigProvider != null) {
                String configValue2 = extConfigProvider.getConfig(configName);
                if (!TextUtils.isEmpty(configValue2)) {
                    return configValue2;
                }
            }
            String configValue3 = H5DefaultConfig.mSwitchMap.get(configName);
            H5Log.w(TAG, "failed to get config value for " + configName);
            return configValue3;
        } catch (Exception e) {
            H5Log.e(TAG, "getConfig exception", e);
            return str;
        }
    }

    public static void startActivity(Context h5Context, Intent intent) {
        Context context;
        if (intent == null) {
            H5Log.w(TAG, "invalid event parameter");
            return;
        }
        MicroApplication microApp = getMicroApplication(h5Context);
        if (microApp != null) {
            try {
                LauncherApplicationAgent.getInstance().getMicroApplicationContext().startActivity(microApp, intent);
            } catch (Exception e) {
                H5Log.e(TAG, "startActivity exception", e);
            }
        } else {
            if (h5Context != null) {
                context = h5Context;
            } else {
                context = getContext();
            }
            if (!(context instanceof Activity)) {
                intent.setFlags(268435456);
            }
            context.startActivity(intent);
        }
    }

    public static boolean isSchemeFile(String url) {
        Uri uri = H5UrlHelper.parseUrl(url);
        if (uri != null) {
            return TextUtils.equals(uri.getScheme(), "file");
        }
        return false;
    }

    public static void showToast(String text, int duration, H5Page h5Page) {
        if (h5Page != null && h5Page.getContext() != null && h5Page.getContext().getContext() != null) {
            Toast.makeText(h5Page.getContext().getContext(), text, 0).show();
        }
    }

    public static void startExtActivity(Intent intent) {
        if (intent == null) {
            H5Log.w(TAG, "invalid event parameter");
            return;
        }
        Context context = LauncherApplicationAgent.getInstance().getApplicationContext();
        intent.setFlags(268435456);
        context.startActivity(intent);
    }

    public static String matchLocalId(String url, String type) {
        if (url != null && url.startsWith("https://resource/") && url.endsWith(type)) {
            Uri uri = H5UrlHelper.parseUrl(url);
            if (uri != null && !TextUtils.isEmpty(uri.getPath())) {
                String[] details = uri.getPath().replace("/", "").split("\\.");
                if (details != null && details.length > 1) {
                    String localId = details[0];
                    try {
                        if (!TextUtils.isEmpty(localId)) {
                            return localId;
                        }
                    } catch (Exception e) {
                        H5Log.e((String) TAG, (Throwable) e);
                    }
                }
            }
        }
        return null;
    }

    public static String getVideoPath(String id) {
        if (H5Utils.isInTinyProcess()) {
            H5EventHandlerService h5EventHandlerService = (H5EventHandlerService) H5Utils.findServiceByInterface(H5EventHandlerService.class.getName());
            if (h5EventHandlerService != null) {
                try {
                    H5IpcServer h5IpcServer = (H5IpcServer) h5EventHandlerService.getIpcProxy(H5IpcServer.class);
                    if (h5IpcServer != null) {
                        return h5IpcServer.getVideoPathById(id);
                    }
                } catch (Throwable throwable) {
                    H5Log.e((String) TAG, throwable);
                }
            }
        } else {
            MultimediaVideoService multimediaImageService = (MultimediaVideoService) findServiceByInterface(MultimediaVideoService.class.getName());
            if (multimediaImageService != null) {
                return multimediaImageService.getVideoPathById(id);
            }
        }
        return null;
    }

    public static String getAudioPath(String localId) {
        MultimediaAudioService multimediaAudioService = (MultimediaAudioService) findServiceByInterface(MultimediaAudioService.class.getName());
        if (multimediaAudioService != null) {
            File wavFile = multimediaAudioService.convertToFormat(localId, "wav");
            if (wavFile != null) {
                return wavFile.getAbsolutePath();
            }
        }
        return null;
    }

    public static void getImageData(final String url, final H5ImageByteListener h5ImageListener) {
        if (h5ImageListener != null) {
            APImageLoadRequest apImageLoadRequest = new APImageLoadRequest();
            apImageLoadRequest.setExpiredTime(86400000);
            apImageLoadRequest.withImageDataInCallback = true;
            apImageLoadRequest.path = url;
            apImageLoadRequest.cutScaleType = CutScaleType.NONE;
            apImageLoadRequest.callback = new APImageDownLoadCallback() {
                public final void onSucc(APImageDownloadRsp apImageDownloadRsp) {
                    if (apImageDownloadRsp != null) {
                        h5ImageListener.onImageByte(apImageDownloadRsp.imageData);
                    } else {
                        h5ImageListener.onImageByte(null);
                    }
                }

                public final void onProcess(String s, int i) {
                }

                public final void onError(APImageDownloadRsp apImageDownloadRsp, Exception e) {
                    H5LogProvider logProvider = (H5LogProvider) H5Utils.getProvider(H5LogProvider.class.getName());
                    if (!(logProvider == null || apImageDownloadRsp == null || apImageDownloadRsp.getRetmsg() == null)) {
                        logProvider.log("H5loadImageException", url, apImageDownloadRsp.getRetmsg().getMsg(), apImageDownloadRsp.getRetmsg().getCode(), null, null);
                    }
                    h5ImageListener.onImageByte(null);
                }
            };
            ((MultimediaImageService) findServiceByInterface(MultimediaImageService.class.getName())).loadImage(apImageLoadRequest, (String) MULTIMEDIA_IMAGE_BIZ);
        }
    }
}
