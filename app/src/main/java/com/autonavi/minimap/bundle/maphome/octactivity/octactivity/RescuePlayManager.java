package com.autonavi.minimap.bundle.maphome.octactivity.octactivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.Html;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.aosservice.response.AosResponseException;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.utils.ui.CompatDialog;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.minimap.R;
import com.autonavi.minimap.falcon.base.FalconAosPrepareResponseCallback;

public final class RescuePlayManager {
    public boolean a = false;
    public a b;

    class RescuePlayCallback extends FalconAosPrepareResponseCallback<czb> {
        private String b;

        public final /* synthetic */ void a(Object obj) {
            czb czb = (czb) obj;
            if (czb != null && !TextUtils.isEmpty(czb.a) && !TextUtils.isEmpty(czb.c) && czb.d != -1 && !RescuePlayManager.this.b.isShowing() && !TextUtils.isEmpty(this.b)) {
                a aVar = RescuePlayManager.this.b;
                String str = czb.a;
                if (!TextUtils.isEmpty(str)) {
                    aVar.a.setText(Html.fromHtml(str));
                }
                a aVar2 = RescuePlayManager.this.b;
                String str2 = czb.c;
                if (!TextUtils.isEmpty(str2)) {
                    aVar2.b.setText(str2);
                }
                RescuePlayManager.this.b.d = czb.b;
                a aVar3 = RescuePlayManager.this.b;
                int i = czb.d;
                if (i == 1) {
                    aVar3.c.setImageResource(R.drawable.rescue_play_save);
                } else if (i == 2) {
                    aVar3.c.setImageResource(R.drawable.rescue_play_no_save);
                }
                a aVar4 = RescuePlayManager.this.b;
                if (aVar4.b != null) {
                    aVar4.b.setBackgroundResource(R.drawable.rescue_play_dlg_btn);
                }
                RescuePlayManager.this.b.show();
                MapSharePreference mapSharePreference = new MapSharePreference((String) "RescuePlayAct");
                mapSharePreference.edit().putString(this.b, this.b);
                mapSharePreference.edit().apply();
            }
            RescuePlayManager.this.a(false);
        }

        public RescuePlayCallback(String str) {
            this.b = str;
        }

        public final void a(AosRequest aosRequest, AosResponseException aosResponseException) {
            RescuePlayManager.this.a(false);
        }

        public final /* synthetic */ Object a(AosByteResponse aosByteResponse) {
            czb czb = new czb();
            czb.parser((byte[]) aosByteResponse.getResult());
            if (!czb.result) {
                RescuePlayManager.this.a(false);
            }
            return czb;
        }
    }

    public class a extends CompatDialog {
        final TextView a;
        final TextView b;
        final ImageView c;
        /* access modifiers changed from: 0000 */
        public String d;
        private final Context f;
        private final ImageView g;

        public a(Activity activity) {
            super(activity, R.style.custom_dlg);
            this.f = activity;
            setContentView(R.layout.rescue_play_dlg);
            Window window = getWindow();
            window.setLayout(-1, -1);
            LayoutParams attributes = window.getAttributes();
            attributes.gravity = 51;
            onWindowAttributesChanged(attributes);
            this.c = (ImageView) findViewById(R.id.rescue_play_dlg_bg_iv);
            this.a = (TextView) findViewById(R.id.rescue_play_dlg_text_tv);
            this.b = (TextView) findViewById(R.id.rescue_play_dlg_btn_tv);
            this.b.setOnClickListener(new OnClickListener(RescuePlayManager.this) {
                public final void onClick(View view) {
                    a.this.dismiss();
                    if (!TextUtils.isEmpty(a.this.d)) {
                        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("androidamap://openFeature?featureName=OpenURL&urlType=0&contentType=autonavi&url=".concat(String.valueOf(a.this.d))));
                        intent.putExtra("owner", "banner");
                        DoNotUseTool.startScheme(intent);
                    }
                    a.this.a();
                }
            });
            this.g = (ImageView) findViewById(R.id.rescue_play_dlg_cancel_iv);
            this.g.setOnClickListener(new OnClickListener(RescuePlayManager.this) {
                public final void onClick(View view) {
                    a.this.dismiss();
                    a.this.a();
                }
            });
            setCanceledOnTouchOutside(false);
            DisplayMetrics displayMetrics = new DisplayMetrics();
            if (getWindow() != null) {
                getWindow().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            }
            int i = displayMetrics.widthPixels;
            int i2 = ((displayMetrics.heightPixels > i ? i : displayMetrics.heightPixels) * 9) / 16;
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.c.getLayoutParams();
            layoutParams.width = i2;
            layoutParams.height = (i2 * 20) / 17;
            this.c.setLayoutParams(layoutParams);
            this.a.setText(Html.fromHtml("<html>解救朋友成功<br/>送你<font color=\"#FF0000\">300金币</font></html>"));
        }

        public final void a() {
            if (this.c != null) {
                this.c.setImageBitmap(null);
            }
            if (this.b != null) {
                this.b.setBackgroundResource(0);
            }
        }
    }

    public RescuePlayManager(Activity activity) {
        this.b = new a(activity);
    }

    public final synchronized void a(boolean z) {
        this.a = z;
    }
}
