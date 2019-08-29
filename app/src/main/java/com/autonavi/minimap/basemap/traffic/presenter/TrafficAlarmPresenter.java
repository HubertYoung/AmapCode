package com.autonavi.minimap.basemap.traffic.presenter;

import android.annotation.TargetApi;
import android.text.Editable;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View.OnClickListener;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName;
import com.amap.bundle.statistics.LogManager;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.bundle.account.api.IAccountService;
import com.autonavi.minimap.R;
import com.autonavi.minimap.basemap.traffic.inter.ITrafficRequestManager;
import com.autonavi.minimap.basemap.traffic.page.TrafficAlarmPage;
import com.autonavi.sdk.log.util.LogConstant;
import com.autonavi.server.TrafficAosUICallback;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class TrafficAlarmPresenter extends AbstractTrafficSubmitPresenter<TrafficAlarmPage> implements OnClickListener {
    private ITrafficRequestManager p = null;
    /* access modifiers changed from: private */
    public String q = "";
    /* access modifiers changed from: private */
    public String r = "";
    private TextWatcher s = new TextWatcher() {
        public final void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        public final void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @TargetApi(16)
        public final void afterTextChanged(Editable editable) {
            TrafficAlarmPresenter.this.k();
        }
    };
    private final TrafficAosUICallback t = new TrafficAosUICallback() {
        public final void a(int i, String str) {
            TrafficAlarmPresenter.this.b();
            TrafficAlarmPresenter.this.m = false;
            if (i == 129) {
                ToastHelper.showToast(((TrafficAlarmPage) TrafficAlarmPresenter.this.mPage).getString(R.string.traffic_report_alarm_unsupport_err));
            } else if (i == 55) {
                ToastHelper.showToast(((TrafficAlarmPage) TrafficAlarmPresenter.this.mPage).getString(R.string.traffic_report_alarm_phone_err));
            } else {
                ToastHelper.showToast(((TrafficAlarmPage) TrafficAlarmPresenter.this.mPage).getString(R.string.traffic_report_alarm_err));
            }
            if (!((TrafficAlarmPage) TrafficAlarmPresenter.this.mPage).isAlive()) {
                TrafficAlarmPresenter.this.a(false);
            }
        }

        public final void a(JSONObject jSONObject) {
            TrafficAlarmPresenter.this.b();
            TrafficAlarmPresenter.this.m = false;
            if (((TrafficAlarmPage) TrafficAlarmPresenter.this.mPage).isAlive()) {
                TrafficAlarmPresenter.this.a(false);
            }
            if (jSONObject != null && "1".equals(jSONObject.optString("code"))) {
                String optString = jSONObject.optString("h5url");
                jSONObject.optString("desc");
                if (!TextUtils.isEmpty(optString)) {
                    TrafficAlarmPresenter.this.i();
                    TrafficAlarmPresenter.this.a(optString);
                }
            }
        }
    };

    public enum LogEvent {
        POI,
        TAKE_PICTURE,
        DEL_PICTURE,
        REPORT,
        TIP,
        HURT
    }

    public TrafficAlarmPresenter(TrafficAlarmPage trafficAlarmPage) {
        super(trafficAlarmPage);
    }

    public final void onPageCreated() {
        this.p = (ITrafficRequestManager) ank.a(ITrafficRequestManager.class);
        super.onPageCreated();
        ((TrafficAlarmPage) this.mPage).b.addTextChangedListener(this.s);
    }

    /* JADX WARNING: Removed duplicated region for block: B:39:0x0116  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void onClick(android.view.View r10) {
        /*
            r9 = this;
            super.onClick(r10)
            int r10 = r10.getId()
            int r0 = com.autonavi.minimap.R.id.traffic_report_right_now
            r1 = 0
            r2 = 1
            if (r10 != r0) goto L_0x0184
            com.autonavi.minimap.basemap.traffic.presenter.TrafficAlarmPresenter$LogEvent r0 = com.autonavi.minimap.basemap.traffic.presenter.TrafficAlarmPresenter.LogEvent.REPORT
            r9.a(r0)
            com.autonavi.map.fragmentcontainer.page.IPage r0 = r9.mPage
            com.autonavi.minimap.basemap.traffic.page.TrafficAlarmPage r0 = (com.autonavi.minimap.basemap.traffic.page.TrafficAlarmPage) r0
            android.content.Context r0 = r0.getContext()
            boolean r0 = defpackage.aaw.c(r0)
            if (r0 != 0) goto L_0x002e
            com.autonavi.map.fragmentcontainer.page.IPage r10 = r9.mPage
            com.autonavi.minimap.basemap.traffic.page.TrafficAlarmPage r10 = (com.autonavi.minimap.basemap.traffic.page.TrafficAlarmPage) r10
            int r0 = com.autonavi.minimap.R.string.oper_check_network_err
            java.lang.String r10 = r10.getString(r0)
            com.amap.bundle.utils.ui.ToastHelper.showToast(r10)
            return
        L_0x002e:
            long r3 = java.lang.System.currentTimeMillis()
            long r5 = defpackage.agc.a
            long r5 = r3 - r5
            long r5 = java.lang.Math.abs(r5)
            r7 = 500(0x1f4, double:2.47E-321)
            int r0 = (r5 > r7 ? 1 : (r5 == r7 ? 0 : -1))
            if (r0 >= 0) goto L_0x0042
            r0 = 1
            goto L_0x0045
        L_0x0042:
            defpackage.agc.a = r3
            r0 = 0
        L_0x0045:
            if (r0 == 0) goto L_0x0048
            return
        L_0x0048:
            boolean r0 = r9.b(r2)
            if (r0 == 0) goto L_0x018b
            bty r0 = com.autonavi.map.fragmentcontainer.page.DoNotUseTool.getMapView()
            if (r0 == 0) goto L_0x0065
            esb r0 = defpackage.esb.a.a
            java.lang.Class<awo> r3 = defpackage.awo.class
            esc r0 = r0.a(r3)
            awo r0 = (defpackage.awo) r0
            if (r0 == 0) goto L_0x0065
            r0.b(r2)
        L_0x0065:
            com.autonavi.common.model.GeoPoint r0 = r9.j
            if (r0 != 0) goto L_0x0080
            r9.h()
            com.autonavi.common.model.GeoPoint r0 = r9.j
            if (r0 != 0) goto L_0x0080
            com.autonavi.map.fragmentcontainer.page.IPage r0 = r9.mPage
            com.autonavi.minimap.basemap.traffic.page.TrafficAlarmPage r0 = (com.autonavi.minimap.basemap.traffic.page.TrafficAlarmPage) r0
            int r3 = com.autonavi.minimap.R.string.oper_need_location
            java.lang.String r0 = r0.getString(r3)
            com.amap.bundle.utils.ui.ToastHelper.showToast(r0)
            r0 = 0
            goto L_0x0114
        L_0x0080:
            cso r0 = new cso
            java.lang.String r3 = "0"
            java.lang.String r4 = "0"
            r0.<init>(r3, r4)
            com.autonavi.map.fragmentcontainer.page.IPage r3 = r9.mPage
            com.autonavi.minimap.basemap.traffic.page.TrafficAlarmPage r3 = (com.autonavi.minimap.basemap.traffic.page.TrafficAlarmPage) r3
            android.widget.EditText r3 = r3.b
            android.text.Editable r3 = r3.getText()
            java.lang.String r3 = r3.toString()
            r0.g = r3
            com.autonavi.map.fragmentcontainer.page.IPage r3 = r9.mPage
            com.autonavi.minimap.basemap.traffic.page.TrafficAlarmPage r3 = (com.autonavi.minimap.basemap.traffic.page.TrafficAlarmPage) r3
            android.widget.ToggleButton r3 = r3.c
            boolean r3 = r3.isChecked()
            r0.E = r3
            r0.D = r2
            java.lang.String r3 = "jpeg"
            r0.j = r3
            java.io.File r3 = r9.g()
            if (r3 == 0) goto L_0x00b3
            r0.k = r3
        L_0x00b3:
            com.autonavi.common.model.GeoPoint r3 = r9.j
            int r3 = r3.x
            long r3 = (long) r3
            com.autonavi.common.model.GeoPoint r5 = r9.j
            int r5 = r5.y
            long r5 = (long) r5
            com.autonavi.minimap.map.DPoint r3 = defpackage.cfg.a(r3, r5)
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            double r5 = r3.x
            r4.append(r5)
            java.lang.String r4 = r4.toString()
            r0.b = r4
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            double r5 = r3.y
            r4.append(r5)
            java.lang.String r3 = r4.toString()
            r0.c = r3
            esb r3 = defpackage.esb.a.a
            java.lang.Class<com.autonavi.bundle.account.api.IAccountService> r4 = com.autonavi.bundle.account.api.IAccountService.class
            esc r3 = r3.a(r4)
            com.autonavi.bundle.account.api.IAccountService r3 = (com.autonavi.bundle.account.api.IAccountService) r3
            if (r3 != 0) goto L_0x00f2
            java.lang.String r3 = ""
            goto L_0x00fd
        L_0x00f2:
            ant r3 = r3.e()
            if (r3 != 0) goto L_0x00fb
            java.lang.String r3 = ""
            goto L_0x00fd
        L_0x00fb:
            java.lang.String r3 = r3.c
        L_0x00fd:
            r0.p = r3
            r3 = -1
            int r4 = r9.c
            r5 = 3
            if (r4 == r5) goto L_0x0112
            com.autonavi.sdk.location.LocationInstrument r3 = com.autonavi.sdk.location.LocationInstrument.getInstance()
            android.location.Location r3 = r3.getLatestLocation()
            float r3 = r3.getAccuracy()
            int r3 = (int) r3
        L_0x0112:
            r0.B = r3
        L_0x0114:
            if (r0 == 0) goto L_0x018b
            if (r0 != 0) goto L_0x0126
            com.autonavi.map.fragmentcontainer.page.IPage r0 = r9.mPage
            com.autonavi.minimap.basemap.traffic.page.TrafficAlarmPage r0 = (com.autonavi.minimap.basemap.traffic.page.TrafficAlarmPage) r0
            int r3 = com.autonavi.minimap.R.string.oper_commit_content_err
            java.lang.String r0 = r0.getString(r3)
            com.amap.bundle.utils.ui.ToastHelper.showToast(r0)
            goto L_0x018b
        L_0x0126:
            r0.o = r2
            java.lang.System.currentTimeMillis()
            java.lang.Class<com.autonavi.minimap.basemap.traffic.inter.ITrafficRequestManager> r3 = com.autonavi.minimap.basemap.traffic.inter.ITrafficRequestManager.class
            java.lang.Object r3 = defpackage.ank.a(r3)
            com.autonavi.minimap.basemap.traffic.inter.ITrafficRequestManager r3 = (com.autonavi.minimap.basemap.traffic.inter.ITrafficRequestManager) r3
            if (r3 == 0) goto L_0x018b
            com.autonavi.map.fragmentcontainer.page.IPage r4 = r9.mPage
            com.autonavi.minimap.basemap.traffic.page.AbstractTrafficSubmitPage r4 = (com.autonavi.minimap.basemap.traffic.page.AbstractTrafficSubmitPage) r4
            android.app.Activity r4 = r4.getActivity()
            com.autonavi.map.widget.ProgressDlg r5 = r9.o
            if (r5 != 0) goto L_0x0165
            com.autonavi.map.widget.ProgressDlg r5 = new com.autonavi.map.widget.ProgressDlg
            com.autonavi.map.fragmentcontainer.page.IPage r6 = r9.mPage
            com.autonavi.minimap.basemap.traffic.page.AbstractTrafficSubmitPage r6 = (com.autonavi.minimap.basemap.traffic.page.AbstractTrafficSubmitPage) r6
            int r7 = com.autonavi.minimap.R.string.traffic_report_processing
            java.lang.String r6 = r6.getString(r7)
            r5.<init>(r4, r6)
            r9.o = r5
            com.autonavi.map.widget.ProgressDlg r5 = r9.o
            r5.setCanceledOnTouchOutside(r1)
            com.autonavi.map.widget.ProgressDlg r5 = r9.o
            com.amap.bundle.commonui.loading.LoadingView r5 = r5.getLoadingView()
            com.autonavi.minimap.basemap.traffic.presenter.AbstractTrafficSubmitPresenter$1 r6 = new com.autonavi.minimap.basemap.traffic.presenter.AbstractTrafficSubmitPresenter$1
            r6.<init>()
            r5.setOnCloseClickListener(r6)
        L_0x0165:
            com.autonavi.map.widget.ProgressDlg r5 = r9.o
            if (r5 == 0) goto L_0x017c
            com.autonavi.map.widget.ProgressDlg r5 = r9.o
            boolean r5 = r5.isShowing()
            if (r5 != 0) goto L_0x017c
            boolean r4 = r4.isFinishing()
            if (r4 != 0) goto L_0x017c
            com.autonavi.map.widget.ProgressDlg r4 = r9.o
            r4.show()
        L_0x017c:
            r9.m = r2
            com.autonavi.server.TrafficAosUICallback r4 = r9.t
            r3.b(r0, r4)
            goto L_0x018b
        L_0x0184:
            int r0 = com.autonavi.minimap.R.id.traffic_report_right_now_shield
            if (r10 != r0) goto L_0x018b
            r9.b(r2)
        L_0x018b:
            int r0 = com.autonavi.minimap.R.id.traffic_report_hurt
            if (r10 != r0) goto L_0x0195
            com.autonavi.minimap.basemap.traffic.presenter.TrafficAlarmPresenter$LogEvent r10 = com.autonavi.minimap.basemap.traffic.presenter.TrafficAlarmPresenter.LogEvent.HURT
            r9.a(r10)
            return
        L_0x0195:
            int r0 = com.autonavi.minimap.R.id.traffic_report_status_layout
            if (r10 != r0) goto L_0x020a
            com.autonavi.minimap.basemap.traffic.presenter.TrafficAlarmPresenter$LogEvent r10 = com.autonavi.minimap.basemap.traffic.presenter.TrafficAlarmPresenter.LogEvent.TIP
            r9.a(r10)
            com.autonavi.map.fragmentcontainer.page.IPage r10 = r9.mPage
            com.autonavi.minimap.basemap.traffic.page.TrafficAlarmPage r10 = (com.autonavi.minimap.basemap.traffic.page.TrafficAlarmPage) r10
            android.content.Context r10 = r10.getContext()
            boolean r10 = defpackage.aaw.c(r10)
            if (r10 != 0) goto L_0x01ba
            com.autonavi.map.fragmentcontainer.page.IPage r10 = r9.mPage
            com.autonavi.minimap.basemap.traffic.page.TrafficAlarmPage r10 = (com.autonavi.minimap.basemap.traffic.page.TrafficAlarmPage) r10
            int r0 = com.autonavi.minimap.R.string.oper_check_network_err
            java.lang.String r10 = r10.getString(r0)
            com.amap.bundle.utils.ui.ToastHelper.showToast(r10)
            return
        L_0x01ba:
            java.lang.String r10 = r9.q
            boolean r10 = android.text.TextUtils.isEmpty(r10)
            if (r10 != 0) goto L_0x020a
            com.amap.bundle.mapstorage.MapSharePreference r10 = new com.amap.bundle.mapstorage.MapSharePreference
            com.amap.bundle.mapstorage.MapSharePreference$SharePreferenceName r0 = com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName.SharedPreferences
            r10.<init>(r0)
            java.lang.String r0 = "2"
            java.lang.String r3 = r9.r
            boolean r0 = r0.equals(r3)
            if (r0 != 0) goto L_0x01dd
            java.lang.String r0 = "5"
            java.lang.String r3 = r9.r
            boolean r0 = r0.equals(r3)
            if (r0 == 0) goto L_0x01ea
        L_0x01dd:
            java.lang.String r0 = "isShow_alarm_tip"
            boolean r0 = r10.getBooleanValue(r0, r2)
            if (r0 == 0) goto L_0x01ea
            java.lang.String r0 = "isShow_alarm_tip"
            r10.putBooleanValue(r0, r1)
        L_0x01ea:
            com.autonavi.map.fragmentcontainer.page.IPage r10 = r9.mPage
            com.autonavi.minimap.basemap.traffic.page.TrafficAlarmPage r10 = (com.autonavi.minimap.basemap.traffic.page.TrafficAlarmPage) r10
            android.app.Activity r0 = r10.getActivity()
            java.lang.String r2 = "input_method"
            java.lang.Object r0 = r0.getSystemService(r2)
            android.view.inputmethod.InputMethodManager r0 = (android.view.inputmethod.InputMethodManager) r0
            android.view.View r10 = r10.getContentView()
            android.os.IBinder r10 = r10.getWindowToken()
            r0.hideSoftInputFromWindow(r10, r1)
            java.lang.String r10 = r9.q
            r9.a(r10)
        L_0x020a:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.basemap.traffic.presenter.TrafficAlarmPresenter.onClick(android.view.View):void");
    }

    public final void d() {
        a(LogEvent.TAKE_PICTURE);
    }

    public final void e() {
        a(LogEvent.DEL_PICTURE);
    }

    public final void f() {
        a(LogEvent.POI);
    }

    /* access modifiers changed from: protected */
    public final void a() {
        boolean z;
        super.a();
        if (this.p != null) {
            this.p.a(new TrafficAosUICallback() {
                public final void a(int i, String str) {
                }

                public final void a(JSONObject jSONObject) {
                    String optString = jSONObject.optString("amapContent");
                    TrafficAlarmPresenter.this.q = jSONObject.optString("h5url");
                    try {
                        JSONArray jSONArray = jSONObject.getJSONArray("process");
                        if (jSONArray != null) {
                            TrafficAlarmPresenter.this.r = jSONArray.getJSONObject(0).optString("status");
                            MapSharePreference mapSharePreference = new MapSharePreference(SharePreferenceName.SharedPreferences);
                            boolean booleanValue = mapSharePreference.getBooleanValue("isShow_alarm_tip", true);
                            if (!"2".equals(TrafficAlarmPresenter.this.r) && !"5".equals(TrafficAlarmPresenter.this.r)) {
                                if (!TextUtils.isEmpty(optString) && !TextUtils.isEmpty(TrafficAlarmPresenter.this.q)) {
                                    ((TrafficAlarmPage) TrafficAlarmPresenter.this.mPage).b(0);
                                    ((TrafficAlarmPage) TrafficAlarmPresenter.this.mPage).a((CharSequence) optString);
                                }
                                mapSharePreference.putBooleanValue("isShow_alarm_tip", true);
                            } else if ("5".equals(TrafficAlarmPresenter.this.r) || "2".equals(TrafficAlarmPresenter.this.r)) {
                                if (booleanValue) {
                                    ((TrafficAlarmPage) TrafficAlarmPresenter.this.mPage).b(0);
                                    ((TrafficAlarmPage) TrafficAlarmPresenter.this.mPage).a((CharSequence) optString);
                                    return;
                                }
                                ((TrafficAlarmPage) TrafficAlarmPresenter.this.mPage).b(8);
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        IAccountService iAccountService = (IAccountService) a.a.a(IAccountService.class);
        if (iAccountService == null) {
            z = false;
        } else {
            z = iAccountService.a();
        }
        if (z && !TextUtils.isEmpty(j())) {
            ((TrafficAlarmPage) this.mPage).b.setText(j());
            Editable text = ((TrafficAlarmPage) this.mPage).b.getText();
            if (text instanceof Spannable) {
                Selection.setSelection(text, text.length());
            }
        }
        k();
    }

    private boolean b(boolean z) {
        if (bnz.b(((TrafficAlarmPage) this.mPage).b.getText().toString())) {
            return true;
        }
        if (z) {
            ToastHelper.showToast(((TrafficAlarmPage) this.mPage).getString(R.string.oper_no_phone_err));
        }
        return false;
    }

    /* access modifiers changed from: private */
    public void k() {
        if (b(false)) {
            ((TrafficAlarmPage) this.mPage).b(true);
            ((TrafficAlarmPage) this.mPage).a(8);
            return;
        }
        ((TrafficAlarmPage) this.mPage).b(false);
        ((TrafficAlarmPage) this.mPage).a(0);
    }

    /* access modifiers changed from: private */
    public void a(String str) {
        aja aja = new aja(str);
        aja.b = new ajf();
        aix aix = (aix) a.a.a(aix.class);
        if (aix != null) {
            aix.a((bid) this.mPage, aja, 1);
        }
    }

    private void a(LogEvent logEvent) {
        String str = "";
        JSONObject jSONObject = null;
        switch (logEvent) {
            case POI:
                str = "B004";
                break;
            case TAKE_PICTURE:
            case DEL_PICTURE:
                str = "B005";
                jSONObject = new JSONObject();
                try {
                    jSONObject.put("status", ((TrafficAlarmPage) this.mPage).getString(logEvent == LogEvent.TAKE_PICTURE ? R.string.log_event_take_picture : R.string.log_event_del_picture));
                    break;
                } catch (JSONException e) {
                    e.printStackTrace();
                    break;
                }
            case REPORT:
                str = "B001";
                break;
            case HURT:
                str = "B003";
                break;
            case TIP:
                str = "B002";
                break;
        }
        if (jSONObject == null) {
            LogManager.actionLogV2(LogConstant.MAIN_MAP_TRAFFIC_ALARM_SUBMIT, str);
        } else {
            LogManager.actionLogV2(LogConstant.MAIN_MAP_TRAFFIC_ALARM_SUBMIT, str, jSONObject);
        }
    }

    private static String j() {
        IAccountService iAccountService = (IAccountService) a.a.a(IAccountService.class);
        if (iAccountService == null) {
            return "";
        }
        ant e = iAccountService.e();
        if (e == null) {
            return "";
        }
        return e.h;
    }
}
