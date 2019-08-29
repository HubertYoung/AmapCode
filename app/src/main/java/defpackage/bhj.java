package defpackage;

import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import com.amap.bundle.drivecommon.payfor.PayforNaviData;
import com.amap.bundle.utils.ui.NoDBClickUtil;
import com.autonavi.bundle.account.api.IAccountService;
import com.autonavi.carowner.payfor.ApplyPayForLocationErrorFragment;
import com.autonavi.carowner.payfor.ApplyPayForLocationErrorFragment.ErrorType;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.POI;
import com.autonavi.minimap.R;
import com.autonavi.widget.ui.TitleBar;

/* renamed from: bhj reason: default package */
/* compiled from: ApplyPayForLocationErrorPresenter */
public final class bhj extends sw<ApplyPayForLocationErrorFragment, bhd> {
    public bhj(ApplyPayForLocationErrorFragment applyPayForLocationErrorFragment) {
        super(applyPayForLocationErrorFragment);
    }

    public final void onPageCreated() {
        ApplyPayForLocationErrorFragment applyPayForLocationErrorFragment = (ApplyPayForLocationErrorFragment) this.mPage;
        PageBundle arguments = applyPayForLocationErrorFragment.getArguments();
        if (arguments != null) {
            applyPayForLocationErrorFragment.m = (ErrorType) arguments.getObject(ApplyPayForLocationErrorFragment.b);
            applyPayForLocationErrorFragment.k = (PayforNaviData) arguments.getObject(ApplyPayForLocationErrorFragment.a);
            View contentView = applyPayForLocationErrorFragment.getContentView();
            applyPayForLocationErrorFragment.h = (TitleBar) contentView.findViewById(R.id.title);
            applyPayForLocationErrorFragment.h.setOnBackClickListener((OnClickListener) new OnClickListener() {
                public final void onClick(View view) {
                    ApplyPayForLocationErrorFragment.this.finish();
                }
            });
            applyPayForLocationErrorFragment.h.setOnActionClickListener(new OnClickListener() {
                public final void onClick(View view) {
                    ApplyPayForLocationErrorFragment.this.a();
                    ApplyPayForLocationErrorFragment.f(ApplyPayForLocationErrorFragment.this);
                }
            });
            applyPayForLocationErrorFragment.i = contentView.findViewById(R.id.btnFetchPoint);
            NoDBClickUtil.a(applyPayForLocationErrorFragment.i, applyPayForLocationErrorFragment.q);
            applyPayForLocationErrorFragment.j = (TextView) contentView.findViewById(R.id.selectedTv);
            applyPayForLocationErrorFragment.j.setVisibility(4);
            applyPayForLocationErrorFragment.d = (TextView) contentView.findViewById(R.id.limit);
            applyPayForLocationErrorFragment.c = (EditText) contentView.findViewById(R.id.description);
            applyPayForLocationErrorFragment.c.removeTextChangedListener(applyPayForLocationErrorFragment.p);
            applyPayForLocationErrorFragment.c.addTextChangedListener(applyPayForLocationErrorFragment.p);
            applyPayForLocationErrorFragment.g = (EditText) contentView.findViewById(R.id.contact);
            applyPayForLocationErrorFragment.g.removeTextChangedListener(applyPayForLocationErrorFragment.p);
            applyPayForLocationErrorFragment.g.addTextChangedListener(applyPayForLocationErrorFragment.p);
            applyPayForLocationErrorFragment.e = (TextView) contentView.findViewById(R.id.yourContactTv);
            View contentView2 = applyPayForLocationErrorFragment.getContentView();
            if (applyPayForLocationErrorFragment.m == ErrorType.KNOW_LOCATION) {
                applyPayForLocationErrorFragment.i.setVisibility(0);
                applyPayForLocationErrorFragment.h.setActionTextEnable(false);
            } else {
                applyPayForLocationErrorFragment.i.setVisibility(8);
            }
            applyPayForLocationErrorFragment.d.setText(String.format(applyPayForLocationErrorFragment.getString(R.string.describe_word_limit), new Object[]{Integer.valueOf(300)}));
            if (applyPayForLocationErrorFragment.m == ErrorType.UNKNOWN_LOCATION) {
                EditText editText = applyPayForLocationErrorFragment.c;
                StringBuilder sb = new StringBuilder();
                sb.append(applyPayForLocationErrorFragment.getString(R.string.oper_cant_find));
                sb.append(applyPayForLocationErrorFragment.k.toAddress);
                editText.setText(sb.toString());
            }
            String str = "";
            IAccountService iAccountService = (IAccountService) a.a.a(IAccountService.class);
            if (iAccountService != null) {
                ant e = iAccountService.e();
                if (e != null) {
                    str = e.h;
                }
            }
            if (!TextUtils.isEmpty(str)) {
                applyPayForLocationErrorFragment.g.setText(str);
            }
            bhb.a((TextView) contentView2.findViewById(R.id.applyTip), (TextView) contentView2.findViewById(R.id.look_over_activities_view), applyPayForLocationErrorFragment.k, applyPayForLocationErrorFragment);
            applyPayForLocationErrorFragment.f = new ForegroundColorSpan(applyPayForLocationErrorFragment.getResources().getColor(R.color.c_10));
            TextView textView = applyPayForLocationErrorFragment.e;
            int i = R.string.your_contact;
            StringBuilder sb2 = new StringBuilder();
            sb2.append(applyPayForLocationErrorFragment.getContext().getString(i));
            sb2.append("＊");
            String sb3 = sb2.toString();
            int length = sb3.length();
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(sb3);
            spannableStringBuilder.setSpan(applyPayForLocationErrorFragment.f, length - 1, length, 33);
            textView.setText(spannableStringBuilder);
        }
    }

    public final void onDestroy() {
        ((ApplyPayForLocationErrorFragment) this.mPage).a();
    }

    public final void onResult(int i, ResultType resultType, PageBundle pageBundle) {
        ApplyPayForLocationErrorFragment applyPayForLocationErrorFragment = (ApplyPayForLocationErrorFragment) this.mPage;
        if (i != 10001) {
            switch (i) {
                case 1:
                    if (resultType == ResultType.OK) {
                        applyPayForLocationErrorFragment.setResult(ResultType.OK, (PageBundle) null);
                        String str = "";
                        if (pageBundle != null) {
                            str = pageBundle.getString("soure_from_page", "");
                        }
                        if (!"submit".equals(str)) {
                            POI poi = (POI) pageBundle.getObject("SelectFixPoiFromMapFragment.MapClickResult");
                            applyPayForLocationErrorFragment.n = poi;
                            EditText editText = applyPayForLocationErrorFragment.c;
                            StringBuilder sb = new StringBuilder();
                            sb.append(applyPayForLocationErrorFragment.k.toAddress);
                            sb.append(applyPayForLocationErrorFragment.getString(R.string.oper_place_err));
                            sb.append(poi.getName());
                            editText.setText(sb.toString());
                            applyPayForLocationErrorFragment.j.setVisibility(0);
                            applyPayForLocationErrorFragment.p.afterTextChanged(null);
                            break;
                        } else {
                            applyPayForLocationErrorFragment.finish();
                            return;
                        }
                    }
                    break;
                case 2:
                    applyPayForLocationErrorFragment.setResult(ResultType.OK, (PageBundle) null);
                    applyPayForLocationErrorFragment.finish();
                    return;
            }
            return;
        }
        applyPayForLocationErrorFragment.setResult(ResultType.OK, (PageBundle) null);
        applyPayForLocationErrorFragment.finish();
    }

    public final /* synthetic */ su a() {
        return new bhd(this);
    }
}
