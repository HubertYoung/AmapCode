package com.autonavi.bundle.banner.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.statistics.LogManager;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.bundle.banner.data.BannerItem;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.minimap.R;
import java.lang.ref.WeakReference;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;
import org.json.JSONException;
import org.json.JSONObject;
import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@Keep
@KeepClassMembers
public class DBanner extends RelativeLayout {
    private static final int AUTO_SCROLL_NEXTPAGE = 1001;
    public static final String INTENT_CALL_OWNER_BANNER = "banner";
    public static final String TAG = "DBanner";
    /* access modifiers changed from: private */
    public d bannerAdapter;
    private final ReentrantLock clickLock;
    private volatile long latestClick;
    /* access modifiers changed from: private */
    public a mAutoScrollHandler;
    private BannerActionListener mBannerActionListener;
    /* access modifiers changed from: private */
    public b mBannerCallback;
    /* access modifiers changed from: private */
    public Integer mBgColor;
    /* access modifiers changed from: private */
    public int mCurrentDisplayItem;
    /* access modifiers changed from: private */
    public ScaleType mImageScaleType;
    /* access modifiers changed from: private */
    public LayoutInflater mInflater;
    private String mLogButtonId;
    private int mLogClick;
    private int mLogPage;
    private String mLogPageId;
    private e mOnItemClickListener;
    private f mOnItemDisplayListener;
    /* access modifiers changed from: private */
    public PageNumber pageNumber;
    /* access modifiers changed from: private */
    public ViewPager viewPager;
    /* access modifiers changed from: private */
    public c vll;

    @KeepClassMembers
    @Keep
    public static class BannerActionListener {
        public void onBannerItemClick(String str) {
        }
    }

    @KeepClassMembers
    @Keep
    public interface BannerListener {
        void onFinish(boolean z);
    }

    static class a extends Handler {
        AtomicBoolean a;
        AtomicBoolean b;
        private WeakReference<DBanner> c;

        /* synthetic */ a(DBanner dBanner, byte b2) {
            this(dBanner);
        }

        private a(DBanner dBanner) {
            this.a = new AtomicBoolean(false);
            this.b = new AtomicBoolean(true);
            this.c = new WeakReference<>(dBanner);
        }

        public final void handleMessage(Message message) {
            super.handleMessage(message);
            if (message.what == 1001 && this.b.get() && this.c != null && this.c.get() != null) {
                if (!this.a.getAndSet(false) && this.b.get()) {
                    ((DBanner) this.c.get()).nextPage();
                }
                removeMessages(1001);
                sendMessageDelayed(obtainMessage(1001, message.obj), Long.parseLong(String.valueOf(message.obj)));
            }
        }
    }

    public interface b {
    }

    class c implements OnClickListener {
        private c() {
        }

        /* synthetic */ c(DBanner dBanner, byte b) {
            this();
        }

        public final void onClick(View view) {
            int currentItem = DBanner.this.viewPager.getCurrentItem();
            try {
                BannerItem bannerItem = DBanner.this.bannerAdapter.a.get(currentItem % DBanner.this.bannerAdapter.b);
                if (bannerItem != null && DBanner.this.bannerAdapter.b > 0) {
                    DBanner.this.prepareIntent(currentItem % DBanner.this.bannerAdapter.b, bannerItem);
                    DBanner.this.addLog(bannerItem);
                    DBanner.this.addLogV2(bannerItem);
                }
            } catch (Exception e) {
                AMapLog.e("banner", String.valueOf(e));
            }
        }
    }

    class d extends PagerAdapter {
        LinkedList<BannerItem> a;
        public int b = this.a.size();

        public final boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }

        public d(LinkedList<BannerItem> linkedList) {
            this.a = linkedList;
        }

        public final int getCount() {
            if (this.b < 2) {
                return this.b;
            }
            return Integer.MAX_VALUE;
        }

