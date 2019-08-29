package com.autonavi.minimap.route.train.model;

public class TrainTicketGeneralInfoItem extends TrainTicketBaseItem {
    private static final long serialVersionUID = -4661676789783783276L;
    public String departure;
    public String destination;
    public String trainName;

    public String toString() {
        StringBuilder sb = new StringBuilder("[train name = ");
        sb.append(this.trainName);
        sb.append("]");
        return sb.toString();
    }
}
