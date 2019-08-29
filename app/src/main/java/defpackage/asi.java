package defpackage;

import com.alipay.mobile.beehive.eventbus.Subscribe;
import com.alipay.mobile.h5container.api.H5Param;
import com.autonavi.bundle.banner.data.BannerItem;
import com.autonavi.bundle.banner.net.BannerResult;
import com.autonavi.sync.beans.GirfFavoriteRoute;
import java.util.LinkedList;
import org.json.JSONArray;
import org.json.JSONObject;

/* renamed from: asi reason: default package */
/* compiled from: BannerParser */
public final class asi {
    public static BannerResult a(JSONObject jSONObject) {
        BannerResult bannerResult = new BannerResult();
        bannerResult.items = new LinkedList<>();
        JSONArray optJSONArray = jSONObject.optJSONArray("data");
        bannerResult.token = jSONObject.optString("md5", "");
        bannerResult.responseTimestamp = jSONObject.optString("timestamp");
        int length = optJSONArray != null ? optJSONArray.length() : 0;
        for (int i = 0; i < length; i++) {
            JSONObject optJSONObject = optJSONArray.optJSONObject(i);
            if (optJSONObject != null) {
                bannerResult.interval = optJSONObject.optInt("slide_time") * 1000;
                BannerItem bannerItem = new BannerItem();
                bannerItem.id = optJSONObject.optString("id", "");
                bannerItem.msg_id = optJSONObject.optString("msg_id", "");
                bannerItem.tag = optJSONObject.optString("tag", "");
                bannerItem.bannerTitle = optJSONObject.optString("title", "");
                bannerItem.title = optJSONObject.optString("title", "");
                bannerItem.type = optJSONObject.optInt("type", -1);
                bannerItem.endDateTimestampInSecond = optJSONObject.optString(GirfFavoriteRoute.JSON_FIELD_ROUTE_END_TIME, "");
                bannerItem.background = optJSONObject.optString(Subscribe.THREAD_BACKGROUND, "");
                bannerItem.font = optJSONObject.optString("font", "");
                bannerItem.icon = optJSONObject.optString(H5Param.MENU_ICON, "");
                bannerItem.impression = optJSONObject.optString("impression", "");
                JSONArray optJSONArray2 = optJSONObject.optJSONArray("resource");
                if (optJSONArray2 != null && optJSONArray2.length() > 0) {
                    bannerItem.imageURL = optJSONArray2.optJSONObject(0).optString("content", "");
                }
                JSONObject optJSONObject2 = optJSONObject.optJSONObject("action");
                if (optJSONObject2 != null) {
                    String optString = optJSONObject2.optString("url", "");
                    if (optString != null) {
                        bannerItem.action = optString;
                    }
                }
                bannerResult.items.add(bannerItem);
            }
        }
        return bannerResult;
    }
}
