package com.autonavi.minimap.basemap.traffic.presenter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ToggleButton;
import com.amap.bundle.statistics.LogManager;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.bundle.account.api.IAccountService;
import com.autonavi.common.PageBundle;
import com.autonavi.map.template.AbstractViewHolderBaseAdapter;
import com.autonavi.minimap.R;
import com.autonavi.minimap.basemap.traffic.ReportType;
import com.autonavi.minimap.basemap.traffic.TrafficUtil;
import com.autonavi.minimap.basemap.traffic.bean.TwiceReportType;
import com.autonavi.minimap.basemap.traffic.inter.ITrafficRequestManager;
import com.autonavi.minimap.basemap.traffic.page.TrafficSubmitPage;
import com.autonavi.sdk.log.util.LogConstant;
import com.autonavi.server.TrafficAosUICallback;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

public final class TrafficSubmitPresenter extends AbstractTrafficSubmitPresenter<TrafficSubmitPage> {
    public static final String p = TrafficSubmitPage.class.getSimpleName();
    private final crx A = new crx() {
        public final void a(String str) {
            if (!TextUtils.isEmpty(str) && !TrafficSubmitPresenter.this.z) {
                TrafficSubmitPresenter.this.z = true;
            }
            TrafficSubmitPresenter.a(TrafficSubmitPresenter.this, str);
            if (TrafficSubmitPresenter.this.mPage != null && ((TrafficSubmitPage) TrafficSubmitPresenter.this.mPage).isStarted()) {
                ((TrafficSubmitPage) TrafficSubmitPresenter.this.mPage).setSoftInputMode(2);
            }
        }

        public final void a() {
            if (TrafficSubmitPresenter.this.mPage != null) {
                ((TrafficSubmitPage) TrafficSubmitPresenter.this.mPage).d = null;
            }
            TrafficSubmitPresenter.this.v = false;
        }
    };
    private ReportType q = ReportType.INVALID;
    /* access modifiers changed from: private */
    public TwiceReportType r;
    private boolean s = false;
    /* access modifiers changed from: private */
    public boolean t = false;
    private LabelListAdapter u;
    /* access modifiers changed from: private */
    public boolean v = false;
    private boolean w = true;
    private String x = "";
    /* access modifiers changed from: private */
    public int y = 0;
    /* access modifiers changed from: private */
    public boolean z = false;

    static class LabelListAdapter extends AbstractViewHolderBaseAdapter<a, a> {
        private Context mContext;

        class a extends com.autonavi.map.template.AbstractViewHolderAdapter.a {
            ToggleButton a;

            public a(View view) {
                super(view);
                this.a = (ToggleButton) view.findViewById(R.id.btn);
            }
        }

        public LabelListAdapter(Context context) {
            this.mContext = context;
        }

        public View onCreateView(ViewGroup viewGroup, int i) {
            return LayoutInflater.from(this.mContext).inflate(R.layout.item_traffic_report_toggle_label, viewGroup, false);
        }

        public a onCreateViewHolder(View view, ViewGroup viewGroup, int i) {
            return new a(view);
        }

        public void onBindViewHolderWithData(a aVar, a aVar2, int i, int i2) {
            if (aVar != null && aVar2 != null) {
                ToggleButton toggleButton = aVar.a;
                if (toggleButton != null) {
                    toggleButton.setText(aVar2.a.b);
                    toggleButton.setTextOn(aVar2.a.b);
                    toggleButton.setTextOff(aVar2.a.b);
                    toggleButton.setChecked(aVar2.b);
                    toggleButton.setOnCheckedChangeListener(new OnLabelCheckedChangeListener(aVar2));
                }
            }
        }

        public List<csj> getCheckedLabels() {
            ArrayList arrayList = new ArrayList();
            List<a> data = getData();
            if (data != null) {
                for (a aVar : data) {
                    if (aVar.b) {
                        arrayList.add(aVar.a);
                    }
                }
            }
            return arrayList;
        }

        public String getCheckedIds() {
            StringBuffer stringBuffer = new StringBuffer();
            List<csj> checkedLabels = getCheckedLabels();
            if (checkedLabels != null && checkedLabels.size() > 0) {
                for (int i = 0; i < checkedLabels.size(); i++) {
                    csj csj = checkedLabels.get(i);
                    if (i != 0) {
                        stringBuffer.append(",");
                    }
                    stringBuffer.append(Integer.toString(csj.a));
                }
            }
            return stringBuffer.toString();
        }
    }

    public enum LogEvent {
        BACK,
        REPORT
    }

    static class OnLabelCheckedChangeListener implements OnCheckedChangeListener {
        WeakReference<a> labelItemModel;

