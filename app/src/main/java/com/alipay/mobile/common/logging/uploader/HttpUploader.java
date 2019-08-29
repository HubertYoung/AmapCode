package com.alipay.mobile.common.logging.uploader;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import com.alipay.mobile.common.logging.api.LogCustomerControl;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.logging.api.customer.LogUploadInfo;
import com.alipay.mobile.common.logging.api.customer.UploadResultInfo;
import com.alipay.mobile.common.logging.api.monitor.DataflowID;
import com.alipay.mobile.common.logging.api.monitor.DataflowModel;
import com.alipay.mobile.common.logging.http.MdapTrafficController.MdapTrafficException;
import com.alipay.mobile.common.logging.process.VariableStoreInToolsProcess;
import com.alipay.mobile.common.logging.strategy.LogStrategyManager;
import java.io.File;
import java.util.HashSet;

public class HttpUploader extends BaseUploader {
    public HttpUploader(File UploadFileDir, Context context) {
        super(UploadFileDir, context);
    }

    public final void a(String logCategory, String uploadUrl, Bundle bundle) {
        try {
            a();
        } catch (Throwable t) {
            Log.w(a, "cleanExpiresFile: " + t);
        }
        LogCustomerControl logCustomerControl = LoggerFactory.getLogContext().getLogCustomerControl();
        File[] uploadFiles = this.b.listFiles();
        if (uploadFiles == null || uploadFiles.length == 0) {
            LoggerFactory.getTraceLogger().info(a, logCategory + " do not need upload HttpUploader");
            return;
        }
        LoggerFactory.getTraceLogger().info(a, logCategory + " will upload, count of all mdap files: " + uploadFiles.length);
        int realUploadCount = 0;
        long realReqSize = 0;
        long realRespSize = 0;
        int errorUploadCount = 0;
        String lastErrorMessage = null;
        int logCategoryCount = 0;
        StringBuilder logCategoryDetail = null;
        HashSet hashSet = null;
        for (File file : uploadFiles) {
            if (file != null && file.exists() && file.isFile()) {
                String fileName = file.getName();
                String sendCategory = logCategory;
                try {
                    sendCategory = LogStrategyManager.getInstance().isLogSend(fileName, logCategory);
                    if (sendCategory != null) {
                        if (hashSet == null || !hashSet.contains(sendCategory)) {
                            String event = "default";
                            if (bundle != null) {
                                if (!TextUtils.isEmpty(bundle.getString("event"))) {
                                    event = bundle.getString("event");
                                }
                            }
                            UploadResultInfo resultInfo = a(file, sendCategory, logCustomerControl);
                            if (resultInfo != null) {
                                if (resultInfo.isUpload) {
                                    uploadUrl = resultInfo.uploadUrl;
                                }
                            }
                            Pair<Long, Long> a = a(file, sendCategory, uploadUrl, event);
                            LoggerFactory.getTraceLogger().info(a, sendCategory + " HttpUploader upload success: " + fileName + " event = " + event);
                            realUploadCount++;
                            realReqSize += ((Long) a.first).longValue();
                            realRespSize += ((Long) a.second).longValue();
                            if (sendCategory.equals(logCategory)) {
                                logCategoryCount++;
                            } else {
                                if (logCategoryDetail == null) {
                                    logCategoryDetail = new StringBuilder();
                                } else {
                                    logCategoryDetail.append("&");
                                }
                                logCategoryDetail.append(sendCategory);
                            }
                        } else {
                            LoggerFactory.getTraceLogger().warn(a, sendCategory + " previous has occured MdapTrafficException, so this uploading is limited and not performed");
                        }
                    }
                } catch (Throwable tr) {
                    errorUploadCount++;
                    lastErrorMessage = tr.toString();
                    LoggerFactory.getTraceLogger().error(a, sendCategory + " upload failed: " + fileName + " at " + lastErrorMessage);
                    if (errorUploadCount == 1 && ((tr instanceof NullPointerException) || (tr.getCause() != null && (tr.getCause() instanceof NullPointerException)))) {
                        LoggerFactory.getTraceLogger().error(a, "uploadLog", tr);
                    }
                    if (tr instanceof MdapTrafficException) {
                        if (hashSet == null) {
                            hashSet = new HashSet();
                        }
                        hashSet.add(sendCategory);
                    }
                }
            }
        }
        LoggerFactory.getTraceLogger().info(a, "uploadLog end, realUploadCount: " + realUploadCount);
        if (realUploadCount != 0) {
            if (logCategoryDetail == null) {
                logCategoryDetail = new StringBuilder();
            } else {
                logCategoryDetail.append("&");
            }
            if (logCategoryCount > 0) {
                logCategoryDetail.append(logCategory);
                if (logCategoryCount > 1) {
                    logCategoryDetail.append("_x").append(logCategoryCount);
                }
            }
            DataflowModel dataflowModel = DataflowModel.obtain(DataflowID.MDAP_LOG, LoggerFactory.getLogContext().getLogHost() + "/loggw/logUpload.do", realReqSize, realRespSize, logCategoryDetail.toString());
            if (!TextUtils.isEmpty(VariableStoreInToolsProcess.d)) {
                dataflowModel.putParam("invokerProc", VariableStoreInToolsProcess.d);
            }
            dataflowModel.report();
        } else if (errorUploadCount > 0) {
            LoggerFactory.getTraceLogger().warn(a, errorUploadCount + " errorUploadCount, all the uploading are failed ! lastErrorMessage: " + lastErrorMessage);
        }
    }

    private static UploadResultInfo a(File file, String logCategory, LogCustomerControl logCustomerControl) {
        if (logCustomerControl == null) {
            return null;
        }
        LogUploadInfo uploadInfo = new LogUploadInfo();
        uploadInfo.logCategory = logCategory;
        uploadInfo.logFile = file;
        return logCustomerControl.isLogUpload(uploadInfo);
    }
}
