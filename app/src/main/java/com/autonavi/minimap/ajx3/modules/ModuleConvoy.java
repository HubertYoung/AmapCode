package com.autonavi.minimap.ajx3.modules;

import android.content.Context;
import com.amap.bundle.datamodel.poi.POIFactory;
import com.autonavi.bundle.maphome.api.reverse.ReverseGeocodeManager;
import com.autonavi.common.Callback;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.core.JsFunctionCallback;
import com.autonavi.minimap.search.model.SearchConst;
import com.autonavi.sdk.location.LocationInstrument;
import java.lang.ref.WeakReference;
import org.json.JSONException;
import org.json.JSONObject;

@AjxModule("convoy")
public class ModuleConvoy extends AbstractModule {
    public static final String MODULE_NAME = "convoy";
    private static volatile int sequence_getMyLocation;

    static class GetMyLocationGeoReverseCallback implements Callback<POI> {
        private String mId = "";
        JsFunctionCallback mJsCallback;
        private WeakReference<Object> mOwner;

        public GetMyLocationGeoReverseCallback(Object obj, JsFunctionCallback jsFunctionCallback, String str) {
            this.mId = str;
            this.mOwner = new WeakReference<>(obj);
            this.mJsCallback = jsFunctionCallback;
        }

        public void callback(POI poi) {
            if (!(this.mOwner.get() == null || this.mJsCallback == null)) {
                this.mJsCallback.callback(ModuleConvoy.buildGetMyLocationResult(this.mId, poi));
            }
        }

        public void error(Throwable th, boolean z) {
            if (!(this.mOwner.get() == null || this.mJsCallback == null)) {
                this.mJsCallback.callback(ModuleConvoy.buildGetMyLocationResult(this.mId, null));
            }
        }
    }

    public ModuleConvoy(IAjxContext iAjxContext) {
        super(iAjxContext);
    }

    @AjxMethod("openDestSearchPage")
    public void openDestSearchPage(JsFunctionCallback jsFunctionCallback) {
        openDestSearchPage(getNativeContext(), jsFunctionCallback);
    }

    @AjxMethod("startCarNavi")
    public void startCarNavi(String str) {
        dfm dfm = (dfm) ank.a(dfm.class);
        if (dfm != null) {
            dfm.a(str);
        }
    }

    public static void openDestSearchPage(Context context, final JsFunctionCallback jsFunctionCallback) {
        if (jsFunctionCallback != null) {
            bid pageContext = AMapPageUtil.getPageContext();
            if (pageContext == null) {
                jsFunctionCallback.callback("");
                return;
            }
            PageBundle pageBundle = new PageBundle();
            pageBundle.putInt("search_for", 2);
            pageBundle.putBoolean("isHideMyPosition", true);
            pageBundle.putString("hint", context.getString(R.string.convoy_dest_search_hint));
            pageBundle.putObject("callback", new Callback<POI>() {
                public final void callback(POI poi) {
                    String str;
                    try {
                        JSONObject b = bnx.b(poi);
                        str = b != null ? b.toString() : "";
                    } catch (Exception unused) {
                        str = "";
                    }
                    if (jsFunctionCallback != null) {
                        jsFunctionCallback.callback(str);
                    }
                }

                public final void error(Throwable th, boolean z) {
                    if (jsFunctionCallback != null) {
                        jsFunctionCallback.callback("");
                    }
                }
            });
            pageContext.startPage((String) "search.fragment.SearchCallbackFragment", pageBundle);
        }
    }

    @AjxMethod(invokeMode = "sync", value = "getMyLocation")
    public String getMyLocation(JsFunctionCallback jsFunctionCallback) {
        return getMyLocation(this, jsFunctionCallback);
    }

    public static String getMyLocation(Object obj, JsFunctionCallback jsFunctionCallback) {
        int i = sequence_getMyLocation + 1;
        sequence_getMyLocation = i;
        String valueOf = String.valueOf(i);
        if (LocationInstrument.getInstance().getLatestPosition(5) != null) {
            GeoPoint latestPosition = LocationInstrument.getInstance().getLatestPosition();
            if (latestPosition != null) {
                ReverseGeocodeManager.get(new GetMyLocationGeoReverseCallback(obj, jsFunctionCallback, valueOf), POIFactory.createPOI(SearchConst.a, latestPosition).getPoint());
            } else {
                jsFunctionCallback.callback(buildGetMyLocationResult(valueOf, null));
            }
        } else {
            jsFunctionCallback.callback(buildGetMyLocationResult(valueOf, null));
        }
        return valueOf;
    }

    /* access modifiers changed from: private */
    public static String buildGetMyLocationResult(String str, POI poi) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("id", str);
            if (poi != null) {
                JSONObject b = bnx.b(poi);
                if (b != null) {
                    jSONObject.put("data", b);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jSONObject.toString();
    }
}