        public final Object instantiateItem(ViewGroup viewGroup, int i) {
            int i2 = i % this.b;
            if (this.a == null) {
                return null;
            }
            BannerItem bannerItem = this.a.get(i2);
            if (bannerItem == null) {
                return null;
            }
            RelativeLayout relativeLayout = (RelativeLayout) DBanner.this.mInflater.inflate(R.layout.banner_page_content, null);
            ImageView imageView = (ImageView) relativeLayout.findViewById(R.id.imageView1);
            if (DBanner.this.mBgColor != null) {
                relativeLayout.setBackgroundColor(DBanner.this.mBgColor.intValue());
            }
            if (DBanner.this.mImageScaleType != null) {
                imageView.setScaleType(DBanner.this.mImageScaleType);
            }
            TextView textView = (TextView) relativeLayout.findViewById(R.id.textview1);
            if (bannerItem.type == 2) {
                imageView.setVisibility(0);
                textView.setVisibility(8);
                textView.setOnClickListener(null);
                imageView.setOnClickListener(DBanner.this.vll);
                String str = bannerItem.imageURL;
                if (!TextUtils.isEmpty(str)) {
                    ko.a(imageView, str);
                }
            } else {
                imageView.setVisibility(8);
                textView.setVisibility(8);
                textView.setOnClickListener(null);
                imageView.setOnClickListener(null);
            }
            viewGroup.addView(relativeLayout);
            return relativeLayout;
        }

