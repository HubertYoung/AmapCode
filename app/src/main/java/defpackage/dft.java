package defpackage;

import android.content.Context;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.amap.bundle.blutils.FileUtil;
import com.amap.bundle.blutils.PathManager;
import com.amap.bundle.blutils.PathManager.DirType;
import com.amap.bundle.blutils.SdCardInfo;
import com.amap.bundle.statistics.LogManager;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.bundle.banner.data.BannerItem;
import com.autonavi.common.PageBundle;
import com.autonavi.minimap.R;
import com.autonavi.minimap.drive.navi.navitts.NVUtil;
import com.autonavi.minimap.drive.navi.navitts.fragment.OfflineNaviTtsFragment;
import com.autonavi.minimap.drive.navi.navitts.storage.StorageFragment;
import com.autonavi.minimap.drive.navi.navitts.widget.BannerView;
import com.autonavi.minimap.drive.navi.navitts.widget.DownloadBackgroundLayout;
import com.autonavi.minimap.drive.navi.navitts.widget.RoundCornerImageView;
import com.autonavi.minimap.drive.navi.navitts_dependencies.DriveOfflineSDK;
import com.autonavi.minimap.search.templete.type.PoiLayoutTemplate;
import com.iflytek.tts.TtsService.Tts;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

/* renamed from: dft reason: default package */
/* compiled from: NaviTtsSquareAdapter */
public class dft extends Adapter implements dfu, defpackage.dfw.c {
    private LinkedList<BannerItem> a;
    protected final List<c> b;
    protected final Map<dgl, Integer> c;
    protected OfflineNaviTtsFragment d;
    public int e;
    protected Context f;
    protected LayoutInflater g;
    protected dfw h;
    protected List<dgl> i;
    /* access modifiers changed from: private */
    public volatile boolean j = false;
    private final Object k = new Object();
    private defpackage.dfu.a l;

    /* renamed from: dft$a */
    /* compiled from: NaviTtsSquareAdapter */
    public static class a extends ViewHolder {
        public a(View view) {
            super(view);
        }
    }

    /* renamed from: dft$b */
    /* compiled from: NaviTtsSquareAdapter */
    class b extends a implements OnClickListener {
        public final TextView a;
        public final ProgressBar b;
        public final TextView c;
        public final TextView d;

        public b(View view) {
            super(view);
            this.a = (TextView) view.findViewById(R.id.sd_tag);
            this.b = (ProgressBar) view.findViewById(R.id.sd_progressBar);
            this.c = (TextView) view.findViewById(R.id.sd_size_desc);
            this.d = (TextView) view.findViewById(R.id.sd_size_total);
            view.findViewById(R.id.siwtch_card).setOnClickListener(this);
        }

        public final void onClick(View view) {
            dft.b(dft.this);
        }
    }

    /* renamed from: dft$c */
    /* compiled from: NaviTtsSquareAdapter */
    public static class c {
        public final int a;
        public final Object b;

        public c(int i, Object obj) {
            this.a = i;
            this.b = obj;
        }
    }

    /* renamed from: dft$d */
    /* compiled from: NaviTtsSquareAdapter */
    class d {
        public final String a;
        public final int b;

        d(String str, int i) {
            this.a = str;
            this.b = i;
        }
    }

    /* renamed from: dft$e */
    /* compiled from: NaviTtsSquareAdapter */
    class e extends a {
        public final TextView a;

        public e(View view) {
            super(view);
            this.a = (TextView) view.findViewById(R.id.voice_header_desc);
            if (this.a != null) {
                this.a.getPaint().setFakeBoldText(true);
            }
        }
    }

    /* renamed from: dft$f */
    /* compiled from: NaviTtsSquareAdapter */
    class f extends a implements OnClickListener, OnLongClickListener {
        public final RoundCornerImageView a;
        public final Button b;
        public final ProgressBar c;
        public final TextView d;
        public final TextView e;
        public final TextView f;
        public final TextView g;
        public final View h;
        public final TextView i;
        public final View j;
        public final DownloadBackgroundLayout k;

