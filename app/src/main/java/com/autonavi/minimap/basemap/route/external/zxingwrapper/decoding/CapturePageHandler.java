package com.autonavi.minimap.basemap.route.external.zxingwrapper.decoding;

import android.os.Handler;
import android.os.Message;
import com.autonavi.minimap.R;
import com.autonavi.minimap.aliyun.AliyunRequestHolder;
import com.autonavi.minimap.aliyun.param.OcrVehicleRequest;
import com.autonavi.minimap.basemap.route.page.CarLicenseScanPage;
import com.autonavi.minimap.basemap.route.presenter.CarLicenseScanPresenter$1;
import com.iflytek.tts.TtsService.Tts;
import java.lang.ref.WeakReference;

public final class CapturePageHandler extends Handler {
    private static final String b = "CapturePageHandler";
    public final cqp a;
    private final WeakReference<CarLicenseScanPage> c;
    private State d = State.SUCCESS;

    enum State {
        PREVIEW,
        SUCCESS,
        DONE
    }

    public CapturePageHandler(CarLicenseScanPage carLicenseScanPage) {
        this.c = new WeakReference<>(carLicenseScanPage);
        new cqq(carLicenseScanPage.c);
        this.a = new cqp(carLicenseScanPage);
        this.a.start();
        cqm.a().c();
        if (this.d == State.SUCCESS) {
            CarLicenseScanPage carLicenseScanPage2 = (CarLicenseScanPage) this.c.get();
            if (carLicenseScanPage2 != null && carLicenseScanPage2.isAlive()) {
                this.d = State.PREVIEW;
                cqm.a().a(this.a.a());
                cqm.a().b(this);
                carLicenseScanPage2.c.drawViewfinder();
            }
        }
    }

    public final void handleMessage(Message message) {
        CarLicenseScanPage carLicenseScanPage = (CarLicenseScanPage) this.c.get();
        if (carLicenseScanPage != null && carLicenseScanPage.isAlive()) {
            int i = message.what;
            if (i != 263) {
                switch (i) {
                    case 257:
                        if (this.d == State.PREVIEW) {
                            cqm.a().b(this);
                            return;
                        }
                        break;
                    case Tts.TTS_STATE_INVALID_DATA /*258*/:
                        this.d = State.SUCCESS;
                        String str = (String) message.obj;
                        if (str != null && str.length() > 0) {
                            ((cqu) carLicenseScanPage.mPresenter).a();
                            cqu cqu = (cqu) carLicenseScanPage.mPresenter;
                            if (str != null && str.length() != 0) {
                                OcrVehicleRequest ocrVehicleRequest = new OcrVehicleRequest();
                                ocrVehicleRequest.b = "50";
                                ocrVehicleRequest.c = str;
                                AliyunRequestHolder.getInstance().sendOcrVehicle(ocrVehicleRequest, new CarLicenseScanPresenter$1(cqu));
                            } else {
                                return;
                            }
                        }
                        return;
                    case Tts.TTS_STATE_CREATED /*259*/:
                        carLicenseScanPage.f();
                        return;
                    case Tts.TTS_STATE_DESTROY /*260*/:
                        carLicenseScanPage.c.setHintText(carLicenseScanPage.getResources().getString(R.string.car_license_scan_hint_suggest_manual_scan));
                        carLicenseScanPage.c.invalidate();
                        carLicenseScanPage.c.postDelayed(new Runnable() {
                            public final void run() {
                                if (CarLicenseScanPage.this.isAlive()) {
                                    CarLicenseScanPage.this.c.setHintText(CarLicenseScanPage.this.getResources().getString(R.string.car_license_scan_hint_align_with_frame));
                                    if (CarLicenseScanPage.this.b != null) {
                                        CarLicenseScanPage.this.b.b();
                                    }
                                }
                            }
                        }, 1000);
                        return;
                }
            } else {
                carLicenseScanPage.d();
            }
        }
    }

    public final void a() {
        this.d = State.DONE;
        cqm.a().d();
        removeCallbacksAndMessages(null);
        this.a.a().removeCallbacksAndMessages(null);
        Message.obtain(this.a.a(), 262).sendToTarget();
        try {
            this.a.join();
        } catch (InterruptedException unused) {
        }
    }

    public final void b() {
        Message.obtain(this.a.a(), 261).sendToTarget();
    }
}
