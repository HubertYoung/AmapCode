package com.autonavi.bundle.feedback.ajx;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout.LayoutParams;
import com.alipay.mobile.h5container.api.H5PageData;
import com.alipay.mobile.scansdk.constant.Constants;
import com.alipay.rdssecuritysdk.constant.DictionaryKeys;
import com.amap.bundle.blutils.app.ConfigerHelper;
import com.amap.bundle.datamodel.poi.POIFactory;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.searchservice.api.ISearchService;
import com.amap.bundle.searchservice.service.search.param.SuggestionParam;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.bundle.entity.sugg.TipItem;
import com.autonavi.common.Callback;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.core.JsFunctionCallback;
import com.autonavi.minimap.ajx3.modules.AbstractModule;
import com.autonavi.minimap.ajx3.modules.AjxMethod;
import com.autonavi.minimap.ajx3.modules.AjxModule;
import com.autonavi.minimap.ajx3.util.DimensionUtils;
import com.autonavi.minimap.search.model.SelectPoiFromMapBean;
import com.autonavi.sdk.location.LocationInstrument;
import com.autonavi.sync.beans.GirfFavoritePoint;
import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@AjxModule("feedback")
public final class ModuleFeedBack extends AbstractModule {
    public static final String INDEX = "index";
    public static final String MESSAGE = "message";
    public static final String MODULE_NAME = "feedback";
    public static final String RECOMMEND = "recommend";
    public static final String TITLE = "title";
    public static final String URL = "url";

    public ModuleFeedBack(IAjxContext iAjxContext) {
        super(iAjxContext);
    }

    @AjxMethod("openLocationErrorGuide")
    @Deprecated
    public final void openLocationErrorGuide() {
        bid pageContext = AMapPageUtil.getPageContext();
        if (pageContext != null) {
            aja aja = new aja(ConfigerHelper.getInstance().getFeedbackLocationErrorTips());
            aja.b = new ajf();
            aix aix = (aix) a.a.a(aix.class);
            if (aix != null) {
                aix.a(pageContext, aja);
            }
        }
    }

    @AjxMethod("openSelectPoi")
    @Deprecated
    public final void openSelectPoi(String str) {
        bid pageContext = AMapPageUtil.getPageContext();
        if (pageContext != null) {
            PageBundle pageBundle = new PageBundle();
            pageBundle.putObject("SelectPoiFromMapBean", createSelectPoiFromMapBean(str, false));
            pageContext.startPageForResult((String) "amap.basemap.action.base_select_fix_poi_from_map_page", pageBundle, 107);
        }
    }

