package com.alipay.android.phone.mobilecommon.multimediabiz.biz.file;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.webkit.CookieManager;
import com.alipay.android.phone.mobilecommon.multimedia.api.data.APMultimediaTaskModel;
import com.alipay.android.phone.mobilecommon.multimedia.file.APFileDownCallback;
import com.alipay.android.phone.mobilecommon.multimedia.file.data.APFileDownloadRsp;
import com.alipay.android.phone.mobilecommon.multimedia.file.data.APFileReq;
import com.alipay.android.phone.mobilecommon.multimedia.file.data.APFileRsp;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.CacheContext;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.disc.CacheHitManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.io.DownloadResponseHelper;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.io.DownloadResponseHelper.FileHeader;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.io.DownloadResponseHelper.ReadBatchFileRespCallback;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.io.IOUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.io.TransferredListener;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.io.output.ProgressOutputStream;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.util.DjangoConstant;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.ConfigConstants;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.ConfigManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.file.valid.FileValidStrategy;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.file.valid.ImageValidStrategy;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.assist.AftsLinkHelper;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.io.RepeatableBufferedInputStream;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.manager.APFileTaskManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.task.MMTask;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.AppUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.CommonUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.DiskExpUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.FileUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.LogItem;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.NBNetUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.PathUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.UCLogUtil;
import com.alipay.android.phone.mobilesdk.storage.file.BaseFile;
import com.alipay.mobile.common.nbnet.api.ExtInfoConstans;
import com.alipay.mobile.common.nbnet.api.download.NBNetDownloadCallback;
import com.alipay.mobile.common.nbnet.api.download.NBNetDownloadClient;
import com.alipay.mobile.common.nbnet.api.download.NBNetDownloadRequest;
import com.alipay.mobile.common.nbnet.api.download.NBNetDownloadResponse;
import com.alipay.mobile.common.nbnet.api.download.proto.MMDPCmdType;
import com.alipay.mobile.common.nbnet.api.download.proto.MMDPExtraData;
import com.alipay.mobile.common.nbnet.api.download.proto.MMDPResType;
import com.alipay.mobile.common.nbnet.api.download.proto.MMDPSourceType;
import com.alipay.mobile.common.transport.Request;
import com.alipay.mobile.common.transport.Response;
import com.alipay.mobile.common.transport.TransportCallback;
import com.alipay.mobile.common.transport.download.DownloadRequest;
import com.alipay.mobile.common.transport.http.HttpUrlResponse;
import com.alipay.mobile.common.transport.monitor.RPCDataParser;
import com.alipay.mobile.common.transport.multimedia.DjgHttpUrlRequest;
import com.alipay.mobile.common.transport.utils.ReadSettingServerUrl;
import com.alipay.mobile.common.transport.utils.TransportConstants;
import com.alipay.mobile.common.utils.MD5Util;
import com.alipay.mobile.framework.service.common.DownloadService;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CancellationException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;

public class FileDownloadMMTask extends FileMMTask {
    public static final String PREFIX_FILE_DOWNLOAD = "file_dl_";
    /* access modifiers changed from: private */
    public static final Logger i = Logger.getLogger((String) "FileDownloadMMTask");
    private final String a;
    private final String b;
    private DownloadResponseHelper c;
    protected final Set<APFileDownCallback> callbacks = Collections.synchronizedSet(new HashSet());
    private Future<?> d;
    private NBNetDownloadClient e;
    private NBNetDownloadRequest f;
    private NBNetDownloadCallback g;
    private String h;
    /* access modifiers changed from: private */
    public int j;
    /* access modifiers changed from: private */
    public long k;
    /* access modifiers changed from: private */
    public long l = 0;
    /* access modifiers changed from: private */
    public int m = -1;
    private AtomicBoolean n = new AtomicBoolean(true);
    /* access modifiers changed from: private */
    public AtomicBoolean o = new AtomicBoolean(false);
    protected final Set<String> savePaths = Collections.synchronizedSet(new HashSet());

    private class HttpDownloadLister implements TransportCallback {
        private int b;

        private HttpDownloadLister() {
        }

        /* synthetic */ HttpDownloadLister(FileDownloadMMTask x0, byte b2) {
            this();
        }

        public void onCancelled(Request request) {
        }

        public void onPreExecute(Request request) {
        }

        public void onPostExecute(Request request, Response response) {
            FileDownloadMMTask.this.a(FileDownloadMMTask.this.taskInfo);
        }

        public void onProgressUpdate(Request request, double percent) {
            int progress = (int) (100.0d * percent);
            if (this.b != progress) {
                this.b = progress;
                FileDownloadMMTask.this.a(FileDownloadMMTask.this.taskInfo, this.b, -1, -1);
            }
        }

        public void onFailed(Request request, int i, String s) {
        }
    }

    public FileDownloadMMTask(Context context, List reqList, APMultimediaTaskModel taskInfo, APFileDownCallback callback) {
        super(context, reqList, taskInfo);
        registeFileDownloadCallback(callback);
        this.c = new DownloadResponseHelper();
        APFileReq req = (APFileReq) reqList.get(0);
        this.a = getSavePath(req);
        this.b = this.a + ".dltmp";
        taskInfo.setDestPath(this.b);
        if (req != null && reqList.size() == 1) {
            this.h = req.getMd5();
        }
        setTag("FileDownloadMMTask");
    }

    public static String generateTaskId(String cloudId) {
        return MD5Util.getMD5String(new StringBuilder(PREFIX_FILE_DOWNLOAD).append(cloudId).toString());
    }

    public static String generateTempFilePathByCloudId(String id) {
        return CacheContext.get().getDiskCache().genPathByKey(id) + ".dltmp";
    }

    public APFileDownloadRsp downloadSync(List reqList) {
        i.d("downloadSync start req size =  " + reqList.size() + ";cur thread id: " + Thread.currentThread().getId(), new Object[0]);
        APFileDownloadRsp rsp = new APFileDownloadRsp();
        try {
            checkCanceled();
            a(this.taskInfo);
            APFileReq req = (APFileReq) reqList.get(0);
            List noCacheList = a(reqList);
            if (noCacheList.isEmpty()) {
                rsp.setRetCode(0);
                rsp.setMsg("down complete from cache");
                rsp.setFileReq((APFileReq) reqList.get(0));
            } else if (checkFileCurrentLimit()) {
                rsp.setRetCode(2000);
                rsp.setMsg(ConfigConstants.MULTIMEDIA_NET_LIMIT_MSG);
            } else if (req.getForceUrl()) {
                a(req, rsp, false);
            } else if (PathUtils.isHttp(req.getCloudId())) {
                a(req, rsp, false);
            } else if (!req.isEncrypt() && ConfigManager.getInstance().getAftsLinkConf().isAftsFileSwitchOn(req.getBizType()) && PathUtils.checkIdForMdn(req.getCloudId())) {
                a(req, rsp, true);
            } else if (req.getBundle() != null || PathUtils.checkAftId(req.getCloudId()) || (NBNetUtils.getNBNetDLSwitch(req.businessId) && noCacheList.size() <= 1)) {
                a(noCacheList, rsp);
            } else {
                b(noCacheList, rsp);
            }
        } catch (RuntimeException e1) {
            if ("multimedia_file_task_canceled".equals(e1.getMessage())) {
                rsp.setRetCode(5);
                rsp.setMsg(e1.getMessage());
            } else {
                i.e(e1, "", new Object[0]);
                rsp.setRetCode(1);
                rsp.setMsg(e1.getMessage());
            }
        } catch (Exception e2) {
            i.e(e2, "", new Object[0]);
            rsp.setRetCode(1);
            rsp.setMsg(e2.getMessage());
        }
        if (isCanceled() || 2 == this.taskInfo.getStatus() || 5 == this.taskInfo.getStatus()) {
            rsp.setRetCode(5);
            rsp.setMsg("multimedia_file_task_canceled");
        }
        if (rsp.getFileReq() == null) {
            rsp.setFileReq((APFileReq) reqList.get(0));
        }
        if (!this.callbacks.isEmpty()) {
            if (rsp.getRetCode() == 0) {
                a(this.taskInfo, rsp);
            } else {
                rsp.getRetCode();
                b(this.taskInfo, rsp);
            }
        }
        return rsp;
    }

    private DownloadRequest a(String url, String savePath, TransportCallback callback, boolean bMdn) {
        DownloadRequest downloadReq = new DownloadRequest(url, savePath, null, null);
        downloadReq.setTransportCallback(callback);
        downloadReq.addTags("bizId", this.bizType);
        if (bMdn) {
            i.p("createDownloadRequest url=" + url + ";bMdn=" + bMdn, new Object[0]);
            downloadReq.addTags(TransportConstants.KEY_TARGET_SPI, TransportConstants.VALUE_TARGET_SPI);
            downloadReq.addTags(TransportConstants.KEY_OPERATION_TYPE, DjgHttpUrlRequest.OPERATION_TYPE);
            if (!ConfigManager.getInstance().getAftsLinkConf().checkNetRetrySwitch()) {
                i.p("setAllowRetryForErrorHttpStatusCode false", new Object[0]);
                downloadReq.setAllowRetryForErrorHttpStatusCode(false);
            }
        }
        return downloadReq;
    }

    private static boolean a(String fileId) {
        if (TextUtils.isEmpty(fileId) || !fileId.startsWith("A*")) {
            return false;
        }
        return true;
    }

