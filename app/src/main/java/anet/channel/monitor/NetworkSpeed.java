package anet.channel.monitor;

public enum NetworkSpeed {
    Slow("弱网络", 1),
    Fast("强网络", 5);
    
    private final int code;
    private final String desc;

    private NetworkSpeed(String str, int i) {
        this.desc = str;
        this.code = i;
    }

    public final String getDesc() {
        return this.desc;
    }

    public final int getCode() {
        return this.code;
    }

    public static NetworkSpeed valueOfCode(int i) {
        return i == 1 ? Slow : Fast;
    }
}
