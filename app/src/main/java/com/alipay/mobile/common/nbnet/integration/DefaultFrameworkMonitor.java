package com.alipay.mobile.common.nbnet.integration;

import com.alipay.mobile.common.logging.api.monitor.DataflowID;
import com.alipay.mobile.common.logging.api.monitor.DataflowModel;
import com.alipay.mobile.common.logging.api.monitor.Performance;
import com.alipay.mobile.common.nbnet.api.ExtInfoConstans;
import com.alipay.mobile.common.nbnet.api.NBNetContext;
import com.alipay.mobile.common.nbnet.api.download.NBNetDownloadRequest;
import com.alipay.mobile.common.nbnet.api.upload.NBNetUploadRequest;
import com.alipay.mobile.common.nbnet.api.upload.NBNetUploadResponse;
import com.alipay.mobile.common.nbnet.biz.download.DownloadEngine;
import com.alipay.mobile.common.nbnet.biz.log.FrameworkMonitorAdapter;
import com.alipay.mobile.common.nbnet.biz.log.MonitorLogUtil;
import com.alipay.mobile.common.nbnet.biz.log.NBNetLogCat;
import com.alipay.mobile.common.transport.monitor.MonitorLoggerUtils;
import com.alipay.mobile.common.transport.monitor.NetworkServiceTracer;
import com.alipay.mobile.common.transport.monitor.TransportPerformance;
import com.alipay.mobile.monitor.api.MonitorFactory;
import java.util.Map;
import java.util.Set;

public class DefaultFrameworkMonitor extends FrameworkMonitorAdapter {
    public final void a(NBNetContext nbNetContext) {
        try {
            Performance pf = new TransportPerformance();
            pf.setSubType("MASS");
            Set monitorKeySet = MonitorLogUtil.k(nbNetContext);
            if (monitorKeySet.isEmpty()) {
                NBNetLogCat.a((String) "DefaultFreamworkMonitor", (String) "monitorKeySet is empty!");
                return;
            }
            pf.setParam1(MonitorLoggerUtils.getLogBizType(pf.getSubType()));
            a(nbNetContext, pf);
            pf.setParam3((String) nbNetContext.getAttribute("nbnet.MONITOR_LOG_TYPE"));
            for (String monitorKey : monitorKeySet) {
                pf.getExtPramas().put(monitorKey, (String) nbNetContext.getAttribute(monitorKey));
            }
            if (MonitorLogUtil.l(nbNetContext) || (!MonitorLogUtil.l(nbNetContext) && !MonitorLogUtil.m(nbNetContext))) {
                MonitorLoggerUtils.uploadPerfLog(pf);
            }
            NBNetLogCat.c(pf.getSubType() + "_PERF", pf.toString() + "\n");
        } catch (Throwable e) {
            NBNetLogCat.b("DefaultFreamworkMonitor", "monitorLog exception. ", e);
        }
    }

    private static void a(NBNetContext nbNetContext, Performance pf) {
        if (MonitorLogUtil.l(nbNetContext)) {
            pf.setParam2("INFO");
        } else {
            pf.setParam2("FATAL");
        }
    }

    public final void a(int errCode, String errMsg, Map<String, String> extMap) {
        try {
            NetworkServiceTracer.getInstance().recordError(6, errCode, errMsg, extMap);
        } catch (Throwable ex) {
            NBNetLogCat.b("DefaultFreamworkMonitor", "reportUnusableError exception", ex);
        }
    }

    public final void a(DownloadEngine downloadEngine, NBNetDownloadRequest originalDownloadRequest) {
        if (downloadEngine != null) {
            try {
                int totalReceivedSize = downloadEngine.g();
                DataflowModel dataflowModel = DataflowModel.obtain(DataflowID.HTTPCLIENT_NBNET, String.valueOf(originalDownloadRequest.getRequestId()), (long) downloadEngine.h(), (long) totalReceivedSize, originalDownloadRequest.getExtInfo(ExtInfoConstans.KEY_MULTIMEDIA_LOG_MARK));
                dataflowModel.fileId = originalDownloadRequest.getFileId();
                dataflowModel.bizId = originalDownloadRequest.getBizType();
                dataflowModel.extParams = originalDownloadRequest.getExtMap();
                dataflowModel.isUpload = false;
                dataflowModel.putParam("NBN_KEY_TRACE_ID", originalDownloadRequest.getSessionId());
                MonitorFactory.getMonitorContext().noteTraficConsume(dataflowModel);
            } catch (Throwable e) {
                NBNetLogCat.a("DefaultFreamworkMonitor", "Download note trafic consume fail", e);
            }
        }
    }

    public final void a(int totalSendedSize, int totalReceivedSize, NBNetUploadRequest nbNetUploadRequest, NBNetUploadResponse uploadResponse) {
        try {
            DataflowModel dataflowModel = DataflowModel.obtain(DataflowID.HTTPCLIENT_NBNET, uploadResponse.getMd5(), (long) totalSendedSize, (long) totalReceivedSize, nbNetUploadRequest.getExtInfo(ExtInfoConstans.KEY_MULTIMEDIA_LOG_MARK));
            dataflowModel.fileId = uploadResponse.getFileId();
            dataflowModel.bizId = nbNetUploadRequest.getBizId();
            dataflowModel.isUpload = true;
            dataflowModel.extParams = nbNetUploadRequest.getExtMap();
            dataflowModel.putParam("NBN_KEY_TRACE_ID", uploadResponse.getTraceId());
            MonitorFactory.getMonitorContext().noteTraficConsume(dataflowModel);
        } catch (Throwable e) {
            NBNetLogCat.a("DefaultFreamworkMonitor", "Upload note trafic consume fail", e);
        }
    }

    public final boolean a(String traficCheckUrl) {
        return MonitorFactory.getMonitorContext().isTraficConsumeAccept(DataflowID.HTTPCLIENT_NBNET, traficCheckUrl);
    }
}
