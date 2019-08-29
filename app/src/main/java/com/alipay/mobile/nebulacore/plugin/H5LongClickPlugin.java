package com.alipay.mobile.nebulacore.plugin;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.media.MediaScannerConnection;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.webkit.WebResourceResponse;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.beehive.rpc.action.ActionConstant;
import com.alipay.mobile.beehive.service.PhotoParam;
import com.alipay.mobile.bqcscanservice.BQCCameraParam;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.mobile.h5container.api.H5Flag;
import com.alipay.mobile.h5container.api.H5Plugin.CommonEvents;
import com.alipay.mobile.h5container.api.H5SimplePlugin;
import com.alipay.mobile.h5container.service.UcService;
import com.alipay.mobile.nebula.R;
import com.alipay.mobile.nebula.appcenter.api.H5ContentProvider;
import com.alipay.mobile.nebula.provider.H5ListPopDialogProvider;
import com.alipay.mobile.nebula.provider.H5ListPopDialogProvider.OnItemClickListener;
import com.alipay.mobile.nebula.provider.H5ToastProvider;
import com.alipay.mobile.nebula.util.H5FileUtil;
import com.alipay.mobile.nebula.util.H5IOUtils;
import com.alipay.mobile.nebula.util.H5ImageUtil;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5ServiceUtils;
import com.alipay.mobile.nebula.util.H5ThreadType;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebula.webview.APHitTestResult;
import com.alipay.mobile.nebulacore.Nebula;
import com.alipay.mobile.nebulacore.core.H5PageImpl;
import com.alipay.mobile.nebulacore.env.H5Environment;
import com.alipay.mobile.nebulacore.manager.H5ProviderManagerImpl;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class H5LongClickPlugin extends H5SimplePlugin implements OnLongClickListener {
    public static final String SAVE_IMAGE = "saveImage";
    public static final String TAG = "H5LongClickPlugin";
    public static final String privateSaveImage = "privateSaveImage";
    /* access modifiers changed from: private */
    public Activity a;
    /* access modifiers changed from: private */
    public H5PageImpl b;
    private View c;
    private boolean d;

    class ImageSaveRunner implements Runnable {
        String a;
        String b;
        H5BridgeContext c;
        boolean d;
        String e;
        String f;

        public ImageSaveRunner(String url, H5BridgeContext bridgeContext, boolean cusHandleResult, String savePath) {
            this.a = url;
            this.c = bridgeContext;
            this.d = cusHandleResult;
            this.e = savePath;
        }

        public void run() {
            final boolean succeed = a();
            H5Utils.runOnMain(new Runnable() {
                public void run() {
                    String message;
                    if (H5LongClickPlugin.this.a != null) {
                        if (succeed) {
                            message = H5Environment.getResources().getString(R.string.h5_save_image_to, new Object[]{ImageSaveRunner.this.f});
                            if (ImageSaveRunner.this.c != null) {
                                ImageSaveRunner.this.c.sendSuccess();
                            }
                        } else {
                            message = H5Environment.getResources().getString(R.string.h5_save_image_failed);
                            if (ImageSaveRunner.this.c != null) {
                                ImageSaveRunner.this.c.sendBridgeResult("error", "17");
                                return;
                            }
                        }
                        if (!ImageSaveRunner.this.d || BQCCameraParam.VALUE_NO.equalsIgnoreCase("h5_cusHandleResult")) {
                            H5ToastProvider h5ToastProvider = (H5ToastProvider) H5ProviderManagerImpl.getInstance().getProvider(H5ToastProvider.class.getName());
                            if (h5ToastProvider != null) {
                                h5ToastProvider.showToastWithSuper(H5LongClickPlugin.this.a, "", message, 0);
                            } else {
                                H5Environment.showToast(H5LongClickPlugin.this.a, message, 0);
                            }
                        } else {
                            H5Log.d(H5LongClickPlugin.TAG, "cusHandleResult true not show toast");
                        }
                    }
                }
            });
        }

        private InputStream a(String url) {
            String extension = H5FileUtil.getFileExtensionFromUrl(url);
            UcService ucService = H5ServiceUtils.getUcService();
            WebResourceResponse response = null;
            if (ucService != null && H5Flag.ucReady && !"gif".equalsIgnoreCase(extension)) {
                response = ucService.getResponse(url);
            }
            if (response == null || response.getData() == null) {
                if (!(H5LongClickPlugin.this.b == null || H5LongClickPlugin.this.b.getSession() == null)) {
                    H5ContentProvider h5ContentProvider = H5LongClickPlugin.this.b.getSession().getWebProvider();
                    if (h5ContentProvider != null) {
                        WebResourceResponse webResourceResponse = h5ContentProvider.getContent(url);
                        if (webResourceResponse != null) {
                            H5Log.d(H5LongClickPlugin.TAG, "get from H5pkg " + url);
                            return webResourceResponse.getData();
                        }
                    }
                }
                try {
                    H5Log.d(H5LongClickPlugin.TAG, "load response from net");
                    URLConnection conn = new URL(url).openConnection();
                    conn.setConnectTimeout(10000);
                    conn.setReadTimeout(10000);
                    H5Log.d(H5LongClickPlugin.TAG, "load response length " + conn.getContentLength());
                    return conn.getInputStream();
                } catch (Throwable t) {
                    H5Log.e(H5LongClickPlugin.TAG, "load response exception", t);
                    return null;
                }
            } else {
                H5Log.d(H5LongClickPlugin.TAG, "load response from uc cache");
                return response.getData();
            }
        }

        private boolean a() {
            InputStream inputStream = null;
            FileOutputStream fos = null;
            try {
                if (this.a.startsWith("http")) {
                    inputStream = a(this.a);
                } else {
                    inputStream = H5ImageUtil.base64ToInputStream(this.a);
                }
                if (inputStream == null) {
                    H5IOUtils.closeQuietly(inputStream);
                    H5IOUtils.closeQuietly(null);
                    return false;
                }
                String filePathPart = "/DCIM/AMap/";
                if (!TextUtils.equals(this.e, "default")) {
                    filePathPart = this.e;
                }
                String ext = "";
                if (this.a.startsWith("http")) {
                    if (H5Utils.isImage(this.a)) {
                        ext = H5FileUtil.getExtension(this.a);
                    } else {
                        ext = "jpg";
                    }
                }
                if (TextUtils.isEmpty(ext)) {
                    ext = "jpg";
                }
                this.f = Environment.getExternalStorageDirectory() + filePathPart;
                this.b = this.f + System.currentTimeMillis() + "." + ext;
                H5Log.d(H5LongClickPlugin.TAG, "filePath " + this.b);
                if (!H5FileUtil.create(this.b)) {
                    H5Log.w(H5LongClickPlugin.TAG, "failed to create file " + this.b);
                    H5IOUtils.closeQuietly(inputStream);
                    H5IOUtils.closeQuietly(null);
                    return false;
                }
                FileOutputStream fos2 = new FileOutputStream(new File(this.b));
                try {
                    byte[] buffer = H5IOUtils.getBuf(1024);
                    while (true) {
                        int count = inputStream.read(buffer);
                        if (count > 0) {
                            fos2.write(buffer, 0, count);
                        } else {
                            H5IOUtils.returnBuf(buffer);
                            fos2.flush();
                            MediaScannerConnection.scanFile(H5Environment.getContext(), new String[]{this.b}, new String[]{"image/*"}, null);
                            H5IOUtils.closeQuietly(inputStream);
                            H5IOUtils.closeQuietly(fos2);
                            FileOutputStream fileOutputStream = fos2;
                            return true;
                        }
                    }
                } catch (Exception e2) {
                    e = e2;
                    fos = fos2;
                    try {
                        H5Log.e(H5LongClickPlugin.TAG, "save image exception", e);
                        H5IOUtils.closeQuietly(inputStream);
                        H5IOUtils.closeQuietly(fos);
                        return false;
                    } catch (Throwable th) {
                        th = th;
                        H5IOUtils.closeQuietly(inputStream);
                        H5IOUtils.closeQuietly(fos);
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    fos = fos2;
                    H5IOUtils.closeQuietly(inputStream);
                    H5IOUtils.closeQuietly(fos);
                    throw th;
                }
            } catch (Exception e3) {
                e = e3;
                H5Log.e(H5LongClickPlugin.TAG, "save image exception", e);
                H5IOUtils.closeQuietly(inputStream);
                H5IOUtils.closeQuietly(fos);
                return false;
            }
        }
    }

    public H5LongClickPlugin(H5PageImpl h5Page) {
        this.b = h5Page;
        Context context = h5Page.getContext().getContext();
        if (context instanceof Activity) {
            this.a = (Activity) context;
        }
        this.c = h5Page.getWebView().getView();
        this.c.setOnLongClickListener(this);
        this.d = Nebula.enableLongClick(h5Page);
    }

    public void onPrepare(H5EventFilter filter) {
        filter.addAction(SAVE_IMAGE);
        filter.addAction(privateSaveImage);
    }

    public void onRelease() {
        H5Log.d(TAG, "onRelease");
        if (this.c != null) {
            this.c.setOnLongClickListener(null);
            this.c = null;
        }
        this.b = null;
        this.a = null;
    }

    public boolean handleEvent(H5Event event, H5BridgeContext bridgeContext) {
        H5Log.d(TAG, "handleEvent event is " + event.getAction());
        String action = event.getAction();
        if (!SAVE_IMAGE.equals(action) && !privateSaveImage.equals(action)) {
            return false;
        }
        JSONObject param = event.getParam();
        String url = H5Utils.getString(param, (String) "src");
        if (TextUtils.isEmpty(url)) {
            bridgeContext.sendBridgeResult("error", "2");
            return true;
        }
        a(url, H5Utils.getBoolean(param, (String) "showActionSheet", true), bridgeContext, H5Utils.getBoolean(param, (String) "cusHandleResult", false), H5Utils.getString(param, (String) PhotoParam.SAVE_PATH, (String) "default"));
        return true;
    }

    private void a(String url, boolean showDialog, H5BridgeContext bridgeContext, boolean cusHandleResult, String savePath) {
        if (!showDialog) {
            a(url, bridgeContext, cusHandleResult, savePath);
            return;
        }
        String savePhone = H5Environment.getResources().getString(R.string.h5_save_to_phone);
        String[] items = {savePhone};
        H5ListPopDialogProvider h5ListPopDialogProvider = (H5ListPopDialogProvider) H5ProviderManagerImpl.getInstance().getProvider(H5ListPopDialogProvider.class.getName());
        if (h5ListPopDialogProvider != null) {
            ArrayList list = new ArrayList();
            list.add(savePhone);
            h5ListPopDialogProvider.createDialog(this.a, list);
            h5ListPopDialogProvider.showDialog();
            final String str = url;
            final H5BridgeContext h5BridgeContext = bridgeContext;
            final boolean z = cusHandleResult;
            final String str2 = savePath;
            h5ListPopDialogProvider.setOnItemClickListener(new OnItemClickListener() {
                public void onItemClick(int index) {
                    if (index == 0) {
                        H5LongClickPlugin.this.a(str, h5BridgeContext, z, str2);
                    }
                }
            });
            return;
        }
        final String str3 = url;
        final H5BridgeContext h5BridgeContext2 = bridgeContext;
        final boolean z2 = cusHandleResult;
        final String str4 = savePath;
        Dialog dialog = new Builder(this.a).setItems(items, new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    H5LongClickPlugin.this.a(str3, h5BridgeContext2, z2, str4);
                }
                if (dialog != null && H5LongClickPlugin.this.a != null && !H5LongClickPlugin.this.a.isFinishing()) {
                    try {
                        dialog.dismiss();
                    } catch (Throwable th) {
                        H5Log.e((String) H5LongClickPlugin.TAG, (String) "dismiss exception.");
                    }
                }
            }
        }).create();
        dialog.setCanceledOnTouchOutside(true);
        if (this.a != null && !this.a.isFinishing()) {
            dialog.show();
        }
    }

    public boolean onLongClick(View view) {
        boolean z;
        H5Log.d(TAG, "onLongClick");
        if (this.b == null || this.b.getWebView() == null || this.a == null) {
            return false;
        }
        if (!this.d) {
            return true;
        }
        APHitTestResult result = this.b.getWebView().getHitTestResult();
        if (result == null || !(result.getType() == 5 || result.getType() == 8)) {
            z = false;
        } else {
            z = true;
        }
        if (!z || TextUtils.isEmpty(result.getExtra())) {
            return false;
        }
        try {
            String imgUrl = result.getExtra();
            H5Log.d(TAG, "imgUrl:" + imgUrl);
            if (!imgUrl.startsWith("http")) {
                return false;
            }
            JSONObject param = new JSONObject();
            param.put((String) ActionConstant.IMG_URL, (Object) imgUrl);
            this.b.sendEvent(CommonEvents.H5_PAGE_LONG_CLICK, param);
            return true;
        } catch (Exception e) {
            H5Log.e(TAG, "getExtras Exception", e);
            return true;
        }
    }

    /* access modifiers changed from: private */
    public void a(String url, H5BridgeContext bridgeContext, boolean cusHandleResult, String savePath) {
        H5Utils.getExecutor(H5ThreadType.URGENT).execute(new ImageSaveRunner(url, bridgeContext, cusHandleResult, savePath));
    }
}
