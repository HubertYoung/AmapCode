package com.autonavi.minimap.drive.navi.navitts.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.Environment;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.aosservice.response.AosResponseException;
import com.amap.bundle.blutils.FileUtil;
import com.amap.bundle.blutils.PathManager;
import com.amap.bundle.blutils.PathManager.DirType;
import com.amap.bundle.drivecommon.mvp.view.DriveBasePage;
import com.amap.bundle.drivecommon.tools.DriveUtil;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.network.response.AbstractAOSParser;
import com.amap.bundle.statistics.LogManager;
import com.amap.bundle.tripgroup.api.IVoicePackageManager;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.bundle.banner.net.BannerResult;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.map.core.LocationMode.LocationIgnore;
import com.autonavi.map.fragmentcontainer.LaunchMode.launchModeSingleTask;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.map.widget.ProgressDlg;
import com.autonavi.miniapp.plugin.map.route.MiniAppShowRouteConfigHelper;
import com.autonavi.minimap.R;
import com.autonavi.minimap.banner.BannerRequestHolder;
import com.autonavi.minimap.banner.param.BannerListRequest;
import com.autonavi.minimap.basemap.traffic.TrafficUtil;
import com.autonavi.minimap.drive.navi.navitts.NVUtil;
import com.autonavi.minimap.drive.navi.navitts.NaviRecordUtil;
import com.autonavi.minimap.drive.navi.navitts.PPHelper.PPApkInstallReceiver;
import com.autonavi.minimap.drive.navi.navitts.PPHelper.PPDownloadResultReceiver;
import com.autonavi.minimap.drive.navi.navitts.adapter.CustomFragmentPagerAdapter;
import com.autonavi.minimap.drive.navi.navitts.download.NaviTtsErrorType;
import com.autonavi.minimap.drive.navi.navitts.net.RequestAllVoiceInfo;
import com.autonavi.minimap.drive.navi.navitts.widget.StickHeadScrollView;
import com.autonavi.minimap.drive.navi.navitts.widget.StickHeadScrollView.a;
import com.autonavi.minimap.drive.navi.navitts_dependencies.DriveOfflineSDK;
import com.autonavi.minimap.falcon.base.FalconAosPrepareResponseCallback;
import com.autonavi.minimap.offline.preference.OfflinePreference;
import com.autonavi.minimap.offline.utils.UserReport;
import com.autonavi.sdk.log.util.LogConstant;
import com.autonavi.widget.ui.AlertView;
import com.autonavi.widget.ui.TitleBar;
import com.iflytek.tts.TtsService.TtsManager;
import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import org.json.JSONException;
import org.json.JSONObject;
import org.xidea.el.json.JSONEncoder;

public class OfflineNaviTtsFragment extends DriveBasePage<dgk> implements Callback, OnPageChangeListener, OnClickListener, OnCheckedChangeListener, LocationIgnore, launchModeSingleTask, a, a, a {
    public static final int REQUEST_CODE_RECORD_ADD = 2;
    public static final int REQUEST_CODE_RECORD_EDIT = 3;
    public static final int REQUEST_CODE_SWITCH_SDCARD = 1;
    private static final String SCENE_ID = "route_dialect";
    private int currentTab = 0;
    /* access modifiers changed from: private */
    public int fromFlag = -1;
    /* access modifiers changed from: private */
    public boolean isShowInitDialog = false;
    /* access modifiers changed from: private */
    public final Object lockObj = new Object();
    private boolean mAdapterFringeLandscapeScreen = false;
    /* access modifiers changed from: private */
    public Context mContext;
    private int mCurrentOritation;
    private CustomFragmentPagerAdapter mCustomPagerAdapter;
    /* access modifiers changed from: private */
    public View mDownloadPPApkDialog;
    /* access modifiers changed from: private */
    public TextView mDownloadPPApkDialogProgressTV;
    /* access modifiers changed from: private */
    public View mDownloadPPApkProgressImg;
    private Handler mHandler = new Handler(Looper.getMainLooper(), this);
    public boolean mIsPPInstallReceiverRegister = false;
    private boolean mIsWifi2MobileShow = false;
    /* access modifiers changed from: private */
    public PPApkInstallReceiver mPPApkInstallReceiver;
    private PPDownloadResultReceiver mPPDownloadResultReceiver;
    private RadioGroup mRadioGroup;
    private StickHeadScrollView mStickHeadScrollView;
    private volatile dhe mTimerOutManager = null;
    private TitleBar mTitleBar;
    private dgi mTtsDialogHelper;
    private ViewPager mViewPager;
    private c mWifi2MobileListener;
    private OfflineNaviTtsMyFragment myFragment;
    private PageBundle nodeBundle;
    private dgl oldRecordVoice;
    /* access modifiers changed from: private */
    public ProgressDlg progressDlg = null;
    private OfflineNaviTtsSquareFragment squareFragment;

    public void onPageScrollStateChanged(int i) {
    }

    public void onPageScrolled(int i, float f, int i2) {
    }

    public int getCurrentTab() {
        return this.currentTab;
    }

    public void onCreate(Context context) {
        super.onCreate(context);
        tt.b(getActivity());
        setContentView(R.layout.fragment_offline_navitts);
        this.mContext = context;
    }

    /* access modifiers changed from: protected */
    public dgk createPresenter() {
        return new dgk(this);
    }

