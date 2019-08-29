package defpackage;

import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.amap.bundle.drive.setting.navisetting.controller.NaviMotorSettingManager$4;
import com.amap.bundle.drivecommon.route.result.view.RoutingPreferenceView;
import com.amap.bundle.drivecommon.tools.DriveUtil;
import com.amap.bundle.statistics.LogManager;
import com.amap.bundle.utils.ui.NoDBClickUtil;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.map.fragmentcontainer.page.dialog.NodeAlertDialogPage;
import com.autonavi.map.fragmentcontainer.page.dialog.NodeAlertDialogPage.Builder;
import com.autonavi.map.fragmentcontainer.page.dialog.NodeAlertDialogPage.NodeDialogFragmentOnClickListener;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;
import com.autonavi.widget.ui.AmapSwitch;
import java.lang.ref.WeakReference;
import org.json.JSONObject;

/* renamed from: qg reason: default package */
/* compiled from: NaviMotorSettingManager */
public final class qg {
    public WeakReference<AbstractBasePage> a;
    public String b;
    public boolean c;
    public boolean d;
    private View e;
    private ImageView f = ((ImageView) this.e.findViewById(R.id.motor_icon_iv));
    private ImageView g = ((ImageView) this.e.findViewById(R.id.motor_setting_icon));
    private ImageView h = ((ImageView) this.e.findViewById(R.id.motor_setting_delete));
    private TextView i;
    private TextView j;
    private RoutingPreferenceView k;
    private LinearLayout l;
    private LinearLayout m;
    private AmapSwitch n;
    private LinearLayout o = ((LinearLayout) this.e.findViewById(R.id.motor_layout));
    private TextView p;

    public qg(AbstractBasePage abstractBasePage, View view) {
        this.a = new WeakReference<>(abstractBasePage);
        this.e = view;
        NoDBClickUtil.a((View) this.h, (OnClickListener) new OnClickListener() {
            public final void onClick(View view) {
                qg qgVar = qg.this;
                AbstractBasePage abstractBasePage = (AbstractBasePage) qgVar.a.get();
                if (abstractBasePage != null && abstractBasePage.isAlive()) {
                    Builder builder = new Builder(abstractBasePage.getActivity());
                    builder.setNegativeButton((CharSequence) abstractBasePage.getString(R.string.auto_delete_cancle), (NodeDialogFragmentOnClickListener) new NodeDialogFragmentOnClickListener() {
                        public final void onClick(NodeAlertDialogPage nodeAlertDialogPage) {
                            nodeAlertDialogPage.finish();
                        }
                    });
                    builder.setPositiveButton((CharSequence) abstractBasePage.getString(R.string.delete), (NodeDialogFragmentOnClickListener) new NodeDialogFragmentOnClickListener() {
                        public final void onClick(NodeAlertDialogPage nodeAlertDialogPage) {
                            if (ro.d() == 0) {
                                ToastHelper.showToast(AMapPageUtil.getAppContext().getString(R.string.delete_plate_success_motor));
                            }
                            qg.this.a();
                            nodeAlertDialogPage.finish();
                        }
                    });
                    builder.setMessage((CharSequence) abstractBasePage.getString(R.string.dialog_msg_delete_motor));
                    abstractBasePage.startAlertDialogPage(builder);
                }
            }
        });
        this.i = (TextView) this.e.findViewById(R.id.motor_plate_tv);
        this.j = (TextView) this.e.findViewById(R.id.motor_info_tv);
        this.k = (RoutingPreferenceView) this.e.findViewById(R.id.motor_setting_routing_preference_view);
        this.k.setChoiceType(11);
        this.k.updateView();
        PageBundle pageBundle = new PageBundle();
        pageBundle.putObject(RoutingPreferenceView.BUNDLE_KEY_OBJ_ORIGIN, this);
        this.k.setPrebuiltData(pageBundle);
        this.n = (AmapSwitch) this.e.findViewById(R.id.motor_limit_checkbox);
        this.n.setOnCheckedChangeListener(new NaviMotorSettingManager$4(this));
        this.l = (LinearLayout) this.e.findViewById(R.id.motor_info_layout);
        NoDBClickUtil.a((View) this.g, (OnClickListener) new OnClickListener() {
            public final void onClick(View view) {
                try {
                    AbstractBasePage abstractBasePage = (AbstractBasePage) qg.this.a.get();
                    if (abstractBasePage != null && abstractBasePage.isAlive()) {
                        qg.this.b = DriveUtil.getMotorInfo();
                        qg.c();
                        ro.c();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        this.m = (LinearLayout) this.e.findViewById(R.id.motor_add_item);
        this.p = (TextView) this.m.findViewById(R.id.setting_add_item_text);
        if (this.p != null) {
            this.p.setText("添加您的摩托车");
        }
        NoDBClickUtil.a((View) this.m, (OnClickListener) new OnClickListener() {
            public final void onClick(View view) {
                AbstractBasePage abstractBasePage = (AbstractBasePage) qg.this.a.get();
                if (abstractBasePage != null && abstractBasePage.isAlive()) {
                    ro.c();
                    qg.c();
                }
            }
        });
    }

    public final void a() {
        AbstractBasePage abstractBasePage = (AbstractBasePage) this.a.get();
        String motorPlateNum = DriveUtil.getMotorPlateNum();
        if (!TextUtils.isEmpty(motorPlateNum)) {
            if (abstractBasePage != null && abstractBasePage.isAlive()) {
                this.f.setImageResource(R.drawable.drive_motor_default_logo);
                this.i.setText(motorPlateNum);
                this.j.setVisibility(8);
            }
            if (!this.c) {
                this.n.setChecked(DriveUtil.isMotorAvoidLimitedPath());
            } else {
                this.n.setChecked(true);
                b(0);
                this.c = false;
            }
            a(false);
            return;
        }
        this.f.setImageDrawable(this.e.getResources().getDrawable(R.drawable.drive_motor_default_logo));
        this.i.setText("");
        this.j.setText("");
        this.n.setChecked(false);
        a(true);
        this.c = false;
    }

    private void a(boolean z) {
        int i2 = 8;
        this.m.setVisibility(z ? 0 : 8);
        LinearLayout linearLayout = this.l;
        if (!z) {
            i2 = 0;
        }
        linearLayout.setVisibility(i2);
    }

    public final void a(int i2) {
        if (this.o != null) {
            a();
            this.o.setVisibility(i2);
        }
    }

    public static pj b() {
        pj pjVar = new pj();
        pjVar.b = ro.e();
        pjVar.c = DriveUtil.isAvoidLimitedPath();
        return pjVar;
    }

    public static void b(int i2) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("type", i2);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        LogManager.actionLogV2("P00433", "B007", jSONObject);
        ku.a().c("motor_log", "[motorActionLog] pageId=P00433buttonId=B007 NaviMotorSettingManager");
    }

    static /* synthetic */ void c() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("from", 1);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        LogManager.actionLogV2("P00434", "D001", jSONObject);
    }
}
