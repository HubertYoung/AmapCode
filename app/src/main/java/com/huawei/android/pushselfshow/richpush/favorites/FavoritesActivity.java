package com.huawei.android.pushselfshow.richpush.favorites;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources.NotFoundException;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Message;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.autonavi.minimap.search.templete.type.PoiLayoutTemplate;
import com.huawei.android.pushselfshow.richpush.html.HtmlViewer;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class FavoritesActivity implements com.huawei.android.pushselfshow.utils.c.a {
    com.huawei.android.pushselfshow.utils.c a = new com.huawei.android.pushselfshow.utils.c(this);
    /* access modifiers changed from: private */
    public Activity b;
    /* access modifiers changed from: private */
    public ImageView c;
    /* access modifiers changed from: private */
    public TextView d;
    /* access modifiers changed from: private */
    public TextView e;
    /* access modifiers changed from: private */
    public ListView f;
    private LinearLayout g;
    /* access modifiers changed from: private */
    public a h;
    /* access modifiers changed from: private */
    public MenuItem i;
    private MenuItem j;
    /* access modifiers changed from: private */
    public boolean k = false;
    private byte[] l = null;
    private byte[] m = null;
    private AlertDialog n = null;

    class a implements OnClickListener {
        private Context b;

        private a(Context context) {
            this.b = context;
        }

        /* synthetic */ a(FavoritesActivity favoritesActivity, Context context, b bVar) {
            this(context);
        }

        public void onClick(View view) {
            if (FavoritesActivity.this.k) {
                FavoritesActivity.this.e();
                return;
            }
            ActionBar actionBar = FavoritesActivity.this.b.getActionBar();
            if (actionBar != null) {
                actionBar.setDisplayShowHomeEnabled(true);
                actionBar.setDisplayHomeAsUpEnabled(true);
                actionBar.setDisplayShowTitleEnabled(true);
                actionBar.setDisplayShowCustomEnabled(false);
                actionBar.setTitle(this.b.getString(com.huawei.android.pushselfshow.utils.d.a(this.b, "hwpush_msg_collect")));
            }
            FavoritesActivity.this.c.setVisibility(4);
            FavoritesActivity.this.e.setVisibility(8);
            FavoritesActivity.this.e.setText("");
            FavoritesActivity.this.d.setText(this.b.getString(com.huawei.android.pushselfshow.utils.d.a(this.b, "hwpush_msg_collect")));
            FavoritesActivity.this.a(false);
            FavoritesActivity.this.h.a(true);
            FavoritesActivity.this.f.setOnItemClickListener(new d(FavoritesActivity.this, null));
            FavoritesActivity.this.f.setLongClickable(true);
        }
    }

    class b implements OnItemClickListener {
        private Context b;

        private b(Context context) {
            this.b = context;
        }

        /* synthetic */ b(FavoritesActivity favoritesActivity, Context context, b bVar) {
            this(context);
        }

        public void onItemClick(AdapterView adapterView, View view, int i, long j) {
            CheckBox checkBox = (CheckBox) view.findViewById(com.huawei.android.pushselfshow.utils.d.e(this.b, "hwpush_delCheck"));
            e a2 = FavoritesActivity.this.h.getItem(i);
            if (checkBox.isChecked()) {
                checkBox.setChecked(false);
                a2.a(false);
            } else {
                checkBox.setChecked(true);
                a2.a(true);
            }
            FavoritesActivity.this.h.a(i, a2);
            List<e> a3 = FavoritesActivity.this.h.a();
            int i2 = 0;
            for (e a4 : a3) {
                if (a4.a()) {
                    i2++;
                }
            }
            if (i2 > 0) {
                FavoritesActivity.this.e.setVisibility(0);
                FavoritesActivity.this.e.setText(String.valueOf(i2));
                FavoritesActivity.this.i.setEnabled(true);
                if (i2 == a3.size()) {
                    FavoritesActivity.this.a(this.b, true);
                } else {
                    FavoritesActivity.this.a(this.b, false);
                }
            } else {
                FavoritesActivity.this.e.setVisibility(8);
                FavoritesActivity.this.e.setText("");
                FavoritesActivity.this.i.setEnabled(false);
                FavoritesActivity.this.a(this.b, false);
            }
        }
    }

    class c implements OnItemLongClickListener {
        private c() {
        }

        /* synthetic */ c(FavoritesActivity favoritesActivity, b bVar) {
            this();
        }

        public boolean onItemLongClick(AdapterView adapterView, View view, int i, long j) {
            FavoritesActivity.this.d();
            FavoritesActivity.this.i.setEnabled(true);
            HashSet hashSet = new HashSet();
            hashSet.add(Integer.valueOf(i));
            FavoritesActivity.this.h.a(false, (Set) hashSet);
            FavoritesActivity.this.e.setVisibility(0);
            FavoritesActivity.this.e.setText("1");
            return true;
        }
    }

    class d implements OnItemClickListener {
        private d() {
        }

        /* synthetic */ d(FavoritesActivity favoritesActivity, b bVar) {
            this();
        }

        public void onItemClick(AdapterView adapterView, View view, int i, long j) {
            e a2 = FavoritesActivity.this.h.getItem(i);
            Intent intent = new Intent("com.huawei.android.push.intent.RICHPUSH");
            intent.putExtra("type", a2.b().E);
            intent.putExtra("selfshow_info", a2.b().c());
            intent.putExtra("selfshow_token", a2.b().d());
            intent.putExtra("selfshow_from_list", true);
            intent.setFlags(268468240);
            intent.setPackage(FavoritesActivity.this.b.getPackageName());
            FavoritesActivity.this.b.finish();
            FavoritesActivity.this.b.startActivity(intent);
        }
    }

    private View a() {
        View inflate = this.b.getLayoutInflater().inflate(com.huawei.android.pushselfshow.utils.d.c(this.b, "hwpush_collection_listview"), null);
        this.f = (ListView) inflate.findViewById(com.huawei.android.pushselfshow.utils.d.e(this.b, "hwpush_collection_list"));
        this.h = new a(this.b);
        this.f.setAdapter(this.h);
        this.f.setLongClickable(true);
        this.f.setOnItemLongClickListener(new c(this, null));
        this.f.setOnItemClickListener(new d(this, null));
        return inflate;
    }

    /* access modifiers changed from: private */
    public void a(Context context, boolean z) {
        String str;
        String str2;
        if (z) {
            this.j.setTitle(com.huawei.android.pushselfshow.utils.d.a(context, "hwpush_unselectall"));
            Drawable drawable = context.getResources().getDrawable(com.huawei.android.pushselfshow.utils.d.g(context, "hwpush_ic_toolbar_multiple1"));
            try {
                int identifier = context.getResources().getIdentifier("colorful_emui", "color", "androidhwext");
                if (identifier != 0) {
                    int color = context.getResources().getColor(identifier);
                    if (color != 0) {
                        drawable.setTint(color);
                    }
                }
            } catch (NotFoundException e2) {
                str2 = "PushSelfShowLog";
                str = e2.toString();
                com.huawei.android.pushagent.a.a.c.d(str2, str);
                this.j.setIcon(drawable);
                return;
            } catch (Exception e3) {
                str2 = "PushSelfShowLog";
                str = e3.toString();
                com.huawei.android.pushagent.a.a.c.d(str2, str);
                this.j.setIcon(drawable);
                return;
            }
            this.j.setIcon(drawable);
            return;
        }
        this.j.setIcon(context.getResources().getDrawable(com.huawei.android.pushselfshow.utils.d.g(context, "hwpush_ic_toolbar_multiple")));
        this.j.setTitle(com.huawei.android.pushselfshow.utils.d.a(context, "hwpush_selectall"));
    }

    private void a(View view) {
        int i2;
        this.c = (ImageView) view.findViewById(com.huawei.android.pushselfshow.utils.d.e(this.b, "hwpush_bt_delete"));
        this.d = (TextView) view.findViewById(com.huawei.android.pushselfshow.utils.d.e(this.b, "hwpush_txt_delitem"));
        this.e = (TextView) view.findViewById(com.huawei.android.pushselfshow.utils.d.e(this.b, "hwpush_txt_delnum"));
        com.huawei.android.pushselfshow.utils.a.a((Context) this.b, this.d);
        com.huawei.android.pushselfshow.utils.a.a((Context) this.b, this.e);
        if (com.huawei.android.pushselfshow.utils.a.d()) {
            int j2 = com.huawei.android.pushselfshow.utils.a.j(this.b);
            if (-1 != j2) {
                if (j2 == 0) {
                    i2 = this.b.getResources().getColor(com.huawei.android.pushselfshow.utils.d.f(this.b, "hwpush_black"));
                    this.c.setImageDrawable(this.b.getResources().getDrawable(com.huawei.android.pushselfshow.utils.d.g(this.b, "hwpush_ic_cancel_light")));
                    this.e.setBackground(this.b.getResources().getDrawable(com.huawei.android.pushselfshow.utils.d.g(this.b, "hwpush_pic_ab_number_light")));
                } else {
                    i2 = this.b.getResources().getColor(com.huawei.android.pushselfshow.utils.d.f(this.b, "hwpush_white"));
                    this.c.setImageDrawable(this.b.getResources().getDrawable(com.huawei.android.pushselfshow.utils.d.g(this.b, "hwpush_ic_cancel")));
                    this.e.setBackground(this.b.getResources().getDrawable(com.huawei.android.pushselfshow.utils.d.g(this.b, "hwpush_pic_ab_number")));
                    this.e.setTextColor(i2);
                }
                this.d.setTextColor(i2);
            }
        }
        this.c.setOnClickListener(new a(this, this.b, null));
    }

    /* access modifiers changed from: private */
    public void a(boolean z) {
        this.i.setVisible(z);
        this.j.setVisible(z);
    }

    private void b() {
        if (this.h != null && this.f != null && this.g != null) {
            StringBuilder sb = new StringBuilder("count:");
            sb.append(this.h.getCount());
            com.huawei.android.pushagent.a.a.c.a("PushSelfShowLog", sb.toString());
            if (this.h.getCount() == 0) {
                this.f.setVisibility(8);
                this.g.setVisibility(0);
                return;
            }
            this.f.setVisibility(0);
            this.g.setVisibility(8);
        }
    }

    private int c() {
        int i2 = 0;
        if (this.h == null) {
            return 0;
        }
        for (e eVar : this.h.a()) {
            if (eVar != null && eVar.a()) {
                i2++;
            }
        }
        com.huawei.android.pushagent.a.a.c.a("PushSelfShowLog", "selectItemsNum:".concat(String.valueOf(i2)));
        return i2;
    }

    /* access modifiers changed from: private */
    public void d() {
        ActionBar actionBar = this.b.getActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayShowCustomEnabled(true);
            actionBar.setDisplayShowHomeEnabled(false);
            View inflate = this.b.getLayoutInflater().inflate(com.huawei.android.pushselfshow.utils.d.c(this.b, "hwpush_custom_titlebar"), null);
            a(inflate);
            actionBar.setCustomView(inflate);
        }
        a(true);
        this.c.setVisibility(0);
        this.d.setText(com.huawei.android.pushselfshow.utils.d.a(this.b, "hwpush_deltitle"));
        this.f.setOnItemClickListener(new b(this, this.b, null));
        this.h.a(false);
        this.f.setLongClickable(false);
        if (1 == this.h.a().size()) {
            a((Context) this.b, true);
        } else {
            a((Context) this.b, false);
        }
    }

    /* access modifiers changed from: private */
    public void e() {
        Intent intent = new Intent("com.huawei.android.push.intent.RICHPUSH");
        intent.putExtra("type", PoiLayoutTemplate.HTML);
        intent.putExtra("selfshow_info", this.l);
        intent.putExtra("selfshow_token", this.m);
        intent.setFlags(268468240);
        intent.setPackage(this.b.getPackageName());
        this.b.finish();
        this.b.startActivity(intent);
    }

    public void handleMessage(Message message) {
        try {
            switch (message.what) {
                case 1000:
                    com.huawei.android.pushagent.a.a.c.a("PushSelfShowLog", "mHandler MSG_LOAD_DONE");
                    this.f.setAdapter(this.h);
                    b();
                    if (this.k) {
                        d();
                    }
                    return;
                case 1001:
                    com.huawei.android.pushagent.a.a.c.a("PushSelfShowLog", "mHandler MSG_DELETE_DONE");
                    if (this.k) {
                        e();
                        return;
                    }
                    this.f.setAdapter(this.h);
                    this.c.performClick();
                    b();
                    return;
                default:
                    return;
            }
        } catch (Exception e2) {
            StringBuilder sb = new StringBuilder("handleMessage error:");
            sb.append(message.what);
            sb.append(",");
            sb.append(e2.toString());
            com.huawei.android.pushagent.a.a.c.c("PushSelfShowLog", sb.toString(), e2);
        }
    }

    public void onActivityResult(int i2, int i3, Intent intent) {
        com.huawei.android.pushagent.a.a.c.a("PushSelfShowLog", "FavoritesActivity onActivityResult");
    }

    public void onCreate(Intent intent) {
        com.huawei.android.pushagent.a.a.c.a("PushSelfShowLog", "FavoritesActivity onCreate");
        try {
            ActionBar actionBar = this.b.getActionBar();
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(true);
                actionBar.setDisplayShowTitleEnabled(true);
                actionBar.setTitle(this.b.getString(com.huawei.android.pushselfshow.utils.d.a(this.b, "hwpush_msg_favorites")));
            }
            this.k = intent.getBooleanExtra("selfshowMsgOutOfBound", false);
            this.l = intent.getByteArrayExtra("selfshow_info");
            this.m = intent.getByteArrayExtra("selfshow_token");
            RelativeLayout relativeLayout = new RelativeLayout(this.b);
            View a2 = a();
            this.g = (LinearLayout) a2.findViewById(com.huawei.android.pushselfshow.utils.d.e(this.b, "hwpush_no_collection_view"));
            StringBuilder sb = new StringBuilder("mNoCollectionLayout:");
            sb.append(this.g);
            com.huawei.android.pushagent.a.a.c.a("PushSelfShowLog", sb.toString());
            relativeLayout.addView(a2);
            new Thread(new b(this)).start();
            this.b.setContentView(relativeLayout);
            if (this.k && this.i != null) {
                this.i.setEnabled(false);
            }
        } catch (RuntimeException e2) {
            StringBuilder sb2 = new StringBuilder("call");
            sb2.append(HtmlViewer.class.getName());
            sb2.append(" onCreate(Intent intent) err: ");
            sb2.append(e2.toString());
            com.huawei.android.pushagent.a.a.c.c("PushSelfShowLog", sb2.toString(), e2);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        this.b.getMenuInflater().inflate(com.huawei.android.pushselfshow.utils.d.d(this.b, "hwpush_collection_menu"), menu);
        return true;
    }

    public void onDestroy() {
        com.huawei.android.pushagent.a.a.c.a("PushSelfShowLog", "FavoritesActivity onDestroy");
        if (this.n != null && this.n.isShowing()) {
            this.n.dismiss();
        }
    }

    public boolean onKeyDown(int i2, KeyEvent keyEvent) {
        com.huawei.android.pushagent.a.a.c.a("PushSelfShowLog", "FavoritesActivity onKeyDown");
        if (i2 == 4 && keyEvent.getAction() == 0) {
            boolean z = false;
            if (this.c != null && this.c.getVisibility() == 0) {
                z = true;
            }
            if (this.k) {
                e();
                return true;
            } else if (z) {
                this.c.performClick();
                return true;
            } else {
                this.b.finish();
            }
        }
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        boolean z;
        CharSequence charSequence;
        com.huawei.android.pushagent.a.a.c.b("PushSelfShowLog", "FavoritesActivity onOptionsItemSelected:".concat(String.valueOf(menuItem)));
        if (menuItem == null) {
            com.huawei.android.pushagent.a.a.c.d("PushSelfShowLog", "onOptionsItemSelected, item is null");
            return false;
        }
        int itemId = menuItem.getItemId();
        if (itemId == 16908332) {
            onKeyDown(4, new KeyEvent(0, 4));
            return true;
        } else if (itemId == com.huawei.android.pushselfshow.utils.d.e(this.b, "hwpush_menu_delete")) {
            try {
                charSequence = this.b.getResources().getQuantityString(com.huawei.android.pushselfshow.utils.d.b(this.b, "hwpush_delete_tip"), c());
            } catch (NotFoundException e2) {
                com.huawei.android.pushagent.a.a.c.c("PushSelfShowLog", e2.toString(), e2);
                charSequence = "";
            }
            this.n = new Builder(this.b, com.huawei.android.pushselfshow.utils.a.h(this.b)).setTitle(charSequence).setPositiveButton(com.huawei.android.pushselfshow.utils.d.a(this.b, "hwpush_delete"), new c(this)).setNegativeButton(com.huawei.android.pushselfshow.utils.d.a(this.b, "hwpush_cancel"), null).create();
            this.n.show();
            this.n.getButton(-1).setTextColor(Color.parseColor("#ffd43e25"));
            return true;
        } else {
            if (itemId == com.huawei.android.pushselfshow.utils.d.e(this.b, "hwpush_menu_selectall")) {
                Iterator it = this.h.a().iterator();
                while (true) {
                    if (it.hasNext()) {
                        if (!((e) it.next()).a()) {
                            z = true;
                            break;
                        }
                    } else {
                        z = false;
                        break;
                    }
                }
                this.h.a(z, (Set) null);
                if (z) {
                    this.e.setVisibility(0);
                    this.e.setText(String.valueOf(this.h.getCount()));
                    this.i.setEnabled(true);
                    a((Context) this.b, true);
                    return true;
                }
                this.e.setVisibility(8);
                this.e.setText("");
                this.i.setEnabled(false);
                a((Context) this.b, false);
            }
            return true;
        }
    }

    public void onPause() {
        com.huawei.android.pushagent.a.a.c.a("PushSelfShowLog", "FavoritesActivity onPause");
    }

    public boolean onPrepareOptionsMenu(Menu menu) {
        com.huawei.android.pushagent.a.a.c.b("PushSelfShowLog", "FavoritesActivity onPrepareOptionsMenu:".concat(String.valueOf(menu)));
        this.i = menu.findItem(com.huawei.android.pushselfshow.utils.d.e(this.b, "hwpush_menu_delete"));
        this.j = menu.findItem(com.huawei.android.pushselfshow.utils.d.e(this.b, "hwpush_menu_selectall"));
        a(false);
        return true;
    }

    public void onRestart() {
        com.huawei.android.pushagent.a.a.c.a("PushSelfShowLog", "FavoritesActivity onRestart");
    }

    public void onResume() {
        com.huawei.android.pushagent.a.a.c.a("PushSelfShowLog", "FavoritesActivity onResume");
    }

    public void onStart() {
        com.huawei.android.pushagent.a.a.c.a("PushSelfShowLog", "FavoritesActivity onStart");
    }

    public void onStop() {
        com.huawei.android.pushagent.a.a.c.a("PushSelfShowLog", "FavoritesActivity onStop");
    }

    public void setActivity(Activity activity) {
        this.b = activity;
    }
}
