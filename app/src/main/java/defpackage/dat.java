package defpackage;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.statistics.LogManager;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.annotation.MainMapFeature;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.map.fragmentcontainer.page.utils.IPageStateListener;
import com.autonavi.minimap.R;
import com.autonavi.minimap.bundle.maphome.service.IMainMapService;
import com.autonavi.minimap.bundle.msgbox.api.IMsgboxService;
import com.autonavi.minimap.bundle.msgbox.api.IMsgboxService.MainMapUIUpdater;
import com.autonavi.minimap.bundle.msgbox.dispatcher.AbsMsgBoxDispatcher;
import com.autonavi.minimap.bundle.msgbox.entity.AmapMessage;
import com.autonavi.minimap.bundle.msgbox.util.AMapHomeMsgManager$1;
import com.autonavi.minimap.bundle.msgbox.util.MessageBoxManager;
import com.autonavi.sdk.log.util.LogConstant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

@MainMapFeature
/* renamed from: dat reason: default package */
/* compiled from: AMapHomeMsgManager */
public class dat implements OnClickListener, btx, IPageStateListener, czj, czq, czr, czu, czv, czz, defpackage.dav.a {
    private dav a = null;
    /* access modifiers changed from: private */
    public AbsMsgBoxDispatcher b;
    private ArrayList<AmapMessage> c = new ArrayList<>();
    private boolean d = false;
    private BroadcastReceiver e = new AMapHomeMsgManager$1(this);

    /* renamed from: dat$a */
    /* compiled from: AMapHomeMsgManager */
    class a implements MainMapUIUpdater {
        private a() {
        }

        /* synthetic */ a(dat dat, byte b) {
            this();
        }

        public final void updateUI(List<AmapMessage> list, boolean z, int i, ArrayList<AmapMessage> arrayList, AmapMessage amapMessage) {
            if (dat.this.b != null && dat.this.b.getHandler() != null) {
                final ArrayList<AmapMessage> arrayList2 = arrayList;
                final AmapMessage amapMessage2 = amapMessage;
                final boolean z2 = z;
                final List<AmapMessage> list2 = list;
                AnonymousClass1 r0 = new Runnable() {
                    public final void run() {
                        dat.a(dat.this, arrayList2);
                        dat.a(dat.this, amapMessage2);
                        if (z2) {
                            dat.b(dat.this);
                        } else if (list2 != null && list2.size() != 0) {
                            dat.a(dat.this, list2);
                        }
                    }
                };
                aho.a(r0);
            }
        }
    }

    public final void a(czj czj) {
    }

    public void onActivityStart() {
    }

    public void onActivityStop() {
    }

    public void onClick(View view) {
    }

    public void onMapSurfaceChanged(int i, int i2) {
    }

    public void onMapSurfaceDestroy() {
    }

    public dat() {
        IMainMapService iMainMapService = (IMainMapService) ank.a(IMainMapService.class);
        ((czj) iMainMapService.a(czj.class)).a((czj) this);
        this.b = iMainMapService.c();
    }

    public final void b() {
        if (!this.c.isEmpty()) {
            MessageBoxManager.getInstance().setNewComingConfirmed(this.c);
            this.c.clear();
        }
    }

    public final boolean a(MotionEvent motionEvent) {
        if (motionEvent.getAction() == 1) {
            g();
        }
        return false;
    }

    public final boolean a(MotionEvent motionEvent, GeoPoint geoPoint) {
        g();
        return false;
    }

    public final void c() {
        g();
    }

    private void g() {
        if (this.b != null) {
            AmapMessage dismissLayerTipsView = this.b.dismissLayerTipsView();
            if (dismissLayerTipsView != null) {
                MessageBoxManager.getInstance().setRead(dismissLayerTipsView);
            }
        }
    }

