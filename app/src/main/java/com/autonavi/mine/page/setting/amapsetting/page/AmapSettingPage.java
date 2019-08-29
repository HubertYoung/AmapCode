package com.autonavi.mine.page.setting.amapsetting.page;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.alipay.android.phone.mobilesdk.socketcraft.monitor.DataflowMonitorModel;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName;
import com.amap.bundle.statistics.LogManager;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.ae.gmap.glinterface.GLGeoPoint;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.annotation.PageAction;
import com.autonavi.bundle.account.api.IAccountService;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.map.core.LocationMode.LocationNetworkOnly;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.map.fragmentcontainer.page.IPresenter;
import com.autonavi.map.widget.ProgressDlg;
import com.autonavi.mine.page.setting.amapsetting.presenter.AmapSettingPresenter;
import com.autonavi.mine.page.setting.sysabout.page.SysAboutPage;
import com.autonavi.mine.page.setting.sysmapset.page.SysMapSettingPage;
import com.autonavi.miniapp.monitor.biz.logmonitor.util.upload.UploadConstants;
import com.autonavi.minimap.R;
import com.autonavi.minimap.account.logout.model.LogoutResponse;
import com.autonavi.minimap.ajx3.Ajx3DialogPage;
import com.autonavi.minimap.ajx3.Ajx3Path;
import com.autonavi.minimap.bundle.msgbox.util.LaboratoryStatusStringDef;
import com.autonavi.minimap.offline.auto.protocol.utils.AutoJsonUtils;
import com.autonavi.sdk.log.util.LogConstant;
import com.autonavi.widget.ui.AlertView;
import com.autonavi.widget.ui.TitleBar;
import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.regex.PatternSyntaxException;
import org.json.JSONException;
import org.json.JSONObject;

@PageAction("amap.basemap.action.amap_setting_page")
public class AmapSettingPage extends AbstractBasePage<AmapSettingPresenter> implements LocationNetworkOnly {
    public final String a = "basemap";
    public final String b = "userIndividualityType";
    public final int c = 1;
    public CheckBox d;
    public CheckBox e;
    RelativeLayout f;
    RelativeLayout g;
    ProgressDlg h;
    private RelativeLayout i;
    private TextView j;
    private RelativeLayout k;
    private RelativeLayout l;
    private RelativeLayout m;
    private RelativeLayout n;
    private RelativeLayout o;
    private RelativeLayout p;
    private RelativeLayout q;
    private RelativeLayout r;
    private RelativeLayout s;
    private RelativeLayout t;
    private TextView u;
    private TextView v;
    private TextView w;
    private TitleBar x;
    private View y;
    private String[] z = {"驾车", "公交"};

    static class a implements OnClickListener {
        private WeakReference<AmapSettingPage> a;

        public a(AmapSettingPage amapSettingPage) {
            this.a = new WeakReference<>(amapSettingPage);
        }

