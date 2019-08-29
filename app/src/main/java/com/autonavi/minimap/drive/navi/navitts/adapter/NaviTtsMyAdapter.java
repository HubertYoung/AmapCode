package com.autonavi.minimap.drive.navi.navitts.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.alipay.android.phone.mobilecommon.multimedia.video.data.CameraParams;
import com.alipay.mobile.beehive.photo.util.DiskFormatter;
import com.amap.bundle.statistics.LogManager;
import com.autonavi.bundle.account.api.IAccountService;
import com.autonavi.minimap.R;
import com.autonavi.minimap.drive.navi.navitts.fragment.OfflineNaviTtsFragment;
import com.autonavi.minimap.drive.navi.navitts.widget.RoundCornerImageView;
import com.autonavi.minimap.offline.utils.UserReport;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

public final class NaviTtsMyAdapter extends dft {
    public List<dgl> a = new ArrayList();
    private boolean j = true;
    private volatile boolean k;
    private Context l;

    class TitleViewHolder extends defpackage.dft.a implements OnCheckedChangeListener {
        public final CheckBox aSwitch;
        public final TextView subTitle;
        public final TextView title;

        public TitleViewHolder(View view) {
            super(view);
            this.title = (TextView) view.findViewById(R.id.tv_title);
            this.subTitle = (TextView) view.findViewById(R.id.tv_head);
            this.aSwitch = (CheckBox) view.findViewById(R.id.tv_operate);
            this.aSwitch.setOnCheckedChangeListener(this);
        }

