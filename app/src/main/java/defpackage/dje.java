package defpackage;

import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import com.amap.bundle.drivecommon.tools.DriveUtil;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.minimap.R;
import com.autonavi.minimap.drive.sticker.StickerTipView;
import com.autonavi.minimap.drive.sticker.overlay.StickersLineOverlay;
import com.autonavi.minimap.drive.sticker.overlay.StickersPointOverlay;
import com.autonavi.minimap.drive.sticker.page.StickerDetailPage;
import com.autonavi.minimap.drive.sticker.page.StickersPage;
import com.autonavi.minimap.drive.sticker.presenter.StickersPresenter$MyHandler$2;
import com.autonavi.minimap.poi.PoiRequestHolder;
import com.autonavi.minimap.poi.param.IllegalparkingRequest;
import com.autonavi.sdk.location.LocationInstrument;
import com.autonavi.widget.ui.CommonTips;
import com.autonavi.widget.ui.TitleBar;

/* renamed from: dje reason: default package */
/* compiled from: StickersPresenter */
public final class dje extends sv<StickersPage, diy> {
    public Handler c;

    /* renamed from: dje$a */
    /* compiled from: StickersPresenter */
    public static class a extends djh<StickersPage> {
        public diy a = null;

        a(StickersPage stickersPage, diy diy) {
            super(stickersPage);
            this.a = diy;
        }

        public final void handleMessage(Message message) {
            StickersPage stickersPage = (StickersPage) a();
            if (stickersPage != null && stickersPage.isAlive()) {
                int i = message.what;
                if (i != 100) {
                    if (i != 200) {
                        if (i == 300) {
                            stickersPage.a();
                            stickersPage.b();
                            ToastHelper.showLongToast(stickersPage.getString(R.string.stickers_too_large_maplevel));
                            stickersPage.j = true;
                            return;
                        } else if (i != 400) {
                            if (i == 500) {
                                stickersPage.h.setVisibility(0);
                                stickersPage.g.setVisibility(8);
                                return;
                            } else if (i == 600) {
                                stickersPage.g.setVisibility(0);
                                stickersPage.h.setVisibility(0);
                                Animation loadAnimation = AnimationUtils.loadAnimation(stickersPage.getContext(), R.anim.anim_stikers_search_center);
                                loadAnimation.setAnimationListener(new AnimationListener() {
                                    public final void onAnimationRepeat(Animation animation) {
                                    }

                                    public final void onAnimationStart(Animation animation) {
                                    }

                                    public final void onAnimationEnd(Animation animation) {
                                        StickersPage.this.g.setVisibility(8);
                                        StickersPage.this.h.setVisibility(8);
                                    }
                                });
                                stickersPage.g.startAnimation(loadAnimation);
                                return;
                            } else if (i == 700) {
                                stickersPage.a((String) message.obj);
                                return;
                            } else if (i == 800) {
                                a(LocationInstrument.getInstance().getLatestPosition());
                                return;
                            } else if (i == 900) {
                                PageBundle pageBundle = new PageBundle();
                                pageBundle.putObject("StickerDetailFragment.sticker_data", (div) message.obj);
                                pageBundle.putObject("StickerDetailFragment.sticker_resultdata", this.a.a);
                                stickersPage.startPage(StickerDetailPage.class, pageBundle);
                            }
                        } else if (this.a != null) {
                            djg djg = this.a.a;
                            if (djg != null && stickersPage.j) {
                                stickersPage.a(djg, true);
                                stickersPage.a(djg, djg.i);
                                stickersPage.j = false;
                            }
                            return;
                        } else if (stickersPage.getMapView() != null) {
                            a(GeoPoint.glGeoPoint2GeoPoint(stickersPage.getMapView().n()));
                            return;
                        }
                    } else if (stickersPage.getMapView() != null) {
                        GeoPoint glGeoPoint2GeoPoint = GeoPoint.glGeoPoint2GeoPoint(stickersPage.getMapView().n());
                        if (this.a != null) {
                            djg djg2 = this.a.a;
                            if (!(djg2 == null || djg2.j == null || cfe.a(glGeoPoint2GeoPoint, this.a.a.j) >= 1000.0f)) {
                                stickersPage.a();
                                return;
                            }
                        }
                        a(glGeoPoint2GeoPoint);
                        return;
                    }
                    return;
                }
                stickersPage.f.setVisibility(8);
            }
        }

        private void a(final GeoPoint geoPoint) {
            final StickersPage stickersPage = (StickersPage) a();
            if (!(stickersPage == null || stickersPage.getMapView() == null)) {
                stickersPage.getMapView().b((Runnable) new Runnable() {
                    public final void run() {
                        if (!(!stickersPage.isAlive() || stickersPage == null || stickersPage.getMapView() == null)) {
                            stickersPage.getMapView().W();
                            stickersPage.getMapView().a(500, -9999.0f, -9999, -9999, geoPoint.x, geoPoint.y);
                        }
                    }
                });
            }
            IllegalparkingRequest illegalparkingRequest = new IllegalparkingRequest();
            StringBuilder sb = new StringBuilder();
            sb.append(DriveUtil.getDecimal(geoPoint.getLongitude()));
            illegalparkingRequest.b = sb.toString();
            StringBuilder sb2 = new StringBuilder();
            sb2.append(DriveUtil.getDecimal(geoPoint.getLatitude()));
            illegalparkingRequest.c = sb2.toString();
            StringBuilder sb3 = new StringBuilder();
            sb3.append(geoPoint.getAdCode());
            illegalparkingRequest.d = sb3.toString();
            illegalparkingRequest.e = "180104";
            PoiRequestHolder.getInstance().sendIllegalparking(illegalparkingRequest, new StickersPresenter$MyHandler$2(this, stickersPage, geoPoint));
        }
    }

