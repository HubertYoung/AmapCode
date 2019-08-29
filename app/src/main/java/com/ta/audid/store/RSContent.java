package com.ta.audid.store;

import com.ta.audid.db.Entity;
import com.ta.audid.db.annotation.Column;
import com.ta.audid.db.annotation.TableName;

@TableName("rs")
public class RSContent extends Entity {
    @Column("content")
    private String content = null;

    public RSContent() {
    }

    public RSContent(String str) {
        setContent(str);
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String str) {
        this.content = str;
    }
}