    public void onPageViewCreated() {
        int i;
        zt.a().a(SCENE_ID, true);
        this.nodeBundle = getArguments();
        int i2 = 0;
        if (this.nodeBundle != null) {
            this.fromFlag = this.nodeBundle.getInt(IVoicePackageManager.SHOW_TTS_FROM_KEY, -1);
            i = this.nodeBundle.getInt(IVoicePackageManager.SHOW_TTS_ORIENTATION, -1);
            this.mAdapterFringeLandscapeScreen = this.fromFlag == 1001;
        } else {
            i = -1;
        }
        if (this.fromFlag < 1001) {
            requestScreenOrientation(1);
        } else if (i != -1) {
            requestScreenOrientation(i);
        }
        dfo.k();
        dfo.j();
        dfo.h();
        dfo.c(getAppInitTTSVer());
        this.mTitleBar = (TitleBar) getContentView().findViewById(R.id.title);
        this.mTitleBar.setActionText(getString(R.string.recode_custom_navitts));
        TitleBar titleBar = this.mTitleBar;
        if (!canShowCustomVoiceRecordView()) {
            i2 = 8;
        }
        titleBar.setActionTextVisibility(i2);
        this.mTitleBar.setDivideVisibility(4);
        this.mTitleBar.setBackgroundResource(R.drawable.white_bg);
        this.mTitleBar.setOnBackClickListener((OnClickListener) new OnClickListener() {
            public final void onClick(View view) {
                if (OfflineNaviTtsFragment.this.isAlive()) {
                    OfflineNaviTtsFragment.this.finish();
                }
            }
        });
        this.mTitleBar.setOnActionClickListener(new OnClickListener() {
            public final void onClick(View view) {
                b.a.b();
                PageBundle pageBundle = new PageBundle();
                pageBundle.putInt("bundle_key_sequence_number", -1);
                pageBundle.putInt("bundle_key_work_mode", 0);
                OfflineNaviTtsFragment.this.startPageForResult(NavigationVoiceRecordFragment.class, pageBundle, 2);
            }
        });
        this.mViewPager = (ViewPager) getContentView().findViewById(R.id.viewpager_navitts);
        this.mCustomPagerAdapter = new CustomFragmentPagerAdapter(getFragmentManager());
        this.mViewPager.setAdapter(this.mCustomPagerAdapter);
        this.mRadioGroup = (RadioGroup) getContentView().findViewById(R.id.voice_radiogroup);
        this.mRadioGroup.setOnCheckedChangeListener(this);
        this.mRadioGroup.setFocusable(true);
        this.mRadioGroup.setFocusableInTouchMode(true);
        this.mRadioGroup.requestFocus();
        RadioButton radioButton = (RadioButton) getContentView().findViewById(R.id.voice_square);
        if (radioButton != null) {
            radioButton.getPaint().setFakeBoldText(true);
            radioButton.setOnClickListener(new OnClickListener() {
                public final void onClick(View view) {
                    OfflineNaviTtsFragment.this.selectSquareTtsTab(true);
                }
            });
        }
        RadioButton radioButton2 = (RadioButton) getContentView().findViewById(R.id.voice_my);
        if (radioButton2 != null) {
            radioButton2.getPaint().setFakeBoldText(true);
            radioButton2.setOnClickListener(new OnClickListener() {
                public final void onClick(View view) {
                    OfflineNaviTtsFragment.this.selectMyTtsTab(true);
                }
            });
        }
        this.mStickHeadScrollView = (StickHeadScrollView) findViewById(R.id.navitts_sticky_scrollview);
        this.mStickHeadScrollView.setHeaderStateChangedListener(this);
        onConfigurationChanged(DriveUtil.getOrientation(AMapAppGlobal.getApplication()));
        initAdapterView();
        TextView textView = (TextView) getContentView().findViewById(R.id.tts_voice_desc);
        if (textView != null) {
            textView.getPaint().setFakeBoldText(true);
        }
        this.mDownloadPPApkDialog = getContentView().findViewById(R.id.download_pp_apk_progress);
        this.mDownloadPPApkDialog.setOnClickListener(this);
        this.mDownloadPPApkDialogProgressTV = (TextView) this.mDownloadPPApkDialog.findViewById(R.id.download_pp_apk_progress_value);
        this.mDownloadPPApkProgressImg = this.mDownloadPPApkDialog.findViewById(R.id.download_pp_apk_progress_img);
        getContentView().findViewById(R.id.download_pp_apk_cancel).setOnClickListener(this);
        if (!dhd.c(this.mContext)) {
            ToastHelper.showToast(getString(R.string.offline_neterror_navitts));
        }
        DriveOfflineSDK.e().b = this.mHandler;
        startDownloadInitData();
        this.mWifi2MobileListener = new c() {
            WeakReference<OfflineNaviTtsFragment> a = new WeakReference<>(OfflineNaviTtsFragment.this);

            public final void a() {
                OfflineNaviTtsFragment offlineNaviTtsFragment = (OfflineNaviTtsFragment) this.a.get();
                if (offlineNaviTtsFragment != null && offlineNaviTtsFragment.isAlive()) {
                    OfflineNaviTtsFragment.this.showNowifiDownloadDialog(null, 100);
                }
            }
        };
        dfy.a().a = this.mWifi2MobileListener;
        this.mTtsDialogHelper = new dgi();
    }

    public void onPageNewNodeFragmentBundle(PageBundle pageBundle) {
        if (pageBundle != null && pageBundle.containsKey("bundle_key_voice_package_name")) {
            String string = pageBundle.getString("bundle_key_voice_package_name");
            if (!TextUtils.isEmpty(string)) {
                NaviRecordUtil.convertToAddNaviTts(string);
                dfo.a(true);
                dfo.d(string);
                if (this.currentTab == 0) {
                    selectMyTtsTab(true);
                    this.currentTab = 1;
                }
                this.myFragment.notifyAllData();
            }
        }
    }

    private boolean canShowCustomVoiceRecordView() {
        return this.fromFlag < 1001;
    }

    private void setCustomVoiceRecordViewVisibility(int i) {
        if (this.mTitleBar != null) {
            this.mTitleBar.setActionTextVisibility(i);
        }
    }

    private String getAppInitTTSVer() {
        DriveOfflineSDK.e();
        return DriveOfflineSDK.a(dhd.b(), (String) "dialectVoiceVersion");
    }

    public void onPageResume() {
        DriveOfflineSDK.e();
        if (DriveOfflineSDK.d()) {
            refreshListView();
        }
        if (this.nodeBundle != null) {
            this.fromFlag = this.nodeBundle.getInt(IVoicePackageManager.SHOW_TTS_FROM_KEY, -1);
        }
        if (this.fromFlag < 1001 && (this.mTitleBar.getActionText().getVisibility() == 8 || this.mTitleBar.getActionText().getVisibility() == 4)) {
            setCustomVoiceRecordViewVisibility(0);
        }
        this.mTtsDialogHelper.a = this;
        this.mTtsDialogHelper.b = this.fromFlag;
        this.mTtsDialogHelper.a();
    }

    public void onPagePause() {
        zt.a().a(SCENE_ID, false);
        if (getResources().getConfiguration().orientation == 1) {
            this.mTtsDialogHelper.a();
        }
        this.mTtsDialogHelper.a = null;
        b.a.b();
    }

    public void onPageResult(int i, ResultType resultType, PageBundle pageBundle) {
        if (i != 1) {
            if (i == 3 && resultType == ResultType.OK && pageBundle != null && pageBundle.containsKey("bundle_key_voice_package_name")) {
                String string = pageBundle.getString("bundle_key_voice_package_name");
                if (!TextUtils.isEmpty(string) && this.oldRecordVoice != null) {
                    NaviRecordUtil.convertToModifyNaviTts(this.oldRecordVoice, string);
                }
                refreshListView();
            }
            return;
        }
        dgu.a().c();
        ahl.a(new a() {
            public final Object doBackground() throws Exception {
                OfflineNaviTtsFragment.this.startDownloadInitData();
                NaviRecordUtil.init();
                OfflineNaviTtsFragment.this.sendHandlerMessage(41);
                return null;
            }
        });
    }

    public void onPageDestroy() {
        this.mTtsDialogHelper.a();
        DriveOfflineSDK.e();
        DriveOfflineSDK.b();
        DriveOfflineSDK.e().b = null;
        this.mHandler.removeCallbacksAndMessages(null);
        if (dfs.a() && dfs.a(this.mContext)) {
            PPDownloadResultReceiver.b(this.mContext, this.mPPDownloadResultReceiver);
        }
        if (this.mPPApkInstallReceiver != null && this.mIsPPInstallReceiverRegister) {
            PPApkInstallReceiver.b(this, this.mPPApkInstallReceiver);
        }
        stopTimer();
        CustomFragmentPagerAdapter customFragmentPagerAdapter = this.mCustomPagerAdapter;
        if (customFragmentPagerAdapter.a != null) {
            for (Fragment next : customFragmentPagerAdapter.a) {
                FragmentTransaction beginTransaction = customFragmentPagerAdapter.b.beginTransaction();
                beginTransaction.detach(next);
                beginTransaction.remove(next);
                beginTransaction.commit();
            }
        }
        b.a.a();
        if (this.progressDlg != null && this.progressDlg.isShowing()) {
            this.progressDlg.dismiss();
        }
        this.progressDlg = null;
        dfy.a().a = null;
    }

    public boolean onPageBackPressed() {
        if (this.mDownloadPPApkDialog == null || this.mDownloadPPApkDialog.getVisibility() != 0) {
            dgi dgi = this.mTtsDialogHelper;
            if (!(dgi.c != null && dgi.c.isShown())) {
                return false;
            }
            this.mTtsDialogHelper.a();
            return true;
        }
        dfs.b();
        this.mDownloadPPApkDialog.setVisibility(8);
        return true;
    }

    public void dismissDialog() {
        this.mTtsDialogHelper.a();
    }

    public void showDownloadDialog(dgl dgl, boolean z) {
        this.mTtsDialogHelper.a(this, dgl, z);
    }

