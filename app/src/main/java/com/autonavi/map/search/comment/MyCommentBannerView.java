package com.autonavi.map.search.comment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import com.amap.bundle.statistics.LogManager;
import com.autonavi.bundle.account.api.IAccountService;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.map.search.comment.model.MyCommentGoldBanner;
import com.autonavi.minimap.R;
import java.util.AbstractMap.SimpleEntry;
import java.util.List;

public class MyCommentBannerView extends FrameLayout {
    /* access modifiers changed from: private */
    public bwi mActionCommand;
    private OnClickListener mOnClickListener = new OnClickListener() {
        public final void onClick(View view) {
            if (view.getId() == R.id.banner_right) {
                MyCommentBannerView.this.startGoldShopFragment();
                LogManager.actionLogV25("P00244", "B003", new SimpleEntry("action", Integer.valueOf(1)));
                return;
            }
            if (view.getId() == R.id.banner_total_gold && !MyCommentBannerView.this.mState.k) {
                MyCommentBannerView.this.startLoginFragment();
                LogManager.actionLogV25("P00244", "B003", new SimpleEntry("action", Integer.valueOf(0)));
            }
        }
    };
    /* access modifiers changed from: private */
    public bwm mState;
    private bvy mViewHolder;

    public MyCommentBannerView(@NonNull Context context) {
        super(context);
    }

    public MyCommentBannerView(Context context, bwi bwi) {
        super(context);
        this.mActionCommand = bwi;
        LayoutInflater.from(context).inflate(R.layout.comment_view_banner, this);
        this.mViewHolder = new bvy(this);
        this.mViewHolder.a(R.id.banner_right, this.mOnClickListener);
        this.mViewHolder.a(R.id.banner_total_gold, this.mOnClickListener);
    }

    public void updateUI(bwm bwm, bwm bwm2) {
        this.mState = bwm;
        MyCommentGoldBanner goldBanner = getGoldBanner(bwm);
        int i = 0;
        this.mViewHolder.a(R.id.banner_img1, getImageUrl(goldBanner.picUrls, 0), R.drawable.comment_banner_icon_default);
        this.mViewHolder.a(R.id.banner_img2, getImageUrl(goldBanner.picUrls, 1), R.drawable.comment_banner_icon_default);
        if (goldBanner.totalGold >= 0) {
            i = goldBanner.totalGold;
        }
        int i2 = 70;
        if (goldBanner.todayGold <= 70) {
            i2 = goldBanner.todayGold;
        }
        if (bwm.k) {
            this.mViewHolder.a(R.id.banner_today_gold_prefix, (CharSequence) "今日可得");
            this.mViewHolder.a(R.id.banner_today_gold, (CharSequence) String.valueOf(i2));
            this.mViewHolder.a(R.id.banner_today_gold_suffix, (CharSequence) "金币");
            bvy bvy = this.mViewHolder;
            int i3 = R.id.banner_total_gold;
            StringBuilder sb = new StringBuilder("累计获得");
            sb.append(i);
            sb.append("金币");
            bvy.a(i3, (CharSequence) sb.toString());
            this.mViewHolder.a(R.id.banner_gold_hint, (CharSequence) "金币可兑换以上商品 更多 >>");
            return;
        }
        this.mViewHolder.a(R.id.banner_today_gold_prefix, (CharSequence) "还有");
        this.mViewHolder.a(R.id.banner_today_gold, (CharSequence) String.valueOf(i2));
        this.mViewHolder.a(R.id.banner_today_gold_suffix, (CharSequence) "金币未领取");
        this.mViewHolder.a(R.id.banner_total_gold, (CharSequence) "登录不再错过金币");
        this.mViewHolder.a(R.id.banner_gold_hint, (CharSequence) "金币可兑换以上商品 更多 >>");
    }

    private MyCommentGoldBanner getGoldBanner(bwm bwm) {
        MyCommentGoldBanner myCommentGoldBanner = new MyCommentGoldBanner();
        if (bwm.a == 0) {
            return bwm.b;
        }
        return bwm.a == 1 ? bwm.c : myCommentGoldBanner;
    }

    private String getImageUrl(List<String> list, int i) {
        if (list == null) {
            return "";
        }
        return (i >= list.size() || i < 0) ? "" : list.get(i);
    }

    /* access modifiers changed from: private */
    public void startGoldShopFragment() {
        MyCommentGoldBanner goldBanner = getGoldBanner(this.mState);
        if (!TextUtils.isEmpty(goldBanner.actionUri)) {
            this.mActionCommand.a(new Intent("android.intent.action.VIEW", Uri.parse(goldBanner.actionUri)));
        }
    }

    /* access modifiers changed from: private */
    public void startLoginFragment() {
        IAccountService iAccountService = (IAccountService) a.a.a(IAccountService.class);
        if (iAccountService != null) {
            iAccountService.a(AMapPageUtil.getPageContext(), (anq) new anq() {
                public final void loginOrBindCancel() {
                }

                public final void onComplete(boolean z) {
                    if (z) {
                        MyCommentBannerView.this.mActionCommand.a();
                        MyCommentBannerView.this.mActionCommand.a((bwa) new bwa() {
                            final /* synthetic */ boolean a = true;

                            public final String a() {
                                return "login";
                            }

                            public final Object b() {
                                return Boolean.valueOf(this.a);
                            }
                        });
                    }
                }
            });
        }
    }
}