        public final void onClick(View view) {
            AmapSettingPage amapSettingPage = (AmapSettingPage) this.a.get();
            if (amapSettingPage != null) {
                int id = view.getId();
                if (id == R.id.autonavi_browser_map_setting) {
                    AmapSettingPage.b(amapSettingPage);
                } else if (id == R.id.autonavi_browser_navi_setting) {
                    AmapSettingPage.c(amapSettingPage);
                } else if (id == R.id.rl_switch_city) {
                    AmapSettingPage.d(amapSettingPage);
                } else if (id == R.id.clear_cache) {
                    LogManager.actionLogV2(LogConstant.PAGE_MORE, "B047");
                    AmapSettingPage.e(amapSettingPage);
                } else if (id == R.id.push) {
                    amapSettingPage.d();
                } else if (id == R.id.about) {
                    amapSettingPage.startPage(SysAboutPage.class, (PageBundle) null);
                } else if (id == R.id.check_update) {
                    AmapSettingPage.a(amapSettingPage, view);
                } else if (id == R.id.auto_update_app) {
                    amapSettingPage.e.toggle();
                    if (amapSettingPage.e.isChecked()) {
                        bim.aa().a((String) UploadConstants.STATUS_PUSH_NOTIFIED, 1);
                    } else {
                        bim.aa().a((String) UploadConstants.STATUS_PUSH_NOTIFIED, 0);
                    }
                } else if (id == R.id.laboratory) {
                    MapSharePreference mapSharePreference = new MapSharePreference(SharePreferenceName.SharedPreferences);
                    mapSharePreference.putBooleanValue(LaboratoryStatusStringDef.SP_KEY_MAIN_HEADER_RED_FLAG, false);
                    mapSharePreference.putBooleanValue("laboratory_red_flag", false);
                    ((ImageView) view.findViewById(R.id.red_flag)).setVisibility(8);
                    mapSharePreference.putBooleanValue(LaboratoryStatusStringDef.SP_KEY_LABORATORY_RED_SHOW_FLAG, false);
                    LogManager.actionLogV2("P00352", "B002");
                    Intent intent = new Intent();
                    intent.setData(Uri.parse("amapuri://ajx?path=path://amap_bundle_laboratory/src/BizBasemapLaboratoryMain.page.js"));
                    DoNotUseTool.startScheme(intent);
                } else if (id == R.id.rl_routecommute) {
                    AmapSettingPage.i(amapSettingPage);
                } else if (id == R.id.autonavi_browser_voice_setting) {
                    AmapSettingPage.c();
                } else if (id == R.id.rl_trip_mode) {
                    PageBundle pageBundle = new PageBundle();
                    pageBundle.putString("url", Ajx3Path.TRIP_MODE_SETTING);
                    amapSettingPage.getClass();
                    amapSettingPage.startPageForResult(Ajx3DialogPage.class, pageBundle, 1);
                } else if (id == R.id.rl_privacy) {
                    PageBundle pageBundle2 = new PageBundle();
                    pageBundle2.putString("url", "path://amap_bundle_setting/src/pages/PrivacySetting.page.js");
                    amapSettingPage.startPage(Ajx3DialogPage.class, pageBundle2);
                } else {
                    if (id == R.id.help_center) {
                        LogManager.actionLogV2("P00352", "B007");
                        Intent intent2 = new Intent();
                        intent2.setData(Uri.parse("amapuri://feedback/trackmyfeedback"));
                        DoNotUseTool.startScheme(intent2);
                    }
                }
            }
        }
    }

