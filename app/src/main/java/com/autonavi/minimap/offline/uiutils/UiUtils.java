package com.autonavi.minimap.offline.uiutils;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import com.amap.bundle.statistics.LogManager;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.map.fragmentcontainer.page.dialog.NodeAlertDialogPage;
import com.autonavi.map.fragmentcontainer.page.dialog.NodeAlertDialogPage.Builder;
import com.autonavi.map.fragmentcontainer.page.dialog.NodeAlertDialogPage.NodeDialogFragmentOnClickListener;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.offline.OfflineNativeSdk;
import com.autonavi.minimap.offline.OfflineSDK;
import com.autonavi.minimap.offline.WorkThreadManager;
import com.autonavi.minimap.offline.WorkThreadManager.OfflineTask;
import com.autonavi.minimap.offline.inter.inner.ICheckOfflineResponse;
import com.autonavi.minimap.offline.utils.OfflineSpUtil;
import com.autonavi.minimap.offline.utils.UserReport;
import com.autonavi.minimap.offlinesdk.model.CityInfo;
import com.autonavi.sdk.location.LocationInstrument;
import com.autonavi.sync.beans.GirfFavoriteRoute;
import com.tencent.connect.common.Constants;
import java.text.DecimalFormat;

public class UiUtils {
    public static void checkOfflineNavi(bid bid, final ICheckOfflineResponse iCheckOfflineResponse) {
        if (bid != null && bid.isAlive()) {
            final FragmentActivity fragmentActivity = (FragmentActivity) DoNotUseTool.getActivity();
            if (fragmentActivity == null) {
                if (iCheckOfflineResponse != null) {
                    iCheckOfflineResponse.callback(false);
                }
                return;
            }
            WorkThreadManager.start(new OfflineTask() {
                public final Object doBackground() throws Exception {
                    if (!OfflineNativeSdk.getInstance().isInit()) {
                        if (iCheckOfflineResponse != null) {
                            iCheckOfflineResponse.callback(false);
                        }
                        UiUtils.showCommonDialog(fragmentActivity, "离线导航数据安装中，请稍后再试", new NodeDialogFragmentOnClickListener() {
                            public final void onClick(NodeAlertDialogPage nodeAlertDialogPage) {
                            }
                        });
                        return null;
                    }
                    lj access$000 = UiUtils.getCurrentAdCity();
                    if (access$000 == null) {
                        fragmentActivity.runOnUiThread(new Runnable() {
                            public final void run() {
                                ToastHelper.showLongToast("当前定位不成功，无法选择离线优先模式进行导航");
                                if (iCheckOfflineResponse != null) {
                                    iCheckOfflineResponse.callback(false);
                                }
                            }
                        });
                        return null;
                    }
                    int i = -1;
                    try {
                        i = access$000.j;
                    } catch (NumberFormatException e) {
                        kf.a((Throwable) e);
                    }
                    boolean checkIsNaviDataDownloaded = OfflineSDK.getInstance().checkIsNaviDataDownloaded(i);
                    iCheckOfflineResponse.callback(checkIsNaviDataDownloaded);
                    if (!checkIsNaviDataDownloaded) {
                        String str = "";
                        if (i > 0) {
                            StringBuilder sb = new StringBuilder("您还没有下载");
                            sb.append(access$000.a);
                            sb.append("离线导航数据，无法离线导航，是否下载？");
                            str = sb.toString();
                        }
                        UiUtils.showCommonDialog(fragmentActivity, str, "立即下载", "忽略", new NodeDialogFragmentOnClickListener() {
                            public final void onClick(NodeAlertDialogPage nodeAlertDialogPage) {
                                nodeAlertDialogPage.finish();
                                PageBundle pageBundle = new PageBundle();
                                pageBundle.putString(UiController.OFFLiNE_DOWNOAD_CURRENT_CITY_NAVI, "Current_city_navi");
                                UiController.startCityDataFragment(pageBundle, nodeAlertDialogPage);
                            }
                        }, new NodeDialogFragmentOnClickListener() {
                            public final void onClick(NodeAlertDialogPage nodeAlertDialogPage) {
                                nodeAlertDialogPage.finish();
                            }
                        });
                    }
                    return null;
                }
            });
        }
    }

