package com.taobao.tao.remotebusiness.listener;

import com.taobao.tao.remotebusiness.IRemoteCacheListener;
import com.taobao.tao.remotebusiness.IRemoteListener;
import com.taobao.tao.remotebusiness.MtopBusiness;
import com.taobao.tao.remotebusiness.handler.HandlerMgr;
import com.taobao.tao.remotebusiness.handler.HandlerParam;
import mtopsdk.common.util.TBSdkLog;
import mtopsdk.common.util.TBSdkLog.LogEnable;
import mtopsdk.mtop.domain.BaseOutDo;
import mtopsdk.mtop.domain.MtopResponse;
import mtopsdk.mtop.util.MtopStatistics;
import mtopsdk.mtop.util.MtopStatistics.a;

class MtopCacheListenerImpl extends MtopBaseListener implements a {
    private static final String TAG = "mtopsdk.MtopCacheListenerImpl";

    public MtopCacheListenerImpl(MtopBusiness mtopBusiness, few few) {
        super(mtopBusiness, few);
    }

    public void onCached(fer fer, Object obj) {
        String seqNo = this.mtopBusiness.getSeqNo();
        if (TBSdkLog.a(LogEnable.InfoEnable)) {
            StringBuilder sb = new StringBuilder("Mtop onCached event received. apiKey=");
            sb.append(this.mtopBusiness.request.getKey());
            TBSdkLog.b((String) TAG, seqNo, sb.toString());
        }
        if (this.mtopBusiness.isTaskCanceled()) {
            if (TBSdkLog.a(LogEnable.InfoEnable)) {
                TBSdkLog.b((String) TAG, seqNo, (String) "The request of MtopBusiness is cancelled.");
            }
        } else if (this.listener == null) {
            TBSdkLog.d(TAG, seqNo, "The listener of MtopBusiness is null.");
        } else if (fer == null) {
            TBSdkLog.d(TAG, seqNo, "MtopCacheEvent is null.");
        } else {
            MtopResponse mtopResponse = fer.a;
            if (mtopResponse == null) {
                TBSdkLog.d(TAG, seqNo, "The MtopResponse of MtopCacheEvent is null.");
                return;
            }
            long currentTimeMillis = System.currentTimeMillis();
            long currentTimeMillis2 = System.currentTimeMillis();
            a aVar = null;
            BaseOutDo a = (!mtopResponse.isApiSuccess() || this.mtopBusiness.clazz == null) ? null : ffx.a(mtopResponse, this.mtopBusiness.clazz);
            long currentTimeMillis3 = System.currentTimeMillis();
            this.mtopBusiness.onBgFinishTime = currentTimeMillis3;
            MtopStatistics mtopStat = mtopResponse.getMtopStat();
            if (mtopStat != null) {
                aVar = mtopStat.d();
                aVar.f = currentTimeMillis3 - currentTimeMillis2;
                aVar.g = aVar.f;
                aVar.j = 1;
                aVar.a = currentTimeMillis - this.mtopBusiness.sendStartTime;
                aVar.d = this.mtopBusiness.onBgFinishTime - this.mtopBusiness.reqStartTime;
                aVar.e = aVar.d;
            }
            HandlerParam handlerMsg = HandlerMgr.getHandlerMsg(this.listener, fer, this.mtopBusiness);
            handlerMsg.pojo = a;
            handlerMsg.mtopResponse = mtopResponse;
            this.mtopBusiness.isCached = true;
            if (this.mtopBusiness.mtopProp.handler != null) {
                if (TBSdkLog.a(LogEnable.InfoEnable)) {
                    TBSdkLog.b((String) TAG, seqNo, (String) "onReceive: ON_CACHED in self-defined handler.");
                }
                if (mtopStat != null) {
                    if (aVar != null && TBSdkLog.a(LogEnable.DebugEnable)) {
                        TBSdkLog.a((String) TAG, seqNo, aVar.toString());
                    }
                    mtopStat.c();
                }
                try {
                    if (handlerMsg.listener instanceof IRemoteCacheListener) {
                        TBSdkLog.b((String) TAG, seqNo, (String) "listener onCached callback");
                        ((IRemoteCacheListener) handlerMsg.listener).onCached(fer, handlerMsg.pojo, obj);
                        return;
                    }
                    TBSdkLog.b((String) TAG, seqNo, (String) "listener onCached transfer to onSuccess callback");
                    ((IRemoteListener) handlerMsg.listener).onSuccess(handlerMsg.mtopBusiness.getRequestType(), handlerMsg.mtopResponse, handlerMsg.pojo, obj);
                } catch (Throwable th) {
                    TBSdkLog.b(TAG, seqNo, "listener onCached callback error in self-defined handler.", th);
                }
            } else {
                HandlerMgr.instance().obtainMessage(4, handlerMsg).sendToTarget();
            }
        }
    }
}
