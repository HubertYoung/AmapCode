package com.alipay.mobile.common.transport.h5;

import android.text.TextUtils;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.logging.api.monitor.DataflowID;
import com.alipay.mobile.common.logging.api.monitor.DataflowModel;
import com.alipay.mobile.common.transport.context.TransportContext;
import com.alipay.mobile.common.transport.http.HttpManager;
import com.alipay.mobile.common.transport.http.HttpUrlRequest;
import com.alipay.mobile.common.transport.http.HttpWorker;
import com.alipay.mobile.common.transport.io.RpcBufferedInputStream;
import com.alipay.mobile.common.transport.monitor.RPCDataItems;
import com.alipay.mobile.common.transport.utils.DataItemsUtil;
import com.alipay.mobile.common.transport.utils.LogCatUtil;
import com.alipay.mobile.common.transport.utils.MonitorLogRecordUtil;
import com.alipay.mobile.common.transport.utils.NetworkAsyncTaskExecutor;
import com.alipay.mobile.monitor.api.MonitorFactory;
import java.io.InputStream;

public class NetworkInputStreamWrapper extends RpcBufferedInputStream {
    private boolean a = false;
    private HttpManager b;
    private long c = -1;
    private HttpWorker d;
    private Throwable e;
    protected TransportContext mTransportContext;

