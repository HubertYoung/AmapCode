package com.alipay.mobile.h5container.api;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.nebula.R;
import com.alipay.mobile.nebula.refresh.H5PullContainer;
import com.alipay.mobile.nebula.util.H5DimensionUtil;
import com.alipay.mobile.nebula.util.H5ImageUtil;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5ThreadType;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebula.view.H5LoadingView;
import com.alipay.mobile.nebula.view.H5Progress;
import com.alipay.mobile.nebula.view.H5Progress.ProgressNotifier;
import com.alipay.mobile.nebula.view.H5WebContentView;
import java.util.concurrent.atomic.AtomicBoolean;

public class H5WebContentImpl implements H5WebContentView {
    /* access modifiers changed from: private */
    public static String TAG = "H5WebContentImpl";
    public View contentView = H5ViewCache.getCachedViewById(R.layout.h5_web_content);
    /* access modifiers changed from: private */
    public ImageView h5BgImage;
    private H5LoadingView h5LoadingView;
    public H5Progress h5Progress;
    public TextView h5ProviderDomain;
    public LinearLayout h5ProviderLayout;
    public TextView h5ProviderUc;
    public TextView h5ProviderUcLogo;
    public View hDivider;
    /* access modifiers changed from: private */
    public AtomicBoolean isShowProgress = new AtomicBoolean(false);
    public H5PullContainer pullContainer;
    public View webContent;

    public H5WebContentImpl(Context context) {
        if (this.contentView == null) {
            this.contentView = LayoutInflater.from(context).inflate(R.layout.h5_web_content, null);
        }
        this.webContent = this.contentView.findViewById(R.id.h5_web_content);
        this.h5ProviderLayout = (LinearLayout) this.contentView.findViewById(R.id.h5_ly_provider_layout);
        this.h5ProviderDomain = (TextView) this.contentView.findViewById(R.id.h5_tv_provider_domain);
        this.h5ProviderUcLogo = (TextView) this.contentView.findViewById(R.id.h5_tv_provider_uclogo);
        this.h5ProviderUc = (TextView) this.contentView.findViewById(R.id.h5_tv_provider_uc);
        this.hDivider = this.contentView.findViewById(R.id.h5_h_divider);
        this.h5Progress = (H5Progress) this.contentView.findViewById(R.id.h5_pb_progress);
        this.h5LoadingView = (H5LoadingView) this.contentView.findViewById(R.id.h5_loading_view);
        this.h5BgImage = (ImageView) this.contentView.findViewById(R.id.h5_iv_bg_image);
        this.h5Progress.setNotifier(new ProgressNotifier() {
            public void onProgressBegin() {
                H5Log.d(H5WebContentImpl.TAG, "isShowProgress:" + H5WebContentImpl.this.isShowProgress + " visible:" + H5WebContentImpl.this.h5Progress.getVisibility());
                H5WebContentImpl.this.isShowProgress.set(true);
            }

            public void onProgressEnd() {
                H5Log.d(H5WebContentImpl.TAG, "isShowProgress:" + H5WebContentImpl.this.isShowProgress + " visible:" + H5WebContentImpl.this.h5Progress.getVisibility());
                if (H5WebContentImpl.this.isShowProgress.compareAndSet(true, false) && H5WebContentImpl.this.h5Progress.getVisibility() == 0) {
                    TranslateAnimation mHiddenAction = new TranslateAnimation(1, 0.0f, 1, 0.0f, 1, 0.0f, 1, -1.0f);
                    mHiddenAction.setDuration(300);
                    H5WebContentImpl.this.h5Progress.startAnimation(mHiddenAction);
                    H5WebContentImpl.this.h5Progress.setVisibility(8);
                }
            }
        });
        this.pullContainer = (H5PullContainer) this.contentView.findViewById(R.id.h5_pc_container);
        this.webContent.setBackgroundColor(context.getResources().getColor(R.color.h5_provider));
    }

    public H5PullContainer getPullContainer() {
        if (this.pullContainer != null) {
            return this.pullContainer;
        }
        return null;
    }

    public H5Progress getProgress() {
        if (this.h5Progress != null) {
            return this.h5Progress;
        }
        return null;
    }