    public void onCreate(Context context) {
        super.onCreate(context);
        requestScreenOrientation(1);
        setContentView(R.layout.setting_main_fragment);
        View contentView = getContentView();
        this.j = (TextView) contentView.findViewById(R.id.icon_autonavi_browser_map_setting);
        this.i = (RelativeLayout) contentView.findViewById(R.id.push);
        this.q = (RelativeLayout) contentView.findViewById(R.id.rl_switch_city);
        this.v = (TextView) contentView.findViewById(R.id.tv_current_city);
        this.m = (RelativeLayout) contentView.findViewById(R.id.auto_update_app);
        this.k = (RelativeLayout) contentView.findViewById(R.id.autonavi_browser_navi_setting);
        this.l = (RelativeLayout) contentView.findViewById(R.id.clear_cache);
        this.n = (RelativeLayout) contentView.findViewById(R.id.check_update);
        this.o = (RelativeLayout) contentView.findViewById(R.id.about);
        this.y = contentView.findViewById(R.id.rl_routecommute);
        axv axv = (axv) defpackage.esb.a.a.a(axv.class);
        if (axv != null) {
            if (axv.j()) {
                this.y.setVisibility(0);
            } else {
                this.y.setVisibility(8);
            }
        }
        this.p = (RelativeLayout) contentView.findViewById(R.id.laboratory);
        if (new bnv().a()) {
            this.r = (RelativeLayout) contentView.findViewById(R.id.rl_trip_mode);
            this.r.setVisibility(0);
            this.u = (TextView) contentView.findViewById(R.id.tv_current_trip_mode);
        }
        this.s = (RelativeLayout) contentView.findViewById(R.id.rl_privacy);
        IAccountService iAccountService = (IAccountService) defpackage.esb.a.a.a(IAccountService.class);
        if (iAccountService == null || !iAccountService.a()) {
            try {
                if (new JSONObject(lo.a().a((String) "profile")).optInt("unloggedState", 0) == 1) {
                    this.s.setVisibility(0);
                }
            } catch (Exception unused) {
            }
        } else {
            this.s.setVisibility(0);
        }
        this.t = (RelativeLayout) contentView.findViewById(R.id.help_center);
        String a2 = lo.a().a((String) "lab_lablist_order");
        if (!TextUtils.isEmpty(a2)) {
            try {
                Iterator<String> keys = new JSONObject(a2).keys();
                while (true) {
                    if (!keys.hasNext()) {
                        break;
                    }
                    if (!TextUtils.isEmpty(lo.a().a(keys.next()))) {
                        this.p.setVisibility(0);
                        LogManager.actionLogV2("P00352", "B001");
                        MapSharePreference mapSharePreference = new MapSharePreference(SharePreferenceName.SharedPreferences);
                        if (mapSharePreference.getBooleanValue("laboratory_red_flag", false)) {
                            ((ImageView) contentView.findViewById(R.id.red_flag)).setVisibility(0);
                            mapSharePreference.putBooleanValue(LaboratoryStatusStringDef.SP_KEY_LABORATORY_RED_SHOW_FLAG, true);
                        }
                    }
                }
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }
        this.w = (TextView) contentView.findViewById(R.id.current_version);
        try {
            this.w.setText(c(getContext().getPackageManager().getPackageInfo(getContext().getPackageName(), 0).versionName));
        } catch (NameNotFoundException e3) {
            e3.printStackTrace();
        }
        this.d = (CheckBox) contentView.findViewById(R.id.check_message_push);
        this.e = (CheckBox) contentView.findViewById(R.id.check_auto_update_app);
        this.x = (TitleBar) contentView.findViewById(R.id.title);
        this.x.setTitle(getString(R.string.soft_setting));
        this.x.setOnBackClickListener((OnClickListener) new OnClickListener() {
            public final void onClick(View view) {
                AmapSettingPage.this.finish();
            }
        });
        lt.a().c();
        this.j.setText("");
        bty mapView = DoNotUseTool.getMapView();
        if (mapView != null) {
            GLGeoPoint n2 = mapView.n();
            if (n2 != null) {
                GeoPoint glGeoPoint2GeoPoint = GeoPoint.glGeoPoint2GeoPoint(n2);
                if (glGeoPoint2GeoPoint != null) {
                    String city = glGeoPoint2GeoPoint.getCity();
                    if (city != null) {
                        TextView textView = this.v;
                        StringBuilder sb = new StringBuilder();
                        sb.append(getString(R.string.settings_current_city));
                        sb.append(city);
                        textView.setText(sb.toString());
                    }
                } else {
                    this.v.setText("");
                }
            } else {
                this.v.setText("");
            }
        } else {
            this.v.setText("");
        }
        this.d.setChecked(bim.aa().k((String) UploadConstants.STATUS_PUSH_RECEIVED));
        this.e.setChecked(bim.aa().k((String) UploadConstants.STATUS_PUSH_NOTIFIED));
        this.d.setContentDescription(getString(this.d.isChecked() ? R.string.checkbox_open_desc : R.string.checkbox_close_desc));
        this.e.setContentDescription(getString(this.e.isChecked() ? R.string.checkbox_open_desc : R.string.checkbox_close_desc));
        AnonymousClass2 r7 = new OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                compoundButton.setContentDescription(AmapSettingPage.this.getString(compoundButton.isChecked() ? R.string.checkbox_open_desc : R.string.checkbox_close_desc));
            }
        };
        this.d.setOnCheckedChangeListener(r7);
        this.e.setOnCheckedChangeListener(r7);
        contentView.findViewById(R.id.autonavi_browser_map_setting).setOnClickListener(new a(this));
        contentView.findViewById(R.id.autonavi_browser_navi_setting).setOnClickListener(new a(this));
        contentView.findViewById(R.id.autonavi_browser_voice_setting).setOnClickListener(new a(this));
        contentView.findViewById(R.id.rl_switch_city).setOnClickListener(new a(this));
        contentView.findViewById(R.id.rl_trip_mode).setOnClickListener(new a(this));
        contentView.findViewById(R.id.rl_privacy).setOnClickListener(new a(this));
        contentView.findViewById(R.id.help_center).setOnClickListener(new a(this));
        contentView.findViewById(R.id.clear_cache).setOnClickListener(new a(this));
        contentView.findViewById(R.id.push).setOnClickListener(new a(this));
        contentView.findViewById(R.id.about).setOnClickListener(new a(this));
        contentView.findViewById(R.id.laboratory).setOnClickListener(new a(this));
        contentView.findViewById(R.id.check_update).setOnClickListener(new a(this));
        this.y.setOnClickListener(new a(this));
        this.m.setOnClickListener(new a(this));
        if (bno.b) {
            this.m.setVisibility(8);
            this.n.setVisibility(8);
        }
        this.g = (RelativeLayout) contentView.findViewById(R.id.autonavi_browser_account_security);
        this.f = (RelativeLayout) contentView.findViewById(R.id.setting_main_logout_layout);
        IAccountService iAccountService2 = (IAccountService) defpackage.esb.a.a.a(IAccountService.class);
        if (iAccountService2 != null) {
            if (iAccountService2.a()) {
                this.f.setVisibility(0);
            } else {
                this.f.setVisibility(8);
            }
            iAccountService2.a((anp) new anp() {
                public final void onUserInfoUpdate(ant ant) {
                }

                public final void onLoginStateChanged(boolean z, final boolean z2) {
                    aho.a(new Runnable() {
                        public final void run() {
                            if (z2) {
                                AmapSettingPage.this.f.setVisibility(0);
                            } else {
                                AmapSettingPage.this.f.setVisibility(8);
                            }
                        }
                    });
                }
            });
        }
        this.g.setOnClickListener(new OnClickListener() {
            public final void onClick(View view) {
                AmapSettingPage.b((String) "B004");
                if (!aaw.a()) {
                    ToastHelper.showToast(AmapSettingPage.this.getResources().getString(R.string.account_network_error));
                    return;
                }
                IAccountService iAccountService = (IAccountService) defpackage.esb.a.a.a(IAccountService.class);
                if (iAccountService != null) {
                    if (iAccountService.a()) {
                        iAccountService.c(AmapSettingPage.this.getPageContext());
                        return;
                    }
                    JSONObject jSONObject = new JSONObject();
                    try {
                        jSONObject.put("from", 2);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    LogManager.actionLogV2("P00400", "B001", jSONObject);
                    iAccountService.a(AmapSettingPage.this.getPageContext(), (anq) null);
                }
            }
        });
        ((Button) contentView.findViewById(R.id.setting_main_logout)).setOnClickListener(new OnClickListener() {
            public final void onClick(View view) {
                LogManager.actionLogV2("P00352", "B005");
                AmapSettingPage.a(AmapSettingPage.this);
            }
        });
        b((String) "B003");
    }

