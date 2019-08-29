package com.autonavi.map.fragmentcontainer.page.mappage.TipsView;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import com.amap.bundle.cloudconfig.appinit.FunctionSupportConfiger;
import com.amap.bundle.datamodel.FavoritePOI;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.common.Callback;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.POI;
import com.autonavi.common.utils.Constant;
import com.autonavi.map.fragmentcontainer.GeocodePOI;
import com.autonavi.map.fragmentcontainer.GpsPOI;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.minimap.R;
import com.autonavi.minimap.basemap.errorback.inter.IErrorReportStarter;
import com.autonavi.minimap.map.mapinterface.AbstractPoiDetailView;
import com.autonavi.minimap.search.model.searchpoi.ISearchPoiData;
import com.autonavi.minimap.search.model.searchpoi.searchpoitype.ChildStationPoiData;
import com.autonavi.sdk.log.util.LogConstant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PoiDetailView extends AbstractPoiDetailView implements OnClickListener {
    public static final int EVENT_CLICK_ADD_NEW = 7;
    public static final int EVENT_CLICK_DETAIl = 0;
    public static final int EVENT_CLICK_NAVI = 3;
    public static final int EVENT_CLICK_REPORT_ERROR = 6;
    public static final int EVENT_CLICK_ROUTE = 2;
    public static final int EVENT_CLICK_SEARCH_ARROUND = 1;
    public static final int EVENT_CLICK_TAXI = 5;
    public static final int EVENT_CLICK_TEL = 8;
    private TextView averagecost;
    private Drawable btn_add_poi;
    private Drawable btn_dingpiao_normal;
    private Drawable btn_hotel_normel;
    private Drawable btn_indoor_disable;
    private Drawable btn_indoor_normal;
    private Drawable btn_movie_normel;
    private Drawable btn_tel;
    private Drawable btn_waimai_normal;
    private View divider;
    private SparseArray<Entry> events = new SparseArray<>();
    private GeoCodeChecker geoCodeChecker;
    private ImageView imgRoute;
    private ImageView logo;
    private Context mContext;
    private LayoutInflater mInflater;
    private DetailViewEventListener mListener;
    private a mPoiListener;
    private TextView more_station_tv;
    private POI poi;
    private ImageView[] poiIvs = new ImageView[3];
    private RatingBar rating;
    private View rootView;
    private View search_around;
    private View search_navi;
    private int stationFlag = 0;
    private LinearLayout super_address_tip;
    private TextView tvDeepinfo;
    private TextView tvDistance;
    private TextView tvLastText;
    private TextView tvMainTitle;
    private TextView tvViceTitle;
    private TextView txt_taxi;
    private View vReportWrongPosition;

    public interface DetailViewEventListener {
        void onNaviClick(POI poi);

        void onRoutClick(POI poi);
    }

    static class Entry {
        Event event;
        int eventType;

        private Entry() {
        }
    }

    public interface Event {
        void onExecute(int i, POI poi);
    }

    public void adjustMargin() {
    }

    public void refreshByScreenState(boolean z) {
    }

    public PoiDetailView(bid bid) {
        super(bid.getActivity());
        init(bid);
    }

    public void setStationFlag(int i) {
        this.stationFlag = i;
    }

    private void init(bid bid) {
        if (this.mContext == null) {
            this.mContext = AMapAppGlobal.getApplication().getApplicationContext();
        }
        if (this.mInflater == null) {
            this.mInflater = LayoutInflater.from(this.mContext);
        }
        this.rootView = this.mInflater.inflate(R.layout.v4_poi_detail, this);
        this.txt_taxi = (TextView) this.rootView.findViewById(R.id.tv_taxi);
        this.imgRoute = (ImageView) this.rootView.findViewById(R.id.route_btn);
        this.poiIvs[0] = (ImageView) this.rootView.findViewById(R.id.poi_iv_1);
        this.poiIvs[1] = (ImageView) this.rootView.findViewById(R.id.poi_iv_2);
        this.poiIvs[2] = (ImageView) this.rootView.findViewById(R.id.poi_iv_3);
        this.rating = (RatingBar) this.rootView.findViewById(R.id.rating_bar);
        this.averagecost = (TextView) this.rootView.findViewById(R.id.avgprice);
        this.logo = (ImageView) this.rootView.findViewById(R.id.logo);
        this.tvMainTitle = (TextView) this.rootView.findViewById(R.id.text_name);
        this.tvViceTitle = (TextView) this.rootView.findViewById(R.id.text_addr);
        this.more_station_tv = (TextView) this.rootView.findViewById(R.id.more_station_tv);
        this.super_address_tip = (LinearLayout) this.rootView.findViewById(R.id.super_address_tip);
        this.tvDeepinfo = (TextView) this.rootView.findViewById(R.id.deepinfo);
        this.tvDistance = (TextView) this.rootView.findViewById(R.id.distance);
        this.divider = this.rootView.findViewById(R.id.divider);
        this.vReportWrongPosition = this.rootView.findViewById(R.id.tv_error);
        this.vReportWrongPosition.setOnClickListener(this);
        this.search_around = this.rootView.findViewById(R.id.tv_fujin);
        this.search_navi = this.rootView.findViewById(R.id.tv_navi);
        this.tvLastText = (TextView) this.rootView.findViewById(R.id.tv_indoor);
        Resources resources = getResources();
        this.btn_hotel_normel = resources.getDrawable(R.drawable.search_result_hotel_new);
        this.btn_movie_normel = resources.getDrawable(R.drawable.search_result_movie_new);
        this.btn_waimai_normal = resources.getDrawable(R.drawable.search_result_waimai_new);
        this.btn_dingpiao_normal = resources.getDrawable(R.drawable.search_result_dingpiao_new);
        this.btn_indoor_normal = resources.getDrawable(R.drawable.mbox_icon_indoor_normal_new);
        this.btn_indoor_disable = resources.getDrawable(R.drawable.mbox_icon_indoor_disable_new);
        this.btn_add_poi = resources.getDrawable(R.drawable.funicon_add_tab_new);
        this.btn_tel = resources.getDrawable(R.drawable.tips_tel_new);
        if (this.btn_hotel_normel != null) {
            this.btn_hotel_normel.setBounds(0, 0, this.btn_hotel_normel.getMinimumWidth(), this.btn_hotel_normel.getMinimumHeight());
        }
        if (this.btn_movie_normel != null) {
            this.btn_movie_normel.setBounds(0, 0, this.btn_movie_normel.getMinimumWidth(), this.btn_movie_normel.getMinimumHeight());
        }
        if (this.btn_waimai_normal != null) {
            this.btn_waimai_normal.setBounds(0, 0, this.btn_waimai_normal.getMinimumWidth(), this.btn_waimai_normal.getMinimumHeight());
        }
        if (this.btn_dingpiao_normal != null) {
            this.btn_dingpiao_normal.setBounds(0, 0, this.btn_dingpiao_normal.getMinimumWidth(), this.btn_dingpiao_normal.getMinimumHeight());
        }
        if (this.btn_indoor_disable != null) {
            this.btn_indoor_disable.setBounds(0, 0, this.btn_indoor_disable.getMinimumWidth(), this.btn_indoor_disable.getMinimumHeight());
        }
        if (this.btn_indoor_normal != null) {
            this.btn_indoor_normal.setBounds(0, 0, this.btn_indoor_normal.getMinimumWidth(), this.btn_indoor_normal.getMinimumHeight());
        }
        if (this.btn_add_poi != null) {
            this.btn_add_poi.setBounds(0, 0, this.btn_add_poi.getMinimumWidth(), this.btn_add_poi.getMinimumHeight());
        }
        if (this.btn_tel != null) {
            this.btn_tel.setBounds(0, 0, this.btn_tel.getMinimumWidth(), this.btn_tel.getMinimumHeight());
        }
        registerListener();
        this.geoCodeChecker = new GeoCodeChecker();
        decorateDefault(this, bid);
    }

    private void registerListener() {
        this.search_around.setOnClickListener(this);
        this.txt_taxi.setOnClickListener(this);
        this.imgRoute.setOnClickListener(this);
        this.search_navi.setOnClickListener(this);
        this.tvLastText.setOnClickListener(this);
    }

    public void setTipItemEvent(a aVar) {
        this.mPoiListener = aVar;
    }

    /* JADX WARNING: Removed duplicated region for block: B:37:0x00e4 A[Catch:{ Exception -> 0x00f8 }] */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x0116  */
    /* JADX WARNING: Removed duplicated region for block: B:55:0x0139  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void setPoi(com.autonavi.common.model.POI r11) {
        /*
            r10 = this;
            if (r11 != 0) goto L_0x0003
            return
        L_0x0003:
            r10.poi = r11
            android.widget.ImageView[] r11 = r10.poiIvs
            int r0 = r11.length
            r1 = 0
            r2 = 0
        L_0x000a:
            r3 = 8
            if (r2 >= r0) goto L_0x0016
            r4 = r11[r2]
            r4.setVisibility(r3)
            int r2 = r2 + 1
            goto L_0x000a
        L_0x0016:
            com.autonavi.common.model.POI r11 = r10.poi
            java.util.HashMap r11 = r11.getPoiExtra()
            r0 = 1
            if (r11 == 0) goto L_0x0056
            com.autonavi.common.model.POI r11 = r10.poi
            java.util.HashMap r11 = r11.getPoiExtra()
            java.lang.String r2 = "group_flag"
            boolean r11 = r11.containsKey(r2)
            if (r11 == 0) goto L_0x0056
            com.autonavi.common.model.POI r11 = r10.poi
            java.util.HashMap r11 = r11.getPoiExtra()
            java.lang.String r2 = "group_flag"
            java.lang.Object r11 = r11.get(r2)
            java.lang.String r11 = (java.lang.String) r11
            java.lang.String r2 = "1"
            boolean r11 = r2.equals(r11)
            if (r11 == 0) goto L_0x0056
            android.widget.ImageView[] r11 = r10.poiIvs
            r11 = r11[r1]
            int r2 = com.autonavi.minimap.R.drawable.poi_group
            r11.setImageResource(r2)
            android.widget.ImageView[] r11 = r10.poiIvs
            r11 = r11[r1]
            r11.setVisibility(r1)
            r11 = 1
            r2 = 1
            goto L_0x0058
        L_0x0056:
            r11 = 0
            r2 = 0
        L_0x0058:
            r4 = 3
            if (r11 >= r4) goto L_0x0151
            com.autonavi.common.model.POI r5 = r10.poi
            java.util.HashMap r5 = r5.getPoiExtra()
            if (r5 == 0) goto L_0x0151
            com.autonavi.common.model.POI r5 = r10.poi
            java.util.HashMap r5 = r5.getPoiExtra()
            java.lang.String r6 = "cinema_isbook"
            java.lang.Object r5 = r5.get(r6)
            java.lang.String r5 = (java.lang.String) r5
            com.autonavi.common.model.POI r6 = r10.poi
            java.util.HashMap r6 = r6.getPoiExtra()
            java.lang.String r7 = "cinema_iscoupons"
            java.lang.Object r6 = r6.get(r7)
            java.lang.String r6 = (java.lang.String) r6
            com.autonavi.common.model.POI r7 = r10.poi     // Catch:{ Exception -> 0x00a6 }
            java.util.HashMap r7 = r7.getPoiExtra()     // Catch:{ Exception -> 0x00a6 }
            java.lang.String r8 = "cinema_number"
            java.lang.Object r7 = r7.get(r8)     // Catch:{ Exception -> 0x00a6 }
            java.lang.String r7 = (java.lang.String) r7     // Catch:{ Exception -> 0x00a6 }
            boolean r7 = r10.isNumeric(r7)     // Catch:{ Exception -> 0x00a6 }
            if (r7 == 0) goto L_0x00aa
            com.autonavi.common.model.POI r7 = r10.poi     // Catch:{ Exception -> 0x00a6 }
            java.util.HashMap r7 = r7.getPoiExtra()     // Catch:{ Exception -> 0x00a6 }
            java.lang.String r8 = "cinema_number"
            java.lang.Object r7 = r7.get(r8)     // Catch:{ Exception -> 0x00a6 }
            java.lang.String r7 = (java.lang.String) r7     // Catch:{ Exception -> 0x00a6 }
            int r7 = java.lang.Integer.parseInt(r7)     // Catch:{ Exception -> 0x00a6 }
            goto L_0x00ab
        L_0x00a6:
            r7 = move-exception
            defpackage.kf.a(r7)
        L_0x00aa:
            r7 = 0
        L_0x00ab:
            if (r7 <= 0) goto L_0x00bf
            java.lang.String r7 = "1"
            boolean r5 = r7.equals(r5)
            if (r5 != 0) goto L_0x00bd
            java.lang.String r5 = "1"
            boolean r5 = r5.equals(r6)
            if (r5 == 0) goto L_0x00bf
        L_0x00bd:
            r5 = 1
            goto L_0x00c0
        L_0x00bf:
            r5 = 0
        L_0x00c0:
            com.autonavi.common.model.POI r6 = r10.poi
            java.util.HashMap r6 = r6.getPoiExtra()
            java.lang.String r7 = "theater_isbook"
            java.lang.Object r6 = r6.get(r7)
            java.lang.String r6 = (java.lang.String) r6
            com.autonavi.common.model.POI r7 = r10.poi     // Catch:{ Exception -> 0x00f8 }
            java.util.HashMap r7 = r7.getPoiExtra()     // Catch:{ Exception -> 0x00f8 }
            java.lang.String r8 = "theater_number"
            java.lang.Object r7 = r7.get(r8)     // Catch:{ Exception -> 0x00f8 }
            java.lang.String r7 = (java.lang.String) r7     // Catch:{ Exception -> 0x00f8 }
            boolean r7 = r10.isNumeric(r7)     // Catch:{ Exception -> 0x00f8 }
            if (r7 == 0) goto L_0x00fc
            com.autonavi.common.model.POI r7 = r10.poi     // Catch:{ Exception -> 0x00f8 }
            java.util.HashMap r7 = r7.getPoiExtra()     // Catch:{ Exception -> 0x00f8 }
            java.lang.String r8 = "theater_number"
            java.lang.Object r7 = r7.get(r8)     // Catch:{ Exception -> 0x00f8 }
            java.lang.String r7 = (java.lang.String) r7     // Catch:{ Exception -> 0x00f8 }
            int r7 = java.lang.Integer.parseInt(r7)     // Catch:{ Exception -> 0x00f8 }
            goto L_0x00fd
        L_0x00f8:
            r7 = move-exception
            defpackage.kf.a(r7)
        L_0x00fc:
            r7 = 0
        L_0x00fd:
            if (r7 <= 0) goto L_0x0108
            java.lang.String r7 = "1"
            boolean r6 = r7.equals(r6)
            if (r6 == 0) goto L_0x0108
            r5 = 1
        L_0x0108:
            com.autonavi.common.model.POI r6 = r10.poi
            java.lang.String r6 = r6.getType()
            com.autonavi.common.model.POI r7 = r10.poi
            java.lang.String r7 = r7.getType()
            if (r7 == 0) goto L_0x0139
            java.lang.String r7 = "080601"
            boolean r7 = r6.contains(r7)
            int r8 = r6.length()
            r9 = 4
            if (r8 < r9) goto L_0x0127
            java.lang.String r6 = r6.substring(r1, r9)
        L_0x0127:
            java.lang.String r8 = "1001"
            boolean r8 = r8.equals(r6)
            if (r8 != 0) goto L_0x0137
            java.lang.String r8 = "1002"
            boolean r6 = r8.equals(r6)
            if (r6 == 0) goto L_0x013a
        L_0x0137:
            r7 = 1
            goto L_0x013a
        L_0x0139:
            r7 = 0
        L_0x013a:
            if (r7 != 0) goto L_0x0151
            if (r5 == 0) goto L_0x0151
            android.widget.ImageView[] r2 = r10.poiIvs
            r2 = r2[r11]
            int r5 = com.autonavi.minimap.R.drawable.poi_booking
            r2.setImageResource(r5)
            android.widget.ImageView[] r2 = r10.poiIvs
            r2 = r2[r11]
            r2.setVisibility(r1)
            int r11 = r11 + 1
            r2 = 1
        L_0x0151:
            if (r11 >= r4) goto L_0x0192
            com.autonavi.common.model.POI r5 = r10.poi
            java.util.HashMap r5 = r5.getPoiExtra()
            if (r5 == 0) goto L_0x0192
            com.autonavi.common.model.POI r5 = r10.poi
            java.util.HashMap r5 = r5.getPoiExtra()
            java.lang.String r6 = "discount_flag"
            boolean r5 = r5.containsKey(r6)
            if (r5 == 0) goto L_0x0192
            com.autonavi.common.model.POI r5 = r10.poi
            java.util.HashMap r5 = r5.getPoiExtra()
            java.lang.String r6 = "discount_flag"
            java.lang.Object r5 = r5.get(r6)
            java.lang.String r5 = (java.lang.String) r5
            java.lang.String r6 = "1"
            boolean r5 = r6.equals(r5)
            if (r5 == 0) goto L_0x0192
            android.widget.ImageView[] r2 = r10.poiIvs
            r2 = r2[r11]
            int r5 = com.autonavi.minimap.R.drawable.poi_favorable
            r2.setImageResource(r5)
            android.widget.ImageView[] r2 = r10.poiIvs
            r2 = r2[r11]
            r2.setVisibility(r1)
            int r11 = r11 + 1
            r2 = 1
        L_0x0192:
            if (r11 >= r4) goto L_0x01d3
            com.autonavi.common.model.POI r5 = r10.poi
            java.util.HashMap r5 = r5.getPoiExtra()
            if (r5 == 0) goto L_0x01d3
            com.autonavi.common.model.POI r5 = r10.poi
            java.util.HashMap r5 = r5.getPoiExtra()
            java.lang.String r6 = "cinemaquan_flag"
            boolean r5 = r5.containsKey(r6)
            if (r5 == 0) goto L_0x01d3
            com.autonavi.common.model.POI r5 = r10.poi
            java.util.HashMap r5 = r5.getPoiExtra()
            java.lang.String r6 = "cinemaquan_flag"
            java.lang.Object r5 = r5.get(r6)
            java.lang.String r5 = (java.lang.String) r5
            java.lang.String r6 = "1"
            boolean r5 = r6.equals(r5)
            if (r5 == 0) goto L_0x01d3
            android.widget.ImageView[] r2 = r10.poiIvs
            r2 = r2[r11]
            int r5 = com.autonavi.minimap.R.drawable.poi_ticket
            r2.setImageResource(r5)
            android.widget.ImageView[] r2 = r10.poiIvs
            r2 = r2[r11]
            r2.setVisibility(r1)
            int r11 = r11 + 1
            r2 = 1
        L_0x01d3:
            com.autonavi.common.model.POI r5 = r10.poi
            java.util.HashMap r5 = r5.getPoiExtra()
            if (r5 == 0) goto L_0x021d
            com.autonavi.common.model.POI r5 = r10.poi
            java.util.HashMap r5 = r5.getPoiExtra()
            java.lang.String r6 = "discount_short_name"
            java.lang.Object r5 = r5.get(r6)
            java.lang.String r5 = (java.lang.String) r5
            boolean r5 = android.text.TextUtils.isEmpty(r5)
            if (r5 != 0) goto L_0x021d
            java.lang.String r5 = "ali_activity_o11"
            com.autonavi.common.model.POI r6 = r10.poi
            java.util.HashMap r6 = r6.getPoiExtra()
            java.lang.String r7 = "discount_srctype"
            java.lang.Object r6 = r6.get(r7)
            boolean r5 = r5.equals(r6)
            if (r5 == 0) goto L_0x021d
            android.widget.ImageView r5 = r10.logo
            r5.setVisibility(r1)
            android.widget.TextView r5 = r10.tvViceTitle
            com.autonavi.common.model.POI r6 = r10.poi
            java.util.HashMap r6 = r6.getPoiExtra()
            java.lang.String r7 = "discount_short_name"
            java.lang.Object r6 = r6.get(r7)
            java.lang.String r6 = (java.lang.String) r6
            r5.setText(r6)
            goto L_0x033b
        L_0x021d:
            com.autonavi.common.model.POI r5 = r10.poi
            java.util.HashMap r5 = r5.getPoiExtra()
            if (r5 == 0) goto L_0x0285
            com.autonavi.common.model.POI r5 = r10.poi
            java.util.HashMap r5 = r5.getPoiExtra()
            java.lang.String r6 = "rating"
            boolean r5 = r5.containsKey(r6)
            if (r5 == 0) goto L_0x0285
            com.autonavi.common.model.POI r5 = r10.poi
            java.util.HashMap r5 = r5.getPoiExtra()
            java.lang.String r6 = "rating"
            java.lang.Object r5 = r5.get(r6)
            java.lang.String r5 = (java.lang.String) r5
            r6 = -1
            if (r5 == 0) goto L_0x027f
            java.lang.String r7 = ""
            boolean r7 = r7.equals(r5)
            if (r7 != 0) goto L_0x027f
            java.lang.String r7 = "None"
            boolean r7 = r7.equals(r5)
            if (r7 != 0) goto L_0x027f
            boolean r7 = r10.isNumeric(r5)
            if (r7 == 0) goto L_0x0267
            java.lang.Float r5 = java.lang.Float.valueOf(r5)
            float r5 = r5.floatValue()
            r6 = 1092616192(0x41200000, float:10.0)
            float r5 = r5 * r6
            int r6 = (int) r5
        L_0x0267:
            if (r6 <= 0) goto L_0x0279
            android.widget.TextView r5 = r10.tvViceTitle
            r5.setVisibility(r3)
            android.widget.RatingBar r5 = r10.rating
            r5.setProgress(r6)
            android.widget.RatingBar r5 = r10.rating
            r5.setVisibility(r1)
            goto L_0x028a
        L_0x0279:
            android.widget.RatingBar r5 = r10.rating
            r5.setVisibility(r3)
            goto L_0x028a
        L_0x027f:
            android.widget.RatingBar r5 = r10.rating
            r5.setVisibility(r3)
            goto L_0x028a
        L_0x0285:
            android.widget.RatingBar r5 = r10.rating
            r5.setVisibility(r3)
        L_0x028a:
            com.autonavi.common.model.POI r5 = r10.poi
            java.util.HashMap r5 = r5.getPoiExtra()
            if (r5 == 0) goto L_0x0336
            com.autonavi.common.model.POI r5 = r10.poi
            java.util.HashMap r5 = r5.getPoiExtra()
            java.lang.String r6 = "averagecost"
            boolean r5 = r5.containsKey(r6)
            if (r5 == 0) goto L_0x0336
            com.autonavi.common.model.POI r5 = r10.poi
            java.util.HashMap r5 = r5.getPoiExtra()
            java.lang.String r6 = "averagecost"
            java.lang.Object r5 = r5.get(r6)
            java.lang.String r5 = (java.lang.String) r5
            r6 = 0
            java.lang.Float r7 = java.lang.Float.valueOf(r6)
            if (r5 == 0) goto L_0x02c0
            int r8 = r5.length()     // Catch:{ Exception -> 0x02c0 }
            if (r8 <= 0) goto L_0x02c0
            java.lang.Float r8 = java.lang.Float.valueOf(r5)     // Catch:{ Exception -> 0x02c0 }
            r7 = r8
        L_0x02c0:
            if (r5 == 0) goto L_0x0330
            java.lang.String r8 = ""
            boolean r8 = r8.equals(r5)
            if (r8 != 0) goto L_0x0330
            float r7 = r7.floatValue()
            int r6 = (r7 > r6 ? 1 : (r7 == r6 ? 0 : -1))
            if (r6 <= 0) goto L_0x0330
            android.content.res.Resources r6 = r10.getResources()
            int r7 = com.autonavi.minimap.R.string.average_cost
            java.lang.String r6 = r6.getString(r7)
            com.autonavi.common.model.POI r7 = r10.poi
            java.util.HashMap r7 = r7.getPoiExtra()
            java.lang.String r8 = "averagecostname"
            boolean r7 = r7.containsKey(r8)
            if (r7 == 0) goto L_0x030c
            com.autonavi.common.model.POI r7 = r10.poi
            java.util.HashMap r7 = r7.getPoiExtra()
            java.lang.String r8 = "averagecostname"
            java.lang.Object r7 = r7.get(r8)
            java.lang.String r7 = (java.lang.String) r7
            boolean r7 = android.text.TextUtils.isEmpty(r7)
            if (r7 != 0) goto L_0x030c
            com.autonavi.common.model.POI r6 = r10.poi
            java.util.HashMap r6 = r6.getPoiExtra()
            java.lang.String r7 = "averagecostname"
            java.lang.Object r6 = r6.get(r7)
            java.lang.String r6 = (java.lang.String) r6
        L_0x030c:
            android.widget.TextView r7 = r10.averagecost
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            r8.append(r6)
            java.lang.String r6 = ":￥"
            r8.append(r6)
            r8.append(r5)
            java.lang.String r5 = r8.toString()
            r7.setText(r5)
            android.widget.TextView r5 = r10.averagecost
            r5.setVisibility(r1)
            android.widget.TextView r5 = r10.tvViceTitle
            r5.setVisibility(r3)
            goto L_0x033b
        L_0x0330:
            android.widget.TextView r5 = r10.averagecost
            r5.setVisibility(r3)
            goto L_0x033b
        L_0x0336:
            android.widget.TextView r5 = r10.averagecost
            r5.setVisibility(r3)
        L_0x033b:
            com.autonavi.common.model.POI r5 = r10.poi
            java.util.HashMap r5 = r5.getPoiExtra()
            if (r5 == 0) goto L_0x0402
            java.lang.String r5 = ""
            java.lang.String r6 = ""
            com.autonavi.common.model.POI r7 = r10.poi
            java.util.HashMap r7 = r7.getPoiExtra()
            java.lang.String r8 = "sc_level"
            java.lang.Object r7 = r7.get(r8)
            if (r7 == 0) goto L_0x0369
            com.autonavi.common.model.POI r5 = r10.poi
            java.util.HashMap r5 = r5.getPoiExtra()
            java.lang.String r7 = "sc_level"
            java.lang.Object r5 = r5.get(r7)
            java.io.Serializable r5 = (java.io.Serializable) r5
            java.lang.String r5 = r5.toString()
        L_0x0369:
            com.autonavi.common.model.POI r7 = r10.poi
            java.util.HashMap r7 = r7.getPoiExtra()
            java.lang.String r8 = "sc_price_lowest"
            java.lang.Object r7 = r7.get(r8)
            if (r7 == 0) goto L_0x038b
            com.autonavi.common.model.POI r6 = r10.poi
            java.util.HashMap r6 = r6.getPoiExtra()
            java.lang.String r7 = "sc_price_lowest"
            java.lang.Object r6 = r6.get(r7)
            java.io.Serializable r6 = (java.io.Serializable) r6
            java.lang.String r6 = r6.toString()
        L_0x038b:
            java.lang.String r7 = ""
            java.lang.String r8 = ""
            boolean r8 = r8.equals(r5)
            if (r8 != 0) goto L_0x03a4
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            r8.append(r7)
            r8.append(r5)
            java.lang.String r7 = r8.toString()
        L_0x03a4:
            java.lang.String r5 = ""
            boolean r5 = r5.equals(r6)
            if (r5 != 0) goto L_0x03e2
            java.lang.String r5 = ""
            boolean r5 = r5.equals(r7)
            if (r5 != 0) goto L_0x03c5
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            r5.append(r7)
            java.lang.String r7 = "&nbsp;&nbsp;"
            r5.append(r7)
            java.lang.String r7 = r5.toString()
        L_0x03c5:
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            r5.append(r7)
            android.content.res.Resources r7 = r10.getResources()
            int r8 = com.autonavi.minimap.R.string.admission_ticket_cost
            java.lang.Object[] r9 = new java.lang.Object[r0]
            r9[r1] = r6
            java.lang.String r6 = r7.getString(r8, r9)
            r5.append(r6)
            java.lang.String r7 = r5.toString()
        L_0x03e2:
            java.lang.String r5 = ""
            boolean r5 = r5.equals(r7)
            if (r5 != 0) goto L_0x0402
            android.widget.TextView r5 = r10.averagecost
            android.text.Spanned r6 = android.text.Html.fromHtml(r7)
            r5.setText(r6)
            android.widget.TextView r5 = r10.averagecost
            r5.setVisibility(r1)
            android.widget.RatingBar r5 = r10.rating
            r5.setVisibility(r3)
            android.widget.TextView r5 = r10.tvViceTitle
            r5.setVisibility(r3)
        L_0x0402:
            com.autonavi.common.model.POI r5 = r10.poi
            java.util.HashMap r5 = r5.getPoiExtra()
            if (r5 == 0) goto L_0x045e
            if (r11 >= r4) goto L_0x045e
            com.autonavi.common.model.POI r5 = r10.poi
            java.lang.String r5 = r10.getCmsInfo(r5)
            com.autonavi.common.model.POI r6 = r10.poi
            java.util.HashMap r6 = r6.getPoiExtra()
            java.lang.String r7 = "car_bitauto_flag"
            java.lang.Object r6 = r6.get(r7)
            java.lang.String r6 = (java.lang.String) r6
            com.autonavi.common.model.POI r7 = r10.poi
            java.util.HashMap r7 = r7.getPoiExtra()
            java.lang.String r8 = "gdsh_flag"
            java.lang.Object r7 = r7.get(r8)
            java.lang.String r7 = (java.lang.String) r7
            java.lang.String r8 = "1"
            boolean r6 = r8.equals(r6)
            if (r6 != 0) goto L_0x043e
            java.lang.String r6 = "1"
            boolean r6 = r6.equals(r7)
            if (r6 == 0) goto L_0x045e
        L_0x043e:
            java.lang.String r6 = ""
            boolean r6 = r6.equals(r5)
            if (r6 != 0) goto L_0x045e
            android.widget.TextView r6 = r10.tvViceTitle
            r6.setVisibility(r1)
            android.widget.TextView r6 = r10.tvViceTitle
            android.text.Spanned r5 = android.text.Html.fromHtml(r5)
            r6.setText(r5)
            android.widget.TextView r5 = r10.averagecost
            r5.setVisibility(r3)
            android.widget.RatingBar r5 = r10.rating
            r5.setVisibility(r3)
        L_0x045e:
            if (r11 >= r4) goto L_0x04a1
            com.autonavi.common.model.POI r4 = r10.poi
            java.util.HashMap r4 = r4.getPoiExtra()
            if (r4 == 0) goto L_0x04a1
            com.autonavi.common.model.POI r4 = r10.poi
            java.util.HashMap r4 = r4.getPoiExtra()
            java.lang.String r5 = "spec_diandian_diningflag"
            boolean r4 = r4.containsKey(r5)
            if (r4 == 0) goto L_0x04a1
            com.autonavi.common.model.POI r4 = r10.poi
            java.util.HashMap r4 = r4.getPoiExtra()
            java.lang.String r5 = "spec_diandian_diningflag"
            java.lang.Object r4 = r4.get(r5)
            java.lang.String r4 = (java.lang.String) r4
            java.lang.String r5 = "1"
            boolean r4 = r5.equals(r4)
            if (r4 == 0) goto L_0x04a1
            android.widget.ImageView[] r2 = r10.poiIvs
            r2 = r2[r11]
            int r4 = com.autonavi.minimap.R.drawable.poi_diandian
            r2.setImageResource(r4)
            android.widget.ImageView[] r2 = r10.poiIvs
            r2 = r2[r11]
            r2.setVisibility(r1)
            int r11 = r11 + 1
            goto L_0x04a2
        L_0x04a1:
            r0 = r2
        L_0x04a2:
            if (r0 == 0) goto L_0x04af
            android.view.View r0 = r10.rootView
            android.widget.ImageView[] r2 = r10.poiIvs
            r2 = r2[r1]
            android.widget.TextView r4 = r10.tvMainTitle
            r10.setTextViewMaxWidth(r11, r0, r2, r4)
        L_0x04af:
            com.autonavi.common.model.POI r11 = r10.poi
            r10.initLastBtn(r11)
            r10.updateRouteBtn()
            android.view.View r11 = r10.rootView
            int r0 = com.autonavi.minimap.R.id.child_station_ll
            android.view.View r11 = r11.findViewById(r0)
            android.view.View r0 = r10.rootView
            int r2 = com.autonavi.minimap.R.id.station_num
            android.view.View r0 = r0.findViewById(r2)
            android.widget.TextView r0 = (android.widget.TextView) r0
            com.autonavi.common.model.POI r2 = r10.poi
            java.lang.Class<com.autonavi.minimap.search.model.searchpoi.searchpoitype.ChildStationPoiData> r4 = com.autonavi.minimap.search.model.searchpoi.searchpoitype.ChildStationPoiData.class
            com.autonavi.common.model.POI r2 = r2.as(r4)
            com.autonavi.minimap.search.model.searchpoi.searchpoitype.ChildStationPoiData r2 = (com.autonavi.minimap.search.model.searchpoi.searchpoitype.ChildStationPoiData) r2
            java.lang.String r2 = r2.getBusinfoAlias()
            if (r2 == 0) goto L_0x04f5
            java.lang.String r4 = r2.toString()
            java.lang.String r5 = ""
            boolean r4 = r4.equals(r5)
            if (r4 != 0) goto L_0x04f5
            r11.setVisibility(r1)
            java.lang.String r1 = r2.toString()
            r0.setText(r1)
            android.widget.TextView r0 = r10.tvMainTitle
            r10.setNameTextMaxWidth(r0, r11)
            goto L_0x04f8
        L_0x04f5:
            r11.setVisibility(r3)
        L_0x04f8:
            com.autonavi.minimap.map.mapinterface.AbstractPoiDetailView$OnSetPoiListener r11 = r10.mOnSetPoiListener
            if (r11 == 0) goto L_0x0503
            com.autonavi.minimap.map.mapinterface.AbstractPoiDetailView$OnSetPoiListener r11 = r10.mOnSetPoiListener
            com.autonavi.common.model.POI r0 = r10.poi
            r11.onSetPoi(r0)
        L_0x0503:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.map.fragmentcontainer.page.mappage.TipsView.PoiDetailView.setPoi(com.autonavi.common.model.POI):void");
    }

    public POI getPoi() {
        return this.poi;
    }

    private void setTextViewMaxWidth(int i, View view, ImageView imageView, TextView textView) {
        if (view != null && imageView != null && textView != null) {
            textView.setMaxWidth((getContext().getResources().getDisplayMetrics().widthPixels - (i * (imageView.getDrawable().getIntrinsicWidth() + getResources().getDimensionPixelSize(R.dimen.default_margin_4A)))) - agn.a(getContext(), 106.0f));
        }
    }

    private void setNameTextMaxWidth(TextView textView, View view) {
        if (textView != null && view != null) {
            view.measure(0, 0);
            textView.setMaxWidth((getContext().getResources().getDisplayMetrics().widthPixels - view.getMeasuredWidth()) - agn.a(getContext(), 114.0f));
        }
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

    /* JADX WARNING: Code restructure failed: missing block: B:104:0x0282, code lost:
        if (android.text.TextUtils.isEmpty(r9) == false) goto L_0x0286;
     */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x0091  */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x00da  */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x010b  */
    /* JADX WARNING: Removed duplicated region for block: B:51:0x0139  */
    /* JADX WARNING: Removed duplicated region for block: B:61:0x0164  */
    /* JADX WARNING: Removed duplicated region for block: B:63:0x0179  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.lang.String getCmsInfo(com.autonavi.common.model.POI r9) {
        /*
            r8 = this;
            java.lang.String r0 = ""
            java.util.HashMap r1 = r9.getPoiExtra()
            if (r1 == 0) goto L_0x0287
            java.lang.String r2 = "sc_ticket_cp_num"
            java.lang.Object r2 = r1.get(r2)
            r3 = 0
            r4 = 1
            if (r2 == 0) goto L_0x005f
            java.lang.String r2 = "sc_ticket_cp_num"
            java.lang.Object r2 = r1.get(r2)
            java.io.Serializable r2 = (java.io.Serializable) r2
            java.lang.String r2 = r2.toString()
            java.lang.String r5 = ""
            boolean r2 = r2.equals(r5)
            if (r2 != 0) goto L_0x005f
            java.lang.String r2 = "sc_ticket_cp_num"
            java.lang.Object r2 = r1.get(r2)     // Catch:{ NumberFormatException -> 0x005b }
            java.io.Serializable r2 = (java.io.Serializable) r2     // Catch:{ NumberFormatException -> 0x005b }
            java.lang.String r2 = r2.toString()     // Catch:{ NumberFormatException -> 0x005b }
            int r2 = java.lang.Integer.parseInt(r2)     // Catch:{ NumberFormatException -> 0x005b }
            if (r2 != r4) goto L_0x0046
            android.content.res.Resources r2 = r8.getResources()     // Catch:{ NumberFormatException -> 0x005b }
            int r5 = com.autonavi.minimap.R.string.book_ticket_free     // Catch:{ NumberFormatException -> 0x005b }
            java.lang.String r2 = r2.getString(r5)     // Catch:{ NumberFormatException -> 0x005b }
            return r2
        L_0x0046:
            if (r2 <= r4) goto L_0x005f
            android.content.res.Resources r5 = r8.getResources()     // Catch:{ NumberFormatException -> 0x005b }
            int r6 = com.autonavi.minimap.R.string.book_some_tickets_free     // Catch:{ NumberFormatException -> 0x005b }
            java.lang.Object[] r7 = new java.lang.Object[r4]     // Catch:{ NumberFormatException -> 0x005b }
            java.lang.String r2 = java.lang.String.valueOf(r2)     // Catch:{ NumberFormatException -> 0x005b }
            r7[r3] = r2     // Catch:{ NumberFormatException -> 0x005b }
            java.lang.String r2 = r5.getString(r6, r7)     // Catch:{ NumberFormatException -> 0x005b }
            return r2
        L_0x005b:
            r2 = move-exception
            defpackage.kf.a(r2)
        L_0x005f:
            java.lang.String r2 = "cinema_isbook"
            java.lang.Object r2 = r1.get(r2)
            java.lang.String r2 = (java.lang.String) r2
            java.lang.String r5 = "cinema_iscoupons"
            java.lang.Object r5 = r1.get(r5)
            java.lang.String r5 = (java.lang.String) r5
            java.lang.String r6 = "cinema_number"
            java.lang.Object r6 = r1.get(r6)     // Catch:{ Exception -> 0x008a }
            java.lang.String r6 = (java.lang.String) r6     // Catch:{ Exception -> 0x008a }
            boolean r6 = r8.isNumeric(r6)     // Catch:{ Exception -> 0x008a }
            if (r6 == 0) goto L_0x008e
            java.lang.String r6 = "cinema_number"
            java.lang.Object r6 = r1.get(r6)     // Catch:{ Exception -> 0x008a }
            java.lang.String r6 = (java.lang.String) r6     // Catch:{ Exception -> 0x008a }
            int r6 = java.lang.Integer.parseInt(r6)     // Catch:{ Exception -> 0x008a }
            goto L_0x008f
        L_0x008a:
            r6 = move-exception
            defpackage.kf.a(r6)
        L_0x008e:
            r6 = 0
        L_0x008f:
            if (r6 <= 0) goto L_0x00da
            java.lang.String r9 = "0"
            boolean r9 = r9.equals(r2)
            if (r9 != 0) goto L_0x00ac
            android.content.res.Resources r9 = r8.getResources()
            int r0 = com.autonavi.minimap.R.string.some_film_on_show_book_ticket
            java.lang.Object[] r1 = new java.lang.Object[r4]
            java.lang.String r2 = java.lang.String.valueOf(r6)
            r1[r3] = r2
            java.lang.String r9 = r9.getString(r0, r1)
            return r9
        L_0x00ac:
            java.lang.String r9 = "0"
            boolean r9 = r9.equals(r5)
            if (r9 != 0) goto L_0x00c7
            android.content.res.Resources r9 = r8.getResources()
            int r0 = com.autonavi.minimap.R.string.some_film_on_show_certificate
            java.lang.Object[] r1 = new java.lang.Object[r4]
            java.lang.String r2 = java.lang.String.valueOf(r6)
            r1[r3] = r2
            java.lang.String r9 = r9.getString(r0, r1)
            return r9
        L_0x00c7:
            android.content.res.Resources r9 = r8.getResources()
            int r0 = com.autonavi.minimap.R.string.some_film_on_show_schedule
            java.lang.Object[] r1 = new java.lang.Object[r4]
            java.lang.String r2 = java.lang.String.valueOf(r6)
            r1[r3] = r2
            java.lang.String r9 = r9.getString(r0, r1)
            return r9
        L_0x00da:
            java.util.HashMap r9 = r9.getPoiExtra()
            java.lang.String r2 = "theater_isbook"
            java.lang.Object r9 = r9.get(r2)
            java.lang.String r9 = (java.lang.String) r9
            java.lang.String r2 = "theater_number"
            java.lang.Object r2 = r1.get(r2)     // Catch:{ Exception -> 0x0104 }
            java.lang.String r2 = (java.lang.String) r2     // Catch:{ Exception -> 0x0104 }
            boolean r2 = r8.isNumeric(r2)     // Catch:{ Exception -> 0x0104 }
            if (r2 == 0) goto L_0x0108
            java.lang.String r2 = "theater_number"
            java.lang.Object r2 = r1.get(r2)     // Catch:{ Exception -> 0x0104 }
            java.lang.String r2 = (java.lang.String) r2     // Catch:{ Exception -> 0x0104 }
            int r2 = java.lang.Integer.parseInt(r2)     // Catch:{ Exception -> 0x0104 }
            goto L_0x0109
        L_0x0104:
            r2 = move-exception
            defpackage.kf.a(r2)
        L_0x0108:
            r2 = 0
        L_0x0109:
            if (r2 <= 0) goto L_0x0139
            java.lang.String r0 = "1"
            boolean r9 = r0.equals(r9)
            if (r9 == 0) goto L_0x0126
            android.content.res.Resources r9 = r8.getResources()
            int r0 = com.autonavi.minimap.R.string.some_activity_nearby_book_ticket
            java.lang.Object[] r1 = new java.lang.Object[r4]
            java.lang.String r2 = java.lang.String.valueOf(r2)
            r1[r3] = r2
            java.lang.String r9 = r9.getString(r0, r1)
            return r9
        L_0x0126:
            android.content.res.Resources r9 = r8.getResources()
            int r0 = com.autonavi.minimap.R.string.some_activity_nearby
            java.lang.Object[] r1 = new java.lang.Object[r4]
            java.lang.String r2 = java.lang.String.valueOf(r2)
            r1[r3] = r2
            java.lang.String r9 = r9.getString(r0, r1)
            return r9
        L_0x0139:
            java.lang.String r9 = "car_brands"
            java.lang.Object r9 = r1.get(r9)
            java.lang.String r9 = (java.lang.String) r9
            java.lang.String r2 = "car_number"
            java.lang.Object r2 = r1.get(r2)     // Catch:{ Exception -> 0x015c }
            java.lang.String r2 = (java.lang.String) r2     // Catch:{ Exception -> 0x015c }
            boolean r2 = r8.isNumeric(r2)     // Catch:{ Exception -> 0x015c }
            if (r2 == 0) goto L_0x0160
            java.lang.String r2 = "car_number"
            java.lang.Object r2 = r1.get(r2)     // Catch:{ Exception -> 0x015c }
            java.lang.String r2 = (java.lang.String) r2     // Catch:{ Exception -> 0x015c }
            int r2 = java.lang.Integer.parseInt(r2)     // Catch:{ Exception -> 0x015c }
            goto L_0x0161
        L_0x015c:
            r2 = move-exception
            defpackage.kf.a(r2)
        L_0x0160:
            r2 = 0
        L_0x0161:
            r5 = 2
            if (r2 <= 0) goto L_0x0179
            android.content.res.Resources r0 = r8.getResources()
            int r1 = com.autonavi.minimap.R.string.hot_products_car
            java.lang.Object[] r5 = new java.lang.Object[r5]
            java.lang.String r2 = java.lang.String.valueOf(r2)
            r5[r3] = r2
            r5[r4] = r9
            java.lang.String r9 = r0.getString(r1, r5)
            return r9
        L_0x0179:
            java.lang.String r9 = "group_number"
            java.lang.Object r9 = r1.get(r9)
            java.lang.String r9 = (java.lang.String) r9
            if (r9 == 0) goto L_0x0190
            java.lang.String r2 = ""
            boolean r2 = r2.equals(r9)
            if (r2 != 0) goto L_0x0190
            int r9 = java.lang.Integer.parseInt(r9)
            goto L_0x0191
        L_0x0190:
            r9 = 0
        L_0x0191:
            java.lang.String r2 = "group_discount"
            java.lang.Object r2 = r1.get(r2)
            java.lang.String r2 = (java.lang.String) r2
            java.lang.String r6 = "group_price"
            java.lang.Object r6 = r1.get(r6)
            java.lang.String r6 = (java.lang.String) r6
            if (r9 != r4) goto L_0x01b4
            android.content.res.Resources r9 = r8.getResources()
            int r0 = com.autonavi.minimap.R.string.some_discount_and_cost
            java.lang.Object[] r1 = new java.lang.Object[r5]
            r1[r3] = r2
            r1[r4] = r6
            java.lang.String r9 = r9.getString(r0, r1)
            return r9
        L_0x01b4:
            if (r9 <= r4) goto L_0x01cb
            android.content.res.Resources r0 = r8.getResources()
            int r1 = com.autonavi.minimap.R.string.some_discount_and_groupby
            java.lang.Object[] r5 = new java.lang.Object[r5]
            r5[r3] = r2
            java.lang.String r9 = java.lang.String.valueOf(r9)
            r5[r4] = r9
            java.lang.String r9 = r0.getString(r1, r5)
            return r9
        L_0x01cb:
            java.lang.String r9 = "discount_number"
            java.lang.Object r9 = r1.get(r9)
            java.lang.String r9 = (java.lang.String) r9
            if (r9 == 0) goto L_0x01e2
            java.lang.String r2 = ""
            boolean r2 = r2.equals(r9)
            if (r2 != 0) goto L_0x01e2
            int r9 = java.lang.Integer.parseInt(r9)
            goto L_0x01e3
        L_0x01e2:
            r9 = 0
        L_0x01e3:
            java.lang.String r2 = "discount_title"
            java.lang.Object r2 = r1.get(r2)
            java.lang.String r2 = (java.lang.String) r2
            if (r9 != r4) goto L_0x01ee
            return r2
        L_0x01ee:
            if (r9 <= r4) goto L_0x0203
            android.content.res.Resources r0 = r8.getResources()
            int r1 = com.autonavi.minimap.R.string.some_favorable
            java.lang.Object[] r2 = new java.lang.Object[r4]
            java.lang.String r9 = java.lang.String.valueOf(r9)
            r2[r3] = r9
            java.lang.String r9 = r0.getString(r1, r2)
            return r9
        L_0x0203:
            java.lang.String r9 = "specialfood_category"
            java.lang.Object r9 = r1.get(r9)
            java.lang.String r9 = (java.lang.String) r9
            java.lang.String r2 = "specialfood_foodlist"
            java.lang.Object r2 = r1.get(r2)
            java.lang.String r2 = (java.lang.String) r2
            if (r9 == 0) goto L_0x023d
            if (r2 == 0) goto L_0x023d
            java.lang.String r5 = ""
            boolean r5 = r5.equals(r9)
            if (r5 != 0) goto L_0x023d
            java.lang.String r5 = ""
            boolean r5 = r5.equals(r2)
            if (r5 != 0) goto L_0x023d
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            r0.append(r9)
            java.lang.String r9 = ":"
            r0.append(r9)
            r0.append(r2)
            java.lang.String r0 = r0.toString()
        L_0x023d:
            boolean r9 = android.text.TextUtils.isEmpty(r0)
            if (r9 == 0) goto L_0x0265
            java.lang.String r9 = "golf_lowestprice"
            java.lang.Object r9 = r1.get(r9)
            java.lang.String r9 = (java.lang.String) r9
            boolean r9 = android.text.TextUtils.isEmpty(r9)
            if (r9 != 0) goto L_0x0265
            android.content.res.Resources r9 = r8.getResources()
            int r0 = com.autonavi.minimap.R.string.book_golf_online
            java.lang.Object[] r2 = new java.lang.Object[r4]
            java.lang.String r4 = "golf_lowestprice"
            java.lang.Object r4 = r1.get(r4)
            r2[r3] = r4
            java.lang.String r0 = r9.getString(r0, r2)
        L_0x0265:
            java.lang.String r9 = "gdsh_flag"
            java.lang.Object r9 = r1.get(r9)
            java.lang.String r9 = (java.lang.String) r9
            java.lang.String r2 = "1"
            boolean r9 = r2.equals(r9)
            if (r9 == 0) goto L_0x0285
            java.lang.String r9 = "spec_gdsh_content"
            java.lang.Object r9 = r1.get(r9)
            java.lang.String r9 = (java.lang.String) r9
            boolean r1 = android.text.TextUtils.isEmpty(r9)
            if (r1 != 0) goto L_0x0285
            goto L_0x0286
        L_0x0285:
            r9 = r0
        L_0x0286:
            return r9
        L_0x0287:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.map.fragmentcontainer.page.mappage.TipsView.PoiDetailView.getCmsInfo(com.autonavi.common.model.POI):java.lang.String");
    }

    private void updateRouteBtn() {
        if (this.poi != null) {
            int adCode = this.poi.getPoint().getAdCode();
            boolean isSupport = adCode != 0 ? FunctionSupportConfiger.getInst().isSupport(String.valueOf(String.valueOf(adCode)), FunctionSupportConfiger.TAXI_TAG) : false;
            boolean isInstance = GpsPOI.class.isInstance(this.poi);
            if (isInstance) {
                this.vReportWrongPosition.setVisibility(0);
                this.search_navi.setVisibility(8);
            } else {
                this.vReportWrongPosition.setVisibility(8);
                this.search_navi.setVisibility(0);
            }
            if (!isInstance || !isSupport) {
                this.txt_taxi.setVisibility(8);
                return;
            }
            this.txt_taxi.setText(R.string.taxi);
            this.txt_taxi.setCompoundDrawablesWithIntrinsicBounds(R.drawable.bubble_search_result_texi, 0, 0, 0);
            this.txt_taxi.setVisibility(0);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:43:0x0115  */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x0122  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void setViceTitle(java.lang.String r12) {
        /*
            r11 = this;
            r0 = 8
            r1 = 0
            if (r12 == 0) goto L_0x00d7
            java.lang.String r2 = ""
            boolean r2 = r2.equals(r12)
            if (r2 == 0) goto L_0x0015
            boolean r2 = r11.isSaveEnabled()
            if (r2 == 0) goto L_0x0015
            goto L_0x00d7
        L_0x0015:
            android.widget.RatingBar r2 = r11.rating
            int r2 = r2.getVisibility()
            if (r2 != r0) goto L_0x00d6
            android.widget.TextView r2 = r11.averagecost
            int r2 = r2.getVisibility()
            if (r2 != r0) goto L_0x00d6
            int r2 = r11.stationFlag
            if (r2 != 0) goto L_0x0038
            android.widget.TextView r0 = r11.tvViceTitle
            r0.setVisibility(r1)
            android.widget.TextView r0 = r11.tvViceTitle
            java.lang.String r12 = java.lang.String.valueOf(r12)
            r0.setText(r12)
            return
        L_0x0038:
            r11.showDivider(r1)
            android.widget.TextView r2 = r11.tvViceTitle
            r2.setVisibility(r0)
            android.widget.LinearLayout r2 = r11.super_address_tip
            r2.setVisibility(r1)
            android.widget.LinearLayout r2 = r11.super_address_tip
            r2.removeAllViews()
            java.lang.String r2 = ";"
            java.lang.String[] r12 = r12.split(r2)
            android.widget.LinearLayout$LayoutParams r2 = new android.widget.LinearLayout$LayoutParams
            r3 = -2
            r2.<init>(r3, r3)
            r2.setMargins(r1, r1, r0, r1)
            android.content.Context r3 = r11.mContext
            android.graphics.Rect r3 = defpackage.ags.a(r3)
            int r3 = r3.width()
            android.widget.TextView r4 = r11.tvDistance
            r4.measure(r1, r1)
            r4 = 0
            r5 = 0
        L_0x006a:
            int r6 = r12.length
            if (r4 >= r6) goto L_0x00d6
            r6 = r12[r4]
            android.widget.TextView r7 = new android.widget.TextView
            android.content.Context r8 = r11.mContext
            r7.<init>(r8)
            r7.setText(r6)
            int r6 = com.autonavi.minimap.R.drawable.search_childstation_superaddress_common_bg
            r7.setBackgroundResource(r6)
            r6 = 1
            r7.setSingleLine(r6)
            java.lang.String r8 = "#42a5ff"
            int r8 = android.graphics.Color.parseColor(r8)
            r7.setTextColor(r8)
            r8 = 6
            r9 = 2
            r7.setPadding(r8, r9, r8, r9)
            r8 = 17
            r7.setGravity(r8)
            r8 = 1092616192(0x41200000, float:10.0)
            r7.setTextSize(r8)
            r7.measure(r1, r1)
            int r8 = r7.getMeasuredWidth()
            int r5 = r5 + r8
            int r8 = r3 - r5
            android.widget.TextView r10 = r11.tvDistance
            int r10 = r10.getMeasuredHeight()
            int r8 = r8 - r10
            int r8 = r8 + -12
            int r10 = r4 * 8
            int r8 = r8 - r10
            int r10 = r7.getMeasuredWidth()
            if (r8 < r10) goto L_0x00b8
            if (r8 > 0) goto L_0x00c9
        L_0x00b8:
            int r8 = r12.length
            if (r8 == r6) goto L_0x00c9
            android.widget.LinearLayout r6 = r11.super_address_tip
            int r6 = r6.getChildCount()
            if (r6 < r9) goto L_0x00c9
            android.widget.TextView r12 = r11.more_station_tv
            r12.setVisibility(r1)
            return
        L_0x00c9:
            android.widget.LinearLayout r6 = r11.super_address_tip
            r6.addView(r7, r2)
            android.widget.TextView r6 = r11.more_station_tv
            r6.setVisibility(r0)
            int r4 = r4 + 1
            goto L_0x006a
        L_0x00d6:
            return
        L_0x00d7:
            android.widget.RatingBar r12 = r11.rating
            int r12 = r12.getVisibility()
            if (r12 != r0) goto L_0x012a
            android.widget.TextView r12 = r11.averagecost
            int r12 = r12.getVisibility()
            if (r12 != r0) goto L_0x012a
            com.autonavi.common.model.POI r12 = r11.poi
            if (r12 == 0) goto L_0x012a
            com.autonavi.common.model.POI r12 = r11.poi
            if (r12 == 0) goto L_0x0112
            com.autonavi.common.model.POI r12 = r11.poi
            java.lang.Class<com.amap.bundle.datamodel.FavoritePOI> r0 = com.amap.bundle.datamodel.FavoritePOI.class
            com.autonavi.common.model.POI r12 = r12.as(r0)
            com.amap.bundle.datamodel.FavoritePOI r12 = (com.amap.bundle.datamodel.FavoritePOI) r12
            java.lang.Class<com> r0 = defpackage.com.class
            java.lang.Object r0 = defpackage.ank.a(r0)
            com r0 = (defpackage.com) r0
            if (r0 == 0) goto L_0x0112
            java.lang.String r2 = r0.a()
            cop r0 = r0.b(r2)
            if (r0 == 0) goto L_0x0112
            boolean r12 = r0.c(r12)
            goto L_0x0113
        L_0x0112:
            r12 = 0
        L_0x0113:
            if (r12 != 0) goto L_0x0122
            android.widget.TextView r12 = r11.tvViceTitle
            r12.setVisibility(r1)
            android.widget.TextView r12 = r11.tvViceTitle
            int r0 = com.autonavi.minimap.R.string.click_for_more
            r12.setText(r0)
            return
        L_0x0122:
            android.widget.TextView r12 = r11.tvViceTitle
            java.lang.String r0 = ""
            r12.setText(r0)
            return
        L_0x012a:
            android.widget.TextView r12 = r11.tvViceTitle
            r12.setVisibility(r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.map.fragmentcontainer.page.mappage.TipsView.PoiDetailView.setViceTitle(java.lang.String):void");
    }

    public void showDivider(boolean z) {
        if (this.divider != null) {
            this.divider.setVisibility(z ? 0 : 8);
        }
    }

    public void setDistance(String str) {
        String str2 = "";
        if (!this.poi.getType().equals("") && this.poi.getType().length() > 5) {
            str2 = this.poi.getType().substring(0, 4);
        }
        boolean z = !str2.equals("1507");
        if (this.tvDistance == null || TextUtils.isEmpty(str) || str.equals("-100") || !z) {
            if (this.tvDistance != null) {
                this.tvDistance.setVisibility(8);
            }
            return;
        }
        this.tvDistance.setVisibility(0);
        this.divider.setVisibility(0);
        TextView textView = this.tvDistance;
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append("米");
        textView.setText(sb.toString());
    }

    public void setDeepinfo(String str) {
        if (this.tvDeepinfo == null || TextUtils.isEmpty(str)) {
            if (this.tvDeepinfo != null) {
                this.tvDeepinfo.setVisibility(8);
            }
            return;
        }
        this.tvDeepinfo.setVisibility(0);
        this.tvDeepinfo.setText(Html.fromHtml(str));
    }

    public void onClick(View view) {
        if (!bnp.a()) {
            if (view == this.vReportWrongPosition) {
                Event event = getEvent(6);
                if (event != null) {
                    event.onExecute(6, this.poi);
                }
            } else if (view == this.search_around) {
                Event event2 = getEvent(1);
                if (this.mPoiListener != null) {
                    this.mPoiListener.c(this.poi);
                }
                if (event2 != null) {
                    event2.onExecute(1, this.poi);
                }
            } else if (view == this.txt_taxi) {
                Event event3 = getEvent(5);
                if (event3 != null) {
                    event3.onExecute(5, this.poi);
                }
            } else if (view == this.imgRoute) {
                Event event4 = getEvent(2);
                if (event4 != null) {
                    if (this.mPoiListener != null) {
                        this.mPoiListener.d(this.poi);
                    }
                    event4.onExecute(2, this.poi);
                    if (this.mListener != null) {
                        this.mListener.onRoutClick(this.poi);
                    }
                }
            } else if (view == this.search_navi) {
                Event event5 = getEvent(3);
                if (this.mPoiListener != null) {
                    this.mPoiListener.e(this.poi);
                }
                if (event5 != null) {
                    event5.onExecute(3, this.poi);
                }
                if (this.mListener != null) {
                    this.mListener.onNaviClick(this.poi);
                }
            } else {
                if (view == this.tvLastText && this.tvLastText.getTag() != null && !getResources().getString(R.string.indoor_map).equals(this.tvLastText.getTag().toString())) {
                    if (AMapAppGlobal.getApplication().getString(R.string.add_poi).equals(this.tvLastText.getTag().toString())) {
                        Event event6 = getEvent(7);
                        if (event6 != null) {
                            event6.onExecute(7, this.poi);
                        }
                    } else if (getResources().getString(R.string.tel).equals(this.tvLastText.getTag().toString())) {
                        Event event7 = getEvent(8);
                        if (event7 != null) {
                            event7.onExecute(8, this.poi);
                        }
                    } else {
                        Event event8 = getEvent(0);
                        if (event8 != null) {
                            if (this.mPoiListener != null) {
                                this.mPoiListener.b(this.poi);
                            }
                            event8.onExecute(0, this.poi);
                        }
                    }
                }
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:62:0x0212  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void initLastBtn(com.autonavi.common.model.POI r8) {
        /*
            r7 = this;
            android.widget.TextView r0 = r7.tvLastText
            r1 = 8
            r0.setVisibility(r1)
            if (r8 != 0) goto L_0x000a
            return
        L_0x000a:
            java.util.HashMap r0 = r8.getPoiExtra()
            java.lang.String r1 = "SrcType"
            boolean r0 = r0.containsKey(r1)
            r1 = 0
            r2 = 0
            if (r0 == 0) goto L_0x005c
            java.util.HashMap r0 = r8.getPoiExtra()
            java.lang.String r3 = "SrcType"
            java.lang.Object r0 = r0.get(r3)
            java.lang.String r0 = (java.lang.String) r0
            boolean r3 = android.text.TextUtils.isEmpty(r0)
            if (r3 != 0) goto L_0x005c
            java.lang.String r3 = "nativepoi"
            boolean r0 = r3.equals(r0)
            if (r0 == 0) goto L_0x005c
            java.lang.String r0 = r8.getPhone()
            boolean r0 = android.text.TextUtils.isEmpty(r0)
            if (r0 != 0) goto L_0x005c
            android.content.res.Resources r0 = r7.getResources()
            int r3 = com.autonavi.minimap.R.string.tel
            java.lang.String r0 = r0.getString(r3)
            android.widget.TextView r3 = r7.tvLastText
            r3.setText(r0)
            android.widget.TextView r3 = r7.tvLastText
            r3.setTag(r0)
            android.widget.TextView r0 = r7.tvLastText
            android.graphics.drawable.Drawable r3 = r7.btn_tel
            r0.setCompoundDrawables(r3, r2, r2, r2)
            android.widget.TextView r0 = r7.tvLastText
            r0.setVisibility(r1)
        L_0x005c:
            java.util.HashMap r0 = r8.getPoiExtra()
            java.lang.String r3 = "cinemazuo_flag"
            java.lang.Object r0 = r0.get(r3)
            java.lang.String r0 = (java.lang.String) r0
            java.util.HashMap r3 = r8.getPoiExtra()
            java.lang.String r4 = "reservable"
            java.lang.Object r3 = r3.get(r4)
            java.lang.String r3 = (java.lang.String) r3
            java.lang.String r4 = "1"
            boolean r0 = r4.equals(r0)
            if (r0 == 0) goto L_0x009c
            android.content.res.Resources r0 = r7.getResources()
            int r4 = com.autonavi.minimap.R.string.book_seat
            java.lang.String r0 = r0.getString(r4)
            android.widget.TextView r4 = r7.tvLastText
            r4.setText(r0)
            android.widget.TextView r4 = r7.tvLastText
            r4.setTag(r0)
            android.widget.TextView r0 = r7.tvLastText
            android.graphics.drawable.Drawable r4 = r7.btn_movie_normel
            r0.setCompoundDrawables(r4, r2, r2, r2)
            android.widget.TextView r0 = r7.tvLastText
            r0.setVisibility(r1)
        L_0x009c:
            java.lang.String r0 = r8.getType()
            if (r0 != 0) goto L_0x00a5
            java.lang.String r0 = ""
            goto L_0x00a9
        L_0x00a5:
            java.lang.String r0 = r8.getType()
        L_0x00a9:
            int r4 = r0.length()
            r5 = 4
            if (r4 < r5) goto L_0x00b4
            java.lang.String r0 = r0.substring(r1, r5)
        L_0x00b4:
            java.lang.String r4 = "0"
            java.util.HashMap r5 = r8.getPoiExtra()
            java.lang.String r6 = "sc_book_flag"
            java.lang.Object r5 = r5.get(r6)
            if (r5 == 0) goto L_0x00d4
            java.util.HashMap r4 = r8.getPoiExtra()
            java.lang.String r5 = "sc_book_flag"
            java.lang.Object r4 = r4.get(r5)
            java.io.Serializable r4 = (java.io.Serializable) r4
            java.lang.String r4 = r4.toString()
        L_0x00d4:
            java.lang.String r5 = "1"
            boolean r4 = r4.equals(r5)
            if (r4 == 0) goto L_0x00fc
            android.content.res.Resources r4 = r7.getResources()
            int r5 = com.autonavi.minimap.R.string.book_ticket
            java.lang.String r4 = r4.getString(r5)
            android.widget.TextView r5 = r7.tvLastText
            r5.setText(r4)
            android.widget.TextView r5 = r7.tvLastText
            r5.setTag(r4)
            android.widget.TextView r4 = r7.tvLastText
            android.graphics.drawable.Drawable r5 = r7.btn_dingpiao_normal
            r4.setCompoundDrawables(r5, r2, r2, r2)
            android.widget.TextView r4 = r7.tvLastText
            r4.setVisibility(r1)
        L_0x00fc:
            java.util.HashMap r4 = r8.getPoiExtra()
            if (r4 == 0) goto L_0x0144
            java.util.HashMap r4 = r8.getPoiExtra()
            java.lang.String r5 = "takeout_flag"
            boolean r4 = r4.containsKey(r5)
            if (r4 == 0) goto L_0x0144
            java.util.HashMap r4 = r8.getPoiExtra()
            java.lang.String r5 = "takeout_flag"
            java.lang.Object r4 = r4.get(r5)
            java.lang.String r4 = (java.lang.String) r4
            java.lang.String r5 = "1"
            boolean r4 = r5.equals(r4)
            if (r4 == 0) goto L_0x0144
            android.content.res.Resources r4 = r7.getResources()
            int r5 = com.autonavi.minimap.R.string.take_out_food
            java.lang.String r4 = r4.getString(r5)
            android.widget.TextView r5 = r7.tvLastText
            r5.setText(r4)
            android.widget.TextView r5 = r7.tvLastText
            r5.setTag(r4)
            android.widget.TextView r4 = r7.tvLastText
            android.graphics.drawable.Drawable r5 = r7.btn_waimai_normal
            r4.setCompoundDrawables(r5, r2, r2, r2)
            android.widget.TextView r4 = r7.tvLastText
            r4.setVisibility(r1)
        L_0x0144:
            java.lang.String r4 = "1000"
            boolean r4 = r4.equals(r0)
            if (r4 != 0) goto L_0x015c
            java.lang.String r4 = "1001"
            boolean r4 = r4.equals(r0)
            if (r4 != 0) goto L_0x015c
            java.lang.String r4 = "1002"
            boolean r0 = r4.equals(r0)
            if (r0 == 0) goto L_0x0184
        L_0x015c:
            java.lang.String r0 = "1"
            boolean r0 = r0.equals(r3)
            if (r0 == 0) goto L_0x0184
            android.widget.TextView r0 = r7.tvLastText
            android.graphics.drawable.Drawable r3 = r7.btn_hotel_normel
            r0.setCompoundDrawables(r3, r2, r2, r2)
            android.content.res.Resources r0 = r7.getResources()
            int r3 = com.autonavi.minimap.R.string.book_room
            java.lang.String r0 = r0.getString(r3)
            android.widget.TextView r3 = r7.tvLastText
            r3.setText(r0)
            android.widget.TextView r3 = r7.tvLastText
            r3.setTag(r0)
            android.widget.TextView r0 = r7.tvLastText
            r0.setVisibility(r1)
        L_0x0184:
            java.lang.Class<com.autonavi.map.fragmentcontainer.GeocodePOI> r0 = com.autonavi.map.fragmentcontainer.GeocodePOI.class
            boolean r0 = r0.isInstance(r8)
            if (r0 == 0) goto L_0x01ae
            android.widget.TextView r0 = r7.tvLastText
            android.graphics.drawable.Drawable r3 = r7.btn_add_poi
            r0.setCompoundDrawables(r3, r2, r2, r2)
            android.widget.TextView r0 = r7.tvLastText
            int r3 = com.autonavi.minimap.R.string.add_poi
            r0.setText(r3)
            android.widget.TextView r0 = r7.tvLastText
            android.app.Application r3 = com.autonavi.amap.app.AMapAppGlobal.getApplication()
            int r4 = com.autonavi.minimap.R.string.add_poi
            java.lang.String r3 = r3.getString(r4)
            r0.setTag(r3)
            android.widget.TextView r0 = r7.tvLastText
            r0.setVisibility(r1)
        L_0x01ae:
            java.util.HashMap r0 = r8.getPoiExtra()
            java.lang.String r3 = "Cpdata"
            boolean r0 = r0.containsKey(r3)
            r3 = 1
            if (r0 == 0) goto L_0x020f
            java.util.HashMap r0 = r8.getPoiExtra()
            java.lang.String r4 = "Cpdata"
            java.lang.Object r0 = r0.get(r4)
            if (r0 == 0) goto L_0x020f
            java.util.HashMap r8 = r8.getPoiExtra()
            java.lang.String r0 = "Cpdata"
            java.lang.Object r8 = r8.get(r0)
            java.io.Serializable r8 = (java.io.Serializable) r8
            java.lang.String r8 = r8.toString()
            org.xidea.el.JsonType r0 = new org.xidea.el.JsonType
            java.lang.Class<java.util.ArrayList> r4 = java.util.ArrayList.class
            java.lang.reflect.Type[] r5 = new java.lang.reflect.Type[r3]
            java.lang.Class<com.amap.bundle.datamodel.poi.CpData> r6 = com.amap.bundle.datamodel.poi.CpData.class
            r5[r1] = r6
            r0.<init>(r4, r5)
            java.lang.Object r8 = org.xidea.el.json.JSONDecoder.decode(r8, r0)
            java.util.ArrayList r8 = (java.util.ArrayList) r8
            if (r8 == 0) goto L_0x020f
            int r0 = r8.size()
            if (r0 <= 0) goto L_0x020f
            java.util.Iterator r8 = r8.iterator()
        L_0x01f6:
            boolean r0 = r8.hasNext()
            if (r0 == 0) goto L_0x020f
            java.lang.Object r0 = r8.next()
            com.amap.bundle.datamodel.poi.CpData r0 = (com.amap.bundle.datamodel.poi.CpData) r0
            java.lang.String r4 = "autonavi"
            java.lang.String r0 = r0.getSource()
            boolean r0 = r4.equals(r0)
            if (r0 == 0) goto L_0x01f6
            goto L_0x0210
        L_0x020f:
            r3 = 0
        L_0x0210:
            if (r3 == 0) goto L_0x0232
            android.content.res.Resources r8 = r7.getResources()
            int r0 = com.autonavi.minimap.R.string.indoor_map
            java.lang.String r8 = r8.getString(r0)
            android.widget.TextView r0 = r7.tvLastText
            r0.setText(r8)
            android.widget.TextView r0 = r7.tvLastText
            android.graphics.drawable.Drawable r3 = r7.btn_indoor_normal
            r0.setCompoundDrawables(r3, r2, r2, r2)
            android.widget.TextView r0 = r7.tvLastText
            r0.setVisibility(r1)
            android.widget.TextView r0 = r7.tvLastText
            r0.setTag(r8)
        L_0x0232:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.map.fragmentcontainer.page.mappage.TipsView.PoiDetailView.initLastBtn(com.autonavi.common.model.POI):void");
    }

    public void reset() {
        ImageView[] imageViewArr;
        this.poi = null;
        for (ImageView imageView : this.poiIvs) {
            if (imageView != null) {
                imageView.setVisibility(8);
            }
        }
        if (this.rating != null) {
            this.rating.setVisibility(8);
        }
        if (this.averagecost != null) {
            this.averagecost.setVisibility(8);
        }
        if (this.super_address_tip != null) {
            this.super_address_tip.setVisibility(8);
        }
        if (this.more_station_tv != null) {
            this.more_station_tv.setVisibility(8);
        }
        this.tvMainTitle.setText(R.string.poidetail_name);
        this.tvViceTitle.setText(R.string.poidetail_address);
        this.search_around.setVisibility(0);
        this.search_navi.setVisibility(0);
        this.txt_taxi.setVisibility(8);
        this.stationFlag = 0;
    }

    public void bindEvent(int i, Event event) {
        if (event != null) {
            Entry entry = this.events.get(i);
            if (entry != null) {
                entry.event = event;
                return;
            }
            Entry entry2 = new Entry();
            entry2.event = event;
            entry2.eventType = i;
            this.events.put(i, entry2);
        }
    }

    private Event getEvent(int i) {
        Entry entry = this.events.get(i);
        if (entry == null || entry.eventType != i) {
            return null;
        }
        return entry.event;
    }

    public void setEventListener(DetailViewEventListener detailViewEventListener) {
        this.mListener = detailViewEventListener;
    }

    public void requestPoiDetail(POI poi2) {
        if (this.geoCodeChecker != null) {
            this.geoCodeChecker.request(poi2.getPoint(), new Callback<PageBundle>() {
                public void error(Throwable th, boolean z) {
                }

                public void callback(PageBundle pageBundle) {
                    PoiDetailView.this.setPoiFooterDetail(pageBundle);
                }
            });
        }
    }

    public void setPoiFooterDetail(PageBundle pageBundle) {
        if (pageBundle != null) {
            String string = pageBundle.getString("mainTitle");
            String string2 = pageBundle.getString("viceTitle");
            POI poi2 = (POI) pageBundle.getObject("POI");
            if (poi2 != null) {
                if (string == null && string2 == null) {
                    if (!((FavoritePOI) poi2.as(FavoritePOI.class)).isSaved()) {
                        string = poi2.getName();
                        string2 = poi2.getAddr();
                    } else {
                        string = ((FavoritePOI) poi2.as(FavoritePOI.class)).getCustomName();
                        if (TextUtils.isEmpty(string)) {
                            string = ((FavoritePOI) poi2.as(FavoritePOI.class)).getName();
                        }
                        string2 = ((FavoritePOI) poi2.as(FavoritePOI.class)).getAddr();
                    }
                }
                boolean isInstance = GpsPOI.class.isInstance(poi2);
                View findViewById = findViewById(R.id.child_station_ll);
                TextView textView = (TextView) findViewById(R.id.station_num);
                if (pageBundle.getBoolean("isChildStation")) {
                    findViewById.setVisibility(0);
                    textView.setText(pageBundle.getString("childAlia"));
                } else {
                    findViewById.setVisibility(8);
                }
                setMainTitle(string);
                setViceTitle(string2);
                ISearchPoiData iSearchPoiData = (ISearchPoiData) poi2.as(ISearchPoiData.class);
                if (iSearchPoiData.getPoiChildrenInfo() != null) {
                    Collection<? extends POI> collection = iSearchPoiData.getPoiChildrenInfo().stationList;
                    if (collection != null && collection.size() > 0) {
                        setPoi(((ChildStationPoiData[]) collection.toArray(new ChildStationPoiData[collection.size()]))[0]);
                        return;
                    }
                }
                if (isInstance && TextUtils.isEmpty(poi2.getName())) {
                    poi2.setName(AMapAppGlobal.getApplication().getString(R.string.my_location));
                }
                setPoi(poi2);
            }
        }
    }

    public static void decorateDefault(PoiDetailView poiDetailView, final bid bid) {
        if (poiDetailView != null && bid != null) {
            poiDetailView.bindEvent(0, new Event() {
                public final void onExecute(int i, POI poi) {
                    PageBundle pageBundle = new PageBundle();
                    pageBundle.putObject("POI", poi);
                    pageBundle.putBoolean("isGeoCode", GeocodePOI.class.isInstance(poi));
                    pageBundle.putBoolean("isGPSPoint", GpsPOI.class.isInstance(poi));
                    pageBundle.putBoolean(Constant.KEY_IS_BACK, true);
                    pageBundle.putString("fromSource", "mainmap");
                    bid.startPage((String) "amap.search.action.poidetail", pageBundle);
                }
            });
            poiDetailView.bindEvent(3, new Event() {
                public final void onExecute(int i, POI poi) {
                    dfm dfm = (dfm) ank.a(dfm.class);
                    if (dfm != null) {
                        dfm.a(poi);
                    }
                }
            });
            poiDetailView.bindEvent(7, new Event() {
                public final void onExecute(int i, POI poi) {
                    IErrorReportStarter iErrorReportStarter = (IErrorReportStarter) ank.a(IErrorReportStarter.class);
                    if (iErrorReportStarter != null) {
                        iErrorReportStarter.startAddPOIFromXYSelectPoint(poi);
                    }
                }
            });
            poiDetailView.bindEvent(6, new Event() {
                public final void onExecute(int i, POI poi) {
                    if (aaw.d(DoNotUseTool.getContext()) == 0) {
                        ToastHelper.showToast(bid.getActivity().getResources().getString(R.string.network_error_message));
                        return;
                    }
                    IErrorReportStarter iErrorReportStarter = (IErrorReportStarter) ank.a(IErrorReportStarter.class);
                    if (iErrorReportStarter != null) {
                        iErrorReportStarter.startLocationError(poi);
                    }
                }
            });
            poiDetailView.bindEvent(2, new Event() {
                public final void onExecute(int i, POI poi) {
                    PageBundle pageBundle = new PageBundle();
                    if (GpsPOI.class.isInstance(poi)) {
                        POI clone = poi.clone();
                        clone.setName(AMapAppGlobal.getApplication().getString(R.string.LocationMe));
                        pageBundle.putObject("bundle_key_poi_start", clone);
                    } else {
                        pageBundle.putObject("bundle_key_poi_end", poi.clone());
                    }
                    pageBundle.putObject("bundle_key_auto_route", Boolean.TRUE);
                    bid.startPage((String) "amap.extra.route.route", pageBundle);
                }
            });
            poiDetailView.bindEvent(1, new Event() {
                public final void onExecute(int i, POI poi) {
                    PageBundle pageBundle = new PageBundle();
                    pageBundle.putObject("POI", poi);
                    bid.startPage((String) "amap.search.action.category", pageBundle);
                }
            });
            poiDetailView.bindEvent(5, new Event() {
                public final void onExecute(int i, POI poi) {
                    ank.a(djl.class);
                }
            });
            poiDetailView.bindEvent(8, new Event() {
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
                                PoiDetailView.addPhoneList(arrayList, phone.substring(0, 3), phone, DoNotUseTool.getContext());
                            } else {
                                String[] split = phone.split(";");
                                for (int i2 = 0; i2 < split.length; i2++) {
                                    PoiDetailView.addPhoneList(arrayList, split[i2].substring(0, 3), split[i2], DoNotUseTool.getContext());
                                }
                            }
                        }
                        if (arrayList.size() > 0) {
                            bnz.a((List<String>) arrayList, bid.getActivity(), (String) LogConstant.SEARCH_RESULT_MAP);
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
                                bnz.a((List<String>) arrayList2, bid.getActivity(), (String) LogConstant.SEARCH_RESULT_MAP);
                            }
                            return;
                        }
                        bid.getActivity();
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
}