    public void onCreate() {
        AMapLog.d("redesign-msgbox", "onCreate()");
        LocalBroadcastManager.getInstance(AMapAppGlobal.getApplication()).registerReceiver(this.e, new IntentFilter("upload_local_blue_bar"));
    }

    public void onDestroy() {
        AMapLog.d("redesign-msgbox", "onDestroy()");
        if (this.a != null && this.a.b()) {
            this.a.a();
            this.a = null;
        }
        if (this.b != null) {
            this.b.clearTipTimer();
            this.b.destroy();
        }
        MessageBoxManager.getInstance().destroy();
        LocalBroadcastManager.getInstance(AMapAppGlobal.getApplication()).unregisterReceiver(this.e);
    }

    public void onCover() {
        if (i()) {
            if (this.b != null) {
                this.b.clearTipTimer();
                AmapMessage dismissTipsView = this.b.dismissTipsView();
                if (dismissTipsView != null) {
                    if (!TextUtils.isEmpty(dismissTipsView.reside) && "2".equals(dismissTipsView.reside)) {
                        MessageBoxManager.getInstance().setShowOnMap(dismissTipsView);
                    }
                }
                AmapMessage dismissLayerTipsView = this.b.dismissLayerTipsView();
                if (dismissLayerTipsView != null) {
                    MessageBoxManager.getInstance().setRead(dismissLayerTipsView);
                }
            }
            if (this.a != null && this.a.b()) {
                this.a.a();
                AmapMessage c2 = this.a.c();
                if (c2 != null) {
                    MessageBoxManager.getInstance().setShowOnMap(c2);
                }
            }
        }
    }

    public void onAppear() {
        if (this.b.isUpdateMsgFlag()) {
            a(true);
        } else {
            this.b.setUpdateMsgFlag(true);
        }
    }

    public final void j_() {
        if (i()) {
            if (this.b != null) {
                this.b.resumeTipTimer();
            }
            b(false);
        }
    }

    public final void k_() {
        if (i()) {
            if (this.b != null) {
                this.b.stopTipTimer();
            }
            b(true);
        }
    }

    private void b(boolean z) {
        MessageBoxManager.getInstance().getMessagesOnAppResume(z, new a(this, 0));
    }

    public void onMapSurfaceCreated() {
        if (!this.d) {
            this.d = true;
            a(false);
        }
    }

    public final void a(boolean z) {
        MessageBoxManager.getInstance().fetchMessageFromMainMap(0, z, new a(this, 0));
    }

    public void onIndoor(boolean z) {
        this.b.setIndoor(z);
    }

    public void onScenic(boolean z) {
        this.b.setSmartScenic(z);
    }

    public void onFullScreenStateChanged(boolean z) {
        this.b.setFullScreen(z);
    }

    public final boolean a() {
        return h() || (this.b != null && this.b.hasMessageShowing());
    }

    public final void d() {
        if (this.a != null && this.a.b()) {
            AmapMessage c2 = this.a.c();
            MessageBoxManager.getInstance().executeAction(c2);
            this.a.a();
            MessageBoxManager.getInstance().setRead(c2);
            JSONObject jSONObject = new JSONObject();
            if (c2 != null) {
                try {
                    jSONObject.put("category", c2.id);
                } catch (JSONException e2) {
                    e2.printStackTrace();
                }
            }
            jSONObject.put("time", dbf.a());
            LogManager.actionLogV2("P00001", "B031", jSONObject);
        }
    }

    public final void e() {
        if (this.a != null && this.a.b()) {
            AmapMessage c2 = this.a.c();
            this.a.a();
            MessageBoxManager.getInstance().setShowOnMap(c2);
            JSONObject jSONObject = new JSONObject();
            if (c2 != null) {
                try {
                    jSONObject.put("category", c2.id);
                } catch (JSONException e2) {
                    e2.printStackTrace();
                }
            }
            jSONObject.put("time", dbf.a());
            LogManager.actionLogV2("P00001", "B032", jSONObject);
        }
    }

