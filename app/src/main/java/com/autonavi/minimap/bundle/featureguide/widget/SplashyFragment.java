package com.autonavi.minimap.bundle.featureguide.widget;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnLayoutChangeListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import com.amap.bundle.blutils.FileUtil;
import com.amap.bundle.commonui.lottie.LottieView;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName;
import com.autonavi.common.utils.Constant;
import com.autonavi.minimap.R;
import com.autonavi.minimap.bundle.featureguide.bean.LottieData;
import com.autonavi.minimap.bundle.splashscreen.api.ISplashManager;
import com.autonavi.minimap.offline.model.FilePathHelper;
import com.autonavi.minimap.widget.ConfirmDlg;
import java.io.File;

public class SplashyFragment extends Fragment {
    private static final String ALC_EVENT_START_MAP_ERROR = "E001";
    private static final String ALC_EVENT_WEBVIEW_ERROR = "E002";
    private static final String ALC_PAGE_SPLASH = "P0016";
    private static final int BUTTON_TYPE_ENTER_MAP = 0;
    private static final int BUTTON_TYPE_LOAD_OFF_DATA = 1;
    private static final long DELAY_SKIP_TIME = 500;
    public static final String INTENT_LOTTIE_DATA = "LOTTIE_DATA";
    public static final String INTENT_VIEW_AREA_PADDING_BOTTOM_HEIGHT = "INTENT_VIEW_AREA_PADDING_BOTTOM_HEIGHT";
    public static final String INTENT_bgColor = "bgColor";
    public static final String INTENT_flagCacheDisplayed = "flagCacheDisplayed";
    public static final String INTENT_hide_experience = "hide_experience";
    public static final String INTENT_pageType = "pageType";
    public static final String INTENT_photoId = "photoId";
    public static final String INTENT_photoPath = "photoPath";
    public static final String INTENT_resId = "resId";
    public static final String INTENT_start_btn_bg = "start_btn_bg";
    private static final int START_BTN_WAIT_TIME = 1000;
    static final String TAG = "SplashyFragment";
    /* access modifiers changed from: private */
    public static boolean showNextPage = true;
    private String downloadUrl;
    /* access modifiers changed from: private */
    public ImageView mAppLogo;
    private String mBGColor = "";
    Bitmap mBit;
    private CheckBox mCheckBox;
    private boolean mFlagCacheDisplayed = false;
    private boolean mHideExperience = false;
    private ImageView mImageView;
    private boolean mImageViewCanRecycle = false;
    private boolean mIsStartMapError = false;
    private LottieView mLottieView;
    private PAGE_TYPE mPageType = PAGE_TYPE.DEFAULT;
    private int mPhotoId = 0;
    private String mPhotoPath = "";
    private int mResId = 0;
    private cxu mSpApp;
    /* access modifiers changed from: private */
    public Button mStart;
    private int mStartBtnBG = 0;
    private String mStartMapLog;
    private int mType = 0;
    private View mView;
    /* access modifiers changed from: private */
    public Handler mhandler = new Handler() {
        public final void handleMessage(Message message) {
            super.handleMessage(message);
            if (SplashyFragment.this.mAppLogo != null) {
                SplashyFragment.this.mAppLogo.setImageBitmap(SplashyFragment.this.mBit);
            }
        }
    };
    /* access modifiers changed from: private */
    public int startButtonVisibility = 4;

    public enum PAGE_TYPE {
        DEFAULT(0),
        LAST(1);
        
        private final int value;

        private PAGE_TYPE(int i) {
            this.value = i;
        }

        public final int value() {
            return this.value;
        }

        public static PAGE_TYPE valueOf(int i) {
            if (i == LAST.ordinal()) {
                return LAST;
            }
            return DEFAULT;
        }
    }

    public void onSaveInstanceState(Bundle bundle) {
    }

    public static void recycleImageView(ImageView imageView) {
        if (imageView != null) {
            Drawable drawable = imageView.getDrawable();
            if (drawable instanceof BitmapDrawable) {
                Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
                if (bitmap != null && !bitmap.isRecycled()) {
                    imageView.setImageBitmap(null);
                    bitmap.recycle();
                }
            }
        }
    }

