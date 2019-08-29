package com.autonavi.minimap.basemap.route.page;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import com.amap.bundle.statistics.LogManager;
import com.amap.bundle.statistics.util.LogUtil;
import com.autonavi.annotation.PageAction;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.map.db.model.Car;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.map.fragmentcontainer.page.IPresenter;
import com.autonavi.miniapp.plugin.carowner.CarOwnerHelper;
import com.autonavi.minimap.R;
import com.autonavi.minimap.ajx3.Ajx3Page;
import com.autonavi.minimap.ajx3.CarInfo;
import com.autonavi.minimap.ajx3.modules.ModuleHistory;
import com.autonavi.minimap.basemap.route.external.zxingwrapper.decoding.CapturePageHandler;
import com.autonavi.minimap.basemap.route.external.zxingwrapper.view.ViewfinderView;
import com.autonavi.minimap.onekeycheck.module.UploadDataResult;
import com.autonavi.widget.ui.AlertView;
import com.autonavi.widget.ui.AlertView.a;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import org.json.JSONException;
import org.json.JSONObject;

@PageAction("amap.basemap.action.carowner_license_scan_page")
public class CarLicenseScanPage extends TitleBarPage<cqu> implements Callback, cqr<Boolean> {
    private static final String m = CarLicenseScanPage.class.getName().toString();
    protected View a = null;
    public CapturePageHandler b;
    public ViewfinderView c;
    public SurfaceView d;
    public boolean e = false;
    Car f = new Car();
    public int g = 0;
    public int h = 0;
    public boolean i = true;
    public boolean j = true;
    public Handler k = new Handler() {
        public final void handleMessage(Message message) {
            switch (message.what) {
                case 1:
                    CarLicenseScanPage.e(CarLicenseScanPage.this);
                    return;
                case 2:
                    if (CarLicenseScanPage.this.i) {
                        CarLicenseScanPage carLicenseScanPage = CarLicenseScanPage.this;
                        JSONObject jSONObject = (JSONObject) message.obj;
                        carLicenseScanPage.g++;
                        if (jSONObject == null || !jSONObject.optBoolean("success") || carLicenseScanPage.f == null) {
                            cqu.a((String) UploadDataResult.FAIL_MSG);
                            if (carLicenseScanPage.g >= 3) {
                                carLicenseScanPage.c.setHintText(carLicenseScanPage.getResources().getString(R.string.car_license_scan_hint_scan_failed));
                                carLicenseScanPage.c.invalidate();
                                carLicenseScanPage.h++;
                                a aVar = new a(carLicenseScanPage.getContext());
                                aVar.a(R.string.car_license_scan_prompt_scan_failed);
                                a b = aVar.b(R.string.car_license_scan_hint_continue_scan_question).a(R.string.car_license_scan_prompt_continue_scan, (a) new a() {
                                    public final void onClick(AlertView alertView, int i) {
                                        CarLicenseScanPage.this.mPresenter;
                                        cqu.b((String) "继续扫描");
                                        CarLicenseScanPage.this.c.postDelayed(new Runnable() {
                                            public final void run() {
                                                CarLicenseScanPage.this.g = 0;
                                                CarLicenseScanPage.this.c.setHintText("");
                                                if (CarLicenseScanPage.this.b != null) {
                                                    CarLicenseScanPage.this.b.b();
                                                }
                                            }
                                        }, 1000);
                                        CarLicenseScanPage.this.dismissViewLayer(alertView);
                                    }
                                }).b(R.string.car_license_scan_prompt_cancel_scan, (a) new a() {
                                    public final void onClick(AlertView alertView, int i) {
                                        CarLicenseScanPage.this.mPresenter;
                                        cqu.b((String) "手动填写");
                                        CarLicenseScanPage.this.dismissViewLayer(alertView);
                                        ((cqu) CarLicenseScanPage.this.mPresenter).a();
                                        CarLicenseScanPage.this.a(true);
                                    }
                                });
                                b.b = new a() {
                                    public final void onClick(AlertView alertView, int i) {
                                    }
                                };
                                b.c = new a() {
                                    public final void onClick(AlertView alertView, int i) {
                                    }
                                };
                                b.a(false);
                                AlertView a2 = aVar.a();
                                carLicenseScanPage.showViewLayer(a2);
                                a2.startAnimation();
                                return;
                            }
                            carLicenseScanPage.f();
                            carLicenseScanPage.c.post(new Runnable() {
                                public final void run() {
                                    CarLicenseScanPage.this.c.setHintText("");
                                    if (CarLicenseScanPage.this.b != null) {
                                        CarLicenseScanPage.this.b.b();
                                    }
                                }
                            });
                            return;
                        }
                        carLicenseScanPage.f.engineNum = jSONObject.optString("engine_num");
                        carLicenseScanPage.f.frameNum = jSONObject.optString(CarOwnerHelper.OPTIONAL_ITEM_VIN);
                        carLicenseScanPage.f.ocrRequestId = jSONObject.optString("request_id");
                        if (!TextUtils.isEmpty(carLicenseScanPage.f.engineNum) && !TextUtils.isEmpty(carLicenseScanPage.f.frameNum)) {
                            cqu.a((String) "全");
                            cqu.a(carLicenseScanPage.f.frameNum, carLicenseScanPage.f.engineNum);
                            carLicenseScanPage.c.setHintText(carLicenseScanPage.getResources().getString(R.string.car_license_scan_hint_scan_success));
                            carLicenseScanPage.c.setFrameNumber(carLicenseScanPage.f.frameNum);
                            carLicenseScanPage.c.setEngineNumber(carLicenseScanPage.f.engineNum);
                            carLicenseScanPage.c.invalidate();
                            carLicenseScanPage.k.sendEmptyMessageDelayed(3, 1000);
                            return;
                        } else if (!TextUtils.isEmpty(carLicenseScanPage.f.engineNum)) {
                            cqu.a((String) "发动机号");
                            return;
                        } else if (!TextUtils.isEmpty(carLicenseScanPage.f.frameNum)) {
                            cqu.a((String) "车架号");
                            return;
                        } else {
                            cqu.a((String) "空");
                            return;
                        }
                    }
                    break;
                case 3:
                    if (CarLicenseScanPage.this.i) {
                        CarLicenseScanPage.this.a(false);
                        return;
                    }
                    break;
                case 4:
                    if (CarLicenseScanPage.this.i) {
                        CarLicenseScanPage.this.j = false;
                        CarLicenseScanPage.this.d();
                        break;
                    }
                    break;
            }
        }
    };
    private Timer n;
    private Timer o;
    private final int p = 500;
    private final int q = 80;
    private CarInfo r = new CarInfo();

