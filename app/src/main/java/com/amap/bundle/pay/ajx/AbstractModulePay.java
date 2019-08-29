package com.amap.bundle.pay.ajx;

import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.core.JsFunctionCallback;
import com.autonavi.minimap.ajx3.modules.AbstractModule;

public abstract class AbstractModulePay extends AbstractModule {
    public abstract String isInstalled(String str);

    public abstract void login(String str, String str2, JsFunctionCallback jsFunctionCallback);

    public abstract void pay(String str, String str2, JsFunctionCallback jsFunctionCallback);

    public abstract void query(String str, String str2, JsFunctionCallback jsFunctionCallback);

    public abstract void setDebug(String str);

    public abstract void sign(String str, String str2, JsFunctionCallback jsFunctionCallback);

    public AbstractModulePay(IAjxContext iAjxContext) {
        super(iAjxContext);
    }
}
