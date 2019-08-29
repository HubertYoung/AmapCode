package com.alipay.mobile.beehive.capture.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.alipay.android.phone.mobilecommon.multimedia.api.MultimediaImageService;
import com.alipay.mobile.beehive.R;
import com.alipay.mobile.beehive.capture.modle.MediaInfo;
import com.alipay.mobile.beehive.capture.service.CaptureParam;
import com.alipay.mobile.beehive.capture.service.CaptureParam.PreviewAction;
import com.alipay.mobile.beehive.capture.service.impl.CaptureServiceImpl;
import com.alipay.mobile.beehive.capture.utils.Logger;
import com.alipay.mobile.beehive.global.impl.BeehiveBaseActivity;
import com.alipay.mobile.beehive.util.MicroServiceUtil;
import com.alipay.mobile.common.lbs.LBSLocationManagerProxy;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.framework.MicroApplicationContext;
import com.googlecode.androidannotations.api.BackgroundExecutor;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

public abstract class BaseRecordPreviewV2Activity extends BeehiveBaseActivity {
    public static final String EXTRA_KEY_MEDIA_INFO = "EXTRA_KEY_MEDIA_INFO";
    private static final String TAG = "RecordPreviewActivity";
    private LinearLayout llActionBtnZone;
    private ArrayList<PreviewAction> mActions;
    private String mBusinessId;
    private boolean mEnableContinueShooting;
    private Bundle mExtras;
    /* access modifiers changed from: private */
    public MediaInfo mediaInfo;
    private ImageView previewImg;

