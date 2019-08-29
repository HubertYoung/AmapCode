package com.alipay.mobile.security.bio.task;

public interface SubTask {
    ActionType action(ActionFrame actionFrame);

    int done();

    int init();
}
