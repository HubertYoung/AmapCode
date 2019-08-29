package com.taobao.accs.internal;

import android.annotation.TargetApi;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import com.taobao.accs.common.Constants;
import com.taobao.accs.common.ThreadPoolExecutorFactory;
import com.taobao.accs.dispatch.IntentDispatch;
import com.taobao.accs.utl.ALog;
import com.taobao.accs.utl.AdapterUtilityImpl;

@TargetApi(21)
public class AccsJobService extends JobService {
    private static final String TAG = "AccsJobService";

    public boolean onStartJob(final JobParameters jobParameters) {
        ALog.d(TAG, "onStartJob", new Object[0]);
        ThreadPoolExecutorFactory.execute(new Runnable() {
            public void run() {
                try {
                    String packageName = AccsJobService.this.getPackageName();
                    Intent intent = new Intent();
                    intent.setPackage(packageName);
                    intent.setAction(Constants.ACTION_COMMAND);
                    intent.putExtra("command", 201);
                    intent.setClassName(packageName, AdapterUtilityImpl.channelService);
                    IntentDispatch.dispatchIntent(AccsJobService.this, intent, false);
                    AccsJobService.this.jobFinished(jobParameters, false);
                } catch (Throwable th) {
                    ALog.e(AccsJobService.TAG, "onStartJob", th, new Object[0]);
                }
            }
        });
        return true;
    }

    public boolean onStopJob(JobParameters jobParameters) {
        try {
            Intent intent = new Intent();
            intent.setPackage(getPackageName());
            intent.setAction(Constants.ACTION_COMMAND);
            intent.putExtra("command", 201);
            intent.setClassName(getPackageName(), AdapterUtilityImpl.channelService);
            IntentDispatch.dispatchIntent(this, intent, false);
        } catch (Throwable th) {
            ALog.e(TAG, "onStopJob", th, new Object[0]);
        }
        return false;
    }
}
