package com.alipay.mobile.common.transportext.biz.shared.spdy;

import com.alipay.mobile.common.transport.utils.NetBeanFactory;
import com.alipay.mobile.common.transport.utils.NetworkAsyncTaskExecutor;
import com.alipay.mobile.common.transportext.biz.util.SecureRunnable;
import java.util.Observable;

public class SpdyAvalibleObservable extends Observable {
    public static final SpdyAvalibleObservable getInstance() {
        return (SpdyAvalibleObservable) NetBeanFactory.getBean(SpdyAvalibleObservable.class);
    }

    public void addSpdyAvalibleListener(SpdyAvalibleListener spdyAvalibleListener) {
        addObserver(spdyAvalibleListener);
    }

    public void asyncNotifySpdyAvalible() {
        NetworkAsyncTaskExecutor.execute(new SecureRunnable() {
            public void call() {
                SpdyAvalibleObservable.this.notifyObservers();
            }
        });
    }

    public void notifyObservers(Object data) {
        setChanged();
        super.notifyObservers(data);
    }
}
