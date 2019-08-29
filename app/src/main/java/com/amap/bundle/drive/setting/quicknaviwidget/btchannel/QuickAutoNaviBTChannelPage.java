package com.amap.bundle.drive.setting.quicknaviwidget.btchannel;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import com.alipay.sdk.sys.a;
import com.amap.bundle.drive.common.speaker.SpeakerPlayManager;
import com.amap.bundle.drivecommon.mvp.view.DriveBasePage;
import com.amap.bundle.drivecommon.tools.DriveSpUtil;
import com.amap.bundle.statistics.util.LogUtil;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;
import com.autonavi.widget.ui.TitleBar;
import org.json.JSONException;
import org.json.JSONObject;

public class QuickAutoNaviBTChannelPage extends DriveBasePage<qq> implements OnClickListener, OnCheckedChangeListener {
    public static final String BUNDLE_KEY_SELECTED_TYPE = "selected_type";
    private TextView mMediaChannel;
    SpeakerPlayManager mSpeakerPlayManager = null;
    private TextView mTelephonyChannel;

    public void onCreate(Context context) {
        super.onCreate(context);
        setContentView(R.layout.navi_settings_bt_channel);
        initSpeakerMode();
    }

    private void initSpeakerMode() {
        this.mSpeakerPlayManager = new SpeakerPlayManager(AMapPageUtil.getAppContext());
        this.mSpeakerPlayManager.d();
    }

    public void onClick(View view) {
        if (this.mMediaChannel == view) {
            setBTChannel(DriveSpUtil.BT_CHANNEL_MEDIA);
            actionLog(DriveSpUtil.BT_CHANNEL_MEDIA);
            return;
        }
        if (this.mTelephonyChannel == view) {
            setBTChannel(DriveSpUtil.BT_CHANNEL_TELEPHONY);
            actionLog(DriveSpUtil.BT_CHANNEL_TELEPHONY);
        }
    }

    private void actionLog(int i) {
        String str = "媒体声道";
        try {
            if (i == DriveSpUtil.BT_CHANNEL_TELEPHONY) {
                str = "电话声道";
            }
            JSONObject createJSONObj = LogUtil.createJSONObj(str);
            createJSONObj.put("from", a.j);
            LogUtil.actionLogV2("P00381", "B002", createJSONObj);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setBTChannel(int i) {
        boolean z = false;
        if (i == DriveSpUtil.BT_CHANNEL_MEDIA) {
            this.mMediaChannel.setSelected(true);
            this.mTelephonyChannel.setSelected(false);
        } else if (i == DriveSpUtil.BT_CHANNEL_TELEPHONY) {
            this.mMediaChannel.setSelected(false);
            this.mTelephonyChannel.setSelected(true);
        }
        if (i == DriveSpUtil.BT_CHANNEL_TELEPHONY) {
            z = true;
        }
        this.mSpeakerPlayManager.a(z);
        re.b((String) "speaker_paly_sound", z);
    }

    public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        if (this.mMediaChannel == compoundButton) {
            re.b((String) "speaker_paly_sound", true);
            return;
        }
        if (this.mTelephonyChannel == compoundButton) {
            re.b((String) "speaker_paly_sound", false);
        }
    }

    /* access modifiers changed from: protected */
    public qq createPresenter() {
        return new qq(this);
    }

    public void initView() {
        this.mMediaChannel = (TextView) findViewById(R.id.checkbox_media_channel);
        this.mTelephonyChannel = (TextView) findViewById(R.id.checkbox_telephony_channel);
        ((TitleBar) findViewById(R.id.title)).setOnBackClickListener((OnClickListener) new OnClickListener() {
            public final void onClick(View view) {
                QuickAutoNaviBTChannelPage.this.finish();
            }
        });
    }

    public void setListeners() {
        this.mMediaChannel.setOnClickListener(this);
        this.mTelephonyChannel.setOnClickListener(this);
    }

    public void checkBundle() {
        getArguments();
    }

    public void releaseSpeakerPlayManager() {
        if (this.mSpeakerPlayManager != null) {
            this.mSpeakerPlayManager.c();
        }
    }

    public void setData() {
        boolean a = re.a((String) "speaker_paly_sound", false);
        this.mMediaChannel.setSelected(!a);
        this.mTelephonyChannel.setSelected(a);
    }
}