    public void surfaceChanged(SurfaceHolder surfaceHolder, int i2, int i3, int i4) {
    }

    public void onCreate(Context context) {
        super.onCreate(context);
        cqw a2 = cqw.a();
        String str = m;
        if (a2.a == null) {
            a2.a = new ArrayList();
        }
        if (a2.a != null) {
            int size = a2.a.size();
            if (size == 0) {
                a2.a(str, this);
            } else if (a2.a(str, this, size)) {
                a2.a(str, this);
            }
        }
    }

    public static void a() {
        cqw a2 = cqw.a();
        String str = m;
        if (a2.b) {
            int i2 = 0;
            if (a2.a != null && a2.a.size() > 0) {
                int size = a2.a.size();
                cqs cqs = null;
                while (true) {
                    if (i2 >= size) {
                        break;
                    }
                    cqs cqs2 = a2.a.get(i2);
                    if (cqs2.a.equals(str)) {
                        cqs = cqs2;
                        break;
                    }
                    i2++;
                }
                a2.a.remove(cqs);
            }
        }
    }

    /* access modifiers changed from: protected */
    public final int b() {
        return R.layout.car_license_scan_fragment;
    }

    public static void c() {
        DoNotUseTool.getMapManager().getMapView().d(0);
        DoNotUseTool.getMapManager().getMapView().N();
    }

    public final void a(PageBundle pageBundle) {
        if (pageBundle != null) {
            if (pageBundle.containsKey("carInfo")) {
                this.r = (CarInfo) pageBundle.getObject("carInfo");
                this.f = this.r.car;
            }
            DoNotUseTool.getMapManager().getMapView().d(8);
            cqm.a((Context) getActivity().getApplication());
            a(getResources().getString(R.string.car_license_scan_add_car_title));
            if (this.l != null) {
                this.l.setActionImgVisibility(8);
            }
            this.c = (ViewfinderView) findViewById(R.id.car_scan_viewfinder_view);
            ((TextView) findViewById(R.id.tvInputLicenseDetail)).setOnClickListener(new OnClickListener() {
                public final void onClick(View view) {
                    cqu cqu = (cqu) CarLicenseScanPage.this.mPresenter;
                    JSONObject jSONObject = new JSONObject();
                    try {
                        jSONObject.put("status", ((CarLicenseScanPage) cqu.mPage).h);
                        LogUtil.actionLogV2("P00224", "B001", jSONObject);
                    } catch (JSONException unused) {
                    }
                    ((cqu) CarLicenseScanPage.this.mPresenter).a();
                    CarLicenseScanPage.this.a(true);
                }
            });
            this.c.setHintText(getResources().getString(R.string.car_license_scan_hint_align_with_frame));
            this.d = (SurfaceView) findViewById(R.id.car_scan_preview_view);
            this.d.setZOrderMediaOverlay(true);
            this.c.setShowDefaultLicense(true);
            this.c.setDefaultLicenseAlpha(255);
            requestScreenOrientation(1);
        }
    }

