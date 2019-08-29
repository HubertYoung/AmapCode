package defpackage;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import com.autonavi.minimap.R;
import com.autonavi.minimap.route.train.model.TrainTypeItem;
import com.autonavi.minimap.route.train.page.TrainTicketListPage;

/* renamed from: ejo reason: default package */
/* compiled from: TrainTicketListPresenter */
public final class ejo extends eaf<TrainTicketListPage> implements OnClickListener {
    public ejo(TrainTicketListPage trainTicketListPage) {
        super(trainTicketListPage);
    }

    public final void onPageCreated() {
        super.onPageCreated();
        TrainTicketListPage trainTicketListPage = (TrainTicketListPage) this.mPage;
        View contentView = trainTicketListPage.getContentView();
        trainTicketListPage.c = (ImageButton) contentView.findViewById(R.id.title_btn_left);
        trainTicketListPage.c.setBackgroundResource(R.drawable.title_bar_back);
        trainTicketListPage.c.setOnClickListener((OnClickListener) trainTicketListPage.mPresenter);
        trainTicketListPage.d = (TextView) contentView.findViewById(R.id.title_text_name);
        trainTicketListPage.d.setTextColor(trainTicketListPage.getContext().getResources().getColor(R.color.blue));
        StringBuilder sb = new StringBuilder();
        sb.append(trainTicketListPage.a);
        sb.append("-");
        sb.append(trainTicketListPage.b);
        trainTicketListPage.d.setText(sb.toString());
        trainTicketListPage.f = contentView.findViewById(R.id.title_split_line);
        trainTicketListPage.e = contentView.findViewById(R.id.tab_layout);
        trainTicketListPage.g = new ejy(trainTicketListPage.f);
        trainTicketListPage.b();
        trainTicketListPage.c();
        trainTicketListPage.g.h = new a() {
            public final void a(int i, int i2, int i3) {
                switch (i) {
                    case 0:
                        TrainTicketListPage.this.o = i3;
                        TrainTicketListPage.this.n.onSelectedDateChanged(TrainTicketListPage.this.o);
                        TrainTicketListPage.this.h.setSelection(0);
                        String string = i3 == 0 ? TrainTicketListPage.this.getString(R.string.tt_ticket_list_default_today) : TrainTicketListPage.a(TrainTicketListPage.this, TrainTicketListPage.this.getString(R.string.tt_ticket_list_purchase_date));
                        TrainTicketListPage.this.l[0].setText(string);
                        TrainTicketListPage.this.g.a(i, string);
                        return;
                    case 1:
                        TrainTicketListPage.this.n.setData(TrainTicketListPage.this.i);
                        TrainTypeItem trainTypeItem = (TrainTypeItem) TrainTicketListPage.this.j.get(i2);
                        switch (AnonymousClass4.a[trainTypeItem.a.ordinal()]) {
                            case 1:
                                TrainTicketListPage.this.n.getFilter().filter("ALL");
                                break;
                            case 2:
                                TrainTicketListPage.this.n.getFilter().filter("OTHERS");
                                break;
                            default:
                                TrainTicketListPage.this.n.getFilter().filter(((TrainTypeItem) TrainTicketListPage.this.j.get(i2)).b);
                                break;
                        }
                        TrainTicketListPage.this.l[1].setText(trainTypeItem.b);
                        TrainTicketListPage.this.g.a(i, trainTypeItem.b);
                        TrainTicketListPage.this.h.setSelection(0);
                        break;
                }
            }
        };
        trainTicketListPage.h = (ListView) contentView.findViewById(R.id.list);
        trainTicketListPage.a();
    }

    public final void onStart() {
        super.onStart();
        ((TrainTicketListPage) this.mPage).requestScreenOrientation(1);
    }

    public final void onClick(View view) {
        if (view.getId() == R.id.title_btn_left) {
            ((TrainTicketListPage) this.mPage).finish();
        }
    }
}
