package com.amap.bundle.drivecommon.model;

public final class CalcErrorType {

    public enum CalcRouteMessage {
        ;
        
        public static final int MODE_OFFLINE_FIRST = 1;
        public static final int MODE_ONLINE_FIRST = 0;
        public int typeCode;

        public abstract String message();

        public abstract void toastMessege();

        private CalcRouteMessage(int i) {
            this.typeCode = Integer.MIN_VALUE;
            this.typeCode = i;
        }

        public void toastMessege(int i) {
            toastMessege();
        }

        public String message(int i) {
            return message();
        }
    }
}
