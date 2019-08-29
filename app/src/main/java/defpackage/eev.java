package defpackage;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.tripgroup.api.IVoicePackageManager;
import com.autonavi.minimap.R;

/* renamed from: eev reason: default package */
/* compiled from: RideNaviSettingController */
public final class eev implements OnClickListener {
    public a a;
    private bid b;
    private IVoicePackageManager c;
    private View d;
    private CheckBox e;
    private MapSharePreference f;

    /* renamed from: eev$a */
    /* compiled from: RideNaviSettingController */
    public interface a {
        void a(boolean z);

        void e();
    }

    public eev(bid bid, View view) {
        this.d = view;
        this.b = bid;
        if (this.d != null) {
            this.d.findViewById(R.id.running_cancel_mask).setAlpha(0.7f);
            this.f = new MapSharePreference((String) "SharedPreferences");
            this.c = (IVoicePackageManager) ank.a(IVoicePackageManager.class);
            this.e = (CheckBox) this.d.findViewById(R.id.running_voice_setting);
            this.e.setOnClickListener(this);
            this.d.findViewById(R.id.run_route_setting_to_voice_square).setOnClickListener(this);
            this.d.findViewById(R.id.running_cancel_mask).setOnClickListener(this);
            this.d.findViewById(R.id.running_navi_setting_exit).setOnClickListener(this);
        }
    }

    public final void onClick(View view) {
        int id = view.getId();
        if (id == R.id.running_voice_setting) {
            this.e.setChecked(this.e.isChecked());
            if (this.a != null) {
                this.a.a(this.e.isChecked());
            }
            boolean isChecked = this.e.isChecked();
            if (this.f != null) {
                this.f.putBooleanValue("ride_voice", isChecked);
            }
        } else if (id == R.id.running_navi_setting_exit || id == R.id.running_cancel_mask) {
            this.d.setVisibility(8);
            this.a.e();
        } else {
            if (id == R.id.run_route_setting_to_voice_square) {
                Intent intent = new Intent();
                intent.putExtra(IVoicePackageManager.SHOW_TTS_FROM_KEY, 1003);
                intent.putExtra(IVoicePackageManager.ENTRANCE_VOICE_SQUARE, true);
                if (!(this.c == null || this.b == null)) {
                    this.c.deal(this.b, intent);
                }
            }
        }
    }

    public final void a() {
        this.e.setChecked(b());
    }

    public final boolean b() {
        return this.f.getBooleanValue("ride_voice", true);
    }
}
