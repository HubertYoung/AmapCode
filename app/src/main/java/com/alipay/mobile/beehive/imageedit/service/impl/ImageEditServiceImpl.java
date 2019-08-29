package com.alipay.mobile.beehive.imageedit.service.impl;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import com.alipay.mobile.beehive.imageedit.activity.DoodleActivity;
import com.alipay.mobile.beehive.imageedit.constant.Constants;
import com.alipay.mobile.beehive.imageedit.modle.ImageInfo;
import com.alipay.mobile.beehive.imageedit.service.ImageEditListener;
import com.alipay.mobile.beehive.imageedit.service.ImageEditService;
import com.alipay.mobile.beehive.imageedit.service.InImageEditListener;
import com.alipay.mobile.beehive.imageedit.utils.CommonUtil;
import com.alipay.mobile.beehive.imageedit.utils.ImageEditLogger;
import com.alipay.mobile.beehive.util.MultiThreadUtil;
import com.alipay.mobile.beehive.util.SpmUtils;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.framework.app.MicroApplication;
import com.jiuyan.inimage.InSDKConfig;
import com.jiuyan.inimage.InSDKEntrance;
import com.jiuyan.inimage.callback.IEditCallback;
import com.jiuyan.inimage.callback.IPhotoSaveDelegate;
import com.jiuyan.inimage.callback.IPhotoSaveDelegate.IPhotoSaveCallback;
import java.io.File;
import java.lang.ref.WeakReference;
import java.util.Map;

public class ImageEditServiceImpl extends ImageEditService {
    private static final String TAG = "ImageEditServiceImpl";
    private static a currentSession;

    static class a {
        private WeakReference<ImageEditListener> a;
        private String b;

        public a(ImageEditListener listener, String oriImagePath) {
            this.a = new WeakReference<>(listener);
            this.b = oriImagePath;
        }

        public final ImageEditListener a() {
            return (ImageEditListener) this.a.get();
        }