    @AjxMethod("onSubmitResult")
    public final void onSubmitResult(String str, String str2) {
        String str3;
        AMapLog.i("----xing---->", "onSubmitResult---->bodyJsonString = ".concat(String.valueOf(str)));
        AMapLog.i("----xing---->", "onSubmitResult---->fileJsonArray = ".concat(String.valueOf(str2)));
        if (!TextUtils.isEmpty(str)) {
            JSONObject jSONObject = null;
            try {
                jSONObject = new JSONObject(str);
            } catch (Exception unused) {
            }
            int optInt = jSONObject.optInt("code");
            boolean z = false;
            boolean z2 = true;
            if (optInt != 1) {
                if (jSONObject.has("fail_toast") && jSONObject.optInt("fail_toast") != 1) {
                    z2 = false;
                }
                if (z2) {
                    Context context = DoNotUseTool.getContext();
                    if (context != null) {
                        if (optInt == 135) {
                            str3 = context.getString(R.string.error_report_to_much_try_tomorrow);
                        } else if (optInt == 3) {
                            str3 = context.getString(R.string.error_wrong_params);
                        } else if (optInt == 92) {
                            str3 = context.getString(R.string.error_report_retry);
                        } else if (optInt == 139) {
                            str3 = context.getString(R.string.error_report_repeat);
                        } else {
                            str3 = context.getString(R.string.ic_net_error_tipinfo);
                        }
                        ToastHelper.showLongToast(str3);
                    } else {
                        return;
                    }
                }
                return;
            }
            if (!jSONObject.has("location_log") || jSONObject.optInt("location_log") == 1) {
                bid pageContext = AMapPageUtil.getPageContext();
                if (pageContext != null) {
                    LocationInstrument.getInstance().setFeedbackTime(pageContext.getContext(), Calendar.getInstance().getTimeInMillis());
                }
            }
            if (!TextUtils.isEmpty(str2)) {
                ArrayList arrayList = new ArrayList();
                try {
                    JSONArray jSONArray = new JSONArray(str2);
                    for (int i = 0; i < jSONArray.length(); i++) {
                        String optString = jSONArray.optJSONObject(i).optString("filePath");
                        AMapLog.i("----xing---->", "---->filePath=".concat(String.valueOf(optString)));
                        if (!TextUtils.isEmpty(optString)) {
                            arrayList.add(optString);
                        }
                    }
                    ahm.a(new Runnable(arrayList) {
                        final /* synthetic */ List a;

                        {
                            this.a = r1;
                        }

                        public final void run() {
                            for (String str : this.a) {
                                if (str != null) {
                                    try {
                                        ahd.a(new File(str));
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                    });
                } catch (Exception unused2) {
                }
            }
            if (!jSONObject.has("open_success") || jSONObject.optInt("open_success") == 1) {
                z = true;
            }
            if (z) {
                String optString2 = jSONObject.optString("record_id");
                String feedBackSuccessPage = ConfigerHelper.getInstance().getFeedBackSuccessPage();
                PageBundle pageBundle = new PageBundle();
                pageBundle.putString("url", feedBackSuccessPage);
                pageBundle.putString("record_id", optString2);
                pageBundle.putBoolean("native_feedback", true);
                aja aja = new aja(pageBundle.getString("url"));
                aja.b = new ajf();
                aix aix = (aix) a.a.a(aix.class);
                if (aix != null) {
                    aix.a(AMapPageUtil.getPageContext(), pageBundle, aja);
                }
            }
        }
    }

    @AjxMethod("openPoi")
    @Deprecated
    public final void openPoi(String str) {
        bid pageContext = AMapPageUtil.getPageContext();
        if (!TextUtils.isEmpty(str) && pageContext != null) {
            PageBundle pageBundle = new PageBundle();
            pageBundle.putObject("POI", bnx.a(str));
            pageContext.startPage((String) "amap.search.action.poidetail", pageBundle);
        }
    }

    @AjxMethod("openSelectPointMapPage")
    public final void openSelectPointMapPage(final JsFunctionCallback jsFunctionCallback, String str) {
        if (jsFunctionCallback != null) {
            if (TextUtils.isEmpty(str)) {
                jsFunctionCallback.callback("");
                return;
            }
            bid pageContext = AMapPageUtil.getPageContext();
            if (pageContext == null) {
                jsFunctionCallback.callback("");
                return;
            }
            try {
                JSONObject jSONObject = new JSONObject(str);
                PageBundle pageBundle = new PageBundle();
                pageBundle.putInt("resultType", jSONObject.optInt("resultType", 0));
                pageBundle.putObject("SelectPoiFromMapBean", createSelectPoiFromMapBean(jSONObject.optString("poi"), true));
                pageBundle.putInt("h", DimensionUtils.standardUnitToPixel((float) jSONObject.optInt("h", 0)));
                pageBundle.putInt("w", DimensionUtils.standardUnitToPixel((float) jSONObject.optInt("w", 0)));
                pageBundle.putObject("resultCallback", new Callback<String>() {
                    public void callback(String str) {
                        jsFunctionCallback.callback(str);
                    }

                    public void error(Throwable th, boolean z) {
                        jsFunctionCallback.callback("");
                    }
                });
                pageBundle.putBoolean(GirfFavoritePoint.JSON_FIELD_POI_NEW_TYPE, true);
                pageContext.startPage((String) "amap.basemap.action.base_select_fix_poi_from_map_page", pageBundle);
            } catch (JSONException unused) {
                jsFunctionCallback.callback("");
            }
        }
    }

    @AjxMethod("openSelectRoadMapPage")
    public final void openSelectRoadMapPage(final JsFunctionCallback jsFunctionCallback, String str) {
        if (jsFunctionCallback != null) {
            if (TextUtils.isEmpty(str)) {
                jsFunctionCallback.callback("");
                return;
            }
            bid pageContext = AMapPageUtil.getPageContext();
            if (pageContext == null) {
                jsFunctionCallback.callback("");
                return;
            }
            try {
                JSONObject jSONObject = new JSONObject(str);
                PageBundle pageBundle = new PageBundle();
                pageBundle.putInt("resultType", jSONObject.optInt("resultType", 0));
                POI oldPOI = createSelectPoiFromMapBean(jSONObject.optString("poi"), true).getOldPOI();
                if (oldPOI != null) {
                    pageBundle.putObject(H5PageData.KEY_UC_START, oldPOI);
                }
                pageBundle.putInt("h", DimensionUtils.standardUnitToPixel((float) jSONObject.optInt("h", 0)));
                pageBundle.putInt("w", DimensionUtils.standardUnitToPixel((float) jSONObject.optInt("w", 0)));
                pageBundle.putObject("resultCallback", new Callback<String>() {
                    public void callback(String str) {
                        jsFunctionCallback.callback(str);
                    }

                    public void error(Throwable th, boolean z) {
                        jsFunctionCallback.callback("");
                    }
                });
                pageBundle.putBoolean(GirfFavoritePoint.JSON_FIELD_POI_NEW_TYPE, true);
                pageContext.startPage((String) "amap.basemap.action.select_road_from_map", pageBundle);
            } catch (JSONException unused) {
                jsFunctionCallback.callback("");
            }
        }
    }

    @AjxMethod("searchPoi")
    @Deprecated
    public final void searchPoi(final JsFunctionCallback jsFunctionCallback, String str) {
        if (jsFunctionCallback != null) {
            bty mapView = DoNotUseTool.getMapView();
            final bid pageContext = AMapPageUtil.getPageContext();
            if (mapView == null || pageContext == null) {
                jsFunctionCallback.callback("");
                return;
            }
            GeoPoint latestPosition = LocationInstrument.getInstance().getLatestPosition();
            ISearchService iSearchService = (ISearchService) a.a.a(ISearchService.class);
            if (iSearchService != null) {
                SuggestionParam suggestionParam = new SuggestionParam();
                suggestionParam.adcode = (long) latestPosition.getAdCode();
                suggestionParam.keyWord = str;
                suggestionParam.category = null;
                suggestionParam.suggestType = "poi";
                suggestionParam.mCenter = latestPosition;
                iSearchService.a(suggestionParam, 2, (aeb<aux>) new aeb<aux>() {
                    public final /* synthetic */ void callback(Object obj) {
                        aux aux = (aux) obj;
                        if (pageContext.isAlive()) {
                            List<TipItem> list = aux.b;
                            if (list == null || list.isEmpty()) {
                                jsFunctionCallback.callback("");
                                return;
                            }
                            JSONArray jSONArray = new JSONArray();
                            for (TipItem next : list) {
                                if (next != null) {
                                    POI createPOI = POIFactory.createPOI(next.name, new GeoPoint(next.x, next.y));
                                    createPOI.setId(next.poiid);
                                    createPOI.setAdCode(next.adcode);
                                    StringBuilder sb = new StringBuilder();
                                    sb.append(next.district);
                                    sb.append(next.addr);
                                    createPOI.setAddr(sb.toString().trim());
                                    createPOI.setType(next.newType);
                                    createPOI.getPoiExtra().put("icon_info", Integer.valueOf(next.iconinfo));
                                    createPOI.setEndPoiExtension(next.endPoiExtension);
                                    createPOI.setTransparent(next.transparent);
                                    if (next.x_entr > 0.0d && next.y_entr > 0.0d) {
                                        ArrayList arrayList = new ArrayList();
                                        arrayList.add(new GeoPoint(next.x_entr, next.y_entr));
                                        createPOI.setEntranceList(arrayList);
                                    }
                                    JSONObject b2 = bnx.b(createPOI);
                                    if (b2 != null) {
                                        jSONArray.put(b2);
                                    }
                                }
                            }
                            jsFunctionCallback.callback(jSONArray.toString());
                        }
                    }

                    public final void error(int i) {
                        if (pageContext.isAlive()) {
                            jsFunctionCallback.callback("");
                        }
                    }
                });
            }
        }
    }

    @AjxMethod("imagePreview")
    public final void jumpToPreview(final JsFunctionCallback jsFunctionCallback, String str) {
        if (jsFunctionCallback != null) {
            if (TextUtils.isEmpty(str)) {
                jsFunctionCallback.callback("");
                return;
            }
            try {
                JSONObject jSONObject = new JSONObject(str);
                boolean optBoolean = jSONObject.optBoolean("hiddenRightBtn");
                ArrayList<String> prepareBeanData = prepareBeanData(jSONObject);
                if (prepareBeanData != null && prepareBeanData.size() > 0) {
                    AnonymousClass4 r4 = null;
                    if (!optBoolean) {
                        r4 = new b() {
                            public final void a(int i) {
                                if (i >= 0) {
                                    jsFunctionCallback.callback(Integer.valueOf(i));
                                }
                            }
                        };
                    }
                    avg avg = new avg(DoNotUseTool.getActivity(), prepareBeanData, r4, jSONObject.optInt("index", 0) + 1);
                    if (euk.a()) {
                        euk.a((Dialog) avg, 0);
                        int a = euk.a(getContext().getNativeContext());
                        View findViewById = avg.getWindow().findViewById(R.id.error_tetle);
                        LayoutParams layoutParams = (LayoutParams) findViewById.getLayoutParams();
                        layoutParams.topMargin = a;
                        findViewById.setLayoutParams(layoutParams);
                    }
                    avg.show();
                }
            } catch (JSONException unused) {
                jsFunctionCallback.callback("");
            }
        }
    }

    private ArrayList<String> prepareBeanData(JSONObject jSONObject) {
        ArrayList<String> arrayList = new ArrayList<>();
        if (jSONObject != null) {
            JSONArray optJSONArray = jSONObject.optJSONArray("list");
            if (optJSONArray != null) {
                for (int i = 0; i < optJSONArray.length(); i++) {
                    JSONObject optJSONObject = optJSONArray.optJSONObject(i);
                    if (optJSONObject != null) {
                        arrayList.add(optJSONObject.optString("url", ""));
                    }
                }
            }
        }
        return arrayList;
    }

    @AjxMethod("addPhoto")
    public final void addPhoto(final JsFunctionCallback jsFunctionCallback, String str) {
        if (jsFunctionCallback != null) {
            bid pageContext = AMapPageUtil.getPageContext();
            if (TextUtils.isEmpty(str) || pageContext == null) {
                jsFunctionCallback.callback("");
                return;
            }
            try {
                JSONObject jSONObject = new JSONObject(str);
                JSONObject optJSONObject = jSONObject.optJSONObject("example");
                String optString = jSONObject.optString("_action", "");
                String optString2 = jSONObject.optString("businessName", "");
                String optString3 = jSONObject.optString(Constants.SERVICE_TITLE_TEXT, "");
                String optString4 = jSONObject.optString("maxLength", "");
                String optString5 = jSONObject.optString("onlyCamera", "");
                String optString6 = jSONObject.optString("onlyPicture", "");
                String optString7 = jSONObject.optString("returnType", "imgbase64");
                AnonymousClass5 r10 = new Callback<JSONObject>() {
                    public void callback(JSONObject jSONObject) {
                        jsFunctionCallback.callback(jSONObject.toString());
                    }

                    public void error(Throwable th, boolean z) {
                        jsFunctionCallback.callback("");
                    }
                };
                PageBundle pageBundle = new PageBundle();
                pageBundle.putString("_action", optString);
                pageBundle.putObject("callback", r10);
                pageBundle.putString("businessName", optString2);
                pageBundle.putString(Constants.SERVICE_TITLE_TEXT, optString3);
                pageBundle.putString("maxLength", optString4);
                pageBundle.putObject("example", optJSONObject);
                pageBundle.putString("returnType", optString7);
                boolean z = !TextUtils.isEmpty(optString5) && Boolean.parseBoolean(optString5);
                boolean z2 = !TextUtils.isEmpty(optString6) && Boolean.parseBoolean(optString6);
                if (z) {
                    pageContext.startPage((String) "amap.basemap.action.photo_select_camera", pageBundle);
                } else if (z2) {
                    pageContext.startPage((String) "amap.basemap.action.photo_select_gallery", pageBundle);
                } else {
                    pageContext.startPage((String) "amap.basemap.action.photo_select_camera_gallery", pageBundle);
                }
            } catch (JSONException unused) {
                jsFunctionCallback.callback("");
            }
        }
    }

    @Deprecated
    private SelectPoiFromMapBean createSelectPoiFromMapBean(String str, boolean z) {
        SelectPoiFromMapBean selectPoiFromMapBean;
        GeoPoint geoPoint;
        if (TextUtils.isEmpty(str)) {
            bty mapView = DoNotUseTool.getMapView();
            if (!z || mapView == null) {
                selectPoiFromMapBean = ave.a(POIFactory.createPOI("我的位置", LocationInstrument.getInstance().getLatestPosition()));
            } else {
                selectPoiFromMapBean = ave.a(POIFactory.createPOI("", mapView.o()));
            }
        } else {
            try {
                JSONObject jSONObject = new JSONObject(str);
                String optString = jSONObject.optString("poiName", "");
                if (z) {
                    geoPoint = new GeoPoint(jSONObject.optDouble(DictionaryKeys.CTRLXY_X), jSONObject.optDouble(DictionaryKeys.CTRLXY_Y));
                } else {
                    geoPoint = new GeoPoint(jSONObject.optInt(DictionaryKeys.CTRLXY_X), jSONObject.optInt(DictionaryKeys.CTRLXY_Y));
                }
                selectPoiFromMapBean = ave.a(POIFactory.createPOI(optString, geoPoint));
            } catch (Exception unused) {
                selectPoiFromMapBean = ave.a(POIFactory.createPOI("我的位置", LocationInstrument.getInstance().getLatestPosition()));
            }
        }
        selectPoiFromMapBean.setLevel(18);
        return selectPoiFromMapBean;
    }
}
