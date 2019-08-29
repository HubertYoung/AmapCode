package com.autonavi.minimap.ajx3;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import com.alipay.mobile.common.share.widget.ResUtils;
import com.alipay.mobile.monitor.track.spm.merge.MergeUtil;
import com.amap.bundle.jsadapter.JsAdapter;
import com.amap.bundle.network.util.NetworkReachability;
import com.amap.bundle.voiceservice.dispatch.IVoiceCmdResponder;
import com.autonavi.common.Callback;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.map.core.LocationMode.LocationGpsAndNetwork;
import com.autonavi.map.core.LocationMode.LocationGpsOnly;
import com.autonavi.map.core.LocationMode.LocationNetworkOnly;
import com.autonavi.map.core.LocationMode.LocationNone;
import com.autonavi.map.core.MapManager;
import com.autonavi.map.fragmentcontainer.page.AbstractBaseMapPage;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.minimap.R;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.loading.DefaultListLoading;
import com.autonavi.minimap.ajx3.loading.LoadingConfig;
import com.autonavi.minimap.ajx3.modules.ModuleAMap;
import com.autonavi.minimap.ajx3.modules.ModuleHistory;
import com.autonavi.minimap.ajx3.modules.ModuleJsBridge;
import com.autonavi.minimap.ajx3.modules.ModuleSchemeLoader;
import com.autonavi.minimap.ajx3.platform.ackor.AjxFileInfo;
import com.autonavi.minimap.ajx3.upgrade.Ajx3UpgradeManager;
import com.autonavi.minimap.ajx3.util.AjxPathList;
import com.autonavi.minimap.ajx3.views.AmapAjxView;
import com.autonavi.minimap.ajx3.views.AmapAjxView.AjxLifeCircleListener;
import com.autonavi.minimap.ajx3.views.AmapAjxView.AjxViewLayerListener;
import com.autonavi.minimap.ajx3.views.AmapAjxView.BackCallback;
import com.autonavi.minimap.ajx3.views.DefaultAjxViewSizeProvider;
import com.autonavi.minimap.map.overlayholder.AjxOverlayPage;
import com.autonavi.minimap.map.overlayholder.OverlayPage.OvProperty;
import com.autonavi.minimap.map.overlayholder.OverlayPage.OverlayPageProperty;
import com.autonavi.minimap.map.overlayholder.OverlayPage.UvOverlay;
import com.autonavi.widget.ui.TitleBar;
import com.autonavi.widget.ui.TitleBar.a;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import org.json.JSONException;
import org.json.JSONObject;

@OverlayPageProperty(overlays = {@OvProperty(overlay = UvOverlay.GpsOverlay, visible = false)})
public class Ajx3Page extends AbstractBaseMapPage<Ajx3PagePresenter> implements IVoiceCmdResponder, Ajx3PageInterface, AjxLifeCircleListener, AjxViewLayerListener, AjxOverlayPage {
    public static final String LOADING_JS_URL = "path://amap_bundle_dynamic_ui/src/WebLoader/pages/WebLoader.page.js";
    public static final String PAGE_DATA = "jsData";
    public static final String PAGE_ENV = "env";
    public static final String PAGE_ID = "pageId";
    public static final String PAGE_LOADING_TYPE = "loadingType";
    public static final int PAGE_LOADING_TYPE_DEFAULT = 0;
    public static final int PAGE_LOADING_TYPE_LIST = 1;
    public static final String PAGE_RESULT_EXECUTOR = "resultExecutor";
    public static final String PAGE_SCHEME_RESULT = "schemeResult";
    public static final String PAGE_START_TIME = "startTime";
    public static final String PAGE_URL = "url";
    public static final String TAG = "Ajx3Page";
    protected AjxPageStateInvoker ajxPageStateInvoker;
    /* access modifiers changed from: private */
    public HashMap<String, Ajx3ViewLayer> layers = new HashMap<>();
    LoadingConfig loadingConfig;
    private Ajx3MapSuspendHelper mAjx3MapSuspendHelper;
    private AjxPageResultExecutor mAjxPageResultExecutor;
    public AmapAjxView mAjxView;
    private FrameLayout mBottomLayout;
    private String mBundleInfo = "";
    private String mBundleInfoUrl = "";
    private String mBundleVersion = "";
    private Object mData = null;
    private String mEnv = null;
    private boolean mHasStarted = false;
    /* access modifiers changed from: private */
    public View mLoadingLayout;
    private int mLoadingType = 0;
    private int mLocationMode;
    private boolean mNeedLoading = true;
    private String mPageId;
    private boolean mRecoveryMapState;
    private Callback mSchemeResult = null;
    private boolean mShowGpsCenter;
    private boolean mShowMap;
    protected MapStateParam mStoreMapStateParam;
    private FrameLayout mTopLayout;
    protected String mUrl = null;

