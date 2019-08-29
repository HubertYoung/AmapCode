package com.alibaba.analytics.core.db;

import com.alibaba.analytics.core.Variables;
import com.alibaba.analytics.core.db.annotation.Column;
import com.alibaba.analytics.core.db.annotation.Ingore;

public class Entity {
    @Ingore
    private static boolean hasCheckdb = false;
    @Column("_id")
    public long _id = -1;

    public void store() {
        Variables.getInstance().getDbMgr().insert(this);
    }

    public void delete() {
        Variables.getInstance().getDbMgr().delete(this);
    }
}
