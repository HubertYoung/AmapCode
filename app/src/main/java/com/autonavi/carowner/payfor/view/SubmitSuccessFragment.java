package com.autonavi.carowner.payfor.view;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.aosservice.response.AosResponse;
import com.amap.bundle.aosservice.response.AosResponseCallbackOnUi;
import com.amap.bundle.aosservice.response.AosResponseException;
import com.amap.bundle.drivecommon.inter.NetConstant;
import com.amap.bundle.drivecommon.mvp.view.DriveBasePage;
import com.amap.bundle.drivecommon.payfor.PayforNaviData;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.bundle.account.api.IAccountService;
import com.autonavi.carowner.payfor.ApplyPayForResultFragment;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.IPresenter;
import com.autonavi.map.widget.ProgressDlg;
import com.autonavi.minimap.R;
import com.autonavi.minimap.usepay.UsePayRequestHolder;
import com.autonavi.minimap.usepay.param.RelateRequest;
import org.json.JSONObject;

public class SubmitSuccessFragment extends DriveBasePage<bhn> implements OnClickListener {
    public View a;
    public View b;
    public TextView c;
    public PayforNaviData d;
    /* access modifiers changed from: private */
    public Handler e;
    private ProgressDlg f;

    public void onCreate(Context context) {
        super.onCreate(context);
        setContentView(R.layout.activities_submit_success_dialog);
        this.e = new Handler();
    }

    public void onClick(View view) {
        if (view == this.a) {
            if (getRequestCode() == 10001) {
                setResult(ResultType.OK, (PageBundle) null);
                finish();
            } else if (getRequestCode() == 1) {
                PageBundle pageBundle = new PageBundle();
                pageBundle.putString("soure_from_page", "submit");
                setResult(ResultType.OK, pageBundle);
                finish();
            } else if (isAlive()) {
                finish();
            }
        } else if (view == this.b) {
            IAccountService iAccountService = (IAccountService) a.a.a(IAccountService.class);
            if (iAccountService != null) {
                iAccountService.a(getPageContext(), (anq) new anq() {
                    public final void loginOrBindCancel() {
                    }

                    public final void onComplete(boolean z) {
                        if (z) {
                            SubmitSuccessFragment.this.e.postDelayed(new Runnable() {
                                public final void run() {
                                    SubmitSuccessFragment.a(SubmitSuccessFragment.this);
                                }
                            }, 500);
                        }
                    }
                });
            }
        }
    }

    public /* synthetic */ IPresenter createPresenter() {
        return new bhn(this);
    }

    static /* synthetic */ void a(SubmitSuccessFragment submitSuccessFragment) {
        final RelateRequest relateRequest = new RelateRequest();
        relateRequest.b = submitSuccessFragment.d.recordId;
        UsePayRequestHolder.getInstance().sendRelate(relateRequest, new AosResponseCallbackOnUi<AosByteResponse>() {
            public /* synthetic */ void onSuccess(AosResponse aosResponse) {
                AosByteResponse aosByteResponse = (AosByteResponse) aosResponse;
                SubmitSuccessFragment.c(SubmitSuccessFragment.this);
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
                    int optInt = jSONObject.optInt("code");
                    if (optInt == 1) {
                        SubmitSuccessFragment.this.d.doneDays = jSONObject.optInt(NetConstant.KEY_DONE_DAYS);
                        SubmitSuccessFragment.this.d.delete();
                        SubmitSuccessFragment.this.e.postDelayed(new Runnable() {
                            public final void run() {
                                if (SubmitSuccessFragment.this.isAlive()) {
                                    SubmitSuccessFragment.this.finish();
                                }
                                SubmitSuccessFragment.this.e.postDelayed(new Runnable() {
                                    public final void run() {
                                        PageBundle pageBundle = new PageBundle();
                                        pageBundle.putObject("payforNaviData", SubmitSuccessFragment.this.d);
                                        SubmitSuccessFragment.this.startPage(ApplyPayForResultFragment.class, pageBundle);
                                    }
                                }, 500);
                            }
                        }, 500);
                    } else if (optInt == 101) {
                        ToastHelper.showLongToast(SubmitSuccessFragment.this.getContext().getString(R.string.activities_cannot_apply_payfor));
                    } else {
                        ToastHelper.showLongToast(SubmitSuccessFragment.this.getContext().getString(R.string.ic_net_error_tipinfo));
                    }
                }
            }

            public void onFailure(AosRequest aosRequest, AosResponseException aosResponseException) {
                SubmitSuccessFragment.c(SubmitSuccessFragment.this);
                ToastHelper.showLongToast(SubmitSuccessFragment.this.getContext().getString(R.string.ic_net_error_tipinfo));
                aosResponseException.printStackTrace();
            }
        });
        String string = submitSuccessFragment.getContext().getString(R.string.activities_applying_payfor);
        submitSuccessFragment.f = new ProgressDlg(submitSuccessFragment.getActivity());
        submitSuccessFragment.f.setMessage(string);
        submitSuccessFragment.f.setOnCancelListener(new OnCancelListener() {
            public final void onCancel(DialogInterface dialogInterface) {
                if (relateRequest != null) {
                    relateRequest.cancel();
                }
            }
        });
        submitSuccessFragment.f.show();
    }

    static /* synthetic */ void c(SubmitSuccessFragment submitSuccessFragment) {
        if (submitSuccessFragment.f != null) {
            submitSuccessFragment.f.dismiss();
            submitSuccessFragment.f = null;
        }
    }
}
