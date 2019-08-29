package com.autonavi.bundle.uitemplate.mapwidget.widget.msg;

import android.text.TextUtils;
import android.view.View;
import com.amap.bundle.statistics.LogManager;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.bundle.uitemplate.mapwidget.widget.BaseMapWidgetPresenter;
import com.autonavi.minimap.R;
import com.autonavi.minimap.basemap.traffic.TrafficUtil;
import com.autonavi.minimap.bundle.msgbox.dispatcher.AbsMsgBoxDispatcher;
import com.autonavi.minimap.bundle.msgbox.entity.AmapMessage;
import com.autonavi.minimap.widget.UPMarqueeView.OnItemClickListener;
import com.autonavi.sdk.log.util.LogConstant;
import org.json.JSONException;
import org.json.JSONObject;

public class MsgGroupWidgetPresenter extends BaseMapWidgetPresenter<MsgGroupMapWidget> implements OnItemClickListener {
    private AbsMsgBoxDispatcher mMsgBoxDispatcher;

    public void setMsgBoxDispatcher(AbsMsgBoxDispatcher absMsgBoxDispatcher) {
        this.mMsgBoxDispatcher = absMsgBoxDispatcher;
    }

    public void bindListener() {
        setWidgetEventIndex(((MsgGroupMapWidget) this.mBindWidget).getTipsFrameLayout(), 0);
        setWidgetEventIndex(((MsgGroupMapWidget) this.mBindWidget).getTipClearView(), 1);
        onBindListener(((MsgGroupMapWidget) this.mBindWidget).getTipClearView(), ((MsgGroupMapWidget) this.mBindWidget).getTipsFrameLayout());
    }

    public void internalClickListener(View view) {
        int id = view == null ? -1 : view.getId();
        if (!(-1 == id || this.mMsgBoxDispatcher == null)) {
            if (id != R.id.msgbox_popup_clear || !(view.getTag() instanceof String) || !AbsMsgBoxDispatcher.MARQUEE_TIPS.equals((String) view.getTag())) {
                AmapMessage amapMessage = null;
                if (view.getTag() instanceof AmapMessage) {
                    amapMessage = (AmapMessage) view.getTag();
                }
                if (amapMessage != null) {
                    if (id == R.id.msgbox_tiao_fl) {
                        this.mMsgBoxDispatcher.dismissTipsView();
                        this.mMsgBoxDispatcher.clearTipTimer();
                        IMsgGroupEventDelegate iMsgGroupEventDelegate = (IMsgGroupEventDelegate) getEventDelegate();
                        if (iMsgGroupEventDelegate != null) {
                            iMsgGroupEventDelegate.executeAction(amapMessage);
                            iMsgGroupEventDelegate.setRead(amapMessage);
                        }
                        logUpdate(LogConstant.MAIN_MSGBOX_TIP_CLICK, amapMessage);
                        if (!TextUtils.isEmpty(amapMessage.actionUri) || !TextUtils.isEmpty(amapMessage.id)) {
                            a.k();
                        }
                        logBlueBarClick(this.mMsgBoxDispatcher.getMarqueeIndex(), amapMessage.isEmergencyNews());
                        this.mMsgBoxDispatcher.reportMsgClick(amapMessage);
                    } else if (id == R.id.msgbox_popup_clear) {
                        this.mMsgBoxDispatcher.dismissTipsView();
                        IMsgGroupEventDelegate iMsgGroupEventDelegate2 = (IMsgGroupEventDelegate) getEventDelegate();
                        if (iMsgGroupEventDelegate2 != null) {
                            iMsgGroupEventDelegate2.setShowOnMap(amapMessage);
                        }
                        logUpdate("B030", amapMessage);
                        this.mMsgBoxDispatcher.reportMsgClose(amapMessage);
                    } else if (id == R.id.msgbox_bubble_up_rl) {
                        onBubbleViewClick(amapMessage);
                    }
                }
            } else {
                this.mMsgBoxDispatcher.dismissTipsView();
                this.mMsgBoxDispatcher.clearTipTimer();
            }
        }
    }

