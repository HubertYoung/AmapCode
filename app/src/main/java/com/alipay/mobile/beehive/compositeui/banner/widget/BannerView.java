package com.alipay.mobile.beehive.compositeui.banner.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import com.alipay.mobile.antui.basic.AUImageView;
import com.alipay.mobile.beehive.compositeui.banner.model.BannerItem;
import com.alipay.mobile.beehive.compositeui.banner.model.BannerItemClickListener;
import com.alipay.mobile.beehive.compositeui.banner.model.BannerLog;
import com.alipay.mobile.beehive.compositeui.banner.model.BannerViewPager;
import com.alipay.mobile.beehive.compositeui.banner.model.CirclePageIndicator;
import com.alipay.mobile.beehive.util.image.ImageWorker;
import java.util.ArrayList;
import java.util.List;

@SuppressLint({"HandlerLeak"})
public class BannerView extends FrameLayout implements OnPageChangeListener {
    private static final int BANNER_GAP_COLOR = -657927;
    private static final int SCALE_FACTOR = 1000;
    private static final String TAG = "BannerView";
    /* access modifiers changed from: private */
    public Handler handler = new Handler() {
        public final void handleMessage(Message msg) {
            super.handleMessage(msg);
            Log.d(BannerView.TAG, "handleMessage, " + BannerView.this.mPager.getCurrentItem());
            if (BannerView.this.mPager.getCurrentItem() == BannerView.this.mPager.getAdapter().getCount() - 2) {
                BannerView.this.mPager.setCurrentItem(BannerView.this.mPager.getAdapter().getCount() / 2);
            } else {
                BannerView.this.mPager.setCurrentItem(BannerView.this.mPager.getCurrentItem() + 1, true);
            }
        }
    };
    /* access modifiers changed from: private */
    public boolean lastPageScrolled = true;
    /* access modifiers changed from: private */
    public CirclePageIndicator mCircleIndicator;
    /* access modifiers changed from: private */
    public BannerItemClickListener mItemClickListener;
    /* access modifiers changed from: private */
    public BannerViewPager mPager;
    /* access modifiers changed from: private */
    public int position;

    public class BannerPagerAdapter extends PagerAdapter {
        /* access modifiers changed from: private */
        public List<BannerItem> item;
        private Context mCtx;

        public BannerPagerAdapter(Context context) {
            this.mCtx = context;
        }

        public void setItems(List<BannerItem> item2) {
            if (this.item == null) {
                this.item = new ArrayList();
            } else {
                this.item.clear();
            }
            this.item.addAll(item2);
            notifyDataSetChanged();
        }

        public BannerItem getItems(int index) {
            return this.item.get(index % getRealCount());
        }

        public void insertItem(int currentIndex, BannerItem newItem) {
            BannerItem currentAdvert = getItems(currentIndex);
            this.item.clear();
            this.item.add(currentAdvert);
            this.item.add(newItem);
            notifyDataSetChanged();
        }

        public int getItemPosition(Object object) {
            return -2;
        }

        public int getCount() {
            if (this.item == null) {
                return 0;
            }
            if (this.item.size() == 1) {
                return 1;
            }
            return this.item.size() * 1000;
        }

        public int getRealCount() {
            if (this.item == null) {
                return 0;
            }
            return this.item.size();
        }

        public boolean isViewFromObject(View view, Object o) {
            return view == o;
        }

        public Object instantiateItem(ViewGroup container, final int position) {
            if (this.item == null) {
                return null;
            }
            int realPos = position % getRealCount();
            AUImageView imageView = new AUImageView(this.mCtx);
            imageView.setScaleType(ScaleType.FIT_XY);
            LayoutParams layoutParams = new LayoutParams(-1, -2);
            int width = BannerView.this.getScreenWidth();
            if (!(this.item == null || this.item.get(realPos) == null || this.item.get(realPos).getImageUrl() == null)) {
                new ImageWorker(this.mCtx).loadImage(this.item.get(realPos).getImageUrl(), (ImageView) imageView, width, width);
            }
            container.addView(imageView, layoutParams);
            BannerLog.d("pos:" + realPos + "item:" + this.item.get(realPos));
            imageView.setOnClickListener(new OnClickListener() {
                public final void onClick(View v) {
                    int pos = position % BannerPagerAdapter.this.getRealCount();
                    BannerLog.d("onClick:pos" + pos + " callback:" + BannerView.this.mItemClickListener);
                    if (pos < BannerPagerAdapter.this.item.size()) {
                        BannerItem bannerItem = (BannerItem) BannerPagerAdapter.this.item.get(pos);
                        if (BannerView.this.mItemClickListener != null) {
                            BannerView.this.mItemClickListener.onClick(BannerView.this, pos, bannerItem);
                        }
                    }
                }
            });
            return imageView;
        }

        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    public BannerView(Context context, List<BannerItem> adverts, BannerItemClickListener itemClickListener) {
        super(context);
        BannerLog.d("BannerView created, callback:" + itemClickListener);
        init(context);
        setBannerItems(adverts);
        this.mItemClickListener = itemClickListener;
    }