    public interface AjxPageResultExecutor {
        void onFragmentResult(AbstractBasePage abstractBasePage, int i, ResultType resultType, PageBundle pageBundle, JsAdapter jsAdapter);
    }

    public static class MapStateParam {
        public float cameraDegree;
        /* access modifiers changed from: private */
        public int mCacheMapMode = 0;
        /* access modifiers changed from: private */
        public int mMapViewModeState = 0;
        public int mMapViewTime = 0;
        public boolean mTrafficLightStyle = false;
        public boolean mTrafficState = false;
        public float mapAngle;
        public GeoPoint mapCenter = new GeoPoint();
        public float zoomLevel;

        protected MapStateParam() {
        }
    }

    public void engineActionGesture(alg alg) {
    }

    /* access modifiers changed from: protected */
    public boolean handleSetContentView() {
        return false;
    }

    /* access modifiers changed from: protected */
    public boolean isFullScreenMode() {
        return true;
    }

    public boolean isSetSoftInput() {
        return false;
    }

    public void mapAnimationFinished(aln aln) {
    }

    /* access modifiers changed from: protected */
    public void onAjxViewCreated(AmapAjxView amapAjxView) {
    }

    /* access modifiers changed from: protected */
    public void onContentViewCreated(View view) {
    }

    public void onPageAppear() {
    }

    public void onPageCover() {
    }

    /* access modifiers changed from: protected */
    public void pageCreated() {
    }

    /* access modifiers changed from: protected */
    public final void pageCreated(int i, int i2) {
    }

    /* access modifiers changed from: protected */
    public void pause() {
    }

    public void onAjxContxtCreated(IAjxContext iAjxContext) {
        if (this.mShowMap) {
            ModuleAMap moduleAMap = (ModuleAMap) this.mAjxView.getJsModule(ModuleAMap.MODULE_NAME);
            if (!(moduleAMap == null || this.mAjx3MapSuspendHelper == null)) {
                this.mAjx3MapSuspendHelper.setAjxContext(iAjxContext);
                moduleAMap.setAMapSuspendView(this.mAjx3MapSuspendHelper);
            }
        }
        if (this.mSchemeResult != null) {
            ModuleSchemeLoader moduleSchemeLoader = (ModuleSchemeLoader) this.mAjxView.getJsModule(ModuleSchemeLoader.MODULE_NAME);
            if (moduleSchemeLoader != null) {
                moduleSchemeLoader.setResultCallback(this.mSchemeResult);
            }
        }
    }

