package com.alipay.zoloz.toyger.workspace.alert;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import com.alipay.mobile.security.bio.constants.CodeConstants;
import com.alipay.mobile.security.bio.utils.StringUtil;
import com.alipay.zoloz.toyger.R;
import com.alipay.zoloz.toyger.extservice.record.ToygerRecordService;
import com.alipay.zoloz.toyger.interfaces.ToygerCallback;
import com.alipay.zoloz.toyger.util.DialogTypeIndex;
import com.alipay.zoloz.toyger.workspace.FaceRemoteConfig;

public class AlertTypeHelper {
    FaceRemoteConfig faceRemoteConfig;
    Context mContext;
    Resources mResources = this.mContext.getResources();
    ToygerCallback mToygerCallback;

    public class AlertContext {
        public String msg1;
        public String msg2;
        public String negative;
        public String positive;
        public int returnCode;
        public String scene;
        public boolean showIcon;
        public DialogTypeIndex tag;
        public String title;

        public AlertContext() {
        }
    }

    public AlertTypeHelper(ToygerCallback toygerCallback, Context context) {
        this.mToygerCallback = toygerCallback;
        this.mContext = context;
        this.faceRemoteConfig = toygerCallback.getRemoteConfig();
    }

    private String getAlertString(String str, String str2) {
        return StringUtil.isNullorEmpty(str) ? str2 : str;
    }

    private int getAlertCode(int i, int i2) {
        return i != -1 ? i : i2;
    }

