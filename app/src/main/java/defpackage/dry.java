package defpackage;

import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.widget.ImageView;
import com.amap.bundle.blutils.app.ConfigerHelper;
import com.amap.bundle.network.request.param.NetworkParam;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.bundle.account.api.IAccountService;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePresenter;
import com.autonavi.minimap.R;
import com.autonavi.minimap.myProfile.page.CarIllegalDlgPage;
import com.autonavi.sdk.location.LocationInstrument;
import java.util.Timer;
import java.util.TimerTask;

/* renamed from: dry reason: default package */
/* compiled from: CarIllegalDlgPresenter */
public final class dry extends AbstractBasePresenter<CarIllegalDlgPage> {
    public boolean a = false;
    public boolean b = true;
    private boolean c = true;
    /* access modifiers changed from: private */
    public Timer d;
    /* access modifiers changed from: private */
    public int e;
    /* access modifiers changed from: private */
    public a f;

    /* renamed from: dry$a */
    /* compiled from: CarIllegalDlgPresenter */
    class a extends Handler {
        a() {
        }

        public final void handleMessage(Message message) {
            switch (message.what) {
                case 1:
                    dry.this.a = true;
                    CarIllegalDlgPage carIllegalDlgPage = (CarIllegalDlgPage) dry.this.mPage;
                    carIllegalDlgPage.b.setVisibility(8);
                    carIllegalDlgPage.a.setVisibility(0);
                    return;
                case 2:
                    CarIllegalDlgPage carIllegalDlgPage2 = (CarIllegalDlgPage) dry.this.mPage;
                    int i = message.arg1;
                    int childCount = carIllegalDlgPage2.d.getChildCount();
                    int i2 = i % childCount;
                    for (int i3 = 0; i3 < childCount; i3++) {
                        ImageView imageView = (ImageView) carIllegalDlgPage2.d.getChildAt(i3);
                        if (i2 == i3) {
                            imageView.setImageResource(R.drawable.loading_point_big);
                        } else {
                            imageView.setImageResource(R.drawable.loading_point_small);
                        }
                    }
                    break;
            }
        }
    }

    public dry(CarIllegalDlgPage carIllegalDlgPage) {
        super(carIllegalDlgPage);
    }

    public final void onPageCreated() {
        super.onPageCreated();
        this.a = false;
        this.c = true;
        this.f = new a();
        this.b = true;
        this.c = true;
        if (this.c) {
            CarIllegalDlgPage carIllegalDlgPage = (CarIllegalDlgPage) this.mPage;
            carIllegalDlgPage.c.setText(Html.fromHtml(AMapAppGlobal.getApplication().getString(R.string.car_illeage_welcome)));
            carIllegalDlgPage.b.setVisibility(0);
            carIllegalDlgPage.a.setVisibility(8);
            this.d = new Timer("CarIllegalDlg.Timer");
            this.e = 0;
            this.d.schedule(new TimerTask() {
                public final void run() {
                    dry.this.e = dry.this.e + 1;
                    if (dry.this.e > 20) {
                        dry.this.d.cancel();
                        dry.this.f.sendEmptyMessage(1);
                        return;
                    }
                    Message obtainMessage = dry.this.f.obtainMessage();
                    obtainMessage.what = 2;
                    obtainMessage.arg1 = dry.this.e;
                    dry.this.f.sendMessage(obtainMessage);
                }
            }, 150, 150);
            return;
        }
        this.f.sendEmptyMessage(1);
    }

    public final void onStart() {
        ((CarIllegalDlgPage) this.mPage).setSoftInputMode(18);
        super.onStart();
    }

    public static String a() {
        StringBuilder sb = new StringBuilder();
        sb.append(ConfigerHelper.getInstance().getIllegalUrl());
        sb.append("?pid=0");
        if (!TextUtils.isEmpty(b())) {
            try {
                sb.append("&phone=".concat(String.valueOf(boc.a(b()))));
            } catch (Exception e2) {
                kf.a((Throwable) e2);
            }
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append(LocationInstrument.getInstance().getLatestPosition().getAdCode());
        String sb3 = sb2.toString();
        if (!TextUtils.isEmpty(sb3)) {
            sb.append("&adcode=".concat(String.valueOf(sb3)));
        }
        StringBuilder sb4 = new StringBuilder("&token=");
        sb4.append(Uri.encode(NetworkParam.getTaobaoID(), "UTF-8"));
        sb.append(sb4.toString());
        sb.append(NetworkParam.getNetworkParam(ConfigerHelper.getInstance().getIllegalUrl()));
        return sb.toString();
    }

    public final boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (i == 4) {
            CarIllegalDlgPage carIllegalDlgPage = (CarIllegalDlgPage) this.mPage;
            if (!carIllegalDlgPage.e.onKeyBackPressed()) {
                carIllegalDlgPage.finish();
            }
        }
        return super.onKeyDown(i, keyEvent);
    }

    public final void onStop() {
        ((CarIllegalDlgPage) this.mPage).a.stopLoading();
        if (this.d != null) {
            this.d.cancel();
        }
    }

    private static String b() {
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
}
