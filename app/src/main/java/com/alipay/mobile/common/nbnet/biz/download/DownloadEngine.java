package com.alipay.mobile.common.nbnet.biz.download;

import android.os.Environment;
import android.text.TextUtils;
import com.alipay.mobile.common.nbnet.api.NBNetContext;
import com.alipay.mobile.common.nbnet.api.NBNetException;
import com.alipay.mobile.common.nbnet.api.download.NBNetDownloadRequest;
import com.alipay.mobile.common.nbnet.api.download.NBNetDownloadResponse;
import com.alipay.mobile.common.nbnet.api.download.proto.MMDPReq;
import com.alipay.mobile.common.nbnet.api.download.proto.MMDPResp;
import com.alipay.mobile.common.nbnet.biz.GlobalNBNetContext;
import com.alipay.mobile.common.nbnet.biz.NBNetInterceptorManager;
import com.alipay.mobile.common.nbnet.biz.db.DownloadTaskModel;
import com.alipay.mobile.common.nbnet.biz.exception.NBNetCancelException;
import com.alipay.mobile.common.nbnet.biz.exception.NBNetProtocolException;
import com.alipay.mobile.common.nbnet.biz.exception.NBNetServerException;
import com.alipay.mobile.common.nbnet.biz.exception.NBNetVerifyException;
import com.alipay.mobile.common.nbnet.biz.io.DownloadVerifiableOutputStream;
import com.alipay.mobile.common.nbnet.biz.io.LengthInputStream;
import com.alipay.mobile.common.nbnet.biz.log.MonitorLogUtil;
import com.alipay.mobile.common.nbnet.biz.log.NBNetLogCat;
import com.alipay.mobile.common.nbnet.biz.util.IOUtils;
import com.alipay.mobile.common.nbnet.biz.util.NBNetEnvUtils;
import com.alipay.mobile.common.nbnet.biz.util.ServerLimitedFlowHelper;
import com.alipay.mobile.common.transport.utils.SDcardUtils;
import java.io.Closeable;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.atomic.AtomicBoolean;

public class DownloadEngine {
    private static final String a = DownloadEngine.class.getSimpleName();
    private final int b;
    private final String c;
    private final NBNetDownloadRequest d;
    private final ProgressObserver e;
    private final File f;
    private DownloadTaskModel g;
    private MMDPTransport h;
    private MMDPReq i;
    private MMDPResp j;
    private AtomicBoolean k = new AtomicBoolean(false);
    private NBNetContext l;
    private long m = -1;
    private int n = 0;
    private int o = 0;

    public DownloadEngine(NBNetDownloadRequest downloadRequest, ProgressObserver progressObserver, NBNetContext nbNetContext) {
        this.d = downloadRequest;
        this.c = downloadRequest.getFileId();
        this.e = progressObserver;
        this.b = downloadRequest.getRequestId();
        this.l = nbNetContext;
        this.f = DownloadCacheManager.a().a(this.b);
    }

    public final void a() {
        NBNetLogCat.c(a, "submitDownloadRequest");
        MonitorLogUtil.b(this.l, NBNetEnvUtils.a());
        i();
        if (this.f.exists() && this.f.length() > 0) {
            this.g = DownloadCacheManager.a().c().a(this.b);
        }
        this.i = new MMDPRequestBuilder(this.d, this.g).a();
        this.h = GlobalNBNetContext.a().b();
        this.h.a(this.l);
        long requestDataLength = j();
        a(requestDataLength);
        NBNetInterceptorManager.a().a(this.c, requestDataLength);
    }

    private void i() {
        if (this.k.get()) {
            throw new NBNetCancelException("download has been canceled");
        }
        IOUtils.b(this.f, new File(this.d.getSavePath()));
        NBNetInterceptorManager.a().a(String.valueOf(this.d.getRequestId()));
    }

