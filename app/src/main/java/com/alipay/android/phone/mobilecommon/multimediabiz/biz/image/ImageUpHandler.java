package com.alipay.android.phone.mobilecommon.multimediabiz.biz.image;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import com.alipay.android.phone.mobilecommon.multimedia.api.data.APConstants;
import com.alipay.android.phone.mobilecommon.multimedia.api.data.APMultimediaTaskModel;
import com.alipay.android.phone.mobilecommon.multimedia.file.APFileUploadCallback;
import com.alipay.android.phone.mobilecommon.multimedia.file.data.APFileReq;
import com.alipay.android.phone.mobilecommon.multimedia.file.data.APFileUploadRsp;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.APImageUploadCallback;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.APImageUploadOption;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.APImageUploadOption.QUALITITY;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.ImageWorkerPlugin;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APImageMarkRequest;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APImageRetMsg;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APImageRetMsg.RETCODE;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APImageUploadRsp;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.adjuster.SandboxWrapper;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.BitmapCacheKey;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.OriginalBitmapCacheKey;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.db.UpCacheHelper;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.module.resp.DjangoFileInfoResp;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.module.resp.FileUpResp;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.common.processor.ProgProcessor;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.common.processor.ProgProcessor.ProcessCallback;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.ConfigManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.SimpleConfigProvider;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.items.GifConf;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.assist.ImageDiskCache;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.gif.GifProcessor;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.manager.APMultimediaTaskManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.net.UriFactory;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.net.UriFactory.Request;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.statistic.image.UploadImagePerf;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.tools.LocalIdTool;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.AESUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.CommonUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.FileUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.LogUnAvailbleItem;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.NBNetUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.ParamChecker;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.PathUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.UCLogUtil;
import com.alipay.android.phone.mobilesdk.storage.file.ZFile;
import com.alipay.mobile.common.nbnet.api.ExtInfoConstans;
import com.alipay.mobile.common.nbnet.api.upload.NBNetUploadCallback;
import com.alipay.mobile.common.nbnet.api.upload.NBNetUploadClient;
import com.alipay.mobile.common.nbnet.api.upload.NBNetUploadRequest;
import com.alipay.mobile.common.nbnet.api.upload.NBNetUploadResponse;
import com.alipay.mobile.common.transport.utils.NetworkUtils;
import com.alipay.mobile.common.utils.MD5Util;
import com.alipay.mobile.monitor.track.spm.merge.MergeUtil;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;

public class ImageUpHandler extends ImageHandler<Void> {
    public static final int TYPE_CACHE_ID = 3;
    public static final int TYPE_CONTET_URI = 4;
    public static final int TYPE_DATA = 1;
    public static final int TYPE_FILE = 0;
    public static final int TYPE_LOCAL_ID = 2;
    /* access modifiers changed from: private */
    public static final Logger o = Logger.getLogger((String) "ImageUpHandler");
    private long d;
    /* access modifiers changed from: private */
    public String e;
    private File f;
    private byte[] g;
    private File h;
    private Map<APImageUploadCallback, Object> i;
    private APImageUploadOption j;
    /* access modifiers changed from: private */
    public APMultimediaTaskModel k;
    private int l = -1;
    /* access modifiers changed from: private */
    public RETCODE m = RETCODE.UPLOAD_ERROR;
    private final String n;
    private int p = 0;
    /* access modifiers changed from: private */
    public int q = 0;
    /* access modifiers changed from: private */
    public AtomicInteger r = new AtomicInteger(-1);
    private ProgProcessor s = null;
    private UploadImagePerf t = new UploadImagePerf();
    private boolean u = true;
    private int v = -1;
    private ProcessCallback w = new ProcessCallback() {
        public void onProcessCallback(int progress) {
            ImageUpHandler.this.r.set(progress);
            ImageUpHandler.this.a(0, 0, Integer.valueOf(progress), false);
        }
    };

    public static class EncryptException extends Exception {
    }

    public ImageUpHandler(Context context, String filePath, APImageUploadCallback cb, APImageUploadOption option, APMultimediaTaskModel taskStatus) {
        super(context);
        filePath = LocalIdTool.isLocalIdRes(filePath) ? LocalIdTool.get().decodeToPath(filePath) : filePath;
        this.e = PathUtils.extractPath(filePath);
        this.i = getImageManager().getUpTaskCallback(taskStatus.getTaskId());
        this.j = option;
        this.k = taskStatus;
        this.k.setSourcePath(filePath);
        this.l = b();
        ParamChecker.pmdCheck(cb);
        String str = option == null ? APConstants.DEFAULT_BUSINESS : TextUtils.isEmpty(option.bizType) ? option.businessId : option.bizType;
        this.n = str;
    }

    public ImageUpHandler(Context context, byte[] fileData, APImageUploadCallback cb, APImageUploadOption option, APMultimediaTaskModel taskStatus) {
        super(context);
        this.g = fileData;
        this.i = getImageManager().getUpTaskCallback(taskStatus.getTaskId());
        this.j = option;
        this.k = taskStatus;
        this.l = 1;
        ParamChecker.pmdCheck(cb);
        String str = option == null ? APConstants.DEFAULT_BUSINESS : TextUtils.isEmpty(option.bizType) ? option.businessId : option.bizType;
        this.n = str;
    }

    private int b() {
        if (this.g != null) {
            return 1;
        }
        if (this.e != null && this.e.startsWith("mm:")) {
            return 3;
        }
        if (PathUtils.isContentFile(this.e)) {
            return 4;
        }
        if (!TextUtils.isEmpty(this.e)) {
            String path = PathUtils.extractPath(this.e);
            return (!PathUtils.isLocalFile(path) || !FileUtils.checkFile(path)) ? 2 : 0;
        }
        throw new IllegalArgumentException("Invalid input source");
    }

    private int c() {
        int quality = 3;
        if (j()) {
            this.t.compressLevel = 3;
        } else {
            if (this.j == null || this.j.getQua() == null) {
                quality = e();
                o.d("quality: " + quality, new Object[0]);
            } else {
                switch (this.j.getQua()) {
                    case ORIGINAL:
                        quality = 3;
                        break;
                    case HIGH:
                        quality = 2;
                        break;
                    case MIDDLE:
                        quality = 1;
                        break;
                    case DEFAULT:
                        quality = e();
                        break;
                    default:
                        quality = 0;
                        break;
                }
                o.d("qua: " + this.j.getQua() + ", quality: " + quality, new Object[0]);
            }
            this.t.compressLevel = quality;
        }
        return quality;
    }

