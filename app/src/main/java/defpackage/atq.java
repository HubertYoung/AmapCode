package defpackage;

import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.LogItem;
import com.alipay.mobile.h5container.api.H5Param;
import com.autonavi.annotation.Router;
import com.autonavi.bundle.account.api.IAccountService;
import com.autonavi.common.PageBundle;
import com.autonavi.common.SuperId;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.minimap.ajx3.Ajx3Page;
import com.autonavi.minimap.ajx3.CarInfo;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

@Router({"violations"})
/* renamed from: atq reason: default package */
/* compiled from: ViolationsRouter */
public class atq extends esk {
    private static String a(String str, String str2) {
        try {
            return URLEncoder.encode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return str2;
        }
    }

    public boolean start(ese ese) {
        Uri uri = ese.a;
        boolean z = false;
        if (!TextUtils.equals("home", uri.getPathSegments().get(0))) {
            return false;
        }
        IAccountService iAccountService = (IAccountService) a.a.a(IAccountService.class);
        if (iAccountService != null) {
            z = iAccountService.a();
        }
        if (z) {
            Map<String, String> a = agt.a(uri.getQueryParameter("url"), (String) "ISO-8859-1");
            StringBuilder sb = new StringBuilder("trafficViolations/index.html?type=queryNative&userId=");
            sb.append(a.get(H5Param.URL));
            sb.append("&carNumber=");
            sb.append(a.get(SuperId.BIT_1_MAIN_BUSSTATION));
            sb.append("&carDriver=");
            sb.append(a.get("f"));
            sb.append("&carCode=");
            sb.append(a.get("m"));
            sb.append("&telephone=");
            sb.append(a.get(LogItem.MM_C15_K4_TIME));
            sb.append("&dataSource=");
            sb.append(a.get("dataSource"));
            String sb2 = sb.toString();
            StringBuilder sb3 = new StringBuilder("amapuri://webview/local?hide_title=1&url=");
            sb3.append(TextUtils.isEmpty(sb2) ? "" : a(sb2, ""));
            DoNotUseTool.startScheme(new Intent("android.intent.action.VIEW", Uri.parse(sb3.toString())));
        } else {
            CarInfo carInfo = new CarInfo();
            carInfo.entryType = "Violation";
            carInfo.needOpenUri = "1";
            carInfo.outUri = uri.toString();
            PageBundle pageBundle = new PageBundle();
            pageBundle.putString("url", "path://amap_bundle_carowner/src/car_owner/CarAddViewController.page.js");
            pageBundle.putString("jsData", CarInfo.CarInfoToJson(carInfo).toString());
            startPage(Ajx3Page.class, pageBundle);
        }
        return true;
    }
}
