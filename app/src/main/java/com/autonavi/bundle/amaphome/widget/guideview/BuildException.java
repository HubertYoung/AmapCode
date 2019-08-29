package com.autonavi.bundle.amaphome.widget.guideview;

public class BuildException extends RuntimeException {
    private static final long serialVersionUID = 6208777692136933357L;
    private final String mDetailMessage;

    public BuildException() {
        this.mDetailMessage = "General error.";
    }

    public BuildException(String str) {
        this.mDetailMessage = str;
    }

    public String getMessage() {
        StringBuilder sb = new StringBuilder("Build GuideFragment failed: ");
        sb.append(this.mDetailMessage);
        return sb.toString();
    }
}
