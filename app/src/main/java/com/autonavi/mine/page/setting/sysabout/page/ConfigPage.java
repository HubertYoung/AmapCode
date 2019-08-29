package com.autonavi.mine.page.setting.sysabout.page;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.Looper;
import android.text.ClipboardManager;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import com.alipay.mobile.beehive.rpc.action.ActionConstant;
import com.alipay.mobile.mrtc.api.wwj.StreamerConstants;
import com.alipay.mobile.nebula.search.H5SearchType;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.alipay.sdk.app.statistic.c;
import com.amap.bundle.blutils.FileUtil;
import com.amap.bundle.blutils.app.ConfigerHelper;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName;
import com.amap.bundle.network.request.param.NetworkParam;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.ae.AEUtil;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.annotation.PageAction;
import com.autonavi.bundle.account.api.IAccountService;
import com.autonavi.jni.alc.ALCManager;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.mine.page.setting.sysabout.presenter.ConfigPresenter$1;
import com.autonavi.mine.page.setting.sysabout.presenter.ConfigPresenter$2;
import com.autonavi.minimap.LeakCanaryUtil;
import com.autonavi.minimap.R;
import com.autonavi.minimap.ajx3.util.AjxDebugConfig;
import com.autonavi.minimap.ajx3.util.AjxDebugUtils;
import com.autonavi.minimap.alc.model.ALCLogLevel;
import com.autonavi.minimap.basemap.view.ToggleButtonList;
import com.autonavi.minimap.basemap.view.ToggleButtonList.a;
import com.autonavi.minimap.bundle.msgbox.entity.AmapMessage;
import com.autonavi.minimap.offline.auto.protocol.utils.AutoConstants;
import com.ut.device.UTDevice;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

@PageAction("amap.basemap.action.config_page")
public class ConfigPage extends AbstractBasePage<cgr> implements OnClickListener, OnCheckedChangeListener {
    public static final String SP_KEY_DRIVE_TOOLS = "drive_tools";
    public static final String SP_KEY_RDMonkey_KEY = "useRDMonkeyTestJobName";
    public static final String SP_KEY_RDMonkey_PROPERTIES = "rd_monkey_jobname";
    public static final String ui_performance_view = "ui_performance_view";
    private CheckBox mAccsSwitch;
    private CheckBox mAjx3DebuggerSwitch;
    /* access modifiers changed from: private */
    public long mAlcFlagGroup = 524222;
    /* access modifiers changed from: private */
    public int mAlcFlagLevel = ALCLogLevel.DEFAULT_LOG_LEVEL;
    private Map<Long, String> mAlcGroupListData = new LinkedHashMap();
    private Map<Integer, String> mAlcLevelListData = new LinkedHashMap();
    private RadioButton mAlipayEnvDev;
    private RadioButton mAlipayEnvOnline;
    private RadioButton mAlipayEnvTest;
    /* access modifiers changed from: private */
    public CheckBox mBatSwitch;
    /* access modifiers changed from: private */
    public CheckBox mBatSwitchClose;
    ToggleButtonList mBatSwitchToggleList;
    private RadioButton mButtonFinal;
    private RadioButton mButtonTest;
    private boolean mCdnFreeLogEnable = false;
    private TextView mCloseTextView;
    private LinearLayout mConfigLayout;
    private TextView mCopyTextView;
    private CheckBox mDriveJointDebuggingToolsSwitch;
    private CheckBox mDriveToolsSwitch;
    private View mDumpCrashSettingText;
    private View mExportCloudSyncFile;
    private RadioButton mInteralRadioBtn;
    private boolean mLeakCanaryEnable = true;
    /* access modifiers changed from: private */
    public List<String> mListData;
    private ListView mListView;
    private CheckBox mLogTestModeSwitch;
    private TextView mMiniappEntranceTextView;
    private RadioButton mPreRadioBtn;
    private RadioButton mPublishRadioBtn;
    private View mQABugScreen;
    private CheckBox mQABugSwitch;
    private CheckBox mQAScreenshotSwitch;
    private TextView mRDMonekyTestTextView;
    private CheckBox mShowGpsOnNavigation;
    private CheckBox mShowMapDrawSwitch;
    private boolean mStrictModeEnable = false;
    private CheckBox mUTChannelSwitch;
    private CheckBox mUiPerformanceSwitch;
    private TextView mUploadLogTextView;
    private CheckBox mWeatherEffectSwitch;

    /* access modifiers changed from: protected */
    public cgr createPresenter() {
        return new cgr(this);
    }

    public void onCreate(Context context) {
        super.onCreate(context);
        setContentView(R.layout.config_layout);
        initAlcListData();
        initView();
    }

