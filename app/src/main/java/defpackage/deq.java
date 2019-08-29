package defpackage;

import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import com.amap.bundle.drivecommon.tools.DriveSpUtil;
import com.amap.bundle.drivecommon.tools.DriveUtil;
import com.amap.bundle.tripgroup.api.IAutoRemoteController;
import com.amap.bundle.utils.ui.NoDBClickUtil;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.bundle.banner.view.DBanner.BannerListener;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.POI;
import com.autonavi.common.utils.Constant.SelectPoiFromMapFragment.SelectFor;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;
import com.autonavi.minimap.basemap.traffic.TrafficUtil;
import com.autonavi.minimap.drive.auto.model.RemoteControlModel;
import com.autonavi.minimap.drive.auto.page.RemoteControlFragment;
import com.autonavi.minimap.drive.quicknaviwidget.RemoteControlFragmentHistoryView;
import com.autonavi.minimap.drive.quicknaviwidget.RemoteControlFragmentHistoryView.a;
import com.autonavi.minimap.drive.quicknaviwidget.RemoteControlFragmentHistoryView.b;
import com.autonavi.widget.ui.TitleBar;
import com.iflytek.tts.TtsService.Tts;

/* renamed from: deq reason: default package */
/* compiled from: RemoteControlPresenter */
public final class deq extends sw<RemoteControlFragment, RemoteControlModel> {
    public deq(RemoteControlFragment remoteControlFragment) {
        super(remoteControlFragment);
    }

    public final void onPageCreated() {
        RemoteControlFragment remoteControlFragment = (RemoteControlFragment) this.mPage;
        remoteControlFragment.d = (RemoteControlFragmentHistoryView) remoteControlFragment.getContentView().findViewById(R.id.quicknavi_fragment_history);
        remoteControlFragment.c = remoteControlFragment.d.getRouteCustomAddressView();
        remoteControlFragment.e = remoteControlFragment.d.getDbBanner();
        remoteControlFragment.f = remoteControlFragment.d.getDbContainerView();
        remoteControlFragment.e.initQuickAutonaviBanner(false, new BannerListener() {
            public final void onFinish(boolean z) {
                if (z) {
                    RemoteControlFragment.this.e.setVisibility(0);
                    RemoteControlFragment.this.f.setVisibility(0);
                    return;
                }
                RemoteControlFragment.this.e.setVisibility(8);
                RemoteControlFragment.this.f.setVisibility(8);
            }
        });
        remoteControlFragment.k = (TitleBar) remoteControlFragment.getContentView().findViewById(R.id.title);
        remoteControlFragment.k.setOnBackClickListener((OnClickListener) new OnClickListener() {
            public final void onClick(View view) {
                RemoteControlFragment.this.c.cancelTmcRequest();
                RemoteControlFragment.this.finish();
            }
        });
        IAutoRemoteController iAutoRemoteController = (IAutoRemoteController) a.a.a(IAutoRemoteController.class);
        if (iAutoRemoteController != null) {
            iAutoRemoteController.setRemoteControlConnectListener(remoteControlFragment.n);
        }
        RemoteControlModel remoteControlModel = (RemoteControlModel) this.b;
        PageBundle arguments = ((RemoteControlFragment) this.mPage).getArguments();
        remoteControlModel.c = fbl.a(AMapPageUtil.getAppContext());
        if (arguments != null && !arguments.isEmpty()) {
            remoteControlModel.d = arguments.getString(TrafficUtil.KEYWORD);
            remoteControlModel.e = arguments.getBoolean("isWifiConnection", false);
        }
    }

    public final void onStart() {
        RemoteControlFragment remoteControlFragment = (RemoteControlFragment) this.mPage;
        if (remoteControlFragment.getContentView() != null) {
            TextView textView = (TextView) remoteControlFragment.getContentView().findViewById(R.id.quick_autonavi);
            if (textView != null) {
                NoDBClickUtil.a((View) textView, remoteControlFragment.m);
            }
        }
        remoteControlFragment.b = !DriveSpUtil.getSearchRouteInNetMode(remoteControlFragment.getContext());
        remoteControlFragment.d.setOnRouteHistoryClickListener(new b() {
            public final void a(sj sjVar) {
                if (sjVar != null && sjVar.a() != null) {
                    RemoteControlFragment.this.d.setEnabled(false);
                    Message message = new Message();
                    message.obj = sjVar.a();
                    message.what = 200;
                    RemoteControlFragment.this.o.sendMessageDelayed(message, 800);
                }
            }
        });
        remoteControlFragment.c.setOnRouteHomeAddressClickListener(remoteControlFragment.p);
        remoteControlFragment.d.setOnCategoryClickListener(new a() {
            public final void a() {
                RemoteControlFragment.a(RemoteControlFragment.this, 1, RemoteControlFragment.this.getString(R.string.navi_gas_station), Tts.TTS_STATE_DESTROY, RemoteControlFragment.this.getString(R.string.act_fromto_dest_input_hint), false, SelectFor.DEFAULT_POI, true);
            }

            public final void b() {
                RemoteControlFragment.a(RemoteControlFragment.this, 1, RemoteControlFragment.this.getString(R.string.car_scene_parking), Tts.TTS_STATE_DESTROY, RemoteControlFragment.this.getString(R.string.act_fromto_dest_input_hint), false, SelectFor.DEFAULT_POI, true);
            }

            public final void c() {
                RemoteControlFragment.a(RemoteControlFragment.this, 1, RemoteControlFragment.this.getString(R.string.toilet), Tts.TTS_STATE_DESTROY, RemoteControlFragment.this.getString(R.string.act_fromto_dest_input_hint), false, SelectFor.DEFAULT_POI, true);
            }
        });
        remoteControlFragment.c.onResume();
        remoteControlFragment.a();
        remoteControlFragment.d.loadHistory();
        remoteControlFragment.a(remoteControlFragment.j);
        if (!TextUtils.isEmpty(((deq) remoteControlFragment.mPresenter).c())) {
            ((RemoteControlModel) ((deq) remoteControlFragment.mPresenter).b).a(remoteControlFragment.b, ((deq) remoteControlFragment.mPresenter).c().toString());
        }
        RemoteControlModel remoteControlModel = (RemoteControlModel) this.b;
        remoteControlModel.f = !DriveSpUtil.getSearchRouteInNetMode(remoteControlModel.a());
        RemoteControlModel remoteControlModel2 = (RemoteControlModel) this.b;
        if (remoteControlModel2.c.b()) {
            remoteControlModel2.c.a(remoteControlModel2.g);
        }
    }

