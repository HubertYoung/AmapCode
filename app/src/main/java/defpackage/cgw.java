package defpackage;

import android.text.TextUtils;
import com.autonavi.annotation.Router;
import com.autonavi.common.PageBundle;
import com.autonavi.minimap.ajx3.Ajx3Page;

@Router({"driveachievement"})
/* renamed from: cgw reason: default package */
/* compiled from: DriveAchievementRouter */
public class cgw extends esk {
    public boolean start(ese ese) {
        if (!TextUtils.equals("home", ese.a.getPathSegments().get(0))) {
            return false;
        }
        PageBundle pageBundle = new PageBundle();
        pageBundle.putString("url", "path://amap_bundle_drive_achievement/src/pages/BizDriveAchievementIndex.page.js");
        startPage(Ajx3Page.class, pageBundle);
        return true;
    }
}
