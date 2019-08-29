package com.autonavi.minimap.map;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import com.amap.bundle.datamodel.poi.POIFactory;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.jni.eyrie.amap.maps.MapViewManager;
import com.autonavi.jni.eyrie.amap.tbt.CustomTextureObserver;
import com.autonavi.jni.eyrie.amap.tbt.NaviEventReceiver;
import com.autonavi.jni.eyrie.amap.tbt.NaviManager;
import com.autonavi.jni.eyrie.amap.tbt.TextureLoader;
import com.autonavi.jni.eyrie.amap.tbt.TextureParam;
import com.autonavi.jni.eyrie.amap.tbt.TextureWrapper;
import com.autonavi.jni.eyrie.amap.tbt.scene.basemap.NaviBasemapMain;
import com.autonavi.map.core.IOverlayManager;
import com.autonavi.minimap.R;
import com.autonavi.sdk.location.LocationInstrument;
import com.autonavi.sync.beans.GirfFavoritePoint;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import org.json.JSONException;
import org.json.JSONObject;

public class FavoriteLayer implements xo {
    public static final String FROM_MAP_PAGE = "FromMapPage";
    public static int GET_FOCUS = 1;
    private static final String GROUP_NAME = "basemap.favorites";
    public static int LOSE_FOCUS = 0;
    public static final String POI_KEY = "poiKey";
    private static final String TAG = "FavoriteLayer";
    private boolean mBubbleEnable;
    private FavoriteItemClickListener mFavoriteItemClickListener;
    private FavoriteItemFocusChangeListener mFavoriteItemFocusChangeListener;
    private FavoriteItemsRefreshListener mFavoriteItemsRefreshListener;
    private NaviBasemapMain mFavoriteNaviBusiness;
    private FavoriteOverlayItem mFocusItem;
    private NaviEventReceiver mNaviEventReceiver = new NaviEventReceiver() {
        public void onNaviEvent(String str) {
            if (!TextUtils.isEmpty(str)) {
                try {
                    final JSONObject jSONObject = new JSONObject(str);
                    aho.a(new Runnable() {
                        public void run() {
                            if (jSONObject.optInt("eventType") == 9001) {
                                FavoriteLayer.this.dealFavoriteClick(jSONObject);
                                return;
                            }
                            if (jSONObject.optInt("eventType") == 9002) {
                                FavoriteLayer.this.dealFocusChange(jSONObject);
                            }
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    };
    private TextureLoader mTextureLoader = new TextureLoader() {
        public boolean loadCustomTextureData(int i, TextureParam textureParam, CustomTextureObserver customTextureObserver) {
            return false;
        }

        public boolean loadTextureData(int i, TextureParam textureParam, TextureWrapper textureWrapper) {
            int i2 = textureParam.resId;
            if (i2 != 210015) {
                switch (i2) {
                    case 210010:
                        textureWrapper.setData(2, FavoriteLayer.this.getBytesFromId(R.drawable.favorite_layer));
                        return true;
                    case 210011:
                        textureWrapper.setData(2, FavoriteLayer.this.getBytesFromId(R.drawable.commute_icon_home));
                        return true;
                    case 210012:
                        textureWrapper.setData(2, FavoriteLayer.this.getBytesFromId(R.drawable.commute_icon_company));
                        return true;
                    default:
                        return false;
                }
            } else {
                textureWrapper.setData(2, FavoriteLayer.this.getBytesFromId(R.drawable.b_poi_hl));
                return true;
            }
        }
    };

    public interface FavoriteItemClickListener {
        void onFavoriteItemClick(FavoriteOverlayItem favoriteOverlayItem);
    }

    public interface FavoriteItemFocusChangeListener {
        void onFavoriteFocusChange(int i, FavoriteOverlayItem favoriteOverlayItem);
    }

    public interface FavoriteItemsRefreshListener {
        void onFavoriteItemsRefresh();
    }

    public void setMoveToFocus(boolean z) {
    }

    public FavoriteLayer() {
        MapViewManager.addTextureLoader(this.mTextureLoader, 80);
        xp xpVar = (xp) a.a.a(xp.class);
        if (xpVar != null) {
            this.mFavoriteNaviBusiness = xpVar.d();
        }
        initEvent();
        setVisible(true);
    }

    private void initEvent() {
        NaviManager.registerEventReceiver(this.mNaviEventReceiver);
    }

    /* access modifiers changed from: private */
    public void dealFocusChange(@NonNull JSONObject jSONObject) {
        int optInt = jSONObject.optInt("hasFocus", -1);
        if (optInt == LOSE_FOCUS) {
            this.mFocusItem = null;
            if (this.mFavoriteItemFocusChangeListener != null) {
                this.mFavoriteItemFocusChangeListener.onFavoriteFocusChange(LOSE_FOCUS, null);
            }
        } else if (optInt == GET_FOCUS) {
            this.mFocusItem = getFavoriteLayerItem(jSONObject.optString("favoritePoiInfo"));
            if (this.mFavoriteItemFocusChangeListener != null) {
                this.mFavoriteItemFocusChangeListener.onFavoriteFocusChange(GET_FOCUS, this.mFocusItem);
            }
        }
    }

    /* access modifiers changed from: private */
    public void dealFavoriteClick(@NonNull JSONObject jSONObject) {
        onFavoriteLayerClick(getFavoriteLayerItem(jSONObject.optString("favoritePoiInfo")));
    }

    private FavoriteOverlayItem getFavoriteLayerItem(String str) {
        try {
            JSONObject jSONObject = new JSONObject(str);
            String optString = jSONObject.optString("name");
            String optString2 = jSONObject.optString(GirfFavoritePoint.JSON_FIELD_POI_COMMON_NAME);
            String optString3 = jSONObject.optString(GirfFavoritePoint.JSON_FIELD_POI_CUSTOM_NAME);
            String str2 = !TextUtils.isEmpty(optString2) ? optString2 : optString;
            if ("家".equals(optString2) || "公司".equals(optString2) || TextUtils.isEmpty(optString3)) {
                optString3 = str2;
            }
            POI createPOI = POIFactory.createPOI(optString, new GeoPoint(jSONObject.optInt("p20X", 0), jSONObject.optInt("p20Y", 0)));
            createPOI.setCityCode(jSONObject.optString("city_code"));
            String optString4 = jSONObject.optString("address");
            if (!TextUtils.isEmpty(optString4)) {
                optString = optString4;
            }
            createPOI.setAddr(optString);
            createPOI.getPoiExtra().put("titleName", optString3);
            createPOI.getPoiExtra().put("pointType", Integer.valueOf(Integer.parseInt(jSONObject.optString("poi_type", "0"))));
            String optString5 = jSONObject.optString("item_id");
            String optString6 = jSONObject.optString("json");
            if (TextUtils.isEmpty(optString6)) {
                return null;
            }
            bth a = bsr.a(optString6, optString5, jSONObject.optString(Oauth2AccessToken.KEY_UID));
            if (a == null) {
                return null;
            }
            createPOI.getPoiExtra().put(POI_KEY, optString5);
            if (FROM_MAP_PAGE.equals(jSONObject.optString(GirfFavoritePoint.JSON_FIELD_POI_NEW_TYPE))) {
                createPOI.setId(jSONObject.optString(LocationInstrument.LOCATION_EXTRAS_KEY_POIID, a.e));
                createPOI.getPoiExtra().put(IOverlayManager.POI_EXTRA_FROM_FAV, Boolean.TRUE);
            } else {
                createPOI.setId(a.e);
            }
            createPOI.setPid(a.e);
            a.a(createPOI);
            return new FavoriteOverlayItem(a);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    /* access modifiers changed from: private */
    public byte[] getBytesFromId(int i) {
        try {
            Bitmap decodeResource = BitmapFactory.decodeResource(AMapAppGlobal.getApplication().getResources(), i);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            decodeResource.compress(CompressFormat.PNG, 100, byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        } catch (Throwable th) {
            th.printStackTrace();
            return null;
        }
    }

    public void setVisible(boolean z) {
        AMapLog.debug(GROUP_NAME, TAG, "setVisible:".concat(String.valueOf(z)));
        if (z) {
            this.mFavoriteNaviBusiness.sendCommand(102110);
        } else {
            this.mFavoriteNaviBusiness.sendCommand(102111);
        }
    }

    public void setClickable(boolean z) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("enable", z ? 1 : 0);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        this.mFavoriteNaviBusiness.sendCommand(102113, jSONObject.toString());
    }

    public void clearFocus() {
        AMapLog.debug(GROUP_NAME, TAG, "clearFocus");
        this.mFavoriteNaviBusiness.sendCommand(102141);
    }

    public void setBubbleEnable(boolean z) {
        this.mBubbleEnable = z;
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("enable", z ? 1 : 0);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        this.mFavoriteNaviBusiness.sendCommand(102114, jSONObject.toString());
    }

    public void setFavoriteItemClickListener(FavoriteItemClickListener favoriteItemClickListener) {
        this.mFavoriteItemClickListener = favoriteItemClickListener;
    }

    public void setFavoriteItemFocusChangeListener(FavoriteItemFocusChangeListener favoriteItemFocusChangeListener) {
        this.mFavoriteItemFocusChangeListener = favoriteItemFocusChangeListener;
    }

    public void setFavoriteItemsRefreshListener(FavoriteItemsRefreshListener favoriteItemsRefreshListener) {
        this.mFavoriteItemsRefreshListener = favoriteItemsRefreshListener;
    }

    public void setHomeAndCompanyVisible(boolean z) {
        AMapLog.debug(GROUP_NAME, TAG, "setHomeAndCompanyVisible:".concat(String.valueOf(z)));
        if (z) {
            this.mFavoriteNaviBusiness.sendCommand(102130);
        } else {
            this.mFavoriteNaviBusiness.sendCommand(102131);
        }
    }

    public void setFavoriteItemVisible(boolean z) {
        AMapLog.debug(GROUP_NAME, TAG, "setFavoriteItemVisible:".concat(String.valueOf(z)));
        if (z) {
            this.mFavoriteNaviBusiness.sendCommand(102120);
        } else {
            this.mFavoriteNaviBusiness.sendCommand(102121);
        }
    }

    public void refreshAll() {
        AMapLog.debug(GROUP_NAME, TAG, "refreshAll");
        this.mFavoriteNaviBusiness.sendCommand(102112);
        if (this.mFavoriteItemsRefreshListener != null) {
            this.mFavoriteItemsRefreshListener.onFavoriteItemsRefresh();
        }
    }

    public void refreshHomeAndCompany() {
        AMapLog.debug(GROUP_NAME, TAG, "refreshHomeAndCompany");
        this.mFavoriteNaviBusiness.sendCommand(102132);
    }

    public void refreshFavorite() {
        AMapLog.debug(GROUP_NAME, TAG, "refreshFavorite");
        this.mFavoriteNaviBusiness.sendCommand(102122);
        if (this.mFavoriteItemsRefreshListener != null) {
            this.mFavoriteItemsRefreshListener.onFavoriteItemsRefresh();
        }
    }

    public void refreshHome() {
        AMapLog.debug(GROUP_NAME, TAG, "refreshFavorite");
        this.mFavoriteNaviBusiness.sendCommand(102135);
    }

    public void refreshCompany() {
        AMapLog.debug(GROUP_NAME, TAG, "refreshCompany");
        this.mFavoriteNaviBusiness.sendCommand(102136);
    }

    public void setDataProvider(long j) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("pointer", j);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        this.mFavoriteNaviBusiness.sendCommand(102100, jSONObject.toString());
    }

    public FavoriteOverlayItem getFocusItem() {
        return this.mFocusItem;
    }

    public void destroy() {
        MapViewManager.removeTextureLoader(this.mTextureLoader);
        NaviManager.unregisterEventReceiver(this.mNaviEventReceiver);
    }

    public void onFavoriteLayerClick(FavoriteOverlayItem favoriteOverlayItem) {
        if (favoriteOverlayItem != null && this.mFavoriteItemClickListener != null) {
            if (this.mBubbleEnable) {
                setFocus(favoriteOverlayItem);
            }
            this.mFavoriteItemClickListener.onFavoriteItemClick(favoriteOverlayItem);
        }
    }

    public void setFocus(FavoriteOverlayItem favoriteOverlayItem) {
        if (favoriteOverlayItem != null && favoriteOverlayItem.getSavePoint() != null && favoriteOverlayItem.getSavePoint().a() != null) {
            Serializable serializable = favoriteOverlayItem.getSavePoint().a().getPoiExtra().get(POI_KEY);
            if (serializable instanceof String) {
                JSONObject jSONObject = new JSONObject();
                try {
                    jSONObject.put("poi", serializable.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                AMapLog.debug(GROUP_NAME, TAG, "setFocus:".concat(String.valueOf(favoriteOverlayItem)));
                this.mFavoriteNaviBusiness.sendCommand(102140, jSONObject.toString());
            }
        }
    }
}
