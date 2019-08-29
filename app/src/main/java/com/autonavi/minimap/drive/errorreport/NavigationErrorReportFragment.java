package com.autonavi.minimap.drive.errorreport;

import android.content.Context;
import android.graphics.Rect;
import android.os.Handler;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLayoutChangeListener;
import android.view.animation.TranslateAnimation;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.alipay.mobile.security.bio.common.record.MetaRecord;
import com.alipay.mobile.tinyappcustom.h5plugin.H5ContactPlugin;
import com.amap.bundle.drivecommon.mvp.view.DriveBaseMapPage;
import com.amap.bundle.drivecommon.navi.navidata.NavigationDataResult;
import com.amap.bundle.drivecommon.tools.DriveUtil;
import com.amap.bundle.statistics.LogManager;
import com.autonavi.annotation.PageAction;
import com.autonavi.bundle.account.api.IAccountService;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.map.core.MapManager;
import com.autonavi.minimap.R;
import com.autonavi.minimap.base.overlay.LineOverlayItem;
import com.autonavi.minimap.basemap.traffic.TrafficUtil;
import com.autonavi.minimap.drive.errorreport.overlay.ErrorReportLineOverlay;
import com.autonavi.minimap.drive.errorreport.overlay.ErrorReportPointOverlay;
import com.autonavi.minimap.drive.errorreport.overlay.ErrorReportRoutePointItem;
import com.autonavi.minimap.drive.errorreport.overlay.ErrorReportRoutePointItem.RoutePointType;
import com.autonavi.minimap.drive.errorreport.overlay.ErrorReportRoutePointOverlay;
import com.autonavi.widget.ui.AlertView;
import com.autonavi.widget.ui.TitleBar;
import com.tencent.open.SocialConstants;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.json.JSONObject;

