package project.project.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "messages")
public class Message {

    @Id
    private String id;
    private int senderId;

    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public int getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(int receiverId) {
        this.receiverId = receiverId;
    }

    private int receiverId;
    private String sender;
    private String receiver;
    private String message;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
    private LocalDateTime date = LocalDateTime.now();
    private boolean read;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
    private LocalDateTime readTimestamp;

    // Getter ve Setter metotları

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }



    public String getReceiverId(String receiverId) {
        return receiverId;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public LocalDateTime getReadTimestamp() {
        return readTimestamp;
    }

    public void setReadTimestamp(LocalDateTime readTimestamp) {
        this.readTimestamp = readTimestamp;
    }


    @Override
    public String toString() {
        return "Message{" +
                "id='" + id + '\'' +
                ", senderId='" + senderId + '\'' +
                ", receiverId='" + receiverId + '\'' +
                ", sender='" + sender + '\'' +
                ", receiver='" + receiver + '\'' +
                ", message='" + message + '\'' +
                ", date=" + date +
                ", read=" + read +
                ", readTimestamp=" + readTimestamp +
                '}';
    }
}