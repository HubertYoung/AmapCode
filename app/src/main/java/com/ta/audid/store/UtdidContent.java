package com.ta.audid.store;

import com.ta.audid.db.Entity;
import com.ta.audid.db.annotation.Column;
import com.ta.audid.db.annotation.Ingore;
import com.ta.audid.db.annotation.TableName;
import com.ta.audid.utils.UtdidLogger;

@TableName("utdid")
public class UtdidContent extends Entity {
    @Ingore
    public static final String FIELD_NAME_PRIORITY = "priority";
    @Ingore
    public static final String FIELD_NAME_TIME = "time";
    @Column("content")
    private String content;
    @Column("priority")
    public String priority;
    @Column("time")
    public String time;

    public UtdidContent() {
        this.time = null;
        this.priority = "3";
        this.content = null;
    }

    public UtdidContent(String str) {
        this.time = null;
        this.priority = "3";
        this.content = null;
        this.priority = "3";
        this.time = String.valueOf(System.currentTimeMillis());
        setContent(str);
    }

    public UtdidContent(String str, String str2) {
        this.time = null;
        this.priority = "3";
        this.content = null;
        this.priority = str;
        this.time = String.valueOf(System.currentTimeMillis());
        setContent(str2);
    }

    public String getContent() {
        return this.content;
    }

    public String getDecodedContent() {
        return UtdidContentUtil.getDecodedContent(this.content);
    }

    public void setContent(String str) {
        if (str != null) {
            try {
                this.content = UtdidContentUtil.getEncodedContent(str);
            } catch (Exception e) {
                UtdidLogger.e("", e, new Object[0]);
            }
        }
    }
}
