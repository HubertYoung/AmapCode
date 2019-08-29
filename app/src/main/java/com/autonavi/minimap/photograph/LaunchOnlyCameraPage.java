package com.autonavi.minimap.photograph;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.media.ExifInterface;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import com.amap.bundle.utils.ui.ToastHelper;
import com.amap.location.sdk.fusion.LocationParams;
import com.autonavi.annotation.PageAction;
import com.autonavi.common.Callback;
import com.autonavi.common.impl.Locator.Provider;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.gdtaojin.camera.CameraInterface;
import com.autonavi.gdtaojin.camera.CameraInterface.onCaptureButtonClickListener;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.map.fragmentcontainer.page.IPresenter;
import com.autonavi.map.fragmentcontainer.page.PageTheme.Transparent;
import com.autonavi.minimap.R;
import com.autonavi.sdk.location.LocationInstrument;
import java.io.File;
import java.lang.ref.WeakReference;
import org.json.JSONException;
import org.json.JSONObject;
import proguard.annotation.KeepName;

@KeepName
@PageAction("amap.basemap.action.photo_select_camera")
public class LaunchOnlyCameraPage extends AbstractBasePage<dtg> implements Transparent {
    public static String d = "imgbase64";
    public boolean a = false;
    public String b;
    public JSONObject c;
    public String e;
    public Callback<JSONObject> f;
    public String g = "";
    public String h = "";
    public int i = 500;
    /* access modifiers changed from: private */
    public String j;
    /* access modifiers changed from: private */
    public String k;
    /* access modifiers changed from: private */
    public final Handler l;

    static class a extends Handler {
        private WeakReference<LaunchOnlyCameraPage> a;

        public a(LaunchOnlyCameraPage launchOnlyCameraPage) {
            this.a = new WeakReference<>(launchOnlyCameraPage);
        }

        public final void handleMessage(Message message) {
            LaunchOnlyCameraPage launchOnlyCameraPage = (LaunchOnlyCameraPage) this.a.get();
            if (launchOnlyCameraPage != null && message.what == 1) {
                if (TextUtils.isEmpty(LaunchOnlyCameraPage.d) || !LaunchOnlyCameraPage.d.toLowerCase().equals("filepath")) {
                    LaunchOnlyCameraPage.a(launchOnlyCameraPage, LaunchOnlyCameraPage.b(launchOnlyCameraPage.k));
                } else {
                    LaunchOnlyCameraPage.a(launchOnlyCameraPage, launchOnlyCameraPage.k, message.arg1, message.arg2);
                }
            }
        }
    }

    public LaunchOnlyCameraPage() {
        StringBuilder sb = new StringBuilder();
        sb.append(Environment.getExternalStorageDirectory().getAbsolutePath());
        sb.append(File.separator);
        sb.append("autonavi");
        sb.append(File.separator);
        sb.append(this.g);
        sb.append(File.separator);
        this.j = sb.toString();
        this.l = new a(this);
    }

    public void onCreate(Context context) {
        super.onCreate(context);
        setContentView(R.layout.launch_only_camera_fragment);
    }

