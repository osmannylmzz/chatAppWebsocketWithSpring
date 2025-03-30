package project.project.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.project.Repository.CallRepository;
import project.project.model.Call;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class VoiceCallService {

    private Logger logger = LoggerFactory.getLogger(VoiceCallService.class);

    @Autowired
    private CallRepository callRepository;


    @Autowired
    private SimpMessagingTemplate messagingTemplate;


    public Call startCall(String initiatorUsername, String recipientUsername) {
        if (initiatorUsername == null || recipientUsername == null) {
            logger.error("Caller ve Receiver bilgileri eksik!");
            throw new IllegalArgumentException("Caller ve Receiver bilgileri eksik!");
        }

        Call call = new Call();
        call.setCallerUsername(initiatorUsername);
        call.setReceiverUsername(recipientUsername);
        call.setCallStatus(Call.CallStatus.ONGOING);
        call.setStartTime(Instant.now().toEpochMilli());
        call.setCallId(UUID.randomUUID().toString());
        call.setTimestamp(Instant.now());

        try {
            callRepository.save(call);
            logger.info("Veritabanına kaydedilen çağrı: {}", call);
        } catch (Exception e) {
            logger.error("Çağrı kaydedilirken hata oluştu: {}", e.getMessage());
            throw e; // Hatanın üst katmana iletilmesi
        }

        messagingTemplate.convertAndSendToUser(recipientUsername, "/queue/call", call);

        return call;
    }


    public void endCall(String callId) {
        Call call = callRepository.findById(callId).orElse(null);
        if (call != null) {
            call.setEndTime(Instant.now().toEpochMilli());
            call.setDuration(calculateDuration(call.getStartTime(), call.getEndTime()));
            call.setCallStatus(Call.CallStatus.COMPLETED);


            try {
                callRepository.save(call);
            } catch (Exception e) {
                System.err.println("Error saving call: " + e.getMessage());
            }


            messagingTemplate.convertAndSendToUser(call.getReceiverUsername(), "/queue/end", call);
            messagingTemplate.convertAndSendToUser(call.getCallerUsername(), "/queue/end", call);
        }
    }


    public void rejectCall(String callId) {
        Call call = callRepository.findById(callId).orElse(null);
        if (call != null) {
            call.setCallStatus(Call.CallStatus.REJECTED);

            try {
                callRepository.save(call);
            } catch (Exception e) {
                System.err.println("Error saving call: " + e.getMessage());
            }

            // Çağrıyı reddetme bildirimi gönder
            messagingTemplate.convertAndSendToUser(call.getReceiverUsername(), "/queue/reject", call);
            messagingTemplate.convertAndSendToUser(call.getCallerUsername(), "/queue/reject", call);
        }
    }

    // ICE candidate'ı işle ve veritabanına kaydet
    public void handleCandidate(String candidate, String receiverUsername) {
        Call call = callRepository.findByReceiverUsername(receiverUsername).orElse(null);

        if (call != null) {
            // ICE candidate'ı listeye ekle
            call.getCandidates().add(candidate);
            try {
                callRepository.save(call);
            } catch (Exception e) {
                System.err.println("Error saving call: " + e.getMessage());
            }
        }

        // Alıcıya ICE candidate gönder
        messagingTemplate.convertAndSendToUser(receiverUsername, "/queue/candidate", candidate);
    }

    // Çağrı süresini hesapla (saniye cinsinden)
    private Long calculateDuration(Long startTime, Long endTime) {
        return Duration.between(Instant.ofEpochMilli(startTime), Instant.ofEpochMilli(endTime)).getSeconds();
    }

    // Tüm çağrıları listele
    public List<Call> getAllCalls() {
        return callRepository.findAll();
    }

    // Kullanıcının çevrimdışı olup olmadığını kontrol et
    public boolean isUserOnline(String username) {
        // Gerçek zamanlı kullanıcı durumu kontrolü eklenebilir
        return true; // Basitçe her zaman çevrimiçi kabul ediyor
    }
    @Transactional
    public void saveCall(Call call) {
        logger.info("Saving call: {}", call);
        callRepository.save(call);
    }
}
