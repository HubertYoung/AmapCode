package com.autonavi.minimap.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import com.amap.bundle.cloudconfig.appinit.FunctionSupportConfiger;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.common.model.POI;
import com.autonavi.map.fragmentcontainer.GpsPOI;
import com.autonavi.minimap.R;
import com.autonavi.minimap.map.mapinterface.AbstractPoiDetailView;
import com.autonavi.minimap.map.mapinterface.AbstractPoiDetailView.Event;
import java.util.regex.Pattern;

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
    private SearchListColorBlockView blockView;
    private Drawable btnAddPoi;
    private Drawable btnDingpiaoNormal;
    private Drawable btnHotelNormel;
    private Drawable btnIndoorDisable;
    private Drawable btnIndoorNormal;
    private Drawable btnMovieNormel;
    private Drawable btnTel;
    private View btnToggle;
    private Drawable btnWaimaiNormal;
    private View cms_divider;
    private View detailBtnToggle;
    private View divider;
    private SparseArray<Entry> events = new SparseArray<>();
    private ImageView logo;
    private Context mContext;
    private boolean mEnableLandStyle = false;
    private LayoutInflater mInflater;
    private DetailViewEventListener mListener;
    /* access modifiers changed from: private */
    public a mPoiListener;
    private TextView moreStationTv;
    /* access modifiers changed from: private */
    public POI poi;
    private ImageView[] poiIvs = new ImageView[3];
    private RatingBar rating;
    private View rootView;
    private View search_around;
    private View search_navi;
    private int stationFlag = 0;
    private LinearLayout superAddressTip;
    private TextView tvDeepinfo;
    private TextView tvDistance;
    private TextView tvLastText;
    private TextView tvMainTitle;
    private TextView tvViceTitle;
    private TextView txt_route;
    private View vReportWrongPosition;

    public interface DetailViewEventListener {
        void onNaviClick(POI poi);

        void onRoutClick(POI poi);
    }

    class Entry {
        int a;
        Event b;

        private Entry() {
        }

        /* synthetic */ Entry(PoiDetailView poiDetailView, byte b2) {
            this();
        }
    }

    public void adjustMargin() {
    }

    public void refreshByScreenState(boolean z) {
    }

    public PoiDetailView(Context context) {
        super(context);
        init();
    }

    public PoiDetailView(Context context, boolean z) {
        super(context);
        this.mEnableLandStyle = z;
        init();
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
        this.rootView = this.mInflater.inflate(R.layout.v4_poi_detail, null);
        this.txt_route = (TextView) this.rootView.findViewById(R.id.tv_luxian);
        this.poiIvs[0] = (ImageView) this.rootView.findViewById(R.id.poi_iv_1);
        this.poiIvs[1] = (ImageView) this.rootView.findViewById(R.id.poi_iv_2);
        this.poiIvs[2] = (ImageView) this.rootView.findViewById(R.id.poi_iv_3);
        this.rating = (RatingBar) this.rootView.findViewById(R.id.rating_bar);
        this.averagecost = (TextView) this.rootView.findViewById(R.id.avgprice);
        this.logo = (ImageView) this.rootView.findViewById(R.id.logo);
        this.tvMainTitle = (TextView) this.rootView.findViewById(R.id.text_name);
        this.tvViceTitle = (TextView) this.rootView.findViewById(R.id.text_addr);
        this.moreStationTv = (TextView) this.rootView.findViewById(R.id.more_station_tv);
        this.superAddressTip = (LinearLayout) this.rootView.findViewById(R.id.super_address_tip);
        this.tvDeepinfo = (TextView) this.rootView.findViewById(R.id.deepinfo);
        this.tvDistance = (TextView) this.rootView.findViewById(R.id.distance);
        this.divider = this.rootView.findViewById(R.id.divider);
        this.cms_divider = this.rootView.findViewById(R.id.cms_divider);
        this.vReportWrongPosition = this.rootView.findViewById(R.id.tv_error);
        this.vReportWrongPosition.setOnClickListener(this);
        this.search_around = this.rootView.findViewById(R.id.tv_fujin);
        this.search_navi = this.rootView.findViewById(R.id.tv_navi);
        this.tvLastText = (TextView) this.rootView.findViewById(R.id.tv_indoor);
        this.detailBtnToggle = this.rootView.findViewById(R.id.poi_detail);
        this.btnToggle = this.rootView.findViewById(R.id.detail_btn_toggle);
        this.blockView = (SearchListColorBlockView) this.rootView.findViewById(R.id.super_addr_color_block);
        Resources resources = getResources();
        this.btnHotelNormel = resources.getDrawable(R.drawable.search_result_hotle);
        this.btnMovieNormel = resources.getDrawable(R.drawable.search_result_movie);
        this.btnWaimaiNormal = resources.getDrawable(R.drawable.search_result_waimai);
        this.btnDingpiaoNormal = resources.getDrawable(R.drawable.search_result_dingpiao);
        this.btnIndoorNormal = resources.getDrawable(R.drawable.mbox_icon_indoor_normal);
        this.btnIndoorDisable = resources.getDrawable(R.drawable.mbox_icon_indoor_disable);
        this.btnAddPoi = resources.getDrawable(R.drawable.funicon_add_tab);
        this.btnTel = resources.getDrawable(R.drawable.search_result_call);
        if (this.btnHotelNormel != null) {
            this.btnHotelNormel.setBounds(0, 0, this.btnHotelNormel.getMinimumWidth(), this.btnHotelNormel.getMinimumHeight());
        }
        if (this.btnMovieNormel != null) {
            this.btnMovieNormel.setBounds(0, 0, this.btnMovieNormel.getMinimumWidth(), this.btnMovieNormel.getMinimumHeight());
        }
        if (this.btnWaimaiNormal != null) {
            this.btnWaimaiNormal.setBounds(0, 0, this.btnWaimaiNormal.getMinimumWidth(), this.btnWaimaiNormal.getMinimumHeight());
        }
        if (this.btnDingpiaoNormal != null) {
            this.btnDingpiaoNormal.setBounds(0, 0, this.btnDingpiaoNormal.getMinimumWidth(), this.btnDingpiaoNormal.getMinimumHeight());
        }
        if (this.btnIndoorDisable != null) {
            this.btnIndoorDisable.setBounds(0, 0, this.btnIndoorDisable.getMinimumWidth(), this.btnIndoorDisable.getMinimumHeight());
        }
        if (this.btnIndoorNormal != null) {
            this.btnIndoorNormal.setBounds(0, 0, this.btnIndoorNormal.getMinimumWidth(), this.btnIndoorNormal.getMinimumHeight());
        }
        if (this.btnAddPoi != null) {
            this.btnAddPoi.setBounds(0, 0, this.btnAddPoi.getMinimumWidth(), this.btnAddPoi.getMinimumHeight());
        }
        if (this.btnTel != null) {
            this.btnTel.setBounds(0, 0, this.btnTel.getMinimumWidth(), this.btnTel.getMinimumHeight());
        }
        registerListener();
        if (this.mEnableLandStyle) {
            addView(this.rootView, new LayoutParams(-1, ags.b(AMapAppGlobal.getTopActivity()) ? AbstractPoiDetailView.TIPSHEIGHTINLAND : AbstractPoiDetailView.TIPSHEIGHTINPORT));
        } else {
            addView(this.rootView);
        }
    }

    private void registerListener() {
        AnonymousClass1 r0 = new OnClickListener() {
            public void onClick(View view) {
                Event access$000 = PoiDetailView.this.getEvent(0);
                if (access$000 != null) {
                    access$000.onExecute(0, PoiDetailView.this.poi);
                }
                if (PoiDetailView.this.mPoiListener != null) {
                    PoiDetailView.this.mPoiListener.b(PoiDetailView.this.poi);
                }
            }
        };
        this.detailBtnToggle.setOnClickListener(r0);
        this.btnToggle.setOnClickListener(r0);
        this.search_around.setOnClickListener(this);
        this.txt_route.setOnClickListener(this);
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
            boolean r7 = r10.isNumberFormat(r5)
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
            return
        L_0x04f5:
            r11.setVisibility(r3)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.widget.PoiDetailView.setPoi(com.autonavi.common.model.POI):void");
    }

    public POI getPoi() {
        return this.poi;
    }

    private void setTextViewMaxWidth(int i, View view, ImageView imageView, TextView textView) {
        if (view != null && imageView != null && textView != null) {
            this.btnToggle.measure(0, 0);
            textView.setMaxWidth((getContext().getResources().getDisplayMetrics().widthPixels - (i * imageView.getDrawable().getIntrinsicWidth())) - (this.btnToggle.getMeasuredWidth() * 2));
        }
    }

    private void setNameTextMaxWidth(TextView textView, View view) {
        if (textView != null && view != null) {
            this.btnToggle.measure(0, 0);
            view.measure(0, 0);
            textView.setMaxWidth((int) ((((((double) getContext().getResources().getDisplayMetrics().widthPixels) * 0.9d) - ((double) view.getMeasuredWidth())) - ((double) this.btnToggle.getMeasuredWidth())) - ((double) getContext().getResources().getDimensionPixelSize(R.dimen.default_margin_21A))));
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
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.widget.PoiDetailView.getCmsInfo(com.autonavi.common.model.POI):java.lang.String");
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
                this.txt_route.setText(R.string.route);
                this.txt_route.setCompoundDrawablesWithIntrinsicBounds(R.drawable.tips_route, 0, 0, 0);
                return;
            }
            this.txt_route.setText(R.string.taxi);
            this.txt_route.setCompoundDrawablesWithIntrinsicBounds(R.drawable.bubble_search_result_texi, 0, 0, 0);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:54:0x0129  */
    /* JADX WARNING: Removed duplicated region for block: B:56:0x0136  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void setViceTitle(java.lang.String r10) {
        /*
            r9 = this;
            r0 = 8
            r1 = 0
            if (r10 == 0) goto L_0x00eb
            java.lang.String r2 = ""
            boolean r2 = r2.equals(r10)
            if (r2 == 0) goto L_0x0015
            boolean r2 = r9.isSaveEnabled()
            if (r2 == 0) goto L_0x0015
            goto L_0x00eb
        L_0x0015:
            android.widget.RatingBar r2 = r9.rating
            int r2 = r2.getVisibility()
            if (r2 != r0) goto L_0x00ea
            android.widget.TextView r2 = r9.averagecost
            int r2 = r2.getVisibility()
            if (r2 != r0) goto L_0x00ea
            int r2 = r9.stationFlag
            if (r2 != 0) goto L_0x0038
            android.widget.TextView r0 = r9.tvViceTitle
            r0.setVisibility(r1)
            android.widget.TextView r0 = r9.tvViceTitle
            java.lang.String r10 = java.lang.String.valueOf(r10)
            r0.setText(r10)
            return
        L_0x0038:
            r9.showDivider(r1)
            android.widget.TextView r2 = r9.tvViceTitle
            r2.setVisibility(r0)
            android.widget.LinearLayout r2 = r9.superAddressTip
            r2.setVisibility(r1)
            java.lang.String r2 = ";"
            java.lang.String[] r10 = r10.split(r2)
            android.widget.LinearLayout$LayoutParams r2 = new android.widget.LinearLayout$LayoutParams
            r3 = -2
            r2.<init>(r3, r3)
            r2.setMargins(r1, r1, r0, r1)
            android.view.View r0 = r9.btnToggle
            r0.measure(r1, r1)
            android.widget.TextView r0 = r9.tvDistance
            r0.measure(r1, r1)
            java.lang.String r0 = "#42a5ff"
            int r2 = r10.length
            com.autonavi.minimap.widget.SearchListColorBlockView$ColorBlock[] r2 = new com.autonavi.minimap.widget.SearchListColorBlockView.ColorBlock[r2]
            r3 = r0
            r0 = 0
        L_0x0065:
            int r4 = r10.length
            if (r0 >= r4) goto L_0x00d4
            r4 = r10[r0]
            java.lang.String r5 = "\\|"
            java.lang.String[] r4 = r4.split(r5)
            int r5 = r4.length
            if (r5 == 0) goto L_0x00d1
            com.autonavi.minimap.widget.SearchListColorBlockView$ColorBlock r5 = new com.autonavi.minimap.widget.SearchListColorBlockView$ColorBlock
            r5.<init>()
            int r6 = r4.length
            r7 = 1
            if (r6 <= r7) goto L_0x00bb
            r6 = r4[r1]
            boolean r6 = android.text.TextUtils.isEmpty(r6)
            if (r6 != 0) goto L_0x00cf
            r6 = r4[r7]
            boolean r6 = android.text.TextUtils.isEmpty(r6)
            if (r6 != 0) goto L_0x00cf
            r6 = r4[r1]
            r5.mText = r6
            int r6 = r10.length
            int r6 = r6 - r7
            if (r0 != r6) goto L_0x00a4
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            java.lang.String r6 = "#"
            r3.<init>(r6)
            r6 = r4[r7]
            r3.append(r6)
            java.lang.String r3 = r3.toString()
        L_0x00a4:
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ IllegalArgumentException -> 0x00c7 }
            java.lang.String r8 = "#"
            r6.<init>(r8)     // Catch:{ IllegalArgumentException -> 0x00c7 }
            r4 = r4[r7]     // Catch:{ IllegalArgumentException -> 0x00c7 }
            r6.append(r4)     // Catch:{ IllegalArgumentException -> 0x00c7 }
            java.lang.String r4 = r6.toString()     // Catch:{ IllegalArgumentException -> 0x00c7 }
            int r4 = android.graphics.Color.parseColor(r4)     // Catch:{ IllegalArgumentException -> 0x00c7 }
            r5.mColor = r4     // Catch:{ IllegalArgumentException -> 0x00c7 }
            goto L_0x00cf
        L_0x00bb:
            r6 = r4[r1]
            boolean r6 = android.text.TextUtils.isEmpty(r6)
            if (r6 != 0) goto L_0x00c7
            r4 = r4[r1]
            r5.mText = r4
        L_0x00c7:
            java.lang.String r4 = "#42a5ff"
            int r4 = android.graphics.Color.parseColor(r4)
            r5.mColor = r4
        L_0x00cf:
            r2[r0] = r5
        L_0x00d1:
            int r0 = r0 + 1
            goto L_0x0065
        L_0x00d4:
            com.autonavi.minimap.widget.SearchListColorBlockView$ItemInfo r10 = new com.autonavi.minimap.widget.SearchListColorBlockView$ItemInfo
            r10.<init>()
            com.autonavi.minimap.widget.SearchListColorBlockView r0 = r9.blockView
            com.autonavi.minimap.widget.SearchListColorBlockView$ItemInfo r10 = r10.setBlocks(r2)
            int r1 = android.graphics.Color.parseColor(r3)
            com.autonavi.minimap.widget.SearchListColorBlockView$ItemInfo r10 = r10.setBlockColor(r1)
            r0.setItemInfo(r10)
        L_0x00ea:
            return
        L_0x00eb:
            android.widget.RatingBar r10 = r9.rating
            int r10 = r10.getVisibility()
            if (r10 != r0) goto L_0x013e
            android.widget.TextView r10 = r9.averagecost
            int r10 = r10.getVisibility()
            if (r10 != r0) goto L_0x013e
            com.autonavi.common.model.POI r10 = r9.poi
            if (r10 == 0) goto L_0x013e
            com.autonavi.common.model.POI r10 = r9.poi
            if (r10 == 0) goto L_0x0126
            com.autonavi.common.model.POI r10 = r9.poi
            java.lang.Class<com.amap.bundle.datamodel.FavoritePOI> r0 = com.amap.bundle.datamodel.FavoritePOI.class
            com.autonavi.common.model.POI r10 = r10.as(r0)
            com.amap.bundle.datamodel.FavoritePOI r10 = (com.amap.bundle.datamodel.FavoritePOI) r10
            java.lang.Class<com> r0 = defpackage.com.class
            java.lang.Object r0 = defpackage.ank.a(r0)
            com r0 = (defpackage.com) r0
            if (r0 == 0) goto L_0x0126
            java.lang.String r2 = r0.a()
            cop r0 = r0.b(r2)
            if (r0 == 0) goto L_0x0126
            boolean r10 = r0.c(r10)
            goto L_0x0127
        L_0x0126:
            r10 = 0
        L_0x0127:
            if (r10 != 0) goto L_0x0136
            android.widget.TextView r10 = r9.tvViceTitle
            r10.setVisibility(r1)
            android.widget.TextView r10 = r9.tvViceTitle
            int r0 = com.autonavi.minimap.R.string.click_for_more
            r10.setText(r0)
            return
        L_0x0136:
            android.widget.TextView r10 = r9.tvViceTitle
            java.lang.String r0 = ""
            r10.setText(r0)
            return
        L_0x013e:
            android.widget.TextView r10 = r9.tvViceTitle
            r10.setVisibility(r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.widget.PoiDetailView.setViceTitle(java.lang.String):void");
    }

    public void showDivider(boolean z) {
        if (this.divider != null) {
            this.divider.setVisibility(z ? 0 : 8);
        }
    }

    public void setDistance(int i) {
        String str = "";
        if (!this.poi.getType().equals("") && this.poi.getType().length() > 5) {
            str = this.poi.getType().substring(0, 4);
        }
        boolean z = !str.equals("1507");
        if (this.tvDistance == null || "-100".equals(String.valueOf(i)) || !z) {
            if (this.tvDistance != null) {
                this.tvDistance.setVisibility(8);
            }
            return;
        }
        this.tvDistance.setVisibility(0);
        this.divider.setVisibility(0);
        this.tvDistance.setText(cfe.a(i));
    }

    public void setDeepinfo(String str) {
        if (this.tvDeepinfo == null || TextUtils.isEmpty(str)) {
            if (this.tvDeepinfo != null) {
                this.tvDeepinfo.setVisibility(8);
            }
            if (this.cms_divider != null) {
                this.cms_divider.setVisibility(0);
            }
        } else {
            this.tvDeepinfo.setVisibility(0);
            this.tvDeepinfo.setText(Html.fromHtml(str));
            if (this.cms_divider != null) {
                this.cms_divider.setVisibility(8);
            }
        }
    }

    public void onClick(View view) {
        if (!bnp.a()) {
            AMapLog.d("CLICKEVENT", "PoiDetailView");
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
            } else if (view == this.txt_route) {
                if (this.txt_route.getText().toString().equals(getResources().getString(R.string.route))) {
                    Event event3 = getEvent(2);
                    if (event3 != null) {
                        if (this.mPoiListener != null) {
                            this.mPoiListener.d(this.poi);
                        }
                        event3.onExecute(2, this.poi);
                        if (this.mListener != null) {
                            this.mListener.onRoutClick(this.poi);
                        }
                    }
                    return;
                }
                Event event4 = getEvent(5);
                if (event4 != null) {
                    event4.onExecute(5, this.poi);
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

    /* JADX WARNING: Removed duplicated region for block: B:61:0x0211  */
    /* JADX WARNING: Removed duplicated region for block: B:65:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void initLastBtn(com.autonavi.common.model.POI r8) {
        /*
            r7 = this;
            android.widget.TextView r0 = r7.tvLastText
            r1 = 8
            r0.setVisibility(r1)
            r0 = 0
            r1 = 0
            if (r8 == 0) goto L_0x005b
            java.util.HashMap r2 = r8.getPoiExtra()
            java.lang.String r3 = "SrcType"
            boolean r2 = r2.containsKey(r3)
            if (r2 == 0) goto L_0x005b
            java.util.HashMap r2 = r8.getPoiExtra()
            java.lang.String r3 = "SrcType"
            java.lang.Object r2 = r2.get(r3)
            java.lang.String r2 = (java.lang.String) r2
            boolean r3 = android.text.TextUtils.isEmpty(r2)
            if (r3 != 0) goto L_0x005b
            java.lang.String r3 = "nativepoi"
            boolean r2 = r3.equals(r2)
            if (r2 == 0) goto L_0x005b
            java.lang.String r2 = r8.getPhone()
            boolean r2 = android.text.TextUtils.isEmpty(r2)
            if (r2 != 0) goto L_0x005b
            android.content.res.Resources r2 = r7.getResources()
            int r3 = com.autonavi.minimap.R.string.tel
            java.lang.String r2 = r2.getString(r3)
            android.widget.TextView r3 = r7.tvLastText
            r3.setText(r2)
            android.widget.TextView r3 = r7.tvLastText
            r3.setTag(r2)
            android.widget.TextView r2 = r7.tvLastText
            android.graphics.drawable.Drawable r3 = r7.btnTel
            r2.setCompoundDrawables(r3, r1, r1, r1)
            android.widget.TextView r2 = r7.tvLastText
            r2.setVisibility(r0)
        L_0x005b:
            java.util.HashMap r2 = r8.getPoiExtra()
            java.lang.String r3 = "cinemazuo_flag"
            java.lang.Object r2 = r2.get(r3)
            java.lang.String r2 = (java.lang.String) r2
            java.util.HashMap r3 = r8.getPoiExtra()
            java.lang.String r4 = "reservable"
            java.lang.Object r3 = r3.get(r4)
            java.lang.String r3 = (java.lang.String) r3
            java.lang.String r4 = "1"
            boolean r2 = r4.equals(r2)
            if (r2 == 0) goto L_0x009b
            android.content.res.Resources r2 = r7.getResources()
            int r4 = com.autonavi.minimap.R.string.book_seat
            java.lang.String r2 = r2.getString(r4)
            android.widget.TextView r4 = r7.tvLastText
            r4.setText(r2)
            android.widget.TextView r4 = r7.tvLastText
            r4.setTag(r2)
            android.widget.TextView r2 = r7.tvLastText
            android.graphics.drawable.Drawable r4 = r7.btnMovieNormel
            r2.setCompoundDrawables(r4, r1, r1, r1)
            android.widget.TextView r2 = r7.tvLastText
            r2.setVisibility(r0)
        L_0x009b:
            java.lang.String r2 = r8.getType()
            if (r2 != 0) goto L_0x00a4
            java.lang.String r2 = ""
            goto L_0x00a8
        L_0x00a4:
            java.lang.String r2 = r8.getType()
        L_0x00a8:
            int r4 = r2.length()
            r5 = 4
            if (r4 < r5) goto L_0x00b3
            java.lang.String r2 = r2.substring(r0, r5)
        L_0x00b3:
            java.lang.String r4 = "0"
            java.util.HashMap r5 = r8.getPoiExtra()
            java.lang.String r6 = "sc_book_flag"
            java.lang.Object r5 = r5.get(r6)
            if (r5 == 0) goto L_0x00d3
            java.util.HashMap r4 = r8.getPoiExtra()
            java.lang.String r5 = "sc_book_flag"
            java.lang.Object r4 = r4.get(r5)
            java.io.Serializable r4 = (java.io.Serializable) r4
            java.lang.String r4 = r4.toString()
        L_0x00d3:
            java.lang.String r5 = "1"
            boolean r4 = r4.equals(r5)
            if (r4 == 0) goto L_0x00fb
            android.content.res.Resources r4 = r7.getResources()
            int r5 = com.autonavi.minimap.R.string.book_ticket
            java.lang.String r4 = r4.getString(r5)
            android.widget.TextView r5 = r7.tvLastText
            r5.setText(r4)
            android.widget.TextView r5 = r7.tvLastText
            r5.setTag(r4)
            android.widget.TextView r4 = r7.tvLastText
            android.graphics.drawable.Drawable r5 = r7.btnDingpiaoNormal
            r4.setCompoundDrawables(r5, r1, r1, r1)
            android.widget.TextView r4 = r7.tvLastText
            r4.setVisibility(r0)
        L_0x00fb:
            java.util.HashMap r4 = r8.getPoiExtra()
            if (r4 == 0) goto L_0x0143
            java.util.HashMap r4 = r8.getPoiExtra()
            java.lang.String r5 = "takeout_flag"
            boolean r4 = r4.containsKey(r5)
            if (r4 == 0) goto L_0x0143
            java.util.HashMap r4 = r8.getPoiExtra()
            java.lang.String r5 = "takeout_flag"
            java.lang.Object r4 = r4.get(r5)
            java.lang.String r4 = (java.lang.String) r4
            java.lang.String r5 = "1"
            boolean r4 = r5.equals(r4)
            if (r4 == 0) goto L_0x0143
            android.content.res.Resources r4 = r7.getResources()
            int r5 = com.autonavi.minimap.R.string.take_out_food
            java.lang.String r4 = r4.getString(r5)
            android.widget.TextView r5 = r7.tvLastText
            r5.setText(r4)
            android.widget.TextView r5 = r7.tvLastText
            r5.setTag(r4)
            android.widget.TextView r4 = r7.tvLastText
            android.graphics.drawable.Drawable r5 = r7.btnWaimaiNormal
            r4.setCompoundDrawables(r5, r1, r1, r1)
            android.widget.TextView r4 = r7.tvLastText
            r4.setVisibility(r0)
        L_0x0143:
            java.lang.String r4 = "1000"
            boolean r4 = r4.equals(r2)
            if (r4 != 0) goto L_0x015b
            java.lang.String r4 = "1001"
            boolean r4 = r4.equals(r2)
            if (r4 != 0) goto L_0x015b
            java.lang.String r4 = "1002"
            boolean r2 = r4.equals(r2)
            if (r2 == 0) goto L_0x0183
        L_0x015b:
            java.lang.String r2 = "1"
            boolean r2 = r2.equals(r3)
            if (r2 == 0) goto L_0x0183
            android.widget.TextView r2 = r7.tvLastText
            android.graphics.drawable.Drawable r3 = r7.btnHotelNormel
            r2.setCompoundDrawables(r3, r1, r1, r1)
            android.content.res.Resources r2 = r7.getResources()
            int r3 = com.autonavi.minimap.R.string.book_room
            java.lang.String r2 = r2.getString(r3)
            android.widget.TextView r3 = r7.tvLastText
            r3.setText(r2)
            android.widget.TextView r3 = r7.tvLastText
            r3.setTag(r2)
            android.widget.TextView r2 = r7.tvLastText
            r2.setVisibility(r0)
        L_0x0183:
            java.lang.Class<com.autonavi.map.fragmentcontainer.GeocodePOI> r2 = com.autonavi.map.fragmentcontainer.GeocodePOI.class
            boolean r2 = r2.isInstance(r8)
            if (r2 == 0) goto L_0x01ad
            android.widget.TextView r2 = r7.tvLastText
            android.graphics.drawable.Drawable r3 = r7.btnAddPoi
            r2.setCompoundDrawables(r3, r1, r1, r1)
            android.widget.TextView r2 = r7.tvLastText
            int r3 = com.autonavi.minimap.R.string.add_poi
            r2.setText(r3)
            android.widget.TextView r2 = r7.tvLastText
            android.app.Application r3 = com.autonavi.amap.app.AMapAppGlobal.getApplication()
            int r4 = com.autonavi.minimap.R.string.add_poi
            java.lang.String r3 = r3.getString(r4)
            r2.setTag(r3)
            android.widget.TextView r2 = r7.tvLastText
            r2.setVisibility(r0)
        L_0x01ad:
            java.util.HashMap r2 = r8.getPoiExtra()
            java.lang.String r3 = "Cpdata"
            boolean r2 = r2.containsKey(r3)
            r3 = 1
            if (r2 == 0) goto L_0x020e
            java.util.HashMap r2 = r8.getPoiExtra()
            java.lang.String r4 = "Cpdata"
            java.lang.Object r2 = r2.get(r4)
            if (r2 == 0) goto L_0x020e
            java.util.HashMap r8 = r8.getPoiExtra()
            java.lang.String r2 = "Cpdata"
            java.lang.Object r8 = r8.get(r2)
            java.io.Serializable r8 = (java.io.Serializable) r8
            java.lang.String r8 = r8.toString()
            org.xidea.el.JsonType r2 = new org.xidea.el.JsonType
            java.lang.Class<java.util.ArrayList> r4 = java.util.ArrayList.class
            java.lang.reflect.Type[] r5 = new java.lang.reflect.Type[r3]
            java.lang.Class<com.amap.bundle.datamodel.poi.CpData> r6 = com.amap.bundle.datamodel.poi.CpData.class
            r5[r0] = r6
            r2.<init>(r4, r5)
            java.lang.Object r8 = org.xidea.el.json.JSONDecoder.decode(r8, r2)
            java.util.ArrayList r8 = (java.util.ArrayList) r8
            if (r8 == 0) goto L_0x020e
            int r2 = r8.size()
            if (r2 <= 0) goto L_0x020e
            java.util.Iterator r8 = r8.iterator()
        L_0x01f5:
            boolean r2 = r8.hasNext()
            if (r2 == 0) goto L_0x020e
            java.lang.Object r2 = r8.next()
            com.amap.bundle.datamodel.poi.CpData r2 = (com.amap.bundle.datamodel.poi.CpData) r2
            java.lang.String r4 = "autonavi"
            java.lang.String r2 = r2.getSource()
            boolean r2 = r4.equals(r2)
            if (r2 == 0) goto L_0x01f5
            goto L_0x020f
        L_0x020e:
            r3 = 0
        L_0x020f:
            if (r3 == 0) goto L_0x0231
            android.content.res.Resources r8 = r7.getResources()
            int r2 = com.autonavi.minimap.R.string.indoor_map
            java.lang.String r8 = r8.getString(r2)
            android.widget.TextView r2 = r7.tvLastText
            r2.setText(r8)
            android.widget.TextView r2 = r7.tvLastText
            android.graphics.drawable.Drawable r3 = r7.btnIndoorNormal
            r2.setCompoundDrawables(r3, r1, r1, r1)
            android.widget.TextView r1 = r7.tvLastText
            r1.setVisibility(r0)
            android.widget.TextView r0 = r7.tvLastText
            r0.setTag(r8)
        L_0x0231:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.widget.PoiDetailView.initLastBtn(com.autonavi.common.model.POI):void");
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
        if (this.superAddressTip != null) {
            this.superAddressTip.setVisibility(8);
        }
        if (this.moreStationTv != null) {
            this.moreStationTv.setVisibility(8);
        }
        this.tvMainTitle.setText(R.string.poidetail_name);
        this.tvViceTitle.setText(R.string.poidetail_address);
        this.search_around.setVisibility(0);
        this.search_navi.setVisibility(0);
        this.txt_route.setVisibility(0);
        this.stationFlag = 0;
    }

    public void bindEvent(int i, Event event) {
        if (event != null) {
            Entry entry = this.events.get(i);
            if (entry != null) {
                entry.b = event;
                return;
            }
            Entry entry2 = new Entry(this, 0);
            entry2.b = event;
            entry2.a = i;
            this.events.put(i, entry2);
        }
    }

    private boolean isNumberFormat(String str) {
        return Pattern.compile("[0-9]+\\.?[0-9]*").matcher(str).matches() || Pattern.compile("[0-9]*").matcher(str).matches();
    }

    /* access modifiers changed from: private */
    public Event getEvent(int i) {
        Entry entry = this.events.get(i);
        if (entry == null || entry.a != i) {
            return null;
        }
        return entry.b;
    }

    public void setEventListener(DetailViewEventListener detailViewEventListener) {
        this.mListener = detailViewEventListener;
    }
}
