package com.autonavi.carowner.roadcamera.page;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.Handler;
import android.os.Looper;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.aosservice.response.AosResponse;
import com.amap.bundle.aosservice.response.AosResponseCallbackOnUi;
import com.amap.bundle.aosservice.response.AosResponseException;
import com.amap.bundle.blutils.app.ConfigerHelper;
import com.amap.bundle.drivecommon.map.db.helper.RdCameraCityInfoDbHelper;
import com.amap.bundle.drivecommon.map.db.model.RdCameraPaymentItem;
import com.amap.bundle.drivecommon.mvp.view.DriveBasePage;
import com.amap.bundle.drivecommon.tools.DriveSpUtil;
import com.amap.bundle.utils.ui.NoDBClickUtil;
import com.autonavi.common.PageBundle;
import com.autonavi.map.core.LocationMode.LocationNone;
import com.autonavi.map.fragmentcontainer.page.IPresenter;
import com.autonavi.map.widget.ProgressDlg;
import com.autonavi.minimap.R;
import com.autonavi.minimap.transfer.TransferRequestHolder;
import com.autonavi.minimap.transfer.param.ViolationConditionsRequest;
import java.util.List;
import org.json.JSONObject;

public class RdCameraPaymentTypeFragment extends DriveBasePage<bhw> implements LocationNone {
    public RdCameraPaymentItem a;
    public List<bho> b;
    public String c;
    public bhs d;
    /* access modifiers changed from: private */
    public boolean e = false;
    private LinearLayout f;
    private ProgressDlg g;
    /* access modifiers changed from: private */
    public Handler h = new Handler(Looper.getMainLooper());
    private final OnClickListener i = new OnClickListener() {
        public final void onClick(View view) {
            int id = view.getId();
            if (id == R.id.know_more_rule) {
                RdCameraPaymentTypeFragment.f(RdCameraPaymentTypeFragment.this);
                return;
            }
            if (id == R.id.btn_back && RdCameraPaymentTypeFragment.this.isAlive()) {
                RdCameraPaymentTypeFragment.this.finish();
            }
        }
    };

    public void onCreate(Context context) {
        super.onCreate(context);
        setContentView(R.layout.rd_camera_payment_type_fragment);
    }

    public final void a() {
        if (euk.a()) {
            int a2 = euk.a(getContext());
            View contentView = getContentView();
            contentView.setPadding(contentView.getPaddingLeft(), contentView.getPaddingTop() + a2, contentView.getPaddingRight(), contentView.getPaddingBottom());
        }
        int i2 = 0;
        ((TextView) getContentView().findViewById(R.id.summary)).setText(Html.fromHtml(getString(R.string.rd_camera_payment_type_sumary, this.c)));
        this.f = (LinearLayout) getContentView().findViewById(R.id.type_container);
        int size = this.b.size();
        while (true) {
            int i3 = size - 1;
            if (i2 < i3) {
                a(this.b.get(i2));
                i2++;
            } else {
                a(this.b.get(i3)).findViewById(R.id.bottom_separator).setVisibility(8);
                NoDBClickUtil.a(getContentView().findViewById(R.id.know_more_rule), this.i);
                NoDBClickUtil.a(getContentView().findViewById(R.id.btn_back), this.i);
                return;
            }
        }
    }

    private View a(final bho bho) {
        View inflate = LayoutInflater.from(getContext()).inflate(R.layout.rd_camera_payment_type_list_item_layout, this.f, false);
        ((TextView) inflate.findViewById(R.id.type_name)).setText(bho.a);
        NoDBClickUtil.a(inflate, (OnClickListener) new OnClickListener() {
            public final void onClick(View view) {
                RdCameraPaymentTypeFragment.a(RdCameraPaymentTypeFragment.this, bho);
            }
        });
        this.f.addView(inflate);
        return inflate;
    }

    /* access modifiers changed from: private */
    public void b() {
        if (this.g != null) {
            this.g.dismiss();
            this.g = null;
        }
    }

    public /* synthetic */ IPresenter createPresenter() {
        return new bhw(this);
    }

