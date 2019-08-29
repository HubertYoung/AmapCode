package com.autonavi.carowner.payfor;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.graphics.Bitmap;
import android.location.Location;
import android.os.Environment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.alipay.mobile.antui.clickspan.BaseClickableSpan;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.aosservice.response.AosResponseCallbackOnUi;
import com.amap.bundle.aosservice.response.AosResponseException;
import com.amap.bundle.blutils.zip.IBitmapCompressedListener;
import com.amap.bundle.drivecommon.mvp.view.DriveBasePage;
import com.amap.bundle.drivecommon.payfor.PayforNaviData;
import com.amap.bundle.utils.device.KeyboardUtil;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.IPresenter;
import com.autonavi.map.widget.ProgressDlg;
import com.autonavi.minimap.R;
import com.autonavi.minimap.bundle.featureguide.widget.SplashyFragment;
import com.autonavi.minimap.photograph.api.IOpenPage;
import com.autonavi.minimap.photograph.api.IOpenPage.PhotoSelectOptions;
import com.autonavi.minimap.usepay.UsePayRequestHolder;
import com.autonavi.minimap.usepay.param.UploadRequest;
import com.autonavi.sdk.location.LocationInstrument;
import java.io.File;
import java.util.regex.Pattern;

public class ApplyPayForFragment extends DriveBasePage<bhk> implements TextWatcher, OnClickListener {
    public b a = null;
    public PayforNaviData b;
    public String c = null;
    public ForegroundColorSpan d;
    public final OnClickListener e = new OnClickListener() {
        public final void onClick(View view) {
            if (ApplyPayForFragment.this.a.b == view) {
                ApplyPayForFragment.this.b();
                ApplyPayForFragment.b(ApplyPayForFragment.this);
                return;
            }
            if (ApplyPayForFragment.this.a.e == view) {
                ApplyPayForFragment.this.b();
                kj.a(ApplyPayForFragment.this.getActivity(), new String[]{"android.permission.CAMERA"}, (defpackage.kj.b) new defpackage.kj.b() {
                    public final void run() {
                        dti dti = (dti) defpackage.esb.a.a.a(dti.class);
                        if (dti != null) {
                            IOpenPage a2 = dti.a();
                            if (a2 != null) {
                                com.autonavi.minimap.photograph.api.IOpenPage.a aVar = new com.autonavi.minimap.photograph.api.IOpenPage.a();
                                aVar.a = "";
                                aVar.b = Boolean.TRUE;
                                aVar.c = Integer.valueOf(BaseClickableSpan.CLICK_ENABLE_TIME);
                                aVar.d = PhotoSelectOptions.TAKE_PHOTO_BY_CAMERA;
                                a2.a(ApplyPayForFragment.this, aVar);
                            }
                        }
                    }
                });
            }
        }
    };
    private File f = null;
    private ProgressDlg g;

    static class a extends Thread {
        IBitmapCompressedListener a = null;
        private String b = null;
        private String c = null;

        public a(String str, String str2) {
            this.b = str;
            this.c = str2;
        }

