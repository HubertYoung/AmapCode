package com.autonavi.minimap.onekeycheck.module;

import android.text.TextUtils;
import com.alibaba.fastjson.JSONObject;
import com.amap.bundle.network.request.param.NetworkParam;
import java.io.Serializable;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import proguard.annotation.KeepName;
import proguard.annotation.KeepPublicClassMembers;

@KeepPublicClassMembers
@KeepName
public class CloudInterfInfos extends ResultData {
    public List<String> cdn_urls;
    public List<RequestUnit> service_urls;
    public String tracert_url = "m5.amap.com";

    @KeepPublicClassMembers
    @KeepName
    public static class RequestUnit implements Serializable {
        public String csId = NetworkParam.getCsid();
        public JSONObject header;
        public boolean is_accs;
        public String method;
        public String name;
        public String params;
        public String url;

        private Set<Entry<String, Object>> filterHeads() {
            if (this.header != null) {
                return this.header.entrySet();
            }
            return null;
        }

        public void addHeader(bph bph) {
            Set<Entry<String, Object>> filterHeads = filterHeads();
            if (filterHeads != null && bph != null) {
                for (Entry next : filterHeads) {
                    bph.addHeader((String) next.getKey(), (String) next.getValue());
                }
            }
        }

        public String getUrlWithCsId() {
            if (TextUtils.isEmpty(this.url)) {
                return "";
            }
            StringBuffer stringBuffer = new StringBuffer(this.url);
            stringBuffer.append("&csid=");
            stringBuffer.append(this.csId);
            return stringBuffer.toString();
        }
    }
}
