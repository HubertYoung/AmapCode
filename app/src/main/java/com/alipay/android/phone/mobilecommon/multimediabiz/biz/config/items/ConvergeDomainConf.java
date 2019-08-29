package com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.items;

import android.text.TextUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;

public class ConvergeDomainConf {
    private HashMap<String, String> mConvergeMap = new HashMap<>();

    public ConvergeDomainConf(String json) {
        initDefaultConfig();
        if (!TextUtils.isEmpty(json)) {
            JSONArray array = JSON.parseArray(json);
            for (int i = 0; i < array.size(); i++) {
                JSONObject item = array.getJSONObject(i);
                if (item != null) {
                    JSONArray sources = item.getJSONArray("sources");
                    String target = item.getString("target");
                    for (int k = 0; k < sources.size(); k++) {
                        this.mConvergeMap.put(sources.getString(k), target);
                    }
                }
            }
        }
    }

    private void initDefaultConfig() {
        this.mConvergeMap.put("img1.tbcdn.cn", "gw.alicdn.com");
        this.mConvergeMap.put("img2.tbcdn.cn", "gw.alicdn.com");
        this.mConvergeMap.put("img3.tbcdn.cn", "gw.alicdn.com");
        this.mConvergeMap.put("img4.tbcdn.cn", "gw.alicdn.com");
        this.mConvergeMap.put("gd1.alicdn.com", "gw.alicdn.com");
        this.mConvergeMap.put("gd2.alicdn.com", "gw.alicdn.com");
        this.mConvergeMap.put("gd3.alicdn.com", "gw.alicdn.com");
        this.mConvergeMap.put("gd4.alicdn.com", "gw.alicdn.com");
        this.mConvergeMap.put("gd5.alicdn.com", "gw.alicdn.com");
        this.mConvergeMap.put("gd6.alicdn.com", "gw.alicdn.com");
        this.mConvergeMap.put("gd7.alicdn.com", "gw.alicdn.com");
        this.mConvergeMap.put("gd8.alicdn.com", "gw.alicdn.com");
        this.mConvergeMap.put("gtms01.alicdn.com", "gw.alicdn.com");
        this.mConvergeMap.put("gtms02.alicdn.com", "gw.alicdn.com");
        this.mConvergeMap.put("gtms03.alicdn.com", "gw.alicdn.com");
        this.mConvergeMap.put("gtms04.alicdn.com", "gw.alicdn.com");
        this.mConvergeMap.put("g01.a.alicdn.com", "gw.alicdn.com");
        this.mConvergeMap.put("g02.a.alicdn.com", "gw.alicdn.com");
        this.mConvergeMap.put("g03.a.alicdn.com", "gw.alicdn.com");
        this.mConvergeMap.put("g04.a.alicdn.com", "gw.alicdn.com");
        this.mConvergeMap.put("g05.a.alicdn.com", "gw.alicdn.com");
        this.mConvergeMap.put("g06.a.alicdn.com", "gw.alicdn.com");
        this.mConvergeMap.put("g07.a.alicdn.com", "gw.alicdn.com");
        this.mConvergeMap.put("g08.a.alicdn.com", "gw.alicdn.com");
        this.mConvergeMap.put("g01.b.alicdn.com", "gw.alicdn.com");
        this.mConvergeMap.put("g02.b.alicdn.com", "gw.alicdn.com");
        this.mConvergeMap.put("g03.b.alicdn.com", "gw.alicdn.com");
        this.mConvergeMap.put("g04.b.alicdn.com", "gw.alicdn.com");
        this.mConvergeMap.put("g01.s.alicdn.com", "gw.alicdn.com");
        this.mConvergeMap.put("g02.s.alicdn.com", "gw.alicdn.com");
        this.mConvergeMap.put("g03.s.alicdn.com", "gw.alicdn.com");
        this.mConvergeMap.put("g04.s.alicdn.com", "gw.alicdn.com");
        this.mConvergeMap.put("g05.s.alicdn.com", "gw.alicdn.com");
        this.mConvergeMap.put("g06.s.alicdn.com", "gw.alicdn.com");
        this.mConvergeMap.put("g07.s.alicdn.com", "gw.alicdn.com");
        this.mConvergeMap.put("g08.s.alicdn.com", "gw.alicdn.com");
        this.mConvergeMap.put("g01.t.alicdn.com", "gw.alicdn.com");
        this.mConvergeMap.put("g02.t.alicdn.com", "gw.alicdn.com");
        this.mConvergeMap.put("g03.t.alicdn.com", "gw.alicdn.com");
        this.mConvergeMap.put("g04.t.alicdn.com", "gw.alicdn.com");
        this.mConvergeMap.put("gw.alicdn.com", "gw.alicdn.com");
        this.mConvergeMap.put("gw1.alicdn.com", "gw.alicdn.com");
        this.mConvergeMap.put("gw2.alicdn.com", "gw.alicdn.com");
        this.mConvergeMap.put("gw3.alicdn.com", "gw.alicdn.com");
        this.mConvergeMap.put("gju1.alicdn.com", "gw.alicdn.com");
        this.mConvergeMap.put("gju2.alicdn.com", "gw.alicdn.com");
        this.mConvergeMap.put("gju3.alicdn.com", "gw.alicdn.com");
        this.mConvergeMap.put("gju4.alicdn.com", "gw.alicdn.com");
        this.mConvergeMap.put("gi1.md.alicdn.com", "gw.alicdn.com");
        this.mConvergeMap.put("gi2.md.alicdn.com", "gw.alicdn.com");
        this.mConvergeMap.put("gi3.md.alicdn.com", "gw.alicdn.com");
        this.mConvergeMap.put("gi4.md.alicdn.com", "gw.alicdn.com");
        this.mConvergeMap.put("img.taobaocdn.com", "gw.alicdn.com");
        this.mConvergeMap.put("img01.taobaocdn.com", "gw.alicdn.com");
        this.mConvergeMap.put("img02.taobaocdn.com", "gw.alicdn.com");
        this.mConvergeMap.put("img03.taobaocdn.com", "gw.alicdn.com");
        this.mConvergeMap.put("img04.taobaocdn.com", "gw.alicdn.com");
        this.mConvergeMap.put("img05.taobaocdn.com", "gw.alicdn.com");
        this.mConvergeMap.put("img06.taobaocdn.com", "gw.alicdn.com");
        this.mConvergeMap.put("img07.taobaocdn.com", "gw.alicdn.com");
        this.mConvergeMap.put("img08.taobaocdn.com", "gw.alicdn.com");
        this.mConvergeMap.put("tfsimg.alipay.com", "gw.alipayobjects.com/tfs");
        this.mConvergeMap.put("tfs.alipayobjects.com", "gw.alipayobjects.com/tfs");
        this.mConvergeMap.put("pic.alipayobjects.com", "gw.alipayobjects.com/pic");
        this.mConvergeMap.put("os.alipayobjects.com", "gw.alipayobjects.com/os");
        this.mConvergeMap.put("zos.alipayobjects.com", "gw.alipayobjects.com/zos");
        this.mConvergeMap.put("g.alipayobjects.com", "gw.alipayobjects.com/g");
        this.mConvergeMap.put("i.alipayobjects.com", "gw.alipayobjects.com/i");
        this.mConvergeMap.put("a.alipayobjects.com", "gw.alipayobjects.com/a");
    }

    public String getConvergeTargetDomain(String domain) {
        return this.mConvergeMap.get(domain);
    }
}