    public final void onStop() {
        ((RemoteControlFragment) this.mPage).c.cancelTmcRequest();
    }

    public final void onDestroy() {
        RemoteControlFragment remoteControlFragment = (RemoteControlFragment) this.mPage;
        IAutoRemoteController iAutoRemoteController = (IAutoRemoteController) a.a.a(IAutoRemoteController.class);
        if (iAutoRemoteController != null) {
            iAutoRemoteController.removeRemoteControlConnectListener(remoteControlFragment.n);
        }
        if (remoteControlFragment.l != null) {
            remoteControlFragment.dismissViewLayer(remoteControlFragment.l);
            remoteControlFragment.l = null;
        }
        RemoteControlModel remoteControlModel = (RemoteControlModel) this.b;
        remoteControlModel.c.b(remoteControlModel.g);
    }

    public final boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (i == 4) {
            ((RemoteControlFragment) this.mPage).c.cancelTmcRequest();
        }
        return super.onKeyDown(i, keyEvent);
    }

    public final void a(String str) {
        if (((RemoteControlFragment) this.mPage).isAlive() && ((RemoteControlFragment) this.mPage).isStarted()) {
            ToastHelper.showLongToast(str);
        }
    }

    public final void a(int i, String str) {
        RemoteControlFragment remoteControlFragment = (RemoteControlFragment) this.mPage;
        if (i == 0) {
            remoteControlFragment.a((String) "已在汽车上为您规划路线", (String) "当前位置", str);
            return;
        }
        String string = remoteControlFragment.getString(R.string.drive_send_fail);
        String string2 = remoteControlFragment.getString(R.string.drive_confirm_and_resend);
        remoteControlFragment.getString(R.string.drive_confirm);
        remoteControlFragment.a(string, string2);
    }

    public final void a(POI poi) {
        ((RemoteControlModel) this.b).a(poi);
    }

    public final void a(boolean z) {
        ((RemoteControlFragment) this.mPage).a(z);
    }

    public final void onResult(int i, ResultType resultType, PageBundle pageBundle) {
        super.onResult(i, resultType, pageBundle);
        if (ResultType.OK == resultType) {
            if (i != 2) {
                switch (i) {
                    case 257:
                        POI pOIHome = DriveUtil.getPOIHome();
                        POI a = a(resultType, pageBundle);
                        if (a != null) {
                            auz auz = (auz) a.a.a(auz.class);
                            if (auz != null) {
                                auz.a(a);
                            }
                            ((RemoteControlFragment) this.mPage).a();
                            if (pOIHome != null) {
                                ToastHelper.showToast(b().getString(R.string.update_favourite_successful));
                                return;
                            } else {
                                ToastHelper.showToast(b().getString(R.string.add_favourite_successful));
                                return;
                            }
                        }
                        break;
                    case Tts.TTS_STATE_INVALID_DATA /*258*/:
                        POI pOICompany = DriveUtil.getPOICompany();
                        POI a2 = a(resultType, pageBundle);
                        if (a2 != null) {
                            auz auz2 = (auz) a.a.a(auz.class);
                            if (auz2 != null) {
                                auz2.b(a2);
                            }
                            ((RemoteControlFragment) this.mPage).a();
                            if (pOICompany != null) {
                                ToastHelper.showToast(b().getString(R.string.update_favourite_successful));
                                return;
                            } else {
                                ToastHelper.showToast(b().getString(R.string.add_favourite_successful));
                                return;
                            }
                        }
                        break;
                    case Tts.TTS_STATE_CREATED /*259*/:
                        break;
                    case Tts.TTS_STATE_DESTROY /*260*/:
                        a(a(resultType, pageBundle));
                        break;
                }
            }
            POI a3 = a(resultType, pageBundle);
            if (a3 != null) {
                a(a3);
            }
        }
    }

    private static POI a(ResultType resultType, PageBundle pageBundle) {
        if (ResultType.OK != resultType || pageBundle == null || !pageBundle.containsKey("result_poi")) {
            return null;
        }
        return (POI) pageBundle.getObject("result_poi");
    }

    private CharSequence c() {
        return ((RemoteControlModel) this.b).d;
    }

    public final /* synthetic */ su a() {
        return new RemoteControlModel(this);
    }
}
