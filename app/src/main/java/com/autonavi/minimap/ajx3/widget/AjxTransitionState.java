package com.autonavi.minimap.ajx3.widget;

import com.autonavi.minimap.ajx3.Ajx;
import com.autonavi.minimap.ajx3.JsRunInfo;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.widget.AjxView.AjxContextLifecycleCallback;

public class AjxTransitionState {
    private static OnPageLifeCircleListener pageLifeCircleListener;
    private AjxEventHandler mAjxEventHandler;
    /* access modifiers changed from: private */
    public AjxView mAjxView;
    private boolean mUrlExist = true;

    public interface OnPageLifeCircleListener {
        void onPageHide(boolean z);

        void onPageShow(boolean z, Object obj);
    }

    class PageShowAjxContextLifecycleCallback implements AjxContextLifecycleCallback {
        private boolean mAppSwitch;
        private Object mPayload;

        private PageShowAjxContextLifecycleCallback(boolean z, Object obj) {
            this.mAppSwitch = z;
            this.mPayload = obj;
        }

        public void onCreated(IAjxContext iAjxContext) {
            iAjxContext.getJsContext().showPage(this.mAppSwitch, this.mPayload);
            AjxTransitionState.this.mAjxView.unregisterAjxContextLifecycleCallback(this);
        }
    }

    public AjxTransitionState(AjxView ajxView) {
        this.mAjxView = ajxView;
        this.mAjxEventHandler = new AjxEventHandler(this.mAjxView);
    }

    /* access modifiers changed from: 0000 */
    public void pageShow(boolean z, Object obj) {
        IAjxContext ajxContext = this.mAjxView.getAjxContext();
        if (ajxContext == null || ajxContext.getJsContext() == null) {
            this.mAjxView.registerAjxContextLifecycleCallback(new PageShowAjxContextLifecycleCallback(z, obj));
        } else {
            ajxContext.invokePageResume();
            ajxContext.getJsContext().showPage(z, obj);
        }
        if (pageLifeCircleListener != null) {
            pageLifeCircleListener.onPageShow(z, obj);
        }
    }

    /* access modifiers changed from: 0000 */
    public void onNewIntent(Object obj) {
        IAjxContext ajxContext = this.mAjxView.getAjxContext();
        if (ajxContext != null && ajxContext.getJsContext() != null) {
            ajxContext.onNewIntent();
            ajxContext.getJsContext().onNewIntent(obj);
        }
    }

    /* access modifiers changed from: 0000 */
    public void pageHide(boolean z) {
        IAjxContext ajxContext = this.mAjxView.getAjxContext();
        if (!(ajxContext == null || ajxContext.getJsContext() == null)) {
            ajxContext.invokePageStop();
            ajxContext.getJsContext().hidePage(z);
        }
        if (pageLifeCircleListener != null) {
            pageLifeCircleListener.onPageHide(z);
        }
    }

    /* access modifiers changed from: 0000 */
    public void pageBecomeActive() {
        IAjxContext ajxContext = this.mAjxView.getAjxContext();
        if (ajxContext != null && ajxContext.getJsContext() != null) {
            ajxContext.getJsContext().pageBecomeActive();
        }
    }

    /* access modifiers changed from: 0000 */
    public void pageResignActive() {
        IAjxContext ajxContext = this.mAjxView.getAjxContext();
        if (ajxContext != null && ajxContext.getJsContext() != null) {
            ajxContext.getJsContext().pageResignActive();
        }
    }

    /* access modifiers changed from: 0000 */
    public IAjxContext createAjxContext(AjxView ajxView, JsRunInfo jsRunInfo) {
        return Ajx.getInstance().createAjxContext(ajxView, jsRunInfo);
    }

