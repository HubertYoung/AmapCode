package com.amap.bundle.cloudconfig.aocs.model;

import com.autonavi.common.annotation.Name;
import proguard.annotation.Keep;
import proguard.annotation.KeepPublicClassMembers;

@KeepPublicClassMembers
@Keep
public class ConfigModule {
    public static final int FLAG_BY_BOOTACTIVE = 0;
    public static final int FLAG_BY_LOOP = 1;
    private String a;
    private String b;
    private int c = -1;
    private long d;
    private String e;

    public ConfigModule() {
    }

    public ConfigModule(String str) {
        this.a = str;
    }

    public ConfigModule(String str, String str2) {
        this.a = str;
        this.b = str2;
    }

    public ConfigModule(String str, String str2, String str3) {
        this.a = str;
        this.b = str2;
        this.e = str3;
    }

    @Name("a")
    public String getName() {
        return this.a;
    }

    public void setName(String str) {
        this.a = str;
    }

    @Name("b")
    public String getVersion() {
        return this.b;
    }

    public void setVersion(String str) {
        this.b = str;
    }

    @Name("c")
    public int getByFlag() {
        return this.c;
    }

    public void setByFlag(int i) {
        this.c = i;
    }

    @Name("e")
    public long getLoopDuration() {
        return this.d;
    }

    public void setLoopDuration(long j) {
        this.d = j;
    }

    @Name("f")
    public String getValue() {
        return this.e;
    }

    public void setValue(String str) {
        this.e = str;
    }
}
