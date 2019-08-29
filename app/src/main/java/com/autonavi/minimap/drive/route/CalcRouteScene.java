package com.autonavi.minimap.drive.route;

public enum CalcRouteScene {
    ;
    
    private static final String invoker_commute = "commute";
    private static final String invoker_home = "home";
    private static final String invoker_navi = "navi";
    private static final String invoker_plan = "plan";
    public static final String invoker_taxi = "car-hailing";
    private static final String invoker_traffic = "traffic";
    private static final String invoker_traffic_item = "traffic_item";
    private static final String invoker_work = "work";

    public abstract String getAosInvoker();

    public abstract boolean isMultiRouteCachePlan();
}
