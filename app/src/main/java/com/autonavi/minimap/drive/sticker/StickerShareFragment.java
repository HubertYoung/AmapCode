package com.autonavi.minimap.drive.sticker;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnDismissListener;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.os.Environment;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.amap.bundle.drivecommon.mvp.view.DriveBaseMapPage;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.map.core.MapManager;
import com.autonavi.map.widget.ProgressDlg;
import com.autonavi.minimap.R;
import com.autonavi.minimap.bundle.share.entity.ShareParam;
import com.autonavi.minimap.bundle.share.entity.ShareParam.e;
import com.autonavi.minimap.bundle.share.entity.ShareParam.f;
import com.autonavi.minimap.map.overlayholder.OverlayPage.OvProperty;
import com.autonavi.minimap.map.overlayholder.OverlayPage.OverlayPageProperty;
import com.autonavi.minimap.map.overlayholder.OverlayPage.UvOverlay;
import com.autonavi.minimap.map.overlayholder.OverlayPage.VisiblePolicy;
import com.autonavi.widget.ui.TitleBar;
import java.io.File;

@OverlayPageProperty(overlays = {@OvProperty(clickable = false, moveToFocus = false, overlay = UvOverlay.TrafficOverlay, visible = false, visiblePolicy = VisiblePolicy.IgnoreConfig), @OvProperty(clickable = false, moveToFocus = false, overlay = UvOverlay.GpsOverlay, visible = false, visiblePolicy = VisiblePolicy.IgnoreConfig)})
public class StickerShareFragment extends DriveBaseMapPage<djd> {
    /* access modifiers changed from: private */
    public static String q = "sitckerShare.png";
    /* access modifiers changed from: private */
    public static String r = "sitckerShareThumbnail.png";
    public div a;
    public Button b;
    public ImageView c;
    public TextView d;
    public TextView e;
    public TextView f;
    public TextView g;
    public TextView h;
    public TextView i;
    public TitleBar j;
    public FrameLayout k;
    public TextView l;
    public RelativeLayout m;
    public OnClickListener n = new OnClickListener() {
        public final void onClick(View view) {
            if (view == StickerShareFragment.this.b) {
                StickerShareFragment.b(StickerShareFragment.this);
            }
        }
    };
    public ProgressDlg o;
    protected boolean p = false;
    private c s = new c() {
        public final void a(final String str) {
            if (TextUtils.isEmpty(str)) {
                ToastHelper.showToast(StickerShareFragment.this.getString(R.string.screenshot_fail));
                StickerShareFragment.this.d();
            } else if (!StickerShareFragment.this.p) {
                ahn.b().execute(new Runnable() {
                    public final void run() {
                        if (!StickerShareFragment.this.p) {
                            Bitmap decodeFile = BitmapFactory.decodeFile(str);
                            if (decodeFile != null) {
                                Bitmap createBitmap = Bitmap.createBitmap(decodeFile.getWidth(), decodeFile.getHeight() - agn.a(StickerShareFragment.this.getContext(), 68.0f), Config.RGB_565);
                                new Canvas(createBitmap).drawBitmap(decodeFile, 0.0f, 0.0f, new Paint());
                                decodeFile.recycle();
                                if (!StickerShareFragment.this.p) {
                                    Bitmap a2 = StickerShareFragment.this.a(createBitmap);
                                    if (createBitmap != null) {
                                        createBitmap.recycle();
                                    }
                                    if (a2 == null) {
                                        aho.a(new Runnable() {
                                            public final void run() {
                                                StickerShareFragment.this.d();
                                            }
                                        });
                                    } else if (!StickerShareFragment.this.p) {
                                        StickerShareFragment.this.a(ahc.a(a2, a2.getWidth() >> 3, a2.getHeight() >> 3), StickerShareFragment.r);
                                        StickerShareFragment.this.a(a2, StickerShareFragment.q);
                                        if (StickerShareFragment.this.p) {
                                            StickerShareFragment.this.p = false;
                                            aho.a(new Runnable() {
                                                public final void run() {
                                                    StickerShareFragment.this.d();
                                                }
                                            });
                                            return;
                                        }
                                        aho.a(new Runnable() {
                                            public final void run() {
                                                Context context = StickerShareFragment.this.getContext();
                                                String a2 = StickerShareFragment.a(StickerShareFragment.r);
                                                String a3 = StickerShareFragment.a(StickerShareFragment.q);
                                                dct dct = new dct();
                                                dct.a();
                                                dct.f = true;
                                                dct.d = true;
                                                dct.e = true;
                                                dcb dcb = (dcb) a.a.a(dcb.class);
                                                if (dcb != null) {
                                                    dcb.a(dct, (dcd) new dcd(a2, a3, context) {
                                                        final /* synthetic */ String a;
                                                        final /* synthetic */ String b;
                                                        final /* synthetic */ Context c;

                                                        {
                                                            this.a = r1;
                                                            this.b = r2;
                                                            this.c = r3;
                                                        }

                                                        public final ShareParam getShareDataByType(int i) {
                                                            switch (i) {
                                                                case 3:
                                                                    e eVar = new e(0);
                                                                    eVar.g = BitmapFactory.decodeFile(this.a);
                                                                    eVar.h = this.b;
                                                                    eVar.c = false;
                                                                    eVar.e = 3;
                                                                    return eVar;
                                                                case 4:
                                                                    e eVar2 = new e(1);
                                                                    eVar2.g = BitmapFactory.decodeFile(this.a);
                                                                    eVar2.h = this.b;
                                                                    eVar2.c = false;
                                                                    eVar2.e = 3;
                                                                    return eVar2;
                                                                case 5:
                                                                    f fVar = new f();
                                                                    fVar.a = this.c.getString(R.string.drive_carefully_tips);
                                                                    fVar.j = true;
                                                                    fVar.h = this.b;
                                                                    fVar.c = false;
                                                                    return fVar;
                                                                default:
                                                                    return null;
                                                            }
                                                        }
                                                    });
                                                }
                                                StickerShareFragment.this.d();
                                            }
                                        });
                                    }
                                }
                            }
                        }
                    }
                });
            }
        }
    };

