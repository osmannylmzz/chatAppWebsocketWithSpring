//package project.project.Controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import project.project.Repository.MessageRepository;
//import project.project.model.Message;
//
//@Component
//public class MongoDBTest {
//
//    @Autowired
//    private MessageRepository yourRepository; // Repository sınıfınızı buraya ekleyin
//
//    public void testConnection() {
//        Message entity = new Message(); // Entity sınıfınızı oluşturun
//       entity.setId("1");
//       entity.setRead(false);
//       entity.setSender("osman2");
//       entity.setReceiver("osman3");
//       entity.setDate(null);
//        entity.setMessage("test3"); // Alanları doldurun
//        yourRepository.save(entity);
//        System.out.println("Veri eklendi.");
//    }
//}