    private boolean a(String sourcePath) {
        try {
            this.h = File.createTempFile("image_up_", null);
            long start = System.currentTimeMillis();
            boolean ret = AESUtils.encryptFile(this.j.fileKey, sourcePath, this.h.getAbsolutePath());
            long cost = System.currentTimeMillis() - start;
            this.t.encryptTime = cost;
            o.d("ImageUpHandler", "encryptFile.sourcePath=" + sourcePath + ",fileKey=" + this.j.fileKey + ",ret=" + ret + ",cost=" + cost);
            return ret;
        } catch (IOException e2) {
            o.e("ImageUpHandler", "encrpytFile createTempFile failed.e=" + e2.getMessage());
            return false;
        }
    }

    private byte[] a(byte[] data) {
        if (data == null) {
            return null;
        }
        long start = System.currentTimeMillis();
        byte[] encryptData = AESUtils.encryptData(this.j.fileKey, data);
        long cost = System.currentTimeMillis() - start;
        this.t.encryptTime = cost;
        o.d("ImageUpHandler", "encryptData.fileKey=" + this.j.fileKey + ",cost=" + cost);
        return encryptData;
    }

    private int d() {
        ByteArrayOutputStream out;
        int cacheX;
        int cacheY;
        boolean isOriginal = 3 == this.q || j();
        if (this.l == 0) {
            o.d("before compress, size:" + this.f.length(), new Object[0]);
            this.t.originalSize = this.f.length();
        } else {
            o.d("before compress, size:" + this.h.length(), new Object[0]);
            this.t.originalSize = this.h.length();
        }
        if (!isOriginal) {
            int x = 0;
            int y = 0;
            if (this.j != null) {
                x = this.j.getImage_x();
                y = this.j.getImage_y();
            }
            long encodeStart = System.currentTimeMillis();
            if (this.l == 0) {
                out = compressImage(this.f, this.q, x, y);
            } else {
                out = compressImage(this.h, this.q, x, y);
                FileUtils.safeCopyToFile(out.toByteArray(), this.h);
            }
            this.t.encodeTime = System.currentTimeMillis() - encodeStart;
            byte[] data = out.toByteArray();
            if (!TextUtils.isEmpty(this.j.fileKey)) {
                data = a(data);
                if (data == null) {
                    throw new EncryptException();
                }
            }
            this.d = (long) data.length;
            if (this.h == null) {
                this.h = a(this.q, data, this.e);
            }
            this.mLocalId = MD5Util.getFileMD5String(this.h);
            if (TextUtils.isEmpty(this.j.fileKey)) {
                String cacheKey = TextUtils.isEmpty(this.e) ? this.mLocalId : this.e;
                if (x < 0 || x == 1280) {
                    cacheX = 0;
                } else {
                    cacheX = x;
                }
                if (y < 0 || y == 1280) {
                    cacheY = 0;
                } else {
                    cacheY = y;
                }
                o.d("compressAndGenImage mFilePath: " + this.e + ", mFileData: " + this.g + ", saved: " + ImageDiskCache.get().saveData(new BitmapCacheKey(cacheKey, cacheX, cacheY, this.c, (ImageWorkerPlugin) null, -1, (APImageMarkRequest) null), data, this.j.businessId), new Object[0]);
            }
        } else if (this.l == 0) {
            if (!TextUtils.isEmpty(this.j.fileKey)) {
                if (a(this.f.getAbsolutePath())) {
                    this.d = this.h.length();
                } else {
                    throw new EncryptException();
                }
            } else {
                this.h = this.f;
                this.d = this.f.length();
            }
            a(this.q, this.h, this.e);
        } else if (this.l == 4) {
            a(this.q, this.h, this.e);
        } else {
            this.d = this.h.length();
            a(this.q, this.h, this.mLocalId);
        }
        this.d = this.h.length();
        this.k.setTotalSize(this.d);
        this.t.size = this.d;
        o.d("after compressed, toUpFile: " + this.h + ", size：" + this.d + ", isOriginal: " + isOriginal, new Object[0]);
        if (TextUtils.isEmpty(this.mLocalId)) {
            if (j()) {
                this.mLocalId = MD5Util.getMD5String(MD5Util.getFileMD5String(this.h) + this.e);
                o.d("after compressed, mLocalId: " + this.mLocalId + ", mFilePath：" + this.e, new Object[0]);
            } else {
                this.mLocalId = MD5Util.getFileMD5String(this.h);
            }
            if (1 == this.l) {
                ImageDiskCache.get().saveData(new OriginalBitmapCacheKey(this.mLocalId, false), this.g, this.j.businessId);
            }
        }
        o.d("calc md5，for rapid transfer...md5: " + this.mLocalId, new Object[0]);
        return this.q;
    }

    private int e() {
        if (!NetworkUtils.isNetworkAvailable(this.mContext) || NetworkUtils.isWiFiMobileNetwork(this.mContext) || NetworkUtils.is4GMobileNetwork(this.mContext)) {
            return 2;
        }
        return 0;
    }

    private boolean a(APImageUploadRsp uploadRsp) {
        o.d("upload image check param..", new Object[0]);
        switch (this.l) {
            case 0:
                return b(uploadRsp);
            case 1:
                return c(uploadRsp);
            case 2:
                return d(uploadRsp);
            case 3:
                return f(uploadRsp);
            case 4:
                return e(uploadRsp);
            default:
                return g(uploadRsp);
        }
    }

    private boolean b(APImageUploadRsp uploadRsp) {
        APImageRetMsg retMsg = uploadRsp.getRetmsg();
        if (this.e == null) {
            this.m = RETCODE.PARAM_ERROR;
            retMsg.setMsg("imagePath isn't set..");
            o.d("path isn't set", new Object[0]);
            a(retMsg, (Exception) null);
            return false;
        }
        if ("file".equalsIgnoreCase(Uri.parse(this.e).getScheme())) {
            this.e = PathUtils.extractPath(this.e);
        }
        this.f = new File(this.e);
        if (this.f.exists() && this.f.isFile() && this.f.length() > 0) {
            return true;
        }
        this.m = RETCODE.FILE_NOT_EXIST;
        retMsg.setMsg(this.e + " isn't exist or file");
        o.d(this.e + " isn't exist or file", new Object[0]);
        a(retMsg, (Exception) null);
        return false;
    }

    private boolean c(APImageUploadRsp uploadRsp) {
        APImageRetMsg retMsg = uploadRsp.getRetmsg();
        if (this.g != null && this.g.length > 0) {
            return true;
        }
        this.m = RETCODE.PARAM_ERROR;
        retMsg.setMsg("fileData is null..");
        o.d("fileData is null", new Object[0]);
        a(retMsg, (Exception) null);
        return false;
    }

