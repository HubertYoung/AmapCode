package com.alipay.mobile.tinyappcustom.permission;

import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.a.a.a.C0098a;
import com.alipay.mobile.antui.basic.AUTextView;
import com.alipay.mobile.antui.basic.AUTitleBar;
import com.alipay.mobile.antui.dialog.AUNoticeDialog;
import com.alipay.mobile.antui.dialog.AUNoticeDialog.OnClickPositiveListener;
import com.alipay.mobile.antui.dialog.AUProgressDialog;
import com.alipay.mobile.antui.tablelist.AUSingleTitleListItem;
import com.alipay.mobile.antui.tablelist.AUSwitchListItem;
import com.alipay.mobile.framework.app.ui.BaseActivity;
import com.alipay.mobile.framework.service.common.TaskScheduleService;
import com.alipay.mobile.framework.service.common.TaskScheduleService.ScheduleType;
import com.alipay.mobile.nebula.appcenter.model.AppInfo;
import com.alipay.mobile.nebula.provider.H5AppProvider;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.tinyappcommon.utils.WalletTinyappUtils;
import com.alipay.mobile.tinyappcustom.h5plugin.H5MiniProgramOpenSettingPlugin;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class MiniProgramOpenSettingActivity extends BaseActivity {
    private AUTitleBar a;
    private AUProgressDialog b;
    /* access modifiers changed from: private */
    public ScrollView c;
    /* access modifiers changed from: private */
    public LinearLayout d;
    /* access modifiers changed from: private */
    public LinearLayout e;
    /* access modifiers changed from: private */
    public AUTextView f;
    /* access modifiers changed from: private */
    public AUTextView g;
    /* access modifiers changed from: private */
    public AUSingleTitleListItem h;
    private AUSwitchListItem i;
    private AUSwitchListItem j;
    private AUSwitchListItem k;
    private AUSwitchListItem l;
    private AUSwitchListItem m;
    private AUSwitchListItem n;
    private AUSwitchListItem o;
    /* access modifiers changed from: private */
    public Map<String, AUSwitchListItem> p = new HashMap(8);
    private String q = "";
    private String r = "";
    private byte s = 0;
    private String t;
    /* access modifiers changed from: private */
    public Map<String, Boolean> u;
    /* access modifiers changed from: private */
    public volatile boolean v = false;
    private String w;
    private boolean x = false;
    /* access modifiers changed from: private */
    public AUNoticeDialog y;

    private class a implements Runnable {
        private a() {
        }

        /* synthetic */ a(MiniProgramOpenSettingActivity x0, byte b) {
            this();
        }

        public final void run() {
            MiniProgramOpenSettingActivity.this.f();
        }
    }

    private class b implements Runnable {
        private b() {
        }

        /* synthetic */ b(MiniProgramOpenSettingActivity x0, byte b) {
            this();
        }

        public final void run() {
            try {
                MiniProgramOpenSettingActivity.this.b();
            } catch (Throwable e) {
                H5Log.e("MiniProgramOpenSettingActivity", "[LoadDataRunnable#run] loadDataAndViewInSubThread exception: " + e.toString(), e);
            }
        }
    }

    class c implements OnCancelListener {
        c() {
        }

        public final void onCancel(DialogInterface dialog) {
            H5Log.d("MiniProgramOpenSettingActivity", "[ProgressOnCancelListener#onCancel] Activity will finish.");
            MiniProgramOpenSettingActivity.this.finish();
        }
    }

    private class d implements OnCheckedChangeListener {
        public AUSwitchListItem a;
        private String c = "";

        public d(String settingKey, AUSwitchListItem switchListItem) {
            this.a = switchListItem;
            this.c = settingKey;
        }

        public final void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            H5Log.d("MiniProgramOpenSettingActivity", "[onCheckedChanged] enter. mSettingKey = " + this.c + ", buttonView = " + buttonView.getClass().getName() + ", isChecked = " + isChecked);
            if (MiniProgramOpenSettingActivity.this.u == null || MiniProgramOpenSettingActivity.this.u.isEmpty()) {
                H5Log.w("MiniProgramOpenSettingActivity", "[SettingSwitchChangedListener#onCheckedChanged] mUserAllSettings is null, the program produces dirty data. mSettingKey = " + this.c);
            } else if (((Boolean) MiniProgramOpenSettingActivity.this.u.get(this.c)) == null) {
                H5Log.w("MiniProgramOpenSettingActivity", "[SettingSwitchChangedListener#onCheckedChanged] oldState is null, the program produces dirty data.  mSettingKey = " + this.c);
            } else {
                MiniProgramOpenSettingActivity.this.u.put(this.c, Boolean.valueOf(isChecked));
                MiniProgramOpenSettingActivity.this.b(this.c, isChecked);
                H5Log.d("MiniProgramOpenSettingActivity", "[onCheckedChanged] Finished. ");
            }
        }
    }

    private class e implements Runnable {
        private final Map<String, Boolean> b;

        e(Map<String, Boolean> userAllSettings) {
            this.b = userAllSettings;
        }

        public final void run() {
            try {
                for (Entry userSettingEntry : this.b.entrySet()) {
                    AUSwitchListItem auSwitchListItemView = (AUSwitchListItem) MiniProgramOpenSettingActivity.this.p.get(userSettingEntry.getKey());
                    if (auSwitchListItemView == null) {
                        H5Log.w("MiniProgramOpenSettingActivity", "[ShowSettingsViewRunnableOnUi#run] Unknown setting key = " + ((String) userSettingEntry.getKey()));
                    } else {
                        Boolean state = (Boolean) userSettingEntry.getValue();
                        if (state == null) {
                            H5Log.w("MiniProgramOpenSettingActivity", "[ShowSettingsViewRunnableOnUi#run] Setting value is null, default it's false.  setting key = " + ((String) userSettingEntry.getKey()));
                            state = Boolean.FALSE;
                        }
                        auSwitchListItemView.setSwitchStatus(state.booleanValue());
                        auSwitchListItemView.setVisibility(0);
                    }
                }
            } catch (Throwable e) {
                H5Log.e("MiniProgramOpenSettingActivity", "[ShowSettingsViewRunnableOnUi#run] Exception: " + e.toString(), e);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(View.inflate(this, com.alipay.mobile.a.a.a.b.layout_mini_program_open_setting_activity, null));
        d();
        c();
        e();
    }

    private void c() {
        this.a = (AUTitleBar) findViewById(C0098a.title_bar);
        this.a.setTitleText(getResources().getString(com.alipay.mobile.a.a.a.c.setting_title));
        this.c = (ScrollView) findViewById(C0098a.scrview_content_panel);
        this.e = (LinearLayout) findViewById(C0098a.layout_main_view_panel);
        this.d = (LinearLayout) findViewById(C0098a.layout_error_view_panel);
        this.g = (AUTextView) findViewById(C0098a.text_error);
        this.f = (AUTextView) findViewById(C0098a.text_allow_use_my);
        this.h = (AUSingleTitleListItem) findViewById(C0098a.user_info_auth);
        this.i = (AUSwitchListItem) findViewById(C0098a.setting_user_location);
        this.j = (AUSwitchListItem) findViewById(C0098a.setting_address);
        this.k = (AUSwitchListItem) findViewById(C0098a.setting_invoicetitle);
        this.l = (AUSwitchListItem) findViewById(C0098a.setting_ali_run);
        this.m = (AUSwitchListItem) findViewById(C0098a.setting_record);
        this.n = (AUSwitchListItem) findViewById(C0098a.setting_write_photos_album);
        this.o = (AUSwitchListItem) findViewById(C0098a.setting_camera);
        this.p.put("location", this.i);
        this.p.put("address", this.j);
        this.p.put(WalletTinyappUtils.CONST_SCOPE_INVOICE_TITLE, this.k);
        this.p.put(WalletTinyappUtils.CONST_SCOPE_ALI_RUN, this.l);
        this.p.put(WalletTinyappUtils.CONST_SCOPE_RECORD, this.m);
        this.p.put("album", this.n);
        this.p.put(WalletTinyappUtils.CONST_SCOPE_CAMERA, this.o);
        this.h.setLeftText(getResources().getText(com.alipay.mobile.a.a.a.c.setting_user_info));
        this.h.setOnClickListener(new OnClickListener() {
            public final void onClick(View v) {
                MiniProgramOpenSettingActivity.this.l();
            }
        });
        for (Entry switchListItemEntry : this.p.entrySet()) {
            ((AUSwitchListItem) switchListItemEntry.getValue()).setOnSwitchListener((OnCheckedChangeListener) new d((String) switchListItemEntry.getKey(), (AUSwitchListItem) switchListItemEntry.getValue()));
        }
        H5Log.d("MiniProgramOpenSettingActivity", "[initViews] Execution finished.");
    }

    public final void a(String msg) {
        this.b = new AUProgressDialog(this);
        this.b.setMessage(msg);
        this.b.setProgressVisiable(true);
        this.b.setCanceledOnTouchOutside(false);
        this.b.setCancelable(true);
        this.b.setOnCancelListener(new c());
        try {
            this.b.show();
        } catch (Throwable e2) {
            H5Log.e("MiniProgramOpenSettingActivity", "[showProgress] Exception: " + e2.toString(), e2);
        }
    }

    public final void a() {
        if (this.b != null && this.b.isShowing() && !isFinishing()) {
            try {
                this.b.dismiss();
            } catch (Throwable e2) {
                H5Log.e("MiniProgramOpenSettingActivity", "[dismissProgress] Exception: " + e2.toString(), e2);
            } finally {
                this.b = null;
            }
        }
    }

    private void d() {
        try {
            Intent intent = getIntent();
            this.q = intent.getStringExtra("user_id");
            this.r = intent.getStringExtra("app_id");
        } catch (Throwable e2) {
            H5Log.e("MiniProgramOpenSettingActivity", "[fieldsInit] Exception: " + e2.toString(), e2);
        }
        H5Log.d("MiniProgramOpenSettingActivity", "[fieldsInit] user_id = " + this.q + ", mAppId = " + this.r);
    }

    private void e() {
        try {
            ((TaskScheduleService) H5Utils.findServiceByInterface(TaskScheduleService.class.getName())).acquireExecutor(ScheduleType.NORMAL).submit(new b(this, 0));
            startLoading();
            H5Log.d("MiniProgramOpenSettingActivity", "[asyncLoadDataAndView] startLoading");
        } catch (Throwable e2) {
            H5Log.e("MiniProgramOpenSettingActivity", "[asyncLoadDataAndView] Exception: " + e2.toString(), e2);
        }
    }

    /* access modifiers changed from: private */
    public void f() {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put((String) "appId", (Object) this.r);
            jsonObject.put((String) "userId", (Object) this.q);
            JSONArray jsonArray = new JSONArray();
            jsonArray.add(jsonObject);
            String rpcResult = com.alipay.mobile.tinyappcustom.b.c.a("alipay.openservice.mini.authinfo.query", jsonArray.toString(), null, false, new JSONObject(), null, null, null).b();
            JSONObject resultObject = H5Utils.parseObject(rpcResult);
            if (resultObject == null || !resultObject.containsKey("authorized") || !resultObject.getBoolean("authorized").booleanValue()) {
                a(false, (String) null);
            } else {
                a(true, resultObject.getString("accessToken"));
            }
            H5Log.d("MiniProgramOpenSettingActivity", "rpcresult:" + rpcResult);
        } catch (Throwable e2) {
            H5Log.e((String) "MiniProgramOpenSettingActivity", "loadDataAndViewInSubThread...e=" + e2);
            a(false, (String) null);
        }
        this.x = false;
    }

    private void a(boolean visible, String authToken) {
        if (visible) {
            runOnUiThread(new Runnable() {
                public final void run() {
                    MiniProgramOpenSettingActivity.this.c.setVisibility(0);
                    MiniProgramOpenSettingActivity.this.e.setVisibility(0);
                    MiniProgramOpenSettingActivity.this.f.setText(MiniProgramOpenSettingActivity.this.g());
                    MiniProgramOpenSettingActivity.this.f.setVisibility(0);
                    MiniProgramOpenSettingActivity.this.h.setVisibility(0);
                }
            });
        } else {
            runOnUiThread(new Runnable() {
                public final void run() {
                    MiniProgramOpenSettingActivity.this.h.setVisibility(8);
                    if (!MiniProgramOpenSettingActivity.this.v) {
                        return;
                    }
                    if (MiniProgramOpenSettingActivity.this.u == null || MiniProgramOpenSettingActivity.this.u.isEmpty()) {
                        MiniProgramOpenSettingActivity.this.i();
                    }
                }
            });
        }
        this.w = authToken;
    }

    /* access modifiers changed from: protected */
    public final void b() {
        H5Log.d("MiniProgramOpenSettingActivity", "[loadDataAndViewInSubThread] enter");
        f();
        try {
            this.u = c.a().a(this.r, this.q);
            this.v = true;
            if (this.u == null || this.u.isEmpty()) {
                H5Log.w("MiniProgramOpenSettingActivity", "[loadDataAndViewInSubThread] allSettings maybe empty.");
                if (TextUtils.isEmpty(this.w)) {
                    i();
                }
                return;
            }
            h();
            a(this.u);
            H5Log.w("MiniProgramOpenSettingActivity", "[loadDataAndViewInSubThread] showSettingsView.");
            stopLoading();
            H5Log.d("MiniProgramOpenSettingActivity", "[loadDataAndViewInSubThread] stopLoading");
        } catch (Throwable e2) {
            j();
            H5Log.e("MiniProgramOpenSettingActivity", "[loadDataAndViewInSubThread#run] showErrorView. exception: " + e2.toString(), e2);
        } finally {
            stopLoading();
            r3 = "MiniProgramOpenSettingActivity";
            r4 = "[loadDataAndViewInSubThread] stopLoading";
            H5Log.d(r3, r4);
        }
    }

    /* access modifiers changed from: private */
    public String g() {
        String appName = k();
        if (TextUtils.isEmpty(appName)) {
            appName = getString(com.alipay.mobile.a.a.a.c.unknown_app_name);
        }
        return String.format(getString(com.alipay.mobile.a.a.a.c.allow_use_my_info), new Object[]{appName});
    }

    private void h() {
        final String subtitle = g();
        runOnUiThread(new Runnable() {
            public final void run() {
                MiniProgramOpenSettingActivity.this.c.setVisibility(0);
                MiniProgramOpenSettingActivity.this.e.setVisibility(0);
                MiniProgramOpenSettingActivity.this.f.setText(subtitle);
                MiniProgramOpenSettingActivity.this.f.setVisibility(0);
                MiniProgramOpenSettingActivity.this.g.setVisibility(8);
            }
        });
    }

    private void a(Map<String, Boolean> userAllSettings) {
        if (userAllSettings == null || userAllSettings.isEmpty()) {
            i();
            H5Log.w("MiniProgramOpenSettingActivity", "[showSettingsView] userAllSettings maybe empty.");
            return;
        }
        runOnUiThread(new e(userAllSettings));
    }

    /* access modifiers changed from: private */
    public void i() {
        try {
            final String appName = k();
            runOnUiThread(new Runnable() {
                public final void run() {
                    MiniProgramOpenSettingActivity.this.c.setVisibility(8);
                    MiniProgramOpenSettingActivity.this.e.setVisibility(8);
                    MiniProgramOpenSettingActivity.this.g.setText(String.format(MiniProgramOpenSettingActivity.this.getString(com.alipay.mobile.a.a.a.c.no_use_any_your_info), new Object[]{appName}));
                    MiniProgramOpenSettingActivity.this.d.setVisibility(0);
                }
            });
        } catch (Throwable e2) {
            H5Log.e("MiniProgramOpenSettingActivity", "[showNoUseAnyAuthInfo] Exception: " + e2.toString(), e2);
        }
    }

    private void j() {
        try {
            final String appName = k();
            runOnUiThread(new Runnable() {
                public final void run() {
                    MiniProgramOpenSettingActivity.this.c.setVisibility(8);
                    MiniProgramOpenSettingActivity.this.e.setVisibility(8);
                    MiniProgramOpenSettingActivity.this.g.setText(String.format(MiniProgramOpenSettingActivity.this.getString(com.alipay.mobile.a.a.a.c.cannot_use_any_your_info), new Object[]{appName}));
                    MiniProgramOpenSettingActivity.this.d.setVisibility(0);
                }
            });
        } catch (Throwable e2) {
            H5Log.e("MiniProgramOpenSettingActivity", "[showErrorView] Exception: " + e2.toString(), e2);
        }
    }

    /* access modifiers changed from: protected */
    public final void a(String settingKey, boolean isChecked) {
        try {
            H5Log.d("MiniProgramOpenSettingActivity", "[doAsyncUpdateSettingInSubThread] Start update setting. settingKey = " + settingKey + ", value = " + isChecked);
            c.a().a(this.r, this.q, settingKey, isChecked);
            H5Log.d("MiniProgramOpenSettingActivity", "[doAsyncUpdateSettingInSubThread] Update setting finished. settingKey = " + settingKey + ", value = " + isChecked);
        } catch (Throwable e2) {
            H5Log.e("MiniProgramOpenSettingActivity", "[doAsyncUpdateSettingInSubThread] Exception: " + e2.toString(), e2);
        }
    }

    /* access modifiers changed from: protected */
    public final void b(final String settingKey, final boolean isChecked) {
        try {
            ((TaskScheduleService) H5Utils.findServiceByInterface(TaskScheduleService.class.getName())).acquireExecutor(ScheduleType.NORMAL).submit(new Runnable() {
                public final void run() {
                    try {
                        MiniProgramOpenSettingActivity.this.a(settingKey, isChecked);
                    } catch (Throwable e) {
                        H5Log.e("MiniProgramOpenSettingActivity", "[asyncUpdateSetting#run] Exception: " + e.toString(), e);
                    }
                }
            });
        } catch (Throwable e2) {
            H5Log.e("MiniProgramOpenSettingActivity", "[asyncUpdateSetting] settingKey = " + settingKey + ", isChecked = " + isChecked + ". Exception: " + e2.toString(), e2);
        }
    }

    public boolean startLoading() {
        this.s = 1;
        try {
            runOnUiThread(new Runnable() {
                public final void run() {
                    MiniProgramOpenSettingActivity.this.a(MiniProgramOpenSettingActivity.this.getString(com.alipay.mobile.a.a.a.c.loading_text));
                }
            });
        } catch (Throwable e2) {
            H5Log.e("MiniProgramOpenSettingActivity", "[startLoading] Exception: " + e2.toString(), e2);
        }
        return true;
    }

    public boolean stopLoading() {
        try {
            if (this.s != 0) {
                runOnUiThread(new Runnable() {
                    public final void run() {
                        MiniProgramOpenSettingActivity.this.a();
                    }
                });
                this.s = 0;
            }
        } catch (Throwable e2) {
            H5Log.e("MiniProgramOpenSettingActivity", "[stopLoading] Exception: " + e2.toString(), e2);
        } finally {
            this.s = 0;
        }
        return true;
    }

    public void onBackPressed() {
        super.onBackPressed();
        try {
            Map allSetting = new HashMap();
            if (this.u != null) {
                allSetting.putAll(this.u);
            }
            allSetting.put("userInfo", Boolean.valueOf(!TextUtils.isEmpty(this.w)));
            H5MiniProgramOpenSettingPlugin.sendOpenSettingResult(this.q, this.r, allSetting);
            H5Log.d("MiniProgramOpenSettingActivity", "allSetting： " + this.u.toString());
        } catch (Throwable e2) {
            H5Log.e("MiniProgramOpenSettingActivity", "[onBackPressed] Exception: " + e2.toString(), e2);
        }
        H5Log.d("MiniProgramOpenSettingActivity", "[onBackPressed] Execution finished.");
    }

    public void finish() {
        stopLoading();
        super.finish();
    }

    private String k() {
        if (!TextUtils.isEmpty(this.t)) {
            return this.t;
        }
        this.t = b(this.r);
        H5Log.d("MiniProgramOpenSettingActivity", "[loadDataAndViewInSubThread] appName = " + this.t);
        return this.t;
    }

    private static String b(String appId) {
        H5AppProvider h5AppProvider = (H5AppProvider) H5Utils.getProvider(H5AppProvider.class.getName());
        if (h5AppProvider != null) {
            AppInfo appInfo = h5AppProvider.getAppInfo(appId);
            if (appInfo != null) {
                return appInfo.name;
            }
        }
        return null;
    }

    /* access modifiers changed from: private */
    public void l() {
        String dialogContext = getString(com.alipay.mobile.a.a.a.c.setting_how_to_cancel_auth_msg);
        try {
            if (this.y != null && this.y.isShowing()) {
                this.y.dismiss();
                this.y = null;
            }
            this.y = new AUNoticeDialog(this, null, dialogContext, "我知道了", null);
            this.y.setPositiveListener(new OnClickPositiveListener() {
                public final void onClick() {
                    MiniProgramOpenSettingActivity.this.y.dismiss();
                    MiniProgramOpenSettingActivity.this.y = null;
                }
            });
            this.y.setCanceledOnTouchOutside(false);
            this.y.setCancelable(true);
            this.y.show();
        } catch (Throwable e2) {
            H5Log.e((String) "MiniProgramOpenSettingActivity", "[showAuthNoticeDialog] Exception: " + e2.toString());
        }
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        if (this.x) {
            WalletTinyappUtils.submitTask(new a(this, 0));
        }
    }
}
