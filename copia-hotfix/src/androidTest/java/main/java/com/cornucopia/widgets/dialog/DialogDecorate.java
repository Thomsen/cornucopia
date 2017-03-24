package com.cornucopia.widgets.dialog;

/**
 * 设置Dialog中的一些内容
 * @author Thomsen
 * @version 1.0
 * @since 12/2/12 3:39 PM
 */
public class DialogDecorate {

    private String title;

    private String message;

    private String okContent;

    private String cancelContent;

    private String discardContent;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getOkContent() {
        return okContent;
    }

    public void setOkContent(String okContent) {
        this.okContent = okContent;
    }

    public String getCancelContent() {
        return cancelContent;
    }

    public void setCancelContent(String cancelContent) {
        this.cancelContent = cancelContent;
    }

    public String getDiscardContent() {
        return discardContent;
    }

    public void setDiscardContent(String discardContent) {
        this.discardContent = discardContent;
    }
}
