package com.amap.bundle.drive.common.dialog;

import android.app.Dialog;
import android.os.Message;
import android.view.ViewGroup;
import com.alibaba.wireless.security.SecExceptionCode;
import com.alipay.mobile.h5container.api.H5ErrorCode;
import com.amap.bundle.drivecommon.mvp.view.DriveBasePage;
import com.autonavi.ae.AEUtil;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.map.fragmentcontainer.IViewLayer;
import com.autonavi.minimap.R;
import com.autonavi.minimap.basemap.favorites.fragment.FavoritesPointFragment;
import java.util.LinkedHashMap;
import java.util.Map;
import org.eclipse.mat.hprof.AbstractParser.Constants.DumpSegment;

public abstract class DriveDlgBaseManager {
    protected bid a;
    protected int b = 0;
    protected Map<DialogId, Object> c = new LinkedHashMap();
    protected Map<DialogId, Integer> d = new LinkedHashMap();

    public enum AlertDialogButtonStyle {
        EXIT(AMapAppGlobal.getApplication().getString(R.string.autonavi_dlg_exit_navi), null, null),
        CANCEL_CONFIRM(AMapAppGlobal.getApplication().getString(R.string.Cancel), null, AMapAppGlobal.getApplication().getString(R.string.route_navi_button_confim)),
        IGNORE_ONLINE(AMapAppGlobal.getApplication().getString(R.string.autonavi_dlg_ignore_online), null, AMapAppGlobal.getApplication().getString(R.string.autonavi_dlg_using_online)),
        EXIT_ONLINE(AMapAppGlobal.getApplication().getString(R.string.autonavi_dlg_exit_navi), null, AMapAppGlobal.getApplication().getString(R.string.autonavi_dlg_using_online)),
        EXIT_TRY(AMapAppGlobal.getApplication().getString(R.string.autonavi_dlg_exit_navi), null, AMapAppGlobal.getApplication().getString(R.string.autonavi_dlg_try_now)),
        SERACH_AROUND(AMapAppGlobal.getApplication().getString(R.string.autonavi_dlg_no_need), null, AMapAppGlobal.getApplication().getString(R.string.autonavi_dlg_search_around)),
        EDOG_NO_GPS(AMapAppGlobal.getApplication().getString(R.string.edog_nogps_dialog_negative_string), null, AMapAppGlobal.getApplication().getString(R.string.edog_nogps_dialog_positive_string));
        
        public String leftButtonString;
        public String middleButtonString;
        public String rightButtonString;

        private AlertDialogButtonStyle(String str, String str2, String str3) {
            this.leftButtonString = str;
            this.middleButtonString = str2;
            this.rightButtonString = str3;
        }
    }

    public enum DialogId {
        DLG_UNKNOWN(-1),
        DLG_CHECK_NAVIGATION_PROTOCOL(801),
        DLG_TIP_LOADING(802),
        DLG_WAKE_TALK(SecExceptionCode.SEC_ERROR_PKG_VALID_NO_MEMORY),
        DLG_CALC_ROUTE_ERROR(403, 1),
        DLG_ONLINE_PREFER_CALC_ROUTE_FAILED(404, 1),
        DLG_OFFLINE_PREFER_CALC_ROUTE_FAILED_BEFORE_START_NAVI(405, 1),
        DLG_OFFLINE_PREFER_CALC_ROUTE_FAILED_AFTER_START_NAVI(406, 1),
        DLG_CLEAR_VIA_POINT(407, 7),
        DLG_SEARCH_AROUND(408, 7),
        DLG_CLEAR_SINGLE_VIA_POINT(H5ErrorCode.HTTP_CONFLICT, 7),
        DLG_MULTI_ROUTE_SWITCH_BY_USER_SUCCESSFUL(H5ErrorCode.HTTP_GONE, 7),
        DLG_MULTI_ROUTE_SWITCH_BY_USER_FAILED(H5ErrorCode.HTTP_LENGTH_REQUIRED, 7),
        DLG_CHOOSE_VIA_POINT(301, 6),
        DLG_NAVIGATION_SETTINGS(302, 6),
        DLG_REPORT(303, 2),
        DLG_REPORT_DETAIL(304, 2),
        DLG_EVENT_VALIDATION_CHECK(SecExceptionCode.SEC_ERROR_STA_INCORRECT_DATA_FILE_DATA, 2),
        DLG_TRY_ONLINE(SecExceptionCode.SEC_ERROR_STA_KEY_NOT_EXISTED, 2),
        DLG_CHOOSE_PARKING(201, 4),
        DLG_TRAFFIC_EVENT_JAM(202, 4),
        DLG_REPORT_SUCCESS_AND_ADD_INFO(203, 0),
        DLG_TRAFFIC_INCIDENT(204, 0),
        DLG_SPECIAL_ETA_INCIDENT_REPORT(205, 0),
        DLG_DATA_MINING_TRAFFIC_EVENT(206, 0),
        DLG_RESTRICT_INFO(207, 0),
        DLG_DEFAULT_LZL(208, 0),
        DLG_TIP_GPS_WEAK(101, 0),
        DLG_WEATHER_EVENT(209, 0),
        DLG_SCHEDULE_ROUTE(210, 4),
        DLG_TIP_ROUTE_PREFERENCE(102, 0),
        DLG_TIP_ROUTE_TRAFFIC_EVENT(103, 0),
        DLG_TRAFFIC_EVENT_JAM_REROUTE_SUCCESS(104, 4),
        DLG_TRAFFIC_EVENT_LIMIT_LINE(105, 0),
        DLG_TRAFFIC_EVENT_DAMAGED_ROAD(106, 0),
        DLG_TRAFFIC_EVENT_LIMIT_FORBID(107, 0),
        DLG_REPORT_DONE(108, 0),
        DLG_DATA_MINING_TRAFFIC_EVENT_REPORT_SUCCESS(109, 0),
        DLG_RESTRICT_INFO_REMIND(110, 0),
        DLG_RESTRICT_IN_NAVI_REMIND(111, 0),
        DLG_SAFEHOME_SHARE_SUCCESS_INFO(112, 0),
        DLG_TIP_AGROUP(113, 0),
        DLG_REPORT_DONE_NOT_EXIST(114, 0),
        DLG_TRUCK_NAVI_LIMIT(115, 0),
        DLG_SCHEDULE_ROUTE_SUCCESS(116, 4),
        COMMUTE_DLG_EXIT(441),
        COMMUTE_DLG_REROUTE_ONLINE_FAIL(442),
        COMMUTE_DLG_REROUTE_OFFLINE(443),
        COMMUTE_DLG_TRAFFIC_EVENT_JAM(FavoritesPointFragment.REQUEST_HOME),
        COMMUTE_DLG_CONGEST(DumpSegment.ANDROID_ROOT_VM_INTERNAL),
        EDOG_DLG_GUIDE(871),
        EDOG_DLG_EXIT(471, 3),
        EDOG_DLG_SETTING(371, 2),
        EDOG_DLG_NO_GPS(372, 3),
        EDOG_DLG_REPORT(373, 2),
        EDOG_DLG_REPORT_DETAIL(374, 2),
        EDOG_DLG_REPORT_SUCCESS(271, 0),
        EDOG_DLG_REPORT_DONE(171, 0),
        PASSPHRASE_CODE_TYPE_ACTIVITIES(172, 0);
        
