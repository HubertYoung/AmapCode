package com.alibaba.analytics.core.config.timestamp;

import com.alibaba.analytics.core.db.Entity;
import com.alibaba.analytics.core.db.annotation.Column;
import com.alibaba.analytics.core.db.annotation.TableName;

@TableName("timestamp_config")
public class TimeStampEntity extends Entity {
    @Column("namespace")
    public String namespace;
    @Column("timestamp")
    public String timestamp;

    public TimeStampEntity() {
    }

    public TimeStampEntity(String str, String str2) {
        this.namespace = str;
        this.timestamp = str2;
    }
}
