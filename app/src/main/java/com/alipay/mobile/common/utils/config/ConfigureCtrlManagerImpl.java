package com.alipay.mobile.common.utils.config;

import com.alipay.mobile.common.transport.utils.LogCatUtil;
import com.alipay.mobile.common.utils.config.fmk.ConfigureOperation;
import com.alipay.mobile.common.utils.config.fmk.ConfigureOperationImpl;
import java.util.Observable;

public class ConfigureCtrlManagerImpl extends ConfigureOperationImpl implements ConfigureCtrlManager, ConfigureOperation {
    private ConfigureChangedObservable a;

    class ConfigureChangedObservable extends Observable {
        ConfigureChangedObservable() {
        }

        public void notifyObservers(Object data) {
            setChanged();
            try {
                super.notifyObservers(data);
            } catch (Exception e) {
                LogCatUtil.error((String) "ConfigureCtrlManager", (Throwable) e);
            }
        }
    }

    public void addConfigureChangedListener(ConfigureChangedListener observer) {
        LogCatUtil.info("ConfigureCtrlManager", "addConfigureChangedListener.  observer=" + observer.getClass().getName());
        a().addObserver(observer);
    }

    public void notifyConfigureChangedEvent() {
        LogCatUtil.info("ConfigureCtrlManager", "notifyConfigureChangedEvent.  observer count=" + a().countObservers());
        a().notifyObservers();
    }

    public void removeConfigureChangedListener(ConfigureChangedListener observer) {
        a().deleteObserver(observer);
    }

    public String getConfgureVersion() {
        return "-1";
    }

    private ConfigureChangedObservable a() {
        if (this.a == null) {
            this.a = new ConfigureChangedObservable();
        }
        return this.a;
    }
}