    private void a(APFileReq req, APFileDownloadRsp downloadRsp, boolean bMdn) {
        Response rsp;
        i.d("requestHttpFile req: " + req + ", rsp: " + downloadRsp, new Object[0]);
        DownloadService downloadService = CommonUtils.getDownloadService();
        if (downloadService == null) {
            throw new RuntimeException("DownloadService can not be null");
        }
        boolean needAddCache = false;
        if (TextUtils.isEmpty(req.getSavePath())) {
            needAddCache = true;
            req.setSavePath(getCachePathByCloudId(req));
            i.d("savePath is empty, add genPath: " + req.getSavePath() + ";bMdn=" + bMdn, new Object[0]);
        }
        checkCanceled();
        String path = req.getCloudId();
        if (req.getForceUrl()) {
            path = req.getUrl();
        } else if (bMdn) {
            path = AftsLinkHelper.genFileDlAftsUrl(req.getCloudId(), req.getBizType());
            i.d("genFileDlAftsUrl is: " + path, new Object[0]);
        }
        DownloadRequest request = a(path, req.getSavePath(), (TransportCallback) new HttpDownloadLister(this, 0), bMdn);
        if (req.getForceUrl() && a(req.getCloudId())) {
            String cookie = CookieManager.getInstance().getCookie(ReadSettingServerUrl.getInstance().getGWFURL(AppUtils.getApplicationContext()));
            if (!com.alipay.android.phone.mobilecommon.multimediabiz.biz.http.util.TextUtils.isEmpty(cookie)) {
                request.addHeader("Cookie", cookie);
            }
        }
        this.d = downloadService.addDownload(request);
        long start = System.currentTimeMillis();
        String exp = null;
        long size = 0;
        int ret = -1;
        int expcode = -1;
        long timeout = (long) ConfigManager.getInstance().getCommonConfigItem().net.dsFileDownloadTimeOut;
        if (req.getTimeout() > 0) {
            timeout = (long) (req.getTimeout() * 1000);
        }
        if (timeout <= 0) {
            try {
                rsp = (Response) this.d.get();
            } catch (InterruptedException e2) {
                rsp = null;
                ret = 5;
                exp = CommonUtils.getExceptionMsg(e2);
            } catch (TimeoutException e3) {
                rsp = null;
                ret = 14;
                exp = "requestHttpFile timeout exp after " + timeout + RPCDataParser.TIME_MS;
            } catch (Exception e4) {
                rsp = null;
                ret = 1;
                exp = CommonUtils.getExceptionMsg(e4);
                expcode = DiskExpUtils.parseException(e4);
                if (429 == expcode) {
                    ret = 2000;
                    exp = ConfigConstants.MULTIMEDIA_NET_LIMIT_MSG;
                }
            } catch (Throwable th) {
                Throwable th2 = th;
                i.d("requestHttpFile finally downloadRsp: " + downloadRsp + ", req: " + req + ";bMdn=" + bMdn, new Object[0]);
                int time = (int) (System.currentTimeMillis() - start);
                String unm = bMdn ? "3" : "1";
                if (isNeedUCLog(req)) {
                    UCLogUtil.UC_MM_C06(String.valueOf(ret), size, time, 0, exp, "", req.getCloudId(), !bMdn, this.bizType, isNoNetwork(ret), unm);
                }
                a(ret, expcode, req.getCallGroup(), size, req.getCloudId(), this.bizType, unm, exp, req.isHttps(), isNoNetwork(ret));
                throw th2;
            }
        } else {
            rsp = (Response) this.d.get(timeout, TimeUnit.MILLISECONDS);
        }
        if (rsp != null) {
            HttpUrlResponse httpResponse = (HttpUrlResponse) rsp;
            if (httpResponse.getResData() != null) {
                size = (long) httpResponse.getResData().length;
            }
            boolean check = true;
            if (CommonUtils.checkDownloadServiceHttpCode(httpResponse.getCode())) {
                check = FileUtils.checkFileByMd5(this.h, req.getSavePath(), true);
                if (check) {
                    downloadRsp.setRetCode(0);
                    ret = 0;
                    if (req.isEncrypt() && !encryptFile(req)) {
                        ret = 13;
                        downloadRsp.setRetCode(13);
                        downloadRsp.setMsg("file encrypt error");
                    }
                    if (ret == 0 && needAddCache) {
                        addCacheFile(req);
                    }
                }
            }
            if (!check) {
                ret = 4;
                downloadRsp.setRetCode(4);
                downloadRsp.setMsg("Http invoker md5 error :4");
            } else {
                downloadRsp.setRetCode(httpResponse.getCode());
                downloadRsp.setMsg("Http invoker error :" + httpResponse.getCode());
                ret = 10;
            }
        } else if (2000 == ret) {
            downloadRsp.setRetCode(ret);
            downloadRsp.setMsg(ConfigConstants.MULTIMEDIA_NET_LIMIT_MSG);
        } else if (14 == ret) {
            downloadRsp.setRetCode(ret);
            downloadRsp.setMsg(exp);
        } else {
            downloadRsp.setRetCode(DjangoConstant.DJANGO_400);
            downloadRsp.setMsg("Http invoker error : " + exp);
        }
        i.d("requestHttpFile finally downloadRsp: " + downloadRsp + ", req: " + req + ";bMdn=" + bMdn, new Object[0]);
        int time2 = (int) (System.currentTimeMillis() - start);
        String unm2 = bMdn ? "3" : "1";
        if (isNeedUCLog(req)) {
            UCLogUtil.UC_MM_C06(String.valueOf(ret), size, time2, 0, exp, "", req.getCloudId(), !bMdn, this.bizType, isNoNetwork(ret), unm2);
        }
        a(ret, expcode, req.getCallGroup(), size, req.getCloudId(), this.bizType, unm2, exp, req.isHttps(), isNoNetwork(ret));
    }

    private void a(List reqList, APFileDownloadRsp rsp) {
        long start;
        NBNetDownloadResponse nbRsp;
        int expcode;
        int expcode2;
        int expcode3;
        int expcode4;
        int expcode5;
        i.d("requestNBNetDjangoFile start", new Object[0]);
        APFileReq req = (APFileReq) reqList.get(0);
        if (!PathUtils.checkDjangoId(req.getCloudId())) {
            throw new Exception("django id illegal: " + req.getCloudId());
        }
        a(req);
        start = System.currentTimeMillis();
        nbRsp = new NBNetDownloadResponse();
        try {
            this.e = NBNetUtils.getNBNetDownloadClient();
            if (this.e == null) {
                throw new RuntimeException("downloadClient can not be null");
            }
            this.g = new NBNetDownloadCallback() {
                public void onCancled(NBNetDownloadRequest nbNetReq) {
                    FileDownloadMMTask.i.d("requestNBNetDjangoFile onDownloadStart fileid=" + nbNetReq.getFileId(), new Object[0]);
                }

                public void onDownloadStart(NBNetDownloadRequest nbNetReq) {
                    FileDownloadMMTask.i.d("requestNBNetDjangoFile onDownloadStart fileid=" + nbNetReq.getFileId(), new Object[0]);
                    FileDownloadMMTask.this.a(FileDownloadMMTask.this.taskInfo);
                }

                public void onDownloadProgress(NBNetDownloadRequest nbNetReq, int progress, long received, long total) {
                    if (FileDownloadMMTask.this.m != progress) {
                        FileDownloadMMTask.this.m = progress;
                        FileDownloadMMTask.this.a(FileDownloadMMTask.this.taskInfo, progress, received, total);
                    }
                }

                public void onDownloadProgress(NBNetDownloadRequest request, int progress, long received, long total, File cacheFile) {
                    if (FileDownloadMMTask.this.m != progress) {
                        FileDownloadMMTask.this.m = progress;
                        FileDownloadMMTask.this.a(FileDownloadMMTask.this.taskInfo, progress, received, total);
                    }
                }

                public void onDownloadError(NBNetDownloadRequest nbNetReq, NBNetDownloadResponse nbNetRsp) {
                    FileDownloadMMTask.i.d("requestNBNetDjangoFile onDownloadError resp=" + (nbNetRsp == null ? "null" : nbNetRsp.toString()), new Object[0]);
                }

                public void onDownloadFinished(NBNetDownloadRequest nbNetReq, NBNetDownloadResponse nbNetRsp) {
                    FileDownloadMMTask.i.d("requestNBNetDjangoFile onDownloadFinished size=" + nbNetRsp.getDataLength(), new Object[0]);
                }
            };
            Future<NBNetDownloadResponse> requestDownload = this.e.requestDownload(this.f, this.g);
            try {
                long timeout = (long) ConfigManager.getInstance().getCommonConfigItem().net.dsFileDownloadTimeOut;
                if (req.getTimeout() > 0) {
                    timeout = (long) (req.getTimeout() * 1000);
                }
                if (timeout > 0) {
                    nbRsp = requestDownload.get(timeout, TimeUnit.MILLISECONDS);
                } else {
                    nbRsp = requestDownload.get();
                }
                long coast = System.currentTimeMillis() - start;
                Logger.TIME("requestNBNetDjangoFile get response costTime: " + coast, coast, new Object[0]);
                long size = a(req, nbRsp, rsp);
                int time = (int) (System.currentTimeMillis() - start);
                if (isNeedUCLog(req)) {
                    UCLogUtil.UC_MM_C06(String.valueOf(rsp.getRetCode()), size, time, 0, rsp.getMsg(), nbRsp == null ? "" : nbRsp.getTraceId(), req.getCloudId(), false, this.bizType, isNoNetwork(rsp.getRetCode()), "2");
                }
                if (nbRsp == null || nbRsp.getErrorCode() >= 0) {
                    expcode5 = -1;
                } else {
                    expcode5 = DiskExpUtils.parseException(nbRsp.getException());
                }
                a(rsp.getRetCode(), expcode5, req.getCallGroup(), size, req.getCloudId(), this.bizType, "2", rsp.getMsg(), req.isHttps(), isNoNetwork(rsp.getRetCode()));
                i.d("requestNBNetDjangoFile end retcode=" + rsp.getRetCode() + ";size=" + size + ";fileid=" + req.getCloudId() + ";tid=" + nbRsp, new Object[0]);
                return;
            } catch (TimeoutException e2) {
                rsp.setRetCode(14);
                rsp.setMsg(e2.getMessage());
                int time2 = (int) (System.currentTimeMillis() - start);
                if (isNeedUCLog(req)) {
                    UCLogUtil.UC_MM_C06(String.valueOf(rsp.getRetCode()), 0, time2, 0, rsp.getMsg(), nbRsp == null ? "" : nbRsp.getTraceId(), req.getCloudId(), false, this.bizType, isNoNetwork(rsp.getRetCode()), "2");
                }
                if (nbRsp == null || nbRsp.getErrorCode() >= 0) {
                    expcode4 = -1;
                } else {
                    expcode4 = DiskExpUtils.parseException(nbRsp.getException());
                }
                a(rsp.getRetCode(), expcode4, req.getCallGroup(), 0, req.getCloudId(), this.bizType, "2", rsp.getMsg(), req.isHttps(), isNoNetwork(rsp.getRetCode()));
                i.d("requestNBNetDjangoFile end retcode=" + rsp.getRetCode() + ";size=0;fileid=" + req.getCloudId() + ";tid=" + nbRsp, new Object[0]);
                return;
            } catch (InterruptedException e3) {
                rsp.setRetCode(5);
                rsp.setMsg(e3.getMessage());
                int time3 = (int) (System.currentTimeMillis() - start);
                if (isNeedUCLog(req)) {
                    UCLogUtil.UC_MM_C06(String.valueOf(rsp.getRetCode()), 0, time3, 0, rsp.getMsg(), nbRsp == null ? "" : nbRsp.getTraceId(), req.getCloudId(), false, this.bizType, isNoNetwork(rsp.getRetCode()), "2");
                }
                if (nbRsp == null || nbRsp.getErrorCode() >= 0) {
                    expcode3 = -1;
                } else {
                    expcode3 = DiskExpUtils.parseException(nbRsp.getException());
                }
                a(rsp.getRetCode(), expcode3, req.getCallGroup(), 0, req.getCloudId(), this.bizType, "2", rsp.getMsg(), req.isHttps(), isNoNetwork(rsp.getRetCode()));
                i.d("requestNBNetDjangoFile end retcode=" + rsp.getRetCode() + ";size=0;fileid=" + req.getCloudId() + ";tid=" + nbRsp, new Object[0]);
                return;
            } catch (CancellationException e4) {
                rsp.setRetCode(5);
                rsp.setMsg(e4.getMessage());
                int time4 = (int) (System.currentTimeMillis() - start);
                if (isNeedUCLog(req)) {
                    UCLogUtil.UC_MM_C06(String.valueOf(rsp.getRetCode()), 0, time4, 0, rsp.getMsg(), nbRsp == null ? "" : nbRsp.getTraceId(), req.getCloudId(), false, this.bizType, isNoNetwork(rsp.getRetCode()), "2");
                }
                if (nbRsp == null || nbRsp.getErrorCode() >= 0) {
                    expcode2 = -1;
                } else {
                    expcode2 = DiskExpUtils.parseException(nbRsp.getException());
                }
                a(rsp.getRetCode(), expcode2, req.getCallGroup(), 0, req.getCloudId(), this.bizType, "2", rsp.getMsg(), req.isHttps(), isNoNetwork(rsp.getRetCode()));
                i.d("requestNBNetDjangoFile end retcode=" + rsp.getRetCode() + ";size=0;fileid=" + req.getCloudId() + ";tid=" + nbRsp, new Object[0]);
                return;
            }
        } catch (Exception e5) {
            i.e(e5, "", new Object[0]);
            rsp.setRetCode(1);
            rsp.setMsg(CommonUtils.getExceptionMsg(e5));
            expcode = DiskExpUtils.parseException(e5);
            throw e5;
        } catch (Throwable th) {
            th = th;
        }
        int time5 = (int) (System.currentTimeMillis() - start);
        if (isNeedUCLog(req)) {
            UCLogUtil.UC_MM_C06(String.valueOf(rsp.getRetCode()), 0, time5, 0, rsp.getMsg(), nbRsp == null ? "" : nbRsp.getTraceId(), req.getCloudId(), false, this.bizType, isNoNetwork(rsp.getRetCode()), "2");
        }
        if (nbRsp != null && nbRsp.getErrorCode() < 0) {
            expcode = DiskExpUtils.parseException(nbRsp.getException());
        }
        a(rsp.getRetCode(), expcode, req.getCallGroup(), 0, req.getCloudId(), this.bizType, "2", rsp.getMsg(), req.isHttps(), isNoNetwork(rsp.getRetCode()));
        i.d("requestNBNetDjangoFile end retcode=" + rsp.getRetCode() + ";size=0;fileid=" + req.getCloudId() + ";tid=" + nbRsp, new Object[0]);
        throw th;
    }

