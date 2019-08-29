package com.autonavi.minimap.widget;

import android.content.Context;
import com.amap.bundle.statistics.LogManager;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.POI;
import com.autonavi.common.utils.Constant;
import com.autonavi.map.fragmentcontainer.GeocodePOI;
import com.autonavi.map.fragmentcontainer.GpsPOI;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;
import com.autonavi.minimap.basemap.errorback.inter.IErrorReportStarter;
import com.autonavi.minimap.basemap.traffic.TrafficUtil;
import com.autonavi.minimap.map.mapinterface.AbstractPoiDetailView;
import com.autonavi.minimap.map.mapinterface.AbstractPoiDetailView.Event;
import com.autonavi.minimap.search.model.searchpoi.searchpoitype.ChildStationPoiData;
import com.autonavi.sdk.log.util.LogConstant;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;

public class PoiDetailViewFactory {
    public static PoiDetailView createPoiDetailView() {
        if (DoNotUseTool.getActivity() == null) {
            return null;
        }
        PoiDetailView poiDetailView = new PoiDetailView(DoNotUseTool.getActivity());
        decorateDefault(poiDetailView);
        return poiDetailView;
    }

    public static void decorateDefault(AbstractPoiDetailView abstractPoiDetailView) {
        final bid pageContext = AMapPageUtil.getPageContext();
        if (abstractPoiDetailView != null && pageContext != null) {
            abstractPoiDetailView.bindEvent(0, new Event() {
                public final void onExecute(int i, POI poi) {
                    PageBundle pageBundle = new PageBundle();
                    pageBundle.putObject("POI", poi);
                    pageBundle.putBoolean("isGeoCode", PoiDetailViewFactory.isGeoCodePoint(poi));
                    pageBundle.putBoolean("isGPSPoint", PoiDetailViewFactory.isGpsPoint(poi));
                    pageBundle.putBoolean(Constant.KEY_IS_BACK, true);
                    pageBundle.putString("fromSource", "mainmap");
                    pageContext.startPage((String) "amap.search.action.poidetail", pageBundle);
                }
            });
            abstractPoiDetailView.bindEvent(3, new Event() {
                public final void onExecute(int i, POI poi) {
                    dfm dfm = (dfm) ank.a(dfm.class);
                    if (dfm != null) {
                        dfm.a(poi);
                    }
                }
            });
            abstractPoiDetailView.bindEvent(7, new Event() {
                public final void onExecute(int i, POI poi) {
                    IErrorReportStarter iErrorReportStarter = (IErrorReportStarter) ank.a(IErrorReportStarter.class);
                    if (iErrorReportStarter != null) {
                        iErrorReportStarter.startAddPOIFromXYSelectPoint(poi);
                    }
                }
            });
            abstractPoiDetailView.bindEvent(6, new Event() {
                public final void onExecute(int i, POI poi) {
                    Context context = DoNotUseTool.getContext();
                    if (context == null || aaw.d(context) != 0) {
                        IErrorReportStarter iErrorReportStarter = (IErrorReportStarter) ank.a(IErrorReportStarter.class);
                        if (iErrorReportStarter != null) {
                            iErrorReportStarter.startLocationError(poi);
                        }
                        return;
                    }
                    ToastHelper.showToast(context.getResources().getString(R.string.network_error_message));
                }
            });
            abstractPoiDetailView.bindEvent(2, new Event() {
                public final void onExecute(int i, POI poi) {
                    PageBundle pageBundle = new PageBundle();
                    if (PoiDetailViewFactory.isGpsPoint(poi)) {
                        POI clone = poi.clone();
                        clone.setName(lh.a(DoNotUseTool.getContext(), R.string.LocationMe));
                        pageBundle.putObject("bundle_key_poi_start", clone);
                    } else {
                        pageBundle.putObject("bundle_key_poi_end", poi.clone());
                    }
                    pageBundle.putObject("bundle_key_auto_route", Boolean.TRUE);
                    pageContext.startPage((String) "amap.extra.route.route", pageBundle);
                }
            });
            abstractPoiDetailView.bindEvent(1, new Event() {
                public final void onExecute(int i, POI poi) {
                    String str;
                    PageBundle pageBundle = new PageBundle();
                    pageBundle.putObject("POI", poi);
                    if (poi instanceof ChildStationPoiData) {
                        pageBundle.putObject("key_source_type", Integer.valueOf(3));
                    }
                    pageContext.startPage((String) "amap.search.action.category", pageBundle);
                    if (poi == null) {
                        str = "";
                    } else {
                        str = poi.getId();
                    }
                    LogManager.actionLogV25(LogConstant.SEARCH_RESULT_MAP, LogConstant.MAIN_MAP_GUIDE_BTN_VISIBLE, new SimpleEntry(TrafficUtil.POIID, str), new SimpleEntry("type", Integer.valueOf(0)));
                }
            });
            abstractPoiDetailView.bindEvent(5, new Event() {
                public final void onExecute(int i, POI poi) {
                    ank.a(djl.class);
                }
            });
            abstractPoiDetailView.bindEvent(8, new Event() {
                public final void onExecute(int i, POI poi) {
                    String type = poi.getType();
                    if (type.length() >= 4) {
                        type = type.substring(0, 4);
                    }
                    String phone = poi.getPhone();
                    if ("1001".equals(type) || "1002".equals(type)) {
                        ArrayList arrayList = new ArrayList();
                        if (phone != null && !"".equals(phone)) {
                            if (phone.indexOf(59) < 0) {
                                PoiDetailViewFactory.addPhoneList(arrayList, phone.substring(0, 3), phone, DoNotUseTool.getContext());
                            } else {
                                String[] split = phone.split(";");
                                for (int i2 = 0; i2 < split.length; i2++) {
                                    PoiDetailViewFactory.addPhoneList(arrayList, split[i2].substring(0, 3), split[i2], DoNotUseTool.getContext());
                                }
                            }
                        }
                        if (arrayList.size() > 0) {
                            bnz.a((List<String>) arrayList, pageContext.getActivity(), (String) LogConstant.SEARCH_RESULT_MAP);
                        }
                        return;
                    }
                    if (phone != null && !"".equals(phone)) {
                        if (phone.indexOf(";") > 0) {
                            ArrayList arrayList2 = new ArrayList();
                            String[] split2 = phone.split(";");
                            for (int i3 = 0; i3 < split2.length; i3++) {
                                StringBuilder sb = new StringBuilder();
                                sb.append(split2[i3]);
                                sb.append("$");
                                sb.append(split2[i3]);
                                arrayList2.add(sb.toString());
                            }
                            if (arrayList2.size() > 0) {
                                bnz.a((List<String>) arrayList2, pageContext.getActivity(), (String) LogConstant.SEARCH_RESULT_MAP);
                            }
                            return;
                        }
                        pageContext.getActivity();
                        bnz.a(phone);
                    }
                }
            });
        }
    }

    /* access modifiers changed from: private */
    public static void addPhoneList(ArrayList<String> arrayList, String str, String str2, Context context) {
        if (str.equals("400")) {
            StringBuilder sb = new StringBuilder();
            sb.append(String.format(context.getString(R.string.book_by_phone), new Object[]{str2}));
            sb.append("$");
            sb.append(str2);
            arrayList.add(sb.toString());
            return;
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append(String.format(context.getString(R.string.reception_phone), new Object[]{str2}));
        sb2.append("$");
        sb2.append(str2);
        arrayList.add(sb2.toString());
    }

    public static boolean isGpsPoint(POI poi) {
        return GpsPOI.class.isInstance(poi);
    }

    public static boolean isGeoCodePoint(POI poi) {
        return GeocodePOI.class.isInstance(poi);
    }
}
