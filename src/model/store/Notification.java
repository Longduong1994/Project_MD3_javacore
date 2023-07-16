package model.store;

import java.io.Serializable;
import java.util.Date;

public class Notification implements Serializable {
    private int id;
    private String title;
    private String content;
    private String sender;
    private String receiver;
    private Date timestamp;
    private boolean status;
    private String type;
    private String reportedUser;

    public Notification() {
    }

    public Notification(int id, String title, String content, String sender, String receiver, Date timestamp, boolean status, String type) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.sender = sender;
        this.receiver = receiver;
        this.timestamp = timestamp;
        this.status = status;
        this.type = type;
    }

    public Notification(int id, String title, String content, String sender, String receiver, Date timestamp, boolean status, String type, String reportedUser) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.sender = sender;
        this.receiver = receiver;
        this.timestamp = timestamp;
        this.status = status;
        this.type = type;
        this.reportedUser = reportedUser;
    }

    public void date(){
        Date date = new Date();
    }
    public void updateTimestamp() {
        this.timestamp = new Date();
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getReportedUser() {
        return reportedUser;
    }

    public void setReportedUser(String reportedUser) {
        this.reportedUser = reportedUser;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", sender='" + sender + '\'' +
                ", receiver='" + receiver + '\'' +
                ", timestamp=" + timestamp +
                ", status=" + status +
                ", type='" + type + '\'' +
                ", reportedUser='" + reportedUser + '\'' +
                '}';
    }
}
