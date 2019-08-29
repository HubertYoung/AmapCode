package defpackage;

import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.amap.bundle.utils.ui.NoDBClickUtil;
import com.autonavi.ae.gmap.glinterface.GLGeoPoint;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.minimap.R;
import com.autonavi.minimap.drive.sticker.StickerShareFragment;
import com.autonavi.widget.ui.TitleBar;

/* renamed from: djd reason: default package */
/* compiled from: StickerSharePresenter */
public final class djd extends sv<StickerShareFragment, dix> {
    public djd(StickerShareFragment stickerShareFragment) {
        super(stickerShareFragment);
    }

    public final void onPageCreated() {
        super.onPageCreated();
        StickerShareFragment stickerShareFragment = (StickerShareFragment) this.mPage;
        stickerShareFragment.requestScreenOrientation(1);
        stickerShareFragment.getMapCustomizeManager().disableView(-1);
        View contentView = stickerShareFragment.getContentView();
        stickerShareFragment.b = (Button) contentView.findViewById(R.id.sticer_btn_confirm);
        stickerShareFragment.c = (ImageView) contentView.findViewById(R.id.status_img);
        stickerShareFragment.d = (TextView) contentView.findViewById(R.id.sticker_count_tv);
        stickerShareFragment.e = (TextView) contentView.findViewById(R.id.sticker_count_tip_tv);
        stickerShareFragment.f = (TextView) contentView.findViewById(R.id.sticker_position_tv);
        stickerShareFragment.g = (TextView) contentView.findViewById(R.id.sticker_last_time_tv);
        stickerShareFragment.h = (TextView) contentView.findViewById(R.id.sticker_frequency_tv);
        stickerShareFragment.i = (TextView) contentView.findViewById(R.id.sticker_status);
        contentView.findViewById(R.id.map_cover).setOnTouchListener(new OnTouchListener() {
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });
        stickerShareFragment.k = (FrameLayout) contentView.findViewById(R.id.strict_control_frame);
        stickerShareFragment.l = (TextView) contentView.findViewById(R.id.strict_control_detail);
        stickerShareFragment.m = (RelativeLayout) contentView.findViewById(R.id.normal_mode_frame);
        stickerShareFragment.j = (TitleBar) contentView.findViewById(R.id.mapTopInteractiveView);
        stickerShareFragment.j.setOnBackClickListener((OnClickListener) new OnClickListener() {
            public final void onClick(View view) {
                StickerShareFragment.this.finish();
            }
        });
        PageBundle arguments = stickerShareFragment.getArguments();
        if (arguments != null && arguments.containsKey("StickerDetailFragment.sticker_data")) {
            stickerShareFragment.a = (div) arguments.getObject("StickerDetailFragment.sticker_data");
            if (stickerShareFragment.getMapView() != null) {
                stickerShareFragment.getMapView().a((GLGeoPoint) new GeoPoint(stickerShareFragment.a.c, stickerShareFragment.a.d));
            }
            stickerShareFragment.f.setText(stickerShareFragment.a.b);
            TextView textView = stickerShareFragment.h;
            StringBuilder sb = new StringBuilder();
            sb.append(stickerShareFragment.a.k);
            sb.append(stickerShareFragment.getString(R.string.stickers_frequency));
            textView.setText(sb.toString());
            stickerShareFragment.g.setText(stickerShareFragment.a.j);
            if (stickerShareFragment.a.g < 3) {
                TextView textView2 = stickerShareFragment.d;
                StringBuilder sb2 = new StringBuilder();
                sb2.append(stickerShareFragment.a.k);
                textView2.setText(sb2.toString());
                stickerShareFragment.e.setVisibility(0);
                stickerShareFragment.m.setVisibility(0);
                stickerShareFragment.k.setVisibility(8);
            } else {
                stickerShareFragment.d.setText(stickerShareFragment.getString(R.string.stickers_strict_control_string));
                stickerShareFragment.e.setVisibility(8);
                stickerShareFragment.m.setVisibility(8);
                stickerShareFragment.k.setVisibility(0);
                stickerShareFragment.l.setText(stickerShareFragment.a.m);
            }
            switch (stickerShareFragment.a.g) {
                case 0:
                    stickerShareFragment.c.setImageResource(R.drawable.sticker_tips_safe);
                    stickerShareFragment.i.setText(stickerShareFragment.getString(R.string.stickers_safe_string));
                    stickerShareFragment.i.setTextColor(-15345994);
                    break;
                case 1:
                    stickerShareFragment.c.setImageResource(R.drawable.sticker_tips_normal);
                    stickerShareFragment.i.setText(stickerShareFragment.getString(R.string.stickers_danger_string));
                    stickerShareFragment.i.setTextColor(-33024);
                    break;
                case 2:
                    stickerShareFragment.c.setImageResource(R.drawable.sticker_tips_danger);
                    stickerShareFragment.i.setText(stickerShareFragment.getString(R.string.stickers_high_danger_string));
                    stickerShareFragment.i.setTextColor(-1894119);
                    break;
                case 3:
                    stickerShareFragment.c.setImageResource(R.drawable.sticker_tips_strict_control);
                    stickerShareFragment.i.setText(stickerShareFragment.getString(R.string.stickers_strict_control_string));
                    stickerShareFragment.i.setTextColor(-5308416);
                    break;
            }
        }
        NoDBClickUtil.a((View) stickerShareFragment.b, stickerShareFragment.n);
    }

    public final void onStart() {
        super.onStart();
        StickerShareFragment stickerShareFragment = (StickerShareFragment) this.mPage;
        if (stickerShareFragment.getMapView() != null) {
            stickerShareFragment.getMapView().b(false);
        }
    }

    public final void onStop() {
        super.onStop();
        StickerShareFragment stickerShareFragment = (StickerShareFragment) this.mPage;
        if (stickerShareFragment.o != null && stickerShareFragment.o.isShowing()) {
            stickerShareFragment.o.cancel();
            stickerShareFragment.o.dismiss();
            stickerShareFragment.o = null;
        }
    }

    public final /* synthetic */ st a() {
        return new dix(this);
    }
}
