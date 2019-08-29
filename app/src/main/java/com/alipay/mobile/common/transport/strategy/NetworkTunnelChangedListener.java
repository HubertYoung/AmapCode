package com.alipay.mobile.common.transport.strategy;

import java.util.Observable;
import java.util.Observer;

public interface NetworkTunnelChangedListener extends Observer {
    void update(Observable observable, Object obj);
}
