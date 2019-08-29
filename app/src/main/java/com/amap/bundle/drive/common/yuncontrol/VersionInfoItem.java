package com.amap.bundle.drive.common.yuncontrol;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import proguard.annotation.KeepName;
import proguard.annotation.KeepPublicClassMembers;

@KeepPublicClassMembers
@KeepName
public class VersionInfoItem {
    public String file;
    public Map<String, String> items = new HashMap();
    public String md5_inner;
    public String md5_zip;
    public String path;
    public int size;
    public long version;

    public String toString() {
        StringBuilder sb = new StringBuilder("file:");
        sb.append(this.file);
        sb.append(", size:");
        sb.append(this.size);
        sb.append(", path:");
        sb.append(this.path);
        sb.append(", version:");
        sb.append(this.version);
        sb.append(", md5_zip:");
        sb.append(this.md5_zip);
        StringBuffer stringBuffer = new StringBuffer(sb.toString());
        for (Entry next : this.items.entrySet()) {
            StringBuilder sb2 = new StringBuilder(", [");
            sb2.append((String) next.getKey());
            sb2.append(" : ");
            sb2.append((String) next.getValue());
            sb2.append("]");
            stringBuffer.append(sb2.toString());
        }
        return stringBuffer.toString();
    }
}
