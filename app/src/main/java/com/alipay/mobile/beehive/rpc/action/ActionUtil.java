package com.alipay.mobile.beehive.rpc.action;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;
import com.alipay.android.phone.mobilecommon.multimedia.api.MultimediaImageService;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.APDisplayer;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APImageLoadRequest;
import com.alipay.mobile.antui.dialog.AUImageDialog;
import com.alipay.mobile.beehive.R;
import com.alipay.mobile.beehive.rpc.RpcConstant;
import com.alipay.mobile.beehive.rpc.RpcUiProcessor;
import com.alipay.mobile.beehive.rpc.model.FollowAction;
import com.alipay.mobile.common.dialog.SalesPromotionLimitDialog;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import java.util.Map;

public class ActionUtil {

    public interface ImageCallback {
        void onDisplay(Drawable drawable, String str);
    }

    public static String getActionType(FollowAction action) {
        if (action == null) {
            return "";
        }
        String tType = action.triggerType;
        if (TextUtils.isEmpty(tType)) {
            return "auto";
        }
        return tType;
    }

    public static boolean equalTriggerType(FollowAction action, String triggerType) {
        return TextUtils.equals(getActionType(action), triggerType);
    }

    public static String getAlertBtnText(RpcUiProcessor rr, String key, FollowAction action) {
        Map extInfo = action.extInfo;
        if (extInfo == null) {
            return "";
        }
        String result = extInfo.get(key);
        if (TextUtils.isEmpty(result)) {
            if (TextUtils.equals(key, ActionConstant.MAIN_BTN_TEXT)) {
                result = extInfo.get(ActionConstant.OLD_MAIN_BTN_TEXT);
            } else if (TextUtils.equals(key, ActionConstant.SUB_BTN_TEXT)) {
                result = extInfo.get(ActionConstant.OLD_SUB_BTN_TEXT);
            }
        }
        if (!TextUtils.isEmpty(result)) {
            return result;
        }
        if (!TextUtils.equals(key, ActionConstant.MAIN_BTN_TEXT) && !TextUtils.equals(key, ActionConstant.OLD_MAIN_BTN_TEXT)) {
            return result;
        }
        try {
            return rr.getActivity().getResources().getString(R.string.confirm);
        } catch (Exception ex) {
            LoggerFactory.getTraceLogger().warn((String) RpcConstant.TAG, (Throwable) ex);
            return result;
        }
    }

    public static Object getAlertClickListener(final DefaultActionProcessor p, boolean isMainOrSubBtn, String btnText, final RpcUiProcessor rr, final FollowAction action) {
        final String triggerType = isMainOrSubBtn ? ActionConstant.TRIGGER_TYPE_MAIN_CLICK : ActionConstant.TRIGGER_TYPE_SUB_CLICK;
        final Runnable r = new Runnable() {
            public final void run() {
                p.runTriggerActions(rr, action, triggerType);
            }
        };
        if (!TextUtils.equals(action.type, "alert")) {
            return null;
        }
        if (!TextUtils.isEmpty(btnText)) {
            return new OnClickListener() {
                public final void onClick(DialogInterface dialog, int which) {
                    r.run();
                }
            };
        }
        return null;
    }

    public static void loadImg(String url, final SalesPromotionLimitDialog dialog) {
        loadImgWithCallback(url, new ImageCallback() {
            public final void onDisplay(Drawable drawable, String path) {
                if (drawable != null) {
                    dialog.setLogoBackground(drawable);
                }
            }
        });
    }

    public static void loadImg(String url, final AUImageDialog dialog) {
        loadImgWithCallback(url, new ImageCallback() {
            public final void onDisplay(Drawable drawable, String path) {
                if (drawable != null) {
                    dialog.getLogoImageView().setImageDrawable(drawable);
                }
            }
        });
    }

    private static void loadImgWithCallback(String url, final ImageCallback callback) {
        MultimediaImageService imgService = (MultimediaImageService) LauncherApplicationAgent.getInstance().getMicroApplicationContext().getExtServiceByInterface(MultimediaImageService.class.getName());
        if (imgService != null) {
            APImageLoadRequest request = new APImageLoadRequest();
            request.path = url;
            request.displayer = new APDisplayer() {
                public final void display(View arg0, Drawable drawable, String arg2) {
                    callback.onDisplay(drawable, arg2);
                }
            };
            imgService.loadImage(request, (String) "beehive-rpc");
        }
    }
}
