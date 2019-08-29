package com.autonavi.minimap.agroup.inter;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public interface IJoinGroup extends bie {

    @Retention(RetentionPolicy.SOURCE)
    public @interface JoinType {
    }
}
