package com.autonavi.map.search.tip.indicator;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.amap.bundle.cloudconfig.appinit.FunctionSupportConfiger;
import com.amap.bundle.statistics.LogManager;
import com.autonavi.bundle.entity.common.searchpoi.SearchPoi;
import com.autonavi.bundle.entity.infolite.internal.InfoliteResult;
import com.autonavi.bundle.entity.infolite.internal.template.PoiButtonTemplate;
import com.autonavi.bundle.routecommon.model.RouteType;
import com.autonavi.bundle.searchresult.ajx.ModulePoi;
import com.autonavi.bundle.uitemplate.mapwidget.widget.diy.DIYMainMapPresenter;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.POI;
import com.autonavi.map.fragmentcontainer.GpsPOI;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.map.widget.DrawableCenterTextView;
import com.autonavi.minimap.R;
import com.autonavi.minimap.search.templete.type.PoiLayoutTemplate;
import com.autonavi.sdk.location.LocationInstrument;
import com.autonavi.sdk.log.util.LogConstant;
import java.util.AbstractMap.SimpleEntry;
import java.util.List;
import org.json.JSONObject;

public class SearchPoiIndicatorView extends FrameLayout implements OnClickListener {
    private static final String TAG = "SearchPoiIndicatorView";
    protected LinearLayout m01Layout;
    protected DrawableCenterTextView m02Button;
    protected LinearLayout m02Layout;
    protected DrawableCenterTextView m03Button;
    protected LinearLayout m03Layout;
    cbu mBehavior;
    protected ImageView mDetailsImage;
    protected TextView mDetailsText;
    private OnTouchListener mDetailsTouchListener;
    private String mGsid;
    private String mIndicatorViewType;
    private a mOnDetailsClickListener;
    private POI mPoi;
    private String mState;
    /* access modifiers changed from: private */
    public int sTouchSlop;

    public interface a {
        void a();
    }

    public SearchPoiIndicatorView(Context context) {
        this(context, "type_default");
    }

    public SearchPoiIndicatorView(Context context, String str) {
        super(context);
        this.sTouchSlop = 0;
        this.mState = ModulePoi.TIPS;
        this.mDetailsTouchListener = new OnTouchListener() {
            private int b;
            private boolean c;
            private boolean d;

            public final boolean onTouch(View view, MotionEvent motionEvent) {
                boolean z = true;
                switch (motionEvent.getAction()) {
                    case 0:
                        this.d = true;
                        this.b = (int) motionEvent.getY();
                        this.c = true;
                        break;
                    case 1:
                        if (this.c && this.d) {
                            this.d = false;
                            if (motionEvent.getAction() == 1) {
                                SearchPoiIndicatorView.this.handleClick01Button();
                                break;
                            }
                        }
                        break;
                    case 2:
                        if (Math.abs(this.b - ((int) motionEvent.getY())) >= SearchPoiIndicatorView.this.sTouchSlop) {
                            z = false;
                        }
                        this.c = z;
                        break;
                }
                return false;
            }
        };
        setId(R.id.IndicatorView_Root);
        this.mIndicatorViewType = str;
        LayoutInflater.from(context).inflate(R.layout.poi_indicator, this, true);
        this.sTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        this.mBehavior = new cbu(this);
        initView();
    }

    private void initView() {
        init01Button();
        init02Button();
        init03Button();
    }

    public void init01Button() {
        this.m01Layout = (LinearLayout) findViewById(R.id.linear_01);
        this.mDetailsImage = (ImageView) findViewById(R.id.image_indicator_details);
        this.mDetailsText = (TextView) findViewById(R.id.text_indicator_details);
        if (ags.a(DoNotUseTool.getContext()).right < 720) {
            this.mDetailsText.setText(R.string.poi_indicator_details);
        } else {
            this.mDetailsText.setText(R.string.poi_indicator_check_details);
        }
        this.mDetailsImage.setImageResource(R.drawable.indicator_detail_selector);
        this.m01Layout.setOnTouchListener(this.mDetailsTouchListener);
    }