    @SuppressLint({"StringFormatInvalid"})
    public AlertContext getAlertContext(AlertType alertType, int i) {
        AlertContext alertContext = new AlertContext();
        DialogTypeIndex dialogTypeIndex = DialogTypeIndex.DIALOG_TYPE_INDEX_INVALID;
        String str = "";
        String str2 = "";
        String str3 = "";
        String str4 = "";
        String str5 = "";
        int i2 = -1;
        Resources resources = this.mResources;
        switch (b.a[alertType.ordinal()]) {
            case 1:
                dialogTypeIndex = DialogTypeIndex.DIALOG_TYPE_INDEX_SYSTEM_ERROR;
                str = getAlertString(this.faceRemoteConfig.getFaceTips().getSystemErrorAlert().getTitle(), CodeConstants.getMessage(CodeConstants.SERVER_PARAM_ERROR));
                str2 = getAlertString(this.faceRemoteConfig.getFaceTips().getSystemErrorAlert().getMessage(), "");
                str3 = getAlertString(this.faceRemoteConfig.getFaceTips().getSystemErrorAlert().getRightButtonText(), resources.getString(R.string.face_detect_dialog_btn_ok));
                str5 = ToygerRecordService.LOAD_ALGORITHM_ERR;
                i2 = getAlertCode(this.faceRemoteConfig.getFaceTips().getSystemErrorAlert().getReturnCode(), 205);
                break;
            case 2:
                dialogTypeIndex = DialogTypeIndex.DIALOG_TYPE_INDEX_EXIT_FACE;
                str = getAlertString(this.faceRemoteConfig.getFaceTips().getExitAlert().getTitle(), resources.getString(R.string.face_detect_dialog_close_title));
                str2 = getAlertString(this.faceRemoteConfig.getFaceTips().getExitAlert().getMessage(), resources.getString(R.string.face_detect_dialog_close_msg));
                str3 = getAlertString(this.faceRemoteConfig.getFaceTips().getExitAlert().getRightButtonText(), resources.getString(R.string.face_detect_dialog_btn_sure));
                str4 = getAlertString(this.faceRemoteConfig.getFaceTips().getExitAlert().getLeftButtonText(), resources.getString(R.string.face_detect_dialog_btn_cancle));
                str5 = "clickXback";
                i2 = getAlertCode(this.faceRemoteConfig.getFaceTips().getExitAlert().getReturnCode(), 202);
                break;
            case 3:
                dialogTypeIndex = DialogTypeIndex.DIALOG_TYPE_INDEX_INTERRUPT_RESUME;
                str = getAlertString(this.faceRemoteConfig.getFaceTips().getInterruptAlert().getTitle(), resources.getString(R.string.face_detect_dialog_interrupt_error));
                str2 = getAlertString(this.faceRemoteConfig.getFaceTips().getInterruptAlert().getMessage(), "");
                str3 = getAlertString(this.faceRemoteConfig.getFaceTips().getInterruptAlert().getRightButtonText(), resources.getString(R.string.loginment_dialog_btn_retry));
                str4 = getAlertString(this.faceRemoteConfig.getFaceTips().getInterruptAlert().getLeftButtonText(), resources.getString(R.string.face_detect_dialog_btn_exit));
                str5 = "systemInterrupt";
                i2 = 301;
                break;
            case 4:
                dialogTypeIndex = DialogTypeIndex.DIALOG_TYPE_INDEX_TIMEOUT;
                str = getAlertString(this.faceRemoteConfig.getFaceTips().getTimeoutAlert().getTitle(), resources.getString(R.string.face_detect_dialog_timeout_error));
                str2 = getAlertString(this.faceRemoteConfig.getFaceTips().getTimeoutAlert().getMessage(), resources.getString(R.string.face_detect_dialog_pose_msg));
                str3 = getAlertString(this.faceRemoteConfig.getFaceTips().getTimeoutAlert().getRightButtonText(), resources.getString(R.string.loginment_dialog_btn_retry));
                str4 = getAlertString(this.faceRemoteConfig.getFaceTips().getTimeoutAlert().getLeftButtonText(), resources.getString(R.string.face_detect_dialog_btn_exit));
                str5 = "timeout";
                i2 = getAlertCode(this.faceRemoteConfig.getFaceTips().getTimeoutAlert().getReturnCode(), 202);
                break;
            case 5:
                dialogTypeIndex = DialogTypeIndex.DIALOG_TYPE_INDEX_UNSUPPORTED_CPU;
                str = getAlertString(this.faceRemoteConfig.getFaceTips().getUnsurpportAlert().getTitle(), resources.getString(R.string.face_detect_dialog_unsurpport_msg));
                str2 = getAlertString(this.faceRemoteConfig.getFaceTips().getUnsurpportAlert().getMessage(), "");
                str3 = getAlertString(this.faceRemoteConfig.getFaceTips().getUnsurpportAlert().getRightButtonText(), resources.getString(R.string.face_detect_dialog_btn_ok));
                str5 = "errorDeviceModel";
                i2 = getAlertCode(this.faceRemoteConfig.getFaceTips().getUnsurpportAlert().getReturnCode(), 102);
                break;
            case 6:
                dialogTypeIndex = DialogTypeIndex.DIALOG_TYPE_INDEX_NO_PERMISSION_OF_CAMERA;
                str = getAlertString(this.faceRemoteConfig.getFaceTips().getCameraNoPermissionAlert().getTitle(), resources.getString(R.string.face_detect_camera_unconnect_title));
                str2 = getAlertString(this.faceRemoteConfig.getFaceTips().getCameraNoPermissionAlert().getMessage(), resources.getString(R.string.face_detect_camera_unconnect_text_default));
                str4 = getAlertString(this.faceRemoteConfig.getFaceTips().getCameraNoPermissionAlert().getLeftButtonText(), resources.getString(R.string.face_detect_dialog_btn_exit));
                str3 = getAlertString(this.faceRemoteConfig.getFaceTips().getCameraNoPermissionAlert().getRightButtonText(), resources.getString(R.string.face_detect_camera_unconnect_ok_text));
                str5 = "cameraPermission";
                i2 = getAlertCode(this.faceRemoteConfig.getFaceTips().getCameraNoPermissionAlert().getReturnCode(), 100);
                break;
            case 7:
                dialogTypeIndex = DialogTypeIndex.DIALOG_TYPE_INDEX_NO_FRONT_CAMERA;
                str = getAlertString(this.faceRemoteConfig.getFaceTips().getUnsurpportAlert().getTitle(), resources.getString(R.string.face_detect_dialog_unsurpport_msg));
                str2 = getAlertString(this.faceRemoteConfig.getFaceTips().getUnsurpportAlert().getMessage(), "");
                str3 = getAlertString(this.faceRemoteConfig.getFaceTips().getUnsurpportAlert().getRightButtonText(), resources.getString(R.string.face_detect_dialog_btn_ok));
                str5 = "errorCameraFront";
                i2 = getAlertCode(this.faceRemoteConfig.getFaceTips().getUnsurpportAlert().getReturnCode(), 101);
                break;
            case 8:
                dialogTypeIndex = DialogTypeIndex.DIALOG_TYPE_INDEX_ANDROID_VERSION_LOW;
                str = getAlertString(this.faceRemoteConfig.getFaceTips().getSystemVersionErrorAlert().getTitle(), resources.getString(R.string.loginment_dialog_error_version_msg));
                str2 = getAlertString(this.faceRemoteConfig.getFaceTips().getSystemVersionErrorAlert().getMessage(), resources.getString(R.string.loginment_dialog_error_version_msg2));
                str3 = getAlertString(this.faceRemoteConfig.getFaceTips().getSystemVersionErrorAlert().getRightButtonText(), resources.getString(R.string.face_detect_dialog_btn_ok));
                str5 = "errorSystemVersion";
                i2 = getAlertCode(this.faceRemoteConfig.getFaceTips().getSystemVersionErrorAlert().getReturnCode(), 105);
                break;
            case 9:
            case 10:
                dialogTypeIndex = DialogTypeIndex.DIALOG_TYPE_INDEX_REMOTE_COMMAND_FAIL;
                str = getAlertString(this.faceRemoteConfig.getFaceTips().getFailAlert().getTitle(), resources.getString(R.string.face_detect_dialog_face_operation_error_text));
                str2 = getAlertString(this.faceRemoteConfig.getFaceTips().getFailAlert().getMessage(), resources.getString(R.string.face_detect_dialog_pose_msg));
                str3 = getAlertString(this.faceRemoteConfig.getFaceTips().getFailAlert().getRightButtonText(), resources.getString(R.string.loginment_dialog_btn_retry));
                str4 = getAlertString(this.faceRemoteConfig.getFaceTips().getFailAlert().getLeftButtonText(), resources.getString(R.string.face_detect_dialog_btn_exit));
                str5 = "serverFail";
                i2 = getAlertCode(this.faceRemoteConfig.getFaceTips().getFailAlert().getReturnCode(), 208);
                break;
            case 11:
            case 12:
                dialogTypeIndex = DialogTypeIndex.DIALOG_TYPE_INDEX_REMOTE_COMMAND_FAIL_MAX_RETRY;
                str = getAlertString(this.faceRemoteConfig.getFaceTips().getLimitAlert().getTitle(), resources.getString(R.string.face_detect_dialog_face_operation_error_text));
                str2 = getAlertString(this.faceRemoteConfig.getFaceTips().getLimitAlert().getMessage(), "");
                str3 = getAlertString(this.faceRemoteConfig.getFaceTips().getLimitAlert().getRightButtonText(), resources.getString(R.string.face_detect_dialog_btn_ok));
                str5 = "serverFail";
                i2 = getAlertCode(this.faceRemoteConfig.getFaceTips().getLimitAlert().getReturnCode(), 208);
                break;
            case 13:
                dialogTypeIndex = DialogTypeIndex.DIALOG_TYPE_INDEX_REMOTE_NETWORK_ERROR;
                str = getAlertString(this.faceRemoteConfig.getFaceTips().getNetworkErrorAlert().getTitle(), CodeConstants.getMessage(CodeConstants.NETWORK_TIMEOUT));
                str2 = getAlertString(this.faceRemoteConfig.getFaceTips().getNetworkErrorAlert().getMessage(), "");
                str3 = getAlertString(this.faceRemoteConfig.getFaceTips().getNetworkErrorAlert().getRightButtonText(), resources.getString(R.string.loginment_dialog_btn_retry));
                str4 = getAlertString(this.faceRemoteConfig.getFaceTips().getNetworkErrorAlert().getLeftButtonText(), resources.getString(R.string.face_detect_dialog_btn_exit));
                str5 = "networkFail";
                i2 = getAlertCode(this.faceRemoteConfig.getFaceTips().getNetworkErrorAlert().getReturnCode(), 207);
                break;
        }
        alertContext.tag = dialogTypeIndex;
        alertContext.msg1 = str;
        alertContext.msg2 = str2;
        alertContext.negative = str4;
        alertContext.positive = str3;
        alertContext.title = "";
        alertContext.showIcon = false;
        alertContext.scene = str5;
        alertContext.returnCode = i2;
        return alertContext;
    }
}
