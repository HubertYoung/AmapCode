package com.alibaba.analytics.core.config;

import com.alibaba.analytics.core.db.Entity;
import com.alibaba.analytics.core.db.annotation.Column;
import com.alibaba.analytics.core.db.annotation.TableName;

@TableName("utap_system")
public class SystemConfig extends Entity {
    @Column("key")
    protected String key;
    @Column("value")
    protected String value;
}
