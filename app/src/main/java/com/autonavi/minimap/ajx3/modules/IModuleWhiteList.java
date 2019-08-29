package com.autonavi.minimap.ajx3.modules;

public interface IModuleWhiteList {
    String getModuleClassName(String str);

    Object[] getNewModule(String str);

    boolean hasRegistered(String str, Class cls);
}
