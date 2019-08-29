package com.alipay.mobile.common.logging.uploader;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import com.alipay.mobile.common.logging.api.LogContext;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.logging.api.encrypt.LogEncryptClient;
import com.alipay.mobile.common.logging.api.monitor.DataflowID;
import com.alipay.mobile.common.logging.api.monitor.DataflowModel;
import com.alipay.mobile.common.logging.api.rpc.LogRpcResult;
import com.alipay.mobile.common.logging.api.rpc.RpcClient;
import com.alipay.mobile.common.logging.api.rpc.RpcLogData;
import com.alipay.mobile.common.logging.api.rpc.RpcLogRequestParam;
import com.alipay.mobile.common.logging.http.MdapTrafficController.MdapTrafficException;
import com.alipay.mobile.common.logging.process.VariableStoreInToolsProcess;
import com.alipay.mobile.common.logging.strategy.LogStrategyManager;
import com.alipay.mobile.common.logging.util.FileUtil;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class RpcUploader extends BaseUploader {
    public RpcUploader(File UploadFileDir, Context context) {
        super(UploadFileDir, context);
    }

    public final void a(String logCategory, Bundle bundle) {
        try {
            a();
        } catch (Throwable t) {
            Log.w(a, "cleanExpiresFile: " + t);
        }
        boolean isMergeUpload = false;
        if (bundle != null) {
            isMergeUpload = bundle.getBoolean(LogContext.IS_MERGE_UPLOAD, false);
        }
        File[] uploadFiles = this.b.listFiles();
        if (uploadFiles == null || uploadFiles.length == 0) {
            LoggerFactory.getTraceLogger().info(a, logCategory + " do not need upload RpcUploader");
            return;
        }
        LoggerFactory.getTraceLogger().info(a, logCategory + " will upload, count of all mdap files: " + uploadFiles.length + " isMerge = " + isMergeUpload);
        if (isMergeUpload) {
            a(logCategory, uploadFiles);
        } else {
            a(logCategory, uploadFiles, bundle);
        }
    }

    private void a(String logCategory, File[] uploadFiles) {
        List mergeUploadFiles = new ArrayList();
        for (File file : uploadFiles) {
            if (file != null && file.exists() && file.isFile()) {
                String fileName = file.getName();
                try {
                    String fileLogCategory = a(fileName);
                    if (!TextUtils.isEmpty(fileLogCategory) && LogStrategyManager.getInstance().isRealTimeLogCategory(fileLogCategory) && LogStrategyManager.getInstance().isLogSend(fileName)) {
                        mergeUploadFiles.add(file);
                    }
                } catch (Throwable tr) {
                    LoggerFactory.getTraceLogger().error(a, tr);
                }
            }
        }
        a(mergeUploadFiles);
        LoggerFactory.getTraceLogger().info(a, "target " + logCategory + " merge uploadLog end");
    }

    private void a(List<File> mergeUploadFiles) {
        ArrayList arrayList;
        ArrayList arrayList2;
        if (mergeUploadFiles.isEmpty()) {
            LoggerFactory.getTraceLogger().info(a, "uploadMergeFile files is empty");
            return;
        }
        Integer logCount = Integer.valueOf(0);
        int length = mergeUploadFiles.size();
        ArrayList arrayList3 = new ArrayList();
        ArrayList arrayList4 = new ArrayList();
        RpcClient rpcClient = LoggerFactory.getLogContext().getLogUploadRpcClient();
        if (rpcClient == null) {
            LoggerFactory.getTraceLogger().info(a, "getRpcClient = null do http upload");
            b(mergeUploadFiles);
            return;
        }
        int i = 0;
        ArrayList arrayList5 = arrayList4;
        ArrayList arrayList6 = arrayList3;
        while (i < length) {
            try {
                File logFile = mergeUploadFiles.get(i);
                if (logFile == null || !logFile.exists()) {
                    arrayList2 = arrayList5;
                    arrayList = arrayList6;
                    i++;
                    arrayList5 = arrayList2;
                    arrayList6 = arrayList;
                } else {
                    String logContent = FileUtil.readFile(logFile);
                    if (TextUtils.isEmpty(logContent)) {
                        logFile.delete();
                        throw new IllegalStateException("file content is empty");
                    }
                    String[] logsStr = logContent.split("\\$\\$");
                    int len = logsStr.length;
                    if (!arrayList5.isEmpty() && logCount.intValue() + len >= 100) {
                        a((List<File>) arrayList6, (List<RpcLogData>) arrayList5, rpcClient, logCount.intValue());
                        logCount = Integer.valueOf(0);
                        arrayList2 = new ArrayList();
                        try {
                            arrayList5 = arrayList2;
                            arrayList6 = new ArrayList();
                        } catch (Throwable th) {
                            e = th;
                            arrayList = arrayList6;
                        }
                    }
                    String logCategory = a(logFile.getName());
                    RpcLogData logData = new RpcLogData();
                    logData.bizCode = logCategory;
                    ArrayList arrayList7 = new ArrayList();
                    int len2 = logsStr.length;
                    for (int j = 0; j < len2; j++) {
                        if (!TextUtils.isEmpty(logsStr[j])) {
                            arrayList7.add(b(logsStr[j]));
                        }
                    }
                    logData.log = arrayList7;
                    arrayList5.add(logData);
                    logCount = Integer.valueOf(logCount.intValue() + arrayList7.size());
                    arrayList6.add(logFile);
                    if (logCount.intValue() >= 100) {
                        a((List<File>) arrayList6, (List<RpcLogData>) arrayList5, rpcClient, logCount.intValue());
                        logCount = Integer.valueOf(0);
                        arrayList2 = new ArrayList();
                        arrayList = new ArrayList();
                    } else {
                        arrayList2 = arrayList5;
                        arrayList = arrayList6;
                    }
                    i++;
                    arrayList5 = arrayList2;
                    arrayList6 = arrayList;
                }
            } catch (Throwable th2) {
                e = th2;
                arrayList2 = arrayList5;
                arrayList = arrayList6;
                LoggerFactory.getTraceLogger().error(a, e);
                i++;
                arrayList5 = arrayList2;
                arrayList6 = arrayList;
            }
        }
        if (!arrayList5.isEmpty()) {
            a((List<File>) arrayList6, (List<RpcLogData>) arrayList5, rpcClient, logCount.intValue());
        }
    }

    private void a(List<File> partUploadFiles, List<RpcLogData> logDataList, RpcClient rpcClient, int logCount) {
        RpcLogRequestParam param = new RpcLogRequestParam();
        param.logs = logDataList;
        int len = partUploadFiles.size();
        LoggerFactory.getTraceLogger().info(a, "doMergeUpload start, files count = " + len + " logCount = " + logCount);
        try {
            LogRpcResult result = rpcClient.uploadLog(param, "merge", null);
            LoggerFactory.getTraceLogger().info(a, "doMergeUpload success, files count = " + len + " logCount = " + logCount);
            if (result == null || result.respCode != 1000) {
                b(partUploadFiles);
                return;
            }
            for (int i = 0; i < len; i++) {
                try {
                    partUploadFiles.get(i).delete();
                } catch (Throwable th) {
                }
            }
        } catch (Throwable e) {
            b(partUploadFiles);
            LoggerFactory.getTraceLogger().error(a, e);
        }
    }

    private void b(List<File> partUploadFiles) {
        try {
            a();
        } catch (Throwable t) {
            Log.w(a, "cleanExpiresFile: " + t);
        }
        if (partUploadFiles == null || partUploadFiles.size() == 0) {
            LoggerFactory.getTraceLogger().info(a, "doDegradeUploadByFiles do not need upload");
            return;
        }
        LoggerFactory.getTraceLogger().info(a, "doDegradeUploadByFiles will upload, count of all mdap files: " + partUploadFiles.size());
        int realUploadCount = 0;
        long realReqSize = 0;
        long realRespSize = 0;
        int errorUploadCount = 0;
        String lastErrorMessage = null;
        StringBuilder logCategoryDetail = null;
        HashSet hashSet = null;
        for (int index = 0; index < partUploadFiles.size(); index++) {
            File file = partUploadFiles.get(index);
            if (file != null && file.exists() && file.isFile()) {
                String fileName = file.getName();
                String logCategory = a(fileName);
                try {
                    if (LogStrategyManager.getInstance().isLogSend(fileName)) {
                        if (hashSet == null || !hashSet.contains(logCategory)) {
                            Pair<Long, Long> a = a(file, logCategory, null, "degrade");
                            LoggerFactory.getTraceLogger().info(a, logCategory + " doDegradeUploadByFiles upload success: " + fileName + " event = " + "degrade");
                            realUploadCount++;
                            realReqSize += ((Long) a.first).longValue();
                            realRespSize += ((Long) a.second).longValue();
                            if (logCategoryDetail == null) {
                                logCategoryDetail = new StringBuilder();
                            } else {
                                logCategoryDetail.append("&");
                            }
                            logCategoryDetail.append(logCategory);
                        } else {
                            LoggerFactory.getTraceLogger().warn(a, logCategory + " previous has occured MdapTrafficException, so this uploading is limited and not performed");
                        }
                    }
                } catch (Throwable tr) {
                    errorUploadCount++;
                    lastErrorMessage = tr.toString();
                    LoggerFactory.getTraceLogger().error(a, logCategory + " doDegradeUploadByFiles failed: " + fileName + " at " + lastErrorMessage);
                    if (errorUploadCount == 1 && ((tr instanceof NullPointerException) || (tr.getCause() != null && (tr.getCause() instanceof NullPointerException)))) {
                        LoggerFactory.getTraceLogger().error(a, "uploadLog", tr);
                    }
                    if (tr instanceof MdapTrafficException) {
                        if (hashSet == null) {
                            hashSet = new HashSet();
                        }
                        hashSet.add(logCategory);
                    }
                }
            }
        }
        LoggerFactory.getTraceLogger().info(a, "doDegradeUploadByFiles end, realUploadCount: " + realUploadCount);
        if (realUploadCount != 0) {
            if (logCategoryDetail == null) {
                logCategoryDetail = new StringBuilder();
            } else {
                logCategoryDetail.append("&");
            }
            String categories = logCategoryDetail.toString();
            DataflowModel dataflowModel = DataflowModel.obtain(DataflowID.MDAP_LOG, LoggerFactory.getLogContext().getLogHost() + "/loggw/logUpload.do", realReqSize, realRespSize, categories);
            if (!TextUtils.isEmpty(VariableStoreInToolsProcess.d)) {
                dataflowModel.putParam("invokerProc", VariableStoreInToolsProcess.d);
            }
            dataflowModel.report();
        } else if (errorUploadCount > 0) {
            LoggerFactory.getTraceLogger().warn(a, errorUploadCount + " errorUploadCount, all the uploading are failed ! lastErrorMessage: " + lastErrorMessage);
        }
    }

    private void a(String logCategory, File[] uploadFiles, Bundle bundle) {
        int realUploadCount = 0;
        for (File file : uploadFiles) {
            if (file != null && file.exists() && file.isFile()) {
                String fileName = file.getName();
                String sendCategory = logCategory;
                try {
                    String sendCategory2 = LogStrategyManager.getInstance().isLogSend(fileName, logCategory);
                    if (sendCategory2 != null) {
                        String event = "default";
                        if (bundle != null && !TextUtils.isEmpty(bundle.getString("event"))) {
                            event = bundle.getString("event");
                        }
                        a(file, sendCategory2, event, bundle);
                        LoggerFactory.getTraceLogger().info(a, sendCategory2 + " uploadByCategory upload success: " + fileName + " event = " + event);
                        realUploadCount++;
                    }
                } catch (Throwable th) {
                    LoggerFactory.getTraceLogger().error(a, sendCategory + " uploadByCategory failed: " + fileName + " at " + th.toString());
                }
            }
        }
        LoggerFactory.getTraceLogger().info(a, "uploadByCategory end, realUploadCount: " + realUploadCount);
    }

    private void a(File logFile, String logCategory, String event, Bundle bundle) {
        if (logFile == null) {
            throw new IllegalStateException("file object is NULL");
        }
        try {
            String logContent = FileUtil.readFile(logFile);
            if (TextUtils.isEmpty(logContent)) {
                logFile.delete();
                throw new IllegalStateException("file content is empty");
            }
            RpcClient rpcClient = LoggerFactory.getLogContext().getLogUploadRpcClient();
            if (rpcClient != null) {
                try {
                    List logDataList = new ArrayList();
                    RpcLogData logData = new RpcLogData();
                    logData.bizCode = logCategory;
                    List logs = new ArrayList();
                    for (String b : logContent.split("\\$\\$")) {
                        logs.add(b(b));
                    }
                    logData.log = logs;
                    logDataList.add(logData);
                    RpcLogRequestParam param = new RpcLogRequestParam();
                    param.logs = logDataList;
                    LogRpcResult result = rpcClient.uploadLog(param, event, bundle);
                    if (result == null) {
                        LoggerFactory.getTraceLogger().info(a, "rpc upload fail result is null");
                        a(bundle, logCategory);
                    } else if (result.respCode == 1000) {
                        LoggerFactory.getTraceLogger().info(a, "rpc upload success category = " + logCategory);
                        try {
                            logFile.delete();
                        } catch (Throwable t) {
                            throw new IllegalStateException("delete file error: " + t, t);
                        }
                    } else {
                        LoggerFactory.getTraceLogger().info(a, "rpc upload fail respCode = " + result.respCode + " resp des = " + result.errorMsg);
                        a(bundle, logCategory);
                    }
                } catch (Throwable e) {
                    LoggerFactory.getTraceLogger().error(a, "rpc upload error,do upload by http", e);
                    a(bundle, logCategory);
                }
            } else {
                a(bundle, logCategory);
            }
        } catch (Throwable e2) {
            throw new IllegalStateException("read file error: " + e2, e2);
        }
    }

    private static String b(String item) {
        if (TextUtils.isEmpty(item)) {
            return item;
        }
        LogEncryptClient client = LoggerFactory.getLogContext().getLogEncryptClient();
        if (client == null || !item.startsWith("1_")) {
            return item;
        }
        String deItem = client.decrypt(item.substring(2));
        if (!TextUtils.isEmpty(deItem)) {
            return deItem;
        }
        return item;
    }

    private void a(Bundle bundle, String logCategory) {
        if (bundle == null) {
            bundle = new Bundle();
        }
        LoggerFactory.getTraceLogger().info(a, "doDegradeUploadByLogCategory logCategory = " + logCategory);
        bundle.putBoolean(LogContext.IS_DEGRADE_UPLOAD, true);
        new HttpUploader(this.b, this.c).a(logCategory, (String) null, bundle);
    }
}
