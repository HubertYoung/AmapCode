package com.alibaba.analytics.core.model;

import android.text.TextUtils;
import com.alibaba.analytics.core.db.Entity;
import com.alibaba.analytics.core.db.annotation.Column;
import com.alibaba.analytics.core.db.annotation.Ingore;
import com.alibaba.analytics.core.db.annotation.TableName;
import com.alibaba.analytics.core.logbuilder.LogAssemble;
import com.alibaba.analytics.core.logbuilder.LogPriorityMgr;
import com.alibaba.analytics.core.logbuilder.SessionTimeAndIndexMgr;
import com.alibaba.analytics.utils.Base64;
import com.alibaba.analytics.utils.RC4;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

@TableName("log")
public class Log extends Entity {
    @Ingore
    public static final String DEFAULT_PRIORITY = "3";
    @Ingore
    private static final int EVENTID_INTERVAL = 800000;
    @Ingore
    public static final String FIELD_NAME_PRIORITY = "priority";
    @Ingore
    public static final String FIELD_NAME_TIME = "time";
    @Ingore
    private static final String TAG = "UTLog";
    @Column("_index")
    public String _index = "";
    @Ingore
    private String arg1;
    @Ingore
    private String arg2;
    @Ingore
    private String arg3;
    @Ingore
    private Map<String, String> args;
    @Column("content")
    private String content;
    @Column("eventId")
    public String eventId;
    @Ingore
    private String page;
    @Column("priority")
    public String priority = "3";
    @Column("streamId")
    public String streamId;
    @Column("time")
    public String time = null;
    @Ingore
    private int topicId = 0;

    @Deprecated
    private String addEventIdInterval(String str) {
        return "";
    }

    @Deprecated
    public String getPlus80WLogContent() {
        return "";
    }

    public Log() {
    }

    public Log(String str, List<String> list, String str2, Map<String, String> map) {
        this.priority = str;
        this.streamId = buildStreamId(list);
        this.eventId = str2;
        this.time = String.valueOf(System.currentTimeMillis());
        this._index = createIndex();
        map.put(LogField.RESERVE3.toString(), this._index);
        setContent(LogAssemble.assemble(map));
    }

    public Log(String str, String str2, String str3, String str4, String str5, Map<String, String> map) {
        this.eventId = str2;
        this.page = str;
        this.arg1 = str3;
        this.arg2 = str4;
        this.arg3 = str5;
        this.args = map;
        this.time = String.valueOf(System.currentTimeMillis());
        this._index = createIndex();
        this.priority = LogPriorityMgr.getInstance().getLogLevel(str2);
        buildLogContent();
    }

    public String getContent() {
        try {
            byte[] decode = Base64.decode(this.content.getBytes("UTF-8"), 2);
            if (decode != null) {
                return new String(RC4.rc4(decode));
            }
            return null;
        } catch (Exception unused) {
            return null;
        }
    }

    @Deprecated
    public String getCipherContent() {
        return this.content;
    }

    public void setContent(String str) {
        if (str != null) {
            try {
                this.content = new String(Base64.encode(RC4.rc4(str.getBytes()), 2), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }

    @Deprecated
    public void setCipherContent(String str) {
        this.content = str;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("Log [eventId=");
        sb.append(this.eventId);
        sb.append(", index=");
        sb.append(this._index);
        sb.append("]");
        return sb.toString();
    }

    public void buildLogContent() {
        if (TextUtils.isEmpty(this.time)) {
            this.time = String.valueOf(System.currentTimeMillis());
        }
        setContent(LogAssemble.assemble(this.page, this.eventId, this.arg1, this.arg2, this.arg3, this.args, this._index, this.time));
    }

    private String buildStreamId(List<String> list) {
        if (list == null) {
            return "";
        }
        if (list.size() == 1) {
            return list.get(0);
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            if (i >= 0) {
                sb.append(",");
            }
            sb.append(list.get(i));
        }
        return sb.toString();
    }

    private String createIndex() {
        String str = "";
        StringBuilder sb = new StringBuilder();
        sb.append(SessionTimeAndIndexMgr.getInstance().getSessionTimestamp());
        String sb2 = sb.toString();
        if (!TextUtils.isEmpty(sb2)) {
            str = sb2.length() >= 2 ? sb2.substring(sb2.length() - 2, sb2.length()) : sb2;
        }
        if ("2202".equalsIgnoreCase(this.eventId)) {
            return String.format("%s%06d,2202_%06d", new Object[]{str, Long.valueOf(SessionTimeAndIndexMgr.getInstance().logIndexIncrementAndGet()), Long.valueOf(SessionTimeAndIndexMgr.getInstance().m2202LogIndexIncrementAndGet())});
        }
        return String.format("%s%06d", new Object[]{str, Long.valueOf(SessionTimeAndIndexMgr.getInstance().logIndexIncrementAndGet())});
    }

    public int getTopicId() {
        return this.topicId;
    }

    public void setTopicId(int i) {
        this.topicId = i;
    }
}