    public void onJsBack(Object obj, String str) {
        PageBundle pageBundle = new PageBundle();
        pageBundle.putObject(ModuleHistory.AJX_BACK_RETURN_DATA_KEY, obj);
        try {
            pageBundle.putObject("data", new JSONObject(obj.toString()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!TextUtils.isEmpty(str)) {
            ajw ajw = (ajw) ank.a(ajw.class);
            if (!(Uri.parse(str).getScheme() != null)) {
                ArrayList arrayList = new ArrayList();
                StringBuilder sb = new StringBuilder("amapuri://ajx?pageid=");
                sb.append(Uri.encode(str));
                arrayList.add(sb.toString());
                ajw.a(arrayList, this, ResultType.OK, pageBundle);
                return;
            }
            ArrayList arrayList2 = new ArrayList();
            arrayList2.add(str);
            ajw.a(arrayList2, this, ResultType.OK, pageBundle);
            return;
        }
        setResult(ResultType.OK, pageBundle);
        finish();
    }

    public String getUniversalOverlayConfig() {
        return this.loadingConfig != null ? this.loadingConfig.overlayStatus : "";
    }

    /* access modifiers changed from: protected */
    public Ajx3PagePresenter createPresenter() {
        return new Ajx3PagePresenter(this);
    }

    public void onCreate(Context context) {
        super.onCreate(context);
        requestScreenOrientation(1);
        handlePageBundle();
        View prepareView = prepareView();
        if (!handleSetContentView()) {
            setContentView(prepareView);
        } else {
            onContentViewCreated(prepareView);
        }
        syncPageContext();
    }

    /* access modifiers changed from: protected */
    public void newIntent(PageBundle pageBundle) {
        if (pageBundle != null && this.mAjxView != null) {
            this.mAjxView.onNewIntent(pageBundle.getObject("jsData"));
        }
    }

    private void fillResumeData(Object obj) {
        if (this.ajxPageStateInvoker != null) {
            this.ajxPageStateInvoker.setResumeData(obj);
        }
    }

    public View getMapSuspendView() {
        if (this.mAjx3MapSuspendHelper == null) {
            return null;
        }
        this.mAjx3MapSuspendHelper.init();
        return this.mAjx3MapSuspendHelper.getSuspendView();
    }

    /* access modifiers changed from: protected */
    public View onCreateView(AmapAjxView amapAjxView) {
        if (!this.mShowMap) {
            return amapAjxView;
        }
        this.mAjx3MapSuspendHelper = new Ajx3MapSuspendHelper(this);
        return createMapInteractiveLayout(amapAjxView);
    }

    public AmapAjxView getAjxView() {
        return this.mAjxView;
    }

    private void handlePageBundle() {
        PageBundle arguments = getArguments();
        this.mBundleVersion = "";
        if (arguments != null) {
            String string = arguments.getString("url");
            if (!TextUtils.isEmpty(string)) {
                this.mUrl = string;
                if (this.mUrl.contains("&")) {
                    String[] split = this.mUrl.split("&");
                    if (split.length == 2 && !TextUtils.isEmpty(split[0]) && !TextUtils.isEmpty(split[1]) && split[1].startsWith("__bv__=")) {
                        this.mUrl = split[0];
                        this.mBundleVersion = split[1].substring(7);
                    }
                }
                this.mUrl = AjxPathList.transform(this.mUrl);
            }
            this.mAjxPageResultExecutor = (AjxPageResultExecutor) arguments.get("resultExecutor");
            this.mData = arguments.getObject("jsData");
            this.mPageId = arguments.getString("pageId");
            this.mEnv = arguments.getString("env");
            this.mLoadingType = arguments.getInt(PAGE_LOADING_TYPE, 0);
            Object object = arguments.getObject(PAGE_SCHEME_RESULT);
            if (object instanceof Callback) {
                this.mSchemeResult = (Callback) object;
            }
        }
    }

    private View prepareView() {
        this.mAjxView = new AmapAjxView(getContext());
        this.mAjxView.attachPage(this);
        this.mAjxView.setAjxLifeCircleListener(this);
        this.mAjxView.setAjxViewLayerListener(this);
        this.ajxPageStateInvoker = new AjxPageStateInvoker(this, this.mAjxView);
        onAjxViewCreated(this.mAjxView);
        String ajx3Url = getAjx3Url();
        this.loadingConfig = Ajx3Path.getLoadingConfig(getContext(), ajx3Url);
        this.mAjxView.setSPM(ajx3Url, null);
        if (this.loadingConfig != null) {
            this.mAjxView.setSPM(ajx3Url, this.loadingConfig.spm);
            this.mLocationMode = this.loadingConfig.locationMode;
            this.mRecoveryMapState = this.loadingConfig.recoveryMapState;
            this.mShowGpsCenter = this.loadingConfig.showGpsCenter;
            final TitleBar creatTitleBar = Ajx3Path.creatTitleBar(getContext(), this.loadingConfig);
            if (creatTitleBar != null) {
                final RelativeLayout relativeLayout = new RelativeLayout(getContext());
                LayoutParams layoutParams = new LayoutParams(-2, -2);
                layoutParams.addRule(10);
                creatTitleBar.setLayoutParams(layoutParams);
                creatTitleBar.setId(R.id.title);
                creatTitleBar.setOnBackClickListener((a) new a() {
                    public void onClick(TitleBar titleBar, int i) {
                        if (!Ajx3Page.this.backPressed()) {
                            Ajx3Page.this.finish();
                        }
                    }
                });
                relativeLayout.addView(creatTitleBar);
                if (this.mLoadingType == 1) {
                    this.mLoadingLayout = new DefaultListLoading(getContext());
                } else if (this.loadingConfig.hasLogo) {
                    this.mLoadingLayout = getLayoutInflater().inflate(R.layout.page_ajx3_loading, null);
                }
                if (this.mLoadingLayout != null) {
                    LayoutParams layoutParams2 = new LayoutParams(-1, -1);
                    layoutParams2.addRule(3, R.id.title);
                    layoutParams2.addRule(13);
                    this.mLoadingLayout.setLayoutParams(layoutParams2);
                    relativeLayout.addView(this.mLoadingLayout);
                }
                LayoutParams layoutParams3 = new LayoutParams(-1, -1);
                layoutParams3.addRule(10);
                this.mAjxView.setLayoutParams(layoutParams3);
                relativeLayout.addView(this.mAjxView);
                if (this.loadingConfig.showMap) {
                    this.mShowMap = true;
                } else if (!TextUtils.isEmpty(this.loadingConfig.bgcolor)) {
                    relativeLayout.setBackgroundColor(Color.parseColor(this.loadingConfig.bgcolor));
                    float f = (float) this.loadingConfig.bgOpacity;
                    if (f >= 0.0f && f <= 1.0f) {
                        relativeLayout.setAlpha(f);
                    }
                } else {
                    relativeLayout.setBackgroundColor(-1);
                }
                this.mAjxView.setLoadingCallback(new Callback<AmapAjxView>() {
                    public void error(Throwable th, boolean z) {
                    }

                    public void callback(AmapAjxView amapAjxView) {
                        if (relativeLayout != null) {
                            if (Ajx3Page.this.mLoadingLayout != null) {
                                relativeLayout.removeView(Ajx3Page.this.mLoadingLayout);
                            }
                            if (creatTitleBar != null) {
                                relativeLayout.removeView(creatTitleBar);
                            }
                        }
                    }
                });
                if (!this.mShowMap) {
                    return relativeLayout;
                }
                this.mAjx3MapSuspendHelper = new Ajx3MapSuspendHelper(this);
                return createMapInteractiveLayout(relativeLayout);
            } else if (this.loadingConfig.showMap) {
                this.mShowMap = true;
                return onCreateView(this.mAjxView);
            } else {
                if (!TextUtils.isEmpty(this.loadingConfig.bgcolor)) {
                    this.mAjxView.setBackgroundColor(Color.parseColor(this.loadingConfig.bgcolor));
                    float f2 = (float) this.loadingConfig.bgOpacity;
                    if (f2 >= 0.0f && f2 <= 1.0f) {
                        this.mAjxView.setAlpha(f2);
                    }
                } else {
                    this.mAjxView.setBackgroundColor(-1);
                }
                return onCreateView(this.mAjxView);
            }
        } else {
            this.mAjxView.setBackgroundColor(-1);
            return onCreateView(this.mAjxView);
        }
    }

    public boolean isShowMap() {
        return this.mShowMap;
    }

    public boolean addViewToTop(View view, FrameLayout.LayoutParams layoutParams) {
        if (this.mTopLayout == null) {
            return false;
        }
        this.mTopLayout.addView(view, layoutParams);
        return true;
    }

    public boolean addViewToBottom(View view, FrameLayout.LayoutParams layoutParams) {
        if (this.mBottomLayout == null) {
            return false;
        }
        this.mBottomLayout.addView(view, layoutParams);
        return true;
    }

    /* access modifiers changed from: protected */
    public View createMapInteractiveLayout(View view) {
        ViewGroup viewGroup = (ViewGroup) getLayoutInflater().inflate(R.layout.page_ajx3_mappage, null, false);
        viewGroup.addView(view, new ViewGroup.LayoutParams(-1, -1));
        this.mTopLayout = (FrameLayout) viewGroup.findViewById(R.id.mapTopInteractiveView);
        this.mBottomLayout = (FrameLayout) viewGroup.findViewById(R.id.mapBottomInteractiveView);
        return viewGroup;
    }

    /* access modifiers changed from: protected */
    public boolean needLoading(String str) {
        if (AjxFileInfo.initMode == AjxFileInfo.FILE_INIT_MODE) {
            return false;
        }
        boolean checkUrlExist = this.mAjxView.checkUrlExist(str);
        if (checkUrlExist && !TextUtils.isEmpty(this.mBundleVersion)) {
            String bundleName = AjxFileInfo.getBundleName(str);
            String loadedDiffAjxFileVersion = AjxFileInfo.getLoadedDiffAjxFileVersion(bundleName);
            if (TextUtils.isEmpty(loadedDiffAjxFileVersion)) {
                loadedDiffAjxFileVersion = AjxFileInfo.getBaseAjxFileVersion(bundleName);
            }
            if (!this.mBundleVersion.equals(loadedDiffAjxFileVersion) && NetworkReachability.a(getContext()) != null) {
                checkUrlExist = false;
            }
        }
        if (checkUrlExist) {
            return Ajx3UpgradeManager.getInstance().needCheckUpdate(AjxFileInfo.getBundleName(str));
        } else if (!checkUrlExist) {
            return true;
        } else {
            return false;
        }
    }

    /* access modifiers changed from: protected */
    public void loadLoadingJS(@NonNull String str, @Nullable Object obj, String str2, String str3, int i, int i2, String str4) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("__webloader_bizcheck_finish__", true);
            jSONObject.put("__webloader_bizrealpagedata__", obj);
            jSONObject.put("__webloader_bizrealpageid__", str2);
        } catch (JSONException unused) {
        }
        JSONObject jSONObject2 = new JSONObject();
        try {
            jSONObject2.put("__webloader_bizpath__", str);
            jSONObject2.put("__webloader_bizpagedata__", jSONObject);
        } catch (JSONException unused2) {
        }
        this.mAjxView.loadDirectly(LOADING_JS_URL, jSONObject2.toString(), "web_loading_ajx", str3, i, i2, str4, -1);
    }

