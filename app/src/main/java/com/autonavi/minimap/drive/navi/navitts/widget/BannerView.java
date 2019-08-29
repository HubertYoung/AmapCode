package com.autonavi.minimap.drive.navi.navitts.widget;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Point;
import android.net.Uri;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import com.alipay.mobile.tinyappcommon.template.TemplateTinyApp;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.bundle.banner.data.BannerItem;
import com.autonavi.minimap.R;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class BannerView extends LinearLayout {
    static Bitmap defaultBackGround;
    private a adapter;
    /* access modifiers changed from: private */
    public LinkedList<BannerItem> bannerItems;
    private ViewPager banner_vwp;
    private final ReentrantLock clickLock = new ReentrantLock();
    /* access modifiers changed from: private */
    public List<View> imageViews = new ArrayList();
    private volatile long latestClick = 0;
    private LinearLayout layout_point;
    private Context mContext;
    private b myPageChangeListener;
    /* access modifiers changed from: private */
    public List<ImageView> points;
    private List<String> titles = new ArrayList();

    static class a extends PagerAdapter {
        private List<View> a;

        public final void finishUpdate(View view) {
        }

        public final boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }

        public final void restoreState(Parcelable parcelable, ClassLoader classLoader) {
        }

        public final Parcelable saveState() {
            return null;
        }

        public final void startUpdate(View view) {
        }

        public a(List<View> list) {
            this.a = list;
        }

        public final int getCount() {
            return this.a.size();
        }

        public final Object instantiateItem(View view, int i) {
            int size = i % this.a.size();
            if (view instanceof ViewPager) {
                ((ViewPager) view).addView(this.a.get(size));
            }
            return this.a.get(size);
        }

        public final void destroyItem(View view, int i, Object obj) {
            if (view instanceof ViewPager) {
                ((ViewPager) view).removeView((View) obj);
            }
        }
    }

    class b implements OnPageChangeListener {
        private int b;

        public final void onPageScrollStateChanged(int i) {
        }

        public final void onPageScrolled(int i, float f, int i2) {
        }

        private b() {
            this.b = 0;
        }

        /* synthetic */ b(BannerView bannerView, byte b2) {
            this();
        }

        public final void onPageSelected(int i) {
            int size = i % BannerView.this.imageViews.size();
            ((ImageView) BannerView.this.points.get(this.b)).setBackgroundResource(R.drawable.dot_normal);
            ((ImageView) BannerView.this.points.get(size)).setBackgroundResource(R.drawable.dot_focused);
            this.b = size;
        }
    }

    public BannerView(Context context, LinkedList<BannerItem> linkedList) {
        super(context);
        this.mContext = context;
        this.imageViews.clear();
        this.titles.clear();
        createBackground();
        this.bannerItems = linkedList;
        initView();
    }

    public BannerView(Context context) {
        super(context);
    }

    private void createBackground() {
        Options options = new Options();
        options.inPreferredConfig = Config.ARGB_8888;
        options.inJustDecodeBounds = false;
        defaultBackGround = BitmapFactory.decodeResource(this.mContext.getResources(), R.drawable.feiji, options);
    }

    public void initView() {
        for (int i = 0; i < this.bannerItems.size(); i++) {
            this.titles.add(this.bannerItems.get(i).bannerTitle);
        }
        for (final int i2 = 0; i2 < this.bannerItems.size(); i2++) {
            View inflate = LayoutInflater.from(getContext()).inflate(R.layout.fragment_offline_navitts_banner_item, null);
            RoundCornerImageView roundCornerImageView = (RoundCornerImageView) inflate.findViewById(R.id.banner_img);
            if (defaultBackGround != null) {
                roundCornerImageView.setImageBitmap(defaultBackGround);
            }
            ko.a(roundCornerImageView, this.bannerItems.get(i2).imageURL, null, R.drawable.feiji);
            if (!TextUtils.isEmpty(this.bannerItems.get(i2).action)) {
                roundCornerImageView.setOnClickListener(new OnClickListener() {
                    public final void onClick(View view) {
                        BannerView.this.prepareIntent(((BannerItem) BannerView.this.bannerItems.get(i2)).action);
                    }
                });
            }
            this.imageViews.add(inflate);
        }
        Point point = new Point();
        ((WindowManager) getContext().getSystemService(TemplateTinyApp.WINDOW_KEY)).getDefaultDisplay().getSize(point);
        int i3 = point.x;
        LayoutParams layoutParams = new LayoutParams(i3, (((i3 - agn.a(getContext(), 40.0f)) * 9) / 16) + agn.a(getContext(), 20.0f) + agn.a(getContext(), 24.0f));
        View inflate2 = LayoutInflater.from(getContext()).inflate(R.layout.f8xx_view_pager_banner, null);
        inflate2.setLayoutParams(layoutParams);
        addView(inflate2);
        this.layout_point = (LinearLayout) findViewById(R.id.layout_point);
        this.layout_point.removeAllViews();
        this.points = new ArrayList();
        for (int i4 = 0; i4 < this.imageViews.size(); i4++) {
            ImageView imageView = (ImageView) LayoutInflater.from(getContext()).inflate(R.layout.point, null);
            imageView.setBackgroundResource(R.drawable.dot_normal);
            LayoutParams layoutParams2 = new LayoutParams(agn.a(getContext(), 8.0f), agn.a(getContext(), 8.0f));
            layoutParams2.setMargins(agn.a(getContext(), 4.5f), 0, agn.a(getContext(), 4.5f), 0);
            imageView.setLayoutParams(layoutParams2);
            this.points.add(imageView);
            this.layout_point.addView(imageView);
        }
        if (this.points.size() <= 1) {
            this.layout_point.setVisibility(8);
        }
        this.points.get(0).setBackgroundResource(R.drawable.dot_focused);
        this.banner_vwp = (ViewPager) findViewById(R.id.banner_vwp);
        this.banner_vwp.removeAllViews();
        this.adapter = new a(this.imageViews);
        this.banner_vwp.setAdapter(this.adapter);
        this.myPageChangeListener = new b(this, 0);
        this.banner_vwp.setOnPageChangeListener(this.myPageChangeListener);
    }

    /* access modifiers changed from: private */
    public void prepareIntent(String str) {
        if (!TextUtils.isEmpty(str)) {
            long currentTimeMillis = System.currentTimeMillis();
            this.clickLock.lock();
            try {
                if (currentTimeMillis - this.latestClick > 500) {
                    this.latestClick = currentTimeMillis;
                    Intent intent = new Intent();
                    intent.setData(Uri.parse(str));
                    intent.putExtra("owner", "banner");
                    Activity topActivity = AMapAppGlobal.getTopActivity();
                    if (topActivity != null) {
                        topActivity.startActivity(intent);
                    }
                }
            } finally {
                this.clickLock.unlock();
            }
        }
    }
}
