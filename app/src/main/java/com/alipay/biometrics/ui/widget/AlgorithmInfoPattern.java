package com.alipay.biometrics.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.alipay.android.phone.mobilecommon.biometric.a.a.c;
import com.alipay.android.phone.mobilecommon.biometric.a.a.d;
import com.alipay.mobile.security.bio.utils.DeviceUtil;

public class AlgorithmInfoPattern extends RelativeLayout {
    private TextView mTxtCode = null;
    private TextView mTxtDistance = null;
    private TextView mTxtFaceLight = null;
    private TextView mTxtFacePitch = null;
    private TextView mTxtFaceQuality = null;
    private TextView mTxtFaceSize = null;
    private TextView mTxtFaceYaw = null;
    private TextView mTxtGaussian = null;
    private TextView mTxtHasFace = null;
    private TextView mTxtIntegrity = null;
    private TextView mTxtLeftEyeOcclusion = null;
    private TextView mTxtLiveScore = null;
    private TextView mTxtMotion = null;
    private TextView mTxtRectWidth = null;
    private TextView mTxtRightEyeOcclusion = null;
    private TextView mTxtVersionName = null;

    public AlgorithmInfoPattern(Context context) {
        super(context);
        initViews();
    }

    public AlgorithmInfoPattern(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initViews();
    }

    public AlgorithmInfoPattern(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        initViews();
    }

    private void initViews() {
        View inflate = LayoutInflater.from(getContext()).inflate(d.bio_algorithm_info, this, true);
        this.mTxtHasFace = (TextView) inflate.findViewById(c.face_circle_has_face);
        this.mTxtFaceQuality = (TextView) inflate.findViewById(c.face_circle_face_quality);
        this.mTxtLiveScore = (TextView) inflate.findViewById(c.face_circle_face_live_score);
        this.mTxtFaceLight = (TextView) inflate.findViewById(c.face_circle_face_light);
        this.mTxtFaceSize = (TextView) inflate.findViewById(c.face_circle_face_size);
        this.mTxtFacePitch = (TextView) inflate.findViewById(c.face_circle_face_pitch);
        this.mTxtFaceYaw = (TextView) inflate.findViewById(c.face_circle_face_yaw);
        this.mTxtGaussian = (TextView) findViewById(c.face_circle_face_gaussian);
        this.mTxtMotion = (TextView) findViewById(c.face_circle_face_motion);
        this.mTxtIntegrity = (TextView) findViewById(c.face_circle_face_integrity);
        this.mTxtLeftEyeOcclusion = (TextView) findViewById(c.face_circle_face_left_eye_occlusion);
        this.mTxtRightEyeOcclusion = (TextView) findViewById(c.face_circle_face_right_eye_occlusion);
        this.mTxtDistance = (TextView) findViewById(c.face_circle_face_distance);
        this.mTxtRectWidth = (TextView) findViewById(c.face_circle_face_rectWidth);
        this.mTxtVersionName = (TextView) findViewById(c.smile_version_name);
        this.mTxtCode = (TextView) findViewById(c.smile_machine_code);
        this.mTxtVersionName.setText(DeviceUtil.getVersionName(getContext()));
        this.mTxtCode.setText(DeviceUtil.getUtdid(getContext()));
    }

    private void reset() {
        this.mTxtHasFace.setText("false");
        this.mTxtFaceQuality.setText("0");
        this.mTxtLiveScore.setText("0");
        this.mTxtFaceLight.setText("0");
        this.mTxtFaceSize.setText("[0,0,0,0]");
        this.mTxtFacePitch.setText("0");
        this.mTxtFaceYaw.setText("0");
        this.mTxtGaussian.setText("0");
        this.mTxtMotion.setText("0");
        this.mTxtIntegrity.setText("0");
        this.mTxtLeftEyeOcclusion.setText("0");
        this.mTxtRightEyeOcclusion.setText("0");
        this.mTxtDistance.setText("0");
        this.mTxtRectWidth.setText("0");
    }

    public void showInfo(AlgorithmInfo algorithmInfo) {
        if (algorithmInfo == null) {
            reset();
            return;
        }
        this.mTxtHasFace.setText(algorithmInfo.isHasFace());
        if (algorithmInfo.isHasFace()) {
            this.mTxtFaceQuality.setText(algorithmInfo.getQuality());
            this.mTxtFaceLight.setText(algorithmInfo.getBrightness());
            if (algorithmInfo.getFaceRegion() != null) {
                float f = algorithmInfo.getFaceRegion().left;
                float f2 = algorithmInfo.getFaceRegion().top;
                this.mTxtFaceSize.setText("x:" + f + " y:" + f2 + " width:" + (algorithmInfo.getFaceRegion().right - f) + " height:" + (algorithmInfo.getFaceRegion().bottom - f2));
            }
            this.mTxtFacePitch.setText(algorithmInfo.getPitch());
            this.mTxtFaceYaw.setText(algorithmInfo.getYaw());
            this.mTxtGaussian.setText(algorithmInfo.getGaussian());
            this.mTxtMotion.setText(algorithmInfo.getMotion());
            this.mTxtIntegrity.setText(algorithmInfo.getIntegrity());
            this.mTxtLeftEyeOcclusion.setText(algorithmInfo.getLeftEyeBlinkRatio());
            this.mTxtRightEyeOcclusion.setText(algorithmInfo.getRightEyeBlinkRatio());
            this.mTxtDistance.setText(algorithmInfo.getDistance());
            this.mTxtLiveScore.setText("-1");
            return;
        }
        reset();
    }
}