    private NBNetDownloadRequest a(APFileReq req) {
        this.f = new NBNetDownloadRequest();
        this.f.setCmdType(MMDPCmdType.FILE_DOWNLOAD);
        this.f.setFileId(req.getCloudId());
        this.f.setSavePath(this.a);
        this.f.setSourceType(MMDPSourceType.FILEID);
        this.f.setBizType(this.bizType);
        this.f.setResType(MMDPResType.FILE);
        this.f.setReqTimeOut(NBNetUtils.getDwonloadFileConfigTimeout());
        Bundle bundle = req.getBundle();
        if (bundle != null) {
            List extList = new ArrayList(2);
            if (!TextUtils.isEmpty(bundle.getString("ssid"))) {
                MMDPExtraData data = new MMDPExtraData();
                data.name = "ssid";
                data.value = bundle.getString("ssid");
                extList.add(data);
            }
            if (!TextUtils.isEmpty(bundle.getString("refid"))) {
                MMDPExtraData data2 = new MMDPExtraData();
                data2.name = "refid";
                data2.value = bundle.getString("refid");
                extList.add(data2);
            }
            if (extList.size() > 0) {
                this.f.setExtList(extList);
            }
            i.d("createDownReq bizSession=" + bundle.getString("ssid") + ";refID=" + bundle.getString("refid"), new Object[0]);
        }
        if (this.requestCallBackClassName != null) {
            i.d("add monitor log: " + this.requestCallBackClassName, new Object[0]);
            this.f.setExtInfo(ExtInfoConstans.KEY_MULTIMEDIA_LOG_MARK, this.requestCallBackClassName);
        }
        return this.f;
    }

    private long a(APFileReq req, NBNetDownloadResponse nbRsp, APFileDownloadRsp rsp) {
        if (nbRsp == null) {
            rsp.setFileReq(req);
            rsp.setRetCode(2);
            rsp.setMsg("handleNBNetDownloadRsp null");
            i.d("handleNBNetDownloadRsp null", new Object[0]);
            return 0;
        }
        long size = 0;
        boolean check = true;
        if (nbRsp.isSuccess()) {
            check = FileUtils.checkFileByMd5(this.h, this.a, true);
            if (check) {
                size = nbRsp.getDataLength();
                i.i("savePath.length: " + size, new Object[0]);
                if (TextUtils.isEmpty(req.getSavePath())) {
                    req.setSavePath(this.a);
                }
                boolean encryptSuccess = true;
                if (req.isEncrypt()) {
                    encryptSuccess = encryptFile(req);
                    if (!encryptSuccess) {
                        rsp.setRetCode(13);
                        rsp.setMsg("file encrypt error");
                    }
                }
                if (encryptSuccess) {
                    if (req.isNeedCache()) {
                        addCacheFile(req);
                    }
                    this.taskInfo.setDestPath(req.getSavePath());
                    rsp.setRetCode(0);
                    rsp.setMsg("download file from nbnet success");
                }
                rsp.setFileReq(req);
                rsp.setTraceId(nbRsp.getTraceId());
                return size;
            }
        }
        if (!check) {
            rsp.setRetCode(4);
            rsp.setMsg("md5 not match");
        } else if (429 == nbRsp.getErrorCode()) {
            rsp.setRetCode(2000);
            rsp.setMsg(ConfigConstants.MULTIMEDIA_NET_LIMIT_MSG);
        } else {
            rsp.setRetCode(nbRsp.getErrorCode());
            rsp.setMsg(nbRsp.getErrorMsg());
        }
        rsp.setFileReq(req);
        rsp.setTraceId(nbRsp.getTraceId());
        return size;
    }

