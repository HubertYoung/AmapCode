package com.autonavi.carowner.payfor;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.location.Location;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.aosservice.response.AosResponseCallbackOnUi;
import com.amap.bundle.aosservice.response.AosResponseException;
import com.amap.bundle.datamodel.poi.POIFactory;
import com.amap.bundle.drivecommon.mvp.view.DriveBasePage;
import com.amap.bundle.drivecommon.payfor.PayforNaviData;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.map.fragmentcontainer.page.IPresenter;
import com.autonavi.map.widget.ProgressDlg;
import com.autonavi.minimap.R;
import com.autonavi.minimap.search.model.SelectPoiFromMapBean;
import com.autonavi.minimap.usepay.UsePayRequestHolder;
import com.autonavi.minimap.usepay.param.UploadRequest;
import com.autonavi.sdk.location.LocationInstrument;
import com.autonavi.widget.ui.TitleBar;
import java.io.File;
import java.util.regex.Pattern;

public class ApplyPayForLocationErrorFragment extends DriveBasePage<bhj> {
    public static final String a;
    public static final String b;
    public EditText c;
    public TextView d;
    public TextView e;
    public ForegroundColorSpan f;
    public EditText g;
    public TitleBar h;
    public View i;
    public TextView j;
    public PayforNaviData k;
    boolean l = true;
    public ErrorType m;
    public POI n;
    CharSequence o;
    public TextWatcher p = new TextWatcher() {
        public final void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        public final void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            ApplyPayForLocationErrorFragment.this.o = ApplyPayForLocationErrorFragment.this.c.getText();
            if (ApplyPayForLocationErrorFragment.this.d != null) {
                ApplyPayForLocationErrorFragment.this.d.setText(String.format(ApplyPayForLocationErrorFragment.this.getString(R.string.describe_word_limit), new Object[]{Integer.valueOf(300 - ApplyPayForLocationErrorFragment.this.o.length())}));
                if (ApplyPayForLocationErrorFragment.this.o.length() == 300) {
                    ApplyPayForLocationErrorFragment.this.d.setTextColor(ApplyPayForLocationErrorFragment.this.getResources().getColor(R.color.color_star));
                    return;
                }
                ApplyPayForLocationErrorFragment.this.d.setTextColor(ApplyPayForLocationErrorFragment.this.getResources().getColor(R.color.f_c_3));
            }
        }

