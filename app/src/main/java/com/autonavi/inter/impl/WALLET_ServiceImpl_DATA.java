package com.autonavi.inter.impl;

import com.autonavi.annotation.helper.ServiceImplLogger;
import java.util.HashMap;
import proguard.annotation.KeepName;

@ServiceImplLogger(impls = {"com.autonavi.map.wallet.WalletImpl"}, inters = {"com.autonavi.minimap.basemap.inter.IWallet"}, module = "wallet")
@KeepName
public final class WALLET_ServiceImpl_DATA extends HashMap<Class, Class<?>> {
    public WALLET_ServiceImpl_DATA() {
        put(cpq.class, cfj.class);
    }
}
