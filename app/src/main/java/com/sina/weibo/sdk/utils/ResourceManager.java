package com.sina.weibo.sdk.utils;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.util.DisplayMetrics;
import android.util.StateSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

public class ResourceManager {
    private static final String DRAWABLE = "drawable";
    private static final String DRAWABLE_HDPI = "drawable-hdpi";
    private static final String DRAWABLE_LDPI = "drawable-ldpi";
    private static final String DRAWABLE_MDPI = "drawable-mdpi";
    private static final String DRAWABLE_XHDPI = "drawable-xhdpi";
    private static final String DRAWABLE_XXHDPI = "drawable-xxhdpi";
    private static final String[] PRE_INSTALL_DRAWBLE_PATHS = {DRAWABLE_XXHDPI, DRAWABLE_XHDPI, DRAWABLE_HDPI, DRAWABLE_MDPI, DRAWABLE_LDPI, "drawable"};
    private static final String TAG = "com.sina.weibo.sdk.utils.ResourceManager";

    public static String getString(Context context, String str, String str2, String str3) {
        Locale language = getLanguage();
        if (Locale.SIMPLIFIED_CHINESE.equals(language) || ("zh".equals(language.getLanguage()) && language.getCountry().contains("CN"))) {
            return str2;
        }
        return (Locale.TRADITIONAL_CHINESE.equals(language) || ("zh".equals(language.getLanguage()) && language.getCountry().contains("TW"))) ? str3 : str;
    }

    public static Drawable getDrawable(Context context, String str) {
        return getDrawableFromAssert(context, getAppropriatePathOfDrawable(context, str), false);
    }

    public static Drawable getNinePatchDrawable(Context context, String str) {
        return getDrawableFromAssert(context, getAppropriatePathOfDrawable(context, str), true);
    }

