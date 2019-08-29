package com.mpaas.nebula.plugin;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import com.alibaba.fastjson.JSONObject;
import com.alipay.android.phone.mobilecommon.multimedia.api.MultimediaImageProcessor;
import com.alipay.android.phone.mobilecommon.multimedia.api.MultimediaImageService;
import com.alipay.android.phone.mobilecommon.multimedia.api.data.APMultimediaTaskModel;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.APImageUploadCallback;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.APImageUploadOption;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.APImageUploadOption.QUALITITY;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APImageUpRequest;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APImageUploadRsp;
import com.alipay.mobile.beehive.audio.Constants;
import com.alipay.mobile.beehive.service.PhotoEditListener;
import com.alipay.mobile.beehive.service.PhotoInfo;
import com.alipay.mobile.beehive.service.PhotoParam;
import com.alipay.mobile.beehive.service.PhotoSelectListener;
import com.alipay.mobile.beehive.service.PhotoService;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.framework.app.MicroApplication;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5Event.Error;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.h5container.api.H5SimplePlugin;
import com.alipay.mobile.nebula.appcenter.util.H5AppUtil;
import com.alipay.mobile.nebula.provider.H5LogProvider;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5ThreadType;
import com.alipay.mobile.nebula.util.H5UrlHelper;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.rome.longlinkservice.LongLinkMsgConstants;
import com.alipay.mobile.tinyappcommon.utils.WalletTinyappUtils;
import com.alipay.multimedia.js.file.H5FileUploadPlugin;
import com.alipay.multimedia.js.image.H5CompressImagePlugin;
import com.mpaas.nebula.NebulaBiz;
import com.mpaas.nebula.adapter.R;
import com.mpaas.nebula.util.ImageHelpUtil;
import com.mpaas.nebula.util.ImageHelpUtil.UploadResult;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;

public class H5PhotoPlugin extends H5SimplePlugin {
    public static final String PHOTO = "photo";
    public static final String TAG = "H5PhotoPlugin";

    public void onPrepare(H5EventFilter filter) {
        filter.addAction("photo");
    }

    public boolean handleEvent(H5Event event, H5BridgeContext bridgeContext) {
        String action = event.getAction();
        if (!(event.getTarget() instanceof H5Page)) {
            H5Log.w("H5PhotoPlugin", "not from h5 page.");
            bridgeContext.sendError(event, Error.INVALID_PARAM);
            return false;
        } else if (!"photo".equals(action)) {
            return false;
        } else {
            a(event, bridgeContext);
            return true;
        }
    }

    private void a(final H5Event event, final H5BridgeContext bridgeContext) {
        PhotoService photoService = (PhotoService) NebulaBiz.getExtServiceByInterface(PhotoService.class.getName());
        MicroApplication microApp = LauncherApplicationAgent.getInstance().getMicroApplicationContext().findTopRunningApp();
        float beautyImageLevel = 0.0f;
        if (event.getParam() != null && event.getParam().containsKey("beautyLevel")) {
            try {
                beautyImageLevel = event.getParam().getFloat("beautyLevel").floatValue();
                H5Log.d("H5PhotoPlugin", "beautyImageLevel:" + beautyImageLevel);
                if (beautyImageLevel < 0.0f || beautyImageLevel > 1.0f) {
                    bridgeContext.sendError(event, Error.INVALID_PARAM);
                    return;
                }
            } catch (Exception e) {
                bridgeContext.sendError(event, Error.INVALID_PARAM);
                return;
            }
        }
        Bundle bundle = new Bundle();
        bundle.putFloat(PhotoParam.BEAUTY_IMAGE_LEVEL, beautyImageLevel);
        bundle.putInt(PhotoParam.MAX_SELECT, 1);
        bundle.putString(PhotoParam.FINISH_TEXT, NebulaBiz.getResources().getString(R.string.ok));
        bundle.putBoolean(PhotoParam.ENABLE_PREVIEW, false);
        photoService.selectPhoto(microApp, bundle, new PhotoSelectListener() {
            public void onPhotoSelected(List<PhotoInfo> list, Bundle bundle) {
                if (list != null && !list.isEmpty()) {
                    H5PhotoPlugin.this.a(list.get(0), event, bridgeContext);
                }
            }

            public void onSelectCanceled() {
                bridgeContext.sendBridgeResult("error", "10");
            }
        });
    }

