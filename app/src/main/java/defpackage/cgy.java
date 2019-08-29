package defpackage;

import android.net.Uri;
import android.text.TextUtils;
import com.amap.bundle.blutils.app.ConfigerHelper;
import com.amap.bundle.network.util.NetworkReachability;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.annotation.Router;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;
import com.autonavi.minimap.basemap.favorites.data.RouteItem;
import java.util.List;

@Router({"toolbox"})
/* renamed from: cgy reason: default package */
/* compiled from: ToolboxRouter */
public class cgy extends esk {
    public boolean start(ese ese) {
        Uri uri = ese.a;
        List<String> pathSegments = uri.getPathSegments();
        if (pathSegments.size() > 0 && TextUtils.equals("home", pathSegments.get(0))) {
            ddq ddq = (ddq) a.a.a(ddq.class);
            if (ddq != null) {
                ddq.a();
            }
            return true;
        } else if (!"thirdpartyservice".equals(uri.getHost()) || !uri.getPath().contains("drivehelp")) {
            if ("Mine".equalsIgnoreCase(uri.getQueryParameter("featureName"))) {
                if ("ToolBox".equals(uri.getQueryParameter("page"))) {
                    if ("DriveHelp".equalsIgnoreCase(uri.getQueryParameter(RouteItem.ITEM_TAG))) {
                        if (!NetworkReachability.b()) {
                            ToastHelper.showLongToast(lh.a(AMapAppGlobal.getApplication(), R.string.error_check_network_and_retry));
                        } else if (TextUtils.isEmpty(uri.getQueryParameter("url"))) {
                            ddq ddq2 = (ddq) a.a.a(ddq.class);
                            if (ddq2 != null) {
                                ddq2.b();
                            }
                        } else {
                            cga.a(uri);
                        }
                        return true;
                    } else if ("QRCode".equalsIgnoreCase(uri.getQueryParameter(RouteItem.ITEM_TAG))) {
                        awt awt = (awt) a.a.a(awt.class);
                        if (awt != null) {
                            awt.a((String) "kit");
                        }
                        return true;
                    }
                } else if ("Fortune".equals(uri.getQueryParameter("page"))) {
                    if ("GoldCoin".equalsIgnoreCase(uri.getQueryParameter(RouteItem.ITEM_TAG))) {
                        StringBuilder sb = new StringBuilder();
                        sb.append(ConfigerHelper.getInstance().getGoldcoinUrl());
                        sb.append("index.html");
                        aja aja = new aja(sb.toString());
                        aja.b = new ajf();
                        aix aix = (aix) a.a.a(aix.class);
                        if (aix != null) {
                            aix.a(AMapPageUtil.getPageContext(), aja);
                        }
                        return true;
                    } else if ("Lottery".equalsIgnoreCase(uri.getQueryParameter(RouteItem.ITEM_TAG))) {
                        String str = "";
                        bgx bgx = (bgx) a.a.a(bgx.class);
                        if (bgx != null) {
                            str = bgx.getUrl("/order/lottery.html");
                        }
                        aja aja2 = new aja(str);
                        aja2.b = new ajf();
                        aix aix2 = (aix) a.a.a(aix.class);
                        if (aix2 != null) {
                            aix2.a(AMapPageUtil.getPageContext(), aja2);
                        }
                        return true;
                    }
                }
            }
            return false;
        } else {
            if (!NetworkReachability.b()) {
                ToastHelper.showLongToast(AMapAppGlobal.getApplication().getString(R.string.network_error_message));
            }
            if (TextUtils.isEmpty(uri.getQueryParameter("url"))) {
                ddq ddq3 = (ddq) a.a.a(ddq.class);
                if (ddq3 != null) {
                    ddq3.b();
                }
            } else {
                cga.a(uri);
            }
            return true;
        }
    }
}
