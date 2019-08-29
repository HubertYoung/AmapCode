package com.autonavi.minimap.bundle.frequentlocation.view;

import android.content.Context;
import android.support.v4.internal.view.SupportMenu;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.autonavi.map.template.AbstractViewHolderBaseAdapter;
import com.autonavi.minimap.R;
import com.autonavi.minimap.bundle.frequentlocation.entity.FrequentLocationData;
import java.util.ArrayList;
import java.util.List;

public class FrequentLocationAdapter2 extends AbstractViewHolderBaseAdapter<FrequentLocationData, c> {
    /* access modifiers changed from: private */
    public static final int MAX_TO_ADD = 15;
    public static boolean mIsTesting = false;
    /* access modifiers changed from: private */
    public a mAdapterListener;
    /* access modifiers changed from: private */
    public Context mContext;
    a mEditViewHolderOwner = new a() {
        public final void a() {
            FrequentLocationAdapter2.this.setIsEditMode(true);
            FrequentLocationAdapter2.this.notifyDataSetChanged();
        }

        public final void b() {
            FrequentLocationAdapter2.this.setIsEditMode(false);
            FrequentLocationAdapter2.this.notifyDataSetChanged();
        }

        public final void c() {
            if (FrequentLocationAdapter2.this.mAdapterListener != null) {
                FrequentLocationAdapter2.this.mAdapterListener.d();
            }
        }

        public final boolean d() {
            return FrequentLocationAdapter2.this.mIsEditMode;
        }

        public final boolean e() {
            return FrequentLocationAdapter2.this.getCount() == 0;
        }

        public final boolean f() {
            List<FrequentLocationData> cloudSyncData = FrequentLocationAdapter2.this.getCloudSyncData(FrequentLocationAdapter2.MAX_TO_ADD);
            return cloudSyncData != null && cloudSyncData.size() >= FrequentLocationAdapter2.MAX_TO_ADD;
        }
    };
    a mHomeCompanyViewHolderOwner = new a() {
        public final void a(FrequentLocationData frequentLocationData) {
            if (FrequentLocationAdapter2.this.mAdapterListener != null) {
                FrequentLocationAdapter2.this.mAdapterListener.a(frequentLocationData);
            }
        }

        public final void a() {
            if (FrequentLocationAdapter2.this.mAdapterListener != null) {
                FrequentLocationAdapter2.this.mAdapterListener.a();
            }
        }

        public final void b() {
            if (FrequentLocationAdapter2.this.mAdapterListener != null) {
                FrequentLocationAdapter2.this.mAdapterListener.b();
            }
        }

        public final Context c() {
            return FrequentLocationAdapter2.this.mContext;
        }

        public final boolean d() {
            return FrequentLocationAdapter2.this.mIsEditMode;
        }

        public final boolean e() {
            return !FrequentLocationAdapter2.this.mEditViewHolderOwner.e();
        }
    };
    /* access modifiers changed from: private */
    public boolean mIsEditMode = false;
    a mNormalViewHolderOwner = new a() {
        public final boolean a() {
            return FrequentLocationAdapter2.this.mIsEditMode;
        }

        public final void a(FrequentLocationData frequentLocationData) {
            if (FrequentLocationAdapter2.this.mAdapterListener != null) {
                FrequentLocationAdapter2.this.mAdapterListener.a(frequentLocationData);
            }
        }

        public final void b(FrequentLocationData frequentLocationData) {
            if (FrequentLocationAdapter2.this.mAdapterListener != null) {
                FrequentLocationAdapter2.this.mAdapterListener.b(frequentLocationData);
            }
        }

        public final int b() {
            return FrequentLocationAdapter2.this.getCount();
        }
    };
    a mSearchViewHolderOwner = new a() {
        public final void a() {
            if (FrequentLocationAdapter2.this.mAdapterListener != null) {
                FrequentLocationAdapter2.this.mAdapterListener.c();
            }
        }
    };

    enum VIEW_TYPE {
        NORMAL,
        HOME,
        COMPANY,
        SEARCH,
        EDIT,
        MAX
    }

    public interface a {
        void a();

        void a(FrequentLocationData frequentLocationData);

        void a(boolean z);

        void b();

        void b(FrequentLocationData frequentLocationData);

        void c();

        void d();
    }

    public static class b extends c {
        a a;
        OnClickListener b = new OnClickListener() {
            public final void onClick(View view) {
                if (b.this.a != null) {
                    b.this.a.a();
                }
            }
        };
        OnClickListener c = new OnClickListener() {
            public final void onClick(View view) {
                if (b.this.a != null) {
                    b.this.a.b();
                }
            }
        };
        OnClickListener d = new OnClickListener() {
            public final void onClick(View view) {
                if (b.this.a != null) {
                    b.this.a.c();
                }
            }
        };
        OnClickListener e = new OnClickListener() {
            public final void onClick(View view) {
            }
        };
        private View g;
        private View h;
        private View i;
        private View j;
        private View k;
        private View l;

