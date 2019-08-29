package com.autonavi.minimap.photograph.page;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import com.alipay.mobile.beehive.imageedit.service.ImageEditService;
import com.alipay.mobile.tinyappcommon.utils.H5TinyAppLogUtil;
import com.amap.bundle.blutils.FileUtil;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.map.fragmentcontainer.page.PageTheme.Transparent;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;
import com.autonavi.minimap.photograph.api.IOpenPage.PhotoSelectOptions;
import com.squareup.leakcanary.internal.LeakCanaryInternals;
import java.io.File;
import java.io.IOException;

public abstract class PickPhotoBasePage extends AbstractBasePage<dtm> implements OnClickListener, Transparent {
    public static final String a = Build.MODEL;
    public static final String b = Build.DEVICE;
    public static final String c = Build.MANUFACTURER;
    public static final String d;
    public File e;
    protected String f;
    public boolean g = true;
    public String h;
    private int i = 0;
    private int j;
    private int k;
    private boolean l = false;
    private int m;
    private int n;
    /* access modifiers changed from: private */
    public View o;
    /* access modifiers changed from: private */
    public boolean p = false;

    /* access modifiers changed from: protected */
    public abstract View a(View view);

    /* access modifiers changed from: protected */
    public abstract void b(View view);

    /* access modifiers changed from: protected */
    public abstract int e();

    static {
        StringBuilder sb = new StringBuilder();
        sb.append(FileUtil.getExternalStroragePath(AMapAppGlobal.getApplication()));
        sb.append("/autonavi/out.jpg");
        d = sb.toString();
    }