    public final boolean f() {
        if (this.a == null || !this.a.b()) {
            return false;
        }
        this.a.a();
        MessageBoxManager.getInstance().setShowOnMap(this.a.c());
        return true;
    }

    public final void a(AmapMessage amapMessage) {
        if (amapMessage != null) {
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("type", amapMessage.id);
                jSONObject.put("name", amapMessage.title);
                jSONObject.put("time", dbf.a());
                jSONObject.put("status", 1);
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
            LogManager.actionLogV2("P00001", LogConstant.MAIN_MSGBOX_POPUP_DISP, jSONObject);
        }
    }

    private static void a(AmapMessage amapMessage, int i, boolean z) {
        if (!amapMessage.isADDisplay()) {
            amapMessage.barDisplay = true;
            MessageBoxManager.getInstance().setBarMsgDisplay(amapMessage.id);
            if (amapMessage.isADMsg()) {
                MessageBoxManager.getInstance().reportDisplayLog(amapMessage.id, i, 1);
            }
            if (!z) {
                JSONObject jSONObject = new JSONObject();
                try {
                    jSONObject.put("name", 1);
                    jSONObject.put("type", amapMessage.isEmergencyNews() ? "紧急" : "常规");
                } catch (JSONException e2) {
                    e2.printStackTrace();
                }
                LogManager.actionLogV2("P00001", LogConstant.BLUE_BAR_SHOW, jSONObject);
            }
        }
    }

    private boolean h() {
        return this.a != null && this.a.b();
    }

    private static void a(AmapMessage amapMessage, int i) {
        if (amapMessage != null) {
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("type", amapMessage.id);
                jSONObject.put("name", amapMessage.title);
                jSONObject.put("time", dbf.a());
                jSONObject.put("status", 1);
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
            switch (i) {
                case 0:
                    LogManager.actionLogV2("P00001", LogConstant.MAIN_MSGBOX_POPUP_DISP, jSONObject);
                    return;
                case 1:
                    try {
                        jSONObject.put("name", b(amapMessage));
                    } catch (JSONException unused) {
                    }
                    LogManager.actionLogV2("P00001", LogConstant.MAIN_MSGBOX_TIP_DISP, jSONObject);
                    HashMap hashMap = new HashMap();
                    hashMap.put("type", amapMessage.id);
                    hashMap.put("name", b(amapMessage));
                    kd.b("amap.P00001.0.B074", hashMap);
                    return;
                case 2:
                    LogManager.actionLogV2("P00001", LogConstant.MAIN_MSGBOX_BUBBLE_DISP, jSONObject);
                    break;
            }
        }
    }

    private static String b(AmapMessage amapMessage) {
        String string = AMapAppGlobal.getApplication().getResources().getString(R.string.amap_app_name);
        if (amapMessage == null) {
            return string;
        }
        if (!TextUtils.isEmpty(amapMessage.descMessage)) {
            string = amapMessage.descMessage;
        }
        return ((amapMessage.id == null || (!amapMessage.id.contentEquals(AmapMessage.TOKEN_UPDATE_APP) && !amapMessage.id.contentEquals(AmapMessage.TOKEN_DOWNLOAD_SEAR_MAP) && !amapMessage.id.contentEquals(AmapMessage.TOKEN_TAOBAO_LOGIN))) && !TextUtils.isEmpty(amapMessage.showBody)) ? amapMessage.showBody : string;
    }

    private static boolean i() {
        bid bid;
        IMainMapService iMainMapService = (IMainMapService) ank.a(IMainMapService.class);
        if (iMainMapService == null) {
            bid = null;
        } else {
            bid = iMainMapService.e();
        }
        apr apr = (apr) defpackage.esb.a.a.a(apr.class);
        if (bid == null || bid == null) {
            return false;
        }
        return apr.a(bid);
    }