    private boolean d(APImageUploadRsp uploadRsp) {
        APImageRetMsg retMsg = uploadRsp.getRetmsg();
        if (a(this.e, this.q) != null) {
            return true;
        }
        this.m = RETCODE.FILE_NOT_EXIST;
        retMsg.setMsg(this.e + " is not exist..");
        o.d(this.e + " not exist..", new Object[0]);
        a(retMsg, (Exception) null);
        return false;
    }

    private boolean e(APImageUploadRsp uploadRsp) {
        APImageRetMsg retMsg = uploadRsp.getRetmsg();
        if (SandboxWrapper.checkFileExist(this.e)) {
            return true;
        }
        this.m = RETCODE.FILE_NOT_EXIST;
        retMsg.setMsg(this.e + " is not exist..");
        o.d(this.e + " not exist..", new Object[0]);
        a(retMsg, (Exception) null);
        return false;
    }

    private boolean f(APImageUploadRsp uploadRsp) {
        APImageRetMsg retMsg = uploadRsp.getRetmsg();
        if (FileUtils.checkFile(ImageDiskCache.get().genPathByKey(this.e))) {
            return true;
        }
        this.m = RETCODE.FILE_NOT_EXIST;
        retMsg.setMsg(this.e + " is not exist..");
        o.d(this.e + " not exist..", new Object[0]);
        a(retMsg, (Exception) null);
        return false;
    }

    private boolean g(APImageUploadRsp uploadRsp) {
        APImageRetMsg retMsg = uploadRsp.getRetmsg();
        o.d("unknown upload type..", new Object[0]);
        this.m = RETCODE.PARAM_ERROR;
        retMsg.setMsg("unknown upload type..");
        a(retMsg, (Exception) null);
        return false;
    }

    /* access modifiers changed from: private */
    public void a(APImageRetMsg retMsg, Exception e2) {
        this.k.setStatus(3);
        removeNetTaskTag(this.e);
        APMultimediaTaskManager.getInstance(this.mContext).updateTaskRecord(this.k);
        APImageUploadRsp rsp = new APImageUploadRsp();
        retMsg.setCode(this.m);
        rsp.setRetmsg(retMsg);
        rsp.setTaskStatus(this.k);
        if (this.i == null) {
            this.i = getImageManager().getUpTaskCallback(this.k.getTaskId());
        }
        o.d("uphandler onError mCode=" + this.m + ";retMsg=" + retMsg, new Object[0]);
        if (this.i != null) {
            for (APImageUploadCallback cb : this.i.keySet()) {
                try {
                    cb.onError(rsp, e2);
                } catch (Exception e1) {
                    o.e(e1, "onError callback exp", new Object[0]);
                }
            }
        }
        l();
        removeUploadCallBack(this.k.getTaskId());
    }

    /* access modifiers changed from: private */
    public void h(APImageUploadRsp uploadRsp) {
        this.k.setStatus(4);
        removeNetTaskTag(this.e);
        if (TextUtils.isEmpty(this.mGifFId) || this.mGifFId.length() != 32) {
            this.k.setCloudId(this.mCloudId);
        } else {
            this.k.setCloudId(this.mCloudId + MergeUtil.SEPARATOR_KV + this.mGifFId);
        }
        uploadRsp.setTaskStatus(this.k);
        this.k.setTotalSize(this.d);
        APMultimediaTaskManager.getInstance(this.mContext).updateTaskRecord(this.k);
        i(uploadRsp);
        this.i = getImageManager().getUpTaskCallback(this.k.getTaskId());
        this.m = RETCODE.SUC;
        if (this.i != null) {
            uploadRsp.getRetmsg().setCode(this.m);
            o.d("uphandler onSuccess callbacks size=" + this.i.size(), new Object[0]);
            for (APImageUploadCallback cb : this.i.keySet()) {
                try {
                    cb.onSuccess(uploadRsp);
                } catch (Exception e2) {
                    o.e(e2, "onSuccess callback exp", new Object[0]);
                }
            }
        }
        removeUploadCallBack(this.k.getTaskId());
        removeTaskModel(this.k.getTaskId());
        l();
        o.d("uphandler onSuccess end mCloudId=" + this.mCloudId + ";taskid=" + this.k.getTaskId() + "，rsp: " + uploadRsp, new Object[0]);
    }

    private void i(APImageUploadRsp uploadRsp) {
        if (this.j.setPublic != null && this.j.setPublic.booleanValue()) {
            Request request = new Request(1);
            request.isPublic = true;
            request.preferHttps = true;
            request.params = new HashMap();
            request.params.put("zoom", "original");
            try {
                uploadRsp.setPublicUrl(UriFactory.buildGetUrl(this.mCloudId, request));
            } catch (Exception e2) {
                o.e(e2, "checkAndFillPublicUrl error, " + uploadRsp, new Object[0]);
            }
        }
    }

    /* access modifiers changed from: private */
    public void f() {
        if (this.i == null) {
            this.i = getImageManager().getUpTaskCallback(this.k.getTaskId());
        }
        if (this.i != null) {
            for (APImageUploadCallback onStartUpload : this.i.keySet()) {
                onStartUpload.onStartUpload(this.k);
            }
        }
    }

    /* access modifiers changed from: private */
    public int a(long cSize, long tSize, Integer progress, boolean real) {
        int progressValue;
        if (tSize <= 0 || progress != null) {
            progressValue = progress.intValue();
        } else {
            progressValue = (int) ((((float) cSize) * 100.0f) / ((float) tSize));
        }
        if (progressValue > 0 && real) {
            l();
        }
        if (this.r.get() < progressValue || !real) {
            this.r.set(progressValue);
            this.k.setCurrentSize(cSize);
            this.k.setTotalSize(tSize);
            o.d("已上传：" + cSize + "/" + tSize + ",progress=" + this.r.get() + ";progressValue=" + progressValue + ";real=" + real, new Object[0]);
            g();
            if (real) {
                this.t.rapid = 0;
            }
        }
        return progressValue;
    }

    private void g() {
        APMultimediaTaskManager.getInstance(this.mContext).updateTaskRecord(this.k);
        if (this.i == null) {
            this.i = getImageManager().getUpTaskCallback(this.k.getTaskId());
        }
        if (this.i != null) {
            for (APImageUploadCallback onProcess : this.i.keySet()) {
                onProcess.onProcess(this.k, this.r.get());
            }
        }
    }

