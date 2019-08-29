package defpackage;

import android.app.Activity;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.autonavi.common.imageloader.ImageLoader;
import com.autonavi.minimap.R;
import java.io.File;
import java.util.ArrayList;

/* renamed from: avg reason: default package */
/* compiled from: PicViewPagerDialog */
public final class avg extends bto implements OnClickListener {
    /* access modifiers changed from: private */
    public TextView a;
    private TextView b;
    private TextView c;
    private ImageView d;
    private ImageButton e;
    private ViewPager f;
    private a g;
    private LinearLayout h;
    private b i;
    /* access modifiers changed from: private */
    public ArrayList<String> j;
    /* access modifiers changed from: private */
    public boolean k = true;
    private OnTouchListener l = new OnTouchListener() {
        public final boolean onTouch(View view, MotionEvent motionEvent) {
            return !avg.this.k;
        }
    };
    private long m;

    /* renamed from: avg$a */
    /* compiled from: PicViewPagerDialog */
    class a extends PagerAdapter {
        public final int getItemPosition(Object obj) {
            return -2;
        }

        public final boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }

        public a() {
        }

        public final int getCount() {
            if (avg.this.j != null) {
                return avg.this.j.size();
            }
            return 0;
        }

        public final void destroyItem(ViewGroup viewGroup, int i, Object obj) {
            if (obj instanceof View) {
                viewGroup.removeView((View) obj);
            }
        }

