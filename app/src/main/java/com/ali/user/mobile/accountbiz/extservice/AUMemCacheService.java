package com.ali.user.mobile.accountbiz.extservice;

public abstract class AUMemCacheService {
    public abstract Object get(String str, String str2);

    public abstract void put(String str, String str2, String str3, Object obj);

    public abstract Object remove(String str);

    public abstract void removeByGroup(String str);
}
