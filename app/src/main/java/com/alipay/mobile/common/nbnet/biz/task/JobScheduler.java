package com.alipay.mobile.common.nbnet.biz.task;

import java.util.concurrent.Future;

public interface JobScheduler {
    Future a(Job job);
}