@PageAction("amap.basemap.action.navigation_error_report")
public class NavigationErrorReportFragment extends DriveBaseMapPage<dez> implements OnClickListener, btz {
    private static final int[] am = {6, 6, 6, 6};
    public TextView A;
    public TextView B;
    public View C;
    public View D;
    public View E;
    public TextView F;
    public TextView G;
    public TextView H;
    public TextView I;
    public View J;
    public View K;
    public View L;
    public View M;
    public TextView N;
    public TextView O;
    public TextView P;
    public boolean Q = false;
    public boolean R = false;
    public boolean S = true;
    public int T = 1;
    public djm U;
    public String V = "";
    public AlertView W = null;
    public dex X;
    public Handler Y = new Handler();
    public int Z;
    public NavigationDataResult a;
    public GeoPoint aa;
    public TranslateAnimation ab;
    public ForegroundColorSpan ac;
    public boolean ad = false;
    public OnLayoutChangeListener ae = new OnLayoutChangeListener() {
        public final void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
            int i9 = i7 - i5;
            int i10 = i8 - i6;
            int width = view.getWidth();
            int height = view.getHeight();
            if (i9 != width || i10 != height) {
                NavigationErrorReportFragment.this.a(width, height);
            }
        }
    };
    /* access modifiers changed from: private */
    public ArrayList<GeoPoint> af = new ArrayList<>();
    private boolean ag = false;
    /* access modifiers changed from: private */
    public int ah = -1;
    private int ai = -1;
    private int aj = -1;
    private String ak = "";
    private boolean al = false;
    public ArrayList<GeoPoint> b = new ArrayList<>();
    public List<dfe> c;
    public dfe d;
    public GeoPoint e;
    public GeoPoint f;
    public ArrayList<POI> g;
    public ArrayList<GeoPoint> h = new ArrayList<>();
    public ErrorReportLineOverlay i;
    public ErrorReportPointOverlay j;
    public ErrorReportRoutePointOverlay k;
    public LinearLayout l;
    public CheckBox m;
    public CheckBox n;
    public View o;
    public TitleBar p;
    public View q;
    public ImageView r;
    public View s;
    public View t;
    public TextView u;
    public TextView v;
    public TextView w;
    public TextView x;
    public TextView y;
    public View z;

    public static class a implements Runnable {
        WeakReference<NavigationErrorReportFragment> a;

        public a(NavigationErrorReportFragment navigationErrorReportFragment) {
            this.a = new WeakReference<>(navigationErrorReportFragment);
        }

        public final void run() {
            NavigationErrorReportFragment navigationErrorReportFragment = (NavigationErrorReportFragment) this.a.get();
            if (navigationErrorReportFragment != null) {
                NavigationErrorReportFragment.a(navigationErrorReportFragment);
            }
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: i */
    public dez createPresenter() {
        return new dez(this);
    }

    public void onCreate(Context context) {
        super.onCreate(context);
        setContentView(R.layout.navigation_error_report_page);
        requestScreenOrientation(1);
    }

    public View getMapSuspendView() {
        ccy suspendWidgetHelper = getSuspendWidgetHelper();
        if (suspendWidgetHelper == null) {
            return null;
        }
        suspendWidgetHelper.a(suspendWidgetHelper.d());
        return new dev(this).getSuspendView();
    }

    public final void a() {
        switch (this.T) {
            case 1:
                finish();
                return;
            case 2:
            case 3:
            case 4:
                if (this.ah == 1 || this.ah == 13) {
                    a(5);
                    return;
                } else {
                    a(1);
                    return;
                }
            case 5:
                if (this.ah != 1 && this.ah != 13) {
                    com.autonavi.widget.ui.AlertView.a aVar = new com.autonavi.widget.ui.AlertView.a(getContext());
                    aVar.a((CharSequence) getResources().getString(R.string.car_error_edit_detail_give_up)).b((CharSequence) getResources().getString(R.string.car_error_edit_detail_cancel), (defpackage.ern.a) new defpackage.ern.a() {
                        public final void onClick(AlertView alertView, int i) {
                            NavigationErrorReportFragment.this.dismissViewLayer(alertView);
                            NavigationErrorReportFragment.this.W = null;
                        }
                    }).a((CharSequence) getResources().getString(R.string.car_error_edit_detail_yes), (defpackage.ern.a) new defpackage.ern.a() {
                        public final void onClick(AlertView alertView, int i) {
                            NavigationErrorReportFragment.this.dismissViewLayer(alertView);
                            NavigationErrorReportFragment.this.W = null;
                            if (NavigationErrorReportFragment.this.ah == 1 || NavigationErrorReportFragment.this.ah == 13) {
                                NavigationErrorReportFragment.this.a(1);
                            } else {
                                NavigationErrorReportFragment.this.a(2);
                            }
                        }
                    });
                    this.W = aVar.a();
                    showViewLayer(this.W);
                    this.W.startAnimation();
                    break;
                } else {
                    a(1);
                    return;
                }
        }
    }

    private void j() {
        this.x.setText(R.string.car_error_edit_detail_description);
        this.y.setText(R.string.car_error_edit_detail_phonenumber);
    }

    public final boolean c() {
        return DriveUtil.NAVI_TYPE_TRUCK.equals(this.V);
    }

    public final void a(TextView textView, int i2) {
        StringBuilder sb = new StringBuilder();
        sb.append(getContext().getString(i2));
        sb.append("＊");
        String sb2 = sb.toString();
        int length = sb2.length();
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(sb2);
        spannableStringBuilder.setSpan(this.ac, length - 1, length, 33);
        textView.setText(spannableStringBuilder);
    }

    public void onClick(View view) {
        int id = view.getId();
        int i2 = 0;
        if (id == R.id.cannot_pass_category) {
            c(3);
            a(2);
            if (!DriveUtil.NAVI_TYPE_MOTORBIKE.equals(this.V)) {
                a(k(), djs.a(getContext(), this.ah), (String) "B037");
                return;
            }
            try {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("type", 0);
                LogManager.actionLogV2("P00441", "B002", jSONObject);
            } catch (Exception unused) {
            }
        } else if (id == R.id.around_way_category) {
            c(2);
            a(2);
            if (!DriveUtil.NAVI_TYPE_MOTORBIKE.equals(this.V)) {
                a(k(), djs.a(getContext(), this.ah), (String) "B037");
                return;
            }
            try {
                JSONObject jSONObject2 = new JSONObject();
                jSONObject2.put("type", 1);
                LogManager.actionLogV2("P00441", "B002", jSONObject2);
            } catch (Exception unused2) {
            }
        } else if (id == R.id.camera_error_category) {
            c(5);
            a(2);
            if (!DriveUtil.NAVI_TYPE_MOTORBIKE.equals(this.V)) {
                a(k(), djs.a(getContext(), this.ah), (String) "B037");
                return;
            }
            try {
                JSONObject jSONObject3 = new JSONObject();
                jSONObject3.put("type", 2);
                LogManager.actionLogV2("P00441", "B002", jSONObject3);
            } catch (Exception unused3) {
            }
        } else if (id == R.id.speed_error_category) {
            c(6);
            a(2);
            if (!DriveUtil.NAVI_TYPE_MOTORBIKE.equals(this.V)) {
                a(k(), djs.a(getContext(), this.ah), (String) "B037");
                return;
            }
            try {
                JSONObject jSONObject4 = new JSONObject();
                jSONObject4.put("type", 3);
                LogManager.actionLogV2("P00441", "B002", jSONObject4);
            } catch (Exception unused4) {
            }
        } else if (id == R.id.destination_error_category) {
            c(1);
            a(5);
            if (!DriveUtil.NAVI_TYPE_MOTORBIKE.equals(this.V)) {
                a(k(), djs.a(getContext(), this.ah), (String) "B037");
                return;
            }
            try {
                JSONObject jSONObject5 = new JSONObject();
                jSONObject5.put("type", 4);
                LogManager.actionLogV2("P00441", "B002", jSONObject5);
            } catch (Exception unused5) {
            }
        } else if (id == R.id.wrong_drive_category) {
            c(7);
            a(2);
            if (!DriveUtil.NAVI_TYPE_MOTORBIKE.equals(this.V)) {
                a(k(), djs.a(getContext(), this.ah), (String) "B037");
                return;
            }
            try {
                JSONObject jSONObject6 = new JSONObject();
                jSONObject6.put("type", 5);
                LogManager.actionLogV2("P00441", "B002", jSONObject6);
            } catch (Exception unused6) {
            }
        } else if (id == R.id.truck_cannot_pass_category) {
            c(8);
            a(2);
            a(k(), djs.a(getContext(), this.ah), (String) "B037");
        } else if (id == R.id.truck_around_way_category) {
            c(9);
            a(2);
            a(k(), djs.a(getContext(), this.ah), (String) "B037");
        } else if (id == R.id.truck_msg_error_category) {
            c(10);
            a(2);
            a(k(), djs.a(getContext(), this.ah), (String) "B037");
        } else if (id == R.id.truck_camera_error_category) {
            c(12);
            a(2);
            a(k(), djs.a(getContext(), this.ah), (String) "B037");
        } else if (id == R.id.truck_destination_error_category) {
            c(13);
            a(5);
            a(k(), djs.a(getContext(), this.ah), (String) "B037");
        } else if (id == R.id.poi_confirm_choose) {
            if (this.U != null) {
                this.aa = this.U.d;
            }
            d();
            a(5);
            a(k(), getString(R.string.car_error_tip_confirm_tip_log_report), (String) "B037");
        } else if (id == R.id.edit_detail_radio_1) {
            b(1);
        } else if (id == R.id.edit_detail_radio_2) {
            b(2);
        } else if (id == R.id.edit_detail_radio_3) {
            b(3);
        } else if (id == R.id.edit_detail_radio_4) {
            b(4);
        } else if (id == R.id.select_destination_position) {
            a(2);
        } else {
            if (id == R.id.car_error_page_edit_problem_input_layout || id == R.id.car_error_page_edit_problem_input) {
                if (this.X == null) {
                    PageBundle pageBundle = new PageBundle();
                    pageBundle.putBoolean("bundle_contact", false);
                    pageBundle.putString("ErrorReportDescriptionInputFragment.hintstring", djs.a(getContext(), this.ah, false));
                    pageBundle.putString("ErrorReportDescriptionInputFragment.inputstring", this.ak);
                    final dex dex = new dex(getContext(), R.layout.route_line_error_report_description_input_layout, pageBundle);
                    dex.c = new defpackage.dex.a() {
                        public final void a(String str) {
                            NavigationErrorReportFragment.this.dismissViewLayer(dex);
                            NavigationErrorReportFragment.b(NavigationErrorReportFragment.this, str);
                        }
                    };
                    dex.d = pageBundle;
                    showViewLayer(dex);
                    setSoftInputMode(18);
                    this.E.setVisibility(8);
                    this.X = dex;
                }
            } else if (id == R.id.car_error_page_edit_contact_input_layout || id == R.id.car_error_page_edit_contact_input) {
                if (this.X == null) {
                    PageBundle pageBundle2 = new PageBundle();
                    pageBundle2.putBoolean("bundle_contact", true);
                    pageBundle2.putString("ErrorReportDescriptionInputFragment.hintstring", getResources().getString(R.string.car_error_edit_detail_input_contact));
                    pageBundle2.putString("ErrorReportDescriptionInputFragment.inputstring", this.O.getText().toString());
                    final dex dex2 = new dex(getContext(), R.layout.route_line_error_report_description_input_layout, pageBundle2);
                    dex2.c = new defpackage.dex.a() {
                        public final void a(String str) {
                            NavigationErrorReportFragment.this.dismissViewLayer(dex2);
                            NavigationErrorReportFragment.a(NavigationErrorReportFragment.this, str);
                        }
                    };
                    dex2.d = pageBundle2;
                    showViewLayer(dex2);
                    setSoftInputMode(18);
                    this.E.setVisibility(8);
                    this.X = dex2;
                }
            } else if (id == R.id.car_error_submit && o()) {
                String str = "";
                if (c()) {
                    switch (this.ah) {
                        case 8:
                            if (this.aj != 1) {
                                if (this.aj != 2) {
                                    if (this.aj == 3) {
                                        str = getString(R.string.car_error_radio_other);
                                        break;
                                    }
                                } else {
                                    str = getString(R.string.car_error_radio_do_work);
                                    break;
                                }
                            } else {
                                str = getString(R.string.car_error_radio_no_way);
                                break;
                            }
                            break;
                        case 9:
                            if (this.aj != 1) {
                                if (this.aj != 2) {
                                    if (this.aj == 3) {
                                        str = getString(R.string.car_error_radio_other);
                                        break;
                                    }
                                } else {
                                    str = getString(R.string.car_error_radio_road_around);
                                    break;
                                }
                            } else {
                                str = getString(R.string.car_error_radio_road_bad);
                                break;
                            }
                            break;
                        case 10:
                            if (this.aj != 1) {
                                if (this.aj != 2) {
                                    if (this.aj != 3) {
                                        if (this.aj == 4) {
                                            str = getString(R.string.car_error_radio_other);
                                            break;
                                        }
                                    } else {
                                        str = getString(R.string.car_error_radio_play_content_error_autonavi);
                                        break;
                                    }
                                } else {
                                    str = getString(R.string.car_error_radio_limit_width_height_weight_autonavi);
                                    break;
                                }
                            } else {
                                str = getString(R.string.car_error_radio_limit_pass);
                                break;
                            }
                            break;
                        case 12:
                            if (this.aj != 1) {
                                if (this.aj != 2) {
                                    if (this.aj != 3) {
                                        if (this.aj == 4) {
                                            str = getString(R.string.car_error_radio_other);
                                            break;
                                        }
                                    } else {
                                        str = getString(R.string.car_error_radio_speed_limit_error);
                                        break;
                                    }
                                } else {
                                    str = getString(R.string.car_error_radio_monitor_redundant_error_autonavi);
                                    break;
                                }
                            } else {
                                str = getString(R.string.car_error_radio_monitor_miss_error_autonavi);
                                break;
                            }
                            break;
                        case 13:
                            if (this.aj != 1) {
                                if (this.aj != 2) {
                                    if (this.aj != 3) {
                                        if (this.aj == 4) {
                                            str = getString(R.string.car_error_radio_other);
                                            break;
                                        }
                                    } else {
                                        str = getString(R.string.car_error_radio_not_find);
                                        break;
                                    }
                                } else {
                                    str = getString(R.string.car_error_radio_location_error);
                                    break;
                                }
                            } else {
                                str = getString(R.string.car_error_radio_closed);
                                break;
                            }
                            break;
                    }
                } else {
                    switch (this.aj) {
                        case 1:
                            if (this.ah != 1) {
                                if (this.ah == 3) {
                                    str = getResources().getString(R.string.car_error_radio_do_work);
                                    i2 = 4906;
                                    break;
                                }
                            } else {
                                str = getResources().getString(R.string.car_error_radio_closed);
                                i2 = 4901;
                                break;
                            }
                            break;
                        case 2:
                            if (this.ah != 1) {
                                if (this.ah == 3) {
                                    str = getResources().getString(R.string.car_error_radio_single_direction);
                                    i2 = 4907;
                                    break;
                                }
                            } else {
                                str = getResources().getString(R.string.car_error_radio_location_error);
                                i2 = 4902;
                                break;
                            }
                            break;
                        case 3:
                            if (this.ah != 1) {
                                if (this.ah == 3) {
                                    str = getResources().getString(R.string.car_error_radio_no_turn);
                                    i2 = 4908;
                                    break;
                                }
                            } else {
                                str = getResources().getString(R.string.car_error_radio_not_find);
                                i2 = 4903;
                                break;
                            }
                            break;
                        case 4:
                            if (this.ah != 1) {
                                if (this.ah == 3) {
                                    str = getResources().getString(R.string.car_error_radio_no_way);
                                    i2 = 4909;
                                    break;
                                }
                            } else {
                                str = getResources().getString(R.string.car_error_radio_other_issue);
                                i2 = 4904;
                                break;
                            }
                            break;
                    }
                    if (this.ah == 2) {
                        i2 = 4905;
                    } else if (this.ah == 5) {
                        i2 = 4910;
                    } else if (this.ah == 6) {
                        i2 = 4911;
                    } else if (this.ah == 7) {
                        i2 = 4912;
                    }
                    this.U.a = i2;
                }
                if (TextUtils.isEmpty(str) || TextUtils.isEmpty(this.ak)) {
                    djm djm = this.U;
                    StringBuilder sb = new StringBuilder();
                    sb.append(str);
                    sb.append(this.ak);
                    djm.g = sb.toString();
                } else {
                    djm djm2 = this.U;
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(str);
                    sb2.append(MetaRecord.LOG_SEPARATOR);
                    sb2.append(this.ak);
                    djm2.g = sb2.toString();
                }
                if (this.aa != null) {
                    this.U.d = this.aa;
                } else {
                    this.U.d.x = Integer.MIN_VALUE;
                    this.U.d.y = Integer.MIN_VALUE;
                }
                final ArrayList arrayList = new ArrayList();
                arrayList.add(this.U);
                MapManager mapManager = getMapManager();
                if (mapManager != null) {
                    cfc.a().a(mapManager, (c) new c() {
                        public final void a(String str) {
                            String charSequence = NavigationErrorReportFragment.this.O.getText().toString();
                            PageBundle pageBundle = new PageBundle();
                            pageBundle.putString(H5ContactPlugin.CONTACT, charSequence);
                            pageBundle.putObject("line_error_list", arrayList);
                            pageBundle.putObject("startpoint", NavigationErrorReportFragment.this.a.getFromPOI());
                            pageBundle.putObject("endpoint", NavigationErrorReportFragment.this.a.getToPOI());
                            pageBundle.putString(H5ContactPlugin.CONTACT, charSequence);
                            pageBundle.putString(SocialConstants.PARAM_AVATAR_URI, str);
                            if (NavigationErrorReportFragment.this.a.getRouteNaviIdAllContainer() != null) {
                                pageBundle.putString("navi_id", DriveUtil.generateNaviIDString(NavigationErrorReportFragment.this.a.getRouteNaviIdAllContainer()));
                            }
                            String method = NavigationErrorReportFragment.this.a.getMethod();
                            if (DriveUtil.isAvoidLimitedPath()) {
                                StringBuilder sb = new StringBuilder();
                                sb.append(method);
                                sb.append("|1");
                                method = sb.toString();
                            }
                            pageBundle.putString("category", method);
                            if (NavigationErrorReportFragment.this.g != null && NavigationErrorReportFragment.this.g.size() > 0) {
                                pageBundle.putObject("midpoints", NavigationErrorReportFragment.this.g);
                            }
                            if (NavigationErrorReportFragment.this.c()) {
                                pageBundle.putString("sourcepage", "44");
                            } else if (DriveUtil.NAVI_TYPE_MOTORBIKE.equals(NavigationErrorReportFragment.this.V)) {
                                pageBundle.putString("sourcepage", "49");
                            } else {
                                pageBundle.putString("sourcepage", "6");
                            }
                            pageBundle.putString("navi_type", NavigationErrorReportFragment.this.V);
                            NavigationErrorReportFragment.this.startPageForResult(RouteCarErrorReportDialog.class, pageBundle, 256);
                        }
                    });
                }
            }
        }
    }

    private void b(int i2) {
        this.J.setVisibility(8);
        this.F.setSelected(false);
        this.K.setVisibility(8);
        this.G.setSelected(false);
        this.L.setVisibility(8);
        this.H.setSelected(false);
        this.M.setVisibility(8);
        this.I.setSelected(false);
        if (i2 == 1 && this.aj != 1) {
            this.J.setVisibility(0);
            this.F.setSelected(true);
            this.aj = i2;
        } else if (i2 == 2 && this.aj != 2) {
            this.K.setVisibility(0);
            this.G.setSelected(true);
            this.aj = i2;
        } else if (i2 == 3 && this.aj != 3) {
            this.L.setVisibility(0);
            this.H.setSelected(true);
            this.aj = i2;
        } else if (i2 != 4 || this.aj == 4) {
            this.aj = -1;
        } else {
            this.M.setVisibility(0);
            this.I.setSelected(true);
            this.aj = i2;
        }
        a(c(), this.ah, i2);
        if (this.ah == 1 || this.ah == 13) {
            l();
        }
        e();
    }

    private String k() {
        if (c()) {
            return "2";
        }
        return DriveUtil.NAVI_TYPE_MOTORBIKE.equals(this.V) ? "3" : "1";
    }

    private void a(boolean z2, int i2, int i3) {
        if (z2) {
            switch (i2) {
                case 8:
                case 9:
                    if (i3 != 3 || !this.H.isSelected()) {
                        this.x.setText(R.string.car_error_edit_detail_description);
                        return;
                    } else {
                        a(this.x, R.string.car_error_edit_detail_description);
                        return;
                    }
                case 10:
                case 12:
                case 13:
                    if (i3 != 4 || !this.I.isSelected()) {
                        this.x.setText(R.string.car_error_edit_detail_description);
                        break;
                    } else {
                        a(this.x, R.string.car_error_edit_detail_description);
                        return;
                    }
                    break;
            }
        }
    }

    private void c(int i2) {
        if (this.ai == -1) {
            this.ai = i2;
        } else {
            this.ai = this.ah;
        }
        this.ah = i2;
        if (this.U == null || this.U.b != this.ah) {
            this.U = new djm();
            this.U.g = "";
            this.U.d.x = Integer.MIN_VALUE;
            this.U.d.y = Integer.MIN_VALUE;
            this.U.a(getContext(), this.ah);
            if (DriveUtil.NAVI_TYPE_MOTORBIKE.equals(this.V)) {
                this.U.c = djs.b(this.ah);
            }
        }
    }

    public final void d() {
        if (this.U != null && this.j != null) {
            if (this.j.getSize() >= 0) {
                this.j.removeAll();
            }
            this.j.addItem(dff.a(this.U));
            this.al = true;
            if (getMapView() != null) {
                getMapView().R();
            }
        }
    }

    public final void a(int i2) {
        if (1 == i2 || this.U != null) {
            this.T = i2;
            switch (i2) {
                case 1:
                    this.D.setVisibility(0);
                    this.E.setVisibility(8);
                    this.r.setVisibility(8);
                    this.s.setVisibility(8);
                    this.t.setVisibility(8);
                    this.u.setVisibility(8);
                    this.j.removeAll();
                    this.aa = null;
                    this.p.setTitle(getResources().getString(R.string.car_error_title_bar_page_category));
                    this.U = null;
                    this.al = false;
                    b(-1);
                    this.ak = "";
                    String b2 = b();
                    if (!TextUtils.isEmpty(b2)) {
                        this.O.setText(b2);
                    } else {
                        this.O.setText("");
                    }
                    cde suspendManager = getSuspendManager();
                    if (suspendManager != null) {
                        cdz d2 = suspendManager.d();
                        if (d2 != null) {
                            d2.f();
                        }
                    }
                    f();
                    return;
                case 2:
                    this.D.setVisibility(8);
                    this.E.setVisibility(8);
                    this.r.setVisibility(0);
                    this.s.setVisibility(0);
                    this.t.setVisibility(8);
                    ((TextView) this.t.findViewById(R.id.car_error_tip_confirm_text)).setText(djs.b(getContext(), this.ah));
                    this.u.setVisibility(0);
                    if (this.ah == 1 || this.ah == 13) {
                        this.j.setVisible(false);
                    } else {
                        this.j.removeAll();
                        this.aa = null;
                    }
                    e();
                    n();
                    this.p.setTitle(djs.a(getContext(), this.ah));
                    return;
                case 3:
                    this.D.setVisibility(8);
                    this.E.setVisibility(8);
                    this.t.setVisibility(8);
                    this.u.setVisibility(8);
                    return;
                case 4:
                    this.D.setVisibility(8);
                    this.E.setVisibility(8);
                    this.t.setVisibility(0);
                    this.u.setVisibility(8);
                    return;
                case 5:
                    this.D.setVisibility(8);
                    this.E.setVisibility(0);
                    this.r.setVisibility(8);
                    this.s.setVisibility(8);
                    this.t.setVisibility(8);
                    this.u.setVisibility(8);
                    this.j.setVisible(true);
                    this.N.setText("");
                    m();
                    this.p.setTitle(getResources().getString(R.string.car_error_title_bar_page_edit_detail));
                    this.v.setText(djs.a(getContext(), this.ah));
                    this.N.setHint(djs.a(getContext(), this.ah, false));
                    this.N.setText(this.ak);
                    e();
                    cde suspendManager2 = getSuspendManager();
                    if (suspendManager2 != null) {
                        cdz d3 = suspendManager2.d();
                        if (d3 != null) {
                            d3.f();
                        }
                    }
                    f();
                    break;
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:4:0x001c, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void l() {
        /*
            r2 = this;
            int r0 = r2.aj
            r1 = -1
            if (r0 == r1) goto L_0x001d
            switch(r0) {
                case 1: goto L_0x001d;
                case 2: goto L_0x0009;
                case 3: goto L_0x001d;
                case 4: goto L_0x001d;
                default: goto L_0x0008;
            }
        L_0x0008:
            goto L_0x001c
        L_0x0009:
            android.view.View r0 = r2.o
            int r1 = com.autonavi.minimap.R.id.select_destination_position_layout
            android.view.View r0 = r0.findViewById(r1)
            r1 = 0
            r0.setVisibility(r1)
            android.widget.TextView r0 = r2.x
            int r1 = com.autonavi.minimap.R.string.car_error_edit_detail_description
            r0.setText(r1)
        L_0x001c:
            return
        L_0x001d:
            android.view.View r0 = r2.o
            int r1 = com.autonavi.minimap.R.id.select_destination_position_layout
            android.view.View r0 = r0.findViewById(r1)
            r1 = 8
            r0.setVisibility(r1)
            int r0 = r2.aj
            r1 = 4
            if (r0 != r1) goto L_0x0037
            android.widget.TextView r0 = r2.x
            int r1 = com.autonavi.minimap.R.string.car_error_edit_detail_description
            r2.a(r0, r1)
            return
        L_0x0037:
            android.widget.TextView r0 = r2.x
            int r1 = com.autonavi.minimap.R.string.car_error_edit_detail_description
            r0.setText(r1)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.drive.errorreport.NavigationErrorReportFragment.l():void");
    }

    private void m() {
        j();
        if (c()) {
            switch (this.ah) {
                case 8:
                    this.z.setVisibility(0);
                    this.w.setVisibility(8);
                    this.F.setText(R.string.car_error_radio_no_way);
                    this.G.setText(R.string.car_error_radio_do_work);
                    this.H.setText(R.string.car_error_radio_other);
                    this.I.setVisibility(8);
                    this.o.findViewById(R.id.select_destination_position_layout).setVisibility(8);
                    j();
                    return;
                case 9:
                    this.z.setVisibility(0);
                    this.w.setVisibility(8);
                    this.F.setText(R.string.car_error_radio_road_bad);
                    this.G.setText(R.string.car_error_radio_road_around);
                    this.H.setText(R.string.car_error_radio_other);
                    this.I.setVisibility(8);
                    this.o.findViewById(R.id.select_destination_position_layout).setVisibility(8);
                    j();
                    return;
                case 10:
                    this.z.setVisibility(0);
                    this.w.setVisibility(8);
                    this.F.setText(R.string.car_error_radio_limit_pass);
                    this.G.setText(R.string.car_error_radio_limit_width_height_weight_autonavi);
                    this.H.setText(R.string.car_error_radio_play_content_error_autonavi);
                    this.I.setText(R.string.car_error_radio_other);
                    this.I.setVisibility(0);
                    this.o.findViewById(R.id.select_destination_position_layout).setVisibility(8);
                    j();
                    return;
                case 12:
                    this.z.setVisibility(0);
                    this.w.setVisibility(8);
                    this.F.setText(R.string.car_error_radio_monitor_miss_error_autonavi);
                    this.G.setText(R.string.car_error_radio_monitor_redundant_error_autonavi);
                    this.H.setText(R.string.car_error_radio_speed_limit_error);
                    this.I.setText(R.string.car_error_radio_other);
                    this.I.setVisibility(0);
                    this.o.findViewById(R.id.select_destination_position_layout).setVisibility(8);
                    j();
                    return;
                case 13:
                    this.w.setVisibility(0);
                    this.z.setVisibility(0);
                    this.F.setText(R.string.car_error_radio_closed);
                    this.G.setText(R.string.car_error_radio_location_error);
                    this.H.setText(R.string.car_error_radio_not_find);
                    this.I.setText(R.string.car_error_radio_other);
                    this.I.setVisibility(0);
                    l();
                    if (this.al) {
                        this.B.setText(getResources().getString(R.string.car_error_edit_detail_destination_reselect));
                    } else {
                        this.B.setText(getResources().getString(R.string.car_error_edit_detail_destination_select));
                    }
                    a(this.y, R.string.car_error_edit_detail_phonenumber);
                    return;
                default:
                    return;
            }
        } else if (this.ah == 1) {
            this.w.setVisibility(0);
            this.z.setVisibility(0);
            this.F.setText(R.string.car_error_radio_closed);
            this.G.setText(R.string.car_error_radio_location_error);
            this.H.setText(R.string.car_error_radio_not_find);
            this.I.setText(R.string.car_error_radio_other_issue);
            l();
            if (this.al) {
                this.B.setText(getResources().getString(R.string.car_error_edit_detail_destination_reselect));
            } else {
                this.B.setText(getResources().getString(R.string.car_error_edit_detail_destination_select));
            }
            a(this.y, R.string.car_error_edit_detail_phonenumber);
        } else {
            this.w.setVisibility(8);
            if (this.ah == 3) {
                this.z.setVisibility(0);
                this.F.setText(R.string.car_error_radio_do_work);
                this.G.setText(R.string.car_error_radio_single_direction);
                this.H.setText(R.string.car_error_radio_no_turn);
                this.I.setText(R.string.car_error_radio_no_way);
            } else {
                this.z.setVisibility(8);
            }
            this.o.findViewById(R.id.select_destination_position_layout).setVisibility(8);
            j();
        }
    }

    private void n() {
        if (this.u != null) {
            this.u.setText(DriveUtil.NAVI_TYPE_MOTORBIKE.equals(this.V) ? djs.d(getContext(), this.ah) : djs.c(getContext(), this.ah));
        }
    }

    public final void e() {
        if (this.P != null) {
            if (!o()) {
                this.P.setBackgroundDrawable(getResources().getDrawable(R.drawable.car_error_submit_invalid_bg));
                this.P.setTextColor(getResources().getColor(R.color.f_c_1_a));
                return;
            }
            this.P.setBackgroundDrawable(getResources().getDrawable(R.drawable.car_error_submit_selector));
            this.P.setTextColor(getResources().getColor(R.color.f_c_1));
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:68:0x00f1 A[RETURN] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean o() {
        /*
            r6 = this;
            djm r0 = r6.U
            r1 = 0
            if (r0 != 0) goto L_0x0006
            return r1
        L_0x0006:
            int r0 = r6.ah
            r2 = -1
            r3 = 5
            r4 = 1
            switch(r0) {
                case 1: goto L_0x00f3;
                case 2: goto L_0x00d4;
                case 3: goto L_0x00b2;
                case 4: goto L_0x000e;
                case 5: goto L_0x00d4;
                case 6: goto L_0x00d4;
                case 7: goto L_0x00d4;
                case 8: goto L_0x004c;
                case 9: goto L_0x004c;
                case 10: goto L_0x004c;
                case 11: goto L_0x000e;
                case 12: goto L_0x004c;
                case 13: goto L_0x000f;
                default: goto L_0x000e;
            }
        L_0x000e:
            return r1
        L_0x000f:
            android.widget.TextView r0 = r6.O
            java.lang.CharSequence r0 = r0.getText()
            android.widget.TextView r2 = r6.N
            java.lang.CharSequence r2 = r2.getText()
            if (r0 == 0) goto L_0x002f
            int r3 = r0.length()
            if (r3 <= 0) goto L_0x002f
            java.lang.String r0 = r0.toString()
            boolean r0 = a(r0)
            if (r0 == 0) goto L_0x002f
            r0 = 1
            goto L_0x0030
        L_0x002f:
            r0 = 0
        L_0x0030:
            int r3 = r6.aj
            switch(r3) {
                case 1: goto L_0x0048;
                case 2: goto L_0x0040;
                case 3: goto L_0x0048;
                case 4: goto L_0x0036;
                default: goto L_0x0035;
            }
        L_0x0035:
            return r1
        L_0x0036:
            if (r0 == 0) goto L_0x003f
            boolean r0 = b(r2)
            if (r0 == 0) goto L_0x003f
            return r4
        L_0x003f:
            return r1
        L_0x0040:
            com.autonavi.common.model.GeoPoint r2 = r6.aa
            if (r2 == 0) goto L_0x0047
            if (r0 == 0) goto L_0x0047
            return r4
        L_0x0047:
            return r1
        L_0x0048:
            if (r0 == 0) goto L_0x004b
            return r4
        L_0x004b:
            return r1
        L_0x004c:
            android.widget.TextView r0 = r6.x
            java.lang.CharSequence r0 = r0.getText()
            java.lang.String r0 = r0.toString()
            java.lang.String r5 = "＊"
            boolean r0 = r0.contains(r5)
            if (r0 == 0) goto L_0x007c
            android.widget.TextView r0 = r6.N
            java.lang.CharSequence r0 = r0.getText()
            if (r0 == 0) goto L_0x00f1
            java.lang.String r2 = r0.toString()
            boolean r2 = android.text.TextUtils.isEmpty(r2)
            if (r2 != 0) goto L_0x00f1
            java.lang.String r0 = r0.toString()
            int r0 = r0.length()
            if (r0 < r3) goto L_0x00f1
            return r4
        L_0x007c:
            android.widget.TextView r0 = r6.N
            java.lang.CharSequence r0 = r0.getText()
            int r5 = r6.aj
            if (r5 == r2) goto L_0x009d
            if (r0 == 0) goto L_0x009c
            java.lang.String r2 = r0.toString()
            boolean r2 = android.text.TextUtils.isEmpty(r2)
            if (r2 != 0) goto L_0x009c
            java.lang.String r0 = r0.toString()
            int r0 = r0.length()
            if (r0 < r3) goto L_0x00f1
        L_0x009c:
            return r4
        L_0x009d:
            java.lang.String r2 = r0.toString()
            boolean r2 = android.text.TextUtils.isEmpty(r2)
            if (r2 != 0) goto L_0x00f1
            java.lang.String r0 = r0.toString()
            int r0 = r0.length()
            if (r0 < r3) goto L_0x00f1
            return r4
        L_0x00b2:
            int r0 = r6.aj
            if (r0 == r2) goto L_0x00b7
            return r4
        L_0x00b7:
            android.widget.TextView r0 = r6.N
            java.lang.CharSequence r0 = r0.getText()
            if (r0 == 0) goto L_0x00f1
            java.lang.String r2 = r0.toString()
            boolean r2 = android.text.TextUtils.isEmpty(r2)
            if (r2 != 0) goto L_0x00f1
            java.lang.String r0 = r0.toString()
            int r0 = r0.length()
            if (r0 < r3) goto L_0x00f1
            return r4
        L_0x00d4:
            android.widget.TextView r0 = r6.N
            java.lang.CharSequence r0 = r0.getText()
            if (r0 == 0) goto L_0x00f2
            java.lang.String r2 = r0.toString()
            boolean r2 = android.text.TextUtils.isEmpty(r2)
            if (r2 != 0) goto L_0x00f2
            java.lang.String r0 = r0.toString()
            int r0 = r0.length()
            if (r0 < r3) goto L_0x00f1
            goto L_0x00f2
        L_0x00f1:
            return r1
        L_0x00f2:
            return r4
        L_0x00f3:
            android.widget.TextView r0 = r6.O
            java.lang.CharSequence r0 = r0.getText()
            android.widget.TextView r2 = r6.N
            java.lang.CharSequence r2 = r2.getText()
            if (r0 == 0) goto L_0x0113
            int r3 = r0.length()
            if (r3 <= 0) goto L_0x0113
            java.lang.String r0 = r0.toString()
            boolean r0 = a(r0)
            if (r0 == 0) goto L_0x0113
            r0 = 1
            goto L_0x0114
        L_0x0113:
            r0 = 0
        L_0x0114:
            int r3 = r6.aj
            switch(r3) {
                case 1: goto L_0x012c;
                case 2: goto L_0x0124;
                case 3: goto L_0x012c;
                case 4: goto L_0x011a;
                default: goto L_0x0119;
            }
        L_0x0119:
            return r1
        L_0x011a:
            if (r0 == 0) goto L_0x0123
            boolean r0 = b(r2)
            if (r0 == 0) goto L_0x0123
            return r4
        L_0x0123:
            return r1
        L_0x0124:
            com.autonavi.common.model.GeoPoint r2 = r6.aa
            if (r2 == 0) goto L_0x012b
            if (r0 == 0) goto L_0x012b
            return r4
        L_0x012b:
            return r1
        L_0x012c:
            if (r0 == 0) goto L_0x012f
            return r4
        L_0x012f:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.drive.errorreport.NavigationErrorReportFragment.o():boolean");
    }

    private static boolean a(CharSequence charSequence) {
        if (TextUtils.isEmpty(charSequence)) {
            return false;
        }
        for (int i2 = 0; i2 < charSequence.length(); i2++) {
            if (!Character.isDigit(charSequence.charAt(i2))) {
                return false;
            }
        }
        return true;
    }

    private static boolean b(CharSequence charSequence) {
        return !TextUtils.isEmpty(charSequence) && charSequence.length() >= 5;
    }

    public final void f() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        a(displayMetrics.widthPixels, displayMetrics.heightPixels - ags.d(getContext()));
    }

    /* access modifiers changed from: private */
    public void a(int i2, int i3) {
        int i4;
        int i5;
        int i6;
        int i7;
        if (this.i != null && this.i.getBound() != null) {
            int a2 = agn.a(getContext(), 51.0f);
            int a3 = agn.a(getContext(), 51.0f);
            if (this.p == null || this.p.getVisibility() != 0) {
                i5 = 0;
                i4 = 0;
            } else {
                if (this.p.getMeasuredHeight() <= 0) {
                    this.p.measure(0, 0);
                }
                i5 = this.p.getMeasuredHeight() + 0;
                i4 = this.p.getMeasuredHeight() + 0;
            }
            int intrinsicHeight = i5 + getResources().getDrawable(R.drawable.bubble_start).getIntrinsicHeight() + agn.a(getContext(), 8.0f);
            if (this.C == null || this.C.getVisibility() != 0) {
                i7 = 0;
                i6 = 0;
            } else {
                if (this.C.getMeasuredHeight() <= 0) {
                    this.C.measure(0, 0);
                }
                i7 = this.C.getMeasuredHeight() + 0;
                i6 = this.C.getMeasuredHeight() + 0;
            }
            Rect rect = new Rect(this.i.getBound());
            rect.union(this.k.acquireBounds());
            int i8 = i2;
            int i9 = i3;
            defpackage.dif.a a4 = new defpackage.dif.a().a(rect, a2 + agn.a(getContext(), (float) am[0]), intrinsicHeight + agn.a(getContext(), (float) am[1]), a3 + agn.a(getContext(), (float) am[2]), i7 + getResources().getDrawable(R.drawable.bubble_end).getIntrinsicHeight() + agn.a(getContext(), 8.0f) + agn.a(getContext(), (float) am[3])).a(getMapManager().getMapView(), i8, i9, i2 / 2, (((i3 - i4) - i6) / 2) + i4);
            a4.f = 0;
            a4.a().a();
        }
    }

    public final void g() {
        if (this.i != null && this.c != null) {
            this.i.removeAll();
            for (int i2 = 0; i2 < this.c.size(); i2++) {
                this.i.addItem((LineOverlayItem) this.c.get(i2));
            }
            if (this.R) {
                this.i.addItem((LineOverlayItem) this.d);
            }
        }
    }

    public final void h() {
        if (this.k != null) {
            this.k.removeAll();
            if (this.Q) {
                int size = this.h.size();
                for (int i2 = 0; i2 < size; i2++) {
                    this.k.addItem(ErrorReportRoutePointItem.a(RoutePointType.OFF_ROUTE, this.h.get(i2)));
                }
            }
            ArrayList arrayList = new ArrayList();
            if (this.g != null && this.g.size() > 0) {
                Iterator<POI> it = this.g.iterator();
                while (it.hasNext()) {
                    arrayList.add(it.next().getPoint());
                }
            }
            if (arrayList.size() > 0) {
                int size2 = arrayList.size();
                if (size2 == 1) {
                    this.k.addItem(ErrorReportRoutePointItem.a(RoutePointType.MID, (GeoPoint) arrayList.get(0)));
                } else {
                    for (int i3 = 0; i3 < size2; i3++) {
                        this.k.addItem(ErrorReportRoutePointItem.a((GeoPoint) arrayList.get(i3), i3));
                    }
                }
            }
            this.k.addItem(ErrorReportRoutePointItem.a(RoutePointType.START, this.e));
            this.k.addItem(ErrorReportRoutePointItem.a(RoutePointType.END, this.f));
        }
    }

    private static void a(String str, String str2, String str3) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("type", str);
            jSONObject.put(TrafficUtil.KEYWORD, str2);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        LogManager.actionLogV2("P00073", str3, jSONObject);
    }

    public static String b() {
        IAccountService iAccountService = (IAccountService) defpackage.esb.a.a.a(IAccountService.class);
        if (iAccountService == null) {
            return "";
        }
        ant e2 = iAccountService.e();
        if (e2 == null) {
            return "";
        }
        return e2.h;
    }

    static /* synthetic */ void a(NavigationErrorReportFragment navigationErrorReportFragment) {
        if ((3 == navigationErrorReportFragment.T || navigationErrorReportFragment.T == 4) && navigationErrorReportFragment.U != null && navigationErrorReportFragment.getMapManager() != null && navigationErrorReportFragment.getMapManager().getMapView() != null) {
            navigationErrorReportFragment.Y.post(new Runnable() {
                public final void run() {
                    if (NavigationErrorReportFragment.this.r != null && NavigationErrorReportFragment.this.ab != null) {
                        NavigationErrorReportFragment.this.a(4);
                        NavigationErrorReportFragment.this.r.clearAnimation();
                        NavigationErrorReportFragment.this.r.startAnimation(NavigationErrorReportFragment.this.ab);
                        NavigationErrorReportFragment.this.ab.startNow();
                    }
                }
            });
            final int left = (navigationErrorReportFragment.s.getLeft() + navigationErrorReportFragment.s.getRight()) / 2;
            final int top = (navigationErrorReportFragment.s.getTop() + navigationErrorReportFragment.s.getBottom()) / 2;
            GeoPoint geoPoint = new GeoPoint(navigationErrorReportFragment.getMapManager().getMapView().c(left, top));
            if (navigationErrorReportFragment.U.a()) {
                djo djo = new djo();
                AnonymousClass1 r5 = new defpackage.djo.a() {
                    public final void a(final GeoPoint geoPoint) {
                        if (geoPoint != null && geoPoint.x != 0 && geoPoint.y != 0 && NavigationErrorReportFragment.this.U != null) {
                            NavigationErrorReportFragment.this.Y.post(new Runnable() {
                                public final void run() {
                                    bty mapView = NavigationErrorReportFragment.this.getMapManager() == null ? null : NavigationErrorReportFragment.this.getMapManager().getMapView();
                                    if (mapView != null) {
                                        mapView.b(geoPoint.x, geoPoint.y, left, top);
                                    }
                                }
                            });
                            NavigationErrorReportFragment.this.U.d = geoPoint;
                        }
                    }
                };
                if (djo.a != null) {
                    djo.a.add(r5);
                }
                ArrayList arrayList = new ArrayList();
                arrayList.add(geoPoint);
                djo.execute(new List[]{navigationErrorReportFragment.af, arrayList});
                return;
            }
            navigationErrorReportFragment.U.d = geoPoint;
        }
    }

    static /* synthetic */ void a(NavigationErrorReportFragment navigationErrorReportFragment, String str) {
        if (TextUtils.isEmpty(str)) {
            str = "";
        }
        navigationErrorReportFragment.O.setText(str);
        navigationErrorReportFragment.e();
        navigationErrorReportFragment.E.clearAnimation();
        navigationErrorReportFragment.E.setVisibility(0);
        navigationErrorReportFragment.X = null;
    }

    static /* synthetic */ void b(NavigationErrorReportFragment navigationErrorReportFragment, String str) {
        navigationErrorReportFragment.ak = str;
        if (TextUtils.isEmpty(str)) {
            navigationErrorReportFragment.N.setText("");
        } else {
            navigationErrorReportFragment.N.setText(str);
        }
        navigationErrorReportFragment.e();
        navigationErrorReportFragment.E.clearAnimation();
        navigationErrorReportFragment.E.setVisibility(0);
        navigationErrorReportFragment.X = null;
    }
}
