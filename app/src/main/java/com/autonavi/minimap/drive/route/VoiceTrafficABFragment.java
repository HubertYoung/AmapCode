package com.autonavi.minimap.drive.route;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.TextView;
import com.amap.bundle.drivecommon.model.ICarRouteResult;
import com.amap.bundle.drivecommon.mvp.view.DriveBaseMapPage;
import com.amap.bundle.drivecommon.tools.DriveUtil;
import com.autonavi.annotation.PageAction;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.map.core.MapManager;
import com.autonavi.minimap.R;
import com.autonavi.minimap.map.overlayholder.OverlayPage.OvProperty;
import com.autonavi.minimap.map.overlayholder.OverlayPage.OverlayPageProperty;
import com.autonavi.minimap.map.overlayholder.OverlayPage.UvOverlay;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

@OverlayPageProperty(overlays = {@OvProperty(clickable = false, moveToFocus = true, overlay = UvOverlay.GpsOverlay, visible = true), @OvProperty(clickable = false, moveToFocus = true, overlay = UvOverlay.MapPointOverlay, visible = true)})
@PageAction("amap.drive.action.trafficab")
public class VoiceTrafficABFragment extends DriveBaseMapPage<die> implements OnClickListener {
    public djq a;
    public POI b;
    public POI c;
    public String d;
    public ICarRouteResult e;
    public ArrayList<dkb> f;
    public View g;
    public View h;
    public View i;
    public a j;
    public boolean k = false;
    public float l;
    public int m;
    public GeoPoint n;
    public dib o;
    public int p;
    public View q = null;
    /* access modifiers changed from: private */
    public djr r;
    /* access modifiers changed from: private */
    public Rect s;
    private TextView t;
    private TextView u;
    private TextView v;
    private final Callback w = new Callback() {
        public final boolean handleMessage(Message message) {
            if (message.what == 0) {
                VoiceTrafficABFragment.this.e = DriveUtil.parseBase64NaviData(VoiceTrafficABFragment.this.d, VoiceTrafficABFragment.this.b, VoiceTrafficABFragment.this.c);
                Message obtain = Message.obtain();
                obtain.what = 0;
                obtain.obj = VoiceTrafficABFragment.this.q;
                VoiceTrafficABFragment.this.j.sendMessage(obtain);
            }
            return true;
        }
    };

    public static class a extends Handler {
        private WeakReference<VoiceTrafficABFragment> a;

        public a(VoiceTrafficABFragment voiceTrafficABFragment) {
            this.a = new WeakReference<>(voiceTrafficABFragment);
        }

        public final void handleMessage(Message message) {
            super.handleMessage(message);
            VoiceTrafficABFragment voiceTrafficABFragment = (VoiceTrafficABFragment) this.a.get();
            if (voiceTrafficABFragment != null) {
                VoiceTrafficABFragment.a(voiceTrafficABFragment, (View) message.obj);
                voiceTrafficABFragment.b();
            }
        }
    }

    public void onCreate(Context context) {
        super.onCreate(context);
        setContentView(R.layout.voice_traffic_fragment_ab);
    }

    /* access modifiers changed from: private */
    /* renamed from: f */
    public die createPresenter() {
        return new die(this);
    }

    public View getMapSuspendView() {
        this.o = new dib(this);
        this.o.b();
        return this.o.getSuspendView();
    }

    private void a(View view) {
        if (view != null && this.f != null && this.f.size() != 0) {
            int i2 = 0;
            dkb dkb = this.f.get(0);
            if (dkb != null) {
                this.g.setOnClickListener(this);
                if (!TextUtils.isEmpty(dkb.a)) {
                    try {
                        i2 = Color.parseColor(dkb.a);
                    } catch (NumberFormatException e2) {
                        e2.printStackTrace();
                    }
                }
                view.findViewById(R.id.view_state_line_left).setBackgroundColor(i2);
                TextView textView = (TextView) view.findViewById(R.id.tv_state_left);
                if (!TextUtils.isEmpty(dkb.c)) {
                    textView.setText(dkb.c);
                    textView.setTextColor(i2);
                }
                this.t = (TextView) view.findViewById(R.id.tv_time_left);
                if (!TextUtils.isEmpty(dkb.d)) {
                    this.t.setText(dkb.d);
                    a(this.t);
                }
                TextView textView2 = (TextView) view.findViewById(R.id.tv_distance_left);
                if (!TextUtils.isEmpty(dkb.b)) {
                    textView2.setText(dkb.b);
                }
            }
        }
    }