        public final Object instantiateItem(ViewGroup viewGroup, int i) {
            ImageView imageView = new ImageView(avg.this.getContext());
            viewGroup.addView(imageView, 0);
            String str = (String) avg.this.j.get(i);
            if (str != null) {
                ImageLoader.a(avg.this.getContext()).a(avg.a(str)).a(imageView, (bjl) null);
            }
            return imageView;
        }
    }

    /* renamed from: avg$b */
    /* compiled from: PicViewPagerDialog */
    public interface b {
        void a(int i);
    }

    public avg(Activity activity, ArrayList<String> arrayList, b bVar, int i2) {
        super(activity, R.style.custom_declare_dlg);
        this.j = new ArrayList<>(arrayList);
        setContentView(R.layout.error_pic_viewpager_layout);
        Window window = getWindow();
        window.setLayout(-1, -1);
        this.h = (LinearLayout) window.findViewById(R.id.delete_footer);
        this.a = (TextView) window.findViewById(R.id.title_text_name);
        this.b = (TextView) window.findViewById(R.id.footer_cancle_btn);
        this.c = (TextView) window.findViewById(R.id.footer_delete_btn);
        this.a = (TextView) window.findViewById(R.id.title_text_name);
        this.d = (ImageView) window.findViewById(R.id.title_btn_left);
        this.e = (ImageButton) window.findViewById(R.id.title_btn_right);
        this.f = (ViewPager) window.findViewById(R.id.error_pic_horizontal_pager);
        window.findViewById(R.id.touch).setOnTouchListener(this.l);
        this.a.setOnClickListener(this);
        this.d.setOnClickListener(this);
        this.e.setOnClickListener(this);
        this.b.setOnClickListener(this);
        this.c.setOnClickListener(this);
        this.g = new a();
        this.f.setAdapter(this.g);
        this.f.setPageMargin(activity.getResources().getDimensionPixelOffset(R.dimen.error_padding_low));
        this.f.setOnPageChangeListener(new OnPageChangeListener() {
            public final void onPageScrollStateChanged(int i) {
            }

            public final void onPageScrolled(int i, float f, int i2) {
            }

            public final void onPageSelected(int i) {
                StringBuilder sb = new StringBuilder();
                sb.append(i + 1);
                sb.append("/");
                sb.append(avg.this.j.size());
                avg.this.a.setText(sb.toString());
            }
        });
        if (bVar == null) {
            this.e.setVisibility(8);
            this.c.setVisibility(8);
        } else {
            this.i = bVar;
        }
        TextView textView = this.a;
        StringBuilder sb = new StringBuilder();
        sb.append(i2);
        sb.append("/");
        sb.append(this.j.size());
        textView.setText(sb.toString());
        this.f.setCurrentItem(i2 - 1);
    }

    public final void dismiss() {
        super.dismiss();
        this.g.notifyDataSetChanged();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:25:0x00bd, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void onClick(android.view.View r10) {
        /*
            r9 = this;
            android.widget.ImageButton r0 = r9.e
            r1 = 100
            r3 = 8
            if (r10 != r0) goto L_0x002b
            android.widget.LinearLayout r10 = r9.h
            int r10 = r10.getVisibility()
            if (r10 != r3) goto L_0x00ee
            r10 = 0
            r9.k = r10
            android.widget.LinearLayout r0 = r9.h
            r0.setVisibility(r10)
            android.content.Context r10 = r9.getContext()
            int r0 = com.autonavi.minimap.R.anim.autonavi_bottom_in
            android.view.animation.Animation r10 = android.view.animation.AnimationUtils.loadAnimation(r10, r0)
            r10.setDuration(r1)
            android.widget.LinearLayout r0 = r9.h
            r0.setAnimation(r10)
            return
        L_0x002b:
            android.widget.TextView r0 = r9.c
            r4 = 1
            if (r10 != r0) goto L_0x00c1
            long r5 = java.lang.System.currentTimeMillis()
            long r7 = r9.m
            long r5 = r5 - r7
            r7 = 1000(0x3e8, double:4.94E-321)
            int r10 = (r5 > r7 ? 1 : (r5 == r7 ? 0 : -1))
            if (r10 <= 0) goto L_0x00ee
            java.util.ArrayList<java.lang.String> r10 = r9.j
            monitor-enter(r10)
            long r5 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x00be }
            r9.m = r5     // Catch:{ all -> 0x00be }
            android.support.v4.view.ViewPager r0 = r9.f     // Catch:{ all -> 0x00be }
            int r0 = r0.getCurrentItem()     // Catch:{ all -> 0x00be }
            java.util.ArrayList<java.lang.String> r5 = r9.j     // Catch:{ all -> 0x00be }
            int r5 = r5.size()     // Catch:{ all -> 0x00be }
            if (r0 >= r5) goto L_0x009b
            java.util.ArrayList<java.lang.String> r5 = r9.j     // Catch:{ all -> 0x00be }
            r5.get(r0)     // Catch:{ all -> 0x00be }
            java.util.ArrayList<java.lang.String> r5 = r9.j     // Catch:{ all -> 0x00be }
            r5.remove(r0)     // Catch:{ all -> 0x00be }
            avg$a r5 = r9.g     // Catch:{ all -> 0x00be }
            r5.notifyDataSetChanged()     // Catch:{ all -> 0x00be }
            avg$b r5 = r9.i     // Catch:{ all -> 0x00be }
            r5.a(r0)     // Catch:{ all -> 0x00be }
            java.util.ArrayList<java.lang.String> r0 = r9.j     // Catch:{ all -> 0x00be }
            int r0 = r0.size()     // Catch:{ all -> 0x00be }
            if (r0 != 0) goto L_0x0075
            r9.dismiss()     // Catch:{ all -> 0x00be }
            monitor-exit(r10)     // Catch:{ all -> 0x00be }
            return
        L_0x0075:
            android.widget.TextView r0 = r9.a     // Catch:{ all -> 0x00be }
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ all -> 0x00be }
            r5.<init>()     // Catch:{ all -> 0x00be }
            android.support.v4.view.ViewPager r6 = r9.f     // Catch:{ all -> 0x00be }
            int r6 = r6.getCurrentItem()     // Catch:{ all -> 0x00be }
            int r6 = r6 + r4
            r5.append(r6)     // Catch:{ all -> 0x00be }
            java.lang.String r6 = "/"
            r5.append(r6)     // Catch:{ all -> 0x00be }
            java.util.ArrayList<java.lang.String> r6 = r9.j     // Catch:{ all -> 0x00be }
            int r6 = r6.size()     // Catch:{ all -> 0x00be }
            r5.append(r6)     // Catch:{ all -> 0x00be }
            java.lang.String r5 = r5.toString()     // Catch:{ all -> 0x00be }
            r0.setText(r5)     // Catch:{ all -> 0x00be }
        L_0x009b:
            android.widget.LinearLayout r0 = r9.h     // Catch:{ all -> 0x00be }
            int r0 = r0.getVisibility()     // Catch:{ all -> 0x00be }
            if (r0 != 0) goto L_0x00bc
            r9.k = r4     // Catch:{ all -> 0x00be }
            android.widget.LinearLayout r0 = r9.h     // Catch:{ all -> 0x00be }
            r0.setVisibility(r3)     // Catch:{ all -> 0x00be }
            android.content.Context r0 = r9.getContext()     // Catch:{ all -> 0x00be }
            int r3 = com.autonavi.minimap.R.anim.autonavi_bottom_out     // Catch:{ all -> 0x00be }
            android.view.animation.Animation r0 = android.view.animation.AnimationUtils.loadAnimation(r0, r3)     // Catch:{ all -> 0x00be }
            r0.setDuration(r1)     // Catch:{ all -> 0x00be }
            android.widget.LinearLayout r1 = r9.h     // Catch:{ all -> 0x00be }
            r1.setAnimation(r0)     // Catch:{ all -> 0x00be }
        L_0x00bc:
            monitor-exit(r10)     // Catch:{ all -> 0x00be }
            return
        L_0x00be:
            r0 = move-exception
            monitor-exit(r10)     // Catch:{ all -> 0x00be }
            throw r0
        L_0x00c1:
            android.widget.TextView r0 = r9.b
            if (r10 != r0) goto L_0x00e7
            android.widget.LinearLayout r10 = r9.h
            int r10 = r10.getVisibility()
            if (r10 != 0) goto L_0x00ee
            r9.k = r4
            android.widget.LinearLayout r10 = r9.h
            r10.setVisibility(r3)
            android.content.Context r10 = r9.getContext()
            int r0 = com.autonavi.minimap.R.anim.autonavi_bottom_out
            android.view.animation.Animation r10 = android.view.animation.AnimationUtils.loadAnimation(r10, r0)
            r10.setDuration(r1)
            android.widget.LinearLayout r0 = r9.h
            r0.setAnimation(r10)
            return
        L_0x00e7:
            android.widget.ImageView r0 = r9.d
            if (r10 != r0) goto L_0x00ee
            r9.dismiss()
        L_0x00ee:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.avg.onClick(android.view.View):void");
    }

    static /* synthetic */ Uri a(String str) {
        if (!str.startsWith("/")) {
            return Uri.parse(str);
        }
        if (new File(str).exists()) {
            return Uri.fromFile(new File(str));
        }
        return Uri.parse(str);
    }
}
