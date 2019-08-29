package defpackage;

import android.graphics.Bitmap;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.amap.bundle.drivecommon.payfor.PayforNaviData;
import com.amap.bundle.utils.ui.NoDBClickUtil;
import com.autonavi.bundle.account.api.IAccountService;
import com.autonavi.carowner.payfor.ApplyPayForFragment;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.minimap.R;
import java.io.FileNotFoundException;

/* renamed from: bhk reason: default package */
/* compiled from: ApplyPayForPresenter */
public final class bhk extends sw<ApplyPayForFragment, bhe> {
    public final void onStart() {
    }

    public bhk(ApplyPayForFragment applyPayForFragment) {
        super(applyPayForFragment);
    }

    public final void onPageCreated() {
        ApplyPayForFragment applyPayForFragment = (ApplyPayForFragment) this.mPage;
        View contentView = applyPayForFragment.getContentView();
        applyPayForFragment.a.a = contentView.findViewById(R.id.title_btn_left);
        ((TextView) contentView.findViewById(R.id.title_text_name)).setText(R.string.oper_payfor);
        applyPayForFragment.a.b = (TextView) contentView.findViewById(R.id.doconfirmmappoint);
        applyPayForFragment.a.b.setEnabled(false);
        applyPayForFragment.a.c = (TextView) contentView.findViewById(R.id.photo_thumbnail_layout_title);
        applyPayForFragment.a.d = (TextView) contentView.findViewById(R.id.demo_hint);
        TextView textView = applyPayForFragment.a.d;
        StringBuilder sb = new StringBuilder("<u>");
        sb.append(applyPayForFragment.getString(R.string.oper_demo));
        sb.append("</u>");
        textView.setText(Html.fromHtml(sb.toString()));
        applyPayForFragment.a.e = contentView.findViewById(R.id.add_image);
        applyPayForFragment.a.f = (ImageView) contentView.findViewById(R.id.photo_has_taken);
        applyPayForFragment.a.g = (EditText) contentView.findViewById(R.id.description);
        applyPayForFragment.a.h = (TextView) contentView.findViewById(R.id.limit);
        applyPayForFragment.a.h.setText(String.format(applyPayForFragment.getString(R.string.describe_word_limit), new Object[]{Integer.valueOf(300)}));
        applyPayForFragment.a.i = (TextView) contentView.findViewById(R.id.phone_number_tv);
        applyPayForFragment.a.j = (EditText) contentView.findViewById(R.id.phone_number);
        applyPayForFragment.a.k = (TextView) contentView.findViewById(R.id.applyTip);
        applyPayForFragment.a.l = (TextView) contentView.findViewById(R.id.look_over_activities_view);
        PageBundle arguments = applyPayForFragment.getArguments();
        if (arguments != null) {
            applyPayForFragment.b = (PayforNaviData) arguments.getObject("naviData");
            if (applyPayForFragment.b != null) {
                applyPayForFragment.a.b.setText(R.string.submit);
                applyPayForFragment.a.b.setVisibility(0);
                EditText editText = applyPayForFragment.a.g;
                StringBuilder sb2 = new StringBuilder();
                sb2.append(applyPayForFragment.b.toAddress);
                sb2.append(applyPayForFragment.getString(R.string.activities_apply_payfor_type_wrongplace));
                editText.setText(sb2.toString());
                applyPayForFragment.a.c.setText(Html.fromHtml(applyPayForFragment.getString(R.string.activities_take_photo_at_right_place)));
                String str = "";
                IAccountService iAccountService = (IAccountService) a.a.a(IAccountService.class);
                if (iAccountService != null) {
                    ant e = iAccountService.e();
                    if (e != null) {
                        str = e.h;
                    }
                }
                if (!TextUtils.isEmpty(str)) {
                    applyPayForFragment.a.j.setText(str);
                }
                applyPayForFragment.a.b.setEnabled(applyPayForFragment.a());
                bhb.a(applyPayForFragment.a.k, applyPayForFragment.a.l, applyPayForFragment.b, applyPayForFragment);
                applyPayForFragment.d = new ForegroundColorSpan(applyPayForFragment.getResources().getColor(R.color.c_10));
                TextView textView2 = applyPayForFragment.a.i;
                int i = R.string.your_contact;
                StringBuilder sb3 = new StringBuilder();
                sb3.append(applyPayForFragment.getContext().getString(i));
                sb3.append("＊");
                String sb4 = sb3.toString();
                int length = sb4.length();
                SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(sb4);
                spannableStringBuilder.setSpan(applyPayForFragment.d, length - 1, length, 33);
                textView2.setText(spannableStringBuilder);
            }
        }
        applyPayForFragment.a.a.setOnClickListener(applyPayForFragment);
        NoDBClickUtil.a((View) applyPayForFragment.a.b, applyPayForFragment.e);
        applyPayForFragment.a.d.setOnClickListener(applyPayForFragment);
        NoDBClickUtil.a(applyPayForFragment.a.e, applyPayForFragment.e);
        applyPayForFragment.a.f.setOnClickListener(applyPayForFragment);
        applyPayForFragment.a.g.addTextChangedListener(applyPayForFragment);
        applyPayForFragment.a.g.setOnClickListener(applyPayForFragment);
        applyPayForFragment.a.j.addTextChangedListener(applyPayForFragment);
        applyPayForFragment.a.j.setOnClickListener(applyPayForFragment);
    }

    public final void onDestroy() {
        ((ApplyPayForFragment) this.mPage).b();
    }

    public final void onResult(int i, ResultType resultType, PageBundle pageBundle) {
        ApplyPayForFragment applyPayForFragment = (ApplyPayForFragment) this.mPage;
        if (i == 10001) {
            applyPayForFragment.setResult(ResultType.OK, (PageBundle) null);
            applyPayForFragment.finish();
        }
        if (i == 1) {
            applyPayForFragment.setResult(ResultType.OK, (PageBundle) null);
            applyPayForFragment.finish();
        }
        if (i == 100 && resultType == ResultType.OK) {
            applyPayForFragment.c = pageBundle.getString("pick_photo_result");
            if (TextUtils.isEmpty(applyPayForFragment.c)) {
                applyPayForFragment.a.e.setVisibility(0);
                return;
            }
            try {
                Bitmap a = kp.a(applyPayForFragment.c, ahc.b(applyPayForFragment.c), (int) (((float) ags.a(applyPayForFragment.getContext()).width()) * 0.2f), (int) (((float) ags.a(applyPayForFragment.getContext()).height()) * 0.2f));
                int c = ahc.c(applyPayForFragment.c);
                if (a != null) {
                    if (c == 0) {
                        applyPayForFragment.a.f.setImageBitmap(a);
                    } else {
                        applyPayForFragment.a.f.setImageBitmap(ahc.a(c, a));
                    }
                }
            } catch (FileNotFoundException e) {
                kf.a((Throwable) e);
            }
            applyPayForFragment.a.e.setVisibility(8);
            applyPayForFragment.a.f.setVisibility(0);
        }
    }

    public final /* synthetic */ su a() {
        return new bhe(this);
    }
}
