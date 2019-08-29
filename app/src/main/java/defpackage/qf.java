package defpackage;

import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.amap.bundle.drive.setting.quicknaviwidget.accessibility.QuickAutoNaviAccessibilitySettings;
import com.amap.bundle.drive.setting.quicknaviwidget.broadcast.QuickAutoNaviBroadcastSettings;
import com.amap.bundle.drive.setting.quicknaviwidget.btchannel.QuickAutoNaviBTChannelPage;
import com.amap.bundle.drive.setting.quicknaviwidget.display.QuickAutoNaviDisplaySettings;
import com.amap.bundle.drive.setting.quicknaviwidget.main.QuickAutonNaviSettingFragment;
import com.amap.bundle.drivecommon.tools.DriveUtil;
import com.amap.bundle.statistics.LogManager;
import com.amap.bundle.statistics.util.LogUtil;
import com.amap.bundle.tripgroup.api.IVoicePackageManager;
import com.amap.bundle.utils.ui.NoDBClickUtil;
import com.autonavi.bundle.account.api.IAccountService;
import com.autonavi.bundle.uitemplate.mapwidget.widget.diy.DIYMainMapPresenter;
import com.autonavi.bundle.vui.vuistate.VUIStateManager;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.minimap.R;
import com.autonavi.minimap.ajx3.Ajx3Page;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: qf reason: default package */
/* compiled from: NaviCommonSettingManager */
public final class qf {
    public AbstractBasePage a;
    public TextView b;
    public TextView c;
    public int d = 1;
    public int e;
    private View f;
    private RelativeLayout g;
    private RelativeLayout h;
    private TextView i;
    private TextView j;
    private TextView k;
    private View l;
    private TextView m;
    private RelativeLayout n;
    private TextView o;
    private RelativeLayout p;
    private LinearLayout q;
    private View r;
    private qk s = null;

