package com.alipay.mobile.common.cache.disk;

import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import java.text.SimpleDateFormat;

public class Entity {
    private String a;
    private String b;
    private int c;
    private long d;
    private String e;
    private long f;
    private long g;
    private String h;
    protected String mGroup;

    public Entity(String owner, String group, String url, int usedTime, long size, String path, long createTime, long period, String contentType) {
        if (owner == null) {
            this.a = "-";
        } else {
            this.a = owner;
        }
        if (group == null) {
            this.mGroup = "-";
        } else {
            this.mGroup = group;
        }
        if (url == null) {
            this.b = "";
        } else {
            this.b = url;
        }
        this.c = usedTime;
        this.d = size;
        if (path == null) {
            this.e = "";
        } else {
            this.e = path;
        }
        this.f = createTime;
        this.g = period;
        if (contentType == null) {
            this.h = "";
        } else {
            this.h = contentType;
        }
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public String getOwner() {
        return this.a;
    }

    public String getGroup() {
        return this.mGroup;
    }

    public String getUrl() {
        return this.b;
    }

    public int getUsedTime() {
        return this.c;
    }

    public void use() {
        this.c++;
    }

    public long getSize() {
        return this.d;
    }

    public String getPath() {
        return this.e;
    }

    public long getCreateTime() {
        return this.f;
    }

    public long getPeriod() {
        return this.g;
    }

    public String getContentType() {
        return this.h;
    }

    public boolean expire() {
        return this.f + (this.g * 1000) < System.currentTimeMillis();
    }

    public boolean authenticate(String owner) {
        if (this.a.equalsIgnoreCase("-")) {
            return true;
        }
        if (owner == null) {
            LoggerFactory.getTraceLogger().warn((String) "Entity", (String) "authenticate: owner is null");
            return false;
        } else if (!owner.contains(this.a)) {
            return false;
        } else {
            return true;
        }
    }

    public String toString() {
        return String.format("url: %s usedTime: %d size: %d path: %s createTime:%s period: %d content-type: %s owner: %s", new Object[]{this.b, Integer.valueOf(this.c), Long.valueOf(this.d), this.e, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Long.valueOf(this.f)), Long.valueOf(this.g), this.h, this.a});
    }
}
