package com.alipay.mobile.tinyappcommon.h5plugin;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.webkit.WebResourceResponse;
import com.alibaba.fastjson.JSONObject;
import com.alipay.android.phone.mobilecommon.multimedia.api.MultimediaImageProcessor;
import com.alipay.android.phone.mobilecommon.multimedia.api.data.image.processor.APImageInfo;
import com.alipay.mobile.beehive.capture.service.CaptureParam;
import com.alipay.mobile.beehive.plugins.Constant;
import com.alipay.mobile.common.transport.h5.H5HttpUrlRequest;
import com.alipay.mobile.common.transport.h5.H5HttpUrlResponse;
import com.alipay.mobile.common.transport.h5.H5NetworkManager;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.h5container.api.H5Session;
import com.alipay.mobile.h5container.api.H5SimplePlugin;
import com.alipay.mobile.nebula.appcenter.api.H5ContentProvider;
import com.alipay.mobile.nebula.appcenter.api.H5ContentProvider.ResponseListen;
import com.alipay.mobile.nebula.filecache.FileCache;
import com.alipay.mobile.nebula.resourcehandler.H5ResourceHandlerUtil;
import com.alipay.mobile.nebula.util.H5CookieUtil;
import com.alipay.mobile.nebula.util.H5FileUtil;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5ThreadType;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.tinyappcommon.TinyappUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import org.apache.http.Header;

public class H5ImageInfoPlugin extends H5SimplePlugin {
    private static final String GET_IMAGE_INFO = "getImageInfo";
    private static final String ORIENTATION_DOWN = "down";
    private static final String ORIENTATION_DOWN_MIRRORED = "down-mirrored";
    private static final String ORIENTATION_LEFT = "left";
    private static final String ORIENTATION_LEFT_MIRRORED = "left-mirrored";
    private static final String ORIENTATION_RIGHT = "right";
    private static final String ORIENTATION_RIGHT_MIRRORED = "right-mirrored";
    private static final String ORIENTATION_UP = "up";
    private static final String ORIENTATION_UP_MIRRORED = "up-mirrored";
    /* access modifiers changed from: private */
    public static final String TAG = H5ImageInfoPlugin.class.getSimpleName();
    private FileCache cache;
    /* access modifiers changed from: private */
    public H5Page h5Page;
    private MultimediaImageProcessor mImageProcessor;

    public void onPrepare(H5EventFilter filter) {
        super.onPrepare(filter);
        filter.addAction(GET_IMAGE_INFO);
        this.mImageProcessor = (MultimediaImageProcessor) LauncherApplicationAgent.getInstance().getMicroApplicationContext().getExtServiceByInterface(MultimediaImageProcessor.class.getName());
    }

    public boolean handleEvent(H5Event event, H5BridgeContext context) {
        if (event.getTarget() instanceof H5Page) {
            this.h5Page = (H5Page) event.getTarget();
        }
        if (GET_IMAGE_INFO.equals(event.getAction())) {
            getImageInfo(event, context);
        }
        return true;
    }

    private String getAbsoluteUrl(String oriUrl, Bundle startParams) {
        String entryUrl = H5Utils.getString(startParams, (String) "url");
        if (!TextUtils.isEmpty(entryUrl)) {
            return H5Utils.getAbsoluteUrlV2(entryUrl, oriUrl, null);
        }
        return null;
    }

    private void getImageInfoFromPkg(String url, ResponseListen listener) {
        H5Log.d(TAG, "getImageInfo...url=" + url);
        if (this.h5Page != null) {
            H5Session h5Session = this.h5Page.getSession();
            if (h5Session != null) {
                H5ContentProvider h5ContentProvider = h5Session.getWebProvider();
                if (h5ContentProvider != null) {
                    h5ContentProvider.getContent(url, listener);
                } else {
                    listener.onGetResponse(null);
                }
            } else {
                listener.onGetResponse(null);
            }
        } else {
            listener.onGetResponse(null);
        }
    }