        public final void afterTextChanged(Editable editable) {
            boolean z = ApplyPayForLocationErrorFragment.this.m != ErrorType.KNOW_LOCATION || (ApplyPayForLocationErrorFragment.this.m == ErrorType.KNOW_LOCATION && ApplyPayForLocationErrorFragment.this.j.getVisibility() == 0);
            if (TextUtils.isEmpty(ApplyPayForLocationErrorFragment.this.g.getText()) || TextUtils.isEmpty(ApplyPayForLocationErrorFragment.this.c.getText()) || ApplyPayForLocationErrorFragment.this.c.getText().length() < 5 || !z) {
                ApplyPayForLocationErrorFragment.this.h.setActionTextEnable(false);
            } else {
                ApplyPayForLocationErrorFragment.this.h.setActionTextEnable(true);
            }
        }
    };
    public final OnClickListener q = new OnClickListener() {
        public final void onClick(View view) {
            if (view.getId() == R.id.btnFetchPoint) {
                ApplyPayForLocationErrorFragment.this.a();
                POI createPOI = POIFactory.createPOI(ApplyPayForLocationErrorFragment.this.k.toAddress, new GeoPoint(ApplyPayForLocationErrorFragment.this.k.endX, ApplyPayForLocationErrorFragment.this.k.endY));
                ApplyPayForLocationErrorFragment applyPayForLocationErrorFragment = ApplyPayForLocationErrorFragment.this;
                PageBundle pageBundle = new PageBundle("plugin.locationselect.SelectFixPoiFromMapFragment", "com.autonavi.minimap");
                SelectPoiFromMapBean selectPoiFromMapBean = new SelectPoiFromMapBean();
                selectPoiFromMapBean.setOldPOI(createPOI);
                if (applyPayForLocationErrorFragment.l) {
                    applyPayForLocationErrorFragment.l = false;
                    selectPoiFromMapBean.setLevel(18);
                }
                pageBundle.putObject("SelectPoiFromMapBean", selectPoiFromMapBean);
                applyPayForLocationErrorFragment.startPageForResult((String) "amap.basemap.action.base_select_fix_poi_from_map_page", pageBundle, 1);
            }
        }
    };
    private ProgressDlg r;

    public enum ErrorType {
        KNOW_LOCATION,
        UNKNOWN_LOCATION
    }

    static {
        StringBuilder sb = new StringBuilder();
        sb.append(ApplyPayForLocationErrorFragment.class.getName());
        sb.append(".PayforNaviData");
        a = sb.toString();
        StringBuilder sb2 = new StringBuilder();
        sb2.append(ApplyPayForLocationErrorFragment.class.getName());
        sb2.append(".ErrorType");
        b = sb2.toString();
    }

    public void onCreate(Context context) {
        super.onCreate(context);
        setContentView(R.layout.activities_apply_pay_for_location_error_fragment);
    }

    public final void a() {
        if (this.g != null) {
            ((InputMethodManager) getContext().getSystemService("input_method")).hideSoftInputFromWindow(this.g.getWindowToken(), 2);
        }
    }

    private static boolean a(String str) {
        try {
            return Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0-9])|(17[0-9]))\\d{8}$").matcher(str).matches();
        } catch (Exception unused) {
            return false;
        }
    }

    public /* synthetic */ IPresenter createPresenter() {
        return new bhj(this);
    }

    static /* synthetic */ void f(ApplyPayForLocationErrorFragment applyPayForLocationErrorFragment) {
        if (TextUtils.isEmpty(applyPayForLocationErrorFragment.c.getText())) {
            ToastHelper.showLongToast(applyPayForLocationErrorFragment.getString(R.string.oper_must_not_empty));
            return;
        }
        if (applyPayForLocationErrorFragment.g != null) {
            String obj = applyPayForLocationErrorFragment.g.getText().toString();
            if (TextUtils.isEmpty(obj)) {
                ToastHelper.showLongToast(applyPayForLocationErrorFragment.getString(R.string.oper_input_phone));
                return;
            } else if (!a(obj)) {
                ToastHelper.showLongToast(applyPayForLocationErrorFragment.getString(R.string.oper_input_valid_phone));
                return;
            }
        }
        Location latestLocation = LocationInstrument.getInstance().getLatestLocation();
        final UploadRequest uploadRequest = new UploadRequest();
        uploadRequest.v = String.valueOf(latestLocation.getLongitude());
        uploadRequest.w = String.valueOf(latestLocation.getLatitude());
        uploadRequest.s = applyPayForLocationErrorFragment.g.getText().toString();
        uploadRequest.x = String.valueOf((int) latestLocation.getAccuracy());
        uploadRequest.y = new File("a123");
        if (applyPayForLocationErrorFragment.m == ErrorType.UNKNOWN_LOCATION) {
            uploadRequest.i = "3";
        } else {
            uploadRequest.i = "2";
        }
        uploadRequest.r = applyPayForLocationErrorFragment.c.getText().toString();
        if (applyPayForLocationErrorFragment.k != null) {
            uploadRequest.g = applyPayForLocationErrorFragment.k.poiid;
            uploadRequest.j = applyPayForLocationErrorFragment.k.fromAddress;
            uploadRequest.k = applyPayForLocationErrorFragment.k.toAddress;
            uploadRequest.l = String.valueOf(applyPayForLocationErrorFragment.k.endX);
            uploadRequest.m = String.valueOf(applyPayForLocationErrorFragment.k.endY);
            uploadRequest.n = String.valueOf(applyPayForLocationErrorFragment.k.distance);
            uploadRequest.o = String.valueOf(applyPayForLocationErrorFragment.k.timeUsed);
            uploadRequest.p = String.valueOf(applyPayForLocationErrorFragment.k.averageSpeed);
            uploadRequest.q = applyPayForLocationErrorFragment.k.naviTime;
        }
        if (applyPayForLocationErrorFragment.n != null) {
            uploadRequest.t = String.valueOf(applyPayForLocationErrorFragment.n.getPoint().getLongitude());
            uploadRequest.u = String.valueOf(applyPayForLocationErrorFragment.n.getPoint().getLatitude());
        } else {
            uploadRequest.t = String.valueOf(latestLocation.getLongitude());
            uploadRequest.u = String.valueOf(latestLocation.getLatitude());
        }
        UsePayRequestHolder.getInstance().sendUpload(uploadRequest, new AosResponseCallbackOnUi<AosByteResponse>() {
            /* JADX WARNING: Removed duplicated region for block: B:11:0x0028 A[RETURN] */
            /* JADX WARNING: Removed duplicated region for block: B:12:0x0029  */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public /* synthetic */ void onSuccess(com.amap.bundle.aosservice.response.AosResponse r5) {
                /*
                    r4 = this;
                    com.amap.bundle.aosservice.response.AosByteResponse r5 = (com.amap.bundle.aosservice.response.AosByteResponse) r5
                    com.autonavi.carowner.payfor.ApplyPayForLocationErrorFragment r0 = com.autonavi.carowner.payfor.ApplyPayForLocationErrorFragment.this
                    com.autonavi.carowner.payfor.ApplyPayForLocationErrorFragment.h(r0)
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
                    com.autonavi.carowner.payfor.ApplyPayForLocationErrorFragment r5 = com.autonavi.carowner.payfor.ApplyPayForLocationErrorFragment.this
                    int r0 = com.autonavi.minimap.R.string.oper_info_err
                    java.lang.String r5 = r5.getString(r0)
                    com.amap.bundle.utils.ui.ToastHelper.showLongToast(r5)
                    return
                L_0x003f:
                    r2 = 100
                    if (r1 != r2) goto L_0x004f
                    com.autonavi.carowner.payfor.ApplyPayForLocationErrorFragment r5 = com.autonavi.carowner.payfor.ApplyPayForLocationErrorFragment.this
                    int r0 = com.autonavi.minimap.R.string.oper_request_err
                    java.lang.String r5 = r5.getString(r0)
                    com.amap.bundle.utils.ui.ToastHelper.showLongToast(r5)
                    return
                L_0x004f:
                    r2 = 103(0x67, float:1.44E-43)
                    if (r1 != r2) goto L_0x005f
                    com.autonavi.carowner.payfor.ApplyPayForLocationErrorFragment r5 = com.autonavi.carowner.payfor.ApplyPayForLocationErrorFragment.this
                    int r0 = com.autonavi.minimap.R.string.oper_activity_finish
                    java.lang.String r5 = r5.getString(r0)
                    com.amap.bundle.utils.ui.ToastHelper.showLongToast(r5)
                    return
                L_0x005f:
                    r2 = 101(0x65, float:1.42E-43)
                    if (r1 != r2) goto L_0x0073
                    com.autonavi.carowner.payfor.ApplyPayForLocationErrorFragment r5 = com.autonavi.carowner.payfor.ApplyPayForLocationErrorFragment.this
                    android.content.Context r5 = r5.getContext()
                    int r0 = com.autonavi.minimap.R.string.activities_cannot_apply_payfor
                    java.lang.String r5 = r5.getString(r0)
                    com.amap.bundle.utils.ui.ToastHelper.showLongToast(r5)
                    return
                L_0x0073:
                    r2 = 104(0x68, float:1.46E-43)
                    if (r1 != r2) goto L_0x0083
                    com.autonavi.carowner.payfor.ApplyPayForLocationErrorFragment r5 = com.autonavi.carowner.payfor.ApplyPayForLocationErrorFragment.this
                    int r0 = com.autonavi.minimap.R.string.oper_upload_img_err
                    java.lang.String r5 = r5.getString(r0)
                    com.amap.bundle.utils.ui.ToastHelper.showLongToast(r5)
                    return
                L_0x0083:
                    r2 = 1
                    if (r1 == r2) goto L_0x008c
                    com.autonavi.carowner.payfor.ApplyPayForLocationErrorFragment r5 = com.autonavi.carowner.payfor.ApplyPayForLocationErrorFragment.this
                    com.amap.bundle.utils.ui.ToastHelper.showLongToast(r5.getContext().getString(com.autonavi.minimap.R.string.ic_net_error_tipinfo))
                    return
                L_0x008c:
                    com.autonavi.carowner.payfor.ApplyPayForLocationErrorFragment r1 = com.autonavi.carowner.payfor.ApplyPayForLocationErrorFragment.this
                    int r1 = r1.getRequestCode()
                    r3 = 10001(0x2711, float:1.4014E-41)
                    if (r1 == r3) goto L_0x009e
                    com.autonavi.carowner.payfor.ApplyPayForLocationErrorFragment r1 = com.autonavi.carowner.payfor.ApplyPayForLocationErrorFragment.this
                    int r1 = r1.getRequestCode()
                    if (r2 != r1) goto L_0x00a5
                L_0x009e:
                    com.autonavi.carowner.payfor.ApplyPayForLocationErrorFragment r1 = com.autonavi.carowner.payfor.ApplyPayForLocationErrorFragment.this
                    com.autonavi.common.Page$ResultType r2 = com.autonavi.common.Page.ResultType.OK
                    r1.setResult(r2, r0)
                L_0x00a5:
                    com.autonavi.carowner.payfor.ApplyPayForLocationErrorFragment r0 = com.autonavi.carowner.payfor.ApplyPayForLocationErrorFragment.this
                    com.amap.bundle.drivecommon.payfor.PayforNaviData r0 = r0.k
                    if (r0 == 0) goto L_0x00da
                    com.autonavi.carowner.payfor.ApplyPayForLocationErrorFragment r0 = com.autonavi.carowner.payfor.ApplyPayForLocationErrorFragment.this
                    com.amap.bundle.drivecommon.payfor.PayforNaviData r0 = r0.k
                    java.lang.String r1 = "record_id"
                    java.lang.String r2 = ""
                    java.lang.String r1 = r5.optString(r1, r2)
                    r0.recordId = r1
                    com.autonavi.carowner.payfor.ApplyPayForLocationErrorFragment r0 = com.autonavi.carowner.payfor.ApplyPayForLocationErrorFragment.this
                    com.amap.bundle.drivecommon.payfor.PayforNaviData r0 = r0.k
                    java.lang.String r1 = "done_days"
                    int r5 = r5.optInt(r1)
                    r0.doneDays = r5
                    android.os.Handler r5 = new android.os.Handler
                    r5.<init>()
                    com.autonavi.carowner.payfor.ApplyPayForLocationErrorFragment$5$1 r0 = new com.autonavi.carowner.payfor.ApplyPayForLocationErrorFragment$5$1
                    r0.<init>()
                    r1 = 1000(0x3e8, double:4.94E-321)
                    r5.postDelayed(r0, r1)
                L_0x00da:
                    return
                */
                throw new UnsupportedOperationException("Method not decompiled: com.autonavi.carowner.payfor.ApplyPayForLocationErrorFragment.AnonymousClass5.onSuccess(com.amap.bundle.aosservice.response.AosResponse):void");
            }

            public void onFailure(AosRequest aosRequest, AosResponseException aosResponseException) {
                ApplyPayForLocationErrorFragment.h(ApplyPayForLocationErrorFragment.this);
                ToastHelper.showLongToast(ApplyPayForLocationErrorFragment.this.getContext().getString(R.string.ic_net_error_tipinfo));
                aosResponseException.printStackTrace();
            }
        });
        String string = applyPayForLocationErrorFragment.getString(R.string.oper_commiting);
        applyPayForLocationErrorFragment.r = new ProgressDlg(applyPayForLocationErrorFragment.getActivity());
        applyPayForLocationErrorFragment.r.setMessage(string);
        applyPayForLocationErrorFragment.r.setOnCancelListener(new OnCancelListener() {
            public final void onCancel(DialogInterface dialogInterface) {
                if (uploadRequest != null) {
                    uploadRequest.cancel();
                }
            }
        });
        applyPayForLocationErrorFragment.r.show();
    }

    static /* synthetic */ void h(ApplyPayForLocationErrorFragment applyPayForLocationErrorFragment) {
        if (applyPayForLocationErrorFragment.r != null) {
            applyPayForLocationErrorFragment.r.dismiss();
            applyPayForLocationErrorFragment.r = null;
        }
    }
}
