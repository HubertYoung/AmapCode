package com.alipay.mobile.nebula.log;

public class H5BehaviorLogConfig {
    public static final int DEDIUM_LOG_LEVEL = 2;
    public static final String H5SECURITY_BEHAVIOUR = "H5SECURITY";
    public static final int HIGH_LOG_LEVEL = 1;
    public static final int LOW_LOG_LEVEL = 3;
    public static final String NEBULA_TCEH_BEHAVIOUR = "NebulaTech";
    public static final String WEBSTAT_BEHAVIOUR = "WEBSTAT";
    private String abTestInfo;
    private String actionId;
    private String behaviourPro;
    private String entityContentId;
    private int logLevel;
    private String pageId;
    private String userCaseId;

    public static H5BehaviorLogConfig newH5BehaviorLogConfig() {
        return new H5BehaviorLogConfig();
    }

    private H5BehaviorLogConfig() {
    }

    public H5BehaviorLogConfig setBehaviourPro(String behaviourPro2) {
        this.behaviourPro = behaviourPro2;
        return this;
    }

    public H5BehaviorLogConfig setLogLevel(int logLevel2) {
        this.logLevel = logLevel2;
        return this;
    }

    public H5BehaviorLogConfig setUserCaseId(String userCaseId2) {
        this.userCaseId = userCaseId2;
        return this;
    }

    public H5BehaviorLogConfig setAbTestInfo(String abTestInfo2) {
        this.abTestInfo = abTestInfo2;
        return this;
    }

    public H5BehaviorLogConfig setEntityContentId(String entityContentId2) {
        this.entityContentId = entityContentId2;
        return this;
    }

    public H5BehaviorLogConfig setPageId(String pageId2) {
        this.pageId = pageId2;
        return this;
    }

    public H5BehaviorLogConfig setActionId(String actionId2) {
        this.actionId = actionId2;
        return this;
    }

    public String getBehaviourPro() {
        return this.behaviourPro;
    }

    public int getLogLevel() {
        return this.logLevel;
    }

    public String getUserCaseId() {
        return this.userCaseId;
    }

    public String getAbTestInfo() {
        return this.abTestInfo;
    }

    public String getEntityContentId() {
        return this.entityContentId;
    }

    public String getPageId() {
        return this.pageId;
    }

    public String getActionId() {
        return this.actionId;
    }
}