        public interface a {
            void a();

            void b();

            void c();

            boolean d();

            boolean e();

            boolean f();
        }

        public final /* bridge */ /* synthetic */ void b(FrequentLocationData frequentLocationData) {
            super.b(frequentLocationData);
        }

        public b(View view, a aVar) {
            super(view);
            this.a = aVar;
            this.g = view.findViewById(R.id.to_add_state_view);
            this.h = view.findViewById(R.id.view_state_view);
            this.i = view.findViewById(R.id.edit_state_view);
            this.j = view.findViewById(R.id.frequent_location_icon_add_view);
            this.k = view.findViewById(R.id.item_frequent_location_add_view);
            this.l = view.findViewById(R.id.item_frequent_location_finish_view);
            this.g.setOnClickListener(this.d);
            this.h.setOnClickListener(this.b);
            this.i.setOnClickListener(this.e);
            this.j.setOnClickListener(this.d);
            this.k.setOnClickListener(this.d);
            this.l.setOnClickListener(this.c);
        }

        /* access modifiers changed from: protected */
        public final void a(FrequentLocationData frequentLocationData) {
            boolean z = false;
            boolean d2 = this.a != null ? this.a.d() : false;
            boolean e2 = this.a != null ? this.a.e() : true;
            boolean f = this.a != null ? this.a.f() : false;
            int i2 = 8;
            this.g.setVisibility((d2 || !e2) ? 8 : 0);
            this.h.setVisibility((d2 || e2) ? 8 : 0);
            View view = this.i;
            if (d2) {
                i2 = 0;
            }
            view.setVisibility(i2);
            this.j.setEnabled(!f);
            View view2 = this.k;
            if (!f) {
                z = true;
            }
            view2.setEnabled(z);
        }
    }

    static abstract class c extends com.autonavi.map.template.AbstractViewHolderAdapter.a {
        protected FrequentLocationData f = null;

        /* access modifiers changed from: protected */
        public abstract void a(FrequentLocationData frequentLocationData);

        public c(View view) {
            super(view);
        }

        public void b(FrequentLocationData frequentLocationData) {
            this.f = frequentLocationData;
            a(frequentLocationData);
        }
    }

    public static class d extends c {
        ImageView a;
        TextView b;
        TextView c;
        TextView d;
        View e;
        View g;
        a h;
        OnClickListener i = new OnClickListener() {
            public final void onClick(View view) {
                if (!(d.this.f == null || d.this.h == null)) {
                    if (!TextUtils.isEmpty(d.this.f.c()) && !d.this.h.d()) {
                        d.this.h.a(d.this.f);
                    } else if (d.this.f.a == 1001) {
                        d.this.h.a();
                    } else if (d.this.f.a == 1002) {
                        d.this.h.b();
                    }
                }
            }
        };
        OnClickListener j = new OnClickListener() {
            public final void onClick(View view) {
                if (d.this.f != null) {
                    if (d.this.f.a == 1001) {
                        if (d.this.h != null) {
                            d.this.h.a();
                        }
                    } else if (d.this.f.a == 1002 && d.this.h != null) {
                        d.this.h.b();
                    }
                }
            }
        };

        public interface a {
            void a();

            void a(FrequentLocationData frequentLocationData);

            void b();

            Context c();

            boolean d();

            boolean e();
        }

        public final /* bridge */ /* synthetic */ void b(FrequentLocationData frequentLocationData) {
            super.b(frequentLocationData);
        }

        public d(View view, a aVar) {
            super(view);
            this.h = aVar;
            this.a = (ImageView) view.findViewById(R.id.frequent_location_icon_view);
            this.b = (TextView) view.findViewById(R.id.frequent_location_type_name_view);
            this.c = (TextView) view.findViewById(R.id.frequent_location_home_company_name_tip_view);
            this.d = (TextView) view.findViewById(R.id.frequent_location_home_company_name_text_view);
            this.e = view.findViewById(R.id.frequent_location_home_company_edit_icon_view);
            this.g = view.findViewById(R.id.frequent_location_line_view);
            view.setOnClickListener(this.i);
            this.e.setOnClickListener(this.j);
        }