    private void initAlcListData() {
        this.mAlcGroupListData.put(Long.valueOf(2), "map");
        this.mAlcGroupListData.put(Long.valueOf(4), "db");
        this.mAlcGroupListData.put(Long.valueOf(8), "guide");
        this.mAlcGroupListData.put(Long.valueOf(16), AutoConstants.AUTO_FILE_ROUTE);
        this.mAlcGroupListData.put(Long.valueOf(32), H5SearchType.SEARCH);
        this.mAlcGroupListData.put(Long.valueOf(64), "pos");
        this.mAlcGroupListData.put(Long.valueOf(128), "bl");
        this.mAlcGroupListData.put(Long.valueOf(256), "ggi");
        this.mAlcGroupListData.put(Long.valueOf(512), "ggl");
        this.mAlcGroupListData.put(Long.valueOf(1024), c.a);
        this.mAlcGroupListData.put(Long.valueOf(2048), "ane");
        this.mAlcGroupListData.put(Long.valueOf(4096), "gpi");
        this.mAlcGroupListData.put(Long.valueOf(8192), "ajx");
        this.mAlcGroupListData.put(Long.valueOf(16384), "offline");
        this.mAlcGroupListData.put(Long.valueOf(32768), "amap");
        this.mAlcGroupListData.put(Long.valueOf(IjkMediaMeta.AV_CH_TOP_BACK_CENTER), AmapMessage.TOKEN_TRAVEL);
        this.mAlcGroupListData.put(Long.valueOf(IjkMediaMeta.AV_CH_TOP_BACK_RIGHT), "wormhole");
        this.mAlcLevelListData.put(Integer.valueOf(ALCLogLevel.LOG_DEBUG.getNum()), "debug");
        this.mAlcLevelListData.put(Integer.valueOf(ALCLogLevel.LOG_INFO.getNum()), "info");
        this.mAlcLevelListData.put(Integer.valueOf(ALCLogLevel.LOG_WARN.getNum()), ActionConstant.EXCEPTION_VIEW_TYPE_WARN);
        this.mAlcLevelListData.put(Integer.valueOf(ALCLogLevel.LOG_ERROR.getNum()), "error");
        this.mAlcLevelListData.put(Integer.valueOf(ALCLogLevel.LOG_FATAL.getNum()), "fatal");
        this.mAlcLevelListData.put(Integer.valueOf(ALCLogLevel.LOG_PERFORMANCE.getNum()), AjxDebugConfig.PERFORMANCE);
        this.mAlcLevelListData.put(Integer.valueOf(ALCLogLevel.LOG_TRACING.getNum()), StreamerConstants.OPTION_KEY_DO_TRACING);
    }

