package com.taobao.tao.remotebusiness;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.taobao.tao.remotebusiness.handler.HandlerMgr;
import com.taobao.tao.remotebusiness.handler.HandlerParam;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import mtopsdk.common.util.TBSdkLog;
import mtopsdk.common.util.TBSdkLog.LogEnable;
import mtopsdk.mtop.domain.MtopResponse;
import mtopsdk.mtop.intf.Mtop;

public class RequestPool {
    private static final String DEFAULT_BIZINFO = "DEFAULT";
    public static final String TAG = "mtopsdk.RequestPool";
    private static Lock lock = new ReentrantLock();
    private static Map<String, List<MtopBusiness>> requestPool = new HashMap();

    public static void addToRequestPool(@NonNull Mtop mtop, @Nullable String str, MtopBusiness mtopBusiness) {
        lock.lock();
        try {
            String requestPoolKey = getRequestPoolKey(mtop, str);
            List list = requestPool.get(requestPoolKey);
            if (list == null) {
                list = new ArrayList();
            }
            list.add(mtopBusiness);
            requestPool.put(requestPoolKey, list);
            if (TBSdkLog.a(LogEnable.InfoEnable)) {
                StringBuilder sb = new StringBuilder(64);
                sb.append(requestPoolKey);
                sb.append(" [addToRequestPool] add mtopBusiness to RequestPool.");
                TBSdkLog.b((String) TAG, mtopBusiness.getSeqNo(), sb.toString());
            }
        } finally {
            lock.unlock();
        }
    }

    public static void removeFromRequestPool(@NonNull Mtop mtop, @Nullable String str, MtopBusiness mtopBusiness) {
        lock.lock();
        try {
            String requestPoolKey = getRequestPoolKey(mtop, str);
            List list = requestPool.get(requestPoolKey);
            if (list != null) {
                list.remove(mtopBusiness);
            }
            if (TBSdkLog.a(LogEnable.InfoEnable)) {
                StringBuilder sb = new StringBuilder(64);
                sb.append(requestPoolKey);
                sb.append(" [removeFromRequestPool] remove mtopBusiness from RequestPool.");
                TBSdkLog.b((String) TAG, mtopBusiness.getSeqNo(), sb.toString());
            }
        } catch (Exception unused) {
        } catch (Throwable th) {
            lock.unlock();
            throw th;
        }
        lock.unlock();
    }

    public static void retryAllRequest(@NonNull Mtop mtop, @Nullable String str) {
        lock.lock();
        try {
            String requestPoolKey = getRequestPoolKey(mtop, str);
            List<MtopBusiness> remove = requestPool.remove(requestPoolKey);
            if (remove != null) {
                if (TBSdkLog.a(LogEnable.InfoEnable)) {
                    StringBuilder sb = new StringBuilder(64);
                    sb.append(requestPoolKey);
                    StringBuilder sb2 = new StringBuilder(" [retryAllRequest] retry all request,current size=");
                    sb2.append(remove.size());
                    sb.append(sb2.toString());
                    TBSdkLog.b(TAG, sb.toString());
                }
                for (MtopBusiness retryRequest : remove) {
                    retryRequest.retryRequest(str);
                }
                lock.unlock();
            }
        } finally {
            lock.unlock();
        }
    }

    public static void failAllRequest(@NonNull Mtop mtop, @Nullable String str, String str2, String str3) {
        MtopResponse mtopResponse;
        lock.lock();
        try {
            String requestPoolKey = getRequestPoolKey(mtop, str);
            List<MtopBusiness> remove = requestPool.remove(requestPoolKey);
            if (remove == null) {
                lock.unlock();
                return;
            }
            if (TBSdkLog.a(LogEnable.InfoEnable)) {
                StringBuilder sb = new StringBuilder(64);
                sb.append(requestPoolKey);
                StringBuilder sb2 = new StringBuilder(" [failAllRequest]fail all request,current size=");
                sb2.append(remove.size());
                sb.append(sb2.toString());
                TBSdkLog.b(TAG, sb.toString());
            }
            for (MtopBusiness mtopBusiness : remove) {
                if (mtopBusiness.request != null) {
                    mtopResponse = new MtopResponse(mtopBusiness.request.getApiName(), mtopBusiness.request.getVersion(), str2, str3);
                } else {
                    mtopResponse = new MtopResponse(str2, str3);
                }
                fff.a();
                if (fff.b()) {
                    fdf createMtopContext = mtopBusiness.createMtopContext(mtopBusiness.listener);
                    createMtopContext.c = mtopResponse;
                    fed.a.a(createMtopContext);
                }
                HandlerParam handlerMsg = HandlerMgr.getHandlerMsg(null, null, mtopBusiness);
                handlerMsg.mtopResponse = mtopResponse;
                HandlerMgr.instance().obtainMessage(3, handlerMsg).sendToTarget();
            }
            lock.unlock();
        } catch (Exception e) {
            StringBuilder sb3 = new StringBuilder("[failAllRequest] do ErrorCode Mapping error.apiKey=");
            sb3.append(mtopResponse.getFullKey());
            TBSdkLog.b((String) TAG, sb3.toString(), (Throwable) e);
        } catch (Throwable th) {
            lock.unlock();
            throw th;
        }
    }

    private static String getRequestPoolKey(@NonNull Mtop mtop, @Nullable String str) {
        if (fdd.b(str)) {
            str = DEFAULT_BIZINFO;
        }
        return fdd.a(mtop.b, str);
    }
}