    static /* synthetic */ void a(RdCameraPaymentTypeFragment rdCameraPaymentTypeFragment, final bho bho) {
        final ViolationConditionsRequest violationConditionsRequest = new ViolationConditionsRequest();
        violationConditionsRequest.b = DriveSpUtil.getString(rdCameraPaymentTypeFragment.getContext(), DriveSpUtil.RDCAMERA_VIOLATION_CONDITIONS_TVMD5, null);
        TransferRequestHolder.getInstance().sendViolationConditions(violationConditionsRequest, new AosResponseCallbackOnUi<AosByteResponse>() {
            public /* synthetic */ void onSuccess(AosResponse aosResponse) {
                AosByteResponse aosByteResponse = (AosByteResponse) aosResponse;
                RdCameraPaymentTypeFragment.this.b();
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
                    RdCameraPaymentTypeFragment.this.e = true;
                    if (jSONObject.has("data")) {
                        JSONObject optJSONObject = jSONObject.optJSONObject("data");
                        String str2 = "";
                        if (optJSONObject != null) {
                            str2 = optJSONObject.optString("tvmd5");
                        }
                        DriveSpUtil.setString(RdCameraPaymentTypeFragment.this.getContext(), DriveSpUtil.RDCAMERA_VIOLATION_CONDITIONS_TVMD5, str2);
                        if (optJSONObject != null && optJSONObject.length() > 0) {
                            RdCameraCityInfoDbHelper.parseCityInfoAndSave(optJSONObject.optJSONArray("conditions"), RdCameraPaymentTypeFragment.this.getContext(), new sh() {
                                public final void a() {
                                    if (RdCameraPaymentTypeFragment.this.h != null && RdCameraPaymentTypeFragment.this.isAlive()) {
                                        RdCameraPaymentTypeFragment.this.h.post(new Runnable() {
                                            public final void run() {
                                                if (RdCameraPaymentTypeFragment.this.isAlive()) {
                                                    PageBundle pageBundle = new PageBundle();
                                                    pageBundle.putObject("rd_camera_payment_data_item", RdCameraPaymentTypeFragment.this.a);
                                                    pageBundle.putObject("rd_camera_payment_type", bho);
                                                    pageBundle.putBoolean("bundle_key_got_rule", RdCameraPaymentTypeFragment.this.e);
                                                    if (RdCameraPaymentTypeFragment.this.d != null) {
                                                        pageBundle.putObject("bundle_key_h5_page_param", RdCameraPaymentTypeFragment.this.d);
                                                    }
                                                    RdCameraPaymentTypeFragment.this.startPage(RdCameraApplyFragment.class, pageBundle);
                                                }
                                            }
                                        });
                                    }
                                }
                            });
                            return;
                        }
                    }
                    PageBundle pageBundle = new PageBundle();
                    pageBundle.putObject("rd_camera_payment_data_item", RdCameraPaymentTypeFragment.this.a);
                    pageBundle.putObject("rd_camera_payment_type", bho);
                    pageBundle.putBoolean("bundle_key_got_rule", RdCameraPaymentTypeFragment.this.e);
                    if (RdCameraPaymentTypeFragment.this.d != null) {
                        pageBundle.putObject("bundle_key_h5_page_param", RdCameraPaymentTypeFragment.this.d);
                    }
                    RdCameraPaymentTypeFragment.this.startPage(RdCameraApplyFragment.class, pageBundle);
                }
            }

            public void onFailure(AosRequest aosRequest, AosResponseException aosResponseException) {
                RdCameraPaymentTypeFragment.this.b();
                RdCameraPaymentTypeFragment.this.e = false;
                PageBundle pageBundle = new PageBundle();
                pageBundle.putObject("rd_camera_payment_data_item", RdCameraPaymentTypeFragment.this.a);
                pageBundle.putObject("rd_camera_payment_type", bho);
                pageBundle.putBoolean("bundle_key_got_rule", RdCameraPaymentTypeFragment.this.e);
                if (RdCameraPaymentTypeFragment.this.d != null) {
                    pageBundle.putObject("bundle_key_h5_page_param", RdCameraPaymentTypeFragment.this.d);
                }
                RdCameraPaymentTypeFragment.this.startPage(RdCameraApplyFragment.class, pageBundle);
            }
        });
        String string = rdCameraPaymentTypeFragment.getString(R.string.rd_camera_payment_loading);
        rdCameraPaymentTypeFragment.b();
        rdCameraPaymentTypeFragment.g = new ProgressDlg(rdCameraPaymentTypeFragment.getActivity());
        rdCameraPaymentTypeFragment.g.setMessage(string);
        rdCameraPaymentTypeFragment.g.setOnCancelListener(new OnCancelListener() {
            public final void onCancel(DialogInterface dialogInterface) {
                if (violationConditionsRequest != null) {
                    violationConditionsRequest.cancel();
                }
            }
        });
        rdCameraPaymentTypeFragment.g.show();
    }

    static /* synthetic */ void f(RdCameraPaymentTypeFragment rdCameraPaymentTypeFragment) {
        aja aja = new aja(ConfigerHelper.getInstance().getRdcameraPaymentKnowMoreRuleUrl());
        aja.b = new ajf() {
            public final a l_() {
                return new a() {
                    public final String a() {
                        return RdCameraPaymentTypeFragment.this.getString(R.string.refresh);
                    }

                    public final boolean b() {
                        if (AnonymousClass3.this.a != null) {
                            AnonymousClass3.this.a.a().reload();
                        }
                        return true;
                    }
                };
            }
        };
        aix aix = (aix) a.a.a(aix.class);
        if (aix != null) {
            aix.a((bid) rdCameraPaymentTypeFragment, aja);
        }
    }
}