    public static void showCommonDialog(final Activity activity, final String str, final NodeDialogFragmentOnClickListener nodeDialogFragmentOnClickListener) {
        activity.runOnUiThread(new Runnable() {
            public final void run() {
                if (activity != null && !activity.isFinishing()) {
                    Builder builder = new Builder(activity);
                    builder.setTitle((CharSequence) str);
                    builder.setNegativeButton((CharSequence) "取消", (NodeDialogFragmentOnClickListener) new NodeDialogFragmentOnClickListener() {
                        public final void onClick(NodeAlertDialogPage nodeAlertDialogPage) {
                            nodeAlertDialogPage.finish();
                        }
                    });
                    builder.setPositiveButton((CharSequence) "确定", nodeDialogFragmentOnClickListener);
                    AMapPageUtil.startAlertDialogPage(builder);
                }
            }
        });
    }

    public static void showCommonDialog(Activity activity, String str, String str2, String str3, NodeDialogFragmentOnClickListener nodeDialogFragmentOnClickListener, NodeDialogFragmentOnClickListener nodeDialogFragmentOnClickListener2) {
        final Activity activity2 = activity;
        final String str4 = str;
        final String str5 = str3;
        final NodeDialogFragmentOnClickListener nodeDialogFragmentOnClickListener3 = nodeDialogFragmentOnClickListener2;
        final String str6 = str2;
        final NodeDialogFragmentOnClickListener nodeDialogFragmentOnClickListener4 = nodeDialogFragmentOnClickListener;
        AnonymousClass3 r0 = new Runnable() {
            public final void run() {
                if (activity2 != null && !activity2.isFinishing()) {
                    Builder builder = new Builder(activity2);
                    builder.setTitle((CharSequence) str4);
                    builder.setNegativeButton((CharSequence) str5, nodeDialogFragmentOnClickListener3);
                    builder.setPositiveButton((CharSequence) str6, nodeDialogFragmentOnClickListener4);
                    AMapPageUtil.startAlertDialogPage(builder);
                }
            }
        };
        activity.runOnUiThread(r0);
    }

    public static void prepareViewMap(CityInfo cityInfo) {
        bid pageContext = AMapPageUtil.getPageContext();
        if (pageContext != null && cityInfo != null) {
            try {
                LogManager.actionLogV2(UserReport.PAGE_OFFLINEDATA_DOWNLOADEDMGR, "B053", UserReport.createJSONObj(GirfFavoriteRoute.JSON_FIELD_ROUTE_POI_NAME, cityInfo.name));
                PageBundle pageBundle = new PageBundle();
                int i = cityInfo.adcode;
                if (i == 0) {
                    pageBundle.putObject("key_map_center", new GeoPoint(221015799, 101716492));
                    pageBundle.putInt("key_map_level", 4);
                } else {
                    lj a = li.a().a((int) ((long) i));
                    if (a == null) {
                        a = li.a().a((int) ((long) cityInfo.cityId));
                    }
                    if (a != null) {
                        pageBundle.putObject("key_map_center", new GeoPoint(a.f, a.g));
                        pageBundle.putInt("key_map_level", a.h);
                        pageBundle.putString("key_area_name", a.a);
                    }
                }
                pageBundle.putString(Constants.KEY_ACTION, "action_switch_city");
                pageContext.startPage((String) "amap.basemap.action.default_page", pageBundle);
            } catch (Exception unused) {
            }
        }
    }

    public static void gotoOfflineNavi() {
        OfflineSpUtil.setNaviConfigOnline(false);
        ToastHelper.showToast("快捷导航算路已设置为离线优先");
        PageBundle pageBundle = new PageBundle("plugin.minimap.RouteFragment", "com.autonavi.minimap");
        bax bax = (bax) a.a.a(bax.class);
        if (bax != null) {
            bax.a(pageBundle);
        }
    }

    public static String formatStr(double d) {
        return new DecimalFormat("0.0").format(d);
    }

    /* access modifiers changed from: private */
    public static lj getCurrentAdCity() {
        GeoPoint latestPosition = LocationInstrument.getInstance().getLatestPosition();
        if (latestPosition != null) {
            return li.a().b(latestPosition.x, latestPosition.y);
        }
        return null;
    }
}
