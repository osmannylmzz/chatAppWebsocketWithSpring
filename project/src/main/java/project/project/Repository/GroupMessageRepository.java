package project.project.Repository;

import org.springframework.stereotype.Repository;
import project.project.model.GroupsMessage;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
@Repository
public interface GroupMessageRepository extends MongoRepository<GroupsMessage, String> {

    List<GroupsMessage> findByGroupname(String groupname);
}