    /* access modifiers changed from: protected */
    public void loadJs() {
        if (isFullScreenMode()) {
            int[] fullScreenSize = getFullScreenSize(getActivity());
            loadJs(fullScreenSize[0], fullScreenSize[1]);
            return;
        }
        loadJs(0, 0);
    }

    /* access modifiers changed from: protected */
    public void loadJs(int i, int i2) {
        String ajx3Url = getAjx3Url();
        if (this.mData instanceof String) {
            try {
                JSONObject jSONObject = new JSONObject((String) this.mData);
                if (jSONObject.has("__webloader_bizcheck_finish__")) {
                    if (jSONObject.has("__webloader_bizrealpagedata__")) {
                        this.mData = jSONObject.get("__webloader_bizrealpagedata__");
                    } else {
                        this.mData = "";
                    }
                    this.mPageId = jSONObject.optString("__webloader_bizrealpageid__", "");
                    this.mAjxView.loadDirectly(ajx3Url, this.mData, this.mPageId, getClass().getSimpleName(), i, i2, this.mEnv, -1);
                    return;
                }
            } catch (Exception unused) {
            }
        } else if (this.mData instanceof JSONObject) {
            JSONObject jSONObject2 = (JSONObject) this.mData;
            if (jSONObject2.has("__webloader_bizcheck_finish__")) {
                try {
                    if (jSONObject2.has("__webloader_bizrealpagedata__")) {
                        this.mData = jSONObject2.get("__webloader_bizrealpagedata__");
                    } else {
                        this.mData = "";
                    }
                    this.mPageId = jSONObject2.optString("__webloader_bizrealpageid__", "");
                } catch (Exception unused2) {
                }
                this.mAjxView.loadDirectly(ajx3Url, this.mData, this.mPageId, getClass().getSimpleName(), i, i2, this.mEnv, -1);
                return;
            }
        }
        if (needLoading(ajx3Url)) {
            loadLoadingJS(ajx3Url, this.mData, this.mPageId, getClass().getSimpleName(), i, i2, this.mEnv);
            return;
        }
        this.mAjxView.loadDirectly(ajx3Url, this.mData, this.mPageId, getClass().getSimpleName(), i, i2, this.mEnv, -1);
    }