    public void onGoMapClick() {
        this.mType = 0;
        skipDelay();
    }

    public void skipDelay() {
        skip();
    }

    private void skip() {
        Intent intent;
        this.mStartMapLog = "start map.";
        if (getActivity() == null) {
            this.mStartMapLog = "start map activity null.";
            return;
        }
        if (this.mCheckBox != null) {
            this.mCheckBox.isChecked();
        }
        Editor edit = new MapSharePreference((String) "SharedPreferences").sharedPrefs().edit();
        edit.putBoolean("isSplashNeedShow", true);
        edit.putInt("versionCode", a.a().b);
        edit.putString("versionName", a.a().a);
        edit.apply();
        MapSharePreference mapSharePreference = new MapSharePreference(SharePreferenceName.SharedPreferences);
        mapSharePreference.putBooleanValue(ConfirmDlg.SP_KEY_log_state, true);
        if (this.mType == 0) {
            if (!TextUtils.isEmpty(this.downloadUrl) && this.mCheckBox != null && this.mCheckBox.isChecked() && this.mSpApp != null) {
                intent = new Intent();
                intent.setClassName(getContext(), Constant.LAUNCHER_ACTIVITY_NAME);
                intent.putExtra("appDownloadUrl", this.downloadUrl);
                intent.putExtra("appDownloadName", this.mSpApp.b);
            } else if (isFromLauncher()) {
                intent = new Intent();
                intent.setClassName(getContext(), Constant.LAUNCHER_ACTIVITY_NAME);
            } else if (getActivity().getIntent() != null) {
                intent = new Intent(getActivity().getIntent());
                intent.setClassName(getContext(), Constant.LAUNCHER_ACTIVITY_NAME);
            } else {
                intent = new Intent();
                intent.setClassName(getContext(), Constant.LAUNCHER_ACTIVITY_NAME);
            }
            if (isFisrtStartApp()) {
                mapSharePreference.putBooleanValue(ISplashManager.Disclaimer, false);
            }
        } else {
            intent = new Intent();
            intent.setData(Uri.parse("androidamap://openFeature?featureName=Mine"));
        }
        this.mIsStartMapError = false;
        intent.putExtra(ISplashManager.FROM_GUIDE_VIEW, true);
        getActivity().startActivity(intent);
        getActivity().overridePendingTransition(R.anim.splash_anim_in, R.anim.splash_anim_out);
        getActivity().finish();
    }

    public void setAppMessage(cxu cxu) {
        if (cxu != null && !TextUtils.isEmpty(cxu.b) && !TextUtils.isEmpty(cxu.c)) {
            this.mSpApp = cxu;
            this.downloadUrl = cxu.a;
            if (this.mCheckBox != null) {
                this.mCheckBox.setText(cxu.b);
            }
            if (this.mStart != null) {
                this.mStart.setVisibility(8);
            }
        }
    }

    public void setAppIcon(final cxu cxu) {
        new Thread(new Runnable() {
            public final void run() {
                String substring = cxu.c.substring(cxu.c.lastIndexOf("/") + 1);
                StringBuilder sb = new StringBuilder();
                sb.append(FileUtil.getMapBaseStorage(SplashyFragment.this.getContext()));
                sb.append(FilePathHelper.APP_FOLDER);
                sb.append(File.separator);
                sb.append(substring);
                if (new File(sb.toString()).exists()) {
                    SplashyFragment splashyFragment = SplashyFragment.this;
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(FileUtil.getMapBaseStorage(SplashyFragment.this.getContext()));
                    sb2.append(FilePathHelper.APP_FOLDER);
                    sb2.append(File.separator);
                    sb2.append(substring);
                    splashyFragment.mBit = BitmapFactory.decodeFile(sb2.toString());
                    SplashyFragment.this.mhandler.sendEmptyMessage(0);
                }
            }
        }).start();
    }

    private boolean isFisrtStartApp() {
        return new MapSharePreference(SharePreferenceName.SharedPreferences).getBooleanValue(ISplashManager.Disclaimer, true);
    }