    private void initView() {
        if (euk.a()) {
            int a = euk.a(getContext());
            this.mConfigLayout = (LinearLayout) findViewById(R.id.config_linearlayout);
            this.mConfigLayout.setPadding(this.mConfigLayout.getPaddingLeft(), this.mConfigLayout.getPaddingTop() + a, this.mConfigLayout.getPaddingRight(), this.mConfigLayout.getPaddingBottom());
        }
        intiListData();
        final MapSharePreference mapSharePreference = new MapSharePreference(SharePreferenceName.SharedPreferences);
        this.mShowGpsOnNavigation = (CheckBox) findViewById(R.id.show_gps_location);
        this.mListView = (ListView) findViewById(R.id.info_listView);
        this.mListView.setAdapter(new ArrayAdapter(getContext(), R.layout.config_adapter_layout, this.mListData));
        this.mCloseTextView = (TextView) findViewById(R.id.close_tv);
        this.mCloseTextView.setOnClickListener(this);
        this.mCopyTextView = (TextView) findViewById(R.id.copy_tv);
        this.mCopyTextView.setOnClickListener(this);
        this.mUploadLogTextView = (TextView) findViewById(R.id.upload_log_tv);
        this.mUploadLogTextView.setOnClickListener(this);
        this.mMiniappEntranceTextView = (TextView) findViewById(R.id.miniappEntrance_tv);
        this.mRDMonekyTestTextView = (TextView) findViewById(R.id.mokey_test_jobname_tv);
        this.mRDMonekyTestTextView.setOnClickListener(this);
        boolean z = AMapAppGlobal.getApplication().getSharedPreferences(SP_KEY_RDMonkey_PROPERTIES, 0).getBoolean(SP_KEY_RDMonkey_KEY, false);
        TextView textView = this.mRDMonekyTestTextView;
        StringBuilder sb = new StringBuilder();
        sb.append(AMapAppGlobal.getApplication().getString(R.string.monkey_test_job_name));
        sb.append(String.valueOf(z));
        textView.setText(sb.toString());
        if (bno.a) {
            this.mRDMonekyTestTextView.setVisibility(0);
        }
        CheckBox checkBox = (CheckBox) findViewById(R.id.leak_canary_switch);
        boolean z2 = true;
        this.mLeakCanaryEnable = mapSharePreference.getBooleanValue(LeakCanaryUtil.SP_KEY_leakcanary_switch, true);
        if (bno.a) {
            checkBox.setChecked(this.mLeakCanaryEnable);
            checkBox.setOnClickListener(this);
        } else {
            findViewById(R.id.divider_leak_canary).setVisibility(8);
            checkBox.setVisibility(8);
        }
        CheckBox checkBox2 = (CheckBox) findViewById(R.id.cdn_log_switch);
        this.mCdnFreeLogEnable = mapSharePreference.getBooleanValue("cdn_log_switch", false);
        if (bno.a) {
            checkBox2.setChecked(this.mCdnFreeLogEnable);
            checkBox2.setOnClickListener(this);
        } else {
            findViewById(R.id.divider_cdn_log).setVisibility(8);
            checkBox2.setVisibility(8);
        }
        TextView textView2 = (TextView) findViewById(R.id.strict_mode_switch);
        if (bno.a) {
            textView2.setOnClickListener(this);
        } else {
            findViewById(R.id.divider_strict_mode).setVisibility(8);
            textView2.setVisibility(8);
        }
        this.mLogTestModeSwitch = (CheckBox) findViewById(R.id.log_test_mode_switch);
        this.mAccsSwitch = (CheckBox) findViewById(R.id.accs_checkbox);
        this.mBatSwitch = (CheckBox) findViewById(R.id.bat_switch);
        this.mBatSwitchClose = (CheckBox) findViewById(R.id.bat_switch_close);
        this.mAjx3DebuggerSwitch = (CheckBox) findViewById(R.id.ajx3_debugger_switch);
        this.mUiPerformanceSwitch = (CheckBox) findViewById(R.id.performance_view_switch);
        this.mDriveToolsSwitch = (CheckBox) findViewById(R.id.drive_tools_switch);
        this.mDriveJointDebuggingToolsSwitch = (CheckBox) findViewById(R.id.drive_joint_debugging_tools);
        this.mBatSwitchToggleList = new ToggleButtonList(new CompoundButton[]{this.mBatSwitch, this.mBatSwitchClose});
        this.mShowMapDrawSwitch = (CheckBox) findViewById(R.id.show_map_draw_switch);
        this.mWeatherEffectSwitch = (CheckBox) findViewById(R.id.weather_effect_open);
        this.mUTChannelSwitch = (CheckBox) findViewById(R.id.ut_channel);
        this.mQAScreenshotSwitch = (CheckBox) findViewById(R.id.qa_screenshot);
        this.mDumpCrashSettingText = findViewById(R.id.dumpcrash_settings);
        this.mExportCloudSyncFile = findViewById(R.id.exprot_cloud_sync_file);
        this.mQABugSwitch = (CheckBox) findViewById(R.id.qa_bug_switch);
        this.mQABugScreen = findViewById(R.id.qa_bug_screen);
        this.mDumpCrashSettingText = findViewById(R.id.dumpcrash_settings);
        if (bno.a) {
            this.mLogTestModeSwitch.setChecked(mapSharePreference.getBooleanValue("log_test_mode", false));
            this.mLogTestModeSwitch.setOnCheckedChangeListener(this);
            this.mAjx3DebuggerSwitch.setChecked(mapSharePreference.getBooleanValue(AjxDebugUtils.SP_KEY_ajx3_debugger, false));
            this.mAjx3DebuggerSwitch.setOnCheckedChangeListener(this);
            this.mDriveToolsSwitch.setChecked(mapSharePreference.getBooleanValue(SP_KEY_DRIVE_TOOLS, false));
            this.mDriveToolsSwitch.setOnCheckedChangeListener(this);
            this.mDriveJointDebuggingToolsSwitch.setChecked(mapSharePreference.getBooleanValue("drive_joint_debugging_tools", false));
            this.mDriveJointDebuggingToolsSwitch.setOnCheckedChangeListener(this);
            findViewById(R.id.imdebug).setVisibility(0);
            CheckBox checkBox3 = (CheckBox) findViewById(R.id.imdebug_switch);
            checkBox3.setChecked(ye.a());
            checkBox3.setOnClickListener(this);
            findViewById(R.id.imdebug_clear).setOnClickListener(this);
            CheckBox checkBox4 = (CheckBox) findViewById(R.id.mqtt_log_switch);
            checkBox4.setChecked(mapSharePreference.getBooleanValue("mqtt_log_switch", false));
            checkBox4.setOnClickListener(this);
            this.mShowMapDrawSwitch.setChecked(mapSharePreference.getBooleanValue("show_map_draw_info", false));
            this.mShowMapDrawSwitch.setOnCheckedChangeListener(this);
            this.mWeatherEffectSwitch.setChecked(mapSharePreference.getBooleanValue("weather_effect_open", false));
            this.mWeatherEffectSwitch.setOnCheckedChangeListener(this);
            this.mUTChannelSwitch.setChecked(mapSharePreference.getBooleanValue("UTChannel", false));
            this.mUTChannelSwitch.setOnCheckedChangeListener(this);
            this.mQAScreenshotSwitch.setChecked(mapSharePreference.getBooleanValue("ScreenShotEnable", false));
            this.mQAScreenshotSwitch.setOnCheckedChangeListener(this);
            this.mQABugSwitch.setChecked(mapSharePreference.getBooleanValue("BugSwitchEnable", false));
            this.mQABugSwitch.setOnCheckedChangeListener(this);
            this.mQABugScreen.setOnClickListener(this);
            this.mUiPerformanceSwitch.setChecked(mapSharePreference.getBooleanValue(ui_performance_view, false));
            this.mUiPerformanceSwitch.setOnCheckedChangeListener(this);
            this.mDumpCrashSettingText.setOnClickListener(this);
            this.mMiniappEntranceTextView.setOnClickListener(this);
            this.mExportCloudSyncFile.setOnClickListener(this);
        } else {
            this.mLogTestModeSwitch.setVisibility(8);
            this.mAjx3DebuggerSwitch.setVisibility(8);
            this.mAccsSwitch.setVisibility(8);
            this.mBatSwitch.setVisibility(8);
            this.mBatSwitchClose.setVisibility(8);
            this.mShowMapDrawSwitch.setVisibility(8);
            this.mDumpCrashSettingText.setVisibility(8);
            this.mMiniappEntranceTextView.setVisibility(8);
            this.mExportCloudSyncFile.setVisibility(8);
        }
        this.mAlipayEnvOnline = (RadioButton) findViewById(R.id.env_online);
        this.mAlipayEnvTest = (RadioButton) findViewById(R.id.env_test);
        this.mAlipayEnvDev = (RadioButton) findViewById(R.id.env_dev);
        this.mAlipayEnvDev.setOnClickListener(this);
        this.mAlipayEnvTest.setOnClickListener(this);
        this.mAlipayEnvOnline.setOnClickListener(this);
        setAlipayEnvValue();
        if (!bno.a) {
            findViewById(R.id.alipay_env).setVisibility(8);
        }
        if (bno.a) {
            this.mAccsSwitch.setVisibility(0);
            this.mAccsSwitch.setChecked(mapSharePreference.getBooleanValue("accs_switch_off", false));
            this.mAccsSwitch.setOnCheckedChangeListener(this);
        } else {
            this.mAccsSwitch.setVisibility(8);
        }
        if (bno.a) {
            this.mInteralRadioBtn = (RadioButton) findViewById(R.id.networt_internal);
            this.mInteralRadioBtn.setOnClickListener(this);
            this.mInteralRadioBtn.setChecked(a.a.a() == 1);
            this.mPreRadioBtn = (RadioButton) findViewById(R.id.networt_pre);
            this.mPreRadioBtn.setOnClickListener(this);
            this.mPreRadioBtn.setChecked(a.a.a() == 2);
            this.mPublishRadioBtn = (RadioButton) findViewById(R.id.networt_publish);
            this.mPublishRadioBtn.setOnClickListener(this);
            this.mPublishRadioBtn.setChecked(a.a.a() == 0);
        } else {
            findViewById(R.id.network_env).setVisibility(8);
        }
        if (bno.a) {
            this.mBatSwitch.setVisibility(0);
            this.mBatSwitchClose.setVisibility(0);
            switch (mapSharePreference.getIntValue("bat_switch_val", bno.a ? 1 : 0)) {
                case 1:
                    this.mBatSwitch.setChecked(true);
                    break;
                case 2:
                    this.mBatSwitchClose.setChecked(true);
                    break;
                default:
                    this.mBatSwitch.setChecked(false);
                    this.mBatSwitchClose.setChecked(false);
                    break;
            }
            ToggleButtonList toggleButtonList = this.mBatSwitchToggleList;
            AnonymousClass1 r6 = new a() {
                public final void a() {
                    CompoundButton compoundButton = ConfigPage.this.mBatSwitchToggleList.b;
                    int i = compoundButton == ConfigPage.this.mBatSwitch ? 1 : compoundButton == ConfigPage.this.mBatSwitchClose ? 2 : 0;
                    new MapSharePreference(SharePreferenceName.SharedPreferences).putIntValue("bat_switch_val", i);
                }
            };
            if (toggleButtonList.a == null) {
                toggleButtonList.a = new HashSet();
            }
            if (!toggleButtonList.a.contains(r6)) {
                toggleButtonList.a.add(r6);
            }
        } else {
            this.mBatSwitch.setVisibility(8);
            this.mBatSwitchClose.setVisibility(8);
        }
        this.mButtonTest = (RadioButton) findViewById(R.id.buttonTest);
        this.mButtonTest.setOnClickListener(this);
        this.mButtonTest.setVisibility(8);
        this.mButtonFinal = (RadioButton) findViewById(R.id.buttonFinal);
        this.mButtonFinal.setOnClickListener(this);
        this.mButtonFinal.setVisibility(8);
        if (bno.a) {
            View findViewById = findViewById(R.id.ll_alc);
            findViewById.setVisibility(0);
            View findViewById2 = findViewById(R.id.ll_logAllIn);
            findViewById2.setVisibility(0);
            cgr cgr = (cgr) this.mPresenter;
            cgr.b = (CheckBox) findViewById2.findViewById(R.id.cb_console);
            cgr.b.setText("控制台输出");
            cgr.b.setChecked((cgr.a.getIntValue("alc_console", 1) & 1) == 1);
            cgr.b.setOnCheckedChangeListener(new ConfigPresenter$1(cgr));
            cgr.c = (CheckBox) findViewById2.findViewById(R.id.cb_file);
            cgr.c.setText("是否写文件");
            CheckBox checkBox5 = cgr.c;
            if ((cgr.a.getIntValue("alc_console", 2) & 2) != 2) {
                z2 = false;
            }
            checkBox5.setChecked(z2);
            cgr.c.setOnCheckedChangeListener(new ConfigPresenter$2(cgr));
            cgr.a(findViewById2.findViewById(R.id.layout_pass), "paas");
            cgr.a(findViewById2.findViewById(R.id.layout_route), AutoConstants.AUTO_FILE_ROUTE);
            cgr.a(findViewById2.findViewById(R.id.layout_sharetrip), "sharetrip");
            cgr.a(findViewById2.findViewById(R.id.layout_basemap), "basemap");
            cgr.a(findViewById2.findViewById(R.id.layout_infoservice), "infoservice");
            cgr.a(findViewById2.findViewById(R.id.layout_ajx3), "ajx3");
            findViewById(R.id.bottom).setVisibility(0);
            ViewGroup viewGroup = (ViewGroup) findViewById.findViewById(R.id.alc_group_list);
            this.mAlcFlagGroup = mapSharePreference.getLongValue("alc_group", this.mAlcFlagGroup);
            for (Entry next : this.mAlcGroupListData.entrySet()) {
                final long longValue = ((Long) next.getKey()).longValue();
                CheckBox checkBox6 = (CheckBox) getLayoutInflater().inflate(R.layout.conf_layout_alc_item, null);
                checkBox6.setText((CharSequence) next.getValue());
                checkBox6.setChecked(agi.a(this.mAlcFlagGroup, longValue));
                final CheckBox checkBox7 = checkBox6;
                final MapSharePreference mapSharePreference2 = mapSharePreference;
                AnonymousClass2 r5 = new OnClickListener() {
                    public final void onClick(View view) {
                        checkBox7.setChecked(checkBox7.isChecked());
                        if (checkBox7.isChecked()) {
                            ConfigPage.this.mAlcFlagGroup = ConfigPage.this.mAlcFlagGroup | longValue;
                        } else {
                            ConfigPage.this.mAlcFlagGroup = ConfigPage.this.mAlcFlagGroup & (~longValue);
                        }
                        mapSharePreference2.putLongValue("alc_group", ConfigPage.this.mAlcFlagGroup);
                        ALCManager.getInstance().setRecordGroupMask(ConfigPage.this.mAlcFlagGroup);
                    }
                };
                checkBox6.setOnClickListener(r5);
                viewGroup.addView(checkBox6);
            }
            ViewGroup viewGroup2 = (ViewGroup) findViewById2.findViewById(R.id.alc_level_list);
            this.mAlcFlagLevel = mapSharePreference.getIntValue("alc_level", this.mAlcFlagLevel);
            for (Entry next2 : this.mAlcLevelListData.entrySet()) {
                final int intValue = ((Integer) next2.getKey()).intValue();
                final CheckBox checkBox8 = (CheckBox) getLayoutInflater().inflate(R.layout.conf_layout_alc_item, null);
                checkBox8.setText((CharSequence) next2.getValue());
                checkBox8.setChecked(agi.a((long) this.mAlcFlagLevel, (long) intValue));
                checkBox8.setOnClickListener(new OnClickListener() {
                    public final void onClick(View view) {
                        checkBox8.setChecked(checkBox8.isChecked());
                        if (checkBox8.isChecked()) {
                            ConfigPage.this.mAlcFlagLevel = (int) (((long) ConfigPage.this.mAlcFlagLevel) | ((long) intValue));
                        } else {
                            ConfigPage.this.mAlcFlagLevel = (int) (((long) ConfigPage.this.mAlcFlagLevel) & (~((long) intValue)));
                        }
                        mapSharePreference.putIntValue("alc_level", ConfigPage.this.mAlcFlagLevel);
                        ALCManager.getInstance().setRecordLogLevelMask(ConfigPage.this.mAlcFlagLevel);
                    }
                });
                viewGroup2.addView(checkBox8);
            }
        }
        initData();
    }

