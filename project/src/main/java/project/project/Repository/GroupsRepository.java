package project.project.Repository;

import org.springframework.stereotype.Repository;
import project.project.model.Groups;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
@Repository
public interface GroupsRepository extends MongoRepository<Groups, String> {

}