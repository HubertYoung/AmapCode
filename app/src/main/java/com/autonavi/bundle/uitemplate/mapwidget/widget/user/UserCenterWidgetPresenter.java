package com.autonavi.bundle.uitemplate.mapwidget.widget.user;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName;
import com.amap.bundle.statistics.LogManager;
import com.amap.bundle.tripgroup.api.IVoicePackageManager;
import com.autonavi.bundle.uitemplate.mapwidget.widget.BaseMapWidgetPresenter;
import com.autonavi.common.PageBundle;
import com.autonavi.common.imageloader.ImageLoader.LoadedFrom;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.bundle.msgbox.api.IMsgboxService;
import com.autonavi.widget.ui.BalloonLayout;
import java.lang.ref.WeakReference;
import org.json.JSONException;
import org.json.JSONObject;

public class UserCenterWidgetPresenter extends BaseMapWidgetPresenter<UserCenterMapWidget> {
    /* access modifiers changed from: private */
    public boolean hasTriggerLongPress;
    private Handler mHandler;
    private UserHeadIconCallback mUserHeadIconCallback;
    private UserSpHelper mUserSpHelper = new UserSpHelper();

    static final class UserHeadIconCallback implements bkf {
        private WeakReference<UserCenterMapWidget> mHost;

        public final void onBitmapLoaded(Bitmap bitmap, LoadedFrom loadedFrom) {
        }

        public final void onPrepareLoad(Drawable drawable) {
        }

        public UserHeadIconCallback(UserCenterMapWidget userCenterMapWidget) {
            this.mHost = new WeakReference<>(userCenterMapWidget);
        }

        public final void onBitmapFailed(Drawable drawable) {
            UserCenterMapWidget userCenterMapWidget = (UserCenterMapWidget) this.mHost.get();
            if (userCenterMapWidget != null) {
                userCenterMapWidget.setDefaultIconForHeadIcon();
            }
        }
    }

    static final class UserSpHelper {
        private final String KEY_RED_DOT_CLICK_FLAG;
        private MapSharePreference msp;

        private UserSpHelper() {
            this.KEY_RED_DOT_CLICK_FLAG = "key_red_dot_click_flag";
            this.msp = new MapSharePreference(SharePreferenceName.SharedPreferences);
        }

        public final void setRedDotClickFlag(boolean z) {
            this.msp.edit().putBoolean("key_red_dot_click_flag", z);
        }

        public final boolean getRedDotClickFlag() {
            return this.msp.getBooleanValue("key_red_dot_click_flag", false);
        }
    }

    public void initialize(UserCenterMapWidget userCenterMapWidget) {
        this.mListenerTypeList.add(Integer.valueOf(1));
        super.initialize(userCenterMapWidget);
    }

    public void internalClickListener(View view) {
        redirectUserCenter();
    }

    public void redirectUserCenter() {
        if (this.mBindWidget != null) {
            updateLogWhenStartUserPage();
            setPortraitClicked();
            startMinePage(false);
        }
    }

    public static void updateLogWhenStartUserPage() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("status", bnv.d());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        LogManager.actionLogV2("P00001", "B001", jSONObject);
    }

    public static void startMinePage(boolean z) {
        bid pageContext = AMapPageUtil.getPageContext();
        PageBundle pageBundle = new PageBundle();
        new bnv();
        pageBundle.putString("jsData", bnv.b());
        if (z) {
            pageBundle.setFlags(16);
        }
        apr apr = (apr) a.a.a(apr.class);
        if (apr != null) {
            apr.b(pageContext, pageBundle);
        }
    }

    public boolean internalOnTouchListener(View view, MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case 0:
                this.hasTriggerLongPress = false;
                if (this.mHandler == null) {
                    this.mHandler = new Handler();
                }
                this.mHandler.postDelayed(new Runnable() {
                    public void run() {
                        UserCenterWidgetPresenter.this.hasTriggerLongPress = true;
                        bid pageContext = AMapPageUtil.getPageContext();
                        if (pageContext != null) {
                            pageContext.startPage((String) "amap.basemap.action.config_page", (PageBundle) null);
                        }
                    }
                }, BalloonLayout.DEFAULT_DISPLAY_DURATION);
                break;
            case 1:
                if (this.mHandler != null) {
                    this.mHandler.removeCallbacksAndMessages(null);
                    this.mHandler = null;
                }
                if (this.hasTriggerLongPress) {
                    return true;
                }
                break;
        }
        return false;
    }

    private void setPortraitClicked() {
        if (((UserCenterMapWidget) this.mBindWidget).isSmallDotVisibility()) {
            this.mUserSpHelper.setRedDotClickFlag(true);
            ((UserCenterMapWidget) this.mBindWidget).setSmallDotVisibility(8);
            IUserEventDelegate iUserEventDelegate = (IUserEventDelegate) getEventDelegate();
            if (iUserEventDelegate != null) {
                iUserEventDelegate.setLaboratoryHeadRedShowFlag(false);
            }
        }
        MapSharePreference mapSharePreference = new MapSharePreference(SharePreferenceName.SharedPreferences);
        mapSharePreference.putBooleanValue("NewFeatureTriggerMainPage756", true);
        mapSharePreference.putBooleanValue("map_skin_indicator_1", false);
        IVoicePackageManager iVoicePackageManager = (IVoicePackageManager) ank.a(IVoicePackageManager.class);
        if (iVoicePackageManager != null) {
            String naviTtsUpdateVer = iVoicePackageManager.getNaviTtsUpdateVer();
            if (!TextUtils.isEmpty(naviTtsUpdateVer)) {
                mapSharePreference.putStringValue(IMsgboxService.SP_KEY_MsgboxNaviTtsVer, naviTtsUpdateVer);
            }
        }
    }

    public void pageResume(bid bid) {
        updatePortrait();
    }

    private void updatePortrait() {
        String str;
        IUserEventDelegate iUserEventDelegate = (IUserEventDelegate) getEventDelegate();
        String str2 = null;
        if (iUserEventDelegate != null) {
            str2 = iUserEventDelegate.getPortraitUrl();
            str = iUserEventDelegate.getUserLevel();
            if (iUserEventDelegate.getLaboratoryHeadRedShowFlag()) {
                ((UserCenterMapWidget) this.mBindWidget).setSmallDotVisibility(0);
            } else if (((UserCenterMapWidget) this.mBindWidget).isSmallDotVisibility()) {
                this.mUserSpHelper.setRedDotClickFlag(false);
                ((UserCenterMapWidget) this.mBindWidget).setSmallDotVisibility(8);
            }
        } else {
            str = null;
        }
        if (this.mUserHeadIconCallback == null) {
            this.mUserHeadIconCallback = new UserHeadIconCallback((UserCenterMapWidget) this.mBindWidget);
        }
        ((UserCenterMapWidget) this.mBindWidget).updateUserHeadIcon(str2, this.mUserHeadIconCallback);
        ((UserCenterMapWidget) this.mBindWidget).updateUserLevel(str);
    }
}
