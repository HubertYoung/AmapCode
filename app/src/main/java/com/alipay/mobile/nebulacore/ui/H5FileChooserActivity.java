package com.alipay.mobile.nebulacore.ui;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.LocalBroadcastManager;
import com.alipay.android.phone.androidannotations.utils.PermissionUtils;
import com.alipay.mobile.nebula.R;
import com.alipay.mobile.nebula.activity.H5BaseActivity;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebulacore.env.H5Environment;
import com.alipay.mobile.nebulacore.view.H5Toast;
import com.alipay.mobile.nebulacore.web.H5WebChromeClient;
import com.alipay.mobile.tinyappcommon.utils.H5TinyAppLogUtil;
import java.io.File;

public class H5FileChooserActivity extends H5BaseActivity {
    public static final String TAG = "H5FileChooserActivity";
    private String a;
    private final String[] b = {"android.permission.CAMERA"};
    private String c = null;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        if (VERSION.SDK_INT != 26) {
            setRequestedOrientation(1);
        }
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        a();
        Intent intent = getIntent();
        if (intent != null) {
            this.c = intent.getStringExtra("appId");
        }
    }

    private void a() {
        if (PermissionUtils.hasSelfPermissions(this, this.b)) {
            H5Log.d(TAG, "get CAMERA permission success!");
            try {
                startActivityForResult(a(this), 1);
            } catch (ActivityNotFoundException e) {
                H5Log.e(TAG, "exception detail", e);
            }
        } else {
            PermissionUtils.requestPermissions((Activity) this, this.b, 1);
        }
    }

    private void b() {
        H5Toast.showToast(this, H5Environment.getResources().getString(R.string.h5_choose_camera), 300);
        Intent callbackIntent = new Intent();
        callbackIntent.setAction(H5WebChromeClient.FILE_CHOOSER_RESULT);
        callbackIntent.putExtra("fileUri", Uri.parse(""));
        LocalBroadcastManager.getInstance(H5Environment.getContext()).sendBroadcastSync(callbackIntent);
        finish();
    }

    private Intent a(Context ctx) {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.addCategory("android.intent.category.OPENABLE");
        intent.setType("*/*");
        Intent chooser = a(ctx, c(), d(), e());
        chooser.putExtra("android.intent.extra.INTENT", intent);
        return chooser;
    }

    private static Intent a(Context ctx, Intent... intents) {
        Intent chooser = new Intent("android.intent.action.CHOOSER");
        chooser.putExtra("android.intent.extra.INITIAL_INTENTS", intents);
        chooser.putExtra("android.intent.extra.TITLE", ctx.getString(R.string.h5_file_chooser));
        return chooser;
    }

    private Intent c() {
        Intent cameraIntent = new Intent("android.media.action.IMAGE_CAPTURE");
        File cameraDataDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath() + File.separator + "browser-photos");
        cameraDataDir.mkdirs();
        this.a = cameraDataDir.getAbsolutePath() + File.separator + System.currentTimeMillis() + ".jpg";
        cameraIntent.putExtra(H5TinyAppLogUtil.TINY_APP_STANDARD_OUTPUT, Uri.fromFile(new File(this.a)));
        return cameraIntent;
    }

    private static Intent d() {
        return new Intent("android.media.action.VIDEO_CAPTURE");
    }

    private static Intent e() {
        return new Intent("android.provider.MediaStore.RECORD_SOUND");
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        H5Log.d(TAG, "EditAvatarModActivity onDestroy");
        super.onDestroy();
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Code restructure failed: missing block: B:10:0x002f, code lost:
        if (r2.exists() != false) goto L_0x0031;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onActivityResult(int r11, int r12, android.content.Intent r13) {
        /*
            r10 = this;
            r4 = 1
            r9 = -1
            if (r11 != r4) goto L_0x008e
            java.lang.String r6 = "H5FileChooserActivity"
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            java.lang.String r8 = "onActivityResult intent "
            r7.<init>(r8)
            java.lang.StringBuilder r7 = r7.append(r13)
            java.lang.String r7 = r7.toString()
            com.alipay.mobile.nebula.util.H5Log.d(r6, r7)
            if (r13 == 0) goto L_0x001c
            if (r12 == r9) goto L_0x0099
        L_0x001c:
            r5 = 0
        L_0x001d:
            if (r12 != r9) goto L_0x0043
            r2 = 0
            java.lang.String r6 = r10.a
            if (r6 == 0) goto L_0x009e
            java.io.File r2 = new java.io.File
            java.lang.String r6 = r10.a
            r2.<init>(r6)
            boolean r6 = r2.exists()
            if (r6 == 0) goto L_0x009e
        L_0x0031:
            if (r5 != 0) goto L_0x0043
            if (r4 == 0) goto L_0x0043
            android.net.Uri r5 = android.net.Uri.fromFile(r2)
            android.content.Intent r6 = new android.content.Intent
            java.lang.String r7 = "android.intent.action.MEDIA_SCANNER_SCAN_FILE"
            r6.<init>(r7, r5)
            r10.sendBroadcast(r6)
        L_0x0043:
            if (r5 != 0) goto L_0x004c
            java.lang.String r6 = "H5FileChooserActivity"
            java.lang.String r7 = "result uri is null"
            com.alipay.mobile.nebula.util.H5Log.e(r6, r7)
        L_0x004c:
            android.content.Intent r1 = new android.content.Intent
            r1.<init>()
            java.lang.String r6 = "FILE_CHOOSER_RESULT"
            r1.setAction(r6)
            java.lang.String r6 = "fileUri"
            r1.putExtra(r6, r5)
            android.content.Context r6 = com.alipay.mobile.nebulacore.env.H5Environment.getContext()
            android.support.v4.content.LocalBroadcastManager r6 = android.support.v4.content.LocalBroadcastManager.getInstance(r6)
            r6.sendBroadcastSync(r1)
            java.lang.Class<com.alipay.mobile.h5container.service.H5EventHandlerService> r6 = com.alipay.mobile.h5container.service.H5EventHandlerService.class
            java.lang.String r6 = r6.getName()
            java.lang.Object r3 = com.alipay.mobile.nebula.util.H5Utils.findServiceByInterface(r6)
            com.alipay.mobile.h5container.service.H5EventHandlerService r3 = (com.alipay.mobile.h5container.service.H5EventHandlerService) r3
            if (r3 == 0) goto L_0x008e
            java.lang.String r6 = r10.c
            boolean r6 = android.text.TextUtils.isEmpty(r6)
            if (r6 != 0) goto L_0x008e
            android.os.Bundle r0 = new android.os.Bundle
            r0.<init>()
            java.lang.String r6 = "fileUri"
            r0.putParcelable(r6, r5)
            java.lang.String r6 = r10.c
            r7 = 200001964(0xbebc9ac, float:9.082215E-32)
            r3.sendDataToTinyProcessWithMsgType(r6, r0, r7)
        L_0x008e:
            java.lang.String r6 = "H5FileChooserActivity"
            java.lang.String r7 = "onActivityResult finish"
            com.alipay.mobile.nebula.util.H5Log.d(r6, r7)
            r10.finish()
            return
        L_0x0099:
            android.net.Uri r5 = r13.getData()
            goto L_0x001d
        L_0x009e:
            r4 = 0
            goto L_0x0031
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.nebulacore.ui.H5FileChooserActivity.onActivityResult(int, int, android.content.Intent):void");
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (!PermissionUtils.verifyPermissions(grantResults)) {
                    b();
                    break;
                } else {
                    H5Log.d(TAG, "get CAMERA permission success!");
                    try {
                        startActivityForResult(a(this), 1);
                        break;
                    } catch (ActivityNotFoundException e) {
                        H5Log.e(TAG, "exception detail", e);
                        break;
                    }
                }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