    /* access modifiers changed from: 0000 */
    public void run(IAjxContext iAjxContext, JsRunInfo jsRunInfo) {
        this.mAjxEventHandler.reset();
        Ajx.getInstance().run(iAjxContext, jsRunInfo, this.mAjxEventHandler);
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Code restructure failed: missing block: B:43:?, code lost:
        r3.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x00bf, code lost:
        r6 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:0x00c0, code lost:
        r6.printStackTrace();
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:39:0x00b7 */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x00bb A[SYNTHETIC, Splitter:B:42:0x00bb] */
    /* JADX WARNING: Removed duplicated region for block: B:48:0x00c6 A[SYNTHETIC, Splitter:B:48:0x00c6] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean checkUrlExist(java.lang.String r6) {
        /*
            r5 = this;
            boolean r0 = android.text.TextUtils.isEmpty(r6)
            r1 = 0
            if (r0 == 0) goto L_0x000a
            r5.mUrlExist = r1
            return r1
        L_0x000a:
            java.lang.String r0 = "id://"
            boolean r0 = r6.startsWith(r0)
            r2 = 1
            if (r0 == 0) goto L_0x0041
            java.lang.String r0 = "id://"
            java.lang.String r3 = ""
            java.lang.String r6 = r6.replace(r0, r3)
            java.lang.String r0 = com.autonavi.minimap.ajx3.platform.ackor.AjxFileInfo.getBundleName(r6)
            com.autonavi.minimap.ajx3.widget.AjxView r3 = r5.mAjxView
            com.autonavi.minimap.ajx3.context.IAjxContext r3 = r3.getAjxContext()
            int r0 = r3.getPatchIndex(r0)
            com.autonavi.minimap.ajx3.Ajx r3 = com.autonavi.minimap.ajx3.Ajx.getInstance()
            com.autonavi.minimap.ajx3.core.PageConfig r6 = r3.getPageConfig(r6, r0)
            if (r6 == 0) goto L_0x003e
            java.lang.String r6 = r6.getUrl()
            boolean r6 = android.text.TextUtils.isEmpty(r6)
            if (r6 != 0) goto L_0x003e
            return r2
        L_0x003e:
            r5.mUrlExist = r1
            return r1
        L_0x0041:
            com.autonavi.minimap.ajx3.widget.AjxView r0 = r5.mAjxView
            android.content.Context r0 = r0.getContext()
            com.autonavi.minimap.ajx3.image.PictureParams r3 = new com.autonavi.minimap.ajx3.image.PictureParams
            r3.<init>()
            r3.url = r6
            com.autonavi.minimap.ajx3.Ajx r4 = com.autonavi.minimap.ajx3.Ajx.getInstance()
            com.autonavi.minimap.ajx3.loader.IAjxImageLoader r6 = r4.lookupLoader(r6)
            java.lang.String r6 = r6.processingPath(r3)
            android.net.Uri r3 = android.net.Uri.parse(r6)
            java.lang.String r3 = r3.getScheme()
            boolean r4 = android.text.TextUtils.isEmpty(r3)
            if (r4 == 0) goto L_0x0071
            boolean r6 = com.autonavi.minimap.ajx3.platform.ackor.AjxFileInfo.isFileExists(r6)
            r5.mUrlExist = r6
            boolean r6 = r5.mUrlExist
            return r6
        L_0x0071:
            java.lang.String r4 = "file"
            boolean r4 = r4.equals(r3)
            if (r4 == 0) goto L_0x008c
            r0 = 7
            java.lang.String r6 = r6.substring(r0)
            java.io.File r0 = new java.io.File
            r0.<init>(r6)
            boolean r6 = r0.exists()
            r5.mUrlExist = r6
            boolean r6 = r5.mUrlExist
            return r6
        L_0x008c:
            java.lang.String r4 = "asset"
            boolean r3 = r4.equals(r3)
            if (r3 == 0) goto L_0x00cf
            r3 = 8
            java.lang.String r6 = r6.substring(r3)
            r3 = 0
            android.content.res.AssetManager r0 = r0.getAssets()     // Catch:{ IOException -> 0x00b7 }
            java.io.InputStream r6 = r0.open(r6)     // Catch:{ IOException -> 0x00b7 }
            r5.mUrlExist = r2     // Catch:{ IOException -> 0x00b3, all -> 0x00b0 }
            if (r6 == 0) goto L_0x00af
            r6.close()     // Catch:{ IOException -> 0x00ab }
            goto L_0x00af
        L_0x00ab:
            r6 = move-exception
            r6.printStackTrace()
        L_0x00af:
            return r2
        L_0x00b0:
            r0 = move-exception
            r3 = r6
            goto L_0x00c4
        L_0x00b3:
            r3 = r6
            goto L_0x00b7
        L_0x00b5:
            r0 = move-exception
            goto L_0x00c4
        L_0x00b7:
            r5.mUrlExist = r1     // Catch:{ all -> 0x00b5 }
            if (r3 == 0) goto L_0x00c3
            r3.close()     // Catch:{ IOException -> 0x00bf }
            goto L_0x00c3
        L_0x00bf:
            r6 = move-exception
            r6.printStackTrace()
        L_0x00c3:
            return r1
        L_0x00c4:
            if (r3 == 0) goto L_0x00ce
            r3.close()     // Catch:{ IOException -> 0x00ca }
            goto L_0x00ce
        L_0x00ca:
            r6 = move-exception
            r6.printStackTrace()
        L_0x00ce:
            throw r0
        L_0x00cf:
            r5.mUrlExist = r1
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.ajx3.widget.AjxTransitionState.checkUrlExist(java.lang.String):boolean");
    }

    /* access modifiers changed from: 0000 */
    public boolean hardwareback() {
        if (!this.mUrlExist) {
            return false;
        }
        IAjxContext ajxContext = this.mAjxView.getAjxContext();
        if (!(ajxContext == null || ajxContext.getJsContext() == null)) {
            if (ajxContext.hasRuntimeException()) {
                return false;
            }
            if (ajxContext.handleBackPressed()) {
                return true;
            }
            ajxContext.getJsContext().hardwareBack();
        }
        if (!this.mAjxEventHandler.hasErrorOccured()) {
            return true;
        }
        return false;
    }

    public static void setPageLifeCircleListener(OnPageLifeCircleListener onPageLifeCircleListener) {
        pageLifeCircleListener = onPageLifeCircleListener;
    }
}
