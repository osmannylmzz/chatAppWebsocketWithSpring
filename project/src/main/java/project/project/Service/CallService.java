//package project.project.service;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import project.project.model.Call;
//import project.project.Repository.CallRepository;
//import java.time.Instant;
//import java.util.List;
//import java.util.Optional;
//
//@Service
//public class CallService {
//
//    @Autowired
//    private CallRepository callRepository;
//
//    // Aramayı başlatan metot
//    public Call startCall(String callerUsername, String receiverUsername) {
//        // Kullanıcı zaten aktif bir görüşme yapıyorsa engelleme
//        if (callRepository.existsByCallerUsernameAndCallStatus(callerUsername, "ACTIVE")) {
//            throw new RuntimeException("Caller already in an active call");
//        }
//
//        Call call = new Call();
//        call.setCallerUsername(callerUsername);
//        call.setReceiverUsername(receiverUsername);
//        call.setCallStatus("ACTIVE");
//        call.setStartTime(Instant.now().toEpochMilli());
//        return callRepository.save(call);
//    }
//
//    // Aramayı bitiren metot
//    public Call endCall(String callId) {
//        Optional<Call> callOpt = callRepository.findById(callId);
//        if (callOpt.isPresent()) {
//            Call call = callOpt.get();
//            call.setCallStatus("ENDED");
//            call.setEndTime(Instant.now().toEpochMilli());
//            call.setDuration((call.getEndTime() - call.getStartTime()) / 1000); // Duration in seconds
//            return callRepository.save(call);
//        } else {
//            throw new RuntimeException("Call not found");
//        }
//    }
//
//    // Tüm aramaları getiren metot
//    public List<Call> getAllCalls() {
//        return callRepository.findAll();
//    }
//
//    // Aramayı susturan metot
//    public Call muteCall(String callId) {
//        Optional<Call> callOpt = callRepository.findById(callId);
//        if (callOpt.isPresent()) {
//            Call call = callOpt.get();
//            call.setMuted(true);
//            return callRepository.save(call);
//        } else {
//            throw new RuntimeException("Call not found");
//        }
//    }
//
//    // Susturmayı açan metot
//    public Call unmuteCall(String callId) {
//        Optional<Call> callOpt = callRepository.findById(callId);
//        if (callOpt.isPresent()) {
//            Call call = callOpt.get();
//            call.setMuted(false);
//            return callRepository.save(call);
//        } else {
//            throw new RuntimeException("Call not found");
//        }
//    }
//
//    // Aramayı beklemeye alan metot
//    public Call holdCall(String callId) {
//        Optional<Call> callOpt = callRepository.findById(callId);
//        if (callOpt.isPresent()) {
//            Call call = callOpt.get();
//            call.setOnHold(true);
//            return callRepository.save(call);
//        } else {
//            throw new RuntimeException("Call not found");
//        }
//    }
//
//    // Beklemeye almayı kaldıran metot
//    public Call resumeCall(String callId) {
//        Optional<Call> callOpt = callRepository.findById(callId);
//        if (callOpt.isPresent()) {
//            Call call = callOpt.get();
//            call.setOnHold(false);
//            return callRepository.save(call);
//        } else {
//            throw new RuntimeException("Call not found");
//        }
//    }
//
//    // Kullanıcının çevrimdışı olup olmadığını kontrol etme
//    public boolean isUserOnline(String username) {
//        // Burada, gerçek zamanlı kullanıcı durumu (örneğin, WebSocket oturumları) kontrol edebilirsiniz
//        return true;  // Test amacıyla her zaman online kabul edelim
//    }
//
//    // Veritabanına kaydetme metodları (offline kullanıcılar için)
//    public void saveOfferToDatabase(String offer, String receiverUsername) {
//        // Bu veriyi veritabanına kaydedin
//    }
//
//    public void saveAnswerToDatabase(String answer, String receiverUsername) {
//        // Bu veriyi veritabanına kaydedin
//    }
//
//    public void saveCandidateToDatabase(String candidate, String receiverUsername) {
//        // Bu veriyi veritabanına kaydedin
//    }
//}