    public qf(AbstractBasePage abstractBasePage, View view, int i2, int i3) {
        this.a = abstractBasePage;
        this.f = view;
        this.e = i2;
        this.d = i3;
        PageBundle arguments = this.a.getArguments();
        if (arguments == null || !arguments.containsKey("amap.extra.prefer.from")) {
            this.d = 2;
        } else {
            this.d = arguments.getInt("amap.extra.prefer.from");
        }
        this.g = (RelativeLayout) this.f.findViewById(R.id.common_setting_voice_layout);
        NoDBClickUtil.a((View) this.g, (OnClickListener) new OnClickListener() {
            public final void onClick(View view) {
                if (qf.this.a.isAlive()) {
                    qf qfVar = qf.this;
                    Intent intent = new Intent();
                    boolean z = true;
                    intent.putExtra(IVoicePackageManager.SHOW_TTS_FROM_KEY, 1);
                    intent.putExtra(IVoicePackageManager.ENTRANCE_VOICE_SQUARE, true);
                    IVoicePackageManager iVoicePackageManager = (IVoicePackageManager) ank.a(IVoicePackageManager.class);
                    if (iVoicePackageManager != null) {
                        iVoicePackageManager.deal(qfVar.a, intent);
                    }
                    ku.a().c("motor_log", "mCommonVoiceLayout click");
                    if (qf.this.e == 2) {
                        qf.a("B003");
                        return;
                    }
                    qf qfVar2 = qf.this;
                    try {
                        JSONObject jSONObject = new JSONObject();
                        if (qfVar2.d != 2) {
                            z = false;
                        }
                        if (z) {
                            jSONObject.put("from", DIYMainMapPresenter.DIY_ENTRY_KEY_MORE);
                        } else {
                            jSONObject.put("from", "路线");
                        }
                        LogUtil.actionLogV2("P00181", "B013", jSONObject);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        this.i = (TextView) this.f.findViewById(R.id.common_setting_voice_tv);
        this.s = new qk();
        this.s.a(this.f);
        this.j = (TextView) this.f.findViewById(R.id.common_setting_map_tv);
        NoDBClickUtil.a((View) this.j, (OnClickListener) new OnClickListener() {
            public final void onClick(View view) {
                if (qf.this.a.isAlive()) {
                    ku.a().c("motor_log", "mCommonMapTv click");
                    if (qf.this.e == 2) {
                        qf.a("B005");
                    }
                    PageBundle pageBundle = new PageBundle();
                    pageBundle.putInt("amap.extra.prefer.from", qf.this.d);
                    qf.this.a.startPage(QuickAutoNaviDisplaySettings.class, pageBundle);
                }
            }
        });
        this.k = (TextView) this.f.findViewById(R.id.common_car_logo_tv);
        this.l = this.f.findViewById(R.id.common_car_logo_line);
        NoDBClickUtil.a((View) this.k, (OnClickListener) new OnClickListener() {
            public final void onClick(View view) {
                if (qf.this.a.isAlive()) {
                    IAccountService iAccountService = (IAccountService) a.a.a(IAccountService.class);
                    if (iAccountService != null) {
                        if (iAccountService.a()) {
                            qf.this.a.startScheme(new Intent("android.intent.action.VIEW", Uri.parse(QuickAutonNaviSettingFragment.CARLOGO_AMAPURI)));
                            return;
                        }
                        iAccountService.a(qf.this.a.getPageContext(), (anq) new anq() {
                            public final void loginOrBindCancel() {
                            }

                            public final void onComplete(boolean z) {
                                if (z && qf.this.a.isAlive()) {
                                    qf.this.a.startScheme(new Intent("android.intent.action.VIEW", Uri.parse(QuickAutonNaviSettingFragment.CARLOGO_AMAPURI)));
                                }
                            }
                        });
                    }
                }
            }
        });
        this.m = (TextView) this.f.findViewById(R.id.common_assist_tv);
        NoDBClickUtil.a((View) this.m, (OnClickListener) new OnClickListener() {
            public final void onClick(View view) {
                if (qf.this.a.isAlive()) {
                    ku.a().c("motor_log", "mAssistTv click");
                    if (qf.this.e == 2) {
                        qf.a("B006");
                    }
                    PageBundle pageBundle = new PageBundle();
                    pageBundle.putInt("amap.extra.prefer.from", qf.this.d);
                    qf.this.a.startPage(QuickAutoNaviAccessibilitySettings.class, pageBundle);
                }
            }
        });
        this.h = (RelativeLayout) this.f.findViewById(R.id.common_setting_broadcast_layout);
        NoDBClickUtil.a((View) this.h, (OnClickListener) new OnClickListener() {
            public final void onClick(View view) {
                ku.a().c("motor_log", "mCommonBroadcastLayout click");
                if (qf.this.e == 2) {
                    qf.a("B004");
                }
                PageBundle pageBundle = new PageBundle();
                pageBundle.putInt("amap.extra.prefer.from", qf.this.d);
                if (qf.this.a != null && qf.this.a.isAlive()) {
                    qf.this.a.startPage(QuickAutoNaviBroadcastSettings.class, pageBundle);
                }
            }
        });
        this.b = (TextView) this.f.findViewById(R.id.common_setting_broadcast_tv);
        b();
        this.n = (RelativeLayout) this.f.findViewById(R.id.common_setting_vcs_layout);
        if (this.d == 4) {
            this.n.setVisibility(8);
        } else {
            this.n.setVisibility(0);
        }
        if (!VUIStateManager.f().m()) {
            this.n.setVisibility(8);
        }
        NoDBClickUtil.a((View) this.n, (OnClickListener) new OnClickListener() {
            public final void onClick(View view) {
                PageBundle pageBundle = new PageBundle();
                pageBundle.putString("url", "path://amap_bundle_globalvoice/src/business/voiceControlSetting/pages/VoiceControlSettingMainPage.page.js");
                if (qf.this.a != null && qf.this.a.isAlive()) {
                    qf.this.a.startPage(Ajx3Page.class, pageBundle);
                }
            }
        });
        this.o = (TextView) this.f.findViewById(R.id.common_setting_vcs_tv);
        c();
        this.p = (RelativeLayout) this.f.findViewById(R.id.common_setting_bt_sound_channel_layout);
        this.q = (LinearLayout) this.f.findViewById(R.id.eagle_setting_layout);
        this.r = this.f.findViewById(R.id.eagle_setting_line);
        if (this.e == 2) {
            this.p.setVisibility(8);
            this.q.setVisibility(8);
            this.r.setVisibility(8);
            this.k.setVisibility(8);
            this.l.setVisibility(8);
        } else {
            this.p.setVisibility(0);
            this.q.setVisibility(0);
            this.r.setVisibility(0);
            this.k.setVisibility(0);
            this.l.setVisibility(0);
        }
        NoDBClickUtil.a((View) this.p, (OnClickListener) new OnClickListener() {
            public final void onClick(View view) {
                PageBundle pageBundle = new PageBundle();
                pageBundle.putInt("amap.extra.prefer.from", qf.this.d);
                pageBundle.putInt(QuickAutoNaviBTChannelPage.BUNDLE_KEY_SELECTED_TYPE, qf.this.e);
                if (qf.this.a != null && qf.this.a.isAlive()) {
                    qf.this.a.startPage(QuickAutoNaviBTChannelPage.class, pageBundle);
                }
            }
        });
        this.c = (TextView) this.f.findViewById(R.id.common_setting_bt_sound_channel_tv);
        View findViewById = this.f.findViewById(R.id.bt_channel_line);
        View findViewById2 = this.f.findViewById(R.id.vcs_line);
        if (this.e == 2) {
            findViewById2.setVisibility(8);
            findViewById.setVisibility(8);
        } else {
            findViewById2.setVisibility(0);
        }
        if (DriveUtil.isNeedFilterBluetoothSpeaker()) {
            findViewById.setVisibility(8);
            this.p.setVisibility(8);
        }
    }

    public final void a() {
        if (!VUIStateManager.f().m() || this.e != 0) {
            this.n.setVisibility(8);
        } else {
            this.n.setVisibility(0);
        }
    }

    public final void b() {
        IVoicePackageManager iVoicePackageManager = (IVoicePackageManager) ank.a(IVoicePackageManager.class);
        if (iVoicePackageManager != null) {
            String currentTtsName2 = iVoicePackageManager.getCurrentTtsName2();
            rp.a();
            if (!TextUtils.isEmpty(currentTtsName2) && this.i != null) {
                re.a(currentTtsName2);
                this.i.setText(currentTtsName2);
            }
        }
    }

    public final void c() {
        if (this.o != null && this.a != null) {
            this.o.setText(this.a.getString(bfj.a().b() ? R.string.common_on : R.string.common_off));
        }
    }

    static /* synthetic */ void a(String str) {
        ku.a().c("motor_log", "[motorActionLog] pageId=P00433buttonId=".concat(String.valueOf(str)));
        LogManager.actionLogV2("P00433", str);
    }
}
