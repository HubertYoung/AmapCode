package com.ta.audid.db;

import com.ta.audid.Variables;
import com.ta.audid.db.annotation.Column;
import com.ta.audid.db.annotation.Ingore;

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