    public View getMapSuspendView() {
        return null;
    }

    /* access modifiers changed from: private */
    /* renamed from: c */
    public djd createPresenter() {
        return new djd(this);
    }

    public void onCreate(Context context) {
        super.onCreate(context);
        setContentView(R.layout.drive_route_stickers_share_layout);
    }

    /* JADX WARNING: Removed duplicated region for block: B:42:0x007c A[SYNTHETIC, Splitter:B:42:0x007c] */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x0088 A[SYNTHETIC, Splitter:B:49:0x0088] */
    /* JADX WARNING: Removed duplicated region for block: B:53:0x0090 A[SYNTHETIC, Splitter:B:53:0x0090] */
    /* JADX WARNING: Unknown top exception splitter block from list: {B:46:0x0083=Splitter:B:46:0x0083, B:39:0x0077=Splitter:B:39:0x0077} */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final synchronized void a(android.graphics.Bitmap r4, java.lang.String r5) {
        /*
            r3 = this;
            monitor-enter(r3)
            if (r4 != 0) goto L_0x0005
            monitor-exit(r3)
            return
        L_0x0005:
            java.lang.String r5 = a(r5)     // Catch:{ IOException -> 0x009b }
            boolean r0 = android.text.TextUtils.isEmpty(r5)     // Catch:{ IOException -> 0x009b }
            if (r0 != 0) goto L_0x009f
            java.io.File r0 = new java.io.File     // Catch:{ IOException -> 0x009b }
            r0.<init>(r5)     // Catch:{ IOException -> 0x009b }
            boolean r1 = r0.exists()     // Catch:{ IOException -> 0x009b }
            if (r1 == 0) goto L_0x0020
            boolean r1 = r0.delete()     // Catch:{ IOException -> 0x009b }
            if (r1 == 0) goto L_0x009f
        L_0x0020:
            boolean r1 = r0.createNewFile()     // Catch:{ IOException -> 0x009b }
            if (r1 == 0) goto L_0x009f
            r1 = 0
            java.io.FileOutputStream r2 = new java.io.FileOutputStream     // Catch:{ FileNotFoundException -> 0x0082, Exception -> 0x0076 }
            r2.<init>(r5)     // Catch:{ FileNotFoundException -> 0x0082, Exception -> 0x0076 }
            android.graphics.Bitmap$CompressFormat r5 = android.graphics.Bitmap.CompressFormat.PNG     // Catch:{ FileNotFoundException -> 0x0070, Exception -> 0x006d, all -> 0x006b }
            r1 = 100
            r4.compress(r5, r1, r2)     // Catch:{ FileNotFoundException -> 0x0070, Exception -> 0x006d, all -> 0x006b }
            java.lang.String r5 = r0.getParent()     // Catch:{ FileNotFoundException -> 0x0070, Exception -> 0x006d, all -> 0x006b }
            java.io.File r0 = new java.io.File     // Catch:{ FileNotFoundException -> 0x0070, Exception -> 0x006d, all -> 0x006b }
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ FileNotFoundException -> 0x0070, Exception -> 0x006d, all -> 0x006b }
            r1.<init>()     // Catch:{ FileNotFoundException -> 0x0070, Exception -> 0x006d, all -> 0x006b }
            r1.append(r5)     // Catch:{ FileNotFoundException -> 0x0070, Exception -> 0x006d, all -> 0x006b }
            java.lang.String r5 = "/.nomedia"
            r1.append(r5)     // Catch:{ FileNotFoundException -> 0x0070, Exception -> 0x006d, all -> 0x006b }
            java.lang.String r5 = r1.toString()     // Catch:{ FileNotFoundException -> 0x0070, Exception -> 0x006d, all -> 0x006b }
            r0.<init>(r5)     // Catch:{ FileNotFoundException -> 0x0070, Exception -> 0x006d, all -> 0x006b }
            boolean r5 = r0.exists()     // Catch:{ FileNotFoundException -> 0x0070, Exception -> 0x006d, all -> 0x006b }
            if (r5 != 0) goto L_0x0062
            boolean r5 = r0.createNewFile()     // Catch:{ FileNotFoundException -> 0x0070, Exception -> 0x006d, all -> 0x006b }
            if (r5 != 0) goto L_0x0062
            r2.close()     // Catch:{ Exception -> 0x005d }
            goto L_0x009f
        L_0x005d:
            r5 = move-exception
        L_0x005e:
            defpackage.kf.a(r5)     // Catch:{ IOException -> 0x009b }
            goto L_0x009f
        L_0x0062:
            r2.flush()     // Catch:{ FileNotFoundException -> 0x0070, Exception -> 0x006d, all -> 0x006b }
            r2.close()     // Catch:{ Exception -> 0x0069 }
            goto L_0x009f
        L_0x0069:
            r5 = move-exception
            goto L_0x005e
        L_0x006b:
            r5 = move-exception
            goto L_0x008e
        L_0x006d:
            r5 = move-exception
            r1 = r2
            goto L_0x0077
        L_0x0070:
            r5 = move-exception
            r1 = r2
            goto L_0x0083
        L_0x0073:
            r5 = move-exception
            r2 = r1
            goto L_0x008e
        L_0x0076:
            r5 = move-exception
        L_0x0077:
            defpackage.kf.a(r5)     // Catch:{ all -> 0x0073 }
            if (r1 == 0) goto L_0x009f
            r1.close()     // Catch:{ Exception -> 0x0080 }
            goto L_0x009f
        L_0x0080:
            r5 = move-exception
            goto L_0x005e
        L_0x0082:
            r5 = move-exception
        L_0x0083:
            defpackage.kf.a(r5)     // Catch:{ all -> 0x0073 }
            if (r1 == 0) goto L_0x009f
            r1.close()     // Catch:{ Exception -> 0x008c }
            goto L_0x009f
        L_0x008c:
            r5 = move-exception
            goto L_0x005e
        L_0x008e:
            if (r2 == 0) goto L_0x0098
            r2.close()     // Catch:{ Exception -> 0x0094 }
            goto L_0x0098
        L_0x0094:
            r0 = move-exception
            defpackage.kf.a(r0)     // Catch:{ IOException -> 0x009b }
        L_0x0098:
            throw r5     // Catch:{ IOException -> 0x009b }
        L_0x0099:
            r4 = move-exception
            goto L_0x00a4
        L_0x009b:
            r5 = move-exception
            defpackage.kf.a(r5)     // Catch:{ all -> 0x0099 }
        L_0x009f:
            r4.recycle()     // Catch:{ all -> 0x0099 }
            monitor-exit(r3)
            return
        L_0x00a4:
            monitor-exit(r3)
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.drive.sticker.StickerShareFragment.a(android.graphics.Bitmap, java.lang.String):void");
    }

    protected static String a(String str) {
        String str2 = null;
        if (Environment.getExternalStorageState().equals("mounted")) {
            File externalStorageDirectory = Environment.getExternalStorageDirectory();
            StringBuilder sb = new StringBuilder("autonavi");
            sb.append(File.separator);
            sb.append("navishare");
            File file = new File(externalStorageDirectory, sb.toString());
            if (!file.exists() && !file.mkdir()) {
                return null;
            }
            StringBuilder sb2 = new StringBuilder();
            sb2.append(file.getAbsolutePath());
            sb2.append("/");
            sb2.append(str);
            str2 = sb2.toString();
        }
        return str2;
    }

    /* access modifiers changed from: private */
    public Bitmap a(Bitmap bitmap) {
        Bitmap bitmap2;
        Error error;
        Bitmap bitmap3 = bitmap;
        Context context = getContext();
        if (context == null || bitmap3 == null) {
            return null;
        }
        int a2 = agn.a(getContext(), 15.0f);
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int i2 = displayMetrics.widthPixels;
        int i3 = displayMetrics.heightPixels;
        int i4 = i2 + 0;
        try {
            int height = ((i4 + 0) * bitmap.getHeight()) / bitmap.getWidth();
            Bitmap decodeResource = BitmapFactory.decodeResource(getResources(), R.drawable.footnavi_share_logo);
            int i5 = a2 + 0 + height;
            int height2 = a2 + i5 + decodeResource.getHeight();
            bitmap2 = Bitmap.createBitmap(i2, height2, Config.RGB_565);
            try {
                Canvas canvas = new Canvas(bitmap2);
                if (this.p) {
                    return null;
                }
                Paint paint = new Paint();
                paint.setColor(-1);
                paint.setStyle(Style.FILL);
                Canvas canvas2 = canvas;
                canvas.drawRect(0.0f, 0.0f, (float) i2, (float) height2, paint);
                canvas2.drawBitmap(bitmap3, new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight()), new Rect(0, 0, i4, height + 0), null);
                canvas2.drawBitmap(decodeResource, (float) ((i2 - decodeResource.getWidth()) / 2), (float) i5, null);
                return bitmap2;
            } catch (OutOfMemoryError e2) {
                error = e2;
                ToastHelper.showToast(getString(R.string.route_screenshots_error));
                kf.a(error);
                return bitmap2;
            }
        } catch (OutOfMemoryError e3) {
            error = e3;
            bitmap2 = null;
            ToastHelper.showToast(getString(R.string.route_screenshots_error));
            kf.a(error);
            return bitmap2;
        }
    }