        public f(View view) {
            super(view);
            this.a = (RoundCornerImageView) view.findViewById(R.id.iv_trylisten_bg);
            this.b = (Button) view.findViewById(R.id.btn_trylisten);
            this.c = (ProgressBar) view.findViewById(R.id.pb_trylisten);
            this.d = (TextView) view.findViewById(R.id.tv_operate);
            this.e = (TextView) view.findViewById(R.id.tv_name);
            this.f = (TextView) view.findViewById(R.id.tv_desc);
            this.g = (TextView) view.findViewById(R.id.tv_size);
            this.e.getPaint().setFakeBoldText(true);
            this.d.getPaint().setFakeBoldText(true);
            this.h = view.findViewById(R.id.download_statu_layout);
            this.i = (TextView) view.findViewById(R.id.tv_progress_size);
            this.j = view.findViewById(R.id.rl_progress);
            this.k = (DownloadBackgroundLayout) view.findViewById(R.id.progress_bg_layout);
            this.a.setOnClickListener(this);
            this.b.setOnClickListener(this);
            this.h.setOnClickListener(this);
            view.setOnClickListener(this);
            view.setOnLongClickListener(this);
        }

        public final void onClick(View view) {
            if (view == this.a) {
                if (dft.this.e < 1001) {
                    dft.this.b(getPosition());
                }
            } else if (view == this.b) {
                dft.this.b(getPosition());
            } else if (view == this.h) {
                dft.this.c(getPosition());
            } else {
                dft.this.d(getPosition());
            }
        }

        public final boolean onLongClick(View view) {
            dft.this.d(getPosition());
            return true;
        }
    }

    public dft(OfflineNaviTtsFragment offlineNaviTtsFragment, int i2) {
        this.d = offlineNaviTtsFragment;
        this.e = i2;
        this.f = offlineNaviTtsFragment.getActivity();
        this.g = LayoutInflater.from(this.f);
        this.b = new ArrayList();
        this.c = new HashMap();
        this.h = defpackage.dfw.b.a;
        this.h.e.add(this);
    }

    public final void a(CopyOnWriteArrayList<dgl> copyOnWriteArrayList) {
        if (copyOnWriteArrayList != null) {
            this.i = (List) copyOnWriteArrayList.clone();
        }
        a();
    }

    public final void a(LinkedList<BannerItem> linkedList) {
        if (linkedList != null) {
            this.a = (LinkedList) linkedList.clone();
        }
        a();
    }

    /* access modifiers changed from: protected */
    public void a() {
        this.b.clear();
        this.c.clear();
        if (this.l != null) {
            this.l.a();
        }
        if (this.e < 1001 && this.f.getResources().getConfiguration().orientation == 1 && this.a != null && this.a.size() != 0) {
            this.b.add(new c(1, this.a));
        }
        boolean z = false;
        boolean z2 = false;
        for (dgl next : this.i) {
            if (next.d() && !next.e()) {
                if (!z2) {
                    this.b.add(new c(5, new d("精品语音", 1)));
                    z2 = true;
                }
                this.c.put(next, Integer.valueOf(this.b.size()));
                this.b.add(new c(3, next));
            }
        }
        for (dgl next2 : this.i) {
            if (!next2.d() && !next2.e()) {
                if (!z) {
                    this.b.add(new c(5, new d("标准语音", 2)));
                    z = true;
                }
                this.c.put(next2, Integer.valueOf(this.b.size()));
                this.b.add(new c(3, next2));
            }
        }
        this.b.add(new c(4, null));
    }

