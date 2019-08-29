package com.autonavi.carowner.roadcamera.page;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputFilter.LengthFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.aosservice.response.AosResponse;
import com.amap.bundle.aosservice.response.AosResponseCallbackOnUi;
import com.amap.bundle.aosservice.response.AosResponseException;
import com.amap.bundle.datamodel.poi.POIFactory;
import com.amap.bundle.drivecommon.map.db.helper.RdCameraCityInfoDbHelper;
import com.amap.bundle.drivecommon.map.db.helper.RdCameraDBHelper;
import com.amap.bundle.drivecommon.map.db.model.RdCameraCityInfo;
import com.amap.bundle.drivecommon.map.db.model.RdCameraPaymentItem;
import com.amap.bundle.drivecommon.mvp.view.DriveBasePage;
import com.amap.bundle.network.request.param.NetworkParam;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.bundle.account.api.IAccountService;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.map.fragmentcontainer.page.IPresenter;
import com.autonavi.minimap.R;
import com.autonavi.minimap.drive.widget.CarPlateInputView;
import com.autonavi.minimap.drive.widget.CarPlateInputView.StatusUpdate;
import com.autonavi.minimap.feedback.FeedbackRequestHolder;
import com.autonavi.minimap.feedback.param.UserPayReportRequest;
import com.autonavi.minimap.search.model.SelectPoiFromMapBean;
import com.autonavi.minimap.transfer.TransferRequestHolder;
import com.autonavi.minimap.transfer.param.ViolationSupportcityRequest;
import java.lang.ref.WeakReference;
import java.util.regex.Pattern;
import org.json.JSONObject;

