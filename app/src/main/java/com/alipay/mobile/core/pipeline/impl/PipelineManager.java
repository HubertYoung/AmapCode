package com.alipay.mobile.core.pipeline.impl;

import android.os.SystemClock;
import android.text.TextUtils;
import com.alipay.android.phone.mobilecommon.multimedia.video.data.CameraParams;
import com.alipay.mobile.framework.FrameworkMonitor;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.framework.msg.MsgCodeConstants;
import com.alipay.mobile.framework.pipeline.BlockablePipeline;
import com.alipay.mobile.framework.pipeline.PausableRunnable;
import com.alipay.mobile.framework.pipeline.Pipeline;
import com.alipay.mobile.framework.pipeline.ValveDescription;
import com.alipay.mobile.quinox.asynctask.AsyncTaskExecutor;
import com.alipay.mobile.quinox.asynctask.StandardPipeline;
import com.alipay.mobile.quinox.asynctask.TimeoutPipeline;
import com.alipay.mobile.quinox.utils.LiteProcessInfo;
import com.alipay.mobile.quinox.utils.StringUtil;
import com.alipay.mobile.quinox.utils.SystemUtil;
import com.alipay.mobile.quinox.utils.TraceLogger;
import com.alipay.sdk.util.h;
import com.mpaas.nebula.plugin.H5ServicePlugin;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

public class PipelineManager {
    /* access modifiers changed from: private */
    public static final String a = PipelineManager.class.getSimpleName();
    private final int b = 5;
    /* access modifiers changed from: private */
    public String c;
    private Map<String, Pipeline> d = new ConcurrentHashMap(5);
    /* access modifiers changed from: private */
    public Map<String, List<ValveDescription>> e = new ConcurrentHashMap();
    /* access modifiers changed from: private */
    public List<String> f = new ArrayList<String>() {
        {
            add("com.alipay.android.app.template.DynamicTemplateQuickPayCache");
            add("com.alipay.mobile.notification.NotificationInitVavle");
            add("com.alipay.android.resourcemanager.check.ResourceCheckTrigger");
            add("com.alipay.mobile.bill.home.BillHomeTask");
            add("com.alipay.android.phone.discovery.o2ohome.dynamic.blocksystem.cache.PreLoadRunnable");
            add("com.alipay.mobile.onsitepaystatic.OnsitePayFirstStartValve");
            add("com.alipay.android.phone.mobilecommon.biometric.BioMetricValve");
            add("com.alipay.android.phone.discovery.o2ohome.personal.DoExpireMsgRunnable");
            add("com.alipay.android.phone.securitycrypt.AntCryptInitValve");
            add("com.alipay.mobile.security.authcenter.login.biz.SecuritySyncValve");
            add("com.alipay.mobile.namecertify.pipeline.NameCertifyDataUpdate");
            add("com.alipay.android.phone.rome.pushipp.IppMonitorValve");
        }
    };
    /* access modifiers changed from: private */
    public List<String> g = new ArrayList<String>() {
        {
            add("com.alipay.mobile.apiexecutor.app.EmojiPipeTask");
            add("com.alipay.android.phone.mobilesdk.apm.pipeline.ApmPipelineValve");
            add("com.alipay.mobile.base.resourceclean.TasksExecutor");
        }
    };

    class StandardPipelineWrapper extends BlockablePipeline {
        String mName;
        StandardPipeline mTarget;
        boolean started = false;

        StandardPipelineWrapper(StandardPipeline target, String name) {
            this.mTarget = target;
            this.mName = name;
        }

        public boolean getStarted() {
            return this.started;
        }

        public void setExecutor(Executor executor) {
            this.mTarget.setExecutor(executor);
        }

        public void addIdleListener(Runnable callback) {
            this.mTarget.addIdleListener(callback);
        }

        public void addTask(Runnable runnable, String s) {
            this.mTarget.addTask(runnable, s);
        }

        public void addTask(Runnable runnable, String s, int i) {
            this.mTarget.addTask(runnable, s, i);
        }