    @SuppressLint({"WrongViewCast"})
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        Bundle arguments = getArguments();
        this.mResId = arguments.getInt(INTENT_resId);
        this.mPageType = PAGE_TYPE.valueOf(arguments.getInt("pageType", 0));
        this.mPhotoId = arguments.getInt(INTENT_photoId, 0);
        this.mPhotoPath = arguments.getString(INTENT_photoPath, "");
        this.mBGColor = arguments.getString(INTENT_bgColor, "");
        this.mStartBtnBG = arguments.getInt(INTENT_start_btn_bg, 0);
        this.mHideExperience = arguments.getBoolean(INTENT_hide_experience, false);
        this.mFlagCacheDisplayed = arguments.getBoolean(INTENT_flagCacheDisplayed, false);
        this.mView = layoutInflater.inflate(this.mResId, null);
        final RelativeLayout relativeLayout = (RelativeLayout) this.mView.findViewById(R.id.guide_view_area);
        if (relativeLayout != null) {
            relativeLayout.setPadding(0, 0, 0, arguments.getInt(INTENT_VIEW_AREA_PADDING_BOTTOM_HEIGHT, 0));
            relativeLayout.addOnLayoutChangeListener(new OnLayoutChangeListener() {
                public final void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                    LayoutParams layoutParams;
                    if (relativeLayout.getHeight() > 0) {
                        relativeLayout.removeOnLayoutChangeListener(this);
                        if (relativeLayout.getHeight() > relativeLayout.getWidth()) {
                            layoutParams = new LayoutParams(-1, -2);
                        } else {
                            layoutParams = new LayoutParams(-2, -1);
                        }
                        relativeLayout.setLayoutParams(layoutParams);
                    }
                }
            });
        }
        LottieData lottieData = (LottieData) arguments.getParcelable(INTENT_LOTTIE_DATA);
        if (lottieData != null) {
            initLottieView(lottieData);
        }
        return this.mView;
    }

    private void initLottieView(LottieData lottieData) {
        this.mLottieView = (LottieView) this.mView.findViewById(R.id.lottieView);
        if (this.mLottieView != null) {
            this.mLottieView.setVisibility(0);
            this.mLottieView.setAssertData(lottieData.d, lottieData.e, lottieData.f, lottieData.g, lottieData.h, lottieData.i, lottieData.j);
        }
    }

    private void recycleImageView() {
        if (this.mImageView != null) {
            if (this.mImageViewCanRecycle) {
                recycleImageView(this.mImageView);
                return;
            }
            this.mImageView.setImageBitmap(null);
        }
    }

    public void onDestroy() {
        super.onDestroy();
    }

    public void onDestroyView() {
        super.onDestroyView();
        if (this.mIsStartMapError) {
            coe.a(ALC_PAGE_SPLASH, "E001", this.mStartMapLog == null ? "null" : this.mStartMapLog);
            this.mIsStartMapError = false;
        }
        recycleImageView();
    }

    public void setStartButtonVisibility(int i) {
        if (this.mPageType != PAGE_TYPE.LAST || this.mStart == null || this.mStart.getVisibility() != 0) {
            this.startButtonVisibility = i;
            if (i == 0) {
                aho.a(new Runnable() {
                    public final void run() {
                        if (SplashyFragment.this.startButtonVisibility == 0) {
                            SplashyFragment.showNextPage = false;
                        }
                        if (SplashyFragment.this.mStart != null && SplashyFragment.this.isVisible()) {
                            SplashyFragment.this.mStart.setVisibility(SplashyFragment.this.startButtonVisibility);
                        }
                    }
                }, 1000);
                return;
            }
            if (this.mStart != null && isVisible()) {
                this.mStart.setVisibility(i);
            }
        }
    }

    public static boolean isShowNextPage() {
        return showNextPage;
    }

    private boolean isFromLauncher() {
        if (!TextUtils.isEmpty(getActivity().getIntent().getAction()) && getActivity().getIntent().getAction().contentEquals("android.intent.action.MAIN") && getActivity().getIntent().getCategories() != null && getActivity().getIntent().getCategories().size() > 0) {
            for (String contentEquals : getActivity().getIntent().getCategories()) {
                if (contentEquals.contentEquals("android.intent.category.LAUNCHER")) {
                    return true;
                }
            }
        }
        return false;
    }
}
