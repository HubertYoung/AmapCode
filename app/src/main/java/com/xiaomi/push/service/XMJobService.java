package com.xiaomi.push.service;

import android.annotation.TargetApi;
import android.app.Service;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.os.Binder;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import com.xiaomi.channel.commonutils.logger.b;

public class XMJobService extends Service {
    static Service a;
    private IBinder b = null;

    @TargetApi(21)
    static class a extends JobService {
        Binder a;
        private Handler b;

        /* renamed from: com.xiaomi.push.service.XMJobService$a$a reason: collision with other inner class name */
        static class C0083a extends Handler {
            JobService a;

            C0083a(JobService jobService) {
                super(jobService.getMainLooper());
                this.a = jobService;
            }

            public void handleMessage(Message message) {
                if (message.what == 1) {
                    JobParameters jobParameters = (JobParameters) message.obj;
                    StringBuilder sb = new StringBuilder("Job finished ");
                    sb.append(jobParameters.getJobId());
                    b.a(sb.toString());
                    this.a.jobFinished(jobParameters, false);
                    if (jobParameters.getJobId() == 1) {
                        com.xiaomi.push.service.timers.a.a(false);
                    }
                }
            }
        }

        a(Service service) {
            this.a = null;
            this.a = (Binder) com.xiaomi.channel.commonutils.reflect.a.a((Object) this, (String) "onBind", new Intent());
            com.xiaomi.channel.commonutils.reflect.a.a((Object) this, (String) "attachBaseContext", service);
        }

        public boolean onStartJob(JobParameters jobParameters) {
            StringBuilder sb = new StringBuilder("Job started ");
            sb.append(jobParameters.getJobId());
            b.a(sb.toString());
            Intent intent = new Intent(this, XMPushService.class);
            intent.setAction("com.xiaomi.push.timer");
            intent.setPackage(getPackageName());
            startService(intent);
            if (this.b == null) {
                this.b = new C0083a(this);
            }
            this.b.sendMessage(Message.obtain(this.b, 1, jobParameters));
            return true;
        }

        public boolean onStopJob(JobParameters jobParameters) {
            StringBuilder sb = new StringBuilder("Job stop ");
            sb.append(jobParameters.getJobId());
            b.a(sb.toString());
            return false;
        }
    }

    static Service a() {
        return a;
    }

    public IBinder onBind(Intent intent) {
        return this.b != null ? this.b : new Binder();
    }

    public void onCreate() {
        super.onCreate();
        if (VERSION.SDK_INT >= 21) {
            this.b = new a(this).a;
        }
        a = this;
    }

    public void onDestroy() {
        super.onDestroy();
        a = null;
    }
}