    public void init02Button() {
        this.m02Layout = (LinearLayout) findViewById(R.id.linear_02);
        this.m02Button = (DrawableCenterTextView) findViewById(R.id.text_indicator_02_btn);
        if (TextUtils.equals(this.mIndicatorViewType, "type_my_position")) {
            this.m02Button.setText(R.string.poi_indicator_call_taxi);
            this.m02Button.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.poi_indicator_call_taxi), null, null, null);
        } else if (TextUtils.equals(this.mIndicatorViewType, "type_press_long")) {
            this.m02Button.setText(R.string.poi_indicator_new_add);
            this.m02Button.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.poi_indicator_new_add), null, null, null);
        } else if (TextUtils.equals(this.mIndicatorViewType, "type_call_taxi_by_server")) {
            this.m02Button.setText(R.string.poi_indicator_call_taxi);
            this.m02Button.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.poi_indicator_call_taxi), null, null, null);
        } else {
            this.m02Button.setText(R.string.poi_indicator_btn);
            this.m02Button.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.poi_indicator_btn), null, null, null);
        }
        this.m02Layout.setOnClickListener(this);
    }

    public void init03Button() {
        this.m03Layout = (LinearLayout) findViewById(R.id.linear_03);
        this.m03Button = (DrawableCenterTextView) findViewById(R.id.text_indicator_03_btn);
        this.m03Button.setText(R.string.poi_indicator_route);
        this.m03Button.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.poi_indicator_route), null, null, null);
        this.m03Layout.setOnClickListener(this);
    }

    public void processIndicatorUI(String str) {
        this.mState = str;
        if (TextUtils.equals(str, "full")) {
            if (ags.a(DoNotUseTool.getContext()).right < 720) {
                this.mDetailsText.setText(getContext().getString(R.string.poi_indicator_map));
            } else {
                this.mDetailsText.setText(getContext().getString(R.string.poi_indicator_check_map));
            }
            this.mDetailsImage.setImageResource(R.drawable.indicator_detail_map_selector);
            return;
        }
        if (TextUtils.equals(str, ModulePoi.TIPS)) {
            if (ags.a(DoNotUseTool.getContext()).right < 720) {
                this.mDetailsText.setText(R.string.poi_indicator_details);
            } else {
                this.mDetailsText.setText(getContext().getString(R.string.poi_indicator_check_details));
            }
            this.mDetailsImage.setImageResource(R.drawable.indicator_detail_detail_selector);
        }
    }

    public void handleClick01Button() {
        if (!(this.mPoi == null || this.mOnDetailsClickListener == null)) {
            String str = this.mIndicatorViewType;
            char c = 65535;
            int i = 3;
            int i2 = 2;
            switch (str.hashCode()) {
                case -2105391913:
                    if (str.equals("type_my_position")) {
                        c = 7;
                        break;
                    }
                    break;
                case -1945048291:
                    if (str.equals("type_press_long")) {
                        c = 6;
                        break;
                    }
                    break;
                case -1472116849:
                    if (str.equals("type_inner_detail_page")) {
                        c = 2;
                        break;
                    }
                    break;
                case -345707834:
                    if (str.equals("type_call_taxi_by_server")) {
                        c = 4;
                        break;
                    }
                    break;
                case -231475772:
                    if (str.equals("type_search_idq")) {
                        c = 0;
                        break;
                    }
                    break;
                case -160697349:
                    if (str.equals("type_press_short")) {
                        c = 5;
                        break;
                    }
                    break;
                case 610021948:
                    if (str.equals("type_default")) {
                        c = 3;
                        break;
                    }
                    break;
                case 2081515087:
                    if (str.equals("type_search_result")) {
                        c = 1;
                        break;
                    }
                    break;
            }
            switch (c) {
                case 0:
                case 1:
                case 2:
                case 3:
                case 4:
                    int i3 = TextUtils.equals(this.mState, ModulePoi.TIPS) ? 1 : TextUtils.equals(this.mState, "full") ? 3 : 0;
                    if (i3 > 0) {
                        LogManager.actionLogV25("P00360", "B001", new SimpleEntry(LocationInstrument.LOCATION_EXTRAS_KEY_POIID, this.mPoi.getId()), new SimpleEntry("status", Integer.valueOf(i3)), new SimpleEntry("gsid", this.mGsid));
                        break;
                    }
                    break;
                case 5:
                    if (TextUtils.equals(this.mState, ModulePoi.TIPS)) {
                        i = 2;
                    } else if (!TextUtils.equals(this.mState, "full")) {
                        i = 0;
                    }
                    if (i > 0) {
                        LogManager.actionLogV25("P00360", "B001", new SimpleEntry(LocationInstrument.LOCATION_EXTRAS_KEY_POIID, this.mPoi.getId()), new SimpleEntry("status", Integer.valueOf(i)));
                        break;
                    }
                    break;
                case 6:
                    int i4 = TextUtils.equals(this.mState, ModulePoi.TIPS) ? 1 : TextUtils.equals(this.mState, "full") ? 2 : 0;
                    if (i4 > 0) {
                        LogManager.actionLogV25("P00361", "B001", new SimpleEntry(LocationInstrument.LOCATION_EXTRAS_KEY_POIID, this.mPoi.getId()), new SimpleEntry("status", Integer.valueOf(i4)));
                        break;
                    }
                    break;
                case 7:
                    if (TextUtils.equals(this.mState, ModulePoi.TIPS)) {
                        i2 = 1;
                    } else if (!TextUtils.equals(this.mState, "full")) {
                        i2 = 0;
                    }
                    if (i2 > 0) {
                        LogManager.actionLogV25("P00362", "B001", new SimpleEntry("status", Integer.valueOf(i2)));
                        break;
                    }
                    break;
            }
            this.mOnDetailsClickListener.a();
        }
    }

    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void handleClick02Button() {
        /*
            r11 = this;
            java.lang.String r0 = r11.mIndicatorViewType
            java.lang.String r1 = "type_press_long"
            boolean r0 = android.text.TextUtils.equals(r0, r1)
            r1 = 1
            r2 = 0
            if (r0 == 0) goto L_0x0043
            java.lang.Class<com.autonavi.minimap.basemap.errorback.inter.IErrorReportStarter> r0 = com.autonavi.minimap.basemap.errorback.inter.IErrorReportStarter.class
            java.lang.Object r0 = defpackage.ank.a(r0)
            com.autonavi.minimap.basemap.errorback.inter.IErrorReportStarter r0 = (com.autonavi.minimap.basemap.errorback.inter.IErrorReportStarter) r0
            if (r0 == 0) goto L_0x0091
            boolean r3 = defpackage.cbw.a()
            if (r3 == 0) goto L_0x003d
            org.json.JSONObject r3 = new org.json.JSONObject
            r3.<init>()
            java.lang.String r4 = "key_new_feedback_type"
            r3.put(r4, r1)     // Catch:{ JSONException -> 0x002f }
            java.lang.String r4 = "address"
            java.lang.String r5 = "main"
            r3.put(r4, r5)     // Catch:{ JSONException -> 0x002f }
            goto L_0x0034
        L_0x002f:
            r3 = move-exception
            r3.printStackTrace()
            r3 = 0
        L_0x0034:
            com.autonavi.common.model.POI r4 = r11.mPoi
            r0.startAddPoi(r4, r3)
            r11.recordAddNewPoiFeedBacklog()
            goto L_0x0091
        L_0x003d:
            com.autonavi.common.model.POI r3 = r11.mPoi
            r0.startAddPOIFromXYSelectPoint(r3)
            goto L_0x0091
        L_0x0043:
            java.lang.String r0 = r11.mIndicatorViewType
            java.lang.String r3 = "type_my_position"
            boolean r0 = android.text.TextUtils.equals(r0, r3)
            if (r0 != 0) goto L_0x008e
            java.lang.String r0 = r11.mIndicatorViewType
            java.lang.String r3 = "type_call_taxi_by_server"
            boolean r0 = android.text.TextUtils.equals(r0, r3)
            if (r0 == 0) goto L_0x005a
            goto L_0x008e
        L_0x005a:
            cbu r0 = r11.mBehavior
            ely$a r3 = r0.d
            if (r3 == 0) goto L_0x0067
            ely$a r3 = r0.d
            com.autonavi.bundle.entity.common.searchpoi.SearchPoi r4 = r0.c
            r3.e(r4)
        L_0x0067:
            de.greenrobot.event.EventBus r3 = de.greenrobot.event.EventBus.getDefault()
            cbm r4 = new cbm
            r4.<init>()
            r3.post(r4)
            com.autonavi.bundle.entity.common.searchpoi.SearchPoi r3 = r0.c
            java.lang.Class<dfm> r4 = defpackage.dfm.class
            java.lang.Object r4 = defpackage.ank.a(r4)
            dfm r4 = (defpackage.dfm) r4
            if (r4 == 0) goto L_0x0082
            r4.a(r3)
        L_0x0082:
            ccg r3 = defpackage.ccg.a()
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r4 = r0.b
            com.autonavi.bundle.entity.common.searchpoi.SearchPoi r0 = r0.c
            r3.a(r4, r0, r2)
            goto L_0x0091
        L_0x008e:
            r11.startCallTaxi()
        L_0x0091:
            java.lang.String r0 = r11.mIndicatorViewType
            r3 = -1
            int r4 = r0.hashCode()
            r5 = 4
            r6 = 3
            r7 = 2
            switch(r4) {
                case -2105391913: goto L_0x00ec;
                case -1945048291: goto L_0x00e1;
                case -1472116849: goto L_0x00d6;
                case -345707834: goto L_0x00cb;
                case -231475772: goto L_0x00c0;
                case -160697349: goto L_0x00b5;
                case 610021948: goto L_0x00aa;
                case 2081515087: goto L_0x009f;
                default: goto L_0x009e;
            }
        L_0x009e:
            goto L_0x00f7
        L_0x009f:
            java.lang.String r4 = "type_search_result"
            boolean r0 = r0.equals(r4)
            if (r0 == 0) goto L_0x00f7
            r0 = 1
            goto L_0x00f8
        L_0x00aa:
            java.lang.String r4 = "type_default"
            boolean r0 = r0.equals(r4)
            if (r0 == 0) goto L_0x00f7
            r0 = 3
            goto L_0x00f8
        L_0x00b5:
            java.lang.String r4 = "type_press_short"
            boolean r0 = r0.equals(r4)
            if (r0 == 0) goto L_0x00f7
            r0 = 5
            goto L_0x00f8
        L_0x00c0:
            java.lang.String r4 = "type_search_idq"
            boolean r0 = r0.equals(r4)
            if (r0 == 0) goto L_0x00f7
            r0 = 0
            goto L_0x00f8
        L_0x00cb:
            java.lang.String r4 = "type_call_taxi_by_server"
            boolean r0 = r0.equals(r4)
            if (r0 == 0) goto L_0x00f7
            r0 = 4
            goto L_0x00f8
        L_0x00d6:
            java.lang.String r4 = "type_inner_detail_page"
            boolean r0 = r0.equals(r4)
            if (r0 == 0) goto L_0x00f7
            r0 = 2
            goto L_0x00f8
        L_0x00e1:
            java.lang.String r4 = "type_press_long"
            boolean r0 = r0.equals(r4)
            if (r0 == 0) goto L_0x00f7
            r0 = 6
            goto L_0x00f8
        L_0x00ec:
            java.lang.String r4 = "type_my_position"
            boolean r0 = r0.equals(r4)
            if (r0 == 0) goto L_0x00f7
            r0 = 7
            goto L_0x00f8
        L_0x00f7:
            r0 = -1
        L_0x00f8:
            switch(r0) {
                case 0: goto L_0x0203;
                case 1: goto L_0x0203;
                case 2: goto L_0x0203;
                case 3: goto L_0x0203;
                case 4: goto L_0x0203;
                case 5: goto L_0x01ac;
                case 6: goto L_0x0155;
                case 7: goto L_0x00fd;
                default: goto L_0x00fb;
            }
        L_0x00fb:
            goto L_0x0265
        L_0x00fd:
            java.lang.String r0 = r11.mState
            java.lang.String r3 = "tips"
            boolean r0 = android.text.TextUtils.equals(r0, r3)
            if (r0 == 0) goto L_0x010a
            r0 = 1
            goto L_0x0117
        L_0x010a:
            java.lang.String r0 = r11.mState
            java.lang.String r3 = "full"
            boolean r0 = android.text.TextUtils.equals(r0, r3)
            if (r0 == 0) goto L_0x0116
            r0 = 2
            goto L_0x0117
        L_0x0116:
            r0 = 0
        L_0x0117:
            if (r0 <= 0) goto L_0x0265
            java.lang.String r3 = "P00362"
            java.lang.String r4 = "B002"
            java.util.Map$Entry[] r5 = new java.util.Map.Entry[r6]
            java.util.AbstractMap$SimpleEntry r6 = new java.util.AbstractMap$SimpleEntry
            java.lang.String r8 = "poiid"
            com.autonavi.common.model.POI r9 = r11.mPoi
            java.lang.String r9 = r9.getId()
            r6.<init>(r8, r9)
            r5[r2] = r6
            java.util.AbstractMap$SimpleEntry r2 = new java.util.AbstractMap$SimpleEntry
            java.lang.String r6 = "text"
            com.autonavi.map.widget.DrawableCenterTextView r8 = r11.m02Button
            java.lang.CharSequence r8 = r8.getText()
            java.lang.String r8 = r8.toString()
            r2.<init>(r6, r8)
            r5[r1] = r2
            java.util.AbstractMap$SimpleEntry r1 = new java.util.AbstractMap$SimpleEntry
            java.lang.String r2 = "status"
            java.lang.Integer r0 = java.lang.Integer.valueOf(r0)
            r1.<init>(r2, r0)
            r5[r7] = r1
            com.amap.bundle.statistics.LogManager.actionLogV25(r3, r4, r5)
            goto L_0x0265
        L_0x0155:
            java.lang.String r0 = r11.mState
            java.lang.String r3 = "tips"
            boolean r0 = android.text.TextUtils.equals(r0, r3)
            if (r0 == 0) goto L_0x0162
            r0 = 1
            goto L_0x016f
        L_0x0162:
            java.lang.String r0 = r11.mState
            java.lang.String r3 = "full"
            boolean r0 = android.text.TextUtils.equals(r0, r3)
            if (r0 == 0) goto L_0x016e
            r0 = 2
            goto L_0x016f
        L_0x016e:
            r0 = 0
        L_0x016f:
            if (r0 <= 0) goto L_0x0265
            java.lang.String r3 = "P00361"
            java.lang.String r4 = "B002"
            java.util.Map$Entry[] r5 = new java.util.Map.Entry[r6]
            java.util.AbstractMap$SimpleEntry r6 = new java.util.AbstractMap$SimpleEntry
            java.lang.String r8 = "poiid"
            com.autonavi.common.model.POI r9 = r11.mPoi
            java.lang.String r9 = r9.getId()
            r6.<init>(r8, r9)
            r5[r2] = r6
            java.util.AbstractMap$SimpleEntry r2 = new java.util.AbstractMap$SimpleEntry
            java.lang.String r6 = "text"
            com.autonavi.map.widget.DrawableCenterTextView r8 = r11.m02Button
            java.lang.CharSequence r8 = r8.getText()
            java.lang.String r8 = r8.toString()
            r2.<init>(r6, r8)
            r5[r1] = r2
            java.util.AbstractMap$SimpleEntry r1 = new java.util.AbstractMap$SimpleEntry
            java.lang.String r2 = "status"
            java.lang.Integer r0 = java.lang.Integer.valueOf(r0)
            r1.<init>(r2, r0)
            r5[r7] = r1
            com.amap.bundle.statistics.LogManager.actionLogV25(r3, r4, r5)
            return
        L_0x01ac:
            java.lang.String r0 = r11.mState
            java.lang.String r3 = "tips"
            boolean r0 = android.text.TextUtils.equals(r0, r3)
            if (r0 == 0) goto L_0x01b9
            r0 = 2
            goto L_0x01c6
        L_0x01b9:
            java.lang.String r0 = r11.mState
            java.lang.String r3 = "full"
            boolean r0 = android.text.TextUtils.equals(r0, r3)
            if (r0 == 0) goto L_0x01c5
            r0 = 3
            goto L_0x01c6
        L_0x01c5:
            r0 = 0
        L_0x01c6:
            if (r0 <= 0) goto L_0x0265
            java.lang.String r3 = "P00360"
            java.lang.String r4 = "B002"
            java.util.Map$Entry[] r5 = new java.util.Map.Entry[r6]
            java.util.AbstractMap$SimpleEntry r6 = new java.util.AbstractMap$SimpleEntry
            java.lang.String r8 = "poiid"
            com.autonavi.common.model.POI r9 = r11.mPoi
            java.lang.String r9 = r9.getId()
            r6.<init>(r8, r9)
            r5[r2] = r6
            java.util.AbstractMap$SimpleEntry r2 = new java.util.AbstractMap$SimpleEntry
            java.lang.String r6 = "text"
            com.autonavi.map.widget.DrawableCenterTextView r8 = r11.m02Button
            java.lang.CharSequence r8 = r8.getText()
            java.lang.String r8 = r8.toString()
            r2.<init>(r6, r8)
            r5[r1] = r2
            java.util.AbstractMap$SimpleEntry r1 = new java.util.AbstractMap$SimpleEntry
            java.lang.String r2 = "status"
            java.lang.Integer r0 = java.lang.Integer.valueOf(r0)
            r1.<init>(r2, r0)
            r5[r7] = r1
            com.amap.bundle.statistics.LogManager.actionLogV25(r3, r4, r5)
            return
        L_0x0203:
            java.lang.String r0 = r11.mState
            java.lang.String r3 = "tips"
            boolean r0 = android.text.TextUtils.equals(r0, r3)
            if (r0 == 0) goto L_0x0210
            r0 = 1
            goto L_0x021d
        L_0x0210:
            java.lang.String r0 = r11.mState
            java.lang.String r3 = "full"
            boolean r0 = android.text.TextUtils.equals(r0, r3)
            if (r0 == 0) goto L_0x021c
            r0 = 3
            goto L_0x021d
        L_0x021c:
            r0 = 0
        L_0x021d:
            if (r0 <= 0) goto L_0x0265
            java.lang.String r3 = "P00360"
            java.lang.String r4 = "B002"
            java.util.Map$Entry[] r5 = new java.util.Map.Entry[r5]
            java.util.AbstractMap$SimpleEntry r8 = new java.util.AbstractMap$SimpleEntry
            java.lang.String r9 = "poiid"
            com.autonavi.common.model.POI r10 = r11.mPoi
            java.lang.String r10 = r10.getId()
            r8.<init>(r9, r10)
            r5[r2] = r8
            java.util.AbstractMap$SimpleEntry r2 = new java.util.AbstractMap$SimpleEntry
            java.lang.String r8 = "status"
            java.lang.Integer r0 = java.lang.Integer.valueOf(r0)
            r2.<init>(r8, r0)
            r5[r1] = r2
            java.util.AbstractMap$SimpleEntry r0 = new java.util.AbstractMap$SimpleEntry
            java.lang.String r1 = "text"
            com.autonavi.map.widget.DrawableCenterTextView r2 = r11.m02Button
            java.lang.CharSequence r2 = r2.getText()
            java.lang.String r2 = r2.toString()
            r0.<init>(r1, r2)
            r5[r7] = r0
            java.util.AbstractMap$SimpleEntry r0 = new java.util.AbstractMap$SimpleEntry
            java.lang.String r1 = "gsid"
            java.lang.String r2 = r11.mGsid
            r0.<init>(r1, r2)
            r5[r6] = r0
            com.amap.bundle.statistics.LogManager.actionLogV25(r3, r4, r5)
            return
        L_0x0265:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.map.search.tip.indicator.SearchPoiIndicatorView.handleClick02Button():void");
    }

    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void handleClick03Button() {
        /*
            r10 = this;
            java.lang.String r0 = r10.mIndicatorViewType
            java.lang.String r1 = "type_my_position"
            boolean r0 = android.text.TextUtils.equals(r0, r1)
            r1 = 0
            if (r0 == 0) goto L_0x0023
            cbu r0 = r10.mBehavior
            com.autonavi.bundle.entity.common.searchpoi.SearchPoi r2 = r0.c
            java.lang.String r3 = "bundle_key_poi_start"
            java.lang.String r4 = ""
            defpackage.cbp.a(r3, r2, r4)
            ccg r2 = defpackage.ccg.a()
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r3 = r0.b
            com.autonavi.bundle.entity.common.searchpoi.SearchPoi r0 = r0.c
            r2.a(r3, r0, r1)
            goto L_0x0051
        L_0x0023:
            cbu r0 = r10.mBehavior
            de.greenrobot.event.EventBus r2 = de.greenrobot.event.EventBus.getDefault()
            cbm r3 = new cbm
            r3.<init>()
            r2.post(r3)
            com.autonavi.bundle.entity.common.searchpoi.SearchPoi r2 = r0.c
            java.lang.String r3 = "tips"
            java.lang.String r4 = "bundle_key_poi_end"
            defpackage.cbp.a(r4, r2, r3)
            ccg r2 = defpackage.ccg.a()
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r3 = r0.b
            com.autonavi.bundle.entity.common.searchpoi.SearchPoi r4 = r0.c
            r2.a(r3, r4, r1)
            ely$a r2 = r0.d
            if (r2 == 0) goto L_0x0051
            ely$a r2 = r0.d
            com.autonavi.bundle.entity.common.searchpoi.SearchPoi r0 = r0.c
            r2.d(r0)
        L_0x0051:
            java.lang.String r0 = r10.mIndicatorViewType
            r2 = -1
            int r3 = r0.hashCode()
            r4 = 3
            r5 = 2
            r6 = 1
            switch(r3) {
                case -2105391913: goto L_0x00ac;
                case -1945048291: goto L_0x00a1;
                case -1472116849: goto L_0x0096;
                case -345707834: goto L_0x008b;
                case -231475772: goto L_0x0080;
                case -160697349: goto L_0x0075;
                case 610021948: goto L_0x006a;
                case 2081515087: goto L_0x005f;
                default: goto L_0x005e;
            }
        L_0x005e:
            goto L_0x00b7
        L_0x005f:
            java.lang.String r3 = "type_search_result"
            boolean r0 = r0.equals(r3)
            if (r0 == 0) goto L_0x00b7
            r0 = 1
            goto L_0x00b8
        L_0x006a:
            java.lang.String r3 = "type_default"
            boolean r0 = r0.equals(r3)
            if (r0 == 0) goto L_0x00b7
            r0 = 3
            goto L_0x00b8
        L_0x0075:
            java.lang.String r3 = "type_press_short"
            boolean r0 = r0.equals(r3)
            if (r0 == 0) goto L_0x00b7
            r0 = 5
            goto L_0x00b8
        L_0x0080:
            java.lang.String r3 = "type_search_idq"
            boolean r0 = r0.equals(r3)
            if (r0 == 0) goto L_0x00b7
            r0 = 0
            goto L_0x00b8
        L_0x008b:
            java.lang.String r3 = "type_call_taxi_by_server"
            boolean r0 = r0.equals(r3)
            if (r0 == 0) goto L_0x00b7
            r0 = 4
            goto L_0x00b8
        L_0x0096:
            java.lang.String r3 = "type_inner_detail_page"
            boolean r0 = r0.equals(r3)
            if (r0 == 0) goto L_0x00b7
            r0 = 2
            goto L_0x00b8
        L_0x00a1:
            java.lang.String r3 = "type_press_long"
            boolean r0 = r0.equals(r3)
            if (r0 == 0) goto L_0x00b7
            r0 = 6
            goto L_0x00b8
        L_0x00ac:
            java.lang.String r3 = "type_my_position"
            boolean r0 = r0.equals(r3)
            if (r0 == 0) goto L_0x00b7
            r0 = 7
            goto L_0x00b8
        L_0x00b7:
            r0 = -1
        L_0x00b8:
            switch(r0) {
                case 0: goto L_0x0186;
                case 1: goto L_0x0186;
                case 2: goto L_0x0186;
                case 3: goto L_0x0186;
                case 4: goto L_0x0186;
                case 5: goto L_0x0144;
                case 6: goto L_0x0101;
                case 7: goto L_0x00bd;
                default: goto L_0x00bb;
            }
        L_0x00bb:
            goto L_0x01d4
        L_0x00bd:
            java.lang.String r0 = r10.mState
            java.lang.String r2 = "tips"
            boolean r0 = android.text.TextUtils.equals(r0, r2)
            if (r0 == 0) goto L_0x00ca
            r0 = 1
            goto L_0x00d7
        L_0x00ca:
            java.lang.String r0 = r10.mState
            java.lang.String r2 = "full"
            boolean r0 = android.text.TextUtils.equals(r0, r2)
            if (r0 == 0) goto L_0x00d6
            r0 = 2
            goto L_0x00d7
        L_0x00d6:
            r0 = 0
        L_0x00d7:
            if (r0 <= 0) goto L_0x01d4
            java.lang.String r2 = "P00362"
            java.lang.String r3 = "B003"
            java.util.Map$Entry[] r4 = new java.util.Map.Entry[r5]
            java.util.AbstractMap$SimpleEntry r5 = new java.util.AbstractMap$SimpleEntry
            java.lang.String r7 = "poiid"
            com.autonavi.common.model.POI r8 = r10.mPoi
            java.lang.String r8 = r8.getId()
            r5.<init>(r7, r8)
            r4[r1] = r5
            java.util.AbstractMap$SimpleEntry r1 = new java.util.AbstractMap$SimpleEntry
            java.lang.String r5 = "status"
            java.lang.Integer r0 = java.lang.Integer.valueOf(r0)
            r1.<init>(r5, r0)
            r4[r6] = r1
            com.amap.bundle.statistics.LogManager.actionLogV25(r2, r3, r4)
            goto L_0x01d4
        L_0x0101:
            java.lang.String r0 = r10.mState
            java.lang.String r2 = "tips"
            boolean r0 = android.text.TextUtils.equals(r0, r2)
            if (r0 == 0) goto L_0x010e
            r0 = 1
            goto L_0x011b
        L_0x010e:
            java.lang.String r0 = r10.mState
            java.lang.String r2 = "full"
            boolean r0 = android.text.TextUtils.equals(r0, r2)
            if (r0 == 0) goto L_0x011a
            r0 = 2
            goto L_0x011b
        L_0x011a:
            r0 = 0
        L_0x011b:
            if (r0 <= 0) goto L_0x01d4
            java.lang.String r2 = "P00361"
            java.lang.String r3 = "B003"
            java.util.Map$Entry[] r4 = new java.util.Map.Entry[r5]
            java.util.AbstractMap$SimpleEntry r5 = new java.util.AbstractMap$SimpleEntry
            java.lang.String r7 = "poiid"
            com.autonavi.common.model.POI r8 = r10.mPoi
            java.lang.String r8 = r8.getId()
            r5.<init>(r7, r8)
            r4[r1] = r5
            java.util.AbstractMap$SimpleEntry r1 = new java.util.AbstractMap$SimpleEntry
            java.lang.String r5 = "status"
            java.lang.Integer r0 = java.lang.Integer.valueOf(r0)
            r1.<init>(r5, r0)
            r4[r6] = r1
            com.amap.bundle.statistics.LogManager.actionLogV25(r2, r3, r4)
            return
        L_0x0144:
            java.lang.String r0 = r10.mState
            java.lang.String r2 = "tips"
            boolean r0 = android.text.TextUtils.equals(r0, r2)
            if (r0 == 0) goto L_0x0151
            r4 = 2
            goto L_0x015d
        L_0x0151:
            java.lang.String r0 = r10.mState
            java.lang.String r2 = "full"
            boolean r0 = android.text.TextUtils.equals(r0, r2)
            if (r0 == 0) goto L_0x015c
            goto L_0x015d
        L_0x015c:
            r4 = 0
        L_0x015d:
            if (r4 <= 0) goto L_0x01d4
            java.lang.String r0 = "P00360"
            java.lang.String r2 = "B003"
            java.util.Map$Entry[] r3 = new java.util.Map.Entry[r5]
            java.util.AbstractMap$SimpleEntry r5 = new java.util.AbstractMap$SimpleEntry
            java.lang.String r7 = "poiid"
            com.autonavi.common.model.POI r8 = r10.mPoi
            java.lang.String r8 = r8.getId()
            r5.<init>(r7, r8)
            r3[r1] = r5
            java.util.AbstractMap$SimpleEntry r1 = new java.util.AbstractMap$SimpleEntry
            java.lang.String r5 = "status"
            java.lang.Integer r4 = java.lang.Integer.valueOf(r4)
            r1.<init>(r5, r4)
            r3[r6] = r1
            com.amap.bundle.statistics.LogManager.actionLogV25(r0, r2, r3)
            return
        L_0x0186:
            java.lang.String r0 = r10.mState
            java.lang.String r2 = "tips"
            boolean r0 = android.text.TextUtils.equals(r0, r2)
            if (r0 == 0) goto L_0x0193
            r0 = 1
            goto L_0x01a0
        L_0x0193:
            java.lang.String r0 = r10.mState
            java.lang.String r2 = "full"
            boolean r0 = android.text.TextUtils.equals(r0, r2)
            if (r0 == 0) goto L_0x019f
            r0 = 3
            goto L_0x01a0
        L_0x019f:
            r0 = 0
        L_0x01a0:
            if (r0 <= 0) goto L_0x01d4
            java.lang.String r2 = "P00360"
            java.lang.String r3 = "B003"
            java.util.Map$Entry[] r4 = new java.util.Map.Entry[r4]
            java.util.AbstractMap$SimpleEntry r7 = new java.util.AbstractMap$SimpleEntry
            java.lang.String r8 = "poiid"
            com.autonavi.common.model.POI r9 = r10.mPoi
            java.lang.String r9 = r9.getId()
            r7.<init>(r8, r9)
            r4[r1] = r7
            java.util.AbstractMap$SimpleEntry r1 = new java.util.AbstractMap$SimpleEntry
            java.lang.String r7 = "status"
            java.lang.Integer r0 = java.lang.Integer.valueOf(r0)
            r1.<init>(r7, r0)
            r4[r6] = r1
            java.util.AbstractMap$SimpleEntry r0 = new java.util.AbstractMap$SimpleEntry
            java.lang.String r1 = "gsid"
            java.lang.String r6 = r10.mGsid
            r0.<init>(r1, r6)
            r4[r5] = r0
            com.amap.bundle.statistics.LogManager.actionLogV25(r2, r3, r4)
            return
        L_0x01d4:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.map.search.tip.indicator.SearchPoiIndicatorView.handleClick03Button():void");
    }

    private void startCallTaxi() {
        PageBundle pageBundle = new PageBundle();
        pageBundle.putObject("bundle_key_route_type", RouteType.TAXI);
        if (TextUtils.equals(this.mIndicatorViewType, "type_call_taxi_by_server")) {
            pageBundle.putObject("bundle_key_poi_end", getPoi());
        }
        bax bax = (bax) defpackage.esb.a.a.a(bax.class);
        if (bax != null) {
            bax.b(pageBundle);
        }
        LogManager.actionLogV2("P00001", LogConstant.MAIN_TAXI_CLICK);
    }

    public POI getPoi() {
        return this.mPoi;
    }

    public void updatePoiData(InfoliteResult infoliteResult, SearchPoi searchPoi, int i) {
        this.mPoi = searchPoi.as(GpsPOI.class);
        if (!(infoliteResult == null || infoliteResult.searchInfo == null || infoliteResult.searchInfo.a == null)) {
            this.mGsid = infoliteResult.searchInfo.a.R;
        }
        this.mBehavior.a(infoliteResult, searchPoi, i);
        if (TextUtils.equals(this.mIndicatorViewType, "type_my_position")) {
            updateCallTaxiVisibility();
        }
        processTemplate();
    }

    private void processTemplate() {
        if (this.mPoi != null) {
            List<PoiLayoutTemplate> templateData = ((SearchPoi) this.mPoi.as(SearchPoi.class)).getTemplateData();
            if (templateData != null && templateData.size() != 0) {
                setChildViewGoneTemp();
                for (PoiLayoutTemplate poiLayoutTemplate : templateData) {
                    new StringBuilder("template.getId() == ").append(poiLayoutTemplate.getId());
                    new StringBuilder("template.getName() == ").append(poiLayoutTemplate.getName());
                    new StringBuilder("template.getType() == ").append(poiLayoutTemplate.getType());
                    new StringBuilder("template.getValue() == ").append(poiLayoutTemplate.getValue());
                    new StringBuilder("template.isEnable() == ").append(poiLayoutTemplate.isEnable());
                    new StringBuilder("template.isShown() == ").append(poiLayoutTemplate.isShown());
                    cbq.a(1002, (View) this.mDetailsText, poiLayoutTemplate);
                    cbq.a(1005, (View) this.m02Button, poiLayoutTemplate);
                    cbq.a(2003, (View) this.m03Button, poiLayoutTemplate);
                    if (1005 == poiLayoutTemplate.getId() && (poiLayoutTemplate instanceof PoiButtonTemplate) && FunctionSupportConfiger.TAXI_TAG.equals(((PoiButtonTemplate) poiLayoutTemplate).getAction())) {
                        this.mIndicatorViewType = "type_call_taxi_by_server";
                        this.m02Button.setText(R.string.poi_indicator_call_taxi);
                        this.m02Button.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.poi_indicator_call_taxi), null, null, null);
                    }
                }
                this.mDetailsImage.setVisibility(this.mDetailsText.getVisibility());
                cbq.a((ViewGroup) this.m01Layout);
                cbq.a((ViewGroup) this.m02Layout);
                cbq.a((ViewGroup) this.m03Layout);
            }
        }
    }

    private void setChildViewGoneTemp() {
        cbq.a(this.m01Layout, false);
        cbq.a(this.m02Layout, false);
        cbq.a(this.m03Layout, false);
    }

    private void updateCallTaxiVisibility() {
        if ((this.mPoi == null || this.mPoi.getPoint().getAdCode() == 0) ? false : isTaxiOpen()) {
            this.m02Layout.setVisibility(0);
        } else {
            this.m02Layout.setVisibility(8);
        }
    }

    private boolean isTaxiOpen() {
        String a2 = lo.a().a((String) DIYMainMapPresenter.DIY_MAIN_MAP_CONFIG_MODULE_NAME);
        return !TextUtils.isEmpty(a2) && a2.contains("\"cab\"");
    }

    public JSONObject getIndicatorInfo() {
        if (TextUtils.isEmpty(this.mIndicatorViewType)) {
            return null;
        }
        JSONObject jSONObject = new JSONObject();
        JSONObject jSONObject2 = new JSONObject();
        try {
            jSONObject.put("indicatorViewType", this.mIndicatorViewType);
            if (this.m01Layout.getVisibility() == 0 && this.mDetailsText.getVisibility() == 0) {
                jSONObject2.put("1002", "VISIBLE");
            } else {
                jSONObject2.put("1002", "GONE");
            }
            if (this.m02Layout.getVisibility() == 0 && this.m02Button.getVisibility() == 0) {
                jSONObject2.put("1005", "VISIBLE");
            } else {
                jSONObject2.put("1005", "GONE");
            }
            if (this.m03Layout.getVisibility() == 0 && this.m03Button.getVisibility() == 0) {
                jSONObject2.put("2003", "VISIBLE");
            } else {
                jSONObject2.put("2003", "GONE");
            }
            jSONObject.put("visible", jSONObject2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jSONObject;
    }

    public void onClick(View view) {
        if (this.mPoi == null || bnp.a()) {
            return;
        }
        if (view == this.m02Layout) {
            handleClick02Button();
            return;
        }
        if (view == this.m03Layout) {
            handleClick03Button();
        }
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        boolean a2 = ekt.a((View) this.m01Layout, motionEvent);
        boolean a3 = ekt.a((View) this.m02Layout, motionEvent);
        boolean a4 = ekt.a((View) this.m03Layout, motionEvent);
        if (a2 || a3 || a4) {
            return super.onTouchEvent(motionEvent);
        }
        return true;
    }

    public void setOnDetailsClickListener(a aVar) {
        this.mOnDetailsClickListener = aVar;
    }

    private void recordAddNewPoiFeedBacklog() {
        LogManager.actionLogV2("P00361", "B004");
    }
}
