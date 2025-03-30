package project.project.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.List;

@Document(collection = "calls")
public class Call {

    @Id
    private String id;  // MongoDB '_id' field

    private String callerUsername; // Arayan kullanıcı adı
    private String receiverUsername; // Aranan kullanıcı adı
    private String receiverId;
    private String callerId;
    private String callStatus;  // "ongoing", "completed", "rejected"
    private Long startTime; // Başlama zamanı (epoch timestamp)
    private Long endTime;   // Bitiş zamanı (epoch timestamp)
    private Long duration;  // Çağrı süresi (saniye cinsinden)

    public Call() {}

    public Call(String callerUsername, String receiverUsername,Instant timestamp ) {
        this.callerUsername = callerUsername;
        this.receiverUsername = receiverUsername;
        this.timestamp= timestamp;

    }
    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public void setCallerId(String callerId) {
        this.callerId = callerId;
    }

    private Instant timestamp; // Çağrının oluşturulma zamanı

    // WebRTC Signaling Bilgisi
    private String offer;  // SDP teklif
    private String answer; // SDP yanıtı
    private List<String> candidates;  // ICE candidates

    private boolean isAnswered; // Çağrı cevaplandı mı?

    // Getter ve Setter Metotları
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getReceiverId() {
        return receiverId;
    }
    public String getCallerUsername() {
        return callerUsername;
    }

    public void setCallerUsername(String callerUsername) {
        this.callerUsername = callerUsername;
    }

    public String getReceiverUsername() {
        return receiverUsername;
    }

    public void setReceiverUsername(String receiverUsername) {
        this.receiverUsername = receiverUsername;
    }

    public String getCallStatus() {
        return callStatus;
    }

    public void setCallStatus(String callStatus) {
        this.callStatus = callStatus;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public String getOffer() {
        return offer;
    }

    public void setOffer(String offer) {
        this.offer = offer;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public List<String> getCandidates() {
        return candidates;
    }

    public void setCandidates(List<String> candidates) {
        this.candidates = candidates;
    }


    @Override
    public String toString() {
        return "Call{" +
                "id='" + id + '\'' +
                ", callerUsername='" + callerUsername + '\'' +
                ", receiverUsername='" + receiverUsername + '\'' +
                ", callStatus='" + callStatus + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", duration=" + duration +
                ", timestamp=" + timestamp +
                ", offer='" + offer + '\'' +
                ", answer='" + answer + '\'' +
                ", candidates=" + candidates +
                ", isAnswered=" + isAnswered +
                '}';
    }

    public boolean isAnswered() {
        return isAnswered;
    }

    public void setAnswered(boolean answered) {
        isAnswered = answered;
    }

    public void setCallId(String id) {
        this.id = id;
    }

    public String getCallerId() {
        return callerId;
    }



    // Çağrı Durumu Sabitleri
    public static class CallStatus {
        public static final String ONGOING = "ongoing";
        public static final String COMPLETED = "completed";
        public static final String REJECTED = "rejected";
    }
}
