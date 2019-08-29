package com.alipay.mobile.beehive.capture.views;

import android.content.Context;
import android.graphics.Color;
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
import com.alipay.android.phone.mobilecommon.multimedia.api.MultimediaImageService;
import com.alipay.android.phone.mobilecommon.multimedia.api.MultimediaMaterialService;
import com.alipay.android.phone.mobilecommon.multimedia.material.APBizMaterialPackage;
import com.alipay.android.phone.mobilecommon.multimedia.material.APMaterialDownloadRequest;
import com.alipay.android.phone.mobilecommon.multimedia.material.APPackageInfo;
import com.alipay.android.phone.mobilecommon.multimedia.material.callback.APBizMaterialPackageQueryCallback;
import com.alipay.android.phone.mobilecommon.multimedia.material.callback.APOnCancelListener;
import com.alipay.android.phone.mobilecommon.multimedia.material.callback.APOnCompleteListener;
import com.alipay.android.phone.mobilecommon.multimedia.material.callback.APOnErrorListener;
import com.alipay.android.phone.mobilecommon.multimedia.material.response.APBizMaterialPackageQueryComplete;
import com.alipay.android.phone.mobilecommon.multimedia.material.response.APBizMaterialPackageQueryError;
import com.alipay.android.phone.mobilecommon.multimedia.material.response.APDownloadCancel;
import com.alipay.android.phone.mobilecommon.multimedia.material.response.APDownloadComplete;
import com.alipay.android.phone.mobilecommon.multimedia.material.response.APDownloadError;
import com.alipay.mobile.antui.basic.AUHorizontalListView;
import com.alipay.mobile.antui.basic.AUProgressBar;
import com.alipay.mobile.beehive.R;
import com.alipay.mobile.beehive.capture.constant.Constants;
import com.alipay.mobile.beehive.capture.modle.Effect;
import com.alipay.mobile.beehive.capture.modle.Effect.ResLocalState;
import com.alipay.mobile.beehive.capture.modle.EffectPackage;
import com.alipay.mobile.beehive.capture.utils.Logger;
import com.alipay.mobile.beehive.capture.utils.PreferenceManager;
import com.alipay.mobile.beehive.capture.utils.ServiceFactory;
import com.alipay.mobile.beehive.capture.utils.StatusCovert;
import com.alipay.mobile.beehive.util.MicroServiceUtil;
import com.alipay.mobile.framework.service.common.TaskScheduleService;
import com.alipay.mobile.framework.service.common.TaskScheduleService.ScheduleType;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class EffectSelectView extends FrameLayout {
    private static final String TAG = "EffectSelectView";
    private AUHorizontalListView effectList;
    private ImageView ivHidePanel;
    /* access modifiers changed from: private */
    public int mColorSelectedPackageBg;
    /* access modifiers changed from: private */
    public Effect mCurrentSelectedEffect;
    /* access modifiers changed from: private */
    public EffectPackage mCurrentSelectedPackage;
    /* access modifiers changed from: private */
    public b mEffectAdapter;
    /* access modifiers changed from: private */
    public EffectSelectListener mEffectListener;
    /* access modifiers changed from: private */
    public List<EffectPackage> mEffectPackages;
    private EffectPackage mLatestUsedEffectPackage;
    /* access modifiers changed from: private */
    public MultimediaMaterialService mMaterialService;
    private d mPackageAdapter;
    /* access modifiers changed from: private */
    public MultimediaImageService multimediaImageService;
    private AUHorizontalListView packageList;
    Animation popDown;
    Animation popUp;

    public interface EffectSelectListener {
        void onEffectSelected(Effect effect);

        void onPanelGone();
    }

    static class a implements APOnCancelListener, APOnCompleteListener, APOnErrorListener {
        private Effect a;
        private WeakReference<EffectSelectView> b;

        public a(EffectSelectView context, Effect effect) {
            this.a = effect;
            this.b = new WeakReference<>(context);
        }

        public final void onCancel(APDownloadCancel apDownloadCancel) {
            a(ResLocalState.NOT_EXIST);
        }

        public final void onComplete(APDownloadComplete apDownloadComplete) {
            a(ResLocalState.EXIST);
        }

        public final void onError(APDownloadError apDownloadError) {
            Logger.debug(EffectSelectView.TAG, "effectId =" + this.a.effectId + "download Error,errorMsg = " + apDownloadError.msg + ",");
            a(ResLocalState.NOT_EXIST);
        }

        private void a(ResLocalState state) {
            EffectSelectView context = (EffectSelectView) this.b.get();
            if (context != null) {
                context.updateEffectState(this.a, state);
            }
        }
    }

    class b extends BaseAdapter {
        /* access modifiers changed from: private */
        public OnClickListener b = new OnClickListener() {
            public final void onClick(View v) {
                c holder = (c) v.getTag();
                if (!b.this.a(holder.b) && EffectSelectView.this.mCurrentSelectedEffect != holder.b) {
                    if (EffectSelectView.this.mCurrentSelectedEffect != null) {
                        EffectSelectView.this.mCurrentSelectedEffect.isSelected = false;
                    }
                    EffectSelectView.this.mCurrentSelectedEffect = holder.b;
                    EffectSelectView.this.mCurrentSelectedEffect.isSelected = true;
                    b.this.notifyDataSetChanged();
                    if (b.this.b != null) {
                        EffectSelectView.this.mEffectListener.onEffectSelected(EffectSelectView.this.mCurrentSelectedEffect);
                    }
                    if (!EffectSelectView.this.mCurrentSelectedPackage.isLatestUsedPackage()) {
                        EffectSelectView.this.recordUsed(EffectSelectView.this.mCurrentSelectedEffect);
                    }
                }
            }
        };

        b() {
        }

        /* access modifiers changed from: private */
        public boolean a(Effect effect) {
            if (effect.isNonEffect() || effect.localState == ResLocalState.EXIST) {
                return false;
            }
            APMaterialDownloadRequest req = new APMaterialDownloadRequest(effect.effectId);
            a wrapper = new a(EffectSelectView.this, effect);
            req.setCancelListener(wrapper);
            req.setCompleteListener(wrapper);
            req.setErrorListener(wrapper);
            EffectSelectView.this.mMaterialService.downloadMaterial(req);
            b(effect);
            return true;
        }

        private void b(Effect target) {
            ResLocalState currentState = StatusCovert.wrapDownloadState(EffectSelectView.this.mMaterialService.getMaterialStatus(target.effectId));
            if (target.localState != currentState) {
                target.localState = currentState;
                notifyDataSetChanged();
            }
        }

        public final int getCount() {
            if (EffectSelectView.this.mCurrentSelectedPackage == null || EffectSelectView.this.mCurrentSelectedPackage.effects == null) {
                return 0;
            }
            return EffectSelectView.this.mCurrentSelectedPackage.effects.size();
        }

        /* access modifiers changed from: private */
        /* renamed from: a */
        public Effect getItem(int position) {
            return EffectSelectView.this.mCurrentSelectedPackage.effects.get(position);
        }

        public final long getItemId(int position) {
            return (long) position;
        }

        public final View getView(int position, View convertView, ViewGroup parent) {
            Effect effect = getItem(position);
            View v = convertView;
            if (convertView == null) {
                v = LayoutInflater.from(EffectSelectView.this.getContext()).inflate(R.layout.view_effect_item, null);
                c holder = new c();
                holder.a = (ImageView) v.findViewById(R.id.ivEffect);
                holder.d = (ImageView) v.findViewById(R.id.ivDownload);
                holder.c = (AUProgressBar) v.findViewById(R.id.progress);
                v.setTag(holder);
            }
            c holder2 = (c) v.getTag();
            holder2.b = effect;
            if (effect.isNonEffect()) {
                holder2.d.setVisibility(8);
                holder2.c.setVisibility(8);
                holder2.a.setAlpha(1.0f);
                EffectSelectView.this.setDrawableThroughMIS(holder2.a, EffectSelectView.this.getResources().getDrawable(R.drawable.ic_clear));
                v.setContentDescription(EffectSelectView.this.getContext().getString(R.string.clear_water_mark));
            } else {
                EffectSelectView.this.multimediaImageService.loadImage(effect.effectIcon, holder2.a, (Drawable) null, (String) Constants.CAPTURE_BUSINESS_ID);
                switch (holder2.b.localState) {
                    case EXIST:
                        holder2.d.setVisibility(8);
                        holder2.c.setVisibility(8);
                        holder2.a.setAlpha(1.0f);
                        break;
                    case NOT_EXIST:
                        holder2.d.setVisibility(0);
                        holder2.c.setVisibility(8);
                        holder2.a.setAlpha(1.0f);
                        break;
                    case DOWNLOADING:
                        holder2.a.setAlpha(0.4f);
                        holder2.d.setVisibility(8);
                        holder2.c.setVisibility(0);
                        break;
                }
                v.setContentDescription(effect.desc);
            }
            if (effect.isSelected) {
                holder2.a.setBackgroundDrawable(EffectSelectView.this.getResources().getDrawable(R.drawable.bg_effect));
            } else {
                holder2.a.setBackgroundDrawable(null);
            }
            v.setOnClickListener(this.b);
            return v;
        }
    }

    static class c {
        ImageView a;
        Effect b;
        AUProgressBar c;
        ImageView d;

        c() {
        }
    }

    class d extends BaseAdapter {
        private OnClickListener b = new OnClickListener() {
            public final void onClick(View v) {
                e holder = (e) v.getTag();
                if (EffectSelectView.this.mCurrentSelectedPackage != holder.b) {
                    if (EffectSelectView.this.mCurrentSelectedPackage != null) {
                        EffectSelectView.this.mCurrentSelectedPackage.isSelected = false;
                    }
                    EffectSelectView.this.mCurrentSelectedPackage = holder.b;
                    EffectSelectView.this.mCurrentSelectedPackage.isSelected = true;
                    EffectSelectView.this.notifyAllRefresh();
                }
            }
        };

        d() {
        }

        public final int getCount() {
            if (EffectSelectView.this.mEffectPackages == null) {
                return 0;
            }
            return EffectSelectView.this.mEffectPackages.size();
        }

        /* access modifiers changed from: private */
        /* renamed from: a */
        public EffectPackage getItem(int position) {
            return (EffectPackage) EffectSelectView.this.mEffectPackages.get(position);
        }

        public final long getItemId(int position) {
            return (long) position;
        }

        public final View getView(int position, View convertView, ViewGroup parent) {
            EffectPackage effectPackage = getItem(position);
            View v = convertView;
            if (convertView == null) {
                v = LayoutInflater.from(EffectSelectView.this.getContext()).inflate(R.layout.view_effect_package_item, null);
                e holder = new e();
                holder.a = (ImageView) v.findViewById(R.id.ivPackage);
                v.setTag(holder);
            }
            e holder2 = (e) v.getTag();
            holder2.b = effectPackage;
            if (holder2.b.isLatestUsedPackage()) {
                EffectSelectView.this.setDrawableThroughMIS(holder2.a, EffectSelectView.this.getResources().getDrawable(holder2.b.isSelected ? R.drawable.ic_used_selected : R.drawable.ic_used));
                v.setContentDescription(EffectSelectView.this.getContext().getString(R.string.latest_used));
            } else {
                EffectSelectView.this.multimediaImageService.loadImage(effectPackage.isSelected ? effectPackage.selectedIcon : effectPackage.packageIcon, holder2.a, (Drawable) null, (String) Constants.CAPTURE_BUSINESS_ID);
                v.setContentDescription(effectPackage.desc);
            }
            v.setBackgroundColor(effectPackage.isSelected ? EffectSelectView.this.mColorSelectedPackageBg : 0);
            v.setOnClickListener(this.b);
            return v;
        }
    }

    static class e {
        ImageView a;
        EffectPackage b;

        e() {
        }
    }

    public EffectSelectView(Context context) {
        this(context, null, 0);
    }

    public EffectSelectView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EffectSelectView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mColorSelectedPackageBg = Color.parseColor("#992C2C2C");
        init();
    }

    public void setEffectSelectedListener(EffectSelectListener listener) {
        this.mEffectListener = listener;
    }

    private void init() {
        this.multimediaImageService = (MultimediaImageService) ServiceFactory.getAliService(MultimediaImageService.class);
        this.mMaterialService = (MultimediaMaterialService) ServiceFactory.getAliService(MultimediaMaterialService.class);
        LayoutInflater.from(getContext()).inflate(R.layout.view_effect_select, this, true);
        this.effectList = (AUHorizontalListView) findViewById(R.id.effect_list);
        this.packageList = (AUHorizontalListView) findViewById(R.id.package_list);
        this.ivHidePanel = (ImageView) findViewById(R.id.ivHidePanel);
        this.mPackageAdapter = new d();
        this.mEffectAdapter = new b();
        this.effectList.setAdapter((ListAdapter) this.mEffectAdapter);
        this.packageList.setAdapter((ListAdapter) this.mPackageAdapter);
        this.ivHidePanel.setOnClickListener(new OnClickListener() {
            public final void onClick(View v) {
                EffectSelectView.this.setVisibility(8);
            }
        });
        this.popUp = AnimationUtils.loadAnimation(getContext(), R.anim.effect_select_pop_up);
        this.popUp.setAnimationListener(new AnimationListener() {
            public final void onAnimationStart(Animation animation) {
                EffectSelectView.super.setVisibility(0);
            }

            public final void onAnimationEnd(Animation animation) {
            }

            public final void onAnimationRepeat(Animation animation) {
            }
        });
        this.popDown = AnimationUtils.loadAnimation(getContext(), R.anim.effect_select_pop_down);
        this.popDown.setAnimationListener(new AnimationListener() {
            public final void onAnimationStart(Animation animation) {
                if (EffectSelectView.this.mEffectListener != null) {
                    EffectSelectView.this.mEffectListener.onPanelGone();
                }
            }

            public final void onAnimationEnd(Animation animation) {
                EffectSelectView.super.setVisibility(8);
            }

            public final void onAnimationRepeat(Animation animation) {
            }
        });
    }

    public void setupData(final String businessId) {
        long start = System.currentTimeMillis();
        APBizMaterialPackage packages = this.mMaterialService.getBizMaterialPackage(businessId, new APBizMaterialPackageQueryCallback() {
            public final void onQueryComplete(APBizMaterialPackageQueryComplete apBizMaterialPackageQueryComplete) {
                Logger.debug(EffectSelectView.TAG, "Query MaterialPackage success,id = " + businessId);
            }

            public final void onQueryError(APBizMaterialPackageQueryError apBizMaterialPackageQueryError) {
                Logger.debug(EffectSelectView.TAG, "Query MaterialPackage error,id = " + businessId + ",errorMsg = " + apBizMaterialPackageQueryError.msg);
            }
        });
        if (packages == null || packages.mPackageInfos == null || packages.mPackageInfos.isEmpty()) {
            Logger.debug(TAG, "When \"setupData\" called, MaterialPackage is not in local.Get preset instead.");
            packages = this.mMaterialService.getPresetBizMaterialPackage(businessId);
        }
        this.mEffectPackages = covertData(packages);
        syncUpdateEffectStatus(this.mEffectPackages);
        initFirstSelected();
        Logger.debug(TAG, "setUpData cost:" + (System.currentTimeMillis() - start));
        notifyAllRefresh();
    }

    private void syncUpdateEffectStatus(final List<EffectPackage> packages) {
        if (packages == null || packages.isEmpty()) {
            Logger.debug(TAG, "syncUpdateEffectStatus called when packages is Null");
            return;
        }
        TaskScheduleService scheduleService = (TaskScheduleService) MicroServiceUtil.getMicroService(TaskScheduleService.class);
        if (scheduleService == null) {
            Logger.warn(TAG, "get TaskScheduleService return Null!");
        } else {
            scheduleService.acquireExecutor(ScheduleType.URGENT).execute(new Runnable() {
                public final void run() {
                    long startTime = System.currentTimeMillis();
                    for (EffectPackage p : packages) {
                        if (p.effects != null && !p.effects.isEmpty()) {
                            for (final Effect e : p.effects) {
                                if (!e.isNonEffect()) {
                                    ResLocalState state = StatusCovert.wrapDownloadState(EffectSelectView.this.mMaterialService.getMaterialStatus(e.effectId));
                                    if (state == ResLocalState.DOWNLOADING) {
                                        Logger.debug(EffectSelectView.TAG, "syncUpdateEffectStatus will treat DOWNLOADING as NOT_EXIST! id = " + e.effectId);
                                        state = ResLocalState.NOT_EXIST;
                                    }
                                    if (state != ResLocalState.NOT_EXIST) {
                                        final ResLocalState localState = state;
                                        EffectSelectView.this.post(new Runnable() {
                                            public final void run() {
                                                e.localState = localState;
                                                EffectSelectView.this.mEffectAdapter.notifyDataSetChanged();
                                            }
                                        });
                                    }
                                }
                            }
                        }
                    }
                    Logger.debug(EffectSelectView.TAG, "syncUpdateEffectStatus cost time = " + (System.currentTimeMillis() - startTime));
                }
            });
        }
    }

    private List<EffectPackage> covertData(APBizMaterialPackage packages) {
        List<EffectPackage> ret = new LinkedList<>();
        if (packages.mPackageInfos != null) {
            for (APPackageInfo pi : packages.mPackageInfos) {
                ret.add(new EffectPackage(pi));
            }
        }
        Map allEffect = new HashMap();
        for (EffectPackage ep : ret) {
            if (ep.effects != null && !ep.effects.isEmpty()) {
                for (Effect e2 : ep.effects) {
                    allEffect.put(e2.effectId, e2);
                }
            }
        }
        this.mLatestUsedEffectPackage = buildLatestUsed(allEffect);
        ret.add(0, this.mLatestUsedEffectPackage);
        return ret;
    }

    private EffectPackage buildLatestUsed(Map<String, Effect> allEffects) {
        EffectPackage ret = new EffectPackage();
        ret.effects = PreferenceManager.getUsed(allEffects);
        ret.effects.add(0, new Effect());
        return ret;
    }

    private void initFirstSelected() {
        if (this.mEffectPackages == null || this.mEffectPackages.isEmpty()) {
            Logger.warn(TAG, "EffectPackage list is Empty!");
            return;
        }
        for (EffectPackage ep : this.mEffectPackages) {
            if (ep.isSelected) {
                if (this.mCurrentSelectedPackage != null) {
                    ep.isSelected = false;
                } else {
                    this.mCurrentSelectedPackage = ep;
                }
            }
            if (ep.effects == null || ep.effects.isEmpty()) {
                Logger.warn(TAG, String.format("Package %s has no effect!", new Object[]{ep.packageId}));
            } else {
                for (Effect e2 : ep.effects) {
                    if (e2.isSelected) {
                        if (this.mCurrentSelectedEffect != null) {
                            e2.isSelected = false;
                        } else {
                            this.mCurrentSelectedEffect = e2;
                        }
                    }
                }
            }
        }
        if (this.mCurrentSelectedPackage == null) {
            this.mCurrentSelectedPackage = this.mEffectPackages.get(0);
            this.mCurrentSelectedPackage.isSelected = true;
        }
        if (this.mCurrentSelectedEffect == null && this.mCurrentSelectedPackage.effects != null && !this.mCurrentSelectedPackage.effects.isEmpty()) {
            this.mCurrentSelectedEffect = this.mCurrentSelectedPackage.effects.get(0);
            this.mCurrentSelectedEffect.isSelected = true;
        }
    }

    /* access modifiers changed from: private */
    public void notifyAllRefresh() {
        this.mPackageAdapter.notifyDataSetChanged();
        this.mEffectAdapter.notifyDataSetChanged();
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

    /* access modifiers changed from: private */
    public void recordUsed(Effect effect) {
        if (!effect.isNonEffect()) {
            List effects = this.mLatestUsedEffectPackage.effects;
            if (!effects.isEmpty()) {
                effects.remove(effect);
            }
            effects.add(1, effect);
            PreferenceManager.updateUsed(effects);
        }
    }

    /* access modifiers changed from: private */
    public void updateEffectState(final Effect which, final ResLocalState state) {
        post(new Runnable() {
            public final void run() {
                if (which.localState != state) {
                    which.localState = state;
                    if (EffectSelectView.this.mEffectAdapter != null) {
                        EffectSelectView.this.mEffectAdapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }

    public void setDrawableThroughMIS(ImageView view, Drawable drawable) {
        MultimediaImageService imageService = (MultimediaImageService) MicroServiceUtil.getMicroService(MultimediaImageService.class);
        if (imageService != null) {
            imageService.loadImage((String) null, view, drawable, (String) Constants.CAPTURE_BUSINESS_ID);
        } else {
            Logger.warn(TAG, "setDrawableThroughMIS failed when MultimediaImageService is Null!");
        }
    }
}
