package project.project.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import project.project.Service.MessageService;
import project.project.model.Message;
import project.project.model.User;
import project.project.Repository.MessageRepository;
import project.project.Repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
public class WebSocketController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MessageRepository messageRepository;

    private Message message = new Message();

    @Autowired
    private MessageService messageService;


    @GetMapping("/chat")
    public ModelAndView showLoginForm() {
        return new ModelAndView("chat2");
    }

    @MessageMapping("/chat")
    public void processMessage(@Payload Message message) {
        // Alıcıyı bul
        Optional<User> receiverUserOptional = userRepository.findByUsername(message.getReceiver());
        Optional<User> senderUserOptinal = userRepository.findByUsername(message.getSender());

        if (receiverUserOptional.isEmpty()) {
            throw new IllegalArgumentException("Alıcı bulunamadı.");
        }
        User receiverUser = receiverUserOptional.get();
        User senderUser= senderUserOptinal.get();


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String senderUsername = (authentication != null) ? authentication.getName() : message.getSender();

        if (senderUsername == null || receiverUser == null) {
            throw new IllegalArgumentException("Gönderici veya alıcı eksik.");
        }




        messagingTemplate.convertAndSendToUser(
                message.getSender(),
                "/queue/reply",
                message
        );


        messagingTemplate.convertAndSendToUser(
                message.getReceiver(),
                "/queue/reply",
                message
        );
        message.setSenderId(senderUser.getId());
        message.setReceiverId(receiverUser.getId());
        message.setSender(senderUsername);
        message.setDate(LocalDateTime.now());
        message.setRead(false);
        messageRepository.save(message);
        System.out.println("Mesaj alıcıya gönderildi: " + message.getReceiver());
    }

    @GetMapping("/messages/{sender}/{receiver}")
    public ResponseEntity<List<Message>> getMessagesBetweenUsers(@PathVariable String sender, @PathVariable String receiver) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = (authentication != null) ? authentication.getName() : null;

        System.out.println("Sender: " + sender + ", Receiver: " + receiver);

        List<Message> messages = messageService.getMessagesBySenderAndReceiver(sender, receiver);
        if (messages.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
        return ResponseEntity.ok(messages);
    }
    @MessageMapping("/message/seen")
    @SendTo("/topic/seen")
    public SeenNotification notifySeen(SeenNotification notification) {
        return notification;
    }

    public static class SeenNotification {
        private String messageId;
        private String sender;

        // Getter ve Setter
    }


//    // Mesajı kaydetmek için başka bir metod (isteğe bağlı)
//    public ResponseEntity<?> saveMessage(Message message) {
//        // Kullanıcıları sorgula
//        Optional<User> senderUserOptional = userRepository.findByUsername(message.getSender());
//        Optional<User> receiverUserOptional = userRepository.findByUsername(message.getReceiver());
//
//        if (senderUserOptional.isEmpty() || receiverUserOptional.isEmpty()) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Geçersiz kullanıcı.");
//        }
//
//        // Kimlikleri ekle
//        User senderUser = senderUserOptional.get();
//        User receiverUser = receiverUserOptional.get();
//
//        message.setSenderId(senderUser.getId());
//        message.setReceiverId(receiverUser.getId());
//
//        // Mesajı kaydet
//        messageRepository.save(message);
//
//        return ResponseEntity.ok("Mesaj başarıyla kaydedildi.");
//    }
}