    public final void a(NBNetDownloadResponse downloadResponse) {
        NBNetLogCat.c(a, "processDownloadResponse");
        if (this.k.get()) {
            throw new NBNetCancelException("download has been canceled");
        }
        this.m = System.currentTimeMillis();
        this.j = this.h.a_();
        if (this.j == null) {
            throw new NBNetProtocolException("read response fail");
        }
        MonitorLogUtil.i(this.l, this.j.via);
        ServerLimitedFlowHelper.a(this.j, NBNetEnvUtils.a());
        if (downloadResponse != null) {
            downloadResponse.setErrorCode(this.j.errcode != null ? this.j.errcode.intValue() : -1);
            downloadResponse.setDataLength(this.j.filelength != null ? (long) this.j.filelength.intValue() : 0);
            downloadResponse.setTraceId(this.j.traceid);
        }
        if (this.j.errcode == null || this.j.errcode.intValue() != 0) {
            NBNetLogCat.d(a, "[processDownloadResponse] error code: " + this.j.errcode);
            if (this.j.errcode != null && this.j.errcode.intValue() == 416) {
                DownloadCacheManager.a().c(this.b);
            }
            ServerLimitedFlowHelper.a(this.j);
            throw new NBNetServerException(this.j.errcode.intValue(), "download response errcode is " + this.j.errcode);
        } else if (this.j.datalength == null || this.j.datalength.intValue() == 0 || this.j.datalength.intValue() > 209715200) {
            throw new NBNetVerifyException("data length is fault:" + this.j.datalength);
        } else if (!TextUtils.equals(this.c, this.j.fileid)) {
            throw new NBNetVerifyException("download response fileid not matching, expect " + this.c + ", but " + this.j.fileid);
        } else {
            if (!(this.i.rangestart == null || this.j.fileoffset == null || this.i.rangestart.equals(this.j.fileoffset))) {
                DownloadCacheManager.a().c(this.b);
                if (this.j.fileoffset.intValue() != 0) {
                    throw new NBNetVerifyException("download response fileoffset is " + this.j.fileoffset + ", not range start " + this.i.rangestart);
                }
            }
            DownloadTaskModel updateTaskModel = this.g;
            if (updateTaskModel == null) {
                updateTaskModel = new DownloadTaskModel();
            }
            updateTaskModel.requestId = this.b;
            updateTaskModel.fileId = this.c;
            updateTaskModel.lastModified = System.currentTimeMillis();
            if (this.j.filemd5 != null && this.j.filemd5.length() > 0) {
                updateTaskModel.fileMD5 = this.j.filemd5;
            }
            if (!(this.j.filelength == null || this.j.filelength.intValue() == 0)) {
                updateTaskModel.fileLength = this.j.filelength.intValue();
            }
            DownloadCacheManager.a().c().a(updateTaskModel);
        }
    }

    public final void b() {
        try {
            k();
        } finally {
            MonitorLogUtil.m(this.l, System.currentTimeMillis() - this.m);
        }
    }

    public final void c() {
        this.k.set(true);
        if (this.h != null) {
            this.h.e();
        }
    }

    public final void d() {
        if (this.h != null) {
            this.h.d();
        }
    }

    public final void e() {
        if (this.h != null) {
            this.h.e();
        }
    }

    private long j() {
        this.h.a(this.i);
        return this.h.b();
    }

    /* JADX INFO: finally extract failed */
    private void k() {
        NBNetLogCat.c(a, "transferDownloadFile");
        if (this.k.get()) {
            throw new NBNetCancelException("download has canceled");
        }
        l();
        int fileLength = n();
        String fileMd5 = m();
        long prevFileLength = this.f.length();
        LengthInputStream inputStream = new LengthInputStream(this.h.c(), this.j.datalength.intValue());
        DownloadVerifiableOutputStream outputStream = new DownloadVerifiableOutputStream(this.f, fileLength, fileMd5, this.e);
        try {
            long startTime = System.currentTimeMillis();
            IOUtils.a((InputStream) inputStream, (OutputStream) outputStream);
            NBNetLogCat.a(a, String.format("monitor_perf: transferStream. file has been cached: %s , cost_time: %d", new Object[]{this.f.getAbsolutePath(), Long.valueOf(System.currentTimeMillis() - startTime)}));
            IOUtils.a((Closeable) inputStream);
            IOUtils.a((Closeable) outputStream);
            b(this.f.length() - prevFileLength);
            NBNetInterceptorManager.a().b(this.c, (long) fileLength);
            try {
                outputStream.a();
            } catch (NBNetVerifyException e2) {
                DownloadCacheManager.a().c(this.b);
                throw e2;
            }
        } catch (Throwable th) {
            IOUtils.a((Closeable) inputStream);
            IOUtils.a((Closeable) outputStream);
            b(this.f.length() - prevFileLength);
            throw th;
        }
    }

    private void l() {
        DownloadCacheManager.a().a(this.j.datalength.intValue(), this.b);
        IOUtils.b(DownloadCacheManager.a().a(this.b));
        File saveFile = new File(this.d.getSavePath());
        if (!saveFile.getAbsolutePath().startsWith(Environment.getExternalStorageDirectory().getAbsolutePath())) {
            NBNetLogCat.a(a, "checkDiskFreeSpace. SaveFile not sdcard. path = " + saveFile.getAbsolutePath());
        }
        if (!SDcardUtils.isSDcardAvailableSpace((long) this.j.datalength.intValue())) {
            throw new NBNetException("Insufficient space on the disk. path: " + saveFile.getAbsolutePath(), -19);
        }
        IOUtils.b(saveFile);
    }

    private String m() {
        if (this.g != null) {
            return this.g.fileMD5;
        }
        if (this.j.filemd5 == null || this.j.filemd5.length() <= 0) {
            return null;
        }
        return this.j.filemd5;
    }

    private int n() {
        if (this.g != null) {
            return this.g.fileLength;
        }
        if (this.j.filelength == null || this.j.filelength.intValue() == 0) {
            return 0;
        }
        return this.j.filelength.intValue();
    }

    public final String f() {
        if (this.i != null) {
            return this.i.traceid;
        }
        return "";
    }

    public final int g() {
        return this.n;
    }

    public final int h() {
        return this.o;
    }

    private void a(long sendedSize) {
        if (sendedSize > 0) {
            this.o = (int) (((long) this.o) + sendedSize);
        }
    }

    private void b(long receivedSize) {
        if (receivedSize > 0) {
            this.n = (int) (((long) this.n) + receivedSize);
        }
    }
}
