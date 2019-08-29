package com.alipay.mobile.h5container.api;

import android.os.Bundle;
import android.text.TextUtils;
import com.alipay.mobile.beehive.capture.utils.AudioUtils;
import com.alipay.mobile.common.transportext.biz.shared.ExtTransportStrategy;
import com.alipay.mobile.monitor.track.spm.merge.MergeUtil;
import com.alipay.mobile.nebula.appcenter.model.H5Refer;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5UrlHelper;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.amap.location.common.model.AmapLoc;
import com.uc.webview.export.extension.UCCore;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class H5PageData implements Cloneable {
    public static final String BRIDGE_READY = "bridgeReady";
    public static final int CHECK_CALL_BACK_HANDLE_RESPONSE = 2;
    public static final int CHECK_CALL_BACK_PAGE_START = 0;
    public static final int CHECK_CALL_BACK_RESOURCE_FINISH_LOAD = 3;
    public static final int CHECK_CALL_BACK_SHOULD_INTERCEPT_REQUEST = 1;
    private static final String[] CREATE_SCENARIO_ARRAY = {"init_uc_unzip", "init_uc", "init", AmapLoc.TYPE_NEW, "push", UCCore.OPTION_LOAD_KERNEL_TYPE};
    public static final int CREATE_SCENARIO_INIT = 2;
    public static final int CREATE_SCENARIO_INIT_UC = 1;
    public static final int CREATE_SCENARIO_INIT_UC_UNZIP = 0;
    public static final int CREATE_SCENARIO_LOAD = 5;
    public static final int CREATE_SCENARIO_NEW = 3;
    public static final int CREATE_SCENARIO_PUSH = 4;
    public static final int DEFAULT_ERROR_CODE = 7000;
    public static final String FROM_TYPE_HERF_CHANGE = "hrefChange";
    public static final String FROM_TYPE_PUSH_WINDOW = "pushWindow";
    public static final String FROM_TYPE_START_APP = "startApp";
    public static final String FROM_TYPE_SUB_VIEW = "subView";
    public static final String IS_ENTRANCE = "isEntrance";
    public static final String IS_H5ACTIVITY = "isH5Activity";
    public static final String IS_OFFLINE_APP = "isOfflineApp";
    public static final String JS_ERRORS = "jsErrors";
    public static final String KEY_UC_START = "start";
    public static final String KEY_UC_T0 = "t0";
    public static final String KEY_UC_T1 = "t1";
    public static final String KEY_UC_T2 = "t2";
    public static final String KEY_UC_T2_PAINT = "t2Paint";
    public static final String KEY_UC_T2_TRACE = "t2Trace";
    public static final String KEY_UC_T3 = "t3";
    private static final int MAX_LOG_LENGTH = 10240;
    public static final String TAG = "H5PageData";
    public static final String WEBVIEW_ERROR_CODE = "webViewErrorCode";
    public static final String WEBVIEW_ERROR_DESC = "webViewErrorDesc";
    public static long createAppTime;
    public static long sAppDownloadDelaySeconds = 60;
    public static int sCreateScene = -1;
    public static long sCreateTimestamp = -1;
    public static boolean sUcFirstWebView = false;
    public static boolean sUseWebViewPool = false;
    public static long swFirstJsApiCallTime;
    public static String ucCacheSdcardLimit;
    public static long walletServiceStart;
    private int abTestUsedTime;
    private int aliRequestResNum;
    private String eagleId;
    private String errorMsg;
    private int errorSpdyCode;
    private String errorSpdyMsg;
    private boolean isSpdy = false;
    private String jsApiDetail;
    private String jsApiPerExtra;
    private int mAboutBlankNum;
    private String mAppId;
    private String mAppVersion;
    private long mAppear;
    private long mAppearFromNative;
    private int[] mCheckFuncStatusList = {0, 0, 0, 0};
    private long mComplete;
    private long mContainerVisible;
    private long mCreate;
    private int mCreateScenario = -1;
    private int mCssLoadNum;
    private long mCssLoadSize;
    private int mCssReqNum;
    private long mCssSize;
    private String mCustomParams;
    private long mEnd;
    private int mErrorCode;
    private String mEtype;
    private final Map<String, Object> mExtraMaps = new HashMap();
    private long mFirstByte;
    private long mFirstVisuallyRender;
    private String mFromType;
    private int mH1RequestCount;
    private int mH2RequestCount;
    private String mH5SessionToken;
    private String mH5Token;
    private long mHtmlLoadSize;
    private long mHtmlSize;
    private String mImageSizeLimit60Urls;
    private int mImgLoadNum;
    private long mImgLoadSize;
    private int mImgReqNum;
    private long mImgSize;
    private String mIsLocal = "NO";
    private String mIsTinyApp;
    private int mJsLoadNum;
    private long mJsLoadSize;
    private int mJsReqNum;
    private long mJsSize;
    private Map<String, H5JsCallData> mJsapiList = new ConcurrentHashMap();
    private long mLastResponseTimestamp;
    private long mLottieLoadingAnimEnd;
    private long mLottieLoadingAnimStart;
    private int mMultiProcessMode;
    private String mNavUrl;
    private long mNavigationStart;
    private Map<String, String> mNetRequestInfo = new ConcurrentHashMap();
    private int mNum1000;
    private int mNum300;
    private int mNum302;
    private int mNum304;
    private int mNum400;
    private int mNum404;
    private int mNum500;
    private String mOpenAppId;
    private int mOtherLoadNum;
    private long mOtherLoadSize;
    private int mOtherReqNum;
    private long mOtherSize;
    private int mPageIndex;
    private long mPageLoad;
    private long mPageLoadSize;
    private long mPageNetLoad;
    private long mPageSize;
    private String mPageToken;
    private String mPageUrl;
    private String mProtocol;
    private String mProxy;
    private String mPublicId;
    private String mReferUrl;
    private String mReferer;
    private String mReleaseType;
    private int mRequestLoadNum;
    private int mRequestNum;
    private String mResPkgInfo;
    private long mServerResponse;
    private String mSessionId;
    private String mShopId;
    private int mSizeLimit200;
    private String mSizeLimit200Urls;
    private int mSizeLimit60;
    private int mSizeLoadLimit200;
    private int mSizeLoadLimit60;
    private int mSpdyRequestCount;
    private long mSrcClick;
    private long mStart;
    private int mStatusCode;
    private String mStype;
    private String mTitle;
    private String mToken;
    private String mUCCorePerfExtra = "";
    private boolean mUcFistWebView = false;
    private LinkedList<String> mUrlList = new LinkedList<>();
    private boolean mUsePushWindowAnim = false;
    private String mVisible;
    private int mWebViewIndex;
    private String mWebViewType;
    private String multimediaID;
    private int netErrorJsNum;
    private int netErrorOtherNum;
    private int netJsReqNum;
    private long netJsSize;
    private long netJsTime;
    private int netOtherReqNum;
    private long netOtherSize;
    private long netOtherTime;
    private boolean preConnectRequest = false;
    private int preRender;
    private int reloadType;
    private String requestId;
    private boolean showErrorPage = false;
    private String spmId;
    private int ucCacheResNum;
    private boolean usePreRequest = false;
    private boolean useWebViewPool = false;
    private HashSet<String> warningTipSet = new HashSet<>();
    private String xContentVersion = "";

    public HashSet<String> getWarningTipSet() {
        return this.warningTipSet;
    }

    public H5PageData() {
        clear();
        this.useWebViewPool = sUseWebViewPool;
        sUseWebViewPool = false;
    }

    public H5PageData clone() {
        return (H5PageData) super.clone();
    }

    public void clear() {
        H5Log.d(TAG, "clear");
        this.mIsLocal = "NO";
        this.mExtraMaps.clear();
        this.mSizeLoadLimit200 = 0;
        this.mSizeLimit200 = 0;
        this.mSizeLoadLimit60 = 0;
        this.mSizeLimit60 = 0;
        this.mSizeLimit200Urls = "";
        this.mImageSizeLimit60Urls = "";
        this.mRequestNum = 0;
        this.mOtherReqNum = 0;
        this.mImgReqNum = 0;
        this.mJsReqNum = 0;
        this.mCssReqNum = 0;
        this.mPageIndex = 0;
        this.mOtherSize = 0;
        this.mImgSize = 0;
        this.mJsSize = 0;
        this.mHtmlSize = 0;
        this.mCssSize = 0;
        this.mTitle = "";
        this.mEtype = "";
        this.mStype = "";
        this.mRequestLoadNum = 0;
        this.mOtherLoadNum = 0;
        this.mImgLoadNum = 0;
        this.mCssLoadNum = 0;
        this.mJsLoadNum = 0;
        this.mNum1000 = 0;
        this.mNum500 = 0;
        this.mNum400 = 0;
        this.mNum404 = 0;
        this.mNum300 = 0;
        this.mNum304 = 0;
        this.mNum302 = 0;
        this.mErrorCode = 7000;
        this.mAppearFromNative = 0;
        this.mEnd = 0;
        this.mComplete = 0;
        this.mAppear = 0;
        this.mStart = 0;
        this.mFirstByte = 0;
        this.mPageSize = 0;
        this.mHtmlLoadSize = 0;
        this.mCssLoadSize = 0;
        this.mJsLoadSize = 0;
        this.mImgLoadSize = 0;
        this.mOtherLoadSize = 0;
        this.mPageLoadSize = 0;
        this.mVisible = "N";
        this.mProxy = "N";
        this.mAboutBlankNum = 0;
        this.mFirstVisuallyRender = 0;
        this.mSpdyRequestCount = 0;
        this.mH1RequestCount = 0;
        this.mH2RequestCount = 0;
        this.mPageLoad = 0;
        this.mContainerVisible = 0;
        this.mSrcClick = 0;
        this.mFromType = "";
        this.mH5SessionToken = "";
        this.mH5Token = "";
        this.mToken = "";
        this.mNavigationStart = 0;
        this.mResPkgInfo = "";
        this.netErrorOtherNum = 0;
        this.netErrorJsNum = 0;
        this.netOtherReqNum = 0;
        this.netJsReqNum = 0;
        this.netOtherTime = 0;
        this.netJsTime = 0;
        this.netOtherSize = 0;
        this.netJsSize = 0;
        this.xContentVersion = "";
        this.requestId = "";
        this.eagleId = "";
        this.multimediaID = "";
        this.jsApiDetail = "";
        this.errorSpdyCode = 0;
        this.errorSpdyMsg = "";
        this.spmId = "";
        this.aliRequestResNum = 0;
        this.ucCacheResNum = 0;
        this.jsApiPerExtra = "";
        this.warningTipSet.clear();
    }

    public boolean isValid() {
        return this.mStart != 0;
    }

    public void onPageStarted(String url) {
        H5Log.d(TAG, "onPageStarted: " + url);
        this.mStart = System.currentTimeMillis();
        this.mPageUrl = url;
        this.mStype = getStartType(url);
    }

    public void resetPageToken() {
        this.mPageToken = UUID.randomUUID().toString();
    }

    public void onPageEnded(String eType) {
        H5Log.d(TAG, "onPageEnded: " + eType);
        this.mEnd = System.currentTimeMillis() - this.mStart;
        if (TextUtils.isEmpty(eType)) {
            this.mEtype = getEndType();
        } else {
            this.mEtype = eType;
        }
        if (this.mAppear > 0) {
            this.mVisible = "Y";
        } else {
            this.mVisible = "N";
        }
    }

    private String getStartType(String url) {
        String type;
        if (this.mUrlList.isEmpty()) {
            type = "open";
            this.mUrlList.addLast(url);
            H5Log.d(TAG, "open : url = " + url);
            this.mReferUrl = H5Refer.referUrl;
        } else if (url.equals(this.mNavUrl)) {
            type = "nav";
            String lastUrl = this.mUrlList.getLast();
            H5Log.d(TAG, "nav : lastUrl" + lastUrl);
            this.mReferUrl = lastUrl;
            this.mUrlList.addLast(url);
        } else {
            type = "resume";
            String lastUrl2 = this.mUrlList.removeLast();
            H5Log.d(TAG, "resume : lastUrl" + lastUrl2);
            this.mReferUrl = lastUrl2;
        }
        H5Log.d(TAG, "getStartType : " + type);
        return type;
    }

    private String getEndType() {
        if (!TextUtils.isEmpty(this.mNavUrl)) {
            return "nav";
        }
        return AudioUtils.CMDPAUSE;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("index=" + this.mPageIndex);
        sb.append("^error=" + this.mStatusCode);
        sb.append("^visible=Y");
        sb.append("^start=" + this.mStart);
        sb.append("^appear=" + this.mAppear);
        sb.append("^complete=" + this.mComplete);
        sb.append("^end=" + this.mEnd);
        sb.append("^stype=" + this.mStype);
        sb.append("^etype=" + this.mEtype);
        sb.append("^proxy=" + this.mProxy);
        sb.append("^title=" + this.mTitle);
        sb.append("^referer=" + this.mReferer);
        sb.append("^xurl=null");
        sb.append("^pageSize=" + this.mPageSize);
        return sb.toString();
    }

    public String getPageInfo() {
        StringBuilder builder = new StringBuilder();
        builder.append("appId=").append(this.mAppId).append("^version=").append(this.mAppVersion).append("^publicId=").append(this.mPublicId).append("^url=").append(this.mPageUrl).append("^viewId=").append(this.mPageUrl).append("^refviewId=").append(this.mReferUrl);
        return builder.toString();
    }

    public long getStart() {
        return this.mStart;
    }

    public void setStart(long start) {
        H5Log.d(TAG, "setStart: " + start);
        this.mStart = start;
    }

    public long getAppear() {
        return this.mAppear;
    }

    public void setAppear(long appear) {
        H5Log.d(TAG, "setAppear: " + appear);
        this.mAppear = appear;
    }

    public long getAppearFromNative() {
        return this.mAppearFromNative;
    }

    public void setAppearFromNative(long appearFromNative) {
        H5Log.d(TAG, "setAppearFromNative: " + appearFromNative);
        this.mAppearFromNative = appearFromNative;
    }

    public long getFirstByte() {
        return this.mFirstByte;
    }

    public void setFirstByte(long firstByte) {
        H5Log.d(TAG, "setFirstByte: " + firstByte);
        this.mFirstByte = firstByte;
    }

    public long getComplete() {
        return this.mComplete;
    }

    public void setComplete(long complete) {
        H5Log.d(TAG, "complete: " + complete);
        this.mComplete = complete;
    }

    public long getEnd() {
        return this.mEnd;
    }

    public void setEnd(long end) {
        H5Log.d(TAG, "setEnd: " + end);
        this.mEnd = end;
    }

    public long getPageNetLoad() {
        return this.mPageNetLoad;
    }

    public void setPageNetLoad(long pageNetLoad) {
        H5Log.d(TAG, "setPageNetLoad: " + pageNetLoad);
        this.mPageNetLoad = pageNetLoad;
    }

    public long getServerResponse() {
        return this.mServerResponse;
    }

    public void setServerResponse(long serverResponse) {
        H5Log.d(TAG, "setServerResponse: " + serverResponse);
        this.mServerResponse = serverResponse;
    }

    public long getCreate() {
        return this.mCreate;
    }

    public String getCreateScenario() {
        H5Log.d(TAG, "getCreateScenario: " + this.mCreateScenario);
        String scenario = "";
        if (this.mCreateScenario < 0) {
            return scenario;
        }
        try {
            return CREATE_SCENARIO_ARRAY[this.mCreateScenario];
        } catch (Throwable throwable) {
            H5Log.w(TAG, "getCreateScenario", throwable);
            return scenario;
        }
    }

    public void setCreateScenario(int createScenario) {
        H5Log.d(TAG, "setCreateScenario : " + createScenario);
        this.mCreateScenario = createScenario;
    }

    public static void setInitScenario(long create, int scenario) {
        H5Log.d(TAG, "setInitScenario: " + create + ", scenario: " + scenario);
        if (sCreateScene < 0 || sCreateScene >= scenario) {
            sCreateScene = scenario;
            sCreateTimestamp = create;
            return;
        }
        H5Log.d(TAG, "setInitScenario fail, sCreateScene: " + sCreateScene + ", sCreateTimestamp: " + sCreateTimestamp);
    }

    public void setCreate(long create, int scenario) {
        H5Log.d(TAG, "setCreate: " + create + ", scenario: " + scenario);
        if (sCreateScene >= 0) {
            H5Log.d(TAG, "use sCreateScene: " + sCreateScene + ", sCreateTimestamp: " + sCreateTimestamp);
            scenario = sCreateScene;
            create = sCreateTimestamp;
            sCreateScene = -1;
            sCreateTimestamp = -1;
        }
        if (this.mCreateScenario < 0 || this.mCreateScenario >= scenario) {
            this.mCreate = create;
            this.mCreateScenario = scenario;
            return;
        }
        H5Log.d(TAG, "setCreate fail, mCreate: " + this.mCreate + ", mCreateScenario: " + this.mCreateScenario);
    }

    public int getPageIndex() {
        return this.mPageIndex;
    }

    public void setPageIndex(int pageIndex) {
        H5Log.d(TAG, "setPageIndex: " + pageIndex);
        this.mPageIndex = pageIndex;
    }

    public int getWebViewIndex() {
        return this.mWebViewIndex;
    }

    public void setWebViewIndex(int webViewIndex) {
        H5Log.d(TAG, "setWebViewIndex: " + webViewIndex);
        this.mWebViewIndex = webViewIndex;
    }

    public int getStatusCode() {
        return this.mStatusCode;
    }

    public void setStatusCode(int statusCode) {
        H5Log.d(TAG, "setStatusCode: " + statusCode);
        this.mStatusCode = statusCode;
    }

    public int getErrorCode() {
        return this.mErrorCode;
    }

    public void setErrorCode(int errorCode) {
        H5Log.d(TAG, "setErrorCode: " + errorCode);
        this.mErrorCode = errorCode;
    }

    public String getPageUrl() {
        return this.mPageUrl;
    }

    public void setPageUrl(String pageUrl) {
        H5Log.d(TAG, "setPageUrl: " + pageUrl);
        this.mPageUrl = pageUrl;
    }

    public String getReferer() {
        return this.mReferer;
    }

    public void setReferer(String referer) {
        H5Log.d(TAG, "setReferer: " + referer);
        this.mReferer = referer;
    }

    public String getVisible() {
        return this.mVisible;
    }

    public void setVisible(String visible) {
        H5Log.d(TAG, "setVisible: " + visible);
        this.mVisible = visible;
    }

    public String getReferUrl() {
        return this.mReferUrl;
    }

    public void setReferUrl(String referUrl) {
        H5Log.d(TAG, "setReferUrl: " + referUrl);
        this.mReferUrl = referUrl;
    }

    public String getStype() {
        return this.mStype;
    }

    public void setStype(String stype) {
        H5Log.d(TAG, "setStype: " + stype);
        this.mStype = stype;
    }

    public String getEtype() {
        return this.mEtype;
    }

    public void setEtype(String etype) {
        H5Log.d(TAG, "setEtype: " + etype);
        this.mEtype = etype;
    }

    public String getProxy() {
        return this.mProxy;
    }

    public void setProxy(String proxy) {
        H5Log.d(TAG, "setProxy: " + proxy);
        this.mProxy = proxy;
    }

    public String getTitle() {
        return this.mTitle;
    }

    public void setTitle(String title) {
        H5Log.d(TAG, "setTitle: " + title);
        this.mTitle = title;
    }

    public long getPageLoadSize() {
        return this.mPageLoadSize;
    }

    public void setPageLoadSize(long pageLoadSize) {
        H5Log.d(TAG, "setPageLoadSize: " + pageLoadSize);
        this.mPageLoadSize = pageLoadSize;
    }

    public long getOtherLoadSize() {
        return this.mOtherLoadSize;
    }

    public void setOtherLoadSize(long otherLoadSize) {
        H5Log.d(TAG, "setOtherLoadSize: " + otherLoadSize);
        this.mOtherLoadSize = otherLoadSize;
    }

    public long getImgLoadSize() {
        return this.mImgLoadSize;
    }

    public void setImgLoadSize(long imgLoadSize) {
        H5Log.d(TAG, "setImgLoadSize: " + imgLoadSize);
        this.mImgLoadSize = imgLoadSize;
    }

    public long getJsLoadSize() {
        return this.mJsLoadSize;
    }

    public void setJsLoadSize(long jsLoadSize) {
        H5Log.d(TAG, "setJsLoadSize: " + jsLoadSize);
        this.mJsLoadSize = jsLoadSize;
    }

    public long getCssLoadSize() {
        return this.mCssLoadSize;
    }

    public void setCssLoadSize(long cssLoadSize) {
        H5Log.d(TAG, "setCssLoadSize: " + cssLoadSize);
        this.mCssLoadSize = cssLoadSize;
    }

    public long getHtmlLoadSize() {
        return this.mHtmlLoadSize;
    }

    public void setHtmlLoadSize(long htmlLoadSize) {
        H5Log.d(TAG, "setHtmlLoadSize: " + htmlLoadSize);
        this.mHtmlLoadSize = htmlLoadSize;
    }

    public int getCssReqNum() {
        return this.mCssReqNum;
    }

    public void setCssReqNum(int cssReqNum) {
        H5Log.d(TAG, "setCssReqNum: " + cssReqNum);
        this.mCssReqNum = cssReqNum;
    }

    public int getJsReqNum() {
        return this.mJsReqNum;
    }

    public void setJsReqNum(int jsReqNum) {
        H5Log.d(TAG, "setJsReqNum: " + jsReqNum);
        this.mJsReqNum = jsReqNum;
    }

    public int getImgReqNum() {
        return this.mImgReqNum;
    }

    public void setImgReqNum(int imgReqNum) {
        H5Log.d(TAG, "setImgReqNum: " + imgReqNum);
        this.mImgReqNum = imgReqNum;
    }

    public int getOtherReqNum() {
        return this.mOtherReqNum;
    }

    public void setOtherReqNum(int otherReqNum) {
        H5Log.d(TAG, "setOtherReqNum: " + otherReqNum);
        this.mOtherReqNum = otherReqNum;
    }

    public int getRequestNum() {
        return this.mRequestNum;
    }

    public void setRequestNum(int requestNum) {
        H5Log.d(TAG, "setRequestNum: " + requestNum);
        this.mRequestNum = requestNum;
    }

    public long getJsSize() {
        return this.mJsSize;
    }

    public void setJsSize(long jsSize) {
        H5Log.d(TAG, "setJsSize: " + jsSize);
        this.mJsSize = jsSize;
    }

    public long getCssSize() {
        return this.mCssSize;
    }

    public void setCssSize(long cssSize) {
        H5Log.d(TAG, "setCssSize: " + cssSize);
        this.mCssSize = cssSize;
    }

    public long getImgSize() {
        return this.mImgSize;
    }

    public void setImgSize(long imgSize) {
        H5Log.d(TAG, "setImgSize: " + imgSize);
        this.mImgSize = imgSize;
    }

    public long getOtherSize() {
        return this.mOtherSize;
    }

    public void setOtherSize(long otherSize) {
        H5Log.d(TAG, "setOtherSize: " + otherSize);
        this.mOtherSize = otherSize;
    }

    public long getHtmlSize() {
        return this.mHtmlSize;
    }

    public void setHtmlSize(long htmlSize) {
        H5Log.d(TAG, "setHtmlSize: " + htmlSize);
        this.mHtmlSize = htmlSize;
    }

    public long getPageSize() {
        return this.mPageSize;
    }

    public void setPageSize(long pageSize) {
        H5Log.d(TAG, "setPageSize: " + pageSize);
        this.mPageSize = pageSize;
    }

    public int getJsLoadNum() {
        return this.mJsLoadNum;
    }

    public void setJsLoadNum(int jsLoadNum) {
        H5Log.d(TAG, "setJsLoadNum: " + jsLoadNum);
        this.mJsLoadNum = jsLoadNum;
    }

    public int getCssLoadNum() {
        return this.mCssLoadNum;
    }

    public void setCssLoadNum(int cssLoadNum) {
        H5Log.d(TAG, "setCssLoadNum: " + cssLoadNum);
        this.mCssLoadNum = cssLoadNum;
    }

    public int getImgLoadNum() {
        return this.mImgLoadNum;
    }

    public void setImgLoadNum(int imgLoadNum) {
        H5Log.d(TAG, "setImgLoadNum: " + imgLoadNum);
        this.mImgLoadNum = imgLoadNum;
    }

    public int getOtherLoadNum() {
        return this.mOtherLoadNum;
    }

    public void setOtherLoadNum(int otherLoadNum) {
        H5Log.d(TAG, "setOtherLoadNum: " + otherLoadNum);
        this.mOtherLoadNum = otherLoadNum;
    }

    public int getRequestLoadNum() {
        return this.mRequestLoadNum;
    }

    public void setRequestLoadNum(int requestLoadNum) {
        H5Log.d(TAG, "setRequestLoadNum: " + requestLoadNum);
        this.mRequestLoadNum = requestLoadNum;
    }

    public int getSizeLimit60() {
        return this.mSizeLimit60;
    }

    public void setSizeLimit60(int sizeLimit60) {
        H5Log.d(TAG, "setSizeLimit60: " + sizeLimit60);
        this.mSizeLimit60 = sizeLimit60;
    }

    public int getSizeLoadLimit60() {
        return this.mSizeLoadLimit60;
    }

    public void setSizeLoadLimit60(int sizeLoadLimit60) {
        H5Log.d(TAG, "setSizeLoadLimit60: " + sizeLoadLimit60);
        this.mSizeLoadLimit60 = sizeLoadLimit60;
    }

    public int getSizeLimit200() {
        return this.mSizeLimit200;
    }

    public void setSizeLimit200(int sizeLimit200) {
        H5Log.d(TAG, "setSizeLimit200: " + sizeLimit200);
        this.mSizeLimit200 = sizeLimit200;
    }

    public int getSizeLoadLimit200() {
        return this.mSizeLoadLimit200;
    }

    public void setSizeLoadLimit200(int sizeLoadLimit200) {
        H5Log.d(TAG, "setSizeLoadLimit200: " + sizeLoadLimit200);
        this.mSizeLoadLimit200 = sizeLoadLimit200;
    }

    public String getImageSizeLimit60Urls() {
        return this.mImageSizeLimit60Urls;
    }

    public void setImageSizeLimit60Urls(String imageSizeLimit60Urls) {
        H5Log.d(TAG, "setImageSizeLimit60Urls: " + imageSizeLimit60Urls);
        this.mImageSizeLimit60Urls = imageSizeLimit60Urls;
    }

    public String getSizeLimit200Urls() {
        return this.mSizeLimit200Urls;
    }

    public void setSizeLimit200Urls(String sizeLimit200Urls) {
        H5Log.d(TAG, "setSizeLimit200Urls: " + sizeLimit200Urls);
        this.mSizeLimit200Urls = sizeLimit200Urls;
    }

    public int getNum302() {
        return this.mNum302;
    }

    public void setNum302(int num302) {
        H5Log.d(TAG, "setNum302: " + num302);
        this.mNum302 = num302;
    }

    public int getNum304() {
        return this.mNum304;
    }

    public void setNum304(int num304) {
        H5Log.d(TAG, "setNum304: " + num304);
        this.mNum304 = num304;
    }

    public int getNum300() {
        return this.mNum300;
    }

    public void setNum300(int num300) {
        H5Log.d(TAG, "setNum300: " + num300);
        this.mNum300 = num300;
    }

    public int getNum404() {
        return this.mNum404;
    }

    public void setNum404(int num404) {
        H5Log.d(TAG, "setNum404: " + num404);
        this.mNum404 = num404;
    }

    public int getNum400() {
        return this.mNum400;
    }

    public void setNum400(int num400) {
        H5Log.d(TAG, "setNum400: " + num400);
        this.mNum400 = num400;
    }

    public int getNum500() {
        return this.mNum500;
    }

    public void setNum500(int num500) {
        H5Log.d(TAG, "setNum500: " + num500);
        this.mNum500 = num500;
    }

    public int getNum1000() {
        return this.mNum1000;
    }

    public void setNum1000(int num1000) {
        H5Log.d(TAG, "setNum1000: " + num1000);
        this.mNum1000 = num1000;
    }

    public String getNavUrl() {
        return this.mNavUrl;
    }

    public void setNavUrl(String navUrl) {
        H5Log.d(TAG, "setNavUrl: " + navUrl);
        this.mNavUrl = navUrl;
    }

    public String getAppId() {
        return this.mAppId;
    }

    public void setAppId(String appId) {
        H5Log.d(TAG, "setAppId: " + appId);
        this.mAppId = appId;
    }

    public String getPublicId() {
        return this.mPublicId;
    }

    public void setPublicId(String publicId) {
        H5Log.d(TAG, "setPublicId: " + publicId);
        this.mPublicId = publicId;
    }

    public String getAppVersion() {
        return this.mAppVersion;
    }

    public void setAppVersion(String appVersion) {
        H5Log.d(TAG, "setAppVersion: " + appVersion);
        this.mAppVersion = appVersion;
    }

    public LinkedList<String> getUrlList() {
        return this.mUrlList;
    }

    public void setUrlList(LinkedList<String> urlList) {
        H5Log.d(TAG, "setUrlList");
        this.mUrlList = urlList;
    }

    public String getCustomParams() {
        return this.mCustomParams;
    }

    public void setCustomParams(String customParams) {
        H5Log.d(TAG, "setCustomParams: " + customParams);
        this.mCustomParams = customParams;
    }

    public int getAboutBlankNum() {
        return this.mAboutBlankNum;
    }

    public void setAboutBlankNum(int aboutBlankNum) {
        H5Log.d(TAG, "setAboutBlankNum: " + aboutBlankNum);
        this.mAboutBlankNum = aboutBlankNum;
    }

    public String getWebViewType() {
        return this.mWebViewType;
    }

    public void setWebViewType(String webViewType) {
        H5Log.d(TAG, "setWebViewType: " + webViewType);
        this.mWebViewType = webViewType;
    }

    public String getPageToken() {
        return this.mPageToken;
    }

    public void setPageToken(String pageToken) {
        H5Log.d(TAG, "setPageToken: " + pageToken);
        this.mPageToken = pageToken;
    }

    public String getIsLocal() {
        return this.mIsLocal;
    }

    public void setIsLocal(String isLocal) {
        H5Log.d(TAG, "setIsLocal: " + isLocal);
        this.mIsLocal = isLocal;
    }

    public String getReleaseType() {
        return this.mReleaseType;
    }

    public void setReleaseType(String releaseType) {
        H5Log.d(TAG, "setReleaseType: " + releaseType);
        this.mReleaseType = releaseType;
    }

    public String getIsTinyApp() {
        return this.mIsTinyApp;
    }

    public void setIsTinyApp(String isTinyApp) {
        H5Log.d(TAG, "setIsTinyApp: " + isTinyApp);
        this.mIsTinyApp = isTinyApp;
    }

    public String getOpenAppId() {
        return this.mOpenAppId;
    }

    public void setOpenAppId(String openAppId) {
        H5Log.d(TAG, "setOpenAppId: " + openAppId);
        this.mOpenAppId = openAppId;
    }

    public String getShopId() {
        return this.mShopId;
    }

    public void setShopId(String shopId) {
        H5Log.d(TAG, "setShopId: " + shopId);
        this.mShopId = shopId;
    }

    public int getAbTestUsedTime() {
        return this.abTestUsedTime;
    }

    public void setAbTestUsedTime(int abTestUsedTime2) {
        this.abTestUsedTime = abTestUsedTime2;
    }

    public String getProtocol() {
        return this.mProtocol;
    }

    public void setProtocol(String protocol) {
        H5Log.d(TAG, "setProtocol: " + protocol);
        this.mProtocol = protocol;
    }

    public void setFirstVisuallyRender(long time) {
        if (this.mFirstVisuallyRender <= 0) {
            H5Log.d(TAG, "onFirstVisuallyRender: " + time);
            this.mFirstVisuallyRender = time;
        }
    }

    public long getFirstVisuallyRender() {
        return this.mFirstVisuallyRender;
    }

    public int getRequestCountByProtocal(String protocal) {
        if (protocal.contains(ExtTransportStrategy.EXT_PROTO_SPDY)) {
            return this.mSpdyRequestCount;
        }
        if (protocal.contains("h2")) {
            return this.mH2RequestCount;
        }
        return this.mH1RequestCount;
    }

    public void updateRequestCountByProtocal(String protocal) {
        if (getComplete() <= 0 || getPageLoad() <= 0) {
            if (protocal.contains(ExtTransportStrategy.EXT_PROTO_SPDY)) {
                this.mSpdyRequestCount++;
            } else if (protocal.contains("h2")) {
                this.mH2RequestCount++;
            } else {
                this.mH1RequestCount++;
            }
            H5Log.d(TAG, "h2count: " + this.mH2RequestCount + " spdycount: " + this.mSpdyRequestCount + "h1count: " + this.mH1RequestCount);
        }
    }

    public void setPageLoad(long pageLoad) {
        H5Log.d(TAG, "setPageLoad " + pageLoad);
        this.mPageLoad = pageLoad;
    }

    public long getPageLoad() {
        return this.mPageLoad;
    }

    public long getSrcClick() {
        return this.mSrcClick;
    }

    public void setSrcClick(long mSrcClick2) {
        H5Log.d(TAG, "setSrcClick: " + mSrcClick2);
        this.mSrcClick = mSrcClick2;
    }

    public long getContainerVisible() {
        return this.mContainerVisible;
    }

    public void setContainerVisible(long mContainerVisible2) {
        H5Log.d(TAG, "setContainerVisible: " + this.mSrcClick);
        this.mContainerVisible = mContainerVisible2;
    }

    public String getFromType() {
        return this.mFromType;
    }

    public void setFromType(String mFromType2) {
        H5Log.d(TAG, "setFromType: " + mFromType2);
        this.mFromType = mFromType2;
    }

    public int getPreRender() {
        return this.preRender;
    }

    public void setPreRender(int preRender2) {
        H5Log.d(TAG, "setPreRender: " + this.preRender);
        this.preRender = preRender2;
    }

    public void setSessionId(String sessionId) {
        H5Log.d(TAG, "setSessionId " + sessionId);
        this.mSessionId = sessionId;
    }

    public String getSessionId() {
        return this.mSessionId;
    }

    public String getToken() {
        return this.mToken;
    }

    public void setToken(String mToken2) {
        H5Log.d(TAG, "setToken " + mToken2);
        this.mToken = mToken2;
    }

    public String getH5Token() {
        return this.mH5Token;
    }

    public void setH5Token(String mH5Token2) {
        H5Log.d(TAG, "setH5Token " + mH5Token2);
        this.mH5Token = mH5Token2;
    }

    public String getH5SessionToken() {
        return this.mH5SessionToken;
    }

    public void setH5SessionToken(String mH5SessionToken2) {
        H5Log.d(TAG, "setH5SessionToken " + mH5SessionToken2);
        this.mH5SessionToken = mH5SessionToken2;
    }

    public void addJsapiInfo(String eventId, H5JsCallData data) {
        H5Log.d(TAG, "addJsapiInfo " + data.getAction() + Token.SEPARATOR + this.mAppId + Token.SEPARATOR + this.mTitle);
        this.mJsapiList.put(eventId, data);
    }

    public H5JsCallData getJsapiInfo(String eventId) {
        return this.mJsapiList.get(eventId);
    }

    public Map<String, H5JsCallData> getJsapiInfoList() {
        H5Log.d(TAG, "getJsapiInfoList: " + this.mAppId);
        return this.mJsapiList;
    }

    public void addNetRequestInfo(String url, String netInfo) {
        H5Log.d(TAG, "addNetRequestInfo: " + url + ", info : " + netInfo);
        this.mNetRequestInfo.put(url, netInfo);
    }

    public Map<String, String> getNetRequestInfo() {
        return this.mNetRequestInfo;
    }

    public long getNavigationStart() {
        return this.mNavigationStart;
    }

    public void setNavigationStart(long navigationStart) {
        H5Log.d(TAG, "setNavigationStart : " + navigationStart);
        this.mNavigationStart = navigationStart;
    }

    public String getResPkgInfo() {
        return this.mResPkgInfo;
    }

    public void setResPkgInfo(String mResPkgInfo2) {
        H5Log.d(TAG, "setResPkgInfo : " + mResPkgInfo2);
        this.mResPkgInfo = mResPkgInfo2;
    }

    public int getNetJsReqNum() {
        return this.netJsReqNum;
    }

    public void setNetJsReqNum(int netJsReqNum2) {
        H5Log.d(TAG, "setNetJsReqNum : " + netJsReqNum2);
        this.netJsReqNum = netJsReqNum2;
    }

    public int getNetOtherReqNum() {
        return this.netOtherReqNum;
    }

    public void setNetOtherReqNum(int netOtherReqNum2) {
        H5Log.d(TAG, "setNetOtherReqNum : " + netOtherReqNum2);
        this.netOtherReqNum = netOtherReqNum2;
    }

    public int getNetErrorJsNum() {
        return this.netErrorJsNum;
    }

    public void setNetErrorJsNum(int netErrorJsNum2) {
        H5Log.d(TAG, "setNetErrorJsNum : " + netErrorJsNum2);
        this.netErrorJsNum = netErrorJsNum2;
    }

    public int getNetErrorOtherNum() {
        return this.netErrorOtherNum;
    }

    public void setNetErrorOtherNum(int netErrorOtherNum2) {
        H5Log.d(TAG, "setNetErrorOtherNum : " + netErrorOtherNum2);
        this.netErrorOtherNum = netErrorOtherNum2;
    }

    public long getNetJsSize() {
        return this.netJsSize;
    }

    public void setNetJsSize(long netJsSize2) {
        H5Log.d(TAG, "setNetJsSize : " + netJsSize2);
        this.netJsSize = netJsSize2;
    }

    public long getNetOtherSize() {
        return this.netOtherSize;
    }

    public void setNetOtherSize(long netOtherSize2) {
        H5Log.d(TAG, "setNetOtherSize : " + netOtherSize2);
        this.netOtherSize = netOtherSize2;
    }

    public long getNetJsTime() {
        return this.netJsTime;
    }

    public void setNetJsTime(long netJsTime2) {
        H5Log.d(TAG, "setNetJsTime : " + netJsTime2);
        this.netJsTime = netJsTime2;
    }

    public long getNetOtherTime() {
        return this.netOtherTime;
    }

    public void setNetOtherTime(long netOtherTime2) {
        H5Log.d(TAG, "setNetOtherTime : " + netOtherTime2);
        this.netOtherTime = netOtherTime2;
    }

    public String getXContentVersion() {
        return this.xContentVersion;
    }

    public void setXContentVersion(String xContentVersion2) {
        H5Log.d(TAG, "setXContentVersion : " + xContentVersion2);
        this.xContentVersion = xContentVersion2;
    }

    public String getEagleId() {
        return this.eagleId;
    }

    public void setEagleId(String eagleId2) {
        H5Log.d(TAG, "setEagleId : " + eagleId2);
        this.eagleId = eagleId2;
    }

    public String getRequestId() {
        return this.requestId;
    }

    public void setRequestId(String requestId2) {
        H5Log.d(TAG, "setRequestId : " + requestId2);
        this.requestId = requestId2;
    }

    public boolean isUsePreRequest() {
        return this.usePreRequest;
    }

    public void setUsePreRequest(boolean usePreRequest2) {
        H5Log.d(TAG, "setUsePreRequest : " + usePreRequest2);
        this.usePreRequest = usePreRequest2;
    }

    public boolean isPreConnectRequest() {
        return this.preConnectRequest;
    }

    public void setPreConnectRequest(boolean preConnect) {
        H5Log.d(TAG, "setPreConnectRequest : " + preConnect);
        this.preConnectRequest = preConnect;
    }

    public boolean isUseWebViewPool() {
        return this.useWebViewPool;
    }

    public void setUseWebViewPool(boolean useWebViewPool2) {
        H5Log.d(TAG, "setUseWebViewPool : " + useWebViewPool2);
        this.useWebViewPool = useWebViewPool2;
    }

    public String getMultimediaID() {
        return this.multimediaID;
    }

    public void setMultimediaID(String multimediaID2) {
        H5Log.d(TAG, "setMultimediaID : " + multimediaID2);
        this.multimediaID = multimediaID2;
    }

    public String getJsApiDetail() {
        if (this.jsApiDetail.length() <= 0 || !this.jsApiDetail.endsWith(MergeUtil.SEPARATOR_KV)) {
            return this.jsApiDetail.toString();
        }
        return this.jsApiDetail.substring(0, this.jsApiDetail.length() - 1);
    }

    public void appendJsApiDetail(String jsApiDetail2) {
        H5Log.d(TAG, "appendJsApiDetail : " + jsApiDetail2);
        byte[] detailBytes = this.jsApiDetail.getBytes();
        if (detailBytes == null || detailBytes.length <= 10240) {
            this.jsApiDetail += jsApiDetail2 + MergeUtil.SEPARATOR_KV;
        } else {
            H5Log.d(TAG, "detailBytes.length > MAX_LOG_LENGTH, return");
        }
    }

    public boolean isSpdy() {
        return this.isSpdy;
    }

    public void setSpdy(boolean spdy) {
        H5Log.d(TAG, "setSpdy : " + spdy);
        this.isSpdy = spdy;
    }

    public int getErrorSpdyCode() {
        return this.errorSpdyCode;
    }

    public void setErrorSpdyCode(int errorSpdyCode2) {
        H5Log.d(TAG, "setErrorSpdyCode : " + errorSpdyCode2);
        this.errorSpdyCode = errorSpdyCode2;
    }

    public String getErrorSpdyMsg() {
        return this.errorSpdyMsg;
    }

    public void setErrorSpdyMsg(String errorSpdyMsg2) {
        H5Log.d(TAG, "setErrorSpdyMsg : " + errorSpdyMsg2);
        this.errorSpdyMsg = errorSpdyMsg2;
    }

    public String getSpmId() {
        return this.spmId;
    }

    public void setSpmId(String mSpmId) {
        H5Log.d(TAG, "setSpmId : " + mSpmId);
        this.spmId = mSpmId;
    }

    public boolean isShowErrorPage() {
        return this.showErrorPage;
    }

    public void setShowErrorPage(boolean showErrorPage2) {
        H5Log.d(TAG, "setShowErrorPage : " + showErrorPage2);
        this.showErrorPage = showErrorPage2;
    }

    public int getReloadType() {
        return this.reloadType;
    }

    public void setReloadType(int reloadType2) {
        H5Log.d(TAG, "setReloadType : " + reloadType2);
        this.reloadType = reloadType2;
    }

    public String getErrorMsg() {
        return this.errorMsg;
    }

    public void setErrorMsg(String errorMsg2) {
        H5Log.d(TAG, "setErrorMsg : " + errorMsg2);
        this.errorMsg = errorMsg2;
    }

    public void setLastResponseTimestamp(long timeMillis) {
        H5Log.d(TAG, "setLastResponseTimestamp : " + timeMillis);
        this.mLastResponseTimestamp = timeMillis;
    }

    public long getLastResponseTimestamp() {
        if (this.mLastResponseTimestamp <= this.mStart) {
            return this.mStart;
        }
        return this.mLastResponseTimestamp;
    }

    public void setMultiProcessMode(int mode) {
        H5Log.d(TAG, "setMultiProcessMode " + mode);
        this.mMultiProcessMode = mode;
    }

    public int getMultiProcessMode() {
        return this.mMultiProcessMode;
    }

    public int getUcCacheResNum() {
        return this.ucCacheResNum;
    }

    public void setUcCacheResNum(int ucCacheResNum2) {
        H5Log.d(TAG, "setUcCacheResNum : " + ucCacheResNum2);
        this.ucCacheResNum = ucCacheResNum2;
    }

    public int getAliRequestResNum() {
        return this.aliRequestResNum;
    }

    public void setAliRequestResNum(int aliRequestResNum2) {
        this.aliRequestResNum = aliRequestResNum2;
    }

    public void setLottieLoadingAnimStart(long timeMillis) {
        H5Log.d(TAG, "setLottieLoadingAnimStart : " + timeMillis);
        this.mLottieLoadingAnimStart = timeMillis;
    }

    public long getLottieLoadingAnimStart() {
        return this.mLottieLoadingAnimStart;
    }

    public void setLottieLoadingAnimEnd(long timeMillis) {
        H5Log.d(TAG, "setLottieLoadingAnimEnd : " + timeMillis);
        this.mLottieLoadingAnimEnd = timeMillis;
    }

    public boolean isUcFistWebView() {
        return this.mUcFistWebView;
    }

    public void setUcFistWebView() {
        this.mUcFistWebView = sUcFirstWebView;
    }

    public long getLottieLoadingAnimEnd() {
        return this.mLottieLoadingAnimEnd;
    }

    public void setFunctionHasCallback(int funcName) {
        this.mCheckFuncStatusList[funcName] = 1;
    }

    public int getCheckFuncStatus() {
        int val = this.mCheckFuncStatusList[0];
        for (int i = 1; i < this.mCheckFuncStatusList.length; i++) {
            val = (val * 10) + this.mCheckFuncStatusList[i];
        }
        return val;
    }

    public void appendJsApiPerExtra(String data) {
        this.jsApiPerExtra = this.jsApiDetail + data;
    }

    public String getsApiPerExtra() {
        return this.jsApiPerExtra;
    }

    public Bundle getCurrentUrlContext() {
        Bundle bundle = new Bundle();
        String pageUrl = getPageUrl();
        if (!TextUtils.isEmpty(pageUrl)) {
            bundle.putString("cleanUrl", H5UrlHelper.purifyUrl(pageUrl));
            String onlineHost = getStringExtra(H5Param.ONLINE_HOST, null);
            boolean isOnline = false;
            if (!TextUtils.isEmpty(onlineHost)) {
                isOnline = pageUrl.startsWith(onlineHost);
            }
            bundle.putBoolean("online", isOnline);
        }
        bundle.putString("appId", getAppId());
        return bundle;
    }

    public void putStringExtra(String key, String value) {
        this.mExtraMaps.put(key, value);
    }

    public String getStringExtra(String key, String defaultValue) {
        Object val = this.mExtraMaps.get(key);
        if (val == null) {
            return defaultValue;
        }
        try {
            return String.valueOf(val);
        } catch (Throwable t) {
            H5Log.e((String) TAG, t);
            return defaultValue;
        }
    }

    public void putIntExtra(String key, int value) {
        this.mExtraMaps.put(key, Integer.valueOf(value));
    }

    public int getIntExtra(String key, int defaultValue) {
        Object val = this.mExtraMaps.get(key);
        if (val == null) {
            return defaultValue;
        }
        try {
            return ((Integer) val).intValue();
        } catch (Throwable t) {
            H5Log.e((String) TAG, t);
            return defaultValue;
        }
    }

    public void putBooleanExtra(String key, boolean value) {
        this.mExtraMaps.put(key, Boolean.valueOf(value));
    }

    public boolean getBooleanExtra(String key, boolean defaultValue) {
        Object val = this.mExtraMaps.get(key);
        if (val == null) {
            return defaultValue;
        }
        try {
            return ((Boolean) val).booleanValue();
        } catch (Throwable t) {
            H5Log.e((String) TAG, t);
            return defaultValue;
        }
    }

    public boolean appendUCCorePerfExtra(String ucCorePerf, int limitKB) {
        H5Log.d(TAG, "append ucCorePerf: " + ucCorePerf);
        if (limitKB > 0) {
            int limit = limitKB * 1024;
            byte[] srcBytes = this.mUCCorePerfExtra.getBytes();
            byte[] appendBytes = ucCorePerf.getBytes();
            if (!(srcBytes == null || appendBytes == null || srcBytes.length + appendBytes.length <= limit)) {
                H5Log.d(TAG, "ucCorePerf.length >" + limit);
                return false;
            }
        }
        this.mUCCorePerfExtra += ucCorePerf;
        return true;
    }

    public String getUCCorePerfExtra() {
        return this.mUCCorePerfExtra;
    }

    public void setUsePushWindowAnim(boolean value) {
        this.mUsePushWindowAnim = value;
    }

    public boolean isUsePushWindowAnim() {
        return this.mUsePushWindowAnim;
    }
}
