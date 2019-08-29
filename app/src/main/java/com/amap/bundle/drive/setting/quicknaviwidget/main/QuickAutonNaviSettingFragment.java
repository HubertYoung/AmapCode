package com.amap.bundle.drive.setting.quicknaviwidget.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.amap.bundle.drive.setting.quicknaviwidget.accessibility.QuickAutoNaviAccessibilitySettings;
import com.amap.bundle.drive.setting.quicknaviwidget.broadcast.QuickAutoNaviBroadcastSettings;
import com.amap.bundle.drive.setting.quicknaviwidget.display.QuickAutoNaviDisplaySettings;
import com.amap.bundle.drive.setting.quicknaviwidget.main.dialog.QuickAutoNaviAdapter;
import com.amap.bundle.drive.setting.quicknaviwidget.main.dialog.QuickAutoNaviAdapter.Style;
import com.amap.bundle.drive.setting.quicknaviwidget.main.dialog.QuickAutoNaviSettingDialogFactory$1;
import com.amap.bundle.drive.setting.quicknaviwidget.main.view.CarPlateTextView;
import com.amap.bundle.drivecommon.mvp.view.DriveBasePage;
import com.amap.bundle.drivecommon.route.result.view.RoutingPreferenceView;
import com.amap.bundle.drivecommon.tools.DriveSpUtil;
import com.amap.bundle.drivecommon.tools.DriveUtil;
import com.amap.bundle.statistics.util.LogUtil;
import com.amap.bundle.tripgroup.api.IVoicePackageManager;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.bundle.account.api.IAccountService;
import com.autonavi.bundle.uitemplate.mapwidget.widget.diy.DIYMainMapPresenter;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.dialog.NodeAlertDialogPage;
import com.autonavi.map.fragmentcontainer.page.dialog.NodeAlertDialogPage.NodeDialogFragmentOnClickListener;
import com.autonavi.minimap.R;
import com.autonavi.minimap.drive.widget.CarPlateInputView;
import com.autonavi.minimap.offline.inter.inner.ICheckOfflineResponse;
import com.autonavi.minimap.offline.map.inter.IOfflineManager;
import com.autonavi.minimap.widget.ListDialog;
import com.autonavi.widget.ui.AlertView;
import com.autonavi.widget.ui.TitleBar;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import org.json.JSONException;
import org.json.JSONObject;

@Deprecated
public class QuickAutonNaviSettingFragment extends DriveBasePage<qu> implements OnClickListener, OnCheckedChangeListener {
    public static final String BUNDLE_KEY_FROM = "from";
    public static final String BUNDLE_KEY_HANDLER = "bundle_key_handler";
    public static final String BUNDLE_KEY_REQUEST_CODE = "bundle_key_request_code";
    public static final String CARLOGO_AMAPURI = "amapuri://carlogoservice/carlogo";
    public static final int FROM_SETTING_IN_MORE = 2;
    public static final int FROM_SETTING_IN_NAVIGATION = 1;
    public static final int NOTICE_DELETE_CAR_PLATE_FOR_SURE = 100;
    public static final int NOTICE_DELETE_TRUCK_CAR_PLATE_FOR_SURE = 101;
    public static final int REQUEST_CODE_LAUNCH_CAR_PLATE_INPUT_ADD = 1000;
    public static final int REQUEST_CODE_LAUNCH_CAR_PLATE_INPUT_EDIT = 1001;
    public static final int REQUEST_CODE_LAUNCH_RESTRICTED_CITY_LIST = 1006;
    public static final int REQUEST_CODE_LAUNCH_TRUCK_CAR_PLATE_DELETE = 1004;
    public static final int REQUEST_CODE_LAUNCH_TRUCK_CAR_PLATE_INPUT_ADD = 1002;
    public static final int REQUEST_CODE_LAUNCH_TRUCK_CAR_PLATE_INPUT_EDIT = 1003;
    private static final int REQUEST_CODE_LAUNCH_TRUCK_PLATE = 10008;
    public static final int REQ_LAUNCH_CAROWNER_SELECT_CAR = 1005;
    private qk eagleTmcSettingView;
    private boolean isGetTruckAll = false;
    /* access modifiers changed from: private */
    public boolean isMendTruckDialogShow;
    /* access modifiers changed from: private */
    public boolean isTruckRefresh = false;
    private View mAccessibility;
    private View mAvoidWayLimits;
    private CheckBox mAvoidWayLimitsCb;
    private View mAvoidWayLimitsTips;
    private View mBroadcastMode;
    /* access modifiers changed from: private */
    public TextView mBroadcastModeChoice;
    private View mCarDivideLine;
    private View mCarDivideLineMatch;
    private CarPlateTextView mCarPlate;
    private View mCarPlateLayout;
    /* access modifiers changed from: private */
    public ListDialog mDialog;
    private View mDisplayCarLogo;
    private View mDisplayEntrance;
    private int mFrom = 1;
    a mHandler = new a(this);
    private View mNaviVoice;
    private TextView mNaviVoiceChoice;
    private View mSearchRouteInNetMode;
    /* access modifiers changed from: private */
    public CheckBox mSearchRouteInNetModeChoice;
    private TitleBar mTitleBar;
    private View mTruckAvoidWayLimits;
    private CheckBox mTruckAvoidWayLimitsCb;
    private CarPlateTextView mTruckCarPlate;
    private LinearLayout mTruckDetailLayout;
    private View mTruckDivideLine;
    private View mTruckDivideLineMatch;
    private TextView mTruckPara;
    private TextView truckLenWidthHeight;
    /* access modifiers changed from: private */
    public boolean willCheck = false;

