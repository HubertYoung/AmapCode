package com.autonavi.bundle.amaphome.dialog;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import com.amap.bundle.appupgrade.config.presenter.UpdatePagerAdapter;
import com.amap.bundle.statistics.LogManager;
import com.autonavi.minimap.R;
import com.autonavi.sdk.log.util.LogConstant;
import com.autonavi.widget.ui.AlertView;
import com.autonavi.widget.ui.AlertView.a;
import org.json.JSONException;
import org.json.JSONObject;

public class UpdateRichTextDialog implements OnPageChangeListener, OnClickListener, jq {
    private Context a = null;
    private a b = null;
    private AlertView c = null;
    private a d = null;
    private ji e = null;
    private jz f = null;
    private ViewPager g;
    private TextView h;
    private TextView i;
    private LinearLayout j;
    private Button k;
    private ImageView l;
    private TextView m;
    /* access modifiers changed from: private */
    public View n;
    private UpdatePagerAdapter o;
    private View[] p;
    private int q;

    /* JADX WARNING: Removed duplicated region for block: B:27:0x0180  */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x01eb  */
    /* JADX WARNING: Removed duplicated region for block: B:50:0x01f9  */
    /* JADX WARNING: Removed duplicated region for block: B:55:0x0216  */
    /* JADX WARNING: Removed duplicated region for block: B:56:0x0237  */
    /* JADX WARNING: Removed duplicated region for block: B:61:0x0254  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void a(android.content.Context r19, defpackage.ji r20, defpackage.jm r21, defpackage.jq.a r22) {
        /*
            r18 = this;
            r1 = r18
            r2 = r19
            r3 = r20
            r1.a = r2
            r4 = r22
            r1.b = r4
            r1.e = r3
            jz r4 = new jz
            r5 = r21
            r4.<init>(r2, r3, r5)
            r1.f = r4
            android.content.Context r2 = r1.a
            android.view.LayoutInflater r2 = android.view.LayoutInflater.from(r2)
            int r3 = com.autonavi.minimap.R.layout.layout_update_dialog
            r4 = 0
            android.view.View r2 = r2.inflate(r3, r4)
            com.autonavi.widget.ui.AlertView$a r3 = new com.autonavi.widget.ui.AlertView$a
            android.content.Context r5 = r1.a
            r3.<init>(r5)
            r1.d = r3
            com.autonavi.widget.ui.AlertView$a r3 = r1.d
            r3.a(r2)
            com.autonavi.widget.ui.AlertView$a r3 = r1.d
            r5 = 1
            r3.b(r5)
            int r3 = com.autonavi.minimap.R.id.update_viewpager
            android.view.View r3 = r2.findViewById(r3)
            android.support.v4.view.ViewPager r3 = (android.support.v4.view.ViewPager) r3
            r1.g = r3
            int r3 = com.autonavi.minimap.R.id.title_layout
            android.view.View r3 = r2.findViewById(r3)
            r1.n = r3
            int r3 = com.autonavi.minimap.R.id.title_textView
            android.view.View r3 = r2.findViewById(r3)
            android.widget.TextView r3 = (android.widget.TextView) r3
            r1.h = r3
            int r3 = com.autonavi.minimap.R.id.subTitle_textView
            android.view.View r3 = r2.findViewById(r3)
            android.widget.TextView r3 = (android.widget.TextView) r3
            r1.i = r3
            int r3 = com.autonavi.minimap.R.id.pos_points_layout
            android.view.View r3 = r2.findViewById(r3)
            android.widget.LinearLayout r3 = (android.widget.LinearLayout) r3
            r1.j = r3
            int r3 = com.autonavi.minimap.R.id.button_ensure
            android.view.View r3 = r2.findViewById(r3)
            android.widget.Button r3 = (android.widget.Button) r3
            r1.k = r3
            android.widget.Button r3 = r1.k
            r3.setOnClickListener(r1)
            int r3 = com.autonavi.minimap.R.id.iv_close
            android.view.View r3 = r2.findViewById(r3)
            android.widget.ImageView r3 = (android.widget.ImageView) r3
            r1.l = r3
            android.widget.ImageView r3 = r1.l
            r3.setOnClickListener(r1)
            int r3 = com.autonavi.minimap.R.id.tv_update_type
            android.view.View r3 = r2.findViewById(r3)
            android.widget.TextView r3 = (android.widget.TextView) r3
            r1.m = r3
            android.widget.TextView r3 = r1.m
            r3.setOnClickListener(r1)
            jz r3 = r1.f
            android.widget.Button r6 = r1.k
            android.widget.TextView r7 = r1.m
            android.widget.ImageView r8 = r1.l
            jm r9 = r3.c
            boolean r9 = r9.e()
            r10 = 0
            if (r9 == 0) goto L_0x00bb
            android.content.Context r9 = r3.a
            int r11 = com.autonavi.minimap.R.string.app_downloader_install_now
            java.lang.String r9 = r9.getString(r11)
            java.lang.Object[] r11 = new java.lang.Object[r5]
            ji r12 = r3.b
            java.lang.String r12 = r12.v
            r11[r10] = r12
            java.lang.String r9 = java.lang.String.format(r9, r11)
            goto L_0x00cf
        L_0x00bb:
            android.content.Context r9 = r3.a
            int r11 = com.autonavi.minimap.R.string.app_downloader_update_now
            java.lang.String r9 = r9.getString(r11)
            java.lang.Object[] r11 = new java.lang.Object[r5]
            ji r12 = r3.b
            java.lang.String r12 = r12.v
            r11[r10] = r12
            java.lang.String r9 = java.lang.String.format(r9, r11)
        L_0x00cf:
            r6.setText(r9)
            java.lang.String r6 = ""
            ji r9 = r3.b
            boolean r9 = r9.k
            r11 = 8
            r12 = -16777216(0xffffffffff000000, float:-1.7014118E38)
            r13 = 1056964608(0x3f000000, float:0.5)
            if (r9 == 0) goto L_0x00f6
            android.content.Context r6 = r3.a
            android.content.res.Resources r6 = r6.getResources()
            int r9 = com.autonavi.minimap.R.string.app_downloader_force_update
            java.lang.String r6 = r6.getString(r9)
            r7.setAlpha(r13)
            r7.setTextColor(r12)
            r8.setVisibility(r11)
            goto L_0x0146
        L_0x00f6:
            android.app.Application r8 = com.autonavi.amap.app.AMapAppGlobal.getApplication()
            int r8 = defpackage.aaw.d(r8)
            bil r9 = defpackage.bin.a
            java.lang.String r14 = "209"
            boolean r9 = r9.o(r14)
            if (r9 != 0) goto L_0x0126
            if (r5 == r8) goto L_0x0126
            if (r8 == 0) goto L_0x0126
            android.content.Context r6 = r3.a
            android.content.res.Resources r6 = r6.getResources()
            int r8 = com.autonavi.minimap.R.string.app_downloader_wifi_hint
            java.lang.String r6 = r6.getString(r8)
            r8 = -12417025(0xffffffffff4287ff, float:-2.5857636E38)
            r7.setTextColor(r8)
            jz$1 r8 = new jz$1
            r8.<init>()
            r7.setOnClickListener(r8)
        L_0x0126:
            jm r8 = r3.c
            boolean r8 = r8.e()
            if (r8 == 0) goto L_0x0146
            boolean r8 = r3.a()
            if (r8 == 0) goto L_0x0146
            android.content.Context r6 = r3.a
            android.content.res.Resources r6 = r6.getResources()
            int r8 = com.autonavi.minimap.R.string.app_downloader_wifi_complete
            java.lang.String r6 = r6.getString(r8)
            r7.setTextColor(r12)
            r7.setAlpha(r13)
        L_0x0146:
            boolean r8 = android.text.TextUtils.isEmpty(r6)
            if (r8 != 0) goto L_0x0161
            r7.setText(r6)
            jm r6 = r3.c
            boolean r6 = r6.e()
            if (r6 == 0) goto L_0x015d
            boolean r3 = r3.a()
            if (r3 == 0) goto L_0x0161
        L_0x015d:
            r7.setVisibility(r10)
            goto L_0x0164
        L_0x0161:
            r7.setVisibility(r11)
        L_0x0164:
            com.amap.bundle.appupgrade.config.presenter.UpdatePagerAdapter r3 = new com.amap.bundle.appupgrade.config.presenter.UpdatePagerAdapter
            android.content.Context r6 = r1.a
            ji r7 = r1.e
            r3.<init>(r6, r7)
            r1.o = r3
            com.amap.bundle.appupgrade.config.presenter.UpdatePagerAdapter r3 = r1.o
            ji r6 = r3.b
            java.util.List<ji$b> r6 = r6.x
            int r6 = r6.size()
            android.view.View[] r7 = new android.view.View[r6]
            r3.c = r7
            r7 = 0
        L_0x017e:
            if (r7 >= r6) goto L_0x0270
            android.content.Context r8 = r3.a
            android.view.LayoutInflater r8 = android.view.LayoutInflater.from(r8)
            int r9 = com.autonavi.minimap.R.layout.layout_update_dialog_item
            android.view.View r8 = r8.inflate(r9, r4)
            ji r9 = r3.b
            java.util.List<ji$b> r9 = r9.x
            java.lang.Object r9 = r9.get(r7)
            ji$b r9 = (defpackage.ji.b) r9
            jy r12 = r3.d
            java.lang.String r11 = r9.f
            ji r13 = r3.b
            java.lang.String r15 = r13.s
            java.lang.String r13 = defpackage.jw.a(r15)
            java.lang.String r13 = defpackage.jw.a(r13, r9)
            int r4 = r11.hashCode()
            r14 = -2043608161(0xffffffff8631039f, float:-3.329266E-35)
            if (r4 == r14) goto L_0x01dd
            r14 = -1839152530(0xffffffff9260c26e, float:-7.092159E-28)
            if (r4 == r14) goto L_0x01d3
            r14 = 70564(0x113a4, float:9.8881E-41)
            if (r4 == r14) goto L_0x01c9
            r14 = 81665115(0x4de1c5b, float:5.221799E-36)
            if (r4 == r14) goto L_0x01bf
            goto L_0x01e7
        L_0x01bf:
            java.lang.String r4 = "VIDEO"
            boolean r4 = r11.equals(r4)
            if (r4 == 0) goto L_0x01e7
            r4 = 3
            goto L_0x01e8
        L_0x01c9:
            java.lang.String r4 = "GIF"
            boolean r4 = r11.equals(r4)
            if (r4 == 0) goto L_0x01e7
            r4 = 1
            goto L_0x01e8
        L_0x01d3:
            java.lang.String r4 = "STATIC"
            boolean r4 = r11.equals(r4)
            if (r4 == 0) goto L_0x01e7
            r4 = 0
            goto L_0x01e8
        L_0x01dd:
            java.lang.String r4 = "LOTTIE"
            boolean r4 = r11.equals(r4)
            if (r4 == 0) goto L_0x01e7
            r4 = 2
            goto L_0x01e8
        L_0x01e7:
            r4 = -1
        L_0x01e8:
            switch(r4) {
                case 0: goto L_0x0254;
                case 1: goto L_0x0237;
                case 2: goto L_0x0216;
                case 3: goto L_0x01f9;
                default: goto L_0x01eb;
            }
        L_0x01eb:
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            java.lang.String r5 = "invalid file type:"
            r4.<init>(r5)
            java.lang.String r5 = r9.f
            r4.append(r5)
            goto L_0x0266
        L_0x01f9:
            int r4 = com.autonavi.minimap.R.id.update_item_surface_view
            android.view.View r4 = r8.findViewById(r4)
            com.amap.bundle.appupgrade.config.presenter.TextureVideoView r4 = (com.amap.bundle.appupgrade.config.presenter.TextureVideoView) r4
            r4.setVisibility(r10)
            r4.setVideoPath(r13)     // Catch:{ Exception -> 0x0210 }
            jy$2 r9 = new jy$2     // Catch:{ Exception -> 0x0210 }
            r9.<init>()     // Catch:{ Exception -> 0x0210 }
            r4.setOnCompletionListener(r9)     // Catch:{ Exception -> 0x0210 }
            goto L_0x0266
        L_0x0210:
            r0 = move-exception
            r4 = r0
            r4.printStackTrace()
            goto L_0x0266
        L_0x0216:
            int r4 = com.autonavi.minimap.R.id.update_item_lottie
            android.view.View r4 = r8.findViewById(r4)
            com.airbnb.lottie.LottieAnimationView r4 = (com.airbnb.lottie.LottieAnimationView) r4
            r4.setVisibility(r10)
            r4.loop(r5)
            ahn r9 = defpackage.ahn.b()
            jy$1 r14 = new jy$1
            r11 = r14
            r5 = r14
            r14 = r8
            r16 = r15
            r15 = r4
            r11.<init>(r13, r14, r15, r16)
            r9.execute(r5)
            goto L_0x0266
        L_0x0237:
            int r4 = com.autonavi.minimap.R.id.update_item_gif
            android.view.View r4 = r8.findViewById(r4)
            pl.droidsonroids.gif.InternalGifImageView r4 = (pl.droidsonroids.gif.InternalGifImageView) r4
            r4.setVisibility(r10)
            pl.droidsonroids.gif.GifDrawable r5 = new pl.droidsonroids.gif.GifDrawable     // Catch:{ IOException -> 0x024e }
            r5.<init>(r13)     // Catch:{ IOException -> 0x024e }
            r5.stop()     // Catch:{ IOException -> 0x024e }
            r4.setImageDrawable(r5)     // Catch:{ IOException -> 0x024e }
            goto L_0x0266
        L_0x024e:
            r0 = move-exception
            r4 = r0
            r4.printStackTrace()
            goto L_0x0266
        L_0x0254:
            int r4 = com.autonavi.minimap.R.id.update_item_iv
            android.view.View r4 = r8.findViewById(r4)
            android.widget.ImageView r4 = (android.widget.ImageView) r4
            r4.setVisibility(r10)
            android.net.Uri r5 = android.net.Uri.parse(r13)
            r4.setImageURI(r5)
        L_0x0266:
            android.view.View[] r4 = r3.c
            r4[r7] = r8
            int r7 = r7 + 1
            r4 = 0
            r5 = 1
            goto L_0x017e
        L_0x0270:
            android.support.v4.view.ViewPager r3 = r1.g
            com.amap.bundle.appupgrade.config.presenter.UpdatePagerAdapter r4 = r1.o
            r3.setAdapter(r4)
            android.support.v4.view.ViewPager r3 = r1.g
            ji r4 = r1.e
            java.util.List<ji$b> r4 = r4.x
            int r4 = r4.size()
            r5 = 1
            int r4 = r4 - r5
            r3.setOffscreenPageLimit(r4)
            com.amap.bundle.appupgrade.config.presenter.UpdatePagerAdapter r3 = r1.o
            r3.notifyDataSetChanged()
            android.support.v4.view.ViewPager r3 = r1.g
            r3.addOnPageChangeListener(r1)
            int r3 = com.autonavi.minimap.R.id.userControlArea_layout
            android.view.View r3 = r2.findViewById(r3)
            android.widget.RelativeLayout r3 = (android.widget.RelativeLayout) r3
            r3.measure(r10, r10)
            android.content.Context r4 = r1.a
            android.content.res.Resources r4 = r4.getResources()
            int r5 = com.autonavi.minimap.R.dimen.update_hint_dialog_width
            int r4 = r4.getDimensionPixelOffset(r5)
            android.widget.RelativeLayout$LayoutParams r5 = new android.widget.RelativeLayout$LayoutParams
            int r6 = r3.getMeasuredHeight()
            int r6 = r6 + r4
            r5.<init>(r4, r6)
            r2.setLayoutParams(r5)
            android.widget.RelativeLayout$LayoutParams r2 = new android.widget.RelativeLayout$LayoutParams
            int r3 = r3.getMeasuredHeight()
            int r3 = r3 + r4
            r2.<init>(r4, r3)
            r3 = 10
            r2.addRule(r3)
            android.support.v4.view.ViewPager r3 = r1.g
            r3.setLayoutParams(r2)
            com.amap.bundle.appupgrade.config.presenter.UpdatePagerAdapter r2 = r1.o
            r2.a(r10)
            ji r2 = r1.e
            java.util.List<ji$b> r2 = r2.x
            java.lang.Object r2 = r2.get(r10)
            ji$b r2 = (defpackage.ji.b) r2
            android.widget.TextView r3 = r1.h
            java.lang.String r4 = r2.a
            r3.setText(r4)
            android.widget.TextView r3 = r1.i
            java.lang.String r2 = r2.b
            r3.setText(r2)
            r18.b()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.bundle.amaphome.dialog.UpdateRichTextDialog.a(android.content.Context, ji, jm, jq$a):void");
    }

    public final AlertView a() {
        if (this.d != null && this.c == null) {
            this.c = this.d.a();
            this.c.setTag("app_update_viewlayer");
            if (this.c != null) {
                View findViewById = this.c.findViewById(R.id.parentPanel);
                if (findViewById != null) {
                    findViewById.setPadding(0, 0, 0, 0);
                }
                View findViewById2 = this.c.findViewById(R.id.customPanel);
                if (findViewById2 != null) {
                    LayoutParams layoutParams = (LayoutParams) findViewById2.getLayoutParams();
                    layoutParams.setMargins(0, 0, 0, 0);
                    findViewById2.setLayoutParams(layoutParams);
                }
                View findViewById3 = this.c.findViewById(R.id.messageDivider);
                if (findViewById3 != null) {
                    findViewById3.setVisibility(8);
                }
            }
        }
        return this.c;
    }

    public void onClick(View view) {
        if (view == this.k) {
            if (this.b != null) {
                this.b.a();
            }
        } else if (view == this.l && this.b != null) {
            this.b.b();
        }
    }

    public void onPageScrolled(int i2, float f2, int i3) {
        if (f2 > 0.0f) {
            boolean z = i2 == this.q;
            if (z && this.p.length - 1 == this.q) {
                return;
            }
            if (z || this.q != 0) {
                int i4 = z ? i2 : i2 + 1;
                if (z) {
                    float f3 = 1.0f - f2;
                    if (i2 == this.q) {
                        this.n.setAlpha(f3);
                    }
                    this.p[i2].setAlpha(1.2f - f2);
                    int i5 = i2 + 1;
                    if (i5 < this.p.length) {
                        this.p[i5].setAlpha(f2 + 0.2f);
                    }
                    return;
                }
                if (i4 == this.q) {
                    this.n.setAlpha(f2);
                }
                this.p[i4].setAlpha(f2 + 0.2f);
                int i6 = i4 - 1;
                if (i6 >= 0) {
                    this.p[i6].setAlpha((1.0f - f2) + 0.2f);
                }
            }
        }
    }

    public void onPageSelected(int i2) {
        this.o.a(i2);
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("name", String.valueOf(i2 + 1));
            LogManager.actionLogV2(LogConstant.PAGE_ID_UPDATE_DIALOG, "B003", jSONObject);
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }

    public void onPageScrollStateChanged(int i2) {
        if (i2 == 0) {
            int currentItem = this.g.getCurrentItem();
            if (currentItem >= 0) {
                try {
                    if (currentItem <= this.e.x.size() - 1) {
                        for (View alpha : this.p) {
                            alpha.setAlpha(0.2f);
                        }
                        this.p[currentItem].setAlpha(1.0f);
                        if (this.q == currentItem) {
                            this.n.setAlpha(1.0f);
                            return;
                        }
                        this.h.setText(this.e.x.get(currentItem).a);
                        this.i.setText(this.e.x.get(currentItem).b);
                        AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
                        alphaAnimation.setDuration(500);
                        alphaAnimation.setAnimationListener(new AnimationListener() {
                            public final void onAnimationRepeat(Animation animation) {
                            }

                            public final void onAnimationStart(Animation animation) {
                                UpdateRichTextDialog.this.n.setAlpha(1.0f);
                            }

                            public final void onAnimationEnd(Animation animation) {
                                if (UpdateRichTextDialog.this.n != null) {
                                    UpdateRichTextDialog.this.n.clearAnimation();
                                }
                            }
                        });
                        this.n.startAnimation(alphaAnimation);
                        this.q = currentItem;
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }
    }

    private void b() {
        if (this.e.x.size() < 2) {
            this.j.setVisibility(8);
            return;
        }
        this.p = new View[this.e.x.size()];
        int dimensionPixelOffset = this.a.getResources().getDimensionPixelOffset(R.dimen.guide_dot_width_6dp);
        LayoutParams layoutParams = new LayoutParams(dimensionPixelOffset, dimensionPixelOffset);
        layoutParams.rightMargin = this.a.getResources().getDimensionPixelOffset(R.dimen.guide_dot_margin_right);
        LayoutParams layoutParams2 = new LayoutParams(dimensionPixelOffset, dimensionPixelOffset);
        this.j.removeAllViews();
        for (int i2 = 0; i2 < this.e.x.size(); i2++) {
            this.p[i2] = new View(this.a);
            if (i2 == this.e.x.size() - 1) {
                this.p[i2].setLayoutParams(layoutParams2);
            } else {
                this.p[i2].setLayoutParams(layoutParams);
            }
            this.p[i2].setBackgroundResource(R.drawable.guide_pos_points);
            this.p[i2].setAlpha(0.2f);
            this.p[i2].setTag(Integer.valueOf(i2));
            this.j.addView(this.p[i2], i2);
        }
        this.q = 0;
        this.p[this.q].setAlpha(1.0f);
    }
}