    /* JADX WARNING: Removed duplicated region for block: B:126:0x0600  */
    /* JADX WARNING: Removed duplicated region for block: B:79:0x0473 A[Catch:{ all -> 0x0485 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void b(java.util.List r49, com.alipay.android.phone.mobilecommon.multimedia.file.data.APFileDownloadRsp r50) {
        /*
            r48 = this;
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger r9 = i
            java.lang.String r10 = "requestDjangoFile start"
            r13 = 0
            java.lang.Object[] r13 = new java.lang.Object[r13]
            r9.d(r10, r13)
            r9 = 0
            r0 = r49
            java.lang.Object r39 = r0.get(r9)
            com.alipay.android.phone.mobilecommon.multimedia.file.data.APFileReq r39 = (com.alipay.android.phone.mobilecommon.multimedia.file.data.APFileReq) r39
            java.lang.String r9 = r39.getCloudId()
            boolean r9 = com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.PathUtils.checkDjangoId(r9)
            if (r9 != 0) goto L_0x0036
            java.lang.Exception r9 = new java.lang.Exception
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            java.lang.String r13 = "django id illegal: "
            r10.<init>(r13)
            java.lang.String r13 = r39.getCloudId()
            java.lang.StringBuilder r10 = r10.append(r13)
            java.lang.String r10 = r10.toString()
            r9.<init>(r10)
            throw r9
        L_0x0036:
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.module.req.FilesDownReq r36 = new com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.module.req.FilesDownReq
            java.lang.String r9 = "|"
            r0 = r49
            java.lang.String r9 = a(r9, r0)
            r0 = r36
            r0.<init>(r9)
            int r9 = r39.getTimeout()
            r0 = r36
            r0.mTimeout = r9
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger r9 = i
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            java.lang.String r13 = "getFileIds:  "
            r10.<init>(r13)
            java.lang.String r13 = r36.getFileIds()
            java.lang.StringBuilder r10 = r10.append(r13)
            java.lang.String r10 = r10.toString()
            r13 = 0
            java.lang.Object[] r13 = new java.lang.Object[r13]
            r9.i(r10, r13)
            com.alipay.android.phone.mobilecommon.multimedia.audio.data.APRequestParam r9 = r39.getRequestParam()
            r0 = r48
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.DjangoClient r32 = r0.getDjangoClient(r9)
            r38 = 0
            r37 = 0
            long r42 = java.lang.System.currentTimeMillis()
            r6 = 0
            r46 = 0
            r11 = 0
            r34 = 0
            r4 = 0
            r40 = 0
            r35 = -1
            r0 = r48
            java.util.concurrent.atomic.AtomicBoolean r9 = r0.o     // Catch:{ Exception -> 0x0454, all -> 0x060b }
            r10 = 0
            r9.set(r10)     // Catch:{ Exception -> 0x0454, all -> 0x060b }
            r48.checkCanceled()     // Catch:{ Exception -> 0x0454, all -> 0x060b }
            int r9 = r49.size()     // Catch:{ Exception -> 0x0454, all -> 0x060b }
            r10 = 1
            if (r9 > r10) goto L_0x012b
            java.io.File r30 = new java.io.File     // Catch:{ Exception -> 0x0454, all -> 0x060b }
            r0 = r48
            java.lang.String r9 = r0.b     // Catch:{ Exception -> 0x0454, all -> 0x060b }
            r0 = r30
            r0.<init>(r9)     // Catch:{ Exception -> 0x0454, all -> 0x060b }
            boolean r9 = r30.isFile()     // Catch:{ Exception -> 0x0454, all -> 0x060b }
            if (r9 == 0) goto L_0x012b
            boolean r9 = r30.exists()     // Catch:{ Exception -> 0x0454, all -> 0x060b }
            if (r9 == 0) goto L_0x012b
            long r14 = r30.length()     // Catch:{ Exception -> 0x0454, all -> 0x060b }
            r0 = r48
            r0.l = r14     // Catch:{ Exception -> 0x0454, all -> 0x060b }
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger r9 = i     // Catch:{ Exception -> 0x0454, all -> 0x060b }
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0454, all -> 0x060b }
            java.lang.String r13 = "requestDjangoFile rangeSize = "
            r10.<init>(r13)     // Catch:{ Exception -> 0x0454, all -> 0x060b }
            r0 = r48
            long r14 = r0.l     // Catch:{ Exception -> 0x0454, all -> 0x060b }
            java.lang.StringBuilder r10 = r10.append(r14)     // Catch:{ Exception -> 0x0454, all -> 0x060b }
            java.lang.String r10 = r10.toString()     // Catch:{ Exception -> 0x0454, all -> 0x060b }
            r13 = 0
            java.lang.Object[] r13 = new java.lang.Object[r13]     // Catch:{ Exception -> 0x0454, all -> 0x060b }
            r9.i(r10, r13)     // Catch:{ Exception -> 0x0454, all -> 0x060b }
            r0 = r48
            long r14 = r0.l     // Catch:{ Exception -> 0x0454, all -> 0x060b }
            r16 = 0
            int r9 = (r14 > r16 ? 1 : (r14 == r16 ? 0 : -1))
            if (r9 <= 0) goto L_0x012b
            r0 = r48
            long r14 = r0.l     // Catch:{ Exception -> 0x0454, all -> 0x060b }
            r16 = 2
            int r9 = (r14 > r16 ? 1 : (r14 == r16 ? 0 : -1))
            if (r9 <= 0) goto L_0x010d
            r0 = r48
            long r14 = r0.l     // Catch:{ Exception -> 0x0454, all -> 0x060b }
            r16 = 1
            long r14 = r14 - r16
            r0 = r48
            r0.l = r14     // Catch:{ Exception -> 0x0454, all -> 0x060b }
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger r9 = i     // Catch:{ Exception -> 0x0454, all -> 0x060b }
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0454, all -> 0x060b }
            java.lang.String r13 = "requestDjangoFile new rangeSize = "
            r10.<init>(r13)     // Catch:{ Exception -> 0x0454, all -> 0x060b }
            r0 = r48
            long r14 = r0.l     // Catch:{ Exception -> 0x0454, all -> 0x060b }
            java.lang.StringBuilder r10 = r10.append(r14)     // Catch:{ Exception -> 0x0454, all -> 0x060b }
            java.lang.String r10 = r10.toString()     // Catch:{ Exception -> 0x0454, all -> 0x060b }
            r13 = 0
            java.lang.Object[] r13 = new java.lang.Object[r13]     // Catch:{ Exception -> 0x0454, all -> 0x060b }
            r9.i(r10, r13)     // Catch:{ Exception -> 0x0454, all -> 0x060b }
        L_0x010d:
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0454, all -> 0x060b }
            java.lang.String r10 = "bytes="
            r9.<init>(r10)     // Catch:{ Exception -> 0x0454, all -> 0x060b }
            r0 = r48
            long r14 = r0.l     // Catch:{ Exception -> 0x0454, all -> 0x060b }
            java.lang.StringBuilder r9 = r9.append(r14)     // Catch:{ Exception -> 0x0454, all -> 0x060b }
            java.lang.String r10 = "-"
            java.lang.StringBuilder r9 = r9.append(r10)     // Catch:{ Exception -> 0x0454, all -> 0x060b }
            java.lang.String r9 = r9.toString()     // Catch:{ Exception -> 0x0454, all -> 0x060b }
            r0 = r36
            r0.setRange(r9)     // Catch:{ Exception -> 0x0454, all -> 0x060b }
        L_0x012b:
            boolean r9 = r39.getForceUrl()     // Catch:{ Exception -> 0x0454, all -> 0x060b }
            if (r9 == 0) goto L_0x0143
            boolean r9 = r39.getForceUrl()     // Catch:{ Exception -> 0x0454, all -> 0x060b }
            r0 = r36
            r0.setForceUrl(r9)     // Catch:{ Exception -> 0x0454, all -> 0x060b }
            java.lang.String r9 = r39.getUrl()     // Catch:{ Exception -> 0x0454, all -> 0x060b }
            r0 = r36
            r0.setSource(r9)     // Catch:{ Exception -> 0x0454, all -> 0x060b }
        L_0x0143:
            boolean r9 = r39.isHttps()     // Catch:{ Exception -> 0x0454, all -> 0x060b }
            r0 = r36
            r0.setbHttps(r9)     // Catch:{ Exception -> 0x0454, all -> 0x060b }
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.api.FileApi r9 = r32.getFileApi()     // Catch:{ Exception -> 0x0454, all -> 0x060b }
            r0 = r36
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.module.resp.FilesDownResp r37 = r9.downloadBatch(r0)     // Catch:{ Exception -> 0x0454, all -> 0x060b }
            if (r37 != 0) goto L_0x0205
            r9 = 2
            r0 = r50
            r0.setRetCode(r9)     // Catch:{ Exception -> 0x0454, all -> 0x060b }
            java.lang.String r9 = "filesDownResp null"
            r0 = r50
            r0.setMsg(r9)     // Catch:{ Exception -> 0x0454, all -> 0x060b }
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger r9 = i     // Catch:{ Exception -> 0x0454, all -> 0x060b }
            java.lang.String r10 = "downloadBatch null"
            r13 = 0
            java.lang.Object[] r13 = new java.lang.Object[r13]     // Catch:{ Exception -> 0x0454, all -> 0x060b }
            r9.d(r10, r13)     // Catch:{ Exception -> 0x0454, all -> 0x060b }
            r18 = r34
        L_0x0171:
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.io.IOUtils.closeQuietly(r38)
            if (r32 == 0) goto L_0x0180
            r0 = r32
            r1 = r37
            r0.release(r1)
            r36.abort()
        L_0x0180:
            int r9 = r50.getRetCode()
            java.lang.String r5 = java.lang.String.valueOf(r9)
            java.lang.String r12 = r39.getCloudId()
            if (r4 == 0) goto L_0x0192
            java.lang.String r5 = java.lang.String.valueOf(r40)
        L_0x0192:
            r0 = r50
            r0.setTraceId(r11)
            long r14 = java.lang.System.currentTimeMillis()
            long r14 = r14 - r42
            int r8 = (int) r14
            r0 = r48
            r1 = r39
            boolean r9 = r0.isNeedUCLog(r1)
            if (r9 == 0) goto L_0x01d9
            if (r4 == 0) goto L_0x05fd
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            java.lang.String r10 = "s"
            r9.<init>(r10)
            java.lang.StringBuilder r9 = r9.append(r5)
            java.lang.String r13 = r9.toString()
        L_0x01b9:
            r17 = 0
            r21 = 0
            r0 = r48
            java.lang.String r0 = r0.bizType
            r22 = r0
            int r9 = r50.getRetCode()
            r0 = r48
            boolean r23 = r0.isNoNetwork(r9)
            java.lang.String r24 = "1"
            r14 = r6
            r16 = r8
            r19 = r11
            r20 = r12
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.UCLogUtil.UC_MM_C06(r13, r14, r16, r17, r18, r19, r20, r21, r22, r23, r24)
        L_0x01d9:
            int r19 = r50.getRetCode()
            r20 = -1
            int r21 = r39.getCallGroup()
            java.lang.String r24 = r39.getCloudId()
            r0 = r48
            java.lang.String r0 = r0.bizType
            r25 = r0
            java.lang.String r26 = "1"
            boolean r28 = r39.isHttps()
            int r9 = r50.getRetCode()
            r0 = r48
            boolean r29 = r0.isNoNetwork(r9)
            r22 = r46
            r27 = r18
            a(r19, r20, r21, r22, r24, r25, r26, r27, r28, r29)
        L_0x0204:
            return
        L_0x0205:
            boolean r9 = r37.isSuccess()     // Catch:{ Exception -> 0x0454, all -> 0x060b }
            if (r9 == 0) goto L_0x053e
            java.lang.String r11 = r37.getTraceId()     // Catch:{ Exception -> 0x0454, all -> 0x060b }
            org.apache.http.HttpResponse r9 = r37.getResp()     // Catch:{ Exception -> 0x0454, all -> 0x060b }
            org.apache.http.HttpEntity r9 = r9.getEntity()     // Catch:{ Exception -> 0x0454, all -> 0x060b }
            long r6 = r9.getContentLength()     // Catch:{ Exception -> 0x0454, all -> 0x060b }
            r0 = r48
            long r14 = r0.l     // Catch:{ Exception -> 0x0454, all -> 0x060b }
            long r46 = r6 + r14
            org.apache.http.HttpResponse r9 = r37.getResp()     // Catch:{ Exception -> 0x0454, all -> 0x060b }
            boolean r9 = com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.util.HttpClientUtils.checkRspContentSizeAndType(r9)     // Catch:{ Exception -> 0x0454, all -> 0x060b }
            if (r9 != 0) goto L_0x02e1
            r9 = 6
            r0 = r50
            r0.setRetCode(r9)     // Catch:{ Exception -> 0x0454, all -> 0x060b }
            java.lang.String r9 = "Content size and type not match"
            r0 = r50
            r0.setMsg(r9)     // Catch:{ Exception -> 0x0454, all -> 0x060b }
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger r9 = i     // Catch:{ Exception -> 0x0454, all -> 0x060b }
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0454, all -> 0x060b }
            java.lang.String r13 = "requestDjangoFile checkRspContentSizeAndType fail size="
            r10.<init>(r13)     // Catch:{ Exception -> 0x0454, all -> 0x060b }
            java.lang.StringBuilder r10 = r10.append(r6)     // Catch:{ Exception -> 0x0454, all -> 0x060b }
            java.lang.String r13 = ";id="
            java.lang.StringBuilder r10 = r10.append(r13)     // Catch:{ Exception -> 0x0454, all -> 0x060b }
            java.lang.String r13 = r39.getCloudId()     // Catch:{ Exception -> 0x0454, all -> 0x060b }
            java.lang.StringBuilder r10 = r10.append(r13)     // Catch:{ Exception -> 0x0454, all -> 0x060b }
            java.lang.String r13 = ";biz="
            java.lang.StringBuilder r10 = r10.append(r13)     // Catch:{ Exception -> 0x0454, all -> 0x060b }
            r0 = r39
            java.lang.String r13 = r0.businessId     // Catch:{ Exception -> 0x0454, all -> 0x060b }
            java.lang.StringBuilder r10 = r10.append(r13)     // Catch:{ Exception -> 0x0454, all -> 0x060b }
            java.lang.String r10 = r10.toString()     // Catch:{ Exception -> 0x0454, all -> 0x060b }
            r13 = 0
            java.lang.Object[] r13 = new java.lang.Object[r13]     // Catch:{ Exception -> 0x0454, all -> 0x060b }
            r9.d(r10, r13)     // Catch:{ Exception -> 0x0454, all -> 0x060b }
            r9 = 0
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.io.IOUtils.closeQuietly(r9)
            if (r32 == 0) goto L_0x027b
            r0 = r32
            r1 = r37
            r0.release(r1)
            r36.abort()
        L_0x027b:
            int r9 = r50.getRetCode()
            java.lang.String r5 = java.lang.String.valueOf(r9)
            java.lang.String r12 = r39.getCloudId()
            r0 = r50
            r0.setTraceId(r11)
            long r14 = java.lang.System.currentTimeMillis()
            long r14 = r14 - r42
            int r8 = (int) r14
            r0 = r48
            r1 = r39
            boolean r9 = r0.isNeedUCLog(r1)
            if (r9 == 0) goto L_0x02b3
            r9 = 0
            r10 = 0
            r13 = 0
            r0 = r48
            java.lang.String r14 = r0.bizType
            int r15 = r50.getRetCode()
            r0 = r48
            boolean r15 = r0.isNoNetwork(r15)
            java.lang.String r16 = "1"
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.UCLogUtil.UC_MM_C06(r5, r6, r8, r9, r10, r11, r12, r13, r14, r15, r16)
        L_0x02b3:
            int r13 = r50.getRetCode()
            r14 = -1
            int r15 = r39.getCallGroup()
            java.lang.String r18 = r39.getCloudId()
            r0 = r48
            java.lang.String r0 = r0.bizType
            r19 = r0
            java.lang.String r20 = "1"
            r21 = 0
            boolean r22 = r39.isHttps()
            int r9 = r50.getRetCode()
            r0 = r48
            boolean r23 = r0.isNoNetwork(r9)
            r16 = r46
            a(r13, r14, r15, r16, r18, r19, r20, r21, r22, r23)
            r18 = r34
            goto L_0x0204
        L_0x02e1:
            org.apache.http.HttpResponse r9 = r37.getResp()     // Catch:{ Exception -> 0x0454, all -> 0x060b }
            org.apache.http.HttpEntity r9 = r9.getEntity()     // Catch:{ Exception -> 0x0454, all -> 0x060b }
            java.io.InputStream r38 = r9.getContent()     // Catch:{ Exception -> 0x0454, all -> 0x060b }
            r48.clearOldFileIfNotEnough()     // Catch:{ Exception -> 0x0454, all -> 0x060b }
            int r9 = r49.size()     // Catch:{ Exception -> 0x0454, all -> 0x060b }
            r10 = 1
            if (r9 > r10) goto L_0x041a
            int r9 = r37.getCode()     // Catch:{ Exception -> 0x0454, all -> 0x060b }
            r10 = 200(0xc8, float:2.8E-43)
            if (r9 != r10) goto L_0x030c
            r14 = 0
            r0 = r48
            r0.l = r14     // Catch:{ Exception -> 0x0454, all -> 0x060b }
            r0 = r48
            java.lang.String r9 = r0.b     // Catch:{ Exception -> 0x0454, all -> 0x060b }
            com.alipay.android.phone.mobilesdk.storage.utils.FileUtils.deleteFileByPath(r9)     // Catch:{ Exception -> 0x0454, all -> 0x060b }
        L_0x030c:
            r0 = r48
            java.lang.String r9 = r0.a     // Catch:{ Exception -> 0x0454, all -> 0x060b }
            com.alipay.android.phone.mobilesdk.storage.utils.FileUtils.deleteFileByPath(r9)     // Catch:{ Exception -> 0x0454, all -> 0x060b }
            r0 = r48
            long r14 = r0.l     // Catch:{ Exception -> 0x0454, all -> 0x060b }
            long r44 = r6 + r14
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger r9 = i     // Catch:{ Exception -> 0x0454, all -> 0x060b }
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0454, all -> 0x060b }
            java.lang.String r13 = "downloadSync tl = "
            r10.<init>(r13)     // Catch:{ Exception -> 0x0454, all -> 0x060b }
            r0 = r44
            java.lang.StringBuilder r10 = r10.append(r0)     // Catch:{ Exception -> 0x0454, all -> 0x060b }
            java.lang.String r13 = ", rs = "
            java.lang.StringBuilder r10 = r10.append(r13)     // Catch:{ Exception -> 0x0454, all -> 0x060b }
            r0 = r48
            long r14 = r0.l     // Catch:{ Exception -> 0x0454, all -> 0x060b }
            java.lang.StringBuilder r10 = r10.append(r14)     // Catch:{ Exception -> 0x0454, all -> 0x060b }
            java.lang.String r10 = r10.toString()     // Catch:{ Exception -> 0x0454, all -> 0x060b }
            r13 = 0
            java.lang.Object[] r13 = new java.lang.Object[r13]     // Catch:{ Exception -> 0x0454, all -> 0x060b }
            r9.i(r10, r13)     // Catch:{ Exception -> 0x0454, all -> 0x060b }
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.io.ProgressInputStream r9 = new com.alipay.android.phone.mobilecommon.multimediabiz.biz.io.ProgressInputStream     // Catch:{ Exception -> 0x0454, all -> 0x060b }
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.file.FileDownloadMMTask$2 r10 = new com.alipay.android.phone.mobilecommon.multimediabiz.biz.file.FileDownloadMMTask$2     // Catch:{ Exception -> 0x0454, all -> 0x060b }
            r0 = r48
            r1 = r36
            r2 = r44
            r10.<init>(r1, r2)     // Catch:{ Exception -> 0x0454, all -> 0x060b }
            r0 = r38
            r9.<init>(r0, r10)     // Catch:{ Exception -> 0x0454, all -> 0x060b }
            r0 = r48
            r1 = r39
            r2 = r44
            r0.a(r1, r9, r2)     // Catch:{ Exception -> 0x0454, all -> 0x060b }
        L_0x035b:
            r46 = r44
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger r9 = i     // Catch:{ Exception -> 0x0454, all -> 0x060b }
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0454, all -> 0x060b }
            java.lang.String r13 = "downloadSync bFinish = "
            r10.<init>(r13)     // Catch:{ Exception -> 0x0454, all -> 0x060b }
            r0 = r48
            java.util.concurrent.atomic.AtomicBoolean r13 = r0.o     // Catch:{ Exception -> 0x0454, all -> 0x060b }
            boolean r13 = r13.get()     // Catch:{ Exception -> 0x0454, all -> 0x060b }
            java.lang.StringBuilder r10 = r10.append(r13)     // Catch:{ Exception -> 0x0454, all -> 0x060b }
            java.lang.String r13 = ", tl = "
            java.lang.StringBuilder r10 = r10.append(r13)     // Catch:{ Exception -> 0x0454, all -> 0x060b }
            r0 = r44
            java.lang.StringBuilder r10 = r10.append(r0)     // Catch:{ Exception -> 0x0454, all -> 0x060b }
            java.lang.String r10 = r10.toString()     // Catch:{ Exception -> 0x0454, all -> 0x060b }
            r13 = 0
            java.lang.Object[] r13 = new java.lang.Object[r13]     // Catch:{ Exception -> 0x0454, all -> 0x060b }
            r9.i(r10, r13)     // Catch:{ Exception -> 0x0454, all -> 0x060b }
            r31 = 1
            r0 = r48
            java.util.concurrent.atomic.AtomicBoolean r9 = r0.o     // Catch:{ Exception -> 0x0454, all -> 0x060b }
            boolean r9 = r9.get()     // Catch:{ Exception -> 0x0454, all -> 0x060b }
            if (r9 == 0) goto L_0x051a
            r0 = r48
            java.lang.String r9 = r0.h     // Catch:{ Exception -> 0x0454, all -> 0x060b }
            r0 = r48
            java.lang.String r10 = r0.a     // Catch:{ Exception -> 0x0454, all -> 0x060b }
            r13 = 1
            boolean r31 = com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.FileUtils.checkFileByMd5(r9, r10, r13)     // Catch:{ Exception -> 0x0454, all -> 0x060b }
            if (r31 == 0) goto L_0x051a
            int r9 = r37.getCode()     // Catch:{ Exception -> 0x0454, all -> 0x060b }
            r0 = r50
            r0.setRetCode(r9)     // Catch:{ Exception -> 0x0454, all -> 0x060b }
            java.lang.String r9 = r37.getMsg()     // Catch:{ Exception -> 0x0454, all -> 0x060b }
            r0 = r50
            r0.setMsg(r9)     // Catch:{ Exception -> 0x0454, all -> 0x060b }
            r0 = r50
            r1 = r39
            r0.setFileReq(r1)     // Catch:{ Exception -> 0x0454, all -> 0x060b }
            r0 = r48
            java.lang.String r9 = r0.a     // Catch:{ Exception -> 0x0454, all -> 0x060b }
            boolean r9 = c(r9)     // Catch:{ Exception -> 0x0454, all -> 0x060b }
            if (r9 != 0) goto L_0x03f8
            r0 = r48
            java.util.concurrent.atomic.AtomicBoolean r9 = r0.n     // Catch:{ Exception -> 0x0454, all -> 0x060b }
            boolean r9 = r9.get()     // Catch:{ Exception -> 0x0454, all -> 0x060b }
            if (r9 != 0) goto L_0x03dd
            r9 = 1
            r0 = r50
            r0.setRetCode(r9)     // Catch:{ Exception -> 0x0454, all -> 0x060b }
            java.lang.String r9 = "download finish,but checkFile is fail,maybe error in file copy or rename!"
            r0 = r50
            r0.setMsg(r9)     // Catch:{ Exception -> 0x0454, all -> 0x060b }
        L_0x03dd:
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger r9 = i     // Catch:{ Exception -> 0x0454, all -> 0x060b }
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0454, all -> 0x060b }
            java.lang.String r13 = "download finish,but checkFile is fail!,save path ="
            r10.<init>(r13)     // Catch:{ Exception -> 0x0454, all -> 0x060b }
            r0 = r48
            java.lang.String r13 = r0.a     // Catch:{ Exception -> 0x0454, all -> 0x060b }
            java.lang.StringBuilder r10 = r10.append(r13)     // Catch:{ Exception -> 0x0454, all -> 0x060b }
            java.lang.String r10 = r10.toString()     // Catch:{ Exception -> 0x0454, all -> 0x060b }
            r13 = 0
            java.lang.Object[] r13 = new java.lang.Object[r13]     // Catch:{ Exception -> 0x0454, all -> 0x060b }
            r9.d(r10, r13)     // Catch:{ Exception -> 0x0454, all -> 0x060b }
        L_0x03f8:
            boolean r9 = r39.isEncrypt()     // Catch:{ Exception -> 0x0454, all -> 0x060b }
            if (r9 == 0) goto L_0x0416
            r0 = r48
            r1 = r39
            boolean r9 = r0.encryptFile(r1)     // Catch:{ Exception -> 0x0454, all -> 0x060b }
            if (r9 != 0) goto L_0x0416
            r9 = 13
            r0 = r50
            r0.setRetCode(r9)     // Catch:{ Exception -> 0x0454, all -> 0x060b }
            java.lang.String r9 = "file encrypt error"
            r0 = r50
            r0.setMsg(r9)     // Catch:{ Exception -> 0x0454, all -> 0x060b }
        L_0x0416:
            r18 = r34
            goto L_0x0171
        L_0x041a:
            r44 = r6
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger r9 = i     // Catch:{ Exception -> 0x0454, all -> 0x060b }
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0454, all -> 0x060b }
            java.lang.String r13 = "downloadSync tl = "
            r10.<init>(r13)     // Catch:{ Exception -> 0x0454, all -> 0x060b }
            java.lang.StringBuilder r10 = r10.append(r6)     // Catch:{ Exception -> 0x0454, all -> 0x060b }
            java.lang.String r10 = r10.toString()     // Catch:{ Exception -> 0x0454, all -> 0x060b }
            r13 = 0
            java.lang.Object[] r13 = new java.lang.Object[r13]     // Catch:{ Exception -> 0x0454, all -> 0x060b }
            r9.i(r10, r13)     // Catch:{ Exception -> 0x0454, all -> 0x060b }
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.io.ProgressInputStream r9 = new com.alipay.android.phone.mobilecommon.multimediabiz.biz.io.ProgressInputStream     // Catch:{ Exception -> 0x0454, all -> 0x060b }
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.file.FileDownloadMMTask$3 r10 = new com.alipay.android.phone.mobilecommon.multimediabiz.biz.file.FileDownloadMMTask$3     // Catch:{ Exception -> 0x0454, all -> 0x060b }
            r0 = r48
            r1 = r36
            r10.<init>(r1)     // Catch:{ Exception -> 0x0454, all -> 0x060b }
            r0 = r38
            r9.<init>(r0, r10)     // Catch:{ Exception -> 0x0454, all -> 0x060b }
            r0 = r48
            r1 = r49
            r0.a(r1, r9, r6)     // Catch:{ Exception -> 0x0454, all -> 0x060b }
            r0 = r48
            java.util.concurrent.atomic.AtomicBoolean r9 = r0.o     // Catch:{ Exception -> 0x0454, all -> 0x060b }
            r10 = 1
            r9.set(r10)     // Catch:{ Exception -> 0x0454, all -> 0x060b }
            goto L_0x035b
        L_0x0454:
            r33 = move-exception
            r18 = r34
        L_0x0457:
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger r9 = i     // Catch:{ all -> 0x0485 }
            java.lang.String r10 = ""
            r13 = 0
            java.lang.Object[] r13 = new java.lang.Object[r13]     // Catch:{ all -> 0x0485 }
            r0 = r33
            r9.e(r0, r10, r13)     // Catch:{ all -> 0x0485 }
            java.lang.String r18 = com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.CommonUtils.getExceptionMsg(r33)     // Catch:{ all -> 0x0485 }
            java.lang.String r9 = "multimedia_file_task_canceled"
            java.lang.String r10 = r33.getMessage()     // Catch:{ all -> 0x0485 }
            boolean r9 = r9.equals(r10)     // Catch:{ all -> 0x0485 }
            if (r9 == 0) goto L_0x0600
            r9 = 5
            r0 = r50
            r0.setRetCode(r9)     // Catch:{ all -> 0x0485 }
        L_0x0479:
            r0 = r50
            r1 = r18
            r0.setMsg(r1)     // Catch:{ all -> 0x0485 }
            int r35 = com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.DiskExpUtils.parseException(r33)     // Catch:{ all -> 0x0485 }
            throw r33     // Catch:{ all -> 0x0485 }
        L_0x0485:
            r9 = move-exception
        L_0x0486:
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.io.IOUtils.closeQuietly(r38)
            if (r32 == 0) goto L_0x0495
            r0 = r32
            r1 = r37
            r0.release(r1)
            r36.abort()
        L_0x0495:
            int r10 = r50.getRetCode()
            java.lang.String r5 = java.lang.String.valueOf(r10)
            java.lang.String r12 = r39.getCloudId()
            if (r4 == 0) goto L_0x04a7
            java.lang.String r5 = java.lang.String.valueOf(r40)
        L_0x04a7:
            r0 = r50
            r0.setTraceId(r11)
            long r14 = java.lang.System.currentTimeMillis()
            long r14 = r14 - r42
            int r8 = (int) r14
            r0 = r48
            r1 = r39
            boolean r10 = r0.isNeedUCLog(r1)
            if (r10 == 0) goto L_0x04ee
            if (r4 == 0) goto L_0x0608
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            java.lang.String r13 = "s"
            r10.<init>(r13)
            java.lang.StringBuilder r10 = r10.append(r5)
            java.lang.String r13 = r10.toString()
        L_0x04ce:
            r17 = 0
            r21 = 0
            r0 = r48
            java.lang.String r0 = r0.bizType
            r22 = r0
            int r10 = r50.getRetCode()
            r0 = r48
            boolean r23 = r0.isNoNetwork(r10)
            java.lang.String r24 = "1"
            r14 = r6
            r16 = r8
            r19 = r11
            r20 = r12
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.UCLogUtil.UC_MM_C06(r13, r14, r16, r17, r18, r19, r20, r21, r22, r23, r24)
        L_0x04ee:
            int r19 = r50.getRetCode()
            int r21 = r39.getCallGroup()
            java.lang.String r24 = r39.getCloudId()
            r0 = r48
            java.lang.String r0 = r0.bizType
            r25 = r0
            java.lang.String r26 = "1"
            boolean r28 = r39.isHttps()
            int r10 = r50.getRetCode()
            r0 = r48
            boolean r29 = r0.isNoNetwork(r10)
            r20 = r35
            r22 = r46
            r27 = r18
            a(r19, r20, r21, r22, r24, r25, r26, r27, r28, r29)
            throw r9
        L_0x051a:
            if (r31 != 0) goto L_0x052d
            r9 = 4
            r0 = r50
            r0.setRetCode(r9)     // Catch:{ Exception -> 0x0454, all -> 0x060b }
            java.lang.String r9 = "md5 not match"
            r0 = r50
            r0.setMsg(r9)     // Catch:{ Exception -> 0x0454, all -> 0x060b }
            r18 = r34
            goto L_0x0171
        L_0x052d:
            r9 = 6
            r0 = r50
            r0.setRetCode(r9)     // Catch:{ Exception -> 0x0454, all -> 0x060b }
            java.lang.String r9 = "size not match"
            r0 = r50
            r0.setMsg(r9)     // Catch:{ Exception -> 0x0454, all -> 0x060b }
            r18 = r34
            goto L_0x0171
        L_0x053e:
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger r9 = i     // Catch:{ Exception -> 0x0454, all -> 0x060b }
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0454, all -> 0x060b }
            java.lang.String r13 = "downloadBatch rsp="
            r10.<init>(r13)     // Catch:{ Exception -> 0x0454, all -> 0x060b }
            r0 = r37
            java.lang.StringBuilder r10 = r10.append(r0)     // Catch:{ Exception -> 0x0454, all -> 0x060b }
            java.lang.String r10 = r10.toString()     // Catch:{ Exception -> 0x0454, all -> 0x060b }
            r13 = 0
            java.lang.Object[] r13 = new java.lang.Object[r13]     // Catch:{ Exception -> 0x0454, all -> 0x060b }
            r9.d(r10, r13)     // Catch:{ Exception -> 0x0454, all -> 0x060b }
            java.lang.String r11 = r37.getTraceId()     // Catch:{ Exception -> 0x0454, all -> 0x060b }
            java.lang.String r18 = r37.getMsg()     // Catch:{ Exception -> 0x0454, all -> 0x060b }
            r4 = 1
            int r40 = r37.getCode()     // Catch:{ Exception -> 0x057c }
            r9 = 404(0x194, float:5.66E-43)
            int r10 = r37.getCode()     // Catch:{ Exception -> 0x057c }
            if (r9 != r10) goto L_0x057f
            r9 = 11
            r0 = r50
            r0.setRetCode(r9)     // Catch:{ Exception -> 0x057c }
        L_0x0573:
            r0 = r50
            r1 = r18
            r0.setMsg(r1)     // Catch:{ Exception -> 0x057c }
            goto L_0x0171
        L_0x057c:
            r33 = move-exception
            goto L_0x0457
        L_0x057f:
            r9 = 429(0x1ad, float:6.01E-43)
            int r10 = r37.getCode()     // Catch:{ Exception -> 0x057c }
            if (r9 != r10) goto L_0x05a1
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger r9 = i     // Catch:{ Exception -> 0x057c }
            java.lang.String r10 = "fileDownload fail by net limit"
            r13 = 0
            java.lang.Object[] r13 = new java.lang.Object[r13]     // Catch:{ Exception -> 0x057c }
            r9.d(r10, r13)     // Catch:{ Exception -> 0x057c }
            r9 = 2000(0x7d0, float:2.803E-42)
            r0 = r50
            r0.setRetCode(r9)     // Catch:{ Exception -> 0x057c }
            boolean r9 = android.text.TextUtils.isEmpty(r18)     // Catch:{ Exception -> 0x057c }
            if (r9 == 0) goto L_0x0573
            java.lang.String r18 = "download fail for limited current"
            goto L_0x0573
        L_0x05a1:
            r9 = 416(0x1a0, float:5.83E-43)
            int r10 = r37.getCode()     // Catch:{ Exception -> 0x057c }
            if (r9 != r10) goto L_0x05d4
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger r9 = i     // Catch:{ Exception -> 0x057c }
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x057c }
            java.lang.String r13 = "fileDownload fail,error code is "
            r10.<init>(r13)     // Catch:{ Exception -> 0x057c }
            int r13 = r37.getCode()     // Catch:{ Exception -> 0x057c }
            java.lang.StringBuilder r10 = r10.append(r13)     // Catch:{ Exception -> 0x057c }
            java.lang.String r10 = r10.toString()     // Catch:{ Exception -> 0x057c }
            r13 = 0
            java.lang.Object[] r13 = new java.lang.Object[r13]     // Catch:{ Exception -> 0x057c }
            r9.d(r10, r13)     // Catch:{ Exception -> 0x057c }
            r0 = r48
            java.lang.String r9 = r0.b     // Catch:{ Exception -> 0x057c }
            com.alipay.android.phone.mobilesdk.storage.utils.FileUtils.deleteFileByPath(r9)     // Catch:{ Exception -> 0x057c }
            r9 = 6
            r0 = r50
            r0.setRetCode(r9)     // Catch:{ Exception -> 0x057c }
            java.lang.String r18 = "fileDownload fail,error code is 416"
            goto L_0x0573
        L_0x05d4:
            r9 = 403(0x193, float:5.65E-43)
            int r10 = r37.getCode()     // Catch:{ Exception -> 0x057c }
            if (r9 != r10) goto L_0x05e4
            r9 = -403(0xfffffffffffffe6d, float:NaN)
            r0 = r50
            r0.setRetCode(r9)     // Catch:{ Exception -> 0x057c }
            goto L_0x0573
        L_0x05e4:
            int r9 = com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.util.DjangoConstant.DJANGO_TIMEOUT     // Catch:{ Exception -> 0x057c }
            int r10 = r37.getCode()     // Catch:{ Exception -> 0x057c }
            if (r9 != r10) goto L_0x05f4
            r9 = 14
            r0 = r50
            r0.setRetCode(r9)     // Catch:{ Exception -> 0x057c }
            goto L_0x0573
        L_0x05f4:
            r9 = 10
            r0 = r50
            r0.setRetCode(r9)     // Catch:{ Exception -> 0x057c }
            goto L_0x0573
        L_0x05fd:
            r13 = r5
            goto L_0x01b9
        L_0x0600:
            r9 = 1
            r0 = r50
            r0.setRetCode(r9)     // Catch:{ all -> 0x0485 }
            goto L_0x0479
        L_0x0608:
            r13 = r5
            goto L_0x04ce
        L_0x060b:
            r9 = move-exception
            r18 = r34
            goto L_0x0486
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.android.phone.mobilecommon.multimediabiz.biz.file.FileDownloadMMTask.b(java.util.List, com.alipay.android.phone.mobilecommon.multimedia.file.data.APFileDownloadRsp):void");
    }

    private void a(List<APFileReq> reqList, InputStream input, long totalLength) {
        final HashMap savePaths2 = new HashMap();
        final HashMap tmpSavePaths = new HashMap();
        this.j = 0;
        this.k = 0;
        final List list = reqList;
        final long j2 = totalLength;
        this.c.writeBatchFiles(input, new ReadBatchFileRespCallback() {
            public OutputStream onReadFile(FileHeader fileHeader, boolean isEmptyFile) {
                FileDownloadMMTask.i.d("downloadSync onReadFile fileId: " + fileHeader.fileId, new Object[0]);
                APFileReq tmpReq = (APFileReq) list.get(FileDownloadMMTask.this.j);
                tmpReq.setCloudId(fileHeader.fileId);
                String savePath = FileDownloadMMTask.this.getSavePath(tmpReq);
                savePaths2.put(tmpReq, savePath);
                String tmpSavePath = savePath + ".dltmp";
                tmpSavePaths.put(tmpReq, tmpSavePath);
                FileDownloadMMTask.this.deleteFileInner(tmpSavePath);
                FileDownloadMMTask.this.deleteFileInner(savePath);
                FileDownloadMMTask.this.j = FileDownloadMMTask.this.j + 1;
                try {
                    return new ProgressOutputStream(new FileOutputStream(savePath), new TransferredListener() {
                        public void onTransferred(long transferredCount) {
                            FileDownloadMMTask.this.checkCanceled();
                            FileDownloadMMTask.this.k = FileDownloadMMTask.this.k + transferredCount;
                            if (!FileDownloadMMTask.this.callbacks.isEmpty()) {
                                int progress = j2 > 0 ? (int) ((((float) FileDownloadMMTask.this.k) * 100.0f) / ((float) j2)) : 0;
                                if (FileDownloadMMTask.this.m != progress) {
                                    FileDownloadMMTask.this.m = progress;
                                    FileDownloadMMTask.this.a(FileDownloadMMTask.this.taskInfo, progress, FileDownloadMMTask.this.j, FileDownloadMMTask.this.k, j2);
                                }
                            }
                        }
                    });
                } catch (IOException e2) {
                    FileDownloadMMTask.i.e(e2, "", new Object[0]);
                    return null;
                }
            }
        });
        for (APFileReq req : reqList) {
            if (TextUtils.isEmpty(req.getSavePath())) {
                req.setSavePath((String) savePaths2.get(req));
            }
            copyFile((String) tmpSavePaths.get(req), (String) savePaths2.get(req));
            if (req.isNeedCache()) {
                addCacheFile(req);
            }
            deleteFileInner((String) tmpSavePaths.get(req));
        }
    }

    private void a(APFileReq req, InputStream input, long totalLength) {
        if (this.l > 0) {
            final long j2 = totalLength;
            this.c.writeSingleFile(input, new File(this.b), this.l, new TransferredListener() {
                public void onTransferred(long transferredCount) {
                    FileDownloadMMTask.this.a(transferredCount, j2);
                }
            });
        } else {
            final long j3 = totalLength;
            this.c.writeSingleFile(input, new ProgressOutputStream(new FileOutputStream(this.b), new TransferredListener() {
                public void onTransferred(long transferredCount) {
                    FileDownloadMMTask.this.a(transferredCount, j3);
                }
            }));
        }
        FileValidStrategy strategy = b(req.getType());
        if (strategy != null && !strategy.checkFileValid(this.b)) {
            com.alipay.android.phone.mobilesdk.storage.utils.FileUtils.deleteFileByPath(this.b);
        }
        File file = new File(this.b);
        i.i("tmpFile.length: " + file.length(), new Object[0]);
        if (!this.o.get() || !file.exists() || !file.isFile() || file.length() != totalLength) {
            this.o.set(false);
            return;
        }
        String originSavePath = req.getSavePath();
        if (TextUtils.isEmpty(req.getSavePath())) {
            req.setSavePath(this.a);
        }
        this.n.set(false);
        if (a(totalLength)) {
            long startTime = System.currentTimeMillis();
            boolean bSuccess = FileUtils.copyFileWithRetry(file, new BaseFile(this.a));
            i.d("handleDjangoSingleDownloadStream copyFile coast=" + (System.currentTimeMillis() - startTime) + ";result=" + bSuccess, new Object[0]);
            this.n.set(bSuccess);
            if (bSuccess) {
                File saveFile = new File(this.a);
                if (saveFile.length() != totalLength) {
                    saveFile.delete();
                    i.d("size of save file after copyFile is wrong, size=" + saveFile.length(), new Object[0]);
                } else {
                    file.delete();
                }
            } else {
                i.d("handleDjangoSingleDownloadStream copyFile error savePath=" + this.a, new Object[0]);
            }
        } else {
            boolean bSuccess2 = com.alipay.android.phone.mobilesdk.storage.utils.FileUtils.moveFile(file, new BaseFile(this.a));
            this.n.set(bSuccess2);
            if (!bSuccess2) {
                i.d("handleDjangoSingleDownloadStream renameFile error savePath=" + this.a, new Object[0]);
            }
        }
        if (req.isNeedCache() && TextUtils.isEmpty(originSavePath)) {
            addCacheFile(req);
        }
        this.taskInfo.setDestPath(req.getSavePath());
    }

    public void onStateChange(int state) {
        if (isCanceled()) {
            this.taskInfo.setStatus(2);
        }
    }

    private static FileValidStrategy b(String type) {
        if (TextUtils.isEmpty(type)) {
            return null;
        }
        if ("image".equalsIgnoreCase(type) || APFileReq.FILE_TYPE_COMPRESS_IMAGE.equalsIgnoreCase(type)) {
            return new ImageValidStrategy();
        }
        return null;
    }

    /* access modifiers changed from: private */
    public void a(long transferredCount, long totalLength) {
        int progress;
        checkCanceled();
        this.k = this.l + transferredCount;
        if (this.callbacks.size() > 0) {
            if (totalLength > 0) {
                progress = (int) ((((float) this.k) * 100.0f) / ((float) totalLength));
            } else {
                progress = 0;
            }
            if (this.m != progress) {
                if (progress <= 1 || progress >= 99) {
                    i.d("onDownloadProgress progress:  " + progress + ", name: " + this.name, new Object[0]);
                }
                this.m = progress;
                a(this.taskInfo, progress, this.k, totalLength);
            }
        }
    }