        public final void destroyItem(ViewGroup viewGroup, int i, Object obj) {
            viewGroup.removeView((View) obj);
        }
    }

    public interface e {
    }

    public interface f {
    }

    public DBanner(Context context) {
        super(AMapAppGlobal.getApplication());
        this.mAutoScrollHandler = new a(this, 0);
        this.vll = new c(this, 0);
        this.mImageScaleType = null;
        this.mCurrentDisplayItem = -1;
        this.latestClick = 0;
        this.clickLock = new ReentrantLock();
    }

    public DBanner(Context context, AttributeSet attributeSet) {
        super(AMapAppGlobal.getApplication(), attributeSet);
        this.mAutoScrollHandler = new a(this, 0);
        this.vll = new c(this, 0);
        this.mImageScaleType = null;
        this.mCurrentDisplayItem = -1;
        this.latestClick = 0;
        this.clickLock = new ReentrantLock();
        this.mInflater = LayoutInflater.from(getContext());
        this.mInflater.inflate(R.layout.banner_layout, this, true);
        this.viewPager = (ViewPager) findViewById(R.id.viewpager);
        this.pageNumber = (PageNumber) findViewById(R.id.pagenumber1);
        this.pageNumber.setColorRes(R.color.f_c_3, R.color.white);
        this.bannerAdapter = new d(new LinkedList());
        this.viewPager.setAdapter(this.bannerAdapter);
        this.viewPager.setOnPageChangeListener(new OnPageChangeListener() {
            boolean a = false;
            boolean b = false;
            private int d = -1;

            public final void onPageScrollStateChanged(int i) {
                boolean z = true;
                if (i != 1) {
                    z = false;
                }
                this.a = z;
                if (i == 2) {
                    this.b = false;
                }
            }

            public final void onPageScrolled(int i, float f, int i2) {
                if (this.a) {
                    this.b = this.d > i2;
                    if (this.b && f < 0.5f) {
                        int i3 = DBanner.this.bannerAdapter.b;
                        if (i3 > 0) {
                            DBanner.this.pageNumber.setCurrent(i % i3);
                        }
                    } else if (!this.b && f > 0.5f) {
                        int i4 = DBanner.this.bannerAdapter.b;
                        if (i4 > 0) {
                            DBanner.this.pageNumber.setCurrent((i % i4) + 1);
                        }
                    }
                }
                this.d = i2;
            }

            public final void onPageSelected(int i) {
                int i2 = DBanner.this.bannerAdapter.b;
                if (i2 > 0) {
                    int i3 = i % i2;
                    DBanner.this.pageNumber.setCurrent(i3);
                    DBanner.this.dispatchItemDisplay(i3);
                }
            }
        });
        this.viewPager.setOnTouchListener(new OnTouchListener() {
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                if (DBanner.this.mAutoScrollHandler != null) {
                    DBanner.this.mAutoScrollHandler.a.set(true);
                }
                return false;
            }
        });
        this.viewPager.setOnClickListener(new c(this, 0));
    }

    public DBanner(Context context, AttributeSet attributeSet, int i) {
        super(AMapAppGlobal.getApplication(), attributeSet, i);
        this.mAutoScrollHandler = new a(this, 0);
        this.vll = new c(this, 0);
        this.mImageScaleType = null;
        this.mCurrentDisplayItem = -1;
        this.latestClick = 0;
        this.clickLock = new ReentrantLock();
    }

    @TargetApi(21)
    public DBanner(Context context, AttributeSet attributeSet, int i, int i2) {
        super(AMapAppGlobal.getApplication(), attributeSet, i, i2);
        this.mAutoScrollHandler = new a(this, 0);
        this.vll = new c(this, 0);
        this.mImageScaleType = null;
        this.mCurrentDisplayItem = -1;
        this.latestClick = 0;
        this.clickLock = new ReentrantLock();
    }

    public void setImageScaleType(ScaleType scaleType) {
        this.mImageScaleType = scaleType;
    }

    public void setBgColor(int i) {
        this.mBgColor = Integer.valueOf(i);
    }

    /* access modifiers changed from: private */
    public void nextPage() {
        final int currentItem = this.viewPager.getCurrentItem() + 1;
        post(new Runnable() {
            public final void run() {
                DBanner.this.viewPager.setCurrentItem(currentItem, true);
            }
        });
    }

    /* access modifiers changed from: private */
    public void startScroll(long j) {
        this.mAutoScrollHandler.removeMessages(1001);
        this.mAutoScrollHandler.sendMessageDelayed(this.mAutoScrollHandler.obtainMessage(1001, String.valueOf(j)), j);
    }

    public void resetData(final LinkedList<BannerItem> linkedList, final long j) {
        post(new Runnable() {
            public final void run() {
                DBanner.this.mCurrentDisplayItem = -1;
                int size = linkedList.size();
                DBanner.this.bannerAdapter = new d(linkedList);
                DBanner.this.viewPager.setAdapter(DBanner.this.bannerAdapter);
                if (size > 1) {
                    DBanner.this.viewPager.setCurrentItem(size * 1000);
                    DBanner.this.pageNumber.init(size);
                    DBanner.this.startScroll(j);
                }
                DBanner.this.bannerAdapter.notifyDataSetChanged();
                DBanner.this.viewPager.invalidate();
                DBanner.this.dispatchItemDisplay(0);
            }
        });
    }

    public void init(boolean z, final BannerListener bannerListener) {
        ash.a("1", z, false, new defpackage.ash.a() {
            public final void a(LinkedList<BannerItem> linkedList, long j) {
                if (linkedList == null || linkedList.size() <= 0) {
                    bannerListener.onFinish(false);
                    return;
                }
                DBanner.this.resetData(linkedList, j);
                bannerListener.onFinish(true);
            }
        });
    }

    public void initOrderHotelBanner(boolean z, final BannerListener bannerListener) {
        ash.a("14", z, false, new defpackage.ash.a() {
            public final void a(LinkedList<BannerItem> linkedList, long j) {
                if (linkedList == null || linkedList.size() <= 0) {
                    bannerListener.onFinish(false);
                    return;
                }
                DBanner.this.resetData(linkedList, j);
                bannerListener.onFinish(true);
            }
        });
    }

    public void initNearByBanner(boolean z, final BannerListener bannerListener) {
        ash.a("17", z, false, new defpackage.ash.a() {
            public final void a(LinkedList<BannerItem> linkedList, long j) {
                if (linkedList == null || linkedList.size() <= 0) {
                    bannerListener.onFinish(false);
                    return;
                }
                if (DBanner.this.mBannerCallback != null) {
                    DBanner.this.mBannerCallback;
                }
                DBanner.this.resetData(linkedList, j);
                bannerListener.onFinish(true);
            }
        });
    }

    public void setOnItemClickListener(e eVar) {
        this.mOnItemClickListener = eVar;
    }

    public void setOnItemDisplayListener(f fVar) {
        this.mOnItemDisplayListener = fVar;
    }

    private void dispatchItemClick(int i) {
        if (this.mOnItemClickListener != null && this.bannerAdapter != null && this.bannerAdapter.a != null && this.bannerAdapter.a.size() > i) {
            this.bannerAdapter.a.get(i);
        }
    }

    /* access modifiers changed from: private */
    public void dispatchItemDisplay(int i) {
        if (this.mOnItemDisplayListener != null && this.mCurrentDisplayItem != i && this.bannerAdapter != null && this.bannerAdapter.a != null && this.bannerAdapter.a.size() > i) {
            this.mCurrentDisplayItem = i;
            this.bannerAdapter.a.get(i);
        }
    }

    public void setBannerDataCallback(b bVar) {
        this.mBannerCallback = bVar;
    }

    public void initBusGreenBanner(boolean z, final BannerListener bannerListener) {
        ash.a("21", z, false, new defpackage.ash.a() {
            public final void a(LinkedList<BannerItem> linkedList, long j) {
                if (linkedList == null || linkedList.size() <= 0) {
                    bannerListener.onFinish(false);
                    return;
                }
                DBanner.this.resetData(linkedList, j);
                bannerListener.onFinish(true);
            }
        });
    }

    public void initQuickAutonaviBanner(boolean z, final BannerListener bannerListener) {
        ash.a("22", z, false, new defpackage.ash.a() {
            public final void a(LinkedList<BannerItem> linkedList, long j) {
                if (linkedList == null || linkedList.size() <= 0) {
                    bannerListener.onFinish(false);
                    return;
                }
                DBanner.this.resetData(linkedList, j);
                bannerListener.onFinish(true);
            }
        });
    }

    public void initRdCameraErrorReportBanner(boolean z, final BannerListener bannerListener) {
        ash.a("24", z, false, new defpackage.ash.a() {
            public final void a(LinkedList<BannerItem> linkedList, long j) {
                if (linkedList == null || linkedList.size() <= 0) {
                    bannerListener.onFinish(false);
                    return;
                }
                DBanner.this.resetData(linkedList, j);
                bannerListener.onFinish(true);
            }
        });
    }

    public void initAutoNaviEndBanner(boolean z, final BannerListener bannerListener) {
        ash.a("9", z, !z, new defpackage.ash.a() {
            public final void a(LinkedList<BannerItem> linkedList, long j) {
                if (linkedList == null || linkedList.size() <= 0) {
                    bannerListener.onFinish(false);
                    return;
                }
                DBanner.this.resetData(linkedList, j);
                bannerListener.onFinish(true);
            }
        });
    }

    public void initMovieBanner(boolean z, final BannerListener bannerListener) {
        ash.a("2", z, false, new defpackage.ash.a() {
            public final void a(LinkedList<BannerItem> linkedList, long j) {
                if (linkedList == null || linkedList.size() <= 0) {
                    bannerListener.onFinish(false);
                    return;
                }
                DBanner.this.resetData(linkedList, j);
                bannerListener.onFinish(true);
            }
        });
    }

    public void initGroupBuyHomepageBanner(boolean z, final BannerListener bannerListener) {
        ash.a("3", z, false, new defpackage.ash.a() {
            public final void a(LinkedList<BannerItem> linkedList, long j) {
                if (linkedList == null || linkedList.size() <= 0) {
                    bannerListener.onFinish(false);
                    return;
                }
                DBanner.this.resetData(linkedList, j);
                bannerListener.onFinish(true);
            }
        });
    }

    public void initTravelChannelBanner(boolean z, final BannerListener bannerListener) {
        ash.a("12", z, false, new defpackage.ash.a() {
            public final void a(LinkedList<BannerItem> linkedList, long j) {
                if (linkedList == null || linkedList.size() <= 0) {
                    bannerListener.onFinish(false);
                    return;
                }
                DBanner.this.resetData(linkedList, j);
                bannerListener.onFinish(true);
            }
        });
    }

    public void initTrafficReportBanner(boolean z, final BannerListener bannerListener) {
        ash.a("29", z, false, new defpackage.ash.a() {
            public final void a(LinkedList<BannerItem> linkedList, long j) {
                if (linkedList == null || linkedList.size() <= 0) {
                    bannerListener.onFinish(false);
                    return;
                }
                DBanner.this.resetData(linkedList, j);
                bannerListener.onFinish(true);
            }
        });
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        if (this.mAutoScrollHandler != null) {
            this.mAutoScrollHandler.b.set(false);
        }
        super.onDetachedFromWindow();
    }

    public void setVisibility(int i) {
        super.setVisibility(i);
        if (i == 0) {
            refreshView();
        }
    }

    private void refreshView() {
        if (this.viewPager.getVisibility() == 0 && this.bannerAdapter != null && this.bannerAdapter.b > 1) {
            int currentItem = this.viewPager.getCurrentItem() + this.bannerAdapter.b;
            if (currentItem < 0) {
                currentItem = 0;
            }
            this.viewPager.setCurrentItem(currentItem + this.bannerAdapter.b, false);
        }
    }

    /* access modifiers changed from: private */
    public void prepareIntent(int i, BannerItem bannerItem) {
        if (bannerItem != null && !TextUtils.isEmpty(bannerItem.action)) {
            long currentTimeMillis = System.currentTimeMillis();
            this.clickLock.lock();
            try {
                if (currentTimeMillis - this.latestClick > 500) {
                    this.latestClick = currentTimeMillis;
                    onBannerItemClick(i, bannerItem);
                }
            } finally {
                this.clickLock.unlock();
            }
        }
    }

    private void onBannerItemClick(int i, BannerItem bannerItem) {
        dispatchItemClick(i);
        if (this.mBannerActionListener != null) {
            this.mBannerActionListener.onBannerItemClick(bannerItem.action);
        }
        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(bannerItem.action));
        intent.putExtra("owner", "banner");
        DoNotUseTool.startScheme(intent);
    }

    public void initFoodHomeBanner(boolean z, final BannerListener bannerListener) {
        ash.a("15", z, false, new defpackage.ash.a() {
            public final void a(LinkedList<BannerItem> linkedList, long j) {
                if (linkedList == null || linkedList.size() <= 0) {
                    bannerListener.onFinish(false);
                    return;
                }
                DBanner.this.resetData(linkedList, j);
                bannerListener.onFinish(true);
            }
        });
    }

    public void setBannerActionListener(BannerActionListener bannerActionListener) {
        this.mBannerActionListener = bannerActionListener;
    }

    public void setLogPage(int i, int i2) {
        this.mLogPage = i;
        this.mLogClick = i2;
    }

    public void setLogPage(String str, String str2) {
        this.mLogPageId = str;
        this.mLogButtonId = str2;
    }

    /* access modifiers changed from: private */
    public void addLogV2(BannerItem bannerItem) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("ItemName", bannerItem.bannerTitle);
        } catch (JSONException e2) {
            AMapLog.e("banner", String.valueOf(e2));
        }
        LogManager.actionLogV2(this.mLogPageId, this.mLogButtonId, jSONObject);
    }

    /* access modifiers changed from: private */
    public void addLog(BannerItem bannerItem) {
        if (this.mLogPage != 0 && this.mLogClick != 0) {
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("ItemName", bannerItem.bannerTitle);
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
            LogManager.actionLog(this.mLogPage, this.mLogClick, jSONObject);
        }
    }
}
