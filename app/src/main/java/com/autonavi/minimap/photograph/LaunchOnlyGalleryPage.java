package com.autonavi.minimap.photograph;

import android.app.Application;
import android.content.Context;
import android.media.ExifInterface;
import android.os.Build.VERSION;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import com.amap.location.sdk.fusion.LocationParams;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.annotation.PageAction;
import com.autonavi.common.Callback;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.map.fragmentcontainer.page.IPresenter;
import com.autonavi.map.fragmentcontainer.page.PageTheme.Transparent;
import com.autonavi.minimap.R;
import java.io.File;
import java.lang.ref.WeakReference;
import org.json.JSONException;
import org.json.JSONObject;
import proguard.annotation.KeepName;

@KeepName
@PageAction("amap.basemap.action.photo_select_gallery")
public class LaunchOnlyGalleryPage extends AbstractBasePage<dth> implements Transparent {
    public static String f = "imgbase64";
    public String a;
    public Callback<JSONObject> b;
    public String c = "";
    public String d = "";
    public int e = 500;
    /* access modifiers changed from: private */
    public String g;
    /* access modifiers changed from: private */
    public String h;
    /* access modifiers changed from: private */
    public String i;
    /* access modifiers changed from: private */
    public final Handler j = new a(this);

    static class a extends Handler {
        private WeakReference<LaunchOnlyGalleryPage> a;

        public a(LaunchOnlyGalleryPage launchOnlyGalleryPage) {
            this.a = new WeakReference<>(launchOnlyGalleryPage);
        }

        public final void handleMessage(Message message) {
            LaunchOnlyGalleryPage launchOnlyGalleryPage = (LaunchOnlyGalleryPage) this.a.get();
            if (launchOnlyGalleryPage != null && message.what == 1) {
                if (TextUtils.isEmpty(LaunchOnlyGalleryPage.f) || !LaunchOnlyGalleryPage.f.toLowerCase().equals("filepath")) {
                    LaunchOnlyGalleryPage.a(launchOnlyGalleryPage, LaunchOnlyGalleryPage.b(launchOnlyGalleryPage.h));
                } else {
                    LaunchOnlyGalleryPage.a(launchOnlyGalleryPage, launchOnlyGalleryPage.h, launchOnlyGalleryPage.i, message.arg1, message.arg2);
                }
            }
        }
    }

    public LaunchOnlyGalleryPage() {
        Application application = AMapAppGlobal.getApplication();
        if (application != null) {
            if (VERSION.SDK_INT >= 29) {
                StringBuilder sb = new StringBuilder();
                sb.append(application.getExternalCacheDir().getAbsolutePath());
                sb.append(File.separator);
                sb.append("autonavi");
                sb.append(File.separator);
                sb.append(this.c);
                sb.append(File.separator);
                this.g = sb.toString();
                return;
            }
            StringBuilder sb2 = new StringBuilder();
            sb2.append(Environment.getExternalStorageDirectory().getAbsolutePath());
            sb2.append(File.separator);
            sb2.append("autonavi");
            sb2.append(File.separator);
            sb2.append(this.c);
            sb2.append(File.separator);
            this.g = sb2.toString();
        }
    }

    public void onCreate(Context context) {
        super.onCreate(context);
        setContentView(R.layout.launch_gallery_fragment);
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
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.photograph.LaunchOnlyGalleryPage.b(java.lang.String):java.lang.String");
    }

    public /* synthetic */ IPresenter createPresenter() {
        return new dth(this);
    }

    static /* synthetic */ void a(LaunchOnlyGalleryPage launchOnlyGalleryPage, String str, String str2, int i2, int i3) {
        if (launchOnlyGalleryPage.b != null) {
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("_action", launchOnlyGalleryPage.a);
                if (!TextUtils.isEmpty(str)) {
                    jSONObject.put("filepath", "file://".concat(String.valueOf(str)));
                    try {
                        ExifInterface exifInterface = new ExifInterface(str2);
                        String attribute = exifInterface.getAttribute("GPSLatitude");
                        String attribute2 = exifInterface.getAttribute("GPSLatitudeRef");
                        String attribute3 = exifInterface.getAttribute("GPSLongitude");
                        String attribute4 = exifInterface.getAttribute("GPSLongitudeRef");
                        String a2 = dte.a(attribute, attribute2);
                        String a3 = dte.a(attribute3, attribute4);
                        if (a2 == null) {
                            a2 = "";
                        }
                        if (a3 == null) {
                            a3 = "";
                        }
                        jSONObject.put("lat", a2);
                        jSONObject.put(LocationParams.PARA_FLP_AUTONAVI_LON, a3);
                        jSONObject.put("angle", "");
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }
                jSONObject.put("source", 0);
                jSONObject.put("w", i2);
                jSONObject.put("h", i3);
                launchOnlyGalleryPage.b.callback(jSONObject);
            } catch (JSONException e3) {
                e3.printStackTrace();
            }
        }
    }

    static /* synthetic */ void a(LaunchOnlyGalleryPage launchOnlyGalleryPage, String str) {
        if (launchOnlyGalleryPage.b != null) {
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("_action", launchOnlyGalleryPage.a);
                jSONObject.put("imgbase64", str);
                jSONObject.put("source", 0);
                launchOnlyGalleryPage.b.callback(jSONObject);
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }
    }
}
