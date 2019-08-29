package com.alipay.edge.impl;

import android.content.Context;
import com.alibaba.wireless.security.SecExceptionCode;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.log.api.trace.TraceLogger;
import com.alipay.apmobilesecuritysdk.commonbiz.ApplistUtil;
import com.alipay.apmobilesecuritysdk.commonbiz.OnlineHostConfig;
import com.alipay.apmobilesecuritysdk.commonbiz.monitor.LogAgent;
import com.alipay.edge.EdgeRiskService;
import com.alipay.edge.face.EdgeRiskResult;
import com.alipay.edge.impl.EdgeNativeBridge.T0DeviceDataListener;
import com.alipay.edge.rpc.util.RpcManager;
import com.alipay.edge.rpc.util.RpcManager.EdgeResponseModel;
import com.alipay.edge.utils.EdgeStorageUtils;
import com.alipay.edge.utils.FindHook;
import com.alipay.rdssecuritysdk.v2.face.RDSClient;
import com.alipay.security.mobile.module.commonutils.CommonUtils;
import com.alipay.security.mobile.module.crypto.Base64Util;
import com.alipay.security.mobile.module.crypto.Hex;
import com.alipay.security.mobile.module.deviceinfo.DeviceInfo;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class EdgeRiskServiceImpl extends EdgeRiskService {
    private static final String DEF_LUA_SCRIPT = "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";
    private static final String TAG = "t0dbg";
    private static final String UA_KCART_CALL = "pay";
    private static AtomicInteger hasEdgeData = new AtomicInteger(0);
    /* access modifiers changed from: private */
    public static TraceLogger logger = LoggerFactory.f();
    private EdgeSwitchManager edgeSwitchManager = null;
    private AtomicBoolean initialized = new AtomicBoolean(false);
    private AtomicBoolean isT0Initializing = new AtomicBoolean(false);
    private Context mContext;
    private RpcManager rpcManager = null;
    /* access modifiers changed from: private */
    public ExecutorService rpcThreadExcutor = Executors.newSingleThreadExecutor();
    private ReentrantLock updateResLock = new ReentrantLock();

    public int initialize(Context context) {
        this.mContext = context;
        this.edgeSwitchManager = EdgeSwitchManager.a(this.mContext);
        this.rpcManager = RpcManager.a();
        if (this.edgeSwitchManager.h()) {
            EdgeStorageUtils.b(this.mContext);
        }
        if (this.edgeSwitchManager.e()) {
            int a = ErrorCode.a(401);
            LogAgent.a(ErrorCode.c(a), 0, -1, a);
            return a;
        } else if (!this.edgeSwitchManager.f()) {
            int a2 = ErrorCode.a(313);
            LogAgent.a(ErrorCode.c(a2), 0, -1, a2);
            if (this.edgeSwitchManager.g()) {
                LogAgent.a(false, true);
                this.edgeSwitchManager.c(false);
            }
            return a2;
        } else if (!CommonUtils.supportArmeabiV7()) {
            int a3 = ErrorCode.a(301);
            LogAgent.a(ErrorCode.c(a3), 0, -1, a3);
            return a3;
        } else if (!EdgeNativeBridge.isLoadOk()) {
            int a4 = ErrorCode.a(402);
            LogAgent.a(ErrorCode.c(a4), 0, -1, a4);
            return a4;
        } else if (this.isT0Initializing.getAndSet(true)) {
            int a5 = ErrorCode.a(SecExceptionCode.SEC_ERROR_STA_DECRYPT_MISMATCH_KEY_DATA);
            LogAgent.a(ErrorCode.c(a5), 0, -1, a5);
            return a5;
        } else if (this.initialized.get()) {
            return 0;
        } else {
            long currentTimeMillis = System.currentTimeMillis();
            int initializeSync = initializeSync();
            long currentTimeMillis2 = System.currentTimeMillis() - currentTimeMillis;
            if (ErrorCode.b(initializeSync)) {
                this.edgeSwitchManager.a(true);
                LogAgent.a(true, false);
                initializeSync = ErrorCode.a(401);
                LogAgent.a(ErrorCode.c(initializeSync), currentTimeMillis2, -1, initializeSync);
            } else if (initializeSync != 0) {
                LogAgent.a(ErrorCode.c(initializeSync), currentTimeMillis2, -1, initializeSync);
            } else {
                LogAgent.a(ErrorCode.c(initializeSync), currentTimeMillis2, (long) getEdgeStrategyVersion(), initializeSync);
                boolean sync = EdgeNativeBridge.getSync();
                if (-1 == getEdgeStrategyVersion()) {
                    LoggerFactory.f().b((String) TAG, (String) "initialize() updateResource inner lua");
                    updateResource(DEF_LUA_SCRIPT);
                }
                if (sync) {
                    LoggerFactory.f().b((String) TAG, (String) "initialize() needSync is true x");
                    syncWithServer();
                }
            }
            this.isT0Initializing.set(false);
            setHasEdgeData(EdgeStorageUtils.c(context));
            return initializeSync;
        }
    }

    private int initializeSync() {
        byte[] bArr = new byte[1];
        String a = EdgeStorageUtils.a(this.mContext);
        if (CommonUtils.isBlank(a)) {
            return ErrorCode.a(SecExceptionCode.SEC_ERROR_STA_KEY_NOT_EXISTED);
        }
        int initialize = EdgeNativeBridge.initialize(this.mContext, bArr, a, getVKeySecretIndex());
        if (initialize == 0) {
            this.initialized.set(true);
        }
        return initialize;
    }

    public int postUserAction(String str, Map<String, String> map) {
        if (!this.initialized.get()) {
            return ErrorCode.a(303);
        }
        if (this.edgeSwitchManager.f()) {
            return EdgeNativeBridge.postUserAction(str, formatMapParameter(map));
        }
        if (this.edgeSwitchManager.g()) {
            LogAgent.a(false, true);
            this.edgeSwitchManager.c(false);
        }
        return ErrorCode.a(313);
    }

    public EdgeRiskResult getRiskResult(String str, Map<String, String> map, int i) {
        String str2 = str;
        Map<String, String> map2 = map;
        LoggerFactory.f().b((String) TAG, (String) "call EdgeRiskResult getRiskResult");
        StringBuilder sb = new StringBuilder();
        if (map2 != null && map2.containsKey("longitude")) {
            sb.append(map2.get("longitude"));
        }
        sb.append("-");
        if (map2 != null && map2.containsKey("latitude")) {
            sb.append(map2.get("latitude"));
        }
        if (this.edgeSwitchManager != null && this.edgeSwitchManager.i()) {
            if (isHasEdgeData() == (isHasEdgeData() & EdgeStorageUtils.c(this.mContext))) {
                TraceLogger f = LoggerFactory.f();
                StringBuilder sb2 = new StringBuilder("check edge data is ");
                sb2.append(EdgeStorageUtils.c(this.mContext));
                f.b((String) TAG, sb2.toString());
                LoggerFactory.f().b((String) TAG, (String) "edge database check ok");
                if (FindHook.a(this.mContext)) {
                    LoggerFactory.f().b((String) TAG, (String) "isHook is true");
                    EdgeRiskResult edgeRiskResult = new EdgeRiskResult();
                    edgeRiskResult.status = ErrorCode.a(314);
                    LogAgent.a(ErrorCode.c(edgeRiskResult.status), (long) edgeRiskResult.result, 0, edgeRiskResult.sealedData, str2, -1, edgeRiskResult.status, sb.toString());
                    TraceLogger f2 = LoggerFactory.f();
                    StringBuilder sb3 = new StringBuilder("ua = ");
                    sb3.append(str2);
                    sb3.append(", result = ");
                    sb3.append(edgeRiskResult.result);
                    sb3.append(", sealed data = [");
                    sb3.append(edgeRiskResult.sealedData);
                    sb3.append("]");
                    f2.b((String) TAG, sb3.toString());
                    return edgeRiskResult;
                }
                LoggerFactory.f().b((String) TAG, (String) "isHook is false");
            } else {
                LoggerFactory.f().b((String) TAG, (String) "edge database check err");
                EdgeRiskResult edgeRiskResult2 = new EdgeRiskResult();
                edgeRiskResult2.status = ErrorCode.a(314);
                LogAgent.a(ErrorCode.c(edgeRiskResult2.status), (long) edgeRiskResult2.result, 0, edgeRiskResult2.sealedData, str2, -1, edgeRiskResult2.status, sb.toString());
                TraceLogger f3 = LoggerFactory.f();
                StringBuilder sb4 = new StringBuilder("ua = ");
                sb4.append(str2);
                sb4.append(", result = ");
                sb4.append(edgeRiskResult2.result);
                sb4.append(", sealed data = [");
                sb4.append(edgeRiskResult2.sealedData);
                sb4.append("]");
                f3.b((String) TAG, sb4.toString());
                return edgeRiskResult2;
            }
        }
        if (!this.initialized.get()) {
            EdgeRiskResult edgeRiskResult3 = new EdgeRiskResult();
            edgeRiskResult3.status = ErrorCode.a(303);
            LogAgent.a(ErrorCode.c(edgeRiskResult3.status), (long) edgeRiskResult3.result, 0, edgeRiskResult3.sealedData, str2, -1, edgeRiskResult3.status, sb.toString());
            TraceLogger f4 = LoggerFactory.f();
            StringBuilder sb5 = new StringBuilder("ua = ");
            sb5.append(str2);
            sb5.append(", result = ");
            sb5.append(edgeRiskResult3.result);
            sb5.append(", sealed data = [");
            sb5.append(edgeRiskResult3.sealedData);
            sb5.append("]");
            f4.b((String) TAG, sb5.toString());
            return edgeRiskResult3;
        } else if (!this.edgeSwitchManager.f()) {
            if (this.edgeSwitchManager.g()) {
                LogAgent.a(false, true);
                this.edgeSwitchManager.c(false);
            }
            EdgeRiskResult edgeRiskResult4 = new EdgeRiskResult();
            edgeRiskResult4.status = ErrorCode.a(313);
            LogAgent.a(ErrorCode.c(edgeRiskResult4.status), (long) edgeRiskResult4.result, 0, edgeRiskResult4.sealedData, str2, -1, edgeRiskResult4.status, sb.toString());
            TraceLogger f5 = LoggerFactory.f();
            StringBuilder sb6 = new StringBuilder("ua = ");
            sb6.append(str2);
            sb6.append(", result = ");
            sb6.append(edgeRiskResult4.result);
            sb6.append(", sealed data = [");
            sb6.append(edgeRiskResult4.sealedData);
            sb6.append("]");
            f5.b((String) TAG, sb6.toString());
            return edgeRiskResult4;
        } else {
            HashMap hashMap = new HashMap();
            long currentTimeMillis = System.currentTimeMillis();
            if (map2 == null) {
                map2 = new HashMap<>();
            }
            EdgeSwitchManager.a(this.mContext);
            map2.put("_reportCount", EdgeSwitchManager.b());
            EdgeSwitchManager.a(this.mContext);
            map2.put("_timeStamp", EdgeSwitchManager.c());
            map2.put("_ad3", new String(Hex.encode(DeviceInfo.getInstance().getSensorDigest(this.mContext))));
            map2.put("_apkverify", String.valueOf(RDSClient.ApkVerify));
            EdgeRiskResult edgeRiskResult5 = new EdgeRiskResult();
            hashMap.putAll(map2);
            EdgeNativeBridge.getRiskResult(str2, formatMapParameter(hashMap), edgeRiskResult5, i);
            String c = ErrorCode.c(edgeRiskResult5.status);
            LogAgent.a(c, (long) edgeRiskResult5.result, System.currentTimeMillis() - currentTimeMillis, edgeRiskResult5.sealedData, str2, getEdgeStrategyVersion(), edgeRiskResult5.status, sb.toString());
            TraceLogger f6 = LoggerFactory.f();
            StringBuilder sb7 = new StringBuilder("ua = ");
            sb7.append(str2);
            sb7.append(", result = ");
            sb7.append(edgeRiskResult5.result);
            sb7.append(", sealed data = [");
            sb7.append(edgeRiskResult5.sealedData);
            sb7.append("]");
            f6.b((String) TAG, sb7.toString());
            TraceLogger f7 = LoggerFactory.f();
            StringBuilder sb8 = new StringBuilder("t0result json ");
            sb8.append(edgeRiskResult5.toStringEx());
            f7.b((String) TAG, sb8.toString());
            if (str2.equals(UA_KCART_CALL)) {
                HashMap hashMap2 = new HashMap();
                if (hashMap.containsKey("rtFund1D1Mth")) {
                    TraceLogger f8 = LoggerFactory.f();
                    StringBuilder sb9 = new StringBuilder("rtFund1D1Mth value is ");
                    sb9.append((String) hashMap.get("rtFund1D1Mth"));
                    f8.b((String) TAG, sb9.toString());
                } else {
                    LoggerFactory.f().b((String) TAG, (String) "rtFund1D1Mth not found");
                }
                if (hashMap.containsKey("rtFundcard1D1Mth")) {
                    TraceLogger f9 = LoggerFactory.f();
                    StringBuilder sb10 = new StringBuilder("rtFundcard1D1Mth value is ");
                    sb10.append((String) hashMap.get("rtFundcard1D1Mth"));
                    f9.b((String) TAG, sb10.toString());
                } else {
                    LoggerFactory.f().b((String) TAG, (String) "rtFundcard1D1Mth not found");
                }
                hashMap2.putAll(hashMap);
                postUserAction(UA_KCART_CALL, hashMap2);
            }
            if (1 == edgeRiskResult5.needSync) {
                LoggerFactory.f().b((String) TAG, (String) "getRiskResult check needSync");
                syncWithServerNow();
            }
            LogAgent.b(edgeRiskResult5.data);
            return edgeRiskResult5;
        }
    }

    public void updateResource(String str) {
        byte[] bArr;
        try {
            this.updateResLock.lock();
            if (!this.initialized.get()) {
                logger.b((String) TAG, (String) "updateResourceWrap() edge not started, deprecate sync rules.");
                return;
            }
            logger.b((String) TAG, (String) "updateResourceWrap() edge started, started to update rules.");
            if (CommonUtils.isNotBlank(str)) {
                try {
                    bArr = Base64Util.decode(str);
                } catch (Exception unused) {
                    bArr = null;
                }
                if (!CommonUtils.isByteArrayEmpty(bArr)) {
                    EdgeNativeBridge.updateResource(bArr);
                    logger.b((String) TAG, (String) "updateResourceWrap(), update cached ta resource data success.");
                }
            }
            setHasEdgeData(EdgeStorageUtils.c(this.mContext));
            this.updateResLock.unlock();
        } finally {
            setHasEdgeData(EdgeStorageUtils.c(this.mContext));
            this.updateResLock.unlock();
        }
    }

    public void syncWithServerNow() {
        syncWithServer();
    }

    private void syncWithServer() {
        if (this.initialized.get()) {
            EdgeNativeBridge.getDeviceDataAsync(-1, new T0DeviceDataListener() {
                public final void a(final byte[] bArr) {
                    EdgeRiskServiceImpl.this.rpcThreadExcutor.execute(new Runnable() {
                        public void run() {
                            EdgeRiskServiceImpl.logger.b((String) EdgeRiskServiceImpl.TAG, "syncWithServer reveived data, data length = ".concat(String.valueOf(bArr == null ? 0 : bArr.length)));
                            EdgeRiskServiceImpl.logger.b((String) EdgeRiskServiceImpl.TAG, (String) "syncWithServer finished.");
                        }
                    });
                }
            });
        }
    }

    private boolean reportDataWrap(byte[] bArr) {
        EdgeResponseModel edgeResponseModel;
        if (bArr != null) {
            try {
                if (bArr.length != 0) {
                    int edgeCrediableTime = getEdgeCrediableTime();
                    int edgeStrategyVersion = getEdgeStrategyVersion();
                    TraceLogger traceLogger = logger;
                    StringBuilder sb = new StringBuilder("reportDataWrap credibleTime = ");
                    sb.append(edgeCrediableTime);
                    sb.append(", stgyVer = ");
                    sb.append(edgeStrategyVersion);
                    traceLogger.b((String) TAG, sb.toString());
                    edgeResponseModel = RpcManager.a(this.mContext, bArr, edgeCrediableTime, edgeStrategyVersion, null);
                    if (edgeResponseModel != null) {
                        if (edgeResponseModel.a) {
                            if (CommonUtils.isApkDebugable(this.mContext)) {
                                int length = edgeResponseModel.b == null ? 0 : edgeResponseModel.b.length;
                                TraceLogger traceLogger2 = logger;
                                StringBuilder sb2 = new StringBuilder("reportDataWrap, flag = ");
                                sb2.append(edgeResponseModel.c);
                                traceLogger2.b((String) TAG, sb2.toString());
                                TraceLogger traceLogger3 = logger;
                                StringBuilder sb3 = new StringBuilder("reportDataWrap, applist ver = ");
                                sb3.append(edgeResponseModel.d);
                                traceLogger3.b((String) TAG, sb3.toString());
                                TraceLogger traceLogger4 = logger;
                                StringBuilder sb4 = new StringBuilder("reportDataWrap, applist = ");
                                sb4.append(edgeResponseModel.e);
                                traceLogger4.b((String) TAG, sb4.toString());
                                logger.b((String) TAG, "reportDataWrap, config len = ".concat(String.valueOf(length)));
                            }
                            ApplistUtil.a(this.mContext, edgeResponseModel.d, edgeResponseModel.e);
                            if ((edgeResponseModel.c & 1) == 1) {
                                logger.b((String) TAG, (String) "reportDataWrap edge switch is on, continue work.");
                                this.edgeSwitchManager.b(true);
                            } else {
                                logger.b((String) TAG, (String) "reportDataWrap edge switch is off, stop work.");
                                this.edgeSwitchManager.b(false);
                                this.edgeSwitchManager.c(true);
                                logger.b((String) TAG, "reportDataWrap read edge switch = ".concat(String.valueOf(this.edgeSwitchManager.f())));
                            }
                            if (!CommonUtils.isByteArrayEmpty(edgeResponseModel.b)) {
                                EdgeNativeBridge.updateResource(edgeResponseModel.b);
                                logger.b((String) TAG, (String) "update resource success!");
                            }
                            return true;
                        }
                    }
                    logger.b((String) TAG, (String) "reportDataWrap report failed.");
                    return false;
                }
            } catch (Exception e) {
                LoggerFactory.e().a((String) "mobilesecurity", (String) "RpcReportDataEx", (Throwable) e);
                edgeResponseModel = null;
            } catch (Throwable unused) {
                return false;
            }
        }
        logger.b((String) TAG, (String) "reportDataWrap data is empty.");
        return false;
    }

    private static String formatMapParameter(Map<String, String> map) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        if (map != null) {
            for (String next : map.keySet()) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append(next);
                sb2.append("=");
                sb2.append(map.get(next));
                sb.append(sb2.toString());
                sb.append("\n");
            }
        }
        sb.append("\n");
        return sb.toString();
    }

    private int getEdgeCrediableTime() {
        try {
            if (this.initialized.get()) {
                byte[] deviceDataSync = EdgeNativeBridge.getDeviceDataSync(32, null);
                if (!CommonUtils.isByteArrayEmpty(deviceDataSync)) {
                    ByteBuffer wrap = ByteBuffer.wrap(deviceDataSync);
                    wrap.order(ByteOrder.LITTLE_ENDIAN);
                    if (wrap.remaining() >= 4) {
                        return wrap.getInt();
                    }
                }
            }
        } catch (Throwable unused) {
        }
        return -1;
    }

    private int getEdgeStrategyVersion() {
        try {
            if (this.initialized.get()) {
                byte[] deviceDataSync = EdgeNativeBridge.getDeviceDataSync(16, null);
                if (!CommonUtils.isByteArrayEmpty(deviceDataSync)) {
                    ByteBuffer wrap = ByteBuffer.wrap(deviceDataSync);
                    wrap.order(ByteOrder.LITTLE_ENDIAN);
                    if (wrap.remaining() >= 4) {
                        return wrap.getInt();
                    }
                }
            }
        } catch (Throwable unused) {
        }
        return -1;
    }

    private int getVKeySecretIndex() {
        if (!CommonUtils.isApkDebugable(this.mContext)) {
            return 0;
        }
        OnlineHostConfig.a();
        OnlineHostConfig.b();
        return 0;
    }

    public static int isHasEdgeData() {
        return hasEdgeData.get();
    }

    public static void setHasEdgeData(int i) {
        LoggerFactory.f().b((String) TAG, "HAS EDGEDATA IS ".concat(String.valueOf(i)));
        hasEdgeData.set(i);
    }
}
