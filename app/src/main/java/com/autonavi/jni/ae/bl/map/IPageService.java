package com.autonavi.jni.ae.bl.map;

public class IPageService {
    protected transient boolean swigCMemOwn;
    private transient long swigCPtr;

    private static native long create();

    private static native long createPage(long j, IPageService iPageService, String str);

    private static native void destroy(long j, IPageService iPageService);

    private static native void destroyNativeObj(long j);

    private static native void destroyPage(long j, IPageService iPageService, long j2, IMapPage iMapPage);

    private static native String getVersion();

    private static native boolean init(long j, IPageService iPageService);

    private static native boolean unInit(long j, IPageService iPageService);

    protected IPageService(long j, boolean z) {
        this.swigCMemOwn = z;
        this.swigCPtr = j;
    }

    protected static long getCPtr(IPageService iPageService) {
        if (iPageService == null) {
            return 0;
        }
        return iPageService.swigCPtr;
    }

    /* access modifiers changed from: protected */
    public void finalize() {
        delete();
    }

    public synchronized void delete() {
        if (this.swigCPtr != 0) {
            if (this.swigCMemOwn) {
                this.swigCMemOwn = false;
                destroyNativeObj(this.swigCPtr);
            }
            this.swigCPtr = 0;
        }
    }

    public static IPageService createS() {
        long create = create();
        if (create == 0) {
            return null;
        }
        return new IPageService(create, false);
    }

    public static void destroyS(IPageService iPageService) {
        destroy(getCPtr(iPageService), iPageService);
    }

    public static String getVersionS() {
        return getVersion();
    }

    public boolean init() {
        return init(this.swigCPtr, this);
    }

    public boolean unInit() {
        return unInit(this.swigCPtr, this);
    }

    public IMapPage createPage(String str) {
        long createPage = createPage(this.swigCPtr, this, str);
        if (createPage == 0) {
            return null;
        }
        return new IMapPage(createPage, false);
    }

    public void destroyPage(IMapPage iMapPage) {
        destroyPage(this.swigCPtr, this, IMapPage.getCPtr(iMapPage), iMapPage);
    }
}
