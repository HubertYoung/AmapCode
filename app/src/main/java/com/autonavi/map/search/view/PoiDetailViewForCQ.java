package com.autonavi.map.search.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout.LayoutParams;
import android.widget.TextView;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.statistics.LogManager;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.common.model.POI;
import com.autonavi.minimap.R;
import com.autonavi.minimap.basemap.traffic.TrafficUtil;
import com.autonavi.minimap.map.mapinterface.AbstractPoiDetailView;
import com.autonavi.minimap.map.mapinterface.AbstractPoiDetailView.Event;
import com.autonavi.sdk.log.util.LogConstant;
import java.util.regex.Pattern;
import org.json.JSONException;
import org.json.JSONObject;

public class PoiDetailViewForCQ extends AbstractPoiDetailView implements OnClickListener, cbs {
    public static final int EVENT_CLICK_ADD_NEW = 7;
    public static final int EVENT_CLICK_DETAIl = 0;
    public static final int EVENT_CLICK_NAVI = 3;
    public static final int EVENT_CLICK_REPORT_ERROR = 6;
    public static final int EVENT_CLICK_ROUTE = 2;
    public static final int EVENT_CLICK_SEARCH_ARROUND = 1;
    public static final int EVENT_CLICK_TAXI = 5;
    public static final int EVENT_CLICK_TEL = 8;
    private Drawable btn_add_poi;
    private Drawable btn_dingpiao_normal;
    private Drawable btn_hotel_normel;
    private Drawable btn_indoor_disable;
    private Drawable btn_indoor_normal;
    private Drawable btn_movie_normel;
    private Drawable btn_tel;
    private Drawable btn_waimai_normal;
    private String cate;
    private SparseArray<b> events = new SparseArray<>();
    private boolean isPoiChildMark;
    private Context mContext;
    private boolean mEnableLandStyle = false;
    private LayoutInflater mInflater;
    private a mListener;
    private defpackage.ely.a mPoiListener;
    private Boolean mShowTipClose = null;
    private OnClickListener mTipCloseClickListener = new OnClickListener() {
        public final void onClick(View view) {
            if (PoiDetailViewForCQ.this.mTipViewListener != null) {
                PoiDetailViewForCQ.this.mTipViewListener;
            }
        }
    };
    /* access modifiers changed from: private */
    public defpackage.cbs.a mTipViewListener = null;
    private POI poi;
    private View rootView;
    private int stationFlag = 0;
    private TextView tvMainTitle;

    public interface a {
    }

    class b {
        int a;
        Event b;

        private b() {
        }

        /* synthetic */ b(PoiDetailViewForCQ poiDetailViewForCQ, byte b2) {
            this();
        }
    }

    private void refreshTipClose() {
    }

    public void setDeepinfo(String str) {
    }

    public void setDistance(String str) {
    }

    public void showDivider(boolean z) {
    }

    public PoiDetailViewForCQ(Context context) {
        super(context);
        init();
    }

    public PoiDetailViewForCQ(Context context, boolean z) {
        super(context);
        this.mEnableLandStyle = z;
        init();
    }

    public void setIsPoiChildMark(boolean z) {
        this.isPoiChildMark = z;
    }

    public void setCate(String str) {
        this.cate = str;
    }

    public void setStationFlag(int i) {
        this.stationFlag = i;
    }

    private void init() {
        if (this.mContext == null) {
            this.mContext = AMapAppGlobal.getApplication().getApplicationContext();
        }
        if (this.mInflater == null) {
            this.mInflater = LayoutInflater.from(this.mContext);
        }
        this.rootView = this.mInflater.inflate(R.layout.v4_poi_detail_cq, this);
        this.tvMainTitle = (TextView) this.rootView.findViewById(R.id.text_name);
        setShowTipClose(false);
    }

    public void setTipItemEvent(defpackage.ely.a aVar) {
        this.mPoiListener = aVar;
    }

    public void setPoi(POI poi2) {
        if (poi2 != null) {
            this.poi = poi2;
        }
    }

    public POI getPoi() {
        return this.poi;
    }

    public void setMainTitle(String str) {
        this.tvMainTitle.setText(String.valueOf(str));
        if (!getResources().getString(R.string.is_getting_address_des).equals(str) && this.poi != null && this.poi.getName().equals(getResources().getString(R.string.map_point))) {
            this.poi.setName(str);
            this.poi = this.poi.clone();
        }
    }

    private boolean isNumeric(String str) {
        if (str == null || str.equals("") || str.equals("null")) {
            return false;
        }
        int length = str.length();
        do {
            length--;
            if (length < 0) {
                return true;
            }
        } while (Character.isDigit(str.charAt(length)));
        return false;
    }

    public void setViceTitle(String str) {
        if (str != null && "".equals(str)) {
            isSaveEnabled();
        }
    }

    public void onClick(View view) {
        if (!bnp.a()) {
            AMapLog.d("CLICKEVENT", "PoiDetailView");
        }
    }

    private void tipAddClickLog(boolean z, String str) {
        if (z) {
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put(TrafficUtil.POIID, this.poi.getId());
                jSONObject.put("text", this.cate);
                if (LogConstant.MAIN_XUANDIAN_SOU_ZHOU_BIAN.equals(str)) {
                    jSONObject.put("from", "1");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            LogManager.actionLogV2(LogConstant.SEARCH_RESULT_MAP, str, jSONObject);
        }
    }

    public void reset() {
        this.poi = null;
        this.stationFlag = 0;
    }

    public void adjustMargin() {
        LayoutParams layoutParams = (LayoutParams) this.rootView.getLayoutParams();
        if (layoutParams != null) {
            layoutParams.setMargins(0, 0, 0, 0);
            layoutParams.gravity = 1;
            this.rootView.setLayoutParams(layoutParams);
        }
    }

    public void refreshByScreenState(boolean z) {
        if (this.rootView.getLayoutParams() == null) {
            this.rootView.setLayoutParams(new ViewGroup.LayoutParams(-1, -2));
        }
    }

    public void bindEvent(int i, Event event) {
        if (event != null) {
            b bVar = this.events.get(i);
            if (bVar != null) {
                bVar.b = event;
                return;
            }
            b bVar2 = new b(this, 0);
            bVar2.b = event;
            bVar2.a = i;
            this.events.put(i, bVar2);
        }
    }

    private boolean isNumberFormat(String str) {
        return Pattern.compile("[0-9]+\\.?[0-9]*").matcher(str).matches() || Pattern.compile("[0-9]*").matcher(str).matches();
    }

    public void setEventListener(a aVar) {
        this.mListener = aVar;
    }

    public void setShowTipClose(boolean z) {
        if (this.mShowTipClose == null || this.mShowTipClose.booleanValue() != z) {
            this.mShowTipClose = Boolean.valueOf(z);
            refreshTipClose();
        }
    }

    public boolean getShowTipClose() {
        return this.mShowTipClose.booleanValue();
    }

    public void setTipViewListener(defpackage.cbs.a aVar) {
        this.mTipViewListener = aVar;
    }

    public defpackage.cbs.a getTipViewListener() {
        return this.mTipViewListener;
    }
}
