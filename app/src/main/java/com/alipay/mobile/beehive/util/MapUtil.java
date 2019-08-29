package com.alipay.mobile.beehive.util;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.View.OnClickListener;
import com.alipay.mobile.antui.dialog.AUListDialog;
import com.alipay.mobile.antui.dialog.AUListDialog.OnItemClickListener;
import com.alipay.mobile.antui.dialog.PopMenuItem;
import com.alipay.mobile.beehive.api.BeehiveConstant;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import java.util.ArrayList;

public class MapUtil {
    /* access modifiers changed from: private */
    public static String packagename_autoNavi = "com.autonavi.minimap";

    public static void popupActionDialog(Context mContext, double dlongitude, double dlatitude, String dSnippet) {
        final ArrayList items = new ArrayList();
        items.add(new PopMenuItem((String) "高德地图", (Drawable) null));
        AUListDialog auListDialog = new AUListDialog((String) null, items, false, (String) null, (OnClickListener) null, (String) null, (OnClickListener) null, mContext);
        final Context context = mContext;
        final double d = dlongitude;
        final double d2 = dlatitude;
        final String str = dSnippet;
        auListDialog.setOnItemClickListener((OnItemClickListener) new OnItemClickListener() {
            public final void onItemClick(int i) {
                if (((PopMenuItem) items.get(i)).getName().equals("高德地图")) {
                    MapUtil.startNaviApp(context, MapUtil.packagename_autoNavi, d, d2, str);
                }
            }
        });
        auListDialog.show();
    }

    public static void startNaviApp(Context mContext, String packageName, double dlongitude, double dlatitude, String dSnippet) {
        try {
            if (mContext.getPackageManager().getLaunchIntentForPackage(packageName) == null) {
                mContext.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("http://wap.amap.com/")));
            } else if (packageName.equals(packagename_autoNavi)) {
                LoggerFactory.getTraceLogger().info(BeehiveConstant.TAG, "使用高德地图");
                Intent intent = new Intent("android.intent.action.VIEW");
                intent.addCategory("android.intent.category.DEFAULT");
                intent.setPackage("com.autonavi.minimap");
                StringBuilder sb = new StringBuilder();
                sb.append("androidamap://route?sourceApplication=softname");
                sb.append("&dlat=").append(dlatitude);
                sb.append("&dlon=").append(dlongitude);
                sb.append("&dname=").append(dSnippet);
                sb.append("&dev=0&m=0&t=4");
                intent.setData(Uri.parse(sb.toString()));
                mContext.startActivity(intent);
            }
        } catch (Exception e) {
            LoggerFactory.getTraceLogger().error(BeehiveConstant.TAG, "startNaviApp " + e.getMessage());
        }
    }
}
