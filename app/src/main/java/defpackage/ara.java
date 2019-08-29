package defpackage;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import com.amap.bundle.utils.ui.CompatDialog;
import com.autonavi.minimap.R;
import com.autonavi.minimap.bundle.msgbox.entity.AmapMessage;

/* renamed from: ara reason: default package */
/* compiled from: MainMapMsgDialog */
public class ara implements OnClickListener, bkf, dav {
    private Context a = null;
    private a b = null;
    private ImageView c = null;
    private AmapMessage d = null;
    private CompatDialog e = null;
    private int f;
    private int g;

    public void onBitmapFailed(Drawable drawable) {
    }

    public void onPrepareLoad(Drawable drawable) {
    }

    public final void a(Activity activity, final a aVar) {
        this.a = activity;
        this.b = aVar;
        this.e = new CompatDialog(activity, R.style.custom_dlg) {
            public final boolean onKeyDown(int i, KeyEvent keyEvent) {
                if (i == 4 && keyEvent.getRepeatCount() == 0 && aVar != null) {
                    return aVar.f();
                }
                return super.onKeyDown(i, keyEvent);
            }
        };
        this.e.setContentView(R.layout.main_map_msg_box_dialog);
        this.c = (ImageView) this.e.findViewById(R.id.main_map_msg_dialog_iv);
        this.c.setOnClickListener(this);
        this.e.findViewById(R.id.main_map_msg_dialog_close).setOnClickListener(this);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        this.e.getWindow().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        this.f = (int) ((displayMetrics.density * 35.0f) + 0.5f);
        int i = displayMetrics.widthPixels;
        int i2 = displayMetrics.heightPixels;
        if (i2 > i) {
            i2 = i;
        }
        this.g = i2;
        this.e.setCanceledOnTouchOutside(false);
    }

    public final void a() {
        this.e.dismiss();
    }

    public final boolean b() {
        return this.e.isShowing();
    }

    public final AmapMessage c() {
        return this.d;
    }

    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.main_map_msg_dialog_iv) {
            if (this.b != null) {
                this.b.d();
            }
        } else if (id == R.id.main_map_msg_dialog_close && this.b != null) {
            this.b.e();
        }
    }

    private void a(int i, int i2) {
        int i3 = (this.g * i) / i2;
        int i4 = (i3 * 3) / 4;
        RelativeLayout relativeLayout = (RelativeLayout) this.e.findViewById(R.id.rlayout_image);
        LayoutParams layoutParams = (LayoutParams) relativeLayout.getLayoutParams();
        layoutParams.width = i4;
        layoutParams.height = i3;
        relativeLayout.setLayoutParams(layoutParams);
        RelativeLayout relativeLayout2 = (RelativeLayout) this.e.findViewById(R.id.rlayout_all);
        FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) relativeLayout2.getLayoutParams();
        layoutParams2.width = i4 + this.f;
        layoutParams2.height = i3 + this.f;
        relativeLayout2.setLayoutParams(layoutParams2);
    }

    public final void a(AmapMessage amapMessage) {
        if (amapMessage != null && amapMessage.imgUrl != null && amapMessage.imgUrl.length > 0 && !TextUtils.isEmpty(amapMessage.imgUrl[0])) {
            this.d = amapMessage;
            ko.a(this.c, amapMessage.imgUrl[0], null, R.drawable.ic_launcher, this);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:13:0x0035  */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x0038  */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x003c  */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x004d  */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x0058  */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x006b  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onBitmapLoaded(android.graphics.Bitmap r3, com.autonavi.common.imageloader.ImageLoader.LoadedFrom r4) {
        /*
            r2 = this;
            java.lang.Class<com.autonavi.minimap.bundle.maphome.service.IMainMapService> r4 = com.autonavi.minimap.bundle.maphome.service.IMainMapService.class
            java.lang.Object r4 = defpackage.ank.a(r4)
            com.autonavi.minimap.bundle.maphome.service.IMainMapService r4 = (com.autonavi.minimap.bundle.maphome.service.IMainMapService) r4
            r0 = 0
            if (r4 != 0) goto L_0x000d
        L_0x000b:
            r4 = 0
            goto L_0x001c
        L_0x000d:
            java.lang.Class<czj> r1 = defpackage.czj.class
            czi r4 = r4.a(r1)
            czj r4 = (defpackage.czj) r4
            if (r4 != 0) goto L_0x0018
            goto L_0x000b
        L_0x0018:
            boolean r4 = r4.a()
        L_0x001c:
            if (r4 == 0) goto L_0x001f
            return
        L_0x001f:
            boolean r4 = com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil.isHomePage()
            if (r4 != 0) goto L_0x0026
            return
        L_0x0026:
            android.content.Context r4 = r2.a
            android.content.res.Resources r4 = r4.getResources()
            android.content.res.Configuration r4 = r4.getConfiguration()
            int r4 = r4.orientation
            r1 = 1
            if (r4 != r1) goto L_0x0036
            r0 = 1
        L_0x0036:
            if (r0 == 0) goto L_0x003c
            r2.a(r1, r1)
            goto L_0x0041
        L_0x003c:
            r4 = 4
            r0 = 5
            r2.a(r4, r0)
        L_0x0041:
            android.widget.ImageView r4 = r2.c
            r0 = 0
            r4.setImageDrawable(r0)
            int r4 = android.os.Build.VERSION.SDK_INT
            r0 = 16
            if (r4 >= r0) goto L_0x0058
            android.widget.ImageView r4 = r2.c
            android.graphics.drawable.BitmapDrawable r0 = new android.graphics.drawable.BitmapDrawable
            r0.<init>(r3)
            r4.setBackgroundDrawable(r0)
            goto L_0x0062
        L_0x0058:
            android.widget.ImageView r4 = r2.c
            android.graphics.drawable.BitmapDrawable r0 = new android.graphics.drawable.BitmapDrawable
            r0.<init>(r3)
            r4.setBackground(r0)
        L_0x0062:
            com.amap.bundle.utils.ui.CompatDialog r3 = r2.e
            r3.show()
            dav$a r3 = r2.b
            if (r3 == 0) goto L_0x0072
            dav$a r3 = r2.b
            com.autonavi.minimap.bundle.msgbox.entity.AmapMessage r4 = r2.d
            r3.a(r4)
        L_0x0072:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.ara.onBitmapLoaded(android.graphics.Bitmap, com.autonavi.common.imageloader.ImageLoader$LoadedFrom):void");
    }
}