    private void b(View view) {
        if (view != null && this.f != null && this.f.size() >= 2) {
            dkb dkb = this.f.get(1);
            if (dkb != null) {
                this.h.setOnClickListener(this);
                int i2 = 0;
                if (!TextUtils.isEmpty(dkb.a)) {
                    i2 = Color.parseColor(dkb.a);
                }
                view.findViewById(R.id.view_state_line_mid).setBackgroundColor(i2);
                TextView textView = (TextView) view.findViewById(R.id.tv_state_mid);
                if (!TextUtils.isEmpty(dkb.c)) {
                    textView.setText(dkb.c);
                    textView.setTextColor(i2);
                }
                this.u = (TextView) view.findViewById(R.id.tv_time_mid);
                if (!TextUtils.isEmpty(dkb.d)) {
                    this.u.setText(dkb.d);
                    a(this.u);
                }
                TextView textView2 = (TextView) view.findViewById(R.id.tv_distance_mid);
                if (!TextUtils.isEmpty(dkb.b)) {
                    textView2.setText(dkb.b);
                }
            }
        }
    }

    public final void b() {
        ICarRouteResult iCarRouteResult = this.e;
        if (iCarRouteResult != null) {
            this.a = new djq(getMapManager().getMapView(), getContext(), iCarRouteResult, this);
            this.a.a(new dhj());
            this.s = this.a.b();
            cde suspendManager = getSuspendManager();
            MapManager mapManager = getMapManager();
            cdz cdz = null;
            bty mapView = mapManager == null ? null : mapManager.getMapView();
            if (suspendManager != null) {
                cdz = suspendManager.d();
            }
            this.r = new djr(mapView, cdz);
            this.g.postDelayed(new Runnable() {
                /* JADX WARNING: Removed duplicated region for block: B:12:0x002f  */
                /* JADX WARNING: Removed duplicated region for block: B:13:0x0038  */
                /* JADX WARNING: Removed duplicated region for block: B:15:0x003b  */
                /* Code decompiled incorrectly, please refer to instructions dump. */
                public final void run() {
                    /*
                        r5 = this;
                        com.autonavi.minimap.drive.route.VoiceTrafficABFragment r0 = com.autonavi.minimap.drive.route.VoiceTrafficABFragment.this
                        djr r0 = r0.r
                        r1 = 0
                        r0.a = r1
                        r0 = 0
                        com.autonavi.minimap.drive.route.VoiceTrafficABFragment r2 = com.autonavi.minimap.drive.route.VoiceTrafficABFragment.this     // Catch:{ Exception -> 0x001a }
                        android.view.View r2 = r2.getTopMapInteractiveView()     // Catch:{ Exception -> 0x001a }
                        com.autonavi.minimap.drive.route.VoiceTrafficABFragment r3 = com.autonavi.minimap.drive.route.VoiceTrafficABFragment.this     // Catch:{ Exception -> 0x0018 }
                        android.view.View r3 = r3.getBottomMapInteractiveView()     // Catch:{ Exception -> 0x0018 }
                        r0 = r3
                        goto L_0x001f
                    L_0x0018:
                        r3 = move-exception
                        goto L_0x001c
                    L_0x001a:
                        r3 = move-exception
                        r2 = r0
                    L_0x001c:
                        r3.printStackTrace()
                    L_0x001f:
                        com.autonavi.minimap.drive.route.VoiceTrafficABFragment r3 = com.autonavi.minimap.drive.route.VoiceTrafficABFragment.this
                        android.content.res.Resources r3 = r3.getResources()
                        android.util.DisplayMetrics r3 = r3.getDisplayMetrics()
                        float r3 = r3.density
                        r4 = 1056964608(0x3f000000, float:0.5)
                        if (r2 == 0) goto L_0x0038
                        int r2 = r2.getHeight()
                        float r2 = (float) r2
                        float r2 = r2 / r3
                        float r2 = r2 + r4
                        int r2 = (int) r2
                        goto L_0x0039
                    L_0x0038:
                        r2 = 0
                    L_0x0039:
                        if (r0 == 0) goto L_0x0043
                        int r0 = r0.getHeight()
                        float r0 = (float) r0
                        float r0 = r0 / r3
                        float r0 = r0 + r4
                        int r1 = (int) r0
                    L_0x0043:
                        if (r2 > 0) goto L_0x0047
                        if (r1 <= 0) goto L_0x0054
                    L_0x0047:
                        com.autonavi.minimap.drive.route.VoiceTrafficABFragment r0 = com.autonavi.minimap.drive.route.VoiceTrafficABFragment.this
                        djr r0 = r0.r
                        int r2 = r2 + 35
                        int r1 = r1 + 5
                        r0.a(r2, r1)
                    L_0x0054:
                        com.autonavi.minimap.drive.route.VoiceTrafficABFragment r0 = com.autonavi.minimap.drive.route.VoiceTrafficABFragment.this
                        djr r0 = r0.r
                        r1 = 1
                        r0.a = r1
                        com.autonavi.minimap.drive.route.VoiceTrafficABFragment r0 = com.autonavi.minimap.drive.route.VoiceTrafficABFragment.this
                        djr r0 = r0.r
                        com.autonavi.minimap.drive.route.VoiceTrafficABFragment r1 = com.autonavi.minimap.drive.route.VoiceTrafficABFragment.this
                        android.graphics.Rect r1 = r1.s
                        r0.a(r1)
                        return
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.drive.route.VoiceTrafficABFragment.AnonymousClass1.run():void");
                }
            }, 300);
        }
    }

    public final void c() {
        this.g.setBackgroundColor(getResources().getColor(R.color.voice_traffic_ab_tab_select_background));
        this.h.setBackgroundColor(getResources().getColor(R.color.voice_traffic_ab_tab_unselect_background));
        this.i.setBackgroundColor(getResources().getColor(R.color.voice_traffic_ab_tab_unselect_background));
        this.t.setTextColor(-14606047);
        if (this.u != null) {
            this.u.setTextColor(getResources().getColor(R.color.f_c_3));
        }
        if (this.v != null) {
            this.v.setTextColor(getResources().getColor(R.color.f_c_3));
        }
    }

    public final void d() {
        this.h.setBackgroundColor(getResources().getColor(R.color.voice_traffic_ab_tab_select_background));
        this.g.setBackgroundColor(getResources().getColor(R.color.voice_traffic_ab_tab_unselect_background));
        this.i.setBackgroundColor(getResources().getColor(R.color.voice_traffic_ab_tab_unselect_background));
        this.t.setTextColor(getResources().getColor(R.color.f_c_3));
        this.u.setTextColor(-14606047);
        if (this.v != null) {
            this.v.setTextColor(getResources().getColor(R.color.f_c_3));
        }
    }

    public final void e() {
        this.i.setBackgroundColor(getResources().getColor(R.color.voice_traffic_ab_tab_select_background));
        this.g.setBackgroundColor(getResources().getColor(R.color.voice_traffic_ab_tab_unselect_background));
        this.h.setBackgroundColor(getResources().getColor(R.color.voice_traffic_ab_tab_unselect_background));
        this.t.setTextColor(getResources().getColor(R.color.f_c_3));
        this.u.setTextColor(getResources().getColor(R.color.f_c_3));
        this.v.setTextColor(-14606047);
    }

    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.lLayout_traffic_left) {
            if (this.e.getFocusRouteIndex() != 0) {
                this.e.setFocusRouteIndex(0);
                this.a.a();
                this.a.a(new dhj());
                this.r.a = 1;
                this.r.a(this.s);
                c();
            }
        } else if (id == R.id.lLayout_traffic_mid) {
            if (this.e.getFocusRouteIndex() != 1) {
                this.e.setFocusRouteIndex(1);
                this.a.a();
                this.a.a(new dhj());
                this.r.a = 1;
                this.r.a(this.s);
                d();
            }
        } else {
            if (id == R.id.lLayout_traffic_right && this.e.getFocusRouteIndex() != 2) {
                this.e.setFocusRouteIndex(2);
                this.a.a();
                this.a.a(new dhj());
                this.r.a = 1;
                this.r.a(this.s);
                e();
            }
        }
    }

    private void a(final TextView textView) {
        if (textView != null) {
            final String charSequence = textView.getText().toString();
            if (!TextUtils.isEmpty(charSequence)) {
                textView.measure(0, 0);
                final int measuredWidth = textView.getMeasuredWidth();
                textView.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
                    public final void onGlobalLayout() {
                        if (textView.getWidth() < measuredWidth) {
                            String string = VoiceTrafficABFragment.this.getResources().getString(R.string.voice_traffic_ab_time_key_word);
                            int indexOf = charSequence.indexOf(string);
                            if (indexOf > 0) {
                                textView.setText(charSequence.substring(0, indexOf + string.length()));
                            }
                        }
                    }
                });
            }
        }
    }

    public static void a() {
        awo awo = (awo) defpackage.esb.a.a.a(awo.class);
        if (awo != null) {
            awo.b(9003);
            awo.b(9001);
        }
    }

    static /* synthetic */ void a(VoiceTrafficABFragment voiceTrafficABFragment, View view) {
        if (view == null || voiceTrafficABFragment.f == null || voiceTrafficABFragment.f.size() == 0) {
            PageBundle pageBundle = new PageBundle();
            voiceTrafficABFragment.finish();
            voiceTrafficABFragment.startPage((String) "amap.search.action.searcherror", pageBundle);
            return;
        }
        switch (voiceTrafficABFragment.f.size()) {
            case 1:
                view.findViewById(R.id.view_cut_line_left).setVisibility(8);
                view.findViewById(R.id.view_cut_line_right).setVisibility(8);
                voiceTrafficABFragment.h.setVisibility(8);
                voiceTrafficABFragment.i.setVisibility(8);
                voiceTrafficABFragment.a(view);
                break;
            case 2:
                view.findViewById(R.id.view_cut_line_right).setVisibility(8);
                voiceTrafficABFragment.i.setVisibility(8);
                voiceTrafficABFragment.a(view);
                voiceTrafficABFragment.b(view);
                break;
            case 3:
                voiceTrafficABFragment.a(view);
                voiceTrafficABFragment.b(view);
                if (!(view == null || voiceTrafficABFragment.f == null || voiceTrafficABFragment.f.size() < 3)) {
                    dkb dkb = voiceTrafficABFragment.f.get(2);
                    if (dkb != null) {
                        voiceTrafficABFragment.i.setOnClickListener(voiceTrafficABFragment);
                        int parseColor = !TextUtils.isEmpty(dkb.a) ? Color.parseColor(dkb.a) : 0;
                        view.findViewById(R.id.view_state_line_right).setBackgroundColor(parseColor);
                        TextView textView = (TextView) view.findViewById(R.id.tv_state_right);
                        if (!TextUtils.isEmpty(dkb.c)) {
                            textView.setText(dkb.c);
                            textView.setTextColor(parseColor);
                        }
                        voiceTrafficABFragment.v = (TextView) view.findViewById(R.id.tv_time_right);
                        if (!TextUtils.isEmpty(dkb.d)) {
                            voiceTrafficABFragment.v.setText(dkb.d);
                            voiceTrafficABFragment.a(voiceTrafficABFragment.v);
                        }
                        TextView textView2 = (TextView) view.findViewById(R.id.tv_distance_right);
                        if (!TextUtils.isEmpty(dkb.b)) {
                            textView2.setText(dkb.b);
                            break;
                        }
                    }
                }
                break;
        }
        view.setVisibility(0);
    }
}
