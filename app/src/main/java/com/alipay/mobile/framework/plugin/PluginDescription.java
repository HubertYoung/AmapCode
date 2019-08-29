package com.alipay.mobile.framework.plugin;

import com.alipay.mobile.framework.MicroDescription;
import com.alipay.mobile.quinox.utils.StringUtil;
import com.alipay.mobile.quinox.utils.bytedata.ByteOrderDataUtil;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.util.HashMap;
import java.util.Map;

public class PluginDescription extends MicroDescription<PluginDescription> {
    private String a;
    private String b;
    private String c;
    private Map<String, String> d = new HashMap();

    public String getType() {
        return this.a;
    }

    public void setType(String mType) {
        this.a = mType;
    }

    public String getMsg() {
        return this.b;
    }

    public void setMsg(String mMsg) {
        this.b = mMsg;
    }

    public String getBundleName() {
        return this.c;
    }

    public void setBundleName(String bundleName) {
        this.c = bundleName;
    }

    public PluginDescription() {
        super(1);
    }

    public PluginDescription serialize(BufferedOutputStream bos) {
        super.serialize(bos);
        ByteOrderDataUtil.writeString2(bos, this.a);
        ByteOrderDataUtil.writeString2(bos, this.b);
        String[] extraKeys = new String[this.d.size()];
        this.d.keySet().toArray(extraKeys);
        String[] extraValues = new String[this.d.size()];
        this.d.values().toArray(extraValues);
        ByteOrderDataUtil.writeStringArray2(bos, extraKeys);
        ByteOrderDataUtil.writeStringArray2(bos, extraValues);
        return this;
    }

    public PluginDescription deserialize(BufferedInputStream bis) {
        super.deserialize(bis);
        this.a = ByteOrderDataUtil.readString2(bis);
        this.b = ByteOrderDataUtil.readString2(bis);
        String[] extraKeys = ByteOrderDataUtil.readStringArray2(bis);
        String[] extraValues = ByteOrderDataUtil.readStringArray2(bis);
        if (!(extraKeys == null || extraValues == null || extraKeys.length != extraValues.length)) {
            for (int index = 0; index < extraKeys.length; index++) {
                this.d.put(extraKeys[index], extraValues[index]);
            }
        }
        return this;
    }

    public String getExtra(String key) {
        return this.d.get(key);
    }

    public void putExtra(String key, String value) {
        if (key != null) {
            this.d.put(key, value);
        }
    }

    public String toString() {
        return "PluginDescription [mType=" + this.a + ", mMsg=" + this.b + ", mExtras=" + StringUtil.map2String(this.d) + ", mName=" + this.mName + ", mClassName=" + this.mClassName + ", mClassLoader=" + this.mClassLoader + "]";
    }
}
