package com.uc.webview.export.internal.setup;

import android.content.Context;
import android.util.Pair;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.util.DjangoUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.ConfigConstants;
import com.alipay.mobile.common.logging.api.LogCategory;
import com.alipay.mobile.h5container.api.H5Param;
import com.alipay.mobile.quinox.bundle.IBundleOperator;
import com.alipay.mobile.securitycommon.aliauth.AliAuthConstants.Value;
import com.mpaas.nebula.plugin.H5ServicePlugin;
import com.tencent.open.SocialConstants;
import com.uc.webview.export.Build.Version;
import com.uc.webview.export.CDParamKeys;
import com.uc.webview.export.annotations.Api;
import com.uc.webview.export.cyclone.UCCyclone;
import com.uc.webview.export.cyclone.UCCyclone.MessageDigestType;
import com.uc.webview.export.cyclone.UCElapseTime;
import com.uc.webview.export.cyclone.UCKnownException;
import com.uc.webview.export.extension.UCCore;
import com.uc.webview.export.internal.SDKFactory;
import com.uc.webview.export.internal.interfaces.IWaStat.WaStat;
import com.uc.webview.export.internal.utility.Log;
import com.uc.webview.export.internal.utility.j;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.json.JSONArray;
import org.json.JSONObject;

@Api
/* compiled from: ProGuard */
public class UCMPackageInfo {
    public static final String[] ARCHS = {"armeabi-v7a", "armeabi", "x86"};
    public static final String ASSETS_DIR = "assets";
    public static final String BROWSER_IF_DEX_FILE_USING_SO_SUFFIX = "libbrowser_if_jar_kj_uc.so";
    public static final String BROWSER_IF_FOR_EXPORT_FILE_USING_SO_SUFFIX = "libbrowser_if_for_export_jar_kj_uc.so";
    public static final String CORE_CLASS_LOADER_IMPL_CLASS = "com.uc.webkit.sdk.CoreClassPreLoaderImpl";
    public static final String CORE_FACTORY_IMPL_CLASS = "com.uc.webkit.sdk.CoreFactoryImpl";
    public static final String CORE_IMPL_DEX_FILE_USING_SO_SUFFIX = "libcore_jar_kj_uc.so";
    public static final String LIBRARY_DIR = "lib";
    public static final String RES_PAKS_DIR_NAME = "paks";
    public static final String SDK_SHELL_DEX_FILE = "sdk_shell.jar";
    public static final String SDK_SHELL_DEX_FILE_USING_SO_SUFFIX = "libsdk_shell_jar_kj_uc.so";
    private static final String[] a = {"webviewuc"};
    private static final String[] b = {"imagehelper"};
    public static final int compareVersion = 10027;
    public static final int deleteTempDecFiles = 10039;
    public static final int deleteUCMSDKDir = 10044;
    public static final int expectCreateDirFile2P = 10035;
    public static final int expectDirFile1F = 10032;
    public static final int expectDirFile1S = 10033;
    public static final int getDataDirHash = 10012;
    public static final int getDecompressRoot = 10003;
    public static final int getDir = 10001;
    public static final int getFlagRoot = 10005;
    public static final int getKernalJarCpyRoot = 10009;
    public static final int getKernalJarLnkRoot = 10007;
    public static final int getKernalResCpyRoot = 10010;
    public static final int getKernalResLnkRoot = 10008;
    public static final int getKernalShareJarCpyRoot = 10047;
    public static final int getKernalShareJarLnkRoot = 10046;
    public static final int getKernelFileIfMultiCoreFromDir = 10028;
    public static final int getKernelFiles = 10022;
    public static final int getKernelResFiles = 10024;
    public static final int getLibFilter = 10023;
    public static final int getOdexRoot = 10004;
    public static final int getRepairApolloRoot = 10045;
    public static final int getRepairRoot = 10006;
    public static final int getUnExistsFilePath = 10021;
    public static final int getUpdateRoot = 10002;
    public static final int getVersion = 10040;
    public static final int hadInstallUCMobile = 10026;
    public static final int initUCMBuildInfo = 10041;
    public static final int isDirShouldBeDeleted = 10043;
    public static final int isThickSDK = 10011;
    public static final int makeDirDeleteFlg = 10042;
    public static final int sortByLastModified = 10025;
    public final Pair<String, String> browserIFModule;
    private Context c;
    public final String coreCode;
    public final Pair<String, String> coreImplModule;
    public final String dataDir;
    public final String disabledFilePath;
    public final boolean isSpecified;
    public ClassLoader mCoreClassLoader = null;
    public ClassLoader mSdkShellClassLoader = null;
    public final String mainLibrary;
    public final String pkgName;
    public final String resDirPath;
    public final Pair<String, String> sdkShellModule;
    public final String soDirPath;

    /* compiled from: ProGuard */
    public static final class a {
        public String a;
        public String b;
        public String c;
        public String d;
        public String e;
        public String f;
        public String g;

        public static List<a> a(File file) {
            ByteArrayOutputStream byteArrayOutputStream;
            FileInputStream fileInputStream;
            int i;
            ArrayList arrayList = new ArrayList();
            if (file == null || !file.exists()) {
                StringBuilder sb = new StringBuilder("configFile:");
                sb.append(file);
                sb.append(" not exist");
                Log.i("Config", sb.toString());
                return arrayList;
            }
            FileInputStream fileInputStream2 = null;
            try {
                fileInputStream = new FileInputStream(file);
                try {
                    byteArrayOutputStream = new ByteArrayOutputStream();
                } catch (Exception e2) {
                    e = e2;
                    byteArrayOutputStream = null;
                    fileInputStream2 = fileInputStream;
                    try {
                        Log.e("Config", H5ServicePlugin.GET_CONFIG, e);
                        UCCyclone.close(fileInputStream2);
                        UCCyclone.close(byteArrayOutputStream);
                        return new ArrayList();
                    } catch (Throwable th) {
                        th = th;
                        fileInputStream = fileInputStream2;
                        UCCyclone.close(fileInputStream);
                        UCCyclone.close(byteArrayOutputStream);
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    byteArrayOutputStream = null;
                    UCCyclone.close(fileInputStream);
                    UCCyclone.close(byteArrayOutputStream);
                    throw th;
                }
                try {
                    byte[] bArr = new byte[1024];
                    while (true) {
                        int read = fileInputStream.read(bArr);
                        if (read <= 0) {
                            break;
                        }
                        byteArrayOutputStream.write(bArr, 0, read);
                    }
                    JSONArray jSONArray = new JSONArray(new String(byteArrayOutputStream.toByteArray()));
                    for (i = 0; i < jSONArray.length(); i++) {
                        JSONObject jSONObject = jSONArray.getJSONObject(i);
                        a aVar = new a();
                        aVar.a = jSONObject.getString("ucmver");
                        aVar.b = jSONObject.getString("sdkMin");
                        if (jSONObject.has("dex")) {
                            aVar.c = jSONObject.getJSONObject("dex").getString("path");
                        }
                        if (jSONObject.has(H5Param.SHOW_OPTION_MENU)) {
                            aVar.d = jSONObject.getJSONObject(H5Param.SHOW_OPTION_MENU).getString("path");
                        }
                        if (jSONObject.has("res")) {
                            aVar.e = jSONObject.getJSONObject("res").getString("path");
                        }
                        if (jSONObject.has("archive")) {
                            JSONObject jSONObject2 = jSONObject.getJSONObject("archive");
                            aVar.f = jSONObject2.getString("path");
                            aVar.g = jSONObject2.getString("algorithm");
                        }
                        arrayList.add(aVar);
                    }
                    UCCyclone.close(fileInputStream);
                    UCCyclone.close(byteArrayOutputStream);
                    return arrayList;
                } catch (Exception e3) {
                    e = e3;
                    fileInputStream2 = fileInputStream;
                    Log.e("Config", H5ServicePlugin.GET_CONFIG, e);
                    UCCyclone.close(fileInputStream2);
                    UCCyclone.close(byteArrayOutputStream);
                    return new ArrayList();
                } catch (Throwable th3) {
                    th = th3;
                    UCCyclone.close(fileInputStream);
                    UCCyclone.close(byteArrayOutputStream);
                    throw th;
                }
            } catch (Exception e4) {
                e = e4;
                byteArrayOutputStream = null;
                Log.e("Config", H5ServicePlugin.GET_CONFIG, e);
                UCCyclone.close(fileInputStream2);
                UCCyclone.close(byteArrayOutputStream);
                return new ArrayList();
            } catch (Throwable th4) {
                th = th4;
                byteArrayOutputStream = null;
                fileInputStream = null;
                UCCyclone.close(fileInputStream);
                UCCyclone.close(byteArrayOutputStream);
                throw th;
            }
        }

        public static boolean a(List<a> list, File file) {
            if (list.size() == 0) {
                StringBuilder sb = new StringBuilder("configs:");
                sb.append(list);
                sb.append(" is empty or configFile:");
                sb.append(file);
                Log.i("Config", sb.toString());
                return false;
            }
            FileOutputStream fileOutputStream = null;
            try {
                JSONArray jSONArray = new JSONArray();
                for (a next : list) {
                    JSONObject jSONObject = new JSONObject();
                    jSONArray.put(jSONObject);
                    jSONObject.put("ucmver", next.a);
                    jSONObject.put("sdkMin", next.b);
                    if (!j.a(next.c)) {
                        JSONObject jSONObject2 = new JSONObject();
                        jSONObject.put("dex", jSONObject2);
                        jSONObject2.put("path", next.c);
                    }
                    if (!j.a(next.d)) {
                        JSONObject jSONObject3 = new JSONObject();
                        jSONObject.put(H5Param.SHOW_OPTION_MENU, jSONObject3);
                        jSONObject3.put("path", next.d);
                    }
                    if (!j.a(next.e)) {
                        JSONObject jSONObject4 = new JSONObject();
                        jSONObject.put("res", jSONObject4);
                        jSONObject4.put("path", next.e);
                    }
                    if (!j.a(next.f)) {
                        JSONObject jSONObject5 = new JSONObject();
                        jSONObject.put("archive", jSONObject5);
                        jSONObject5.put("path", next.f);
                        jSONObject5.put("algorithm", j.a(next.g) ? "zip" : next.g);
                    }
                }
                FileOutputStream fileOutputStream2 = new FileOutputStream(file);
                try {
                    fileOutputStream2.write(jSONArray.toString().getBytes());
                    UCCyclone.close(fileOutputStream2);
                    return true;
                } catch (Exception e2) {
                    fileOutputStream = fileOutputStream2;
                    e = e2;
                    try {
                        Log.e("Config", "saveConfig", e);
                        UCCyclone.close(fileOutputStream);
                        return false;
                    } catch (Throwable th) {
                        th = th;
                        UCCyclone.close(fileOutputStream);
                        throw th;
                    }
                } catch (Throwable th2) {
                    fileOutputStream = fileOutputStream2;
                    th = th2;
                    UCCyclone.close(fileOutputStream);
                    throw th;
                }
            } catch (Exception e3) {
                e = e3;
                Log.e("Config", "saveConfig", e);
                UCCyclone.close(fileOutputStream);
                return false;
            }
        }
    }

