package com.alipay.android.phone.mobilesdk.permission.guide;

import com.alipay.android.phone.mobilesdk.permission.a.a.C0094a;
import com.alipay.android.phone.mobilesdk.permission.sdk.PermissionGuideSdk.DefaultGuideConfigure;

/* compiled from: AlipayWalletGuideConfigure */
public final class a implements DefaultGuideConfigure {
    public final String getDefaultGuideTitle(PermissionType permissionType) {
        int id;
        switch (permissionType) {
            case LBS:
                id = C0094a.title_lbs;
                break;
            case LBSSERVICE:
                id = C0094a.title_lbsservice;
                break;
            case CAMERA:
                id = C0094a.title_camera;
                break;
            case SHINFO:
                id = C0094a.title_shinfo;
                break;
            case SHORTCUT:
                id = C0094a.title_shortcut;
                break;
            case MICROPHONE:
                id = C0094a.title_microphone;
                break;
            case ADDRESSBOOK:
                id = C0094a.title_addressbook;
                break;
            case NOTIFICATION:
                id = C0094a.title_notification;
                break;
            default:
                id = 0;
                break;
        }
        if (id == 0) {
            return "";
        }
        return g.a(id);
    }

    public final String getDefaultGuideContent(PermissionType permissionType) {
        int id;
        switch (permissionType) {
            case LBS:
                id = C0094a.content_lbs;
                break;
            case LBSSERVICE:
                id = C0094a.content_lbsservice;
                break;
            case CAMERA:
                id = C0094a.content_camera;
                break;
            case SHINFO:
                id = C0094a.content_shinfo;
                break;
            case SHORTCUT:
                id = C0094a.content_shortcut;
                break;
            case MICROPHONE:
                id = C0094a.content_microphone;
                break;
            case ADDRESSBOOK:
                id = C0094a.content_addressbook;
                break;
            case NOTIFICATION:
                id = C0094a.content_notification;
                break;
            default:
                id = 0;
                break;
        }
        if (id == 0) {
            return "";
        }
        return g.a(id);
    }

    public final String getTextForGoToSettings() {
        return g.a(C0094a.goto_setting);
    }

    public final String getTextForConfirm() {
        return g.a(C0094a.confirm);
    }
}