    public static /* synthetic */ void a(dat dat, String str, String str2) {
        if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2) && str.equals(str2)) {
            AmapMessage dismissTipsView = dat.b.dismissTipsView();
            if (dismissTipsView != null) {
                MessageBoxManager.getInstance().setRead(dismissTipsView);
            }
            MessageBoxManager.getInstance().setMessageHasReadByMsgInfo(str);
        }
        dat.a(true);
    }

    static /* synthetic */ void a(dat dat, ArrayList arrayList) {
        dat.c.clear();
        dat.c.addAll(arrayList);
        IMsgboxService iMsgboxService = (IMsgboxService) defpackage.esb.a.a.a(IMsgboxService.class);
        int a2 = iMsgboxService != null ? iMsgboxService.getMsgboxStorageService().a() : 0;
        if (dat.c.size() > 0 || a2 > 0) {
            if (dat.b != null) {
                dat.b.setNewMsgVisibility(0);
            }
        } else if (dat.b != null) {
            dat.b.setNewMsgVisibility(8);
        }
    }

    static /* synthetic */ void a(dat dat, AmapMessage amapMessage) {
        if (dat.b == null ? false : dat.b.showActivity(amapMessage)) {
            a(amapMessage, 3, true);
        }
    }

    static /* synthetic */ void b(dat dat) {
        if (dat.b != null) {
            dat.b.clearTipTimer();
            AmapMessage dismissTipsView = dat.b.dismissTipsView();
            if (dismissTipsView != null) {
                MessageBoxManager.getInstance().setShowOnMap(dismissTipsView);
            }
            AmapMessage dismissLayerTipsView = dat.b.dismissLayerTipsView();
            if (dismissLayerTipsView != null) {
                MessageBoxManager.getInstance().setRead(dismissLayerTipsView);
            }
        }
        if (dat.a != null && dat.a.b()) {
            dat.a.a();
            AmapMessage c2 = dat.a.c();
            if (c2 != null) {
                MessageBoxManager.getInstance().setShowOnMap(c2);
            }
        }
    }

    static /* synthetic */ void a(dat dat, List list) {
        bid bid;
        boolean z = false;
        AmapMessage amapMessage = (AmapMessage) list.get(0);
        if (amapMessage.priority <= 100) {
            if ("1".equals(new MapSharePreference((String) "basemap").getStringValue("new_user_guide_is_shown", "")) && dat.a == null) {
                dat.a = (dav) ank.a(dav.class);
                dav dav = dat.a;
                IMainMapService iMainMapService = (IMainMapService) ank.a(IMainMapService.class);
                Activity activity = null;
                if (iMainMapService == null) {
                    bid = null;
                } else {
                    bid = iMainMapService.e();
                }
                if (bid != null) {
                    activity = bid.getActivity();
                }
                dav.a(activity, dat);
            }
            if ((dat.a == null || !dat.a.b()) && dat.a != null) {
                dat.a.a(amapMessage);
            }
        } else if (amapMessage.priority > 300) {
            if (amapMessage.priority <= 500 && !dat.a() && dat.b != null && dat.b.showBubble(amapMessage)) {
                a(amapMessage, 2);
                if (!(amapMessage.location == 4 || amapMessage.location == 5)) {
                    z = true;
                }
                if (amapMessage.hasSub && z && amapMessage.sub_page == 1) {
                    MessageBoxManager.getInstance().setCurDispBubbleMsg(amapMessage);
                }
            }
        } else if (!amapMessage.isEmergencyNews() && list.size() > 1) {
            if (dat.b != null && !dat.h() && dat.b.showMarqueeTips(list)) {
                dat.b.marqueeMessage(list);
            }
        } else if (dat.b != null && !dat.h()) {
            boolean showTips = dat.b.showTips(amapMessage);
            if (showTips) {
                a(amapMessage, 2, false);
            }
            if (showTips && amapMessage.isToastTips()) {
                dat.b.tickMessage(amapMessage);
            }
            a(amapMessage, 1);
        }
    }
}