        public OnLabelCheckedChangeListener(a aVar) {
            this.labelItemModel = new WeakReference<>(aVar);
        }

        public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
            if (this.labelItemModel != null && this.labelItemModel.get() != null) {
                ((a) this.labelItemModel.get()).b = z;
            }
        }
    }

    static class a {
        csj a;
        boolean b = false;

        a() {
        }
    }

    public TrafficSubmitPresenter(TrafficSubmitPage trafficSubmitPage) {
        super(trafficSubmitPage);
        PageBundle arguments = ((TrafficSubmitPage) this.mPage).getArguments();
        if (arguments != null) {
            this.q = (ReportType) arguments.getObject("ReportType");
            this.s = arguments.getBoolean("ShowResultDialog", false);
            this.r = (TwiceReportType) arguments.getObject("intent_twice_report_type");
            this.w = arguments.getBoolean("key_open_traffic_later", true);
            if (this.q == null && this.r != null) {
                this.q = this.r.a;
            }
        }
    }

    public final void onPageCreated() {
        this.u = new LabelListAdapter(((TrafficSubmitPage) this.mPage).getContext());
        super.onPageCreated();
        ((TrafficSubmitPage) this.mPage).e.setAdapter(this.u);
    }

    /* JADX WARNING: Removed duplicated region for block: B:100:0x0351  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void onClick(android.view.View r10) {
        /*
            r9 = this;
            super.onClick(r10)
            int r10 = r10.getId()
            int r0 = com.autonavi.minimap.R.id.traffic_report_desc
            r1 = -1
            r2 = 0
            r3 = 0
            r4 = 1
            if (r10 != r0) goto L_0x01c5
            com.autonavi.map.fragmentcontainer.page.IPage r10 = r9.mPage
            com.autonavi.minimap.basemap.traffic.page.TrafficSubmitPage r10 = (com.autonavi.minimap.basemap.traffic.page.TrafficSubmitPage) r10
            boolean r10 = r10.isAlive()
            if (r10 == 0) goto L_0x03e0
            com.autonavi.map.fragmentcontainer.page.IPage r10 = r9.mPage
            com.autonavi.minimap.basemap.traffic.page.TrafficSubmitPage r10 = (com.autonavi.minimap.basemap.traffic.page.TrafficSubmitPage) r10
            android.app.Activity r10 = r10.getActivity()
            boolean r10 = r10.isFinishing()
            if (r10 != 0) goto L_0x03e0
            com.autonavi.common.PageBundle r10 = new com.autonavi.common.PageBundle
            r10.<init>()
            java.lang.String r0 = "input_string"
            java.lang.String r5 = r9.x
            r10.putString(r0, r5)
            java.lang.String r0 = "hint_string"
            com.autonavi.map.fragmentcontainer.page.IPage r5 = r9.mPage
            com.autonavi.minimap.basemap.traffic.page.TrafficSubmitPage r5 = (com.autonavi.minimap.basemap.traffic.page.TrafficSubmitPage) r5
            int r6 = com.autonavi.minimap.R.string.traffic_report_desc_hint
            java.lang.String r5 = r5.getString(r6)
            r10.putString(r0, r5)
            java.lang.String r0 = "max_edit_text_length"
            r5 = 60
            r10.putInt(r0, r5)
            com.autonavi.map.fragmentcontainer.page.IPage r0 = r9.mPage
            com.autonavi.minimap.basemap.traffic.page.TrafficSubmitPage r0 = (com.autonavi.minimap.basemap.traffic.page.TrafficSubmitPage) r0
            crx r5 = r9.A
            crw r6 = new crw
            android.content.Context r7 = r0.getContext()
            android.view.LayoutInflater r8 = r0.b
            r6.<init>(r7, r8)
            r0.d = r6
            crw r6 = r0.d
            android.view.View r0 = r0.getContentView()
            r6.h = r5
            java.lang.String r5 = android.os.Build.MODEL
            if (r5 == 0) goto L_0x008f
            java.lang.String r5 = android.os.Build.MODEL
            java.lang.String r7 = "SM-G9209"
            boolean r5 = r5.equalsIgnoreCase(r7)
            if (r5 != 0) goto L_0x0086
            java.lang.String r5 = android.os.Build.MODEL
            java.lang.String r7 = "SM-G9250"
            boolean r5 = r5.equalsIgnoreCase(r7)
            if (r5 != 0) goto L_0x0086
            java.lang.String r5 = android.os.Build.MODEL
            java.lang.String r7 = "SM-G9200"
            boolean r5 = r5.equalsIgnoreCase(r7)
            if (r5 == 0) goto L_0x008f
        L_0x0086:
            android.view.LayoutInflater r5 = r6.c
            int r7 = com.autonavi.minimap.R.layout.error_report_description_input_top_layout
            android.view.View r2 = r5.inflate(r7, r2)
            goto L_0x0097
        L_0x008f:
            android.view.LayoutInflater r5 = r6.c
            int r7 = com.autonavi.minimap.R.layout.error_report_description_input_layout
            android.view.View r2 = r5.inflate(r7, r2)
        L_0x0097:
            r2.setFocusable(r4)
            r2.setFocusableInTouchMode(r4)
            int r5 = com.autonavi.minimap.R.id.tip_container
            android.view.View r5 = r2.findViewById(r5)
            r6.f = r5
            android.view.View r5 = r6.f
            int r7 = com.autonavi.minimap.R.id.tip
            android.view.View r5 = r5.findViewById(r7)
            android.widget.TextView r5 = (android.widget.TextView) r5
            r6.g = r5
            android.widget.PopupWindow r5 = new android.widget.PopupWindow
            r5.<init>(r2, r1, r1, r4)
            r6.a = r5
            android.widget.PopupWindow r1 = r6.a
            defpackage.euk.a(r1)
            android.widget.PopupWindow r1 = r6.a
            r1.setFocusable(r4)
            android.widget.PopupWindow r1 = r6.a
            android.graphics.drawable.BitmapDrawable r5 = new android.graphics.drawable.BitmapDrawable
            r5.<init>()
            r1.setBackgroundDrawable(r5)
            android.widget.PopupWindow r1 = r6.a
            r5 = 16
            r1.setSoftInputMode(r5)
            android.widget.PopupWindow r1 = r6.a
            com.autonavi.minimap.basemap.traffic.ErrorReportInputDialog$1 r5 = new com.autonavi.minimap.basemap.traffic.ErrorReportInputDialog$1
            r5.<init>(r6)
            r1.setOnDismissListener(r5)
            android.widget.PopupWindow r1 = r6.a
            r1.update()
            crw$1 r1 = new crw$1
            r1.<init>()
            r2.setOnTouchListener(r1)
            crw$2 r1 = new crw$2
            r1.<init>()
            r2.setOnKeyListener(r1)
            int r1 = com.autonavi.minimap.R.id.edit_input
            android.view.View r1 = r2.findViewById(r1)
            android.widget.EditText r1 = (android.widget.EditText) r1
            r6.d = r1
            android.widget.EditText r1 = r6.d
            com.autonavi.minimap.basemap.traffic.ErrorReportInputDialog$4 r5 = new com.autonavi.minimap.basemap.traffic.ErrorReportInputDialog$4
            r5.<init>(r6)
            r1.setOnEditorActionListener(r5)
            android.widget.EditText r1 = r6.d
            crw$3 r5 = new crw$3
            r5.<init>()
            r1.addTextChangedListener(r5)
            java.lang.String r1 = android.os.Build.BRAND
            java.lang.String r1 = r1.toLowerCase()
            java.lang.String r5 = "meizu"
            boolean r1 = r1.equals(r5)
            if (r1 == 0) goto L_0x0136
            java.lang.String r1 = android.os.Build.DEVICE
            java.lang.String r1 = r1.toLowerCase()
            java.lang.String r5 = "mx2"
            boolean r1 = r1.equals(r5)
            if (r1 == 0) goto L_0x0136
            android.widget.EditText r1 = r6.d
            crw$4 r5 = new crw$4
            r5.<init>()
            r1.setOnTouchListener(r5)
        L_0x0136:
            java.lang.String r1 = "max_edit_text_length"
            int r1 = r10.getInt(r1, r3)
            r6.k = r1
            java.lang.String r1 = "input_string"
            boolean r1 = r10.containsKey(r1)
            if (r1 == 0) goto L_0x0151
            java.lang.String r1 = "input_string"
            java.lang.String r1 = r10.getString(r1)
            android.widget.EditText r5 = r6.d
            r5.setText(r1)
        L_0x0151:
            java.lang.String r1 = "hint_string"
            boolean r1 = r10.containsKey(r1)
            if (r1 == 0) goto L_0x0168
            java.lang.String r1 = "hint_string"
            java.lang.String r1 = r10.getString(r1)
            r6.i = r1
            android.widget.EditText r1 = r6.d
            java.lang.String r5 = r6.i
            r1.setHint(r5)
        L_0x0168:
            java.lang.String r1 = "tip_string"
            java.lang.String r5 = ""
            java.lang.String r1 = r10.getString(r1, r5)
            r6.l = r1
            java.lang.String r1 = r6.l
            boolean r1 = android.text.TextUtils.isEmpty(r1)
            if (r1 != 0) goto L_0x0182
            java.lang.String r1 = r6.l
            r7 = 4000(0xfa0, double:1.9763E-320)
            r6.a(r1, r7)
        L_0x0182:
            java.lang.String r1 = "always_tip_string"
            java.lang.String r5 = ""
            java.lang.String r10 = r10.getString(r1, r5)
            r6.m = r10
            java.lang.String r10 = r6.m
            boolean r10 = android.text.TextUtils.isEmpty(r10)
            if (r10 != 0) goto L_0x019b
            java.lang.String r10 = r6.m
            r7 = -1
            r6.a(r10, r7)
        L_0x019b:
            int r10 = com.autonavi.minimap.R.id.des_submit
            android.view.View r10 = r2.findViewById(r10)
            com.autonavi.map.widget.AmapButton r10 = (com.autonavi.map.widget.AmapButton) r10
            r6.e = r10
            com.autonavi.map.widget.AmapButton r10 = r6.e
            crw$5 r1 = new crw$5
            r1.<init>()
            r10.setOnClickListener(r1)
            android.widget.PopupWindow r10 = r6.a
            r1 = 53
            r10.showAtLocation(r0, r1, r3, r3)
            r6.j = r4
            com.autonavi.map.widget.AmapButton r10 = r6.e
            crw$6 r0 = new crw$6
            r0.<init>()
            r10.post(r0)
            r9.v = r4
            return
        L_0x01c5:
            int r0 = com.autonavi.minimap.R.id.traffic_report_right_now
            if (r10 != r0) goto L_0x03e0
            com.autonavi.minimap.basemap.traffic.presenter.TrafficSubmitPresenter$LogEvent r10 = com.autonavi.minimap.basemap.traffic.presenter.TrafficSubmitPresenter.LogEvent.REPORT
            r9.a(r10)
            com.autonavi.minimap.basemap.traffic.ReportType r10 = r9.q
            com.autonavi.map.core.MapManager r0 = com.autonavi.map.fragmentcontainer.page.DoNotUseTool.getMapManager()
            if (r0 == 0) goto L_0x03d8
            esb r0 = defpackage.esb.a.a
            java.lang.Class<awo> r5 = defpackage.awo.class
            esc r0 = r0.a(r5)
            awo r0 = (defpackage.awo) r0
            if (r0 == 0) goto L_0x01f1
            boolean r5 = r9.w
            if (r5 == 0) goto L_0x01f1
            boolean r5 = r0.d()
            if (r5 != 0) goto L_0x01f1
            r0.b(r4)
        L_0x01f1:
            com.autonavi.common.model.GeoPoint r0 = r9.j
            if (r0 != 0) goto L_0x020b
            r9.h()
            com.autonavi.common.model.GeoPoint r0 = r9.j
            if (r0 != 0) goto L_0x020b
            com.autonavi.map.fragmentcontainer.page.IPage r10 = r9.mPage
            com.autonavi.minimap.basemap.traffic.page.TrafficSubmitPage r10 = (com.autonavi.minimap.basemap.traffic.page.TrafficSubmitPage) r10
            int r0 = com.autonavi.minimap.R.string.oper_need_location
            java.lang.String r10 = r10.getString(r0)
            com.amap.bundle.utils.ui.ToastHelper.showLongToast(r10)
            goto L_0x034f
        L_0x020b:
            if (r10 == 0) goto L_0x021d
            com.autonavi.minimap.basemap.traffic.ReportType r0 = com.autonavi.minimap.basemap.traffic.ReportType.INVALID
            if (r10 != r0) goto L_0x0212
            goto L_0x021d
        L_0x0212:
            cso r0 = new cso
            java.lang.String r2 = r10.layerId
            java.lang.String r10 = r10.layerTag
            r0.<init>(r2, r10)
            r2 = r0
            goto L_0x0227
        L_0x021d:
            cso r10 = new cso
            java.lang.String r0 = "0"
            java.lang.String r2 = "0"
            r10.<init>(r0, r2)
            r2 = r10
        L_0x0227:
            com.autonavi.minimap.basemap.traffic.bean.TwiceReportType r10 = r9.r
            if (r10 == 0) goto L_0x0237
            com.autonavi.minimap.basemap.traffic.bean.TwiceReportType r10 = r9.r
            int r10 = r10.b
            r2.I = r10
            com.autonavi.minimap.basemap.traffic.bean.TwiceReportType r10 = r9.r
            int r10 = r10.c
            r2.J = r10
        L_0x0237:
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            r10.<init>()
            int r0 = r9.g
            r10.append(r0)
            java.lang.String r10 = r10.toString()
            r2.h = r10
            java.lang.String r10 = r9.x
            boolean r0 = android.text.TextUtils.isEmpty(r10)
            if (r0 == 0) goto L_0x0252
            java.lang.String r10 = ""
            goto L_0x0256
        L_0x0252:
            java.lang.String r10 = r10.toString()
        L_0x0256:
            r2.g = r10
            java.lang.String r10 = "jpeg"
            r2.j = r10
            java.io.File r10 = r9.g()
            if (r10 == 0) goto L_0x0264
            r2.k = r10
        L_0x0264:
            java.lang.String r10 = r9.d
            boolean r10 = android.text.TextUtils.isEmpty(r10)
            if (r10 != 0) goto L_0x028a
            java.lang.String r10 = r9.e
            boolean r10 = android.text.TextUtils.isEmpty(r10)
            if (r10 == 0) goto L_0x0275
            goto L_0x028a
        L_0x0275:
            java.lang.String r10 = r9.d
            r2.q = r10
            java.lang.String r10 = r9.e
            r2.r = r10
            java.lang.String r10 = r9.h
            r2.u = r10
            java.lang.String r10 = r9.i
            r2.v = r10
            java.lang.String r10 = r9.f
            r2.w = r10
            goto L_0x02b8
        L_0x028a:
            com.autonavi.common.model.GeoPoint r10 = r9.j
            int r10 = r10.x
            long r5 = (long) r10
            com.autonavi.common.model.GeoPoint r10 = r9.j
            int r10 = r10.y
            long r7 = (long) r10
            com.autonavi.minimap.map.DPoint r10 = defpackage.cfg.a(r5, r7)
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            double r5 = r10.x
            r0.append(r5)
            java.lang.String r0 = r0.toString()
            r2.b = r0
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            double r5 = r10.y
            r0.append(r5)
            java.lang.String r10 = r0.toString()
            r2.c = r10
        L_0x02b8:
            java.lang.Class<dfm> r10 = defpackage.dfm.class
            java.lang.Object r10 = defpackage.ank.a(r10)
            dfm r10 = (defpackage.dfm) r10
            if (r10 == 0) goto L_0x02cd
            boolean r10 = r10.b()
            if (r10 == 0) goto L_0x02cd
            java.lang.String r10 = "0"
            r2.s = r10
            goto L_0x02d1
        L_0x02cd:
            java.lang.String r10 = "1"
            r2.s = r10
        L_0x02d1:
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            r10.<init>()
            bty r0 = com.autonavi.map.fragmentcontainer.page.DoNotUseTool.getMapView()
            int r0 = r0.w()
            r10.append(r0)
            java.lang.String r10 = r10.toString()
            r2.z = r10
            java.lang.String r10 = l()
            boolean r10 = android.text.TextUtils.isEmpty(r10)
            if (r10 == 0) goto L_0x0310
            esb r10 = defpackage.esb.a.a
            java.lang.Class<com.autonavi.bundle.account.api.IAccountService> r0 = com.autonavi.bundle.account.api.IAccountService.class
            esc r10 = r10.a(r0)
            com.autonavi.bundle.account.api.IAccountService r10 = (com.autonavi.bundle.account.api.IAccountService) r10
            if (r10 != 0) goto L_0x0302
            java.lang.String r10 = ""
            goto L_0x030d
        L_0x0302:
            ant r10 = r10.e()
            if (r10 != 0) goto L_0x030b
            java.lang.String r10 = ""
            goto L_0x030d
        L_0x030b:
            java.lang.String r10 = r10.c
        L_0x030d:
            r2.p = r10
            goto L_0x0316
        L_0x0310:
            java.lang.String r10 = l()
            r2.p = r10
        L_0x0316:
            int r10 = r9.c
            r0 = 3
            if (r10 == r0) goto L_0x0328
            com.autonavi.sdk.location.LocationInstrument r10 = com.autonavi.sdk.location.LocationInstrument.getInstance()
            android.location.Location r10 = r10.getLatestLocation()
            float r10 = r10.getAccuracy()
            int r1 = (int) r10
        L_0x0328:
            r2.B = r1
            int r10 = r9.c
            r2.C = r10
            com.autonavi.map.fragmentcontainer.page.IPage r10 = r9.mPage
            com.autonavi.minimap.basemap.traffic.page.TrafficSubmitPage r10 = (com.autonavi.minimap.basemap.traffic.page.TrafficSubmitPage) r10
            int r10 = r10.c()
            if (r10 >= 0) goto L_0x0339
            r10 = 0
        L_0x0339:
            java.lang.String r10 = java.lang.Integer.toString(r10)
            r2.F = r10
            java.lang.String r10 = r9.m()
            boolean r0 = android.text.TextUtils.isEmpty(r10)
            if (r0 != 0) goto L_0x034b
            r2.G = r10
        L_0x034b:
            java.lang.String r10 = "0"
            r2.H = r10
        L_0x034f:
            if (r2 == 0) goto L_0x03e0
            if (r2 != 0) goto L_0x0361
            com.autonavi.map.fragmentcontainer.page.IPage r10 = r9.mPage
            com.autonavi.minimap.basemap.traffic.page.TrafficSubmitPage r10 = (com.autonavi.minimap.basemap.traffic.page.TrafficSubmitPage) r10
            int r0 = com.autonavi.minimap.R.string.oper_commit_content_err
            java.lang.String r10 = r10.getString(r0)
            com.amap.bundle.utils.ui.ToastHelper.showLongToast(r10)
            goto L_0x03bf
        L_0x0361:
            com.autonavi.map.fragmentcontainer.page.IPage r10 = r9.mPage
            com.autonavi.minimap.basemap.traffic.page.TrafficSubmitPage r10 = (com.autonavi.minimap.basemap.traffic.page.TrafficSubmitPage) r10
            android.content.Context r10 = r10.getContext()
            boolean r10 = defpackage.aaw.c(r10)
            if (r10 != 0) goto L_0x037d
            com.autonavi.map.fragmentcontainer.page.IPage r10 = r9.mPage
            com.autonavi.minimap.basemap.traffic.page.TrafficSubmitPage r10 = (com.autonavi.minimap.basemap.traffic.page.TrafficSubmitPage) r10
            int r0 = com.autonavi.minimap.R.string.oper_check_network_err
            java.lang.String r10 = r10.getString(r0)
            com.amap.bundle.utils.ui.ToastHelper.showToast(r10)
            goto L_0x03bf
        L_0x037d:
            r2.o = r4
            long r0 = java.lang.System.currentTimeMillis()
            r9.a(r2, r0)
            com.autonavi.minimap.basemap.traffic.bean.TwiceReportType r10 = r9.r
            if (r10 == 0) goto L_0x0390
            com.autonavi.minimap.basemap.traffic.bean.TwiceReportType r10 = r9.r
            int r10 = r10.d
            if (r10 != r4) goto L_0x0391
        L_0x0390:
            r3 = 1
        L_0x0391:
            r9.a(r2, r0, r3)
            boolean r10 = r9.s
            if (r10 == 0) goto L_0x03bf
            com.autonavi.map.fragmentcontainer.page.IPage r10 = r9.mPage
            com.autonavi.minimap.basemap.traffic.page.TrafficSubmitPage r10 = (com.autonavi.minimap.basemap.traffic.page.TrafficSubmitPage) r10
            android.app.Activity r10 = r10.getActivity()
            boolean r0 = r10.isFinishing()
            if (r0 != 0) goto L_0x03bf
            java.lang.Class<com.autonavi.minimap.basemap.errorback.inter.ITrafficReportController> r0 = com.autonavi.minimap.basemap.errorback.inter.ITrafficReportController.class
            java.lang.Object r0 = defpackage.ank.a(r0)
            com.autonavi.minimap.basemap.errorback.inter.ITrafficReportController r0 = (com.autonavi.minimap.basemap.errorback.inter.ITrafficReportController) r0
            if (r0 == 0) goto L_0x03bf
            android.app.Dialog r0 = r0.a(r10)
            boolean r10 = r10.isFinishing()
            if (r10 != 0) goto L_0x03bf
            r0.show()
            r9.t = r4
        L_0x03bf:
            org.json.JSONObject r10 = new org.json.JSONObject
            r10.<init>()
            java.lang.String r0 = "layerid"
            java.lang.String r1 = r2.d     // Catch:{ JSONException -> 0x03d3 }
            r10.put(r0, r1)     // Catch:{ JSONException -> 0x03d3 }
            java.lang.String r0 = "layertag"
            java.lang.String r1 = r2.e     // Catch:{ JSONException -> 0x03d3 }
            r10.put(r0, r1)     // Catch:{ JSONException -> 0x03d3 }
            return
        L_0x03d3:
            r10 = move-exception
            r10.printStackTrace()
            return
        L_0x03d8:
            java.lang.String r10 = p
            java.lang.String r0 = "submit: mapContainer is null"
            com.amap.bundle.logs.AMapLog.d(r10, r0)
        L_0x03e0:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.basemap.traffic.presenter.TrafficSubmitPresenter.onClick(android.view.View):void");
    }

    public final void c() {
        a(LogEvent.BACK);
    }

    /* access modifiers changed from: protected */
    public final void a() {
        super.a();
        ((TrafficSubmitPage) this.mPage).a(8);
        ((TrafficSubmitPage) this.mPage).b(8);
        if (this.q == ReportType.ACCIDENT || this.q == ReportType.PROCESS || this.q == ReportType.STOP || this.q == ReportType.TROUBLE) {
            ((TrafficSubmitPage) this.mPage).a(0);
        } else if (this.q != ReportType.INVALID) {
            ((TrafficSubmitPage) this.mPage).b(0);
            ((TrafficSubmitPage) this.mPage).f.setChecked(true);
        }
        csi a2 = csi.a();
        List<csj> list = a2.a.get(this.q);
        if (list == null || list.size() <= 0) {
            ((TrafficSubmitPage) this.mPage).e.setVisibility(8);
            return;
        }
        LabelListAdapter labelListAdapter = this.u;
        ArrayList arrayList = null;
        if (list != null) {
            arrayList = new ArrayList();
            for (csj csj : list) {
                a aVar = new a();
                aVar.a = csj;
                arrayList.add(aVar);
            }
        }
        labelListAdapter.setData(arrayList);
    }

    /* access modifiers changed from: private */
    public void a(cso cso, long j) {
        ITrafficRequestManager iTrafficRequestManager = (ITrafficRequestManager) ank.a(ITrafficRequestManager.class);
        if (iTrafficRequestManager != null) {
            this.m = true;
            final Context applicationContext = ((TrafficSubmitPage) this.mPage).getContext().getApplicationContext();
            final cso cso2 = cso;
            final long j2 = j;
            AnonymousClass2 r2 = new TrafficAosUICallback() {
                public final void a(int i, String str) {
                    TrafficSubmitPresenter.this.m = false;
                    if (TextUtils.isEmpty(str) || !str.equals(applicationContext.getString(R.string.network_error_message)) || TrafficSubmitPresenter.this.y > 0) {
                        TrafficSubmitPresenter.this.y = 0;
                        if (i == 2 || str == null || str.trim().equals("")) {
                            str = applicationContext.getString(R.string.traffic_send_failed);
                        }
                        if (i == 30) {
                            IAccountService iAccountService = (IAccountService) defpackage.esb.a.a.a(IAccountService.class);
                            if (iAccountService != null) {
                                iAccountService.d();
                            } else {
                                return;
                            }
                        } else if (i == 48) {
                            str = applicationContext.getString(R.string.traffic_senitive_word);
                        }
                        if (!TrafficSubmitPresenter.this.t) {
                            ToastHelper.showLongToast(str);
                        }
                        csn.a().a((int) j2);
                        if (TrafficSubmitPresenter.this.mPage != null && !((TrafficSubmitPage) TrafficSubmitPresenter.this.mPage).isAlive()) {
                            TrafficSubmitPresenter.this.a(false);
                        }
                        return;
                    }
                    TrafficSubmitPresenter.this.y = TrafficSubmitPresenter.this.y + 1;
                    TrafficSubmitPresenter.this.a(cso2, 0);
                }

                public final void a(JSONObject jSONObject) {
                    TrafficSubmitPresenter.this.m = false;
                    if (cso2 != null) {
                        TrafficSubmitPresenter.this.l = jSONObject.optInt("event_id");
                        if (TrafficSubmitPresenter.this.l > 0 && TrafficSubmitPresenter.this.r == null) {
                            csn.a().a((int) j2, TrafficSubmitPresenter.this.l);
                        }
                    }
                    if (!TrafficSubmitPresenter.this.t) {
                        if (TrafficSubmitPresenter.k()) {
                            ToastHelper.showLongToast(applicationContext.getString(R.string.traffic_send_success_logined));
                        } else {
                            ToastHelper.showLongToast(applicationContext.getString(R.string.traffic_send_success_nologin));
                        }
                    }
                    if (TrafficSubmitPresenter.this.mPage != null && !((TrafficSubmitPage) TrafficSubmitPresenter.this.mPage).isAlive()) {
                        TrafficSubmitPresenter.this.a(false);
                    }
                }
            };
            iTrafficRequestManager.a(cso, r2);
        }
    }

    private String m() {
        if (this.u != null) {
            return this.u.getCheckedIds();
        }
        return "";
    }

    private String a(int i) {
        if (i == 0) {
            return ((TrafficSubmitPage) this.mPage).getString(R.string.traffic_report_driveway_name_same);
        }
        if (i == 1) {
            return ((TrafficSubmitPage) this.mPage).getString(R.string.traffic_report_driveway_name_diff);
        }
        if (i == 2) {
            return ((TrafficSubmitPage) this.mPage).getString(R.string.traffic_report_driveway_name_left);
        }
        if (i == 3) {
            return ((TrafficSubmitPage) this.mPage).getString(R.string.traffic_report_driveway_name_mid);
        }
        if (i == 4) {
            return ((TrafficSubmitPage) this.mPage).getString(R.string.traffic_report_driveway_name_right);
        }
        return "";
    }

    private void a(LogEvent logEvent) {
        String str = "";
        JSONObject jSONObject = null;
        switch (logEvent) {
            case BACK:
                str = "B003";
                break;
            case REPORT:
                str = "B002";
                jSONObject = new JSONObject();
                try {
                    jSONObject.put(TrafficUtil.KEYWORD, j());
                    int c = ((TrafficSubmitPage) this.mPage).c();
                    if (c < 0) {
                        c = 0;
                    }
                    jSONObject.put("type", a(c));
                    String m = m();
                    if (!TextUtils.isEmpty(m)) {
                        jSONObject.put("text", m);
                    }
                    if (!TextUtils.isEmpty(this.x)) {
                        jSONObject.put("action", ((TrafficSubmitPage) this.mPage).getString(R.string.traffic_report_submit_log_content));
                    }
                    if (g() != null) {
                        jSONObject.put("url", ((TrafficSubmitPage) this.mPage).getString(R.string.traffic_report_submit_log_img));
                    }
                    if (this.b) {
                        jSONObject.put("lat", ((TrafficSubmitPage) this.mPage).getString(R.string.traffic_report_submit_log_poi));
                        break;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    break;
                }
                break;
        }
        if (!TextUtils.isEmpty(LogConstant.PAGE_ID_MAIN_REPORT_KIND) && !TextUtils.isEmpty(str)) {
            if (jSONObject == null) {
                LogManager.actionLogV2(LogConstant.PAGE_ID_MAIN_REPORT_KIND, str);
                return;
            }
            LogManager.actionLogV2(LogConstant.PAGE_ID_MAIN_REPORT_KIND, str, jSONObject);
        }
    }

    private static String l() {
        IAccountService iAccountService = (IAccountService) defpackage.esb.a.a.a(IAccountService.class);
        if (iAccountService == null) {
            return "";
        }
        ant e = iAccountService.e();
        if (e == null) {
            return "";
        }
        return e.e;
    }

    public final String j() {
        String str = "";
        if (this.r != null) {
            if (this.r.d == 1) {
                str = ((TrafficSubmitPage) this.mPage).getString(R.string.traffic_report_update);
            } else if (this.r.d == 2) {
                str = ((TrafficSubmitPage) this.mPage).getString(R.string.traffic_report_open);
            } else if (this.r.d == 3) {
                str = ((TrafficSubmitPage) this.mPage).getString(R.string.traffic_report_complete);
            }
        }
        if (TextUtils.isEmpty(str)) {
            ReportType reportType = this.q;
            String str2 = "";
            if (!(reportType == null || reportType == ReportType.INVALID)) {
                if (reportType == ReportType.ACCIDENT) {
                    str2 = ((TrafficSubmitPage) this.mPage).getString(R.string.traffic_report_accident);
                } else if (reportType == ReportType.POLICE) {
                    str2 = ((TrafficSubmitPage) this.mPage).getString(R.string.traffic_report_police);
                } else if (reportType == ReportType.PROCESS) {
                    str2 = ((TrafficSubmitPage) this.mPage).getString(R.string.traffic_report_process);
                } else if (reportType == ReportType.CONGESTION) {
                    str2 = ((TrafficSubmitPage) this.mPage).getString(R.string.traffic_report_congestion);
                } else if (reportType == ReportType.PONDING) {
                    str2 = ((TrafficSubmitPage) this.mPage).getString(R.string.traffic_report_ponding);
                } else if (reportType == ReportType.STOP) {
                    str2 = ((TrafficSubmitPage) this.mPage).getString(R.string.traffic_report_closure);
                }
            }
            str = str2;
        }
        return !TextUtils.isEmpty(str) ? str : ((TrafficSubmitPage) this.mPage).getString(R.string.traffic_report_default);
    }

    static /* synthetic */ void a(TrafficSubmitPresenter trafficSubmitPresenter, String str) {
        if (str == null) {
            str = "";
        }
        if (!str.equals(trafficSubmitPresenter.x)) {
            trafficSubmitPresenter.x = str;
            ((TrafficSubmitPage) trafficSubmitPresenter.mPage).c.setText(trafficSubmitPresenter.x);
        }
    }

    static /* synthetic */ boolean k() {
        IAccountService iAccountService = (IAccountService) defpackage.esb.a.a.a(IAccountService.class);
        if (iAccountService == null) {
            return false;
        }
        return iAccountService.a();
    }
}