        /* JADX WARNING: Removed duplicated region for block: B:29:0x0058 A[SYNTHETIC, Splitter:B:29:0x0058] */
        /* JADX WARNING: Removed duplicated region for block: B:38:0x0067 A[SYNTHETIC, Splitter:B:38:0x0067] */
        /* JADX WARNING: Removed duplicated region for block: B:45:0x0074 A[Catch:{ Exception -> 0x0090 }] */
        /* JADX WARNING: Removed duplicated region for block: B:48:0x0084 A[SYNTHETIC, Splitter:B:48:0x0084] */
        /* JADX WARNING: Unknown top exception splitter block from list: {B:35:0x0062=Splitter:B:35:0x0062, B:26:0x0053=Splitter:B:26:0x0053} */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final void run() {
            /*
                r5 = this;
                java.lang.String r0 = r5.b     // Catch:{ Exception -> 0x0090 }
                r1 = 500(0x1f4, float:7.0E-43)
                android.graphics.Bitmap r0 = defpackage.kp.a(r0, r1, r1)     // Catch:{ Exception -> 0x0090 }
                if (r0 != 0) goto L_0x000b
                return
            L_0x000b:
                java.io.ByteArrayOutputStream r1 = new java.io.ByteArrayOutputStream     // Catch:{ Exception -> 0x0090 }
                r1.<init>()     // Catch:{ Exception -> 0x0090 }
                android.graphics.Bitmap$CompressFormat r2 = android.graphics.Bitmap.CompressFormat.JPEG     // Catch:{ Exception -> 0x0090 }
                r3 = 50
                r0.compress(r2, r3, r1)     // Catch:{ Exception -> 0x0090 }
                byte[] r1 = r1.toByteArray()     // Catch:{ Exception -> 0x0090 }
                java.io.File r2 = new java.io.File     // Catch:{ Exception -> 0x0090 }
                java.lang.String r3 = r5.c     // Catch:{ Exception -> 0x0090 }
                r2.<init>(r3)     // Catch:{ Exception -> 0x0090 }
                boolean r3 = r2.exists()     // Catch:{ Exception -> 0x0090 }
                if (r3 != 0) goto L_0x002f
                java.io.File r3 = r2.getParentFile()     // Catch:{ Exception -> 0x0090 }
                r3.mkdirs()     // Catch:{ Exception -> 0x0090 }
            L_0x002f:
                r3 = 0
                java.io.FileOutputStream r4 = new java.io.FileOutputStream     // Catch:{ FileNotFoundException -> 0x0061, IOException -> 0x0052 }
                r4.<init>(r2)     // Catch:{ FileNotFoundException -> 0x0061, IOException -> 0x0052 }
                r4.write(r1)     // Catch:{ FileNotFoundException -> 0x004d, IOException -> 0x004a, all -> 0x0047 }
                r4.flush()     // Catch:{ FileNotFoundException -> 0x004d, IOException -> 0x004a, all -> 0x0047 }
                r4.close()     // Catch:{ IOException -> 0x003f }
                goto L_0x0043
            L_0x003f:
                r1 = move-exception
                defpackage.kf.a(r1)     // Catch:{ Exception -> 0x0090 }
            L_0x0043:
                r0.recycle()     // Catch:{ Exception -> 0x0090 }
                goto L_0x0070
            L_0x0047:
                r1 = move-exception
                r3 = r4
                goto L_0x0082
            L_0x004a:
                r1 = move-exception
                r3 = r4
                goto L_0x0053
            L_0x004d:
                r1 = move-exception
                r3 = r4
                goto L_0x0062
            L_0x0050:
                r1 = move-exception
                goto L_0x0082
            L_0x0052:
                r1 = move-exception
            L_0x0053:
                defpackage.kf.a(r1)     // Catch:{ all -> 0x0050 }
                if (r3 == 0) goto L_0x0043
                r3.close()     // Catch:{ IOException -> 0x005c }
                goto L_0x0043
            L_0x005c:
                r1 = move-exception
                defpackage.kf.a(r1)     // Catch:{ Exception -> 0x0090 }
                goto L_0x0043
            L_0x0061:
                r1 = move-exception
            L_0x0062:
                defpackage.kf.a(r1)     // Catch:{ all -> 0x0050 }
                if (r3 == 0) goto L_0x0043
                r3.close()     // Catch:{ IOException -> 0x006b }
                goto L_0x0043
            L_0x006b:
                r1 = move-exception
                defpackage.kf.a(r1)     // Catch:{ Exception -> 0x0090 }
                goto L_0x0043
            L_0x0070:
                com.amap.bundle.blutils.zip.IBitmapCompressedListener r0 = r5.a     // Catch:{ Exception -> 0x0090 }
                if (r0 == 0) goto L_0x0081
                java.lang.String r0 = r5.c     // Catch:{ Exception -> 0x0090 }
                android.graphics.Bitmap r0 = android.graphics.BitmapFactory.decodeFile(r0)     // Catch:{ Exception -> 0x0090 }
                com.amap.bundle.blutils.zip.IBitmapCompressedListener r1 = r5.a     // Catch:{ Exception -> 0x0090 }
                java.lang.String r2 = r5.b     // Catch:{ Exception -> 0x0090 }
                r1.onCompress(r0, r2)     // Catch:{ Exception -> 0x0090 }
            L_0x0081:
                return
            L_0x0082:
                if (r3 == 0) goto L_0x008c
                r3.close()     // Catch:{ IOException -> 0x0088 }
                goto L_0x008c
            L_0x0088:
                r2 = move-exception
                defpackage.kf.a(r2)     // Catch:{ Exception -> 0x0090 }
            L_0x008c:
                r0.recycle()     // Catch:{ Exception -> 0x0090 }
                throw r1     // Catch:{ Exception -> 0x0090 }
            L_0x0090:
                r0 = move-exception
                defpackage.kf.a(r0)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.autonavi.carowner.payfor.ApplyPayForFragment.a.run():void");
        }
    }

    public static class b {
        public View a;
        public TextView b;
        public TextView c;
        public TextView d;
        public View e;
        public ImageView f;
        public EditText g;
        public TextView h;
        public TextView i;
        public EditText j;
        public TextView k;
        public TextView l;

        private b() {
        }

        /* synthetic */ b(byte b2) {
            this();
        }
    }

