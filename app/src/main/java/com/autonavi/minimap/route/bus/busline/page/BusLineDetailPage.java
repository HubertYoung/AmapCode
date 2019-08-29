package com.autonavi.minimap.route.bus.busline.page;

import android.content.Context;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.text.TextUtils.TruncateAt;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewStub;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import com.amap.bundle.statistics.LogManager;
import com.amap.bundle.voiceservice.dispatch.IVoiceCmdResponder;
import com.autonavi.annotation.PageAction;
import com.autonavi.map.core.LocationMode.LocationNetworkOnly;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.map.fragmentcontainer.page.IPresenter;
import com.autonavi.map.widget.FlowLayout;
import com.autonavi.minimap.R;
import com.autonavi.minimap.route.bus.model.Bus;
import com.autonavi.minimap.route.common.view.ListViewOnScrollListener;
import com.autonavi.minimap.route.common.view.RouteArrivingBusInfoView;
import com.autonavi.minimap.route.common.view.RouteFrameFooterView;
import com.autonavi.minimap.route.net.module.RTBusLocation;
import com.autonavi.widget.ui.TitleBar;

@PageAction("amap.extra.route.busline_detail")
public class BusLineDetailPage extends AbstractBasePage<dut> implements OnClickListener, IVoiceCmdResponder, LocationNetworkOnly {
    public View a;
    public ListView b;
    public BusDetailListAdapter c;
    public RouteFrameFooterView d;
    private TitleBar e;
    private TextView f;
    private TextView g;
    private TextView h;
    private TextView i;
    private View j;
    private TextView k;
    private TextView l;
    private TextView m;
    private ViewStub n;
    /* access modifiers changed from: private */
    public View o;

    public class BusDetailListAdapter extends BaseAdapter implements OnTouchListener {
        private int mBlueColor;
        private Bus mCurBus;
        private int mExpandedPosition = -1;
        private int mGrayColor;
        private LayoutInflater mLayoutInflater;
        private boolean mLoadingRealTime;
        private RTBusLocation mRealTimeBusline;
        private String[] mStationIdArray;
        private String[] mStationNameArray;
        private int[] mStationStatusArray;
        private int mStatus = -1;
        private String mTagNear;
        private String near1KmStationName;
        private int normalHeight;

        public long getItemId(int i) {
            return (long) i;
        }

        public boolean onTouch(View view, MotionEvent motionEvent) {
            return true;
        }

        BusDetailListAdapter(Bus bus) {
            this.mCurBus = bus;
            this.mStationNameArray = bus.stations;
            this.mStationIdArray = bus.stationIds;
            this.mStationStatusArray = bus.stationstatus;
            this.mLayoutInflater = LayoutInflater.from(BusLineDetailPage.this.getContext());
            this.normalHeight = agn.a(BusLineDetailPage.this.getContext(), 20.0f);
            this.mGrayColor = ContextCompat.getColor(BusLineDetailPage.this.getContext(), R.color.f_c_3);
            this.mBlueColor = ContextCompat.getColor(BusLineDetailPage.this.getContext(), R.color.f_c_6);
            this.mTagNear = BusLineDetailPage.this.getString(R.string.busline_close_to_me);
        }

        public void setLoadingRealTime(boolean z) {
            this.mLoadingRealTime = z;
        }

        public void setRealTimeBusline(RTBusLocation rTBusLocation) {
            this.mRealTimeBusline = rTBusLocation;
        }

        public void setRealTimeStatus(int i) {
            this.mStatus = i;
        }

        /* access modifiers changed from: 0000 */
        public void setExpandedPosition(int i) {
            this.mExpandedPosition = i;
        }

        public void setNear1KmStationName(String str) {
            this.near1KmStationName = str;
        }

        /* access modifiers changed from: 0000 */
        public void setDatas(Bus bus) {
            this.mCurBus = bus;
            this.mStationNameArray = bus.stations;
            this.mStationIdArray = bus.stationIds;
            this.mStationStatusArray = bus.stationstatus;
        }

        public int getCount() {
            if (this.mStationNameArray == null) {
                return 0;
            }
            return this.mStationNameArray.length;
        }

        public Object getItem(int i) {
            return this.mStationNameArray[i];
        }