        public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
            NaviTtsMyAdapter.a(NaviTtsMyAdapter.this, z);
        }
    }

    class a extends defpackage.dft.a implements OnClickListener, OnLongClickListener {
        public final RoundCornerImageView a;
        public final Button b;
        public final ProgressBar c;
        public final TextView d;
        public final TextView e;
        public final TextView f;

        public a(View view) {
            super(view);
            this.a = (RoundCornerImageView) view.findViewById(R.id.iv_trylisten_bg);
            this.b = (Button) view.findViewById(R.id.btn_trylisten);
            this.c = (ProgressBar) view.findViewById(R.id.pb_trylisten);
            this.d = (TextView) view.findViewById(R.id.tv_operate);
            this.e = (TextView) view.findViewById(R.id.tv_name);
            this.f = (TextView) view.findViewById(R.id.tv_desc);
            this.e.getPaint().setFakeBoldText(true);
            this.a.setOnClickListener(this);
            this.b.setOnClickListener(this);
            this.d.setOnClickListener(this);
            view.setOnClickListener(this);
            view.setOnLongClickListener(this);
        }

        public final void onClick(View view) {
            if (view == this.a) {
                if (NaviTtsMyAdapter.this.e < 1001) {
                    NaviTtsMyAdapter.this.b(getPosition());
                }
            } else if (view == this.b) {
                NaviTtsMyAdapter.this.b(getPosition());
            } else if (view == this.d) {
                NaviTtsMyAdapter.this.c(getPosition());
            } else {
                NaviTtsMyAdapter.this.d(getPosition());
            }
        }

        public final boolean onLongClick(View view) {
            NaviTtsMyAdapter.this.d(getPosition());
            return true;
        }
    }

    public NaviTtsMyAdapter(OfflineNaviTtsFragment offlineNaviTtsFragment, int i) {
        super(offlineNaviTtsFragment, i);
        this.l = offlineNaviTtsFragment.getContext();
        this.j = dfo.f();
    }

    public final void a() {
        this.j = dfo.f();
        this.b.clear();
        this.c.clear();
        for (dgl dgl : this.i) {
            this.c.put(dgl, Integer.valueOf(this.b.size()));
            this.b.add(new c(3, dgl));
        }
        if (this.a != null && !this.a.isEmpty() && this.e <= 1001) {
            this.b.add(new c(12, null));
            if (this.j) {
                for (dgl next : this.a) {
                    this.c.put(next, Integer.valueOf(this.b.size()));
                    this.b.add(new c(13, next));
                }
            }
        }
        this.b.add(new c(4, null));
    }

    public final ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        switch (i) {
            case 11:
                return new TitleViewHolder(this.g.inflate(R.layout.navitts_custom_view_pager_title, viewGroup, false));
            case 12:
                return new TitleViewHolder(this.g.inflate(R.layout.navitts_custom_view_pager_title, viewGroup, false));
            case 13:
                return new a(this.g.inflate(R.layout.layout_offline_navitts_item_custom, viewGroup, false));
            default:
                return super.onCreateViewHolder(viewGroup, i);
        }
    }

    public final void onBindViewHolder(ViewHolder viewHolder, int i) {
        boolean z;
        String str;
        switch (getItemViewType(i)) {
            case 11:
                TitleViewHolder titleViewHolder = (TitleViewHolder) viewHolder;
                titleViewHolder.aSwitch.setVisibility(8);
                titleViewHolder.subTitle.setVisibility(8);
                titleViewHolder.title.setText(this.l.getString(R.string.navitts_title));
                return;
            case 12:
                TitleViewHolder titleViewHolder2 = (TitleViewHolder) viewHolder;
                titleViewHolder2.aSwitch.setVisibility(0);
                titleViewHolder2.subTitle.setVisibility(0);
                this.k = true;
                titleViewHolder2.aSwitch.setChecked(this.j);
                this.k = false;
                titleViewHolder2.title.setText(this.l.getString(R.string.custom_navitts_title));
                titleViewHolder2.subTitle.setText(this.l.getString(R.string.custom_navitts_subtitle));
                return;
            case 13:
                a aVar = (a) viewHolder;
                dgl a2 = a(i);
                if (a2 != null) {
                    String str2 = a2.a.k;
                    IAccountService iAccountService = (IAccountService) defpackage.esb.a.a.a(IAccountService.class);
                    if (iAccountService == null) {
                        z = false;
                    } else {
                        z = iAccountService.a();
                    }
                    if (!z || TextUtils.isEmpty(str2)) {
                        aVar.a.setImageResource(R.drawable.default0);
                    } else {
                        ko.a(aVar.a, str2, null, R.drawable.default0);
                    }
                    aVar.e.setText(a2.a.l);
                    int f = (int) a2.f();
                    TextView textView = aVar.f;
                    if (f >= 1024) {
                        StringBuilder sb = new StringBuilder();
                        sb.append(f / 1024);
                        sb.append(DiskFormatter.KB);
                        str = sb.toString();
                    } else {
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append(f);
                        sb2.append(DiskFormatter.B);
                        str = sb2.toString();
                    }
                    textView.setText(str);
                    if (this.e < 1001) {
                        aVar.b.setVisibility(0);
                        if (this.h.a(a2)) {
                            aVar.b.setBackgroundResource(R.drawable.pause);
                        } else {
                            aVar.b.setBackgroundResource(R.drawable.play);
                        }
                    } else {
                        aVar.b.setVisibility(8);
                    }
                    aVar.c.setVisibility(8);
                    String g = dfo.g();
                    if (g == null || !g.equals(a2.a.c)) {
                        aVar.d.setVisibility(8);
                        aVar.itemView.setBackgroundColor(this.l.getResources().getColor(R.color.white));
                    } else {
                        aVar.d.setEnabled(true);
                        aVar.d.setBackgroundResource(R.drawable.offline_sdcard_selected);
                        aVar.d.setVisibility(0);
                        aVar.itemView.setBackgroundColor(this.l.getResources().getColor(R.color.offline_navitts_using_background));
                        return;
                    }
                }
                return;
            default:
                super.onBindViewHolder(viewHolder, i);
                return;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:14:0x0039  */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x0045  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void a(defpackage.dgl r3) {
        /*
            r2 = this;
            boolean r0 = r3.c()
            if (r0 == 0) goto L_0x004b
            java.lang.String r0 = r3.a()
            java.io.File r1 = new java.io.File
            r1.<init>(r0)
            boolean r0 = r1.exists()
            if (r0 == 0) goto L_0x0031
            boolean r0 = r1.isDirectory()
            if (r0 == 0) goto L_0x0031
            dfp$1 r0 = new dfp$1
            r0.<init>()
            java.io.File[] r0 = r1.listFiles(r0)
            if (r0 == 0) goto L_0x0031
            int r1 = r0.length
            if (r1 <= 0) goto L_0x0031
            r1 = 0
            r0 = r0[r1]
            java.lang.String r0 = r0.getAbsolutePath()
            goto L_0x0033
        L_0x0031:
            java.lang.String r0 = ""
        L_0x0033:
            boolean r1 = android.text.TextUtils.isEmpty(r0)
            if (r1 == 0) goto L_0x0045
            android.content.Context r3 = r2.l
            int r0 = com.autonavi.minimap.R.string.custom_navitts_error
            java.lang.String r3 = r3.getString(r0)
            com.amap.bundle.utils.ui.ToastHelper.showToast(r3)
            return
        L_0x0045:
            dfw r1 = r2.h
            r1.b(r3, r0)
            return
        L_0x004b:
            super.a(r3)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.drive.navi.navitts.adapter.NaviTtsMyAdapter.a(dgl):void");
    }

    static /* synthetic */ void a(NaviTtsMyAdapter naviTtsMyAdapter, boolean z) {
        if (!naviTtsMyAdapter.k) {
            naviTtsMyAdapter.j = z;
            if (!z && b.a.d != null) {
                b.a.d.a();
            }
            dfo.a(z);
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("type", z ? CameraParams.FLASH_MODE_ON : CameraParams.FLASH_MODE_OFF);
                LogManager.actionLogV2(UserReport.PAGE_NAVITTS_MYNAVITTS, "B004", jSONObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            naviTtsMyAdapter.a();
            naviTtsMyAdapter.notifyDataSetChanged();
        }
    }
}
