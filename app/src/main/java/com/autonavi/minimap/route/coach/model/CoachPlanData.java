package com.autonavi.minimap.route.coach.model;

import java.io.Serializable;
import java.util.ArrayList;

public class CoachPlanData implements Serializable {
    private static final long serialVersionUID = -70381867356002700L;
    public String arrNameList;
    public String bsid;
    public int code;
    public int count;
    public String depNameList;
    public String hasShiftType;
    public String isEnlargeCity;
    public ArrayList<CoachPlanItem> planItems;
    public int service_switch;
    public int ticketshow;
    public int why;
}