    public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
    }

    public void onCreate(Context context) {
        super.onCreate(context);
        this.a = new b(0);
        setContentView(R.layout.activities_apply_pay_for_dialog);
    }

    public void onClick(View view) {
        if (this.a.a == view) {
            finish();
        } else if (this.a.d == view) {
            b();
            startPage((String) "amap.basemap.action.examplepage", (PageBundle) null);
        } else if (this.a.f == view) {
            b();
            PageBundle pageBundle = new PageBundle();
            pageBundle.putString(SplashyFragment.INTENT_photoPath, this.c);
            startPage((String) "amap.basemap.action.lookoverpicture", pageBundle);
        } else if (this.a.g == view) {
            KeyboardUtil.showKeyboard(this.a.g);
        } else {
            if (this.a.j == view) {
                KeyboardUtil.showKeyboard(this.a.j);
            }
        }
    }

    public final boolean a() {
        return !TextUtils.isEmpty(this.a.j.getText()) && !TextUtils.isEmpty(this.a.g.getText()) && this.a.g.getText().length() >= 5;
    }

    public void afterTextChanged(Editable editable) {
        this.a.b.setEnabled(a());
    }

    public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        this.a.h.setText(String.format(getString(R.string.describe_word_limit), new Object[]{Integer.valueOf(300 - charSequence.toString().length())}));
        if (charSequence.toString().length() == 300) {
            this.a.h.setTextColor(getResources().getColor(R.color.color_star));
        } else {
            this.a.h.setTextColor(getResources().getColor(R.color.f_c_3));
        }
    }

    public final void b() {
        KeyboardUtil.forceHideKeyboard(this.a.g);
        KeyboardUtil.forceHideKeyboard(this.a.j);
    }

    /* access modifiers changed from: private */
    public void c() {
        Location latestLocation = LocationInstrument.getInstance().getLatestLocation();
        if (TextUtils.isEmpty(this.c)) {
            this.c = "a123";
        }
        final UploadRequest uploadRequest = new UploadRequest();
        uploadRequest.s = this.a.j.getEditableText().toString();
        uploadRequest.x = String.valueOf((int) latestLocation.getAccuracy());
        uploadRequest.i = "1";
        uploadRequest.v = String.valueOf(latestLocation.getLongitude());
        uploadRequest.w = String.valueOf(latestLocation.getLatitude());
        uploadRequest.y = new File(this.c);
        uploadRequest.r = this.a.g.getText().toString();
        if (this.b != null) {
            uploadRequest.l = String.valueOf(this.b.endX);
            uploadRequest.m = String.valueOf(this.b.endY);
            uploadRequest.g = this.b.poiid;
            uploadRequest.j = this.b.fromAddress;
            uploadRequest.k = this.b.toAddress;
            uploadRequest.n = String.valueOf(this.b.distance);
            uploadRequest.o = String.valueOf(this.b.timeUsed);
            uploadRequest.p = String.valueOf(this.b.averageSpeed);
            uploadRequest.q = this.b.naviTime;
        }
        UsePayRequestHolder.getInstance().sendUpload(uploadRequest, new AosResponseCallbackOnUi<AosByteResponse>() {
            /* JADX WARNING: Removed duplicated region for block: B:11:0x0028 A[RETURN] */
            /* JADX WARNING: Removed duplicated region for block: B:12:0x0029  */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public /* synthetic */ void onSuccess(com.amap.bundle.aosservice.response.AosResponse r5) {
                /*
                    r4 = this;
                    com.amap.bundle.aosservice.response.AosByteResponse r5 = (com.amap.bundle.aosservice.response.AosByteResponse) r5
                    com.autonavi.carowner.payfor.ApplyPayForFragment r0 = com.autonavi.carowner.payfor.ApplyPayForFragment.this
                    com.autonavi.carowner.payfor.ApplyPayForFragment.e(r0)
                    r0 = 0
                    if (r5 == 0) goto L_0x0025
                    java.lang.Object r5 = r5.getResult()     // Catch:{ Exception -> 0x0025 }
                    byte[] r5 = (byte[]) r5     // Catch:{ Exception -> 0x0025 }
                    if (r5 == 0) goto L_0x0025
                    java.lang.String r1 = new java.lang.String     // Catch:{ Exception -> 0x0025 }
                    java.lang.String r2 = "UTF-8"
                    r1.<init>(r5, r2)     // Catch:{ Exception -> 0x0025 }
                    boolean r5 = android.text.TextUtils.isEmpty(r1)     // Catch:{ Exception -> 0x0025 }
                    if (r5 != 0) goto L_0x0025
                    org.json.JSONObject r5 = new org.json.JSONObject     // Catch:{ Exception -> 0x0025 }
                    r5.<init>(r1)     // Catch:{ Exception -> 0x0025 }
                    goto L_0x0026
                L_0x0025:
                    r5 = r0
                L_0x0026:
                    if (r5 != 0) goto L_0x0029
                    return
                L_0x0029:
                    java.lang.String r1 = "code"
                    int r1 = r5.optInt(r1)
                    r2 = 102(0x66, float:1.43E-43)
                    if (r1 != r2) goto L_0x003f
                    com.autonavi.carowner.payfor.ApplyPayForFragment r5 = com.autonavi.carowner.payfor.ApplyPayForFragment.this
                    int r0 = com.autonavi.minimap.R.string.oper_info_err
                    java.lang.String r5 = r5.getString(r0)
                    com.amap.bundle.utils.ui.ToastHelper.showLongToast(r5)
                    return
                L_0x003f:
                    r2 = 100
                    if (r1 != r2) goto L_0x004f
                    com.autonavi.carowner.payfor.ApplyPayForFragment r5 = com.autonavi.carowner.payfor.ApplyPayForFragment.this
                    int r0 = com.autonavi.minimap.R.string.oper_request_err
                    java.lang.String r5 = r5.getString(r0)
                    com.amap.bundle.utils.ui.ToastHelper.showLongToast(r5)
                    return
                L_0x004f:
                    r2 = 103(0x67, float:1.44E-43)
                    if (r1 != r2) goto L_0x005f
                    com.autonavi.carowner.payfor.ApplyPayForFragment r5 = com.autonavi.carowner.payfor.ApplyPayForFragment.this
                    int r0 = com.autonavi.minimap.R.string.oper_activity_finish
                    java.lang.String r5 = r5.getString(r0)
                    com.amap.bundle.utils.ui.ToastHelper.showLongToast(r5)
                    return
                L_0x005f:
                    r2 = 101(0x65, float:1.42E-43)
                    if (r1 != r2) goto L_0x0073
                    com.autonavi.carowner.payfor.ApplyPayForFragment r5 = com.autonavi.carowner.payfor.ApplyPayForFragment.this
                    android.content.Context r5 = r5.getContext()
                    int r0 = com.autonavi.minimap.R.string.activities_cannot_apply_payfor
                    java.lang.String r5 = r5.getString(r0)
                    com.amap.bundle.utils.ui.ToastHelper.showLongToast(r5)
                    return
                L_0x0073:
                    r2 = 104(0x68, float:1.46E-43)
                    if (r1 != r2) goto L_0x0083
                    com.autonavi.carowner.payfor.ApplyPayForFragment r5 = com.autonavi.carowner.payfor.ApplyPayForFragment.this
                    int r0 = com.autonavi.minimap.R.string.oper_upload_img_err
                    java.lang.String r5 = r5.getString(r0)
                    com.amap.bundle.utils.ui.ToastHelper.showLongToast(r5)
                    return
                L_0x0083:
                    r2 = 1
                    if (r1 == r2) goto L_0x008c
                    com.autonavi.carowner.payfor.ApplyPayForFragment r5 = com.autonavi.carowner.payfor.ApplyPayForFragment.this
                    com.amap.bundle.utils.ui.ToastHelper.showLongToast(r5.getString(com.autonavi.minimap.R.string.ic_net_error_tipinfo))
                    return
                L_0x008c:
                    com.autonavi.carowner.payfor.ApplyPayForFragment r1 = com.autonavi.carowner.payfor.ApplyPayForFragment.this
                    int r1 = r1.getRequestCode()
                    r3 = 10001(0x2711, float:1.4014E-41)
                    if (r1 != r3) goto L_0x009d
                    com.autonavi.carowner.payfor.ApplyPayForFragment r1 = com.autonavi.carowner.payfor.ApplyPayForFragment.this
                    com.autonavi.common.Page$ResultType r3 = com.autonavi.common.Page.ResultType.OK
                    r1.setResult(r3, r0)
                L_0x009d:
                    com.autonavi.carowner.payfor.ApplyPayForFragment r0 = com.autonavi.carowner.payfor.ApplyPayForFragment.this
                    com.amap.bundle.drivecommon.payfor.PayforNaviData r0 = r0.b
                    if (r0 == 0) goto L_0x0135
                    com.autonavi.carowner.payfor.ApplyPayForFragment r0 = com.autonavi.carowner.payfor.ApplyPayForFragment.this
                    com.amap.bundle.drivecommon.payfor.PayforNaviData r0 = r0.b
                    java.lang.String r1 = "record_id"
                    java.lang.String r3 = ""
                    java.lang.String r1 = r5.optString(r1, r3)
                    r0.recordId = r1
                    com.autonavi.carowner.payfor.ApplyPayForFragment r0 = com.autonavi.carowner.payfor.ApplyPayForFragment.this
                    com.amap.bundle.drivecommon.payfor.PayforNaviData r0 = r0.b
                    java.lang.String r1 = "done_days"
                    int r5 = r5.optInt(r1)
                    r0.doneDays = r5
                    com.autonavi.carowner.payfor.ApplyPayForFragment r5 = com.autonavi.carowner.payfor.ApplyPayForFragment.this
                    com.amap.bundle.drivecommon.payfor.PayforNaviData r5 = r5.b
                    java.lang.String r5 = r5.recordId
                    boolean r5 = android.text.TextUtils.isEmpty(r5)
                    if (r5 != 0) goto L_0x0109
                    java.lang.String r5 = "null"
                    com.autonavi.carowner.payfor.ApplyPayForFragment r0 = com.autonavi.carowner.payfor.ApplyPayForFragment.this
                    com.amap.bundle.drivecommon.payfor.PayforNaviData r0 = r0.b
                    java.lang.String r0 = r0.recordId
                    boolean r5 = r5.equals(r0)
                    if (r5 == 0) goto L_0x00e2
                    goto L_0x0109
                L_0x00e2:
                    com.autonavi.carowner.payfor.ApplyPayForFragment r5 = com.autonavi.carowner.payfor.ApplyPayForFragment.this
                    com.amap.bundle.drivecommon.payfor.PayforNaviData r5 = r5.b
                    r5.save()
                    com.autonavi.common.PageBundle r5 = new com.autonavi.common.PageBundle
                    r5.<init>()
                    java.lang.String r0 = "naviData"
                    com.autonavi.carowner.payfor.ApplyPayForFragment r1 = com.autonavi.carowner.payfor.ApplyPayForFragment.this
                    com.amap.bundle.drivecommon.payfor.PayforNaviData r1 = r1.b
                    r5.putObject(r0, r1)
                    com.autonavi.carowner.payfor.ApplyPayForFragment r0 = com.autonavi.carowner.payfor.ApplyPayForFragment.this
                    java.lang.Class<com.autonavi.carowner.payfor.view.SubmitSuccessFragment> r1 = com.autonavi.carowner.payfor.view.SubmitSuccessFragment.class
                    com.autonavi.carowner.payfor.ApplyPayForFragment r2 = com.autonavi.carowner.payfor.ApplyPayForFragment.this
                    int r2 = r2.getRequestCode()
                    r0.startPageForResult(r1, r5, r2)
                    goto L_0x0135
                L_0x0109:
                    com.autonavi.carowner.payfor.ApplyPayForFragment r5 = com.autonavi.carowner.payfor.ApplyPayForFragment.this
                    com.amap.bundle.drivecommon.payfor.PayforNaviData r5 = r5.b
                    r5.delete()
                    com.autonavi.carowner.payfor.ApplyPayForFragment r5 = com.autonavi.carowner.payfor.ApplyPayForFragment.this
                    int r0 = com.autonavi.minimap.R.string.oper_commited
                    java.lang.String r5 = r5.getString(r0)
                    com.amap.bundle.utils.ui.ToastHelper.showLongToast(r5)
                    com.autonavi.common.PageBundle r5 = new com.autonavi.common.PageBundle
                    r5.<init>()
                    java.lang.String r0 = "payforNaviData"
                    com.autonavi.carowner.payfor.ApplyPayForFragment r1 = com.autonavi.carowner.payfor.ApplyPayForFragment.this
                    com.amap.bundle.drivecommon.payfor.PayforNaviData r1 = r1.b
                    r5.putObject(r0, r1)
                    com.autonavi.carowner.payfor.ApplyPayForFragment r0 = com.autonavi.carowner.payfor.ApplyPayForFragment.this
                    java.lang.Class<com.autonavi.carowner.payfor.ApplyPayForResultFragment> r1 = com.autonavi.carowner.payfor.ApplyPayForResultFragment.class
                    r0.startPageForResult(r1, r5, r2)
                    return
                L_0x0135:
                    return
                */
                throw new UnsupportedOperationException("Method not decompiled: com.autonavi.carowner.payfor.ApplyPayForFragment.AnonymousClass3.onSuccess(com.amap.bundle.aosservice.response.AosResponse):void");
            }

            public void onFailure(AosRequest aosRequest, AosResponseException aosResponseException) {
                ApplyPayForFragment.e(ApplyPayForFragment.this);
                ToastHelper.showLongToast(ApplyPayForFragment.this.getString(R.string.ic_net_error_tipinfo));
                aosResponseException.printStackTrace();
            }
        });
        String string = getString(R.string.oper_commiting);
        this.g = new ProgressDlg(getActivity());
        this.g.setMessage(string);
        this.g.setOnCancelListener(new OnCancelListener() {
            public final void onCancel(DialogInterface dialogInterface) {
                if (uploadRequest != null) {
                    uploadRequest.cancel();
                }
            }
        });
        this.g.show();
    }

    public /* synthetic */ IPresenter createPresenter() {
        return new bhk(this);
    }

    static /* synthetic */ void b(ApplyPayForFragment applyPayForFragment) {
        if (applyPayForFragment.a.j != null) {
            String obj = applyPayForFragment.a.j.getText().toString();
            if (TextUtils.isEmpty(obj)) {
                ToastHelper.showLongToast(applyPayForFragment.getString(R.string.oper_input_phone));
                return;
            } else if (!Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0-9])|(17[0-9]))\\d{8}$").matcher(obj).matches()) {
                ToastHelper.showLongToast(applyPayForFragment.getString(R.string.oper_input_valid_phone));
                return;
            }
        }
        StringBuilder sb = new StringBuilder();
        sb.append(Environment.getExternalStorageDirectory());
        sb.append("/autonavi/out.jpg");
        final String sb2 = sb.toString();
        if (!TextUtils.isEmpty(applyPayForFragment.c)) {
            a aVar = new a(applyPayForFragment.c, sb2);
            aVar.a = new IBitmapCompressedListener() {
                public final void onException(Exception exc) {
                }

                public final void onCompress(Bitmap bitmap, String str) {
                    ApplyPayForFragment.this.c = sb2;
                    ApplyPayForFragment.this.getActivity().runOnUiThread(new Runnable() {
                        public final void run() {
                            ApplyPayForFragment.this.c();
                        }
                    });
                }
            };
            aVar.start();
            return;
        }
        applyPayForFragment.c();
    }

    static /* synthetic */ void e(ApplyPayForFragment applyPayForFragment) {
        if (applyPayForFragment.g != null) {
            applyPayForFragment.g.dismiss();
            applyPayForFragment.g = null;
        }
    }
}