    public View getContentView() {
        return this.contentView;
    }

    public void setProviderText(String provider) {
        this.h5ProviderDomain.setText(provider);
    }

    public void setProviderLogo(Drawable drawable) {
        if (VERSION.SDK_INT >= 16) {
            this.h5ProviderUcLogo.setBackground(drawable);
        } else {
            this.h5ProviderUcLogo.setBackgroundDrawable(drawable);
        }
    }

    public void setProviderUc(String providerUc) {
        this.h5ProviderUc.setText(providerUc);
    }

    public void showProviderVisibility(boolean show) {
        if (show) {
            this.h5ProviderLayout.setVisibility(0);
        } else {
            this.h5ProviderLayout.setVisibility(8);
        }
    }

    public boolean isCustomBackground() {
        return false;
    }

    public View getHdivider() {
        if (this.hDivider != null) {
            return this.hDivider;
        }
        return null;
    }

    public LinearLayout getProviderLayout() {
        return this.h5ProviderLayout;
    }

    public void switchCustomContentBg(int color, Drawable ucLogo, boolean includeLogo) {
        if (color != -16777216) {
            color |= -16777216;
        }
        this.webContent.setBackgroundColor(color);
        this.h5ProviderDomain.setTextColor(-1);
        this.h5ProviderDomain.setAlpha(0.8f);
        this.h5ProviderDomain.setBackgroundColor(color);
        if (includeLogo) {
            this.h5ProviderUc.setTextColor(-1);
            this.h5ProviderUc.setAlpha(0.8f);
            this.h5ProviderUc.setBackgroundColor(color);
            setProviderLogo(ucLogo);
        }
    }

    public void swicthDefaultContentBg(Drawable ucLogo, boolean includeLogo) {
        this.webContent.setBackgroundColor(H5Param.DEFAULT_LONG_BOUNCE_TOP_COLOR);
        this.h5ProviderDomain.setTextColor(-6579301);
        this.h5ProviderDomain.setAlpha(1.0f);
        this.h5ProviderDomain.setBackgroundColor(H5Param.DEFAULT_LONG_BOUNCE_TOP_COLOR);
        if (includeLogo) {
            this.h5ProviderUc.setTextColor(-6579301);
            this.h5ProviderUc.setAlpha(1.0f);
            this.h5ProviderUc.setBackgroundColor(H5Param.DEFAULT_LONG_BOUNCE_TOP_COLOR);
            setProviderLogo(ucLogo);
        }
    }

    public TextView getH5ProviderDomain() {
        return this.h5ProviderDomain;
    }

    public H5LoadingView getH5LoadingView() {
        return this.h5LoadingView;
    }

    public void setBackgroundImageColor(int color) {
        this.h5BgImage.setVisibility(0);
        this.h5BgImage.setBackgroundColor(-16777216 | color);
    }

    public void setBackgroundImage(final Bitmap image) {
        if (image != null) {
            this.h5BgImage.setVisibility(0);
            H5Utils.getExecutor(H5ThreadType.IO).execute(new Runnable() {
                public void run() {
                    int width;
                    try {
                        if (H5WebContentImpl.this.h5BgImage.getWidth() > 0) {
                            width = H5WebContentImpl.this.h5BgImage.getWidth();
                        } else {
                            width = H5DimensionUtil.getScreenWidth(LauncherApplicationAgent.getInstance().getApplicationContext());
                        }
                        final Bitmap scaleBitmap = H5ImageUtil.scaleBitmap(image, width, Integer.MAX_VALUE);
                        H5Utils.runOnMain(new Runnable() {
                            public void run() {
                                H5WebContentImpl.this.h5BgImage.setImageBitmap(scaleBitmap != null ? scaleBitmap : image);
                            }
                        });
                    } catch (Exception e) {
                        H5Log.e(H5WebContentImpl.TAG, (Throwable) e);
                        H5WebContentImpl.this.h5BgImage.setImageBitmap(image);
                    }
                }
            });
        }
    }

    public void hideBackgroundImage() {
        this.h5BgImage.setVisibility(4);
    }
}
