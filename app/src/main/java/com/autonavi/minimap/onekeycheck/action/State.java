package com.autonavi.minimap.onekeycheck.action;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import proguard.annotation.KeepName;
import proguard.annotation.KeepPublicClassMembers;

@KeepPublicClassMembers
@KeepName
public class State {
    public static final int FINISH = 4;
    public static final int PREPARE = 0;
    public static final int PROCESS = 3;
    public static final int START = 1;
    private int a;

    @Target({ElementType.METHOD, ElementType.TYPE, ElementType.FIELD, ElementType.PARAMETER})
    @KeepPublicClassMembers
    @KeepName
    @Retention(RetentionPolicy.SOURCE)
    public @interface StateDesc {
    }

    public State() {
        this(0);
    }

    public State(int i) {
        update(i);
    }

    public int getState() {
        return this.a;
    }

    public void update(int i) {
        this.a = i;
    }
}