    /* access modifiers changed from: protected */
    public void resume() {
        if (this.ajxPageStateInvoker != null) {
            this.ajxPageStateInvoker.onResume();
        }
        changeLocationMode();
        syncPageContext();
        bmp.a((String) "bundleInfo", getPageBundleInfo());
    }

    private void changeLocationMode() {
        if (but.d != null) {
            switch (this.mLocationMode) {
                case 1:
                    but.d.a(new LocationNone() {
                    });
                    return;
                case 2:
                    but.d.a(new LocationGpsOnly() {
                    });
                    return;
                case 3:
                    but.d.a(new LocationNetworkOnly() {
                    });
                    return;
                case 4:
                    but.d.a(new LocationGpsAndNetwork() {
                    });
                    break;
            }
        }
    }

    public void windowFocusChanged(boolean z) {
        if (this.ajxPageStateInvoker != null) {
            this.ajxPageStateInvoker.windowFocusChanged(z);
        }
    }

    public void start() {
        if (!this.mHasStarted) {
            if (this.mRecoveryMapState) {
                saveMapState();
            }
            loadJs();
            this.mHasStarted = true;
            bmp.a((String) "bundleInfo", getPageBundleInfo());
        }
        if (this.loadingConfig == null || TextUtils.isEmpty(this.loadingConfig.overlayStatus)) {
            if (this.mShowGpsCenter) {
                getMapManager().getOverlayManager().setGPSVisible(true);
                return;
            }
            getMapManager().getOverlayManager().setGPSVisible(false);
        }
    }

