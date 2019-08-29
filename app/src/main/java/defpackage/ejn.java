package defpackage;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.URLSpan;
import android.text.style.UnderlineSpan;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.autonavi.minimap.R;
import com.autonavi.minimap.route.train.page.TrainSearchPage;
import com.autonavi.minimap.route.train.page.TrainSearchPage.SearchType;
import com.autonavi.minimap.route.train.view.TrainSearchInfoView;
import com.autonavi.minimap.route.train.view.TrainSearchStationEndView;
import com.autonavi.minimap.route.train.view.TrainSearchStationStartView;

/* renamed from: ejn reason: default package */
/* compiled from: TrainSearchPresenter */
public final class ejn extends eaf<TrainSearchPage> implements OnClickListener, OnKeyListener {
    public SearchType a;

    public ejn(TrainSearchPage trainSearchPage) {
        super(trainSearchPage);
    }

    public final void onPageCreated() {
        super.onPageCreated();
        this.a = SearchType.TICKET_LIST;
        TrainSearchPage trainSearchPage = (TrainSearchPage) this.mPage;
        View contentView = trainSearchPage.getContentView();
        View findViewById = contentView.findViewById(R.id.title_layout);
        if (euk.a()) {
            contentView.setPadding(contentView.getPaddingLeft(), contentView.getPaddingTop() + euk.a(trainSearchPage.getContext()), contentView.getPaddingRight(), contentView.getPaddingBottom());
            findViewById.setBackgroundColor(-1);
        }
        contentView.findViewById(R.id.title_btn_right).setOnClickListener((OnClickListener) trainSearchPage.mPresenter);
        ((TextView) contentView.findViewById(R.id.title_text_name)).setText(trainSearchPage.getString(R.string.tt_train_search));
        trainSearchPage.a = contentView.findViewById(R.id.btnStation);
        trainSearchPage.b = contentView.findViewById(R.id.btnNo);
        trainSearchPage.i = (TextView) contentView.findViewById(R.id.no_title);
        trainSearchPage.j = (TextView) contentView.findViewById(R.id.station_title);
        trainSearchPage.f = (TrainSearchStationStartView) contentView.findViewById(R.id.train_search_startStation);
        trainSearchPage.g = (TrainSearchStationEndView) contentView.findViewById(R.id.train_search_endStation);
        trainSearchPage.h = (TrainSearchInfoView) contentView.findViewById(R.id.train_search_no);
        trainSearchPage.f.setTrainDlg(trainSearchPage);
        trainSearchPage.g.setTrainDlg(trainSearchPage);
        trainSearchPage.c = (EditText) trainSearchPage.f.findViewById(R.id.search_text);
        trainSearchPage.c.requestFocus();
        trainSearchPage.c.setOnKeyListener((OnKeyListener) trainSearchPage.mPresenter);
        trainSearchPage.d = (EditText) trainSearchPage.g.findViewById(R.id.search_end_text);
        trainSearchPage.d.setOnKeyListener((OnKeyListener) trainSearchPage.mPresenter);
        trainSearchPage.e = (EditText) trainSearchPage.h.findViewById(R.id.search_text);
        trainSearchPage.e.setOnKeyListener(trainSearchPage.p);
        trainSearchPage.c.setHint(trainSearchPage.getContext().getResources().getString(R.string.train_start_hint));
        trainSearchPage.d.setHint(trainSearchPage.getContext().getResources().getString(R.string.train_end_hint));
        trainSearchPage.e.setHint(trainSearchPage.getContext().getResources().getString(R.string.train_no_hint));
        trainSearchPage.k = (ImageButton) contentView.findViewById(R.id.title_btn_left);
        trainSearchPage.k.setBackgroundResource(R.drawable.title_bar_back);
        trainSearchPage.k.setOnClickListener((OnClickListener) trainSearchPage.mPresenter);
        trainSearchPage.a.setEnabled(false);
        trainSearchPage.b.setEnabled(true);
        trainSearchPage.j.setTextColor(trainSearchPage.getContext().getResources().getColor(R.color.blue));
        trainSearchPage.i.setTextColor(trainSearchPage.getContext().getResources().getColor(R.color.gray));
        trainSearchPage.a.setOnClickListener((OnClickListener) trainSearchPage.mPresenter);
        trainSearchPage.b.setOnClickListener((OnClickListener) trainSearchPage.mPresenter);
        trainSearchPage.a(SearchType.TICKET_LIST);
        trainSearchPage.l = (TextView) contentView.findViewById(R.id.autonavi_announcement);
        trainSearchPage.m = (LinearLayout) contentView.findViewById(R.id.announcement_content);
        trainSearchPage.n = (TextView) contentView.findViewById(R.id.announcement_page_link);
        trainSearchPage.o = (TextView) contentView.findViewById(R.id.announcement_tel);
        Drawable drawable = trainSearchPage.getResources().getDrawable(R.drawable.directions_more_up_blue);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        Drawable drawable2 = trainSearchPage.getResources().getDrawable(R.drawable.directions_more_down_blue);
        drawable2.setBounds(0, 0, drawable2.getMinimumWidth(), drawable2.getMinimumHeight());
        trainSearchPage.l.setOnClickListener(new OnClickListener(drawable, drawable2) {
            final /* synthetic */ Drawable a;
            final /* synthetic */ Drawable b;

            {
                this.a = r2;
                this.b = r3;
            }

            public final void onClick(View view) {
                if (TrainSearchPage.this.m.getVisibility() == 4) {
                    TrainSearchPage.this.m.setVisibility(0);
                    TrainSearchPage.this.l.setCompoundDrawables(null, null, this.a, null);
                    return;
                }
                TrainSearchPage.this.m.setVisibility(4);
                TrainSearchPage.this.l.setCompoundDrawables(null, null, this.b, null);
            }
        });
        SpannableString spannableString = new SpannableString(trainSearchPage.getString(R.string.tt_disclaimer_agreement_caption));
        int length = spannableString.length();
        spannableString.setSpan(new AbsoluteSizeSpan(12, true), 0, length, 33);
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#ff999999")), 0, 25, 33);
        spannableString.setSpan(new UnderlineSpan(), 25, length, 33);
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#ff0091ff")), 25, length, 33);
        spannableString.setSpan(new URLSpan(trainSearchPage.getString(R.string.tt_disclaimer_agreement_link)), 25, length, 33);
        trainSearchPage.n.setText(spannableString);
        trainSearchPage.n.setMovementMethod(LinkMovementMethod.getInstance());
        SpannableString spannableString2 = new SpannableString(trainSearchPage.getString(R.string.tt_disclaimer_hot_line_caption));
        int length2 = spannableString2.length();
        spannableString2.setSpan(new AbsoluteSizeSpan(12, true), 0, length2, 33);
        spannableString2.setSpan(new ForegroundColorSpan(Color.parseColor("#ff999999")), 0, 28, 33);
        spannableString2.setSpan(new UnderlineSpan(), 28, length2, 33);
        spannableString2.setSpan(new ForegroundColorSpan(Color.parseColor("#ff0091ff")), 28, length2, 33);
        spannableString2.setSpan(new URLSpan(trainSearchPage.getString(R.string.tt_disclaimer_hot_line_number)), 28, length2, 33);
        trainSearchPage.o.setText(spannableString2);
        trainSearchPage.o.setMovementMethod(LinkMovementMethod.getInstance());
    }