        public void doStart() {
            if (!this.started) {
                this.started = true;
                if (!LiteProcessInfo.g(LauncherApplicationAgent.getInstance().getApplicationContext()).isCurrentProcessALiteProcess()) {
                    AsyncTaskExecutor.getInstance().execute(new Runnable() {
                        public void run() {
                            if (SystemUtil.isPreloadLaunch()) {
                                try {
                                    Thread.sleep(2000);
                                } catch (InterruptedException e) {
                                    TraceLogger.w(PipelineManager.a, (Throwable) e);
                                }
                            }
                            List shortStrings = null;
                            List<ValveDescription> tasks = (List) PipelineManager.this.e.get(StandardPipelineWrapper.this.mName);
                            if (tasks != null) {
                                shortStrings = new ArrayList(tasks.size());
                                long t1 = SystemClock.elapsedRealtime();
                                boolean shouldIntercept = PipelineManager.c(StandardPipelineWrapper.this.mName);
                                for (ValveDescription valve : tasks) {
                                    try {
                                        TraceLogger.i(PipelineManager.a, "new valve.runnable, valve=" + valve.getClassName());
                                        if (shouldIntercept) {
                                            if (PipelineManager.this.c == null) {
                                                PipelineManager.this.c = PipelineManager.b("perf_opt_intercept_pipeline");
                                            }
                                            if (!CameraParams.FLASH_MODE_OFF.equals(PipelineManager.this.c)) {
                                                String taskName = valve.getClassName();
                                                if (PipelineManager.this.f.contains(taskName)) {
                                                    PipelineManager.this.a(MsgCodeConstants.PIPELINE_IDLE).add(valve);
                                                    TraceLogger.i(PipelineManager.a, "intercept " + taskName + " into idle pipeline");
                                                } else if (PipelineManager.this.g.contains(taskName)) {
                                                    PipelineManager.this.a(MsgCodeConstants.PIPELINE_TASKSCHEDULESERVICE_IDLE).add(valve);
                                                    TraceLogger.i(PipelineManager.a, "intercept " + taskName + " into taskservice idleTask");
                                                }
                                            }
                                        }
                                    } catch (Throwable e2) {
                                        FrameworkMonitor.getInstance(null).handleDescriptionInitFail(valve, e2);
                                        String access$000 = PipelineManager.a;
                                        RuntimeException runtimeException = new RuntimeException("Failed to reflect Valve[" + valve + "]", e2);
                                        TraceLogger.e(access$000, (Throwable) runtimeException);
                                    }
                                    StandardPipelineWrapper.this.addTask(new PausableRunnable((Runnable) valve.getClazz().newInstance()), valve.getThreadName(), valve.getWeight());
                                    shortStrings.add(valve.toShortString());
                                }
                                long cost = SystemClock.elapsedRealtime() - t1;
                                boolean exceed = cost > ((long) (SystemUtil.isPreloadLaunch() ? 1000 : 600));
                                TraceLogger.i(PipelineManager.a, "create pipeline runnable cost: " + cost + ", adjust the priority of all Runnable ? : " + exceed);
                                if (exceed) {
                                    PausableRunnable.setAdjustPriority(true);
                                }
                            }
                            TraceLogger.i((String) "perf_opt", "PipeLine[" + StandardPipelineWrapper.this.mName + "] valves:" + StringUtil.collection2String(shortStrings));
                            StandardPipelineWrapper.this.mTarget.start();
                        }
                    }, "start_pipeline#" + this.mName);
                }
            }
        }

        public String getName() {
            return this.mName;
        }

        public void next() {
            this.mTarget.next();
        }

        public void stop() {
            this.mTarget.stop();
        }

        public String toString() {
            return "Pipeline{name=" + this.mName + ", started=" + this.started + h.d;
        }
    }

    public Pipeline getPipelineByName(String name) {
        return getPipelineByName(name, TimeUnit.SECONDS.toMillis(2));
    }

    public Pipeline getPipelineByName(String name, long timeout) {
        Pipeline pipeline = this.d.get(name);
        if (pipeline == null) {
            pipeline = new StandardPipelineWrapper(new TimeoutPipeline(name, AsyncTaskExecutor.getInstance().getExecutor(), timeout), name);
            this.d.put(name, pipeline);
            int size = this.d.size();
            TraceLogger.w(a, name + " pipeline size: " + size);
            if (size > 5) {
                TraceLogger.e(a, name + " pipeline size: " + size);
            }
        }
        return pipeline;
    }

    public void addValve(String name, String threadName, Runnable task, int wight) {
        getPipelineByName(name).addTask(task, threadName, wight);
    }

    public void addValve(ValveDescription description) {
        String pipelineName = description.getPipelineName();
        if (TextUtils.isEmpty(pipelineName)) {
            TraceLogger.w(a, (String) "PipelineName is null");
        } else {
            a(pipelineName).add(description);
        }
    }

    /* access modifiers changed from: private */
    public List<ValveDescription> a(String pipelineName) {
        List tasks = this.e.get(pipelineName);
        if (tasks != null) {
            return tasks;
        }
        List tasks2 = new CopyOnWriteArrayList();
        this.e.put(pipelineName, tasks2);
        return tasks2;
    }

    /* access modifiers changed from: private */
    public static String b(String key) {
        Object configService = LauncherApplicationAgent.getInstance().getMicroApplicationContext().findServiceByInterface("com.alipay.mobile.base.config.ConfigService");
        return String.valueOf(configService.getClass().getDeclaredMethod(H5ServicePlugin.GET_CONFIG, new Class[]{String.class}).invoke(configService, new Object[]{key}));
    }

    /* access modifiers changed from: private */
    public static boolean c(String pipelineName) {
        if (!TextUtils.isEmpty(pipelineName) && !"com.alipay.mobile.framework.INITED".equals(pipelineName) && !MsgCodeConstants.PIPELINE_TASKSCHEDULESERVICE_IDLE.equals(pipelineName) && !MsgCodeConstants.PIPELINE_IDLE.equals(pipelineName)) {
            return true;
        }
        return false;
    }
}