    /* access modifiers changed from: private */
    public static void b(String str) {
        JSONObject jSONObject = new JSONObject();
        IAccountService iAccountService = (IAccountService) defpackage.esb.a.a.a(IAccountService.class);
        int i2 = 0;
        if (iAccountService != null ? iAccountService.a() : false) {
            i2 = 1;
        }
        try {
            jSONObject.put("type", i2);
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        LogManager.actionLogV2("P00352", str, jSONObject);
    }

    public final void a() {
        if (this.u != null) {
            String str = "";
            String stringValue = new MapSharePreference((String) "basemap").getStringValue("userIndividualityType", "");
            if ("1".equals(stringValue)) {
                str = this.z[0];
            } else if ("2".equals(stringValue)) {
                str = this.z[1];
            }
            this.u.setText(str);
        }
    }

    public final void b() {
        if (this.h != null) {
            this.h.dismiss();
        }
    }

    /* access modifiers changed from: private */
    public JSONObject d() {
        this.d.toggle();
        if (this.d.isChecked()) {
            bim.aa().a((String) UploadConstants.STATUS_PUSH_RECEIVED, 1);
        } else {
            fhb fhb = (fhb) defpackage.esb.a.a.a(fhb.class);
            if (fhb != null) {
                fhb.d().a();
            }
            bim.aa().a((String) UploadConstants.STATUS_PUSH_RECEIVED, 0);
        }
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("action", this.d.isChecked() ? "open" : DataflowMonitorModel.METHOD_NAME_CLOSE);
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        LogManager.actionLogV2("P00065", "B004", jSONObject);
        JSONObject jSONObject2 = new JSONObject();
        try {
            jSONObject2.put("type", this.d.isChecked());
        } catch (JSONException e3) {
            e3.printStackTrace();
        }
        return jSONObject2;
    }

    public final void a(int i2) {
        Context context = this.j.getContext();
        if (context != null) {
            ToastHelper.showLongToast(context.getString(i2));
        }
    }

    private String c(String str) {
        if (TextUtils.isEmpty(str)) {
            return AMapAppGlobal.getApplication().getString(R.string.current_newest_version);
        }
        String string = new MapSharePreference((String) "SharedPreferences").sharedPrefs().getString("update_hint_config_file_download_complete_version", "");
        if (TextUtils.isEmpty(string)) {
            return AMapAppGlobal.getApplication().getString(R.string.current_newest_version);
        }
        try {
            String replace = str.replace(".", "");
            String replace2 = string.replace(".", "");
            if (TextUtils.isDigitsOnly(replace) && TextUtils.isDigitsOnly(replace2) && Long.parseLong(replace2) > Long.parseLong(replace)) {
                findViewById(R.id.iv_new_version).setVisibility(0);
                return String.format(AMapAppGlobal.getApplication().getString(R.string.update_to_version), new Object[]{string});
            }
        } catch (PatternSyntaxException e2) {
            e2.printStackTrace();
        }
        return AMapAppGlobal.getApplication().getString(R.string.current_newest_version);
    }

    public /* synthetic */ IPresenter createPresenter() {
        return new AmapSettingPresenter(this);
    }

    static /* synthetic */ void a(AmapSettingPage amapSettingPage) {
        com.autonavi.widget.ui.AlertView.a aVar = new com.autonavi.widget.ui.AlertView.a(amapSettingPage.getActivity());
        aVar.a(R.string.sns_log_out);
        String string = amapSettingPage.getResources().getString(R.string.exit_application);
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(string);
        spannableStringBuilder.setSpan(new ForegroundColorSpan(amapSettingPage.getResources().getColor(R.color.f_c_8)), 0, string.length(), 33);
        aVar.a((CharSequence) spannableStringBuilder, (defpackage.ern.a) new defpackage.ern.a() {
            public final void onClick(AlertView alertView, int i) {
                LogManager.actionLogV2("P00352", "B006");
                AmapSettingPage.this.dismissViewLayer(alertView);
                if (!aaw.a()) {
                    ToastHelper.showToast(AmapSettingPage.this.getResources().getString(R.string.account_network_error));
                    return;
                }
                IAccountService iAccountService = (IAccountService) defpackage.esb.a.a.a(IAccountService.class);
                if (iAccountService != null) {
                    AmapSettingPage amapSettingPage = AmapSettingPage.this;
                    if (amapSettingPage.h == null) {
                        amapSettingPage.h = new ProgressDlg(amapSettingPage.getActivity(), null);
                        amapSettingPage.h.setCanceledOnTouchOutside(false);
                    }
                    if (!TextUtils.isEmpty("处理中")) {
                        amapSettingPage.h.setMessage("处理中");
                    }
                    if (amapSettingPage.h != null && !amapSettingPage.getActivity().isFinishing()) {
                        amapSettingPage.h.show();
                    }
                    iAccountService.a((dko<LogoutResponse>) new dko<LogoutResponse>() {
                        /* JADX WARNING: Code restructure failed: missing block: B:39:0x00ed, code lost:
                            if (r1 != false) goto L_0x00ff;
                         */
                        /* Code decompiled incorrectly, please refer to instructions dump. */
                        public final /* synthetic */ void a(defpackage.dkm r12) {
                            /*
                                r11 = this;
                                com.autonavi.minimap.account.logout.model.LogoutResponse r12 = (com.autonavi.minimap.account.logout.model.LogoutResponse) r12
                                if (r12 == 0) goto L_0x0119
                                int r0 = r12.code
                                r1 = 1
                                if (r0 != r1) goto L_0x0025
                                com.autonavi.mine.page.setting.amapsetting.page.AmapSettingPage$11 r12 = com.autonavi.mine.page.setting.amapsetting.page.AmapSettingPage.AnonymousClass11.this
                                com.autonavi.mine.page.setting.amapsetting.page.AmapSettingPage r12 = com.autonavi.mine.page.setting.amapsetting.page.AmapSettingPage.this
                                android.view.View r12 = r12.getContentView()
                                if (r12 == 0) goto L_0x00ff
                                com.autonavi.mine.page.setting.amapsetting.page.AmapSettingPage$11 r12 = com.autonavi.mine.page.setting.amapsetting.page.AmapSettingPage.AnonymousClass11.this
                                com.autonavi.mine.page.setting.amapsetting.page.AmapSettingPage r12 = com.autonavi.mine.page.setting.amapsetting.page.AmapSettingPage.this
                                android.view.View r12 = r12.getContentView()
                                com.autonavi.mine.page.setting.amapsetting.page.AmapSettingPage$11$1$1 r0 = new com.autonavi.mine.page.setting.amapsetting.page.AmapSettingPage$11$1$1
                                r0.<init>()
                                r12.post(r0)
                                goto L_0x00ff
                            L_0x0025:
                                dsz r0 = new dsz
                                com.autonavi.mine.page.setting.amapsetting.page.AmapSettingPage$11 r2 = com.autonavi.mine.page.setting.amapsetting.page.AmapSettingPage.AnonymousClass11.this
                                com.autonavi.mine.page.setting.amapsetting.page.AmapSettingPage r2 = com.autonavi.mine.page.setting.amapsetting.page.AmapSettingPage.this
                                r0.<init>(r2)
                                com.autonavi.minimap.account.logout.model.LogoutOrderConf r2 = r12.orderConf
                                if (r2 == 0) goto L_0x00ef
                                int r8 = r12.code
                                java.lang.String r4 = r2.text
                                java.lang.String r5 = r2.schema
                                java.lang.String r6 = r2.okText
                                java.lang.String r2 = r2.cancelText
                                r3 = 10080(0x2760, float:1.4125E-41)
                                r7 = 0
                                r9 = 10064(0x2750, float:1.4103E-41)
                                if (r8 == r3) goto L_0x0048
                                if (r8 == r9) goto L_0x0048
                            L_0x0045:
                                r1 = 0
                                goto L_0x00ed
                            L_0x0048:
                                java.lang.ref.WeakReference<bid> r3 = r0.a
                                java.lang.Object r3 = r3.get()
                                bid r3 = (defpackage.bid) r3
                                if (r3 == 0) goto L_0x0045
                                android.view.View r10 = r3.getContentView()
                                if (r10 != 0) goto L_0x0059
                                goto L_0x0045
                            L_0x0059:
                                if (r8 != r9) goto L_0x0082
                                android.os.Looper r2 = android.os.Looper.myLooper()
                                android.os.Looper r4 = android.os.Looper.getMainLooper()
                                if (r2 == r4) goto L_0x0073
                                android.view.View r2 = r3.getContentView()
                                dsz$1 r4 = new dsz$1
                                r4.<init>(r0, r3)
                                r2.post(r4)
                                goto L_0x00ed
                            L_0x0073:
                                android.content.Context r2 = r3.getContext()
                                int r3 = com.autonavi.minimap.R.string.account_logout_errmsg_10064
                                java.lang.String r2 = r2.getString(r3)
                                r0.a(r2)
                                goto L_0x00ed
                            L_0x0082:
                                boolean r7 = android.text.TextUtils.isEmpty(r2)
                                if (r7 == 0) goto L_0x0092
                                android.content.Context r2 = r3.getContext()
                                int r7 = com.autonavi.minimap.R.string.i_know
                                java.lang.String r2 = r2.getString(r7)
                            L_0x0092:
                                r7 = r2
                                boolean r2 = android.text.TextUtils.isEmpty(r5)
                                if (r2 != 0) goto L_0x00c9
                                boolean r2 = android.text.TextUtils.isEmpty(r4)
                                if (r2 != 0) goto L_0x00c9
                                boolean r2 = android.text.TextUtils.isEmpty(r6)
                                if (r2 == 0) goto L_0x00a6
                                goto L_0x00c9
                            L_0x00a6:
                                android.os.Looper r2 = android.os.Looper.myLooper()
                                android.os.Looper r9 = android.os.Looper.getMainLooper()
                                if (r2 == r9) goto L_0x00bf
                                android.view.View r9 = r3.getContentView()
                                dsz$4 r10 = new dsz$4
                                r2 = r10
                                r3 = r0
                                r2.<init>(r3, r4, r5, r6, r7, r8)
                                r9.post(r10)
                                goto L_0x00ed
                            L_0x00bf:
                                r2 = r0
                                r3 = r4
                                r4 = r5
                                r5 = r6
                                r6 = r7
                                r7 = r8
                                r2.a(r3, r4, r5, r6, r7)
                                goto L_0x00ed
                            L_0x00c9:
                                android.os.Looper r2 = android.os.Looper.myLooper()
                                android.os.Looper r4 = android.os.Looper.getMainLooper()
                                if (r2 == r4) goto L_0x00e0
                                android.view.View r2 = r3.getContentView()
                                dsz$3 r4 = new dsz$3
                                r4.<init>(r0, r3)
                                r2.post(r4)
                                goto L_0x00ed
                            L_0x00e0:
                                android.content.Context r2 = r3.getContext()
                                int r3 = com.autonavi.minimap.R.string.account_logout_errmsg_default
                                java.lang.String r2 = r2.getString(r3)
                                r0.a(r2)
                            L_0x00ed:
                                if (r1 != 0) goto L_0x00ff
                            L_0x00ef:
                                java.lang.String r0 = r12.errmsg
                                boolean r0 = android.text.TextUtils.isEmpty(r0)
                                if (r0 != 0) goto L_0x00fa
                                java.lang.String r12 = r12.errmsg
                                goto L_0x00fc
                            L_0x00fa:
                                java.lang.String r12 = r12.message
                            L_0x00fc:
                                com.amap.bundle.utils.ui.ToastHelper.showLongToast(r12)
                            L_0x00ff:
                                com.autonavi.mine.page.setting.amapsetting.page.AmapSettingPage$11 r12 = com.autonavi.mine.page.setting.amapsetting.page.AmapSettingPage.AnonymousClass11.this
                                com.autonavi.mine.page.setting.amapsetting.page.AmapSettingPage r12 = com.autonavi.mine.page.setting.amapsetting.page.AmapSettingPage.this
                                android.view.View r12 = r12.getContentView()
                                if (r12 == 0) goto L_0x0119
                                com.autonavi.mine.page.setting.amapsetting.page.AmapSettingPage$11 r12 = com.autonavi.mine.page.setting.amapsetting.page.AmapSettingPage.AnonymousClass11.this
                                com.autonavi.mine.page.setting.amapsetting.page.AmapSettingPage r12 = com.autonavi.mine.page.setting.amapsetting.page.AmapSettingPage.this
                                android.view.View r12 = r12.getContentView()
                                com.autonavi.mine.page.setting.amapsetting.page.AmapSettingPage$11$1$2 r0 = new com.autonavi.mine.page.setting.amapsetting.page.AmapSettingPage$11$1$2
                                r0.<init>()
                                r12.post(r0)
                            L_0x0119:
                                return
                            */
                            throw new UnsupportedOperationException("Method not decompiled: com.autonavi.mine.page.setting.amapsetting.page.AmapSettingPage.AnonymousClass11.AnonymousClass1.a(dkm):void");
                        }

                        public final void a(Exception exc) {
                            if (AmapSettingPage.this.getContentView() != null) {
                                AmapSettingPage.this.getContentView().post(new Runnable() {
                                    public final void run() {
                                        AmapSettingPage.this.b();
                                        ToastHelper.showLongToast("退出失败");
                                    }
                                });
                            }
                        }
                    });
                }
            }
        });
        aVar.b(R.string.cancel, (defpackage.ern.a) new defpackage.ern.a() {
            public final void onClick(AlertView alertView, int i) {
                AmapSettingPage.this.dismissViewLayer(alertView);
            }
        });
        aVar.b = new defpackage.ern.a() {
            public final void onClick(AlertView alertView, int i) {
            }
        };
        aVar.c = new defpackage.ern.a() {
            public final void onClick(AlertView alertView, int i) {
            }
        };
        aVar.a(false);
        AlertView a2 = aVar.a();
        amapSettingPage.showViewLayer(a2);
        a2.startAnimation();
    }

    static /* synthetic */ void b(AmapSettingPage amapSettingPage) {
        String str = "";
        bty mapView = DoNotUseTool.getMapView();
        if (!(mapView == null || mapView.n() == null)) {
            str = String.valueOf(GeoPoint.glGeoPoint2GeoPoint(mapView.n()).getAdCode());
        }
        if (TextUtils.isEmpty(str)) {
            str = "";
        }
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(AutoJsonUtils.JSON_ADCODE, str);
        } catch (JSONException e2) {
            kf.a((Throwable) e2);
        }
        LogManager.actionLogV2("P00219", "B005", jSONObject);
        amapSettingPage.startPage(SysMapSettingPage.class, (PageBundle) null);
    }