    /* access modifiers changed from: private */
    public void a(boolean z) {
        this.r.car = this.f;
        if ("edit".equals(this.r.fromPage)) {
            ajw ajw = (ajw) ank.a(ajw.class);
            if (ajw != null) {
                PageBundle pageBundle = new PageBundle();
                pageBundle.putString(ModuleHistory.AJX_BACK_RETURN_DATA_KEY, CarInfo.CarInfoToJson(this.r).toString());
                ArrayList arrayList = new ArrayList();
                arrayList.add("amapuri://carownerservice/editcar");
                ajw.a(arrayList, this, ResultType.OK, pageBundle);
            }
            finish();
            return;
        }
        cqu cqu = (cqu) this.mPresenter;
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("status", ((CarLicenseScanPage) cqu.mPage).h);
            LogUtil.actionLogV2("P00224", "B004", jSONObject);
        } catch (JSONException unused) {
        }
        PageBundle pageBundle2 = new PageBundle();
        if (z) {
            this.r.addLicenseManually = "1";
        } else {
            this.r.addLicenseManually = "0";
        }
        pageBundle2.putString("url", "path://amap_bundle_carowner/src/car_owner/CarLicenceController.page.js");
        pageBundle2.putString("jsData", CarInfo.CarInfoToJson(this.r).toString());
        startPageForResult(Ajx3Page.class, pageBundle2, 1002);
    }

    public final void d() {
        e();
        a(true);
    }

    public final void e() {
        if (this.b != null) {
            this.b.a();
            this.b = null;
        }
        if (cqm.a() != null) {
            cqm.a().b();
        }
        if (this.n != null) {
            this.n.cancel();
            this.n.purge();
            this.n = null;
        }
        if (this.o != null) {
            this.o.cancel();
            this.o.purge();
            this.o = null;
        }
        this.c.cleanUp();
    }

    public final boolean a(SurfaceHolder surfaceHolder) {
        try {
            cqm.a().a(surfaceHolder);
            if (this.b == null) {
                this.b = new CapturePageHandler(this);
            }
            this.n = new Timer("CarLicenseScanPage.Timer");
            this.n.schedule(new TimerTask() {
                public final void run() {
                    CarLicenseScanPage.c(CarLicenseScanPage.this);
                }
            }, 500);
            return true;
        } catch (IOException unused) {
            return false;
        } catch (RuntimeException unused2) {
            return false;
        }
    }

    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        if (!this.e) {
            this.e = true;
            if (!a(surfaceHolder)) {
                this.k.sendEmptyMessage(4);
            }
        }
    }

    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        this.e = false;
    }

    public final void f() {
        if (isAlive()) {
            this.c.setHintText(getResources().getString(R.string.car_license_scan_hint_adjust_position_or_light));
        }
    }

    /* access modifiers changed from: protected */
    public final void g() {
        LogManager.actionLogV2("P00224", "B007");
        finish();
    }

    public final void a(Object obj) {
        Message obtainMessage = this.k.obtainMessage();
        obtainMessage.what = 2;
        obtainMessage.obj = obj;
        this.k.sendMessage(obtainMessage);
    }

    public /* synthetic */ IPresenter createPresenter() {
        return new cqu(this);
    }

    static /* synthetic */ void c(CarLicenseScanPage carLicenseScanPage) {
        try {
            carLicenseScanPage.o = new Timer("CarLicenseScanPage.Timer2");
            carLicenseScanPage.o.schedule(new TimerTask() {
                public final void run() {
                    Message obtainMessage = CarLicenseScanPage.this.k.obtainMessage();
                    obtainMessage.what = 1;
                    CarLicenseScanPage.this.k.sendMessage(obtainMessage);
                }
            }, 0, 80);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    static /* synthetic */ void e(CarLicenseScanPage carLicenseScanPage) {
        int defaultLicenseAlpha = carLicenseScanPage.c.getDefaultLicenseAlpha();
        if (defaultLicenseAlpha > 0) {
            defaultLicenseAlpha -= 20;
        }
        carLicenseScanPage.c.setShowDefaultLicense(defaultLicenseAlpha > 0);
        carLicenseScanPage.c.setDefaultLicenseAlpha(defaultLicenseAlpha);
        carLicenseScanPage.c.drawViewfinder();
        if (defaultLicenseAlpha <= 0 && carLicenseScanPage.o != null) {
            carLicenseScanPage.o.cancel();
            carLicenseScanPage.o.purge();
            carLicenseScanPage.o = null;
        }
    }
}
