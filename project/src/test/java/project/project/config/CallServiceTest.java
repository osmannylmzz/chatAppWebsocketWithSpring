//package project.project.config;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//import project.project.Service.VoiceCallService;
//import project.project.model.Call;
//
//import java.time.Instant;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class CallServiceTest {
//
//    @Autowired
//    private VoiceCallService callService;
//
//   @Test
//    public void testMongoDBInsert() {
//        Call call = new Call();
//        call.setCallerUsername("YourUsername");
//        call.setReceiverUsername("yusuf");
//        call.setCallStatus("IN_PROGRESS");
//        call.setStartTime(System.currentTimeMillis());
//        call.setTimestamp(Instant.ofEpochSecond(System.currentTimeMillis()));
//        try {
//            callService.testMongoDBInsert();
//            // Burada kaydın başarılı olduğuna dair bir test ekleyebilirsiniz.
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}