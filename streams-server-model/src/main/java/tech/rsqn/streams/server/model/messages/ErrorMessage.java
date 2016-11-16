package tech.rsqn.streams.server.model.messages;

public class ErrorMessage {
    private String sourceMessageId;
    private boolean error = true;
    private String title;
    private String detail;

    public ErrorMessage with(String t, String d) {
        this.title = t;
        this.detail = d;
        return this;
    }

    public ErrorMessage andSourceMessageId(String s) {
        this.sourceMessageId = s;
        return this;
    }

    public String getSourceMessageId() {
        return sourceMessageId;
    }

    public void setSourceMessageId(String sourceMessageId) {
        this.sourceMessageId = sourceMessageId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }
}