    public dje(StickersPage stickersPage) {
        super(stickersPage);
    }

    public final void onPageCreated() {
        super.onPageCreated();
        this.c = new a((StickersPage) this.mPage, (diy) this.b);
        StickersPage stickersPage = (StickersPage) this.mPage;
        View contentView = stickersPage.getContentView();
        stickersPage.requestScreenOrientation(1);
        if (stickersPage.getMapManager() != null) {
            stickersPage.l = stickersPage.getMapManager().getMapView();
        }
        stickersPage.getSuspendManager().b().disableView(18624);
        stickersPage.f = (CommonTips) contentView.findViewById(R.id.sticker_tips);
        stickersPage.g = contentView.findViewById(R.id.search_center);
        stickersPage.h = contentView.findViewById(R.id.centre);
        stickersPage.e = (StickerTipView) contentView.findViewById(R.id.sticker_view);
        ((TitleBar) contentView.findViewById(R.id.title)).setOnBackClickListener((OnClickListener) new OnClickListener() {
            public final void onClick(View view) {
                StickersPage.this.finish();
            }
        });
        stickersPage.a = new StickersPointOverlay(stickersPage.l);
        stickersPage.addOverlay(stickersPage.a);
        stickersPage.c = new StickersLineOverlay(stickersPage.l);
        stickersPage.addOverlay(stickersPage.c);
        stickersPage.d = new StickersLineOverlay(stickersPage.l);
        stickersPage.addOverlay(stickersPage.d);
        stickersPage.b = new StickersPointOverlay(stickersPage.l);
        stickersPage.addOverlay(stickersPage.b);
        stickersPage.k = stickersPage.getString(R.string.tip_sticker_parking_suggest);
        stickersPage.f.setTipText((CharSequence) stickersPage.k);
        stickersPage.f.setVisibility(0);
        ((dje) stickersPage.mPresenter).c.sendEmptyMessageDelayed(100, 4000);
    }

    public final void onStart() {
        super.onStart();
        StickersPage stickersPage = (StickersPage) this.mPage;
        djg djg = ((diy) this.b).a;
        bty mapView = stickersPage.getMapManager() == null ? null : stickersPage.getMapManager().getMapView();
        if (mapView != null) {
            mapView.b(false);
        }
        if (!(stickersPage.getSuspendManager() == null || stickersPage.getSuspendManager().d() == null)) {
            stickersPage.getSuspendManager().d().f();
        }
        if (djg != null) {
            stickersPage.a(djg, false);
            stickersPage.a(djg, djg.i);
        } else if (mapView != null) {
            if (mapView.w() <= 11) {
                ((dje) stickersPage.mPresenter).c.removeMessages(300);
                ((dje) stickersPage.mPresenter).c.sendEmptyMessageDelayed(300, 1000);
                GeoPoint latestPosition = LocationInstrument.getInstance().getLatestPosition();
                mapView.a(latestPosition.x, latestPosition.y);
            } else {
                ((dje) stickersPage.mPresenter).c.sendEmptyMessage(800);
            }
        }
        if (stickersPage.i != null) {
            stickersPage.i.a();
        }
    }

    public final void onStop() {
        super.onStop();
        StickersPage stickersPage = (StickersPage) this.mPage;
        boolean booleanValue = new MapSharePreference(SharePreferenceName.SharedPreferences).getBooleanValue("traffic", false);
        if (stickersPage.getMapView() != null) {
            stickersPage.getMapView().b(booleanValue);
        }
    }

    public final boolean onMapTouchEvent(MotionEvent motionEvent) {
        Handler handler = ((dje) ((StickersPage) this.mPage).mPresenter).c;
        handler.removeMessages(200);
        int action = motionEvent.getAction();
        if (action == 0) {
            handler.sendEmptyMessage(500);
        } else if (action == 1 || action == 3) {
            handler.sendEmptyMessage(600);
        }
        return super.onMapTouchEvent(motionEvent);
    }

    public final boolean onMapMotionStop() {
        StickersPage stickersPage = (StickersPage) this.mPage;
        Handler handler = ((dje) stickersPage.mPresenter).c;
        if (stickersPage.getMapView() != null) {
            if (stickersPage.getMapView().w() > 11) {
                handler.removeMessages(200);
                handler.sendEmptyMessageDelayed(200, 600);
            } else {
                handler.removeMessages(300);
                handler.sendEmptyMessageDelayed(300, 1000);
            }
        }
        return super.onMapMotionStop();
    }

    public final boolean onMapLevelChange(boolean z) {
        StickersPage stickersPage = (StickersPage) this.mPage;
        Handler handler = ((dje) stickersPage.mPresenter).c;
        if (stickersPage.getMapView() != null) {
            if (stickersPage.getMapView().w() <= 11) {
                handler.removeMessages(300);
                handler.sendEmptyMessageDelayed(300, 1000);
            } else {
                handler.removeMessages(300);
                handler.removeMessages(400);
                handler.sendEmptyMessageDelayed(400, 1000);
            }
        }
        return super.onMapLevelChange(z);
    }

    public final /* synthetic */ st a() {
        return new diy(this);
    }
}
