package com.autonavi.minimap.route.train.page;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager.BadTokenException;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.alipay.mobile.common.share.widget.ResUtils;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.map.fragmentcontainer.page.IPresenter;
import com.autonavi.minimap.R;
import com.autonavi.minimap.route.train.adapter.TrainTicketListAdapter;
import com.autonavi.minimap.route.train.model.TrainTicketGeneralInfoItem;
import com.autonavi.minimap.route.train.model.TrainTypeItem;
import com.autonavi.minimap.route.train.model.TrainTypeItem.TrainType;
import com.autonavi.minimap.route.train.presenter.TrainManager;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;

public class TrainTicketListPage extends AbstractBasePage<ejo> {
    public String a = "";
    public String b = "";
    public ImageButton c;
    public TextView d;
    public View e;
    public View f;
    public ejy g;
    public ListView h;
    /* access modifiers changed from: private */
    public List<TrainTicketGeneralInfoItem> i;
    /* access modifiers changed from: private */
    public List<TrainTypeItem> j = new ArrayList();
    private List<eix> k;
    /* access modifiers changed from: private */
    public final TextView[] l = new TextView[4];
    /* access modifiers changed from: private */
    public final LinearLayout[] m = new LinearLayout[4];
    /* access modifiers changed from: private */
    public TrainTicketListAdapter n;
    /* access modifiers changed from: private */
    public int o = 0;
    private ejg p = new ejg();
    private int q = -1;