    static class a extends Handler {
        WeakReference<QuickAutonNaviSettingFragment> a;

        a(QuickAutonNaviSettingFragment quickAutonNaviSettingFragment) {
            this.a = new WeakReference<>(quickAutonNaviSettingFragment);
        }

        public final void handleMessage(Message message) {
            if (((QuickAutonNaviSettingFragment) this.a.get()) != null) {
            }
        }
    }

    public void onPageDestroyView() {
    }

    public boolean isMendTruckDialogShow() {
        return this.isMendTruckDialogShow;
    }

    /* access modifiers changed from: protected */
    public qu createPresenter() {
        return new qu(this);
    }

    public void onCreate(Context context) {
        super.onCreate(context);
        setContentView(R.layout.navi_settings);
    }

    public void init() {
        initView(getContentView());
    }

    public void onPageResume() {
        updateNaviVoiceItemState();
        if (this.mBroadcastModeChoice != null) {
            this.mBroadcastModeChoice.setText(re.a(getContext()));
        }
        if (this.willCheck && !this.mSearchRouteInNetModeChoice.isChecked()) {
            showOfflineAlertDialog();
        }
        setTruckCarAvoidWayLimits();
        setCarAvoidWayLimits();
    }

    private void setTruckCarAvoidWayLimits() {
        boolean isTruckAvoidLimitedPath = DriveUtil.isTruckAvoidLimitedPath();
        boolean isEmpty = TextUtils.isEmpty(DriveUtil.getTruckCarPlateNumber());
        if (isTruckAvoidLimitedPath) {
            setTruckCheckedData(!isEmpty);
            return;
        }
        if (!isEmpty) {
            boolean truckHasEmptyProperty = DriveUtil.truckHasEmptyProperty();
            if (!truckHasEmptyProperty && this.isTruckRefresh) {
                setTruckCheckedData(true);
            }
            if (!truckHasEmptyProperty && !DriveUtil.isCarTruckInfoEmpty() && this.isGetTruckAll) {
                setTruckCheckedData(true);
            }
        } else {
            setTruckCheckedData(false);
        }
        resetTruckRefresh();
    }

    private void setTruckCheckedData(boolean z) {
        this.mTruckAvoidWayLimitsCb.setChecked(z);
        refreshTruckCarItem(z);
    }

    private void resetTruckRefresh() {
        if (this.isTruckRefresh) {
            this.isTruckRefresh = false;
        }
        if (this.isGetTruckAll) {
            this.isGetTruckAll = false;
        }
    }

    private void setCarAvoidWayLimits() {
        boolean isAvoidLimitedPath = DriveUtil.isAvoidLimitedPath();
        String carPlateNumber = DriveUtil.getCarPlateNumber();
        if (TextUtils.isEmpty(carPlateNumber)) {
            this.mAvoidWayLimitsCb.setChecked(false);
        } else if (isAvoidLimitedPath || this.mAvoidWayLimitsCb.isChecked()) {
            this.mAvoidWayLimitsCb.setChecked(true);
            setOpenCarSetting(carPlateNumber);
        }
    }

