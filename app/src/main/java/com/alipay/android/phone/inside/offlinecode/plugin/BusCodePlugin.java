package com.alipay.android.phone.inside.offlinecode.plugin;

import com.alipay.android.phone.inside.framework.plugin.IInsidePlugin;
import com.alipay.android.phone.inside.framework.service.IInsideService;
import com.alipay.android.phone.inside.offlinecode.plugin.service.AllCardListService;
import com.alipay.android.phone.inside.offlinecode.plugin.service.AllCityListService;
import com.alipay.android.phone.inside.offlinecode.plugin.service.AuthBusCodeService;
import com.alipay.android.phone.inside.offlinecode.plugin.service.BusCodeStatusService;
import com.alipay.android.phone.inside.offlinecode.plugin.service.CloseBusCodeService;
import com.alipay.android.phone.inside.offlinecode.plugin.service.GenBusCodeService;
import com.alipay.android.phone.inside.offlinecode.plugin.service.IssuedCardListService;
import com.alipay.android.phone.inside.offlinecode.plugin.service.ReceiveCardService;
import java.util.HashMap;
import java.util.Map;

public class BusCodePlugin implements IInsidePlugin {
    static final String BUS_CODE_PLUGIN_ALL_CARD = "BUS_CODE_PLUGIN_ALL_CARD";
    static final String BUS_CODE_PLUGIN_ALL_CITY = "BUS_CODE_PLUGIN_ALL_CITY";
    static final String BUS_CODE_PLUGIN_AUTH = "BUS_CODE_PLUGIN_AUTH";
    static final String BUS_CODE_PLUGIN_CLOSE = "BUS_CODE_PLUGIN_CLOSE";
    static final String BUS_CODE_PLUGIN_GEN_CODE = "BUS_CODE_PLUGIN_GEN_CODE";
    static final String BUS_CODE_PLUGIN_ISSUED_CARD = "BUS_CODE_PLUGIN_ISSUED_CARD";
    static final String BUS_CODE_PLUGIN_RECEIVE_CARD = "BUS_CODE_PLUGIN_RECEIVE_CARD";
    static final String BUS_CODE_PLUGIN_STATUS = "BUS_CODE_PLUGIN_STATUS";
    private Map<String, IInsideService> mSerivce;

    public void onRegisted(Object obj) {
    }

    public void onUnRegisted(Object obj) {
    }

    public Map<String, IInsideService> getServiceMap() {
        if (this.mSerivce == null) {
            this.mSerivce = new HashMap();
            this.mSerivce.put(BUS_CODE_PLUGIN_GEN_CODE, new GenBusCodeService());
            this.mSerivce.put(BUS_CODE_PLUGIN_AUTH, new AuthBusCodeService());
            this.mSerivce.put(BUS_CODE_PLUGIN_ALL_CARD, new AllCardListService());
            this.mSerivce.put(BUS_CODE_PLUGIN_ISSUED_CARD, new IssuedCardListService());
            this.mSerivce.put(BUS_CODE_PLUGIN_ALL_CITY, new AllCityListService());
            this.mSerivce.put(BUS_CODE_PLUGIN_RECEIVE_CARD, new ReceiveCardService());
            this.mSerivce.put(BUS_CODE_PLUGIN_CLOSE, new CloseBusCodeService());
            this.mSerivce.put(BUS_CODE_PLUGIN_STATUS, new BusCodeStatusService());
        }
        return this.mSerivce;
    }

    public IInsideService getService(String str) {
        return this.mSerivce.get(str);
    }
}
