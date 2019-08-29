package com.alipay.mobile.beehive.capture.views;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import com.alipay.android.phone.mobilecommon.multimedia.api.MultimediaImageService;
import com.alipay.android.phone.mobilecommon.multimedia.api.MultimediaMaterialService;
import com.alipay.android.phone.mobilecommon.multimedia.material.APFilterInfo;
import com.alipay.mobile.antui.basic.AUHorizontalListView;
import com.alipay.mobile.beehive.R;
import com.alipay.mobile.beehive.capture.constant.Constants;
import com.alipay.mobile.beehive.capture.modle.Filter;
import com.alipay.mobile.beehive.capture.utils.Logger;
import com.alipay.mobile.beehive.capture.utils.ServiceFactory;
import java.util.LinkedList;
import java.util.List;

public class FilterSelectView extends FrameLayout {
    private static final String TAG = "FilterSelectView";
    /* access modifiers changed from: private */
    public Drawable defaultDrawable;
    private AUHorizontalListView filterList;
    private ImageView goneBtn;
    private a mAdapter;
    /* access modifiers changed from: private */
    public Filter mCurrentSelected;
    /* access modifiers changed from: private */
    public List<Filter> mFilters;
    /* access modifiers changed from: private */
    public MultimediaImageService mImageService;
    private MultimediaMaterialService mMeterialService;
    /* access modifiers changed from: private */
    public FilterSelectListener mSelectListener;
    Animation popDown;
    Animation popUp;

    public interface FilterSelectListener {
        void onFilterSelectd(Filter filter);

        void onPanelGone();
    }

    class a extends BaseAdapter {
        private OnClickListener b = new OnClickListener() {
            public final void onClick(View v) {
                b holder = (b) v.getTag();
                if (holder.c != FilterSelectView.this.mCurrentSelected) {
                    if (FilterSelectView.this.mCurrentSelected != null) {
                        FilterSelectView.this.mCurrentSelected.isSelected = false;
                    }
                    FilterSelectView.this.mCurrentSelected = holder.c;
                    FilterSelectView.this.mCurrentSelected.isSelected = true;
                    if (FilterSelectView.this.mSelectListener != null) {
                        FilterSelectView.this.mSelectListener.onFilterSelectd(FilterSelectView.this.mCurrentSelected);
                    }
                    a.this.notifyDataSetChanged();
                }
            }
        };

        a() {
        }

        public final int getCount() {
            if (FilterSelectView.this.mFilters == null) {
                return 0;
            }
            return FilterSelectView.this.mFilters.size();
        }

        /* access modifiers changed from: private */
        /* renamed from: a */
        public Filter getItem(int position) {
            return (Filter) FilterSelectView.this.mFilters.get(position);
        }

        public final long getItemId(int position) {
            return (long) position;
        }

        public final View getView(int position, View convertView, ViewGroup parent) {
            View ret = convertView;
            if (convertView == null) {
                ret = LayoutInflater.from(FilterSelectView.this.getContext()).inflate(R.layout.view_filter_item, null);
                b h = new b();
                h.a = (ImageView) ret.findViewById(R.id.ivFilter);
                h.b = (TextView) ret.findViewById(R.id.tvFilterName);
                ret.setTag(h);
            }
            b holder = (b) ret.getTag();
            Filter filter = getItem(position);
            holder.c = filter;
            FilterSelectView.this.mImageService.loadImage(filter.filterIcon, holder.a, FilterSelectView.this.defaultDrawable, (String) Constants.CAPTURE_BUSINESS_ID);
            if (filter.isSelected) {
                holder.a.setBackgroundDrawable(FilterSelectView.this.getResources().getDrawable(R.drawable.bg_effect));
                holder.b.setTextColor(FilterSelectView.this.getResources().getColor(R.color.filter_selected_color));
            } else {
                holder.a.setBackgroundDrawable(null);
                holder.b.setTextColor(FilterSelectView.this.getResources().getColor(R.color.filter_unselected_color));
            }
            holder.b.setText(holder.c.desc);
            ret.setOnClickListener(this.b);
            return ret;
        }
    }

    static class b {
        public ImageView a;
        public TextView b;
        public Filter c;

        b() {
        }
    }

    public FilterSelectView(Context context) {
        this(context, null, 0);
    }

    public FilterSelectView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public void setUpData() {
        long start = System.currentTimeMillis();
        this.mFilters.clear();
        for (APFilterInfo info : this.mMeterialService.getSupportedFilters()) {
            this.mFilters.add(new Filter(info));
        }
        setFirstSelected();
        Logger.debug(TAG, "setUpData cost:" + (System.currentTimeMillis() - start));
        this.mAdapter.notifyDataSetChanged();
    }

    public void setSelectListener(FilterSelectListener listener) {
        this.mSelectListener = listener;
    }

    private void setFirstSelected() {
        if (this.mFilters != null && !this.mFilters.isEmpty()) {
            for (Filter f : this.mFilters) {
                if (f.isSelected) {
                    if (this.mCurrentSelected == null) {
                        this.mCurrentSelected = f;
                    } else {
                        f.isSelected = false;
                    }
                }
                if (this.mCurrentSelected == null) {
                    this.mCurrentSelected = this.mFilters.get(0);
                    this.mCurrentSelected.isSelected = true;
                }
            }
        }
    }

    public FilterSelectView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mFilters = new LinkedList();
        this.mMeterialService = (MultimediaMaterialService) ServiceFactory.getAliService(MultimediaMaterialService.class);
        LayoutInflater.from(context).inflate(R.layout.view_filter_select, this, true);
        this.goneBtn = (ImageView) findViewById(R.id.ivHideFilterPanel);
        this.filterList = (AUHorizontalListView) findViewById(R.id.filterList);
        this.mAdapter = new a();
        this.filterList.setAdapter((ListAdapter) this.mAdapter);
        this.goneBtn.setOnClickListener(new OnClickListener() {
            public final void onClick(View v) {
                FilterSelectView.this.setVisibility(8);
            }
        });
        this.mImageService = (MultimediaImageService) ServiceFactory.getAliService(MultimediaImageService.class);
        this.defaultDrawable = getResources().getDrawable(R.drawable.drawable_default);
        this.popUp = AnimationUtils.loadAnimation(getContext(), R.anim.effect_select_pop_up);
        this.popUp.setAnimationListener(new AnimationListener() {
            public final void onAnimationStart(Animation animation) {
                FilterSelectView.super.setVisibility(0);
            }

            public final void onAnimationEnd(Animation animation) {
            }

            public final void onAnimationRepeat(Animation animation) {
            }
        });
        this.popDown = AnimationUtils.loadAnimation(getContext(), R.anim.effect_select_pop_down);
        this.popDown.setAnimationListener(new AnimationListener() {
            public final void onAnimationStart(Animation animation) {
                if (FilterSelectView.this.mSelectListener != null) {
                    FilterSelectView.this.mSelectListener.onPanelGone();
                }
            }

            public final void onAnimationEnd(Animation animation) {
                FilterSelectView.super.setVisibility(8);
            }

            public final void onAnimationRepeat(Animation animation) {
            }
        });
    }

    public void setVisibility(int visibility) {
        if (getVisibility() != visibility) {
            switch (visibility) {
                case 0:
                    startAnimation(this.popUp);
                    return;
                case 4:
                    super.setVisibility(visibility);
                    return;
                case 8:
                    startAnimation(this.popDown);
                    return;
                default:
                    return;
            }
        }
    }
}
