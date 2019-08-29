package com.alipay.mobile.mrtc.api.report;

public class APStatsReport {
    public final String id;
    public final double timestamp;
    public final String type;
    public final Value[] values;

    public static class Value {
        public final String name;
        public final String value;

        public Value(String name2, String value2) {
            this.name = name2;
            this.value = value2;
        }

        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append("[").append(this.name).append(": ").append(this.value).append("]");
            return builder.toString();
        }
    }

    public APStatsReport(String id2, String type2, double timestamp2, Value[] values2) {
        this.id = id2;
        this.type = type2;
        this.timestamp = timestamp2;
        this.values = values2;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("id: ").append(this.id).append(", type: ").append(this.type).append(", timestamp: ").append(this.timestamp).append(", values: ");
        for (Value value : this.values) {
            builder.append(value.toString()).append(", ");
        }
        return builder.toString();
    }
}
