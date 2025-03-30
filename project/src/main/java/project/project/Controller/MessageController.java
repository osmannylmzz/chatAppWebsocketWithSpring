package project.project.Controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.project.Repository.MessageRepository;
import project.project.model.Message;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    @Autowired
    private MessageRepository messageRepository;

    @PutMapping("/{id}/seen")
    public ResponseEntity<Map<String, String>> markAsSeen(@PathVariable String id) {
        Optional<Message> optionalMessage = messageRepository.findById(id);

        if (!optionalMessage.isPresent()) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "Hata: Mesaj bulunamadı!");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        Message message = optionalMessage.get();
        message.setRead(true);
        message.setReadTimestamp(LocalDateTime.now());
        messageRepository.save(message);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Mesaj görüldü olarak işaretlendi");
        return ResponseEntity.ok(response);
    }
}

