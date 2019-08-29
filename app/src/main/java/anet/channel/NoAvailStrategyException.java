package anet.channel;

public class NoAvailStrategyException extends RuntimeException {
    private static final long serialVersionUID = 1;

    public NoAvailStrategyException(String str) {
        super(str);
    }
}