        private int attributes;
        private int value;

        public final int getValue() {
            return this.value;
        }

        private DialogId(int i) {
            this.value = i;
        }

        private DialogId(int i, int i2) {
            this.value = i;
            this.attributes = i2;
        }

        public final boolean getBlocker() {
            return (this.attributes & 1) > 0;
        }

        public final boolean getActiveLaunched() {
            return (this.attributes & 2) > 0;
        }

        public final boolean getChangeMap() {
            return (this.attributes & 4) > 0;
        }
    }

    public interface a {
        void a();
    }

    public DriveDlgBaseManager(bid bid) {
        if (bid == null) {
            throw new NullPointerException("page is null in DriveDialogManager's Constructor");
        }
        this.a = bid;
    }

    public final boolean a(DialogId dialogId) {
        Object obj = this.c.get(dialogId);
        if (AEUtil.IS_DEBUG) {
            ku a2 = ku.a();
            StringBuilder sb = new StringBuilder("[DriveDialogManager]#isDialogShowing#dialogId:");
            sb.append(dialogId);
            sb.append("#dialog:");
            sb.append(obj);
            a2.c("NaviMonitor", sb.toString());
        }
        if (obj != null) {
            boolean z = obj instanceof nh ? ((nh) obj).a() : obj instanceof Dialog ? ((Dialog) obj).isShowing() : (!(obj instanceof IViewLayer) || this.a == null) ? (obj instanceof DriveBasePage) && !((DriveBasePage) obj).isDriveBasePageFinish() : this.a.isViewLayerShowing((IViewLayer) obj);
            if (z) {
                return true;
            }
        }
        return false;
    }

    public final boolean b(DialogId dialogId) {
        if (a(dialogId) && this.a != null && this.a.getActivity() != null && !this.a.getActivity().isFinishing() && a(dialogId)) {
            Object obj = this.c.get(dialogId);
            if (AEUtil.IS_DEBUG) {
                ku a2 = ku.a();
                StringBuilder sb = new StringBuilder("[DriveDialogManager]#dismiss#type:");
                sb.append(dialogId);
                sb.append("#dialog:");
                sb.append(obj);
                a2.c("NaviMonitor", sb.toString());
            }
            if (obj instanceof nh) {
                nh nhVar = (nh) obj;
                if (nhVar.b() && nhVar.a()) {
                    bid bid = (bid) nhVar.b.get();
                    if (!(bid == null || bid.getContentView() == null)) {
                        ((ViewGroup) bid.getContentView()).removeView(nhVar.c);
                    }
                }
                if (nhVar.c != null) {
                    nhVar.c.setOnTouchListener(null);
                }
                nhVar.d = false;
                if (nhVar.a != null) {
                    Message.obtain(nhVar.a).sendToTarget();
                }
            }
            if (obj instanceof Dialog) {
                ((Dialog) obj).dismiss();
            }
            if (obj instanceof DriveBasePage) {
                ((DriveBasePage) obj).finish();
            }
            if (obj instanceof IViewLayer) {
                this.a.dismissViewLayer((IViewLayer) obj);
            }
        }
        this.c.remove(dialogId);
        return true;
    }

    public final void a(bid bid) {
        this.a = bid;
    }
}