    /* access modifiers changed from: protected */
    public void stop() {
        if (this.ajxPageStateInvoker != null) {
            this.ajxPageStateInvoker.onStop();
        }
    }

    /* access modifiers changed from: protected */
    public void destroy() {
        if (this.layers != null && this.layers.size() > 0) {
            for (Entry<String, Ajx3ViewLayer> value : this.layers.entrySet()) {
                ((Ajx3ViewLayer) value.getValue()).destroy();
            }
            this.layers.clear();
        }
        if (this.mAjxView != null) {
            this.mAjxView.onDestroy();
            this.mAjxView.setAjxLifeCircleListener(null);
            this.mAjxView = null;
        }
        if (this.mAjxPageResultExecutor != null) {
            this.mAjxPageResultExecutor = null;
        }
        if (this.ajxPageStateInvoker != null) {
            this.ajxPageStateInvoker.onDestroy();
        }
        if (this.mRecoveryMapState) {
            recoveryMapState();
        }
    }

    public static int[] getFullScreenSize(Activity activity) {
        int i;
        int i2;
        if (activity == null) {
            return new int[]{0, 0};
        }
        int[] iArr = new int[2];
        try {
            i2 = activity.getWindow().getDecorView().getWidth();
            try {
                i = activity.getWindow().getDecorView().getHeight();
            } catch (Exception unused) {
                i = 0;
                DisplayMetrics displayMetrics = activity.getResources().getDisplayMetrics();
                int i3 = displayMetrics.widthPixels;
                i = displayMetrics.heightPixels - getStatusBarHeight(activity);
                i2 = i3;
                iArr[0] = i2;
                iArr[1] = i - getBottomNavigationBarHeight(activity);
                return iArr;
            }
        } catch (Exception unused2) {
            i2 = 0;
            i = 0;
            DisplayMetrics displayMetrics2 = activity.getResources().getDisplayMetrics();
            int i32 = displayMetrics2.widthPixels;
            i = displayMetrics2.heightPixels - getStatusBarHeight(activity);
            i2 = i32;
            iArr[0] = i2;
            iArr[1] = i - getBottomNavigationBarHeight(activity);
            return iArr;
        }
        if (i2 == 0 || i == 0) {
            DisplayMetrics displayMetrics22 = activity.getResources().getDisplayMetrics();
            int i322 = displayMetrics22.widthPixels;
            i = displayMetrics22.heightPixels - getStatusBarHeight(activity);
            i2 = i322;
        }
        iArr[0] = i2;
        iArr[1] = i - getBottomNavigationBarHeight(activity);
        return iArr;
    }