    public NetworkInputStreamWrapper(InputStream in, TransportContext transportContext, HttpManager httpManager, HttpWorker httpWorker) {
        super(in);
        this.mTransportContext = transportContext;
        this.b = httpManager;
        this.d = httpWorker;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0025, code lost:
        if (r10.e != null) goto L_0x0027;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x0062, code lost:
        if (r10.e != null) goto L_0x0064;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized int read(byte[] r11, int r12, int r13) {
        /*
            r10 = this;
            r9 = -1
            monitor-enter(r10)
            long r5 = r10.c     // Catch:{ all -> 0x006c }
            r7 = -1
            int r5 = (r5 > r7 ? 1 : (r5 == r7 ? 0 : -1))
            if (r5 != 0) goto L_0x0010
            long r5 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x006c }
            r10.c = r5     // Catch:{ all -> 0x006c }
        L_0x0010:
            r4 = -1
            long r1 = java.lang.System.currentTimeMillis()     // Catch:{ IOException -> 0x0030, Throwable -> 0x006f }
            int r4 = super.read(r11, r12, r13)     // Catch:{ IOException -> 0x0030, Throwable -> 0x006f }
            com.alipay.mobile.common.transport.monitor.networkqos.AlipayQosService r5 = com.alipay.mobile.common.transport.monitor.networkqos.AlipayQosService.getInstance()     // Catch:{ IOException -> 0x0030, Throwable -> 0x006f }
            r6 = 5
            r5.estimateByStartTime(r1, r6)     // Catch:{ IOException -> 0x0030, Throwable -> 0x006f }
            if (r4 == r9) goto L_0x0027
            java.lang.Throwable r5 = r10.e     // Catch:{ all -> 0x006c }
            if (r5 == 0) goto L_0x002e
        L_0x0027:
            boolean r5 = r10.a     // Catch:{ all -> 0x006c }
            if (r5 != 0) goto L_0x002e
            r10.monitorLog()     // Catch:{ all -> 0x006c }
        L_0x002e:
            monitor-exit(r10)
            return r4
        L_0x0030:
            r0 = move-exception
            java.lang.String r5 = "NetworkInputStreamWrapper"
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ all -> 0x005d }
            java.lang.String r7 = "read ex:"
            r6.<init>(r7)     // Catch:{ all -> 0x005d }
            java.lang.String r7 = r0.toString()     // Catch:{ all -> 0x005d }
            java.lang.StringBuilder r6 = r6.append(r7)     // Catch:{ all -> 0x005d }
            java.lang.String r6 = r6.toString()     // Catch:{ all -> 0x005d }
            com.alipay.mobile.common.transport.utils.LogCatUtil.debug(r5, r6)     // Catch:{ all -> 0x005d }
            boolean r5 = r0 instanceof java.net.SocketTimeoutException     // Catch:{ all -> 0x005d }
            if (r5 == 0) goto L_0x005a
            com.alipay.mobile.common.transport.monitor.networkqos.AlipayQosService r5 = com.alipay.mobile.common.transport.monitor.networkqos.AlipayQosService.getInstance()     // Catch:{ all -> 0x005d }
            r6 = 4662219572839972864(0x40b3880000000000, double:5000.0)
            r8 = 5
            r5.estimate(r6, r8)     // Catch:{ all -> 0x005d }
        L_0x005a:
            r10.e = r0     // Catch:{ all -> 0x005d }
            throw r0     // Catch:{ all -> 0x005d }
        L_0x005d:
            r5 = move-exception
            if (r4 == r9) goto L_0x0064
            java.lang.Throwable r6 = r10.e     // Catch:{ all -> 0x006c }
            if (r6 == 0) goto L_0x006b
        L_0x0064:
            boolean r6 = r10.a     // Catch:{ all -> 0x006c }
            if (r6 != 0) goto L_0x006b
            r10.monitorLog()     // Catch:{ all -> 0x006c }
        L_0x006b:
            throw r5     // Catch:{ all -> 0x006c }
        L_0x006c:
            r5 = move-exception
            monitor-exit(r10)
            throw r5
        L_0x006f:
            r0 = move-exception
            r10.e = r0     // Catch:{ all -> 0x005d }
            java.io.InterruptedIOException r3 = new java.io.InterruptedIOException     // Catch:{ all -> 0x005d }
            java.lang.String r5 = r0.getMessage()     // Catch:{ all -> 0x005d }
            r3.<init>(r5)     // Catch:{ all -> 0x005d }
            r3.initCause(r0)     // Catch:{ all -> 0x005d }
            throw r3     // Catch:{ all -> 0x005d }
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.common.transport.h5.NetworkInputStreamWrapper.read(byte[], int, int):int");
    }

    public void close() {
        LogCatUtil.warn((String) "NetworkInputStreamWrapper", (String) "H5InputStream invoke close.");
        if (!this.a) {
            monitorLog();
        }
        super.close();
    }

    /* access modifiers changed from: protected */
    public void monitorLog() {
        long startTime = System.currentTimeMillis();
        try {
            this.a = true;
            this.mTransportContext.getCurrentDataContainer().timeItemRelease("ALL_TIME");
            long readTiming = System.currentTimeMillis() - this.c;
            if (this.c == -1) {
                readTiming = 0;
            }
            this.mTransportContext.getCurrentDataContainer().putDataItem(RPCDataItems.READ_TIME, String.valueOf(readTiming));
            if (c()) {
                a();
            } else {
                b();
            }
        } catch (Exception e2) {
            LogCatUtil.error((String) "NetworkInputStreamWrapper", (Throwable) e2);
        } finally {
            r6 = "NetworkInputStreamWrapper";
            r8 = "cost:";
            LogCatUtil.info(r6, (System.currentTimeMillis() - startTime));
        }
    }

    private void a() {
        NetworkAsyncTaskExecutor.executeHighSerial(new Runnable() {
            public void run() {
                NetworkInputStreamWrapper.this.b();
            }
        });
    }

    /* access modifiers changed from: private */
    public void b() {
        DataflowModel dataflowModel;
        if (this.e != null) {
            LogCatUtil.error("NetworkInputStreamWrapper", "Read fail, exception: ", this.e);
            this.mTransportContext.getCurrentDataContainer().putDataItem("ERROR", this.e.toString());
        }
        if (this.d.getOriginRequest().isCanceled()) {
            DataItemsUtil.putDataItem2DataContainer(this.mTransportContext.getCurrentDataContainer(), RPCDataItems.CANCEL, "T");
        }
        this.mTransportContext.getCurrentDataContainer().putDataItem("RES_SIZE", String.valueOf(getReaded()));
        this.d.doMonitorLog();
        try {
            DataflowID type = DataflowID.HTTPCLIENT_H5;
            if (this.mTransportContext.bizType == 3) {
                type = DataflowID.HTTPCLIENT_DJANGO;
            }
            if (this.mTransportContext.bizType == 5) {
                type = DataflowID.HTTPCLIENT_MDAP;
            }
            String url = this.mTransportContext.url;
            String reqLength = this.mTransportContext.getCurrentDataContainer().getDataItem("REQ_SIZE");
            dataflowModel = DataflowModel.obtain(type, url, !TextUtils.isEmpty(reqLength) ? Long.valueOf(reqLength).longValue() : 0, (long) getReaded(), null);
            dataflowModel.isUpload = false;
            dataflowModel.bizId = this.d.getOriginRequest().getTag("bizId");
        } catch (Throwable e2) {
            LoggerFactory.getTraceLogger().warn("noteTraficConsume", "NetworkISW.outer", e2);
        }
        try {
            dataflowModel.reqHeaders = this.d.getOriginRequest().getHttpUriRequest().getAllHeaders();
        } catch (Throwable t) {
            LoggerFactory.getTraceLogger().error((String) "noteTraficConsume", "NetworkISW.reqHeaders: " + t);
        }
        try {
            dataflowModel.respHeaders = this.d.getHttpResponse().getAllHeaders();
        } catch (Throwable t2) {
            LoggerFactory.getTraceLogger().error((String) "noteTraficConsume", "NetworkISW.respHeaders: " + t2);
        }
        HttpUrlRequest originRequest = this.d.getOriginRequest();
        if (originRequest != null && (originRequest instanceof H5HttpUrlRequest)) {
            MonitorLogRecordUtil.recordCtrlPrintURLFlagToDataflow(dataflowModel, ((H5HttpUrlRequest) originRequest).isPrintUrlToMonitorLog());
        }
        MonitorFactory.getMonitorContext().noteTraficConsume(dataflowModel);
        String dataItem = this.mTransportContext.getCurrentDataContainer().getDataItem(RPCDataItems.READ_TIME);
        if (!TextUtils.isEmpty(dataItem)) {
            try {
                this.b.addSocketTime(Long.parseLong(dataItem));
            } catch (Throwable th) {
            }
        }
    }

    private boolean c() {
        try {
            HttpUrlRequest originRequest = this.d.getOriginRequest();
            if (originRequest == null || !(originRequest instanceof H5HttpUrlRequest)) {
                return false;
            }
            return ((H5HttpUrlRequest) originRequest).isAsyncMonitorLog();
        } catch (Throwable e2) {
            LogCatUtil.error("NetworkInputStreamWrapper", "isAsyncMonitorLog error. ", e2);
            return false;
        }
    }
}
