package com.autonavi.mine.qrcode;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Rect;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import com.alipay.mobile.beehive.imageedit.service.ImageEditService;
import com.alipay.mobile.tinyappcommon.utils.H5TinyAppLogUtil;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.aosservice.response.AosResponseException;
import com.amap.bundle.blutils.FileUtil;
import com.amap.bundle.network.response.AbstractAOSParser;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;
import com.autonavi.minimap.falcon.base.FalconAosPrepareResponseCallback;
import com.autonavi.minimap.qrcode.QRCodeScanActivityHandler;
import com.autonavi.minimap.qrcode.QrCodeRequestHolder;
import com.autonavi.minimap.qrcode.param.ConfirmRequest;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.DecodeHintType;
import com.google.zxing.Result;
import com.squareup.leakcanary.internal.LeakCanaryInternals;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public class QRCodeScanActivity extends Activity implements Callback, dtz {
    public static final File a;
    public static final String b = Build.MODEL;
    public static final String c = Build.DEVICE;
    public static final String d = Build.MANUFACTURER;
    public static final String e;
    private Handler A = new Handler() {
        public final void handleMessage(Message message) {
            if (message.what == R.id.return_scan_result) {
                QRCodeScanActivity.this.z.dismiss();
                QRCodeScanActivity.this.a((Result) message.obj);
                return;
            }
            if (message.what == R.id.decode_failed) {
                QRCodeScanActivity.this.z.dismiss();
                ToastHelper.showToast(AMapPageUtil.getAppContext().getString(R.string.qrscan_failed));
            }
        }
    };
    /* access modifiers changed from: private */
    public Uri B;
    /* access modifiers changed from: private */
    public FalconAosPrepareResponseCallback<JSONObject> C = new FalconAosPrepareResponseCallback<JSONObject>() {
        public final void a(AosRequest aosRequest, AosResponseException aosResponseException) {
        }

        public final /* synthetic */ Object a(AosByteResponse aosByteResponse) {
            return b(aosByteResponse);
        }

        public final /* synthetic */ void a(Object obj) {
            JSONObject jSONObject = (JSONObject) obj;
            if (jSONObject != null) {
                if (jSONObject.optInt("code") == 1) {
                    ToastHelper.showLongToast("登录成功！");
                    return;
                }
                StringBuilder sb = new StringBuilder("登录失败！");
                sb.append(jSONObject.toString());
                ToastHelper.showLongToast(sb.toString());
            }
        }

        private static JSONObject b(AosByteResponse aosByteResponse) {
            if (aosByteResponse.getResult() == null) {
                return null;
            }
            try {
                return AbstractAOSParser.aosByteResponseToJSONObject(aosByteResponse);
            } catch (JSONException unused) {
                return null;
            }
        }
    };
    private final OnCompletionListener D = new OnCompletionListener() {
        public final void onCompletion(MediaPlayer mediaPlayer) {
            mediaPlayer.seekTo(0);
        }
    };
    private final OnPreparedListener E = new OnPreparedListener() {
        public final void onPrepared(MediaPlayer mediaPlayer) {
            QRCodeScanActivity.this.f = true;
        }
    };
    boolean f = false;
    private dtt g;
    private QRCodeScanActivityHandler h;
    private dty i;
    private boolean j;
    private Collection<BarcodeFormat> k;
    private Map<DecodeHintType, ?> l;
    private String m;
    private MediaPlayer n;
    private boolean o;
    private boolean p;
    /* access modifiers changed from: private */
    public Button q;
    private ImageButton r;
    private ImageButton s;
    private ImageButton t;
    private ImageButton u;
    private TextView v;
    private boolean w = false;
    private View x;
    /* access modifiers changed from: private */
    public dtw y;
    /* access modifiers changed from: private */
    public ProgressDialog z;

    public final Context b() {
        return this;
    }

    public void surfaceChanged(SurfaceHolder surfaceHolder, int i2, int i3, int i4) {
    }

    static {
        StringBuilder sb = new StringBuilder();
        sb.append(Environment.getExternalStorageDirectory());
        sb.append("/DCIM/CROP");
        a = new File(sb.toString());
        StringBuilder sb2 = new StringBuilder();
        sb2.append(FileUtil.getExternalStroragePath(AMapAppGlobal.getApplication()));
        sb2.append("/autonavi/out.jpg");
        e = sb2.toString();
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.v4_qrcode_scan);
        this.j = false;
        this.i = (dty) findViewById(R.id.viewfinder_view);
        this.i.setStateChangedListner(new a() {
            public final void a(Rect rect) {
                QRCodeScanActivity.a(QRCodeScanActivity.this, rect.bottom);
            }
        });
        this.q = (Button) findViewById(R.id.btn_cancel_scan);
        this.x = findViewById(R.id.tip_panel);
        AnonymousClass5 r0 = new OnClickListener() {
            public final void onClick(View view) {
                QRCodeScanActivity.this.finish();
            }
        };
        this.r = (ImageButton) findViewById(R.id.title_btn_left);
        this.r.setImageDrawable(getResources().getDrawable(R.drawable.v4_com_title_bar_selector));
        this.r.setOnClickListener(r0);
        this.t = (ImageButton) findViewById(R.id.qrcode_btn_back);
        this.t.setOnClickListener(r0);
        AnonymousClass6 r02 = new OnClickListener() {
            public final void onClick(View view) {
                QRCodeScanActivity qRCodeScanActivity = QRCodeScanActivity.this;
                try {
                    Intent intent = new Intent("android.intent.action.GET_CONTENT");
                    intent.setType("image/*");
                    qRCodeScanActivity.startActivityForResult(intent, 12321);
                } catch (ActivityNotFoundException e) {
                    e.printStackTrace();
                    ToastHelper.showLongToast(qRCodeScanActivity.getResources().getString(R.string.publish_fail));
                }
            }
        };
        this.s = (ImageButton) findViewById(R.id.title_btn_right);
        this.s.setImageDrawable(getResources().getDrawable(R.drawable.qrcode_scan_photo));
        this.s.setVisibility(0);
        this.s.setOnClickListener(r02);
        this.u = (ImageButton) findViewById(R.id.qrcode_btn_photo);
        this.u.setOnClickListener(r02);
        this.v = (TextView) findViewById(R.id.title_text_name);
        this.v.setText(R.string.v4_qrscan);
        this.z = new ProgressDialog(this);
        this.z.setMessage(getString(R.string.barcode_decoding));
        this.z.setCanceledOnTouchOutside(false);
        this.z.setOnCancelListener(new OnCancelListener() {
            public final void onCancel(DialogInterface dialogInterface) {
                if (QRCodeScanActivity.this.y != null && QRCodeScanActivity.this.y.isAlive()) {
                    QRCodeScanActivity.this.y.interrupt();
                    QRCodeScanActivity.this.y = null;
                }
            }
        });
        this.o = true;
        if (((AudioManager) getSystemService("audio")).getRingerMode() != 2) {
            this.o = false;
        }
        if (this.o && this.n == null) {
            setVolumeControlStream(3);
            this.f = false;
            this.n = new MediaPlayer();
            this.n.setAudioStreamType(3);
            this.n.setOnCompletionListener(this.D);
            this.n.setOnPreparedListener(this.E);
            AssetFileDescriptor openRawResourceFd = getResources().openRawResourceFd(R.raw.beep);
            try {
                this.n.setDataSource(openRawResourceFd.getFileDescriptor(), openRawResourceFd.getStartOffset(), openRawResourceFd.getLength());
                openRawResourceFd.close();
                this.n.setVolume(0.5f, 0.5f);
                this.n.prepareAsync();
            } catch (IOException unused) {
                this.n = null;
                this.f = false;
            }
        }
        this.p = true;
        this.q.setOnClickListener(new OnClickListener() {
            public final void onClick(View view) {
                if (QRCodeScanActivity.this.q.getText().equals(QRCodeScanActivity.this.getString(R.string.qr_login))) {
                    ConfirmRequest confirmRequest = new ConfirmRequest();
                    confirmRequest.b = QRCodeScanActivity.this.B.getQueryParameter("id");
                    QrCodeRequestHolder.getInstance().sendConfirm(confirmRequest, QRCodeScanActivity.this.C);
                    return;
                }
                QRCodeScanActivity.this.finish();
            }
        });
    }

    private void a(String str) {
        if (!TextUtils.isEmpty(str)) {
            this.z.show();
            Intent intent = new Intent();
            intent.putExtra("INTENT_PHOTO_CUT_PATH", str);
            this.y = new dtw(this, this.A, intent);
            this.y.start();
        }
    }

    private static boolean f() {
        return VERSION.SDK_INT >= 24;
    }

    private Intent a(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra(ImageEditService.IN_EDIT_TYPE_CROP, "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.qrcode_save_dimen);
        intent.putExtra("outputX", dimensionPixelSize);
        intent.putExtra("outputY", dimensionPixelSize);
        intent.putExtra(WidgetType.SCALE, true);
        if (g()) {
            intent.putExtra("return-data", true);
        } else {
            File file = new File(e);
            try {
                if (file.exists()) {
                    file.delete();
                }
                file.getParentFile().mkdirs();
                file.createNewFile();
            } catch (IOException unused) {
            }
            intent.putExtra(H5TinyAppLogUtil.TINY_APP_STANDARD_OUTPUT, Uri.fromFile(file));
            intent.putExtra("return-data", false);
            intent.putExtra("outputFormat", CompressFormat.JPEG.toString());
            intent.putExtra("noFaceDetection", false);
        }
        if (f()) {
            intent.addFlags(1);
        }
        return intent;
    }

    public void onActivityResult(int i2, int i3, Intent intent) {
        String str;
        super.onActivityResult(i2, i3, intent);
        if (i3 == -1) {
            switch (i2) {
                case 12321:
                    try {
                        Uri data = intent.getData();
                        if (data != null) {
                            a(new File(ahb.a(this, data)));
                        } else {
                            Bundle extras = intent.getExtras();
                            if (extras != null) {
                                Bitmap bitmap = (Bitmap) extras.getParcelable("data");
                                if (bitmap != null) {
                                    a(new File(a(bitmap)));
                                } else {
                                    return;
                                }
                            }
                        }
                    } catch (Exception unused) {
                        ToastHelper.showToast(getResources().getString(R.string.gallay_error));
                    }
                    a((String) null);
                    return;
                case 12322:
                    if (g()) {
                        str = a((Bitmap) intent.getParcelableExtra("data"));
                    } else {
                        str = e;
                    }
                    a(str);
                    break;
            }
        }
    }

    private static boolean g() {
        return "MB860".equals(b) && "olympus".equals(c) && LeakCanaryInternals.MOTOROLA.equals(d);
    }

    /* JADX WARNING: Removed duplicated region for block: B:26:0x004b A[SYNTHETIC, Splitter:B:26:0x004b] */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x0059 A[SYNTHETIC, Splitter:B:32:0x0059] */
    @edu.umd.cs.findbugs.annotations.SuppressFBWarnings({"OBL_UNSATISFIED_OBLIGATION_EXCEPTION_EDGE"})
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.lang.String a(android.graphics.Bitmap r5) {
        /*
            java.lang.String r0 = ""
            r1 = 0
            java.io.File r2 = a     // Catch:{ Exception -> 0x0045 }
            boolean r2 = r2.exists()     // Catch:{ Exception -> 0x0045 }
            if (r2 != 0) goto L_0x0010
            java.io.File r2 = a     // Catch:{ Exception -> 0x0045 }
            r2.mkdirs()     // Catch:{ Exception -> 0x0045 }
        L_0x0010:
            java.io.File r2 = new java.io.File     // Catch:{ Exception -> 0x0045 }
            java.io.File r3 = a     // Catch:{ Exception -> 0x0045 }
            java.lang.String r4 = defpackage.ahb.a()     // Catch:{ Exception -> 0x0045 }
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
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.mine.qrcode.QRCodeScanActivity.a(android.graphics.Bitmap):java.lang.String");
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        if (getRequestedOrientation() != 1) {
            setRequestedOrientation(1);
        }
        super.onResume();
        if (this.g == null) {
            this.g = new dtt(getApplication());
            this.i.setCameraManager(this.g);
        }
        this.h = null;
        this.k = null;
        this.m = null;
        SurfaceHolder holder = ((SurfaceView) findViewById(R.id.preview_view)).getHolder();
        if (this.j) {
            a(holder);
        } else {
            holder.addCallback(this);
        }
    }

    public boolean onKeyDown(int i2, KeyEvent keyEvent) {
        if (i2 == 4) {
            finish();
            return true;
        } else if (i2 == 27 || i2 == 80) {
            return true;
        } else {
            return super.onKeyDown(i2, keyEvent);
        }
    }

    private void h() {
        if (this.h != null) {
            this.h.a();
            this.h = null;
        }
        if (this.g != null) {
            this.g.b();
        }
    }

    /* access modifiers changed from: protected */
    public void onPause() {
        h();
        super.onPause();
    }

    private void a(SurfaceHolder surfaceHolder) {
        if (surfaceHolder == null) {
            throw new IllegalStateException("No SurfaceHolder provided");
        } else if (this.g == null) {
            throw new IllegalStateException("CameraManager is null");
        } else {
            try {
                this.g.a(surfaceHolder);
                if (this.h == null) {
                    QRCodeScanActivityHandler qRCodeScanActivityHandler = new QRCodeScanActivityHandler(this, this.k, this.l, this.m, this.g);
                    this.h = qRCodeScanActivityHandler;
                }
            } catch (IOException unused) {
            } catch (RuntimeException unused2) {
                ToastHelper.showToast(AMapAppGlobal.getApplication().getString(R.string.camera_init_fail));
                finish();
            }
        }
    }

    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        if (!this.j) {
            this.j = true;
            a(surfaceHolder);
        }
    }

    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        this.j = false;
        h();
    }

    public final void a(Result result) {
        if (!this.w) {
            if (result == null) {
                i();
                return;
            }
            if (this.o && this.n != null && this.f) {
                this.n.start();
            }
            if (this.p) {
                ((Vibrator) getSystemService("vibrator")).vibrate(200);
            }
            String text = result.getText();
            if (TextUtils.isEmpty(text)) {
                i();
                return;
            }
            this.B = Uri.parse(text);
            String scheme = this.B.getScheme();
            if (TextUtils.isEmpty(scheme) || (!scheme.equals("http") && !scheme.equals("https") && !scheme.equals("androidamap") && !scheme.equals("amaplink"))) {
                mi miVar = new mi(this);
                miVar.a(R.string.qr_code_unrecognized);
                miVar.b(R.string.Ok, new a() {
                    public final void a(mi miVar) {
                        miVar.a.dismiss();
                        new Thread() {
                            public final void run() {
                                QRCodeScanActivity.this.i();
                            }
                        }.start();
                    }
                });
                miVar.a();
                miVar.b();
                return;
            }
            if (!TextUtils.isEmpty(text)) {
                Intent intent = new Intent();
                intent.putExtra("qr_uri", text);
                setResult(-1, intent);
            } else {
                setResult(0);
            }
            finish();
        }
    }

    /* access modifiers changed from: private */
    public void i() {
        try {
            Message.obtain(this.h, R.id.restart_preview).sendToTarget();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public final dty a() {
        return this.i;
    }

    public final Handler c() {
        return this.h;
    }

    public final void d() {
        this.i.drawViewfinder();
    }

    public final void a(Intent intent) {
        setResult(-1, intent);
        finish();
    }

    public final void b(Intent intent) {
        startActivity(intent);
    }

    public final dtt e() {
        return this.g;
    }

    private void a(File file) {
        Uri uri;
        try {
            if (f()) {
                uri = FileProvider.getUriForFile(AMapPageUtil.getAppContext(), FileUtil.FILE_PROVIDER, file);
            } else {
                uri = Uri.fromFile(file);
            }
            startActivityForResult(a(uri), 12322);
        } catch (Exception unused) {
            ToastHelper.showLongToast(getResources().getString(R.string.publish_fail));
        }
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        SurfaceHolder holder = ((SurfaceView) findViewById(R.id.preview_view)).getHolder();
        if (holder != null) {
            holder.removeCallback(this);
        }
        if (this.i != null) {
            this.i.cleanUp();
        }
        super.onDestroy();
    }

    static /* synthetic */ void a(QRCodeScanActivity qRCodeScanActivity, int i2) {
        if (qRCodeScanActivity.x != null) {
            LayoutParams layoutParams = (LayoutParams) qRCodeScanActivity.x.getLayoutParams();
            layoutParams.setMargins(0, i2, 0, 0);
            qRCodeScanActivity.x.setLayoutParams(layoutParams);
            qRCodeScanActivity.x.setVisibility(0);
        }
    }
}
