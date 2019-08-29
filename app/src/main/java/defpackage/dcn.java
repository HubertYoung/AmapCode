package defpackage;

import android.text.TextUtils;
import com.alipay.mobile.beehive.rpc.RpcConstant;
import com.alipay.rdssecuritysdk.constant.DictionaryKeys;
import com.amap.location.sdk.fusion.LocationParams;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.bundle.share.entity.ShareBase;
import com.autonavi.sdk.location.LocationInstrument;
import com.autonavi.server.aos.serverkey;
import java.net.URLEncoder;
import org.json.JSONObject;

/* renamed from: dcn reason: default package */
/* compiled from: SendToCarOtherShare */
public final class dcn extends ShareBase {
    private f a;
    private bid b;

    public final int getShareType() {
        return 10;
    }

    public final void onFinishResult(String str) {
    }

    public dcn(f fVar) {
        this.a = fVar;
    }

    private static String a() {
        String ajxStorageItem = getAjxStorageItem("localPoiInfo");
        if (TextUtils.isEmpty(ajxStorageItem)) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        try {
            JSONObject jSONObject = new JSONObject(ajxStorageItem);
            JSONObject optJSONObject = jSONObject.optJSONObject("clientData");
            Object obj = null;
            JSONObject optJSONObject2 = optJSONObject != null ? optJSONObject.optJSONObject("poiInfo") : null;
            JSONObject optJSONObject3 = jSONObject.optJSONObject("aosData");
            JSONObject optJSONObject4 = optJSONObject3 != null ? optJSONObject3.optJSONObject(RpcConstant.BASE) : null;
            sb.append("longitude=");
            Object opt = optJSONObject2 != null ? optJSONObject2.opt(LocationParams.PARA_FLP_AUTONAVI_LON) : null;
            if (opt == null || TextUtils.isEmpty(opt.toString())) {
                opt = optJSONObject4 != null ? optJSONObject4.opt(DictionaryKeys.CTRLXY_X) : null;
            }
            sb.append(opt != null ? URLEncoder.encode(opt.toString()) : "");
            sb.append("&latitude=");
            Object opt2 = optJSONObject2 != null ? optJSONObject2.opt("lat") : null;
            if (opt2 == null || TextUtils.isEmpty(opt2.toString())) {
                opt2 = optJSONObject4 != null ? optJSONObject4.opt(DictionaryKeys.CTRLXY_Y) : null;
            }
            sb.append(opt2 != null ? URLEncoder.encode(opt2.toString()) : "");
            sb.append("&name=");
            Object opt3 = optJSONObject2 != null ? optJSONObject2.opt("name") : null;
            if (opt3 == null || TextUtils.isEmpty(opt3.toString())) {
                opt3 = optJSONObject4 != null ? optJSONObject4.opt("name") : null;
            }
            sb.append(opt3 != null ? URLEncoder.encode(opt3.toString()) : "");
            sb.append("&address=");
            Object opt4 = optJSONObject2 != null ? optJSONObject2.opt("address") : null;
            if (opt4 == null || TextUtils.isEmpty(opt4.toString())) {
                opt4 = optJSONObject4 != null ? optJSONObject4.opt("address") : null;
            }
            sb.append(opt4 != null ? URLEncoder.encode(opt4.toString()) : "");
            sb.append("&poi_type=");
            Object opt5 = optJSONObject2 != null ? optJSONObject2.opt("new_type") : null;
            sb.append(opt5 != null ? URLEncoder.encode(opt5.toString()) : "");
            sb.append("&id=");
            if (optJSONObject2 != null) {
                obj = optJSONObject2.opt(LocationInstrument.LOCATION_EXTRAS_KEY_POIID);
            }
            sb.append(obj != null ? URLEncoder.encode(obj.toString()) : "");
            String amapEncode = serverkey.amapEncode("2");
            String amapEncode2 = serverkey.amapEncode(sb.toString());
            StringBuilder sb2 = new StringBuilder();
            sb2.append("https://telematics.autonavi.com/sendtocar/?");
            sb2.append("ent=");
            sb2.append(!TextUtils.isEmpty(amapEncode) ? URLEncoder.encode(amapEncode) : "");
            sb2.append("&in=");
            sb2.append(!TextUtils.isEmpty(amapEncode2) ? URLEncoder.encode(amapEncode2) : "");
            return sb2.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public final void startShare() {
        this.b = AMapPageUtil.getPageContext();
        if (this.b == null || TextUtils.isEmpty(this.a.g)) {
            notifyShareResult(-1);
            return;
        }
        String a2 = a();
        if (!TextUtils.isEmpty(a2)) {
            PageBundle pageBundle = new PageBundle();
            pageBundle.putString("url", a2);
            pageBundle.putString("title", this.a.a);
            this.b.startPage((String) "amap.search.action.detial", pageBundle);
        }
        notifyShareResult(0);
    }
}
