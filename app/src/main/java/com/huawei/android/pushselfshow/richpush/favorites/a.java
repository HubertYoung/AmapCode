package com.huawei.android.pushselfshow.richpush.favorites;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import com.huawei.android.pushselfshow.utils.a.c;
import com.huawei.android.pushselfshow.utils.d;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class a extends BaseAdapter {
    private Context a;
    private boolean b = true;
    private boolean c = false;
    private List d = new ArrayList();

    /* renamed from: com.huawei.android.pushselfshow.richpush.favorites.a$a reason: collision with other inner class name */
    static class C0060a {
        ImageView a;
        TextView b;
        TextView c;
        CheckBox d;

        private C0060a() {
        }
    }

    public a(Context context) {
        this.a = context;
    }

    /* renamed from: a */
    public e getItem(int i) {
        return (e) this.d.get(i);
    }

    public List a() {
        return this.d;
    }

    public void a(int i, e eVar) {
        try {
            if (this.d.size() >= i) {
                this.d.set(i, eVar);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void a(boolean z) {
        this.b = z;
        notifyDataSetChanged();
    }

    public void a(boolean z, Set set) {
        this.c = z;
        int i = 0;
        for (e eVar : this.d) {
            if (set == null || !set.contains(Integer.valueOf(i))) {
                eVar.a(z);
            } else {
                eVar.a(!z);
            }
            a(i, eVar);
            i++;
        }
        notifyDataSetChanged();
    }

    public void b() {
        this.d = c.a(this.a, (String) null);
    }

    public int getCount() {
        return this.d.size();
    }

    public long getItemId(int i) {
        return (long) i;
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        C0060a aVar;
        if (view == null) {
            try {
                aVar = new C0060a();
                View inflate = ((LayoutInflater) this.a.getSystemService("layout_inflater")).inflate(d.c(this.a, "hwpush_collection_item"), null);
                try {
                    aVar.a = (ImageView) inflate.findViewById(d.e(this.a, "hwpush_favicon"));
                    aVar.b = (TextView) inflate.findViewById(d.e(this.a, "hwpush_selfshowmsg_title"));
                    aVar.c = (TextView) inflate.findViewById(d.e(this.a, "hwpush_selfshowmsg_content"));
                    aVar.d = (CheckBox) inflate.findViewById(d.e(this.a, "hwpush_delCheck"));
                    inflate.setTag(aVar);
                    view = inflate;
                } catch (Exception e) {
                    e = e;
                    view = inflate;
                    com.huawei.android.pushagent.a.a.c.b("PushSelfShowLog", e.toString());
                    return view;
                }
            } catch (Exception e2) {
                e = e2;
                com.huawei.android.pushagent.a.a.c.b("PushSelfShowLog", e.toString());
                return view;
            }
        } else {
            aVar = (C0060a) view.getTag();
        }
        Bitmap d2 = ((e) this.d.get(i)).d();
        if (d2 == null) {
            d2 = BitmapFactory.decodeResource(this.a.getResources(), d.g(this.a, "hwpush_main_icon"));
        }
        aVar.a.setBackgroundDrawable(new BitmapDrawable(this.a.getResources(), d2));
        String str = ((e) this.d.get(i)).b().s;
        if (str != null && str.length() > 0) {
            aVar.b.setText(str);
        }
        String str2 = ((e) this.d.get(i)).b().q;
        if (str2 != null && str2.length() > 0) {
            aVar.c.setText(str2);
        }
        if (this.b) {
            aVar.d.setVisibility(4);
            return view;
        }
        aVar.d.setVisibility(0);
        if (!this.c) {
            if (!((e) this.d.get(i)).a()) {
                aVar.d.setChecked(false);
                return view;
            }
        }
        aVar.d.setChecked(true);
        return view;
    }
}
