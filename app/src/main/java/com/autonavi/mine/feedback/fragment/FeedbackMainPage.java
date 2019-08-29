package com.autonavi.mine.feedback.fragment;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager.SimpleOnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import com.amap.bundle.blutils.app.ConfigerHelper;
import com.amap.bundle.statistics.LogManager;
import com.autonavi.annotation.PageAction;
import com.autonavi.bundle.feedback.widget.NonSwipeableViewPager;
import com.autonavi.carowner.payfor.ApplyPayForListFragment;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.LaunchMode.launchModeSingleTask;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePresenter;
import com.autonavi.map.fragmentcontainer.page.IPresenter;
import com.autonavi.minimap.R;
import com.autonavi.sdk.log.util.LogConstant;
import com.autonavi.widget.ui.CommonTab;
import com.autonavi.widget.ui.TitleBar;

@PageAction("amap.basemap.action.help_and_feedback_page")
public class FeedbackMainPage extends AbstractBasePage<a> implements OnClickListener, launchModeSingleTask {
    NonSwipeableViewPager a;
    private TitleBar b;
    /* access modifiers changed from: private */
    public CommonTab c;
    private c d;
    private OnClickListener e = new OnClickListener() {
        public final void onClick(View view) {
            FeedbackMainPage.this.finish();
        }
    };

    public static class a extends AbstractBasePresenter<FeedbackMainPage> {
        public a(FeedbackMainPage feedbackMainPage) {
            super(feedbackMainPage);
        }

        public final void onNewIntent(PageBundle pageBundle) {
            super.onNewIntent(pageBundle);
            if (pageBundle != null) {
                ((FeedbackMainPage) this.mPage).a(pageBundle.getInt(ApplyPayForListFragment.KEY_PAGE_TYPE));
            }
        }

        public final void onDestroy() {
            super.onDestroy();
            FeedbackMainPage feedbackMainPage = (FeedbackMainPage) this.mPage;
            if (feedbackMainPage.a != null && (feedbackMainPage.a.getAdapter() instanceof FragmentPagerAdapter)) {
                FragmentPagerAdapter fragmentPagerAdapter = (FragmentPagerAdapter) feedbackMainPage.a.getAdapter();
                FragmentTransaction beginTransaction = feedbackMainPage.getFragmentManager().beginTransaction();
                for (int count = fragmentPagerAdapter.getCount() - 1; count >= 0; count--) {
                    Fragment item = fragmentPagerAdapter.getItem(count);
                    beginTransaction.detach(item);
                    beginTransaction.remove(item);
                }
                try {
                    beginTransaction.commit();
                } catch (Exception e) {
                    if (bno.a) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    class b extends SimpleOnPageChangeListener {
        private b() {
        }

        /* synthetic */ b(FeedbackMainPage feedbackMainPage, byte b) {
            this();
        }

        public final void onPageSelected(int i) {
            switch (i) {
                case 0:
                    FeedbackMainPage.this.c.setSelectTab(0);
                    return;
                case 1:
                    FeedbackMainPage.this.c.setSelectTab(1);
                    break;
            }
        }
    }

    static class c extends FragmentPagerAdapter {
        Fragment a = null;
        WebviewFragment b = null;

        public final int getCount() {
            return 1;
        }

        public c(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        public final Fragment getItem(int i) {
            if (i == 0) {
                if (this.a == null) {
                    this.a = WebviewFragment.create(ConfigerHelper.getInstance().getFeedbackHelpcenterUrl(), false);
                }
                return this.a;
            } else if (i != 1) {
                return null;
            } else {
                if (this.b == null) {
                    this.b = WebviewFragment.create(ConfigerHelper.getInstance().getFeedbackListUrl(), false);
                }
                return this.b;
            }
        }

        public final void a() {
            if (this.b != null) {
                this.b.reload();
            }
        }
    }

    public void onClick(View view) {
    }

    public void onCreate(Context context) {
        super.onCreate(context);
        setContentView(R.layout.feedback_main_fragment);
        requestScreenOrientation(1);
        View contentView = getContentView();
        this.b = (TitleBar) contentView.findViewById(R.id.title);
        this.b.setTitle(getString(R.string.feedback_main_fragment_title));
        this.b.setDivideVisibility(8);
        this.b.setOnBackClickListener(this.e);
        this.c = (CommonTab) contentView.findViewById(R.id.tab);
        this.c.setVisibility(8);
        this.c.addTab(0, getString(R.string.feedback_main_fragment_tab_help_center), true);
        this.c.addTab(1, getString(R.string.feedback_main_fragment_tab_my_feedback), false);
        this.c.setOnTabSelectedListener(new erq() {
            public final void a(int i) {
                if (i == 0) {
                    FeedbackMainPage.a(FeedbackMainPage.this, 0);
                    LogManager.actionLogV2(LogConstant.PAGE_USER_FEEDBACK, "B001");
                    return;
                }
                if (i == 1) {
                    FeedbackMainPage.a(FeedbackMainPage.this, 1);
                }
            }

            public final void b(int i) {
                if (i == 0) {
                    FeedbackMainPage.a(FeedbackMainPage.this, 0);
                    LogManager.actionLogV2(LogConstant.PAGE_USER_FEEDBACK, "B001");
                    return;
                }
                if (i == 1) {
                    FeedbackMainPage.a(FeedbackMainPage.this, 1);
                }
            }
        });
        this.a = (NonSwipeableViewPager) contentView.findViewById(R.id.feedback_main_fragment_pager);
        this.a.setOnPageChangeListener(new b(this, 0));
        this.d = new c(getFragmentManager());
        this.a.setAdapter(this.d);
        PageBundle arguments = getArguments();
        if (arguments != null) {
            a(arguments.getInt(ApplyPayForListFragment.KEY_PAGE_TYPE));
        } else {
            a(0);
        }
    }

    /* access modifiers changed from: private */
    public void a(int i) {
        if (i == 1) {
            this.a.setCurrentItem(1);
            this.d.a();
            return;
        }
        this.a.setCurrentItem(0);
    }

    public /* synthetic */ IPresenter createPresenter() {
        return new a(this);
    }

    static /* synthetic */ void a(FeedbackMainPage feedbackMainPage, int i) {
        try {
            feedbackMainPage.a.setCurrentItem(i);
        } catch (Exception unused) {
        }
    }
}
