package com.autonavi.jni.ae.route;

import android.content.Context;
import com.autonavi.jni.ae.route.model.LightBarItem;
import com.autonavi.jni.ae.route.model.RerouteOption;
import com.autonavi.jni.ae.route.model.RouteConfig;
import com.autonavi.jni.ae.route.model.RouteOption;
import com.autonavi.jni.ae.route.model.TmcRoutePath;
import com.autonavi.jni.ae.route.observer.HttpInterface;
import com.autonavi.jni.ae.route.observer.RouteObserver;
import com.autonavi.jni.ae.route.route.CalcRouteResult;

public class RouteService {
    private Context mContext;
    private HttpInterface mHttpProcess;
    private long mPtr;
    private RouteObserver mRouteObserver;

    public static native CalcRouteResult decodeRouteData(byte[] bArr);

    public static native LightBarItem[] decodeRouteTmcBar(byte[] bArr, TmcRoutePath tmcRoutePath);

    public static native String getRouteVersion();

    public static native String getSdkVersion();

    public static native String getTravelSdkVersion();

    private final native void init(RouteConfig routeConfig);

    public native void abortRoutePlan();

    public native int control(int i, String str);

    public final native void destroy();

    public native void processHttpData(int i, int i2, byte[] bArr);

    public native void processHttpError(int i, int i2);

    public native int requestRoute(RouteOption routeOption);

    public native int reroute(RerouteOption rerouteOption);

    public RouteService(RouteConfig routeConfig, Context context) {
        init(routeConfig);
        this.mContext = context;
    }

    public void setRouteObserver(RouteObserver routeObserver) {
        this.mRouteObserver = routeObserver;
    }

    public void registerHttpProcesser(HttpInterface httpInterface) {
        this.mHttpProcess = httpInterface;
    }