    /* access modifiers changed from: private */
    public void a(PhotoInfo photoInfo, final H5Event event, final H5BridgeContext context) {
        boolean allowEdit = H5Utils.getBoolean(event.getParam(), (String) "allowEdit", false);
        final String scene = photoInfo.isPicCurrentlyTaked() ? WalletTinyappUtils.CONST_SCOPE_CAMERA : "assets";
        H5Log.debug("H5PhotoPlugin", "photo allowEdit " + allowEdit);
        if (!allowEdit) {
            a(event, photoInfo.getPhotoPath(), context, scene);
            return;
        }
        MicroApplication microApp = LauncherApplicationAgent.getInstance().getMicroApplicationContext().findTopRunningApp();
        Bundle bundle = new Bundle();
        bundle.putBoolean(PhotoParam.SAVE_ON_EDIT, true);
        ((PhotoService) NebulaBiz.findServiceByInterface(PhotoService.class.getName())).editPhoto(microApp, photoInfo, bundle, new PhotoEditListener() {
            public void onPhotoEdited(PhotoInfo photoInfo, Bundle bundle, Bitmap bitmap) {
                String imageUrl = bundle.getString(PhotoParam.SAVE_PATH);
                if (TextUtils.isEmpty(imageUrl)) {
                    imageUrl = photoInfo.getPhotoPath();
                }
                H5PhotoPlugin.this.a(event, imageUrl, context, scene);
            }

            public void onEditCanceled(PhotoInfo photoInfo) {
                if (context != null) {
                    context.sendBridgeResult("error", "10");
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public void a(H5Event event, String originalPath, H5BridgeContext bridgeContext, String scene) {
        try {
            final String imageUrl = H5UrlHelper.parseUrl(originalPath).getPath();
            final H5Event h5Event = event;
            final H5BridgeContext h5BridgeContext = bridgeContext;
            final String str = scene;
            final String str2 = originalPath;
            H5Utils.getExecutor(H5ThreadType.URGENT).execute(new Runnable() {
                /* JADX WARNING: Removed duplicated region for block: B:63:0x02ce A[Catch:{ Throwable -> 0x02d9, all -> 0x0369 }, LOOP:0: B:61:0x02c1->B:63:0x02ce, LOOP_END] */
                /* JADX WARNING: Removed duplicated region for block: B:85:0x0352 A[EDGE_INSN: B:85:0x0352->B:86:? ?: BREAK  
                EDGE_INSN: B:85:0x0352->B:86:? ?: BREAK  
                EDGE_INSN: B:85:0x0352->B:86:? ?: BREAK  , SYNTHETIC, Splitter:B:85:0x0352] */
                /* JADX WARNING: Removed duplicated region for block: B:85:0x0352 A[EDGE_INSN: B:85:0x0352->B:86:? ?: BREAK  
                EDGE_INSN: B:85:0x0352->B:86:? ?: BREAK  , SYNTHETIC, Splitter:B:85:0x0352] */
                /* Code decompiled incorrectly, please refer to instructions dump. */
                public void run() {
                    /*
                        r54 = this;
                        r0 = r54
                        com.alipay.mobile.h5container.api.H5Event r4 = r2
                        com.alibaba.fastjson.JSONObject r6 = r4.getParam()
                        java.lang.String r4 = "dataType"
                        java.lang.String r9 = com.alipay.mobile.nebula.util.H5Utils.getString(r6, r4)
                        java.lang.String r4 = "upload"
                        r5 = 0
                        boolean r7 = com.alipay.mobile.nebula.util.H5Utils.getBoolean(r6, r4, r5)
                        java.lang.String r4 = "imageFormat"
                        java.lang.String r41 = com.alipay.mobile.nebula.util.H5Utils.getString(r6, r4)
                        java.lang.String r4 = "jpg"
                        r0 = r41
                        boolean r4 = r4.equalsIgnoreCase(r0)
                        if (r4 != 0) goto L_0x0031
                        java.lang.String r4 = "png"
                        r0 = r41
                        boolean r4 = r4.equalsIgnoreCase(r0)
                        if (r4 != 0) goto L_0x0031
                        java.lang.String r41 = "jpg"
                    L_0x0031:
                        java.lang.String r4 = "quality"
                        int r47 = com.alipay.mobile.nebula.util.H5Utils.getInt(r6, r4)
                        java.lang.String r4 = "maxWidth"
                        int r43 = com.alipay.mobile.nebula.util.H5Utils.getInt(r6, r4)
                        java.lang.String r4 = "maxHeight"
                        int r42 = com.alipay.mobile.nebula.util.H5Utils.getInt(r6, r4)
                        r45 = 0
                        if (r43 > 0) goto L_0x0049
                        if (r42 <= 0) goto L_0x004b
                    L_0x0049:
                        r45 = 1
                    L_0x004b:
                        java.lang.String r4 = "jpg"
                        r0 = r41
                        boolean r4 = r0.equals(r4)
                        if (r4 == 0) goto L_0x032a
                        r4 = 100
                        r0 = r47
                        if (r0 == r4) goto L_0x032a
                        r45 = 1
                    L_0x005d:
                        java.lang.String r4 = "H5PhotoPlugin"
                        java.lang.StringBuilder r5 = new java.lang.StringBuilder
                        java.lang.String r11 = "maxWidth "
                        r5.<init>(r11)
                        r0 = r43
                        java.lang.StringBuilder r5 = r5.append(r0)
                        java.lang.String r11 = " maxHeight "
                        java.lang.StringBuilder r5 = r5.append(r11)
                        r0 = r42
                        java.lang.StringBuilder r5 = r5.append(r0)
                        java.lang.String r11 = " quality "
                        java.lang.StringBuilder r5 = r5.append(r11)
                        r0 = r47
                        java.lang.StringBuilder r5 = r5.append(r0)
                        java.lang.String r5 = r5.toString()
                        com.alipay.mobile.nebula.util.H5Log.debug(r4, r5)
                        java.lang.Class<com.alipay.android.phone.mobilecommon.multimedia.api.MultimediaImageProcessor> r4 = com.alipay.android.phone.mobilecommon.multimedia.api.MultimediaImageProcessor.class
                        java.lang.String r4 = r4.getName()
                        java.lang.Object r44 = com.mpaas.nebula.NebulaBiz.findServiceByInterface(r4)
                        com.alipay.android.phone.mobilecommon.multimedia.api.MultimediaImageProcessor r44 = (com.alipay.android.phone.mobilecommon.multimedia.api.MultimediaImageProcessor) r44
                        if (r44 == 0) goto L_0x0329
                        java.lang.String r4 = "H5PhotoPlugin"
                        java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0335 }
                        java.lang.String r11 = "imageUrl "
                        r5.<init>(r11)     // Catch:{ Exception -> 0x0335 }
                        r0 = r54
                        java.lang.String r11 = r3     // Catch:{ Exception -> 0x0335 }
                        java.lang.StringBuilder r5 = r5.append(r11)     // Catch:{ Exception -> 0x0335 }
                        java.lang.String r5 = r5.toString()     // Catch:{ Exception -> 0x0335 }
                        com.alipay.mobile.nebula.util.H5Log.debug(r4, r5)     // Catch:{ Exception -> 0x0335 }
                        java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0335 }
                        java.lang.String r5 = "file://"
                        r4.<init>(r5)     // Catch:{ Exception -> 0x0335 }
                        r0 = r54
                        java.lang.String r5 = r3     // Catch:{ Exception -> 0x0335 }
                        java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ Exception -> 0x0335 }
                        java.lang.String r10 = r4.toString()     // Catch:{ Exception -> 0x0335 }
                        java.lang.String r4 = "orientation"
                        int r46 = com.alipay.mobile.nebula.util.H5Utils.getInt(r6, r4)     // Catch:{ Exception -> 0x0335 }
                        r50 = 0
                        r13 = 0
                        long r26 = java.lang.System.currentTimeMillis()     // Catch:{ Exception -> 0x0335 }
                        com.alipay.android.phone.mobilecommon.multimedia.api.data.image.processor.decode.APDecodeOptions r16 = new com.alipay.android.phone.mobilecommon.multimedia.api.data.image.processor.decode.APDecodeOptions     // Catch:{ Exception -> 0x0335 }
                        r16.<init>()     // Catch:{ Exception -> 0x0335 }
                        com.alipay.android.phone.mobilecommon.multimedia.api.data.image.processor.decode.APDecodeOptions$MaxLenMode r4 = new com.alipay.android.phone.mobilecommon.multimedia.api.data.image.processor.decode.APDecodeOptions$MaxLenMode     // Catch:{ Exception -> 0x0335 }
                        r0 = r43
                        r1 = r42
                        int r5 = java.lang.Math.max(r0, r1)     // Catch:{ Exception -> 0x0335 }
                        java.lang.Integer r5 = java.lang.Integer.valueOf(r5)     // Catch:{ Exception -> 0x0335 }
                        r4.<init>(r5)     // Catch:{ Exception -> 0x0335 }
                        r0 = r16
                        r0.mode = r4     // Catch:{ Exception -> 0x0335 }
                        java.io.File r4 = new java.io.File     // Catch:{ Exception -> 0x0335 }
                        r0 = r54
                        java.lang.String r5 = r3     // Catch:{ Exception -> 0x0335 }
                        r4.<init>(r5)     // Catch:{ Exception -> 0x0335 }
                        r0 = r44
                        r1 = r16
                        com.alipay.android.phone.mobilecommon.multimedia.api.data.image.processor.decode.APDecodeResult r17 = r0.decodeBitmap(r4, r1)     // Catch:{ Exception -> 0x0335 }
                        if (r17 == 0) goto L_0x0104
                        r0 = r17
                        android.graphics.Bitmap r0 = r0.bitmap     // Catch:{ Exception -> 0x0335 }
                        r50 = r0
                    L_0x0104:
                        if (r50 != 0) goto L_0x010e
                        r0 = r54
                        java.lang.String r4 = r3     // Catch:{ Exception -> 0x0335 }
                        android.graphics.Bitmap r50 = android.graphics.BitmapFactory.decodeFile(r4)     // Catch:{ Exception -> 0x0335 }
                    L_0x010e:
                        long r4 = java.lang.System.currentTimeMillis()     // Catch:{ Exception -> 0x0335 }
                        long r4 = r4 - r26
                        java.lang.Long r33 = java.lang.Long.valueOf(r4)     // Catch:{ Exception -> 0x0335 }
                        java.lang.String r4 = "H5PhotoPlugin"
                        java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0335 }
                        java.lang.String r11 = "cropBitmap cost "
                        r5.<init>(r11)     // Catch:{ Exception -> 0x0335 }
                        r0 = r33
                        java.lang.StringBuilder r5 = r5.append(r0)     // Catch:{ Exception -> 0x0335 }
                        java.lang.String r5 = r5.toString()     // Catch:{ Exception -> 0x0335 }
                        com.alipay.mobile.nebula.util.H5Log.debug(r4, r5)     // Catch:{ Exception -> 0x0335 }
                        long r28 = java.lang.System.currentTimeMillis()     // Catch:{ Exception -> 0x0335 }
                        r0 = r54
                        com.mpaas.nebula.plugin.H5PhotoPlugin r4 = com.mpaas.nebula.plugin.H5PhotoPlugin.this     // Catch:{ Exception -> 0x0335 }
                        r0 = r50
                        r1 = r47
                        android.graphics.Bitmap r50 = r4.imageQuality(r0, r1)     // Catch:{ Exception -> 0x0335 }
                        long r4 = java.lang.System.currentTimeMillis()     // Catch:{ Exception -> 0x0335 }
                        long r4 = r4 - r28
                        java.lang.Long r48 = java.lang.Long.valueOf(r4)     // Catch:{ Exception -> 0x0335 }
                        java.lang.String r4 = "H5PhotoPlugin"
                        java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0335 }
                        java.lang.String r11 = "imageQuality cost "
                        r5.<init>(r11)     // Catch:{ Exception -> 0x0335 }
                        r0 = r48
                        java.lang.StringBuilder r5 = r5.append(r0)     // Catch:{ Exception -> 0x0335 }
                        java.lang.String r5 = r5.toString()     // Catch:{ Exception -> 0x0335 }
                        com.alipay.mobile.nebula.util.H5Log.debug(r4, r5)     // Catch:{ Exception -> 0x0335 }
                        com.alipay.android.phone.mobilecommon.multimedia.api.data.image.processor.encode.APEncodeOptions r18 = new com.alipay.android.phone.mobilecommon.multimedia.api.data.image.processor.encode.APEncodeOptions     // Catch:{ Exception -> 0x0335 }
                        r18.<init>()     // Catch:{ Exception -> 0x0335 }
                        if (r46 == 0) goto L_0x032e
                        java.lang.Integer r4 = java.lang.Integer.valueOf(r46)     // Catch:{ Exception -> 0x0335 }
                        r0 = r18
                        r0.forceRotate = r4     // Catch:{ Exception -> 0x0335 }
                    L_0x016d:
                        r4 = 0
                        r0 = r18
                        r0.outFormat = r4     // Catch:{ Exception -> 0x0335 }
                        r4 = 1
                        r0 = r18
                        r0.requireOutputInfo = r4     // Catch:{ Exception -> 0x0335 }
                        if (r45 == 0) goto L_0x01dc
                        java.lang.String r4 = "jpg"
                        r0 = r41
                        boolean r4 = r4.equals(r0)     // Catch:{ Exception -> 0x0335 }
                        if (r4 == 0) goto L_0x01dc
                        android.content.Context r4 = com.mpaas.nebula.NebulaBiz.getContext()     // Catch:{ Exception -> 0x0335 }
                        java.lang.String r34 = com.alipay.mobile.nebula.util.H5ImageUtil.getImageTempDir(r4)     // Catch:{ Exception -> 0x0335 }
                        java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0335 }
                        r4.<init>()     // Catch:{ Exception -> 0x0335 }
                        long r52 = java.lang.System.currentTimeMillis()     // Catch:{ Exception -> 0x0335 }
                        r0 = r52
                        java.lang.StringBuilder r4 = r4.append(r0)     // Catch:{ Exception -> 0x0335 }
                        java.lang.String r5 = ".jpg"
                        java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ Exception -> 0x0335 }
                        java.lang.String r36 = r4.toString()     // Catch:{ Exception -> 0x0335 }
                        com.alipay.mobile.nebula.util.H5FileUtil.mkdirs(r34)     // Catch:{ Exception -> 0x0335 }
                        java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0335 }
                        r4.<init>()     // Catch:{ Exception -> 0x0335 }
                        r0 = r34
                        java.lang.StringBuilder r4 = r4.append(r0)     // Catch:{ Exception -> 0x0335 }
                        r0 = r36
                        java.lang.StringBuilder r4 = r4.append(r0)     // Catch:{ Exception -> 0x0335 }
                        java.lang.String r4 = r4.toString()     // Catch:{ Exception -> 0x0335 }
                        r0 = r18
                        r0.outputFile = r4     // Catch:{ Exception -> 0x0335 }
                        java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0335 }
                        java.lang.String r5 = "file://"
                        r4.<init>(r5)     // Catch:{ Exception -> 0x0335 }
                        r0 = r34
                        java.lang.StringBuilder r4 = r4.append(r0)     // Catch:{ Exception -> 0x0335 }
                        r0 = r36
                        java.lang.StringBuilder r4 = r4.append(r0)     // Catch:{ Exception -> 0x0335 }
                        java.lang.String r10 = r4.toString()     // Catch:{ Exception -> 0x0335 }
                        java.lang.String r4 = "H5PhotoPlugin"
                        com.alipay.mobile.nebula.util.H5Log.debug(r4, r10)     // Catch:{ Exception -> 0x0335 }
                    L_0x01dc:
                        long r30 = java.lang.System.currentTimeMillis()     // Catch:{ Exception -> 0x0335 }
                        r0 = r44
                        r1 = r50
                        r2 = r18
                        com.alipay.android.phone.mobilecommon.multimedia.api.data.image.processor.encode.APEncodeResult r19 = r0.compress(r1, r2)     // Catch:{ Exception -> 0x0335 }
                        long r4 = java.lang.System.currentTimeMillis()     // Catch:{ Exception -> 0x0335 }
                        long r4 = r4 - r30
                        java.lang.Long r32 = java.lang.Long.valueOf(r4)     // Catch:{ Exception -> 0x0335 }
                        java.lang.String r4 = "H5PhotoPlugin"
                        java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0335 }
                        java.lang.String r11 = "after compress "
                        r5.<init>(r11)     // Catch:{ Exception -> 0x0335 }
                        r0 = r32
                        java.lang.StringBuilder r5 = r5.append(r0)     // Catch:{ Exception -> 0x0335 }
                        java.lang.String r5 = r5.toString()     // Catch:{ Exception -> 0x0335 }
                        com.alipay.mobile.nebula.util.H5Log.debug(r4, r5)     // Catch:{ Exception -> 0x0335 }
                        r14 = 0
                        r15 = 0
                        if (r19 == 0) goto L_0x0224
                        r0 = r19
                        byte[] r13 = r0.encodeData     // Catch:{ Exception -> 0x0335 }
                        r0 = r19
                        com.alipay.android.phone.mobilecommon.multimedia.api.data.image.processor.APImageInfo r4 = r0.imageInfo     // Catch:{ Exception -> 0x0335 }
                        if (r4 == 0) goto L_0x0224
                        r0 = r19
                        com.alipay.android.phone.mobilecommon.multimedia.api.data.image.processor.APImageInfo r4 = r0.imageInfo     // Catch:{ Exception -> 0x0335 }
                        int r14 = r4.width     // Catch:{ Exception -> 0x0335 }
                        r0 = r19
                        com.alipay.android.phone.mobilecommon.multimedia.api.data.image.processor.APImageInfo r4 = r0.imageInfo     // Catch:{ Exception -> 0x0335 }
                        int r15 = r4.height     // Catch:{ Exception -> 0x0335 }
                    L_0x0224:
                        if (r45 == 0) goto L_0x029b
                        java.lang.String r4 = "png"
                        r0 = r41
                        boolean r4 = android.text.TextUtils.equals(r0, r4)     // Catch:{ Exception -> 0x0335 }
                        if (r4 == 0) goto L_0x029b
                        if (r13 == 0) goto L_0x029b
                        android.content.Context r4 = com.mpaas.nebula.NebulaBiz.getContext()     // Catch:{ Exception -> 0x0335 }
                        java.lang.String r34 = com.alipay.mobile.nebula.util.H5ImageUtil.getImageTempDir(r4)     // Catch:{ Exception -> 0x0335 }
                        java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0335 }
                        r4.<init>()     // Catch:{ Exception -> 0x0335 }
                        long r52 = java.lang.System.currentTimeMillis()     // Catch:{ Exception -> 0x0335 }
                        r0 = r52
                        java.lang.StringBuilder r4 = r4.append(r0)     // Catch:{ Exception -> 0x0335 }
                        java.lang.String r5 = "."
                        java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ Exception -> 0x0335 }
                        r0 = r41
                        java.lang.StringBuilder r4 = r4.append(r0)     // Catch:{ Exception -> 0x0335 }
                        java.lang.String r36 = r4.toString()     // Catch:{ Exception -> 0x0335 }
                        com.alipay.mobile.nebula.util.H5FileUtil.mkdirs(r34)     // Catch:{ Exception -> 0x0335 }
                        r37 = 0
                        java.io.FileOutputStream r38 = new java.io.FileOutputStream     // Catch:{ Throwable -> 0x033e }
                        java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x033e }
                        r4.<init>()     // Catch:{ Throwable -> 0x033e }
                        r0 = r34
                        java.lang.StringBuilder r4 = r4.append(r0)     // Catch:{ Throwable -> 0x033e }
                        r0 = r36
                        java.lang.StringBuilder r4 = r4.append(r0)     // Catch:{ Throwable -> 0x033e }
                        java.lang.String r4 = r4.toString()     // Catch:{ Throwable -> 0x033e }
                        r0 = r38
                        r0.<init>(r4)     // Catch:{ Throwable -> 0x033e }
                        r0 = r38
                        r0.write(r13)     // Catch:{ Throwable -> 0x037b, all -> 0x0377 }
                        com.alipay.mobile.nebula.util.H5IOUtils.closeQuietly(r38)     // Catch:{ Exception -> 0x0335 }
                        r37 = r38
                    L_0x0284:
                        java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0335 }
                        java.lang.String r5 = "file://"
                        r4.<init>(r5)     // Catch:{ Exception -> 0x0335 }
                        r0 = r34
                        java.lang.StringBuilder r4 = r4.append(r0)     // Catch:{ Exception -> 0x0335 }
                        r0 = r36
                        java.lang.StringBuilder r4 = r4.append(r0)     // Catch:{ Exception -> 0x0335 }
                        java.lang.String r10 = r4.toString()     // Catch:{ Exception -> 0x0335 }
                    L_0x029b:
                        if (r13 != 0) goto L_0x02ed
                        r0 = r18
                        java.lang.String r4 = r0.outputFile     // Catch:{ Exception -> 0x0335 }
                        boolean r4 = android.text.TextUtils.isEmpty(r4)     // Catch:{ Exception -> 0x0335 }
                        if (r4 != 0) goto L_0x02ed
                        r20 = 0
                        r39 = 0
                        java.io.ByteArrayOutputStream r21 = new java.io.ByteArrayOutputStream     // Catch:{ Throwable -> 0x036f }
                        r21.<init>()     // Catch:{ Throwable -> 0x036f }
                        java.io.FileInputStream r40 = new java.io.FileInputStream     // Catch:{ Throwable -> 0x0372, all -> 0x0365 }
                        r0 = r18
                        java.lang.String r4 = r0.outputFile     // Catch:{ Throwable -> 0x0372, all -> 0x0365 }
                        r0 = r40
                        r0.<init>(r4)     // Catch:{ Throwable -> 0x0372, all -> 0x0365 }
                        r4 = 4096(0x1000, float:5.74E-42)
                        byte[] r0 = new byte[r4]     // Catch:{ Throwable -> 0x02d9, all -> 0x0369 }
                        r25 = r0
                    L_0x02c1:
                        r0 = r40
                        r1 = r25
                        int r49 = r0.read(r1)     // Catch:{ Throwable -> 0x02d9, all -> 0x0369 }
                        r4 = -1
                        r0 = r49
                        if (r0 == r4) goto L_0x0352
                        r4 = 0
                        r0 = r21
                        r1 = r25
                        r2 = r49
                        r0.write(r1, r4, r2)     // Catch:{ Throwable -> 0x02d9, all -> 0x0369 }
                        goto L_0x02c1
                    L_0x02d9:
                        r51 = move-exception
                        r39 = r40
                        r20 = r21
                    L_0x02de:
                        java.lang.String r4 = "H5PhotoPlugin"
                        java.lang.String r5 = "read bytes error"
                        r0 = r51
                        com.alipay.mobile.nebula.util.H5Log.e(r4, r5, r0)     // Catch:{ all -> 0x035d }
                        com.alipay.mobile.nebula.util.H5IOUtils.closeQuietly(r39)     // Catch:{ Exception -> 0x0335 }
                        com.alipay.mobile.nebula.util.H5IOUtils.closeQuietly(r20)     // Catch:{ Exception -> 0x0335 }
                    L_0x02ed:
                        long r22 = java.lang.System.currentTimeMillis()     // Catch:{ Exception -> 0x0335 }
                        r4 = 2
                        java.lang.String r8 = android.util.Base64.encodeToString(r13, r4)     // Catch:{ Exception -> 0x0335 }
                        long r4 = java.lang.System.currentTimeMillis()     // Catch:{ Exception -> 0x0335 }
                        long r4 = r4 - r22
                        java.lang.Long r24 = java.lang.Long.valueOf(r4)     // Catch:{ Exception -> 0x0335 }
                        java.lang.String r4 = "H5PhotoPlugin"
                        java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0335 }
                        java.lang.String r11 = "base64 cost:"
                        r5.<init>(r11)     // Catch:{ Exception -> 0x0335 }
                        r0 = r24
                        java.lang.StringBuilder r5 = r5.append(r0)     // Catch:{ Exception -> 0x0335 }
                        java.lang.String r5 = r5.toString()     // Catch:{ Exception -> 0x0335 }
                        com.alipay.mobile.nebula.util.H5Log.debug(r4, r5)     // Catch:{ Exception -> 0x0335 }
                        r0 = r54
                        com.mpaas.nebula.plugin.H5PhotoPlugin r4 = com.mpaas.nebula.plugin.H5PhotoPlugin.this     // Catch:{ Exception -> 0x0335 }
                        r0 = r54
                        com.alipay.mobile.h5container.api.H5BridgeContext r5 = r4     // Catch:{ Exception -> 0x0335 }
                        r0 = r54
                        java.lang.String r11 = r5     // Catch:{ Exception -> 0x0335 }
                        r0 = r54
                        java.lang.String r12 = r6     // Catch:{ Exception -> 0x0335 }
                        r4.a(r5, r6, r7, r8, r9, r10, r11, r12, r13, r14, r15)     // Catch:{ Exception -> 0x0335 }
                    L_0x0329:
                        return
                    L_0x032a:
                        r47 = 100
                        goto L_0x005d
                    L_0x032e:
                        r4 = 1
                        r0 = r18
                        r0.autoRotate = r4     // Catch:{ Exception -> 0x0335 }
                        goto L_0x016d
                    L_0x0335:
                        r35 = move-exception
                        java.lang.String r4 = "H5PhotoPlugin"
                        r0 = r35
                        com.alipay.mobile.nebula.util.H5Log.e(r4, r0)
                        goto L_0x0329
                    L_0x033e:
                        r51 = move-exception
                    L_0x033f:
                        java.lang.String r4 = "H5PhotoPlugin"
                        java.lang.String r5 = "write file error"
                        r0 = r51
                        com.alipay.mobile.nebula.util.H5Log.e(r4, r5, r0)     // Catch:{ all -> 0x034d }
                        com.alipay.mobile.nebula.util.H5IOUtils.closeQuietly(r37)     // Catch:{ Exception -> 0x0335 }
                        goto L_0x0284
                    L_0x034d:
                        r4 = move-exception
                    L_0x034e:
                        com.alipay.mobile.nebula.util.H5IOUtils.closeQuietly(r37)     // Catch:{ Exception -> 0x0335 }
                        throw r4     // Catch:{ Exception -> 0x0335 }
                    L_0x0352:
                        byte[] r13 = r21.toByteArray()     // Catch:{ Throwable -> 0x02d9, all -> 0x0369 }
                        com.alipay.mobile.nebula.util.H5IOUtils.closeQuietly(r40)     // Catch:{ Exception -> 0x0335 }
                        com.alipay.mobile.nebula.util.H5IOUtils.closeQuietly(r21)     // Catch:{ Exception -> 0x0335 }
                        goto L_0x02ed
                    L_0x035d:
                        r4 = move-exception
                    L_0x035e:
                        com.alipay.mobile.nebula.util.H5IOUtils.closeQuietly(r39)     // Catch:{ Exception -> 0x0335 }
                        com.alipay.mobile.nebula.util.H5IOUtils.closeQuietly(r20)     // Catch:{ Exception -> 0x0335 }
                        throw r4     // Catch:{ Exception -> 0x0335 }
                    L_0x0365:
                        r4 = move-exception
                        r20 = r21
                        goto L_0x035e
                    L_0x0369:
                        r4 = move-exception
                        r39 = r40
                        r20 = r21
                        goto L_0x035e
                    L_0x036f:
                        r51 = move-exception
                        goto L_0x02de
                    L_0x0372:
                        r51 = move-exception
                        r20 = r21
                        goto L_0x02de
                    L_0x0377:
                        r4 = move-exception
                        r37 = r38
                        goto L_0x034e
                    L_0x037b:
                        r51 = move-exception
                        r37 = r38
                        goto L_0x033f
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.mpaas.nebula.plugin.H5PhotoPlugin.AnonymousClass3.run():void");
                }
            });
        } catch (Exception e) {
            H5Log.e((String) "H5PhotoPlugin", (Throwable) e);
        }
    }

    public Bitmap imageQuality(Bitmap bitmap, int quality) {
        if (bitmap == null) {
            return bitmap;
        }
        if (quality < 50 || quality > 100) {
            H5Log.d("H5PhotoPlugin", "set quality as default 75.");
            quality = 75;
        }
        if (quality != 100) {
            try {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(CompressFormat.JPEG, quality, baos);
                ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
                bitmap = BitmapFactory.decodeStream(bais, null, null);
                baos.close();
                bais.close();
            } catch (Throwable e) {
                H5Log.e((String) "H5PhotoPlugin", e);
            }
        }
        return bitmap;
    }

    /* access modifiers changed from: private */
    public void a(H5BridgeContext bridgeContext, JSONObject param, boolean upload, String data, String dataType, String fileUrl, String scene, String originalPath, byte[] bytes, int width, int height) {
        JSONObject info = new JSONObject();
        if (upload && !param.containsKey("multimediaConfig")) {
            UploadResult upRes = ImageHelpUtil.getUploadResult("PUBLICID", LongLinkMsgConstants.MSG_PACKET_TYPE_CHAT, H5Utils.getString(param, (String) "uploadTo", (String) "123123"), data, width, height);
            info.put((String) "uploadedImageID", (Object) upRes != null ? upRes.id : "");
            info.put((String) "uploadedImageURL", (Object) upRes != null ? upRes.url : "");
            info.put((String) "uploadedSuccess", (Object) Boolean.valueOf(upRes != null));
            if (!(upRes == null || upRes.status == 100)) {
                info.put((String) "error", (Object) NebulaBiz.getResources().getString(R.string.networkbusi));
            }
        }
        if (H5CompressImagePlugin.DATA_TYPE_FILE_URL.equals(dataType)) {
            info.put((String) H5CompressImagePlugin.DATA_TYPE_FILE_URL, (Object) fileUrl);
        } else {
            info.put((String) H5CompressImagePlugin.DATA_TYPE_DATA_URL, (Object) data);
            info.put((String) H5CompressImagePlugin.DATA_TYPE_FILE_URL, (Object) fileUrl);
        }
        info.put((String) H5AppUtil.scene, (Object) scene);
        if (H5Utils.getBoolean(param, (String) "storeOriginal", false)) {
            info.put((String) "originalFileURL", (Object) originalPath);
        }
        if (param.containsKey("multimediaConfig")) {
            a(info, H5Utils.getJSONObject(param, "multimediaConfig", null), bridgeContext, bytes, width, height);
        } else {
            bridgeContext.sendBridgeResult(info);
        }
    }

    private void a(JSONObject info, JSONObject config, H5BridgeContext bridgeContext, byte[] bytes, int width, int height) {
        final String business = H5Utils.getString(config, (String) Constants.KEY_AUDIO_BUSINESS_ID, (String) NebulaBiz.TAG);
        final int compress = H5Utils.getInt(config, (String) MultimediaImageProcessor.COMPOSITE_INT_KEY_COMPRESS_LEVEL, 4);
        final JSONObject jSONObject = info;
        final H5BridgeContext h5BridgeContext = bridgeContext;
        final int i = width;
        final int i2 = height;
        final byte[] bArr = bytes;
        H5Utils.getExecutor(H5ThreadType.URGENT).execute(new Runnable() {
            public void run() {
                MultimediaImageService multimediaImageService = (MultimediaImageService) NebulaBiz.findServiceByInterface(MultimediaImageService.class.getName());
                if (multimediaImageService != null) {
                    APImageUpRequest req = new APImageUpRequest();
                    APImageUploadOption option = new APImageUploadOption();
                    if (compress == 0) {
                        option.setQua(QUALITITY.LOW);
                    } else if (compress == 1) {
                        option.setQua(QUALITITY.MIDDLE);
                    } else if (compress == 2) {
                        option.setQua(QUALITITY.HIGH);
                    } else if (compress == 3) {
                        option.setQua(QUALITITY.ORIGINAL);
                    } else {
                        option.setQua(QUALITITY.DEFAULT);
                    }
                    req.option = option;
                    req.callback = new APImageUploadCallback() {
                        public void onCompressSucc(Drawable drawable) {
                        }

                        public void onStartUpload(APMultimediaTaskModel apMultimediaTaskModel) {
                        }

                        public void onProcess(APMultimediaTaskModel apMultimediaTaskModel, int i) {
                        }

                        public void onSuccess(APImageUploadRsp apImageUploadRsp) {
                            String multimediaID = (apImageUploadRsp == null || apImageUploadRsp.getTaskStatus() == null || apImageUploadRsp.getTaskStatus().getCloudId() == null) ? "" : apImageUploadRsp.getTaskStatus().getCloudId();
                            jSONObject.put((String) H5FileUploadPlugin.RESULT_ID, (Object) multimediaID);
                            H5Log.debug("H5PhotoPlugin", "multimediaID:" + multimediaID);
                            if (h5BridgeContext != null) {
                                h5BridgeContext.sendBridgeResult(jSONObject);
                            }
                        }

                        public void onError(APImageUploadRsp apImageUploadRsp, Exception e) {
                            if (!(apImageUploadRsp == null || apImageUploadRsp.getRetmsg() == null)) {
                                jSONObject.put((String) "error", (Object) apImageUploadRsp.getRetmsg().getCode());
                                jSONObject.put((String) "message", (Object) apImageUploadRsp.getRetmsg().getMsg());
                                H5LogProvider logProvider = (H5LogProvider) H5Utils.getProvider(H5LogProvider.class.getName());
                                if (logProvider != null) {
                                    logProvider.log("H5PhotoPlugin", null, null, null, null, "H5PhotoPlugin^uploadImageException=" + apImageUploadRsp.getRetmsg().getMsg());
                                }
                            }
                            if (h5BridgeContext != null) {
                                h5BridgeContext.sendBridgeResult(jSONObject);
                            }
                        }
                    };
                    req.width = i;
                    req.height = i2;
                    req.fileData = bArr;
                    multimediaImageService.uploadImage(req, business);
                    return;
                }
                H5Log.e((String) "H5PhotoPlugin", (String) "multimediaImageService == null");
            }
        });
    }
}
