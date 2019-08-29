package com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.items;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.http.util.TextUtils;
import java.util.ArrayList;
import java.util.List;

public class DjangoDomainConf {
    private List<String> domainList = new ArrayList();

    public DjangoDomainConf() {
        this.domainList.add("dl.django.t.taobao.com");
        this.domainList.add("oalipay-dl-django.alicdn.com");
        this.domainList.add("alipay-dl.django.t.taobao.com");
    }

    public DjangoDomainConf(String json) {
        JSONArray array = JSON.parseArray(json);
        for (int i = 0; i < array.size(); i++) {
            String domain = array.getString(i);
            if (!TextUtils.isEmpty(domain)) {
                this.domainList.add(domain);
            }
        }
    }

    public boolean contains(String domain) {
        return this.domainList.contains(domain);
    }
}
