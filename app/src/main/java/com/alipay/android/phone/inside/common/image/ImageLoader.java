package com.alipay.android.phone.inside.common.image;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import com.alipay.android.phone.inside.framework.LauncherApplication;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.autonavi.common.SuperId;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ImageLoader {
    /* access modifiers changed from: private */
    public static final Pattern a = Pattern.compile("(\\?|&)biztype=([^&]*)(&|$)");
    private static ImageLoader b;

    public static class ClipsInfo {
        private ClipsType a;
        private int b;
        /* access modifiers changed from: private */
        public int[] c;
        private boolean d = false;

        public final int[] a() {
            return this.c;
        }

        public final ClipsType b() {
            return this.a;
        }

        public final int c() {
            return this.b;
        }

        public static Bitmap a(Bitmap bitmap, int i, int i2) {
            int width = bitmap.getWidth() == 0 ? 1 : bitmap.getWidth();
            int height = bitmap.getHeight() == 0 ? 1 : bitmap.getHeight();
            Matrix matrix = new Matrix();
            matrix.postScale(((float) i) / (((float) width) * 1.0f), ((float) i2) / (((float) height) * 1.0f));
            Bitmap createBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            Bitmap createBitmap2 = Bitmap.createBitmap(i, i2, Config.ARGB_8888);
            Canvas canvas = new Canvas(createBitmap2);
            Paint paint = new Paint();
            Rect rect = new Rect((createBitmap.getWidth() - i) / 2, (createBitmap.getHeight() - i2) / 2, (createBitmap.getWidth() + i) / 2, (createBitmap.getHeight() + i2) / 2);
            Rect rect2 = new Rect(0, 0, i, i2);
            paint.setAntiAlias(true);
            canvas.drawARGB(0, 0, 0, 0);
            paint.setColor(-12434878);
            int i3 = i / 2;
            int i4 = i2 / 2;
            int i5 = i3 > i4 ? i4 : i3;
            canvas.drawColor(0, Mode.SRC_IN);
            canvas.drawCircle((float) i3, (float) i4, (float) i5, paint);
            paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
            canvas.drawBitmap(createBitmap, rect, rect2, paint);
            return createBitmap2;
        }

        public static Bitmap a(Bitmap bitmap, int i, int i2, int i3) {
            int width = bitmap.getWidth() == 0 ? 1 : bitmap.getWidth();
            int height = bitmap.getHeight() == 0 ? 1 : bitmap.getHeight();
            Matrix matrix = new Matrix();
            matrix.postScale(((float) i2) / (((float) width) * 1.0f), ((float) i3) / (((float) height) * 1.0f));
            Bitmap createBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            Bitmap createBitmap2 = Bitmap.createBitmap(i2, i3, Config.ARGB_8888);
            Canvas canvas = new Canvas(createBitmap2);
            Paint paint = new Paint();
            paint.setAntiAlias(true);
            Rect rect = new Rect(0, 0, i2, i3);
            float f = (float) i;
            canvas.drawRoundRect(new RectF(rect), f, f, paint);
            paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
            canvas.drawBitmap(createBitmap, rect, rect, paint);
            return createBitmap2;
        }
    }

    public enum ClipsType {
        None,
        Round,
        Corner
    }

    interface ILoadImageCallback<T> {
        void a(T t);
    }

    public enum LoadAction {
        Image,
        Background
    }

    public class NetImageAsyncTask extends AsyncTask<Object, Void, Bitmap> {
        /* access modifiers changed from: private */
        public ClipsInfo b = null;
        private ILoadImageCallback<Bitmap> c;

        public NetImageAsyncTask() {
        }

        /* access modifiers changed from: protected */
        public /* synthetic */ void onPostExecute(Object obj) {
            Bitmap bitmap = (Bitmap) obj;
            super.onPostExecute(bitmap);
            if (this.c != null) {
                this.c.a(bitmap);
            }
        }

        /* JADX WARNING: Removed duplicated region for block: B:14:0x002e A[SYNTHETIC, Splitter:B:14:0x002e] */
        /* JADX WARNING: Removed duplicated region for block: B:23:0x0040 A[SYNTHETIC, Splitter:B:23:0x0040] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private static boolean a(android.graphics.Bitmap r2, java.lang.String r3) {
            /*
                r0 = 0
                java.io.File r1 = new java.io.File     // Catch:{ Exception -> 0x003d, all -> 0x002b }
                r1.<init>(r3)     // Catch:{ Exception -> 0x003d, all -> 0x002b }
                r1.createNewFile()     // Catch:{ Exception -> 0x003d, all -> 0x002b }
                java.io.FileOutputStream r3 = new java.io.FileOutputStream     // Catch:{ Exception -> 0x003d, all -> 0x002b }
                r3.<init>(r1)     // Catch:{ Exception -> 0x003d, all -> 0x002b }
                android.graphics.Bitmap$CompressFormat r0 = android.graphics.Bitmap.CompressFormat.PNG     // Catch:{ Exception -> 0x003e, all -> 0x0028 }
                r1 = 100
                r2.compress(r0, r1, r3)     // Catch:{ Exception -> 0x003e, all -> 0x0028 }
                r3.flush()     // Catch:{ Exception -> 0x003e, all -> 0x0028 }
                r2 = 1
                r3.close()     // Catch:{ IOException -> 0x001d }
                goto L_0x004f
            L_0x001d:
                r3 = move-exception
                com.alipay.android.phone.inside.log.api.trace.TraceLogger r0 = com.alipay.android.phone.inside.log.api.LoggerFactory.f()
                java.lang.String r1 = "inside"
                r0.c(r1, r3)
                goto L_0x004f
            L_0x0028:
                r2 = move-exception
                r0 = r3
                goto L_0x002c
            L_0x002b:
                r2 = move-exception
            L_0x002c:
                if (r0 == 0) goto L_0x003c
                r0.close()     // Catch:{ IOException -> 0x0032 }
                goto L_0x003c
            L_0x0032:
                r3 = move-exception
                com.alipay.android.phone.inside.log.api.trace.TraceLogger r0 = com.alipay.android.phone.inside.log.api.LoggerFactory.f()
                java.lang.String r1 = "inside"
                r0.c(r1, r3)
            L_0x003c:
                throw r2
            L_0x003d:
                r3 = r0
            L_0x003e:
                if (r3 == 0) goto L_0x004e
                r3.close()     // Catch:{ IOException -> 0x0044 }
                goto L_0x004e
            L_0x0044:
                r2 = move-exception
                com.alipay.android.phone.inside.log.api.trace.TraceLogger r3 = com.alipay.android.phone.inside.log.api.LoggerFactory.f()
                java.lang.String r0 = "inside"
                r3.c(r0, r2)
            L_0x004e:
                r2 = 0
            L_0x004f:
                return r2
            */
            throw new UnsupportedOperationException("Method not decompiled: com.alipay.android.phone.inside.common.image.ImageLoader.NetImageAsyncTask.a(android.graphics.Bitmap, java.lang.String):boolean");
        }

        /* JADX WARNING: Removed duplicated region for block: B:33:0x0072 A[SYNTHETIC, Splitter:B:33:0x0072] */
        /* JADX WARNING: Removed duplicated region for block: B:36:0x0077 A[Catch:{ IOException -> 0x003a }] */
        /* JADX WARNING: Removed duplicated region for block: B:40:0x007e A[SYNTHETIC, Splitter:B:40:0x007e] */
        /* JADX WARNING: Removed duplicated region for block: B:45:0x0086 A[Catch:{ IOException -> 0x0082 }] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private static android.graphics.Bitmap a(java.lang.String r7) {
            /*
                r0 = 0
                java.net.URL r1 = new java.net.URL     // Catch:{ Exception -> 0x0062, all -> 0x005d }
                r1.<init>(r7)     // Catch:{ Exception -> 0x0062, all -> 0x005d }
                java.net.URLConnection r7 = r1.openConnection()     // Catch:{ Exception -> 0x0062, all -> 0x005d }
                java.net.HttpURLConnection r7 = (java.net.HttpURLConnection) r7     // Catch:{ Exception -> 0x0062, all -> 0x005d }
                r1 = 1
                r7.setDoInput(r1)     // Catch:{ Exception -> 0x0058, all -> 0x0053 }
                r7.connect()     // Catch:{ Exception -> 0x0058, all -> 0x0053 }
                java.io.InputStream r1 = r7.getInputStream()     // Catch:{ Exception -> 0x0058, all -> 0x0053 }
                android.graphics.Bitmap r2 = android.graphics.BitmapFactory.decodeStream(r1)     // Catch:{ Exception -> 0x004e }
                java.lang.String r0 = "Cache-Control"
                java.lang.String r0 = r7.getHeaderField(r0)     // Catch:{ Exception -> 0x004c }
                com.alipay.android.phone.inside.log.api.trace.TraceLogger r3 = com.alipay.android.phone.inside.log.api.LoggerFactory.f()     // Catch:{ Exception -> 0x004c }
                java.lang.String r4 = "inside"
                java.lang.String r5 = "ImageLoader Cache-Control "
                java.lang.String r0 = java.lang.String.valueOf(r0)     // Catch:{ Exception -> 0x004c }
                java.lang.String r0 = r5.concat(r0)     // Catch:{ Exception -> 0x004c }
                r3.e(r4, r0)     // Catch:{ Exception -> 0x004c }
                if (r1 == 0) goto L_0x003c
                r1.close()     // Catch:{ IOException -> 0x003a }
                goto L_0x003c
            L_0x003a:
                r7 = move-exception
                goto L_0x0042
            L_0x003c:
                if (r7 == 0) goto L_0x007a
                r7.disconnect()     // Catch:{ IOException -> 0x003a }
                goto L_0x007a
            L_0x0042:
                com.alipay.android.phone.inside.log.api.trace.TraceLogger r0 = com.alipay.android.phone.inside.log.api.LoggerFactory.f()
                java.lang.String r1 = "inside"
                r0.c(r1, r7)
                goto L_0x007a
            L_0x004c:
                r0 = move-exception
                goto L_0x0067
            L_0x004e:
                r2 = move-exception
                r6 = r2
                r2 = r0
                r0 = r6
                goto L_0x0067
            L_0x0053:
                r1 = move-exception
                r6 = r1
                r1 = r0
                r0 = r6
                goto L_0x007c
            L_0x0058:
                r1 = move-exception
                r2 = r0
                r0 = r1
                r1 = r2
                goto L_0x0067
            L_0x005d:
                r7 = move-exception
                r1 = r0
                r0 = r7
                r7 = r1
                goto L_0x007c
            L_0x0062:
                r7 = move-exception
                r1 = r0
                r2 = r1
                r0 = r7
                r7 = r2
            L_0x0067:
                com.alipay.android.phone.inside.log.api.trace.TraceLogger r3 = com.alipay.android.phone.inside.log.api.LoggerFactory.f()     // Catch:{ all -> 0x007b }
                java.lang.String r4 = "inside"
                r3.c(r4, r0)     // Catch:{ all -> 0x007b }
                if (r1 == 0) goto L_0x0075
                r1.close()     // Catch:{ IOException -> 0x003a }
            L_0x0075:
                if (r7 == 0) goto L_0x007a
                r7.disconnect()     // Catch:{ IOException -> 0x003a }
            L_0x007a:
                return r2
            L_0x007b:
                r0 = move-exception
            L_0x007c:
                if (r1 == 0) goto L_0x0084
                r1.close()     // Catch:{ IOException -> 0x0082 }
                goto L_0x0084
            L_0x0082:
                r7 = move-exception
                goto L_0x008a
            L_0x0084:
                if (r7 == 0) goto L_0x0093
                r7.disconnect()     // Catch:{ IOException -> 0x0082 }
                goto L_0x0093
            L_0x008a:
                com.alipay.android.phone.inside.log.api.trace.TraceLogger r1 = com.alipay.android.phone.inside.log.api.LoggerFactory.f()
                java.lang.String r2 = "inside"
                r1.c(r2, r7)
            L_0x0093:
                throw r0
            */
            throw new UnsupportedOperationException("Method not decompiled: com.alipay.android.phone.inside.common.image.ImageLoader.NetImageAsyncTask.a(java.lang.String):android.graphics.Bitmap");
        }

        /* access modifiers changed from: private */
        /* JADX WARNING: Removed duplicated region for block: B:17:0x005d A[SYNTHETIC, Splitter:B:17:0x005d] */
        /* JADX WARNING: Removed duplicated region for block: B:23:0x0077  */
        /* JADX WARNING: Removed duplicated region for block: B:27:0x0085  */
        /* JADX WARNING: Removed duplicated region for block: B:29:0x008c A[RETURN] */
        /* renamed from: a */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public android.graphics.Bitmap doInBackground(java.lang.Object... r5) {
            /*
                r4 = this;
                r0 = 1
                r1 = 0
                if (r5 == 0) goto L_0x008d
                int r2 = r5.length
                r3 = 3
                if (r2 >= r3) goto L_0x000a
                goto L_0x008d
            L_0x000a:
                r2 = 0
                r2 = r5[r2]
                java.lang.String r2 = (java.lang.String) r2
                r0 = r5[r0]
                android.content.Context r0 = (android.content.Context) r0
                r3 = 2
                r5 = r5[r3]
                com.alipay.android.phone.inside.common.image.ImageLoader$ILoadImageCallback r5 = (com.alipay.android.phone.inside.common.image.ImageLoader.ILoadImageCallback) r5
                r4.c = r5
                if (r0 != 0) goto L_0x001e
                r5 = r1
                goto L_0x0037
            L_0x001e:
                java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0079 }
                r5.<init>()     // Catch:{ Exception -> 0x0079 }
                java.io.File r0 = r0.getFilesDir()     // Catch:{ Exception -> 0x0079 }
                java.lang.String r0 = r0.getPath()     // Catch:{ Exception -> 0x0079 }
                r5.append(r0)     // Catch:{ Exception -> 0x0079 }
                java.lang.String r0 = "/img/"
                r5.append(r0)     // Catch:{ Exception -> 0x0079 }
                java.lang.String r5 = r5.toString()     // Catch:{ Exception -> 0x0079 }
            L_0x0037:
                if (r2 == 0) goto L_0x0056
                boolean r0 = android.text.TextUtils.isEmpty(r2)     // Catch:{ Exception -> 0x0079 }
                if (r0 == 0) goto L_0x0040
                goto L_0x0056
            L_0x0040:
                java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0079 }
                r0.<init>()     // Catch:{ Exception -> 0x0079 }
                java.lang.String r3 = com.alipay.android.phone.inside.common.util.MD5Util.a(r2)     // Catch:{ Exception -> 0x0079 }
                r0.append(r3)     // Catch:{ Exception -> 0x0079 }
                java.lang.String r3 = ".png"
                r0.append(r3)     // Catch:{ Exception -> 0x0079 }
                java.lang.String r0 = r0.toString()     // Catch:{ Exception -> 0x0079 }
                goto L_0x0057
            L_0x0056:
                r0 = r1
            L_0x0057:
                android.graphics.Bitmap r3 = a(r5, r0)     // Catch:{ Exception -> 0x0079 }
                if (r3 != 0) goto L_0x0077
                android.graphics.Bitmap r1 = a(r2)     // Catch:{ Exception -> 0x0074 }
                java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0079 }
                r2.<init>()     // Catch:{ Exception -> 0x0079 }
                r2.append(r5)     // Catch:{ Exception -> 0x0079 }
                r2.append(r0)     // Catch:{ Exception -> 0x0079 }
                java.lang.String r5 = r2.toString()     // Catch:{ Exception -> 0x0079 }
                a(r1, r5)     // Catch:{ Exception -> 0x0079 }
                goto L_0x0083
            L_0x0074:
                r5 = move-exception
                r1 = r3
                goto L_0x007a
            L_0x0077:
                r1 = r3
                goto L_0x0083
            L_0x0079:
                r5 = move-exception
            L_0x007a:
                com.alipay.android.phone.inside.log.api.trace.TraceLogger r0 = com.alipay.android.phone.inside.log.api.LoggerFactory.f()
                java.lang.String r2 = "inside"
                r0.c(r2, r5)
            L_0x0083:
                if (r1 == 0) goto L_0x008c
                com.alipay.android.phone.inside.common.image.ImageLoader$ClipsInfo r5 = r4.b
                android.graphics.Bitmap r5 = com.alipay.android.phone.inside.common.image.ImageLoader.b(r5, r1)
                return r5
            L_0x008c:
                return r1
            L_0x008d:
                r4.cancel(r0)
                return r1
            */
            throw new UnsupportedOperationException("Method not decompiled: com.alipay.android.phone.inside.common.image.ImageLoader.NetImageAsyncTask.doInBackground(java.lang.Object[]):android.graphics.Bitmap");
        }

        /* access modifiers changed from: protected */
        public void onCancelled() {
            super.onCancelled();
        }

        /* access modifiers changed from: protected */
        public void onPreExecute() {
            super.onPreExecute();
        }

        private static Bitmap a(String str, String str2) {
            File file = new File(str);
            if (!file.exists()) {
                file.mkdir();
            }
            File[] listFiles = file.listFiles();
            if (listFiles == null) {
                return null;
            }
            for (File name : listFiles) {
                if (str2.equals(name.getName())) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(str);
                    sb.append(str2);
                    return BitmapFactory.decodeFile(sb.toString());
                }
            }
            return null;
        }
    }

    public static class NetImageFormater {
        public static String a(String str) throws UnsupportedEncodingException {
            return URLDecoder.decode(str.substring(4), "UTF-8");
        }

        public static NetImageUrl a(String str, int[] iArr) {
            String str2;
            String str3 = null;
            if (str == null || "".equals(str)) {
                return null;
            }
            String str4 = SuperId.BIT_2_REALTIMEBUS_BUSSTATION;
            String str5 = SuperId.BIT_2_REALTIMEBUS_BUSSTATION;
            if (iArr != null && iArr.length == 2) {
                if (iArr[0] > 0) {
                    str4 = Integer.toString(iArr[0]);
                }
                if (iArr[1] > 0) {
                    str5 = Integer.toString(iArr[1]);
                }
            }
            if (str.contains("[pixelWidth]") || str.contains("_[pixelWidth]x") || str.contains("_[pixelWidth]x[pixelWidth]")) {
                str2 = str.replace("[pixelWidth]", str4);
            } else if (str.contains("_[imgWidth]x[imgHeight].jpg") || str.contains("&width=[imgWidth]&height=[imgHeight]&type=sns")) {
                str2 = str.replace("[imgWidth]", str4).replace("[imgHeight]", str5);
            } else {
                str2 = str;
            }
            Matcher matcher = ImageLoader.a.matcher(str);
            if (matcher.find()) {
                str3 = matcher.group(2);
            }
            return new NetImageUrl(str2, str3);
        }
    }

    public static class NetImageUrl {
        private String a;
        private String b;

        public NetImageUrl(String str, String str2) {
            this.a = str;
            this.b = str2;
        }

        public final String a() {
            return this.a;
        }

        public final String b() {
            return this.b;
        }
    }

    public static void a(View view, String str, boolean z, ClipsInfo clipsInfo) {
        LoadAction loadAction;
        Application a2 = LauncherApplication.a();
        if (b == null) {
            b = new ImageLoader();
        }
        ImageLoader imageLoader = b;
        if (z) {
            loadAction = LoadAction.Background;
        } else {
            loadAction = LoadAction.Image;
        }
        final LoadAction loadAction2 = loadAction;
        if (view != null && !TextUtils.isEmpty(str)) {
            try {
                if (!TextUtils.isEmpty(str) && str.startsWith("local:")) {
                    b(view, str, loadAction2, clipsInfo, a2);
                    return;
                }
                if (!TextUtils.isEmpty(str) && (str.startsWith("net:") || str.startsWith("http"))) {
                    if (str == null || !str.startsWith("http")) {
                        NetImageFormater.a(str);
                        str = "";
                    }
                    NetImageUrl a3 = NetImageFormater.a(str, clipsInfo.c);
                    NetImageAsyncTask netImageAsyncTask = new NetImageAsyncTask();
                    netImageAsyncTask.b = clipsInfo;
                    final NetImageUrl netImageUrl = a3;
                    final View view2 = view;
                    final ClipsInfo clipsInfo2 = clipsInfo;
                    final Application application = a2;
                    AnonymousClass1 r0 = new ILoadImageCallback<Bitmap>() {
                        public final /* synthetic */ void a(Object obj) {
                            Bitmap bitmap = (Bitmap) obj;
                            if (bitmap != null) {
                                ImageLoader.b(view2, loadAction2, new BitmapDrawable(application.getResources(), bitmap));
                            } else if (netImageUrl.b() == null) {
                                ImageLoader.b(view2);
                            } else {
                                ImageLoader.b(view2, netImageUrl.b(), loadAction2, clipsInfo2, application);
                            }
                        }
                    };
                    if (VERSION.SDK_INT >= 11) {
                        netImageAsyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Object[]{a3.a(), a2, r0});
                    } else {
                        netImageAsyncTask.execute(new Object[]{a3.a(), a2, r0});
                    }
                } else {
                    b(view);
                }
            } catch (Exception e) {
                b(view);
                LoggerFactory.f().c((String) "inside", (Throwable) e);
            }
        }
    }

    /* access modifiers changed from: private */
    public static void b(View view, String str, LoadAction loadAction, ClipsInfo clipsInfo, Context context) {
        if (view != null && str != null && !TextUtils.isEmpty(str)) {
            Drawable drawable = context.getResources().getDrawable(-1);
            if (TextUtils.equals(drawable.getClass().getSimpleName(), BitmapDrawable.class.getSimpleName())) {
                drawable = new BitmapDrawable(context.getResources(), b(clipsInfo, BitmapFactory.decodeResource(context.getResources(), -1)));
            }
            b(view, loadAction, drawable);
        }
    }

    /* access modifiers changed from: private */
    public static void b(View view, LoadAction loadAction, Drawable drawable) {
        if (view != null && drawable != null) {
            switch (loadAction) {
                case Image:
                    if (view instanceof ImageView) {
                        ((ImageView) view).setImageDrawable(drawable);
                        return;
                    } else {
                        view.setBackgroundDrawable(drawable);
                        return;
                    }
                case Background:
                    view.setBackgroundDrawable(drawable);
                    return;
                default:
                    view.setBackgroundDrawable(drawable);
                    return;
            }
        }
    }

    /* access modifiers changed from: private */
    public static Bitmap b(ClipsInfo clipsInfo, Bitmap bitmap) {
        if (clipsInfo == null) {
            return bitmap;
        }
        ClipsType b2 = clipsInfo.b();
        int[] a2 = clipsInfo.a();
        if (b2 == null || b2 == ClipsType.None || a2 == null || a2.length != 2) {
            return bitmap;
        }
        if (a2[0] <= 0) {
            a2[0] = 64;
        }
        if (a2[1] <= 0) {
            a2[1] = 64;
        }
        switch (b2) {
            case Corner:
                bitmap = ClipsInfo.a(bitmap, clipsInfo.c(), a2[0], a2[1]);
                break;
            case Round:
                bitmap = ClipsInfo.a(bitmap, a2[0], a2[1]);
                break;
        }
        return bitmap;
    }

    /* access modifiers changed from: private */
    public static void b(View view) {
        if (view instanceof ImageView) {
            ((ImageView) view).setScaleType(ScaleType.FIT_XY);
        }
    }
}
