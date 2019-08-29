package com.ali.user.mobile.ui.widget;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.ali.user.mobile.security.ui.R;
import com.alipay.mobile.tinyappcommon.template.TemplateTinyApp;
import java.util.ArrayList;
import java.util.Iterator;

public class APListPopDialog extends Dialog implements android.widget.AdapterView.OnItemClickListener {
    private final int a;
    private final Context b;
    private OnItemClickListener c;
    /* access modifiers changed from: private */
    public ArrayList<PopMenuItem> d = new ArrayList<>();
    private View e;
    /* access modifiers changed from: private */
    public final LayoutInflater f;
    private String g = "";
    private ListAdapter h;

    final class ListAdapter extends BaseAdapter {
        public final long getItemId(int i) {
            return (long) i;
        }

        private ListAdapter() {
        }

        /* synthetic */ ListAdapter(APListPopDialog aPListPopDialog, byte b) {
            this();
        }

        public final int getCount() {
            return APListPopDialog.this.d.size();
        }

        public final Object getItem(int i) {
            return APListPopDialog.this.d.get(i);
        }

        public final View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder viewHolder;
            if (view == null) {
                view = APListPopDialog.this.f.inflate(R.layout.D, null);
                viewHolder = new ViewHolder(0);
                viewHolder.b = (ImageView) view.findViewById(R.id.am);
                viewHolder.a = (TextView) view.findViewById(R.id.an);
                viewHolder.d = (ImageView) view.findViewById(R.id.ao);
                viewHolder.c = (RelativeLayout) view.findViewById(R.id.ai);
                view.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }
            PopMenuItem popMenuItem = (PopMenuItem) APListPopDialog.this.d.get(i);
            if (popMenuItem == null) {
                return view;
            }
            viewHolder.a.setText(popMenuItem.a());
            APListPopDialog.a(viewHolder.b, popMenuItem);
            APListPopDialog.a(viewHolder.d, popMenuItem.d());
            if (i == APListPopDialog.this.d.size()) {
                view.setBackgroundResource(R.drawable.C);
            }
            return view;
        }
    }

    public interface OnItemClickListener {
        void a(int i);
    }

    static class ViewHolder {
        TextView a;
        ImageView b;
        RelativeLayout c;
        ImageView d;

        private ViewHolder() {
        }

        /* synthetic */ ViewHolder(byte b2) {
            this();
        }
    }

    public APListPopDialog(Context context, ArrayList<String> arrayList) {
        super(context, R.style.h);
        this.b = context;
        this.f = LayoutInflater.from(context);
        this.a = this.b.getResources().getDimensionPixelSize(R.dimen.p);
        if (arrayList != null) {
            Iterator<String> it = arrayList.iterator();
            while (it.hasNext()) {
                this.d.add(new PopMenuItem(it.next()));
            }
        }
    }

    public APListPopDialog(String str, ArrayList<PopMenuItem> arrayList, Context context) {
        super(context, R.style.h);
        this.g = str;
        this.b = context;
        this.f = LayoutInflater.from(context);
        this.a = this.b.getResources().getDimensionPixelSize(R.dimen.p);
        if (arrayList != null) {
            this.d = arrayList;
        }
    }

    public final void a(OnItemClickListener onItemClickListener) {
        this.c = onItemClickListener;
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        View inflate = this.f.inflate(R.layout.C, null);
        this.e = inflate;
        ListView listView = (ListView) inflate.findViewById(R.id.U);
        this.h = new ListAdapter(this, 0);
        listView.setAdapter(this.h);
        listView.setOnItemClickListener(this);
        TextView textView = (TextView) inflate.findViewById(R.id.as);
        if (!TextUtils.isEmpty(this.g)) {
            textView.setText(this.g);
            textView.setVisibility(0);
            inflate.findViewById(R.id.at).setVisibility(0);
        }
    }

    public void show() {
        int i;
        super.show();
        setContentView(this.e);
        int width = ((WindowManager) this.b.getSystemService(TemplateTinyApp.WINDOW_KEY)).getDefaultDisplay().getWidth() - this.b.getResources().getDimensionPixelSize(R.dimen.r);
        if (this.d != null && TextUtils.isEmpty(this.g) && this.d.size() >= 6) {
            i = this.a + 5;
        } else if (this.d == null || TextUtils.isEmpty(this.g) || this.d.size() < 5) {
            i = -2;
        } else {
            i = this.a;
        }
        getWindow().setLayout(width, i);
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
        APPopClickTimeRecoder.a();
        if (this.c != null) {
            this.c.a(i);
        }
        dismiss();
    }

    static /* synthetic */ void a(ImageView imageView, PopMenuItem popMenuItem) {
        int b2 = popMenuItem.b();
        if (b2 != 0) {
            imageView.setImageResource(b2);
            imageView.setVisibility(0);
            return;
        }
        Drawable c2 = popMenuItem.c();
        if (c2 != null) {
            imageView.setImageDrawable(c2);
            imageView.setVisibility(0);
            return;
        }
        imageView.setVisibility(8);
    }

    static /* synthetic */ void a(ImageView imageView, int i) {
        if (i != 1001) {
            imageView.setVisibility(8);
            return;
        }
        imageView.setImageResource(R.drawable.A);
        imageView.setVisibility(0);
    }
}
