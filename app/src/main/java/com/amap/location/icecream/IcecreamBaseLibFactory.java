package com.amap.location.icecream;

import com.amap.location.icecream.interfaces.IIcecreamBaseLib;

public class IcecreamBaseLibFactory {
    private static IIcecreamBaseLib mBaseLib;

    protected static void setIcecreamBaseLib(IIcecreamBaseLib iIcecreamBaseLib) {
        mBaseLib = iIcecreamBaseLib;
    }

    public static IIcecreamBaseLib getIcecreamBaseLib() {
        return mBaseLib;
    }
}
