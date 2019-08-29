package com.autonavi.minimap.bundle.featureguide.widget;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;
import com.autonavi.minimap.bundle.featureguide.bean.LottieData;
import com.autonavi.minimap.bundle.featureguide.widget.SplashyFragment.PAGE_TYPE;
import java.util.ArrayList;
import java.util.List;

public class SplashyFragmentPagerAdapter2 extends FragmentPagerAdapter {
    public List<a> a = new ArrayList();
    public Fragment b;
    public int c = 0;
    private final Context d;

    public static class a {
        public int a = 0;
        public int b = 0;
        public String c = "";
        public String d = "";
        public int e = 0;
        public boolean f = false;
        public PAGE_TYPE g = PAGE_TYPE.DEFAULT;
        public boolean h = false;
        public LottieData i;
    }

    public int getItemPosition(Object obj) {
        return -2;
    }

    public SplashyFragmentPagerAdapter2(Context context, FragmentManager fragmentManager) {
        super(fragmentManager);
        this.d = context;
    }

    public Fragment getItem(int i) {
        this.b = new SplashyFragment();
        Bundle bundle = new Bundle();
        a aVar = this.a.get(i);
        bundle.putInt(SplashyFragment.INTENT_resId, aVar.a);
        bundle.putInt("pageType", aVar.g.value());
        bundle.putString(SplashyFragment.INTENT_bgColor, aVar.d);
        bundle.putInt(SplashyFragment.INTENT_start_btn_bg, aVar.e);
        bundle.putBoolean(SplashyFragment.INTENT_hide_experience, aVar.f);
        bundle.putBoolean(SplashyFragment.INTENT_flagCacheDisplayed, aVar.h);
        bundle.putInt(SplashyFragment.INTENT_VIEW_AREA_PADDING_BOTTOM_HEIGHT, this.c);
        if (aVar.i != null) {
            bundle.putParcelable(SplashyFragment.INTENT_LOTTIE_DATA, aVar.i);
        } else {
            bundle.putInt(SplashyFragment.INTENT_photoId, aVar.b);
            bundle.putString(SplashyFragment.INTENT_photoPath, aVar.c);
        }
        this.b.setArguments(bundle);
        return this.b;
    }

    public int getCount() {
        return this.a.size();
    }

    public void destroyItem(ViewGroup viewGroup, int i, Object obj) {
        super.destroyItem(viewGroup, i, obj);
    }

    public Object instantiateItem(ViewGroup viewGroup, int i) {
        Fragment fragment = (Fragment) super.instantiateItem(viewGroup, i);
        if (fragment instanceof SplashyFragment) {
            ((SplashyFragment) fragment).setStartButtonVisibility(getCount() > 1 ? 4 : 0);
        }
        return fragment;
    }
}
