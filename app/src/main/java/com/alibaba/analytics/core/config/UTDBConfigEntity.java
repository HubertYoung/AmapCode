package com.alibaba.analytics.core.config;

import com.alibaba.analytics.core.db.Entity;
import com.alibaba.analytics.core.db.annotation.Column;
import com.alibaba.analytics.core.db.annotation.Ingore;
import com.alibaba.analytics.core.db.annotation.TableName;

@TableName("onlineconfig")
public class UTDBConfigEntity extends Entity {
    @Column("timestamp")
    private long mConfTimestamp = 0;
    @Column("content")
    private String mContent = null;
    @Column("groupname")
    private String mGroupname = null;
    @Ingore
    private boolean mIs304 = false;

    public String getGroupname() {
        return this.mGroupname;
    }

    public void setConfContent(String str) {
        this.mContent = str;
    }

    public void setGroupname(String str) {
        this.mGroupname = str;
    }

    public String getConfContent() {
        return this.mContent;
    }

    public long getConfTimestamp() {
        return this.mConfTimestamp;
    }

    public void setConfTimestamp(long j) {
        this.mConfTimestamp = j;
    }

    public void set304Flag() {
        this.mIs304 = true;
    }

    public boolean is304() {
        return this.mIs304;
    }
}
