package com.alipay.mobile.nebulacore.plugin;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Picture;
import android.graphics.Rect;
import android.media.MediaScannerConnection;
import android.os.Environment;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.view.Display;
import android.view.View;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.h5container.api.H5Bridge;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5CoreNode;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.h5container.api.H5Param;
import com.alipay.mobile.h5container.api.H5Plugin.CommonEvents;
import com.alipay.mobile.h5container.api.H5Session;
import com.alipay.mobile.h5container.api.H5SimplePlugin;
import com.alipay.mobile.nebula.R;
import com.alipay.mobile.nebula.log.H5LogData;
import com.alipay.mobile.nebula.log.H5LogUtil;
import com.alipay.mobile.nebula.provider.H5EnvProvider;
import com.alipay.mobile.nebula.util.H5FileUtil;
import com.alipay.mobile.nebula.util.H5ImageUtil;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5ThreadType;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebula.webview.APWebView;
import com.alipay.mobile.nebulacore.env.H5Environment;
import com.alipay.mobile.nebulacore.util.H5ScreenShotObserver;
import com.alipay.mobile.nebulacore.util.H5ScreenShotObserver.H5ScreenShotListener;
import com.alipay.multimedia.js.image.H5CompressImagePlugin;
import com.autonavi.minimap.ajx3.util.Constants;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class H5SnapshotPlugin extends H5SimplePlugin {
    public static final String TAG = "H5SnapshotPlugin";
    private final Set<H5Page> a = new HashSet();
    /* access modifiers changed from: private */
    public final Set<H5Page> b = new HashSet();
    private String c;
    private H5ScreenShotObserver d;
    /* access modifiers changed from: private */
    public H5Session e;
    /* access modifiers changed from: private */
    public LocalBroadcastManager f;
    /* access modifiers changed from: private */
    public List<BroadcastReceiver> g;

    private class EmbedViewSnapshotBroadcastReceiver extends BroadcastReceiver {
        public void onReceive(Context context, Intent intent) {
        }
    }

    public H5SnapshotPlugin(H5Session session) {
        this.e = session;
        this.f = LocalBroadcastManager.getInstance(H5Environment.getContext());
        this.g = new ArrayList();
    }

    public void onPrepare(H5EventFilter h5EventFilter) {
        h5EventFilter.addAction(H5Param.SNAPSHOT);
        h5EventFilter.addAction("addScreenshotListener");
        h5EventFilter.addAction(CommonEvents.H5_PAGE_RESUME);
        h5EventFilter.addAction(CommonEvents.H5_PAGE_PAUSE);
        h5EventFilter.addAction(CommonEvents.H5_PAGE_CLOSED);
    }

    public void onRelease() {
        if (this.d != null) {
            this.d.release();
            this.d = null;
        }
        if (this.a != null) {
            this.a.clear();
        }
        if (this.b != null) {
            this.b.clear();
        }
        if (this.g != null) {
            for (BroadcastReceiver broadcastReceiver : this.g) {
                this.f.unregisterReceiver(broadcastReceiver);
            }
            this.g.clear();
        }
    }

    public boolean interceptEvent(H5Event event, H5BridgeContext context) {
        String action = event.getAction();
        if (event.getTarget() instanceof H5Page) {
            H5Page page = (H5Page) event.getTarget();
            if (this.a.contains(page)) {
                char c2 = 65535;
                switch (action.hashCode()) {
                    case -1689332344:
                        if (action.equals(CommonEvents.H5_PAGE_CLOSED)) {
                            c2 = 2;
                            break;
                        }
                        break;
                    case -1266238391:
                        if (action.equals(CommonEvents.H5_PAGE_RESUME)) {
                            c2 = 0;
                            break;
                        }
                        break;
                    case -1151189414:
                        if (action.equals(CommonEvents.H5_PAGE_PAUSE)) {
                            c2 = 1;
                            break;
                        }
                        break;
                }
                switch (c2) {
                    case 0:
                        this.b.add(page);
                        break;
                    case 1:
                        this.b.remove(page);
                        break;
                    case 2:
                        this.a.remove(page);
                        this.b.remove(page);
                        break;
                }
            }
        }
        return super.interceptEvent(event, context);
    }

    public boolean handleEvent(H5Event h5Event, H5BridgeContext h5BridgeContext) {
        String action = h5Event.getAction();
        if (TextUtils.equals(H5Param.SNAPSHOT, action)) {
            a(h5Event, h5BridgeContext);
        } else if (!TextUtils.equals("addScreenshotListener", action)) {
            return false;
        } else {
            H5CoreNode node = h5Event.getTarget();
            if (node == null || !(node instanceof H5Page)) {
                h5BridgeContext.sendSuccess();
            } else {
                final H5Page target = (H5Page) node;
                if (this.a.contains(target)) {
                    h5BridgeContext.sendError(-1, (String) "already listening!");
                } else {
                    this.a.add(target);
                    this.b.add(target);
                    if (this.d == null) {
                        this.d = new H5ScreenShotObserver(H5Environment.getContext());
                    }
                    this.d.registerListener(new H5ScreenShotListener() {
                        public void onScreenShot() {
                            H5Page topPage = H5SnapshotPlugin.this.e.getTopPage();
                            if (topPage != null && topPage == target && H5SnapshotPlugin.this.b.contains(target)) {
                                H5Bridge bridge = target.getBridge();
                                if (bridge != null) {
                                    H5Log.d(H5SnapshotPlugin.TAG, "send screenshotbyuser event");
                                    bridge.sendToWeb("screenshotbyuser", null, null);
                                }
                            }
                        }
                    });
                    h5BridgeContext.sendSuccess();
                }
            }
        }
        return true;
    }

    private void a(H5Event event, H5BridgeContext bridgeContext) {
        H5Page h5Page = null;
        if (event.getTarget() instanceof H5Page) {
            h5Page = (H5Page) event.getTarget();
        }
        if (h5Page != null) {
            Activity activity = null;
            if (h5Page.getContext().getContext() instanceof Activity) {
                activity = (Activity) h5Page.getContext().getContext();
            }
            JSONObject param = event.getParam();
            JSONObject result = new JSONObject();
            int maxWidth = H5Utils.getInt(param, (String) "maxWidth", -1);
            int maxHeight = H5Utils.getInt(param, (String) "maxHeight", -1);
            String range = H5Utils.getString(param, (String) "range", (String) "screen");
            boolean saveToGallery = H5Utils.getBoolean(param, (String) "saveToGallery", true);
            String dataType = H5Utils.getString(param, (String) "dataType", (String) Constants.ANIMATOR_NONE);
            String imageFormat = H5Utils.getString(param, (String) "imageFormat", (String) "jpg");
            int quality = H5Utils.getInt(param, (String) "quality", 75);
            Bitmap bitmap = null;
            if (TextUtils.equals(range, "screen")) {
                bitmap = captureScreen(activity);
            } else if (TextUtils.equals(range, "viewport")) {
                bitmap = a(h5Page.getWebView(), activity);
            } else if (TextUtils.equals(range, "document")) {
                bitmap = b(h5Page.getWebView(), activity);
            } else if (TextUtils.equals(range, "embedview")) {
                captureEmbedView(activity, event, bridgeContext, result, maxWidth, maxHeight, saveToGallery, dataType, imageFormat, quality, H5Utils.getBoolean(param, (String) "hasMapTitleBar", false));
                return;
            }
            a(bridgeContext, activity, result, maxWidth, maxHeight, saveToGallery, dataType, imageFormat, quality, bitmap);
        }
    }

    /* access modifiers changed from: private */
    public void a(H5BridgeContext bridgeContext, Activity activity, JSONObject result, int maxWidth, int maxHeight, boolean saveToGallery, String dataType, String imageFormat, int quality, Bitmap bitmap) {
        if (bitmap == null) {
            result.put((String) "success", (Object) Boolean.valueOf(false));
            result.put((String) "error", (Object) "2");
            result.put((String) "errorMessage", (Object) H5Environment.getResources().getString(R.string.h5_getpicfailed));
            bridgeContext.sendBridgeResult(result);
            return;
        }
        if (maxWidth < 0) {
            maxWidth = bitmap.getWidth();
        }
        if (maxHeight < 0) {
            maxHeight = bitmap.getHeight();
        }
        if (!(maxHeight == bitmap.getHeight() && maxWidth == bitmap.getWidth())) {
            bitmap = H5ImageUtil.scaleBitmap(bitmap, maxWidth, maxHeight);
        }
        if (!(bitmap == null || !"jpg".equals(imageFormat) || quality == 100)) {
            bitmap = H5ImageUtil.imageQuality(bitmap, quality);
        }
        if (saveToGallery) {
            int error = a(bitmap, imageFormat);
            if (error != 0) {
                String message = H5Environment.getResources().getString(R.string.h5_save_image_failed);
                if (activity != null && !activity.isFinishing()) {
                    if (error == 1) {
                        message = H5Environment.getResources().getString(R.string.h5_snap_sd_error);
                    } else if (error == 10 || error == 3) {
                        message = H5Environment.getResources().getString(R.string.h5_snap_error);
                    }
                    H5Environment.showToast(activity, message, 0);
                }
                result.put((String) "success", (Object) Boolean.valueOf(false));
                result.put((String) "error", (Object) Integer.valueOf(error));
                result.put((String) "errorMsg", (Object) message);
                bridgeContext.sendBridgeResult(result);
                return;
            } else if (activity != null && !activity.isFinishing()) {
                H5Environment.showToast(activity, H5Environment.getResources().getString(R.string.h5_save_image_to, new Object[]{this.c}), 0);
            }
        }
        if (H5CompressImagePlugin.DATA_TYPE_FILE_URL.equals(dataType)) {
            String destDirName = H5ImageUtil.getImageTempDir(H5Environment.getContext());
            String fileName = System.currentTimeMillis() + "." + imageFormat;
            H5FileUtil.mkdirs(destDirName);
            H5ImageUtil.writeImage(bitmap, imageFormat.equals("jpg") ? CompressFormat.JPEG : CompressFormat.PNG, destDirName + fileName);
            if (!H5FileUtil.exists(destDirName + fileName)) {
                result.put((String) "success", (Object) Boolean.valueOf(false));
                result.put((String) "error", (Object) Integer.valueOf(11));
                result.put((String) "errorMessage", (Object) H5Environment.getResources().getString(R.string.h5_savepicfailed));
            } else {
                result.put((String) "success", (Object) Boolean.valueOf(true));
                result.put((String) H5CompressImagePlugin.DATA_TYPE_FILE_URL, (Object) destDirName + fileName);
            }
            bridgeContext.sendBridgeResult(result);
            if (bitmap != null && !bitmap.isRecycled()) {
                bitmap.recycle();
            }
        } else if (H5CompressImagePlugin.DATA_TYPE_DATA_URL.equals(dataType)) {
            final Bitmap finalBitmap = bitmap;
            final String str = imageFormat;
            final H5BridgeContext h5BridgeContext = bridgeContext;
            H5Utils.getExecutor(H5ThreadType.URGENT).execute(new Runnable() {
                public void run() {
                    String base64 = H5ImageUtil.bitmapToString(finalBitmap, str);
                    JSONObject result = new JSONObject();
                    result.put((String) H5CompressImagePlugin.DATA_TYPE_DATA_URL, (Object) base64);
                    result.put((String) "success", (Object) Boolean.valueOf(true));
                    h5BridgeContext.sendBridgeResult(result);
                    if (finalBitmap != null) {
                        finalBitmap.recycle();
                    }
                }
            });
        } else {
            bridgeContext.sendBridgeResult("success", Boolean.valueOf(true));
            if (bitmap != null && !bitmap.isRecycled()) {
                bitmap.recycle();
            }
        }
    }

    public Bitmap captureScreen(Activity activity) {
        try {
            View view = activity.getWindow().getDecorView();
            view.buildDrawingCache();
            Rect rect = new Rect();
            view.getWindowVisibleDisplayFrame(rect);
            int statusBarHeights = rect.top;
            Display display = activity.getWindowManager().getDefaultDisplay();
            int widths = display.getWidth();
            int heights = display.getHeight();
            view.setDrawingCacheEnabled(true);
            if (heights > view.getDrawingCache().getHeight()) {
                return view.getDrawingCache();
            }
            Bitmap createBitmap = Bitmap.createBitmap(view.getDrawingCache(), 0, statusBarHeights, widths, heights - statusBarHeights);
            view.destroyDrawingCache();
            return createBitmap;
        } catch (Exception e2) {
            H5Log.e((String) TAG, (Throwable) e2);
            H5LogUtil.logNebulaTech(H5LogData.seedId(TAG).param4().add("captureScreenException", e2));
            return null;
        }
    }

    public void captureEmbedView(Activity activity, H5Event h5Event, H5BridgeContext bridgeContext, JSONObject result, int maxWidth, int maxHeight, boolean saveToGallery, String dataType, String imageFormat, int quality, boolean hasMapTitleBar) {
        try {
            final H5Page h5Page = (H5Page) h5Event.getTarget();
            if (h5Page != null) {
                if (hasMapTitleBar || !h5Page.ifContainsEmbedSurfaceView()) {
                    a(bridgeContext, activity, result, maxWidth, maxHeight, saveToGallery, dataType, imageFormat, quality, captureScreen(activity));
                    return;
                }
                final int widths = activity.getWindowManager().getDefaultDisplay().getWidth();
                final int heights = h5Page.getWebView().getView().getMeasuredHeight();
                Bitmap bitmap = Bitmap.createBitmap(widths, heights, Config.ARGB_8888);
                IntentFilter intentFilter = new IntentFilter();
                intentFilter.addAction("embedview.snapshot.complete");
                final Bitmap finalBitmap = bitmap;
                final H5BridgeContext h5BridgeContext = bridgeContext;
                final Activity activity2 = activity;
                final JSONObject jSONObject = result;
                final int i = maxWidth;
                final int i2 = maxHeight;
                final boolean z = saveToGallery;
                final String str = dataType;
                final String str2 = imageFormat;
                final int i3 = quality;
                BroadcastReceiver embedViewSnapshotBroadcastReceiver = new BroadcastReceiver() {
                    public void onReceive(Context context, Intent intent) {
                        H5Log.d(H5SnapshotPlugin.TAG, "embedview.snapshot.complete");
                        if (H5SnapshotPlugin.this.f != null && H5SnapshotPlugin.this.g != null && h5Page != null) {
                            H5SnapshotPlugin.this.f.unregisterReceiver(this);
                            H5SnapshotPlugin.this.g.remove(this);
                            H5Log.d(H5SnapshotPlugin.TAG, "snapResult " + h5Page.getWebView().getCurrentPageSnapshot(new Rect(0, 0, widths, heights), new Rect(0, 0, widths, heights), finalBitmap, false, 0));
                            H5SnapshotPlugin.this.a(h5BridgeContext, activity2, jSONObject, i, i2, z, str, str2, i3, finalBitmap);
                        }
                    }
                };
                this.f.registerReceiver(embedViewSnapshotBroadcastReceiver, intentFilter);
                this.g.add(embedViewSnapshotBroadcastReceiver);
                h5Page.getEmbededViewProvider().triggerPreSnapshot();
            }
        } catch (Exception e2) {
            H5Log.e((String) TAG, (Throwable) e2);
            H5LogUtil.logNebulaTech(H5LogData.seedId(TAG).param4().add("captureScreenException", e2));
        }
    }

    private Bitmap a(APWebView webView, Activity activity) {
        try {
            View view = webView.getView();
            view.setDrawingCacheEnabled(true);
            Bitmap bitmap = view.getDrawingCache().copy(view.getDrawingCache().getConfig(), false);
            view.setDrawingCacheEnabled(false);
            return bitmap;
        } catch (Exception e2) {
            H5Log.e((String) TAG, (Throwable) e2);
            H5LogUtil.logNebulaTech(H5LogData.seedId(TAG).param4().add("captureWebViewException", e2));
            return captureScreen(activity);
        }
    }

    private Bitmap b(APWebView webView, Activity activity) {
        try {
            Picture picture = webView.capturePicture();
            if (picture == null) {
                return null;
            }
            Bitmap bitmap = Bitmap.createBitmap(picture.getWidth(), picture.getHeight(), Config.ARGB_8888);
            picture.draw(new Canvas(bitmap));
            return bitmap;
        } catch (Exception e2) {
            H5Log.e((String) TAG, (Throwable) e2);
            H5LogUtil.logNebulaTech(H5LogData.seedId(TAG).param4().add("captureDocumentException", e2));
            return a(webView, activity);
        }
    }

    private int a(Bitmap bitmap, String format) {
        if (!TextUtils.equals(Environment.getExternalStorageState(), "mounted")) {
            return 1;
        }
        String imgPathPart = null;
        try {
            H5EnvProvider provider = (H5EnvProvider) H5Utils.getProvider(H5EnvProvider.class.getName());
            if (provider != null) {
                imgPathPart = provider.getSnapshotJsapiSavePath();
            }
            if (TextUtils.isEmpty(imgPathPart)) {
                imgPathPart = "/DCIM/AMap/";
            }
            this.c = Environment.getExternalStorageDirectory() + imgPathPart + System.currentTimeMillis() + "." + format;
            H5FileUtil.create(this.c);
            FileOutputStream fos = new FileOutputStream(this.c);
            boolean success = bitmap.compress("jpg".equals(format) ? CompressFormat.JPEG : CompressFormat.PNG, 100, fos);
            fos.close();
            if (!success) {
                return 10;
            }
            MediaScannerConnection.scanFile(H5Environment.getContext(), new String[]{this.c}, new String[]{"image/*"}, null);
            return 0;
        } catch (Exception e2) {
            H5Log.e(TAG, "saveImage exception.", e2);
            return 3;
        }
    }
}