    static /* synthetic */ void c(AmapSettingPage amapSettingPage) {
        PageBundle pageBundle = new PageBundle();
        pageBundle.putInt("from", 2);
        amapSettingPage.startPage((String) "amap.drive.action.navigation.prefer", pageBundle);
    }

    static /* synthetic */ void d(AmapSettingPage amapSettingPage) {
        PageBundle pageBundle = new PageBundle();
        pageBundle.putInt("SWITCH_CITY_FOR", 1);
        amapSettingPage.startPage((String) "amap.basemap.action.switch_city_node_page", pageBundle);
    }

    static /* synthetic */ void e(AmapSettingPage amapSettingPage) {
        com.autonavi.widget.ui.AlertView.a aVar = new com.autonavi.widget.ui.AlertView.a(amapSettingPage.getActivity());
        aVar.a(R.string.del_cache);
        String string = amapSettingPage.getResources().getString(R.string.del_now);
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(string);
        spannableStringBuilder.setSpan(new ForegroundColorSpan(amapSettingPage.getResources().getColor(R.color.f_c_8)), 0, string.length(), 33);
        aVar.a((CharSequence) spannableStringBuilder, (defpackage.ern.a) new defpackage.ern.a() {
            public final void onClick(AlertView alertView, int i) {
                JSONObject jSONObject = new JSONObject();
                try {
                    jSONObject.put("type", 1);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                LogManager.actionLogV2(LogConstant.PAGE_MORE, "B048", jSONObject);
                AmapSettingPresenter amapSettingPresenter = (AmapSettingPresenter) AmapSettingPage.this.mPresenter;
                if (amapSettingPresenter.a != null) {
                    if (amapSettingPresenter.b == null) {
                        amapSettingPresenter.b = new ProgressDlg(((AmapSettingPage) amapSettingPresenter.mPage).getActivity(), ((AmapSettingPage) amapSettingPresenter.mPage).getString(R.string.ic_delete_cache));
                        amapSettingPresenter.b.setCancelable(true);
                        amapSettingPresenter.b.setOnCancelListener(new OnCancelListener() {
                            public final void onCancel(DialogInterface dialogInterface) {
                                if (AmapSettingPresenter.this.e != null && AmapSettingPresenter.this.e.isAlive()) {
                                    AmapSettingPresenter.this.c = false;
                                }
                            }
                        });
                    }
                    amapSettingPresenter.b.show();
                    amapSettingPresenter.a.a(((AmapSettingPage) amapSettingPresenter.mPage).getContext().getApplicationContext());
                    amapSettingPresenter.a.a();
                }
                AmapSettingPage.this.dismissViewLayer(alertView);
            }
        });
        aVar.b(R.string.cancel, (defpackage.ern.a) new defpackage.ern.a() {
            public final void onClick(AlertView alertView, int i) {
                AmapSettingPage.this.dismissViewLayer(alertView);
                JSONObject jSONObject = new JSONObject();
                try {
                    jSONObject.put("type", 0);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                LogManager.actionLogV2(LogConstant.PAGE_MORE, "B048", jSONObject);
            }
        });
        aVar.b = new defpackage.ern.a() {
            public final void onClick(AlertView alertView, int i) {
            }
        };
        aVar.c = new defpackage.ern.a() {
            public final void onClick(AlertView alertView, int i) {
            }
        };
        aVar.a(false);
        AlertView a2 = aVar.a();
        amapSettingPage.showViewLayer(a2);
        a2.startAnimation();
    }

    static /* synthetic */ void a(AmapSettingPage amapSettingPage, final View view) {
        amapSettingPage.findViewById(R.id.iv_new_version).setVisibility(4);
        view.setEnabled(false);
        view.postDelayed(new Runnable() {
            public final void run() {
                view.setEnabled(true);
            }
        }, 1000);
        Intent intent = new Intent(amapSettingPage.getContext(), AMapAppGlobal.getTopActivity().getClass());
        intent.setAction("action.autonavi.checkappupdate");
        amapSettingPage.startActivity(intent);
    }

    static /* synthetic */ void i(AmapSettingPage amapSettingPage) {
        axw axw = (axw) defpackage.esb.a.a.a(axw.class);
        if (axw != null) {
            axw.b(amapSettingPage.getPageContext(), "grzx");
        }
    }

    static /* synthetic */ void c() {
        bfo bfo = (bfo) defpackage.esb.a.a.a(bfo.class);
        if (bfo != null) {
            bfo.f();
        }
    }
}