    /* JADX WARNING: Removed duplicated region for block: B:12:0x001f A[SYNTHETIC, Splitter:B:12:0x001f] */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x0026 A[SYNTHETIC, Splitter:B:20:0x0026] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static android.graphics.Bitmap a(android.content.Context r2, android.net.Uri r3) {
        /*
            r0 = 0
            android.content.ContentResolver r2 = r2.getContentResolver()     // Catch:{ Throwable -> 0x0023, all -> 0x001c }
            java.lang.String r1 = "r"
            android.os.ParcelFileDescriptor r2 = r2.openFileDescriptor(r3, r1)     // Catch:{ Throwable -> 0x0023, all -> 0x001c }
            java.io.FileDescriptor r3 = r2.getFileDescriptor()     // Catch:{ Throwable -> 0x0024, all -> 0x0019 }
            android.graphics.Bitmap r3 = android.graphics.BitmapFactory.decodeFileDescriptor(r3)     // Catch:{ Throwable -> 0x0024, all -> 0x0019 }
            if (r2 == 0) goto L_0x002a
            r2.close()     // Catch:{ IOException -> 0x002a }
            goto L_0x002a
        L_0x0019:
            r3 = move-exception
            r0 = r2
            goto L_0x001d
        L_0x001c:
            r3 = move-exception
        L_0x001d:
            if (r0 == 0) goto L_0x0022
            r0.close()     // Catch:{ IOException -> 0x0022 }
        L_0x0022:
            throw r3
        L_0x0023:
            r2 = r0
        L_0x0024:
            if (r2 == 0) goto L_0x0029
            r2.close()     // Catch:{ IOException -> 0x0029 }
        L_0x0029:
            r3 = r0
        L_0x002a:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.photograph.page.PickPhotoBasePage.a(android.content.Context, android.net.Uri):android.graphics.Bitmap");
    }

    public final void a(String str) {
        if (!TextUtils.isEmpty(str)) {
            PageBundle pageBundle = new PageBundle();
            pageBundle.putString("pick_photo_result", str);
            setResult(ResultType.OK, pageBundle);
        } else {
            setResult(ResultType.CANCEL, (PageBundle) null);
        }
        finish();
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public dtm createPresenter() {
        return new dtm(this);
    }

    public void onCreate(Context context) {
        super.onCreate(context);
        setContentView(e());
        View contentView = getContentView();
        PageBundle arguments = getArguments();
        this.f = arguments.getString("title");
        boolean z = true;
        this.g = arguments.getBoolean(ImageEditService.IN_EDIT_TYPE_CROP, true);
        this.l = arguments.getBoolean(ImageEditService.IN_EDIT_TYPE_CROP, true);
        this.i = arguments.getInt("cropSize", 0);
        this.j = dto.a(AMapPageUtil.getAppContext());
        this.k = this.j;
        this.n = this.i > 0 ? this.i : this.j;
        this.m = this.i > 0 ? this.i : this.j;
        PhotoSelectOptions photoSelectOptions = (PhotoSelectOptions) arguments.getObject("option");
        if (photoSelectOptions == null) {
            photoSelectOptions = PhotoSelectOptions.DEFALUT;
        }
        switch (photoSelectOptions) {
            case TAKE_PHOTO_BY_CAMERA:
                c();
                break;
            case SELECT_PHOTO_FROM_GALLARY:
                b();
                break;
        }
        this.o = a(contentView);
        if (PhotoSelectOptions.DEFALUT == photoSelectOptions) {
            if (contentView != null) {
                contentView.setVisibility(0);
            }
            b(contentView);
        } else if (this.o != null) {
            this.o.setVisibility(4);
        }
        if (this.o == null || this.o.getVisibility() != 0) {
            z = false;
        }
        this.p = z;
        if (contentView != null) {
            contentView.setOnTouchListener(new OnTouchListener() {
                public final boolean onTouch(View view, MotionEvent motionEvent) {
                    if (PickPhotoBasePage.this.o == null || motionEvent.getAction() != 0 || !PickPhotoBasePage.this.p) {
                        return false;
                    }
                    motionEvent.getX();
                    motionEvent.getY();
                    Rect rect = new Rect();
                    PickPhotoBasePage.this.o.getGlobalVisibleRect(rect);
                    if (rect.contains((int) motionEvent.getRawX(), (int) motionEvent.getRawY())) {
                        return true;
                    }
                    PickPhotoBasePage.this.d();
                    return true;
                }
            });
        }
    }

    /* access modifiers changed from: protected */
    public final void b() {
        try {
            startActivityForResult(g(), 12321);
        } catch (ActivityNotFoundException e2) {
            e2.printStackTrace();
            d();
        }
    }

    /* access modifiers changed from: protected */
    public final void c() {
        if ("mounted".equals(Environment.getExternalStorageState())) {
            f();
            return;
        }
        ToastHelper.showToast(getResources().getString(R.string.publish_sd_notexist));
        d();
    }

    private void f() {
        kj.a(AMapAppGlobal.getTopActivity(), new String[]{"android.permission.CAMERA"}, (b) new b() {
            public final void run() {
                try {
                    if (!dto.a.exists()) {
                        dto.a.mkdirs();
                    }
                    PickPhotoBasePage.this.e = new File(dto.a, dto.a());
                    PickPhotoBasePage.this.startActivityForResult(PickPhotoBasePage.a(PickPhotoBasePage.this.e), 12323);
                } catch (Exception e) {
                    e.printStackTrace();
                    PickPhotoBasePage.this.d();
                }
            }
        });
    }

    private static Intent g() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        return intent;
    }

    private static boolean h() {
        return VERSION.SDK_INT >= 24;
    }

    public static Intent a(File file) {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        if (h()) {
            intent.addFlags(1);
        }
        intent.putExtra(H5TinyAppLogUtil.TINY_APP_STANDARD_OUTPUT, c(file));
        return intent;
    }

