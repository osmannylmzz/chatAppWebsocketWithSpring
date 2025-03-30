package project.project.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.project.Repository.MessageRepository;
import project.project.Repository.UserRepository;
import project.project.model.Message;
import project.project.model.User;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    private MongoTemplate mongoTemplate;

    @Autowired
    private UserRepository userRepository;


    @Transactional
    public ResponseEntity<?> saveMessage(Message message) {

        Optional<User> senderUserOptional = userRepository.findByUsername(message.getSender());
        Optional<User> receiverUserOptional = userRepository.findByUsername(message.getReceiver());


        if (senderUserOptional.isEmpty() || receiverUserOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Geçersiz kullanıcı.");
        }

        User senderUser = senderUserOptional.get();
        User receiverUser = receiverUserOptional.get();


        message.setSenderId((senderUser.getId()));
        message.setReceiverId((receiverUser.getId()));


        messageRepository.save(message);


        return ResponseEntity.ok("Mesaj başarıyla kaydedildi.");
    }


    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    public List<Message> getMessagesBySenderAndReceiver(String sender, String receiver) {

        List<Message> messages = messageRepository.findBySenderAndReceiver(sender, receiver);
        List<Message> messages1 = messageRepository.findBySenderAndReceiver(receiver, sender);


        messages.addAll(messages1);


        messages.sort(Comparator.comparing(Message::getDate).reversed());

        return messages;
    }

}
