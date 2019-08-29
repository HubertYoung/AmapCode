package com.autonavi.gdtaojin.camera;

import android.content.Context;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.PopupWindow;
import android.widget.TextView;

public class CameraSettingsMenu implements OnTouchListener, OnCheckedChangeListener {
    public static final String PREF_KEY_DEVELOP_PIC_QUALITY = "developPicQuality";
    public static final String PREF_KEY_FLASHLIGHT = "flushType";
    public static final String PREF_KEY_IS_VOLUMEKEY_TAKEPIC = "volumeKeyTakePic";
    public static final String PREF_KEY_TOUCH_TAKE = "touchTake";
    public static final int SETTING_CHANGE_PICTURE_SIZE = 4;
    public static final int SETTING_DEVELOP_PICTURE_QUALITY = 2;
    public static final int SETTING_FLASHLIGHT = 1;
    public static final int SETTING_VOLUME_KEY_TAKE_PICTURE = 3;
    private View btChangePicSize;
    private CheckBox cbFlashlight;
    private CheckBox cbTouchTake;
    private CheckBox cbVolumeKeyFunction;
    private Context context;
    private int flashlightOn;
    private View layoutFlashlight;
    private View layoutTouchCapture;
    private View layoutVolumeKey;
    private OnSettingChangeListener listener;
    private TextView mTextViewPictureSize;
    private PopupWindow popupCameraSettings;
    private Resources resourse;
    private TextView textView;
    private View viewDivider;
    private View viewDivider2;

    public interface OnSettingChangeListener {
        void onSettingChange(int i, boolean z);
    }

    public CameraSettingsMenu(Context context2) {
        this.context = context2;
        initPopupCameraSettings();
    }

    public CameraSettingsMenu(Context context2, OnSettingChangeListener onSettingChangeListener, AbstractCameraControllerManager abstractCameraControllerManager, Resources resources) {
        this.context = context2;
        this.listener = onSettingChangeListener;
        this.resourse = resources;
        initPopupCameraSettings();
    }

    private void initPopupCameraSettings() {
        if (this.popupCameraSettings == null && this.resourse != null) {
            this.flashlightOn = this.context.getSharedPreferences("SharedPreferences", 0).getInt(PREF_KEY_FLASHLIGHT, 0);
            setVolumeKeyTakePic(CameraConst.IS_HAS_VOLUME_CAPTURE);
            setTouchTake(CameraConst.IS_HAS_TOUCH_CAPTURE);
        }
    }

    public void showMenu(View view, boolean z, boolean z2) {
        int i;
        int i2;
        int i3 = 0;
        if (!z) {
            this.cbFlashlight.setEnabled(false);
        }
        if (!z2) {
            this.cbTouchTake.setEnabled(false);
        }
        if (CameraConst.IS_HAS_FLASH || this.layoutFlashlight == null || this.viewDivider == null) {
            i = 0;
        } else {
            this.layoutFlashlight.setVisibility(8);
            i = 1;
        }
        if (CameraConst.IS_HAS_TOUCH_CAPTURE || this.layoutTouchCapture == null || this.viewDivider2 == null) {
            i2 = 0;
        } else {
            this.layoutTouchCapture.setVisibility(8);
            i2 = 1;
        }
        if (!(CameraConst.IS_HAS_VOLUME_CAPTURE || this.layoutVolumeKey == null || this.viewDivider2 == null)) {
            this.layoutVolumeKey.setVisibility(8);
            i3 = 1;
        }
        int i4 = i + i3 + i2;
        if (!(this.viewDivider == null || this.viewDivider2 == null)) {
            if (i4 == 1) {
                if (i == 1) {
                    this.viewDivider.setVisibility(8);
                }
                if (i2 == 1) {
                    this.viewDivider2.setVisibility(8);
                }
                if (i3 == 1) {
                    this.viewDivider2.setVisibility(8);
                }
            } else if (i4 == 2) {
                if (i == 1) {
                    this.viewDivider.setVisibility(8);
                }
                if (i2 == 1) {
                    this.viewDivider2.setVisibility(8);
                }
                if (i3 == 1) {
                    this.viewDivider.setVisibility(8);
                    this.viewDivider2.setVisibility(8);
                }
            }
        }
        this.popupCameraSettings.showAsDropDown(view);
    }

    public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        if (compoundButton == this.cbFlashlight) {
            setFlashlightOn(z ? 1 : 0);
            if (this.listener != null) {
                this.listener.onSettingChange(1, z);
            }
        } else if (compoundButton == this.cbTouchTake) {
            setTouchTake(z);
        } else if (compoundButton == this.cbVolumeKeyFunction) {
            if (z) {
                this.textView.setText("音量键功能(拍照)");
            } else {
                this.textView.setText("音量键功能(焦距)");
            }
            setVolumeKeyTakePic(z);
            if (this.listener != null) {
                this.listener.onSettingChange(3, z);
            }
        }
    }

    private void setVolumeKeyTakePic(boolean z) {
        Editor edit = this.context.getSharedPreferences("SharedPreferences", 0).edit();
        edit.putBoolean(PREF_KEY_IS_VOLUMEKEY_TAKEPIC, z);
        edit.commit();
    }

    public int getFlashlightOn() {
        return this.flashlightOn;
    }

    public void setFlashlightOn(int i) {
        this.flashlightOn = i;
        Editor edit = this.context.getSharedPreferences("SharedPreferences", 0).edit();
        edit.putInt(PREF_KEY_FLASHLIGHT, i);
        edit.commit();
    }

    private void setTouchTake(boolean z) {
        Editor edit = this.context.getSharedPreferences("SharedPreferences", 0).edit();
        edit.putBoolean(PREF_KEY_TOUCH_TAKE, z);
        edit.commit();
    }

    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == 1 && this.listener != null) {
            this.listener.onSettingChange(4, true);
        }
        return true;
    }
}