    /* access modifiers changed from: private */
    public void d() {
        this.b.setClickable(true);
        this.b.setTextColor(-1);
        if (this.o != null) {
            this.o.dismiss();
            this.o = null;
        }
    }

    static /* synthetic */ void b(StickerShareFragment stickerShareFragment) {
        stickerShareFragment.b.setClickable(false);
        stickerShareFragment.b.setTextColor(Integer.MAX_VALUE);
        String string = stickerShareFragment.getString(R.string.screenshoting);
        try {
            stickerShareFragment.d();
            stickerShareFragment.j.setWidgetVisibility(1, 4);
            stickerShareFragment.p = false;
            if (TextUtils.isEmpty(string)) {
                string = "";
            }
            stickerShareFragment.o = new ProgressDlg(stickerShareFragment.getActivity(), string, "");
            stickerShareFragment.o.setCancelable(false);
            stickerShareFragment.o.setOnDismissListener(new OnDismissListener() {
                public final void onDismiss(DialogInterface dialogInterface) {
                    StickerShareFragment.this.j.setWidgetVisibility(1, 0);
                }
            });
            stickerShareFragment.o.setOnCancelListener(new OnCancelListener() {
                public final void onCancel(DialogInterface dialogInterface) {
                    StickerShareFragment.this.p = true;
                }
            });
            stickerShareFragment.o.show();
        } catch (Exception e2) {
            kf.a((Throwable) e2);
        }
        MapManager mapManager = stickerShareFragment.getMapManager();
        if (mapManager != null) {
            cfc.a().a(mapManager, stickerShareFragment.s);
        }
    }
}
