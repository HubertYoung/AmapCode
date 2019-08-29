package com.autonavi.minimap.route.ugc.page;

import android.content.Context;
import android.text.Editable;
import android.text.Selection;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;
import com.amap.bundle.statistics.LogManager;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.bundle.account.api.IAccountService;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.map.fragmentcontainer.page.IPresenter;
import com.autonavi.minimap.R;
import com.autonavi.sdk.log.util.LogConstant;
import com.autonavi.widget.ui.TitleBar;
import java.util.ArrayList;
import java.util.List;

public abstract class ReviewPage<Presenter extends IPresenter> extends AbstractBasePage<IPresenter> implements OnClickListener {
    protected RatingBar a;
    protected EditText b;
    protected TextView c;
    protected TextView d;
    public int e;
    private TextView[] f = new TextView[5];
    private int[] g = new int[5];

    /* access modifiers changed from: protected */
    public abstract void a();

    /* access modifiers changed from: protected */
    public abstract void a(View view);

    /* access modifiers changed from: protected */
    public abstract void b(View view);

    /* access modifiers changed from: protected */
    public abstract String[] c();

    public void onCreate(Context context) {
        super.onCreate(context);
        requestScreenOrientation(1);
        setContentView(R.layout.ugc_bus_navi_review_layout);
        a();
        View contentView = getContentView();
        TitleBar titleBar = (TitleBar) contentView.findViewById(R.id.title_bar);
        titleBar.setBackImg(R.drawable.icon_a2_selector);
        titleBar.setOnBackClickListener((OnClickListener) new OnClickListener() {
            public final void onClick(View view) {
                if (ReviewPage.this.mPresenter instanceof ekh) {
                    LogManager.actionLogV2("P00266", "B002");
                }
                ReviewPage.this.setResult(ResultType.CANCEL, (PageBundle) null);
                ReviewPage.this.finish();
            }
        });
        a(contentView);
        this.a = (RatingBar) contentView.findViewById(R.id.rb_stars);
        final TextView textView = (TextView) contentView.findViewById(R.id.tv_des);
        final View findViewById = contentView.findViewById(R.id.fl_tags);
        this.a.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, float f, boolean z) {
                if (f <= 1.0f) {
                    textView.setText(ReviewPage.this.getString(R.string.ugc_rating_star_des_1));
                    ratingBar.setRating(1.0f);
                } else if (1.0f < f && f <= 2.0f) {
                    textView.setText(ReviewPage.this.getString(R.string.ugc_rating_star_des_2));
                    ratingBar.setRating(2.0f);
                } else if (2.0f < f && f <= 3.0f) {
                    textView.setText(ReviewPage.this.getString(R.string.ugc_rating_star_des_3));
                    ratingBar.setRating(3.0f);
                } else if (3.0f < f && f <= 4.0f) {
                    textView.setText(ReviewPage.this.getString(R.string.ugc_rating_star_des_4));
                    ratingBar.setRating(4.0f);
                } else if (4.0f < f && f <= 5.0f) {
                    textView.setText(ReviewPage.this.getString(R.string.ugc_rating_star_des_5));
                    ratingBar.setRating(5.0f);
                }
                if (4.0f >= f || f > 5.0f) {
                    findViewById.setVisibility(0);
                } else {
                    findViewById.setVisibility(8);
                }
            }
        });
        ekj ekj = new ekj(getContext(), R.drawable.icon_review_hint_indicator);
        StringBuilder sb = new StringBuilder("  ");
        sb.append(getString(R.string.ugc_review_hint));
        SpannableString spannableString = new SpannableString(sb.toString());
        spannableString.setSpan(ekj, 0, 1, 17);
        this.b = (EditText) contentView.findViewById(R.id.et_review);
        this.b.setHint(spannableString);
        this.b.addTextChangedListener(new TextWatcher() {
            public final void afterTextChanged(Editable editable) {
            }

            public final void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public final void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                Editable text = ReviewPage.this.b.getText();
                if (text.length() > 500) {
                    int selectionEnd = Selection.getSelectionEnd(text);
                    ReviewPage.this.b.setText(text.toString().substring(0, 500));
                    Editable text2 = ReviewPage.this.b.getText();
                    if (selectionEnd > text2.length()) {
                        selectionEnd = text2.length();
                    }
                    Selection.setSelection(text2, selectionEnd);
                    ToastHelper.showToast("已超出评论上限");
                }
            }
        });
        c(contentView);
        this.c = (TextView) contentView.findViewById(R.id.tv_name);
        this.d = (TextView) contentView.findViewById(R.id.tv_from_user);
        this.c.setOnClickListener(this);
        i();
        b(contentView);
        contentView.findViewById(R.id.scroll_child).setOnClickListener(new OnClickListener() {
            public final void onClick(View view) {
                ((InputMethodManager) ReviewPage.this.getActivity().getSystemService("input_method")).hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        });
    }

    private void c(View view) {
        this.f[0] = (TextView) view.findViewById(R.id.tv_tag_0);
        this.f[1] = (TextView) view.findViewById(R.id.tv_tag_1);
        this.f[2] = (TextView) view.findViewById(R.id.tv_tag_2);
        this.f[3] = (TextView) view.findViewById(R.id.tv_tag_3);
        this.f[4] = (TextView) view.findViewById(R.id.tv_tag_4);
        for (TextView onClickListener : this.f) {
            onClickListener.setOnClickListener(this);
        }
        String[] c2 = c();
        int min = Math.min(this.f.length, 5);
        for (int i = 0; i < min; i++) {
            this.f[i].setText(c2[i]);
        }
    }

    public void onClick(View view) {
        if (view.getId() == R.id.tv_name) {
            IAccountService iAccountService = (IAccountService) a.a.a(IAccountService.class);
            if (iAccountService != null) {
                if (!d()) {
                    LogManager.actionLogV2(LogConstant.PAGE_MORE, "B010");
                    iAccountService.a(getPageContext(), (anq) new anq() {
                        public final void loginOrBindCancel() {
                        }

                        public final void onComplete(boolean z) {
                            if (z) {
                                brn brn = (brn) ank.a(brn.class);
                                if (brn != null) {
                                    brn.b();
                                }
                            }
                        }
                    });
                    return;
                }
                LogManager.actionLogV2(LogConstant.PAGE_MORE, "B008");
                iAccountService.b(getPageContext());
            }
            return;
        }
        view.setSelected(!view.isSelected());
        int i = 0;
        for (int i2 = 0; i2 < this.f.length; i2++) {
            if (this.f[i2] == view) {
                int[] iArr = this.g;
                if (iArr[i2] == 0) {
                    i = 1;
                }
                iArr[i2] = i;
                return;
            }
        }
    }

    /* access modifiers changed from: protected */
    public final List<Integer> e() {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < this.g.length; i++) {
            if (this.g[i] == 1) {
                arrayList.add(Integer.valueOf(i));
            }
        }
        return arrayList;
    }

    private void i() {
        if (!d()) {
            this.d.setVisibility(8);
            this.c.setText(getString(R.string.ugc_user_name_unlogin));
        } else if (!TextUtils.isEmpty(h())) {
            this.d.setVisibility(0);
            this.c.setText(h());
        } else if (!TextUtils.isEmpty(g())) {
            this.d.setVisibility(0);
            this.c.setText(g());
        }
    }

    public void b() {
        i();
    }

    public final void f() {
        this.e = getActivity().getWindow().getAttributes().softInputMode;
    }

    private static boolean d() {
        IAccountService iAccountService = (IAccountService) a.a.a(IAccountService.class);
        return iAccountService != null && iAccountService.a();
    }

    private static String g() {
        IAccountService iAccountService = (IAccountService) a.a.a(IAccountService.class);
        if (iAccountService == null) {
            return "";
        }
        ant e2 = iAccountService.e();
        if (e2 == null) {
            return "";
        }
        return e2.c;
    }

    private static String h() {
        IAccountService iAccountService = (IAccountService) a.a.a(IAccountService.class);
        if (iAccountService == null) {
            return "";
        }
        ant e2 = iAccountService.e();
        if (e2 == null) {
            return "";
        }
        return e2.e;
    }
}
