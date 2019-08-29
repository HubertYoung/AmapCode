package defpackage;

import android.graphics.Rect;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.URLSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.amap.bundle.datamodel.poi.POIFactory;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.minimap.R;
import com.autonavi.minimap.drive.sticker.StickerShareFragment;
import com.autonavi.minimap.drive.sticker.StickerTimeTableView;
import com.autonavi.minimap.drive.sticker.page.StickerDetailPage;
import com.autonavi.minimap.drive.sticker.page.StickerDetailPage.a;
import com.autonavi.widget.ui.TitleBar;

/* renamed from: djc reason: default package */
/* compiled from: StickerDetailPresenter */
public final class djc extends sw<StickerDetailPage, diw> implements OnClickListener {
    public djc(StickerDetailPage stickerDetailPage) {
        super(stickerDetailPage);
    }

    public final void onPageCreated() {
        URLSpan[] uRLSpanArr;
        super.onPageCreated();
        StickerDetailPage stickerDetailPage = (StickerDetailPage) this.mPage;
        View contentView = stickerDetailPage.getContentView();
        TitleBar titleBar = (TitleBar) contentView.findViewById(R.id.title);
        titleBar.setOnBackClickListener((OnClickListener) new OnClickListener() {
            public final void onClick(View view) {
                StickerDetailPage.this.finish();
            }
        });
        titleBar.setOnActionClickListener(new OnClickListener() {
            public final void onClick(View view) {
                StickerDetailPage.this.finish();
            }
        });
        stickerDetailPage.b = (ImageView) contentView.findViewById(R.id.status_img);
        stickerDetailPage.c = (TextView) contentView.findViewById(R.id.sticker_count_tv);
        stickerDetailPage.d = (TextView) contentView.findViewById(R.id.sticker_count_tip_tv);
        stickerDetailPage.e = (TextView) contentView.findViewById(R.id.sticker_position_tv);
        stickerDetailPage.f = (TextView) contentView.findViewById(R.id.sticker_last_time_tv);
        stickerDetailPage.g = (TextView) contentView.findViewById(R.id.sticker_type_tv);
        stickerDetailPage.h = (TextView) contentView.findViewById(R.id.sticker_parking_search);
        stickerDetailPage.i = (TextView) contentView.findViewById(R.id.sticker_navi_to);
        stickerDetailPage.j = (TextView) contentView.findViewById(R.id.sticker_share);
        stickerDetailPage.a = (StickerTimeTableView) contentView.findViewById(R.id.sticker_timer_table);
        stickerDetailPage.k = (TextView) contentView.findViewById(R.id.data_source);
        stickerDetailPage.l = (FrameLayout) contentView.findViewById(R.id.strict_control_frame);
        stickerDetailPage.m = (TextView) contentView.findViewById(R.id.strict_control_detail);
        stickerDetailPage.n = (LinearLayout) contentView.findViewById(R.id.normal_mode_frame);
        diw diw = (diw) this.b;
        PageBundle arguments = ((StickerDetailPage) this.mPage).getArguments();
        if (arguments != null && arguments.containsKey("StickerDetailFragment.sticker_data")) {
            diw.c = (div) arguments.getObject("StickerDetailFragment.sticker_data");
            if (arguments.containsKey("StickerDetailFragment.sticker_resultdata")) {
                diw.d = (djg) arguments.getObject("StickerDetailFragment.sticker_resultdata");
            }
        }
        StickerDetailPage stickerDetailPage2 = (StickerDetailPage) this.mPage;
        div div = ((diw) this.b).c;
        djg djg = ((diw) this.b).d;
        if (div != null) {
            if (djg != null && !TextUtils.isEmpty(djg.a(div.n))) {
                String replace = stickerDetailPage2.getString(R.string.sticker_data_source).replace("xxx", djg.a(div.n));
                stickerDetailPage2.k.setText(Html.fromHtml(replace));
                stickerDetailPage2.k.setMovementMethod(LinkMovementMethod.getInstance());
                try {
                    CharSequence text = stickerDetailPage2.k.getText();
                    if (text instanceof Spannable) {
                        Spannable spannable = (Spannable) stickerDetailPage2.k.getText();
                        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(text);
                        spannableStringBuilder.clearSpans();
                        for (URLSpan uRLSpan : (URLSpan[]) spannable.getSpans(0, text.length(), URLSpan.class)) {
                            spannableStringBuilder.setSpan(new a(uRLSpan.getURL()), spannable.getSpanStart(uRLSpan), spannable.getSpanEnd(uRLSpan), 34);
                        }
                        stickerDetailPage2.k.setText(spannableStringBuilder);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    stickerDetailPage2.k.setText(Html.fromHtml(replace));
                    stickerDetailPage2.k.setMovementMethod(LinkMovementMethod.getInstance());
                }
            }
            if (div.g < 3) {
                TextView textView = stickerDetailPage2.c;
                StringBuilder sb = new StringBuilder();
                sb.append(div.k);
                textView.setText(sb.toString());
                stickerDetailPage2.d.setVisibility(0);
                stickerDetailPage2.n.setVisibility(0);
                stickerDetailPage2.l.setVisibility(8);
            } else {
                stickerDetailPage2.c.setText(stickerDetailPage2.getString(R.string.stickers_strict_control_string));
                stickerDetailPage2.d.setVisibility(8);
                stickerDetailPage2.n.setVisibility(8);
                stickerDetailPage2.l.setVisibility(0);
                stickerDetailPage2.m.setText(div.m);
            }
            stickerDetailPage2.e.setText(div.b);
            TextView textView2 = stickerDetailPage2.f;
            StringBuilder sb2 = new StringBuilder();
            sb2.append(div.j);
            textView2.setText(sb2.toString());
            TextView textView3 = stickerDetailPage2.g;
            StringBuilder sb3 = new StringBuilder();
            sb3.append(div.l);
            textView3.setText(sb3.toString());
            switch (div.g) {
                case 0:
                    stickerDetailPage2.b.setImageResource(R.drawable.sticker_tips_safe);
                    break;
                case 1:
                    stickerDetailPage2.b.setImageResource(R.drawable.sticker_tips_normal);
                    break;
                case 2:
                    stickerDetailPage2.b.setImageResource(R.drawable.sticker_tips_danger);
                    break;
                case 3:
                    stickerDetailPage2.b.setImageResource(R.drawable.sticker_tips_strict_control);
                    break;
            }
            if (stickerDetailPage2.a != null) {
                stickerDetailPage2.a.setStickerTimerInfo(div);
            }
        }
        StickerDetailPage stickerDetailPage3 = (StickerDetailPage) this.mPage;
        stickerDetailPage3.j.setOnClickListener((OnClickListener) stickerDetailPage3.mPresenter);
        stickerDetailPage3.i.setOnClickListener((OnClickListener) stickerDetailPage3.mPresenter);
        stickerDetailPage3.h.setOnClickListener((OnClickListener) stickerDetailPage3.mPresenter);
    }

    public final void onClick(View view) {
        if (view.getId() == R.id.sticker_share) {
            PageBundle pageBundle = new PageBundle();
            pageBundle.putObject("StickerDetailFragment.sticker_data", ((diw) this.b).c);
            ((StickerDetailPage) this.mPage).startPage(StickerShareFragment.class, pageBundle);
        } else if (view.getId() == R.id.sticker_navi_to) {
            if (((diw) this.b).c != null) {
                POI createPOI = POIFactory.createPOI(((diw) this.b).c.b, new GeoPoint(((diw) this.b).c.c, ((diw) this.b).c.d));
                dfm dfm = (dfm) ank.a(dfm.class);
                if (dfm != null) {
                    dfm.a(createPOI);
                }
            }
        } else if (view.getId() == R.id.sticker_parking_search && ((diw) this.b).c != null) {
            String string = ((StickerDetailPage) this.mPage).getString(R.string.car_scene_parking);
            GeoPoint geoPoint = new GeoPoint(((diw) this.b).c.c, ((diw) this.b).c.d);
            Rect rect = new Rect(geoPoint.x - 100, geoPoint.y - 100, geoPoint.x + 100, geoPoint.y + 100);
            ael ael = new ael(string, geoPoint);
            ael.e = bbw.a(rect);
            bck bck = (bck) a.a.a(bck.class);
            if (bck != null) {
                bck.a(ael, 2);
            }
        }
    }

    public final /* synthetic */ su a() {
        return new diw(this);
    }
}