    public void onClick(View view) {
        if (view.getId() == R.id.download_pp_apk_cancel) {
            dfs.b();
            if (this.mDownloadPPApkDialog != null) {
                this.mDownloadPPApkDialog.setVisibility(8);
            }
        }
    }

    public void onSDCardUNMounted() {
        if (isInitDialogShowing()) {
            showSdcardUnmountedDialog();
        } else {
            showFileIOExceptionDialog();
        }
    }

    private boolean isInitDialogShowing() {
        return this.isShowInitDialog && this.progressDlg != null && this.progressDlg.isShowing();
    }

    private void showSdcardUnmountedDialog() {
        if (getValidActivity() != null) {
            AlertView.a aVar = new AlertView.a(AMapAppGlobal.getApplication());
            aVar.a((CharSequence) getString(R.string.offline_navi_tts_sdcard_dialog_title)).a((CharSequence) getString(R.string.offline_navi_tts_retry), (a) new a() {
                public final void onClick(AlertView alertView, int i) {
                    OfflineNaviTtsFragment.this.dismissViewLayer(alertView);
                    OfflineNaviTtsFragment.this.startDownloadInitData();
                    OfflineNaviTtsFragment.this.refreshListView();
                }
            }).b((CharSequence) getString(R.string.cancle), (a) new a() {
                public final void onClick(AlertView alertView, int i) {
                    OfflineNaviTtsFragment.this.finish();
                }
            });
            aVar.a(true);
            showViewLayer(aVar.a());
        }
    }

    public void onRefreshSDMessage() {
        refreshListView();
    }

    public void onClickDialogItem(int i, Object obj) {
        String str;
        final dgl dgl = (dgl) obj;
        switch (i) {
            case 1:
                checkNetAndSendHandlerMessage(dgl, 100);
                if (dgl.g() != 64) {
                    LogManager.actionLogV2("P00067", "B011", dgv.a(WidgetType.LAYER, dgl.a.c, NVUtil.a(this.fromFlag), NVUtil.a()));
                }
                if (dfs.a() && dfs.a(this.mContext)) {
                    LogManager.actionLogV2("P00067", "B028", 0, 0);
                    return;
                } else if (dfs.a() && dfs.b(this.mContext) && aaw.b(this.mContext) == 4) {
                    LogManager.actionLogV2("P00067", "B025", 0, 0);
                    return;
                }
                break;
            case 2:
                DriveOfflineSDK.e().c(dgl);
                return;
            case 3:
                checkNetAndSendHandlerMessage(dgl, 101);
                return;
            case 4:
                ahl.a(new a() {
                    public final Object doBackground() throws Exception {
                        synchronized (OfflineNaviTtsFragment.this.lockObj) {
                            b.a.b();
                            dfo.d(null);
                            TtsManager.getInstance().TTS_Txt(OfflineNaviTtsFragment.this.mContext.getApplicationContext(), OfflineNaviTtsFragment.this.getString(R.string.offline_navitts_recode_cancel));
                            OfflineNaviTtsFragment.this.getActivity().runOnUiThread(new Runnable() {
                                public final void run() {
                                    ToastHelper.showToast(OfflineNaviTtsFragment.this.getString(R.string.offline_navitts_recode_cancel));
                                    OfflineNaviTtsFragment.this.refreshListView();
                                }
                            });
                        }
                        return null;
                    }
                });
                LogManager.actionLogV2(UserReport.PAGE_NAVITTS_MYNAVITTS, "B002", 0, 0);
                return;
            case 5:
                ahl.a(new a() {
                    public final Object doBackground() throws Exception {
                        synchronized (OfflineNaviTtsFragment.this.lockObj) {
                            b.a.b();
                            dfo.d(dgl.a.c);
                            TtsManager.getInstance().TTS_Txt(OfflineNaviTtsFragment.this.mContext.getApplicationContext(), OfflineNaviTtsFragment.this.getString(R.string.offline_navitts_recode_setting));
                            OfflineNaviTtsFragment.this.getActivity().runOnUiThread(new Runnable() {
                                public final void run() {
                                    ToastHelper.showToast(OfflineNaviTtsFragment.this.getString(R.string.offline_navitts_recode_setting));
                                    OfflineNaviTtsFragment.this.refreshListView();
                                }
                            });
                        }
                        return null;
                    }
                });
                LogManager.actionLogV2(UserReport.PAGE_NAVITTS_MYNAVITTS, "B003", 0, 0);
                return;
            case 6:
                b.a.b();
                final dgl c = dfx.a().c();
                dfx.a();
                dfx.a(dgl, (dgx) new dgx() {
                    public final void a(boolean z) {
                        String str;
                        OfflineNaviTtsFragment.this.sendHandlerMessage(41);
                        OfflineNaviTtsFragment offlineNaviTtsFragment = OfflineNaviTtsFragment.this;
                        if (c == null) {
                            str = "";
                        } else {
                            str = c.a.l;
                        }
                        offlineNaviTtsFragment.uploadTTSNameLog(str);
                    }
                });
                refreshListView();
                return;
            case 7:
                b.a.b();
                this.oldRecordVoice = dgl;
                startVoiceListFragment(dgl);
                break;
            case 8:
                if (dgl == null || dgl.a.o != 50) {
                    str = getString(R.string.offline_navitts_recode_delete_tip_official);
                } else {
                    str = getString(R.string.offline_navitts_recode_delete_tip_custom);
                }
                showDeleteDataDialog(str, dgl);
                return;
            case 9:
                sendHandlerMessage(103, dgl);
                if (dgl.g() != 1) {
                    return;
                }
                break;
            case 10:
                if (dfs.a(this.mContext)) {
                    dfr.a(this.mContext, dgl);
                    LogManager.actionLogV2("P00067", LogConstant.MAIN_MSGBOX_TIP_CLICK, 0, 0);
                    return;
                } else if (dfs.b(getContext())) {
                    boolean a = dfs.a(new dfq(this, dgl));
                    LogManager.actionLogV2("P00067", "B024", 0, 0);
                    if (!a) {
                        ToastHelper.showLongToast("PP高速引擎启动失败，请稍后再试");
                    }
                    if (this.mPPDownloadResultReceiver == null) {
                        this.mPPDownloadResultReceiver = new PPDownloadResultReceiver(this);
                        PPDownloadResultReceiver.a(this.mContext, this.mPPDownloadResultReceiver);
                    }
                    return;
                }
                break;
        }
    }

    private void showDeleteDataDialog(String str, final dgl dgl) {
        if (getValidActivity() != null) {
            final bid pageContext = AMapPageUtil.getPageContext();
            AlertView.a aVar = new AlertView.a(AMapAppGlobal.getApplication());
            aVar.a((CharSequence) str).a((CharSequence) getString(R.string.Ok), (a) new a() {
                public final void onClick(AlertView alertView, int i) {
                    if (pageContext != null) {
                        pageContext.dismissViewLayer(alertView);
                    }
                    OfflineNaviTtsFragment.this.sendHandlerMessage(103, dgl);
                }
            }).b((CharSequence) getString(R.string.cancle), (a) new a() {
                public final void onClick(AlertView alertView, int i) {
                    if (pageContext != null) {
                        pageContext.dismissViewLayer(alertView);
                    }
                }
            });
            aVar.a(true);
            if (pageContext != null) {
                pageContext.showViewLayer(aVar.a());
            }
        }
    }