    /* access modifiers changed from: 0000 */
    public abstract int getLayoutId();

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        makeFullScreen();
        if (parseParams()) {
            setContentView(getLayoutId());
            this.previewImg = (ImageView) findViewById(R.id.iv_capture_image);
            renderActionButtons();
            renderViews(this.mediaInfo);
        }
    }

    private void makeFullScreen() {
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
    }

    private void renderActionButtons() {
        this.llActionBtnZone = (LinearLayout) findViewById(R.id.ll_action_btn_zone);
        renderActionButton(CaptureParam.ACTION_RE_CAPTURE, (ViewGroup) this.llActionBtnZone.getChildAt(0));
        ViewGroup v = (ViewGroup) this.llActionBtnZone.getChildAt(1);
        if (this.mEnableContinueShooting) {
            renderActionButton(CaptureParam.ACTION_ADD_ONE_MORE, v);
            renderActionButton(CaptureParam.ACTION_DONE_CAPTURE, (ViewGroup) this.llActionBtnZone.getChildAt(2));
            return;
        }
        renderActionButton(CaptureParam.ACTION_DONE_CAPTURE, v);
        ((ViewGroup) this.llActionBtnZone.getChildAt(2)).setVisibility(8);
    }

    private void renderActionButton(final String type, ViewGroup btn) {
        PreviewAction action = null;
        if (this.mActions != null && !this.mActions.isEmpty()) {
            Iterator<PreviewAction> it = this.mActions.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                PreviewAction a = it.next();
                if (TextUtils.equals(type, a.actionType)) {
                    action = a;
                    break;
                }
            }
        }
        btn.setVisibility(0);
        int bgId = R.drawable.dr_preview_btn_bg_normal;
        String actionText = getDefaultActionTextByType(type);
        if (action != null) {
            if (action.specialColor) {
                bgId = R.drawable.dr_preview_btn_bg_selected;
            }
            if (!TextUtils.isEmpty(action.actionText)) {
                actionText = action.actionText;
            }
        }
        btn.setBackgroundResource(bgId);
        ((TextView) btn.getChildAt(0)).setText(actionText);
        btn.setOnClickListener(new OnClickListener() {
            public final void onClick(View v) {
                if (CaptureParam.ACTION_ADD_ONE_MORE.equals(type)) {
                    CaptureServiceImpl.addOneMorePicToSession(BaseRecordPreviewV2Activity.this.mediaInfo);
                    BaseRecordPreviewV2Activity.this.takePictureAgain(false);
                } else if (CaptureParam.ACTION_DONE_CAPTURE.equals(type)) {
                    BaseRecordPreviewV2Activity.this.doneCapture();
                } else if (CaptureParam.ACTION_RE_CAPTURE.equals(type)) {
                    BaseRecordPreviewV2Activity.this.takePictureAgain(true);
                }
            }
        });
    }

    private String getDefaultActionTextByType(String type) {
        if (CaptureParam.ACTION_ADD_ONE_MORE.equals(type)) {
            return getString(R.string.str_add_one_more);
        }
        if (CaptureParam.ACTION_DONE_CAPTURE.equals(type)) {
            return getString(R.string.str_done);
        }
        if (CaptureParam.ACTION_RE_CAPTURE.equals(type)) {
            return getString(R.string.record_again);
        }
        return "";
    }

    /* access modifiers changed from: private */
    public void doneCapture() {
        CaptureServiceImpl.addOneMorePicToSession(this.mediaInfo);
        CaptureServiceImpl.notifyIndustryCaptureAction(false, null, true);
        finish();
    }

    /* access modifiers changed from: private */
    public void takePictureAgain(boolean deleteCurrentOne) {
        this.mExtras.remove("EXTRA_KEY_MEDIA_INFO");
        this.mExtras.putInt(CaptureParam.INIT_TYPE, 1);
        MicroApplicationContext microContext = LauncherApplicationAgent.getInstance().getMicroApplicationContext();
        CaptureV2OrientationActivity.startCaptureV2Activity(microContext.findTopRunningApp(), microContext, this, this.mExtras);
        if (deleteCurrentOne) {
            deleteImage(this.mediaInfo.path);
        }
        finish();
    }

    private void deleteImage(String path) {
        if (TextUtils.isEmpty(path)) {
            Logger.debug(TAG, "deleteImage called when path is Empty.");
        } else {
            BackgroundExecutor.execute((Runnable) new a(path));
        }
    }

    private boolean parseParams() {
        this.mExtras = getIntent().getExtras();
        if (this.mExtras == null) {
            finish();
            Logger.debug(TAG, "Extras is Null.");
            return false;
        }
        this.mEnableContinueShooting = this.mExtras.getBoolean(CaptureParam.SUPPORT_CONTINUE_SHOOTING);
        this.mediaInfo = (MediaInfo) this.mExtras.getSerializable("EXTRA_KEY_MEDIA_INFO");
        if (this.mediaInfo == null) {
            finish();
            Logger.debug(TAG, "PhotoInfo is Null.");
            return false;
        }
        this.mediaInfo.location = LBSLocationManagerProxy.getInstance().getLastKnownLocation(getApplicationContext());
        if (this.mediaInfo.location == null) {
            Logger.debug(TAG, "Get lbs location is null.");
        }
        this.mBusinessId = this.mExtras.getString("businessId");
        parseActions();
        return true;
    }

    private void parseActions() {
        Serializable o = this.mExtras.getSerializable(CaptureParam.PREVIEW_ACTIONS);
        if (o instanceof ArrayList) {
            this.mActions = (ArrayList) o;
        }
    }

    private void renderViews(MediaInfo info) {
        this.previewImg.setScaleType(ScaleType.FIT_CENTER);
        getImageService().loadOriginalImage(info.path, this.previewImg, null, null, this.mBusinessId);
    }

    private MultimediaImageService getImageService() {
        MultimediaImageService ret = (MultimediaImageService) MicroServiceUtil.getMicroService(MultimediaImageService.class);
        if (ret == null) {
            Logger.warn(TAG, "MultimediaImageService is Null.");
        }
        return ret;
    }

    public void onBackPressed() {
        takePictureAgain(true);
        super.onBackPressed();
    }
}