    /* JADX WARNING: Removed duplicated region for block: B:26:0x004b A[SYNTHETIC, Splitter:B:26:0x004b] */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x0059 A[SYNTHETIC, Splitter:B:32:0x0059] */
    @edu.umd.cs.findbugs.annotations.SuppressFBWarnings({"OBL_UNSATISFIED_OBLIGATION_EXCEPTION_EDGE"})
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String a(android.graphics.Bitmap r5) {
        /*
            java.lang.String r0 = ""
            r1 = 0
            java.io.File r2 = defpackage.dto.b     // Catch:{ Exception -> 0x0045 }
            boolean r2 = r2.exists()     // Catch:{ Exception -> 0x0045 }
            if (r2 != 0) goto L_0x0010
            java.io.File r2 = defpackage.dto.b     // Catch:{ Exception -> 0x0045 }
            r2.mkdirs()     // Catch:{ Exception -> 0x0045 }
        L_0x0010:
            java.io.File r2 = new java.io.File     // Catch:{ Exception -> 0x0045 }
            java.io.File r3 = defpackage.dto.b     // Catch:{ Exception -> 0x0045 }
            java.lang.String r4 = defpackage.dto.a()     // Catch:{ Exception -> 0x0045 }
            r2.<init>(r3, r4)     // Catch:{ Exception -> 0x0045 }
            r2.createNewFile()     // Catch:{ Exception -> 0x0045 }
            java.lang.String r3 = r2.getAbsolutePath()     // Catch:{ Exception -> 0x0045 }
            java.io.FileOutputStream r0 = new java.io.FileOutputStream     // Catch:{ Exception -> 0x0040 }
            r0.<init>(r2)     // Catch:{ Exception -> 0x0040 }
            android.graphics.Bitmap$CompressFormat r1 = android.graphics.Bitmap.CompressFormat.JPEG     // Catch:{ Exception -> 0x003d, all -> 0x003a }
            r2 = 100
            r5.compress(r1, r2, r0)     // Catch:{ Exception -> 0x003d, all -> 0x003a }
            r0.flush()     // Catch:{ Exception -> 0x0035 }
            r0.close()     // Catch:{ Exception -> 0x0035 }
            goto L_0x0039
        L_0x0035:
            r5 = move-exception
            r5.printStackTrace()
        L_0x0039:
            return r3
        L_0x003a:
            r5 = move-exception
            r1 = r0
            goto L_0x0057
        L_0x003d:
            r5 = move-exception
            r1 = r0
            goto L_0x0041
        L_0x0040:
            r5 = move-exception
        L_0x0041:
            r0 = r3
            goto L_0x0046
        L_0x0043:
            r5 = move-exception
            goto L_0x0057
        L_0x0045:
            r5 = move-exception
        L_0x0046:
            r5.printStackTrace()     // Catch:{ all -> 0x0043 }
            if (r1 == 0) goto L_0x0056
            r1.flush()     // Catch:{ Exception -> 0x0052 }
            r1.close()     // Catch:{ Exception -> 0x0052 }
            goto L_0x0056
        L_0x0052:
            r5 = move-exception
            r5.printStackTrace()
        L_0x0056:
            return r0
        L_0x0057:
            if (r1 == 0) goto L_0x0064
            r1.flush()     // Catch:{ Exception -> 0x0060 }
            r1.close()     // Catch:{ Exception -> 0x0060 }
            goto L_0x0064
        L_0x0060:
            r0 = move-exception
            r0.printStackTrace()
        L_0x0064:
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.photograph.page.PickPhotoBasePage.a(android.graphics.Bitmap):java.lang.String");
    }

    public final void b(File file) {
        try {
            startActivityForResult(a(c(file)), 12322);
        } catch (Exception unused) {
            ToastHelper.showLongToast(getResources().getString(R.string.publish_fail));
        }
    }

    public final void d() {
        this.h = null;
        a((String) null);
    }

    private Intent a(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra(ImageEditService.IN_EDIT_TYPE_CROP, "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        if (this.l) {
            intent.putExtra("outputX", this.m);
            intent.putExtra("outputY", this.n);
        } else {
            intent.putExtra("outputX", this.j);
            intent.putExtra("outputY", this.k);
        }
        intent.putExtra(WidgetType.SCALE, true);
        intent.putExtra("return-data", true);
        if ("MB860".equals(a) && "olympus".equals(b) && LeakCanaryInternals.MOTOROLA.equals(c)) {
            intent.putExtra("return-data", true);
        } else {
            File file = new File(d);
            try {
                if (file.exists()) {
                    file.delete();
                }
                file.getParentFile().mkdirs();
                file.createNewFile();
            } catch (IOException unused) {
            }
            intent.putExtra("outputFormat", CompressFormat.JPEG.toString());
            intent.putExtra("noFaceDetection", false);
            intent.putExtra(H5TinyAppLogUtil.TINY_APP_STANDARD_OUTPUT, Uri.fromFile(file));
            if (!TextUtils.isEmpty(c) && c.equalsIgnoreCase("LeMobile") && !TextUtils.isEmpty(a) && a.startsWith("Le X")) {
                intent.putExtra("return-data", false);
            }
        }
        if (h()) {
            intent.addFlags(1);
        }
        return intent;
    }

    private static Uri c(File file) {
        if (h()) {
            return FileProvider.getUriForFile(AMapPageUtil.getAppContext(), FileUtil.FILE_PROVIDER, file);
        }
        return Uri.fromFile(file);
    }
}