    /* renamed from: com.autonavi.minimap.route.train.page.TrainTicketListPage$4 reason: invalid class name */
    static /* synthetic */ class AnonymousClass4 {
        static final /* synthetic */ int[] a = new int[TrainType.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(6:0|1|2|3|4|6) */
        /* JADX WARNING: Code restructure failed: missing block: B:7:?, code lost:
            return;
         */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0014 */
        static {
            /*
                com.autonavi.minimap.route.train.model.TrainTypeItem$TrainType[] r0 = com.autonavi.minimap.route.train.model.TrainTypeItem.TrainType.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                a = r0
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x0014 }
                com.autonavi.minimap.route.train.model.TrainTypeItem$TrainType r1 = com.autonavi.minimap.route.train.model.TrainTypeItem.TrainType.ALL     // Catch:{ NoSuchFieldError -> 0x0014 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0014 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0014 }
            L_0x0014:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x001f }
                com.autonavi.minimap.route.train.model.TrainTypeItem$TrainType r1 = com.autonavi.minimap.route.train.model.TrainTypeItem.TrainType.OTHERS     // Catch:{ NoSuchFieldError -> 0x001f }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001f }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001f }
            L_0x001f:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.route.train.page.TrainTicketListPage.AnonymousClass4.<clinit>():void");
        }
    }

    public void onCreate(Context context) {
        super.onCreate(context);
        setContentView(R.layout.train_ticket_layout);
    }

    public final void a() {
        PageBundle arguments = getArguments();
        if (arguments != null) {
            this.i = (List) arguments.getObject("result list");
            this.n = new TrainTicketListAdapter(getPageContext(), this.o);
            this.h.setAdapter(this.n);
            this.n.setDataAndChangeDataSet(this.i);
            HashSet hashSet = new HashSet();
            this.j.clear();
            this.j.add(new TrainTypeItem(TrainType.ALL));
            for (TrainTicketGeneralInfoItem a2 : this.i) {
                TrainType a3 = TrainManager.a(a2);
                if (!hashSet.contains(a3)) {
                    hashSet.add(a3);
                    this.j.add(new TrainTypeItem(a3));
                }
            }
            Collections.sort(this.j, new Comparator<TrainTypeItem>() {
                public final /* bridge */ /* synthetic */ int compare(Object obj, Object obj2) {
                    TrainTypeItem trainTypeItem = (TrainTypeItem) obj;
                    TrainTypeItem trainTypeItem2 = (TrainTypeItem) obj2;
                    if (trainTypeItem != trainTypeItem2) {
                        if (trainTypeItem.c < trainTypeItem2.c) {
                            return -1;
                        }
                        if (trainTypeItem.c > trainTypeItem2.c) {
                            return 1;
                        }
                    }
                    return 0;
                }
            });
            if (arguments.containsKey("departure") && arguments.containsKey("destination")) {
                this.a = arguments.getString("departure");
                this.b = arguments.getString("destination");
                StringBuilder sb = new StringBuilder();
                sb.append(this.a);
                sb.append("-");
                sb.append(this.b);
                this.d.setText(sb.toString());
            }
        }
    }

    public final void b() {
        int i2 = 0;
        while (i2 < this.l.length) {
            TextView[] textViewArr = this.l;
            View view = this.e;
            StringBuilder sb = new StringBuilder("caption");
            int i3 = i2 + 1;
            sb.append(i3);
            textViewArr[i2] = (TextView) view.findViewWithTag(sb.toString());
            LinearLayout[] linearLayoutArr = this.m;
            View view2 = this.e;
            StringBuilder sb2 = new StringBuilder(ResUtils.LAYOUT);
            sb2.append(i3);
            linearLayoutArr[i2] = (LinearLayout) view2.findViewWithTag(sb2.toString());
            if (this.m[i2] != null) {
                this.m[i2].setOnClickListener(new OnClickListener() {
                    public final void onClick(View view) {
                        for (int i = 0; i < TrainTicketListPage.this.l.length; i++) {
                            if (TrainTicketListPage.this.m[i].equals(view)) {
                                TrainTicketListPage.this.g.a(i);
                                ejy c = TrainTicketListPage.this.g;
                                try {
                                    switch (c.a) {
                                        case 0:
                                            if (!c.d.isShowing()) {
                                                c.d.showAsDropDown(c.c);
                                                if (c.e.getVisibility() == 0) {
                                                    break;
                                                } else {
                                                    new Handler().postDelayed(new Runnable() {
                                                        public final void run() {
                                                            ejy.this.c();
                                                        }
                                                    }, 200);
                                                    break;
                                                }
                                            } else {
                                                c.d();
                                                c.d.dismiss();
                                                break;
                                            }
                                        case 1:
                                            if (!c.d.isShowing()) {
                                                c.d.showAsDropDown(c.c);
                                                if (c.g.getVisibility() == 0) {
                                                    break;
                                                } else {
                                                    new Handler().postDelayed(new Runnable() {
                                                        public final void run() {
                                                            ejy.this.a();
                                                        }
                                                    }, 200);
                                                    break;
                                                }
                                            } else {
                                                c.b();
                                                c.d.dismiss();
                                                break;
                                            }
                                    }
                                } catch (IllegalStateException e) {
                                    kf.a((Throwable) e);
                                } catch (BadTokenException unused) {
                                }
                            }
                        }
                    }
                });
            }
            i2 = i3;
        }
    }

    public final void c() {
        eix eix = new eix();
        eix.a = 0;
        eix.b = getString(R.string.tt_ticket_list_default_today);
        eix eix2 = new eix();
        eix2.a = 1;
        eix2.b = getString(R.string.tt_ticket_list_all_types);
        eix2.c = this.j;
        this.k = new ArrayList();
        this.k.add(eix);
        this.k.add(eix2);
        if (this.k == null || this.k.size() <= 0) {
            this.e.setVisibility(8);
            return;
        }
        this.e.setVisibility(0);
        this.g.a(this.k);
        for (int i2 = 0; i2 < this.l.length; i2++) {
            if (i2 < this.k.size()) {
                eix eix3 = this.k.get(i2);
                if (eix3 != null) {
                    this.l[i2].setText(eix3.b);
                    this.l[i2].setEnabled(true);
                    this.m[i2].setEnabled(true);
                    this.l[i2].setTextColor(getActivity().getResources().getColorStateList(R.color.filter_text_click_selector));
                } else {
                    this.l[i2].setEnabled(false);
                    this.m[i2].setEnabled(false);
                }
            } else {
                this.l[i2].setVisibility(8);
                this.m[i2].setVisibility(8);
            }
        }
    }

    public /* synthetic */ IPresenter createPresenter() {
        return new ejo(this);
    }

    static /* synthetic */ String a(TrainTicketListPage trainTicketListPage, String str) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(str, Locale.CHINA);
        Calendar instance = Calendar.getInstance();
        instance.set(6, instance.get(6) + trainTicketListPage.o);
        return simpleDateFormat.format(instance.getTime());
    }
}
