package defpackage;

import android.app.Activity;
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
import com.amap.bundle.utils.ui.CompatDialog;
import com.autonavi.minimap.R;
import java.util.ArrayList;

/* renamed from: cst reason: default package */
/* compiled from: PicViewPagerDialog2 */
public final class cst extends CompatDialog implements OnClickListener {
    /* access modifiers changed from: private */
    public TextView a;
    private TextView b;
    private TextView c;
    private ImageView d;
    private ImageButton e;
    private ViewPager f;
    private View g;
    private View h;
    private a i;
    private LinearLayout j;
    private b k;
    /* access modifiers changed from: private */
    public ArrayList<String> l;
    /* access modifiers changed from: private */
    public boolean m = true;
    private OnTouchListener n = new OnTouchListener() {
        public final boolean onTouch(View view, MotionEvent motionEvent) {
            return !cst.this.m;
        }
    };
    private long o;

    /* renamed from: cst$a */
    /* compiled from: PicViewPagerDialog2 */
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
            if (cst.this.l != null) {
                return cst.this.l.size();
            }
            return 0;
        }

        public final void destroyItem(ViewGroup viewGroup, int i, Object obj) {
            if (obj instanceof View) {
                viewGroup.removeView((View) obj);
            }
        }

        public final Object instantiateItem(ViewGroup viewGroup, int i) {
            ImageView imageView = new ImageView(cst.this.getContext());
            viewGroup.addView(imageView, 0);
            ko.a(imageView, (String) cst.this.l.get(i));
            imageView.setOnClickListener(new OnClickListener() {
                public final void onClick(View view) {
                }
            });
            return imageView;
        }
    }

    /* renamed from: cst$b */
    /* compiled from: PicViewPagerDialog2 */
    public interface b {
        void a();

        void a(String str);
    }

    public cst(Activity activity, ArrayList<String> arrayList, b bVar, int i2) {
        super(activity, R.style.TrafficDialog);
        this.k = bVar;
        this.l = new ArrayList<>(arrayList);
        setContentView(R.layout.error_pic_viewpager_layout2);
        Window window = getWindow();
        window.setLayout(-1, -1);
        this.j = (LinearLayout) window.findViewById(R.id.delete_footer);
        this.a = (TextView) window.findViewById(R.id.title_text_name);
        this.b = (TextView) window.findViewById(R.id.footer_cancle_btn);
        this.c = (TextView) window.findViewById(R.id.footer_delete_btn);
        this.d = (ImageView) window.findViewById(R.id.title_btn_left);
        this.e = (ImageButton) window.findViewById(R.id.title_btn_right);
        this.f = (ViewPager) window.findViewById(R.id.error_pic_horizontal_pager);
        this.g = window.findViewById(R.id.btn_delete);
        this.h = window.findViewById(R.id.btn_rephotogragh);
        window.findViewById(R.id.touch).setOnTouchListener(this.n);
        this.a.setOnClickListener(this);
        this.d.setOnClickListener(this);
        this.e.setOnClickListener(this);
        this.b.setOnClickListener(this);
        this.c.setOnClickListener(this);
        this.g.setOnClickListener(this);
        this.h.setOnClickListener(this);
        this.i = new a();
        this.f.setAdapter(this.i);
        this.f.setPageMargin(getContext().getResources().getDimensionPixelOffset(R.dimen.error_padding_low));
        this.f.setOnPageChangeListener(new OnPageChangeListener() {
            public final void onPageScrollStateChanged(int i) {
            }

            public final void onPageScrolled(int i, float f, int i2) {
            }

            public final void onPageSelected(int i) {
                StringBuilder sb = new StringBuilder();
                sb.append(i + 1);
                sb.append("/");
                sb.append(cst.this.l.size());
                cst.this.a.setText(sb.toString());
            }
        });
        this.f.setCurrentItem(i2 - 1);
        a();
    }

    private void a() {
        if (this.a != null) {
            this.a.setText(getContext().getString(R.string.photo_preview_title2));
        }
    }

    public final void dismiss() {
        super.dismiss();
        this.i.notifyDataSetChanged();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:45:0x00e2, code lost:
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
            android.widget.LinearLayout r10 = r9.j
            int r10 = r10.getVisibility()
            if (r10 != r3) goto L_0x00e6
            r10 = 0
            r9.m = r10
            android.widget.LinearLayout r0 = r9.j
            r0.setVisibility(r10)
            android.content.Context r10 = r9.getContext()
            int r0 = com.autonavi.minimap.R.anim.autonavi_bottom_in
            android.view.animation.Animation r10 = android.view.animation.AnimationUtils.loadAnimation(r10, r0)
            r10.setDuration(r1)
            android.widget.LinearLayout r0 = r9.j
            r0.setAnimation(r10)
            return
        L_0x002b:
            android.widget.TextView r0 = r9.c
            r4 = 1
            if (r10 == r0) goto L_0x0071
            android.view.View r0 = r9.g
            if (r10 != r0) goto L_0x0035
            goto L_0x0071
        L_0x0035:
            android.widget.TextView r0 = r9.b
            if (r10 != r0) goto L_0x005b
            android.widget.LinearLayout r10 = r9.j
            int r10 = r10.getVisibility()
            if (r10 != 0) goto L_0x00e6
            r9.m = r4
            android.widget.LinearLayout r10 = r9.j
            r10.setVisibility(r3)
            android.content.Context r10 = r9.getContext()
            int r0 = com.autonavi.minimap.R.anim.autonavi_bottom_out
            android.view.animation.Animation r10 = android.view.animation.AnimationUtils.loadAnimation(r10, r0)
            r10.setDuration(r1)
            android.widget.LinearLayout r0 = r9.j
            r0.setAnimation(r10)
            return
        L_0x005b:
            android.widget.ImageView r0 = r9.d
            if (r10 != r0) goto L_0x0063
            r9.dismiss()
            return
        L_0x0063:
            android.view.View r0 = r9.h
            if (r10 != r0) goto L_0x00e6
            cst$b r10 = r9.k
            if (r10 == 0) goto L_0x00e6
            cst$b r10 = r9.k
            r10.a()
            goto L_0x00e6
        L_0x0071:
            long r5 = java.lang.System.currentTimeMillis()
            long r7 = r9.o
            long r5 = r5 - r7
            r7 = 1000(0x3e8, double:4.94E-321)
            int r10 = (r5 > r7 ? 1 : (r5 == r7 ? 0 : -1))
            if (r10 <= 0) goto L_0x00e6
            java.util.ArrayList<java.lang.String> r10 = r9.l
            monitor-enter(r10)
            long r5 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x00e3 }
            r9.o = r5     // Catch:{ all -> 0x00e3 }
            android.support.v4.view.ViewPager r0 = r9.f     // Catch:{ all -> 0x00e3 }
            int r0 = r0.getCurrentItem()     // Catch:{ all -> 0x00e3 }
            java.util.ArrayList<java.lang.String> r5 = r9.l     // Catch:{ all -> 0x00e3 }
            int r5 = r5.size()     // Catch:{ all -> 0x00e3 }
            if (r0 >= r5) goto L_0x00c0
            java.util.ArrayList<java.lang.String> r5 = r9.l     // Catch:{ all -> 0x00e3 }
            java.lang.Object r5 = r5.get(r0)     // Catch:{ all -> 0x00e3 }
            java.lang.String r5 = (java.lang.String) r5     // Catch:{ all -> 0x00e3 }
            java.util.ArrayList<java.lang.String> r6 = r9.l     // Catch:{ all -> 0x00e3 }
            r6.remove(r0)     // Catch:{ all -> 0x00e3 }
            cst$a r0 = r9.i     // Catch:{ all -> 0x00e3 }
            r0.notifyDataSetChanged()     // Catch:{ all -> 0x00e3 }
            cst$b r0 = r9.k     // Catch:{ all -> 0x00e3 }
            if (r0 == 0) goto L_0x00b0
            cst$b r0 = r9.k     // Catch:{ all -> 0x00e3 }
            r0.a(r5)     // Catch:{ all -> 0x00e3 }
        L_0x00b0:
            java.util.ArrayList<java.lang.String> r0 = r9.l     // Catch:{ all -> 0x00e3 }
            int r0 = r0.size()     // Catch:{ all -> 0x00e3 }
            if (r0 != 0) goto L_0x00bd
            r9.dismiss()     // Catch:{ all -> 0x00e3 }
            monitor-exit(r10)     // Catch:{ all -> 0x00e3 }
            return
        L_0x00bd:
            r9.a()     // Catch:{ all -> 0x00e3 }
        L_0x00c0:
            android.widget.LinearLayout r0 = r9.j     // Catch:{ all -> 0x00e3 }
            int r0 = r0.getVisibility()     // Catch:{ all -> 0x00e3 }
            if (r0 != 0) goto L_0x00e1
            r9.m = r4     // Catch:{ all -> 0x00e3 }
            android.widget.LinearLayout r0 = r9.j     // Catch:{ all -> 0x00e3 }
            r0.setVisibility(r3)     // Catch:{ all -> 0x00e3 }
            android.content.Context r0 = r9.getContext()     // Catch:{ all -> 0x00e3 }
            int r3 = com.autonavi.minimap.R.anim.autonavi_bottom_out     // Catch:{ all -> 0x00e3 }
            android.view.animation.Animation r0 = android.view.animation.AnimationUtils.loadAnimation(r0, r3)     // Catch:{ all -> 0x00e3 }
            r0.setDuration(r1)     // Catch:{ all -> 0x00e3 }
            android.widget.LinearLayout r1 = r9.j     // Catch:{ all -> 0x00e3 }
            r1.setAnimation(r0)     // Catch:{ all -> 0x00e3 }
        L_0x00e1:
            monitor-exit(r10)     // Catch:{ all -> 0x00e3 }
            return
        L_0x00e3:
            r0 = move-exception
            monitor-exit(r10)     // Catch:{ all -> 0x00e3 }
            throw r0
        L_0x00e6:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.cst.onClick(android.view.View):void");
    }
}