    private static String a(String str) {
        if (!j.a(str)) {
            File file = new File(str);
            if (file.exists()) {
                return file.getAbsolutePath();
            }
        }
        return str;
    }

    public static String subStringForAppFilePath(Context context, String str) {
        return (context == null || j.a(str) || str.indexOf(context.getPackageName()) <= 0) ? str : str.substring(str.indexOf(context.getPackageName()), str.length());
    }

    /* JADX WARNING: Code restructure failed: missing block: B:37:0x013e, code lost:
        r8 = r9;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public UCMPackageInfo(android.content.Context r17, java.lang.String r18, java.lang.String r19, java.lang.String r20, java.lang.String r21, boolean r22, java.lang.String r23, java.lang.String r24, java.lang.String r25, java.lang.String r26, boolean r27) throws com.uc.webview.export.internal.setup.UCSetupException {
        /*
            r16 = this;
            r0 = r16
            r1 = r17
            r16.<init>()
            r3 = 0
            r0.mSdkShellClassLoader = r3
            r0.mCoreClassLoader = r3
            java.lang.String r4 = a(r19)
            java.lang.String r5 = a(r20)
            java.lang.String r6 = a(r21)
            java.lang.String r7 = a(r23)
            java.lang.String r8 = a(r24)
            java.lang.String r9 = a(r25)
            java.lang.String r10 = a(r26)
            android.content.Context r11 = r17.getApplicationContext()
            r0.c = r11
            r11 = r18
            r0.pkgName = r11
            r0.soDirPath = r4
            if (r27 == 0) goto L_0x003a
            java.lang.String r5 = a(r1, r6, r5)
        L_0x003a:
            if (r5 != 0) goto L_0x003e
            r5 = r3
            goto L_0x0058
        L_0x003e:
            java.lang.String r11 = "/"
            boolean r11 = r5.endsWith(r11)
            if (r11 == 0) goto L_0x0047
            goto L_0x0058
        L_0x0047:
            java.lang.StringBuilder r11 = new java.lang.StringBuilder
            r11.<init>()
            r11.append(r5)
            java.lang.String r5 = "/"
            r11.append(r5)
            java.lang.String r5 = r11.toString()
        L_0x0058:
            r0.resDirPath = r5
            r5 = r22
            r0.isSpecified = r5
            r5 = 1
            r11 = 0
            if (r6 == 0) goto L_0x00df
            r12 = 10035(0x2733, float:1.4062E-41)
            r13 = 2
            java.lang.Object[] r13 = new java.lang.Object[r13]
            if (r10 != 0) goto L_0x0076
            r10 = 10004(0x2714, float:1.4019E-41)
            java.lang.Object[] r14 = new java.lang.Object[r5]
            r14[r11] = r1
            java.lang.Object r10 = invoke(r10, r14)
            java.io.File r10 = (java.io.File) r10
            goto L_0x007c
        L_0x0076:
            java.io.File r14 = new java.io.File
            r14.<init>(r10)
            r10 = r14
        L_0x007c:
            r13[r11] = r10
            r10 = 10012(0x271c, float:1.403E-41)
            java.lang.Object[] r14 = new java.lang.Object[r5]
            java.lang.String r15 = subStringForAppFilePath(r1, r6)
            r14[r11] = r15
            java.lang.Object r10 = invoke(r10, r14)
            java.lang.String r10 = (java.lang.String) r10
            r13[r5] = r10
            java.lang.Object r10 = invoke(r12, r13)
            java.io.File r10 = (java.io.File) r10
            r0.dataDir = r6
            java.lang.StringBuilder r12 = new java.lang.StringBuilder
            r12.<init>()
            r12.append(r6)
            java.lang.String r13 = "/e1df430e25e9dacb26ead508abb3413f"
            r12.append(r13)
            java.lang.String r12 = r12.toString()
            r0.disabledFilePath = r12
            android.util.Pair r12 = new android.util.Pair
            if (r27 == 0) goto L_0x00b3
            java.lang.String r7 = b(r1, r6, r7)
        L_0x00b3:
            java.lang.String r13 = r10.getAbsolutePath()
            r12.<init>(r7, r13)
            r0.sdkShellModule = r12
            android.util.Pair r7 = new android.util.Pair
            if (r27 == 0) goto L_0x00c4
            java.lang.String r8 = b(r1, r6, r8)
        L_0x00c4:
            java.lang.String r12 = r10.getAbsolutePath()
            r7.<init>(r8, r12)
            r0.browserIFModule = r7
            android.util.Pair r7 = new android.util.Pair
            if (r27 == 0) goto L_0x00d5
            java.lang.String r9 = b(r1, r6, r9)
        L_0x00d5:
            java.lang.String r2 = r10.getAbsolutePath()
            r7.<init>(r9, r2)
            r0.coreImplModule = r7
            goto L_0x00f3
        L_0x00df:
            r0.dataDir = r3
            r2 = 10021(0x2725, float:1.4042E-41)
            java.lang.Object[] r6 = new java.lang.Object[r11]
            java.lang.Object r2 = invoke(r2, r6)
            java.lang.String r2 = (java.lang.String) r2
            r0.disabledFilePath = r2
            r0.sdkShellModule = r3
            r0.browserIFModule = r3
            r0.coreImplModule = r3
        L_0x00f3:
            boolean r2 = com.uc.webview.export.internal.utility.j.a(r4)
            if (r2 == 0) goto L_0x0104
            android.content.pm.ApplicationInfo r2 = r17.getApplicationInfo()
            java.lang.String r2 = r2.nativeLibraryDir
            java.lang.String r2 = a(r2)
            goto L_0x0105
        L_0x0104:
            r2 = r4
        L_0x0105:
            java.lang.String[] r6 = a
            int r7 = r6.length
            r8 = r3
            r3 = 0
        L_0x010a:
            if (r3 >= r7) goto L_0x0142
            r9 = r6[r3]
            java.io.File r10 = new java.io.File
            java.lang.StringBuilder r12 = new java.lang.StringBuilder
            java.lang.String r13 = "lib"
            r12.<init>(r13)
            r12.append(r9)
            java.lang.String r13 = ".so"
            r12.append(r13)
            java.lang.String r12 = r12.toString()
            r10.<init>(r2, r12)
            boolean r12 = r10.exists()
            if (r12 == 0) goto L_0x013f
            long r12 = r10.lastModified()
            r14 = 0
            int r12 = (r12 > r14 ? 1 : (r12 == r14 ? 0 : -1))
            if (r12 <= 0) goto L_0x013e
            long r12 = r10.lastModified()
            int r10 = (r12 > r14 ? 1 : (r12 == r14 ? 0 : -1))
            if (r10 <= 0) goto L_0x013f
        L_0x013e:
            r8 = r9
        L_0x013f:
            int r3 = r3 + 1
            goto L_0x010a
        L_0x0142:
            if (r8 != 0) goto L_0x015f
            boolean r3 = com.uc.webview.export.internal.utility.j.a(r4)
            if (r3 != 0) goto L_0x015f
            com.uc.webview.export.internal.setup.UCSetupException r1 = new com.uc.webview.export.internal.setup.UCSetupException
            r2 = 3001(0xbb9, float:4.205E-42)
            java.lang.String r3 = "Main so file U4 [%s] not exists."
            java.lang.Object[] r4 = new java.lang.Object[r5]
            java.lang.String r5 = "webviewuc"
            r4[r11] = r5
            java.lang.String r3 = java.lang.String.format(r3, r4)
            r1.<init>(r2, r3)
            throw r1
        L_0x015f:
            r0.mainLibrary = r8
            java.lang.String r3 = "webviewuc"
            java.lang.String r4 = r0.mainLibrary
            boolean r3 = r3.equals(r4)
            if (r3 == 0) goto L_0x0170
            java.lang.String r3 = "u4"
            goto L_0x0179
        L_0x0170:
            java.lang.String r3 = r0.mainLibrary
            if (r3 != 0) goto L_0x0177
            java.lang.String r3 = "null"
            goto L_0x0179
        L_0x0177:
            java.lang.String r3 = "error"
        L_0x0179:
            r0.coreCode = r3
            java.lang.String r3 = r0.coreCode
            java.lang.String r4 = "u4"
            boolean r3 = r3.equals(r4)
            if (r3 == 0) goto L_0x01e2
            java.lang.String[] r3 = b
            int r4 = r3.length
            r5 = 0
        L_0x018a:
            if (r5 >= r4) goto L_0x01e2
            r6 = r3[r5]
            java.io.File r7 = new java.io.File
            android.content.pm.ApplicationInfo r8 = r17.getApplicationInfo()
            java.lang.String r8 = r8.nativeLibraryDir
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            java.lang.String r10 = "lib"
            r9.<init>(r10)
            r9.append(r6)
            java.lang.String r10 = ".so"
            r9.append(r10)
            java.lang.String r9 = r9.toString()
            r7.<init>(r8, r9)
            java.io.File r8 = new java.io.File
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            java.lang.String r10 = "lib"
            r9.<init>(r10)
            r9.append(r6)
            java.lang.String r6 = ".so"
            r9.append(r6)
            java.lang.String r6 = r9.toString()
            r8.<init>(r2, r6)
            boolean r6 = r7.exists()
            if (r6 == 0) goto L_0x01df
            long r9 = r7.lastModified()
            boolean r6 = r8.exists()
            if (r6 == 0) goto L_0x01dc
            long r12 = r8.lastModified()
            int r6 = (r12 > r9 ? 1 : (r12 == r9 ? 0 : -1))
            if (r6 >= 0) goto L_0x01df
        L_0x01dc:
            com.uc.webview.export.internal.utility.j.a(r7, r8, r8, r11)
        L_0x01df:
            int r5 = r5 + 1
            goto L_0x018a
        L_0x01e2:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.uc.webview.export.internal.setup.UCMPackageInfo.<init>(android.content.Context, java.lang.String, java.lang.String, java.lang.String, java.lang.String, boolean, java.lang.String, java.lang.String, java.lang.String, java.lang.String, boolean):void");
    }

    public String getDirAlias(Context context) {
        if (!(this.coreImplModule == null || this.coreImplModule.first == null)) {
            String str = (String) this.coreImplModule.first;
            if (str != null) {
                if (str.startsWith(((File) invoke(10003, context)).getAbsolutePath())) {
                    return ConfigConstants.APMULTIMEDIA_DEVICE_CONFIG_LIVEPLAY_DECODE_TYPE_KEY;
                }
                if (str.startsWith(((File) invoke(10002, context)).getAbsolutePath())) {
                    return "upd";
                }
                if (str.startsWith(((File) invoke(10007, context)).getAbsolutePath())) {
                    return "kjl";
                }
                if (str.startsWith(((File) invoke(10009, context)).getAbsolutePath())) {
                    return "kjc";
                }
                return str.startsWith(((File) invoke(10006, context)).getAbsolutePath()) ? "rep" : "oth";
            }
        }
        return "nul";
    }

    public Map<String, String> getFileHashs() {
        HashMap hashMap = new HashMap(16);
        if (this.coreImplModule == null || this.coreImplModule.first == null) {
            hashMap.put("core", "null");
        } else {
            File file = new File((String) this.coreImplModule.first);
            hashMap.put(file.getName(), UCCyclone.hashFileContents(file, MessageDigestType.MD5));
        }
        if (this.browserIFModule == null || this.browserIFModule.first == null) {
            hashMap.put("browser_if", "null");
        } else {
            File file2 = new File((String) this.browserIFModule.first);
            hashMap.put(file2.getName(), UCCyclone.hashFileContents(file2, MessageDigestType.MD5));
        }
        if (this.sdkShellModule == null || this.sdkShellModule.first == null) {
            hashMap.put("sdk_shell", "null");
        } else {
            File file3 = new File((String) this.sdkShellModule.first);
            hashMap.put(file3.getName(), UCCyclone.hashFileContents(file3, MessageDigestType.MD5));
        }
        String str = this.soDirPath;
        if (j.a(str)) {
            str = this.c.getApplicationInfo().nativeLibraryDir;
        }
        if (str != null) {
            File file4 = new File(str);
            if (file4.isDirectory()) {
                try {
                    if (this.mSdkShellClassLoader != null) {
                        Class<?> cls = Class.forName("com.uc.webview.browser.shell.NativeLibraries", true, this.mSdkShellClassLoader);
                        if (cls != null) {
                            Field declaredField = cls.getDeclaredField("LIBRARIES");
                            declaredField.setAccessible(true);
                            String[][] strArr = (String[][]) declaredField.get(null);
                            if (strArr != null) {
                                for (String[] strArr2 : strArr) {
                                    String str2 = strArr2[0];
                                    j.d(strArr2[1]);
                                    String str3 = strArr2[2];
                                    String hashFileContents = UCCyclone.hashFileContents(new File(file4, str2), MessageDigestType.MD5);
                                    if (j.a(str3) || str3.equals(hashFileContents)) {
                                        hashMap.put(str2, Value.OK);
                                    } else {
                                        hashMap.put(str2, hashFileContents);
                                    }
                                }
                            } else {
                                hashMap.put("NativeLibraries", "null");
                            }
                        }
                    } else {
                        hashMap.put("sdk_shell_cl", "null");
                    }
                } catch (Throwable unused) {
                    hashMap.put("NativeLibraries", LogCategory.CATEGORY_EXCEPTION);
                }
            } else {
                hashMap.put("so_dir", "null");
            }
        } else {
            hashMap.put("so_path", "null");
        }
        return hashMap;
    }

    static synchronized List<UCMPackageInfo> a(Context context, ConcurrentHashMap<String, Object> concurrentHashMap) {
        synchronized (UCMPackageInfo.class) {
            List<UCMPackageInfo> arrayList = new ArrayList<>();
            String str = (String) concurrentHashMap.get(UCCore.OPTION_DEX_FILE_PATH);
            if (!j.a(str)) {
                arrayList = a(context, new File(str), arrayList);
            }
            String str2 = (String) concurrentHashMap.get(UCCore.OPTION_SET_ODEX_ROOT_PATH);
            if (str2 == null) {
                str2 = ((File) invoke(10004, context)).getAbsolutePath();
            }
            if (arrayList.size() == 0) {
                UCMPackageInfo a2 = a(context, null, (String) concurrentHashMap.get(UCCore.OPTION_DEX_FILE_PATH), (String) concurrentHashMap.get(UCCore.OPTION_SO_FILE_PATH), (String) concurrentHashMap.get(UCCore.OPTION_RES_FILE_PATH), str2);
                if (a2 != null) {
                    arrayList.add(a2);
                }
                if (((Boolean) invoke(10011, new Object[0])).booleanValue()) {
                    return arrayList;
                }
            }
            String str3 = (String) concurrentHashMap.get(UCCore.OPTION_UCM_KRL_DIR);
            if (!j.a(str3)) {
                arrayList = a(context, new File(str3), arrayList);
            }
            String str4 = (String) concurrentHashMap.get(UCCore.OPTION_UCM_LIB_DIR);
            if (!j.a(str4)) {
                arrayList.add(c(context, str4, str2));
            }
            List<UCMPackageInfo> list = (List) invoke(sortByLastModified, arrayList);
            return list;
        }
    }

    /* JADX WARNING: type inference failed for: r1v13 */
    /* JADX WARNING: Can't wrap try/catch for region: R(7:86|87|98|99|100|101|102) */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:100:0x01f7 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:77:0x01d0 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:83:0x01d7 */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x0075  */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.Object invoke(int r7, java.lang.Object... r8) {
        /*
            r0 = 10001(0x2711, float:1.4014E-41)
        L_0x0002:
            r1 = 10035(0x2733, float:1.4062E-41)
            r2 = 1
            r3 = 0
            if (r7 == r1) goto L_0x040d
            r1 = 0
            r4 = 2
            switch(r7) {
                case 10001: goto L_0x03ed;
                case 10002: goto L_0x03dc;
                case 10003: goto L_0x03cf;
                case 10004: goto L_0x03c2;
                case 10005: goto L_0x03b5;
                case 10006: goto L_0x03a8;
                case 10007: goto L_0x039b;
                case 10008: goto L_0x038e;
                case 10009: goto L_0x0381;
                case 10010: goto L_0x0374;
                case 10011: goto L_0x0369;
                case 10012: goto L_0x0354;
                default: goto L_0x000d;
            }
        L_0x000d:
            switch(r7) {
                case 10021: goto L_0x0341;
                case 10022: goto L_0x0333;
                case 10023: goto L_0x032d;
                case 10024: goto L_0x031f;
                case 10025: goto L_0x030a;
                case 10026: goto L_0x02d5;
                case 10027: goto L_0x0287;
                case 10028: goto L_0x0256;
                default: goto L_0x0010;
            }
        L_0x0010:
            switch(r7) {
                case 10032: goto L_0x0235;
                case 10033: goto L_0x021f;
                default: goto L_0x0013;
            }
        L_0x0013:
            switch(r7) {
                case 10039: goto L_0x01fc;
                case 10040: goto L_0x017b;
                case 10041: goto L_0x00f0;
                case 10042: goto L_0x00c8;
                case 10043: goto L_0x008d;
                case 10044: goto L_0x0042;
                case 10045: goto L_0x0034;
                case 10046: goto L_0x0026;
                case 10047: goto L_0x0018;
                default: goto L_0x0016;
            }
        L_0x0016:
            goto L_0x021e
        L_0x0018:
            r7 = r8[r3]
            android.content.Context r7 = (android.content.Context) r7
            java.lang.Object[] r8 = new java.lang.Object[r4]
            r8[r3] = r7
            java.lang.String r7 = "share/kjcopies"
            r8[r2] = r7
            goto L_0x03e9
        L_0x0026:
            r7 = r8[r3]
            android.content.Context r7 = (android.content.Context) r7
            java.lang.Object[] r8 = new java.lang.Object[r4]
            r8[r3] = r7
            java.lang.String r7 = "share/kjlinks"
            r8[r2] = r7
            goto L_0x03e9
        L_0x0034:
            r7 = r8[r3]
            android.content.Context r7 = (android.content.Context) r7
            java.lang.Object[] r8 = new java.lang.Object[r4]
            r8[r3] = r7
            java.lang.String r7 = "rep_apollo"
            r8[r2] = r7
            goto L_0x03e9
        L_0x0042:
            r7 = r8[r3]
            android.content.Context r7 = (android.content.Context) r7
            r0 = r8[r2]
            java.io.File r0 = (java.io.File) r0
            r8 = r8[r4]
            java.io.File r8 = (java.io.File) r8
            boolean r5 = r0.isFile()
            if (r5 == 0) goto L_0x0057
            r6 = r1
        L_0x0055:
            r5 = 1
            goto L_0x0073
        L_0x0057:
            boolean r5 = r0.isDirectory()
            if (r5 == 0) goto L_0x0071
            r5 = 10043(0x273b, float:1.4073E-41)
            java.lang.Object[] r6 = new java.lang.Object[r4]
            r6[r3] = r7
            r6[r2] = r0
            java.lang.Object r5 = invoke(r5, r6)
            java.io.File r5 = (java.io.File) r5
            if (r5 == 0) goto L_0x006f
            r6 = r5
            goto L_0x0055
        L_0x006f:
            r6 = r5
            goto L_0x0072
        L_0x0071:
            r6 = r1
        L_0x0072:
            r5 = 0
        L_0x0073:
            if (r5 == 0) goto L_0x021e
            com.uc.webview.export.cyclone.UCCyclone.recursiveDelete(r0, r3, r8)
            boolean r8 = r0.exists()
            if (r8 != 0) goto L_0x021e
            if (r6 == 0) goto L_0x021e
            r8 = 10042(0x273a, float:1.4072E-41)
            java.lang.Object[] r0 = new java.lang.Object[r4]
            r0[r3] = r7
            r0[r2] = r6
            invoke(r8, r0)
            goto L_0x021e
        L_0x008d:
            r7 = r8[r3]
            android.content.Context r7 = (android.content.Context) r7
            r8 = r8[r2]
            java.io.File r8 = (java.io.File) r8
            r0 = 10005(0x2715, float:1.402E-41)
            java.lang.Object[] r2 = new java.lang.Object[r2]
            r2[r3] = r7
            java.lang.Object r7 = invoke(r0, r2)
            java.io.File r7 = (java.io.File) r7
            java.io.File r0 = new java.io.File
            java.lang.String r2 = "setup_delete"
            r0.<init>(r7, r2)
            java.io.File r7 = com.uc.webview.export.cyclone.UCCyclone.expectCreateDirFile(r0)
            java.lang.String r8 = r8.getAbsolutePath()
            java.lang.String r8 = com.uc.webview.export.cyclone.UCCyclone.getSourceHash(r8)
            java.io.File r0 = new java.io.File
            r0.<init>(r7, r8)
            java.io.File r7 = com.uc.webview.export.cyclone.UCCyclone.expectCreateDirFile(r0)
            java.lang.String[] r8 = r7.list()
            int r8 = r8.length
            r0 = 12
            if (r8 < r0) goto L_0x00c7
            return r1
        L_0x00c7:
            return r7
        L_0x00c8:
            r7 = r8[r2]
            java.io.File r7 = (java.io.File) r7
            long r2 = java.lang.System.currentTimeMillis()
            java.lang.String r8 = java.lang.String.valueOf(r2)
            java.io.File r0 = new java.io.File     // Catch:{ Throwable -> 0x00e7 }
            r0.<init>(r7, r8)     // Catch:{ Throwable -> 0x00e7 }
            boolean r7 = r0.createNewFile()     // Catch:{ Throwable -> 0x00e7 }
            if (r7 != 0) goto L_0x021e
            java.lang.Exception r7 = new java.lang.Exception     // Catch:{ Throwable -> 0x00e7 }
            java.lang.String r8 = "createNewFile return false"
            r7.<init>(r8)     // Catch:{ Throwable -> 0x00e7 }
            throw r7     // Catch:{ Throwable -> 0x00e7 }
        L_0x00e7:
            r7 = move-exception
            com.uc.webview.export.cyclone.UCKnownException r8 = new com.uc.webview.export.cyclone.UCKnownException
            r0 = 1006(0x3ee, float:1.41E-42)
            r8.<init>(r0, r7)
            throw r8
        L_0x00f0:
            r7 = r8[r3]
            java.lang.ClassLoader r7 = (java.lang.ClassLoader) r7
            if (r7 != 0) goto L_0x00ff
            java.lang.String r8 = "com.uc.webview.browser.shell.Build$Version"
            java.lang.Class r8 = java.lang.Class.forName(r8)     // Catch:{ Exception -> 0x00fd }
            goto L_0x0105
        L_0x00fd:
            r8 = move-exception
            goto L_0x0132
        L_0x00ff:
            java.lang.String r8 = "com.uc.webview.browser.shell.Build$Version"
            java.lang.Class r8 = java.lang.Class.forName(r8, r3, r7)     // Catch:{ Exception -> 0x00fd }
        L_0x0105:
            java.lang.String r0 = "NAME"
            java.lang.reflect.Field r0 = r8.getField(r0)     // Catch:{ Exception -> 0x00fd }
            java.lang.Object r0 = r0.get(r1)     // Catch:{ Exception -> 0x00fd }
            java.lang.String r0 = r0.toString()     // Catch:{ Exception -> 0x00fd }
            java.lang.String r4 = "SUPPORT_SDK_MIN"
            java.lang.reflect.Field r4 = r8.getField(r4)     // Catch:{ Exception -> 0x00fd }
            java.lang.Object r4 = r4.get(r1)     // Catch:{ Exception -> 0x00fd }
            java.lang.String r4 = r4.toString()     // Catch:{ Exception -> 0x00fd }
            com.uc.webview.export.Build.UCM_VERSION = r0     // Catch:{ Exception -> 0x00fd }
            com.uc.webview.export.Build.UCM_SUPPORT_SDK_MIN = r4     // Catch:{ Exception -> 0x00fd }
            java.lang.String r0 = "API_LEVEL"
            java.lang.reflect.Field r8 = r8.getField(r0)     // Catch:{ Exception -> 0x00fd }
            int r8 = r8.getInt(r1)     // Catch:{ Exception -> 0x00fd }
            com.uc.webview.export.Build.Version.API_LEVEL = r8     // Catch:{ Exception -> 0x00fd }
            goto L_0x013b
        L_0x0132:
            java.lang.String r0 = "UCMPackageInfo"
            java.lang.String r4 = "exception"
            com.uc.webview.export.internal.utility.Log.d(r0, r4, r8)
            com.uc.webview.export.Build.Version.API_LEVEL = r2
        L_0x013b:
            java.lang.String r8 = "com.uc.webview.browser.shell.Build"
            java.lang.Class r7 = java.lang.Class.forName(r8, r3, r7)     // Catch:{ Exception -> 0x0161 }
            java.lang.String r8 = "CORE_VERSION"
            java.lang.reflect.Field r8 = r7.getField(r8)     // Catch:{ Exception -> 0x0161 }
            java.lang.Object r8 = r8.get(r1)     // Catch:{ Exception -> 0x0161 }
            java.lang.String r8 = r8.toString()     // Catch:{ Exception -> 0x0161 }
            com.uc.webview.export.Build.CORE_VERSION = r8     // Catch:{ Exception -> 0x0161 }
            java.lang.String r8 = "TIME"
            java.lang.reflect.Field r7 = r7.getField(r8)     // Catch:{ Exception -> 0x0161 }
            java.lang.Object r7 = r7.get(r1)     // Catch:{ Exception -> 0x0161 }
            java.lang.String r7 = r7.toString()     // Catch:{ Exception -> 0x0161 }
            com.uc.webview.export.Build.CORE_TIME = r7     // Catch:{ Exception -> 0x0161 }
        L_0x0161:
            r7 = 10052(0x2744, float:1.4086E-41)
            java.lang.Object[] r8 = new java.lang.Object[r3]
            java.lang.Object r7 = com.uc.webview.export.internal.SDKFactory.invoke(r7, r8)
            java.lang.Boolean r7 = (java.lang.Boolean) r7
            boolean r7 = r7.booleanValue()
            if (r7 == 0) goto L_0x021e
            com.uc.webview.export.internal.setup.ce r7 = new com.uc.webview.export.internal.setup.ce
            r7.<init>()
            com.uc.webview.export.internal.utility.j.a(r7)
            goto L_0x021e
        L_0x017b:
            r7 = r8[r3]
            java.lang.String r7 = (java.lang.String) r7
            java.io.File r8 = new java.io.File
            java.lang.String r0 = "curver"
            r8.<init>(r7, r0)
            boolean r7 = r8.exists()
            if (r7 == 0) goto L_0x01fb
            java.io.FileReader r7 = new java.io.FileReader     // Catch:{ Exception -> 0x01e7, all -> 0x01e3 }
            r7.<init>(r8)     // Catch:{ Exception -> 0x01e7, all -> 0x01e3 }
            java.io.BufferedReader r8 = new java.io.BufferedReader     // Catch:{ Exception -> 0x01e0, all -> 0x01dd }
            r8.<init>(r7)     // Catch:{ Exception -> 0x01e0, all -> 0x01dd }
            java.lang.String r0 = r8.readLine()     // Catch:{ Exception -> 0x01db }
            if (r0 == 0) goto L_0x01d4
            java.lang.String r0 = r0.trim()     // Catch:{ Exception -> 0x01db }
            java.lang.String r2 = "/"
            boolean r2 = r0.startsWith(r2)     // Catch:{ Exception -> 0x01db }
            if (r2 == 0) goto L_0x01a9
            goto L_0x01b3
        L_0x01a9:
            java.lang.String r2 = "/"
            java.lang.String r0 = java.lang.String.valueOf(r0)     // Catch:{ Exception -> 0x01db }
            java.lang.String r0 = r2.concat(r0)     // Catch:{ Exception -> 0x01db }
        L_0x01b3:
            java.lang.String r2 = "/"
            boolean r2 = r0.endsWith(r2)     // Catch:{ Exception -> 0x01db }
            if (r2 == 0) goto L_0x01bc
            goto L_0x01cd
        L_0x01bc:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x01db }
            r2.<init>()     // Catch:{ Exception -> 0x01db }
            r2.append(r0)     // Catch:{ Exception -> 0x01db }
            java.lang.String r0 = "/"
            r2.append(r0)     // Catch:{ Exception -> 0x01db }
            java.lang.String r0 = r2.toString()     // Catch:{ Exception -> 0x01db }
        L_0x01cd:
            r8.close()     // Catch:{ Exception -> 0x01d0 }
        L_0x01d0:
            r7.close()     // Catch:{ Exception -> 0x01d3 }
        L_0x01d3:
            return r0
        L_0x01d4:
            r8.close()     // Catch:{ Exception -> 0x01d7 }
        L_0x01d7:
            r7.close()     // Catch:{ Exception -> 0x01fb }
            goto L_0x01fb
        L_0x01db:
            r0 = move-exception
            goto L_0x01ea
        L_0x01dd:
            r0 = move-exception
            r8 = r1
            goto L_0x01f4
        L_0x01e0:
            r0 = move-exception
            r8 = r1
            goto L_0x01ea
        L_0x01e3:
            r0 = move-exception
            r7 = r1
            r8 = r7
            goto L_0x01f4
        L_0x01e7:
            r0 = move-exception
            r7 = r1
            r8 = r7
        L_0x01ea:
            java.lang.String r2 = "tag_test_log"
            java.lang.String r3 = "getVersion"
            com.uc.webview.export.internal.utility.Log.i(r2, r3, r0)     // Catch:{ all -> 0x01f3 }
            goto L_0x01d4
        L_0x01f3:
            r0 = move-exception
        L_0x01f4:
            r8.close()     // Catch:{ Exception -> 0x01f7 }
        L_0x01f7:
            r7.close()     // Catch:{ Exception -> 0x01fa }
        L_0x01fa:
            throw r0
        L_0x01fb:
            return r1
        L_0x01fc:
            r7 = r8[r3]
            android.content.Context r7 = (android.content.Context) r7
            java.io.File r7 = r7.getCacheDir()
            com.uc.webview.export.internal.setup.cf r8 = new com.uc.webview.export.internal.setup.cf
            r8.<init>()
            java.io.File[] r7 = r7.listFiles(r8)
            if (r7 == 0) goto L_0x021e
            int r8 = r7.length
            if (r8 <= 0) goto L_0x021e
            int r8 = r7.length
            r0 = 0
        L_0x0214:
            if (r0 >= r8) goto L_0x021e
            r2 = r7[r0]
            com.uc.webview.export.cyclone.UCCyclone.recursiveDelete(r2, r3, r1)
            int r0 = r0 + 1
            goto L_0x0214
        L_0x021e:
            return r1
        L_0x021f:
            r7 = r8[r3]
            java.lang.String r7 = (java.lang.String) r7
            r8 = 10032(0x2730, float:1.4058E-41)
            java.lang.Object[] r0 = new java.lang.Object[r2]
            java.io.File r1 = new java.io.File
            r1.<init>(r7)
            r0[r3] = r1
            java.lang.Object r7 = invoke(r8, r0)
            java.io.File r7 = (java.io.File) r7
            return r7
        L_0x0235:
            r7 = r8[r3]
            java.io.File r7 = (java.io.File) r7
            boolean r8 = r7.exists()
            if (r8 != 0) goto L_0x0255
            com.uc.webview.export.internal.setup.UCSetupException r8 = new com.uc.webview.export.internal.setup.UCSetupException
            r0 = 1002(0x3ea, float:1.404E-42)
            java.lang.String r1 = "Directory [%s] not exists."
            java.lang.Object[] r2 = new java.lang.Object[r2]
            java.lang.String r7 = r7.getAbsolutePath()
            r2[r3] = r7
            java.lang.String r7 = java.lang.String.format(r1, r2)
            r8.<init>(r0, r7)
            throw r8
        L_0x0255:
            return r7
        L_0x0256:
            r7 = r8[r3]
            java.lang.String r7 = (java.lang.String) r7
            int r8 = com.uc.webview.export.Build.PACK_TYPE
            r0 = 34
            if (r8 == r0) goto L_0x0267
            int r8 = com.uc.webview.export.Build.PACK_TYPE
            r0 = 43
            if (r8 == r0) goto L_0x0267
            return r1
        L_0x0267:
            r8 = 10033(0x2731, float:1.4059E-41)
            java.lang.Object[] r0 = new java.lang.Object[r2]
            r0[r3] = r7
            java.lang.Object r7 = invoke(r8, r0)
            java.io.File r7 = (java.io.File) r7
            r8 = 10022(0x2726, float:1.4044E-41)
            java.lang.Object[] r0 = new java.lang.Object[r2]
            r0[r3] = r7
            java.lang.Object r7 = invoke(r8, r0)
            java.io.File[] r7 = (java.io.File[]) r7
            if (r7 == 0) goto L_0x0286
            int r8 = r7.length
            if (r8 <= 0) goto L_0x0286
            r1 = r7[r3]
        L_0x0286:
            return r1
        L_0x0287:
            r7 = r8[r3]
            java.lang.String r7 = (java.lang.String) r7
            r0 = r8[r2]
            java.lang.String r0 = (java.lang.String) r0
            r8 = r8[r4]
            java.lang.String r8 = (java.lang.String) r8
            java.lang.String r1 = "\\."
            java.lang.String[] r7 = r7.split(r1)
            java.lang.String r1 = "\\."
            java.lang.String[] r0 = r0.split(r1)
            r1 = r7[r3]
            int r1 = com.uc.webview.export.internal.utility.j.c(r1)
            r3 = r0[r3]
            int r3 = com.uc.webview.export.internal.utility.j.c(r3)
            if (r1 < r3) goto L_0x02cd
            r1 = r7[r2]
            int r1 = com.uc.webview.export.internal.utility.j.c(r1)
            r2 = r0[r2]
            int r2 = com.uc.webview.export.internal.utility.j.c(r2)
            if (r1 < r2) goto L_0x02cd
            r7 = r7[r4]
            int r7 = com.uc.webview.export.internal.utility.j.c(r7)
            r0 = r0[r4]
            int r0 = com.uc.webview.export.internal.utility.j.c(r0)
            if (r7 >= r0) goto L_0x02ca
            goto L_0x02cd
        L_0x02ca:
            java.lang.Boolean r7 = java.lang.Boolean.TRUE
            return r7
        L_0x02cd:
            java.lang.String r7 = "UCMPackageInfo"
            com.uc.webview.export.internal.utility.Log.d(r7, r8)
            java.lang.Boolean r7 = java.lang.Boolean.FALSE
            return r7
        L_0x02d5:
            r7 = r8[r3]
            android.content.Context r7 = (android.content.Context) r7
            android.content.pm.PackageManager r7 = r7.getPackageManager()
            r8 = 64
            java.util.List r7 = r7.getInstalledPackages(r8)
            java.util.Iterator r7 = r7.iterator()
        L_0x02e7:
            boolean r8 = r7.hasNext()
            if (r8 == 0) goto L_0x0304
            java.lang.Object r8 = r7.next()
            android.content.pm.PackageInfo r8 = (android.content.pm.PackageInfo) r8
            java.lang.String r0 = r8.packageName
            java.lang.String r1 = "com.UCMobile"
            boolean r0 = r0.startsWith(r1)
            if (r0 == 0) goto L_0x02e7
            android.content.pm.ApplicationInfo r8 = r8.applicationInfo
            boolean r8 = r8.enabled
            if (r8 == 0) goto L_0x02e7
            goto L_0x0305
        L_0x0304:
            r2 = 0
        L_0x0305:
            java.lang.Boolean r7 = java.lang.Boolean.valueOf(r2)
            return r7
        L_0x030a:
            r7 = r8[r3]
            java.util.List r7 = (java.util.List) r7
            if (r7 == 0) goto L_0x031e
            int r8 = r7.size()
            if (r8 <= r2) goto L_0x031e
            com.uc.webview.export.internal.setup.cd r8 = new com.uc.webview.export.internal.setup.cd
            r8.<init>()
            java.util.Collections.sort(r7, r8)
        L_0x031e:
            return r7
        L_0x031f:
            r7 = r8[r3]
            java.io.File r7 = (java.io.File) r7
            com.uc.webview.export.internal.setup.cc r8 = new com.uc.webview.export.internal.setup.cc
            r8.<init>()
            java.lang.String[] r7 = r7.list(r8)
            return r7
        L_0x032d:
            com.uc.webview.export.internal.setup.cb r7 = new com.uc.webview.export.internal.setup.cb
            r7.<init>()
            return r7
        L_0x0333:
            r7 = r8[r3]
            java.io.File r7 = (java.io.File) r7
            com.uc.webview.export.internal.setup.ca r8 = new com.uc.webview.export.internal.setup.ca
            r8.<init>()
            java.io.File[] r7 = r7.listFiles(r8)
            return r7
        L_0x0341:
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            java.lang.String r8 = "/unexists/"
            r7.<init>(r8)
            long r0 = java.lang.System.currentTimeMillis()
            r7.append(r0)
            java.lang.String r7 = r7.toString()
            return r7
        L_0x0354:
            r7 = r8[r3]
            java.lang.String r7 = (java.lang.String) r7
            int r7 = r7.hashCode()
            java.lang.String r7 = java.lang.String.valueOf(r7)
            r8 = 45
            r0 = 95
            java.lang.String r7 = r7.replace(r8, r0)
            return r7
        L_0x0369:
            java.lang.String r7 = "com.uc.webkit.sdk.CoreFactoryImpl"
            java.lang.Class.forName(r7)     // Catch:{ Throwable -> 0x0371 }
            java.lang.Boolean r7 = java.lang.Boolean.TRUE     // Catch:{ Throwable -> 0x0371 }
            return r7
        L_0x0371:
            java.lang.Boolean r7 = java.lang.Boolean.FALSE
            return r7
        L_0x0374:
            r7 = r8[r3]
            android.content.Context r7 = (android.content.Context) r7
            java.lang.Object[] r8 = new java.lang.Object[r4]
            r8[r3] = r7
            java.lang.String r7 = "krcopies"
            r8[r2] = r7
            goto L_0x03e9
        L_0x0381:
            r7 = r8[r3]
            android.content.Context r7 = (android.content.Context) r7
            java.lang.Object[] r8 = new java.lang.Object[r4]
            r8[r3] = r7
            java.lang.String r7 = "kjcopies"
            r8[r2] = r7
            goto L_0x03e9
        L_0x038e:
            r7 = r8[r3]
            android.content.Context r7 = (android.content.Context) r7
            java.lang.Object[] r8 = new java.lang.Object[r4]
            r8[r3] = r7
            java.lang.String r7 = "krlinks"
            r8[r2] = r7
            goto L_0x03e9
        L_0x039b:
            r7 = r8[r3]
            android.content.Context r7 = (android.content.Context) r7
            java.lang.Object[] r8 = new java.lang.Object[r4]
            r8[r3] = r7
            java.lang.String r7 = "kjlinks"
            r8[r2] = r7
            goto L_0x03e9
        L_0x03a8:
            r7 = r8[r3]
            android.content.Context r7 = (android.content.Context) r7
            java.lang.Object[] r8 = new java.lang.Object[r4]
            r8[r3] = r7
            java.lang.String r7 = "repairs"
            r8[r2] = r7
            goto L_0x03e9
        L_0x03b5:
            r7 = r8[r3]
            android.content.Context r7 = (android.content.Context) r7
            java.lang.Object[] r8 = new java.lang.Object[r4]
            r8[r3] = r7
            java.lang.String r7 = "flags"
            r8[r2] = r7
            goto L_0x03e9
        L_0x03c2:
            r7 = r8[r3]
            android.content.Context r7 = (android.content.Context) r7
            java.lang.Object[] r8 = new java.lang.Object[r4]
            r8[r3] = r7
            java.lang.String r7 = "odexs"
            r8[r2] = r7
            goto L_0x03e9
        L_0x03cf:
            r7 = r8[r3]
            android.content.Context r7 = (android.content.Context) r7
            java.lang.Object[] r8 = new java.lang.Object[r4]
            r8[r3] = r7
            java.lang.String r7 = "decompresses2"
            r8[r2] = r7
            goto L_0x03e9
        L_0x03dc:
            r7 = r8[r3]
            android.content.Context r7 = (android.content.Context) r7
            java.lang.Object[] r8 = new java.lang.Object[r4]
            r8[r3] = r7
            java.lang.String r7 = "updates"
            r8[r2] = r7
        L_0x03e9:
            r7 = 10001(0x2711, float:1.4014E-41)
            goto L_0x0002
        L_0x03ed:
            r7 = r8[r3]
            android.content.Context r7 = (android.content.Context) r7
            int r0 = r8.length
            if (r0 < r4) goto L_0x03f9
            r8 = r8[r2]
            r1 = r8
            java.lang.String r1 = (java.lang.String) r1
        L_0x03f9:
            java.lang.String r8 = "ucmsdk"
            java.io.File r7 = r7.getDir(r8, r3)
            if (r1 != 0) goto L_0x0403
            return r7
        L_0x0403:
            java.io.File r8 = new java.io.File
            r8.<init>(r7, r1)
            java.io.File r7 = com.uc.webview.export.cyclone.UCCyclone.expectCreateDirFile(r8)
            return r7
        L_0x040d:
            r7 = r8[r3]
            java.io.File r7 = (java.io.File) r7
            r8 = r8[r2]
            java.lang.String r8 = (java.lang.String) r8
            java.io.File r0 = new java.io.File
            r0.<init>(r7, r8)
            java.io.File r7 = com.uc.webview.export.cyclone.UCCyclone.expectCreateDirFile(r0)
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.uc.webview.export.internal.setup.UCMPackageInfo.invoke(int, java.lang.Object[]):java.lang.Object");
    }

    private static String a(Context context, String str, String str2) {
        if (j.a(str2)) {
            return null;
        }
        boolean z = false;
        File file = (File) invoke(expectDirFile1S, str2);
        String[] strArr = (String[]) invoke(getKernelResFiles, file);
        if (strArr == null || strArr.length == 0) {
            return null;
        }
        String str3 = (String) invoke(10012, str);
        File file2 = (File) invoke(expectCreateDirFile2P, (File) invoke(10008, context), str3);
        File file3 = (File) invoke(expectCreateDirFile2P, (File) invoke(10010, context), str3);
        File file4 = (File) invoke(expectCreateDirFile2P, file2, RES_PAKS_DIR_NAME);
        File file5 = (File) invoke(expectCreateDirFile2P, file3, RES_PAKS_DIR_NAME);
        File[] fileArr = new File[strArr.length];
        File[] fileArr2 = new File[strArr.length];
        File[] fileArr3 = new File[strArr.length];
        UCElapseTime uCElapseTime = new UCElapseTime();
        int i = 0;
        while (i < strArr.length) {
            String str4 = strArr[i];
            boolean endsWith = str4.endsWith("_pak_kr_uc.so");
            String substring = str4.substring(3, str4.length() - 9);
            int lastIndexOf = substring.lastIndexOf(95);
            File file6 = file4;
            StringBuilder sb = new StringBuilder();
            File file7 = file5;
            sb.append(substring.substring(0, lastIndexOf));
            sb.append(DjangoUtils.EXTENSION_SEPARATOR);
            sb.append(substring.substring(lastIndexOf + 1));
            String sb2 = sb.toString();
            File file8 = new File(file, str4);
            File file9 = new File(endsWith ? file6 : file2, sb2);
            File file10 = new File(endsWith ? file7 : file3, sb2);
            fileArr[i] = file8;
            fileArr2[i] = file9;
            fileArr3[i] = file10;
            i++;
            file4 = file6;
            file5 = file7;
        }
        File[] a2 = j.a(fileArr, fileArr2, fileArr3);
        StringBuilder sb3 = new StringBuilder("getLnkOrCpyResDirFromSO: file count:");
        sb3.append(strArr.length);
        sb3.append(" time:");
        sb3.append(uCElapseTime.getMilis());
        if (a2[0] == fileArr2[0]) {
            z = true;
        }
        if (!z) {
            return file3.getAbsolutePath();
        }
        return file2.getAbsolutePath();
    }

    private static String b(Context context, String str, String str2) {
        if (j.a(str2)) {
            return null;
        }
        File file = new File(str2);
        String name = file.getName();
        if (!name.startsWith("lib") || !name.endsWith("_jar_kj_uc.so")) {
            return str2;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(name.substring(3, name.length() - 13));
        sb.append(IBundleOperator.EXTENSION);
        String sb2 = sb.toString();
        String str3 = (String) invoke(10012, str);
        return j.a(file, new File((File) invoke(expectCreateDirFile2P, (File) invoke(10007, context), str3), sb2), new File((File) invoke(expectCreateDirFile2P, (File) invoke(10009, context), str3), sb2), false).getAbsolutePath();
    }

    private static UCMPackageInfo c(Context context, String str, String str2) throws UCSetupException {
        String[] strArr;
        String str3;
        String str4;
        String str5 = str;
        loop0:
        while (true) {
            File file = (File) invoke(expectDirFile1S, str5);
            try {
                String absolutePath = UCCyclone.expectFile(file, CORE_IMPL_DEX_FILE_USING_SO_SUFFIX).getAbsolutePath();
                try {
                    str3 = UCCyclone.expectFile(file, SDK_SHELL_DEX_FILE_USING_SO_SUFFIX).getAbsolutePath();
                } catch (Throwable unused) {
                    str3 = null;
                }
                try {
                    str4 = UCCyclone.expectFile(file, BROWSER_IF_DEX_FILE_USING_SO_SUFFIX).getAbsolutePath();
                } catch (Throwable unused2) {
                    str4 = null;
                }
                com.uc.webview.export.internal.utility.UCMPackageInfo uCMPackageInfo = new com.uc.webview.export.internal.utility.UCMPackageInfo(context, SocialConstants.PARAM_SPECIFIED, file.getAbsolutePath(), file.getAbsolutePath(), file.getAbsolutePath(), true, str3, str4, absolutePath, str2, true);
                return uCMPackageInfo;
            } catch (UCKnownException e) {
                UCKnownException uCKnownException = e;
                File[] listFiles = file.listFiles();
                if (listFiles == null) {
                    break;
                }
                for (String str6 : ARCHS) {
                    int length = listFiles.length;
                    int i = 0;
                    while (i < length) {
                        File file2 = listFiles[i];
                        if (!str6.equals(file2.getName()) || !file2.isDirectory()) {
                            i++;
                        } else {
                            str5 = file2.getAbsolutePath();
                        }
                    }
                }
                break loop0;
                throw uCKnownException;
            }
        }
        throw uCKnownException;
    }

    private static UCMPackageInfo a(Context context, String str, String str2, String str3, String str4, String str5) throws UCSetupException {
        String str6;
        String str7;
        String str8;
        String str9;
        boolean a2 = j.a(str2);
        boolean a3 = j.a(str3);
        boolean a4 = j.a(str4);
        if (!a2 || ((Boolean) invoke(10011, new Object[0])).booleanValue()) {
            if (!a2) {
                File file = (File) invoke(expectDirFile1S, str2);
                String absolutePath = UCCyclone.expectFile(file, "core.jar").getAbsolutePath();
                try {
                    str9 = UCCyclone.expectFile(file, SDK_SHELL_DEX_FILE).getAbsolutePath();
                } catch (Throwable unused) {
                    str9 = null;
                }
                try {
                    str7 = UCCyclone.expectFile(file, "browser_if.jar").getAbsolutePath();
                    str6 = absolutePath;
                    str8 = str9;
                } catch (Throwable unused2) {
                    str6 = absolutePath;
                    str8 = str9;
                    str7 = null;
                }
            } else {
                str8 = null;
                str7 = null;
                str6 = null;
            }
            com.uc.webview.export.internal.utility.UCMPackageInfo uCMPackageInfo = new com.uc.webview.export.internal.utility.UCMPackageInfo(context, str == null ? SocialConstants.PARAM_SPECIFIED : str, str3, str4, str2, true, str8, str7, str6, str5, false);
            return uCMPackageInfo;
        } else if (a3 && a4) {
            return null;
        } else {
            throw new UCSetupException(3002, (String) "No ucm dex file specified.");
        }
    }

    private static List<UCMPackageInfo> a(Context context, File file, List<UCMPackageInfo> list) throws UCSetupException {
        File file2;
        File file3 = file;
        List<UCMPackageInfo> arrayList = list != null ? list : new ArrayList<>();
        if (file.exists() && file.isDirectory()) {
            File file4 = new File(file3, SDK_SHELL_DEX_FILE);
            File file5 = new File(file3, "browser_if.jar");
            File file6 = new File(file3, "core.jar");
            File file7 = new File(file3, "lib");
            boolean z = file6.exists() && (!SDKFactory.n || file4.exists()) && ((!SDKFactory.n || file5.exists()) && file7.isDirectory());
            if (z) {
                String[] strArr = ARCHS;
                int length = strArr.length;
                int i = 0;
                while (true) {
                    if (i >= length) {
                        file2 = file7;
                        break;
                    }
                    file2 = new File(file7, strArr[i]);
                    if (file2.isDirectory()) {
                        break;
                    }
                    i++;
                }
                File file8 = new File(file3, "assets");
                if (z) {
                    com.uc.webview.export.internal.utility.UCMPackageInfo uCMPackageInfo = new com.uc.webview.export.internal.utility.UCMPackageInfo(context, SocialConstants.PARAM_SPECIFIED, file2.getAbsolutePath(), file8.getAbsolutePath(), file.getAbsolutePath(), true, file4.getAbsolutePath(), file5.getAbsolutePath(), file6.getAbsolutePath(), null, false);
                    arrayList.add(uCMPackageInfo);
                }
            }
            File[] listFiles = file.listFiles();
            if (listFiles != null) {
                for (File file9 : listFiles) {
                    if (file9.isDirectory()) {
                        a(context, file9, arrayList);
                    } else {
                        Context context2 = context;
                    }
                }
            }
        }
        return arrayList;
    }

    public static boolean checkNeedDecompress(Context context, String str, String str2) {
        if (!new File(str2).exists()) {
            return false;
        }
        for (UCMPackageInfo uCMPackageInfo : a(context, new File(str), (List<UCMPackageInfo>) new ArrayList<UCMPackageInfo>())) {
            if (!bv.a(context, uCMPackageInfo.dataDir, str2)) {
                return false;
            }
        }
        return true;
    }

    private static void a(long j) {
        WaStat.statAKV(new Pair("sc_lsuk", new cg(j)));
    }

    public static List<UCMPackageInfo> listFromSharedApps(Context context, ConcurrentHashMap<String, Object> concurrentHashMap) {
        Throwable th;
        String str;
        String str2;
        String str3;
        Throwable th2;
        String str4;
        int i;
        String str5;
        String[] strArr;
        long j;
        String param = UCCore.getParam(CDParamKeys.CD_KEY_SHARE_CORE_CLIENT_SPECIAL_HOST_PKG_NAME_LIST);
        String param2 = UCCore.getParam(CDParamKeys.CD_KEY_SHARE_CORE_CLIENT_PATH_LIST);
        long j2 = j.a(param) ? 16385 : 1;
        if (j.a(param2)) {
            j2 |= 32768;
        }
        if (!j.a(param)) {
            param2 = param;
        }
        ArrayList arrayList = new ArrayList();
        try {
            if (j.a(param2)) {
                throw new UCSetupException(8001, (String) "Please set sc_pkgl or sc_plist parameter!");
            }
            String[] split = param2.split(CDParamKeys.CD_VALUE_STRING_SPLITER);
            int length = split.length;
            long j3 = j2;
            int i2 = 0;
            while (i2 < length) {
                try {
                    String str6 = split[i2];
                    if (!j.a(str6)) {
                        if (j.a(param) || !str6.equals(context.getPackageName())) {
                            if (!j.a(param)) {
                                StringBuilder sb = new StringBuilder("/data/data/");
                                sb.append(str6);
                                sb.append("/app_ucmsdk");
                                str4 = sb.toString();
                            } else {
                                str4 = str6;
                            }
                            List<a> a2 = a.a(new File(str4, "config.json"));
                            if (a2.size() == 0) {
                                j3 |= 8;
                            } else {
                                File file = new File("/data/data/");
                                for (a next : a2) {
                                    if (!j.a(next.c)) {
                                        File file2 = new File(next.c);
                                        File file3 = new File(next.d);
                                        File file4 = new File(next.e);
                                        String str7 = next.a;
                                        String str8 = next.b;
                                        strArr = split;
                                        str5 = str6;
                                        boolean booleanValue = ((Boolean) invoke(compareVersion, str7, Version.SUPPORT_U4_MIN, "最小u4内核版本不通过")).booleanValue();
                                        i = length;
                                        boolean booleanValue2 = ((Boolean) invoke(compareVersion, Version.NAME, str8, "最小SDK版本不通过")).booleanValue();
                                        if (!booleanValue || !booleanValue2) {
                                            StringBuilder sb2 = new StringBuilder("版本校验不通过>>config ucmVersion:");
                                            sb2.append(str7);
                                            sb2.append(",SUPPORT_U4_MIN=");
                                            sb2.append(Version.SUPPORT_U4_MIN);
                                            Log.d("UCMPackageInfo", sb2.toString());
                                            StringBuilder sb3 = new StringBuilder("版本校验不通过>>config ucmSuportSDKMin:");
                                            sb3.append(str8);
                                            sb3.append(",NAME=");
                                            sb3.append(Version.NAME);
                                            Log.d("UCMPackageInfo", sb3.toString());
                                        } else {
                                            Log.d("UCMPackageInfo", "版本校验通过!!!");
                                            if (!file2.exists() || !file3.exists() || !file4.exists()) {
                                                StringBuilder sb4 = new StringBuilder();
                                                sb4.append(file2.getAbsolutePath());
                                                sb4.append(" or ");
                                                sb4.append(file3.getAbsolutePath());
                                                sb4.append(" or ");
                                                sb4.append(file4.getAbsolutePath());
                                                sb4.append(" not exist");
                                                Log.i("UCMPackageInfo", sb4.toString());
                                                j = !file2.exists() ? 4608 | j3 : j3;
                                                if (!file3.exists()) {
                                                    j |= 5120;
                                                }
                                                j3 = !file4.exists() ? 6144 | j : j;
                                            } else {
                                                j = 16 | j3;
                                                try {
                                                    if (!ay.a(file2, file)) {
                                                        StringBuilder sb5 = new StringBuilder("checkDexFilePermissions ");
                                                        sb5.append(file2.getAbsolutePath());
                                                        sb5.append(" failure!");
                                                        Log.i("UCMPackageInfo", sb5.toString());
                                                        j3 = 32 | j;
                                                    } else if (!ay.b(file3, file)) {
                                                        StringBuilder sb6 = new StringBuilder("checkSoFilePermissions ");
                                                        sb6.append(file3.getAbsolutePath());
                                                        sb6.append(" failure!");
                                                        Log.i("UCMPackageInfo", sb6.toString());
                                                        j3 = 64 | j;
                                                    } else if (!ay.c(file4, file)) {
                                                        StringBuilder sb7 = new StringBuilder("checkFilePermissions ");
                                                        sb7.append(file4.getAbsolutePath());
                                                        sb7.append(" failure!");
                                                        Log.i("UCMPackageInfo", sb7.toString());
                                                        j3 = 128 | j;
                                                    } else {
                                                        arrayList.add(a(context, !j.a(param) ? str5 : null, file2.getAbsolutePath(), file3.getAbsolutePath(), file4.getAbsolutePath(), null));
                                                        j3 = 256 | j;
                                                    }
                                                } catch (Exception e) {
                                                    th2 = e;
                                                    j2 = j;
                                                    try {
                                                        Log.e("UCMPackageInfo", "getAppUCMPackageInfo", th2);
                                                        a(j2);
                                                        str3 = "UCMPackageInfo";
                                                        str2 = "listFromSharedApps.run listShareStat: ";
                                                        str = String.valueOf(j2);
                                                        Log.d(str3, str2.concat(str));
                                                        return arrayList;
                                                    } catch (Throwable th3) {
                                                        th = th3;
                                                        j3 = j2;
                                                        a(j3);
                                                        Log.d("UCMPackageInfo", "listFromSharedApps.run listShareStat: ".concat(String.valueOf(j3)));
                                                        throw th;
                                                    }
                                                } catch (Throwable th4) {
                                                    th = th4;
                                                    j3 = j;
                                                    a(j3);
                                                    Log.d("UCMPackageInfo", "listFromSharedApps.run listShareStat: ".concat(String.valueOf(j3)));
                                                    throw th;
                                                }
                                            }
                                        }
                                    } else {
                                        strArr = split;
                                        str5 = str6;
                                        i = length;
                                    }
                                    split = strArr;
                                    str6 = str5;
                                    length = i;
                                }
                            }
                        } else {
                            j3 |= 8192;
                        }
                    }
                    i2++;
                    split = split;
                    length = length;
                } catch (Exception e2) {
                    th2 = e2;
                    j2 = j3;
                } catch (Throwable th5) {
                    th = th5;
                    a(j3);
                    Log.d("UCMPackageInfo", "listFromSharedApps.run listShareStat: ".concat(String.valueOf(j3)));
                    throw th;
                }
            }
            a(j3);
            str3 = "UCMPackageInfo";
            str2 = "listFromSharedApps.run listShareStat: ";
            str = String.valueOf(j3);
            Log.d(str3, str2.concat(str));
            return arrayList;
        } catch (Exception e3) {
            th2 = e3;
            Log.e("UCMPackageInfo", "getAppUCMPackageInfo", th2);
            a(j2);
            str3 = "UCMPackageInfo";
            str2 = "listFromSharedApps.run listShareStat: ";
            str = String.valueOf(j2);
            Log.d(str3, str2.concat(str));
            return arrayList;
        }
    }
}
