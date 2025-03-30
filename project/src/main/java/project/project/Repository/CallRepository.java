package project.project.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import project.project.model.Call;

import java.util.List;
import java.util.Optional;

@Repository
public interface CallRepository extends MongoRepository<Call, String> {
    Optional<Call> findById(String id);

    List<Call> findAll();

    List<Call> findByCallerUsernameOrReceiverUsername(String callerUsername, String receiverUsername);



    boolean existsByCallerUsernameAndCallStatus(String callerUsername, String callStatus);


    Optional<Call> findByReceiverUsername(String receiverUsername);
}

