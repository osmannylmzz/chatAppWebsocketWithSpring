//package project.project.Controller;
//
//import org.apache.logging.log4j.message.SimpleMessage;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.messaging.handler.annotation.MessageMapping;
//import org.springframework.messaging.handler.annotation.Payload;
//import org.springframework.messaging.handler.annotation.SendTo;
//import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
//import org.springframework.messaging.simp.SimpMessagingTemplate;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.servlet.ModelAndView;
//import project.project.Repository.MessageRepository;
//import project.project.Service.MessageService;
//
//import project.project.model.Message;
//
//import java.time.LocalDateTime;
//import java.util.List;
//
//
//@RequestMapping("/api/chat")
//@Controller
//public class ChatController {
//
//    @Autowired
//    private MessageRepository messageRepository;
//
//    @Autowired
//    private SimpMessagingTemplate messagingTemplate;  // Mesaj gönderimi için kullanılır
//
//    @Autowired
//    private MessageService messageService;
//
//    @GetMapping("/chat2")
//    public ModelAndView showLoginForm() {
//        return new ModelAndView("chat2");
//    }
//
//    @GetMapping("/messages")
//    public List<Message> getMessages() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String username = (authentication != null) ? authentication.getName() : null;
//
//        if (username == null) {
//            return null;  // Unauthorized kullanıcıya mesaj verilmez.
//        }
//
//        // Kullanıcının hem gönderdiği hem aldığı mesajları al
//        return messageRepository.findBySenderOrReceiverOrderByDateAsc(username, username);
//    }
//
//    /**
//     * Grup mesajı gönderme işlemi (WebSocket üzerinden yapılacak).
//     */
//    @MessageMapping("/sendToGroup")
//    public void sendMessageToGroup(Message message) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String sender = (authentication != null) ? authentication.getName() : null;
//
//        if (sender == null) {
//            return; // Unauthorized kullanıcıya mesaj verilmez.
//        }
//
//        // Mesaj doğrulaması
//        if (message.getMessage() == null || message.getMessage().trim().isEmpty()) {
//            return;
//        }
//
//        // Gönderici bilgilerini ayarla
//        message.setSender(sender);
//        message.setDate(LocalDateTime.now());
//        message.setRead(false); // Başlangıçta okunmamış
//
//        try {
//            // Mesajı kaydet
//            ResponseEntity<?> savedMessage = messageService.saveMessage(message);
//
//            // Mesajı tüm grup üyelerine gönder
//            messagingTemplate.convertAndSend("/topic/group", savedMessage); // Burada '/topic/group' grup mesajlarını yayınlar
//
//        } catch (Exception e) {
//            System.err.println("Mesaj kaydedilirken hata oluştu: " + e.getMessage());
//        }
//    }
//
//    /**
//     * Bireysel mesajı sadece alıcıya gönderme (WebSocket üzerinden yapılacak).
//     */
//    @MessageMapping("/sendToUser")
//    public void sendMessageToUser(Message message) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String sender = (authentication != null) ? authentication.getName() : null;
//        String receiver = message.getReceiver();
//
//        if (sender == null || receiver == null) {
//            return; // Unauthorized veya eksik alıcı/gönderici
//        }
//
//        // Mesaj doğrulaması
//        if (message.getMessage() == null || message.getMessage().trim().isEmpty()) {
//            return;
//        }
//
//        // Gönderici bilgilerini ayarla
//        message.setSender(sender);
//        message.setDate(LocalDateTime.now());
//        message.setRead(false); // Başlangıçta okunmamış
//
//        try {
//            // Mesajı kaydet
//            ResponseEntity<?> savedMessage = messageService.saveMessage(message);
//
//            // Mesajı sadece alıcıya gönder
//            messagingTemplate.convertAndSendToUser(receiver, "/queue/reply", savedMessage); // Burada '/queue/reply' sadece alıcıya mesaj gönderir
//
//        } catch (Exception e) {
//            System.err.println("Mesaj kaydedilirken hata oluştu: " + e.getMessage());
//        }
//    }
//
//    /**
//     * Belirli iki kullanıcı arasındaki mesajları al.
//     */
//    @GetMapping("/messages/{sender}/{receiver}")
//    public List<Message> getMessagesBetweenUsers(@PathVariable String sender, @PathVariable String receiver) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String username = (authentication != null) ? authentication.getName() : null;
//
//        if (username == null || (!sender.equals(username) && !receiver.equals(username))) {
//            return null;  // Unauthorized veya yetkisiz kullanıcı
//        }
//
//        // Belirtilen iki kullanıcı arasındaki mesajları al
//        return messageRepository.findBySenderAndReceiverOrderByDateAsc(sender, receiver);
//    }
//
//    /**
//     * Mesaj okundu olarak işaretleme.
//     */
//    @PutMapping("/messages/{id}/read")
//    public String markMessageAsRead(@PathVariable String id) {
//        Message message = messageRepository.findById(id).orElse(null);
//        if (message == null) {
//            return "Message not found";
//        }
//        message.setRead(true);
//        message.setReadTimestamp(LocalDateTime.now());  // Okundu zamanı LocalDateTime olarak ayarlanabilir
//        messageRepository.save(message);
//        return "Message marked as read";
//    }
//
//    /**
//     * Tüm mesajları al (admin/test için kullanılabilir).
//     */
//    @GetMapping("/all")
//    public List<Message> getAllMessages() {
//        return messageRepository.findAll();
//    }
//}