    /* JADX WARNING: Removed duplicated region for block: B:43:0x0060 A[SYNTHETIC, Splitter:B:43:0x0060] */
    /* JADX WARNING: Removed duplicated region for block: B:48:0x006a A[SYNTHETIC, Splitter:B:48:0x006a] */
    /* JADX WARNING: Removed duplicated region for block: B:55:0x0076 A[SYNTHETIC, Splitter:B:55:0x0076] */
    /* JADX WARNING: Removed duplicated region for block: B:60:0x0080 A[SYNTHETIC, Splitter:B:60:0x0080] */
    /* JADX WARNING: Removed duplicated region for block: B:67:0x008c A[SYNTHETIC, Splitter:B:67:0x008c] */
    /* JADX WARNING: Removed duplicated region for block: B:72:0x0096 A[SYNTHETIC, Splitter:B:72:0x0096] */
    /* JADX WARNING: Unknown top exception splitter block from list: {B:52:0x0071=Splitter:B:52:0x0071, B:40:0x005b=Splitter:B:40:0x005b} */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public byte[] readAssetsFile(int r7, int r8) {
        /*
            r6 = this;
            r7 = 0
            r0 = 1
            if (r8 != r0) goto L_0x0007
            java.lang.String r8 = "navi/road_config.bin"
            goto L_0x0008
        L_0x0007:
            r8 = r7
        L_0x0008:
            boolean r0 = android.text.TextUtils.isEmpty(r8)
            if (r0 == 0) goto L_0x000f
            return r7
        L_0x000f:
            android.content.Context r0 = r6.mContext     // Catch:{ Exception -> 0x006e, OutOfMemoryError -> 0x0058, all -> 0x0053 }
            android.content.res.AssetManager r0 = r0.getAssets()     // Catch:{ Exception -> 0x006e, OutOfMemoryError -> 0x0058, all -> 0x0053 }
            java.io.InputStream r8 = r0.open(r8)     // Catch:{ Exception -> 0x006e, OutOfMemoryError -> 0x0058, all -> 0x0053 }
            java.io.ByteArrayOutputStream r0 = new java.io.ByteArrayOutputStream     // Catch:{ Exception -> 0x0050, OutOfMemoryError -> 0x004d, all -> 0x0048 }
            r0.<init>()     // Catch:{ Exception -> 0x0050, OutOfMemoryError -> 0x004d, all -> 0x0048 }
            r1 = 1024(0x400, float:1.435E-42)
            byte[] r2 = new byte[r1]     // Catch:{ Exception -> 0x0046, OutOfMemoryError -> 0x0044 }
        L_0x0022:
            r3 = 0
            int r4 = r8.read(r2, r3, r1)     // Catch:{ Exception -> 0x0046, OutOfMemoryError -> 0x0044 }
            if (r4 <= 0) goto L_0x002d
            r0.write(r2, r3, r4)     // Catch:{ Exception -> 0x0046, OutOfMemoryError -> 0x0044 }
            goto L_0x0022
        L_0x002d:
            byte[] r1 = r0.toByteArray()     // Catch:{ Exception -> 0x0046, OutOfMemoryError -> 0x0044 }
            if (r8 == 0) goto L_0x003b
            r8.close()     // Catch:{ IOException -> 0x0037 }
            goto L_0x003b
        L_0x0037:
            r7 = move-exception
            r7.printStackTrace()
        L_0x003b:
            r0.close()     // Catch:{ IOException -> 0x003f }
            goto L_0x0043
        L_0x003f:
            r7 = move-exception
            r7.printStackTrace()
        L_0x0043:
            return r1
        L_0x0044:
            r1 = move-exception
            goto L_0x005b
        L_0x0046:
            r1 = move-exception
            goto L_0x0071
        L_0x0048:
            r0 = move-exception
            r5 = r0
            r0 = r7
            r7 = r5
            goto L_0x008a
        L_0x004d:
            r1 = move-exception
            r0 = r7
            goto L_0x005b
        L_0x0050:
            r1 = move-exception
            r0 = r7
            goto L_0x0071
        L_0x0053:
            r8 = move-exception
            r0 = r7
            r7 = r8
            r8 = r0
            goto L_0x008a
        L_0x0058:
            r1 = move-exception
            r8 = r7
            r0 = r8
        L_0x005b:
            r1.printStackTrace()     // Catch:{ all -> 0x0089 }
            if (r8 == 0) goto L_0x0068
            r8.close()     // Catch:{ IOException -> 0x0064 }
            goto L_0x0068
        L_0x0064:
            r8 = move-exception
            r8.printStackTrace()
        L_0x0068:
            if (r0 == 0) goto L_0x0088
            r0.close()     // Catch:{ IOException -> 0x0084 }
            goto L_0x0088
        L_0x006e:
            r1 = move-exception
            r8 = r7
            r0 = r8
        L_0x0071:
            r1.printStackTrace()     // Catch:{ all -> 0x0089 }
            if (r8 == 0) goto L_0x007e
            r8.close()     // Catch:{ IOException -> 0x007a }
            goto L_0x007e
        L_0x007a:
            r8 = move-exception
            r8.printStackTrace()
        L_0x007e:
            if (r0 == 0) goto L_0x0088
            r0.close()     // Catch:{ IOException -> 0x0084 }
            goto L_0x0088
        L_0x0084:
            r8 = move-exception
            r8.printStackTrace()
        L_0x0088:
            return r7
        L_0x0089:
            r7 = move-exception
        L_0x008a:
            if (r8 == 0) goto L_0x0094
            r8.close()     // Catch:{ IOException -> 0x0090 }
            goto L_0x0094
        L_0x0090:
            r8 = move-exception
            r8.printStackTrace()
        L_0x0094:
            if (r0 == 0) goto L_0x009e
            r0.close()     // Catch:{ IOException -> 0x009a }
            goto L_0x009e
        L_0x009a:
            r8 = move-exception
            r8.printStackTrace()
        L_0x009e:
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.jni.ae.route.RouteService.readAssetsFile(int, int):byte[]");
    }
}
