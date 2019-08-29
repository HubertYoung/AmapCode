package com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.items;

import com.alibaba.fastjson.annotation.JSONField;
import java.util.Arrays;

public class HostConfig {
    @JSONField(name = "tcs")
    public int taskOccursSwitch = 0;
    @JSONField(name = "twh")
    public String[] taskWhiteHosts = {"zos.alipayobjects.com", "tfs.alipayobjects.com", "t.alipayobjects.com", "pic.alipayobjects.com", "img.alicdn.com", "os.alipayobjects.com", "tfsimg.alipay.com", "gw.alicdn.com", "alipay.dl.django.t.taobao.com", "i.alipayobjects.com"};

    public String toString() {
        return "HostConfig{, taskOccursSwitch=" + this.taskOccursSwitch + "taskWhiteHosts=" + Arrays.toString(this.taskWhiteHosts) + '}';
    }
}