    private void getImageInfoFromNet(final String url, final H5BridgeContext context) {
        if (!TinyappUtils.checkSuffixSupported(url)) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put((String) "error", (Object) Integer.valueOf(22));
            jsonObject.put((String) "errorMessage", (Object) "不支持的地址后缀名");
            context.sendBridgeResult(jsonObject);
            return;
        }
        H5Utils.getExecutor(H5ThreadType.IO).execute(new Runnable() {
            public void run() {
                String type = "";
                String extension = "";
                try {
                    H5NetworkManager h5NetworkManager = new H5NetworkManager(H5Utils.getContext());
                    H5HttpUrlRequest h5HttpUrlRequest = new H5HttpUrlRequest(url);
                    r0 = "GET";
                    h5HttpUrlRequest.setRequestMethod("GET");
                    h5HttpUrlRequest.setReqData(null);
                    h5HttpUrlRequest.setTimeout(60000);
                    h5HttpUrlRequest.linkType = 2;
                    String cookieStr = H5CookieUtil.getCookie(H5ImageInfoPlugin.this.h5Page != null ? H5ImageInfoPlugin.this.h5Page.getParams() : null, url);
                    if (!TextUtils.isEmpty(cookieStr)) {
                        h5HttpUrlRequest.addHeader("Cookie", cookieStr);
                        H5Log.d(H5ImageInfoPlugin.TAG, "add cookie:" + cookieStr + " , for h5HttpUrlRequest");
                    }
                    H5HttpUrlResponse httpUrlResponse = (H5HttpUrlResponse) h5NetworkManager.enqueue(h5HttpUrlRequest).get();
                    if (httpUrlResponse == null || httpUrlResponse.getHeader() == null) {
                        H5Log.e(H5ImageInfoPlugin.TAG, (String) "getImageInfoFromNet response error");
                        context.sendError(21, (String) "下载图片信息失败");
                        return;
                    }
                    Header[] allHeaders = httpUrlResponse.getHeader().getAllHeaders();
                    int length = allHeaders.length;
                    for (int i = 0; i < length; i++) {
                        Header header = allHeaders[i];
                        String headerName = header.getName();
                        if (headerName != null) {
                            String headerValue = header.getValue();
                            H5Log.d(H5ImageInfoPlugin.TAG, "name:" + headerName + " - value:" + headerValue);
                            if (headerName.equalsIgnoreCase("Content-Type")) {
                                String contentType = headerValue;
                                if (!TextUtils.isEmpty(headerValue)) {
                                    type = TinyappUtils.getTypeFromMimeType(contentType);
                                    extension = H5FileUtil.getExtensionFromMimeType(contentType);
                                }
                            }
                            if (headerName.equalsIgnoreCase("set-cookie")) {
                                H5CookieUtil.setCookie(H5ImageInfoPlugin.this.h5Page != null ? H5ImageInfoPlugin.this.h5Page.getParams() : null, url, headerValue);
                                H5Log.d(H5ImageInfoPlugin.TAG, "insert cookie:" + headerValue + " , for " + url);
                            }
                        }
                    }
                    String tempPath = H5ImageInfoPlugin.this.getFileCache().getTempPath(H5Utils.getContext(), url, extension);
                    H5FileUtil.create(tempPath, true);
                    File file = new File(tempPath);
                    FileOutputStream fileOutputStream = new FileOutputStream(file);
                    InputStream inputStream = httpUrlResponse.getInputStream();
                    byte[] b = new byte[1024];
                    while (true) {
                        int j = inputStream.read(b);
                        if (j != -1) {
                            fileOutputStream.write(b, 0, j);
                        } else {
                            fileOutputStream.flush();
                            fileOutputStream.close();
                            inputStream.close();
                            String apFilePath = H5ResourceHandlerUtil.localIdToUrl(TinyappUtils.encodeToLocalId(tempPath), type);
                            FileInputStream fileInputStream = new FileInputStream(file);
                            Bitmap bitmap = BitmapFactory.decodeStream(fileInputStream);
                            fileInputStream.close();
                            JSONObject jsonObject = new JSONObject();
                            jsonObject.put((String) "width", (Object) Integer.valueOf(bitmap.getWidth()));
                            jsonObject.put((String) "height", (Object) Integer.valueOf(bitmap.getHeight()));
                            jsonObject.put((String) "path", (Object) apFilePath);
                            H5ImageInfoPlugin.this.parseTypeAndOrientation(tempPath, jsonObject);
                            context.sendBridgeResult(jsonObject);
                            return;
                        }
                    }
                } catch (Exception e) {
                    H5Log.e(H5ImageInfoPlugin.TAG, "getImageInfoFromNet...e=" + e);
                    context.sendError(18, (String) "获取图片信息失败");
                }
            }
        });
    }

    private void getImageInfo(H5Event event, final H5BridgeContext context) {
        final String path = H5Utils.getString(event.getParam(), (String) "src");
        if (TextUtils.isEmpty(path)) {
            JSONObject result = new JSONObject();
            result.put((String) "error", (Object) Integer.valueOf(12));
            result.put((String) "errorMessage", (Object) "路径不能为空");
            context.sendBridgeResult(result);
            return;
        }
        H5Log.d(TAG, "getImageInfo...path=" + path);
        try {
            if (path.startsWith("https://resource/") && path.endsWith(Constant.AL_IMAGE_SUFFIX)) {
                String localPath = TinyappUtils.transferApPathToLocalPath(path);
                Bitmap bitmap = BitmapFactory.decodeFile(localPath);
                JSONObject jsonObject = new JSONObject();
                jsonObject.put((String) "width", (Object) Integer.valueOf(bitmap.getWidth()));
                jsonObject.put((String) "height", (Object) Integer.valueOf(bitmap.getHeight()));
                jsonObject.put((String) "path", (Object) path);
                parseTypeAndOrientation(localPath, jsonObject);
                context.sendBridgeResult(jsonObject);
            } else if (path.startsWith("http")) {
                getImageInfoFromNet(path, context);
            } else {
                String realPath = getAbsoluteUrl(path, this.h5Page.getParams());
                H5Log.d(TAG, "getImageInfo realPath " + realPath);
                getImageInfoFromPkg(realPath, new ResponseListen() {
                    public void onGetResponse(WebResourceResponse webResourceResponse) {
                        if (webResourceResponse == null) {
                            try {
                                JSONObject result = new JSONObject();
                                result.put((String) "error", (Object) Integer.valueOf(18));
                                result.put((String) "errorMessage", (Object) "获取图片信息失败");
                                context.sendBridgeResult(result);
                            } catch (Throwable e) {
                                H5Log.e(H5ImageInfoPlugin.TAG, "onGetResponse...e=" + e);
                                JSONObject result2 = new JSONObject();
                                result2.put((String) "error", (Object) Integer.valueOf(18));
                                result2.put((String) "errorMessage", (Object) "获取图片信息失败");
                                context.sendBridgeResult(result2);
                            }
                        } else {
                            Bitmap bitmap = BitmapFactory.decodeStream(webResourceResponse.getData());
                            JSONObject jsonObject = new JSONObject();
                            jsonObject.put((String) "width", (Object) Integer.valueOf(bitmap.getWidth()));
                            jsonObject.put((String) "height", (Object) Integer.valueOf(bitmap.getHeight()));
                            jsonObject.put((String) "path", (Object) H5ImageInfoPlugin.this.formatRelativePath(path));
                            context.sendBridgeResult(jsonObject);
                        }
                    }
                });
            }
        } catch (Throwable e) {
            H5Log.e(TAG, "getImageInfo...e=" + e);
            JSONObject result2 = new JSONObject();
            result2.put((String) "error", (Object) Integer.valueOf(18));
            result2.put((String) "errorMessage", (Object) "获取图片信息失败");
            context.sendBridgeResult(result2);
        }
    }

    /* access modifiers changed from: private */
    public void parseTypeAndOrientation(String localPath, JSONObject jsonObject) {
        if (this.mImageProcessor != null) {
            String orientationStr = ORIENTATION_UP;
            try {
                APImageInfo imageInfo = this.mImageProcessor.parseImageInfo(localPath);
                String type = "unknown";
                if (imageInfo != null) {
                    type = imageInfo.type;
                    if (type.indexOf(".") == 0) {
                        type = type.substring(1);
                    }
                    switch (imageInfo.orientation) {
                        case 1:
                            orientationStr = ORIENTATION_UP;
                            break;
                        case 2:
                            orientationStr = ORIENTATION_UP_MIRRORED;
                            break;
                        case 3:
                            orientationStr = ORIENTATION_DOWN;
                            break;
                        case 4:
                            orientationStr = ORIENTATION_DOWN_MIRRORED;
                            break;
                        case 5:
                            orientationStr = ORIENTATION_LEFT_MIRRORED;
                            break;
                        case 6:
                            orientationStr = "right";
                            break;
                        case 7:
                            orientationStr = ORIENTATION_RIGHT_MIRRORED;
                            break;
                        case 8:
                            orientationStr = "left";
                            break;
                    }
                }
                jsonObject.put((String) CaptureParam.ORIENTATION_MODE, (Object) orientationStr);
                jsonObject.put((String) "type", (Object) type);
            } catch (Exception e) {
                H5Log.w(TAG, "parseTypeAndOrientation exception:" + e.getMessage());
            }
        }
    }

    /* access modifiers changed from: private */
    public String formatRelativePath(String originalPath) {
        if (TextUtils.isEmpty(originalPath)) {
            return null;
        }
        if (originalPath.startsWith("./")) {
            return originalPath.substring(2);
        }
        if (originalPath.startsWith("/")) {
            return originalPath.substring(1);
        }
        return originalPath;
    }

    /* access modifiers changed from: private */
    public FileCache getFileCache() {
        if (this.cache == null) {
            String appId = H5Utils.getString(this.h5Page.getParams(), (String) "appId");
            if (TextUtils.isEmpty(appId)) {
                appId = "20000067";
            }
            this.cache = new FileCache(this.h5Page.getContext().getContext(), appId);
        }
        return this.cache;
    }
}
