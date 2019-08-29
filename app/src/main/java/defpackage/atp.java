package defpackage;

import android.net.Uri;
import android.text.TextUtils;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.annotation.Router;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.ajx3.Ajx3Page;
import com.autonavi.minimap.ajx3.CarInfo;
import com.autonavi.minimap.basemap.route.page.CarLicenseScanPage;
import java.util.List;

@Router({"carOwnerService"})
/* renamed from: atp reason: default package */
/* compiled from: CarOwnerRouter */
public class atp extends esk {
    public boolean start(ese ese) {
        if (ese != null) {
            Uri uri = ese.a;
            String str = null;
            if (uri != null) {
                List<String> pathSegments = uri.getPathSegments();
                if (pathSegments != null && pathSegments.size() > 0) {
                    str = pathSegments.get(0);
                }
            }
            if (TextUtils.isEmpty(str)) {
                return false;
            }
            if (TextUtils.equals(str, "goNativePage")) {
                if ("scan".equals(uri.getQueryParameter("type"))) {
                    final String queryParameter = uri.getQueryParameter("data");
                    kj.a(AMapAppGlobal.getTopActivity(), new String[]{"android.permission.CAMERA"}, (b) new b() {
                        public final void run() {
                            a(queryParameter);
                        }

                        public final void reject() {
                            a(queryParameter);
                        }

                        private static void a(String str) {
                            bid pageContext = AMapPageUtil.getPageContext();
                            if (pageContext != null) {
                                PageBundle pageBundle = new PageBundle();
                                pageBundle.putObject("carInfo", CarInfo.JsonToCarInfo(str));
                                pageContext.startPageForResult(CarLicenseScanPage.class, pageBundle, 1002);
                            }
                        }
                    });
                    return true;
                }
            } else if (TextUtils.equals(str, "carList")) {
                bid pageContext = AMapPageUtil.getPageContext();
                PageBundle pageBundle = new PageBundle();
                CarInfo carInfo = new CarInfo();
                carInfo.entryType = "DrivingCheivement";
                pageBundle.putString("jsData", CarInfo.CarInfoToJson(carInfo).toString());
                pageBundle.putString("url", "path://amap_bundle_carowner/src/car_owner/CarListViewController.page.js");
                pageContext.startPage(Ajx3Page.class, pageBundle);
                return true;
            }
        }
        return false;
    }
}
