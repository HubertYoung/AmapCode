package com.autonavi.minimap.drive.errorreport;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.LogUnAvailbleItem;
import com.alipay.mobile.monitor.track.spm.merge.MergeUtil;
import com.alipay.mobile.securitycommon.aliauth.AliAuthConstants.Result;
import com.alipay.mobile.tinyappcustom.h5plugin.H5ContactPlugin;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.aosservice.response.AosResponse;
import com.amap.bundle.aosservice.response.AosResponseCallbackOnUi;
import com.amap.bundle.aosservice.response.AosResponseException;
import com.amap.bundle.blutils.FileUtil;
import com.amap.bundle.drivecommon.mvp.view.DriveBasePage;
import com.amap.bundle.drivecommon.tools.DriveUtil;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.bundle.account.api.IAccountService;
import com.autonavi.common.Page.ON_BACK_TYPE;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.common.SuperId;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.map.fragmentcontainer.page.IPresenter;
import com.autonavi.map.fragmentcontainer.page.PageTheme.Transparent;
import com.autonavi.minimap.R;
import com.autonavi.minimap.feedback.FeedbackRequestHolder;
import com.autonavi.minimap.feedback.param.ReportBatchRequest;
import com.tencent.open.SocialConstants;
import com.uc.webview.export.extension.UCCore;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RouteCarErrorReportDialog extends DriveBasePage<dfb> implements OnTouchListener, Transparent {
    public String a;
    /* access modifiers changed from: private */
    public ArrayList<djm> b;
    private ArrayList<djm> c;
    private int d = 0;
    private String e;
    private String f;
    private String g;
    private POI h;
    private POI i;
    private List<POI> j;
    private View k;
    private String l;
    private boolean m = false;
    private String n = "";

    public void onCreate(Context context) {
        super.onCreate(context);
        this.m = false;
        PageBundle arguments = getArguments();
        this.b = (ArrayList) arguments.getObject("line_error_list");
        this.f = arguments.getString("sourcepage");
        this.g = arguments.getString("category");
        this.e = arguments.getString(H5ContactPlugin.CONTACT);
        if (TextUtils.isEmpty(this.e)) {
            IAccountService iAccountService = (IAccountService) a.a.a(IAccountService.class);
            if (iAccountService != null) {
                ant e2 = iAccountService.e();
                if (e2 != null) {
                    this.e = e2.h;
                }
            }
        }
        this.a = arguments.getString(SocialConstants.PARAM_AVATAR_URI);
        this.h = (POI) arguments.getObject("startpoint");
        this.i = (POI) arguments.getObject("endpoint");
        this.j = (List) arguments.getObject("midpoints");
        this.c = new ArrayList<>();
        this.l = arguments.getString("navi_id");
        this.n = arguments.getString("navi_type");
        ReportBatchRequest a2 = new det(getContext()).a(this.e, b(), new ReportBatchRequest());
        a2.j = this.g;
        a2.k = "0";
        a2.i = this.f;
        a2.l = DriveUtil.NAVI_TYPE_MOTORBIKE.equals(this.n) ? DriveUtil.isMotorAvoidLimitedPath() : DriveUtil.isAvoidLimitedPath() ? "1" : "0";
        if (this.h != null) {
            a2.m = this.h.getName();
        }
        if (this.i != null) {
            a2.o = this.i.getName();
            a2.n = this.i.getId();
        }
        JSONObject jSONObject = new JSONObject();
        try {
            if (DriveUtil.NAVI_TYPE_TRUCK.equals(this.n)) {
                if (DriveUtil.getTruckAvoidLimitedLoad()) {
                    jSONObject.put("truck_load_option", "1");
                } else {
                    jSONObject.put("truck_load_option", "0");
                }
                if (DriveUtil.getTruckAvoidSwitch()) {
                    jSONObject.put("truck_navi_option", "1");
                } else {
                    jSONObject.put("truck_navi_option", "0");
                }
                a2.p = DriveUtil.getTruckCarPlateNumber();
                jSONObject.put("max_height", DriveUtil.getTruckHeight() == 0.0f ? "" : String.valueOf(DriveUtil.getTruckHeight()));
                jSONObject.put(UCCore.OPTION_LOAD_KERNEL_TYPE, DriveUtil.getTruckLoad() == 0.0f ? "" : String.valueOf(DriveUtil.getTruckLoad()));
                jSONObject.put("truck_type", String.valueOf(DriveUtil.getTruckType()));
                jSONObject.put("truck_length", DriveUtil.getTruckLength());
                jSONObject.put("truck_width", DriveUtil.getTruckWidth());
                jSONObject.put("truck_weight", DriveUtil.getTruckWeight());
                jSONObject.put("truck_axles", DriveUtil.getTruckAxles());
            } else if (DriveUtil.NAVI_TYPE_MOTORBIKE.equals(this.n)) {
                String motorInfo = DriveUtil.getMotorInfo();
                if (!TextUtils.isEmpty(motorInfo)) {
                    JSONObject jSONObject2 = new JSONObject(motorInfo);
                    jSONObject.put("swept_volume", jSONObject2.optString("sweptVolume"));
                    jSONObject.put("plate", jSONObject2.optString("motorPlateNum"));
                } else {
                    jSONObject.put("swept_volume", "");
                    jSONObject.put("plate", "");
                }
            } else {
                String carPlateNumber = DriveUtil.getCarPlateNumber();
                if (!TextUtils.isEmpty(carPlateNumber)) {
                    a2.p = carPlateNumber;
                }
            }
        } catch (JSONException e3) {
            e3.printStackTrace();
        }
        a2.q = jSONObject.toString();
        if (!TextUtils.isEmpty(this.a)) {
            a2.a((String) SocialConstants.PARAM_AVATAR_URI, new File(this.a));
        }
        String a3 = tl.a().a(getContext(), this.l, null);
        File file = new File(FileUtil.getFilesDir(), "settings.zcfk");
        FileUtil.writeTextFile(file, a3);
        a2.a((String) "attachment", file);
        FeedbackRequestHolder.getInstance().sendReportBatch(a2, new AosResponseCallbackOnUi<AosByteResponse>() {
            public /* synthetic */ void onSuccess(AosResponse aosResponse) {
                byte[] bArr = (byte[]) ((AosByteResponse) aosResponse).getResult();
                if (bArr != null) {
                    deu deu = new deu();
                    deu.parser(bArr);
                    RouteCarErrorReportDialog.a(RouteCarErrorReportDialog.this, deu.errorCode);
                }
            }

            public void onFailure(AosRequest aosRequest, AosResponseException aosResponseException) {
                RouteCarErrorReportDialog routeCarErrorReportDialog = RouteCarErrorReportDialog.this;
                if (RouteCarErrorReportDialog.this.b != null) {
                    RouteCarErrorReportDialog.this.b.size();
                }
                RouteCarErrorReportDialog.a(routeCarErrorReportDialog, 0);
                aosResponseException.printStackTrace();
            }
        });
        RelativeLayout relativeLayout = new RelativeLayout(getContext());
        relativeLayout.setLayoutParams(new LayoutParams(-2, -2));
        View inflate = LayoutInflater.from(context).inflate(R.layout.drive_widget_progress_dlg, null);
        ((TextView) inflate.findViewById(R.id.msg)).setText(R.string.route_error_report_waiting);
        relativeLayout.addView(inflate);
        LayoutParams layoutParams = (LayoutParams) inflate.getLayoutParams();
        layoutParams.addRule(13);
        inflate.setLayoutParams(layoutParams);
        this.k = relativeLayout;
        setContentView((View) relativeLayout);
    }

    public static ON_BACK_TYPE a() {
        return ON_BACK_TYPE.TYPE_IGNORE;
    }

    public boolean onTouch(View view, MotionEvent motionEvent) {
        return !this.m;
    }

    private String a(GeoPoint geoPoint) {
        StringBuilder sb = new StringBuilder();
        if (this.h != null) {
            sb.append(this.h.getPoint().getLongitude());
            sb.append(",");
            sb.append(this.h.getPoint().getLatitude());
        }
        sb.append(MergeUtil.SEPARATOR_KV);
        if (this.i != null) {
            sb.append(this.i.getPoint().getLongitude());
            sb.append(",");
            sb.append(this.i.getPoint().getLatitude());
        }
        sb.append(MergeUtil.SEPARATOR_KV);
        if (this.j == null || this.j.size() == 0) {
            sb.append(MergeUtil.SEPARATOR_KV);
        } else {
            int i2 = 0;
            for (int i3 = 0; i3 < 3; i3++) {
                if (this.j != null && this.j.size() > i3) {
                    POI poi = this.j.get(i3);
                    if (!(poi == null || poi.getPoint() == null)) {
                        sb.append(poi.getPoint().getLongitude());
                        sb.append(",");
                        sb.append(poi.getPoint().getLatitude());
                        sb.append(MergeUtil.SEPARATOR_KV);
                        i2++;
                    }
                }
            }
            if (i2 == 0) {
                sb.append(MergeUtil.SEPARATOR_KV);
            }
        }
        if (!(geoPoint == null || geoPoint.x == Integer.MIN_VALUE || geoPoint.y == Integer.MIN_VALUE)) {
            sb.append(geoPoint.getLongitude());
            sb.append(",");
            sb.append(geoPoint.getLatitude());
        }
        return sb.toString();
    }

    private JSONObject a(djm djm) {
        JSONObject jSONObject = new JSONObject();
        try {
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("uDes", djm.g);
            JSONObject jSONObject3 = new JSONObject();
            jSONObject3.put("des", djm.f);
            JSONArray jSONArray = new JSONArray();
            jSONArray.put(0, jSONObject3);
            jSONObject2.put("reDes", jSONArray);
            jSONObject.put("type", djm.c);
            jSONObject.put(LogUnAvailbleItem.EXTRA_KEY_SUBTYPE, djm.f);
            jSONObject.put("points", a(djm.d));
            jSONObject.put("description", jSONObject2);
            if (!DriveUtil.NAVI_TYPE_MOTORBIKE.equals(this.n) || djm.a <= 0) {
                String str = "";
                if ("6".equals(this.f)) {
                    str = "0601";
                    if (Result.RUBBISH_ACCOUNT.equals(djm.c)) {
                        str = "0602";
                    } else if ("6001".equals(djm.c)) {
                        str = "0604";
                    } else if ("6002".equals(djm.c)) {
                        str = "0605";
                    } else if ("4001".equals(djm.c) && djs.a(getContext(), 3).contains(djm.f)) {
                        str = "0603";
                    } else if ("5008".equals(djm.c) && djs.a(getContext(), 7).contains(djm.f)) {
                        str = "0606";
                    }
                } else if ("4".equals(this.f)) {
                    if (djm.b == 2) {
                        str = "0402";
                    } else if (djm.b == 3) {
                        str = "0403";
                    } else if (djm.b == 4) {
                        str = "0404";
                    }
                } else if ("15".equals(this.f)) {
                    if (djm.b == 2) {
                        str = "1502";
                    } else if (djm.b == 3) {
                        str = "1503";
                    } else if (djm.b == 4) {
                        str = "1504";
                    }
                } else if ("42".equals(this.f)) {
                    if ("4001".equals(djm.c)) {
                        str = "4201";
                    } else if ("5012".equals(djm.c)) {
                        str = "4202";
                    } else if ("5011".equals(djm.c)) {
                        str = "4203";
                    } else if ("5000".equals(djm.c)) {
                        str = "4204";
                    }
                } else if (SuperId.BIT_2_INDOOR_TAG_HOT.equals(this.f)) {
                    if ("4001".equals(djm.c)) {
                        str = "4101";
                    } else if ("5012".equals(djm.c)) {
                        str = "4102";
                    } else if ("5011".equals(djm.c)) {
                        str = "4103";
                    } else if ("5000".equals(djm.c)) {
                        str = "4104";
                    }
                } else if ("44".equals(this.f)) {
                    if ("4001".equals(djm.c)) {
                        str = "4401";
                    } else if ("5012".equals(djm.c)) {
                        str = "4402";
                    } else if ("5011".equals(djm.c)) {
                        str = "4403";
                    } else if ("6000".equals(djm.c)) {
                        str = "4404";
                    } else if ("2003".equals(djm.c)) {
                        str = "4404";
                    }
                }
                jSONObject.put("error_id", str);
                return jSONObject;
            }
            jSONObject.put("error_id", djm.a);
            return jSONObject;
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }

    private String b() {
        int size = this.b.size();
        if (size >= 7) {
            size = 7;
        }
        JSONArray jSONArray = new JSONArray();
        for (int i2 = 0; i2 < size; i2++) {
            try {
                jSONArray.put(i2, a(this.b.get(i2)));
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }
        return jSONArray.toString();
    }

    public /* synthetic */ IPresenter createPresenter() {
        return new dfb(this);
    }

    static /* synthetic */ void a(RouteCarErrorReportDialog routeCarErrorReportDialog, int i2) {
        if (routeCarErrorReportDialog.isAlive()) {
            boolean z = true;
            if (i2 == 1) {
                ToastHelper.showLongToast(AMapAppGlobal.getApplication().getString(R.string.thanks_for_feedback));
            } else {
                ToastHelper.showLongToast(AMapAppGlobal.getApplication().getString(R.string.network_error_message));
            }
            PageBundle pageBundle = new PageBundle();
            if (i2 != 1) {
                z = false;
            }
            pageBundle.putBoolean("upload_result", z);
            routeCarErrorReportDialog.setResult(ResultType.OK, pageBundle);
            routeCarErrorReportDialog.finish();
        }
    }
}