    private List a(List<APFileReq> reqList) {
        List noCacheFileList = new ArrayList();
        for (APFileReq req : reqList) {
            String cached = "1";
            String cachePath = checkCacheFile(req);
            if (!TextUtils.isEmpty(cachePath)) {
                if (TextUtils.isEmpty(req.getSavePath())) {
                    req.setSavePath(cachePath);
                } else {
                    copyFile(cachePath, req.getSavePath());
                }
                cached = "0";
                if (!PathUtils.checkIsResourcePreDownload(req.businessId)) {
                    if (req.getCallGroup() == 1001) {
                        CacheHitManager.getInstance().imageCacheHit();
                    } else if (req.getCallGroup() == 1003) {
                        CacheHitManager.getInstance().videoCacheHit();
                    } else if (req.getCallGroup() == 1002) {
                        CacheHitManager.getInstance().audioCacheHit();
                    } else {
                        CacheHitManager.getInstance().fileCacheHit();
                    }
                }
            } else {
                noCacheFileList.add(req);
                if (!PathUtils.checkIsResourcePreDownload(req.businessId)) {
                    if (req.getCallGroup() == 1001) {
                        CacheHitManager.getInstance().imageCacheMissed();
                    } else if (req.getCallGroup() == 1003) {
                        CacheHitManager.getInstance().videoCacheMissed();
                    } else if (req.getCallGroup() == 1002) {
                        CacheHitManager.getInstance().audioCacheMissed();
                    } else {
                        CacheHitManager.getInstance().fileCacheMissed();
                    }
                }
            }
            if (PathUtils.isPreloadNeedReport(req.businessId, req.getCloudId())) {
                UCLogUtil.UC_MM_48(cached, req.getCloudId(), UCLogUtil.getTypeByCallGroup(req.getCallGroup()));
            }
        }
        return noCacheFileList;
    }

