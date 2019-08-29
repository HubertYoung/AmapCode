package com.alipay.zoloz.toyger.workspace;

import android.os.Handler;
import com.alipay.mobile.security.bio.service.BioServiceManager;
import com.alipay.mobile.security.bio.service.BioTaskService;
import com.alipay.mobile.security.bio.task.ActionFrame;
import com.alipay.mobile.security.bio.task.SubTask;
import com.alipay.zoloz.toyger.interfaces.ToygerCallback;
import com.alipay.zoloz.toyger.upload.UploadManager;
import com.alipay.zoloz.toyger.widget.ToygerCirclePattern;
import com.alipay.zoloz.toyger.workspace.task.CherryCaptureTask;
import com.alipay.zoloz.toyger.workspace.task.ToygerBaseTask;
import java.util.Iterator;

public class ToygerTaskManager {
    protected boolean isRunning;
    private BioServiceManager mBioServiceManager;
    private BioTaskService mBioTaskService = ((BioTaskService) this.mBioServiceManager.getBioService(BioTaskService.class));
    private ToygerCallback mToygerCallback;
    private ToygerCirclePattern mToygerCirclePattern;
    private UploadManager mUploadManager;
    private Handler mWorkspaceHandler;

    public ToygerTaskManager(BioServiceManager bioServiceManager, ToygerCirclePattern toygerCirclePattern, Handler handler, ToygerCallback toygerCallback, UploadManager uploadManager) {
        this.mBioServiceManager = bioServiceManager;
        this.mToygerCirclePattern = toygerCirclePattern;
        this.mWorkspaceHandler = handler;
        this.mToygerCallback = toygerCallback;
        this.mUploadManager = uploadManager;
    }

    public void resetTask() {
        this.isRunning = true;
        clearTask();
        CherryCaptureTask cherryCaptureTask = new CherryCaptureTask(this.mBioServiceManager, this.mToygerCirclePattern, this.mWorkspaceHandler, this.mToygerCallback);
        cherryCaptureTask.setUploadManager(this.mUploadManager);
        if (this.mBioTaskService != null) {
            this.mBioTaskService.addTask(cherryCaptureTask);
            this.mBioTaskService.initAndBegin();
        }
    }

    public void action(ActionFrame actionFrame) {
        if (this.mBioTaskService != null && this.isRunning) {
            this.mBioTaskService.action(actionFrame);
        }
    }

    private void clearTask() {
        if (this.mBioTaskService != null) {
            Iterator<SubTask> it = this.mBioTaskService.getTasks().iterator();
            while (it.hasNext()) {
                SubTask next = it.next();
                if (next instanceof ToygerBaseTask) {
                    ((ToygerBaseTask) next).stop();
                }
            }
            this.mBioTaskService.clearTask();
        }
    }

    public void destroy() {
        clearTask();
        this.isRunning = false;
        this.mBioServiceManager = null;
        this.mToygerCirclePattern = null;
        this.mWorkspaceHandler = null;
        this.mBioTaskService = null;
    }
}
