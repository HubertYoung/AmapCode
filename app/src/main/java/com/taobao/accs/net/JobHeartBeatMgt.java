package com.taobao.accs.net;

import android.app.job.JobInfo.Builder;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import com.taobao.accs.internal.AccsJobService;

public class JobHeartBeatMgt extends HeartbeatManager {
    public static final int DEAMON_JOB_ID = 2051;
    public static final int HB_JOB_ID = 2050;
    private static final long INTERVAL = 1200000;

    protected JobHeartBeatMgt(Context context) {
        super(context);
    }

    /* access modifiers changed from: protected */
    public void setInner(int i) {
        Builder builder = new Builder(HB_JOB_ID, new ComponentName(this.mContext.getPackageName(), AccsJobService.class.getName()));
        long j = (long) (i * 1000);
        ((JobScheduler) this.mContext.getSystemService("jobscheduler")).schedule(builder.setMinimumLatency(j).setOverrideDeadline(j).setRequiredNetworkType(1).build());
    }

    public static void setPeriodic(Context context) {
        ((JobScheduler) context.getSystemService("jobscheduler")).schedule(new Builder(DEAMON_JOB_ID, new ComponentName(context.getPackageName(), AccsJobService.class.getName())).setPeriodic(INTERVAL).setRequiredNetworkType(1).build());
    }
}
