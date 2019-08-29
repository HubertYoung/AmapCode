package com.ut.mini;

import android.app.Activity;
import android.net.Uri;
import android.text.TextUtils;
import com.alibaba.analytics.utils.Logger;
import com.alibaba.fastjson.JSON;
import com.alipay.mobile.antui.screenadpt.AUScreenAdaptTool;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class UTPageHitHelper {
    private static final String FORCE_SPM_CNT = "force-spm-cnt";
    private static final String FORCE_SPM_URL = "force-spm-url";
    private static final int MAX_SKIP_CLEAR_PAGE_OBJECT_CACHE_CAPACITY = 100;
    private static final int MAX_SPM_OBJECT_CACHE_CAPACITY = 50;
    static final String SKIPBK = "skipbk";
    static final String UTPARAM_CNT = "utparam-cnt";
    static final String UTPARAM_URL = "utparam-url";
    private static ArrayList<PageChangeListener> mPageChangerListeners = new ArrayList<>();
    private static UTPageHitHelper s_instance = new UTPageHitHelper();
    private Map<String, String> mBackupNextPageProperties = null;
    private Queue<String> mClearUTPageStateObjectList = new LinkedList();
    private String mCurPage = null;
    private String mCurrentPageCacheKey = null;
    private boolean mIsTurnOff = false;
    private String mLastCacheKey = null;
    private String mLastCacheKeyScmUrl = null;
    private String mLastCacheKeySpmUrl = null;
    private String mLastCacheKeyUtParam = null;
    private String mLastCacheKeyUtParamCnt = null;
    private Map<String, String> mNextPageProperties = new HashMap();
    private boolean mNextPageSkipBack = false;
    private Map<String, UTPageEventObject> mPageEventObjects = new HashMap();
    private Map<String, String> mPageProperties = new HashMap();
    private Map<String, UTPageStateObject> mPageStateObjects = new HashMap();
    private Queue<String> mSPMObjectList = new LinkedList();
    private Map<String, String> mSPMObjectMap = new HashMap();
    private Queue<UTPageEventObject> mSkipClearPageObjectList = new LinkedList();

    public interface PageChangeListener {
        void onPageAppear(Object obj);

        void onPageDisAppear(Object obj);
    }

    public static class UTPageEventObject {
        private String mCacheKey = null;
        private boolean mIsH5Called = false;
        private boolean mIsPageAppearCalled = false;
        private boolean mIsSkipPage = false;
        private Map<String, String> mNextPageProperties = null;
        private long mPageAppearTimestamp = 0;
        private String mPageName = null;
        private Map<String, String> mPageProperties = new HashMap();
        private UTPageStatus mPageStatus = null;
        private int mPageStatusCode = 0;
        private long mPageStayTimstamp = 0;
        private Uri mPageUrl = null;
        private String mRefPage = null;

        public void setNextPageProperties(Map<String, String> map) {
            this.mNextPageProperties = map;
        }

        public Map<String, String> getNextPageProperties() {
            return this.mNextPageProperties;
        }

        public void setCacheKey(String str) {
            this.mCacheKey = str;
        }

        public String getCacheKey() {
            return this.mCacheKey;
        }

        public void resetPropertiesWithoutSkipFlagAndH5Flag() {
            this.mPageProperties = new HashMap();
            this.mPageAppearTimestamp = 0;
            this.mPageStayTimstamp = 0;
            this.mPageUrl = null;
            this.mPageName = null;
            this.mRefPage = null;
            if (this.mPageStatus == null || this.mPageStatus != UTPageStatus.UT_H5_IN_WebView) {
                this.mPageStatus = null;
            }
            this.mIsPageAppearCalled = false;
            this.mIsH5Called = false;
            this.mPageStatusCode = 0;
            this.mNextPageProperties = null;
        }

        public boolean isH5Called() {
            return this.mIsH5Called;
        }

        public void setH5Called() {
            this.mIsH5Called = true;
        }

        public void setToSkipPage() {
            this.mIsSkipPage = true;
        }

        public boolean isSkipPage() {
            return this.mIsSkipPage;
        }

        public void setPageAppearCalled() {
            this.mIsPageAppearCalled = true;
        }

        public boolean isPageAppearCalled() {
            return this.mIsPageAppearCalled;
        }

        public void setPageStatus(UTPageStatus uTPageStatus) {
            this.mPageStatus = uTPageStatus;
        }

        public UTPageStatus getPageStatus() {
            return this.mPageStatus;
        }

        public Map<String, String> getPageProperties() {
            return this.mPageProperties;
        }

        public void setPageProperties(Map<String, String> map) {
            this.mPageProperties = map;
        }

        public long getPageAppearTimestamp() {
            return this.mPageAppearTimestamp;
        }

        public void setPageAppearTimestamp(long j) {
            this.mPageAppearTimestamp = j;
        }

        public long getPageStayTimstamp() {
            return this.mPageStayTimstamp;
        }

        public void setPageStayTimstamp(long j) {
            this.mPageStayTimstamp = j;
        }

        public Uri getPageUrl() {
            return this.mPageUrl;
        }

        public void setPageUrl(Uri uri) {
            this.mPageUrl = uri;
        }

        public void setPageName(String str) {
            this.mPageName = str;
        }

        public String getPageName() {
            return this.mPageName;
        }

        public void setRefPage(String str) {
            this.mRefPage = str;
        }

        public String getRefPage() {
            return this.mRefPage;
        }

        public void setPageStatusCode(int i) {
            this.mPageStatusCode = i;
        }

        public int getPageStatusCode() {
            return this.mPageStatusCode;
        }
    }

    public static class UTPageStateObject {
        public boolean mIsBack = false;
        public boolean mIsFrame = false;
        public boolean mIsH5Page = false;
        boolean mIsSkipBack = false;
        boolean mIsSkipBackForever = false;
        public boolean mIsSwitchBackground = false;
        public String mScmPre = null;
        public String mScmUrl = null;
        public String mSpmCnt = null;
        public String mSpmPre = null;
        public String mSpmUrl = null;
        public String mUtparamCnt = null;
        public String mUtparamPre = null;
        public String mUtparamUrl = null;

        public Map<String, String> getPageStatMap(boolean z) {
            HashMap hashMap = new HashMap();
            if (!TextUtils.isEmpty(this.mSpmCnt)) {
                hashMap.put("spm-cnt", this.mSpmCnt);
            }
            if (!TextUtils.isEmpty(this.mSpmUrl)) {
                hashMap.put("spm-url", this.mSpmUrl);
            }
            if (!TextUtils.isEmpty(this.mSpmPre)) {
                hashMap.put("spm-pre", this.mSpmPre);
            }
            if (!TextUtils.isEmpty(this.mScmPre)) {
                hashMap.put("scm-pre", this.mScmPre);
            }
            if (this.mIsSwitchBackground) {
                hashMap.put("isbf", "1");
            } else if (this.mIsFrame && z) {
                hashMap.put("isfm", "1");
            } else if (this.mIsBack) {
                hashMap.put("ut_isbk", "1");
            }
            if (!TextUtils.isEmpty(this.mUtparamCnt)) {
                hashMap.put(UTPageHitHelper.UTPARAM_CNT, this.mUtparamCnt);
            }
            if (!TextUtils.isEmpty(this.mUtparamUrl)) {
                hashMap.put(UTPageHitHelper.UTPARAM_URL, this.mUtparamUrl);
            }
            if (!TextUtils.isEmpty(this.mUtparamPre)) {
                hashMap.put("utparam-pre", this.mUtparamPre);
            }
            return hashMap;
        }
    }

    private UTPageStateObject copyUTPageStateObject(UTPageStateObject uTPageStateObject) {
        if (uTPageStateObject == null) {
            return null;
        }
        UTPageStateObject uTPageStateObject2 = new UTPageStateObject();
        uTPageStateObject2.mSpmCnt = uTPageStateObject.mSpmCnt;
        uTPageStateObject2.mSpmUrl = uTPageStateObject.mSpmUrl;
        uTPageStateObject2.mSpmPre = uTPageStateObject.mSpmPre;
        uTPageStateObject2.mIsBack = uTPageStateObject.mIsBack;
        uTPageStateObject2.mIsFrame = uTPageStateObject.mIsFrame;
        uTPageStateObject2.mIsSwitchBackground = uTPageStateObject.mIsSwitchBackground;
        uTPageStateObject2.mUtparamCnt = uTPageStateObject.mUtparamCnt;
        uTPageStateObject2.mUtparamUrl = uTPageStateObject.mUtparamUrl;
        uTPageStateObject2.mUtparamPre = uTPageStateObject.mUtparamPre;
        uTPageStateObject2.mScmUrl = uTPageStateObject.mScmUrl;
        uTPageStateObject2.mScmPre = uTPageStateObject.mScmPre;
        uTPageStateObject2.mIsSkipBack = uTPageStateObject.mIsSkipBack;
        uTPageStateObject2.mIsSkipBackForever = uTPageStateObject.mIsSkipBackForever;
        return uTPageStateObject2;
    }

    public void setLastCacheKey(String str) {
        this.mLastCacheKey = str;
    }

    public void setLastCacheKeySpmUrl(String str) {
        this.mLastCacheKeySpmUrl = str;
    }

    public void setLastCacheKeyScmUrl(String str) {
        this.mLastCacheKeyScmUrl = str;
    }

    public void setLastCacheKeyUtParam(String str) {
        this.mLastCacheKeyUtParam = str;
    }

    public void setLastCacheKeyUtParamCnt(String str) {
        this.mLastCacheKeyUtParamCnt = str;
    }

    public String getLastCacheKey() {
        return this.mLastCacheKey;
    }

    public String getLastCacheKeySpmUrl() {
        return this.mLastCacheKeySpmUrl;
    }

    public String getLastCacheKeyScmUrl() {
        return this.mLastCacheKeyScmUrl;
    }

    public String getLastCacheKeyUtParam() {
        return this.mLastCacheKeyUtParam;
    }

    public String getLastCacheKeyUtParamCnt() {
        return this.mLastCacheKeyUtParamCnt;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0015, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static synchronized void addPageChangerListener(com.ut.mini.UTPageHitHelper.PageChangeListener r2) {
        /*
            java.lang.Class<com.ut.mini.UTPageHitHelper> r0 = com.ut.mini.UTPageHitHelper.class
            monitor-enter(r0)
            if (r2 != 0) goto L_0x0007
            monitor-exit(r0)
            return
        L_0x0007:
            java.util.ArrayList<com.ut.mini.UTPageHitHelper$PageChangeListener> r1 = mPageChangerListeners     // Catch:{ all -> 0x0016 }
            boolean r1 = r1.contains(r2)     // Catch:{ all -> 0x0016 }
            if (r1 != 0) goto L_0x0014
            java.util.ArrayList<com.ut.mini.UTPageHitHelper$PageChangeListener> r1 = mPageChangerListeners     // Catch:{ all -> 0x0016 }
            r1.add(r2)     // Catch:{ all -> 0x0016 }
        L_0x0014:
            monitor-exit(r0)
            return
        L_0x0016:
            r2 = move-exception
            monitor-exit(r0)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ut.mini.UTPageHitHelper.addPageChangerListener(com.ut.mini.UTPageHitHelper$PageChangeListener):void");
    }

    static synchronized void disPathcherPageChangerEvent(int i, Object obj) {
        synchronized (UTPageHitHelper.class) {
            int size = mPageChangerListeners.size();
            for (int i2 = 0; i2 < size; i2++) {
                PageChangeListener pageChangeListener = mPageChangerListeners.get(i2);
                if (pageChangeListener != null) {
                    if (i == 0) {
                        pageChangeListener.onPageAppear(obj);
                    } else {
                        pageChangeListener.onPageDisAppear(obj);
                    }
                }
            }
        }
    }

    public static UTPageHitHelper getInstance() {
        return s_instance;
    }

    /* access modifiers changed from: 0000 */
    public synchronized Map<String, String> getNextPageProperties(Object obj) {
        if (obj == null) {
            return null;
        }
        try {
            return _getOrNewAUTPageEventObject(obj).getNextPageProperties();
        }
    }

    /* access modifiers changed from: 0000 */
    public synchronized void _releaseSkipFlagAndH5FlagPageObject(UTPageEventObject uTPageEventObject) {
        uTPageEventObject.resetPropertiesWithoutSkipFlagAndH5Flag();
        if (!this.mSkipClearPageObjectList.contains(uTPageEventObject)) {
            this.mSkipClearPageObjectList.add(uTPageEventObject);
        }
        if (this.mSkipClearPageObjectList.size() > 200) {
            for (int i = 0; i < 100; i++) {
                UTPageEventObject poll = this.mSkipClearPageObjectList.poll();
                if (poll != null && this.mPageEventObjects.containsKey(poll.getCacheKey())) {
                    this.mPageEventObjects.remove(poll.getCacheKey());
                }
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public synchronized void _releaseSPMCacheObj(String str) {
        if (!this.mSPMObjectList.contains(str)) {
            this.mSPMObjectList.add(str);
        }
        if (this.mSPMObjectList.size() > 100) {
            for (int i = 0; i < 50; i++) {
                String poll = this.mSPMObjectList.poll();
                if (poll != null && this.mSPMObjectMap.containsKey(poll)) {
                    this.mSPMObjectMap.remove(poll);
                }
            }
        }
    }

    @Deprecated
    public synchronized void turnOffAutoPageTrack() {
        this.mIsTurnOff = true;
    }

    public String getCurrentPageName() {
        return this.mCurPage;
    }

    /* access modifiers changed from: 0000 */
    public void pageAppearByAuto(Activity activity) {
        if (!this.mIsTurnOff) {
            pageAppear(activity);
        }
    }

    private String _getPageEventObjectCacheKey(Object obj) {
        String str;
        if (obj instanceof String) {
            str = (String) obj;
        } else {
            str = obj.getClass().getSimpleName();
        }
        int hashCode = obj.hashCode();
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(hashCode);
        return sb.toString();
    }

    /* access modifiers changed from: 0000 */
    public synchronized boolean isH52001(Object obj) {
        if (obj != null) {
            try {
                UTPageEventObject _getOrNewAUTPageEventObject = _getOrNewAUTPageEventObject(obj);
                if (_getOrNewAUTPageEventObject.getPageStatus() != null && _getOrNewAUTPageEventObject.getPageStatus() == UTPageStatus.UT_H5_IN_WebView) {
                    return true;
                }
            }
        }
        return false;
    }

    /* access modifiers changed from: 0000 */
    public synchronized void setH5Called(Object obj) {
        if (obj != null) {
            UTPageEventObject _getOrNewAUTPageEventObject = _getOrNewAUTPageEventObject(obj);
            if (_getOrNewAUTPageEventObject.getPageStatus() != null) {
                _getOrNewAUTPageEventObject.setH5Called();
            }
        }
    }

    private synchronized UTPageEventObject _getOrNewAUTPageEventObject(Object obj) {
        String _getPageEventObjectCacheKey = _getPageEventObjectCacheKey(obj);
        if (this.mPageEventObjects.containsKey(_getPageEventObjectCacheKey)) {
            return this.mPageEventObjects.get(_getPageEventObjectCacheKey);
        }
        UTPageEventObject uTPageEventObject = new UTPageEventObject();
        this.mPageEventObjects.put(_getPageEventObjectCacheKey, uTPageEventObject);
        uTPageEventObject.setCacheKey(_getPageEventObjectCacheKey);
        return uTPageEventObject;
    }

    private synchronized void _putUTPageEventObjectToCache(String str, UTPageEventObject uTPageEventObject) {
        this.mPageEventObjects.put(str, uTPageEventObject);
    }

    private synchronized void _clearUTPageEventObjectCache(UTPageEventObject uTPageEventObject) {
        if (this.mPageEventObjects.containsKey(uTPageEventObject.getCacheKey())) {
            this.mPageEventObjects.remove(uTPageEventObject.getCacheKey());
        }
    }

    private synchronized void _removeUTPageEventObject(Object obj) {
        String _getPageEventObjectCacheKey = _getPageEventObjectCacheKey(obj);
        if (this.mPageEventObjects.containsKey(_getPageEventObjectCacheKey)) {
            this.mPageEventObjects.remove(_getPageEventObjectCacheKey);
        }
    }

    @Deprecated
    public synchronized void pageAppear(Object obj) {
        pageAppear(obj, null, false);
    }

    /* access modifiers changed from: 0000 */
    /* JADX WARNING: Code restructure failed: missing block: B:69:0x01b0, code lost:
        return;
     */
    /* JADX WARNING: Exception block dominator not found, dom blocks: [] */
    /* JADX WARNING: Missing exception handler attribute for start block: B:42:0x011f */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x0125 A[Catch:{ Throwable -> 0x00ca }] */
    /* JADX WARNING: Removed duplicated region for block: B:46:0x0126 A[Catch:{ Throwable -> 0x00ca }] */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x0131 A[Catch:{ Throwable -> 0x00ca }] */
    /* JADX WARNING: Removed duplicated region for block: B:52:0x015a A[Catch:{ Throwable -> 0x00ca }] */
    /* JADX WARNING: Removed duplicated region for block: B:58:0x018b A[Catch:{ Throwable -> 0x00ca }] */
    /* JADX WARNING: Removed duplicated region for block: B:67:0x01ad A[Catch:{ Throwable -> 0x00ca }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void pageAppear(java.lang.Object r11, java.lang.String r12, boolean r13) {
        /*
            r10 = this;
            monitor-enter(r10)
            com.alibaba.analytics.utils.Logger.d()     // Catch:{ all -> 0x01be }
            com.ut.mini.UTPvidHelper.pageAppear()     // Catch:{ all -> 0x01be }
            com.ut.mini.module.trackerlistener.UTTrackerListenerMgr r0 = com.ut.mini.module.trackerlistener.UTTrackerListenerMgr.getInstance()     // Catch:{ all -> 0x01be }
            com.ut.mini.UTAnalytics r1 = com.ut.mini.UTAnalytics.getInstance()     // Catch:{ all -> 0x01be }
            com.ut.mini.UTTracker r1 = r1.getDefaultTracker()     // Catch:{ all -> 0x01be }
            r0.pageAppear(r1, r11, r12, r13)     // Catch:{ all -> 0x01be }
            r0 = 1
            r1 = 0
            if (r11 == 0) goto L_0x01b1
            java.lang.String r2 = r10._getPageEventObjectCacheKey(r11)     // Catch:{ all -> 0x01be }
            if (r2 == 0) goto L_0x002a
            java.lang.String r3 = r10.mCurrentPageCacheKey     // Catch:{ all -> 0x01be }
            boolean r2 = r2.equals(r3)     // Catch:{ all -> 0x01be }
            if (r2 == 0) goto L_0x002a
            monitor-exit(r10)
            return
        L_0x002a:
            java.lang.String r2 = r10.mCurrentPageCacheKey     // Catch:{ all -> 0x01be }
            if (r2 == 0) goto L_0x004c
            java.lang.String r2 = "lost 2001"
            java.lang.Object[] r3 = new java.lang.Object[r0]     // Catch:{ all -> 0x01be }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x01be }
            java.lang.String r5 = "Last page requires leave("
            r4.<init>(r5)     // Catch:{ all -> 0x01be }
            java.lang.String r5 = r10.mCurrentPageCacheKey     // Catch:{ all -> 0x01be }
            r4.append(r5)     // Catch:{ all -> 0x01be }
            java.lang.String r5 = ")."
            r4.append(r5)     // Catch:{ all -> 0x01be }
            java.lang.String r4 = r4.toString()     // Catch:{ all -> 0x01be }
            r3[r1] = r4     // Catch:{ all -> 0x01be }
            com.alibaba.analytics.utils.Logger.e(r2, r3)     // Catch:{ all -> 0x01be }
        L_0x004c:
            com.ut.mini.UTPageHitHelper$UTPageEventObject r2 = r10._getOrNewAUTPageEventObject(r11)     // Catch:{ all -> 0x01be }
            if (r13 != 0) goto L_0x0079
            boolean r3 = r2.isSkipPage()     // Catch:{ all -> 0x01be }
            if (r3 == 0) goto L_0x0079
            java.lang.String r12 = "skip page[pageAppear]"
            java.lang.Object[] r13 = new java.lang.Object[r0]     // Catch:{ all -> 0x01be }
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ all -> 0x01be }
            java.lang.String r2 = "page name:"
            r0.<init>(r2)     // Catch:{ all -> 0x01be }
            java.lang.Class r11 = r11.getClass()     // Catch:{ all -> 0x01be }
            java.lang.String r11 = r11.getSimpleName()     // Catch:{ all -> 0x01be }
            r0.append(r11)     // Catch:{ all -> 0x01be }
            java.lang.String r11 = r0.toString()     // Catch:{ all -> 0x01be }
            r13[r1] = r11     // Catch:{ all -> 0x01be }
            com.alibaba.analytics.utils.Logger.i(r12, r13)     // Catch:{ all -> 0x01be }
            monitor-exit(r10)
            return
        L_0x0079:
            disPathcherPageChangerEvent(r1, r11)     // Catch:{ all -> 0x01be }
            com.ut.mini.module.UTOperationStack r3 = com.ut.mini.module.UTOperationStack.getInstance()     // Catch:{ all -> 0x01be }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x01be }
            java.lang.String r5 = "pageAppear:"
            r4.<init>(r5)     // Catch:{ all -> 0x01be }
            java.lang.Class r5 = r11.getClass()     // Catch:{ all -> 0x01be }
            java.lang.String r5 = r5.getSimpleName()     // Catch:{ all -> 0x01be }
            r4.append(r5)     // Catch:{ all -> 0x01be }
            java.lang.String r4 = r4.toString()     // Catch:{ all -> 0x01be }
            r3.addAction(r4)     // Catch:{ all -> 0x01be }
            com.ut.mini.UTVariables r3 = com.ut.mini.UTVariables.getInstance()     // Catch:{ all -> 0x01be }
            java.lang.String r3 = r3.getH5Url()     // Catch:{ all -> 0x01be }
            r4 = 0
            if (r3 == 0) goto L_0x00db
            com.ut.mini.UTVariables r5 = com.ut.mini.UTVariables.getInstance()     // Catch:{ all -> 0x01be }
            r5.setBackupH5Url(r3)     // Catch:{ all -> 0x01be }
            android.net.Uri r3 = android.net.Uri.parse(r3)     // Catch:{ Throwable -> 0x00ca }
            java.lang.String r5 = "spm"
            java.lang.String r5 = r3.getQueryParameter(r5)     // Catch:{ Throwable -> 0x00ca }
            java.lang.String r6 = "scm"
            java.lang.String r3 = r3.getQueryParameter(r6)     // Catch:{ Throwable -> 0x00ca }
            java.util.Map<java.lang.String, java.lang.String> r6 = r10.mPageProperties     // Catch:{ Throwable -> 0x00ca }
            java.lang.String r7 = "spm"
            r6.put(r7, r5)     // Catch:{ Throwable -> 0x00ca }
            java.util.Map<java.lang.String, java.lang.String> r5 = r10.mPageProperties     // Catch:{ Throwable -> 0x00ca }
            java.lang.String r6 = "scm"
            r5.put(r6, r3)     // Catch:{ Throwable -> 0x00ca }
            goto L_0x00d4
        L_0x00ca:
            r3 = move-exception
            java.lang.String r5 = ""
            java.lang.Object[] r6 = new java.lang.Object[r0]     // Catch:{ all -> 0x01be }
            r6[r1] = r3     // Catch:{ all -> 0x01be }
            com.alibaba.analytics.utils.Logger.d(r5, r6)     // Catch:{ all -> 0x01be }
        L_0x00d4:
            com.ut.mini.UTVariables r3 = com.ut.mini.UTVariables.getInstance()     // Catch:{ all -> 0x01be }
            r3.setH5Url(r4)     // Catch:{ all -> 0x01be }
        L_0x00db:
            java.lang.String r3 = _getPageName(r11)     // Catch:{ all -> 0x01be }
            boolean r5 = com.ut.mini.extend.UTExtendSwitch.bJTrackExtend     // Catch:{ all -> 0x01be }
            if (r5 == 0) goto L_0x011f
            java.lang.Class r5 = r11.getClass()     // Catch:{ Throwable -> 0x011f }
            java.lang.String r5 = r5.getSimpleName()     // Catch:{ Throwable -> 0x011f }
            java.lang.String r5 = com.ut.mini.extend.JTrackExtend.getPageName(r5)     // Catch:{ Throwable -> 0x011f }
            boolean r6 = android.text.TextUtils.isEmpty(r5)     // Catch:{ Throwable -> 0x011f }
            if (r6 != 0) goto L_0x011f
            java.lang.String r6 = r5.toLowerCase()     // Catch:{ Throwable -> 0x011f }
            java.lang.String r7 = "activity"
            boolean r6 = r6.endsWith(r7)     // Catch:{ Throwable -> 0x011f }
            if (r6 == 0) goto L_0x010b
            int r6 = r5.length()     // Catch:{ Throwable -> 0x011f }
            int r6 = r6 + -8
            java.lang.String r5 = r5.substring(r1, r6)     // Catch:{ Throwable -> 0x011f }
        L_0x010b:
            java.lang.String r6 = "JTrack"
            java.lang.Object[] r7 = new java.lang.Object[r0]     // Catch:{ Throwable -> 0x011f }
            java.lang.String r8 = "getPageName:"
            java.lang.String r9 = java.lang.String.valueOf(r5)     // Catch:{ Throwable -> 0x011f }
            java.lang.String r8 = r8.concat(r9)     // Catch:{ Throwable -> 0x011f }
            r7[r1] = r8     // Catch:{ Throwable -> 0x011f }
            com.alibaba.analytics.utils.Logger.i(r6, r7)     // Catch:{ Throwable -> 0x011f }
            r3 = r5
        L_0x011f:
            boolean r5 = com.alibaba.analytics.utils.StringUtils.isEmpty(r12)     // Catch:{ all -> 0x01be }
            if (r5 != 0) goto L_0x0126
            goto L_0x0127
        L_0x0126:
            r12 = r3
        L_0x0127:
            java.lang.String r3 = r2.getPageName()     // Catch:{ all -> 0x01be }
            boolean r3 = com.alibaba.analytics.utils.StringUtils.isEmpty(r3)     // Catch:{ all -> 0x01be }
            if (r3 != 0) goto L_0x0135
            java.lang.String r12 = r2.getPageName()     // Catch:{ all -> 0x01be }
        L_0x0135:
            r10.mCurPage = r12     // Catch:{ all -> 0x01be }
            r2.setPageName(r12)     // Catch:{ all -> 0x01be }
            long r5 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x01be }
            r2.setPageAppearTimestamp(r5)     // Catch:{ all -> 0x01be }
            long r5 = android.os.SystemClock.elapsedRealtime()     // Catch:{ all -> 0x01be }
            r2.setPageStayTimstamp(r5)     // Catch:{ all -> 0x01be }
            com.ut.mini.UTVariables r12 = com.ut.mini.UTVariables.getInstance()     // Catch:{ all -> 0x01be }
            java.lang.String r12 = r12.getRefPage()     // Catch:{ all -> 0x01be }
            r2.setRefPage(r12)     // Catch:{ all -> 0x01be }
            r2.setPageAppearCalled()     // Catch:{ all -> 0x01be }
            java.util.Map<java.lang.String, java.lang.String> r12 = r10.mNextPageProperties     // Catch:{ all -> 0x01be }
            if (r12 == 0) goto L_0x017f
            java.util.Map<java.lang.String, java.lang.String> r12 = r10.mNextPageProperties     // Catch:{ all -> 0x01be }
            r10.mBackupNextPageProperties = r12     // Catch:{ all -> 0x01be }
            java.util.Map<java.lang.String, java.lang.String> r12 = r10.mNextPageProperties     // Catch:{ all -> 0x01be }
            r2.setNextPageProperties(r12)     // Catch:{ all -> 0x01be }
            java.util.Map r12 = r2.getPageProperties()     // Catch:{ all -> 0x01be }
            if (r12 != 0) goto L_0x016f
            java.util.Map<java.lang.String, java.lang.String> r12 = r10.mNextPageProperties     // Catch:{ all -> 0x01be }
            r2.setPageProperties(r12)     // Catch:{ all -> 0x01be }
            goto L_0x017f
        L_0x016f:
            java.util.HashMap r3 = new java.util.HashMap     // Catch:{ all -> 0x01be }
            r3.<init>()     // Catch:{ all -> 0x01be }
            r3.putAll(r12)     // Catch:{ all -> 0x01be }
            java.util.Map<java.lang.String, java.lang.String> r12 = r10.mNextPageProperties     // Catch:{ all -> 0x01be }
            r3.putAll(r12)     // Catch:{ all -> 0x01be }
            r2.setPageProperties(r3)     // Catch:{ all -> 0x01be }
        L_0x017f:
            r10.mNextPageProperties = r4     // Catch:{ all -> 0x01be }
            java.lang.String r12 = r10._getPageEventObjectCacheKey(r11)     // Catch:{ all -> 0x01be }
            r10.mCurrentPageCacheKey = r12     // Catch:{ all -> 0x01be }
            boolean r12 = r10.mNextPageSkipBack     // Catch:{ all -> 0x01be }
            if (r12 == 0) goto L_0x0195
            com.ut.mini.UTPageHitHelper$UTPageStateObject r12 = r10.getOrNewUTPageStateObject(r11)     // Catch:{ all -> 0x01be }
            if (r12 == 0) goto L_0x0195
            r12.mIsSkipBack = r0     // Catch:{ all -> 0x01be }
            r10.mNextPageSkipBack = r1     // Catch:{ all -> 0x01be }
        L_0x0195:
            r10._clearUTPageEventObjectCache(r2)     // Catch:{ all -> 0x01be }
            java.lang.String r12 = r10._getPageEventObjectCacheKey(r11)     // Catch:{ all -> 0x01be }
            r10._putUTPageEventObjectToCache(r12, r2)     // Catch:{ all -> 0x01be }
            if (r13 == 0) goto L_0x01af
            boolean r12 = r2.isSkipPage()     // Catch:{ all -> 0x01be }
            if (r12 == 0) goto L_0x01af
            com.ut.mini.UTPageHitHelper$UTPageStateObject r11 = r10.getOrNewUTPageStateObject(r11)     // Catch:{ all -> 0x01be }
            if (r11 == 0) goto L_0x01af
            r11.mIsFrame = r0     // Catch:{ all -> 0x01be }
        L_0x01af:
            monitor-exit(r10)
            return
        L_0x01b1:
            java.lang.String r11 = "pageAppear"
            java.lang.Object[] r12 = new java.lang.Object[r0]     // Catch:{ all -> 0x01be }
            java.lang.String r13 = "The page object should not be null"
            r12[r1] = r13     // Catch:{ all -> 0x01be }
            com.alibaba.analytics.utils.Logger.e(r11, r12)     // Catch:{ all -> 0x01be }
            monitor-exit(r10)
            return
        L_0x01be:
            r11 = move-exception
            monitor-exit(r10)
            throw r11
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ut.mini.UTPageHitHelper.pageAppear(java.lang.Object, java.lang.String, boolean):void");
    }

    /* access modifiers changed from: 0000 */
    public synchronized void pageAppear(Object obj, String str) {
        pageAppear(obj, str, false);
    }

    @Deprecated
    public synchronized void updatePageProperties(Map<String, String> map) {
        if (map != null) {
            this.mPageProperties.putAll(map);
        }
    }

    /* access modifiers changed from: 0000 */
    public synchronized void updatePageProperties(Object obj, Map<String, String> map) {
        if (!(obj == null || map == null)) {
            if (map.size() != 0) {
                HashMap hashMap = new HashMap();
                hashMap.putAll(map);
                UTPageEventObject _getOrNewAUTPageEventObject = _getOrNewAUTPageEventObject(obj);
                Map<String, String> pageProperties = _getOrNewAUTPageEventObject.getPageProperties();
                if (pageProperties == null) {
                    _getOrNewAUTPageEventObject.setPageProperties(hashMap);
                    return;
                }
                HashMap hashMap2 = new HashMap();
                hashMap2.putAll(pageProperties);
                hashMap2.putAll(hashMap);
                _getOrNewAUTPageEventObject.setPageProperties(hashMap2);
                return;
            }
        }
        Logger.e((String) "", "failed to update project properties");
    }

    public Map<String, String> getPageProperties(Object obj) {
        if (obj == null) {
            return null;
        }
        HashMap hashMap = new HashMap();
        if (this.mPageProperties != null) {
            hashMap.putAll(this.mPageProperties);
        }
        Map<String, String> pageProperties = _getOrNewAUTPageEventObject(obj).getPageProperties();
        if (pageProperties != null) {
            hashMap.putAll(pageProperties);
        }
        return hashMap;
    }

    /* access modifiers changed from: 0000 */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0033, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0038, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void updatePageUtparam(java.lang.Object r3, java.lang.String r4) {
        /*
            r2 = this;
            monitor-enter(r2)
            if (r3 == 0) goto L_0x0037
            boolean r0 = com.alibaba.analytics.utils.StringUtils.isEmpty(r4)     // Catch:{ all -> 0x0034 }
            if (r0 == 0) goto L_0x000a
            goto L_0x0037
        L_0x000a:
            java.util.Map r0 = r2.getPageProperties(r3)     // Catch:{ all -> 0x0034 }
            java.lang.String r1 = ""
            if (r0 == 0) goto L_0x001b
            java.lang.String r1 = "utparam-cnt"
            java.lang.Object r0 = r0.get(r1)     // Catch:{ all -> 0x0034 }
            r1 = r0
            java.lang.String r1 = (java.lang.String) r1     // Catch:{ all -> 0x0034 }
        L_0x001b:
            java.lang.String r4 = r2.refreshUtParam(r4, r1)     // Catch:{ all -> 0x0034 }
            boolean r0 = android.text.TextUtils.isEmpty(r4)     // Catch:{ all -> 0x0034 }
            if (r0 != 0) goto L_0x0032
            java.util.HashMap r0 = new java.util.HashMap     // Catch:{ all -> 0x0034 }
            r0.<init>()     // Catch:{ all -> 0x0034 }
            java.lang.String r1 = "utparam-cnt"
            r0.put(r1, r4)     // Catch:{ all -> 0x0034 }
            r2.updatePageProperties(r3, r0)     // Catch:{ all -> 0x0034 }
        L_0x0032:
            monitor-exit(r2)
            return
        L_0x0034:
            r3 = move-exception
            monitor-exit(r2)
            throw r3
        L_0x0037:
            monitor-exit(r2)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ut.mini.UTPageHitHelper.updatePageUtparam(java.lang.Object, java.lang.String):void");
    }

    /* access modifiers changed from: 0000 */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0019, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void updatePageName(java.lang.Object r2, java.lang.String r3) {
        /*
            r1 = this;
            monitor-enter(r1)
            if (r2 == 0) goto L_0x0018
            boolean r0 = com.alibaba.analytics.utils.StringUtils.isEmpty(r3)     // Catch:{ all -> 0x0015 }
            if (r0 == 0) goto L_0x000a
            goto L_0x0018
        L_0x000a:
            com.ut.mini.UTPageHitHelper$UTPageEventObject r2 = r1._getOrNewAUTPageEventObject(r2)     // Catch:{ all -> 0x0015 }
            r2.setPageName(r3)     // Catch:{ all -> 0x0015 }
            r1.mCurPage = r3     // Catch:{ all -> 0x0015 }
            monitor-exit(r1)
            return
        L_0x0015:
            r2 = move-exception
            monitor-exit(r1)
            throw r2
        L_0x0018:
            monitor-exit(r1)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ut.mini.UTPageHitHelper.updatePageName(java.lang.Object, java.lang.String):void");
    }

    /* access modifiers changed from: 0000 */
    public synchronized void updatePageUrl(Object obj, Uri uri) {
        if (obj != null) {
            _getOrNewAUTPageEventObject(obj).setPageUrl(uri);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:14:0x001d, code lost:
        return null;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized java.lang.String getPageUrl(java.lang.Object r3) {
        /*
            r2 = this;
            monitor-enter(r2)
            r0 = 0
            if (r3 != 0) goto L_0x0006
            monitor-exit(r2)
            return r0
        L_0x0006:
            com.ut.mini.UTPageHitHelper$UTPageEventObject r3 = r2._getOrNewAUTPageEventObject(r3)     // Catch:{ all -> 0x001e }
            if (r3 == 0) goto L_0x001c
            android.net.Uri r1 = r3.getPageUrl()     // Catch:{ all -> 0x001e }
            if (r1 == 0) goto L_0x001c
            android.net.Uri r3 = r3.getPageUrl()     // Catch:{ all -> 0x001e }
            java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x001e }
            monitor-exit(r2)
            return r3
        L_0x001c:
            monitor-exit(r2)
            return r0
        L_0x001e:
            r3 = move-exception
            monitor-exit(r2)
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ut.mini.UTPageHitHelper.getPageUrl(java.lang.Object):java.lang.String");
    }

    /* access modifiers changed from: 0000 */
    public synchronized void updatePageStatus(Object obj, UTPageStatus uTPageStatus) {
        if (obj != null) {
            _getOrNewAUTPageEventObject(obj).setPageStatus(uTPageStatus);
        }
    }

    /* access modifiers changed from: 0000 */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0048, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void updateNextPageProperties(java.util.Map<java.lang.String, java.lang.String> r4) {
        /*
            r3 = this;
            monitor-enter(r3)
            if (r4 == 0) goto L_0x0047
            java.util.HashMap r0 = new java.util.HashMap     // Catch:{ all -> 0x0044 }
            r0.<init>()     // Catch:{ all -> 0x0044 }
            r0.putAll(r4)     // Catch:{ all -> 0x0044 }
            java.util.Map<java.lang.String, java.lang.String> r4 = r3.mNextPageProperties     // Catch:{ all -> 0x0044 }
            if (r4 != 0) goto L_0x0013
            r3.mNextPageProperties = r0     // Catch:{ all -> 0x0044 }
            monitor-exit(r3)
            return
        L_0x0013:
            java.util.Map<java.lang.String, java.lang.String> r4 = r3.mNextPageProperties     // Catch:{ all -> 0x0044 }
            java.lang.String r1 = "utparam-url"
            java.lang.Object r4 = r4.get(r1)     // Catch:{ all -> 0x0044 }
            java.lang.String r4 = (java.lang.String) r4     // Catch:{ all -> 0x0044 }
            java.util.Map<java.lang.String, java.lang.String> r1 = r3.mNextPageProperties     // Catch:{ all -> 0x0044 }
            java.lang.String r2 = "utparam-cnt"
            java.lang.Object r1 = r1.get(r2)     // Catch:{ all -> 0x0044 }
            java.lang.String r1 = (java.lang.String) r1     // Catch:{ all -> 0x0044 }
            r3.mNextPageProperties = r0     // Catch:{ all -> 0x0044 }
            boolean r0 = android.text.TextUtils.isEmpty(r4)     // Catch:{ all -> 0x0044 }
            if (r0 != 0) goto L_0x0036
            java.util.Map<java.lang.String, java.lang.String> r0 = r3.mNextPageProperties     // Catch:{ all -> 0x0044 }
            java.lang.String r2 = "utparam-url"
            r0.put(r2, r4)     // Catch:{ all -> 0x0044 }
        L_0x0036:
            boolean r4 = android.text.TextUtils.isEmpty(r1)     // Catch:{ all -> 0x0044 }
            if (r4 != 0) goto L_0x0047
            java.util.Map<java.lang.String, java.lang.String> r4 = r3.mNextPageProperties     // Catch:{ all -> 0x0044 }
            java.lang.String r0 = "utparam-cnt"
            r4.put(r0, r1)     // Catch:{ all -> 0x0044 }
            goto L_0x0047
        L_0x0044:
            r4 = move-exception
            monitor-exit(r3)
            throw r4
        L_0x0047:
            monitor-exit(r3)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ut.mini.UTPageHitHelper.updateNextPageProperties(java.util.Map):void");
    }

    /* access modifiers changed from: 0000 */
    public synchronized void updateNextPageUtparam(String str) {
        if (!TextUtils.isEmpty(str)) {
            String str2 = "";
            if (this.mNextPageProperties != null) {
                str2 = this.mNextPageProperties.get(UTPARAM_URL);
            } else {
                this.mNextPageProperties = new HashMap();
            }
            String refreshUtParam = refreshUtParam(str, str2);
            if (!TextUtils.isEmpty(refreshUtParam)) {
                HashMap hashMap = new HashMap();
                hashMap.put(UTPARAM_URL, refreshUtParam);
                this.mNextPageProperties.putAll(hashMap);
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public synchronized void updateNextPageUtparamCnt(String str) {
        if (!TextUtils.isEmpty(str)) {
            String str2 = "";
            if (this.mNextPageProperties != null) {
                str2 = this.mNextPageProperties.get(UTPARAM_CNT);
            } else {
                this.mNextPageProperties = new HashMap();
            }
            String refreshUtParam = refreshUtParam(str, str2);
            if (!TextUtils.isEmpty(refreshUtParam)) {
                HashMap hashMap = new HashMap();
                hashMap.put(UTPARAM_CNT, refreshUtParam);
                this.mNextPageProperties.putAll(hashMap);
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public synchronized void setPageStatusCode(Object obj, int i) {
        if (obj != null) {
            _getOrNewAUTPageEventObject(obj).setPageStatusCode(i);
        }
    }

    /* access modifiers changed from: 0000 */
    public void pageDisAppearByAuto(Activity activity) {
        if (!this.mIsTurnOff) {
            pageDisAppear(activity, UTAnalytics.getInstance().getDefaultTracker());
        }
    }

    /* access modifiers changed from: 0000 */
    public synchronized void skipPage(Object obj) {
        if (obj != null) {
            _getOrNewAUTPageEventObject(obj).setToSkipPage();
        }
    }

    /* access modifiers changed from: 0000 */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x000f, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void skipBack(java.lang.Object r2) {
        /*
            r1 = this;
            monitor-enter(r1)
            if (r2 != 0) goto L_0x0005
            monitor-exit(r1)
            return
        L_0x0005:
            com.ut.mini.UTPageHitHelper$UTPageStateObject r2 = r1.getOrNewUTPageStateObject(r2)     // Catch:{ all -> 0x0010 }
            if (r2 == 0) goto L_0x000e
            r0 = 1
            r2.mIsSkipBack = r0     // Catch:{ all -> 0x0010 }
        L_0x000e:
            monitor-exit(r1)
            return
        L_0x0010:
            r2 = move-exception
            monitor-exit(r1)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ut.mini.UTPageHitHelper.skipBack(java.lang.Object):void");
    }

    /* access modifiers changed from: 0000 */
    public synchronized void skipNextPageBack() {
        this.mNextPageSkipBack = true;
    }

    /* access modifiers changed from: 0000 */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x000e, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void skipBackForever(java.lang.Object r1, boolean r2) {
        /*
            r0 = this;
            monitor-enter(r0)
            if (r1 != 0) goto L_0x0005
            monitor-exit(r0)
            return
        L_0x0005:
            com.ut.mini.UTPageHitHelper$UTPageStateObject r1 = r0.getOrNewUTPageStateObject(r1)     // Catch:{ all -> 0x000f }
            if (r1 == 0) goto L_0x000d
            r1.mIsSkipBackForever = r2     // Catch:{ all -> 0x000f }
        L_0x000d:
            monitor-exit(r0)
            return
        L_0x000f:
            r1 = move-exception
            monitor-exit(r0)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ut.mini.UTPageHitHelper.skipBackForever(java.lang.Object, boolean):void");
    }

    private void _clearPageDisAppearContext() {
        this.mPageProperties = new HashMap();
        this.mCurrentPageCacheKey = null;
        this.mCurPage = null;
        this.mBackupNextPageProperties = null;
        UTVariables.getInstance().setBackupH5Url(null);
    }

    /* JADX INFO: finally extract failed */
    /* JADX WARNING: Exception block dominator not found, dom blocks: [] */
    /* JADX WARNING: Missing exception handler attribute for start block: B:97:0x0215 */
    /* JADX WARNING: Removed duplicated region for block: B:100:0x021b A[Catch:{ Exception -> 0x0482, all -> 0x0590 }] */
    /* JADX WARNING: Removed duplicated region for block: B:103:0x0226 A[Catch:{ Exception -> 0x0482, all -> 0x0590 }] */
    /* JADX WARNING: Removed duplicated region for block: B:117:0x0265  */
    /* JADX WARNING: Removed duplicated region for block: B:191:0x0361 A[Catch:{ Exception -> 0x0482, all -> 0x0590 }] */
    /* JADX WARNING: Removed duplicated region for block: B:195:0x0373 A[Catch:{ Exception -> 0x0482, all -> 0x0590 }] */
    /* JADX WARNING: Removed duplicated region for block: B:226:0x0465  */
    /* JADX WARNING: Removed duplicated region for block: B:230:0x047c A[Catch:{ Exception -> 0x0482, all -> 0x0590 }] */
    /* JADX WARNING: Removed duplicated region for block: B:245:0x04c5 A[Catch:{ Exception -> 0x0482, all -> 0x0590 }] */
    /* JADX WARNING: Removed duplicated region for block: B:255:0x0527 A[Catch:{ Exception -> 0x0482, all -> 0x0590 }] */
    /* JADX WARNING: Removed duplicated region for block: B:257:0x052f A[Catch:{ Exception -> 0x0482, all -> 0x0590 }] */
    @java.lang.Deprecated
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void pageDisAppear(java.lang.Object r31, com.ut.mini.UTTracker r32) {
        /*
            r30 = this;
            r7 = r30
            r8 = r31
            r9 = r32
            monitor-enter(r30)
            com.alibaba.analytics.utils.Logger.d()     // Catch:{ all -> 0x0590 }
            com.ut.mini.module.trackerlistener.UTTrackerListenerMgr r1 = com.ut.mini.module.trackerlistener.UTTrackerListenerMgr.getInstance()     // Catch:{ all -> 0x0590 }
            r1.pageDisAppear(r9, r8)     // Catch:{ all -> 0x0590 }
            r10 = 1
            r11 = 0
            if (r8 == 0) goto L_0x0581
            java.lang.String r1 = r7.mCurrentPageCacheKey     // Catch:{ all -> 0x0590 }
            if (r1 != 0) goto L_0x0026
            java.lang.String r1 = "pageDisAppear"
            java.lang.Object[] r2 = new java.lang.Object[r10]     // Catch:{ all -> 0x0590 }
            java.lang.String r3 = "UT has already recorded the page disappear event on this page"
            r2[r11] = r3     // Catch:{ all -> 0x0590 }
            com.alibaba.analytics.utils.Logger.e(r1, r2)     // Catch:{ all -> 0x0590 }
            monitor-exit(r30)
            return
        L_0x0026:
            com.ut.mini.UTPageHitHelper$UTPageEventObject r12 = r30._getOrNewAUTPageEventObject(r31)     // Catch:{ all -> 0x0590 }
            boolean r1 = r12.isPageAppearCalled()     // Catch:{ all -> 0x0590 }
            if (r1 == 0) goto L_0x053b
            com.ut.mini.module.UTOperationStack r1 = com.ut.mini.module.UTOperationStack.getInstance()     // Catch:{ all -> 0x0590 }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ all -> 0x0590 }
            java.lang.String r3 = "pageDisAppear:"
            r2.<init>(r3)     // Catch:{ all -> 0x0590 }
            java.lang.Class r3 = r31.getClass()     // Catch:{ all -> 0x0590 }
            java.lang.String r3 = r3.getSimpleName()     // Catch:{ all -> 0x0590 }
            r2.append(r3)     // Catch:{ all -> 0x0590 }
            java.lang.String r2 = r2.toString()     // Catch:{ all -> 0x0590 }
            r1.addAction(r2)     // Catch:{ all -> 0x0590 }
            com.ut.mini.UTPageStatus r1 = r12.getPageStatus()     // Catch:{ all -> 0x0590 }
            if (r1 == 0) goto L_0x009b
            com.ut.mini.UTPageStatus r1 = com.ut.mini.UTPageStatus.UT_H5_IN_WebView     // Catch:{ all -> 0x0590 }
            com.ut.mini.UTPageStatus r2 = r12.getPageStatus()     // Catch:{ all -> 0x0590 }
            if (r1 != r2) goto L_0x009b
            int r1 = r12.getPageStatusCode()     // Catch:{ all -> 0x0590 }
            if (r10 != r1) goto L_0x0074
            java.util.Map<java.lang.String, java.lang.String> r1 = r7.mBackupNextPageProperties     // Catch:{ all -> 0x0590 }
            r7.mNextPageProperties = r1     // Catch:{ all -> 0x0590 }
            com.ut.mini.UTVariables r1 = com.ut.mini.UTVariables.getInstance()     // Catch:{ all -> 0x0590 }
            com.ut.mini.UTVariables r2 = com.ut.mini.UTVariables.getInstance()     // Catch:{ all -> 0x0590 }
            java.lang.String r2 = r2.getBackupH5Url()     // Catch:{ all -> 0x0590 }
            r1.setH5Url(r2)     // Catch:{ all -> 0x0590 }
        L_0x0074:
            int r1 = r12.getPageStatusCode()     // Catch:{ all -> 0x0590 }
            if (r10 == r1) goto L_0x0080
            boolean r1 = r12.isH5Called()     // Catch:{ all -> 0x0590 }
            if (r1 == 0) goto L_0x009b
        L_0x0080:
            java.lang.String r1 = "pageDisAppear"
            java.lang.Object[] r2 = new java.lang.Object[r10]     // Catch:{ all -> 0x0590 }
            java.lang.String r3 = "UTTracker.PAGE_STATUS_CODE_302 or PageEventObject.isH5Called"
            r2[r11] = r3     // Catch:{ all -> 0x0590 }
            com.alibaba.analytics.utils.Logger.d(r1, r2)     // Catch:{ all -> 0x0590 }
            com.ut.mini.UTPageHitHelper$UTPageStateObject r1 = r30.getOrNewUTPageStateObject(r31)     // Catch:{ all -> 0x0590 }
            if (r1 == 0) goto L_0x0093
            r1.mIsH5Page = r11     // Catch:{ all -> 0x0590 }
        L_0x0093:
            r7._releaseSkipFlagAndH5FlagPageObject(r12)     // Catch:{ all -> 0x0590 }
            r30._clearPageDisAppearContext()     // Catch:{ all -> 0x0590 }
            monitor-exit(r30)
            return
        L_0x009b:
            long r13 = r12.getPageAppearTimestamp()     // Catch:{ all -> 0x0590 }
            long r1 = android.os.SystemClock.elapsedRealtime()     // Catch:{ all -> 0x0590 }
            long r3 = r12.getPageStayTimstamp()     // Catch:{ all -> 0x0590 }
            r5 = 0
            long r5 = r1 - r3
            boolean r1 = r8 instanceof android.app.Activity     // Catch:{ all -> 0x0590 }
            if (r1 == 0) goto L_0x0134
            disPathcherPageChangerEvent(r10, r8)     // Catch:{ all -> 0x0590 }
            boolean r1 = com.alibaba.analytics.utils.Logger.isDebug()     // Catch:{ all -> 0x0590 }
            if (r1 == 0) goto L_0x00f3
            r1 = r8
            android.app.Activity r1 = (android.app.Activity) r1     // Catch:{ all -> 0x0590 }
            android.content.Intent r1 = r1.getIntent()     // Catch:{ all -> 0x0590 }
            if (r1 == 0) goto L_0x00f3
            r1 = r8
            android.app.Activity r1 = (android.app.Activity) r1     // Catch:{ all -> 0x0590 }
            android.content.Intent r1 = r1.getIntent()     // Catch:{ all -> 0x0590 }
            android.net.Uri r1 = r1.getData()     // Catch:{ all -> 0x0590 }
            if (r1 == 0) goto L_0x00f3
            java.lang.String r1 = "pageDisAppear"
            java.lang.Object[] r3 = new java.lang.Object[r10]     // Catch:{ all -> 0x0590 }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x0590 }
            java.lang.String r15 = "uri="
            r4.<init>(r15)     // Catch:{ all -> 0x0590 }
            r15 = r8
            android.app.Activity r15 = (android.app.Activity) r15     // Catch:{ all -> 0x0590 }
            android.content.Intent r15 = r15.getIntent()     // Catch:{ all -> 0x0590 }
            android.net.Uri r15 = r15.getData()     // Catch:{ all -> 0x0590 }
            java.lang.String r15 = r15.toString()     // Catch:{ all -> 0x0590 }
            r4.append(r15)     // Catch:{ all -> 0x0590 }
            java.lang.String r4 = r4.toString()     // Catch:{ all -> 0x0590 }
            r3[r11] = r4     // Catch:{ all -> 0x0590 }
            com.alibaba.analytics.utils.Logger.i(r1, r3)     // Catch:{ all -> 0x0590 }
        L_0x00f3:
            android.net.Uri r1 = r12.getPageUrl()     // Catch:{ all -> 0x0590 }
            if (r1 == 0) goto L_0x00fe
            java.lang.String r1 = r1.toString()     // Catch:{ all -> 0x0590 }
            goto L_0x00ff
        L_0x00fe:
            r1 = 0
        L_0x00ff:
            r3 = r8
            android.app.Activity r3 = (android.app.Activity) r3     // Catch:{ all -> 0x0590 }
            android.content.Intent r3 = r3.getIntent()     // Catch:{ all -> 0x0590 }
            if (r3 == 0) goto L_0x010d
            android.net.Uri r3 = r3.getData()     // Catch:{ all -> 0x0590 }
            goto L_0x010e
        L_0x010d:
            r3 = 0
        L_0x010e:
            if (r3 == 0) goto L_0x0115
            java.lang.String r4 = r3.toString()     // Catch:{ all -> 0x0590 }
            goto L_0x0116
        L_0x0115:
            r4 = 0
        L_0x0116:
            if (r1 == 0) goto L_0x011e
            boolean r15 = r1.equals(r4)     // Catch:{ all -> 0x0590 }
            if (r15 == 0) goto L_0x0126
        L_0x011e:
            if (r4 == 0) goto L_0x0128
            boolean r1 = r4.equals(r1)     // Catch:{ all -> 0x0590 }
            if (r1 != 0) goto L_0x0128
        L_0x0126:
            r1 = 1
            goto L_0x0129
        L_0x0128:
            r1 = 0
        L_0x0129:
            android.net.Uri r4 = r12.getPageUrl()     // Catch:{ all -> 0x0590 }
            if (r4 != 0) goto L_0x0134
            if (r1 == 0) goto L_0x0134
            r12.setPageUrl(r3)     // Catch:{ all -> 0x0590 }
        L_0x0134:
            java.lang.String r1 = r12.getPageName()     // Catch:{ all -> 0x0590 }
            java.lang.String r3 = r12.getRefPage()     // Catch:{ all -> 0x0590 }
            if (r3 == 0) goto L_0x0144
            int r4 = r3.length()     // Catch:{ all -> 0x0590 }
            if (r4 != 0) goto L_0x0146
        L_0x0144:
            java.lang.String r3 = "-"
        L_0x0146:
            java.util.Map<java.lang.String, java.lang.String> r4 = r7.mPageProperties     // Catch:{ all -> 0x0590 }
            if (r4 != 0) goto L_0x014f
            java.util.HashMap r4 = new java.util.HashMap     // Catch:{ all -> 0x0590 }
            r4.<init>()     // Catch:{ all -> 0x0590 }
        L_0x014f:
            boolean r15 = com.ut.mini.extend.UTExtendSwitch.bJTrackExtend     // Catch:{ all -> 0x0590 }
            if (r15 == 0) goto L_0x0213
            boolean r15 = r8 instanceof android.app.Activity     // Catch:{ Throwable -> 0x0213 }
            if (r15 == 0) goto L_0x0213
            r15 = r8
            android.app.Activity r15 = (android.app.Activity) r15     // Catch:{ Throwable -> 0x0213 }
            android.content.Intent r15 = r15.getIntent()     // Catch:{ Throwable -> 0x0213 }
            android.net.Uri r15 = r15.getData()     // Catch:{ Throwable -> 0x0213 }
            if (r15 == 0) goto L_0x0183
            java.lang.String r2 = "JTrack"
            java.lang.Object[] r11 = new java.lang.Object[r10]     // Catch:{ Throwable -> 0x0213 }
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0213 }
            r17 = r1
            java.lang.String r1 = "uri:"
            r10.<init>(r1)     // Catch:{ Throwable -> 0x0215 }
            java.lang.String r1 = r15.toString()     // Catch:{ Throwable -> 0x0215 }
            r10.append(r1)     // Catch:{ Throwable -> 0x0215 }
            java.lang.String r1 = r10.toString()     // Catch:{ Throwable -> 0x0215 }
            r10 = 0
            r11[r10] = r1     // Catch:{ Throwable -> 0x0215 }
            com.alibaba.analytics.utils.Logger.i(r2, r11)     // Catch:{ Throwable -> 0x0215 }
            goto L_0x0185
        L_0x0183:
            r17 = r1
        L_0x0185:
            java.lang.String r1 = r12.getPageName()     // Catch:{ Throwable -> 0x0215 }
            boolean r1 = com.alibaba.analytics.utils.StringUtils.isEmpty(r1)     // Catch:{ Throwable -> 0x0215 }
            if (r1 != 0) goto L_0x01b9
            java.lang.String r1 = r12.getPageName()     // Catch:{ Throwable -> 0x0215 }
            java.util.Map r2 = com.ut.mini.extend.JTrackExtend.getArgsMap(r1, r15)     // Catch:{ Throwable -> 0x0215 }
            java.lang.String r1 = "JTrack"
            r10 = 1
            java.lang.Object[] r11 = new java.lang.Object[r10]     // Catch:{ Throwable -> 0x0215 }
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0215 }
            r18 = r2
            java.lang.String r2 = "getArgsMap by pagename:"
            r10.<init>(r2)     // Catch:{ Throwable -> 0x0215 }
            java.lang.String r2 = r12.getPageName()     // Catch:{ Throwable -> 0x0215 }
            r10.append(r2)     // Catch:{ Throwable -> 0x0215 }
            java.lang.String r2 = r10.toString()     // Catch:{ Throwable -> 0x0215 }
            r10 = 0
            r11[r10] = r2     // Catch:{ Throwable -> 0x0215 }
            com.alibaba.analytics.utils.Logger.i(r1, r11)     // Catch:{ Throwable -> 0x0215 }
            r1 = r18
            goto L_0x01ba
        L_0x01b9:
            r1 = 0
        L_0x01ba:
            if (r1 == 0) goto L_0x01c2
            int r2 = r1.size()     // Catch:{ Throwable -> 0x0215 }
            if (r2 != 0) goto L_0x01ea
        L_0x01c2:
            r1 = r8
            android.app.Activity r1 = (android.app.Activity) r1     // Catch:{ Throwable -> 0x0215 }
            java.util.Map r1 = com.ut.mini.extend.JTrackExtend.getArgsMap(r1, r15)     // Catch:{ Throwable -> 0x0215 }
            java.lang.String r2 = "JTrack"
            r10 = 1
            java.lang.Object[] r11 = new java.lang.Object[r10]     // Catch:{ Throwable -> 0x0215 }
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0215 }
            java.lang.String r15 = "getArgsMap by activity:"
            r10.<init>(r15)     // Catch:{ Throwable -> 0x0215 }
            java.lang.Class r15 = r31.getClass()     // Catch:{ Throwable -> 0x0215 }
            java.lang.String r15 = r15.getName()     // Catch:{ Throwable -> 0x0215 }
            r10.append(r15)     // Catch:{ Throwable -> 0x0215 }
            java.lang.String r10 = r10.toString()     // Catch:{ Throwable -> 0x0215 }
            r15 = 0
            r11[r15] = r10     // Catch:{ Throwable -> 0x0215 }
            com.alibaba.analytics.utils.Logger.i(r2, r11)     // Catch:{ Throwable -> 0x0215 }
        L_0x01ea:
            if (r1 == 0) goto L_0x0215
            int r2 = r1.size()     // Catch:{ Throwable -> 0x0215 }
            if (r2 <= 0) goto L_0x0215
            r4.putAll(r1)     // Catch:{ Throwable -> 0x0215 }
            java.lang.String r2 = "JTrack"
            r10 = 1
            java.lang.Object[] r11 = new java.lang.Object[r10]     // Catch:{ Throwable -> 0x0215 }
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0215 }
            java.lang.String r15 = "ArgsMap:"
            r10.<init>(r15)     // Catch:{ Throwable -> 0x0215 }
            java.lang.String r1 = com.alibaba.analytics.utils.StringUtils.convertMapToString(r1)     // Catch:{ Throwable -> 0x0215 }
            r10.append(r1)     // Catch:{ Throwable -> 0x0215 }
            java.lang.String r1 = r10.toString()     // Catch:{ Throwable -> 0x0215 }
            r10 = 0
            r11[r10] = r1     // Catch:{ Throwable -> 0x0215 }
            com.alibaba.analytics.utils.Logger.i(r2, r11)     // Catch:{ Throwable -> 0x0215 }
            goto L_0x0215
        L_0x0213:
            r17 = r1
        L_0x0215:
            java.util.Map r1 = r12.getPageProperties()     // Catch:{ all -> 0x0590 }
            if (r1 == 0) goto L_0x0222
            java.util.Map r1 = r12.getPageProperties()     // Catch:{ all -> 0x0590 }
            r4.putAll(r1)     // Catch:{ all -> 0x0590 }
        L_0x0222:
            boolean r1 = r8 instanceof com.ut.mini.IUTPageTrack     // Catch:{ all -> 0x0590 }
            if (r1 == 0) goto L_0x0255
            r1 = r8
            com.ut.mini.IUTPageTrack r1 = (com.ut.mini.IUTPageTrack) r1     // Catch:{ all -> 0x0590 }
            java.lang.String r2 = r1.getReferPage()     // Catch:{ all -> 0x0590 }
            boolean r10 = com.alibaba.analytics.utils.StringUtils.isEmpty(r2)     // Catch:{ all -> 0x0590 }
            if (r10 != 0) goto L_0x0234
            r3 = r2
        L_0x0234:
            java.util.Map r2 = r1.getPageProperties()     // Catch:{ all -> 0x0590 }
            if (r2 == 0) goto L_0x0247
            int r10 = r2.size()     // Catch:{ all -> 0x0590 }
            if (r10 <= 0) goto L_0x0247
            java.util.Map<java.lang.String, java.lang.String> r4 = r7.mPageProperties     // Catch:{ all -> 0x0590 }
            r4.putAll(r2)     // Catch:{ all -> 0x0590 }
            java.util.Map<java.lang.String, java.lang.String> r4 = r7.mPageProperties     // Catch:{ all -> 0x0590 }
        L_0x0247:
            java.lang.String r1 = r1.getPageName()     // Catch:{ all -> 0x0590 }
            boolean r2 = com.alibaba.analytics.utils.StringUtils.isEmpty(r1)     // Catch:{ all -> 0x0590 }
            if (r2 != 0) goto L_0x0255
            r11 = r1
            r15 = r3
            r10 = r4
            goto L_0x0259
        L_0x0255:
            r15 = r3
            r10 = r4
            r11 = r17
        L_0x0259:
            java.lang.String r1 = ""
            java.lang.String r2 = ""
            java.lang.String r3 = ""
            android.net.Uri r4 = r12.getPageUrl()     // Catch:{ all -> 0x0590 }
            if (r4 == 0) goto L_0x0361
            r19 = r1
            java.util.HashMap r1 = new java.util.HashMap     // Catch:{ Throwable -> 0x034d }
            r1.<init>()     // Catch:{ Throwable -> 0x034d }
            r20 = r2
            java.lang.String r2 = r7._getSpmByUri(r4)     // Catch:{ Throwable -> 0x034b }
            boolean r16 = com.alibaba.analytics.utils.StringUtils.isEmpty(r2)     // Catch:{ Throwable -> 0x034b }
            if (r16 != 0) goto L_0x02c4
            r21 = r3
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x02c1 }
            r3.<init>()     // Catch:{ Throwable -> 0x02c1 }
            r22 = r5
            java.lang.Class r5 = r31.getClass()     // Catch:{ Throwable -> 0x02be }
            java.lang.String r5 = r5.getSimpleName()     // Catch:{ Throwable -> 0x02be }
            r3.append(r5)     // Catch:{ Throwable -> 0x02be }
            int r5 = r31.hashCode()     // Catch:{ Throwable -> 0x02be }
            r3.append(r5)     // Catch:{ Throwable -> 0x02be }
            java.lang.String r3 = r3.toString()     // Catch:{ Throwable -> 0x02be }
            java.util.Map<java.lang.String, java.lang.String> r5 = r7.mSPMObjectMap     // Catch:{ Throwable -> 0x02be }
            boolean r5 = r5.containsKey(r3)     // Catch:{ Throwable -> 0x02be }
            if (r5 == 0) goto L_0x02ad
            java.util.Map<java.lang.String, java.lang.String> r5 = r7.mSPMObjectMap     // Catch:{ Throwable -> 0x02be }
            java.lang.Object r5 = r5.get(r3)     // Catch:{ Throwable -> 0x02be }
            boolean r5 = r2.equals(r5)     // Catch:{ Throwable -> 0x02be }
            if (r5 == 0) goto L_0x02ad
            r5 = 1
            goto L_0x02ae
        L_0x02ad:
            r5 = 0
        L_0x02ae:
            if (r5 != 0) goto L_0x02c8
            java.lang.String r5 = "spm"
            r1.put(r5, r2)     // Catch:{ Throwable -> 0x02be }
            java.util.Map<java.lang.String, java.lang.String> r5 = r7.mSPMObjectMap     // Catch:{ Throwable -> 0x02be }
            r5.put(r3, r2)     // Catch:{ Throwable -> 0x02be }
            r7._releaseSPMCacheObj(r3)     // Catch:{ Throwable -> 0x02be }
            goto L_0x02c8
        L_0x02be:
            r0 = move-exception
            goto L_0x0354
        L_0x02c1:
            r0 = move-exception
            goto L_0x0352
        L_0x02c4:
            r21 = r3
            r22 = r5
        L_0x02c8:
            java.lang.String r3 = "utparam"
            java.lang.String r3 = r4.getQueryParameter(r3)     // Catch:{ Throwable -> 0x0344 }
            java.lang.String r5 = "scm"
            java.lang.String r5 = r4.getQueryParameter(r5)     // Catch:{ Throwable -> 0x033d }
            boolean r6 = com.alibaba.analytics.utils.StringUtils.isEmpty(r5)     // Catch:{ Throwable -> 0x0334 }
            if (r6 != 0) goto L_0x02ea
            java.lang.String r6 = "scm"
            r1.put(r6, r5)     // Catch:{ Throwable -> 0x02e0 }
            goto L_0x02ea
        L_0x02e0:
            r0 = move-exception
            r1 = r0
            r19 = r2
            r20 = r3
            r21 = r5
            goto L_0x0355
        L_0x02ea:
            java.lang.String r6 = "pg1stepk"
            java.lang.String r6 = r4.getQueryParameter(r6)     // Catch:{ Throwable -> 0x0334 }
            boolean r16 = com.alibaba.analytics.utils.StringUtils.isEmpty(r6)     // Catch:{ Throwable -> 0x0334 }
            if (r16 != 0) goto L_0x0300
            r24 = r2
            java.lang.String r2 = "pg1stepk"
            r1.put(r2, r6)     // Catch:{ Throwable -> 0x02fe }
            goto L_0x0302
        L_0x02fe:
            r0 = move-exception
            goto L_0x0337
        L_0x0300:
            r24 = r2
        L_0x0302:
            java.lang.String r2 = "point"
            java.lang.String r2 = r4.getQueryParameter(r2)     // Catch:{ Throwable -> 0x02fe }
            boolean r2 = com.alibaba.analytics.utils.StringUtils.isEmpty(r2)     // Catch:{ Throwable -> 0x02fe }
            if (r2 != 0) goto L_0x0315
            java.lang.String r2 = "issb"
            java.lang.String r6 = "1"
            r1.put(r2, r6)     // Catch:{ Throwable -> 0x02fe }
        L_0x0315:
            java.lang.String r2 = _getOutsideTTID(r4)     // Catch:{ Throwable -> 0x02fe }
            boolean r6 = com.alibaba.analytics.utils.StringUtils.isEmpty(r2)     // Catch:{ Throwable -> 0x02fe }
            if (r6 != 0) goto L_0x0326
            com.alibaba.analytics.core.ClientVariables r6 = com.alibaba.analytics.core.ClientVariables.getInstance()     // Catch:{ Throwable -> 0x02fe }
            r6.setOutsideTTID(r2)     // Catch:{ Throwable -> 0x02fe }
        L_0x0326:
            int r2 = r1.size()     // Catch:{ Throwable -> 0x02fe }
            if (r2 <= 0) goto L_0x032f
            r10.putAll(r1)     // Catch:{ Throwable -> 0x02fe }
        L_0x032f:
            r6 = r5
            r19 = r24
            r5 = r3
            goto L_0x036d
        L_0x0334:
            r0 = move-exception
            r24 = r2
        L_0x0337:
            r1 = r0
            r20 = r3
            r21 = r5
            goto L_0x0348
        L_0x033d:
            r0 = move-exception
            r24 = r2
            r1 = r0
            r20 = r3
            goto L_0x0348
        L_0x0344:
            r0 = move-exception
            r24 = r2
            r1 = r0
        L_0x0348:
            r19 = r24
            goto L_0x0355
        L_0x034b:
            r0 = move-exception
            goto L_0x0350
        L_0x034d:
            r0 = move-exception
            r20 = r2
        L_0x0350:
            r21 = r3
        L_0x0352:
            r22 = r5
        L_0x0354:
            r1 = r0
        L_0x0355:
            java.lang.String r2 = ""
            r3 = 1
            java.lang.Object[] r5 = new java.lang.Object[r3]     // Catch:{ all -> 0x0590 }
            r3 = 0
            r5[r3] = r1     // Catch:{ all -> 0x0590 }
            com.alibaba.analytics.utils.Logger.d(r2, r5)     // Catch:{ all -> 0x0590 }
            goto L_0x0369
        L_0x0361:
            r19 = r1
            r20 = r2
            r21 = r3
            r22 = r5
        L_0x0369:
            r5 = r20
            r6 = r21
        L_0x036d:
            com.ut.mini.UTPageHitHelper$UTPageStateObject r3 = r30.getOrNewUTPageStateObject(r31)     // Catch:{ all -> 0x0590 }
            if (r3 == 0) goto L_0x0465
            com.ut.mini.UTPageStatus r1 = r12.getPageStatus()     // Catch:{ all -> 0x0590 }
            if (r1 == 0) goto L_0x0398
            com.ut.mini.UTPageStatus r1 = com.ut.mini.UTPageStatus.UT_H5_IN_WebView     // Catch:{ all -> 0x0590 }
            com.ut.mini.UTPageStatus r2 = r12.getPageStatus()     // Catch:{ all -> 0x0590 }
            if (r1 != r2) goto L_0x0398
            boolean r1 = r3.mIsBack     // Catch:{ all -> 0x0590 }
            if (r1 == 0) goto L_0x0388
            r7.clearUTPageStateObject(r10)     // Catch:{ all -> 0x0590 }
        L_0x0388:
            r1 = 0
            java.util.Map r2 = r3.getPageStatMap(r1)     // Catch:{ all -> 0x0590 }
            r10.putAll(r2)     // Catch:{ all -> 0x0590 }
            r9 = r3
            r26 = r13
            r28 = r22
            r13 = r4
            goto L_0x040b
        L_0x0398:
            java.lang.String r1 = r30._getPageEventObjectCacheKey(r31)     // Catch:{ all -> 0x0590 }
            java.lang.String r2 = r7.mLastCacheKey     // Catch:{ all -> 0x0590 }
            boolean r2 = r1.equals(r2)     // Catch:{ all -> 0x0590 }
            boolean r1 = r3.mIsSwitchBackground     // Catch:{ all -> 0x0590 }
            if (r1 != 0) goto L_0x03e9
            java.lang.String r1 = "1"
            r25 = r4
            java.lang.String r4 = "skipbk"
            java.lang.Object r4 = r10.get(r4)     // Catch:{ all -> 0x0590 }
            boolean r1 = r1.equals(r4)     // Catch:{ all -> 0x0590 }
            if (r1 != 0) goto L_0x03be
            boolean r1 = r3.mIsSkipBackForever     // Catch:{ all -> 0x0590 }
            if (r1 != 0) goto L_0x03be
            boolean r1 = r3.mIsSkipBack     // Catch:{ all -> 0x0590 }
            if (r1 == 0) goto L_0x03c3
        L_0x03be:
            r1 = 0
            r3.mIsBack = r1     // Catch:{ all -> 0x0590 }
            r3.mIsSkipBack = r1     // Catch:{ all -> 0x0590 }
        L_0x03c3:
            boolean r1 = r3.mIsBack     // Catch:{ all -> 0x0590 }
            if (r1 == 0) goto L_0x03d7
            boolean r1 = r3.mIsFrame     // Catch:{ all -> 0x0590 }
            if (r1 == 0) goto L_0x03ce
            if (r2 == 0) goto L_0x03ce
            goto L_0x03d7
        L_0x03ce:
            r9 = r3
            r26 = r13
            r28 = r22
            r13 = r25
            r14 = r2
            goto L_0x03f6
        L_0x03d7:
            r1 = r7
            r4 = r2
            r2 = r3
            r9 = r3
            r3 = r10
            r26 = r13
            r13 = r25
            r14 = r4
            r4 = r19
            r28 = r22
            r1.refreshUTPageStateObject(r2, r3, r4, r5, r6)     // Catch:{ all -> 0x0590 }
            goto L_0x03f6
        L_0x03e9:
            r9 = r3
            r26 = r13
            r28 = r22
            r1 = 0
            r14 = r2
            r13 = r4
            r9.mIsBack = r1     // Catch:{ all -> 0x0590 }
            r7.clearUTPageStateObject(r10)     // Catch:{ all -> 0x0590 }
        L_0x03f6:
            boolean r1 = r9.mIsBack     // Catch:{ all -> 0x0590 }
            if (r1 == 0) goto L_0x03fd
            r7.clearUTPageStateObject(r10)     // Catch:{ all -> 0x0590 }
        L_0x03fd:
            java.util.Map r1 = r12.getPageProperties()     // Catch:{ all -> 0x0590 }
            r7.forceSetSpm(r9, r1)     // Catch:{ all -> 0x0590 }
            java.util.Map r1 = r9.getPageStatMap(r14)     // Catch:{ all -> 0x0590 }
            r10.putAll(r1)     // Catch:{ all -> 0x0590 }
        L_0x040b:
            java.lang.String r1 = r30._getPageEventObjectCacheKey(r31)     // Catch:{ all -> 0x0590 }
            r7.setLastCacheKey(r1)     // Catch:{ all -> 0x0590 }
            java.lang.String r1 = r9.mSpmUrl     // Catch:{ all -> 0x0590 }
            r7.setLastCacheKeySpmUrl(r1)     // Catch:{ all -> 0x0590 }
            java.lang.String r1 = r9.mScmUrl     // Catch:{ all -> 0x0590 }
            r7.setLastCacheKeyScmUrl(r1)     // Catch:{ all -> 0x0590 }
            java.lang.String r1 = r9.mUtparamUrl     // Catch:{ all -> 0x0590 }
            r7.setLastCacheKeyUtParam(r1)     // Catch:{ all -> 0x0590 }
            java.lang.String r1 = r9.mUtparamCnt     // Catch:{ all -> 0x0590 }
            r7.setLastCacheKeyUtParamCnt(r1)     // Catch:{ all -> 0x0590 }
            java.lang.String r1 = ""
            r2 = 1
            java.lang.Object[] r3 = new java.lang.Object[r2]     // Catch:{ all -> 0x0590 }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ all -> 0x0590 }
            java.lang.String r4 = "mLastCacheKey:"
            r2.<init>(r4)     // Catch:{ all -> 0x0590 }
            java.lang.String r4 = r7.mLastCacheKey     // Catch:{ all -> 0x0590 }
            r2.append(r4)     // Catch:{ all -> 0x0590 }
            java.lang.String r4 = ",mLastCacheKeySpmUrl:"
            r2.append(r4)     // Catch:{ all -> 0x0590 }
            java.lang.String r4 = r7.mLastCacheKeySpmUrl     // Catch:{ all -> 0x0590 }
            r2.append(r4)     // Catch:{ all -> 0x0590 }
            java.lang.String r4 = ",mLastCacheKeyUtParam:"
            r2.append(r4)     // Catch:{ all -> 0x0590 }
            java.lang.String r4 = r7.mLastCacheKeyUtParam     // Catch:{ all -> 0x0590 }
            r2.append(r4)     // Catch:{ all -> 0x0590 }
            java.lang.String r4 = ",mLastCacheKeyUtParamCnt:"
            r2.append(r4)     // Catch:{ all -> 0x0590 }
            java.lang.String r4 = r7.mLastCacheKeyUtParamCnt     // Catch:{ all -> 0x0590 }
            r2.append(r4)     // Catch:{ all -> 0x0590 }
            java.lang.String r2 = r2.toString()     // Catch:{ all -> 0x0590 }
            r4 = 0
            r3[r4] = r2     // Catch:{ all -> 0x0590 }
            com.alibaba.analytics.utils.Logger.d(r1, r3)     // Catch:{ all -> 0x0590 }
            r1 = 1
            r9.mIsBack = r1     // Catch:{ all -> 0x0590 }
            r9.mIsSwitchBackground = r4     // Catch:{ all -> 0x0590 }
            goto L_0x046a
        L_0x0465:
            r26 = r13
            r28 = r22
            r13 = r4
        L_0x046a:
            com.alibaba.analytics.core.config.UTTPKBiz r1 = com.alibaba.analytics.core.config.UTTPKBiz.getInstance()     // Catch:{ Exception -> 0x0482 }
            android.net.Uri r2 = r12.getPageUrl()     // Catch:{ Exception -> 0x0482 }
            java.lang.String r1 = r1.getTpkString(r2, r10)     // Catch:{ Exception -> 0x0482 }
            boolean r2 = com.alibaba.analytics.utils.StringUtils.isEmpty(r1)     // Catch:{ Exception -> 0x0482 }
            if (r2 != 0) goto L_0x0493
            java.lang.String r2 = "_tpk"
            r10.put(r2, r1)     // Catch:{ Exception -> 0x0482 }
            goto L_0x0493
        L_0x0482:
            r0 = move-exception
            r1 = r0
            java.lang.String r2 = ""
            r3 = 1
            java.lang.Object[] r4 = new java.lang.Object[r3]     // Catch:{ all -> 0x0590 }
            java.lang.String r1 = r1.toString()     // Catch:{ all -> 0x0590 }
            r3 = 0
            r4[r3] = r1     // Catch:{ all -> 0x0590 }
            com.alibaba.analytics.utils.Logger.d(r2, r4)     // Catch:{ all -> 0x0590 }
        L_0x0493:
            java.util.Map r1 = r12.getPageProperties()     // Catch:{ all -> 0x0590 }
            if (r1 == 0) goto L_0x04b1
            java.util.Map r1 = r12.getPageProperties()     // Catch:{ all -> 0x0590 }
            java.lang.String r2 = "_allow_override_value"
            boolean r1 = r1.containsKey(r2)     // Catch:{ all -> 0x0590 }
            if (r1 == 0) goto L_0x04b1
            java.util.Map r1 = r12.getPageProperties()     // Catch:{ all -> 0x0590 }
            r10.putAll(r1)     // Catch:{ all -> 0x0590 }
            java.lang.String r1 = "_allow_override_value"
            r10.remove(r1)     // Catch:{ all -> 0x0590 }
        L_0x04b1:
            java.lang.String r1 = "Page_Webview"
            boolean r1 = r1.equalsIgnoreCase(r11)     // Catch:{ all -> 0x0590 }
            if (r1 == 0) goto L_0x04f4
            if (r13 == 0) goto L_0x04f4
            java.lang.String r1 = r13.toString()     // Catch:{ all -> 0x0590 }
            boolean r2 = android.text.TextUtils.isEmpty(r1)     // Catch:{ all -> 0x0590 }
            if (r2 != 0) goto L_0x04f4
            java.lang.String r2 = "?"
            int r2 = r1.indexOf(r2)     // Catch:{ all -> 0x0590 }
            r3 = -1
            if (r2 == r3) goto L_0x04d4
            r3 = 0
            java.lang.String r2 = r1.substring(r3, r2)     // Catch:{ all -> 0x0590 }
            goto L_0x04d5
        L_0x04d4:
            r2 = r1
        L_0x04d5:
            boolean r3 = android.text.TextUtils.isEmpty(r2)     // Catch:{ all -> 0x0590 }
            if (r3 != 0) goto L_0x04dc
            r11 = r2
        L_0x04dc:
            java.lang.String r3 = ""
            r4 = 4
            java.lang.Object[] r4 = new java.lang.Object[r4]     // Catch:{ all -> 0x0590 }
            java.lang.String r5 = "temp"
            r6 = 0
            r4[r6] = r5     // Catch:{ all -> 0x0590 }
            r5 = 1
            r4[r5] = r1     // Catch:{ all -> 0x0590 }
            r1 = 2
            java.lang.String r5 = "urlForPageName"
            r4[r1] = r5     // Catch:{ all -> 0x0590 }
            r1 = 3
            r4[r1] = r2     // Catch:{ all -> 0x0590 }
            com.alibaba.analytics.utils.Logger.d(r3, r4)     // Catch:{ all -> 0x0590 }
        L_0x04f4:
            com.ut.mini.UTHitBuilders$UTPageHitBuilder r1 = new com.ut.mini.UTHitBuilders$UTPageHitBuilder     // Catch:{ all -> 0x0590 }
            r1.<init>(r11)     // Catch:{ all -> 0x0590 }
            com.ut.mini.UTHitBuilders$UTPageHitBuilder r2 = r1.setReferPage(r15)     // Catch:{ all -> 0x0590 }
            r3 = r28
            com.ut.mini.UTHitBuilders$UTPageHitBuilder r2 = r2.setDurationOnPage(r3)     // Catch:{ all -> 0x0590 }
            r2.setProperties(r10)     // Catch:{ all -> 0x0590 }
            com.alibaba.analytics.core.model.LogField r2 = com.alibaba.analytics.core.model.LogField.RECORD_TIMESTAMP     // Catch:{ all -> 0x0590 }
            java.lang.String r2 = r2.toString()     // Catch:{ all -> 0x0590 }
            r3 = r26
            java.lang.String r3 = java.lang.String.valueOf(r3)     // Catch:{ all -> 0x0590 }
            r1.setProperty(r2, r3)     // Catch:{ all -> 0x0590 }
            java.lang.String r2 = "_priority"
            java.lang.String r3 = "4"
            r1.setProperty(r2, r3)     // Catch:{ all -> 0x0590 }
            com.ut.mini.UTVariables r2 = com.ut.mini.UTVariables.getInstance()     // Catch:{ all -> 0x0590 }
            r2.setRefPage(r11)     // Catch:{ all -> 0x0590 }
            r2 = r32
            if (r2 != 0) goto L_0x052f
            java.lang.NullPointerException r1 = new java.lang.NullPointerException     // Catch:{ all -> 0x0590 }
            java.lang.String r2 = "Tracker instance is null,please init sdk first."
            r1.<init>(r2)     // Catch:{ all -> 0x0590 }
            throw r1     // Catch:{ all -> 0x0590 }
        L_0x052f:
            java.util.Map r1 = r1.build()     // Catch:{ all -> 0x0590 }
            java.util.Map r1 = com.ut.mini.UTPvidHelper.processPagePvid(r1)     // Catch:{ all -> 0x0590 }
            r2.send(r1)     // Catch:{ all -> 0x0590 }
            goto L_0x055d
        L_0x053b:
            java.lang.String r1 = "UT"
            r2 = 1
            java.lang.Object[] r2 = new java.lang.Object[r2]     // Catch:{ all -> 0x0590 }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x0590 }
            java.lang.String r4 = "Please call pageAppear first("
            r3.<init>(r4)     // Catch:{ all -> 0x0590 }
            java.lang.String r4 = _getPageName(r31)     // Catch:{ all -> 0x0590 }
            r3.append(r4)     // Catch:{ all -> 0x0590 }
            java.lang.String r4 = ")."
            r3.append(r4)     // Catch:{ all -> 0x0590 }
            java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x0590 }
            r4 = 0
            r2[r4] = r3     // Catch:{ all -> 0x0590 }
            com.alibaba.analytics.utils.Logger.e(r1, r2)     // Catch:{ all -> 0x0590 }
        L_0x055d:
            boolean r1 = r12.isSkipPage()     // Catch:{ all -> 0x0590 }
            if (r1 == 0) goto L_0x0567
            r7._releaseSkipFlagAndH5FlagPageObject(r12)     // Catch:{ all -> 0x0590 }
            goto L_0x057c
        L_0x0567:
            com.ut.mini.UTPageStatus r1 = r12.getPageStatus()     // Catch:{ all -> 0x0590 }
            if (r1 == 0) goto L_0x0579
            com.ut.mini.UTPageStatus r1 = com.ut.mini.UTPageStatus.UT_H5_IN_WebView     // Catch:{ all -> 0x0590 }
            com.ut.mini.UTPageStatus r2 = r12.getPageStatus()     // Catch:{ all -> 0x0590 }
            if (r1 != r2) goto L_0x0579
            r7._releaseSkipFlagAndH5FlagPageObject(r12)     // Catch:{ all -> 0x0590 }
            goto L_0x057c
        L_0x0579:
            r30._removeUTPageEventObject(r31)     // Catch:{ all -> 0x0590 }
        L_0x057c:
            r30._clearPageDisAppearContext()     // Catch:{ all -> 0x0590 }
            monitor-exit(r30)
            return
        L_0x0581:
            java.lang.String r1 = "pageDisAppear"
            r2 = 1
            java.lang.Object[] r2 = new java.lang.Object[r2]     // Catch:{ all -> 0x0590 }
            java.lang.String r3 = "The page object should not be null"
            r4 = 0
            r2[r4] = r3     // Catch:{ all -> 0x0590 }
            com.alibaba.analytics.utils.Logger.e(r1, r2)     // Catch:{ all -> 0x0590 }
            monitor-exit(r30)
            return
        L_0x0590:
            r0 = move-exception
            r1 = r0
            monitor-exit(r30)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ut.mini.UTPageHitHelper.pageDisAppear(java.lang.Object, com.ut.mini.UTTracker):void");
    }

    private void forceSetSpm(UTPageStateObject uTPageStateObject, Map<String, String> map) {
        if (uTPageStateObject != null && map != null) {
            String str = map.get(FORCE_SPM_CNT);
            if (!TextUtils.isEmpty(str)) {
                uTPageStateObject.mSpmCnt = str;
            }
            String str2 = map.get(FORCE_SPM_URL);
            if (!TextUtils.isEmpty(str2)) {
                uTPageStateObject.mSpmUrl = str2;
            }
        }
    }

    /* access modifiers changed from: 0000 */
    /* JADX WARNING: Removed duplicated region for block: B:62:0x00c5 A[Catch:{ Throwable -> 0x008a }] */
    /* JADX WARNING: Removed duplicated region for block: B:75:0x00f0 A[Catch:{ Throwable -> 0x008a }] */
    /* JADX WARNING: Removed duplicated region for block: B:78:0x00f9 A[Catch:{ Throwable -> 0x008a }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized java.util.Map<java.lang.String, java.lang.String> getPageAllProperties(android.app.Activity r12) {
        /*
            r11 = this;
            monitor-enter(r11)
            r0 = 0
            r1 = 1
            r2 = 0
            if (r12 == 0) goto L_0x010a
            java.lang.String r3 = r11.mCurrentPageCacheKey     // Catch:{ all -> 0x0108 }
            if (r3 != 0) goto L_0x000c
            goto L_0x010a
        L_0x000c:
            com.ut.mini.UTPageHitHelper$UTPageEventObject r3 = r11._getOrNewAUTPageEventObject(r12)     // Catch:{ all -> 0x0108 }
            boolean r4 = r3.isPageAppearCalled()     // Catch:{ all -> 0x0108 }
            if (r4 != 0) goto L_0x0023
            java.lang.String r12 = "getPagePropertiesWithSpmAndUtparam"
            java.lang.Object[] r1 = new java.lang.Object[r1]     // Catch:{ all -> 0x0108 }
            java.lang.String r3 = "activity isPageAppearCalled is false"
            r1[r2] = r3     // Catch:{ all -> 0x0108 }
            com.alibaba.analytics.utils.Logger.e(r12, r1)     // Catch:{ all -> 0x0108 }
            monitor-exit(r11)
            return r0
        L_0x0023:
            com.ut.mini.UTPageStatus r4 = r3.getPageStatus()     // Catch:{ all -> 0x0108 }
            if (r4 == 0) goto L_0x003e
            com.ut.mini.UTPageStatus r4 = com.ut.mini.UTPageStatus.UT_H5_IN_WebView     // Catch:{ all -> 0x0108 }
            com.ut.mini.UTPageStatus r5 = r3.getPageStatus()     // Catch:{ all -> 0x0108 }
            if (r4 != r5) goto L_0x003e
            java.lang.String r12 = "getPagePropertiesWithSpmAndUtparam"
            java.lang.Object[] r1 = new java.lang.Object[r1]     // Catch:{ all -> 0x0108 }
            java.lang.String r3 = "activity is UT_H5_IN_WebView"
            r1[r2] = r3     // Catch:{ all -> 0x0108 }
            com.alibaba.analytics.utils.Logger.e(r12, r1)     // Catch:{ all -> 0x0108 }
            monitor-exit(r11)
            return r0
        L_0x003e:
            com.ut.mini.UTPageHitHelper$UTPageStateObject r4 = r11.getOrNewUTPageStateObject(r12)     // Catch:{ all -> 0x0108 }
            com.ut.mini.UTPageHitHelper$UTPageStateObject r4 = r11.copyUTPageStateObject(r4)     // Catch:{ all -> 0x0108 }
            if (r4 != 0) goto L_0x0055
            java.lang.String r12 = "getPagePropertiesWithSpmAndUtparam"
            java.lang.Object[] r1 = new java.lang.Object[r1]     // Catch:{ all -> 0x0108 }
            java.lang.String r3 = "getOrNewUTPageStateObject is null"
            r1[r2] = r3     // Catch:{ all -> 0x0108 }
            com.alibaba.analytics.utils.Logger.e(r12, r1)     // Catch:{ all -> 0x0108 }
            monitor-exit(r11)
            return r0
        L_0x0055:
            java.util.HashMap r0 = new java.util.HashMap     // Catch:{ all -> 0x0108 }
            r0.<init>()     // Catch:{ all -> 0x0108 }
            java.util.Map<java.lang.String, java.lang.String> r1 = r11.mPageProperties     // Catch:{ all -> 0x0108 }
            r0.putAll(r1)     // Catch:{ all -> 0x0108 }
            java.util.Map r1 = r3.getPageProperties()     // Catch:{ all -> 0x0108 }
            if (r1 == 0) goto L_0x006c
            java.util.Map r1 = r3.getPageProperties()     // Catch:{ all -> 0x0108 }
            r0.putAll(r1)     // Catch:{ all -> 0x0108 }
        L_0x006c:
            java.lang.String r1 = ""
            java.lang.String r5 = ""
            java.lang.String r6 = ""
            android.net.Uri r3 = r3.getPageUrl()     // Catch:{ all -> 0x0108 }
            if (r3 != 0) goto L_0x0082
            android.content.Intent r7 = r12.getIntent()     // Catch:{ all -> 0x0108 }
            if (r7 == 0) goto L_0x0082
            android.net.Uri r3 = r7.getData()     // Catch:{ all -> 0x0108 }
        L_0x0082:
            if (r3 == 0) goto L_0x00b4
            java.lang.String r7 = r11._getSpmByUri(r3)     // Catch:{ Throwable -> 0x008a }
            r1 = r7
            goto L_0x0092
        L_0x008a:
            r7 = move-exception
            java.lang.String r8 = "getPagePropertiesWithSpmAndUtparam"
            java.lang.Object[] r9 = new java.lang.Object[r2]     // Catch:{ all -> 0x0108 }
            com.alibaba.analytics.utils.Logger.e(r8, r7, r9)     // Catch:{ all -> 0x0108 }
        L_0x0092:
            java.lang.String r7 = "utparam"
            java.lang.String r7 = r3.getQueryParameter(r7)     // Catch:{ Throwable -> 0x009a }
            r5 = r7
            goto L_0x00a2
        L_0x009a:
            r7 = move-exception
            java.lang.String r8 = "getPagePropertiesWithSpmAndUtparam"
            java.lang.Object[] r9 = new java.lang.Object[r2]     // Catch:{ all -> 0x0108 }
            com.alibaba.analytics.utils.Logger.e(r8, r7, r9)     // Catch:{ all -> 0x0108 }
        L_0x00a2:
            java.lang.String r7 = "scm"
            java.lang.String r3 = r3.getQueryParameter(r7)     // Catch:{ Throwable -> 0x00ac }
            r8 = r1
            r10 = r3
            r9 = r5
            goto L_0x00b7
        L_0x00ac:
            r3 = move-exception
            java.lang.String r7 = "getPagePropertiesWithSpmAndUtparam"
            java.lang.Object[] r8 = new java.lang.Object[r2]     // Catch:{ all -> 0x0108 }
            com.alibaba.analytics.utils.Logger.e(r7, r3, r8)     // Catch:{ all -> 0x0108 }
        L_0x00b4:
            r8 = r1
            r9 = r5
            r10 = r6
        L_0x00b7:
            java.lang.String r12 = r11._getPageEventObjectCacheKey(r12)     // Catch:{ all -> 0x0108 }
            java.lang.String r1 = r11.mLastCacheKey     // Catch:{ all -> 0x0108 }
            boolean r12 = r12.equals(r1)     // Catch:{ all -> 0x0108 }
            boolean r1 = r4.mIsSwitchBackground     // Catch:{ all -> 0x0108 }
            if (r1 != 0) goto L_0x00f0
            java.lang.String r1 = "1"
            java.lang.String r3 = "skipbk"
            java.lang.Object r3 = r0.get(r3)     // Catch:{ all -> 0x0108 }
            boolean r1 = r1.equals(r3)     // Catch:{ all -> 0x0108 }
            if (r1 != 0) goto L_0x00db
            boolean r1 = r4.mIsSkipBackForever     // Catch:{ all -> 0x0108 }
            if (r1 != 0) goto L_0x00db
            boolean r1 = r4.mIsSkipBack     // Catch:{ all -> 0x0108 }
            if (r1 == 0) goto L_0x00df
        L_0x00db:
            r4.mIsBack = r2     // Catch:{ all -> 0x0108 }
            r4.mIsSkipBack = r2     // Catch:{ all -> 0x0108 }
        L_0x00df:
            boolean r1 = r4.mIsBack     // Catch:{ all -> 0x0108 }
            if (r1 == 0) goto L_0x00e9
            boolean r1 = r4.mIsFrame     // Catch:{ all -> 0x0108 }
            if (r1 == 0) goto L_0x00f5
            if (r12 == 0) goto L_0x00f5
        L_0x00e9:
            r5 = r11
            r6 = r4
            r7 = r0
            r5.refreshUTPageStateObject(r6, r7, r8, r9, r10)     // Catch:{ all -> 0x0108 }
            goto L_0x00f5
        L_0x00f0:
            r4.mIsBack = r2     // Catch:{ all -> 0x0108 }
            r11.clearUTPageStateObject(r0)     // Catch:{ all -> 0x0108 }
        L_0x00f5:
            boolean r1 = r4.mIsBack     // Catch:{ all -> 0x0108 }
            if (r1 == 0) goto L_0x00fc
            r11.clearUTPageStateObject(r0)     // Catch:{ all -> 0x0108 }
        L_0x00fc:
            r11.forceSetSpm(r4, r0)     // Catch:{ all -> 0x0108 }
            java.util.Map r12 = r4.getPageStatMap(r12)     // Catch:{ all -> 0x0108 }
            r0.putAll(r12)     // Catch:{ all -> 0x0108 }
            monitor-exit(r11)
            return r0
        L_0x0108:
            r12 = move-exception
            goto L_0x0117
        L_0x010a:
            java.lang.String r12 = "getPagePropertiesWithSpmAndUtparam"
            java.lang.Object[] r1 = new java.lang.Object[r1]     // Catch:{ all -> 0x0108 }
            java.lang.String r3 = "activity or CurrentPageCacheKey is null"
            r1[r2] = r3     // Catch:{ all -> 0x0108 }
            com.alibaba.analytics.utils.Logger.e(r12, r1)     // Catch:{ all -> 0x0108 }
            monitor-exit(r11)
            return r0
        L_0x0117:
            monitor-exit(r11)
            throw r12
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ut.mini.UTPageHitHelper.getPageAllProperties(android.app.Activity):java.util.Map");
    }

    public static Map<String, String> encodeUtParam(Map<String, String> map) {
        if (map == null) {
            return map;
        }
        try {
            String str = map.get(UTPARAM_CNT);
            if (!TextUtils.isEmpty(str)) {
                map.put(UTPARAM_CNT, URLEncoder.encode(str));
            }
        } catch (Throwable th) {
            Logger.e(null, th, new Object[0]);
        }
        try {
            String str2 = map.get(UTPARAM_URL);
            if (!TextUtils.isEmpty(str2)) {
                map.put(UTPARAM_URL, URLEncoder.encode(str2));
            }
        } catch (Throwable th2) {
            Logger.e(null, th2, new Object[0]);
        }
        try {
            String str3 = map.get("utparam-pre");
            if (!TextUtils.isEmpty(str3)) {
                map.put("utparam-pre", URLEncoder.encode(str3));
            }
        } catch (Throwable th3) {
            Logger.e(null, th3, new Object[0]);
        }
        return map;
    }

    /* access modifiers changed from: 0000 */
    /* JADX WARNING: Can't wrap try/catch for region: R(8:14|15|16|(2:18|(1:20))|(3:22|23|24)|25|26|(2:28|29)(4:30|31|(2:33|(5:35|(1:39)|40|(1:43)|(2:45|(1:47)(2:48|(1:50)(1:51)))(1:52))(1:53))|(1:55))) */
    /* JADX WARNING: Code restructure failed: missing block: B:57:0x00a2, code lost:
        return r1;
     */
    /* JADX WARNING: Missing exception handler attribute for start block: B:25:0x0039 */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x003f A[DONT_GENERATE] */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x0041 A[SYNTHETIC, Splitter:B:30:0x0041] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized java.lang.String getPageSpmUrl(android.app.Activity r6) {
        /*
            r5 = this;
            monitor-enter(r5)
            if (r6 != 0) goto L_0x000a
            java.lang.String r6 = ""
            monitor-exit(r5)
            return r6
        L_0x0007:
            r6 = move-exception
            goto L_0x00a3
        L_0x000a:
            com.ut.mini.UTPageHitHelper$UTPageEventObject r0 = r5._getOrNewAUTPageEventObject(r6)     // Catch:{ all -> 0x0007 }
            com.ut.mini.UTPageStatus r1 = r0.getPageStatus()     // Catch:{ all -> 0x0007 }
            if (r1 == 0) goto L_0x0020
            com.ut.mini.UTPageStatus r1 = com.ut.mini.UTPageStatus.UT_H5_IN_WebView     // Catch:{ all -> 0x0007 }
            com.ut.mini.UTPageStatus r2 = r0.getPageStatus()     // Catch:{ all -> 0x0007 }
            if (r1 != r2) goto L_0x0020
            java.lang.String r6 = ""
            monitor-exit(r5)
            return r6
        L_0x0020:
            java.lang.String r1 = ""
            android.net.Uri r2 = r0.getPageUrl()     // Catch:{ all -> 0x0007 }
            if (r2 != 0) goto L_0x0032
            android.content.Intent r3 = r6.getIntent()     // Catch:{ all -> 0x0007 }
            if (r3 == 0) goto L_0x0032
            android.net.Uri r2 = r3.getData()     // Catch:{ all -> 0x0007 }
        L_0x0032:
            if (r2 == 0) goto L_0x0039
            java.lang.String r2 = r5._getSpmByUri(r2)     // Catch:{ Throwable -> 0x0039 }
            r1 = r2
        L_0x0039:
            boolean r2 = android.text.TextUtils.isEmpty(r1)     // Catch:{ all -> 0x0007 }
            if (r2 != 0) goto L_0x0041
            monitor-exit(r5)
            return r1
        L_0x0041:
            com.ut.mini.UTPageHitHelper$UTPageStateObject r2 = r5.getOrNewUTPageStateObject(r6)     // Catch:{ all -> 0x0007 }
            if (r2 == 0) goto L_0x009d
            boolean r1 = r2.mIsBack     // Catch:{ all -> 0x0007 }
            boolean r3 = r2.mIsSwitchBackground     // Catch:{ all -> 0x0007 }
            if (r3 != 0) goto L_0x009b
            boolean r3 = r2.mIsSkipBackForever     // Catch:{ all -> 0x0007 }
            r4 = 0
            if (r3 != 0) goto L_0x0056
            boolean r3 = r2.mIsSkipBack     // Catch:{ all -> 0x0007 }
            if (r3 == 0) goto L_0x0057
        L_0x0056:
            r1 = 0
        L_0x0057:
            java.lang.String r6 = r5._getPageEventObjectCacheKey(r6)     // Catch:{ all -> 0x0007 }
            java.lang.String r3 = r5.mLastCacheKey     // Catch:{ all -> 0x0007 }
            boolean r6 = r6.equals(r3)     // Catch:{ all -> 0x0007 }
            boolean r3 = r2.mIsFrame     // Catch:{ all -> 0x0007 }
            if (r3 == 0) goto L_0x0068
            if (r6 == 0) goto L_0x0068
            r1 = 0
        L_0x0068:
            if (r1 != 0) goto L_0x0098
            java.util.Map r6 = r0.getPageProperties()     // Catch:{ all -> 0x0007 }
            java.lang.String r0 = "spm-url"
            java.lang.Object r0 = r6.get(r0)     // Catch:{ all -> 0x0007 }
            r1 = r0
            java.lang.String r1 = (java.lang.String) r1     // Catch:{ all -> 0x0007 }
            java.lang.String r0 = "spm_url"
            java.lang.Object r0 = r6.get(r0)     // Catch:{ all -> 0x0007 }
            java.lang.String r0 = (java.lang.String) r0     // Catch:{ all -> 0x0007 }
            boolean r2 = android.text.TextUtils.isEmpty(r1)     // Catch:{ all -> 0x0007 }
            if (r2 != 0) goto L_0x0086
            goto L_0x009d
        L_0x0086:
            boolean r1 = android.text.TextUtils.isEmpty(r0)     // Catch:{ all -> 0x0007 }
            if (r1 != 0) goto L_0x008e
            r1 = r0
            goto L_0x009d
        L_0x008e:
            java.lang.String r0 = "spm"
            java.lang.Object r6 = r6.get(r0)     // Catch:{ all -> 0x0007 }
            r1 = r6
            java.lang.String r1 = (java.lang.String) r1     // Catch:{ all -> 0x0007 }
            goto L_0x009d
        L_0x0098:
            java.lang.String r1 = r2.mSpmUrl     // Catch:{ all -> 0x0007 }
            goto L_0x009d
        L_0x009b:
            java.lang.String r1 = r2.mSpmUrl     // Catch:{ all -> 0x0007 }
        L_0x009d:
            if (r1 != 0) goto L_0x00a1
            java.lang.String r1 = ""
        L_0x00a1:
            monitor-exit(r5)
            return r1
        L_0x00a3:
            monitor-exit(r5)
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ut.mini.UTPageHitHelper.getPageSpmUrl(android.app.Activity):java.lang.String");
    }

    /* access modifiers changed from: 0000 */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x0060, code lost:
        return r0;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized java.lang.String getPageSpmPre(android.app.Activity r6) {
        /*
            r5 = this;
            monitor-enter(r5)
            if (r6 != 0) goto L_0x0009
            java.lang.String r6 = ""
            monitor-exit(r5)
            return r6
        L_0x0007:
            r6 = move-exception
            goto L_0x0061
        L_0x0009:
            com.ut.mini.UTPageHitHelper$UTPageEventObject r0 = r5._getOrNewAUTPageEventObject(r6)     // Catch:{ all -> 0x0007 }
            com.ut.mini.UTPageStatus r1 = r0.getPageStatus()     // Catch:{ all -> 0x0007 }
            if (r1 == 0) goto L_0x001f
            com.ut.mini.UTPageStatus r1 = com.ut.mini.UTPageStatus.UT_H5_IN_WebView     // Catch:{ all -> 0x0007 }
            com.ut.mini.UTPageStatus r0 = r0.getPageStatus()     // Catch:{ all -> 0x0007 }
            if (r1 != r0) goto L_0x001f
            java.lang.String r6 = ""
            monitor-exit(r5)
            return r6
        L_0x001f:
            java.lang.String r0 = ""
            com.ut.mini.UTPageHitHelper$UTPageStateObject r1 = r5.getOrNewUTPageStateObject(r6)     // Catch:{ all -> 0x0007 }
            if (r1 == 0) goto L_0x005b
            boolean r2 = r1.mIsBack     // Catch:{ all -> 0x0007 }
            boolean r3 = r1.mIsSwitchBackground     // Catch:{ all -> 0x0007 }
            if (r3 != 0) goto L_0x0059
            boolean r3 = r1.mIsSkipBackForever     // Catch:{ all -> 0x0007 }
            r4 = 0
            if (r3 != 0) goto L_0x0036
            boolean r3 = r1.mIsSkipBack     // Catch:{ all -> 0x0007 }
            if (r3 == 0) goto L_0x0037
        L_0x0036:
            r2 = 0
        L_0x0037:
            java.lang.String r6 = r5._getPageEventObjectCacheKey(r6)     // Catch:{ all -> 0x0007 }
            java.lang.String r3 = r5.mLastCacheKey     // Catch:{ all -> 0x0007 }
            boolean r6 = r6.equals(r3)     // Catch:{ all -> 0x0007 }
            boolean r3 = r1.mIsFrame     // Catch:{ all -> 0x0007 }
            if (r3 == 0) goto L_0x0048
            if (r6 == 0) goto L_0x0048
            r2 = 0
        L_0x0048:
            if (r2 != 0) goto L_0x0055
            java.lang.String r6 = r5.mLastCacheKey     // Catch:{ all -> 0x0007 }
            boolean r6 = android.text.TextUtils.isEmpty(r6)     // Catch:{ all -> 0x0007 }
            if (r6 != 0) goto L_0x005b
            java.lang.String r0 = r5.mLastCacheKeySpmUrl     // Catch:{ all -> 0x0007 }
            goto L_0x005b
        L_0x0055:
            java.lang.String r6 = r1.mSpmPre     // Catch:{ all -> 0x0007 }
            r0 = r6
            goto L_0x005b
        L_0x0059:
            java.lang.String r0 = r1.mSpmPre     // Catch:{ all -> 0x0007 }
        L_0x005b:
            if (r0 != 0) goto L_0x005f
            java.lang.String r0 = ""
        L_0x005f:
            monitor-exit(r5)
            return r0
        L_0x0061:
            monitor-exit(r5)
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ut.mini.UTPageHitHelper.getPageSpmPre(android.app.Activity):java.lang.String");
    }

    /* access modifiers changed from: 0000 */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x0060, code lost:
        return r0;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized java.lang.String getPageScmPre(android.app.Activity r6) {
        /*
            r5 = this;
            monitor-enter(r5)
            if (r6 != 0) goto L_0x0009
            java.lang.String r6 = ""
            monitor-exit(r5)
            return r6
        L_0x0007:
            r6 = move-exception
            goto L_0x0061
        L_0x0009:
            com.ut.mini.UTPageHitHelper$UTPageEventObject r0 = r5._getOrNewAUTPageEventObject(r6)     // Catch:{ all -> 0x0007 }
            com.ut.mini.UTPageStatus r1 = r0.getPageStatus()     // Catch:{ all -> 0x0007 }
            if (r1 == 0) goto L_0x001f
            com.ut.mini.UTPageStatus r1 = com.ut.mini.UTPageStatus.UT_H5_IN_WebView     // Catch:{ all -> 0x0007 }
            com.ut.mini.UTPageStatus r0 = r0.getPageStatus()     // Catch:{ all -> 0x0007 }
            if (r1 != r0) goto L_0x001f
            java.lang.String r6 = ""
            monitor-exit(r5)
            return r6
        L_0x001f:
            java.lang.String r0 = ""
            com.ut.mini.UTPageHitHelper$UTPageStateObject r1 = r5.getOrNewUTPageStateObject(r6)     // Catch:{ all -> 0x0007 }
            if (r1 == 0) goto L_0x005b
            boolean r2 = r1.mIsBack     // Catch:{ all -> 0x0007 }
            boolean r3 = r1.mIsSwitchBackground     // Catch:{ all -> 0x0007 }
            if (r3 != 0) goto L_0x0059
            boolean r3 = r1.mIsSkipBackForever     // Catch:{ all -> 0x0007 }
            r4 = 0
            if (r3 != 0) goto L_0x0036
            boolean r3 = r1.mIsSkipBack     // Catch:{ all -> 0x0007 }
            if (r3 == 0) goto L_0x0037
        L_0x0036:
            r2 = 0
        L_0x0037:
            java.lang.String r6 = r5._getPageEventObjectCacheKey(r6)     // Catch:{ all -> 0x0007 }
            java.lang.String r3 = r5.mLastCacheKey     // Catch:{ all -> 0x0007 }
            boolean r6 = r6.equals(r3)     // Catch:{ all -> 0x0007 }
            boolean r3 = r1.mIsFrame     // Catch:{ all -> 0x0007 }
            if (r3 == 0) goto L_0x0048
            if (r6 == 0) goto L_0x0048
            r2 = 0
        L_0x0048:
            if (r2 != 0) goto L_0x0055
            java.lang.String r6 = r5.mLastCacheKey     // Catch:{ all -> 0x0007 }
            boolean r6 = android.text.TextUtils.isEmpty(r6)     // Catch:{ all -> 0x0007 }
            if (r6 != 0) goto L_0x005b
            java.lang.String r0 = r5.mLastCacheKeyScmUrl     // Catch:{ all -> 0x0007 }
            goto L_0x005b
        L_0x0055:
            java.lang.String r6 = r1.mScmPre     // Catch:{ all -> 0x0007 }
            r0 = r6
            goto L_0x005b
        L_0x0059:
            java.lang.String r0 = r1.mScmPre     // Catch:{ all -> 0x0007 }
        L_0x005b:
            if (r0 != 0) goto L_0x005f
            java.lang.String r0 = ""
        L_0x005f:
            monitor-exit(r5)
            return r0
        L_0x0061:
            monitor-exit(r5)
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ut.mini.UTPageHitHelper.getPageScmPre(android.app.Activity):java.lang.String");
    }

    @Deprecated
    public synchronized void pageDisAppear(Object obj) {
        pageDisAppear(obj, UTAnalytics.getInstance().getDefaultTracker());
    }

    private static String _getOutsideTTID(Uri uri) {
        if (uri != null) {
            List<String> queryParameters = uri.getQueryParameters("ttid");
            if (queryParameters != null) {
                for (String next : queryParameters) {
                    if (!next.contains(AUScreenAdaptTool.PREFIX_ID) && !next.contains("%40")) {
                        return next;
                    }
                }
            }
        }
        return null;
    }

    private static String _getPageName(Object obj) {
        String simpleName = obj.getClass().getSimpleName();
        return (simpleName == null || !simpleName.toLowerCase().endsWith(WidgetType.ACTIVITY)) ? simpleName : simpleName.substring(0, simpleName.length() - 8);
    }

    public void pageDestroyed(Activity activity) {
        String _getPageEventObjectCacheKey = _getPageEventObjectCacheKey(activity);
        if (this.mPageStateObjects.containsKey(_getPageEventObjectCacheKey)) {
            this.mPageStateObjects.remove(_getPageEventObjectCacheKey);
        }
        if (this.mClearUTPageStateObjectList.contains(_getPageEventObjectCacheKey)) {
            this.mClearUTPageStateObjectList.remove(_getPageEventObjectCacheKey);
        }
        _releaseUTPageStateObject();
    }

    public void pageSwitchBackground() {
        if (this.mPageStateObjects.containsKey(this.mLastCacheKey)) {
            this.mPageStateObjects.get(this.mLastCacheKey).mIsSwitchBackground = true;
        }
    }

    public synchronized UTPageStateObject getOrNewUTPageStateObject(Object obj) {
        try {
            if (!(obj instanceof Activity)) {
                return null;
            }
            String _getPageEventObjectCacheKey = _getPageEventObjectCacheKey(obj);
            if (!this.mClearUTPageStateObjectList.contains(_getPageEventObjectCacheKey)) {
                this.mClearUTPageStateObjectList.add(_getPageEventObjectCacheKey);
            }
            if (this.mPageStateObjects.containsKey(_getPageEventObjectCacheKey)) {
                return this.mPageStateObjects.get(_getPageEventObjectCacheKey);
            }
            UTPageStateObject uTPageStateObject = new UTPageStateObject();
            this.mPageStateObjects.put(_getPageEventObjectCacheKey, uTPageStateObject);
            return uTPageStateObject;
        }
    }

    private synchronized void _releaseUTPageStateObject() {
        if (this.mClearUTPageStateObjectList.size() > 100) {
            for (int i = 0; i < 50; i++) {
                String poll = this.mClearUTPageStateObjectList.poll();
                if (poll != null && this.mPageStateObjects.containsKey(poll)) {
                    this.mPageStateObjects.remove(poll);
                }
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:14:0x0037  */
    /* JADX WARNING: Removed duplicated region for block: B:22:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.lang.String _getSpmByUri(android.net.Uri r7) throws java.lang.Exception {
        /*
            r6 = this;
            java.lang.String r0 = "spm"
            java.lang.String r0 = r7.getQueryParameter(r0)
            boolean r1 = com.alibaba.analytics.utils.StringUtils.isEmpty(r0)
            r2 = 0
            if (r1 == 0) goto L_0x0031
            java.lang.String r1 = r7.toString()     // Catch:{ Exception -> 0x0029 }
            java.lang.String r3 = "UTF-8"
            java.lang.String r1 = java.net.URLDecoder.decode(r1, r3)     // Catch:{ Exception -> 0x0029 }
            android.net.Uri r1 = android.net.Uri.parse(r1)     // Catch:{ Exception -> 0x0029 }
            java.lang.String r7 = "spm"
            java.lang.String r7 = r1.getQueryParameter(r7)     // Catch:{ Exception -> 0x0024 }
            r0 = r7
            r7 = r1
            goto L_0x0031
        L_0x0024:
            r7 = move-exception
            r5 = r1
            r1 = r7
            r7 = r5
            goto L_0x002a
        L_0x0029:
            r1 = move-exception
        L_0x002a:
            java.lang.String r3 = ""
            java.lang.Object[] r4 = new java.lang.Object[r2]
            com.alibaba.analytics.utils.Logger.w(r3, r1, r4)
        L_0x0031:
            boolean r1 = com.alibaba.analytics.utils.StringUtils.isEmpty(r0)
            if (r1 == 0) goto L_0x0061
            java.lang.String r0 = "spm_url"
            java.lang.String r0 = r7.getQueryParameter(r0)
            boolean r1 = com.alibaba.analytics.utils.StringUtils.isEmpty(r0)
            if (r1 == 0) goto L_0x0061
            java.lang.String r7 = r7.toString()     // Catch:{ Exception -> 0x0059 }
            java.lang.String r1 = "UTF-8"
            java.lang.String r7 = java.net.URLDecoder.decode(r7, r1)     // Catch:{ Exception -> 0x0059 }
            android.net.Uri r7 = android.net.Uri.parse(r7)     // Catch:{ Exception -> 0x0059 }
            java.lang.String r1 = "spm_url"
            java.lang.String r7 = r7.getQueryParameter(r1)     // Catch:{ Exception -> 0x0059 }
            r0 = r7
            goto L_0x0061
        L_0x0059:
            r7 = move-exception
            java.lang.String r1 = ""
            java.lang.Object[] r2 = new java.lang.Object[r2]
            com.alibaba.analytics.utils.Logger.w(r1, r7, r2)
        L_0x0061:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ut.mini.UTPageHitHelper._getSpmByUri(android.net.Uri):java.lang.String");
    }

    private void clearUTPageStateObject(Map<String, String> map) {
        if (map != null && map.size() > 0) {
            map.remove("spm-cnt");
            map.remove("spm-url");
            map.remove("spm-pre");
            map.remove(UTPARAM_CNT);
            map.remove(UTPARAM_URL);
            map.remove("utparam-pre");
            map.remove("scm-pre");
        }
    }

    private void refreshUTPageStateObject(UTPageStateObject uTPageStateObject, Map<String, String> map, String str, String str2, String str3) {
        String str4 = map.get("spm-cnt");
        if (!TextUtils.isEmpty(str4)) {
            uTPageStateObject.mSpmCnt = str4;
        } else {
            uTPageStateObject.mSpmCnt = map.get("spm_cnt");
        }
        if (!TextUtils.isEmpty(str)) {
            uTPageStateObject.mSpmUrl = str;
        } else {
            String str5 = map.get("spm-url");
            String str6 = map.get("spm_url");
            if (!TextUtils.isEmpty(str5)) {
                uTPageStateObject.mSpmUrl = str5;
            } else if (!TextUtils.isEmpty(str6)) {
                uTPageStateObject.mSpmUrl = str6;
            } else {
                uTPageStateObject.mSpmUrl = map.get("spm");
            }
        }
        if (!TextUtils.isEmpty(this.mLastCacheKey)) {
            uTPageStateObject.mSpmPre = this.mLastCacheKeySpmUrl;
        } else {
            uTPageStateObject.mSpmPre = "";
        }
        if (!TextUtils.isEmpty(str3)) {
            uTPageStateObject.mScmUrl = str3;
        } else {
            uTPageStateObject.mScmUrl = map.get("scm");
        }
        if (!TextUtils.isEmpty(this.mLastCacheKey)) {
            uTPageStateObject.mScmPre = this.mLastCacheKeyScmUrl;
        } else {
            uTPageStateObject.mScmPre = "";
        }
        String str7 = map.get(UTPARAM_CNT);
        if (!TextUtils.isEmpty(str7)) {
            uTPageStateObject.mUtparamCnt = str7;
        } else {
            uTPageStateObject.mUtparamCnt = "";
        }
        String str8 = "";
        if (!TextUtils.isEmpty(this.mLastCacheKey)) {
            str8 = this.mLastCacheKeyUtParamCnt;
        }
        uTPageStateObject.mUtparamUrl = refreshUtParam(str2, refreshUtParam(map.get(UTPARAM_URL), str8));
        if (!TextUtils.isEmpty(this.mLastCacheKey)) {
            uTPageStateObject.mUtparamPre = this.mLastCacheKeyUtParam;
        } else {
            uTPageStateObject.mUtparamPre = "";
        }
    }

    public String refreshUtParam(String str, String str2) {
        try {
            if (TextUtils.isEmpty(str)) {
                return str2;
            }
            Map<String, Object> parseJsonToMap = parseJsonToMap(str);
            if (parseJsonToMap != null) {
                if (parseJsonToMap.size() > 0) {
                    if (TextUtils.isEmpty(str2)) {
                        return str;
                    }
                    Map<String, Object> parseJsonToMap2 = parseJsonToMap(str2);
                    if (parseJsonToMap2 != null) {
                        if (parseJsonToMap2.size() > 0) {
                            parseJsonToMap2.putAll(parseJsonToMap);
                            return JSON.toJSONString(parseJsonToMap2);
                        }
                    }
                    return str;
                }
            }
            return str2;
        } catch (Exception e) {
            Logger.d((String) "", e);
            return "";
        }
    }

    private Map<String, Object> parseJsonToMap(String str) {
        try {
            return (Map) JSON.parseObject(str, Map.class);
        } catch (Exception unused) {
            return null;
        }
    }
}