    private void logUpdate(String str, AmapMessage amapMessage) {
        String str2;
        JSONObject jSONObject = new JSONObject();
        IMsgGroupEventDelegate iMsgGroupEventDelegate = (IMsgGroupEventDelegate) getEventDelegate();
        try {
            jSONObject.put("category", amapMessage.id);
            jSONObject.put("name", getMsgBarText(amapMessage));
            if (iMsgGroupEventDelegate == null) {
                str2 = "";
            } else {
                str2 = iMsgGroupEventDelegate.getCurHourInterval();
            }
            jSONObject.put("time", str2);
            jSONObject.put("type", amapMessage.id);
            jSONObject.put("status", 1);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        LogManager.actionLogV2("P00001", str, jSONObject);
    }

    public void onItemClick(int i, View view) {
        if (this.mMsgBoxDispatcher != null) {
            AmapMessage amapMessage = (AmapMessage) view.getTag();
            if (amapMessage != null) {
                IMsgGroupEventDelegate iMsgGroupEventDelegate = (IMsgGroupEventDelegate) getEventDelegate();
                if (iMsgGroupEventDelegate != null) {
                    iMsgGroupEventDelegate.executeAction(amapMessage);
                    iMsgGroupEventDelegate.setRead(amapMessage);
                }
                this.mMsgBoxDispatcher.dismissTipsView();
                this.mMsgBoxDispatcher.clearTipTimer();
                logBlueBarClick(this.mMsgBoxDispatcher.getMarqueeIndex(), amapMessage.isEmergencyNews());
            }
        }
    }

    private void logBlueBarClick(int i, boolean z) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("name", i + 1);
            jSONObject.put("type", z ? "紧急" : "常规");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        StringBuilder sb = new StringBuilder("B246----曝光---nameValue：");
        sb.append(i);
        sb.append("----json:");
        sb.append(jSONObject.toString());
        bez.b("----redesign--msgbox-->", sb.toString(), new bew[0]);
        LogManager.actionLogV2("P00001", LogConstant.BLUE_BAR_CLICK, jSONObject);
    }

    private void onBubbleViewClick(AmapMessage amapMessage) {
        String str;
        String str2;
        String str3;
        if (this.mMsgBoxDispatcher != null) {
            IMsgGroupEventDelegate iMsgGroupEventDelegate = (IMsgGroupEventDelegate) getEventDelegate();
            if (iMsgGroupEventDelegate != null) {
                iMsgGroupEventDelegate.setRead(amapMessage);
                iMsgGroupEventDelegate.setSubRead(amapMessage.id);
                iMsgGroupEventDelegate.executeAction(amapMessage);
                iMsgGroupEventDelegate.setCurDispBubbleMsg();
            }
            if (amapMessage.location == 0) {
                JSONObject jSONObject = new JSONObject();
                try {
                    jSONObject.put("category", amapMessage.id);
                    jSONObject.put(TrafficUtil.KEYWORD, "0");
                    jSONObject.put("name", !TextUtils.isEmpty(amapMessage.title) ? amapMessage.title : "");
                    if (iMsgGroupEventDelegate == null) {
                        str3 = "";
                    } else {
                        str3 = iMsgGroupEventDelegate.getCurHourInterval();
                    }
                    jSONObject.put("time", str3);
                    jSONObject.put("status", 1);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                LogManager.actionLogV2("P00001", "B033", jSONObject);
            }
            if (amapMessage.location == 1 || amapMessage.location == 2) {
                JSONObject jSONObject2 = new JSONObject();
                try {
                    jSONObject2.put("category", amapMessage.id);
                    jSONObject2.put(TrafficUtil.KEYWORD, "1");
                    jSONObject2.put("name", !TextUtils.isEmpty(amapMessage.title) ? amapMessage.title : "");
                    if (iMsgGroupEventDelegate == null) {
                        str2 = "";
                    } else {
                        str2 = iMsgGroupEventDelegate.getCurHourInterval();
                    }
                    jSONObject2.put("time", str2);
                    jSONObject2.put("status", 1);
                } catch (JSONException e2) {
                    e2.printStackTrace();
                }
                LogManager.actionLogV2("P00001", "B033", jSONObject2);
            }
            if (amapMessage.location == 3) {
                JSONObject jSONObject3 = new JSONObject();
                try {
                    jSONObject3.put("category", amapMessage.id);
                    jSONObject3.put(TrafficUtil.KEYWORD, "3");
                    jSONObject3.put("name", !TextUtils.isEmpty(amapMessage.title) ? amapMessage.title : "");
                    if (iMsgGroupEventDelegate == null) {
                        str = "";
                    } else {
                        str = iMsgGroupEventDelegate.getCurHourInterval();
                    }
                    jSONObject3.put("time", str);
                    jSONObject3.put("status", 1);
                } catch (JSONException e3) {
                    e3.printStackTrace();
                }
                LogManager.actionLogV2("P00001", "B033", jSONObject3);
            }
        }
    }

    private String getMsgBarText(AmapMessage amapMessage) {
        String string = AMapAppGlobal.getApplication().getResources().getString(R.string.amap_app_name);
        if (amapMessage == null) {
            return string;
        }
        if (!TextUtils.isEmpty(amapMessage.descMessage)) {
            string = amapMessage.descMessage;
        }
        return ((amapMessage.id == null || (!amapMessage.id.contentEquals(AmapMessage.TOKEN_UPDATE_APP) && !amapMessage.id.contentEquals(AmapMessage.TOKEN_DOWNLOAD_SEAR_MAP) && !amapMessage.id.contentEquals(AmapMessage.TOKEN_TAOBAO_LOGIN))) && !TextUtils.isEmpty(amapMessage.showBody)) ? amapMessage.showBody : string;
    }
}
