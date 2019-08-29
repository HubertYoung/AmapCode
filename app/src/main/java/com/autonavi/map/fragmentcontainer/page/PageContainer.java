package com.autonavi.map.fragmentcontainer.page;

import android.content.Context;
import android.content.res.Configuration;
import android.os.SystemClock;
import android.support.annotation.AnimRes;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import com.autonavi.common.Page.ON_BACK_TYPE;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.inter.IPageManifest;
import com.autonavi.minimap.R;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PageContainer extends FrameLayout {
    /* access modifiers changed from: private */
    public boolean isHideForbid;
    private AnimationCache mAnimationCache = new AnimationCache();
    private TransitionAnimationListener mAnimationListener = new TransitionAnimationListener();
    private PageRecord mCurrentRecord;
    /* access modifiers changed from: private */
    public TransitionAnimation mEnterAnimation;
    /* access modifiers changed from: private */
    public TransitionAnimation mExitAnimation;
    private boolean mFirstPause = true;
    private AbstractBasePage mHostPage;
    /* access modifiers changed from: private */
    public boolean mIsDoAnimation;
    private PageAnimationListener mPageAnimationListener;
    private List<PageRecord> mPageRecords = new ArrayList();
    private List<Runnable> mResumeEventQueue = new ArrayList();
    private boolean mWillDoAnimation;

    static class AnimationCache extends SparseArray<Animation> {
        private AnimationCache() {
        }

        public void cache(@AnimRes int i, Animation animation) {
            put(i, animation);
        }

        public Animation load(@AnimRes int i) {
            Animation animation = (Animation) get(i);
            if (animation != null) {
                animation.cancel();
                animation.reset();
            }
            return animation;
        }
    }

    public interface PageAnimationListener {
        void onPageAnimationDone();
    }

    public static class PageRecord {
        PageBundle mArgs;
        int mLifeCycle = 0;
        Class<? extends AbstractBasePage> mPageClazz;
        AbstractBasePage mPageInstance;

        interface LifeCycle {
            public static final int CREATED = 10;
            public static final int NONE = 0;
            public static final int PAUSED = 40;
            public static final int RESUMED = 30;
            public static final int STARTED = 20;
            public static final int STOPED = 50;
        }

        public Class<?> getPageClazz() {
            return this.mPageClazz;
        }
    }

    public static class Transition {
        /* access modifiers changed from: private */
        @AnimRes
        public int mEnterAnim;
        /* access modifiers changed from: private */
        @AnimRes
        public int mExitAnim;

        public Transition(@AnimRes int i, @AnimRes int i2) {
            this.mEnterAnim = i;
            this.mExitAnim = i2;
        }
    }

    class TransitionAnimation {
        /* access modifiers changed from: private */
        public long id = Long.MIN_VALUE;
        /* access modifiers changed from: private */
        public boolean isRunning;
        /* access modifiers changed from: private */
        public Animation mAnimation;
        /* access modifiers changed from: private */
        public PageRecord mTarget;

        TransitionAnimation(Animation animation) {
            this.mAnimation = animation;
        }

        public void resume(PageRecord pageRecord) {
            this.mTarget = pageRecord;
            View contentView = pageRecord.mPageInstance.getContentView();
            contentView.setVisibility(0);
            PageContainer.this.mIsDoAnimation = true;
            PageContainer.this.isHideForbid = true;
            this.isRunning = true;
            contentView.startAnimation(this.mAnimation);
            this.id = ((long) hashCode()) + SystemClock.currentThreadTimeMillis();
        }

        public long getID() {
            return this.id;
        }

        public void pause() {
            if (this.mTarget != null && this.isRunning) {
                this.mTarget.mPageInstance.getContentView().clearAnimation();
                this.isRunning = false;
            }
        }

        public boolean isRunning() {
            return this.isRunning;
        }
    }

    class TransitionAnimationListener implements AnimationListener {
        private long mAnimationID;

        public void onAnimationRepeat(Animation animation) {
        }

        public void onAnimationStart(Animation animation) {
        }

        private TransitionAnimationListener() {
            this.mAnimationID = Long.MIN_VALUE;
        }

        /* access modifiers changed from: 0000 */
        public void reset() {
            this.mAnimationID = Long.MIN_VALUE;
        }

        /* access modifiers changed from: 0000 */
        public void attachAnimation(long j) {
            this.mAnimationID = j;
        }

        public void onAnimationEnd(Animation animation) {
            TransitionAnimation access$100 = PageContainer.this.mEnterAnimation;
            TransitionAnimation access$200 = PageContainer.this.mExitAnimation;
            if (access$100 != null && access$200 != null && access$100.id == this.mAnimationID) {
                PageContainer.this.transitionEnd(access$100.mTarget, access$200.mTarget);
            }
        }
    }

    private void printPages(String str) {
    }

    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        if (this.mIsDoAnimation) {
            return true;
        }
        return super.dispatchTouchEvent(motionEvent);
    }

    public void setPageAnimationListener(PageAnimationListener pageAnimationListener) {
        this.mPageAnimationListener = pageAnimationListener;
    }

    public void setId(int i) {
        if (i != R.id.page_container_id) {
            throw new IllegalStateException("请使用固定ID:R.id.page_container_id");
        }
        super.setId(i);
    }

    public final boolean isGoingDoAnimation() {
        return this.mWillDoAnimation;
    }

    public PageContainer(Context context) {
        super(context);
    }

    public PageContainer(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public PageContainer(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    /* access modifiers changed from: 0000 */
    public void attachHostPage(AbstractBasePage abstractBasePage) {
        this.mHostPage = abstractBasePage;
    }

    private PageRecord performCreate(PageRecord pageRecord) {
        if (pageRecord.mPageInstance != null) {
            if (pageRecord.mLifeCycle < 10) {
                pageRecord.mPageInstance.attach(this.mHostPage.getContext(), this.mHostPage.getLayoutInflater(), this.mHostPage, this.mHostPage.getPageId(), this.mHostPage.getMvpActivityContext());
                pageRecord.mPageInstance.performCreate(getContext());
                pageRecord.mLifeCycle = 10;
            }
            return pageRecord;
        } else if (pageRecord.mPageClazz == null) {
            return null;
        } else {
            try {
                pageRecord.mPageInstance = (AbstractBasePage) pageRecord.mPageClazz.newInstance();
                pageRecord.mPageInstance.attach(this.mHostPage.getContext(), this.mHostPage.getLayoutInflater(), this.mHostPage, this.mHostPage.getPageId(), this.mHostPage.getMvpActivityContext());
                pageRecord.mPageInstance.setArguments(pageRecord.mArgs);
                pageRecord.mPageInstance.performCreate(getContext());
                pageRecord.mLifeCycle = 10;
                pageRecord.mPageInstance.getContentView().setVisibility(8);
                addView(pageRecord.mPageInstance.getContentView());
                return pageRecord;
            } catch (InstantiationException e) {
                e.printStackTrace();
                return null;
            } catch (IllegalAccessException e2) {
                e2.printStackTrace();
                return null;
            }
        }
    }

    public AbstractBasePage getCureentRecordPage() {
        if (this.mCurrentRecord != null) {
            return this.mCurrentRecord.mPageInstance;
        }
        return null;
    }

    /* access modifiers changed from: 0000 */
    public void onPageResult(int i, ResultType resultType, PageBundle pageBundle) {
        if (this.mCurrentRecord != null) {
            this.mCurrentRecord.mPageInstance.onResult(i, resultType, pageBundle);
        }
    }

    /* access modifiers changed from: 0000 */
    public void onStart() {
        if (this.mCurrentRecord != null) {
            performStart(this.mCurrentRecord);
        }
    }

    /* access modifiers changed from: 0000 */
    public void onResume() {
        if (this.mCurrentRecord != null) {
            performResume(this.mCurrentRecord);
        }
        for (Runnable run : this.mResumeEventQueue) {
            run.run();
        }
        this.mResumeEventQueue.clear();
    }

    /* access modifiers changed from: 0000 */
    public void onConfigurationChange(Configuration configuration) {
        for (int size = this.mPageRecords.size() - 1; size >= 0; size--) {
            performConfigurationChanged(this.mPageRecords.get(size), configuration);
        }
    }

    /* access modifiers changed from: 0000 */
    public void onPause() {
        if (this.mCurrentRecord != null) {
            performPause(this.mCurrentRecord);
        }
    }

    /* access modifiers changed from: 0000 */
    public void onStop() {
        if (this.mCurrentRecord != null) {
            performStop(this.mCurrentRecord);
        }
    }

    /* access modifiers changed from: 0000 */
    public void onDestroy() {
        for (int size = this.mPageRecords.size() - 1; size >= 0; size--) {
            performDestroy(this.mPageRecords.get(size));
        }
        this.mCurrentRecord = null;
        this.mPageRecords.clear();
        removeAllViews();
    }

    /* access modifiers changed from: 0000 */
    public void finishInvokeStateChange(boolean z) {
        if (this.mCurrentRecord != null && this.mCurrentRecord.mPageInstance != null) {
            this.mCurrentRecord.mPageInstance.finishInvokeStateChange(z);
        }
    }

    /* access modifiers changed from: 0000 */
    public ON_BACK_TYPE onBackPressed() {
        if (this.mCurrentRecord != null) {
            return this.mCurrentRecord.mPageInstance.onBackPressed();
        }
        return ON_BACK_TYPE.TYPE_NORMAL;
    }

    /* access modifiers changed from: private */
    public void showPage(PageRecord pageRecord, PageBundle pageBundle, Animation animation, Animation animation2) {
        if (pageRecord != null) {
            if (!this.mHostPage.isStarted()) {
                final PageRecord pageRecord2 = pageRecord;
                final PageBundle pageBundle2 = pageBundle;
                final Animation animation3 = animation;
                final Animation animation4 = animation2;
                AnonymousClass1 r1 = new Runnable() {
                    public void run() {
                        PageContainer.this.showPage(pageRecord2, pageBundle2, animation3, animation4);
                    }
                };
                queueResumeEvent(r1);
            } else if (this.mCurrentRecord == pageRecord) {
                if (pageBundle != null) {
                    if (this.mCurrentRecord.mLifeCycle == 30) {
                        this.mCurrentRecord.mPageInstance.onPause();
                    }
                    this.mCurrentRecord.mPageInstance.setArguments(pageBundle);
                    this.mCurrentRecord.mPageInstance.onNewIntent(pageBundle);
                    this.mCurrentRecord.mPageInstance.onResume();
                }
            } else {
                if (this.mEnterAnimation != null && this.mEnterAnimation.isRunning) {
                    this.mEnterAnimation.pause();
                    PageRecord pageRecord3 = null;
                    if (this.mExitAnimation != null) {
                        this.mExitAnimation.pause();
                        pageRecord3 = this.mExitAnimation.mTarget;
                    }
                    transitionEnd(this.mEnterAnimation.mTarget, pageRecord3);
                }
                if (animation != null) {
                    this.mEnterAnimation = new TransitionAnimation(animation);
                    this.mExitAnimation = new TransitionAnimation(animation2);
                }
                if ((this.mExitAnimation == null && this.mEnterAnimation == null) || this.mCurrentRecord == null) {
                    showPageDirect(pageRecord, pageBundle);
                } else {
                    showPageWithAnimation(pageRecord, pageBundle);
                }
            }
        }
    }

    private void showPageDirect(PageRecord pageRecord, PageBundle pageBundle) {
        if (this.mCurrentRecord != null) {
            performPause(this.mCurrentRecord);
            performStop(this.mCurrentRecord);
        }
        if (pageRecord.mLifeCycle < 10) {
            pageRecord = performCreate(pageRecord);
        }
        this.mCurrentRecord = pageRecord;
        if (this.mCurrentRecord != null) {
            if (pageBundle != null) {
                this.mCurrentRecord.mPageInstance.setArguments(pageBundle);
            }
            performStart(this.mCurrentRecord);
            performResume(this.mCurrentRecord);
        }
    }

    private void showPageWithAnimation(PageRecord pageRecord, PageBundle pageBundle) {
        this.mWillDoAnimation = true;
        if (pageRecord.mLifeCycle < 10) {
            pageRecord = performCreate(pageRecord);
        }
        if (pageBundle != null) {
            pageRecord.mPageInstance.setArguments(pageBundle);
        }
        this.mEnterAnimation.mAnimation.setAnimationListener(this.mAnimationListener);
        this.mEnterAnimation.resume(pageRecord);
        this.mExitAnimation.resume(this.mCurrentRecord);
        this.mAnimationListener.attachAnimation(this.mEnterAnimation.getID());
    }

    /* access modifiers changed from: private */
    public void transitionEnd(PageRecord pageRecord, PageRecord pageRecord2) {
        this.mAnimationListener.reset();
        if (pageRecord != null) {
            this.mIsDoAnimation = false;
        }
        if (pageRecord2 != null) {
            this.mIsDoAnimation = false;
        }
        if (pageRecord != pageRecord2) {
            bringChildToFront(pageRecord.mPageInstance.getContentView());
            performPause(pageRecord2);
            performStop(pageRecord2);
            performStart(pageRecord);
            performResume(pageRecord);
            this.mWillDoAnimation = false;
            if (this.mPageAnimationListener != null) {
                this.mPageAnimationListener.onPageAnimationDone();
            }
            this.isHideForbid = false;
            this.mCurrentRecord = pageRecord;
            this.mEnterAnimation = null;
            this.mExitAnimation = null;
        }
    }

    public void showPage(String str, PageBundle pageBundle) {
        Class<?> page = ((IPageManifest) bqn.a(IPageManifest.class)).getPage(str);
        if (page != null) {
            showPage(page, pageBundle);
        }
    }

    public void showPage(String str) {
        Class<?> page = ((IPageManifest) bqn.a(IPageManifest.class)).getPage(str);
        if (page != null) {
            showPage(page);
        }
    }

    public void showPage(String str, PageBundle pageBundle, Transition transition) {
        Class<?> page = ((IPageManifest) bqn.a(IPageManifest.class)).getPage(str);
        if (page != null) {
            showPage(page, pageBundle, transition);
        }
    }

    public void showPage(String str, Transition transition) {
        Class<?> page = ((IPageManifest) bqn.a(IPageManifest.class)).getPage(str);
        if (page != null) {
            showPage(page, transition);
        }
    }

    public void showPage(Class<? extends AbstractBasePage> cls) {
        showPage(cls, (PageBundle) null);
    }

    public void showPage(Class<? extends AbstractBasePage> cls, PageBundle pageBundle) {
        showPage(cls, pageBundle, (Transition) null);
    }

    private PageRecord loadOrAdd(Class<? extends AbstractBasePage> cls, PageBundle pageBundle) {
        PageRecord pageRecord;
        Iterator<PageRecord> it = this.mPageRecords.iterator();
        while (true) {
            if (!it.hasNext()) {
                pageRecord = null;
                break;
            }
            pageRecord = it.next();
            if (pageRecord.mPageClazz == cls) {
                break;
            }
        }
        if (pageRecord != null) {
            return pageRecord;
        }
        PageRecord pageRecord2 = new PageRecord();
        pageRecord2.mPageClazz = cls;
        pageRecord2.mArgs = pageBundle;
        this.mPageRecords.add(pageRecord2);
        return pageRecord2;
    }

    public void showPage(Class<? extends AbstractBasePage> cls, Transition transition) {
        showPage(cls, (PageBundle) null, transition);
    }

    public void showPage(Class<? extends AbstractBasePage> cls, PageBundle pageBundle, Transition transition) {
        Animation animation;
        PageRecord loadOrAdd = loadOrAdd(cls, pageBundle);
        Animation animation2 = null;
        if (transition != null) {
            animation2 = this.mAnimationCache.load(transition.mEnterAnim);
            if (animation2 == null) {
                animation2 = AnimationUtils.loadAnimation(getContext(), transition.mEnterAnim);
                this.mAnimationCache.put(transition.mEnterAnim, animation2);
            }
            animation = this.mAnimationCache.load(transition.mExitAnim);
            if (animation == null) {
                animation = AnimationUtils.loadAnimation(getContext(), transition.mExitAnim);
                this.mAnimationCache.put(transition.mExitAnim, animation);
            }
        } else {
            animation = null;
        }
        showPage(loadOrAdd, pageBundle, animation2, animation);
    }

    private PageRecord performStart(PageRecord pageRecord) {
        if (pageRecord.mLifeCycle != 20 && pageRecord.mLifeCycle > 0) {
            pageRecord.mPageInstance.onStart();
            pageRecord.mLifeCycle = 20;
        }
        return pageRecord;
    }

    private PageRecord performResume(PageRecord pageRecord) {
        if (pageRecord.mLifeCycle == 30) {
            return pageRecord;
        }
        if (pageRecord.mLifeCycle == 20 || pageRecord.mLifeCycle == 40) {
            View contentView = pageRecord.mPageInstance.getContentView();
            if (indexOfChild(contentView) != getChildCount() - 1) {
                bringChildToFront(contentView);
            }
            contentView.setVisibility(0);
            pageRecord.mPageInstance.onResume();
            if (but.d != null) {
                but.d.a(pageRecord.mPageInstance);
            }
            pageRecord.mLifeCycle = 30;
        }
        return pageRecord;
    }

    private PageRecord performConfigurationChanged(PageRecord pageRecord, Configuration configuration) {
        if (!(pageRecord == null || pageRecord.mPageInstance == null)) {
            pageRecord.mPageInstance.onConfigurationChanged(configuration);
        }
        return pageRecord;
    }

    private PageRecord performPause(PageRecord pageRecord) {
        if (pageRecord != null && pageRecord.mLifeCycle == 30) {
            pageRecord.mPageInstance.onPause();
            if (this.mFirstPause || !((IPageHost) pageRecord.mPageInstance.getActivity()).isHostPaused() || this.isHideForbid || this.mWillDoAnimation) {
                pageRecord.mPageInstance.getContentView().setVisibility(8);
                this.mFirstPause = false;
            }
            pageRecord.mLifeCycle = 40;
        }
        return pageRecord;
    }

    private PageRecord performStop(PageRecord pageRecord) {
        if (pageRecord != null && pageRecord.mLifeCycle == 40) {
            pageRecord.mPageInstance.onStop();
            pageRecord.mLifeCycle = 50;
        }
        return pageRecord;
    }

    private PageRecord performDestroy(PageRecord pageRecord) {
        if (pageRecord.mLifeCycle > 0) {
            removeView(pageRecord.mPageInstance.getContentView());
            pageRecord.mPageInstance.onDestroy();
            pageRecord.mLifeCycle = 0;
        }
        this.mPageRecords.remove(pageRecord);
        if (this.mCurrentRecord == pageRecord) {
            this.mCurrentRecord = null;
        }
        return pageRecord;
    }

    private void queueResumeEvent(Runnable runnable) {
        if (this.mResumeEventQueue.contains(runnable)) {
            this.mResumeEventQueue.remove(runnable);
        }
        this.mResumeEventQueue.add(runnable);
    }

    public List<PageRecord> getPageRecords() {
        return this.mPageRecords;
    }
}