    private static int getBottomNavigationBarHeight(Activity activity) {
        if (euj.a(activity)) {
            return euj.b(activity);
        }
        return 0;
    }

    private void syncPageContext() {
        Context context = getContext();
        DefaultAjxViewSizeProvider.getInstance(context.getApplicationContext()).updatePageContext(context);
    }

    private static int getStatusBarHeight(Context context) {
        int i = 0;
        if (euk.a()) {
            return 0;
        }
        int identifier = context.getResources().getIdentifier("status_bar_height", ResUtils.DIMEN, "android");
        if (identifier > 0) {
            i = context.getResources().getDimensionPixelSize(identifier);
        }
        return i;
    }

    /* access modifiers changed from: protected */
    public void result(int i, ResultType resultType, PageBundle pageBundle) {
        Object obj;
        new StringBuilder("result method start ").append(this.ajxPageStateInvoker.getResumeData());
        if (pageBundle == null || resultType != ResultType.OK) {
            obj = null;
        } else {
            obj = pageBundle.get(ModuleHistory.AJX_BACK_RETURN_DATA_KEY);
            new StringBuilder("get mResumeData ").append(obj);
        }
        fillResumeData(obj);
        if (this.mAjxPageResultExecutor != null && this.mAjxView.getAjxContext() != null) {
            this.mAjxPageResultExecutor.onFragmentResult(this, i, resultType, pageBundle, ((ModuleJsBridge) this.mAjxView.getJsModule("js")).getJsMethod());
        }
    }

    /* access modifiers changed from: protected */
    public boolean backPressed() {
        if (this.mAjxView != null) {
            return this.mAjxView.backPressed();
        }
        return false;
    }

    @Nullable
    public String getAjx3Url() {
        if (this.mUrl != null) {
            return this.mUrl;
        }
        if (this.mAjxView != null) {
            return this.mAjxView.getUrl();
        }
        return null;
    }

    public String getAjxPageId() {
        if (this.mAjxView != null) {
            return this.mAjxView.getPageId();
        }
        return null;
    }

    public String getBundleInfo() {
        return getPageBundleInfo();
    }

    private String getPageBundleInfo() {
        if (TextUtils.isEmpty(this.mBundleInfoUrl) || !this.mBundleInfoUrl.equals(getAjx3Url())) {
            JSONObject jSONObject = new JSONObject();
            this.mBundleInfoUrl = getAjx3Url();
            String bundleName = AjxFileInfo.getBundleName(this.mBundleInfoUrl);
            String ajxEngineVersion = Ajx.getInstance().getAjxEngineVersion();
            try {
                jSONObject.put(com.alipay.sdk.authjs.a.d, bundleName);
                jSONObject.put("pagePath", this.mBundleInfoUrl);
                jSONObject.put("baseBundleVersion", AjxFileInfo.getBaseAjxFileVersion(bundleName));
                jSONObject.put("patchBundleVersion", AjxFileInfo.getLoadedDiffAjxFileVersion(bundleName));
                jSONObject.put("type", "ajx");
                jSONObject.put("engineVersion", ajxEngineVersion);
            } catch (JSONException unused) {
            }
            this.mBundleInfo = jSONObject.toString();
        }
        return this.mBundleInfo;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(super.toString());
        sb.append(MergeUtil.SEPARATOR_KV);
        sb.append(getAjx3Url());
        return sb.toString();
    }

