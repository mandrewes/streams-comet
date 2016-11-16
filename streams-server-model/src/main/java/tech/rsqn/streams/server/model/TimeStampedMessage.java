package tech.rsqn.streams.server.model;

public class TimeStampedMessage {
    private long tsFrom;
    private long tsIn;
    private long tsOut;

    public long getTsFrom() {
        return tsFrom;
    }

    public void setTsFrom(long tsFrom) {
        this.tsFrom = tsFrom;
    }

    public long getTsIn() {
        return tsIn;
    }

    public void setTsIn(long tsIn) {
        this.tsIn = tsIn;
    }

    public long getTsOut() {
        return tsOut;
    }

    public void setTsOut(long tsOut) {
        this.tsOut = tsOut;
    }
}