    public boolean hasItems() {
        return (this.mPager == null || this.mPager.getAdapter() == null || this.mPager.getAdapter().getCount() <= 0) ? false : true;
    }

    public void setBannerItems(List<BannerItem> adverts) {
        BannerLog.d("BannerView setBannerItems:" + adverts);
        if (adverts != null && adverts.size() != 0) {
            if (this.mPager == null || this.mPager.getAdapter() == null) {
                init(getContext());
            }
            ((BannerPagerAdapter) this.mPager.getAdapter()).setItems(adverts);
            if (adverts.size() == 1) {
                this.mCircleIndicator.setVisibility(4);
            } else {
                this.mCircleIndicator.setVisibility(0);
                this.mPager.setCurrentItem((((BannerPagerAdapter) this.mPager.getAdapter()).getRealCount() * 1000) / 2);
            }
            this.mPager.getAdapter().notifyDataSetChanged();
        }
    }

    public void notifyBannerViewPager() {
        if (this.mPager != null) {
            this.mPager.getAdapter().notifyDataSetChanged();
        }
    }

    public void startLoop(final boolean reset) {
        this.mPager.getAdapter().notifyDataSetChanged();
        post(new Runnable() {
            public final void run() {
                if (BannerView.this.mPager.getAdapter().getCount() == 1) {
                    BannerView.this.mPager.setCurrentItem(0);
                    BannerView.this.mCircleIndicator.setVisibility(4);
                    return;
                }
                BannerView.this.mCircleIndicator.setVisibility(0);
                if (reset) {
                    BannerView.this.mPager.setCurrentItem((((BannerPagerAdapter) BannerView.this.mPager.getAdapter()).getRealCount() * 1000) / 2);
                } else {
                    BannerView.this.lastPageScrolled = true;
                    BannerView.this.handler.removeMessages(0);
                    BannerView.this.handler.sendEmptyMessageDelayed(0, ((BannerPagerAdapter) BannerView.this.mPager.getAdapter()).getItems(BannerView.this.position).getLoopTime());
                }
                Log.d(BannerView.TAG, "startLoop, position:" + BannerView.this.position + ",currentitem:" + BannerView.this.mPager.getCurrentItem());
                Log.d(BannerView.TAG, "adapter count:" + BannerView.this.mPager.getAdapter().getCount());
            }
        });
    }

    public void onPageScrolled(int i, float v, int i2) {
        Log.d(TAG, "onPageScrolled, i:" + i + ",i2:" + i2);
        this.lastPageScrolled = true;
    }

    public void onPageSelected(int i) {
        Log.d(TAG, "onPageSelected:" + i + ",visible:" + getVisibility());
        this.position = i;
        if (this.lastPageScrolled) {
            this.lastPageScrolled = false;
            this.handler.removeMessages(0);
            this.handler.sendEmptyMessageDelayed(0, ((BannerPagerAdapter) this.mPager.getAdapter()).getItems(i).getLoopTime());
        }
    }

    public void onPageScrollStateChanged(int i) {
    }

    private void init(Context context) {
        removeAllViews();
        setBackgroundColor(-657927);
        this.mPager = new BannerViewPager(context);
        this.mPager.setOnPageChangeListener(this);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-1, -2);
        layoutParams.gravity = 16;
        this.mPager.setAdapter(new BannerPagerAdapter(context));
        addView(this.mPager, layoutParams);
        this.mCircleIndicator = new CirclePageIndicator(getContext());
        this.mCircleIndicator.setViewPager(this.mPager);
        this.mCircleIndicator.setOnPageChangeListener(this);
        FrameLayout.LayoutParams layoutParams2 = new FrameLayout.LayoutParams(-1, -2);
        layoutParams2.gravity = 81;
        this.mCircleIndicator.setPadding(10, 10, 10, 10);
        addView(this.mCircleIndicator, layoutParams2);
    }

    public int getScreenWidth() {
        return getResources().getDisplayMetrics().widthPixels;
    }
}
