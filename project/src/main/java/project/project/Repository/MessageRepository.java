package project.project.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import project.project.model.Message;

import java.util.List;
@Repository
public interface MessageRepository extends MongoRepository<Message, String> {

    List<Message> findBySenderOrReceiverOrderByDateAsc(String sender, String receiver);



    List<Message> findBySenderAndReceiverOrderByDateAsc(String sender, String receiver);

    List<Message> findBySenderAndReceiverOrReceiverAndSenderOrderByDateAsc(String sender1, String receiver1, String sender2, String receiver2);

    List<Message> findBySenderAndReceiver(String sender, String receiver);
}

