package com.autonavi.minimap.life.order.base.page;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import com.autonavi.map.core.LocationMode.LocationNone;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.map.fragmentcontainer.page.IPresenter;
import com.autonavi.minimap.R;
import com.autonavi.minimap.life.order.base.adapter.ViewPagerAdapter;
import com.autonavi.widget.ui.CommonTab;
import com.autonavi.widget.ui.TitleBar;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class BaseOrderTabPage extends AbstractBasePage<dpu> implements LocationNone {
    protected TitleBar a;
    protected CommonTab b;
    protected ArrayList<Fragment> c = new ArrayList<>();
    protected Boolean d = Boolean.FALSE;
    protected List<View> e = new ArrayList();
    /* access modifiers changed from: private */
    public ViewPager f = null;
    /* access modifiers changed from: private */
    public ViewPagerAdapter g = null;
    private OnClickListener h = new OnClickListener() {
        public final void onClick(View view) {
            BaseOrderTabPage.this.finish();
        }
    };

    class a implements OnPageChangeListener {
        public final void onPageScrollStateChanged(int i) {
        }

        public final void onPageScrolled(int i, float f, int i2) {
        }

        private a() {
        }

        /* synthetic */ a(BaseOrderTabPage baseOrderTabPage, byte b) {
            this();
        }

        public final void onPageSelected(int i) {
            if (i != 1) {
                BaseOrderTabPage.this.b.setSelectTab(0);
                if (((dpu) BaseOrderTabPage.this.mPresenter).a) {
                    BaseOrderTabPage.this.g.a.get(0).onResume();
                    ((dpu) BaseOrderTabPage.this.mPresenter).a = false;
                }
                if (BaseOrderTabPage.this.d.booleanValue()) {
                    BaseOrderTabPage.b(BaseOrderTabPage.this);
                }
            } else {
                BaseOrderTabPage.this.b.setSelectTab(1);
                if (((dpu) BaseOrderTabPage.this.mPresenter).a) {
                    BaseOrderTabPage.this.g.a.get(1).onResume();
                    ((dpu) BaseOrderTabPage.this.mPresenter).a = false;
                }
                if (BaseOrderTabPage.this.d.booleanValue()) {
                    BaseOrderTabPage.h(BaseOrderTabPage.this);
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public abstract void a();

    public void onCreate(Context context) {
        super.onCreate(context);
        setContentView(R.layout.order_base_tab_layout);
        View contentView = getContentView();
        this.a = (TitleBar) contentView.findViewById(R.id.title);
        this.a.setDivideVisibility(8);
        this.a.setOnBackClickListener(this.h);
        this.b = (CommonTab) contentView.findViewById(R.id.tab);
        this.b.addTab(0, getString(R.string.vaild_voucher), true);
        this.b.addTab(1, getString(R.string.invaild_voucher), false);
        this.b.setOnTabSelectedListener(new erq() {
            public final void a(int i) {
                if (i == 0) {
                    BaseOrderTabPage.this.f.setCurrentItem(0, true);
                    if (BaseOrderTabPage.this.d.booleanValue()) {
                        BaseOrderTabPage.b(BaseOrderTabPage.this);
                    }
                } else if (i == 1) {
                    BaseOrderTabPage.this.f.setCurrentItem(1, true);
                }
            }

            public final void b(int i) {
                if (i == 0) {
                    BaseOrderTabPage.this.f.setCurrentItem(0, true);
                    if (BaseOrderTabPage.this.d.booleanValue()) {
                        BaseOrderTabPage.b(BaseOrderTabPage.this);
                    }
                } else if (i == 1) {
                    BaseOrderTabPage.this.f.setCurrentItem(1, true);
                }
            }
        });
        a();
        this.g = new ViewPagerAdapter(getFragmentManager(), this.c);
        this.f = (ViewPager) contentView.findViewById(R.id.viewpager);
        this.f.setAdapter(this.g);
        this.f.setOnPageChangeListener(new a(this, 0));
    }

    public final void b() {
        if (this.c != null && this.c.size() > 0) {
            FragmentTransaction beginTransaction = getFragmentManager().beginTransaction();
            Iterator<Fragment> it = this.c.iterator();
            while (it.hasNext()) {
                Fragment next = it.next();
                beginTransaction.detach(next);
                beginTransaction.remove(next);
            }
            beginTransaction.commit();
        }
    }

    public /* synthetic */ IPresenter createPresenter() {
        return new dpu(this);
    }

    static /* synthetic */ void b(BaseOrderTabPage baseOrderTabPage) {
        try {
            ((InputMethodManager) baseOrderTabPage.getActivity().getSystemService("input_method")).hideSoftInputFromWindow(baseOrderTabPage.getActivity().getCurrentFocus().getWindowToken(), 2);
        } catch (Exception unused) {
        }
    }

    static /* synthetic */ void h(BaseOrderTabPage baseOrderTabPage) {
        try {
            ((InputMethodManager) baseOrderTabPage.getActivity().getSystemService("input_method")).toggleSoftInput(0, 2);
        } catch (Exception unused) {
        }
    }
}