    public boolean handleMessage(Message message) {
        int i = message.what;
        if (i != 12) {
            if (i == 41) {
                refreshListView();
            } else if (i == 50) {
                ensureDialogDismiss();
            } else if (i != 52) {
                switch (i) {
                    case 0:
                    case 1:
                    case 2:
                    case 3:
                        if (message.obj != null && (message.obj instanceof dgl)) {
                            notifyItemData((dgl) message.obj);
                            break;
                        }
                    case 4:
                        if (message.obj != null && (message.obj instanceof dgl)) {
                            dgl dgl = (dgl) message.obj;
                            dgl.a(4);
                            this.mTtsDialogHelper.a(this, dgl, true);
                            notifyItemData(dgl);
                            dgm.a().a(true);
                            break;
                        }
                    case 5:
                        if (message.obj != null && (message.obj instanceof DriveOfflineSDK.a)) {
                            DriveOfflineSDK.a aVar = (DriveOfflineSDK.a) message.obj;
                            final dgl dgl2 = aVar.a;
                            if (aVar.b == NaviTtsErrorType.network_exception) {
                                dgl2.a(10);
                            }
                            notifyItemData(dgl2);
                            if (aVar.b != NaviTtsErrorType.MD5_ERROR) {
                                if (aVar.b != NaviTtsErrorType.network_exception) {
                                    if (aVar.b != NaviTtsErrorType.file_io_exception) {
                                        if (aVar.b == NaviTtsErrorType.download_not_enoughspace) {
                                            ToastHelper.showToast(this.mContext.getApplicationContext().getString(R.string.offline_storage_noenough));
                                            break;
                                        }
                                    } else {
                                        showFileIOExceptionDialog();
                                        break;
                                    }
                                } else {
                                    getHandler().postDelayed(new Runnable() {
                                        public final void run() {
                                            if (!OfflineNaviTtsFragment.this.isAlive() || dhd.b(OfflineNaviTtsFragment.this.getContext()) || !dhd.a(OfflineNaviTtsFragment.this.getContext())) {
                                                ToastHelper.showLongToast(OfflineNaviTtsFragment.this.mContext.getApplicationContext().getString(R.string.offline_neterror));
                                            } else {
                                                OfflineNaviTtsFragment.this.showNowifiDownloadDialog(dgl2, 100);
                                            }
                                        }
                                    }, 1000);
                                    break;
                                }
                            } else {
                                showMd5ErrorDialog(dgl2);
                                break;
                            }
                        }
                        break;
                    default:
                        switch (i) {
                            case 30:
                                ensureDialogDismiss();
                                showInitDownloadingDialog();
                                break;
                            case 31:
                                finishInitManager();
                                break;
                            default:
                                switch (i) {
                                    case 100:
                                    case 101:
                                        if (message.obj instanceof dgl) {
                                            final dgl dgl3 = (dgl) message.obj;
                                            if (!dfp.a(dgl3)) {
                                                ToastHelper.showToast(this.mContext.getApplicationContext().getString(R.string.offline_storage_noenough));
                                                break;
                                            } else {
                                                ahl.a(new a() {
                                                    public final Object doBackground() throws Exception {
                                                        JSONObject jSONObject;
                                                        DriveOfflineSDK e = DriveOfflineSDK.e();
                                                        Context access$500 = OfflineNaviTtsFragment.this.mContext;
                                                        dgl dgl = dgl3;
                                                        if (!dfp.a(dgl)) {
                                                            ToastHelper.showToast(access$500.getString(R.string.offline_storage_noenough));
                                                        } else {
                                                            e.b(dgl);
                                                            try {
                                                                String str = dgl.a.c;
                                                                if (str == null) {
                                                                    str = dgl.a.l;
                                                                }
                                                                if (!TextUtils.isEmpty(str)) {
                                                                    jSONObject = new JSONObject();
                                                                    jSONObject.put(TrafficUtil.KEYWORD, str);
                                                                } else {
                                                                    jSONObject = null;
                                                                }
                                                                LogManager.actionLogV2(UserReport.PAGE_NAVITTS_SQUARE_DOWNLOAD, "B001", jSONObject);
                                                            } catch (JSONException e2) {
                                                                e2.printStackTrace();
                                                            }
                                                        }
                                                        OfflineNaviTtsFragment.this.getActivity().runOnUiThread(new Runnable() {
                                                            public final void run() {
                                                                OfflineNaviTtsFragment.this.refreshListView();
                                                            }
                                                        });
                                                        return null;
                                                    }
                                                });
                                                break;
                                            }
                                        }
                                        break;
                                    case 102:
                                        DriveOfflineSDK e = DriveOfflineSDK.e();
                                        if (DriveOfflineSDK.d()) {
                                            CopyOnWriteArrayList<dgl> p = DriveOfflineSDK.p();
                                            if (p != null && p.size() > 0) {
                                                Iterator<dgl> it = p.iterator();
                                                while (it.hasNext()) {
                                                    dgl next = it.next();
                                                    if (next != null) {
                                                        int g = next.g();
                                                        if (g == 5 || g == 10) {
                                                            e.b(next);
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                        refreshListView();
                                        break;
                                    case 103:
                                        if (message.obj instanceof dgl) {
                                            final dgl dgl4 = (dgl) message.obj;
                                            if (!dgl4.c()) {
                                                if (isStarted()) {
                                                    showDeletingDialog();
                                                }
                                                DriveOfflineSDK.e().a(dgl4, (dgx) new dgx() {
                                                    public final void a(boolean z) {
                                                        String str;
                                                        OfflineNaviTtsFragment offlineNaviTtsFragment = OfflineNaviTtsFragment.this;
                                                        if (dgl4 == null) {
                                                            str = "";
                                                        } else {
                                                            str = dgl4.a.l;
                                                        }
                                                        offlineNaviTtsFragment.uploadTTSNameLog(str);
                                                    }
                                                });
                                                if (dgl4.e()) {
                                                    this.squareFragment.notifyAllData();
                                                    break;
                                                }
                                            } else {
                                                NaviRecordUtil.delete(dgl4);
                                                LogManager.actionLogV2(UserReport.PAGE_NAVITTS_MYNAVITTS, "B005", 0, 0);
                                                this.myFragment.notifyAllData();
                                                break;
                                            }
                                        }
                                        break;
                                    default:
                                        switch (i) {
                                            case MiniAppShowRouteConfigHelper.LINE_TYPE_VIA_ROAD_LINE_FREE_ /*120*/:
                                                handleTtsRequest();
                                                break;
                                            case 121:
                                                ToastHelper.showLongToast(this.mContext.getApplicationContext().getString(R.string.offline_neterror_navitts));
                                                break;
                                            default:
                                                return false;
                                        }
                                }
                        }
                }
            } else if (message.obj instanceof dgl) {
                showNowifiDownloadDialog((dgl) message.obj, message.arg1);
            }
        } else if (message.obj != null && (message.obj instanceof dgl)) {
            dgl dgl5 = (dgl) message.obj;
            dgl5.a(4);
            notifyItemData(dgl5);
            dgm.a().a(true);
        }
        return true;
    }

    /* access modifiers changed from: private */
    public void showNowifiDownloadDialog(final dgl dgl, final int i) {
        if (getValidActivity() != null && !this.mIsWifi2MobileShow) {
            AlertView.a aVar = new AlertView.a(AMapAppGlobal.getApplication());
            aVar.a((CharSequence) this.mContext.getApplicationContext().getString(R.string.offline_navitts_download_not_wifi)).a((CharSequence) getString(R.string.Ok), (a) new a() {
                public final void onClick(AlertView alertView, int i) {
                    OfflineNaviTtsFragment.this.dismissViewLayer(alertView);
                    if (dgl != null) {
                        OfflineNaviTtsFragment.this.sendHandlerMessage(i, dgl);
                        return;
                    }
                    DriveOfflineSDK e = DriveOfflineSDK.e();
                    if (DriveOfflineSDK.d()) {
                        ahl.a(new a() {
                            public final Object doBackground() throws Exception {
                                CopyOnWriteArrayList<dgl> p = DriveOfflineSDK.p();
                                if (p != null && p.size() > 0) {
                                    Iterator<dgl> it = p.iterator();
                                    while (it.hasNext()) {
                                        dgl next = it.next();
                                        int g = next.g();
                                        if (g == 1 || g == 2 || g == 5 || g == 64 || g == 10) {
                                            DriveOfflineSDK.this.b(next);
                                        }
                                    }
                                }
                                return null;
                            }
                        });
                    }
                }
            }).b((CharSequence) getString(R.string.cancle), (a) new a() {
                public final void onClick(AlertView alertView, int i) {
                    OfflineNaviTtsFragment.this.dismissViewLayer(alertView);
                }
            });
            aVar.a(true);
            showViewLayer(aVar.a());
        }
    }

    private void showMd5ErrorDialog(final dgl dgl) {
        if (getValidActivity() != null) {
            AlertView.a aVar = new AlertView.a(AMapAppGlobal.getApplication());
            aVar.a((CharSequence) getString(R.string.offline_navi_tts_voice_fail_dialog_title)).a((CharSequence) getString(R.string.Ok), (a) new a() {
                public final void onClick(AlertView alertView, int i) {
                    OfflineNaviTtsFragment.this.dismissViewLayer(alertView);
                    OfflineNaviTtsFragment.this.sendHandlerMessage(103, dgl);
                }
            }).b((CharSequence) getString(R.string.offline_navi_tts_voice_fail_dialog_down_retry), (a) new a() {
                public final void onClick(AlertView alertView, int i) {
                    OfflineNaviTtsFragment.this.dismissViewLayer(alertView);
                    OfflineNaviTtsFragment.this.sendHandlerMessage(103, dgl);
                    dgl.a(0);
                    OfflineNaviTtsFragment.this.checkNetAndSendHandlerMessage(dgl, 100);
                }
            });
            aVar.a(true);
            showViewLayer(aVar.a());
        }
    }

    public void notifyItemData(dgl dgl) {
        if (dgl != null) {
            if (this.myFragment != null) {
                this.myFragment.notifyData(dgl);
            }
            if (this.squareFragment != null) {
                this.squareFragment.notifyData(dgl);
            }
        }
    }

    public void onPageSelected(int i) {
        this.currentTab = i;
        if (this.mRadioGroup != null) {
            if (i == 0) {
                this.mRadioGroup.check(R.id.voice_square);
            }
            if (i == 1) {
                this.mRadioGroup.check(R.id.voice_my);
            }
        }
    }

    public void onResult() {
        dismissProgressDialog();
        Activity activity = getActivity();
        if (activity != null) {
            stopTimer();
            activity.runOnUiThread(new Runnable(activity, this.mContext.getString(R.string.offline_db_error_dialog_title), this.mContext.getString(R.string.offline_db_error_dialog_content), this.mContext.getString(R.string.offline_db_error_dialog_know)) {
                final /* synthetic */ Activity a;
                final /* synthetic */ String b;
                final /* synthetic */ String c;
                final /* synthetic */ String d;

                {
                    this.a = r1;
                    this.b = r2;
                    this.c = r3;
                    this.d = r4;
                }

                public final void run() {
                    if (this.a != null && !this.a.isFinishing()) {
                        AlertView.a aVar = new AlertView.a(AMapAppGlobal.getApplication());
                        final bid pageContext = AMapPageUtil.getPageContext();
                        aVar.a((CharSequence) this.b).b((CharSequence) this.c).a((CharSequence) this.d, (a) new a() {
                            public final void onClick(AlertView alertView, int i) {
                                if (pageContext != null) {
                                    pageContext.dismissViewLayer(alertView);
                                }
                            }
                        });
                        aVar.a(true);
                        if (pageContext != null) {
                            pageContext.showViewLayer(aVar.a());
                        }
                    }
                }
            });
        }
    }

    public void dismissProgressDialog() {
        Activity activity = getActivity();
        if (activity != null && !activity.isFinishing() && this.progressDlg != null && this.progressDlg.isShowing()) {
            this.progressDlg.dismiss();
        }
    }

    public void refreshListView() {
        if (this.myFragment != null) {
            this.myFragment.notifyAllData();
        }
        if (this.squareFragment != null) {
            this.squareFragment.notifyAllData();
        }
    }

    public Handler getHandler() {
        return this.mHandler;
    }

    public void sendHandlerMessage(int i) {
        this.mHandler.sendEmptyMessage(i);
    }

    public void sendHandlerMessage(int i, Object obj) {
        this.mHandler.sendMessage(this.mHandler.obtainMessage(i, obj));
    }

    public void sendHandlerMessage(int i, int i2, Object obj) {
        this.mHandler.sendMessage(this.mHandler.obtainMessage(i, i2, 0, obj));
    }

    public synchronized void checkNetAndSendHandlerMessage(dgl dgl, int i) {
        if (aaw.d(this.mContext.getApplicationContext()) == 0) {
            ToastHelper.showToast(this.mContext.getApplicationContext().getString(R.string.offline_neterror_navitts));
        } else if (aaw.d(this.mContext.getApplicationContext()) == 1) {
            sendHandlerMessage(i, dgl);
        } else {
            sendHandlerMessage(52, i, dgl);
        }
    }

    /* JADX INFO: finally extract failed */
    /* access modifiers changed from: private */
    public void startDownloadInitData() {
        String str;
        String str2;
        String str3;
        DriveOfflineSDK.e();
        for (dgl next : DriveOfflineSDK.p()) {
            if (next != null && (next.g() == 1 || next.g() == 2)) {
                return;
            }
        }
        showInitDownloadingDialog();
        startTimer();
        DriveOfflineSDK e = DriveOfflineSDK.e();
        AnonymousClass14 r1 = new dgx() {
            public final void a(boolean z) {
                AMapLog.i(OfflineNaviTtsFragment.class.getSimpleName(), "daihq  startDownloadInitData   networkRequestTtsDataAll   callback   success:".concat(String.valueOf(z)));
                if (!z) {
                    OfflineNaviTtsFragment.this.sendHandlerMessage(121);
                }
                OfflineNaviTtsFragment.this.sendHandlerMessage(MiniAppShowRouteConfigHelper.LINE_TYPE_VIA_ROAD_LINE_FREE_);
            }
        };
        CountDownLatch countDownLatch = new CountDownLatch(2);
        e.a = false;
        com.autonavi.minimap.drive.navi.navitts_dependencies.DriveOfflineSDK.AnonymousClass1 r6 = new dgx(countDownLatch) {
            final /* synthetic */ CountDownLatch a;

            {
                this.a = r2;
            }

            public final void a(boolean z) {
                this.a.countDown();
                DriveOfflineSDK.this.a = z;
            }
        };
        dgz a = dgz.a();
        a.a((dgx) r6);
        defpackage.dgz.AnonymousClass2 r8 = new dgx(r6) {
            final /* synthetic */ dgx a;

            {
                this.a = r2;
            }

            public final void a(boolean z) {
                StringBuilder sb = new StringBuilder("requestVoiceBannerList callback ThreadID:");
                sb.append(Thread.currentThread().getId());
                sb.append(", success:");
                sb.append(z);
                dhb.a("OfflineDataInit", sb.toString());
                dgz.b(dgz.this);
                if (this.a != null) {
                    this.a.a(z);
                }
            }
        };
        RequestAllVoiceInfo requestAllVoiceInfo = new RequestAllVoiceInfo();
        requestAllVoiceInfo.a.lock();
        double[] c = dhd.c();
        String[] d = dhd.d();
        if (d != null) {
            str2 = d[0];
            str = d[1];
            str3 = d[2];
        } else {
            str2 = "";
            str = "";
            str3 = "";
        }
        try {
            BannerListRequest bannerListRequest = new BannerListRequest();
            bannerListRequest.d = str2;
            bannerListRequest.j = str3;
            bannerListRequest.i = str;
            bannerListRequest.g = "";
            bannerListRequest.h = Integer.parseInt("19");
            bannerListRequest.b = String.valueOf(c[0]);
            bannerListRequest.c = String.valueOf(c[1]);
            bannerListRequest.setTimeout(8000);
            bannerListRequest.setRetryTimes(0);
            BannerRequestHolder.getInstance().sendList(bannerListRequest, new FalconAosPrepareResponseCallback<BannerResult>(r8) {
                final /* synthetic */ dgx a;

                {
                    this.a = r2;
                }

                public final /* synthetic */ Object a(AosByteResponse aosByteResponse) {
                    return b(aosByteResponse);
                }

                public final /* synthetic */ void a(Object obj) {
                    BannerResult bannerResult = (BannerResult) obj;
                    String str = null;
                    LinkedList linkedList = bannerResult == null ? null : bannerResult.items;
                    if (linkedList == null || linkedList.size() <= 0) {
                        dfo.a((String) null, 0);
                    } else {
                        if (!linkedList.isEmpty()) {
                            str = JSONEncoder.encode(linkedList);
                        }
                        dfo.a(str, dhd.g());
                    }
                    if (this.a != null) {
                        this.a.a(true);
                    }
                }

                public final void a(AosRequest aosRequest, AosResponseException aosResponseException) {
                    dfo.a((String) null, 0);
                    if (this.a != null) {
                        this.a.a(false);
                    }
                }

                private static BannerResult b(AosByteResponse aosByteResponse) {
                    if (aosByteResponse.getResult() == null) {
                        return null;
                    }
                    try {
                        JSONObject aosByteResponseToJSONObject = AbstractAOSParser.aosByteResponseToJSONObject(aosByteResponse);
                        if (aosByteResponseToJSONObject != null) {
                            new asi();
                            return asi.a(aosByteResponseToJSONObject);
                        }
                    } catch (JSONException e) {
                        AMapLog.w("Banner", e.getLocalizedMessage());
                    }
                    return null;
                }
            });
            requestAllVoiceInfo.a.unlock();
            new Thread(new Runnable(countDownLatch, r1) {
                final /* synthetic */ CountDownLatch a;
                final /* synthetic */ dgx b;

                {
                    this.a = r2;
                    this.b = r3;
                }

                public final void run() {
                    String simpleName;
                    StringBuilder sb;
                    try {
                        this.a.await();
                        simpleName = DriveOfflineSDK.class.getSimpleName();
                        sb = new StringBuilder("daihq   networkRequestTtsDataAll  等待状态变为0, 即为初始化完成 callback   mResultSuccess:");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        simpleName = DriveOfflineSDK.class.getSimpleName();
                        sb = new StringBuilder("daihq   networkRequestTtsDataAll  等待状态变为0, 即为初始化完成 callback   mResultSuccess:");
                    } catch (Throwable th) {
                        String simpleName2 = DriveOfflineSDK.class.getSimpleName();
                        StringBuilder sb2 = new StringBuilder("daihq   networkRequestTtsDataAll  等待状态变为0, 即为初始化完成 callback   mResultSuccess:");
                        sb2.append(DriveOfflineSDK.this.a);
                        AMapLog.i(simpleName2, sb2.toString());
                        this.b.a(DriveOfflineSDK.this.a);
                        throw th;
                    }
                    sb.append(DriveOfflineSDK.this.a);
                    AMapLog.i(simpleName, sb.toString());
                    this.b.a(DriveOfflineSDK.this.a);
                }
            }).start();
        } catch (Throwable th) {
            requestAllVoiceInfo.a.unlock();
            throw th;
        }
    }

    private void handleTtsRequest() {
        DriveOfflineSDK.e();
        if (!DriveOfflineSDK.d()) {
            DriveOfflineSDK.e();
            DriveOfflineSDK.j();
        }
        finishInitManager();
    }

    private void finishInitManager() {
        final List<ua> c = dgm.a().c();
        stopTimer();
        if (isAlive()) {
            getActivity().runOnUiThread(new Runnable() {
                public final void run() {
                    OfflineNaviTtsFragment.this.ensureDialogDismiss();
                    if (OfflineNaviTtsFragment.this.fromFlag < 1001 && c != null && c.size() > 0) {
                        OfflineNaviTtsFragment.this.showUpdateTip();
                    }
                    OfflineNaviTtsFragment.this.refreshListView();
                    OfflineNaviTtsFragment.this.checkStorage();
                }
            });
            if (dfs.a() && dfs.a(this.mContext) && this.mPPDownloadResultReceiver == null) {
                this.mPPDownloadResultReceiver = new PPDownloadResultReceiver(this);
                PPDownloadResultReceiver.a(this.mContext, this.mPPDownloadResultReceiver);
            }
            if (dfs.a()) {
                scanPPHelperDirectoryAndGetVoiceFile();
            }
        }
    }

    public void stopTimer() {
        if (this.mTimerOutManager != null) {
            this.mTimerOutManager.a = null;
            this.mTimerOutManager.a();
            this.mTimerOutManager = null;
        }
    }

    public void startTimer() {
        if (this.mTimerOutManager == null) {
            this.mTimerOutManager = new dhe();
            this.mTimerOutManager.a = this;
        }
        dhe dhe = this.mTimerOutManager;
        if (dhe.b != null) {
            dhe.a();
        }
        dhe.b = new Timer("TimerOutManager.Timer");
        dhe.c = new b(dhe, 0);
        dhe.b.schedule(dhe.c, 120000);
        dhb.a("TimerOutManager", "start timer..");
    }

    private void initAdapterView() {
        if (this.nodeBundle != null) {
            this.fromFlag = this.nodeBundle.getInt(IVoicePackageManager.SHOW_TTS_FROM_KEY);
        }
        if (this.squareFragment == null && this.myFragment == null) {
            ArrayList arrayList = new ArrayList(2);
            this.squareFragment = new OfflineNaviTtsSquareFragment();
            this.squareFragment.setParentFragment(this);
            OfflineNaviTtsSquareFragment offlineNaviTtsSquareFragment = this.squareFragment;
            DriveOfflineSDK.e();
            offlineNaviTtsSquareFragment.setData(DriveOfflineSDK.p());
            OfflineNaviTtsSquareFragment offlineNaviTtsSquareFragment2 = this.squareFragment;
            DriveOfflineSDK.e();
            offlineNaviTtsSquareFragment2.setBannerData(DriveOfflineSDK.q());
            this.squareFragment.setFromFlag(this.fromFlag);
            arrayList.add(this.squareFragment);
            this.myFragment = new OfflineNaviTtsMyFragment();
            this.myFragment.setParentFragment(this);
            this.myFragment.setFromFlag(this.fromFlag);
            arrayList.add(this.myFragment);
            this.mCustomPagerAdapter.a = arrayList;
        } else {
            if (this.squareFragment != null) {
                OfflineNaviTtsSquareFragment offlineNaviTtsSquareFragment3 = this.squareFragment;
                DriveOfflineSDK.e();
                offlineNaviTtsSquareFragment3.setData(DriveOfflineSDK.p());
                OfflineNaviTtsSquareFragment offlineNaviTtsSquareFragment4 = this.squareFragment;
                DriveOfflineSDK.e();
                offlineNaviTtsSquareFragment4.setBannerData(DriveOfflineSDK.q());
                this.squareFragment.setFromFlag(this.fromFlag);
                this.squareFragment.notifyAllData();
            }
            if (this.myFragment != null) {
                this.myFragment.setFromFlag(this.fromFlag);
                this.myFragment.notifyAllData();
            }
        }
        this.mCustomPagerAdapter.notifyDataSetChanged();
        if (this.fromFlag >= 1001) {
            setCustomVoiceRecordViewVisibility(8);
        } else {
            setCustomVoiceRecordViewVisibility(0);
        }
        this.mViewPager.setOnPageChangeListener(this);
        this.mViewPager.setCurrentItem(this.currentTab);
    }

    /* access modifiers changed from: private */
    public void selectMyTtsTab(boolean z) {
        if (this.mViewPager.getCurrentItem() != 1) {
            this.mViewPager.setCurrentItem(1, z);
        }
    }

    /* access modifiers changed from: private */
    public void selectSquareTtsTab(boolean z) {
        if (this.mViewPager.getCurrentItem() != 0) {
            this.mViewPager.setCurrentItem(0, z);
        }
    }

    /* access modifiers changed from: private */
    public void showFileIOExceptionDialog() {
        if (getValidActivity() != null) {
            DriveOfflineSDK.e().o();
            AlertView.a aVar = new AlertView.a(AMapAppGlobal.getApplication());
            aVar.a((CharSequence) getString(R.string.offline_navi_tts_sdcard_fail_dialog_title)).a((CharSequence) getString(R.string.retry), (a) new a() {
                public final void onClick(AlertView alertView, int i) {
                    OfflineNaviTtsFragment.this.dismissViewLayer(alertView);
                    if (FileUtil.getPathIsCanWrite(PathManager.a().b(DirType.DRIVE_VOICE))) {
                        OfflineNaviTtsFragment.this.checkNetAndSendHandlerMessage(null, 102);
                    } else {
                        OfflineNaviTtsFragment.this.showFileIOExceptionDialog();
                    }
                }
            }).b((CharSequence) getString(R.string.cancle), (a) new a() {
                public final void onClick(AlertView alertView, int i) {
                    OfflineNaviTtsFragment.this.finish();
                }
            });
            aVar.a(true);
            showViewLayer(aVar.a());
        }
    }

    private void showInitDownloadingDialog() {
        final Activity validActivity = getValidActivity();
        if (validActivity != null) {
            getActivity().runOnUiThread(new Runnable() {
                public final void run() {
                    if (OfflineNaviTtsFragment.this.isAlive()) {
                        OfflineNaviTtsFragment.this.progressDlg = new ProgressDlg(validActivity);
                        OfflineNaviTtsFragment.this.progressDlg.setMessage("正在加载...");
                        OfflineNaviTtsFragment.this.progressDlg.setOnCancelListener(new OnCancelListener() {
                            public final void onCancel(DialogInterface dialogInterface) {
                                OfflineNaviTtsFragment.this.ensureDialogDismiss();
                                DriveOfflineSDK.e();
                                DriveOfflineSDK.n();
                            }
                        });
                        OfflineNaviTtsFragment.this.progressDlg.show();
                        OfflineNaviTtsFragment.this.isShowInitDialog = true;
                    }
                }
            });
        }
    }

    private void showDeletingDialog() {
        ensureDialogDismiss();
        Activity validActivity = getValidActivity();
        if (validActivity != null) {
            this.progressDlg = new ProgressDlg(validActivity, "正在删除文件");
            this.progressDlg.show();
        }
    }

    /* access modifiers changed from: private */
    public void ensureDialogDismiss() {
        if (this.progressDlg != null) {
            this.progressDlg.dismiss();
            this.progressDlg = null;
        }
        this.isShowInitDialog = false;
    }

    /* access modifiers changed from: private */
    public void showUpdateTip() {
        DriveOfflineSDK.e();
        if (DriveOfflineSDK.r() && !sy.a().getBooleanValue(OfflinePreference.KEY_OFFLINE_TTS_GUIDE_TIP_SHOWN, false)) {
            Activity activity = getActivity();
            if (activity != null && !activity.isFinishing()) {
                AlertView.a aVar = new AlertView.a(AMapAppGlobal.getApplication());
                aVar.a((CharSequence) getString(R.string.offline_navi_tts_voice_guide_dialog_title)).b((CharSequence) getString(R.string.offline_navi_tts_voice_guide_dialog_msg)).a((CharSequence) getString(R.string.drive_taxi_compare_agree), (a) new a() {
                    public final void onClick(AlertView alertView, int i) {
                        OfflineNaviTtsFragment.this.dismissViewLayer(alertView);
                        OfflineNaviTtsFragment.this.refreshListView();
                    }
                });
                showViewLayer(aVar.a());
                DriveOfflineSDK.e();
                DriveOfflineSDK.a(false);
                sy.a().putBooleanValue(OfflinePreference.KEY_OFFLINE_TTS_GUIDE_TIP_SHOWN, true);
            }
        }
    }

    private Activity getValidActivity() {
        Activity activity = getActivity();
        if (activity == null || activity.isFinishing()) {
            return null;
        }
        return activity;
    }

    private void scanPPHelperDirectoryAndGetVoiceFile() {
        if (Environment.getExternalStorageDirectory() != null && Environment.getExternalStorageState().equals("mounted")) {
            StringBuilder sb = new StringBuilder();
            sb.append(Environment.getExternalStorageDirectory().getAbsolutePath());
            sb.append("/pp/PPGaode/download");
            File file = new File(sb.toString());
            if (file.isDirectory()) {
                DriveOfflineSDK.e();
                CopyOnWriteArrayList<dgl> p = DriveOfflineSDK.p();
                if (p != null && p.size() != 0) {
                    File[] listFiles = file.listFiles();
                    if (listFiles != null) {
                        for (File file2 : listFiles) {
                            String name = file2.getName();
                            Iterator<dgl> it = p.iterator();
                            while (it.hasNext()) {
                                dgl next = it.next();
                                if (next != null) {
                                    String a = dgl.a(next.a.d);
                                    if (a != null && a.length() >= 2 && name.equals(a.substring(1))) {
                                        copyFromPPtoAmap(file2.getPath(), next);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void copyFromPPtoAmap(String str, dgl dgl) {
        if (ahd.b(dgl.a()) && dgl.g() == 4) {
            ahd.a(new File(str));
        } else if (!dgl.a(str, dgl.a.i)) {
            ahd.a(new File(str));
        } else {
            if (dgl.g() == 1 || dgl.g() == 2) {
                dfy.a().a(dgl);
                dgl.a(0);
            }
            try {
                a aVar = new a(this, getActivity().getApplicationContext(), dgl, str, dgl.a.i);
                ahl.a(aVar);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void onReceivePPHelperDownloadFinishBroadcast(String str, String str2) {
        dgl a = dfs.a(str2);
        if (a != null && !TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2)) {
            if (ahd.b(a.a()) && a.g() == 4) {
                ahd.a(new File(str));
            } else if (!dgl.a(str, a.a.i)) {
                ahd.a(new File(str));
            } else {
                if (a.g() == 1 || a.g() == 2) {
                    dfy.a().a(a);
                    a.a(0);
                }
                try {
                    if (dfp.a(a)) {
                        a aVar = new a(this, getActivity().getApplicationContext(), a, str, str2);
                        ahl.a(aVar);
                        return;
                    }
                    ToastHelper.showToast(this.mContext.getApplicationContext().getString(R.string.offline_storage_noenough));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void showPPHelperApkDownloadDialog(final String str) {
        getActivity().runOnUiThread(new Runnable() {
            public final void run() {
                if (OfflineNaviTtsFragment.this.mDownloadPPApkDialog != null) {
                    OfflineNaviTtsFragment.this.mDownloadPPApkDialog.setVisibility(0);
                    TextView access$1400 = OfflineNaviTtsFragment.this.mDownloadPPApkDialogProgressTV;
                    StringBuilder sb = new StringBuilder();
                    sb.append(str);
                    sb.append("将进入高速下载");
                    access$1400.setText(sb.toString());
                }
            }
        });
    }

    public void updatePPHelperApkDownloadDialogProgress(final int i) {
        getActivity().runOnUiThread(new Runnable() {
            public final void run() {
                if (i >= 0 && i <= 100 && OfflineNaviTtsFragment.this.mDownloadPPApkDialogProgressTV != null) {
                    LayoutParams layoutParams = OfflineNaviTtsFragment.this.mDownloadPPApkProgressImg.getLayoutParams();
                    layoutParams.width = agn.a(OfflineNaviTtsFragment.this.mContext, (float) (i * 198)) / 100;
                    OfflineNaviTtsFragment.this.mDownloadPPApkProgressImg.setLayoutParams(layoutParams);
                }
            }
        });
    }

    public void closePPHelperApkDownloadDialog(final boolean z, final dgl dgl) {
        getActivity().runOnUiThread(new Runnable() {
            public final void run() {
                if (OfflineNaviTtsFragment.this.mDownloadPPApkDialog != null) {
                    OfflineNaviTtsFragment.this.mDownloadPPApkDialog.setVisibility(8);
                }
                if (z) {
                    ToastHelper.showLongToast("PP高速引擎启动失败，请稍后再试");
                } else if (!TextUtils.isEmpty(dfs.c())) {
                    File file = new File(dfs.c());
                    if (!file.exists() || !file.canRead() || file.length() <= 1024) {
                        ToastHelper.showLongToast("PP高速引擎启动失败，请稍后再试");
                    } else if (!OfflineNaviTtsFragment.this.mIsPPInstallReceiverRegister) {
                        OfflineNaviTtsFragment.this.mPPApkInstallReceiver = dfs.a(OfflineNaviTtsFragment.this, dfs.c(), dgl);
                    } else {
                        dfs.a(OfflineNaviTtsFragment.this, dfs.c(), dgl);
                        if (OfflineNaviTtsFragment.this.mPPApkInstallReceiver != null) {
                            PPApkInstallReceiver access$1600 = OfflineNaviTtsFragment.this.mPPApkInstallReceiver;
                            dgl dgl = dgl;
                            if (dgl != null) {
                                access$1600.a = dgl;
                            }
                        }
                    }
                }
                dfs.d();
            }
        });
    }

    public void uploadTTSNameLog(String str) {
        IVoicePackageManager iVoicePackageManager = (IVoicePackageManager) ank.a(IVoicePackageManager.class);
        if (iVoicePackageManager != null) {
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put(TrafficUtil.KEYWORD, iVoicePackageManager.getCurrentTtsName2());
                jSONObject.put("from", getCurrentTab() == 0 ? "语音广场页" : "我的语音页");
                if (!TextUtils.isEmpty(str)) {
                    jSONObject.put("type", str);
                }
                LogManager.actionLogV2("P00067", "B030", jSONObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:34:0x0080  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onConfigurationChanged(int r5) {
        /*
            r4 = this;
            r4.mCurrentOritation = r5
            com.autonavi.widget.ui.TitleBar r0 = r4.mTitleBar
            if (r0 == 0) goto L_0x0020
            r0 = 2
            if (r5 != r0) goto L_0x0019
            com.autonavi.minimap.drive.navi.navitts.widget.StickHeadScrollView r0 = r4.mStickHeadScrollView
            if (r0 == 0) goto L_0x0020
            com.autonavi.minimap.drive.navi.navitts.widget.StickHeadScrollView r0 = r4.mStickHeadScrollView
            boolean r0 = r0.isHiddenTop()
            if (r0 == 0) goto L_0x0020
            r4.setPageTitle()
            goto L_0x0020
        L_0x0019:
            com.autonavi.widget.ui.TitleBar r0 = r4.mTitleBar
            java.lang.String r1 = ""
            r0.setTitle(r1)
        L_0x0020:
            com.autonavi.minimap.drive.navi.navitts.widget.StickHeadScrollView r0 = r4.mStickHeadScrollView
            if (r0 == 0) goto L_0x002d
            com.autonavi.minimap.drive.navi.navitts.widget.StickHeadScrollView r0 = r4.mStickHeadScrollView
            android.widget.RadioGroup r1 = r4.mRadioGroup
            android.support.v4.view.ViewPager r2 = r4.mViewPager
            r0.resetHeight(r1, r2)
        L_0x002d:
            boolean r0 = r4.mAdapterFringeLandscapeScreen
            if (r0 != 0) goto L_0x0032
            return
        L_0x0032:
            android.app.Activity r0 = r4.getActivity()
            r1 = 1
            r2 = 0
            if (r0 == 0) goto L_0x005c
            android.app.Activity r0 = r4.getActivity()
            android.view.Window r0 = r0.getWindow()
            boolean r0 = defpackage.ts.a(r0)
            if (r0 == 0) goto L_0x005c
            if (r5 != r1) goto L_0x004f
            android.view.View r5 = r4.getContentView()
            goto L_0x0062
        L_0x004f:
            android.view.View r5 = r4.getContentView()
            int r0 = defpackage.ts.b()
            int r2 = defpackage.ts.b()
            goto L_0x007e
        L_0x005c:
            if (r5 != r1) goto L_0x0064
            android.view.View r5 = r4.getContentView()
        L_0x0062:
            r0 = 0
            goto L_0x007e
        L_0x0064:
            android.view.View r5 = r4.getContentView()
            boolean r0 = defpackage.ts.a()
            if (r0 == 0) goto L_0x0073
            int r0 = defpackage.ts.b()
            goto L_0x0074
        L_0x0073:
            r0 = 0
        L_0x0074:
            boolean r1 = defpackage.ts.a()
            if (r1 == 0) goto L_0x007e
            int r2 = defpackage.ts.b()
        L_0x007e:
            if (r5 == 0) goto L_0x008b
            int r1 = r5.getPaddingTop()
            int r3 = r5.getPaddingBottom()
            r5.setPadding(r0, r1, r2, r3)
        L_0x008b:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.drive.navi.navitts.fragment.OfflineNaviTtsFragment.onConfigurationChanged(int):void");
    }

    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        if (i != R.id.voice_square && i == R.id.voice_my) {
            LogManager.actionLogV2("P00067", "B010", 0, 0);
        }
    }

    public void onStateChanged(int i) {
        if (this.mTitleBar != null) {
            if (this.mCurrentOritation != 2 || i == 1) {
                this.mTitleBar.setTitle("");
            } else if (i == 2) {
                this.mTitleBar.setTitle("导航语音包");
            }
        }
    }

    private void setPageTitle() {
        if (this.mTitleBar != null) {
            this.mTitleBar.setTitle("导航语音包");
        }
    }

    /* access modifiers changed from: private */
    public void checkStorage() {
        if (!FileUtil.getPathIsCanWrite(PathManager.a().a(DirType.DRIVE_VOICE))) {
            if (!TextUtils.isEmpty(FileUtil.canWritePath(this.mContext))) {
                dfp.a((a) new a() {
                    public final void a() {
                        OfflineNaviTtsFragment.this.refreshListView();
                    }
                });
                return;
            }
            ToastHelper.showToast(AMapPageUtil.getAppContext().getString(R.string.offline_data_unavalable_for_voice));
        }
    }

    private void startVoiceListFragment(dgl dgl) {
        String str = dgl.a.c;
        File file = new File(dgu.a(str));
        PageBundle pageBundle = new PageBundle();
        pageBundle.putString("bundle_key_voice_package_name", str);
        pageBundle.putObject("bundle_key_voice_package_obj", file);
        pageBundle.putInt("bundle_key_work_mode", 1);
        startPageForResult(NavigationVoiceListFragment.class, pageBundle, 3);
    }
}