    public static Locale getLanguage() {
        Locale locale = Locale.getDefault();
        return (Locale.SIMPLIFIED_CHINESE.equals(locale) || Locale.TRADITIONAL_CHINESE.equals(locale) || (locale.getLanguage().equals("zh") && (locale.getCountry().contains("CN") || locale.getCountry().contains("TW")))) ? locale : Locale.ENGLISH;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0065, code lost:
        if (java.lang.Math.abs(r4 - r2) <= java.lang.Math.abs(r4 - r5)) goto L_0x0072;
     */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x007d  */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x0085  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.lang.String getAppropriatePathOfDrawable(android.content.Context r8, java.lang.String r9) {
        /*
            boolean r0 = android.text.TextUtils.isEmpty(r9)
            r1 = 0
            if (r0 == 0) goto L_0x000f
            java.lang.String r8 = TAG
            java.lang.String r9 = "id is NOT correct!"
            com.sina.weibo.sdk.utils.LogUtil.e(r8, r9)
            return r1
        L_0x000f:
            java.lang.String r0 = getCurrentDpiFolder(r8)
            java.lang.String r2 = TAG
            java.lang.String r3 = "find Appropriate path..."
            com.sina.weibo.sdk.utils.LogUtil.d(r2, r3)
            r2 = 0
            r3 = -1
            r4 = -1
            r5 = -1
        L_0x001e:
            java.lang.String[] r6 = PRE_INSTALL_DRAWBLE_PATHS
            int r6 = r6.length
            if (r2 >= r6) goto L_0x0055
            java.lang.String[] r6 = PRE_INSTALL_DRAWBLE_PATHS
            r6 = r6[r2]
            boolean r6 = r6.equals(r0)
            if (r6 == 0) goto L_0x002e
            r4 = r2
        L_0x002e:
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            java.lang.String[] r7 = PRE_INSTALL_DRAWBLE_PATHS
            r7 = r7[r2]
            r6.append(r7)
            java.lang.String r7 = "/"
            r6.append(r7)
            r6.append(r9)
            java.lang.String r6 = r6.toString()
            boolean r7 = isFileExisted(r8, r6)
            if (r7 == 0) goto L_0x0052
            if (r4 != r2) goto L_0x004f
            return r6
        L_0x004f:
            if (r4 >= 0) goto L_0x0056
            r5 = r2
        L_0x0052:
            int r2 = r2 + 1
            goto L_0x001e
        L_0x0055:
            r2 = -1
        L_0x0056:
            if (r5 <= 0) goto L_0x0068
            if (r2 <= 0) goto L_0x0068
            int r8 = r4 - r2
            int r8 = java.lang.Math.abs(r8)
            int r4 = r4 - r5
            int r0 = java.lang.Math.abs(r4)
            if (r8 > r0) goto L_0x006c
            goto L_0x0072
        L_0x0068:
            if (r5 <= 0) goto L_0x006e
            if (r2 >= 0) goto L_0x006e
        L_0x006c:
            r3 = r5
            goto L_0x007b
        L_0x006e:
            if (r5 >= 0) goto L_0x0074
            if (r2 <= 0) goto L_0x0074
        L_0x0072:
            r3 = r2
            goto L_0x007b
        L_0x0074:
            java.lang.String r8 = TAG
            java.lang.String r0 = "Not find the appropriate path for drawable"
            com.sina.weibo.sdk.utils.LogUtil.e(r8, r0)
        L_0x007b:
            if (r3 >= 0) goto L_0x0085
            java.lang.String r8 = TAG
            java.lang.String r9 = "Not find the appropriate path for drawable"
            com.sina.weibo.sdk.utils.LogUtil.e(r8, r9)
            return r1
        L_0x0085:
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            java.lang.String[] r0 = PRE_INSTALL_DRAWBLE_PATHS
            r0 = r0[r3]
            r8.append(r0)
            java.lang.String r0 = "/"
            r8.append(r0)
            r8.append(r9)
            java.lang.String r8 = r8.toString()
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.sina.weibo.sdk.utils.ResourceManager.getAppropriatePathOfDrawable(android.content.Context, java.lang.String):java.lang.String");
    }

    /* JADX WARNING: type inference failed for: r1v0 */
    /* JADX WARNING: type inference failed for: r1v1, types: [android.graphics.drawable.Drawable] */
    /* JADX WARNING: type inference failed for: r1v2 */
    /* JADX WARNING: type inference failed for: r1v3 */
    /* JADX WARNING: type inference failed for: r10v2, types: [android.graphics.drawable.BitmapDrawable] */
    /* JADX WARNING: type inference failed for: r1v4 */
    /* JADX WARNING: type inference failed for: r2v0, types: [android.graphics.drawable.NinePatchDrawable] */
    /* JADX WARNING: type inference failed for: r1v5 */
    /* JADX WARNING: type inference failed for: r1v6 */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r1v2
      assigns: []
      uses: []
      mth insns count: 44
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
    	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:311)
    	at jadx.api.JavaClass.decompile(JavaClass.java:62)
    	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:217)
     */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x0064 A[SYNTHETIC, Splitter:B:23:0x0064] */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x006b A[SYNTHETIC, Splitter:B:28:0x006b] */
    /* JADX WARNING: Unknown variable types count: 3 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static android.graphics.drawable.Drawable getDrawableFromAssert(android.content.Context r8, java.lang.String r9, boolean r10) {
        /*
            android.content.res.AssetManager r0 = r8.getAssets()
            r1 = 0
            java.io.InputStream r9 = r0.open(r9)     // Catch:{ IOException -> 0x005d, all -> 0x005a }
            if (r9 == 0) goto L_0x004f
            android.graphics.Bitmap r4 = android.graphics.BitmapFactory.decodeStream(r9)     // Catch:{ IOException -> 0x004d }
            android.content.res.Resources r0 = r8.getResources()     // Catch:{ IOException -> 0x004d }
            android.util.DisplayMetrics r0 = r0.getDisplayMetrics()     // Catch:{ IOException -> 0x004d }
            if (r10 == 0) goto L_0x003d
            android.content.res.Resources r10 = r8.getResources()     // Catch:{ IOException -> 0x004d }
            android.content.res.Configuration r10 = r10.getConfiguration()     // Catch:{ IOException -> 0x004d }
            android.content.res.Resources r3 = new android.content.res.Resources     // Catch:{ IOException -> 0x004d }
            android.content.res.AssetManager r8 = r8.getAssets()     // Catch:{ IOException -> 0x004d }
            r3.<init>(r8, r0, r10)     // Catch:{ IOException -> 0x004d }
            android.graphics.drawable.NinePatchDrawable r8 = new android.graphics.drawable.NinePatchDrawable     // Catch:{ IOException -> 0x004d }
            byte[] r5 = r4.getNinePatchChunk()     // Catch:{ IOException -> 0x004d }
            android.graphics.Rect r6 = new android.graphics.Rect     // Catch:{ IOException -> 0x004d }
            r10 = 0
            r6.<init>(r10, r10, r10, r10)     // Catch:{ IOException -> 0x004d }
            r7 = 0
            r2 = r8
            r2.<init>(r3, r4, r5, r6, r7)     // Catch:{ IOException -> 0x004d }
            r1 = r8
            goto L_0x004f
        L_0x003d:
            int r10 = r0.densityDpi     // Catch:{ IOException -> 0x004d }
            r4.setDensity(r10)     // Catch:{ IOException -> 0x004d }
            android.graphics.drawable.BitmapDrawable r10 = new android.graphics.drawable.BitmapDrawable     // Catch:{ IOException -> 0x004d }
            android.content.res.Resources r8 = r8.getResources()     // Catch:{ IOException -> 0x004d }
            r10.<init>(r8, r4)     // Catch:{ IOException -> 0x004d }
            r1 = r10
            goto L_0x004f
        L_0x004d:
            r8 = move-exception
            goto L_0x005f
        L_0x004f:
            if (r9 == 0) goto L_0x0067
            r9.close()     // Catch:{ IOException -> 0x0055 }
            goto L_0x0067
        L_0x0055:
            r8 = move-exception
            r8.printStackTrace()
            goto L_0x0067
        L_0x005a:
            r8 = move-exception
            r9 = r1
            goto L_0x0069
        L_0x005d:
            r8 = move-exception
            r9 = r1
        L_0x005f:
            r8.printStackTrace()     // Catch:{ all -> 0x0068 }
            if (r9 == 0) goto L_0x0067
            r9.close()     // Catch:{ IOException -> 0x0055 }
        L_0x0067:
            return r1
        L_0x0068:
            r8 = move-exception
        L_0x0069:
            if (r9 == 0) goto L_0x0073
            r9.close()     // Catch:{ IOException -> 0x006f }
            goto L_0x0073
        L_0x006f:
            r9 = move-exception
            r9.printStackTrace()
        L_0x0073:
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.sina.weibo.sdk.utils.ResourceManager.getDrawableFromAssert(android.content.Context, java.lang.String, boolean):android.graphics.drawable.Drawable");
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(6:19|18|21|22|(0)|28) */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x003c, code lost:
        r5 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:?, code lost:
        r1.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x005c, code lost:
        r4 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x005d, code lost:
        r4.printStackTrace();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:?, code lost:
        r1.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x0067, code lost:
        r4 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x0068, code lost:
        r4.printStackTrace();
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:21:0x003e */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x0058 A[SYNTHETIC, Splitter:B:24:0x0058] */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x0063 A[SYNTHETIC, Splitter:B:30:0x0063] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static boolean isFileExisted(android.content.Context r4, java.lang.String r5) {
        /*
            r0 = 0
            if (r4 == 0) goto L_0x006c
            boolean r1 = android.text.TextUtils.isEmpty(r5)
            if (r1 == 0) goto L_0x000a
            goto L_0x006c
        L_0x000a:
            android.content.res.AssetManager r4 = r4.getAssets()
            r1 = 0
            java.io.InputStream r4 = r4.open(r5)     // Catch:{ IOException -> 0x003e }
            java.lang.String r1 = TAG     // Catch:{ IOException -> 0x003a, all -> 0x0037 }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x003a, all -> 0x0037 }
            java.lang.String r3 = "file ["
            r2.<init>(r3)     // Catch:{ IOException -> 0x003a, all -> 0x0037 }
            r2.append(r5)     // Catch:{ IOException -> 0x003a, all -> 0x0037 }
            java.lang.String r3 = "] existed"
            r2.append(r3)     // Catch:{ IOException -> 0x003a, all -> 0x0037 }
            java.lang.String r2 = r2.toString()     // Catch:{ IOException -> 0x003a, all -> 0x0037 }
            com.sina.weibo.sdk.utils.LogUtil.d(r1, r2)     // Catch:{ IOException -> 0x003a, all -> 0x0037 }
            if (r4 == 0) goto L_0x0035
            r4.close()     // Catch:{ IOException -> 0x0031 }
            goto L_0x0035
        L_0x0031:
            r4 = move-exception
            r4.printStackTrace()
        L_0x0035:
            r4 = 1
            return r4
        L_0x0037:
            r5 = move-exception
            r1 = r4
            goto L_0x0061
        L_0x003a:
            r1 = r4
            goto L_0x003e
        L_0x003c:
            r5 = move-exception
            goto L_0x0061
        L_0x003e:
            java.lang.String r4 = TAG     // Catch:{ all -> 0x003c }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ all -> 0x003c }
            java.lang.String r3 = "file ["
            r2.<init>(r3)     // Catch:{ all -> 0x003c }
            r2.append(r5)     // Catch:{ all -> 0x003c }
            java.lang.String r5 = "] NOT existed"
            r2.append(r5)     // Catch:{ all -> 0x003c }
            java.lang.String r5 = r2.toString()     // Catch:{ all -> 0x003c }
            com.sina.weibo.sdk.utils.LogUtil.d(r4, r5)     // Catch:{ all -> 0x003c }
            if (r1 == 0) goto L_0x0060
            r1.close()     // Catch:{ IOException -> 0x005c }
            goto L_0x0060
        L_0x005c:
            r4 = move-exception
            r4.printStackTrace()
        L_0x0060:
            return r0
        L_0x0061:
            if (r1 == 0) goto L_0x006b
            r1.close()     // Catch:{ IOException -> 0x0067 }
            goto L_0x006b
        L_0x0067:
            r4 = move-exception
            r4.printStackTrace()
        L_0x006b:
            throw r5
        L_0x006c:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.sina.weibo.sdk.utils.ResourceManager.isFileExisted(android.content.Context, java.lang.String):boolean");
    }

    private static String getCurrentDpiFolder(Context context) {
        int i = context.getResources().getDisplayMetrics().densityDpi;
        if (i <= 120) {
            return DRAWABLE_LDPI;
        }
        if (i > 120 && i <= 160) {
            return DRAWABLE_MDPI;
        }
        if (i <= 160 || i > 240) {
            return (i <= 240 || i > 320) ? DRAWABLE_XXHDPI : DRAWABLE_XHDPI;
        }
        return DRAWABLE_HDPI;
    }

    private static View extractView(Context context, String str, ViewGroup viewGroup) throws Exception {
        return ((LayoutInflater) context.getSystemService("layout_inflater")).inflate(context.getAssets().openXmlResourceParser(str), viewGroup);
    }

    private static Drawable extractDrawable(Context context, String str) throws Exception {
        InputStream open = context.getAssets().open(str);
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        TypedValue typedValue = new TypedValue();
        typedValue.density = displayMetrics.densityDpi;
        Drawable createFromResourceStream = Drawable.createFromResourceStream(context.getResources(), typedValue, open, str);
        open.close();
        return createFromResourceStream;
    }

    public static int dp2px(Context context, int i) {
        return (int) (((double) (((float) i) * context.getResources().getDisplayMetrics().density)) + 0.5d);
    }

    public static ColorStateList createColorStateList(int i, int i2) {
        int[] iArr = {i2, i2, i2, i};
        return new ColorStateList(new int[][]{new int[]{16842919}, new int[]{16842913}, new int[]{16842908}, StateSet.WILD_CARD}, iArr);
    }

    public static StateListDrawable createStateListDrawable(Context context, String str, String str2) {
        Drawable drawable;
        Drawable drawable2;
        if (str.indexOf(".9") >= 0) {
            drawable = getNinePatchDrawable(context, str);
        } else {
            drawable = getDrawable(context, str);
        }
        if (str2.indexOf(".9") >= 0) {
            drawable2 = getNinePatchDrawable(context, str2);
        } else {
            drawable2 = getDrawable(context, str2);
        }
        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(new int[]{16842919}, drawable2);
        stateListDrawable.addState(new int[]{16842913}, drawable2);
        stateListDrawable.addState(new int[]{16842908}, drawable2);
        stateListDrawable.addState(StateSet.WILD_CARD, drawable);
        return stateListDrawable;
    }

    public static StateListDrawable createStateListDrawable(Context context, String str, String str2, String str3) {
        Drawable drawable;
        Drawable drawable2;
        Drawable drawable3;
        if (str.indexOf(".9") >= 0) {
            drawable = getNinePatchDrawable(context, str);
        } else {
            drawable = getDrawable(context, str);
        }
        if (str3.indexOf(".9") >= 0) {
            drawable2 = getNinePatchDrawable(context, str3);
        } else {
            drawable2 = getDrawable(context, str3);
        }
        if (str2.indexOf(".9") >= 0) {
            drawable3 = getNinePatchDrawable(context, str2);
        } else {
            drawable3 = getDrawable(context, str2);
        }
        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(new int[]{16842919}, drawable3);
        stateListDrawable.addState(new int[]{16842913}, drawable3);
        stateListDrawable.addState(new int[]{16842908}, drawable3);
        stateListDrawable.addState(new int[]{16842766}, drawable2);
        stateListDrawable.addState(StateSet.WILD_CARD, drawable);
        return stateListDrawable;
    }

    public static String readCountryFromAsset(Context context, String str) {
        String str2;
        try {
            InputStream open = context.getAssets().open(str);
            if (open != null) {
                DataInputStream dataInputStream = new DataInputStream(open);
                byte[] bArr = new byte[dataInputStream.available()];
                dataInputStream.read(bArr);
                str2 = new String(bArr, "UTF-8");
                try {
                    open.close();
                    return str2;
                } catch (IOException e) {
                    e = e;
                }
            } else {
                r4 = "";
                return "";
            }
        } catch (IOException e2) {
            e = e2;
            str2 = "";
            e.printStackTrace();
            return str2;
        }
    }
}
