package com.autonavi.miniapp.monitor.biz.logmonitor.util.tracing;

import android.content.Context;
import android.util.Log;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.autonavi.miniapp.monitor.api.analysis.diagnose.UploadTaskStatus;
import com.autonavi.miniapp.monitor.api.analysis.diagnose.UploadTaskStatus.Code;
import com.autonavi.miniapp.monitor.biz.apm.util.APMTimer;
import com.autonavi.miniapp.monitor.biz.logmonitor.util.upload.HttpUpload;
import com.autonavi.miniapp.monitor.biz.logmonitor.util.upload.UploadConstants;
import com.autonavi.miniapp.monitor.biz.logmonitor.util.upload.UserDiagnostician.DiagnoseTask;
import com.autonavi.miniapp.monitor.util.FileUtils;
import com.autonavi.miniapp.monitor.util.ZipUtils;
import com.autonavi.minimap.offline.model.FilePathHelper;
import java.io.File;
import java.util.ArrayList;

public class TracingUploader {
    private static final long MIN_STORAGE = 13631488;
    /* access modifiers changed from: private */
    public Context mContext;
    /* access modifiers changed from: private */
    public DiagnoseTask mDiagnoseTask;
    /* access modifiers changed from: private */
    public String mOutputPath;
    /* access modifiers changed from: private */
    public String mSourcePath;
    /* access modifiers changed from: private */
    public UploadTaskStatus mTaskCallBack;
    private String mTraceName;
    UploadTaskStatus zListener = new UploadTaskStatus() {
        public void onSuccess(String str) {
            HttpUpload httpUpload = new HttpUpload(TracingUploader.this.mOutputPath, UploadConstants.getUploadFileUrl(TracingUploader.this.mDiagnoseTask.fromType == Code.TASK_BY_MANUAL), TracingUploader.this.mContext, TracingUploader.this.mDiagnoseTask, new UploadTaskStatus() {
                public void onSuccess(String str) {
                    if (TracingUploader.this.mTaskCallBack != null) {
                        TracingUploader.this.mTaskCallBack.onSuccess(str);
                    }
                    FileUtils.deleteFileByPath(TracingUploader.this.mSourcePath);
                    FileUtils.deleteFileByPath(TracingUploader.this.mOutputPath);
                }

                public void onFail(Code code, String str) {
                    if (TracingUploader.this.mTaskCallBack != null) {
                        TracingUploader.this.mTaskCallBack.onFail(code, str);
                    }
                    FileUtils.deleteFileByPath(TracingUploader.this.mSourcePath);
                    FileUtils.deleteFileByPath(TracingUploader.this.mOutputPath);
                }
            });
            APMTimer.getInstance().post(httpUpload);
        }

        public void onFail(Code code, String str) {
            if (TracingUploader.this.mTaskCallBack != null) {
                TracingUploader.this.mTaskCallBack.onFail(code, str);
            }
            FileUtils.deleteFileByPath(TracingUploader.this.mSourcePath);
            FileUtils.deleteFileByPath(TracingUploader.this.mOutputPath);
        }
    };

    class ZipAndDeletedThread extends Thread {
        private UploadTaskStatus mListener;
        private String mOutputPath;
        private String mSourcePath;

        public ZipAndDeletedThread(String str, String str2, UploadTaskStatus uploadTaskStatus) {
            this.mSourcePath = str;
            this.mOutputPath = str2;
            this.mListener = uploadTaskStatus;
        }

        public void run() {
            String str;
            super.run();
            ArrayList arrayList = new ArrayList();
            try {
                arrayList.add(new File(this.mSourcePath));
                ZipUtils.zipFile(arrayList, this.mOutputPath, null, null);
                StringBuilder sb = new StringBuilder("tracing zipped file: ");
                sb.append(this.mOutputPath);
                String sb2 = sb.toString();
                LoggerFactory.getTraceLogger().info("ZipAndDeletedThread", sb2);
                FileUtils.deleteFileByPath(this.mSourcePath);
                if (this.mListener != null) {
                    this.mListener.onSuccess(sb2);
                }
            } catch (Throwable th) {
                if (arrayList.isEmpty()) {
                    str = "[no files to upload] contains none file.";
                } else {
                    str = Log.getStackTraceString(th);
                }
                LoggerFactory.getTraceLogger().error((String) "ZipAndDeletedThread", str);
                if (this.mListener != null) {
                    this.mListener.onFail(Code.ZIPPING_ERROR, "[TracingUploader.tracingAndUpload] ".concat(String.valueOf(str)));
                }
            }
        }
    }

    public TracingUploader(Context context, String str, DiagnoseTask diagnoseTask) {
        this.mContext = context;
        this.mTraceName = str;
        this.mDiagnoseTask = diagnoseTask;
        StringBuilder sb = new StringBuilder();
        sb.append(FileUtils.getSDPath());
        sb.append("/");
        sb.append(str);
        sb.append(".trace");
        this.mSourcePath = sb.toString();
        StringBuilder sb2 = new StringBuilder();
        sb2.append(FileUtils.getSDPath());
        sb2.append("/");
        sb2.append(str);
        sb2.append(FilePathHelper.SUFFIX_DOT_ZIP);
        this.mOutputPath = sb2.toString();
    }

    public void tracingAndUpload() {
        if (!FileUtils.isSDcardAvailableSpace(MIN_STORAGE)) {
            if (this.mTaskCallBack != null) {
                this.mTaskCallBack.onFail(Code.NO_SPACE, "[TracingUploader.tracingAndUpload] sd card is not enough");
            }
            return;
        }
        MethodTracing.getInstance().startMethodTracing(this.mTraceName, this.mDiagnoseTask.traceTime, new UploadTaskStatus() {
            public void onSuccess(String str) {
                new ArrayList().add(new File(TracingUploader.this.mSourcePath));
                new ZipAndDeletedThread(TracingUploader.this.mSourcePath, TracingUploader.this.mOutputPath, TracingUploader.this.zListener).start();
            }

            public void onFail(Code code, String str) {
                if (TracingUploader.this.mTaskCallBack != null) {
                    TracingUploader.this.mTaskCallBack.onFail(code, str);
                }
                FileUtils.deleteFileByPath(TracingUploader.this.mSourcePath);
            }
        }, this.mDiagnoseTask.traceSize);
    }

    public void setUploadTaskStatus(UploadTaskStatus uploadTaskStatus) {
        this.mTaskCallBack = uploadTaskStatus;
    }
}