    /* access modifiers changed from: protected */
    public final dgl a(int i2) {
        dgl dgl;
        if (this.b == null || this.b.size() <= i2) {
            return null;
        }
        try {
            dgl = (dgl) this.b.get(i2).b;
        } catch (Exception unused) {
            dgl = null;
        }
        return dgl;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0020, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final synchronized void b(final defpackage.dgl r3) {
        /*
            r2 = this;
            monitor-enter(r2)
            java.util.Map<dgl, java.lang.Integer> r0 = r2.c     // Catch:{ all -> 0x0027 }
            if (r0 == 0) goto L_0x0025
            java.util.Map<dgl, java.lang.Integer> r0 = r2.c     // Catch:{ all -> 0x0027 }
            boolean r0 = r0.containsKey(r3)     // Catch:{ all -> 0x0027 }
            if (r0 == 0) goto L_0x0025
            com.autonavi.minimap.drive.navi.navitts.fragment.OfflineNaviTtsFragment r0 = r2.d     // Catch:{ IllegalStateException -> 0x0021 }
            if (r0 == 0) goto L_0x001f
            com.autonavi.minimap.drive.navi.navitts.fragment.OfflineNaviTtsFragment r0 = r2.d     // Catch:{ IllegalStateException -> 0x0021 }
            android.app.Activity r0 = r0.getActivity()     // Catch:{ IllegalStateException -> 0x0021 }
            dft$1 r1 = new dft$1     // Catch:{ IllegalStateException -> 0x0021 }
            r1.<init>(r3)     // Catch:{ IllegalStateException -> 0x0021 }
            r0.runOnUiThread(r1)     // Catch:{ IllegalStateException -> 0x0021 }
        L_0x001f:
            monitor-exit(r2)
            return
        L_0x0021:
            r3 = move-exception
            r3.printStackTrace()     // Catch:{ all -> 0x0027 }
        L_0x0025:
            monitor-exit(r2)
            return
        L_0x0027:
            r3 = move-exception
            monitor-exit(r2)
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.dft.b(dgl):void");
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i2) {
        if (i2 == 1) {
            return new a(new BannerView(this.f, this.a));
        }
        switch (i2) {
            case 3:
                f fVar = new f(this.g.inflate(R.layout.layout_offline_navitts_item, viewGroup, false));
                fVar.setIsRecyclable(false);
                return fVar;
            case 4:
                return new b(this.g.inflate(R.layout.f8xx_sd_size_reconstruct, viewGroup, false));
            case 5:
                return new e(this.g.inflate(R.layout.layout_offline_navitts_item_header, viewGroup, false));
            default:
                return null;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:52:0x010d  */
    /* JADX WARNING: Removed duplicated region for block: B:53:0x012c  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onBindViewHolder(android.support.v7.widget.RecyclerView.ViewHolder r19, int r20) {
        /*
            r18 = this;
            r0 = r18
            r1 = r19
            r2 = r20
            r3 = -1
            if (r1 == 0) goto L_0x000e
            int r4 = r19.getItemViewType()
            goto L_0x000f
        L_0x000e:
            r4 = -1
        L_0x000f:
            if (r4 != r3) goto L_0x0015
            int r4 = r0.getItemViewType(r2)
        L_0x0015:
            r3 = 1
            if (r4 == r3) goto L_0x0356
            r6 = 2
            r7 = 0
            switch(r4) {
                case 3: goto L_0x0157;
                case 4: goto L_0x0036;
                case 5: goto L_0x001f;
                default: goto L_0x001d;
            }
        L_0x001d:
            goto L_0x0355
        L_0x001f:
            if (r1 == 0) goto L_0x0355
            boolean r3 = r1 instanceof defpackage.dft.e
            if (r3 == 0) goto L_0x0355
            dft$e r1 = (defpackage.dft.e) r1
            dft$d r2 = r0.g(r2)
            if (r2 == 0) goto L_0x0355
            android.widget.TextView r1 = r1.a
            java.lang.String r2 = r2.a
            r1.setText(r2)
            goto L_0x0355
        L_0x0036:
            if (r1 == 0) goto L_0x0355
            boolean r2 = r1 instanceof defpackage.dft.b
            if (r2 == 0) goto L_0x0355
            dft$b r1 = (defpackage.dft.b) r1
            com.amap.bundle.blutils.PathManager r2 = com.amap.bundle.blutils.PathManager.a()
            com.amap.bundle.blutils.PathManager$DirType r3 = com.amap.bundle.blutils.PathManager.DirType.DRIVE_VOICE
            java.lang.String r2 = r2.b(r3)
            android.content.Context r3 = r0.f
            android.content.Context r3 = r3.getApplicationContext()
            java.util.ArrayList r3 = com.amap.bundle.blutils.FileUtil.getSDCardInfoList(r3, r7)
            if (r3 == 0) goto L_0x0091
            boolean r4 = r3.isEmpty()
            if (r4 != 0) goto L_0x0091
            java.util.Iterator r3 = r3.iterator()
        L_0x005e:
            boolean r4 = r3.hasNext()
            if (r4 == 0) goto L_0x0091
            java.lang.Object r4 = r3.next()
            com.amap.bundle.blutils.SdCardInfo r4 = (com.amap.bundle.blutils.SdCardInfo) r4
            java.lang.String r8 = r4.b
            if (r8 == 0) goto L_0x005e
            java.lang.String r8 = r4.b
            boolean r8 = r8.equals(r2)
            if (r8 == 0) goto L_0x005e
            com.amap.bundle.blutils.SdCardInfo$SDCardType r8 = r4.a
            com.amap.bundle.blutils.SdCardInfo$SDCardType r9 = com.amap.bundle.blutils.SdCardInfo.SDCardType.INNERCARD
            if (r8 != r9) goto L_0x0084
            android.widget.TextView r3 = r1.a
            java.lang.String r4 = "内置卡"
            r3.setText(r4)
            goto L_0x0091
        L_0x0084:
            com.amap.bundle.blutils.SdCardInfo$SDCardType r4 = r4.a
            com.amap.bundle.blutils.SdCardInfo$SDCardType r8 = com.amap.bundle.blutils.SdCardInfo.SDCardType.EXTERNALCARD
            if (r4 != r8) goto L_0x005e
            android.widget.TextView r3 = r1.a
            java.lang.String r4 = "外置卡"
            r3.setText(r4)
        L_0x0091:
            long[] r2 = defpackage.ahd.e(r2)
            r3 = r2[r7]
            r7 = 1024(0x400, double:5.06E-321)
            long r3 = r3 / r7
            long r3 = r3 / r7
            r9 = r2[r6]
            long r9 = r9 / r7
            long r9 = r9 / r7
            r11 = 0
            int r2 = (r11 > r9 ? 1 : (r11 == r9 ? 0 : -1))
            if (r2 == 0) goto L_0x0156
            java.lang.String r2 = ""
            java.lang.String r6 = ""
            java.text.DecimalFormat r13 = new java.text.DecimalFormat
            java.lang.String r14 = "#.00"
            r13.<init>(r14)
            int r14 = (r9 > r7 ? 1 : (r9 == r7 ? 0 : -1))
            r15 = 1149239296(0x44800000, float:1024.0)
            if (r14 >= 0) goto L_0x00cc
            int r16 = (r9 > r11 ? 1 : (r9 == r11 ? 0 : -1))
            if (r16 <= 0) goto L_0x00cc
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            r2.append(r9)
            java.lang.String r14 = "MB"
            r2.append(r14)
            java.lang.String r2 = r2.toString()
            goto L_0x00ef
        L_0x00cc:
            if (r14 <= 0) goto L_0x00ef
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            r17 = r6
            long r5 = r9 / r7
            r2.append(r5)
            float r5 = (float) r9
            float r5 = r5 % r15
            float r5 = r5 / r15
            double r5 = (double) r5
            java.lang.String r5 = r13.format(r5)
            r2.append(r5)
            java.lang.String r5 = "GB"
            r2.append(r5)
            java.lang.String r2 = r2.toString()
            goto L_0x00f1
        L_0x00ef:
            r17 = r6
        L_0x00f1:
            int r5 = (r3 > r7 ? 1 : (r3 == r7 ? 0 : -1))
            if (r5 >= 0) goto L_0x010b
            int r6 = (r3 > r11 ? 1 : (r3 == r11 ? 0 : -1))
            if (r6 < 0) goto L_0x010b
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            r5.append(r3)
            java.lang.String r6 = "MB"
            r5.append(r6)
            java.lang.String r6 = r5.toString()
            goto L_0x012e
        L_0x010b:
            if (r5 <= 0) goto L_0x012c
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            long r6 = r3 / r7
            r5.append(r6)
            float r6 = (float) r3
            float r6 = r6 % r15
            float r6 = r6 / r15
            double r6 = (double) r6
            java.lang.String r6 = r13.format(r6)
            r5.append(r6)
            java.lang.String r6 = "GB"
            r5.append(r6)
            java.lang.String r6 = r5.toString()
            goto L_0x012e
        L_0x012c:
            r6 = r17
        L_0x012e:
            android.widget.TextView r5 = r1.c
            r5.setText(r6)
            android.widget.TextView r5 = r1.d
            java.lang.String r6 = "/"
            java.lang.String r2 = java.lang.String.valueOf(r2)
            java.lang.String r2 = r6.concat(r2)
            r5.setText(r2)
            r5 = 100
            long r3 = r3 * r5
            long r3 = r3 / r9
            int r2 = (int) r3
            android.widget.ProgressBar r3 = r1.b
            r4 = 100
            r3.setMax(r4)
            android.widget.ProgressBar r1 = r1.b
            int r5 = 100 - r2
            r1.setProgress(r5)
        L_0x0156:
            return
        L_0x0157:
            if (r1 == 0) goto L_0x0355
            boolean r4 = r1 instanceof defpackage.dft.f
            if (r4 == 0) goto L_0x0355
            dft$f r1 = (defpackage.dft.f) r1
            dgl r2 = r0.a(r2)
            if (r2 == 0) goto L_0x0354
            tw r4 = r2.a
            java.lang.String r4 = r4.f
            int r5 = com.autonavi.minimap.R.drawable.default0
            com.autonavi.minimap.drive.navi.navitts.widget.RoundCornerImageView r8 = r1.a
            r8.setImageResource(r5)
            tw r8 = r2.a
            java.lang.String r8 = r8.k
            boolean r9 = android.webkit.URLUtil.isNetworkUrl(r8)
            r10 = 0
            if (r9 == 0) goto L_0x0180
            com.autonavi.minimap.drive.navi.navitts.widget.RoundCornerImageView r9 = r1.a
            defpackage.ko.a(r9, r8, r10, r5)
        L_0x0180:
            tw r5 = r2.a
            java.lang.String r5 = r5.l
            boolean r8 = android.text.TextUtils.isEmpty(r5)
            if (r8 == 0) goto L_0x018e
            tw r5 = r2.a
            java.lang.String r5 = r5.c
        L_0x018e:
            boolean r8 = android.text.TextUtils.isEmpty(r5)
            if (r8 != 0) goto L_0x0199
            android.widget.TextView r8 = r1.e
            r8.setText(r5)
        L_0x0199:
            tw r5 = r2.a
            java.lang.String r5 = r5.n
            boolean r8 = android.text.TextUtils.isEmpty(r5)
            if (r8 != 0) goto L_0x01a8
            android.widget.TextView r8 = r1.f
            r8.setText(r5)
        L_0x01a8:
            tw r5 = r2.a
            long r8 = r5.g
            float r5 = (float) r8
            float r5 = defpackage.dhd.a(r5)
            r8 = 0
            int r8 = (r5 > r8 ? 1 : (r5 == r8 ? 0 : -1))
            r9 = 8
            if (r8 <= 0) goto L_0x01d4
            android.widget.TextView r8 = r1.g
            java.lang.StringBuilder r11 = new java.lang.StringBuilder
            r11.<init>()
            r11.append(r5)
            java.lang.String r5 = "M"
            r11.append(r5)
            java.lang.String r5 = r11.toString()
            r8.setText(r5)
            android.widget.TextView r5 = r1.g
            r5.setVisibility(r7)
            goto L_0x01d9
        L_0x01d4:
            android.widget.TextView r5 = r1.g
            r5.setVisibility(r9)
        L_0x01d9:
            boolean r5 = r2.d()
            if (r5 != 0) goto L_0x01e5
            android.widget.TextView r5 = r1.e
            r5.setCompoundDrawables(r10, r10, r10, r10)
            goto L_0x01f9
        L_0x01e5:
            android.content.Context r5 = r0.f
            int r8 = com.autonavi.minimap.R.drawable.tj
            android.graphics.drawable.Drawable r5 = r5.getDrawable(r8)
            r8 = 54
            r11 = 27
            r5.setBounds(r7, r7, r8, r11)
            android.widget.TextView r8 = r1.e
            r8.setCompoundDrawables(r10, r10, r5, r10)
        L_0x01f9:
            int r5 = r0.e
            r8 = 1001(0x3e9, float:1.403E-42)
            if (r5 >= r8) goto L_0x0253
            tw r5 = r2.a
            java.lang.String r5 = r5.m
            boolean r5 = android.webkit.URLUtil.isNetworkUrl(r5)
            if (r5 == 0) goto L_0x0253
            dfw r5 = r0.h
            boolean r5 = r5.a(r2)
            if (r5 == 0) goto L_0x0241
            dfw r5 = r0.h
            dfw$a r5 = r5.c
            android.media.MediaPlayer r8 = r5.b
            if (r8 == 0) goto L_0x0221
            int r5 = r5.a
            r8 = 100
            if (r5 >= r8) goto L_0x0221
            r5 = 1
            goto L_0x0222
        L_0x0221:
            r5 = 0
        L_0x0222:
            if (r5 == 0) goto L_0x022f
            android.widget.Button r5 = r1.b
            r5.setVisibility(r9)
            android.widget.ProgressBar r5 = r1.c
            r5.setVisibility(r7)
            goto L_0x025d
        L_0x022f:
            android.widget.Button r5 = r1.b
            int r8 = com.autonavi.minimap.R.drawable.pause
            r5.setBackgroundResource(r8)
            android.widget.Button r5 = r1.b
            r5.setVisibility(r7)
            android.widget.ProgressBar r5 = r1.c
            r5.setVisibility(r9)
            goto L_0x025d
        L_0x0241:
            android.widget.Button r5 = r1.b
            int r8 = com.autonavi.minimap.R.drawable.play
            r5.setBackgroundResource(r8)
            android.widget.Button r5 = r1.b
            r5.setVisibility(r7)
            android.widget.ProgressBar r5 = r1.c
            r5.setVisibility(r9)
            goto L_0x025d
        L_0x0253:
            android.widget.Button r5 = r1.b
            r5.setVisibility(r9)
            android.widget.ProgressBar r5 = r1.c
            r5.setVisibility(r9)
        L_0x025d:
            int r5 = r2.g()
            r8 = 64
            r9 = 4
            if (r5 != 0) goto L_0x0270
            java.lang.String r2 = "下载"
            r0.a(r1, r2, r7)
            c(r1)
            goto L_0x02f1
        L_0x0270:
            r11 = 10
            if (r5 != r3) goto L_0x02a8
            java.lang.String r6 = ""
            r0.a(r1, r6, r3)
            double r2 = d(r2)
            a(r1, r2)
            int r2 = (int) r2
            if (r2 >= r11) goto L_0x0297
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            java.lang.String r6 = " "
            r3.<init>(r6)
        L_0x028a:
            r3.append(r2)
            java.lang.String r2 = "%"
            r3.append(r2)
            java.lang.String r2 = r3.toString()
            goto L_0x029d
        L_0x0297:
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            goto L_0x028a
        L_0x029d:
            android.widget.TextView r3 = r1.i
            r3.setText(r2)
            android.view.View r2 = r1.j
            r2.setVisibility(r7)
            goto L_0x02f1
        L_0x02a8:
            if (r5 != r6) goto L_0x02ba
            java.lang.String r3 = "等待"
            r0.a(r1, r3, r6)
            double r2 = d(r2)
            a(r1, r2)
            b(r1)
            goto L_0x02f1
        L_0x02ba:
            r3 = 3
            if (r5 == r3) goto L_0x02e2
            if (r5 != r11) goto L_0x02c0
            goto L_0x02e2
        L_0x02c0:
            if (r5 != r9) goto L_0x02cb
            java.lang.String r2 = "使用"
            r0.a(r1, r2, r7)
            c(r1)
            goto L_0x02f1
        L_0x02cb:
            if (r5 != r8) goto L_0x02d6
            java.lang.String r2 = "更新"
            r0.a(r1, r2, r7)
            c(r1)
            goto L_0x02f1
        L_0x02d6:
            r2 = 5
            if (r5 != r2) goto L_0x02f1
            java.lang.String r2 = "重试"
            r0.a(r1, r2, r3)
            c(r1)
            goto L_0x02f1
        L_0x02e2:
            java.lang.String r3 = "继续"
            r0.a(r1, r3, r7)
            double r2 = d(r2)
            a(r1, r2)
            b(r1)
        L_0x02f1:
            android.view.View r2 = r1.itemView
            android.content.Context r3 = r0.f
            android.content.res.Resources r3 = r3.getResources()
            int r6 = com.autonavi.minimap.R.color.white
            int r3 = r3.getColor(r6)
            r2.setBackgroundColor(r3)
            dfx r2 = defpackage.dfx.a()
            java.lang.String r2 = r2.b()
            if (r2 == 0) goto L_0x0333
            boolean r3 = r4.equals(r2)
            if (r3 == 0) goto L_0x0333
            android.view.View r2 = r1.itemView
            android.content.Context r3 = r0.f
            android.content.res.Resources r3 = r3.getResources()
            int r4 = com.autonavi.minimap.R.color.offline_navitts_using_background
            int r3 = r3.getColor(r4)
            r2.setBackgroundColor(r3)
            if (r5 != r9) goto L_0x032b
            java.lang.String r2 = "使用中"
            r0.a(r1, r2, r9)
            return
        L_0x032b:
            if (r5 != r8) goto L_0x0354
            java.lang.String r2 = "更新"
            r0.a(r1, r2, r7)
            return
        L_0x0333:
            if (r2 != 0) goto L_0x0354
            dfx r2 = defpackage.dfx.a()
            java.lang.String r3 = "linzhilingyuyin"
            r2.a(r3, r10)
            android.view.View r2 = r1.itemView
            android.content.Context r3 = r0.f
            android.content.res.Resources r3 = r3.getResources()
            int r4 = com.autonavi.minimap.R.color.offline_navitts_using_background
            int r3 = r3.getColor(r4)
            r2.setBackgroundColor(r3)
            java.lang.String r2 = "使用中"
            r0.a(r1, r2, r9)
        L_0x0354:
            return
        L_0x0355:
            return
        L_0x0356:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.dft.onBindViewHolder(android.support.v7.widget.RecyclerView$ViewHolder, int):void");
    }

    public int getItemCount() {
        if (this.b == null) {
            return 0;
        }
        return this.b.size();
    }

    public int getItemViewType(int i2) {
        return this.b.get(i2).a;
    }

    public final void c(dgl dgl) {
        b(dgl);
    }

    public final void b() {
        ToastHelper.showToast(this.f.getString(R.string.offline_neterror));
    }

    public final void b(int i2) {
        dgl a2 = a(i2);
        if (a2 != null) {
            if (this.h.a(a2)) {
                this.h.b();
                return;
            }
            this.h.b();
            if (Tts.getInstance().JniIsPlaying() == 1) {
                ToastHelper.showToast(this.f.getString(R.string.navitts_isplaying));
            } else {
                a(a2);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void a(dgl dgl) {
        String str;
        if (!aaw.c(this.f.getApplicationContext())) {
            ToastHelper.showToast(this.f.getString(R.string.offline_neterror));
            return;
        }
        try {
            String str2 = dgl.a.m;
            File file = new File(defpackage.dfw.b.a.a, dhd.b(str2));
            if (!file.exists() || !file.isFile()) {
                str = null;
            } else {
                str = file.getAbsolutePath();
            }
            if (!TextUtils.isEmpty(str)) {
                this.h.a(dgl, str);
            } else {
                this.h.a(dgl, str2);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
            ToastHelper.showToast(this.f.getString(R.string.offline_neterror));
        }
    }

    public final void c(int i2) {
        dgl a2 = a(i2);
        if (a2 != null && !a2.c()) {
            int g2 = a2.g();
            String str = a2.a.c;
            if (g2 == 0 || g2 == 64) {
                Context applicationContext = AMapAppGlobal.getApplication().getApplicationContext();
                if (!dfs.a() || (!dfs.a(applicationContext) && !dfs.b(applicationContext))) {
                    this.d.checkNetAndSendHandlerMessage(a2, 100);
                } else {
                    d(i2);
                }
                if (g2 == 0) {
                    LogManager.actionLogV2("P00067", "B011", dgv.a(PoiLayoutTemplate.BUTTON, str, NVUtil.a(this.e), NVUtil.a()));
                }
            } else if (g2 == 2 || g2 == 1) {
                DriveOfflineSDK.e().c(a2);
            } else if (g2 == 3 || g2 == 5 || g2 == 10) {
                this.d.checkNetAndSendHandlerMessage(a2, 101);
            } else if (g2 == 4) {
                synchronized (this.k) {
                    if (this.j) {
                        ToastHelper.showToast(this.f.getString(R.string.navitts_insetting));
                    } else {
                        this.j = true;
                        defpackage.dfw.b.a.b();
                        final dgl c2 = dfx.a().c();
                        dfx.a();
                        dfx.a(a2, (dgx) new dgx() {
                            public final void a(boolean z) {
                                String str;
                                dft.this.d.sendHandlerMessage(41);
                                dft.this.d.dismissDialog();
                                dft.this.j = false;
                                OfflineNaviTtsFragment offlineNaviTtsFragment = dft.this.d;
                                if (c2 == null) {
                                    str = "";
                                } else {
                                    str = c2.a.l;
                                }
                                offlineNaviTtsFragment.uploadTTSNameLog(str);
                            }
                        });
                        this.d.refreshListView();
                    }
                }
            }
        }
    }

    public final void d(int i2) {
        dgl a2 = a(i2);
        if (a2 != null) {
            this.d.showDownloadDialog(a2, false);
        }
    }

    private d g(int i2) {
        d dVar;
        if (this.b == null || this.b.size() <= i2) {
            return null;
        }
        try {
            dVar = (d) this.b.get(i2).b;
        } catch (Exception unused) {
            dVar = null;
        }
        return dVar;
    }

    private static void a(f fVar, double d2) {
        if (d2 <= 0.0d) {
            a(fVar);
            return;
        }
        fVar.k.setCurrentProgress((float) (d2 / 100.0d));
        fVar.k.setVisibility(0);
    }

    private static void a(f fVar) {
        fVar.k.setVisibility(8);
    }

    private static void b(f fVar) {
        fVar.j.setVisibility(8);
    }

    private static void c(f fVar) {
        a(fVar);
        b(fVar);
    }

    private void a(f fVar, String str, int i2) {
        fVar.d.setText(str);
        if (TextUtils.isEmpty(str)) {
            fVar.d.setVisibility(8);
            return;
        }
        fVar.d.setVisibility(0);
        if (i2 == 4) {
            fVar.h.setEnabled(false);
        } else {
            fVar.h.setEnabled(true);
        }
        if (i2 == 4) {
            fVar.d.setTextColor(this.f.getApplicationContext().getResources().getColor(R.color.offline_navitts_using));
            fVar.h.setBackgroundColor(this.f.getResources().getColor(R.color.offline_navitts_using_background));
        } else if (i2 == 0) {
            fVar.d.setTextColor(this.f.getResources().getColor(R.color.f_c_6));
        } else if (i2 == 1) {
            fVar.d.setTextColor(this.f.getResources().getColor(R.color.f_c_6));
        } else if (i2 == 2) {
            fVar.d.setTextColor(this.f.getResources().getColor(R.color.offline_navitts_waiting));
        } else {
            if (i2 == 3) {
                fVar.d.setTextColor(this.f.getResources().getColor(R.color.f_c_6));
            }
        }
    }

    public final boolean e(int i2) {
        return getItemViewType(i2) == 5;
    }

    public final int f(int i2) {
        return getItemViewType(i2);
    }

    public final void a(defpackage.dfu.a aVar) {
        this.l = aVar;
    }

    private static double d(dgl dgl) {
        double d2 = (double) (dgl.b != null ? dgl.b.d : 0);
        double f2 = (double) dgl.f();
        double a2 = (f2 > d2 || d2 == 0.0d) ? 0.0d : dhd.a((f2 / d2) * 100.0d);
        if (a2 <= 0.0d) {
            return 0.0d;
        }
        if (a2 >= 100.0d) {
            return 100.0d;
        }
        return a2;
    }

    static /* synthetic */ void b(dft dft) {
        int i2;
        DriveOfflineSDK.e();
        switch (DriveOfflineSDK.a()) {
            case 1:
                i2 = R.string.offline_storage_warn_downing_offline;
                break;
            case 2:
                i2 = R.string.offline_storage_warn_downing_navitts;
                break;
            case 3:
                i2 = R.string.offline_storage_warn_downing;
                break;
            default:
                i2 = 0;
                break;
        }
        if (i2 != 0) {
            ToastHelper.showToast(dft.f.getString(i2));
            return;
        }
        String b2 = PathManager.a().b(DirType.DRIVE_VOICE);
        ArrayList<SdCardInfo> enumExternalSDcardInfo = FileUtil.enumExternalSDcardInfo(dft.f.getApplicationContext());
        if (enumExternalSDcardInfo != null && enumExternalSDcardInfo.size() > 0) {
            if (enumExternalSDcardInfo.size() > 1) {
                Iterator<SdCardInfo> it = enumExternalSDcardInfo.iterator();
                while (it.hasNext()) {
                    SdCardInfo next = it.next();
                    if (next != null && !TextUtils.isEmpty(next.b) && !next.b.equals(b2)) {
                        dhd.a(next.b);
                    }
                }
            }
            dft.d.startPageForResult(StorageFragment.class, new PageBundle(), 1);
        }
    }
}