    private static String a(CharSequence delimiter, List<APFileReq> reqList) {
        StringBuilder sb = new StringBuilder();
        boolean firstTime = true;
        for (APFileReq req : reqList) {
            if (firstTime) {
                firstTime = false;
            } else {
                sb.append(delimiter);
            }
            sb.append(req.getCloudId());
        }
        return sb.toString();
    }

    public void cancel() {
        super.cancel();
        if (this.e != null) {
            this.e.cancelDownload(this.f);
        }
        if (this.d != null) {
            this.d.cancel(true);
        }
    }

    public APFileDownloadRsp taskRun() {
        try {
            i.d("FileDownloadMMTask taskRun start", new Object[0]);
            APFileRsp rsp = super.taskRun();
            if (rsp == null) {
                return downloadSync(this.fileReqList);
            }
            APFileDownloadRsp downloadRsp = new APFileDownloadRsp();
            downloadRsp.setRetCode(rsp.getRetCode());
            downloadRsp.setMsg(rsp.getMsg());
            downloadRsp.setFileReq((APFileReq) this.fileReqList.get(0));
            b(this.taskInfo, downloadRsp);
            return downloadRsp;
        } catch (Exception e2) {
            i.e(e2, "", new Object[0]);
            i.d("taskRun end", new Object[0]);
            return null;
        }
    }