    public long getScene() {
        ModuleJsBridge moduleJsBridge = (ModuleJsBridge) this.mAjxView.getJsModule("js");
        if (moduleJsBridge != null) {
            String jsData = moduleJsBridge.getJsData("voiceCmd");
            if (!TextUtils.isEmpty(jsData)) {
                try {
                    return Long.parseLong(jsData);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return Ajx3Path.FEED_PATH.equals(getAjx3Url()) ? 268435456 : 0;
    }

    private void saveMapState() {
        this.mStoreMapStateParam = new MapStateParam();
        cde suspendManager = getSuspendManager();
        MapManager mapManager = getMapManager();
        if (suspendManager != null && mapManager != null && mapManager.getMapView() != null) {
            bty mapView = mapManager.getMapView();
            bty mapView2 = getMapView();
            mapView.e(true);
            mapView.d(true);
            this.mStoreMapStateParam.mapAngle = mapView.I();
            this.mStoreMapStateParam.zoomLevel = mapView.v();
            this.mStoreMapStateParam.cameraDegree = mapView.J();
            this.mStoreMapStateParam.mapCenter = GeoPoint.glGeoPoint2GeoPoint(mapView.n());
            this.mStoreMapStateParam.mTrafficState = mapView.s();
            this.mStoreMapStateParam.mTrafficLightStyle = mapView.r();
            this.mStoreMapStateParam.mMapViewTime = mapView.l(true);
            this.mStoreMapStateParam.mCacheMapMode = mapView2.j(true);
            this.mStoreMapStateParam.mMapViewModeState = mapView2.k(true);
        }
    }

    /* access modifiers changed from: protected */
    public void recoveryMapState() {
        if (getMapManager() != null && getMapView() != null) {
            bty mapView = getMapManager().getMapView();
            bty mapView2 = getMapView();
            mapView.ab();
            mapView.B();
            mapView.z();
            mapView.N();
            mapView2.a(this.mStoreMapStateParam.mCacheMapMode, this.mStoreMapStateParam.mMapViewTime, this.mStoreMapStateParam.mMapViewModeState);
            if (!(this.mStoreMapStateParam.mapCenter == null || this.mStoreMapStateParam.mapCenter.x == 0 || this.mStoreMapStateParam.mapCenter.y == 0)) {
                mapView.a(this.mStoreMapStateParam.mapCenter.x, this.mStoreMapStateParam.mapCenter.y);
                brj.a(this.mStoreMapStateParam.mapCenter.x, this.mStoreMapStateParam.mapCenter.y);
            }
            mapView.a(this.mStoreMapStateParam.mTrafficLightStyle);
            mapView.e(this.mStoreMapStateParam.mapAngle);
            mapView.d(this.mStoreMapStateParam.zoomLevel);
            mapView.g(this.mStoreMapStateParam.cameraDegree);
        }
    }

    public void onAddLayer(String str, final String str2, Object obj) {
        Ajx3ViewLayer remove = this.layers.remove(str2);
        if (remove != null) {
            remove.destroy();
            dismissViewLayer(remove);
        }
        Ajx3ViewLayer ajx3ViewLayer = new Ajx3ViewLayer(getContext(), str, str2, obj);
        ajx3ViewLayer.getView().setBackCallBack(new BackCallback() {
            public void back(Object obj, String str) {
                Ajx3ViewLayer ajx3ViewLayer = (Ajx3ViewLayer) Ajx3Page.this.layers.remove(str2);
                if (ajx3ViewLayer != null) {
                    ajx3ViewLayer.destroy();
                    Ajx3Page.this.dismissViewLayer(ajx3ViewLayer);
                }
            }
        });
        showViewLayer(ajx3ViewLayer);
        this.layers.put(str2, ajx3ViewLayer);
    }

    public void onRemoveLayer(String str) {
        Ajx3ViewLayer remove = this.layers.remove(str);
        if (remove != null) {
            remove.destroy();
            dismissViewLayer(remove);
        }
    }

    public String getLaunchMode() {
        if (this.loadingConfig != null) {
            return this.loadingConfig.launchMode;
        }
        return null;
    }
}
