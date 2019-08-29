package com.taobao.tao.remotebusiness.listener;

import com.taobao.tao.remotebusiness.IRemoteProcessListener;
import com.taobao.tao.remotebusiness.MtopBusiness;
import com.taobao.tao.remotebusiness.handler.HandlerMgr;
import mtopsdk.common.util.TBSdkLog;
import mtopsdk.common.util.TBSdkLog.LogEnable;

class MtopProgressListenerImpl extends MtopBaseListener implements c, d {
    private static final String TAG = "mtopsdk.MtopProgressListenerImpl";

    public MtopProgressListenerImpl(MtopBusiness mtopBusiness, few few) {
        super(mtopBusiness, few);
    }

    public void onDataReceived(fex fex, Object obj) {
        String seqNo = this.mtopBusiness.getSeqNo();
        if (TBSdkLog.a(LogEnable.InfoEnable)) {
            TBSdkLog.b((String) TAG, seqNo, (String) "Mtop onDataReceived event received.");
        }
        if (this.mtopBusiness.isTaskCanceled()) {
            if (TBSdkLog.a(LogEnable.InfoEnable)) {
                TBSdkLog.b((String) TAG, seqNo, (String) "The request of MtopBusiness is cancelled.");
            }
            return;
        }
        if (this.listener instanceof IRemoteProcessListener) {
            if (this.mtopBusiness.mtopProp.handler != null) {
                if (TBSdkLog.a(LogEnable.InfoEnable)) {
                    TBSdkLog.b((String) TAG, seqNo, (String) "onReceive: ON_DATA_RECEIVED in self-defined handler.");
                }
                try {
                    ((IRemoteProcessListener) this.listener).onDataReceived(fex, obj);
                } catch (Throwable th) {
                    TBSdkLog.b(TAG, seqNo, "listener onDataReceived callback error in self-defined handler.", th);
                }
            } else {
                HandlerMgr.instance().obtainMessage(1, HandlerMgr.getHandlerMsg(this.listener, fex, this.mtopBusiness)).sendToTarget();
            }
        }
    }

    public void onHeader(fev fev, Object obj) {
        String seqNo = this.mtopBusiness.getSeqNo();
        if (TBSdkLog.a(LogEnable.InfoEnable)) {
            TBSdkLog.b((String) TAG, seqNo, (String) "Mtop onHeader event received.");
        }
        if (this.mtopBusiness.isTaskCanceled()) {
            if (TBSdkLog.a(LogEnable.InfoEnable)) {
                TBSdkLog.b((String) TAG, seqNo, (String) "The request of MtopBusiness is cancelled.");
            }
            return;
        }
        if (this.listener instanceof IRemoteProcessListener) {
            if (this.mtopBusiness.mtopProp.handler != null) {
                if (TBSdkLog.a(LogEnable.InfoEnable)) {
                    TBSdkLog.b((String) TAG, seqNo, (String) "onReceive: ON_HEADER in self-defined handler.");
                }
                try {
                    ((IRemoteProcessListener) this.listener).onHeader(fev, obj);
                } catch (Throwable th) {
                    TBSdkLog.b(TAG, seqNo, "listener onHeader callback error in self-defined handler.", th);
                }
            } else {
                HandlerMgr.instance().obtainMessage(2, HandlerMgr.getHandlerMsg(this.listener, fev, this.mtopBusiness)).sendToTarget();
            }
        }
    }
}
