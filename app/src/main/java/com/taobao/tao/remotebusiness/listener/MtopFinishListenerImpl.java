package com.taobao.tao.remotebusiness.listener;

import com.taobao.tao.remotebusiness.IRemoteParserListener;
import com.taobao.tao.remotebusiness.MtopBusiness;
import com.taobao.tao.remotebusiness.handler.HandlerMgr;
import com.taobao.tao.remotebusiness.handler.HandlerParam;
import mtopsdk.common.util.TBSdkLog;
import mtopsdk.common.util.TBSdkLog.LogEnable;
import mtopsdk.mtop.domain.MtopResponse;
import mtopsdk.mtop.util.MtopStatistics;
import mtopsdk.mtop.util.MtopStatistics.a;

class MtopFinishListenerImpl extends MtopBaseListener implements b {
    private static final String TAG = "mtopsdk.MtopFinishListenerImpl";

    public MtopFinishListenerImpl(MtopBusiness mtopBusiness, few few) {
        super(mtopBusiness, few);
    }

    public void onFinished(feu feu, Object obj) {
        long j;
        String seqNo = this.mtopBusiness.getSeqNo();
        if (TBSdkLog.a(LogEnable.InfoEnable)) {
            TBSdkLog.b((String) TAG, seqNo, (String) "Mtop onFinished event received.");
        }
        if (this.mtopBusiness.isTaskCanceled()) {
            if (TBSdkLog.a(LogEnable.InfoEnable)) {
                TBSdkLog.b((String) TAG, seqNo, (String) "The request of MtopBusiness is canceled.");
            }
        } else if (this.listener == null) {
            TBSdkLog.d(TAG, seqNo, "The listener of MtopBusiness is null.");
        } else if (feu == null) {
            TBSdkLog.d(TAG, seqNo, "MtopFinishEvent is null.");
        } else {
            MtopResponse mtopResponse = feu.a;
            if (mtopResponse == null) {
                TBSdkLog.d(TAG, seqNo, "The MtopResponse of MtopFinishEvent is null.");
                return;
            }
            long currentTimeMillis = System.currentTimeMillis();
            if (this.listener instanceof IRemoteParserListener) {
                try {
                    ((IRemoteParserListener) this.listener).parseResponse(mtopResponse);
                } catch (Exception e) {
                    TBSdkLog.b(TAG, seqNo, "listener parseResponse callback error.", e);
                }
            }
            HandlerParam handlerMsg = HandlerMgr.getHandlerMsg(this.listener, feu, this.mtopBusiness);
            handlerMsg.mtopResponse = mtopResponse;
            long currentTimeMillis2 = System.currentTimeMillis();
            if (!mtopResponse.isApiSuccess() || this.mtopBusiness.clazz == null) {
                j = currentTimeMillis2;
            } else {
                handlerMsg.pojo = ffx.a(mtopResponse, this.mtopBusiness.clazz);
                j = System.currentTimeMillis();
            }
            this.mtopBusiness.onBgFinishTime = j;
            MtopStatistics mtopStat = mtopResponse.getMtopStat();
            a aVar = null;
            if (mtopStat != null) {
                aVar = mtopStat.d();
                aVar.b = this.mtopBusiness.sendStartTime - this.mtopBusiness.reqStartTime;
                aVar.a = currentTimeMillis - this.mtopBusiness.sendStartTime;
                aVar.c = this.mtopBusiness.onBgFinishTime - currentTimeMillis;
                aVar.h = currentTimeMillis2 - currentTimeMillis;
                aVar.f = j - currentTimeMillis2;
                aVar.g = aVar.f;
                aVar.d = this.mtopBusiness.onBgFinishTime - this.mtopBusiness.reqStartTime;
                aVar.e = aVar.d;
            }
            if (this.mtopBusiness.mtopProp.handler != null) {
                if (TBSdkLog.a(LogEnable.InfoEnable)) {
                    TBSdkLog.b((String) TAG, seqNo, (String) "onReceive: ON_FINISHED in self-defined handler.");
                }
                long currentTimeMillis3 = System.currentTimeMillis();
                handlerMsg.mtopBusiness.doFinish(handlerMsg.mtopResponse, handlerMsg.pojo);
                if (TBSdkLog.a(LogEnable.InfoEnable)) {
                    long j2 = 0;
                    if (handlerMsg.mtopResponse.getBytedata() != null) {
                        j2 = (long) handlerMsg.mtopResponse.getBytedata().length;
                    }
                    StringBuilder sb = new StringBuilder(128);
                    sb.append("onReceive: ON_FINISHED in self-defined handler.doFinishTime=");
                    sb.append(System.currentTimeMillis() - currentTimeMillis3);
                    sb.append(", dataSize=");
                    sb.append(j2);
                    sb.append("; ");
                    if (aVar != null) {
                        sb.append(aVar.toString());
                    }
                    TBSdkLog.b((String) TAG, seqNo, sb.toString());
                }
                if (mtopStat != null) {
                    mtopStat.c();
                }
                return;
            }
            HandlerMgr.instance().obtainMessage(3, handlerMsg).sendToTarget();
        }
    }
}