    /* JADX INFO: finally extract failed */
    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void h() {
        /*
            r9 = this;
            r8 = 0
            byte[] r5 = r9.g     // Catch:{ Exception -> 0x007a }
            if (r5 == 0) goto L_0x0047
            java.lang.String r6 = "image_up_"
            byte[] r5 = r9.g     // Catch:{ Exception -> 0x007a }
            int r5 = com.alipay.multimedia.img.utils.ImageFileType.detectImageDataType(r5)     // Catch:{ Exception -> 0x007a }
            if (r5 != 0) goto L_0x0044
            java.lang.String r5 = ".jpg"
        L_0x0011:
            java.io.File r5 = java.io.File.createTempFile(r6, r5)     // Catch:{ Exception -> 0x007a }
            r9.h = r5     // Catch:{ Exception -> 0x007a }
            byte[] r5 = r9.g     // Catch:{ Exception -> 0x007a }
            java.io.File r6 = r9.h     // Catch:{ Exception -> 0x007a }
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.FileUtils.safeCopyToFile(r5, r6)     // Catch:{ Exception -> 0x007a }
        L_0x001e:
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger r5 = o     // Catch:{ Exception -> 0x007a }
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x007a }
            java.lang.String r7 = "checkAndGenerateUploadFile, type: "
            r6.<init>(r7)     // Catch:{ Exception -> 0x007a }
            int r7 = r9.l     // Catch:{ Exception -> 0x007a }
            java.lang.StringBuilder r6 = r6.append(r7)     // Catch:{ Exception -> 0x007a }
            java.lang.String r7 = ", file: "
            java.lang.StringBuilder r6 = r6.append(r7)     // Catch:{ Exception -> 0x007a }
            java.io.File r7 = r9.h     // Catch:{ Exception -> 0x007a }
            java.lang.StringBuilder r6 = r6.append(r7)     // Catch:{ Exception -> 0x007a }
            java.lang.String r6 = r6.toString()     // Catch:{ Exception -> 0x007a }
            r7 = 0
            java.lang.Object[] r7 = new java.lang.Object[r7]     // Catch:{ Exception -> 0x007a }
            r5.d(r6, r7)     // Catch:{ Exception -> 0x007a }
        L_0x0043:
            return
        L_0x0044:
            java.lang.String r5 = ".png"
            goto L_0x0011
        L_0x0047:
            r5 = 4
            int r6 = r9.l     // Catch:{ Exception -> 0x007a }
            if (r5 != r6) goto L_0x009c
            java.lang.String r6 = "image_up_"
            java.lang.String r5 = r9.e     // Catch:{ Exception -> 0x007a }
            android.net.Uri r5 = android.net.Uri.parse(r5)     // Catch:{ Exception -> 0x007a }
            int r5 = com.alipay.android.phone.mobilecommon.multimediabiz.biz.adjuster.SandboxWrapper.getImageHeaderType(r5)     // Catch:{ Exception -> 0x007a }
            boolean r5 = com.alipay.multimedia.img.utils.ImageFileType.isJPEG(r5)     // Catch:{ Exception -> 0x007a }
            if (r5 == 0) goto L_0x0085
            java.lang.String r5 = ".jpg"
        L_0x0060:
            java.io.File r5 = java.io.File.createTempFile(r6, r5)     // Catch:{ Exception -> 0x007a }
            r9.h = r5     // Catch:{ Exception -> 0x007a }
            r4 = 0
            java.lang.String r5 = r9.e     // Catch:{ IOException -> 0x0088 }
            android.net.Uri r5 = android.net.Uri.parse(r5)     // Catch:{ IOException -> 0x0088 }
            java.io.InputStream r4 = com.alipay.android.phone.mobilecommon.multimediabiz.biz.adjuster.SandboxWrapper.openContentResolverInputStream(r5)     // Catch:{ IOException -> 0x0088 }
            java.io.File r5 = r9.h     // Catch:{ IOException -> 0x0088 }
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.FileUtils.safeCopyToFile(r4, r5)     // Catch:{ IOException -> 0x0088 }
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.io.IOUtils.closeQuietly(r4)     // Catch:{ Exception -> 0x007a }
            goto L_0x001e
        L_0x007a:
            r2 = move-exception
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger r5 = o
            java.lang.String r6 = "checkAndGenerateUploadFile error "
            java.lang.Object[] r7 = new java.lang.Object[r8]
            r5.e(r2, r6, r7)
            goto L_0x0043
        L_0x0085:
            java.lang.String r5 = ".png"
            goto L_0x0060
        L_0x0088:
            r3 = move-exception
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger r5 = o     // Catch:{ all -> 0x0097 }
            java.lang.String r6 = "checkAndGenerateUploadFile error "
            r7 = 0
            java.lang.Object[] r7 = new java.lang.Object[r7]     // Catch:{ all -> 0x0097 }
            r5.e(r3, r6, r7)     // Catch:{ all -> 0x0097 }
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.io.IOUtils.closeQuietly(r4)     // Catch:{ Exception -> 0x007a }
            goto L_0x001e
        L_0x0097:
            r5 = move-exception
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.io.IOUtils.closeQuietly(r4)     // Catch:{ Exception -> 0x007a }
            throw r5     // Catch:{ Exception -> 0x007a }
        L_0x009c:
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.assist.ImageDiskCache r5 = com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.assist.ImageDiskCache.get()     // Catch:{ Exception -> 0x007a }
            java.lang.String r6 = r9.e     // Catch:{ Exception -> 0x007a }
            java.lang.String r1 = r5.genPathByKey(r6)     // Catch:{ Exception -> 0x007a }
            boolean r5 = com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.FileUtils.checkFile(r1)     // Catch:{ Exception -> 0x007a }
            if (r5 == 0) goto L_0x001e
            java.io.File r0 = new java.io.File     // Catch:{ Exception -> 0x007a }
            r0.<init>(r1)     // Catch:{ Exception -> 0x007a }
            java.lang.String r6 = "image_up_"
            boolean r5 = com.alipay.multimedia.img.utils.ImageFileType.isJPEG(r0)     // Catch:{ Exception -> 0x007a }
            if (r5 == 0) goto L_0x00c8
            java.lang.String r5 = ".jpg"
        L_0x00bb:
            java.io.File r5 = java.io.File.createTempFile(r6, r5)     // Catch:{ Exception -> 0x007a }
            r9.h = r5     // Catch:{ Exception -> 0x007a }
            java.io.File r5 = r9.h     // Catch:{ Exception -> 0x007a }
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.FileUtils.safeCopyToFile(r0, r5)     // Catch:{ Exception -> 0x007a }
            goto L_0x001e
        L_0x00c8:
            java.lang.String r5 = ".png"
            goto L_0x00bb
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.ImageUpHandler.h():void");
    }

    public APImageUploadRsp upImage() {
        o.d("start up image... upType: " + this.l + ";filepath=" + this.e, new Object[0]);
        long start = System.currentTimeMillis();
        this.k.setStatus(1);
        APImageUploadRsp uploadRsp = new APImageUploadRsp();
        APImageRetMsg retMsg = new APImageRetMsg();
        uploadRsp.setRetmsg(retMsg);
        uploadRsp.setTaskStatus(this.k);
        String exp = "";
        this.q = c();
        if (!a(uploadRsp)) {
            a(String.valueOf(this.m.ordinal()), start, exp, (String) null);
        } else {
            putNetTaskTag(this.e, this.e);
            switch (this.l) {
                case 0:
                    break;
                case 1:
                case 3:
                case 4:
                    h();
                    break;
                case 2:
                    o.p("upImage with localId: " + this.e, new Object[0]);
                    this.k.setCacheId(this.e);
                    this.q = c();
                    long searchStart = System.currentTimeMillis();
                    this.h = a(this.e, this.q);
                    this.t.searchCacheTime = System.currentTimeMillis() - searchStart;
                    o.p("upImage with localId: " + this.e + ", path: " + this.h + ", co: " + this.q, new Object[0]);
                    a(uploadRsp, retMsg, exp);
                    break;
            }
            boolean bCompOk = true;
            try {
                d();
                o.d("after compressed, size=" + this.d + ";md5=" + this.mLocalId, new Object[0]);
                UCLogUtil.UC_MM_C07(0, this.d, (int) (System.currentTimeMillis() - start), 0);
                UCLogUtil.UC_MM_BIZ_UNAVAILBLE(LogUnAvailbleItem.SUB_CP_PIC, "0");
            } catch (EncryptException e2) {
                o.e(e2, "encrypt for uploading failed", new Object[0]);
                this.m = RETCODE.ENCRYPT_FAILED;
                exp = e2.getMessage();
                retMsg.setMsg("encypt for uploading failed");
                bCompOk = false;
                a(retMsg, (Exception) e2);
                UCLogUtil.UC_MM_C07(1, this.d, (int) (System.currentTimeMillis() - start), 0);
                UCLogUtil.UC_MM_BIZ_UNAVAILBLE(LogUnAvailbleItem.SUB_CP_PIC, String.valueOf(this.m.value()));
            } catch (Exception e3) {
                o.e(e3, "compress for uploading failed", new Object[0]);
                this.m = RETCODE.COMPRESS_ERROR;
                exp = e3.getMessage();
                retMsg.setMsg("compress for uploading failed");
                bCompOk = false;
                a(retMsg, e3);
                UCLogUtil.UC_MM_C07(1, this.d, (int) (System.currentTimeMillis() - start), 0);
                UCLogUtil.UC_MM_BIZ_UNAVAILBLE(LogUnAvailbleItem.SUB_CP_PIC, String.valueOf(this.m.value()));
            } catch (Throwable th) {
                Throwable th2 = th;
                UCLogUtil.UC_MM_C07(bCompOk ? 0 : 1, this.d, (int) (System.currentTimeMillis() - start), 0);
                UCLogUtil.UC_MM_BIZ_UNAVAILBLE(LogUnAvailbleItem.SUB_CP_PIC, bCompOk ? "0" : String.valueOf(this.m.value()));
                throw th2;
            }
            if (!bCompOk) {
                a(String.valueOf(this.m.ordinal()), start, exp, (String) null);
            } else {
                a(uploadRsp, retMsg, exp);
            }
        }
        return uploadRsp;
    }

    private void a(APImageUploadRsp uploadRsp, APImageRetMsg retMsg, String exp) {
        boolean z = false;
        this.u = CommonUtils.isActiveNetwork(this.mContext);
        if (this.u || !CommonUtils.isNeedCheckActiveNet(false)) {
            k();
            if (!TextUtils.isEmpty(this.j.fileKey)) {
                z = true;
            }
            if (z || NBNetUtils.getNBNetUPSwitch(this.j.businessId) || j()) {
                b(uploadRsp, retMsg, exp);
            } else {
                c(uploadRsp, retMsg, exp);
            }
        } else {
            this.m = RETCODE.INVALID_NETWORK;
            retMsg.setMsg("network isn't ok");
            o.d("network isn't ok", new Object[0]);
            a(retMsg, (Exception) null);
        }
    }

    private void b(APImageUploadRsp uploadRsp, APImageRetMsg retMsg, String exp) {
        this.t.netMethod = 2;
        long start = System.currentTimeMillis();
        StringBuilder traceId = new StringBuilder();
        StringBuilder errorCode = new StringBuilder();
        NBNetUploadResponse nbnetRsp = null;
        o.d("uploadFileByNBNet start toUploadFile=" + this.h.getPath(), new Object[0]);
        try {
            nbnetRsp = b(this.h);
            boolean fromCache = nbnetRsp != null;
            if (nbnetRsp == null) {
                NBNetUploadClient nbnetUpClient = NBNetUtils.getNBNetUploadClient();
                if (nbnetUpClient == null) {
                    throw new RuntimeException("uploadClient can not be null");
                }
                FutureTask<NBNetUploadResponse> addUploadTask = nbnetUpClient.addUploadTask(i());
                try {
                    if (this.v > 0) {
                        nbnetRsp = addUploadTask.get((long) this.v, TimeUnit.SECONDS);
                    } else {
                        nbnetRsp = addUploadTask.get();
                    }
                } catch (TimeoutException e2) {
                    this.m = RETCODE.TIME_OUT;
                    nbnetRsp = null;
                } catch (Throwable th) {
                    nbnetRsp = null;
                }
            }
            this.t.rapid = fromCache ? 1 : 0;
            a(nbnetRsp, uploadRsp, retMsg, traceId, errorCode, fromCache);
            String ret = String.valueOf(this.m.ordinal());
            String errorCodeString = errorCode.toString();
            if (!TextUtils.isEmpty(errorCodeString)) {
                ret = errorCodeString;
            }
            if (TextUtils.isEmpty(exp)) {
                exp = retMsg.getMsg();
            }
            this.t.netTime = System.currentTimeMillis() - start;
            this.t.retCode = this.m.ordinal();
            a(ret, start, exp, traceId.toString());
            o.d("uploadFileByNBNet end nbnetRsp=" + nbnetRsp, new Object[0]);
        } catch (Exception e3) {
            o.e(e3, "uploadFileByNBNet exp", new Object[0]);
            this.m = RETCODE.UNKNOWN_ERROR;
            exp = CommonUtils.getExceptionMsg(e3);
            retMsg.setMsg(exp);
            a(retMsg, (Exception) null);
            String ret2 = String.valueOf(this.m.ordinal());
            String errorCodeString2 = errorCode.toString();
            if (!TextUtils.isEmpty(errorCodeString2)) {
                ret2 = errorCodeString2;
            }
            if (TextUtils.isEmpty(exp)) {
                exp = retMsg.getMsg();
            }
            this.t.netTime = System.currentTimeMillis() - start;
            this.t.retCode = this.m.ordinal();
            a(ret2, start, exp, traceId.toString());
            o.d("uploadFileByNBNet end nbnetRsp=" + nbnetRsp, new Object[0]);
        } catch (Throwable th2) {
            String ret3 = String.valueOf(this.m.ordinal());
            String errorCodeString3 = errorCode.toString();
            if (!TextUtils.isEmpty(errorCodeString3)) {
                ret3 = errorCodeString3;
            }
            if (TextUtils.isEmpty(exp)) {
                exp = retMsg.getMsg();
            }
            this.t.netTime = System.currentTimeMillis() - start;
            this.t.retCode = this.m.ordinal();
            a(ret3, start, exp, traceId.toString());
            o.d("uploadFileByNBNet end nbnetRsp=" + nbnetRsp, new Object[0]);
            throw th2;
        }
    }

    private NBNetUploadRequest i() {
        NBNetUploadRequest req = new NBNetUploadRequest(this.h, this.n, (NBNetUploadCallback) new NBNetUploadCallback() {
            public void onUploadStart(NBNetUploadRequest req) {
                ImageUpHandler.this.f();
            }

            public void onUploadProgress(NBNetUploadRequest req, int progress, int fileLength, int uploadedFileLength) {
                ImageUpHandler.this.a((long) uploadedFileLength, (long) fileLength, Integer.valueOf(progress), true);
            }

            public void onUploadFinished(NBNetUploadRequest req, NBNetUploadResponse rsp) {
                ImageUpHandler.o.d("onUploadFinished cloudid=" + rsp.getFileId(), new Object[0]);
            }

            public void onUploadError(NBNetUploadRequest req, int code, String errorMessage) {
                ImageUpHandler.o.d("onUploadError code=" + code + ";errorMessage=" + errorMessage, new Object[0]);
            }
        });
        a(req);
        if (this.j.setPublic != null) {
            req.setPublicScope(this.j.setPublic.booleanValue());
        }
        if (!TextUtils.isEmpty(this.j.fileKeyToken)) {
            req.addHeader("fktoken", this.j.fileKeyToken);
        }
        if (!TextUtils.isEmpty(this.j.bizSessionID)) {
            req.addHeader("ssid", this.j.bizSessionID);
        }
        if (j()) {
            req.addHeader("x-afts-gif-fid", "1");
            GifConf conf = SimpleConfigProvider.get().getGifConfig();
            if (conf != null && conf.checkForceUpload()) {
                req.setForceUpload(true);
            }
        }
        o.d("ImageUpHandler", "createNBNetUpReq.fileKeyToken=" + this.j.fileKeyToken + ",bizSessionID=" + this.j.bizSessionID);
        if (this.i != null && !this.i.isEmpty()) {
            Object o2 = this.i.keySet().iterator().next();
            if (o2 != null) {
                o.d("add monitor log: " + o2.getClass().getName(), new Object[0]);
                req.setExtInfo(ExtInfoConstans.KEY_MULTIMEDIA_LOG_MARK, o2.getClass().getName());
            }
        }
        return req;
    }

    private void a(NBNetUploadRequest req) {
        String suffix = ".jpg";
        if (this.j == null || QUALITITY.ORIGINAL.equals(this.j.getQua())) {
            suffix = FileUtils.getFileType(this.e);
            if (TextUtils.isEmpty(suffix)) {
                suffix = FileUtils.getSuffix(this.e);
            }
        }
        if (j()) {
            suffix = ".gif";
        }
        req.setFileNameExt(FileUtils.getSuffixWithoutSeparator(suffix));
    }

    private boolean j() {
        return this.j != null && this.j.getFileType() == 1;
    }

    private void a(NBNetUploadResponse nbnetRsp, APImageUploadRsp rsp, APImageRetMsg retMsg, StringBuilder traceId, StringBuilder errorCode, boolean fromCache) {
        if (nbnetRsp == null) {
            o.d("handleNBNetUpRsp nbnetRsp is null", new Object[0]);
            if (this.m == RETCODE.TIME_OUT) {
                retMsg.setMsg("nbnetRsp is null by timeout");
            } else {
                this.m = RETCODE.UNKNOWN_ERROR;
                retMsg.setMsg("nbnetRsp is null");
            }
            a(retMsg, (Exception) null);
        } else if (nbnetRsp.isSuccess()) {
            this.mCloudId = nbnetRsp.getFileId();
            if (!TextUtils.isEmpty(nbnetRsp.getTraceId())) {
                traceId.append(nbnetRsp.getTraceId());
            }
            if (!TextUtils.isEmpty(this.mCloudId)) {
                if (nbnetRsp.getRespHeader() != null) {
                    this.mGifFId = nbnetRsp.getRespHeader().get("x-gif-fid");
                }
                if (!TextUtils.isEmpty(this.e)) {
                    if (j()) {
                        GifProcessor.relateCloudidToLocalgif(this.mCloudId, this.mGifFId, GifProcessor.getRelateGifSourcePath(this.e));
                    }
                    if (!TextUtils.isEmpty(this.j.fileKey)) {
                        ImageDiskCache.get().appendAliasKey(this.e + this.j.fileKey, this.mCloudId);
                    } else {
                        ImageDiskCache.get().appendAliasKey(this.e, this.mCloudId);
                    }
                } else {
                    ImageDiskCache.get().appendAliasKey(this.mLocalId, this.mCloudId);
                }
            }
            b(this.q, this.k.getCacheId());
            if (!fromCache) {
                a(nbnetRsp);
            }
            h(rsp);
        } else {
            if (!TextUtils.isEmpty(nbnetRsp.getTraceId())) {
                traceId.append(nbnetRsp.getTraceId());
            }
            if (nbnetRsp.getErrorCode() == 429) {
                this.m = RETCODE.CURRENT_LIMIT;
            } else {
                this.m = RETCODE.UPLOAD_ERROR;
            }
            errorCode.append(nbnetRsp.getErrorCode());
            retMsg.setMsg(nbnetRsp.getErrorMsg());
            a(retMsg, (Exception) null);
        }
    }

    private void c(APImageUploadRsp uploadRsp, APImageRetMsg retMsg, String exp) {
        String aliasFileName;
        this.t.netMethod = 1;
        long start = System.currentTimeMillis();
        final StringBuilder traceId = new StringBuilder();
        final StringBuilder fileErrorCode = new StringBuilder();
        try {
            String aliasFileName2 = this.h.getName();
            String imageType = APFileReq.FILE_TYPE_COMPRESS_IMAGE;
            if (this.j == null || QUALITITY.ORIGINAL.equals(this.j.getQua())) {
                imageType = "image";
                String suffix = FileUtils.getFileType(this.e);
                StringBuilder append = new StringBuilder().append(aliasFileName2);
                if (TextUtils.isEmpty(suffix)) {
                    suffix = FileUtils.getSuffix(this.e);
                }
                aliasFileName = append.append(suffix).toString();
            } else {
                aliasFileName = aliasFileName2 + ".jpg";
            }
            APFileReq upReq = new APFileReq();
            upReq.setSavePath(this.h.getAbsolutePath());
            upReq.setIsNeedCache(false);
            upReq.setType(imageType);
            upReq.setAliasFileName(aliasFileName);
            upReq.setBizType(this.n);
            upReq.setCallGroup(1001);
            upReq.setPublic(this.j.setPublic);
            upReq.setTimeout(this.v);
            this.d = this.h.length();
            final CountDownLatch waitLatch = new CountDownLatch(1);
            final APImageUploadRsp aPImageUploadRsp = uploadRsp;
            final APImageRetMsg aPImageRetMsg = retMsg;
            this.mFileService.upLoad(upReq, (APFileUploadCallback) new APFileUploadCallback() {
                public void onUploadStart(APMultimediaTaskModel taskInfo) {
                    ImageUpHandler.this.f();
                }

                public void onUploadProgress(APMultimediaTaskModel taskInfo, int progress, long hasUploadSize, long total) {
                    ImageUpHandler.this.a(hasUploadSize, total, (Integer) null, true);
                }

                public void onUploadFinished(APMultimediaTaskModel taskInfo, APFileUploadRsp rsp) {
                    ImageUpHandler.this.mCloudId = rsp.getFileReq().getCloudId();
                    if (!TextUtils.isEmpty(rsp.getTraceId())) {
                        traceId.append(rsp.getTraceId());
                    }
                    if (!TextUtils.isEmpty(ImageUpHandler.this.mCloudId)) {
                        if (!TextUtils.isEmpty(ImageUpHandler.this.e)) {
                            ImageDiskCache.get().appendAliasKey(ImageUpHandler.this.e, ImageUpHandler.this.mCloudId);
                        } else {
                            ImageDiskCache.get().appendAliasKey(ImageUpHandler.this.mLocalId, ImageUpHandler.this.mCloudId);
                        }
                    }
                    ImageUpHandler.this.b(ImageUpHandler.this.q, ImageUpHandler.this.k.getCacheId());
                    ImageUpHandler.this.h(aPImageUploadRsp);
                    waitLatch.countDown();
                }

                public void onUploadError(APMultimediaTaskModel taskInfo, APFileUploadRsp rsp) {
                    if (!TextUtils.isEmpty(rsp.getTraceId())) {
                        traceId.append(rsp.getTraceId());
                    }
                    fileErrorCode.append(rsp.getRetCode());
                    aPImageRetMsg.setMsg(rsp.getMsg());
                    if (rsp.getRetCode() == 2000) {
                        ImageUpHandler.this.m = RETCODE.CURRENT_LIMIT;
                    } else if (rsp.getRetCode() == 14) {
                        ImageUpHandler.this.m = RETCODE.TIME_OUT;
                    } else {
                        ImageUpHandler.this.m = RETCODE.UPLOAD_ERROR;
                    }
                    ImageUpHandler.this.a(aPImageRetMsg, (Exception) null);
                    waitLatch.countDown();
                }
            }, this.j.businessId);
            if (this.v > 0) {
                waitLatch.await((long) this.v, TimeUnit.SECONDS);
            } else {
                waitLatch.await();
            }
            String ret = String.valueOf(this.m.ordinal());
            String fileErrorCodeString = fileErrorCode.toString();
            if (!TextUtils.isEmpty(fileErrorCodeString)) {
                ret = "s" + fileErrorCodeString;
            }
            if (TextUtils.isEmpty(exp)) {
                exp = retMsg.getMsg();
            }
            this.t.netTime = System.currentTimeMillis() - start;
            this.t.retCode = this.m.ordinal();
            a(ret, start, exp, traceId.toString());
        } catch (Exception ex) {
            o.e(ex, "uploadFileInner exception", new Object[0]);
            if (ex instanceof TimeoutException) {
                this.m = RETCODE.TIME_OUT;
            } else {
                this.m = RETCODE.UNKNOWN_ERROR;
            }
            String exp2 = CommonUtils.getExceptionMsg(ex);
            retMsg.setMsg(exp2);
            a(retMsg, (Exception) null);
            String ret2 = String.valueOf(this.m.ordinal());
            String fileErrorCodeString2 = fileErrorCode.toString();
            if (!TextUtils.isEmpty(fileErrorCodeString2)) {
                ret2 = "s" + fileErrorCodeString2;
            }
            if (TextUtils.isEmpty(exp2)) {
                exp2 = retMsg.getMsg();
            }
            this.t.netTime = System.currentTimeMillis() - start;
            this.t.retCode = this.m.ordinal();
            a(ret2, start, exp2, traceId.toString());
        } catch (Throwable th) {
            String ret3 = String.valueOf(this.m.ordinal());
            String fileErrorCodeString3 = fileErrorCode.toString();
            if (!TextUtils.isEmpty(fileErrorCodeString3)) {
                ret3 = "s" + fileErrorCodeString3;
            }
            if (TextUtils.isEmpty(exp)) {
                exp = retMsg.getMsg();
            }
            this.t.netTime = System.currentTimeMillis() - start;
            this.t.retCode = this.m.ordinal();
            a(ret3, start, exp, traceId.toString());
            throw th;
        }
    }

    public Void call() {
        this.t.rapid = 1;
        upImage();
        m();
        return null;
    }

    private void a(String ret, long start, String exp, String traceId) {
        if (this.u || "0".equalsIgnoreCase(ret)) {
            int it = 1;
            if (this.j.getQua() == QUALITITY.ORIGINAL) {
                it = 2;
            }
            UCLogUtil.UC_MM_C01(ret, this.d, (int) (System.currentTimeMillis() - start), this.p, this.q, it, this.mLocalId, exp, traceId, this.n);
            this.t.md5 = this.mLocalId;
            this.t.traceId = traceId;
            this.t.exception = exp;
            this.t.imageType = it;
            this.t.biz = this.n;
            this.t.id = this.mCloudId;
            this.t.submitRemoteAsync();
        }
    }

    private void a(int quality, File src, String srcPath) {
        String targetName = a(quality, srcPath);
        try {
            FileUtils.safeCopyToFile(src, (File) new ZFile(this.mContext, FileUtils.GROUP_ID, targetName, "toUpload"));
            this.k.setCacheId(srcPath);
        } catch (IOException e2) {
            o.e(e2, "copyToUploadDir error: " + targetName, new Object[0]);
        }
    }

    private File a(int quality, byte[] src, String srcPath) {
        String targetName = a(quality, srcPath);
        ZFile dstFile = new ZFile(this.mContext, FileUtils.GROUP_ID, targetName, "toUpload");
        try {
            FileUtils.safeCopyToFile(src, (File) dstFile);
            this.k.setCacheId(srcPath);
        } catch (IOException e2) {
            o.e(e2, "copyToUploadDir error: " + targetName, new Object[0]);
        }
        return dstFile;
    }

    private File a(String path, int quality) {
        String path2 = PathUtils.extractPath(path);
        ZFile dstFile = new ZFile(this.mContext, FileUtils.GROUP_ID, a(quality, path2), "toUpload");
        if (!FileUtils.checkFile((File) dstFile)) {
            dstFile = null;
        }
        o.d("makeToUploadLocalFile " + path2 + ";quality=" + quality, new Object[0]);
        return dstFile;
    }

    private String a(int quality, String srcPath) {
        String path;
        if (!TextUtils.isEmpty(this.j.fileKey)) {
            path = MD5Util.getMD5String(srcPath + this.j.fileKey) + "_" + quality;
        } else {
            String path2 = MD5Util.getMD5String(srcPath);
            if (3 == quality) {
                path = path2 + "_" + quality + (j() ? ".gif" : FileUtils.getSuffix(srcPath));
            } else {
                path = path2 + ".jpg";
            }
        }
        o.d("makeLocalUploadFileName " + path + ";quality=" + quality + ";srcPath=" + srcPath, new Object[0]);
        return path;
    }

    /* access modifiers changed from: private */
    public boolean b(int quality, String srcPath) {
        File localFile = a(srcPath, quality);
        switch (quality) {
            case 3:
                return a(localFile);
            default:
                FileUtils.delete(localFile);
                return false;
        }
    }

    private boolean a(File localFile) {
        String filePath = this.e;
        if (!TextUtils.isEmpty(this.j.fileKey)) {
            filePath = filePath + this.j.fileKey;
        }
        if (!FileUtils.checkFile(localFile) || TextUtils.isEmpty(filePath)) {
            return false;
        }
        File cloudFile = new File(ImageDiskCache.get().genPathByKey(filePath));
        try {
            if (!FileUtils.safeCopyToFile(localFile, cloudFile)) {
                return false;
            }
            FileUtils.delete(localFile);
            return ImageDiskCache.get().savePath(new OriginalBitmapCacheKey(filePath, false), cloudFile.getAbsolutePath(), 128, this.j.businessId);
        } catch (IOException e2) {
            o.e(e2, "moveToImageCacheDir fail", new Object[0]);
            return false;
        }
    }

    private NBNetUploadResponse b(File file) {
        String md5;
        String str;
        NBNetUploadResponse rsp = null;
        String md52 = null;
        String fileMd5 = null;
        try {
            if (j()) {
                fileMd5 = MD5Util.getFileMD5String(file);
                md5 = MD5Util.getMD5String(fileMd5 + this.e);
                o.d("loadFromCache, md5: " + md5 + ", mFilePath：" + this.e, new Object[0]);
            } else {
                md5 = MD5Util.getFileMD5String(file);
            }
            md52 = b(md5);
            FileUpResp cache = UpCacheHelper.loadExistsResult(FileUpResp.class, md52);
            if (!(cache == null || cache.getFileInfo() == null)) {
                NBNetUploadResponse rsp2 = new NBNetUploadResponse();
                try {
                    rsp2.setFileId(cache.getFileInfo().getId());
                    if (TextUtils.isEmpty(fileMd5)) {
                        str = cache.getFileInfo().getMd5();
                    } else {
                        str = fileMd5;
                    }
                    rsp2.setMd5(str);
                    rsp2.setTraceId(cache.getTraceId());
                    rsp = rsp2;
                } catch (Exception e2) {
                    e = e2;
                    rsp = rsp2;
                    o.w("loadFromCache error: " + e, new Object[0]);
                    o.d("loadFromCache file: " + file + ", md5: " + md52 + ", rsp: " + rsp, new Object[0]);
                    return rsp;
                }
            }
        } catch (Exception e3) {
            e = e3;
        }
        o.d("loadFromCache file: " + file + ", md5: " + md52 + ", rsp: " + rsp, new Object[0]);
        return rsp;
    }

    private String b(String md5) {
        if (this.j.setPublic == null || !this.j.setPublic.booleanValue()) {
            return md5;
        }
        return md5 + "_pub";
    }

    private void a(NBNetUploadResponse rsp) {
        String md5;
        if (rsp != null && !TextUtils.isEmpty(rsp.getFileId()) && !TextUtils.isEmpty(rsp.getMd5())) {
            FileUpResp cache = new FileUpResp();
            DjangoFileInfoResp fileInfo = new DjangoFileInfoResp();
            cache.setFileInfo(fileInfo);
            if (!j() || TextUtils.isEmpty(this.mGifFId)) {
                fileInfo.setId(rsp.getFileId());
            } else {
                fileInfo.setId(rsp.getFileId() + MergeUtil.SEPARATOR_KV + this.mGifFId);
            }
            cache.setTraceId(rsp.getTraceId());
            if (j()) {
                md5 = b(MD5Util.getMD5String(MD5Util.getFileMD5String(this.h) + this.e));
                fileInfo.setMd5(md5);
                o.d("saveNBNetUpRspToCache, md5: " + md5 + ", mFilePath：" + this.e, new Object[0]);
            } else {
                fileInfo.setMd5(rsp.getMd5());
                md5 = b(rsp.getMd5());
            }
            UpCacheHelper.saveToLocal(cache, md5);
        }
        o.d("saveNBNetUpRspToCache rsp: " + rsp, new Object[0]);
    }

    private void k() {
        this.s = new ProgProcessor(ConfigManager.getInstance().getCommonConfigItem().progConf.imgUpProgMin, ConfigManager.getInstance().getCommonConfigItem().progConf.imgUpProgMax);
        this.s.setSwitch(ConfigManager.getInstance().getCommonConfigItem().progConf.isImageProgOn());
        this.s.setCallBack(this.w);
        this.s.startProgress();
    }

    private void l() {
        if (this.s != null) {
            this.s.removeProgressMessage();
        }
    }

    private void m() {
        if (this.s != null) {
            this.s.handlerQuit();
        }
    }

    public void setTimeOut(int timeout) {
        this.v = timeout;
    }
}