    public void onMergeTask(MMTask task) {
        if (task != null && (task instanceof FileDownloadMMTask)) {
            FileDownloadMMTask mergeTask = (FileDownloadMMTask) task;
            synchronized (this.callbacks) {
                this.callbacks.addAll(mergeTask.callbacks);
            }
            mergeTask.taskInfo = this.taskInfo;
            this.savePaths.add(mergeTask.a);
        }
    }

    public void onAddTask() {
        APFileTaskManager.getInstance(this.mContext).addTaskRecord(this.taskInfo);
        if (this.callbacks != null && !this.callbacks.isEmpty()) {
            synchronized (this.callbacks) {
                Object o2 = this.callbacks.iterator().next();
                this.requestCallBackClassName = o2 == null ? null : o2.getClass().getName();
            }
        }
    }

    public void registeFileDownloadCallback(APFileDownCallback callback) {
        if (callback != null) {
            synchronized (this.callbacks) {
                this.callbacks.add(callback);
            }
        }
    }

    public void unregisteFileDownloadCallback(APFileDownCallback callback) {
        synchronized (this.callbacks) {
            if (callback == null) {
                this.callbacks.clear();
            } else {
                this.callbacks.remove(callback);
            }
        }
    }

    /* access modifiers changed from: private */
    public void a(APMultimediaTaskModel taskInfo) {
        if (2 == taskInfo.getStatus()) {
            i.d("onDownloadStart cancel return ", new Object[0]);
            return;
        }
        updateTaskModelStatus(taskInfo, 1);
        synchronized (this.callbacks) {
            if (!this.callbacks.isEmpty()) {
                i.p("notifyDownloadStart callbacks " + this.callbacks.size(), new Object[0]);
                for (APFileDownCallback onDownloadStart : this.callbacks) {
                    onDownloadStart.onDownloadStart(taskInfo);
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public void a(APMultimediaTaskModel taskInfo, int progress, long hasDownSize, long total) {
        if (taskInfo != null) {
            taskInfo.setCurrentSize(hasDownSize);
            taskInfo.setTotalSize(total);
        }
        if (taskInfo == null || 2 != taskInfo.getStatus()) {
            synchronized (this.callbacks) {
                if (!this.callbacks.isEmpty()) {
                    if (progress <= 1 || progress >= 99) {
                        i.d("FileDownLoadListener callbacks " + this.callbacks.size() + ";progress=" + progress, new Object[0]);
                    } else {
                        i.p("FileDownLoadListener callbacks " + this.callbacks.size() + ";progress=" + progress, new Object[0]);
                    }
                    i.p("notifyDownloadProgress callbacks " + this.callbacks.size(), new Object[0]);
                    for (APFileDownCallback onDownloadProgress : this.callbacks) {
                        onDownloadProgress.onDownloadProgress(taskInfo, progress, hasDownSize, total);
                    }
                }
            }
            return;
        }
        i.d("onDownloadProgress cancel return ", new Object[0]);
    }

    /* access modifiers changed from: private */
    public void a(APMultimediaTaskModel taskInfo, int progress, int curIndex, long hasDownSize, long total) {
        if (taskInfo != null) {
            taskInfo.setCurrentSize(hasDownSize);
            taskInfo.setTotalSize(total);
        }
        if (taskInfo == null || 2 != taskInfo.getStatus()) {
            synchronized (this.callbacks) {
                if (!this.callbacks.isEmpty()) {
                    if (progress <= 1 || progress >= 99) {
                        i.d("FileDownLoadListener callbacks " + this.callbacks.size() + ";progress=" + progress, new Object[0]);
                    } else {
                        i.p("FileDownLoadListener callbacks " + this.callbacks.size() + ";progress=" + progress, new Object[0]);
                    }
                    i.p("notifyDownloadBatchProgress callbacks " + this.callbacks.size(), new Object[0]);
                    for (APFileDownCallback onDownloadBatchProgress : this.callbacks) {
                        onDownloadBatchProgress.onDownloadBatchProgress(taskInfo, progress, curIndex, hasDownSize, total);
                    }
                }
            }
            return;
        }
        i.d("onDownloadBatchProgress cancel return ", new Object[0]);
    }

    private void a(APMultimediaTaskModel taskInfo, APFileDownloadRsp rsp) {
        if (rsp.getFileReq() == null || rsp.getFileReq().isNeedCache()) {
            updateTaskModelStatus(taskInfo, 4);
        } else {
            removeTaskRecord(taskInfo.getTaskId());
        }
        if (rsp.getFileReq() != null) {
            a(taskInfo.getTaskId(), rsp.getFileReq().getSavePath());
        }
        synchronized (this.callbacks) {
            if (!this.callbacks.isEmpty()) {
                i.p("notifyDownFinish callbacks " + this.callbacks.size(), new Object[0]);
                for (APFileDownCallback onDownloadFinished : this.callbacks) {
                    onDownloadFinished.onDownloadFinished(taskInfo, rsp);
                }
            }
        }
    }

    private void b(APMultimediaTaskModel taskInfo, APFileDownloadRsp rsp) {
        APFileReq req = rsp.getFileReq();
        if (req == null || req.isNeedCache() || req.isCacheWhileError()) {
            updateTaskModelStatus(taskInfo, 3);
        } else {
            removeTaskRecord(taskInfo.getTaskId());
        }
        synchronized (this.callbacks) {
            if (!this.callbacks.isEmpty()) {
                i.p("notifyDownloadError callbacks " + this.callbacks.size() + ";rsp=" + rsp, new Object[0]);
                for (APFileDownCallback onDownloadError : this.callbacks) {
                    onDownloadError.onDownloadError(taskInfo, rsp);
                }
            }
        }
    }

    private void a(String taskId, String originalPath) {
        RepeatableBufferedInputStream bufferedInputStream;
        i.d("copyFileAfterDownload " + taskId + ", originalPath: " + originalPath, new Object[0]);
        if (!TextUtils.isEmpty(taskId) && !TextUtils.isEmpty(originalPath)) {
            synchronized (this.savePaths) {
                if (!this.savePaths.isEmpty()) {
                    RepeatableBufferedInputStream bufferedInputStream2 = null;
                    for (String path : this.savePaths) {
                        i.p("copyFileAfterDownload, path: " + path, new Object[0]);
                        if (!TextUtils.isEmpty(path) && !path.equalsIgnoreCase(originalPath)) {
                            i.d("copyFileAfterDownload, copy file to: " + path, new Object[0]);
                            try {
                                bufferedInputStream = new RepeatableBufferedInputStream(new FileInputStream(originalPath));
                                try {
                                    bufferedInputStream.flip();
                                    File reqFile = new File(path);
                                    File parent = reqFile.getParentFile();
                                    if (parent.exists() || parent.mkdirs()) {
                                        com.alipay.android.phone.mobilesdk.storage.utils.FileUtils.copyFile(bufferedInputStream, reqFile);
                                        IOUtils.closeQuietly((InputStream) bufferedInputStream);
                                        bufferedInputStream2 = bufferedInputStream;
                                    } else {
                                        throw new RuntimeException("Couldn't create dir: " + parent);
                                    }
                                } catch (Throwable th) {
                                    e = th;
                                    try {
                                        i.e(e, "", new Object[0]);
                                        throw new RuntimeException(e.getMessage());
                                    } catch (Throwable th2) {
                                        th = th2;
                                        IOUtils.closeQuietly((InputStream) bufferedInputStream);
                                        throw th;
                                    }
                                }
                            } catch (Throwable th3) {
                                th = th3;
                                bufferedInputStream = bufferedInputStream2;
                                IOUtils.closeQuietly((InputStream) bufferedInputStream);
                                throw th;
                            }
                        }
                    }
                }
            }
        }
    }

    private static void a(int ret, int expcode, int callCroup, long size, String id, String biz, String unm, String exp, boolean bHttps, boolean noNetwork) {
        String str;
        if (ret == 0 || expcode > 0) {
            String type = "fl";
            String zoom = "";
            if (callCroup == 1002) {
                type = "ad";
            } else if (callCroup == 1001) {
                type = "im";
                zoom = "original";
            } else if (callCroup == 1003) {
                type = LogItem.MM_C18_K4_VD;
            }
            String valueOf = ret == 0 ? "0" : String.valueOf(expcode);
            if (bHttps) {
                str = "1";
            } else {
                str = "0";
            }
            UCLogUtil.UC_MM_C47(valueOf, size, 0, id, type, biz, unm, exp, zoom, str, noNetwork);
        }
    }

    private static boolean c(String path) {
        try {
            if (TextUtils.isEmpty(path)) {
                return false;
            }
            return new File(path).exists();
        } catch (Throwable th) {
            return false;
        }
    }

    private static boolean a(long fileSize) {
        return fileSize > 0 && fileSize <= ConfigManager.getInstance().getCommonConfigItem().djangoConf.maxCopyFileSize;
    }
}
