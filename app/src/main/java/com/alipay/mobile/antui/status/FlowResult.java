package com.alipay.mobile.antui.status;

import java.util.List;

public class FlowResult {
    public static final int POSITION_BOTTOM = 2;
    public static final int POSITION_MIDDLE = 1;
    public static final int POSITION_SINGLE = 3;
    public static final int POSITION_TOP = 0;
    public String mainInfoText;
    private int position;
    public int resultStatus;
    public ResultStatusIcon statusIcon;
    public int statusIconId;
    public List<String> subTitles;

    public FlowResult(int resultStatus2, ResultStatusIcon statusIcon2, String mainInfoText2, List<String> subTitles2) {
        this.resultStatus = resultStatus2;
        this.statusIcon = statusIcon2;
        this.mainInfoText = mainInfoText2;
        this.subTitles = subTitles2;
    }

    public FlowResult(int resultStatus2, int statusIconId2, String mainInfoText2, List<String> subTitles2) {
        this.resultStatus = resultStatus2;
        this.statusIconId = statusIconId2;
        this.mainInfoText = mainInfoText2;
        this.subTitles = subTitles2;
    }

    public int getPosition() {
        return this.position;
    }

    public void setPosition(int position2) {
        this.position = position2;
    }
}
