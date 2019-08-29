package com.amap.location.common.model;

import java.util.Hashtable;

public class Adjacent {
    public static final String BTM = "btm";
    public static final String BTM_LEFT = "btmleft";
    public static final String BTM_RIGHT = "btmright";
    public static final String EVEN = "even";
    public static final String LEFT = "left";
    public static final String ODD = "odd";
    public static final String RIGHT = "right";
    public static final String TOP = "top";
    public static final String TOP_LEFT = "topleft";
    public static final String TOP_RIGHT = "topright";

    public static class Borders {
        private static final String BTM_LEFT = "028b";
        private static final String LEFT_BTM = "0145hjnp";
        private static final String RIGHT_TOP = "bcfguvyz";
        private static final String TOP_RIGHT = "prxz";
        public static final Hashtable<String, Hashtable<String, String>> htBorders = new Hashtable<>();

        static {
            Hashtable hashtable = new Hashtable();
            hashtable.put(Adjacent.EVEN, RIGHT_TOP);
            hashtable.put(Adjacent.ODD, TOP_RIGHT);
            Hashtable hashtable2 = new Hashtable();
            hashtable2.put(Adjacent.EVEN, LEFT_BTM);
            hashtable2.put(Adjacent.ODD, BTM_LEFT);
            Hashtable hashtable3 = new Hashtable();
            hashtable3.put(Adjacent.EVEN, TOP_RIGHT);
            hashtable3.put(Adjacent.ODD, RIGHT_TOP);
            Hashtable hashtable4 = new Hashtable();
            hashtable4.put(Adjacent.EVEN, BTM_LEFT);
            hashtable4.put(Adjacent.ODD, LEFT_BTM);
            htBorders.put("top", hashtable);
            htBorders.put(Adjacent.BTM, hashtable2);
            htBorders.put("right", hashtable3);
            htBorders.put("left", hashtable4);
        }
    }

    public static class Neighbors {
        private static final String BTM_LEFT = "14365h7k9dcfesgujnmqp0r2twvyx8zb";
        private static final String LEFT_BTM = "238967debc01fg45kmstqrwxuvhjyznp";
        private static final String RIGHT_TOP = "bc01fg45238967deuvhjyznpkmstqrwx";
        private static final String TOP_RIGHT = "p0r21436x8zb9dcf5h7kjnmqesgutwvy";
        public static final Hashtable<String, Hashtable<String, String>> mapNb = new Hashtable<>();

        static {
            Hashtable hashtable = new Hashtable();
            hashtable.put(Adjacent.EVEN, RIGHT_TOP);
            hashtable.put(Adjacent.ODD, TOP_RIGHT);
            Hashtable hashtable2 = new Hashtable();
            hashtable2.put(Adjacent.EVEN, LEFT_BTM);
            hashtable2.put(Adjacent.ODD, BTM_LEFT);
            Hashtable hashtable3 = new Hashtable();
            hashtable3.put(Adjacent.EVEN, TOP_RIGHT);
            hashtable3.put(Adjacent.ODD, RIGHT_TOP);
            Hashtable hashtable4 = new Hashtable();
            hashtable4.put(Adjacent.EVEN, BTM_LEFT);
            hashtable4.put(Adjacent.ODD, LEFT_BTM);
            mapNb.put("top", hashtable);
            mapNb.put(Adjacent.BTM, hashtable2);
            mapNb.put("right", hashtable3);
            mapNb.put("left", hashtable4);
        }
    }
}
