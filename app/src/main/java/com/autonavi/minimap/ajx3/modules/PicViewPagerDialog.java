package com.autonavi.minimap.ajx3.modules;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
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
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import com.amap.bundle.utils.ui.CompatDialog;
import com.autonavi.common.imageloader.ImageLoader;
import com.autonavi.minimap.R;
import java.io.File;
import java.util.ArrayList;

public class PicViewPagerDialog extends CompatDialog implements OnClickListener {
    private ErrorPagerAdapter adapter;
    private ImageView back;
    private TextView cancleTV;
    private TextView deletTV;
    private ImageButton delete;
    private LinearLayout deleteFooter;
    private OnDeleteListener deleteListener;
    /* access modifiers changed from: private */
    public ArrayList<String> mPicPaths;
    private ViewPager picPager;
    /* access modifiers changed from: private */
    public boolean scrollerble = true;
    private long times;
    /* access modifiers changed from: private */
    public TextView title;
    private OnTouchListener touch = new OnTouchListener() {
        public boolean onTouch(View view, MotionEvent motionEvent) {
            return !PicViewPagerDialog.this.scrollerble;
        }
    };

    class ErrorPagerAdapter extends PagerAdapter {
        public int getItemPosition(Object obj) {
            return -2;
        }

        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }

        public ErrorPagerAdapter() {
        }

        public int getCount() {
            if (PicViewPagerDialog.this.mPicPaths != null) {
                return PicViewPagerDialog.this.mPicPaths.size();
            }
            return 0;
        }

        public void destroyItem(ViewGroup viewGroup, int i, Object obj) {
            if (obj instanceof View) {
                viewGroup.removeView((View) obj);
            }
        }