        public final void a(boolean isCanceled, ImageInfo mediaInfo) {
            ImageEditListener listener = (ImageEditListener) this.a.get();
            if (listener != null) {
                ImageEditLogger.info(ImageEditServiceImpl.TAG, "Is canceled : " + isCanceled);
                listener.onImageEdit(isCanceled, this.b, mediaInfo);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        ImageEditLogger.debug(TAG, "ImageEditServiceImpl onCreate.");
    }

    /* access modifiers changed from: protected */
    public void onDestroy(Bundle bundle) {
        ImageEditLogger.debug(TAG, "ImageEditServiceImpl onDestroy.");
    }

    public static synchronized a getCurrentSession() {
        a aVar;
        synchronized (ImageEditServiceImpl.class) {
            aVar = currentSession;
        }
        return aVar;
    }

    public static synchronized void setUpCurrentSession(ImageEditListener listener, String oriPath) {
        synchronized (ImageEditServiceImpl.class) {
            if (currentSession != null) {
                if (currentSession.a() == listener) {
                    ImageEditLogger.warn((String) TAG, (String) "ImageEditListener is same,do nothing.");
                } else {
                    ImageEditLogger.warn((String) TAG, (String) "ImageEdit job concurrent,cancel the old one!");
                    currentSession.a(true, null);
                }
            }
            ImageEditLogger.warn((String) TAG, (String) "Update ImageEditSession");
            currentSession = new a(listener, oriPath);
        }
    }

    public static synchronized void notifyAction(boolean isCancel, ImageInfo imageInfo) {
        synchronized (ImageEditServiceImpl.class) {
            ImageEditLogger.warn((String) TAG, (String) "notifyAction called");
            if (currentSession != null) {
                currentSession.a(isCancel, imageInfo);
                ImageEditLogger.warn((String) TAG, (String) "Clear session");
                currentSession = null;
            } else {
                ImageEditLogger.warn((String) TAG, (String) "notifyAction called when ImageEditSession is Null!");
            }
        }
    }

    private boolean checkArgs(String businessId, ImageEditListener listener, String pathOrId) {
        return !TextUtils.isEmpty(businessId) && listener != null && !TextUtils.isEmpty(pathOrId);
    }

    public void editImage(MicroApplication app, ImageEditListener listener, String pathOrId, String businessId, Bundle params) {
        if (checkArgs(businessId, listener, pathOrId)) {
            setUpCurrentSession(listener, pathOrId);
            if (params == null) {
                params = new Bundle();
            }
            params.putString("imagePath", pathOrId);
            params.putString("businessId", businessId);
            Intent intent = new Intent(LauncherApplicationAgent.getInstance().getApplicationContext(), DoodleActivity.class);
            SpmUtils.addSourceAndBizTypeByTop(app, (Activity) getMicroApplicationContext().getTopActivity().get(), params);
            intent.putExtras(params);
            LauncherApplicationAgent.getInstance().getMicroApplicationContext().startActivity(app, intent);
            return;
        }
        ImageEditLogger.debug(TAG, "ImageEditService action called,but required params is missing!");
    }

    public void editImageUseIn(Map<String, Object> params, InImageEditListener listener) {
        editImageUseIn(null, params, listener);
    }

    public void editImageUseIn(Context context, Map<String, Object> params, InImageEditListener listener) {
        if (params == null) {
            try {
                LoggerFactory.getTraceLogger().info(TAG, "参数异常: params为空");
            } catch (Exception ex) {
                LoggerFactory.getTraceLogger().warn((String) TAG, (Throwable) ex);
            }
        } else {
            Bitmap bitmap = null;
            if (params.containsKey(Constants.KEY_BITMAP) && (params.get(Constants.KEY_BITMAP) instanceof Bitmap)) {
                bitmap = (Bitmap) params.get(Constants.KEY_BITMAP);
            }
            if (bitmap != null) {
                callInEdit(context, "", bitmap, params, listener);
            } else if (!params.containsKey("path") || !(params.get("path") instanceof String)) {
                LoggerFactory.getTraceLogger().info(TAG, "path参数非法");
            } else {
                final String path = (String) params.get("path");
                if (!TextUtils.isEmpty(path)) {
                    final Context context2 = context;
                    final Map<String, Object> map = params;
                    final InImageEditListener inImageEditListener = listener;
                    MultiThreadUtil.runOnBackgroundThread(new Runnable() {
                        public final void run() {
                            final Bitmap bmp = CommonUtil.decodePath(path);
                            if (bmp == null) {
                                LoggerFactory.getTraceLogger().info(ImageEditServiceImpl.TAG, "参数异常: bitmap参数或path不能都为空");
                            } else {
                                MultiThreadUtil.runOnUiThread(new Runnable() {
                                    public final void run() {
                                        ImageEditServiceImpl.this.callInEdit(context2, path, bmp, map, inImageEditListener);
                                    }
                                });
                            }
                        }
                    });
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public void callInEdit(Context context, final String inPath, Bitmap bitmap, Map<String, Object> params, InImageEditListener listener) {
        try {
            final InImageEditListener inImageEditListener = listener;
            IEditCallback editCallback = new IEditCallback() {
                public final void onEditCancel() {
                    LoggerFactory.getTraceLogger().info(ImageEditServiceImpl.TAG, "inimage onEditCancel");
                    if (inImageEditListener != null) {
                        inImageEditListener.onResult(false, "", null, null);
                    }
                    ImageEditServiceImpl.this.afterInEditFinish();
                }

                public final void onEditNothing(Bitmap bmp) {
                    LoggerFactory.getTraceLogger().info(ImageEditServiceImpl.TAG, "inimage onEditNothing");
                    if (inImageEditListener != null) {
                        inImageEditListener.onResult(true, ImageEditServiceImpl.this.addFileProtocolIfNot(inPath), bmp, null);
                    }
                    ImageEditServiceImpl.this.afterInEditFinish();
                }

                public final void onEditDone(boolean saveSuccess, Bitmap bitmap, String outPath) {
                    LoggerFactory.getTraceLogger().info(ImageEditServiceImpl.TAG, "inimage onEditDone: " + outPath);
                    if (inImageEditListener != null) {
                        inImageEditListener.onResult(true, ImageEditServiceImpl.this.addFileProtocolIfNot(outPath), bitmap, null);
                    }
                    ImageEditServiceImpl.this.afterInEditFinish();
                }
            };
            InSDKConfig config = InSDKConfig.build().setFlag(0).bitmap(bitmap).editCallback(editCallback).photoSaveDelegate(new IPhotoSaveDelegate() {
                public final void savePhoto(Bitmap bitmap, IPhotoSaveCallback iPhotoSaveCallback) {
                    String outPath = ImageEditServiceImpl.this.saveToPath(bitmap);
                    boolean v = !TextUtils.isEmpty(outPath);
                    if (v) {
                        Intent scanIntent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
                        scanIntent.setData(Uri.fromFile(new File(outPath)));
                        LauncherApplicationAgent.getInstance().getApplicationContext().sendBroadcast(scanIntent);
                    } else {
                        LoggerFactory.getTraceLogger().info(ImageEditServiceImpl.TAG, "callInEdit save photo fail!");
                    }
                    iPhotoSaveCallback.onPhotoSaveResult(v, outPath);
                }
            });
            config.activitySecure(readParamBool(params, ImageEditService.IN_KEY_DISABLE_SCREEN_SHOT, false).booleanValue());
            String[] editTypes = (String[]) params.get("editTypes");
            if (editTypes == null || editTypes.length <= 0 || stringArrayFind(editTypes, "full") >= 0) {
                config.setFlag(-1);
            } else {
                if (stringArrayFind(editTypes, ImageEditService.IN_EDIT_TYPE_CROP) >= 0) {
                    config.addFlag(1);
                }
                if (stringArrayFind(editTypes, ImageEditService.IN_EDIT_TYPE_ROTATE) >= 0) {
                    config.addFlag(2);
                }
                if (stringArrayFind(editTypes, ImageEditService.IN_EDIT_TYPE_MAGIC) >= 0) {
                    config.addFlag(4);
                }
                if (stringArrayFind(editTypes, ImageEditService.IN_EDIT_TYPE_PASTER) >= 0) {
                    config.addFlag(8);
                }
                if (stringArrayFind(editTypes, "text") >= 0) {
                    config.addFlag(16);
                }
            }
            String textLeft = readParamString(params, ImageEditService.IN_KEY_CANCEL_TEXT);
            if (!TextUtils.isEmpty(textLeft)) {
                config.customText(InSDKConfig.KEY_CONFIG_TEXT_LEFT, textLeft);
            }
            String funcText = readParamString(params, ImageEditService.IN_KEY_WATERMARK_TEXT);
            if (!TextUtils.isEmpty(funcText)) {
                config.customText(InSDKConfig.KEY_CONFIG_TEXT_FUNC_TEXT, funcText);
            }
            if (context != null) {
                InSDKEntrance.startEdit(context, config);
            } else {
                InSDKEntrance.startEdit(LauncherApplicationAgent.getInstance().getApplicationContext(), config);
            }
        } catch (Exception ex) {
            LoggerFactory.getTraceLogger().warn((String) TAG, (Throwable) ex);
        }
    }

    public int stringArrayFind(String[] targetEditType, String type) {
        for (int i = 0; i < targetEditType.length; i++) {
            if (TextUtils.equals(type, targetEditType[i])) {
                return i;
            }
        }
        return -1;
    }

    private String readParamString(Map<String, Object> params, String key) {
        if (!params.containsKey(key) || !(params.get(key) instanceof String)) {
            return null;
        }
        return (String) params.get(key);
    }

    private Boolean readParamBool(Map<String, Object> params, String key, boolean defaultV) {
        if (!params.containsKey(key) || !(params.get(key) instanceof Boolean)) {
            return Boolean.valueOf(defaultV);
        }
        return (Boolean) params.get(key);
    }

    /* access modifiers changed from: private */
    public void afterInEditFinish() {
        try {
            InSDKEntrance.release();
        } catch (Exception ex) {
            LoggerFactory.getTraceLogger().warn((String) TAG, (Throwable) ex);
        }
    }

    /* access modifiers changed from: private */
    public String addFileProtocolIfNot(String path) {
        if (TextUtils.isEmpty(path) || path.startsWith("file://")) {
            return path;
        }
        return "file://" + path;
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x0090 A[SYNTHETIC, Splitter:B:30:0x0090] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.String saveToPath(android.graphics.Bitmap r11) {
        /*
            r10 = this;
            r3 = 0
            java.lang.String r6 = android.os.Environment.DIRECTORY_DCIM     // Catch:{ Exception -> 0x0072 }
            java.io.File r6 = android.os.Environment.getExternalStoragePublicDirectory(r6)     // Catch:{ Exception -> 0x0072 }
            java.lang.String r0 = r6.getAbsolutePath()     // Catch:{ Exception -> 0x0072 }
            java.io.File r2 = new java.io.File     // Catch:{ Exception -> 0x0072 }
            r2.<init>(r0)     // Catch:{ Exception -> 0x0072 }
            boolean r6 = r2.exists()     // Catch:{ Exception -> 0x0072 }
            if (r6 != 0) goto L_0x0019
            r2.mkdirs()     // Catch:{ Exception -> 0x0072 }
        L_0x0019:
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0072 }
            r6.<init>()     // Catch:{ Exception -> 0x0072 }
            java.lang.StringBuilder r6 = r6.append(r0)     // Catch:{ Exception -> 0x0072 }
            java.lang.String r7 = java.io.File.separator     // Catch:{ Exception -> 0x0072 }
            java.lang.StringBuilder r6 = r6.append(r7)     // Catch:{ Exception -> 0x0072 }
            java.lang.String r7 = "inedit_"
            java.lang.StringBuilder r6 = r6.append(r7)     // Catch:{ Exception -> 0x0072 }
            long r8 = java.lang.System.currentTimeMillis()     // Catch:{ Exception -> 0x0072 }
            java.lang.StringBuilder r6 = r6.append(r8)     // Catch:{ Exception -> 0x0072 }
            java.lang.String r7 = ".jpg"
            java.lang.StringBuilder r6 = r6.append(r7)     // Catch:{ Exception -> 0x0072 }
            java.lang.String r5 = r6.toString()     // Catch:{ Exception -> 0x0072 }
            java.io.FileOutputStream r4 = new java.io.FileOutputStream     // Catch:{ Exception -> 0x0072 }
            r4.<init>(r5)     // Catch:{ Exception -> 0x0072 }
            android.graphics.Bitmap$CompressFormat r6 = android.graphics.Bitmap.CompressFormat.JPEG     // Catch:{ Exception -> 0x00a2, all -> 0x009f }
            r7 = 95
            boolean r6 = r11.compress(r6, r7, r4)     // Catch:{ Exception -> 0x00a2, all -> 0x009f }
            if (r6 == 0) goto L_0x005f
            r4.close()     // Catch:{ IOException -> 0x0054 }
        L_0x0052:
            r3 = r4
        L_0x0053:
            return r5
        L_0x0054:
            r1 = move-exception
            com.alipay.mobile.common.logging.api.trace.TraceLogger r6 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()
            java.lang.String r7 = "ImageEditServiceImpl"
            r6.warn(r7, r1)
            goto L_0x0052
        L_0x005f:
            r4.close()     // Catch:{ IOException -> 0x0066 }
            r3 = r4
        L_0x0063:
            java.lang.String r5 = ""
            goto L_0x0053
        L_0x0066:
            r1 = move-exception
            com.alipay.mobile.common.logging.api.trace.TraceLogger r6 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()
            java.lang.String r7 = "ImageEditServiceImpl"
            r6.warn(r7, r1)
            r3 = r4
            goto L_0x0063
        L_0x0072:
            r1 = move-exception
        L_0x0073:
            com.alipay.mobile.common.logging.api.trace.TraceLogger r6 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()     // Catch:{ all -> 0x008d }
            java.lang.String r7 = "ImageEditServiceImpl"
            r6.warn(r7, r1)     // Catch:{ all -> 0x008d }
            if (r3 == 0) goto L_0x0063
            r3.close()     // Catch:{ IOException -> 0x0082 }
            goto L_0x0063
        L_0x0082:
            r1 = move-exception
            com.alipay.mobile.common.logging.api.trace.TraceLogger r6 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()
            java.lang.String r7 = "ImageEditServiceImpl"
            r6.warn(r7, r1)
            goto L_0x0063
        L_0x008d:
            r6 = move-exception
        L_0x008e:
            if (r3 == 0) goto L_0x0093
            r3.close()     // Catch:{ IOException -> 0x0094 }
        L_0x0093:
            throw r6
        L_0x0094:
            r1 = move-exception
            com.alipay.mobile.common.logging.api.trace.TraceLogger r7 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()
            java.lang.String r8 = "ImageEditServiceImpl"
            r7.warn(r8, r1)
            goto L_0x0093
        L_0x009f:
            r6 = move-exception
            r3 = r4
            goto L_0x008e
        L_0x00a2:
            r1 = move-exception
            r3 = r4
            goto L_0x0073
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.beehive.imageedit.service.impl.ImageEditServiceImpl.saveToPath(android.graphics.Bitmap):java.lang.String");
    }
}