    private void setOpenCarSetting(String str) {
        this.mCarPlateLayout.setVisibility(0);
        this.mCarPlate.setText(str);
        DriveUtil.setAvoidLimitedPath(true);
        closeOtherSwitch(this.mAvoidWayLimitsCb);
    }

    private void initView(View view) {
        PageBundle arguments = getArguments();
        if (arguments != null && arguments.containsKey("amap.extra.prefer.from")) {
            this.mFrom = arguments.getInt("amap.extra.prefer.from");
        }
        this.mTitleBar = (TitleBar) view.findViewById(R.id.title);
        if (this.mTitleBar != null) {
            this.mTitleBar.setBackImgContentDescription(getString(R.string.exit_navi_done_confirm));
            this.mTitleBar.setOnBackClickListener((OnClickListener) new OnClickListener() {
                public final void onClick(View view) {
                    QuickAutonNaviSettingFragment.this.finish();
                }
            });
        }
        this.mSearchRouteInNetMode = view.findViewById(R.id.search_route_in_net);
        this.mSearchRouteInNetModeChoice = (CheckBox) view.findViewById(R.id.search_route_in_net_choice);
        Context context = getContext();
        if (context != null) {
            this.mSearchRouteInNetModeChoice.setChecked(!DriveSpUtil.getSearchRouteInNetMode(context));
        }
        this.mSearchRouteInNetMode.setOnClickListener(this);
        this.mSearchRouteInNetModeChoice.setOnCheckedChangeListener(this);
        PageBundle pageBundle = new PageBundle();
        pageBundle.putObject(RoutingPreferenceView.BUNDLE_KEY_OBJ_ORIGIN, this);
        ((RoutingPreferenceView) view.findViewById(R.id.routing_preference_view)).setPrebuiltData(pageBundle);
        this.mAvoidWayLimitsTips = view.findViewById(R.id.avoid_way_limits_tips);
        this.mAvoidWayLimitsTips.setOnClickListener(this);
        this.mCarDivideLine = view.findViewById(R.id.divide_non_match_car);
        this.mCarDivideLineMatch = view.findViewById(R.id.divide_match_car);
        this.mTruckDivideLine = view.findViewById(R.id.divide_non_match_truck);
        this.mTruckDivideLineMatch = view.findViewById(R.id.divide_match_truck);
        this.mAvoidWayLimits = view.findViewById(R.id.avoid_way_limits);
        this.mAvoidWayLimitsCb = (CheckBox) view.findViewById(R.id.avoid_way_limits_checkbox);
        this.mAvoidWayLimits.setOnClickListener(this);
        this.mAvoidWayLimitsCb.setOnCheckedChangeListener(this);
        this.mCarPlateLayout = view.findViewById(R.id.car_plate_layout);
        this.mCarPlateLayout.setOnClickListener(new OnClickListener() {
            public final void onClick(View view) {
                try {
                    DriveUtil.startCarList(1, QuickAutonNaviSettingFragment.this);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        this.mCarPlate = (CarPlateTextView) view.findViewById(R.id.car_plate);
        ((LinearLayout) view.findViewById(R.id.truck_avoid_limit_layout)).setVisibility(0);
        this.mTruckAvoidWayLimits = view.findViewById(R.id.truck_avoid_way_limits);
        this.mTruckAvoidWayLimitsCb = (CheckBox) view.findViewById(R.id.truck_avoid_way_limits_checkbox);
        this.mTruckAvoidWayLimits.setOnClickListener(this);
        this.mTruckAvoidWayLimitsCb.setOnClickListener(this);
        this.mTruckDetailLayout = (LinearLayout) view.findViewById(R.id.truck_detail_layout);
        this.mTruckDetailLayout.setVisibility(0);
        this.mTruckDetailLayout.setOnClickListener(this);
        this.mTruckCarPlate = (CarPlateTextView) view.findViewById(R.id.truck_car_plate);
        this.mTruckPara = (TextView) view.findViewById(R.id.truck_para);
        this.truckLenWidthHeight = (TextView) view.findViewById(R.id.truck_len_width_height);
        String truckCarPlateNumber = DriveUtil.getTruckCarPlateNumber();
        if (!TextUtils.isEmpty(truckCarPlateNumber)) {
            String[] a2 = sf.a();
            if (a2 != null) {
                this.mTruckCarPlate.setCarPlate(truckCarPlateNumber);
                this.mTruckPara.setText(a2[0]);
                this.mTruckDetailLayout.setVisibility(0);
                this.truckLenWidthHeight.setText(a2[1]);
            }
        }
        boolean isAvoidLimitedPath = DriveUtil.isAvoidLimitedPath();
        String carPlateNumber = DriveUtil.getCarPlateNumber();
        if (isAvoidLimitedPath && TextUtils.isEmpty(carPlateNumber)) {
            isAvoidLimitedPath = false;
        }
        this.mAvoidWayLimitsCb.setChecked(isAvoidLimitedPath);
        boolean isTruckAvoidLimitedPath = DriveUtil.isTruckAvoidLimitedPath();
        if (isTruckAvoidLimitedPath && TextUtils.isEmpty(truckCarPlateNumber)) {
            isTruckAvoidLimitedPath = false;
        }
        this.mTruckAvoidWayLimitsCb.setChecked(isTruckAvoidLimitedPath);
        if (!isAvoidLimitedPath) {
            this.mCarPlateLayout.setVisibility(8);
        } else {
            this.mCarPlate.setCarPlate(carPlateNumber);
            this.mCarPlateLayout.setVisibility(0);
        }
        if (!isTruckAvoidLimitedPath) {
            this.mTruckDetailLayout.setVisibility(8);
        }
        closeOtherSwitch(this.mTruckAvoidWayLimitsCb);
        this.mNaviVoice = view.findViewById(R.id.navi_voice);
        this.mNaviVoiceChoice = (TextView) view.findViewById(R.id.navi_voice_choice);
        this.mNaviVoice.setOnClickListener(this);
        this.mBroadcastMode = view.findViewById(R.id.broadcast_mode);
        this.mBroadcastModeChoice = (TextView) view.findViewById(R.id.broadcast_mode_choice_text);
        this.mBroadcastModeChoice.setText(re.a(getContext()));
        this.mBroadcastMode.setOnClickListener(this);
        this.mDisplayEntrance = view.findViewById(R.id.display_entrance);
        this.mDisplayEntrance.setOnClickListener(this);
        this.mDisplayCarLogo = view.findViewById(R.id.display_carlogo);
        this.mDisplayCarLogo.setOnClickListener(this);
        this.mAccessibility = view.findViewById(R.id.accessibility);
        this.mAccessibility.setOnClickListener(this);
        this.eagleTmcSettingView = new qk();
        this.eagleTmcSettingView.a(view);
        this.mTruckAvoidWayLimitsCb.setOnCheckedChangeListener(this);
    }

    private void launchVoiceSquare() {
        Intent intent = new Intent();
        intent.putExtra(IVoicePackageManager.SHOW_TTS_FROM_KEY, 1);
        intent.putExtra(IVoicePackageManager.ENTRANCE_VOICE_SQUARE, true);
        IVoicePackageManager iVoicePackageManager = (IVoicePackageManager) ank.a(IVoicePackageManager.class);
        if (iVoicePackageManager != null) {
            iVoicePackageManager.deal(this, intent);
        }
    }

    public void onClick(View view) {
        if (this.mSearchRouteInNetMode == view) {
            if (this.mSearchRouteInNetModeChoice.isChecked()) {
                this.mSearchRouteInNetModeChoice.toggle();
                return;
            }
            this.willCheck = true;
            checkOfflineAvailable();
        } else if (this.mAvoidWayLimits == view) {
            this.mAvoidWayLimitsCb.toggle();
        } else if (this.mTruckAvoidWayLimits == view) {
            this.mTruckAvoidWayLimitsCb.toggle();
        } else {
            if (this.mTruckAvoidWayLimitsCb != view) {
                if (this.mBroadcastMode == view) {
                    PageBundle pageBundle = new PageBundle();
                    pageBundle.putInt("amap.extra.prefer.from", this.mFrom);
                    startPage(QuickAutoNaviBroadcastSettings.class, pageBundle);
                } else if (this.mNaviVoice == view) {
                    launchVoiceSquare();
                    actionLogV2("B013", null);
                } else if (this.mTruckDetailLayout == view) {
                    DriveUtil.startCarList(2, this);
                } else if (this.mDisplayEntrance == view) {
                    PageBundle pageBundle2 = new PageBundle();
                    pageBundle2.putInt("amap.extra.prefer.from", this.mFrom);
                    startPage(QuickAutoNaviDisplaySettings.class, pageBundle2);
                } else if (this.mDisplayCarLogo == view) {
                    IAccountService iAccountService = (IAccountService) defpackage.esb.a.a.a(IAccountService.class);
                    if (iAccountService != null) {
                        if (iAccountService.a()) {
                            startScheme(new Intent("android.intent.action.VIEW", Uri.parse(CARLOGO_AMAPURI)));
                        } else {
                            iAccountService.a(getPageContext(), (anq) new anq() {
                                public final void loginOrBindCancel() {
                                }

                                public final void onComplete(boolean z) {
                                    if (z && QuickAutonNaviSettingFragment.this.isAlive()) {
                                        QuickAutonNaviSettingFragment.this.startScheme(new Intent("android.intent.action.VIEW", Uri.parse(QuickAutonNaviSettingFragment.CARLOGO_AMAPURI)));
                                    }
                                }
                            });
                        }
                    }
                } else if (this.mAccessibility == view) {
                    PageBundle pageBundle3 = new PageBundle();
                    pageBundle3.putInt("amap.extra.prefer.from", this.mFrom);
                    startPage(QuickAutoNaviAccessibilitySettings.class, pageBundle3);
                } else if (this.mAvoidWayLimitsTips == view) {
                    startPageForResult((String) "amap.basemap.action.car_restrict_city_list", new PageBundle(), 1006);
                }
            }
        }
    }

    private void showBroadcastMode() {
        ListDialog listDialog = null;
        if (this.mDialog != null) {
            this.mDialog.dismiss();
            this.mDialog = null;
        }
        qv a2 = defpackage.qv.a.a;
        Activity activity = getActivity();
        String charSequence = this.mBroadcastModeChoice.getText().toString();
        AnonymousClass6 r4 = new b() {
            public final void a(int i, String str) {
                if (i == 0) {
                    re.a(QuickAutonNaviSettingFragment.this.getContext(), 2);
                    re.j(QuickAutonNaviSettingFragment.this.getContext(), true);
                } else if (i == 1) {
                    re.a(QuickAutonNaviSettingFragment.this.getContext(), 1);
                    re.j(QuickAutonNaviSettingFragment.this.getContext(), false);
                }
                QuickAutonNaviSettingFragment.this.mBroadcastModeChoice.setText(str);
                QuickAutonNaviSettingFragment.this.mDialog.dismiss();
            }
        };
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        qw qwVar = new qw(activity);
        Style style = Style.ITEM_WITH_CHECKBOX;
        arrayList.add(AMapAppGlobal.getApplication().getString(R.string.quickautonavi_car_setting_broadcast_mode_greenhand));
        arrayList2.add(AMapAppGlobal.getApplication().getString(R.string.quickautonavi_setting_broadcast_mode_greenhand_sub));
        arrayList.add(AMapAppGlobal.getApplication().getString(R.string.quickautonavi_car_setting_braodcast_mode_classic));
        arrayList2.add(AMapAppGlobal.getApplication().getString(R.string.quickautonavi_setting_broadcast_mode_classic_sub));
        String string = AMapAppGlobal.getApplication().getString(R.string.quickautonavi_setting_broadcast_mode);
        if (arrayList.size() > 0) {
            qwVar.setDlgTitle(string);
            ListView listView = qwVar.getListView();
            QuickAutoNaviAdapter quickAutoNaviAdapter = new QuickAutoNaviAdapter(activity, arrayList, arrayList2, charSequence);
            quickAutoNaviAdapter.setStyle(style);
            listView.setAdapter(quickAutoNaviAdapter);
            qwVar.setOnItemClickListener(new QuickAutoNaviSettingDialogFactory$1(a2, r4, arrayList));
            listDialog = qwVar;
        }
        this.mDialog = listDialog;
        if (this.mDialog != null) {
            this.mDialog.show();
        }
    }

    private void checkOfflineAvailable() {
        IOfflineManager iOfflineManager = (IOfflineManager) ank.a(IOfflineManager.class);
        if (iOfflineManager != null) {
            iOfflineManager.checkOfflineNavi(this, new ICheckOfflineResponse() {
                public void callback(boolean z) {
                    QuickAutonNaviSettingFragment.this.willCheck = false;
                    if (z) {
                        QuickAutonNaviSettingFragment.this.mHandler.sendMessage(QuickAutonNaviSettingFragment.this.mHandler.obtainMessage());
                        QuickAutonNaviSettingFragment.this.getActivity().runOnUiThread(new Runnable() {
                            public final void run() {
                                if (!QuickAutonNaviSettingFragment.this.mSearchRouteInNetModeChoice.isChecked()) {
                                    QuickAutonNaviSettingFragment.this.showOfflineAlertDialog();
                                } else {
                                    QuickAutonNaviSettingFragment.this.mSearchRouteInNetModeChoice.toggle();
                                }
                            }
                        });
                    }
                }
            }, new NodeDialogFragmentOnClickListener() {
                public final void onClick(NodeAlertDialogPage nodeAlertDialogPage) {
                    QuickAutonNaviSettingFragment.this.willCheck = true;
                }
            }, new NodeDialogFragmentOnClickListener() {
                public final void onClick(NodeAlertDialogPage nodeAlertDialogPage) {
                    QuickAutonNaviSettingFragment.this.willCheck = false;
                }
            });
        }
    }

    /* access modifiers changed from: private */
    public void showOfflineAlertDialog() {
        com.autonavi.widget.ui.AlertView.a aVar = new com.autonavi.widget.ui.AlertView.a(AMapAppGlobal.getApplication());
        aVar.a((CharSequence) getString(R.string.navi_settings_offline_dialog_title));
        aVar.b((CharSequence) getString(R.string.navi_settings_offline_dialog_msg));
        aVar.b((CharSequence) getString(R.string.navi_settings_offline_dialog_negative), (defpackage.ern.a) new defpackage.ern.a() {
            public final void onClick(AlertView alertView, int i) {
                QuickAutonNaviSettingFragment.this.willCheck = false;
                DriveSpUtil.setSearchRouteInNeMode(QuickAutonNaviSettingFragment.this.getContext(), true);
                QuickAutonNaviSettingFragment.this.dismissViewLayer(alertView);
            }
        });
        aVar.a((CharSequence) getString(R.string.navi_settings_offline_dialog_positive), (defpackage.ern.a) new defpackage.ern.a() {
            public final void onClick(AlertView alertView, int i) {
                QuickAutonNaviSettingFragment.this.willCheck = false;
                DriveSpUtil.setSearchRouteInNeMode(QuickAutonNaviSettingFragment.this.getContext(), false);
                QuickAutonNaviSettingFragment.this.mSearchRouteInNetModeChoice.toggle();
                QuickAutonNaviSettingFragment.this.dismissViewLayer(alertView);
            }
        });
        aVar.a(true);
        showViewLayer(aVar.a());
    }

    public void updateNaviVoiceItemState() {
        IVoicePackageManager iVoicePackageManager = (IVoicePackageManager) ank.a(IVoicePackageManager.class);
        if (iVoicePackageManager != null) {
            String currentTtsName2 = iVoicePackageManager.getCurrentTtsName2();
            rp.a();
            if (!TextUtils.isEmpty(currentTtsName2) && this.mNaviVoiceChoice != null) {
                re.a(currentTtsName2);
                this.mNaviVoiceChoice.setText(currentTtsName2);
            }
            iVoicePackageManager.isNaviTtsNewVersion();
        }
    }

    public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        if (this.mAvoidWayLimitsCb == compoundButton) {
            String carPlateNumber = DriveUtil.getCarPlateNumber();
            if (!z) {
                DriveUtil.setAvoidLimitedPath(z);
                this.mCarDivideLine.setVisibility(0);
                this.mCarDivideLineMatch.setVisibility(8);
                this.mCarPlateLayout.setVisibility(8);
            } else if (TextUtils.isEmpty(carPlateNumber)) {
                startPageForResult((String) "amap.basemap.action.car_plate_input", new PageBundle(), 65536);
            } else {
                DriveUtil.setAvoidLimitedPath(z);
                this.mCarDivideLine.setVisibility(8);
                this.mCarDivideLineMatch.setVisibility(0);
                this.mCarPlate.setCarPlate(carPlateNumber);
                this.mCarPlateLayout.setVisibility(0);
                closeOtherSwitch(this.mAvoidWayLimitsCb);
            }
        } else if (this.mTruckAvoidWayLimitsCb == compoundButton) {
            boolean isShowCarInfoDialog = DriveUtil.isShowCarInfoDialog();
            if (!z) {
                DriveUtil.setTruckAvoidLimitedPath(z);
                this.mTruckDivideLine.setVisibility(0);
                this.mTruckDivideLineMatch.setVisibility(8);
                this.mTruckDetailLayout.setVisibility(8);
            } else if (!isShowCarInfoDialog || !z || !DriveUtil.hasTruckPlateDesShown()) {
                onTruckItemClick(true);
            } else {
                this.mTruckAvoidWayLimitsCb.setChecked(false);
                DriveUtil.showNotAllDailog(this, R.string.dialog_cancel, null, new tm<String, Boolean>() {
                    public final /* synthetic */ void a(Object obj) {
                        QuickAutonNaviSettingFragment.this.isTruckRefresh = ((Boolean) obj).booleanValue();
                    }

                    public final void a(boolean z) {
                        QuickAutonNaviSettingFragment.this.isMendTruckDialogShow = z;
                    }
                });
            }
        } else {
            if (this.mSearchRouteInNetModeChoice == compoundButton) {
                DriveSpUtil.setSearchRouteInNeMode(getContext(), !this.mSearchRouteInNetModeChoice.isChecked());
            }
        }
    }

    private void onTruckItemClick(boolean z) {
        String truckCarPlateNumber = DriveUtil.getTruckCarPlateNumber();
        if (!z) {
            DriveUtil.setTruckAvoidLimitedPath(z);
            this.mTruckDivideLine.setVisibility(0);
            this.mTruckDivideLineMatch.setVisibility(8);
            this.mTruckDetailLayout.setVisibility(8);
        } else if (TextUtils.isEmpty(truckCarPlateNumber)) {
            this.isGetTruckAll = true;
            this.mTruckAvoidWayLimitsCb.setChecked(false);
            DriveUtil.startCarList(2, this);
        } else {
            DriveUtil.setTruckAvoidLimitedPath(z);
            closeOtherSwitch(this.mTruckAvoidWayLimitsCb);
            String[] a2 = sf.a();
            if (a2 != null) {
                this.mTruckCarPlate.setCarPlate(truckCarPlateNumber);
                this.mTruckPara.setText(a2[0]);
                this.mTruckDetailLayout.setVisibility(0);
                this.truckLenWidthHeight.setText(a2[1]);
            }
            this.mTruckDivideLine.setVisibility(8);
            this.mTruckDivideLineMatch.setVisibility(0);
        }
    }

    private void refreshTruckCarItem(boolean z) {
        String truckCarPlateNumber = DriveUtil.getTruckCarPlateNumber();
        if (!z) {
            DriveUtil.setTruckAvoidLimitedPath(z);
            this.mTruckDivideLine.setVisibility(0);
            this.mTruckDivideLineMatch.setVisibility(8);
            this.mTruckDetailLayout.setVisibility(8);
        } else if (!TextUtils.isEmpty(truckCarPlateNumber)) {
            DriveUtil.setTruckAvoidLimitedPath(z);
            closeOtherSwitch(this.mTruckAvoidWayLimitsCb);
            String[] a2 = sf.a();
            if (a2 != null) {
                this.mTruckCarPlate.setCarPlate(truckCarPlateNumber);
                this.mTruckPara.setText(a2[0]);
                this.mTruckDetailLayout.setVisibility(0);
                this.truckLenWidthHeight.setText(a2[1]);
            }
            this.mTruckDivideLine.setVisibility(8);
            this.mTruckDivideLineMatch.setVisibility(0);
        }
    }

    public void onPageResult(int i, ResultType resultType, PageBundle pageBundle) {
        if (resultType == ResultType.OK && pageBundle != null && pageBundle.containsKey(CarPlateInputView.BUNDLE_KEY_CAR_PLATE_NUMBER)) {
            if (i == 65536) {
                String string = pageBundle.getString(CarPlateInputView.BUNDLE_KEY_CAR_PLATE_NUMBER);
                boolean z = !TextUtils.isEmpty(string);
                if (z) {
                    this.mCarPlate.setCarPlate(string);
                    this.mCarPlateLayout.setVisibility(0);
                } else {
                    this.mCarPlateLayout.setVisibility(8);
                }
                this.mAvoidWayLimitsCb.setChecked(z);
                if (z) {
                    DriveUtil.setAvoidLimitedPath(true);
                }
                closeOtherSwitch(this.mAvoidWayLimitsCb);
            } else if (i == 65538 || i == 65539) {
                String string2 = pageBundle.getString(CarPlateInputView.BUNDLE_KEY_CAR_PLATE_NUMBER);
                boolean z2 = !TextUtils.isEmpty(string2);
                if (z2) {
                    String[] a2 = sf.a();
                    if (a2 != null) {
                        this.mTruckCarPlate.setCarPlate(string2);
                        this.mTruckPara.setText(a2[0]);
                        this.mTruckDetailLayout.setVisibility(0);
                        this.truckLenWidthHeight.setText(a2[1]);
                    }
                } else {
                    this.mTruckDetailLayout.setVisibility(8);
                }
                if (i == 65538) {
                    this.mTruckAvoidWayLimitsCb.setChecked(z2);
                    if (z2) {
                        DriveUtil.setTruckAvoidLimitedPath(true);
                    }
                    closeOtherSwitch(this.mTruckAvoidWayLimitsCb);
                }
            } else if (i == 1048582) {
                String string3 = pageBundle.getString(CarPlateInputView.BUNDLE_KEY_CAR_PLATE_NUMBER);
                if (!TextUtils.isEmpty(string3)) {
                    this.mCarPlate.setText(string3);
                }
            } else if (i == 10008) {
                if (!TextUtils.isEmpty(pageBundle.getString(CarPlateInputView.BUNDLE_KEY_CAR_PLATE_NUMBER))) {
                    this.mTruckAvoidWayLimitsCb.setChecked(true);
                    onTruckItemClick(true);
                    return;
                }
                this.mTruckAvoidWayLimitsCb.setChecked(false);
                onTruckItemClick(false);
            } else if (i == 1006) {
                String string4 = pageBundle.getString(CarPlateInputView.BUNDLE_KEY_CAR_PLATE_NUMBER);
                if (!TextUtils.isEmpty(string4)) {
                    if (DriveUtil.isAvoidLimitedPath()) {
                        this.mAvoidWayLimitsCb.setChecked(true);
                    } else if (DriveUtil.isTruckAvoidLimitedPath()) {
                        this.mTruckAvoidWayLimitsCb.setChecked(true);
                        String[] a3 = sf.a();
                        if (a3 != null) {
                            this.mTruckCarPlate.setCarPlate(string4);
                            this.mTruckPara.setText(a3[0]);
                            this.truckLenWidthHeight.setText(a3[1]);
                        }
                    }
                }
            }
        }
    }

    private void closeOtherSwitch(CheckBox checkBox) {
        if (checkBox.isChecked()) {
            if (checkBox == this.mAvoidWayLimitsCb) {
                this.mTruckAvoidWayLimitsCb.setChecked(false);
                DriveUtil.setTruckAvoidLimitedPath(false);
            } else if (checkBox == this.mTruckAvoidWayLimitsCb) {
                this.mAvoidWayLimitsCb.setChecked(false);
                DriveUtil.setAvoidLimitedPath(false);
            }
        }
    }

    private void actionLogV2(String str, JSONObject jSONObject) {
        if (jSONObject == null) {
            try {
                jSONObject = new JSONObject();
            } catch (JSONException e) {
                e.printStackTrace();
                return;
            }
        }
        if (isFromSettingInMore()) {
            jSONObject.put("from", DIYMainMapPresenter.DIY_ENTRY_KEY_MORE);
        } else {
            jSONObject.put("from", "路线");
        }
        LogUtil.actionLogV2("P00181", str, jSONObject);
    }

    private boolean isFromSettingInMore() {
        return this.mFrom == 2;
    }

    public void configurationChanged(Configuration configuration) {
        if (this.eagleTmcSettingView != null) {
            this.eagleTmcSettingView.a(configuration.orientation);
        }
    }
}