        public Object instantiateItem(ViewGroup viewGroup, int i) {
            ImageView imageView = new ImageView(PicViewPagerDialog.this.getContext());
            viewGroup.addView(imageView, 0);
            String str = (String) PicViewPagerDialog.this.mPicPaths.get(i);
            if (str != null) {
                ImageLoader.a(PicViewPagerDialog.this.getContext()).a(PicViewPagerDialog.this.pathToUri(str)).a(imageView, (bjl) null);
            }
            return imageView;
        }
    }

    public interface OnDeleteListener {
        void onDelete(String str);

        void onIndexDelete(int i);
    }

    public PicViewPagerDialog(Activity activity) {
        super(activity);
    }

    public PicViewPagerDialog(Activity activity, ArrayList<String> arrayList, OnDeleteListener onDeleteListener, int i) {
        super(activity, R.style.TrafficDialog);
        this.deleteListener = onDeleteListener;
        this.mPicPaths = new ArrayList<>(arrayList);
        init(activity);
        TextView textView = this.title;
        StringBuilder sb = new StringBuilder();
        sb.append(i);
        sb.append("/");
        sb.append(this.mPicPaths.size());
        textView.setText(sb.toString());
        this.picPager.setCurrentItem(i - 1);
    }

    private void init(Context context) {
        setContentView(R.layout.error_pic_viewpager_layout);
        Window window = getWindow();
        window.setLayout(-1, -1);
        this.deleteFooter = (LinearLayout) window.findViewById(R.id.delete_footer);
        this.title = (TextView) window.findViewById(R.id.title_text_name);
        this.cancleTV = (TextView) window.findViewById(R.id.footer_cancle_btn);
        this.deletTV = (TextView) window.findViewById(R.id.footer_delete_btn);
        this.title = (TextView) window.findViewById(R.id.title_text_name);
        this.back = (ImageView) window.findViewById(R.id.title_btn_left);
        this.delete = (ImageButton) window.findViewById(R.id.title_btn_right);
        this.picPager = (ViewPager) window.findViewById(R.id.error_pic_horizontal_pager);
        window.findViewById(R.id.touch).setOnTouchListener(this.touch);
        this.title.setOnClickListener(this);
        this.back.setOnClickListener(this);
        this.delete.setOnClickListener(this);
        this.cancleTV.setOnClickListener(this);
        this.deletTV.setOnClickListener(this);
        this.adapter = new ErrorPagerAdapter();
        this.picPager.setAdapter(this.adapter);
        this.picPager.setPageMargin(context.getResources().getDimensionPixelOffset(R.dimen.error_padding_low));
        this.picPager.setOnPageChangeListener(new OnPageChangeListener() {
            public void onPageScrollStateChanged(int i) {
            }

            public void onPageScrolled(int i, float f, int i2) {
            }

            public void onPageSelected(int i) {
                StringBuilder sb = new StringBuilder();
                sb.append(i + 1);
                sb.append("/");
                sb.append(PicViewPagerDialog.this.mPicPaths.size());
                PicViewPagerDialog.this.title.setText(sb.toString());
            }
        });
        if (euk.a()) {
            int a = euk.a(context);
            euk.a((Dialog) this, 0);
            View findViewById = window.findViewById(R.id.error_tetle);
            LayoutParams layoutParams = (LayoutParams) findViewById.getLayoutParams();
            layoutParams.topMargin = a;
            findViewById.setLayoutParams(layoutParams);
        }
    }

    public void dismiss() {
        super.dismiss();
        this.adapter.notifyDataSetChanged();
    }

    /* access modifiers changed from: private */
    public Uri pathToUri(@NonNull String str) {
        if (!str.startsWith("/")) {
            return Uri.parse(str);
        }
        if (new File(str).exists()) {
            return Uri.fromFile(new File(str));
        }
        return Uri.parse(str);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:25:0x00c5, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onClick(android.view.View r10) {
        /*
            r9 = this;
            android.widget.ImageButton r0 = r9.delete
            r1 = 100
            r3 = 8
            if (r10 != r0) goto L_0x002b
            android.widget.LinearLayout r10 = r9.deleteFooter
            int r10 = r10.getVisibility()
            if (r10 != r3) goto L_0x00f6
            r10 = 0
            r9.scrollerble = r10
            android.widget.LinearLayout r0 = r9.deleteFooter
            r0.setVisibility(r10)
            android.content.Context r10 = r9.getContext()
            int r0 = com.autonavi.minimap.R.anim.autonavi_bottom_in
            android.view.animation.Animation r10 = android.view.animation.AnimationUtils.loadAnimation(r10, r0)
            r10.setDuration(r1)
            android.widget.LinearLayout r0 = r9.deleteFooter
            r0.setAnimation(r10)
            return
        L_0x002b:
            android.widget.TextView r0 = r9.deletTV
            r4 = 1
            if (r10 != r0) goto L_0x00c9
            long r5 = java.lang.System.currentTimeMillis()
            long r7 = r9.times
            long r5 = r5 - r7
            r7 = 1000(0x3e8, double:4.94E-321)
            int r10 = (r5 > r7 ? 1 : (r5 == r7 ? 0 : -1))
            if (r10 <= 0) goto L_0x00f6
            java.util.ArrayList<java.lang.String> r10 = r9.mPicPaths
            monitor-enter(r10)
            long r5 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x00c6 }
            r9.times = r5     // Catch:{ all -> 0x00c6 }
            android.support.v4.view.ViewPager r0 = r9.picPager     // Catch:{ all -> 0x00c6 }
            int r0 = r0.getCurrentItem()     // Catch:{ all -> 0x00c6 }
            java.util.ArrayList<java.lang.String> r5 = r9.mPicPaths     // Catch:{ all -> 0x00c6 }
            int r5 = r5.size()     // Catch:{ all -> 0x00c6 }
            if (r0 >= r5) goto L_0x00a3
            java.util.ArrayList<java.lang.String> r5 = r9.mPicPaths     // Catch:{ all -> 0x00c6 }
            java.lang.Object r5 = r5.get(r0)     // Catch:{ all -> 0x00c6 }
            java.lang.String r5 = (java.lang.String) r5     // Catch:{ all -> 0x00c6 }
            java.util.ArrayList<java.lang.String> r6 = r9.mPicPaths     // Catch:{ all -> 0x00c6 }
            r6.remove(r0)     // Catch:{ all -> 0x00c6 }
            com.autonavi.minimap.ajx3.modules.PicViewPagerDialog$ErrorPagerAdapter r6 = r9.adapter     // Catch:{ all -> 0x00c6 }
            r6.notifyDataSetChanged()     // Catch:{ all -> 0x00c6 }
            com.autonavi.minimap.ajx3.modules.PicViewPagerDialog$OnDeleteListener r6 = r9.deleteListener     // Catch:{ all -> 0x00c6 }
            r6.onDelete(r5)     // Catch:{ all -> 0x00c6 }
            com.autonavi.minimap.ajx3.modules.PicViewPagerDialog$OnDeleteListener r5 = r9.deleteListener     // Catch:{ all -> 0x00c6 }
            r5.onIndexDelete(r0)     // Catch:{ all -> 0x00c6 }
            java.util.ArrayList<java.lang.String> r0 = r9.mPicPaths     // Catch:{ all -> 0x00c6 }
            int r0 = r0.size()     // Catch:{ all -> 0x00c6 }
            if (r0 != 0) goto L_0x007d
            r9.dismiss()     // Catch:{ all -> 0x00c6 }
            monitor-exit(r10)     // Catch:{ all -> 0x00c6 }
            return
        L_0x007d:
            android.widget.TextView r0 = r9.title     // Catch:{ all -> 0x00c6 }
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ all -> 0x00c6 }
            r5.<init>()     // Catch:{ all -> 0x00c6 }
            android.support.v4.view.ViewPager r6 = r9.picPager     // Catch:{ all -> 0x00c6 }
            int r6 = r6.getCurrentItem()     // Catch:{ all -> 0x00c6 }
            int r6 = r6 + r4
            r5.append(r6)     // Catch:{ all -> 0x00c6 }
            java.lang.String r6 = "/"
            r5.append(r6)     // Catch:{ all -> 0x00c6 }
            java.util.ArrayList<java.lang.String> r6 = r9.mPicPaths     // Catch:{ all -> 0x00c6 }
            int r6 = r6.size()     // Catch:{ all -> 0x00c6 }
            r5.append(r6)     // Catch:{ all -> 0x00c6 }
            java.lang.String r5 = r5.toString()     // Catch:{ all -> 0x00c6 }
            r0.setText(r5)     // Catch:{ all -> 0x00c6 }
        L_0x00a3:
            android.widget.LinearLayout r0 = r9.deleteFooter     // Catch:{ all -> 0x00c6 }
            int r0 = r0.getVisibility()     // Catch:{ all -> 0x00c6 }
            if (r0 != 0) goto L_0x00c4
            r9.scrollerble = r4     // Catch:{ all -> 0x00c6 }
            android.widget.LinearLayout r0 = r9.deleteFooter     // Catch:{ all -> 0x00c6 }
            r0.setVisibility(r3)     // Catch:{ all -> 0x00c6 }
            android.content.Context r0 = r9.getContext()     // Catch:{ all -> 0x00c6 }
            int r3 = com.autonavi.minimap.R.anim.autonavi_bottom_out     // Catch:{ all -> 0x00c6 }
            android.view.animation.Animation r0 = android.view.animation.AnimationUtils.loadAnimation(r0, r3)     // Catch:{ all -> 0x00c6 }
            r0.setDuration(r1)     // Catch:{ all -> 0x00c6 }
            android.widget.LinearLayout r1 = r9.deleteFooter     // Catch:{ all -> 0x00c6 }
            r1.setAnimation(r0)     // Catch:{ all -> 0x00c6 }
        L_0x00c4:
            monitor-exit(r10)     // Catch:{ all -> 0x00c6 }
            return
        L_0x00c6:
            r0 = move-exception
            monitor-exit(r10)     // Catch:{ all -> 0x00c6 }
            throw r0
        L_0x00c9:
            android.widget.TextView r0 = r9.cancleTV
            if (r10 != r0) goto L_0x00ef
            android.widget.LinearLayout r10 = r9.deleteFooter
            int r10 = r10.getVisibility()
            if (r10 != 0) goto L_0x00f6
            r9.scrollerble = r4
            android.widget.LinearLayout r10 = r9.deleteFooter
            r10.setVisibility(r3)
            android.content.Context r10 = r9.getContext()
            int r0 = com.autonavi.minimap.R.anim.autonavi_bottom_out
            android.view.animation.Animation r10 = android.view.animation.AnimationUtils.loadAnimation(r10, r0)
            r10.setDuration(r1)
            android.widget.LinearLayout r0 = r9.deleteFooter
            r0.setAnimation(r10)
            return
        L_0x00ef:
            android.widget.ImageView r0 = r9.back
            if (r10 != r0) goto L_0x00f6
            r9.dismiss()
        L_0x00f6:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.ajx3.modules.PicViewPagerDialog.onClick(android.view.View):void");
    }
}
