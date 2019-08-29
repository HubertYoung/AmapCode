package com.alipay.multimedia.utils;

import com.alipay.mobile.framework.service.common.TaskScheduleService;

public class ServiceUtils {
    public static TaskScheduleService taskScheduleService() {
        return (TaskScheduleService) AppUtils.getMicroAppCtx().findServiceByInterface(TaskScheduleService.class.getName());
    }
}
