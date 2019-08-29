package com.taobao.tao.remotebusiness.handler;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.taobao.tao.remotebusiness.IRemoteCacheListener;
import com.taobao.tao.remotebusiness.IRemoteListener;
import com.taobao.tao.remotebusiness.IRemoteProcessListener;
import com.taobao.tao.remotebusiness.MtopBusiness;
import mtopsdk.common.util.TBSdkLog;
import mtopsdk.common.util.TBSdkLog.LogEnable;
import mtopsdk.mtop.util.MtopStatistics;
import mtopsdk.mtop.util.MtopStatistics.a;

public class HandlerMgr extends Handler {
    public static final int ON_CACHED = 4;
    public static final int ON_DATA_RECEIVED = 1;
    public static final int ON_FINISHED = 3;
    public static final int ON_HEADER = 2;
    private static final String TAG = "mtopsdk.HandlerMgr";
    private static volatile Handler mHandler;

    private HandlerMgr(Looper looper) {
        super(looper);
    }

    public static Handler instance() {
        if (mHandler == null) {
            synchronized (HandlerMgr.class) {
                try {
                    if (mHandler == null) {
                        mHandler = new HandlerMgr(Looper.getMainLooper());
                    }
                }
            }
        }
        return mHandler;
    }

    public void handleMessage(Message message) {
        a aVar;
        MtopStatistics mtopStatistics;
        HandlerParam handlerParam = (HandlerParam) message.obj;
        if (checkBeforeCallback(handlerParam)) {
            String seqNo = handlerParam.mtopBusiness.getSeqNo();
            Object reqContext = handlerParam.mtopBusiness.getReqContext();
            switch (message.what) {
                case 1:
                    if (TBSdkLog.a(LogEnable.InfoEnable)) {
                        TBSdkLog.b((String) TAG, seqNo, (String) "onReceive: ON_DATA_RECEIVED.");
                    }
                    try {
                        ((IRemoteProcessListener) handlerParam.listener).onDataReceived((fex) handlerParam.event, reqContext);
                        break;
                    } catch (Throwable th) {
                        TBSdkLog.b(TAG, seqNo, "listener onDataReceived callback error.", th);
                        break;
                    }
                case 2:
                    if (TBSdkLog.a(LogEnable.InfoEnable)) {
                        TBSdkLog.b((String) TAG, seqNo, (String) "onReceive: ON_HEADER.");
                    }
                    try {
                        ((IRemoteProcessListener) handlerParam.listener).onHeader((fev) handlerParam.event, reqContext);
                        break;
                    } catch (Throwable th2) {
                        TBSdkLog.b(TAG, seqNo, "listener onHeader callback error.", th2);
                        break;
                    }
                case 3:
                    if (TBSdkLog.a(LogEnable.InfoEnable)) {
                        TBSdkLog.b((String) TAG, seqNo, (String) "onReceive: ON_FINISHED.");
                    }
                    long currentTimeMillis = System.currentTimeMillis();
                    long j = 0;
                    if (handlerParam.mtopResponse != null) {
                        mtopStatistics = handlerParam.mtopResponse.getMtopStat();
                        if (mtopStatistics != null) {
                            aVar = mtopStatistics.d();
                            aVar.i = currentTimeMillis - handlerParam.mtopBusiness.onBgFinishTime;
                            if (handlerParam.mtopResponse.getBytedata() != null) {
                                j = (long) handlerParam.mtopResponse.getBytedata().length;
                            }
                        } else {
                            aVar = null;
                        }
                    } else {
                        mtopStatistics = null;
                        aVar = null;
                    }
                    handlerParam.mtopBusiness.doFinish(handlerParam.mtopResponse, handlerParam.pojo);
                    if (TBSdkLog.a(LogEnable.InfoEnable)) {
                        StringBuilder sb = new StringBuilder(128);
                        sb.append("onReceive: ON_FINISHED. doFinishTime=");
                        sb.append(System.currentTimeMillis() - currentTimeMillis);
                        sb.append("; dataSize=");
                        sb.append(j);
                        sb.append("; ");
                        if (aVar != null) {
                            sb.append(aVar.toString());
                        }
                        TBSdkLog.b((String) TAG, seqNo, sb.toString());
                    }
                    if (mtopStatistics != null) {
                        mtopStatistics.c();
                        break;
                    }
                    break;
                case 4:
                    if (TBSdkLog.a(LogEnable.InfoEnable)) {
                        TBSdkLog.b((String) TAG, seqNo, (String) "onReceive: ON_CACHED");
                    }
                    fer fer = (fer) handlerParam.event;
                    if (fer != null) {
                        if (fer.a != null) {
                            MtopStatistics mtopStat = fer.a.getMtopStat();
                            if (mtopStat != null) {
                                a d = mtopStat.d();
                                d.i = System.currentTimeMillis() - handlerParam.mtopBusiness.onBgFinishTime;
                                if (TBSdkLog.a(LogEnable.DebugEnable)) {
                                    TBSdkLog.a((String) TAG, seqNo, d.toString());
                                }
                                mtopStat.c();
                            }
                        }
                        try {
                            if (!(handlerParam.listener instanceof IRemoteCacheListener)) {
                                TBSdkLog.b((String) TAG, handlerParam.mtopBusiness.getSeqNo(), (String) "listener onCached transfer to onSuccess callback");
                                ((IRemoteListener) handlerParam.listener).onSuccess(handlerParam.mtopBusiness.getRequestType(), handlerParam.mtopResponse, handlerParam.pojo, reqContext);
                                break;
                            } else {
                                TBSdkLog.b((String) TAG, seqNo, (String) "listener onCached callback");
                                ((IRemoteCacheListener) handlerParam.listener).onCached(fer, handlerParam.pojo, reqContext);
                                break;
                            }
                        } catch (Throwable th3) {
                            TBSdkLog.b(TAG, seqNo, "listener onCached callback error.", th3);
                            break;
                        }
                    } else {
                        TBSdkLog.d(TAG, seqNo, "HandlerMsg.event is null.");
                        return;
                    }
            }
            message.obj = null;
        }
    }

    public static HandlerParam getHandlerMsg(few few, fet fet, MtopBusiness mtopBusiness) {
        return new HandlerParam(few, fet, mtopBusiness);
    }

    private boolean checkBeforeCallback(HandlerParam handlerParam) {
        if (handlerParam == null) {
            TBSdkLog.d(TAG, "", "HandlerMsg is null.");
            return false;
        }
        if (handlerParam.mtopBusiness != null) {
            String seqNo = handlerParam.mtopBusiness.getSeqNo();
            if (handlerParam.mtopBusiness.isTaskCanceled()) {
                TBSdkLog.b((String) TAG, seqNo, (String) "The request of MtopBusiness is cancelled.");
                return false;
            }
        }
        return true;
    }
}
