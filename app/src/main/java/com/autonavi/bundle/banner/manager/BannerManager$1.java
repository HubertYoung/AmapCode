package com.autonavi.bundle.banner.manager;

import android.app.Application;
import android.text.TextUtils;
import com.alipay.mobile.beehive.eventbus.Subscribe;
import com.alipay.mobile.h5container.api.H5Param;
import com.alipay.mobile.tinyappcommon.h5plugin.H5SensorPlugin;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.bundle.banner.data.BannerItem;
import com.autonavi.bundle.banner.net.BannerResult;
import com.autonavi.common.Callback;
import java.util.Iterator;
import java.util.LinkedList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class BannerManager$1 implements Callback<BannerResult> {
    final /* synthetic */ String a;
    final /* synthetic */ String b;
    final /* synthetic */ a c;

    public BannerManager$1(String str, String str2, a aVar) {
        this.a = str;
        this.b = str2;
        this.c = aVar;
    }

    public final void callback(BannerResult bannerResult) {
        if (TextUtils.isEmpty(this.a) || !this.a.equalsIgnoreCase(bannerResult.token)) {
            LinkedList<BannerItem> linkedList = bannerResult.items;
            if (!TextUtils.equals(this.b, "20")) {
                JSONObject jSONObject = new JSONObject();
                try {
                    jSONObject.put(H5SensorPlugin.PARAM_INTERVAL, bannerResult.interval);
                    JSONArray jSONArray = new JSONArray();
                    if (linkedList != null) {
                        Iterator it = linkedList.iterator();
                        while (it.hasNext()) {
                            BannerItem bannerItem = (BannerItem) it.next();
                            JSONObject jSONObject2 = new JSONObject();
                            jSONObject2.put("action", bannerItem.action);
                            jSONObject2.put("id", bannerItem.id);
                            jSONObject2.put("msg_id", bannerItem.msg_id);
                            jSONObject2.put("tag", bannerItem.tag);
                            jSONObject2.put("image", bannerItem.imageURL);
                            jSONObject2.put("type", bannerItem.type);
                            jSONObject2.put("title", bannerItem.title);
                            jSONObject2.put("btitle", bannerItem.bannerTitle);
                            jSONObject2.put(Subscribe.THREAD_BACKGROUND, bannerItem.background);
                            jSONObject2.put("font", bannerItem.font);
                            jSONObject2.put("height", bannerItem.height);
                            jSONObject2.put(H5Param.MENU_ICON, bannerItem.icon);
                            jSONObject2.put("mIsHide", bannerItem.mIsHide);
                            jSONObject2.put("impression", bannerItem.impression);
                            jSONArray.put(jSONObject2);
                        }
                    }
                    jSONObject.put("items", jSONArray);
                    jSONObject.put("token", bannerResult.token);
                    Application application = AMapAppGlobal.getApplication();
                    StringBuilder sb = new StringBuilder("BANNER_DATA");
                    sb.append(this.b);
                    application.getSharedPreferences(sb.toString(), 0).edit().putString("latest_data", jSONObject.toString()).apply();
                } catch (JSONException e) {
                    AMapLog.e("banner", String.valueOf(e), true);
                }
            }
            if (this.c != null) {
                this.c.a(linkedList, (long) bannerResult.interval);
            }
        }
    }

    public final void error(Throwable th, boolean z) {
        if (TextUtils.equals(this.b, "26") && this.c != null) {
            this.c.a(null, 0);
        }
    }
}
