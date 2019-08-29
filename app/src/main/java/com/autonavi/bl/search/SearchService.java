package com.autonavi.bl.search;

public class SearchService {
    protected transient boolean swigCMemOwn;
    private transient long swigCPtr;

    private static native void abortSearch(long j, SearchService searchService, long j2);

    private static native long createService(String str, boolean z);

    private static native long createService1(String str);

    private static native void destroyNativeObj(long j);

    private static native long startKeywordSearch(long j, SearchService searchService, long j2, InfoliteParam infoliteParam, int i, long j3, InfoliteCallback infoliteCallback);

    public SearchService(long j, boolean z) {
        this.swigCMemOwn = z;
        this.swigCPtr = j;
    }

    public static long getCPtr(SearchService searchService) {
        if (searchService == null) {
            return 0;
        }
        return searchService.swigCPtr;
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

    public static SearchService createServiceS(String str, boolean z) {
        long createService = createService(str, z);
        if (createService == 0) {
            return null;
        }
        return new SearchService(createService, false);
    }

    public static SearchService createServiceS(String str) {
        long createService1 = createService1(str);
        if (createService1 == 0) {
            return null;
        }
        return new SearchService(createService1, false);
    }

    public long startKeywordSearch(InfoliteParam infoliteParam, int i, InfoliteCallback infoliteCallback) {
        return startKeywordSearch(this.swigCPtr, this, 0, infoliteParam, i, InfoliteCallback.getCPtr(infoliteCallback), infoliteCallback);
    }

    public void abortSearch(long j) {
        abortSearch(this.swigCPtr, this, j);
    }
}