    public final void onClick(View view) {
        if (view.getId() == R.id.title_btn_right) {
            if (this.a == SearchType.TICKET_LIST) {
                ((TrainSearchPage) this.mPage).b();
            } else if (this.a == SearchType.TRAIN_INFO) {
                ((TrainSearchPage) this.mPage).c();
            }
        } else if (view.getId() == R.id.btnStation) {
            this.a = SearchType.TICKET_LIST;
            ((TrainSearchPage) this.mPage).a(this.a);
        } else if (view.getId() == R.id.btnNo) {
            this.a = SearchType.TRAIN_INFO;
            ((TrainSearchPage) this.mPage).a(this.a);
        } else if (view.getId() == R.id.title_btn_left) {
            ((TrainSearchPage) this.mPage).a();
        }
    }

    public final boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (i == 66) {
            if (this.a == SearchType.TICKET_LIST) {
                ((TrainSearchPage) this.mPage).b();
            } else if (this.a == SearchType.TRAIN_INFO) {
                ((TrainSearchPage) this.mPage).c();
            }
            return true;
        } else if (i != 4) {
            return super.onKeyDown(i, keyEvent);
        } else {
            ((TrainSearchPage) this.mPage).a();
            return true;
        }
    }

    public final boolean onKey(View view, int i, KeyEvent keyEvent) {
        if (i == 66 && keyEvent.getAction() == 1) {
            if (this.a == SearchType.TICKET_LIST) {
                ((TrainSearchPage) this.mPage).b();
            } else if (this.a == SearchType.TRAIN_INFO) {
                ((TrainSearchPage) this.mPage).c();
            }
            return true;
        }
        if (i == 4 && keyEvent.getAction() == 1) {
            if (view.getId() == R.id.search_text) {
                TrainSearchPage trainSearchPage = (TrainSearchPage) this.mPage;
                if (!trainSearchPage.c.hasFocus()) {
                    return false;
                }
                trainSearchPage.c.clearFocus();
                return true;
            } else if (view.getId() == R.id.search_end_text) {
                TrainSearchPage trainSearchPage2 = (TrainSearchPage) this.mPage;
                if (!trainSearchPage2.d.hasFocus()) {
                    return false;
                }
                trainSearchPage2.d.clearFocus();
                return true;
            }
        }
        return false;
    }
}
