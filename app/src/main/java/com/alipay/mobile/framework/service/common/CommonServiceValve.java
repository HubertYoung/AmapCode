package com.alipay.mobile.framework.service.common;

import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.framework.MicroApplicationContext;
import com.alipay.mobile.framework.msg.MsgCodeConstants;
import com.alipay.mobile.framework.pipeline.Pipeline;

public class CommonServiceValve implements Runnable {
    private static final String a = CommonServiceValve.class.getSimpleName();

    public CommonServiceValve() {
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public void run() {
        Log.d(a, "CommonServiceValve.run()");
        MicroApplicationContext microApp = LauncherApplicationAgent.getInstance().getMicroApplicationContext();
        if (microApp == null) {
            LoggerFactory.getTraceLogger().error(a, (String) "MicroApplicationContext is NULL");
            return;
        }
        final TaskScheduleService scheduleService = (TaskScheduleService) microApp.findServiceByInterface(TaskScheduleService.class.getName());
        if (scheduleService == null) {
            LoggerFactory.getTraceLogger().error(a, (String) "TaskScheduleService is NULL");
            return;
        }
        final Pipeline pipeline_1 = microApp.getPipelineByName("com.alipay.mobile.framework.INITED");
        pipeline_1.addIdleListener(new Runnable() {
            {
                if (Boolean.FALSE.booleanValue()) {
                    Log.v("hackbyte ", ClassVerifier.class.toString());
                }
            }

            public void run() {
                scheduleService.onPipelineFinished("com.alipay.mobile.framework.INITED");
                pipeline_1.addIdleListener(null);
            }
        });
        final Pipeline pipeline_2 = microApp.getPipelineByName(MsgCodeConstants.PIPELINE_FRAMEWORK_CLIENT_STARTED);
        pipeline_2.addIdleListener(new Runnable() {
            {
                if (Boolean.FALSE.booleanValue()) {
                    Log.v("hackbyte ", ClassVerifier.class.toString());
                }
            }

            public void run() {
                scheduleService.onPipelineFinished(MsgCodeConstants.PIPELINE_FRAMEWORK_CLIENT_STARTED);
                pipeline_2.addIdleListener(null);
            }
        });
        final Pipeline pipeline_3 = microApp.getPipelineByName(MsgCodeConstants.PIPELINE_TABLAUNCHER_ACTIVATED);
        pipeline_3.addIdleListener(new Runnable() {
            {
                if (Boolean.FALSE.booleanValue()) {
                    Log.v("hackbyte ", ClassVerifier.class.toString());
                }
            }

            public void run() {
                scheduleService.onPipelineFinished(MsgCodeConstants.PIPELINE_TABLAUNCHER_ACTIVATED);
                pipeline_3.addIdleListener(null);
            }
        });
    }
}
