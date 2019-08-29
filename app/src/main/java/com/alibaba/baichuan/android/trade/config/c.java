package com.alibaba.baichuan.android.trade.config;

import com.alibaba.baichuan.android.trade.config.a.a;
import com.alibaba.baichuan.android.trade.constants.ConfigConstant;
import com.alibaba.baichuan.android.trade.constants.UserTrackerConstants;
import com.alibaba.baichuan.android.trade.utils.http.HttpHelper;
import com.alibaba.baichuan.android.trade.utils.http.HttpHelper.HttpHelpterException;
import com.alibaba.baichuan.android.trade.utils.log.AlibcLogger;
import org.json.JSONException;
import org.json.JSONObject;

class c implements Runnable {
    final /* synthetic */ b a;

    c(b bVar) {
        this.a = bVar;
    }

    public void run() {
        String str = null;
        try {
            if (!ConfigConstant.getConfigUrl().equals(this.a.a)) {
                this.a.a = ConfigConstant.getConfigUrl();
            }
            StringBuilder sb = new StringBuilder("开始从网络拉取config数据,url为：");
            sb.append(this.a.a);
            AlibcLogger.d("AlibcConfigPullProcessor", sb.toString());
            String str2 = HttpHelper.get(this.a.a, null);
            JSONObject jSONObject = new JSONObject(str2);
            StringBuilder sb2 = new StringBuilder("网络拉取的config数据为");
            sb2.append(jSONObject.toString());
            AlibcLogger.d("AlibcConfigPullProcessor", sb2.toString());
            a aVar = new a();
            aVar.a(jSONObject);
            if (this.a.a(aVar)) {
                AlibcLogger.d("AlibcConfigPullProcessor", "网络拉取config数据成功");
                this.a.d();
                this.a.c.a(aVar, str2);
                return;
            }
            AlibcLogger.e("AlibcConfigPullProcessor", "config文件校验失败");
            this.a.a((String) UserTrackerConstants.EM_CHECK_FAILURE);
            this.a.c.a("config文件校验失败");
        } catch (HttpHelpterException e) {
            StringBuilder sb3 = new StringBuilder("获取Http网络错误");
            sb3.append(e.getMessage());
            AlibcLogger.e("AlibcConfigPullProcessor", sb3.toString());
            if (e.statusCode != -999) {
                str = String.valueOf(e.statusCode);
            }
            this.a.a(str, (String) UserTrackerConstants.EM_NETWORK_ERROR);
            this.a.c.a(e.getMessage());
        } catch (JSONException e2) {
            StringBuilder sb4 = new StringBuilder("解析JSON出错");
            sb4.append(e2.getMessage());
            AlibcLogger.e("AlibcConfigPullProcessor", sb4.toString());
            this.a.a((String) UserTrackerConstants.EM_ANALYSE_FAILURE);
            this.a.c.a(e2.getMessage());
        }
    }
}