        /* JADX WARNING: Removed duplicated region for block: B:47:0x01e1  */
        /* JADX WARNING: Removed duplicated region for block: B:48:0x01e5  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public android.view.View getView(int r13, android.view.View r14, android.view.ViewGroup r15) {
            /*
                r12 = this;
                r15 = 3
                r0 = 0
                r1 = 1
                r2 = 0
                if (r14 != 0) goto L_0x00b7
                android.view.LayoutInflater r14 = r12.mLayoutInflater
                int r3 = com.autonavi.minimap.R.layout.busline_detail_listview_item
                android.view.View r14 = r14.inflate(r3, r0)
                com.autonavi.minimap.route.bus.busline.page.BusLineDetailPage$a r3 = new com.autonavi.minimap.route.bus.busline.page.BusLineDetailPage$a
                r3.<init>(r2)
                int r4 = com.autonavi.minimap.R.id.busline_detail_station_icon
                android.view.View r4 = r14.findViewById(r4)
                android.widget.ImageView r4 = (android.widget.ImageView) r4
                r3.a = r4
                int r4 = com.autonavi.minimap.R.id.busline_detail_line_icon
                android.view.View r4 = r14.findViewById(r4)
                android.widget.ImageView r4 = (android.widget.ImageView) r4
                r3.b = r4
                int r4 = com.autonavi.minimap.R.id.busline_detail_refresh
                android.view.View r4 = r14.findViewById(r4)
                r3.c = r4
                android.view.View r4 = r3.c
                com.autonavi.minimap.route.bus.busline.page.BusLineDetailPage r5 = com.autonavi.minimap.route.bus.busline.page.BusLineDetailPage.this
                r4.setOnClickListener(r5)
                int r4 = com.autonavi.minimap.R.id.busline_detail_arriving_bus
                android.view.View r4 = r14.findViewById(r4)
                android.widget.TextView r4 = (android.widget.TextView) r4
                r3.d = r4
                com.autonavi.minimap.route.common.view.RouteArrivingBusInfoView[] r4 = new com.autonavi.minimap.route.common.view.RouteArrivingBusInfoView[r15]
                r3.e = r4
                com.autonavi.minimap.route.common.view.RouteArrivingBusInfoView[] r4 = r3.e
                int r5 = com.autonavi.minimap.R.id.arriving_bus_info_item_1
                android.view.View r5 = r14.findViewById(r5)
                com.autonavi.minimap.route.common.view.RouteArrivingBusInfoView r5 = (com.autonavi.minimap.route.common.view.RouteArrivingBusInfoView) r5
                r4[r2] = r5
                com.autonavi.minimap.route.common.view.RouteArrivingBusInfoView[] r4 = r3.e
                int r5 = com.autonavi.minimap.R.id.arriving_bus_info_item_2
                android.view.View r5 = r14.findViewById(r5)
                com.autonavi.minimap.route.common.view.RouteArrivingBusInfoView r5 = (com.autonavi.minimap.route.common.view.RouteArrivingBusInfoView) r5
                r4[r1] = r5
                com.autonavi.minimap.route.common.view.RouteArrivingBusInfoView[] r4 = r3.e
                r5 = 2
                int r6 = com.autonavi.minimap.R.id.arriving_bus_info_item_3
                android.view.View r6 = r14.findViewById(r6)
                com.autonavi.minimap.route.common.view.RouteArrivingBusInfoView r6 = (com.autonavi.minimap.route.common.view.RouteArrivingBusInfoView) r6
                r4[r5] = r6
                int r4 = com.autonavi.minimap.R.id.busline_detail_station_name
                android.view.View r4 = r14.findViewById(r4)
                android.widget.TextView r4 = (android.widget.TextView) r4
                r3.f = r4
                int r4 = com.autonavi.minimap.R.id.busline_detail_station_container
                android.view.View r4 = r14.findViewById(r4)
                com.autonavi.map.widget.FlowLayout r4 = (com.autonavi.map.widget.FlowLayout) r4
                r3.g = r4
                int r4 = com.autonavi.minimap.R.id.busline_detail_expand_icon
                android.view.View r4 = r14.findViewById(r4)
                android.widget.ImageView r4 = (android.widget.ImageView) r4
                r3.h = r4
                android.widget.ImageView r4 = r3.h
                com.autonavi.minimap.route.bus.busline.page.BusLineDetailPage r5 = com.autonavi.minimap.route.bus.busline.page.BusLineDetailPage.this
                r4.setOnClickListener(r5)
                int r4 = com.autonavi.minimap.R.id.busline_detail_info_container
                android.view.View r4 = r14.findViewById(r4)
                r3.i = r4
                int r4 = com.autonavi.minimap.R.id.busline_station
                android.view.View r4 = r14.findViewById(r4)
                r3.j = r4
                android.view.View r4 = r3.j
                com.autonavi.minimap.route.bus.busline.page.BusLineDetailPage r5 = com.autonavi.minimap.route.bus.busline.page.BusLineDetailPage.this
                r4.setOnClickListener(r5)
                int r4 = com.autonavi.minimap.R.id.real_time_arriving_bus_details
                android.view.View r4 = r14.findViewById(r4)
                int r5 = com.autonavi.minimap.R.drawable.busline_detail_info_blue_bg
                r4.setBackgroundResource(r5)
                r14.setOnTouchListener(r12)
                r14.setTag(r3)
                goto L_0x00bd
            L_0x00b7:
                java.lang.Object r3 = r14.getTag()
                com.autonavi.minimap.route.bus.busline.page.BusLineDetailPage$a r3 = (com.autonavi.minimap.route.bus.busline.page.BusLineDetailPage.a) r3
            L_0x00bd:
                android.view.View r4 = r3.j
                java.lang.Integer r5 = java.lang.Integer.valueOf(r13)
                r4.setTag(r5)
                java.lang.String[] r4 = r12.mStationNameArray
                java.lang.String r5 = ""
                java.lang.Object r4 = defpackage.ebm.a(r4, r13, r5)
                java.lang.String r4 = (java.lang.String) r4
                java.lang.String r5 = r12.near1KmStationName
                boolean r5 = r4.equals(r5)
                if (r5 == 0) goto L_0x00e0
                android.widget.ImageView r6 = r3.a
                int r7 = com.autonavi.minimap.R.drawable.direction_bus_list_green
                r6.setImageResource(r7)
                goto L_0x00e7
            L_0x00e0:
                android.widget.ImageView r6 = r3.a
                int r7 = com.autonavi.minimap.R.drawable.direction_bus_list_blue
                r6.setImageResource(r7)
            L_0x00e7:
                android.text.SpannableStringBuilder r6 = new android.text.SpannableStringBuilder
                r6.<init>(r4)
                if (r5 == 0) goto L_0x00f3
                java.lang.String r7 = r12.mTagNear
                r6.append(r7)
            L_0x00f3:
                int[] r7 = r12.mStationStatusArray
                if (r7 == 0) goto L_0x00ff
                if (r13 < 0) goto L_0x00ff
                int r8 = r7.length
                if (r13 >= r8) goto L_0x00ff
                r7 = r7[r13]
                goto L_0x0100
            L_0x00ff:
                r7 = 1
            L_0x0100:
                r8 = 33
                if (r7 != 0) goto L_0x011e
                com.autonavi.minimap.route.bus.busline.page.BusLineDetailPage r15 = com.autonavi.minimap.route.bus.busline.page.BusLineDetailPage.this
                int r5 = com.autonavi.minimap.R.string.busline_temp_outage
                java.lang.String r15 = r15.getString(r5)
                r6.append(r15)
                android.text.style.ForegroundColorSpan r15 = new android.text.style.ForegroundColorSpan
                int r5 = r12.mGrayColor
                r15.<init>(r5)
                int r5 = r6.length()
                r6.setSpan(r15, r2, r5, r8)
                goto L_0x0155
            L_0x011e:
                if (r7 != r15) goto L_0x013a
                com.autonavi.minimap.route.bus.busline.page.BusLineDetailPage r15 = com.autonavi.minimap.route.bus.busline.page.BusLineDetailPage.this
                int r5 = com.autonavi.minimap.R.string.busline_defer_open
                java.lang.String r15 = r15.getString(r5)
                r6.append(r15)
                android.text.style.ForegroundColorSpan r15 = new android.text.style.ForegroundColorSpan
                int r5 = r12.mGrayColor
                r15.<init>(r5)
                int r5 = r6.length()
                r6.setSpan(r15, r2, r5, r8)
                goto L_0x0155
            L_0x013a:
                if (r5 == 0) goto L_0x0155
                int r15 = r4.length()
                int r5 = r4.length()
                java.lang.String r7 = r12.mTagNear
                int r7 = r7.length()
                int r5 = r5 + r7
                android.text.style.ForegroundColorSpan r7 = new android.text.style.ForegroundColorSpan
                int r9 = r12.mBlueColor
                r7.<init>(r9)
                r6.setSpan(r7, r15, r5, r8)
            L_0x0155:
                int r15 = r12.mExpandedPosition
                if (r13 != r15) goto L_0x0165
                android.text.style.StyleSpan r15 = new android.text.style.StyleSpan
                r15.<init>(r1)
                int r5 = r6.length()
                r6.setSpan(r15, r2, r5, r8)
            L_0x0165:
                android.widget.TextView r15 = r3.f
                r15.setText(r6)
                java.lang.String[] r15 = r12.mStationIdArray
                java.lang.String r5 = ""
                java.lang.Object r15 = defpackage.ebm.a(r15, r13, r5)
                java.lang.String r15 = (java.lang.String) r15
                com.autonavi.minimap.route.bus.model.Bus r5 = r12.mCurBus
                com.autonavi.minimap.route.bus.model.BusSubwayInfo r5 = r5.subwayInfo
                java.util.ArrayList r15 = r5.getSubwayInfoByName(r4, r15)
                com.autonavi.map.widget.FlowLayout r4 = r3.g
                r4.removeAllViews()
                com.autonavi.map.widget.FlowLayout r4 = r3.g
                android.widget.TextView r5 = r3.f
                r4.addView(r5)
                if (r15 == 0) goto L_0x020b
                int r4 = r15.size()
                if (r4 <= 0) goto L_0x020b
                int r4 = r15.size()
                r5 = 0
            L_0x0195:
                if (r5 >= r4) goto L_0x020b
                java.lang.Object r6 = r15.get(r5)
                com.autonavi.minimap.route.bus.model.BusSubwayStation r6 = (com.autonavi.minimap.route.bus.model.BusSubwayStation) r6
                java.lang.String r7 = r6.subwayName
                boolean r7 = android.text.TextUtils.isEmpty(r7)
                if (r7 != 0) goto L_0x0208
                android.view.LayoutInflater r7 = r12.mLayoutInflater
                int r8 = com.autonavi.minimap.R.layout.busline_station_subwayinfo
                android.view.View r7 = r7.inflate(r8, r0)
                int r8 = com.autonavi.minimap.R.id.subway_name_tv
                android.view.View r8 = r7.findViewById(r8)
                android.widget.TextView r8 = (android.widget.TextView) r8
                java.lang.String r9 = r6.subwayName
                r8.setText(r9)
                r9 = -16777216(0xffffffffff000000, float:-1.7014118E38)
                java.lang.String r10 = r6.subwayColor
                if (r10 == 0) goto L_0x01d9
                java.lang.String r10 = "#"
                java.lang.String r11 = r6.subwayColor
                java.lang.String r11 = r11.trim()
                boolean r10 = r10.equals(r11)
                if (r10 != 0) goto L_0x01d9
                java.lang.String r6 = r6.subwayColor     // Catch:{ Exception -> 0x01d5 }
                int r6 = android.graphics.Color.parseColor(r6)     // Catch:{ Exception -> 0x01d5 }
                goto L_0x01db
            L_0x01d5:
                r6 = move-exception
                r6.printStackTrace()
            L_0x01d9:
                r6 = -16777216(0xffffffffff000000, float:-1.7014118E38)
            L_0x01db:
                int r9 = android.os.Build.VERSION.SDK_INT
                r10 = 15
                if (r9 >= r10) goto L_0x01e5
                r8.setBackgroundColor(r6)
                goto L_0x0203
            L_0x01e5:
                android.graphics.drawable.GradientDrawable r9 = new android.graphics.drawable.GradientDrawable
                r9.<init>()
                r9.setShape(r2)
                com.autonavi.minimap.route.bus.busline.page.BusLineDetailPage r10 = com.autonavi.minimap.route.bus.busline.page.BusLineDetailPage.this
                android.content.Context r10 = r10.getContext()
                r11 = 1094713344(0x41400000, float:12.0)
                int r10 = defpackage.agn.a(r10, r11)
                float r10 = (float) r10
                r9.setCornerRadius(r10)
                r9.setColor(r6)
                r8.setBackgroundDrawable(r9)
            L_0x0203:
                com.autonavi.map.widget.FlowLayout r6 = r3.g
                r6.addView(r7)
            L_0x0208:
                int r5 = r5 + 1
                goto L_0x0195
            L_0x020b:
                com.autonavi.minimap.route.bus.model.Bus r15 = r12.mCurBus
                boolean r15 = r15.isRealTime
                r0 = 8
                if (r15 != 0) goto L_0x021e
                android.widget.ImageView r15 = r3.h
                r15.setVisibility(r0)
                android.view.View r15 = r3.i
                r15.setVisibility(r0)
                goto L_0x028b
            L_0x021e:
                android.view.View r15 = r3.c
                java.lang.Integer r4 = java.lang.Integer.valueOf(r13)
                r15.setTag(r4)
                android.widget.ImageView r15 = r3.h
                java.lang.Integer r4 = java.lang.Integer.valueOf(r13)
                r15.setTag(r4)
                android.widget.ImageView r15 = r3.h
                r15.setVisibility(r2)
                int r15 = r12.mExpandedPosition
                if (r13 == r15) goto L_0x0246
                android.widget.ImageView r15 = r3.h
                int r4 = com.autonavi.minimap.R.drawable.route_arrow_down
                r15.setImageResource(r4)
                android.view.View r15 = r3.i
                r15.setVisibility(r0)
                goto L_0x028b
            L_0x0246:
                android.widget.ImageView r15 = r3.h
                int r0 = com.autonavi.minimap.R.drawable.route_arrow_up
                r15.setImageResource(r0)
                android.view.View r15 = r3.i
                r15.setVisibility(r2)
                boolean r15 = r12.mLoadingRealTime
                if (r15 == 0) goto L_0x025c
                com.autonavi.minimap.route.common.view.RouteArrivingBusInfoView[] r15 = r3.e
                defpackage.ebj.a(r15, r2)
                goto L_0x028b
            L_0x025c:
                int r15 = r12.mStatus
                if (r1 != r15) goto L_0x0281
                com.autonavi.minimap.route.net.module.RTBusLocation r15 = r12.mRealTimeBusline
                if (r15 == 0) goto L_0x028b
                com.autonavi.minimap.route.net.module.RTBusLocation r15 = r12.mRealTimeBusline
                java.util.ArrayList r15 = r15.getTripList()
                if (r15 == 0) goto L_0x028b
                int r0 = r15.size()
                if (r0 <= 0) goto L_0x028b
                android.widget.TextView r0 = r3.d
                int r4 = r15.size()
                defpackage.ebj.a(r0, r4)
                com.autonavi.minimap.route.common.view.RouteArrivingBusInfoView[] r0 = r3.e
                defpackage.ebj.a(r0, r15)
                goto L_0x028b
            L_0x0281:
                android.widget.TextView r0 = r3.d
                defpackage.ebj.a(r0, r2)
                com.autonavi.minimap.route.common.view.RouteArrivingBusInfoView[] r0 = r3.e
                defpackage.ebj.a(r0, r15)
            L_0x028b:
                android.widget.ImageView r15 = r3.b
                android.view.ViewGroup$LayoutParams r15 = r15.getLayoutParams()
                android.widget.FrameLayout$LayoutParams r15 = (android.widget.FrameLayout.LayoutParams) r15
                r0 = -1
                if (r13 != 0) goto L_0x029e
                r15.height = r0
                int r13 = r12.normalHeight
                r15.setMargins(r2, r13, r2, r2)
                goto L_0x02b0
            L_0x029e:
                java.lang.String[] r4 = r12.mStationNameArray
                int r4 = r4.length
                int r4 = r4 - r1
                if (r13 != r4) goto L_0x02ab
                int r13 = r12.normalHeight
                int r13 = r13 + 10
                r15.height = r13
                goto L_0x02b0
            L_0x02ab:
                r15.height = r0
                r15.setMargins(r2, r2, r2, r2)
            L_0x02b0:
                android.widget.ImageView r13 = r3.b
                r13.setLayoutParams(r15)
                return r14
            */
            throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.route.bus.busline.page.BusLineDetailPage.BusDetailListAdapter.getView(int, android.view.View, android.view.ViewGroup):android.view.View");
        }
    }

    static class a {
        ImageView a;
        ImageView b;
        View c;
        TextView d;
        RouteArrivingBusInfoView[] e;
        TextView f;
        FlowLayout g;
        ImageView h;
        View i;
        View j;

        private a() {
        }

        /* synthetic */ a(byte b2) {
            this();
        }
    }

    public long getScene() {
        return 2097152;
    }

    public void onCreate(Context context) {
        int i2;
        super.onCreate(context);
        setContentView(R.layout.v4_busline_detail_view);
        requestScreenOrientation(1);
        dys.a((String) "P00265", (String) "B001", 1);
        View contentView = getContentView();
        this.e = (TitleBar) contentView.findViewById(R.id.title_bar);
        this.e.setBackImgContentDescription(getString(R.string.autonavi_back));
        this.e.setOnBackClickListener((OnClickListener) new OnClickListener() {
            public final void onClick(View view) {
                ((BusLineDetailPage) ((dut) BusLineDetailPage.this.mPresenter).mPage).finish();
            }
        });
        this.e.setOnActionClickListener(new OnClickListener() {
            public final void onClick(View view) {
                ((dut) BusLineDetailPage.this.mPresenter).a(-1, 1);
                LogManager.actionLogV2("P00116", "B004");
            }
        });
        this.n = (ViewStub) contentView.findViewById(R.id.bus_temporary_change_vs);
        this.o = this.n.inflate();
        this.o.setVisibility(8);
        this.f = (TextView) contentView.findViewById(R.id.txtStartEndStationName);
        this.a = contentView.findViewById(R.id.btn_busline_return);
        this.a.setOnClickListener(this);
        this.m = (TextView) contentView.findViewById(R.id.busline_result_detial_status_desc);
        this.g = (TextView) contentView.findViewById(R.id.timeStart);
        this.h = (TextView) contentView.findViewById(R.id.timeEnd);
        this.i = (TextView) contentView.findViewById(R.id.txtInterval);
        this.k = (TextView) contentView.findViewById(R.id.distance);
        this.l = (TextView) contentView.findViewById(R.id.price);
        this.d = (RouteFrameFooterView) contentView.findViewById(R.id.footer);
        this.d.setRouteFooterListener((com.autonavi.minimap.route.common.view.RouteFrameFooterView.a) this.mPresenter);
        this.b = (ListView) contentView.findViewById(R.id.detail_list);
        if (this.b.getFooterViewsCount() == 0) {
            TextView textView = new TextView(getContext());
            int a2 = agn.a(getContext(), 15.0f);
            textView.setPadding(0, a2, 0, a2);
            textView.setBackgroundColor(0);
            this.b.addFooterView(textView, null, false);
        }
        if (this.d != null) {
            this.d.measure(MeasureSpec.makeMeasureSpec(0, 0), MeasureSpec.makeMeasureSpec(0, 0));
            i2 = this.d.getMeasuredHeight();
        } else {
            i2 = getResources().getDimensionPixelSize(R.dimen.detail_bar_height);
        }
        this.b.setOnScrollListener(new ListViewOnScrollListener(this.d, i2));
    }

    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btn_busline_return) {
            dut dut = (dut) this.mPresenter;
            dut.e = true;
            dut.d = -2;
            dut.b.b();
            LogManager.actionLogV2("P00116", "B006");
        } else if (id == R.id.busline_detail_refresh) {
            ((dut) this.mPresenter).a(((Integer) view.getTag()).intValue(), true);
        } else if (id == R.id.busline_station) {
            ((dut) this.mPresenter).a(((Integer) view.getTag()).intValue(), 0);
            LogManager.actionLogV2("P00116", "B005");
        } else {
            if (id == R.id.busline_detail_expand_icon) {
                dut dut2 = (dut) this.mPresenter;
                int intValue = ((Integer) view.getTag()).intValue();
                if (dut2.d == intValue) {
                    dut2.d = -1;
                    dut2.a.a();
                } else {
                    dut2.d = intValue;
                    dut2.a(intValue, false);
                }
                ((BusLineDetailPage) dut2.mPage).a(dut2.d);
            }
        }
    }

    public final void a(boolean z) {
        this.d.setSaveBtnState(z);
    }

    public final void a() {
        this.c.setLoadingRealTime(true);
    }

    public final void a(int i2) {
        this.c.setExpandedPosition(i2);
        this.c.notifyDataSetInvalidated();
    }

    public final void a(Bus bus) {
        if (bus != null) {
            StringBuilder sb = new StringBuilder();
            sb.append(bus.key_name);
            sb.append(getString(R.string.route_detail));
            this.e.setTitle(sb.toString());
            StringBuilder sb2 = new StringBuilder();
            sb2.append(bus.startName);
            sb2.append(" > ");
            sb2.append(bus.endName);
            this.f.setText(axs.a(sb2.toString()));
            a(this.g, bus.startTime);
            a(this.h, bus.endTime);
            String str = bus.interval;
            if (!TextUtils.isEmpty(str)) {
                this.i.setVisibility(0);
                this.i.setText(str);
            } else {
                this.i.setVisibility(8);
            }
            String str2 = bus.otherLen;
            if (TextUtils.isEmpty(str2) || "0.0".equals(str2)) {
                this.k.setVisibility(8);
            } else {
                TextView textView = this.k;
                StringBuilder sb3 = new StringBuilder();
                sb3.append(getString(R.string.busline_whole_journey));
                sb3.append(str2);
                sb3.append(getString(R.string.km));
                textView.setText(sb3.toString());
                this.k.setVisibility(0);
            }
            String ticketDesc = bus.getTicketDesc();
            if (!TextUtils.isEmpty(ticketDesc)) {
                this.l.setText(ticketDesc);
                this.l.setVisibility(0);
            } else {
                this.l.setVisibility(8);
            }
            int i2 = bus.status;
            this.m.setVisibility(0);
            if (i2 == 0) {
                this.m.setText(R.string.busline_result_status_outage);
            } else if (i2 == 2) {
                this.m.setText(R.string.busline_result_status_planning);
            } else if (i2 == 3) {
                this.m.setText(R.string.busline_result_status_under_construction);
            } else {
                this.m.setVisibility(8);
            }
            boolean z = true;
            if (bus == null || bus.emergency == null || bus.emergency.b == null || "".equals(bus.emergency.b) || this.n == null) {
                this.o.setVisibility(8);
            } else {
                this.o.setVisibility(0);
                ImageView imageView = (ImageView) this.o.findViewById(R.id.bus_temporary_change_logo);
                final ImageView imageView2 = (ImageView) this.o.findViewById(R.id.bus_temporary_change_more);
                final TextView textView2 = (TextView) this.o.findViewById(R.id.bus_temporary_change_text);
                this.o.findViewById(16908294).setVisibility(0);
                if (imageView != null) {
                    LayoutParams layoutParams = (LayoutParams) imageView.getLayoutParams();
                    if (layoutParams != null) {
                        layoutParams.setMargins((int) (getResources().getDisplayMetrics().density * 18.0f), (int) (getResources().getDisplayMetrics().density * 10.0f), (int) (getResources().getDisplayMetrics().density * 3.0f), 0);
                    }
                }
                ((LayoutParams) imageView2.getLayoutParams()).setMargins(0, (int) (getResources().getDisplayMetrics().density * 10.0f), (int) (getResources().getDisplayMetrics().density * 18.0f), 0);
                ((LayoutParams) textView2.getLayoutParams()).setMargins(0, (int) (getResources().getDisplayMetrics().density * 8.0f), 0, 0);
                ((LinearLayout) this.o.getParent()).setPadding(0, 0, 0, (int) (getResources().getDisplayMetrics().density * 8.0f));
                String str3 = bus.emergency.b;
                textView2.setText(str3);
                this.o.requestLayout();
                Paint paint = new Paint();
                paint.setTextSize(getResources().getDisplayMetrics().density * 13.0f);
                if (paint.measureText(str3) > ((float) getResources().getDisplayMetrics().widthPixels) - (getResources().getDisplayMetrics().density * 60.0f)) {
                    imageView2.setVisibility(0);
                    textView2.setSingleLine(true);
                    textView2.setEllipsize(TruncateAt.END);
                    imageView2.setImageResource(R.drawable.bus_temporary_change_down);
                    this.o.requestLayout();
                    this.o.setOnClickListener(new OnClickListener() {
                        public final void onClick(View view) {
                            if (textView2.getLineCount() == 1) {
                                textView2.setSingleLine(false);
                                imageView2.setImageResource(R.drawable.bus_temporary_change_up);
                                BusLineDetailPage.this.o.requestLayout();
                                return;
                            }
                            textView2.setSingleLine(true);
                            textView2.setEllipsize(TruncateAt.END);
                            imageView2.setImageResource(R.drawable.bus_temporary_change_down);
                            BusLineDetailPage.this.o.requestLayout();
                        }
                    });
                }
            }
            b();
            if (this.b.getHeaderViewsCount() > 0) {
                this.b.removeHeaderView(this.j);
            }
            if (bus.irregulartime == null || (TextUtils.isEmpty(bus.irregulartime.normalday) && TextUtils.isEmpty(bus.irregulartime.workday) && TextUtils.isEmpty(bus.irregulartime.holiday))) {
                this.j = LayoutInflater.from(getContext()).inflate(R.layout.busline_detail_list_block, null);
            } else {
                this.j = LayoutInflater.from(getContext()).inflate(R.layout.busline_detail_irregulartime, null);
                if (!TextUtils.isEmpty(bus.irregulartime.normalday)) {
                    a(this.j, R.string.normalday, bus.irregulartime.normalday);
                } else {
                    if (!TextUtils.isEmpty(bus.irregulartime.workday)) {
                        a(this.j, R.string.workday, bus.irregulartime.workday);
                    } else {
                        z = false;
                    }
                    if (!TextUtils.isEmpty(bus.irregulartime.holiday)) {
                        if (z) {
                            this.j.findViewById(R.id.busline_detail_layout_expandable).setVisibility(0);
                            View view = this.j;
                            int i3 = R.string.holiday;
                            String str4 = bus.irregulartime.holiday;
                            if (view != null && !TextUtils.isEmpty(str4)) {
                                ((TextView) view.findViewById(R.id.busline_irregulartime_expandable_text)).setText(i3);
                                a((FlowLayout) view.findViewById(R.id.busline_irregulartime_expandable_flowlayout), str4);
                            }
                        } else {
                            a(this.j, R.string.holiday, bus.irregulartime.holiday);
                        }
                    }
                }
            }
            this.b.addHeaderView(this.j);
        }
    }

    public final void b() {
        if (((dut) this.mPresenter).c != null) {
            if (this.c == null || ((dut) this.mPresenter).e) {
                this.c = new BusDetailListAdapter(((dut) this.mPresenter).c);
                this.b.setAdapter(this.c);
                ((dut) this.mPresenter).e = false;
                return;
            }
            this.c.setDatas(((dut) this.mPresenter).c);
            this.c.notifyDataSetChanged();
        }
    }

    private static void a(TextView textView, int i2) {
        if (i2 >= 0) {
            textView.setText(axt.b(i2));
            textView.setVisibility(0);
            return;
        }
        textView.setVisibility(8);
    }

    private void a(View view, int i2, String str) {
        if (view != null && !TextUtils.isEmpty(str)) {
            ((TextView) view.findViewById(R.id.busline_irregulartime_text)).setText(i2);
            a((FlowLayout) view.findViewById(R.id.busline_irregulartime_flowlayout), str);
        }
    }

    private void a(FlowLayout flowLayout, String str) {
        String[] split;
        if (flowLayout != null && !TextUtils.isEmpty(str)) {
            flowLayout.removeAllViews();
            for (String str2 : str.split(",")) {
                if (!TextUtils.isEmpty(str2)) {
                    TextView textView = new TextView(getContext());
                    textView.setTextSize(1, 13.0f);
                    textView.setTextColor(ContextCompat.getColor(getContext(), R.color.f_c_2));
                    textView.setText(str2);
                    flowLayout.addView(textView);
                }
            }
        }
    }

    public /* synthetic */ IPresenter createPresenter() {
        return new dut(this);
    }
}