    /* JADX WARNING: type inference failed for: r0v0, types: [boolean] */
    /* JADX WARNING: type inference failed for: r0v1, types: [java.io.Closeable] */
    /* JADX WARNING: type inference failed for: r0v2 */
    /* JADX WARNING: type inference failed for: r0v3, types: [java.io.Closeable] */
    /* JADX WARNING: type inference failed for: r0v4 */
    /* JADX WARNING: type inference failed for: r0v5 */
    /* JADX WARNING: type inference failed for: r0v6 */
    /* JADX WARNING: type inference failed for: r0v7 */
    /* JADX WARNING: type inference failed for: r0v8 */
    /* JADX WARNING: type inference failed for: r0v9, types: [java.io.FileInputStream, java.io.InputStream] */
    /* JADX WARNING: type inference failed for: r0v10 */
    /* JADX WARNING: type inference failed for: r0v11 */
    /* JADX WARNING: type inference failed for: r0v12 */
    /* JADX WARNING: type inference failed for: r0v13 */
    /* JADX WARNING: type inference failed for: r0v14 */
    /* JADX WARNING: type inference failed for: r0v15 */
    /* JADX WARNING: type inference failed for: r0v16 */
    /* JADX WARNING: type inference failed for: r0v17 */
    /* JADX WARNING: type inference failed for: r0v18 */
    /* JADX WARNING: type inference failed for: r0v19 */
    /* access modifiers changed from: private */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r0v2
      assigns: []
      uses: []
      mth insns count: 42
    	at jadx.core.dex.visitors.typeinference.TypeSearch.fillTypeCandidates(TypeSearch.java:237)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.dex.visitors.typeinference.TypeSearch.run(TypeSearch.java:53)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runMultiVariableSearch(TypeInferenceVisitor.java:104)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:97)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:27)
    	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$1(DepthTraversal.java:14)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
    	at jadx.core.ProcessClass.process(ProcessClass.java:30)
    	at jadx.core.ProcessClass.lambda$processDependencies$0(ProcessClass.java:49)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:49)
    	at jadx.core.ProcessClass.process(ProcessClass.java:35)
    	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:311)
    	at jadx.api.JavaClass.decompile(JavaClass.java:62)
    	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:217)
     */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x0036  */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x003b A[RETURN] */
    /* JADX WARNING: Unknown variable types count: 7 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String b(java.lang.String r3) {
        /*
            boolean r0 = android.text.TextUtils.isEmpty(r3)
            r1 = 0
            if (r0 == 0) goto L_0x0008
            return r1
        L_0x0008:
            java.io.FileInputStream r0 = new java.io.FileInputStream     // Catch:{ FileNotFoundException -> 0x002b, IOException -> 0x0024, all -> 0x0021 }
            r0.<init>(r3)     // Catch:{ FileNotFoundException -> 0x002b, IOException -> 0x0024, all -> 0x0021 }
            int r3 = r0.available()     // Catch:{ FileNotFoundException -> 0x001e, IOException -> 0x001b }
            byte[] r3 = new byte[r3]     // Catch:{ FileNotFoundException -> 0x001e, IOException -> 0x001b }
            r0.read(r3)     // Catch:{ FileNotFoundException -> 0x0019, IOException -> 0x0017 }
            goto L_0x0031
        L_0x0017:
            r2 = move-exception
            goto L_0x0027
        L_0x0019:
            r2 = move-exception
            goto L_0x002e
        L_0x001b:
            r2 = move-exception
            r3 = r1
            goto L_0x0027
        L_0x001e:
            r2 = move-exception
            r3 = r1
            goto L_0x002e
        L_0x0021:
            r3 = move-exception
            r0 = r1
            goto L_0x003d
        L_0x0024:
            r2 = move-exception
            r3 = r1
            r0 = r3
        L_0x0027:
            r2.printStackTrace()     // Catch:{ all -> 0x003c }
            goto L_0x0031
        L_0x002b:
            r2 = move-exception
            r3 = r1
            r0 = r3
        L_0x002e:
            r2.printStackTrace()     // Catch:{ all -> 0x003c }
        L_0x0031:
            defpackage.ahe.a(r0)
            if (r3 == 0) goto L_0x003b
            java.lang.String r3 = defpackage.agv.a(r3)
            return r3
        L_0x003b:
            return r1
        L_0x003c:
            r3 = move-exception
        L_0x003d:
            defpackage.ahe.a(r0)
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.photograph.LaunchOnlyCameraPage.b(java.lang.String):java.lang.String");
    }

    public static boolean a() {
        return LocationInstrument.getInstance().isProviderEnabled(Provider.PROVIDER_GPS);
    }

    public /* synthetic */ IPresenter createPresenter() {
        return new dtg(this);
    }

    static /* synthetic */ void a(LaunchOnlyCameraPage launchOnlyCameraPage, String str, int i2, int i3) {
        if (launchOnlyCameraPage.f != null) {
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("_action", launchOnlyCameraPage.b);
                if (!TextUtils.isEmpty(str)) {
                    jSONObject.put("filepath", "file://".concat(String.valueOf(str)));
                    try {
                        ExifInterface exifInterface = new ExifInterface(str);
                        String attribute = exifInterface.getAttribute("GPSLatitude");
                        String attribute2 = exifInterface.getAttribute("GPSLongitude");
                        GeoPoint latestPosition = LocationInstrument.getInstance().getLatestPosition();
                        if (attribute == null || "".equals(attribute)) {
                            StringBuilder sb = new StringBuilder();
                            sb.append(latestPosition.getLatitude());
                            attribute = sb.toString();
                        }
                        if (attribute2 == null || "".equals(attribute2)) {
                            StringBuilder sb2 = new StringBuilder();
                            sb2.append(latestPosition.getLongitude());
                            attribute2 = sb2.toString();
                        }
                        jSONObject.put("lat", attribute);
                        jSONObject.put(LocationParams.PARA_FLP_AUTONAVI_LON, attribute2);
                        jSONObject.put("angle", launchOnlyCameraPage.e);
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }
                jSONObject.put("source", 2);
                jSONObject.put("w", i2);
                jSONObject.put("h", i3);
                launchOnlyCameraPage.f.callback(jSONObject);
            } catch (JSONException e3) {
                e3.printStackTrace();
            }
        }
    }

    static /* synthetic */ void a(LaunchOnlyCameraPage launchOnlyCameraPage, String str) {
        if (launchOnlyCameraPage.f != null) {
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("_action", launchOnlyCameraPage.b);
                jSONObject.put("imgbase64", str);
                jSONObject.put("source", 2);
                launchOnlyCameraPage.f.callback(jSONObject);
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }
    }

    static /* synthetic */ void b(LaunchOnlyCameraPage launchOnlyCameraPage) {
        try {
            StringBuilder sb = new StringBuilder("autonavi");
            sb.append(File.separator);
            sb.append(launchOnlyCameraPage.g);
            String sb2 = sb.toString();
            if (launchOnlyCameraPage.c != null) {
                Activity activity = launchOnlyCameraPage.getActivity();
                AnonymousClass3 r3 = new Callback<Object>() {
                    public void callback(Object obj) {
                    }

                    public void error(Throwable th, boolean z) {
                    }
                };
                String jSONObject = launchOnlyCameraPage.c.toString();
                CameraInterface.setCameraFloder(sb2);
                CameraInterface.setOnCaptureButtonClickListener(new onCaptureButtonClickListener(r3) {
                    final /* synthetic */ Callback a;

                    public final void onCaptureEnd() {
                    }

                    {
                        this.a = r1;
                    }

                    public final void onCapture() {
                        this.a.callback(null);
                    }
                });
                CameraInterface.showCameraActivityForResultWithParameter(activity, 4096, jSONObject);
                return;
            }
            adu.a(sb2, launchOnlyCameraPage.getActivity(), 4096, new Callback<Object>() {
                public void callback(Object obj) {
                }

                public void error(Throwable th, boolean z) {
                }
            });
        } catch (ActivityNotFoundException unused) {
            ToastHelper.showLongToast("您设备上的照相机功能异常，请确认。");
        }
    }
}