        /* access modifiers changed from: protected */
        public final void a(FrequentLocationData frequentLocationData) {
            String str;
            boolean z = true;
            int i2 = 8;
            if (frequentLocationData.a == 1001) {
                this.a.setImageResource(R.drawable.icon_frequent_location_home);
                TextView textView = this.b;
                if (this.h != null) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(this.h.c().getString(R.string.home_location_type_name));
                    sb.append("　");
                    str = sb.toString();
                } else {
                    str = "";
                }
                textView.setText(str);
                String c2 = frequentLocationData.c();
                boolean isEmpty = TextUtils.isEmpty(c2);
                this.d.setText(c2);
                this.c.setText(R.string.home_location_type_name_edit_hint);
                this.c.setVisibility(isEmpty ? 0 : 8);
                this.d.setVisibility(isEmpty ? 8 : 0);
                this.b.setVisibility(isEmpty ? 0 : 8);
                if (this.h == null || (!this.h.d() && (TextUtils.isEmpty(c2) || this.h.e()))) {
                    z = false;
                }
                View view = this.e;
                if (z) {
                    i2 = 0;
                }
                view.setVisibility(i2);
                return;
            }
            if (frequentLocationData.a == 1002) {
                this.a.setImageResource(R.drawable.icon_frequent_location_company);
                this.b.setText(this.h != null ? this.h.c().getString(R.string.company_location_type_name) : "");
                String c3 = frequentLocationData.c();
                boolean isEmpty2 = TextUtils.isEmpty(c3);
                this.d.setText(c3);
                this.c.setText(R.string.company_location_type_name_edit_hint);
                this.c.setVisibility(isEmpty2 ? 0 : 8);
                this.d.setVisibility(isEmpty2 ? 8 : 0);
                this.b.setVisibility(isEmpty2 ? 0 : 8);
                if (this.h == null || (!this.h.d() && (TextUtils.isEmpty(c3) || this.h.e()))) {
                    z = false;
                }
                this.e.setVisibility(z ? 0 : 8);
                if (this.h != null) {
                    this.g.setVisibility(0);
                    return;
                }
                this.g.setVisibility(8);
            }
        }
    }

    public static class e extends c {
        a a;
        OnClickListener b = new OnClickListener() {
            public final void onClick(View view) {
                if (e.this.f != null && e.this.a != null) {
                    e.this.a.a(e.this.f);
                }
            }
        };
        OnClickListener c = new OnClickListener() {
            public final void onClick(View view) {
                if (e.this.f != null && e.this.a != null) {
                    e.this.a.b(e.this.f);
                }
            }
        };
        private View d;
        private TextView e;
        private View g;
        private View h;
        private View i;

        public interface a {
            void a(FrequentLocationData frequentLocationData);

            boolean a();

            int b();

            void b(FrequentLocationData frequentLocationData);
        }

        public final /* bridge */ /* synthetic */ void b(FrequentLocationData frequentLocationData) {
            super.b(frequentLocationData);
        }

        e(View view, a aVar) {
            super(view);
            this.a = aVar;
            this.d = view.findViewById(R.id.drag_edit_state_view);
            this.e = (TextView) view.findViewById(R.id.frequent_location_name_normal_edit_text_view);
            this.g = view.findViewById(R.id.frequent_location_icon_normal_edit_del_view);
            this.h = view.findViewById(R.id.frequent_location_icon_normal_view);
            this.i = view.findViewById(R.id.frequent_location_icon_normal_edit_head_view);
            this.g.setOnClickListener(this.c);
        }

        /* access modifiers changed from: protected */
        public final void a(FrequentLocationData frequentLocationData) {
            this.e.setText(frequentLocationData.c());
            if (FrequentLocationAdapter2.mIsTesting) {
                this.e.setTextColor(frequentLocationData.b ? SupportMenu.CATEGORY_MASK : -16776961);
            }
            int i2 = 0;
            boolean a2 = this.a != null ? this.a.a() : false;
            int i3 = 4;
            this.h.setVisibility(a2 ? 4 : 0);
            View view = this.i;
            if (a2 && this.a != null && this.a.b() > 1) {
                i3 = 0;
            }
            view.setVisibility(i3);
            View view2 = this.g;
            if (!a2) {
                i2 = 8;
            }
            view2.setVisibility(i2);
        }
    }

    public static class f extends c {
        a a;
        OnClickListener b = new OnClickListener() {
            public final void onClick(View view) {
                if (f.this.a != null) {
                    f.this.a.a();
                }
            }
        };
        private View c;

        public interface a {
            void a();
        }

        /* access modifiers changed from: protected */
        public final void a(FrequentLocationData frequentLocationData) {
        }

        public final /* bridge */ /* synthetic */ void b(FrequentLocationData frequentLocationData) {
            super.b(frequentLocationData);
        }

        public f(View view, a aVar) {
            super(view);
            this.a = aVar;
            this.c = view.findViewById(R.id.item_frequent_location_search_view);
            this.c.setOnClickListener(this.b);
        }
    }

    public int getDivPosition() {
        return 1;
    }

    public FrequentLocationAdapter2(Context context) {
        this.mContext = context;
    }

    public void setAdapterListener(a aVar) {
        this.mAdapterListener = aVar;
    }

    private VIEW_TYPE getViewType(int i) {
        switch (i) {
            case 1001:
                return VIEW_TYPE.HOME;
            case 1002:
                return VIEW_TYPE.COMPANY;
            case 10003:
                return VIEW_TYPE.SEARCH;
            case 10004:
                return VIEW_TYPE.EDIT;
            default:
                return VIEW_TYPE.NORMAL;
        }
    }

    public int getItemViewHolderType(int i) {
        FrequentLocationData frequentLocationData = (FrequentLocationData) getDataByPos(i);
        if (frequentLocationData != null) {
            return getViewType(frequentLocationData.a).ordinal();
        }
        return getViewType(1000).ordinal();
    }

    public int getViewHolderTypeCount() {
        return VIEW_TYPE.MAX.ordinal() + 1;
    }

    public void onBindViewHolderWithData(c cVar, FrequentLocationData frequentLocationData, int i, int i2) {
        if (frequentLocationData != null) {
            cVar.b(frequentLocationData);
        }
    }

    public View onCreateView(ViewGroup viewGroup, int i) {
        if (i == VIEW_TYPE.HOME.ordinal()) {
            return LayoutInflater.from(this.mContext).inflate(R.layout.item_frequent_location_home_company_layout, viewGroup, false);
        }
        if (i == VIEW_TYPE.COMPANY.ordinal()) {
            return LayoutInflater.from(this.mContext).inflate(R.layout.item_frequent_location_home_company_layout, viewGroup, false);
        }
        if (i == VIEW_TYPE.SEARCH.ordinal()) {
            return LayoutInflater.from(this.mContext).inflate(R.layout.item_frequent_location_search_layout, viewGroup, false);
        }
        if (i == VIEW_TYPE.EDIT.ordinal()) {
            return LayoutInflater.from(this.mContext).inflate(R.layout.item_frequent_location_edit_layout, viewGroup, false);
        }
        return LayoutInflater.from(this.mContext).inflate(R.layout.item_frequent_location_normal_layout2, viewGroup, false);
    }

    public c onCreateViewHolder(View view, ViewGroup viewGroup, int i) {
        if (i == VIEW_TYPE.HOME.ordinal()) {
            return new d(view, this.mHomeCompanyViewHolderOwner);
        }
        if (i == VIEW_TYPE.COMPANY.ordinal()) {
            return new d(view, this.mHomeCompanyViewHolderOwner);
        }
        if (i == VIEW_TYPE.SEARCH.ordinal()) {
            return new f(view, this.mSearchViewHolderOwner);
        }
        if (i == VIEW_TYPE.EDIT.ordinal()) {
            return new b(view, this.mEditViewHolderOwner);
        }
        return new e(view, this.mNormalViewHolderOwner);
    }

    public void setIsEditMode(boolean z) {
        if (this.mIsEditMode != z) {
            this.mIsEditMode = z;
            if (this.mAdapterListener != null) {
                this.mAdapterListener.a(this.mIsEditMode);
            }
        }
    }

    public boolean getIsEditMode() {
        return this.mIsEditMode;
    }

    public List<FrequentLocationData> getCloudSyncData(int i) {
        ArrayList arrayList = new ArrayList();
        List<FrequentLocationData> data = getData();
        if (data != null) {
            int i2 = 0;
            for (FrequentLocationData frequentLocationData : data) {
                if (i >= 0 && i2 >= i) {
                    break;
                } else if (frequentLocationData != null && frequentLocationData.b) {
                    arrayList.add(frequentLocationData);
                    i2++;
                }
            }
        }
        return arrayList;
    }

    public a getHomeCompanyViewHolderOwner() {
        return this.mHomeCompanyViewHolderOwner;
    }

    public a getSearchViewHolderOwner() {
        return this.mSearchViewHolderOwner;
    }

    public a getEditViewHolderOwner() {
        return this.mEditViewHolderOwner;
    }

    public a getNormalViewHolderOwner() {
        return this.mNormalViewHolderOwner;
    }

    private boolean justHomeCompanyData() {
        return getCount() == 2;
    }

    public int getPosition(FrequentLocationData frequentLocationData) {
        if (getData() != null) {
            return getData().indexOf(frequentLocationData);
        }
        return -1;
    }

    public void onDismissLocation() {
        setIsEditMode(false);
    }
}
