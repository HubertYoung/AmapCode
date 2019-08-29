package com.amap.bundle.planhome.page;

import com.amap.bundle.voiceservice.dispatch.IVoiceCmdResponder;
import com.autonavi.annotation.PageAction;
import com.autonavi.bundle.routecommon.model.RouteType;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.LaunchMode.launchModeSingleInstance;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.map.fragmentcontainer.page.PageContainer;
import org.json.JSONObject;

@PageAction("amap.extra.route.route")
public class PlanHomePage extends AbstractPlanHomePage implements bgm, IVoiceCmdResponder, launchModeSingleInstance {
    public void finishSelf() {
    }

    public boolean isInnerPage() {
        return false;
    }

    public long getScene() {
        RouteType a = acr.a();
        long j = 1024;
        if (this.mPresenter != null && isAlive() && (this.mPresenter instanceof add)) {
            if (((add) this.mPresenter).r() == 1) {
                j = 2;
                if (a != null) {
                    switch (a) {
                        case BUS:
                            j = 34;
                            break;
                        case CAR:
                            j = 10;
                            break;
                        case ONFOOT:
                            j = 130;
                            break;
                        case TRAIN:
                            j = 258;
                            break;
                        case RIDE:
                            j = 66;
                            break;
                        case COACH:
                            j = 514;
                            break;
                        case TAXI:
                            j = 6;
                            break;
                        case TRUCK:
                            j = 18;
                            break;
                        case ETRIP:
                            j = 137438953474L;
                            break;
                        case MOTOR:
                            j = 1125899906842626L;
                            break;
                    }
                }
            } else if (((add) this.mPresenter).r() == 2) {
                PageBundle arguments = getArguments();
                if (arguments == null || !arguments.getBoolean("key_favorites", false)) {
                    if (a != null) {
                        switch (a) {
                            case BUS:
                                j = 17408;
                                break;
                            case CAR:
                                j = 5120;
                                break;
                            case ONFOOT:
                                j = 132096;
                                break;
                            case TRAIN:
                                j = 263168;
                                break;
                            case RIDE:
                                j = 66560;
                                break;
                            case COACH:
                                j = 525312;
                                break;
                            case TAXI:
                                j = 3072;
                                break;
                            case TRUCK:
                                j = 9216;
                                break;
                            case MOTOR:
                                j = 2251799813686272L;
                                break;
                        }
                    }
                } else {
                    return 1024;
                }
            }
            return j;
        }
        j = 0;
        return j;
    }

    public bgo getPresenter() {
        return (bgo) this.mPresenter;
    }

    public long getScenesID() {
        return getScene();
    }

    public JSONObject getScenesData() {
        PageContainer pageContainer = getPageContainer();
        if (pageContainer != null) {
            AbstractBasePage cureentRecordPage = pageContainer.getCureentRecordPage();
            if (cureentRecordPage instanceof bgm) {
                return ((bgm) cureentRecordPage).getScenesData();
            }
        }
        return null;
    }

    public boolean needKeepSessionAlive() {
        PageContainer pageContainer = getPageContainer();
        if (pageContainer != null) {
            AbstractBasePage cureentRecordPage = pageContainer.getCureentRecordPage();
            if (cureentRecordPage instanceof bgm) {
                return ((bgm) cureentRecordPage).needKeepSessionAlive();
            }
        }
        return false;
    }
}
