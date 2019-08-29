package com.autonavi.minimap.route.train.model;

public class TrainTicketStationInfoItem extends TrainTicketBaseItem {
    private static final long serialVersionUID = -5256445563174529196L;
    public String daysOnJourney;
    public String index;
    public String name;

    public String toString() {
        StringBuilder sb = new StringBuilder("[Index = ");
        sb.append(this.index);
        sb.append(", name = ");
        sb.append(this.name);
        sb.append("]");
        return sb.toString();
    }
}
