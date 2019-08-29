package com.alibaba.baichuan.android.trade.config;

import android.content.Context;
import com.alibaba.baichuan.android.trade.AlibcContext;
import com.alibaba.baichuan.android.trade.adapter.ut.UserTrackerCompoment;
import com.alibaba.baichuan.android.trade.constants.ConfigConstant;
import com.alibaba.baichuan.android.trade.constants.UserTrackerConstants;
import com.alibaba.baichuan.android.trade.utils.code.Md5Utils;
import com.alibaba.baichuan.android.trade.utils.log.AlibcLogger;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Map;

public class b {
    /* access modifiers changed from: private */
    public String a = ConfigConstant.getConfigUrl();
    private Context b;
    /* access modifiers changed from: private */
    public a c;
    private final long d = 5000000;

    public interface a {
        void a(com.alibaba.baichuan.android.trade.config.a.a aVar, String str);

        void a(String str);
    }

    public b(Context context, a aVar) {
        this.b = context;
        this.c = aVar;
        b();
    }

    /* access modifiers changed from: private */
    public void a(String str) {
        a((String) null, str);
    }

    /* access modifiers changed from: private */
    public void a(String str, String str2) {
        UserTrackerCompoment.sendUseabilityFailure(UserTrackerConstants.U_FETCH_CONFIG, str2, str);
    }

    /* access modifiers changed from: private */
    public boolean a(com.alibaba.baichuan.android.trade.config.a.a aVar) {
        if (aVar.b()) {
            String b2 = b(aVar);
            aVar.c();
            Map map = (Map) aVar.a.get(ConfigConstant.CHECK_GROUP_NAME);
            if (map != null) {
                String str = (String) map.get("sign");
                if (str != null && str.equals(b2)) {
                    return true;
                }
            }
        }
        return false;
    }

    private String b(com.alibaba.baichuan.android.trade.config.a.a aVar) {
        StringBuilder sb = new StringBuilder();
        for (String str : aVar.a.keySet()) {
            sb.append(str);
            Map map = (Map) aVar.a.get(str);
            for (String str2 : map.keySet()) {
                sb.append(str2);
                sb.append((String) map.get(str2));
            }
        }
        try {
            sb.append(ConfigConstant.MD5_SALT);
            char[] charArray = sb.toString().toCharArray();
            Arrays.sort(charArray);
            return Md5Utils.md5Digest(new String(charArray).getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            StringBuilder sb2 = new StringBuilder("生成摘要错误");
            sb2.append(e.getMessage());
            AlibcLogger.e("AlibcConfigPullProcessor", sb2.toString());
            return null;
        }
    }

    private void b() {
        this.a = ConfigConstant.getConfigUrl();
    }

    private void c() {
        AlibcContext.executorService.a(new c(this), 2000);
    }

    /* access modifiers changed from: private */
    public void d() {
        UserTrackerCompoment.sendUseabilitySuccess(UserTrackerConstants.U_FETCH_CONFIG);
    }

    public void a() {
        AlibcLogger.d("AlibcConfigPullProcessor", "开启拉取网络配置");
        if (com.alibaba.baichuan.android.trade.utils.b.a.a(this.b)) {
            c();
            return;
        }
        this.c.a("没有网络，无法拉取config配置");
        AlibcLogger.i("AlibcConfigPullProcessor", "没有网络，无法拉取config配置");
    }
}