    private void setAlipayEnvValue() {
        String stringValue = new MapSharePreference(SharePreferenceName.SharedPreferences).getStringValue(ConfigerHelper.alipay_env_new, "online");
        if (TextUtils.equals(stringValue, "online")) {
            this.mAlipayEnvOnline.setChecked(true);
        } else if (TextUtils.equals(stringValue, "test")) {
            this.mAlipayEnvTest.setChecked(true);
        } else {
            if (TextUtils.equals(stringValue, "dev")) {
                this.mAlipayEnvDev.setChecked(true);
            }
        }
    }

    private void initData() {
        initShowGpsStatus();
    }

    private void initShowGpsStatus() {
        if (((dfm) ank.a(dfm.class)) != null) {
            this.mShowGpsOnNavigation.setChecked(new MapSharePreference((String) "SharedPreferences").sharedPrefs().getBoolean("show_my_gps_in_navigation", false));
            this.mShowGpsOnNavigation.setOnCheckedChangeListener(new OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                    new MapSharePreference((String) "SharedPreferences").sharedPrefs().edit().putBoolean("show_my_gps_in_navigation", z).apply();
                }
            });
        }
    }

    private void intiListData() {
        this.mListData = new ArrayList();
        this.mListData.add(String.valueOf(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())));
        String netCondition = ConfigerHelper.getInstance().getNetCondition();
        StringBuilder sb = new StringBuilder("network=");
        if (netCondition == null) {
            netCondition = "";
        }
        sb.append(netCondition);
        this.mListData.add(sb.toString());
        StringBuilder sb2 = new StringBuilder("dic=");
        sb2.append(NetworkParam.getDic());
        this.mListData.add(sb2.toString());
        StringBuilder sb3 = new StringBuilder("div=");
        sb3.append(NetworkParam.getDiv());
        this.mListData.add(sb3.toString());
        StringBuilder sb4 = new StringBuilder("dibv=");
        sb4.append(NetworkParam.getDibv());
        this.mListData.add(sb4.toString());
        StringBuilder sb5 = new StringBuilder("dip=");
        sb5.append(NetworkParam.getDip());
        this.mListData.add(sb5.toString());
        StringBuilder sb6 = new StringBuilder("diu=");
        sb6.append(NetworkParam.getDiu());
        this.mListData.add(sb6.toString());
        StringBuilder sb7 = new StringBuilder("adiu=");
        sb7.append(NetworkParam.getAdiu());
        this.mListData.add(sb7.toString());
        StringBuilder sb8 = new StringBuilder("utdid=");
        sb8.append(UTDevice.getUtdid(getContext()));
        this.mListData.add(sb8.toString());
        String adiu_extras = NetworkParam.getAdiu_extras();
        String concat = "adiu_extras=".concat(String.valueOf(adiu_extras));
        if (!TextUtils.isEmpty(adiu_extras)) {
            this.mListData.add(concat);
        }
        StringBuilder sb9 = new StringBuilder("dai=");
        sb9.append(NetworkParam.getdai());
        this.mListData.add(sb9.toString());
        StringBuilder sb10 = new StringBuilder("dsn=");
        sb10.append(NetworkParam.getdsn());
        this.mListData.add(sb10.toString());
        StringBuilder sb11 = new StringBuilder("dcs=");
        sb11.append(NetworkParam.getdcs());
        this.mListData.add(sb11.toString());
        StringBuilder sb12 = new StringBuilder("cifa=");
        sb12.append(NetworkParam.getCifa());
        this.mListData.add(sb12.toString());
        StringBuilder sb13 = new StringBuilder("diu2=");
        sb13.append(NetworkParam.getMac());
        this.mListData.add(sb13.toString());
        StringBuilder sb14 = new StringBuilder("diu3=");
        sb14.append(NetworkParam.getIsn());
        this.mListData.add(sb14.toString());
        StringBuilder sb15 = new StringBuilder("tid=");
        sb15.append(NetworkParam.getTaobaoID());
        this.mListData.add(sb15.toString());
        StringBuilder sb16 = new StringBuilder("token=");
        sb16.append(NetworkParam.getDeviceToken(getContext()));
        this.mListData.add(sb16.toString());
        StringBuilder sb17 = new StringBuilder("IMEI=");
        sb17.append(NetworkParam.getDiu());
        this.mListData.add(sb17.toString());
        StringBuilder sb18 = new StringBuilder("Mac=");
        sb18.append(NetworkParam.getMac());
        this.mListData.add(sb18.toString());
        StringBuilder sb19 = new StringBuilder("BID=");
        sb19.append(TextUtils.isEmpty(NetworkParam.getBid()) ? "nobid" : NetworkParam.getBid());
        this.mListData.add(sb19.toString());
        this.mListData.add(TextUtils.isEmpty(getUid()) ? "Login=false" : "Login=true");
        String stringValue = new MapSharePreference(SharePreferenceName.SharedPreferences).getStringValue(SysAboutPage.SP_KEY_AUI_PACKAGE_VERSION, "");
        if (!TextUtils.isEmpty(stringValue)) {
            this.mListData.add(stringValue);
        }
        this.mListData.add(AEUtil.getVersionInfo());
        if (bno.a) {
            List<String> list = this.mListData;
            StringBuilder sb20 = new StringBuilder();
            StringBuilder sb21 = new StringBuilder("是否覆盖安装:");
            sb21.append(!emu.a());
            sb21.append("\n");
            sb20.append(sb21.toString());
            sb20.append("当前版本信息:\n");
            emt a = emu.a((String) "VERSION_CURVERINFO");
            if (a == null) {
                sb20.append("获取版本信息失败");
            } else {
                StringBuilder sb22 = new StringBuilder();
                sb22.append(a.toString());
                sb22.append("\n");
                sb20.append(sb22.toString());
                emt a2 = emu.a((String) "VERSION_LASTVERINFO");
                if (a2 != null) {
                    sb20.append("被覆盖版本信息:\n");
                    sb20.append(a2.toString());
                }
            }
            list.add(sb20.toString());
        }
        initHotfixData();
        if (bno.a) {
            this.mListData.add("asan.enable=true");
        }
    }

    private void initHotfixData() {
        final String b = enh.a(AMapAppGlobal.getApplication()).b();
        if (b != null) {
            final int size = this.mListData.size();
            File file = new File(b);
            StringBuilder sb = new StringBuilder();
            sb.append(file.getParentFile().getName());
            sb.append(".");
            sb.append(file.getName());
            final String sb2 = sb.toString();
            List<String> list = this.mListData;
            StringBuilder sb3 = new StringBuilder("\n补丁: ");
            sb3.append(sb2);
            sb3.append("\n");
            list.add(sb3.toString());
            ahn.b().execute(new Runnable() {
                public final void run() {
                    StringBuilder sb = new StringBuilder();
                    sb.append("\n补丁: ");
                    sb.append(sb2);
                    File[] listFiles = new File(b).listFiles();
                    if (listFiles != null) {
                        int i = 1;
                        for (File file : listFiles) {
                            String name = file.getName();
                            if (name.endsWith(".so")) {
                                sb.append("\n  ");
                                StringBuilder sb2 = new StringBuilder();
                                sb2.append(i);
                                sb2.append(". 模块: ");
                                sb.append(sb2.toString());
                                sb.append(name);
                                sb.append("  ");
                                String a2 = agy.a(file, "", false);
                                if (a2.length() == 32) {
                                    sb.append("指纹: ");
                                    int i2 = 0;
                                    while (i2 < 32) {
                                        int i3 = i2 + 4;
                                        sb.append(a2.substring(i2, i3));
                                        sb.append(Token.SEPARATOR);
                                        i2 = i3;
                                    }
                                }
                                i++;
                            }
                        }
                    }
                    sb.append("\n");
                    final String sb3 = sb.toString();
                    aho.a(new Runnable() {
                        public final void run() {
                            ConfigPage.this.mListData.set(size, sb3);
                            ConfigPage.this.updateListView();
                        }
                    });
                }
            });
        }
    }

    /* access modifiers changed from: private */
    public void updateListView() {
        if (this.mListView != null) {
            ListAdapter adapter = this.mListView.getAdapter();
            if (adapter instanceof BaseAdapter) {
                ((BaseAdapter) adapter).notifyDataSetChanged();
            }
        }
    }

    public void onClick(View view) {
        MapSharePreference mapSharePreference = new MapSharePreference(SharePreferenceName.SharedPreferences);
        if (view.getId() == R.id.buttonTest) {
            this.mButtonTest.setChecked(true);
            this.mButtonFinal.setChecked(false);
        } else if (view.getId() == R.id.buttonFinal) {
            this.mButtonTest.setChecked(false);
            this.mButtonFinal.setChecked(true);
        } else if (view.getId() == R.id.close_tv) {
            finish();
        } else if (view.getId() == R.id.copy_tv) {
            try {
                ((ClipboardManager) getContext().getSystemService("clipboard")).setText(this.mListData.toString());
            } catch (SecurityException e) {
                e.printStackTrace();
            }
            ToastHelper.showToast(getResources().getString(R.string.caidan_copy_success));
        } else if (view.getId() == R.id.upload_log_tv) {
            awh awh = (awh) a.a.a(awh.class);
            if (awh != null) {
                awh.a();
            }
        } else if (view.getId() == R.id.miniappEntrance_tv) {
            awh awh2 = (awh) a.a.a(awh.class);
            Context context = getContext();
            if (!(awh2 == null || context == null)) {
                awh2.a(context);
            }
        } else if (view.getId() == R.id.leak_canary_switch) {
            this.mLeakCanaryEnable = ((CheckBox) findViewById(R.id.leak_canary_switch)).isChecked();
            mapSharePreference.putBooleanValue(LeakCanaryUtil.SP_KEY_leakcanary_switch, this.mLeakCanaryEnable);
            LeakCanaryUtil.setEnabled(AMapAppGlobal.getApplication(), this.mLeakCanaryEnable);
            if (this.mLeakCanaryEnable) {
                ToastHelper.showToast(getResources().getString(R.string.caidan_leakcanary_enable));
            } else {
                ToastHelper.showToast(getResources().getString(R.string.caidan_leakcanary_disable));
            }
        } else if (view.getId() == R.id.cdn_log_switch) {
            this.mCdnFreeLogEnable = ((CheckBox) findViewById(R.id.cdn_log_switch)).isChecked();
            mapSharePreference.putBooleanValue("cdn_log_switch", this.mCdnFreeLogEnable);
            ze.a().a = this.mCdnFreeLogEnable;
            if (this.mCdnFreeLogEnable) {
                ToastHelper.showToast(getResources().getString(R.string.cdn_log_enable));
            } else {
                ToastHelper.showToast(getResources().getString(R.string.cdn_log_disable));
            }
        } else if (view.getId() == R.id.strict_mode_switch) {
            this.mStrictModeEnable = !this.mStrictModeEnable;
            if (this.mStrictModeEnable) {
                getActivity();
                adw.a();
                ToastHelper.showToast(getResources().getString(R.string.caidan_strict_mode_enable));
                return;
            }
            adw.b();
            ToastHelper.showToast(getResources().getString(R.string.caidan_strict_mode_disable));
        } else if (view.getId() == R.id.env_online) {
            mapSharePreference.putBooleanValue(ConfigerHelper.alipay_env, false);
            mapSharePreference.putStringValue(ConfigerHelper.alipay_env_new, "online");
            ToastHelper.showToast("已切换到支付宝正式环境,重启后生效!");
        } else if (view.getId() == R.id.env_test) {
            mapSharePreference.putBooleanValue(ConfigerHelper.alipay_env, false);
            mapSharePreference.putStringValue(ConfigerHelper.alipay_env_new, "test");
            ToastHelper.showToast("已切换到支付宝test环境,重启后生效!");
        } else if (view.getId() == R.id.env_dev) {
            mapSharePreference.putBooleanValue(ConfigerHelper.alipay_env, true);
            mapSharePreference.putStringValue(ConfigerHelper.alipay_env_new, "dev");
            ToastHelper.showToast("已切换到支付宝dev环境,重启后生效!");
        } else if (view.getId() == R.id.imdebug_switch) {
            CheckBox checkBox = (CheckBox) view;
            ye.a(checkBox.isChecked());
            findViewById(R.id.imdebug_clear).setEnabled(checkBox.isChecked());
        } else if (view.getId() == R.id.imdebug_clear) {
            ye b = ye.b();
            if (ye.a) {
                if (Looper.myLooper() == Looper.getMainLooper() && b.b != null) {
                    b.b.setText("");
                } else if (b.c != null) {
                    b.c.post(new Runnable() {
                        public final void run() {
                            if (ye.this.b != null) {
                                ye.this.b.setText("");
                            }
                        }
                    });
                }
            }
        } else if (view.getId() == R.id.mqtt_log_switch) {
            boolean isChecked = ((CheckBox) view).isChecked();
            mapSharePreference.putBooleanValue("mqtt_log_switch", isChecked);
            yh.a(isChecked);
        } else if (view.getId() == R.id.mokey_test_jobname_tv) {
            boolean z = !AMapAppGlobal.getApplication().getSharedPreferences(SP_KEY_RDMonkey_PROPERTIES, 0).getBoolean(SP_KEY_RDMonkey_KEY, false);
            Editor edit = AMapAppGlobal.getApplication().getSharedPreferences(SP_KEY_RDMonkey_PROPERTIES, 0).edit();
            edit.putBoolean(SP_KEY_RDMonkey_KEY, z);
            edit.commit();
            TextView textView = this.mRDMonekyTestTextView;
            StringBuilder sb = new StringBuilder();
            sb.append(AMapAppGlobal.getApplication().getString(R.string.monkey_test_job_name));
            sb.append(String.valueOf(z));
            textView.setText(sb.toString());
        } else if (view.getId() == R.id.networt_internal) {
            this.mInteralRadioBtn.setChecked(true);
            this.mPreRadioBtn.setChecked(false);
            this.mPublishRadioBtn.setChecked(false);
            bdc.a();
        } else if (view.getId() == R.id.networt_pre) {
            this.mInteralRadioBtn.setChecked(false);
            this.mPreRadioBtn.setChecked(true);
            this.mPublishRadioBtn.setChecked(false);
            new a(0).execute(new Integer[]{Integer.valueOf(2)});
        } else if (view.getId() == R.id.networt_publish) {
            this.mPublishRadioBtn.setChecked(true);
            this.mPreRadioBtn.setChecked(false);
            this.mInteralRadioBtn.setChecked(false);
            bdc.b();
        } else if (view.getId() == R.id.dumpcrash_settings) {
            Intent intent = new Intent();
            intent.setClassName(getContext(), "com.autonavi.bundle.crashsettings.CrashSettingsActivity");
            getActivity().startActivity(intent);
        } else if (view.getId() == R.id.qa_bug_screen) {
            Intent intent2 = new Intent();
            intent2.setClassName(getContext(), "com.amap.bundle.qaplugin.QABug.BugActivity");
            getActivity().startActivity(intent2);
        } else {
            if (view.getId() == R.id.exprot_cloud_sync_file) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append(FileUtil.getFilesDir().getPath());
                sb2.append("/girf_sync.db");
                File file = new File(sb2.toString());
                StringBuilder sb3 = new StringBuilder();
                sb3.append(FileUtil.getAppSDCardFileDir());
                sb3.append("/girf_sync.db");
                ahd.a(file, new File(sb3.toString()));
            }
        }
    }

    public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        int id = compoundButton.getId();
        if (id == R.id.log_test_mode_switch) {
            new MapSharePreference(SharePreferenceName.SharedPreferences).putBooleanValue("log_test_mode", z);
        } else if (id == R.id.ajx3_debugger_switch) {
            AjxDebugUtils.debug(z);
        } else if (id == R.id.accs_checkbox) {
            new MapSharePreference(SharePreferenceName.SharedPreferences).putBooleanValue("accs_switch_off", z);
        } else if (id == R.id.drive_tools_switch) {
            new MapSharePreference(SharePreferenceName.SharedPreferences).putBooleanValue(SP_KEY_DRIVE_TOOLS, z);
            if (z) {
                ToastHelper.showLongToast("内存，性能测试时请关闭此开关。（驾车开发测试工具开发））");
            }
            b.a();
        } else if (id == R.id.drive_joint_debugging_tools) {
            new MapSharePreference(SharePreferenceName.SharedPreferences).putBooleanValue("drive_joint_debugging_tools", z);
            if (z) {
                ToastHelper.showLongToast("开启驾车联调测试环境");
            } else {
                ToastHelper.showLongToast("关闭驾车联调测试环境");
            }
        } else if (id == R.id.show_map_draw_switch) {
            new MapSharePreference(SharePreferenceName.SharedPreferences).putBooleanValue("show_map_draw_info", z);
            StringBuilder sb = new StringBuilder();
            sb.append(z ? "已开启" : "已关闭");
            sb.append("引擎回调日志,重启后生效!");
            ToastHelper.showToast(sb.toString());
            b.a();
        } else if (id == R.id.weather_effect_open) {
            new MapSharePreference(SharePreferenceName.SharedPreferences).putBooleanValue("weather_effect_open", z);
            StringBuilder sb2 = new StringBuilder();
            sb2.append(z ? "已开启" : "已关闭");
            sb2.append("引擎回调日志,重启后生效!");
            ToastHelper.showToast(sb2.toString());
            b.a();
        } else if (id == R.id.performance_view_switch) {
            new MapSharePreference(SharePreferenceName.SharedPreferences).putBooleanValue(ui_performance_view, z);
            if (z) {
                emo.a(getContext()).a();
            } else {
                emo.a(getContext()).a.b();
            }
        } else if (id == R.id.ut_channel) {
            getContext().startActivity(new Intent("android.intent.action.VIEW", Uri.parse(String.format("amapuri://QATest/UTChannel?state=%s", new Object[]{Boolean.valueOf(z)}))));
        } else if (id == R.id.qa_screenshot) {
            getContext().startActivity(new Intent("android.intent.action.VIEW", Uri.parse(String.format("amapuri://QATest/ScreenShotEnable?state=%s", new Object[]{Boolean.valueOf(z)}))));
        } else {
            if (id == R.id.qa_bug_switch) {
                getContext().startActivity(new Intent("android.intent.action.VIEW", Uri.parse(String.format("amapuri://QABug/BugSwitchEnable?state=%s", new Object[]{Boolean.valueOf(z)}))));
            }
        }
    }

    private String getUid() {
        IAccountService iAccountService = (IAccountService) a.a.a(IAccountService.class);
        if (iAccountService == null) {
            return "";
        }
        ant e = iAccountService.e();
        if (e == null) {
            return "";
        }
        return e.a;
    }
}