public class RdCameraApplyFragment extends DriveBasePage<bht> implements StatusUpdate {
    /* access modifiers changed from: private */
    public boolean A = true;
    private final Handler B = new a(this, 0);
    public TextView a;
    public POI b;
    public EditText c;
    public TextView d;
    public CarPlateInputView e;
    public View f;
    public EditText g;
    public View h;
    public View i;
    public EditText j;
    public View k;
    public View l;
    public EditText m;
    public CheckBox n;
    public TextView o;
    public TextView p;
    public TextView q;
    public Button r;
    public RdCameraPaymentItem s;
    public bho t;
    public bhs u;
    public boolean v;
    public TextWatcher w = new TextWatcher() {
        public final void afterTextChanged(Editable editable) {
        }

        public final void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        public final void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            RdCameraApplyFragment.this.a(false);
        }
    };
    public InputFilter[] x = {new LengthFilter(30), new InputFilter() {
        public final CharSequence filter(CharSequence charSequence, int i, int i2, Spanned spanned, int i3, int i4) {
            boolean z;
            Pattern compile = Pattern.compile("[一-龥]");
            compile.matcher(charSequence.toString());
            StringBuffer stringBuffer = new StringBuffer();
            if (!TextUtils.isEmpty(charSequence.toString())) {
                z = false;
                for (int i5 = 0; i5 < charSequence.toString().length(); i5++) {
                    if (compile.matcher(String.valueOf(charSequence.toString().charAt(i5))).matches()) {
                        z |= true;
                    } else {
                        stringBuffer.append(charSequence.toString().charAt(i5));
                    }
                }
            } else {
                z = false;
            }
            return z ? stringBuffer.toString() : charSequence;
        }
    }};
    private String y;
    private boolean z;

    static class a extends Handler {
        private WeakReference<RdCameraApplyFragment> a;

        /* synthetic */ a(RdCameraApplyFragment rdCameraApplyFragment, byte b) {
            this(rdCameraApplyFragment);
        }

        private a(RdCameraApplyFragment rdCameraApplyFragment) {
            this.a = new WeakReference<>(rdCameraApplyFragment);
        }

        public final void handleMessage(Message message) {
            RdCameraApplyFragment rdCameraApplyFragment = (RdCameraApplyFragment) this.a.get();
            if (rdCameraApplyFragment != null && message.what == 300) {
                if (rdCameraApplyFragment.b(((Boolean) message.obj).booleanValue())) {
                    rdCameraApplyFragment.r.setEnabled(false);
                    rdCameraApplyFragment.r.setTextColor(rdCameraApplyFragment.getResources().getColor(R.color.font_cb_disable));
                    return;
                }
                rdCameraApplyFragment.r.setEnabled(true);
                rdCameraApplyFragment.r.setTextColor(rdCameraApplyFragment.getResources().getColor(R.color.search_btn_text_color));
            }
        }
    }

    public void carPlateChanged() {
    }

    public final void a(boolean z2) {
        Message message = new Message();
        message.what = 300;
        message.obj = Boolean.valueOf(z2);
        this.B.sendMessage(message);
    }

    public void onCreate(Context context) {
        super.onCreate(context);
        requestScreenOrientation(1);
        setContentView(R.layout.rd_camera_apply_fragment);
    }

    private void e() {
        this.j.setHint(R.string.rd_camera_apply_compensation_car_frame_all_words);
        this.g.setHint(R.string.rd_camera_apply_compensation_car_engine_all_words);
        this.f.setVisibility(0);
        this.i.setVisibility(0);
        g();
    }

    public void provinceNameUpdated() {
        a();
        a(false);
        if (this.z) {
            f();
        }
    }

    public void carPlateValid() {
        a();
        this.z = true;
        this.A = true;
        a(false);
        f();
    }

    public void carPlateInvalid() {
        a();
        this.z = false;
        a(false);
    }

    public void onKeyDoneClick(String str) {
        if (this.e != null && this.e.isCarNumberValid()) {
            djk djk = (djk) ank.a(djk.class);
            if (djk != null) {
                djk.a(str);
            }
            this.e.dismissAllKeyboards();
        }
    }

    public final void a() {
        this.y = this.e.getCarPlateString();
        if (!TextUtils.isEmpty(this.y) && this.y.length() > 0) {
            int length = this.y.length();
            String substring = this.y.substring(0, 1);
            if (RdCameraCityInfoDbHelper.getInstance(getContext()).isInSingleWordSet(substring)) {
                a(RdCameraCityInfoDbHelper.getInstance(getContext()).getCityInfoByCarNumberPrefix(substring));
            } else if (length == 1) {
                e();
            } else {
                a(RdCameraCityInfoDbHelper.getInstance(getContext()).getCityInfoByCarNumberPrefix(this.y.substring(0, 2)));
            }
        }
    }

    private void a(RdCameraCityInfo rdCameraCityInfo) {
        if (rdCameraCityInfo == null) {
            e();
            return;
        }
        int intValue = rdCameraCityInfo.carCodeLen.intValue();
        if (intValue != 0) {
            if (intValue == 99) {
                this.j.setHint(R.string.rd_camera_apply_compensation_car_frame_all_words);
            } else {
                this.j.setHint(String.format(getString(R.string.rd_camera_apply_compensation_car_frame_words_count_from_tail), new Object[]{Integer.valueOf(intValue)}));
            }
            this.i.setVisibility(0);
        } else {
            this.i.setVisibility(8);
        }
        int intValue2 = rdCameraCityInfo.carEngineLen.intValue();
        if (intValue2 != 0) {
            if (intValue2 == 99) {
                this.g.setHint(R.string.rd_camera_apply_compensation_car_engine_all_words);
            } else {
                this.g.setHint(String.format(getString(R.string.rd_camera_apply_compensation_car_engine_words_count_from_tail), new Object[]{Integer.valueOf(intValue2)}));
            }
            this.f.setVisibility(0);
        } else {
            this.f.setVisibility(8);
        }
        g();
    }

    private void g() {
        if (this.f.getVisibility() == 0 && this.i.getVisibility() == 0) {
            this.h.setVisibility(8);
        }
    }

    /* access modifiers changed from: private */
    public boolean b(boolean z2) {
        if (this.b == null) {
            if (z2) {
                ToastHelper.showLongToast("需要在地图上标记违章地点");
            }
            return true;
        } else if (!this.z || !this.A) {
            return true;
        } else {
            if (this.f.getVisibility() == 0 && TextUtils.isEmpty(this.g.getText().toString().trim())) {
                return true;
            }
            if ((this.i.getVisibility() != 0 || !TextUtils.isEmpty(this.j.getText().toString().trim())) && !TextUtils.isEmpty(this.m.getText().toString().trim()) && this.n.isChecked()) {
                return false;
            }
            return true;
        }
    }

    public final void b() {
        d();
        a(this.c);
        a(this.g);
        a(this.j);
        a(this.m);
    }

    public final boolean c() {
        return this.e.getProvinceKeyboard().isShowing();
    }

    public final void d() {
        if (isAlive() && this.e.getProvinceKeyboard().isShowing()) {
            this.e.getProvinceKeyboard().dismiss();
        }
    }

    private void a(EditText editText) {
        InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService("input_method");
        if (inputMethodManager != null) {
            editText.setFocusableInTouchMode(false);
            editText.clearFocus();
            inputMethodManager.hideSoftInputFromWindow(editText.getApplicationWindowToken(), 0);
        }
    }

    private void f() {
        String str = "";
        IAccountService iAccountService = (IAccountService) defpackage.esb.a.a.a(IAccountService.class);
        if (iAccountService != null) {
            ant e2 = iAccountService.e();
            if (e2 != null) {
                str = e2.a;
            }
        }
        ViolationSupportcityRequest violationSupportcityRequest = new ViolationSupportcityRequest();
        violationSupportcityRequest.b = str;
        violationSupportcityRequest.c = this.e.getCarPlateString();
        TransferRequestHolder.getInstance().sendViolationSupportcity(violationSupportcityRequest, new AosResponseCallbackOnUi<AosByteResponse>() {
            public void onFailure(AosRequest aosRequest, AosResponseException aosResponseException) {
            }

            public /* synthetic */ void onSuccess(AosResponse aosResponse) {
                AosByteResponse aosByteResponse = (AosByteResponse) aosResponse;
                if (aosByteResponse != null) {
                    JSONObject jSONObject = null;
                    if (aosByteResponse != null) {
                        try {
                            byte[] bArr = (byte[]) aosByteResponse.getResult();
                            if (bArr != null) {
                                String str = new String(bArr, "UTF-8");
                                if (!TextUtils.isEmpty(str)) {
                                    jSONObject = new JSONObject(str);
                                }
                            }
                        } catch (Exception unused) {
                        }
                    }
                    if (jSONObject != null && jSONObject.optInt("status") == 0) {
                        String optString = jSONObject.optString("data");
                        if (TextUtils.isEmpty(optString) || optString.equals("0")) {
                            ToastHelper.showLongToast("抱歉，该车牌号暂不支持");
                            RdCameraApplyFragment.this.A = false;
                        }
                    }
                }
            }
        });
    }

    public /* synthetic */ IPresenter createPresenter() {
        return new bht(this);
    }

    static /* synthetic */ void c(RdCameraApplyFragment rdCameraApplyFragment) {
        POI createPOI = POIFactory.createPOI("", rdCameraApplyFragment.s.getStpoint());
        POI createPOI2 = POIFactory.createPOI("", rdCameraApplyFragment.s.getEndpoint());
        GeoPoint[] geoPointArr = new GeoPoint[rdCameraApplyFragment.s.getPathpoints().size()];
        rdCameraApplyFragment.s.getPathpoints().toArray(geoPointArr);
        Intent intent = new Intent();
        intent.setAction("plugin.selectpoi.SelectFixPoiFromMapFragment");
        intent.setPackage(rdCameraApplyFragment.getActivity().getPackageName());
        SelectPoiFromMapBean selectPoiFromMapBean = new SelectPoiFromMapBean();
        POI poi = rdCameraApplyFragment.b;
        if (poi == null) {
            poi = POIFactory.createPOI("", geoPointArr[geoPointArr.length / 2]);
        }
        selectPoiFromMapBean.setOldPOI(poi);
        selectPoiFromMapBean.setFromPOI(createPOI);
        selectPoiFromMapBean.setToPOI(createPOI2);
        selectPoiFromMapBean.setPoints(geoPointArr);
        selectPoiFromMapBean.setLineOverlayItemStyle(1);
        intent.putExtra("SelectPoiFromMapBean", selectPoiFromMapBean);
        rdCameraApplyFragment.startPageForResult((String) "amap.basemap.action.base_select_fix_poi_from_map_page", new PageBundle(intent), 261);
    }

    static /* synthetic */ boolean a(RdCameraApplyFragment rdCameraApplyFragment, EditText editText) {
        InputMethodManager inputMethodManager = (InputMethodManager) rdCameraApplyFragment.getContext().getSystemService("input_method");
        if (inputMethodManager == null || editText == null) {
            return false;
        }
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
        editText.setImeOptions(268435456);
        return inputMethodManager.showSoftInput(editText, 0);
    }

    static /* synthetic */ void i(RdCameraApplyFragment rdCameraApplyFragment) {
        if (!rdCameraApplyFragment.b(true)) {
            int i2 = -1;
            try {
                i2 = Integer.parseInt(rdCameraApplyFragment.t.b);
            } catch (NumberFormatException e2) {
                kf.a((Throwable) e2);
            }
            UserPayReportRequest userPayReportRequest = new UserPayReportRequest();
            userPayReportRequest.b = NetworkParam.getTaobaoID();
            userPayReportRequest.h = rdCameraApplyFragment.c.getText().toString();
            userPayReportRequest.l = rdCameraApplyFragment.m.getText().toString().trim();
            userPayReportRequest.i = rdCameraApplyFragment.e.getCarPlateString();
            userPayReportRequest.j = rdCameraApplyFragment.g.getText().toString().trim();
            userPayReportRequest.k = rdCameraApplyFragment.j.getText().toString().trim();
            userPayReportRequest.c = RdCameraPaymentItem.GeoPointToString(rdCameraApplyFragment.b.getPoint());
            userPayReportRequest.d = RdCameraPaymentItem.GeoPointToString(rdCameraApplyFragment.s.getStpoint());
            userPayReportRequest.e = RdCameraPaymentItem.GeoPointToString(rdCameraApplyFragment.s.getEndpoint());
            userPayReportRequest.f = rdCameraApplyFragment.s.start;
            userPayReportRequest.g = rdCameraApplyFragment.s.end;
            StringBuilder sb = new StringBuilder();
            sb.append(rdCameraApplyFragment.s.distance.intValue());
            userPayReportRequest.n = sb.toString();
            StringBuilder sb2 = new StringBuilder();
            sb2.append(rdCameraApplyFragment.s.cost_time.intValue());
            userPayReportRequest.o = sb2.toString();
            StringBuilder sb3 = new StringBuilder();
            sb3.append(rdCameraApplyFragment.s.speed.intValue());
            userPayReportRequest.p = sb3.toString();
            StringBuilder sb4 = new StringBuilder();
            sb4.append(rdCameraApplyFragment.s.navi_timestamp);
            userPayReportRequest.q = sb4.toString();
            userPayReportRequest.m = String.valueOf(i2);
            userPayReportRequest.r = "4";
            FeedbackRequestHolder.getInstance().sendUserPayReport(userPayReportRequest, new AosResponseCallbackOnUi<AosByteResponse>() {
                public /* synthetic */ void onSuccess(AosResponse aosResponse) {
                    AosByteResponse aosByteResponse = (AosByteResponse) aosResponse;
                    RdCameraApplyFragment.this.b();
                    JSONObject jSONObject = null;
                    if (aosByteResponse != null) {
                        try {
                            byte[] bArr = (byte[]) aosByteResponse.getResult();
                            if (bArr != null) {
                                String str = new String(bArr, "UTF-8");
                                if (!TextUtils.isEmpty(str)) {
                                    jSONObject = new JSONObject(str);
                                }
                            }
                        } catch (Exception unused) {
                        }
                    }
                    if (jSONObject != null) {
                        int optInt = jSONObject.optInt("report_status");
                        if (optInt == 0) {
                            ToastHelper.showToast(RdCameraApplyFragment.this.getString(R.string.rd_camera_apply_fail));
                        } else if (optInt == -1) {
                            ToastHelper.showToast(RdCameraApplyFragment.this.getString(R.string.rd_camera_apply_duplicated_application));
                        } else if (optInt == -2) {
                            ToastHelper.showLongToast(RdCameraApplyFragment.this.getString(R.string.rd_camera_apply_net_error));
                        } else {
                            if (optInt == -3) {
                                ToastHelper.showLongToast(RdCameraApplyFragment.this.getString(R.string.rd_camera_apply_end));
                            }
                            if ("1".equals(jSONObject.optString("code")) && optInt == 1) {
                                String optString = jSONObject.optString("share_link");
                                String optString2 = jSONObject.optString("share_title");
                                RdCameraDBHelper.getInstance(RdCameraApplyFragment.this.getContext()).deleteWithTimeTamp(RdCameraApplyFragment.this.s);
                                PageBundle pageBundle = new PageBundle();
                                pageBundle.putString("bundle_key_compensation_amount", RdCameraApplyFragment.this.t.c);
                                pageBundle.putString("bundle_key_sharing_title", optString2);
                                pageBundle.putString("bundle_key_sharing_content", "分享内容");
                                pageBundle.putString("bundle_key_sharing_url", optString);
                                RdCameraApplyFragment.this.startPage(RdCameraApplyResultFragment.class, pageBundle);
                            }
                        }
                    }
                }

                public void onFailure(AosRequest aosRequest, AosResponseException aosResponseException) {
                    ToastHelper.showLongToast(RdCameraApplyFragment.this.getString(R.string.rd_camera_apply_net_error));
                }
            });
        }
    }
}
